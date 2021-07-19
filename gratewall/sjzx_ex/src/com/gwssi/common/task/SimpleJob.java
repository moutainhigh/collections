package com.gwssi.common.task;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.TxnContext;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.jms.cj.producer.JmsClient;
import com.gwssi.socket.client.SocketClient;
import com.gwssi.webservice.client.DatabaseClient;
import com.gwssi.webservice.client.FtpClient;
import com.gwssi.webservice.client.TaskInfo;
import com.gwssi.webservice.client.WsClient;
import com.gwssi.webservice.server.CheckService;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�SimpleJob ��������ִ��������� �����ˣ�lizheng ����ʱ�䣺May 10, 2013
 * 4:03:57 PM �޸��ˣ�lizheng �޸�ʱ�䣺May 10, 2013 4:03:57 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class SimpleJob implements StatefulJob
{

	// ��־
	protected static Logger	logger		= TxnLogger.getLogger(SimpleJob.class
												.getName());

	TaskInfo				taskInfo	= new TaskInfo();

	/**
	 * ��ʼ������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
	}

	public void execute(JobExecutionContext jobCtx)
	{
		// ҵ���߼�
		// 1.�жϵ�ǰʱ���Ƿ��ڵ�ǰ����������õ�ʱ�䷶Χ��
		// 2.��ȡ��������ID taskId
		// 3.��ѯ�������������,���ͷ�Ϊweb service;FTP;���ݿ�ȵ�
		// 4.���ݲ�ͬ���������͵��ò�ͬ�Ľӿ�
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String nowTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
		logger.debug("��ǰʱ��Ϊ��" + nowTime + " ��ʼ�������SimpleJob_execute...");

		String taskId = jobCtx.getTrigger().getName(); // ����ID
		logger.debug("����IDΪ��[" + taskId + "]");

		String startStr = "";
		String endStr = "";
		JobDataMap dataMap = jobCtx.getJobDetail().getJobDataMap(); // ��ȡ��ʼʱ�䡢����ʱ��
		if (null != dataMap && !dataMap.isEmpty()) {
			if (null != dataMap.get("start"))
				startStr = dataMap.get("start").toString();
			if (null != dataMap.get("end"))
				endStr = dataMap.get("end").toString();
		} else {
			logger.debug("�����������ʱ��������...");
		}
		if (!"".equals(startStr) && !"".equals(endStr)) {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			String calNow = sd.format(new java.util.Date());
			String startTime = calNow + " " + startStr + ":00";
			String endTime = calNow + " " + endStr + ":00";

			// �Ƚ�ϵͳ��ǰʱ���������ȿ�ʼʱ�䡢����ʱ��
			CheckService check = new CheckService();
			boolean isIn = check.isInDatesForJob(nowTime.toString(), startTime,
					endTime);

			logger.debug("startTime��" + startTime);
			logger.debug("��ǰϵͳʱ��Ϊ��" + nowTime);
			logger.debug("endTime��" + endTime);
			if (isIn) {
				this.doTask(taskId);
			} else {
				logger.debug("���������ʱ�䷶Χ��...");
			}
		} else {
			logger.debug("�����������ʱ��������...");
		}
		logger.debug("�������SimpleJob_execute����...");
	}

	/**
	 * 
	 * doTask ��������
	 * 
	 * @param taskId
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private void doTask(String taskId)
	{
		if (null != taskId && !"".equals(taskId)) {
			String taskType = taskInfo.queryCollectType(taskId);
			logger.debug("�ɼ���������Ϊ��" + taskType);
			if (null != taskType
					&& CollectConstants.TYPE_CJLX_WEBSERVICE.equals(taskType)) {// webservice�ɼ�����
				doWebService(taskId);
			} else if (null != taskType
					&& CollectConstants.TYPE_CJLX_FTP.equals(taskType)) {// FTP�ɼ�����
				doFtp(taskId);
			} else if (null != taskType
					&& CollectConstants.TYPE_CJLX_SOCKET.equals(taskType)) {// socket�ɼ�����
				//doSocket(taskId);
			} else if (null != taskType
					&& CollectConstants.TYPE_CJLX_DATABASE.equals(taskType)) {// ���ݿ�ɼ�����
				//doDatabase(taskId);
			} else if (null != taskType
					&& CollectConstants.TYPE_CJLX_JMS.equals(taskType)) {// JMS�ɼ�����
				//doJms(taskId);
			}
		} else {
			logger.debug("����ID taskId ������...");
		}
	}

	/**
	 * 
	 * doWebService WebService����
	 * 
	 * @param taskId
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private void doWebService(String taskId)
	{
		try {
			WsClient.doCollectTask(taskId);
		} catch (DBException e) {
			logger.debug("doWebService ���ݿ�ִ�д���" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("doWebService �ļ��洢����" + e);
			e.printStackTrace();
		} finally {
			logger.debug("doWebService����...");
		}
	}

	/**
	 * 
	 * doFtp Ftp����
	 * 
	 * @param taskId
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private void doFtp(String taskId)
	{
		FtpClient ftpClient = new FtpClient();
		try {
			ftpClient.doCollectTaskFtpNew(taskId);
		} catch (DBException e) {
			logger.debug("doFtp ���ݿ�ִ�д���" + e);
			e.printStackTrace();
		} catch (TxnDataException e) {
			logger.debug("doFtp ��ȡftp�ļ�����" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("doFtp �ļ��洢����" + e);
			e.printStackTrace();
		} finally {
			logger.debug("doFtp����...");
		}
	}

	/**
	 * 
	 * doSocket Socket����
	 * 
	 * @param taskId
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private void doSocket(String taskId)
	{
		try {
			SocketClient.doCollectTask(taskId);
		} catch (DBException e) {
			logger.debug("doSocket ���ݿ�ִ�д���" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("doSocket �ļ��洢����" + e);
			e.printStackTrace();
		} finally {
			logger.debug("doFtp����...");
		}
	}

	/**
	 * 
	 * doDatabase Database����
	 * 
	 * @param taskId
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private void doDatabase(String taskId)
	{
		DatabaseClient databaseClient = new DatabaseClient();
		try {
			databaseClient.doCollectTaskDatabase(taskId);
		} catch (DBException e) {
			logger.debug("Database ���ݿ�ִ�д���" + e);
			e.printStackTrace();
		} catch (TxnDataException e) {
			logger.debug("Database ��ȡftp�ļ�����" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("Database �ļ��洢����" + e);
			e.printStackTrace();
		} finally {
			logger.debug("doDatabase����...");
		}
	}

	/**
	 * 
	 * doJms Jms����
	 * 
	 * @param taskId
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private void doJms(String taskId)
	{
		try {
			JmsClient.doCollectTask(taskId);
		} catch (DBException e) {
			logger.debug("doJms ���ݿ�ִ�д���" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("doJms �ļ��洢����" + e);
			e.printStackTrace();
		} finally {
			logger.debug("dJms����...");
		}
	}

}
