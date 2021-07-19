package com.gwssi.webservice.client;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import utils.system;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.DataBus;

import com.f1j.xml.xslt.lo;
import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.collect.webservice.vo.VoCollectTask;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.AnalyCollectFile;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.ftp.FtpUtil;
import com.gwssi.ftp.download.DownloadFile;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.resource.svrobj.vo.VoResServiceTargets;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：WsClient 类描述：web service客户端 创建人：lizheng 创建时间：Apr 10,
 * 2013 10:06:58 AM 修改人：lizheng 修改时间：Apr 10, 2013 10:06:58 AM 修改备注：
 * 
 * @version
 * 
 */
public class FtpClient
{

	public FtpClient()
	{
	}

	protected static Logger	logger		= TxnLogger.getLogger(FtpClient.class
												.getName());	// 日志

	TaskInfo				taskInfo	= new TaskInfo();		// 任务信息

	/**
	 * 
	 * doCollectTaskFtp 执行 FTP 采集任务
	 * 
	 * @param taskId
	 * @throws DBException
	 * @throws IOException
	 * @throws TxnDataException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void doCollectTaskFtp(String collect_task_id) throws DBException,
			IOException, TxnDataException
	{
		System.out.println("doCollectTaskFtp begin");
		Long start = System.currentTimeMillis(); // 开始时间用于计算耗时
		String batch = UuidGenerator.getUUID(); // 批次号
		CollectLogVo collectLogVo = new CollectLogVo(); // 采集日志记录
		collectLogVo.setBatch_num(batch);
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime1 = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		collectLogVo.setTask_start_time(startTime1); // 采集开始时间

		Map taskMap1 = taskInfo.queryTask(collect_task_id);
		VoCollectTask taskVo = new VoCollectTask();
		ParamUtil.mapToBean(taskMap1, taskVo, false);
		String srvTargetId = taskVo.getService_targets_id();
		logger.debug("查询任务完毕...");
		logger.debug("采集对象ID为：" + srvTargetId);

		collectLogVo.setCollect_task_id(taskVo.getCollect_task_id());
		collectLogVo.setCollect_type(taskVo.getCollect_type());
		collectLogVo.setTask_name(taskVo.getTask_name());

		logger.debug("开始查询采集对象...");
		VoResServiceTargets srvTargetVo = new VoResServiceTargets();
		Map srvTargetMap = taskInfo.querySrvTager(srvTargetId);
		ParamUtil.mapToBean(srvTargetMap, srvTargetVo, false);
		logger.debug("查询采集对象完毕...");

		// 日志服务对象
		collectLogVo.setService_targets_id(srvTargetVo.getService_targets_id());
		collectLogVo.setService_targets_name(srvTargetVo
				.getService_targets_name());
		collectLogVo.setIs_formal(srvTargetVo.getIs_formal());

		StringBuffer sql = new StringBuffer();
/*
		sql
				.append("select a.*,b.collect_task_id,b.service_targets_id,b.task_name,b.collect_type,b.creator_id,b.last_modify_id,b.log_file_path,c.service_targets_name from ");
		sql.append("res_data_source a,collect_task b,res_service_targets c ");
		sql
				.append("where a.data_source_id = b.data_source_id and b.service_targets_id = c. service_targets_id and b.collect_task_id = '"
						+ collect_task_id + "'");*/
		sql.append("select a.data_source_ip,a.access_port,a.access_url,")
			.append(" a.db_username,a.db_password,b.service_targets_id,b.task_name,b.collect_type,b.creator_id,b.last_modify_id,b.log_file_path,c.* ")
			.append(" from res_data_source a, collect_task b, collect_ftp_task c")   
	    	.append(" where a.data_source_id = b.data_source_id")  
	    	.append(" and b.collect_task_id=c.collect_task_id")
	      	.append(" and c.collect_task_id='")
	      	.append(collect_task_id).append("'");
	      
