package com.gwssi.comselect.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.comselect.service.BlackEntSelectService;

@Controller
@RequestMapping("/BlackEntSelect")
public class BlackEntSelectController{

	@Resource
	private BlackEntSelectService blackEntSelectService;
	
	/**
	 * 黑名单地址企业列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/BlackEntQueryList")
	public void BlackEntQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("blackEntQueryListPanel");
		List<Map> lstParams= blackEntSelectService.getList(form);
		resp.addGrid("portalAppQueryListGrid",lstParams);
	}
	
	/**
	 * 黑名单地址企业详情
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/BlackEntQueryDetail")
	public void BlackEntQueryDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("id");
		resp.addForm("blackEntEditOrAddPannel", blackEntSelectService.getListDetail(id));
	}
	
	/**
	 * 黑名单地址企业更新
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/BlackEntUpdate")
	public void BlackEntUpdate(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = (String)req.getAttr("id");
		Map<String, String> form = req.getForm("blackEntEditOrAddPannel");
		blackEntSelectService.updateListDetail(form,id);
		resp.addAttr("del", "success");
	}
	
	/**
	 * 黑名单地址企业增加
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/BlackEntInsert")
	public void BlackEntInsert(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("blackEntEditOrAddPannel");
		blackEntSelectService.updateListInsert(form);
		resp.addAttr("del", "success");
	}
	
	/**
	 * 黑名单地址企业删除
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/BlackEntDelete")
	public void BlackEntDelete(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = (String)req.getAttr("id");
		blackEntSelectService.updateListDelete(id);
		resp.addAttr("del", "success");
	}
	

}
