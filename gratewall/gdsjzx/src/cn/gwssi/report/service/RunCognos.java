package cn.gwssi.report.service;




import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPHeaderElement;

import cn.gwssi.report.model.TCognosReportBO;

import com.cognos.developer.schemas.bibus._3.AsynchDetailReportOutput;
import com.cognos.developer.schemas.bibus._3.AsynchOptionEnum;
import com.cognos.developer.schemas.bibus._3.AsynchOptionInt;
import com.cognos.developer.schemas.bibus._3.AsynchReply;
import com.cognos.developer.schemas.bibus._3.AsynchReplyStatusEnum;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.BiBusHeader;
import com.cognos.developer.schemas.bibus._3.CAM;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_PortType;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.FormFieldVar;
import com.cognos.developer.schemas.bibus._3.FormatEnum;
import com.cognos.developer.schemas.bibus._3.HdrSession;
import com.cognos.developer.schemas.bibus._3.Option;
import com.cognos.developer.schemas.bibus._3.OutputEncapsulationEnum;
import com.cognos.developer.schemas.bibus._3.ParameterValue;
import com.cognos.developer.schemas.bibus._3.ParmValueItem;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.ReportService_PortType;
import com.cognos.developer.schemas.bibus._3.ReportService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.RunOptionBoolean;
import com.cognos.developer.schemas.bibus._3.RunOptionEnum;
import com.cognos.developer.schemas.bibus._3.RunOptionOutputEncapsulation;
import com.cognos.developer.schemas.bibus._3.RunOptionStringArray;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.SearchPathSingleObject;
import com.cognos.developer.schemas.bibus._3.SimpleParmValueItem;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.sshtools.j2ssh.util.Hash;




public class RunCognos  implements Callable<String>{
	
	private volatile TCognosReportBO bo;
	public   RunCognos(TCognosReportBO bo) {
		this.bo=bo;
	}
	
	
	public RunCognos() {
		// TODO Auto-generated constructor stub
	}


	private static final String BIBUS_NS = "http://developer.cognos.com/schemas/bibus/3/";

	
	public String getConfigValue(String key_name){
		InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("config.properties");
		Properties p=new Properties();
		try{
			p.load(inputStream);
		}catch(IOException e){
			e.printStackTrace();
		}
		return p.getProperty(key_name);
	}
	
