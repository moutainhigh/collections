package com.gwssi.comselect.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.comselect.service.QuickSelectService;

@Controller
@RequestMapping("/quickselect")
public class QuickSelectController{

	@Resource
	private QuickSelectService quickSelectService;
	
	/**
	 * 快速查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/QuickQueryList")
	public void CFQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("CFQueryListPanel");
		String flag = req.getParameter("flag");
		if(flag !=null){
			String count= quickSelectService.getSXListCount(form);
			resp.addAttr("count", count);
		}else {
			List<Map> lstParams= quickSelectService.getSXList(form,req.getHttpRequest());
			//System.out.println(lstParams.toString());
			resp.addGrid("CFQueryListGrid",lstParams);
		}
	}
	
}
