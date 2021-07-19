/**
 * FileServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.orifound.www.FileArchivingService;

public class FileServiceLocator extends org.apache.axis.client.Service implements com.gwssi.orifound.www.FileArchivingService.FileService {

	private static final long serialVersionUID = 1L;

	public FileServiceLocator() {
    }


    public FileServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FileServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FileServiceSoap
//    private java.lang.String FileServiceSoap_address = "http://160.99.16.23/FileArchivingService/FileService.asmx";
    private java.lang.String FileServiceSoap_address = "http://160.99.16.144/FileArchivingService/FileService.asmx";

    public java.lang.String getFileServiceSoapAddress() {
        return FileServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FileServiceSoapWSDDServiceName = "FileServiceSoap";

    public java.lang.String getFileServiceSoapWSDDServiceName() {
        return FileServiceSoapWSDDServiceName;
    }

    public void setFileServiceSoapWSDDServiceName(java.lang.String name) {
        FileServiceSoapWSDDServiceName = name;
    }

    public com.gwssi.orifound.www.FileArchivingService.FileServiceSoap getFileServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FileServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFileServiceSoap(endpoint);
    }

    public com.gwssi.orifound.www.FileArchivingService.FileServiceSoap getFileServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.gwssi.orifound.www.FileArchivingService.FileServiceSoapStub _stub = new com.gwssi.orifound.www.FileArchivingService.FileServiceSoapStub(portAddress, this);
            _stub.setPortName(getFileServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFileServiceSoapEndpointAddress(java.lang.String address) {
        FileServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(@SuppressWarnings("rawtypes") Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.gwssi.orifound.www.FileArchivingService.FileServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.gwssi.orifound.www.FileArchivingService.FileServiceSoapStub _stub = new com.gwssi.orifound.www.FileArchivingService.FileServiceSoapStub(new java.net.URL(FileServiceSoap_address), this);
                _stub.setPortName(getFileServiceSoapWSDDServiceName());
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
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, @SuppressWarnings("rawtypes") Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("FileServiceSoap".equals(inputPortName)) {
            return getFileServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.orifound.com/FileArchivingService", "FileService");
    }

    @SuppressWarnings("rawtypes")
	private java.util.HashSet ports = null;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.orifound.com/FileArchivingService", "FileServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FileServiceSoap".equals(portName)) {
            setFileServiceSoapEndpointAddress(address);
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
