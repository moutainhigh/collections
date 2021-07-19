/**
 * GsWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.runmgr.webservices.localtax.in.client;

public interface GsWebService extends javax.xml.rpc.Service {
    public java.lang.String getGsWebServicePortAddress();

    public GsWebServicePort_PortType getGsWebServicePort() throws javax.xml.rpc.ServiceException;

    public GsWebServicePort_PortType getGsWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
