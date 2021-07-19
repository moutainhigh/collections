package com.gwssi.dw.runmgr.db.txn;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.dw.runmgr.db.DbConstant;
import com.gwssi.dw.runmgr.db.TxnBaseDbMgr;
import com.gwssi.dw.runmgr.services.common.Constants;
public class TxnSysDbConfig extends TxnBaseDbMgr
{
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_db_config";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "getDBConfigList";
	
	// ��ѯ�б�
	private static final String SELF_ROWSET_FUNCTION = "getUserConfigList";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_db_config";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_db_config";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_db_config";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_db_config";
	
	//��ѯ�û��ķ���
	private static final String ISEXIST_CONFIGNAME = "is exist sys_db_config";
	
	// ɾ����¼
	private static final String DELETE_USERS_FUNCTION = "delete user sys_db_config";

	// ��ѯ��¼
	private static final String SELECT_FUNCTION_BY_CONFIGID = "select one sys_db_config by config_id";

	// ��ѯ������ü�¼
	private static final String ROWSET_SELFCONFIGL_FUNCTION = "select self config list";
	
	// ��������Ϊ��ͼ����
	private static final String CONFIG_TYPE_1="01";
	// ��������Ϊ�������
	private static final String CONFIG_TYPE_2="02";
	/**
	 * ���캯��
	 */
	public TxnSysDbConfig()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��ͼ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103001( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		if(context.getRecord("select-key").getValue("showall") != null){ 
			context.getRecord(inputNode).setValue("sys_db_user_id",
					context.getRecord("select-key").getValue("sys_db_user_id"));
			Attribute.setPageRow(context, outputNode, -1);
		}
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysSvrConfig result[] = context.getSysSvrConfigs( outputNode );
	}
	
