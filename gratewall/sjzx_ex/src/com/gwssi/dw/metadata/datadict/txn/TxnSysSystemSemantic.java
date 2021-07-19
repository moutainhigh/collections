package com.gwssi.dw.metadata.datadict.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.metadata.datadict.vo.SysSystemSemanticContext;

public class TxnSysSystemSemantic extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysSystemSemantic.class, SysSystemSemanticContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_system_semantic";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_system_semantic list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_system_semantic";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_system_semantic";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_system_semantic";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_system_semantic";
	
	/**
	 * ���캯��
	 */
	public TxnSysSystemSemantic()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯϵͳ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30401001( SysSystemSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysSystemSemanticSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysSystemSemantic result[] = context.getSysSystemSemantics( outputNode );
	}
	
	/** �޸�ϵͳ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30401002( SysSystemSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String sys_name = context.getRecord(inputNode).getValue("sys_name");
		String sys_no = context.getRecord(inputNode).getValue("sys_no");
		String sys_id = context.getRecord("primary-key").getValue("sys_id");
		String sys_simple = context.getRecord(inputNode).getValue("sys_simple");
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("sys_no", sys_no);
		txnContext.getRecord("select-key").setValue("sys_name", sys_name);
		txnContext.getRecord("select-key").setValue("sys_id", sys_id);
		txnContext.getRecord("select-key").setValue("sys_simple", sys_simple);
		txnContext.getRecord("select-key").setValue("type", "modify");

		int n = table.executeFunction("validateSysNo", txnContext, "select-key", "record");
		if (n > 0)
		{
			throw new TxnDataException("999999","��������ظ��������һ��������룡");
		}			
		int j = table.executeFunction("validateSysName", txnContext, "select-key", "record");
		if (j > 0)
		{
			throw new TxnDataException("999999","���������ظ��������һ���������ƣ�");
		}
		int k = table.executeFunction("validateSysSimpleName", txnContext, "select-key", "record");
		if (k > 0)
		{
			throw new TxnDataException("999999","�������ظ��������һ�������ƣ�");
		}	
		
		// �޸ļ�¼������ VoSysSystemSemantic sys_system_semantic = context.getSysSystemSemantic( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		setBizLog("�޸����⣺", context,sys_name);
	}
	
	/** ����ϵͳ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30401003( SysSystemSemanticContext context ) throws TxnException
	{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String sys_name = context.getRecord(inputNode).getValue("sys_name");
		String sys_no = context.getRecord(inputNode).getValue("sys_no");
		String sys_simple = context.getRecord(inputNode).getValue("sys_simple");
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("sys_name", sys_name);
		txnContext.getRecord("select-key").setValue("sys_no", sys_no);
		txnContext.getRecord("select-key").setValue("sys_simple", sys_simple);
		txnContext.getRecord("select-key").setValue("type", "add");
		int i = table.executeFunction("validateSysPrimaryKey", txnContext, "select-key", "record");
		if (i > 0)
		{
			throw new TxnDataException("999999","ϵͳ�����ظ��������һ��ϵͳ���룡");
		}
		int j = table.executeFunction("validateSysName", txnContext, "select-key", "record");
		if (j > 0)
		{
			throw new TxnDataException("999999","ϵͳ�����ظ��������һ��ϵͳ���ƣ�");
		}
		int k = table.executeFunction("validateSysSimpleName", txnContext, "select-key", "record");
		if (k > 0)
		{
			throw new TxnDataException("999999","ϵͳ����ظ��������һ��ϵͳ��ƣ�");
		}
		// ���Ӽ�¼������ VoSysSystemSemantic sys_system_semantic = context.getSysSystemSemantic( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		setBizLog("�������⣺", context,sys_name);
	}
	
	/** ��ѯϵͳ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30401004( SysSystemSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysSystemSemanticPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysSystemSemantic result = context.getSysSystemSemantic( outputNode );
	}
	
	/** ɾ��ϵͳ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30401005( SysSystemSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Recordset rs = context.getRecordset("primary-key");
		String sys_names = "";
		for (int i=0; i < rs.size(); i++)
		{
			DataBus db = rs.get(i);
			String sys_no = db.getValue("sys_id");
			TxnContext txnContext = new TxnContext();
			txnContext.getRecord("select-key").setValue("sys_no", sys_no);
			int index = table.executeFunction("validateSysCanBeDelete", txnContext, "select-key", "record");
			if(index > 0)
			{
				throw new TxnDataException("999999","ɾ��ҵ��ϵͳǰ��Ҫ��ɾ��ҵ��ϵͳ�µ����б�!");
			}
			sys_names+= "," + rs.get(i).getValue("sys_name");
		}		
		if(!sys_names.equals("")) sys_names = sys_names.substring(1);
		
		// ɾ����¼�������б� VoSysSystemSemanticPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		setBizLog("ɾ�����⣺", context,sys_names);
	}
	/**
	 * ��¼��־
	 * @param type
	 * @param context
	 */
    private void setBizLog (String type,TxnContext context,String jgmc){
    	
    	context.getRecord("biz_log").setValue("desc", type + jgmc);
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
		SysSystemSemanticContext appContext = new SysSystemSemanticContext( context );
		invoke( method, appContext );
	}
}
