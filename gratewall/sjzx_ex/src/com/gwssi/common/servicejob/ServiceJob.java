package com.gwssi.common.servicejob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.util.AnalyCollectFile;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.ftp.FtpUtil;
import com.gwssi.resource.svrobj.vo.VoResDataSource;
import com.gwssi.share.ftp.vo.VoShareFtpService;
import com.gwssi.webservice.server.CheckService;
import com.gwssi.webservice.server.GSGeneralWebService;
import com.gwssi.webservice.server.ServerInfo;

public class ServiceJob implements StatefulJob
{
	protected static Logger	logger		= TxnLogger.getLogger(ServiceJob.class
												.getName());	// 日志

	ServerInfo				serverInfo	= new ServerInfo();

	/**
	 * 共享服务定时任务
	 */
	public void execute(JobExecutionContext jobCtx)
	{
		// 业务逻辑
		// 1.首先申明所有的共享服务都是被动的等待用户来访问交换平台接口
		// 2.只有FTP是需要主动的在指定时间内将接口的数据生成文件存放到FTP服务器上供用户来取
		// 3.在指定时间调用doTask方法获取数据
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String nowTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		logger.debug("当前时间为：" + nowTime + " 开始共享任务调度ServiceJob_execute...");
		String taskId = jobCtx.getTrigger().getName(); // 任务ID（即：service id）

		String startStr = ""; // 开始时间
		String endStr = ""; // 结束时间
		JobDataMap dataMap = jobCtx.getJobDetail().getJobDataMap();
		// System.out.println("taskId is " + taskId);
		logger.debug("srvId is " + dataMap.get("srvId"));
		// System.out.println("startTime is " + dataMap.get("startTime"));
		// System.out.println("endTime is " + dataMap.get("endTime"));
		if (null != dataMap && !dataMap.isEmpty()) {
			if (null != dataMap.get("startTime"))
				startStr = dataMap.get("startTime").toString();
			if (null != dataMap.get("endTime"))
				endStr = dataMap.get("endTime").toString();
		} else {
			logger.debug("此条任务调度时间有问题...");
		}
		if (!"".equals(startStr) && !"".equals(endStr)) {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			String calNow = sd.format(new java.util.Date());
			String startTime = calNow + " " + startStr + ":00";
			String endTime = calNow + " " + endStr + ":00";

			// 比较系统当前时间和任务调度开始时间、结束时间
			CheckService check = new CheckService();
			boolean isIn = check.isInDatesForJob(nowTime.toString(), startTime,
					endTime);

			if (isIn) {
				try {
					this.doTask(taskId);
				} catch (FileNotFoundException e) {
					logger.info("执行共享服务FTP调度失败");
					e.printStackTrace();
				}
			} else {
				logger.debug("在任务调度时间范围外...");
			}
		} else {
			logger.debug("此条任务调度时间有问题...");
		}
	}

	/**
	 * 
	 * doTask 执行共享服务的任务调度
	 * 
	 * @param taskId
	 *            void
	 * @throws FileNotFoundException 
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private void doTask(String taskId) throws FileNotFoundException
	{
		// 1.根据taskID即：service id查询FTP服务的信息
		// 2.获取接口参数信息
		// 3.将参数进行格式化，便于调用服务
		logger.info("共享FTP任务 id="+taskId+" 开始执行");
		
		/**获取当前日期作为文件名日期后缀**/
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String date= df.format(calendar.getTime());
		
		/**获取任务相关信息**/
		Map ftpService = serverInfo.queryFtpService(taskId);
		VoShareFtpService ftpServiceVo = new VoShareFtpService();
		ParamUtil.mapToBean(ftpService, ftpServiceVo, false);

		// 获取文件名称类型
		String fileName = ftpServiceVo.getFile_name();
		String fileType = ftpServiceVo.getFile_type();
		if ("01".equals(fileType)) {
			//以文件生成日期为后缀			
			fileName = fileName +"_"+ date +".txt";
		}
		
		/**获取并格式化任务参数**/
		String ftpServiceId = ftpServiceVo.getFtp_service_id();
		List paramList = serverInfo.queryFtpParam(ftpServiceId); // 获取参数
		Map param = serverInfo.formatParam(paramList); // 格式化参数		
		param.put("USER_TYPE", "SHARE_FTP"); //设置共享FTP任务专用用户类型标识
		
