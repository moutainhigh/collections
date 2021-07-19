package com.gwssi.dw.metadata.datadict.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
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
import com.gwssi.dw.metadata.datadict.vo.SysColumnSemanticContext;

public class TxnSysColumnSemantic extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysColumnSemantic.class, SysColumnSemanticContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_column_semantic";
	
	// ��ѯ�б�
	//private static final String ROWSET_FUNCTION = "select sys_column_semantic list";
	
	private static final String ROWSET_FUNCTION = "queryList";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_column_semantic";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_column_semantic";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_column_semantic";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_column_semantic";
	
	/**
	 * ���캯��
	 */
	public TxnSysColumnSemantic()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯҵ���ֶ��б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30403001( SysColumnSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysColumnSemanticSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysColumnSemantic result[] = context.getSysColumnSemantics( outputNode );
	}
	
	/** �޸�ҵ���ֶ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30403002( SysColumnSemanticContext context ) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		DataBus db_rec = context.getRecord("record");
		TxnContext txnContext = new TxnContext();
		DataBus db_sel = txnContext.getRecord("select-key");
		String column_name = db_rec.getValue("column_name");
		// type����������ӻ����޸�.
		db_sel.setValue("type", "modify");
		db_sel.setValue("column_no", db_rec.getValue("column_no"));
		db_sel.setValue("column_name", column_name);
		db_sel.setValue("column_name_cn", db_rec.getValue("column_name_cn"));
		db_sel.setValue("table_no", db_rec.getValue("table_no"));
		int j = table.executeFunction( "validateColumnName", txnContext, "select-key", "record" );
		if (j > 0)
		{
			throw new TxnDataException("999999","ͬһҵ������ֶ����ظ��������һ���ֶ�����");
		}
		int k = table.executeFunction( "validateColumnNameCn", txnContext, "select-key", "record" );
		if (k > 0)
		{
			throw new TxnDataException("999999","ͬһҵ������ֶ��������ظ��������һ���ֶ���������");
		}
		String old_column_name = db_rec.getValue("old_column_name");
		if(!column_name.equals(old_column_name)){
			
			String column_byname = "";
			int m = table.executeFunction( "queryColumnByName", txnContext, "select-key", "record" );
			if (m > 0){
				table.executeFunction( "queryMaxAlias", txnContext, "select-key", "record" );
				String alias = txnContext.getRecord("record").getValue("column_byname");
				if(alias==null||alias.equals("")){
					column_byname = "ALIAS1000001";
				}else{
					int count = Integer.parseInt(alias.substring(5)) + 1;
					String newCount = String.valueOf(count);
					int reCount = newCount.length();
					column_byname = alias.substring(0, 12-reCount) + newCount;
				}
			}else{
				column_byname = "";
			}
			context.getRecord("record").setValue("column_byname", column_byname);			
		}
		// �޸ļ�¼������ VoSysColumnSemantic sys_column_semantic = context.getSysColumnSemantic( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		setBizLog("�޸�ҵ���ֶΣ�", context,column_name);
	}
	
	/** ����ҵ���ֶ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30403003( SysColumnSemanticContext context ) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		DataBus db_rec = context.getRecord("record");
		TxnContext txnContext = new TxnContext();
		DataBus db_sel = txnContext.getRecord("select-key");
		String column_name = db_rec.getValue("column_name");
		// type����������ӻ����޸�.
		db_sel.setValue("type", "add");
		db_sel.setValue("column_no", db_rec.getValue("column_no"));
		db_sel.setValue("column_name", column_name);
		db_sel.setValue("column_name_cn", db_rec.getValue("column_name_cn"));
		db_sel.setValue("table_no", db_rec.getValue("table_no"));
//		int i = table.executeFunction( "validateColumnPrimaryKey", txnContext, "select-key", "record" );
//		if (i > 0)
//		{
//			throw new TxnDataException("999999","�ֶα����ظ��������һ���ֶα��룡");
//		}
		int j = table.executeFunction( "validateColumnName", txnContext, "select-key", "record" );
		if (j > 0){
			throw new TxnDataException("999999","ͬһҵ������ֶ����ظ��������һ���ֶ�����");
		}
		int k = table.executeFunction( "validateColumnNameCn", txnContext, "select-key", "record" );
		if (k > 0){
			throw new TxnDataException("999999","ͬһҵ������ֶ��������ظ��������һ���ֶ���������");
		}
		// ���Ӽ�¼������ VoSysColumnSemantic sys_column_semantic = context.getSysColumnSemantic( inputNode );
		String column_byname = "";
		int m = table.executeFunction( "queryColumnByName", txnContext, "select-key", "record" );
		if (m > 0){
			table.executeFunction( "queryMaxAlias", txnContext, "select-key", "record" );
			String alias = txnContext.getRecord("record").getValue("column_byname");
			if(alias==null||alias.equals("")){
				column_byname = "ALIAS1000001";
			}else{
				int count = Integer.parseInt(alias.substring(5)) + 1;
				String newCount = String.valueOf(count);
				int reCount = newCount.length();
				column_byname = alias.substring(0, 12-reCount) + newCount;
			}
		}
		context.getRecord("record").setValue("column_byname", column_byname);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		setBizLog("����ҵ���ֶΣ�", context,column_name);
	}
	
	/** ��ѯҵ���ֶ������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30403004( SysColumnSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysColumnSemanticPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysColumnSemantic result = context.getSysColumnSemantic( outputNode );
	}
	
	/** ɾ��ҵ���ֶ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30403005( SysColumnSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Recordset rs = context.getRecordset("primary-key");
		String column_names = "";
		for (int i=0; i < rs.size(); i++)
		{
			column_names+= "," + rs.get(i).getValue("column_name");
		}		
		if(!column_names.equals("")) column_names = column_names.substring(1);		
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		setBizLog("ɾ��ҵ���ֶΣ�", context,column_names);
	}
	
	/**
	 * У���ֶ��ܷ����
	 * @param context
	 * @throws TxnException
	 */
	public void txn30403006( SysColumnSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		// this.callService("cn.gwssi.gwaic.metadata.txn.TxnSysColumnSemantic", "txn30403003", context);
	}
	
	/**
	 * ���ݱ�����ѯҵ���ֶ��б�
	 * @param context
	 * @throws TxnException
	 */
	public void txn30403007( SysColumnSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getColumnListByTableName", context, inputNode, outputNode);
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
		SysColumnSemanticContext appContext = new SysColumnSemanticContext( context );
		invoke( method, appContext );
	}
}
