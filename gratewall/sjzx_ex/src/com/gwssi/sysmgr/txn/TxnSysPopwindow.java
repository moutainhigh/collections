package com.gwssi.sysmgr.txn;

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
import com.gwssi.sysmgr.vo.SysPopwindowContext;

public class TxnSysPopwindow extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysPopwindow.class, SysPopwindowContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_popwindow";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_popwindow list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_popwindow";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_popwindow";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_popwindow";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_popwindow";
	
	/**
	 * ���캯��
	 */
	public TxnSysPopwindow()
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
	public void txn60800001( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysPopwindowSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysPopwindow result[] = context.getSysPopwindows( outputNode );
	}
	/** ������չʾ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60800008( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysPopwindowPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		Attribute.setPageRow(context, "tz-record", 8);
		table.executeFunction( ROWSET_FUNCTION, context, "tz-query", "tz-record" );
		//table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysPopwindow result = context.getSysPopwindow( outputNode );
	}
	/** �޸ĵ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60800002( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysPopwindow sys_popwindow = context.getSysPopwindow( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӵ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60800003( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String uuid = com.gwssi.common.util.UuidGenerator.getUUID();
		context.getRecord(inputNode).setValue("sys_popwindow_id", uuid);
		// ���Ӽ�¼������ VoSysPopwindow sys_popwindow = context.getSysPopwindow( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60800004( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysPopwindowPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysPopwindow result = context.getSysPopwindow( outputNode );
	}
	
	/** ɾ������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60800005( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysPopwindowPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * ��ѯ��ǰ�û��ĵ�����Ϣ�б�
	 * @param context
	 * @throws TxnException
	 */
	public void txn60800006( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysPopwindowPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );

		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		//table.executeFunction( "getPopWindowContentByRoles", context, inputNode, outputNode );
	}
	
	/**
	 * ��ѯ��ǰ�û��ĵ�����Ϣ����
	 * @param context
	 * @throws TxnException
	 */
	public void txn60800007( SysPopwindowContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		// ɾ����¼�������б� VoSysPopwindowPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "getPopWindowCountByRoles", context, inputNode, outputNode );
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
		SysPopwindowContext appContext = new SysPopwindowContext( context );
		invoke( method, appContext );
	}
}
