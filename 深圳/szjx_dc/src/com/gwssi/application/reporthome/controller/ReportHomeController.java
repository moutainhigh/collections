package com.gwssi.application.reporthome.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.application.reporthome.service.ReportHomeService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

@Controller
@RequestMapping("/reportHome")
public class ReportHomeController {
	@Autowired
	private ReportHomeService reportHomeService;
	private static Logger logger = Logger.getLogger(ReportHomeController.class);

	/**
	 * 用于首页加载当前用户有权限访问的应用或系统
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getAppFuncByAppId")
	@ResponseBody
	public void loadAppForIndex(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {

/*		boolean isSuperAdmin = false; // 是否是超级管理员
		boolean isAdmin = false; // 是否是本系统管理员

		// 从缓存中获取当前登录用户信息
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 获取当前登录用户角色列表
		List roleList = user.getRoleList();

		// 判断是否是超级管理员，系统管理员，普通用户
		SmRoleBO bo = new SmRoleBO();
		for (int i = 0; i < roleList.size(); i++) {
			ParamUtil.mapToBean((Map) roleList.get(i), bo, false);
			if (AppConstants.ROLE_TYPE_SUPER.equals(bo.getRoleType())) {
				isSuperAdmin = true; // 是超级管理员
				logger.info("超级管理员登录系统！");
				break;
			} else if (AppConstants.ROLE_TYPE_SYS.equals(bo.getRoleType())) {
				isAdmin = true; // 是系统管理员
				logger.info("某系统管理员登录系统！");
				break;
			}
		}

		// 通过当前登录用户角色查询用户有权限访问的应用
		List appList = new ArrayList();
		if (isSuperAdmin) {
			appList = homeService.getApp();
		} else {
			if (isAdmin) {
				Properties properties = ConfigManager.getProperties("common");
				String sysAppCode = properties.getProperty("common.sys.code");
				appList = homeService.getApp(roleList, sysAppCode);
			} else {
				appList = homeService.getApp(roleList);
			}
		}
		// 封装数据并返回前台*/
		
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		List funcList = reportHomeService.getAppFuncByAppId(user.getUserId());
		//funcList = user.getFunclist();
	
		// 封装数据并返回前台
		resp.addTree("funlist", funcList);
	}}
