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
import com.gwssi.comselect.service.PortalAppSelectService;

@Controller
@RequestMapping("/PortalAppSelect")
public class PortalAppSelectController{
	private static  Logger log=Logger.getLogger(PortalAppSelectController.class);

	@Resource
	private PortalAppSelectService portalAppSelectService;
	
	/**
	 * 门户首页业务系统列表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/PortalAppQueryList")
	public void PortalAppQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("portalAppQueryListPanel");
			List<Map> lstParams= portalAppSelectService.getPortalAppList(form,req.getHttpRequest());//查询年报前100条信息 
			System.out.println(lstParams.toString());
			resp.addGrid("portalAppQueryListGrid",lstParams);
	}
	
	/**
	 * 门户首页业务系统详情
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/PortalAppQueryDetail")
	public void PortalAppQueryDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
			String pkSysIntegration ="";
			if ((String)req.getAttr("pkSysIntegration")==null) {
				pkSysIntegration=req.getParameter("pkSysIntegration");
			}else{
				pkSysIntegration=(String)req.getAttr("pkSysIntegration"); //关联字段
			}
			if(pkSysIntegration==null){
				throw new OptimusException("获取参数失败");
			}
			resp.addForm("portalAppCRUDPanel", portalAppSelectService.getPortalAppDetail(pkSysIntegration));
	}
	
	/**
	 * 增加门户首页业务系统
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/saveAdd")
	public void saveAdd(OptimusRequest req, OptimusResponse resp) throws OptimusException {
			String back ="fail";
			String pkSysIntegration ="";
			if ((String)req.getAttr("pkSysIntegration")==null) {
				pkSysIntegration=req.getParameter("pkSysIntegration");
			}else{
				pkSysIntegration=(String)req.getAttr("pkSysIntegration"); //关联字段
			}
			if(pkSysIntegration==null){
				throw new OptimusException("获取参数失败");
			}
		//	String oldIp = (String)req.getAttr("pkSysIntegration");
			//获取formpanel中填写的数据
			Map<String,String> map = req.getForm("portalAppCRUDPanel");
			String systemName = (String)map.get("systemName");
			List<Map> lstParams =  portalAppSelectService.getPortalAppBySystemName(systemName);
			
			//当添加的记录不存在时
			if(lstParams == null){
				//保存新增的信息
			//	codeDataService.saveCodeDataAdd(dcStandardCodedataBO);
				back = "success";
			} 
			
			//封装结果集
			resp.addAttr("back",back);
			resp.addForm("portalAppCRUDPanel", portalAppSelectService.getPortalAppDetail(pkSysIntegration));
	}
	

}
