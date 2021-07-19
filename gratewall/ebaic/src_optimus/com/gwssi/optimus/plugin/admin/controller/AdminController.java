package com.gwssi.optimus.plugin.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.ebaic.apply.util.SessionConst;
import com.gwssi.optimus.core.common.Constants;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.TPtYhBO;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;
import com.gwssi.optimus.util.MD5Util;

@Controller
@RequestMapping("/admin")
@SuppressWarnings({"rawtypes","unchecked"})
public class AdminController extends BaseController {
	@Autowired
	private AuthService authService;

	@RequestMapping("login")
	@ResponseBody
	public Map login(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		// Map<String,String> map = req.getForm("formpanel");
		// String dlm = map.get("dlm");
		// String mm = map.get("mm");
		String dlm = req.getParameter("dlm").trim();
		String mm = req.getParameter("mm").trim();
        Map map=new HashMap();
		mm = MD5Util.MD5Encode(mm);
		if (StringUtils.isBlank(dlm)) {
			map.put("back", "fail");
			map.put("msg", "用户名不能为空");
			return map;
		}
		TPtYhBO yhBO = authService.login(dlm, mm);
		boolean isSA = false;
		if (null == yhBO) {
			map.put("back", "fail");
			map.put("msg", "用户名或密码错误");
			return map;
		}
		if (OptimusAuthManager.USER_TYPE_NORMAL.equals(yhBO.getUserType())) {
			map.put("back", "fail");
			map.put("msg", "普通用户不能登录后台");
			return map;
		}
		HttpSession session = WebContext.getHttpSession();
		session.setAttribute(OptimusAuthManager.LOGIN_NAME, dlm);
		String url = "/page/admin/main.html";
		List<Map> list = authService.getRoleIdListByLoginName(dlm);
		List urlList = authService.getDefaultUrl(dlm);
		if (urlList.size() > 0) {
			Map urlMap = (Map) urlList.get(0);
			url = (String) urlMap.get("defUrl");
		}
		List<String> roleList = new ArrayList<String>();
		for (Map mapRole : list) {
			String role = (String) mapRole.get("jsId");
			roleList.add(role);
			if ("superadmin".equals(role)) {
				isSA = true;
			}
		}
		User user = new User();
		user.setUserId(yhBO.getUserId());
		user.setLoginName(yhBO.getUserName());
		user.setRoleIdList(roleList);
		// SystemUser user = new SystemUser();
		// user.setUserId(dlm);
		// user.setRoleIdList(roleList);
		session.setAttribute(OptimusAuthManager.USER, user);
		session.setAttribute(OptimusAuthManager.SUPERADMIN, isSA);
		map.put("url", url);
		map.put("back", "success");
		return map;
	}
	
	@RequestMapping("/getLoginName")
    @ResponseBody
    public Map<String,String> username(HttpSession session) throws OptimusException {
        Map<String,String> map=new HashMap<String,String>();
        User user=(User) session.getAttribute(SessionConst.USER);
        if(null!=user){
            String loginName = user.getLoginName();
            map.put("loginName", loginName);
        }
        return map;
    }
	
	@RequestMapping("/queryFuncTree")
	public void queryFuncTree(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		HttpSession session = req.getHttpRequest().getSession();
		String dlm = (String) session
				.getAttribute(OptimusAuthManager.LOGIN_NAME);
		boolean flag = false;
		if (Constants.SECURITY_AUTHCHECK) {
			if (session.getAttribute(OptimusAuthManager.SUPERADMIN) != null)
				flag = (Boolean) session
						.getAttribute(OptimusAuthManager.SUPERADMIN);
		}
		List funcList = authService.queryMenu(dlm, flag);
		Map rootNode = new HashMap();
		rootNode.put("name", "菜单树");
		rootNode.put("id", "0");
		funcList.add(rootNode);
		res.addTree("funcTree", funcList);
	}

	@RequestMapping("quit")
	public void quit(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		session.removeAttribute(OptimusAuthManager.LOGIN_NAME);
		session.removeAttribute(OptimusAuthManager.USER);
		session.removeAttribute(OptimusAuthManager.SUPERADMIN);
	}
}
