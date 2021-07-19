package com.gwssi.dw.runmgr.webservices.localtax.in;


import com.gwssi.common.util.CalendarUtil;
import com.gwssi.dw.runmgr.webservices.common.ProcessResult;
import com.gwssi.dw.runmgr.webservices.localtax.LocalTaxWSException;

public abstract class Process
{
	private Process	next;


	public Process()
	{
		next = null;
	}

	protected void setProcess(Process next)
	{
		this.next = next;
	}

	public void handle(ProcessResult result) throws LocalTaxWSException
	{
		
		try {
			getData(null, result);
		} catch (LocalTaxWSException e) {
			result.appendLog(e.getMessage());
		}
		processData(result);
		next(result);
	}

	public ProcessCondition getCondition(ProcessCondition con,
			ProcessResult result) throws LocalTaxWSException
	{
		String maxCount = result.getEndNum();
		String start = result.getStartDate();
		String end = result.getEndDate();	
		
		if (con == null) {
			con = new ProcessCondition();
			con.setStartNum(result.getStartNum());
			con.setEndNum(result.getEndNum());
			try {
				long t = CalendarUtil.getDayBetweenTwoDate(result
						.getStartDate(), result.getEndDate());
				if (t < 7) {
					con.setStartDate(start);
					con.setEndDate(end);
					con.setFlag(ProcessCondition.FLAG_NORMAL);
				} else {
					con.setStartDate(start);
					con.setEndDate(CalendarUtil.getAfterDateBySecond(
							CalendarUtil.FORMAT11_1, start, Integer
									.parseInt(result.getMaxDay())));
					con.setFlag(ProcessCondition.FLAG_OUTTIME);
				}
			} catch (Exception e) {
				throw new LocalTaxWSException("转换查询开始日期或结束日期失败,程序bug!");
			}
		} else {
			if (ProcessCondition.FLAG_OUTNUM.equals(con.getFlag())) {
				int startI = Integer.parseInt(con.getStartNum())
						+ Integer.parseInt(maxCount);
				int endI = Integer.parseInt(con.getEndNum()) + Integer.parseInt(maxCount);
				con.setStartNum(String.valueOf(startI));
				con.setEndNum(String.valueOf(endI));
			}
			if (ProcessCondition.FLAG_OUTTIME.equals(con.getFlag())) {

				try {
					String nextStart = CalendarUtil.getAfterDateBySecond(
							CalendarUtil.FORMAT11_1, con.getEndDate(), 1);

					long t = CalendarUtil.getDayBetweenTwoDate(nextStart,
							result.getEndDate());

					if (t < 7) {
						con.setStartDate(nextStart);
						con.setEndDate(end);
						con.setFlag(ProcessCondition.FLAG_NORMAL);
					} else {
						con.setStartDate(nextStart);
						con.setEndDate(CalendarUtil.getAfterDateBySecond(
								CalendarUtil.FORMAT11_1, nextStart, Integer
										.parseInt(result.getMaxDay())));						
					}
				} catch (Exception e) {
					throw new LocalTaxWSException("转换查询开始日期或结束日期失败,程序bug!");
				}
			}
		}
		return con;
	}

	public abstract void processData(ProcessResult result)
			throws LocalTaxWSException;

	public abstract void getData(ProcessCondition con, ProcessResult result)
			throws LocalTaxWSException;

	public void next(ProcessResult result) throws LocalTaxWSException
	{
		if (next != null) {
			next.handle(result);
		}
	}
}
