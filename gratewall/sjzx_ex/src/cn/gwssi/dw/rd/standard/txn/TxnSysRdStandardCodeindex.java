package cn.gwssi.dw.rd.standard.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.standard.vo.SysRdStandardCodeindexContext;

public class TxnSysRdStandardCodeindex extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandardCodeindex.class, SysRdStandardCodeindexContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_standard_codeindex";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_standard_codeindex list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_standard_codeindex";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard_codeindex";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard_codeindex";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard_codeindex";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdStandardCodeindex()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询代码集列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000401( SysRdStandardCodeindexContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysRdStandardCodeindexSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysRdStandardCodeindex result[] = context.getSysRdStandardCodeindexs( outputNode );
	}
	
	/** 修改代码集信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000402( SysRdStandardCodeindexContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysRdStandardCodeindex sys_rd_standard_codeindex = context.getSysRdStandardCodeindex( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加代码集信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000403( SysRdStandardCodeindexContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysRdStandardCodeindex sys_rd_standard_codeindex = context.getSysRdStandardCodeindex( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询代码集用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000404( SysRdStandardCodeindexContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardCodeindexPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandardCodeindex result = context.getSysRdStandardCodeindex( outputNode );
	}
	
	/** 删除代码集信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000405( SysRdStandardCodeindexContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysRdStandardCodeindexPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	/** 查询代码集用于视图
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000406( SysRdStandardCodeindexContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardCodeindexPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandardCodeindex result = context.getSysRdStandardCodeindex( outputNode );
		
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
		SysRdStandardCodeindexContext appContext = new SysRdStandardCodeindexContext( context );
		invoke( method, appContext );
	}
}
