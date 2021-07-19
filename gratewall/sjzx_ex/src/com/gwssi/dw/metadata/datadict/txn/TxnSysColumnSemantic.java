package com.gwssi.dw.metadata.datadict.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
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
import com.gwssi.dw.metadata.datadict.vo.SysColumnSemanticContext;

public class TxnSysColumnSemantic extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysColumnSemantic.class, SysColumnSemanticContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_column_semantic";
	
	// 查询列表
	//private static final String ROWSET_FUNCTION = "select sys_column_semantic list";
	
	private static final String ROWSET_FUNCTION = "queryList";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_column_semantic";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_column_semantic";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_column_semantic";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_column_semantic";
	
	/**
	 * 构造函数
	 */
	public TxnSysColumnSemantic()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询业务字段列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30403001( SysColumnSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysColumnSemanticSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysColumnSemantic result[] = context.getSysColumnSemantics( outputNode );
	}
	
	/** 修改业务字段信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30403002( SysColumnSemanticContext context ) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		DataBus db_rec = context.getRecord("record");
		TxnContext txnContext = new TxnContext();
		DataBus db_sel = txnContext.getRecord("select-key");
		String column_name = db_rec.getValue("column_name");
		// type用来区分添加还是修改.
		db_sel.setValue("type", "modify");
		db_sel.setValue("column_no", db_rec.getValue("column_no"));
		db_sel.setValue("column_name", column_name);
		db_sel.setValue("column_name_cn", db_rec.getValue("column_name_cn"));
		db_sel.setValue("table_no", db_rec.getValue("table_no"));
		int j = table.executeFunction( "validateColumnName", txnContext, "select-key", "record" );
		if (j > 0)
		{
			throw new TxnDataException("999999","同一业务表下字段名重复，请更换一个字段名！");
		}
		int k = table.executeFunction( "validateColumnNameCn", txnContext, "select-key", "record" );
		if (k > 0)
		{
			throw new TxnDataException("999999","同一业务表下字段中文名重复，请更换一个字段中文名！");
		}
		String old_column_name = db_rec.getValue("old_column_name");
		if(!column_name.equals(old_column_name)){
			
			String column_byname = "";
			int m = table.executeFunction( "queryColumnByName", txnContext, "select-key", "record" );
			if (m > 0){
				table.executeFunction( "queryMaxAlias", txnContext, "select-key", "record" );
				String alias = txnContext.getRecord("record").getValue("column_byname");
				if(alias==null||alias.equals("")){
					column_byname = "ALIAS1000001";
				}else{
					int count = Integer.parseInt(alias.substring(5)) + 1;
					String newCount = String.valueOf(count);
					int reCount = newCount.length();
					column_byname = alias.substring(0, 12-reCount) + newCount;
				}
			}else{
				column_byname = "";
			}
			context.getRecord("record").setValue("column_byname", column_byname);			
		}
		// 修改记录的内容 VoSysColumnSemantic sys_column_semantic = context.getSysColumnSemantic( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		setBizLog("修改业务字段：", context,column_name);
	}
	
	/** 增加业务字段信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30403003( SysColumnSemanticContext context ) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		DataBus db_rec = context.getRecord("record");
		TxnContext txnContext = new TxnContext();
		DataBus db_sel = txnContext.getRecord("select-key");
		String column_name = db_rec.getValue("column_name");
		// type用来区分添加还是修改.
		db_sel.setValue("type", "add");
		db_sel.setValue("column_no", db_rec.getValue("column_no"));
		db_sel.setValue("column_name", column_name);
		db_sel.setValue("column_name_cn", db_rec.getValue("column_name_cn"));
		db_sel.setValue("table_no", db_rec.getValue("table_no"));
//		int i = table.executeFunction( "validateColumnPrimaryKey", txnContext, "select-key", "record" );
//		if (i > 0)
//		{
//			throw new TxnDataException("999999","字段编码重复，请更换一个字段编码！");
//		}
		int j = table.executeFunction( "validateColumnName", txnContext, "select-key", "record" );
		if (j > 0){
			throw new TxnDataException("999999","同一业务表下字段名重复，请更换一个字段名！");
		}
		int k = table.executeFunction( "validateColumnNameCn", txnContext, "select-key", "record" );
		if (k > 0){
			throw new TxnDataException("999999","同一业务表下字段中文名重复，请更换一个字段中文名！");
		}
		// 增加记录的内容 VoSysColumnSemantic sys_column_semantic = context.getSysColumnSemantic( inputNode );
		String column_byname = "";
		int m = table.executeFunction( "queryColumnByName", txnContext, "select-key", "record" );
		if (m > 0){
			table.executeFunction( "queryMaxAlias", txnContext, "select-key", "record" );
			String alias = txnContext.getRecord("record").getValue("column_byname");
			if(alias==null||alias.equals("")){
				column_byname = "ALIAS1000001";
			}else{
				int count = Integer.parseInt(alias.substring(5)) + 1;
				String newCount = String.valueOf(count);
				int reCount = newCount.length();
				column_byname = alias.substring(0, 12-reCount) + newCount;
			}
		}
		context.getRecord("record").setValue("column_byname", column_byname);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		setBizLog("增加业务字段：", context,column_name);
	}
	
	/** 查询业务字段用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30403004( SysColumnSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysColumnSemanticPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysColumnSemantic result = context.getSysColumnSemantic( outputNode );
	}
	
	/** 删除业务字段信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30403005( SysColumnSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Recordset rs = context.getRecordset("primary-key");
		String column_names = "";
		for (int i=0; i < rs.size(); i++)
		{
			column_names+= "," + rs.get(i).getValue("column_name");
		}		
		if(!column_names.equals("")) column_names = column_names.substring(1);		
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		setBizLog("删除业务字段：", context,column_names);
	}
	
	/**
	 * 校验字段能否被添加
	 * @param context
	 * @throws TxnException
	 */
	public void txn30403006( SysColumnSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		// this.callService("cn.gwssi.gwaic.metadata.txn.TxnSysColumnSemantic", "txn30403003", context);
	}
	
	/**
	 * 根据表名查询业务字段列表
	 * @param context
	 * @throws TxnException
	 */
	public void txn30403007( SysColumnSemanticContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getColumnListByTableName", context, inputNode, outputNode);
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
		SysColumnSemanticContext appContext = new SysColumnSemanticContext( context );
		invoke( method, appContext );
	}
}
