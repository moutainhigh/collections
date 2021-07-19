package com.gwssi.report.tzsb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.report.tzsb.service.RegService;

@Controller
@RequestMapping("/tzsb")
public class TZSBRegCodes {

	
	@Autowired
	private RegService service;

	// 所属区域
	@ResponseBody
	@RequestMapping("code_value")
	public void getCode(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		HttpServletRequest request = req.getHttpRequest();
		String type = request.getParameter("type");
		resp.addTree(type, service.queryCode_value(type));
	}

	//// 所属工商所
	@ResponseBody
	@RequestMapping("adminbrancode")
	public void getAdminbrancode(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		HttpServletRequest request = req.getHttpRequest();
		String code = request.getParameter("code");
		System.out.println(code);
		resp.addTree(code, service.getAdminbrancode(code));
	}
	
	
}
