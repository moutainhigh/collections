package com.bo.domain;

import java.util.Date;

//http://blog.csdn.net/haibo0668/article/details/52594289
//http://blog.csdn.net/edison_03/article/details/77413099
//http://fjfj910.iteye.com/blog/1202219
public class Holiday {
	private Date holiday;
	private String holidayName;

	public Date getHoliday() {
		return holiday;
	}

	public void setHoliday(Date holiday) {
		this.holiday = holiday;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

}
