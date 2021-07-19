package com.gwssi.dw.runmgr.etl.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.runmgr.etl.vo.EtlTableCountContext;

public class TxnEtlTableCount extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnEtlTableCount.class, EtlTableCountContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "etl_table_count";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select etl_table_count list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one etl_table_count";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one etl_table_count";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one etl_table_count";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one etl_table_count";
	
	/**
	 * ���캯��
	 */
	public TxnEtlTableCount()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ���Ŀ�������ͳ�Ʊ��б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80000001( EtlTableCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "queryCountList", context, inputNode, outputNode );
//		System.out.println(context);
//		Recordset rs = context.getRecordset(outputNode);
//		String ent_type = "";
//		String etl_date = "";
//		TxnContext queryContext = null;
//        DataBus db = null;
//        if (rs != null && !rs.isEmpty()) {
//            for (int k = 0; k < rs.size(); k++) {
//            	db = rs.get(k);
//            	ent_type = db.getValue("ent_type");
//            	etl_date = db.getValue("etl_date");
//            	queryContext = new TxnContext();
//            	queryContext.getRecord("select-key").setValue("ent_type", ent_type);
//            	table.executeFunction( "getValue", queryContext, "select-key", "record" );
//            	db.setValue("ent_type", queryContext.getRecord("record").getValue("jcsjfx_mc"));
//            	if(etl_date!=null){
//            	      db.setValue("etl_date", etl_date.substring(0,10));
//            	}
//            }
//        } 
	}
	
	/** ���Ŀ�������ͳ�Ʊ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80000002( EtlTableCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		table.executeFunction( "queryCountByMc", context, inputNode, outputNode );
	}
	/** �������Ŀ�������ͳ�Ʊ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80000003( EtlTableCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoEtlTableCount etl_table_count = context.getEtlTableCount( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}	
		
	
	/** ɾ�����Ŀ�������ͳ�Ʊ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80000005( EtlTableCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoEtlTableCountPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	public void txn80000017( EtlTableCountContext context ) throws TxnException
	{
         
	}		
	/**
	 * ���ظ���ķ����������滻���׽ӿڵ��������
	 * ���ú���
	 * @param funcName ��������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����"
			);
		}
		
		// ִ��
		EtlTableCountContext appContext = new EtlTableCountContext( context );
		invoke( method, appContext );
	}
}
