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
 * 项目名称：bjgs_exchange 类名称：AnalyzeWsdl 类描述：解析wsdl 创建人：lizheng 创建时间：Apr 9, 2013
 * 5:19:32 PM 修改人：lizheng 修改时间：Apr 9, 2013 5:19:32 PM 修改备注：
 * 
 * @version
 * 
 */
public class AnalyzeWsdl
{
	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(AnalyzeWsdl.class
											.getName());

	/**
	 * 
	 * analyzeWsdl根据wsdlUrl解析wsdl文件
	 * 
	 * @param wsdlUrl
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static Map analyzeWsdl(String wsdlUrl)
	{
		logger.debug("=========开始执行analyzeWsdl方法=========");
		Map resultMap = new HashMap();
		ComponentBuilder builder = new ComponentBuilder();
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setWsdllocation(wsdlUrl);
		try {
			serviceInfo = builder.buildserviceinformation(serviceInfo);
			logger.debug("=========进入try========="+serviceInfo);
			if (null != serviceInfo) {
				resultMap = ResultFormat.createConnect();
			} else {
				resultMap = ResultFormat.createConnectFailed();
			}
		} catch (Exception e) {
			logger.debug("=========进入Exception=========");
			e.printStackTrace();
			resultMap = ResultFormat.createConnectFailed();
		} finally {
			logger.debug(resultMap);
			return resultMap;
		}
	}

	/**
	 * 
	 * getService获取服务的信息
	 * 
	 * @param wsdlUrl
	 * @return ServiceInfo
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static ServiceInfo getService(String wsdlUrl)
	{
		logger.debug("=========开始执行getService方法=========");
		ComponentBuilder builder = new ComponentBuilder();
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setWsdllocation(wsdlUrl);
		try {
			serviceInfo = builder.buildserviceinformation(serviceInfo);
		} catch (Exception e) {
			logger.debug("ServiceInfo 报错！");
			e.printStackTrace();
		}
		logger.debug("=========结束getService方法=========");
		return serviceInfo;
	}

	public static void main(String[] args)
	{
		int i = 0, j = 0;
		try {
			String wsdllocation = "C:\\Users\\lizheng\\Desktop\\new1.wsdl";
			ServiceInfo serviceInfo = getService(wsdllocation);
			System.out.println("getTargetnamespace："
					+ serviceInfo.getTargetnamespace());
			System.out.println("");
			Iterator iter = serviceInfo.getOperations();
			System.out.println("现在可以查看远端Web服务对象的有关情况了(对应本地Web服务类,ServiceInfo)");
			System.out.println(serviceInfo.getName() + "提供的操作有:");
			while (iter.hasNext()) {
				i++;
				OperationInfo oper = (OperationInfo) iter.next();
				System.out.println("");
				System.out
						.println("操作:" + i + " " + oper.getTargetMethodName());
				System.out.println("namespace为：" + oper.getNamespaceURI());
				List inps = oper.getInparameters();
				List outps = oper.getOutparameters();
				if (inps.size() == 0) {
					System.out.println("此操作所需的输入参数为:");
					System.out.println("执行此操作不需要输入任何参数!");
				} else {
					System.out.println("此操作所需的输入参数为:");
					for (Iterator iterator1 = inps.iterator(); iterator1
							.hasNext();) {
						ParameterInfo element = (ParameterInfo) iterator1
								.next();
						System.out.println("参数名为:" + element.getName());
						System.out.println("参数类型为:" + element.getKind());
					}
				}
				if (outps.size() == 0) {
					System.out.println("执行此操作不返回任何参数!");
				} else {
					System.out.println("此操作的输出参数为:");
					for (Iterator iterator2 = outps.iterator(); iterator2
							.hasNext();) {
						ParameterInfo element = (ParameterInfo) iterator2
								.next();
						System.out.println("参数名:" + element.getName());
						System.out.println("类型为:" + element.getKind());
					}
				}
				System.out.println("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
