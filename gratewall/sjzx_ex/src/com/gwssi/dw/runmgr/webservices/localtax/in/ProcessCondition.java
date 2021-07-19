package com.gwssi.dw.runmgr.webservices.localtax.in;

public class ProcessCondition
{
	// 查询纪录数在最大范围(2000)内,查询时间在最大范围(7天)内
	public static String FLAG_NORMAL = "0";
	// 查询纪录数超出最大范围(2000),查询时间在最大范围(7天)内
	public static String FLAG_OUTNUM = "1";
//	 查询纪录数在最大范围(2000)内,查询时间超出最大范围(7天)
	public static String FLAG_OUTTIME = "2";
	
	private String startDate;
	private String endDate;
	private String startNum;
	private String endNum;
	private String flag;
	public String getFlag()
	{
		return flag;
	}
	public void setFlag(String flag)
	{
		this.flag = flag;
	}
	public String getEndDate()
	{
		return endDate;
	}
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	public String getEndNum()
	{
		return endNum;
	}
	public void setEndNum(String endNum)
	{
		this.endNum = endNum;
	}
	public String getStartDate()
	{
		return startDate;
	}
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}
	public String getStartNum()
	{
		return startNum;
	}
	public void setStartNum(String startNum)
	{
		this.startNum = startNum;
	}
}
