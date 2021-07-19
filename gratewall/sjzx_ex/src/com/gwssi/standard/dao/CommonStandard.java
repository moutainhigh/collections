package com.gwssi.standard.dao;

import org.apache.commons.lang.StringUtils;

import com.gwssi.common.constant.ExConstant;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class CommonStandard extends BaseTable
{
   public CommonStandard()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
		registerSQLFunction( "queryDataStandard", DaoFunction.SQL_ROWSET, "查看数据标准信息" );
		registerSQLFunction( "queryTechStandard", DaoFunction.SQL_ROWSET, "查看技术标准信息" );
		registerSQLFunction( "queryRenameStandard", DaoFunction.SQL_ROWSET, "查看命名标准信息" );
		registerSQLFunction("getInfoBydataId", DaoFunction.SQL_ROWSET,
		"根据服务对象获得服务统计信息");
		registerSQLFunction("getInfoBytechId", DaoFunction.SQL_ROWSET,
		"根据服务对象获得服务统计信息");
		registerSQLFunction("getInfoByrenameId", DaoFunction.SQL_ROWSET,
		"根据服务对象获得服务统计信息");

   }
	public SqlStatement getInfoBydataId(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String table = inputData.getValue("table_name");
		String column = inputData.getValue("col_name");
		String title = inputData.getValue("col_title");
		if (StringUtils.isNotBlank(table) && StringUtils.isNotBlank(column)
				&& StringUtils.isNotBlank(title)) {
			sqlBuffer
			.append("select tar." + column + " as key, tar." + title
					+ " as title ")
			.append("from " + table + " tar ")
			.append("where tar.specificate_type='1' and tar.is_markup = 'Y' ")
			.append("order by tar.last_modify_time ");
			stmt.addSqlStmt(sqlBuffer.toString());
		}

		return stmt;
	}
	public SqlStatement getInfoBytechId(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String table = inputData.getValue("table_name");
		String column = inputData.getValue("col_name");
		String title = inputData.getValue("col_title");
		if (StringUtils.isNotBlank(table) && StringUtils.isNotBlank(column)
				&& StringUtils.isNotBlank(title)) {
			sqlBuffer
			.append("select tar." + column + " as key, tar." + title
					+ " as title ")
			.append("from " + table + " tar ")
			.append("where tar.specificate_type='2' and tar.is_markup = 'Y' ")
			.append("order by tar.last_modify_time ");
			stmt.addSqlStmt(sqlBuffer.toString());
		}

		return stmt;
	}
	public SqlStatement getInfoByrenameId(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String table = inputData.getValue("table_name");
		String column = inputData.getValue("col_name");
		String title = inputData.getValue("col_title");
		if (StringUtils.isNotBlank(table) && StringUtils.isNotBlank(column)
				&& StringUtils.isNotBlank(title)) {
			sqlBuffer
			.append("select tar." + column + " as key, tar." + title
					+ " as title ")
			.append("from " + table + " tar ")
			.append("where tar.specificate_type='3' and tar.is_markup = 'Y' ")
			.append("order by tar.last_modify_time ");
			stmt.addSqlStmt(sqlBuffer.toString());
		}

		return stmt;
	}
   public SqlStatement queryDataStandard(TxnContext request,
			DataBus inputData) throws TxnException
	{
		
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		//String standard_name = request.getRecord("select-key").getValue("standard_name");
		
		DataBus db = request.getRecord("select-key");
		String created_time_start = db.getValue("created_time_start");
		String created_time_end = db.getValue("created_time_end");
		String standard_id = db.getValue("standard_id");

		sql.append(" select standard_id,standard_name,standard_type,specificate_type,issuing_unit,specificate_no,specificate_desc,specificate_status,is_markup,creator_id,created_time,last_modify_id,last_modify_time,rtrim(ltrim(fjmc,','),',') as fjmc,enable_time from common_standard where specificate_type='1' ");
		if (standard_id != null && !standard_id.equals("")) {
			sql.append(" and standard_id = '" + standard_id + "'");
		}	
		if (created_time_start != null && !"".equals(created_time_start)) {
			sql.append(" and enable_time >= '" + created_time_start
					+ "'");
		}

		if (created_time_end != null && !"".equals(created_time_end)) {
			sql.append(" and enable_time <= '" + created_time_end + "'");
		}
		sql.append(" and is_markup='"+ExConstant.IS_MARKUP_Y+"'");
		sql.append(" order by created_time desc");
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );
		//System.out.println("----------数据-------------"+sql.toString()+"--------------------------------");
		
		return stmt;
	}
  
   
   public SqlStatement queryTechStandard(TxnContext request,
			DataBus inputData) throws TxnException
	{
		
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		/*String standard_name = request.getRecord("select-key").getValue("standard_name");
		String created_time_start = request.getRecord("select-key").getValue("created_time_start");
		String created_time_end = request.getRecord("select-key").getValue("created_time_end");*/
		DataBus db = request.getRecord("select-key");
		String created_time_start = db.getValue("created_time_start");
		String created_time_end = db.getValue("created_time_end");
		String standard_id = db.getValue("standard_id");
		sql.append(" select standard_id,standard_name,standard_type,specificate_type,issuing_unit,specificate_no,specificate_desc,specificate_status,is_markup,creator_id,created_time,last_modify_id,last_modify_time,rtrim(ltrim(fjmc,','),',') as fjmc,enable_time from common_standard where specificate_type='2' ");
		/*if (standard_name != null && !"".equals(standard_name)) {
			sql.append(" and standard_name like '%" + standard_name + "%'");
		}		*/
		if (standard_id != null && !standard_id.equals("")) {
			sql.append(" and standard_id = '" + standard_id + "'");
		}	
		if (created_time_start != null && !"".equals(created_time_start)) {
			sql.append(" and enable_time >= '" + created_time_start
					+ "'");
		}

		if (created_time_end != null && !"".equals(created_time_end)) {
			sql.append(" and enable_time <= '" + created_time_end + "'");
		}
		sql.append(" and is_markup='"+ExConstant.IS_MARKUP_Y+"'");
		sql.append(" order by created_time desc");
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );
		//System.out.println("----------技术-------------"+sql.toString()+"--------------------------------");
		return stmt;
	}
   
   
   
   
   public SqlStatement queryRenameStandard(TxnContext request,
			DataBus inputData) throws TxnException
	{
		
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		/*String standard_name = request.getRecord("select-key").getValue("standard_name");
		String created_time_start = request.getRecord("select-key").getValue("created_time_start");
		String created_time_end = request.getRecord("select-key").getValue("created_time_end");*/
		DataBus db = request.getRecord("select-key");
		String created_time_start = db.getValue("created_time_start");
		String created_time_end = db.getValue("created_time_end");
		String standard_id = db.getValue("standard_id");
		sql.append(" select standard_id,standard_name,standard_type,specificate_type,issuing_unit,specificate_no,specificate_desc,specificate_status,is_markup,creator_id,created_time,last_modify_id,last_modify_time,rtrim(ltrim(fjmc,','),',') as fjmc,enable_time from common_standard where specificate_type='3' ");
		/*if (standard_name != null && !"".equals(standard_name)) {
			sql.append(" and standard_name like '%" + standard_name + "%'");
		}	*/	
		if (standard_id != null && !standard_id.equals("")) {
			sql.append(" and standard_id = '" + standard_id + "'");
		}	
		if (created_time_start != null && !"".equals(created_time_start)) {
			sql.append(" and enable_time >= '" + created_time_start
					+ "'");
		}

		if (created_time_end != null && !"".equals(created_time_end)) {
			sql.append(" and enable_time <= '" + created_time_end + "'");
		}
		sql.append(" and is_markup='"+ExConstant.IS_MARKUP_Y+"'");
		sql.append(" order by created_time desc");
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );
		//System.out.println("----------命名-------------"+sql.toString()+"--------------------------------");
		
		return stmt;
	}
  
  
   /**
    * 执行SQL语句前的处理
    */
   public void prepareExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

   /**
    * 执行完SQL语句后的处理
    */
   public void afterExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

}
