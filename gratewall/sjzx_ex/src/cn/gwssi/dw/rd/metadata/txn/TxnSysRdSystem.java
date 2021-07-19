package cn.gwssi.dw.rd.metadata.txn;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.gwssi.common.util.CalendarUtil;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.concurrent.*;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

public class TxnSysRdSystem extends TxnService
{
	/**
	 * ���ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdSystem.class, TxnContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_rd_system";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_rd_system list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_rd_system";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_rd_system";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_rd_system";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_rd_system";
	
	/**
	 * ���캯��
	 */
	public TxnSysRdSystem()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/**
	 * ��ѯ�����б�
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000111( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
	}
	
	/**
	 * �޸�������Ϣ
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000112( TxnContext context ) throws TxnException
	{
		DataBus inputData = context.getRecord(inputNode);
		
		//ʱ���
		inputData.setValue("timestamp",CalendarUtil.getCurrentDateTime());
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}
	
	/**
	 * ����������Ϣ
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000113( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		DataBus inputData = context.getRecord(inputNode);
		//�����
		table.executeFunction("selectsortsysrdsystem", context, inputNode, "temp");
		String num = context.getRecord("temp").getString("num");
		String sort ="1";
		if(num!=null && !"".equals(num)){
			sort = String.valueOf(Integer.parseInt(num)+1);
		}
		inputData.setValue("sort", sort);
		//ʱ���
		inputData.setValue("timestamp", CalendarUtil.getCurrentDateTime());
		
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}
	
	/**
	 * ��ѯ���������޸�
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000114( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
	}
	
	/**
	 * ɾ��������Ϣ
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000115( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
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
		TxnContext appContext = new TxnContext( context );
		invoke( method, appContext );
		//SysSystemSemanticContext appContext = new SysSystemSemanticContext( context );
		//invoke( method, appContext );
	}
}
