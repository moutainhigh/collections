package com.gwssi.collect.etl.txn;

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
import com.gwssi.collect.etl.vo.EtlSubjectContext;
import com.gwssi.collect.webservice.vo.CollectTaskContext;
import com.runqian.report4.model.expression.function.Count;

public class TxnEtlSubject extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnEtlSubject.class, EtlSubjectContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "etl_subject";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select etl_subject list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one etl_subject";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one etl_subject";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one etl_subject";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one etl_subject";
	
	/**
	 * ���캯��
	 */
	public TxnEtlSubject()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯetl�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30300001( EtlSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �޸�etl������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30300002( EtlSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoEtlSubject etl_subject = context.getEtlSubject( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����etl������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30300003( EtlSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoEtlSubject etl_subject = context.getEtlSubject( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯetl���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30300004( EtlSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEtlSubjectPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoEtlSubject result = context.getEtlSubject( outputNode );
	}
	
	/** ɾ��etl������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30300005( EtlSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoEtlSubjectPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 
	 * ��ѯETL������ϸ
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn30300006(EtlSubjectContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("getEtlMinWorkRunId", context, inputNode, "minId");
		System.out.println(context.getRecord("minId"));
		table.executeFunction("getEtlWorkletTree", context, inputNode,
				outputNode);
		context.getRecord(inputNode).setValue("workflow_run_id",
				context.getRecord("minId").getValue("workflow_run_id"));
	}

	/**
	 * 
	 * ��ѯETL��������ϸ
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn30300007(EtlSubjectContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getEtlTaskDetail", context, inputNode,outputNode);
	}
	
	/**
	 * 
	 * txn30300008(������һ�仰�����������������)    
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
	public void txn30300008( EtlSubjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEtlSubjectPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		//table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		table.executeFunction( "getEtlInfo", context, inputNode, outputNode );
		Attribute.setPageRow(context, "subject", -1);
		table.executeFunction("getCollectTableDetail", context, inputNode,"subject");
		Attribute.setPageRow(context, "subjectNum", -1);
		table.executeFunction("getSubjectNum", context, inputNode,"subjectNum");
		//System.out.println("size=="+context.getRecordset("subject").size());
		//System.out.println("size=="+context.getRecordset("subjectNum").size());
		// ��ѯ���ļ�¼���� VoEtlSubject result = context.getEtlSubject( outputNode );
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
		EtlSubjectContext appContext = new EtlSubjectContext( context );
		invoke( method, appContext );
	}
}
