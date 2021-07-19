package com.gwssi.application.log.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

/**
 * 查询日志记录
 * @author yangzihao
 */
public class LogUtil {

	/**
	 * 向日志记录表插入记录
	 * @param operationDescribe 操作描述 比如执行了什么查询
	 * @param operationSql 执行的sql
	 * @param operationSqlParatemer 执行的sql的参数
	 * @throws OptimusException 
	 */
	public static void insertLog(String operationDescribe,String operationSql,
			String operationSqlParatemer,HttpServletRequest req,IPersistenceDAO dao) throws OptimusException{
		
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		
		String userId = "";
		if(user != null){
			userId = user.getUserId().toUpperCase();
		}
		String userIp = getIp(req);//获取ip
		
		LogBo logBo = new LogBo();
		logBo.setSysFlag("ZHCX");
		logBo.setUserId(userId);
		logBo.setUserIp(userIp);
		logBo.setOperationDescribe(operationDescribe);
		logBo.setOperationSql(operationSql);
		logBo.setOperationSqlParatemer(operationSqlParatemer);
		
		dao.insert(logBo);
	}
	
	/**
	 * 获取当前request的ip 如果是本地访问返回localhost
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"localhost":ip;
	}
}
