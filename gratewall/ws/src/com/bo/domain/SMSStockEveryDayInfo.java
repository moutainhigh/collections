package com.bo.domain;

import java.util.Date;

//每日短信股的记录
public class SMSStockEveryDayInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String stockCode;
	private String stockName;
	private Date addDate;
	private double todayMaxPrice;
	private double todayMinPrice;
	private double closePrice;
	private double closePrevPrice;
	private int stockBelong;
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
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public double getTodayMaxPrice() {
		return todayMaxPrice;
	}
	public void setTodayMaxPrice(double todayMaxPrice) {
		this.todayMaxPrice = todayMaxPrice;
	}
	public double getTodayMinPrice() {
		return todayMinPrice;
	}
	public void setTodayMinPrice(double todayMinPrice) {
		this.todayMinPrice = todayMinPrice;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	public double getClosePrevPrice() {
		return closePrevPrice;
	}
	public void setClosePrevPrice(double closePrevPrice) {
		this.closePrevPrice = closePrevPrice;
	}
	public int getStockBelong() {
		return stockBelong;
	}
	public void setStockBelong(int stockBelong) {
		this.stockBelong = stockBelong;
	}

}
