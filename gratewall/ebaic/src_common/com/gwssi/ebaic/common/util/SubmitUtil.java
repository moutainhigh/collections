package com.gwssi.ebaic.common.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwssi.ebaic.domain.BeWkReqBO;
import com.gwssi.ebaic.domain.BeWkReqprocessBO;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;
import com.gwssi.rodimus.validate.msg.ValidateMsgEntity;
import com.gwssi.torch.util.UUIDUtil;

/**
 * 业务提交。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class SubmitUtil {
	
	
	
	
	/**
	 * 判断申请人是否拥有所有法人和股东的授权
	 * @param gid
	 */
	public static boolean submitAuthCheck(String gid) {
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("无效的业务标识:"+gid);
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select case when count(*) = count(case  when auth_to_apply_user = '1' then 1 end) ")
		.append(" then '1'　else '0'　end as all_authed from")
        .append(" (select i.auth_to_apply_user from be_wk_investor i where gid =?")  
        .append(" union all select l.auth_to_apply_user from be_wk_le_rep l where gid =? )");
        String allAuthed = DaoUtil.getInstance().queryForOneString(sql.toString(), gid,gid);
        return "1".equals(allAuthed);
	}
	
	/**
	 * 判断当前用户是否为业务的法人或股东
	 * @param gid
	 * @return
	 */	
	public static boolean isInvOrLe (String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("无效的业务标识:"+gid);
		}
		// 当前登录用户
		TPtYhBO user = HttpSessionUtil.getCurrentUser();
		if(user==null){
			throw new RuntimeException("登录超时，请重新登录。");
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from (select i.cer_type, i.cer_no, i.inv as name")
		.append(" from be_wk_investor i where gid =?")
		.append(" union all select l.le_rep_cer_type as cer_type,")
		.append(" l.le_rep_cer_no   as cer_no,l.le_rep_name     as name")
		.append(" from be_wk_le_rep l where gid =?")
		.append(" where cer_type =? and cer_no =? and name =?");
		long n = DaoUtil.getInstance().queryForOneLong(sql.toString(), gid,gid,user.getCerType(),user.getCerNo(),user.getUserName());
		return n>=1;
	}
	
	/**
	 * 判断是否所有法人和股东都已经确认提交或授权
	 * @param gid
	 * @return
	 */
	public static boolean allConfirmedOrAuthed(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("无效的业务标识:"+gid);
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select case when count(*) = count(case  when is_confirm = '1' or auth_to_apply_user='1' then 1 end) ")
		.append(" then '1'　else '0'　end as all_authed from")
        .append(" (select i.is_confirm,i.auth_to_apply_user from be_wk_investor i where gid =?")  
        .append(" union all select l.is_confirm,l.auth_to_apply_user from be_wk_le_rep l where gid =? )");
        String allConfirmed = DaoUtil.getInstance().queryForOneString(sql.toString(), gid,gid);
        return "1".equals(allConfirmed);
	}
	
	/**
	 * 检查身份认证状态。
	 * @param gid
	 */
	public static void checkReqIdentityState(String gid) {
		int identityState = IdentityCertificateUtil.checkReqIdentityState(gid);
		switch(identityState){
			case IdentityCertificateUtil.IDENTITY_STATE_ALL_OK : return;
			case IdentityCertificateUtil.IDENTITY_STATE_PICTURE_OK : return;
			default:throw new RuntimeException("法定代表人均需通过现场身份认证或通过移动客户端上传身份认证图片后才可以提交业务。");
		}
	}
	/**
	 * 判断状态。
	 * 
	 * @param gid
	 * @throws RuntimeException
	 */
	public static void submitStateCheck(String gid) throws RuntimeException {
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("无效的业务标识:"+gid);
		}
		String sql =" SELECT t.state, t.operation_type FROM be_wk_requisition t WHERE t.gid =? ";
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql, gid);
		
		String curState = null ;
		if(list!=null && list.size()>0){
			Map<String, Object> row = list.get(0);
			curState = (String)row.get("state");
		}
		
		// 只有当前状态为1可以提交
		if(!"1".equals(curState)){
			throw new RuntimeException("当前业务已经提交，请勿重复提交。");
		}
	}
	
	/**
	 * 提交给法人和股东确认
	 * @param gid
	 */
	public static void submitToConfirm(String gid){
		//规则
		Map<String,Object> params = ParamUtil.prepareParams(gid);
		params.put("gid", gid);		
		ValidateMsg msg= RuleUtil.getInstance().runApp("ebaic_setup_submit",params);
		List<ValidateMsgEntity> errors =  msg.getAllMsg();
		if(errors!=null && !errors.isEmpty()){
			
			throw new EBaicException(msg.getAllMsgString());
		}
		
		Calendar now = DateUtil.getCurrentTime();
		BeWkReqBO reqBo = DaoUtil.getInstance().get(BeWkReqBO.class, gid);
        if(reqBo==null) {
            reqBo = new BeWkReqBO();
        }
		reqBo.setRequisitionId(gid);
		reqBo.setState("17");// 17 - 待法人确认
		reqBo.setGid(gid);
		reqBo.setAuthType("");//提交方式清空 由法人选择
		reqBo.setSubmitUserId(reqBo.getAppUserId());
		reqBo.setSubmitDate(now);
		reqBo.setTimestamp(now);
		// 版本号加1,FIXME ModifyUtil
		//long verson = reqBo.getVersion()==null? 1 : reqBo.getVersion().longValue();
		//reqBo.setVersion(BigDecimal.valueOf(verson+1));
		
		DaoUtil.getInstance().update(reqBo);
		
		//重置所有的确认状态
        //股东表
        String s ="update be_wk_investor i set i.is_confirm='0' where i.gid=? ";
    	DaoUtil.getInstance().execute(s,gid);
        //法人表
    	s ="update be_wk_le_rep l set l.is_confirm='0' where l.gid=? ";
    	DaoUtil.getInstance().execute(s,gid);
		
	}
	/**
	 * 公司设立提交。
	 * session校验由调用者判断
	 * 取requisition表记录的申请人信息
	 * @param gid
	 */
	public static void cpSetupSubmit(String gid) {
		
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("无效的业务标识:"+gid);
		}
		
		//检查提交业务规则
		Map<String,Object> params = ParamUtil.prepareParams(gid);
		if(params==null || params.isEmpty()){
			throw new RuntimeException("传入的gid参数不正确。");			
		}
		params.put("gid", gid);
		ValidateMsg msg= RuleUtil.getInstance().runApp("ebaic_setup_submit",params);
		String errors =  msg.getLockedMsg();
		if(StringUtil.isNotBlank(errors)){
			throw new RuntimeException(msg.getAllMsgString());
		}
		Calendar now = DateUtil.getCurrentTime();
		BeWkReqBO reqBo = DaoUtil.getInstance().get(BeWkReqBO.class, gid);
		if(reqBo==null) {
		    reqBo = new BeWkReqBO();
		}
		reqBo.setRequisitionId(gid);
		reqBo.setState("2");// 2 - 已提交
		reqBo.setGid(gid);
		reqBo.setSubmitUserId(reqBo.getAppUserId());
		reqBo.setSubmitDate(now);
		reqBo.setTimestamp(now);
		
		
		//清空审查或核准意见信息
		if("12".equals(reqBo.getCurStep())){
			reqBo.setApproveResult("");
			reqBo.setApproveNotion("");
			reqBo.setApproveNo("");
			reqBo.setApproveName("");
			reqBo.setApproveUserId("");
		}else{
			reqBo.setCensorName("");
			reqBo.setCensorNotion("");
			reqBo.setCensorResult("");
			reqBo.setCensorUserId("");
			reqBo.setCensorNo("");
		}
		
		
		
		// 版本号加1
		long verson = reqBo.getVersion()==null? 1 : reqBo.getVersion().longValue();
		reqBo.setVersion(BigDecimal.valueOf(verson+1));
		
		DaoUtil.getInstance().update(reqBo);
	}
	
	/**
	 * 在process表写入记录
	 * 记录包括：
	 * 
	 * @param gid
	 * @param authType
	 */
	public static void processRecord(String gid,String step,String state,String notion,String name) {
		
		if(StringUtils.isBlank(gid)){
			throw new EBaicException("无效的业务标识:"+gid);
		}
		
		String userId ="";
		/*if(OptimusAuthManager.LOGIN_USER_TYPE_PERSON.equals(HttpSessionUtil.getLoginUserType())){
			//网登用户 获取用户信息
			TPtYhBO user =HttpSessionUtil.getCurrentUser();
			userId=user.getUserId();
			name=user.getUserName();
		}*/
		BeWkReqprocessBO bo = new BeWkReqprocessBO();
		bo.setReqprocessId(UUIDUtil.getUUID());		
		bo.setUserName(name);
		bo.setUserId(userId);
		Calendar now = Calendar.getInstance();
		bo.setProEndDate(now);
		bo.setTimestamp(now);
		bo.setProcessDate(now);
		
		bo.setProcessNotion(notion);
		bo.setGid(gid);
		bo.setRequisitionId(gid);
		bo.setState(state);
		bo.setProcessStep(step);
		DaoUtil.getInstance().insert(bo);
	}
	
	/**
	 * 保存法人或股东确认信息，授权信息
	 * @param gid
	 * @param auth
	 * @param result
	 */
	public static void updateAuthAndConfirm(String gid,String auth,String result){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("无效的业务标识:"+gid);
		}
		TPtYhBO userBo = HttpSessionUtil.getCurrentUser();
		if(userBo==null){
			throw new RuntimeException("登录超时，请重新登录。");
		}
		String cerNo = userBo.getCerNo();
		String cerType = userBo.getCerType();
		String userType = userBo.getUserType();
		//排除乱值干扰
		if(!"1".equals(auth)){
			auth="0";
		}
		if(!"1".equals(result)){
			result="0";
		}
		
		String sql="";
		if("100".equals(userType)){
			//TODO
			//非自然人股东确认
		}else{			
			//自然人
			//更新股东表
			sql = "update be_wk_investor i set i.auth_to_apply_user=?,i.is_confirm=? where i.gid=? and i.cer_type=? and i.cer_no=?";
			DaoUtil.getInstance().execute(sql, auth,result,gid,cerType,cerNo);			
		}
		//更新法人表
		sql="update be_wk_le_rep l set l.auth_to_apply_user=?,l.is_confirm=? where l.gid=? and l.le_rep_cer_type=? and l.le_rep_cer_no=?";
		DaoUtil.getInstance().execute(sql, auth,result,gid,cerType,cerNo);
		
	}
	
	
	/**
	 * 目前用于统一更新 be_wk_requisition 表时间戳
	 * @param dbName 表名
	 * @param param 根据什么条件更新
	 * @param id 参数值
	 */
	public static void updateTimeStamp(String dbName,String param,String id){
		
		//sql update be_wk_requisition a set a.timestamp = sysdate where a.gid = id;
		String sql = "update "+dbName+" a set a.TIMESTAMP = sysdate where a."+param+" = '"+id+"'";
		DaoUtil.getInstance().execute(sql);
		return;
	}
}
