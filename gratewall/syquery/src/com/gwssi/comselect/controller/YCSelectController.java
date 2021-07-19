package com.gwssi.comselect.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.comselect.service.YCSelectService;

@Controller
@RequestMapping("/ycselect")
public class YCSelectController{

	@Resource
	private YCSelectService ycSelectService;
	
	/**
	 * 异常名录查询列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/YCQueryList")
	public void CFQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
			Map<String, String> form = req.getForm("CFQueryListPanel");
			List<Map> lstParams= ycSelectService.getSXList(form,req.getHttpRequest());
			//System.out.println(lstParams.toString());
			resp.addGrid("CFQueryListGrid",lstParams);
		}
	
	/**
	 * 异常名录查询详情信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getQueryById")
	public void getQueryById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String sExtSequence = req.getParameter("sExtSequence");
		if(sExtSequence==null){
			throw new OptimusException("获取参数失败");
		}
		resp.addForm("querySXDetialPanel", ycSelectService.getSXQueryById(sExtSequence));
	}
}
