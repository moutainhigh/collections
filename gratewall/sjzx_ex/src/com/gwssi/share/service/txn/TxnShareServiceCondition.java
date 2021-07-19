package com.gwssi.share.service.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.share.service.vo.ShareServiceConditionContext;

public class TxnShareServiceCondition extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnShareServiceCondition.class, ShareServiceConditionContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "share_service_condition";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select share_service_condition list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one share_service_condition";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one share_service_condition";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one share_service_condition";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one share_service_condition";
	
	/**
	 * ���캯��
	 */
	public TxnShareServiceCondition()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�����ѯ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40210001( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoShareServiceConditionSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoShareServiceCondition result[] = context.getShareServiceConditions( outputNode );
	}
	
	/** �޸ķ����ѯ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40210002( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoShareServiceCondition share_service_condition = context.getShareServiceCondition( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӷ����ѯ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40210003( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoShareServiceCondition share_service_condition = context.getShareServiceCondition( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�����ѯ���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40210004( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoShareServiceConditionPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoShareServiceCondition result = context.getShareServiceCondition( outputNode );
	}
	
	/** ɾ�������ѯ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40210005( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoShareServiceConditionPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	/**
	 * 
	 * txn40210006(���ݷ���IDɾ������)    
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
	public void txn40210006( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoShareServiceConditionPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "deleteConditionsByShareServiceID", context, inputNode, outputNode );
	}
	
	/** ��ѯ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40210101( ShareServiceConditionContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoShareServiceConditionSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryCondition", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoShareServiceCondition result[] = context.getShareServiceConditions( outputNode );
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
		ShareServiceConditionContext appContext = new ShareServiceConditionContext( context );
		invoke( method, appContext );
	}
}
