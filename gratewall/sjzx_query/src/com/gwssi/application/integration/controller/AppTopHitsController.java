package com.gwssi.application.integration.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import net.sf.excelutils.ExcelException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.integration.service.AppHitsService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
public class AppTopHitsController {

	@Autowired
	private AppHitsService app;

	
	//当前只用到这个
	@SuppressWarnings("rawtypes")
	@RequestMapping("/tag")
	public void getAppListTab(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		List list = app.getApplistView();
		System.out.println(list);
		resp.addAttr("list", list);
	}
	
	
	
	
	/*@SuppressWarnings("rawtypes")
	@RequestMapping("/app")
	public void getAppList(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String param = req.getParameter("name");
		
		if (param == null) {
			param = (String) req.getAttr("name");
		}

		System.out.println(param);
		if (param == null) {
			throw new OptimusException("参数不能为空");
		}else{
			resp.addAttr("msg",param);
		}
	}

	
	@RequestMapping("/list")
	public void getList(OptimusRequest req, OptimusResponse resp)	throws OptimusException, UnsupportedEncodingException {
		//String param = req.getParameter("name");
		
		String str = new String(req.getParameter("name").getBytes("iso-8859-1"), "utf-8");
		List list = app.getApplist(str);
		if (list != null && list.size() > 0) {
			resp.addGrid("CFQueryListGrid", list);
		}

	}*/
}
