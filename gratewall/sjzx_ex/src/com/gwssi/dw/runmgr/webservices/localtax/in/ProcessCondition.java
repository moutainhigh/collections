package com.gwssi.dw.runmgr.webservices.localtax.in;

public class ProcessCondition
{
	// ��ѯ��¼�������Χ(2000)��,��ѯʱ�������Χ(7��)��
	public static String FLAG_NORMAL = "0";
	// ��ѯ��¼���������Χ(2000),��ѯʱ�������Χ(7��)��
	public static String FLAG_OUTNUM = "1";
//	 ��ѯ��¼�������Χ(2000)��,��ѯʱ�䳬�����Χ(7��)
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
