package cn.gwssi.dw.rd.metadata.txn;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.gwssi.common.util.CalendarUtil;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.concurrent.*;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

public class TxnSysRdSystem extends TxnService
{
	/**
	 * 类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdSystem.class, TxnContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_system";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_system list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_system";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_system";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_system";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_system";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdSystem()
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
	 * 查询主题列表
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000111( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
	}
	
	/**
	 * 修改主题信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000112( TxnContext context ) throws TxnException
	{
		DataBus inputData = context.getRecord(inputNode);
		
		//时间戳
		inputData.setValue("timestamp",CalendarUtil.getCurrentDateTime());
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}
	
	/**
	 * 增加主题信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000113( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		DataBus inputData = context.getRecord(inputNode);
		//排序号
		table.executeFunction("selectsortsysrdsystem", context, inputNode, "temp");
		String num = context.getRecord("temp").getString("num");
		String sort ="1";
		if(num!=null && !"".equals(num)){
			sort = String.valueOf(Integer.parseInt(num)+1);
		}
		inputData.setValue("sort", sort);
		//时间戳
		inputData.setValue("timestamp", CalendarUtil.getCurrentDateTime());
		
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}
	
	/**
	 * 查询主题用于修改
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000114( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
	}
	
	/**
	 * 删除主题信息
	 * @param context
	 * @throws TxnException
	 */
	public void txn8000115( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
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
		TxnContext appContext = new TxnContext( context );
		invoke( method, appContext );
		//SysSystemSemanticContext appContext = new SysSystemSemanticContext( context );
		//invoke( method, appContext );
	}
}
