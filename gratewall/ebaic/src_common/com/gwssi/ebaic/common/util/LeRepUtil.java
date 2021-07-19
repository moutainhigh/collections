package com.gwssi.ebaic.common.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 法定代表人工具类。
 * 
 * @author lxb
 */
public class LeRepUtil {

	private static String TO_LE_REP_SUBMIT;

	public static String getToLeRepSubmtState(){
		if(TO_LE_REP_SUBMIT==null){
			TO_LE_REP_SUBMIT = ConfigUtil.get("state.toLeRepSubmit");
		}
		return TO_LE_REP_SUBMIT ;
	}
	
	/**
	 * 检查业务状态
	 * @param gid
	 */
	public static void busiState(String gid){
		if(StringUtils.isBlank(gid)){
			throw new EBaicException("无效的业务标识:"+gid);
		}
		String sql = "select t.state from be_wk_requisition t where t.gid = ?";
		Map<String,Object> row = DaoUtil.getInstance().queryForRow(sql, gid);
		if(row==null || row.isEmpty()){
			throw new EBaicException("未查询到业务数据，请检查批次号是否正确或联系系统管理员。") ;
		}
		String state = StringUtil.safe2String(row.get("state"));
		if("2".equals(state)){
			throw new EBaicException("业务已提交，请不要重复提交");
		}
		if(!getToLeRepSubmtState().equals(state)){
			throw new EBaicException("业务数据状态有误，请联系工作人员。状态值："+state);
		}
	}
	/**
	 * 判断当前登录用户是否为业务法人
	 * @param gid
	 * @return
	 */
	public static boolean isLeRep(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("无效的业务标识:"+gid);
		}
		// 当前登录用户
		TPtYhBO user = HttpSessionUtil.getCurrentUser();
		if(user==null){
			throw new EBaicException("登录超时，请重新登录。");
		}
		String curUserCerType = user.getCerType();
		String curUserCerNo = user.getCerNo();
		
		String sql = "select le.le_rep_cer_type, le.le_rep_cer_no,t.state from be_wk_requisition t, Be_Wk_Le_Rep le where t.gid = le.gid and t.gid = ?";
		Map<String,Object> row = DaoUtil.getInstance().queryForRow(sql, gid);
		if(row==null || row.isEmpty()){
			throw new EBaicException("未查询到业务数据，请检查批次号是否正确或联系系统管理员。") ;
		}

		
		String leRepCerType = StringUtil.safe2String(row.get("leRepCerType"));
		String leRepCerNo = StringUtil.safe2String(row.get("leRepCerNo"));
		if(curUserCerType.equals(leRepCerType) && curUserCerNo.equals(leRepCerNo)){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 查询业务法人帐号是否已经认证。
	 * 
	 * @param gid
	 * @return
	 */
	public static boolean hasLeRef(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("无效的业务标识:"+gid);
		}
		// 查询法定代表人信息
		String sql = "select le.le_rep_cer_type,le.le_rep_cer_no from Be_Wk_Le_Rep le where le.flag='1' and le.gid = ?";
		Map<String,Object> leRow = DaoUtil.getInstance().queryForRow(sql, gid);
		if(leRow==null || leRow.isEmpty()){
			throw new EBaicException("尚未设定法定代表人，请返回“主要人员”页面设定。");
		}
		String leRepCerType = StringUtil.safe2String(leRow.get("leRepCerType"));
		String leRepCerNo = StringUtil.safe2String(leRow.get("leRepCerNo"));
	
		// FIXME 这个不应该写死在当前方法中，应该根据业务情况单独调用
		// 法定代表人是否有网上登记账号
//		boolean hasApplyAccount = ApplyUserUtil.hasApplyAccount(leRepCerType, leRepCerNo);
//		if(!hasApplyAccount){
//			throw new EBaicException("法定代表人（或拟任法定代表人）尚未在系统注册帐号。") ;
//		}
		
		// 法定代表人是否通过了身份认证
		boolean ret = IdentityCertificateUtil.isCertificated(leRepCerType, leRepCerNo);
		//boolean ret = "1".equals(flag);
		return ret;
	}
	
	/**
	 * 检查当前登陆用户是否具有法人授权
	 * @param gid
	 * @return
	 */
	
	public static boolean hasAuth(String gid){
		if(StringUtils.isBlank(gid)){
			throw new EBaicException("无效的业务标识:"+gid);
		}
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtils.isBlank(userId)){
			throw new EBaicException("登录超时，请重新登录。");
		}
		//授权未过期、授权给当前用户、操作类型在授权范围内
		StringBuffer sql= new StringBuffer();
		sql.append("select r.operation_type from be_wk_requisition r where r.gid=? and r.app_user_id=?");
		String oprType = DaoUtil.getInstance().queryForOneString(sql.toString(),gid,userId );
		if(StringUtils.isBlank(oprType)){
			throw new EBaicException("您不是当前业务的申请人，不能执行提交操作");
		}
		sql.setLength(0);
		sql.append("select count(*) from be_wk_auth a ")
			.append(" where a.gid = ? and a.is_le_rep_auth='1'") 
			.append(" and a.end_date >= sysdate and a.to_user_id=?")
			.append(" and instr(a.opr_type,?)>0 and flag = '1'");
		long n = DaoUtil.getInstance().queryForOneLong(sql.toString(), gid,userId,oprType);
		
		if(n==1){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取申请人选择的提交方式
	 * 1-申请法人授权
	 * 2-提交法人确认
	 * 空-尚未选择
	 * @return
	 */
	public static String getAuthType(String gid){
		if(StringUtils.isBlank(gid)){
			throw new EBaicException("无效的业务标识:"+gid);
		}
		String sql = "select auth_type from be_wk_requisition where gid=?";
		
		return DaoUtil.getInstance().queryForOneString(sql, gid);
	}
	/**
	 * 查询业务的法定代表人移动电话码
	 * @param gid
	 * @return
	 */
	public static String getLeRepMObile(String gid){
		if(StringUtils.isBlank(gid)){
			throw new EBaicException("无效的业务标识:"+gid);
		}
		String sql = "select l.le_rep_mob from be_wk_le_rep l where l.gid=?";
		
		return DaoUtil.getInstance().queryForOneString(sql, gid);
	}
	
}
