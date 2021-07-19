package com.gwssi.dw.aic.download.txn;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.dw.aic.download.vo.DownloadLogContext;

public class TxnDownloadLog extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnDownloadLog.class,
														DownloadLogContext.class);

	// 数据表名称
	private static final String	TABLE_NAME		= "download_log";

	// 查询列表
	private static final String	ROWSET_FUNCTION	= "select download_log list";

	// 查询记录
	private static final String	SELECT_FUNCTION	= "select one download_log";

	// 修改记录
	private static final String	UPDATE_FUNCTION	= "update one download_log";

	// 增加记录
	private static final String	INSERT_FUNCTION	= "insert one download_log";

	// 删除记录
	private static final String	DELETE_FUNCTION	= "delete one download_log";

	private static CodeMap		code			= PublicResource
														.getCodeFactory();

	/**
	 * 构造函数
	 */
	public TxnDownloadLog()
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
	 * 查询下载日志列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn60340001(DownloadLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("loadDownloadLogList", context, inputNode,
				outputNode);
		Recordset rs = context.getRecordset(outputNode);
		while (rs.hasNext()) {
			DataBus db = (DataBus) rs.next();
//			if (db.getValue("opertype").equals("0")) {
//				db.setValue("opertype", "下载");
//				db.setValue("status", code.getCodeDesc(context, "申请状态", db
//						.getValue("status")));
//			} else {
//				db.setValue("opertype", "查询");
//				db.setValue("apply_name", "--");
//				db.setValue("status", "--");
//
//			}
			if (StringUtils.isNotBlank(db.getValue("status"))) {
				db.setValue("opertype", "下载");
				db.setValue("status", code.getCodeDesc(context, "申请状态", db
						.getValue("status")));
			}else {
				db.setValue("opertype", "查询");
				db.setValue("apply_name", "--");
				db.setValue("status", "--");

			}
		}
	}

	/**
	 * 修改下载日志信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn60340002(DownloadLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 修改记录的内容 VoDownloadLog download_log = context.getDownloadLog(
		// inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 增加下载日志信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn60340003(DownloadLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		DataBus dataBus = context.getRecord(inputNode);
		DataBus operData = context.getRecord("oper-data");
		String download_cond = dataBus.getValue("download_cond");

		dataBus.setValue("download_log_id", com.gwssi.common.util.UuidGenerator
				.getUUID());
		dataBus.setValue("operdate", com.gwssi.common.util.DateUtil
				.getYMDTime());
		dataBus.setValue("opertime", com.gwssi.common.util.DateUtil
				.getHHmmssTime());
		dataBus.setValue("opertor", operData.getValue("oper-name"));
		dataBus.setValue("operdept", operData.getValue("org-code"));
		dataBus.setValue("yhid_pk", operData.getValue("userID"));
//		dataBus.setValue("opertype", dataBus.getValue("opertype") == null ? "0"
//				: dataBus.getValue("opertype"));
		try {
			dataBus.setValue("opername", java.net.URLDecoder.decode(dataBus
					.getValue("opername"), "UTF-8"));
			if (download_cond != null && !download_cond.equals("")) {
				if (getFindCount(download_cond) >= 20) {
					dataBus.setValue("download_cond", java.net.URLDecoder
							.decode(download_cond, "UTF-8"));
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 增加记录的内容 VoDownloadLog download_log = context.getDownloadLog(
		// inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	private int getFindCount(String str)
	{
		int len = str.length();
		int count = 0;
		for (int i = 0; i < len; i++) {
			String temp = str.charAt(i) + "";
			if (temp.equals("%")) {
				count++;
				if (count >= 20) {
					break;
				}
			}
		}
		return count;

	}

	/**
	 * 查询下载日志用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn60340004(DownloadLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoDownloadLogPrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录内容 VoDownloadLog result = context.getDownloadLog( outputNode );
	}

	/**
	 * 删除下载日志信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn60340005(DownloadLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 删除记录的主键列表 VoDownloadLogPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 查看查询的日志记录
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn60340006(DownloadLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("select one download_log1", context, inputNode,
				outputNode);
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
		DownloadLogContext appContext = new DownloadLogContext(context);
		invoke(method, appContext);
	}

}
