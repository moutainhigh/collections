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
import cn.gwssi.dw.aic.bj.ecomm.vo.EbSiteRelatTypeContext;

public class TxnEbSiteRelatType extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnEbSiteRelatType.class, EbSiteRelatTypeContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "eb_site_relat_type";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select eb_site_relat_type list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one eb_site_relat_type";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one eb_site_relat_type";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one eb_site_relat_type";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one eb_site_relat_type";
	
	/**
	 * 构造函数
	 */
	public TxnEbSiteRelatType()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询补充基本信息列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026201( EbSiteRelatTypeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoEbSiteRelatTypeSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoEbSiteRelatType result[] = context.getEbSiteRelatTypes( outputNode );
	}
	
	/** 修改补充基本信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026202( EbSiteRelatTypeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoEbSiteRelatType eb_site_relat_type = context.getEbSiteRelatType( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加补充基本信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026203( EbSiteRelatTypeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoEbSiteRelatType eb_site_relat_type = context.getEbSiteRelatType( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询补充基本信息用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026204( EbSiteRelatTypeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoEbSiteRelatTypePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoEbSiteRelatType result = context.getEbSiteRelatType( outputNode );
	}
	
	/** 删除补充基本信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026205( EbSiteRelatTypeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoEbSiteRelatTypePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		EbSiteRelatTypeContext appContext = new EbSiteRelatTypeContext( context );
		invoke( method, appContext );
	}
}