	/** �޸���ͼ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103002( TxnContext context ) throws TxnException
	{
		//����ҳ�����б�������ݽ��н���
		try {
			context.getRecord(inputNode).setValue("sysParams", URLDecoder.decode(context.getRecord(inputNode).getValue("sysParams"),"UTF-8"));
			context.getRecord(inputNode).setValue("query_sql", URLDecoder.decode(context.getRecord(inputNode).getValue("query_sql"),"UTF-8"));
//			context.getRecord(inputNode).setValue("userParams",URLDecoder.decode(context.getRecord(inputNode).getValue("userParams"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		// ɾ�����õ����в���
		DataBus param = new DataBus();
		param.setValue("sys_db_config_id", context.getRecord(inputNode).getValue("sys_db_config_id"));
		context.setValue("select-key", param);
		callService("52103106", context);
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysSvrConfig sys_svr_config = context.getSysSvrConfig( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		
		String config_name = context.getRecord(inputNode).getValue("config_name");
		String login_name = context.getRecord(inputNode).getValue("login_name");
		String sys_db_config_id = context.getRecord(inputNode).getValue("sys_db_config_id");
		String str = context.getRecord(inputNode).getValue("sysParams");
		String sql = context.getRecord(inputNode).getValue("query_sql");
		String user_type = context.getRecord(inputNode).getValue("user_type");
		
		JSONArray connJSONArray = JSONArray.fromObject(str);
		
		if(connJSONArray != null){
			context.remove(inputNode);
			boolean flag = false;
			for(Iterator it = connJSONArray.iterator(); it.hasNext();){
				JSONObject jObj = (JSONObject) it.next();
				
				DataBus db = new DataBus();
				db.setValue("sys_db_config_id", sys_db_config_id);
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
				db.setValue("param_type", "0");
				table.executeSelect("SELECT MAX(param_order) as param_order FROM sys_db_config_param", context, "max-order");
				String max = context.getRecord("max-order").getValue("param_order");
				if(max.trim().equals(""))
					max = "0";
				db.setValue("param_order", ""+(Integer.parseInt(max) + 1 ));
				db.setValue("param_text", jObj.getString("paramText"));
				context.addRecord(inputNode, db);
				flag = true;
			}
			if(flag)	
			    callService("52103103", context);
		}
		createOrUpdateView(null,null,login_name,config_name,sql,user_type);
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "�޸����ݿ���ͼ���ã��������ƣ�"+config_name);
	}
	
	/** ������ͼ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103003( TxnContext context ) throws TxnException
	{
		//����ҳ�����б�������ݽ��н���
		try {
			context.getRecord(inputNode).setValue("sysParams", URLDecoder.decode(context.getRecord(inputNode).getValue("sysParams"),"UTF-8"));
			context.getRecord(inputNode).setValue("query_sql", URLDecoder.decode(context.getRecord(inputNode).getValue("query_sql"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		//У�����������Ƿ��ظ�
		DataBus selectKey = new DataBus();
		selectKey.setValue("sys_db_user_id", context.getRecord(inputNode).getValue("sys_db_user_id"));
		selectKey.setValue("config_name", context.getRecord(inputNode).getValue("config_name"));
		context.setValue("select-key", selectKey);
		callService("52103013", context);
		//У�����������Ƿ��ظ����
		
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		context.getRecord(inputNode).setValue("create_date", new SimpleDateFormat(Constants.DB_DATE_FORMAT).format(new Date()));
		String uId = context.getOperData().getValue("oper-name");
		context.getRecord(inputNode).setValue("create_by", uId);
		
		table.executeSelect("SELECT MAX(config_order) as config_order FROM sys_db_config", context, "max-order");
		String max = context.getRecord("max-order").getValue("config_order");
		if(max.trim().equals(""))
			max = "0";
		int next = Integer.parseInt(max) + 1;
		context.getRecord(inputNode).setValue("config_order", "" + next);
		context.getRecord(inputNode).setValue("config_type", CONFIG_TYPE_1);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		
		String config_name = context.getRecord(inputNode).getValue("config_name");
		String login_name = context.getRecord(inputNode).getValue("login_name");
		String grant_table = context.getRecord(inputNode).getValue("grant_table");
		String selected_table = context.getRecord(inputNode).getValue("table_no");
		String sys_db_config_id = context.getRecord(inputNode).getValue("sys_db_config_id");
		String sys_db_user_id = context.getRecord(inputNode).getValue("sys_db_user_id");
		String str = context.getRecord(inputNode).getValue("sysParams");
		String sql = context.getRecord(inputNode).getValue("query_sql");
		String user_type = context.getRecord(inputNode).getValue("user_type");
		
		JSONArray connJSONArray = JSONArray.fromObject(str);
		
		if(connJSONArray != null){
			context.remove(inputNode);
			boolean flag = false;
			for(Iterator it = connJSONArray.iterator(); it.hasNext();){
				JSONObject jObj = (JSONObject) it.next();
				
				DataBus db = new DataBus();
				db.setValue("sys_db_config_id", sys_db_config_id);
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
				db.setValue("param_type", "0");
				table.executeSelect("SELECT MAX(param_order) as param_order FROM sys_db_config_param", context, "max-order");
				max = context.getRecord("max-order").getValue("param_order");
				if(max.trim().equals(""))
					max = "0";
				db.setValue("param_order", ""+(Integer.parseInt(max) + 1 ));
				db.setValue("param_text", jObj.getString("paramText"));
				context.addRecord(inputNode, db);
				flag = true;
			}
			if(flag){	
			    callService("52103103", context);
			    context.remove(inputNode);
			}
		}
		String[] result = calculateTables(grant_table,selected_table,null);		
		context.getRecord(inputNode).setValue("grant_table", result[0]);	
		context.getRecord(inputNode).setValue("sys_db_user_id", sys_db_user_id);
		context.getRecord(inputNode).setValue("sys_db_config_id", sys_db_config_id);
		context.getRecord(inputNode).setValue("config_name", config_name);
		context.getRecord(inputNode).setValue("table_no", selected_table);
		table.executeFunction( "updateUserTables", context, inputNode, outputNode );

		Recordset rs = null;
		if (result[1] != null && !"".equals(result[1])) {
			context.getRecord(inputNode).setValue("table_Ids", result[1]);
			callService("52103012", context);
			try {
				rs = context.getRecordset("table-info");
			} catch (TxnException e) {
				log.error("", e);
			}
		}
		createOrUpdateView(rs,null,login_name,config_name,sql,user_type);
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "�������ݿ���ͼ���ã��������ƣ�"+config_name);
	}
	
	private String formatSQL(String sql){
		String sb = sql.replaceAll("'", "''");
		
		return sb;
	}	
	
	/** ��ѯ��ͼ���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103004( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		try{
			table.executeFunction( SELECT_FUNCTION, context, inputNode, "config-inf" );
			context.getRecord("select-key").setValue("sys_db_config_id", context.getRecord("config-inf").getValue("sys_db_config_id"));
			callService("52103101", context);
			
			Recordset rs = context.getRecordset("config-param");
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
				
				db.setValue("params", jsonObj.toString());
			}
		}catch(Exception e){
			log.info(e.getMessage());
		}
		
		
		DataBus db = new DataBus();
		db.setValue("sys_db_view_id", context.getRecord(inputNode).getValue("sys_db_view_id"));
		context.addRecord("select-key", db);
		callService("52102004", context);
		context.remove("select-key");
	}
	
	/** ɾ����ͼ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	
	public void txn52103005( TxnContext context ) throws TxnException
	{
		deleteConfig(context,"01");
	}

	/**
	 * 
	 * @param context
	 * @param flag ɾ����ʶ��01��ʾɾ����ͼ���ã�02��ʾɾ���������
	 * @throws TxnException
	 * @throws TxnErrorException
	 */
	private void deleteConfig(TxnContext context,String flag) throws TxnException, TxnErrorException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		String configIds = context.getRecord(inputNode).getValue("sys_db_config_id");
		String configNames = context.getRecord(inputNode).getValue("config_name");
		String login_name = context.getRecord(inputNode).getValue("login_name");
		String user_type = context.getRecord(inputNode).getValue("user_type");
		String grant_table = context.getRecord(inputNode).getValue("grant_table");
		String old_table = context.getRecord(inputNode).getValue("old_table");
		
		
		Map map = executeDBSql(user_type,
				"select username from v$session where username ='" + login_name.toUpperCase()
						+ "'", "��ѯ�û��Ƿ������������ݿ�ʧ��,������������ԭ��!");
		if (map != null && (String)map.get("USERNAME") !=null&&!"".equals((String)map.get("USERNAME"))) {
			throw new TxnErrorException("99999", "�û��Ѿ����ӵ����ݿ⣬����ɾ����");
		}
		
