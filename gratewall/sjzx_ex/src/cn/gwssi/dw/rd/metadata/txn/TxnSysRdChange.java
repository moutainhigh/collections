package cn.gwssi.dw.rd.metadata.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.metadata.vo.SysRdChangeContext;

public class TxnSysRdChange extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdChange.class, SysRdChangeContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_change";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_change list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_change";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_change";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_change";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_change";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdChange()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询变更记录表列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002301( SysRdChangeContext context ) throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String table_name = db.getValue("table_name");
		String column_name = db.getValue("column_name");
		if(table_name!=null && !"".equals(table_name)){
			table_name = table_name.toUpperCase();
			db.setValue("table_name", table_name);
		}
		if(column_name!=null && !"".equals(column_name)){
			column_name = column_name.toUpperCase();
			db.setValue("column_name", column_name);
		}
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysRdChangeSelectKey selectKey = context.getSelectKey( inputNode );
		//table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		table.executeFunction( "queryChangeTable", context, inputNode, outputNode );
		// 查询到的记录集 VoSysRdChange result[] = context.getSysRdChanges( outputNode );
	}
	
	/** 修改变更记录表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002302( SysRdChangeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysRdChange sys_rd_change = context.getSysRdChange( inputNode );
		context.getRecord("record").setValue("change_result","1" );
		String change_item = context.getRecord("record").getValue("change_item");

		if("1".equals(change_item)){
			//System.out.println("......... 1 ........");
			table.executeFunction( "updatePrimary", context, inputNode, outputNode );
			
		} else if ("2".equals(change_item)){
			//System.out.println("......... 2 ........");
			table.executeFunction( "updateIndex", context, inputNode, outputNode );
			
		} else if ("3".equals(change_item)){
			//System.out.println("......... 3 ........");
			table.executeFunction( "deleteColumn", context, inputNode, outputNode );
			table.executeFunction( "deleteTable", context, inputNode, outputNode );
			
			
		} else if ("4".equals(change_item)){
			//System.out.println("......... 4 ........");
			table.executeFunction( "addColumn", context, inputNode, outputNode );
			
		} else if ("5".equals(change_item)){
			//System.out.println("......... 5 ........");
			table.executeFunction( "changeColumnType", context, inputNode, outputNode );
			
		} else if ("6".equals(change_item)){
			//System.out.println("......... 6 ........");
			table.executeFunction( "changeColumnLength", context, inputNode, outputNode );
			
		} else if ("7".equals(change_item)){
			//System.out.println("......... 7 ........");
			table.executeFunction( "deleteColumn", context, inputNode, outputNode );
			
		} else {
			System.out.println("......... nothing to do ........");
		}
		//更新变更记录表
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加变更记录表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002303( SysRdChangeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysRdChange sys_rd_change = context.getSysRdChange( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询变更记录表用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002304( SysRdChangeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdChangePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		
		VoUser user = context.getOperData();
  		String userName = user.getOperName();
  		
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		context.getRecord("record").setValue("change_time", CalendarUtil.getCurrentDate());	
		context.getRecord("record").setValue("change_oprater", userName);
		// 查询到的记录内容 VoSysRdChange result = context.getSysRdChange( outputNode );
	}
	
	/** 删除变更记录表信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002305( SysRdChangeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysRdChangePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询变更记录表用于查看变更详情
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002306( SysRdChangeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdChangePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdChange result = context.getSysRdChange( outputNode );
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
		SysRdChangeContext appContext = new SysRdChangeContext( context );
		invoke( method, appContext );
	}
}
