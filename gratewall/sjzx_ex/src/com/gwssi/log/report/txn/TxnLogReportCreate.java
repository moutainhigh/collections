package com.gwssi.log.report.txn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoFileInfo;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.log.vo.LogReportCreateContext;
import cn.gwssi.dw.rd.log.vo.LogReportUseContext;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.DeleteFileUtil;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.UploadUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.common.util.ValueSetCodeUtil;
import com.gwssi.log.report.dao.LogReportManager;
import com.gwssi.log.systemlog.vo.FirstPageQuerySystemlogContext;

public class TxnLogReportCreate extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnLogReportCreate.class,
														LogReportCreateContext.class);

	CodeMap						codeMap			= PublicResource
														.getCodeFactory();

	// 数据表名称
	private static final String	TABLE_NAME		= "log_report_create";

	// 查询列表
	private static final String	ROWSET_FUNCTION	= "select log_report_create list";

	// 查询记录
	private static final String	SELECT_FUNCTION	= "select one log_report_create";

	// 修改记录
	private static final String	UPDATE_FUNCTION	= "update one log_report_create";

	// 增加记录
	private static final String	INSERT_FUNCTION	= "insert one log_report_create";

	// 删除记录
	private static final String	DELETE_FUNCTION	= "delete one log_report_create";

	public static final String	DB_CONFIG		= "app";

	/**
	 * 构造函数
	 */
	public TxnLogReportCreate()
	{

	}

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

	/**
	 * 查询系统使用情况报告列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn620200101(LogReportCreateContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoLogReportCreateSelectKey selectKey = context.getSelectKey(
		// inputNode );
		// table.executeFunction( ROWSET_FUNCTION, context, inputNode,
		// outputNode );
		
		String created_date= context.getSelectKey().getValue("created_time");
		System.out.println(created_date);
		LogReportCreateContext statecontext = new LogReportCreateContext();
		Attribute.setPageRow(statecontext, outputNode, -1);
		table.executeFunction("getStateSet", statecontext, inputNode,
				outputNode);
		Recordset stateRs = statecontext.getRecordset("record");
		context.setValue("state", JsonDataUtil.getJsonByRecordSet(stateRs));
		
		
		table.executeFunction("queryLogReportCreate", context, inputNode,
				outputNode);
		// context.setAttribute("record", "record-number", context
		// .getRecordset(outputNode).size() + "");
		// if (!context.getRecord(outputNode).isEmpty()
		// && !context.getRecord("attribute-node").isEmpty()) {
		// context.getRecord("attribute-node").setValue(
		// "record_record-number",
		// context.getRecordset(outputNode).size() + "");
		// }
		//System.out.println(context);
		// 查询到的记录集 VoLogReportCreate result[] = context.getLogReportCreates(
		// outputNode );

	}

	/**
	 * 查询系统使用情况报告列
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn620200191(LogReportCreateContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		LogReportCreateContext stateContext = new LogReportCreateContext();
		// 构造服务状态json数据
		stateContext.getRecord(inputNode).setValue("codetype", "报告状态");
		stateContext.getRecord(inputNode).setValue("column", "state");
		Attribute.setPageRow(stateContext, outputNode, -1);
		table.executeFunction("queryreport", stateContext, inputNode,
				outputNode);
		//System.out.println("stateContext===" + stateContext);
		Recordset stateRs = stateContext.getRecordset("record");

		context.setValue("repState", JsonDataUtil.getJsonByRecordSet(stateRs));

	}

	/**
	 * 查询系统使用情况报告列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn620200192(LogReportCreateContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoShareServiceSelectKey selectKey = context.getSelectKey(
		// inputNode );
		String create_date = context.getRecord("select-key").getValue(
				"create_date");
		if (StringUtils.isNotBlank(create_date)) {
			String[] ctime = com.gwssi.common.util.DateUtil
					.getDateRegionByDatePicker(create_date, true);
			context.getRecord("select-key").setValue("create_date_start",
					ctime[0]);
			context.getRecord("select-key").setValue("create_date_end",
					ctime[1]);
			context.getRecord("select-key").remove("create_date");
		}
		String publish_date = context.getRecord("select-key").getValue(
				"publish_date");
		if (StringUtils.isNotBlank(publish_date)) {
			String[] ctime = com.gwssi.common.util.DateUtil
					.getDateRegionByDatePicker(publish_date, true);
			context.getRecord("select-key").setValue("publish_date_start",
					ctime[0]);
			context.getRecord("select-key").setValue("publish_date_end",
					ctime[1]);
			context.getRecord("select-key").remove("publish_date");
		}

		table.executeFunction("queryLogReportCreate", context, inputNode,
				outputNode);

	}

	/**
	 * 修改系统使用情况报告信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn620200102(LogReportCreateContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 修改记录的内容 VoLogReportCreate log_report_create =
		// context.getLogReportCreate( inputNode );
		String path = context.getRecord("record").getValue("path");

		// 刪除服務器上的文件
		if (path != null && !"".equals(path)) {
			// if(DeleteFileUtil.deleteFile(path)){//刪除成功后重新上傳文件
			VoFileInfo[] files = context.getConttrolData().getUploadFileList();
			for (int i = 0; i < files.length; i++) {
				// 得到上传时的文件名
				String localName = files[i].getLocalFileName();
				// 得到文件在服务器上的绝对路径和文件名
				String serverName = files[i].getOriFileName();

				try {
					InputStream in = new FileInputStream(localName);
					OutputStream out = new FileOutputStream(new File(path));
					// 将文件流输出到指定上传目录
					try {
						UploadUtil.exchangeStream(in, out);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/*
			 * }else{ System.out.println("文件刪除失敗"); }
			 */
		}

		table.executeFunction("modifyReport", context, inputNode, outputNode);

		// 根据主键把记录查询出来
		this.callService("620200104", context);
		// 保存系统使用报告操作情况
		DataBus db = new DataBus();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		db.setValue("log_report_create_id", context.getRecord("record")
				.getValue("log_report_create_id"));
		db.setValue("oper_person", context.getOperData().getOperName());
		db.setValue("oper_date", df.format(new Date()));
		db.setValue("oper_remark", "修改系统使用报告["
				+ context.getRecord("record").getValue("report_name") + "]");
		LogReportUseContext context_use = new LogReportUseContext();
		context_use.addRecord("record", db);
		this.callService("620200203", context_use);
	}

	/**
	 * 增加系统使用情况报告信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn620200103(LogReportCreateContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		context.getRecord("record").setValue("log_report_create_id",
				UuidGenerator.getUUID());
		String year = context.getRecord("record").getValue("year");
		String month = context.getRecord("record").getValue("month");
		String fileName = new Date().getTime() + ".doc";
		context.getRecord("record").setValue("filename", fileName);
		String path = java.util.ResourceBundle.getBundle("app").getString(
				"docFilePath");
		File file = new File(path);
		// 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdir();
		}
		context.getRecord("record").setValue("path", path+File.separator+fileName);
		LogReportManager reportManager = new LogReportManager();
		try {
			reportManager.CreateReport(Integer.parseInt(year),
					Integer.parseInt(month), fileName);
			table.executeFunction("addLogReport", context, inputNode,
					outputNode);
		} catch (DBException e) {
			System.out.println("生成报告错误！");
		}
		// 保存系统使用报告操作情况
		DataBus db = new DataBus();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		db.setValue("log_report_create_id", context.getRecord("record")
				.getValue("log_report_create_id"));
		db.setValue("oper_person", context.getOperData().getOperName());
		db.setValue("oper_date", df.format(new Date()));
		db.setValue("oper_remark", "新增系统使用报告["
				+ context.getRecord("record").getValue("report_name") + "]");
		LogReportUseContext context_use = new LogReportUseContext();
		context_use.addRecord("record", db);
		this.callService("620200203", context_use);
	}

	/**
	 * 查询系统使用情况报告用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn620200104(LogReportCreateContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoLogReportCreatePrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录内容 VoLogReportCreate result = context.getLogReportCreate(
		// outputNode );
	}

	/**
	 * 删除系统使用情况报告信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn620200105(LogReportCreateContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 根据主键把记录查询出来
		this.callService("620200104", context);

		table.executeFunction("updateReportState", context, inputNode,
				outputNode);
		String path = context.getRecord("primary-key").getValue("path");
		// 删除相关文件
		if (path != null && !"".equals(path)) {
			try {
				DeleteFileUtil.deleteFile(path);
			} catch (Exception e) {
				System.out.println("文件删除失败！");
			}
		}

		// 保存系统使用报告操作情况
		DataBus db = new DataBus();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		db.setValue("log_report_create_id", context.getRecord("record")
				.getValue("log_report_create_id"));
		db.setValue("oper_person", context.getOperData().getOperName());
		db.setValue("oper_date", df.format(new Date()));
		db.setValue("oper_remark", "删除系统使用报告["
				+ context.getRecord("record").getValue("report_name") + "]");
		LogReportUseContext context_use = new LogReportUseContext();
		context_use.addRecord("record", db);
		this.callService("620200203", context_use);

	}

	/**
	 * 上传系统使用情况报告信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn620200106(LogReportCreateContext context)
			throws TxnException
	{

		String rootPath = java.util.ResourceBundle.getBundle(DB_CONFIG)
				.getString("docFilePath");
		File file = new File(rootPath);
		// 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdir();
		}
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		context.getRecord("record").setValue("log_report_create_id",
				UuidGenerator.getUUID());
		VoFileInfo[] files = context.getConttrolData().getUploadFileList();
		for (int i = 0; i < files.length; i++) {
			// 得到上传时的文件名
			String localName = files[i].getLocalFileName();
			// 得到文件在服务器上的绝对路径和文件名
			String serverName = files[i].getOriFileName();
			String timestamp = CalendarUtil.getCurrentDateTime();
			String filename = timestamp.replaceAll("-", "").replaceAll(":", "")
					.replaceAll(" ", "")
					+ ".doc";
			try {
				InputStream in = new FileInputStream(localName);
				OutputStream out = new FileOutputStream(new File(rootPath + ""
						+ filename));
				// 将文件流输出到指定上传目录
				try {
					UploadUtil.exchangeStream(in, out);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			context.getRecord("record").setValue("filename", filename);
			context.getRecord("record").setValue("path",
					rootPath + "/" + filename);

		}

		// table.executeFunction("upLoadLogReport", context, inputNode,
		// outputNode);
		// // 保存系统使用报告操作情况
		// DataBus db = new DataBus();
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// db.setValue("log_report_create_id", context.getRecord("record")
		// .getValue("log_report_create_id"));
		// db.setValue("oper_person", context.getOperData().getOperName());
		// db.setValue("oper_date", df.format(new Date()));
		// db.setValue("oper_remark", "删除系统使用报告["
		// + context.getRecord("record").getValue("report_name") + "]");
		// LogReportUseContext context_use = new LogReportUseContext();
		// context_use.addRecord("record", db);
		// this.callService("620200203", context_use);

	}

	/**
	 * 发布系统使用情况报告
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn620200107(LogReportCreateContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("publishReport", context, inputNode, outputNode);
		// 根据主键把记录查询出来
		context.addRecord("primary-key", context.getRecord("select-key"));
		this.callService("620200104", context);
		// 保存系统使用报告操作情况
		DataBus db = new DataBus();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		db.setValue("log_report_create_id", context.getRecord("record")
				.getValue("log_report_create_id"));
		db.setValue("oper_person", context.getOperData().getOperName());
		db.setValue("oper_date", df.format(new Date()));
		db.setValue("oper_remark", "发布系统使用报告["
				+ context.getRecord("record").getValue("report_name") + "]");
		LogReportUseContext context_use = new LogReportUseContext();
		context_use.addRecord("record", db);
		this.callService("620200203", context_use);

		this.callService("620200101", context);

	}

	/**
	 * 增加报告操作情况记录
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn620200108(LogReportCreateContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// System.out.println("id::"+context.getRecord(""));
		// 查询记录的主键 VoLogReportCreatePrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		table.executeFunction("addReportUse", context, inputNode, outputNode);
		this.callService("620200101", context);
		// table.executeFunction( SELECT_FUNCTION, context, inputNode,
		// outputNode );
		// 查询到的记录内容 VoLogReportCreate result = context.getLogReportCreate(
		// outputNode );
	}

	/**
	 * 退回系统使用情况报告用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn620200109(LogReportCreateContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("returnReport", context, inputNode, outputNode);
		// 根据主键把记录查询出来
		context.addRecord("primary-key", context.getRecord("select-key"));
		this.callService("620200104", context);
		// 保存系统使用报告操作情况
		DataBus db = new DataBus();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		db.setValue("log_report_create_id", context.getRecord("record")
				.getValue("log_report_create_id"));
		db.setValue("oper_person", context.getOperData().getOperName());
		db.setValue("oper_date", df.format(new Date()));
		db.setValue("oper_remark", "退回系统使用报告["
				+ context.getRecord("record").getValue("report_name") + "]");
		LogReportUseContext context_use = new LogReportUseContext();
		context_use.addRecord("record", db);
		this.callService("620200203", context_use);
		// 重新查询列表
		this.callService("620200101", context);

	}

	/**
	 * 查找系统使用报告名称
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn620200110(LogReportCreateContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("checkReportName", context, inputNode, outputNode);
		Recordset rs1 = context.getRecordset("record");
		if (rs1 != null && rs1.size() > 0) {
			throw new TxnErrorException("999999:未知的错误", "报告已经存在！");
		}

	}

	/**
	 * 退回系统使用情况报告用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn620200111(LogReportCreateContext context)
			throws TxnException, DBException
	{
		// LogReportManager reportManager = new LogReportManager();
		// reportManager.CreateReport(2012, 8);

	}

	/**
	 * 重载父类的方法，用于替换交易接口的输入变量 调用函数
	 * 
	 * @param funcName
	 *            方法名称
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数");
		}

		// 执行
		LogReportCreateContext appContext = new LogReportCreateContext(context);
		invoke(method, appContext);
	}
}
