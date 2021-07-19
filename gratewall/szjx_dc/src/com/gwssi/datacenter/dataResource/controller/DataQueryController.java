package com.gwssi.datacenter.dataResource.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.datacenter.dataResource.service.DataQueryService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.StringUtil;

@Controller
@RequestMapping("/dataQuery")
public class DataQueryController extends BaseController{
	
	@Autowired
	private DataQueryService dataQueryService;
	
	/**
	 * 进入查询页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getQueryPage")
	public void getQueryPage(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//进入元数据查询页面
		resp.addPage("/page/dataResource/dataQuery_list.jsp");
	}
	
	/**
	 * 元数据查询
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void queryData(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取数据
		Map param = req.getForm("formpanel");
		
		//通过条件查询元数据
		List list = dataQueryService.queryData(param);
		
		//封装数据并返回
		resp.addGrid("gridpanel", list);
	}
	
	/**
     * 获取业务对象的主键 与 对应的名称
     * @param req
     * @param resp
     * @throws OptimusException
     */
	@RequestMapping("/findKeyValueDcBusiObjectBO")
	public void findKeyValueDcBusiObjectBO(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		 	List<Map<String, Object>> funcList =null;
	        funcList  = dataQueryService.findKeyValueDcBusiObjectBO();
	        resp.addTree("pkDcBusiObject", funcList); 
	}
	
	/**
     * 获取表信息的主键 与 对应的表名
     * @param req
     * @param resp
     * @throws OptimusException
     */
	@RequestMapping("/findKeyValueDcTableBO")
	public void findKeyValueDcTableBO(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		 	List<Map<String, Object>> funcList =null;
	        funcList  = dataQueryService.findKeyValueDcTableBO();
	        resp.addTree("pkDcTable", funcList); 
	}

}
