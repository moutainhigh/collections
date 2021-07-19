package cn.gwssi.dw.rd.standard.txn;

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
import com.gwssi.common.util.UuidGenerator;
import cn.gwssi.dw.rd.standard.vo.SysRdStandardDataelementContext;

public class TxnSysRdStandardDataelement extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandardDataelement.class, SysRdStandardDataelementContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_rd_standard_dataelement";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_rd_standard_dataelement list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_rd_standard_dataelement";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard_dataelement";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard_dataelement";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard_dataelement";
	
	/**
	 * ���캯��
	 */
	public TxnSysRdStandardDataelement()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��������Ԫ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000301( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardDataelementSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysRdStandardDataelement result[] = context.getSysRdStandardDataelements( outputNode );
	}
	
	/** ��ѯ��������Ԫ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7004401( SysRdStandardDataelementContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, 5);
		callService("7000301", context);
	}
	
	/** �޸Ļ�������Ԫ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000302( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysRdStandardDataelement sys_rd_standard_dataelement = context.getSysRdStandardDataelement( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӻ�������Ԫ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000303( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysRdStandardDataelement sys_rd_standard_dataelement = context.getSysRdStandardDataelement( inputNode );
		
		context.getRecord("record").setValue("sys_rd_standard_dataelement_id",UuidGenerator.getUUID());
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ��������Ԫ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000304( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardDataelementPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandardDataelement result = context.getSysRdStandardDataelement( outputNode );
	}
	
	/** ɾ����������Ԫ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000305( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysRdStandardDataelementPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ��������Ԫ������ͼ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000306( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardDataelementPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandardDataelement result = context.getSysRdStandardDataelement( outputNode );
	}
	
	/** ��ѯ��������Ԫ�����޸���������ֶ�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000307( SysRdStandardDataelementContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardDataelementPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( "selectonedataelement", context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandardDataelement result = context.getSysRdStandardDataelement( outputNode );
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
		SysRdStandardDataelementContext appContext = new SysRdStandardDataelementContext( context );
		invoke( method, appContext );
	}
}
