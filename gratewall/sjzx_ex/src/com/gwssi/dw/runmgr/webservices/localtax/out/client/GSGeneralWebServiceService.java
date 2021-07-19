/**
 * GSGeneralWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.runmgr.webservices.localtax.out.client;

public interface GSGeneralWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getGSWebServiceAddress();

    public GSGeneralWebService getGSWebService() throws javax.xml.rpc.ServiceException;

    public GSGeneralWebService getGSWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
