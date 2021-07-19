package cn.gwssi.dw.rd.metadata.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[sys_rd_column]的处理类
 * @author Administrator
 *
 */
public class SysRdColumn extends BaseTable
{
	public SysRdColumn()
	{
		
	}
	
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		// 以下是注册用户自定义函数的过程
		// 包括三个参数：SQL语句的名称，类型，描述
		// 业务类可以通过以下函数调用:
		// table.executeFunction( "loadSysRdColumnList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysRdColumnList", DaoFunction.SQL_ROWSET, "获取已认领表字段表列表" );
		registerSQLFunction( "queryDistinctColumnListByName", DaoFunction.SQL_ROWSET, "字段中文名查询" );
		registerSQLFunction( "queryDistinctColumnListByCode", DaoFunction.SQL_ROWSET, "字段名查询" );
		registerSQLFunction( "queryColumnListByName", DaoFunction.SQL_ROWSET, "字段中文名引用查询" );
		registerSQLFunction( "queryColumnListByCode", DaoFunction.SQL_ROWSET, "字段名引用查询" );
		registerSQLFunction( "queryColumnType", DaoFunction.SQL_ROWSET, "查询字段类型和长度" );
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
	/**
	 * 得到字段中文名
	 */
	 public SqlStatement queryDistinctColumnListByName(TxnContext request,DataBus inputData) throws TxnException
		{	 

			SqlStatement stmt = new SqlStatement();
			String sql = "";
			String sqlCount = "";
			StringBuffer stringBfSql = new StringBuffer("select distinct(column_name) from SYS_RD_COLUMN where column_name like  ");
			StringBuffer stringBfCount = new StringBuffer("select count(distinct(column_name)) from SYS_RD_COLUMN where column_name like  ");
			DataBus dataBus = request.getRecord("select-key");	
			String column_name = dataBus.getValue("column_name");	
			stringBfSql.append("'%"+column_name+"%' ");
			stringBfCount.append("'%"+column_name+"%' ");

			sql = stringBfSql.toString();
			sqlCount = stringBfCount.toString();
			stmt.addSqlStmt(sql);	
			stmt.setCountStmt(sqlCount);	
			return stmt;

		}

		/**
		 * 通过字段中文名得到引用信息
		 */
	 public SqlStatement queryColumnListByName(TxnContext request,DataBus inputData) throws TxnException
		{	 
		 SqlStatement stmt = new SqlStatement();
		 String sql = "";
		 String sqlCount = "";
			StringBuffer stringBfSql = new StringBuffer("select  sys_rd_column.column_code,sys_rd_column.column_name,sys_rd_table.table_code,sys_rd_table.table_name from sys_rd_column left join sys_rd_table on sys_rd_column.sys_rd_table_id = sys_rd_table.sys_rd_table_id ");
			StringBuffer stringBfCount = new StringBuffer("select count(*) from sys_rd_column left join sys_rd_table on sys_rd_column.sys_rd_table_id = sys_rd_table.sys_rd_table_id  ");

			String column_name = inputData.getValue("column_name");
			if(column_name==null){
				stringBfSql.append(" ORDER BY sys_rd_column.column_code");
				stringBfCount.append(" ORDER BY sys_rd_column.column_code");
			}else{
			stringBfSql.append("where sys_rd_column.column_name = '"+column_name+"' ORDER BY sys_rd_column.column_code");
			stringBfCount.append("where sys_rd_column.column_name = '"+column_name+"' ORDER BY sys_rd_column.column_code");
			}
			sql = stringBfSql.toString();
			sqlCount = stringBfCount.toString();
			stmt.addSqlStmt(sql);	
			stmt.setCountStmt(sqlCount);
		 return stmt;
		}
	 
