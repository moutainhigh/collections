package com.gwssi.rodimus.upload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.StringUtil;


/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 * @author lixibo
 */
public class UploadListUtil {
	
	/**
	 * 查询状态不是删除的文件
	 * @param gid
	 * @return
	 */
	public static List<Map<String, Object>> getFileList(String gid) {
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("无效的gid：gid为空");
		}
		
		String sql = "select f.f_id,f.category_id,f.ref_id,f.ref_text,f.file_id,f.thumb_file_id,f.approve_msg,f.state,gid from be_wk_upload_file f where  gid=? and state<>'2' order by category_id,sn";
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql, gid);
		return list;
	}
	/**
	 * 获取指定分类下的图片文件ID列表
	 * @param categoryId
	 * @return
	 */
	public static List<Map<String, Object>> getListByCategory(String categoryId,String gid){

		if(StringUtils.isBlank(categoryId) || StringUtils.isBlank(gid)){
			throw new RuntimeException("无效的分类标识符");
		}
		if(StringUtils.isBlank(gid) ){
			throw new RuntimeException("无效的业务标识");
		}
		String sql = "select f_id,file_id,thumb_file_id,data_type,f.state,f.sn,f.category_id,ref_id from be_wk_upload_file f where gid=? and category_id=? and state<>'2' order by sn";
		return DaoUtil.getInstance().queryForList(sql, gid,categoryId);
	}

	/**
	 * 将已上传的文件与配置信息匹配起来
	 * @param configList
	 * @param fileList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> mergeList(
			List<Map<String, Object>> configList,
			List<Map<String, Object>> fileList) {
		List<List<Object>> needUpdate = new ArrayList<List<Object>>();
		List<List<Object>> needDelete = new ArrayList<List<Object>>();
		if(configList==null || fileList==null){
			return configList;
		}
		//遍历已经上传的文件找到对应的配置项，如果有业务数据关联，则关联
		for (Map<String,Object> file : fileList) {
			String categoryId = StringUtil.safe2String(file.get("categoryId"));
			String refId=StringUtil.safe2String(file.get("refId"));
			
			//遍历配置信息
			for (Map<String, Object> configRow : configList) {
				String configCategoryId = StringUtil.safe2String(configRow.get("categoryId"));
				if(!categoryId.equals(configCategoryId)){
					//该文件不属于这个类别
					continue;
				}
				//配置信息中的文件列表
				List<Map<String, Object>> tmpList = (List<Map<String, Object>>) configRow.get("fileArray");
				if(tmpList ==null || tmpList.isEmpty()){
					tmpList = new ArrayList<Map<String,Object>>();							
				}
				if(StringUtils.isBlank(refId)){
					//不需要关联，直接加到配置列表中即可
					
					Map<String,Object> map = new HashMap<String, Object>();						
					map.put("fileId", file.get("fileId"));
					map.put("fId", file.get("fId"));
					map.put("categoryId", file.get("categoryId"));
					map.put("state", file.get("state"));
					map.put("thumbFileId", file.get("thumbFileId"));
					map.put("approveMsg", file.get("approveMsg"));
					tmpList.add(map);
					configRow.put("fileArray", tmpList);
					break;
				}
								
				
				//遍历查找需要关联项
				
				int tmpList_len = tmpList.size();
				int j=0;
				for (j = 0; j < tmpList_len; j++) {
					Map<String,Object> objMap = tmpList.get(j);
					Object fileId = objMap.get("fileId");
					Object configRefId = objMap.get("refId");
					if(fileId!=null){
						//这是已经关联过的数据
						continue;
					}
					//refId一致，找到映射关系
					if(refId.equals(configRefId) ){
						String refTextOld = StringUtil.safe2String(file.get("refText"));
						String refTextNew = StringUtil.safe2String(objMap.get("refText"));
						if(!refTextOld.equals(refTextNew)){
							//数据有更新
							//出现场景：上传了图片后修改了主要人员的姓名
							List<Object> tmp = new ArrayList<Object>();
							tmp.add(refTextNew);
							tmp.add(file.get("fId"));
							needUpdate.add(tmp);
						}
						file.putAll(objMap);
						objMap.putAll(file);
						break;
					}	
					
				}
				if(j==tmpList_len){
					//有ref_id但是配置项中没有找到对应项
					//出现这种的情况的可能场景：添加了一个主要人员信息，上传了图片后又删除了这个人员。
					List<Object> tmp = new ArrayList<Object>();
					tmp.add("2");
					tmp.add(file.get("fId"));
					needDelete.add(tmp);
				}
			}
			
							
		}
		//be_wk_upload_file表的refText数据要更新
		if(needUpdate.size()>0){
			String sql ="update be_wk_upload_file set ref_text=? where f_id=?";
			DaoUtil.getInstance().executeBatch(sql, needUpdate);
			
		}
		//be_wk_upload_file表有数据要标记为删除
		if(needDelete.size()>0){
			String sql="update be_wk_upload_file set state=? where f_id=?";
			DaoUtil.getInstance().executeBatch(sql, needDelete);
			
		}
		return configList;
	}
	public static Map<String, Object> getPicInfo(String fId) {
		
		String sql = "select f.gid from be_wk_upload_file f where f.f_id = ?";
		String gid = DaoUtil.getInstance().queryForOneString(sql, fId);
		
		Map<String, Object> ret = new HashMap<String,Object>();
		
		sql = "select f.data_type,f.ref_id,f.approve_msg,f.state,f.category_id from be_wk_upload_file f where f.f_id = ?";
		Map<String,Object> row = DaoUtil.getInstance().queryForRow(sql, fId);
		if(row==null){
			throw new RuntimeException("传入的fId参数不正确。");
		}
		String dataType = StringUtil.safe2String(row.get("dataType"));
		String refId = StringUtil.safe2String(row.get("refId"));
		String state = StringUtil.safe2String(row.get("state"));
		String approveMsg = StringUtil.safe2String(row.get("approveMsg"));

		if("dom".equals(refId)){// 住所产权页
			dataType = "signAddr";
		}
		if("inv".equals(refId)){// 股东名称列表
			dataType = "signInv";
		}
		if("leg".equals(refId)){// 法定代表人
			dataType = "signLe";
		}
		if("leg_auth".equals(refId)){// 法定代表人授权
			dataType = "signLeAuth";
		}
		if("apply_name".equals(refId)){// 申请人信息
			dataType = "signLinkman";
		}
		if("zph".equals(refId)){// 代理机构指派函
			dataType = "agentAuth";
		}
		if("cyzg".equals(refId)){// 经济人从业资格
			dataType = "agentPersonCert";
		}
		if("yyzz".equals(refId)){// 代理机构营业执照
			dataType = "agentCert";
		}
		
		// 数据类型
		ret.put("dataType", dataType);
		ret.put("state", state);
		ret.put("approveMsg", approveMsg);
		
		sql = "select m.msg_id,m.msg,m.user_name,m.reg_org,m.create_time from be_wk_upload_file_msg m where m.f_id = ? and m.flag='1' order by m.create_time desc";
		List<Map<String,Object>> hisApproveMsg = DaoUtil.getInstance().queryForList(sql, fId);
		
		// 历史审批意见
		ret.put("hisApproveMsg", hisApproveMsg);
		
		// 关联业务数据
		if("inv".equals(dataType)){
			sql = "select i.inv as name,td.wb cer_type,i.cer_no, i.dom,fd.wb as folk from be_wk_investor i "+
					" left join t_pt_dmsjb td on i.cer_type=td.dm and td.dmb_id='CB16' "+
					" left join t_pt_dmsjb fd on i.folk=fd.dm and fd.dmb_id='CB32' "+
					"  where i.investor_id = ?"; 
			Map<String,Object> data = DaoUtil.getInstance().queryForRow(sql, refId);
			ret.put("data", data);
			// 身份证信息
//			if(data!=null){
//				String cerType = StringUtil.safe2String(data.get("cerType"));
//				if("中华人民共和国居民身份证".equals(cerType)){ // 身份证
//					String name = StringUtil.safe2String(data.get("inv"));
//					String cerNo = StringUtil.safe2String(data.get("cerNo"));
//					IdentityCardBO identityBo = IdentityCardUtil.getIdentityCardInfo(name, cerNo);
//					ret.put("idCardInfo", identityBo);
//				}
//			}
		}
		if("cpInv".equals(dataType)){
			sql = "select i.inv as name,td.wb as b_lic_type,i.b_lic_no,i.corp_rpt,i.dom,i.prov from be_wk_investor i "+
					 "left join t_pt_dmsjb td on i.b_lic_type=td.dm and td.dmb_id='CA50' "+
					 " where i.investor_id = ? ";
			Map<String,Object> data = DaoUtil.getInstance().queryForRow(sql, refId);
			ret.put("data", data);
			//右侧 北京企业，照面信息
			if(data!=null){
				//String prov = StringUtil.safe2String(data.get("prov"));
				//if("110000".equals(prov)){
				String bLicNo =  StringUtil.safe2String(data.get("bLicNo"));
				if(StringUtil.isNotBlank(bLicNo)){
					sql = "select to_char(t.op_from,'yyyy-MM-dd') as op_from,to_char(t.op_to,'yyyy-MM-dd') as op_to,t.op_scope || t.pt_bus_scope as op_scope from cp_rs_ent t where t.reg_no = ? or t.uni_scid=? order by t.reg_end_date desc";
					Map<String,Object> rsEntData = DaoUtil.getInstance().queryForRow(sql, bLicNo, bLicNo);
					ret.put("ent", rsEntData);
				}
				//}
			}
		}
		if("mbr".equals(dataType)){
			sql = "select m.name, nvl(m.le_rep_sign,'0') as le_rep_sign,m.house_add,cd.wb as cer_type,m.cer_no, "+
				" (select listagg(jd.wb,'、') within GROUP(order by j.position) as position from be_wk_job j "+
				" left join t_pt_dmsjb jd on jd.dm=j.position and jd.dmb_id='CB18' where j.entmember_id=m.entmember_id) as position "+
				" from be_wk_entmember m "+
				" left join t_pt_dmsjb cd on cd.dm=m.cer_type and cd.dmb_id='CB16' "+
				" where m.entmember_id = ?";
			Map<String,Object> data = DaoUtil.getInstance().queryForRow(sql, refId);
			ret.put("data", data);
			// 身份证信息
//			if(data!=null){
//				String cerType = StringUtil.safe2String(data.get("cerType"));
//				if("中华人民共和国居民身份证".equals(cerType)){ // 身份证
//					String name = StringUtil.safe2String(data.get("name"));
//					String cerNo = StringUtil.safe2String(data.get("cerNo"));
//					IdentityCardBO identityBo = IdentityCardUtil.getIdentityCardInfo(name, cerNo);
//					ret.put("idCardInfo", identityBo);
//				}
//			}
		}
		if("addr".equals(dataType) || "signAddr".equals(dataType)){
			sql = "select t.ent_name, t.op_loc,t.dom_owner,t.dom_pro_right,decode(t.dom_own_type,'1','有房产证','2','无房产证') as dom_own_type,t.busi_place,t.pro_loc from be_wk_ent t where t.gid = ?";
			Map<String,Object> data = DaoUtil.getInstance().queryForRow(sql, gid);
			ret.put("data", data);
		}
		
		if("signInv".equals(dataType)){
			Map<String,List<Map<String,Object>>> inv = new HashMap<String,List<Map<String,Object>>>();
			sql = "select i.inv from be_wk_investor i where i.inv_type in ('20','35','36','91') and i.gid = ?";
			List<Map<String,Object>> personList = DaoUtil.getInstance().queryForList(sql, gid);
			sql = "select i.inv from be_wk_investor i where i.inv_type not in ('20','35','36','91') and i.gid = ?";
			List<Map<String,Object>> cpList = DaoUtil.getInstance().queryForList(sql, gid);
			inv.put("person", personList);
			inv.put("cp", cpList);
			ret.put("data", inv);
			
			sql = "select t.ent_name,t.linkman,t.cert_type,t.cert_no from be_wk_requisition t where t.gid = ?";
			Map<String,Object> req = DaoUtil.getInstance().queryForRow(sql, gid);
			ret.put("req", req);
			
			sql = "select m.name,listagg(jd.wb,'、') within group( order by j.position) as position from be_wk_entmember m "+
				" left join be_wk_job j on m.entmember_id=j.entmember_id "+
				" left join t_pt_dmsjb jd on jd.dm = j.position and jd.dmb_id='CB18' "+
				" where m.gid = ? "+
				" group by m.name "+
				" order by m.name ";
			List<Map<String,Object>> mbr = DaoUtil.getInstance().queryForList(sql, gid);
			ret.put("mbr", mbr);
		}
		
		if("signLe".equals(dataType) || "signLeAuth".equals(dataType)){
			sql = "select i.le_rep_name as name, cd.wb as cer_type,i.le_rep_cer_no,jd.wb as position,i.le_rep_mob as mob_tel,i.le_rep_email as email  " +
				" from be_wk_le_rep i  " +
				" left join t_pt_dmsjb cd on cd.dm=i.le_rep_cer_type and cd.dmb_id='CB16'  " +
				" left join t_pt_dmsjb jd on jd.dm=i.le_rep_job_code and jd.dmb_id='CB18'  " +
				"  where i.gid = ?   " ;
			Map<String,Object> data = DaoUtil.getInstance().queryForRow(sql, gid);
			ret.put("data", data);
			
			sql = "select t.ent_name,t.linkman,t.cert_type,t.cert_no from be_wk_requisition t where t.gid = ?";
			Map<String,Object> req = DaoUtil.getInstance().queryForRow(sql, gid);
			ret.put("req", req);
		}
		
		if("signLinkman".equals(dataType)){
			sql = "select t.ent_name,t.linkman,cd.wb as cer_type,t.cert_no,t.agent_reg_no,t.agent_name,opd.wb as operation_type  from be_wk_requisition t "+
				" left join t_pt_dmsjb cd on cd.dm=t.cert_type and cd.dmb_id='CB16' "+
				" left join t_pt_dmsjb opd on opd.dm=t.operation_type and opd.dmb_id='DFCD05'  "+	
				" where t.gid = ?" ;
			Map<String,Object> data = DaoUtil.getInstance().queryForRow(sql, gid);
			ret.put("data", data);
		}
		
		if("agentAuth".equals(dataType) || "agentPersonCert".equals(dataType) || "agentCert".equals(dataType)){
			sql = "select r.linkman,cd.wb as cer_type,r.cert_no as cer_no,r.agent_reg_no,r.agent_name from be_wk_requisition r "+
					" left join t_pt_dmsjb cd on cd.dm=r.cert_type and cd.dmb_id='CB16' where r.gid = ?";
			Map<String,Object> data = DaoUtil.getInstance().queryForRow(sql, gid);
			ret.put("data", data);
		}
		return ret;
	}
	

}
