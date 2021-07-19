package cn.gwssi.dw.aic.bj.ecomm.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.aic.bj.ecomm.vo.EbSysRightUserContext;

public class TxnEbSysRightUser extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnEbSysRightUser.class, EbSysRightUserContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "eb_sys_right_user";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select eb_sys_right_user list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one eb_sys_right_user";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "${mod.function.insert}";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "${mod.function.delete}";
	
	/**
	 * 构造函数
	 */
	public TxnEbSysRightUser()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询电子商务用户表列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026601( EbSysRightUserContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoEbSysRightUserSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoEbSysRightUser result[] = context.getEbSysRightUsers( outputNode );
	}
	
	/** 修改电子商务用户表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026602( EbSysRightUserContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoEbSysRightUser eb_sys_right_user = context.getEbSysRightUser( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加电子商务用户表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026603( EbSysRightUserContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoEbSysRightUser eb_sys_right_user = context.getEbSysRightUser( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询电子商务用户表用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026604( EbSysRightUserContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoEbSysRightUserPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoEbSysRightUser result = context.getEbSysRightUser( outputNode );
	}
	
	/** 删除电子商务用户表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026605( EbSysRightUserContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoEbSysRightUserPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		EbSysRightUserContext appContext = new EbSysRightUserContext( context );
		invoke( method, appContext );
	}
}