		/**
		 * 得到字段名
		 */
	 public SqlStatement queryDistinctColumnListByCode(TxnContext request,DataBus inputData) throws TxnException
		{	 
		
		 SqlStatement stmt = new SqlStatement();
			String sql = "";
			String sqlCount = "";
			StringBuffer stringBfSql = new StringBuffer("select distinct(column_code) from SYS_RD_COLUMN where column_code like   ");
			StringBuffer stringBfCount = new StringBuffer("select count(distinct(column_code)) from SYS_RD_COLUMN where column_code like ");
			
			DataBus dataBus = request.getRecord("select-key");	
			String column_code = dataBus.getValue("column_code");
			if(column_code==null){
				column_code="";
			}
			stringBfSql.append("'%"+column_code+"%' ");
			stringBfCount.append("'%"+column_code+"%' ");
			
			sql = stringBfSql.toString();
			sqlCount = stringBfCount.toString();
			stmt.addSqlStmt(sql);	
			stmt.setCountStmt(sqlCount);	
			return stmt;

		}
		/**
		 * 通过字段名得到引用信息
		 */
	 public SqlStatement queryColumnListByCode(TxnContext request,DataBus inputData) throws TxnException
		{	 
		 SqlStatement stmt = new SqlStatement();
		 String sql = "";
		 String sqlCount = "";
			StringBuffer stringBfSql = new StringBuffer("select  sys_rd_column.column_code,sys_rd_column.column_name,sys_rd_table.table_code,sys_rd_table.table_name from sys_rd_column left join sys_rd_table on sys_rd_column.sys_rd_table_id = sys_rd_table.sys_rd_table_id ");
			StringBuffer stringBfCount = new StringBuffer("select count(*) from sys_rd_column left join sys_rd_table on sys_rd_column.sys_rd_table_id = sys_rd_table.sys_rd_table_id  ");
			
			String column_code = inputData.getValue("column_code");
			if(column_code==null||column_code.equals("")){
				stringBfSql.append(" ORDER BY sys_rd_column.column_code");
				stringBfCount.append("  ORDER BY sys_rd_column.column_code");
			}else{
			stringBfSql.append("where sys_rd_column.column_code='"+column_code+"' ORDER BY sys_rd_column.column_code");
			stringBfCount.append("where sys_rd_column.column_code='"+column_code+"' ORDER BY sys_rd_column.column_code");
			}
			
			sql = stringBfSql.toString();
			sqlCount = stringBfCount.toString();
			stmt.addSqlStmt(sql);	
			stmt.setCountStmt(sqlCount);
		 return stmt;
		
		}
	 
	 /**
	     * 查询字段类型和长度用于配置接口的导出功能
	     */
	 public SqlStatement queryColumnType(TxnContext request,DataBus inputData) throws TxnException
		{	   
			SqlStatement stmt = new SqlStatement();
			
			//获取所有字段号
			String column_nos = request.getRecord("select-key").getValue("column_no");
			
			//VoUser user = request.getOperData();
			//String userName = user.getOperName();
			
			StringBuffer querySql = new StringBuffer(" select tt.*,tt1.codename typename from(select t.table_name,t1.table_code,column_code,column_name,column_type,column_length from Sys_Rd_Column t1,sys_rd_table t  where t1.sys_rd_table_id = t.sys_rd_table_id ");
			if(column_nos !=null && !"".equals(column_nos)){
				querySql.append(" and t1.column_no in("+column_nos+")");
			}
			
			querySql.append(")tt,(select codevalue,codename from codedata  where codetype='字段数据类型') tt1 where tt.column_type = tt1.codevalue ");
			querySql.append(" order by tt.table_code");
			//System.out.println("字段长度1："+querySql.toString());
			stmt.addSqlStmt(querySql.toString());
			stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
			return stmt;
		}
	/**
	 * XXX:用户自定义的SQL语句
	 * 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句
	 * 对于其它的语句，只需要生成一个语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	public SqlStatement loadSysRdColumnList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_rd_column" );
		stmt.setCountStmt( "select count(*) from sys_rd_column" );
		return stmt;
	}
	 */
	
}

