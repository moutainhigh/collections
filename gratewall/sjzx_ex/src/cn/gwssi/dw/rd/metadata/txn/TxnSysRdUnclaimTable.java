package cn.gwssi.dw.rd.metadata.txn;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import oracle.jdbc.OracleConnection;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.ds.ConnectFactory;
import cn.gwssi.common.dao.ds.source.DBController;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.metadata.vo.SysRdUnclaimTableContext;

import com.gwssi.common.database.DBOperation;
import com.gwssi.common.util.CalendarUtil;

public class TxnSysRdUnclaimTable extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnSysRdUnclaimTable.class,
														SysRdUnclaimTableContext.class);

	// 数据表名称
	private static final String	TABLE_NAME		= "sys_rd_unclaim_table";

	// 查询记录
	private static final String	SELECT_FUNCTION	= "select one sys_rd_unclaim_table";

	// 修改记录
	private static final String	UPDATE_FUNCTION	= "update one sys_rd_unclaim_table";

	// 增加记录
	private static final String	INSERT_FUNCTION	= "insert one sys_rd_unclaim_table";

	// 删除记录
	private static final String	DELETE_FUNCTION	= "delete one sys_rd_unclaim_table";

	public static String[]		TYPE_OF_TABLE	= { "TABLE" };

	public static String[]		TYPE_OF_VIEW	= { "VIEW" };

	/**
	 * 构造函数
	 */
	public TxnSysRdUnclaimTable()
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
	 * 查询未认领表列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn80002000(SysRdUnclaimTableContext context)
			throws TxnException
	{
		// System.out.println("TxnSysRdTable>>>>txn80002207");
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("selectStateOfTable", context, inputNode,
				outputNode);
	}

	/**
	 * 查询未认领表列表1
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn80002001(SysRdUnclaimTableContext context)
			throws TxnException
	{
		// System.out.println("TxnSysRdTable>>>>txn80002207");
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("selectStateOfTable", context, inputNode,
				outputNode);
	}

	/**
	 * 查询未认领表列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn80002101(SysRdUnclaimTableContext context)
			throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String unclaim_table_code = db.getValue("unclaim_table_code");
		if (unclaim_table_code != null && !"".equals(unclaim_table_code)) {
			unclaim_table_code = unclaim_table_code.toUpperCase();
			db.setValue("unclaim_table_code", unclaim_table_code);
		}

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoSysRdUnclaimTableSelectKey selectKey =
		// context.getSelectKey( inputNode );
		// table.executeFunction( ROWSET_FUNCTION, context, inputNode,
		// outputNode );
		table.executeFunction("queryUnclaimTable", context, inputNode,
				outputNode);
		/*
		 * Recordset rs = context.getRecordset(outputNode); for (int i = 0; i <
		 * rs.size(); i ++) { DataBus dataBus = rs.get(i);
		 * dataBus.setValue("table_name",
		 * dataBus.getValue("unclaim_table_name"));
		 * dataBus.setValue("table_code",
		 * dataBus.getValue("unclaim_table_code")); }
		 */
		// System.out.println(">>>> query unclaim list \n"+context);
		// 查询到的记录集 VoSysRdUnclaimTable result[] = context.getSysRdUnclaimTables(
		// outputNode );
	}

	/**
	 * 修改未认领表信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn80002102(SysRdUnclaimTableContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 修改记录的内容 VoSysRdUnclaimTable sys_rd_unclaim_table =
		// context.getSysRdUnclaimTable( inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
		// table.executeFunction( "insertClaimTable", context, inputNode,
		// outputNode );
	}

	/**
	 * 增加未认领表信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn80002103(SysRdUnclaimTableContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 增加记录的内容 VoSysRdUnclaimTable sys_rd_unclaim_table =
		// context.getSysRdUnclaimTable( inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 查询未认领表用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn80002104(SysRdUnclaimTableContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoSysRdUnclaimTablePrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录内容 VoSysRdUnclaimTable result = context.getSysRdUnclaimTable(
		// outputNode );
	}

	/**
	 * 删除未认领表信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn80002105(SysRdUnclaimTableContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 删除记录的主键列表 VoSysRdUnclaimTablePrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	public void txn80002106(SysRdUnclaimTableContext context)
			throws TxnException
	{
		Recordset rs = context.getRecordset(inputNode);

		for (int i = 0; i < rs.size(); i++) {
			DataBus dataBus = rs.get(i);

			dataBus.setValue("sys_rd_unclaim_table_id", dataBus
					.getValue("sys_rd_unclaim_table_id"));
			dataBus.setValue("table_name", dataBus
					.getValue("unclaim_table_name"));
			dataBus.setValue("table_code", dataBus
					.getValue("unclaim_table_code"));
			dataBus.setValue("first_record_count", dataBus
					.getValue("cur_record_count"));
			dataBus.setValue("table_index", dataBus
					.getValue("tb_index_columns"));
			dataBus.setValue("table_primary_key", dataBus
					.getValue("tb_pk_columns"));
		}

	}

	/**
	 * 更新未认领表数据量字段&&查询未认领表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn80002108(SysRdUnclaimTableContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String db_name = context.getRecord("record").getValue("db_name");
		//判断是不是基础数据源,如果不是,则要取对应数据源来执行查询
		if (!db_name.equals("gwssi")) {
			String table_name = context.getRecord("record").getValue("unclaim_table_code");
			String table_id = context.getRecord("record").getValue("sys_rd_unclaim_table_id");
			ConnectFactory cf = cn.gwssi.common.dao.ds.ConnectFactory
					.getInstance();
			DBController dbcon = cf.getConnection(db_name);
			OracleConnection conn = (OracleConnection) dbcon.getConnection();
			ResultSet rs = null;
			String count = "0";
			try {
				conn.setRemarksReporting(true);
				rs = conn.createStatement().executeQuery(
						"select count(1) c from " + table_name);
				while (rs.next()) {
					count = rs.getString("c");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbcon.closeResultSet(rs);
			}
			//修改未认领表的数据量
			table.executeUpdate("update sys_rd_unclaim_table  set cur_record_count="
							+ count
							+ " where sys_rd_unclaim_table_id='"
							+ table_id + "'");

		} else {
			table.executeFunction("updateRecordCount", context, inputNode,
					outputNode);
		}
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 未认领视图查询
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn80003101(SysRdUnclaimTableContext context)
			throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String unclaim_table_code = db.getValue("unclaim_table_code");
		if (unclaim_table_code != null && !"".equals(unclaim_table_code)) {
			unclaim_table_code = unclaim_table_code.toUpperCase();
			db.setValue("unclaim_table_code", unclaim_table_code);
		}

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryUnclaimViewList", context, inputNode,
				outputNode);
	}

	/**
	 * 未认领函数查询
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn80004101(SysRdUnclaimTableContext context)
			throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String unclaim_table_code = db.getValue("unclaim_table_code");
		if (unclaim_table_code != null && !"".equals(unclaim_table_code)) {
			unclaim_table_code = unclaim_table_code.toUpperCase();
			db.setValue("unclaim_table_code", unclaim_table_code);
		}
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryUnclaimFunctionList", context, inputNode,
				outputNode);
	}

	/**
	 * 未认领存储过程查询
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn80005101(SysRdUnclaimTableContext context)
			throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String unclaim_table_code = db.getValue("unclaim_table_code");
		if (unclaim_table_code != null && !"".equals(unclaim_table_code)) {
			unclaim_table_code = unclaim_table_code.toUpperCase();
			db.setValue("unclaim_table_code", unclaim_table_code);
		}
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryUnclaimProcedureList", context, inputNode,
				outputNode);
	}

	/**
	 * 查询未认领视图，函数，存储过程用于认领
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn80003102(SysRdUnclaimTableContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 认领视图，函数，存储过程
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn80003103(SysRdUnclaimTableContext context)
			throws TxnException
	{
		TxnContext temp = (TxnContext) context.clone();
		DataBus dbus = temp.getRecord(inputNode);

		VoUser user = context.getOperData();
		String operatorId = user.getOperName();
		String operator = user.getUserName();

		// 系统id
		String SysId = dbus.getString("sys_rd_system_id");
		String SysNo = "";
		String SysName = "";
		// 根据系统id查系统编号和系统名称
		if (!"".endsWith(SysId) && SysId != null) {
			DataBus dbSys = new DataBus();
			dbSys.setValue("sys_rd_system_id", SysId);
			BaseTable table = TableFactory.getInstance().getTableObject(this,
					"sys_rd_system");
			table.executeFunction("select one sys_rd_system", context, dbSys,
					outputNode);
			DataBus dbOut = context.getRecord(outputNode);
			SysNo = dbOut.getValue("sys_no");
			SysName = dbOut.getValue("sys_name");
		}

		// 封装要保存的数据字段
		// 业务主题ID
		dbus.setValue("sys_rd_system_id", SysId);
		// 系统名称
		dbus.setValue("sys_name", SysName);
		// 系统编号
		dbus.setValue("sys_no", SysNo);
		// 视图名称
		dbus.setValue("view_name", dbus.getString("unclaim_table_code"));
		// 视图用途
		dbus.setValue("view_use", dbus.getString("remark"));
		// 数据类型
		dbus.setValue("object_type", dbus.getString("data_object_type"));
		dbus.setValue("claim_operator", operatorId);
		dbus.setValue("claim_date", CalendarUtil.getCurrentDate());
		dbus.setValue("timestamp", CalendarUtil.getCurrentDateTime());
		dbus.setValue("changed_status", "0");
		dbus.setValue("object_schema", dbus.getString("object_schema"));
		dbus.setValue("view_script", dbus.getString("object_script"));
		context.getRecord(outputNode).clear();
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				"SYS_RD_CLAIM_VIEW");
		table.executeFunction("insert one sys_rd_claim_view", context, dbus,
				outputNode);
	}

	/**
	 * 查询未认领视图，函数，存储过程信息
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn80003104(SysRdUnclaimTableContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoSysRdUnclaimTablePrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 针对数据源形式获取table,视图
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws SQLException
	 */
	public void txn80003107(SysRdUnclaimTableContext context)
			throws TxnException
	{
		DataBus db = context.getRecord("select-key");
		String db_name = db.getValue("db_name");
		String table_type = db.getValue("table_type");
		String[] typeOfTable = null;
		if (table_type != null && table_type.equals("TABLE")) {
			typeOfTable = TYPE_OF_TABLE;
		} else if (table_type != null && table_type.equals("VIEW")) {
			typeOfTable = TYPE_OF_VIEW;
		}
		Recordset rds = new Recordset();
		ConnectFactory cf = cn.gwssi.common.dao.ds.ConnectFactory.getInstance();
		DBController dbcon = null;
		OracleConnection conn = null;
		ResultSet rs = null;
		try {
			dbcon = cf.getConnection(db_name);
			conn = (OracleConnection) dbcon.getConnection();
			conn.setRemarksReporting(true);
			DatabaseMetaData dbmd = conn.getMetaData();
			String schema = dbmd.getUserName();
			rs = dbmd.getTables(null, schema, "%", typeOfTable);
			int i = 0;
			while (rs.next()) {
				i++;
				String tableName = rs.getString("TABLE_NAME");
				String tableType = rs.getString("TABLE_TYPE");
				String remarks = "";
				Object remarks_obj = rs.getObject(5);
				if (remarks_obj != null && (remarks_obj instanceof String)) {
					remarks = (String) remarks_obj;
				}
				// System.out.println(tableName+" -- "+ remarks);
				DataBus db2 = new DataBus();
				db2.setValue("table_name", tableName);
				db2.setValue("table_type", tableType);
				db2.setValue("table_schema", schema);
				db2.setValue("remarks", remarks);
				rds.add(db2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[txn80003107] 执行错误！ ");
		} finally {
			dbcon.closeResultSet(rs);
		}
		context.addRecord("recordTable", rds);
	}

	/**
	 * 针对数据源形式获取表中的列信息
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws SQLException
	 */
	public void txn80003108(SysRdUnclaimTableContext context)
			throws TxnException
	{
		DataBus db = context.getRecord("select-key");
		String db_name = db.getValue("db_name");
		String table_name = db.getValue("table_name");
		Recordset rds = new Recordset();

		ConnectFactory cf = cn.gwssi.common.dao.ds.ConnectFactory.getInstance();
		OracleConnection conn = null;
		DBController dbcon = null;
		ResultSet rs = null;
		try {
			dbcon = cf.getConnection(db_name);
			conn = (OracleConnection) dbcon.getConnection();
			conn.setRemarksReporting(true);
			DatabaseMetaData dbmd = conn.getMetaData();
			String schema = dbmd.getUserName();
			rs = dbmd.getColumns(null, schema, table_name, "%");
			int i = 0;
			while (rs.next()) {
				i++;
				String columnName = rs.getString("COLUMN_NAME");
				String columnSize = rs.getString("COLUMN_SIZE");
				String isNullable = rs.getString("IS_NULLABLE");
				String nullable = rs.getString("NULLABLE");
				String dataType = rs.getString("DATA_TYPE");
				// String remarks = rs.getString("remarks");
				Object remarks_obj = rs.getObject(12);
				// Object default_value_obj = rs.getString("COLUMN_DEF");
				String remarks = "";
				if (remarks_obj != null && (remarks_obj instanceof String)) {
					remarks = (String) remarks_obj;
				}
				DataBus db2 = new DataBus();
				db2.setValue("column_name", columnName);
				db2.setValue("column_size", columnSize);
				db2.setValue("is_nullable", isNullable);
				db2.setValue("nullable", nullable);
				db2.setValue("data_type", dataType);
				db2.setValue("remarks", remarks);
				db2.setValue("defaultValue", "");
				rds.add(db2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[txn80003108] 执行错误！ ");
		} finally {
			dbcon.closeResultSet(rs);
		}
		context.addRecord("recordColumn", rds);
	}

	/**
	 * 针对数据源形式获取存储过程、函数
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws SQLException
	 */
	public void txn80003109(SysRdUnclaimTableContext context)
			throws TxnException
	{
		DataBus db = context.getRecord("select-key");
		String db_name = db.getValue("db_name");
		String need_procedure_type = db.getValue("procedure_type");

		Recordset rds = new Recordset();
		Recordset rds1 = new Recordset();
		Recordset rds2 = new Recordset();
		ConnectFactory cf = cn.gwssi.common.dao.ds.ConnectFactory.getInstance();
		DBController dbcon = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			dbcon = cf.getConnection(db_name);
			conn = dbcon.getConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			String schema = dbmd.getUserName();
			rs = dbmd.getProcedures(null, schema, "%");
			while (rs.next()) {
				String procedure_name = rs.getString("procedure_name");
				String procedure_type = rs.getString("procedure_type");
				/*
				 * 存储过程 DatabaseMetaData.procedureNoResult=1 函数
				 * DatabaseMetaData.procedureReturnsResult=2 未知
				 * DatabaseMetaData.procedureColumnUnknown=0
				 */
				String remarks = rs.getString("remarks");
				if (procedure_type != null && procedure_type.equals("1")) {
					DataBus db2 = new DataBus();
					db2.setValue("procedure_name", procedure_name);
					db2.setValue("procedure_type", procedure_type);
					db2.setValue("table_schema", schema);
					db2.setValue("remarks", remarks);
					db2.setValue("db_name", db_name);
					rds1.add(db2);
				} else if (procedure_type != null && procedure_type.equals("2")) {
					DataBus db2 = new DataBus();
					db2.setValue("procedure_name", procedure_name);
					db2.setValue("procedure_type", procedure_type);
					db2.setValue("table_schema", schema);
					db2.setValue("remarks", remarks);
					db2.setValue("db_name", db_name);
					rds2.add(db2);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbcon.closeResultSet(rs);
		}

		if (need_procedure_type != null && need_procedure_type.equals("1")) {
			context.addRecord("recordProcedure", rds1);
		} else if (need_procedure_type != null
				&& need_procedure_type.equals("2")) {
			context.addRecord("recordFunction", rds2);
		} else {
			if (rds1 != null && rds1.size() > 0) {
				DataBus[] dbs1 = rds1.toArray();
				rds.add(dbs1);
			}
			if (rds2 != null && rds2.size() > 0) {
				DataBus[] dbs2 = rds2.toArray();
				rds.add(dbs2);
			}
			context.addRecord("record", rds);
		}
	}

	/**
	 * 针对数据源形式获取约束
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws SQLException
	 */
	public void txn80003110(SysRdUnclaimTableContext context)
			throws TxnException
	{
		DataBus db = context.getRecord("select-key");
		String db_name = db.getValue("db_name");
		String need_table_name = db.getValue("table_name");

		Recordset rds = new Recordset();
		ConnectFactory cf = cn.gwssi.common.dao.ds.ConnectFactory.getInstance();
		DBController dbcon = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			dbcon = cf.getConnection(db_name);
			conn = dbcon.getConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			String schema = dbmd.getUserName();
			rs = dbmd.getPrimaryKeys(null, schema, need_table_name);
			while (rs.next()) {
				String table_name = rs.getString("table_name");
				String column_name = rs.getString("column_name");
				String pk_name = rs.getString("pk_name");
				String key_seq = rs.getString("key_seq");
				DataBus db2 = new DataBus();
				db2.setValue("table_name", table_name);
				db2.setValue("column_name", column_name);
				db2.setValue("table_schema", schema);
				db2.setValue("pk_name", pk_name);
				db2.setValue("key_seq", key_seq);
				db2.setValue("db_name", db_name);
				rds.add(db2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbcon.closeResultSet(rs);
		}
		context.addRecord("recordPk", rds);
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
		SysRdUnclaimTableContext appContext = new SysRdUnclaimTableContext(
				context);
		invoke(method, appContext);
	}
}
