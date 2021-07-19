package com.gwssi.otherselect.controller;


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

@Controller
@RequestMapping("/otherselect")
public class OtherSelectController{

	@Resource
	private OtherSelectService otherSelectService;
	
	/**
	 * 餐饮许可证信息查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/CYQueryList")
	public void CYQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("CYQueryListPanel");
		List<Map> lstParams= otherSelectService.getCYFWList(form,req.getHttpRequest());
		resp.addGrid("CYQueryListGrid",lstParams);
	}
	
	
	/**
	 * 食品流通许可证信息查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/SPQueryList")
	public void SPQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("SPQueryListPanel");
		List<Map> lstParams= otherSelectService.getSPFWList(form,req.getHttpRequest());
		resp.addGrid("SPQueryListGrid",lstParams);
	}
	
	
	/**
	 * 餐饮许可证信息查询详情信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getCYQueryById")
	public void getCYQueryById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		if(id==null){
			throw new OptimusException("获取参数失败");
		}
		resp.addForm("queryCYDetialPanel", otherSelectService.getCYQueryById(id));
	}
	
	/**
	 * 食品流通许可证信息查询详情信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getSPQueryById")
	public void getSPQueryById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		if(id==null){
			throw new OptimusException("获取参数失败");
		}
		resp.addForm("querySPDetialPanel", otherSelectService.getSPQueryById(id));
	}
}
