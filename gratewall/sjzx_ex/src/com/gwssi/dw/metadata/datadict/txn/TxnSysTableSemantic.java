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
import com.gwssi.dw.metadata.datadict.vo.SysTableSemanticContext;

public class TxnSysTableSemantic extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysTableSemantic.class, SysTableSemanticContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_table_semantic";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_table_semantic list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_table_semantic";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_table_semantic";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_table_semantic";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_table_semantic";
	
	/**
	 * ���캯��
	 */
	public TxnSysTableSemantic()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯҵ����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30402001( SysTableSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		Recordset rs = context.getRecordset("record");
		DataBus rsDb = null;
		for(int i=0;i<rs.size();i++){
			rsDb = rs.get(i);
			rsDb.setValue("table_order", rsDb.getValue("sys_no") + "_" + rsDb.getValue("table_order"));
		}
	}
	
	/** �޸�ҵ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30402002( SysTableSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String sys_id= context.getRecord(inputNode).getValue("sys_id");
		String table_no = context.getRecord(inputNode).getValue("table_no");
		String table_name_cn = context.getRecord(inputNode).getValue("table_name_cn");
		String table_name = context.getRecord(inputNode).getValue("table_name");
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("type", "modify");
		txnContext.getRecord("select-key").setValue("sys_id", sys_id);
		txnContext.getRecord("select-key").setValue("table_no", table_no);
		txnContext.getRecord("select-key").setValue("table_name_cn", table_name_cn);
		txnContext.getRecord("select-key").setValue("table_name", table_name);
		int i = table.executeFunction("validateTableName", txnContext, "select-key", "record");
		if (i > 0)
		{
			throw new TxnDataException("999999","ͬһҵ��ϵͳ��ҵ������ظ��������һ��ҵ�������");
		}
		int j = table.executeFunction("validateTableNameCn", txnContext, "select-key", "record");
		if (j > 0)
		{
			throw new TxnDataException("999999","ͬһҵ��ϵͳ��ҵ����������ظ��������һ��ҵ�����������");
		}
		// �޸ļ�¼������ VoSysTableSemantic sys_table_semantic = context.getSysTableSemantic( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		setBizLog("�޸�ҵ���", context,table_name);
	}
	
	/** ����ҵ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30402003( SysTableSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		String sys_id = context.getRecord(inputNode).getValue("sys_id");
		String table_no = context.getRecord(inputNode).getValue("table_no");
		String table_name_cn = context.getRecord(inputNode).getValue("table_name_cn");
		String table_name = context.getRecord(inputNode).getValue("table_name");
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("type", "add");
		txnContext.getRecord("select-key").setValue("sys_id", sys_id);
		txnContext.getRecord("select-key").setValue("table_no", table_no);
		txnContext.getRecord("select-key").setValue("table_name_cn", table_name_cn);
		txnContext.getRecord("select-key").setValue("table_name", table_name);
//		int k = table.executeFunction("validateTablePrimaryKey", txnContext, "select-key", "record");
//		if (k > 0)
//		{
//			throw new TxnDataException("999999","ҵ�������ظ��������һ��ҵ�����룡");
//		}
		int i = table.executeFunction("validateTableName", txnContext, "select-key", "record");
		if (i > 0)
		{
			throw new TxnDataException("999999","ͬһҵ��ϵͳ��ҵ������ظ��������һ��ҵ�������");
		}
		int j = table.executeFunction("validateTableNameCn", txnContext, "select-key", "record");
		if (j > 0)
		{
			throw new TxnDataException("999999","ͬһҵ��ϵͳ��ҵ����������ظ��������һ��ҵ�����������");
		}
		
		// ���Ӽ�¼������ VoSysTableSemantic sys_table_semantic = context.getSysTableSemantic( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		setBizLog("����ҵ���", context,table_name);
	}
	
	/** ��ѯҵ��������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30402004( SysTableSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysTableSemanticPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysTableSemantic result = context.getSysTableSemantic( outputNode );
	}
	
	/** ɾ��ҵ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30402005( SysTableSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		Recordset rs = context.getRecordset("primary-key");
		String table_names = "";
		for (int i=0; i < rs.size(); i++)
		{
			DataBus db = rs.get(i);
			String table_no = db.getValue("table_no");
			TxnContext txnContext = new TxnContext();
			txnContext.getRecord("select-key").setValue("table_no", table_no);
			int index = table.executeFunction("validateTableCanBeDelete", txnContext, "select-key", "record");
			if(index > 0)
			{
				throw new TxnDataException("","ɾ��ҵ���ǰ��Ҫ��ɾ��ҵ����µ������ֶ�!");
			}
			table_names+= "," + rs.get(i).getValue("table_name");
		}		
		if(!table_names.equals("")) table_names = table_names.substring(1);
		
		// ɾ����¼�������б� VoSysTableSemanticPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		setBizLog("ɾ��ҵ���", context,table_names);
	}
	
	/** ��ѯҵ����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30402006( SysTableSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "select sys_table_semantic list by system", context, inputNode, outputNode );
		Recordset rs = context.getRecordset("record");
		DataBus rsDb = null;
		for(int i=0;i<rs.size();i++){
			rsDb = rs.get(i);
			rsDb.setValue("table_order", rsDb.getValue("sys_no") + "_" + rsDb.getValue("table_order"));
		}
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
		SysTableSemanticContext appContext = new SysTableSemanticContext( context );
		invoke( method, appContext );
	}
}
