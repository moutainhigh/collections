package com.gwssi.rodimus.indentity.support;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

public class IdentifyWebserviceClientgs {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    
    @SuppressWarnings("rawtypes")
	private HashMap endpoints = new HashMap();
    private Service service0;

    @SuppressWarnings("unchecked")
	public IdentifyWebserviceClientgs() {
        create0();
        Endpoint IdentifyWebserviceHttpPortEP = service0 .addEndpoint(new QName("http://webserv.gwssi.com", "IdentifyWebserviceHttpPort"), new QName("http://webserv.gwssi.com", "IdentifyWebserviceHttpBinding"), "http://160.100.0.134:7001/bjgsnciic/services/IdentifyWebservice");
        //Endpoint IdentifyWebserviceHttpPortEP = service0 .addEndpoint(new QName("http://webserv.gwssi.com", "IdentifyWebserviceHttpPort"), new QName("http://webserv.gwssi.com", "IdentifyWebserviceHttpBinding"), "http://127.0.0.1:8001/bjgsnciic/services/IdentifyWebservice");
        endpoints.put(new QName("http://webserv.gwssi.com", "IdentifyWebserviceHttpPort"), IdentifyWebserviceHttpPortEP);
        Endpoint IdentifyWebservicePortTypeLocalEndpointEP = service0 .addEndpoint(new QName("http://webserv.gwssi.com", "IdentifyWebservicePortTypeLocalEndpoint"), new QName("http://webserv.gwssi.com", "IdentifyWebservicePortTypeLocalBinding"), "xfire.local://IdentifyWebservice");
        endpoints.put(new QName("http://webserv.gwssi.com", "IdentifyWebservicePortTypeLocalEndpoint"), IdentifyWebservicePortTypeLocalEndpointEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

    @SuppressWarnings("rawtypes")
	public Collection getEndpoints() {
        return endpoints.values();
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap props = new HashMap();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((IdentifyWebservicePortType.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://webserv.gwssi.com", "IdentifyWebservicePortTypeLocalBinding"), "urn:xfire:transport:local");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://webserv.gwssi.com", "IdentifyWebserviceHttpBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public IdentifyWebservicePortType getIdentifyWebserviceHttpPort() {
        return ((IdentifyWebservicePortType)(this).getEndpoint(new QName("http://webserv.gwssi.com", "IdentifyWebserviceHttpPort")));
    }

    public IdentifyWebservicePortType getIdentifyWebserviceHttpPort(String url) {
        IdentifyWebservicePortType var = getIdentifyWebserviceHttpPort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public IdentifyWebservicePortType getIdentifyWebservicePortTypeLocalEndpoint() {
        return ((IdentifyWebservicePortType)(this).getEndpoint(new QName("http://webserv.gwssi.com", "IdentifyWebservicePortTypeLocalEndpoint")));
    }

    public IdentifyWebservicePortType getIdentifyWebservicePortTypeLocalEndpoint(String url) {
        IdentifyWebservicePortType var = getIdentifyWebservicePortTypeLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public static void main(String[] args) {
        IdentifyWebserviceClientgs client = new IdentifyWebserviceClientgs();
        IdentifyWebservicePortType service = client.getIdentifyWebserviceHttpPort();
        System.out.println( service.checkOneIdCard("杨水林", "330521470122051"));
    }

}
