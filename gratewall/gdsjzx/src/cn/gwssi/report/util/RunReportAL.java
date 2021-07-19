package cn.gwssi.report.util;

/* 
 * 运行导出报表 
 */  
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import com.cognos.developer.schemas.bibus._3.AsynchDetailReportOutput;
import com.cognos.developer.schemas.bibus._3.AsynchDetailReportStatus;
import com.cognos.developer.schemas.bibus._3.AsynchDetailReportStatusEnum;
import com.cognos.developer.schemas.bibus._3.AsynchOptionEnum;
import com.cognos.developer.schemas.bibus._3.AsynchOptionInt;
import com.cognos.developer.schemas.bibus._3.AsynchReply;
import com.cognos.developer.schemas.bibus._3.AsynchReplyStatusEnum;
import com.cognos.developer.schemas.bibus._3.AsynchSecondaryRequest;
import com.cognos.developer.schemas.bibus._3.BaseClass;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_PortType;
import com.cognos.developer.schemas.bibus._3.Option;
import com.cognos.developer.schemas.bibus._3.Output;
import com.cognos.developer.schemas.bibus._3.OutputEncapsulationEnum;
import com.cognos.developer.schemas.bibus._3.ParameterValue;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.RunOptionBoolean;
import com.cognos.developer.schemas.bibus._3.RunOptionEnum;
import com.cognos.developer.schemas.bibus._3.RunOptionLanguageArray;
import com.cognos.developer.schemas.bibus._3.RunOptionOutputEncapsulation;
import com.cognos.developer.schemas.bibus._3.RunOptionStringArray;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.SearchPathSingleObject;
import com.cognos.developer.schemas.bibus._3.Sort;

/** 
 * 运行报表 
 *  
 * @author  
 */  
