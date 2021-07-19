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
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysEtlWf.class, SysEtlWfContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_etl_wf";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_etl_wf list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_etl_wf";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_etl_wf";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_etl_wf";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_etl_wf";
	
	/**
	 * 构造函数
	 */
	public TxnSysEtlWf()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询抽取服务管理列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn501030001( SysEtlWfContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysEtlWfSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow( context, outputNode, 10 );
		table.executeFunction( "queryEtlWf", context, inputNode, outputNode );
		
		// 查询到的记录集 VoSysEtlWf result[] = context.getSysEtlWfs( outputNode );
	}
	
	/** 连接抽取服务管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn501030002( SysEtlWfContext context ) throws TxnException
	{
//		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
//		// 修改记录的内容 VoSysEtlWf sys_etl_wf = context.getSysEtlWf( inputNode );
//		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		DataBus dataBus = context.getRecord(inputNode);
		//调用ETL的API需要的参数
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
		//调用ETL服务，返回结果,未完成...
		DataBus ds = new DataBus();
		ds.setValue("wf_db_source", "test_db_source");
		ds.setValue("wf_db_target", "test_db_target");
		ds.setValue("wf_state", "test_sucess");
		context.remove(inputNode);
		context.addRecord(outputNode, ds);
	}
	
	/** 运行抽取服务管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void txn501030003( SysEtlWfContext context ) throws TxnException, IOException, InterruptedException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysEtlWf sys_etl_wf = context.getSysEtlWf( inputNode );
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
		System.out.println("执行命令："+ command);
		Runtime.getRuntime().exec(command);		
	}
	
	/** 转入调度抽取服务管理页面
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn501030004( SysEtlWfContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysEtlWfPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( "querySingleEtlWf", context, inputNode, outputNode );
		// 查询到的记录内容 VoSysEtlWf result = context.getSysEtlWf( outputNode );
	}
	
	/** 调度抽取服务管理
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn501030005( SysEtlWfContext context ) throws TxnException
	{
//		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
//		// 删除记录的主键列表 VoSysEtlWfPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
//		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}

	/** 查询抽取日志列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn501030006( SysEtlWfContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysEtlWfSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow( context, outputNode, 10 );
		table.executeFunction( "queryEtlWf", context, inputNode, outputNode );
		
		// 查询到的记录集 VoSysEtlWf result[] = context.getSysEtlWfs( outputNode );
	}	
	/** 转入抽取日志明细页面
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn501030007( SysEtlWfContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysEtlWfPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( "querySingleEtlWf", context, inputNode, outputNode );
		// 查询到的记录内容 VoSysEtlWf result = context.getSysEtlWf( outputNode );
		DataBus old_db = context.getRecord(outputNode);
		DataBus db = new DataBus();
		db.setValue("wf_name", old_db.getValue("wf_name"));
		db.setValue("log_start_time", "2008-02-25 20:00:00 ");
		db.setValue("log_end_time", "2008-02-25 20:05:00 ");
		db.setValue("log_state", "sucess");
		context.remove(outputNode);
		context.addRecord(outputNode, db);
	}	
	/** 显示日志明细管理
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn501030008( SysEtlWfContext context ) throws TxnException
	{

	}	
	/**
	 * 重载父类的方法，用于替换交易接口的输入变量
	 * 调用函数
	 * @param funcName 方法名称
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数"
			);
		}
		
		// 执行
		SysEtlWfContext appContext = new SysEtlWfContext( context );
		invoke( method, appContext );
	}
}
