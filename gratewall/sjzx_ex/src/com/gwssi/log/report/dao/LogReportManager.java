package com.gwssi.log.report.dao;

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
import org.apache.log4j.Logger;
import org.jfree.data.category.CategoryDataset;


import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.template.freemarker.FreemarkerUtil;

import com.f1j.swing.p3;
import com.f1j.swing.ss.p4;
import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.util.MathUtils;
import com.gwssi.dw.dq.ChartDataFactory;

public class LogReportManager
{
	protected static Logger		log			= TxnLogger
													.getLogger(LogReportManager.class
															.getName());

	public static final String	DB_CONFIG	= "systemReport";

	public DBOperation			operation	= null;

	public ResourceBundle		sqlFactory	= ResourceBundle
													.getBundle(DB_CONFIG);

	private String				totalUse;

	private String[]			workdays;

	private int					lastMonth_Year;

	private int					lastMonth;

	public void CreateReport(int year, int month, String fileName)
			throws DBException
	{
		System.out.println("------���뽨����111-------");
		//this.setWorkDay(year, month);
		Map dataMap = new HashMap();
		long begin = System.currentTimeMillis();
      
		System.out.println("---ִ�����----");
		dataMap.putAll(this.getPartOneData(year, month));
		dataMap.putAll(this.getPartTwoData(year, month));
		dataMap.putAll(this.getPartThreeData(year, month));
//		dataMap.putAll(this.getFullText(year, month));
//		dataMap.putAll(this.getImageMap(year, month));
//		dataMap.putAll(this.getSystemTotal(year, month));
//		dataMap.putAll(this.getPrincipalBusiness(year, month));
//		dataMap.putAll(this.getPartOne(year, month));
//		dataMap.putAll(this.getCollectData(year, month));
//		dataMap.putAll(this.getFuncTop5(year, month));
//		dataMap.putAll(this.getBlackData(year, month));
//		dataMap.putAll(this.getCarReport(year, month));
//		dataMap.putAll(this.getPartThreeMap(year, month));
		FreemarkerUtil f = new FreemarkerUtil();
		f.exportWord("log_report.xml", fileName, dataMap);
		System.out.println("ϵͳʹ�ñ��洴����ʱ:" + (System.currentTimeMillis() - begin));
	}
	
	
	
	/**
	 * ��װ��һ�½ڵ����ݣ�����Map��ʽ����
	 * ������������ɴ˲��ֵ����ݷ�װ
	 * @param year
	 * @param month
	 * @return map
	 * @throws DBException
	 * @author Alex
	 */
	private Map getPartOneData(int year, int month) throws DBException
	{
		Map map = new HashMap();
		operation = DBOperationFactory.createTimeOutOperation();

		String sqlstr = sqlFactory.getString("p1totalData");
		String[] params = { year + "", month + "" };
		sqlstr = replaceParam(sqlstr, params);

		List oracledata = operation.select(sqlstr);
		if (!oracledata.isEmpty()) {

			map = (Map) oracledata.get(0);
			map.put("p1year", getResult(map, "YEAR"));

			map.put("p1month", getResult(map, "MONTH"));
			map.put("p1year1", getResult(map, "YEAR"));

			map.put("p1monthdi", getResult(map, "MONTH"));
			map.put("p1sharedata", getResult(map, "P1"));
			map.put("p1indata", getResult(map, "P2"));
			map.put("p1quxianfenju", getResult(map, "P3"));
			map.put("p1shareoutdata", getResult(map, "P4"));
			map.put("p1insertmonth", getResult(map, "P5"));
			String value=getResult(map, "P6");
			String valString=getResult(map, "P6").replace("-", "");
			if(value.indexOf("-")>-1){
				map.put("p1baifenbi", "����"+valString);
			}
			else {
				map.put("p1baifenbi", "����"+valString);
			}
			
			map.put("p1colletdata", getResult(map, "P7"));
			map.put("p1collectindata", getResult(map, "P8"));
			map.put("p1collectquxianfenju", getResult(map, "P9"));
			map.put("p1collectoutdata", getResult(map, "P10"));
			map.put("p1sharecount", getResult(map, "P11"));
			map.put("p1sharediaoyongdata", getResult(map, "P12"));
			map.put("p1sharemostdata", getResult(map, "P13"));
			map.put("p1sharecishu", getResult(map, "P14"));
			map.put("p1shareliangda", getResult(map, "P15"));
			map.put("p1collectcount", getResult(map, "P16"));
			map.put("p1collectdiaoyong", getResult(map, "P17"));
			map.put("p1collectzidong", getResult(map, "P18"));
			map.put("p1collectshoudong", getResult(map, "P19"));
			map.put("p1collectdanci", getResult(map, "P20"));
			map.put("p1collectbingfa", getResult(map, "P21"));
			map.put("p1time", getResult(map, "P22"));
		}
		return map;
	}
	
	/**
	 * ��װ�ڶ��½ڵ����ݣ�����Map��ʽ���� ��Ҧ�졿��ɴ˲��ֵ����ݷ�װ
	 * 
	 * @param year
	 * @param month
	 * @return map
	 * @throws DBException
	 * @author Alex
	 */
	private Map getPartTwoData(int year, int month) throws DBException
	{
		Map dataMap = new HashMap();
		operation = DBOperationFactory.createTimeOutOperation();
		String p1ParaSql = sqlFactory.getString("p1ParaSql");
		String[] params = { year + "", month + ""};
		p1ParaSql = replaceParam(p1ParaSql, params);
		List listPara = operation.select(p1ParaSql);
		Map paraMap = new HashMap();
		if (!listPara.isEmpty()) {
			paraMap = (Map) listPara.get(0);
			dataMap.put("p2DataDourceNum", getResult(paraMap, "P1"));
			dataMap.put("p2CollectTaskNum", getResult(paraMap, "P2"));
			dataMap.put("p2ETLNum", getResult(paraMap, "P3"));
			dataMap.put("p2WebServiceNum", getResult(paraMap, "P4"));
			dataMap.put("p2OtherTaskNum", getResult(paraMap, "P5"));
			
			dataMap.put("p2MonthCollectNum", getResult(paraMap, "P6"));
			dataMap.put("p2MonthColDataNum", getResult(paraMap, "P7"));
			dataMap.put("p2MonthDataSum", getResult(paraMap, "P8"));
			dataMap.put("p2DataGrowthMoM", getResult(paraMap, "P9").replace("-", ""));
			if(getResult(paraMap, "P9").indexOf(String.valueOf('-')) == -1){
				dataMap.put("p2MoMVary", "����");
			}else{
				dataMap.put("p2MoMVary", "����");			
			}			
			dataMap.put("p2DataGrowthFirst", getResult(paraMap, "P10"));
			dataMap.put("p2FirstDayGrowth", getResult(paraMap, "P11"));
			dataMap.put("p2DayAveragePercent", getResult(paraMap, "P12"));
			
			dataMap.put("p2DataGrowthSecond", getResult(paraMap, "P13"));
			dataMap.put("p2DataGrowthThird", getResult(paraMap, "P14"));
			dataMap.put("p2SecondDayGrowth", getResult(paraMap, "P15"));
			dataMap.put("p2ThirdDayGrowth", getResult(paraMap, "P16"));
		
		}		
		String p1TableSql = sqlFactory.getString("p1TableSql");
		p1TableSql = replaceParam(p1TableSql, params);
		List listTable= operation.select(p1TableSql);
		List list1=new ArrayList();		
		int tasksum=0;
		int collectsum=0;
		int monthgrowth=0;
		int num=0;
		if (!listTable.isEmpty()) {
			for (int i = 0; i < listTable.size(); i++) {
				Map map=new HashMap();
				Map temp = new HashMap();
				temp = (Map) listTable.get(i);
				num=i+1;
				map.put("t1Num", num+"");
				map.put("t1Type", getResult(temp, "P1"));
				map.put("t1DataSource", getResult(temp, "P2"));
				map.put("t1TaskNum", getResult(temp, "P3"));
				map.put("t1CollectNum", getResult(temp, "P4"));
				map.put("t1MonthGrowth", getResult(temp, "P5"));
				tasksum=tasksum+Integer.parseInt(getResult(temp, "P3"));
				collectsum=collectsum+Integer.parseInt(getResult(temp, "P4"));
				monthgrowth=monthgrowth+Integer.parseInt(getResult(temp, "P5"));
				list1.add(map);
			}
		}
		dataMap.put("t1TaskSum", tasksum+"");
		dataMap.put("t1CollectSum", collectsum+"");
		dataMap.put("t1MonthGrowthSum", monthgrowth+"");
		dataMap.put("list1", list1);
		return dataMap;
		
	}
	
