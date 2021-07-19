/**
 * IQuartzWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.component.quartz.webservice;

public interface IQuartzWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getQuartzWebServiceAddress();

    public com.gwssi.dw.component.quartz.webservice.IQuartzWebService getQuartzWebService() throws javax.xml.rpc.ServiceException;

    public com.gwssi.dw.component.quartz.webservice.IQuartzWebService getQuartzWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
