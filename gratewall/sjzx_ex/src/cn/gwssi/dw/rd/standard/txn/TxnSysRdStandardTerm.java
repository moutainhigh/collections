package cn.gwssi.dw.rd.standard.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.gwssi.common.util.UuidGenerator;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.common.util.UuidGenerator;
import cn.gwssi.dw.rd.standard.vo.SysRdStandardTermContext;

public class TxnSysRdStandardTerm extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandardTerm.class, SysRdStandardTermContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_rd_standard_term";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_rd_standard_term list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_rd_standard_term";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard_term";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard_term";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard_term";
	
	/**
	 * ���캯��
	 */
	public TxnSysRdStandardTerm()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000101( SysRdStandardTermContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardTermSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysRdStandardTerm result[] = context.getSysRdStandardTerms( outputNode );
	}
	
	/** �޸�������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000102( SysRdStandardTermContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysRdStandardTerm sys_rd_standard_term = context.getSysRdStandardTerm( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000103( SysRdStandardTermContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysRdStandardTerm sys_rd_standard_term = context.getSysRdStandardTerm( inputNode );
		String Str=UuidGenerator.getUUID();
		context.getRecord("record").setValue("sys_rd_standar_term_id", Str);
		System.out.println(context);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		
	}
	
	/** ��ѯ���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000104( SysRdStandardTermContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardTermPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandardTerm result = context.getSysRdStandardTerm( outputNode );
	}
	
	/** ɾ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000105( SysRdStandardTermContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysRdStandardTermPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ����������ͼ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000106( SysRdStandardTermContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardTermPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandardTerm result = context.getSysRdStandardTerm( outputNode );
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
		SysRdStandardTermContext appContext = new SysRdStandardTermContext( context );
		invoke( method, appContext );
	}
}
