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
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnShareLogStatistics.class,
														ShareLogContext.class);

	// 数据表名称
	private static final String	TABLE_NAME		= "share_log";

	// 查询列表
	private static final String	ROWSET_FUNCTION	= "select share_log list";

	// 查询记录
	private static final String	SELECT_FUNCTION	= "select one share_log";

	// 修改记录
	private static final String	UPDATE_FUNCTION	= "${mod.function.update}";

	// 增加记录
	private static final String	INSERT_FUNCTION	= "${mod.function.insert}";

	// 删除记录
	private static final String	DELETE_FUNCTION	= "${mod.function.delete}";

	private static CodeMap		codeZh			= PublicResource
														.getCodeFactory();

	/**
	 * 构造函数
	 */
	public TxnShareLogStatistics()
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
	 * 删除共享日志信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6010005(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 删除记录的主键列表 VoShareLogPrimaryKey primaryKey[] = context.getPrimaryKeys(
		// inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 跳转到共享日志信息页面
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6010006(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryShareLog", context, inputNode, outputNode);

	}

	// ----------------------分割线-------------------------
	// 601开头的交易是日志查询，602开头的是统计统计

	public void txn6020005(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//构造统计粒度 json数据
		List indexlist=new ArrayList();
		Map map =new HashMap();
		map.put("title", "月");
		map.put("key", "month");		
		indexlist.add(map);
		Map map1 =new HashMap();
		map1.put("title", "日");
		map1.put("key", "day");		
		indexlist.add(map1);
		context.setValue("query_index", JsonDataUtil.toJSONString(indexlist));
		String query_index=context.getSelectKey().getValue("query_index");
		if(StringUtils.isBlank(query_index)){
			context.getSelectKey().setValue("query_index", "day");
		}
		
		// 构造服务对象 json数据
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
		Recordset rs = codeMap.lookup(svrTarcontext, "资源管理_服务对象类型");
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
	 * 服务未用情况统计
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
		String nouse_num = "月";
		if (StringUtils.isNotBlank(query_index)) {
			if (query_index.equals("month")) {
				nouse_num = "月";
			} else {
				nouse_num = "天";
			}
		}
		// 如果为图表模式
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
	 * 服务异常情况
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6020023(ShareLogContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		//System.out.println("23begin----"+context);
		//构造统计粒度 json数据
		List indexlist=new ArrayList();
		Map map2 =new HashMap();
		map2.put("title", "年");
		map2.put("key", "year");		
		indexlist.add(map2);
		Map map =new HashMap();
		map.put("title", "月");
		map.put("key", "month");		
		indexlist.add(map);
		Map map1 =new HashMap();
		map1.put("title", "日");
		map1.put("key", "day");		
		indexlist.add(map1);
		context.setValue("query_index", JsonDataUtil.toJSONString(indexlist));
		//设置默认选中为month
		context.getSelectKey().setValue("query_index", "month");
		//设置默认展示方式为图
		//context.getSelectKey().setValue("show_type", "chart");
		// 构造服务对象 json数据
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
		Recordset rs = codeMap.lookup(svrTarcontext, "资源管理_服务对象类型");
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
	 * 服务异常情况统计
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
		// 如果为图表模式
		if (StringUtils.isNotBlank(show_type)) {
			if (show_type.equals("chart")) {
				if (StringUtils.isBlank(query_date)) {
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MONTH, -1);
					DateFormat df = new SimpleDateFormat("yyyy-MM");
					query_date = df.format(calendar.getTime());
					//getDateRegionRemark对年的处理有误，当查询日期为空时默认查上一个月的数据
				}
				String showdate=DateUtil.getDateRegionRemark(query_date, query_index);
				if("year".equals(query_index)){
					//getDateRegionRemark对年的处理有误
					if(showdate.length()>5){
						showdate=showdate.substring(0,7).replace("-", "年")+"月";
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
						error_code = "未知错误";
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
						error_code = "未知错误";
					}
					db.setValue("error_name", error_code);
					//System.out.println(db);
				}
			}
		}
		//System.out.println("07end----"+context);
	}

	/**
	 * 服务未用情况统计
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
		String unit = "条";
		if (StringUtils.isNotBlank(query_index)) {
			if (query_index.equals("type_number")
					|| query_index.equals("target_number")) {
				unit = "次";
			} else {
				unit = "条";
			}
		}
		// 如果为图表模式
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
						query_index = "type_quantity"; // 默认显示 响应请求次数(按类型分布)
					}

					if (StringUtils.isNotBlank(query_index)) {
						dataMap.put("count", db.getValue("count"));
					}

					if (query_index.equals("type_number")
							|| query_index.equals("type_quantity")) {
						dataMap.put(
								"type",
								codeZh.getCodeDesc(context, "资源管理_服务对象类型",
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
	 * 服务异常情况统计
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
		// 查询出选择时间段 包括当期统计数据
		table.executeFunction("queryShareLogMonthInfo", context, inputNode,
				outputNode);
		// 取传入参数
		DataBus db_select = context.getRecord("select-key");
		String show_type = db_select.getValue("show_type");
		String query_index = db_select.getValue("query_index");
		String query_date = db_select.getValue("query_date");
		DataBus db = context.getRecord(outputNode);
		db_select.put("date_remark",
				DateUtil.getDateRegionRemark(query_date, query_index));
		// 如果当期有数据
		if (!db.isEmpty()) {
			// 查询所选择时间的具体数据
			Attribute.setPageRow(context, "charts", -1);
			table.executeFunction("queryShareLogMonthMainInfo", context,
					inputNode, "charts");
			// // 如果为图表模式
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
			// 查询出选择时间段 该用户的最后访问时间和访问错误的次数
			table.executeFunction("queryShareLogExpeAndTime", context,
					inputNode, "expeCount");
			if (context.getRecord("expeCount").isEmpty()) {
				db.setValue("error_rate", "0");
				db.setValue("last_use_time", "无");
			} else {
				Recordset rs_expt = context.getRecordset("expeCount");
				if (rs_expt.size() == 1) {
					DataBus db_expt = rs_expt.get(0);
					// 查到的记录为最后访问时间
					if (db_expt.getValue("log_type").equals("1")) {
						db.setValue("last_use_time",
								db_expt.getValue("last_use_time"));
						db.setValue("first_use_time",
								db_expt.getValue("first_use_time"));
						db.setValue("error_count", "0");
						db.setValue("error_rate", "0");
					} else {// 否则为出错次数
						db.setValue("error_count",
								db_expt.getValue("error_count"));
						String error_rate = MathUtils.percentage(
								db_expt.getValue("error_count"),
								db.getValue("sum_count"), 6);
						db.setValue("error_rate", error_rate);
					}
				} else {
					DataBus db_expt1 = rs_expt.get(0);// 最后访问时间
					DataBus db_expt2 = rs_expt.get(1);// 出错次数
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
			// 如果时间粒度为月
			if (StringUtils.isBlank(query_index) || !query_index.equals("year")) {
				// //查询上月数据
				DateFormat df = new SimpleDateFormat("yyyy-MM");
				// 查询环比数据
				db_select.setValue("is_last", "0");
				table.executeFunction("queryShareLogMonthInfo", context,
						inputNode, "lastMonth");
				DataBus db_last = context.getRecord("lastMonth");
				if (!db_last.isEmpty()) {
					db.setValue("last_sum_num", db_last.getValue("sum_num"));// 上月共享数据量
					String rate = this.calMoM(db.getValue("avg_num"),
							db_last.getValue("avg_num"));
					db.setValue("num_rate", rate);// 环比
					db.setValue("avg_num",
							MathUtils.toCountUp(db.getValue("avg_num"), 0));
					db.setValue("avg_time",
							MathUtils.toCountUp(db.getValue("avg_time"), 2));

				} else {
					db.setValue("last_sum_num", "0");// 上月共享数据量
					String rate = this.calMoM(db.getValue("avg_num"), "0");
					db.setValue("num_rate", rate);// 环比
					db.setValue("avg_num",
							MathUtils.toCountUp(db.getValue("avg_num"), 0));
					db.setValue("avg_time",
							MathUtils.toCountUp(db.getValue("avg_time"), 2));
				}

				// 查询出选择时间段 包括同比上期的统计数据
				db_select.setValue("is_last", "1");
				table.executeFunction("queryShareLogMonthInfo", context,
						inputNode, "lastTime");
				DataBus lastTime = context.getRecord("lastTime");
				// 如果同期并无数据
				if (lastTime.isEmpty()) {
					db.setValue("lastyear_sum_num", "0");
					String rate = this.calMoM(db.getValue("avg_num"), "0");
					db.setValue("lastyear_num_rate", rate);// 同比
				} else {
					db.setValue("lastyear_sum_num",
							lastTime.getValue("sum_num"));
					String rate = this.calMoM(db.getValue("avg_num"),
							lastTime.getValue("avg_num"));
					db.setValue("lastyear_num_rate", rate);// 同比
				}
			} else {
				// 查询上年数据
				// 查询出选择时间段 包括同比上期的统计数据
				db_select.setValue("is_last", "1");
				table.executeFunction("queryShareLogMonthInfo", context,
						inputNode, "lastMonth");
				DataBus db_last = context.getRecord("lastMonth");
				if (!db_last.isEmpty()) {
					db.setValue("last_sum_num", db_last.getValue("sum_num"));// 上期共享数据量
					db.setValue("lastyear_sum_num", db_last.getValue("sum_num"));
					String rate = this.calMoM(db.getValue("avg_num"),
							db_last.getValue("avg_num"));
					db.setValue("num_rate", rate);// 环比
					db.setValue("lastyear_num_rate", rate);// 同比
				} else {
					db.setValue("last_sum_num", "0");// 上期共享数据量
					db.setValue("lastyear_sum_num", "0");
					String rate = this.calMoM(db.getValue("avg_num"), "0");
					db.setValue("num_rate", rate);// 环比
					db.setValue("lastyear_num_rate", rate);// 同比
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
	 * 首页服务对象跳转页面的Starpath图的数据，
	 * 针对单服务对象 
	 */
	public void txn6030012(ShareLogContext context) throws TxnException, DBException
	{
		String query_type = context.getRecord("select-key").getValue(
				"query_type");
		String begin_time = context.getRecord("select-key").getValue(
				"startTime");
		String end_time = context.getRecord("select-key").getValue("endTime");
		String service_targets_id = context.getRecord("select-key").getValue("service_targets_id");
		/** 所有查询时需要的参数 */
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
	 * 首页服务对象跳转页面的Starpath图的数据，
	 * 针对单服务对象 
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
