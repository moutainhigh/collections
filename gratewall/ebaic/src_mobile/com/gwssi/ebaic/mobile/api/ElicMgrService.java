package com.gwssi.ebaic.mobile.api;

import java.util.List;
import java.util.Map;

import com.gwssi.mobile.api.annotation.ParameterName;

/**
 * <h2>电子营业执照企业管理员</h2>
 * 
 * @author liuxiangqian
 *
 */
public interface ElicMgrService {
	/**
	 * <h3>获取电子营业执照账号下的所有管理员列表</h3>
	 * 
	 * @param accountId 企业账户ID
	 * @return List<Map<String, String>> : 
	 * 	  [{managerId ： 管理员ID
	 * 		name	      ： 管理员名称
	 * 		mobile	      ： 管理员手机号
	 * 		role 	      ：管理员角色}]
	 */
	public List<Map<String,String>> getManagerList(@ParameterName(value="accountId")String accountId);
	
	/**
	 * <h3>编辑管理员</h3>
	 * 
	 * @param managerId
	 * @return Map :
	 * 		managerId : 管理员ID
	 * 		name : 管理员名称
	 * 		cerNo :  管理员证件号
	 * 		mobile : 管理员手机号
	 * 		role :	管理员角色（中文汉字）
	 * 		accState :	管理员账户状态（1-启用，2-停用）
	 * 		operation :	管理员权限（逗号分隔字符串）
	 * 
	 */
	public Map<String, String> editManager(@ParameterName(value="managerId")String managerId);
	
	/**
	 * <h3>编辑的管理员保存</h3>
	 * 
	 * @param managerId : 管理员ID
	 * @param mobileVerCode : 手机验证码
	 * @param name : 管理员名称
	 * @param cerNo :  管理员证件号
	 * @param mobile : 管理员手机号
	 * @param role : 管理员角色（中文汉字）
	 * @param accState : 管理员账户状态（1-启用，2-停用）
	 * @param operation : 管理员权限（逗号分隔字符串）
	 */
	public void saveManager(@ParameterName(value="managerId")String managerId,
			@ParameterName(value="mobileVerCode")String mobileVerCode,
			@ParameterName(value="name")String name,
			@ParameterName(value="cerNo")String cerNo,
			@ParameterName(value="mobile")String mobile,
			@ParameterName(value="role")String role,
			@ParameterName(value="accState")String accState,
			@ParameterName(value="operation")String operation);
	
	/**
	 * <h3>新增管理员</h3>
	 * 
	 * @param accountId : 企业账户ID
	 * @param mobileVerCode  : 手机验证码
	 * @param name : 管理员名称
	 * @param cerNo :  管理员证件号
	 * @param mobile : 管理员手机号
	 * @param role : 管理员角色（中文汉字）
	 * @param accState : 管理员账户状态（1-启用，2-停用）
	 * @param operation : 管理员权限（逗号分隔字符串）
	 */
	public void addManager(@ParameterName(value="accountId")String accountId,
			@ParameterName(value="mobileVerCode")String mobileVerCode,
			@ParameterName(value="name")String name,
			@ParameterName(value="cerNo")String cerNo,
			@ParameterName(value="mobile")String mobile,
			@ParameterName(value="role")String role,
			@ParameterName(value="accState")String accState,
			@ParameterName(value="operation")String operation);
	/**
	 * <h3>删除管理员</h3>
	 * 
	 * @param managerId
	 */
	public void deleteManager(@ParameterName(value="managerId")String managerId);
	
}
