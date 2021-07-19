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
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods					= getAllMethod(
																	TxnCollectTask.class,
																	CollectTaskContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME					= "collect_task";

	// private static final String TABLE_COLLECT_DATAITEM =
	// "res_collect_dataitem";
	// ���ݱ�����
	private static final String	TABLE_NAME_FILE				= "collect_file_upload_task";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION				= "select collect_task list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION				= "select one collect_task";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION				= "update one collect_task";
	private static final String UPDATE_FUNCTION_DATABASE           = "update one collect_task For Database";
	private static final String	UPDATE_FUNCTION_FILEUPLOAD	= "update one collect_task for fileupload";

	private static final String	UPDATE_FUNCTION_FILE		= "update one collect_file_upload_task";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION				= "insert one collect_task";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION_FILEUPLOAD	= "insert one collect_task for fileupload";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION_FILE		= "insert one collect_file_upload_task";

	// ɾ����¼
	private static final String	DELETE_FUNCTION				= "delete one collect_task";

	/**
	 * ���캯��
	 */
	public TxnCollectTask()
	{

	}

	/**
	 * ��ʼ������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * 
	 * txn30101000(��ѯ����Դ�����Ƿ�ʹ��)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn30101000(CollectTaskContext context) throws TxnException
	{

		String data_source_id = context.getRecord("primary-key").getValue(
				"data_source_id");// ����ԴID
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");// ����ID
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
		System.out.println("��ѯ����Դ�����Ƿ�ʹ��" + sql.toString());
		table.executeRowset(sql.toString(), context, outputNode);

	}

	/**
	 * ��ѯ��
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn30109001(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		// ��������״̬json����
		CollectTaskContext stateContext = new CollectTaskContext();
		stateContext.getRecord(inputNode).setValue("codetype", "��Դ����_�鵵����״̬");
		stateContext.getRecord(inputNode).setValue("column", "task_status");
		Attribute.setPageRow(stateContext, outputNode, -1);
		table.executeFunction("getInfoByType", stateContext, inputNode,
				outputNode);
		Recordset stateRs = stateContext.getRecordset("record");
		context.setValue("taskStatus", JsonDataUtil.getJsonByRecordSet(stateRs));
		// ����ɼ�����json����
		CollectTaskContext typeContext = new CollectTaskContext();
		typeContext.getRecord(inputNode).setValue("codetype", "�ɼ�����_�ɼ�����");
		typeContext.getRecord(inputNode).setValue("column", "collect_type");
		Attribute.setPageRow(typeContext, outputNode, -1);
		table.executeFunction("getInfoByType", typeContext, inputNode,
				outputNode);
		Recordset typeRs = typeContext.getRecordset("record");
		context.setValue("colType", JsonDataUtil.getJsonByRecordSet(typeRs));

		// ���������� json����
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
		Recordset rs = codeMap.lookup(targetContext, "��Դ����_�����������");
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
	 * ��ѯ�ɼ������б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	/*
	 * public void txn30101001(CollectTaskContext context) throws TxnException {
	 * BaseTable table = TableFactory.getInstance().getTableObject(this,
	 * TABLE_NAME); // ��ѯ��¼������ VoCollectTaskSelectKey selectKey =
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
	 * outputNode); // ��ѯ���ļ�¼�� VoCollectTask result[] = context.getCollectTasks(
	 * outputNode // ); }
	 */

	public void txn30101001(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// start add by dwn 2014011
		// ���������� json����
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
		Recordset rs = codeMap.lookup(targetContext, "��Դ����_�����������");
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

		// ����ӿ�״̬json����
		CollectTaskContext stateContext = new CollectTaskContext();

		stateContext.getRecord(inputNode).setValue("codetype", "��Դ����_�鵵����״̬");
		stateContext.getRecord(inputNode).setValue("column", "task_status");
		Attribute.setPageRow(stateContext, outputNode, -1);
		table.executeFunction("getInfoByTaskStatus", stateContext, inputNode,
				outputNode);
		Recordset stateRs = stateContext.getRecordset("record");
		context.setValue("state_data", JsonDataUtil.getJsonByRecordSet(stateRs));
		//����ӿ�����json����
		CollectTaskContext typeContext = new CollectTaskContext();
		typeContext.getRecord(inputNode).setValue("codetype", "�ɼ�����_�ɼ�����");
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
	 * �޸Ĳɼ�������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101002(CollectTaskContext context) throws TxnException
	{

		// ��ȡҳ���ϵĻ�����Ϻͱ�ɾ���Ļ�����ϼ����Ե�ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// �ɼ������ļ����·��

		// ��������Ϣ���ݵ�inputNode
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
					"data_source_id");// ����Դ
			// �������Դ�ı� Ҫ���»�ȡ����
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
				rs = conn.createStatement().executeQuery(sql.toString()); // ��ȡ�����
				System.out.println("sql=====" + sql);
				if (rs.next()) {
					String count = rs.getString("name_nums");
					System.out.println("cont====" + count);
					if (count != null && count.equals("0")) {
						String sqlDataSource = "select access_url,data_source_type from res_data_source where data_source_id = '"
								+ data_source_id + "'";

						try {
							ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
							Map tablepMap = daoTable
									.queryService(sqlDataSource);
							String access_url = (String) tablepMap
									.get("ACCESS_URL");// ����URL
							data_source_type = (String) tablepMap
									.get("DATA_SOURCE_TYPE");// ����Դ����
							table.executeFunction("getFuncAndParam", context,
									inputNode, outputNode);// ��ȡ����������
						} catch (Exception e) {
							System.out.println("��������Դʧ��!");
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
							data_source_type);// �ɼ�����
				}
				context.getRecord("record").setValue("last_modify_time",
						CalendarUtil.getCurrentDateTime());// ����޸�ʱ��
				context.getRecord("record").setValue("last_modify_id",
						context.getRecord("oper-data").getValue("userID"));// ����޸���ID
				context.getRecord("record").setValue("log_file_path", "");// ��־�ļ�·��
				context.getRecord("record").setValue("collect_status",
						CollectConstants.COLLECT_STATUS_NOT);// δ�ɼ�
				table.executeFunction(UPDATE_FUNCTION, context, inputNode,
						outputNode);
				String task_scheduling_id = context.getRecord("record")
						.getValue("task_scheduling_id");// �ƻ�����ID
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
	 * ���Ӳɼ�������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101003(CollectTaskContext context) throws TxnException
	{

		// ��ȡҳ���ϵĻ�����Ϻͱ�ɾ���Ļ�����ϼ����Ե�ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// �ɼ������ļ����·��

		// ��������Ϣ���ݵ�inputNode
		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		String collect_type = context.getRecord("record").getValue(
				"collect_type");
		String data_source_id = context.getRecord("record").getValue(
				"data_source_id");// ����Դ
		ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		if (collect_task_id == null || "".equals(collect_task_id)) {
			// ����
			String id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("collect_task_id", id);// ���ݱ�ID
			System.out.println("new collect_task-id="+id);
			context.getRecord("record").setValue("created_time",
					CalendarUtil.getCurrentDateTime());// ����ʱ��
			context.getRecord("record").setValue("creator_id",
					context.getRecord("oper-data").getValue("userID"));// ������ID
			context.getRecord("record").setValue("is_markup",
					ExConstant.IS_MARKUP_Y);// ���볣�� ��Ч���
			context.getRecord("record").setValue("task_status",
					ExConstant.SERVICE_STATE_Y);// ���볣�� ����
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// δ�ɼ�

			table.executeFunction(INSERT_FUNCTION, context, inputNode,
						outputNode);
			//System.out.println(context);	
			context.getPrimaryKey().setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id") );

		} else {
			System.out.println("edit collect_task_id="+collect_task_id);
			// �������Դ�ı� Ҫ���»�ȡ����
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
				rs = conn.createStatement().executeQuery(sql.toString()); // ��ȡ�����
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
										context, inputNode, outputNode);// ��ȡ����������
							}
						} catch (Exception e) {
							System.out.println("��������Դʧ��!");
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
						CalendarUtil.getCurrentDateTime());// ����޸�ʱ��
				context.getRecord("record").setValue("last_modify_id",
						context.getRecord("oper-data").getValue("userID"));// ����޸���ID
				context.getRecord("record").setValue("log_file_path", "");// ��־�ļ�·��
				context.getRecord("record").setValue("collect_status",
						CollectConstants.COLLECT_STATUS_NOT);// δ�ɼ�
				table.executeFunction(UPDATE_FUNCTION, context, inputNode,
						outputNode);
				/*String task_scheduling_id = context.getRecord("record")
						.getValue("task_scheduling_id");// �ƻ�����ID
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
	 * ��ѯ�ɼ����������޸�
	 * 
	 * @param context
	 *            ����������
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
					"dataItem");// �����б�

			// ������ڵ��л�ȡ������ϵ�ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ������ϵ�����
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}

		context.addRecord("select-key", selectBus);
		//System.out.println("context1======" + context);
	}

	/**
	 * 
	 * txn30101222 ��ת���ɼ�����������Ϣҳ��   
	 * @param context
	 * @throws TxnException
	 * @throws DBException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
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

			// ������ڵ��л�ȡ������ϵ�ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ������ϵ�����
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}

		context.addRecord("select-key", selectBus);
		System.out.println("context1======" + context);
		
		
	}

	/**
	 * ɾ���ɼ�������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30101005(CollectTaskContext context) throws TxnException,
			DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		// ɾ���������
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		String task_scheduling_id = null;
		ServiceDAO daoTable = new ServiceDAOImpl();
		// �������ݱ�Dao
		String sql = "select task_scheduling_id from collect_task_scheduling where collect_task_id = '"
				+ collect_task_id + "'";

		Map tablepMap = daoTable.queryService(sql);// ��ȡ�������ID
		if (tablepMap != null && !tablepMap.isEmpty()) {
			task_scheduling_id = (String) tablepMap.get("TASK_SCHEDULING_ID");
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
				vo.setcollect_task_id(collect_task_id);
				SimpleTriggerRunner.removeFromScheduler(vo);
			}
		}

		table.executeFunction("deleteTaskItem", context, inputNode, outputNode);// ɾ������������
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);

	}

	/**
	 * ��ѯ�ɼ��������ڲ鿴
	 * 
	 * @param context
	 *            ����������
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
				"dataItem");// �����б�
		
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

		// ������ڵ��л�ȡ������ϵ�ID
		String fjids = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		System.out.println("fjids==" + fjids);
		// ������ڵ��л�ȡ������ϵ�����
		String filenames = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	}

	/**
	 * ��ѯ�ɼ��������ڲ鿴
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101016(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("getCollectTask", context, inputNode, outputNode);
		Attribute.setPageRow(context, "dataItem", -1);
		table.executeFunction("getFunctionByTask", context, inputNode,
				"dataItem");// �����б�
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

		// ������ڵ��л�ȡ������ϵ�ID
		String fjids = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		System.out.println("fjids==" + fjids);
		// ������ڵ��л�ȡ������ϵ�����
		String filenames = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	}

	/**
	 * 
	 * txn30101111 ��ѯ�ɼ�������������webservice��tabҳ��
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn30101111(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_type = context.getRecord("primary-key").getValue(
				"collect_type");// �ɼ�����
		System.out.print("collect_type1111===" + collect_type);// �ɼ�����
	}

	/**
	 * ��ѯ�ɼ�������������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101007(CollectTaskContext context) throws TxnException
	{
		// ��ת��һ��,����Ӧ�ø��ݲ�ͬ�Ĳɼ�������ת����ͬ��ҳ����ʱû����
		String collect_type = context.getRecord("primary-key").getValue(
				"collect_type");// �ɼ�����
		//System.out.print("collect_type===" + collect_type);
		context.getRecord(outputNode).setValue("collect_type", collect_type);// �ɼ�����,��ת���ɼ�����ҳ��insert-collect_task
	}

	/**
	 * ��ȡ����
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101008(CollectTaskContext context) throws TxnException
	{
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");// ����ID
		String collect_type = context.getRecord("primary-key").getValue(
				"collect_type");// �ɼ�����
		// lizheng
		System.out.println("collect_task_id=======" + collect_task_id);
		System.out.println("�ɼ�����collect_type=======" + collect_type);
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
			// FTP�ɼ�����
			FtpClient ftpClient = new FtpClient();
			try {
				ftpClient.doCollectTaskFtp(collect_task_id);
			} catch (DBException e) {
				System.out.println("���ݿ�ִ�д���" + e);
				e.printStackTrace();
			} catch (TxnDataException e) {
				System.out.println("��ȡftp�ļ�����" + e);
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("�ļ��洢����" + e);
				e.printStackTrace();
			}

		}

	}

	/**
	 * ��ȡ�ɼ������Ӧ����
	 * 
	 * @param context
	 *            ����������
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
					"dataItem");// �����б�

			// ������ڵ��л�ȡ������ϵ�ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ������ϵ�����
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
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
	 * txn30101010(�޸�����/ͣ��״̬)
	 * 
	 * @param context
	 * @throws Exception
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn30101010(CollectTaskContext context) throws Exception
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
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
				// //ͣ�� �Ƿ�����Ч����
				sql += ExConstant.SERVICE_STATE_N + "' ";

				// ɾ���������
				String task_scheduling_id = null;

				String sqlScheduling = "select task_scheduling_id from collect_task_scheduling where collect_task_id = '"
						+ collect_task_id + "'";

				Map tablepMap = daoTable.queryService(sqlScheduling);// ��ȡ�������ID
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

				// ����������ȱ�
				sqlScheduling = "update collect_task_scheduling  set is_markup = '"
						+ ExConstant.SERVICE_STATE_N
						+ "' where collect_task_id = '" + collect_task_id + "'";
				System.out.println(sqlScheduling);
				table.executeUpdate(sqlScheduling);

			} else {
				// sql+= ExConstant.SERVICE_STATE_Y
				// +"',is_markup ='"+ExConstant.IS_MARKUP_Y+"' ";
				sql += ExConstant.SERVICE_STATE_Y + "' ";

				// �����������
				String sqlScheduling = "select * from collect_task_scheduling where collect_task_id = '"
						+ collect_task_id + "'";

				Map tablepMap = daoTable.queryService(sqlScheduling);// ��ȡ�������ID
				if (tablepMap != null && !tablepMap.isEmpty()) {
					VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
					vo.setJhrw_lx((String) tablepMap.get("SCHEDULING_TYPE"));// �ƻ���������
					vo.setJhrw_rq((String) tablepMap.get("SCHEDULING_DAY"));// �ƻ���������
					vo.setJhrw_start_sj((String) tablepMap.get("START_TIME"));// �ƻ�����ʼʱ��
					vo.setJhrw_end_sj((String) tablepMap.get("END_TIME"));// �ƻ��������ʱ��
					vo.setJhrw_zt((String) tablepMap.get("SCHEDULING_WEEK"));// �ƻ���������
					vo.setJhrwzx_cs((String) tablepMap.get("SCHEDULING_COUNT"));// �ƻ�����ִ�д���
					vo.setJhrwzx_jg((String) tablepMap.get("INTERVAL_TIME"));// �ƻ�����ִ��
					// ���
					vo
							.setJob_class_name(TaskSchedulingConstants.JOB_CLASS_NAME);// �������õ�����
					vo.settask_scheduling_id((String) tablepMap
							.get("COLLECT_TASK_ID"));
					vo.setcollect_task_id(collect_task_id);
					SimpleTriggerRunner.addToScheduler(vo);

					// ����������ȱ�
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
	 * ��ѯ�ɼ�������������ftp
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101011(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("getFtpByTask", context, inputNode, "dataItem");// ftp�б�
		context.getRecord("record").setValue("flag", "add");
	}

	/**
	 * ���Ӳɼ�������Ϣftp ��һ��������ת���ڶ���
	 * 
	 * @param context
	 *            ����������
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
			//û������ID,������
			saveNew=true;
		}
		//System.out.println("first save="+context);
		// ��ȡҳ���ϵĻ�����Ϻͱ�ɾ���Ļ�����ϼ����Ե�ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);
		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// �ɼ������ļ����·��

		// ��������Ϣ���ݵ�inputNode
		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		//System.out.println("first file="+context);
		if(saveNew){
			System.out.println("txn30101101----����first-step");
			String id = UuidGenerator.getUUID();
			
			context.getRecord("record").setValue("collect_task_id", id);// ���ݱ�ID
			context.getRecord("record").setValue("created_time",
					CalendarUtil.getCurrentDateTime());// ����ʱ��
			context.getRecord("record").setValue("creator_id",
					context.getRecord("oper-data").getValue("userID"));// ������ID
			context.getRecord("record").setValue("is_markup",
					ExConstant.IS_MARKUP_Y);// ���볣�� ��Ч���
			context.getRecord("record").setValue("task_status",
					ExConstant.SERVICE_STATE_Y);// ���볣�� ����
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// δ�ɼ�
			/*
			try {
				table.executeFunction("getFtpFile", context, inputNode,
						outputNode);// ��ȡFTP�ļ� �����
			} catch (Exception e) {
				System.out.println("����ftp����Դʧ��!!");
			}*/
			context.getRecord("record").setValue("collect_type",
					data_source_type);// �ɼ�����
			context.getRecord("fileinfo").setValue("ftp_task_id",
					"");// �ļ�IDĬ������Ϊ��
			//System.out.println("һ����="+context);
			
			
			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
			//��ȡ�ڶ���ҳ����ļ���Ϣ
			context.getSelectKey().setValue("collect_task_id", id);
			context.getSelectKey().setValue("service_targets_id", 
					context.getRecord("record").getValue("service_targets_id"));
			context.getSelectKey().setValue("task_name", 
					context.getRecord("record").getValue("task_name"));
			//System.out.println(context);
			table.executeFunction("getFileInfoTree", context, inputNode,
					"treeinfo");
			
		}else{
			System.out.println("txn30101101----�޸�first-step");
			context.getRecord("record").setValue("collect_task_id", collect_task_id);
			context.getRecord("record").setValue("last_modify_time",
					CalendarUtil.getCurrentDateTime());// ����޸�ʱ��
			context.getRecord("record").setValue("last_modify_id",
					context.getRecord("oper-data").getValue("userID"));// ����޸���ID
			//System.out.println("�޸�="+context);
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
			
			//��ȡ�ڶ���ҳ����ļ���Ϣ
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
	 * ���Ӳɼ�������Ϣftp �ڶ���������ת��������
	 * 
	 * @param context
	 *            ����������
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
			//��ʼ��������ҳ��
			
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
			// ������ڵ��л�ȡ�ļ�ID
			String fjids = context.getRecord("record").getValue(
					VoCollectTask.ITEM_FJ_FK);
			//System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ�ļ�������
			String filenames = context.getRecord("record").getValue(
					VoCollectTask.ITEM_FJMC);
			if(fjids!=null && filenames!=null ){
				// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
				UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
			}
			//System.out.println("getFileInfo="+context);
			
			//System.out.println("end="+context);
			
		}
		
	}
	/**
	 * ���Ӳɼ�������Ϣftp ����������
	 * 
	 * @param context
	 *            ����������
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
					.getValue("task_scheduling_id");// �ƻ�����ID
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
	 * ���Ӳɼ�������Ϣftp ���ص�һ��
	 * 
	 * @param context
	 *            ����������
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
			// ������ڵ��л�ȡ�ļ�ID
			String fjids = context.getRecord("record").getValue(
					VoCollectTask.ITEM_FJ_FK);
			//System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ�ļ�������
			String filenames = context.getRecord("record").getValue(
					VoCollectTask.ITEM_FJMC);

			// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
			//System.out.println("getFileInfoTree="+context);
		}
		
	}
	/**
	 * ���Ӳɼ�������Ϣftp ���صڶ���
	 * 
	 * @param context
	 *            ����������
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
	 * ���Ӳɼ�������Ϣftp �ڶ��������ļ���Ϣ
	 * 
	 * @param context
	 *            ����������
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
		if(StringUtils.isNotBlank(ftp_task_id)){//�޸��ļ���Ϣ
			System.out.println("txn30101107---�޸�="+ftp_task_id);
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
			if(!first){//�зǿյ��ֶβ�ִ��
				sql+="WHERE ftp_task_id='"+ftp_task_id+"'";
				//System.out.println(sql);
				table.executeUpdate(sql);
			}
			
		}else{//�������ļ���Ϣд�����ݿ��
			ftp_task_id = UuidGenerator.getUUID();
			System.out.println("����ftp_task_id="+ftp_task_id);
			
			TxnCollectWebserviceTask tasktmp= new TxnCollectWebserviceTask();
			int count = -1;
			ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
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
	 * ���Ӳɼ�������Ϣftp �ڶ��� ɾ���ļ���Ϣ
	 * 
	 * @param context
	 *            ����������
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
		//���»�ȡ�ڶ���ҳ����ļ���Ϣ
		table.executeFunction("getFileInfoTree", context, inputNode,
				"treeinfo");
		
		
	}
	
	/**
	 * ���Ӳɼ�������Ϣftp �ڶ��� �������޸��ļ���Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101109(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		
		
		context.addRecord("select-key",context.getRecord("record") );
		String ftp_task_id = context.getRecord("select-key").getValue(
				"ftp_task_id");
		
		
		if(StringUtils.isNotBlank(ftp_task_id)){//�޸��ļ���Ϣ
			//��ȡ��Ӧ���ļ���Ϣ
			
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
			//����
			//System.out.println("add109--"+context);
		}
		
		//System.out.println("end109--"+context);
	}
	/**
	 * ˢ�²ɼ�������Ϣftp�ڶ���
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101110(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//System.out.println("ˢ��---"+context);
		String collect_task_id = context.getRecord("select-key").getValue(
				"collect_task_id");
		if(StringUtils.isNotBlank(collect_task_id)){//�޸��ļ���Ϣ
			//��ȡ��Ӧ���ļ���Ϣ
			table.executeFunction("getFileInfoTree", context, inputNode,
					"treeinfo");
			
		}
		
	}
	/**
	 * ���Ӳɼ�������Ϣftp
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101012(CollectTaskContext context) throws TxnException
	{	
		System.out.println("12="+context);

		// ��ȡҳ���ϵĻ�����Ϻͱ�ɾ���Ļ�����ϼ����Ե�ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// �ɼ������ļ����·��

		// UploadFileVO fileVO1 = new UploadFileVO();
		// //fileVO1.setRecordName("record:fjmc");
		// fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);
		//
		// vo = UploadHelper.saveFile(context, fileVO1,
		// ConstUploadFileType.ZWGL);
		//
		// ��������Ϣ���ݵ�inputNode
		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		
		System.out.println("12file="+context);
		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		String data_source_id = context.getRecord("record").getValue(
				"data_source_id");// ����Դ
		ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Map tablepMap = null;
		String data_source_type = CollectConstants.TYPE_CJLX_FTP;
		if (collect_task_id == null || "".equals(collect_task_id)) {

			String id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("collect_task_id", id);// ���ݱ�ID

			context.getRecord("record").setValue("created_time",
					CalendarUtil.getCurrentDateTime());// ����ʱ��
			context.getRecord("record").setValue("creator_id",
					context.getRecord("oper-data").getValue("userID"));// ������ID
			context.getRecord("record").setValue("is_markup",
					ExConstant.IS_MARKUP_Y);// ���볣�� ��Ч���
			context.getRecord("record").setValue("task_status",
					ExConstant.SERVICE_STATE_Y);// ���볣�� ����
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// δ�ɼ�

			try {
				table.executeFunction("getFtpFile", context, inputNode,
						outputNode);// ��ȡFTP�ļ� �����
			} catch (Exception e) {
				System.out.println("����ftp����Դʧ��!!");
			}
			context.getRecord("record").setValue("collect_type",
					data_source_type);// �ɼ�����
			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// �ƻ�����ID
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
						+ id
						+ "' where task_scheduling_id = '"
						+ task_scheduling_id + "'";
				System.out.println("sql========" + sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
			}
		}// ����

		else {

			// �������Դ�ı� Ҫ���»�ȡ����
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
				rs = conn.createStatement().executeQuery(sql.toString()); // ��ȡ�����
				System.out.println("sql=====" + sql);
				if (rs.next()) {
					String count = rs.getString("name_nums");
					System.out.println("cont====" + count);
					if (count != null && count.equals("0")) {// ����������Դ
						table.executeFunction("getFtpFile", context, inputNode,
								outputNode);// ��ȡFTP�ļ� �����
					}
				}
			} catch (Exception e) {
				System.out.println("����ftp����Դʧ��!!");
				table.executeFunction("deleteTaskItem", context, inputNode,
						outputNode);// ɾ��ftp�ļ���

			} finally {
				dbcon.closeResultSet(rs);
				if (data_source_type != null) {
					context.getRecord("record").setValue("collect_type",
							data_source_type);// �ɼ�����
				}
				context.getRecord("record").setValue("last_modify_time",
						CalendarUtil.getCurrentDateTime());// ����޸�ʱ��
				context.getRecord("record").setValue("last_modify_id",
						context.getRecord("oper-data").getValue("userID"));// ����޸���ID
				table.executeFunction(UPDATE_FUNCTION, context, inputNode,
						outputNode);
				String task_scheduling_id = context.getRecord("record")
						.getValue("task_scheduling_id");// �ƻ�����ID
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
	 * ��ѯ�ɼ����������޸� ftp
	 * 
	 * @param context
	 *            ����������
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
			
			//System.out.println("�޸�FTP="+context);
			//System.out.println(context);
			table.executeFunction("getFTPTaskInfo", context, inputNode,
					"record");
			
			// ������ڵ��л�ȡ�ļ�ID
			String fjids = context.getRecord("record").getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ�ļ�������
			String filenames = context.getRecord("record").getValue(
					VoCollectTask.ITEM_FJMC);

			if(fjids!=null && filenames!=null ){
				// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
				UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
			}
		}
		context.getRecord("select-key").setValue("flag", "edit");
		/*System.out.println("�޸�FTP="+context);
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
					"dataItem");// �����б�

			// ������ڵ��л�ȡ������ϵ�ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ������ϵ�����
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		context.addRecord("select-key", selectBus);*/

	}

	/**
	 * �޸Ĳɼ�������Ϣ ftp
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101014(CollectTaskContext context) throws TxnException
	{

		// ��ȡҳ���ϵĻ�����Ϻͱ�ɾ���Ļ�����ϼ����Ե�ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// �ɼ������ļ����·��

		// ��������Ϣ���ݵ�inputNode
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJ_FK,
				vo.getReturnId());
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJMC,
				vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		context.getRecord("record").setValue("collect_task_id",
				context.getRecord("primary-key").getValue("collect_task_id"));// ����ID

		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		String data_source_id = context.getRecord("record").getValue(
				"data_source_id");// ����Դ
		// �������Դ�ı� Ҫ���»�ȡ����
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
			rs = conn.createStatement().executeQuery(sql.toString()); // ��ȡ�����
			System.out.println("sql=====" + sql);
			if (rs.next()) {
				String count = rs.getString("name_nums");
				System.out.println("cont====" + count);
				if (count != null && count.equals("0")) {

					try {
						table.executeFunction("getFtpFile", context, inputNode,
								outputNode);// ��ȡftp�ļ������
					} catch (Exception e) {
						System.out.println("��������Դʧ��!");
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
					CalendarUtil.getCurrentDateTime());// ����޸�ʱ��
			context.getRecord("record").setValue("last_modify_id",
					context.getRecord("oper-data").getValue("userID"));// ����޸���ID
			context.getRecord("record").setValue("log_file_path", "");// ��־�ļ�·��
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// ��־�ļ�·��
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// �ƻ�����ID
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
	 * ��ѯ�ɼ��������ڲ鿴 ftp
	 * 
	 * @param context
	 *            ����������
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
		//table.executeFunction("getFtpByTask", context, inputNode, "dataItem");// �����б�
		
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

		// ������ڵ��л�ȡ������ϵ�ID
		String fjids = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		System.out.println("fjids==" + fjids);
		// ������ڵ��л�ȡ������ϵ�����
		String filenames = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		//System.out.println("15---"+context);
	}

	/**
	 * ��ȡ�ɼ������Ӧ�ļ� ftp
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30101017(CollectTaskContext context) throws TxnException,
			DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("getFtpFile", context, inputNode, outputNode);// ��ȡ����������
		this.callService("30101013", context);
	}

	/**
	 * ���Ӳɼ�������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101021(CollectTaskContext context) throws TxnException
	{
		String COLLECT_TASK_ID = UuidGenerator.getUUID();// �ɼ�����id
		String FILE_UPLOAD_TASK_ID = UuidGenerator.getUUID();// �ļ��ϴ�����ID
		UploadFileVO fileVO1 = new UploadFileVO();
		fileVO1.setFileStatus(FileConstant.UPLOAD_FILESTATUS_SINGLE);

		UploadFileVO vo = UploadHelper.saveFile(context, fileVO1,
				ConstUploadFileType.FILE_UPLOAD, COLLECT_TASK_ID);

		// ��������Ϣ���ݵ�inputNode
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
				ExConstant.IS_MARKUP_Y);// ���볣��
		context.getRecord("record").setValue("creator_id",
				context.getRecord("oper-data").getValue("userID"));
		context.getRecord("record").setValue("task_status",
				ExConstant.SERVICE_STATE_Y);// ���볣�� ����
		context.getRecord("record").setValue("collect_status",
				CollectConstants.COLLECT_STATUS_NOT);// δ�ɼ�

		// �ɼ�����ļ���Ҫ���ֶ���Ϣ
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

			/* ��װ��־��Ϣmap */
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
			log.info("��־�ļ�·��:" + logFile);

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
				System.out.println("�ϴ��Ĳɼ��ļ����Ͳ���ȷ!");
			}

		}
		if (return_codes != null && !return_codes.equals("")) {
			context.getRecord("record")
					.setValue("collect_status", return_codes);// �ɼ��ɹ�
		}
		context.getRecord("record").setValue("log_file_path", logFile);
		table.executeFunction(INSERT_FUNCTION_FILEUPLOAD, context, inputNode,
				outputNode);// ��������
		table_file.executeFunction(INSERT_FUNCTION_FILE, context, inputNode,
				outputNode);

	}

	/**
	 * ��ѯ�ɼ����������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101022(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("getFileUploadInfo", context, inputNode,
				outputNode);

		// ������ڵ��л�ȡ������ϵ�ID
		String fjids = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		System.out.println("fjids==" + fjids);
		// ������ڵ��л�ȡ������ϵ�����
		String filenames = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");

	}

	/**
	 * ����ɼ������޸���Ϣ
	 * 
	 * @param context
	 *            ����������
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

		// ��������Ϣ���ݵ�inputNode
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

		// �ɼ�����ļ���Ҫ���ֶ���Ϣ
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

			/* ��װ��־��Ϣmap */
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
				System.out.println("�ϴ��Ĳɼ��ļ����Ͳ���ȷ!");
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
	 * ɾ���ɼ�������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101024(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("deleteFileUpload", context, inputNode,
				outputNode);// ɾ���ļ��ϴ��ɼ�����
		// table.executeFunction( DELETE_FUNCTION, context, inputNode,
		// outputNode );

	}

	/**
	 * ��ѯ�ɼ��������ڲ鿴
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101025(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("getFileUploadInfo", context, inputNode,
				outputNode);

		// ������ڵ��л�ȡ������ϵ�ID
		String fjids = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);

		// ������ڵ��л�ȡ������ϵ�����
		String filenames = context.getRecord(outputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
		UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
	}

	/**
	 * ��ѯ�ɼ���������Ϣ
	 * 
	 * @param context
	 *            ����������
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
	 * ��ѯ�ɼ�������������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101030(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("getDBByTask", context, inputNode, "dataItem");// �����б�
	}
	/**
	 * ��ѯ�ɼ�������������(��һ�� start add by dwn 20140306)
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn301010300(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		//table.executeFunction("getDBByTask", context, inputNode, "dataItem");// �����б�
		String collect_task_id = context.getRecord("primary-key").getValue("collect_task_id");//�ɼ�����ID
		
		if(collect_task_id==null || "".equals(collect_task_id)){
			
		}else{
			table.executeFunction("getCollectTask", context, inputNode,
					outputNode);
			table
					.executeFunction("getDBByTask", context, inputNode,
							"dataItem");// �����б�

			// ������ڵ��л�ȡ������ϵ�ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ������ϵ�����
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		
		
	}

	/**
	 * �������ݿ�ɼ�����
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101031(CollectTaskContext context) throws TxnException
	{
		// BaseTable table = TableFactory.getInstance().getTableObject( this,
		// TABLE_NAME );

		// ��ȡҳ���ϵĻ�����Ϻͱ�ɾ���Ļ�����ϼ����Ե�ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// �ɼ������ļ����·��

		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		String data_source_id = context.getRecord("record").getValue(
				"data_source_id");// ����Դ
		ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Map tablepMap = null;
		String data_source_type = null;
		if (collect_task_id == null || "".equals(collect_task_id)) {

			String id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("collect_task_id", id);// ���ݱ�ID

			context.getRecord("record").setValue("created_time",
					CalendarUtil.getCurrentDateTime());// ����ʱ��
			context.getRecord("record").setValue("creator_id",
					context.getRecord("oper-data").getValue("userID"));// ������ID
			context.getRecord("record").setValue("is_markup",
					ExConstant.IS_MARKUP_Y);// ���볣�� ��Ч���
			context.getRecord("record").setValue("task_status",
					ExConstant.SERVICE_STATE_Y);// ���볣�� ����
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// δ�ɼ�

			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// �ƻ�����ID
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
	 * txn301010311(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn301010311(CollectTaskContext context) throws TxnException
	{
		// BaseTable table = TableFactory.getInstance().getTableObject( this,
		// TABLE_NAME );

		// ��ȡҳ���ϵĻ�����Ϻͱ�ɾ���Ļ�����ϼ����Ե�ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// �ɼ������ļ����·��

		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		String data_source_id = context.getRecord("record").getValue(
				"data_source_id");// ����Դ
		ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Map tablepMap = null;
		String data_source_type = null;
		if (collect_task_id == null || "".equals(collect_task_id)) {

			String id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("collect_task_id", id);// ���ݱ�ID
			context.getRecord("primary-key").setValue("collect_task_id", id);// ���ݱ�ID

			context.getRecord("record").setValue("created_time",
					CalendarUtil.getCurrentDateTime());// ����ʱ��
			context.getRecord("record").setValue("creator_id",
					context.getRecord("oper-data").getValue("userID"));// ������ID
			context.getRecord("record").setValue("is_markup",
					ExConstant.IS_MARKUP_Y);// ���볣�� ��Ч���
			context.getRecord("record").setValue("task_status",
					ExConstant.SERVICE_STATE_Y);// ���볣�� ����
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// δ�ɼ�

			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// �ƻ�����ID
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
						+ id
						+ "' where task_scheduling_id = '"
						+ task_scheduling_id + "'";
				System.out.println("sql========" + sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
			}
		}else{
			context.getRecord("primary-key").setValue("collect_task_id", collect_task_id);// ���ݱ�ID
			table.executeFunction(UPDATE_FUNCTION_DATABASE, context, inputNode,
					outputNode);
		}
		
		
	}

	/**
	 * �޸����ݿ�ɼ�������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101032(CollectTaskContext context) throws TxnException
	{

		// ��ȡҳ���ϵĻ�����Ϻͱ�ɾ���Ļ�����ϼ����Ե�ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// �ɼ������ļ����·��

		// ��������Ϣ���ݵ�inputNode
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJ_FK,
				vo.getReturnId());
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJMC,
				vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		System.out.println("collect_task_id 222========="
				+ context.getRecord("primary-key").getValue("collect_task_id"));
		// context.getRecord("record").setValue("collect_task_id",context.getRecord("primary-key").getValue("collect_task_id"));//����ID

		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		if (collect_task_id != null && !collect_task_id.equals("")) {
			/*
			 * System.out.println("collect_task_id========="+collect_task_id);
			 * String data_source_id =
			 * context.getRecord("record").getValue("data_source_id");//����Դ
			 * //�������Դ�ı� Ҫ���»�ȡ���� StringBuffer sql= new StringBuffer();
			 * sql.append( "select count(*) as name_nums from collect_task t
			 * where t.is_markup='" +ExConstant.IS_MARKUP_Y+"'"); sql.append("
			 * and t.data_source_id='"+data_source_id+"'"); sql.append(" and
			 * t.collect_task_id='"+collect_task_id+"'"); ConnectFactory cf =
			 * ConnectFactory.getInstance(); DBController dbcon =
			 * cf.getConnection(); OracleConnection conn = (OracleConnection)
			 * dbcon.getConnection(); ResultSet rs = null; String
			 * data_source_type=null; conn.setRemarksReporting(true); try{ rs =
			 * conn.createStatement().executeQuery(sql.toString()); // ��ȡ�����
			 * System.out.println("sql====="+sql); if (rs.next()) { String
			 * count= rs.getString("name_nums");
			 * System.out.println("cont===="+count);
			 * if(count!=null&&count.equals("0")){ String sqlDataSource= "select
			 * access_url,data_source_type from res_data_source where
			 * data_source_id = '" +data_source_id+"'";
			 * 
			 * try { ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao Map
			 * tablepMap = daoTable.queryService(sqlDataSource); String
			 * access_url=(String) tablepMap.get("ACCESS_URL");//����URL
			 * data_source_type=(String)
			 * tablepMap.get("DATA_SOURCE_TYPE");//����Դ���� // ServiceInfo
			 * serviceInfo = new ServiceInfo(); //
			 * System.out.println("access_url===="+access_url); //
			 * serviceInfo.setWsdllocation(access_url); // ComponentBuilder
			 * builder = new ComponentBuilder(); // serviceInfo =
			 * builder.buildserviceinformation(serviceInfo);
			 * table.executeFunction("getFuncAndParam", context,
			 * inputNode,outputNode);//��ȡ���������� }catch(Exception e){
			 * System.out.println("��������Դʧ��!");
			 * table.executeFunction("deleteTaskItem", context, inputNode,
			 * outputNode); } } } }catch(Exception e){ e.printStackTrace();
			 * }finally { dbcon.closeResultSet(rs); if(data_source_type!=null){
			 * context
			 * .getRecord("record").setValue("collect_type",data_source_type);//
			 * �ɼ����� }
			 */
			context.getRecord("record").setValue("last_modify_time",
					CalendarUtil.getCurrentDateTime());// ����޸�ʱ��
			context.getRecord("record").setValue("last_modify_id",
					context.getRecord("oper-data").getValue("userID"));// ����޸���ID
			context.getRecord("record").setValue("log_file_path", "");// ��־�ļ�·��
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// δ�ɼ�
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// �ƻ�����ID
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
	 * ��ѯ���ݿ�ɼ����������޸�
	 * 
	 * @param context
	 *            ����������
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
							"dataItem");// �����б�
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

			// ������ڵ��л�ȡ������ϵ�ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ������ϵ�����
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		context.addRecord("select-key", selectBus);
	}
	
	/**
	 * 
	 * txn301010344(���ݿ�ɼ�������������  add by dwn 20140306)    
	   
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
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
							"dataItem");// �����б�

			// ������ڵ��л�ȡ������ϵ�ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ������ϵ�����
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		context.addRecord("select-key", selectBus);
	}

	/**
	 * ɾ���ɼ�������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn30101036(CollectTaskContext context) throws TxnException,
			DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("deleteTaskDatabaseItem", context, inputNode,
				outputNode);// ɾ������������
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);

		// ɾ���������
		String collect_task_id = context.getRecord("primary-key").getValue(
				"collect_task_id");
		String task_scheduling_id = null;
		ServiceDAO daoTable = new ServiceDAOImpl();
		; // �������ݱ�Dao
		String sql = "select task_scheduling_id from collect_task_scheduling where collect_task_id = '"
				+ collect_task_id + "'";

		Map tablepMap = daoTable.queryService(sql);// ��ȡ�������ID
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
	 * txn30101040(�޸����ݿ�ɼ�����ڶ���)    
	  
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
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

		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// �ɼ������ļ����·��

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
			//context.getRecord("primary-key").setValue("collect_task_id", collect_task_id);// ���ݱ�ID
			table.executeFunction(UPDATE_FUNCTION_DATABASE, context, inputNode,outputNode);
			
			table.executeFunction("getCollectTask", context, inputNode,outputNode);
			table.executeFunction("getDBByTask", context, inputNode,"dataItem");// �����б�

			
		}
		//context.addRecord("select-key", selectBus);
	}
	
	/**
	 * 
	 * txn30101041(�������ݿ�ɼ�����ڶ���)    
	  
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
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

		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// �ɼ������ļ����·��

		context.getRecord(inputNode).setValue("fj_fk", vo.getReturnId());
		context.getRecord(inputNode).setValue("fjmc", vo.getReturnName());
		
		if (collect_task_id == null || "".equals(collect_task_id)) {

			String id = UuidGenerator.getUUID();
			context.getRecord("record").setValue("collect_task_id", id);// ���ݱ�ID
			context.getRecord("primary-key").setValue("collect_task_id", id);// ���ݱ�ID

			context.getRecord("record").setValue("created_time",
					CalendarUtil.getCurrentDateTime());// ����ʱ��
			context.getRecord("record").setValue("creator_id",
					context.getRecord("oper-data").getValue("userID"));// ������ID
			context.getRecord("record").setValue("is_markup",
					ExConstant.IS_MARKUP_Y);// ���볣�� ��Ч���
			context.getRecord("record").setValue("task_status",
					ExConstant.SERVICE_STATE_Y);// ���볣�� ����
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// δ�ɼ�
			
			context.getRecord("primary-key").setValue("collect_task_id",
					context.getRecord("record").getValue("collect_task_id"));
			
			

			table.executeFunction(INSERT_FUNCTION, context, inputNode,
					outputNode);
			
			table.executeFunction("getCollectTask", context, inputNode,outputNode);
			table.executeFunction("getDBByTask", context, inputNode,"dataItem");// �����б�
			
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// �ƻ�����ID
			if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {
				String sqlTaskSchedule = "update collect_task_scheduling set collect_task_id = '"
						+ id
						+ "' where task_scheduling_id = '"
						+ task_scheduling_id + "'";
				System.out.println("sql========" + sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
			}
		}else{
			context.getRecord("primary-key").setValue("collect_task_id", collect_task_id);// ���ݱ�ID
			table.executeFunction(UPDATE_FUNCTION_DATABASE, context, inputNode,outputNode);
			table.executeFunction("getCollectTask", context, inputNode,outputNode);
			table.executeFunction("getDBByTask", context, inputNode,"dataItem");// �����б�
		}

	}
	
	/**
	 * 
	 * txn30101042(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
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
							"dataItem");// �����б�

			// ������ڵ��л�ȡ������ϵ�ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ������ϵ�����
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		//context.addRecord("select-key", selectBus);
	}

	/**
	 * ��ѯ�ɼ������������� socket
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101051(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_type = context.getRecord("primary-key").getValue(
				"collect_type");// �ɼ�����
		System.out.print("collect_type===" + collect_type);
		context.getRecord(outputNode).setValue("collect_type", collect_type);// �ɼ�����
		table.executeFunction("getFunctionByTask", context, inputNode,
				"dataItem");// �����б�
	}

	/**
	 * ��ѯ�ɼ����������޸� socket
	 * 
	 * @param context
	 *            ����������
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
					"dataItem");// �����б�

			// ������ڵ��л�ȡ������ϵ�ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ������ϵ�����
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		context.addRecord("select-key", selectBus);
	}

	/**
	 * �޸Ĳɼ�������Ϣ socket
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101053(CollectTaskContext context) throws TxnException
	{

		// ��ȡҳ���ϵĻ�����Ϻͱ�ɾ���Ļ�����ϼ����Ե�ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// �ɼ������ļ����·��

		// ��������Ϣ���ݵ�inputNode
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJ_FK,
				vo.getReturnId());
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJMC,
				vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		System.out.println("collect_task_id  5========="
				+ context.getRecord("primary-key").getValue("collect_task_id"));
		// context.getRecord("record").setValue("collect_task_id",context.getRecord("primary-key").getValue("collect_task_id"));//����ID

		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		if (collect_task_id != null && !collect_task_id.equals("")) {
			System.out.println("collect_task_id 6=========" + collect_task_id);
			/**
			 * String data_source_id =
			 * context.getRecord("record").getValue("data_source_id");//����Դ
			 * //�������Դ�ı� Ҫ���»�ȡ���� StringBuffer sql= new StringBuffer();
			 * sql.append( "select count(*) as name_nums from collect_task t
			 * where t.is_markup='" +ExConstant.IS_MARKUP_Y+"'"); sql.append("
			 * and t.data_source_id='"+data_source_id+"'"); sql.append(" and
			 * t.collect_task_id='"+collect_task_id+"'"); ConnectFactory cf =
			 * ConnectFactory.getInstance(); DBController dbcon =
			 * cf.getConnection(); OracleConnection conn = (OracleConnection)
			 * dbcon.getConnection(); ResultSet rs = null; String
			 * data_source_type=null; conn.setRemarksReporting(true); try{ rs =
			 * conn.createStatement().executeQuery(sql.toString()); // ��ȡ�����
			 * System.out.println("sql====="+sql); if (rs.next()) { String
			 * count= rs.getString("name_nums");
			 * System.out.println("cont===="+count);
			 * if(count!=null&&count.equals("0")){ String sqlDataSource= "select
			 * access_url,data_source_type from res_data_source where
			 * data_source_id = '" +data_source_id+"'";
			 * 
			 * try { ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao Map
			 * tablepMap = daoTable.queryService(sqlDataSource); String
			 * access_url=(String) tablepMap.get("ACCESS_URL");//����URL
			 * data_source_type=(String)
			 * tablepMap.get("DATA_SOURCE_TYPE");//����Դ���� // ServiceInfo
			 * serviceInfo = new ServiceInfo(); //
			 * System.out.println("access_url===="+access_url); //
			 * serviceInfo.setWsdllocation(access_url); // ComponentBuilder
			 * builder = new ComponentBuilder(); // serviceInfo =
			 * builder.buildserviceinformation(serviceInfo);
			 * table.executeFunction("getFuncAndParam", context,
			 * inputNode,outputNode);//��ȡ���������� }catch(Exception e){
			 * System.out.println("��������Դʧ��!");
			 * table.executeFunction("deleteTaskItem", context, inputNode,
			 * outputNode); } } } }catch(Exception e){ e.printStackTrace();
			 * }finally { dbcon.closeResultSet(rs); if(data_source_type!=null){
			 * context
			 * .getRecord("record").setValue("collect_type",data_source_type);//
			 * �ɼ����� }
			 */
			context.getRecord("record").setValue("last_modify_time",
					CalendarUtil.getCurrentDateTime());// ����޸�ʱ��
			context.getRecord("record").setValue("last_modify_id",
					context.getRecord("oper-data").getValue("userID"));// ����޸���ID
			context.getRecord("record").setValue("log_file_path", "");// ��־�ļ�·��
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// δ�ɼ�
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// �ƻ�����ID
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
	 * ��ѯ�ɼ������������� socket
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101061(CollectTaskContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String collect_type = context.getRecord("primary-key").getValue(
				"collect_type");// �ɼ�����
		System.out.print("collect_type===" + collect_type);
		context.getRecord(outputNode).setValue("collect_type", collect_type);// �ɼ�����
		table.executeFunction("getFunctionByTask", context, inputNode,
				"dataItem");// �����б�
	}

	/**
	 * ��ѯ�ɼ����������޸� socket
	 * 
	 * @param context
	 *            ����������
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
					"dataItem");// �����б�

			// ������ڵ��л�ȡ������ϵ�ID
			String fjids = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJ_FK);
			System.out.println("fjids==" + fjids);
			// ������ڵ��л�ȡ������ϵ�����
			String filenames = context.getRecord(outputNode).getValue(
					VoCollectTask.ITEM_FJMC);

			// ���ýӿڽ���ȡ���ļ���Ϣһһ����context
			UploadHelper.getFileInfo(context, fjids, filenames, "fjdb");
		}
		context.addRecord("select-key", selectBus);

	}

	/**
	 * �޸Ĳɼ�������Ϣ socket
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn30101063(CollectTaskContext context) throws TxnException
	{

		// ��ȡҳ���ϵĻ�����Ϻͱ�ɾ���Ļ�����ϼ����Ե�ID
		String delIDs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELIDS);
		String delNAMEs = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_DELNAMES);
		String hyclid = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJ_FK);
		String hycl = context.getRecord(inputNode).getValue(
				VoCollectTask.ITEM_FJMC);

		// ����һ��UploadFileVO���󣬱�������������͵Ķ฽��
		UploadFileVO fileVO = new UploadFileVO();
		fileVO.setRecordName("record:fjmc");
		fileVO.setDeleteId(delIDs);// ҳ�汣��ı�ɾ������Idֵ
		fileVO.setDeleteName(delNAMEs);// ҳ�汣��ı�ɾ������nameֵ
		fileVO.setOriginId(hyclid);// ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
		fileVO.setOriginName(hycl);// ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
		fileVO.setFileStatus(FileConstant.UPLOAD_FILESTATUS_MULTIPLE);// �฽��
		UploadFileVO vo = UploadHelper.updateFile(context, fileVO,
				ConstUploadFileType.COLRECORD);// �ɼ������ļ����·��

		// ��������Ϣ���ݵ�inputNode
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJ_FK,
				vo.getReturnId());
		context.getRecord(inputNode).setValue(VoZwTzglJbxx.ITEM_FJMC,
				vo.getReturnName());
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		System.out.println("collect_task_id========="
				+ context.getRecord("primary-key").getValue("collect_task_id"));
		// context.getRecord("record").setValue("collect_task_id",context.getRecord("primary-key").getValue("collect_task_id"));//����ID

		String collect_task_id = context.getRecord("record").getValue(
				"collect_task_id");
		if (collect_task_id != null && !collect_task_id.equals("")) {
			System.out.println("collect_task_id=========" + collect_task_id);

			context.getRecord("record").setValue("last_modify_time",
					CalendarUtil.getCurrentDateTime());// ����޸�ʱ��
			context.getRecord("record").setValue("last_modify_id",
					context.getRecord("oper-data").getValue("userID"));// ����޸���ID
			context.getRecord("record").setValue("log_file_path", "");// ��־�ļ�·��
			context.getRecord("record").setValue("collect_status",
					CollectConstants.COLLECT_STATUS_NOT);// δ�ɼ�
			table.executeFunction(UPDATE_FUNCTION, context, inputNode,
					outputNode);
			String task_scheduling_id = context.getRecord("record").getValue(
					"task_scheduling_id");// �ƻ�����ID
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
	 * �鿴�ɼ�������Ϣ������ҳ������Ϣ
	 * 
	 * @param context
	 *            ����������
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
	 * �鿴�ɼ�������Ϣ������ҳ������Ϣ
	 * 
	 * @param context
	 *            ����������
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
	 * �ֶ�ִ�вɼ�����
	 * @param context
	 * @throws Exception 
	 */
	public void txn30182103(CollectTaskContext context) throws Exception
	{
		
		DataBus record=context.getRecord("record");
		//System.out.println("�ֶ�ִ�У�"+context);
		String collect_type=record.getValue("collect_type");		
		
		if("02".equals(collect_type)){//FTP����
			
			FtpClient ftpClient =new FtpClient();
			DataBus rsBus=ftpClient.doManualFTP(record);
			context.addRecord("rsBack", rsBus);
		}else {
			DataBus rsBus=new DataBus();
			rsBus.put("count", "0");
			rsBus.put("errorMsg", "�ݲ�֧���������͵Ĳɼ�����");
			context.addRecord("rsBack", rsBus);
		}
	}	
	
	/**
	 * ���ظ���ķ����������滻���׽ӿڵ�������� ���ú���
	 * 
	 * @param funcName
	 *            ��������
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����");
		}

		// ִ��
		CollectTaskContext appContext = new CollectTaskContext(context);
		invoke(method, appContext);
	}
}