		//若未设置开始记录数和结束揭露两个参数，则为这两个参数设置默认值
		if(!param.containsKey(ShareConstants.SERVICE_OUT_PARAM_KSJLS)){
			param.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS, "1");
		}
		int interval=5000;//每次取数据的增量间隔 默认为5000条
		if(param.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS)==null
				|| param.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS)==""){
			param.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS, "5000");
			
		}else{
			//以设置的结束记录数作为每次取数据的量
			interval= Integer.parseInt((String)param.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));
		}
		/**数据获取与文件生成**/
		try {
			GSGeneralWebService gs = new GSGeneralWebService();
			
			int zts=0;//记录已采集到的数据总条数
			boolean hasMore=false;
			StringBuffer filecontext = new StringBuffer();//文件内容
			String[] colNameEnArray=null;//英文列名
			long start = System.currentTimeMillis();
			do{
				System.out.println("参数为："+param);
				/**调用服务获取数据**/
				Map result = gs.query(param); // 调用query接口获取结果
				if(result.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)!=null){
					String returnCode=result.get(ShareConstants.SERVICE_OUT_PARAM_FHDM).toString();
					if(returnCode!=ShareConstants.SERVICE_FHDM_SUCCESS && returnCode!=ShareConstants.SERVICE_FHDM_NO_RESULT){
						//查询报错不生成文件
						logger.info("获取数据失败未生成文件 任务ID="+taskId+"  返回码="+returnCode);
						return;
					}else if(returnCode==ShareConstants.SERVICE_FHDM_NO_RESULT)
					{
						//无数据
						logger.info("无数据，生成空文件 任务ID="+taskId+"  返回码="+returnCode);
						break;
					}
				}
				
				/**结果数据处理**/
				//long s=System.currentTimeMillis();
				if(!hasMore){
					/**只有第一次执行时添加标题行**/
					Map tepMap = gs.getOhterColumn(taskId);
					String columnStrEn = tepMap.get("column_name_en").toString();
					//String columnStrCn = tepMap.get("column_name_cn").toString();
					colNameEnArray = columnStrEn.split(",");
					for (int i = 0; i < colNameEnArray.length; i++) {
						if (i == (colNameEnArray.length - 1)) {
							filecontext.append(colNameEnArray[i] + "\r\n");
						} else {
							filecontext.append(colNameEnArray[i] + "@#");
						}
					}					
				}
				
				/**数据量判断**/
				String ztsCount = (String)result.get("ZTS");//数据总条数
				//结束记录数
				String jsjls= (String)result.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS);
				if(ztsCount!=null && jsjls!=null ){
					zts=Integer.parseInt(ztsCount);
					int jsjlsInt=Integer.parseInt(jsjls);
					if(jsjlsInt<zts){
						logger.debug("数据量过大，需要分多次读取");
						hasMore=true;//标识位						
						param.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS,String.valueOf(jsjlsInt+1) );
						if(zts-interval>jsjlsInt){
							//每次取interval条
							param.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS,String.valueOf(jsjlsInt+interval) );
						}else {
							//剩余数据不足interval条
							param.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS,String.valueOf(zts) );
						}						
					}else{
						//没有数据了
						hasMore=false;
					}
					
				}
				
				HashMap[] filemapArray = (HashMap[]) result.get("GSDJ_INFO_ARRAY");				
				if (filemapArray != null && filemapArray.length != 0) {
					
					//数据内容
					for (int i = 0; filemapArray != null && i < filemapArray.length; i++) {
						for (int j = 0; j < colNameEnArray.length; j++) {
							HashMap tmpMap1 = (HashMap) filemapArray[i];
							String tmpStr = "";
							if (tmpMap1.get(colNameEnArray[j].toUpperCase()) != null) {
								tmpStr = tmpMap1.get(
										colNameEnArray[j].toUpperCase()).toString();
							}
							if (j == (colNameEnArray.length - 1)) {
								filecontext.append(replaceBlank(tmpStr.trim())
										+ "\r\n");
							} else {
								filecontext.append(replaceBlank(tmpStr.trim())
										+ "@#");
							}
						}
					}
				}
				//long e=System.currentTimeMillis();
				//logger.debug("数据整理耗时："+(e-s)/1000f+"秒");
			}while(hasMore);
			long end = System.currentTimeMillis();
			logger.debug("数据获取耗时："+(end-start)/1000f+"秒");
			/**根据获取到的数据生成数据文件**/
			long start1 = System.currentTimeMillis();
			// 创建存放数据文件的文件名
			StringBuffer dataFileName = new StringBuffer();
			dataFileName.append(ExConstant.FILE_FTP);
			dataFileName.append(File.separator);
			dataFileName.append(fileName);
			//dataFileName.append(".txt");
			System.out.println("dataFileName is " + dataFileName);
			//创建数据校验文件
			StringBuffer checkFileContext=new StringBuffer();
			checkFileContext.append(ftpServiceVo.getFile_name())
				.append("_").append(date).append("@#").append(String.valueOf(zts)).append("\r\n");
			StringBuffer checkFileName=new StringBuffer();
			checkFileName.append(ExConstant.FILE_FTP);
			checkFileName.append(File.separator);
			String loginName="";
			if(param.get("LOGIN_NAME")!=null){				
				loginName=param.get("LOGIN_NAME").toString();
			}			
			if(StringUtils.isNotBlank(loginName)){
				//为避免校验文件混淆，生成的校验文件在本地先按登录名分文件夹保存
				checkFileName.append(loginName);
				checkFileName.append(File.separator);
			}			
			String checkName="CHECK_"+date+".txt";
			checkFileName.append(checkName);
			// 生成文件
			AnalyCollectFile ac = new AnalyCollectFile();
			ac.writeFile(dataFileName.toString(), filecontext.toString());
			logger.info("生成文件："+dataFileName);
			//采用追加方式以免覆盖
			ac.appendContext(checkFileName.toString(), checkFileContext.toString());
			logger.info("生成校验文件："+checkFileName);
			long end1 = System.currentTimeMillis();
			logger.debug("文件生成耗时："+(end1-start1)/1000f+"秒");
			/**上传文件**/
			String datasourceId = ftpServiceVo.getDatasource_id();// 获取FTP数据源ID
			// 查询FTP数据源信息
			Map datasourceMap = serverInfo.queryFTPDatasource(datasourceId);
			//System.out.println("datasourceMap is " + datasourceMap);
			VoResDataSource vo = new VoResDataSource();
			ParamUtil.mapToBean(datasourceMap, vo, false);

			// 获取FTP服务器IP 端口
			String ftpIp = vo.getData_source_ip();
			String port = vo.getAccess_port();
			String username = vo.getDb_username();
			String password = vo.getDb_password();
			String path = vo.getAccess_url();
			
			FileInputStream in;
			in = new FileInputStream(new File(dataFileName.toString()));
			boolean rs=FtpUtil.uploadFile(ftpIp, Integer.parseInt(port), username, password,
					path, fileName, in);
			if (rs) {
				logger.info("数据文件"+fileName+"上传成功");
			}else{
				logger.info("数据文件"+fileName+"上传失败");
			}
			
			in = new FileInputStream(new File(checkFileName.toString()));			
			rs=FtpUtil.uploadFile(ftpIp, Integer.parseInt(port), username, password,
					path, checkName, in);
			if (rs) {
				logger.info("校验文件"+checkName+"上传成功");
			}else{
				logger.info("校验文件"+checkName+"上传失败");
			}

		} catch (DBException e) {
			e.printStackTrace();
		}finally{
			logger.info("共享FTP任务 id="+taskId+" 执行结束");
		}
