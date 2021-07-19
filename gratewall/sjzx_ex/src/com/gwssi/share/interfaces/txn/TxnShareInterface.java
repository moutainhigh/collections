package com.gwssi.share.interfaces.txn;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.database.DBPoolConnection;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.share.interfaces.vo.ShareInterfaceContext;

public class TxnShareInterface extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnShareInterface.class,
														ShareInterfaceContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "share_interface";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select share_interface list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one share_interface";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one share_interface";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one share_interface";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one share_interface";

	/**
	 * ���캯��
	 */
	public TxnShareInterface()
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
	 * ��ѯ��
	 * @param context
	 * @throws TxnException
	 */
	public void txn40100015(ShareInterfaceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		ShareInterfaceContext stateContext = new ShareInterfaceContext();
		// ����ӿ�״̬json����
		stateContext.getRecord(inputNode).setValue("codetype", "��Դ����_�鵵����״̬");
		stateContext.getRecord(inputNode).setValue("column", "interface_state");
		Attribute.setPageRow(stateContext, outputNode, -1);
		table.executeFunction("getInfoByitfState", stateContext, inputNode,
				outputNode);
		Recordset stateRs = stateContext.getRecordset("record");
		context.setValue("itfState", JsonDataUtil.getJsonByRecordSet(stateRs));
		//����ӿ�����json����
		ShareInterfaceContext interfaceContext = new ShareInterfaceContext();
		Attribute.setPageRow(interfaceContext, outputNode, -1);
		table.executeFunction("getInfoByService", interfaceContext, inputNode,
				outputNode);

		Recordset interfaceRs = interfaceContext.getRecordset("record");
		context.setValue("itfName",
				JsonDataUtil.getPYJsonByRecordSet(interfaceRs));
	}
	/**
	 * ��ѯ�����������ӿ������б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn401001(ShareInterfaceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		
		this.callService("40109001", context);
		
		// ��ѯ��¼������ VoShareServiceSelectKey selectKey = context.getSelectKey(
		// inputNode );
		/*String create_time = context.getRecord("select-key").getValue(
				"created_time");
		if (StringUtils.isNotBlank(create_time)) {
			String[] ctime = DateUtil.getDateRegionByDatePicker(create_time,
					true);
			context.getRecord("select-key").setValue("created_time_start",
					ctime[0]);
			context.getRecord("select-key").setValue("created_time_end",
					ctime[1]);
			context.getRecord("select-key").remove("created_time");
		}*/
		table.executeFunction("query_interface_markuplist", context, inputNode, outputNode);
	}

	/**
	 * �޸Ĺ����������ӿ�������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn401002(ShareInterfaceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		System.out.println("update----------\n" + context);
		String interface_id = context.getRecord("record").getValue(
				"interface_id");
		System.out.println("interface_id:" + interface_id);
		DataBus oper_data = context.getRecord("oper-data");
		context.getRecord("record").setValue("last_modify_id",
				oper_data.getValue("userID"));
		context.getRecord("record").setValue("last_modify_time",
				DateUtil.getYMDHMSTime());
		String interface_name = context.getRecord("record").getValue(
				"interface_name");
		context.getRecord("record").setValue("interface_name",
				interface_name);
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
		// ɾ��ԭ���Ĺ�����ϵ�Ͳ�ѯ����
		ShareInterfaceContext context_delete = new ShareInterfaceContext();
		context_delete.setProperty("key", interface_id);
		table.executeFunction("deleteTableCondition", context_delete,
				inputNode, outputNode);
		table.executeFunction("deleteTableParam", context_delete, inputNode,
				outputNode);
		// �������������
		this.saveTableCondition(context);
		// �������ѯ����
		this.saveTableParam(context);

	}

	/**
	 * ���ӹ����������ӿ�������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn401003(ShareInterfaceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
//		context.getRecord("record").setValue("sql", " ");
		context.getRecord("record").setValue("interface_state", ExConstant.SERVICE_STATE_Y);
		context.getRecord("record").setValue("is_markup", ExConstant.IS_MARKUP_Y);

		DataBus oper_data = context.getRecord("oper-data");
		context.getRecord("record").setValue("creator_id",
				oper_data.getValue("userID"));
		context.getRecord("record").setValue("created_time",
				DateUtil.getYMDHMSTime());
		context.getRecord("record").setValue("last_modify_id",
				oper_data.getValue("userID"));
		context.getRecord("record").setValue("last_modify_time",
				DateUtil.getYMDHMSTime());
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
		// String interface_id =
		// context.getRecord("record").getValue("interface_id");
		//System.out.println("----context----\n" + context);
		// �������������
		this.saveTableCondition(context);
		// �������ѯ����
		this.saveTableParam(context);
	}

	/**
	 * ������������� saveTableCondition(������һ�仰�����������������)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private void saveTableCondition(ShareInterfaceContext context)
			throws TxnException
	{
		String jsonString = context.getRecord("record").getValue("condition");
		if (StringUtils.isNotBlank(jsonString)) {
			// �õ�json��
			JSONArray json = JsonDataUtil.getJsonArray(jsonString, "data");
			String interface_id = context.getRecord("record").getValue(
					"interface_id");
			for (int i = 0; i < json.size(); i++) {
				JSONObject object = (JSONObject) json.get(i);
				JSONObject leftTable = object.getJSONObject("leftTable");
				JSONObject leftColumn = object.getJSONObject("leftColumn");
				JSONObject rightTable = object.getJSONObject("rightTable");
				JSONObject rightColumn = object.getJSONObject("rightColumn");
				DataBus db = new DataBus();
				db.setValue("interface_id", interface_id);
				db.setValue("FRIST_CONNECTOR", "");
				db.setValue("LEFT_PAREN", "");
				db.setValue("LEFT_TABLE_NO", leftTable.getString("id"));
				db.setValue("LEFT_TABLE_NAME_EN", leftTable
						.getString("name_en"));
				db.setValue("LEFT_TABLE_NAME_CN", leftTable
						.getString("name_cn"));
				db.setValue("LEFT_COLUMN_NO", leftColumn.getString("id"));
				db.setValue("LEFT_COLUMN_NAME_EN", leftColumn
						.getString("name_en"));
				db.setValue("LEFT_COLUMN_NAME_CN", leftColumn
						.getString("name_cn"));
				db
						.setValue("SECOND_CONNECTOR", object
								.getString("middleParen"));
				db.setValue("RIGHT_TABLE_NO", rightTable.getString("id"));
				db.setValue("RIGHT_TABLE_NAME_EN", rightTable
						.getString("name_en"));
				db.setValue("RIGHT_TABLE_NAME_CN", rightTable
						.getString("name_cn"));
				db.setValue("RIGHT_COLUMN_NO", rightColumn.getString("id"));
				db.setValue("RIGHT_COLUMN_NAME_EN", rightColumn
						.getString("name_en"));
				db.setValue("RIGHT_COLUMN_NAME_CN", rightColumn
						.getString("name_cn"));
				db.setValue("RIGHT_PAREN", "");
				BaseTable table = TableFactory.getInstance().getTableObject(
						this, TABLE_NAME);
				table.executeFunction("insertTableCondition", context, db,
						outputNode);
			}
		}
	}

	/**
	 * �������ѯ���� saveTableCondition(������һ�仰�����������������)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private void saveTableParam(ShareInterfaceContext context)
			throws TxnException
	{
		String jsonString = context.getRecord("record").getValue("param");
		if (StringUtils.isNotBlank(jsonString)) {
			// �õ�json��
			JSONArray json = JsonDataUtil.getJsonArray(jsonString, "data");
			String interface_id = context.getRecord("record").getValue(
					"interface_id");
			for (int i = 0; i < json.size(); i++) {
				JSONObject object = (JSONObject) json.get(i);
				JSONObject leftTable = object.getJSONObject("leftTable");
				JSONObject leftColumn = object.getJSONObject("leftColumn");
				JSONObject middleParen = object.getJSONObject("middleParen");
				DataBus db = new DataBus();
				db.setValue("interface_id", interface_id);
				db.setValue("FRIST_CONNECTOR", object.getString("cond"));
				db.setValue("LEFT_PAREN", object.getString("leftParen"));
				db.setValue("TABLE_NO", leftTable.getString("id"));
				db.setValue("TABLE_NAME_EN", leftTable.getString("name_en"));
				db.setValue("TABLE_NAME_CN", leftTable.getString("name_cn"));
				db.setValue("COLUMN_NO", leftColumn.getString("id"));
				db.setValue("COLUMN_NAME_EN", leftColumn.getString("name_en"));
				db.setValue("COLUMN_NAME_CN", leftColumn.getString("name_cn"));
				db.setValue("SECOND_CONNECTOR", middleParen.getString("value"));
				db.setValue("PARAM_TYPE", middleParen.getString("value"));
				db.setValue("PARAM_VALUE", object.getString("paramValue"));
				db.setValue("RIGHT_PAREN", object.getString("rightParen"));
				BaseTable table = TableFactory.getInstance().getTableObject(
						this, TABLE_NAME);
				table.executeFunction("insertTableParam", context, db,
						outputNode);
			}
		}
	}

	/**
	 * ��ѯ�����������ӿ����������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	@SuppressWarnings("unchecked")
	public void txn401004(ShareInterfaceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		
		String interface_id = context.getRecord("primary-key").getValue("interface_id");
		StringBuffer sql = new StringBuffer();
		
		sql.append("select t1.*,t1.created_time as cretime,t1.last_modify_time as modtime, yh1.yhxm as crename,yh2.yhxm as modname from share_interface t1,xt_zzjg_yh_new yh1,xt_zzjg_yh_new yh2");
		sql.append(" where  t1.creator_id = yh1.yhid_pk(+)  and t1.last_modify_id = yh2.yhid_pk(+)  and t1.interface_id = '");
		sql.append(interface_id);
		sql.append("'");
		table.executeRowset(sql.toString(), context, outputNode);
		
		// ��ѯ�ӿ���ѡ�����ϸ��Ϣ
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("querySelectTable", context, inputNode,
				"selectTable");
		Recordset rs = context.getRecordset("selectTable");
		List dataList = new ArrayList();
		for (int i = 0; i < rs.size(); i++) {
			DataBus db = rs.get(i);
			Map dataMap = new HashMap();
			dataMap.put("key", db.get("key"));
			dataMap.put("title", db.get("title"));
			dataMap.put("code", db.get("code"));
			dataList.add(dataMap);
		}
		context.setProperty("dataString", JsonDataUtil.toJSONString(dataList));
		if (rs.size() > 0) {
			ShareInterfaceContext context_topics = new ShareInterfaceContext();
			String topics_id = rs.get(0).getValue("topics_id");
			context.setProperty("selected_topics_id",topics_id );
			context_topics.setValue("key", topics_id);
			// ѡ����ѡ���еĵ�һ����������������б�
			this.callService("401008", context_topics);
			context.setProperty("dataTable", context_topics
					.getValue("dataString"));
			// ���ݵ�һ��ѡ����Ӧ������id ��ȡ����Ӧ�ķ������ID
			table.executeFunction("queryObjectByTopics", context_topics,
					inputNode, "shareObject");
			ShareInterfaceContext context_system = new ShareInterfaceContext();
			String service_targets_id = context_topics.getRecord("shareObject").getValue("service_targets_id");
			context.setProperty("selected_service_targets_id",service_targets_id );
			context_system.setValue("key", service_targets_id);
			this.callService("401007", context_system);
			context.setProperty("dataSystem", context_system
					.getValue("dataString"));
			// ��ѯ���ѯ����
			ShareInterfaceContext context_param = new ShareInterfaceContext();
			context_param.setValue("key", interface_id);
			table.executeFunction("queryTableParam", context_param, inputNode,
					outputNode);
			Recordset rs_param = context_param.getRecordset(outputNode);
			List paramList = new ArrayList();
			for (int i = 0; i < rs_param.size(); i++) {
				DataBus db = rs_param.get(i);
				Map dataMap = new HashMap();
				dataMap.put("cond", db.getValue("frist_connector"));
				dataMap.put("leftParen", db.getValue("left_paren"));
				Map left_table = new HashMap();
				left_table.put("id", db.getValue("table_no"));
				left_table.put("name_en", db.getValue("table_name_en"));
				left_table.put("name_cn", db.getValue("table_name_cn"));
				dataMap.put("leftTable", left_table);
				Map left_column = new HashMap();
				left_column.put("id", db.getValue("column_no"));
				left_column.put("name_en", db.getValue("dataitem_name_en"));
				left_column.put("name_cn", db.getValue("dataitem_name_cn"));
				dataMap.put("leftColumn", left_column);
				Map middleMap = new HashMap();
				middleMap.put("value", db.getValue("param_type"));
				middleMap.put("name_cn", db.getValue("param_type"));
				middleMap.put("type", db.getValue("dataitem_type_r"));
				dataMap.put("middleParen", middleMap);
				dataMap.put("paramValue", db.getValue("param_value"));
				dataMap.put("rightParen", db.getValue("right_paren"));
				paramList.add(dataMap);
			}
			context.setProperty("tableParam", JsonDataUtil
					.toJSONString(paramList));
			//System.out.println(JsonDataUtil.toJSONString(paramList));
			// ��ѯ���������
			ShareInterfaceContext context_condition = new ShareInterfaceContext();
			context_condition.setValue("key", interface_id);
			table.executeFunction("queryTableCondition", context_condition,
					inputNode, outputNode);
			Recordset rs_condition = context_condition.getRecordset(outputNode);
			List conditionList = new ArrayList();
			for (int i = 0; i < rs_condition.size(); i++) {
				DataBus db = rs_condition.get(i);
				Map dataMap = new HashMap();
				Map left_table = new HashMap();
				left_table.put("id", db.getValue("left_table_no"));
				left_table.put("name_en", db.getValue("left_table_name_en"));
				left_table.put("name_cn", db.getValue("left_table_name_cn"));
				dataMap.put("leftTable", left_table);
				Map left_column = new HashMap();
				left_column.put("id", db.getValue("left_column_no"));
				left_column.put("name_en", db.getValue("left_column_name_en"));
				left_column.put("name_cn", db.getValue("left_column_name_cn"));
				dataMap.put("leftColumn", left_column);
				dataMap.put("middleParen", db.getValue("second_connector"));
				Map right_table = new HashMap();
				right_table.put("id", db.getValue("right_table_no"));
				right_table.put("name_en", db.getValue("right_table_name_en"));
				right_table.put("name_cn", db.getValue("right_table_name_cn"));
				dataMap.put("rightTable", right_table);
				Map right_column = new HashMap();
				right_column.put("id", db.get("right_column_no"));
				right_column
						.put("name_en", db.getValue("right_column_name_en"));
				right_column
						.put("name_cn", db.getValue("right_column_name_cn"));
				dataMap.put("rightColumn", right_column);
				conditionList.add(dataMap);
			}
			context.setProperty("tableCondition", JsonDataUtil
					.toJSONString(conditionList));
		}
	}

	/**
	 * ɾ�������������ӿ�������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn401005(ShareInterfaceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		ShareInterfaceContext sContext = new ShareInterfaceContext();
		sContext = context;
		table.executeFunction("queryServiceByInterfaceId",sContext, inputNode, outputNode);
		String serviceCount = sContext.getRecord("record").getValue("amount");
		if(StringUtils.isNotBlank(serviceCount)){
			if("0".equals(serviceCount)){
				table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
			}
		}
		
	}

	/**
	 * ��ȡ�ṩ�����ҵ��ϵͳ txn401006
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	@SuppressWarnings("unchecked")
	public void txn401006(ShareInterfaceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getShareBusiSystem", context, inputNode,
				outputNode);
		Recordset rs = context.getRecordset(outputNode);
		List dataList = new ArrayList();
		for (int i = 0; i < rs.size(); i++) {
			DataBus db = rs.get(i);
			Map dataMap = new HashMap();
			dataMap.put("key", db.get("key"));
			dataMap.put("title", db.get("title"));
			dataList.add(dataMap);
		}
		context.setProperty("dataString", JsonDataUtil.toJSONString(dataList));
	}

	/**
	 * ��ѯ���������б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	@SuppressWarnings("unchecked")
	public void txn401007(ShareInterfaceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("getTopicByBusSystem", context, inputNode,
				outputNode);
		Recordset rs = context.getRecordset(outputNode);
		List dataList = new ArrayList();
		for (int i = 0; i < rs.size(); i++) {
			DataBus db = rs.get(i);
			Map dataMap = new HashMap();
			dataMap.put("key", db.get("key"));
			dataMap.put("title", db.get("title"));
			dataList.add(dataMap);
		}
		context.setProperty("dataString", JsonDataUtil.toJSONString(dataList));
	}

	/**
	 * 
	 * txn401008 ����ҵ������ID��ȡ��ҵ�������µ����б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	@SuppressWarnings("unchecked")
	public void txn401008(ShareInterfaceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table
				.executeFunction("getTableByTopic", context, inputNode,
						outputNode);
		Recordset rs = context.getRecordset(outputNode);
		List dataList = new ArrayList();
		for (int i = 0; i < rs.size(); i++) {
			DataBus db = rs.get(i);
			Map dataMap = new HashMap();
			dataMap.put("key", db.get("key"));
			dataMap.put("title", db.get("title"));
			dataMap.put("code", db.get("code"));
			dataList.add(dataMap);
		}
		context.setProperty("dataString", JsonDataUtil.toJSONString(dataList));
	}

	/**
	 * 
	 * txn401009 ���ݱ�ID��ȡ�����µ����й���������
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	@SuppressWarnings("unchecked")
	public void txn401009(ShareInterfaceContext context) throws TxnException
	{
		//System.out.println("enter -- \n"+context);
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		table.executeFunction("getItemsByTable", context, inputNode, outputNode);
		Recordset rs = context.getRecordset(outputNode);
		List dataList = new ArrayList();
		for (int i = 0; i < rs.size(); i++) {
			DataBus db = rs.get(i);
			Map dataMap = new HashMap();
			dataMap.put("key", db.get("key"));
			dataMap.put("title", db.get("title"));
			dataMap.put("code", db.get("code"));
			dataMap.put("type",db.get("col_type"));
			dataMap.put("code_table",db.get("code_table"));
			dataList.add(dataMap);
		}

		context.setProperty("dataString", JsonDataUtil.toJSONString(dataList));
		//System.out.println("======="+context);
	}

	/**
	 * txn401010(�ӿ�����Ԥ��)
	 * 
	 * @param context
	 * @throws TxnException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn401010(ShareInterfaceContext context) throws TxnException
	{
		String tableIds = context.getRecord(inputNode).getValue("tableIds");
		String sql = context.getRecord(inputNode).getValue("sql");
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryTableTopColumn", context, inputNode,
				outputNode);
		Recordset rs = context.getRecordset(outputNode);
		String sql_temp = "";
		for (int i = 0; i < rs.size(); i++) {
			DataBus db = rs.get(i);
			sql_temp += (sql_temp == "" ? db.getValue("table_name_en") + "."
					+ db.getValue("dataitem_name_en") : ","
					+ db.getValue("table_name_en") + "."
					+ db.getValue("dataitem_name_en"));
		}
		sql=sql.replace("\\*", sql_temp);
		System.out.println(sql);
		DBPoolConnection conn=new DBPoolConnection(CollectConstants.DATASOURCE_GXK);
		List list=conn.selectBySql(sql);
		
		

	}

	/**
	 * 
	 * txn401011(�߼�ɾ��)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn401011(ShareInterfaceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "setInterfaceIsmarkupAsDelete", context, inputNode, outputNode );
	}
	
	/**
	 * 
	 * txn401012(�ж��Ƿ��з������ô˽ӿ�)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn401012(ShareInterfaceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String interface_id = context.getRecord(inputNode).getValue("interface_id"); 
		String sql = "select count(*) as interfaceusednum ,'"+interface_id+"' as interface_id from share_service where  is_markup='"+ExConstant.IS_MARKUP_Y+"' AND interface_id='"+interface_id+"'";
		System.out.println(sql);
		table.executeSelect(sql, context, outputNode);
	}
	
	/**
	 * 
	 * txn401013(�޸�����ͣ��)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn401013(ShareInterfaceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//System.out.println(context);
		
		for(int i=0;i<context.getRecordset("primary-key").size();i++){
			String sql = "update share_interface t set t.interface_state='";
			String interface_id=context.getRecordset("primary-key").get(i).getValue("interface_id");
			String interface_state=context.getRecordset("primary-key").get(i).getValue("interface_state");
			if(interface_state.equals("N")){
				sql+= ExConstant.SERVICE_STATE_N+"' ";
			}
			else{
				sql+= ExConstant.SERVICE_STATE_Y +"' ";
			}
			sql+=" where t.interface_id='"+interface_id+"'";
			table.executeUpdate(sql);	
			//System.out.println(sql);
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
		ShareInterfaceContext appContext = new ShareInterfaceContext(context);
		invoke(method, appContext);
	}

	public static void main(String[] args)
	{

	}
}
