package com.gwssi.resource.share.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.resource.share.vo.ResShareTableContext;

public class TxnResShareTable extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnResShareTable.class, ResShareTableContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "res_share_table";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select res_share_table list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one res_share_table";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one res_share_table";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one res_share_table";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one res_share_table";
	
	/**
	 * ���캯��
	 */
	public TxnResShareTable()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20301001( ResShareTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoResShareTableSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoResShareTable result[] = context.getResShareTables( outputNode );
	}
	
	/** �޸Ĺ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20301002( ResShareTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoResShareTable res_share_table = context.getResShareTable( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӹ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20301003( ResShareTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoResShareTable res_share_table = context.getResShareTable( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ����������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20301004( ResShareTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoResShareTablePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoResShareTable result = context.getResShareTable( outputNode );
	}
	
	/** ɾ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn20301005( ResShareTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoResShareTablePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	/**
	 * 
	 * txn20301006(�����������������ı�)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn20301006( ResShareTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		table.executeFunction( "quertShareTableByTopic", context, inputNode, outputNode );
		//System.out.println(context);
		
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
		ResShareTableContext appContext = new ResShareTableContext( context );
		invoke( method, appContext );
	}
}
