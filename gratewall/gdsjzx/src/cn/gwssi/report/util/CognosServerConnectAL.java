package cn.gwssi.report.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.message.SOAPHeaderElement;

import com.cognos.developer.schemas.bibus._3.BiBusHeader;
import com.cognos.developer.schemas.bibus._3.CAM;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_PortType;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.FormFieldVar;
import com.cognos.developer.schemas.bibus._3.FormatEnum;
import com.cognos.developer.schemas.bibus._3.HdrSession;
import com.cognos.developer.schemas.bibus._3.ReportService_PortType;
import com.cognos.developer.schemas.bibus._3.ReportService_ServiceLocator;
import com.cognos.org.apache.axis.client.Stub;
import com.cognos.org.apache.axis.message.MessageElement;


/** 
* 连接cognos服务器 
*/  
public class CognosServerConnectAL  {  
	
	private static final String BIBUS_NS = "http://developer.cognos.com/schemas/bibus/3/";

/** 
  * cognos内容库服务Locater对象 
  */  
private ContentManagerService_ServiceLocator cmServiceLocator;  
/** 
  * cognos报表服务Locater对象 
  */  
private ReportService_ServiceLocator reportServiceLocator;  
/** 
  * cognos内容库服务对象 
  */  
private ContentManagerService_PortType cmService;  
/** 
  * cognos报表服务对象 
  */  
private ReportService_PortType repService;  
/** 
  * 获取内容库服务Locater对象 
  * @return 
  */  
public ContentManagerService_ServiceLocator   
  
getCmServiceLocator() {  
  return cmServiceLocator;  
}  
/** 
  * 设置内容库服务Locater对象 
  * @param cmServiceLocator 
  */  
public void setCmServiceLocator(  
   ContentManagerService_ServiceLocator cmServiceLocator) {  
  this.cmServiceLocator = cmServiceLocator;  
}  
/** 
  * 获取报表服务Locater对象 
  * @return 
  */  
public ReportService_ServiceLocator getReportServiceLocator() {  
  return reportServiceLocator;  
}  
/** 
  * 设置报表服务Locater对象 
  * @param reportServiceLocator 
  */  
public void setReportServiceLocator(  
   ReportService_ServiceLocator reportServiceLocator) {  
  this.reportServiceLocator = reportServiceLocator;  
}  
  
/** 
  * 获得内容库连接 
  *  
  * @param cognosUrl cognos服务器URL 
  * @return 
  * @throws ServiceException 
  * @throws MalformedURLException 
  */  
public ContentManagerService_PortType connectToCognosServer(String   cognosUrl)  
   throws ServiceException, MalformedURLException {  
  java.net.URL serverURL = new java.net.URL(cognosUrl);  
  
  cmService = cmServiceLocator.getcontentManagerService  
  
(serverURL);  
  repService = reportServiceLocator.getreportService  
  
(serverURL);  
  ((Stub) cmService).setTimeout(0);//内容库超时  
  ((Stub) repService).setTimeout(0);//报表服务超时  
  return cmService;  
}  
  
/** 
  * 获得cognos内容库服务对象 
  *  
  * @return 
  */  
public ContentManagerService_PortType getCMService() {  
  return cmService;  
}  
  
/** 
  * 获得cognos报表服务对象 
  *  
  * @return 
  */  
public ReportService_PortType getReportService() {  
  return repService;  
}  



public void set() throws Exception{
	String retStr="";
	String serverHost="10.1.2.124";
	String errormsg="";
	//���������·��
//http://10.1.2.124/ibmcognos/cgi-bin/cognosisapi.dll?
	//b_action=cognosViewer&ui.action=run&ui.object=/content/folder[@name=%27report%27]/folder[@name=%27%E5%B8%82%E5%9C%BA%E4%B8%BB%E4%BD%93%27]/folder[@name=%27new%27]/report[@name=%27%E4%BC%81%E4%B8%9A%E8%AE%BE%E7%AB%8B%E7%99%BB%E8%AE%B0_%E6%8C%89%E5%85%A8%E7%9C%81%E5%90%84%E5%B8%82%E5%88%86%E6%9E%90%28%E6%9C%88%29%27]&ui.name=%E4%BC%81%E4%B8%9A%E8%AE%BE%E7%AB%8B%E7%99%BB%E8%AE%B0_%E6%8C%89%E5%85%A8%E7%9C%81%E5%90%84%E5%B8%82%E5%88%86%E6%9E%90%28%E6%9C%88%29&run.outputFormat=&run.prompt=true&ui.backURL=/ibmcognos/cgi-bin/cognosisapi.dll?
	//b_action=xts.run&m=portal/cc.xts&m_folder=i5257CE9E1B354681B9B7C2327A6DBD50#
//	String reportPath="content/package[@name='"+package_name+"']/report[@name='"+report_name+"']";
	//��½��Ϣ
	String userName="admin";
	String userPassword="123456";
	String userNamespace="GdSjzxProvider";
	//��������������
	String Cognos_URL="http://"+serverHost+"/ibmcognos/cgi-bin/cognosisapi.dll";
	//String Cognos_URL="http://10.1.2.124:9300/p2pd/servlet/dispatch";

	System.out.println(Cognos_URL);
	//��ȡ��ʼʱ��
	long startTime=System.currentTimeMillis();
	ReportService_ServiceLocator reportServiceLocator=new ReportService_ServiceLocator();
	ReportService_PortType repService=null;
	ContentManagerService_ServiceLocator cmServiceLocator=new ContentManagerService_ServiceLocator();
	ContentManagerService_PortType cmService=null;
	try{
		repService=reportServiceLocator.getreportService(new URL(Cognos_URL));
		cmService=cmServiceLocator.getcontentManagerService(new URL(Cognos_URL));
	}
	catch(MalformedURLException ex){
		System.out.println("Caught a MalformedURLException:\n"+ex.getMessage());
		System.out.println("IBM Cognos server URL was: "+Cognos_URL);
		errormsg+=ex.getMessage();
		System.exit(-1);
	}
	catch(ServiceException ex){
		System.out.println("Caught a ServiceException: "+ex.getMessage());
		ex.printStackTrace();
		errormsg+=ex.getMessage();
		System.exit(-1);
	}
	//������֤��Ϣ
	if((userName.length()>0)&&(userPassword.length()>0)&&(userNamespace.length()>0)){
		System.out.println("Logging on as "+userName+" in the "+userNamespace+" namespace.");
		try {
			setUpHeader(repService,userName,userPassword,userNamespace);
			setUpCmHeader(cmService,userName,userPassword,userNamespace);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//����report����
}


public void setUpHeader(ReportService_PortType repService,String userName,String userPassword,String userNamespace) throws Exception{
	//ȥ��cognos��ͷ��Ϣ
MessageElement sOAPHeaderElement = ((Stub)repService).getResponseHeader(BIBUS_NS, "biBusHeader");
	BiBusHeader biBusHeader=null;
	if(sOAPHeaderElement != null){
		biBusHeader = (BiBusHeader)sOAPHeaderElement.getValueAsType(new QName(BIBUS_NS, "biBusHeader"));
		if(biBusHeader!=null){
			if(biBusHeader.getTracking()!=null){
				if(biBusHeader.getTracking().getConversationContext()!=null){
					biBusHeader.getTracking().setConversationContext(null);
				}
			}
			System.out.println("biBusHeader is not null!");
			return;
		}
	}
	System.out.println("biBusHeader is null!");
	//����һ���µ�biBusHeader��ע��
	biBusHeader=new BiBusHeader();
	biBusHeader.setCAM(new CAM());
	//��CAM�ķ�ʽ���е�½
	biBusHeader.getCAM().setAction("logonAs");
	biBusHeader.setHdrSession(new HdrSession());
	//��½��Ϣ����
	FormFieldVar ffs[] = new FormFieldVar[3];
	//��½���û���
	ffs[0]=new FormFieldVar();
	ffs[0].setName("CAMUsername");
	ffs[0].setValue(userName);
	ffs[0].setFormat(FormatEnum.not_encrypted);
	//��½������
	ffs[1]=new FormFieldVar();
	ffs[1].setName("CAMPassword");
	ffs[1].setValue(userPassword);
	ffs[1].setFormat(FormatEnum.not_encrypted);
	//��½������ռ�
	ffs[2]=new FormFieldVar();
	ffs[2].setName("CAMNamespace");
	ffs[2].setValue(userNamespace);
	ffs[2].setFormat(FormatEnum.not_encrypted);
	//���õ�¼��Ϣ
	biBusHeader.getHdrSession().setFormFieldVars(ffs);
	((Stub)repService).setHeader(BIBUS_NS,"biBusHeader",biBusHeader);
}

public void setUpCmHeader(ContentManagerService_PortType cmService,String userName,String userPassword,String userNamespace) throws Exception{
	//ȥ��cognos��ͷ��Ϣ
	MessageElement sOAPHeaderElement = ((Stub)cmService).getResponseHeader(BIBUS_NS, "biBusHeader");
	BiBusHeader biBusHeader=null;
	if(sOAPHeaderElement != null){
		biBusHeader = (BiBusHeader)sOAPHeaderElement.getValueAsType(new QName(BIBUS_NS, "biBusHeader"));
		if(biBusHeader!=null){
			if(biBusHeader.getTracking()!=null){
				if(biBusHeader.getTracking().getConversationContext()!=null){
					biBusHeader.getTracking().setConversationContext(null);
				}
			}
			System.out.println("biBusHeader is not null!");
			return;
		}
	}
	System.out.println("biBusHeader is null!");
	//����һ���µ�biBusHeader��ע��
	biBusHeader=new BiBusHeader();
	biBusHeader.setCAM(new CAM());
	//��CAM�ķ�ʽ���е�½
	biBusHeader.getCAM().setAction("logonAs");
	biBusHeader.setHdrSession(new HdrSession());
	//��½��Ϣ����
	FormFieldVar ffs[] = new FormFieldVar[3];
	//��½���û���
	ffs[0]=new FormFieldVar();
	ffs[0].setName("CAMUsername");
	ffs[0].setValue(userName);
	ffs[0].setFormat(FormatEnum.not_encrypted);
	//��½������
	ffs[1]=new FormFieldVar();
	ffs[1].setName("CAMPassword");
	ffs[1].setValue(userPassword);
	ffs[1].setFormat(FormatEnum.not_encrypted);
	//��½������ռ�
	ffs[2]=new FormFieldVar();
	ffs[2].setName("CAMNamespace");
	ffs[2].setValue(userNamespace);
	ffs[2].setFormat(FormatEnum.not_encrypted);
	//���õ�¼��Ϣ
	biBusHeader.getHdrSession().setFormFieldVars(ffs);
	((Stub)cmService).setHeader(BIBUS_NS,"biBusHeader",biBusHeader);
}

}  