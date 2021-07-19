package com.gwssi.ebaic.apply.security.user.service;


import org.springframework.stereotype.Service;

import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.api.Val;

/**
 * FIXME 生产库loginName不唯一。
 * 
 * @author 
 */
@Service("findPwdService")
public class FindPwdService {
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
		
		// 
		String sql = "select y.mobile from t_pt_yh y where login_name=? and yx_bj='1' and user_type='1' ";
		String mobileInDb = DaoUtil.getInstance().queryForOneString(sql, loginName);
		
		if(StringUtil.isBlank(mobileInDb)){
			throw new EBaicException("登录名不正确，请检查后重新输入。");
		}
		
		if(!mobileInDb.equals(mobile)){
			throw new EBaicException("输入的移动电话与登记信息不一致，请查验后重新输入。");
		}
	}
	
	
	/**
	 * 修改用户密码
	 * @param mm
	 * @param userId
	 * @return
	 */
	public void save(String loginName, String mobile ,String verCode,String pwd)  {
		// 0. 参数校验
		if(StringUtil.isBlank(loginName)){
			throw new EBaicException("登录名不能为空。");
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
		
		String sql = "update t_pt_yh set user_pwd=?,mob_check_state='1' where login_name = ? and mobile = ?  and yx_bj='1' and user_type='1' ";
		String md5Pwd = MD5Util.MD5Encode(pwd);
		DaoUtil.getInstance().execute(sql, md5Pwd, loginName, mobile);
	}

}
