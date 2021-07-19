/**
 * GSGeneralWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.runmgr.webservices.localtax.out.client;

public class GSGeneralWebServiceServiceLocator extends org.apache.axis.client.Service implements GSGeneralWebServiceService {

    public GSGeneralWebServiceServiceLocator() {
    }


    public GSGeneralWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GSGeneralWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GSWebService
    private java.lang.String GSWebService_address = "http://localhost:7001/bjaicdb/services/GSWebService";

    public java.lang.String getGSWebServiceAddress() {
        return GSWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GSWebServiceWSDDServiceName = "GSWebService";

    public java.lang.String getGSWebServiceWSDDServiceName() {
        return GSWebServiceWSDDServiceName;
    }

    public void setGSWebServiceWSDDServiceName(java.lang.String name) {
        GSWebServiceWSDDServiceName = name;
    }

    public GSGeneralWebService getGSWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GSWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGSWebService(endpoint);
    }

    public GSGeneralWebService getGSWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            GSWebServiceSoapBindingStub _stub = new GSWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getGSWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGSWebServiceEndpointAddress(java.lang.String address) {
        GSWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (GSGeneralWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                GSWebServiceSoapBindingStub _stub = new GSWebServiceSoapBindingStub(new java.net.URL(GSWebService_address), this);
                _stub.setPortName(getGSWebServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("GSWebService".equals(inputPortName)) {
            return getGSWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:7001/bjaicdb/services/GSWebService", "GSGeneralWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://localhost:7001/bjaicdb/services/GSWebService", "GSWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GSWebService".equals(portName)) {
            setGSWebServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
