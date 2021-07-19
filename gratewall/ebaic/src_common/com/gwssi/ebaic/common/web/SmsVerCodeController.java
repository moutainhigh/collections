package com.gwssi.ebaic.common.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.security.user.service.UserSecurityCenterService;
import com.gwssi.ebaic.common.util.LeRepUtil;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.validate.api.Val;

/**
 *  <h2>发送短信验证码</h2>
 *  通过 /common/sms/vercode/send.do?mobile=18600107299 发送短信验证码后。<br />
 *  通过 SmsVerCodeUtil.verify(mobile, verCode); 验证验证码是否正确。
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller
@RequestMapping("/common/sms/vercode")
public class SmsVerCodeController {
	
	UserSecurityCenterService service = new UserSecurityCenterService();
	
	/**
	 * 向指定的移动电话码发送短信验证码
	 * 用于新移动电话的校验
	 * 已通过手机认证的使用sendToUser
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/send")
	public void send(OptimusRequest request, OptimusResponse response) throws OptimusException{
		String mobile = ParamUtil.get("mobile");
		SmsVerCodeUtil.send(mobile);
		response.addAttr("result", "success");
	}
	
	/**
	 * 向当前用户认证的移动电话发送短信验证码
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/sendToUser")
	public void sendToUser(OptimusRequest request, OptimusResponse response) throws OptimusException{
		TPtYhBO user = HttpSessionUtil.getCurrentUser();
		if(user==null){
			throw new OptimusException("登录超时，请重新登陆");
		}
		//String mobile = user.getMobile();
		String userId = user.getUserId();
		String mobile = service.getAppovedUserMobile(userId);
		if(StringUtils.isBlank(mobile)){
			throw new OptimusException("您尚未填写帐号认证移动电话，请通过安全中心页面完成手机认证");
		}
		
		if(!"1".equals(user.getMobCheckState())){
			throw new OptimusException("您的移动电话尚未经过认证，请通过安全中心页面完成手机认证");
		}
		mobile = mobile.trim();
		Val.field.cellphone("移动电话", mobile);
		if(!Val.getMsg().isEmpty()){
			throw new OptimusException("请输入规范的移动电话码");
		}
		SmsVerCodeUtil.send(mobile);
		response.addAttr("result", "success");
	}
	
	/**
	 * 向当前业务法定代表人移动电话发送短信验证码
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/sendToLeRep")
	public void sendToLeRep(OptimusRequest request, OptimusResponse response) throws OptimusException{
		TPtYhBO user = HttpSessionUtil.getCurrentUser();
		if(user==null){
			throw new OptimusException("登录超时，请重新登陆");
		}
		String gid =(String)request.getAttr("gid");
		String mobile = LeRepUtil.getLeRepMObile(gid);
		
		Val.field.cellphone("移动电话", mobile);
		if(!Val.getMsg().isEmpty()){
			throw new OptimusException("请输入规范的移动电话码");
		}
		SmsVerCodeUtil.send(mobile);
		response.addAttr("result", "success");
	}
	
	
}
