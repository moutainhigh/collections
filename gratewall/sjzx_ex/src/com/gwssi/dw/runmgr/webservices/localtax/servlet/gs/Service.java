/**
 * Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.runmgr.webservices.localtax.servlet.gs;

public interface Service extends javax.xml.rpc.Service {
    public java.lang.String getServiceSoapAddress();

    public com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ServiceSoap_PortType getServiceSoap() throws javax.xml.rpc.ServiceException;

    public com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ServiceSoap_PortType getServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
