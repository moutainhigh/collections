package com.gwssi.dw.runmgr.etl.txn;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.common.util.CSDBConfig;
import com.gwssi.dw.runmgr.etl.vo.OpbSchedulerContext;

public class TxnOpbScheduler extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnOpbScheduler.class, OpbSchedulerContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "opb_scheduler";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select opb_scheduler list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one opb_scheduler";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one opb_scheduler";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one opb_scheduler";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one opb_scheduler";
	
	/**
	 * ���캯��
	 */
	public TxnOpbScheduler()
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
	public void txn50106001( OpbSchedulerContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoOpbSchedulerSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoOpbScheduler result[] = context.getOpbSchedulers( outputNode );
	}
	
	/** �޸ĵ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 * @throws IOException 
	 */
	public void txn50106002( OpbSchedulerContext context ) throws TxnException, IOException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoOpbScheduler opb_scheduler = context.getOpbScheduler( inputNode );
		table.executeFunction( "updateSchedulerInfoByDbuser", context, inputNode, outputNode );
	}
	
	/** ���ӵ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50106003( OpbSchedulerContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoOpbScheduler opb_scheduler = context.getOpbScheduler( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50106004( OpbSchedulerContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoOpbSchedulerPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoOpbScheduler result = context.getOpbScheduler( outputNode );
	}
	
	/** ɾ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50106005( OpbSchedulerContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoOpbSchedulerPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * ����WorkFlow��ѯScheduler��Ϣ
	 * @param context
	 * @throws TxnException
	 */
	public void txn50106006( OpbSchedulerContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoOpbSchedulerPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "getSchedulerByWorkFlowId", context, inputNode, outputNode );
	}
	
	public void txn50106007( OpbSchedulerContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoOpbSchedulerPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		// ����ֻȡһ����¼
		Attribute.setPageRow(context, "record", 1);
		table.executeFunction( "getWorkFlowExecInfo", context, "select-key", "record" );
	}
	
	public void txn50106008( OpbSchedulerContext context ) throws TxnException, IOException, InterruptedException
	{
		DataBus dataBus = context.getRecord(inputNode);
		String wf_name = dataBus.getValue("wf_name");
		String rep_foldername = dataBus.getValue("rep_foldername");
		String domain_name = dataBus.getValue("domain_name");
		String server_name = dataBus.getValue("server_name");
		String rootPath = CSDBConfig.get("pmcmdFilePath");
		String pcSrvBinPath = CSDBConfig.get("pcSrvBinPath");
		String command = "\""+rootPath+"\\pmcmdScheduler.bat\" " + 
			rep_foldername + " " + wf_name + " " + domain_name + " " + server_name+ 
			" " + pcSrvBinPath + " " + pcSrvBinPath.substring(0, 2); 
		System.out.println("ִ�����"+ command);
		Process p = Runtime.getRuntime().exec(command);
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
		OpbSchedulerContext appContext = new OpbSchedulerContext( context );
		invoke( method, appContext );
	}
}
