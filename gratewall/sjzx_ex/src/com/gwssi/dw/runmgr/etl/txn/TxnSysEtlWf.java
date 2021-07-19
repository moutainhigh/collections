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
import com.gwssi.dw.runmgr.etl.vo.SysEtlWfContext;


public class TxnSysEtlWf extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysEtlWf.class, SysEtlWfContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_etl_wf";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_etl_wf list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_etl_wf";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_etl_wf";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_etl_wf";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_etl_wf";
	
	/**
	 * ���캯��
	 */
	public TxnSysEtlWf()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��ȡ��������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn501030001( SysEtlWfContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysEtlWfSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow( context, outputNode, 10 );
		table.executeFunction( "queryEtlWf", context, inputNode, outputNode );
		
		// ��ѯ���ļ�¼�� VoSysEtlWf result[] = context.getSysEtlWfs( outputNode );
	}
	
	/** ���ӳ�ȡ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn501030002( SysEtlWfContext context ) throws TxnException
	{
//		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
//		// �޸ļ�¼������ VoSysEtlWf sys_etl_wf = context.getSysEtlWf( inputNode );
//		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		DataBus dataBus = context.getRecord(inputNode);
		//����ETL��API��Ҫ�Ĳ���
		String etl_hostname = dataBus.getValue("etl_hostname");
		String etl_portno = dataBus.getValue("etl_portno");
		String etl_domainname = dataBus.getValue("etl_domainname");
		String rep_name_en = dataBus.getValue("rep_name_en");
		String user_id = dataBus.getValue("user_id");
		String user_password = dataBus.getValue("user_password");
		String rep_foldername = dataBus.getValue("rep_foldername");
		String wf_name = dataBus.getValue("wf_name");
		
		HashMap map = new HashMap();
		map.put("etl_hostname", etl_hostname);
		map.put("etl_portno", etl_portno);
		map.put("etl_domainname", etl_domainname);
		map.put("rep_name_en", rep_name_en);
		map.put("user_id", user_id);
		map.put("user_password", user_password);
		map.put("rep_foldername", rep_foldername);
		map.put("wf_name", wf_name);
		//����ETL���񣬷��ؽ��,δ���...
		DataBus ds = new DataBus();
		ds.setValue("wf_db_source", "test_db_source");
		ds.setValue("wf_db_target", "test_db_target");
		ds.setValue("wf_state", "test_sucess");
		context.remove(inputNode);
		context.addRecord(outputNode, ds);
	}
	
	/** ���г�ȡ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void txn501030003( SysEtlWfContext context ) throws TxnException, IOException, InterruptedException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysEtlWf sys_etl_wf = context.getSysEtlWf( inputNode );
		// table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		DataBus dataBus = context.getRecord(inputNode);
		String wf_name = dataBus.getValue("wf_name");
		String rep_foldername = dataBus.getValue("rep_foldername");
		String domain_name = dataBus.getValue("domain_name");
		String server_name = dataBus.getValue("server_name");
		String rootPath = CSDBConfig.get("pmcmdFilePath");
		String pcSrvBinPath = CSDBConfig.get("pcSrvBinPath");
		String command = "\""+rootPath+"\\pmcmd.bat\" " + 
			rep_foldername + " " + wf_name + " " + domain_name + " " + server_name+ 
			" " + pcSrvBinPath + " " + pcSrvBinPath.substring(0, 2); 
		// System.out.println("rootPath:"+rootPath);
		// System.out.println("pcSrvBinPath:"+pcSrvBinPath);
		System.out.println("ִ�����"+ command);
		Runtime.getRuntime().exec(command);		
	}
	
	/** ת����ȳ�ȡ�������ҳ��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn501030004( SysEtlWfContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysEtlWfPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( "querySingleEtlWf", context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysEtlWf result = context.getSysEtlWf( outputNode );
	}
	
	/** ���ȳ�ȡ�������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn501030005( SysEtlWfContext context ) throws TxnException
	{
//		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
//		// ɾ����¼�������б� VoSysEtlWfPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
//		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}

	/** ��ѯ��ȡ��־�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn501030006( SysEtlWfContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysEtlWfSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow( context, outputNode, 10 );
		table.executeFunction( "queryEtlWf", context, inputNode, outputNode );
		
		// ��ѯ���ļ�¼�� VoSysEtlWf result[] = context.getSysEtlWfs( outputNode );
	}	
	/** ת���ȡ��־��ϸҳ��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn501030007( SysEtlWfContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysEtlWfPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( "querySingleEtlWf", context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysEtlWf result = context.getSysEtlWf( outputNode );
		DataBus old_db = context.getRecord(outputNode);
		DataBus db = new DataBus();
		db.setValue("wf_name", old_db.getValue("wf_name"));
		db.setValue("log_start_time", "2008-02-25 20:00:00 ");
		db.setValue("log_end_time", "2008-02-25 20:05:00 ");
		db.setValue("log_state", "sucess");
		context.remove(outputNode);
		context.addRecord(outputNode, db);
	}	
	/** ��ʾ��־��ϸ����
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn501030008( SysEtlWfContext context ) throws TxnException
	{

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
		SysEtlWfContext appContext = new SysEtlWfContext( context );
		invoke( method, appContext );
	}
}
