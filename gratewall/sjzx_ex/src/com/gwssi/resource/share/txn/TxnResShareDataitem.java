package com.gwssi.resource.share.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.resource.share.vo.ResShareDataitemContext;

public class TxnResShareDataitem extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnResShareDataitem.class,
														ResShareDataitemContext.class);

	// 数据表名称
	private static final String	TABLE_NAME		= "res_share_dataitem";

	// 查询列表
	private static final String	ROWSET_FUNCTION	= "select res_share_dataitem list";

	// 查询记录
	private static final String	SELECT_FUNCTION	= "select one res_share_dataitem";

	// 修改记录
	private static final String	UPDATE_FUNCTION	= "update one res_share_dataitem";

	// 增加记录
	private static final String	INSERT_FUNCTION	= "insert one res_share_dataitem";

	// 删除记录
	private static final String	DELETE_FUNCTION	= "delete one res_share_dataitem";

	/**
	 * 构造函数
	 */
	public TxnResShareDataitem()
	{

	}

	/**
	 * 初始化函数
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * 查询共享数据项信息列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn20301021(ResShareDataitemContext context)
			throws TxnException
	{
		System.out.println(context);

		String share_table_id = context.getRecord("select-key").getValue(
				"share_table_id");
		String sql = "select t1.service_targets_name,t2.topics_name,t.table_name_en,t.table_name_cn "
				+ "from res_share_table t,res_business_topics t2,res_service_targets t1 "
				+ "where   t1.service_targets_id=t2.service_targets_id and t2.business_topics_id=t.business_topics_id "
				+ "and t.share_table_id='" + share_table_id + "' ";

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeRowset(sql, context, "fatherRecord");

		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录集 VoResShareDataitem result[] = context.getResShareDataitems(
		// outputNode );

	}

	/**
	 * 修改共享数据项信息信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn20301022(ResShareDataitemContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 修改记录的内容 VoResShareDataitem res_share_dataitem =
		// context.getResShareDataitem( inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 增加共享数据项信息信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn20301023(ResShareDataitemContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 增加记录的内容 VoResShareDataitem res_share_dataitem =
		// context.getResShareDataitem( inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 查询共享数据项信息用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn20301024(ResShareDataitemContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoResShareDataitemPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录内容 VoResShareDataitem result = context.getResShareDataitem(
		// outputNode );
	}

	/**
	 * 删除共享数据项信息信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn20301025(ResShareDataitemContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 删除记录的主键列表 VoResShareDataitemPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 跳转到代码集查看页面
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn20301026(ResShareDataitemContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		table.executeFunction("queryCodeMusterById", context, inputNode,
				outputNode);
		
	}

	/**
	 * 重载父类的方法，用于替换交易接口的输入变量 调用函数
	 * 
	 * @param funcName
	 *            方法名称
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数");
		}

		// 执行
		ResShareDataitemContext appContext = new ResShareDataitemContext(
				context);
		invoke(method, appContext);
	}
}
