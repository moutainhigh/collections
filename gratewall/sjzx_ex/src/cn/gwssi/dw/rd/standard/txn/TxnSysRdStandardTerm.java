package cn.gwssi.dw.rd.standard.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.gwssi.common.util.UuidGenerator;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.common.util.UuidGenerator;
import cn.gwssi.dw.rd.standard.vo.SysRdStandardTermContext;

public class TxnSysRdStandardTerm extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandardTerm.class, SysRdStandardTermContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_standard_term";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_standard_term list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_standard_term";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard_term";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard_term";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard_term";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdStandardTerm()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询术语列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000101( SysRdStandardTermContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysRdStandardTermSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysRdStandardTerm result[] = context.getSysRdStandardTerms( outputNode );
	}
	
	/** 修改术语信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000102( SysRdStandardTermContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysRdStandardTerm sys_rd_standard_term = context.getSysRdStandardTerm( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加术语信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000103( SysRdStandardTermContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysRdStandardTerm sys_rd_standard_term = context.getSysRdStandardTerm( inputNode );
		String Str=UuidGenerator.getUUID();
		context.getRecord("record").setValue("sys_rd_standar_term_id", Str);
		System.out.println(context);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		
	}
	
	/** 查询术语用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000104( SysRdStandardTermContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardTermPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandardTerm result = context.getSysRdStandardTerm( outputNode );
	}
	
	/** 删除术语信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000105( SysRdStandardTermContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysRdStandardTermPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询术语用于视图
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000106( SysRdStandardTermContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardTermPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandardTerm result = context.getSysRdStandardTerm( outputNode );
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
		SysRdStandardTermContext appContext = new SysRdStandardTermContext( context );
		invoke( method, appContext );
	}
}
