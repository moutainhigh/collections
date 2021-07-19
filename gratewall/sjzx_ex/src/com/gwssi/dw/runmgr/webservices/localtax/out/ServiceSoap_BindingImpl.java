/**
 * ServiceSoap_BindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.runmgr.webservices.localtax.out;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.util.StringUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.services.ServiceDAO;
import com.gwssi.dw.runmgr.services.ServiceDAOImpl;
import com.gwssi.dw.runmgr.services.common.Constants;

public class ServiceSoap_BindingImpl implements ServiceSoap_PortType{
    private QueryService service = null;
    
    private static DBOperation operation = null;
    /** 地税用户ID，在静态方法中查询获得 */
    private static String dsUserId = "";
    /** 地税用户名，在静态方法中查询获得 */
    private static String dsUserName = "";
    /** 为地税提供的服务ID，在静态方法中查询获得 */
    private static String dsServiceId = "";
    /** 为地税提供的服务名称，在静态方法中查询获得 */
    private static String dsServiceName = "";
    
    static{
		operation = DBOperationFactory.createOperation();
		//查询地税用户及服务的相关信息，仅用于记录日志使用..........
		try {
			Map result = operation.selectOne("SELECT SYS_SVR_USER_ID,USER_NAME FROM sys_svr_user WHERE login_name='ds_user'");
			if(result != null){
				dsUserId = (String)result.get("SYS_SVR_USER_ID");
				dsUserName = (String)result.get("USER_NAME");
			}
			result = operation.selectOne("SELECT SYS_SVR_SERVICE_ID,SVR_NAME FROM sys_svr_service WHERE svr_name='地税调用的服务'");
			if(result != null){
				dsServiceId = (String)result.get("SYS_SVR_SERVICE_ID");
				dsServiceName = (String)result.get("SVR_NAME");
			}
		} catch (DBException e) {
			e.printStackTrace();
		}
    }
    
    public ServiceSoap_BindingImpl()
	{
		service = new QueryServiceImpl();
	}
	
	public ReturnMultiGSData getLJ_Query() throws java.rmi.RemoteException {
    	ReturnMultiGSData data = new ReturnMultiGSData();
    	data.setFHDM(Constants.SERVICE_FHDM_LJ_QUERY);
    	return data;
    }

    public ReturnMultiGSData getGSDJ_QUERY(java.lang.String cxrqq, java.lang.String cxrqz, java.lang.String ksjls, java.lang.String jsjls) throws java.rmi.RemoteException {
    	long d = new Date().getTime();
    	ReturnMultiGSData data = service.queryQY_Info_List(cxrqq, cxrqz, ksjls, jsjls);
    	writeLog(data, d);
    	return data;
    }

    public ReturnMultiGSData getZX_QUERY(java.lang.String cxrqq, java.lang.String cxrqz, java.lang.String ksjls, java.lang.String jsjls) throws java.rmi.RemoteException {
        long d = new Date().getTime();
    	ReturnMultiGSData data = service.queryZXQY_Info_List(cxrqq, cxrqz, ksjls, jsjls);
    	writeLog(data, d);
    	return data;
    }

    public ReturnMultiGSData getDX_QUERY(java.lang.String cxrqq, java.lang.String cxrqz, java.lang.String ksjls, java.lang.String jsjls) throws java.rmi.RemoteException {
        long d = new Date().getTime();
    	ReturnMultiGSData data = service.queryDXQY_Info_List(cxrqq, cxrqz, ksjls, jsjls);
    	writeLog(data, d);
    	return data;
    }

    public ReturnMultiGSData getBG_QUERY(java.lang.String cxrqq, java.lang.String cxrqz, java.lang.String ksjls, java.lang.String jsjls) throws java.rmi.RemoteException {
        long d = new Date().getTime();
    	ReturnMultiGSData data = service.queryBGQY_InfoList(cxrqq, cxrqz, ksjls, jsjls);
    	writeLog(data, d);
    	return data;
    }

    public ReturnMultiGSData getGSDJ_INFO(java.lang.String qymc, java.lang.String yyzzh) throws java.rmi.RemoteException {
    	long d = new Date().getTime();
    	ReturnMultiGSData data = service.queryQY_Info(qymc, yyzzh);
    	writeLog(data, d);
    	return data;
    }

    public ReturnMultiGSData getZX_INFO(java.lang.String qymc, java.lang.String yyzzh) throws java.rmi.RemoteException {
    	long d = new Date().getTime();
    	ReturnMultiGSData data = service.queryZXQY_Info(qymc, yyzzh);
    	writeLog(data, d);
    	return data;
    }

    public ReturnMultiGSData getDX_INFO(java.lang.String qymc, java.lang.String yyzzh) throws java.rmi.RemoteException {
    	long d = new Date().getTime();
    	ReturnMultiGSData data = service.queryDXQY_Info(qymc, yyzzh);
    	writeLog(data, d);
    	return data;
    }

    public ReturnMultiGSData getBG_INFO(java.lang.String qymc, java.lang.String yyzzh) throws java.rmi.RemoteException {
    	long d = new Date().getTime();
    	ReturnMultiGSData data = service.queryBGQY_Info(qymc, yyzzh);
    	writeLog(data, d);
    	return data;
    }
	
    private String getClientIp()
	{
		MessageContext mc = MessageContext.getCurrentContext();
		HttpServletRequest request = (HttpServletRequest) mc.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
		String clientIP = request.getHeader("x-forwarded-for");
		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getHeader("Proxy-Client-IP");
		}
		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getHeader("WL-Proxy-Client-IP");
		}
		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getRemoteAddr();
		}
		return clientIP;
	}
    
    protected void writeLog(ReturnMultiGSData result, long startTime){
		if(dsUserId != null && !dsUserId.trim().equals("")){//如果未取到用户信息，则不记录日志，请相关人员检查共享服务中用户及服务的配置
	    	String clientIp = getClientIp();
			StringBuffer sql = new StringBuffer("INSERT INTO sys_svr_log (sys_svr_log_id,sys_svr_user_id,sys_svr_user_name,sys_svr_service_id,sys_svr_service_name,execute_start_time,execute_end_time,state,records_mount,error_msg,client_ip) VALUES (");
			sql.append("'").append(UuidGenerator.getUUID()).append("', ")
			.append("'").append(dsUserId).append("', ")
			.append("'").append(dsUserName).append("', ")
			.append("'").append(dsServiceId).append("', ")
			.append("'").append(dsServiceName).append("', ")
			.append("'").append(StringUtil.formatDateToString(new Date(startTime),"yyyy-MM-dd HH:mm:ss")).append("', ")
			.append("'").append(StringUtil.formatDateToString(new Date(),"yyyy-MM-dd HH:mm:ss")).append("', ");
			String fhdm = result.getFHDM();
			sql.append("'").append((fhdm.trim().equalsIgnoreCase(Constants.SERVICE_FHDM_SUCCESS) ? "成功" : "失败")).append("', ")
			.append("").append(result.getGSDJ_INFO_ARRAY()==null?"0":""+(result.getGSDJ_INFO_ARRAY().length)).append(", ")
			.append("'").append(fhdm).append("', ")
			.append("'").append(clientIp).append("')");
			
			try {
				operation.execute(sql.toString(), false);
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
	}

}
