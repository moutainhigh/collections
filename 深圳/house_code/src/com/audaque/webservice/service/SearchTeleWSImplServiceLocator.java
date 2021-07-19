/**
 * SearchTeleWSImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.audaque.webservice.service;

public class SearchTeleWSImplServiceLocator extends org.apache.axis.client.Service implements com.audaque.webservice.service.SearchTeleWSImplService {

    public SearchTeleWSImplServiceLocator() {
    }


    public SearchTeleWSImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SearchTeleWSImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SearchTeleWSImplPort
    private java.lang.String SearchTeleWSImplPort_address = "http://61.144.226.124:7890/czwSearchAdmin/services/searchTeleWS";

    public java.lang.String getSearchTeleWSImplPortAddress() {
        return SearchTeleWSImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SearchTeleWSImplPortWSDDServiceName = "SearchTeleWSImplPort";

    public java.lang.String getSearchTeleWSImplPortWSDDServiceName() {
        return SearchTeleWSImplPortWSDDServiceName;
    }

    public void setSearchTeleWSImplPortWSDDServiceName(java.lang.String name) {
        SearchTeleWSImplPortWSDDServiceName = name;
    }

    public com.audaque.webservice.service.SearchTeleWS getSearchTeleWSImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SearchTeleWSImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSearchTeleWSImplPort(endpoint);
    }

    public com.audaque.webservice.service.SearchTeleWS getSearchTeleWSImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.audaque.webservice.service.SearchTeleWSImplServiceSoapBindingStub _stub = new com.audaque.webservice.service.SearchTeleWSImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getSearchTeleWSImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSearchTeleWSImplPortEndpointAddress(java.lang.String address) {
        SearchTeleWSImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.audaque.webservice.service.SearchTeleWS.class.isAssignableFrom(serviceEndpointInterface)) {
                com.audaque.webservice.service.SearchTeleWSImplServiceSoapBindingStub _stub = new com.audaque.webservice.service.SearchTeleWSImplServiceSoapBindingStub(new java.net.URL(SearchTeleWSImplPort_address), this);
                _stub.setPortName(getSearchTeleWSImplPortWSDDServiceName());
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
        if ("SearchTeleWSImplPort".equals(inputPortName)) {
            return getSearchTeleWSImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("service.webservice.audaque.com", "SearchTeleWSImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("service.webservice.audaque.com", "SearchTeleWSImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SearchTeleWSImplPort".equals(portName)) {
            setSearchTeleWSImplPortEndpointAddress(address);
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
