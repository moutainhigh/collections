package com.gwssi.webservice.client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.collect.webservice.param.vo.VoCollectWsParamValue;
import com.gwssi.collect.webservice.vo.VoCollectWebservicePatameter;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：TaskInfo 类描述：采集任务信息 创建人：lizheng 创建时间：Jul 23, 2013
 * 4:26:52 PM 修改人：lizheng 修改时间：Jul 23, 2013 4:26:52 PM 修改备注：
 * 
 * @version
 * 
 */
public class TaskInfo
{

	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(TaskInfo.class
											.getName());

	ClientDAO				dao		= null;

	public TaskInfo()
	{
		dao = new ClientDAOImpl();
	}

	/**
	 * 
	 * queryTargetNo 根据采集任务ID查询采集任务对象名称
	 * 
	 * @param taskId
	 * @return
	 * @throws DBException
	 *             String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String queryTargetNo(String taskId)
	{
		String sql = SQLHelper.queryTargetNo(taskId);
		String targetNo = "";
		try {
			targetNo = dao.queryTargetNo(sql);
		} catch (DBException e) {
			logger.debug("方法queryTargetNo查询数据库出错...");
			e.printStackTrace();
		}
		return targetNo;
	}

	/**
	 * 
	 * queryDataSource 根据taskId查询数据源信息
	 * 
	 * @param taskId
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public Map queryDataSource(String taskId)
	{
		String sql = SQLHelper.queryDataSource(taskId);
		Map dataSourceMap = new HashMap();
		try {
			dataSourceMap = dao.queryDataSource(sql);
		} catch (DBException e) {
			logger.debug("方法queryDataSource查询数据库出错...");
			e.printStackTrace();
		}
		return dataSourceMap;
	}

	/**
	 * 
	 * queryMethod 根据taskID查询此任务下有多少方法
	 * 
	 * @param taskId
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public List queryMethod(String taskId)
	{
		String sql = SQLHelper.queryMethodListSQL(taskId);
		List methodList = new ArrayList();
		try {
			methodList = dao.queryMethodList(sql);
		} catch (DBException e) {
			logger.debug("方法queryMethod查询数据库出错...");
			e.printStackTrace();
		}
		return methodList;
	}

	/**
	 * 
	 * queryTask 根据任务ID查询任务信息
	 * 
	 * @param taskId
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public Map queryTask(String taskId)
	{
		String sql = SQLHelper.queryTask(taskId);
		Map taskMap = new HashMap();
		try {
			taskMap = dao.queryTask(sql);
		} catch (DBException e) {
			logger.debug("方法queryTask查询数据库出错...");
			e.printStackTrace();
		}
		return taskMap;
	}

	/**
	 * 
	 * querySrvTager 根据服务对象ID查询服务对象信息
	 * 
	 * @param srvTargetId
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public Map querySrvTager(String srvTargetId)
	{
		String sql = SQLHelper.querySrvTager(srvTargetId);
		Map taskMap = new HashMap();
		try {
			taskMap = dao.querySrvTager(sql);
		} catch (DBException e) {
			logger.debug("方法querySrvTager查询数据库出错...");
			e.printStackTrace();
		}
		return taskMap;
	}

	/**
	 * 
	 * queryTable 根据tableId查询表信息
	 * 
	 * @param tableId
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public Map queryTable(String tableId)
	{
		String sql = SQLHelper.queryTable(tableId);
		Map tableMap = new HashMap();
		try {
			tableMap = dao.queryTable(sql);
		} catch (DBException e) {
			logger.debug("方法queryTable查询数据库出错...");
			e.printStackTrace();
		}
		return tableMap;
	}

	/**
	 * 
	 * queryDataitemName 根据表ID查询该表里的字段
	 * 
	 * @param tableId
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public List queryDataitemName(String tableId)
	{
		String sql = SQLHelper.queryDataitem(tableId);
		List tepList = new ArrayList();
		List columnList = new ArrayList();
		try {
			tepList = dao.queryDataitem(sql);
			for (int i = 0; i < tepList.size(); i++) {
				Map tepMap = (Map) tepList.get(i);
				columnList.add(tepMap
						.get(CollectConstants.COLLECT_DATAITEM_NAME));
			}
		} catch (DBException e) {
			logger.debug("方法queryDataitemName查询数据库出错...");
			e.printStackTrace();
		}
		return columnList;
	}

	/**
	 * 
	 * queryParam 根据webserviceTaskId查询参数
	 * 
	 * @param wsTaskId
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public List queryParam(String wsTaskId)
	{
		String sql = SQLHelper.queryParam(wsTaskId);
		List paramList = new ArrayList();
		try {
			paramList = dao.queryParam(sql);
		} catch (DBException e) {
			logger.debug("方法queryParam查询数据库出错...");
			e.printStackTrace();
		}
		return paramList;
	}

	/**
	 * 
	 * queyrParamValue 根据参数的Id查询参数值
	 * 
	 * @param paramId
	 * @return List
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public List queyrParamValue(String paramId)
	{
		String sql = SQLHelper.queryParamValue(paramId);
		List valueList = new ArrayList();
		try {
			valueList = dao.queryParamValue(sql);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return valueList;
	}

	/**
	 * 
	 * queryCollectType 查询任务类型
	 * 
	 * @param taskId
	 * @return
	 * @throws DBException
	 *             String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String queryCollectType(String taskId)
	{
		String sql = SQLHelper.queryCollectType(taskId);
		String collectType = "";
		try {
			collectType = dao.queryCollectType(sql);
		} catch (DBException e) {
			logger.debug("方法queryCollectType查询数据库出错...");
			e.printStackTrace();
		}
		return collectType;
	}

	/**
	 * 
	 * insertLog 记录日志
	 * 
	 * @param vo
	 * @return int
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public int insertLog(CollectLogVo vo)
	{
		logger.debug("记录日志...");
		String sql = SQLHelper.insertCollectLog(vo).toString();
		int count = 0;
		try {
			count = dao.insertCollectLog(sql);
		} catch (DBException e) {
			logger.debug("记录日志报错..." + e);
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 
	 * insertLog 记录日志
	 * 
	 * @param id
	 * @param vo
	 * @return int
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public int insertLog(String id, CollectLogVo vo)
	{
		logger.debug("记录日志...");
		String sql = SQLHelper.insertCollectLog(id, vo).toString();
		int count = 0;
		try {
			count = dao.insertCollectLog(sql);
		} catch (DBException e) {
			logger.debug("记录日志报错..." + e);
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 
	 * getParamValue 根据webservice任务表ID获取参数值
	 * 
	 * @param webserviceTaskId
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String getParamValue(String webserviceTaskId)
	{
		String paramValue = "";
		List paramList = queryParam(webserviceTaskId);
		Map paramValueMap = fromatParamValue(paramList);
		// System.out.println("paramValueMap is " + paramValueMap);
		if (null != paramValueMap.get(CollectConstants.PARAM_VALUE)
				&& !"".equals(paramValueMap.get(CollectConstants.PARAM_VALUE)
						.toString())) {
			paramValue = paramValueMap.get(CollectConstants.PARAM_VALUE)
					.toString();
		}
		// System.out.println("paramValue is " + paramValue);
		return paramValue;
	}

	/**
	 * 
	 * fromatParamValue 格式化参数
	 * 
	 * @param paramList
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public Map fromatParamValue(List paramList)
	{
		// System.out.println("paramList is " + paramList);
		Map tepMap = new HashMap();
		List paramNameList = new ArrayList();
		Map paramValueMap = new HashMap();
		Map valueMap = new HashMap();
		for (int i = 0; i < paramList.size(); i++) {
			Map parMap = (Map) paramList.get(i);
			// System.out.println("parMap is " + parMap);
			VoCollectWebservicePatameter vo = new VoCollectWebservicePatameter();
			ParamUtil.mapToBean(parMap, vo, false);
			String name = vo.getPatameter_name();
			String style = vo.getPatameter_style();
			// System.out.println("style is " + style);
			if (CollectConstants.TYPE_PARAM_STYLE_01.equals(style)) {
				logger.debug("参数格式是字符串");
			} else if (CollectConstants.TYPE_PARAM_STYLE_02.equals(style)) {
				logger.debug("参数格式是XML");
				String type = vo.getPatameter_type();
				String paramid = vo.getWebservice_patameter_id();
				if ("string".equals(type)||CollectConstants.PARAM_STYLE_00.equals(type)) {
					valueMap = getXmlStyle(paramid);
				} else if ("Map".equals(type)) {

				} else {
					logger.debug("暂时未考虑这种方式...");
				}
			}
			paramNameList.add(name);
			tepMap.put(name, valueMap);
		}
		paramValueMap.put(CollectConstants.PARAM_LIST, paramNameList);
		paramValueMap.put(CollectConstants.PARAM_VALUE, tepMap);
		return paramValueMap;
	}

	/**
	 * 
	 * getXmlStyle 转换成xml格式
	 * 
	 * @param paramid
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private Map getXmlStyle(String paramid)
	{
		Map valueMap = new HashMap();
		List paramValueList = queyrParamValue(paramid);
		// System.out.println("paramValueList is " + paramValueList);
		for (int i = 0; i < paramValueList.size(); i++) {
			Map paramValueMap = (Map) paramValueList.get(i);
			// System.out.println("paramValueMap is " + paramValueMap);
			VoCollectWsParamValue vo = new VoCollectWsParamValue();
			ParamUtil.mapToBean(paramValueMap, vo, false);
			String type = vo.getParam_value_type();
			if (CollectConstants.PARAM_STYLE_00.equals(type)
					|| CollectConstants.PARAM_STYLE_01.equals(type)) { // 是字符串或者是数字
				valueMap.put(vo.getPatameter_name(), vo.getPatameter_value());
			}
			if (CollectConstants.PARAM_STYLE_02.equals(type)) {
				String style = vo.getStyle();
				// System.out.println("style is " + style);
				String value = vo.getPatameter_value();
				//时间参数默认值
				if("TODAY".equals(value)){
					value="TODAY-0";
				}
				int day = 0;
				String time = "";
				if (value.contains("-")) {
					day = Integer.parseInt(value.substring(value
							.lastIndexOf("-") + 1, value.length()));
				}
				if (CollectConstants.DATE_STYLE_01.equals(style)) {
					time = get01Time(day);
				} else if (CollectConstants.DATE_STYLE_02.equals(style)) {
					time = get02Time(day);
				} else if (CollectConstants.DATE_STYLE_03.equals(style)) {
					time = get03Time(day);
				}
				if (valueMap.containsKey(vo.getPatameter_name())) {
					String tmp = valueMap.get(vo.getPatameter_name())
							.toString();
					valueMap.put(vo.getPatameter_name(), time + "," + tmp);
				} else {
					valueMap.put(vo.getPatameter_name(), time);
				}
			}
		}
		// System.out.println("valueMap is " + valueMap);
		return valueMap;
	}

	/**
	 * 
	 * get01Time
	 * 
	 * @param day
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String get01Time(int day)
	{
		String currenttime = "";
		if (day != 0) {
			Date dNow = new Date(); // 当前时间
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(dNow);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -day); // 设置为前一天
			dBefore = calendar.getTime(); // 得到前一天的时间
			java.sql.Timestamp date = new java.sql.Timestamp(dBefore.getTime());
			String time = date.toString();
			if (time != null && !"".equals(time)) {
				String temp[] = time.split(" ");
				String temp1[] = temp[0].split("-");
				for (int i = 0; i < temp1.length; i++) {
					currenttime = currenttime + temp1[i];
				}
			}
		} else {
			java.sql.Timestamp date = new java.sql.Timestamp(new Date()
					.getTime());
			String time = date.toString();
			if (time != null && !"".equals(time)) {
				String temp[] = time.split(" ");
				String temp1[] = temp[0].split("-");
				for (int i = 0; i < temp1.length; i++) {
					currenttime = currenttime + temp1[i];
				}
			}
		}
		return currenttime;
	}

	/**
	 * 
	 * get02Time
	 * 
	 * @param day
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String get02Time(int day)
	{
		String currenttime = "";
		if (day != 0) {
			Date dNow = new Date(); // 当前时间
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(dNow);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -day); // 设置为前一天
			dBefore = calendar.getTime(); // 得到前一天的时间
			java.sql.Timestamp date = new java.sql.Timestamp(dBefore.getTime());
			String time = date.toString();
			if (time != null && !"".equals(time)) {
				String temp[] = time.split(" ");
				String _time = temp[0];
				currenttime = _time.replaceAll("-", "/");
			}
		} else {
			java.sql.Timestamp date = new java.sql.Timestamp(new Date()
					.getTime());
			String time = date.toString();
			if (time != null && !"".equals(time)) {
				String temp[] = time.split(" ");
				String _time = temp[0];
				currenttime = _time.replaceAll("-", "/");
			}
		}
		return currenttime;
	}

	/**
	 * 
	 * get03Time
	 * 
	 * @param day
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String get03Time(int day)
	{
		String currenttime = "";
		if (day != 0) {
			Date dNow = new Date(); // 当前时间
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); // 得到日历
			calendar.setTime(dNow);// 把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -day); // 设置为前一天
			dBefore = calendar.getTime(); // 得到前一天的时间
			java.sql.Timestamp date = new java.sql.Timestamp(dBefore.getTime());
			String time = date.toString();
			if (time != null && !"".equals(time)) {
				String temp[] = time.split(" ");
				currenttime = temp[0];
			}
		} else {
			java.sql.Timestamp date = new java.sql.Timestamp(new Date()
					.getTime());
			String time = date.toString();
			if (time != null && !"".equals(time)) {
				String temp[] = time.split(" ");
				currenttime = temp[0];
			}
		}
		return currenttime;
	}

	public Map fromatParam(String param, Map tepMap)
	{
		tepMap.put("KSJLS", "1");// 开始记录数
		tepMap.put("JSJLS", "2000");// 结束记录数
		String[] p = param.split("&");
		for (int j = 0; j < p.length; j++) {
			String s = p[j];
			String key = s.substring(s.indexOf("{") + 1, s.lastIndexOf("="));
			String value = s.substring(s.indexOf("=") + 1, s.lastIndexOf("}"));
			if ("#".equals(value)) {
				tepMap.put(key, tepMap.get(key));
			} else if ("time".equals(value)) {
				tepMap.put(key, getDay());
			} else {
				tepMap.put(key, value);
			}
		}
		return tepMap;
	}

	public String getDay()
	{
		String currenttime = "";
		java.sql.Timestamp dateForFileName = new java.sql.Timestamp(new Date()
				.getTime());
		String time = dateForFileName.toString();
		if (time != null && !"".equals(time)) {
			String temp[] = time.split(" ");
			String temp1[] = temp[0].split("-");
			String temp2[] = temp[1].split(":");
			for (int i = 0; i < temp1.length; i++) {
				currenttime = currenttime + temp1[i];
			}
		}
		return currenttime;
	}

	public Map fromatParam(List paramList, Map tepMap)
	{
		for (int j = 0; j < paramList.size(); j++) {
			Map param = (Map) paramList.get(j);
			VoCollectWebservicePatameter vo = new VoCollectWebservicePatameter();
			ParamUtil.mapToBean(param, vo, false);
			tepMap.put(vo.getPatameter_name(), vo.getPatameter_value());
		}
		return tepMap;
	}

	public static void main(String[] args)
	{
		TaskInfo t = new TaskInfo();
		// System.out.println(t.get02Time());
		String value = "TODAY-1";
		int day = 0;
		if (value.contains("-")) {
			day = Integer.parseInt(value.substring(value.lastIndexOf("-") + 1,
					value.length()));
		}
		System.out.println(day);
		String c = t.get01Time(day);
String a = t.get02Time(day);
String b = t.get03Time(day);
System.out.println(c);
System.out.println(a);
System.out.println(b);
	}

}
