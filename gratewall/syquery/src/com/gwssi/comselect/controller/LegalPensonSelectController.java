package com.gwssi.comselect.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.otherselect.service.OtherSelectService;
import com.alibaba.fastjson.JSON;
import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.comselect.service.LegalPensonSelectService;
import com.gwssi.comselect.service.RepeatSelectService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.otherselect.service.OtherSelectService;

@Controller
@RequestMapping("/LegalPensonselect")
public class LegalPensonSelectController{

	@Resource
	private LegalPensonSelectService legalPensonSelectService;
	
	/**
	 *法定代表人查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/LegalPensonQueryList")
	public void LegalPensonQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("LegalPensonQueryListPanel");
		String flag = req.getParameter("flag");
		if(flag !=null){
			String count= legalPensonSelectService.getLegalPensonListCount(form);
			resp.addAttr("count", count);
		}else {
			List<Map> lstParams= legalPensonSelectService.getLegalPensonList(form,req.getHttpRequest());
			//System.out.println(lstParams.toString());
			resp.addGrid("LegalPensonQueryListGrid",lstParams);
		}
	}
	
}
