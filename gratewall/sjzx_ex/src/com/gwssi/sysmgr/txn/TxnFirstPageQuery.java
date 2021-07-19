package com.gwssi.sysmgr.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.sysmgr.vo.FirstPageQueryContext;

public class TxnFirstPageQuery extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnFirstPageQuery.class, FirstPageQueryContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "first_page_query_tj";
	/**
	 * 构造函数
	 */
	public TxnFirstPageQuery()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询人员登录列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn61000001( FirstPageQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String date_start = context.getRecord(inputNode).getValue("query_date_start");
		String date_end = context.getRecord(inputNode).getValue("query_date_end");
		table.executeFunction( "queryXtjgYh", context, inputNode, outputNode );
		Recordset rs= context.getRecordset(outputNode);
		int m,n;
		m=rs.size();
		DataBus databus = null;
		String username;
		String count = "";
		String sfyx = "";
		for(n=0;n<m;n++){
			
			databus=rs.get(n);			
			username=databus.getValue("username");
			sfyx=databus.getValue("sfyx");
			FirstPageQueryContext countContext = new FirstPageQueryContext();
			DataBus countDb = countContext.getRecord("record");
			countDb.setValue("username", username);
			countDb.setValue("date_start", date_start);
			countDb.setValue("date_end", date_end);
			table.executeFunction( "queryCountByYh", countContext, "record", "record" );
			DataBus reDb= countContext.getRecord("record");
			count = reDb.getValue("count");
			if(sfyx==null||!sfyx.equals("0")){
				if(count!=null&&count.equals("0")){
					databus.clear();
				}else{
					databus.setValue("sfyx", "停用");
				}
			}else{
				databus.setValue("sfyx", "启用");
			}
			databus.setValue("count", count);					
		}	
	}
	
	/** 详细信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn61000002( FirstPageQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryDetailYhdlcs", context, inputNode, outputNode );
	}
	/** 查询分局列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn61000003( FirstPageQueryContext context ) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String date_start = context.getRecord(inputNode).getValue("query_date_start");
		String date_end = context.getRecord(inputNode).getValue("query_date_end");
		table.executeFunction( "queryXtjgFj", context, inputNode, outputNode );
		Recordset rs= context.getRecordset(outputNode);
		int m,n;
		m=rs.size();
		DataBus databus = null;
		String sjjgid;
		String count = "";
		for(n=0;n<m;n++){
			
			databus=rs.get(n);			
			sjjgid=databus.getValue("sjjgid");
			FirstPageQueryContext countContext = new FirstPageQueryContext();
			DataBus countDb = countContext.getRecord("record");
			countDb.setValue("sjjgid", sjjgid);
			countDb.setValue("date_start", date_start);
			countDb.setValue("date_end", date_end);
			table.executeFunction( "queryCountByFj", countContext, "record", "record" );
			DataBus reDb= countContext.getRecord("record");
			count = reDb.getValue("count");
			databus.setValue("count", count);					
		}		
	}
	/** 查询科室列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn61000004( FirstPageQueryContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String date_start = context.getRecord(inputNode).getValue("query_date_start");
		String date_end = context.getRecord(inputNode).getValue("query_date_end");
		table.executeFunction( "queryXtjgKs", context, inputNode, outputNode );
		Recordset rs= context.getRecordset(outputNode);
		int m,n;
		m=rs.size();
		DataBus databus = null;
		String orgid;
		String count = "";
		for(n=0;n<m;n++){
			
			databus=rs.get(n);			
			orgid=databus.getValue("orgid");
			FirstPageQueryContext countContext = new FirstPageQueryContext();
			DataBus countDb = countContext.getRecord("record");
			countDb.setValue("orgid", orgid);
			countDb.setValue("date_start", date_start);
			countDb.setValue("date_end", date_end);
			table.executeFunction( "queryCountByKs", countContext, "record", "record" );
			DataBus reDb= countContext.getRecord("record");
			count = reDb.getValue("count");
			databus.setValue("count", count);					
		}	
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
		FirstPageQueryContext appContext = new FirstPageQueryContext( context );
		invoke( method, appContext );
	}
}
