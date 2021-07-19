package cn.gwssi.dw.aic.bj.ecomm.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.aic.bj.ecomm.vo.EbSysRightUserContext;

public class TxnEbSysRightUser extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnEbSysRightUser.class, EbSysRightUserContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "eb_sys_right_user";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select eb_sys_right_user list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one eb_sys_right_user";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "${mod.function.insert}";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "${mod.function.delete}";
	
	/**
	 * ���캯��
	 */
	public TxnEbSysRightUser()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ���������û����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026601( EbSysRightUserContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbSysRightUserSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoEbSysRightUser result[] = context.getEbSysRightUsers( outputNode );
	}
	
	/** �޸ĵ��������û�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026602( EbSysRightUserContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoEbSysRightUser eb_sys_right_user = context.getEbSysRightUser( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӵ��������û�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026603( EbSysRightUserContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoEbSysRightUser eb_sys_right_user = context.getEbSysRightUser( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ���������û��������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026604( EbSysRightUserContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbSysRightUserPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoEbSysRightUser result = context.getEbSysRightUser( outputNode );
	}
	
	/** ɾ�����������û�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026605( EbSysRightUserContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoEbSysRightUserPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
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
		EbSysRightUserContext appContext = new EbSysRightUserContext( context );
		invoke( method, appContext );
	}
}
