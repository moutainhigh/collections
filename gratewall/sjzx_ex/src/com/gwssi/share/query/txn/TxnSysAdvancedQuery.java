package com.gwssi.share.query.txn;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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

import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.services.common.Constants;
import com.gwssi.share.query.vo.SysAdvancedQueryContext;

public class TxnSysAdvancedQuery extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysAdvancedQuery.class, SysAdvancedQueryContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_advanced_query";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_advanced_query list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_advanced_query";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_advanced_query";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_advanced_query";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_advanced_query";
	
	// ���ݲ�ѯ���Ʋ�ѯ�����ڲ��뼰�޸ļ�¼�ǵ�����Ψһ�Ե�У��
	private static final String SELECT_NAME_FUNCTION = "select sys_advanced_query name";
	
	
	/**
	 * ���캯��
	 */
	public TxnSysAdvancedQuery()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�߼���ѯ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60210001( SysAdvancedQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String createBy = context.getOperData().getValue("oper-name");
		String username = context.getOperData().getValue("username");
		String userId = context.getOperData().getValue("userID");
		
		context.getRecord("select-key").setValue("create_by", createBy);
		context.getRecord("select-key").setValue("username", username);
		context.getRecord("select-key").setValue("userid", userId);
		
		// ��ѯ��¼������ VoSysAdvancedQuerySelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryList", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysAdvancedQuery result[] = context.getSysAdvancedQuerys( outputNode );
	}
	
	/** �޸ĸ߼���ѯ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60210002( SysAdvancedQueryContext context ) throws TxnException
	{
		//����ҳ�����б�������ݽ��н���
		try {
			context.getRecord(inputNode).setValue("name", URLDecoder.decode(context.getRecord(inputNode).getValue("name"),"UTF-8"));
			context.getRecord(inputNode).setValue("step1_param",URLDecoder.decode(context.getRecord(inputNode).getValue("step1_param"),"UTF-8"));
			context.getRecord(inputNode).setValue("step2_param",URLDecoder.decode(context.getRecord(inputNode).getValue("step2_param"),"UTF-8"));
			context.getRecord(inputNode).setValue("query_sql",URLDecoder.decode(context.getRecord(inputNode).getValue("query_sql"),"UTF-8"));
			context.getRecord(inputNode).setValue("display_columns_cn_array",URLDecoder.decode(context.getRecord(inputNode).getValue("display_columns_cn_array"),"UTF-8"));
			context.getRecord(inputNode).setValue("display_columns_en_array",URLDecoder.decode(context.getRecord(inputNode).getValue("display_columns_en_array"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		
		//��ø߼���ѯ��ID
		String svrId = context.getRecord(inputNode).getValue("sys_advanced_query_id");
		context.getRecord("primary-key").setValue("sys_advanced_query_id", svrId);
		//ɾ���߼���ѯID�����б�������������
		callService("6022206", context);
		//ɾ���߼���ѯID�����в�������
		callService("6022306", context);
		
		JSONArray connJSONArray = JSONArray.fromObject(context.getRecord(inputNode).getValue("step1_param"));
		JSONArray queryJSONArray = JSONArray.fromObject(context.getRecord(inputNode).getValue("step2_param"));
		
		context.remove(inputNode);
		String max = "0";
		if(connJSONArray != null){
			for(Iterator it = connJSONArray.iterator(); it.hasNext();){
				JSONObject jObj = (JSONObject) it.next();
				
				String uuid = UuidGenerator.getUUID();
				DataBus db = new DataBus();
				db.setValue("sys_advquery_step1_param_id", uuid);
				db.setValue("sys_advanced_query_id", svrId);
				db.setValue("operator1", jObj.getString("condition"));
				db.setValue("left_paren", jObj.getString("preParen"));
				db.setValue("left_table_no", jObj.getString("tableOneId"));
				db.setValue("left_table_name", jObj.getString("tableOneName"));
				db.setValue("left_table_name_cn", jObj.getString("tableOneNameCn"));
				db.setValue("left_column_no", jObj.getString("columnOneId"));
				db.setValue("left_column_name", jObj.getString("columnOneName"));
				db.setValue("left_column_name_cn", jObj.getString("columnOneNameCn"));
				db.setValue("operator2", jObj.getString("relation"));
				db.setValue("right_table_no", jObj.getString("tableTwoId"));
				db.setValue("right_table_name", jObj.getString("tableTwoName"));
				db.setValue("right_table_name_cn", jObj.getString("tableTwoNameCn"));
				db.setValue("right_column_no", jObj.getString("columnTwoId"));
				db.setValue("right_column_name", jObj.getString("columnTwoName"));
				db.setValue("right_column_name_cn", jObj.getString("columnTwoNameCn"));
				db.setValue("right_paren", jObj.getString("postParen"));
				table.executeSelect("SELECT MAX(order_col) as order_col FROM sys_advquery_step1_param", context, "max-order");
				db.setValue("order_col", ""+(Integer.parseInt(max) + 1 ));
				context.addRecord("record", db);
				db.setValue("param_text", jObj.getString("paramText"));
				callService("6022203", context);
				context.remove("record");
			}
		}
		
		if(queryJSONArray != null){
			for(Iterator it = queryJSONArray.iterator(); it.hasNext();){
				JSONObject jObj = (JSONObject) it.next();
				String uuid = UuidGenerator.getUUID();
				DataBus db = new DataBus();
				db.setValue("sys_advquery_step2_param_id", uuid);
				db.setValue("sys_advanced_query_id", svrId);
				db.setValue("operator1", jObj.getString("condition"));
				db.setValue("left_paren", jObj.getString("preParen"));
				db.setValue("left_table_no", jObj.getString("tableOneId"));
				db.setValue("left_table_name", jObj.getString("tableOneName"));
				db.setValue("left_table_name_cn", jObj.getString("tableOneNameCn"));
				db.setValue("left_column_no", jObj.getString("columnOneId"));
				db.setValue("left_column_name", jObj.getString("columnOneName"));
				db.setValue("left_column_name_cn", jObj.getString("columnOneNameCn"));
				db.setValue("operator2", jObj.getString("relation"));
				db.setValue("param_value", formatSQL(jObj.getString("paramValue")));
				db.setValue("right_paren", jObj.getString("postParen"));
				table.executeSelect("SELECT MAX(order_col) as order_col FROM sys_advquery_step2_param", context, "max-order");
				max = context.getRecord("max-order").getValue("order_col");
				if(max.trim().equals(""))
					max = "0";
				db.setValue("order_col", ""+(Integer.parseInt(max) + 1 ));
				db.setValue("param_text", jObj.getString("paramText"));
				context.addRecord(inputNode, db);
				callService("6022303", context);
				context.remove("record");
			}
		}
	}
	
	/** ���Ӹ߼���ѯ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60210003( SysAdvancedQueryContext context ) throws TxnException
	{
		//����ҳ�����б�������ݽ��н���
		try {
			context.getRecord(inputNode).setValue("name", URLDecoder.decode(context.getRecord(inputNode).getValue("name"),"UTF-8"));
			context.getRecord(inputNode).setValue("step1_param",URLDecoder.decode(context.getRecord(inputNode).getValue("step1_param"),"UTF-8"));
			context.getRecord(inputNode).setValue("step2_param",URLDecoder.decode(context.getRecord(inputNode).getValue("step2_param"),"UTF-8"));
			context.getRecord(inputNode).setValue("query_sql",URLDecoder.decode(context.getRecord(inputNode).getValue("query_sql"),"UTF-8"));
			context.getRecord(inputNode).setValue("display_columns_cn_array",URLDecoder.decode(context.getRecord(inputNode).getValue("display_columns_cn_array"),"UTF-8"));
			context.getRecord(inputNode).setValue("display_columns_en_array",URLDecoder.decode(context.getRecord(inputNode).getValue("display_columns_en_array"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		context.getRecord(inputNode).setValue("create_date", new SimpleDateFormat(Constants.DB_DATE_FORMAT).format(new Date()));
		context.getRecord(inputNode).setValue("create_by", context.getOperData().getValue("oper-name"));
		context.getRecord(inputNode).setValue("exec_total", "0");
		context.getRecord(inputNode).setValue("sys_svr_user_id", context.getOperData().getValue("userID"));
		
		table.executeSelect("SELECT MAX(order_col) as order_col FROM sys_advanced_query", context, "max-order");
		String max = context.getRecord("max-order").getValue("order_col");
		if(max.trim().equals(""))
			max = "0";
		int next = Integer.parseInt(max) + 1;
		context.getRecord("record").setValue("order_col", "" + next);
		context.getRecord("record").setValue("sys_advanced_query_id", UuidGenerator.getUUID());
		
		//�滻���̨�Զ�ת�ɵ�˫����
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );

		String svrId = context.getRecord(inputNode).getValue("sys_advanced_query_id");
		
		JSONArray connJSONArray = JSONArray.fromObject(context.getRecord(inputNode).getValue("step1_param"));
		JSONArray queryJSONArray = JSONArray.fromObject(context.getRecord(inputNode).getValue("step2_param"));
		DataBus dbs=new DataBus();
		dbs.setValue("sys_advanced_query_id", svrId);
		context.addRecord("select-key", dbs);
		context.remove("record");
		if(connJSONArray != null){
			for(Iterator it = connJSONArray.iterator(); it.hasNext();){
				JSONObject jObj = (JSONObject) it.next();
				
				String uuid = UuidGenerator.getUUID();
				DataBus db = new DataBus();
				db.setValue("sys_advquery_step1_param_id", uuid);
				db.setValue("sys_advanced_query_id", svrId);
				db.setValue("operator1", jObj.getString("condition"));
				db.setValue("left_paren", jObj.getString("preParen"));
				db.setValue("left_table_no", jObj.getString("tableOneId"));
				db.setValue("left_table_name", jObj.getString("tableOneName"));
				db.setValue("left_table_name_cn", jObj.getString("tableOneNameCn"));
				db.setValue("left_column_no", jObj.getString("columnOneId"));
				db.setValue("left_column_name", jObj.getString("columnOneName"));
				db.setValue("left_column_name_cn", jObj.getString("columnOneNameCn"));
				db.setValue("operator2", jObj.getString("relation"));
				db.setValue("right_table_no", jObj.getString("tableTwoId"));
				db.setValue("right_table_name", jObj.getString("tableTwoName"));
				db.setValue("right_table_name_cn", jObj.getString("tableTwoNameCn"));
				db.setValue("right_column_no", jObj.getString("columnTwoId"));
				db.setValue("right_column_name", jObj.getString("columnTwoName"));
				db.setValue("right_column_name_cn", jObj.getString("columnTwoNameCn"));
				db.setValue("right_paren", jObj.getString("postParen"));
				table.executeSelect("SELECT MAX(order_col) as order_col FROM sys_advquery_step1_param", context, "max-order");
				max = context.getRecord("max-order").getValue("order_col");
				if(max.trim().equals(""))
					max = "0";
				db.setValue("order_col", ""+(Integer.parseInt(max) + 1 ));
				db.setValue("param_text", jObj.getString("paramText"));
				context.addRecord("record", db);
				callService("6022203", context);
				context.remove("record");
			}
		}
		
		if(queryJSONArray != null){
			for(Iterator it = queryJSONArray.iterator(); it.hasNext();){
				JSONObject jObj = (JSONObject) it.next();
				String uuid = UuidGenerator.getUUID();
				DataBus db = new DataBus();
				db.setValue("sys_advquery_step2_param_id", uuid);
				db.setValue("sys_advanced_query_id", svrId);
				db.setValue("operator1", jObj.getString("condition"));
				db.setValue("left_paren", jObj.getString("preParen"));
				db.setValue("left_table_no", jObj.getString("tableOneId"));
				db.setValue("left_table_name", jObj.getString("tableOneName"));
				db.setValue("left_table_name_cn", jObj.getString("tableOneNameCn"));
				db.setValue("left_column_no", jObj.getString("columnOneId"));
				db.setValue("left_column_name", jObj.getString("columnOneName"));
				db.setValue("left_column_name_cn", jObj.getString("columnOneNameCn"));
				db.setValue("operator2", jObj.getString("relation"));
				db.setValue("param_value", formatSQL(jObj.getString("paramValue")));
				db.setValue("right_paren", jObj.getString("postParen"));
				table.executeSelect("SELECT MAX(order_col) as order_col FROM sys_advquery_step2_param", context, "max-order");
				max = context.getRecord("max-order").getValue("order_col");
				if(max.trim().equals(""))
					max = "0";
				db.setValue("order_col", ""+(Integer.parseInt(max) + 1 ));
				db.setValue("param_text", jObj.getString("paramText"));
				context.addRecord(inputNode, db);
				callService("6022303", context);
				context.remove("record");
			}
		}
	}
	
	/** ��ѯ�߼���ѯ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60210004( SysAdvancedQueryContext context ) throws TxnException
	{
		//�����Զ����ѯID��ѯ����һ�����б�
		callService("6022201", context);
		Recordset rs = context.getRecordset("step1-param");
		//ѭ�����еĲ�������֯�����ַ���
		while(rs.hasNext()){
			DataBus db = (DataBus)rs.next();
			
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
			
			db.setValue("step1_param", jsonObj.toString());
		}
		
		//�����Զ����ѯID��ѯ����������б�
		callService("6022301", context);
		
		rs = context.getRecordset("step2-param");
		while(rs.hasNext()){
			DataBus db = (DataBus)rs.next();
			
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
			jsonObj.put("paramText", db.getValue("param_text"));
			
			db.setValue("step2_param", jsonObj.toString());
		}

		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		// ��ѯ��¼������ VoSysAdvancedQueryPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );

		String[] tableNos = context.getRecord(outputNode).getValue("table_no").split(",");
		for(int i = 0;i < tableNos.length;i++){
			context.getRecord("select-key").setValue("table_no", tableNos[i]);
			table.executeFunction( "queryTable", context, inputNode, "temp-table-info" );
			DataBus db = new DataBus();
			db.setValue("table_no", 		context.getRecord("temp-table-info").getValue("table_no"));
			db.setValue("table_name", 		context.getRecord("temp-table-info").getValue("table_name"));
			db.setValue("table_name_cn", 	context.getRecord("temp-table-info").getValue("table_name_cn"));
			db.setValue("sys_id", 			context.getRecord("temp-table-info").getValue("sys_id"));
			db.setValue("sys_name", 		context.getRecord("temp-table-info").getValue("sys_name"));
			context.addRecord("table-info", db);
		}
		context.remove("temp-table-info");
		String[] columnNos = context.getRecord(outputNode).getValue("display_columns").split(",");
		for(int i = 0; i < columnNos.length;i++){
			context.getRecord("select-key").setValue("column_no", columnNos[i]);
			table.executeFunction( "queryColumn", context, inputNode, "temp-column-info" );
			DataBus db = context.getRecord("temp-column-info");
//			db.setValue("column_no", 		context.getRecord("temp-column-info").getValue("column_no"));
//			db.setValue("column_name", 		context.getRecord("temp-column-info").getValue("column_name"));
//			db.setValue("column_name_cn", 	context.getRecord("temp-column-info").getValue("column_name_cn"));
//			db.setValue("column_byname", 	context.getRecord("temp-column-info").getValue("column_byname"));
//			db.setValue("table_no", 		context.getRecord("temp-column-info").getValue("table_no"));
//			db.setValue("table_name", 		context.getRecord("temp-column-info").getValue("table_name"));
//			db.setValue("table_name_cn", 		context.getRecord("temp-column-info").getValue("table_name_cn"));
//			db.setValue("edit_type", 			context.getRecord("temp-table-info").getValue("edit_type"));
//			db.setValue("demo", 			context.getRecord("temp-table-info").getValue("demo"));
			context.addRecord("column-info", db);
		}
		context.remove("temp-column-info");
	}
	
	/** ɾ���߼���ѯ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60210005( SysAdvancedQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//����ɾ���������е����в���
		TxnContext cont = new TxnContext();
		DataBus db = new DataBus();
		db.setValue("sys_advanced_query_id", context.getRecord(inputNode).getValue("sys_advanced_query_id"));
		cont.addRecord(inputNode, db);
		callService("6022206", cont);
		callService("6022306", cont);
		
		// ɾ����¼�������б� VoSysAdvancedQueryPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		
		
	}
	
	/** ��ѯ����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60210006( SysAdvancedQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getFirstLevelNode", context, inputNode, outputNode );
		setExpand(context);
	}
	
	
	/**
	 * ��ѯ�����µı�
	 * @param context
	 * @throws TxnException
	 */
	public void txn60210008( SysAdvancedQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getAllTables", context, inputNode, outputNode );
	}
	
	/**
	 * ��ѯ����ֶ�
	 * @param context
	 * @throws TxnException
	 */
	public void txn60210009( SysAdvancedQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getAllColumns", context, inputNode, outputNode );
	}
	
	/**
	 * ��ѯ����Ψһ��У��
	 * @param context
	 * @throws TxnException
	 */
	public void txn60210010( SysAdvancedQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		context.getRecord(inputNode).setValue("create_by", context.getOperData().getOperName());
		table.executeFunction( SELECT_NAME_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * ��ѯ����������Ϣ
	 * @param context
	 * @throws TxnException
	 */
	public void txn60210012( SysAdvancedQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getJCDMFX", context, inputNode, outputNode );
	}
	
	/**
	 * ��ѯ����ֶ�(�ȶ�����)
	 * @param context
	 * @throws TxnException
	 */
	public void txn60210013( SysAdvancedQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getAllColumnsCompare", context, inputNode, outputNode );
	}
	
	public void txn60210014( SysAdvancedQueryContext context ) throws TxnException
	{
		callService("60210004", context);
	}
	

	private static final String INSERT_COPY_FUNCTION = "insert copy sys_advanced_query";
	
	public void txn60210015( SysAdvancedQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "copyOrigin", context, inputNode, outputNode );

		Recordset rs = context.getRecordset(outputNode);
		while(rs.hasNext()){
			DataBus db = (DataBus)rs.next();
			TxnContext tc = new TxnContext();
			tc.addRecord(outputNode, db);
			table.executeFunction( INSERT_COPY_FUNCTION, tc, outputNode, outputNode );
		}
	}
	
	private void setExpand( SysAdvancedQueryContext context ){
		Recordset rs = null;
		try {
			rs = context.getRecordset("record");
		} catch (TxnException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < rs.size(); i ++)
		{
			DataBus dataBus = rs.get(i);
			dataBus.setValue("expand", "true");
		}
	}
		
	private String formatSQL(String sql){
		String sb = sql.replaceAll("'", "''");
		
		return sb;
	}
	/**
	 * ���ظ���ķ����������滻���׽ӿڵ��������
	 * ���ú���
	 * @param funcName ��������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����"
			);
		}
		
		// ִ��
		SysAdvancedQueryContext appContext = new SysAdvancedQueryContext( context );
		invoke( method, appContext );
	}
	
	/**
	 * �߼���ѯ������
	 * @param context
	 * @throws TxnException
	 */
	public void txn60210016( SysAdvancedQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "getAllColumnsCompare", context, inputNode, outputNode );
	}
}
