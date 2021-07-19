package com.bo.domain;

import java.util.Date;

public class Stock implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String stockCode;
	private String stockName;
	private double pointerPrice;
	private double pointerRates;
	private double fudu;
	private int isImp;
	private int ispub;
	private int isnew;
	private double buyPrice;
	private double todayMaxPrice;
	private double todayMinPrice;
	private int holdNum;
	private int isHistoryBuy;
	private double prevClose;
	private double currentPrice;
	private double positions;
	private int isRecentImport;// 是否近期关注
	private Date addDate;
	private int stockBelong;
	private double zhangDie;
	private int mostImp;
	private int waitImp;
	private int longhuBang;
	private int isconsole;// 是否控制台显示
	private String thumbsImg;
	private String pinYin;

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getThumbsImg() {
		return thumbsImg;
	}

	public void setThumbsImg(String thumbsImg) {
		this.thumbsImg = thumbsImg;
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

	public double getPointerPrice() {
		return pointerPrice;
	}

	public void setPointerPrice(double pointerPrice) {
		this.pointerPrice = pointerPrice;
	}

	public double getPointerRates() {
		return pointerRates;
	}

	public void setPointerRates(double pointerRates) {
		this.pointerRates = pointerRates;
	}

	public double getFudu() {
		return fudu;
	}

	public void setFudu(double fudu) {
		this.fudu = fudu;
	}

	public int getIsImp() {
		return isImp;
	}

	public void setIsImp(int isImp) {
		this.isImp = isImp;
	}

	public int getIspub() {
		return ispub;
	}

	public void setIspub(int ispub) {
		this.ispub = ispub;
	}

	public int getIsnew() {
		return isnew;
	}

	public void setIsnew(int isnew) {
		this.isnew = isnew;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
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

	public int getHoldNum() {
		return holdNum;
	}

	public void setHoldNum(int holdNum) {
		this.holdNum = holdNum;
	}

	public int getIsHistoryBuy() {
		return isHistoryBuy;
	}

	public void setIsHistoryBuy(int isHistoryBuy) {
		this.isHistoryBuy = isHistoryBuy;
	}

	public double getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(double prevClose) {
		this.prevClose = prevClose;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public double getPositions() {
		return positions;
	}

	public void setPositions(double positions) {
		this.positions = positions;
	}

	public int getIsRecentImport() {
		return isRecentImport;
	}

	public void setIsRecentImport(int isRecentImport) {
		this.isRecentImport = isRecentImport;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public int getStockBelong() {
		return stockBelong;
	}

	public void setStockBelong(int stockBelong) {
		this.stockBelong = stockBelong;
	}

	public double getZhangDie() {
		return zhangDie;
	}

	public void setZhangDie(double zhangDie) {
		this.zhangDie = zhangDie;
	}

	public int getMostImp() {
		return mostImp;
	}

	public void setMostImp(int mostImp) {
		this.mostImp = mostImp;
	}

	public int getWaitImp() {
		return waitImp;
	}

	public void setWaitImp(int waitImp) {
		this.waitImp = waitImp;
	}

	public int getLonghuBang() {
		return longhuBang;
	}

	public void setLonghuBang(int longhuBang) {
		this.longhuBang = longhuBang;
	}

	public int getIsconsole() {
		return isconsole;
	}

	public void setIsconsole(int isconsole) {
		this.isconsole = isconsole;
	}

}
