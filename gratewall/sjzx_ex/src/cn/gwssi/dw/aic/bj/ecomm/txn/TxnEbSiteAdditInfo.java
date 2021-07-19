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
import cn.gwssi.dw.aic.bj.ecomm.vo.EbSiteAdditInfoContext;

public class TxnEbSiteAdditInfo extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnEbSiteAdditInfo.class, EbSiteAdditInfoContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "eb_site_addit_info";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select eb_site_addit_info list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one eb_site_addit_info";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one eb_site_addit_info";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one eb_site_addit_info";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one eb_site_addit_info";
	
	/**
	 * 构造函数
	 */
	public TxnEbSiteAdditInfo()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询建站信息列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026101( EbSiteAdditInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoEbSiteAdditInfoSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoEbSiteAdditInfo result[] = context.getEbSiteAdditInfos( outputNode );
	}
	
	/** 修改建站信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026102( EbSiteAdditInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoEbSiteAdditInfo eb_site_addit_info = context.getEbSiteAdditInfo( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加建站信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026103( EbSiteAdditInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoEbSiteAdditInfo eb_site_addit_info = context.getEbSiteAdditInfo( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询建站信息用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026104( EbSiteAdditInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoEbSiteAdditInfoPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoEbSiteAdditInfo result = context.getEbSiteAdditInfo( outputNode );
	}
	
	/** 删除建站信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026105( EbSiteAdditInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoEbSiteAdditInfoPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查看建站信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026106( EbSiteAdditInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoEbSiteAdditInfo eb_site_addit_info = context.getEbSiteAdditInfo( inputNode );
		table.executeFunction( "viewSiteAdditInfo", context, inputNode, outputNode );
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
		EbSiteAdditInfoContext appContext = new EbSiteAdditInfoContext( context );
		invoke( method, appContext );
	}
}
