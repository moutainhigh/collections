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
	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(Test.class.getName());

	public String testWeb()
	{
		Long start = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
		// ��װҪ���ʷ������ڲ���,��*Ϊ������
		Map paraMap = new HashMap();
		// *��¼ϵͳ���û���
		paraMap.put("LOGIN_NAME", "W_SYGTJJW");
		// *����
		paraMap.put("PASSWORD", "2ba99665");
		// *��ʼ��¼��
		paraMap.put("KSJLS", "1");
		// *������¼��(��ʼĬ��Ϊ5000��)
		paraMap.put("JSJLS", "100000");
		// *�������(�ɹ��̾��ṩ)
		paraMap.put("SVR_CODE", "service45");
		// ���ݸ������ڴ��ڵ���2012-01-01��С�ڵ���2013-01-01,����ʱ�뽫���ڷ�Χ��С��������������
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
			logger.debug("����Ϊ��" + dom);
			Long end1 = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consume1 = String.valueOf(end1 - start);

			String result = (String) call.invoke(new Object[] { dom });
			Long end2 = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consume2 = String.valueOf(end2 - end1);

			Map resultMap = XmlToMapUtil.dom2Map(result);
			Long end3 = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consume3 = String.valueOf(end3 - end2);

			String fhdm = resultMap.get("FHDM").toString();
			String zts = resultMap.get("ZTS").toString();
			String time1 = "׼����ʱ��" + consume1 + ",";
			String time2 = "����SQL��ʱ��" + consume2 + ",";
			String time3 = "��װ�����ʱ��" + consume3 + ",";
			System.out.println("fhdm" + fhdm);
			System.out.println("zts" + zts);
			StringBuffer a = new StringBuffer();
			a.append("������Ϊ��");
			a.append(zts);
			a.append(", ");
			a.append(time1);
			a.append(time2);
			a.append(time3);
			logger.debug(a);
			return a.toString();
		} catch (ServiceException e) {
			logger.debug("webService�������..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createWsError());
		} catch (MalformedURLException e) {
			logger.debug("webService����URL����..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createWsUrlError());
		} catch (RemoteException e) {
			logger.debug("����webService�������..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createInvokeError());
		} finally {
			Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("���÷�����������յ��Է����ݹ���ʱ��" + consumeTime + "�룡");
		}
	}

	public String testWeb1()
	{
		Long start = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
		// ��װҪ���ʷ������ڲ���,��*Ϊ������
		Map paraMap = new HashMap();
		// *��¼ϵͳ���û���
		paraMap.put("LOGIN_NAME", "N_DJXT");
		// *����
		paraMap.put("PASSWORD", "a293f76d");
		// *��ʼ��¼��
		paraMap.put("KSJLS", "1");
		// *������¼��(��ʼĬ��Ϊ5000��)
		paraMap.put("JSJLS", "3");
		// *�������(�ɹ��̾��ṩ)
		paraMap.put("SVR_CODE", "service44");
		paraMap.put("REGNO", "445100400000141");

		// ���ݸ������ڴ��ڵ���2012-01-01��С�ڵ���2013-01-01,����ʱ�뽫���ڷ�Χ��С��������������
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
			logger.debug("����Ϊ��" + dom);
			Long end1 = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consume1 = String.valueOf(end1 - start);
			
			String result = (String) call.invoke(new Object[] { dom });
			Long end2 = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consume2 = String.valueOf(end2 - end1);

			Map resultMap = XmlToMapUtil.dom2Map(result);
			Long end3 = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consume3 = String.valueOf(end3 - end2);
			
			String fhdm = resultMap.get("FHDM").toString();
			String zts = resultMap.get("ZTS").toString();
			String time1 = "׼����ʱ��" + consume1 + ",";
			String time2 = "����SQL��ʱ��" + consume2 + ",";
			String time3 = "��װ�����ʱ��" + consume3 + ",";
			System.out.println("fhdm" + fhdm);
			System.out.println("zts" + zts);
			StringBuffer a = new StringBuffer();
			a.append("������Ϊ��");
			a.append(zts);
			a.append(", ");
			a.append(time1);
			a.append(time2);
			a.append(time3);
			logger.debug(a);
			return a.toString();
		} catch (ServiceException e) {
			logger.debug("webService�������..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createWsError());
		} catch (MalformedURLException e) {
			logger.debug("webService����URL����..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createWsUrlError());
		} catch (RemoteException e) {
			logger.debug("����webService�������..." + e);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createInvokeError());
		} finally {
			Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("���÷�����������յ��Է����ݹ���ʱ��" + consumeTime + "�룡");
		}
	}
}
