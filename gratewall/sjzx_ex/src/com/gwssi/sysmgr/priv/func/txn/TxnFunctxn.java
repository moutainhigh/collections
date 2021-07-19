package com.gwssi.sysmgr.priv.func.txn;

import java.util.HashMap;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoCodeItem;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.common.web.config.ActionDefine;

public class TxnFunctxn extends TxnService
{
   // 数据表名称
   private final String TABLE_NAME = "functxn";
   
   // 党建表名称
   private final String TABLE_PARTYINFO = "party_info";

   // 查询列表
   private final String ROWSET_FUNCTION = "select functxn list";

   // 查询记录
   private final String SELECT_FUNCTION = "select one functxn";

   // 修改记录
   private final String UPDATE_FUNCTION = "update one functxn";

   // 增加记录
   private final String INSERT_FUNCTION = "insert one functxn";

   // 删除记录
   private final String DELETE_FUNCTION = "delete one functxn";

   public TxnFunctxn()
   {
      
   }

   /** 初始化函数
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }

   /** 查询列表
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980311( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
	}

   /** 修改记录
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980312( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}

   /** 增加记录
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980313( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}

   /** 查询记录
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980314( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}

   /** 删除记录
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980315( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}

	/**
	 * 批量查询交易列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn980316( TxnContext context ) throws TxnException
	{
		// 结果
		Recordset list = new Recordset();
		StringBuffer selectedList = new StringBuffer();
		HashMap selectedAction = new HashMap();

		// 临时变量
		String txnCode;
		DataBus value;
		
		// 从文件取交易列表
		String fileName = context.getString( "select-key:file-name" );
		context.put( "input-data:filename", fileName );
		Recordset codeList = PublicResource.getCodeFactory().getValueListFromClass( context, 
				"cn.gwssi.console.param.ParamStruts", "getAllTxnCodeList"
		);
		
		if( codeList != null ){
			int ptr;
			String checkType;
			String txnName;
			for( int ii=0; ii<codeList.size(); ii++ ){
				VoCodeItem item = new VoCodeItem( codeList.get(ii) );
				value = new DataBus();
				
				// 判断是否需要授权
				checkType = item.getDescription();
				if( checkType != null && checkType.length() != 0 ){
					if( checkType.compareTo(ActionDefine.AUTH_PUBLIC) == 0 ||
							checkType.compareTo(ActionDefine.AUTH_LOGIN) == 0 )
					{
						continue;
					}
				}
				
				txnCode = item.getCodevalue();
				txnName = item.getCodename();
				ptr = txnName.indexOf("[");
				if( ptr > 0 ){
					txnName = txnName.substring(0, ptr);
				}
				
				value.setValue( "txncode", txnCode );
				value.setValue( "txnname", txnName );
				list.add( value );
				selectedAction.put( txnCode, value );
			}
		}
		
		// 取已经配置的交易列表
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		try{
			codeList = context.getRecordset( outputNode );
		}
		catch( TxnException e ){
			codeList = null;
		}
		
		if( codeList != null ){
			for( int ii=0; ii<codeList.size(); ii++ ){
				DataBus data = codeList.get(ii);
				txnCode = data.getValue( "txncode" );
				selectedList.append( txnCode );
				selectedList.append( ";" );
				
				value = (DataBus)selectedAction.get(txnCode);
				if( value == null ){
					list.add( data );
				}
				else{
					value.setValue( "txnname", data.getValue("txnname") );
					value.setValue( "memo", data.getValue("memo") );
				}
			}
		}
		
		// 返回结果
		context.put( outputNode, list );
		context.setAttribute( outputNode, Attribute.KEYVALUE_NODE, selectedList.toString() );
	}
	
	/**
	 * 批量增加交易列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn980317( TxnContext context ) throws TxnException
	{
		// 结果
		Recordset list = new Recordset();
		
		// 功能代码
		String funcCode = context.getString( "select-key:funccode" );
		
		// 选中的记录
		String	selectColumns = context.getAttribute( "record", Attribute.KEYVALUE_NODE );
		selectColumns = ";" + selectColumns + ";";

		// 取交易列表
		Recordset txnList = context.getRecordset( "record" );
		if( txnList.size() == 0 ){
			return;
		}
		
		// 增加Field
		DataBus	data;
		String	txnCode;
		for( int ii=0; ii<txnList.size(); ii++ ){
			data = txnList.get(ii);
			txnCode = ";" + data.getValue("txncode") + ";";
			if( selectColumns.indexOf(txnCode) >= 0 ){
				data.setValue( "funccode", funcCode );
				list.add( data );
			}
		}
		
		// 设置选中的记录集
		context.setValue( "record", list );
		
		// 保存记录
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "batchAddTxnList", context, "record", null );
	}
	
	/**
	 * 非烽火台权限控制交易
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn980318( TxnContext context ) throws TxnException
	{	
		
	}
	
	/**
	 * 党建报表控制交易
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn980319( TxnContext context ) throws TxnException
	{	
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_PARTYINFO );
		table.executeFunction( "getFirstNode", context, inputNode, outputNode );
	}
}