	/**
	 * ��װ�����½ڵ����ݣ�����Map��ʽ����
	 * 
	 * @param year
	 * @param month
	 * @return map
	 * @throws DBException
	 * @author Alex
	 */
	private Map getPartThreeData(int year, int month) throws DBException
	{
		Map map = new HashMap();
		String sqlString = "";
		//���ݲ���
		operation = DBOperationFactory.createTimeOutOperation();
		sqlString = sqlFactory.getString("p3ShareData");
		String[] params = new String[] { year + "", month + "" };
		sqlString = replaceParam(sqlString, params);
		
		Map tmp = operation.selectOne(sqlString);
		//�����µ�һ�ڲ���������װ
		String value = getResult(tmp, "P1");
		map.put("p3inSystemNum", value);
		value = getResult(tmp, "P2");
		map.put("p3outSystemNum", value);
		value = getResult(tmp, "P3");
		map.put("p3areaSystemNum", value);
		value = getResult(tmp, "P4");
		map.put("p3userNum", value);
		value = getResult(tmp, "P5");
		map.put("p3shareServiceNum", value);
		value = getResult(tmp, "P6");
		map.put("p3WebServiceNum", value);
		value = getResult(tmp, "P7");
		map.put("p3searchNum", value);
		value = getResult(tmp, "P8");
		map.put("p3otherServiceNum", value);
		value = getResult(tmp, "P9");
		map.put("p3totalData", value);
		value = getResult(tmp, "P10");
		if(value.indexOf("-")>-1){
			map.put("p3percentage", "����"+value.replace("-", ""));
		}else {
			map.put("p3percentage", "����"+value);
		}
		
		
		value = getResult(tmp, "P11");
		map.put("p3serviceNum1", value);
		value = getResult(tmp, "P12");
		map.put("p3serviceNum2", value);
		value = getResult(tmp, "P13");
		map.put("p3serviceNum3", value);
		value = getResult(tmp, "P14");
		map.put("p3percentage2", value);
		//�����µڶ��ڲ���������װ
		value = getResult(tmp, "P15");
		map.put("p3notUserNum", value);
		value = getResult(tmp, "P16");
		map.put("p3notUserNumforYear", value);
		value = getResult(tmp, "P17");
		map.put("p3percentage3", value);
		value = getResult(tmp, "P18");
		map.put("p3yearNum", value);
		value = getResult(tmp, "P19");
		map.put("p3monthNum", value);
		//�����µ�һ�ڱ��ͷ�����ܼ�������װ
		map.put("p3curMonth", month+"");
		map.put("p3lastMonth", getLastMonth(month)+"");
		
		
		
		StringBuffer sqlUnion = new StringBuffer("");
		sqlString = sqlFactory.getString("t2getTableShareServiceData1");
		sqlString = replaceParam(sqlString, params);
		sqlUnion.append(sqlString);
		sqlString = sqlFactory.getString("t2getTableShareServiceData2");
		sqlString = replaceParam(sqlString, params);
		sqlUnion.append(" "+sqlString);
		
		
		List tableOne = operation.select(sqlUnion.toString());
		ArrayList t2 = new ArrayList();
		int totalp3 = 0;
		int totalp4 = 0;
		int totalp5 = 0;
		int totalp6 = 0;
		int totalp7 = 0;
		int totalp8 = 0;
		for (int i = 0; i < tableOne.size(); i++) {
			Map tmpMap = new HashMap();
			tmpMap = (Map)tableOne.get(i);
			tmpMap.put("index", i+1);
			value = getResult(tmpMap, "P1");
			tmpMap.put("user_type", value);
			value = getResult(tmpMap, "P2");
			tmpMap.put("user", value);
			value = getResult(tmpMap, "P3");
			tmpMap.put("service_num", value);
			value = getResult(tmpMap, "P4");
			tmpMap.put("huan_percentage", value);
			value = getResult(tmpMap, "P5");
			tmpMap.put("request_num1", value);
			value = getResult(tmpMap, "P6");
			tmpMap.put("return_num1", value);
			value = getResult(tmpMap, "P7");
			tmpMap.put("request_num2", value);
			value = getResult(tmpMap, "P8");
			tmpMap.put("return_num2", value);
			t2.add(tmpMap);
			
			totalp3 = totalp3+Integer.parseInt(getResult(tmpMap, "P3"));
			totalp4 = totalp4+Integer.parseInt(getResult(tmpMap, "P4"));
			totalp5 = totalp5+Integer.parseInt(getResult(tmpMap, "P5"));
			totalp6 = totalp6+Integer.parseInt(getResult(tmpMap, "P6"));
			totalp7 = totalp7+Integer.parseInt(getResult(tmpMap, "P7"));
			totalp8 = totalp8+Integer.parseInt(getResult(tmpMap, "P8"));	
		}
		map.put("p3total_service_num", totalp3);
		map.put("p3total_huan_percentage", totalp4);
		map.put("p3total_current_month_request_num", totalp5);
		map.put("p3total_current_month_return_num", totalp6);
		map.put("p3total_last_month_request_num", totalp7);
		map.put("p3total_last_month_return_num", totalp8);
		map.put("t2", t2);
		
		//��װ���������ݱ�
		sqlString = sqlFactory.getString("t3getTableNotUseServiceData");
		sqlString = replaceParam(sqlString, params);
		List tableOne2 = operation.select(sqlString);
		ArrayList t3 = new ArrayList();
		for (int i = 0; i < tableOne2.size(); i++) {
			Map tmpMap = new HashMap();
			tmpMap = (Map)tableOne2.get(i);
			tmpMap.put("index", i+1);
			value = getResult(tmpMap, "P1");
			tmpMap.put("service_name", value);
			value = getResult(tmpMap, "P2");
			tmpMap.put("share_service_name", value);
			value = getResult(tmpMap, "P3");
			tmpMap.put("create_date", value);
			value = getResult(tmpMap, "P4");
			tmpMap.put("not_use_month", value);
			if(i==0){
				System.out.println("@@@@@@@@@@@@@@value=="+value);
				String p3dayNum=value.substring(value.indexOf("��")+1);
				System.out.println("@@@@@@@@@@@@@@p3dayNum=="+p3dayNum);
				map.put("p3dayNum", p3dayNum.replace("��", ""));
			}
			
			t3.add(tmpMap);
		}
		map.put("t3", t3);
		return map;
	}

