package com.gwssi.ebaic.mobile.api;

import java.util.Map;

import com.gwssi.rodimus.rpc.ParameterName;

/**
 * 
 * <h2>手机首页登录页面接口</h2>
 * 
 * @author 祥乾 刘
 *
 */
public interface LoginService {
	
	/**
	 * <h3>网登/全程电子化 个人用户登录</h3>
	 * 
	 * 
	 * @param cerType 证件类型[CB16]（默认为1，预留以后扩展使用）
	 * @param cerNo 证件号码  或者是登录名
	 * @param password 登录密码
	 * @return Map:
	 * 		userId : 用户唯一标识
	 * 		userName ： 用户真实姓名
	 * 		userType ： 用户类型（account-普通用户，agent-代理机构）
	 * 		checkState ： 身份证验证状态（0-合法，1-不合法）
	 * 		identityState : 身份认证状态
	 */
	public Map<String, String> personLogin(@ParameterName(value="cerType")String cerType,
										   @ParameterName(value="cerNo")String cerNo,
										   @ParameterName(value="password")String password);
	
	
	/**
	 * <h3>电子营业执照登录</h3>
	 * 
	 * @param entName 企业名称
	 * @param passWord 登录密码
	 * @param mobile（管理员手机号/法定代表人手机号）
	 * @param validateCode（手机验证码）
	 * @return Map:
	 * 		userAuth : 用户权限（逗号分隔的权限id资源，all表示全部）
	 * 		userType ： 用户类型 （法人-leg/管理员-mgr）
	 * 		accountId ： 用户ID
	 * 		managerId : 管理员编号（如果法人登录，则为空）
	 * 		entName ： 企业名称
	 * 
	 */
	public Map<String, String> eEntLogin(@ParameterName(value="entName")String entName,
										 @ParameterName(value="password")String password,
										 @ParameterName(value="mobile")String mobile,
										 @ParameterName(value="mobileValidateCode")String mobileValidateCode);
	
	
	
	/**
	 * <h3>个人     身份认证/业务确认  入口</h3>
	 * 
	 * <ol>
	 *		<li>如果是第一次进入，判断“证件类型+证件号码”是否在identity表存在，如果不存在，创建新记录。</li>
	 * </ol>
	 * @param name 真实姓名
	 * @param cerType 证件类型，传“1”，表示是身份证 
	 * @param cerNo 身份证号码
	 * @param mobile 手机号码
	 * @param mobileVerCode 手机校验码，通过调用ebaicCommonService.sendMobileVerCode接口获得
	 * @param entryType 进入类型 0-身份认证入口；1-业务确认入口
	 * @return Map:
	 * 		identityId  :  认证单据编号。
	 * 		flag      :  认证状态： -1 - 第一次进入，未提交过身份认证信息；0-已经提交过，等待审核；2-审核未通过；1-已经通过身份认证。
	 */
	public Map<String,String> personEntry(@ParameterName(value="name")String name,
			@ParameterName(value="cerType")String cerType,
			@ParameterName(value="cerNo")String cerNo,
			@ParameterName(value="mobile")String mobile,
			@ParameterName(value="mobileVerCode")String mobileVerCode,
			@ParameterName(value="entryType")String entryType);
	
	/**
	 * <h3>单位股东     身份认证/业务确认    入口</h3>
	 * 
	 * <ol>
	 *		<li>如果是第一次进入，判断“注册号/统一社会信用代码+法定代表人证件号码”是否在identity表存在，如果不存在，创建新记录。</li>
	 * </ol>
	 * @param entName 企业名称
	 * @param regNo 统一社会信用代码或注册号
	 * @param mobile 企业联系人移动电话
	 * @param mobileVerCode 移动电电话校验码
	 * @param entryType 进入类型 0-身份认证入口；1-业务确认入口
	 * @return Map:
	 * 		identityId  :  认证单据编号。
	 * 		flag      :  认证状态： -1 - 第一次进入，未提交过身份认证信息；0-已经提交过，等待审核；2-审核未通过；1-已经通过身份认证。
	 */
	public Map<String,String> entEntry(@ParameterName(value="entName")String entName,
			@ParameterName(value="regNo")String regNo, 
			@ParameterName(value="mobile")String mobile,
			@ParameterName(value="mobileVerCode")String mobileVerCode,
			@ParameterName(value="entryType")String entryType);

}
