package com.gwssi.dw.metadata.msurunit.dao;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.dao.func.SqlStatement;


/**
 * 数据表[gz_zb_jldw]的处理类
 * @author Administrator
 *
 */
public class GzZbJldw extends BaseTable
{
	public GzZbJldw()
	{
		
	}
	
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		registerSQLFunction( "queryrowset", DaoFunction.SQL_ROWSET, "获取计量单位表列表" );
		registerSQLFunction( "iscandelete", DaoFunction.SQL_ROWSET, "查询指标表" );
		registerSQLFunction( "iscandelete2", DaoFunction.SQL_ROWSET, "查询指标属性关联表" );
		registerSQLFunction( "selectSjfx", DaoFunction.SQL_ROWSET, "查询计量单位是否被使用" );
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
	 *  */
	public SqlStatement queryrowset( TxnContext request, DataBus inputData )
	{
		DataBus data=request.getRecord("select-key");
		String dwlb_dm=data.getValue("dwlb_dm");
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select jldw_dm,dwlb_dm,jldw_cn_mc,jldw_sjz,jldw_en_mc from gz_zb_jldw where dwlb_dm='"+dwlb_dm+"' order by jldw_dm" );
		stmt.setCountStmt( "select count(*) from gz_zb_jldw where dwlb_dm='"+dwlb_dm+"'");
		return stmt;
	}
	
	
////查询选中的计量单位在指标表中是否有关联
	public SqlStatement iscandelete( TxnContext request, DataBus inputData ) throws TxnException
	{
		inputData=request.getRecord("select-key");
		String jldw_dm=inputData.getValue("jldw_dm");
		if(jldw_dm==null||jldw_dm.length()<=0)
		{
			throw new TxnDataException("","计量单位代码为空1");
		}
		SqlStatement stmt = new SqlStatement( );
		String sql1="select zb_id from GZ_ZB_ZB where JLDW_DM='"+jldw_dm+"'";
		String sql2="select count(*) from GZ_ZB_ZB where JLDW_DM='"+jldw_dm+"'";
		stmt.addSqlStmt( sql1 );
		stmt.setCountStmt(sql2 );
		return stmt;
	}
	
//	查询选中的计量单位在指标属性关联表中是否有关联
	public SqlStatement iscandelete2( TxnContext request, DataBus inputData ) throws TxnException
	{
		inputData=request.getRecord("select-key");
		String jldw_dm=inputData.getValue("jldw_dm");
		if(jldw_dm==null||jldw_dm.length()<=0)
		{
			throw new TxnDataException("","计量单位代码为空2");
		}
		SqlStatement stmt = new SqlStatement( );
		String sql1="select gl_id from GZ_ZB_ZBSX where JLDW_DM='"+jldw_dm+"'";
		String sql2="select count(*) from GZ_ZB_ZBSX where JLDW_DM='"+jldw_dm+"'";
		stmt.addSqlStmt( sql1 );
		stmt.setCountStmt(sql2 );
		return stmt;
	}
	
	public SqlStatement selectSjfx(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus=request.getRecord("select-key");
		String jldw_dm=fzx_idDataBus.getValue("jldw_dm");
		stmt.addSqlStmt("select count(*) as count from gz_zb_zb,gz_zb_zbsx where gz_zb_zb.jldw_dm='"+jldw_dm+"'or gz_zb_zbsx.jldw_dm='"+jldw_dm+"'");		
		return stmt;
	}
	
}

