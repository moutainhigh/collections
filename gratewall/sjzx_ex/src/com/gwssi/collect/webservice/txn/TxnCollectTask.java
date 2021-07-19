package com.gwssi.collect.webservice.txn;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import oracle.jdbc.OracleConnection;
import weblogic.tools.db.sql2ejb;
import weblogic.utils.classfile.attribute_info;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.ds.ConnectFactory;
import cn.gwssi.common.dao.ds.source.DBController;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;
import cn.gwssi.common.txn.TxnService;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.collect.webservice.vo.CollectTaskContext;
import com.gwssi.collect.webservice.vo.VoCollectTask;
import com.gwssi.collect.webservice.vo.VoCollectTaskScheduling;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ConstUploadFileType;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.FileConstant;
import com.gwssi.common.constant.TaskSchedulingConstants;
import com.gwssi.common.task.SimpleTriggerRunner;
import com.gwssi.common.upload.UploadFileVO;
import com.gwssi.common.upload.UploadHelper;
import com.gwssi.common.util.AnalyCollectFile;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.JSONUtils;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.file.demo.vo.VoZwTzglJbxx;
import com.gwssi.file.manage.txn.TxnXtCcglWjys;
import com.gwssi.share.interfaces.vo.ShareInterfaceContext;
import com.gwssi.share.service.vo.ShareServiceContext;
import com.gwssi.webservice.client.FtpClient;
import com.gwssi.webservice.client.WsClient;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

