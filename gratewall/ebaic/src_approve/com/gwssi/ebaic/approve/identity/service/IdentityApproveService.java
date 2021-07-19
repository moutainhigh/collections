package com.gwssi.ebaic.approve.identity.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.approve.util.ApproveUserUtil;
import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.StringUtil;

@Service("identityApproveService")
public class IdentityApproveService {

	/**
	 * 加载待审核的图片。
	 * 
	 * @param gid
	 * @return
	 */
	public List<Map<String, Object>> load(String gid) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from (")
		.append(" select le.le_rep_cer_no as cer_no,lep.type as pic_type,lep.picture_id as biz_id,lep.file_id,lep.thumb_file_id,lep.flag as state from be_wk_le_rep le ")
		.append(" left join sysmgr_identity lei on le.le_rep_cer_type =lei.cer_type and le.le_rep_cer_no=lei.cer_no ")
		.append(" left join sysmgr_identity_picture lep on lep.identity_id=lei.identity_id ")
		.append(" where (lei.flag = '0' or lei.flag is null) and lei.type='0' and lep.picture_id is not null and le.gid = ? ")
		.append(" union ")
		.append(" select r.cert_no as cer_no, lep.type as pic_type,lep.picture_id as biz_id,lep.file_id,lep.thumb_file_id,lep.flag as state from be_wk_requisition r ")
		.append(" left join SYSMGR_IDENTITY li on r.cert_type=li.cer_type and r.cert_no=li.cer_no ")
		.append(" left join sysmgr_identity_picture lep on lep.identity_id=li.identity_id ")
		.append(" where (li.flag = '0' or li.flag is null) and li.type='0' and lep.picture_id is not null and r.gid = ?")
		.append("  ) t order by cer_no asc , pic_type asc ");

		List<Map<String, Object>> ret = DaoUtil.getInstance().queryForList(sb.toString(), gid, gid);
		return ret;
	}

