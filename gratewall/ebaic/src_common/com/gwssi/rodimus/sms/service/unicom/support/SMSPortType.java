package com.gwssi.rodimus.sms.service.unicom.support;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "SMSPortType", targetNamespace = "http://webservice.sms.api.poweru.cn")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface SMSPortType {

	@WebMethod(operationName = "GetRecvSMSList", action = "")
	@WebResult(name = "out", targetNamespace = "http://webservice.sms.api.poweru.cn")
	public String getRecvSMSList(
			@WebParam(name = "in0", targetNamespace = "http://webservice.sms.api.poweru.cn") String in0,
			@WebParam(name = "in1", targetNamespace = "http://webservice.sms.api.poweru.cn") String in1);

	@WebMethod(operationName = "AddSMSList", action = "")
	@WebResult(name = "out", targetNamespace = "http://webservice.sms.api.poweru.cn")
	public String addSMSList(
			@WebParam(name = "in0", targetNamespace = "http://webservice.sms.api.poweru.cn") String in0,
			@WebParam(name = "in1", targetNamespace = "http://webservice.sms.api.poweru.cn") String in1);

	@WebMethod(operationName = "GetReportSMSList", action = "")
	@WebResult(name = "out", targetNamespace = "http://webservice.sms.api.poweru.cn")
	public String getReportSMSList(
			@WebParam(name = "in0", targetNamespace = "http://webservice.sms.api.poweru.cn") String in0,
			@WebParam(name = "in1", targetNamespace = "http://webservice.sms.api.poweru.cn") String in1);

	
}
