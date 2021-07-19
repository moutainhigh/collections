package com.bo.domain;

import java.util.Date;

//√ø»’“ªæ‰
public class EveryDayTip implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String contents;
	private Date addDate;

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
