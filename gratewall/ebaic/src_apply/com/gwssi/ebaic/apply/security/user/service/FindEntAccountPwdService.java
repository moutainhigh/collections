package com.gwssi.ebaic.apply.security.user.service;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.entaccount.domain.EntAccountBo;
import com.gwssi.ebaic.apply.entaccount.domain.EntAccountManagerBo;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.api.Val;

/**
 * 
 * 
 * @author 
 */
@Service("findEntAccountPwdService")
public class FindEntAccountPwdService {
	/**
	 * 根据企业名称 注册号或统一信用码 法定代表人姓名 法定代表人证件号校验企业账号，并返回移动电话
	 * @param entName
	 * @param regNo
	 * @param legName
	 * @param regCerNo
	 * @return
	 */
	public String queryMobile(String entName,String regNo,String legName,String regCerNo){
		if(StringUtil.isBlank(entName)){
			throw new EBaicException("企业名称不能为空。");
		}
		if(StringUtil.isBlank(regNo)){
			throw new EBaicException("注册号或统一社会信用代码不能为空。");
		}
		if(StringUtil.isBlank(legName)){
			throw new EBaicException("法定代表人姓名不能为空。");
		}
		if(StringUtil.isBlank(regCerNo)){
			throw new EBaicException("法定代表人身份证号不能为空。");
		}
		String sql = "select c.* from cp_account c where c.ent_name=? and c.leg_name=? and c.leg_cer_no=? and c.accout_state='0' ";
		List<String> param = new ArrayList<String>();
		param.add(entName);
		param.add(legName);
		param.add(regCerNo);
		List<EntAccountBo> list = DaoUtil.getInstance().queryForListBo(sql, EntAccountBo.class, param);
		if(list.size()<=0){
			throw new EBaicException("企业身份校验数据错误。");
		}
		EntAccountBo entAccount = list.get(0);
		if(!regNo.equals(entAccount.getUniScid())){
			String rsSql = "select r.reg_no  from cp_rs_ent r where r.ent_id=?";
			List<String> rsParam = new ArrayList<String>();
			rsParam.add(entAccount.getRsEntId());
			String dbRegNo = (String) DaoUtil.getInstance().queryForOne(rsSql, rsParam);
			if(dbRegNo==null){
				throw new EBaicException("注册号或统一社会信用代码数据错误。");
			}
			if(!regNo.equals(dbRegNo)){
				throw new EBaicException("注册号或统一社会信用代码数据错误。");
			}
		}
		if(entAccount.getLegMobile()==null){
			throw new EBaicException("该校验企业的法定代表人移动电话不存在。");
		}
		return entAccount.getLegMobile();
	}
	/**
	 * 检查移动电话。
	 * 
	 * @param username
	 * @return
	 */
	public void checkMobile(String loginName,String mobile){
		// 0. 参数校验
		if(StringUtil.isBlank(loginName)){
			throw new EBaicException("登录名不能为空。");
		}
		if(StringUtil.isBlank(mobile)){
			throw new EBaicException("移动电话不能为空。");
		}
		Val.field.cellphone("移动电话", mobile);
		String sql = "select * from cp_account c where c.ent_name=? and c.accout_state='0'";
		List<String> param = new ArrayList<String>();
		param.add(loginName);
		EntAccountBo entAccount = (EntAccountBo) DaoUtil.getInstance().queryForOne(sql, param);
		if(entAccount==null){
			throw new EBaicException("企业账号不存在。");
		}
		boolean f =false;
		if(!mobile.equals(entAccount.getLegMobile())){
			String managerSql = "select * from cp_account_manager m where  m.account_id=? ";
			List<String> managerParam = new ArrayList<String>();
			managerParam.add(entAccount.getAccountId());
			List<EntAccountManagerBo> list = DaoUtil.getInstance().queryForListBo(managerSql, EntAccountManagerBo.class, managerParam);
			if(list.size()<0){
				throw new EBaicException("移动电话与企业账户不一致。");
			}else{
				for(int i=0;i<list.size();i++){
					EntAccountManagerBo entManagerAcc = list.get(i);
					if(mobile.equals(entManagerAcc.getMobile())&&"1".equals(entManagerAcc.getAccState())){//只要管理员账户有一个移动电话相等
						f = f||true;
					}
				}
			}
		}
		if(!f){
			throw new EBaicException("移动电话与企业账户不一致。");
		}
		
	}
	
	/**
	 * 修改企业用户密码
	 * @param loginName
	 * @param mobile
	 * @param verCode
	 * @param pwd
	 */
	public void save(String entAccName, String mobile ,String verCode,String pwd)  {
		// 0. 参数校验
		if(StringUtil.isBlank(entAccName)){
			throw new EBaicException("企业名称不能为空。");
		}
		if(StringUtil.isBlank(mobile)){
			throw new EBaicException("移动电话不能为空。");
		}
		if(StringUtil.isBlank(verCode)){
			throw new EBaicException("验证码不能为空。");
		}
		if(StringUtil.isBlank(pwd)){
			throw new EBaicException("密码不能为空。");
		}
		SmsVerCodeUtil.verify(mobile, verCode);
		
		String sql = "update cp_account set account_pwd=? where ent_name = ? and leg_mobile = ?  and accout_state='0' ";
		String md5Pwd = MD5Util.MD5Encode(pwd);
		DaoUtil.getInstance().execute(sql, md5Pwd, entAccName, mobile);
	}

}
