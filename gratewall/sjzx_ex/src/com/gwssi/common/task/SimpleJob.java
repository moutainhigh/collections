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
 * 项目名称：bjgs_exchange 类名称：SimpleJob 类描述：执行任务调度 创建人：lizheng 创建时间：May 10, 2013
 * 4:03:57 PM 修改人：lizheng 修改时间：May 10, 2013 4:03:57 PM 修改备注：
 * 
 * @version
 * 
 */
public class SimpleJob implements StatefulJob
{

	// 日志
	protected static Logger	logger		= TxnLogger.getLogger(SimpleJob.class
												.getName());

	TaskInfo				taskInfo	= new TaskInfo();

	/**
	 * 初始化函数
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
	}

	public void execute(JobExecutionContext jobCtx)
	{
		// 业务逻辑
		// 1.判断当前时间是否在当前任务允许调用的时间范围内
		// 2.获取参数任务ID taskId
		// 3.查询看此任务的类型,类型分为web service;FTP;数据库等等
		// 4.根据不同的任务类型调用不同的接口
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String nowTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		logger.debug("当前时间为：" + nowTime + " 开始任务调度SimpleJob_execute...");

		String taskId = jobCtx.getTrigger().getName(); // 任务ID
		logger.debug("任务ID为：[" + taskId + "]");

		String startStr = "";
		String endStr = "";
		JobDataMap dataMap = jobCtx.getJobDetail().getJobDataMap(); // 获取开始时间、结束时间
		if (null != dataMap && !dataMap.isEmpty()) {
			if (null != dataMap.get("start"))
				startStr = dataMap.get("start").toString();
			if (null != dataMap.get("end"))
				endStr = dataMap.get("end").toString();
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

			logger.debug("startTime：" + startTime);
			logger.debug("当前系统时间为：" + nowTime);
			logger.debug("endTime：" + endTime);
			if (isIn) {
				this.doTask(taskId);
			} else {
				logger.debug("在任务调度时间范围外...");
			}
		} else {
			logger.debug("此条任务调度时间有问题...");
		}
		logger.debug("任务调度SimpleJob_execute结束...");
	}

	/**
	 * 
	 * doTask 调用任务
	 * 
	 * @param taskId
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private void doTask(String taskId)
	{
		if (null != taskId && !"".equals(taskId)) {
			String taskType = taskInfo.queryCollectType(taskId);
			logger.debug("采集任务类型为：" + taskType);
			if (null != taskType
					&& CollectConstants.TYPE_CJLX_WEBSERVICE.equals(taskType)) {// webservice采集任务
				doWebService(taskId);
			} else if (null != taskType
					&& CollectConstants.TYPE_CJLX_FTP.equals(taskType)) {// FTP采集任务
				doFtp(taskId);
			} else if (null != taskType
					&& CollectConstants.TYPE_CJLX_SOCKET.equals(taskType)) {// socket采集任务
				//doSocket(taskId);
			} else if (null != taskType
					&& CollectConstants.TYPE_CJLX_DATABASE.equals(taskType)) {// 数据库采集任务
				//doDatabase(taskId);
			} else if (null != taskType
					&& CollectConstants.TYPE_CJLX_JMS.equals(taskType)) {// JMS采集任务
				//doJms(taskId);
			}
		} else {
			logger.debug("任务ID taskId 不存在...");
		}
	}

	/**
	 * 
	 * doWebService WebService任务
	 * 
	 * @param taskId
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private void doWebService(String taskId)
	{
		try {
			WsClient.doCollectTask(taskId);
		} catch (DBException e) {
			logger.debug("doWebService 数据库执行错误" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("doWebService 文件存储错误" + e);
			e.printStackTrace();
		} finally {
			logger.debug("doWebService结束...");
		}
	}

	/**
	 * 
	 * doFtp Ftp任务
	 * 
	 * @param taskId
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private void doFtp(String taskId)
	{
		FtpClient ftpClient = new FtpClient();
		try {
			ftpClient.doCollectTaskFtpNew(taskId);
		} catch (DBException e) {
			logger.debug("doFtp 数据库执行错误" + e);
			e.printStackTrace();
		} catch (TxnDataException e) {
			logger.debug("doFtp 获取ftp文件错误" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("doFtp 文件存储错误" + e);
			e.printStackTrace();
		} finally {
			logger.debug("doFtp结束...");
		}
	}

	/**
	 * 
	 * doSocket Socket任务
	 * 
	 * @param taskId
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private void doSocket(String taskId)
	{
		try {
			SocketClient.doCollectTask(taskId);
		} catch (DBException e) {
			logger.debug("doSocket 数据库执行错误" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("doSocket 文件存储错误" + e);
			e.printStackTrace();
		} finally {
			logger.debug("doFtp结束...");
		}
	}

	/**
	 * 
	 * doDatabase Database任务
	 * 
	 * @param taskId
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private void doDatabase(String taskId)
	{
		DatabaseClient databaseClient = new DatabaseClient();
		try {
			databaseClient.doCollectTaskDatabase(taskId);
		} catch (DBException e) {
			logger.debug("Database 数据库执行错误" + e);
			e.printStackTrace();
		} catch (TxnDataException e) {
			logger.debug("Database 获取ftp文件错误" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("Database 文件存储错误" + e);
			e.printStackTrace();
		} finally {
			logger.debug("doDatabase结束...");
		}
	}

	/**
	 * 
	 * doJms Jms任务
	 * 
	 * @param taskId
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private void doJms(String taskId)
	{
		try {
			JmsClient.doCollectTask(taskId);
		} catch (DBException e) {
			logger.debug("doJms 数据库执行错误" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("doJms 文件存储错误" + e);
			e.printStackTrace();
		} finally {
			logger.debug("dJms结束...");
		}
	}

}
