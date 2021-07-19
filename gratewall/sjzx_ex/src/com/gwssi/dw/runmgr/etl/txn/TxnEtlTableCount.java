package com.gwssi.dw.runmgr.etl.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.runmgr.etl.vo.EtlTableCountContext;

public class TxnEtlTableCount extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnEtlTableCount.class, EtlTableCountContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "etl_table_count";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select etl_table_count list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one etl_table_count";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one etl_table_count";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one etl_table_count";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one etl_table_count";
	
	/**
	 * 构造函数
	 */
	public TxnEtlTableCount()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询中心库数据量统计表列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80000001( EtlTableCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "queryCountList", context, inputNode, outputNode );
//		System.out.println(context);
//		Recordset rs = context.getRecordset(outputNode);
//		String ent_type = "";
//		String etl_date = "";
//		TxnContext queryContext = null;
//        DataBus db = null;
//        if (rs != null && !rs.isEmpty()) {
//            for (int k = 0; k < rs.size(); k++) {
//            	db = rs.get(k);
//            	ent_type = db.getValue("ent_type");
//            	etl_date = db.getValue("etl_date");
//            	queryContext = new TxnContext();
//            	queryContext.getRecord("select-key").setValue("ent_type", ent_type);
//            	table.executeFunction( "getValue", queryContext, "select-key", "record" );
//            	db.setValue("ent_type", queryContext.getRecord("record").getValue("jcsjfx_mc"));
//            	if(etl_date!=null){
//            	      db.setValue("etl_date", etl_date.substring(0,10));
//            	}
//            }
//        } 
	}
	
	/** 中心库数据量统计表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80000002( EtlTableCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "queryCountByMc", context, inputNode, outputNode );
	}
	/** 增加中心库数据量统计表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80000003( EtlTableCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoEtlTableCount etl_table_count = context.getEtlTableCount( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}	
		
	
	/** 删除中心库数据量统计表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80000005( EtlTableCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoEtlTableCountPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	public void txn80000017( EtlTableCountContext context ) throws TxnException
	{
         
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
		EtlTableCountContext appContext = new EtlTableCountContext( context );
		invoke( method, appContext );
	}
}