// } catch (FileNotFoundException e) {
// e.printStackTrace();
// }
	}

	public String replaceBlank(String str)
	{
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			str = m.replaceAll("");
		}
		return str;
	}

	private String timeFormat(String time)
	{
		String currenttime = "";
		if (time != null && !"".equals(time)) {
			String temp[] = time.split(" ");
			String temp1[] = temp[0].split("-");
			String temp2[] = temp[1].split(":");
			for (int i = 0; i < temp1.length; i++) {
				currenttime = currenttime + temp1[i];
			}
			for (int j = 0; j < temp2.length - 1; j++) {
				currenttime = currenttime + temp2[j];
			}
		}
		return currenttime;
	}

	private String timeFormat1(String time)
	{
		Calendar cal = Calendar.getInstance();
		int yearInt = cal.get(Calendar.YEAR);
		String theYear = String.valueOf(yearInt);

		int monthInt = cal.get(Calendar.MONTH) + 1;
		String theMonth = String.valueOf(monthInt);
		// 如果月份为:1的话年份应为上一年
		if (monthInt == 1) {
			theYear = String.valueOf(yearInt - 1);
			theMonth = "12";
		} else {
			theMonth = String.valueOf(monthInt - 1);
			if (theMonth.length() == 1) {
				theMonth = "0" + theMonth;
			}
		}
		return theYear + theMonth;
	}

	public static void main(String[] arags)
	{
		

	}
}
