package com.gwssi.sysmgr.priv.datapriv.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.sysmgr.priv.datapriv.PrivilegeManager;
import com.gwssi.sysmgr.priv.datapriv.vo.DataaccdispContext;

public class TxnDataaccdisp extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnDataaccdisp.class, DataaccdispContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "dataaccdisp";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select dataaccdisp list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "${mod.function.select}";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one dataaccdisp";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one dataaccdisp";
	
	/**
	 * ���캯��
	 */
	public TxnDataaccdisp()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ����Ȩ�޷����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103041( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDataaccdispSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoDataaccdisp result[] = context.getDataaccdisps( outputNode );
	}
	
	/** �޸�����Ȩ�޷�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103042( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoDataaccdisp dataaccdisp = context.getDataaccdisp( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��������Ȩ�޷�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103043( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoDataaccdisp dataaccdisp = context.getDataaccdisp( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ����Ȩ�޷��������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103044( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDataaccdispPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoDataaccdisp result = context.getDataaccdisp( outputNode );
	}
	
	/** ɾ������Ȩ�޷�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103045( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoDataaccdispPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ݽ�ɫ���ʵĽ����б��ȡ����Ȩ���б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103046( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDataaccdispSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "loadRoleTxnDataAcc", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoDataaccdisp result[] = context.getDataaccdisps( outputNode );
	}
	
	/** ���ݽ�ɫ����IDɾ�����������Ȩ��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103047( DataaccdispContext context ) throws TxnException
	{
		try {
			BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
			// ɾ����¼�������б� VoDataaccdispPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
			table.executeFunction( "deleteDispByObjIdAndDispObj" , context, inputNode, outputNode );
		} catch (TxnException e) {
			e.printStackTrace();
		}
	}
	
	/** ���ݽ�ɫ����IDɾ�����������Ȩ��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103048( DataaccdispContext context ) throws TxnException
	{
		String objectids = context.getRecord(inputNode).getValue("objectids");
		String dataaccgrpids = context.getRecord(inputNode).getValue("dataaccgrpids");
		String dataaccdispobj = context.getRecord(inputNode).getValue("dataaccdispobj");
		
		String[] objectid = objectids.split(",");
		String[] dataaccgrpid = dataaccgrpids.split(",");
		
		for(int i = 0; i < objectid.length; i++){
			for(int j = 0; j < dataaccgrpid.length; j++){
				DataaccdispContext delete = new DataaccdispContext();
				DataBus db = new DataBus();
				db.setValue("objectid", objectid[i]);
				db.setValue("dataaccgrpid", dataaccgrpid[j]);
				db.setValue("dataaccdispobj", dataaccdispobj);
				delete.addRecord(inputNode,db);
				
				doService("txn103045",delete);
			}
		}
		try{
			PrivilegeManager.getInst().init("ͳ���ƶ�");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/** ���ݽ�ɫ���ʵĽ���ID��ȡ�Զ���Ȩ����ID
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103049( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDataaccdispSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "loadRoleCustomAcc", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoDataaccdisp result[] = context.getDataaccdisps( outputNode );
	}

	/** ��������Ȩ����ID��ɾ������Ȩ�޷�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn1030410( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoDataaccdispPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "delete many", context, inputNode, outputNode );
	}
	
	/** ���ݽ�ɫ�б��ȡ����Ȩ���б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn1030411( DataaccdispContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDataaccdispSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "loadRoleDataAcc", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoDataaccdisp result[] = context.getDataaccdisps( outputNode );
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
		DataaccdispContext appContext = new DataaccdispContext( context );
		invoke( method, appContext );
	}
}
