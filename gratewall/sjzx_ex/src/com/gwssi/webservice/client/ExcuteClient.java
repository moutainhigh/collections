package com.gwssi.webservice.client;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：ExcuteClient 类描述：客户端的执行方法 创建人：lizheng 创建时间：Apr 2, 2013
 * 3:53:30 PM 修改人：lizheng 修改时间：Apr 2, 2013 3:53:30 PM 修改备注：
 * 
 * @version
 * 
 */
public class ExcuteClient extends AbsGeneralClient
{
	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(ExcuteClient.class
											.getName());

	public String collectData(Map param, CollectLogVo collectLogVo, Map countMap)
	{
		logger.debug("开始调用对方webservice服务提供的采集数据的方法...");
		Long start = System.currentTimeMillis(); // 结束时间用于计算耗时
		String targetEendPoint = ""; // 路径
		if (null != param.get(CollectConstants.WSDL_URL)
				&& !"".equals(param.get(CollectConstants.WSDL_URL).toString())) {
			targetEendPoint = param.get(CollectConstants.WSDL_URL).toString();
		}
		String qName = ""; // 参数
		if (null != param.get(CollectConstants.QNAME)
				&& !"".equals(param.get(CollectConstants.QNAME).toString())) {
			qName = param.get(CollectConstants.QNAME).toString();
		}
		String nameSpace = "";
		if (null != param.get(CollectConstants.WEB_NAME_SPACE)
				&& !"".equals(param.get(CollectConstants.WEB_NAME_SPACE)
						.toString())) {
			nameSpace = param.get(CollectConstants.WEB_NAME_SPACE).toString();
		}
		String paramName = "";
		//System.out.println("---"+param);
		if (null != param.get(CollectConstants.PARAM_LIST)) {
			List paramNameList = (List) param.get(CollectConstants.PARAM_LIST);
			//System.out.println("-----------"+paramNameList);
			if(paramNameList.size()>0){
				paramName = paramNameList.get(0).toString();
			}else {
				logger.info("参数列表为空");
			}
			
		}

		Map ValueMap = new HashMap();
		if (null != param.get(CollectConstants.PARAM_VALUE)) {
			Map paramValueMap = (Map) param.get(CollectConstants.PARAM_VALUE);
			ValueMap = (Map) paramValueMap.get(paramName);
			if (null != countMap.get("isDo")
					&& "Y".equals(countMap.get("isDo").toString())) {
				logger.debug("由于数据总条数过大，必须分多次取数据");
				ValueMap.put(CollectConstants.KSJLS, countMap
						.get(CollectConstants.KSJLS));
				ValueMap.put(CollectConstants.JSJLS, countMap
						.get(CollectConstants.JSJLS));
			}
		}
		logger.debug("调用访问路径为 :" + targetEendPoint);
		logger.debug("执行方法名称为 :" + qName);
		
		logger.debug("参数名为 :" + paramName);
		logger.debug("参数值为 :" + ValueMap);
		if (!"".equals(targetEendPoint) && !"".equals(qName)) {
			Service service = new Service();
			try {
				Call call = (Call) service.createCall();
				call.setTimeout(300000);
				logger.debug("设置超时时间为 :" + 300000 / 1000f + "秒");
				call.setTargetEndpointAddress(new URL(targetEendPoint));
				if(nameSpace!=null && nameSpace!=""){
					logger.debug("命名空间为 :" + nameSpace);
					call.setOperationName(new QName(nameSpace, qName));
					call.addParameter(new QName(nameSpace, paramName),
							org.apache.axis.encoding.XMLType.XSD_STRING,
							javax.xml.rpc.ParameterMode.IN);
				}else{
					//若命名空间未设置，使用另一种方式访问服务
					logger.debug("未设置命名空间，使用默认访问方式");
					call.setOperation(qName);
					call.addParameter(paramName,
							org.apache.axis.encoding.XMLType.XSD_STRING,
							javax.xml.rpc.ParameterMode.IN);
				}
				
				call.setReturnType(org.apache.axis.Constants.XSD_STRING);
				call.setUseSOAPAction(true);
				String dom ="";
				if(ValueMap!=null){
					dom = XmlToMapUtil.map2Dom(ValueMap);
				}else{
					dom ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><params></params>";
				}
				
				//System.out.println("dom="+dom);
				collectLogVo.setPatameter(dom);
				String result = (String) call.invoke(new Object[] { dom });
				return result;
			} catch (ServiceException e) {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_WS_ERROR);
				logger.debug("webService服务错误..." + e);
				e.printStackTrace();
				return XmlToMapUtil.map2Dom(ResultFormat.createWsError());
			} catch (MalformedURLException e) {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_WS_URL_ERROR);
				logger.debug("webService服务URL错误..." + e);
				e.printStackTrace();
				return XmlToMapUtil.map2Dom(ResultFormat.createWsUrlError());
			} catch (RemoteException e) {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_INVOKE_ERROR);
				logger.debug("调用webService服务错误..." + e);
				e.printStackTrace();
				return XmlToMapUtil.map2Dom(ResultFormat.createInvokeError());
			} finally {
				Long end = System.currentTimeMillis(); // 结束时间用于计算耗时
				String consumeTime = String.valueOf(((end - start) / 1000f));
				logger.debug("调用服务操作并接收到对方数据共耗时：" + consumeTime + "秒！");
			}
		} else {
			// 调用方法路径和方法名称错误
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_TASK_ERROR);
			logger.debug("调用webService服务方法名称或访问URL错误...");
			return XmlToMapUtil.map2Dom(ResultFormat.createTaskError());
		}
	}

	public Map collectQualitySupervisionData(Map param,
			CollectLogVo collectLogVo)
	{
		logger.debug("开始调用质监的webservice服务提供的采集数据的方法...");
		Long start = System.currentTimeMillis(); // 结束时间用于计算耗时
		String targetEendPoint = ""; // 路径
		if (null != param.get(CollectConstants.WSDL_URL)
				&& !"".equals(param.get(CollectConstants.WSDL_URL).toString())) {
			targetEendPoint = param.get(CollectConstants.WSDL_URL).toString();
		}
		String qName = ""; // 参数
		if (null != param.get(CollectConstants.QNAME)
				&& !"".equals(param.get(CollectConstants.QNAME).toString())) {
			qName = param.get(CollectConstants.QNAME).toString();
		}
		String nameSpace = "";
		if (null != param.get(CollectConstants.WEB_NAME_SPACE)
				&& !"".equals(param.get(CollectConstants.WEB_NAME_SPACE)
						.toString())) {
			nameSpace = param.get(CollectConstants.WEB_NAME_SPACE).toString();
		}
		String paramName = "";
		if (null != param.get(CollectConstants.PARAM_LIST)) {
			paramName = param.get(CollectConstants.PARAM_LIST).toString();
		}
		String paramVlaue = "";
		if (null != param.get(CollectConstants.PARAM_VALUE)) {
			paramVlaue = param.get(CollectConstants.PARAM_VALUE).toString();
		}
		logger.debug("调用访问路径为 :" + targetEendPoint);
		logger.debug("执行方法名称为 :" + qName);
		logger.debug("命名空间为 :" + nameSpace);
		logger.debug("参数名为 :" + paramName);
		logger.debug("参数值为 :" + paramVlaue);
		if (!"".equals(targetEendPoint) && !"".equals(qName)) {
			Service service = new Service();
			try {
				Call call = (Call) service.createCall();
				call.setTimeout(300000);
				logger.debug("设置超时时间为 :" + 300000 / 1000f + "秒");
				call.setTargetEndpointAddress(new URL(targetEendPoint));
				call.setOperationName(new QName(nameSpace, qName));

				call.addParameter(new QName("", paramName),
						org.apache.axis.encoding.XMLType.XSD_BASE64,
						javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.Constants.XSD_BASE64);
				call.setUseSOAPAction(true);
				logger.debug("input is " + paramVlaue);
				byte[] dom = paramVlaue.getBytes("UTF-8");
				byte[] result = (byte[]) call.invoke(new Object[] { dom });
				logger.debug("result length is " + result.length);
				String outputString = new String(result, "UTF-8");
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_SUCCESS);
				Map resultMap = ResultFormat.createCollectSuccess();
				resultMap.put("RESULT", outputString);
				return resultMap;
			} catch (UnsupportedEncodingException e) {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_WS_ERROR);
				logger.debug("UnsupportedEncodingException错误..." + e);
				e.printStackTrace();
				return ResultFormat.createWsError();
			} catch (ServiceException e) {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_WS_ERROR);
				logger.debug("webService服务错误..." + e);
				e.printStackTrace();
				return ResultFormat.createWsError();
			} catch (MalformedURLException e) {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_WS_URL_ERROR);
				logger.debug("webService服务URL错误..." + e);
				e.printStackTrace();
				return ResultFormat.createWsUrlError();
			} catch (RemoteException e) {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_INVOKE_ERROR);
				logger.debug("调用webService服务错误..." + e);
				e.printStackTrace();
				return ResultFormat.createInvokeError();
			} finally {
				Long end = System.currentTimeMillis(); // 结束时间用于计算耗时
				String consumeTime = String.valueOf(((end - start) / 1000f));
				logger.debug("调用服务操作并接收到对方数据共耗时：" + consumeTime + "秒！");
			}
		} else {
			// 调用方法路径和方法名称错误
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_TASK_ERROR);
			logger.debug("调用webService服务方法名称或访问URL错误...");
			return ResultFormat.createTaskError();
		}
	}

	public String collectSocialSecurityData(Map param, CollectLogVo collectLogVo)
	{
		logger.debug("开始调用对方webservice服务提供的采集数据的方法...");
		Long start = System.currentTimeMillis(); // 结束时间用于计算耗时
		String targetEendPoint = ""; // 路径
		if (null != param.get(CollectConstants.WSDL_URL)
				&& !"".equals(param.get(CollectConstants.WSDL_URL).toString())) {
			targetEendPoint = param.get(CollectConstants.WSDL_URL).toString();
			param.remove(CollectConstants.WSDL_URL);
		}
		logger.debug("调用访问路径为 :" + targetEendPoint);
		String qName = ""; // 参数
		if (null != param.get(CollectConstants.QNAME)
				&& !"".equals(param.get(CollectConstants.QNAME).toString())) {
			qName = param.get(CollectConstants.QNAME).toString();
			param.remove(CollectConstants.QNAME);
		}
		logger.debug("执行方法名称为 :" + qName);
		// String targetEendPoint =
		// "http://172.24.15.18/JHKWebService/Service.asmx?wsdl";
		// String qName = "JHKDataExchange";
		String nameSpace = "http://microsoft.com/webservices/";
		Service service = new Service();
		try {

			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(targetEendPoint);
			call.setOperationName(new QName(nameSpace, qName));

			call.addParameter(new QName(nameSpace, "DataConfig"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter(new QName("http://microsoft.com/webservices/",
					"parameter"), org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.Constants.XSD_STRING);
			call.setUseSOAPAction(true);
			// call.setSOAPActionURI(nameSpace + qName);
			call
					.setSOAPActionURI("http://microsoft.com/webservices/JHKDataExchange");

			// String DataConfig = "61";
			String DataConfig = param.get("DataConfig").toString();
			// 用户名+“&”+密码+“&”+查询日期起+“&”+查询日期止

			String parameter = param.get("parameter").toString();
			// String startTime = "2013-05-06 00:00:00";
			// String endTime = "2013-05-06 12:00:00";
			// String parameter = "gsj_user&2w3u5ub2&" + startTime + "&" +
			// endTime;
			// System.out.println("参数为: " + parameter);
			String result = (String) call.invoke(new Object[] { DataConfig,
					parameter });
			System.out.println("解密前数据");
			System.out.println(result);
			System.out.println("解密前数据");
			return result;
		} catch (ServiceException e) {
			collectLogVo.setReturn_codes(CollectConstants.CLIENT_FHDM_WS_ERROR);
			logger.debug("webService服务错误..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createWsError());
		} catch (RemoteException e) {
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_INVOKE_ERROR);
			logger.debug("调用webService服务错误..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createInvokeError());
		} finally {
			Long end = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("调用服务操作并接收到对方数据共耗时：" + consumeTime + "秒！");
		}
	}
}
