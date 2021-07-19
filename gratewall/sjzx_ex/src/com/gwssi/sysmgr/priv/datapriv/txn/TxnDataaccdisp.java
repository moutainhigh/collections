package com.gwssi.sysmgr.priv.datapriv.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.sysmgr.priv.datapriv.PrivilegeManager;
import com.gwssi.sysmgr.priv.datapriv.vo.DataaccdispContext;

public class TxnDataaccdisp extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnDataaccdisp.class, DataaccdispContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "dataaccdisp";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select dataaccdisp list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "${mod.function.select}";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one dataaccdisp";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one dataaccdisp";
	
	/**
	 * 构造函数
	 */
	public TxnDataaccdisp()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询数据权限分配列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103041( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDataaccdispSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoDataaccdisp result[] = context.getDataaccdisps( outputNode );
	}
	
	/** 修改数据权限分配信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103042( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoDataaccdisp dataaccdisp = context.getDataaccdisp( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加数据权限分配信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103043( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoDataaccdisp dataaccdisp = context.getDataaccdisp( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询数据权限分配用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103044( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoDataaccdispPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoDataaccdisp result = context.getDataaccdisp( outputNode );
	}
	
	/** 删除数据权限分配信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103045( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoDataaccdispPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 根据角色访问的交易列表获取数据权限列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103046( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDataaccdispSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "loadRoleTxnDataAcc", context, inputNode, outputNode );
		// 查询到的记录集 VoDataaccdisp result[] = context.getDataaccdisps( outputNode );
	}
	
	/** 根据角色功能ID删除分配的数据权限
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103047( DataaccdispContext context ) throws TxnException
	{
		try {
			BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
			// 删除记录的主键列表 VoDataaccdispPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
			table.executeFunction( "deleteDispByObjIdAndDispObj" , context, inputNode, outputNode );
		} catch (TxnException e) {
			e.printStackTrace();
		}
	}
	
	/** 根据角色功能ID删除分配的数据权限
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103048( DataaccdispContext context ) throws TxnException
	{
		String objectids = context.getRecord(inputNode).getValue("objectids");
		String dataaccgrpids = context.getRecord(inputNode).getValue("dataaccgrpids");
		String dataaccdispobj = context.getRecord(inputNode).getValue("dataaccdispobj");
		
		String[] objectid = objectids.split(",");
		String[] dataaccgrpid = dataaccgrpids.split(",");
		
		for(int i = 0; i < objectid.length; i++){
			for(int j = 0; j < dataaccgrpid.length; j++){
				DataaccdispContext delete = new DataaccdispContext();
				DataBus db = new DataBus();
				db.setValue("objectid", objectid[i]);
				db.setValue("dataaccgrpid", dataaccgrpid[j]);
				db.setValue("dataaccdispobj", dataaccdispobj);
				delete.addRecord(inputNode,db);
				
				doService("txn103045",delete);
			}
		}
		try{
			PrivilegeManager.getInst().init("统计制度");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/** 根据角色访问的交易ID获取自定义权限组ID
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103049( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDataaccdispSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "loadRoleCustomAcc", context, inputNode, outputNode );
		// 查询到的记录集 VoDataaccdisp result[] = context.getDataaccdisps( outputNode );
	}

	/** 根据数据权限组ID串删除数据权限分配信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn1030410( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoDataaccdispPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "delete many", context, inputNode, outputNode );
	}
	
	/** 根据角色列表获取数据权限列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn1030411( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDataaccdispSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "loadRoleDataAcc", context, inputNode, outputNode );
		// 查询到的记录集 VoDataaccdisp result[] = context.getDataaccdisps( outputNode );
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
		DataaccdispContext appContext = new DataaccdispContext( context );
		invoke( method, appContext );
	}
}
