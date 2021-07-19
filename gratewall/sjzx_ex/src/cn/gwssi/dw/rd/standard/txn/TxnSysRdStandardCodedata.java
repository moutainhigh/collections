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
import cn.gwssi.dw.rd.standard.vo.SysRdStandardCodedataContext;

public class TxnSysRdStandardCodedata extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandardCodedata.class, SysRdStandardCodedataContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_rd_standard_codedata";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_rd_standard_codedata list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_rd_standard_codedata";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard_codedata";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard_codedata";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard_codedata";
	
	/**
	 * ���캯��
	 */
	public TxnSysRdStandardCodedata()
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
	public void txn7000411( SysRdStandardCodedataContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardCodedataSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysRdStandardCodedata result[] = context.getSysRdStandardCodedatas( outputNode );
	}
	
	/** �޸Ļ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000412( SysRdStandardCodedataContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysRdStandardCodedata sys_rd_standard_codedata = context.getSysRdStandardCodedata( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӻ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000413( SysRdStandardCodedataContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysRdStandardCodedata sys_rd_standard_codedata = context.getSysRdStandardCodedata( inputNode );
		context.getRecord("record").setValue("id", UuidGenerator.getUUID());//���ID
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000414( SysRdStandardCodedataContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardCodedataPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandardCodedata result = context.getSysRdStandardCodedata( outputNode );
	}
	
	/** ɾ������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000415( SysRdStandardCodedataContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysRdStandardCodedataPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		SysRdStandardCodedataContext appContext = new SysRdStandardCodedataContext( context );
		invoke( method, appContext );
	}
}
