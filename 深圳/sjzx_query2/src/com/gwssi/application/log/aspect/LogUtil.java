package com.gwssi.application.log.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

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
		User user = null;
		if(session != null){
			user = (User) session.getAttribute(OptimusAuthManager.USER);
		}
		
		String userId = "";
		String userIp =  (String) session.getAttribute(OptimusAuthManager.USERIP);
		if(user != null){
			userId = user.getUserId().toUpperCase();
		}
		//String userIp = getIp(req);//获取ip
	//	String userIp = getIp(req);//获取ip
		System.out.println(userIp);
		
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
	/*public static String getIp(HttpServletRequest request){
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
	}*/
	
	/*public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
     ///   System.out.println("X-Forwarded-For"+ip);
        if(ip != null && ip.length() > 0 && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(ip != null && ip.length() > 0 && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
	*/
	/**@rgm
     * 获取访问者IP
     * 
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     * 
     * @param request
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
        	  int index = ip.lastIndexOf(",");
              if(index != -1){
            	  return ip.substring(index+1,ip.length()); 
              }else{
            	  return ip; 
              }
        } else {
            return request.getRemoteAddr();
        }
    }
	
	
	/*
	public static void main(String[] args) {
		String ipAdd ="192.168.1.110,192.168.1.120,192.168.1.130,192.168.1.100";
		  int index = ipAdd.lastIndexOf(",");
          if(index != -1){
              System.out.println(ipAdd.substring(index+1,ipAdd.length())); 
          }else{
        	  System.out.println(ipAdd); 
          }
	}*/
}
