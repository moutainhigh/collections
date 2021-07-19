/**
 * GsWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.runmgr.webservices.localtax.in.client;

public class GsWebServiceLocator extends org.apache.axis.client.Service implements GsWebService {

    public GsWebServiceLocator() {
    }


    public GsWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GsWebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GsWebServicePort
    private java.lang.String GsWebServicePort_address = "http://172.24.27.106:9001/BjtaxGsSwdj/GsWebService";

    public java.lang.String getGsWebServicePortAddress() {
        return GsWebServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GsWebServicePortWSDDServiceName = "GsWebServicePort";

    public java.lang.String getGsWebServicePortWSDDServiceName() {
        return GsWebServicePortWSDDServiceName;
    }

    public void setGsWebServicePortWSDDServiceName(java.lang.String name) {
        GsWebServicePortWSDDServiceName = name;
    }

    public GsWebServicePort_PortType getGsWebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GsWebServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGsWebServicePort(endpoint);
    }

    public GsWebServicePort_PortType getGsWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
           GsWebServicePortStub _stub = new GsWebServicePortStub(portAddress, this);
            _stub.setPortName(getGsWebServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGsWebServicePortEndpointAddress(java.lang.String address) {
        GsWebServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (GsWebServicePort_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                GsWebServicePortStub _stub = new GsWebServicePortStub(new java.net.URL(GsWebServicePort_address), this);
                _stub.setPortName(getGsWebServicePortWSDDServiceName());
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
        if ("GsWebServicePort".equals(inputPortName)) {
            return getGsWebServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:GsWebServiceEjb", "GsWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:GsWebServiceEjb", "GsWebServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GsWebServicePort".equals(portName)) {
            setGsWebServicePortEndpointAddress(address);
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
