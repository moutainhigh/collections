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
import cn.gwssi.dw.aic.bj.ecomm.vo.EbEntLicInfoContext;

public class TxnEbEntLicInfo extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnEbEntLicInfo.class, EbEntLicInfoContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "eb_ent_lic_info";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select eb_ent_lic_info list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one eb_ent_lic_info";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one eb_ent_lic_info";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one eb_ent_lic_info";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one eb_ent_lic_info";
	
	/**
	 * 构造函数
	 */
	public TxnEbEntLicInfo()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询网站许可证信息列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026301( EbEntLicInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoEbEntLicInfoSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoEbEntLicInfo result[] = context.getEbEntLicInfos( outputNode );
	}
	
	/** 修改网站许可证信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026302( EbEntLicInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoEbEntLicInfo eb_ent_lic_info = context.getEbEntLicInfo( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加网站许可证信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026303( EbEntLicInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoEbEntLicInfo eb_ent_lic_info = context.getEbEntLicInfo( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询网站许可证信息用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026304( EbEntLicInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoEbEntLicInfoPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoEbEntLicInfo result = context.getEbEntLicInfo( outputNode );
	}
	
	/** 删除网站许可证信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026305( EbEntLicInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoEbEntLicInfoPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询网站许可证信息列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn6026306( EbEntLicInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoEbEntLicInfoSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryWebLicInfo", context, inputNode, outputNode );
		// 查询到的记录集 VoEbEntLicInfo result[] = context.getEbEntLicInfos( outputNode );
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
		EbEntLicInfoContext appContext = new EbEntLicInfoContext( context );
		invoke( method, appContext );
	}
}