public class TxnCollectTask extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods					= getAllMethod(
																	TxnCollectTask.class,
																	CollectTaskContext.class);

	// 数据表名称
	private static final String	TABLE_NAME					= "collect_task";

	// private static final String TABLE_COLLECT_DATAITEM =
	// "res_collect_dataitem";
	// 数据表名称
	private static final String	TABLE_NAME_FILE				= "collect_file_upload_task";

	// 查询列表
	private static final String	ROWSET_FUNCTION				= "select collect_task list";

	// 查询记录
	private static final String	SELECT_FUNCTION				= "select one collect_task";

	// 修改记录
	private static final String	UPDATE_FUNCTION				= "update one collect_task";
	private static final String UPDATE_FUNCTION_DATABASE           = "update one collect_task For Database";
	private static final String	UPDATE_FUNCTION_FILEUPLOAD	= "update one collect_task for fileupload";

	private static final String	UPDATE_FUNCTION_FILE		= "update one collect_file_upload_task";

	// 增加记录
	private static final String	INSERT_FUNCTION				= "insert one collect_task";

	// 增加记录
	private static final String	INSERT_FUNCTION_FILEUPLOAD	= "insert one collect_task for fileupload";

	// 增加记录
	private static final String	INSERT_FUNCTION_FILE		= "insert one collect_file_upload_task";

	// 删除记录
	private static final String	DELETE_FUNCTION				= "delete one collect_task";

	/**
	 * 构造函数
	 */
	public TxnCollectTask()
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
	 * 
	 * txn30101000(查询数据源名称是否使用)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn30101000(CollectTaskContext context) throws TxnException
	{

		String data_source_id = context.getRecord("primary-key").getValue(
				"data_source_id");// 数据源ID
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");// 任务ID
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as name_nums from collect_task t where t.is_markup='"
						+ ExConstant.IS_MARKUP_Y
						+ "' and t.data_source_id='"
						+ data_source_id + "'");
		if (collect_task_id != null && !collect_task_id.equals("")) {
			sql.append(" and t.collect_task_id!= '" + collect_task_id + "'");
		}
		System.out.println("查询数据源名称是否使用" + sql.toString());
		table.executeRowset(sql.toString(), context, outputNode);

	}

	/**
	 * 查询框
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn30109001(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		// 构造任务状态json数据
		CollectTaskContext stateContext = new CollectTaskContext();
		stateContext.getRecord(inputNode).setValue("codetype", "资源管理_归档服务状态");
		stateContext.getRecord(inputNode).setValue("column", "task_status");
		Attribute.setPageRow(stateContext, outputNode, -1);
		table.executeFunction("getInfoByType", stateContext, inputNode,
				outputNode);
		Recordset stateRs = stateContext.getRecordset("record");
		context.setValue("taskStatus", JsonDataUtil.getJsonByRecordSet(stateRs));
		// 构造采集类型json数据
		CollectTaskContext typeContext = new CollectTaskContext();
		typeContext.getRecord(inputNode).setValue("codetype", "采集任务_采集类型");
		typeContext.getRecord(inputNode).setValue("column", "collect_type");
		Attribute.setPageRow(typeContext, outputNode, -1);
		table.executeFunction("getInfoByType", typeContext, inputNode,
				outputNode);
		Recordset typeRs = typeContext.getRecordset("record");
		context.setValue("colType", JsonDataUtil.getJsonByRecordSet(typeRs));

		// 构造服务对象 json数据
		CollectTaskContext targetContext = new CollectTaskContext();
		targetContext.getRecord(inputNode).setValue("table_name",
				"res_service_targets");
		targetContext.getRecord(inputNode).setValue("col_name",
				"service_targets_id");
		targetContext.getRecord(inputNode).setValue("col_title",
				"service_targets_name");
		Attribute.setPageRow(targetContext, outputNode, -1);
		table.executeFunction("getInfoByTarget", targetContext, inputNode,
				outputNode);
		Recordset targetRs = targetContext.getRecordset("record");
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(targetContext, "资源管理_服务对象类型");
		if (!rs.isEmpty()) {
			String[] keys = new String[rs.size()];
			String[] values = new String[rs.size()];
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				keys[i] = db.getValue("codename");
				values[i] = db.getValue("codevalue");
			}
			String groupValue = JsonDataUtil.getJsonGroupByRecordSet(targetRs,
					"service_targets_type", keys, values);
			context.setValue("svrTarget", groupValue);
		}
	}

	/**
	 * 查询采集任务列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	/*
	 * public void txn30101001(CollectTaskContext context) throws TxnException {
	 * BaseTable table = TableFactory.getInstance().getTableObject(this,
	 * TABLE_NAME); // 查询记录的条件 VoCollectTaskSelectKey selectKey =
	 * context.getSelectKey( // inputNode );
	 * 
	 * String create_time = context.getRecord("select-key").getValue(
	 * "created_time"); if (StringUtils.isNotBlank(create_time)) { String[]
	 * ctime = DateUtil.getDateRegionByDatePicker(create_time, true);
	 * context.getRecord("select-key").setValue("created_time_start", ctime[0]);
	 * context.getRecord("select-key").setValue("created_time_end", ctime[1]);
	 * context.getRecord("select-key").remove("created_time"); }
	 * 
	 * table.executeFunction("queryCollectTaskList", context, inputNode,
	 * outputNode); // 查询到的记录集 VoCollectTask result[] = context.getCollectTasks(
	 * outputNode // ); }
	 */

	public void txn30101001(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// start add by dwn 2014011
		// 构造服务对象 json数据
		CollectTaskContext targetContext = new CollectTaskContext();
		targetContext.getRecord(inputNode).setValue("table_name",
				"res_service_targets");
		targetContext.getRecord(inputNode).setValue("col_name",
				"service_targets_id");
		targetContext.getRecord(inputNode).setValue("col_title",
				"service_targets_name");
		Attribute.setPageRow(targetContext, outputNode, -1);
		table.executeFunction("getInfoByTargetType", targetContext, inputNode,
				outputNode);
		Recordset targetRs = targetContext.getRecordset("record");
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(targetContext, "资源管理_服务对象类型");
		if (!rs.isEmpty()) {
			String[] keys = new String[rs.size()];
			String[] values = new String[rs.size()];
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				keys[i] = db.getValue("codename");
				values[i] = db.getValue("codevalue");
			}
			String groupValue = JsonDataUtil.getJsonGroupByRecordSet(targetRs,
					"service_targets_type", keys, values);
			context.setValue("svrTarget", groupValue);
		}

		// 构造接口状态json数据
		CollectTaskContext stateContext = new CollectTaskContext();

		stateContext.getRecord(inputNode).setValue("codetype", "资源管理_归档服务状态");
		stateContext.getRecord(inputNode).setValue("column", "task_status");
		Attribute.setPageRow(stateContext, outputNode, -1);
		table.executeFunction("getInfoByTaskStatus", stateContext, inputNode,
				outputNode);
		Recordset stateRs = stateContext.getRecordset("record");
		context.setValue("state_data", JsonDataUtil.getJsonByRecordSet(stateRs));
		//构造接口名称json数据
		CollectTaskContext typeContext = new CollectTaskContext();
		typeContext.getRecord(inputNode).setValue("codetype", "采集任务_采集类型");
		typeContext.getRecord(inputNode).setValue("column", "collect_type");
		Attribute.setPageRow(typeContext, outputNode, -1);
		table.executeFunction("getInfoByCollectType", typeContext, inputNode,
				outputNode);

		Recordset interfaceRs = typeContext.getRecordset("record");
		context.setValue("type_data",
				JsonDataUtil.getJsonByRecordSet(interfaceRs));
		//end add by dwn 2014011
		
		table.executeFunction("queryCollectTaskNewList", context, inputNode,outputNode);
		//System.out.println("01:"+context);
	}

	/**
	 * 修改采集任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101002(CollectTaskContext context) throws TxnException
	{

		// 获取页面上的会议材料和被删除的会议材料及各自的ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// 采集备案文件存放路径

		// 将附件信息传递到inputNode
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJ_FK,
				vo.getReturnId());
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJMC,
				vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		System.out.println("collect_task_id========="
				+ context.getRecord("primary-key").getValue("collect_task_id"));

		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		if (collect_task_id != null && !collect_task_id.equals("")) {
			System.out.println("collect_task_id=========" + collect_task_id);
			String data_source_id = context.getRecord("record").getValue(
					"data_source_id");// 数据源
			// 如果数据源改变 要重新获取方法
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) as name_nums from collect_task t where t.is_markup='"
					+ ExConstant.IS_MARKUP_Y + "'");
			sql.append(" and t.data_source_id='" + data_source_id + "'");
			sql.append(" and t.collect_task_id='" + collect_task_id + "'");
			ConnectFactory cf = ConnectFactory.getInstance();
			DBController dbcon = cf.getConnection();
			OracleConnection conn = (OracleConnection) dbcon.getConnection();
			ResultSet rs = null;
			String data_source_type = null;
			conn.setRemarksReporting(true);
			try {
				rs = conn.createStatement().executeQuery(sql.toString()); // 获取结果集
				System.out.println("sql=====" + sql);
				if (rs.next()) {
					String count = rs.getString("name_nums");
					System.out.println("cont====" + count);
					if (count != null && count.equals("0")) {
						String sqlDataSource = "select access_url,data_source_type from res_data_source where data_source_id = '"
								+ data_source_id + "'";

						try {
							ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
							Map tablepMap = daoTable
									.queryService(sqlDataSource);
							String access_url = (String) tablepMap
									.get("ACCESS_URL");// 访问URL
							data_source_type = (String) tablepMap
									.get("DATA_SOURCE_TYPE");// 数据源类型
							table.executeFunction("getFuncAndParam", context,
									inputNode, outputNode);// 获取方法及参数
						} catch (Exception e) {
							System.out.println("连接数据源失败!");
							table.executeFunction("deleteTaskItem", context,
									inputNode, outputNode);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dbcon.closeResultSet(rs);
				if (data_source_type != null) {
					context.getRecord("record").setValue("collect_type",
							data_source_type);// 采集类型
				}
				context.getRecord("record").setValue("last_modify_time",
						CalendarUtil.getCurrentDateTime());// 最后修改时间
				context.getRecord("record").setValue("last_modify_id",
						context.getRecord("oper-data").getValue("userID"));// 最后修改人ID
				context.getRecord("record").setValue("log_file_path", "");// 日志文件路径
				context.getRecord("record").setValue("collect_status",
						CollectConstants.COLLECT_STATUS_NOT);// 未采集
				table.executeFunction(UPDATE_FUNCTION, context, inputNode,
						outputNode);
				String task_scheduling_id = context.getRecord("record")
						.getValue("task_scheduling_id");// 计划任务ID
				if (task_scheduling_id != null
						&& !"".equals(task_scheduling_id)) {
					String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
							+ collect_task_id
							+ "' where task_scheduling_id = '"
							+ task_scheduling_id + "'";
					System.out.println("sql========" + sqlTaskSchedule);
					table.executeUpdate(sqlTaskSchedule);
				}
			}
		}
		this.callService("30101004", context);
	}

	/**
	 * 增加采集任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101003(CollectTaskContext context) throws TxnException
	{

		// 获取页面上的会议材料和被删除的会议材料及各自的ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// 采集备案文件存放路径

		// 将附件信息传递到inputNode
		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		String collect_type = context.getRecord("record").getValue(
				"collect_type");
		String data_source_id = context.getRecord("record").getValue(
				"data_source_id");// 数据源
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		if (collect_task_id == null || "".equals(collect_task_id)) {
			// 新增
			String id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("collect_task_id", id);// 数据表ID
			System.out.println("new collect_task-id="+id);
			context.getRecord("record").setValue("created_time",
					CalendarUtil.getCurrentDateTime());// 创建时间
			context.getRecord("record").setValue("creator_id",
					context.getRecord("oper-data").getValue("userID"));// 创建人ID
			context.getRecord("record").setValue("is_markup",
					ExConstant.IS_MARKUP_Y);// 引入常量 有效标记
			context.getRecord("record").setValue("task_status",
					ExConstant.SERVICE_STATE_Y);// 引入常量 启用
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// 未采集

			table.executeFunction(INSERT_FUNCTION, context, inputNode,
						outputNode);
			//System.out.println(context);	
			context.getPrimaryKey().setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id") );

		} else {
			System.out.println("edit collect_task_id="+collect_task_id);
			// 如果数据源改变 要重新获取方法
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) as name_nums from collect_task t where t.is_markup='"
					+ ExConstant.IS_MARKUP_Y + "'");
			sql.append(" and t.data_source_id='" + data_source_id + "'");
			sql.append(" and t.collect_task_id='" + collect_task_id + "'");
			ConnectFactory cf = ConnectFactory.getInstance();
			DBController dbcon = cf.getConnection();
			OracleConnection conn = (OracleConnection) dbcon.getConnection();
			ResultSet rs = null;

			conn.setRemarksReporting(true);
			try {
				rs = conn.createStatement().executeQuery(sql.toString()); // 获取结果集
				System.out.println("sql=====" + sql);
				if (rs.next()) {
					String count = rs.getString("name_nums");
					System.out.println("cont====" + count);
					if (count != null && count.equals("0")) {
						try {
							if (collect_type != null
									&& collect_type
											.equals(CollectConstants.TYPE_CJLX_WEBSERVICE)) {
								table.executeFunction("getFuncAndParam",
										context, inputNode, outputNode);// 获取方法及参数
							}
						} catch (Exception e) {
							System.out.println("连接数据源失败!");
							table.executeFunction("deleteTaskItem", context,
									inputNode, outputNode);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dbcon.closeResultSet(rs);
				context.getRecord("record").setValue("last_modify_time",
						CalendarUtil.getCurrentDateTime());// 最后修改时间
				context.getRecord("record").setValue("last_modify_id",
						context.getRecord("oper-data").getValue("userID"));// 最后修改人ID
				context.getRecord("record").setValue("log_file_path", "");// 日志文件路径
				context.getRecord("record").setValue("collect_status",
						CollectConstants.COLLECT_STATUS_NOT);// 未采集
				table.executeFunction(UPDATE_FUNCTION, context, inputNode,
						outputNode);
				/*String task_scheduling_id = context.getRecord("record")
						.getValue("task_scheduling_id");// 计划任务ID
				if (task_scheduling_id != null
						&& !"".equals(task_scheduling_id)) {
					String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
							+ collect_task_id
							+ "' where task_scheduling_id = '"
							+ task_scheduling_id + "'";
					System.out.println("sql========" + sqlTaskSchedule);
					table.executeUpdate(sqlTaskSchedule);
				}*/
			}
			this.callService("30101004", context);
		}
		
	}

	/**
	 * 查询采集任务用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101004(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		DataBus selectBus = context.getSelectKey();
		//System.out.println("context======" + context);
		System.out.println("txn30101004 collect_task_id==" + collect_task_id);
		if (collect_task_id == null || "".equals(collect_task_id)) {
			context.getRecord("primary-key").setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id"));
			collect_task_id = context.getRecord("record").getValue(
					"collect_task_id");
		}

		if (collect_task_id != null && !"".equals(collect_task_id)) {
			table.executeFunction("getCollectTask2", context, inputNode,
					outputNode);
			table.executeFunction("getFunctionByTask", context, inputNode,
					"dataItem");// 方法列表

			// 从输出节点中获取会议材料的ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// 从输出节点中获取会议材料的名称
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// 调用接口将获取的文件信息一一传回context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}

		context.addRecord("select-key", selectBus);
		//System.out.println("context1======" + context);
	}

	/**
	 * 
	 * txn30101222 跳转到采集任务配置信息页面   
	 * @param context
	 * @throws TxnException
	 * @throws DBException        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void txn30101222(CollectTaskContext context) throws TxnException,
			DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		DataBus selectBus = context.getSelectKey();
		System.out.println("context======" + context);
		System.out.println("txn30101222 collect_task_id==" + collect_task_id);
		if (collect_task_id == null || "".equals(collect_task_id)) {
			context.getRecord("primary-key").setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id"));
			collect_task_id = context.getRecord("record").getValue(
					"collect_task_id");
		}

		if (collect_task_id != null && !"".equals(collect_task_id)) {
			table.executeFunction("getCollectTask", context, inputNode,
					outputNode);

			// 从输出节点中获取会议材料的ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// 从输出节点中获取会议材料的名称
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// 调用接口将获取的文件信息一一传回context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}

		context.addRecord("select-key", selectBus);
		System.out.println("context1======" + context);
		
		
	}

	/**
	 * 删除采集任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30101005(CollectTaskContext context) throws TxnException,
			DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		// 删除任务调度
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		String task_scheduling_id = null;
		ServiceDAO daoTable = new ServiceDAOImpl();
		// 操作数据表Dao
		String sql = "select task_scheduling_id from collect_task_scheduling where collect_task_id = '"
				+ collect_task_id + "'";

		Map tablepMap = daoTable.queryService(sql);// 获取任务调度ID
		if (tablepMap != null && !tablepMap.isEmpty()) {
			task_scheduling_id = (String) tablepMap.get("TASK_SCHEDULING_ID");
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
				vo.setcollect_task_id(collect_task_id);
				SimpleTriggerRunner.removeFromScheduler(vo);
			}
		}

		table.executeFunction("deleteTaskItem", context, inputNode, outputNode);// 删除方法及参数
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);

	}

	/**
	 * 查询采集任务用于查看
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101006(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		
		table.executeFunction("getCollectTask2", context, inputNode, outputNode);
		// table.executeFunction(SELECT_FUNCTION, context, inputNode,
		// outputNode);
		Attribute.setPageRow(context, "dataItem", -1);
		table.executeFunction("getFunctionByTask", context, inputNode,
				"dataItem");// 方法列表
		
		Recordset rs = context.getRecordset("dataItem");
		if(rs!=null&&rs.size()>0){
			context.remove("dataItem");
			int i=1;
			while(rs.hasNext()){
				DataBus temp = (DataBus)rs.next();
				temp.setValue("index", i+"");
				context.addRecord("dataItem", temp);
				i++;
			}
		}

		// 从输出节点中获取会议材料的ID
		String fjids = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		System.out.println("fjids==" + fjids);
		// 从输出节点中获取会议材料的名称
		String filenames = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 调用接口将获取的文件信息一一传回context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	}

	/**
	 * 查询采集任务用于查看
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101016(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("getCollectTask", context, inputNode, outputNode);
		Attribute.setPageRow(context, "dataItem", -1);
		table.executeFunction("getFunctionByTask", context, inputNode,
				"dataItem");// 方法列表
		Recordset rs = context.getRecordset("dataItem");
		if(rs!=null&&rs.size()>0){
			context.remove("dataItem");
			int i=1;
			while(rs.hasNext()){
				DataBus temp = (DataBus)rs.next();
				temp.setValue("index", i+"");
				context.addRecord("dataItem", temp);
				i++;
			}
		}

		// 从输出节点中获取会议材料的ID
		String fjids = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		System.out.println("fjids==" + fjids);
		// 从输出节点中获取会议材料的名称
		String filenames = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 调用接口将获取的文件信息一一传回context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	}

	/**
	 * 
	 * txn30101111 查询采集任务用于新增webservice的tab页面
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn30101111(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_type = context.getRecord("primary-key").getValue(
				"collect_type");// 采集类型
		System.out.print("collect_type1111===" + collect_type);// 采集类型
	}

	/**
	 * 查询采集任务用于新增
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101007(CollectTaskContext context) throws TxnException
	{
		// 中转了一下,这里应该根据不同的采集类型跳转到不同的页面暂时没有做
		String collect_type = context.getRecord("primary-key").getValue(
				"collect_type");// 采集类型
		//System.out.print("collect_type===" + collect_type);
		context.getRecord(outputNode).setValue("collect_type", collect_type);// 采集类型,跳转到采集新增页面insert-collect_task
	}

	/**
	 * 获取数据
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101008(CollectTaskContext context) throws TxnException
	{
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");// 服务ID
		String collect_type = context.getRecord("primary-key").getValue(
				"collect_type");// 采集类型
		// lizheng
		System.out.println("collect_task_id=======" + collect_task_id);
		System.out.println("采集类型collect_type=======" + collect_type);
		if (collect_type != null
				&& collect_type
						.equals(CollectConstants.TYPE_CJLX_WEBSERVICE_NAME)) {// webservice
			WsClient wsClient = new WsClient();
			try {
				wsClient.doCollectTask(collect_task_id);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (collect_type != null
				&& collect_type.equals(CollectConstants.TYPE_CJLX_FTP_NAME)) {// ftp
			// FTP采集任务
			FtpClient ftpClient = new FtpClient();
			try {
				ftpClient.doCollectTaskFtp(collect_task_id);
			} catch (DBException e) {
				System.out.println("数据库执行错误" + e);
				e.printStackTrace();
			} catch (TxnDataException e) {
				System.out.println("获取ftp文件错误" + e);
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("文件存储错误" + e);
				e.printStackTrace();
			}

		}

	}

	/**
	 * 获取采集任务对应方法
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30101009(CollectTaskContext context) throws TxnException,
			DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		DataBus selectBus = context.getSelectKey();
		//System.out.println("context======" + context);
		System.out.println("txn30101009 collect_task_id==" + collect_task_id);
		if (collect_task_id == null || "".equals(collect_task_id)) {
			context.getRecord("primary-key").setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id"));
			collect_task_id = context.getRecord("record").getValue(
					"collect_task_id");
		}

		if (collect_task_id != null && !"".equals(collect_task_id)) {
			table.executeFunction("getCollectTask", context, inputNode,
					outputNode);
			table.executeFunction("getFunctionByTask", context, inputNode,
					"dataItem");// 方法列表

			// 从输出节点中获取会议材料的ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// 从输出节点中获取会议材料的名称
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// 调用接口将获取的文件信息一一传回context
//			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}

		context.addRecord("select-key", selectBus);
		//System.out.println("context1======" + context);
	}
	
//	public void txn30101099(CollectTaskContext context) throws TxnException,
//	DBException{
//		BaseTable table = TableFactory.getInstance().getTableObject(this,
//				TABLE_NAME);
//		table.executeFunction("getFuncTest", context, inputNode,
//				outputNode);
//	}

	/**
	 * 
	 * txn30101010(修改启用/停用状态)
	 * 
	 * @param context
	 * @throws Exception
	 * @throws TxnException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn30101010(CollectTaskContext context) throws Exception
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
		for (int i = 0; i < context.getRecordset("primary-key").size(); i++) {
			String sql = "update collect_task t set t.task_status='";
			String collect_task_id = context.getRecordset("primary-key").get(i)
					.getValue("collect_task_id");
			String task_status = context.getRecordset("primary-key").get(i)
					.getValue("task_status");

			if (task_status != null
					&& task_status.equals(ExConstant.SERVICE_STATE_Y)) {
				// sql+=
				// ExConstant.SERVICE_STATE_N+"',is_markup
				// ='"+ExConstant.IS_MARKUP_N+"' ";
				// //停用 是否变成无效？？
				sql += ExConstant.SERVICE_STATE_N + "' ";

				// 删除任务调度
				String task_scheduling_id = null;

				String sqlScheduling = "select task_scheduling_id from collect_task_scheduling where collect_task_id = '"
						+ collect_task_id + "'";

				Map tablepMap = daoTable.queryService(sqlScheduling);// 获取任务调度ID
				if (tablepMap != null && !tablepMap.isEmpty()) {
					task_scheduling_id = (String) tablepMap
							.get("TASK_SCHEDULING_ID");
					if (task_scheduling_id != null
							&& !"".equals(task_scheduling_id)) {
						VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
						vo.setcollect_task_id(collect_task_id);
						SimpleTriggerRunner.removeFromScheduler(vo);
					}
				}

				// 更新任务调度表
				sqlScheduling = "update collect_task_scheduling  set is_markup = '"
						+ ExConstant.SERVICE_STATE_N
						+ "' where collect_task_id = '" + collect_task_id + "'";
				System.out.println(sqlScheduling);
				table.executeUpdate(sqlScheduling);

			} else {
				// sql+= ExConstant.SERVICE_STATE_Y
				// +"',is_markup ='"+ExConstant.IS_MARKUP_Y+"' ";
				sql += ExConstant.SERVICE_STATE_Y + "' ";

				// 启动任务调度
				String sqlScheduling = "select * from collect_task_scheduling where collect_task_id = '"
						+ collect_task_id + "'";

				Map tablepMap = daoTable.queryService(sqlScheduling);// 获取任务调度ID
				if (tablepMap != null && !tablepMap.isEmpty()) {
					VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
					vo.setJhrw_lx((String) tablepMap.get("SCHEDULING_TYPE"));// 计划任务类型
					vo.setJhrw_rq((String) tablepMap.get("SCHEDULING_DAY"));// 计划任务日期
					vo.setJhrw_start_sj((String) tablepMap.get("START_TIME"));// 计划任务开始时间
					vo.setJhrw_end_sj((String) tablepMap.get("END_TIME"));// 计划任务结束时间
					vo.setJhrw_zt((String) tablepMap.get("SCHEDULING_WEEK"));// 计划任务周天
					vo.setJhrwzx_cs((String) tablepMap.get("SCHEDULING_COUNT"));// 计划任务执行次数
					vo.setJhrwzx_jg((String) tablepMap.get("INTERVAL_TIME"));// 计划任务执行
					// 间隔
					vo
							.setJob_class_name(TaskSchedulingConstants.JOB_CLASS_NAME);// 触发调用的类名
					vo.settask_scheduling_id((String) tablepMap
							.get("COLLECT_TASK_ID"));
					vo.setcollect_task_id(collect_task_id);
					SimpleTriggerRunner.addToScheduler(vo);

					// 更新任务调度表
					sqlScheduling = "update collect_task_scheduling  set is_markup = '"
							+ ExConstant.SERVICE_STATE_Y
							+ "' where collect_task_id = '"
							+ collect_task_id
							+ "'";
					System.out.println(sqlScheduling);
					table.executeUpdate(sqlScheduling);
				}
			}
			sql += " where t.collect_task_id='" + collect_task_id + "'";
			System.out.println(sql);
			table.executeUpdate(sql);
		}

		// table.executeFunction( SELECT_FUNCTION, context, inputNode,
		// outputNode );
	}

	/**
	 * 查询采集任务用于新增ftp
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101011(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("getFtpByTask", context, inputNode, "dataItem");// ftp列表
		context.getRecord("record").setValue("flag", "add");
	}

	/**
	 * 增加采集任务信息ftp 第一步保存跳转至第二步
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101101(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String data_source_type = CollectConstants.TYPE_CJLX_FTP;		
		
		String collect_task_id = context.getRecord("select-key").getValue(
				"collect_task_id");
		String data_source_id = context.getRecord("record").getValue(
				"data_source_id");
		boolean saveNew=false;
		if(StringUtils.isBlank(collect_task_id)){
			//没有主键ID,是新增
			saveNew=true;
		}
		//System.out.println("first save="+context);
		// 获取页面上的会议材料和被删除的会议材料及各自的ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);
		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// 采集备案文件存放路径

		// 将附件信息传递到inputNode
		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		//System.out.println("first file="+context);
		if(saveNew){
			System.out.println("txn30101101----新增first-step");
			String id = UuidGenerator.getUUID();
			
			context.getRecord("record").setValue("collect_task_id", id);// 数据表ID
			context.getRecord("record").setValue("created_time",
					CalendarUtil.getCurrentDateTime());// 创建时间
			context.getRecord("record").setValue("creator_id",
					context.getRecord("oper-data").getValue("userID"));// 创建人ID
			context.getRecord("record").setValue("is_markup",
					ExConstant.IS_MARKUP_Y);// 引入常量 有效标记
			context.getRecord("record").setValue("task_status",
					ExConstant.SERVICE_STATE_Y);// 引入常量 启用
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// 未采集
			/*
			try {
				table.executeFunction("getFtpFile", context, inputNode,
						outputNode);// 获取FTP文件 并入库
			} catch (Exception e) {
				System.out.println("连接ftp数据源失败!!");
			}*/
			context.getRecord("record").setValue("collect_type",
					data_source_type);// 采集类型
			context.getRecord("fileinfo").setValue("ftp_task_id",
					"");// 文件ID默认设置为空
			//System.out.println("一保存="+context);
			
			
			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
			//获取第二步页面的文件信息
			context.getSelectKey().setValue("collect_task_id", id);
			context.getSelectKey().setValue("service_targets_id", 
					context.getRecord("record").getValue("service_targets_id"));
			context.getSelectKey().setValue("task_name", 
					context.getRecord("record").getValue("task_name"));
			//System.out.println(context);
			table.executeFunction("getFileInfoTree", context, inputNode,
					"treeinfo");
			
		}else{
			System.out.println("txn30101101----修改first-step");
			context.getRecord("record").setValue("collect_task_id", collect_task_id);
			context.getRecord("record").setValue("last_modify_time",
					CalendarUtil.getCurrentDateTime());// 最后修改时间
			context.getRecord("record").setValue("last_modify_id",
					context.getRecord("oper-data").getValue("userID"));// 最后修改人ID
			//System.out.println("修改="+context);
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
			
			//获取第二步页面的文件信息
			table.executeFunction("getFileInfoTree", context, inputNode,
					"treeinfo");
			//System.out.println("txn30101101--"+context);
		}
			
		context.getSelectKey().setValue("task_name",
				context.getRecord("record").getValue("task_name"));
		context.getSelectKey().setValue("service_targets_id",
				context.getRecord("record").getValue("service_targets_id"));
		
		
	}
	/**
	 * 增加采集任务信息ftp 第二步保存跳转至第三步
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101102(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		DataBus recordDB =context.getSelectKey();
		String collect_task_id =recordDB.getValue(
				"collect_task_id");
		
		if(StringUtils.isNotBlank(collect_task_id)){
			System.out.println("txn30101102----save second-step");
			//初始化第三步页面
			
			table.executeFunction("getFTPTaskInfo", context, inputNode,
					"record");
			//System.out.println("getFTPTaskInfo="+context);
			//DataBus collect_task = context.getRecord("collect_task");
			//context.getRecord("collect_task").clear();
			//context.clear();
			
			//Attribute.setPageRow(context, outputNode, -1);
			table.executeFunction("getFileInfoTree", context, inputNode,
					"dataItem");
			//System.out.println("getFileInfoTree="+context);
			// 从输出节点中获取文件ID
			String fjids = context.getRecord("record").getValue(
					VoCollectTask.ITEM_FJ_FK);
			//System.out.println("fjids==" + fjids);
			// 从输出节点中获取文件的名称
			String filenames = context.getRecord("record").getValue(
					VoCollectTask.ITEM_FJMC);
			if(fjids!=null && filenames!=null ){
				// 调用接口将获取的文件信息一一传回context
				UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
			}
			//System.out.println("getFileInfo="+context);
			
			//System.out.println("end="+context);
			
		}
		
	}
	/**
	 * 增加采集任务信息ftp 第三步保存
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101103(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("select-key").getValue(
				"collect_task_id");
		if(StringUtils.isNotBlank(collect_task_id)){
			System.out.println("txn30101103----save third-step");
			String task_scheduling_id = context.getRecord("record")
					.getValue("task_scheduling_id");// 计划任务ID
			if (task_scheduling_id != null
					&& !"".equals(task_scheduling_id)) {
				String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
						+ collect_task_id
						+ "' where task_scheduling_id = '"
						+ task_scheduling_id + "'";
				//System.out.println("sql========" + sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
			}
		}
		//System.out.println("txn30101103:"+context);
	}
	/**
	 * 增加采集任务信息ftp 返回第一步
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101104(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("select-key").getValue(
				"collect_task_id");
		String service_targets_id = context.getRecord("select-key").getValue(
				"service_targets_id");
		String flag = context.getRecord("select-key").getValue(
				"flag");
		if(StringUtils.isNotBlank(collect_task_id)){
			System.out.println("txn30101104----turn back to first-step");
			table.executeFunction("getFTPTaskInfo", context, inputNode,
					"record");
			//System.out.println("getFTPStep3="+context);
			//DataBus collect_task = context.getRecord("collect_task");
			
			//context.clear();
			//collect_task.put("flag", flag);
			//collect_task.put("service_targets_id", service_targets_id);
			//context.addRecord("record", collect_task);
			// 从输出节点中获取文件ID
			String fjids = context.getRecord("record").getValue(
					VoCollectTask.ITEM_FJ_FK);
			//System.out.println("fjids==" + fjids);
			// 从输出节点中获取文件的名称
			String filenames = context.getRecord("record").getValue(
					VoCollectTask.ITEM_FJMC);

			// 调用接口将获取的文件信息一一传回context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
			//System.out.println("getFileInfoTree="+context);
		}
		
	}
	/**
	 * 增加采集任务信息ftp 返回第二步
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101105(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("select-key").getValue(
				"collect_task_id");
		if(StringUtils.isNotBlank(collect_task_id)){
			System.out.println("txn30101103----turn back to second-step");
			table.executeFunction("getFileInfoTree", context, inputNode,
					"treeinfo");
		}
		
	}
	
	/**
	 * 增加采集任务信息ftp 第二步保存文件信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException 
	 */
	public void txn30101107(CollectTaskContext context) throws TxnException, DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("select-key").getValue(
				"collect_task_id");
		String ftp_task_id = context.getRecord("select-key").getValue(
				"ftp_task_id");   
		//System.out.println("txn30101107----"+context);
		DataBus rd= context.getRecord("record");
		if(StringUtils.isNotBlank(ftp_task_id)){//修改文件信息
			System.out.println("txn30101107---修改="+ftp_task_id);
			String sql = "UPDATE collect_ftp_task SET ";
			boolean first=true;
			if(StringUtils.isNotBlank(rd.getValue("file_name_en"))){
				sql+=" file_name_en='"+rd.getValue("file_name_en")+"'";
				first=false;
			}
			if(StringUtils.isNotBlank(rd.getValue("file_name_cn"))){
				if(first){
					sql+=" file_name_cn='"+rd.getValue("file_name_cn")+"'";
					first=false;
				}else{
					sql+=" ,file_name_cn='"+rd.getValue("file_name_cn")+"'";
				}
			}
			if(StringUtils.isNotBlank(rd.getValue("collect_mode"))){
				if(first){
					sql+=" collect_mode='"+rd.getValue("collect_mode")+"'";
					first=false;
				}else{
					sql+=" ,collect_mode='"+rd.getValue("collect_mode")+"'";
				}
			}
			if(StringUtils.isNotBlank(rd.getValue("collect_table"))){
				if(first){
					sql+=" collect_table='"+rd.getValue("collect_table")+"'";
					first=false;
				}else{
					sql+=" ,collect_table='"+rd.getValue("collect_table")+"'";
				}
			}
			if(StringUtils.isNotBlank(rd.getValue("file_description"))){
				if(first){
					sql+=" file_description='"+rd.getValue("file_description")+"'";
					first=false;
				}else{
					sql+=" ,file_description='"+rd.getValue("file_description")+"'";
				}
			}
			
			if(StringUtils.isNotBlank(rd.getValue("file_title_type"))){
				if(first){
					sql+=" file_title_type='"+rd.getValue("file_title_type")+"'";
					first=false;
				}else{
					sql+=" ,file_title_type='"+rd.getValue("file_title_type")+"'";
				}
			}
			if(StringUtils.isNotBlank(rd.getValue("file_sepeator"))){
				if(first){
					sql+=" file_sepeator='"+rd.getValue("file_sepeator")+"'";
					first=false;
				}else{
					sql+=" ,file_sepeator='"+rd.getValue("file_sepeator")+"'";
				}
			}
			if(StringUtils.isNotBlank(rd.getValue("month"))){
				if(first){
					sql+=" month='"+rd.getValue("month")+"'";
					first=false;
				}else{
					sql+=" ,month='"+rd.getValue("month")+"'";
				}
			}
			if(StringUtils.isNotBlank(rd.getValue("day_month"))){
				if(first){
					sql+=" day_month='"+rd.getValue("day_month")+"'";
					first=false;
				}else{
					sql+=" ,day_month='"+rd.getValue("day_month")+"'";
				}
			}
			if(StringUtils.isNotBlank(rd.getValue("name_type"))){
				if(first){
					sql+=" name_type='"+rd.getValue("name_type")+"'";
					first=false;
				}else{
					sql+=" ,name_type='"+rd.getValue("name_type")+"'";
				}
			}
			if(StringUtils.isNotBlank(rd.getValue("day_num"))){
				if(first){
					sql+=" day_num='"+rd.getValue("day_num")+"'";
					first=false;
				}else{
					sql+=" ,day_num='"+rd.getValue("day_num")+"'";
				}
			}
			if(!first){//有非空的字段才执行
				sql+="WHERE ftp_task_id='"+ftp_task_id+"'";
				//System.out.println(sql);
				table.executeUpdate(sql);
			}
			
		}else{//将新增文件信息写入数据库表
			ftp_task_id = UuidGenerator.getUUID();
			System.out.println("新增ftp_task_id="+ftp_task_id);
			
			TxnCollectWebserviceTask tasktmp= new TxnCollectWebserviceTask();
			int count = -1;
			ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
			count = tasktmp.getNum(CollectConstants.TYPE_FTP_TABLE, daoTable);
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
			
			
			//context.getRecord("record").setValue("ftp_task_id", ftp_task_id);
			String sql="insert into collect_ftp_task "
					+"(FTP_TASK_ID, COLLECT_TASK_ID,SERVICE_NO, FILE_NAME_EN,FILE_NAME_CN,COLLECT_MODE,COLLECT_TABLE,FILE_STATUS,FILE_DESCRIPTION,FJ_PATH,FILE_SEPEATOR,FILE_TITLE_TYPE,MONTH,DAY_MONTH,NAME_TYPE,DAY_NUM)"
					+"values ('"+ftp_task_id+"', '"+collect_task_id+"','"
					+service_no+"','"+rd.getValue("file_name_en")+"',"
					+"'"+rd.getValue("file_name_cn")+"',"
					+"'"+rd.getValue("collect_mode")+"',"
					+"'"+rd.getValue("collect_table")+"',1,"
					+"'"+rd.getValue("file_description")+"','',"
					+"'"+rd.getValue("file_sepeator")+"',"
					+"'"+rd.getValue("file_title_type")+"',"
					+"'"+rd.getValue("month")+"',"
					+"'"+rd.getValue("day_month")+"',"
					+"'"+rd.getValue("name_type")+"',"
					+"'"+rd.getValue("day_num")+"'"
					+")";
			System.out.println(sql);
			table.executeUpdate(sql);
		}
		
	}
	
	/**
	 * 增加采集任务信息ftp 第二步 删除文件信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101108(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String ftp_task_id = context.getRecord("select-key").getValue(
				"ftp_task_id");
		//System.out.println("108---"+context);
		if(StringUtils.isNotBlank(ftp_task_id)){
			System.out.println("txn30101108----del file="+ftp_task_id);
			String sql= "delete from collect_ftp_task where ftp_task_id='"
					+ftp_task_id+"'";
			table.executeUpdate(sql);	
			
		}
		//重新获取第二步页面的文件信息
		table.executeFunction("getFileInfoTree", context, inputNode,
				"treeinfo");
		
		
	}
	
	/**
	 * 增加采集任务信息ftp 第二步 新增、修改文件信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101109(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		
		
		context.addRecord("select-key",context.getRecord("record") );
		String ftp_task_id = context.getRecord("select-key").getValue(
				"ftp_task_id");
		
		
		if(StringUtils.isNotBlank(ftp_task_id)){//修改文件信息
			//获取对应的文件信息
			
			table.executeFunction("getFileInfo", context, inputNode,
					"record");
			context.getRecord("record").setValue("task_name", 
					context.getSelectKey().getValue("task_name"));
			context.getRecord("record").setValue("service_targets_id", 
					context.getSelectKey().getValue("service_targets_id"));
			context.getRecord("record").setValue("flag", 
					context.getSelectKey().getValue("flag"));
			//System.out.println("edit109--"+context);
			/*context.getRecord("record").setValue("task_name", record.getValue("task_name"));
			context.getRecord("record").setValue("flag", record.getValue("flag"));
			context.getRecord("record").setValue("service_targets_id", record.getValue("service_targets_id"));
			*/
		}else{
			//新增
			//System.out.println("add109--"+context);
		}
		
		//System.out.println("end109--"+context);
	}
	/**
	 * 刷新采集任务信息ftp第二步
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101110(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//System.out.println("刷新---"+context);
		String collect_task_id = context.getRecord("select-key").getValue(
				"collect_task_id");
		if(StringUtils.isNotBlank(collect_task_id)){//修改文件信息
			//获取对应的文件信息
			table.executeFunction("getFileInfoTree", context, inputNode,
					"treeinfo");
			
		}
		
	}
	/**
	 * 增加采集任务信息ftp
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101012(CollectTaskContext context) throws TxnException
	{	
		System.out.println("12="+context);

		// 获取页面上的会议材料和被删除的会议材料及各自的ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// 采集备案文件存放路径

		// UploadFileVO fileVO1 = new UploadFileVO();
		// //fileVO1.setRecordName("record:fjmc");
		// fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);
		//
		// vo = UploadHelper.saveFile(context, fileVO1,
		// ConstUploadFileType.ZWGL);
		//
		// 将附件信息传递到inputNode
		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		
		System.out.println("12file="+context);
		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		String data_source_id = context.getRecord("record").getValue(
				"data_source_id");// 数据源
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Map tablepMap = null;
		String data_source_type = CollectConstants.TYPE_CJLX_FTP;
		if (collect_task_id == null || "".equals(collect_task_id)) {

			String id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("collect_task_id", id);// 数据表ID

			context.getRecord("record").setValue("created_time",
					CalendarUtil.getCurrentDateTime());// 创建时间
			context.getRecord("record").setValue("creator_id",
					context.getRecord("oper-data").getValue("userID"));// 创建人ID
			context.getRecord("record").setValue("is_markup",
					ExConstant.IS_MARKUP_Y);// 引入常量 有效标记
			context.getRecord("record").setValue("task_status",
					ExConstant.SERVICE_STATE_Y);// 引入常量 启用
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// 未采集

			try {
				table.executeFunction("getFtpFile", context, inputNode,
						outputNode);// 获取FTP文件 并入库
			} catch (Exception e) {
				System.out.println("连接ftp数据源失败!!");
			}
			context.getRecord("record").setValue("collect_type",
					data_source_type);// 采集类型
			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// 计划任务ID
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
						+ id
						+ "' where task_scheduling_id = '"
						+ task_scheduling_id + "'";
				System.out.println("sql========" + sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
			}
		}// 新增

		else {

			// 如果数据源改变 要重新获取方法
			StringBuffer sql = new StringBuffer();
			sql
					.append("select count(*) as name_nums from collect_task t where t.is_markup='"
							+ ExConstant.IS_MARKUP_Y + "'");
			sql.append(" and t.data_source_id='" + data_source_id + "'");
			sql.append(" and t.collect_task_id='" + collect_task_id + "'");
			ConnectFactory cf = ConnectFactory.getInstance();
			DBController dbcon = cf.getConnection();
			OracleConnection conn = (OracleConnection) dbcon.getConnection();
			ResultSet rs = null;

			conn.setRemarksReporting(true);
			try {
				rs = conn.createStatement().executeQuery(sql.toString()); // 获取结果集
				System.out.println("sql=====" + sql);
				if (rs.next()) {
					String count = rs.getString("name_nums");
					System.out.println("cont====" + count);
					if (count != null && count.equals("0")) {// 更改了数据源
						table.executeFunction("getFtpFile", context, inputNode,
								outputNode);// 获取FTP文件 并入库
					}
				}
			} catch (Exception e) {
				System.out.println("连接ftp数据源失败!!");
				table.executeFunction("deleteTaskItem", context, inputNode,
						outputNode);// 删除ftp文件表

			} finally {
				dbcon.closeResultSet(rs);
				if (data_source_type != null) {
					context.getRecord("record").setValue("collect_type",
							data_source_type);// 采集类型
				}
				context.getRecord("record").setValue("last_modify_time",
						CalendarUtil.getCurrentDateTime());// 最后修改时间
				context.getRecord("record").setValue("last_modify_id",
						context.getRecord("oper-data").getValue("userID"));// 最后修改人ID
				table.executeFunction(UPDATE_FUNCTION, context, inputNode,
						outputNode);
				String task_scheduling_id = context.getRecord("record")
						.getValue("task_scheduling_id");// 计划任务ID
				if (task_scheduling_id != null
						&& !"".equals(task_scheduling_id)) {
					String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
							+ collect_task_id
							+ "' where task_scheduling_id = '"
							+ task_scheduling_id + "'";
					System.out.println("sql========" + sqlTaskSchedule);
					table.executeUpdate(sqlTaskSchedule);
				}
			}
		}
		this.callService("30101013", context);
	}
	
	/**
	 * 查询采集任务用于修改 ftp
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101013(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		if(StringUtils.isNotBlank(collect_task_id)){
			context.getSelectKey().setValue("collect_task_id", collect_task_id);
			
			//System.out.println("修改FTP="+context);
			//System.out.println(context);
			table.executeFunction("getFTPTaskInfo", context, inputNode,
					"record");
			
			// 从输出节点中获取文件ID
			String fjids = context.getRecord("record").getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// 从输出节点中获取文件的名称
			String filenames = context.getRecord("record").getValue(
					VoCollectTask.ITEM_FJMC);

			if(fjids!=null && filenames!=null ){
				// 调用接口将获取的文件信息一一传回context
				UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
			}
		}
		context.getRecord("select-key").setValue("flag", "edit");
		/*System.out.println("修改FTP="+context);
		DataBus selectBus= context.getSelectKey();
		System.out.println("collect_task_id==" + collect_task_id);
		if (collect_task_id == null || "".equals(collect_task_id)) {
			context.getRecord("primary-key").setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id"));
			collect_task_id = context.getRecord("record").getValue(
					"collect_task_id");
		}
		System.out.println("collect_task_id3333333333====" + collect_task_id);
		if (collect_task_id != null && !"".equals(collect_task_id)) {
			table.executeFunction("getCollectTask", context, inputNode,
					outputNode);
			table.executeFunction("getFtpByTask", context, inputNode,
					"dataItem");// 方法列表

			// 从输出节点中获取会议材料的ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// 从输出节点中获取会议材料的名称
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// 调用接口将获取的文件信息一一传回context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		context.addRecord("select-key", selectBus);*/

	}

	/**
	 * 修改采集任务信息 ftp
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101014(CollectTaskContext context) throws TxnException
	{

		// 获取页面上的会议材料和被删除的会议材料及各自的ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// 采集备案文件存放路径

		// 将附件信息传递到inputNode
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJ_FK,
				vo.getReturnId());
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJMC,
				vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		context.getRecord("record").setValue("collect_task_id",
				context.getRecord("primary-key").getValue("collect_task_id"));// 任务ID

		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		String data_source_id = context.getRecord("record").getValue(
				"data_source_id");// 数据源
		// 如果数据源改变 要重新获取方法
		StringBuffer sql = new StringBuffer();
		sql
				.append("select count(*) as name_nums from collect_task t where t.is_markup='"
						+ ExConstant.IS_MARKUP_Y + "'");
		sql.append(" and t.data_source_id='" + data_source_id + "'");
		sql.append(" and t.collect_task_id='" + collect_task_id + "'");
		ConnectFactory cf = ConnectFactory.getInstance();
		DBController dbcon = cf.getConnection();
		OracleConnection conn = (OracleConnection) dbcon.getConnection();
		ResultSet rs = null;
		conn.setRemarksReporting(true);
		try {
			rs = conn.createStatement().executeQuery(sql.toString()); // 获取结果集
			System.out.println("sql=====" + sql);
			if (rs.next()) {
				String count = rs.getString("name_nums");
				System.out.println("cont====" + count);
				if (count != null && count.equals("0")) {

					try {
						table.executeFunction("getFtpFile", context, inputNode,
								outputNode);// 获取ftp文件并入库
					} catch (Exception e) {
						System.out.println("连接数据源失败!");
						table.executeFunction("deleteTaskItem", context,
								inputNode, outputNode);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbcon.closeResultSet(rs);

			context.getRecord("record").setValue("last_modify_time",
					CalendarUtil.getCurrentDateTime());// 最后修改时间
			context.getRecord("record").setValue("last_modify_id",
					context.getRecord("oper-data").getValue("userID"));// 最后修改人ID
			context.getRecord("record").setValue("log_file_path", "");// 日志文件路径
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// 日志文件路径
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// 计划任务ID
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
						+ collect_task_id
						+ "' where task_scheduling_id = '"
						+ task_scheduling_id + "'";
				System.out.println("sql========" + sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
			}
		}
		this.callService("30101013", context);
	}

	/**
	 * 查询采集任务用于查看 ftp
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101015(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		context.getSelectKey().setValue("collect_task_id", 
				context.getRecord("primary-key").getValue("collect_task_id"));
		table.executeFunction("getFTPTaskInfo", context, inputNode,
				"record");
		Attribute.setPageRow(context, "dataItem", -1);
		table.executeFunction("getFileInfoTree", context, inputNode,
				"dataItem");
		//table.executeFunction("getCollectTask", context, inputNode, outputNode);
		//table.executeFunction("getFtpByTask", context, inputNode, "dataItem");// 方法列表
		
		Recordset rs = context.getRecordset("dataItem");
		if(rs!=null&&rs.size()>0){
			context.remove("dataItem");
			int i=1;
			while(rs.hasNext()){
				DataBus temp = (DataBus)rs.next();
				temp.setValue("index", i+"");
				context.addRecord("dataItem", temp);
				i++;
			}
		}

		// 从输出节点中获取会议材料的ID
		String fjids = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		System.out.println("fjids==" + fjids);
		// 从输出节点中获取会议材料的名称
		String filenames = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 调用接口将获取的文件信息一一传回context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		//System.out.println("15---"+context);
	}

	/**
	 * 获取采集任务对应文件 ftp
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30101017(CollectTaskContext context) throws TxnException,
			DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("getFtpFile", context, inputNode, outputNode);// 获取方法及参数
		this.callService("30101013", context);
	}

	/**
	 * 增加采集任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101021(CollectTaskContext context) throws TxnException
	{
		String COLLECT_TASK_ID = UuidGenerator.getUUID();// 采集任务id
		String FILE_UPLOAD_TASK_ID = UuidGenerator.getUUID();// 文件上传任务ID
		UploadFileVO fileVO1 = new UploadFileVO();
		fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_SINGLE);

		UploadFileVO vo = UploadHelper.saveFile(context, fileVO1,
				ConstUploadFileType.FILE_UPLOAD, COLLECT_TASK_ID);

		// 将附件信息传递到inputNode
		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		BaseTable table_file = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME_FILE);

		context.getRecord("record")
				.setValue("collect_task_id", COLLECT_TASK_ID);
		context.getRecord("record").setValue("file_upload_task_id",
				FILE_UPLOAD_TASK_ID);

		context.getRecord("record").setValue("created_time",
				CalendarUtil.getCurrentDateTime());
		context.getRecord("record").setValue("is_markup",
				ExConstant.IS_MARKUP_Y);// 引入常量
		context.getRecord("record").setValue("creator_id",
				context.getRecord("oper-data").getValue("userID"));
		context.getRecord("record").setValue("task_status",
				ExConstant.SERVICE_STATE_Y);// 引入常量 启用
		context.getRecord("record").setValue("collect_status",
				CollectConstants.COLLECT_STATUS_NOT);// 未采集

		// 采集结果文件需要的字段信息
		String logFile = "";
		String startTime = CalendarUtil.getCurrentDateTime();
		String user = context.getRecord("oper-data").getValue("username");
		String return_codes = "";
		StringBuffer colTable_ColumnInfo = new StringBuffer("");

		if (vo.getReturnId() != null && !"".equals(vo.getReturnId())) {
			table.executeFunction("queryFilePath", context, inputNode,
					"collectfilepath");
			table.executeFunction("queryCollectTableInfo", context, inputNode,
					"tableInfo");
			table.executeFunction("querySerTarName", context, inputNode,
					"serName");

			String filepath = context.getRecord("collectfilepath").getValue(
					"file_path").replace("\\", "/");
			String cclbbh_pk = context.getRecord("collectfilepath").getValue(
					"cclbbh_pk");
			TxnXtCcglWjys wjys = new TxnXtCcglWjys();
			filepath = wjys.getPathByCCLB(cclbbh_pk).replace("\\", "/") + "/"
					+ filepath;

			String collect_mode = context.getRecord("record").getValue(
					"collect_mode");
			String serName = context.getRecord("serName").getValue(
					"service_targets_name");

			log.info("filepath=" + filepath);
			String table_name = "";
			HashMap cols = new HashMap();
			Recordset rs = context.getRecordset("tableInfo");
			DataBus colInfo = null;
			for (int i = 0; i < rs.size(); i++) {
				colInfo = rs.get(i);
				if (colInfo.getValue("dataitem_name_en") != null
						&& !colInfo.getValue("dataitem_name_en").equals("")) {
					cols.put(i, colInfo.getValue("dataitem_name_en")
							.toUpperCase());
					String tmpStr = "";
					String colName = "";
					if (colInfo.getValue("dataitem_name_cn") != null
							&& !colInfo.getValue("dataitem_name_cn").equals("")) {
						colName = colInfo.getValue("dataitem_name_cn");
					}
					if (i != (rs.size() - 1)) {
						tmpStr = colInfo.getValue("dataitem_name_en") + "("
								+ colName + "),";
					} else {
						tmpStr = colInfo.getValue("dataitem_name_en") + "("
								+ colName + ")";
					}
					colTable_ColumnInfo.append(tmpStr);
				}
				if (table_name.equals("")) {
					table_name = colInfo.getValue("table_name_en");
				}
			}

			/* 组装日志信息map */
			HashMap logInfo = new HashMap();

			String collect_joumal_id = context.getRecord("record").getValue(
					"collect_joumal_id");
			if (collect_joumal_id != null && !"".equals(collect_joumal_id)) {
				logInfo.put("COLLECT_JOUMAL_ID", collect_joumal_id);
			}

			// logInfo.put("COLLECT_TASK_ID", COLLECT_TASK_ID);
			// logInfo.put("TASK_ID", FILE_UPLOAD_TASK_ID);
			if (context.getRecord("record").getValue("collect_task_id") != null) {
				logInfo.put("COLLECT_TASK_ID", context.getRecord("record")
						.getValue("collect_task_id"));
			} else {
				logInfo.put("COLLECT_TASK_ID", "");
			}
			if (context.getRecord("record").getValue("file_upload_task_id") != null) {
				logInfo.put("TASK_ID", context.getRecord("record").getValue(
						"file_upload_task_id"));
			} else {
				logInfo.put("TASK_ID", "");
			}
			if (context.getRecord("record").getValue("task_name") != null) {
				logInfo.put("TASK_NAME", context.getRecord("record").getValue(
						"task_name"));
			} else {
				logInfo.put("TASK_NAME", "");
			}

			if (context.getRecord("record").getValue("service_targets_id") != null) {
				logInfo.put("SERVICE_TARGETS_ID", context.getRecord("record")
						.getValue("service_targets_id"));
			} else {
				logInfo.put("SERVICE_TARGETS_ID", "");
			}
			logInfo.put("SERVICE_TARGETS_NAME", serName);
			if (context.getRecord("record").getValue("collect_table") != null) {
				logInfo.put("COLLECT_TABLE", context.getRecord("record")
						.getValue("collect_table"));
			} else {
				logInfo.put("COLLECT_TABLE", "");
			}
			logInfo.put("COLLECT_TABLE_NAME", table_name);

			if (context.getRecord("record").getValue("collect_mode") != null) {
				logInfo.put("COLLECT_MODE", context.getRecord("record")
						.getValue("collect_mode"));
			} else {
				logInfo.put("COLLECT_MODE", "");
			}
			if (context.getRecord("record").getValue("collect_type") != null) {
				logInfo.put("COLLECT_TYPE", context.getRecord("record")
						.getValue("collect_type"));
			} else {
				logInfo.put("COLLECT_TYPE", "");
			}

			AnalyCollectFile ae = new AnalyCollectFile();
			String fileType = filepath.substring(filepath.lastIndexOf("."));
			logFile = filepath.substring(0, filepath.lastIndexOf("."))
					+ "_"
					+ startTime.replace("-", "").replace(":", "").replace(" ",
							"") + "_CollectFileImportResult.txt";
			log.info("日志文件路径:" + logFile);

			if (".xls".equals(fileType) || ".xlsx".equals(fileType)) {
				String checkResultPath = filepath.substring(0, filepath
						.lastIndexOf("."))
						+ "_xls_" + "checkResult.txt";
				// ae.analyExcelData(logInfo,filepath, table_name, cols,
				// collect_mode, checkResultPath,);
				return_codes = ae.analyExcelData2(logInfo, filepath,
						table_name, cols, collect_mode, checkResultPath, user,
						logFile, colTable_ColumnInfo);
			} else if (".txt".equals(fileType)) {
				String checkResultPath = filepath.substring(0, filepath
						.lastIndexOf("."))
						+ "_txt_" + "checkResult.txt";
				// ae.analyTxtData(logInfo,filepath, table_name, cols,
				// collect_mode, "", checkResultPath);
				return_codes = ae.analyTxtData2(logInfo, filepath, table_name,
						cols, collect_mode, "~", checkResultPath, user,
						logFile, colTable_ColumnInfo);

			} else if (".mdb".equals(fileType)) {

			} else {
				System.out.println("上传的采集文件类型不正确!");
			}

		}
		if (return_codes != null && !return_codes.equals("")) {
			context.getRecord("record")
					.setValue("collect_status", return_codes);// 采集成功
		}
		context.getRecord("record").setValue("log_file_path", logFile);
		table.executeFunction(INSERT_FUNCTION_FILEUPLOAD, context, inputNode,
				outputNode);// 新增任务
		table_file.executeFunction(INSERT_FUNCTION_FILE, context, inputNode,
				outputNode);

	}

	/**
	 * 查询采集任务用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101022(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("getFileUploadInfo", context, inputNode,
				outputNode);

		// 从输出节点中获取会议材料的ID
		String fjids = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		System.out.println("fjids==" + fjids);
		// 从输出节点中获取会议材料的名称
		String filenames = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 调用接口将获取的文件信息一一传回context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");

	}

	/**
	 * 保存采集任务修改信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101023(CollectTaskContext context) throws TxnException
	{
		Long start = System.currentTimeMillis();
		// System.out.println("start0:"+start);

		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		UploadFileVO fileVO1 = new UploadFileVO();
		fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_SINGLE);

		UploadFileVO vo = UploadHelper.saveFile(context, fileVO1,
				ConstUploadFileType.FILE_UPLOAD, collect_task_id);

		// 将附件信息传递到inputNode
		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		BaseTable table_file = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME_FILE);
		// BaseTable table_dataitem =
		// TableFactory.getInstance().getTableObject(this,
		// TABLE_COLLECT_DATAITEM);

		context.getRecord("record").setValue("last_modify_time",
				CalendarUtil.getCurrentDateTime());
		context.getRecord("record").setValue("last_modify_id",
				context.getRecord("oper-data").getValue("userID"));

		// 采集结果文件需要的字段信息
		String log_file_path = context.getRecord("record").getValue(
				"log_file_path");

		String logFile = "";
		String startTime = CalendarUtil.getCurrentDateTime();
		String return_codes = "";
		String user = context.getRecord("oper-data").getValue("username");
		StringBuffer colTable_ColumnInfo = new StringBuffer("");

		if (vo.getReturnId() != null && !"".equals(vo.getReturnId())) {
			table.executeFunction("queryFilePath", context, inputNode,
					"collectfilepath");
			table.executeFunction("queryCollectTableInfo", context, inputNode,
					"tableInfo");
			table.executeFunction("querySerTarName", context, inputNode,
					"serName");

			String filepath = context.getRecord("collectfilepath").getValue(
					"file_path").replace("\\", "/");
			String cclbbh_pk = context.getRecord("collectfilepath").getValue(
					"cclbbh_pk");
			TxnXtCcglWjys wjys = new TxnXtCcglWjys();
			filepath = wjys.getPathByCCLB(cclbbh_pk).replace("\\", "/") + "/"
					+ filepath;
			String collect_mode = context.getRecord("record").getValue(
					"collect_mode");
			String serName = context.getRecord("serName").getValue(
					"service_targets_name");

			// System.out.println("filepath="+filepath);
			String table_name = "";
			HashMap<Integer, String> cols = new HashMap<Integer, String>();
			Recordset rs = context.getRecordset("tableInfo");
			DataBus colInfo = null;
			for (int i = 0; i < rs.size(); i++) {
				colInfo = rs.get(i);
				if (colInfo.getValue("dataitem_name_en") != null
						&& !colInfo.getValue("dataitem_name_en").equals("")) {
					cols.put(i, colInfo.getValue("dataitem_name_en")
							.toUpperCase());
					String tmpStr = "";
					String colName = "";
					if (colInfo.getValue("dataitem_name_cn") != null
							&& !colInfo.getValue("dataitem_name_cn").equals("")) {
						colName = colInfo.getValue("dataitem_name_cn");
					}
					if (i != (rs.size() - 1)) {
						tmpStr = colInfo.getValue("dataitem_name_en") + "("
								+ colName + "),";
					} else {
						tmpStr = colInfo.getValue("dataitem_name_en") + "("
								+ colName + ")";
					}
					colTable_ColumnInfo.append(tmpStr);
				}
				if (table_name.equals("")) {
					table_name = colInfo.getValue("table_name_en");
				}
			}

			/* 组装日志信息map */
			HashMap<String, String> logInfo = new HashMap<String, String>();

			String collect_joumal_id = context.getRecord("record").getValue(
					"collect_joumal_id");
			if (collect_joumal_id != null && !"".equals(collect_joumal_id)) {
				logInfo.put("COLLECT_JOUMAL_ID", collect_joumal_id);
			}
			// logInfo.put("COLLECT_TASK_ID", COLLECT_TASK_ID);
			// logInfo.put("TASK_ID", FILE_UPLOAD_TASK_ID);
			if (context.getRecord("record").getValue("collect_task_id") != null) {
				logInfo.put("COLLECT_TASK_ID", context.getRecord("record")
						.getValue("collect_task_id"));
			} else {
				logInfo.put("COLLECT_TASK_ID", "");
			}
			if (context.getRecord("record").getValue("file_upload_task_id") != null) {
				logInfo.put("TASK_ID", context.getRecord("record").getValue(
						"file_upload_task_id"));
			} else {
				logInfo.put("TASK_ID", "");
			}
			if (context.getRecord("record").getValue("task_name") != null) {
				logInfo.put("TASK_NAME", context.getRecord("record").getValue(
						"task_name"));
			} else {
				logInfo.put("TASK_NAME", "");
			}

			if (context.getRecord("record").getValue("service_targets_id") != null) {
				logInfo.put("SERVICE_TARGETS_ID", context.getRecord("record")
						.getValue("service_targets_id"));
			} else {
				logInfo.put("SERVICE_TARGETS_ID", "");
			}
			logInfo.put("SERVICE_TARGETS_NAME", serName);
			if (context.getRecord("record").getValue("collect_table") != null) {
				logInfo.put("COLLECT_TABLE", context.getRecord("record")
						.getValue("collect_table"));
			} else {
				logInfo.put("COLLECT_TABLE", "");
			}

			logInfo.put("COLLECT_TABLE_NAME", table_name);

			if (context.getRecord("record").getValue("collect_mode") != null) {
				logInfo.put("COLLECT_MODE", context.getRecord("record")
						.getValue("collect_mode"));
			} else {
				logInfo.put("COLLECT_MODE", "");
			}
			if (context.getRecord("record").getValue("collect_type") != null) {
				logInfo.put("COLLECT_TYPE", context.getRecord("record")
						.getValue("collect_type"));
			} else {
				logInfo.put("COLLECT_TYPE", "");
			}

			AnalyCollectFile ae = new AnalyCollectFile();
			String fileType = filepath.substring(filepath.lastIndexOf("."));
			if (log_file_path == null || "".equals(log_file_path)) {
				logFile = filepath.substring(0, filepath.lastIndexOf("."))
						+ "_"
						+ startTime.replace("-", "").replace(":", "").replace(
								" ", "") + "_CollectFileImportResult.txt";
				context.getRecord("record").setValue("log_file_path", logFile);
			} else {
				logFile = log_file_path;
			}

			if (".xls".equals(fileType) || ".xlsx".equals(fileType)) {
				String checkResultPath = filepath.substring(0, filepath
						.lastIndexOf("."))
						+ "_xls_" + "checkResult.txt";
				// ae.analyExcelData(logInfo,filepath, table_name, cols,
				// collect_mode, checkResultPath);
				return_codes = ae.analyExcelData2(logInfo, filepath,
						table_name, cols, collect_mode, checkResultPath, user,
						logFile, colTable_ColumnInfo);
			} else if (".txt".equals(fileType)) {
				String checkResultPath = filepath.substring(0, filepath
						.lastIndexOf("."))
						+ "_txt_" + "checkResult.txt";
				// ae.analyTxtData(logInfo,filepath, table_name, cols,
				// collect_mode, "", checkResultPath);
				return_codes = ae.analyTxtData2(logInfo, filepath, table_name,
						cols, collect_mode, "~", checkResultPath, user,
						logFile, colTable_ColumnInfo);

			} else if (".mdb".equals(fileType)) {

			} else {
				System.out.println("上传的采集文件类型不正确!");
			}

		}
		if (return_codes != null && !return_codes.equals("")) {
			context.getRecord("record")
					.setValue("collect_status", return_codes);
		}

		context.getRecord("record").setValue("log_file_path", logFile);
		table.executeFunction(UPDATE_FUNCTION_FILEUPLOAD, context, inputNode,
				outputNode);
		table_file.executeFunction(UPDATE_FUNCTION_FILE, context, inputNode,
				outputNode);

	}

	/**
	 * 删除采集任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101024(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("deleteFileUpload", context, inputNode,
				outputNode);// 删除文件上传采集任务
		// table.executeFunction( DELETE_FUNCTION, context, inputNode,
		// outputNode );

	}

	/**
	 * 查询采集任务用于查看
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101025(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("getFileUploadInfo", context, inputNode,
				outputNode);

		// 从输出节点中获取会议材料的ID
		String fjids = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);

		// 从输出节点中获取会议材料的名称
		String filenames = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 调用接口将获取的文件信息一一传回context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	}

	/**
	 * 查询采集任务结果信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101026(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("getCollectFileResult", context, inputNode,
				"result");
		// System.out.println("26666666666:context::"+context);

	}

	/**
	 * 查询采集任务用于新增
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101030(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("getDBByTask", context, inputNode, "dataItem");// 方法列表
	}
	/**
	 * 查询采集任务用于新增(上一步 start add by dwn 20140306)
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn301010300(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		//table.executeFunction("getDBByTask", context, inputNode, "dataItem");// 方法列表
		String collect_task_id = context.getRecord("primary-key").getValue("collect_task_id");//采集任务ID
		
		if(collect_task_id==null || "".equals(collect_task_id)){
			
		}else{
			table.executeFunction("getCollectTask", context, inputNode,
					outputNode);
			table
					.executeFunction("getDBByTask", context, inputNode,
							"dataItem");// 方法列表

			// 从输出节点中获取会议材料的ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// 从输出节点中获取会议材料的名称
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// 调用接口将获取的文件信息一一传回context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		
		
	}

	/**
	 * 增加数据库采集任务
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101031(CollectTaskContext context) throws TxnException
	{
		// BaseTable table = TableFactory.getInstance().getTableObject( this,
		// TABLE_NAME );

		// 获取页面上的会议材料和被删除的会议材料及各自的ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// 采集备案文件存放路径

		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		String data_source_id = context.getRecord("record").getValue(
				"data_source_id");// 数据源
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Map tablepMap = null;
		String data_source_type = null;
		if (collect_task_id == null || "".equals(collect_task_id)) {

			String id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("collect_task_id", id);// 数据表ID

			context.getRecord("record").setValue("created_time",
					CalendarUtil.getCurrentDateTime());// 创建时间
			context.getRecord("record").setValue("creator_id",
					context.getRecord("oper-data").getValue("userID"));// 创建人ID
			context.getRecord("record").setValue("is_markup",
					ExConstant.IS_MARKUP_Y);// 引入常量 有效标记
			context.getRecord("record").setValue("task_status",
					ExConstant.SERVICE_STATE_Y);// 引入常量 启用
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// 未采集

			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// 计划任务ID
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
						+ id
						+ "' where task_scheduling_id = '"
						+ task_scheduling_id + "'";
				System.out.println("sql========" + sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
			}
		}
	}
	
	/**
	 * 
	 * txn301010311(这里用一句话描述这个方法的作用)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void txn301010311(CollectTaskContext context) throws TxnException
	{
		// BaseTable table = TableFactory.getInstance().getTableObject( this,
		// TABLE_NAME );

		// 获取页面上的会议材料和被删除的会议材料及各自的ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// 采集备案文件存放路径

		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		String data_source_id = context.getRecord("record").getValue(
				"data_source_id");// 数据源
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Map tablepMap = null;
		String data_source_type = null;
		if (collect_task_id == null || "".equals(collect_task_id)) {

			String id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("collect_task_id", id);// 数据表ID
			context.getRecord("primary-key").setValue("collect_task_id", id);// 数据表ID

			context.getRecord("record").setValue("created_time",
					CalendarUtil.getCurrentDateTime());// 创建时间
			context.getRecord("record").setValue("creator_id",
					context.getRecord("oper-data").getValue("userID"));// 创建人ID
			context.getRecord("record").setValue("is_markup",
					ExConstant.IS_MARKUP_Y);// 引入常量 有效标记
			context.getRecord("record").setValue("task_status",
					ExConstant.SERVICE_STATE_Y);// 引入常量 启用
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// 未采集

			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// 计划任务ID
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
						+ id
						+ "' where task_scheduling_id = '"
						+ task_scheduling_id + "'";
				System.out.println("sql========" + sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
			}
		}else{
			context.getRecord("primary-key").setValue("collect_task_id", collect_task_id);// 数据表ID
			table.executeFunction(UPDATE_FUNCTION_DATABASE, context, inputNode,
					outputNode);
		}
		
		
	}

	/**
	 * 修改数据库采集任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101032(CollectTaskContext context) throws TxnException
	{

		// 获取页面上的会议材料和被删除的会议材料及各自的ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// 采集备案文件存放路径

		// 将附件信息传递到inputNode
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJ_FK,
				vo.getReturnId());
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJMC,
				vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		System.out.println("collect_task_id 222========="
				+ context.getRecord("primary-key").getValue("collect_task_id"));
		// context.getRecord("record").setValue("collect_task_id",context.getRecord("primary-key").getValue("collect_task_id"));//任务ID

		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		if (collect_task_id != null && !collect_task_id.equals("")) {
			/*
			 * System.out.println("collect_task_id========="+collect_task_id);
			 * String data_source_id =
			 * context.getRecord("record").getValue("data_source_id");//数据源
			 * //如果数据源改变 要重新获取方法 StringBuffer sql= new StringBuffer();
			 * sql.append( "select count(*) as name_nums from collect_task t
			 * where t.is_markup='" +ExConstant.IS_MARKUP_Y+"'"); sql.append("
			 * and t.data_source_id='"+data_source_id+"'"); sql.append(" and
			 * t.collect_task_id='"+collect_task_id+"'"); ConnectFactory cf =
			 * ConnectFactory.getInstance(); DBController dbcon =
			 * cf.getConnection(); OracleConnection conn = (OracleConnection)
			 * dbcon.getConnection(); ResultSet rs = null; String
			 * data_source_type=null; conn.setRemarksReporting(true); try{ rs =
			 * conn.createStatement().executeQuery(sql.toString()); // 获取结果集
			 * System.out.println("sql====="+sql); if (rs.next()) { String
			 * count= rs.getString("name_nums");
			 * System.out.println("cont===="+count);
			 * if(count!=null&&count.equals("0")){ String sqlDataSource= "select
			 * access_url,data_source_type from res_data_source where
			 * data_source_id = '" +data_source_id+"'";
			 * 
			 * try { ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao Map
			 * tablepMap = daoTable.queryService(sqlDataSource); String
			 * access_url=(String) tablepMap.get("ACCESS_URL");//访问URL
			 * data_source_type=(String)
			 * tablepMap.get("DATA_SOURCE_TYPE");//数据源类型 // ServiceInfo
			 * serviceInfo = new ServiceInfo(); //
			 * System.out.println("access_url===="+access_url); //
			 * serviceInfo.setWsdllocation(access_url); // ComponentBuilder
			 * builder = new ComponentBuilder(); // serviceInfo =
			 * builder.buildserviceinformation(serviceInfo);
			 * table.executeFunction("getFuncAndParam", context,
			 * inputNode,outputNode);//获取方法及参数 }catch(Exception e){
			 * System.out.println("连接数据源失败!");
			 * table.executeFunction("deleteTaskItem", context, inputNode,
			 * outputNode); } } } }catch(Exception e){ e.printStackTrace();
			 * }finally { dbcon.closeResultSet(rs); if(data_source_type!=null){
			 * context
			 * .getRecord("record").setValue("collect_type",data_source_type);//
			 * 采集类型 }
			 */
			context.getRecord("record").setValue("last_modify_time",
					CalendarUtil.getCurrentDateTime());// 最后修改时间
			context.getRecord("record").setValue("last_modify_id",
					context.getRecord("oper-data").getValue("userID"));// 最后修改人ID
			context.getRecord("record").setValue("log_file_path", "");// 日志文件路径
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// 未采集
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// 计划任务ID
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
						+ collect_task_id
						+ "' where task_scheduling_id = '"
						+ task_scheduling_id + "'";
				System.out.println("sql========" + sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
			}
			/* } */
		}
		this.callService("30101034", context);
	}

	/**
	 * 查询数据库采集任务用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101034(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		DataBus selectBus = context.getSelectKey();
		if (collect_task_id == null || "".equals(collect_task_id)) {
			context.getRecord("primary-key").setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id"));
			collect_task_id = context.getRecord("record").getValue(
					"collect_task_id");
		}

		if (collect_task_id != null && !"".equals(collect_task_id)) {
			table.executeFunction("getCollectTask", context, inputNode,
					outputNode);
			Attribute.setPageRow(context, "dataItem", -1);
			table
					.executeFunction("getDBByTask", context, inputNode,
							"dataItem");// 方法列表
			Recordset rs = context.getRecordset("dataItem");
			if(rs!=null&&rs.size()>0){
				context.remove("dataItem");
				int i=1;
				while(rs.hasNext()){
					DataBus temp = (DataBus)rs.next();
					temp.setValue("index", i+"");
					context.addRecord("dataItem", temp);
					i++;
				}
			}

			// 从输出节点中获取会议材料的ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// 从输出节点中获取会议材料的名称
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// 调用接口将获取的文件信息一一传回context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		context.addRecord("select-key", selectBus);
	}
	
	/**
	 * 
	 * txn301010344(数据库采集第三部交易码  add by dwn 20140306)    
	   
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void txn301010344(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		DataBus selectBus = context.getSelectKey();
		System.out.println("context======" + context);
		System.out.println("collect_task_id   333=" + collect_task_id);
		if (collect_task_id == null || "".equals(collect_task_id)) {
			context.getRecord("primary-key").setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id"));
			collect_task_id = context.getRecord("record").getValue(
					"collect_task_id");
		}

		if (collect_task_id != null && !"".equals(collect_task_id)) {
			table.executeFunction("getCollectTask", context, inputNode,
					outputNode);
			table
					.executeFunction("getDBByTask", context, inputNode,
							"dataItem");// 方法列表

			// 从输出节点中获取会议材料的ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// 从输出节点中获取会议材料的名称
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// 调用接口将获取的文件信息一一传回context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		context.addRecord("select-key", selectBus);
	}

	/**
	 * 删除采集任务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30101036(CollectTaskContext context) throws TxnException,
			DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("deleteTaskDatabaseItem", context, inputNode,
				outputNode);// 删除方法及参数
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);

		// 删除任务调度
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		String task_scheduling_id = null;
		ServiceDAO daoTable = new ServiceDAOImpl();
		; // 操作数据表Dao
		String sql = "select task_scheduling_id from collect_task_scheduling where collect_task_id = '"
				+ collect_task_id + "'";

		Map tablepMap = daoTable.queryService(sql);// 获取任务调度ID
		if (tablepMap != null && !tablepMap.isEmpty()) {
			task_scheduling_id = (String) tablepMap.get("TASK_SCHEDULING_ID");
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
				vo.setcollect_task_id(collect_task_id);
				SimpleTriggerRunner.removeFromScheduler(vo);
			}
		}

	}
	
	/**
	 * 
	 * txn30101040(修改数据库采集任务第二部)    
	  
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void txn30101040(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		
		
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// 采集备案文件存放路径

		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		//String collect_task_id = context.getRecord("record").getValue("collect_task_id");
		
		
		if (collect_task_id == null || "".equals(collect_task_id)) {
			context.getRecord("primary-key").setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id"));
			collect_task_id = context.getRecord("record").getValue(
					"collect_task_id");
		}

		if (collect_task_id != null && !"".equals(collect_task_id)) {
			//context.getRecord("primary-key").setValue("collect_task_id", collect_task_id);// 数据表ID
			table.executeFunction(UPDATE_FUNCTION_DATABASE, context, inputNode,outputNode);
			
			table.executeFunction("getCollectTask", context, inputNode,outputNode);
			table.executeFunction("getDBByTask", context, inputNode,"dataItem");// 方法列表

			
		}
		//context.addRecord("select-key", selectBus);
	}
	
	/**
	 * 
	 * txn30101041(插入数据库采集任务第二部)    
	  
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void txn30101041(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		
		
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// 采集备案文件存放路径

		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		
		if (collect_task_id == null || "".equals(collect_task_id)) {

			String id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("collect_task_id", id);// 数据表ID
			context.getRecord("primary-key").setValue("collect_task_id", id);// 数据表ID

			context.getRecord("record").setValue("created_time",
					CalendarUtil.getCurrentDateTime());// 创建时间
			context.getRecord("record").setValue("creator_id",
					context.getRecord("oper-data").getValue("userID"));// 创建人ID
			context.getRecord("record").setValue("is_markup",
					ExConstant.IS_MARKUP_Y);// 引入常量 有效标记
			context.getRecord("record").setValue("task_status",
					ExConstant.SERVICE_STATE_Y);// 引入常量 启用
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// 未采集
			
			context.getRecord("primary-key").setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id"));
			
			

			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
			
			table.executeFunction("getCollectTask", context, inputNode,outputNode);
			table.executeFunction("getDBByTask", context, inputNode,"dataItem");// 方法列表
			
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// 计划任务ID
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
						+ id
						+ "' where task_scheduling_id = '"
						+ task_scheduling_id + "'";
				System.out.println("sql========" + sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
			}
		}else{
			context.getRecord("primary-key").setValue("collect_task_id", collect_task_id);// 数据表ID
			table.executeFunction(UPDATE_FUNCTION_DATABASE, context, inputNode,outputNode);
			table.executeFunction("getCollectTask", context, inputNode,outputNode);
			table.executeFunction("getDBByTask", context, inputNode,"dataItem");// 方法列表
		}

	}
	
	/**
	 * 
	 * txn30101042(这里用一句话描述这个方法的作用)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void txn30101042(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		
		System.out.println("context======" + context);
		System.out.println("collect_task_id   333=" + collect_task_id);
		if (collect_task_id == null || "".equals(collect_task_id)) {
			context.getRecord("primary-key").setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id"));
			collect_task_id = context.getRecord("record").getValue(
					"collect_task_id");
		}

		if (collect_task_id != null && !"".equals(collect_task_id)) {
			table.executeFunction("getCollectTask", context, inputNode,
					outputNode);
			table
					.executeFunction("getDBByTask", context, inputNode,
							"dataItem");// 方法列表

			// 从输出节点中获取会议材料的ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// 从输出节点中获取会议材料的名称
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// 调用接口将获取的文件信息一一传回context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		//context.addRecord("select-key", selectBus);
	}

	/**
	 * 查询采集任务用于新增 socket
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101051(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_type = context.getRecord("primary-key").getValue(
				"collect_type");// 采集类型
		System.out.print("collect_type===" + collect_type);
		context.getRecord(outputNode).setValue("collect_type", collect_type);// 采集类型
		table.executeFunction("getFunctionByTask", context, inputNode,
				"dataItem");// 方法列表
	}

	/**
	 * 查询采集任务用于修改 socket
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101052(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		DataBus selectBus = context.getSelectKey();
		System.out.println("context======" + context);
		System.out.println("collect_task_id  4==" + collect_task_id);
		if (collect_task_id == null || "".equals(collect_task_id)) {
			context.getRecord("primary-key").setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id"));
			collect_task_id = context.getRecord("record").getValue(
					"collect_task_id");
		}

		if (collect_task_id != null && !"".equals(collect_task_id)) {
			table.executeFunction("getCollectTask", context, inputNode,
					outputNode);
			table.executeFunction("getFunctionByTask", context, inputNode,
					"dataItem");// 方法列表

			// 从输出节点中获取会议材料的ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// 从输出节点中获取会议材料的名称
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// 调用接口将获取的文件信息一一传回context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		context.addRecord("select-key", selectBus);
	}

	/**
	 * 修改采集任务信息 socket
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101053(CollectTaskContext context) throws TxnException
	{

		// 获取页面上的会议材料和被删除的会议材料及各自的ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// 采集备案文件存放路径

		// 将附件信息传递到inputNode
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJ_FK,
				vo.getReturnId());
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJMC,
				vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		System.out.println("collect_task_id  5========="
				+ context.getRecord("primary-key").getValue("collect_task_id"));
		// context.getRecord("record").setValue("collect_task_id",context.getRecord("primary-key").getValue("collect_task_id"));//任务ID

		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		if (collect_task_id != null && !collect_task_id.equals("")) {
			System.out.println("collect_task_id 6=========" + collect_task_id);
			/**
			 * String data_source_id =
			 * context.getRecord("record").getValue("data_source_id");//数据源
			 * //如果数据源改变 要重新获取方法 StringBuffer sql= new StringBuffer();
			 * sql.append( "select count(*) as name_nums from collect_task t
			 * where t.is_markup='" +ExConstant.IS_MARKUP_Y+"'"); sql.append("
			 * and t.data_source_id='"+data_source_id+"'"); sql.append(" and
			 * t.collect_task_id='"+collect_task_id+"'"); ConnectFactory cf =
			 * ConnectFactory.getInstance(); DBController dbcon =
			 * cf.getConnection(); OracleConnection conn = (OracleConnection)
			 * dbcon.getConnection(); ResultSet rs = null; String
			 * data_source_type=null; conn.setRemarksReporting(true); try{ rs =
			 * conn.createStatement().executeQuery(sql.toString()); // 获取结果集
			 * System.out.println("sql====="+sql); if (rs.next()) { String
			 * count= rs.getString("name_nums");
			 * System.out.println("cont===="+count);
			 * if(count!=null&&count.equals("0")){ String sqlDataSource= "select
			 * access_url,data_source_type from res_data_source where
			 * data_source_id = '" +data_source_id+"'";
			 * 
			 * try { ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao Map
			 * tablepMap = daoTable.queryService(sqlDataSource); String
			 * access_url=(String) tablepMap.get("ACCESS_URL");//访问URL
			 * data_source_type=(String)
			 * tablepMap.get("DATA_SOURCE_TYPE");//数据源类型 // ServiceInfo
			 * serviceInfo = new ServiceInfo(); //
			 * System.out.println("access_url===="+access_url); //
			 * serviceInfo.setWsdllocation(access_url); // ComponentBuilder
			 * builder = new ComponentBuilder(); // serviceInfo =
			 * builder.buildserviceinformation(serviceInfo);
			 * table.executeFunction("getFuncAndParam", context,
			 * inputNode,outputNode);//获取方法及参数 }catch(Exception e){
			 * System.out.println("连接数据源失败!");
			 * table.executeFunction("deleteTaskItem", context, inputNode,
			 * outputNode); } } } }catch(Exception e){ e.printStackTrace();
			 * }finally { dbcon.closeResultSet(rs); if(data_source_type!=null){
			 * context
			 * .getRecord("record").setValue("collect_type",data_source_type);//
			 * 采集类型 }
			 */
			context.getRecord("record").setValue("last_modify_time",
					CalendarUtil.getCurrentDateTime());// 最后修改时间
			context.getRecord("record").setValue("last_modify_id",
					context.getRecord("oper-data").getValue("userID"));// 最后修改人ID
			context.getRecord("record").setValue("log_file_path", "");// 日志文件路径
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// 未采集
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// 计划任务ID
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
						+ collect_task_id
						+ "' where task_scheduling_id = '"
						+ task_scheduling_id + "'";
				System.out.println("sql========" + sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
			}
		}
		// }
		this.callService("30101052", context);
	}

	/**
	 * 查询采集任务用于新增 socket
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101061(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_type = context.getRecord("primary-key").getValue(
				"collect_type");// 采集类型
		System.out.print("collect_type===" + collect_type);
		context.getRecord(outputNode).setValue("collect_type", collect_type);// 采集类型
		table.executeFunction("getFunctionByTask", context, inputNode,
				"dataItem");// 方法列表
	}

	/**
	 * 查询采集任务用于修改 socket
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101062(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		DataBus selectBus = context.getSelectKey();

		System.out.println("context======" + context);
		System.out.println("collect_task_id  7==" + collect_task_id);
		if (collect_task_id == null || "".equals(collect_task_id)) {
			context.getRecord("primary-key").setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id"));
			collect_task_id = context.getRecord("record").getValue(
					"collect_task_id");
		}

		if (collect_task_id != null && !"".equals(collect_task_id)) {
			table.executeFunction("getCollectTask", context, inputNode,
					outputNode);
			table.executeFunction("getFunctionByTask", context, inputNode,
					"dataItem");// 方法列表

			// 从输出节点中获取会议材料的ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// 从输出节点中获取会议材料的名称
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// 调用接口将获取的文件信息一一传回context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		context.addRecord("select-key", selectBus);

	}

	/**
	 * 修改采集任务信息 socket
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101063(CollectTaskContext context) throws TxnException
	{

		// 获取页面上的会议材料和被删除的会议材料及各自的ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// 生成一个UploadFileVO对象，保存政务管理类型的多附件
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// 页面保存的被删除附件Id值
		fileVO.setDeleteName(delNAMEs);// 页面保存的被删除附件name值
		fileVO.setOriginId(hyclid);// 更新附件前业务数据库表附件id字段存储的值
		fileVO.setOriginName(hycl);// 更新附件前业务数据库表附件name字段存储的值
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// 多附件
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// 采集备案文件存放路径

		// 将附件信息传递到inputNode
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJ_FK,
				vo.getReturnId());
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJMC,
				vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		System.out.println("collect_task_id========="
				+ context.getRecord("primary-key").getValue("collect_task_id"));
		// context.getRecord("record").setValue("collect_task_id",context.getRecord("primary-key").getValue("collect_task_id"));//任务ID

		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		if (collect_task_id != null && !collect_task_id.equals("")) {
			System.out.println("collect_task_id=========" + collect_task_id);

			context.getRecord("record").setValue("last_modify_time",
					CalendarUtil.getCurrentDateTime());// 最后修改时间
			context.getRecord("record").setValue("last_modify_id",
					context.getRecord("oper-data").getValue("userID"));// 最后修改人ID
			context.getRecord("record").setValue("log_file_path", "");// 日志文件路径
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// 未采集
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// 计划任务ID
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
						+ collect_task_id
						+ "' where task_scheduling_id = '"
						+ task_scheduling_id + "'";
				System.out.println("sql========" + sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
			}
		}
		// }
		this.callService("30101062", context);
	}

	public void txn30101064(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryCollectTaskNewList", context, inputNode,
				outputNode);
	}

	/**
	 * 查看采集任务信息用于首页服务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101018(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("getCollectTask", context, inputNode, outputNode);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getCollectTaskTableAndDataitems", context,
				inputNode, "dataitems");
		System.out.println(context);
	}

	/**
	 * 查看采集任务信息用于首页服务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn30101065(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		DataBus databus = context.getRecord("select-key");
		String task_name = databus.getValue("task_name");
		table.executeFunction("getCollectTaskInfo", context, inputNode,
				outputNode);
		Recordset collect_rs = context.getRecordset(outputNode);
		if (!collect_rs.isEmpty()) {
			String ctime_str = "";
			String cnum_str = "";
			String ccount_str = "";
			List clist_info = new ArrayList();
			Map call_map = new HashMap();
			for (int i = 0; i < collect_rs.size(); i++) {
				DataBus db = collect_rs.get(i);
				ctime_str += ctime_str == "" ? "\"" + db.getValue("log_date")
						+ "\"" : ",\"" + db.getValue("log_date") + "\"";
				cnum_str += cnum_str == "" ? "\"" + db.getValue("sum_num")
						+ "\"" : ",\"" + db.getValue("sum_num") + "\"";
				ccount_str += ccount_str == "" ? "\""
						+ db.getValue("sum_count") + "\"" : ",\""
						+ db.getValue("sum_count") + "\"";
				String[] info = new String[] {
						db.getValue("sum_num").toString(),
						db.getValue("sum_count").toString(),
						db.getValue("avg_time").toString(),
						db.getValue("error_num").toString() };
				call_map.put(db.getValue("log_date"), info);
			}
			clist_info.add(call_map);
			context.getRecord(outputNode).clear();
			context.getRecord(outputNode).setValue("ctime_str", ctime_str);
			context.getRecord(outputNode).setValue("cstat_num_str", cnum_str);
			context.getRecord(outputNode).setValue("cstat_count_str",
					ccount_str);
			context.getRecord(outputNode).setValue("task_name", task_name);
			context.getRecord(outputNode).setValue("cstat_info_str",
					JSONUtils.toJSONString(clist_info));

		}
		// System.out.println(context);
	}
	
	
	/**
	 * 手动执行采集任务
	 * @param context
	 * @throws Exception 
	 */
	public void txn30182103(CollectTaskContext context) throws Exception
	{
		
		DataBus record=context.getRecord("record");
		//System.out.println("手动执行："+context);
		String collect_type=record.getValue("collect_type");		
		
		if("02".equals(collect_type)){//FTP任务
			
			FtpClient ftpClient =new FtpClient();
			DataBus rsBus=ftpClient.doManualFTP(record);
			context.addRecord("rsBack", rsBus);
		}else {
			DataBus rsBus=new DataBus();
			rsBus.put("count", "0");
			rsBus.put("errorMsg", "暂不支持这种类型的采集任务");
			context.addRecord("rsBack", rsBus);
		}
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
		CollectTaskContext appContext = new CollectTaskContext(context);
		invoke(method, appContext);
	}
}
