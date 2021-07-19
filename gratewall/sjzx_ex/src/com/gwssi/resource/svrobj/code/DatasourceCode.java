package com.gwssi.resource.svrobj.code;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

import com.gwssi.common.testconnection.ConnDatabaseUtil;

/**
 * 
 *     
 * 项目名称：bjgs_exchange    
 * 类名称：DatasourceCode    
 * 类描述：    
 * 创建人：lvhao    
 * 创建时间：2013-3-15 下午03:22:05    
 * 修改人：lvhao    
 * 修改时间：2013-3-15 下午03:22:05    
 * 修改备注：    
 * @version     
 *
 */
public class DatasourceCode extends ParamHelp
{

	/**
	 * 获取用户名称
	 * @param context
	 * @return
	 * @throws TxnException
	 */
	public Recordset getSvrObjName(TxnContext context) throws TxnException {
		BaseTable table = null;
		StringBuffer sql = new StringBuffer("select service_targets_id,service_targets_name from res_service_targets where is_markup='Y'  order by service_targets_type,show_order ");

		try{
			table = TableFactory.getInstance().getTableObject(this, "res_service_targets");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			return null;
		}

		return getParamList(context.getRecordset("selected-code-listed"), "service_targets_name", "service_targets_id");

	}
	
	/**
	 * 获取用户名称
	 * @param context
	 * @return
	 * @throws TxnException
	 */
	public Recordset getSvrObjNameSecond(TxnContext context) throws TxnException {
		BaseTable table = null;
		String targets_type=context.getString("input-data:targets_type");
		StringBuffer sql = new StringBuffer("select service_targets_id,service_targets_name from res_service_targets where 1=1 ");
		if(StringUtils.isNotEmpty(targets_type)){
			sql.append(" and service_targets_type='"+targets_type+"'");
		}
		
		sql.append(" order by service_targets_type,show_order  ");
		try{
			table = TableFactory.getInstance().getTableObject(this, "res_service_targets");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			return null;
		}

		return getParamList(context.getRecordset("selected-code-listed"), "service_targets_name", "service_targets_id");

	}
	
	/**
	 * 获取共享服务对象名称
	 * @param context
	 * @return
	 * @throws TxnException
	 */
	public Recordset getShareSvrObjName(TxnContext context) throws TxnException {
		BaseTable table = null;
		
		StringBuffer sql = new StringBuffer("select service_targets_id,service_targets_name from res_service_targets where is_markup='Y' order by service_targets_type ,show_order ");
		
		try{
			table = TableFactory.getInstance().getTableObject(this, "res_service_targets");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			return null;
		}

		return getParamList(context.getRecordset("selected-code-listed"), "service_targets_name", "service_targets_id");

	}
	
	/**
	 * 获取服务对象名称
	 * @param context
	 * @return
	 * @throws TxnException
	 */
	public Recordset getAllSvrObjName(TxnContext context) throws TxnException {
		BaseTable table = null;
		
		StringBuffer sql = new StringBuffer("select service_targets_id,service_targets_name from res_service_targets where is_markup='Y'  order by service_targets_type,show_order  ");
		
		try{
			table = TableFactory.getInstance().getTableObject(this, "res_service_targets");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			return null;
		}

		return getParamList(context.getRecordset("selected-code-listed"), "service_targets_name", "service_targets_id");

	}
	
	/**
	 * 获取采集任务服务对象名称
	 * @param context
	 * @return
	 * @throws TxnException
	 */
	public Recordset getCjTaskSvrObjName(TxnContext context) throws TxnException {
		BaseTable table = null;
		
		StringBuffer sql = new StringBuffer("select distinct(b.service_targets_id),b.service_targets_name,b.service_targets_type,b.show_order from collect_task a,res_service_targets b");
		sql.append(" where a.service_targets_id = b.service_targets_id and a.is_markup='Y'  order by b.service_targets_type,b.show_order  ");

		try{
			table = TableFactory.getInstance().getTableObject(this, "res_service_targets");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			return null;
		}

		return getParamList(context.getRecordset("selected-code-listed"), "service_targets_name", "service_targets_id");

	}
	
