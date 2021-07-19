package com.gwssi.dw.runmgr.webservices.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.StringUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.webservices.localtax.LocalTaxWSException;

public class ProcessResult
{	
	private static Map returnCode = new HashMap();
	static{
		returnCode.put("TAX0000", "正常返回数据");
		returnCode.put("TAX0010", "没有查询结果返回");
		returnCode.put("TAX0020", "查询结果超出限定范围（最多2000条）");
		returnCode.put("TAX0030", "入口参数错误");
		returnCode.put("TAX0040", "查询日期时间段过长（最长7天）");
		returnCode.put("TAX0050", "系统错误");
		returnCode.put("TAX9999", "未知错误");
	}
	// 记录总的日志
	private StringBuffer strLog = new StringBuffer();
	
	// 记录本次采集的数据开始时间
	private String startDate;
	// 记录本次采集的数据结束时间
	private String endDate;

	// 记录本次采集的开始记录数
	private String startNum;
	
	// 记录本次采集的结束记录数，每次采集默认为最大能够采集的记录数	
	private String endNum;
	
	// 记录地税采集的最开始的数据日期	
	private String dataDate;
	
	// 每一次采集允许的最大时间间隔（天）
	private String maxDay;
	
	private List	resultSql;
	
	private CollectionLog log;

	public ProcessResult(){
		log = new CollectionLog();
	}

	public List getResultSql()
	{
		if(resultSql == null){
			resultSql = new ArrayList();
		}
		return resultSql;
	}

	public void addSqls(List sqls)
	{
		if (resultSql == null) {
			resultSql = new ArrayList();
		}
		resultSql.add(sqls);
	}

	public String toString()
	{
		if (resultSql == null) {
			return super.toString();
		} else {
			StringBuffer str = new StringBuffer();
			for(int i=0;i<resultSql.size();i++){
				String sql = (String)resultSql.get(i);
				str.append(sql).append("\n");
			}
			return str.toString();
		}
	}
	
	public static void main(String[] args){
//		ProcessResult result = new ProcessResult();
//		result.setStartDate("20080201");
//		result.setEndDate("20080522");
//		result.setStartTime("20080522220000");
//		result.setEndTime("20080522230000");
//		result.setFlag("1");
//		result.setReturnMsg("本次采集成功!");
//
//		System.out.println(CalendarUtil
//				.getCalendarByFormat(CalendarUtil.FORMAT11_1));
//		System.out.println(result.getLog());
	}
	
	public List getLog()
	{
		return log.getLogSql();
//		StringBuffer sql = new StringBuffer(
//				"insert into EXC_LOG(exc_log_id,type,startdate,enddate,starttime,endtime,flag,returnmsg) values(");
//		sql
//				.append("'")
//				.append(UuidGenerator.getUUID())
//				.append("',")
//				.append("'localtax',")
//				.append("'")
//				.append(StringUtil.nullToEmpty(startDate))
//				.append("',")
//				.append("'")
//				.append(StringUtil.nullToEmpty(endDate))
//				.append("',")
//				.append("'")
//				.append(StringUtil.nullToEmpty(startTime))
//				.append("',")
//				.append("'")
//				.append(StringUtil.nullToEmpty(endTime))
//				.append("',")
//				.append("'")
//				.append(StringUtil.nullToEmpty(flag))
//				.append("',")
//				.append("'")
//				.append(StringUtil.nullToEmpty(returnMsg))
//				.append("')");

	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
		log.setClt_startdate(startDate);
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
		log.setClt_enddate(endDate);
	}

	public String getEndNum()
	{
		return endNum;
	}

	public void setEndNum(String endNum)
	{
		this.endNum = endNum;
	}

	public String getStartNum()
	{
		return startNum;
	}

	public void setStartNum(String startNum)
	{
		this.startNum = startNum;
	}
	
	public boolean returnCorrect(String fhdm) throws LocalTaxWSException{
		if("TAX0000".equals(fhdm)||"TAX0010".equals(fhdm)){
			return true;
		}else {
			throw new LocalTaxWSException((String)returnCode.get(fhdm));
		}
	}
	
	public boolean hasData(String fhdm) throws LocalTaxWSException{
		if("TAX0000".equals(fhdm)){
			return true;
		}else {
			return false;
		}
	}

	public String getDataDate()
	{
		return dataDate;
	}

	public void setDataDate(String dataDate)
	{
		this.dataDate = dataDate;
	}

	public String getMaxDay()
	{
		return maxDay;
	}

	public void setMaxDay(String maxDay)
	{
		this.maxDay = maxDay;
	}

	public void setCollectionLog(CollectionLog log)
	{
		this.log = log;
	}
	
	public CollectionLog getCollectionLog()
	{
		return this.log;
	}

	public void setResultSql(List resultSql)
	{
		this.resultSql = resultSql;
	}

	public StringBuffer getStrLog()
	{
		return strLog;
	}

	public void appendLog(String str)
	{
		this.strLog.append(str);
	}
}
