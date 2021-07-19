/**
 * 
 */
package com.gwssi.application.integration.controller;

import java.io.File;
import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.common.FileUtil;
import com.gwssi.application.integration.service.IntegrationService;
import com.gwssi.application.integration.service.SmRoleService;
import com.gwssi.application.log.annotation.LogBOAnnotation;
import com.gwssi.application.model.SmPositionRoleBO;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.core.web.fileupload.OptimusFileItem;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.util.DateUtil;
import com.gwssi.optimus.util.UuidGenerator;

/**
 * @author xiaohan
 *
 */

@Controller
@RequestMapping("/integration")
public class IntegrationController {

	@Autowired
	IntegrationService integrationservice;
	
	@Autowired
	SmRoleService roleservice;
	
	/**
	 * 应用集成-系统新增初始化页面跳转
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/initListIntegration")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0101",operationType="初始化页面跳转",operationCode=AppConstants.LOG_OPERATE_INIT)
	public void initListIntegration(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		/*			List rolelist = user.getRoleList();
		// falg 标识位  0 系统管理员 1 超级管理员
	String flag = "0";
		for(Object rolebo:rolelist){
			Map rolemap = (Map)rolebo;
			String roletype = rolemap.get("roleType").toString();
			if(roletype.equals(AppConstants.ROLE_TYPE_SUPER)){
				flag = "1";
				break;
			}
		}*/
		


