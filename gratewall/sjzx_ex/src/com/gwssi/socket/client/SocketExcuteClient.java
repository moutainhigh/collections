package com.gwssi.socket.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.webservice.client.ResultFormat;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：ExcuteClient 类描述：客户端的执行方法 创建人：lizheng 创建时间：Apr 2, 2013
 * 3:53:30 PM 修改人：lizheng 修改时间：Apr 2, 2013 3:53:30 PM 修改备注：
 * 
 * @version
 * 
 */
public class SocketExcuteClient extends SocketAbsGeneralClient
{
	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(SocketExcuteClient.class.getName());
	String result=null;
	public String colGetDom(Map param, CollectLogVo collectLogVo)
	{
		logger.debug("开始调用对方socket服务提供的采集数据的方法...");
		Long start = System.currentTimeMillis(); // 结束时间用于计算耗时
		String targetEendPoint = ""; // 路径
		if (null != param.get(CollectConstants.WSDL_URL)
				&& !"".equals(param.get(CollectConstants.WSDL_URL).toString())) {
			targetEendPoint = param.get(CollectConstants.WSDL_URL).toString();
			param.remove(CollectConstants.WSDL_URL);
		}
		logger.debug("调用访问路径为 :" + targetEendPoint);
		String ip = ""; // IP
		if (null != param.get(CollectConstants.WSDL_IP)
				&& !"".equals(param.get(CollectConstants.WSDL_IP).toString())) {
			ip = param.get(CollectConstants.WSDL_IP).toString();
			param.remove(CollectConstants.WSDL_IP);
		}
		logger.debug("调用访问IP为 :" + ip);
		
		String port = ""; // PORT
		if (null != param.get(CollectConstants.WSDL_PORT)
				&& !"".equals(param.get(CollectConstants.WSDL_PORT).toString())) {
			port = param.get(CollectConstants.WSDL_PORT).toString();
			param.remove(CollectConstants.WSDL_PORT);
		}
		logger.debug("调用访问port为 :" + port);
		String qName = ""; // 参数
		if (null != param.get(CollectConstants.QNAME)
				&& !"".equals(param.get(CollectConstants.QNAME).toString())) {
			qName = param.get(CollectConstants.QNAME).toString();
			//param.remove(CollectConstants.QNAME);
		}
//		String nameSpace = "";
//		if (null != param.get(CollectConstants.WEB_NAME_SPACE)
//				&& !"".equals(param.get(CollectConstants.WEB_NAME_SPACE)
//						.toString())) {
//			nameSpace = param.get(CollectConstants.WEB_NAME_SPACE).toString();
//			param.remove(CollectConstants.WEB_NAME_SPACE);
//		}
		
		String paramName = "";
		if (null != param.get(CollectConstants.PARAM_LIST)) {
			List paramNameList = (List) param.get(CollectConstants.PARAM_LIST);
			paramName = paramNameList.get(0).toString();
		}

		Map ValueMap = new HashMap();
		if (null != param.get(CollectConstants.PARAM_VALUE)) {
			Map paramValueMap = (Map) param.get(CollectConstants.PARAM_VALUE);
			ValueMap = (Map) paramValueMap.get(paramName);
		}
		ValueMap.put(CollectConstants.QNAME, qName);
		System.out.println("执行方法名称为 :" + qName);
		System.out.println("参数名为 :" + paramName);
		System.out.println("参数值为 :" + ValueMap);
		
	
		if (!"".equals(ip)&& !"".equals(port) && !"".equals(qName)) {
			try {
				
				String dom = XmlToMapUtil.map2Dom(ValueMap);
				collectLogVo.setPatameter(dom);
				System.out.println("dom==========="+dom.replace("\n", ""));
				System.out.println("ip==========="+ip);
				System.out.println("port==========="+port);
				Socket socket = null;
				PrintWriter pw = null;
				BufferedReader br = null;
				try {
					socket = new Socket(ip, Integer.parseInt(port));
					pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					pw.println(dom.replace("\n", ""));
					pw.flush();
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					result = br.readLine();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;
			} catch (Exception e) {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_WS_ERROR);
				logger.debug("webService服务错误..." + e);
				e.printStackTrace();
				return XmlToMapUtil.map2Dom(ResultFormat.createWsError());
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

	
}
