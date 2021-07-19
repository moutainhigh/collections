package com.gwssi.dw.aic.bj.newmon.ent.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.bj.newmon.ent.vo.MonEntTskContext;

public class TxnMonEntTsk extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnMonEntTsk.class, MonEntTskContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "mon_ent_tsk";
		
	/**
	 * 构造函数
	 */
	public TxnMonEntTsk()
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
	 * 根据主体名称查询检查任务列表
	 * TxnMonEntTsk:txn81119001 
	 * @creater - caiwd
	 * @creatertime - Nov 19, 2008
	 * @param context
	 * @throws TxnException
	 * @returnType void
	 */
	public void txn81119001( MonEntTskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryMonEntTsk_List", context, inputNode, outputNode );
		
	}
	
	/**
	 * 查看检查任务详细信息
	 * TxnMonEntTsk:txn81119002 
	 * @creater - caiwd
	 * @creatertime - Nov 20, 2008
	 * @param context
	 * @throws TxnException
	 * @returnType void
	 */
	public void txn81119002( MonEntTskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "viewMonEntTsk_Detail", context, inputNode, outputNode );
		
		String mon_mis_id = context.getString("record:mon_mis_id");
		if(null!= mon_mis_id && !"".equals(mon_mis_id)){
			table.executeFunction("queryMonBussQusAndType_List", context, "record", "que-type");
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
		MonEntTskContext appContext = new MonEntTskContext( context );
		invoke( method, appContext );
	}
}
