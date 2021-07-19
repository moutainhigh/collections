package com.gwssi.trs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.trs.Conts;
import com.gwssi.trs.PageDataUtilX;
import com.gwssi.trs.service.TrsService;

@Controller
@RequestMapping("/datatrsxiao")
public class TrsControllerX {
		
	private static  Logger log=Logger.getLogger(TrsControllerX.class);
	
	@Autowired
	private TrsService trsServicexiao;
	  
	@RequestMapping("/querydataxiao")
	@ResponseBody
	public Map querydata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		Map map=null;
		try{
		PageDataUtilX pageData=new PageDataUtilX();
		String pages=req.getParameter("pages");
		String queryKeyWord = req.getParameter("queryKeyWord");
		String labelFlag = req.getParameter("labelFlag");
		//System.out.println(pages);
		
		//System.out.println("search===="+req.getParameter("search"));//搜索条件。
		if(pages!=null){
			map=pageData.pagesDate(Integer.parseInt(pages),Conts.pageSize,queryKeyWord,labelFlag);
		}
		}catch (Exception e) {
			throw new OptimusException("trs出错");
		}
		return map;
	}
	
	
	
	@RequestMapping("/querytreedataxiao")
	@ResponseBody
	public Object querytreedata(OptimusRequest req, OptimusResponse res) throws OptimusException {
		List obj=new ArrayList();
		obj.add("基本信息1");
		obj.add("基本信息2");
		return obj;
	}
}
