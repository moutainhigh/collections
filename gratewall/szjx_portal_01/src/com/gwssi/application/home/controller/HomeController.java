package com.gwssi.application.home.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.application.common.AppConstants;
import com.gwssi.application.common.ParamUtil;
import com.gwssi.application.home.service.HomeService;
import com.gwssi.application.home.service.PortalWaitService;
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

	@Autowired
	private PortalWaitService portalWaitService;
	
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

/*		String pkBusiDomain = req.getAttr("pkBusiDomain").toString();
		boolean isSuperAdmin = false; // 是否是超级管理员
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
			appList = homeService.getApp(pkBusiDomain);
		} else {
			if (isAdmin) {
				Properties properties = ConfigManager.getProperties("common");
				String sysAppCode = properties.getProperty("common.sys.code");
				appList = homeService.getApp(pkBusiDomain,roleList, sysAppCode);
			} else {
				appList = homeService.getApp(pkBusiDomain,roleList);
			}
		}*/
		
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		
		List<String> userconnlist= UserUtil.getAllPksysList(user);
		
		
		
		
		String pkBusiDomain =null;//系统主键
		if(null!=req.getAttr("pkBusiDomain")){
			pkBusiDomain=req.getAttr("pkBusiDomain").toString();
		}else{
			pkBusiDomain=req.getParameter("pkBusiDomain");
		}
		
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
	 * 获取当前登录用户在本系统中的访问功能列表
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getAppFuncByAppId")
	@ResponseBody
	public void getAppFuncByAppId(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {

		/*
		boolean isSuperAdmin = false; // 是否是超级管理员
		boolean isAdmin = false; // 是否是系统管理员

		// 获取应用系统主键
		String appCode = req.getParameter("appCode");
		String appID = req.getParameter("appID");

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
		if (sysAppCode.equals(sysAppCode)) {
			if (isSuperAdmin)
				funcList = homeService.getFunc(appCode);
			else
				funcList = homeService.getFunc(appCode, sysFuncCode);
		} else {
			if (isSuperAdmin || isAdmin)
				funcList = homeService.getFunc(appCode);
			else
				funcList = homeService.getFunc(roleList, appCode);
		}

		*/
		
		
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
	
	/**查询业务域
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/loadAppByBusionDomain")
	@ResponseBody
	public void loadAppByBusionDomain(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		boolean showDC =false;
		if(user.getAdminSysList()!=null && user.getAdminSysList().size()>0){
			showDC=true;
		}
		
		if(user.getUserSysList()!=null&&user.getUserSysList().size()>0){
			List list = homeService.getAllBusiDomain(user.getUserSysList(),showDC);
			resp.addTree("busidomain", list);	
		}
	}
	
	@RequestMapping("/getAppWait")
	@ResponseBody
	public Map getAppWait(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {

		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		int pages=Integer.parseInt(req.getParameter("pages"));
		int pagesize=Integer.parseInt(req.getParameter("shownum"));
		return portalWaitService.dopagesDate(pages, pagesize, user.getUserId());

	
	}
	
	/**
	 * 获取所有系统
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/getAllApp")
	@ResponseBody
	public void getAppAll(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {

/*		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		boolean showDC =false;
		if(user.getAdminSysList()!=null && user.getAdminSysList().size()>0){
			showDC=true;
		}
		
		if(user.getUserSysList()!=null&&user.getUserSysList().size()>0){
			List list1 = homeService.dogetAllApp(user.getUserSysList(),showDC);
			resp.addTree("busidomain", list1);	
		}
*/
		//List list1 = homeService.dogetAllApp();
		List list1 = homeService.dogetAllAppByView(req);
		resp.addTree("busidomain", list1);	
	}
	
	@RequestMapping("/getAppAllWait")
	@ResponseBody
	public Map getAppAllWait(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {

		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		return portalWaitService.dogetAllWait(user.getUserId());

	
	}
	@RequestMapping("/getUserName")
	@ResponseBody
	public void getUserName(OptimusRequest req, OptimusResponse resp)
			throws OptimusException, IOException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		Map userMap =  new HashMap<String,String>();
		userMap.put("user", user.getUserId());
		
		HttpServletResponse response= resp.getHttpResponse();
		HttpServletRequest	request= req.getHttpRequest();
        response.setContentType("text/plain");  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
 /*       Map<String,String> map = new HashMap<String,String>();   
        map.put("result", "content");  */
        PrintWriter out = response.getWriter();       
        String jsonText = JSON.toJSONString(userMap, true);  
        JSONObject resultJSON = JSONObject.parseObject(jsonText); //根据需要拼装json  
        String jsonpCallback = request.getParameter("jsonpCallback");//客户端请求参数  
        out.println(jsonpCallback+"("+jsonText+")");//返回jsonp格式数据  
        out.flush();  
        out.close(); 
        
        

		
		//return userMap;

	
	}
	
	
	@RequestMapping(value = "getUserNameByjsonp")
	@ResponseBody
	public String getMobileAuthCode( HttpServletRequest request, String callback)
	        throws Exception {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
	    String result =  "{'ret':'"+user.getUserId()+"'}";
	    //加上返回参数
	    result=callback+"("+result+")";
	   return result;
	}
	
	/**
	 * 获取所有业务系统
	 * @param req
	 * @param callback
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/getAllAppByJsop")
	@ResponseBody
	public String getAppAllByjson(OptimusRequest req,  String callback,OptimusResponse resp)
			throws OptimusException {

		List list1 = homeService.dogetAllAppByView(req);
		 String jsonText = JSON.toJSONString(list1, true);  
		String result=callback+"("+jsonText+")";
	//	resp.addTree("busidomain", list1);	
		return result;
	}
	
	/**
	 * 获取用户中文名和用户ID
	 * @param req
	 * @param callback
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/getUserByJsop")
	@ResponseBody
	public String getUserByjson(OptimusRequest req,  String callback,OptimusResponse resp)
			throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		Map<String,String> userMap =new HashMap<String,String>();
		userMap.put("userId", user.getUserId());
		userMap.put("userName", user.getUserName());
		 String jsonText = JSON.toJSONString(userMap, true);
		String result=callback+"("+jsonText+")";
	//	resp.addTree("busidomain", list1);	
		return result;
	}
	
	
	
	
	
	/***
	 * webService掉OA待办列表接口，通过域用户ID查询OA列表及待办数量/URL
	 * 域用户ID错误，会抛出异常
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/getOaWaitByWebService")
	@ResponseBody
	public String getOaWaitByWebService()
			throws OptimusException {
		logger.debug("getOaWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String result = null;
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if(user==null||user.getUserId()==null){
			return result;
		}
		Properties properties = ConfigManager.getProperties("oaWait");
		String endpointAddress = properties.getProperty("OA_UPCOMING_ADDR"); // 地址
		String targetNamespace = properties.getProperty("OA_TARGET_NAME_SPACE"); // targetNamespace
		String operName = properties.getProperty("OPER_NAME"); // operName
		String paramName = properties.getProperty("PARAM_NAME"); // 参数名称
		
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpointAddress);
			call.setOperationName(new QName(targetNamespace, operName));
			call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//接口的参数
			call.setReturnType(XMLType.XSD_STRING);//设置返回类型 
			logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+user.getUserId());
//			result = (String)call.invoke(new Object[]{"LINQY1@SZAIC"});
			result = (String)call.invoke(new Object[]{user.getUserId()});
			logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+result);
			}
			catch (Exception e) {
				logger.error("call getOaWaitByWebService  is error ,error message>>>>>>>>>> :"+e.getMessage());
			}
		return result;
	}
	
	/***
	 * webService掉市场监管待办列表接口，通过域用户ID查询市场监管列表及待办数量/URL
	 * 域用户ID错误，会抛出异常
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/getSCJGWaitByWebService")
	@ResponseBody
	public Map<String,Object> getSCJGWaitByWebService()
			throws OptimusException {
		logger.debug("getSCJGWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String result = null;
		Map<String,Object> resultMap = null;
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if(user==null||user.getUserId()==null){
			return resultMap;
		}
		Properties properties = ConfigManager.getProperties("scjgWait");
		String endpointAddress = properties.getProperty("ADDR"); // 地址
		String targetNamespace = properties.getProperty("NAME_SPACE"); // targetNamespace
		String operName = properties.getProperty("OPER_NAME"); // operName
		String paramName = properties.getProperty("PARAM_NAME"); // 参数名称
		
		try {
			//直接引用远程的wsdl文件
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpointAddress);
			call.setOperationName(new QName(targetNamespace, operName));
			call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//接口的参数
			logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+user.getUserId());
//			resultMap =(Map<String, Object>) call.invoke(new Object[]{"CaoRW"});
			resultMap =(Map<String, Object>) call.invoke(new Object[]{user.getUserId()});
			logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+resultMap);
			}
			catch (Exception e) {
				logger.error("call getSCJGWaitByWebService  is error ,error message>>>>>>>>>> :"+e.getMessage());
				return resultMap;
			}
		return resultMap;
	}
	/***
	 * webService掉O执法办案接口，通过域用户ID查询O执法办案及待办数量/URL
	 * 域用户ID错误，会抛出异常
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/getZFBAWaitByWebService")
	@ResponseBody
	public Map<String,Object> getZFBAWaitByWebService()
			throws OptimusException {
		logger.debug("getZFBAWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String result = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if(user==null||user.getUserId()==null){
			return resultMap;
		}
		Properties properties = ConfigManager.getProperties("zfbaWait");
		String endpointAddress = properties.getProperty("ADDR"); // 地址
		String targetNamespace = properties.getProperty("NAME_SPACE"); // targetNamespace
		String operName = properties.getProperty("OPER_NAME"); // operName
		String paramName = properties.getProperty("PARAM_NAME"); // 参数名称
		
		try {
			//直接引用远程的wsdl文件
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpointAddress);
			call.setOperationName(new QName(targetNamespace, operName));
			call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//接口的参数
			logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+user.getUserId());
			resultMap.put("count", result);
			result =(String) call.invoke(new Object[]{user.getUserId()});
			logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+result);
		}
		catch (Exception e) {
			logger.error("call wsgetZFBAWaitByWebServicedl  is error ,error message>>>>>>>>>> :"+e.getMessage());
			return resultMap;
		}
		return resultMap;
	}
	
/***
	 * webService掉短信接口，通过域用户ID查询短信待办数量
	 * 域用户ID错误，会抛出异常
	 * @return
		{"code":"1","message":"用户编码不正确"}
		{"code":"1","message":"没有审批权限"}
		
		{"code":"0","message":"8"}
		
		0：成功，Message：数量
		1：错误，Message：错误消息
	 * @throws OptimusException
	 */
	@RequestMapping("/getMessageWaitByWebService")
	@ResponseBody
	public Map<String,Object> getMessageWaitByWebService()
			throws OptimusException {
		logger.debug("getMessageWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String result = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if(user==null||user.getUserId()==null){
			return resultMap;
		}
		Properties properties = ConfigManager.getProperties("dxWait");
		String endpointAddress = properties.getProperty("ADDR"); // 地址
		String targetNamespace = properties.getProperty("NAME_SPACE"); // targetNamespace
		String operName = properties.getProperty("OPER_NAME"); // operName
		String paramName = properties.getProperty("PARAM_NAME"); // 参数名称
		
		try {
			//直接引用远程的wsdl文件
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpointAddress);
			call.setSOAPActionURI("http://tempuri.org/getSmsCount");
			call.setOperationName(new QName(targetNamespace, operName));
			call.addParameter(new QName(targetNamespace, paramName), XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
			String userId =user.getUserId();
			userId =userId.split("@")[0];
			logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
//			result =(String) call.invoke(new Object[]{"LINQY1"});
			result =(String) call.invoke(new Object[]{userId});
			JSONObject jsonObj =JSONObject.parseObject(result);
			resultMap.put("code", jsonObj.get("code"));
			resultMap.put("message", jsonObj.get("message"));
			logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+resultMap);
		}
		catch (Exception e) {
			logger.error("call getMessageWaitByWebService  is error ,error message>>>>>>>>>> :"+e.getMessage());
			return resultMap;
		}
		return resultMap;
	}
	
}
