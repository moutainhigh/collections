package com.gwssi.dw.metadata.datadict.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.metadata.datadict.vo.SysTableSemanticContext;

public class TxnSysTableSemantic extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysTableSemantic.class, SysTableSemanticContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_table_semantic";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_table_semantic list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_table_semantic";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_table_semantic";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_table_semantic";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_table_semantic";
	
	/**
	 * 构造函数
	 */
	public TxnSysTableSemantic()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询业务表列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30402001( SysTableSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		Recordset rs = context.getRecordset("record");
		DataBus rsDb = null;
		for(int i=0;i<rs.size();i++){
			rsDb = rs.get(i);
			rsDb.setValue("table_order", rsDb.getValue("sys_no") + "_" + rsDb.getValue("table_order"));
		}
	}
	
	/** 修改业务表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30402002( SysTableSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String sys_id= context.getRecord(inputNode).getValue("sys_id");
		String table_no = context.getRecord(inputNode).getValue("table_no");
		String table_name_cn = context.getRecord(inputNode).getValue("table_name_cn");
		String table_name = context.getRecord(inputNode).getValue("table_name");
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("type", "modify");
		txnContext.getRecord("select-key").setValue("sys_id", sys_id);
		txnContext.getRecord("select-key").setValue("table_no", table_no);
		txnContext.getRecord("select-key").setValue("table_name_cn", table_name_cn);
		txnContext.getRecord("select-key").setValue("table_name", table_name);
		int i = table.executeFunction("validateTableName", txnContext, "select-key", "record");
		if (i > 0)
		{
			throw new TxnDataException("999999","同一业务系统下业务表名重复，请更换一个业务表名！");
		}
		int j = table.executeFunction("validateTableNameCn", txnContext, "select-key", "record");
		if (j > 0)
		{
			throw new TxnDataException("999999","同一业务系统下业务表中文名重复，请更换一个业务表中文名！");
		}
		// 修改记录的内容 VoSysTableSemantic sys_table_semantic = context.getSysTableSemantic( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		setBizLog("修改业务表：", context,table_name);
	}
	
	/** 增加业务表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30402003( SysTableSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		String sys_id = context.getRecord(inputNode).getValue("sys_id");
		String table_no = context.getRecord(inputNode).getValue("table_no");
		String table_name_cn = context.getRecord(inputNode).getValue("table_name_cn");
		String table_name = context.getRecord(inputNode).getValue("table_name");
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("type", "add");
		txnContext.getRecord("select-key").setValue("sys_id", sys_id);
		txnContext.getRecord("select-key").setValue("table_no", table_no);
		txnContext.getRecord("select-key").setValue("table_name_cn", table_name_cn);
		txnContext.getRecord("select-key").setValue("table_name", table_name);
//		int k = table.executeFunction("validateTablePrimaryKey", txnContext, "select-key", "record");
//		if (k > 0)
//		{
//			throw new TxnDataException("999999","业务表编码重复，请更换一个业务表编码！");
//		}
		int i = table.executeFunction("validateTableName", txnContext, "select-key", "record");
		if (i > 0)
		{
			throw new TxnDataException("999999","同一业务系统下业务表名重复，请更换一个业务表名！");
		}
		int j = table.executeFunction("validateTableNameCn", txnContext, "select-key", "record");
		if (j > 0)
		{
			throw new TxnDataException("999999","同一业务系统下业务表中文名重复，请更换一个业务表中文名！");
		}
		
		// 增加记录的内容 VoSysTableSemantic sys_table_semantic = context.getSysTableSemantic( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		setBizLog("增加业务表：", context,table_name);
	}
	
	/** 查询业务表用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30402004( SysTableSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysTableSemanticPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysTableSemantic result = context.getSysTableSemantic( outputNode );
	}
	
	/** 删除业务表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30402005( SysTableSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		Recordset rs = context.getRecordset("primary-key");
		String table_names = "";
		for (int i=0; i < rs.size(); i++)
		{
			DataBus db = rs.get(i);
			String table_no = db.getValue("table_no");
			TxnContext txnContext = new TxnContext();
			txnContext.getRecord("select-key").setValue("table_no", table_no);
			int index = table.executeFunction("validateTableCanBeDelete", txnContext, "select-key", "record");
			if(index > 0)
			{
				throw new TxnDataException("","删除业务表前需要先删除业务表下的所有字段!");
			}
			table_names+= "," + rs.get(i).getValue("table_name");
		}		
		if(!table_names.equals("")) table_names = table_names.substring(1);
		
		// 删除记录的主键列表 VoSysTableSemanticPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		setBizLog("删除业务表：", context,table_names);
	}
	
	/** 查询业务表列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30402006( SysTableSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "select sys_table_semantic list by system", context, inputNode, outputNode );
		Recordset rs = context.getRecordset("record");
		DataBus rsDb = null;
		for(int i=0;i<rs.size();i++){
			rsDb = rs.get(i);
			rsDb.setValue("table_order", rsDb.getValue("sys_no") + "_" + rsDb.getValue("table_order"));
		}
	}	
	/**
	 * 记录日志
	 * @param type
	 * @param context
	 */
    private void setBizLog (String type,TxnContext context,String jgmc){
    	
    	context.getRecord("biz_log").setValue("desc", type + jgmc);
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
		SysTableSemanticContext appContext = new SysTableSemanticContext( context );
		invoke( method, appContext );
	}
}
