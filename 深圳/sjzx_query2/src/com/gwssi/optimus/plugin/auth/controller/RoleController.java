package com.gwssi.optimus.plugin.auth.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmPositionRoleBO;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.application.model.SmRoleFuncBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.FuncService;
import com.gwssi.optimus.plugin.auth.service.RoleService;
import com.gwssi.optimus.util.TreeUtil;

@Controller
@RequestMapping("/auth")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private FuncService funcService;

	/**
	 * 通过主键获取一个角色
	 */
	@RequestMapping("getRoleByPK")
	public void getRoleByPK(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkRole = req.getParameter("pkRole");
		SmRoleBO smf = roleService.findRoleByPKBO(pkRole);
		res.addForm("formpanel", smf);

	}

	/**
	 * 角色管理--角色维护（获取某个系统的所有角色） 获取某个系统下的所有角色
	 */

	@RequestMapping("getRolebyPKsyInte")
	public void getRolebyPKsyInte(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		String funcName = null;// 功能名称
		if (req.getParameter("funcName") != null) {
			funcName = req.getParameter("funcName");
		}
		List<String> sysid = new ArrayList<String>();
		sysid.add(pkSysIntegration);
		List list = roleService.findRolebyPKsyInte(sysid, funcName);
		res.addGrid("gridpanel", list);

	}

	/**
	 * 查询角色
	 * 
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("getSomeRole")
	public void getSomeRole(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		SmRoleBO sms = req.getForm("formpanel", SmRoleBO.class);
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		String roleName = sms.getRoleName();
		List<String> sysid = new ArrayList<String>();
		sysid.add(pkSysIntegration);
		List list = roleService.findRolebyPKsyInte(sysid, roleName);
		res.addGrid("gridpanel", list);

	}

	/**
	 * 角色管理--角色维护（获取某个系统的角色内容） 获取某个系统下的所有角色名字 通过系统主键
	 */
	@RequestMapping("queryRolecName")
	public void queryRolecName(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		List<String> sysid = new ArrayList<String>();
		sysid.add(pkSysIntegration);
		List list1 = roleService.findRoleName(sysid);
		res.addAttr("functionName", list1);
	}

	/**
	 * 角色管理（页面跳转） 页面跳转 跳转到Role_manage.jsp(角色管理)
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getRole_manageHtml")
	public void getRole_manageHtml(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		resp.addPage("/page/auth/role_manage.jsp");
	}

	/**
	 * 角色管理--角色维护（页面跳转） 页面跳转 从角色管理（Role_manage.jsp）到（角色维护 fix_role.html ）
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */

	@RequestMapping("/getRole_fixHtml")
	public void getRole_fixHtml(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		resp.addAttr("pkSysIntegration", pkSysIntegration);
		// resp.addPage("/page/auth/role_fix.jsp");
		resp.addPage("/page/auth/role_list.jsp");
		// resp.addPage("/page/auth/yangshi11.jsp");
	}

	/**
	 * 角色管理--角色维护（页面跳转） 页面跳转 跳转到具体细节页面 从功能维护到具体细节
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getRole_crudHtml")
	public void getMenu_crudHtml(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String type = req.getParameter("type");
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		String pkRole = req.getParameter("pkRole");// 这里的pkrole就是主键 roleid
		String queryone = req.getParameter("queryone");
		String superFunction = req.getParameter("superFunction");// pkfunction
		if (type == null || type.equals(null) || type.equals("")) {
			type = "";
		} else {
		}
		if (pkSysIntegration == null || pkSysIntegration.equals("")
				|| pkSysIntegration.equals(null)) {
			pkSysIntegration = "";
		} else {
		}
		if (pkRole == null || pkRole.equals("") || pkRole.equals(null)) {
			pkRole = "";
		}
		if (queryone == null || queryone.equals("") || queryone.equals(null)) {
			queryone = "";
		}
		if (superFunction == null || superFunction.equals("")
				|| superFunction.equals(null)) {
			superFunction = "";
		}
		resp.addAttr("type", type);
		resp.addAttr("pkSysIntegration", pkSysIntegration);
		resp.addAttr("pkRole", pkRole);
		resp.addAttr("queryone", queryone);
		resp.addAttr("superFunction", superFunction);
		resp.addPage("/page/auth/role_crud.jsp");
	}

	/**
	 * 角色管理--角色维护--具体细节（第一个Tab页） 页面跳转 跳转到第一个tab（新增）
	 */
	@RequestMapping("/getAddSmRoleHtml")
	public void getAddSmRoleHtml(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String type = req.getParameter("type");
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		String pkRole = req.getParameter("pkRole");
		if (type == null || type.equals(null) || type.equals("")) {
			type = "";
		} else {
		}
		if (pkSysIntegration == null || pkSysIntegration.equals("")
				|| pkSysIntegration.equals(null)) {
			pkSysIntegration = "";
		} else {
		}
		resp.addAttr("type", type);
		resp.addAttr("pkSysIntegration", pkSysIntegration);
		resp.addAttr("pkRole", pkRole);
		resp.addPage("/page/auth/role_basic_message.jsp");

	}

	/**
	 * 角色管理--角色维护--具体细节（跳转到第二个Tab页） 页面跳转跳转到角色权限分配(第二个Tab页面)
	 * 
	 */
	@RequestMapping("getRoleFuncHtml")
	public void getRoleFunc(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String type = req.getParameter("type");
		String pkRole = req.getParameter("pkRole");// 这里的pkrole就是rolecode
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		if (type == null || type.equals(null) || type.equals("")) {
			type = "";
		}
		if (pkRole == null || pkRole.equals("") || pkRole.equals(null)) {
			pkRole = "";
		} else {
		}
		if (pkSysIntegration == null || pkSysIntegration.equals("")
				|| pkSysIntegration.equals(null)) {
			pkSysIntegration = "";
		} else {
		}
		SmRoleBO s1 = roleService.findRoleByPKBO(pkRole);
		if (s1.getRoleType().equals(AppConstants.ROLE_TYPE_SYS)) {
			resp.addAttr("isSys", "Y");
		} else {
			resp.addAttr("isSys", "N");
		}
		resp.addAttr("type", type);
		resp.addAttr("pkSysIntegration", pkSysIntegration);
		resp.addAttr("pkRole", pkRole);
		resp.addPage("/page/auth/role_func_auth.jsp");
	}

	/**
	 * 角色管理--角色维护--具体细节（第三个Tab 岗位和角色） 页面跳转 跳转到岗位和角色分配
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getRolePosiHtml")
	public void getRolePosi(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String type = req.getParameter("type");
		String pkRole = req.getParameter("pkRole");
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		if (type == null || type.equals(null) || type.equals("")) {
			type = "";
		}
		if (pkRole == null || pkRole.equals("") || pkRole.equals(null)) {
			pkRole = "";
		} else {
		}
		if (pkSysIntegration == null || pkSysIntegration.equals("")
				|| pkSysIntegration.equals(null)) {
			pkSysIntegration = "";
		} else {
		}
		resp.addAttr("type", type);
		resp.addAttr("pkSysIntegration", pkSysIntegration);
		resp.addAttr("pkRole", pkRole);
		resp.addPage("/page/auth/role_posi_auth.jsp");
	}

	/**
	 * 获取创建角色类型 子系统管理员不能创建 超级管理员权限
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/findRoleType")
	public void findRoleType(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		boolean isSuperAdmin = false; // 是否是超级管理员
		boolean isAdmin = false; // 是否是本系统管理员

		Map systemN = null;
		systemN = new HashMap();
		List system = new ArrayList();

		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		// 获取当前登录用户角色列表
		List<Map> roleList = user.getRoleList();

		// 判断是否是超级管理员，系统管理员，普通用户
		SmRoleBO bo = new SmRoleBO();
		List<SmRoleBO> smrolelist = funcService.findRolebyPKList(roleList);

		List<String> pksys = new ArrayList();
		for (SmRoleBO smrole : smrolelist) {
			if (AppConstants.ROLE_TYPE_SUPER.equals(smrole.getRoleType())) {
				isSuperAdmin = true;
			}
			if (AppConstants.ROLE_TYPE_SYS.equals(smrole.getRoleType())) {
				pksys.add(smrole.getPkSysIntegration());
				isAdmin = true;
			}
		}

		List zhuxitong = null;
		if (isSuperAdmin) {
			// systemN.put("text", "超级系统管理员");
			// systemN.put("value", ""+AppConstants.ROLE_TYPE_SUPER+"");
			// system.add(systemN);
			systemN = new HashMap();
			systemN.put("text", "子系统管理员");
			systemN.put("value", "" + AppConstants.ROLE_TYPE_SYS);
			system.add(systemN);
		}
		/*
		 * if(isAdmin){ systemN=new HashMap(); systemN.put("text", "子系统管理员");
		 * systemN.put("value", ""+AppConstants.ROLE_TYPE_SYS);
		 * system.add(systemN); }
		 */
		systemN = new HashMap();
		systemN.put("text", "普通用户");
		systemN.put("value", "" + AppConstants.ROLE_TYPE_DEFAULT);
		system.add(systemN);
		resp.addTree("roleType", system);
	}

	/**
	 * 角色管理--角色维护--具体细节--第一个Tab(新增角色设置) 设置新增角色（包括PK CREATETIME ..）
	 */
	@RequestMapping("/setRole")
	public void setRole(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		// 获取主键 type 类型 BO类
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		String type = req.getParameter("type");
		SmSysIntegrationBO sys = roleService.getSystem(pkSysIntegration);// 获取system

		// 设置角色代码左边字母部分如（SM001）设置为SM
		String roleCodeleft = sys.getSystemCode();

		// 获取当前主键下的角色数量，设置右半部分数字代码 如（SM001） 设置为001
		List<String> pksys = new ArrayList<String>();
		pksys.add(sys.getPkSysIntegration());
		List<SmRoleBO> list = roleService.findSmrole(pksys);// 找到当前下面的数量

		// 设置右边如（001）部分代码
		int rolecoderight = 0;
		String rolecode = null;
		rolecoderight = list.size() - 1;// 从0开始
		rolecode = roleService.findRoleCode(rolecoderight, roleCodeleft);// 角色代码

		// 设置角色代码 创建人 创建时间
		SmRoleBO role = new SmRoleBO();
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);// 读取静态User
		role.setCreaterId(user.getUserId());
		role.setCreaterTime(Calendar.getInstance());
		// role.setPkRole(rolecode);
		role.setRoleCode(rolecode);

		// 获取系统名
		SmSysIntegrationBO sms = funcService.findSystem(pkSysIntegration);// 获取系统名

		// 设置创建人 返回页面
		Map form = new HashMap();
		form.put("roleCode", role.getRoleCode());
		form.put("pkRole", role.getRoleCode());
		form.put("createrTime", role.getCreaterTime());
		form.put("roleState", AppConstants.ROLE_STATE_ON);
		form.put("roleType", AppConstants.ROLE_TYPE_DEFAULT);
		form.put("createrId", role.getCreaterId());
		form.put("systemName", sms.getSystemName());
		form.put("createrName", user.getUserName());
		resp.addForm("formpanel", form);
	}

	/**
	 * 主系统下的新增角色
	 * 
	 */
	@RequestMapping("saveSmRole")
	public void saveSmRole(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String back = "failue";
		Map<String, String> map = req.getForm("formpanel");
		SmRoleBO smrole = req.getForm("formpanel", SmRoleBO.class);

		String str = map.get("createrTime");// String 转换为Calendar
		smrole.setCreaterTime(funcService.changeStringToCalendar(str));

		smrole.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
		try {
			roleService.addSmRole(smrole);
			back = "success";
		} catch (Exception e) {
			back = "failure";
		}
		resp.addAttr("back", back);
	}

	/**
	 * 获取创建角色类型 子系统管理员不能创建 超级管理员权限
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/findRoleState")
	public void findRoleState(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		List system = new ArrayList();

		Map systemN = null;
		systemN = new HashMap();
		systemN.put("text", "启用");
		systemN.put("value", "" + AppConstants.ROLE_STATE_ON);
		system.add(systemN);
		systemN = new HashMap();
		systemN.put("text", "停止");
		systemN.put("value", "" + AppConstants.ROLE_STATE_OFF);
		system.add(systemN);
		resp.addTree("roleState", system);
	}

	/**
	 * 修改时 通过主键获取一个角色
	 */
	@RequestMapping("getRoleSysNameByPK")
	public void getRoleByPKfun(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkRole = req.getParameter("pkRole");
		List smf = roleService.findRoleByPK(pkRole);
		Map m1 = new HashMap();
		if (smf.size() > 0) {
			m1 = (Map) smf.get(0);
		}
		String pkSysIntegration = (String) m1.get("pkSysIntegration");// 岗位id
		SmSysIntegrationBO sms = funcService.findSystem(pkSysIntegration);
		m1.put("systemName", sms.getSystemName());
		m1.put("pkRole", (String) m1.get("roleCode"));

		// 获取当前user
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);// 读取静态User
		Calendar calendar1 = Calendar.getInstance();

		// 修改人信息并不是数据库中存储的 为当前人信息
		// 设置修改人ID name time
		m1.remove("modifierId");
		m1.put("modifierId", user.getUserId());

		m1.remove("modifierName");
		m1.put("modifierName", user.getUserName());

		m1.remove("modifierTime");
		m1.put("modifierTime", calendar1);

		res.addForm("formpanel", m1);

	}

	/**
	 * 主系统下的修改角色
	 * 
	 */
	@RequestMapping("updateSmRole")
	public void updateSmRole(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String back = "failue";

		// 获取BO
		Map<String, String> map = req.getForm("formpanel");
		SmRoleBO smrole = req.getForm("formpanel", SmRoleBO.class);

		// 转换创建时间和修改时间类型
		String str = map.get("createrTime");// String 转换为Calendar
		smrole.setCreaterTime(funcService.changeStringToCalendar(str));

		String str1 = map.get("modifierTime");
		smrole.setModifierTime(funcService.changeStringToCalendar(str1));

		/*
		 * HttpSession session = WebContext.getHttpSession(); User user=(User)
		 * session.getAttribute(OptimusAuthManager.USER);//读取静态User Calendar
		 * calendar1 = Calendar.getInstance();
		 * smrole.setModifierTime(calendar1);
		 * smrole.setModifierId(user.getUserId());
		 * smrole.setModifierName(user.getUserName());
		 */

		// 修改角色
		roleService.updateSmRole(smrole);

		back = "success";
		resp.addAttr("back", back);
	}

	/**
	 * 角色管理--角色维护--具体细节（第三个Tab 获取岗位树） 获取岗位树
	 * 
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/queryAuthPosi")
	public void queryAuthPosi(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkRole = req.getParameter("pkRole");
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		List<String> pkrole = new ArrayList<String>();
		pkrole.add(pkRole);

		// 获取已经选择的岗位
		List authPosiList = roleService.findAuthPosi(pkRole);// 已经选择的岗位

		// 获取所有岗位
		List listPost = funcService.findPostTree();// 所有的岗位

		// 把已经选择的加上key:checked value:true
		TreeUtil.makeCheckedTree(listPost, authPosiList, "id");

		// 加上根节点
		Map rootNode = new HashMap();
		rootNode.put("name", "深圳市市场监督管理委");
		rootNode.put("id", "1");
		rootNode.put("open", true);
		listPost.add(rootNode);

		// 返回树
		res.addTree("listPost", listPost);
	}

	/**
	 * 角色管理--角色维护--具体细节（第二个Tab 获取功能权限树） 获取功能权限树
	 * 
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/queryAuthFunc")
	public void queryAuthFunc(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkRole = req.getParameter("pkRole");
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		List<String> pkrole = new ArrayList<String>();
		pkrole.add(pkRole);

		// 找到所有功能 并把已经有的功能进行转换checked
		List authFuncList = roleService.findAuthFunc(pkRole);
		List allFucnList = funcService.findFuncByPKTree(pkSysIntegration);
		TreeUtil.makeCheckedTree(allFucnList, authFuncList, "id");

		// 设置根节点
		SmSysIntegrationBO sms = funcService.findSystem(pkSysIntegration);
		List changeList = funcService.getChangeList(allFucnList, "system");// 让空的pid变为system
		Map rootNode = new HashMap();
		rootNode.put("name", sms.getSystemName());
		rootNode.put("id", "system");
		rootNode.put("open", true);
		// allFucnList.add(rootNode);
		changeList.add(rootNode);

		// 返回树
		res.addTree("funcTree", changeList);
		// res.addTree("funcTree", allFucnList);
	}

	/**
	 * 角色管理--角色维护--具体细节（第二个Tab 保存角色所具有的功能） 角色权限分配 保存角色所具有的功能
	 * 
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/saveSmRolefunc")
	public void saveSmRolefunc(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkRole = req.getParameter("pkRole");
		String pkSysIntegration = req.getParameter("pkSysIntegration");

		// 获取选择的功能代码并转化为list
		String funcIdsStr = (String) req.getAttr("funcIdsStr");
		List<String> funcIds = new ArrayList<String>();// 功能代码
		if (StringUtils.isEmpty(funcIdsStr)) {
		} else {
			funcIds = Arrays.asList(funcIdsStr.split(","));
		}

		// 先删除本角色所具有的功能 人后再保存选择的功能
		if (funcIds.size() <= 0) {
			roleService.deleteAllRoleFunc(pkRole);
		} else {
			// 先删除所有角色
			roleService.deleteAllRoleFunc(pkRole);

			// 保存所具有的功能
			for (int i = 0; i < funcIds.size(); i++) {
				// List <SmFunctionBO>
				// smf=funcService.queryPkfunctionbyPKsysFuncCode(funcIds.get(i).toString(),pkSysIntegration);

				SmRoleFuncBO smrobo = new SmRoleFuncBO();
				smrobo.setFunctionCode(funcIds.get(i).toString());
				smrobo.setRoleCode(pkRole);

				roleService.addRoleFunc(smrobo);

			}

		}
	}

	/**
	 * 角色管理--角色维护--具体细节（第三个Tab 分配岗位） 角色岗位分配 保存角色所具有的岗位
	 * 
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/saveRoleposi")
	public void saveRoleposi(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkRole = req.getParameter("pkRole");
		String pkSysIntegration = req.getParameter("pkSysIntegration");

		// 获取选择的岗位
		String funcIdsStr = (String) req.getAttr("funcIdsStr");
		List<String> funcIds = new ArrayList<String>();// 功能代码
		if (StringUtils.isEmpty(funcIdsStr)) {
		} else {
			funcIds = Arrays.asList(funcIdsStr.split(","));
		}

		// 先删除所具有的岗位，然后在存储选择的岗位
		if (funcIds.size() <= 0) {
			roleService.deleteAllRolePosi(pkRole);
		} else {
			// 先删除所有岗位
			roleService.deleteAllRolePosi(pkRole);

			// 存储所选择的岗位
			for (int i = 0; i < funcIds.size(); i++) {
				SmPositionRoleBO smrobo = new SmPositionRoleBO();
				smrobo.setRoleCode(pkRole);
				smrobo.setPkPosition(funcIds.get(i).toString());

				roleService.addRolePosi(smrobo);
			}

		}

	}

	/**
	 * 角色管理--角色维护（删除树中角色）
	 * 
	 * @throws OptimusException
	 */
	@RequestMapping("/deletRole1")
	public void deleteRole1(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {

		// 获取所选的角色
		String funcIdsStr = (String) req.getAttr("funcIdsStr");
		if (StringUtils.isEmpty(funcIdsStr)) {
			return;
		}
		List<String> funcIds = Arrays.asList(funcIdsStr.split(","));

		// 删除所选的角色，并删除所具有的岗位和功能
		for (String s : funcIds) {
			// 并不是真正意义上的删除，设置BO
			SmRoleBO sms = new SmRoleBO();
			// sms.setPkRole(s);
			sms.setRoleCode(s);
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

	/**
	 * 角色管理--角色维护（删除角色）
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteAllRole")
	public void deleteAllRole(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		// 获取当前系统
		String pkSysIntegration = (String) req.getAttr("pkSysIntegration");
		List<String> sysid = new ArrayList<String>();
		sysid.add(pkSysIntegration);

		// 获取当先系统下所有的角色
		List<Map> list = roleService.findRolebyPKsyIntenew(sysid, null);

		// 获取当前系统下所有角色主键
		List<String> funcIds = new ArrayList<String>();
		for (Map s : list) {
			funcIds.add(s.get("roleCode").toString());
		}

		// 删除所选的角色，并删除所具有的岗位和功能
		for (String s : funcIds) {
			SmRoleBO sms = new SmRoleBO();
			// sms.setPkRole(s);
			sms.setRoleCode(s);
			HttpSession session = WebContext.getHttpSession();
			User user = (User) session.getAttribute(OptimusAuthManager.USER);// 读取静态User
			Calendar calendar1 = Calendar.getInstance();
			sms.setModifierTime(calendar1);
			sms.setModifierId(user.getUserId());
			sms.setModifierName(user.getUserName());
			sms.setEffectiveMarker(AppConstants.EFFECTIVE_N);

			// 删除岗位和功能
			roleService.deleteAllRolePosi(sms.getRoleCode());// 删除和岗位相连的数据
			roleService.deleteAllRoleFunc(sms.getRoleCode());// 删除和功能

			// 删除角色
			roleService.deleteSomeRole(sms);
		}

	}

	/**
	 * 角色管理--角色维护（删除角色） 删除角色 没有用到
	 */
	@RequestMapping("/deleteSomeRole")
	public void deleteSomeRole(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		List<Map<String, String>> list = req.getGrid("gridpanel");
		for (Map<String, String> map : list) {
			String pkRole = (String) map.get("pkRole");
			SmRoleBO sms = new SmRoleBO();
			// sms.setPkRole(pkRole);
			HttpSession session = WebContext.getHttpSession();
			User user = (User) session.getAttribute(OptimusAuthManager.USER);// 读取静态User
			Calendar calendar1 = Calendar.getInstance();
			sms.setModifierTime(calendar1);
			sms.setModifierId(user.getUserId());
			sms.setEffectiveMarker(AppConstants.EFFECTIVE_N);
			// roleService.deleteAllRolePosi(sms.getPkRole());//删除和岗位相连的数据
			// roleService.deleteAllRoleFunc(sms.getPkRole());//删除和功能
			// funcService.deleteSomeRole(sms);
		}
		resp.addAttr("back", "success");
	}

	/**
	 * 角色管理 --角色维护（删除单个角色）
	 */
	@RequestMapping("/deleteOneRole")
	public void deleteOneRole(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkRole = req.getAttr("pkId").toString();
		SmRoleBO sms = new SmRoleBO();
		// sms.setPkRole(pkRole);
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);// 读取静态User
		Calendar calendar1 = Calendar.getInstance();
		sms.setModifierTime(calendar1);
		sms.setModifierId(user.getUserId());
		sms.setEffectiveMarker(AppConstants.EFFECTIVE_N);

		roleService.deleteAllRolePosi(sms.getRoleCode());// 删除和岗位相连的数据
		roleService.deleteAllRoleFunc(sms.getRoleCode());// 删除和功能
		roleService.deleteSomeRole(sms);
	}

	/**
	 * 菜单管理--查询角色树
	 * 
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("/queryRoleTree")
	public void queryRoleTree(OptimusRequest req, OptimusResponse res)
			throws OptimusException {
		String pkSysIntegration = null;
		pkSysIntegration = req.getParameter("pkSysIntegration");
		List funcList = null; // 返回树

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
		List<Map> rolel = roleList;
		List<String> role = new ArrayList();
		for (Map r1 : rolel) {
			String roleType = (String) r1.get("roleType");
			if (AppConstants.ROLE_TYPE_SUPER.equals(roleType)) {
				isSuperAdmin = true;

			}
		}
		// 获取角色树
		funcList = roleService.findRoleTree(pkSysIntegration, isSuperAdmin);

		// 增加节点
		Map rootNode = new HashMap();
		SmSysIntegrationBO sms = funcService.findSystem(pkSysIntegration);
		rootNode.put("name", sms.getSystemName());
		rootNode.put("id", "0");
		rootNode.put("open", true);
		funcList.add(rootNode);

		// 返回树
		res.addTree("funcTree", funcList);
	}
}
