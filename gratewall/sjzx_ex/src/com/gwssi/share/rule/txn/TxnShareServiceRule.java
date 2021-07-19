package com.gwssi.share.rule.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.share.rule.vo.ShareServiceRuleContext;

public class TxnShareServiceRule extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnShareServiceRule.class, ShareServiceRuleContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "share_service_rule";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select share_service_rule list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one share_service_rule";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one share_service_rule";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one share_service_rule";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one share_service_rule";
	
	/**
	 * ���캯��
	 */
	public TxnShareServiceRule()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ���ʹ����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn403001( ShareServiceRuleContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoShareServiceRuleSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoShareServiceRule result[] = context.getShareServiceRules( outputNode );
	}
	
	/** �޸ķ��ʹ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn403002( ShareServiceRuleContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoShareServiceRule share_service_rule = context.getShareServiceRule( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӷ��ʹ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn403003( ShareServiceRuleContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoShareServiceRule share_service_rule = context.getShareServiceRule( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ���ʹ��������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn403004( ShareServiceRuleContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoShareServiceRulePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoShareServiceRule result = context.getShareServiceRule( outputNode );
	}
	
	/** ɾ�����ʹ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn403005( ShareServiceRuleContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoShareServiceRulePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 
	 * txn403007(���ݷ���IDɾ����Ӧ��¼)    
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
	public void txn403007( ShareServiceRuleContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoShareServiceRulePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "delByService_ID", context, inputNode, outputNode );
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
		ShareServiceRuleContext appContext = new ShareServiceRuleContext( context );
		invoke( method, appContext );
	}
}
