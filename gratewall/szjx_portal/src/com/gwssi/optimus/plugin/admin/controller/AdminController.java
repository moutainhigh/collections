package com.gwssi.optimus.plugin.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.application.webservice.client.IReceiveHrApp;
import com.gwssi.application.webservice.client.ReceiveHrAppImpl;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

	@Autowired
	private AuthService authService;

	private static Logger logger = Logger.getLogger(AdminController.class);

	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("/login")
	@ResponseBody
	public void login(OptimusRequest req, OptimusResponse resp)
			throws Exception {

		// 获取登录用户名
		String userName = req.getParameter("dlm").trim();

		// 调用人事接口获取当前登录人的人员信息和岗位信息
		IReceiveHrApp iReceiveHr = new ReceiveHrAppImpl();
		User user = iReceiveHr.getUserInfo(userName);

		// 根据当前登录的岗位信息查出角色集合
		List<Map> roleList = authService.getRoleIdByPost(
				user.getPostList());
		logger.info("roleList is " + roleList);
		user.setRoleList(roleList);

		// 将当前登录人的信息存入缓存中
		HttpSession session = WebContext.getHttpSession();
		session.setAttribute(OptimusAuthManager.LOGIN_NAME, userName);
		session.setAttribute(OptimusAuthManager.USER, user);
	}

//	@RequestMapping("/getLoginName")
//	@ResponseBody
//	public Map<String, String> username(HttpSession session)
//			throws OptimusException {
//		Map<String, String> map = new HashMap<String, String>();
//		User user = (User) session.getAttribute(OptimusAuthManager.USER);
//		if (null != user) {
//			String loginName = user.getLoginName();
//			map.put("loginName", loginName);
//		}
//		return map;
//	}

//	@RequestMapping("/queryFuncTree")
//	public void queryFuncTree(OptimusRequest req, OptimusResponse res)
//			throws OptimusException {
//		HttpSession session = req.getHttpRequest().getSession();
//		String dlm = (String) session
//				.getAttribute(OptimusAuthManager.LOGIN_NAME);
//		boolean flag = false;
//		if (Constants.SECURITY_AUTHCHECK) {
//			if (session.getAttribute(OptimusAuthManager.SUPERADMIN) != null)
//				flag = (Boolean) session
//						.getAttribute(OptimusAuthManager.SUPERADMIN);
//		}
//		List funcList = authService.queryMenu(dlm, flag);
//		Map rootNode = new HashMap();
//		rootNode.put("name", "菜单树");
//		rootNode.put("id", "0");
//		funcList.add(rootNode);
//		res.addTree("funcTree", funcList);
//	}

	@RequestMapping("quit")
	public void quit(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		session.removeAttribute(OptimusAuthManager.LOGIN_NAME);
		session.removeAttribute(OptimusAuthManager.USER);
	}

}
