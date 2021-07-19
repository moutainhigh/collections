package com.gwssi.dw.runmgr.services.txn;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.gwssi.dw.runmgr.services.vo.SysSvrConfigContext;

public class TxnSysSvrConfig extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysSvrConfig.class, SysSvrConfigContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_svr_config";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_svr_config list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_svr_config";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_svr_config";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_svr_config";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_svr_config";
	
	//查询用户的服务
	private static final String SELECT_USER_SVR_FUNCTION = "select sys_svr_service list by user";
	
	// 删除记录
	private static final String DELETE_USERS_FUNCTION = "delete user sys_svr_config";

	// 删除记录
	private static final String UPDATE_SQL_FUNCTION = "update one sys_svr_config sql";
	
	/**
	 * 构造函数
	 */
	public TxnSysSvrConfig()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询服务配置列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50203001( SysSvrConfigContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysSvrConfigSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryList", context, inputNode, outputNode );
		// 查询到的记录集 VoSysSvrConfig result[] = context.getSysSvrConfigs( outputNode );
	}
	
	/** 修改服务配置信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50203002( SysSvrConfigContext context ) throws TxnException
	{
		//将在页面层进行编码的数据进行解码
		try {
			context.getRecord(inputNode).setValue("sysParams", URLDecoder.decode(context.getRecord(inputNode).getValue("sysParams"),"UTF-8"));
			context.getRecord(inputNode).setValue("userParams",URLDecoder.decode(context.getRecord(inputNode).getValue("userParams"),"UTF-8"));
			context.getRecord(inputNode).setValue("permit_column_cn_array", URLDecoder.decode(context.getRecord(inputNode).getValue("permit_column_cn_array"),"UTF-8"));
			context.getRecord(inputNode).setValue("permit_column_en_array", URLDecoder.decode(context.getRecord(inputNode).getValue("permit_column_en_array"),"UTF-8"));
			//context.getRecord(inputNode).setValue("query_sql", formatSQL(URLDecoder.decode(context.getRecord(inputNode).getValue("query_sql"),"UTF-8")));
			context.getRecord(inputNode).setValue("column_alias", formatSQL(URLDecoder.decode(context.getRecord(inputNode).getValue("column_alias"),"UTF-8")));
			context.getRecord("select-key").setValue("user_name", formatSQL(URLDecoder.decode(context.getRecord("select-key").getValue("user_name"),"UTF-8")));
			context.getRecord("select-key").setValue("svr_name", formatSQL(URLDecoder.decode(context.getRecord("select-key").getValue("svr_name"),"UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		System.out.println("进入用户服务配置修改流程 \n"+ context);
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysSvrConfig sys_svr_config = context.getSysSvrConfig( inputNode );
		
		//DC2-jufeng-20120726
		//(1)加入服务的系统参数
		String query_sql = null;
		try{
			query_sql = URLDecoder.decode(context.getRecord(inputNode).getValue("query_sql"),"UTF-8");
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		System.out.println("要新存的query_sql 开始是：  "+query_sql);
		TxnContext t1 = new TxnContext();
		String svrId = context.getRecord(inputNode).getValue("sys_svr_service_id");
		System.out.println("输入的sverId是：  \n"+svrId);
		DataBus b3=new DataBus();
		b3.setValue("sys_svr_service_id", svrId);
		t1.addRecord("select-key", b3);
		int n = table.executeFunction("querySvrSysParams", t1, "select-key", "record");
	
		System.out.println("执行查询要复制的服务的结果是 \n"+n+" \n 查询结果的context是 ： \n"+t1);
		StringBuffer sb2 = new StringBuffer();
		if(n!=0){
			Recordset r1 = t1.getRecordset("record");
			if(r1!=null&&r1.size()>0){
				for(int j=0; j<r1.size(); j++){
					DataBus d1 = r1.get(j);
					sb2.append(" "+d1.getValue("param"));
				}
			}
		}
		
       System.out.println("查询服务对应的系统参数拼成最终的sql语句为： "+sb2);	
		//(2)加入orderby语句
		//先查询该服务用到的所有表
		TxnContext t2 = new TxnContext();
		StringBuffer sb3 = new StringBuffer(" select t.table_no  from sys_svr_service t where 1=1 " );
		sb3.append( " and t.sys_svr_service_id='")
		  .append(svrId)
		  .append("' ");
		table.executeSelect(sb3.toString(), t2, "record");
		DataBus b1 = t2.getRecord("record");
		String tableNos = b1.getValue("table_no");
		String tableNos2 = tableNos.replaceAll(",", "','");
		
		ArrayList list1 = new ArrayList();
		TxnContext t3 = new TxnContext();
		StringBuffer sb4 = new StringBuffer(" select table_code, table_primary_key from sys_rd_table where 1=1 ");
		sb4.append(" and table_no in ('")
			.append(tableNos2)
			.append("')");
		table.executeRowset(sb4.toString(), t3, "record");
	//	System.out.println("查询资源目录以获取主键信息 context is \n"+t3);
		Recordset r2 = t3.getRecordset("record");
		if(r2!=null&&r2.size()>0){
			for(int j=0; j<r2.size(); j++){
				DataBus b2 = r2.get(j);
				String table_code = b2.getValue("table_code");
				String table_primary_key = b2.getValue("table_primary_key");
				if(table_primary_key!=null&&!table_primary_key.equals("")){
					list1.add(table_code+"."+table_primary_key);
				}
			}
		}
		StringBuffer sb5 = new StringBuffer();
		if(list1.size()>0){
			for(int j=0; j<list1.size(); j++){
				if(j==0){
					sb5.append(" "+list1.get(j));
				}else{
					sb5.append(", "+list1.get(j));
				}
				
			}
		}
		String sb6 = null;
		if(sb5.length()>0){
			sb6 = " ORDER BY "+ sb5.toString();
		}
		
		System.out.println("最终的order by 语句为： "+ sb6);
		
		//组装最终的query_sql
		//1考虑服务的系统参数 sb2 
		//2考虑order by 语句
		//3本身sql 是否带有where语句
		
		//当没有服务的系统参数，只有order by 语句时
		if(sb2.length()==0&&sb6!=null){
			query_sql = query_sql + sb6;
		}
		//当没有服务的系统参数，也没有order by 语句时
		else if(sb2.length()==0&&sb6==null){
			
		}
		//当有服务的系统参数，没有order by 语句时
		else if(sb2.length()>0 && sb6==null ){
			if(query_sql.indexOf("WHERE")>-1){
				query_sql = query_sql + " AND "+ sb2;
			}else{
				query_sql = query_sql + " WHERE " + sb2;
			}
		}
		//当有服务的系统参数，也有order by 语句时
		else if(sb2.length()>0 && sb6!=null){
			if(query_sql.indexOf("WHERE")>-1){
				query_sql = query_sql + " AND "+ sb2 +sb6;
			}else{
				query_sql = query_sql + " WHERE " + sb2 +sb6;
			}
		}
		
		System.out.println("@@@@@@@@最终装配的query_sql是------------------》》》》   "+query_sql);
		
		context.getRecord(inputNode).setValue("query_sql", formatSQL(query_sql));
		
		//end of 
		 
			
		StringBuffer log = new StringBuffer();
		log.append("修改用户\"")
		   .append(context.getRecord("select-key").getValue("user_name"))
		   .append("\"的服务\"")
		   .append(context.getRecord("select-key").getValue("svr_name"))
		   .append("\"");
		setBizLog("修改共享服务配置：", context, log.toString());
		
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		
		context.getRecord("select-key").setValue("sys_svr_config_id", context.getRecord("record").getValue("sys_svr_config_id"));
		//删除所有相关配置参数
		callService("50206005", context);
		
		String cfgId = context.getRecord(inputNode).getValue("sys_svr_config_id");
		JSONArray sysParamJSONArray = JSONArray.fromObject(context.getRecord(inputNode).getValue("sysParams"));
		JSONArray userParamJSONArray = JSONArray.fromObject(context.getRecord(inputNode).getValue("userParams"));
		context.remove(inputNode);
		
		String max = "0";
		if(sysParamJSONArray != null){
			for(Iterator it = sysParamJSONArray.iterator(); it.hasNext();){
				JSONObject jObj = (JSONObject) it.next();
				String uuid = UuidGenerator.getUUID();
				DataBus db = new DataBus();
				db.setValue("sys_svr_config_param_id", uuid);
				db.setValue("sys_svr_config_id", cfgId);
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
				db.setValue("param_text", jObj.getString("paramText"));
				table.executeSelect("SELECT MAX(param_order) as param_order FROM sys_svr_config_param", context, "max-order");
				max = context.getRecord("max-order").getValue("param_order");
				if(max.trim().equals(""))
					max = "0";
				db.setValue("param_order", ""+(Integer.parseInt(max) + 1 ));
				db.setValue("param_type", "0");
				context.addRecord("record", db);
				callService("50206003", context);
				context.remove("record");
			}
		}
		
		if(userParamJSONArray != null){
			for(Iterator it = userParamJSONArray.iterator(); it.hasNext();){
				JSONObject jObj = (JSONObject) it.next();
				String uuid = UuidGenerator.getUUID();
				DataBus db = new DataBus();
				db.setValue("sys_svr_config_param_id", uuid);
				db.setValue("sys_svr_config_id", cfgId);
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
				db.setValue("param_text", jObj.getString("paramText"));
				table.executeSelect("SELECT MAX(param_order) as param_order FROM sys_svr_config_param", context, "max-order");
				max = context.getRecord("max-order").getValue("param_order");
				if(max.trim().equals(""))
					max = "0";
				db.setValue("param_order", ""+(Integer.parseInt(max) + 1 ));
				db.setValue("param_type", "1");
				context.addRecord("record", db);
				callService("50206003", context);
				context.remove("record");
			}
		}
	}
	
	/** 增加服务配置信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50203003( SysSvrConfigContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//将在页面层进行编码的数据进行解码
		try {
			context.getRecord(inputNode).setValue("sysParams", URLDecoder.decode(context.getRecord(inputNode).getValue("sysParams"),"UTF-8"));
			context.getRecord(inputNode).setValue("userParams",URLDecoder.decode(context.getRecord(inputNode).getValue("userParams"),"UTF-8"));
			context.getRecord(inputNode).setValue("permit_column_cn_array", URLDecoder.decode(context.getRecord(inputNode).getValue("permit_column_cn_array"),"UTF-8"));
			context.getRecord(inputNode).setValue("permit_column_en_array", URLDecoder.decode(context.getRecord(inputNode).getValue("permit_column_en_array"),"UTF-8"));
			//context.getRecord(inputNode).setValue("query_sql", formatSQL(URLDecoder.decode(context.getRecord(inputNode).getValue("query_sql"),"UTF-8")));
			context.getRecord(inputNode).setValue("column_alias", formatSQL(URLDecoder.decode(context.getRecord(inputNode).getValue("column_alias"),"UTF-8")));
			context.getRecord("select-key").setValue("user_name", formatSQL(URLDecoder.decode(context.getRecord("select-key").getValue("user_name"),"UTF-8")));
			context.getRecord("select-key").setValue("svr_name", formatSQL(URLDecoder.decode(context.getRecord("select-key").getValue("svr_name"),"UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		//DC2-jufeng-20120726
		//(1)加入服务的系统参数
		String query_sql = null;
		try{
			query_sql = URLDecoder.decode(context.getRecord(inputNode).getValue("query_sql"),"UTF-8");
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		System.out.println("要新存的query_sql 开始是：  "+query_sql);
		TxnContext t1 = new TxnContext();
		String svrId = context.getRecord(inputNode).getValue("sys_svr_service_id");
		System.out.println("输入的sverId是：  \n"+svrId);
		DataBus b3=new DataBus();
		b3.setValue("sys_svr_service_id", svrId);
		t1.addRecord("select-key", b3);
		int n = table.executeFunction("querySvrSysParams", t1, "select-key", "record");
	
		System.out.println("执行查询要复制的服务的结果是 \n"+n+" \n 查询结果的context是 ： \n"+t1);
		StringBuffer sb2 = new StringBuffer();
		if(n!=0){
			Recordset r1 = t1.getRecordset("record");
			if(r1!=null&&r1.size()>0){
				for(int j=0; j<r1.size(); j++){
					DataBus d1 = r1.get(j);
					sb2.append(" "+d1.getValue("param"));
				}
			}
		}
		
       System.out.println("查询服务对应的系统参数拼成最终的sql语句为： "+sb2);	
		//(2)加入orderby语句
		//先查询该服务用到的所有表
		TxnContext t2 = new TxnContext();
		StringBuffer sb3 = new StringBuffer(" select t.table_no  from sys_svr_service t where 1=1 " );
		sb3.append( " and t.sys_svr_service_id='")
		  .append(svrId)
		  .append("' ");
		table.executeSelect(sb3.toString(), t2, "record");
		DataBus b1 = t2.getRecord("record");
		String tableNos = b1.getValue("table_no");
		String tableNos2 = tableNos.replaceAll(",", "','");
		
		ArrayList list1 = new ArrayList();
		TxnContext t3 = new TxnContext();
		StringBuffer sb4 = new StringBuffer(" select table_code, table_primary_key from sys_rd_table where 1=1 ");
		sb4.append(" and table_no in ('")
			.append(tableNos2)
			.append("')");
		table.executeRowset(sb4.toString(), t3, "record");
	//	System.out.println("查询资源目录以获取主键信息 context is \n"+t3);
		Recordset r2 = t3.getRecordset("record");
		if(r2!=null&&r2.size()>0){
			for(int j=0; j<r2.size(); j++){
				DataBus b2 = r2.get(j);
				String table_code = b2.getValue("table_code");
				String table_primary_key = b2.getValue("table_primary_key");
				if(table_primary_key!=null&&!table_primary_key.equals("")){
					list1.add(table_code+"."+table_primary_key);
				}
			}
		}
		StringBuffer sb5 = new StringBuffer();
		if(list1.size()>0){
			for(int j=0; j<list1.size(); j++){
				if(j==0){
					sb5.append(" "+list1.get(j));
				}else{
					sb5.append(", "+list1.get(j));
				}
				
			}
		}
		String sb6 = null;
		if(sb5.length()>0){
			sb6 = " ORDER BY "+ sb5.toString();
		}
		
		System.out.println("最终的order by 语句为： "+ sb6);
		
		//组装最终的query_sql
		//1考虑服务的系统参数 sb2 
		//2考虑order by 语句
		//3本身sql 是否带有where语句
		
		//当没有服务的系统参数，只有order by 语句时
		if(sb2.length()==0&&sb6!=null){
			query_sql = query_sql + sb6;
		}
		//当没有服务的系统参数，也没有order by 语句时
		else if(sb2.length()==0&&sb6==null){
			
		}
		//当有服务的系统参数，没有order by 语句时
		else if(sb2.length()>0 && sb6==null ){
			if(query_sql.indexOf("WHERE")>-1){
				query_sql = query_sql + " AND "+ sb2;
			}else{
				query_sql = query_sql + " WHERE " + sb2;
			}
		}
		//当有服务的系统参数，也有order by 语句时
		else if(sb2.length()>0 && sb6!=null){
			if(query_sql.indexOf("WHERE")>-1){
				query_sql = query_sql + " AND "+ sb2 +sb6;
			}else{
				query_sql = query_sql + " WHERE " + sb2 +sb6;
			}
		}
		
		System.out.println("@@@@@@@@最终装配的query_sql是------------------》》》》   "+query_sql);
		
		context.getRecord(inputNode).setValue("query_sql", formatSQL(query_sql));
		
		//end of 
		 
		String cfgId = UuidGenerator.getUUID();
		context.getRecord("record").setValue("sys_svr_config_id", cfgId);
		context.getRecord("record").setValue("create_date", new SimpleDateFormat(Constants.DB_DATE_FORMAT).format(new Date()));
		String uId = context.getOperData().getValue("oper-name");
		context.getRecord("record").setValue("create_by", uId);
		
		table.executeSelect("SELECT MAX(config_order) as config_order FROM sys_svr_config", context, "max-order");
		String max = context.getRecord("max-order").getValue("config_order");
		if(max.trim().equals(""))
			max = "0";
		int next = Integer.parseInt(max) + 1;
		context.getRecord("record").setValue("config_order", "" + next);

		StringBuffer log = new StringBuffer();
		log.append("服务\"")
		   .append(context.getRecord("select-key").getValue("svr_name"))
		   .append("\"配置给用户\"")
		   .append(context.getRecord("select-key").getValue("user_name"))
		   .append("\"");
		setBizLog("新建共享服务配置：", context, log.toString());
		
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		
		JSONArray sysParamJSONArray = JSONArray.fromObject(context.getRecord(inputNode).getValue("sysParams"));
		JSONArray userParamJSONArray = JSONArray.fromObject(context.getRecord(inputNode).getValue("userParams"));
		context.remove(inputNode);
		
		if(sysParamJSONArray != null){
			for(Iterator it = sysParamJSONArray.iterator(); it.hasNext();){
				JSONObject jObj = (JSONObject) it.next();
				String uuid = UuidGenerator.getUUID();
				DataBus db = new DataBus();
				db.setValue("sys_svr_config_param_id", uuid);
				db.setValue("sys_svr_config_id", cfgId);
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
				db.setValue("param_text", jObj.getString("paramText"));
				table.executeSelect("SELECT MAX(param_order) as param_order FROM sys_svr_config_param", context, "max-order");
				max = context.getRecord("max-order").getValue("param_order");
				if(max.trim().equals(""))
					max = "0";
				db.setValue("param_order", ""+(Integer.parseInt(max) + 1 ));
				db.setValue("param_type", "0");
				context.addRecord("record", db);
				callService("50206003", context);
				context.remove("record");
			}
		}
		
		if(userParamJSONArray != null){
			for(Iterator it = userParamJSONArray.iterator(); it.hasNext();){
				JSONObject jObj = (JSONObject) it.next();
				String uuid = UuidGenerator.getUUID();
				DataBus db = new DataBus();
				db.setValue("sys_svr_config_param_id", uuid);
				db.setValue("sys_svr_config_id", cfgId);
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
				db.setValue("param_text", jObj.getString("paramText"));
				table.executeSelect("SELECT MAX(param_order) as param_order FROM sys_svr_config_param", context, "max-order");
				max = context.getRecord("max-order").getValue("param_order");
				if(max.trim().equals(""))
					max = "0";
				db.setValue("param_order", ""+(Integer.parseInt(max) + 1 ));
				db.setValue("param_type", "1");
				context.addRecord("record", db);
				callService("50206003", context);
				context.remove("record");
			}
		}
		
		context.getRecord("record").setValue("sys_svr_config_id", cfgId);
		
	}
	
	private String formatSQL(String sql){
		String sb = sql.replaceAll("'", "''");
		
		return sb;
	}
	
	/** 查询服务配置用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50203004( SysSvrConfigContext context ) throws TxnException
	{
		//System.out.println("txn50203004="+context);
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		try{
			table.executeFunction( SELECT_FUNCTION, context, inputNode, "config-inf" );
			context.getRecord("select-key").setValue("sys_svr_config_id", context.getRecord("config-inf").getValue("sys_svr_config_id"));
			callService("50206001", context);
			
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
				jsonObj.put("paramType", db.getValue("param_type"));
				jsonObj.put("paramText", db.getValue("param_text"));
				
				db.setValue("params", jsonObj.toString());
			}
		}catch(Exception e){
			System.out.println("------------->"+e.getMessage());
		}
		
		//System.out.println("在txn50203004 中查询  358行 \n"+context);
		//查询共享服务详细信息
		DataBus db = new DataBus();
		db.setValue("sys_svr_service_id", context.getRecord(inputNode).getValue("sys_svr_service_id"));
		context.addRecord("select-key", db);
		callService("50202004", context);
		//System.out.println("在txn50203004 中查询  364行 \n"+context);
	}
	
	/** 删除服务配置信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50203005( SysSvrConfigContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//删除所有相关配置参数
		callService("50206005", context);

		StringBuffer log = new StringBuffer();
		log.append("用户\"")
		   .append(context.getRecord("select-key").getValue("user_name"))
		   .append("\"的服务\"")
		   .append(context.getRecord("select-key").getValue("svr_name"))
		   .append("\"");
		setBizLog("删除共享服务配置：", context, log.toString());
		
		// 删除记录的主键列表 VoSysSvrConfigPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询用户的服务配置信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50203006( SysSvrConfigContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysSvrConfigPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( SELECT_USER_SVR_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 删除用户所有的服务配置信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50203007( SysSvrConfigContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		//查询用户所有服务
		callService("50203006", context);
		Recordset rs = context.getRecordset("record");
		while(rs.hasNext()){
			DataBus d = (DataBus)rs.next();
			
			TxnContext cont = new TxnContext();
			DataBus db = new DataBus();
			db.setValue("sys_svr_config_id", d.getValue("sys_svr_config_id"));
			cont.addRecord("select-key", db);
//			System.out.println(cont);
			callService("50206006", cont);
		}
		
		
		// 删除记录的主键列表 VoSysSvrConfigPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_USERS_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 查询某用户可以享受的服务列表
	 * @param context
	 * @throws TxnException
	 */
	public void txn50203008( SysSvrConfigContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "queryUserServiceList", context, inputNode, outputNode );
	}
	
	/** 生成SQL语句
	 * @param context 交易上下文
	 * @throws TxnException
	 */
