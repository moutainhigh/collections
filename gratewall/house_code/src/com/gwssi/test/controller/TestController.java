package com.gwssi.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.test.service.TestService;


@Controller
@RequestMapping("/testDao")
public class TestController {
	@Autowired
	private TestService testService;
	
	
	@RequestMapping("/testDao1")
	@ResponseBody
	public String testDao(HttpServletRequest req, HttpServletResponse res) throws OptimusException{
		System.out.println("已经调用");
		
		return testService.testDao();
	}
	
}
