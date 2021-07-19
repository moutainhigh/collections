package com.gwssi.comselect.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.comselect.service.TZRSelectService;

@Controller
@RequestMapping("/TZRController")
public class TZRController{

	@Resource
	private TZRSelectService TZRSelectService;
	/**
	 * 投资人查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/TZRQueryList")
	public void CFQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("TZRQueryListPanel");
		String flag = req.getParameter("flag");
		if(flag !=null){
			String count= TZRSelectService.getSXListCount(form);
			resp.addAttr("count", count);
		}else {
			List<Map> lstParams= TZRSelectService.getSXList(form,req.getHttpRequest());
			//System.out.println(lstParams.toString());
			resp.addGrid("TRZQueryListGrid",lstParams);
		}
	}
}
