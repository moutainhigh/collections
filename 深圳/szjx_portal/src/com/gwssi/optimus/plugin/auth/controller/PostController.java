package com.gwssi.optimus.plugin.auth.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.log.annotation.LogBOAnnotation;
import com.gwssi.application.model.SmPositionRoleBO;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.PositionService;
import com.gwssi.optimus.plugin.auth.service.RoleService;

@Controller
@RequestMapping("/auth")
public class PostController {
	@Autowired
	private PositionService positionService;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 岗位管理--系统角色表（页面跳转） 
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 * @throws IOException 
	 */

	@RequestMapping("/getPosition_Html")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0203",operationType="获取岗位信息",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public void getPosition_Html(OptimusRequest req, OptimusResponse resp)
			throws OptimusException, IOException {
		String pkposition = req.getParameter("pkposition");
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if(user.getIsSuperAdmin()){
			resp.addAttr("pkposition", pkposition);
			resp.addPage("/page/auth/post_list.jsp");
		}else{
			HttpServletResponse res =resp.getHttpResponse();
			res.sendRedirect(req.getHttpRequest().getContextPath()+"/page/home/permission.jsp");
		}
		

	}
	
	
	/**
	 * 得到posi现存的角色
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getPosiCurrRoleHtml")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0203",operationType="获取岗位信息",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public void getPosiCurrRoleHtml(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkposition = req.getParameter("pkposition");
		resp.addAttr("pkposition", pkposition);
		resp.addPage("/page/auth/posi_rolelist.jsp");
	}
	
	/**
	 * 岗位管理--获取岗位名称 
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */

