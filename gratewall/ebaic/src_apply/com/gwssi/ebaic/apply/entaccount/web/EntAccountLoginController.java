package com.gwssi.ebaic.apply.entaccount.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.entaccount.domain.EntAccountBo;
import com.gwssi.ebaic.apply.entaccount.domain.EntAccountManagerBo;
import com.gwssi.ebaic.apply.entaccount.service.EntAccountService;
import com.gwssi.ebaic.apply.util.SessionConst;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.HttpSessionUtil;
/**
 * 主要人员  保存、下一步。
 * 
 * @author zhongxiaoqi
 */
@Controller
@RequestMapping("/apply/entAccount")
public class EntAccountLoginController {
	
	@Autowired
	EntAccountService entAccountService;
	
	/**
	 * 查询企业用户权限
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/queryAuth")
	public void queryAuth(OptimusRequest request,OptimusResponse response) throws OptimusException{
		//
		HttpSession session = HttpSessionUtil.getSession();
		EntAccountManagerBo managerAcc = (EntAccountManagerBo)session.getAttribute(SessionConst.ENT_ACCOUNT_MGR);
		EntAccountBo entAccount = (EntAccountBo)session.getAttribute(SessionConst.ENT_ACCOUNT);
		Map<String,Object> map = new HashMap<String,Object>();
		//用户权限
		if("1".equals(session.getAttribute(SessionConst.IS_ENT_ACCOUNT))){//若是法定代表人用户，展示所有
			map.put("auth", "all");
		}else{//若是管理员用户，根据其权限展示
			if(managerAcc==null){
				throw new EBaicException("获取管理员账户信息失败。");
			}
			map.put("auth", managerAcc.getOperation());
		}
		//判断是否对应有相应的企业补充信息和企业联系人，来决定是新添还是修改
		map.put("entSnd", entAccountService.checkEntSnd(entAccount.getRsEntId()));
		map.put("entContact", entAccountService.checkEntContact(entAccount.getRsEntId()));
		map.put("entAccount", entAccount);
		map.put("managerAcc", managerAcc);
		response.addAttr("result",map);
	}
	/**
	 * 验证移动电话
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/checkMob")
	public void checkMob(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String entName = ParamUtil.get("user");
		String entPwd = ParamUtil.get("passwrod");
		String mobile = ParamUtil.get("mobile");
		entAccountService.checkMobile(entName, mobile,entPwd);
		response.addAttr("result","success");
	}
	/**
	 * 企业用户登录
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/loginCheck")
	public void checkLoginInfo(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String vercode = ParamUtil.get("vercode",false);
		// 企业账户登录
		String entName = ParamUtil.get("user");
		String entPwd = ParamUtil.get("password");
		String mobile = ParamUtil.get("mobile");
		//用户校验
		entAccountService.save(entName, entPwd,vercode,mobile);
		
		
		
		response.addAttr("result", "success");
	}
	/**
	 * 退出--返回营业执照登陆页面
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/entLogout")
	public void logout(OptimusRequest request,OptimusResponse response) throws OptimusException, IOException, ServletException{
		HttpSession session = HttpSessionUtil.getSession();
		session.removeAttribute(SessionConst.USER);
	}
	
	
}
