package com.gwssi.dw.aic.download.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.download.vo.DownloadCheckPurvContext;

public class TxnDownloadCheckPurv extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnDownloadCheckPurv.class, DownloadCheckPurvContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "download_check_purv";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select download_check_purv list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one download_check_purv";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one download_check_purv";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one download_check_purv";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one download_check_purv";
	
	/**
	 * ���캯��
	 */
	public TxnDownloadCheckPurv()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ����Ȩ���б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60600001( DownloadCheckPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDownloadCheckPurvSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoDownloadCheckPurv result[] = context.getDownloadCheckPurvs( outputNode );
	}
	
	/** �޸�����Ȩ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60600002( DownloadCheckPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoDownloadCheckPurv download_check_purv = context.getDownloadCheckPurv( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��������Ȩ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60600003( DownloadCheckPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoDownloadCheckPurv download_check_purv = context.getDownloadCheckPurv( inputNode );
		System.out.println("context:" + context);
		DataBus db = context.getRecord(inputNode);
		String roleid = db.getValue("roleid");
		String[] rolesArray = null;
		if (roleid != null){
			rolesArray = roleid.split(",");
		}
		String min_size = db.getValue("min_size");
		String max_size = db.getValue("max_size");
		String download_purv_id = db.getValue("download_purv_id");
		String multi_download_check = db.getValue("multi_download_check");
		
		for (int i = 0; i < rolesArray.length; i++){
			if (rolesArray[i] != null && !rolesArray[i].trim().equals("")){
				DataBus insertDb = new DataBus();
				insertDb.setValue("download_check_purv_id", com.gwssi.common.util.UuidGenerator.getUUID());
				insertDb.setValue("roleid", rolesArray[i].trim());
				insertDb.setValue("max_size", max_size);
				insertDb.setValue("min_size", min_size);
				insertDb.setValue("download_purv_id", download_purv_id);
				insertDb.setValue("multi_download_check", multi_download_check);
				context.addRecord("insert", insertDb);
			}
		}
		table.executeFunction( INSERT_FUNCTION, context, "insert", outputNode );
	}
	
	/** ��ѯ����Ȩ�������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60600004( DownloadCheckPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDownloadCheckPurvPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoDownloadCheckPurv result = context.getDownloadCheckPurv( outputNode );
	}
	
	/** ɾ������Ȩ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60600005( DownloadCheckPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoDownloadCheckPurvPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	/**
	 * ��ȡ��ɫ����������Ȩ����Ϣ
	 * @param context
	 * @throws TxnException
	 */
	public void txn60600006( DownloadCheckPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String role_list = context.getRecord("oper-data").getValue("role-list");
		role_list = role_list.replaceAll(";", "','");
		role_list = "'" + role_list + "'";
		DataBus db = new DataBus();
		db.setValue("role-list", role_list);
		context.addRecord("roleInfo", db);
		Attribute.setPageRow(context, "downloadCheckPurv", -1);
		table.executeFunction( "getDownloadCheckPurvInfo", context, "roleInfo", "downloadCheckPurv" );
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
		DownloadCheckPurvContext appContext = new DownloadCheckPurvContext( context );
		invoke( method, appContext );
	}
}
