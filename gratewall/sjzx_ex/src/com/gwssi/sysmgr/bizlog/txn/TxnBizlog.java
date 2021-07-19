package com.gwssi.sysmgr.bizlog.txn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoCtrl;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.sysmgr.bizlog.vo.VoBizlog;

/**
 * 业务日志查询 分类信息：module 相关类: cn.gwssi.app.bizlog.dao.BizlogDao
 * 
 * @author Administrator
 */
public class TxnBizlog extends TxnService
{
	// 数据表名称
	private static final String	TABLE_NAME	= "biz_log";

	/**
	 * 取step中init-value的配置参数，根据实际的参数修改
	 * 
	 * @param context
	 *            请求和应答参数
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * 查询业务日志列表 交易：981211 的处理函数 输入输出定义：BizlogRowsetForm
	 * 
	 * @param context
	 *            交易上下文
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn981211(TxnContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String regdate = context.getRecord(inputNode).getValue("regdate");
		if (regdate != null && !regdate.equals("")) {
			context.getRecord(inputNode).setValue("regdate",
					regdate.replaceAll("-", ""));
		}
		table.executeFunction("selectBizLogList", context, inputNode,
				outputNode);
		/**
		 * 修改页面上的日期和时间格式 20070101 --> 2007-01-01 175959 --> 17:59:59
		 */
		Recordset rs = context.getRecordset(outputNode);
		for (int i = 0; i < rs.size(); i++) {
			DataBus dateBus = rs.get(i);
			dateBus.setValue("regdate", formatDate(dateBus.getValue("regdate")));
			dateBus.setValue("regtime", formatTime(dateBus.getValue("regtime")));
		}
	}

	static String formatDate(String str)
	{
		if (!str.equals("") && str.length() == 8) {
			str = str.substring(0, 4) + "-" + str.substring(4, 6) + "-"
					+ str.substring(6, 8);
		}
		return str;
	}

	static String formatTime(String str)
	{
		if (!str.equals("") && str.length() == 6) {
			str = str.substring(0, 2) + ":" + str.substring(2, 4) + ":"
					+ str.substring(4, 6);
		}
		return str;
	}

	/**
	 * 查询业务日志 交易：981214 的处理函数 输入输出定义：BizlogUpdateForm
	 * 
	 * @param context
	 *            交易上下文
	 * @throws cn.gwssi.common.component.exception.TxnException
	 * @throws DBException
	 */
	public void txn981214(TxnContext context) throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("select one biz_log", context, inputNode,
				outputNode);
//		String resultData=this.getBlob(context.getRecord(inputNode).getValue("flowno"));
//		context.getRecord(outputNode).setValue("resultdata", resultData);

	}

	/**
	 * 获取blob字段内容
	 * @param id
	 * @return
	 */
	public String getBlob(String id)
	{
		String result="";
		try {
			String sql = "select resultdata from biz_log where flowno=?";
			String connType = ResourceBundle.getBundle("app").getString(
					"dbConnectionType");
			Connection conn = DbUtils.getConnection(connType);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			InputStream in = null;
			if (rs.next()) {
				Object blobObj = rs.getBlob("RESULTDATA");
				Blob modelBLOB = (Blob) blobObj;
				in = modelBLOB.getBinaryStream();
				try {
					result = this.inputStream2String(in);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			DbUtils.freeConnection(conn);
			conn = null;
		} catch (DBException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String inputStream2String(InputStream is) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	/**
	 * 记录日志
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn981217(TxnContext context) throws TxnException
	{
		DataBus biz_log = context.getRecord("biz_log");
		// 控制信息
		VoCtrl ctrl = context.getConttrolData();

		// 没有流水号的交易不记录日志
		String flowid = ctrl.getFlowid();
		if (flowid == null) {
			flowid = String.valueOf(System.currentTimeMillis());
		}
		// 生成日志数据
		VoBizlog logData = new VoBizlog();
		logData.setReqflowno(flowid);
		// logData.setUsername( biz_log.getValue("trdtype") );
		// logData.setOpername( biz_log.getValue("trdtype") );
		// logData.setOrgname( biz_log.getValue("trdtype") );

		// 交易类型
		logData.setTrdtype(biz_log.getValue("trdtype"));
		// 处理结果
		logData.setErrcode(biz_log.getValue("errcode"));
		logData.setErrdesc(biz_log.getValue("errdesc"));
		// 结果数据
		logData.setResultdata(biz_log.getValue("desc"));
		// 增加记录
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeUpdate("insert one biz_log", context, logData);
	}

	/**
	 * 用户日志查询 交易：981216 的处理函数 输入输出定义：UserBizlogRowsetForm
	 * 
	 * @param context
	 *            交易上下文
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn981216(TxnContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("select user biz_log list", context, inputNode,
				outputNode);
	}
}
