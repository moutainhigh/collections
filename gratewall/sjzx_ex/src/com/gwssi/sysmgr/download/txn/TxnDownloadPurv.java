package com.gwssi.sysmgr.download.txn;

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
import com.gwssi.sysmgr.download.vo.DownloadPurvContext;

public class TxnDownloadPurv extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnDownloadPurv.class, DownloadPurvContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "download_purv";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select download_purv list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one download_purv";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one download_purv";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one download_purv";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one download_purv";
	
	/**
	 * ���캯��
	 */
	public TxnDownloadPurv()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ���������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn105001( DownloadPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//Attribute.setPageRow(context, "record", -1);
		table.executeFunction( ROWSET_FUNCTION, context, "select-key", "record" );
		Recordset rs = context.getRecordset("record");
		for(int i=0; i < rs.size(); i++){
			DataBus db = rs.get(i);
			db.setValue("jgmc", db.getValue("sjjgname") + db.getValue("jgmc"));
			db.setValue("expand", "true");
		}
	}
	
	/** ��ѯ���������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn1050011( DownloadPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, "record", -1);
		table.executeFunction( ROWSET_FUNCTION, context, "select-key", "record" );
		Recordset rs = context.getRecordset("record");
		for(int i=0; i < rs.size(); i++){
			DataBus db = rs.get(i);
			db.setValue("jgmc", db.getValue("sjjgname") + db.getValue("jgmc"));
			db.setValue("expand", "true");
		}
	}
	
	/** �޸�����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn105002( DownloadPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoDownloadPurv download_purv = context.getDownloadPurv( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		String agency_id = context.getRecord(inputNode).getValue("agency_id");
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("jgid_pk", agency_id);
		table.executeFunction("getOrgName", txnContext, "select-key", "record");
		context.getRecord("biz_log").setValue("desc", "�޸� " + txnContext.getRecord("record").getValue("jgmc") + " ������Ȩ��");
	}
	
	/** ��������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn105003( DownloadPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoDownloadPurv download_purv = context.getDownloadPurv( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn105004( DownloadPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDownloadPurvPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoDownloadPurv result = context.getDownloadPurv( outputNode );
	}
	
	/** ɾ������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn105005( DownloadPurvContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoDownloadPurvPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	
	public void txn105006( DownloadPurvContext context ) throws TxnException
	{
		// ȡ���û���Ϣ
		String agencyId = context.getRecord("oper-data").getValue("org-code");
		context.getRecord("select-key").setValue("agency_id", agencyId);
		this.callService("com.gwssi.sysmgr.download.txn.TxnDownloadPurv", "txn105001", context);
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
		DownloadPurvContext appContext = new DownloadPurvContext( context );
		invoke( method, appContext );
	}
}
