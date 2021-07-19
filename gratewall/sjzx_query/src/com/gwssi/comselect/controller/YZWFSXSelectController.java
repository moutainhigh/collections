package com.gwssi.comselect.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.comselect.service.YZWFSXSelectService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

/**
 * 严重违法失信
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/yzwfsx_select")
public class YZWFSXSelectController {
	
	@Autowired
	private YZWFSXSelectService yzwfsxSelectService;
	
	/**
	 * 严重违法失信企业查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException 
	 */
	@RequestMapping("/YZWFSXEntList")
	public void YZWFSXEntList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("CFQueryListPanel");
		List<Map> list = yzwfsxSelectService.getEntList(form, req.getHttpRequest());
		resp.addGrid("CFQueryListGrid", list);
	}
	
	/**
	 * 严重违法失信企业 
	 * @param req
	 * @param resp
	 * @throws OptimusException 
	 */
	@RequestMapping("/YZWFSXQueryList")
	public void YZWFSXQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String entid = (String) req.getAttr("entid");
		List<Map> list = yzwfsxSelectService.getSXList(entid, req.getHttpRequest());
		resp.addGrid("CFQueryListGrid", list);
	}
	
	/**
	 * 根据sExtSequence查询严重违法失信详细信息
	 * @param req
	 * @param resp
	 * @throws OptimusException 
	 */
	@RequestMapping("/getQueryById")
	public void getQueryById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String id = req.getParameter("sExtSequence");
		if(id == null) {
			throw new OptimusException("获取参数失败");
		}
		Map map = yzwfsxSelectService.getSXInfoById(id);
		resp.addForm("querySXDetialPanel", map);
	}

}
