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
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ExcuteClient ���������ͻ��˵�ִ�з��� �����ˣ�lizheng ����ʱ�䣺Apr 2, 2013
 * 3:53:30 PM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 2, 2013 3:53:30 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class SocketExcuteClient extends SocketAbsGeneralClient
{
	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(SocketExcuteClient.class.getName());
	String result=null;
	public String colGetDom(Map param, CollectLogVo collectLogVo)
	{
		logger.debug("��ʼ���öԷ�socket�����ṩ�Ĳɼ����ݵķ���...");
		Long start = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
		String targetEendPoint = ""; // ·��
		if (null != param.get(CollectConstants.WSDL_URL)
				&& !"".equals(param.get(CollectConstants.WSDL_URL).toString())) {
			targetEendPoint = param.get(CollectConstants.WSDL_URL).toString();
			param.remove(CollectConstants.WSDL_URL);
		}
		logger.debug("���÷���·��Ϊ :" + targetEendPoint);
		String ip = ""; // IP
		if (null != param.get(CollectConstants.WSDL_IP)
				&& !"".equals(param.get(CollectConstants.WSDL_IP).toString())) {
			ip = param.get(CollectConstants.WSDL_IP).toString();
			param.remove(CollectConstants.WSDL_IP);
		}
		logger.debug("���÷���IPΪ :" + ip);
		
		String port = ""; // PORT
		if (null != param.get(CollectConstants.WSDL_PORT)
				&& !"".equals(param.get(CollectConstants.WSDL_PORT).toString())) {
			port = param.get(CollectConstants.WSDL_PORT).toString();
			param.remove(CollectConstants.WSDL_PORT);
		}
		logger.debug("���÷���portΪ :" + port);
		String qName = ""; // ����
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
		System.out.println("ִ�з�������Ϊ :" + qName);
		System.out.println("������Ϊ :" + paramName);
		System.out.println("����ֵΪ :" + ValueMap);
		
	
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
				logger.debug("webService�������..." + e);
				e.printStackTrace();
				return XmlToMapUtil.map2Dom(ResultFormat.createWsError());
			} finally {

				Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
				String consumeTime = String.valueOf(((end - start) / 1000f));
				logger.debug("���÷�����������յ��Է����ݹ���ʱ��" + consumeTime + "�룡");
			}
		} else {
			// ���÷���·���ͷ������ƴ���
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_TASK_ERROR);
			logger.debug("����webService���񷽷����ƻ����URL����...");
			return XmlToMapUtil.map2Dom(ResultFormat.createTaskError());
		}
	}

	
}
