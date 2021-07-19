package com.gwssi.log.sharelog.txn;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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

import com.f1j.calc.v;
import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.task.IndexJob;
import com.gwssi.common.task.ZwtJsonCreat;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.JSONUtils;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.MathUtils;
import com.gwssi.common.util.ValueSetCodeUtil;
import com.gwssi.log.sharelog.vo.ShareLogContext;

public class TxnShareLogStatistics extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnShareLogStatistics.class,
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

	private static CodeMap		codeZh			= PublicResource
														.getCodeFactory();

	/**
	 * ���캯��
	 */
	public TxnShareLogStatistics()
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

	}

	// ----------------------�ָ���-------------------------
	// 601��ͷ�Ľ�������־��ѯ��602��ͷ����ͳ��ͳ��

	public void txn6020005(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//����ͳ������ json����
		List indexlist=new ArrayList();
		Map map =new HashMap();
		map.put("title", "��");
		map.put("key", "month");		
		indexlist.add(map);
		Map map1 =new HashMap();
		map1.put("title", "��");
		map1.put("key", "day");		
		indexlist.add(map1);
		context.setValue("query_index", JsonDataUtil.toJSONString(indexlist));
		String query_index=context.getSelectKey().getValue("query_index");
		if(StringUtils.isBlank(query_index)){
			context.getSelectKey().setValue("query_index", "day");
		}
		
		// ���������� json����
		ShareLogContext svrTarcontext = new ShareLogContext();
		svrTarcontext.getRecord(inputNode).setValue("table_name",
				"res_service_targets");
		svrTarcontext.getRecord(inputNode).setValue("col_name",
				"service_targets_id");
		svrTarcontext.getRecord(inputNode).setValue("col_title",
				"service_targets_name");
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
	}

	/**
	 * ����δ�����ͳ��
	 * 
	 * @param context
	 * @throws TxnException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void txn6020006(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("queryShareLogUnUse", context, inputNode,
				outputNode);
		
		
		
		
		DataBus db_select = context.getRecord("select-key");
		String query_index = db_select.getValue("query_index");
		String show_type = db_select.getValue("show_type");
		String query_type = StringUtils.isNotBlank(db_select
				.getValue("query_index")) ? db_select.getValue("query_index")
				: "month";
		String nouse_num = "��";
		if (StringUtils.isNotBlank(query_index)) {
			if (query_index.equals("month")) {
				nouse_num = "��";
			} else {
				nouse_num = "��";
			}
		}
		// ���Ϊͼ��ģʽ
		if (StringUtils.isNotBlank(show_type)) {
			if (show_type.equals("chart")) {
				Recordset rsChart = context.getRecordset(outputNode);
				Map data = new HashMap();
				data.put("nouse_num", nouse_num);
				ArrayList dataList = new ArrayList();
				for (int i = 0; i < rsChart.size(); i++) {
					DataBus db = rsChart.get(i);
					Map dataMap = new HashMap();
					dataMap.put("name", db.get("service_name"));
					if (query_type.equals("month")) {
						dataMap.put("num", db.get("no_use_months"));
					} else {
						dataMap.put("num", db.get("no_use_days"));
					}
					dataList.add(dataMap);
				}
				data.put("list", dataList);
				FreemarkerUtil freeUtil = new FreemarkerUtil();
				String chartXML = freeUtil.exportXmlString("unuse.xml", data);
				System.out.println(chartXML);
				DataBus chart = new DataBus();
				chart.setValue("chartXML", chartXML);
				context.addRecord("chart", chart);
			}
		}
	}
	/**
	 * �����쳣���
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn6020023(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//System.out.println("23begin----"+context);
		//����ͳ������ json����
		List indexlist=new ArrayList();
		Map map2 =new HashMap();
		map2.put("title", "��");
		map2.put("key", "year");		
		indexlist.add(map2);
		Map map =new HashMap();
		map.put("title", "��");
		map.put("key", "month");		
		indexlist.add(map);
		Map map1 =new HashMap();
		map1.put("title", "��");
		map1.put("key", "day");		
		indexlist.add(map1);
		context.setValue("query_index", JsonDataUtil.toJSONString(indexlist));
		//����Ĭ��ѡ��Ϊmonth
		context.getSelectKey().setValue("query_index", "month");
		//����Ĭ��չʾ��ʽΪͼ
		//context.getSelectKey().setValue("show_type", "chart");
		// ���������� json����
		ShareLogContext svrTarcontext = new ShareLogContext();
		svrTarcontext.getRecord(inputNode).setValue("table_name",
				"res_service_targets");
		svrTarcontext.getRecord(inputNode).setValue("col_name",
				"service_targets_id");
		svrTarcontext.getRecord(inputNode).setValue("col_title",
				"service_targets_name");
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
		//System.out.println("23end---"+context);
	}
	/**
	 * �����쳣���ͳ��
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void txn6020007(ShareLogContext context) throws TxnException,
			ParseException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//System.out.println("07begin----"+context);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("queryShareLogExpetion", context, inputNode,
				outputNode);
		DataBus db_select = context.getRecord("select-key");
		String show_type = db_select.getValue("show_type");
		String query_date = db_select.getValue("query_date");
		String query_index = db_select.getValue("query_index");
		Recordset rsChart = context.getRecordset(outputNode);
		ResourceBundle resource = ResourceBundle.getBundle("share_error");
		// ���Ϊͼ��ģʽ
		if (StringUtils.isNotBlank(show_type)) {
			if (show_type.equals("chart")) {
				if (StringUtils.isBlank(query_date)) {
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MONTH, -1);
					DateFormat df = new SimpleDateFormat("yyyy-MM");
					query_date = df.format(calendar.getTime());
					//getDateRegionRemark����Ĵ������󣬵���ѯ����Ϊ��ʱĬ�ϲ���һ���µ�����
				}
				String showdate=DateUtil.getDateRegionRemark(query_date, query_index);
				if("year".equals(query_index)){
					//getDateRegionRemark����Ĵ�������
					if(showdate.length()>5){
						showdate=showdate.substring(0,7).replace("-", "��")+"��";
						//System.out.println("showdate="+showdate);
					}
				}
				Map data = new HashMap();
				data.put("query_date",showdate);
				ArrayList dataList = new ArrayList();
				for (int i = 0; i < rsChart.size(); i++) {
					DataBus db = rsChart.get(i);
					Map dataMap = new HashMap();
					dataMap.put("error_code", db.get("error_code"));
					String error_code = "";
					try {
						error_code = resource.getString(db.get("error_code")
								.toString());
					} catch (Exception e) {
						error_code = "δ֪����";
					}
					dataMap.put("error_name", error_code);
					dataMap.put("count", db.get("error_count"));
					dataList.add(dataMap);
				}
				data.put("list", dataList);
				FreemarkerUtil freeUtil = new FreemarkerUtil();
				String chartXML = freeUtil.exportXmlString("Pie3D2.xml", data);
				System.out.println(chartXML);
				DataBus chart = new DataBus();
				chart.setValue("chartXML", chartXML);
				
				context.addRecord("chart", chart);
			} else {
				for (int i = 0; i < rsChart.size(); i++) {
					DataBus db = rsChart.get(i);
					String error_code = "";
					try {
						error_code = resource.getString(db.get("error_code")
								.toString());
					} catch (Exception e) {
						error_code = "δ֪����";
					}
					db.setValue("error_name", error_code);
					//System.out.println(db);
				}
			}
		}
		//System.out.println("07end----"+context);
	}

	/**
	 * ����δ�����ͳ��
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void txn6020008(ShareLogContext context) throws TxnException,
			ParseException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("queryShareLogSpread", context, inputNode,
				outputNode);
		DataBus db_select = context.getRecord("select-key");
		String query_index = db_select.getValue("query_index");
		String query_date = db_select.getValue("query_date");
		String show_type = db_select.getValue("show_type");
		String unit = "��";
		if (StringUtils.isNotBlank(query_index)) {
			if (query_index.equals("type_number")
					|| query_index.equals("target_number")) {
				unit = "��";
			} else {
				unit = "��";
			}
		}
		// ���Ϊͼ��ģʽ
		if (StringUtils.isNotBlank(show_type)) {
			if (show_type.equals("chart")) {
				Recordset rsChart = context.getRecordset(outputNode);
				Map data = new HashMap();
				data.put("unit", unit);
				ArrayList dataList = new ArrayList();
				for (int i = 0; i < rsChart.size(); i++) {
					DataBus db = rsChart.get(i);
					Map dataMap = new HashMap();
					dataMap.put("unit", unit);
					if (StringUtils.isBlank(query_index)) {
						query_index = "type_quantity"; // Ĭ����ʾ ��Ӧ�������(�����ͷֲ�)
					}

					if (StringUtils.isNotBlank(query_index)) {
						dataMap.put("count", db.getValue("count"));
					}

					if (query_index.equals("type_number")
							|| query_index.equals("type_quantity")) {
						dataMap.put(
								"type",
								codeZh.getCodeDesc(context, "��Դ����_�����������",
										db.getValue("name")));
					} else {
						dataMap.put("name", db.getValue("name"));
					}

					dataList.add(dataMap);
				}
				data.put("list", dataList);
				data.put("date_remark",
						DateUtil.getDateRegionRemark(query_date, query_index));
				FreemarkerUtil freeUtil = new FreemarkerUtil();
				if (query_index.equals("target_number")
						|| query_index.equals("target_quantity")) {
					String chartXML = freeUtil.exportXmlString(
							"spread_target.xml", data);
					System.out.println(chartXML);
					DataBus chart = new DataBus();
					chart.setValue("chartXML", chartXML);
					context.addRecord("chart", chart);

				} else {
					String chartXML = freeUtil.exportXmlString(
							"spread_type.xml", data);
					System.out.println(chartXML);
					DataBus chart = new DataBus();
					chart.setValue("chartXML", chartXML);
					context.addRecord("chart", chart);
				}

			}
		}
	}

	/**
	 * �����쳣���ͳ��
	 * 
	 * @param context
	 * @throws TxnException
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void txn6020009(ShareLogContext context) throws TxnException,
			ParseException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		// ��ѯ��ѡ��ʱ��� ��������ͳ������
		table.executeFunction("queryShareLogMonthInfo", context, inputNode,
				outputNode);
		// ȡ�������
		DataBus db_select = context.getRecord("select-key");
		String show_type = db_select.getValue("show_type");
		String query_index = db_select.getValue("query_index");
		String query_date = db_select.getValue("query_date");
		DataBus db = context.getRecord(outputNode);
		db_select.put("date_remark",
				DateUtil.getDateRegionRemark(query_date, query_index));
		// �������������
		if (!db.isEmpty()) {
			// ��ѯ��ѡ��ʱ��ľ�������
			Attribute.setPageRow(context, "charts", -1);
			table.executeFunction("queryShareLogMonthMainInfo", context,
					inputNode, "charts");
			// // ���Ϊͼ��ģʽ
			if (StringUtils.isNotBlank(show_type)) {
				if (show_type.equals("chart")) {
					Recordset rsChart = context.getRecordset("charts");
					Map data = new HashMap();
					ArrayList dataList = new ArrayList();
					if (!rsChart.isEmpty()) {
						for (int i = 0; i < rsChart.size(); i++) {
							DataBus db_chart = rsChart.get(i);
							Map dataMap = new HashMap();
							dataMap.put("log_date", db_chart.get("log_date"));
							dataMap.put("sum_num", db_chart.get("st"));
							dataMap.put("exec_count", db_chart.get("se"));
							dataList.add(dataMap);
						}
						data.put("labelStep", rsChart.size() > 12 ? "0" : "1");
						data.put("list", dataList);
						FreemarkerUtil freeUtil = new FreemarkerUtil();
						String chartXML = freeUtil.exportXmlString(
								"share_log_info.xml", data);
						System.out.println(chartXML);
						DataBus chart = new DataBus();
						chart.setValue("chartXML", chartXML);
						context.addRecord("chart", chart);
					}
				}
			}
			// ��ѯ��ѡ��ʱ��� ���û���������ʱ��ͷ��ʴ���Ĵ���
			table.executeFunction("queryShareLogExpeAndTime", context,
					inputNode, "expeCount");
			if (context.getRecord("expeCount").isEmpty()) {
				db.setValue("error_rate", "0");
				db.setValue("last_use_time", "��");
			} else {
				Recordset rs_expt = context.getRecordset("expeCount");
				if (rs_expt.size() == 1) {
					DataBus db_expt = rs_expt.get(0);
					// �鵽�ļ�¼Ϊ������ʱ��
					if (db_expt.getValue("log_type").equals("1")) {
						db.setValue("last_use_time",
								db_expt.getValue("last_use_time"));
						db.setValue("first_use_time",
								db_expt.getValue("first_use_time"));
						db.setValue("error_count", "0");
						db.setValue("error_rate", "0");
					} else {// ����Ϊ�������
						db.setValue("error_count",
								db_expt.getValue("error_count"));
						String error_rate = MathUtils.percentage(
								db_expt.getValue("error_count"),
								db.getValue("sum_count"), 6);
						db.setValue("error_rate", error_rate);
					}
				} else {
					DataBus db_expt1 = rs_expt.get(0);// ������ʱ��
					DataBus db_expt2 = rs_expt.get(1);// �������
					db.setValue("last_use_time",
							db_expt1.getValue("last_use_time"));
					db.setValue("first_use_time",
							db_expt1.getValue("first_use_time"));
					System.out.println(db_expt2.getValue("error_count") + "---"
							+ db.getValue("sum_count"));
					String error_rate = MathUtils.percentage(
							db_expt2.getValue("error_count"),
							db.getValue("sum_count"), 6);
					db.setValue("error_count", db_expt2.getValue("error_count"));
					db.setValue("error_rate", error_rate);
				}
			}
			// ���ʱ������Ϊ��
			if (StringUtils.isBlank(query_index) || !query_index.equals("year")) {
				// //��ѯ��������
				DateFormat df = new SimpleDateFormat("yyyy-MM");
				// ��ѯ��������
				db_select.setValue("is_last", "0");
				table.executeFunction("queryShareLogMonthInfo", context,
						inputNode, "lastMonth");
				DataBus db_last = context.getRecord("lastMonth");
				if (!db_last.isEmpty()) {
					db.setValue("last_sum_num", db_last.getValue("sum_num"));// ���¹���������
					String rate = this.calMoM(db.getValue("avg_num"),
							db_last.getValue("avg_num"));
					db.setValue("num_rate", rate);// ����
					db.setValue("avg_num",
							MathUtils.toCountUp(db.getValue("avg_num"), 0));
					db.setValue("avg_time",
							MathUtils.toCountUp(db.getValue("avg_time"), 2));

				} else {
					db.setValue("last_sum_num", "0");// ���¹���������
					String rate = this.calMoM(db.getValue("avg_num"), "0");
					db.setValue("num_rate", rate);// ����
					db.setValue("avg_num",
							MathUtils.toCountUp(db.getValue("avg_num"), 0));
					db.setValue("avg_time",
							MathUtils.toCountUp(db.getValue("avg_time"), 2));
				}

				// ��ѯ��ѡ��ʱ��� ����ͬ�����ڵ�ͳ������
				db_select.setValue("is_last", "1");
				table.executeFunction("queryShareLogMonthInfo", context,
						inputNode, "lastTime");
				DataBus lastTime = context.getRecord("lastTime");
				// ���ͬ�ڲ�������
				if (lastTime.isEmpty()) {
					db.setValue("lastyear_sum_num", "0");
					String rate = this.calMoM(db.getValue("avg_num"), "0");
					db.setValue("lastyear_num_rate", rate);// ͬ��
				} else {
					db.setValue("lastyear_sum_num",
							lastTime.getValue("sum_num"));
					String rate = this.calMoM(db.getValue("avg_num"),
							lastTime.getValue("avg_num"));
					db.setValue("lastyear_num_rate", rate);// ͬ��
				}
			} else {
				// ��ѯ��������
				// ��ѯ��ѡ��ʱ��� ����ͬ�����ڵ�ͳ������
				db_select.setValue("is_last", "1");
				table.executeFunction("queryShareLogMonthInfo", context,
						inputNode, "lastMonth");
				DataBus db_last = context.getRecord("lastMonth");
				if (!db_last.isEmpty()) {
					db.setValue("last_sum_num", db_last.getValue("sum_num"));// ���ڹ���������
					db.setValue("lastyear_sum_num", db_last.getValue("sum_num"));
					String rate = this.calMoM(db.getValue("avg_num"),
							db_last.getValue("avg_num"));
					db.setValue("num_rate", rate);// ����
					db.setValue("lastyear_num_rate", rate);// ͬ��
				} else {
					db.setValue("last_sum_num", "0");// ���ڹ���������
					db.setValue("lastyear_sum_num", "0");
					String rate = this.calMoM(db.getValue("avg_num"), "0");
					db.setValue("num_rate", rate);// ����
					db.setValue("lastyear_num_rate", rate);// ͬ��
				}
			}
			db.setValue("avg_num",
					MathUtils.toCountUp(db.getValue("avg_num"), 0));
			db.setValue("avg_time",
					MathUtils.toCountUp(db.getValue("avg_time"), 2));
			context.addRecord("shareInfo", db);
		}
	}

	public void txn6030011(ShareLogContext context) throws TxnException, DBException
	{
		String query_type = context.getRecord("select-key").getValue(
				"query_type");
		String begin_time = context.getRecord("select-key").getValue(
				"startTime");
		String end_time = context.getRecord("select-key").getValue("endTime");
		IndexJob job = new IndexJob();
		List lst_time=job.getTimeString(query_type, begin_time, end_time);
		List list_temp = new ArrayList();
		Map map_time=new HashMap();
		for (int i = 0; i < lst_time.size(); i++) {
			Map time_map = new HashMap();
			Map map = (Map) lst_time.get(i);
			time_map.put("id", map.get("NID")+"");
			time_map.put("md", map.get("MD"));
			list_temp.add(time_map);
			
			map_time.put(map.get("MD"), map.get("NID"));
		}
		String str = job.getShareCollectDataStr(query_type, begin_time,end_time,map_time);
		
		context.getRecord(outputNode).setValue("data_str", str);
		context.getRecord(outputNode).setValue("time_str", JSONUtils.toJSONString(list_temp));

	}
	
	/**
	 * ��ҳ���������תҳ���Starpathͼ�����ݣ�
	 * ��Ե�������� 
	 */
	public void txn6030012(ShareLogContext context) throws TxnException, DBException
	{
		String query_type = context.getRecord("select-key").getValue(
				"query_type");
		String begin_time = context.getRecord("select-key").getValue(
				"startTime");
		String end_time = context.getRecord("select-key").getValue("endTime");
		String service_targets_id = context.getRecord("select-key").getValue("service_targets_id");
		/** ���в�ѯʱ��Ҫ�Ĳ��� */
		Map<String, String> selectKeyMap = new HashMap<String, String>();
		selectKeyMap.put("beginTime", begin_time);
		selectKeyMap.put("endTime", end_time);
		selectKeyMap.put("queryType", query_type);
		selectKeyMap.put("service_targets_id", service_targets_id);
		
		IndexJob job = new IndexJob();
		List lst_time=job.getTimeString(query_type, begin_time, end_time);
		List list_temp = new ArrayList();
		Map map_time=new HashMap();
		for (int i = 0; i < lst_time.size(); i++) {
			Map time_map = new HashMap();
			Map map = (Map) lst_time.get(i);
			time_map.put("id", map.get("NID")+"");
			time_map.put("md", map.get("MD"));
			list_temp.add(time_map);
			
			map_time.put(map.get("MD"), map.get("NID"));
		}
		String str = job.getShareCollectDataStr(selectKeyMap, map_time);
	//	System.out.println("str="+ str + "\n\ntese= "+ JSONUtils.toJSONString(list_temp));
		
		context.getRecord(outputNode).setValue("data_str", str);
		context.getRecord(outputNode).setValue("time_str", JSONUtils.toJSONString(list_temp));

	}
	
	/**
	 * ��ҳ���������תҳ���Starpathͼ�����ݣ�
	 * ��Ե�������� 
	 */
	public void txn6030013(ShareLogContext context) throws TxnException, DBException
	{
		String qx = context.getRecord("select-key").getValue("qx");
		String nb = context.getRecord("select-key").getValue("nb");
		String wb = context.getRecord("select-key").getValue("wb");
		String iname = context.getRecord("select-key").getValue("iname");
		
		
		
		if(iname==null){
			iname="";
		}
		
		StringBuffer strBuf = new StringBuffer("");
		if(qx.equals("1")){
			strBuf.append("'000'");
		}
		if(nb.equals("1")){
			if(!strBuf.toString().equals("")){
				strBuf.append(",'001'");
			}else{
				strBuf.append("'001'");
			}
		}
		if(wb.equals("1")){
			if(!strBuf.toString().equals("")){
				strBuf.append(",'002'");
			}else{
				strBuf.append("'002'");
			}
		}
		
		String strCon="";
		
		if(!strBuf.toString().equals("")){
			strCon = "("+strBuf.toString()+")";
		}
		
		System.out.println("qx="+qx+"\nstrCon="+strCon);
		ResourceBundle bundle = ResourceBundle.getBundle("index");
		String rootPath = bundle.getString("root_path");
		
		ZwtJsonCreat jsdata = new ZwtJsonCreat();
		try {
			String strType="";
			if(iname!=null&&!iname.equals("")){
				strType="all";
			}else{
				strType="part";
			}
			
			HashMap mapData = jsdata.getForceMapdata("part",strCon,iname);
			
			context.getRecord(outputNode).setValue("strNodes", mapData.get("nodes").toString());
			context.getRecord(outputNode).setValue("strLinks", mapData.get("links").toString());
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//IndexJob jj = new IndexJob();
		//jj.bulidForceIndexData(rootPath, strCon);
		//jj.bulidForceIndexData("E:/workspace_bjdjgz/bjgs_exchange_fb");

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

	public String calMoM(String thisMon, String lastMon)
	{
		String result = "";
		BigDecimal thisMonNum = new BigDecimal(thisMon);
		BigDecimal lastMonNum = new BigDecimal(lastMon);
		BigDecimal divideNum = new BigDecimal(lastMon.equals("0") ? "1"
				: lastMon);
		if (thisMonNum.subtract(lastMonNum).doubleValue() == 0) {
			return "0";
		}
		result = thisMonNum.subtract(lastMonNum)
				.divide(divideNum, 4, BigDecimal.ROUND_HALF_UP)
				.multiply(new BigDecimal(100)).stripTrailingZeros()
				.toPlainString();
		return result;
	}

	public static void main(String[] args) throws TxnException
	{
		TxnShareLogStatistics t = new TxnShareLogStatistics();
		System.out.println(MathUtils.percentage("3", "82812", 6));
	}
}
