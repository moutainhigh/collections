package com.gwssi.rodimus.sms.admin.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.rodimus.sms.admin.service.SmsAdminService;
import com.gwssi.rodimus.sms.domain.SmsWkTemplateBO;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

/**
 * 短信模板。
 * 
 * @author LIUHAILONG
 */
@Controller
@RequestMapping("/sms/tpl/admin")
public class SmsAdminController {
	
	@Autowired
	private SmsAdminService smsAdminService;
	
	/**
	 * 获得列表。
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/list")
	public void getTemplateList(OptimusRequest request,OptimusResponse response)throws OptimusException{
		List<Map> list = smsAdminService.getList();
		response.addGrid("smsGrid", list);
	}
	
	/**
	 * 获得单个模板信息。
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/get")
	public void getTemplate(OptimusRequest request,OptimusResponse response)throws OptimusException{
		String id = ParamUtil.get("id");
		SmsWkTemplateBO bo = smsAdminService.load(id);
		response.addForm("editpanel", bo);
	}
	
	/**
	 * 新增模板。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/add")
	public void addTemplate(OptimusRequest request,OptimusResponse response) throws OptimusException{
		SmsWkTemplateBO bo = request.getForm("addpanel", SmsWkTemplateBO.class);
		smsAdminService.add(bo);
		response.addAttr("result","操作成功");
		
	}
	
	/**
	 * 保存模板。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/save")
	public void saveTemplate(OptimusRequest request,OptimusResponse response)throws OptimusException{
		SmsWkTemplateBO bo = request.getForm("editpanel", SmsWkTemplateBO.class);
		smsAdminService.save(bo);
		response.addAttr("result","操作成功");
	}
	
	/**
	 * 停用模板。
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/disable")
	public void disableTemplate(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String id = ParamUtil.get("id");
		smsAdminService.disable(id);
		response.addAttr("result","操作成功");
		
	}
	/**
	 * 启用模板。
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/enable")
	public void enableTemplate(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String id = ParamUtil.get("id");
		smsAdminService.enable(id);
		response.addAttr("result","操作成功");
		
	}
}
