package com.gwssi.application.log.controller;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.log.annotation.LogBOAnnotation;
import com.gwssi.application.log.service.LogOSBService;
import com.gwssi.application.log.service.LogService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/log")
public class LogOSBController extends BaseController{
	@Autowired
	private LogOSBService logOsbService;
	
	/**
	 * 获取服务代码
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("requestServiceCode")
	public void queryRequestSystem(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		//获取当前全部系统名称
		List servicelist = logOsbService.querygsbServiceCode();
		
		//封装数据并返回前台
		resp.addTree("serviceCode", servicelist);
	}
	
	@RequestMapping("osblog")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0306",operationType="显示OSB接口调用日志页面",operationCode= AppConstants.LOG_OPERATE_QUERY)
	public void getOSBLog(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//进入接口日志页面
		resp.addPage("/page/log/osb_log.jsp");
	}
	

	@RequestMapping("queryOsbLog")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0306",operationType="OSB日志查询",operationCode= AppConstants.LOG_OPERATE_QUERY)
	public void queryInterface(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		
		//获取formpanel中的数据
		Map params = req.getForm("formpanel");
		
		//通过条件查询
		List list = logOsbService.queryOsbLog(params);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
	}
	
	@RequestMapping("osblogDetail")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0306",operationType="页面跳转显示OSB接口调用详细信息",operationCode= AppConstants.LOG_OPERATE_QUERY)
	public void getOSBLogDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {

		String pkOsbLog=req.getParameter("pkOsbLog");
		if(StringUtils.isBlank(pkOsbLog)){
			pkOsbLog="";
		}
		resp.addAttr("pkOsbLog",pkOsbLog);
		//进入接口日志页面
		resp.addPage("/page/log/osb_service_detail.jsp");
	}
	
	@RequestMapping("osblogDetailBypk")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0306",operationType="显示OSB接口调用详细信息",operationCode= AppConstants.LOG_OPERATE_QUERY)
	public void getOSBLogDetailBypk(OptimusRequest req, OptimusResponse resp) throws OptimusException {

		String pkOsbLog=req.getParameter("pkOsbLog");
		if(StringUtils.isBlank(pkOsbLog)){
			pkOsbLog="";
		}
		Map servicelist = logOsbService.queryOSBLogByPK(pkOsbLog);
		resp.addForm("formpanel", servicelist);
	}
}