		String flag = "0";
		if(user.getIsSuperAdmin()){
			flag="1";
		}
		resp.addAttr("flag", flag);
		resp.addPage("/page/integeration/xt_list.jsp");
	}
	
	/**
	 * 应用集成-系统新增初始化页面跳转
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/initIntegration")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0101",operationType="初始化页面",operationCode=AppConstants.LOG_OPERATE_INIT)
	public void initIntegration(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		String username = user.getUserId();
		Calendar calendar = Calendar.getInstance();
		String currentDate = DateUtil.toDateStr(calendar);
		resp.addAttr("username", username);
		resp.addAttr("currentDate", currentDate);
		resp.addPage("/page/integeration/xt_add.jsp");
	}

	/**
	 * 应用集成-系统新增和修改
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveIntegration")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0101",operationType="添加应用系统",operationCode=AppConstants.LOG_OPERATE_ADD)
	public void saveIntegration(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		integrationservice.dosaveIntegration(req,resp);
		this.findByListIntegration(req, resp);
	}
	
	/**
	 * 应用集成-系统Id查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByIdIntegration")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0101",operationType="查询单个系统",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public SmSysIntegrationBO findByIdIntegration(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		String pkSysIntegration = req.getAttr("pkId").toString();
		List paramObject = new ArrayList();
		paramObject.add(pkSysIntegration);
		List<SmSysIntegrationBO> listIntegration = integrationservice.findByIdIntegration(paramObject);
		if (!listIntegration.isEmpty()) {
			SmSysIntegrationBO integrationBo = (SmSysIntegrationBO)listIntegration.get(0);
			resp.addForm("formpanel", integrationBo);
		}
		return null;
	}
	
	/**
	 * 应用集成-系统列表查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByListIntegration")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0101",operationType="查询整个系统",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public Map<String,List> findByListIntegration(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
/*		List rolelist = user.getRoleList();
		// falg 标识位  0 系统管理员 1 超级管理员
		String flag = "0";
		List listCode = new ArrayList();
		for(Object rolebo:rolelist){
			Map rolemap = (Map)rolebo;
			String roletype = rolemap.get("roleType").toString();
			String rolecode = rolemap.get("pkSysIntegration").toString();
			listCode.add(rolecode);
			if(roletype.equals(AppConstants.ROLE_TYPE_SUPER)){
				flag = "1";
			}
		}*/
		List<SmSysIntegrationBO> rolelist = user.getAdminSysList();
		List listCode = new ArrayList();
		if(rolelist!=null &&rolelist.size()>0){
			for(SmSysIntegrationBO smbo:rolelist){
				String rolecode = smbo.getPkSysIntegration();
				listCode.add(rolecode);
			}
		}
		String flag = "0";
		if(user.getIsSuperAdmin()){
			flag="1";
		}
		
		Map<String,String> formMap = req.getForm("formpanel");
		List listIntegration = integrationservice.findIntegration(formMap,flag,listCode);
		resp.addGrid("gridpanel", listIntegration);
		return null;
	}
	
	/**
	 * 应用集成-系统删除
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteIntegration")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0101",operationType="删除应用集成系统",operationCode=AppConstants.LOG_OPERATE_DELETE)
	public void deleteIntegration(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkSysIntegrations = req.getAttr("pkId").toString();
		
		SmSysIntegrationBO integration = integrationservice.findIntegrationById(pkSysIntegrations);
		integration.setEffectiveMarker(AppConstants.EFFECTIVE_N);
		integrationservice.updateIntegration(integration);
		
		if(StringUtils.equals(AppConstants.SYSTEM_TYPE_SYS, integration.getSystemType())){
		SmRoleBO rolebo = roleservice.findRoleById(integration.getSystemCode()+"_ADMIN");
		rolebo.setEffectiveMarker(AppConstants.EFFECTIVE_N);
		roleservice.updateSmRole(rolebo);
		}
	}
	
	
	/**
	 * 应用集成-角色列表查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByListSmRole")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0101",operationType="查询角色列表",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public Map<String,List> findByListSmRole(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		Map<String,String> formMap = req.getForm("formpanel");
		List listIntegration = roleservice.findRole(formMap);
		resp.addGrid("gridpanel", listIntegration);
		return null;
	}
	
	/**
	 * 应用集成-岗位资源树查询
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/findByListPost")
	@ResponseBody
	@LogBOAnnotation(systemCode="SM",functionCode="SM0101",operationType="查询岗位资源",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public void findByListPost(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		List listPost = roleservice.findPostTree();
		resp.addTree("listPost", listPost);
	}
	
	/**
	 * 应用集成-岗位资源树保存
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/saveListPost")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0101",operationType="保存岗位资源",operationCode=AppConstants.LOG_OPERATE_UPDATE)
	public void saveListPost(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		List postListBo = new ArrayList();
		String pkRole =  req.getAttr("pkRole").toString();
		roleservice.deletePosttionRole(pkRole);
		String pkPositions =  req.getAttr("pkPositions").toString();
		String[] pkPositionlist = pkPositions.split(",");
		for(String pkPosition: pkPositionlist){
			SmPositionRoleBO postrolebo = new SmPositionRoleBO();
			postrolebo.setRoleCode(pkRole);
			postrolebo.setPkPosition(pkPosition);
			postListBo.add(postrolebo);
		}
		roleservice.savePosttionRole(postListBo);
	}
	
	/**
	 * 应用集成-岗位资源树查询
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/findPostitonRole")
	@ResponseBody
	@LogBOAnnotation(systemCode="SM",functionCode="SM0101",operationType="查询岗位资源",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public void findPostitonRole(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkPositions = "";
		String pkRole =  req.getParameter("pkRole");
		List listPostition = roleservice.findPostitonRole(pkRole);
		for(Object position: listPostition){
			Map map = (Map)position;
			pkPositions += map.get("pkPosition") + ",";
		}
		resp.addAttr("pkPositions", pkPositions);
		resp.addAttr("pkRole", pkRole);
		resp.addPage("/page/integeration/qx_add.jsp");
	}
	
	/**查询业务域
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getBusiDomain")
	@ResponseBody
	@LogBOAnnotation(systemCode="SM",functionCode="SM0101",operationType="查询业务域",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public void getBusiDomain(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		List list = integrationservice.getBusiDomain();
		resp.addTree("busidomain", list);			
	}

}
