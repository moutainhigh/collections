package cn.gwssi.dw.rd.metadata.txn;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.proxy.config.ParamValues;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.metadata.dao.SysRdClaimView;
import cn.gwssi.dw.rd.metadata.vo.SysRdUnclaimTableContext;

public class TxnSysRdClaimView extends TxnService
{
	
	/**
	 * 类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdClaimView.class, TxnContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_claim_view";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_claim_view list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_claim_view";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_claim_view";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_claim_view";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_claim_view";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdClaimView()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/**
	 * 视图认领情况查询
	 * @param context
	 * @throws TxnException
	 */
	public void txn80003200( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryClaimViewList", context, inputNode, outputNode );
	}
	
	/**
	 * 函数认领情况查询
	 * @param context
	 * @throws TxnException
	 */
	public void txn80004200( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryClaimFunctionList", context, inputNode, outputNode );
	}
	
	/**
	 * 存储过程认领情况查询
	 * @param context
	 * @throws TxnException
	 */
	public void txn80005200( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryClaimProcedureList", context, inputNode, outputNode );
	}
	
	/** 查询已认领视图列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80003201( TxnContext context ) throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String view_name = db.getValue("view_name");
		if(view_name!=null && !"".equals(view_name)){
			view_name = view_name.toUpperCase();
			db.setValue("view_name", view_name);
		}
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryClaimViewList", context, inputNode, outputNode );
	}
	
	/** 查询已认领视图列表——概览
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80003206( TxnContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, 5);
		callService("80003201", context);
	}
	/** 查询已认领函数列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80004201( TxnContext context ) throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String view_name = db.getValue("view_name");
		if(view_name!=null && !"".equals(view_name)){
			view_name = view_name.toUpperCase();
			db.setValue("view_name", view_name);
		}
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryClaimFunctionList", context, inputNode, outputNode );
	}
	/** 查询已认领存储过程列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80005201( TxnContext context ) throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String view_name = db.getValue("view_name");
		if(view_name!=null && !"".equals(view_name)){
			view_name = view_name.toUpperCase();
			db.setValue("view_name", view_name);
		}
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "queryClaimProcedureList", context, inputNode, outputNode );
	}
	
	/** 查看已认领视图，函数，存储过程用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80003204( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 修改已认领视图，函数，存储过程信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80003202( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 删除已认领视图，函数，存储过程
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80003203( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/**
	 * 视图认领情况查询
	 * @param context
	 * @throws TxnException
	 */
	public void txn80003000( TxnContext context ) throws TxnException
	{
		/*String object_type = context.getRecord(inputNode).getValue("object_type");
		String data_source_id = context.getRecord(inputNode).getValue("sys_rd_data_source_id");
		String dbName = context.getRecord(inputNode).getValue("sys_rd_data_source_id");
		StringBuffer sql = new StringBuffer();
		sql.append("select temp.* from (");
		sql.append("select a.data_source_id,COALESCE(a.cnt1,0) as total,COALESCE(b.cnt2,0) as claim,COALESCE(COALESCE(a.cnt1,0)-COALESCE(b.cnt2,0),0) as unclaim from ");
		sql.append("(select sys_rd_data_source_id,count(*) as cnt1 from sys_rd_unclaim_table where data_object_type='" + object_type + "' group by sys_rd_data_source_id) a left join ");
		sql.append("(select sys_rd_data_source_id,count(*) as cnt2 from sys_rd_claim_view where object_type='" + object_type + "' group by sys_rd_data_source_id) b on a.sys_rd_data_source_id=b.sys_rd_data_source_id");
		sql.append(") as temp where 1=1");
		if(!"".equals(data_source_id) && data_source_id !=null){
			sql.append(" and sys_rd_data_source_id like'%" + data_source_id + "%'");
		}
		if(!"".equals(dbName) && dbName !=null){
			sql.append(" and db_name like'%" + dbName + "%'");
		}
		DataBus inputData = new DataBus();
		inputData.setValue("sql", sql.toString());
		sysRdClaimView.QueryVFP(context).execute(inputData, outputNode);*/
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "QueryView", context, inputNode, outputNode );
		
	}
	
	/**
	 * 函数认领情况查询
	 * @param context
	 * @throws TxnException
	 */
	public void txn80004000( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "QueryFunction", context, inputNode, outputNode );
	}
	
	/**
	 * 过程认领情况查询
	 * @param context
	 * @throws TxnException
	 */
	public void txn80005000( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "QueryProcedure", context, inputNode, outputNode );
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
		SysRdUnclaimTableContext appContext = new SysRdUnclaimTableContext( context );
		invoke( method, appContext );
	}
}
