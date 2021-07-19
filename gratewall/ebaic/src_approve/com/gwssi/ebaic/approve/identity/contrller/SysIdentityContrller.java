package com.gwssi.ebaic.approve.identity.contrller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.ebaic.approve.identity.service.SysIdentityService;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.JSON;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.indentity.domain.IdentityCardBO;
import com.gwssi.rodimus.util.ParamUtil;

/**
 * 现场身份认证。
 * 
 * @author XUPENG
 */
@Controller
@RequestMapping("/approve/identity")
public class SysIdentityContrller {
	
	@Autowired
	private SysIdentityService sysIdentityService;
	
	
	/**
	 * 调用公安部接口，校验姓名和身份证号码。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/checkIdentityCard")
	public void checkIdentityCard(OptimusRequest request,OptimusResponse response) throws OptimusException{
		
		String name = ParamUtil.get("name");
		String cerNo = ParamUtil.get("cerNo");
		//调用dao层次进行查询。
		IdentityCardBO ret = sysIdentityService.checkIdentityCard(name, cerNo);
		response.addAttr("result",ret);
	}
	
	
	/**
	 * 如果选择证件类型不是身份证，是其他，保存当前证件信息
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/saveCer")
	public void saveCer(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String name = ParamUtil.get("name");
		String cerNo = ParamUtil.get("cerNo");
		String cerType = ParamUtil.get("cerType");
		sysIdentityService.saveCer(name, cerNo, cerType);
		response.addAttr("result", "success");
	}
	
	
	/**
	 * 通过姓名、证件类型、证件号码比对，判断是否已经通过身份认证
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/checkIsIdentity")
	public void checkIsIdentity(OptimusRequest request,OptimusResponse response) throws EBaicException{
		String name = ParamUtil.get("name");
		String cerNo = ParamUtil.get("cerNo");
		String cerType = ParamUtil.get("cerType");
		boolean flag = sysIdentityService.checkIsIdentity(name, cerNo, cerType);
		response.addResponseBody(JSON.toJSON(flag));
	}
	
	
	/**
	 * 根据证件号，移动电话， 校验移动电话是否已经被当前证件号使用
	 * 如果移动电话已经存在并且当前证件号使用，或者移动电话码不存在,返回true
	 * 否则，返回false
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/checkMobile")
	@ResponseBody
	public boolean checkMobile(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String cerNo = ParamUtil.get("cerNo");
		//String cerType = ParamUtil.get("cerType");
		String mobile = ParamUtil.get("mobile");
		boolean mobileIsExist = sysIdentityService.checkMobileIsExist(cerNo, mobile);
		return mobileIsExist;
	}
	
	
	/**
	 * 校验手机验证码。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/saveMobile")
	public void saveMobile(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String cerNo = ParamUtil.get("cerNo");
		String cerType = ParamUtil.get("cerType");
		String mobile = ParamUtil.get("mobile");
		String verCode = ParamUtil.get("verCode");
		sysIdentityService.saveIdentityMobile(cerNo,cerType, mobile, verCode);
		response.addAttr("result", "success");
	}
	
	/**
	 * 认证通过（或不通过），保存相关信息。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/approve")
	public void approve(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String cerNo = ParamUtil.get("cerNo");
		String cerType = ParamUtil.get("cerType");
		String flag = ParamUtil.get("flag");
		String approveMsg = ParamUtil.get("approveMsg",false);
		sysIdentityService.approveIdentity(cerNo,cerType,flag,approveMsg);
		response.addAttr("result", "success");
	}
	
	
	/**
	 * 根据cerNo查询对应的网上登记用户
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("checkApplyUserExisit")
	public void checkApplyUserExisit(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String cerNo = ParamUtil.get("cerNo");
		String cerType = ParamUtil.get("cerType");
		TPtYhBO yhbo = sysIdentityService.checkApplyUserExisit(cerNo,cerType);
		response.addAttr("result", yhbo);
		
	}
	
	/**
	 * 账号绑定
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("bingding")
	public void bingding(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String cerNo = ParamUtil.get("cerNo");
		String cerType = ParamUtil.get("cerType");
		String curName = ParamUtil.get("curName");
		sysIdentityService.bingding(cerNo,cerType, curName);
		response.addAttr("result", "success");
	}
	
	/**
	 * 现场身份认证-判断输入的登录名是否已经存在
	 *  1-代表登录名已存在 0-代表登录名不存在，可以使用
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/checkLoginName")
	public void checkLoginName(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String loginName = ParamUtil.get("loginName");
		List<TPtYhBO> list = sysIdentityService.getTptYhboByloginName(loginName);
		if(list!=null && !(list.isEmpty())){
			response.addResponseBody("1");
		}else{
			response.addResponseBody("0");
		}
	}
	
	
	/**
	 * 生成账号并绑定
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("createAndBinding")
	public void createAndBinding(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String cerNo = ParamUtil.get("cerNo");
		String cerType = ParamUtil.get("cerType");
		TPtYhBO yhBO = request.getForm("localRegformpanel", TPtYhBO.class);
		sysIdentityService.createAndBinding(cerNo,cerType,yhBO);
		response.addAttr("result", "success");
	}
	
	/**
	 * 重置密码
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("resetPassword")
	public void resetPassword(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String cerNo = ParamUtil.get("cerNo");
		String cerType = ParamUtil.get("cerType");
		String newPassword = sysIdentityService.resetPassword(cerNo,cerType);
		response.addAttr("newPassword", newPassword);
	}
	
}
