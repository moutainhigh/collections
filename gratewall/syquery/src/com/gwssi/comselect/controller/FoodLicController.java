package com.gwssi.comselect.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.comselect.service.FoodLicSelectService;

@Controller
@RequestMapping("/FoodLicController")
public class FoodLicController{

	@Resource
	private FoodLicSelectService foodLicSelectService;
	/**
	 * 投资人查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/FoodLicQueryList")
	public void CFQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("FoodLicQueryListPanel");
		String flag = req.getParameter("flag");
		if(flag !=null){
			String count= foodLicSelectService.getSXListCount(form);
			resp.addAttr("count", count);
		}else {
			List<Map> lstParams= foodLicSelectService.getList(form,req.getHttpRequest());
			resp.addGrid("FoodLicQueryListGrid",lstParams);
		}
	}
}
