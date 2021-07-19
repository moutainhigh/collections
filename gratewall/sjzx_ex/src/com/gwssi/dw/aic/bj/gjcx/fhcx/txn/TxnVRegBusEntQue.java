package com.gwssi.dw.aic.bj.gjcx.fhcx.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.bj.gjcx.fhcx.vo.VRegBusEntQueContext;

public class TxnVRegBusEntQue extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnVRegBusEntQue.class, VRegBusEntQueContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "v_reg_bus_ent_que";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select v_reg_bus_ent_que list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "${mod.function.select}";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "${mod.function.insert}";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "${mod.function.delete}";
	
	/**
	 * 构造函数
	 */
	public TxnVRegBusEntQue()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询复合列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60220001( VRegBusEntQueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		SqlStatement sql = table.getSqlStatement("queryEntList", context,context.getRecord(inputNode));
		if(sql == null)
			return;
		//String[] s= sql.getSqlStmt().toArray();
		//log.debug(s[0]);
		table.executeFunction( "queryEntList", context, inputNode, outputNode );
	}
	
	/** 选择代码集
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60220002( VRegBusEntQueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "queryJcdmfx", context, inputNode, outputNode );
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
		VRegBusEntQueContext appContext = new VRegBusEntQueContext( context );
		invoke( method, appContext );
	}
}
