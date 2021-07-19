package com.gwssi.sysmgr.priv.func.txn;

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
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.sysmgr.role.vo.OperrolefunContext;

public class TxnOperrolefun extends TxnService {
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod(TxnOperrolefun.class,
			OperrolefunContext.class);

	// 数据表名称
	private static final String TABLE_NAME = "operrolefun";

	// 查询列表
	private static final String ROWSET_FUNCTION = "select operrolefun list";

	// 查询记录
	private static final String SELECT_FUNCTION = "${mod.function.select}";

	// 修改记录
	private static final String UPDATE_FUNCTION = "${mod.function.update}";

	// 增加记录
	private static final String INSERT_FUNCTION = "insert one operrolefun";

	// 删除记录
	private static final String DELETE_FUNCTION = "delete one operrolefun";

	/**
	 * 构造函数
	 */
	public TxnOperrolefun() {

	}

	/**
	 * 初始化函数
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException {

	}

	/**
	 * 查询角色功能权限列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn103031(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoOperrolefunSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录集 VoOperrolefun result[] = context.getOperrolefuns( outputNode
		// );
	}

	/**
	 * 修改角色功能权限信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn103032(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 修改记录的内容 VoOperrolefun operrolefun = context.getOperrolefun( inputNode
		// );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 增加角色功能权限信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn103033(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 增加记录的内容 VoOperrolefun operrolefun = context.getOperrolefun( inputNode
		// );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 查询角色功能权限用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn103034(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoOperrolefunPrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录内容 VoOperrolefun result = context.getOperrolefun( outputNode );
	}

	/**
	 * 删除角色功能权限信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn103035(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 删除记录的主键列表 VoOperrolefunPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 删除角色功能权限信息，按角色ID
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn103036(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String roleid = context.getRecord(inputNode).getValue("roleid");
		String funcList = context.getRecord(inputNode).getValue("funcList");

		if (funcList == null || funcList.equals("")) {

			TxnContext select = new TxnContext();
			DataBus db = new DataBus();
			db.setValue("roleid", roleid);
			select.addRecord(inputNode, db);
			callService("com.gwssi.sysmgr.priv.func.txn.TxnOperrolefun", "txn103031",
					select);
			Recordset rs = select.getRecordset(outputNode);
			for (int i = 0; i < rs.size(); i++) {
				String roleaccid = rs.get(i).getValue("roleaccid");
				TxnContext delete = new TxnContext();
				db = new DataBus();
				db.setValue("objectid", roleaccid);
				db.setValue("dataaccdispobj", "1");
				delete.addRecord("record", db);
				callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp",
						"txn103047", delete);

				delete = new TxnContext();
				db = new DataBus();
				db.setValue("objectid", roleaccid);
				db.setValue("dataaccdispobj", "2");
				delete.addRecord("record", db);
				callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp",
						"txn103047", delete);
			}
			// 删除记录的主键列表 VoOperrolefunPrimaryKey primaryKey[] =
			// context.getPrimaryKeys( inputNode );
			table.executeFunction("按角色删除", context, inputNode, outputNode);
		} else {

			funcList = "'" + funcList.replaceAll(";", "','") + "'";
			TxnContext valContext = new TxnContext();
			Attribute.setPageRow(valContext, outputNode, -1);
			valContext.getRecord("select-key").setValue("funcList", funcList);
			table.executeFunction("selectFunc", valContext, inputNode,
					outputNode);
			Recordset queryList = valContext.getRecordset(outputNode);
			TxnContext select = null;
			DataBus db = null;
			DataBus queryDb = null;
			String funcCode = "";
			TxnContext deleteContext = null;
			DataBus deleteDb = null;
			String roleaccid = "";
			TxnContext delete = null;
			for (int k = 0; k < queryList.size(); k++) {

				String roleaccidS = "";
				queryDb = queryList.get(k);
				funcCode = queryDb.getValue("funccode");
				select = new TxnContext();
				Attribute.setPageRow(select, outputNode, -1);
				db = new DataBus();
				db.setValue("roleid", roleid);
				db.setValue("txncode", funcCode);
				select.addRecord("select-key", db);
				table.executeFunction("selectOperRoleId", select, inputNode,
						outputNode);
				Recordset rs = select.getRecordset(outputNode);
				// for(int i = 0; i < rs.size(); i++){
				// roleaccid = rs.get(i).getValue("roleaccid");
				// TxnContext delete = new TxnContext();
				// db = new DataBus();
				// db.setValue("objectid", roleaccid);
				// db.setValue("dataaccdispobj", "1");
				// delete.addRecord("record", db);
				// callService("cn.gwssi.csdb.xtgl.txn.TxnDataaccdisp",
				// "txn103047", delete);
				//					
				// delete = new TxnContext();
				// db = new DataBus();
				// db.setValue("objectid", roleaccid);
				// db.setValue("dataaccdispobj", "2");
				// delete.addRecord("record", db);
				// callService("cn.gwssi.csdb.xtgl.txn.TxnDataaccdisp",
				// "txn103047", delete);
				// }
				for (int i = 0; i < rs.size(); i++) {

					roleaccid = rs.get(i).getValue("roleaccid");
					roleaccidS += "," + roleaccid;
				}
				if (roleaccidS != null && !roleaccidS.equals("")) {

					roleaccidS = roleaccidS.substring(1);
					delete = new TxnContext();
					db = new DataBus();
					db.setValue("objectid", roleaccidS);
					delete.addRecord("select-key", db);
					table.executeFunction("deleteDispByObjIdS", delete,
							inputNode, outputNode);
				}
				deleteContext = new TxnContext();
				deleteDb = new DataBus();
				deleteDb.setValue("roleid", roleid);
				deleteDb.setValue("txncode", funcCode);
				deleteContext.addRecord("select-key", deleteDb);

				table.executeFunction("deleteOperRoleFun", deleteContext,
						inputNode, outputNode);
			}
		}
	}

	/**
	 * 查询角色所有交易代码
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn103037(OperrolefunContext context) throws TxnException {
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoOperrolefunPrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("loadRoleTxn", context, inputNode, outputNode);
		// 查询到的记录内容 VoOperrolefun result = context.getOperrolefun( outputNode );
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
			throws TxnException {
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数");
		}

		// 执行
		OperrolefunContext appContext = new OperrolefunContext(context);
		invoke(method, appContext);
	}
}
