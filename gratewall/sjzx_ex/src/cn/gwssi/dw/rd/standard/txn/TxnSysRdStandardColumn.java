package cn.gwssi.dw.rd.standard.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.common.util.UuidGenerator;
import cn.gwssi.dw.rd.standard.vo.SysRdStandardColumnContext;

public class TxnSysRdStandardColumn extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandardColumn.class, SysRdStandardColumnContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_rd_standard_column";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_rd_standard_column list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_rd_standard_column";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard_column";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard_column";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard_column";
	
	/**
	 * ���캯��
	 */
	public TxnSysRdStandardColumn()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯָ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000221( SysRdStandardColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardColumnSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysRdStandardColumn result[] = context.getSysRdStandardColumns( outputNode );
	}
	
	/** �޸�ָ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000222( SysRdStandardColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysRdStandardColumn sys_rd_standard_column = context.getSysRdStandardColumn( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����ָ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000223( SysRdStandardColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysRdStandardColumn sys_rd_standard_column = context.getSysRdStandardColumn( inputNode );
		context.getRecord("record").setValue("sys_rd_standard_column_id",UuidGenerator.getUUID());
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯָ���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000224( SysRdStandardColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardColumnPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandardColumn result = context.getSysRdStandardColumn( outputNode );
	}
	
	/** ɾ��ָ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000225( SysRdStandardColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysRdStandardColumnPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯָ����������ͼ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000226( SysRdStandardColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardColumnPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandardColumn result = context.getSysRdStandardColumn( outputNode );
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
		SysRdStandardColumnContext appContext = new SysRdStandardColumnContext( context );
		invoke( method, appContext );
	}
}
