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
import cn.gwssi.dw.rd.standard.vo.SysRdStandardCodeindexContext;

public class TxnSysRdStandardCodeindex extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandardCodeindex.class, SysRdStandardCodeindexContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_rd_standard_codeindex";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_rd_standard_codeindex list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_rd_standard_codeindex";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard_codeindex";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard_codeindex";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard_codeindex";
	
	/**
	 * ���캯��
	 */
	public TxnSysRdStandardCodeindex()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ���뼯�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000401( SysRdStandardCodeindexContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardCodeindexSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysRdStandardCodeindex result[] = context.getSysRdStandardCodeindexs( outputNode );
	}
	
	/** �޸Ĵ��뼯��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000402( SysRdStandardCodeindexContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysRdStandardCodeindex sys_rd_standard_codeindex = context.getSysRdStandardCodeindex( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���Ӵ��뼯��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000403( SysRdStandardCodeindexContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysRdStandardCodeindex sys_rd_standard_codeindex = context.getSysRdStandardCodeindex( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ���뼯�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000404( SysRdStandardCodeindexContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardCodeindexPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandardCodeindex result = context.getSysRdStandardCodeindex( outputNode );
	}
	
	/** ɾ�����뼯��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000405( SysRdStandardCodeindexContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysRdStandardCodeindexPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	/** ��ѯ���뼯������ͼ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000406( SysRdStandardCodeindexContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardCodeindexPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandardCodeindex result = context.getSysRdStandardCodeindex( outputNode );
		
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
		SysRdStandardCodeindexContext appContext = new SysRdStandardCodeindexContext( context );
		invoke( method, appContext );
	}
}
