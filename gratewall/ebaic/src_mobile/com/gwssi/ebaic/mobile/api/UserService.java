package com.gwssi.ebaic.mobile.api;

import java.util.Map;

import com.gwssi.ebaic.mobile.domain.UserBo;
import com.gwssi.rodimus.rpc.ParameterName;

/**
 * <h2>用户服务</h2>
 * 
 * @author liuxiangqian
 */
public interface UserService {

	/**
	 * <h3>registered user</h3>
	 * 
	 * <p>用于注册新用户。</p>
	 * 
	 * <p>只用于普通用户注册，不能用于注册代理机构用户。</p>
	 * 
	 * <p>userName需填写用户真实姓名，必须同身份证上载写的保持一致，而且注册成功后不允许修改。</p>
	 * 
	 * <p>需校验同一个身份证号码在库中只能存在一个，如果已经存在了当前提交的身份证号码，则不予注册。</p>
	 * 
	 * <p>
	 * 数据库操作：
	 * <ul>
	 * 	<ol>保存到数据库 t_pt_yh 表；</ol>
	 * 	<ol>userId采用新生成的UUID；</ol>
	 * 	<ol>userPwd经md5处理后再保存；</ol>
	 * 	<ol>zyJsId字段保存default_role；</ol>
	 * 	<ol>countryCity字段保存156；</ol>
	 * 	<ol>cerType字段保存1；</ol>
	 * 	<ol>idCard字段保存cerNo的值；</ol>
	 *  <ol>userType字段保存1；</ol>
	 *  <ol>facilityId字段保存空；</ol>
	 *  <ol>createTime字段保存当前时间，精确到微秒；</ol>
	 *  <ol>yxBj字段保存1；</ol>
	 *  <ol>checkState字段保存1。</ol>
	 * </ul>
	 * 
	 * </p>
	 * 
	 * @param user 参数属性中userId不需要传，由服务器端生成。
	 * 
	 * @return 新注册成功的用户编号（userId）。
	 * 
	 */
	public String regist(UserBo user);
	
	/**
	 * <p>query whether there is same name.</p>
	 * 
	 * <p>检查登录名是否已经存在，检查时忽略大小写。</p>
	 * 
	 * @param loginName
	 * 
	 * @return boolean 校验通过返回true，表示库中没有相同的登录名；反之返回false。
	 * 
	 */
	public boolean checkLoginName(@ParameterName(value="loginName")String loginName) ;
	
	/**
	 * <h3>user login</h3>
	 * 
	 * <p>检查用户名、密码是否合法。</p>
	 * 
	 * <p>密码将进行md5运算后再和数据库中的记录比对。</p>
	 * 
	 * <p>如果p_pt_yh表 check_state值为1，则执行身份证校验。如果校验失败，用户可以通过 reCerNo接口再次提交身份证号码，提交成功后，重新登录。如果校验成功，将check_state字段的值改为0。</p>
	 * 
	 * <p>目前的实现并不在服务器端执行创建session、token等操作，不在服务器端记录登录信息。并不排除在以后的版本中提供类似功能。</p>
	 * 
	 * @param loginName 登录名
	 * @param passWord 密码
	 * 
	 * @return 返回 Map<String,Object> 格式的数据集合，包含userId（用户编号，唯一标识）、userName（用户真实姓名）
	 * 
	 * @throws RodimusException 
	 */
	public Map<String,Object> login(@ParameterName(value="loginName")String loginName,@ParameterName(value="passWord")String passWord);

	
	
	/**
	 * 
	 * 重新提交身份证号码，更新到t_pt_yh表中，需要同时更新 idcard和cerNo两个字段。
	 * 
	 * 需校验提交的身份证号码在库中不曾出现过。
	 * 
	 * 如果checkState为0，则报错：身份证核查已经通过，无需提交。
	 * 
	 * @param userId 
	 * @param newCerNo
	 * 
	 * @throws RodimusException
	 */
	public void reCerNo(@ParameterName(value="userId")String userId, @ParameterName(value="newCerNo")String newCerNo) ;
	
	/**
	 * <h3>user logout</h3>
	 * 
	 * <p>目前的实现中，不执行任何操作。</p>
	 * 
	 * @param userId
	 * @throws RodimusException 
	 */
	public void logout(@ParameterName(value="userId")String userId);
	
	/**
	 * 
	 * get 4 bit verification code
	 * 
	 * @return verifyCode
	 * @throws RodimusException 
	 */
	@Deprecated
	public String getVerifyCode();
	
}
