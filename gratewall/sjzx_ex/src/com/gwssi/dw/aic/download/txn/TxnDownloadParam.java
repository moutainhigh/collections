package com.gwssi.dw.aic.download.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.download.vo.DownloadParamContext;

public class TxnDownloadParam extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnDownloadParam.class, DownloadParamContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "download_param";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select download_param list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one download_param";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one download_param";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one download_param";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one download_param";
	
	/**
	 * ���캯��
	 */
	public TxnDownloadParam()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ���ز����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60400001( DownloadParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDownloadParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoDownloadParam result[] = context.getDownloadParams( outputNode );
	}
	
	/** �޸����ز�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60400002( DownloadParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoDownloadParam download_param = context.getDownloadParam( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �������ز�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60400003( DownloadParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoDownloadParam download_param = context.getDownloadParam( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, "param", "param" );
	}
	
	/** ��ѯ���ز��������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60400004( DownloadParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDownloadParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoDownloadParam result = context.getDownloadParam( outputNode );
	}
	
	/** ɾ�����ز�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60400005( DownloadParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoDownloadParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * ��ȡ�ֶ�ȫ����Ϣ
	 * @param context
	 * @throws TxnException
	 */
	public void txn60400006( DownloadParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoDownloadParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "getColumnInfo", context, inputNode, outputNode );
		//System.out.println(context);
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord(inputNode).setValue("download_status_id", 
				context.getRecord(inputNode).getValue("download_status_id"));
		this.callService("com.gwssi.dw.aic.download.txn.TxnDownloadStatus", "txn60300004", txnContext);
		//System.out.println(txnContext);
		context.addRecord("download", txnContext.getRecord(outputNode));
		// table.executeFunction("getDownloadInfo", context, inputNode, "download");
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
		DownloadParamContext appContext = new DownloadParamContext( context );
		invoke( method, appContext );
	}
}
