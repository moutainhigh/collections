package cn.gwssi.dw.rd.standard.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.common.util.UuidGenerator;
import cn.gwssi.dw.rd.standard.vo.SysRdStandardDataelementContext;

public class TxnSysRdStandardDataelement extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandardDataelement.class, SysRdStandardDataelementContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_standard_dataelement";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_standard_dataelement list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_standard_dataelement";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard_dataelement";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard_dataelement";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard_dataelement";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdStandardDataelement()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询基础数据元列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000301( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysRdStandardDataelementSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysRdStandardDataelement result[] = context.getSysRdStandardDataelements( outputNode );
	}
	
	/** 查询基础数据元概览列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7004401( SysRdStandardDataelementContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, 5);
		callService("7000301", context);
	}
	
	/** 修改基础数据元信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000302( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysRdStandardDataelement sys_rd_standard_dataelement = context.getSysRdStandardDataelement( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加基础数据元信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000303( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysRdStandardDataelement sys_rd_standard_dataelement = context.getSysRdStandardDataelement( inputNode );
		
		context.getRecord("record").setValue("sys_rd_standard_dataelement_id",UuidGenerator.getUUID());
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询基础数据元用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000304( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardDataelementPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandardDataelement result = context.getSysRdStandardDataelement( outputNode );
	}
	
	/** 删除基础数据元信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000305( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysRdStandardDataelementPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询基础数据元用于试图
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000306( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardDataelementPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandardDataelement result = context.getSysRdStandardDataelement( outputNode );
	}
	
	/** 查询基础数据元用于修改已认领表字段
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000307( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardDataelementPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( "selectonedataelement", context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandardDataelement result = context.getSysRdStandardDataelement( outputNode );
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
		SysRdStandardDataelementContext appContext = new SysRdStandardDataelementContext( context );
		invoke( method, appContext );
	}
}
