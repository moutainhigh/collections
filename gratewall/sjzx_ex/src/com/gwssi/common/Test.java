package com.gwssi.common;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
import cn.gwssi.common.component.logger.TxnLogger;

import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.webservice.client.ResultFormat;

public class Test
{
	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(Test.class.getName());

	public String testWeb()
	{
		Long start = System.currentTimeMillis(); // 结束时间用于计算耗时
		// 组装要访问服务的入口参数,带*为必填项
		Map paraMap = new HashMap();
		// *登录系统的用户名
		paraMap.put("LOGIN_NAME", "W_SYGTJJW");
		// *密码
		paraMap.put("PASSWORD", "2ba99665");
		// *开始记录数
		paraMap.put("KSJLS", "1");
		// *结束记录数(初始默认为5000条)
		paraMap.put("JSJLS", "100000");
		// *服务代码(由工商局提供)
		paraMap.put("SVR_CODE", "service45");
		// 数据更新日期大于等于2012-01-01，小于等于2013-01-01,测试时请将日期范围缩小以免数据量过大
		paraMap.put("UPDATE_DATE", "2000-09-27,2013-09-28");

		String targetEendPoint = "http://localhost:7002/services/GSWebService?wsdl";
		String qName = "queryData";
		String nameSpace = "http://localhost:7002/services/GSWebService";
		String paramName = "string";

		Service service = new Service();
		try {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(targetEendPoint));
			call.setOperationName(new QName(nameSpace, qName));
			call.addParameter(new QName(nameSpace, paramName),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.Constants.XSD_STRING);
			call.setUseSOAPAction(true);
			String dom = XmlToMapUtil.map2Dom(paraMap);
			logger.debug("参数为：" + dom);
			Long end1 = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consume1 = String.valueOf(end1 - start);

			String result = (String) call.invoke(new Object[] { dom });
			Long end2 = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consume2 = String.valueOf(end2 - end1);

			Map resultMap = XmlToMapUtil.dom2Map(result);
			Long end3 = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consume3 = String.valueOf(end3 - end2);

			String fhdm = resultMap.get("FHDM").toString();
			String zts = resultMap.get("ZTS").toString();
			String time1 = "准备耗时：" + consume1 + ",";
			String time2 = "调用SQL耗时：" + consume2 + ",";
			String time3 = "封装结果耗时：" + consume3 + ",";
			System.out.println("fhdm" + fhdm);
			System.out.println("zts" + zts);
			StringBuffer a = new StringBuffer();
			a.append("总条数为：");
			a.append(zts);
			a.append(", ");
			a.append(time1);
			a.append(time2);
			a.append(time3);
			logger.debug(a);
			return a.toString();
		} catch (ServiceException e) {
			logger.debug("webService服务错误..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createWsError());
		} catch (MalformedURLException e) {
			logger.debug("webService服务URL错误..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createWsUrlError());
		} catch (RemoteException e) {
			logger.debug("调用webService服务错误..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createInvokeError());
		} finally {
			Long end = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("调用服务操作并接收到对方数据共耗时：" + consumeTime + "秒！");
		}
	}

	public String testWeb1()
	{
		Long start = System.currentTimeMillis(); // 结束时间用于计算耗时
		// 组装要访问服务的入口参数,带*为必填项
		Map paraMap = new HashMap();
		// *登录系统的用户名
		paraMap.put("LOGIN_NAME", "N_DJXT");
		// *密码
		paraMap.put("PASSWORD", "a293f76d");
		// *开始记录数
		paraMap.put("KSJLS", "1");
		// *结束记录数(初始默认为5000条)
		paraMap.put("JSJLS", "3");
		// *服务代码(由工商局提供)
		paraMap.put("SVR_CODE", "service44");
		paraMap.put("REGNO", "445100400000141");

		// 数据更新日期大于等于2012-01-01，小于等于2013-01-01,测试时请将日期范围缩小以免数据量过大
		// paraMap.put("UPDATE_DATE", "2000-09-27,2013-09-28");

		String targetEendPoint = "http://localhost:7002/services/GSWebService?wsdl";
		String qName = "queryData";
		String nameSpace = "http://localhost:7002/services/GSWebService";
		String paramName = "string";

		Service service = new Service();
		try {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(targetEendPoint));
			call.setOperationName(new QName(nameSpace, qName));
			call.addParameter(new QName(nameSpace, paramName),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.Constants.XSD_STRING);
			call.setUseSOAPAction(true);
			String dom = XmlToMapUtil.map2Dom(paraMap);
			logger.debug("参数为：" + dom);
			Long end1 = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consume1 = String.valueOf(end1 - start);
			
			String result = (String) call.invoke(new Object[] { dom });
			Long end2 = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consume2 = String.valueOf(end2 - end1);

			Map resultMap = XmlToMapUtil.dom2Map(result);
			Long end3 = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consume3 = String.valueOf(end3 - end2);
			
			String fhdm = resultMap.get("FHDM").toString();
			String zts = resultMap.get("ZTS").toString();
			String time1 = "准备耗时：" + consume1 + ",";
			String time2 = "调用SQL耗时：" + consume2 + ",";
			String time3 = "封装结果耗时：" + consume3 + ",";
			System.out.println("fhdm" + fhdm);
			System.out.println("zts" + zts);
			StringBuffer a = new StringBuffer();
			a.append("总条数为：");
			a.append(zts);
			a.append(", ");
			a.append(time1);
			a.append(time2);
			a.append(time3);
			logger.debug(a);
			return a.toString();
		} catch (ServiceException e) {
			logger.debug("webService服务错误..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createWsError());
		} catch (MalformedURLException e) {
			logger.debug("webService服务URL错误..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createWsUrlError());
		} catch (RemoteException e) {
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
