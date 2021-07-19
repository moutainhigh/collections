package com.gwssi.dw.aic.bj.homepage.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.bj.homepage.vo.WorkStatusContext;

public class TxnWorkStatus extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnWorkStatus.class, WorkStatusContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "work_status";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select work_status list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one work_status";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one work_status";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one work_status";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one work_status";
	
	/**
	 * ���캯��
	 */
	public TxnWorkStatus()
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
	public void txn54000001( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		pubDestroy(table, context);
		this.callService("com.gwssi.dw.aic.download.txn.TxnDownloadCheckPurv", "txn60600006", context);
		Attribute.setPageRow(context, "ws-record", 20);
		table.executeFunction( "queryTotalData", context, "ws-query", "ws-record" );
		//System.out.println("----"+context.getRecordset(""));
	}
	
	/** �޸Ĵ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn54000002( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoWorkStatus work_status = context.getWorkStatus( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, "ws-record", "ws-record" );
	}
	
	/** ���Ӵ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn54000003( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoWorkStatus work_status = context.getWorkStatus( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, "record", "record" );
	}
	
	/** ��ѯ�������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn54000004( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoWorkStatusPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoWorkStatus result = context.getWorkStatus( outputNode );
	}
	
	/** ɾ������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn54000005( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoWorkStatusPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	
	/**
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn54000006( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );	
		pubDestroy(table, context);
		this.callService("com.gwssi.dw.aic.download.txn.TxnDownloadCheckPurv", "txn60600006", context);
		Attribute.setPageRow(context, "ws-record", 10);
		table.executeFunction( "queryTotalData", context, "ws-query", "ws-record" );
	}
	
	/**
	 * ��ȡ����ĸ���
	 * @param context
	 * @throws TxnException
	 */
	public void txn54000007( WorkStatusContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		pubDestroy(table, context);
		this.callService("com.gwssi.dw.aic.download.txn.TxnDownloadCheckPurv", "txn60600006", context);
		Attribute.setPageRow(context, "ws-record", 20);
		table.executeFunction( "queryTotalDataCount", context, "ws-query", "record" );
		context.getRecordset("downloadCheckPurv").clear();
		context.getRecordset("ws-query").clear();
		//System.out.println("----"+context);
	}
	
	/**
	 * 
	 * @param table
	 * @param context
	 * @throws TxnException
	 */
	public void pubDestroy( BaseTable table, WorkStatusContext context ) throws TxnException{
		String roleList = context.getRecord("oper-data").getValue("role-list");
//		System.out.println("ת��ǰ:" + roleList);
		roleList = "'" + roleList.replaceAll(";", "','") + "'";
//		System.out.println("ת����:" + roleList);
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("roleList", roleList);
		table.executeFunction("getRoleList", txnContext, "select-key", "record");
		Recordset rs = txnContext.getRecordset("record");
		String totalList = "";
		for (int i=0; i < rs.size(); i++){
			String temp = rs.get(i).getValue("funclist").trim();
			if(temp != null && !temp.equals("")){
				temp = temp.substring(temp.length()-1).equals(";") ? temp : temp+";";
			}
			totalList += temp;
		}
//		System.out.println("ȫ������Ȩ�ޣ�" + totalList);
		String inStr = totalList.replaceAll(";", "','");
		inStr = "'" + inStr.substring(0, inStr.length() - 2);
//		System.out.println("ȫ������Ȩ�ޣ�" + inStr);
		
		// ����ֻ��ȡ��Ч��ʶΪ1������
		context.getRecord("ws-query").setValue("funclist", inStr);
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
		WorkStatusContext appContext = new WorkStatusContext( context );
		invoke( method, appContext );
	}
}
