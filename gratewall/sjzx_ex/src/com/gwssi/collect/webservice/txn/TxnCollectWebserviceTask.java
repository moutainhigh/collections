package com.gwssi.collect.webservice.txn;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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

import com.genersoft.frame.base.database.DBException;
import com.gwssi.collect.webservice.param.vo.CollectWsParamValueContext;
import com.gwssi.collect.webservice.vo.CollectWebserviceTaskContext;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.webservice.client.TaskInfo;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

public class TxnCollectWebserviceTask extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnCollectWebserviceTask.class,
														CollectWebserviceTaskContext.class);

	// 数据表名称
	private static final String	TABLE_NAME		= "collect_webservice_task";

	// 查询列表
	private static final String	ROWSET_FUNCTION	= "select collect_webservice_task list";

	// 查询记录
	private static final String	SELECT_FUNCTION	= "select one collect_webservice_task";

	// 修改记录
	private static final String	UPDATE_FUNCTION	= "update one collect_webservice_task";

	// 增加记录
	private static final String	INSERT_FUNCTION	= "insert one collect_webservice_task";

	// 删除记录
	private static final String	DELETE_FUNCTION	= "delete one collect_webservice_task";

	/**
	 * 构造函数
	 */
	public TxnCollectWebserviceTask()
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

	public void txn30102111(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//System.out.println("11 ="+context);
		String wsTaskId = context.getRecord("select-key").getValue(
				"webservice_task_id");
		table
				.executeFunction("getWsMethodInfo", context, inputNode,
						outputNode);
		
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// 参数列表
		
		
	}

	public void txn30102112(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String taskId = context.getRecord("select-key").getValue(
				"collect_task_id");
		System.out.println("taskId is " + taskId);
		table.executeFunction("getTaskInfo", context, inputNode, outputNode);

		int count = -1;
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
		count = this.getNum(CollectConstants.TYPE_FTP_TABLE, daoTable);
		count = count + 1;
		String service_no = "";
		System.out.println("count===" + count);
		if (count < 10) {
			service_no = "SERVICE_00" + count;
		} else if (count >= 10 && count <= 99) {
			service_no = "SERVICE_0" + count;
		} else {
			service_no = "SERVICE_" + count;
		}
		context.getRecord("record").setValue("service_no", service_no);// 任务编号
		context.getRecord("record").setValue("collect_task_id", taskId);// 任务编号
	}

	public void txn30102113(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String id = context.getRecord("record").getValue("webservice_task_id");
		String is_code = context.getRecord("record").getValue("encrypt_mode");
		if (is_code != null && is_code.equals("01")) {
			context.getRecord("record").setValue("is_encryption", "0");// 无加密
		} else {
			context.getRecord("record").setValue("is_encryption", "1");// 需要解密
		}

		if (id == null || "".equals(id)) {
			id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("webservice_task_id", id);// 数据表ID
			context.getRecord("record").setValue("method_status",
					CollectConstants.TYPE_QY);// 数据表ID
			context.getRecord("primary-key").setValue("webservice_task_id", id);// 数据表ID

			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
			//System.out.println("增加方法："+context);
		} else {
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
			//System.out.println("编辑方法："+context);
		}
	}

	public void txn30102114(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String wsTaskId = context.getRecord("select-key").getValue(
				"webservice_task_id");
		System.out.println("wsTaskId is " + wsTaskId);
		table
				.executeFunction("getWsMethodInfo", context, inputNode,
						outputNode);
	}

	/**
	 * 查询webservice任务列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30102001(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String taskId = context.getRecord("select-key").getValue(
				"collect_task_id");
		System.out.println("taskId is " + taskId);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 修改webservice任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30102002(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		String is_code = context.getRecord("record").getValue("encrypt_mode");
		if (is_code != null && is_code.equals("01")) {
			context.getRecord("record").setValue("is_encryption", "0");// 无加密
		} else {
			context.getRecord("record").setValue("is_encryption", "1");// 需要解密
		}

		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
		// DataBus db=context.getRecord("primary-key");
		//
		// System.out.println("/法描述======"+db);
		// context.addRecord("primary-key", db);
		// XtjgCode xtjgCode = new XtjgCode();
		// xtjgCode.getCjbList(context);
		this.callService("30102004", context);

	}

	/**
	 * 增加socket任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102003(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String id = context.getRecord("record").getValue("webservice_task_id");
		String is_code = context.getRecord("record").getValue("encrypt_mode");
		if (is_code != null && is_code.equals("01")) {
			context.getRecord("record").setValue("is_encryption", "0");// 无加密
		} else {
			context.getRecord("record").setValue("is_encryption", "1");// 需要解密
		}
		if (id == null || "".equals(id)) {
			id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("webservice_task_id", id);// 数据表ID
			context.getRecord("record").setValue("web_name_space", "");// 表空间
			context.getRecord("record").setValue("method_status",
					CollectConstants.TYPE_QY);// 数据表ID
			context.getRecord("primary-key").setValue("webservice_task_id", id);// 数据表ID

			int count = -1;
			ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
			count = this.getNum(CollectConstants.TYPE_FTP_TABLE, daoTable);
			count = count + 1;
			String service_no = "";
			System.out.println("count===" + count);
			if (count < 10) {
				service_no = "SERVICE_00" + count;
			} else if (count >= 10 && count <= 99) {
				service_no = "SERVICE_0" + count;
			} else {
				service_no = "SERVICE_" + count;
			}
			context.getRecord("record").setValue("service_no", service_no);// 任务编号
			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);

			String paraId = UuidGenerator.getUUID();
			// 增加参数信息
			String sql = "insert into collect_webservice_patameter(webservice_patameter_id,webservice_task_id,patameter_type) values('"
					+ paraId
					+ "','"
					+ id
					+ "','"
					+ CollectConstants.PARAM_STYLE_00 + "')";
			System.out.println("sql========" + sql);
			table.executeUpdate(sql);

		} else {
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
		}

		this.callService("30102008", context);
	}

	/**
	 * 获取任务编号
	 * 
	 * @param tableName
	 * @param daoTable
	 * @return
	 * @throws DBException
	 */
	public int getNum(String tableName, ServiceDAO daoTable) throws DBException
	{
		int num = 0;
//		String sql = "select service_no from collect_webservice_task a,collect_task b where a.collect_task_id = b.collect_task_id "
//				+ " and b.collect_type = '05' order by service_no desc";
		
		
		String sql = "select service_no from collect_webservice_task a,collect_task b where a.collect_task_id = b.collect_task_id "
			+ " order by service_no desc";
		List list = daoTable.query(sql);
		Map map = new HashMap();
		if (list != null && list.size() > 0) {
			map = (HashMap) list.get(0);
			System.out.println(map.toString());
			String service_no = (String) map.get("SERVICE_NO");// 最大值
			if (service_no != null && !"".equals(service_no)) {
				service_no = service_no.substring(service_no.indexOf("_") + 1);
				num = Integer.parseInt(service_no);
			}
		}
		System.out.println("num===" + num);
		return num;
	}

	/**
	 * 查询webservice任务用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102004(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		String service_targets_id = context.getRecord("primary-key").getValue(
				"service_targets_id");// 服务对象ID

		if (service_targets_id != null && !"".equals(service_targets_id)) {
			context.getRecord("record").setValue("service_targets_id",
					service_targets_id);
		}

		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// 参数列表
		context = getParamValueList(context, table);
		// System.out.println(context);
	}

	public CollectWebserviceTaskContext getParamValueList(
			CollectWebserviceTaskContext context1, BaseTable table)
			throws TxnException
	{
		CollectWebserviceTaskContext context = context1;
		Recordset rs = context.getRecordset("dataItem");
		TaskInfo ti = new TaskInfo();
		String tmpStr = "";
		for (int ii = 0; ii < rs.size(); ii++) {
			DataBus db = rs.get(ii);
			String paramid = db.getValue("webservice_patameter_id");
			// String paramid = db.getValue("webservice_task_id");
			List paramValueList = ti.queyrParamValue(paramid);
			for (int jj = 0; jj < paramValueList.size(); jj++) {
				Map tmpMap = (Map) paramValueList.get(jj);
				// if ("01".equals(db.get("patameter_style").toString())) {
				tmpStr += tmpMap.get("PATAMETER_NAME").toString().toUpperCase()
						+ "="
						+ tmpMap.get("PATAMETER_VALUE").toString()
								.toUpperCase() + ",";
				// + tmpMap.get("CONNECTOR");
				// }
			}
			// tmpStr = ti.getParamValue(paramid);
			System.out.println("tmpStr = " + tmpStr);

			if (StringUtils.isNotBlank(tmpStr)) {
				// 更新参数值
				String sqlUpdate = " update collect_webservice_patameter set patameter_value = '"
						+ tmpStr
						+ "' where webservice_patameter_id = '"
						+ paramid + "'";
				System.out.println("更新参数值sql = " + sqlUpdate);
				table.executeUpdate(sqlUpdate);

				tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
				rs.get(ii).setProperty("patameter_value", tmpStr);
			}

		}
		return context;
	}

	/**
	 * 删除webservice任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30102005(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("deleteFunc", context, inputNode, outputNode);// 删除方法对应参数

		this.callService("30101004", context);
	}

	/**
	 * 删除webservice任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30102025(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("deleteFunc", context, inputNode, outputNode);// 删除方法对应参数
	}

	/**
	 * 查询webservice任务用于查看
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102006(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//System.out.println("begin:"+context);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		String webservice_task_id=context.getPrimaryKey().getValue("webservice_task_id");
		context.getSelectKey().setValue("webservice_task_id", webservice_task_id);
		Attribute.setPageRow(context, "dataItem", -1);
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// 参数列表
		//System.out.println("end:"+context);
	}

	/**
	 * 查询socket任务用于查看
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102016(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// 参数列表
	}

	/**
	 * 查询socket任务用于新增
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102007(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");// 采集任务ID
		String service_targets_id = context.getRecord("primary-key").getValue(
				"service_targets_id");// 采集任务ID
		context.getRecord(outputNode).setValue("collect_task_id",
				collect_task_id);// 采集任务ID
		context.getRecord(outputNode).setValue("service_targets_id",
				service_targets_id);// 服务对象ID
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// 参数列表
	}

	/**
	 * 查询socket任务用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102008(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String webservice_task_id = context.getRecord("record").getValue(
				"webservice_task_id");// 服务对象ID
		if (webservice_task_id != null && !"".equals(webservice_task_id)) {
			context.getRecord("primary-key").setValue("webservice_task_id",
					webservice_task_id);
		}

		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		String service_targets_id = context.getRecord("primary-key").getValue(
				"service_targets_id");// 服务对象ID

		if (service_targets_id != null && !"".equals(service_targets_id)) {
			context.getRecord("record").setValue("service_targets_id",
					service_targets_id);
		}

		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// 参数列表
		Recordset rs = context.getRecordset("dataItem");
		String jsonData = "";
		String patameter_value = "";
		String paramid = "";
		for (int ii = 0; ii < rs.size(); ii++) {
			DataBus db = new DataBus();
			db = rs.get(ii);
			paramid = db.getValue("webservice_patameter_id");
			patameter_value = getParamValue(paramid);

			jsonData += "{\"webservice_patameter_id\": \"" + paramid
					+ "\", \"webservice_task_id\": \""
					+ db.getValue("webservice_task_id")
					+ "\", \"patameter_type\": \""
					+ db.getValue("patameter_type")
					+ "\", \"patameter_name\": \""
					+ db.getValue("patameter_name")
					+ "\", \"patameter_value\": \"" + patameter_value
					+ "\", \"patameter_style\": \""
					+ db.getValue("patameter_style") + "\"}";
			jsonData += ", ";

			// 更新参数值
			String sqlUpdate = " update collect_webservice_patameter set patameter_value = '"
					+ patameter_value
					+ "' where webservice_patameter_id = '"
					+ paramid + "'";
			System.out.println("更新参数值sql = " + sqlUpdate);
			table.executeUpdate(sqlUpdate);
		}
		if (StringUtils.isNotBlank(jsonData)) {
			jsonData = jsonData.substring(0, jsonData.lastIndexOf(","));
			jsonData = "[" + jsonData + "]";
		}
		context.setProperty("jsondata", jsonData);
		// System.out.println(context);
	}

	public String getParamValue(String webservice_patameter_id)
	{

		TaskInfo ti = new TaskInfo();
		List paramValueList = ti.queyrParamValue(webservice_patameter_id);
		String tmpStr = "";
		for (int jj = 0; jj < paramValueList.size(); jj++) {
			Map tmpMap = (Map) paramValueList.get(jj);
			// if ("01".equals(db.get("patameter_style").toString())) {
			tmpStr += tmpMap.get("PATAMETER_NAME").toString().toUpperCase()
					+ "="
					+ tmpMap.get("PATAMETER_VALUE").toString().toUpperCase()
					+ ",";
		}
		System.out.println("tmpStr = " + tmpStr);
		if (StringUtils.isNotBlank(tmpStr)) {
			tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
		}
		return tmpStr;
	}

	/**
	 * 修改socket任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30102009(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		String is_code = context.getRecord("record").getValue("encrypt_mode");
		if (is_code != null && is_code.equals("01")) {
			context.getRecord("record").setValue("is_encryption", "0");// 无加密
		} else {
			context.getRecord("record").setValue("is_encryption", "1");// 需要解密
		}

		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
		// DataBus db=context.getRecord("primary-key");
		//
		// System.out.println("/法描述======"+db);
		// context.addRecord("primary-key", db);
		// XtjgCode xtjgCode = new XtjgCode();
		// xtjgCode.getCjbList(context);
		this.callService("30102008", context);

	}

	/**
	 * 查询socket任务用于查看
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102010(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// 参数列表
	}

	/**
	 * 删除socket任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30102011(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("deleteFunc", context, inputNode, outputNode);// 删除方法对应参数

		this.callService("30101052", context);
	}

	/**
	 * 增加jms任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102013(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String id = context.getRecord("record").getValue("webservice_task_id");
		String is_code = context.getRecord("record").getValue("encrypt_mode");
		if (is_code != null && is_code.equals("01")) {
			context.getRecord("record").setValue("is_encryption", "0");// 无加密
		} else {
			context.getRecord("record").setValue("is_encryption", "1");// 需要解密
		}
		if (id == null || "".equals(id)) {
			id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("webservice_task_id", id);// 数据表ID
			context.getRecord("record").setValue("web_name_space", "");// 表空间
			context.getRecord("record").setValue("method_status",
					CollectConstants.TYPE_QY);// 数据表ID
			context.getRecord("primary-key").setValue("webservice_task_id", id);// 数据表ID

			int count = -1;
			ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
			count = this.getNum(CollectConstants.TYPE_FTP_TABLE, daoTable);
			count = count + 1;
			String service_no = "";
			System.out.println("count===" + count);
			if (count < 10) {
				service_no = "SERVICE_00" + count;
			} else if (count >= 10 && count <= 99) {
				service_no = "SERVICE_0" + count;
			} else {
				service_no = "SERVICE_" + count;
			}
			context.getRecord("record").setValue("service_no", service_no);// 任务编号
			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);

			String paraId = UuidGenerator.getUUID();
			// 增加参数信息
			String sql = "insert into collect_webservice_patameter(webservice_patameter_id,webservice_task_id,patameter_type) values('"
					+ paraId
					+ "','"
					+ id
					+ "','"
					+ CollectConstants.PARAM_STYLE_00 + "')";
			System.out.println("sql========" + sql);
			table.executeUpdate(sql);

		} else {
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
		}

		this.callService("30102018", context);
	}

	/**
	 * 查询jms任务用于新增
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102017(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");// 采集任务ID
		String service_targets_id = context.getRecord("primary-key").getValue(
				"service_targets_id");// 采集任务ID
		context.getRecord(outputNode).setValue("collect_task_id",
				collect_task_id);// 采集任务ID
		context.getRecord(outputNode).setValue("service_targets_id",
				service_targets_id);// 服务对象ID
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// 参数列表
	}

	/**
	 * 查询jms任务用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102018(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String webservice_task_id = context.getRecord("record").getValue(
				"webservice_task_id");// 服务对象ID
		if (webservice_task_id != null && !"".equals(webservice_task_id)) {
			context.getRecord("primary-key").setValue("webservice_task_id",
					webservice_task_id);
		}

		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		String service_targets_id = context.getRecord("primary-key").getValue(
				"service_targets_id");// 服务对象ID

		if (service_targets_id != null && !"".equals(service_targets_id)) {
			context.getRecord("record").setValue("service_targets_id",
					service_targets_id);
		}

		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// 参数列表
		Recordset rs = context.getRecordset("dataItem");
		String jsonData = "";
		String patameter_value = "";
		String paramid = "";
		for (int ii = 0; ii < rs.size(); ii++) {
			DataBus db = new DataBus();
			db = rs.get(ii);
			paramid = db.getValue("webservice_patameter_id");
			patameter_value = getParamValue(paramid);

			jsonData += "{\"webservice_patameter_id\": \"" + paramid
					+ "\", \"webservice_task_id\": \""
					+ db.getValue("webservice_task_id")
					+ "\", \"patameter_type\": \""
					+ db.getValue("patameter_type")
					+ "\", \"patameter_name\": \""
					+ db.getValue("patameter_name")
					+ "\", \"patameter_value\": \"" + patameter_value
					+ "\", \"patameter_style\": \""
					+ db.getValue("patameter_style") + "\"}";
			jsonData += ", ";

			// 更新参数值
			String sqlUpdate = " update collect_webservice_patameter set patameter_value = '"
					+ patameter_value
					+ "' where webservice_patameter_id = '"
					+ paramid + "'";
			System.out.println("更新参数值sql = " + sqlUpdate);
			table.executeUpdate(sqlUpdate);
		}
		if (StringUtils.isNotBlank(jsonData)) {
			jsonData = jsonData.substring(0, jsonData.lastIndexOf(","));
			jsonData = "[" + jsonData + "]";
		}
		context.setProperty("jsondata", jsonData);
		// System.out.println(context);
	}

	/**
	 * 修改jms任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30102019(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		String is_code = context.getRecord("record").getValue("encrypt_mode");
		if (is_code != null && is_code.equals("01")) {
			context.getRecord("record").setValue("is_encryption", "0");// 无加密
		} else {
			context.getRecord("record").setValue("is_encryption", "1");// 需要解密
		}

		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
		// DataBus db=context.getRecord("primary-key");
		//
		// System.out.println("/法描述======"+db);
		// context.addRecord("primary-key", db);
		// XtjgCode xtjgCode = new XtjgCode();
		// xtjgCode.getCjbList(context);
		this.callService("30102018", context);

	}

	/**
	 * 查询jms任务用于查看
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30102020(CollectWebserviceTaskContext context)
			throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		table.executeFunction("getParamByFuncID", context, inputNode,
				"dataItem");// 参数列表
	}

	/**
	 * 删除jms任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30102021(CollectWebserviceTaskContext context)
			throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("deleteFunc", context, inputNode, outputNode);// 删除方法对应参数

		this.callService("30101062", context);
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
		CollectWebserviceTaskContext appContext = new CollectWebserviceTaskContext(
				context);
		invoke(method, appContext);
	}
}
