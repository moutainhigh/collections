package com.ly.pojo;

/**
 * 新增
 * 
 * 
 * DROP TRIGGER IF EXISTS insert_Sts; CREATE TRIGGER insert_Sts BEFORE insert ON
 * buy FOR EACH ROW BEGIN if new.`code` LIKE '000%' or new.code like '001%' THEN
 * set new.marketType ='4609'; set new.exchange_type='2'; elseif new.`code` LIKE
 * '002%' THEN set new.marketType ='4614'; set new.exchange_type='2'; elseif
 * new.`code` LIKE '300%' THEN set new.marketType ='4621'; set
 * new.exchange_type='2'; elseif new.`code` LIKE '60%' THEN set new.marketType
 * ='4353'; set new.exchange_type='1'; end if; end
 * 
 * 
 * 
 * 
 */


public class StockDayLineDatasMa  implements java.io.Serializable {
	private static final long serialVersionUID = -3859526119096611813L;
	private String code;
	private String name;
	private String closePrice;
	private String openPrice;
	private String minPrice;
	private String maxPrice;
	private String ma5;
	private String ma10;
	private String ma20;
	private String ma30;
	private String ma60;
	private String marketType;
	private String exchange_type;
	private String date;
	private Integer count;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(String closePrice) {
		this.closePrice = closePrice;
	}
	public String getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(String openPrice) {
		this.openPrice = openPrice;
	}
	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getMa5() {
		return ma5;
	}
	public void setMa5(String ma5) {
		this.ma5 = ma5;
	}
	public String getMa10() {
		return ma10;
	}
	public void setMa10(String ma10) {
		this.ma10 = ma10;
	}
	public String getMa20() {
		return ma20;
	}
	public void setMa20(String ma20) {
		this.ma20 = ma20;
	}
	public String getMa30() {
		return ma30;
	}
	public void setMa30(String ma30) {
		this.ma30 = ma30;
	}
	public String getMa60() {
		return ma60;
	}
	public void setMa60(String ma60) {
		this.ma60 = ma60;
	}
	public String getMarketType() {
		return marketType;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	public String getExchange_type() {
		return exchange_type;
	}
	public void setExchange_type(String exchange_type) {
		this.exchange_type = exchange_type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	
	

}
