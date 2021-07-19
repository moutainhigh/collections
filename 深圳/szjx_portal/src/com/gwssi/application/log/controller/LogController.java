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
import com.gwssi.application.log.service.LogService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/log")
public class LogController extends BaseController{
	@Autowired
	private LogService logService;
	
	/**操作日志页面的跳转
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("operationLog")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0301",operationType="显示操作日志页面",operationCode= AppConstants.LOG_OPERATE_QUERY)
	public void getOperationLog(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//进入操作日志页面
		resp.addPage("/page/log/operation_log.jsp");
	}
	
	@RequestMapping("ExceOperationLog")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0305",operationType="显示异常日志页面",operationCode= AppConstants.LOG_OPERATE_QUERY)
	public void getExceOperationLog(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//进入异常日志页面
		resp.addPage("/page/log/operation_exception_log.jsp");
	}
	
	/**接口日志页面的跳转
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("interfaceLog")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0304",operationType="显示接口交换日志页面",operationCode= AppConstants.LOG_OPERATE_QUERY)
	public void getInterfaceLog(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//进入接口日志页面
		resp.addPage("/page/log/interface_log.jsp");
	}
	
	/**业务流程页面的跳转
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("flowLog")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0302",operationType="显示业务流程日志页面",operationCode= AppConstants.LOG_OPERATE_QUERY)
	public void getFlowLog(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//进入业务流程日志页面
		resp.addPage("/page/log/operationflow_log.jsp");
	}
	
	/**操作日志查询
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0301",operationType="操作日志查询",operationCode= AppConstants.LOG_OPERATE_QUERY)
	public void queryOperationLog(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取查询条件
		Map params = req.getForm("formpanel");
		
		//获取查询结果集
		List list = logService.queryOperationLog(params,"log");
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
		
	}
	/**
	 * 异常日志查看
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryException")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0305",operationType="异常日志查询",operationCode= AppConstants.LOG_OPERATE_QUERY)
	public void queryExceptionLog(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取查询条件
		Map params = req.getForm("formpanel");
		
		//获取查询结果集
		List list = logService.queryOperationLog(params,"exlog");
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
		
	}	
	
	
	/**将全部系统名称显示在下拉列表中
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryLogCodeValue")
	public void queryLogCodeValue(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String treename=req.getAttr("flag").toString();
				

		List systemList = logService.queryLogCodeValue(treename);
		if(StringUtils.isNotEmpty(treename)){
		
			resp.addTree("treename", systemList);
		}
	}
	/**将全部系统名称显示在下拉列表中
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("requestSystem")
	public void queryRequestSystem(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		//获取当前全部系统名称
		List systemList = logService.querySystemList();
		
		//封装数据并返回前台
		resp.addTree("reqPkSys", systemList);
	}
	
	/**将全部系统名称显示在下拉列表中
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("targetSystem")
	public void queryTargetSystem(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		//获取当前全部系统名称
		List systemList = logService.querySystemList();
		
		//封装数据并返回前台
		resp.addTree("resPkSys", systemList);
	}
	
	/**业务流程日志查询
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryFlow")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0302",operationType="业务流程日志查询",operationCode= AppConstants.LOG_OPERATE_QUERY)
	public void queryFlow(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		
		//获取formpanel中的数据
		Map params = req.getForm("formpanel");
		
		//通过条件查询
		List list = logService.queryFlowLog(params);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
	}
	
	/**接口日志查询
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryInterface")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0304",operationType="接口日志查询",operationCode= AppConstants.LOG_OPERATE_QUERY)
	public void queryInterface(OptimusRequest req,OptimusResponse resp) throws OptimusException{
		
		//获取formpanel中的数据
		Map params = req.getForm("formpanel");
		
		//通过条件查询
		List list = logService.queryInterfaceLog(params);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
	}
	
	@RequestMapping("logTest")
	@LogBOAnnotation(systemCode="SM",functionCode="SM04",operationType="测试",operationCode= AppConstants.LOG_OPERATE_INIT)
	public void logTest(OptimusRequest req, OptimusResponse resp){
		HttpServletRequest request=	req.getHttpRequest();
		String agent = req.getHttpRequest().getHeader("user-agent");
		System.out.println(agent);
		StringTokenizer st = new StringTokenizer(agent,";");
		st.nextToken();
		String userbrowser = st.nextToken();
		System.out.println(userbrowser);
		String useros = st.nextToken();
		System.out.println(useros);
		System.out.println("系统："+System.getProperty("os.name")); //win2003竟然是win xp？
		System.out.println("版本："+System.getProperty("os.version"));
		System.out.println(System.getProperty("os.arch"));
		System.out.println(request.getHeader("user-agent")); //返回客户端浏览器的版本号、类型
		System.out.println(request.getMethod()); //：获得客户端向服务器端传送数据的方法有get、post、put等类型
		System.out.println(request.getRequestURI()); //：获得发出请求字符串的客户端地址
		System.out.println(request.getServletPath()); //：获得客户端所请求的脚本文件的文件路径
		System.out.println(request.getServerName()); //：获得服务器的名字
		System.out.println(request.getServerPort()); //：获得服务器的端口号
		System.out.println(request.getRemoteAddr()); //：获得客户端的ip地址
		System.out.println(request.getRemoteHost()); //：获得客户端电脑的名字，若失败，则返回客户端电脑的ip地址
		System.out.println(request.getProtocol()); //：
		System.out.println(request.getHeaderNames()); //：返回所有request header的名字，结果集是一个enumeration（枚举）类的实例
		System.out.println("Protocol: " + request.getProtocol());
		System.out.println("Scheme: " + request.getScheme());
		System.out.println("Server Name: " + request.getServerName() );
		System.out.println("Server Port: " + request.getServerPort());
		System.out.println("Protocol: " + request.getProtocol());
	//	System.out.println("Server Info: " + getServletConfig().getServletContext().getServerInfo());
		System.out.println("Remote Addr: " + request.getRemoteAddr());
		System.out.println("Remote Host: " + request.getRemoteHost());
		System.out.println("Character Encoding: " + request.getCharacterEncoding());
		System.out.println("Content Length: " + request.getContentLength());
		System.out.println("Content Type: "+ request.getContentType());
		System.out.println("Auth Type: " + request.getAuthType());
		System.out.println("HTTP Method: " + request.getMethod());
		System.out.println("Path Info: " + request.getPathInfo());
		System.out.println("Path Trans: " + request.getPathTranslated());
		System.out.println("Query String: " + request.getQueryString());
		System.out.println("Remote User: " + request.getRemoteUser());
		System.out.println("Session Id: " + request.getRequestedSessionId());
		System.out.println("Request URI: " + request.getRequestURI());
		System.out.println("Servlet Path: " + request.getServletPath());
		System.out.println("Accept: " + request.getHeader("Accept"));
		System.out.println("Host: " + request.getHeader("Host"));
		System.out.println("Referer : " + request.getHeader("Referer"));
		System.out.println("Accept-Language : " + request.getHeader("Accept-Language"));
		System.out.println("Accept-Encoding : " + request.getHeader("Accept-Encoding"));
		System.out.println("User-Agent : " + request.getHeader("User-Agent"));
		System.out.println("Connection : " + request.getHeader("Connection"));
		System.out.println("Cookie : " + request.getHeader("Cookie"));

		 
	}
	
	@RequestMapping("asyncTest")
	public void AsnyTest(OptimusRequest req, OptimusResponse resp){
		logService.doasyncTest();
		  System.out.println("我已经执行了！");
	}
	
}
