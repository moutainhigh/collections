package com.gwssi.ebaic.common.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.gwssi.ebaic.common.service.BjcaService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/bjca")
public class BjcaController {
	
	@Autowired
	BjcaService bjcaService ; 
	
	@RequestMapping("/service")
	public void getFile(OptimusRequest request,OptimusResponse response){
		String serviceName = request.getParameter("m");
		Map<String,Object> ret = bjcaService.service(serviceName, request.getHttpRequest());
		response.addResponseBody(JSON.toJSONString(ret,true));
	}
//	
//	@RequestMapping("/test")
//	public void test(OptimusRequest request,OptimusResponse response){
//		String gid = request.getParameter("gid");
//		
//		System.out.println(gid);
////		throw new OptimusException("fail");
//		response.addResponseBody(JSON.toJSONString(gid,true));
//	}
//	@RequestMapping("/test1")
//	public void test1(OptimusRequest request,OptimusResponse response) throws OptimusException{
//		String gid = request.getParameter("gid");
//		int s = 1/0;
//		
//		
////		throw new OptimusException("fail");
//	}
	
}