public class RunReportAL  {  
	/** 
//  * 设置报表参数AL 
//  */  
	//private SetReportParameterAL setReportParameterAL;  
	//public SetReportParameterAL getSetReportParameterAL() {  
	//  return setReportParameterAL;  
	//}  
	//public void setSetReportParameterAL(  
	//   SetReportParameterAL setReportParameterAL) {  
	//  this.setReportParameterAL = setReportParameterAL;  
	//}  
	/** 
	 * 运行报表 
	 *  
	 * @param connect 
	 *            报表服务器连接AL 
	 * @param reportSearchPath 
	 *            报表搜索路径 
	 * @param reportType 
	 *            报表类型：1:XLS 2:HTML 
	 * @param parameters 
	 *            报表参数helper 
	 * @return 
	 * @throws IOException 
	 */  
	public String run(CognosServerConnectAL connect, String reportSearchPath,  
			int reportType, Map parameters) throws IOException { 
		String output = null;  
		try{  
			if ((connect != null) && (reportSearchPath != null)) {  
				/* 通过reportSearchPath【报表存放路劲】获得报表对象 
				 */  
				BaseClass[] reportDomain = getRoportModel(reportSearchPath, connect.getCMService());  
				if (reportDomain.length > 0) {  
					ParameterValue[] emptyParameterValues = new ParameterValue[] {}; // 空参  
					ParameterValue[] reportParameterValues = null;//  
					String compagers = null;  
					String savePath = null;  
					String partOfFileoutPutName = null;//生成报表静态文件名称  
					if (parameters != null) {  
						// 设置报表参数  
						//     reportParameterValues = setReportParameterAL  
						//       .setReportParameters(parameters);  

						compagers=(String) parameters.get("compagers");
						savePath=(String) parameters.get("savePath");

						//     compagers = parameters.getCompagers();  
						//     savePath = parameters.getSavePath();  
						/* 
     文件名组成：报表ID+报表英文名称 
						 */  
						partOfFileoutPutName ="wz1";
						//    		 parameters.getReportPutInfoDomain()  
						//       .getReportID()  
						//       + parameters.getReportPutInfoDomain()  
						//         .getReportEnglishName();  
						//    }  
						//如果传入的参数数组为空则数组为null  
						if ((parameters == null)  
								|| (parameters.size()<= 0)) {  
							//emptyParameterValues  上面定义的一组空参(执行报表必须要有参数数组，数组内容可为空)  
							reportParameterValues = emptyParameterValues;  
						}  
						//执行报表  
						/* 
     reportSearchPath:报表存放地址 
     connect：连接对象 
     reportType：报表文件类型（html,excel） 
     reportParameterValues：参数数组 
						 */  
						output = executeReport(reportSearchPath, connect, reportType,  
								reportParameterValues, compagers, savePath,  
								partOfFileoutPutName);  

					}  }}
			return output;}
		catch (Exception e) {  	
			e.printStackTrace();  
			return output;  
		}  
	}  
	/** 
	 * 执行报表 
	 *  
	 * @param reportSearchPath 
	 *            报表搜索路径 
	 * @param connect 
	 *            报表服务器连接AL 
	 * @param reportType 
	 *            报表类型：1:XLS 2:HTML 
	 * @param paramValueArray 
	 *            报表参数 
	 * @param compagers 
	 * @param savePath 
	 *            保存路径 
	 * @param partOfFileoutPutName 
	 *            报表英文名称 
	 * @return 
	 * @throws IOException 
	 */  
	private String executeReport(String reportSearchPath,  
			CognosServerConnectAL connect, int reportType,  
			ParameterValue[] paramValueArray, String compagers,  
			String savePath, String partOfFileoutPutName) throws IOException {  
		try{  
			Option[] execReportRunOptions = getRunOptions(reportType); // 报表运行配置  
			AsynchReply rsr = null; // 报表内容  
			// main  
			rsr = connect.getReportService().run(  
					new SearchPathSingleObject(reportSearchPath), paramValueArray,  
					execReportRunOptions);  
			// 输出对象没有立刻获得时，执行等待，直到获得输出对象为止  
			if (!rsr.getStatus().equals(AsynchReplyStatusEnum.complete)  
					&& !rsr.getStatus().equals(  
							AsynchReplyStatusEnum.conversationComplete)) {  
				while (!rsr.getStatus().equals(AsynchReplyStatusEnum.complete)  
						&& !rsr.getStatus().equals(  
								AsynchReplyStatusEnum.conversationComplete)) {  
					// 执行等待之前，进行确认  
					if (!hasSecondaryRequest(rsr, "wait")) {  
						return null;  
					}  
					rsr = connect.getReportService().wait(rsr.getPrimaryRequest(),  
							new ParameterValue[] {}, new Option[] {});  
				}  
				// 确保获得输出对象前，输出对象已经完成提交  
				if (outputIsReady(rsr)) {  
					rsr = connect.getReportService().getOutput(  
							rsr.getPrimaryRequest(), new ParameterValue[] {},  
							new Option[] {});  
				} else {  
					return null;  
				}  
			}  
			// 导出  
			return textOrBinaryOutput(connect, rsr, partOfFileoutPutName,  
					reportType, compagers, savePath);  
		}catch (Exception e) {  
			e.printStackTrace();  
			return null;  
		}  
	}  
	/** 
	 * 报表运行选项设置 
	 *  
	 * @param reportType 
	 * @return 
	 */  
	private Option[] getRunOptions(int reportType) {  
		//定义运行报表选项数组  
		Option[] execReportRunOptions = new Option[7];  
		//指定是否应该将报表输出保存到内容库中  
		RunOptionBoolean saveOutputRunOption = new RunOptionBoolean();  
		//导出文件格式  
		RunOptionStringArray outputFormat = new RunOptionStringArray();  
		//是否跳过提示页面  
		RunOptionBoolean promptFlag = new RunOptionBoolean();  
		//指定存储输出的位置  
		RunOptionOutputEncapsulation outputEncapsulation = new RunOptionOutputEncapsulation();  
		saveOutputRunOption.setName(RunOptionEnum.saveOutput);  
		saveOutputRunOption.setValue(false);//true为保存，false反之  
		outputFormat.setName(RunOptionEnum.outputFormat);  
		String[] reportFormat = null;  
		reportFormat = setFormatByType(reportType);  
		outputFormat.setValue(reportFormat);//setValue值应为String类型的数组  
		promptFlag.setName(RunOptionEnum.prompt);  
		promptFlag.setValue(false);//false：跳过提示页面运行报表  
		//开始之前等待报表完成的初始时间，以秒为单位，默认值为 7 秒  
		AsynchOptionInt primaryWaitThreshold = new AsynchOptionInt();  
		primaryWaitThreshold.setName(AsynchOptionEnum.primaryWaitThreshold);  
		primaryWaitThreshold.setValue(20);  

		//在匿名会话的过程中，等待状态检查的时间，以秒为单位  
		AsynchOptionInt secondaryWait = new AsynchOptionInt();  
		secondaryWait.setName(AsynchOptionEnum.secondaryWaitThreshold);  
		secondaryWait.setValue(65);  

		//设置语言本地化  
		RunOptionLanguageArray outputLocale = new RunOptionLanguageArray();  
		outputLocale.setName(RunOptionEnum.outputLocale);  
		outputLocale.setValue(new String[]{"zh"});  

		outputEncapsulation.setName(RunOptionEnum.outputEncapsulation);  
		/* 
  枚举none：输出不存入临时内容库对象中 
      (如果报表输出的内容较大如果保存至临时内容库会造成内存溢出 
		 */  
		outputEncapsulation.setValue(OutputEncapsulationEnum.none);  
		//添加各个运行选项  
		execReportRunOptions[0] = saveOutputRunOption;  
		execReportRunOptions[1] = outputFormat;  
		execReportRunOptions[2] = promptFlag;  
		execReportRunOptions[3] = primaryWaitThreshold;  
		execReportRunOptions[4] = outputLocale;  
		execReportRunOptions[5] = outputEncapsulation;  
		execReportRunOptions[6] = secondaryWait;  
		//返回 选项 数组  
		return execReportRunOptions; 

	}  
	/** 
	 * 设置导出格式 
	 *  
	 * @param fileType 
	 *            1:XLS 2:HTML 
	 * @return 
	 */  
	private String[] setFormatByType(int fileType) {  
//		switch (fileType) { 
//
//		case ParameterUtil.XLS:  
//			return new String[] { "singleXLS" };//excel格式  
//		case ParameterUtil.HTML:  
//			return new String[] { "HTML" };//html格式  
//		default:  
			return new String[] { "singleXLS" };  
//		}  
		/* 
   格式还可以为CSV , HTMLFragment, MHT, PDF, singleXLS, XHTML, XLS, XLWA, XML 
		 */  
	}  
	/** 
	 * 检查响应是否进入了secondaryRequest状态 
	 *  
	 * @param response 
	 *            报表内容 
	 * @param secondaryRequest 
	 *            wait 
	 * @return 
	 */  
	private boolean hasSecondaryRequest(AsynchReply response,  
			String secondaryRequest) {  
		AsynchSecondaryRequest[] secondaryRequests = response  
				.getSecondaryRequests();  
		for (int i = 0; i < secondaryRequests.length; i++) {  
			if (secondaryRequests[i].getName().compareTo(secondaryRequest) == 0) {  
				return true;  
			}  
		}  
		return false;  
	}  
	/** 
	 * 输出对象是否已经完全获得 
	 *  
	 * @param response 
	 *            报表内容 
	 * @return 
	 */  
	private boolean outputIsReady(AsynchReply response) {  
		for (int i = 0; i < response.getDetails().length; i++) {  
			if ((response.getDetails()[i] instanceof AsynchDetailReportStatus) && (((AsynchDetailReportStatus) response.getDetails()[i])  
					.getStatus() == AsynchDetailReportStatusEnum.responseReady)  
					&& (hasSecondaryRequest(response, "getOutput"))) { 
				
				
				return true;  
			}  
		}  
		return false;  
	}  
	/** 
	 * 生成不同格式的报表 
	 *  
	 * @param connect 
	 *            报表服务器连接AL 
	 * @param rsr 
	 *            报表内容 
	 * @param partOfFileoutPutName 
	 *            报表英文名称 
	 * @param reportType 
	 *            报表类型 
	 * @param compagers 
	 * @param savePath 
	 *            保存路径 
	 * @return 
	 * @throws IOException 
	 */  
	private String textOrBinaryOutput(CognosServerConnectAL connect,  
			AsynchReply rsr, String partOfFileoutPutName, int reportType,  
			String compagers, String savePath) throws IOException {  
		String textOutput = null;  
//		if (reportType == ParameterUtil.XLS) {  
			textOutput = saveBinaryOutput(connect, rsr, partOfFileoutPutName,  
					reportType, compagers, savePath);  
//		}  
//		if (reportType == ParameterUtil.HTML) {  
//			textOutput = getOutputPage(connect, rsr, partOfFileoutPutName,  
//					reportType, compagers, savePath);  
//		}  
		return textOutput;  
	}  
	/** 
	 * 导出xls格式的报表，并且写入文件 
	 *  
	 * @param connection 
	 * @param response 
	 * @param partOfFileoutPutName 
	 * @param reportType 
	 * @param compagers 
	 * @param savePath 
	 * @return 
	 * @throws IOException 
	 */  
	private String saveBinaryOutput(CognosServerConnectAL connection,  
			AsynchReply response, String partOfFileoutPutName, int reportType,  
			String compagers, String savePath) throws IOException {  
		String fileSaveUrl = null;  
		byte[] binaryOutput = null;  
		AsynchDetailReportOutput reportOutput = null;  
		for (int i = 0; i < response.getDetails().length; i++) {  
			if (response.getDetails()[i] instanceof AsynchDetailReportOutput) {  
				reportOutput = (AsynchDetailReportOutput) response.getDetails()[i];  
				break;  
			}  
		}  
		binaryOutput = (reportOutput.getOutputPages()[0]).getBytes("UTF-8");  
		if (binaryOutput == null) {  
			return null;  
		}  
		//创建文件  
		createNewFile(savePath);  
		File oFile = new File(setFilenameByType(savePath, partOfFileoutPutName,  
				reportType, compagers));  
		FileOutputStream fos = new FileOutputStream(oFile);  
		fos.write(binaryOutput);  
		fos.flush();  
		fos.close();  
		//获取绝对路劲  
		fileSaveUrl = oFile.getAbsolutePath();  
		return fileSaveUrl;  
	}  
	/** 
	 * 导出html格式的报表,并且写入文件 
	 *  
	 * @param connect 
	 * @param response 
	 * @param partOfFileoutPutName 
	 * @param reportType 
	 * @param compagers 
	 * @param savePath 
	 * @return 
	 * @throws IOException 
	 */  
	private String getOutputPage(CognosServerConnectAL connect,  
			AsynchReply response, String partOfFileoutPutName, int reportType,  
			String compagers, String savePath) throws IOException {  
		String fileSaveUrl = null;  
		String textOutput = "";  
		AsynchDetailReportOutput reportOutput = null;  
		for (int i = 0; i < response.getDetails().length; i++) {  
			if (response.getDetails()[i] instanceof AsynchDetailReportOutput) {  
				reportOutput = (AsynchDetailReportOutput) response.getDetails()[i];  
				break;  
			}  
		}  
		if (reportOutput == null) {  
			return null;  
		}  
		if (reportOutput.getOutputObjects().length > 0) {  
			textOutput = replaceLocalGraphicsInOutput(connect, reportOutput,  
					savePath, partOfFileoutPutName, compagers);  
		} else {  
			textOutput = reportOutput.getOutputPages()[0].toString();  
		}  
		createNewFile(savePath);  
		File oFile = new File(setFilenameByType(savePath, partOfFileoutPutName,  
				reportType, compagers));  
		FileOutputStream fos = new FileOutputStream(oFile);  
		fos.write(textOutput.getBytes("UTF-8"));  
		fos.flush();  
		fos.close();  
		fileSaveUrl = oFile.getAbsolutePath();  
		return fileSaveUrl;  
	}  
	/** 
	 * 包含图表的报表特殊处理 
	 * 这一部分不是很熟悉 
	 * @param connect 
	 * @param reportOutput 
	 * @param savePath 
	 * @param partOfFileoutPutName 
	 * @param compages 
	 * @return 
	 * @throws IOException 
	 */  
	private String replaceLocalGraphicsInOutput(CognosServerConnectAL connect,  
			AsynchDetailReportOutput reportOutput, String savePath,  
			String partOfFileoutPutName, String compages) throws IOException {  
		BaseClass[] bcGraphic;  
		SearchPathMultipleObject graphicSearchPath = new SearchPathMultipleObject();  
		graphicSearchPath.set_value(reportOutput.getOutputObjects()[0]  
				.getSearchPath().getValue());  
		bcGraphic = connect.getCMService().query(graphicSearchPath,  
				new PropEnum[] { PropEnum.searchPath }, new Sort[] {},  
				new QueryOptions());  
		Output out = null;  
		if ((bcGraphic.length > 0) && (bcGraphic[0] instanceof Output)) {  
			SearchPathMultipleObject outSearchPath = new SearchPathMultipleObject();  
			out = (Output) bcGraphic[0];  
			outSearchPath.set_value(out.getSearchPath().getValue() + "/graphic");  
			BaseClass[] g = connect.getCMService().query(  
					outSearchPath,  
					new PropEnum[] { PropEnum.searchPath, PropEnum.data,  
							PropEnum.dataType }, new Sort[] {},  
							new QueryOptions());  
			StringBuffer finalHtml = new StringBuffer();  
			String[] pages = reportOutput.getOutputPages();  
			String html = pages[0].toString();  
			String start = null;  
			String end = null;  
			String graphicFile = null;  
			// 替换html文件中img标签的属性  
			for (int i = 0; i < g.length; i++) {  
				String pictrueName = partOfFileoutPutName + "_" + compages + i  
						+ ".png";  
				graphicFile = savePath + "/" + pictrueName;  
				// 图表保存本地  
				File gFile = new File(graphicFile);  
				FileOutputStream fos = new FileOutputStream(gFile);
//				fos.write(((Graphic)  g.getData().getValue());  
				fos.flush();  
				fos.close();  
				int index = 0;  
				index = html.indexOf("<img", 0);  
				start = html.substring(0, index);  
				end = html.substring(html.indexOf(">", index) + 1);  
				finalHtml.append(start + "<img src='" + pictrueName + "'>");  
				html = end;  
			}  
			finalHtml.append(html);  
			return finalHtml.toString();  
		}  
		return null;  
	}  
	/** 
	 * 根据格式设置文件名 
	 *  
	 * @param filePath文件路劲 
	 * @param reportName文件名称 
	 * @param fileType文件类型 
	 * @return 
	 */  
	private String setFilenameByType(String filePath,  
			String partOfFileoutPutName, int fileType, String compagers) {  
		switch (fileType) {  

//		case Parameter.XLS:  
//			return filePath + System.getProperty("file.separator")  
//					+ partOfFileoutPutName + "_" + compagers + ".xls";  
//		case ParameterUtil.HTML:  
//			return filePath + System.getProperty("file.separator")  
//					+ partOfFileoutPutName + "_" + compagers + ".html";  
		default:  
			 return filePath + System.getProperty("file.separator")  
					+ partOfFileoutPutName + "_" + compagers + ".xls";  
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
	/** 
	 * 内容库报表对象查询 
	 *  
	 * @param searchPath 
	 * @return 
	 * @throws RemoteException 
	 */  
	private BaseClass[] getRoportModel(String searchPath,  
			ContentManagerService_PortType cmService) throws RemoteException {  
		PropEnum[] props = { PropEnum.searchPath };  
		BaseClass[] bc = null;  
		SearchPathMultipleObject spMulti = new SearchPathMultipleObject();  
		spMulti.set_value(searchPath);  
		bc = cmService.query(spMulti, props, new Sort[] {}, new QueryOptions());  
		return bc;  
	}  
	
	
	public static void main(String[] args) throws Exception {
		RunReportAL al=new RunReportAL();
		CognosServerConnectAL cognosServer=new CognosServerConnectAL();
		cognosServer.set();
		al.run(cognosServer, "/content/folder[@name='report']/folder[@name='工商制式报表']/report[@name='外资1表']", 1, null);
	}
}  