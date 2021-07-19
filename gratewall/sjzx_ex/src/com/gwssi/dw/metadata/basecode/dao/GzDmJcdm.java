package com.gwssi.dw.metadata.basecode.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[gz_dm_jcdm]的处理类
 * @author Administrator
 *
 */
public class GzDmJcdm extends BaseTable
{
	public GzDmJcdm()
	{
		
	}
	
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		registerSQLFunction( "selectSjfx", DaoFunction.SQL_SELECT, "检验基础数据分项数据" );
		
		registerSQLFunction( "verifyJcdmdm", DaoFunction.SQL_SELECT, "检验基础代码数据" );
		
		registerSQLFunction( "selectSystemCodeTable", DaoFunction.SQL_ROWSET, "查询系统代码集" );
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
	 * XXX:用户自定义的SQL语句
	 * 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句
	 * 对于其它的语句，只需要生成一个语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	public SqlStatement loadGzDmJcdmList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from gz_dm_jcdm" );
		stmt.setCountStmt( "select count(*) from gz_dm_jcdm" );
		return stmt;
	}
	 */
	public SqlStatement selectSjfx(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus=request.getRecord("select-key");
		String jc_dm_id=fzx_idDataBus.getValue("jc_dm_id");
		
		stmt.addSqlStmt("select count(*) as count from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"'");		
		return stmt;
	}	
	/**
	 * 验证基础代码代码是否存在
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement verifyJcdmdm(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String jc_dm_dm = dataBus.getValue("jc_dm_dm");
		stmt.addSqlStmt("select count(*) as count from gz_dm_jcdm where jc_dm_dm='"+jc_dm_dm+"'");		
		return stmt;
	}	
	
	/**
	    * 查询基础代码集
	    * @param request
	    * @param inputData
	    * @return
	    * @throws TxnException
	    */
		public SqlStatement selectSystemCodeTable(TxnContext request,DataBus inputData) throws TxnException
		{	   
			SqlStatement stmt = new SqlStatement();
			
			String jc_dm_dm = request.getRecord("select-key").getValue("jc_dm_dm");
			String jc_dm_mc = request.getRecord("select-key").getValue("jc_dm_mc");
			String jc_dm_bzly = request.getRecord("select-key").getValue("jc_dm_bzly");
			String jcsjfx_mc = request.getRecord("select-key").getValue("jcsjfx_mc");
			StringBuffer querySql = new StringBuffer("select a.jc_dm_id,a.jc_dm_dm,a.jc_dm_mc,a.jc_dm_bzly,a.jc_dm_ms from gz_dm_jcdm a  ");
			
			querySql.append(" where 1=1 ");
			
			
			if(jc_dm_dm!=null && !"".equals(jc_dm_dm)){
				querySql.append(" and a.jc_dm_dm like '%").append(jc_dm_dm).append("%'");
			}
			
			if(jc_dm_mc!=null && !"".equals(jc_dm_mc)){
				querySql.append(" and a.jc_dm_mc like '%").append(jc_dm_mc).append("%'");
			}
			
			if(jc_dm_bzly!=null && !"".equals(jc_dm_bzly)){
				querySql.append(" and a.jc_dm_bzly ='").append(jc_dm_bzly).append("'");
			}
			
			if(jcsjfx_mc!=null && !"".equals(jcsjfx_mc)){
				querySql.append(" and a.jc_dm_id in ( select jc_dm_id from gz_dm_jcdm_fx where jcsjfx_mc = '").append(jcsjfx_mc).append("')");
			}
			
			
			
			
			querySql.append(" order by a.jc_dm_id ");
			stmt.addSqlStmt(querySql.toString());
			stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
			return stmt;
		}
}

