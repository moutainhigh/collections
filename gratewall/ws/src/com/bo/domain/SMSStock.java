package com.bo.domain;

import java.util.Date;

public class SMSStock implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String stockCode;
	private String stockName;
	private String pinYin; //汉语拼音
	private double rates;
	private Date addDate;
	private double currentPrice;
	private double prevClose;
	private double todayMaxPrice;
	private double todayMinPrice;
	private double fudu;//振幅
	private int stockBelong;
	private int types;//早上还是下午的 0 早上，1下午
	private String thumbsImg;//早上还是下午的 0 早上，1下午


	public String getThumbsImg() {
		return thumbsImg;
	}

	public void setThumbsImg(String thumbsImg) {
		this.thumbsImg = thumbsImg;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public int getStockBelong() {
		return stockBelong;
	}

	public void setStockBelong(int stockBelong) {
		this.stockBelong = stockBelong;
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

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public int getTypes() {
		return types;
	}

	public void setTypes(int types) {
		this.types = types;
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


	public double getFudu() {
		return fudu;
	}

	public void setFudu(double fudu) {
		this.fudu = fudu;
	}

	public double getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(double prevClose) {
		this.prevClose = prevClose;
	}

}
