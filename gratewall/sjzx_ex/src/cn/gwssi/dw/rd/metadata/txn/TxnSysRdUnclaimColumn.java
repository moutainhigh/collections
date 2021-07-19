package cn.gwssi.dw.rd.metadata.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.metadata.vo.SysRdUnclaimColumnContext;

public class TxnSysRdUnclaimColumn extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdUnclaimColumn.class, SysRdUnclaimColumnContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_unclaim_column";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_unclaim_column list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_unclaim_column";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_unclaim_column";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_unclaim_column";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_unclaim_column";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdUnclaimColumn()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询未认领表字段列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002401( SysRdUnclaimColumnContext context ) throws TxnException
	{
		
		DataBus db = context.getRecord(inputNode);
		String unclaim_column_code = db.getValue("unclaim_column_code");
		if(unclaim_column_code!=null && !"".equals(unclaim_column_code)){
			unclaim_column_code = unclaim_column_code.toUpperCase();
			db.setValue("unclaim_column_code", unclaim_column_code);
		}
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysRdUnclaimColumnSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysRdUnclaimColumn result[] = context.getSysRdUnclaimColumns( outputNode );
	}
	
	/** 修改未认领表字段信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002402( SysRdUnclaimColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysRdUnclaimColumn sys_rd_unclaim_column = context.getSysRdUnclaimColumn( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加未认领表字段信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002403( SysRdUnclaimColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysRdUnclaimColumn sys_rd_unclaim_column = context.getSysRdUnclaimColumn( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询未认领表字段用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002404( SysRdUnclaimColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdUnclaimColumnPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdUnclaimColumn result = context.getSysRdUnclaimColumn( outputNode );
	}
	
	/** 删除未认领表字段信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002405( SysRdUnclaimColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysRdUnclaimColumnPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
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
		SysRdUnclaimColumnContext appContext = new SysRdUnclaimColumnContext( context );
		invoke( method, appContext );
	}
}
