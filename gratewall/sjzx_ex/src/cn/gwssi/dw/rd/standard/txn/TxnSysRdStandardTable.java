package cn.gwssi.dw.rd.standard.txn;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;
import cn.gwssi.dw.rd.standard.vo.SysRdStandardTableContext;

public class TxnSysRdStandardTable extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandardTable.class, SysRdStandardTableContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_rd_standard_table";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_rd_standard_table list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_rd_standard_table";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard_table";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard_table";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard_table";
	
	/**
	 * ���캯��
	 */
	public TxnSysRdStandardTable()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯʵ����Ϣ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000211( SysRdStandardTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardTableSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysRdStandardTable result[] = context.getSysRdStandardTables( outputNode );
	}
	
	/** �޸�ʵ����Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000212( SysRdStandardTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysRdStandardTable sys_rd_standard_table = context.getSysRdStandardTable( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����ʵ����Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000213( SysRdStandardTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysRdStandardTable sys_rd_standard_table = context.getSysRdStandardTable( inputNode );
		table.executeFunction("queryMaxSort", context, inputNode, "sort");//��ȡ��������
		Recordset rs = context.getRecordset("sort");
		DataBus tempSort = (DataBus)rs.get(0);
        if(tempSort.getValue("max(sort)")!=null&&!tempSort.getValue("max(sort)").equals("")){
        	int Intsort=Integer.parseInt(tempSort.getValue("max(sort)"))+1;
            String sort=Intsort+"";
        	context.getRecord("record").setValue("sort", sort);
        }else{
        	context.getRecord("record").setValue("sort","1" );
        }
		context.getRecord("record").setValue("sys_rd_standard_table_id",UuidGenerator.getUUID());
		String TimeStamp=CalendarUtil.getCurrentDateTime();//���ʱ���
		context.getRecord("record").setValue("timestamp", TimeStamp);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		
	}
	
	/** ��ѯʵ����Ϣ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000214( SysRdStandardTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardTablePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandardTable result = context.getSysRdStandardTable( outputNode );
	}
	
	/** ɾ��ʵ����Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000215( SysRdStandardTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysRdStandardTablePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯʵ����Ϣ������ͼ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn7000216( SysRdStandardTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysRdStandardTablePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysRdStandardTable result = context.getSysRdStandardTable( outputNode );
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
		SysRdStandardTableContext appContext = new SysRdStandardTableContext( context );
		invoke( method, appContext );
	}
}
