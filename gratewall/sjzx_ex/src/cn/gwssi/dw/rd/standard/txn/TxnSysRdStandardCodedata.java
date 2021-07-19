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
import cn.gwssi.dw.rd.standard.vo.SysRdStandardCodedataContext;

public class TxnSysRdStandardCodedata extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandardCodedata.class, SysRdStandardCodedataContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_standard_codedata";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_standard_codedata list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_standard_codedata";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard_codedata";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard_codedata";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard_codedata";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdStandardCodedata()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询基础代码列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000411( SysRdStandardCodedataContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysRdStandardCodedataSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysRdStandardCodedata result[] = context.getSysRdStandardCodedatas( outputNode );
	}
	
	/** 修改基础代码信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000412( SysRdStandardCodedataContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysRdStandardCodedata sys_rd_standard_codedata = context.getSysRdStandardCodedata( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加基础代码信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000413( SysRdStandardCodedataContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysRdStandardCodedata sys_rd_standard_codedata = context.getSysRdStandardCodedata( inputNode );
		context.getRecord("record").setValue("id", UuidGenerator.getUUID());//添加ID
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询基础代码用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000414( SysRdStandardCodedataContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardCodedataPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandardCodedata result = context.getSysRdStandardCodedata( outputNode );
	}
	
	/** 删除基础代码信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000415( SysRdStandardCodedataContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysRdStandardCodedataPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		SysRdStandardCodedataContext appContext = new SysRdStandardCodedataContext( context );
		invoke( method, appContext );
	}
}