	//预先运行cognos report将报表下载至服务器
	/**
	 * @param reportPath 报表在cognos路径
	 * @param htparams   报表传入参数
	 * @param savePath	  如果是下载excel的话，excel存放服务器路径
	 * @param reportName  报表名称
	 * @return
	 * @throws Exception
	 */
	public String sdk_m_visit(String reportPath,HashMap htparams,String savePath,String reportName) throws Exception{
		String retStr="";
		String serverHost="10.1.2.124";
		String errormsg="";
		String userName="admin";
		String userPassword="123456";
		String userNamespace="GdSjzxProvider";
		
		//http://10.1.2.124/ibmcognos/cgi-bin/cognosisapi.dll?
		//b_action=cognosViewer&ui.action=run&ui.object=/content/folder[@name=%27report%27]/folder[@name=%27%E5%B8%82%E5%9C%BA%E4%B8%BB%E4%BD%93%27]/folder[@name=%27new%27]/report[@name=%27%E4%BC%81%E4%B8%9A%E8%AE%BE%E7%AB%8B%E7%99%BB%E8%AE%B0_%E6%8C%89%E5%85%A8%E7%9C%81%E5%90%84%E5%B8%82%E5%88%86%E6%9E%90%28%E6%9C%88%29%27]&ui.name=%E4%BC%81%E4%B8%9A%E8%AE%BE%E7%AB%8B%E7%99%BB%E8%AE%B0_%E6%8C%89%E5%85%A8%E7%9C%81%E5%90%84%E5%B8%82%E5%88%86%E6%9E%90%28%E6%9C%88%29&run.outputFormat=&run.prompt=true&ui.backURL=/ibmcognos/cgi-bin/cognosisapi.dll?
		//b_action=xts.run&m=portal/cc.xts&m_folder=i5257CE9E1B354681B9B7C2327A6DBD50#
		//String reportPath="content/package[@name='"+package_name+"']/report[@name='"+report_name+"']";

	
		//cognos服务器地址
		String Cognos_URL="http://"+serverHost+"/ibmcognos/cgi-bin/cognosisapi.dll";
		//String Cognos_URL="http://10.1.2.124:9300/p2pd/servlet/dispatch";

		//开始执行时间
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
		//cognos登入
		if((userName.length()>0)&&(userPassword.length()>0)&&(userNamespace.length()>0)){
			setUpHeader(repService,userName,userPassword,userNamespace);
			setUpCmHeader(cmService,userName,userPassword,userNamespace);
		}
		//参数设置
		ParameterValue parameters[] = new ParameterValue[]{};
		if(htparams!=null&&!htparams.isEmpty()){
			System.out.println("start to load param!");
			int paramsize=htparams.size();
			parameters=new ParameterValue[paramsize];
			
			Iterator iterator=htparams.entrySet().iterator();
			int index=0;
			while(iterator.hasNext()){	
				Entry entry=(Entry)iterator.next();
				Object value=entry.getValue();
				Object key=entry.getKey();
				
				ParmValueItem[] p1=new ParmValueItem[index+1];
				SimpleParmValueItem item1=new SimpleParmValueItem();
				item1.setUse(value.toString());
				item1.setDisplay(value.toString());
				item1.setInclusive(true);
				p1[index]=item1;
				parameters[index]=new ParameterValue();
				parameters[index].setName(key.toString());
				parameters[index].setValue(p1);
				index++;
			}
		}
		//cognos基础运行配置
		Option runOptions[]=new Option[6];
		
		RunOptionBoolean saveOutput=new RunOptionBoolean();
		saveOutput.setName(RunOptionEnum.saveOutput);
		saveOutput.setValue(false);
		runOptions[0]=saveOutput;
		//cognos运行方式xls
		RunOptionStringArray outputFormat=new RunOptionStringArray();
		outputFormat.setName(RunOptionEnum.outputFormat);
		outputFormat.setValue(new String[] { "singleXLS" });
		runOptions[1]=outputFormat;
		

		RunOptionOutputEncapsulation outputEncapsulation=new RunOptionOutputEncapsulation();
		outputEncapsulation.setName(RunOptionEnum.outputEncapsulation);
		outputEncapsulation.setValue(OutputEncapsulationEnum.none);
		runOptions[2]=outputEncapsulation;
		
		
		AsynchOptionInt primaryWait=new AsynchOptionInt();
		primaryWait.setName(AsynchOptionEnum.primaryWaitThreshold);
		primaryWait.setValue(0);
		runOptions[3]=primaryWait;
	
		AsynchOptionInt secondWait=new AsynchOptionInt();
		secondWait.setName(AsynchOptionEnum.secondaryWaitThreshold);
		secondWait.setValue(0);
		runOptions[4]=secondWait;
		
		RunOptionBoolean promtFlag=new RunOptionBoolean();
		promtFlag.setName(RunOptionEnum.prompt);
		promtFlag.setValue(false);
		runOptions[5]=promtFlag;

	  
		
		
		AsynchReply res=repService.run(new SearchPathSingleObject(reportPath), parameters, runOptions);
		BaseClass baseClassArray[] 
		                         = cmService.query(new SearchPathMultipleObject("/content//package"), 
		                  new PropEnum[]{ 
		                  PropEnum.defaultName, 
		                  PropEnum.searchPath}, 
		                  new Sort[]{}, 
		                  new QueryOptions());
		if(res.getStatus()==AsynchReplyStatusEnum.complete){
			AsynchDetailReportOutput reportOutput=null;
			for(int i=0;i<res.getDetails().length;i++){
				if(res.getDetails()[i] instanceof AsynchDetailReportOutput){
					reportOutput=(AsynchDetailReportOutput)res.getDetails()[i];
					break;
				}
			}
			String[] data=reportOutput.getOutputPages();
			retStr=data[0];

		}
		
//		this.saveBinaryOutput( 0, reportName, reportPath, retStr);
		retStr=retStr.replaceFirst("<style>", "<style> table{ border-spacing:inherit }");
		//保持导出
		return retStr;
	}
	
