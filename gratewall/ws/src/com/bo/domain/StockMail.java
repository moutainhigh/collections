package com.bo.domain;

public class StockMail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String mailId;
	private String mailAddress;
	private String mailName;
	private Integer hasSender;
	private Integer ispermitted;
	private Integer stockRateTimes;
	private Integer smsStockRateTimes;

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getMailName() {
		return mailName;
	}

	public void setMailName(String mailName) {
		this.mailName = mailName;
	}

	public Integer getHasSender() {
		return hasSender;
	}

	public void setHasSender(Integer hasSender) {
		this.hasSender = hasSender;
	}

	public Integer getIspermitted() {
		return ispermitted;
	}

	public void setIspermitted(Integer ispermitted) {
		this.ispermitted = ispermitted;
	}

	public Integer getStockRateTimes() {
		return stockRateTimes;
	}

	public void setStockRateTimes(Integer stockRateTimes) {
		this.stockRateTimes = stockRateTimes;
	}

	public Integer getSmsStockRateTimes() {
		return smsStockRateTimes;
	}

	public void setSmsStockRateTimes(Integer smsStockRateTimes) {
		this.smsStockRateTimes = smsStockRateTimes;
	}

}
