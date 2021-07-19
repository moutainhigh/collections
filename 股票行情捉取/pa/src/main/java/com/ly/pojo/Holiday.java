package com.ly.pojo;

import java.util.Date;

public class Holiday  implements java.io.Serializable {
	private static final long serialVersionUID = -3859526119096611813L;
	private Date holiday;
	private String holidayName;
	private Date addDate;
	private Integer count;

	public Date getAddDate() {
		return addDate;
	}

	public Date getHoliday() {
		return holiday;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public void setHoliday(Date holiday) {
		this.holiday = holiday;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
