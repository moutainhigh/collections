package com.gwssi.dw.metadata.basecode.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[gz_dm_jcdm_fx]的处理类
 * @author Administrator
 *
 */
public class GzDmJcdmFx extends BaseTable
{
	public GzDmJcdmFx()
	{
		
	}
	
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		registerSQLFunction( "getFirstNode", DaoFunction.SQL_ROWSET, "读取第一层节点" );
		registerSQLFunction( "getSecondNode", DaoFunction.SQL_ROWSET, "读取第二层节点" );
		registerSQLFunction( "selectSx", DaoFunction.SQL_SELECT, "得到顺序码" );
		registerSQLFunction( "selectFjd", DaoFunction.SQL_SELECT, "检验是否为最后一个子结点" );
		registerSQLFunction( "getSerchNode", DaoFunction.SQL_ROWSET, "基础数据分项节点模糊查询" );
		registerSQLFunction( "verifyJcsjfxdm", DaoFunction.SQL_SELECT, "检验基础代码数据" );
		registerSQLFunction( "selectZb", DaoFunction.SQL_SELECT, "检验基础代码是否被引用" );
		registerSQLFunction( "selectFz", DaoFunction.SQL_SELECT, "检验基础代码是否被引用" );
		registerSQLFunction( "rightQuery", DaoFunction.SQL_ROWSET, "右模糊查询" );
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
	public SqlStatement loadGzDmJcdmFxList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from gz_dm_jcdm_fx" );
		stmt.setCountStmt( "select count(*) from gz_dm_jcdm_fx" );
		return stmt;
	}
	 */
	
	public SqlStatement getFirstNode( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data=request.getRecord("select-key");
		String jc_dm_id=data.getValue("jc_dm_id");
		stmt.addSqlStmt( "select jc_dm_id, jcsjfx_id,jcsjfx_mc, jcsjfx_dm, jcsjfx_cjm,jcsjfx_fjd," +
		"szcc,xssx,sfmx,fx_ms,sy_zt  from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"' and (jcsjfx_fjd='' or jcsjfx_fjd is null) order by xssx"  );
		stmt.setCountStmt("select count(*) from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"' and (jcsjfx_fjd='' or jcsjfx_fjd is null)" );
		return stmt;
	}
	
	public SqlStatement getSecondNode(TxnContext request,DataBus inputData)
	{
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String jcsjfx_fjd=dataBus.getValue("jcsjfx_fjd"); 
		stmt.addSqlStmt("select jc_dm_id, jcsjfx_id,jcsjfx_mc, jcsjfx_dm, jcsjfx_cjm,jcsjfx_fjd," +
				"szcc,xssx,sfmx,fx_ms,sy_zt  from gz_dm_jcdm_fx where jcsjfx_fjd='"+jcsjfx_fjd+"' order by xssx");
		stmt.setCountStmt("select count(*) from gz_dm_jcdm_fx where jcsjfx_fjd='"+jcsjfx_fjd+"'");
		return stmt;
	}
	
	public SqlStatement selectSx(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt=new SqlStatement();
		DataBus jcsj_idDataBus=request.getRecord("record");
		String jcsjfx_id=jcsj_idDataBus.getValue("jcsjfx_id");		
		String jc_dm_id=jcsj_idDataBus.getValue("jc_dm_id");
		String sql = "";
		if(jcsjfx_id!=null&&!jcsjfx_id.equals("")){
			sql = "select Max(xssx)+1 as xssx from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"' and jcsjfx_fjd='"+jcsjfx_id+"'";
		}else{
			sql = "select Max(xssx)+1 as xssx from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"' and ( jcsjfx_fjd='' or jcsjfx_fjd is null ) ";
		}
		stmt.addSqlStmt(sql);		
		return stmt;
	}
	
	public SqlStatement selectFjd(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus=request.getRecord("record");
		String jcsjfx_fjd=fzx_idDataBus.getValue("jcsjfx_fjd");	
		String jc_dm_id=fzx_idDataBus.getValue("jc_dm_id");
		String sql = "";
		if(jcsjfx_fjd!=null&&!jcsjfx_fjd.equals("")){
			sql = "select count(*) as count from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"' and jcsjfx_id='"+jcsjfx_fjd+"'";
		}else{
			sql = "select count(*) as count from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"' and ( jcsjfx_id='' or jcsjfx_id is null ) ";
		}
		stmt.addSqlStmt(sql);		
		return stmt;
	}	
	/**
	 * 获取模糊查询节点语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	 */
	public SqlStatement getSerchNode( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String jcsjfx_mc = dataBus.getValue("jcsjfx_mc");
		String jc_dm_id = dataBus.getValue("jc_dm_id");
		String sql = "select jc_dm_id, jcsjfx_id,jcsjfx_mc, jcsjfx_dm, jcsjfx_cjm,jcsjfx_fjd," +
				" szcc,xssx,sfmx,fx_ms,sy_zt  from gz_dm_jcdm_fx where jcsjfx_mc like'%"+jcsjfx_mc+"%' " +
				" and jc_dm_id='"+jc_dm_id+"'  order by xssx";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( "select count(*) from gz_dm_jcdm_fx where jcsjfx_mc like'%"+jcsjfx_mc+"%' " +
				" and jc_dm_id='"+jc_dm_id+"'  order by xssx" );
		return stmt;
	}
	
	/**
	 * 验证基础数据分项代码是否存在
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement verifyJcsjfxdm(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String jc_dm_id = dataBus.getValue("jc_dm_id");
		String jcsjfxdm = dataBus.getValue("jcsjfx_dm");
		stmt.addSqlStmt("select count(*) as count from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"'" +
				" and jcsjfx_dm='"+jcsjfxdm+"'");		
		return stmt;
	}	
	
	/**
	 * 验证基础数据分项代码是否被指标引用
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectZb(TxnContext request,DataBus inputData)
	{				
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String jcsjfx_id = dataBus.getValue("jcsjfx_id");
		stmt.addSqlStmt("select count(*) as count from GZ_ZB_ZB where jcsjfx_id='"+jcsjfx_id+"'" );				
		return stmt;
	}
	
	/**
	 * 验证基础数据分项代码是否被分组引用
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectFz(TxnContext request,DataBus inputData)
	{				
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String jcsjfx_id = dataBus.getValue("jcsjfx_id");
		stmt.addSqlStmt("select count(*) as count from GZ_ZB_FZFX where jcsjfx_id='"+jcsjfx_id+"'" );				
		return stmt;
	}
	
	/**
	    * 右模糊查询
	    * @param request
	    * @param inputData
	    * @return
	    * @throws TxnException
	    */
		public SqlStatement rightQuery(TxnContext request,DataBus inputData) throws TxnException
		{	   
			SqlStatement stmt = new SqlStatement();
			
			String jc_dm_id = request.getRecord("select-key").getValue("jc_dm_id");
			String jcsjfx_dm = request.getRecord("select-key").getValue("jcsjfx_dm");
			String jcsjfx_mc = request.getRecord("select-key").getValue("jcsjfx_mc");
			
			StringBuffer querySql = new StringBuffer("select jcsjfx_id, jc_dm_id, jcsjfx_dm, jcsjfx_mc, jcsjfx_cjm, jcsjfx_fjd, szcc, xssx, sfmx, fx_ms, sy_zt from gz_dm_jcdm_fx where 1=1 ");
			
			if(jc_dm_id!=null && !"".equals(jc_dm_id)){
				querySql.append(" and jc_dm_id = '").append(jc_dm_id).append("'");
			}
			
			if(jcsjfx_dm!=null && !"".equals(jcsjfx_dm)){
				querySql.append(" and jcsjfx_dm like '").append(jcsjfx_dm).append("%'");
			}
			
			if(jcsjfx_mc!=null && !"".equals(jcsjfx_mc)){
				querySql.append(" and jcsjfx_mc like '%").append(jcsjfx_mc).append("%'");
			}
			
			querySql.append(" order by jcsjfx_dm ");
			stmt.addSqlStmt(querySql.toString());
			stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
			return stmt;
		}
	
}

