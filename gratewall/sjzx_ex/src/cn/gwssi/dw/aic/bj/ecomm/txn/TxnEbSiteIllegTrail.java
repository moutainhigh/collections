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
import cn.gwssi.dw.aic.bj.ecomm.vo.EbSiteIllegTrailContext;

public class TxnEbSiteIllegTrail extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnEbSiteIllegTrail.class, EbSiteIllegTrailContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "eb_site_illeg_trail";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select eb_site_illeg_trail list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one eb_site_illeg_trail";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one eb_site_illeg_trail";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one eb_site_illeg_trail";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one eb_site_illeg_trail";
	
	/**
	 * 构造函数
	 */
	public TxnEbSiteIllegTrail()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询违法线索列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026401( EbSiteIllegTrailContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoEbSiteIllegTrailSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoEbSiteIllegTrail result[] = context.getEbSiteIllegTrails( outputNode );
	}
	
	/** 修改违法线索信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026402( EbSiteIllegTrailContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoEbSiteIllegTrail eb_site_illeg_trail = context.getEbSiteIllegTrail( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加违法线索信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026403( EbSiteIllegTrailContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoEbSiteIllegTrail eb_site_illeg_trail = context.getEbSiteIllegTrail( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询违法线索用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026404( EbSiteIllegTrailContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoEbSiteIllegTrailPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoEbSiteIllegTrail result = context.getEbSiteIllegTrail( outputNode );
	}
	
	/** 删除违法线索信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026405( EbSiteIllegTrailContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoEbSiteIllegTrailPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询违法线索列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026406( EbSiteIllegTrailContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoEbSiteIllegTrailSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryIllegTrail", context, inputNode, outputNode );
		// 查询到的记录集 VoEbSiteIllegTrail result[] = context.getEbSiteIllegTrails( outputNode );
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
		EbSiteIllegTrailContext appContext = new EbSiteIllegTrailContext( context );
		invoke( method, appContext );
	}
}
