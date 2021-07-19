/**
 * GsWebServicePort_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.runmgr.webservices.localtax.in.client;

public interface GsWebServicePort_PortType extends java.rmi.Remote {
    public ReturnMultiTaxData getSWDJ_INFO(java.lang.String string, java.lang.String string0) throws java.rmi.RemoteException;
    public ReturnMultiGSData getJIES_INFO(java.lang.String string, java.lang.String string0) throws java.rmi.RemoteException;
    public ReturnMultiTaxData getDX_QUERY(java.lang.String string, java.lang.String string0, java.lang.String string1, java.lang.String string2) throws java.rmi.RemoteException;
    public ReturnMultiTaxData getFZC_QUERY(java.lang.String string, java.lang.String string0, java.lang.String string1, java.lang.String string2) throws java.rmi.RemoteException;
    public ReturnMultiGSData getJS_INFO(java.lang.String string, java.lang.String string0) throws java.rmi.RemoteException;
    public ReturnMultiTaxData getDX_INFO(java.lang.String string, java.lang.String string0) throws java.rmi.RemoteException;
    public ReturnMultiTaxData getZX_INFO(java.lang.String string, java.lang.String string0) throws java.rmi.RemoteException;
    public ReturnMultiGSData getJIESXX_QUERY(java.lang.String string, java.lang.String string0, java.lang.String string1, java.lang.String string2) throws java.rmi.RemoteException;
    public ReturnMultiTaxData getBG_QUERY(java.lang.String string, java.lang.String string0, java.lang.String string1, java.lang.String string2) throws java.rmi.RemoteException;
    public ReturnMultiGSData getJSXX_QUERY(java.lang.String string, java.lang.String string0, java.lang.String string1, java.lang.String string2) throws java.rmi.RemoteException;
    public ReturnMultiTaxData getLJ_QUERY() throws java.rmi.RemoteException;
    public ReturnMultiTaxData getBG_INFO(java.lang.String string, java.lang.String string0) throws java.rmi.RemoteException;
    public ReturnMultiGSData getTQDX_INFO(java.lang.String string, java.lang.String string0) throws java.rmi.RemoteException;
    public ReturnMultiTaxData getZX_QUERY(java.lang.String string, java.lang.String string0, java.lang.String string1, java.lang.String string2) throws java.rmi.RemoteException;
    public ReturnMultiGSData getTQDX_QUERY(java.lang.String string, java.lang.String string0, java.lang.String string1, java.lang.String string2) throws java.rmi.RemoteException;
    public ReturnMultiTaxData getSWDJ_QUERY(java.lang.String string, java.lang.String string0, java.lang.String string1, java.lang.String string2) throws java.rmi.RemoteException;
    public ReturnMultiTaxData getFZC_INFO(java.lang.String string, java.lang.String string0) throws java.rmi.RemoteException;
}
