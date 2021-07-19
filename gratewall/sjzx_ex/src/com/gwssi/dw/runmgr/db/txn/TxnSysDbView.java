package com.gwssi.dw.runmgr.db.txn;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.db.TxnBaseDbMgr;
import com.gwssi.dw.runmgr.services.common.Constants;

public class TxnSysDbView extends TxnBaseDbMgr
{
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_db_view";
	
	// 数据表名称
	private static final String PARAM_TABLE_NAME = "sys_db_view_param";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "getDBViewList";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_db_view";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_db_view";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_db_view";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_db_view";
	
	// 查询记录
	private static final String SELECT_CODE_FUNCTION = "select one sys_db_view_code";
	
	// 查询记录
	private static final String SELECT_NAME_FUNCTION = "select one sys_db_view_name";

	private static final String SELECT_MAX_DB_ORDER = "select max sys_db_view_db_order";
	
	private static final String SELECT_MAX_DB_PARAMORDER = "select next_order sys_db_view_param";
	/**
	 * 构造函数
	 */
	public TxnSysDbView()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询视图列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102001( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		if(context.getRecord(inputNode).getValue("showall") != null){ 
			Attribute.setPageRow(context, outputNode, -1);
		}
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		
		Recordset rs = context.getRecordset(outputNode);
		
		for(int i=0;rs!=null&&i<rs.size();i++){
			DataBus temp = rs.get(i);
			String sys_db_view_id = temp.getValue("sys_db_view_id");			
			if (hasConfigView(table,null,sys_db_view_id)) {
				temp.setValue("hasConfig", "1");
			}else{
				temp.setValue("hasConfig", "0");
			}
		}
		
	}
	
