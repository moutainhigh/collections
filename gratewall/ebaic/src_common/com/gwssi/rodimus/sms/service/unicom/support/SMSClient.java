package com.gwssi.rodimus.sms.service.unicom.support;

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

@SuppressWarnings({"rawtypes","unchecked"})
public class SMSClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    
	private HashMap endpoints = new HashMap();
    private Service service0;

	public SMSClient(String url) {
        create0();
        Endpoint SMSPortTypeLocalEndpointEP = service0 .addEndpoint(new QName("http://webservice.sms.api.poweru.cn", "SMSPortTypeLocalEndpoint"), new QName("http://webservice.sms.api.poweru.cn", "SMSPortTypeLocalBinding"), "xfire.local://SMS");
        endpoints.put(new QName("http://webservice.sms.api.poweru.cn", "SMSPortTypeLocalEndpoint"), SMSPortTypeLocalEndpointEP);
        Endpoint SMSHttpPortEP = service0 .addEndpoint(new QName("http://webservice.sms.api.poweru.cn", "SMSHttpPort"), new QName("http://webservice.sms.api.poweru.cn", "SMSHttpBinding"), url);
        endpoints.put(new QName("http://webservice.sms.api.poweru.cn", "SMSHttpPort"), SMSHttpPortEP);
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

    public Collection getEndpoints() {
        return endpoints.values();
    }

    @SuppressWarnings("unused")
	private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap props = new HashMap();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((SMSPortType.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://webservice.sms.api.poweru.cn", "SMSPortTypeLocalBinding"), "urn:xfire:transport:local");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://webservice.sms.api.poweru.cn", "SMSHttpBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public SMSPortType getSMSPortTypeLocalEndpoint() {
        return ((SMSPortType)(this).getEndpoint(new QName("http://webservice.sms.api.poweru.cn", "SMSPortTypeLocalEndpoint")));
    }

    public SMSPortType getSMSPortTypeLocalEndpoint(String url) {
        SMSPortType var = getSMSPortTypeLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public SMSPortType getSMSHttpPort() {
        return ((SMSPortType)(this).getEndpoint(new QName("http://webservice.sms.api.poweru.cn", "SMSHttpPort")));
    }

    public SMSPortType getSMSHttpPort(String url) {
        SMSPortType var = getSMSHttpPort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public static void main(String[] args) {
    	/*String url = "http://localhost:4231/SMSG/services/SMS";

        SMSClient client = new SMSClient(url);
        
        SMSPortType service = client.getSMSHttpPort();
        String xml = SMSUtils.getInstance().getSendXML( "test11" , "13426202377;18612128090;18912128090", "之前托希尔曾说对阵那不勒斯和切塞纳这两场比赛是对马扎里的考验，国米主帅最终有惊无险地以不败战绩过了关，但印尼人在击败切塞纳之后对媒体表示：“我们需要以公平的方式分析教练的问题。我们看看未来两场比赛结果如何，当然无论如何在赛季中段更换教练总归不好，我们希望再给马扎里一个机会。虽然关于国际米兰换帅的传闻很多，但我们只关注最好的教练。”");

        System.out.println( xml );
        String sms = client.getSMSHttpPort().addSMSList("POWERU-SMS", xml);
        System.out.println( sms );
        System.out.println(client.getSMSHttpPort().getRecvSMSList("POWERU-SMS", "test11"));
        System.out.println(client.getSMSHttpPort().getReportSMSList("POWERU-SMS", "test11"));
        		//
        		//service.yourServiceOperationHere();
        
		System.out.println("test client completed");
        		System.exit(0);
*/    }

}
