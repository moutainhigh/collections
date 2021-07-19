package com.gwssi.report.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
//import com.sshtools.j2ssh.util.Hash;
import com.gwssi.report.model.TCognosReportBO;
import com.gwssi.report.util.Report2Stream;

public class RunCognos implements Callable<String> {
	private static Logger log = Logger.getLogger(RunCognos.class);
	static {
		Logger.getRootLogger().setLevel(Level.INFO);
	}
	private TCognosReportBO bo;

	public RunCognos(TCognosReportBO bo) {
		this.bo = bo;
	}

	public RunCognos() {
		// TODO Auto-generated constructor stub
	}

	private static final String BIBUS_NS = "http://developer.cognos.com/schemas/bibus/3/";

	public String getConfigValue(String key_name) {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("config.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty(key_name);
	}

	// 预先运行cognos report将报表下载至服务器
	/**
	 * @param reportPath
	 *            报表在cognos路径
	 * @param htparams
	 *            报表传入参数
	 * @param savePath
	 *            如果是下载excel的话，excel存放服务器路径
	 * @param reportName
	 *            报表名称
	 * @return
	 * @throws Exception
	 */
	public String sdk_m_visit(String reportPath, HashMap htparams,
			String savePath, String reportName) throws Exception {
		String retStr = "";
		// String serverHost="10.3.70.41";
		String serverHost = "10.0.4.225:9300";
		String errormsg = "";
		String userName = "admin";
		String userPassword = "123456";
		String userNamespace = "GdSjzxProvider";
		// String userNamespace="ForJava";

		// http://10.1.2.124/ibmcognos/cgi-bin/cognosisapi.dll?
		// b_action=cognosViewer&ui.action=run&ui.object=/content/folder[@name=%27report%27]/folder[@name=%27%E5%B8%82%E5%9C%BA%E4%B8%BB%E4%BD%93%27]/folder[@name=%27new%27]/report[@name=%27%E4%BC%81%E4%B8%9A%E8%AE%BE%E7%AB%8B%E7%99%BB%E8%AE%B0_%E6%8C%89%E5%85%A8%E7%9C%81%E5%90%84%E5%B8%82%E5%88%86%E6%9E%90%28%E6%9C%88%29%27]&ui.name=%E4%BC%81%E4%B8%9A%E8%AE%BE%E7%AB%8B%E7%99%BB%E8%AE%B0_%E6%8C%89%E5%85%A8%E7%9C%81%E5%90%84%E5%B8%82%E5%88%86%E6%9E%90%28%E6%9C%88%29&run.outputFormat=&run.prompt=true&ui.backURL=/ibmcognos/cgi-bin/cognosisapi.dll?
		// b_action=xts.run&m=portal/cc.xts&m_folder=i5257CE9E1B354681B9B7C2327A6DBD50#
		// String
		// reportPath="content/package[@name='"+package_name+"']/report[@name='"+report_name+"']";

		// cognos服务器地址
		// String Cognos_URL =
		// "http://10.0.4.225:9300/ibmcognos/cgi-bin/cognosisapi.dll";
		String Cognos_URL = "http://10.0.4.225:9300/p2pd/servlet/dispatch?b_action=xts.run&m=portal/launch.xts&ui.tool=CognosViewer&ui.action=run&ui.object=";
		// String Cognos_URL="http://10.1.2.124:9300/p2pd/servlet/dispatch";

		System.out.println(Cognos_URL);
		// 开始执行时间
		long startTime = System.currentTimeMillis();
		ReportService_ServiceLocator reportServiceLocator = new ReportService_ServiceLocator();
		ReportService_PortType repService = null;
		ContentManagerService_ServiceLocator cmServiceLocator = new ContentManagerService_ServiceLocator();
		ContentManagerService_PortType cmService = null;
		try {
			repService = reportServiceLocator.getreportService(new URL(
					Cognos_URL));
			cmService = cmServiceLocator.getcontentManagerService(new URL(
					Cognos_URL));
			// 设置超时时间无限
			((Stub) cmService).setTimeout(0);
			((Stub) repService).setTimeout(0);
		} catch (MalformedURLException ex) {
			System.out.println("Caught a MalformedURLException:\n"
					+ ex.getMessage());
			System.out.println("IBM Cognos server URL was: " + Cognos_URL);
			errormsg += ex.getMessage();
			System.exit(-1);
		} catch (ServiceException ex) {
			System.out.println("Caught a ServiceException: " + ex.getMessage());
			ex.printStackTrace();
			errormsg += ex.getMessage();
			System.exit(-1);
		}
		// cognos登入
		// if((userName.length()>0)&&(userPassword.length()>0)&&(userNamespace.length()>0)){
		// System.out.println("Logging on as "+userName+" in the "+userNamespace+" namespace.");
		// setUpHeader(repService,userName,userPassword,userNamespace);
		// setUpCmHeader(cmService,userName,userPassword,userNamespace);
		// }
		// 参数设置
		ParameterValue parameters[] = new ParameterValue[] {};
		if (htparams != null && !htparams.isEmpty()) {
			System.out.println("start to load param!");
			int paramsize = htparams.size();
			parameters = new ParameterValue[paramsize];

			Iterator iterator = htparams.entrySet().iterator();
			int index = 0;
			while (iterator.hasNext()) {
				Entry entry = (Entry) iterator.next();
				Object value = entry.getValue();
				Object key = entry.getKey();

				ParmValueItem[] p1 = new ParmValueItem[index + 1];
				SimpleParmValueItem item1 = new SimpleParmValueItem();
				item1.setUse(value.toString());
				item1.setDisplay(value.toString());
				item1.setInclusive(true);
				p1[index] = item1;
				parameters[index] = new ParameterValue();
				parameters[index].setName(key.toString());
				parameters[index].setValue(p1);
				index++;
			}
		}
		// cognos基础运行配置
		Option runOptions[] = new Option[6];

		RunOptionBoolean saveOutput = new RunOptionBoolean();
		saveOutput.setName(RunOptionEnum.saveOutput);
		saveOutput.setValue(false);
		runOptions[0] = saveOutput;
		// cognos运行方式xls
		RunOptionStringArray outputFormat = new RunOptionStringArray();
		outputFormat.setName(RunOptionEnum.outputFormat);
		outputFormat.setValue(new String[] { "singleXLS" });
		runOptions[1] = outputFormat;

		RunOptionOutputEncapsulation outputEncapsulation = new RunOptionOutputEncapsulation();
		outputEncapsulation.setName(RunOptionEnum.outputEncapsulation);
		outputEncapsulation.setValue(OutputEncapsulationEnum.none);
		runOptions[2] = outputEncapsulation;

		AsynchOptionInt primaryWait = new AsynchOptionInt();
		primaryWait.setName(AsynchOptionEnum.primaryWaitThreshold);
		primaryWait.setValue(0);
		runOptions[3] = primaryWait;

		AsynchOptionInt secondWait = new AsynchOptionInt();
		secondWait.setName(AsynchOptionEnum.secondaryWaitThreshold);
		secondWait.setValue(0);
		runOptions[4] = secondWait;

		RunOptionBoolean promtFlag = new RunOptionBoolean();
		promtFlag.setName(RunOptionEnum.prompt);
		promtFlag.setValue(false);
		runOptions[5] = promtFlag;

		AsynchReply res = repService.run(
				new SearchPathSingleObject(reportPath), parameters, runOptions);
		BaseClass baseClassArray[] = cmService.query(
				new SearchPathMultipleObject("/content//package"),
				new PropEnum[] { PropEnum.defaultName, PropEnum.searchPath },
				new Sort[] {}, new QueryOptions());
		if (res.getStatus() == AsynchReplyStatusEnum.complete) {
			AsynchDetailReportOutput reportOutput = null;
			for (int i = 0; i < res.getDetails().length; i++) {
				if (res.getDetails()[i] instanceof AsynchDetailReportOutput) {
					reportOutput = (AsynchDetailReportOutput) res.getDetails()[i];
					break;
				}
			}
			String[] data = reportOutput.getOutputPages();
			retStr = data[0];

		}

		// this.saveBinaryOutput( 0, reportName, reportPath, retStr);
		retStr = retStr.replaceFirst("<style>",
				"<style> table{ border-spacing:inherit }");
		// 保持导出
		return retStr;
	}

	public void setUpHeader(ReportService_PortType repService, String userName,
			String userPassword, String userNamespace) throws Exception {
		// ȥ��cognos��ͷ��Ϣ
		SOAPHeaderElement sOAPHeaderElement = ((Stub) repService)
				.getResponseHeader(BIBUS_NS, "biBusHeader");
		BiBusHeader biBusHeader = null;
		if (sOAPHeaderElement != null) {
			biBusHeader = (BiBusHeader) sOAPHeaderElement
					.getValueAsType(new QName(BIBUS_NS, "biBusHeader"));
			if (biBusHeader != null) {
				if (biBusHeader.getTracking() != null) {
					if (biBusHeader.getTracking().getConversationContext() != null) {
						biBusHeader.getTracking().setConversationContext(null);
					}
				}
				System.out.println("biBusHeader is not null!");
				return;
			}
		}
		System.out.println("biBusHeader is null!");
		// ����һ���µ�biBusHeader��ע��
		biBusHeader = new BiBusHeader();
		biBusHeader.setCAM(new CAM());
		// ��CAM�ķ�ʽ���е�½
		biBusHeader.getCAM().setAction("logonAs");
		biBusHeader.setHdrSession(new HdrSession());
		// ��½��Ϣ����
		FormFieldVar ffs[] = new FormFieldVar[3];
		// ��½���û���
		ffs[0] = new FormFieldVar();
		ffs[0].setName("CAMUsername");
		ffs[0].setValue(userName);
		ffs[0].setFormat(FormatEnum.not_encrypted);
		// ��½������
		ffs[1] = new FormFieldVar();
		ffs[1].setName("CAMPassword");
		ffs[1].setValue(userPassword);
		ffs[1].setFormat(FormatEnum.not_encrypted);
		// ��½������ռ�
		ffs[2] = new FormFieldVar();
		ffs[2].setName("CAMNamespace");
		ffs[2].setValue(userNamespace);
		ffs[2].setFormat(FormatEnum.not_encrypted);
		// ���õ�¼��Ϣ
		biBusHeader.getHdrSession().setFormFieldVars(ffs);
		((Stub) repService).setHeader(BIBUS_NS, "biBusHeader", biBusHeader);
	}

	public void setUpCmHeader(ContentManagerService_PortType cmService,
			String userName, String userPassword, String userNamespace)
			throws Exception {
		// ȥ��cognos��ͷ��Ϣ
		SOAPHeaderElement sOAPHeaderElement = ((Stub) cmService)
				.getResponseHeader(BIBUS_NS, "biBusHeader");
		BiBusHeader biBusHeader = null;
		if (sOAPHeaderElement != null) {
			biBusHeader = (BiBusHeader) sOAPHeaderElement
					.getValueAsType(new QName(BIBUS_NS, "biBusHeader"));
			if (biBusHeader != null) {
				if (biBusHeader.getTracking() != null) {
					if (biBusHeader.getTracking().getConversationContext() != null) {
						biBusHeader.getTracking().setConversationContext(null);
					}
				}
				System.out.println("biBusHeader is not null!");
				return;
			}
		}
		System.out.println("biBusHeader is null!");
		// ����һ���µ�biBusHeader��ע��
		biBusHeader = new BiBusHeader();
		biBusHeader.setCAM(new CAM());
		// ��CAM�ķ�ʽ���е�½
		biBusHeader.getCAM().setAction("logonAs");
		biBusHeader.setHdrSession(new HdrSession());
		// ��½��Ϣ����
		FormFieldVar ffs[] = new FormFieldVar[3];
		// ��½���û���
		ffs[0] = new FormFieldVar();
		ffs[0].setName("CAMUsername");
		ffs[0].setValue(userName);
		ffs[0].setFormat(FormatEnum.not_encrypted);
		// ��½������
		ffs[1] = new FormFieldVar();
		ffs[1].setName("CAMPassword");
		ffs[1].setValue(userPassword);
		ffs[1].setFormat(FormatEnum.not_encrypted);
		// ��½������ռ�
		ffs[2] = new FormFieldVar();
		ffs[2].setName("CAMNamespace");
		ffs[2].setValue(userNamespace);
		ffs[2].setFormat(FormatEnum.not_encrypted);
		// ���õ�¼��Ϣ
		biBusHeader.getHdrSession().setFormFieldVars(ffs);
		((Stub) cmService).setHeader(BIBUS_NS, "biBusHeader", biBusHeader);
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
	private String saveBinaryOutput(int reportType, String reportName,
			String savePath, String result) throws IOException {
		String fileSaveUrl = null;
		byte[] binaryOutput = null;
		binaryOutput = result.getBytes("UTF-8");
		AsynchDetailReportOutput reportOutput = null;
		if (binaryOutput == null) {
			return null;
		}
		// 创建文件
		createNewFile(savePath);
		File oFile = new File(setFilenameByType(savePath, reportType,
				reportName));
		FileOutputStream fos = new FileOutputStream(oFile);
		fos.write(binaryOutput);
		fos.flush();
		fos.close();
		// 获取绝对路劲
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
	private String setFilenameByType(String filePath, int fileType,
			String reportName) {
		switch (fileType) {

		// case Parameter.XLS:
		// return filePath + System.getProperty("file.separator")
		// + partOfFileoutPutName + "_" + reportName + ".xls";
		// case ParameterUtil.HTML:
		// return filePath + System.getProperty("file.separator")
		// + partOfFileoutPutName + "_" + reportName + ".html";
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

		String rootPath = "/content/folder[@name='report']/folder[@name='工商制式报表']/report[@name='"
				+ bo.getReportname() + "']";
		String result = this.sdk_m_visit(rootPath, bo.getMapHelper(), null,
				bo.getReportname());
		CognosService service = new CognosService();
		service.saveReport(bo, result);
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws Exception {
		String nowTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		boolean flag = true;// 是否需要按地域导入
		int i = 2;
		List<Map> br = null;// new RunCognos().cognos.getReportNames("1","1");
		// final BufferedReader br = new BufferedReader(
		// new InputStreamReader(
		// new FileInputStream("d:/test.txt")));

		String line = null;
		String params = null;
		String t[] = null;
		String districts[] = flag ? new String[] { "001", "440303", "440304",
				"440305", "440306", "440307", "440308", "440309", "440310",
				"440342", "440343", "440399" } : new String[] { "001" };
		String reportName = "内资1表";
		for (int j = 0; j < br.size(); j++) {

			for (String district : districts) {
				// t = line.split("\\s+");
				reportName = (String) br.get(i).get("reportname");
				// reportName = t[0].trim();
				// t[0];
				// reportName = "内资2表";
				// reportName= "外资2表";
				// reportName = "综合1表";
				params = "2016-11-03";
				// nowTime.substring(0,4)+"-"+nowTime.substring(4,6)+"-03";
				RunCognos cognosx = new RunCognos();
				// String
				// rootPath="/content/folder[@name='report']/folder[@name='质检报表']/report[@name='"+reportName+"']";
				// String
				// rootPath="/content/folder[@name='report']/folder[@name='工商制式报表']/report[@name='"+reportName+"']";
				// String
				// rootPath="/content/folder[@name='report']/folder[@name='业务报表']/report[@name='"+reportName+"']";
				// String
				// rootPath="/content/folder[@name='report']/folder[@name='食药报表']/report[@name='"+reportName+"']";
				String rootPath = (String) br.get(i).get("cognosPath");
				HashMap map = new HashMap();
				map.put("term", params);// 期别
				if (!"001".equals(district)) {// 区域代码
					map.put("district", district);
				}
				String result = null;
				long startTime = System.currentTimeMillis();
				try {
					System.out.println("Start get report from cognos: "
							+ reportName);
					log.info("Start get report from cognos: " + reportName);
					result = cognosx.sdk_m_visit(rootPath, map, null,
							reportName);// 从cognos获取报表
					System.out.println("End get report from cognos: "
							+ reportName + ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					log.info("End get report from cognos: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					// System.out.println(result);
					TCognosReportBO tcb = new TCognosReportBO();
					tcb.setReporttype("1");
					tcb.setRegcode(district);
					// tcb.setReportname("TestReportTime");
					tcb.setReportname(reportName);
					tcb.setMouth(params.substring(5, 7));
					tcb.setYear(params.substring(0, 4));
					// tcb.setMouth("01");
					// tcb.setYear("2015");
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss:SSS");
					String formatStr = formatter.format(new Date());
					tcb.setReporttime(formatStr);
					tcb.setReportparamters(params.substring(5, 10));// 报表参数
					// tcb.setReportparamters("01月报");
					// result=cognos.StrToBinstr(result);
					// testOracle(tcb, result);
					// Report2Stream.fromOraclePrint(tcb,result);
					result = result.replaceAll("&nbsp;</span>", "</span>");// 去掉数据项中可能的换行
					result = result.replaceAll(
							"<table.*width=\".*\"*style=\".*\">", "<table>");// 去掉列表中table的宽度设置
					System.out.println("Start load to oracle: " + reportName);
					log.info("Start load to oracle: " + reportName);
					// System.out.println(result);
					Report2Stream.printOracle(tcb, result);
					System.out.println("End load to oracle: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					log.info("End load to oracle: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					System.out
							.println("-------------------I'm a split line----------------");
					log.info("-------------------I'm a split line----------------");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("time : "
							+ (System.currentTimeMillis() - startTime) + " ms");
					log.info("time : "
							+ (System.currentTimeMillis() - startTime) + " ms");
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void debugReport(String param) {
		String reportName = "Debug报表";
		// String
		// paths[]={"/content/folder[@name='report']/folder[@name='质检报表']/report[@name='Debug报表']",
		// "/content/folder[@name='report']/folder[@name='工商制式报表']/report[@name='Debug报表']",
		// "/content/folder[@name='report']/folder[@name='业务报表']/report[@name='Debug报表']",
		// "/content/folder[@name='report']/folder[@name='食药报表']/report[@name='Debug报表']"};
		String rootPath = "/content/folder[@name='report']/folder[@name='工商制式报表']/report[@name='Debug报表']";
		String districts[] = new String[] { "001", "440303", "440304",
				"440305", "440306", "440307", "440308", "440309", "440310",
				"440342", "440343", "440399" };
		for (String district : districts) {
			RunCognos cognosx = new RunCognos();
			HashMap map = new HashMap();
			map.put("term", param);// 期别
			if (!"001".equals(district)) {// 区域代码
				map.put("district", district);
			}
			try {
				cognosx.sdk_m_visit(rootPath, map, null, reportName);// 从cognos获取报表
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void test() {
		String nowTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		boolean flag = true;//否需要按地域导入
		List<Map> br = new CognosService().getReportNamesJ("%4%");
		String params = nowTime.substring(0, 4) + "-" + nowTime.substring(4, 6)
				+ "-03";
		String districts[] = flag ? new String[] { "001", "440303", "440304",
				"440305", "440306", "440307", "440308", "440309", "440310",
				"440342", "440343", "440399" } : new String[] { "001" };
		String reportName = "内资1表";
		params = "2017-06-03";
		for (int j = -1; j < br.size(); j++) {
			if (j == -1) {
				this.debugReport(params);
				continue;
			}
			A: for (String district : districts) {
				reportName = (String) br.get(j).get("reportname");
				if (!district.equals("001")
						&& (reportName.equals("市场和质量监督管理统计")
								|| reportName.equals("深圳市企业统计表（按行业、行政区域分组）")
								|| reportName.equals("深圳市市场主体管辖区域统计表(按企业类型分组)")
								|| reportName.equals("深圳市法人企业统计表（按行业、行政区域分组）")
								|| reportName
										.equals("深圳市企业统计表（按行业、行政区域分组）(本期登记)")
								|| reportName
										.equals("深圳市市场主体管辖区域统计表(按企业类型分组)(本期登记)") || reportName
									.equals("深圳市法人企业统计表（按行业、行政区域分组）(本期登记)")
									|| reportName.startsWith("前海蛇口自贸片区"))) {
					continue A;
				}
				RunCognos cognosx = new RunCognos();
				String rootPath = (String) br.get(j).get("cognospath");
				HashMap map = new HashMap();
				map.put("term", params);// 期别
				if (!"001".equals(district)) {// 区域代码
					map.put("district", district);
				}
				String result = null;
				long startTime = System.currentTimeMillis();
				try {
					System.out.println("Start get report from cognos: "
							+ reportName);
					log.info("Start get report from cognos: " + reportName);
					result = cognosx.sdk_m_visit(rootPath, map, null,
							reportName);// 从cognos获取报表
					System.out.println("End get report from cognos: "
							+ reportName + ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					log.info("End get report from cognos: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					TCognosReportBO tcb = new TCognosReportBO();
					tcb.setReporttype((String) br.get(j).get("reporttype"));
					tcb.setRegcode(district);
					tcb.setReportname(reportName);
					tcb.setMouth(params.substring(5, 7));
					tcb.setYear(params.substring(0, 4));
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss:SSS");
					String formatStr = formatter.format(new Date());
					tcb.setReporttime(formatStr);
					tcb.setReportparamters(params.substring(5, 10));// 报表参数
					result = result.replaceAll("&nbsp;</span>", "</span>");// 去掉数据项中可能的换行
					result = result.replaceAll(
							"<table.*width=\".*\"*style=\".*\">", "<table>");// 去掉列表中table的宽度设置
					System.out.println("Start load to oracle: " + reportName);
					log.info("Start load to oracle: " + reportName);
					Report2Stream.printOracle(tcb, result);
					System.out.println("End load to oracle: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					log.info("End load to oracle: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					System.out
							.println("-------------------I'm a split line----------------");
					log.info("-------------------I'm a split line----------------");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("time : "
							+ (System.currentTimeMillis() - startTime) + " ms");
					System.out.println(rootPath);
					log.info("time : "
							+ (System.currentTimeMillis() - startTime) + " ms");
				}
			}
		}
	}
//List<Map> br = new CognosService().getReportNamesFor12315("%4%");
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getCognos12315() {
		String nowTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		//String nowTime="20170315";
		boolean flag = true;// 是否需要按地域导入
		Map<String,List<Map>> brs;
		List<String> param = new ArrayList<String>();
		
		if ("03".equals(nowTime.substring(4, 6))
				|| "09".equals(nowTime.substring(4, 6))) {
			brs=new CognosService().getReportNamesFor12315("%4%");
			param.add(nowTime.substring(0,4)+"-"+nowTime.substring(4,6)+"-02");
			param.add(nowTime.substring(0,4)+"-"+nowTime.substring(4,6)+"-03");
		} else if ("06".equals(nowTime.substring(4, 6))) {
			brs=new CognosService().getReportNamesFor12315("%3%");
			param.add(nowTime.substring(0,4)+"-06-01");
			param.add(nowTime.substring(0,4)+"-06-03");
		} else if ("12".equals(nowTime.substring(4, 6))) {
			brs=new CognosService().getReportNamesFor12315("%1%");
			param.add(nowTime.substring(0,4)+"-12-01");
			param.add(nowTime.substring(0,4)+"-12-03");
		} else {
			brs=new CognosService().getReportNamesFor12315("%1%");
			param.add(nowTime.substring(0,4)+"-"+nowTime.substring(4,6)+"-03");
		}
		// params = nowTime.substring(0,4)+"-"+nowTime.substring(4,6)+"-03";
		String districts[] = flag ? new String[] { "001", "440303", "440304",
				"440305", "440306", "440307", "440308", "440309", "440310",
				"440342", "440343", "440399" } : new String[] { "001" };
		String reportName = "内资1表";
		for (int g = 0; g < param.size(); g++) {
			List<Map> br=null;
			if ("03".equals(param.get(g).substring(8, 10))) {
				br=brs.get("yuebao");
			} else {
				br=brs.get("jibao");
			}

			for (int j = -1; j < br.size(); j++) {
				if (j == -1) {
					this.debugReport(param.get(g));
					continue;
				}
				for (String district : districts) {
					reportName = (String) br.get(j).get("reportname");
					RunCognos cognosx = new RunCognos();
					String rootPath = (String) br.get(j).get("cognospath");
					HashMap map = new HashMap();
					map.put("term", param.get(g));// 期别
					if (!"001".equals(district)) {// 区域代码
						map.put("district", district);
					}
					String result = null;
					long startTime = System.currentTimeMillis();
					try {
						log.info("Start get report from cognos: " + reportName);
						result = cognosx.sdk_m_visit(rootPath, map, null,
								reportName);// 从cognos获取报表
						log.info("End get report from cognos: " + reportName
								+ ", use time :"
								+ (System.currentTimeMillis() - startTime)
								+ " ms");
						TCognosReportBO tcb = new TCognosReportBO();
						tcb.setReporttype((String) br.get(j).get("reporttype"));
						tcb.setRegcode(district);
						tcb.setReportname(reportName);
						tcb.setMouth(param.get(g).substring(5,7));
						tcb.setYear(param.get(g).substring(0, 4));
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss:SSS");
						String formatStr = formatter.format(new Date());
						tcb.setReporttime(formatStr);
						tcb.setReportparamters(param.get(g).substring(5, 10));// 报表参数
						result = result.replaceAll("&nbsp;</span>", "</span>");// 去掉数据项中可能的换行
						result = result
								.replaceAll(
										"<table.*width=\".*\"*style=\".*\">",
										"<table>");// 去掉列表中table的宽度设置
						log.info("Start load to oracle: " + reportName);
						Report2Stream.printOracle(tcb, result);
						log.info("End load to oracle: " + reportName
								+ ", use time :"
								+ (System.currentTimeMillis() - startTime)
								+ " ms");
						log.info("-------------------I'm a split line----------------");
					} catch (Exception e) {
						e.printStackTrace();
						log.info("time : "
								+ (System.currentTimeMillis() - startTime)
								+ " ms");
					}
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getCognos12315g() {
		String nowTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		boolean flag = true;// 是否需要按地域导入
		int i;
		List<Map> br = new CognosService().getReportNamesFor12315g("%4%");
		i = br == null ? 0 : br.size();
		String params = nowTime.substring(0, 4) + "-" + nowTime.substring(4, 6);
		if ("03".equals(nowTime.substring(4, 6))
				|| "09".equals(nowTime.substring(4, 6))) {
			params += "-02";
		} else if ("06".equals(nowTime.substring(4, 6))
				|| "12".equals(nowTime.substring(4, 6))) {
			params += "-02";
		} else {
			params += "-03";
		}

		String districts[] = flag ? new String[] { "001", "440303", "440304",
				"440305", "440306", "440307", "440308", "440309", "440310",
				"440342", "440343", "440399" } : new String[] { "001" };
		String reportName = "内资1表";
		// params="2017-03-02";
		for (int j = -1; j < i; j++) {
			if (j == -1) {
				this.debugReport(params);
				continue;
			}
			for (String district : districts) {
				reportName = (String) br.get(j).get("reportname");
				RunCognos cognosx = new RunCognos();
				String rootPath = (String) br.get(j).get("cognospath");
				HashMap map = new HashMap();
				map.put("term", params);// 期别
				if (!"001".equals(district)) {// 区域代码
					map.put("district", district);
				}
				String result = null;
				long startTime = System.currentTimeMillis();
				try {
					System.out.println("Start get report from cognos: "
							+ reportName);
					log.info("Start get report from cognos: " + reportName);
					result = cognosx.sdk_m_visit(rootPath, map, null,
							reportName);// 从cognos获取报表
					System.out.println("End get report from cognos: "
							+ reportName + ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					log.info("End get report from cognos: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					TCognosReportBO tcb = new TCognosReportBO();
					tcb.setReporttype((String) br.get(j).get("reporttype"));
					tcb.setRegcode(district);
					tcb.setReportname(reportName);
					tcb.setMouth(params.substring(5, 7));
					tcb.setYear(params.substring(0, 4));
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss:SSS");
					String formatStr = formatter.format(new Date());
					tcb.setReporttime(formatStr);
					tcb.setReportparamters(params.substring(5, 10));// 报表参数
					result = result.replaceAll("&nbsp;</span>", "</span>");// 去掉数据项中可能的换行
					result = result.replaceAll(
							"<table.*width=\".*\"*style=\".*\">", "<table>");// 去掉列表中table的宽度设置
					System.out.println("Start load to oracle: " + reportName);
					log.info("Start load to oracle: " + reportName);
					Report2Stream.printOracle(tcb, result);
					System.out.println("End load to oracle: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					log.info("End load to oracle: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					System.out
							.println("-------------------I'm a split line----------------");
					log.info("-------------------I'm a split line----------------");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("time : "
							+ (System.currentTimeMillis() - startTime) + " ms");
					System.out.println(rootPath);
					log.info("time : "
							+ (System.currentTimeMillis() - startTime) + " ms");
				}
			}
		}
	}

	// 获取月报
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getYueBao() throws Exception {
		String nowTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		//String nowTime="20170315";
		boolean flag = true;// 是否需要按地域导入
		Map<String,List<Map>> brs;
		List<String> param = new ArrayList<String>();
		
		if ("03".equals(nowTime.substring(4, 6))
				|| "09".equals(nowTime.substring(4, 6))) {
			brs=new CognosService().getReportNames("%2%");
			param.add(nowTime.substring(0,4)+"-"+nowTime.substring(4,6)+"-02");
			param.add(nowTime.substring(0,4)+"-"+nowTime.substring(4,6)+"-03");
		} else if ("06".equals(nowTime.substring(4, 6))) {
			brs=new CognosService().getReportNames("%3%");
			param.add(nowTime.substring(0,4)+"-06-01");
			param.add(nowTime.substring(0,4)+"-06-03");
		} else if ("12".equals(nowTime.substring(4, 6))) {
			brs=new CognosService().getReportNames("%4%");
			param.add(nowTime.substring(0,4)+"-12-01");
			param.add(nowTime.substring(0,4)+"-12-03");
		} else {
			brs=new CognosService().getReportNames("%1%");
			param.add(nowTime.substring(0,4)+"-"+nowTime.substring(4,6)+"-03");
		}
		// params = nowTime.substring(0,4)+"-"+nowTime.substring(4,6)+"-03";
		String districts[] = flag ? new String[] { "001", "440303", "440304",
				"440305", "440306", "440307", "440308", "440309", "440310",
				"440342", "440343", "440399" } : new String[] { "001" };
		String reportName = "内资1表";
		for (int g = 0; g < param.size(); g++) {
			List<Map> br=null;
			if ("03".equals(param.get(g).substring(8, 10))) {
				br=brs.get("yuebao");
			} else {
				br=brs.get("jibao");
			}

			for (int j = -1; j < br.size(); j++) {
				if (j == -1) {
					this.debugReport(param.get(g));
					continue;
				}
				B: for (String district : districts) {
					reportName = (String) br.get(j).get("reportname");
					if (!district.equals("001")
							&& (reportName.equals("市场和质量监督管理统计")
									|| reportName
											.equals("深圳市企业统计表（按行业、行政区域分组）")
									|| reportName
											.equals("深圳市市场主体管辖区域统计表(按企业类型分组)")
									|| reportName
											.equals("深圳市法人企业统计表（按行业、行政区域分组）")
									|| reportName
											.equals("深圳市企业统计表（按行业、行政区域分组）(本期登记)")
									|| reportName
											.equals("深圳市市场主体管辖区域统计表(按企业类型分组)(本期登记)") || reportName
										.equals("深圳市法人企业统计表（按行业、行政区域分组）(本期登记)")||
										reportName.startsWith("前海蛇口自贸片区"))) {
						continue B;
					}
					RunCognos cognosx = new RunCognos();
					String rootPath = (String) br.get(j).get("cognospath");
					HashMap map = new HashMap();
					map.put("term", param.get(g));// 期别
					if (!"001".equals(district)) {// 区域代码
						map.put("district", district);
					}
					String result = null;
					long startTime = System.currentTimeMillis();
					try {
						log.info("Start get report from cognos: " + reportName);
						result = cognosx.sdk_m_visit(rootPath, map, null,
								reportName);// 从cognos获取报表
						log.info("End get report from cognos: " + reportName
								+ ", use time :"
								+ (System.currentTimeMillis() - startTime)
								+ " ms");
						TCognosReportBO tcb = new TCognosReportBO();
						tcb.setReporttype((String) br.get(j).get("reporttype"));
						tcb.setRegcode(district);
						tcb.setReportname(reportName);
						tcb.setMouth(param.get(g).substring(5,7));
						tcb.setYear(param.get(g).substring(0, 4));
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss:SSS");
						String formatStr = formatter.format(new Date());
						tcb.setReporttime(formatStr);
						tcb.setReportparamters(param.get(g).substring(5, 10));// 报表参数
						result = result.replaceAll("&nbsp;</span>", "</span>");// 去掉数据项中可能的换行
						result = result
								.replaceAll(
										"<table.*width=\".*\"*style=\".*\">",
										"<table>");// 去掉列表中table的宽度设置
						log.info("Start load to oracle: " + reportName);
						Report2Stream.printOracle(tcb, result);
						log.info("End load to oracle: " + reportName
								+ ", use time :"
								+ (System.currentTimeMillis() - startTime)
								+ " ms");
						log.info("-------------------I'm a split line----------------");
					} catch (Exception e) {
						e.printStackTrace();
						log.info("time : "
								+ (System.currentTimeMillis() - startTime)
								+ " ms");
					}
				}
			}
		}
	}

	// 获取季报
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getJiBao() throws Exception {
		String nowTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		boolean flag = true;// 是否需要按地域导入
		List<Map> br = null;//new CognosService().getReportNames("%2%");
		String params = nowTime.substring(0, 4) + "-" + nowTime.substring(4, 6)
				+ "-02";
		String districts[] = flag ? new String[] { "001", "440303", "440304",
				"440305", "440306", "440307", "440308", "440309", "440310",
				"440342", "440343", "440399" } : new String[] { "001" };
		String reportName = "内资1表";

		for (int j = -1; j < br.size(); j++) {
			if (j == -1) {
				this.debugReport(nowTime.substring(0, 4) + "-"
						+ nowTime.substring(4, 6) + "-02");
				continue;
			}
			C: for (String district : districts) {
				reportName = (String) br.get(j).get("reportname");
				if (!district.equals("001")
						&& (reportName.equals("市场和质量监督管理统计")
								|| reportName.equals("深圳市企业统计表（按行业、行政区域分组）")
								|| reportName.equals("深圳市市场主体管辖区域统计表(按企业类型分组)")
								|| reportName.equals("深圳市法人企业统计表（按行业、行政区域分组）")
								|| reportName
										.equals("深圳市企业统计表（按行业、行政区域分组）(本期登记)")
								|| reportName
										.equals("深圳市市场主体管辖区域统计表(按企业类型分组)(本期登记)") || reportName
									.equals("深圳市法人企业统计表（按行业、行政区域分组）(本期登记)"))) {
					continue C;
				}
				RunCognos cognosx = new RunCognos();
				String rootPath = (String) br.get(j).get("cognospath");
				HashMap map = new HashMap();
				map.put("term", params);// 期别
				if (!"001".equals(district)) {// 区域代码
					map.put("district", district);
				}
				String result = null;
				long startTime = System.currentTimeMillis();
				try {
					log.info("Start get report from cognos: " + reportName);
					result = cognosx.sdk_m_visit(rootPath, map, null,
							reportName);// 从cognos获取报表
					log.info("End get report from cognos: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					TCognosReportBO tcb = new TCognosReportBO();
					tcb.setReporttype((String) br.get(j).get("reporttype"));
					tcb.setRegcode(district);
					tcb.setReportname(reportName);
					tcb.setMouth(params.substring(5, 7));
					tcb.setYear(params.substring(0, 4));
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss:SSS");
					String formatStr = formatter.format(new Date());
					tcb.setReporttime(formatStr);
					tcb.setReportparamters(params.substring(5, 10));// 报表参数
					result = result.replaceAll("&nbsp;</span>", "</span>");// 去掉数据项中可能的换行
					result = result.replaceAll(
							"<table.*width=\".*\"*style=\".*\">", "<table>");// 去掉列表中table的宽度设置
					log.info("Start load to oracle: " + reportName);
					Report2Stream.printOracle(tcb, result);
					log.info("End load to oracle: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					log.info("-------------------I'm a split line----------------");
				} catch (Exception e) {
					e.printStackTrace();
					log.info("time : "
							+ (System.currentTimeMillis() - startTime) + " ms");
				}
			}
		}
	}

	// 获取半年报
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getBanNianBao() throws Exception {
		String nowTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		boolean flag = true;// 是否需要按地域导入
		List<Map> br =null;// new CognosService().getReportNames("%3%");
		String params = nowTime.substring(0, 4) + "-06-01";
		String districts[] = flag ? new String[] { "001", "440303", "440304",
				"440305", "440306", "440307", "440308", "440309", "440310",
				"440342", "440343", "440399" } : new String[] { "001" };
		String reportName = "内资1表";

		for (int j = -1; j < br.size(); j++) {
			if (j == -1) {
				this.debugReport(params);
				continue;
			}
			D: for (String district : districts) {
				reportName = (String) br.get(j).get("reportname");
				if (!district.equals("001")
						&& (reportName.equals("市场和质量监督管理统计")
								|| reportName.equals("深圳市企业统计表（按行业、行政区域分组）")
								|| reportName.equals("深圳市市场主体管辖区域统计表(按企业类型分组)")
								|| reportName.equals("深圳市法人企业统计表（按行业、行政区域分组）")
								|| reportName
										.equals("深圳市企业统计表（按行业、行政区域分组）(本期登记)")
								|| reportName
										.equals("深圳市市场主体管辖区域统计表(按企业类型分组)(本期登记)") || reportName
									.equals("深圳市法人企业统计表（按行业、行政区域分组）(本期登记)"))) {
					continue D;
				}
				RunCognos cognosx = new RunCognos();
				String rootPath = (String) br.get(j).get("cognospath");
				HashMap map = new HashMap();
				map.put("term", params);// 期别
				if (!"001".equals(district)) {// 区域代码
					map.put("district", district);
				}
				String result = null;
				long startTime = System.currentTimeMillis();
				try {
					log.info("Start get report from cognos: " + reportName);
					result = cognosx.sdk_m_visit(rootPath, map, null,
							reportName);// 从cognos获取报表
					log.info("End get report from cognos: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					TCognosReportBO tcb = new TCognosReportBO();
					tcb.setReporttype((String) br.get(j).get("reporttype"));
					tcb.setRegcode(district);
					tcb.setReportname(reportName);
					tcb.setMouth(params.substring(5, 7));
					tcb.setYear(params.substring(0, 4));
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss:SSS");
					String formatStr = formatter.format(new Date());
					tcb.setReporttime(formatStr);
					tcb.setReportparamters(params.substring(5, 10));// 报表参数
					result = result.replaceAll("&nbsp;</span>", "</span>");// 去掉数据项中可能的换行
					result = result.replaceAll(
							"<table.*width=\".*\"*style=\".*\">", "<table>");// 去掉列表中table的宽度设置
					log.info("Start load to oracle: " + reportName);
					Report2Stream.printOracle(tcb, result);
					log.info("End load to oracle: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					log.info("-------------------I'm a split line----------------");
				} catch (Exception e) {
					e.printStackTrace();
					log.info("time : "
							+ (System.currentTimeMillis() - startTime) + " ms");
				}
			}
		}
	}

	// 获取年报
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getNianBao() throws Exception {
		String nowTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		boolean flag = true;// 是否需要按地域导入
		List<Map> br = null;//new CognosService().getReportNames("%4%");
		String params = nowTime.substring(0, 4) + "-12-01";
		String districts[] = flag ? new String[] { "001", "440303", "440304",
				"440305", "440306", "440307", "440308", "440309", "440310",
				"440342", "440343", "440399" } : new String[] { "001" };
		String reportName = "内资1表";

		for (int j = -1; j < br.size(); j++) {
			if (j == -1) {
				this.debugReport(params);
				continue;
			}
			E: for (String district : districts) {
				reportName = (String) br.get(j).get("reportname");
				if (!district.equals("001")
						&& (reportName.equals("市场和质量监督管理统计")
								|| reportName.equals("深圳市企业统计表（按行业、行政区域分组）")
								|| reportName.equals("深圳市市场主体管辖区域统计表(按企业类型分组)")
								|| reportName.equals("深圳市法人企业统计表（按行业、行政区域分组）")
								|| reportName
										.equals("深圳市企业统计表（按行业、行政区域分组）(本期登记)")
								|| reportName
										.equals("深圳市市场主体管辖区域统计表(按企业类型分组)(本期登记)") || reportName
									.equals("深圳市法人企业统计表（按行业、行政区域分组）(本期登记)"))) {
					continue E;
				}
				RunCognos cognosx = new RunCognos();
				String rootPath = (String) br.get(j).get("cognospath");
				HashMap map = new HashMap();
				map.put("term", params);// 期别
				if (!"001".equals(district)) {// 区域代码
					map.put("district", district);
				}
				String result = null;
				long startTime = System.currentTimeMillis();
				try {
					log.info("Start get report from cognos: " + reportName);
					result = cognosx.sdk_m_visit(rootPath, map, null,
							reportName);// 从cognos获取报表
					log.info("End get report from cognos: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					TCognosReportBO tcb = new TCognosReportBO();
					tcb.setReporttype((String) br.get(j).get("reporttype"));
					tcb.setRegcode(district);
					tcb.setReportname(reportName);
					tcb.setMouth(params.substring(5, 7));
					tcb.setYear(params.substring(0, 4));
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss:SSS");
					String formatStr = formatter.format(new Date());
					tcb.setReporttime(formatStr);
					tcb.setReportparamters(params.substring(5, 10));// 报表参数
					result = result.replaceAll("&nbsp;</span>", "</span>");// 去掉数据项中可能的换行
					result = result.replaceAll(
							"<table.*width=\".*\"*style=\".*\">", "<table>");// 去掉列表中table的宽度设置
					log.info("Start load to oracle: " + reportName);
					Report2Stream.printOracle(tcb, result);
					log.info("End load to oracle: " + reportName
							+ ", use time :"
							+ (System.currentTimeMillis() - startTime) + " ms");
					log.info("-------------------I'm a split line----------------");
				} catch (Exception e) {
					e.printStackTrace();
					log.info("time : "
							+ (System.currentTimeMillis() - startTime) + " ms");
				}
			}
		}
	}

	// 有些地址报表找不到里面的数据，得问志超或者明华。
	/*
	 * public static void main(String[] args) throws Exception { String[]
	 * reportName=new String[]{ "个体1表","个体2表","个体3表","个体4表","个体5表",
	 * "人事1表","人事2表", "人事3表","人事4表","人事5表", "人事6表","人事7表",
	 * "内资1表","内资2表","内资3表","内资4表", "农合1表",
	 * "合同1表","合同2表","商标1表","商标2表","商标3表","商标4表", "商标评审1表","商标评审2表",
	 * "外资1表","外资2表","外资3表", "市场1表","市场2表","市场3表","市场4表", "广告1表","广告2表","广告3表",
	 * "无照经营1表","法制1表","法制2表","法制3表", "消保1表","消保2表","消保3表","消保4表","消保5表",
	 * "消保6表","直销1表","直销2表", "竞争执法1表","竞争执法2表","竞争执法3表",
	 * "综合1表","综合2表","综合3表","综合4表", "综合5表","综合6表","食品1表","食品2表"}; RunCognos
	 * cognosx=new RunCognos(); Connection con = null;// 创建一个数据库连接 为这个旅行包被巡逻民
	 * System.out.println("开始尝试连接数据库！"); String url =
	 * "jdbc:oracle:thin:@10.3.70.134:1521:oragxk";//
	 * 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名 String user = "db_dc";// 用户名,系统默认的账户名
	 * String password = "db_dc";// 你安装时选设置的密码 System.out.println("连接成功！");
	 * for(int i=10;i<reportName.length;i++){ con =
	 * DriverManager.getConnection(url, user, password);// 获取连接 String rootPath=
	 * "/content/folder[@name='report']/folder[@name='工商制式报表']/report[@name='"
	 * +reportName[i]+"']"; HashMap map=new HashMap(); map.put("term",
	 * "2016-01-03"); // map.put("unit", "440200"); String result=null; try {
	 * result=cognosx.sdk_m_visit(rootPath,map , null, reportName[i]);
	 * System.out.println(); } catch (Exception e) { System.out.println(i); }
	 * 
	 * //bo.getRegcode() result bo.getReporttype() bo.getReporttime()
	 * //bo.getReportparamters() bo.getReportname为这个旅行包被巡逻民() bo.getMouth()
	 * bo.getYear() TCognosReportBO tcb=new TCognosReportBO();
	 * tcb.setReporttype(i+""); tcb.setRegcode("深圳市");
	 * tcb.setReportname(reportName[i]); tcb.setMouth("01");
	 * tcb.setYear("2016"); SimpleDateFormat formatter = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"); String formatStr
	 * =formatter.format(new Date()); tcb.setReporttime(formatStr);
	 * tcb.setReportparamters("01-03"); //result=cognos.StrToBinstr(result);
	 * //testOracle(tcb, result); //Report2Stream.fromOraclePrint(tcb,result);
	 * 
	 * Report2Stream.printOracle(tcb,result);
	 * 
	 * // String rootPath=
	 * "/content/folder[@name='report']/folder[@name='工商制式报表']/report[@name='外资1表']"
	 * ; // HashMap map=new HashMap(); // map.put("term", "2016-08-03"); //
	 * map.put("unit", "440200"); // cognosx.sdk_m_visit(rootPath, map, null,
	 * "外资1表"); // con.close(); } }
	 */

}