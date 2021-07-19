package com.gwssi.application.integration.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.application.integration.service.OrgPubService;
import com.gwssi.optimus.core.exception.OptimusException;


/**
 * 
 * @author liuhailong
 */
@Controller
@RequestMapping("/OrgPub")
public class OrgPubController {
	
	@Autowired
	private OrgPubService orgPubService;
	
	private static Logger logger = Logger.getLogger(OrgPubController.class);
	
	@RequestMapping(value = "getOrgData")
	@ResponseBody
	public String getOrgData(HttpServletRequest request) throws OptimusException {
		String invokerId = request.getParameter("invokerId");
		String strVersion = request.getParameter("version");
		int version = 0;
		try{
			version = Integer.parseInt(strVersion);
		}catch(Exception e){}
		String ret = orgPubService.getOrgData(invokerId, version);
		logger.debug(ret);
		return ret;
	}
	
}
