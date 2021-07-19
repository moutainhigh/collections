/**
 * ServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.runmgr.webservices.localtax.servlet.zr;

public interface ServiceSoap_PortType extends java.rmi.Remote {
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ReturnMultiGSData getLJ_Query() throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ReturnMultiGSData getGSDJ_QUERY(java.lang.String cxrqq, java.lang.String cxrqz, java.lang.String ksjls, java.lang.String jsjls) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ReturnMultiGSData getZX_QUERY(java.lang.String cxrqq, java.lang.String cxrqz, java.lang.String ksjls, java.lang.String jsjls) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ReturnMultiGSData getDX_QUERY(java.lang.String cxrqq, java.lang.String cxrqz, java.lang.String ksjls, java.lang.String jsjls) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ReturnMultiGSData getBG_QUERY(java.lang.String cxrqq, java.lang.String cxrqz, java.lang.String ksjls, java.lang.String jsjls) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ReturnMultiGSData getGSDJ_INFO(java.lang.String qymc, java.lang.String yyzzh) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ReturnMultiGSData getZX_INFO(java.lang.String qymc, java.lang.String yyzzh) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ReturnMultiGSData getDX_INFO(java.lang.String qymc, java.lang.String yyzzh) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.zr.ReturnMultiGSData getBG_INFO(java.lang.String qymc, java.lang.String yyzzh) throws java.rmi.RemoteException;
}
