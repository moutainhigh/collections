package com.gwssi.log.sharelog.txn;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.template.freemarker.FreemarkerUtil;

import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.MathUtils;
import com.gwssi.common.util.ValueSetCodeUtil;
import com.gwssi.log.sharelog.vo.ShareLogContext;

public class TxnShareLog extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnShareLog.class,
														ShareLogContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "share_log";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select share_log list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one share_log";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "${mod.function.update}";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "${mod.function.insert}";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "${mod.function.delete}";

	/**
	 * ���캯��
	 */
	public TxnShareLog()
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
	 * ��ѯ������־�б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6010001(ShareLogContext context) throws TxnException
	{
		/*
		 * ShareLogVo vo =new ShareLogVo();
		 * vo.setLog_id(UuidGenerator.getUUID());
		 * 
		 * LogUtil util=new LogUtil(); util.saveShareLog(vo);
		 */
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoShareLogSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction("queryShareLogList", context, inputNode,
				outputNode);

		Recordset resultList = context.getRecordset(outputNode);
		for (int i = 0; i < resultList.size(); i++) {
			String return_code = ValueSetCodeUtil.getPropertiesByKey(
					"share_error", resultList.get(i).getValue("return_codes"));
			resultList.get(i).setValue("return_codes", return_code);
		}
		// ��ѯ���ļ�¼�� VoShareLog result[] = context.getShareLogs( outputNode );
		
		
		String created_time_start = context.getRecord("select-key").getValue("created_time_start");
		String created_time_end = context.getRecord("select-key").getValue("created_time_end");
		
		
		if((created_time_start==null||"".equals(created_time_start))&&(created_time_end==null||"".equals(created_time_end))){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String start_date = df.format(calendar.getTime());
			String end_date = df.format(new Date());
		context.getRecord("select-key").setValue("created_time_start",
				start_date);
		context.getRecord("select-key").setValue("created_time_end",
				end_date);
		}
		
		/*if (StringUtils.isNotBlank(create_time)) {
			String[] ctime = com.gwssi.common.util.DateUtil
				.getDateRegionByDatePicker(create_time, true);
			context.getRecord("select-key").setValue("created_time_start",
					ctime[0]);
			context.getRecord("select-key").setValue("created_time_end",
					ctime[1]);
			//context.getRecord("select-key").remove("created_time");
}*/

	}

	/**
	 * �޸Ĺ�����־��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6010002(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoShareLog share_log = context.getShareLog( inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ���ӹ�����־��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6010003(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoShareLog share_log = context.getShareLog( inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ѯ������־�����޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6010004(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoShareLogPrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoShareLog result = context.getShareLog( outputNode );
	}

	/**
	 * ɾ��������־��Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6010005(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoShareLogPrimaryKey primaryKey[] = context.getPrimaryKeys(
		// inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ת��������־��Ϣҳ��
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6010006(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryShareLog", context, inputNode, outputNode);

		// System.out.println("context="+context);
		Recordset resultList = context.getRecordset(outputNode);
		for (int i = 0; i < resultList.size(); i++) {
			String return_code = ValueSetCodeUtil.getPropertiesByKey(
					"share_error", resultList.get(i).getValue("return_codes"));
			resultList.get(i).setValue("return_codes", return_code);
		}

	}

	/**
	 * ��ѯ�б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 * @throws IOException
	 */
	public void txn6010010(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		ShareLogContext targetContext = new ShareLogContext();
		Attribute.setPageRow(targetContext, outputNode, -1);
		table.executeFunction("getlistserobj", targetContext, inputNode,
				outputNode);
		System.out.println(targetContext);
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

	public void txn6010011(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String create_time = context.getRecord("select-key").getValue(
				"created_time");
		if (StringUtils.isNotBlank(create_time)) {
			String[] ctime = com.gwssi.common.util.DateUtil
					.getDateRegionByDatePicker(create_time, true);
			context.getRecord("select-key").setValue("created_time_start",
					ctime[0]);
			context.getRecord("select-key").setValue("created_time_end",
					ctime[1]);
			context.getRecord("select-key").remove("created_time");
		}
		table.executeFunction("queryserobj", context, inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoShareService result[] = context.getShareServices(
		// outputNode );
	}

	/**
	 * �鿴���м����־
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6010007(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String start_time = context.getRecord("select-key").getValue(
				"startTime");
		String end_time = context.getRecord("select-key").getValue("endTime");
		if (StringUtils.isBlank(start_time)) {
			context.getRecord("select-key").setValue("startTime",
					DateUtil.getYesterdayDate(DateUtil.getToday()));
		}
		if (StringUtils.isBlank(end_time)) {
			context.getRecord("select-key").setValue("endTime",
					DateUtil.getToday());
		}
		table.executeFunction("queryMonitorLog", context, inputNode, outputNode);
		Recordset resultList = context.getRecordset(outputNode);
		for (int i = 0; i < resultList.size(); i++) {
			String return_code = ValueSetCodeUtil.getPropertiesByKey(
					"share_error", resultList.get(i).getValue("monitor_value"));
			resultList.get(i).setValue("monitor_value", return_code);
		}

	}

	// ----------------------�ָ���-------------------------
	// 601��ͷ�Ľ�������־��ѯ��602��ͷ����ͳ��ͳ��

	/**
	 * txn6020001(������һ�仰�����������������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C
	 * ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn6020001(ShareLogContext context) throws TxnException
	{

		System.out.println("txn6020001");
		// BaseTable table =
		// TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		// table.executeFunction("queryShareLog", context,
		// inputNode,outputNode);
	}

	/**
	 * 
	 * txn6020002(������һ�仰�����������������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C
	 * ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn6020002(ShareLogContext context) throws TxnException
	{

		// System.out.println("txn6020002");
		// System.out.println(context);
		String query_index = context.getRecord("select-key").getValue(
				"query_index");
		String service_targets_id = context.getRecord("select-key").getValue(
				"service_targets_id");
		// String statistical_granularity =
		// context.getRecord("select-key").getValue("statistical_granularity");
		// String startTime =
		// context.getRecord("select-key").getValue("startTime");
		// String endTime = context.getRecord("select-key").getValue("endTime");

		// System.out.println(service_targets_id);
		// System.out.println(query_index);
		// System.out.println(statistical_granularity);
		// System.out.println(startTime);
		// System.out.println(endTime);

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("queryStatisticsShareLog", context, inputNode,
				outputNode);

		FreemarkerUtil freeUtil = new FreemarkerUtil();
		Recordset rsChart = context.getRecordset(outputNode);

		Map dataMap2 = new HashMap();
		ArrayList dataList = new ArrayList();
		String timeFrom = "";
		String timeTo = "";
		for (int i = 0; i < rsChart.size(); i++) {
			DataBus db1 = rsChart.get(i);
			db1.setValue("ts", MathUtils.toCountUp(db1.get("ts"), 2));
			Map dataMap = new HashMap();
			// System.out.println("area_name="+chartdb.get("area_name"));
			dataMap.put("month", db1.get("mon"));
			dataMap.put("times", db1.get("s"));
			dataMap.put("num", db1.get("n"));
			dataMap.put("AVG_CONSUME_TIME", MathUtils.toCountUp(db1.get("ts"),
					2));
			dataList.add(dataMap);
			if (i == 0)
				timeFrom = db1.get("mon").toString();
			if (i == rsChart.size() - 1)
				timeTo = db1.get("mon").toString();
		}
		dataMap2.put("list", dataList);
		dataMap2.put("query_index", query_index);
		dataMap2.put("timeFrom", timeFrom);
		dataMap2.put("timeTo", timeTo);
		// String[] service_targets_id_arry = null;
		// if (service_targets_id != null && !"".equals(service_targets_id)) {
		// service_targets_id_arry = service_targets_id.split("--");
		// dataMap2.put("target_name", service_targets_id_arry[1]);
		// }
		String chartXML = freeUtil.exportXmlString("share_log.xml", dataMap2);
		DataBus db = new DataBus();
		db.setValue("chartXML", chartXML);
		context.addRecord("chart", db);
	}

	public void txn6020003(ShareLogContext context) throws TxnException
	{

		System.out.println("txn6020003");
		// BaseTable table =
		// TableFactory.getInstance().getTableObject(this,TABLE_NAME);
		// table.executeFunction("queryShareLog", context,
		// inputNode,outputNode);
	}

	/**
	 * 
	 * txn6020004(������һ�仰�����������������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C
	 * ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void txn6020004(ShareLogContext context) throws TxnException
	{
		DataBus db = null;
		System.out.println("txn6020004");
		// String query_index =
		// context.getRecord("select-key").getValue("query_index");
		// String service_targets_id =
		// context.getRecord("select-key").getValue("service_targets_id");
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryTargetsShareLog", context, inputNode,
				outputNode);

		FreemarkerUtil freeUtil = new FreemarkerUtil();
		Recordset rsChart = context.getRecordset(outputNode);

		Map dataMap2 = new HashMap();
		ArrayList dataList = new ArrayList();
		for (int i = 0; i < rsChart.size(); i++) {
			db = rsChart.get(i);
			Map dataMap = new HashMap();
			dataMap.put("name", db.get("service_targets_name"));
			dataMap.put("time", db.get("times"));
			dataMap.put("num", db.get("nums"));
			dataList.add(dataMap);
		}
		dataMap2.put("list", dataList);

		String chartXML = freeUtil.exportXmlString(
				"share_log_targets_Pi2D.xml", dataMap2);
		// System.out.println("chartXML="+chartXML);

		db.setValue("chartXML", chartXML);
		context.addRecord("chart", db);
		// System.out.println("context="+context);
	}

	/**
	 * ��ȡ��Сʱ֮�ڲɼ����񡢹��������������
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn6020019(ShareLogContext context) throws TxnException
	{
		// DataBus db = null;
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("queryRunningInfo", context, inputNode,
				outputNode);
		// ��ѯ���������������ͳ��
		// ShareLogContext shareContext = new ShareLogContext();
		// table.executeFunction("queryRunTaskInfo", shareContext, inputNode,
		// "running-info");
		// context.addRecord("running-info",
		// shareContext.getRecordset("running-info"));
		// ��ѯ�������ľ�����Ϣ
		ShareLogContext wamingContext = new ShareLogContext();
		table.executeFunction("queryTaskWamingInfo", wamingContext, inputNode,
				"waming-info");
		Attribute.setPageRow(wamingContext, outputNode, -1);
		context.addRecord("waming-info", wamingContext
				.getRecordset("waming-info"));
//		System.out.println("wamingContext: " + wamingContext);
		// System.out.print("\nxxxxxx\n" + context);
	}

	public void txn6020018(ShareLogContext context) throws TxnException
	{
		// DataBus db = null;
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryRunningInfoIndex", context, inputNode,
				outputNode);
	}

	public void txn6030014(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		String time_start = context.getRecord("record").getValue(
				"created_time_start");
		String time_end = context.getRecord("record").getValue(
				"created_time_end");

		System.out.println(time_start + "1"+time_end);
		int count = table.executeFunction("updateShareLog", context,
				inputNode, outputNode);
		context.getRecord("record").setValue("count", String.valueOf(count));
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
		ShareLogContext appContext = new ShareLogContext(context);
		invoke(method, appContext);
	}
}
