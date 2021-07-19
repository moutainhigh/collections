package com.gwssi.dw.metadata.msurunit.dao;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.component.exception.TxnDataException;

/**
 * 数据表[gz_zb_jldw_fl]的处理类
 * @author Administrator
 *
 */
public class GzZbJldwFl extends BaseTable
{
	public GzZbJldwFl()
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
		// table.executeFunction( "loadGzZbJldwFlList", context, inputNode, outputNode );
         registerSQLFunction( "queryrowset", DaoFunction.SQL_ROWSET, "获取计量单位管理列表" );
         registerSQLFunction( "query", DaoFunction.SQL_ROWSET, "查询计量单位" );
         registerSQLFunction( "selectSjfx", DaoFunction.SQL_ROWSET, "查询计量单位" );
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
		String dwlb_cn_mc=data.getValue("dwlb_cn_mc");
		SqlStatement stmt = new SqlStatement( );
		String sql="select dwlb_dm,dwlb_cn_mc,dwlb_cn_ms,dwlb_en_mc,dwlb_en_ms from gz_zb_jldw_fl where dwlb_dm like '%'";
		String sql2= "select count(*) from gz_zb_jldw_fl where dwlb_dm like '%'";
		if (dwlb_dm == null || dwlb_dm.length() <= 0) 
        {

		} 
        else 
        {
			sql = sql + "and dwlb_dm = '" + dwlb_dm + "'";
			sql2 = sql2 + "and dwlb_dm = '" + dwlb_dm + "'";
		}
		if (dwlb_cn_mc == null || dwlb_cn_mc.length() <= 0) 
		{

		} 
		else 
		{
			sql = sql + "and dwlb_cn_mc = '" + dwlb_cn_mc + "'";
			sql2 = sql2 + "and dwlb_cn_mc = '" + dwlb_cn_mc + "' ";
		}
		sql=sql + "order by dwlb_dm";
		// System.out.println(sql2);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( sql2 );
		return stmt;
	}
	
	//在计量单位表里查询指定的计量单位类别代码下的计量单位
	public SqlStatement query( TxnContext request, DataBus inputData ) throws TxnException
	{
		DataBus data=request.getRecord("select-key");
		String dwlbdm=data.getValue("dwlb_dm");
		if(dwlbdm==null||dwlbdm.length()<=0)
		{
			throw new TxnDataException("","计量单位类别代码为空");
		}	
		String sql="select jldw_dm from GZ_ZB_JLDW where DWLB_DM='"+dwlbdm+"'";
		String sql2="select count(*) from GZ_ZB_JLDW where DWLB_DM='"+dwlbdm+"'";
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( sql2 );
		return stmt;
	}
	
//	在计量单位表里查询指定的计量单位类别代码下的计量单位数量	
	public SqlStatement selectSjfx(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus=request.getRecord("select-key");
		String dwlb_dm=fzx_idDataBus.getValue("dwlb_dm");
		stmt.addSqlStmt("select count(*) as count from GZ_ZB_JLDW where dwlb_dm='"+dwlb_dm+"'");		
		return stmt;
	}
	
}

