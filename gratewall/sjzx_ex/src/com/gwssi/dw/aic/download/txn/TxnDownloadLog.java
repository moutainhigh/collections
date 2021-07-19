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
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnDownloadLog.class,
														DownloadLogContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "download_log";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select download_log list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one download_log";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one download_log";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one download_log";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one download_log";

	private static CodeMap		code			= PublicResource
														.getCodeFactory();

	/**
	 * ���캯��
	 */
	public TxnDownloadLog()
	{

	}

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

	/**
	 * ��ѯ������־�б�
	 * 
	 * @param context
	 *            ����������
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
//				db.setValue("opertype", "����");
//				db.setValue("status", code.getCodeDesc(context, "����״̬", db
//						.getValue("status")));
//			} else {
//				db.setValue("opertype", "��ѯ");
//				db.setValue("apply_name", "--");
//				db.setValue("status", "--");
//
//			}
			if (StringUtils.isNotBlank(db.getValue("status"))) {
				db.setValue("opertype", "����");
				db.setValue("status", code.getCodeDesc(context, "����״̬", db
						.getValue("status")));
			}else {
				db.setValue("opertype", "��ѯ");
				db.setValue("apply_name", "--");
				db.setValue("status", "--");

			}
		}
	}

	/**
	 * �޸�������־��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60340002(DownloadLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoDownloadLog download_log = context.getDownloadLog(
		// inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ����������־��Ϣ
	 * 
	 * @param context
	 *            ����������
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
		// ���Ӽ�¼������ VoDownloadLog download_log = context.getDownloadLog(
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
	 * ��ѯ������־�����޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60340004(DownloadLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoDownloadLogPrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoDownloadLog result = context.getDownloadLog( outputNode );
	}

	/**
	 * ɾ��������־��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60340005(DownloadLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoDownloadLogPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * �鿴��ѯ����־��¼
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
	 * ���ظ���ķ����������滻���׽ӿڵ�������� ���ú���
	 * 
	 * @param funcName
	 *            ��������
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����");
		}

		// ִ��
		DownloadLogContext appContext = new DownloadLogContext(context);
		invoke(method, appContext);
	}

}
