package com.gwssi.application.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.log.annotation.LogBOAnnotation;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping("operationLog")
	@LogBOAnnotation(functionCode = "ABCD", operationType = "查看", systemCode = "T1")
	public void getOperationLog(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		
		System.out.println("This is a test method for log");
//		throw new NumberFormatException();
		
	}
}
