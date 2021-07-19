package com.gwssi.dw.runmgr.etl.txn;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.dw.runmgr.etl.vo.OpbSubjectContext;

public class TxnOpbSubject extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnOpbSubject.class, OpbSubjectContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "opb_subject";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select opb_subject list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one opb_subject";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one opb_subject";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one opb_subject";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one opb_subject";
	
	/**
	 * ���캯��
	 */
	public TxnOpbSubject()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50105001( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoOpbSubjectSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoOpbSubject result[] = context.getOpbSubjects( outputNode );
	}
	
	/** �޸Ĳ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50105002( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoOpbSubject opb_subject = context.getOpbSubject( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���Ӳ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50105003( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoOpbSubject opb_subject = context.getOpbSubject( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50105004( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoOpbSubjectPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoOpbSubject result = context.getOpbSubject( outputNode );
	}
	
	/** ɾ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50105005( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoOpbSubjectPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * ��ȡ��������ѯ�б�
	 * @param context
	 * @throws TxnException
	 */
	public void txn50105006( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoOpbSubjectSelectKey selectKey = context.getSelectKey( inputNode );
		// TxnContext txnContext = new TxnContext();
		long before = java.util.Calendar.getInstance().getTimeInMillis();
		table.executeFunction( "loadOpbSubjectList", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoOpbSubject result[] = context.getOpbSubjects( outputNode );
		Recordset rs = context.getRecordset(outputNode);
		for (int i=0; i < rs.size(); i++){
			DataBus db = rs.get(i);
			String strWorkflowId = db.getValue("workflow_id");
			String dbuser = db.getValue("dbuser");
			TxnContext txnContextForSourceAndTarget = new TxnContext();
			txnContextForSourceAndTarget.getRecord("select-key").setValue("workflow_id", strWorkflowId);
			txnContextForSourceAndTarget.getRecord("select-key").setValue("dbuser", dbuser);
			table.executeFunction( "getSourceAndTarget", txnContextForSourceAndTarget, "select-key", "record" );
			String[] sourceAndTarget = generateSourceAndTarget(txnContextForSourceAndTarget);
			db.setValue("wf_db_source", sourceAndTarget[0]);
			db.setValue("wf_db_target", sourceAndTarget[1]);
		}
		long after = java.util.Calendar.getInstance().getTimeInMillis();
		System.out.println("ϵͳ��ִ�У�" + (after-before) + "����");
	}
	
	/**
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50105007( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoOpbSubjectSelectKey selectKey = context.getSelectKey( inputNode );
		// TxnContext txnContext = new TxnContext();
		long before = java.util.Calendar.getInstance().getTimeInMillis();
		table.executeFunction( "loadWorkflowRunList", context, inputNode, outputNode );
		// System.out.println(context);
		Recordset rs  = context.getRecordset(outputNode);
		for (int i = 0; i < rs.size(); i++){
			DataBus db = rs.get(i);
			if (db.getValue("first_error_code").equals("0")){
				db.setValue("status", "�ɹ�");
			}else{
				db.setValue("status", "ʧ��");
			}
		}
		long after = java.util.Calendar.getInstance().getTimeInMillis();
		System.out.println("ϵͳ��ִ�У�" + (after-before) + "����");
	}
	
	/**
	 * ��ѯ��־��ϸ��Ϣ
	 * @param context
	 * @throws TxnException
	 */
	public void txn50105008( OpbSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoOpbSubjectSelectKey selectKey = context.getSelectKey( inputNode );
		// TxnContext txnContext = new TxnContext();
		long before = java.util.Calendar.getInstance().getTimeInMillis();
		table.executeFunction( "loadWorkflowRunLog", context, inputNode, outputNode );
//		Recordset rs  = context.getRecordset(outputNode);
//		for (int i = 0; i < rs.size(); i++){
//			DataBus db = rs.get(i);
//			if (db.getValue("run_err_code").equals("0")){
//				db.setValue("status", "�ɹ�");
//			}else{
//				db.setValue("status", "ʧ��");
//			}
//		}
		long after = java.util.Calendar.getInstance().getTimeInMillis();
		System.out.println("ϵͳ��ִ�У�" + (after-before) + "����");
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws TxnException
	 */
	public String[] generateSourceAndTarget(TxnContext context) throws TxnException{
		String[] sourceAndTarget = new String[2];
		sourceAndTarget[0] = "";
		sourceAndTarget[1] = "";
		List sourceList = new ArrayList();
		List targetList = new ArrayList();
		Recordset rs = context.getRecordset("record");
		for (int i=0; i < rs.size(); i++)
		{
			DataBus db = rs.get(i);
			String isTarget = db.getString("is_target");
			if (isTarget.equals("1"))
			{
				String connection_name = db.getString("connection_name");
				boolean isExist = false;
				for (int n = 0; n < targetList.size() && isExist==false; n++){
					String temp = (String) targetList.get(n);
					if (connection_name.equals(temp)){
						isExist = true;
					}
				}
				if (isExist == false)
				{
					targetList.add(connection_name);
				}				
			}else if(isTarget.equals("0"))
			{
				String connection_name = db.getString("connection_name");
				boolean isExist = false;
				for (int n = 0; n < sourceList.size() && isExist==false; n++){
					String temp = (String) sourceList.get(n);
					if (connection_name.equals(temp)){
						isExist = true;
					}
				}
				if (isExist == false)
				{
					sourceList.add(connection_name);
				}
			}
		}
		sourceAndTarget[0] = sourceList.toString();
		sourceAndTarget[1] = targetList.toString();
		return sourceAndTarget;
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
		OpbSubjectContext appContext = new OpbSubjectContext( context );
		invoke( method, appContext );
	}
}
