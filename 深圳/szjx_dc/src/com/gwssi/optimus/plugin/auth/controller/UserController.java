package com.gwssi.optimus.plugin.auth.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.plugin.auth.service.RoleService;
import com.gwssi.optimus.plugin.auth.service.UserService;

@Controller
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@RequestMapping("/queryUser")
	public void queryUser(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		Map params = req.getForm("formpanel");
		List<Map> userList = userService.queryUser(params);
		resp.addGrid("userGrid", userList);
	}

	@RequestMapping("userDetail")
	public void userDetail(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String userId = req.getParameter("userId");
		Map<String, String> map = userService.getUser(userId);
		// 设置密码为空，不为保存
		map.put("userPwd", "");
		resp.addForm("formpanel", map);
	}
}
