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
 * ҵ����־��ѯ ������Ϣ��module �����: cn.gwssi.app.bizlog.dao.BizlogDao
 * 
 * @author Administrator
 */
public class TxnBizlog extends TxnService
{
	// ���ݱ�����
	private static final String	TABLE_NAME	= "biz_log";

	/**
	 * ȡstep��init-value�����ò���������ʵ�ʵĲ����޸�
	 * 
	 * @param context
	 *            �����Ӧ�����
	 * @throws cn.gwssi.common.component.exception.TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * ��ѯҵ����־�б� ���ף�981211 �Ĵ����� ����������壺BizlogRowsetForm
	 * 
	 * @param context
	 *            ����������
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
		 * �޸�ҳ���ϵ����ں�ʱ���ʽ 20070101 --> 2007-01-01 175959 --> 17:59:59
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
	 * ��ѯҵ����־ ���ף�981214 �Ĵ����� ����������壺BizlogUpdateForm
	 * 
	 * @param context
	 *            ����������
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
	 * ��ȡblob�ֶ�����
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
	 * ��¼��־
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn981217(TxnContext context) throws TxnException
	{
		DataBus biz_log = context.getRecord("biz_log");
		// ������Ϣ
		VoCtrl ctrl = context.getConttrolData();

		// û����ˮ�ŵĽ��ײ���¼��־
		String flowid = ctrl.getFlowid();
		if (flowid == null) {
			flowid = String.valueOf(System.currentTimeMillis());
		}
		// ������־����
		VoBizlog logData = new VoBizlog();
		logData.setReqflowno(flowid);
		// logData.setUsername( biz_log.getValue("trdtype") );
		// logData.setOpername( biz_log.getValue("trdtype") );
		// logData.setOrgname( biz_log.getValue("trdtype") );

		// ��������
		logData.setTrdtype(biz_log.getValue("trdtype"));
		// ������
		logData.setErrcode(biz_log.getValue("errcode"));
		logData.setErrdesc(biz_log.getValue("errdesc"));
		// �������
		logData.setResultdata(biz_log.getValue("desc"));
		// ���Ӽ�¼
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeUpdate("insert one biz_log", context, logData);
	}

	/**
	 * �û���־��ѯ ���ף�981216 �Ĵ����� ����������壺UserBizlogRowsetForm
	 * 
	 * @param context
	 *            ����������
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
