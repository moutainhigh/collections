package cn.gwssi.dw.rd.metadata.txn;

import java.lang.reflect.Method;
import java.util.HashMap;


import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.metadata.vo.SysRdColumnContext;

public class TxnSysRdColumn extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdColumn.class, SysRdColumnContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_column";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_column list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_column";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_column";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_column";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_column";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdColumn()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询已认领表字段表列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002501( SysRdColumnContext context ) throws TxnException
	{
		
		DataBus db = context.getRecord(inputNode);
		String column_code = db.getValue("column_code");
		if(column_code!=null && !"".equals(column_code)){
			column_code = column_code.toUpperCase();
			db.setValue("column_code", column_code);
		}
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysRdColumnSelectKey selectKey = context.getSelectKey( inputNode );
		String show_all = context.getRecord(inputNode).getValue("show_all");
		if(show_all!=null&&!show_all.equals("")){
			Attribute.setPageRow(context, outputNode, -1);
		}
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		//页面上为了显示是否做了一次反转，保存时再做一次。
		for(int i=0;i<rs.size();i++)
		{
			if(rs.get(i).getValue("use_type").equals("0"))
				rs.get(i).setValue("use_type","1");
			else if(rs.get(i).getValue("use_type").equals("1"))
				rs.get(i).setValue("use_type","0");
		}
		

		// 查询到的记录集 VoSysRdColumn result[] = context.getSysRdColumns( outputNode );
	}
	
	/** 查询已认领表字段表列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80003501( SysRdColumnContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, 10);
		callService("80002501", context);
	}
	
	/** 查询已认领表字段表列表用于配置接口导出功能
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002507( SysRdColumnContext context ) throws TxnException
	{
		
		
		/*String column_code = db.getValue("column_code");
		if(column_code!=null && !"".equals(column_code)){
			column_code = column_code.toUpperCase();
			db.setValue("column_code", column_code);
		}*/
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "queryColumnType", context, inputNode, outputNode );
		// 查询到的记录集 VoSysRdColumn result[] = context.getSysRdColumns( outputNode );
	}
	/** 修改已认领表字段表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002502( SysRdColumnContext context ) throws TxnException
	{
		DataBus db = context.getRecord("record");
		//页面上为了显示是否做了一次反转，保存时再做一次。
		if(db.getValue("use_type").equals("0"))
			db.setValue("use_type","1");
			else if(db.getValue("use_type").equals("1"))
			db.setValue("use_type","0");
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysRdColumn sys_rd_column = context.getSysRdColumn( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加已认领表字段表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002503( SysRdColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysRdColumn sys_rd_column = context.getSysRdColumn( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询已认领表字段表用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002504( SysRdColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdColumnPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdColumn result = context.getSysRdColumn( outputNode );
	}
	
	/** 删除已认领表字段表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002505( SysRdColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysRdColumnPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询已认领表字段表用于查看
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002506( SysRdColumnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdColumnPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdColumn result = context.getSysRdColumn( outputNode );
	}
		
	/** 查询数据元管理列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn8000601( SysRdColumnContext context ) throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String column_code = db.getValue("column_code");
		if(column_code!=null && !"".equals(column_code)){
			column_code = column_code.toUpperCase();
			db.setValue("column_code", column_code);
		}
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String column_name = context.getRecord(inputNode).getValue("column_name");
		if(column_name !=null && !"".equals(column_name)){
			table.executeFunction( "queryDistinctColumnListByName", context, inputNode, outputNode );
		}else{
			table.executeFunction( "queryDistinctColumnListByCode", context, inputNode, outputNode );
		}
		Recordset rs = context.getRecordset(outputNode);
		for(int i=0;i<rs.size();i++){
			DataBus temp = (DataBus)rs.get(i);
			TxnContext ctx = new TxnContext();
			if(column_name!=null&&!column_name.equals("")){
				table.executeFunction( "queryColumnListByName", ctx, temp, "ListDB" );
			}else{
				table.executeFunction( "queryColumnListByCode", ctx, temp, "ListDB" );
			}
			Recordset rst = ctx.getRecordset("ListDB");
			String column_n = "";
			String column_cn = "";
			String content = "";
			StringBuffer codeBuffer = new StringBuffer();
			StringBuffer contentBuffer = new StringBuffer();
			for(int j=0;j<rst.size();j++){
				DataBus ListTemp = (DataBus)rst.get(j);
				if(column_name !=null && !"".equals(column_name)){
					column_n = ListTemp.getValue("column_name");
					if(ListTemp.getValue("column_code")!=null && !"".equals(ListTemp.getValue("column_code")) && codeBuffer.toString().indexOf(ListTemp.getValue("column_code")) < 0){
						codeBuffer.append(";");
						codeBuffer.append(ListTemp.getValue("column_code"));
					}
					contentBuffer.append(";");
					contentBuffer.append(ListTemp.getValue("table_name"));
					contentBuffer.append("(");
		            contentBuffer.append(ListTemp.getValue("table_code"));
					contentBuffer.append(")");
				}else{
					column_n = ListTemp.getValue("column_code");
					if(ListTemp.getValue("column_name")!=null && !"".equals(ListTemp.getValue("column_name")) && codeBuffer.toString().indexOf(ListTemp.getValue("column_name")) < 0){
						codeBuffer.append(";");
						codeBuffer.append(ListTemp.getValue("column_name"));
					}
					contentBuffer.append(";");
					contentBuffer.append(ListTemp.getValue("table_name"));
					contentBuffer.append("(");
					contentBuffer.append(ListTemp.getValue("table_code"));
					contentBuffer.append(")");
					}
				}
				column_cn = codeBuffer.toString();
			    content = contentBuffer.toString();
			if(column_cn !=null && !"".equals(column_cn)){
				column_cn = codeBuffer.toString().substring(1);
				column_cn = column_cn.replaceAll("\r", "");
				column_cn = column_cn.replaceAll("\n", "");
			}
			if(content !=null && !"".equals(content)){
				content = contentBuffer.toString().substring(1);
				content = content.replaceAll("\r", "");
				content = content.replaceAll("\n", "");
			}

			if(column_name !=null && !"".equals(column_name)){
				temp.setValue("column_code", column_cn);
				temp.setValue("column_name", column_n);
				temp.setValue("content", content);
			}else{
				temp.setValue("column_code", column_n);
				temp.setValue("column_name", column_cn );
				temp.setValue("content", content);
			}
			
				}
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
		SysRdColumnContext appContext = new SysRdColumnContext( context );
		invoke( method, appContext );
	}
}