	/** 修改视图信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102002( TxnContext context ) throws TxnException
	{
		//将在页面层进行编码的数据进行解码
		try {
			context.getRecord(inputNode).setValue("view_name", URLDecoder.decode(context.getRecord(inputNode).getValue("view_name"),"UTF-8"));
			context.getRecord(inputNode).setValue("view_code",URLDecoder.decode(context.getRecord(inputNode).getValue("view_code"),"UTF-8"));
			context.getRecord(inputNode).setValue("view_desc",URLDecoder.decode(context.getRecord(inputNode).getValue("view_desc"),"UTF-8"));
			context.getRecord(inputNode).setValue("params",URLDecoder.decode(context.getRecord(inputNode).getValue("params"),"UTF-8"));
			context.getRecord(inputNode).setValue("column_cn",URLDecoder.decode(context.getRecord(inputNode).getValue("column_cn"),"UTF-8"));
			context.getRecord(inputNode).setValue("column_en",URLDecoder.decode(context.getRecord(inputNode).getValue("column_en"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );

		String sys_db_view_id = context.getRecord(inputNode).getValue("sys_db_view_id");
		String view_name = context.getRecord(inputNode).getValue("view_name");
		DataBus bus = new DataBus();
		bus.setValue("sys_db_view_id", sys_db_view_id);
		context.addRecord("select-key", bus);
		context.addRecord("primary-key", bus);
		int i = table.executeFunction("haveAssigned", context, "select-key", "config");
		System.out.println(context);
		if(i > 0){
			if(context.getRecord("config")!=null&&!"".equals(context.getRecord("config").getValue("sys_db_view_id"))){
			   throw new TxnDataException("999999","该视图已进行配置，禁止修改！");
			}
		}

		context.remove("config");
		context.remove("select-key");
		
//		DataBus bus = new DataBus();
//		bus.setValue("view_code", context.getRecord("record").getValue("svr_code"));
//		context.addRecord("select-key", bus);
//		int i = table.executeFunction(SELECT_CODE_FUNCTION, context, "select-key", "exist-service");
//
//		if(i > 0){
//			if(!sys_db_view_id.equalsIgnoreCase(context.getRecord("exist-service").getValue("sys_db_view_id"))){
//				throw new TxnDataException("999999","该服务代码已存在！");
//			}
//		}
		
//		context.remove("exist-service");
//		context.remove("select-key");
		bus = new DataBus();
		bus.setValue("view_name", context.getRecord("record").getValue("view_name"));
		context.addRecord("select-key", bus);
		i = table.executeFunction(SELECT_NAME_FUNCTION, context, "select-key", "exist-service");

		if(i > 0){
			if(!sys_db_view_id.equalsIgnoreCase(context.getRecord("exist-service").getValue("sys_db_view_id"))){
				throw new TxnDataException("999999","该视图名称已存在！");
			}
		}
		context.remove("exist-service");
		
		// 修改记录的内容 VoSysSvrService sys_db_view = context.getSysSvrService( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );

		

		BaseTable param_table = TableFactory.getInstance().getTableObject( this, PARAM_TABLE_NAME );
		// 删除此视图包含的全部参数
		callService("52102104", context);
		String str = context.getRecord(inputNode).getValue("params");
		String svrId = context.getRecord(inputNode).getValue("sys_db_view_id");
		context.remove(inputNode);
		
		JSONArray connJSONArray = JSONArray.fromObject(str);
		
		if(connJSONArray != null){
			boolean f = false;
			for(Iterator it = connJSONArray.iterator(); it.hasNext();){
				
				JSONObject jObj = (JSONObject) it.next();
				
				DataBus db = new DataBus();
				db.setValue("sys_db_view_id", svrId);
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
				param_table.executeFunction(SELECT_MAX_DB_PARAMORDER, context, db, "max-order");
				String max = context.getRecord("max-order").getValue("param_order");
				if(max.trim().equals(""))
					max = "0";
				db.setValue("param_order", ""+(Integer.parseInt(max) + 1 ));
				db.setValue("param_text", jObj.getString("paramText"));
				context.addRecord(inputNode, db);
				f = true;
			}	
			if(f)
			    callService("52102102", context);
		}
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "修改数据库视图，视图名称："+view_name);
	}
	
	/** 增加视图信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102003( TxnContext context ) throws TxnException
	{
		//将在页面层进行编码的数据进行解码
		try {
			context.getRecord(inputNode).setValue("view_name", URLDecoder.decode(context.getRecord(inputNode).getValue("view_name"),"UTF-8"));
			context.getRecord(inputNode).setValue("view_code",URLDecoder.decode(context.getRecord(inputNode).getValue("view_code"),"UTF-8"));
			context.getRecord(inputNode).setValue("view_desc",URLDecoder.decode(context.getRecord(inputNode).getValue("view_desc"),"UTF-8"));
			context.getRecord(inputNode).setValue("params",URLDecoder.decode(context.getRecord(inputNode).getValue("params"),"UTF-8"));
			context.getRecord(inputNode).setValue("column_cn",URLDecoder.decode(context.getRecord(inputNode).getValue("column_cn"),"UTF-8"));
			context.getRecord(inputNode).setValue("column_en",URLDecoder.decode(context.getRecord(inputNode).getValue("column_en"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		BaseTable param_table = TableFactory.getInstance().getTableObject( this, PARAM_TABLE_NAME );
		

		String name = context.getRecord(inputNode).getValue("view_name");
		DataBus bus = new DataBus();
		bus.setValue("view_name", name);
		context.addRecord("select-key", bus);
		int i = table.executeFunction(SELECT_NAME_FUNCTION, context, "select-key", "exist-service");
		if(i > 0){
			throw new TxnDataException("999999","该视图名称已存在！");
		}
		//删除上次的查询条件
		context.remove("select-key");
		context.remove("exist-service");
		
		context.getRecord("record").setValue("create_date", new SimpleDateFormat(Constants.DB_DATE_FORMAT).format(new Date()));
		String uId = context.getOperData().getValue("oper-name");
		context.getRecord("record").setValue("create_by", uId);
		table.executeFunction( SELECT_MAX_DB_ORDER, context, inputNode, "max-order" );
		String max = context.getRecord("max-order").getValue("view_order");
		if(max.trim().equals(""))
			max = "0";
		int next = Integer.parseInt(max) + 1;
		context.getRecord("record").setValue("view_order", "" + next);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		String str = context.getRecord(inputNode).getValue("params");
		String svrId = context.getRecord(inputNode).getValue("sys_db_view_id");
		context.remove(inputNode);
		
		JSONArray connJSONArray = JSONArray.fromObject(str);
		
		if(connJSONArray != null){
			boolean f = false;
			for(Iterator it = connJSONArray.iterator(); it.hasNext();){
				JSONObject jObj = (JSONObject) it.next();
				
				DataBus db = new DataBus();
				db.setValue("sys_db_view_id", svrId);
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
				param_table.executeFunction(SELECT_MAX_DB_PARAMORDER, context, db, "max-order");
				max = context.getRecord("max-order").getValue("param_order");
				if(max.trim().equals(""))
					max = "0";
				db.setValue("param_order", ""+(Integer.parseInt(max) + 1 ));
				db.setValue("param_text", jObj.getString("paramText"));
				context.addRecord(inputNode, db);
				f = true;
			}
			if(f)
			    callService("52102102", context);
		}
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "增加数据库视图，视图名称："+name);
		/*
		String[] params = str.split(",");
		
		if(params != null && params.length > 0){
			for(int j = 0; j < params.length ; j++){
				if(params[j] == null || params[j].trim().equals("")){
					continue;
				}
				String[] param = params[j].split("~");
				DataBus db = new DataBus();
				db.setValue("sys_db_view_id", svrId);
				db.setValue("operator1", param[0].equals("-1") ? "" : param[0]);
				db.setValue("left_paren", param[1].equals("-1") ? "" : param[1]);
				db.setValue("left_table_no", param[2]);
				db.setValue("left_column_no", param[3]);
				db.setValue("operator2", param[4]);
				db.setValue("right_table_no", param[5]);
				db.setValue("right_column_no", param[6]);
				db.setValue("right_paren", param[7].equals("-1") ? "" : param[7]);
				param_table.executeFunction(SELECT_MAX_DB_PARAMORDER, context, db, "max-order");
				max = context.getRecord("max-order").getValue("param_order");
				if(max.trim().equals(""))
					max = "0";
				db.setValue("param_order", ""+(Integer.parseInt(max) + 1 ));
				context.addRecord(inputNode, db);
				callService("52102102", context);
				context.remove(inputNode);
			}
		}
		*/
	}
	
	/** 查询视图用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102004( TxnContext context ) throws TxnException
	{
		//根据自定义查询ID查询步骤一参数列表
		callService("52102101", context);
		Recordset rs = context.getRecordset("record");
		//循环所有的参数，组织参数字符串
		while(rs!=null&&rs.hasNext()){
			DataBus db = (DataBus)rs.next();
			/*TxnContext txnContext = new TxnContext();
			txnContext.getRecord("primary-key").setValue("table_no", db.getValue("left_table_no"));
			callService("30402004", txnContext);
			String leftTblName = txnContext.getRecord("record").getValue("table_name");
			String leftTblNameCn = txnContext.getRecord("record").getValue("table_name_cn");
			
			txnContext = new TxnContext();
			txnContext.getRecord("primary-key").setValue("table_no", db.getValue("right_table_no"));
			callService("30402004", txnContext);
			String rightTblName = txnContext.getRecord("record").getValue("table_name");
			String rightTblNameCn = txnContext.getRecord("record").getValue("table_name_cn");
			
			txnContext = new TxnContext();
			txnContext.getRecord("primary-key").setValue("column_no", db.getValue("left_column_no"));
			callService("30403004", txnContext);
			String leftColName = txnContext.getRecord("record").getValue("column_name");
			String leftColNameCn = txnContext.getRecord("record").getValue("column_name_cn");
			
			txnContext = new TxnContext();
			txnContext.getRecord("primary-key").setValue("column_no", db.getValue("right_column_no"));
			callService("30403004", txnContext);
			String rightColName = txnContext.getRecord("record").getValue("column_name");
			String rightColNameCn = txnContext.getRecord("record").getValue("column_name_cn");
			
			StringBuffer params = new StringBuffer();
			params.append(db.getValue("sys_advquery_step1_param_id")).append("~")
				  .append(db.getValue("operator1")).append("~")
				  .append(db.getValue("left_paren")).append("~")
				  .append(db.getValue("left_table_no")).append("~")
				  .append(leftTblName).append("~")
				  .append(leftTblNameCn).append("~")
				  .append(db.getValue("left_column_no")).append("~")
				  .append(leftColName).append("~")
				  .append(leftColNameCn).append("~")
				  .append(db.getValue("operator2")).append("~")
				  .append(db.getValue("right_table_no")).append("~")
				  .append(rightTblName).append("~")
				  .append(rightTblNameCn).append("~")
				  .append(db.getValue("right_column_no")).append("~")
				  .append(rightColName).append("~")
				  .append(rightColNameCn).append("~")
				  .append(db.getValue("right_paren"));*/
			
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

		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		// 查询记录的主键 VoSysAdvancedQueryPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, "view-service" );

		table = TableFactory.getInstance().getTableObject( this, "sys_advanced_query" );
		String[] tableNos = context.getRecord("view-service").getValue("table_no").split(",");
		for(int i = 0;i < tableNos.length;i++){
			context.getRecord(inputNode).setValue("table_no", tableNos[i]);
			table.executeFunction( "queryTable", context, inputNode, "temp-table-info" );
			DataBus db = new DataBus();
			db.setValue("table_no", 		context.getRecord("temp-table-info").getValue("table_no"));
			db.setValue("table_name", 		context.getRecord("temp-table-info").getValue("table_name"));
			db.setValue("table_name_cn", 	context.getRecord("temp-table-info").getValue("table_name_cn"));
			db.setValue("sys_id", 			context.getRecord("temp-table-info").getValue("sys_id"));
			db.setValue("sys_name", 		context.getRecord("temp-table-info").getValue("sys_name"));
			db.setValue("sys_no", 			context.getRecord("temp-table-info").getValue("sys_no"));
			context.addRecord("table-info", db);
		}
		context.remove("temp-table-info");
		String[] columnNos = context.getRecord("view-service").getValue("column_no").split(",");
		for(int i = 0; i < columnNos.length;i++){
			context.getRecord("select-key").setValue("column_no", columnNos[i]);
			table.executeFunction( "queryColumn", context, inputNode, "temp-column-info" );
			DataBus db = new DataBus();
			db.setValue("column_no", 		context.getRecord("temp-column-info").getValue("column_no"));
			db.setValue("column_name", 		context.getRecord("temp-column-info").getValue("column_name"));
			db.setValue("column_name_cn", 	context.getRecord("temp-column-info").getValue("column_name_cn"));
			db.setValue("column_byname", 	context.getRecord("temp-column-info").getValue("column_byname"));
			db.setValue("table_no", 		context.getRecord("temp-column-info").getValue("table_no"));
			db.setValue("table_name", 		context.getRecord("temp-column-info").getValue("table_name"));
			db.setValue("table_name_cn", 		context.getRecord("temp-column-info").getValue("table_name_cn"));
			context.addRecord("column-info", db);
		}
		context.remove("temp-column-info");		
		
		
		
		/*
		//根据服务ID查询视图参数列表
		callService("52102101", context);
		Recordset rs = context.getRecordset("record");
		//循环所有的参数，组织参数字符串
		while(rs.hasNext()){
			DataBus db = (DataBus)rs.next();
			TxnContext txnContext = new TxnContext();
			txnContext.getRecord("primary-key").setValue("table_no", db.getValue("left_table_no"));
			callService("30402004", txnContext);
			String leftTblNameCn = txnContext.getRecord("record").getValue("table_name_cn");
			
			txnContext = new TxnContext();
			txnContext.getRecord("primary-key").setValue("table_no", db.getValue("right_table_no"));
			callService("30402004", txnContext);
			String rightTblNameCn = txnContext.getRecord("record").getValue("table_name_cn");
			
			txnContext = new TxnContext();
			txnContext.getRecord("primary-key").setValue("column_no", db.getValue("left_column_no"));
			callService("30403004", txnContext);
			String leftColNameCn = txnContext.getRecord("record").getValue("column_name_cn");
			
			txnContext = new TxnContext();
			txnContext.getRecord("primary-key").setValue("column_no", db.getValue("right_column_no"));
			callService("30403004", txnContext);
			String rightColNameCn = txnContext.getRecord("record").getValue("column_name_cn");
			
			StringBuffer params = new StringBuffer();
			params.append(db.getValue("sys_db_view_param_id")).append("~")
				  .append(db.getValue("sys_db_view_id")).append("~")
				  .append(db.getValue("operator1")).append("~")
				  .append(db.getValue("left_paren")).append("~")
				  .append(db.getValue("left_table_no")).append("~")
				  .append(leftTblNameCn).append("~")
				  .append(db.getValue("left_column_no")).append("~")
				  .append(leftColNameCn).append("~")
				  .append(db.getValue("operator2")).append("~")
				  .append(db.getValue("right_table_no")).append("~")
				  .append(rightTblNameCn).append("~")
				  .append(db.getValue("right_column_no")).append("~")
				  .append(rightColNameCn).append("~")
				  .append(db.getValue("right_paren"));
			
			db.setValue("params", params.toString());
		}
		
		//根据服务ID查询服务信息
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		//查询所有选择的数据表
		String tblNos = context.getRecord(outputNode).getValue("table_no");
		String[] temp = tblNos.split(",");
		for(int i = 0; i < temp.length; i++){
			TxnContext txnContext = new TxnContext();
			txnContext.getRecord("primary-key").setValue("table_no", temp[i]);
			callService("30402004", txnContext);
			DataBus db = new DataBus();
			db.setValue("table_no", txnContext.getRecord("record").getValue("table_no"));
			db.setValue("table_name", txnContext.getRecord("record").getValue("table_name"));
			db.setValue("table_name_cn", txnContext.getRecord("record").getValue("table_name_cn"));
			db.setValue("sys_id", txnContext.getRecord("record").getValue("sys_id"));
			context.addRecord("tbl-info", db);
		}
		
		//查询所有共享的字段
		String colNos = context.getRecord(outputNode).getValue("column_no");
		temp = colNos.split(",");
		for(int i = 0; i < temp.length; i++){
			if(temp[i].trim().equals("")) continue;
			TxnContext txnContext = new TxnContext();
			txnContext.getRecord("primary-key").setValue("column_no", temp[i]);
			callService("30403004", txnContext);
			DataBus db = new DataBus();
			db.setValue("table_no", txnContext.getRecord("record").getValue("table_no"));
			db.setValue("column_no", txnContext.getRecord("record").getValue("column_no"));
			db.setValue("column_name", txnContext.getRecord("record").getValue("column_name"));
			db.setValue("column_name_cn", txnContext.getRecord("record").getValue("column_name_cn"));
			context.addRecord("col-info", db);
		}
		*/
	}
	
	/** 删除视图信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102005( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
//		String sys_db_view_id = context.getRecord("primary-key").getValue("sys_db_view_id");
//		TxnContext txnContext = new TxnContext();
//		txnContext.getRecord("record").setValue("sys_db_view_id", sys_db_view_id);
//		int i = table.executeFunction("haveAssigned", txnContext, "select-key", "record");
//		if(i > 0){
//			throw new TxnDataException("999999","该服务已被使用，不能删除！");
//		}
		callService("52102104", context);
		
		// 删除记录的主键列表 VoSysSvrServicePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );

		Recordset rs = context.getRecordset(inputNode);
		StringBuffer name = new StringBuffer();
		for (int i = 0; rs != null && i < rs.size(); i++) {			
			if(name.length()>0){
				name.append(",");
			}
			name.append(rs.get(i).getValue("view_name"));
		}
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "删除数据库视图，视图名称："+name);
	}
	
	/** 修改视图校验
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102006( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );		
		table.executeFunction("haveAssigned", context, inputNode, outputNode);
	}	
	
	/** 查询服务最新代码
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn52102010( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_MAX_DB_ORDER, context, inputNode, outputNode );
	}

	
}