//	public void txn50203008( SysSvrConfigContext context ) throws TxnException
//	{
//		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
//		String confId = context.getRecord(inputNode).getValue("sys_svr_config_id");
//		//根据配置id查询配置的字段及相应服务的信息
//		System.out.println("根据配置id查询配置的字段及相应服务的信息>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		table.executeSelect("SELECT s.sys_svr_service_id, c.permit_column, s.table_no, s.column_no, s.max_records FROM sys_svr_config c,sys_svr_service s where c.sys_svr_service_id = s.sys_svr_service_id and c.sys_svr_config_id='"+confId+"'", context, outputNode);
//		String permitCol = context.getRecord(outputNode).getValue("permit_column");
//		
//		//如果未进一步过滤显示字段，则使用服务配置的共享字段
//		if(permitCol.trim().equals("")){
//			System.out.println("未过滤显示字段，使用服务的共享字段！！！！！");
//			permitCol = context.getRecord(outputNode).getValue("column_no");
//		}
//		System.out.println("permitCol==>"+permitCol);
//		
//		//查询每个字段的对应信息，组织SQL中显示部分
//		String[] cols = permitCol.split(",");
//		//以”表名.字段名[ AS 别名 ]“的形式保存
//		StringBuffer colPart = new StringBuffer();
//		String firstTblName = "";//保存第一个表名的变量
//		Map colMap = new HashMap();//保存字段，用于判断是否添加别名
//		for(int i = 0; i < cols.length; i++){
//			if(cols[i].trim().equals("")) continue;
//			//循环查询每个字段的详细信息
//			TxnContext cont = new TxnContext();
//			
//			table.executeSelect("SELECT t.table_name,c.column_name,c.column_byname FROM SYS_COLUMN_SEMANTIC c, sys_table_semantic t where c.table_no = t.table_no and c.column_no='"+cols[i]+"'", cont, "col-inf");
//			String tblName = cont.getRecord("col-inf").getValue("table_name");
//			String colName = cont.getRecord("col-inf").getValue("column_name");
//			String colByName = cont.getRecord("col-inf").getValue("column_byname");
//			colPart.append(tblName).append(".").append(colName);
//			//判断是否存在已存在的字段名，添加 "AS 别名"的关键字
//			if(colMap.containsKey(colName)){
//				colPart.append(" AS ").append(colByName);
//			}else{
//				colMap.put(colName, colName);
//			}
//			if(i == (cols.length - 1) )
//				continue;
//			colPart.append(", ");
//		}
//		
//		System.out.println("字段部分》》》》》》》》"+colPart.toString());
//		//查询共享的表
//		String[] tableNos = context.getRecord(outputNode).getValue("table_no").split(",");
//		StringBuffer tblPart = new StringBuffer();
//		for(int i = 0; i < tableNos.length; i++){
//			TxnContext cont = new TxnContext();
//			
//			table.executeSelect("SELECT table_name FROM sys_table_semantic where table_no='"+tableNos[i]+"'", cont, "tbl-inf");
//			tblPart.append(cont.getRecord("tbl-inf").getValue("table_name"));
//			if(i == 0)//获得第一个表名，如果未配置共享字段，则默认查询此表所有字段
//				firstTblName = cont.getRecord("tbl-inf").getValue("table_name");
//			if(i == (tableNos.length - 1))
//				continue;
//			tblPart.append(", ");
//		}
//
//		System.out.println("要查询的表部分》》》》》"+tblPart.toString());
//		//保存SQL WHERE 后面的条件内容
//		StringBuffer wherePart = new StringBuffer();
//		//根据创建服务时配置的参数；
//		String svrId = context.getRecord(outputNode).getValue("sys_svr_service_id");
//		TxnContext cont1 = new TxnContext();
//		DataBus db1 = new DataBus();
//		db1.setValue("sys_svr_service_id", svrId);
//		cont1.addRecord("select-key", db1);
//		callService("50205001",cont1);
//		Recordset rs = cont1.getRecordset("record");
//		int i = 0;
//		while(rs.hasNext()){
//			DataBus db = (DataBus)rs.next();
//			if((db.getValue("operator1").trim().equalsIgnoreCase(""))){// 如果连接符为空
//				if(i != 0){//如果不是第一个参数
//					wherePart.append(" AND ");
//				}
//			}
//			wherePart.append(db.getValue("operator1")).append(" ")
//					 .append(db.getValue("left_paren"));
//			TxnContext cont = new TxnContext();
//			table.executeSelect("select table_name from sys_table_semantic where table_no='"+db.getValue("left_table_no")+"'", cont, "tbl-inf");
//			wherePart.append(cont.getRecord("tbl-inf").getValue("table_name")).append(".");
//			table.executeSelect("select column_name from sys_column_semantic where column_no='"+db.getValue("left_column_no")+"'", cont, "col-inf");
//			wherePart.append(cont.getRecord("col-inf").getValue("column_name"))
//					 .append(db.getValue("operator2"));
//			table.executeSelect("select table_name from sys_table_semantic where table_no='"+db.getValue("right_table_no")+"'", cont, "tbl-inf");
//			wherePart.append(cont.getRecord("tbl-inf").getValue("table_name")).append(".");
//			table.executeSelect("select column_name from sys_column_semantic where column_no='"+db.getValue("right_column_no")+"'", cont, "col-inf");
//			wherePart.append(cont.getRecord("col-inf").getValue("column_name"))
//					 .append(db.getValue("right_paren")).append(" ");
//			i++;
//		}
//		System.out.println("服务参数部分》》》》》》"+wherePart.toString());
//		
//		//查询服务配置时配置的系统参数
//		TxnContext txnContext = new TxnContext();
//		DataBus db = new DataBus();
//		db.setValue("sys_svr_config_id", confId);
//		txnContext.addRecord("select-key", db);
//		callService("50206001", txnContext);
//		rs = txnContext.getRecordset("config-param");
//		StringBuffer confParamPart = new StringBuffer();
//		while(rs.hasNext()){
//			DataBus tempDb = (DataBus)rs.next();
//			if(tempDb.getValue("operator1").trim().equals("") && !(wherePart.toString().trim().equals("")))
//				confParamPart.append(" AND ");
//			confParamPart.append(tempDb.getValue("operator1")).append(" ")
//			 		 	 .append(tempDb.getValue("left_paren"));
//			TxnContext cont = new TxnContext();
//			table.executeSelect("select table_name from sys_table_semantic where table_no='"+tempDb.getValue("left_table_no")+"'", cont, "tbl-inf");
//			confParamPart.append(cont.getRecord("tbl-inf").getValue("table_name")).append(".");
//			table.executeSelect("select column_name from sys_column_semantic where column_no='"+tempDb.getValue("left_column_no")+"'", cont, "col-inf");
//			confParamPart.append(cont.getRecord("col-inf").getValue("column_name"))
//					 .append(tempDb.getValue("operator2"));
//			if(tempDb.getValue("param_type").trim().equals("0"))//判断如果是系统参数值，则需要在值的两边加单引号
//				confParamPart.append("'").append(tempDb.getValue("param_value")).append("'");
//			else
//				confParamPart.append("").append(tempDb.getValue("param_value")).append("");
//			confParamPart.append(tempDb.getValue("right_paren")).append(" ");
//		}
//		System.out.println("配置的参数部分》》》》》"+confParamPart.toString());
//		
//		StringBuffer sql = new StringBuffer();
//		if (colPart.toString().trim().equals(""))//如果未配置字段，则默认取第一个表的所有
//			colPart.append(firstTblName).append(".*");
//		sql.append("SELECT ").append(colPart).append(" FROM ").append(tblPart);
//		if(!wherePart.toString().trim().equals("") || !confParamPart.toString().trim().equals(""))
//			sql.append(" WHERE ").append(wherePart).append(confParamPart);
//        
//		System.out.println("sql==>"+sql);
////		context.getRecord("record").setValue("query_sql", sql.toString().replaceAll("'", "''"));
////        context.getRecord("record").setValue("sys_svr_config_id", confId);
////		this.callService("50203009", context);
//		
//		DBOperation operation = DBOperationFactory.createOperation();
//		try {
//			operation.executeJDBCUpdate("update sys_svr_config set query_sql=?  where sys_svr_config_id=?",sql.toString(),confId);
//		} catch (Exception e) {
//			throw new TxnDataException("999999",e.getMessage());
//		}
//	}
	
	
	/** 生成SQL语句
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50203009( SysSvrConfigContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction(UPDATE_SQL_FUNCTION, context, inputNode, outputNode);
	}

	
	public void txn50203010( SysSvrConfigContext context ) throws TxnException
	{
		callService("50206006", context);

		StringBuffer log = new StringBuffer();
		log.append("用户\"")
		   .append(context.getRecord("select-key").getValue("user_name"))
		   .append("\"的服务\"")
		   .append(context.getRecord("select-key").getValue("svr_name"))
		   .append("\"");
		setBizLog("删除共享服务配置：", context, log.toString());
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "deleteSysConfig", context, inputNode, outputNode );
	}
	
	/** 查询服务配置列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50203011( SysSvrConfigContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysSvrConfigSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysSvrConfig result[] = context.getSysSvrConfigs( outputNode );
	}
	
	
	/**
	 * 重载父类的方法，用于替换交易接口的输入变量
	 * 调用函数
	 * @param funcName 方法名称
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数"
			);
		}
		
		// 执行
		SysSvrConfigContext appContext = new SysSvrConfigContext( context );
		invoke( method, appContext );
	}
	
	/**
	 * 记录日志
	 * @param type
	 * @param context
	 */
    private void setBizLog (String type,TxnContext context,String jgmc){
    	
    	context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, type + jgmc);
    }
}
