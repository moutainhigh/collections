package com.gwssi.dw.aic.bj.newmon.mov.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.bj.newmon.mov.vo.MonMovDomContext;

public class TxnMonMovDom extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnMonMovDom.class, MonMovDomContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "mon_mov_dom";
	
	
	/**
	 * 构造函数
	 */
	public TxnMonMovDom()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
    /**
     * 异地经营企业信息列表
     * TxnMonMovDom:txn81120001 
     * @creater - caiwd
     * @creatertime - Nov 20, 2008
     * @param context
     * @throws TxnException
     * @returnType void
     */
	public void txn81120001( MonMovDomContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryMonMovDom_List", context, inputNode, outputNode );
	}
	
	/**
	 * 查看异地经营详细信息
	 * TxnMonMovDom:txn81120002 
	 * @creater - caiwd
	 * @creatertime - Nov 20, 2008
	 * @param context
	 * @throws TxnException
	 * @returnType void
	 */
	public void txn81120002( MonMovDomContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "viewMonMovDom_Detail", context, inputNode, outputNode );
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
		MonMovDomContext appContext = new MonMovDomContext( context );
		invoke( method, appContext );
	}
}
