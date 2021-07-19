package com.gwssi.dw.runmgr.etl.txn;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.template.freemarker.FreemarkerUtil;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.task.IndexJob;
import com.gwssi.common.task.PieDataCollect;
import com.gwssi.common.task.PieDataCreat;
import com.gwssi.common.util.JSONUtils;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.MathUtils;
import com.gwssi.common.util.ValueSetCodeUtil;
import com.gwssi.dw.runmgr.etl.vo.SysNoticeInfoContext;

public class TxnSysNoticeInfo extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnSysNoticeInfo.class,
														SysNoticeInfoContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "sys_notice_info";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select sys_notice_info list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one sys_notice_info";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one sys_notice_info";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one sys_notice_info";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one sys_notice_info";

	public static final String	DB_CONFIG		= "app";

	private static final String	DB_CONNECT_TYPE	= "downloadFilePath";

	// �ϼ�����
	private String				ORG_NAME		= "";

	/**
	 * ���캯��
	 */
	public TxnSysNoticeInfo()
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
	 * ��ѯ֪ͨͨ����б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void txn53000009(SysNoticeInfoContext context) throws TxnException
	{
		// System.out.println("111");
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ���ļ�¼�� VoViewSysCountSemantic result[] =
		// context.getViewSysCountSemantics( outputNode );
		// ��ѯ֪ͨ����
		this.callService("60800008", context);
		// ��ѯ��ҳ�������ͳ����Ϣ
		Attribute.setPageRow(context, outputNode, 5);

		// ������ǰ��fusionͼƬ������
		// Ŀǰ����Ҫ
		/*
		 * table.executeFunction("FCServiceXMLQuery", context, inputNode,
		 * "share-record"); Recordset rsChart =
		 * context.getRecordset("share-record"); Map data = new HashMap();
		 * ArrayList dataList = new ArrayList(); if (!rsChart.isEmpty()) { for
		 * (int i = 0; i < rsChart.size(); i++) { DataBus db_chart =
		 * rsChart.get(i); Map dataMap = new HashMap();
		 * dataMap.put("record_amount", db_chart.get("record_amount")); //
		 * dataMap.put("consume_time", db_chart.get("consume_time"));
		 * dataMap.put("datey", db_chart.get("datey"));
		 * dataMap.put("exec_count", db_chart.get("exec_count"));
		 * dataList.add(dataMap); } data.put("list", dataList); FreemarkerUtil
		 * freeUtil = new FreemarkerUtil(); String chartXML =
		 * freeUtil.exportXmlString("index_share.xml", data); DataBus chart =
		 * new DataBus(); // chart.setValue("chartShareXML", "hello");
		 * chart.setValue("chartShareXML", chartXML); context.addRecord("chart",
		 * chart); } // rsChart.remove(); data.clear(); dataList.clear();
		 */

		// ��ѯ��ҳ�ɼ�����ͳ����Ϣ
		/*
		 * Attribute.setPageRow(context, outputNode, 5);
		 * table.executeFunction("FCCollectXMLQuery", context, inputNode,
		 * "collect-record"); rsChart = context.getRecordset("collect-record");
		 * // data = new HashMap(); // ArrayList dataList = new ArrayList(); if
		 * (!rsChart.isEmpty()) { for (int i = 0; i < rsChart.size(); i++) {
		 * DataBus db_chart = rsChart.get(i); Map dataMap = new HashMap();
		 * dataMap.put("record_amount", db_chart.get("record_amount"));
		 * dataMap.put("total_times", db_chart.get("total_times"));
		 * dataMap.put("datey", db_chart.get("datey")); dataList.add(dataMap); }
		 * data.put("list", dataList); FreemarkerUtil freeUtil = new
		 * FreemarkerUtil(); String chartXML =
		 * freeUtil.exportXmlString("index_collect.xml", data); //
		 * System.out.println(chartXML); DataBus chart = new DataBus();
		 * chart.setValue("chartCollectXML", chartXML);
		 * context.addRecord("chart_collect", chart); }
		 */
		// ��ҳ��������ѯ
		// ��ѯ�������Ĳɼ����񡢹������ֲ����
		// context.getSelectKey().setProperty("service_targets_type", "001"); //
		// �ڲ�ϵͳ
		Attribute.setPageRow(context, "mon-inner-record", -1);
		table.executeFunction("indexServiceTargetsInfo", context, inputNode,
				"mon-inner-record");
		// context.getSelectKey().setProperty("service_targets_type", "002"); //
		// �ⲿϵͳ
		// Attribute.setPageRow(context, "mon-outer-record", -1);
		// table.executeFunction("indexServiceTargetsInfo", context, inputNode,
		// "mon-outer-record");
		// context.getSelectKey().setProperty("service_targets_type", "000"); //
		// ���ط־�
		// Attribute.setPageRow(context, "mon-district-record", -1);
		// table.executeFunction("indexServiceTargetsInfo", context, inputNode,
		// "mon-district-record");
		// System.out.println("\n\n\n"+context+"\n\n\n");
		// System.out.println("\n\n"+context+"\n\n");
	}

	/*
	 * public Map<String, List<String>> getTargets(String type) throws
	 * TxnException{ Map<String, List<String>> targetList = new HashMap<String,
	 * List<String>>(); BaseTable table =
	 * TableFactory.getInstance().getTableObject( this, TABLE_NAME );
	 * SysNoticeInfoContext context = new SysNoticeInfoContext();
	 * Attribute.setPageRow(context, outputNode, -1);
	 * table.executeFunction("indexAllTargets", context, inputNode, outputNode);
	 * Recordset rsTarget = context.getRecordset(outputNode); List<String>
	 * innerList = new ArrayList<String>(); List<String> outerList = new
	 * ArrayList<String>(); List<String> distList = new ArrayList<String>();
	 * for(int ii=0; ii<rsTarget.size(); ii++){ DataBus db = new DataBus(); db =
	 * rsTarget.get(ii); if("00".equals(db.getValue("service_targets_state"))){
	 * innerList.add(db.getValue("service_targets_name")); }else
	 * if("01".equals(db.getValue("service_targets_state"))){
	 * outerList.add(db.getValue("service_targets_name")); }else
	 * if("02".equals(db.getValue("service_targets_state"))){
	 * distList.add(db.getValue("service_targets_name")); } }
	 * targetList.put("inner", innerList); targetList.put("outer", outerList);
	 * targetList.put("dist", distList);
	 * 
	 * return targetList; }
	 */
	/**
	 * ϵͳ����ҳ��
	 * 
	 * @param context
	 * @throws TxnException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void txn53000010(SysNoticeInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		// ��֯�����������ṹ����
		table.executeFunction("previewObjInfo", context, inputNode, outputNode);
		Recordset rs = context.getRecordset(outputNode);
		Map map = new HashMap();
		for (int i = 0; i < rs.size(); i++) {
			DataBus db = rs.get(i);
			String s_type = db.getValue("codename");
			Map detail = new HashMap();
			detail.put("service_targets_id", db.getValue("service_targets_id"));
			detail.put("service_targets_name",
					db.getValue("service_targets_name"));
			detail.put("share_num", db.getValue("share_num"));
			detail.put("collect_num", db.getValue("collect_num"));
			if (!map.containsKey(s_type)) {
				List list = new ArrayList();
				list.add(detail);
				map.put(s_type, list);
			} else {
				List list = (List) map.get(s_type);
				list.add(detail);
				map.put(s_type, list);
			}
		}
		List list = new ArrayList();
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs_code = codeMap.lookup(context, "��Դ����_�����������");
		for (int i = 0; i < rs_code.size(); i++) {
			DataBus db = rs_code.get(i);
			Map data = new HashMap();
			data.put("name", db.getValue("codename"));
			data.put("data", map.get(db.getValue("codename")));
			list.add(data);
		}
		String obj_str = JsonDataUtil.toJSONString(list);
		context.setValue("obj_str", obj_str);
		// �������ֲ�
		Attribute.setPageRow(context, "service-record", -1);
		table.executeFunction("FCshareServiceXMLQuery", context, inputNode,
				"service-record");
		Recordset rsChart = context.getRecordset("service-record");
		Map data = new HashMap();
		ArrayList dataList = new ArrayList();
		if (!rsChart.isEmpty()) {
			for (int i = 0; i < rsChart.size(); i++) {
				DataBus db_chart = rsChart.get(i);
				Map dataMap = new HashMap();
				dataMap.put("typename", db_chart.get("typename"));
				dataMap.put("typeid", db_chart.get("typeid"));
				dataMap.put("sernum", db_chart.get("sernum"));
				dataList.add(dataMap);
			}
			data.put("list", dataList);
			FreemarkerUtil freeUtil = new FreemarkerUtil();
			String chartXML = freeUtil.exportXmlString("xtgl_gxfb.xml", data);
			DataBus chart = new DataBus();
			chart.setValue("chartShareserviceXML", chartXML);
			context.addRecord("service-chart", chart);
		}
		data.clear();
		dataList.clear();

		String serviceTypeName = context.getRecord("detail-record").getValue(
				"serviceTypeName");
		if (serviceTypeName == null || serviceTypeName.equals("")) {
			serviceTypeName = "�ڲ�ϵͳ";
		}
		// ��ѯ����ֲ������ϸ��Ϣ
		Attribute.setPageRow(context, "detail-record", 10);
		table.executeFunction("FCsharedetailXMLQuery", context, inputNode,
				"detail-record");
		Recordset detChart = context.getRecordset("detail-record");
		if (!detChart.isEmpty()) {
			for (int i = 0; i < detChart.size(); i++) {
				DataBus db_chart = detChart.get(i);
				Map dataMap = new HashMap();
				dataMap.put("typename", db_chart.get("typename"));
				dataMap.put("sernum", db_chart.get("sernum"));
				dataList.add(dataMap);
			}
			data.put("list", dataList);
			data.put("serviceTypeName", serviceTypeName);
			FreemarkerUtil freeUtil = new FreemarkerUtil();
			String detailchartXML = freeUtil.exportXmlString("xtgl_fwdx.xml",
					data);
			DataBus detailchart = new DataBus();
			detailchart.setValue("chartSharedetailXML", detailchartXML);
			context.addRecord("detail-chart", detailchart);
		}
		data.clear();
		dataList.clear();

		// ��ѯ�ɼ����ͳ����Ϣ
		Attribute.setPageRow(context, "coltask-record", 10);
		table.executeFunction("FCColTaskXMLQuery", context, inputNode,
				"coltask-record");
		Recordset csChart = context.getRecordset("coltask-record");
		String firstTypename = "";
		String firstTypeid = "";
		if (!csChart.isEmpty()) {
			for (int i = 0; i < csChart.size(); i++) {
				DataBus db_chart = csChart.get(i);
				Map dataMap = new HashMap();
				dataMap.put("typename", db_chart.get("typename"));
				dataMap.put("typeid", db_chart.get("typeid"));
				dataMap.put("sernum", db_chart.get("sernum"));
				dataList.add(dataMap);
				if (i == 0) {
					firstTypename = db_chart.get("typename").toString();
					firstTypeid = db_chart.get("typeid").toString();
					context.getRecord("coltask-record").setValue("firstTypeid",
							firstTypeid);
				}
			}
			data.put("list", dataList);
			FreemarkerUtil freeUtil = new FreemarkerUtil();
			String chartXML = freeUtil.exportXmlString("xtgl_cjfw.xml", data);
			DataBus chart = new DataBus();
			chart.setValue("chartColTaskXML", chartXML);
			context.addRecord("coltask-chart", chart);
		}
		data.clear();
		dataList.clear();

		// ��ѯ�ɼ������ϸ��Ϣ
		String cdserviceTypeName = context.getRecord("coldetail-record")
				.getValue("serviceTypeName");
		if (cdserviceTypeName == null || cdserviceTypeName.equals("")) {
			cdserviceTypeName = firstTypename;
		}
		Attribute.setPageRow(context, "coldetail-record", -1);
		table.executeFunction("FCColdetailXMLQuery", context, inputNode,
				"coldetail-record");
		Recordset csdetChart = context.getRecordset("coldetail-record");
		if (!csdetChart.isEmpty()) {
			for (int i = 0; i < csdetChart.size(); i++) {
				DataBus db_chart = csdetChart.get(i);
				Map dataMap = new HashMap();
				dataMap.put("coldate", db_chart.get("coldate"));
				dataMap.put("sernum", db_chart.get("sernum"));
				dataList.add(dataMap);
			}
			data.put("list", dataList);
			data.put("cdserviceTypeName", cdserviceTypeName);
			FreemarkerUtil freeUtil = new FreemarkerUtil();
			String detailchartXML = freeUtil.exportXmlString("xtgl_cjfwxx.xml",
					data);
			DataBus detailchart = new DataBus();
			detailchart.setValue("chartColdetailXML", detailchartXML);
			context.addRecord("coldetail-chart", detailchart);
		}
		data.clear();
		dataList.clear();
		table.executeFunction("shareLogPriview", context, inputNode,
				"serviceExchange-data");
		// ����ͳ�����ݱ�
		// table.executeFunction("statisticsShareService", context, inputNode,
		// "share_service_total");
		// table.executeFunction("statisticsCollectService", context, inputNode,
		// "collect_service_total");
		// table.executeFunction("statisticsShareServiceType", context,
		// inputNode, "share_service_type");
		// table.executeFunction("statisticsCollectServiceType", context,
		// inputNode, "collect_service_type");
		// �����������ͼ������
		// this.callService("53000015", context);

	}

	public void txn53000110(SysNoticeInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		// ��֯�����������ṹ����
		table.executeFunction("previewObjInfo", context, inputNode, outputNode);
		Recordset rs = context.getRecordset(outputNode);
		Map map = new HashMap();
		for (int i = 0; i < rs.size(); i++) {
			DataBus db = rs.get(i);
			String s_type = db.getValue("codename");
			Map detail = new HashMap();
			detail.put("service_targets_id", db.getValue("service_targets_id"));
			detail.put("service_targets_name",
					db.getValue("service_targets_name"));
			detail.put("share_num", db.getValue("share_num"));
			detail.put("collect_num", db.getValue("collect_num"));
			if (!map.containsKey(s_type)) {
				List list = new ArrayList();
				list.add(detail);
				map.put(s_type, list);
			} else {
				List list = (List) map.get(s_type);
				list.add(detail);
				map.put(s_type, list);
			}
		}
		List list = new ArrayList();
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs_code = codeMap.lookup(context, "��Դ����_�����������");
		for (int i = 0; i < rs_code.size(); i++) {
			DataBus db = rs_code.get(i);
			Map data = new HashMap();
			data.put("name", db.getValue("codename"));
			data.put("data", map.get(db.getValue("codename")));
			list.add(data);
		}
		String obj_str = JsonDataUtil.toJSONString(list);
		context.setValue("obj_str", obj_str);
	}

	public void txn53000111(SysNoticeInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		// ��֯�����������ṹ����
		table.executeFunction("previewObjInfo", context, inputNode, outputNode);
		Recordset rs = context.getRecordset(outputNode);
		Map map = new HashMap();
		for (int i = 0; i < rs.size(); i++) {
			DataBus db = rs.get(i);
			String s_type = db.getValue("codename");
			Map detail = new HashMap();
			detail.put("service_targets_id", db.getValue("service_targets_id"));
			detail.put("service_targets_name",
					db.getValue("service_targets_name"));
			detail.put("share_num", db.getValue("share_num"));
			detail.put("collect_num", db.getValue("collect_num"));
			if (!map.containsKey(s_type)) {
				List list = new ArrayList();
				list.add(detail);
				map.put(s_type, list);
			} else {
				List list = (List) map.get(s_type);
				list.add(detail);
				map.put(s_type, list);
			}
		}
		List list = new ArrayList();
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs_code = codeMap.lookup(context, "��Դ����_�����������");
		for (int i = 0; i < rs_code.size(); i++) {
			DataBus db = rs_code.get(i);
			Map data = new HashMap();
			data.put("name", db.getValue("codename"));
			data.put("data", map.get(db.getValue("codename")));
			list.add(data);
		}
		String obj_str = JsonDataUtil.toJSONString(list);
		context.setValue("obj_str", obj_str);
	}

	/**
	 * �鿴������������ϸajax
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn53000011(SysNoticeInfoContext context) throws TxnException
	{
		// ��ѯ����ֲ������ϸ��Ϣ
		String serviceTypeName = context.getRecord("detail-record").getValue(
				"serviceTypeName");
		if (StringUtils.isBlank(serviceTypeName)) {
			serviceTypeName = "�ڲ�ϵͳ";
		}
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("FCsharedetailXMLQuery", context, inputNode,
				"detail-record");
		Map data = new HashMap();
		ArrayList dataList = new ArrayList();
		Recordset detChart = context.getRecordset("detail-record");
		if (!detChart.isEmpty()) {
			for (int i = 0; i < detChart.size(); i++) {
				DataBus db_chart = detChart.get(i);
				Map dataMap = new HashMap();
				dataMap.put("typename", db_chart.get("typename"));
				dataMap.put("sernum", db_chart.get("sernum"));
				dataList.add(dataMap);
			}
			data.put("list", dataList);
			data.put("serviceTypeName", serviceTypeName);
			FreemarkerUtil freeUtil = new FreemarkerUtil();
			String detailchartXML = freeUtil.exportXmlString("xtgl_fwdx.xml",
					data);
			DataBus detailchart = new DataBus();
			detailchart.setValue("chartSharedetailXML", detailchartXML);
			context.addRecord("detail-chart", detailchart);
		}
		data.clear();
		dataList.clear();
	}

	/**
	 * �鿴����������
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn53000012(SysNoticeInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		TxnContext txnContext = new TxnContext();
		txnContext.addRecord("primary-key", context.getSelectKey());
		this.callService("201004", txnContext);
		context.addRecord("svrInfo", txnContext.getRecord(outputNode));
		context.addRecord("fjdb", txnContext.getRecord("fjdb"));
		// System.out.println("context=" + context);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getShareServiceByObj", context, inputNode,
				outputNode);
		SysNoticeInfoContext cont = new SysNoticeInfoContext();
		// cont.setProperty("select-key", context.getSelectKey());
		cont.setValue("select-key", context.getSelectKey());
		// System.out.println("cont=" + cont);
		this.callService("53000013", cont);
		// System.out.println("cont=" + cont);
		context.addRecord("collcet_task", cont.getRecordset(outputNode));
		// System.out.println("context=" + context);
	}

	/**
	 * �鿴�ɼ��������
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn53000013(SysNoticeInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getCollectTaskByObj", context, inputNode,
				outputNode);
	}

	/**
	 * �鿴�ɼ����������ϸajax
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn53000014(SysNoticeInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		// ��ѯ�ɼ������ϸ��Ϣ
		String cdserviceTypeName = context.getRecord("coldetail-record")
				.getValue("cdserviceTypeName");
		Attribute.setPageRow(context, "coldetail-record", -1);
		table.executeFunction("FCColdetailXMLQuery", context, inputNode,
				"coldetail-record");
		Recordset detChart = context.getRecordset("coldetail-record");
		Map data = new HashMap();
		ArrayList dataList = new ArrayList();
		if (!detChart.isEmpty()) {
			for (int i = 0; i < detChart.size(); i++) {
				DataBus db_chart = detChart.get(i);
				Map dataMap = new HashMap();
				dataMap.put("coldate", db_chart.get("coldate"));
				dataMap.put("sernum", db_chart.get("sernum"));
				dataList.add(dataMap);
			}
			data.put("list", dataList);
			data.put("cdserviceTypeName", cdserviceTypeName);
			FreemarkerUtil freeUtil = new FreemarkerUtil();
			String detailchartXML = freeUtil.exportXmlString("xtgl_cjfwxx.xml",
					data);
			DataBus detailchart = new DataBus();
			detailchart.setValue("chartColdetailXML", detailchartXML);
			context.addRecord("coldetail-chart", detailchart);
		}
		data.clear();
		dataList.clear();
	}

	/**
	 * 
	 * txn53000018(��������ͼ�����������ļ� add by dwn 20130730)
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void txn53000015(SysNoticeInfoContext context) throws TxnException
	{
		// String query_index =
		// context.getRecord("select-key").getValue("query_index");
		String query_index = "����������,���ô���";
		// String service_targets_id =
		// context.getRecord("select-key").getValue("service_targets_id");

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
			dataList.add(dataMap);
			if (i == 0)
				timeFrom = db1.get("mon").toString();
			if (i == rsChart.size() - 1)
				timeTo = db1.get("mon").toString();
		}
		dataMap2.put("list", dataList);
		dataMap2.put("query_index", query_index);
		String chartXML = freeUtil.exportXmlString("share_log_preview.xml",
				dataMap2);
		DataBus db = new DataBus();
		db.setValue("chartXML", chartXML);
		context.addRecord("chart", db);

	}

	/*
	 * �˵���ת �����Ǵ���menuID ���ֵ�ַ�������
	 */
	public void txn53000099(SysNoticeInfoContext context) throws TxnException
	{

	}

	/**
	 * ���빲���ѯ��ͼ�ڶ���Ŀ¼
	 */
	public void txn53000210(SysNoticeInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);

		String type = context.getRecord("select-key").getValue("type");
		String count_type = context.getRecord("select-key").getValue(
				"count_type");
		String start_time = context.getRecord("select-key").getValue(
				"start_time");
		String end_time = context.getRecord("select-key").getValue("end_time");
		if (start_time == null) {
			start_time = "";
		}
		if (end_time == null) {
			end_time = "";
		}
		// System.out.println("count_type=="+count_type);
		PieDataCreat xmlStr = new PieDataCreat();
		String tableStr = xmlStr.getPie2TableData(type);
		// String chartXML = xmlStr.getMLPie2Str(type, count_type);
		String chartXML = xmlStr.getMLPie2Str_eChart(type, count_type,
				start_time, end_time);
		DataBus db = new DataBus();
		db.setValue("chartXML", chartXML);
		db.setValue("tableData", tableStr);
		db.setValue("count_type", count_type);// �����ѯ����
		db.setValue("type", type);// �����ѯ����
		db.setValue("start_time", start_time);// ����ʱ�����
		db.setValue("end_time", end_time);// ����ʱ�����

		context.addRecord("chart", db);
		// table.executeFunction("getShareServiceByType", context,
		// inputNode,outputNode);
	}

	/**
	 * ���빲���ѯ��ͼ������Ŀ¼
	 */
	public void txn53000211(SysNoticeInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		String service_targets_id = context.getRecord("select-key").getValue(
				"service_targets_id");
		String count_type = context.getRecord("select-key").getValue(
				"count_type");
		String start_time = context.getRecord("select-key").getValue(
				"start_time");
		String end_time = context.getRecord("select-key").getValue("end_time");
		if (start_time == null) {
			start_time = "";
		}
		if (end_time == null) {
			end_time = "";
		}

		PieDataCreat xmlStr = new PieDataCreat();
		String chartXML = "";
		String link2 = "";
		// if("00".equals(count_type)){
		// String[] str = xmlStr.getMLPie3Str(service_targets_id, count_type);
		String[] str = xmlStr.getMLPie3Str_eChart(service_targets_id,
				count_type, start_time, end_time);
		chartXML = str[0];// ����������
		link2 = str[1];

		String tableStr = xmlStr.getPie3TableData(service_targets_id);
		DataBus db = new DataBus();
		db.setValue("chartXML", chartXML);
		db.setValue("tableData", tableStr);
		db.setValue("service_targets_id", service_targets_id);// �����ѯ����
		db.setValue("count_type", count_type);// �����ѯ����
		db.setValue("start_time", start_time);// ����ʱ�����
		db.setValue("end_time", end_time);// ����ʱ�����
		db.setValue("link2", link2);// �������ӵ�ַ
		context.addRecord("chart", db);

	}

	public static void main(String[] args)
	{

		// List list = new ArrayList();
		// HashMap map = new HashMap();
		// HashMap map1 = new HashMap();
		// HashMap map_w = new HashMap();
		// map_w.put("last_month", "333");
		// map_w.put("this_month", "4444");
		// map.put("webservice", map_w);
		// map1.put("���ݿ�", "���ݿ�");
		// list.add(map);
		// list.add(map1);
		// System.out.println("----" + JsonDataUtil.toJSONString(list));
	}

	/**
	 * ����ɼ��ֲ��������ͼ
	 * 
	 * @throws DBException
	 */
	public void txn53000212(SysNoticeInfoContext context) throws TxnException,
			DBException
	{
		DataBus bus = context.getSelectKey();
		String service_targets_id = bus.get("service_targets_id").toString();
		String dateFrom = bus.get("startTime").toString();
		String dateTo = bus.get("endTime").toString();
		PieDataCollect sqlrs = new PieDataCollect();

		List list = sqlrs.getCollectSql2(service_targets_id, dateFrom, dateTo);
		// System.out.println(list);
		String service_targets_name = "";
		String yAxis_data = "''";
		String series_data1 = "''";
		String series_data2 = "''";
		if (list != null && list.size() > 0) {

			service_targets_name = ((Map) list.get(0)).get(
					"SERVICE_TARGETS_NAME").toString();

			List list_data1 = new ArrayList(); // Y��
			List list_data2 = new ArrayList(); // �ɼ�������
			List list_data3 = new ArrayList(); // �ɼ�����
			int len = list.size();
			for (int i = 0; i < len; i++) {
				Map tmp = (Map) list.get(i);
				list_data1.add(tmp.get("TABLE_NAME_CN"));
				list_data2.add(tmp.get("AMOUNT"));
				list_data3.add(tmp.get("EXEC_COUNT"));
			}
			yAxis_data = JsonDataUtil.toJSONString(list_data1);
			series_data1 = JsonDataUtil.toJSONString(list_data2);
			series_data2 = JsonDataUtil.toJSONString(list_data3);
			// System.out.println(yAxis_data);
			// System.out.println(series_data1);
			// System.out.println(series_data2);
		}

		List list2 = sqlrs.getCollectTableSql2(service_targets_id);// ��ȡ�Ҳ�table����
		// System.out.println("list2="+list2.toString());
		String tableData = "";
		if (list2 != null && list2.size() > 0) {
			tableData = JsonDataUtil.toJSONString(list2);
		} else {
			tableData = "[{\"T6\":0,\"T7\":0,\"T4\":0,\"T5\":0,\"LTIMES\":0,\"CAMOUNT\":0,\"T1\":0,\"T3\":0,\"CTIMES\":0,\"T2\":0,\"LAMOUNT\":0}]";
		}

		// System.out.println("tableData="+tableData);
		DataBus db = new DataBus();
		db.setValue("service_targets_name", service_targets_name);
		db.setValue("yAxis_data", yAxis_data);// ����ͼY������
		db.setValue("series_data1", series_data1);// ����ͼ�ɼ�������
		db.setValue("series_data2", series_data2);// ����ͼ�ɼ���������
		db.setValue("tableData", tableData);// �Ҳ�table����
		db.setValue("startTime", dateFrom);
		db.setValue("endTime", dateTo);
		context.addRecord("chart", db);

	}

	/**
	 * ���������ȡ-ͳ��(����)
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn53000112(SysNoticeInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getShareStatById", context, inputNode,
				outputNode);
		Recordset share_rs = context.getRecordset(outputNode);
		//
		// SysNoticeInfoContext cont = new SysNoticeInfoContext();
		// cont.setValue("select-key", context.getSelectKey());
		// txn53000115(cont);
		// Recordset collect_rs = cont.getRecordset(outputNode);

		if (!share_rs.isEmpty()) {
			String stime_str = "";
			String snum_str = "";
			String scount_str = "";
			List slist_info = new ArrayList();
			Map sall_map = new HashMap();
			for (int i = 0; i < share_rs.size(); i++) {
				DataBus db = share_rs.get(i);
				stime_str += stime_str == "" ? "\"" + db.getValue("log_date")
						+ "\"" : ",\"" + db.getValue("log_date") + "\"";
				snum_str += snum_str == "" ? "\"" + db.getValue("sum_num")
						+ "\"" : ",\"" + db.getValue("sum_num") + "\"";
				scount_str += scount_str == "" ? "\""
						+ db.getValue("sum_count") + "\"" : ",\""
						+ db.getValue("sum_count") + "\"";
				String[] info = new String[] {
						db.getValue("sum_num").toString(),
						db.getValue("sum_count").toString(),
						db.getValue("avg_time").toString(),
						db.getValue("error_num").toString() };
				sall_map.put(db.getValue("log_date"), info);
			}
			slist_info.add(sall_map);
			context.getRecordset(outputNode).clear();
			context.getRecord(outputNode).setValue("stime_str", stime_str);
			context.getRecord(outputNode).setValue("sstat_num_str", snum_str);
			context.getRecord(outputNode).setValue("sstat_count_str",
					scount_str);
			context.getRecord(outputNode).setValue("sstat_info_str",
					JSONUtils.toJSONString(slist_info));

		}
		// context.addRecord("collect", collect_rs);
		// System.out.println(context);

	}

	/**
	 * ���������ȡ-ͳ��(�ɼ�)
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn53000115(SysNoticeInfoContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getCollectStatById", context, inputNode,
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
			context.getRecordset(outputNode).clear();
			context.getRecord(outputNode).setValue("ctime_str", ctime_str);
			context.getRecord(outputNode).setValue("cstat_num_str", cnum_str);
			context.getRecord(outputNode).setValue("cstat_count_str",
					ccount_str);
			context.getRecord(outputNode).setValue("cstat_info_str",
					JSONUtils.toJSONString(clist_info));
			// System.out.println("time_str:"+ctime_str);
			// System.out.println("num_str:"+cnum_str);
			// System.out.println("count_str:"+ccount_str);

		}
	}

	/**
	 * MLPie_L4.jsp ʱ�������ת
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn53000213(SysNoticeInfoContext context) throws TxnException,
			DBException
	{
		PieDataCollect job = new PieDataCollect();
		DataBus bus = context.getSelectKey();
		job.setPieData(bus.get("startTime").toString(), bus.get("endTime")
				.toString());

	}

	/**
	 * TopTable.jsp����
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws DBException
	 */
	public void txn53000214(SysNoticeInfoContext context) throws TxnException,
			DBException
	{
		PieDataCollect job = new PieDataCollect();
		job.setPieData("", "");

	}

	public void txn53000113(SysNoticeInfoContext context) throws TxnException,
			DBException
	{
		IndexJob job=new IndexJob();
		job.execute(null);
	}

	/**
	 * �����������ͳ��
	 * 
	 * @param context
	 * @throws DBException
	 */
	public void txn53000216(SysNoticeInfoContext context) throws TxnException,
			DBException
	{
		// ���� ��ʼʱ�䡢����ʱ�䡢�������id
		 
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���������� json����
		SysNoticeInfoContext svrTarcontext = new SysNoticeInfoContext();
		
		Attribute.setPageRow(svrTarcontext, outputNode, -1);
		table.executeFunction("getTargetInfo", svrTarcontext, inputNode,
				outputNode);
		Recordset targetRs = svrTarcontext.getRecordset("record");
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(svrTarcontext, "��Դ����_�����������");
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
			//System.out.println("group----"+groupValue);
			context.setValue("svrTarget", groupValue);
		}
		
		//��ȡ��ѯʱ��
		String start_time = context.getRecord("select-key").getValue("created_time");
		//���Ϊ�չ���Ĭ��ֵ�����������
		if(StringUtils.isBlank(start_time)){
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal=Calendar.getInstance();
			Date date=cal.getTime();
			String to= df.format(date);//����Ϊ��������
			cal.add(Calendar.MONTH, -2);//��ǰ������
			date=cal.getTime();
			String from= df.format(date);
			
			start_time=from+" �� "+to;
			context.getRecord("select-key").setValue("created_time",
					start_time);
			//System.out.println(start_time);
		}
		//System.out.println(context.getRecord("select-key"));
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("getServiceTargetsInfoRoot", context, inputNode,
				outputNode);
		Attribute.setPageRow(context, "LeafNode", -1);
		table.executeFunction("getServiceTargetsInfoLeaf", context, inputNode,
				"LeafNode");
	}
	/**
	 * �����������ͳ�ƴ�����ϸ��Ϣ
	 * 
	 * @param context
	 * @throws DBException
	 */
	public void txn53000217(SysNoticeInfoContext context) throws TxnException,
			DBException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		
		// ��ʼʱ�䡢����ʱ�䡢�������id
		DataBus db= context.getSelectKey();
		String location	=	db.get("location").toString();
		String type		=	db.get("type").toString();
		
		if("S".equals(type)){
			table.executeFunction("getShareErr", context, inputNode,"errinfos");
			Recordset rs=context.getRecordset("errinfos");
			int len=rs.size();
			for(int i=0;i<len;i++){
				DataBus tmpdb=rs.get(i);
				String errdes=ValueSetCodeUtil.getPropertiesByKey("share_error",tmpdb.get("return_codes").toString());
				tmpdb.setValue("errdes", errdes);
				if(i==0){
					context.getSelectKey().setValue("service_targets_name", tmpdb.get("service_targets_name").toString());
				}
			}
		}else if("C".equals(type)){
			table.executeFunction("getCollectErr", context, inputNode,"errinfos");
			Recordset rs=context.getRecordset("errinfos");
			int len=rs.size();
			for(int i=0;i<len;i++){
				DataBus tmpdb=rs.get(i);
				String errdes=ValueSetCodeUtil.getPropertiesByKey("share_error",tmpdb.get("return_codes").toString());
				tmpdb.setValue("errdes", errdes);
				if(i==0){
					context.getSelectKey().setValue("service_targets_name", tmpdb.get("service_targets_name").toString());
				}
			}
			
			//System.out.println(context);
		}

	}

	/**
	 * 
	 */
	public static String getDefaultStr(String val)
	{
		return StringUtils.isNotBlank(val) ? val : "-";
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
		SysNoticeInfoContext appContext = new SysNoticeInfoContext(context);
		invoke(method, appContext);
	}

	public String getORG_NAME()
	{
		return ORG_NAME;
	}

	public void setORG_NAME(String org_name)
	{
		ORG_NAME = org_name;
	}
}
