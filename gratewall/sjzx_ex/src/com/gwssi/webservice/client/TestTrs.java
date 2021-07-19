package com.gwssi.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.gwssi.common.util.XmlToMapUtil;

public class TestTrs
{
	protected static Logger	logger	= TxnLogger.getLogger(Test.class.getName());

	public static void main(String[] args)
	{
		logger.debug("��ʼ�����������Է���...");
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
		System.out.println("��ǰʱ��Ϊ��" + startTime + "��ʼ�ɼ�����...");
		String targetEendPoint = "http://160.99.1.4:7002/services/GSWebService?wsdl";// ·��
		// String targetEendPoint =
		// "http://localhost:7001/services/GSWebService?wsdl";// ·��
		logger.info("����·��Ϊ..." + targetEendPoint);
		String qName = "queryTrsData";

		System.out.println("���ʷ���Ϊ..." + qName);
		Map paraMap = new HashMap();
		// paraMap.put("USER_TYPE", "TEST");
		paraMap.put("SVR_CODE", "trsService2");
		// *��¼ϵͳ���û���
		paraMap.put("LOGIN_NAME", "N_DZSW");
		// *����
		paraMap.put("PASSWORD", "b01c297c");
		paraMap.put("queryStr", "����");
		paraMap.put("search_num", "10");

		String param = XmlToMapUtil.map2Dom(paraMap);
		System.out.println("����Ϊ��" + param);
		try {

			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTimeout(300000);
			call.setTargetEndpointAddress(new URL(targetEendPoint));
			call.setOperationName(new QName(
					"http://160.99.1.4:7002/services/GSWebService", qName));
			call.addParameter(new QName(
					"http://160.99.1.4:7002/services/GSWebService", "xml"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.Constants.XSD_STRING);
			call.setUseSOAPAction(true);
			System.out.println("en222=====");
			String result = (String) call.invoke(new Object[] { param });
			System.out.println("end1=====");
			System.out.println("resultΪ..." + result);
		} catch (ServiceException e) {
			logger.debug("ServiceException..." + e);
			e.printStackTrace();
		} catch (MalformedURLException e) {
			logger.debug("MalformedURLException..." + e);
			e.printStackTrace();
		} catch (RemoteException e) {
			logger.debug("RemoteException..." + e);
			e.printStackTrace();
		}
	}

	// public static void main(String[] args){
	// String currenttime = "";
	// java.sql.Timestamp dateForFileName = new java.sql.Timestamp(new Date()
	// .getTime());
	// String time = dateForFileName.toString();
	// if (time != null && !"".equals(time)) {
	// String temp[] = time.split(" ");
	// String temp1[] = temp[0].split("-");
	// String temp2[] = temp[1].split(":");
	// for (int i = 0; i < temp1.length; i++) {
	// currenttime = currenttime + temp1[i];
	// }
	// }
	// System.out.println(currenttime);
	// }
}
