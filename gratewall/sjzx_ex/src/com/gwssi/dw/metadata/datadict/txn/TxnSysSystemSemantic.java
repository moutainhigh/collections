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
import com.gwssi.dw.metadata.datadict.vo.SysSystemSemanticContext;

public class TxnSysSystemSemantic extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysSystemSemantic.class, SysSystemSemanticContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_system_semantic";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_system_semantic list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_system_semantic";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_system_semantic";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_system_semantic";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_system_semantic";
	
	/**
	 * 构造函数
	 */
	public TxnSysSystemSemantic()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询系统列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30401001( SysSystemSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysSystemSemanticSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysSystemSemantic result[] = context.getSysSystemSemantics( outputNode );
	}
	
	/** 修改系统信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30401002( SysSystemSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String sys_name = context.getRecord(inputNode).getValue("sys_name");
		String sys_no = context.getRecord(inputNode).getValue("sys_no");
		String sys_id = context.getRecord("primary-key").getValue("sys_id");
		String sys_simple = context.getRecord(inputNode).getValue("sys_simple");
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("sys_no", sys_no);
		txnContext.getRecord("select-key").setValue("sys_name", sys_name);
		txnContext.getRecord("select-key").setValue("sys_id", sys_id);
		txnContext.getRecord("select-key").setValue("sys_simple", sys_simple);
		txnContext.getRecord("select-key").setValue("type", "modify");

		int n = table.executeFunction("validateSysNo", txnContext, "select-key", "record");
		if (n > 0)
		{
			throw new TxnDataException("999999","主题代码重复，请更换一个主题代码！");
		}			
		int j = table.executeFunction("validateSysName", txnContext, "select-key", "record");
		if (j > 0)
		{
			throw new TxnDataException("999999","主题名称重复，请更换一个主题名称！");
		}
		int k = table.executeFunction("validateSysSimpleName", txnContext, "select-key", "record");
		if (k > 0)
		{
			throw new TxnDataException("999999","主题简称重复，请更换一个主题简称！");
		}	
		
		// 修改记录的内容 VoSysSystemSemantic sys_system_semantic = context.getSysSystemSemantic( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		setBizLog("修改主题：", context,sys_name);
	}
	
	/** 增加系统信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30401003( SysSystemSemanticContext context ) throws TxnException
	{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String sys_name = context.getRecord(inputNode).getValue("sys_name");
		String sys_no = context.getRecord(inputNode).getValue("sys_no");
		String sys_simple = context.getRecord(inputNode).getValue("sys_simple");
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("sys_name", sys_name);
		txnContext.getRecord("select-key").setValue("sys_no", sys_no);
		txnContext.getRecord("select-key").setValue("sys_simple", sys_simple);
		txnContext.getRecord("select-key").setValue("type", "add");
		int i = table.executeFunction("validateSysPrimaryKey", txnContext, "select-key", "record");
		if (i > 0)
		{
			throw new TxnDataException("999999","系统编码重复，请更换一个系统编码！");
		}
		int j = table.executeFunction("validateSysName", txnContext, "select-key", "record");
		if (j > 0)
		{
			throw new TxnDataException("999999","系统名称重复，请更换一个系统名称！");
		}
		int k = table.executeFunction("validateSysSimpleName", txnContext, "select-key", "record");
		if (k > 0)
		{
			throw new TxnDataException("999999","系统简称重复，请更换一个系统简称！");
		}
		// 增加记录的内容 VoSysSystemSemantic sys_system_semantic = context.getSysSystemSemantic( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		setBizLog("增加主题：", context,sys_name);
	}
	
	/** 查询系统用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30401004( SysSystemSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysSystemSemanticPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysSystemSemantic result = context.getSysSystemSemantic( outputNode );
	}
	
	/** 删除系统信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30401005( SysSystemSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Recordset rs = context.getRecordset("primary-key");
		String sys_names = "";
		for (int i=0; i < rs.size(); i++)
		{
			DataBus db = rs.get(i);
			String sys_no = db.getValue("sys_id");
			TxnContext txnContext = new TxnContext();
			txnContext.getRecord("select-key").setValue("sys_no", sys_no);
			int index = table.executeFunction("validateSysCanBeDelete", txnContext, "select-key", "record");
			if(index > 0)
			{
				throw new TxnDataException("999999","删除业务系统前需要先删除业务系统下的所有表!");
			}
			sys_names+= "," + rs.get(i).getValue("sys_name");
		}		
		if(!sys_names.equals("")) sys_names = sys_names.substring(1);
		
		// 删除记录的主键列表 VoSysSystemSemanticPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		setBizLog("删除主题：", context,sys_names);
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
		SysSystemSemanticContext appContext = new SysSystemSemanticContext( context );
		invoke( method, appContext );
	}
}
