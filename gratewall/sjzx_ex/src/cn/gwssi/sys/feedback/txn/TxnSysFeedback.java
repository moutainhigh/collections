package cn.gwssi.sys.feedback.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.sys.feedback.vo.SysFeedbackContext;

public class TxnSysFeedback extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysFeedback.class, SysFeedbackContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_feedback";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_feedback list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_feedback";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_feedback";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_feedback";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_feedback";
	
	/**
	 * 构造函数
	 */
	public TxnSysFeedback()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询意见反馈列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn711001( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysFeedbackSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysFeedback result[] = context.getSysFeedbacks( outputNode );
	}
	
	/** 修改意见反馈信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn711002( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysFeedback sys_feedback = context.getSysFeedback( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加意见反馈信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn711003( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String uuid = com.gwssi.common.util.UuidGenerator.getUUID();
		context.getRecord(inputNode).setValue("sys_feedback_id", uuid);
		// 增加记录的内容 VoSysFeedback sys_feedback = context.getSysFeedback( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询意见反馈用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn711004( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysFeedbackPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysFeedback result = context.getSysFeedback( outputNode );
	}
	
	/** 删除意见反馈信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn711005( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysFeedbackPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 意见反馈的记录信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn711006( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 意见反馈的修改日志
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn711007( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.getHistoryLog( context, inputNode, outputNode );
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
		SysFeedbackContext appContext = new SysFeedbackContext( context );
		invoke( method, appContext );
	}
}
