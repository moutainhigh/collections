package cn.gwssi.broker.server.cxf.restful;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import cn.gwssi.broker.server.auth.ServiceAuth;
import cn.gwssi.broker.server.listener.ServiceBrokerProperties;
import cn.gwssi.broker.server.task.AsyncServiceWithTimeout;
import cn.gwssi.broker.server.task.SyncServiceWithTimeout;
import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.model.ReponseContextBase;
import cn.gwssi.common.model.RequestContext;
import cn.gwssi.common.model.SynReponseContext;
import cn.gwssi.common.model.TPtFwrzjbxxBO;
import cn.gwssi.common.model.TPtFwrzxxxxBO;
import cn.gwssi.common.resource.DataHandler;
import cn.gwssi.common.resource.GetIPAddressUtil;
import cn.gwssi.common.resource.ServiceConstants;

/**
 * 实现类-所有发布的服务（broker内部服务）
 * @author xue
 * @version 1.0
 * @since 2016/4/28
 */
@Path(value = "/SOAService")
public class ResponseService implements IResponseService{
	private static  Logger log=Logger.getLogger(ResponseService.class);
	private static Logger logK = Logger.getLogger("kafka");

	@Context
    private UriInfo uriInfo;
    
    @Context
    private Request request;
    
    @Context 
    private HttpServletRequest hreq;
    
