package cn.gwssi.dw.rd.standard.txn;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;
import cn.gwssi.dw.rd.standard.vo.SysRdStandardTableContext;

public class TxnSysRdStandardTable extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdStandardTable.class, SysRdStandardTableContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_standard_table";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_standard_table list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_standard_table";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_standard_table";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_standard_table";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_standard_table";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdStandardTable()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询实体信息列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000211( SysRdStandardTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysRdStandardTableSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysRdStandardTable result[] = context.getSysRdStandardTables( outputNode );
	}
	
	/** 修改实体信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000212( SysRdStandardTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysRdStandardTable sys_rd_standard_table = context.getSysRdStandardTable( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加实体信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000213( SysRdStandardTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoSysRdStandardTable sys_rd_standard_table = context.getSysRdStandardTable( inputNode );
		table.executeFunction("queryMaxSort", context, inputNode, "sort");//获取最大排序号
		Recordset rs = context.getRecordset("sort");
		DataBus tempSort = (DataBus)rs.get(0);
        if(tempSort.getValue("max(sort)")!=null&&!tempSort.getValue("max(sort)").equals("")){
        	int Intsort=Integer.parseInt(tempSort.getValue("max(sort)"))+1;
            String sort=Intsort+"";
        	context.getRecord("record").setValue("sort", sort);
        }else{
        	context.getRecord("record").setValue("sort","1" );
        }
		context.getRecord("record").setValue("sys_rd_standard_table_id",UuidGenerator.getUUID());
		String TimeStamp=CalendarUtil.getCurrentDateTime();//添加时间戳
		context.getRecord("record").setValue("timestamp", TimeStamp);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		
	}
	
	/** 查询实体信息用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000214( SysRdStandardTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardTablePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandardTable result = context.getSysRdStandardTable( outputNode );
	}
	
	/** 删除实体信息信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000215( SysRdStandardTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysRdStandardTablePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询实体信息用于视图
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn7000216( SysRdStandardTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdStandardTablePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdStandardTable result = context.getSysRdStandardTable( outputNode );
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
		SysRdStandardTableContext appContext = new SysRdStandardTableContext( context );
		invoke( method, appContext );
	}
}
