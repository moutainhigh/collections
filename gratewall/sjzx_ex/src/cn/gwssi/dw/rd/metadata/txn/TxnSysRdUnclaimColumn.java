package cn.gwssi.dw.rd.metadata.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.metadata.vo.SysRdUnclaimColumnContext;

public class TxnSysRdUnclaimColumn extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdUnclaimColumn.class, SysRdUnclaimColumnContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_rd_unclaim_column";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_rd_unclaim_column list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_rd_unclaim_column";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_rd_unclaim_column";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_rd_unclaim_column";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_rd_unclaim_column";
	
	/**
	 * ���캯��
	 */
	public TxnSysRdUnclaimColumn()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯδ������ֶ��б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80002401( SysRdUnclaimColumnContext context ) throws TxnException
	{
		
		DataBus db = context.getRecord(inputNode);
		String unclaim_column_code = db.getValue("unclaim_column_code");
		if(unclaim_column_code!=null && !"".equals(unclaim_column_code)){
			unclaim_column_code = unclaim_column_code.toUpperCase();
			db.setValue("unclaim_column_code", unclaim_column_code);
		}
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdUnclaimColumnSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysRdUnclaimColumn result[] = context.getSysRdUnclaimColumns( outputNode );
	}
	
	/** �޸�δ������ֶ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80002402( SysRdUnclaimColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysRdUnclaimColumn sys_rd_unclaim_column = context.getSysRdUnclaimColumn( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����δ������ֶ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80002403( SysRdUnclaimColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysRdUnclaimColumn sys_rd_unclaim_column = context.getSysRdUnclaimColumn( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯδ������ֶ������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80002404( SysRdUnclaimColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdUnclaimColumnPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdUnclaimColumn result = context.getSysRdUnclaimColumn( outputNode );
	}
	
	/** ɾ��δ������ֶ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn80002405( SysRdUnclaimColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysRdUnclaimColumnPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		SysRdUnclaimColumnContext appContext = new SysRdUnclaimColumnContext( context );
		invoke( method, appContext );
	}
}
