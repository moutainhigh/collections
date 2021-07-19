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
		returnCode.put("TAX0000", "������������");
		returnCode.put("TAX0010", "û�в�ѯ�������");
		returnCode.put("TAX0020", "��ѯ��������޶���Χ�����2000����");
		returnCode.put("TAX0030", "��ڲ�������");
		returnCode.put("TAX0040", "��ѯ����ʱ��ι������7�죩");
		returnCode.put("TAX0050", "ϵͳ����");
		returnCode.put("TAX9999", "δ֪����");
	}
	// ��¼�ܵ���־
	private StringBuffer strLog = new StringBuffer();
	
	// ��¼���βɼ������ݿ�ʼʱ��
	private String startDate;
	// ��¼���βɼ������ݽ���ʱ��
	private String endDate;

	// ��¼���βɼ��Ŀ�ʼ��¼��
	private String startNum;
	
	// ��¼���βɼ��Ľ�����¼����ÿ�βɼ�Ĭ��Ϊ����ܹ��ɼ��ļ�¼��	
	private String endNum;
	
	// ��¼��˰�ɼ����ʼ����������	
	private String dataDate;
	
	// ÿһ�βɼ���������ʱ�������죩
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
//		result.setReturnMsg("���βɼ��ɹ�!");
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
