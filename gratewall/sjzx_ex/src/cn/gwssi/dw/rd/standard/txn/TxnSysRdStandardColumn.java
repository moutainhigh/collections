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
import com.gwssi.common.util.UuidGenerator;
import cn.gwssi.dw.rd.standard.vo.SysRdStandardColumnContext;

public class TxnSysRdStandardColumn extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandardColumn.class, SysRdStandardColumnContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_standard_column";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_standard_column list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_standard_column";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard_column";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard_column";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard_column";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdStandardColumn()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询指标项列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000221( SysRdStandardColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysRdStandardColumnSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysRdStandardColumn result[] = context.getSysRdStandardColumns( outputNode );
	}
	
	/** 修改指标项信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000222( SysRdStandardColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysRdStandardColumn sys_rd_standard_column = context.getSysRdStandardColumn( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加指标项信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000223( SysRdStandardColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysRdStandardColumn sys_rd_standard_column = context.getSysRdStandardColumn( inputNode );
		context.getRecord("record").setValue("sys_rd_standard_column_id",UuidGenerator.getUUID());
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询指标项用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000224( SysRdStandardColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardColumnPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandardColumn result = context.getSysRdStandardColumn( outputNode );
	}
	
	/** 删除指标项信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000225( SysRdStandardColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysRdStandardColumnPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询指标项用于视图
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000226( SysRdStandardColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardColumnPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandardColumn result = context.getSysRdStandardColumn( outputNode );
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
		SysRdStandardColumnContext appContext = new SysRdStandardColumnContext( context );
		invoke( method, appContext );
	}
}