    /**
     * 特殊服务，参数不做任何处理（包括框架参数解析）
     * @param excuteServicePath
     * @param xml
     * @return
     */
    @GET
    @Path(value = "/specialSynUnifiedService/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String specialSynUnifiedService(@PathParam("excuteServicePath")String excuteServicePath,@QueryParam("xml")String xml) throws BrokerException{
    	log.info("Method:【" + request.getMethod()+"】>>>uri:【" + uriInfo.getPath()+"】>>参数:【"+uriInfo.getPathParameters()+ "】"+"【excuteServicePath"+"】"+excuteServicePath+"【xml"+"】"+xml);
    	Date date= new Date();
    	String returnString="";
  		TPtFwrzjbxxBO t1 = new TPtFwrzjbxxBO();//日志
  		List resultList = new ArrayList();
  		try {
	        String ip = GetIPAddressUtil.getIpAddress(hreq);
	        String params="";//如果是post请求hreq.getQueryString() 不能获取参数，从xml获取
	        if("POST".equalsIgnoreCase(request.getMethod().toUpperCase())){
	    		params = xml;
	    	}else{
	    		params = hreq.getQueryString();
	    	}
	        String url = hreq.getRequestURL().toString();
	        log.info("请求参数："+params);
	        log.info("请求url："+url);
	        String uuid = UUID.randomUUID().toString();
	        t1.setFwmc(url);
	        t1.setCallername(ip);
  	  		t1.setFwrzjbid(uuid);
  	  		t1.setCallertime(ServiceConstants.sdf.format(date));
  	  		t1.setCallerparameter(params);
  	  		t1.setCallerip(ip);
			String localname = (String) ServiceBrokerProperties.serviceBrokerProp.get("localname");
			t1.setCalleer(localname);
			
			TPtFwrzxxxxBO t = new TPtFwrzxxxxBO();
			t.setFwrzjbid(uuid);
			Date startTime = new Date();
			
			t.setStartTime(ServiceConstants.sdf.format(startTime));
			t.setObj("服务端本地鉴权");
			t.setFwrzxxid(UUID.randomUUID().toString());
			
			RequestContext requestContext = new RequestContext();
			requestContext.setExcuteServicePath(excuteServicePath);
			requestContext.setServiceId(uuid);
			requestContext.setParams(params);
			//如果是数据中心的获取权限和通知服务，不做权限验证
			if(localname.equals(ServiceConstants.DATA_CENTER_NAME)){
				t.setDetail(requestContext.toString());
				try{
					if(requestContext.getServiceName().equals(ServiceConstants.DATA_CENTER_AUTH_CHECK)){
						requestContext.setExcuteServicePath(ServiceConstants.DATA_CENTER_AUTH_CLASS);
					}else{
						requestContext.setExcuteServicePath(ServiceConstants.DATA_CENTER_NOTICE_CLASS);
					}
					SyncServiceWithTimeout.callSyncWithTimout(requestContext);
					resultList = SyncServiceWithTimeout.rsresult;
					t.setCode("0");
					t.setExecutecontent("成功");
				}catch (Exception e) {
					t.setCode("1");
					t.setExecutecontent("失败");
					e.printStackTrace();
					throw new BrokerException(e.getMessage());
				}finally{
					Date endTime =new Date(); 
					long time = endTime.getTime()-startTime.getTime();
					t.setEndTime(ServiceConstants.sdf.format(endTime));
					t.setTime(String.valueOf(time));
					logK.info(t);
				}
			}else{
				t.setDetail("请求地址："+url+" 【IP】："+ip);
				boolean flag = false;
				try {//调用权限判断
					log.info("请求地址："+url+" 【IP】："+ip);
					flag = ServiceAuth.servicePermissionCheck(url,ip);
					log.info("是否有权限访问："+flag);
					log.info("权限缓存:"+ServiceAuth.getAuth().toString());
					t1.setCallername(ServiceAuth.getAuth().get("serviceobjectname"));
					t1.setFwmc(ServiceAuth.getAuth().get("servicename"));
					if(!flag){
						t.setCode("1");
						t.setExecutecontent("鉴权失败,没有权限！");
					}else{
						t.setCode("0");
						t.setExecutecontent("鉴权成功");
					}
				}catch (Exception e) {
					t.setCode("1");
					t.setExecutecontent("鉴权失败");
					e.printStackTrace();
					throw new BrokerException(e.getMessage());
				}finally{
					Date endTime =new Date(); 
					long time = endTime.getTime()-startTime.getTime();
					t.setEndTime(ServiceConstants.sdf.format(endTime));
					t.setTime(String.valueOf(time));
					logK.info(t);
				}
				if(flag){
					t = new TPtFwrzxxxxBO();
					t.setFwrzjbid(uuid);
					startTime = new Date();
					
					t.setStartTime(ServiceConstants.sdf.format(startTime));
					t.setObj("服务端发送服务请求");
					t.setFwrzxxid(UUID.randomUUID().toString());
					t.setDetail(params);
					t.setDetail(requestContext.toString());
					try {
						//requestContext.setRequestorIp(hreq.getRemoteHost());//设置调用服务的ip地址
						requestContext.setTimeOut(ServiceAuth.getAuth().get("defaulttime"));
						requestContext.setServerModel(ServiceAuth.getAuth().get("executetype"));
						requestContext.setExcuteServicePath(ServiceAuth.getAuth().get("invokeclass"));
						SyncServiceWithTimeout.callSyncWithTimout(requestContext);
						resultList = SyncServiceWithTimeout.rsresult;
						t.setCode("0");
						t.setExecutecontent("成功");
					}catch (Exception e) {
						t.setCode("1");
						t.setExecutecontent("失败");
						e.printStackTrace();
						throw new BrokerException(e.getMessage());
					}finally{
						Date endTime =new Date(); 
						long time = endTime.getTime()-startTime.getTime();
						t.setEndTime(ServiceConstants.sdf.format(endTime));
						t.setTime(String.valueOf(time));
						logK.info(t);
					}
				}
			}
			t1.setExecuteresult("成功");
		} catch (Exception e) {
			t1.setExecuteresult("失败");
			e.printStackTrace();
		}finally{
			Date callerenttime =new Date(); 
			long executetime = callerenttime.getTime()-date.getTime();
			t1.setCallerenttime(ServiceConstants.sdf.format(callerenttime));
			t1.setExecutetime(String.valueOf(executetime));
			logK.info(t1);
		}
		if(resultList!=null && resultList.size()>0){
			for(int i=0;i<resultList.size();i++){
				returnString += resultList.get(i);
			}
		}else{
			returnString = "";
		}
		log.info("获取返回结果的数据参数："+returnString);
        return returnString;
	}
    
    /**
     * 特殊服务，参数不做任何处理（包括框架参数解析）
     * @param excuteServicePath
     * @param xml
     * @return
     */
    @POST
    @Path(value = "/specialSynUnifiedService/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String specialSynUnifiedServicePost(@PathParam("excuteServicePath")String excuteServicePath,String xml) throws BrokerException{
    	//log.info("Method:【" + request.getMethod()+"】>>>uri:【" + uriInfo.getPath()+"】>>参数:【"+uriInfo.getPathParameters()+ "】"+"【excuteServicePath"+"】"+excuteServicePath+"【xml"+"】"+xml);
        return specialSynUnifiedService(excuteServicePath,xml);
	}
    
