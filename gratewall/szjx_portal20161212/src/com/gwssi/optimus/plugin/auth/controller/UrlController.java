package com.gwssi.optimus.plugin.auth.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.model.SmWhiteFunctionBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.service.RoleService;
import com.gwssi.optimus.plugin.auth.service.UrlService;

@Controller
@RequestMapping("/auth")
public class UrlController {

	@Autowired
	private UrlService urlService;
	@Autowired
	private RoleService roleService;

	@RequestMapping("/saveUrl")
	public void saveUser(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		SmWhiteFunctionBO bo = req
				.getForm("formpanel", SmWhiteFunctionBO.class);
		// 取当前用户ID
		String userId = (String) WebContext.getHttpSession().getAttribute(
				OptimusAuthManager.LOGIN_NAME);

		String url = bo.getFunctionUrl();

		PathMatcher pathMatcher = new AntPathMatcher();
		if (pathMatcher.isPattern(url)) {
			bo.setIsExactMatch("0");// 非精确匹配
		} else {
			bo.setIsExactMatch("1");
		}

		String back = "fail";
		List<SmWhiteFunctionBO> userList = urlService.queryUrl(bo
				.getFunctionName());
		if (userList.isEmpty()) {
			if (bo.getPkWhiteFunction() == null
					|| "".equals(bo.getPkWhiteFunction())) {
				bo.setCreaterId(userId);
				bo.setCreateTime(Calendar.getInstance());
				urlService.saveUrl(bo);
				back = "success";
			} else {
				bo.setLastUpdaterId(userId);
				bo.setLastUpdateTime(Calendar.getInstance());
				urlService.updateUrl(bo);
				back = "success";
			}
		} else {
			if (bo.getPkWhiteFunction() != null
					&& bo.getPkWhiteFunction().equals(
							userList.get(0).getPkWhiteFunction())) {
				bo.setLastUpdaterId(userId);
				bo.setLastUpdateTime(Calendar.getInstance());
				urlService.updateUrl(bo);
				back = "success";
			}
		}
		resp.addAttr("back", back);
	}

	@RequestMapping("urlDetail")
	public void userDetail(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String urlId = req.getParameter("urlId");
		SmWhiteFunctionBO whiteFuncionBo = urlService.getUrl(urlId);
		resp.addForm("formpanel", whiteFuncionBo);
	}

	@RequestMapping("/deleteUrl")
	public void deleteUser(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		List<Map<String, String>> list = req.getGrid("urlGrid");
		for (Map<String, String> map : list) {
			String urlId = (String) map.get("pkWhiteFunction");
			SmWhiteFunctionBO bo = new SmWhiteFunctionBO();
			bo.setPkWhiteFunction(urlId);
			urlService.deleteUrl(bo);
		}
		resp.addAttr("back", "success");
	}
}
