package com.gwssi.ebaic.apply.entaccount.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.entaccount.domain.EntAccountBo;
import com.gwssi.ebaic.apply.entaccount.domain.EntAccountManagerBo;
import com.gwssi.ebaic.apply.util.SessionConst;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.api.Val;

@Service("entAccountService")
public class EntAccountService {
	
	/**
	 * 检查移动电话。
	 * @param entName
	 * @param mobile
	 * @param entPwd
	 * @return
	 */
	public void checkMobile(String entName,String mobile,String entPwd){
		// 0. 参数校验
		if(StringUtil.isBlank(entName)){
			throw new EBaicException("登录名不能为空。");
		}
		if(StringUtil.isBlank(mobile)){
			throw new EBaicException("移动电话不能为空。");
		}
		Val.field.cellphone("移动电话", mobile);
		//
		EntAccountBo account=this.getEntAccount(entName, entPwd);
		//如果输入的是法人移动电话
		if (account.getLegMobile()!=null&&account.getLegMobile().equals(mobile)) {
			
		}else {
			EntAccountManagerBo managerAcc = this.getEntAccountManager(mobile,account.getAccountId());
			//判断是否为管理员移动电话
			if (mobile.equals(managerAcc.getMobile())) {
				
			}else {
				throw new EBaicException("输入的移动电话无效");
			}
		}
	}
	/**
	 * 企业账户登录。
	 * @param entName
	 * @param entPwd
	 * @param vercode
	 */
	public void save(String entName,String entPwd,String vercode,String mobile){
		if(StringUtil.isBlank(entName)){
			throw new EBaicException("登录名不能为空。");
		}
		if(StringUtil.isBlank(entPwd)){
			throw new EBaicException("密码不能为空。");
		}
		if(StringUtil.isBlank(mobile)){
			throw new EBaicException("移动电话不能为空。");
		}
		if(StringUtil.isBlank(vercode)){
			throw new EBaicException("验证码不能为空。");
		}
		/**1、验证码校验**/
		SmsVerCodeUtil.verify(mobile, vercode);
		
		EntAccountBo account= this.getEntAccount(entName, entPwd);
		
		//保存session
		HttpSession session = HttpSessionUtil.getSession();
		//如果输入的是法人移动电话
		session.setAttribute("entAccount", account);
		if (mobile.equals(account.getLegMobile())) {
			session.setAttribute("isEntAccount", "1");
		}else {
			EntAccountManagerBo managerAcc = this.getEntAccountManager(mobile,account.getAccountId());
			//判断是否为管理员移动电话
			if (mobile.equals(managerAcc.getMobile())) {
				session.setAttribute("managerAcc", managerAcc);
				session.setAttribute("isEntAccount", "0");
			}else {
				throw new EBaicException("输入的移动电话无效");
			}
		}
	}
	/**
	 * 获取企业账户登录信息
	 * @param entName
	 * @param entPwd
	 * @return
	 */
	public EntAccountBo getEntAccount(String entName, String entPwd) {
		String sql = "select * from cp_account c where c.ent_name=? ";
		List<String> param = new ArrayList<String>();
		param.add(entName);
//		param.add(MD5Util.MD5Encode(entPwd));
		List<EntAccountBo> list = DaoUtil.getInstance().queryForListBo(sql, EntAccountBo.class, param);
		if(list.isEmpty()){
			throw new EBaicException("企业账号不存在。");
		}
		entPwd = MD5Util.MD5Encode(entPwd);
		
		if(!entPwd.equals(list.get(0).getAccountPwd())){
			throw new EBaicException("密码不正确。");
		}
		if(!"0".equals(list.get(0).getAccoutState())){
			throw new EBaicException("企业账号已停用。");
		}
		return list.isEmpty() ? null : list.get(0);
	}
	/**
	 * 获取企业管理员账户登录信息
	 * @param mobile
	 * @param accountId
	 * @return
	 */
	public EntAccountManagerBo getEntAccountManager(String mobile,String accountId) {
		String sql = "select * from cp_account_manager m where m.mobile=? and  m.account_id=? ";
		List<String> params = new ArrayList<String>();
		params.add(mobile);
		params.add(accountId);
		
		List<EntAccountManagerBo> list = DaoUtil.getInstance().queryForListBo(sql, EntAccountManagerBo.class, params);
		if(list.isEmpty()){
			throw new EBaicException("输入的账号或移动电话错误。");
		}
		if(!"1".equals(list.get(0).getAccState())){
			throw new EBaicException("企业管理员账号已停用。");
		}
		return list.isEmpty() ? null : list.get(0);
	}
	/**
	 * 批量删除企业管理员用户
	 * @param managerIds
	 */
	public void deleteAccountManager(String managerIds){
		System.out.println(managerIds);
		if(StringUtil.isBlank(managerIds)){
			throw new EBaicException("请选中数据后删除。");
		}
		String[] managerId = managerIds.split(",");
		if(managerId.length==0){
			throw new EBaicException("请选中数据后删除。");
		}
		String sql = "delete from cp_account_manager m where m.manager_id = ?";
		List<List<Object>> sqlParamsList = new ArrayList<List<Object>>();
		for(int i=0;i<managerId.length;i++){
			List<Object> param = new ArrayList<Object>();
			param.add(managerId[i]);
			sqlParamsList.add(param);
		}
		
		DaoUtil.getInstance().executeBatch(sql, sqlParamsList);
	}
	/**
	 * 切换企业管理员用户状态
	 * @param state
	 */
	public void updateAccountManagerState(String state,String managerId){
		if(StringUtil.isBlank(state)){
			throw new EBaicException("企业管理员用户状态数据错误。");
		}
		String sql = "update cp_account_manager m set m.acc_state = ? where m.manager_id=?";
		List<Object> param = new ArrayList<Object>();
		param.add(state);
		param.add(managerId);
		
		DaoUtil.getInstance().execute(sql, param);
	}
	/**
	 * 查询管理员用户操作权限
	 * @param managerId
	 */
	public String queryOpr(String managerId){
		if(StringUtil.isBlank(managerId)){
			throw new EBaicException("企业管理员用户数据错误。");
		}
		String sql = "select operation from cp_account_manager where manager_id  = ?";
		return (String) DaoUtil.getInstance().queryForOne(sql, managerId);
		
	}
	/**
	 * 根据企业id校验对应企业补充信息是否存在
	 * @param entId
	 * @return
	 */
	public boolean checkEntSnd(String entId){
		if(StringUtil.isBlank(entId)){
			throw new RuntimeException("企业id不能为空。");
		}
		String sql = " select count(1) from cp_rs_entsnd where ent_id = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(entId);
		BigDecimal count =  (BigDecimal) DaoUtil.getInstance().queryForOne(sql, param);
		if(count.intValue()==0){
			return false;
		}
		return true;
	}
	/**
	 * 根据企业id校验对应企业联系人信息是否存在
	 * @param entId
	 * @return
	 */
	public boolean checkEntContact(String entId){
		if(StringUtil.isBlank(entId)){
			throw new RuntimeException("企业id不能为空。");
		}
		String sql = " select count(1) from cp_rs_entcontact where ent_id = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(entId);
		BigDecimal count =  (BigDecimal) DaoUtil.getInstance().queryForOne(sql, param);
		if(count.intValue()==0){
			return false;
		}
		return true;
	}
	/**
	 * 校验移动电话是否被当前用户或管理员用户使用
	 * @param mobile
	 * @return true 被使用；false 没被使用
	 */
	public boolean checkMobileIsUsed(String mobile,String managerId){
		if(StringUtil.isBlank(mobile)){
			throw new RuntimeException("移动电话不能为空。");
		}
		HttpSession session = HttpSessionUtil.getSession();
		EntAccountBo entAccount = (EntAccountBo)session.getAttribute(SessionConst.ENT_ACCOUNT);
		if(entAccount==null){
			throw new RuntimeException("获取当前账户信息失败。");
		}
		String accountId = entAccount.getAccountId();
		String entName = entAccount.getEntName();
		//1.先在企业账户里校验移动电话是否被当前账户使用
		String sql = "select count(1) from CP_ACCOUNT where accout_state='0' and ent_name=? and leg_mobile=?";
		List<Object> params = new ArrayList<Object>();
		params.add(entName);
		params.add(mobile);
		BigDecimal count =  (BigDecimal) DaoUtil.getInstance().queryForOne(sql, params);
		if(count.intValue()>0){
			return true;
		}else{
			//2.管理员账户里校验移动电话是否被当前账户的管理员账户使用
			String managerSql = "";
			List<Object> managerParams = new ArrayList<Object>();
			if("add".equals(managerId)){//添加时校验
				managerSql = "select count(1) from cp_account_manager where account_id=? and mobile=?";
				managerParams.add(accountId);
				managerParams.add(mobile);
			}else{//编辑时校验
				managerSql = "select count(1) from cp_account_manager where account_id=? and mobile=? and manager_id!=?";
				managerParams.add(accountId);
				managerParams.add(mobile);
				managerParams.add(managerId);
			}
			BigDecimal managerCount =  (BigDecimal) DaoUtil.getInstance().queryForOne(managerSql, managerParams);
			if(managerCount.intValue()>0){
				return true;
			}
		}
		return false;
	}
	//通过entId查询企业信息
    public Map<String, Object> queryEntInfoByEntId(String gid) {
    	if(gid==null){
			throw new RuntimeException("获取当前企业信息失败。");
		}
        String sql = "select t.uni_scid,t.ent_name,t.ent_type,t.op_loc,r.le_rep_name,t.reg_cap,to_char(t.est_date,'yyyy\"年\"mm\"月\"dd\"日\"') as est_date,to_char(t.op_from,'yyyy\"年\"mm\"月\"dd\"日\"') as op_from,to_char(t.op_to,'yyyy\"年\"mm\"月\"dd\"日\"') as op_to,t.business_scope, m.wb from be_wk_ent t, be_wk_le_rep r, t_pt_dmsjb m where t.gid = r.gid and t.ent_type = m.dm and m.dmb_id = 'CA16' and t.gid=?";
        Map<String, Object> resultMap = DaoUtil.getInstance().queryForRow(sql, gid);
        Map<String, Object> appDateInfoMap = DaoUtil.getInstance().queryForRow("select to_char(t.app_date,'yyyy') as app_date_year,to_char(t.app_date,'mm') as app_date_month,to_char(t.app_date,'dd') as app_date_day from be_wk_certificate t where t.ori_cop_sign='1' and t.gid=?", gid);
        if(appDateInfoMap!=null&&appDateInfoMap.size()>0){
        	resultMap.putAll(appDateInfoMap);
        	return resultMap;
        }else{
        	throw new RuntimeException("当前获取执照失败");
        }
    }
    
    /**
     * 获取当天的企业认证记录信息
     * @return
     */
    public List<Map<String, Object>> queryEntValidateRecord(){
    	HttpSession session = HttpSessionUtil.getSession();
		EntAccountBo entAccount = (EntAccountBo)session.getAttribute(SessionConst.ENT_ACCOUNT);
		if(entAccount==null){
			throw new RuntimeException("获取当前账户信息失败。");
		}
		String entName = entAccount.getEntName();
		String  sql = "select oper_code,auth_code,to_date(to_char(validate_code_start_time,'YYYY/MM/dd hh24:mi:ss') ,'YYYY/MM/dd hh24:mi:ss') validate_code_start_time,to_date(to_char(validate_code_end_time,'YYYY/MM/dd hh24:mi:ss'),'YYYY/MM/dd hh24:mi:ss') validate_code_end_time "+
				" from cp_account_auth  where  ent_name=? and validate_code_end_time >= sysdate and validate_code_start_time <sysdate order by validate_code_start_time";
    	return DaoUtil.getInstance().queryForList(sql, entName);
    }
}