	/**
	 * 
	 * getCjTaskSvrObjName(获取数据表名)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param context
	 * @return
	 * @throws TxnException        
	 * Recordset       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public Recordset getTableName(TxnContext context) throws TxnException {
		
		String data_source_id = context.getString("input-data:data_source_id");
		BaseTable table = null;
		
		
		StringBuffer sql_database = new StringBuffer("select data_source_id,service_targets_id,data_source_type,data_source_name,data_source_ip,access_port,access_url,db_type,db_instance,db_username,db_password,db_desc,db_status,is_markup,creator_id,created_time,last_modify_id,last_modify_time from res_data_source ");
		if(data_source_id!=null&&!"".equals(data_source_id)){
			sql_database.append(" where data_source_id='"+data_source_id+"'");
		}
		
		try{
			table = TableFactory.getInstance().getTableObject(this, "res_data_source");
			table.executeRowset(sql_database.toString(),context,"database-info");
		}
		catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			return null;
		}
		
		
		String db_type = context.getRecord("database-info").getValue("db_type");
		String data_source_ip = context.getRecord("database-info").getValue("data_source_ip");
		String access_port = context.getRecord("database-info").getValue("access_port");
		String db_instance = context.getRecord("database-info").getValue("db_instance");
		String db_username = context.getRecord("database-info").getValue("db_username");
		String db_password = context.getRecord("database-info").getValue("db_password");
		String url = "";
		
		if(db_type!=null&&!db_type.equals("")){
			if("00".equals(db_type))//oracle
			{
				url = "jdbc:oracle:thin:@"+data_source_ip+":"+access_port+":"+db_instance;
				
			}else if("01".equals(db_type))//db2
			{
				//jdbc:db2://172.30.7.189:50000/newxcdb
				url = "jdbc:db2://"+data_source_ip+":"+access_port+"/"+db_instance;
				
			}else if("02".equals(db_type))//sql server
			{
				//jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=mydb
				url = "jdbc:sqlserver://"+data_source_ip+":"+access_port+";DatabaseName="+db_instance;
			}else if("03".equals(db_type))//mysql
			{
				//jdbc:mysql://localhost:3306/myuser
				url = "jdbc:mysql://"+data_source_ip+":"+access_port+"/"+db_instance;
				
			}else if("04".equals(db_type)){
				url = "jdbc:rmi://"+data_source_ip+"/jdbc:odbc:"+db_instance;
			}else{
				
			}
		}
		
		Recordset rset = new Recordset();
		ConnDatabaseUtil cd =new ConnDatabaseUtil();
		HashMap<Integer, String> tableName = cd.getTableName(db_type, url, db_username, db_password);
		for (int i = 0; i < tableName.size(); i++) {
			DataBus db_tmp = new DataBus();
			String tName = tableName.get(i);
			db_tmp.setValue("codename", tName);
			db_tmp.setValue("codevalue", tName);
			rset.add(db_tmp);
		}
		
		return getParamList(rset, "codename", "codevalue");

	}
	
public Recordset getTableColumnName(TxnContext context) throws TxnException {
		
		String data_source_id = context.getString("input-data:data_source_id");
		String source_collect_table = context.getString("input-data:source_collect_table");
		BaseTable table = null;
		
		
		StringBuffer sql_database = new StringBuffer("select data_source_id,service_targets_id,data_source_type,data_source_name,data_source_ip,access_port,access_url,db_type,db_instance,db_username,db_password,db_desc,db_status,is_markup,creator_id,created_time,last_modify_id,last_modify_time from res_data_source ");
		if(data_source_id!=null&&!"".equals(data_source_id)){
			sql_database.append(" where data_source_id='"+data_source_id+"'");
		}
		
		try{
			table = TableFactory.getInstance().getTableObject(this, "res_data_source");
			table.executeRowset(sql_database.toString(),context,"database-info");
		}
		catch( TxnException e ){
			log.error( "取数据库表时错误", e );
			return null;
		}
		
		
		String db_type = context.getRecord("database-info").getValue("db_type");
		String data_source_ip = context.getRecord("database-info").getValue("data_source_ip");
		String access_port = context.getRecord("database-info").getValue("access_port");
		String db_instance = context.getRecord("database-info").getValue("db_instance");
		String db_username = context.getRecord("database-info").getValue("db_username");
		String db_password = context.getRecord("database-info").getValue("db_password");
		String url = "";
		
		if(db_type!=null&&!db_type.equals("")){
			if("00".equals(db_type))//oracle
			{
				url = "jdbc:oracle:thin:@"+data_source_ip+":"+access_port+":"+db_instance;
				
			}else if("01".equals(db_type))//db2
			{
				//jdbc:db2://172.30.7.189:50000/newxcdb
				url = "jdbc:db2://"+data_source_ip+":"+access_port+"/"+db_instance;
				
			}else if("02".equals(db_type))//sql server
			{
				//jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=mydb
				url = "jdbc:sqlserver://"+data_source_ip+":"+access_port+";DatabaseName="+db_instance;
			}else if("03".equals(db_type))//mysql
			{
				//jdbc:mysql://localhost:3306/myuser
				url = "jdbc:mysql://"+data_source_ip+":"+access_port+"/"+db_instance;
				
			}else if("04".equals(db_type)){
				url = "jdbc:rmi://"+data_source_ip+"/jdbc:odbc:"+db_instance;
			}else{
				
			}
		}
		
		Recordset rset = new Recordset();
		ConnDatabaseUtil cd =new ConnDatabaseUtil();
		HashMap<Integer, String> columnName = cd.getTableColumnName(db_type, url, db_username, db_password,source_collect_table);
		for (int i = 0; i < columnName.size(); i++) {
			DataBus db_tmp = new DataBus();
			String cName = columnName.get(i);
			db_tmp.setValue("codename", cName);
			db_tmp.setValue("codevalue", cName);
			rset.add(db_tmp);
		}
		
		return getParamList(rset, "codename", "codevalue");

	}
}