		String[] config_id = configIds.split(",");
		if (config_id != null && config_id.length > 0) {
			for (int i = 0; i < config_id.length; i++) {
				String tmpStr = config_id[i];
				if (tmpStr != null && !"".equals(tmpStr)) {
					DataBus db = new DataBus();
					db.setValue("sys_db_config_id", tmpStr);
					context.addRecord("select-key", db);
				}
			}
			if ("01".equals(flag)) {
				callService("52103106", context);
			}
			table.executeFunction(DELETE_FUNCTION, context, "select-key",
					outputNode);
		}
		
		String[] result = calculateTables(grant_table,null,old_table);
		context.getRecord(inputNode).setValue("grant_table", result[0]);
		table.executeFunction( "updateUserTables", context, inputNode, outputNode );
		
		Recordset rs = null;
		if (result[2] != null && !"".equals(result[2])) {
			context.getRecord(inputNode).setValue("table_Ids", result[2]);
			callService("52103012", context);
			try {
				rs = context.getRecordset("table-info");
			} catch (TxnException e) {
				log.error("", e);
			}
		}		
		
		dropView(login_name,configNames,rs,user_type);
		if("01".equals(flag)){
			context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "ɾ�����ݿ���ͼ���ã��������ƣ�"+configNames);
		}else{
			context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "ɾ�������ͼ���ã��������ƣ�"+configNames);
		}
	}
	
	/*
	private void dropView(BaseTable table,String login_name, String[] configNames, String tables, String userId,String user_type) throws TxnException
	{
		
		List sql = new ArrayList();
		List callSql = new ArrayList();
		for(int m=0;m<configNames.length;m++){
			if (configNames[m] != null && !"".equals(configNames[m])) {
				sql.add("drop view " + login_name + "." + configNames[m]);
				callSql.add("{ call DBMS_FGA.drop_policy ( ?, ?, ?) }&"+login_name+"&"+configNames[m]+"&"+configNames[m]+"_access");
			}
		}
		String[] tableId = tables.split(",");
		Recordset rs = null;
		TxnContext cont = new TxnContext();
		try {
		
		    if(tableId!=null&&tableId.length>0){
		        String tableSql = "select table_name from sys_table_semantic where ";
		    	for(int l = 0;l<tableId.length;l++){
		    	    if(l>0){
		    	    	tableSql += " or ";
		    	    }
		    	    tableSql += " table_no='"+tableId[l]+"'";   
		    	}
		    	table.executeRowset(tableSql, cont, "tmpRecord");
		    }

			rs = cont.getRecordset("tmpRecord");
		} catch (TxnException ex) {
			log.debug("�������" + ex.getErrCode());
			if (ex.getErrCode().compareTo("SQL000") != 0) {
				throw new TxnErrorException("99999", "ִ�в�ѯsql������");
			}

		}
		
		if (rs != null && rs.size() > 0) {
			for (int j = 0; j < rs.size(); j++) {
				sql.add(getRevokeSql(rs.get(j).getValue("table_name"), user_type, login_name));
			}
		}
		
		DBOperation operation = DBOperationFactory.createOperation();
        try{
        	if ("0".equals(user_type)) {
            	operation.executeCall(callSql, true, "2");
            }else if("1".equals(user_type)) {
            	operation.executeCall(callSql, true, "3");         	
            }
        }catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		try {			
			if ("0".equals(user_type)) {
				operation.execute(sql, true, "2");
			} else if ("1".equals(user_type)) {
				operation.execute(sql, true, "3");
			}
		} catch (DBException e) {
			e.printStackTrace();
			throw new TxnErrorException("99999", "ִ��ɾ�����ݿ���ͼsql������");
		}
		
	}
	*/
    public void txn52103006( TxnContext context ) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		log.debug("tableObj = " + table);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( SELF_ROWSET_FUNCTION, context, inputNode, outputNode );    	
    }
    
    
	/** ɾ���û����е���ͼ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
    /*
	public void txn52103007( TxnContext context ) throws TxnException
	{
		String user_type = context.getRecord(inputNode).getValue("user_type");
		String login_name = context.getRecord(inputNode).getValue("login_name");
		Map map = executeDBSql(user_type,
				"select username from v$session where username ='" + login_name.toUpperCase()
						+ "'", "��ѯ�û��Ƿ������������ݿ�ʧ��,������������ԭ��!");
		if (map != null && (String)map.get("USERNAME") !=null&&!"".equals((String)map.get("USERNAME"))) {
			throw new TxnErrorException("99999", "�û��Ѿ����ӵ����ݿ⣬����ɾ����");
		}
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		//��ѯ�û�������ͼ
		callService("52103001", context);
		Recordset rs = context.getRecordset("record");

		TxnContext cont = new TxnContext();
		while(rs.hasNext()){
			DataBus d = (DataBus)rs.next();
			
			DataBus db = new DataBus();
			db.setValue("sys_db_config_id", d.getValue("sys_db_config_id"));
			cont.addRecord("select-key", db);						
		}
		callService("52103106", cont);
		table.executeFunction( DELETE_USERS_FUNCTION, context, inputNode, outputNode );
		
		dropUsersAllView(table,context,user_type);
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "ɾ�����ݿ��û������е����ݿ���ͼ���ã��û�����"+login_name);
		// ɾ����¼�������б� VoSysSvrConfigPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		
	}
	
	private void dropUsersAllView(BaseTable table,TxnContext cont,String user_type) throws TxnException
	{
		String userId = cont.getRecord("select-key").getValue("sys_db_user_id");
		Recordset rs = null;
		try {
			table.executeRowset("SELECT c.config_name, s.table_no, t.table_name, u.login_name FROM sys_db_config c,sys_db_view s,sys_db_user u,sys_table_semantic t where c.sys_db_user_id='"+userId+"' and c.sys_db_view_id = s.sys_db_view_id and c.sys_db_user_id = u.sys_db_user_id and s.table_no = t.table_no" , cont, "config-inf");
			rs = cont.getRecordset("config-inf");
		} catch (TxnException ex) {
			if (ex.getErrCode().compareTo("SQL000") != 0) {
				throw new TxnErrorException("99999", "ִ�в�ѯsql������");
			}

		}
		if(rs!=null&&rs.size()>0){
			List sql = new ArrayList();
			List callSql = new ArrayList();
			String login_name = rs.get(0).getValue("login_name");
			StringBuffer str = new StringBuffer();
		    for (int i = 0; rs != null && i < rs.size(); i++) {
				DataBus db = rs.get(i);
				String config_name = db.getValue("config_name");
				String table_no = db.getValue("table_name");
				String[] tablesId = table_no.split(",");
				for(int j = 0;j<tablesId.length;j++){
				    if(str.indexOf(tablesId[j])<0){
					    if(str.length()>0){
							str.append(",");
						}
					    str.append(tablesId[j]);
				    }
				}
				sql.add("drop view "+login_name+"."+config_name);
				callSql.add("{ call DBMS_FGA.drop_policy ( ?, ?, ?) }&"+login_name+"&"+config_name+"&"+config_name+"_access");
			}
		    String[] tables = str.toString().split(",");
		    for(int m = 0;m<tables.length;m++){
		    	sql.add(getRevokeSql(tables[m],user_type,login_name));
		    }
		    DBOperation operation = DBOperationFactory.createOperation();
		    
	        try{
	        	if ("0".equals(user_type)) {
	            	operation.executeCall(callSql, true, "2");
	            }else if("1".equals(user_type)) {
	            	operation.executeCall(callSql, true, "3");           	
	            }
	        }catch (DBException e) {
				e.printStackTrace();
			}
	        
			try {
				if ("0".equals(user_type)) {
					operation.execute(sql, true, "2");
				} else if ("1".equals(user_type)) {
					operation.execute(sql, true, "3");
				}
			} catch (DBException e) {
				e.printStackTrace();
				throw new TxnErrorException("99999","ִ��ɾ�����ݿ���ͼsql������");	
			}
		}		
	}	
	*/
	/** �鿴��ͼ������־
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103008( TxnContext context ) throws TxnException
	{
		String user_type = context.getRecord(inputNode).getValue("user_type");
		if("0".equals(user_type)){
			BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
			table.executeFunction( "getViewAuditLog", context, inputNode, outputNode );
		}else if("1".equals(user_type)){
			BaseTable table = TableFactory.getInstance().getTableObject( this, "reg_bus_ent_wbcjk" );
			table.executeFunction( "getViewAuditLog", context, inputNode, outputNode );
		}
		
		Recordset rs = context.getRecordset(outputNode);
		StringBuffer logStr = null;
		for(int i=0;rs!=null&&i<rs.size();i++){
			DataBus db = rs.get(i);
			logStr = new StringBuffer();
			logStr.append("���ݿ��û� ").append(db.getValue("db_user"))
			   .append(" �� ").append(db.getValue("timestamp"))
			   .append(" �� ").append(db.getValue("object_schema"))
			   .append(" �û��µ� ").append(db.getValue("object_name"))
			   .append(" ��ͼ�����˲�ѯ������ʹ�õ����Ϊ��").append(db.getValue("sql_text"));
			db.setValue("log", logStr.toString());
		}
	}
	/*
	public void createView(String tblPart,String loginName,String cfgname,String sql,String user_type) throws TxnException{		
		DBOperation operation = DBOperationFactory.createOperation();
		try {
			if(tblPart!=null&&!"".equals(tblPart)&&sql!=null){
				
				if ("0".equals(user_type)) {
					List grantList = new ArrayList();
					String[] tableNames = tblPart.split(",");
					for (int i = 0; i < tableNames.length; i++) {
						if (tableNames[i] != null && !"".equals(tableNames[i])) {
							log.debug("��Ȩsql="+"grant select on " + DbConstant.ZXK_USER+"."+tableNames[i]
									+ " to " + loginName);
							grantList.add("grant select on " + DbConstant.ZXK_USER+"."+tableNames[i]
									+ " to " + loginName);
						}
					}
					StringBuffer str = new StringBuffer("create or replace view ");
					str.append(loginName).append(".").append(cfgname)
							.append(" as ").append(sql);
					log.debug("������ͼsql="+str);
					grantList.add(str.toString());
					operation.execute(grantList, true, "2");
					
				} else if ("1".equals(user_type)) {
					List grantList = new ArrayList();
					String[] tableNames = tblPart.split(",");
					for (int i = 0; i < tableNames.length; i++) {
						if (tableNames[i] != null && !"".equals(tableNames[i])) {
							grantList.add("grant select on " + DbConstant.GXK_USER+"."+tableNames[i]
									+ " to " + loginName);
						}
					}
					StringBuffer str = new StringBuffer("create or replace view ");
					str.append(loginName).append(".").append(cfgname)
							.append(" as ").append(sql);
					grantList.add(str.toString());
					operation.execute(grantList, true, "3");
				}
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TxnErrorException("99999","ִ�д������ݿ���ͼsql������");
		}
        try{
        	if ("0".equals(user_type)) {
            	String[] params = {loginName,cfgname,cfgname+"_access"};
            	operation.executeCall("{ call DBMS_FGA.add_policy ( ?, ?, ?) }", 
            			params, true, "2");
            }else if("1".equals(user_type)) {
            	String[] params = {loginName,cfgname,cfgname+"_access"};
            	operation.executeCall("{ call DBMS_FGA.add_policy ( ?, ?, ?) }", 
            			params, true, "3");            	
            }
        }catch (DBException e) {
			e.printStackTrace();
		}
	}	
	*/
	/** ѡ����ͼ���ý���ɾ��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103009( TxnContext context ) throws TxnException
	{
		String config_type=context.getRecord(inputNode).getValue("config_type");
		deleteConfig(context,config_type);
	}
	
	public void txn52103010( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "selectViewNotOfUser", context, inputNode, outputNode );
	}	
	/**
	 * ��ѯ��������б�
	 * @param context
	 * @throws TxnException
	 */
	public void txn52103011( TxnContext context ) throws TxnException
	{
		// txn52101004
		context.getRecord(outputNode).setValue("sys_db_user_id",
				context.getRecord(inputNode).getValue("sys_db_user_id"));
		callService("52101004", context);
		context.getRecord(inputNode).setValue("login_name", context.getRecord(outputNode).getValue("login_name"));
		context.getRecord(inputNode).setValue("user_name", context.getRecord(outputNode).getValue("user_name"));
		context.getRecord(inputNode).setValue("user_type", context.getRecord(outputNode).getValue("user_type"));
		context.getRecord(inputNode).setValue("grant_table", context.getRecord(outputNode).getValue("grant_table"));
		context.remove(outputNode);
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction(ROWSET_SELFCONFIGL_FUNCTION, context, inputNode,
				outputNode);
	}
	/**
	 * ���ݱ��ID��ѯ�����Ϣ
	 * @param context
	 * @throws TxnException
	 */
	public void txn52103012( TxnContext context ) throws TxnException
	{
		String tableIds = context.getRecord(inputNode).getValue("table_Ids");
		if (tableIds != null && !"".equals(tableIds)) {
			BaseTable table = TableFactory.getInstance().getTableObject(this,
					TABLE_NAME);
			Attribute.setPageRow(context, outputNode, -1);
			table.executeFunction("queryTablesOfTableIds", context, inputNode,
					"table-info");
		}
	}
	/**
	 * ������������Ƿ��ظ�
	 * @param context
	 * @throws TxnException
	 */
	public void txn52103013( TxnContext context ) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		int i = table.executeFunction(ISEXIST_CONFIGNAME, context, inputNode, "tmpRecord");

		if (i > 0) {
			if (context.getRecord("tmpRecord") != null
					&& context.getRecord("tmpRecord").getValue(
							"sys_db_config_id") != null
					&& !"".equals(context.getRecord("tmpRecord").getValue(
							"sys_db_config_id"))) {
				throw new TxnDataException("999999", "�����������Ѵ��ڣ�");
			}
		}			
	}
	
	/** ���������ͼ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103014( TxnContext context ) throws TxnException
	{
		//����ҳ�����б�������ݽ��н���
		try {
			context.getRecord(inputNode).setValue("query_sql", URLDecoder.decode(context.getRecord(inputNode).getValue("query_sql"),"UTF-8"));
			context.getRecord(inputNode).setValue("transfer_sql", URLDecoder.decode(context.getRecord(inputNode).getValue("transfer_sql"),"UTF-8"));
			context.getRecord(inputNode).setValue("alias_column", URLDecoder.decode(context.getRecord(inputNode).getValue("alias_column"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		context.getRecord(inputNode).setValue("create_date", new SimpleDateFormat(Constants.DB_DATE_FORMAT).format(new Date()));
		String uId = context.getOperData().getValue("oper-name");
		context.getRecord(inputNode).setValue("create_by", uId);
		context.getRecord(inputNode).setValue("sys_db_view_id", "");
		
		table.executeSelect("SELECT MAX(config_order) as config_order FROM sys_db_config", context, "max-order");
		String max = context.getRecord("max-order").getValue("config_order");
		if(max.trim().equals(""))
			max = "0";
		int next = Integer.parseInt(max) + 1;
		context.getRecord(inputNode).setValue("config_order", "" + next);
		context.getRecord(inputNode).setValue("config_type", CONFIG_TYPE_2);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		
		String config_name = context.getRecord(inputNode).getValue("config_name");
		String login_name = context.getRecord(inputNode).getValue("login_name");
		String grant_table = context.getRecord(inputNode).getValue("grant_table");
		String selected_table = context.getRecord(inputNode).getValue("permit_column");
		String sql = context.getRecord(inputNode).getValue("transfer_sql");
		String user_type = context.getRecord(inputNode).getValue("user_type");
		String[] result = calculateTables(grant_table,selected_table,null);		
		context.getRecord(inputNode).setValue("grant_table", result[0]);
		table.executeFunction( "updateUserTables", context, inputNode, outputNode );

		Recordset rs = null;
		if (result[1] != null && !"".equals(result[1])) {
			context.getRecord(inputNode).setValue("table_Ids", result[1]);
			callService("52103012", context);
			try {
				rs = context.getRecordset("table-info");
			} catch (TxnException e) {
				log.error("", e);
			}
		}
		createOrUpdateView(rs,null,login_name,config_name,sql,user_type);
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "�������ݿ�������ã��������ƣ�"+config_name);
	}
	
	/** ��ѯ�����ͼ������Ϣ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103015( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION_BY_CONFIGID, context, inputNode, "config-inf" );
		String table_Ids = context.getRecord("config-inf").getValue("permit_column");
		DataBus db = new DataBus();
		db.setValue("table_Ids", table_Ids);
		context.setValue("record", db);
		callService("52103012", context);
	}
	
	/** �޸������ͼ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103016( TxnContext context ) throws TxnException
	{
		//����ҳ�����б�������ݽ��н���
		try {
			context.getRecord(inputNode).setValue("query_sql", URLDecoder.decode(context.getRecord(inputNode).getValue("query_sql"),"UTF-8"));
			context.getRecord(inputNode).setValue("transfer_sql", URLDecoder.decode(context.getRecord(inputNode).getValue("transfer_sql"),"UTF-8"));
			context.getRecord(inputNode).setValue("alias_column", URLDecoder.decode(context.getRecord(inputNode).getValue("alias_column"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 	
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		String config_name = context.getRecord(inputNode).getValue("config_name");
		String login_name = context.getRecord(inputNode).getValue("login_name");
		String grant_table = context.getRecord(inputNode).getValue("grant_table");
		String selected_table = context.getRecord(inputNode).getValue("permit_column");
		String old_table = context.getRecord(inputNode).getValue("old_table");
		String sql = context.getRecord(inputNode).getValue("transfer_sql");
		String user_type = context.getRecord(inputNode).getValue("user_type");
		
		String[] result = calculateTables(grant_table,selected_table,old_table);	
		// ��¼�޸ĺ����ӵ���Ȩ���б�
		Recordset addRs = null;
		// ��¼�޸ĺ�ɾ������Ȩ���б�
		Recordset subRs = null;
		
		if(!grant_table.equals(result[0])){			
			context.getRecord(inputNode).setValue("grant_table", result[0]);
			table.executeFunction( "updateUserTables", context, inputNode, outputNode );
		}
		if(result[1]!=null&&!"".equals(result[1])){
			context.getRecord(inputNode).setValue("table_Ids", result[1]);
			callService("52103012", context);
			try {
				addRs = context.getRecordset("table-info");
			} catch (TxnException e) {
				log.error("", e);
			}
			log.debug("add-context="+context);
			context.remove("table-info");
		}
		if(result[2]!=null&&!"".equals(result[2])){
			context.getRecord(inputNode).setValue("table_Ids", result[2]);
			callService("52103012", context);
			try {
				subRs = context.getRecordset("table-info");
			} catch (TxnException e) {
				log.error("", e);
			}
			log.debug("sub-context="+context);
			context.remove("table-info");			
		}
		createOrUpdateView(addRs,subRs,login_name,config_name,sql,user_type);
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "�޸����ݿ�������ã��������ƣ�"+config_name);
	}
	
	/** ɾ�������ͼ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103017( TxnContext context ) throws TxnException
	{
		deleteConfig(context,"02");
	}
	
	private void dropView(String login_name, String configNames, Recordset rs, String user_type) throws TxnException
	{
		List sql = new ArrayList();
		List callSql = new ArrayList();
		String[] configArray = configNames.split(",");
		for(int m=0;m<configArray.length;m++){
			if (configArray[m] != null && !"".equals(configArray[m])) {
				sql.add("drop view " + login_name + "." + configArray[m]);
				callSql.add("{ call DBMS_FGA.drop_policy ( ?, ?, ?) }&"+login_name+"&"+configArray[m]+"&"+configArray[m]);
			}
		}
		
		if (rs != null && rs.size() > 0) {
			for (int j = 0; j < rs.size(); j++) {
				sql.add(getRevokeSql(rs.get(j).getValue("table_name"), user_type, login_name));
			}
		}
		
		DBOperation operation = DBOperationFactory.createOperation();
        try{
        	if ("0".equals(user_type)) {
            	operation.executeCall(callSql, true, "2");
            }else if("1".equals(user_type)) {
            	operation.executeCall(callSql, true, "3");         	
            }
        }catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		try {			
			if ("0".equals(user_type)) {
				operation.execute(sql, true, "2");
			} else if ("1".equals(user_type)) {
				operation.execute(sql, true, "3");
			}
		} catch (DBException e) {
			e.printStackTrace();
			throw new TxnErrorException("99999", "ִ��ɾ�����ݿ���ͼsql������");
		}
		
	}
	
    private String getRevokeSql(String tableId,String user_type,String login_name){
    	String sql = null;
		if("0".equals(user_type)){
		    sql="revoke select on "
				+ DbConstant.ZXK_USER + "." + tableId + " from "
				+ login_name;
		}else if("1".equals(user_type)){
			sql = "revoke select on "
					+ DbConstant.GXK_USER + "." + tableId + " from "
					+ login_name;							
		} 
		return sql;
    }

	/**
	 * 
	 * @param addRs
	 * @param subRs
	 * @param loginName
	 * @param cfgname
	 * @param sql
	 * @param user_type
	 * @throws TxnException
	 */
	private void createOrUpdateView(Recordset addRs,Recordset subRs, String loginName, String cfgname, String sql, String user_type) throws TxnException
	{
		DBOperation operation = DBOperationFactory.createOperation();
		try {
				try {
					sql = URLDecoder.decode(sql,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					log.error("ת��������ͼsql����", e);
				}
				if ("0".equals(user_type)) {
					List grantList = new ArrayList();
					for (int i = 0; addRs!=null&&i < addRs.size(); i++) {
						String table_name = addRs.get(i).getValue("table_name");
						if (table_name != null && !"".equals(table_name)) {
							log.debug("��Ȩsql="+"grant select on " + DbConstant.ZXK_USER+"."+table_name
									+ " to " + loginName);
							grantList.add("grant select on " + DbConstant.ZXK_USER+"."+table_name
									+ " to " + loginName);
						}
					}
					for (int i = 0; subRs!=null&&i < subRs.size(); i++) {
						String table_name = subRs.get(i).getValue("table_name");
						if (table_name != null && !"".equals(table_name)) {
							log.debug("��Ȩsql="+"revoke select on " + DbConstant.ZXK_USER+"."+table_name
									+ " from " + loginName);
							grantList.add("revoke select on " + DbConstant.ZXK_USER+"."+table_name
									+ " from " + loginName);
						}
					}
					StringBuffer str = new StringBuffer("create or replace view ");
					str.append(loginName).append(".").append(cfgname)
							.append(" as ").append(sql);
					log.debug("������ͼsql="+str);
					grantList.add(str.toString());
					operation.execute(grantList, true, "2");
					
				} else if ("1".equals(user_type)) {
					List grantList = new ArrayList();
					for (int i = 0; addRs!=null&&i < addRs.size(); i++) {
						String table_name = addRs.get(i).getValue("table_name");
						if (table_name != null && !"".equals(table_name)) {
							grantList.add("grant select on " + DbConstant.GXK_USER+"."+table_name
									+ " to " + loginName);
						}
					}
					for (int i = 0; subRs!=null&&i < subRs.size(); i++) {
						String table_name = subRs.get(i).getValue("table_name");
						if (table_name != null && !"".equals(table_name)) {
							grantList.add("revoke select on " + DbConstant.GXK_USER+"."+table_name
									+ " from " + loginName);
						}
					}
					StringBuffer str = new StringBuffer("create or replace view ");
					str.append(loginName).append(".").append(cfgname)
							.append(" as ").append(sql);
					grantList.add(str.toString());
					operation.execute(grantList, true, "3");
				}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TxnErrorException("99999","ִ�д������ݿ���ͼsql������");
		}
        try{
        	if ("0".equals(user_type)) {
            	String[] params = {loginName,cfgname,cfgname};
            	operation.executeCall("{ call DBMS_FGA.add_policy ( ?, ?, ?) }", 
            			params, true, "2");
            }else if("1".equals(user_type)) {
            	String[] params = {loginName,cfgname,cfgname};
            	operation.executeCall("{ call DBMS_FGA.add_policy ( ?, ?, ?) }", 
            			params, true, "3");            	
            }
        }catch (DBException e) {
			e.printStackTrace();
		}
		
	}	
	/**
	 * �������ӡ��޸ġ�ɾ������û���Ȩ��ID�����ӵı�ID��ɾ���ı�ID
	 * @param grant_table
	 * @param selected_table
	 * @param old_table
	 * @return ����һ�����飬��һ��ֵ���û���Ȩ��ID�����ӵı�ID��ɾ���ı�ID
	 */
	private String[] calculateTables(String grant_table, String selected_table, String old_table)
	{
		grant_table = grant_table==null?"":grant_table;
		selected_table = selected_table==null?"":selected_table;
		old_table = old_table==null?"":old_table;
		
		String[] result =  new String[3];
		//�������ӵ����������ʱ��old_table�϶�Ϊ��
		if(old_table==null||"".equals(old_table)){
			if(selected_table!=null&&!"".equals(selected_table)){
				String[] selTables = selected_table.split(",");
				StringBuffer add = new StringBuffer();
				for(int i=0;i<selTables.length;i++){
					if(grant_table.indexOf(selTables[i])<0){
						if(add.length()>0){
							add.append(",");
						}						
						add.append(selTables[i]);						
					}
				}
				result[1] = add.toString();
				if(grant_table==null||"".equals(grant_table)){
					result[0] = selected_table;
				}else{
					result[0] = grant_table+","+selected_table;
				}
			}
		}
		//����ɾ���������ɾ��ʱ��selected_table�϶�Ϊ��
		else if(selected_table==null||"".equals(selected_table)){
			if(old_table!=null&&!"".equals(old_table)){
				String[] oldTables = old_table.split(",");
				StringBuffer sub = new StringBuffer();
				String table_ids = grant_table;
				for (int i = 0; i < oldTables.length; i++) {
					table_ids = table_ids.replaceFirst(oldTables[i], "").replaceAll(",,", ",");
					if(table_ids.endsWith(",")){
						table_ids = table_ids.substring(0, table_ids.length()-1);
					}
					if(table_ids.startsWith(",")){
						table_ids = table_ids.substring(1, table_ids.length());
					}
					if(table_ids.indexOf(oldTables[i])<0&&sub.indexOf(oldTables[i])<0){
						if(sub.length()>0){
							sub.append(",");
						}						
						sub.append(oldTables[i]);
					}
				}
				result[0]=table_ids;
				result[2]=sub.toString();
			}
		}
		//�����޸ĵ�������޸�ʱ��selected_table��old_table����Ϊ��
		else{
			if(!selected_table.equals(old_table)){
				// ��¼�޸ĺ����ӵ���Ȩ��id��
				StringBuffer add = new StringBuffer();
				// ��¼�޸ĺ�ɾ������Ȩ��id��
				StringBuffer sub = new StringBuffer();
				// �õ��޸ĺ�ɾ������Ȩ��ID
				String[] sleTableIds = selected_table.split(",");
				// �õ��޸ĺ���������Ȩ��ID
				for(int i=0;i<sleTableIds.length;i++){
					if(old_table.indexOf(sleTableIds[i])<0&&grant_table.indexOf(sleTableIds[i])<0){
						if(add.length()>0){
							add.append(",");
						}
						add.append(sleTableIds[i]);
					}
				}
				// �õ��޸ĺ�ɾ������Ȩ��ID
				String table_ids = grant_table;
				String[] oldTableIds = old_table.split(",");
				for(int i=0;i<oldTableIds.length;i++){
					table_ids = table_ids.replaceFirst(oldTableIds[i], "").replaceAll(",,", ",");
					if(table_ids.endsWith(",")){
						table_ids = table_ids.substring(0, table_ids.length()-1);
					}
					if(table_ids.startsWith(",")){
						table_ids = table_ids.substring(1, table_ids.length());
					}
					if(selected_table.indexOf(oldTableIds[i])<0&&table_ids.indexOf(oldTableIds[i])<0){
						if(sub.length()>0){
							sub.append(",");
						}
						sub.append(oldTableIds[i]);
					}
				}
				if(table_ids==null||"".equals(table_ids)){
					result[0]=selected_table;
				}else{
					result[0]=table_ids+","+selected_table;
				}
				result[1]=add.toString();
				result[2]=sub.toString();
			}else{
				result[0] = grant_table;
			}
		}
		log.debug("result = "+result[0]+"|"+result[1]+"|"+result[2]);
		return result;
	}
}
