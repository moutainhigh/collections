package com.gwssi.rodimus.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
//import com.gwssi.rodimus.dao.DaoUtil;

/**
 * 
 * @author lixibo
 */
public class ExceptionLogUtil {

	protected static Logger logger = Logger.getLogger(ExceptionLogUtil.class);
	
	public static void log(HttpServletRequest req, Object paramObject, Throwable e){
		try {
			//String sql ="insert into SYSMGR_EXCEPTION_LOG(LOG_ID, SESSION_ID, PAGE_REFERER, URI, PARAMS, CLASS_METHOD, EXCEPTION_CLASS, EXCEPTION_MSG, EXCEPTION_CAUSE,EXCEPTION_STACKTRACE, UPDATE_TIME) values (sys_guid(),?,?,?,?,?,?,?,?,?,sysdate)";
	    	List<String> list = new ArrayList<String>();
	    	//session_id
	    	list.add(req.getSession().getId());
	    	//引用页面
	    	list.add(req.getHeader("Referer"));
	    	//URI
	    	list.add(req.getRequestURI());
	    	//页面传递的参数
	    	list.add(JSONObject.toJSONString(req.getParameterMap(),true));
	    	//报错的类和方法
	    	list.add(paramObject.toString());
	    	//异常名
	    	list.add(e.getClass().toString());
	    	//异常信息
	    	list.add(e.getMessage());
	    	//异常原因信息
	    	String causeStr="";
	    	Throwable cause=e.getCause();
	    	if(cause !=null){
	    		causeStr=cause.getMessage();
	    	}
	    	list.add(causeStr);
	    	//异常堆栈信息
	    	StringWriter errStack = new StringWriter();
	    	PrintWriter printWriter = new PrintWriter(errStack);
	    	e.printStackTrace(printWriter);
	    	list.add(errStack.toString());
	    	
	    	//插入日志
			//DaoUtil.getInstance().execute(sql, list);
	    	e.printStackTrace();
		} catch (Exception error) {
			logger.error("记录异常日志出错:"+error.getMessage());
		}
	}
}