		System.out.println("sql===" + sql);
		List fileList = null;
		Map tablepMap = null;
		HashMap taskMap = null;
		String user = "";
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
		try {
			fileList = daoTable.query(sql.toString());
			//taskMap = (HashMap) daoTable.queryService(sql.toString());
			/*if (taskMap.get("LAST_MODIFY_ID") != null
					&& !"".equals(taskMap.get("LAST_MODIFY_ID"))) {
				user = taskMap.get("LAST_MODIFY_ID").toString();
			} else {
				user = taskMap.get("CREATOR_ID").toString();
			}*/
			//System.out.println("fileList="+fileList);
			if(fileList!=null){
				//按照数据库配置的文件列表依次下载文件至本地
				for(int i=0;i<fileList.size();i++){
					Map tmpMap =(Map)fileList.get(i);
					
					boolean flag= 
						FtpUtil.downFile(tmpMap.get("DATA_SOURCE_IP").toString(),
							Integer.parseInt(tmpMap.get("ACCESS_PORT").toString()), 
							tmpMap.get("DB_USERNAME").toString(),
							tmpMap.get("DB_PASSWORD").toString(), 
							tmpMap.get("ACCESS_URL").toString(), 
							tmpMap.get("FILE_NAME_EN").toString(),
							ExConstant.FILE_FTP);
					if(flag){
						//文件成功下载标志
						tmpMap.put("flag", "Y");
					}
				}
			//fileList = DownloadFile.downFtpFile(taskMap, ExConstant.FILE_FTP);// //获取ftp文件并下载到服务器上
			
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			throw new TxnDataException("error", "连接ftp数据源失败!");
		}
		Map map = new HashMap();
		String filename = "";
		String ftpFileName = "";
		String filetype = "";
		String filePath ="";
		String resultFile = "";
		String cj_zt = "";
		String logFile = "";//(String) taskMap.get("LOG_FILE_PATH");
		String tableName = "";
		String tableId = "";
		String collect_mode = "";
		String speator="";
		String titleType="";
		String startTime = CalendarUtil.getCurrentDateTime();
		if (fileList != null && fileList.size() > 0) {
			for (int i = 0; i < fileList.size(); i++) {
				map = (Map) fileList.get(i);
				if(!"Y".equals(map.get("flag"))){
					continue;//这个文件不存在或未下载成功不执行后续操作
				}
				System.out.println("filemap="+map);
				logFile  = (String) map.get("LOG_FILE_PATH");
				filename = (String) map.get("FILE_NAME_EN");
				speator	 = (String) map.get("FILE_SEPEATOR");//分隔符
				titleType= (String) map.get("FILE_TITLE_TYPE");//数据文件标题行类型  "EN"表示英文  "CN"表示中文
				filePath = ExConstant.FILE_FTP + File.separator + filename;

				if (filename != null && !"".equals(filename)) {
					tablepMap = new HashMap();
					filetype = filename.substring(filename.indexOf(".") + 1);
					if (logFile == null || "".equals(logFile)) {
						logFile = filePath.substring(0, filePath
								.lastIndexOf("."))
								+ "_"
								+ startTime.replace("-", "").replace(":", "")
										.replace(" ", "")
								+ "_CollectFileImportResult.txt";
					}
					resultFile = ExConstant.FILE_FTP + File.separator
							+ filename.substring(0, filename.indexOf("."))
							+ "_result.txt";
					ftpFileName = filename;//.substring(filename.indexOf("_") + 1);
					System.out.println("type==" + filetype);

					AnalyCollectFile file = new AnalyCollectFile();
					sql = new StringBuffer();
					sql.append("select a.*,b.table_name_en from ");
					sql.append("collect_ftp_task a,res_collect_table b ");
					sql
							.append("where a.collect_table = b.collect_table_id and a.file_name_en = '"
									+ ftpFileName
									+ "'  and a.collect_task_id = '"
									+ collect_task_id + "'");
					System.out.println("查询ftp文件表sql==" + sql);
					tablepMap = daoTable.queryService(sql.toString());
					tableName = (String) tablepMap.get("TABLE_NAME_EN");// 采集表
					tableId = (String) tablepMap.get("COLLECT_TABLE");// 采集表ID
					System.out.println("tableId==" + tableId);
					System.out.println("tableName==" + tableName);
					collect_mode = (String) tablepMap.get("COLLECT_MODE");// 采集方式
					taskMap=new HashMap(map);
					taskMap.put("TASK_ID", tablepMap.get("FTP_TASK_ID"));// 任务ID
					taskMap.put("SERVICE_NO", tablepMap.get("SERVICE_NO"));// 任务编号
					taskMap.put("COLLECT_TABLE", tableId);// 采集表ID
					taskMap.put("COLLECT_TABLE_NAME", tableName);// 采集表名称
					taskMap.put("COLLECT_MODE", collect_mode);// 采集方式
					collectLogVo.setCollect_task_id(tableId);
					collectLogVo.setCollect_table_name(tableName);
					collectLogVo.setCollect_mode(collect_mode);

					// 更新ftp文件表 文件路径
					sql = new StringBuffer();
					sql.append("update collect_ftp_task set fj_path = '"
							+ filePath + "'");
					sql.append(" where file_name_en = '" + ftpFileName
							+ "'  and collect_task_id = '" + collect_task_id
							+ "'");
					Connection conn = null;
					Statement st = null;
					try {
						conn = DbUtils.getConnection("2"); // 实例化con
						// 5对应的是DataSource5这个数据源
						// conn.setAutoCommit(false);
						st = conn.createStatement();
						st.executeUpdate(sql.toString());// 执行sql
						// conn.commit();
					} catch (Exception e) {
						throw new TxnDataException("error", "更新ftp文件表文件路径失败!");
					} finally {
						try {
							if (null != st) {
								st.close();
							}
							DbUtils.freeConnection(conn);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

					sql = new StringBuffer();
					sql.append("select a.* from ");
					sql.append("res_collect_dataitem a ");
					sql.append("where a.collect_table_id = '" + tableId + "'");
					System.out.println("sql====" + sql);
					List list = daoTable.query(sql.toString());
					//System.out.println("res_collect_dataitem list="+list);
					Map tableMap = new HashMap();
					HashMap resultMap = new HashMap();
					StringBuffer colTable_ColumnInfo = new StringBuffer();
					if (list != null && list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							tableMap = (Map) list.get(j);
							if("EN".equals(titleType)){
								resultMap.put(j, tableMap.get("DATAITEM_NAME_EN")
										.toString().toUpperCase());
							}else{
								resultMap.put(j, tableMap.get("DATAITEM_NAME_CN")
										.toString().toUpperCase());
							}
							
							if (j != list.size()) {
								colTable_ColumnInfo.append(tableMap.get(
										"DATAITEM_NAME_EN").toString()
										+ "("
										+ tableMap.get("DATAITEM_NAME_CN")
												.toString() + "),");
							} else {
								colTable_ColumnInfo.append(tableMap.get(
										"DATAITEM_NAME_EN").toString()
										+ "("
										+ tableMap.get("DATAITEM_NAME_CN")
												.toString() + ")");
							}
						}
						System.out.println("ColumnInfo="+colTable_ColumnInfo.toString());
					}
					if (filetype != null && (filetype.equals("xls"))
							|| filetype.equals("xlsx")) {// excel
						String checkResultPath = filePath.substring(0, filePath
								.lastIndexOf("."))
								+ "_xls_" + "checkResult.txt";
						if (cj_zt != null
								&& cj_zt
										.equals(CollectConstants.COLLECT_STATUS_FAIL)) {
							file.analyExcelData2(taskMap, filePath, tableName,
									resultMap, collect_mode, checkResultPath,
									user, logFile, colTable_ColumnInfo);
						} else {
							cj_zt = file.analyExcelData2(taskMap, filePath,
									tableName, resultMap, collect_mode,
									checkResultPath, user, logFile,
									colTable_ColumnInfo);
						}

					} else if (filetype != null && filetype.equals("txt")) {
						String checkResultPath = filePath.substring(0, filePath
								.lastIndexOf("."))
								+ "_txt_" + "checkResult.txt";
						if (cj_zt != null
								&& cj_zt
										.equals(CollectConstants.COLLECT_STATUS_FAIL)) {
							file.analyTxtData2(taskMap, filePath, tableName,
									resultMap, collect_mode, speator,
									checkResultPath, user, logFile,
									colTable_ColumnInfo);
						} else {
							System.out.println("before speator="+speator);
							cj_zt = file.analyTxtData2(taskMap, filePath,
									tableName, resultMap, collect_mode, speator,
									checkResultPath, user, logFile,
									colTable_ColumnInfo);
							System.out.println("after cj_zt="+cj_zt);
						}
					} else if (filetype != null && filetype.equals("mdb")) {

					}
				}
			}

			// 更新采集状态
			Connection conn = null;
			Statement st = null;
			String sql2 = "update collect_task set collect_status = '" + cj_zt
					+ "',log_file_path = '" + logFile
					+ "' where collect_task_id = '" + collect_task_id + "'";
			System.out.println("更新采集任务表采集状态及日志文件路径sql=====" + sql2);
			try {
				conn = DbUtils.getConnection("2"); // 实例化con
				// 5对应的是DataSource5这个数据源
				st = conn.createStatement();
				st.executeUpdate(sql2);// 执行sql
			} catch (Exception e) {
				throw new TxnDataException("error", "更新ftp文件表文件路径失败!");
			} finally {
				try {
					if (null != st) {
						st.close();
					}
					DbUtils.freeConnection(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		collectLogVo.setReturn_codes(cj_zt);
		Long end1 = System.currentTimeMillis(); // 结束时间用于计算耗时
		String consumeTime1 = String.valueOf(((end1 - start) / 1000f));
		collectLogVo.setTask_consume_time(consumeTime1);
		logger.debug("采集数据共耗时：" + consumeTime1 + "秒");

		SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endTime = end.format(new java.util.Date());// 系统当前年月日时分秒
		collectLogVo.setTask_end_time(endTime);
		logger.debug("开始记录日志...");
		taskInfo.insertLog(collectLogVo);
		logger.debug("记录日志成功...");
	}
	/**
	 * 
	 * doCollectTaskFtp1 执行 FTP 采集任务
	 * 
	 * @param taskId
	 * @throws Exception 
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void doCollectTaskFtp1(String collect_task_id) throws Exception
	{
		System.out.println("doCollectTaskFtp1 begin");
		//1.准备日志记录所需信息
		
		Long start = System.currentTimeMillis(); // 开始时间用于计算耗时
		String batch = UuidGenerator.getUUID(); // 批次号
		CollectLogVo collectLogVo = new CollectLogVo(); // 采集日志记录
		collectLogVo.setBatch_num(batch);
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime1 = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		collectLogVo.setTask_start_time(startTime1); // 采集开始时间

		Map taskMap1 = taskInfo.queryTask(collect_task_id);
		VoCollectTask taskVo = new VoCollectTask();
		ParamUtil.mapToBean(taskMap1, taskVo, false);
		String srvTargetId = taskVo.getService_targets_id();
		logger.debug("查询任务完毕...");
		logger.debug("采集对象ID为：" + srvTargetId);

		collectLogVo.setCollect_task_id(taskVo.getCollect_task_id());
		collectLogVo.setCollect_type(taskVo.getCollect_type());
		collectLogVo.setTask_name(taskVo.getTask_name());

		logger.debug("开始查询采集对象...");
		VoResServiceTargets srvTargetVo = new VoResServiceTargets();
		Map srvTargetMap = taskInfo.querySrvTager(srvTargetId);
		ParamUtil.mapToBean(srvTargetMap, srvTargetVo, false);
		logger.debug("查询采集对象完毕...");

		// 日志服务对象
		collectLogVo.setService_targets_id(srvTargetVo.getService_targets_id());
		collectLogVo.setService_targets_name(srvTargetVo
				.getService_targets_name());
		collectLogVo.setIs_formal(srvTargetVo.getIs_formal());

		//2.从FTP服务器下载文件
		List fileList = null;//要下载的文件信息
		Map tablepMap = null;
		HashMap taskMap = null;
		String user = "";
		StringBuffer sql = new StringBuffer();
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
		try {
			
			
			//
			sql.append("select a.data_source_ip,a.access_port,a.access_url,")
				.append(" a.db_username,a.db_password,b.service_targets_id,b.task_name,b.collect_type,b.creator_id,b.last_modify_id,b.log_file_path,c.* ")
				.append(" from res_data_source a, collect_task b, collect_ftp_task c")   
		    	.append(" where a.data_source_id = b.data_source_id")  
		    	.append(" and b.collect_task_id=c.collect_task_id")
		      	.append(" and c.collect_task_id='")
		      	.append(collect_task_id).append("'");
		      
			System.out.println("sql===" + sql);
			fileList = daoTable.query(sql.toString());
			
			if(fileList!=null){
				//按照数据库配置的文件列表依次下载文件至本地
				for(int i=0;i<fileList.size();i++){
					Map tmpMap =(Map)fileList.get(i);
					
					boolean flag= 
						FtpUtil.downFile(tmpMap.get("DATA_SOURCE_IP").toString(),
							Integer.parseInt(tmpMap.get("ACCESS_PORT").toString()), 
							tmpMap.get("DB_USERNAME").toString(),
							tmpMap.get("DB_PASSWORD").toString(), 
							tmpMap.get("ACCESS_URL").toString(), 
							tmpMap.get("FILE_NAME_EN").toString(),
							ExConstant.FILE_FTP);
					if(flag){
						//文件成功下载标志
						tmpMap.put("flag", "Y");
					}
				}
			//fileList = DownloadFile.downFtpFile(taskMap, ExConstant.FILE_FTP);// //获取ftp文件并下载到服务器上
			
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			throw new TxnDataException("error", "连接ftp数据源失败!");
		}
		
		
		//3.依次读取文件数据并写入对应的采集表
		Map map = new HashMap();
		String filename = "";
		String ftpFileName = "";
		String filetype = "";
		String filePath ="";
		String resultFile = "";
		String cj_zt = "";
		String logFile = "";//(String) taskMap.get("LOG_FILE_PATH");
		String tableName = "";
		String tableId = "";
		String collect_mode = "";
		String speator="";
		String titleType="";
		String startTime = CalendarUtil.getCurrentDateTime();
		if (fileList != null && fileList.size() > 0) {
			for (int i = 0; i < fileList.size(); i++) {
				map = (Map) fileList.get(i);
				if(!"Y".equals(map.get("flag"))){
					continue;//这个文件不存在或未下载成功不执行后续操作
				}
				System.out.println("filemap="+map);
				logFile  = (String) map.get("LOG_FILE_PATH");
				filename = (String) map.get("FILE_NAME_EN");
				speator	 = (String) map.get("FILE_SEPEATOR");//分隔符
				titleType= (String) map.get("FILE_TITLE_TYPE");//数据文件标题行类型  "EN"表示英文  "CN"表示中文
				filePath = ExConstant.FILE_FTP + File.separator + filename;

				if (filename != null && !"".equals(filename)) {
					tablepMap = new HashMap();
					filetype = filename.substring(filename.indexOf(".") + 1);
					//日志文件
					if (logFile == null || "".equals(logFile)) {
						logFile = filePath.substring(0, filePath
								.lastIndexOf("."))
								+ "_"
								+ startTime.replace("-", "").replace(":", "")
										.replace(" ", "")
								+ "_CollectFileImportResult.txt";
					}
					//结果文件
					resultFile = ExConstant.FILE_FTP + File.separator
							+ filename.substring(0, filename.indexOf("."))
							+ "_result.txt";
					ftpFileName = filename;//.substring(filename.indexOf("_") + 1);
					System.out.println("type==" + filetype);
					
					AnalyCollectFile file = new AnalyCollectFile();
					sql = new StringBuffer();
					sql.append("select a.*,b.table_name_en ,c.service_targets_name from ");
					sql.append("collect_ftp_task a,res_collect_table b ,res_service_targets c ");
					sql.append("where a.collect_table = b.collect_table_id and " +
							" b.service_targets_id=c.service_targets_id and a.file_name_en = '"
									+ ftpFileName
									+ "'  and a.collect_task_id = '"
									+ collect_task_id + "'");
					System.out.println("查询ftp文件表sql==" + sql);
					tablepMap = daoTable.queryService(sql.toString());
					tableName = (String) tablepMap.get("TABLE_NAME_EN");// 采集表
					tableId = (String) tablepMap.get("COLLECT_TABLE");// 采集表ID
					System.out.println("tableId==" + tableId);
					System.out.println("tableName==" + tableName);
					collect_mode = (String) tablepMap.get("COLLECT_MODE");// 采集方式
					taskMap=new HashMap(map);
					taskMap.put("TASK_ID", tablepMap.get("FTP_TASK_ID"));// 任务ID
					taskMap.put("SERVICE_NO", tablepMap.get("SERVICE_NO"));// 任务编号
					taskMap.put("COLLECT_TABLE", tableId);// 采集表ID
					taskMap.put("METHOD_NAME_EN", tablepMap.get("FILE_NAME_EN"));
					taskMap.put("METHOD_NAME_CN", tablepMap.get("FILE_NAME_CN"));
					taskMap.put("COLLECT_TABLE_NAME", tableName);// 采集表名称
					taskMap.put("COLLECT_MODE", collect_mode);// 采集方式
					taskMap.put("COLLECT_TASK_ID", tablepMap.get("COLLECT_TASK_ID"));
					taskMap.put("SERVICE_TARGETS_NAME",tablepMap.get("SERVICE_TARGETS_NAME"));//服务对象名称
					taskMap.put("BATCH", batch);//日志批次号
					taskMap.put("SERVICE_NO",tablepMap.get("SERVICE_NO") );
					taskMap.put("IS_FORMAL", "Y");
					collectLogVo.setCollect_task_id((String)tablepMap.get("COLLECT_TASK_ID"));
					collectLogVo.setCollect_table(tableId);
					collectLogVo.setCollect_table_name(tableName);
					collectLogVo.setCollect_mode(collect_mode);

					// 更新ftp文件表 文件路径
					sql = new StringBuffer();
					sql.append("update collect_ftp_task set fj_path = '"
							+ filePath + "'");
					sql.append(" where file_name_en = '" + ftpFileName
							+ "'  and collect_task_id = '" + collect_task_id
							+ "'");
					Connection conn = null;
					Statement st = null;
					try {
						conn = DbUtils.getConnection("2"); // 实例化con
						// 5对应的是DataSource5这个数据源
						// conn.setAutoCommit(false);
						st = conn.createStatement();
						st.executeUpdate(sql.toString());// 执行sql
						// conn.commit();
					} catch (Exception e) {
						throw new TxnDataException("error", "更新ftp文件表文件路径失败!");
					} finally {
						try {
							if (null != st) {
								st.close();
							}
							DbUtils.freeConnection(conn);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

					sql = new StringBuffer();
					sql.append("select a.* from ");
					sql.append("res_collect_dataitem a ");
					sql.append("where a.collect_table_id = '" + tableId + "'");
					System.out.println("sql====" + sql);
					List list = daoTable.query(sql.toString());
					//System.out.println("res_collect_dataitem list="+list);
					Map tableMap = new HashMap();
					
					//列英文名为VALUE 中文名为KEY 用于数据文件中标题行为中文
					HashMap colNamesMap = new HashMap();
					
					
					StringBuffer colTable_ColumnInfo = new StringBuffer();
					if (list != null && list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							tableMap = (Map) list.get(j);
							
							colNamesMap.put(tableMap.get("DATAITEM_NAME_CN")
									.toString().toUpperCase(), tableMap.get("DATAITEM_NAME_EN")
									.toString().toUpperCase());
							
							
							//列名字段 写入日志文件用
							if (j != list.size()) {
								colTable_ColumnInfo.append(tableMap.get(
										"DATAITEM_NAME_EN").toString()
										+ "("
										+ tableMap.get("DATAITEM_NAME_CN")
												.toString() + "),");
							} else {
								colTable_ColumnInfo.append(tableMap.get(
										"DATAITEM_NAME_EN").toString()
										+ "("
										+ tableMap.get("DATAITEM_NAME_CN")
												.toString() + ")");
							}
						}
						System.out.println("ColumnInfo="+colTable_ColumnInfo.toString());
					}
					
					if (filetype != null && (filetype.equals("xls"))
							|| filetype.equals("xlsx")) {// excel
						String checkResultPath = filePath.substring(0, filePath
								.lastIndexOf("."))
								+ "_xls_" + "checkResult.txt";
						/*if (cj_zt != null
								&& cj_zt
										.equals(CollectConstants.COLLECT_STATUS_FAIL)) {
							file.analyExcelData2(taskMap, filePath, titleMap,
									colNameCnMap, collect_mode, checkResultPath,
									user, logFile, colTable_ColumnInfo);
						} else {
							cj_zt = file.analyExcelData2(taskMap, filePath,
									titleMap, colNameCnMap, collect_mode,
									checkResultPath, user, logFile,
									colTable_ColumnInfo);
						}*/

					} else if (filetype != null && filetype.equals("txt")) {
						String checkResultPath = filePath.substring(0, filePath
								.lastIndexOf("."))
								+ "_txt_" + "checkResult.txt";
						if (cj_zt != null
								&& cj_zt
										.equals(CollectConstants.COLLECT_STATUS_FAIL)) {
							file.analyTxtData3(taskMap, filePath, tableName,
									colNamesMap,titleType, collect_mode, speator,
									checkResultPath, user, logFile,
									colTable_ColumnInfo);
						} else {
							System.out.println("before speator="+speator);
							cj_zt = file.analyTxtData3(taskMap, filePath,
									tableName, colNamesMap,titleType,collect_mode, speator,
									checkResultPath, user, logFile,
									colTable_ColumnInfo);
							System.out.println("after cj_zt="+cj_zt);
						}
					} else if (filetype != null && filetype.equals("mdb")) {

					}
				}
			}

			// 更新采集状态
			Connection conn = null;
			Statement st = null;
			String sql2 = "update collect_task set collect_status = '" + cj_zt
					+ "',log_file_path = '" + logFile
					+ "' where collect_task_id = '" + collect_task_id + "'";
			System.out.println("更新采集任务表采集状态及日志文件路径sql=====" + sql2);
			try {
				conn = DbUtils.getConnection("2"); // 实例化con
				// 5对应的是DataSource5这个数据源
				st = conn.createStatement();
				st.executeUpdate(sql2);// 执行sql
			} catch (Exception e) {
				throw new TxnDataException("error", "更新ftp文件表文件路径失败!");
			} finally {
				try {
					if (null != st) {
						st.close();
					}
					DbUtils.freeConnection(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		collectLogVo.setReturn_codes(cj_zt);
		Long end1 = System.currentTimeMillis(); // 结束时间用于计算耗时
		String consumeTime1 = String.valueOf(((end1 - start) / 1000f));
		collectLogVo.setTask_consume_time(consumeTime1);
		logger.debug("采集数据共耗时：" + consumeTime1 + "秒");

		SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endTime = end.format(new java.util.Date());// 系统当前年月日时分秒
		collectLogVo.setTask_end_time(endTime);
		collectLogVo.setMethod_name_cn("11");
		logger.debug("开始记录日志...");
		//taskInfo.insertLog(collectLogVo);
		logger.debug("记录日志成功...");
	}
	
	
	/**
	 * 为文件分析方法设置参数Map值
	 * @param colsMap
	 * @param colsInfo
	 * @param tableId
	 * @param dao
	 * @throws DBException
	 */
	public void setColsNameMap(HashMap colsMap,StringBuffer colsInfo,String tableId,ServiceDAO dao) throws DBException
	{
		StringBuffer sql = SQLHelper.getDataItems(tableId);
		List list = dao.query(sql.toString());			
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				Map tmpMap = (Map) list.get(j);					
				colsMap.put(tmpMap.get("DATAITEM_NAME_CN")
						.toString().toUpperCase(), tmpMap.get("DATAITEM_NAME_EN")
						.toString().toUpperCase());
				
				//列名字段 写入日志文件用
				if (j != list.size()) {
					colsInfo.append(tmpMap.get(
							"DATAITEM_NAME_EN").toString()
							+ "("
							+ tmpMap.get("DATAITEM_NAME_CN")
									.toString() + "),");
				} else {
					colsInfo.append(tmpMap.get(
							"DATAITEM_NAME_EN").toString()
							+ "("
							+ tmpMap.get("DATAITEM_NAME_CN")
									.toString() + ")");
				}
			}
			//System.out.println("ColumnInfo="+colsInfo.toString());
		}
		return;
	}
	/**
	 * 设置日志记录所用的Map
	 * @param fileMap
	 * @param batch
	 * @return
	 */
	public HashMap setLogMap(Map fileMap,String batch)
	{
		HashMap logMap =new HashMap();
		logMap.put("TASK_NAME", fileMap.get("TASK_NAME"));//任务名称
		logMap.put("SERVICE_TARGETS_ID", fileMap.get("SERVICE_TARGETS_ID"));//服务对象ID 
		logMap.put("COLLECT_TYPE", fileMap.get("COLLECT_TYPE"));//采集类型
		logMap.put("TASK_ID", fileMap.get("FTP_TASK_ID"));// 任务ID
		logMap.put("SERVICE_NO", fileMap.get("SERVICE_NO"));// 任务编号
		logMap.put("COLLECT_TABLE", fileMap.get("COLLECT_TABLE"));// 采集表ID
		logMap.put("METHOD_NAME_EN", fileMap.get("FILE_NAME_EN"));
		logMap.put("METHOD_NAME_CN", fileMap.get("FILE_NAME_CN"));
		logMap.put("COLLECT_TABLE_NAME", fileMap.get("TABLE_NAME_EN"));// 采集表名称
		logMap.put("COLLECT_MODE", fileMap.get("COLLECT_MODE"));// 采集方式
		logMap.put("COLLECT_TASK_ID", fileMap.get("COLLECT_TASK_ID"));
		logMap.put("SERVICE_TARGETS_NAME",fileMap.get("SERVICE_TARGETS_NAME"));//服务对象名称
		logMap.put("BATCH", batch);//日志批次号
		logMap.put("SERVICE_NO",fileMap.get("SERVICE_NO") );
		logMap.put("IS_FORMAL", "Y");
		return logMap;
	}
	/**
	 * 更新ftp任务表文件路径
	 * @param conn
	 * @param filePath
	 * @param filename
	 * @param collect_task_id
	 * @throws TxnDataException
	 */
	public void updateFilePath(Connection conn,String filePath,String filename,String collect_task_id) throws TxnDataException
	{
		
		StringBuffer sql = new StringBuffer();
		sql.append("update collect_ftp_task set fj_path = '"
				+ filePath + "'");
		sql.append(" where file_name_en = '" + filename
				+ "'  and collect_task_id = '" + collect_task_id
				+ "'");
		
		Statement st = null;
		try {
			
			st = conn.createStatement();
			st.executeUpdate(sql.toString());// 执行sql
			// conn.commit();
		} catch (Exception e) {
			throw new TxnDataException("error", "更新ftp文件表文件路径失败!");
		} finally {
			try {
				if (null != st) {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void updateCollectStatus(String cj_zt,String collect_task_id) throws TxnDataException, DBException{
		Connection conn = null;
		Statement st = null;
		String sql = "update collect_task set collect_status = '" + cj_zt
				+ "' where collect_task_id = '" + collect_task_id + "'";
		logger.info("更新采集任务表采集状态sql=====" + sql);
		try {
			conn = DbUtils.getConnection("2"); // 实例化con
			// 5对应的是DataSource5这个数据源
			st = conn.createStatement();
			st.executeUpdate(sql);// 执行sql
		} catch (Exception e) {
			throw new TxnDataException("error", "更新采集任务表采集状态失败!");
		} finally {
			try {
				if (null != st) {
					st.close();
				}
				DbUtils.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 处理下载的数据文件 将数据写入对应的采集表
	 * @param fileMap  文件信息
	 * @param collect_task_id  采集任务ID
	 * @param batch 日志批次号
	 * @param dao   数据库表操作dao
	 * @param conn 数据库连接
	 * @throws Exception 
	 */
	public String dealFileData(Map fileMap,String collect_task_id,String batch,ServiceDAO dao,Connection conn) throws Exception{
		
		//全局参数
		
		//日志文件（含全路径）
		String logFile  = "";
		//结果文件
		String resultFile="";
		//文件名
		String filename = (String) fileMap.get("FILE_NAME_EN");
		//列分隔符
		String speator	 = (String) fileMap.get("FILE_SEPEATOR");
		//数据文件标题行类型  "EN"表示英文  "CN"表示中文
		String titleType= (String) fileMap.get("FILE_TITLE_TYPE");
		//采集状态
		String cj_zt = "";
		//文件下载本地路径
		String filePath = ExConstant.FILE_FTP + File.separator + filename;
		//开始时间
		String startTime = CalendarUtil.getCurrentDateTime();
		//文件解析处理类
		AnalyCollectFile file = new AnalyCollectFile();
		if (filename != null && !"".equals(filename)) {
			String filetype = filename.substring(filename.indexOf(".") + 1);
			//日志文件
			logFile = filePath.substring(0, filePath.lastIndexOf("."))
						+ "_"
						+ startTime.replace("-", "").replace(":", "")
								.replace(" ", "")
						+ "_CollectFileImportResult.txt";
			
			//结果文件
			resultFile = ExConstant.FILE_FTP + File.separator
					+ filename.substring(0, filename.indexOf("."))
					+ "_result.txt";
			
			//日志记录参数
			HashMap logMap = setLogMap(fileMap,batch);
			System.out.println("logMap="+logMap);
			// 更新ftp文件表 文件路径
			updateFilePath(conn,filePath,filename,collect_task_id);			
			logger.info("更新ftp文件表 文件路径");
			
			
			//列英文名为VALUE 中文名为KEY 用于数据文件中标题行处理
			HashMap colsMap = new HashMap();			
			//列名字段 写入日志文件用
			StringBuffer colTable_ColumnInfo = new StringBuffer();
			//为文件分析方法设置参数Map值
			setColsNameMap(colsMap,colTable_ColumnInfo,(String)fileMap.get("COLLECT_TABLE"),dao);
			
			
			if (filetype != null && (filetype.equals("xls"))
					|| filetype.equals("xlsx")) {// excel
				logger.info("开始excel文件处理");
				String checkResultPath = filePath.substring(0, filePath
						.lastIndexOf("."))
						+ "_xls_" + "checkResult.txt";
				/*if (cj_zt != null
						&& cj_zt
								.equals(CollectConstants.COLLECT_STATUS_FAIL)) {
					file.analyExcelData2(taskMap, filePath, titleMap,
							colNameCnMap, collect_mode, checkResultPath,
							user, logFile, colTable_ColumnInfo);
				} else {
					cj_zt = file.analyExcelData2(taskMap, filePath,
							titleMap, colNameCnMap, collect_mode,
							checkResultPath, user, logFile,
							colTable_ColumnInfo);
				}*/
				cj_zt = file.analyExcelData_Ftp(logMap, filePath,
						(String)fileMap.get("TABLE_NAME_EN"), colsMap, titleType,(String)fileMap.get("COLLECT_MODE"),
						checkResultPath, "用户", logFile,
						colTable_ColumnInfo);

			} else if (filetype != null && filetype.equals("txt")) {
				//校验结果文件
				String checkResultPath = filePath.substring(0, filePath
						.lastIndexOf("."))
						+ "_txt_" + "checkResult.txt";
				logger.info("txt文件开始处理");
				cj_zt = file.analyTxtData3New(logMap, filePath,
						(String)fileMap.get("TABLE_NAME_EN"), colsMap,titleType,(String)fileMap.get("COLLECT_MODE"), speator,
						checkResultPath, "用户", logFile,
						colTable_ColumnInfo);
					
				
			} else if (filetype != null && filetype.equals("mdb")) {
				logger.info("暂未实现mdb格式文件处理");
			}else{
				logger.info("不支持的文件格式 filetype="+filetype);
			}
			
		}
		return cj_zt;
	}
	
	
	/**
	 * 根据规则添加文件名后缀
	 * @param filename
	 * @return
	 */
	public String getFileName(Map fileMap){
		//获取当前日期
		Calendar calendar = Calendar.getInstance();
		//指定日期格式
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		String nameType =(String)fileMap.get("NAME_TYPE");//命名类型 M按月 D按日
		String fileName =(String)fileMap.get("FILE_NAME_EN").toString();//文件名前缀
		String filetype=fileName.substring(fileName.indexOf(".") + 1);//文件扩展名
		
		String date=""; //按规则生成的日期字符串，将作为文件名后缀
		if("M".equals(nameType)){//按月规则生成日期
			String month    =(String)fileMap.get("MONTH");//按月方式的月规则
			String dayMonth =(String)fileMap.get("DAY_MONTH");//按月方式的指定日
			if(StringUtils.isBlank(month)){
				//默认为当月
				month="0";
			}
			if(StringUtils.isBlank(dayMonth)){
				//默认为每月1号
				dayMonth="1";
			}
			System.out.println("month="+month+"----day="+dayMonth);
			//设置月份
			int monthInt=Integer.parseInt(month);			
			//设置日
			if ("last".equals(dayMonth)) {
				//月最后一天
				monthInt+=1;
				System.out.println("monthInt="+monthInt);
				calendar.add(Calendar.MONTH, monthInt);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
			}else {
				//指定的日号
				calendar.add(Calendar.MONTH, monthInt);
				int dayMonthInt=Integer.parseInt(dayMonth);
				calendar.set(Calendar.DAY_OF_MONTH, dayMonthInt);
			}
			date = df.format(calendar.getTime());
		}else if("D".equals(nameType)){//按日规则生成日期
			String dayNumStr=(String)fileMap.get("DAY_NUM");//按日方式的日规则
			if(StringUtils.isBlank(dayNumStr)){
				//默认为当天
				dayNumStr="0";
			}
			int dayNumInt=Integer.parseInt(dayNumStr);
			calendar.add(Calendar.DAY_OF_MONTH, dayNumInt);
			date = df.format(calendar.getTime());
		}
		System.out.println("date="+date);
		//为文件名增加时间后缀
		fileName=fileName.substring(0, fileName.lastIndexOf("."))+"_"+date+"."+filetype;
		return fileName;
	}
	/**
	 * 
	 * doCollectTaskFtpNew 执行 FTP 采集任务
	 * 
	 * @param taskId
	 * @throws DBException
	 * @throws IOException
	 * @throws TxnDataException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void doCollectTaskFtpNew(String collect_task_id) throws DBException,
			IOException, TxnDataException
	{

		logger.info("-----FTP采集任务开始执行-----");
		
		//全局参数
		String batch = UuidGenerator.getUUID(); // 日志批次号
		List fileList = null;//要下载的文件信息
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao	
		String cj_zt = "";//采集状态
				
		try {
			//查询数据库获取FTP连接信息及要下载的文件信息
			
			StringBuffer sql = new StringBuffer();
			sql=SQLHelper.getFilesInfo(collect_task_id);
			
			fileList = daoTable.query(sql.toString());
			
			//按照数据库配置的文件列表依次下载文件至本地，并将数据插入对应的采集表
			if(fileList!=null){
				Connection conn = null;
				
				try {
					conn = DbUtils.getConnection("2"); // 实例化con
					logger.info("共有"+fileList.size()+"个文件要下载");
					for(int i=0;i<fileList.size();i++){
						Map fileMap =(Map)fileList.get(i);
						String fileName = getFileName(fileMap);
						
						fileMap.put("FILE_NAME_EN", fileName);
						//从FTP服务器下载文件
						boolean flag= 
							FtpUtil.downFile(fileMap.get("DATA_SOURCE_IP").toString(),
								Integer.parseInt(fileMap.get("ACCESS_PORT").toString()), 
								fileMap.get("DB_USERNAME").toString(),
								fileMap.get("DB_PASSWORD").toString(), 
								fileMap.get("ACCESS_URL").toString(), 
								fileMap.get("FILE_NAME_EN").toString(),
								ExConstant.FILE_FTP);
						if(flag){
							//文件成功下载，处理文件内数据
							logger.info(fileName+"成功下载");
							cj_zt = dealFileData(fileMap,collect_task_id,batch,daoTable,conn);
						}else{
							logger.info(fileName+"文件下载失败");
						}
					}	
				} catch (Exception e) {
					throw new TxnDataException("error", e.getMessage());
				} finally {
					DbUtils.freeConnection(conn);
				}
			}else {
				logger.info("collect_task_id="+collect_task_id+" 任务没有要下载的文件");
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
			throw new TxnDataException("error", e.getMessage());
		}
		
		// 更新采集状态
		updateCollectStatus(cj_zt,collect_task_id);
			
		
	}
	
	/**
	 * 手动执行FTP采集任务
	 * @param record
	 * @throws Exception 
	 */
	public DataBus doManualFTP(DataBus record) throws Exception{
		String file_name_en=record.getValue("file_name_en");
		String file_sepeator=record.getValue("file_sepeator");
		String service_targets_id=record.getValue("service_targets_id");
		String collect_type=record.getValue("collect_type");
		String data_source_id=record.getValue("data_source_id");		
		String collect_table=record.getValue("collect_table");
		String collect_mode=record.getValue("collect_mode");
		DataBus resultBus=new DataBus();
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
		//文件下载本地路径
		String filePath = ExConstant.FILE_FTP + File.separator + file_name_en;
		//获取数据源信息
		StringBuffer sql = new StringBuffer();
		sql=SQLHelper.getManualSource(data_source_id,collect_table);		
		List sourceList= daoTable.query(sql.toString());
		Map dataSource=new HashMap();
		if(sourceList!=null && sourceList.size()>0){
			dataSource =(Map)sourceList.get(0);	
			//logger.info("dataSource="+dataSource);
			//从FTP服务器下载文件
			boolean flag= 
				FtpUtil.downFile(dataSource.get("DATA_SOURCE_IP").toString(),
					Integer.parseInt(dataSource.get("ACCESS_PORT").toString()), 
					dataSource.get("DB_USERNAME").toString(),
					dataSource.get("DB_PASSWORD").toString(), 
					dataSource.get("ACCESS_URL").toString(), 
					file_name_en,
					ExConstant.FILE_FTP);
			if(flag){
				//文件成功下载，处理文件内数据
				logger.info(file_name_en+"成功下载 ");
				
				//文件解析处理类
				AnalyCollectFile file = new AnalyCollectFile();
				//列英文名为VALUE 中文名为KEY 用于数据文件中标题行处理
				HashMap colsMap = new HashMap();			
				//列名字段 写入日志文件用
				StringBuffer colTable_ColumnInfo = new StringBuffer();
				//为文件分析方法设置参数Map值
				setColsNameMap(colsMap,colTable_ColumnInfo,collect_table,daoTable);
				
				//校验结果文件
				String checkResultPath = filePath.substring(0, filePath
						.lastIndexOf("."))
						+ "_txt_" + "checkResult.txt";
				logger.info("开始解析文件");
				
				String[] sqls = file.analyTxt2(filePath, dataSource.get("TABLE_NAME_EN").toString(), colsMap,"CN",collect_mode,
						file_sepeator, checkResultPath);
				int count=0;
				if (sqls != null && sqls.length != 0) {
					try {
						logger.info("开始执行SQL语句");
						count = file.execBatch(sqls);
						if(count==-1){
							resultBus.put("count", "0");
							resultBus.put("errorMsg", "SQL语句执行错误");
							count=0;
						}else{
							resultBus.put("count", count);
							resultBus.put("errorMsg", "采集成功");
						}
					} catch (Exception e) {
						resultBus.put("count", count);
						resultBus.put("errorMsg", "采集失败:数据格式错误");
						throw new TxnDataException("采集数据失败", "数据格式错误!");
					} finally {
						logger.info("往表" + dataSource.get("TABLE_NAME_EN").toString() + "批量执行条" + count);
						return resultBus;
					}
				}else{
					resultBus.put("count", count);
					resultBus.put("errorMsg", "采集失败:数据格式错误");
					return resultBus;
				}
				
			}else{
				logger.info(file_name_en+"文件下载失败");
				resultBus.put("count", "0");
				resultBus.put("errorMsg", "文件下载失败");
				return resultBus;
			}
		}else{
			logger.info("没有找到对应的数据源或采集表");
			resultBus.put("count", "0");
			resultBus.put("errorMsg", "没有找到对应的数据源或采集表");
			return resultBus;
		}	
	}

}
