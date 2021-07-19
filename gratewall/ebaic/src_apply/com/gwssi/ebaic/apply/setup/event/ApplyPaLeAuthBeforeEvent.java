package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.util.UUIDUtil;
import com.gwssi.torch.web.TorchController;
/**
 * 法定代表人授权。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service("applyPaLeAuthBefore")
public class ApplyPaLeAuthBeforeEvent implements OnEventListener{

	/* (non-Javadoc)
	 * @see com.gwssi.ebaic.torch.event.OnEventListener#execEdit(com.gwssi.torch.web.TorchController, java.util.Map, com.gwssi.torch.domain.edit.EditConfigBo, java.lang.Object)
	 */
	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {

		
		System.out.println("edit before");
		
		String gid = StringUtil.safe2String(params.get("gid"));
		if(StringUtil.isBlank(gid)){
			throw new EBaicException("gid不能为空。");
		}
		
		String oprType ="10";//目前默认是设立权限 后面可以扩展为从前台页面选择
		String isLeRepAuth="1";//具有法人授权
		
		
		
		TPtYhBO currentUser = HttpSessionUtil.getCurrentUser();
		if(currentUser==null){
			throw new EBaicException("登录超时，请重新登录。");
		}
		//授权人
		String fromCerType=currentUser.getCerType();
		String fromCerNo=currentUser.getCerNo();
		String fromUserId=currentUser.getUserId();		
		String fromName=currentUser.getUserName();
		
		String sql = "select r.cert_type,r.cert_no,r.linkman,r.app_user_id,r.ent_id from be_wk_requisition r where r.gid = ?";
		Map<String,Object> toUser = DaoUtil.getInstance().queryForRow(sql, gid);
		if(toUser ==null || toUser.isEmpty()){
			throw new EBaicException("没有查到对应的业务数据,请重新登录或联系工作人员。业务标识="+gid);
		}
		//被授权人
		String toName = StringUtil.safe2String(toUser.get("linkman"));
		String toCerType = StringUtil.safe2String(toUser.get("certType"));
		String toCerNo = StringUtil.safe2String(toUser.get("certNo"));
		String toUserId = StringUtil.safe2String(toUser.get("appUserId"));
		
		// 如果授权表没有记录，则新建一条
		sql = "select * from be_wk_auth a where a.gid = ?";
		Map<String, Object> row  = DaoUtil.getInstance().queryForRow(sql, gid);
		if(row==null || row.isEmpty()){
			
			sql = "insert into be_wk_auth(auth_id,from_type,from_cer_type,from_cer_no,from_name,from_user_id,"
					+ "to_name,to_cer_type,to_cer_no,to_user_id,flag,gid,timestamp,is_le_rep_auth,opr_type)"
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			String authId = UUIDUtil.getUUID();
			String fromType = "2";// 法定代表人
			String flag = "1"; // 0-停用，1-启用
			DaoUtil.getInstance().execute(sql, authId , fromType,currentUser.getCerType(),currentUser.getCerNo(),currentUser.getUserName(),currentUser.getUserId(),
					toName,toCerType,toCerNo,toUserId,flag,gid,DateUtil.getCurrentTime(),isLeRepAuth,oprType);
		}
		
		//加入参数列表
		params.put("oprType", oprType);
		params.put("isLeRepAuth", isLeRepAuth);
		params.put("fromCerType", fromCerType);
		params.put("fromCerNo", fromCerNo);
		params.put("fromUserId", fromUserId);
		params.put("fromName", fromName);
		params.put("toName", toName);
		params.put("toCerType", toCerType);
		params.put("toCerNo", toCerNo);
		params.put("toUserId", toUserId);
		//params.put("entName", entName);
		
		
	}
	
	
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {

		System.out.println("query before");
	}

}
