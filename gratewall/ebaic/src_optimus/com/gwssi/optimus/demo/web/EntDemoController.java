package com.gwssi.optimus.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.demo.domain.BeWkEntDemoBO;
import com.gwssi.optimus.demo.service.EntDemoService;
import com.gwssi.rodimus.util.ParamUtil;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller
@RequestMapping("/entdemo")
public class EntDemoController {
	
	@Autowired
	EntDemoService entDemoService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/doAdd")
	public void add(OptimusRequest request,OptimusResponse response) throws OptimusException{
		BeWkEntDemoBO bo = request.getForm("entDemoFormpanel", BeWkEntDemoBO.class);
		entDemoService.add(bo);
		response.addAttr("result", "success");
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/doDel")
	public void doDel(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String entId = ParamUtil.get("ent_id");
		entDemoService.delete(entId);
		response.addAttr("result", "success");
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/get")
	public void get(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String entId = ParamUtil.get("ent_id");
		BeWkEntDemoBO entDemoBo = entDemoService.get(entId);
		response.addForm("entDemoFormpanel", entDemoBo);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(OptimusRequest request,OptimusResponse response) throws OptimusException{
		BeWkEntDemoBO bo = request.getForm("entDemoFormpanel", BeWkEntDemoBO.class);
		entDemoService.update(bo);
		response.addAttr("result", "success");
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/setRegOrg")
	public void setRegOrg(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String entId = ParamUtil.get("ent_id");
		String regOrg = ParamUtil.get("reg_org");
		entDemoService.setRegOrg(entId, regOrg);
		response.addAttr("result", "success");
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/getList")
	public void getList(OptimusRequest request,OptimusResponse response) throws OptimusException{
		BeWkEntDemoBO condition = request.getForm("entDemoQueryFormpanel", BeWkEntDemoBO.class);
		List<BeWkEntDemoBO> list = entDemoService.getList(condition);
		response.addGrid("entDemoGridpanel", list, "entType,CA16","regOrg,MC_DJSPJG");
	}
}
