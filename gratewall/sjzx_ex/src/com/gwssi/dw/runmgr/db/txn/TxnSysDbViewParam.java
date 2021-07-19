package com.gwssi.dw.runmgr.db.txn;

import java.lang.reflect.Method;
import java.util.HashMap;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.runmgr.services.vo.SysSvrServiceParamContext;

public class TxnSysDbViewParam extends TxnService
{

	// ���ݱ�����
	private static final String TABLE_NAME = "sys_db_view_param";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_db_view_param list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_db_view_param";
	
	// �޸ļ�¼
	// private static final String UPDATE_FUNCTION = "update one sys_db_view_param";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_db_view_param";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete sys_db_view_param list";
	
	// ɾ����¼
	private static final String DELETE_ONE = "delete one sys_db_view_param";
	
	// ��ѯ��¼
	private static final String SELECT_ORDER_FUNCTION = "select next_order sys_db_view_param";
	
	/**
	 * ���캯��
	 */
	public TxnSysDbViewParam()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ������ͼ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52102101( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		// ��ѯ��¼������ VoSysSvrServiceParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysSvrServiceParam result[] = context.getSysSvrServiceParams( outputNode );
	}
	
	/** �޸�������ͼ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
//	public void txn50205002( TxnContext context ) throws TxnException
//	{
//		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
//		// �޸ļ�¼������ VoSysSvrServiceParam sys_db_view_param = context.getSysSvrServiceParam( inputNode );
//		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
//	}
	
	/** ����������ͼ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52102102( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysSvrServiceParam sys_db_view_param = context.getSysSvrServiceParam( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ������ͼ���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52102103( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysSvrServiceParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysSvrServiceParam result = context.getSysSvrServiceParam( outputNode );
	}
	
	/** ɾ��������ͼ������Ϣ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52102104( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysSvrServiceParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ɾ��������ͼ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52102105( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysSvrServiceParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_ONE, context, inputNode, outputNode );
	}
	
	/** ��ѯ������ͼ����˳��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52102106( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysSvrServiceParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( SELECT_ORDER_FUNCTION, context, inputNode, outputNode );
	}
		
}
