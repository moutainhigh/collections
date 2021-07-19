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
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�WsClient ��������web service�ͻ��� �����ˣ�lizheng ����ʱ�䣺Apr 10,
 * 2013 10:06:58 AM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 10, 2013 10:06:58 AM �޸ı�ע��
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
												.getName());	// ��־

	TaskInfo				taskInfo	= new TaskInfo();		// ������Ϣ

	/**
	 * 
	 * doCollectTaskFtp ִ�� FTP �ɼ�����
	 * 
	 * @param taskId
	 * @throws DBException
	 * @throws IOException
	 * @throws TxnDataException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void doCollectTaskFtp(String collect_task_id) throws DBException,
			IOException, TxnDataException
	{
		System.out.println("doCollectTaskFtp begin");
		Long start = System.currentTimeMillis(); // ��ʼʱ�����ڼ����ʱ
		String batch = UuidGenerator.getUUID(); // ���κ�
		CollectLogVo collectLogVo = new CollectLogVo(); // �ɼ���־��¼
		collectLogVo.setBatch_num(batch);
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime1 = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
		collectLogVo.setTask_start_time(startTime1); // �ɼ���ʼʱ��

		Map taskMap1 = taskInfo.queryTask(collect_task_id);
		VoCollectTask taskVo = new VoCollectTask();
		ParamUtil.mapToBean(taskMap1, taskVo, false);
		String srvTargetId = taskVo.getService_targets_id();
		logger.debug("��ѯ�������...");
		logger.debug("�ɼ�����IDΪ��" + srvTargetId);

		collectLogVo.setCollect_task_id(taskVo.getCollect_task_id());
		collectLogVo.setCollect_type(taskVo.getCollect_type());
		collectLogVo.setTask_name(taskVo.getTask_name());

		logger.debug("��ʼ��ѯ�ɼ�����...");
		VoResServiceTargets srvTargetVo = new VoResServiceTargets();
		Map srvTargetMap = taskInfo.querySrvTager(srvTargetId);
		ParamUtil.mapToBean(srvTargetMap, srvTargetVo, false);
		logger.debug("��ѯ�ɼ��������...");

		// ��־�������
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
		ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
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
				//�������ݿ����õ��ļ��б����������ļ�������
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
						//�ļ��ɹ����ر�־
						tmpMap.put("flag", "Y");
					}
				}
			//fileList = DownloadFile.downFtpFile(taskMap, ExConstant.FILE_FTP);// //��ȡftp�ļ������ص���������
			
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			throw new TxnDataException("error", "����ftp����Դʧ��!");
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
					continue;//����ļ������ڻ�δ���سɹ���ִ�к�������
				}
				System.out.println("filemap="+map);
				logFile  = (String) map.get("LOG_FILE_PATH");
				filename = (String) map.get("FILE_NAME_EN");
				speator	 = (String) map.get("FILE_SEPEATOR");//�ָ���
				titleType= (String) map.get("FILE_TITLE_TYPE");//�����ļ�����������  "EN"��ʾӢ��  "CN"��ʾ����
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
					System.out.println("��ѯftp�ļ���sql==" + sql);
					tablepMap = daoTable.queryService(sql.toString());
					tableName = (String) tablepMap.get("TABLE_NAME_EN");// �ɼ���
					tableId = (String) tablepMap.get("COLLECT_TABLE");// �ɼ���ID
					System.out.println("tableId==" + tableId);
					System.out.println("tableName==" + tableName);
					collect_mode = (String) tablepMap.get("COLLECT_MODE");// �ɼ���ʽ
					taskMap=new HashMap(map);
					taskMap.put("TASK_ID", tablepMap.get("FTP_TASK_ID"));// ����ID
					taskMap.put("SERVICE_NO", tablepMap.get("SERVICE_NO"));// ������
					taskMap.put("COLLECT_TABLE", tableId);// �ɼ���ID
					taskMap.put("COLLECT_TABLE_NAME", tableName);// �ɼ�������
					taskMap.put("COLLECT_MODE", collect_mode);// �ɼ���ʽ
					collectLogVo.setCollect_task_id(tableId);
					collectLogVo.setCollect_table_name(tableName);
					collectLogVo.setCollect_mode(collect_mode);

					// ����ftp�ļ��� �ļ�·��
					sql = new StringBuffer();
					sql.append("update collect_ftp_task set fj_path = '"
							+ filePath + "'");
					sql.append(" where file_name_en = '" + ftpFileName
							+ "'  and collect_task_id = '" + collect_task_id
							+ "'");
					Connection conn = null;
					Statement st = null;
					try {
						conn = DbUtils.getConnection("2"); // ʵ����con
						// 5��Ӧ����DataSource5�������Դ
						// conn.setAutoCommit(false);
						st = conn.createStatement();
						st.executeUpdate(sql.toString());// ִ��sql
						// conn.commit();
					} catch (Exception e) {
						throw new TxnDataException("error", "����ftp�ļ����ļ�·��ʧ��!");
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

			// ���²ɼ�״̬
			Connection conn = null;
			Statement st = null;
			String sql2 = "update collect_task set collect_status = '" + cj_zt
					+ "',log_file_path = '" + logFile
					+ "' where collect_task_id = '" + collect_task_id + "'";
			System.out.println("���²ɼ������ɼ�״̬����־�ļ�·��sql=====" + sql2);
			try {
				conn = DbUtils.getConnection("2"); // ʵ����con
				// 5��Ӧ����DataSource5�������Դ
				st = conn.createStatement();
				st.executeUpdate(sql2);// ִ��sql
			} catch (Exception e) {
				throw new TxnDataException("error", "����ftp�ļ����ļ�·��ʧ��!");
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
		Long end1 = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
		String consumeTime1 = String.valueOf(((end1 - start) / 1000f));
		collectLogVo.setTask_consume_time(consumeTime1);
		logger.debug("�ɼ����ݹ���ʱ��" + consumeTime1 + "��");

		SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endTime = end.format(new java.util.Date());// ϵͳ��ǰ������ʱ����
		collectLogVo.setTask_end_time(endTime);
		logger.debug("��ʼ��¼��־...");
		taskInfo.insertLog(collectLogVo);
		logger.debug("��¼��־�ɹ�...");
	}
	/**
	 * 
	 * doCollectTaskFtp1 ִ�� FTP �ɼ�����
	 * 
	 * @param taskId
	 * @throws Exception 
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void doCollectTaskFtp1(String collect_task_id) throws Exception
	{
		System.out.println("doCollectTaskFtp1 begin");
		//1.׼����־��¼������Ϣ
		
		Long start = System.currentTimeMillis(); // ��ʼʱ�����ڼ����ʱ
		String batch = UuidGenerator.getUUID(); // ���κ�
		CollectLogVo collectLogVo = new CollectLogVo(); // �ɼ���־��¼
		collectLogVo.setBatch_num(batch);
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime1 = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
		collectLogVo.setTask_start_time(startTime1); // �ɼ���ʼʱ��

		Map taskMap1 = taskInfo.queryTask(collect_task_id);
		VoCollectTask taskVo = new VoCollectTask();
		ParamUtil.mapToBean(taskMap1, taskVo, false);
		String srvTargetId = taskVo.getService_targets_id();
		logger.debug("��ѯ�������...");
		logger.debug("�ɼ�����IDΪ��" + srvTargetId);

		collectLogVo.setCollect_task_id(taskVo.getCollect_task_id());
		collectLogVo.setCollect_type(taskVo.getCollect_type());
		collectLogVo.setTask_name(taskVo.getTask_name());

		logger.debug("��ʼ��ѯ�ɼ�����...");
		VoResServiceTargets srvTargetVo = new VoResServiceTargets();
		Map srvTargetMap = taskInfo.querySrvTager(srvTargetId);
		ParamUtil.mapToBean(srvTargetMap, srvTargetVo, false);
		logger.debug("��ѯ�ɼ��������...");

		// ��־�������
		collectLogVo.setService_targets_id(srvTargetVo.getService_targets_id());
		collectLogVo.setService_targets_name(srvTargetVo
				.getService_targets_name());
		collectLogVo.setIs_formal(srvTargetVo.getIs_formal());

		//2.��FTP�����������ļ�
		List fileList = null;//Ҫ���ص��ļ���Ϣ
		Map tablepMap = null;
		HashMap taskMap = null;
		String user = "";
		StringBuffer sql = new StringBuffer();
		ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
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
				//�������ݿ����õ��ļ��б����������ļ�������
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
						//�ļ��ɹ����ر�־
						tmpMap.put("flag", "Y");
					}
				}
			//fileList = DownloadFile.downFtpFile(taskMap, ExConstant.FILE_FTP);// //��ȡftp�ļ������ص���������
			
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			throw new TxnDataException("error", "����ftp����Դʧ��!");
		}
		
		
		//3.���ζ�ȡ�ļ����ݲ�д���Ӧ�Ĳɼ���
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
					continue;//����ļ������ڻ�δ���سɹ���ִ�к�������
				}
				System.out.println("filemap="+map);
				logFile  = (String) map.get("LOG_FILE_PATH");
				filename = (String) map.get("FILE_NAME_EN");
				speator	 = (String) map.get("FILE_SEPEATOR");//�ָ���
				titleType= (String) map.get("FILE_TITLE_TYPE");//�����ļ�����������  "EN"��ʾӢ��  "CN"��ʾ����
				filePath = ExConstant.FILE_FTP + File.separator + filename;

				if (filename != null && !"".equals(filename)) {
					tablepMap = new HashMap();
					filetype = filename.substring(filename.indexOf(".") + 1);
					//��־�ļ�
					if (logFile == null || "".equals(logFile)) {
						logFile = filePath.substring(0, filePath
								.lastIndexOf("."))
								+ "_"
								+ startTime.replace("-", "").replace(":", "")
										.replace(" ", "")
								+ "_CollectFileImportResult.txt";
					}
					//����ļ�
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
					System.out.println("��ѯftp�ļ���sql==" + sql);
					tablepMap = daoTable.queryService(sql.toString());
					tableName = (String) tablepMap.get("TABLE_NAME_EN");// �ɼ���
					tableId = (String) tablepMap.get("COLLECT_TABLE");// �ɼ���ID
					System.out.println("tableId==" + tableId);
					System.out.println("tableName==" + tableName);
					collect_mode = (String) tablepMap.get("COLLECT_MODE");// �ɼ���ʽ
					taskMap=new HashMap(map);
					taskMap.put("TASK_ID", tablepMap.get("FTP_TASK_ID"));// ����ID
					taskMap.put("SERVICE_NO", tablepMap.get("SERVICE_NO"));// ������
					taskMap.put("COLLECT_TABLE", tableId);// �ɼ���ID
					taskMap.put("METHOD_NAME_EN", tablepMap.get("FILE_NAME_EN"));
					taskMap.put("METHOD_NAME_CN", tablepMap.get("FILE_NAME_CN"));
					taskMap.put("COLLECT_TABLE_NAME", tableName);// �ɼ�������
					taskMap.put("COLLECT_MODE", collect_mode);// �ɼ���ʽ
					taskMap.put("COLLECT_TASK_ID", tablepMap.get("COLLECT_TASK_ID"));
					taskMap.put("SERVICE_TARGETS_NAME",tablepMap.get("SERVICE_TARGETS_NAME"));//�����������
					taskMap.put("BATCH", batch);//��־���κ�
					taskMap.put("SERVICE_NO",tablepMap.get("SERVICE_NO") );
					taskMap.put("IS_FORMAL", "Y");
					collectLogVo.setCollect_task_id((String)tablepMap.get("COLLECT_TASK_ID"));
					collectLogVo.setCollect_table(tableId);
					collectLogVo.setCollect_table_name(tableName);
					collectLogVo.setCollect_mode(collect_mode);

					// ����ftp�ļ��� �ļ�·��
					sql = new StringBuffer();
					sql.append("update collect_ftp_task set fj_path = '"
							+ filePath + "'");
					sql.append(" where file_name_en = '" + ftpFileName
							+ "'  and collect_task_id = '" + collect_task_id
							+ "'");
					Connection conn = null;
					Statement st = null;
					try {
						conn = DbUtils.getConnection("2"); // ʵ����con
						// 5��Ӧ����DataSource5�������Դ
						// conn.setAutoCommit(false);
						st = conn.createStatement();
						st.executeUpdate(sql.toString());// ִ��sql
						// conn.commit();
					} catch (Exception e) {
						throw new TxnDataException("error", "����ftp�ļ����ļ�·��ʧ��!");
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
					
					//��Ӣ����ΪVALUE ������ΪKEY ���������ļ��б�����Ϊ����
					HashMap colNamesMap = new HashMap();
					
					
					StringBuffer colTable_ColumnInfo = new StringBuffer();
					if (list != null && list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							tableMap = (Map) list.get(j);
							
							colNamesMap.put(tableMap.get("DATAITEM_NAME_CN")
									.toString().toUpperCase(), tableMap.get("DATAITEM_NAME_EN")
									.toString().toUpperCase());
							
							
							//�����ֶ� д����־�ļ���
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

			// ���²ɼ�״̬
			Connection conn = null;
			Statement st = null;
			String sql2 = "update collect_task set collect_status = '" + cj_zt
					+ "',log_file_path = '" + logFile
					+ "' where collect_task_id = '" + collect_task_id + "'";
			System.out.println("���²ɼ������ɼ�״̬����־�ļ�·��sql=====" + sql2);
			try {
				conn = DbUtils.getConnection("2"); // ʵ����con
				// 5��Ӧ����DataSource5�������Դ
				st = conn.createStatement();
				st.executeUpdate(sql2);// ִ��sql
			} catch (Exception e) {
				throw new TxnDataException("error", "����ftp�ļ����ļ�·��ʧ��!");
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
		Long end1 = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
		String consumeTime1 = String.valueOf(((end1 - start) / 1000f));
		collectLogVo.setTask_consume_time(consumeTime1);
		logger.debug("�ɼ����ݹ���ʱ��" + consumeTime1 + "��");

		SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endTime = end.format(new java.util.Date());// ϵͳ��ǰ������ʱ����
		collectLogVo.setTask_end_time(endTime);
		collectLogVo.setMethod_name_cn("11");
		logger.debug("��ʼ��¼��־...");
		//taskInfo.insertLog(collectLogVo);
		logger.debug("��¼��־�ɹ�...");
	}
	
	
	/**
	 * Ϊ�ļ������������ò���Mapֵ
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
				
				//�����ֶ� д����־�ļ���
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
	 * ������־��¼���õ�Map
	 * @param fileMap
	 * @param batch
	 * @return
	 */
	public HashMap setLogMap(Map fileMap,String batch)
	{
		HashMap logMap =new HashMap();
		logMap.put("TASK_NAME", fileMap.get("TASK_NAME"));//��������
		logMap.put("SERVICE_TARGETS_ID", fileMap.get("SERVICE_TARGETS_ID"));//�������ID 
		logMap.put("COLLECT_TYPE", fileMap.get("COLLECT_TYPE"));//�ɼ�����
		logMap.put("TASK_ID", fileMap.get("FTP_TASK_ID"));// ����ID
		logMap.put("SERVICE_NO", fileMap.get("SERVICE_NO"));// ������
		logMap.put("COLLECT_TABLE", fileMap.get("COLLECT_TABLE"));// �ɼ���ID
		logMap.put("METHOD_NAME_EN", fileMap.get("FILE_NAME_EN"));
		logMap.put("METHOD_NAME_CN", fileMap.get("FILE_NAME_CN"));
		logMap.put("COLLECT_TABLE_NAME", fileMap.get("TABLE_NAME_EN"));// �ɼ�������
		logMap.put("COLLECT_MODE", fileMap.get("COLLECT_MODE"));// �ɼ���ʽ
		logMap.put("COLLECT_TASK_ID", fileMap.get("COLLECT_TASK_ID"));
		logMap.put("SERVICE_TARGETS_NAME",fileMap.get("SERVICE_TARGETS_NAME"));//�����������
		logMap.put("BATCH", batch);//��־���κ�
		logMap.put("SERVICE_NO",fileMap.get("SERVICE_NO") );
		logMap.put("IS_FORMAL", "Y");
		return logMap;
	}
	/**
	 * ����ftp������ļ�·��
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
			st.executeUpdate(sql.toString());// ִ��sql
			// conn.commit();
		} catch (Exception e) {
			throw new TxnDataException("error", "����ftp�ļ����ļ�·��ʧ��!");
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
		logger.info("���²ɼ������ɼ�״̬sql=====" + sql);
		try {
			conn = DbUtils.getConnection("2"); // ʵ����con
			// 5��Ӧ����DataSource5�������Դ
			st = conn.createStatement();
			st.executeUpdate(sql);// ִ��sql
		} catch (Exception e) {
			throw new TxnDataException("error", "���²ɼ������ɼ�״̬ʧ��!");
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
	 * �������ص������ļ� ������д���Ӧ�Ĳɼ���
	 * @param fileMap  �ļ���Ϣ
	 * @param collect_task_id  �ɼ�����ID
	 * @param batch ��־���κ�
	 * @param dao   ���ݿ�����dao
	 * @param conn ���ݿ�����
	 * @throws Exception 
	 */
	public String dealFileData(Map fileMap,String collect_task_id,String batch,ServiceDAO dao,Connection conn) throws Exception{
		
		//ȫ�ֲ���
		
		//��־�ļ�����ȫ·����
		String logFile  = "";
		//����ļ�
		String resultFile="";
		//�ļ���
		String filename = (String) fileMap.get("FILE_NAME_EN");
		//�зָ���
		String speator	 = (String) fileMap.get("FILE_SEPEATOR");
		//�����ļ�����������  "EN"��ʾӢ��  "CN"��ʾ����
		String titleType= (String) fileMap.get("FILE_TITLE_TYPE");
		//�ɼ�״̬
		String cj_zt = "";
		//�ļ����ر���·��
		String filePath = ExConstant.FILE_FTP + File.separator + filename;
		//��ʼʱ��
		String startTime = CalendarUtil.getCurrentDateTime();
		//�ļ�����������
		AnalyCollectFile file = new AnalyCollectFile();
		if (filename != null && !"".equals(filename)) {
			String filetype = filename.substring(filename.indexOf(".") + 1);
			//��־�ļ�
			logFile = filePath.substring(0, filePath.lastIndexOf("."))
						+ "_"
						+ startTime.replace("-", "").replace(":", "")
								.replace(" ", "")
						+ "_CollectFileImportResult.txt";
			
			//����ļ�
			resultFile = ExConstant.FILE_FTP + File.separator
					+ filename.substring(0, filename.indexOf("."))
					+ "_result.txt";
			
			//��־��¼����
			HashMap logMap = setLogMap(fileMap,batch);
			System.out.println("logMap="+logMap);
			// ����ftp�ļ��� �ļ�·��
			updateFilePath(conn,filePath,filename,collect_task_id);			
			logger.info("����ftp�ļ��� �ļ�·��");
			
			
			//��Ӣ����ΪVALUE ������ΪKEY ���������ļ��б����д���
			HashMap colsMap = new HashMap();			
			//�����ֶ� д����־�ļ���
			StringBuffer colTable_ColumnInfo = new StringBuffer();
			//Ϊ�ļ������������ò���Mapֵ
			setColsNameMap(colsMap,colTable_ColumnInfo,(String)fileMap.get("COLLECT_TABLE"),dao);
			
			
			if (filetype != null && (filetype.equals("xls"))
					|| filetype.equals("xlsx")) {// excel
				logger.info("��ʼexcel�ļ�����");
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
						checkResultPath, "�û�", logFile,
						colTable_ColumnInfo);

			} else if (filetype != null && filetype.equals("txt")) {
				//У�����ļ�
				String checkResultPath = filePath.substring(0, filePath
						.lastIndexOf("."))
						+ "_txt_" + "checkResult.txt";
				logger.info("txt�ļ���ʼ����");
				cj_zt = file.analyTxtData3New(logMap, filePath,
						(String)fileMap.get("TABLE_NAME_EN"), colsMap,titleType,(String)fileMap.get("COLLECT_MODE"), speator,
						checkResultPath, "�û�", logFile,
						colTable_ColumnInfo);
					
				
			} else if (filetype != null && filetype.equals("mdb")) {
				logger.info("��δʵ��mdb��ʽ�ļ�����");
			}else{
				logger.info("��֧�ֵ��ļ���ʽ filetype="+filetype);
			}
			
		}
		return cj_zt;
	}
	
	
	/**
	 * ���ݹ�������ļ�����׺
	 * @param filename
	 * @return
	 */
	public String getFileName(Map fileMap){
		//��ȡ��ǰ����
		Calendar calendar = Calendar.getInstance();
		//ָ�����ڸ�ʽ
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		String nameType =(String)fileMap.get("NAME_TYPE");//�������� M���� D����
		String fileName =(String)fileMap.get("FILE_NAME_EN").toString();//�ļ���ǰ׺
		String filetype=fileName.substring(fileName.indexOf(".") + 1);//�ļ���չ��
		
		String date=""; //���������ɵ������ַ���������Ϊ�ļ�����׺
		if("M".equals(nameType)){//���¹�����������
			String month    =(String)fileMap.get("MONTH");//���·�ʽ���¹���
			String dayMonth =(String)fileMap.get("DAY_MONTH");//���·�ʽ��ָ����
			if(StringUtils.isBlank(month)){
				//Ĭ��Ϊ����
				month="0";
			}
			if(StringUtils.isBlank(dayMonth)){
				//Ĭ��Ϊÿ��1��
				dayMonth="1";
			}
			System.out.println("month="+month+"----day="+dayMonth);
			//�����·�
			int monthInt=Integer.parseInt(month);			
			//������
			if ("last".equals(dayMonth)) {
				//�����һ��
				monthInt+=1;
				System.out.println("monthInt="+monthInt);
				calendar.add(Calendar.MONTH, monthInt);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
			}else {
				//ָ�����պ�
				calendar.add(Calendar.MONTH, monthInt);
				int dayMonthInt=Integer.parseInt(dayMonth);
				calendar.set(Calendar.DAY_OF_MONTH, dayMonthInt);
			}
			date = df.format(calendar.getTime());
		}else if("D".equals(nameType)){//���չ�����������
			String dayNumStr=(String)fileMap.get("DAY_NUM");//���շ�ʽ���չ���
			if(StringUtils.isBlank(dayNumStr)){
				//Ĭ��Ϊ����
				dayNumStr="0";
			}
			int dayNumInt=Integer.parseInt(dayNumStr);
			calendar.add(Calendar.DAY_OF_MONTH, dayNumInt);
			date = df.format(calendar.getTime());
		}
		System.out.println("date="+date);
		//Ϊ�ļ�������ʱ���׺
		fileName=fileName.substring(0, fileName.lastIndexOf("."))+"_"+date+"."+filetype;
		return fileName;
	}
	/**
	 * 
	 * doCollectTaskFtpNew ִ�� FTP �ɼ�����
	 * 
	 * @param taskId
	 * @throws DBException
	 * @throws IOException
	 * @throws TxnDataException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void doCollectTaskFtpNew(String collect_task_id) throws DBException,
			IOException, TxnDataException
	{

		logger.info("-----FTP�ɼ�����ʼִ��-----");
		
		//ȫ�ֲ���
		String batch = UuidGenerator.getUUID(); // ��־���κ�
		List fileList = null;//Ҫ���ص��ļ���Ϣ
		ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao	
		String cj_zt = "";//�ɼ�״̬
				
		try {
			//��ѯ���ݿ��ȡFTP������Ϣ��Ҫ���ص��ļ���Ϣ
			
			StringBuffer sql = new StringBuffer();
			sql=SQLHelper.getFilesInfo(collect_task_id);
			
			fileList = daoTable.query(sql.toString());
			
			//�������ݿ����õ��ļ��б����������ļ������أ��������ݲ����Ӧ�Ĳɼ���
			if(fileList!=null){
				Connection conn = null;
				
				try {
					conn = DbUtils.getConnection("2"); // ʵ����con
					logger.info("����"+fileList.size()+"���ļ�Ҫ����");
					for(int i=0;i<fileList.size();i++){
						Map fileMap =(Map)fileList.get(i);
						String fileName = getFileName(fileMap);
						
						fileMap.put("FILE_NAME_EN", fileName);
						//��FTP�����������ļ�
						boolean flag= 
							FtpUtil.downFile(fileMap.get("DATA_SOURCE_IP").toString(),
								Integer.parseInt(fileMap.get("ACCESS_PORT").toString()), 
								fileMap.get("DB_USERNAME").toString(),
								fileMap.get("DB_PASSWORD").toString(), 
								fileMap.get("ACCESS_URL").toString(), 
								fileMap.get("FILE_NAME_EN").toString(),
								ExConstant.FILE_FTP);
						if(flag){
							//�ļ��ɹ����أ������ļ�������
							logger.info(fileName+"�ɹ�����");
							cj_zt = dealFileData(fileMap,collect_task_id,batch,daoTable,conn);
						}else{
							logger.info(fileName+"�ļ�����ʧ��");
						}
					}	
				} catch (Exception e) {
					throw new TxnDataException("error", e.getMessage());
				} finally {
					DbUtils.freeConnection(conn);
				}
			}else {
				logger.info("collect_task_id="+collect_task_id+" ����û��Ҫ���ص��ļ�");
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
			throw new TxnDataException("error", e.getMessage());
		}
		
		// ���²ɼ�״̬
		updateCollectStatus(cj_zt,collect_task_id);
			
		
	}
	
	/**
	 * �ֶ�ִ��FTP�ɼ�����
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
		ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
		//�ļ����ر���·��
		String filePath = ExConstant.FILE_FTP + File.separator + file_name_en;
		//��ȡ����Դ��Ϣ
		StringBuffer sql = new StringBuffer();
		sql=SQLHelper.getManualSource(data_source_id,collect_table);		
		List sourceList= daoTable.query(sql.toString());
		Map dataSource=new HashMap();
		if(sourceList!=null && sourceList.size()>0){
			dataSource =(Map)sourceList.get(0);	
			//logger.info("dataSource="+dataSource);
			//��FTP�����������ļ�
			boolean flag= 
				FtpUtil.downFile(dataSource.get("DATA_SOURCE_IP").toString(),
					Integer.parseInt(dataSource.get("ACCESS_PORT").toString()), 
					dataSource.get("DB_USERNAME").toString(),
					dataSource.get("DB_PASSWORD").toString(), 
					dataSource.get("ACCESS_URL").toString(), 
					file_name_en,
					ExConstant.FILE_FTP);
			if(flag){
				//�ļ��ɹ����أ������ļ�������
				logger.info(file_name_en+"�ɹ����� ");
				
				//�ļ�����������
				AnalyCollectFile file = new AnalyCollectFile();
				//��Ӣ����ΪVALUE ������ΪKEY ���������ļ��б����д���
				HashMap colsMap = new HashMap();			
				//�����ֶ� д����־�ļ���
				StringBuffer colTable_ColumnInfo = new StringBuffer();
				//Ϊ�ļ������������ò���Mapֵ
				setColsNameMap(colsMap,colTable_ColumnInfo,collect_table,daoTable);
				
				//У�����ļ�
				String checkResultPath = filePath.substring(0, filePath
						.lastIndexOf("."))
						+ "_txt_" + "checkResult.txt";
				logger.info("��ʼ�����ļ�");
				
				String[] sqls = file.analyTxt2(filePath, dataSource.get("TABLE_NAME_EN").toString(), colsMap,"CN",collect_mode,
						file_sepeator, checkResultPath);
				int count=0;
				if (sqls != null && sqls.length != 0) {
					try {
						logger.info("��ʼִ��SQL���");
						count = file.execBatch(sqls);
						if(count==-1){
							resultBus.put("count", "0");
							resultBus.put("errorMsg", "SQL���ִ�д���");
							count=0;
						}else{
							resultBus.put("count", count);
							resultBus.put("errorMsg", "�ɼ��ɹ�");
						}
					} catch (Exception e) {
						resultBus.put("count", count);
						resultBus.put("errorMsg", "�ɼ�ʧ��:���ݸ�ʽ����");
						throw new TxnDataException("�ɼ�����ʧ��", "���ݸ�ʽ����!");
					} finally {
						logger.info("����" + dataSource.get("TABLE_NAME_EN").toString() + "����ִ����" + count);
						return resultBus;
					}
				}else{
					resultBus.put("count", count);
					resultBus.put("errorMsg", "�ɼ�ʧ��:���ݸ�ʽ����");
					return resultBus;
				}
				
			}else{
				logger.info(file_name_en+"�ļ�����ʧ��");
				resultBus.put("count", "0");
				resultBus.put("errorMsg", "�ļ�����ʧ��");
				return resultBus;
			}
		}else{
			logger.info("û���ҵ���Ӧ������Դ��ɼ���");
			resultBus.put("count", "0");
			resultBus.put("errorMsg", "û���ҵ���Ӧ������Դ��ɼ���");
			return resultBus;
		}	
	}

}
