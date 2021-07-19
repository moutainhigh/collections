/**
 * IQuartzWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.component.quartz.webservice;

public class IQuartzWebServiceServiceLocator extends org.apache.axis.client.Service implements com.gwssi.dw.component.quartz.webservice.IQuartzWebServiceService {

    public IQuartzWebServiceServiceLocator() {
    }


    public IQuartzWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IQuartzWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for QuartzWebService
    private java.lang.String QuartzWebService_address = "http://"+java.util.ResourceBundle.getBundle("app").getString("cltURL");

    public java.lang.String getQuartzWebServiceAddress() {
        return QuartzWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String QuartzWebServiceWSDDServiceName = "QuartzWebService";

    public java.lang.String getQuartzWebServiceWSDDServiceName() {
        return QuartzWebServiceWSDDServiceName;
    }

    public void setQuartzWebServiceWSDDServiceName(java.lang.String name) {
        QuartzWebServiceWSDDServiceName = name;
    }

    public com.gwssi.dw.component.quartz.webservice.IQuartzWebService getQuartzWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(QuartzWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getQuartzWebService(endpoint);
    }

    public com.gwssi.dw.component.quartz.webservice.IQuartzWebService getQuartzWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.gwssi.dw.component.quartz.webservice.QuartzWebServiceSoapBindingStub _stub = new com.gwssi.dw.component.quartz.webservice.QuartzWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getQuartzWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setQuartzWebServiceEndpointAddress(java.lang.String address) {
        QuartzWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.gwssi.dw.component.quartz.webservice.IQuartzWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.gwssi.dw.component.quartz.webservice.QuartzWebServiceSoapBindingStub _stub = new com.gwssi.dw.component.quartz.webservice.QuartzWebServiceSoapBindingStub(new java.net.URL(QuartzWebService_address), this);
                _stub.setPortName(getQuartzWebServiceWSDDServiceName());
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
        if ("QuartzWebService".equals(inputPortName)) {
            return getQuartzWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:QuartzWebService", "IQuartzWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:QuartzWebService", "QuartzWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("QuartzWebService".equals(portName)) {
            setQuartzWebServiceEndpointAddress(address);
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
