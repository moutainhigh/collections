package com.gwssi.application.webservice.client;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPBinding;

import com.gwssi.application.webservice.service.XmlToMapUtil;

/**
 * 测试客户端
 * 
 * @author lizheng
 * 
 */
public class WSClient {

	// 命名空间 targetNamespace
	public static final String targetNamespace = "http://www.aixs.com";

	// 服务名 serviceName
	public static final String serName = "services";

	// 端口名 portName
	public static final String pName = "service";

	// 服务地址 endpointAddress
	public static final String endpointAddress = "http://10.1.32.11:7001/testaxis/services/TestWebService?wsdl";

	// 方法名 operationName
	public static final String OPER_NAME = "Hello";

	// 参数名 @WebParam
	public static final String INPUT_NMAE = "param";

	public static String param() {
		HashMap dataMap = new HashMap();
		dataMap.put("USER_ID", "zhangfengrui");
		dataMap.put("APP_CODE", "SZBZ");
		dataMap.put("POST",
				"des87b33d0344ecb354afd64c859717,de9991b8d44ecb354afd64c859717");
		String xml = XmlToMapUtil.map2Dom(dataMap);
		return xml;
	}

	public static void main(String[] args) throws Exception {

		QName serviceName = new QName(targetNamespace, serName);
		QName portName = new QName(targetNamespace, pName);

		// 创建服务并添加访问
		Service service = Service.create(serviceName);
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING,
				endpointAddress);

		Dispatch<SOAPMessage> dispatch = service.createDispatch(portName,
				SOAPMessage.class, Service.Mode.MESSAGE);

		BindingProvider bp = (BindingProvider) dispatch;
		Map<String, Object> rc = bp.getRequestContext();
		rc.put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
		rc.put(BindingProvider.SOAPACTION_URI_PROPERTY, OPER_NAME);
		MessageFactory factory = ((SOAPBinding) bp.getBinding())
				.getMessageFactory();

		SOAPMessage request = factory.createMessage();
		SOAPBody body = request.getSOAPBody();
		QName payloadName = new QName(targetNamespace, OPER_NAME, "name");
		SOAPBodyElement payload = body.addBodyElement(payloadName);

		// 添加参数
		SOAPElement message = payload.addChildElement(INPUT_NMAE);
		String xml = WSClient.param();
		message.addTextNode("lizheng");

		SOAPMessage reply = null;

		try {
			reply = dispatch.invoke(request);
		} catch (WebServiceException wse) {
			wse.printStackTrace();
		}
		SOAPBody soapBody = reply.getSOAPBody();
		SOAPBodyElement nextSoapBodyElement = (SOAPBodyElement) soapBody
				.getChildElements().next();
		SOAPElement soapElement = (SOAPElement) nextSoapBodyElement
				.getChildElements().next();

		System.out.println("获取回应信息为：" + soapElement.getValue());
	}

}