	@RequestMapping("/getPositionName")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0203",operationType="获取岗位名称",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public void getPositionName(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkposition = req.getParameter("pkposition");
		List<Map> funcList = positionService.getPositionName(pkposition);
		if(funcList.isEmpty()){
			resp.addAttr("showPosition", "");
		}else{
			resp.addAttr("showPosition", funcList.get(0).get("postName"));
		}
	}
	
	/**
	 * 菜单管理--查询系统-角色树
	 * 
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/queryPositionTree")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0203",operationType="获取角色信息",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public void queryPositionTree(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkposition = null;
		pkposition = req.getParameter("pkposition");
		List funcList = null; // 返回树
		List rollList = null;
		List<Map<String,String>> currentRoleList = null;
		// List funcList = funcService.queryFuncTree(pkSysIntegration);
		boolean isSuperAdmin = false; // 是否是超级管理员

		Map systemN = null;
		systemN = new HashMap();
		List system = new ArrayList();

		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 获取当前登录用户角色列表
		List<Map> roleList = user.getRoleList();

		// 判断当前用户是否为超级管理员
/*		List<Map> rolel = roleList;
		List<String> role = new ArrayList();
		for (Map r1 : rolel) {
			String roleType = (String) r1.get("roleType");
			if (AppConstants.ROLE_TYPE_SUPER.equals(roleType)) {
				isSuperAdmin = true;

			}
		}*/
		isSuperAdmin= user.getIsSuperAdmin();
		// 获取功能树
		funcList = positionService.findSystemTree(isSuperAdmin);
		// 获取角色树
		roleList = positionService.findRoleTree(isSuperAdmin);
		// 获取当前岗位的角色ID
		currentRoleList = positionService.findcurrentRole(pkposition);
		if(!currentRoleList.isEmpty()){
			for(int i=0; i<currentRoleList.size();i++){
				String s = currentRoleList.get(i).get("roleCode");
				for(int j=0;j<roleList.size();j++){
					String b = (String)roleList.get(j).get("id");
					//System.out.println("roleList________" + b);
					if(b.equals(s)){
						//System.out.println("OK");
						roleList.get(j).put("checked", "true");
						//System.out.println("roleList_________" + roleList.get(j));
					}
				}
			}
		}
	
		//SmSysIntegrationBO sms = funcService.findSystem(pkSysIntegration);
		funcList.addAll(roleList);
		// 返回树
		res.addAttr("pkposition", pkposition);
		res.addTree("funcTree", funcList);	
	}
	
	/**
	 * 角色管理--角色维护（页面跳转） 页面跳转 跳转到具体细节页面 从功能维护到具体细节
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getPost_crudHtml")
	public void getPost_crudHtml(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String type = req.getParameter("type");
		String sysid = req.getParameter("pkSysIntegration");
		String pkRole = req.getParameter("pkRole");// 这里的pkrole就是主键 roleid
		//String queryone = req.getParameter("queryone");
		//String superFunction = req.getParameter("superFunction");// pkfunction
		if (type == null || type.equals(null) || type.equals("")) {
			type = "";
		} else {
		}
		if (sysid == null || sysid.equals("")
				|| sysid.equals(null)) {
			sysid = "";
		} else {
		}
		if (pkRole == null || pkRole.equals("") || pkRole.equals(null)) {
			pkRole = "";
		}
//		if (queryone == null || queryone.equals("") || queryone.equals(null)) {
//			queryone = "";
//		}
//		if (superFunction == null || superFunction.equals("")
//				|| superFunction.equals(null)) {
//			superFunction = "";
//		}
		resp.addAttr("type", type);
		resp.addAttr("sysid", sysid);
		resp.addAttr("pkRole", pkRole);
		resp.addPage("/page/auth/post_crud.jsp");
	}
	
	/**
	 * 获取岗位所具有的系统--角色 树
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/getPosiRole")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0203",operationType="获取系统信息",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public void getPosiRole(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkposition = req.getParameter("pkposition");
		 List rolelist=positionService.findRoleList(pkposition);
		 List syslist =positionService.findPosiSYs(pkposition);
		 
		 rolelist.addAll(syslist);	 
		 res.addTree("funcTree", rolelist);
	}
	
	
	
	/**
	 * 岗位管理--角色维护  角色权限分配 保存岗位所具有的功能
	 * 
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/savePositionRolefunc")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0203",operationType="保存角色功能",operationCode=AppConstants.LOG_OPERATE_SAVE)
	public void savePositionRolefunc(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkRole = req.getParameter("pkRole");
		String pkposition = req.getParameter("pkposition");
		
		//List<SmPositionRoleBO> poro = positionService.queryAllPositionFunc();
		
		// 获取选择的功能代码并转化为list
		String funcIdsStr = (String) req.getAttr("funcIdsStr");
		List<String> funcIds = new ArrayList<String>();// 功能代码
		if (StringUtils.isEmpty(funcIdsStr)) {
		} else {
			funcIds = Arrays.asList(funcIdsStr.split(","));
		}
		
		// 先删除本角色所具有的功能 人后再保存选择的功能
		if (funcIds.size() <= 0) {
			positionService.deleteAllRoleFunc(pkposition);
		} else {
			// 先删除所有角色
			positionService.deleteAllRoleFunc(pkposition);

			// 保存所具有的功能
			for (int i = 0; i < funcIds.size(); i++) {
				
				SmPositionRoleBO smpobo = new SmPositionRoleBO();
				smpobo.setRoleCode(funcIds.get(i).toString());
				smpobo.setPkPosition(pkposition);

				positionService.addPositionFunc(smpobo);

			}

		}
		List funcList = new ArrayList();
		List roleList = new ArrayList();
		List<Map> systempList = new ArrayList();
		List<Map> roletempList = new ArrayList();
		Set<String> temp = new HashSet<String>();
		for (int i = 0; i < funcIds.size(); i++){
			System.out.println(funcIds.get(i));
			systempList = positionService.findSystemTreeByPK(funcIds.get(i).toString());
			roletempList = positionService.findRoleTreeByPK(funcIds.get(i).toString());
			//System.out.println("systempList__________________" + (String)systempList.get(0).get("id"));
			//System.out.println("roletempList__________________" + roletempList);
			if(!temp.contains((String)systempList.get(0).get("id"))){
				funcList.addAll(systempList);
				temp.add((String)systempList.get(0).get("id"));
			}
			else{
				//System.out.println("重复出现的系统id");
			}
			//funcList.addAll(systempList);
			funcList.addAll(roletempList);	
		}
		System.out.println(temp);
		//System.out.println("tempList_________" + tempList);
		//System.out.println("funcList_______________" + funcList);
		res.addTree("funcTree", funcList);
	}
	
	
	/**
	 * 角色管理--角色维护--具体细节 获取功能权限树
	 * 
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/queryPositionFunc")
	public void queryPositionFunc(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkRole = req.getParameter("pkRole");
		String sysid = req.getParameter("sysid");
		List<String> pkrole = new ArrayList<String>();
		pkrole.add(pkRole);

		// 找到所有功能 并把已经有的功能进行转换checked
		List SysList = positionService.findSysByPKRole(pkRole);
		System.out.println(SysList.get(0));
		List allFucnList = positionService.findFuncByPKTree(sysid);
		
		SysList.addAll(allFucnList);
		

		// 返回树
		 res.addTree("funcTree", SysList);
	}
	
	/**
	 * 角色管理--角色维护--具体细节 获取功能权限树
	 * 
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteRole")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0203",operationType="删除角色信息",operationCode=AppConstants.LOG_OPERATE_DELETE)
	public void deleteRole(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		// 获取所选的角色
		String pkRole = (String) req.getAttr("pkRole");
		String pkSysIntegration = (String) req.getAttr("pkSysIntegration");
		SmRoleBO sms = new SmRoleBO();
		// sms.setPkRole(s);
		sms.setRoleCode(pkRole);
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);// 读取静态User
		Calendar calendar1 = Calendar.getInstance();
		sms.setModifierTime(calendar1);
		sms.setModifierId(user.getUserId());
		sms.setModifierName(user.getUserName());
		sms.setEffectiveMarker(AppConstants.EFFECTIVE_N);

		// 删除所具有的岗位和功能
		roleService.deleteAllRolePosi(sms.getRoleCode());// 删除和岗位相连的数据
		roleService.deleteAllRoleFunc(sms.getRoleCode());// 删除和功能

		// 删除（更新）角色
		roleService.deleteSomeRole(sms);
	}
	
	

}
