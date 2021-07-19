package cn.gwssi.broker.server.auth;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import cn.gwssi.common.model.RequestContext;
import cn.gwssi.common.model.SynReponseContext;
import cn.gwssi.common.model.TPtFwrzxxxxBO;
import cn.gwssi.common.resource.DataHandler;
import cn.gwssi.common.resource.ServiceConstants;

/**
 * 同步结果线程处理
 * @author xue
 * @version 1.0
 * @since 2016/5/15
 */
public class SynServiceClientTaskWithResult implements Callable<Object> {
	
	private RequestContext requestParam;
	
	private static Logger log = Logger.getLogger("kafka");
	public SynServiceClientTaskWithResult(RequestContext requestParam){
		this.requestParam=requestParam;
	}
	
	@Override
	public Object call() {
		SynReponseContext synReponseContext = new SynReponseContext();
		try {
			String s = DataHandler.Object2xml(requestParam);
			//内部cxf调用
			/*TPtFwrzxxxxBO t = new TPtFwrzxxxxBO();
			String uuid = UUID.randomUUID().toString();
			t.setFwrzxxid(uuid);
			t.setDetail("内部cxf调用");
			t.setFwrzjbid(requestParam.getServiceId());
			t.setExecutecontent(requestParam.toString());
			t.setTime(ServiceConstants.sdf.format(new Date()));
			log.info(t);*/
			System.out.println(requestParam.getServiceUrl());
			//String xml=WebClient.create(requestParam.getServiceUrl()).path("/SOAService/synUnifiedService").post(s,String.class);
			String xml=WebClient.create(requestParam.getServiceUrl()).post(s,String.class);
			System.out.println(xml);
			synReponseContext = (SynReponseContext) DataHandler.xml2Object(xml, synReponseContext);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			synReponseContext.setCode("1");
			synReponseContext.setDesc("同步服务异常！");
			synReponseContext.setErrorCode("005");
			synReponseContext.setErrorMsg("xml字符串转化为实体bean异常-NoSuchMethodException:"+e.getMessage());
		} catch (SecurityException e) {
			e.printStackTrace();
			synReponseContext.setCode("1");
			synReponseContext.setDesc("同步服务异常！");
			synReponseContext.setErrorCode("005");
			synReponseContext.setErrorMsg("xml字符串转化为实体bean异常-SecurityException:"+e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			synReponseContext.setCode("1");
			synReponseContext.setDesc("同步服务异常！");
			synReponseContext.setErrorCode("005");
			synReponseContext.setErrorMsg("xml字符串转化为实体bean异常-IllegalAccessException:"+e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			synReponseContext.setCode("1");
			synReponseContext.setDesc("同步服务异常！");
			synReponseContext.setErrorCode("005");
			synReponseContext.setErrorMsg("xml字符串转化为实体bean异常-IllegalArgumentException:"+e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			synReponseContext.setCode("1");
			synReponseContext.setDesc("同步服务异常！");
			synReponseContext.setErrorCode("005");
			synReponseContext.setErrorMsg("xml字符串转化为实体bean异常-InvocationTargetException:"+e.getMessage());
		}catch (DocumentException e) {
			e.printStackTrace();
			synReponseContext.setCode("1");
			synReponseContext.setDesc("同步服务异常！");
			synReponseContext.setErrorCode("005");
			synReponseContext.setErrorMsg("xml字符串转化为实体bean异常-DocumentException:"+e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			synReponseContext.setCode("1");
			synReponseContext.setDesc("同步服务异常！");
			synReponseContext.setErrorCode("006");
			synReponseContext.setErrorMsg("服务内部组件连接异常:"+e.getMessage());
		}
		return synReponseContext;
	}

}