	/**
	 * 加载单个图片信息。
	 * 
	 * {dataType:'mbr',data:{}, hisMsg:''}
	 * 
	 * @param biz
	 * @return
	 */
	public Map<String, Object> item(String bizId,String type) {
	    
		Map<String, Object> ret = new HashMap<String,Object>();
		ret.put("dataType", "identity");
		String sql = "select i.name,i.cer_type,i.cer_no,p.approve_msg,p.flag from sysmgr_identity_picture p "+
			" left join sysmgr_identity i on p.identity_id = i.identity_id "+
			" where i.type=? and p.picture_id = ? ";
		if(StringUtil.isBlank(type)) {
		    type="0";
		}
		Map<String,Object> data = DaoUtil.getInstance().queryForRow(sql, type.trim(),bizId);
		String hisMsg = "";
		if(data!=null){
			hisMsg = StringUtil.safe2String(data.get("approveMsg"));
			data.remove("approveMsg");
		}
		ret.put("data", data);
		ret.put("hisMsg", hisMsg);
		return ret;
	}
	//加载身份核查用户信息
    public Map<String, Object> loadCheckUser(String gid) {
        //0:法人，1：申请人，2：自然人股东，3：非自然人股东的法定代表人
        Map<String, Object> resultMap = new HashMap<String, Object>();
        StringBuffer sb = new StringBuffer();
        sb.append("select * from (")
        .append(" select le.le_rep_name as name,le.le_rep_cer_no as cer_no,'0' as user_type,lei.flag from be_wk_le_rep le "
                + " left join sysmgr_identity lei on le.le_rep_cer_type =lei.cer_type and le.le_rep_cer_no=lei.cer_no "
                + " left join sysmgr_identity_picture lep on lep.identity_id=lei.identity_id where "
                + " lei.type='0' and lei.flag not in('-1','3') and le.gid = ? group by le.le_rep_name,le.le_rep_cer_no,lei.flag")
        .append(" union ")
        .append(" select r.linkman as name,r.cert_no as cer_no,'1' as user_type,li.flag from be_wk_requisition r "
                + " left join SYSMGR_IDENTITY li on r.cert_type=li.cer_type and r.cert_no=li.cer_no "
                + " left join sysmgr_identity_picture lep on lep.identity_id=li.identity_id "
                + " where li.type='0' and li.flag not in('-1','3') and r.gid = ? group by r.linkman, r.cert_no,li.flag")
        .append(" union ")       
        .append(" select t.inv as name,t.cer_no as cer_no,'2' as user_type,m.flag from be_wk_investor t "
                + " left join sysmgr_identity m on t.cer_type = m.cer_type and t.cer_no = m.cer_no "
                + " left join sysmgr_identity_picture n on n.identity_id = m.identity_id "
                + " where m.type = '0' and m.flag not in('-1','3') and t.inv_type in('20','35','36','91') "
                + " and t.gid =? group by t.inv, t.cer_no,m.flag")
        .append(" union ")
        .append(" select t.inv as name,t.b_lic_no as cer_no,'3' as user_type,m.flag from be_wk_investor t "
                + " left join sysmgr_identity m  on  t.b_lic_no = m.cer_no  left join sysmgr_identity_picture n on n.identity_id = m.identity_id "
                + " where  m.type = '0' and m.flag not in('-1','3') and n.picture_id is not null and t.inv_type not in('20','35','36','91') and t.gid = ? group by t.inv, t.b_lic_no,m.flag")
        .append("  ) t order by flag,user_type asc,cer_no asc");

        List<Map<String, Object>> contentList = DaoUtil.getInstance().queryForList(sb.toString(), gid, gid,gid,gid);
        resultMap.put("checkUser", contentList);
        return resultMap;
    }
    // 加载所选用户的审批图片列表
    public List<Map<String, Object>> loadSelectUserPic(String userName, String cerNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from (")
        .append(" select n.picture_id as biz_id,n.type as pic_type,n.file_id,n.thumb_file_id,n.flag as state,m.flag,m.contact_name,m.contact_cerno,m.mobile from sysmgr_identity m ")
        .append(" inner join sysmgr_identity_picture n on m.identity_id = n.identity_id ")
        .append(" where  m.type = '0' and m.flag not in('-1','3') and n.flag not in('-1','3') and m.name=? and m.cer_no=?")
        .append(" ) t order by pic_type asc ");
        List<Map<String, Object>> ret = DaoUtil.getInstance().queryForList(sb.toString(), userName, cerNo);
        return ret;
    }

    /**
     * 
     * @param bizId
     * @param state
     * @param msg
     * @return
     */
    public Map<String,Object> save(String bizId, String state, String msg) {
        Map<String,Object> ret = new HashMap<String,Object>();
        if(StringUtil.isBlank(bizId)){
            ret.put("state", "error");
            ret.put("msg", "保存失败：业务编号（bizId）不能为空。");
            return ret;
        }
        if(StringUtil.isBlank(state)){
            ret.put("state", "error");
            ret.put("msg", "保存失败：业务状态（state）不能为空。");
            return ret;
        }
        // 如果不是通过，则审批意见必须有。
        if(!"1".equals(state)){
            if(StringUtil.isBlank(msg)){
                ret.put("state", "error");
                ret.put("msg", "保存失败：审批意见不能为空。");
                return ret;
            }
        }
        Calendar now = DateUtil.getCurrentTime();
        // 保存单个图片审批记录
        String sql = "update sysmgr_identity_picture t set t.approve_msg = ? , t.timestamp = ? ,t.flag = ? where t.picture_id = ?";
        IPersistenceDAO dao = DaoUtil.getInstance().getDao();
        DaoUtil.getInstance().execute(dao,sql, msg,now,state,bizId);
        
        if("1".equals(state)){//点击通过按钮,处理整体审批进度
            String totalPicSql = "select count(1) from sysmgr_identity_picture p where p.identity_id in "
                    + "(select t.identity_id from sysmgr_identity_picture t where t.picture_id = ?)";
            long totalPicCount =  DaoUtil.getInstance().queryForOneLong(totalPicSql, bizId);
            sql = "select count(1) as cnt from sysmgr_identity_picture p where p.identity_id in " +
                     "  (select t.identity_id from sysmgr_identity_picture t where t.picture_id = ? ) and p.flag = '1'";
            long cnt = DaoUtil.getInstance().queryForOneLong(sql, bizId);
            if(cnt<totalPicCount){
                ret.put("state", "success");
                ret.put("msg", "保存成功，当前人员审核状态："+cnt+"/"+totalPicCount+"，请继续审核。");
                return ret;
            }
        }
        SysmgrUser approveUser = ApproveUserUtil.getLoginUser();
        if(approveUser==null){
            ret.put("state", "error");
            ret.put("msg", "保存失败：登录超时，请重新登录。");
            return ret;
        }
        sql = "update sysmgr_identity i "+
            " set i.reg_org=?,i.approve_user_id=?,i.approve_user_name=?,i.approver_date=?,i.approve_msg=?,i.flag=?,i.timestamp=? "+
            " where i.identity_id in (select t.identity_id from sysmgr_identity_picture t where t.picture_id = ? )";
        String approveMsg = msg;
        if("1".equals(state) && StringUtil.isBlank(approveMsg)){
            approveMsg = "通过。";
        }
        String flag = "";
        if("1".equals(state)){
            flag = "1";
        }else{
            flag = "2";
        }
        DaoUtil.getInstance().execute(sql, approveUser.getOrgCodeFk(),approveUser.getUserId(),approveUser.getUserName(),now,approveMsg,flag,now,bizId);

        if("2".equals(state)){
            ret.put("state", "success");
            //ret.put("msg", "保存成功，当前人员实名认证审核不通过；当前业务应做退回修改处理。");
            ret.put("msg", "保存成功，审核不通过。");
            return ret;
        }else {
            //删除失效的数据
            String qSql = "select t.name,t.cer_type,t.cer_no from sysmgr_identity t where t.identity_id in(select t.identity_id from sysmgr_identity_picture t where t.picture_id=?)";
            Map<String,Object> identityMap =  DaoUtil.getInstance().queryForRow(qSql, bizId);
            String name = (String) identityMap.get("name");
            String cerType = (String) identityMap.get("cerType");
            String cerNo = (String) identityMap.get("cerNo");
            String delIdentityPic = "delete from sysmgr_identity_picture t where t.identity_id in(select t.identity_id from sysmgr_identity t where t.flag='3' and t.name=? and t.cer_type=? and t.cer_no=?)";
            String delIdentity ="delete from sysmgr_identity t where t.identity_id in(select t.identity_id from sysmgr_identity t where t.flag='3' and t.name=? and t.cer_type=? and t.cer_no=?)";
            DaoUtil.getInstance().execute(delIdentityPic, name,cerType,cerNo);
            DaoUtil.getInstance().execute(delIdentity, name,cerType,cerNo);
            ret.put("state", "success");
            //ret.put("msg", "保存成功，当前人员实名认证审核通过，请继续审核当前业务申请。");
            ret.put("msg", "保存成功，审核通过。");
            return ret;
        }
    }

    public List<Map<String, Object>> loadLiveUserPic(String userName,String cerNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from (")
        .append(" select n.picture_id as biz_id,n.type as pic_type,n.file_id,n.thumb_file_id,n.flag as state,m.flag,m.contact_name,m.contact_cerno,m.mobile from sysmgr_identity m ")
        .append(" inner join sysmgr_identity_picture n on m.identity_id = n.identity_id ")
        .append(" where  m.type = '0' and m.flag not in('-1','3') and n.flag not in('-1','3') and m.name=? and m.cer_no=? ")
        .append(" ) t order by pic_type asc ");
        List<Map<String, Object>> ret = DaoUtil.getInstance().queryForList(sb.toString(), userName, cerNo);
        return ret;
    }
}
