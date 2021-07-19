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
import com.gwssi.comselect.service.PersonSelectService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/PersonSelect")
public class PersonSelectController{

	@Resource
	private PersonSelectService personSelectService;
	
	/**
	 *法定代表人查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/PersonQueryList")
	public void PersonQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("PersonQueryListPanel");
		String flag = req.getParameter("flag");
		if(flag !=null){
			String count= personSelectService.getPersonListCount(form);
			resp.addAttr("count", count);
		}else {
			List<Map> lstParams= personSelectService.getPersonList(form,req.getHttpRequest());
			//System.out.println(lstParams.toString());
			resp.addGrid("LegalPensonQueryListGrid",lstParams);
		}
	}
	
}
