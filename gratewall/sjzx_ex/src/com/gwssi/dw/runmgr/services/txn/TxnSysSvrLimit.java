package com.gwssi.dw.runmgr.services.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.services.vo.SysSvrLimitContext;

public class TxnSysSvrLimit extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnSysSvrLimit.class,
														SysSvrLimitContext.class);

	// 数据表名称
	private static final String	TABLE_NAME		= "sys_svr_limit";

	// 查询列表
	private static final String	ROWSET_FUNCTION	= "select sys_svr_limit list";

	// 查询记录
	private static final String	SELECT_FUNCTION	= "select one sys_svr_limit";

	// 修改记录
	private static final String	UPDATE_FUNCTION	= "update one sys_svr_limit";

	// 增加记录
	private static final String	INSERT_FUNCTION	= "insert one sys_svr_limit";

	// 删除记录
	private static final String	DELETE_FUNCTION	= "delete one sys_svr_limit";

	/**
	 * 构造函数
	 */
	public TxnSysSvrLimit()
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
	 * 查询用户服务限制列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn50011701(SysSvrLimitContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoSysSvrLimitSelectKey selectKey = context.getSelectKey(
		// inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录集 VoSysSvrLimit result[] = context.getSysSvrLimits( outputNode
		// );
	}

	/**
	 * 修改用户服务限制信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn50011702(SysSvrLimitContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 修改记录的内容 VoSysSvrLimit sys_svr_limit = context.getSysSvrLimit(
		// inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 增加用户服务限制信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn50011703(SysSvrLimitContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 增加记录的内容 VoSysSvrLimit sys_svr_limit = context.getSysSvrLimit(
		// inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 查询用户服务限制用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn50011704(SysSvrLimitContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoSysSvrLimitPrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录内容 VoSysSvrLimit result = context.getSysSvrLimit( outputNode );
	}

	/**
	 * 删除用户服务限制信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn50011705(SysSvrLimitContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 删除记录的主键列表 VoSysSvrLimitPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 配置用户服务限制信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn50011706(SysSvrLimitContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// System.out.println("in txn50011706 ---------------------- >>>> \n"+
		// context);
		DataBus db = context.getRecord("record");
		TxnContext temp = new TxnContext();
		DataBus d1 = new DataBus();
		String sys_svr_service_id = db.getValue("sys_svr_service_id");
		String sys_svr_user_id = db.getValue("sys_svr_user_id");
		String visit_weeks = db.getValue("visit_weeks");
		d1.setValue("sys_svr_service_id", sys_svr_service_id);
		d1.setValue("sys_svr_user_id", sys_svr_user_id);
		temp.addRecord("select-key", d1);
		this.callService("50011701", temp);
		// System.out.println("in 50011701 ---------------------- >>>> \n"+
		// temp);
		Recordset rs = temp.getRecordset("record");
		if (rs != null && rs.size() > 0) {
			// 删除操作
			StringBuffer sb = new StringBuffer(" delete from sys_svr_limit ");
			sb.append(" where sys_svr_service_id='").append(sys_svr_service_id)
					.append("' and sys_svr_user_id='").append(sys_svr_user_id)
					.append("' ");
			int j = table.executeUpdate(sb.toString());
			// System.out.println("删除操作的结果是 "+j);
		}
		String[] s = new String[8];
		for (int j = 1; j < 8; j++) {
			StringBuffer sb1 = new StringBuffer(
					" insert into  sys_svr_limit (sys_svr_limit_id, sys_svr_user_id, sys_svr_service_id, is_limit_week,"
							+ "is_limit_time,is_limit_number,is_limit_total,limit_week,limit_time,limit_start_time, limit_end_time, limit_number, limit_total) values ");
			sb1.append("( '").append(UuidGenerator.getUUID() + "',")
					.append("'").append(sys_svr_user_id).append("'").append(
							",'").append(sys_svr_service_id).append("'");
			String is_limit_week = "0";
			if (visit_weeks != null && !visit_weeks.equals("")) {
				String weeks = ";" + visit_weeks + ";";
				String e = String.valueOf(j);
				if (weeks.indexOf(e) >= 0) {
					// 能访问的
				} else {
					// 不能访问的
					is_limit_week = "1";
				}
			}
			sb1.append(",'").append(is_limit_week).append("'").append(",'")
					.append(db.getValue("is_limit_time")).append("'").append(
							",'").append(db.getValue("is_limit_number"))
					.append("'").append(",'").append(
							db.getValue("is_limit_total")).append("'").append(
							",'").append("" + j + "").append("'").append(",'")
					.append(
							db.getValue("limit_start_time") + "-"
									+ db.getValue("limit_end_time"))
					.append("'").append(",'").append(
							db.getValue("limit_start_time")).append("'")
					.append(",'").append(db.getValue("limit_end_time")).append(
							"'").append(",'").append(
							db.getValue("limit_number")).append("'").append(
							",'").append(db.getValue("limit_total")).append(
							"' ) ");
			// System.out.println("第"+j+"条SQL是：" +sb1);
			s[j] = sb1.toString();
		}

		SqlStatement[] sqlStmt = new SqlStatement[7];
		for (int j = 1; j < 8; j++) {
			SqlStatement ss = new SqlStatement();
			ss.addSqlStmt(s[j]);
			sqlStmt[j - 1] = ss;
		}

		table.executeBatch(sqlStmt);
		
		StringBuffer delete_lock = new StringBuffer(" delete from sys_svr_lock ");
		delete_lock.append(" where sys_svr_service_id='").append(
				sys_svr_service_id).append("' and sys_svr_user_id='").append(
				sys_svr_user_id).append("' ");
		int j = table.executeUpdate(delete_lock.toString());

		// 增加记录的内容 VoSysSvrLimit sys_svr_limit = context.getSysSvrLimit(
		// inputNode );
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
		SysSvrLimitContext appContext = new SysSvrLimitContext(context);
		invoke(method, appContext);
	}
}
