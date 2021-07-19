/**
 * ServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.runmgr.webservices.localtax.servlet.gs;

public interface ServiceSoap_PortType extends java.rmi.Remote {
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ReturnMultiGSData getLJ_Query() throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ReturnMultiGSData getGSDJ_QUERY(java.lang.String cxrqq, java.lang.String cxrqz, java.lang.String ksjls, java.lang.String jsjls) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ReturnMultiGSData getZX_QUERY(java.lang.String cxrqq, java.lang.String cxrqz, java.lang.String ksjls, java.lang.String jsjls) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ReturnMultiGSData getDX_QUERY(java.lang.String cxrqq, java.lang.String cxrqz, java.lang.String ksjls, java.lang.String jsjls) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ReturnMultiGSData getBG_QUERY(java.lang.String cxrqq, java.lang.String cxrqz, java.lang.String ksjls, java.lang.String jsjls) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ReturnMultiGSData getGSDJ_INFO(java.lang.String qymc, java.lang.String yyzzh) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ReturnMultiGSData getZX_INFO(java.lang.String qymc, java.lang.String yyzzh) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ReturnMultiGSData getDX_INFO(java.lang.String qymc, java.lang.String yyzzh) throws java.rmi.RemoteException;
    public com.gwssi.dw.runmgr.webservices.localtax.servlet.gs.ReturnMultiGSData getBG_INFO(java.lang.String qymc, java.lang.String yyzzh) throws java.rmi.RemoteException;
}
