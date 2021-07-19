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

/*
 * 更新 DROP TRIGGER IF EXISTS updateSts; CREATE TRIGGER updateSts BEFORE update
 * ON buy FOR EACH ROW BEGIN if new.`code` LIKE '000%' THEN set new.marketType
 * ='4609'; set new.exchange_type='2'; elseif new.`code` LIKE '002%' THEN set
 * new.marketType ='4614'; set new.exchange_type='2'; elseif new.`code` LIKE
 * '300%' THEN set new.marketType ='4621'; set new.exchange_type='2'; elseif
 * new.`code` LIKE '60%' THEN set new.marketType ='4353'; set
 * new.exchange_type='1'; end if; end
 */

public class StockMonthLineUpByAuto  implements java.io.Serializable {
	private static final long serialVersionUID = -3859526119096611813L;

	private String code;
	private String name;
	private String exchange_type;
	private String marketType;
	private String closePrice;
	private String openPrice;
	private String prevClose;
	private String minPrice;
	private String maxPrice;
	private String isAddToBuy;
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

	public String getExchange_type() {
		return exchange_type;
	}

	public void setExchange_type(String exchange_type) {
		this.exchange_type = exchange_type;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public String getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(String prevClose) {
		this.prevClose = prevClose;
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

	public String getIsAddToBuy() {
		return isAddToBuy;
	}

	public void setIsAddToBuy(String isAddToBuy) {
		this.isAddToBuy = isAddToBuy;
	}

}
