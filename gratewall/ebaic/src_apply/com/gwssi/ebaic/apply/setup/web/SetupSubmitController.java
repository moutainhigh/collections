package com.gwssi.ebaic.apply.setup.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.req.service.ReqService;
import com.gwssi.ebaic.apply.setup.service.SetupSubmitService;
import com.gwssi.ebaic.domain.SysmgrIdentityBO;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.ParamUtil;

/**
 * 设立提交。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller
@RequestMapping("/apply/setup/submit")
public class SetupSubmitController {
	
	@Autowired
	SetupSubmitService setupSubmitService; 
	@Autowired
	ReqService reqService;
	
	@RequestMapping("/beforeSubmit")
	public void beforeSubmit(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		/*String isAuth = ParamUtil.get("isAuth");
		String result = ParamUtil.get("result");
		*/
		String status = setupSubmitService.beforeSubmit(gid);
		response.addAttr("status", status);	
	}
	/**
	 * 设立提交
	 * 触发条件：	1、申请人提交时已经获取了所有股东和法人的授权
	 * 			2、最后一个股东或法人完成了确认
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/cpSubmit")
	public void cpSubmit(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		//String mobileCode = ParamUtil.get("mobileCode");
		
		String msg =setupSubmitService.cpSubmit(gid,"");
		response.addAttr("msg", msg);		
	}
	
	
	
	/**
	 * 法人提交
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/confirmSubmit")
	public void confirmSubmit(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String userType = HttpSessionUtil.getLoginUserType();
		if(StringUtils.isBlank(userType)){
			throw new EBaicException("登录超时，请重新登录");
		}
		String gid = ParamUtil.get("gid");
		String withAuth = ParamUtil.get("withAuth");
		String msg="0";
		if(OptimusAuthManager.LOGIN_USER_TYPE_ENT.equals(userType)){
			SysmgrIdentityBO ent = HttpSessionUtil.getEntUser();
			if(ent==null){
				throw new EBaicException("登录超时，请重新登录");
			}
			msg = reqService.cpConfirm(ent.getCerType(), ent.getCerNo(), gid, withAuth);
			
		}else if (OptimusAuthManager.LOGIN_USER_TYPE_PERSON.equals(userType)){
			TPtYhBO user = HttpSessionUtil.getCurrentUser();
			if(user==null){
				throw new EBaicException("登录超时，请重新登录");
			}
			msg = reqService.personConfirm(user.getCerType(), user.getCerNo(), gid, withAuth);
		}
		response.addAttr("user_type", userType);
		response.addAttr("msg", msg);
	}
	
	/**
	 * 退回申请人
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/backToApp")
	public void backToApp(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String userType = HttpSessionUtil.getLoginUserType();
		if(StringUtils.isBlank(userType)){
			throw new EBaicException("登录超时，请重新登录");
		}
		String gid = ParamUtil.get("gid");
		String reason = ParamUtil.get("reason");
		String msg="0";
		if(OptimusAuthManager.LOGIN_USER_TYPE_ENT.equals(userType)){
			SysmgrIdentityBO ent = HttpSessionUtil.getEntUser();
			if(ent==null){
				throw new EBaicException("登录超时，请重新登录");
			}
			msg = reqService.cpBackToAppUser(ent.getCerNo(), gid, reason);
			
		}else if (OptimusAuthManager.LOGIN_USER_TYPE_PERSON.equals(userType)){
			TPtYhBO user = HttpSessionUtil.getCurrentUser();
			if(user==null){
				throw new EBaicException("登录超时，请重新登录");
			}
			msg = reqService.personBackToAppUser(user.getCerType(), user.getCerNo(), gid, reason);
		}
		response.addAttr("user_type", userType);		
		if("0".equals(msg)){
			response.addAttr("msg", "已成功退回申请人，请及时联系申请人完成信息修改重新提交");
		}else{
			response.addAttr("msg", msg);
		}
		
		
		
		
	}
	
}