    /**
     * 同步请求
     * @param xml
     * @return
     */
    @GET
    @Path(value = "/synUnifiedService/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String synUnifiedService(@PathParam("excuteServicePath")String excuteServicePath,@QueryParam("xml")String xml) {
    	log.info("Method:【" + request.getMethod()+"】>>>uri:【" + uriInfo.getPath()+"】>>参数:【"+uriInfo.getPathParameters()+ "】"+"【excuteServicePath"+"】"+excuteServicePath+"【xml"+"】"+xml);
    	Date date = new Date();
    	TPtFwrzxxxxBO t = new TPtFwrzxxxxBO();
    	String returnString="";
        SynReponseContext reponseContext = new SynReponseContext();
		try {
			//日志
			t.setStartTime(ServiceConstants.sdf.format(date));
			t.setObj("服务端收到请求");
			t.setFwrzxxid(UUID.randomUUID().toString());
			t.setDetail(xml);
			
			String ip = GetIPAddressUtil.getIpAddress(hreq);
	        String url = hreq.getRequestURL().toString();
			RequestContext requestContext = new RequestContext();
			requestContext =(RequestContext) DataHandler.xml2Object(xml, requestContext);
			requestContext.setExcuteServicePath(excuteServicePath);
			//requestContext.setObjectName();
			//如果是数据中心的获取权限和通知服务，不做权限验证
			t.setFwrzjbid(requestContext.getServiceId());
			reponseContext.setServiceId(requestContext.getServiceId());
			String localname = (String) ServiceBrokerProperties.serviceBrokerProp.get("localname");
			TPtFwrzxxxxBO t1 = new TPtFwrzxxxxBO();
			if((localname.equals(ServiceConstants.DATA_CENTER_NAME)) && (requestContext.getServiceName().equals(ServiceConstants.DATA_CENTER_AUTH_CHECK) || requestContext.getServiceName().equals(ServiceConstants.DATA_CENTER_NOTICE))){
				t1.setFwrzjbid(requestContext.getServiceId());
				Date startTime = new Date();
				
				t1.setStartTime(ServiceConstants.sdf.format(startTime));
				t1.setObj("服务端执行数据处理请求");
				t1.setFwrzxxid(UUID.randomUUID().toString());
				t1.setDetail(requestContext.toString());
				try {
					SyncServiceWithTimeout.callSyncWithTimout(requestContext);
					reponseContext.setResponseResult(SyncServiceWithTimeout.rsresult);
					reponseContext.setCode("0");
					reponseContext.setDesc("获取数据成功！");
					t1.setCode("0");
					t1.setExecutecontent("成功");
				}finally{
					Date endTime =new Date(); 
					long time = endTime.getTime()-startTime.getTime();
					t1.setEndTime(ServiceConstants.sdf.format(endTime));
					t1.setTime(String.valueOf(time));
					logK.info(t1);
				}
			}else{
				t1.setFwrzjbid(requestContext.getServiceId());
				Date startTime = new Date();
				t1.setStartTime(ServiceConstants.sdf.format(startTime));
				t1.setObj("服务端本地鉴权");
				t1.setFwrzxxid(UUID.randomUUID().toString());
				t1.setDetail("服务地址："+url+" IP："+ip);
				try {//调用权限判断
					boolean flag = ServiceAuth.servicePermissionCheck(url,ip);
					if(!flag){
						reponseContext.setCode("1");
						reponseContext.setDesc("权限不足，请查看是否有权访问该服务！");
						t1.setExecutecontent("鉴权失败");
						t1.setCode("1");
						throw new BrokerException(reponseContext.getDesc());
					}
					t1.setCode("0");
					t1.setExecutecontent("鉴权成功");
				}catch (Exception e) {
					t1.setCode("1");
					t1.setExecutecontent("鉴权失败");
					e.printStackTrace();
					throw new BrokerException(e.getMessage());
				}finally{
					Date endTime =new Date(); 
					long time = endTime.getTime()-startTime.getTime();
					t1.setEndTime(ServiceConstants.sdf.format(endTime));
					t1.setTime(String.valueOf(time));
					logK.info(t1);
				}
				t1 = new TPtFwrzxxxxBO();
				t1.setFwrzjbid(requestContext.getServiceId());
				startTime = new Date();
				
				t1.setStartTime(ServiceConstants.sdf.format(startTime));
				t1.setObj("服务端执行数据处理请求");
				t1.setFwrzxxid(UUID.randomUUID().toString());
				t1.setDetail(requestContext.toString());
				try {
					//requestContext.setRequestorIp(hreq.getRemoteHost());//设置调用服务的ip地址
					requestContext.setTimeOut(ServiceAuth.getAuth().get("defaulttime"));
					requestContext.setServerModel(ServiceAuth.getAuth().get("executetype"));
					requestContext.setExcuteServicePath(ServiceAuth.getAuth().get("invokeclass"));
					SyncServiceWithTimeout.callSyncWithTimout(requestContext);
					reponseContext.setResponseResult(SyncServiceWithTimeout.rsresult);
					reponseContext.setCode("0");
					reponseContext.setDesc("获取数据成功！");
					t1.setCode("0");
					t1.setExecutecontent("成功");
				}catch (Exception e) {
					t1.setCode("1");
					t1.setExecutecontent("失败");
					e.printStackTrace();
					throw new BrokerException(e.getMessage());
				}finally{
					Date endTime =new Date(); 
					long time = endTime.getTime()-startTime.getTime();
					t1.setEndTime(ServiceConstants.sdf.format(endTime));
					t1.setTime(String.valueOf(time));
					logK.info(t1);
				}
			}
			t.setCode("0");
			t.setExecutecontent("成功");
		} catch (NoSuchMethodException e) {
			t.setCode("1");
			t.setExecutecontent("失败");
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode("005");
			reponseContext.setErrorMsg("xml字符串转化为实体bean异常-NoSuchMethodException:"+e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			t.setCode("1");
			t.setExecutecontent("失败");
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode("005");
			reponseContext.setErrorMsg("xml字符串转化为实体bean异常-SecurityException:"+e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			t.setCode("1");
			t.setExecutecontent("失败");
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode("005");
			reponseContext.setErrorMsg("xml字符串转化为实体bean异常-IllegalAccessException:"+e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			t.setCode("1");
			t.setExecutecontent("失败");
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode("005");
			reponseContext.setErrorMsg("xml字符串转化为实体bean异常-IllegalArgumentException:"+e.getMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			t.setCode("1");
			t.setExecutecontent("失败");
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode("005");
			reponseContext.setErrorMsg("xml字符串转化为实体bean异常-InvocationTargetException:"+e.getMessage());
			e.printStackTrace();
		} catch (DocumentException e) {
			t.setCode("1");
			t.setExecutecontent("失败");
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode("005");
			reponseContext.setErrorMsg("xml字符串转化为实体bean异常-DocumentException:"+e.getMessage());
			e.printStackTrace();
		} catch (BrokerException e) {
			t.setCode("1");
			t.setExecutecontent("失败");
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode(e.getCode());
			reponseContext.setErrorMsg("【"+e.getMsg()+"】"+e.getMsgDes());
			e.printStackTrace();
		}finally{
			Date endTime =new Date(); 
			long time = endTime.getTime()-date.getTime();
			t.setEndTime(ServiceConstants.sdf.format(endTime));
			t.setTime(String.valueOf(time));
			logK.info(t);
			try {
				returnString = DataHandler.Object2xml(reponseContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("获取返回结果的数据参数："+returnString);
        return returnString;
	}
    
    /**
     * 同步请求
     * @param xml
     * @return
     */
    @POST
    @Path(value = "/synUnifiedService/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String synUnifiedServicePost(@PathParam("excuteServicePath")String excuteServicePath,String xml) {
    	//log.info("Method:【" + request.getMethod()+"】>>>uri:【" + uriInfo.getPath()+"】>>参数:【"+uriInfo.getPathParameters()+ "】"+"【excuteServicePath"+"】"+excuteServicePath+"【xml"+"】"+xml);
        return synUnifiedService(excuteServicePath,xml);
	}
    
    /**
     * 异步请求
     * @param xml
     * @return
     */
    @GET
    @Path(value = "/asynUnifiedService/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String asynUnifiedService(@PathParam("excuteServicePath")String excuteServicePath,@QueryParam("xml")String xml) {
    	log.info("Method:【" + request.getMethod()+"】>>>uri:【" + uriInfo.getPath()+"】>>参数:【"+uriInfo.getPathParameters()+ "】"+"【excuteServicePath"+"】"+excuteServicePath+"【xml"+"】"+xml);
		
        String returnString="";
        ReponseContextBase reponseContext = new ReponseContextBase();
        String ip = GetIPAddressUtil.getIpAddress(hreq);
        String url = hreq.getRequestURL().toString();
		try {
			RequestContext requestContext = new RequestContext();
			requestContext =(RequestContext) DataHandler.xml2Object(xml, requestContext);
			requestContext.setExcuteServicePath(excuteServicePath);
			//调用权限判断
			TPtFwrzxxxxBO t = new TPtFwrzxxxxBO();
			String uuid = UUID.randomUUID().toString();
			t.setFwrzxxid(uuid);
			t.setDetail(xml);
			t.setFwrzjbid(requestContext.getServiceId());
			t.setExecutecontent("服务端"+requestContext.getParams());
			t.setTime(ServiceConstants.sdf.format(new Date()));
			logK.info(t);
			reponseContext.setServiceId(requestContext.getServiceId());
			boolean flag = ServiceAuth.servicePermissionCheck(url,ip);
			if(!flag){
				reponseContext.setCode("1");
				reponseContext.setDesc("权限不足，请查看是否有权访问该服务！");
			}else{
				//requestContext.setRequestorIp(hreq.getRemoteHost());//设置调用服务的ip地址
				requestContext.setExcuteServicePath(ServiceAuth.getAuth().get("invokeclass"));
				AsyncServiceWithTimeout.callAsyncWithTimout(requestContext);
				reponseContext.setCode("0");
				reponseContext.setDesc("异步调用成功！");
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode("005");
			reponseContext.setErrorMsg("xml字符串转化为实体bean异常-NoSuchMethodException:"+e.getMessage());
		} catch (SecurityException e) {
			e.printStackTrace();
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode("005");
			reponseContext.setErrorMsg("xml字符串转化为实体bean异常-SecurityException:"+e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode("005");
			reponseContext.setErrorMsg("xml字符串转化为实体bean异常-IllegalAccessException:"+e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode("005");
			reponseContext.setErrorMsg("xml字符串转化为实体bean异常-IllegalArgumentException:"+e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode("005");
			reponseContext.setErrorMsg("xml字符串转化为实体bean异常-InvocationTargetException:"+e.getMessage());
		} catch (DocumentException e) {
			e.printStackTrace();
			reponseContext.setCode("1");
			reponseContext.setDesc("获取数据失败！");
			reponseContext.setErrorCode("005");
			reponseContext.setErrorMsg("xml字符串转化为实体bean异常-DocumentException:"+e.getMessage());
		}finally{
			try {
				returnString = DataHandler.Object2xml(reponseContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        return returnString;
	}
    
    /**
     * 异步请求
     * @param xml
     * @return
     */
    @POST
    @Path(value = "/asynUnifiedService/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String asynUnifiedServicePost(@PathParam("excuteServicePath")String excuteServicePath,String xml) {
    	//log.info("Method:【" + request.getMethod()+"】>>>uri:【" + uriInfo.getPath()+"】>>参数:【"+uriInfo.getPathParameters()+ "】"+"【excuteServicePath"+"】"+excuteServicePath+"【xml"+"】"+xml);
        return asynUnifiedService(excuteServicePath,xml);
	}
    
    @GET
    @Path(value = "/{id}/test")
    @Produces({ MediaType.APPLICATION_JSON })//MediaType.TEXT_PLAIN,MediaType.APPLICATION_XML
	public String test(@PathParam("id")String id,@QueryParam("name")String xml) {
    	log.info("Method:【" + request.getMethod()+"】>>>uri:【" + uriInfo.getPath()+"】>>参数:【"+uriInfo.getPathParameters()+ "】");
        System.out.println("@GET"+id);
        System.out.println("@GET"+xml);
        System.out.println("结束");
    	return xml;
	}
    
    @POST
    @Path(value = "/{id}/test")
    @Produces({MediaType.APPLICATION_JSON })
	public String test1(@PathParam("id")String id,String xml) {
    	//log.info("Method:【" + request.getMethod()+"】>>>uri:【" + uriInfo.getPath()+"】>>参数:【"+uriInfo.getPathParameters()+ "】");
        System.out.println("@POST"+id);
        System.out.println("@POST"+xml);
        test(id,xml);
    	return xml;
	}
    
    @GET
    @Path(value = "/testApp/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String testApp(@PathParam("excuteServicePath")String excuteServicePath,@QueryParam("xml")String xml) {
    	log.info("Method:【" + request.getMethod()+"】>>>uri:【" + uriInfo.getPath()+"】>>参数:【"+uriInfo.getPathParameters()+ "】");
    	//如果是post请求hreq.getQueryString() 不能获取参数，从xml获取
    	String params = "";
    	if("POST".equalsIgnoreCase(request.getMethod().toUpperCase())){
    		params = xml;
    	}else{
    		params = hreq.getQueryString();
    	}
    	log.info(request.getMethod()+"==="+params);
    	String returnStr="";
    	if(GetIPAddressUtil.getIpAddress(hreq).equals(ServiceConstants.DATA_CENTER_IP)){//访问的ip地址是数据服务中心的才可以
    		returnStr ="success";
        }else{
        	returnStr ="fail";
        }
    	return returnStr;
	}
    
    @POST
    @Path(value = "/testApp/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String testAppPost(@PathParam("excuteServicePath")String excuteServicePath,String xml) {
    	//log.info("Method:【" + request.getMethod()+"】>>>uri:【" + uriInfo.getPath()+"】>>参数:【"+uriInfo.getPathParameters()+ "】");
    	return testApp(excuteServicePath,xml);
	}
	
}