	private void setWorkDay(int pyear, int pmonth) throws DBException
	{
		String sqlstr = sqlFactory.getString("p10Workday2M");
		operation = DBOperationFactory.createTimeOutOperation();
		Calendar calendar = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyyMM");
		int lastMonthYear = 0;
		int lastMonth = 0;
		try {
			calendar.setTime(df.parse(pyear + "" + pmonth));
			calendar.add(Calendar.MONTH, -1);
			lastMonthYear = calendar.get(Calendar.YEAR);
			lastMonth = calendar.get(Calendar.MONTH) + 1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String[] params = { pyear + "", lastMonthYear + "", pmonth + "",
				lastMonth + "" };
		sqlstr = replaceParam(sqlstr, params);
		List tableOne = operation.select(sqlstr);
		Map tmp = new HashMap();
		String[] workday = new String[2];
		if (!tableOne.isEmpty()) {
			for (int i = 0; i < tableOne.size(); i++) {
				tmp = (Map) tableOne.get(i);
				workday[i] = getResult(tmp, "DAYS");
			}
			if (workday.length == 0) {
				for (int i = 0; i < workday.length; i++) {
					workday[i] = "21";
				}
			}
		}
		setWorkdays(workday);
		this.lastMonth = lastMonth;
		this.lastMonth_Year = lastMonthYear;
	}

	/**
	 * ͼƬ����
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws DBException
	 */
	private Map getImageMap(int year, int month) throws DBException
	{

		Map map = new HashMap();
		operation = DBOperationFactory.createTimeOutOperation();
		Calendar calendar = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyyMM");
		int lastMonthYear = 0;
		int lastMonth = 0;
		try {
			calendar.setTime(df.parse(year + "" + month));
			calendar.add(Calendar.MONTH, -1);
			lastMonthYear = calendar.get(Calendar.YEAR);
			lastMonth = calendar.get(Calendar.MONTH) + 1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// ʹ�ô���TOP5
		String sql = replaceParam(sqlFactory.getString("pnum_top5"),
				new String[] { year + "", month + "" });
		List list = operation.select(sql);
		String[] series = new String[] { year + "��" + month + "��",
				lastMonthYear + "��" + lastMonth + "��" };
		FreemarkerUtil fUtil = new FreemarkerUtil();
		if (!list.isEmpty()) {
			String[] categorys = new String[list.size()];
			Double[][] values = new Double[2][list.size()];
			Double[] value1 = new Double[list.size()];
			Double[] value2 = new Double[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map data = (Map) list.get(i);
				categorys[i] = getResult(data, "AREA_NAME");
				value1[i] = new Double(getResult(data, "USER_NUM"));
				value2[i] = new Double(getResult(data, "LAST_MONTH_NUM"));

			}
			values[0] = value1;
			values[1] = value2;
			CategoryDataset dataset = ChartDataFactory.createDataset(series,
					categorys, values);
			String title = year + "��" + month + "��ʹ�ô���ǰ������(��)";
			String path = fUtil.createBarChart(dataset, title, "", "",
					"pnumtop5.png", true);
			map.put("image_sum_top5", fUtil.getImageStr(path));
		}
		// ���ش���TOP5
		sql = replaceParam(sqlFactory.getString("download_top5"), new String[] {
				year + "", month + "" });
		list = operation.select(sql);
		series = new String[] { year + "��" + month + "��",
				lastMonthYear + "��" + lastMonth + "��" };
		if (!list.isEmpty()) {
			String[] categorys = new String[list.size()];
			Double[][] values = new Double[2][list.size()];
			Double[] value1 = new Double[list.size()];
			Double[] value2 = new Double[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map data = (Map) list.get(i);
				categorys[i] = getResult(data, "AREA_NAME");
				value1[i] = new Double(getResult(data, "USER_NUM"));
				value2[i] = new Double(getResult(data, "LAST_MONTH_NUM"));

			}
			values[0] = value1;
			values[1] = value2;
			CategoryDataset dataset = ChartDataFactory.createDataset(series,
					categorys, values);
			String title = year + "��" + month + "�����ش���ǰ������(��)";
			String path = fUtil.createBarChart(dataset, title, "", "",
					"down_top5.png", true);
			map.put("image_down_top5", fUtil.getImageStr(path));
		}

		// ONLINE��½����
		sql = replaceParam(sqlFactory.getString("online_top5"), new String[] {
				year + "", month + "" });
		list = operation.select(sql);
		series = new String[] { year + "��" + month + "��" };
		if (!list.isEmpty()) {
			String[] categorys = new String[list.size()];
			Double[][] values = new Double[1][list.size()];
			Double[] value = new Double[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map data = (Map) list.get(i);
				categorys[i] = getResult(data, "AREA_NAME");
				value[i] = new Double(getResult(data, "USER_NUM"));
			}
			values[0] = value;
			CategoryDataset dataset = ChartDataFactory.createDataset(series,
					categorys, values);
			String title = month + "�µ�½�û������";
			String path = fUtil.createLineChart(dataset, title, "��",
					"onlineTop5.png");
			map.put("image_online", fUtil.getImageStr(path));
		}
		log.info("ϵͳʹ�ñ���[ͼƬ����]���ɳɹ�");
		return map;
	}

	/**
	 * С�ͳ�ָ���걨
	 * 
	 * @return
	 * @throws DBException
	 */
	private Map getCarReport(int year, int month) throws DBException
	{
		Map map = new HashMap();
		operation = DBOperationFactory.createTimeOutOperation();
		String sourceSql = sqlFactory.getString("p41CarReport");
		String sourceSql2 = sqlFactory.getString("p41CarReport2");
		String queryStr1 = "";
		String queryStr2 = "";
		if(month>9)
		{
			queryStr1="'"+year + "-"+month+"'";
		}
		else
		{
			queryStr1="'"+year + "-0"+month+"'";
		}
		if(month>9)
		{
			queryStr2="'"+this.lastMonth_Year + "-"+this.lastMonth+"'";
		}
		else
		{
			queryStr2="'"+this.lastMonth_Year + "-0"+this.lastMonth+"'";
		}
		// String[] query1 = { year + "", month + "" };
		String[] query1 = { queryStr1, queryStr1,queryStr1 };
		
		String[] query2 = { queryStr2 };
		
		
		String sql1 = replaceParam(sourceSql, query1);
		String sql2 = replaceParam(sourceSql2, query2);
		
		List list  = operation.select(sql1);
		String[] value1 = new String[list.size()];
		if (!list.isEmpty()) {
			
			for (int i = 0; i < list.size(); i++) {
				Map data = (Map) list.get(i);
				value1[i] = getResult(data, "CARNUM");
				//System.out.println(value1[i]);
			}
		}
		Map tmp =   operation.selectOne(sql2);
		String valueLastMon = getResult(tmp, "CARNUM");
		String APPLY_NUM = value1[0];
		String YES_NUM = MathUtils.add(value1[1],value1[2]);
		String CarPassPer = MathUtils.divide(MathUtils.mult(YES_NUM, "100"),
				APPLY_NUM, 2);
		
		map.put("p41CarApplyNum", value1[0]);// ������������
		map.put("p41CarAcceptNum", YES_NUM);// ���ͨ��
		map.put("p41CarFailedNum", MathUtils.sub(APPLY_NUM,YES_NUM));// ���ϸ�����
		map.put("p41CarApplyMon", value1[3]);// ��������
		map.put("p41CarEntMatch", value1[4]);// ������ҵƥ��
		map.put("p41CarInstitution", value1[5]);// ������ҵ��λƥ��
		map.put("p41CarUnmatch", MathUtils.sub(MathUtils.sub(value1[3],value1[4]),value1[5]));// ����δƥ��
		
		map.put("p41CarPassPer", CarPassPer);// ����ϸ���
		try {
			if (workdays.length > 0) {
				String rate = calMoM(value1[3],
						valueLastMon, workdays[1],
						workdays[0]);
				if(rate.indexOf("-") > -1){
					map.put("p41CarLoopIncTitle", " �����ȼ���");
					rate = rate.replaceFirst("-", "");
				}else{
					map.put("p41CarLoopIncTitle", " ����������");
				}
				map.put("p41CarLoopInc", rate);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("ϵͳʹ�ñ���[С����]���ɳɹ�");
		return map;
	}

	/**
	 * ��Ҫҵ������
	 * 
	 * @return
	 * @throws DBException
	 */
	private Map getPrincipalBusiness(int year, int month) throws DBException
	{
		Map map = new HashMap();
		try {
			int lastyear = year;
			int lastmonth = month - 1;
			if (month == 1) {
				lastyear--;
				lastmonth = 12;
			}
			String time1 = "";
			if (month > 9) {
				time1 = year + "-" + month;
			} else {
				time1 = year + "-0" + month;
			}
			String time2 = "";
			if (lastmonth > 9) {
				time2 = lastyear + "-" + lastmonth;
			} else {
				time2 = lastyear + "-0" + lastmonth;
			}
			operation = DBOperationFactory.createTimeOutOperation();
			String worddaysThisMonth = workdays[1];
			String worddaysLastMonth = workdays[0];

			String sourceSql2 = sqlFactory.getString("p22MonReportNum2");
			String[] query12 = { time1 };
			String[] query22 = { time2 };
			String sql2 = replaceParam(sourceSql2, query12);
			//System.out.println("��һ����"+sql2);
			List resultList = operation.select(sql2);
			HashMap restltMap1 = new HashMap();
			HashMap restltMap2 = new HashMap();
			for (int i = 0; i < resultList.size(); i++) {
				String Colum = getResult((Map) resultList.get(i), "COLUM");
				String RPT_NUM = getResult((Map) resultList.get(i), "RPT_NUM");
				restltMap1.put(Colum, RPT_NUM);
			}
			sql2 = replaceParam(sourceSql2, query22);
			//System.out.println("�ڶ�����"+sql2);
			resultList = operation.select(sql2);
			for (int i = 0; i < resultList.size(); i++) {
				String Colum = getResult((Map) resultList.get(i), "COLUM");
				String RPT_NUM = getResult((Map) resultList.get(i), "RPT_NUM");
				restltMap2.put(Colum, RPT_NUM);
			}

			// ��ҵ���¿�ҵ����

			map.put("p22OpenNumMon1", restltMap1.get("ent_rpt_mon_num"));
			map.put("p22LoopPer31", calMoM((String) restltMap1
					.get("ent_rpt_mon_num"), (String) restltMap2
					.get("ent_rpt_mon_num"), worddaysThisMonth,
					worddaysLastMonth));
			// ���ʵ��¿�ҵ����
			map.put("p22OpenNumMon2", restltMap1.get("wz_rpt_mon_num"));
			map.put("p22LoopPer32", calMoM((String) restltMap1
					.get("wz_rpt_mon_num"), (String) restltMap2
					.get("wz_rpt_mon_num"), worddaysThisMonth,
					worddaysLastMonth));
			// ����������¿�ҵ����
			map.put("p22OpenNumMon3", restltMap1.get("jg_rpt_mon_num"));
			map.put("p22LoopPer33", calMoM((String) restltMap1
					.get("jg_rpt_mon_num"), (String) restltMap2
					.get("jg_rpt_mon_num"), worddaysThisMonth,
					worddaysLastMonth));
			// ���嵱�¿�ҵ����
			map.put("p22OpenNumMon4", restltMap1.get("gt_rpt_mon_num"));
			map.put("p22LoopPer34", calMoM((String) restltMap1
					.get("gt_rpt_mon_num"), (String) restltMap2
					.get("gt_rpt_mon_num"), worddaysThisMonth,
					worddaysLastMonth));
			// ��ҵ���¿�ҵ���(��Ԫ)
			map.put("p22MoneyDay1", MathUtils.divide(MathUtils.sub(restltMap1.get("ent_rpt_mon_sum"), "0"), "1", 2));
			map.put("p22LoopPer35", calMoM((String) restltMap1
					.get("ent_rpt_mon_sum"), (String) restltMap2
					.get("ent_rpt_mon_sum"), worddaysThisMonth,
					worddaysLastMonth));
			// ���ʵ��¿�ҵ���(��Ԫ)
			map.put("p22MoneyDay2", MathUtils.divide(MathUtils.sub(restltMap1.get("wz_rpt_mon_sum"), "0"), "1", 2));
			map.put("p22LoopPer36", calMoM((String) restltMap1
					.get("wz_rpt_mon_sum"), (String) restltMap2
					.get("wz_rpt_mon_sum"), worddaysThisMonth,
					worddaysLastMonth));
			// ���嵱�¿�ҵ���(��Ԫ)
			map.put("p22MoneyDay4", MathUtils.divide(MathUtils.sub(restltMap1.get("gt_rpt_mon_sum"), "0"), "1", 2));
			map.put("p22LoopPer38", calMoM((String) restltMap1
					.get("gt_rpt_mon_sum"), (String) restltMap2
					.get("gt_rpt_mon_sum"), worddaysThisMonth,
					worddaysLastMonth));
			// ���°�����
			map.put("p22CaseOverMon", restltMap1.get("cas_rpt_mon_num"));
			map.put("p22LoopPer39", calMoM((String) restltMap1
					.get("cas_rpt_mon_num"), (String) restltMap2
					.get("cas_rpt_mon_num"), worddaysThisMonth,
					worddaysLastMonth));
			// ���·�û��Ԫ��
			map.put("p22PenaltyMon", restltMap1.get("cas_rpt_mon_sum"));
			map.put("p22LoopPer40", calMoM((String) restltMap1
					.get("cas_rpt_mon_sum"), (String) restltMap2
					.get("cas_rpt_mon_sum"), worddaysThisMonth,
					worddaysLastMonth));
			// ʳƷ���¼������
			map.put("p22CheckNum1", restltMap1.get("fod_rpt_mon_num"));
			map.put("p22CheckPer1", calMoM((String) restltMap1
					.get("fod_rpt_mon_num"), (String) restltMap2
					.get("fod_rpt_mon_num"), worddaysThisMonth,
					worddaysLastMonth));
			// ʳƷ���²��ϸ���
			map.put("p22UnpassNumMon1", restltMap1.get("no_rpt_mon_num"));
			map.put("p22LoopPer41", calMoM((String) restltMap1
					.get("no_rpt_mon_num"), (String) restltMap2
					.get("no_rpt_mon_num"), worddaysThisMonth,
					worddaysLastMonth));
			// ��Ʒ���¼������
			map.put("p22CheckNum2", restltMap1.get("mds_rpt_mon_num"));
			map.put("p22CheckPer2", calMoM((String) restltMap1
					.get("mds_rpt_mon_num"), (String) restltMap2
					.get("mds_rpt_mon_num"), worddaysThisMonth,
					worddaysLastMonth));
			// ��Ʒ���²��ϸ���
			map.put("p22UnpassNumMon2", restltMap1.get("mno_rpt_mon_num"));
			map.put("p22LoopPer42", calMoM((String) restltMap1
					.get("mno_rpt_mon_num"), (String) restltMap2
					.get("mno_rpt_mon_num"), worddaysThisMonth,
					worddaysLastMonth));
			// ���ߵ���������
			map.put("p22AcceptMon1", restltMap1.get("xb_app_rpt_mon"));
			map.put("p22LoopPer43", calMoM((String) restltMap1
					.get("xb_app_rpt_mon"), (String) restltMap2
					.get("xb_app_rpt_mon"), worddaysThisMonth,
					worddaysLastMonth));
			// ���ߵ��°����
			map.put("p22OverMon1", restltMap1.get("app_rpt_mon_or"));
			map.put("p22LoopPer47", calMoM((String) restltMap1
					.get("app_rpt_mon_or"), (String) restltMap2
					.get("app_rpt_mon_or"), worddaysThisMonth,
					worddaysLastMonth));
			// �ٱ�����������
			map.put("p22AcceptMon2", restltMap1.get("rep_rpt_mon_num"));
			map.put("p22LoopPer44", calMoM((String) restltMap1
					.get("rep_rpt_mon_num"), (String) restltMap2
					.get("rep_rpt_mon_num"), worddaysThisMonth,
					worddaysLastMonth));
			// �ٱ����°����
			map.put("p22OverMon2", restltMap1.get("rep_rpt_mon_or"));
			map.put("p22LoopPer48", calMoM((String) restltMap1
					.get("rep_rpt_mon_or"), (String) restltMap2
					.get("rep_rpt_mon_or"), worddaysThisMonth,
					worddaysLastMonth));
			// ��ѯ����������
			map.put("p22AcceptMon3", restltMap1.get("zx_rpt_mon_num"));
			map.put("p22LoopPer45", calMoM((String) restltMap1
					.get("zx_rpt_mon_num"), (String) restltMap2
					.get("zx_rpt_mon_num"), worddaysThisMonth,
					worddaysLastMonth));
			// ��ѯ���°����
			map.put("p22OverMon3", restltMap1.get("zx_rpt_mon_or"));
			map.put("p22LoopPer49",
					calMoM((String) restltMap1.get("zx_rpt_mon_or"),
							(String) restltMap2.get("zx_rpt_mon_or"),
							worddaysThisMonth, worddaysLastMonth));
			// ���鵱��������
			map.put("p22AcceptMon4", restltMap1.get("jy_rpt_mon_num"));
			map.put("p22LoopPer46", calMoM((String) restltMap1
					.get("jy_rpt_mon_num"), (String) restltMap2
					.get("jy_rpt_mon_num"), worddaysThisMonth,
					worddaysLastMonth));
			// ���鵱�°����
			map.put("p22OverMon4", restltMap1.get("jy_rpt_mon_over"));
			map.put("p22LoopPer50", calMoM((String) restltMap1
					.get("jy_rpt_mon_over"), (String) restltMap2
					.get("jy_rpt_mon_over"), worddaysThisMonth,
					worddaysLastMonth));

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��Ҫҵ���������");
		}
		log.info("ϵͳʹ�ñ���[��Ҫҵ������]���ɳɹ�");
		return map;
	}

	/**
	 * �������岿��
	 * 
	 * @return
	 * @throws DBException
	 */
	private Map getSystemTotal(int year, int month)
	{
		Map map = new HashMap();
		try {
			operation = DBOperationFactory.createTimeOutOperation();
			/** ********��ʼ����********** */
			String useSumthisMon = "";
			String dataSumthisMon = "";
			String sqlstr = sqlFactory.getString("p00totalData");
			String[] params = { year + "", month + "" };
			String totalData = replaceParam(sqlstr, params);
			//System.out.println("22sql = " + totalData);
			Map tmp = operation.selectOne(totalData);
			String value = getResult(tmp, "YEAR");
			map.put("p00currentYear0", value); // ���
			value = getResult(tmp, "MONTH");
			map.put("p00currentMonth0", value); // �·�
			value = getResult(tmp, "YEAR");
			map.put("p00currentYear1", value);
			value = getResult(tmp, "MONTH");
			map.put("p00currentMonth1", value);
			value = getResult(tmp, "DATA_SUM");
			map.put("p00totalData", value); // ���洢����������
			value = getResult(tmp, "DATA_MONTH_ADD"); // ������������
			dataSumthisMon = value;
			if(value.indexOf("-") > -1){
				map.put("p00TotalDataAddTitle", "����");
				value = value.replaceFirst("-", "");
			}else{
				map.put("p00TotalDataAddTitle", "����");
			}
			map.put("p00TotalDataAdd", value);
			value = getResult(tmp, "USE_SUM");
			setTotalUse(value);
			map.put("p00TotalUse", value); // ����ʹ�ô�������
			value = getResult(tmp, "MONTH_USE_SUM");
			useSumthisMon = value;
			map.put("p00MonthUse", value);
			value = getResult(tmp, "DAY_AVG");
			map.put("p00DayAver", value);
			value = getResult(tmp, "MAX_ONLINE");
			map.put("p00MaxOnlineMonth", value);
			value = getResult(tmp, "MAX_ONLINE_TIME");
			map.put("p00Year", value);
			value = getResult(tmp, "INTERFACE_NUM");
			map.put("p00countInterface", value); // �ӿڵ��ô���
			value = getResult(tmp, "USE_INTERFACE");
			map.put("p00useInterface", value); // �ӿ�����
			value = getResult(tmp, "USE_NUM");
			map.put("p00interfaceNum", value);
			value = getResult(tmp, "INTERFACE_DATA");
			map.put("p00datanum", value);
			// ���㻷��
			params[0] = lastMonth_Year + "";
			params[1] = lastMonth + "";
			totalData = replaceParam(sqlstr, params);
			//System.out.println("33sql = " + totalData);
			tmp = operation.selectOne(totalData);
			// ϵͳ�洢������������
			String lastMonth_Add = getResult(tmp, "DATA_MONTH_ADD");
			String resultLoop = calMoM(dataSumthisMon, lastMonth_Add,
					workdays[1], workdays[0]);
			if(resultLoop.indexOf("-") > -1){
				map.put("p00TotaltbTitle", "����");
				resultLoop = resultLoop.replaceFirst("-", "");
			}else{
				map.put("p00TotaltbTitle", "����");
			}
			map.put("p00Totaltb", resultLoop);
			// ϵͳʹ�ô�����������
			String lastUseMonth_Add = getResult(tmp, "MONTH_USE_SUM");
			resultLoop = calMoM(useSumthisMon, lastUseMonth_Add, workdays[1],
					workdays[0]);
			if(resultLoop.indexOf("-") > -1){
				map.put("p00TotalUserTbTitle", "����");
				resultLoop = resultLoop.replaceFirst("-", "");
			}else{
				map.put("p00TotalUserTbTitle", "����");
			}
			
			map.put("p00TotalUserTb", resultLoop);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ϵͳʹ�ñ����������ִ���");
		}
		log.info("ϵͳʹ�ñ���[��������]���ɳɹ�");
		return map;
	}

	/**
	 * ��һ���ֵ�һС�ڣ������������
	 * 
	 * @param year
	 * @param month
	 * @return map
	 * @throws DBException
	 * @author Alex
	 */
	private Map getPartOne(int year, int month) throws DBException
	{
		Map map = new HashMap();
		try {
			String sqlstr = "";
			operation = DBOperationFactory.createTimeOutOperation();
			Map tmp = new HashMap();
			// �����ֲ�ͳ��
			String value = "";
			/** *********ϵͳʹ�������������************** */
			map.put("p10currentYear0", year + "");
			map.put("p10currentMonth0", month + "");

			sqlstr = sqlFactory.getString("p10SystemUseTwoMonth");
			String[] params = new String[] { year + "", month + "",
					this.lastMonth_Year + "", this.lastMonth + "" };
			sqlstr = replaceParam(sqlstr, params);
			List tableOne = operation.select(sqlstr);
			if (!tableOne.isEmpty()) {
				// ȡ���һ���ϼ���
				tmp = (Map) tableOne.get(tableOne.size() - 1);
				if (getResult(tmp, "AREA_NAME").equals("�ϼ�")) {
					// ���ñ���ϵͳ����ʹ�ö��ٴ�
					String monthUse = getResult(tmp, "USER_NUM");
					map.put("p10MonthUse", monthUse);
					// ���������Ϊ�գ����㱾���վ�ϵͳʹ����
					if (StringUtils.isNotBlank(monthUse)) {
						map.put("p10DayAver", MathUtils.divide(monthUse,
								workdays[1], 2));
					}
					String last_monthUse = getResult(tmp, "LAST_MONTH_NUM");
					// ����ϵͳʹ�ô������ϸ��»���
					String valueTmp = calMoM(monthUse, last_monthUse, workdays[1], workdays[0]);
					if(valueTmp.indexOf("-") > -1){
						map.put("p10TotalUserTbTitle", "����");
						valueTmp = valueTmp.replaceFirst("-", "");
					}else{
						map.put("p10TotalUserTbTitle", "����");
					}
					map.put("p10TotalUserTb", valueTmp);
				}
				// ��ʹ�ô���TOP3��ֵ
				for (int ii = 0; ii < tableOne.size(); ii++) {
					tmp = (Map) tableOne.get(ii);
					value = getResult(tmp, "AREA_NAME");
					map.put("p11useTop" + (ii + 1), value);
					map.put("p10useTop" + (ii + 1), value);
				}

				/** *******************�ڶ��µ�һ�� 1ʹ�÷ֲ����**************************** */
				// �������ֲ������� ����ֵ
				for (int ii = 0; ii < tableOne.size(); ii++) {
					int i = ii + 1;
					tmp = (Map) tableOne.get(ii);
					value = getResult(tmp, "AREA_NAME");
					map.put("p11FJName" + i, value);
					String thisMonth = getResult(tmp, "USER_NUM");
					map.put("p11UseCount" + i, thisMonth);
					String lastMonth = getResult(tmp, "LAST_MONTH_NUM");
					map.put("p11LastMonCount" + i, lastMonth);
					map.put("p11LoopDecrease" + i, this.calMoM(thisMonth,
							lastMonth, workdays[1], workdays[0]));
				}
			}

			// �����ֲ���� ����Top3������
			sqlstr = sqlFactory.getString("p10SysUseRateSort");
			params = new String[] { workdays[1], workdays[0], workdays[0],
					year + "", month + "", this.lastMonth_Year + "",
					this.lastMonth + "" };
			sqlstr = replaceParam(sqlstr, params);
			//System.out.println("p10SysUseRateSort:" + sqlstr);
			tableOne = operation.select(sqlstr);
			for (int ii = 0; ii < tableOne.size(); ii++) {
				tmp = (Map) tableOne.get(ii);
				value = getResult(tmp, "AREA_NAME");
				int i = ii + 1;
				map.put("p11increTop" + i, value);
			}
			/** *******************�ڶ��µ�һ��1 ʹ�÷ֲ��������**************************** */

			/** ********* �ڶ��µ�һ��2 ����ʹ�ô���ͳ��************ */
			params = new String[] { "2", year + "", month + "" };
			sqlstr = sqlFactory.getString("p11FuncData");
			sqlstr = replaceParam(sqlstr, params);
			tableOne = operation.select(sqlstr);
			String per = "";
			String name = "";
			for (int ii = 0; ii < tableOne.size(); ii++) {
				tmp = (Map) tableOne.get(ii);
				value = getResult(tmp, "AREA_NAME");
				name = value;
				int i = 1 + ii;
				map.put("p11Row" + i, value);
				value = getResult(tmp, "USER_NUM");
				i = i + 1;
				map.put("p11UseCount2" + i, value);
				value = getResult(tmp, "RATE");
				if ("�򵥲�ѯ".equals(name)) {
					per = value;
				}
				map.put("percentage1" + ii, value);
			}
			map.put("p11CGSearchPercentage", per);
			/** ********* �ڶ��µ�һ��2 ����ʹ�ô���ͳ�ƽ���************ */

			/** ********* �ڶ��µ�һ��2 �����ѯ����ʹ�ô�����ϸ************ */
			params[0] = "3";
			sqlstr = sqlFactory.getString("p11FuncData");
			sqlstr = replaceParam(sqlstr, params);
			tableOne = operation.select(sqlstr);
			for (int ii = 0; ii < tableOne.size(); ii++) {
				tmp = (Map) tableOne.get(ii);
				int i = 1 + ii;
				value = getResult(tmp, "AREA_NAME");
				if (i != tableOne.size()) {
					String[] temp = value.split(">");
					if (temp.length > 0) {
						map.put("p11ProName" + i,
								value.split(">")[temp.length - 1].trim());
					} else {
						map.put("p11ProName" + i, value);
					}
				}
				value = getResult(tmp, "USER_NUM");
				map.put("p11LastSearchC" + i, value);
				value = getResult(tmp, "LAST_MONTH_NUM");
				map.put("p11SearchCount" + i, value);
				value = getResult(tmp, "RATE");
				i = i + 13;
				map.put("percentage" + i, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ϵͳʹ�ò��ֵ�һ�´���");
		}
		log.info("ϵͳʹ�ñ���[��һ��]���ɳɹ�");
		return map;
	}

	/**
	 * ���ļ���
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws DBException
	 * @author Alex
	 */
	private Map getFullText(int year, int month) throws DBException
	{
		Map map = new HashMap();
		map.put("p10FullTextSearch", "1000");
		map.put("p1012315", "12315");
		map.put("p10SearchCount", "102");
		map.put("p10Percentage1", "40");
		map.put("p10MobileLaw", "300");
		map.put("p10Percentage2", "30");
		map.put("p10DataCount1", "300");
		map.put("p10Percentage3", "30");

		return map;
	}

	/**
	 * ��ȡ���չ���ʹ�ô����������
	 * 
	 * @param year
	 * @param month
	 * @return map
	 * @throws DBException
	 * @author Alex
	 */
	private Map getFuncTop5(int year, int month) throws DBException
	{
		Map map = new HashMap();
		try {
			operation = DBOperationFactory.createTimeOutOperation();
			String sqlstr = sqlFactory.getString("p10SystemUse");
			String[] params = { "6", year + "", month + "" };
			sqlstr = replaceParam(sqlstr, params);
			List tableData = operation.select(sqlstr);
			String value = "";
			Map tmp = new HashMap();
			for (int ii = 0; ii < tableData.size(); ii++) {
				tmp = (Map) tableData.get(ii);
				value = getResult(tmp, "AREA_NAME");
				int i = ii + 1;
				map.put("p12FunName" + i, value);
				value = getResult(tmp, "USER_NUM");
				map.put("p12UseCount3" + ii, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ϵͳʹ�ñ��湦��ʹ�ô������д���");
		}
		log.info("ϵͳʹ�ñ���[����ʹ�ô���]���ɳɹ�");
		return map;
	}

	/**
	 * ��ȡ�ӿ�ͳ������
	 * 
	 * @param year
	 * @param month
	 * @return map
	 * @throws DBException
	 * @author Alex
	 */
	private Map getCollectData(int year, int month) throws DBException
	{
		Map map = new HashMap();
		try {
			// ���ݲɼ�����������
			operation = DBOperationFactory.createTimeOutOperation();
			String sqlstr = sqlFactory.getString("p21CollectData");
			String[] params = { "8", year + "", month + "" };
			sqlstr = replaceParam(sqlstr, params);
			List tableData = operation.select(sqlstr);
			String value = "";
			Map tmp = new HashMap();
			int ii = 0;

			for (ii = 0; ii < tableData.size(); ii++) {
				tmp = (Map) tableData.get(ii);
				int i = ii + 1;
				try {
					value = getResult(tmp, "USER_NUM");
					map.put("p21InterfaceC" + i, value);
					value = getResult(tmp, "LAST_MONTH_NUM");
					map.put("p21CollectC" + i, value);
					value = getResult(tmp, "TYPE_USE_NUM");
					if ("".equals(value)) {
						value = "0";
					}
					map.put("p21CollectMon" + i, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			map.put("p21DataSource", ii + "");
			// ͳ������ǰ��������
			sqlstr = sqlFactory.getString("p21CollectTop3");
			sqlstr = replaceParam(sqlstr, params);
			tableData = operation.select(sqlstr);
			String maxMonNum = "";
			if (!tableData.isEmpty()) {
				for (ii = 0; ii < tableData.size(); ii++) {
					tmp = (Map) tableData.get(ii);
					int i = ii + 1;
					value = getResult(tmp, "TYPE_NAME");
					map.put("p21Top" + i + "Mon", value);
					value = getResult(tmp, "TYPE_USE_NUM");
					if (ii == 0) {
						maxMonNum = value;
					}
					if (StringUtils.isBlank(value)) {
						value = "0";
					}
					String dayAvg = MathUtils.divide(value, Integer
							.parseInt(workdays[1])
							* 10000 + "", 2);
					map.put("p21IncreaseDayC" + i, dayAvg);
				}
			}

			// ͳ�Ʊ��º����µĸ��������ܺ�
			String sqlstr1 = sqlFactory.getString("p21CollectTotal");
			String[] params2 = { "8", year + "", month + "", "8" };
			sqlstr = replaceParam(sqlstr1, params2);
			tmp = operation.selectOne(sqlstr);

			// ��ȡ���������ܺ�
			String thisMonTotal = getResult(tmp, "TOTAL");
			map.put("p21ColDataSum", thisMonTotal);// ����Ϸ���
			map.put("p21CollectMon15", thisMonTotal); // ���ײ���

			if (StringUtils.isBlank(thisMonTotal)) {
				thisMonTotal = "1";
			}
			if (StringUtils.isBlank(maxMonNum)) {
				maxMonNum = "0";
			}
			// �����վ�����ռ�������ı���
			map.put("p21IncPerTotal", MathUtils.divide(MathUtils.mult(
					maxMonNum, "100"), thisMonTotal, 2));

			value = getResult(tmp, "INTERFACESUM");
			map.put("p21DataCollect", value); // ����Ϸ��Ľӿ��ܺ�
			map.put("p21InterfaceC15", value); // �ӿ������ܺ�
			value = getResult(tmp, "COLLECTSUM");
			map.put("p21CollectC15", value); // �ɼ������ܺ�
			map.put("p21CollectCount", value); // ����Ϸ��Ĳɼ��ܴ���

			// ���㻷������
			params2[0] = "8";
			params2[1] = lastMonth_Year + "";
			params2[2] = lastMonth + "";
			sqlstr = replaceParam(sqlstr1, params2);
			tmp = operation.selectOne(sqlstr);
			String valueLast = getResult(tmp, "TOTAL");
			String valueLoop = calMoM(thisMonTotal, valueLast, workdays[1],workdays[0]);
			if(valueLoop.indexOf("-") > -1){
				map.put("p21LoopIncreaseTitle", "�����ݣ����ȼ���");
				valueLoop = valueLoop.replaceFirst("-", "");
			}else{
				map.put("p21LoopIncreaseTitle", "�����ݣ���������");
			}
			map.put("p21LoopIncrease", valueLoop);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ϵͳʹ�ñ���ӿ�ͳ�����ݴ���");
		}
		log.info("ϵͳʹ�ñ���[�ӿ�ͳ��]���ɳɹ�");
		return map;
	}

	/**
	 * ��ȡ�����ֺܾ�����ҵ��һ�����޹�˾����Ӧ�����
	 * 
	 * @param year
	 * @param month
	 * @return map
	 * @throws DBException
	 * @author Alex
	 */
	private Map getBlackData(int year, int month) throws DBException
	{

		Map map = new HashMap();
		try {
			operation = DBOperationFactory.createTimeOutOperation();
			String sqlstr = sqlFactory.getString("p42BlackData");
			String[] params = { year + "", month + "" };
			sqlstr = replaceParam(sqlstr, params);
			Map tmp = operation.selectOne(sqlstr);
			if (tmp.size() > 0) {
				String value = "";
				value = getResult(tmp, "BLACK_SUM");
				map.put("p42BlackEnt", value);
				value = getResult(tmp, "ONE_PERSON_SUM");
				map.put("p42InvidualEnt", value);
				value = getResult(tmp, "FDDB_NUM");
				map.put("p42RepreBusiness", value);
				value = getResult(tmp, "FJJG_NUM");
				map.put("p42Branch", value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("---���ƴ���-----");
		}
		log.info("ϵͳʹ�ñ���[�ֺܾ���]���ɳɹ�");
		return map;
	}

	/**
	 * ��ȡ���ݹ������
	 * 
	 * @param year
	 * @param month
	 * @return map
	 * @throws DBException
	 */
	private Map getPartThreeMap(int year, int month) throws DBException
	{
		Map map = new HashMap();
		try {
			int reqTop3Total = 0;
			int retTop3Total = 0;
			int reqNumTotal = 0;
			int retNumTotal = 0;
			String retThisMonth = "";
			String retLastMonth = "";
			// ��ȡ���з����û�
			List names = operation.select(sqlFactory
					.getString("p30Interface_UserName"));
			String[] userName = new String[0];
			if (!names.isEmpty()) {
				userName = new String[names.size()];
				for (int i = 0; i < names.size(); i++) {
					userName[i] = getResult((Map) names.get(i), "AREA_NAME");
				}
			}
			operation = DBOperationFactory.createTimeOutOperation();
			// ������������
			String sqlstr = sqlFactory.getString("p30DataShare");
			String[] params = { year + "", month + "" };
			sqlstr = replaceParam(sqlstr, params);
			List tableData = operation.select(sqlstr);
			String value = "";
			Map tmp = new HashMap();
			for (int i = 0; i < tableData.size(); i++) {
				tmp = (Map) tableData.get(i);
				value = getResult(tmp, "AREA_NAME");
				if (!value.equals("")) {
					int indexNum = getIndex(value, userName);
					if (indexNum != -1) {
						String interNum = getResult(tmp, "USER_NUM");
						String reqNum = getResult(tmp, "LAST_MONTH_NUM");
						String retNum = getResult(tmp, "TYPE_USE_NUM");
						map.put("p30INum" + indexNum, interNum);
						map.put("p30RequestNum" + indexNum, reqNum);
						map.put("p30ReturnNum" + indexNum, retNum);
					}
				}
			}

			// �������ݺϼ�
			sqlstr = sqlFactory.getString("p30SumResult");
			sqlstr = replaceParam(sqlstr, params);
			List sumMonth = operation.select(sqlstr);
			value = "";
			Map tmp0 = new HashMap();
			for (int i = 0; i < sumMonth.size(); i++) {
				tmp0 = (Map) sumMonth.get(i);
				String interNum = getResult(tmp0, "USER_NUM");
				String reqNum = getResult(tmp0, "LAST_MONTH_NUM");
				String retNum = getResult(tmp0, "TYPE_USE_NUM");
				if (reqNum != null && !"".equals(reqNum)) {
					reqNumTotal = Integer.parseInt(reqNum);
				}
				if (retNum != null && !"".equals(retNum)) {
					retNumTotal = Integer.parseInt(retNum);
				}
				retThisMonth = retNum;
				map.put("p30INum31", interNum);
				map.put("p30RequestNum31", reqNum);
				map.put("p30DataTotalNum1", retNum);// �����ṩ����������
				map.put("p30ReturnNum31", retNum);

			}
			// ��������������ǰ��������
			sqlstr = sqlFactory.getString("p30ReqDataTop3");
			sqlstr = replaceParam(sqlstr, params);
			List requDataTop3 = operation.select(sqlstr);
			value = "";
			Map reqTop3 = new HashMap();
			for (int i = 0; i < requDataTop3.size(); i++) {
				reqTop3 = (Map) requDataTop3.get(i);
				String reqNum = getResult(reqTop3, "LAST_MONTH_NUM");
				value = getResult(reqTop3, "AREA_NAME");
				int index = i + 1;
				map.put("p30VisitTop" + index, value);
				map.put("p30VisitTop" + index + "Num", reqNum);
				if (reqNum != null && !"".equals(reqNum)) {
					reqTop3Total = reqTop3Total + Integer.parseInt(reqNum);
				}
			}
			String top3Rate = "";
			if (reqNumTotal == 0) {
				top3Rate = "100";
			} else {
				top3Rate = MathUtils.divide(reqTop3Total * 100 + "",
						reqNumTotal + "", 2);
			}
			map.put("p30VisitDataP", top3Rate);

			// ��������������ǰ��������
			sqlstr = sqlFactory.getString("p30RetDataTop3");
			sqlstr = replaceParam(sqlstr, params);
			List retuDataTop3 = operation.select(sqlstr);
			value = "";
			Map retTop3 = new HashMap();
			for (int i = 0; i < retuDataTop3.size(); i++) {
				retTop3 = (Map) retuDataTop3.get(i);
				String retNum = getResult(retTop3, "TYPE_USE_NUM");
				value = getResult(retTop3, "AREA_NAME");
				int index = i + 1;
				map.put("p30ReturnTop" + index, value);
				map.put("p30ReturnTop" + index + "Num", retNum);
				if (retNum != null && !"".equals(retNum)) {
					retTop3Total = retTop3Total + Integer.parseInt(retNum);
				}
			}
			if (retNumTotal == 0) {
				top3Rate = "100";
			} else {
				top3Rate = MathUtils.divide(retTop3Total * 100 + "",
						retNumTotal + "", 2);
			}
			map.put("p30ReturnDataP", top3Rate);
			Calendar calendar = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("yyyyMM");
			int lastMonthYear = 0;
			int lastMonth = 0;
			try {
				calendar.setTime(df.parse(year + "" + month));
				calendar.add(Calendar.MONTH, -1);
				lastMonthYear = calendar.get(Calendar.YEAR);
				lastMonth = calendar.get(Calendar.MONTH) + 1;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// ������������
			sqlstr = sqlFactory.getString("p30DataShare");
			params[0] = lastMonthYear + "";
			params[1] = lastMonth + "";
			sqlstr = replaceParam(sqlstr, params);
			List tableData1 = operation.select(sqlstr);
			value = "";
			Map tmp1 = new HashMap();
			for (int i = 0; i < tableData1.size(); i++) {
				tmp1 = (Map) tableData1.get(i);
				value = getResult(tmp1, "AREA_NAME");
				if (!value.equals("")) {
					int indexNum = getIndex(value, userName);
					if (indexNum != -1) {
						String reqNum = getResult(tmp1, "LAST_MONTH_NUM");
						String retNum = getResult(tmp1, "TYPE_USE_NUM");
						map.put("p30RequestLMonC" + indexNum, reqNum);
						map.put("p30ReDataNum" + indexNum, retNum);
					}
				}
			}
			// �������ݺϼ�
			sqlstr = sqlFactory.getString("p30SumResult");
			sqlstr = replaceParam(sqlstr, params);
			List sumLastMonth = operation.select(sqlstr);
			value = "";
			Map tmp2 = new HashMap();
			for (int i = 0; i < sumLastMonth.size(); i++) {
				tmp2 = (Map) sumLastMonth.get(i);
				String reqNum = getResult(tmp2, "LAST_MONTH_NUM");
				String retNum = getResult(tmp2, "TYPE_USE_NUM");
				retLastMonth = retNum;
				map.put("p30RequestLMonC31", reqNum);
				map.put("p30ReDataNum31", retNum);

			}

			String worddaysThisMonth = workdays[1];
			String worddaysLastMonth = workdays[0];
			String loopPer = calMoM(retThisMonth, retLastMonth,
					worddaysThisMonth, worddaysLastMonth);
			if(loopPer.indexOf("-") > -1){
				map.put("p30LoopDecrease10Title", "�����ݣ����ȼ���");
				loopPer = loopPer.replaceFirst("-", "");
			}else{
				map.put("p30LoopDecrease10Title", "�����ݣ���������");
			}
			map.put("p30LoopDecrease10", loopPer);
			map.put("p30ThisMonth", Integer.toString(month));
			map.put("p30LastMonth", Integer.toString(lastMonth));
			map.put("p30ShareInterface", "276");
			map.put("p30WebServNum", "173");
			map.put("p30ViewInterface", "7");// ��ͼ�ӿ�
			map.put("p30FullTextNum", "3");// ȫ�ļ����ӿ�
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���ݹ������");
		}
		log.info("ϵͳʹ�ñ���[���������ݹ���]���ɳɹ�");
		return map;
	}

	public int getIndex(String name, String[] userName)
	{
		int index = -1;
		for (int i = 0; i < userName.length; i++) {
			if (name.equals(userName[i])) {
				index = i + 1;
				return index;
			}
		}
		return index;
	}

	private String getResult(Map map, String key)
	{
		if (map.isEmpty()) {
			return "";
		}
		if (null == map.get(key)) {
			return "";
		} else {
			if (StringUtils.isNotBlank(map.get(key).toString().trim())) {
				return map.get(key).toString().trim();
			} else {
				return "";
			}
		}
	}

	/**
	 * �滻��������sql
	 * 
	 * @param sql
	 * @param param
	 * @return
	 */
	public String replaceParam(String sql, String[] param)
	{
		try {
			String temp = sql;
			for (int i = 0; i < param.length; i++) {
				temp = temp.replaceFirst("\\?", param[i]);
			}
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * ���㻷��
	 */
	public String calMoM(String thisMon, String lastMon)
	{
		String result = "-";
		BigDecimal thisMonNum = new BigDecimal(thisMon);
		BigDecimal lastMonNum = new BigDecimal(lastMon);

		result = thisMonNum.subtract(lastMonNum).divide(
				lastMonNum
						.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP),
				2, BigDecimal.ROUND_HALF_UP).toString();
		return result;
	}

	/**
	 * ���㻷��
	 * @param thisMon ��������
	 * @param lastMon ��������
	 * @param daysThisMon ��������
	 * @param daysLastMon ��������
	 * @return
	 */
	public String calMoM(String thisMon, String lastMon, String daysThisMon,
			String daysLastMon)
	{
		if (thisMon == "" || thisMon == null) {
			thisMon = "0";
		}
		if (lastMon == "" || lastMon == null) {
			lastMon = "0";
		}
		if (daysThisMon.equals("0")) {
			daysThisMon = "1";
		}
		if (daysLastMon.equals("0")) {
			daysThisMon = "1";
		}
		String result = "-";
		BigDecimal thisMonNum = new BigDecimal(thisMon);
		BigDecimal thisMonDays = new BigDecimal(daysThisMon);
		BigDecimal lastMonNum = new BigDecimal(lastMon);
		BigDecimal daysLastMonDays = new BigDecimal(daysLastMon);
		String a = MathUtils.divide(thisMonNum, thisMonDays, 6);
		String b = MathUtils.divide(lastMonNum, daysLastMonDays, 6);
		if (Double.parseDouble(b) == 0) {
			result = MathUtils.divide(MathUtils.sub(a, "0"), "1", 2);
		} else {
			result = MathUtils.divide(MathUtils
					.mult(MathUtils.sub(a, b), "100"), b, 2);
		}
		return result;
	}

	/**
	 * ��ȡ����ʱ�������µ�����
	 * 
	 * @param beginTime
	 * @return
	 */
	public int getCurrentMonthDays(String beginTime)
	{
		Calendar calendar = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			calendar.setTime(df.parse(beginTime));
			calendar.set(Calendar.DATE, calendar
					.getActualMaximum(Calendar.DATE));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.get(Calendar.DATE);
	}

	public String getTotalUse()
	{
		return totalUse;
	}

	public void setTotalUse(String totalUse)
	{
		this.totalUse = totalUse;
	}

	public static void main(String[] args) throws DBException
	{
		//LogReportManager reportManager = new LogReportManager();
		///reportManager.calMoM("300", "", "22", "20");
		
		
	}

	public String[] getWorkdays()
	{
		return workdays;
	}

	public void setWorkdays(String[] workdays)
	{
		this.workdays = workdays;
	}

	public int getLastMonth_Year()
	{
		return lastMonth_Year;
	}

	public void setLastMonth_Year(int lastMonth_Year)
	{
		this.lastMonth_Year = lastMonth_Year;
	}

	public int getLastMonth()
	{
		return lastMonth;
	}

	public void setLastMonth(int lastMonth)
	{
		this.lastMonth = lastMonth;
	}
	
	/**
	 * 
	 * getLastMonth(��������ĵ�ǰ�·ݻ�ȡ��������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param currentMonth
	 * @return        
	 * int       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public int getLastMonth(int currentMonth){
		
		Calendar c = Calendar.getInstance();
		int  lastmonth = currentMonth;
		
		SimpleDateFormat format = new SimpleDateFormat("MM");
		try {
			c.setTime(format.parse(Integer.toString(currentMonth)));
			c.add(c.MONTH, -1);
			String time = format.format(c.getTime());
			
			lastmonth = Integer.parseInt(time);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("lastmonth="+lastmonth);
		return lastmonth;
	}

}
