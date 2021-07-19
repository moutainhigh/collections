package com.gwssi.dw.runmgr.services.txn;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.services.SelfServiceDefine;
import com.gwssi.dw.runmgr.services.common.ColumnBean;
import com.gwssi.dw.runmgr.services.common.Constants;
import com.gwssi.dw.runmgr.services.impl.TestShareService;
import com.gwssi.dw.runmgr.services.vo.SysSvrServiceContext;
import com.trs.client.TRSConnection;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;

public class TxnSysSvrService extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods				= getAllMethod(
																TxnSysSvrService.class,
																SysSvrServiceContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME				= "sys_svr_service";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION			= "select sys_svr_service list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION			= "select one sys_svr_service";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION			= "update one sys_svr_service";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION			= "insert one sys_svr_service";

	// ɾ����¼
	private static final String	DELETE_FUNCTION			= "delete one sys_svr_service";

	// �����Զ������
	private static final String	INSERT_SELF_FUNCTION	= "insert self sys_svr_service";

	// �޸ļ�¼
	private static final String	UPDATE_SELF_FUNCTION	= "update one sys_svr_self_service";

	// ��ѯ��¼
	private static final String	SELECT_CODE_FUNCTION	= "select one sys_svr_service_code";

	// ��ѯ��¼
	private static final String	SELECT_NAME_FUNCTION	= "select one sys_svr_service_name";

	private static final String	SELECT_SERVICE_FUNCTION	= "select one sys_svr_service_id";

	private static final String	SELECT_MAX_SVR_CODE		= "select max sys_svr_service_svr_code";

	/**
	 * ���캯��
	 */
	public TxnSysSvrService()
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
	 * ��ѯ��������б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202001(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryList", context, inputNode, outputNode);

		// ��ѯȫ����¼
		SysSvrServiceContext context_temp = new SysSvrServiceContext();
		Attribute.setPageRow(context_temp, "record-all", -1);
		table.executeFunction("queryList", context_temp, inputNode,
				"record-all");
		Recordset rs_temp = context_temp.getRecordset("record-all");
		Recordset rs = new Recordset();
		while (rs_temp.hasNext()) {
			DataBus db_temp = (DataBus) rs_temp.next();
			DataBus db = new DataBus();
			db.setValue("sys_svr_service_id",
					db_temp.getValue("sys_svr_service_id"));
			db.setValue("svr_name", db_temp.getValue("svr_name"));
			rs.add(db);
		}
		context.addRecord("record-all", rs);
		// System.out.println("----111----" + context);
	}

	/**
	 * �޸Ĺ��������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202002(SysSvrServiceContext context) throws TxnException
	{
		// System.out.println("�����޸ķ��� txn50202002 \n"+context);
		// ����ҳ�����б�������ݽ��н���
		try {
			context.getRecord(inputNode).setValue(
					"svr_name",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue("svr_name"),
							"UTF-8"));
			context.getRecord(inputNode).setValue(
					"svr_code",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue("svr_code"),
							"UTF-8"));
			context.getRecord(inputNode).setValue(
					"svr_desc",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue("svr_desc"),
							"UTF-8"));
			context.getRecord(inputNode).setValue(
					"svr_type",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue("svr_type"),
							"UTF-8"));
			context.getRecord(inputNode).setValue(
					"query_sql",
					formatSQL(URLDecoder.decode(context.getRecord(inputNode)
							.getValue("query_sql"), "UTF-8")));
			context.getRecord(inputNode).setValue(
					"shared_columns_en_array",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue(
									"shared_columns_en_array"), "UTF-8"));
			context.getRecord(inputNode).setValue(
					"shared_columns_cn_array",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue(
									"shared_columns_cn_array"), "UTF-8"));
			context.getRecord(inputNode).setValue(
					"params",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue("params"),
							"UTF-8"));
			context.getRecord(inputNode).setValue(
					"sysParams",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue("sysParams"),
							"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		String sys_svr_service_id = context.getRecord("record").getValue(
				"sys_svr_service_id");

		// ɾ���߼���ѯID�����б�������������
		context.getRecord("primary-key").setValue("sys_svr_service_id",
				sys_svr_service_id);
		callService("50205005", context);
		// �޸ļ�¼������ VoSysSvrService sys_svr_service = context.getSysSvrService(
		// inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);

		JSONArray connJSONArray = JSONArray.fromObject(context.getRecord(
				inputNode).getValue("params"));

		// ���޸ķ���ʱ��ϵͳ���������޸�
		JSONArray sysParamJSONArray = JSONArray.fromObject(context.getRecord(
				inputNode).getValue("sysParams"));

		setBizLog("�޸Ĺ������", context,
				context.getRecord("record").getValue("svr_name"));
		context.remove(inputNode);

		if (connJSONArray != null) {
			for (Iterator it = connJSONArray.iterator(); it.hasNext();) {
				JSONObject jObj = (JSONObject) it.next();

				String uuid = UuidGenerator.getUUID();
				DataBus db = new DataBus();
				db.setValue("sys_svr_service_param_id", uuid);
				db.setValue("sys_svr_service_id", sys_svr_service_id);
				db.setValue("operator1", jObj.getString("condition"));
				db.setValue("left_paren", jObj.getString("preParen"));
				db.setValue("left_table_no", jObj.getString("tableOneId"));
				db.setValue("left_table_name", jObj.getString("tableOneName"));
				db.setValue("left_table_name_cn",
						jObj.getString("tableOneNameCn"));
				db.setValue("left_column_no", jObj.getString("columnOneId"));
				db.setValue("left_column_name", jObj.getString("columnOneName"));
				db.setValue("left_column_name_cn",
						jObj.getString("columnOneNameCn"));
				db.setValue("operator2", jObj.getString("relation"));
				db.setValue("right_table_no", jObj.getString("tableTwoId"));
				db.setValue("right_table_name", jObj.getString("tableTwoName"));
				db.setValue("right_table_name_cn",
						jObj.getString("tableTwoNameCn"));
				db.setValue("right_column_no", jObj.getString("columnTwoId"));
				db.setValue("right_column_name",
						jObj.getString("columnTwoName"));
				db.setValue("right_column_name_cn",
						jObj.getString("columnTwoNameCn"));
				db.setValue("right_paren", jObj.getString("postParen"));
				table.executeSelect(
						"SELECT MAX(param_order) as param_order FROM sys_svr_service_param",
						context, "max-order");
				String max = context.getRecord("max-order").getValue(
						"param_order");
				if (max.trim().equals(""))
					max = "0";
				db.setValue("param_order", "" + (Integer.parseInt(max) + 1));
				db.setValue("param_text", jObj.getString("paramText"));
				context.addRecord("record", db);
				callService("50205003", context);
				context.remove("record");
			}
		}

		// ���޸ķ���ʱ��ɾ���÷���ԭ�е�ϵͳ���������������ڵ�ϵͳ���� jufeng 2012-07-04
		// �޸ķ����ϵͳ�����Ȳ�ѯ�Ƿ�ԭ����ϵͳ����
		// System.out.println("���޸ķ����ǽ����޸�ϵͳ�������֡�1�� \n" +context);
		context.getRecord("select-key").setValue("sys_svr_config_id",
				sys_svr_service_id);
		callService("50206001", context);
		Recordset sysParamsRs = context.getRecordset("config-param");
		// System.out.println("���޸ķ����ǽ����޸�ϵͳ�������֡�2�� \n" +context);
		if (sysParamsRs != null && sysParamsRs.size() > 0) {
			// ԭ����ϵͳ������ִ��ɾ������
			context.remove("config-param");
			callService("50206005", context);
			context.remove("record");
		}
		// �޸ķ�����ɾ��ԭ��ϵͳ���������������ϵͳ���� jufeng 2012-07-04
		if (sysParamJSONArray != null) {
			for (Iterator it = sysParamJSONArray.iterator(); it.hasNext();) {
				JSONObject jObj = (JSONObject) it.next();
				String uuid = UuidGenerator.getUUID();
				DataBus db = new DataBus();
				db.setValue("sys_svr_config_param_id", uuid);
				db.setValue("sys_svr_config_id", sys_svr_service_id);
				db.setValue("operator1", jObj.getString("condition"));
				db.setValue("left_paren", jObj.getString("preParen"));
				db.setValue("left_table_no", jObj.getString("tableOneId"));
				db.setValue("left_table_name", jObj.getString("tableOneName"));
				db.setValue("left_table_name_cn",
						jObj.getString("tableOneNameCn"));
				db.setValue("left_column_no", jObj.getString("columnOneId"));
				db.setValue("left_column_name", jObj.getString("columnOneName"));
				db.setValue("left_column_name_cn",
						jObj.getString("columnOneNameCn"));
				db.setValue("operator2", jObj.getString("relation"));
				db.setValue("param_value",
						formatSQL(jObj.getString("paramValue")));
				db.setValue("right_paren", jObj.getString("postParen"));
				db.setValue("param_text", jObj.getString("paramText"));
				table.executeSelect(
						"SELECT MAX(param_order) as param_order FROM sys_svr_config_param",
						context, "max-order");
				String max = context.getRecord("max-order").getValue(
						"param_order");
				if (max.trim().equals(""))
					max = "0";
				db.setValue("param_order", "" + (Integer.parseInt(max) + 1));
				db.setValue("param_type", "0");
				context.addRecord("record", db);
				callService("50206003", context);
				context.remove("record");
			}
		}
	}

	/**
	 * ���ӹ��������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202003(SysSvrServiceContext context) throws TxnException
	{
		// ����ҳ�����б�������ݽ��н���
		try {
			context.getRecord(inputNode).setValue(
					"svr_name",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue("svr_name"),
							"UTF-8"));
			context.getRecord(inputNode).setValue(
					"svr_code",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue("svr_code"),
							"UTF-8"));
			context.getRecord(inputNode).setValue(
					"svr_desc",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue("svr_desc"),
							"UTF-8"));
			context.getRecord(inputNode).setValue(
					"svr_type",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue("svr_type"),
							"UTF-8"));
			context.getRecord(inputNode).setValue(
					"query_sql",
					formatSQL(URLDecoder.decode(context.getRecord(inputNode)
							.getValue("query_sql"), "UTF-8")));
			context.getRecord(inputNode).setValue(
					"shared_columns_en_array",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue(
									"shared_columns_en_array"), "UTF-8"));
			context.getRecord(inputNode).setValue(
					"shared_columns_cn_array",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue(
									"shared_columns_cn_array"), "UTF-8"));
			context.getRecord(inputNode).setValue(
					"params",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue("params"),
							"UTF-8"));
			context.getRecord(inputNode).setValue(
					"sysParams",
					URLDecoder.decode(
							context.getRecord(inputNode).getValue("sysParams"),
							"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		context.getRecord("record").setValue(
				"create_date",
				new SimpleDateFormat(Constants.DB_DATE_FORMAT)
						.format(new Date()));
		context.getRecord("record").setValue("create_by",
				context.getOperData().getValue("oper-name"));
		context.getRecord("record").setValue("sys_svr_service_id",
				UuidGenerator.getUUID());

		table.executeSelect(
				"SELECT MAX(svr_order) as svr_order FROM sys_svr_service",
				context, "max-order");
		String max = context.getRecord("max-order").getValue("svr_order");
		if (max.trim().equals(""))
			max = "0";
		int next = Integer.parseInt(max) + 1;
		context.getRecord("record").setValue("svr_order", "" + next);
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
		// ɾ���ϴεĲ�ѯ����
		context.remove("select-key");
		String svrId = context.getRecord(inputNode).getValue(
				"sys_svr_service_id");
		JSONArray connJSONArray = JSONArray.fromObject(context.getRecord(
				inputNode).getValue("params"));

		// ��������ʱ�����ϵͳ���� jufeng 2012-06-28
		JSONArray sysParamJSONArray = JSONArray.fromObject(context.getRecord(
				inputNode).getValue("sysParams"));

		// System.out.println("���ӷ���ʱ����Ҫ���ӵ�ϵͳ����
		// \n"+sysParamJSONArray.toString());
		setBizLog("�����������", context,
				context.getRecord("record").getValue("svr_name"));
		// System.out.println("------------------------�������� ��������(1) \n" +
		// context);
		context.remove(inputNode);
		// System.out.println("------------------------�������� ��������(2) \n" +
		// context);
		if (connJSONArray != null) {
			for (Iterator it = connJSONArray.iterator(); it.hasNext();) {
				JSONObject jObj = (JSONObject) it.next();

				String uuid = UuidGenerator.getUUID();
				DataBus db = new DataBus();
				db.setValue("sys_svr_service_param_id", uuid);
				db.setValue("sys_svr_service_id", svrId);
				db.setValue("operator1", jObj.getString("condition"));
				db.setValue("left_paren", jObj.getString("preParen"));
				db.setValue("left_table_no", jObj.getString("tableOneId"));
				db.setValue("left_table_name", jObj.getString("tableOneName"));
				db.setValue("left_table_name_cn",
						jObj.getString("tableOneNameCn"));
				db.setValue("left_column_no", jObj.getString("columnOneId"));
				db.setValue("left_column_name", jObj.getString("columnOneName"));
				db.setValue("left_column_name_cn",
						jObj.getString("columnOneNameCn"));
				db.setValue("operator2", jObj.getString("relation"));
				db.setValue("right_table_no", jObj.getString("tableTwoId"));
				db.setValue("right_table_name", jObj.getString("tableTwoName"));
				db.setValue("right_table_name_cn",
						jObj.getString("tableTwoNameCn"));
				db.setValue("right_column_no", jObj.getString("columnTwoId"));
				db.setValue("right_column_name",
						jObj.getString("columnTwoName"));
				db.setValue("right_column_name_cn",
						jObj.getString("columnTwoNameCn"));
				db.setValue("right_paren", jObj.getString("postParen"));
				table.executeSelect(
						"SELECT MAX(param_order) as param_order FROM sys_svr_service_param",
						context, "max-order");
				max = context.getRecord("max-order").getValue("param_order");
				if (max.trim().equals(""))
					max = "0";
				db.setValue("param_order", "" + (Integer.parseInt(max) + 1));
				db.setValue("param_text", jObj.getString("paramText"));
				context.addRecord("record", db);
				callService("50205003", context);
				context.remove("record");
			}
		}
		// ��������ʱ�����ϵͳ���� jufeng 2012-06-28
		if (sysParamJSONArray != null) {
			for (Iterator it = sysParamJSONArray.iterator(); it.hasNext();) {
				JSONObject jObj = (JSONObject) it.next();
				String uuid = UuidGenerator.getUUID();
				DataBus db = new DataBus();
				db.setValue("sys_svr_config_param_id", uuid);
				db.setValue("sys_svr_config_id", svrId);
				db.setValue("operator1", jObj.getString("condition"));
				db.setValue("left_paren", jObj.getString("preParen"));
				db.setValue("left_table_no", jObj.getString("tableOneId"));
				db.setValue("left_table_name", jObj.getString("tableOneName"));
				db.setValue("left_table_name_cn",
						jObj.getString("tableOneNameCn"));
				db.setValue("left_column_no", jObj.getString("columnOneId"));
				db.setValue("left_column_name", jObj.getString("columnOneName"));
				db.setValue("left_column_name_cn",
						jObj.getString("columnOneNameCn"));
				db.setValue("operator2", jObj.getString("relation"));
				db.setValue("param_value",
						formatSQL(jObj.getString("paramValue")));
				db.setValue("right_paren", jObj.getString("postParen"));
				db.setValue("param_text", jObj.getString("paramText"));
				table.executeSelect(
						"SELECT MAX(param_order) as param_order FROM sys_svr_config_param",
						context, "max-order");
				max = context.getRecord("max-order").getValue("param_order");
				if (max.trim().equals(""))
					max = "0";
				db.setValue("param_order", "" + (Integer.parseInt(max) + 1));
				db.setValue("param_type", "0");
				context.addRecord("record", db);
				callService("50206003", context);
				context.remove("record");
			}
		}
		// System.out.println("------------------------�������� ����������� \n" +
		// context);
	}

	/**
	 * ��ѯ������������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202004(SysSvrServiceContext context) throws TxnException
	{
		// ���ݷ���ID��ѯ�����������б�
		callService("50205001", context);
		Recordset rs = context.getRecordset("record");

		// ѭ�����еĲ�������֯�����ַ���
		while (rs.hasNext()) {
			DataBus db = (DataBus) rs.next();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("condition", db.getValue("operator1"));
			jsonObj.put("preParen", db.getValue("left_paren"));
			jsonObj.put("tableOneId", db.getValue("left_table_no"));
			jsonObj.put("tableOneName", db.getValue("left_table_name"));
			jsonObj.put("tableOneNameCn", db.getValue("left_table_name_cn"));
			jsonObj.put("columnOneId", db.getValue("left_column_no"));
			jsonObj.put("columnOneName", db.getValue("left_column_name"));
			jsonObj.put("columnOneNameCn", db.getValue("left_column_name_cn"));
			jsonObj.put("relation", db.getValue("operator2"));
			jsonObj.put("tableTwoId", db.getValue("right_table_no"));
			jsonObj.put("tableTwoName", db.getValue("right_table_name"));
			jsonObj.put("tableTwoNameCn", db.getValue("right_table_name_cn"));
			jsonObj.put("columnTwoId", db.getValue("right_column_no"));
			jsonObj.put("columnTwoName", db.getValue("right_column_name"));
			jsonObj.put("columnTwoNameCn", db.getValue("right_column_name_cn"));
			jsonObj.put("postParen", db.getValue("right_paren"));
			jsonObj.put("paramText", db.getValue("param_text"));

			db.setValue("params", jsonObj.toString());
		}

		// jufeng add 20120703 �޸ķ���ʱ��ѯϵͳ����
		TxnContext context2 = new TxnContext();
		DataBus db2 = new DataBus();
		db2.setValue("sys_svr_config_id", context.getRecord("select-key")
				.getValue("sys_svr_service_id"));
		context2.addRecord("select-key", db2);
		callService("50206001", context2);

		Recordset rs2 = context2.getRecordset("config-param");
		Recordset rs3 = new Recordset();
		while (rs2.hasNext()) {
			DataBus db = (DataBus) rs2.next();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("condition", db.getValue("operator1"));
			jsonObj.put("preParen", db.getValue("left_paren"));
			jsonObj.put("tableOneId", db.getValue("left_table_no"));
			jsonObj.put("tableOneName", db.getValue("left_table_name"));
			jsonObj.put("tableOneNameCn", db.getValue("left_table_name_cn"));
			jsonObj.put("columnOneId", db.getValue("left_column_no"));
			jsonObj.put("columnOneName", db.getValue("left_column_name"));
			jsonObj.put("columnOneNameCn", db.getValue("left_column_name_cn"));
			jsonObj.put("relation", db.getValue("operator2"));
			jsonObj.put("paramValue", db.getValue("param_value"));
			jsonObj.put("postParen", db.getValue("right_paren"));
			jsonObj.put("paramType", db.getValue("param_type"));
			jsonObj.put("paramText", db.getValue("param_text"));
			DataBus db4 = new DataBus();
			db4.setValue("sysParams", jsonObj.toString());
			rs3.add(db4);
		}

		if (rs3 != null && rs3.size() > 0) {
			context.addRecord("config-param", rs3);
		}
		// System.out.println("!!!��ѯ������������޸� txn50202004 \n" + context);

		// ���ݷ���ID��ѯ������Ϣ
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ����ѡ������ݱ�
		String[] tblNos = context.getRecord(outputNode).getValue("table_no")
				.split(",");
		for (int i = 0; i < tblNos.length; i++) {
			context.getRecord("select-key").setValue("table_no", tblNos[i]);
			table.executeFunction("queryTable", context, inputNode,
					"temp-table-info");
			DataBus db = new DataBus();
			db.setValue("table_no", context.getRecord("temp-table-info")
					.getValue("table_no"));
			db.setValue("table_name", context.getRecord("temp-table-info")
					.getValue("table_name"));
			db.setValue("table_name_cn", context.getRecord("temp-table-info")
					.getValue("table_name_cn"));
			db.setValue("sys_id", context.getRecord("temp-table-info")
					.getValue("sys_id"));
			db.setValue("sys_name", context.getRecord("temp-table-info")
					.getValue("sys_name"));
			db.setValue("sys_no", context.getRecord("temp-table-info")
					.getValue("sys_no"));
			context.addRecord("tbl-info", db);
		}
		context.remove("temp-table-info");

		// ��ѯ���й�����ֶ�
		String[] colNos = context.getRecord(outputNode).getValue("column_no")
				.split(",");
		for (int i = 0; i < colNos.length; i++) {
			context.getRecord("select-key").setValue("column_no", colNos[i]);
			table.executeFunction("queryColumn", context, inputNode,
					"temp-column-info");
			DataBus db = new DataBus();
			db.setValue("column_no", context.getRecord("temp-column-info")
					.getValue("column_no"));
			db.setValue("column_name", context.getRecord("temp-column-info")
					.getValue("column_name"));
			db.setValue("column_name_cn", context.getRecord("temp-column-info")
					.getValue("column_name_cn"));
			db.setValue("column_byname", context.getRecord("temp-column-info")
					.getValue("column_byname"));
			db.setValue("table_no", context.getRecord("temp-column-info")
					.getValue("table_no"));
			db.setValue("table_name", context.getRecord("temp-column-info")
					.getValue("table_name"));
			db.setValue("table_name_cn", context.getRecord("temp-column-info")
					.getValue("table_name_cn"));
			context.addRecord("col-info", db);
		}
		context.remove("temp-column-info");
		// System.out.println("txn50202004���ý��� \n"+context);
	}

	/**
	 * ɾ�����������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202005(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// String sys_svr_service_id =
		// context.getRecord("record").getValue("sys_svr_service_id");
		// TxnContext txnContext = new TxnContext();
		// txnContext.getRecord("record").setValue("sys_svr_service_id",
		// sys_svr_service_id);
		// int i = table.executeFunction("haveAssigned", txnContext,
		// "select-key", "record");
		// if(i > 0){
		// throw new TxnDataException("999999","�÷����ѱ�ʹ�ã�����ɾ����");
		// }

		callService("50205005", context);

		// ɾ����¼�������б� VoSysSvrServicePrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction("deleteService", context, inputNode, outputNode);

		setBizLog("ɾ���������", context,
				context.getRecord("record").getValue("svr_name"));
	}

	/**
	 * ��ѯ����������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202006(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getFirstLevelNode", context, inputNode,
				outputNode);
		setExpand(context);
	}

	/**
	 * ��ȡ�ڶ��㼰�Ժ�ڵ�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	// public void txn50202007( SysSvrServiceContext context ) throws
	// TxnException
	// {
	// BaseTable table = TableFactory.getInstance().getTableObject( this,
	// TABLE_NAME );
	// Attribute.setPageRow( context, outputNode, -1 );
	// table.executeFunction( "getSecondLevelNode", context, inputNode,
	// outputNode );
	// setExpand(context);
	// }
	/**
	 * ������������ѯ�������ݱ�
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50202008(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getAllTables", context, inputNode, outputNode);
	}

	/**
	 * �������ݱ�����ѯ�����ֶ�
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50202009(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getAllColumns", context, inputNode, outputNode);
	}

	/**
	 * �����Զ�������ֶμ�����
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50202010(SysSvrServiceContext context) throws TxnException
	{
		// BaseTable table = TableFactory.getInstance().getTableObject( this,
		// TABLE_NAME );
		try {
			analyseClass(context);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TxnDataException("999999", "���������");
		}
		// table.executeFunction( "getAllColumns", context, inputNode,
		// outputNode );
	}

	/**
	 * �����Զ������
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50202011(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		DataBus bus = new DataBus();
		bus.setValue("svr_code",
				context.getRecord("record").getValue("svr_code"));
		context.addRecord("select-key", bus);
		int i = table.executeFunction(SELECT_CODE_FUNCTION, context,
				"select-key", "exist-service");
		if (i > 0) {
			throw new TxnDataException("999999", "�÷�������Ѵ��ڣ�");
		}

		context.remove("exist-service");
		context.remove("select-key");
		bus = new DataBus();
		bus.setValue("name", context.getRecord("record").getValue("name"));
		context.addRecord("select-key", bus);
		i = table.executeFunction(SELECT_NAME_FUNCTION, context, "select-key",
				"exist-service");
		if (i > 0) {
			throw new TxnDataException("999999", "�÷��������Ѵ��ڣ�");
		}

		context.getRecord("record").setValue(
				"create_date",
				new SimpleDateFormat(Constants.DB_DATE_FORMAT)
						.format(new Date()));
		String uId = context.getOperData().getValue("oper-name");
		context.getRecord("record").setValue("create_by", uId);
		context.getRecord("record").setValue("sys_svr_service_id",
				UuidGenerator.getUUID());

		table.executeFunction(INSERT_SELF_FUNCTION, context, inputNode,
				outputNode);
	}

	/**
	 * ��ѯ�Զ�����������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202012(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoSysSvrServicePrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoSysSvrService result = context.getSysSvrService(
		// outputNode );
		try {
			analyseClass(context);
		} catch (Exception e) {
			throw new TxnDataException("999999", "���������");
		}
	}

	/**
	 * �޸��Զ��������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202013(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		String sys_svr_service_id = context.getRecord("record").getValue(
				"sys_svr_service_id");
		String name = context.getRecord("record").getValue("name");

		DataBus bus = new DataBus();
		bus.setValue("sys_svr_service_id", sys_svr_service_id);
		context.addRecord("select-key", bus);

		int i = table.executeFunction("haveAssigned", context, "select-key",
				"config");
		if (i > 0) {
			throw new TxnDataException("999999", "�÷����ѱ�ʹ�ã���ֹ�޸ģ�");
		}
		context.remove("config");
		context.remove("select-key");

		bus = new DataBus();
		bus.setValue("svr_code",
				context.getRecord("record").getValue("svr_code"));
		context.addRecord("select-key", bus);
		i = table.executeFunction(SELECT_CODE_FUNCTION, context, "select-key",
				"exist-service");

		if (i > 0) {
			String existId = context.getRecord("exist-service").getValue(
					"sys_svr_service_id");
			if (!(sys_svr_service_id.trim().equalsIgnoreCase(existId.trim()))) {
				throw new TxnDataException("999999", "�÷�������Ѵ��ڣ�");
			}
		}

		context.remove("exist-service");
		context.remove("select-key");
		bus = new DataBus();
		bus.setValue("name", name);
		context.addRecord("select-key", bus);
		i = table.executeFunction(SELECT_NAME_FUNCTION, context, "select-key",
				"exist-service");

		if (i > 0) {
			String existId = context.getRecord("exist-service").getValue(
					"sys_svr_service_id");
			if (!(sys_svr_service_id.trim().equalsIgnoreCase(existId.trim()))) {
				try {
					analyseClass(context);
				} catch (Exception e) {
					throw new TxnDataException("999999", "���������");
				}
				throw new TxnDataException("999999", "�÷��������Ѵ��ڣ�");
			}
		}
		context.remove("exist-service");

		try {
			analyseClass(context);
		} catch (Exception e) {
			throw new TxnDataException("999999", "���������");
		}

		// �޸ļ�¼������ VoSysSvrService sys_svr_service = context.getSysSvrService(
		// inputNode );
		table.executeFunction(UPDATE_SELF_FUNCTION, context, inputNode,
				outputNode);
	}

	/**
	 * ��ѯ������ϸ��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202014(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_SERVICE_FUNCTION, context, inputNode,
				outputNode);
	}

	/**
	 * ��ѯ�������´���
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202015(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_MAX_SVR_CODE, context, inputNode,
				outputNode);
	}

	/**
	 * ��ѯ�����Ƿ��Ѿ�������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202016(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		int i = table.executeFunction("haveAssigned", context, inputNode,
				outputNode);
		if (i > 0) {
			throw new TxnDataException("999999", "�÷����ѱ�ʹ�ã���ֹ�޸ģ�");
		}
	}

	/**
	 * ��ѯ�����Ƿ��Ѿ�����
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202017(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction(SELECT_NAME_FUNCTION, context, inputNode,
				outputNode);
	}

	/**
	 * ��ѯ�������ø���Щ�û���
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50202018(SysSvrServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("queryUserList", context, inputNode, outputNode);
	}

	private String formatSQL(String sql)
	{
		String sb = sql.replaceAll("'", "''");

		return sb;
	}

	private void setExpand(SysSvrServiceContext context)
	{
		Recordset rs = null;
		try {
			rs = context.getRecordset("record");
		} catch (TxnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < rs.size(); i++) {
			DataBus dataBus = rs.get(i);
			dataBus.setValue("expand", "true");
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
		SysSvrServiceContext appContext = new SysSvrServiceContext(context);
		invoke(method, appContext);
	}

	/**
	 * �����Զ�������࣬��װcontext
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void analyseClass(SysSvrServiceContext context) throws Exception
	{

		String service_class = context.getRecord("record").getValue("table_no");
		Class clazz = Class.forName(Constants.SELF_SERVICE_PACKAGE
				+ service_class);
		SelfServiceDefine service = (TestShareService) clazz.newInstance();
		Method method = clazz.getDeclaredMethod("getVisitURL", null);
		String visitURL = (String) method.invoke(service, null);
		context.getRecord("record").setValue("visit_URL", visitURL);

		method = clazz.getDeclaredMethod("getSharedColumns", null);
		List list = (List) method.invoke(service, null);
		for (int i = 0; i < list.size(); i++) {
			ColumnBean column = (ColumnBean) list.get(i);
			DataBus db = new DataBus();
			db.setValue("column_name", column.getName());
			db.setValue("column_name_cn", column.getDesc());
			context.addRecord("shared-column", db);
		}

		method = clazz.getDeclaredMethod("getParamColumns", null);
		list = (List) method.invoke(service, null);
		for (int i = 0; i < list.size(); i++) {
			ColumnBean column = (ColumnBean) list.get(i);
			DataBus db = new DataBus();
			db.setValue("column_name", column.getName());
			db.setValue("column_name_cn", column.getDesc());
			context.addRecord("param-column", db);
		}

	}

	/**
	 * ��¼��־
	 * 
	 * @param type
	 * @param context
	 */
	private void setBizLog(String type, TxnContext context, String jgmc)
	{

		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME)
				.setValue(com.gwssi.common.util.Constants.VALUE_NAME,
						type + jgmc);
	}

	public static void main(String[] args)
	{
		TRSConnection conn = null;
		TRSResultSet rs = null;
		long begin = System.currentTimeMillis();
		try {
			conn = new TRSConnection();
			conn.connect("160.100.0.218", "8889", "system", "manager", "T10");
			// conn.SetExtendOption("SORTPRUNE", "1000");
			// ��Ч��������¼�������������򣬰�������ʽ����Ľ��ֻ��֤ǰһ���ּ�¼������ģ���ҪSERVER6.10.3300���ϡ�
			// conn.SetExtendOption("SORTVALID", "1000");
			rs = new TRSResultSet();
			rs.setConnection(conn);
			rs.executeSelect("interface_12315", "����=Ӱ��", false);
			System.out.println(rs.getRecordCount());
			// rs.sortResult("-��������", true);
			for (int i = 0; i < 10 && i < rs.getRecordCount(); i++) {
				rs.moveTo(0, i);
				// System.out.println("��" + i + "����¼");
				System.out.println(rs.getString("TYPE"));
			}
		} catch (TRSException e) {
			System.out.println("ErrorCode: " + e.getErrorCode());
			System.out.println("ErrorString: " + e.getErrorString());
		} finally {
			if (rs != null)
				rs.close();
			rs = null;
			if (conn != null)
				conn.close();
			conn = null;
		}
		System.out.println("��ʱ��" + (System.currentTimeMillis() - begin));
	}
}
