package com.gwssi.comselect.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.comselect.service.HLSelectService;
import com.gwssi.comselect.service.YCSelectService;

@Controller
@RequestMapping("/hlselect")
public class HLSelectController{

	@Resource
	private HLSelectService hlSelectService;
	
	/**
	 * 前海汇率查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/HLQueryList")
	public void CFQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
			Map<String, String> form = req.getForm("CFQueryListPanel");
			List<Map> lstParams= hlSelectService.getSXList(form,req.getHttpRequest());
			resp.addGrid("CFQueryListGrid",lstParams);
		}
	
	/**
	 * 前海汇率新增信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/HLInsert")
	public void getQueryById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("whiteEntEditOrAddPannel");
		hlSelectService.updateListInsert(form);
		resp.addAttr("del", "success");
	}
	
	/**
	 * 前海汇率删除信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/HLDelete")
	public void HLDelete(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = (String)req.getAttr("id");
		hlSelectService.deleteHL(id);
		resp.addAttr("del", "success");
	}
}
