package com.gwssi.rodimus.indentity.support;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "IdentifyWebservicePortType", targetNamespace = "http://webserv.gwssi.com")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface IdentifyWebservicePortType {

    @WebMethod(operationName = "example", action = "")
    @WebResult(name = "out", targetNamespace = "http://webserv.gwssi.com")
    public String example(
            @WebParam(name = "in0", targetNamespace = "http://webserv.gwssi.com")
            String in0);

    @WebMethod(operationName = "checkOneIdCard", action = "")
    @WebResult(name = "out", targetNamespace = "http://webserv.gwssi.com")
    public String checkOneIdCard(
            @WebParam(name = "in0", targetNamespace = "http://webserv.gwssi.com")
            String in0,
            @WebParam(name = "in1", targetNamespace = "http://webserv.gwssi.com")
            String in1);

    @WebMethod(operationName = "checkOneIdCardAllParam", action = "")
    @WebResult(name = "out", targetNamespace = "http://webserv.gwssi.com")
    public String checkOneIdCardAllParam(
            @WebParam(name = "in0", targetNamespace = "http://webserv.gwssi.com")
            String in0,
            @WebParam(name = "in1", targetNamespace = "http://webserv.gwssi.com")
            String in1,
            @WebParam(name = "in2", targetNamespace = "http://webserv.gwssi.com")
            String in2,
            @WebParam(name = "in3", targetNamespace = "http://webserv.gwssi.com")
            String in3);

    @WebMethod(operationName = "checkIdCards", action = "")
    @WebResult(name = "out", targetNamespace = "http://webserv.gwssi.com")
    public String checkIdCards(
            @WebParam(name = "in0", targetNamespace = "http://webserv.gwssi.com")
            String in0);

}
