package com.gwssi.dw.aic.download.txn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoCtrl;
import cn.gwssi.common.context.vo.VoFileInfo;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.UploadUtil;
import com.gwssi.dw.aic.download.vo.DownloadStatusContext;

public class TxnDownloadStatus extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnDownloadStatus.class,
														DownloadStatusContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "download_status";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select download_status list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one download_status";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one download_status";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one download_status";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one download_status";

	public static final String	DB_CONFIG		= "app";

	private String getUploadPath()
	{
		return java.util.ResourceBundle.getBundle(DB_CONFIG).getString(
				"uploadFilePath");
	}

	/**
	 * ���캯��
	 */
	public TxnDownloadStatus()
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
	 * ��ѯ���������б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60300001(DownloadStatusContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoDownloadStatusSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoDownloadStatus result[] = context.getDownloadStatuss(
		// outputNode );
	}

	/**
	 * �޸�����������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60300002(DownloadStatusContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String status = context.getRecord("record").getValue("status");
		String check_idea = context.getRecord("record").getValue("check_idea");
		table
				.executeFunction(SELECT_FUNCTION, context, "primary-key",
						"record");
		String check_user = context.getRecord("oper-data")
				.getValue("oper-name");
		String check_date = com.gwssi.common.util.DateUtil.getYMDTime();
		context.getRecord("record").setValue("status", status);
		context.getRecord("record").setValue("check_user", check_user);
		context.getRecord("record").setValue("check_date", check_date);
		context.getRecord("record").setValue("check_idea", check_idea);
		table.executeFunction("updateDownloadStatus", context, "record",
				"record");

		TxnContext txnContext = new TxnContext();
		DataBus getWorkStatus = txnContext.getRecord("ws-query");
		getWorkStatus.setValue("scour_table_name", "DOWNLOAD_STATUS");
		getWorkStatus.setValue("scour_table_key_col", context.getRecord(
				"primary-key").getValue("download_status_id"));
		txnContext.getRecord("oper-data").setValue("role-list",
				context.getRecord("oper-data").getValue("role-list"));
		// System.out.println("txnContext:" + txnContext);
		this.callService("com.gwssi.dw.aic.bj.homepage.txn.TxnWorkStatus",
				"txn54000001", txnContext);
		// System.out.println(txnContext);
		txnContext.getRecord("ws-record").setValue("isvalid", "0");
		this.callService("com.gwssi.dw.aic.bj.homepage.txn.TxnWorkStatus",
				"txn54000002", txnContext);
		// System.out.println(txnContext);

		TxnContext logContext = new TxnContext();
		DataBus log_db = logContext.getRecord("record");
		log_db.setValue("opername", "��������");
		log_db.setValue("download_status_id", context.getRecord("record")
				.getValue("download_status_id"));
		log_db.setValue("download_cond", "");
		log_db.setValue("download_count", context.getRecord("record").getValue(
				"apply_count"));
		logContext.addRecord("oper-data", context.getRecord("oper-data"));
		this.callService("com.gwssi.dw.aic.download.txn.TxnDownloadLog",
				"txn60340003", logContext);

	}

	/**
	 * �ϴ��ļ�
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn60300010(DownloadStatusContext context) throws TxnException
	{
		try {
			VoFileInfo[] files = context.getConttrolData().getUploadFileList();
			if (files == null || files.length == 0) {
				return;
			}
			String rootPath = this.getUploadPath();
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			String datestr = df.format(new Date());
			String prePath = rootPath + "/" + datestr;
			File f = new File(rootPath + "/" + datestr);
			if (!f.exists()) {
				f.mkdirs();
			}
			String fileName = "";
			String filePath = "";
			for (int i = 0; i < files.length; i++) {
				VoFileInfo voFileInfo = files[i];
				if (voFileInfo == null
						|| voFileInfo.getOriFileName().trim().length() == 0) {
					continue;
				}
				// ����ϴ���ԭʼ�ļ���������"11��1��ץ��ͨ��.doc"
				String OldFileName = voFileInfo.getOriFileName().trim();
				fileName = OldFileName;
				String ccFileNameWithWxt = new Date().getTime() + ".txt";
				filePath = prePath + "/" + ccFileNameWithWxt;
				try {
					// ����ϴ��ļ��ڷ�������ʱĿ¼���ļ���
					String uploadFileName = voFileInfo
							.getValue(VoCtrl.ITEM_SVR_FILENAME);
					System.out.println("dwn-�ϴ��ļ�����"+uploadFileName);
					System.out.println("dwn-prePath��"+prePath);
					System.out.println("dwn-ccFileNameWithWxt��"+ccFileNameWithWxt);
					InputStream in = new FileInputStream(uploadFileName);
					OutputStream out = new FileOutputStream(new File(prePath + "/"
							+ ccFileNameWithWxt));
					// ���ļ��������ָ���ϴ�Ŀ¼
					UploadUtil.exchangeStream(in, out);
				} catch (Exception ex) {
					throw new TxnErrorException("999999", "�����ļ��ϴ�ʧ�ܣ��������ϴ�");
				}
			}
			DataBus db = context.getRecord("record");
			db.setValue("fj_file_path", filePath);
			db.setValue("fj_file_name", fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��������������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws UnsupportedEncodingException
	 */
	public void txn60300003(DownloadStatusContext context) throws TxnException,
			UnsupportedEncodingException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String download_status_id = com.gwssi.common.util.UuidGenerator
				.getUUID();
		DataBus dataBus = context.getRecord(inputNode);
		DataBus infoDataBus = context.getRecord("oper-data");
		String queryCondition = dataBus.getValue("query_condition");
		if (queryCondition != null && !queryCondition.equals("")) {
			queryCondition = java.net.URLDecoder
					.decode(queryCondition, "UTF-8");
			dataBus.setValue("query_condition", queryCondition);
		}
		String sql = dataBus.getValue("sql");
		if (sql != null && !sql.equals("")) {
			sql = java.net.URLDecoder.decode(sql, "UTF-8");
			sql = sql.replaceAll("\\(%2B\\)", "(+)");
			dataBus.setValue("sql", sql);
		}
		String sid = String.valueOf(System.currentTimeMillis());
		String display_name = dataBus.getValue("display_name");
		if (display_name != null && !display_name.equals("")) {
			display_name = java.net.URLDecoder.decode(java.net.URLDecoder
					.decode(display_name, "UTF-8"), "UTF-8");
			dataBus.setValue("display_name", display_name);
		} else {
			dataBus.setValue("display_name", sid);
		}
		dataBus.setValue("filepath", sid);
		dataBus.setValue("download_status_id", download_status_id);

		String apply_count = dataBus.getValue("apply_count");
		String apply_name = dataBus.getValue("apply_name");
		apply_name = java.net.URLDecoder.decode(apply_name, "UTF-8");
		dataBus.setValue("apply_name", apply_name);
		String apply_reason = dataBus.getValue("apply_reason");
		apply_reason = java.net.URLDecoder.decode(apply_reason, "UTF-8");
		dataBus.setValue("apply_reason", apply_reason);
		dataBus.setValue("apply_user", infoDataBus.getValue("userID"));
		dataBus.setValue("apply_date", DateUtil.getYMDHMSTime());
		dataBus.setValue("check_user", "");
		dataBus.setValue("check_date", "");
		dataBus.setValue("check_idea", "");
		dataBus.setValue("has_gener", "0");

		String columnscnarray = dataBus.getValue("columnscnarray");
		// System.out.println("columnscnarray:" + columnscnarray);
		columnscnarray = java.net.URLDecoder.decode(columnscnarray, "UTF-8");
		dataBus.setValue("columnscnarray", columnscnarray);
		String columnsenarray = dataBus.getValue("columnsenarray");
		columnsenarray = java.net.URLDecoder.decode(columnsenarray, "UTF-8");
		dataBus.setValue("columnsenarray", columnsenarray);

		try {
			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String status = dataBus.getValue("status");
		String is_mutil_download = dataBus.getValue("is_mutil_download");
		if (is_mutil_download != null && is_mutil_download.equals("1")) {
			Recordset rs = context.getRecordset("param");
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				String param_value = db.getValue("param_value");
				param_value = java.net.URLDecoder.decode(param_value, "UTF-8");
				db.setValue("param_value", param_value);
				String param_text = db.getValue("param_text");
				param_text = java.net.URLDecoder.decode(param_text, "UTF-8");
				String download_param_id = com.gwssi.common.util.UuidGenerator
						.getUUID();
				db.setValue("download_status_id", download_status_id);
				db.setValue("download_param_id", download_param_id);
				db.setValue("param_text", param_text);
			}
			this.callService("com.gwssi.dw.aic.download.txn.TxnDownloadParam",
					"txn60400003", context);
		}

		 TxnContext logContext = new TxnContext();
		 DataBus log_db = logContext.getRecord("record");
		 log_db.setValue("opername", "��������");
		 log_db.setValue("download_status_id", download_status_id);
		 log_db.setValue("download_cond", "");
		 log_db.setValue("download_count",
		 context.getRecord("record").getValue("apply_count"));
		 logContext.addRecord("oper-data", context.getRecord("oper-data"));
		 this.callService("com.gwssi.dw.aic.download.txn.TxnDownloadLog",
		 "txn60340003", logContext);

		if (status != null && status.equals("4")) {
			TxnContext txnContext = new TxnContext();
			DataBus insertWorkStatus = txnContext.getRecord("record");
			DataBus operData = context.getRecord("oper-data");
			insertWorkStatus.setValue("work_status_id",
					com.gwssi.common.util.UuidGenerator.getUUID());
			insertWorkStatus.setValue("work_status_name", "��������");
			insertWorkStatus.setValue("work_status_content", "���� "
					+ operData.getValue("oper-name") + " ��������������");
			insertWorkStatus.setValue("initiator_id", operData
					.getValue("userID"));
			insertWorkStatus.setValue("initiator_name", operData
					.getValue("oper-name"));
			insertWorkStatus.setValue("initiator_agen_id", operData
					.getValue("org-code"));
			insertWorkStatus.setValue("initiator_agen_name", operData
					.getValue("org-name"));
			insertWorkStatus.setValue("initiator_date",
					com.gwssi.common.util.DateUtil.getYMDTime());
			insertWorkStatus.setValue("initiator_time",
					com.gwssi.common.util.DateUtil.getHHmmssTime());
			insertWorkStatus.setValue("link_url",
					"txn60300004.do?primary-key:download_status_id="
							+ download_status_id);
			insertWorkStatus.setValue("txncode", "60330000");
			insertWorkStatus.setValue("isvalid", "1");
			insertWorkStatus.setValue("scour_table_name", "DOWNLOAD_STATUS");
			insertWorkStatus
					.setValue("scour_table_key_col", download_status_id);
			insertWorkStatus.setValue("apply_count", apply_count);
			// bak_col_1�ֶμ�¼�Ƿ����ظ�������Ϣ
			insertWorkStatus.setValue("bak_col_1", is_mutil_download);
			insertWorkStatus.setValue("bak_col_2", "");
			insertWorkStatus.setValue("bak_col_3", "");
			insertWorkStatus.setValue("bak_col_4", "");
			insertWorkStatus.setValue("bak_col_5", "");
			this.callService("com.gwssi.dw.aic.bj.homepage.txn.TxnWorkStatus",
					"txn54000003", txnContext);
		}
	}

	/**
	 * ��ѯ�������������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60300004(DownloadStatusContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoDownloadStatusPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoDownloadStatus result = context.getDownloadStatus(
		// outputNode );
	}

	/**
	 * ɾ������������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn60300005(DownloadStatusContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoDownloadStatusPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ����sql����Ƿ���ȷ
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn60300006(DownloadStatusContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoDownloadStatusPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction("testSql", context, inputNode, outputNode);
	}

	/**
	 * ���������б�
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn60300007(DownloadStatusContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		
		// �������������Լ�
		context.getRecord(inputNode).setValue("apply_user",
				context.getRecord("oper-data").getValue("userID"));
		// String sortColumn = "download_status.apply_date desc,
		// download_status.status";
		// Attribute.setSortColumn(context, outputNode, sortColumn);
		table.executeFunction("downloadStatusApply", context, inputNode,
				outputNode);
	}

	/**
	 * ���������б�
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn60300008(DownloadStatusContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		this.callService("com.gwssi.dw.aic.download.txn.TxnDownloadCheckPurv",
				"txn60600006", context);
		table.executeFunction("downloadStatusCheck", context, inputNode,
				outputNode);
	}

	// �����������벢��ת����ҳ��
	public void txn60300009(DownloadStatusContext context) throws TxnException,
			UnsupportedEncodingException
	{
		try {
			this.callService("60300003", context);
			String download_status_id = context.getRecord("record").getValue(
					"download_status_id");
			context.remove(context.getRecord("record"));
			DataBus db = new DataBus();
			db.setValue("download_status_id", download_status_id);
			context.addRecord("select-key", db);
			this.callService("60400006", context);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		DownloadStatusContext appContext = new DownloadStatusContext(context);
		invoke(method, appContext);
	}

}
