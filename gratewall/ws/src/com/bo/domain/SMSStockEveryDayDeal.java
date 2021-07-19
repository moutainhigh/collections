package com.bo.domain;

import java.util.Date;

public class SMSStockEveryDayDeal implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String stockCode;
	private String stockName;
	private double rates;
	private double currentPrice;
	private double zhangdieFu;
	private Date recordDate;
	private int stockBelong;
	
	public double getZhangdieFu() {
		return zhangdieFu;
	}

	public void setZhangdieFu(double zhangdieFu) {
		this.zhangdieFu = zhangdieFu;
	}

	public int getStockBelong() {
		return stockBelong;
	}

	public void setStockBelong(int stockBelong) {
		this.stockBelong = stockBelong;
	}

	public SMSStockEveryDayDeal() {
		super();
	}

	public SMSStockEveryDayDeal(String stockCode, String stockName, double rates, double currentPrice) {
		super();
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.rates = rates;
		this.currentPrice = currentPrice;
	}

	public SMSStockEveryDayDeal(String stockCode, String stockName, double currentPrice) {
		super();
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.currentPrice = currentPrice;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public double getRates() {
		return rates;
	}

	public void setRates(double rates) {
		this.rates = rates;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

}
