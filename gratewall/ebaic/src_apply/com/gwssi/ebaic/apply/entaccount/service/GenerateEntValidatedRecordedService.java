package com.gwssi.ebaic.apply.entaccount.service;

import org.springframework.stereotype.Service;

import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;

//ye
//用户调用工商局的企业认证时，自动利用Torch对企业认证记录表插入一条句句
@Service("generateEntValidatedRecordedService")
public class GenerateEntValidatedRecordedService {

	// 默认验证码过期时间为30分钟

	/** 
	 * <一句话方法简述> 保存企业认证记录<br/>
	 * <功能详细描述> 保存企业认证信息<br/>
	 * 参数:  @param operCode校验码<br/>
	 * 参数:  @param authCode当前用户授权码<br/>
	 * 参数:  @param entName企业名称<br/>
	 * 参数:  @param regNo企业注册号<br/>
	 * 参数:  @param unicCode 认证序号<br/>
	 * 返回值类型: void<br/> 
	 * @see [类、类#方法、类#成员] <br/>
	 */
	public void save(String operCode, String authCode, String entName, String regNo, String unicCode) {
		String sql = "insert into cp_account_auth (auth_id,oper_code,auth_code,ent_name,reg_no,unic_code,validate_code_start_time,validate_code_end_time,time_stamp_code) "
				+ " values (sys_guid(),?,?,?,?,?,sysdate,sysdate+3/144,sysdate)";// sysdate+3/144默认是30分钟验证码的有效时间

		// 后台信息认证功能
		if (StringUtil.isBlank(operCode)) {
			throw new EBaicException("系统繁忙或短信接口调用失败，请稍后再试");
		}

		if (StringUtil.isBlank(authCode)) {
			throw new EBaicException("系统繁忙或短信接口调用失败，请稍后再试");
		}

		if (StringUtil.isBlank(entName)) {
			throw new EBaicException("工商局返回的企业名字不存在，请核对。");
		}

		if (StringUtil.isBlank(regNo)) {
			throw new EBaicException("工商局返回的企业注册信息不存在，请核对。");
		}

		if (StringUtil.isBlank(unicCode)) {
			throw new EBaicException("工商局返回的企业统一信用编码信息不存在，请核对。");
		}

		DaoUtil.getInstance().execute(sql, operCode, authCode, entName, regNo, unicCode);
	}
}
