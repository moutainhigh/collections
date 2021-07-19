package com.gwssi.webservice.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gwssi.webservice.client.wsdl.ComponentBuilder;
import com.gwssi.webservice.client.wsdl.OperationInfo;
import com.gwssi.webservice.client.wsdl.ParameterInfo;
import com.gwssi.webservice.client.wsdl.ServiceInfo;

import cn.gwssi.common.component.logger.TxnLogger;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�AnalyzeWsdl ������������wsdl �����ˣ�lizheng ����ʱ�䣺Apr 9, 2013
 * 5:19:32 PM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 9, 2013 5:19:32 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class AnalyzeWsdl
{
	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(AnalyzeWsdl.class
											.getName());

	/**
	 * 
	 * analyzeWsdl����wsdlUrl����wsdl�ļ�
	 * 
	 * @param wsdlUrl
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static Map analyzeWsdl(String wsdlUrl)
	{
		logger.debug("=========��ʼִ��analyzeWsdl����=========");
		Map resultMap = new HashMap();
		ComponentBuilder builder = new ComponentBuilder();
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setWsdllocation(wsdlUrl);
		try {
			serviceInfo = builder.buildserviceinformation(serviceInfo);
			logger.debug("=========����try========="+serviceInfo);
			if (null != serviceInfo) {
				resultMap = ResultFormat.createConnect();
			} else {
				resultMap = ResultFormat.createConnectFailed();
			}
		} catch (Exception e) {
			logger.debug("=========����Exception=========");
			e.printStackTrace();
			resultMap = ResultFormat.createConnectFailed();
		} finally {
			logger.debug(resultMap);
			return resultMap;
		}
	}

	/**
	 * 
	 * getService��ȡ�������Ϣ
	 * 
	 * @param wsdlUrl
	 * @return ServiceInfo
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static ServiceInfo getService(String wsdlUrl)
	{
		logger.debug("=========��ʼִ��getService����=========");
		ComponentBuilder builder = new ComponentBuilder();
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setWsdllocation(wsdlUrl);
		try {
			serviceInfo = builder.buildserviceinformation(serviceInfo);
		} catch (Exception e) {
			logger.debug("ServiceInfo ����");
			e.printStackTrace();
		}
		logger.debug("=========����getService����=========");
		return serviceInfo;
	}

	public static void main(String[] args)
	{
		int i = 0, j = 0;
		try {
			String wsdllocation = "C:\\Users\\lizheng\\Desktop\\new1.wsdl";
			ServiceInfo serviceInfo = getService(wsdllocation);
			System.out.println("getTargetnamespace��"
					+ serviceInfo.getTargetnamespace());
			System.out.println("");
			Iterator iter = serviceInfo.getOperations();
			System.out.println("���ڿ��Բ鿴Զ��Web���������й������(��Ӧ����Web������,ServiceInfo)");
			System.out.println(serviceInfo.getName() + "�ṩ�Ĳ�����:");
			while (iter.hasNext()) {
				i++;
				OperationInfo oper = (OperationInfo) iter.next();
				System.out.println("");
				System.out
						.println("����:" + i + " " + oper.getTargetMethodName());
				System.out.println("namespaceΪ��" + oper.getNamespaceURI());
				List inps = oper.getInparameters();
				List outps = oper.getOutparameters();
				if (inps.size() == 0) {
					System.out.println("�˲���������������Ϊ:");
					System.out.println("ִ�д˲�������Ҫ�����κβ���!");
				} else {
					System.out.println("�˲���������������Ϊ:");
					for (Iterator iterator1 = inps.iterator(); iterator1
							.hasNext();) {
						ParameterInfo element = (ParameterInfo) iterator1
								.next();
						System.out.println("������Ϊ:" + element.getName());
						System.out.println("��������Ϊ:" + element.getKind());
					}
				}
				if (outps.size() == 0) {
					System.out.println("ִ�д˲����������κβ���!");
				} else {
					System.out.println("�˲������������Ϊ:");
					for (Iterator iterator2 = outps.iterator(); iterator2
							.hasNext();) {
						ParameterInfo element = (ParameterInfo) iterator2
								.next();
						System.out.println("������:" + element.getName());
						System.out.println("����Ϊ:" + element.getKind());
					}
				}
				System.out.println("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
