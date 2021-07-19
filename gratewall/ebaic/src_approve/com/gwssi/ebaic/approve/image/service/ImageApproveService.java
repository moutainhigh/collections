package com.gwssi.ebaic.approve.image.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.approve.util.ApproveUserUtil;
import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.upload.UploadConfigUtil;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.util.UUIDUtil;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service(value="imageApproveService")
public class ImageApproveService {
	
	public List<Map<String,Object>> getCatgoryList(OptimusRequest request){
		if(request==null){
			throw new EBaicException("request不能为空。");
		}
		String gid = ParamUtil.get("gid");
		StringBuffer sb = new StringBuffer();
		sb.append("select category_id, sum(total) as total_cnt, sum(to_cnt) as to_cnt,sum(pass_cnt) as pass_cnt,sum(unpass_cnt) as un_pass_cnt ")
			.append("from (")
			.append("select f.category_id,f.state,1 as total,case when f.state=1 then 1 else 0 end as to_cnt,case when f.state=3 then 1 else 0 end as pass_cnt,case when f.state=4 then 1 else 0 end as unpass_cnt ")
			.append("from be_wk_upload_file f where f.category_id not in('1','5','8') and f.gid = ? and f.state not in ('2')")
			.append("union ")
			.append("select f.category_id,f.state,1 as total,case when f.state=1 then 1 else 0 end as to_cnt,case when f.state=3 then 1 else 0 end as pass_cnt,case when f.state=4 then 1 else 0 end as unpass_cnt ")
            .append("from be_wk_upload_file f where f.category_id ='5' and f.gid = ? and f.state not in ('2') and f.ref_id<>'dom' ")
            .append("union ")
            .append("(select f.category_id,f.state,1 as total,case when f.state=1 then 1 else 0 end as to_cnt,case when f.state=3 then 1 else 0 end as pass_cnt,case when f.state=4 then 1 else 0 end as unpass_cnt ")
            .append("from be_wk_upload_file f where f.category_id ='1' and f.gid = ? and f.state not in ('2') ")
            .append("union all ")
            .append("select '1',f.state,1 as total,case when f.state=1 then 1 else 0 end as to_cnt,case when f.state=3 then 1 else 0 end as pass_cnt,case when f.state=4 then 1 else 0 end as unpass_cnt ")
            .append("from be_wk_upload_file f where f.category_id ='5' and f.gid = ? and f.state not in ('2') and f.ref_id='dom') ")
		    .append(") t ")
			.append("group by category_id ")
			.append("order by category_id");
		List<Map<String, Object>> dataSummaryList = DaoUtil.getInstance().queryForList(sb.toString(), gid,gid,gid,gid);
		Map<String,Map<String,Object>> dataSummaryMap = new HashMap<String,Map<String,Object>>();
		for(Map<String, Object> row : dataSummaryList){
			String categoryId = StringUtil.safe2String(row.get("categoryId"));
			row.remove("categoryId");
			dataSummaryMap.put(categoryId, row);
		}
		
		List<Map<String, Object>> configList = UploadConfigUtil.getCatgoryList(UploadConfigUtil.CP_SETUP_1100,request);
		for(Map<String, Object> row : configList){
			String categoryId = StringUtil.safe2String(row.get("categoryId"));
			Map<String, Object> dataSummaryRow = dataSummaryMap.get(categoryId);
			if(dataSummaryRow!=null){
				row.putAll(dataSummaryRow);
			}
		}
		
		return configList;
	}
	/**
	 * 保存图像审查意见。
	 * @param fileId
	 * @param result
	 * @param remark
	 * @return
	 */
	public void saveApproveResult(String fId,String gid, String state,String msg){
		if(StringUtil.isBlank(fId)){
			throw new EBaicException("图片编号（fId）不能为空。");
		}
		SysmgrUser currentUser = ApproveUserUtil.getLoginUser();
		Calendar now = DateUtil.getCurrentTime();
		if(currentUser==null){
			throw new EBaicException("登录超时，请重新登录。");
		}
		
		String sql = "select f.ref_id,f.data_type,f.category_id from be_wk_upload_file f where f.f_id = ?";
		Map<String,Object> uploadFile = DaoUtil.getInstance().queryForRow(sql, fId);
		if(uploadFile==null){
			throw new EBaicException("找不到图片编号（fId）对应的记录。");
		}
		
		// 查看是否有当前审批意见
		sql = "select m.msg_id from be_wk_upload_file_msg m where m.f_id = ? and m.flag = '0' order by m.timestamp desc";
		String msgId = DaoUtil.getInstance().queryForOneString(sql, fId);
		if(StringUtil.isBlank(msgId)){
			// 如果没有，插入1条
			msgId = UUIDUtil.getUUID();
			sql = "insert into be_wk_upload_file_msg(msg_id,f_id,gid,msg,user_name,  reg_org,user_id,create_time,timestamp,flag,approve_state) "+
					" values(?,?,?,?,?, ?,?,?,?,?,?)";
			DaoUtil.getInstance().execute(sql, msgId, fId, gid,    msg,currentUser.getUserName(),  currentUser.getOrgCodeFk(),currentUser.getUserId(),now,now,"0",state);
			
			sql = "update be_wk_upload_file u set u.approve_msg= ? , u.approve_msg_id = ? where u.f_id = ? ";
			DaoUtil.getInstance().execute(sql, msg, msgId, fId);
		}else{
			// 如果有，更新
			sql = "update be_wk_upload_file_msg set msg=?, user_name=?, reg_org=?, user_id=?, timestamp=? , approve_state=? where msg_id = ?";
			DaoUtil.getInstance().execute(sql, msg, currentUser.getUserName(), currentUser.getOrgCodeFk(), currentUser.getUserId(),now,state, msgId);
		}
		
		sql = "update be_wk_upload_file f set f.state = ?,approve_msg=? where f.f_id = ?";
		DaoUtil.getInstance().execute(sql, state, msg, fId);
		
		if("3".equals(state)){// 如果是审批通过
			String refId = StringUtil.safe2String(uploadFile.get("refId"));
			String dataType = StringUtil.safe2String(uploadFile.get("dataType"));
			String categoryId = StringUtil.safe2String(uploadFile.get("categoryId"));
			if(StringUtil.isBlank(dataType)){
				sql = "select c.data_type from sys_upload_category c where c.category_id = ?";
				dataType = DaoUtil.getInstance().queryForOneString(sql, categoryId);
			}
			sql = "";
			if("inv".equals(dataType)){
				sql = "select i.cer_type,i.inv as name,i.cer_no from be_wk_investor i where i.investor_id = ?";
			}
			if("mbr".equals(dataType)){
				sql = "select m.cer_type,m.name,m.cer_no from be_wk_entmember m where m.entmember_id=?";
			}
			if("contact".equals(dataType)){
				sql = "select c.cer_type,c.name,c.cer_no from be_wk_entcontact c where c.contact_id=?";
			}
			if(StringUtil.isNotBlank(sql)){// 如果审核的是人的信息
				Map<String,Object> person = DaoUtil.getInstance().queryForRow(sql, refId);
				if(person!=null){
					String cerType = StringUtil.safe2String(person.get("cerType"));
					//if("1".equals(cerType)){//如果是身份证
						String name = StringUtil.safe2String(person.get("name"));
						String cerNo = StringUtil.safe2String(person.get("cerNo"));
						// 这里要做一件重要的事情，插入到 sysmgr_identity
						// 先看是否已经有了
						sql = "select count(1) as cnt from sysmgr_identity i where i.cer_type=? and i.name=? and i.cer_no=? and i.type='2'";
						long cnt = DaoUtil.getInstance().queryForOneLong(sql, cerType, name,cerNo);
						if(cnt>0){//如果已经有了
							sql = "update sysmgr_identity i set i.flag = '1' , i.timestamp=? , i.approve_user_id = ? , i.approve_user_name = ?, i.approver_date= ?, i.approve_msg= ? "
									+ "where i.type='2' and i.cer_type=? and i.cer_no=? and i.name = ? and i.flag='0' ";
							DaoUtil.getInstance().execute(sql, now,currentUser.getUserId(),currentUser.getUserName(),now, msg, cerType,cerNo,name);
						}else{
							sql = "insert into sysmgr_identity(identity_id,type,name,cer_type,cer_no,   approve_user_id,approve_user_name,approver_date,approve_msg,reg_org,   flag,timestamp) "
									+ "values (?,?,?,?,?,  ?,?,?,?,?,  ?,?)";
							String identityId = UUIDUtil.getUUID();
							DaoUtil.getInstance().execute(sql, identityId,"2",name,cerType,cerNo,  currentUser.getUserId(),currentUser.getUserName(),now,msg,currentUser.getOrgCodeFk(),	"1",now);
						}
					//}
				}
			}
		}
	}

}
