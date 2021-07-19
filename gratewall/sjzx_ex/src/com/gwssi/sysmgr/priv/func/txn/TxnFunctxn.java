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
   // ���ݱ�����
   private final String TABLE_NAME = "functxn";
   
   // ����������
   private final String TABLE_PARTYINFO = "party_info";

   // ��ѯ�б�
   private final String ROWSET_FUNCTION = "select functxn list";

   // ��ѯ��¼
   private final String SELECT_FUNCTION = "select one functxn";

   // �޸ļ�¼
   private final String UPDATE_FUNCTION = "update one functxn";

   // ���Ӽ�¼
   private final String INSERT_FUNCTION = "insert one functxn";

   // ɾ����¼
   private final String DELETE_FUNCTION = "delete one functxn";

   public TxnFunctxn()
   {
      
   }

   /** ��ʼ������
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }

   /** ��ѯ�б�
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980311( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
	}

   /** �޸ļ�¼
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980312( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}

   /** ���Ӽ�¼
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980313( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}

   /** ��ѯ��¼
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980314( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}

   /** ɾ����¼
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980315( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}

	/**
	 * ������ѯ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn980316( TxnContext context ) throws TxnException
	{
		// ���
		Recordset list = new Recordset();
		StringBuffer selectedList = new StringBuffer();
		HashMap selectedAction = new HashMap();

		// ��ʱ����
		String txnCode;
		DataBus value;
		
		// ���ļ�ȡ�����б�
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
				
				// �ж��Ƿ���Ҫ��Ȩ
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
		
		// ȡ�Ѿ����õĽ����б�
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
		
		// ���ؽ��
		context.put( outputNode, list );
		context.setAttribute( outputNode, Attribute.KEYVALUE_NODE, selectedList.toString() );
	}
	
	/**
	 * �������ӽ����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn980317( TxnContext context ) throws TxnException
	{
		// ���
		Recordset list = new Recordset();
		
		// ���ܴ���
		String funcCode = context.getString( "select-key:funccode" );
		
		// ѡ�еļ�¼
		String	selectColumns = context.getAttribute( "record", Attribute.KEYVALUE_NODE );
		selectColumns = ";" + selectColumns + ";";

		// ȡ�����б�
		Recordset txnList = context.getRecordset( "record" );
		if( txnList.size() == 0 ){
			return;
		}
		
		// ����Field
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
		
		// ����ѡ�еļ�¼��
		context.setValue( "record", list );
		
		// �����¼
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "batchAddTxnList", context, "record", null );
	}
	
	/**
	 * �Ƿ��̨Ȩ�޿��ƽ���
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn980318( TxnContext context ) throws TxnException
	{	
		
	}
	
	/**
	 * ����������ƽ���
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn980319( TxnContext context ) throws TxnException
	{	
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_PARTYINFO );
		table.executeFunction( "getFirstNode", context, inputNode, outputNode );
	}
}
