package com.gwssi.ebaic.apply.security.user.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.ebaic.apply.security.user.service.UserSecurityCenterService;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;

@Controller
@RequestMapping("/security/auth/info")
public class UserSecurityCenterController {


	@Autowired
	private UserSecurityCenterService UserSecurityCenterService;
	
	
	/**
	 * 修改用户密码
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/updatePassword")
	@ResponseBody
	public void updatePassword(OptimusRequest request, OptimusResponse response) throws OptimusException{
		
		Map<String, String> map = request.getForm("user_info");
		if(map == null || map.isEmpty()){
			throw new EBaicException("获取表单数据异常,请联系管理员");
		}
		String userPwdOld = map.get("userPwdOld");
		String curMM = map.get("userPwd");
		String cerNo = map.get("cerNo");
		String mobileCode =map.get("mobileCode");
		
		if(StringUtil.isBlank(userPwdOld) || StringUtil.isBlank(curMM) ||
				StringUtil.isBlank(cerNo)){
			throw new EBaicException("获取表单数据异常,请联系管理员");
		}
		
		userPwdOld = MD5Util.MD5Encode(userPwdOld);
		curMM = MD5Util.MD5Encode(curMM);
		
		//数据库中的各个字段值
		TPtYhBO currentUser = HttpSessionUtil.getCurrentUser();
		String userId = currentUser.getUserId();
		String userMM = currentUser.getUserPwd();
		String ucerNo = currentUser.getCerNo();
		String mobile = currentUser.getMobile();
		
		if(this.getMobileValidate().equals("1")){
			if(StringUtil.isBlank(mobileCode) || StringUtils.isBlank(mobile)){
				throw new EBaicException("获取表单数据异常,请联系管理员");
			}
			SmsVerCodeUtil.verify(mobile, mobileCode);
		}
		
		String result = "";
		
		if(!cerNo.equals(ucerNo)){
			throw new EBaicException("证件号码填写不正确！");
		}
		if(userPwdOld.equals(userMM)){
			boolean res = UserSecurityCenterService.updatePassword(curMM ,userId);
			if(res){
				result ="1";//修改密码成功
			}else{
				result ="2";;//修改密码失败
			}
		}else{
			result ="0";;//原始密码不正确
		}
		
		response.addAttr("result", result);
	}
	
	
	/**
	 * 获取用户的手机验证状态,1代表已验证，其他为未验证
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/getMobileValidate")
	@ResponseBody
	public String getMobileValidate(){
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtils.isBlank(userId)){
			throw new EBaicException("用户信息失效，请重新登录！");
		}
		String flag = UserSecurityCenterService.getMobileValidate(userId);
		return flag;
	}

	/**
	 * 获取用户的相关信息
	 * @throws OptimusException 
	 */
	@RequestMapping("/getUserInfo")
	@ResponseBody
	public void getUserInfo(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtils.isBlank(userId)){
			throw new EBaicException("用户信息失效，请重新登录！");
		}
		Map<String ,Object> yhInfo = UserSecurityCenterService.getUserInfo(userId);
		
		response.addForm("user_info", yhInfo);
	}
	
	@RequestMapping("/getUserRegInfo")
	@ResponseBody
	public void getUserRegInfo(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtils.isBlank(userId)){
			throw new EBaicException("用户信息失效，请重新登录！");
		}
		Map<String ,Object> yhInfo = UserSecurityCenterService.getUserRegInfo(userId);
		response.addForm("changeRegInfoSave_Form", yhInfo);
	}
	/**
	 * 发送验证码到当前登录用户的手机上。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/sendVerCode")
	public void sendVerCode(OptimusRequest request,OptimusResponse response){
		UserSecurityCenterService.sendVerCode();
	}
	/**
	 * 校验验证码是否输入正确，正确则返回pass
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/checkMobile")
	public void checkMobile(OptimusRequest request,OptimusResponse response) throws OptimusException{
		TPtYhBO currentUser = HttpSessionUtil.getCurrentUser();
		if(StringUtils.isBlank(currentUser.getUserId())){
			throw new EBaicException("用户信息失效，请重新登录！");
		}
		String mobile = currentUser.getMobile();
		String mobileCode = ParamUtil.get("mobileCode");
		SmsVerCodeUtil.verify(mobile, mobileCode);
		response.addAttr("url", "../../../page/apply/security/changeRegInfo.html");
	}
	
	/**
	 * 移动电话认证
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/validateMobile")
	public void validateMobile(OptimusRequest request,OptimusResponse response) throws OptimusException{
		TPtYhBO user = HttpSessionUtil.getCurrentUser();
		if(user == null){
			throw new EBaicException("用户信息失效，请重新登录！");
		}
		Map<String,String> map = request.getForm("mobile_info");
		String result = UserSecurityCenterService.validateMobile(map,user);
		response.addAttr("result", result);
	}
	
	
	/**
	 * 看填写的身份证号码与库中是否相同，相同则返回 1
	 * @param cerNo
	 * @return
	 */
	@RequestMapping("/valideCerNo")
	@ResponseBody
	public String valideCerNo(String cerNo){
		String userId = HttpSessionUtil.getCurrentUserId();
		if(userId == null){
			throw new EBaicException("用户信息失效，请重新登录！");
		}
		boolean flag = UserSecurityCenterService.validateCerNo(cerNo,userId);
		if(flag){
			return  "1";
		}else{
			return  "0";
		}
		
	}
}
