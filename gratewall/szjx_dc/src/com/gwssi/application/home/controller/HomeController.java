package com.gwssi.application.home.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.common.ParamUtil;
import com.gwssi.application.home.service.HomeService;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.application.model.SmScheduleBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.util.UserUtil;
import com.gwssi.optimus.util.DateUtil;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private HomeService homeService;

	private static Logger logger = Logger.getLogger(HomeController.class);

	/**
	 * 用于首页加载当前用户有权限访问的应用或系统
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/loadAppForIndex")
	@ResponseBody
	public void loadAppForIndex(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {

/*		boolean isSuperAdmin = false; // 是否是超级管理员
		boolean isAdmin = false; // 是否是本系统管理员

		// 从缓存中获取当前登录用户信息
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 获取当前登录用户角色列表
		List roleList = user.getRoleList();

		// 判断是否是超级管理员，系统管理员，普通用户
		SmRoleBO bo = new SmRoleBO();
		for (int i = 0; i < roleList.size(); i++) {
			ParamUtil.mapToBean((Map) roleList.get(i), bo, false);
			if (AppConstants.ROLE_TYPE_SUPER.equals(bo.getRoleType())) {
				isSuperAdmin = true; // 是超级管理员
				logger.info("超级管理员登录系统！");
				break;
			} else if (AppConstants.ROLE_TYPE_SYS.equals(bo.getRoleType())) {
				isAdmin = true; // 是系统管理员
				logger.info("某系统管理员登录系统！");
				break;
			}
		}

		// 通过当前登录用户角色查询用户有权限访问的应用
		List appList = new ArrayList();
		if (isSuperAdmin) {
			appList = homeService.getApp();
		} else {
			if (isAdmin) {
				Properties properties = ConfigManager.getProperties("common");
				String sysAppCode = properties.getProperty("common.sys.code");
				appList = homeService.getApp(roleList, sysAppCode);
			} else {
				appList = homeService.getApp(roleList);
			}
		}
		// 封装数据并返回前台
		resp.addTree("applist", appList);*/
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		
		List<String> userconnlist= UserUtil.getAllPksysList(user);
		
		
		
		
		String pkBusiDomain = req.getAttr("pkBusiDomain").toString();//系统主键
		
		List<Map<String,Object>> appList  = homeService.getApp(pkBusiDomain);//该业务系统下的所有系统
		List<Map<String,Object>> appList2 = new ArrayList();
		appList2.addAll(appList);
		for(Map<String,Object> map1 :appList){
			if(userconnlist.contains(map1.get("pkSysIntegration"))){
		
			}else{
				appList2.remove(map1);
			}
		
		}
		
		
		// 新增 返回按钮
	    Map back  = new HashMap();
		back.put("id", "BACK");
		back.put("systemImgUrl", "/static/images/other/back.png");
		back.put("systemCode", "BACK");
		back.put("systemName", "返回");
		appList2.add(back);	
		// 封装数据并返回前台
		resp.addTree("applist", appList2);
	}

	/**
	 * 用于更新首页当前用户有权限访问的应用或系统
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/updateAppForIndex")
	@ResponseBody
	public void updateAppForIndex(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {

		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		List appContList = homeService.getAppCount(user.getUserId());
		resp.addTree("appContList", appContList);
	}

	/**
	 * 获取当前登录用户在某个应用系统中的访问权限
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getAppFuncByAppId")
	@ResponseBody
	public void getAppFuncByAppId(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {

/*		boolean isSuperAdmin = false; // 是否是超级管理员
		boolean isAdmin = false; // 是否是系统管理员
		
		// 获取应用系统主键
		String appCode = req.getParameter("appCode");

		// 从缓存中获取当前登录用户角色集合
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		List roleList = user.getRoleList();

		// 判断是否是超级管理员，系统管理员，普通用户
		SmRoleBO bo = new SmRoleBO();
		List<String> roleCodeList = new ArrayList();
		for (int i = 0; i < roleList.size(); i++) {
			ParamUtil.mapToBean((Map) roleList.get(i), bo, false);
			if (AppConstants.ROLE_TYPE_SUPER.equals(bo.getRoleType())) {
				isSuperAdmin = true; // 是超级管理员
				break;
			} else if (AppConstants.ROLE_TYPE_SYS.equals(bo.getRoleType())) {
				isAdmin = true; // 是系统管理员
				logger.info("是当前访问系统的系统管理员！");
				break;
			}
		}

	
		// 通过角色，查询用户所有应用情况
		List funcList = new ArrayList();
		Properties properties = ConfigManager.getProperties("common");
		String sysAppCode = properties.getProperty("common.sys.code");
		String sysFuncCode = properties.getProperty("common.sys.func");
		
		if (isSuperAdmin || isAdmin){

			funcList = homeService.getFunc(sysAppCode);
		}
		else{
	
			//funcList = homeService.getFunc(roleList, appCode);
			funcList =homeService.getFunc(roleList, sysAppCode);
		}
		// 封装数据并返回前台
		resp.addTree("funlist", funcList);*/
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		List funcList = new ArrayList();
		funcList = user.getFunclist();
	
		// 封装数据并返回前台
		resp.addTree("funlist", funcList);
	}

	/**
	 * 获取当前登录用户的即时信息
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getMessage")
	@ResponseBody
	public void getMessage(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String type = req.getParameter("type");
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		List postList = user.getPostList();
		List messageList = homeService.getMessageList(postList, type);
		if ("1".equals(type)) {
			resp.addGrid("messageList", messageList);
		} else {
			resp.addTree("messageList", messageList);
		}
	}
	
	/**
	 * 保存当前登录用户的日程安排数量
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveSchedule")
	@ResponseBody
	public void saveSchedule(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		SmScheduleBO scheduleBo = (SmScheduleBO) req.getForm("formpanel", SmScheduleBO.class);
		String remindType = scheduleBo.getRemindType();
		
		Map<String,String> scheduleForm = req.getForm("formpanel");
		String day = scheduleForm.get("day");
		String hour = scheduleForm.get("hour");
		String minute = scheduleForm.get("minute");
		String time = day+" "+hour+":"+minute+":0";
		
		Calendar createTime = DateUtil.parseCalendar(time,1);
		
		scheduleBo.setCreaterId(user.getUserId());
		scheduleBo.setCreaterUser(user.getUserName());
		scheduleBo.setCreaterTime(createTime);
		scheduleBo.setModifierTime(createTime);
		
		Calendar compareTime = DateUtil.parseCalendar(time,1);
		if(scheduleBo.getRemindValue() != null){
			int remindValue = Integer.parseInt(scheduleBo.getRemindValue().toString());
			if(AppConstants.REMIND_TYPE_3.equals(remindType)){
				int oldday = compareTime.get(Calendar.DAY_OF_YEAR);
				compareTime.set(Calendar.DAY_OF_YEAR, oldday + remindValue);
			}else if(AppConstants.REMIND_TYPE_2.equals(remindType)){
				int oldhour = compareTime.get(Calendar.HOUR_OF_DAY);
				compareTime.set(Calendar.HOUR_OF_DAY, oldhour + remindValue);
			}else if(AppConstants.REMIND_TYPE_1.equals(remindType)){
				int oldminute = compareTime.get(Calendar.MINUTE);
				compareTime.set(Calendar.MINUTE, oldminute + remindValue);
			}
		}
		
		scheduleBo.setCompareTime(compareTime);
		scheduleBo.setCompareState(AppConstants.EFFECTIVE_Y);
		
		homeService.saveSchedule(scheduleBo);
	}
	

	/**
	 * 获取当前登录用户的日程安排数量
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getScheduleCount")
	@ResponseBody
	public void getScheduleCount(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		List dateList = homeService.getScheduleCount(user.getUserId());
		resp.addTree("dateList", dateList);
	}

	/**
	 * 获取当前登录用户的日程安排数量
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getScheduleContent")
	@ResponseBody
	public void getScheduleContent(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String date = req.getAttr("date").toString();
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		List dateList = homeService.getScheduleContent(user.getUserId(), date);
		resp.addTree("dateList", dateList);
	}

	/**
	 * 当前登录用户的日程删除
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteSchedule")
	public void deleteSchedule(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkSchedule = req.getAttr("pkId").toString();
		homeService.deleteSchedule(pkSchedule);
	}
	
	/**
	 * 当前登录用户的日程提示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/getTipSchedule")
	@ResponseBody
	public void getTipSchedule(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		List tipList = homeService.getTipSchedule(user.getUserId(), AppConstants.EFFECTIVE_Y);
		homeService.updateTipSchedule(user.getUserId(), AppConstants.EFFECTIVE_N);
		resp.addTree("tipList", tipList);
	}
	
	/**
	 * 当前登录用户的日程提示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/getRemind")
	@ResponseBody
	public void getRemind(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		List list = homeService.getRemind();
		resp.addTree("remindlist", list);
	}

}