	public void setUpHeader(ReportService_PortType repService,String userName,String userPassword,String userNamespace) throws Exception{
		//ȥ��cognos��ͷ��Ϣ
		SOAPHeaderElement sOAPHeaderElement = ((Stub)repService).getResponseHeader(BIBUS_NS, "biBusHeader");
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
		SOAPHeaderElement sOAPHeaderElement = ((Stub)cmService).getResponseHeader(BIBUS_NS, "biBusHeader");
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
	
	
	
	/** 
	 * 导出xls格式的报表，并且写入文件 
	 *  
	 * @param connection 
	 * @param response 
	 * @param partOfFileoutPutName 
	 * @param reportType 
	 * @param reportName 
	 * @param savePath 
	 * @return 
	 * @throws IOException 
	 */  
	private String saveBinaryOutput(int reportType,  
			String reportName, String savePath,String result) throws IOException {  
		String fileSaveUrl = null;  
		byte[] binaryOutput = null;  
		binaryOutput = result.getBytes("UTF-8");  
		AsynchDetailReportOutput reportOutput = null;  
		if (binaryOutput == null) {  
			return null;  
		}  
		//创建文件  
		createNewFile(savePath);  
		File oFile = new File(setFilenameByType(savePath,  
				reportType, reportName));  
		FileOutputStream fos = new FileOutputStream(oFile);  
		fos.write(binaryOutput);  
		fos.flush();  
		fos.close();  
		//获取绝对路劲  
		fileSaveUrl = oFile.getAbsolutePath();  
		return fileSaveUrl;  
	}  
	/** 
	 * 根据格式设置文件名 
	 *  
	 * @param filePath文件路劲 
	 * @param reportName文件名称 
	 * @param fileType文件类型 
	 * @return 
	 */  
	private String setFilenameByType(String filePath, int fileType, String reportName) {  
		switch (fileType) {  

//		case Parameter.XLS:  
//			return filePath + System.getProperty("file.separator")  
//					+ partOfFileoutPutName + "_" + reportName + ".xls";  
//		case ParameterUtil.HTML:  
//			return filePath + System.getProperty("file.separator")  
//					+ partOfFileoutPutName + "_" + reportName + ".html";  
		default:  
			 return "D:/xxms.xls";  
		}  
	}  
	/** 
	 * 创建目录 
	 *  
	 * @param savePath 
	 */  
	private void createNewFile(String savePath) {  
		File file = new File(savePath);  
		if (!file.exists()) {  
			file.mkdirs();  
		}  
	}

	@Override
	public String call() throws Exception {

		String rootPath="/content/folder[@name='report']/folder[@name='工商制式报表']/report[@name='"+bo.getReportname()+"']";
		String result=this.sdk_m_visit(rootPath, bo.getMapHelper(), null, bo.getReportname());
		CognosService service=new CognosService();
		service.saveReport(bo, result);
		return null;
	}  
	
	public static void main(String[] args) throws Exception {
		RunCognos cognosx=new RunCognos();

		String rootPath="/content/folder[@name='report']/folder[@name='工商制式报表']/report[@name='个体1表']";
		HashMap map=new HashMap();
		map.put("term", "2016-10-03");
		map.put("unit", "440200");
		String result=cognosx.sdk_m_visit(rootPath,map , null, "个体1表");
		System.out.println(result);
//		String rootPath="/content/folder[@name='report']/folder[@name='工商制式报表']/report[@name='外资1表']";
//		HashMap map=new HashMap();
//		map.put("term", "2016-08-03");
//		map.put("unit", "440200");
//		cognosx.sdk_m_visit(rootPath, map, null, "外资1表");
	}

}