package com.gwssi.dw.runmgr.db.txn;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

public class TxnSysDbConfigParam extends TxnService
{
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_db_config_param";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_db_config_param list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_db_config_param";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_db_config_param";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_db_config_param";
	
	// ɾ����¼
	private static final String DELETE_LIST_FUNCTION = "delete config sys_db_config_param";
	
	/**
	 * ���캯��
	 */
	public TxnSysDbConfigParam()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��ͼ���ò����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103101( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		// ��ѯ��¼������ VoSysSvrConfigParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysSvrConfigParam result[] = context.getSysSvrConfigParams( outputNode );
	}
	
	/** �޸���ͼ���ò�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103102( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysSvrConfigParam sys_svr_config_param = context.getSysSvrConfigParam( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ������ͼ���ò�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103103( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysSvrConfigParam sys_svr_config_param = context.getSysSvrConfigParam( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ��ͼ���ò��������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103104( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysSvrConfigParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysSvrConfigParam result = context.getSysSvrConfigParam( outputNode );
	}
	
	/** ɾ����ͼ���ò�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103105( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysSvrConfigParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ɾ����ͼ�������в�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52103106( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysSvrConfigParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_LIST_FUNCTION, context, inputNode, outputNode );
	}

}
