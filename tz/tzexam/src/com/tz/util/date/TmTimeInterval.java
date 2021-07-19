package com.tz.util.date;

import java.io.Serializable;
import java.util.Date;

public class TmTimeInterval implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date startTime;
	private Date endTime;
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Boolean isContain(Date date) {
		String a = "";
		if(date.after(getStartTime()) && date.before(getEndTime()) || date.equals(getStartTime()) || date.equals(getEndTime())) {
			return true;
		}
		return false;
	}
	
}
