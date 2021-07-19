package com.gwssi.dw.aic.download.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.download.vo.DownloadColumnContext;

public class TxnDownloadColumn extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnDownloadColumn.class, DownloadColumnContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "download_column";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select download_column list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one download_column";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one download_column";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one download_column";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one download_column";
	
	/**
	 * ���캯��
	 */
	public TxnDownloadColumn()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�ֶ��б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60501001( DownloadColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDownloadColumnSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoDownloadColumn result[] = context.getDownloadColumns( outputNode );
	}
	
	/** �޸��ֶ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60501002( DownloadColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoDownloadColumn download_column = context.getDownloadColumn( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �����ֶ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60501003( DownloadColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoDownloadColumn download_column = context.getDownloadColumn( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�ֶ������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60501004( DownloadColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDownloadColumnPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoDownloadColumn result = context.getDownloadColumn( outputNode );
	}
	
	/** ɾ���ֶ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60501005( DownloadColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoDownloadColumnPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
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
		DownloadColumnContext appContext = new DownloadColumnContext( context );
		invoke( method, appContext );
	}
}
