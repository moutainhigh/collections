package com.ly.pojo;

//https://blog.csdn.net/yangsir7/article/details/80089198

public class StockWeekLineUpByHand implements java.io.Serializable {
	private static final long serialVersionUID = -3859526119096611813L;
	// private String shiYingLv;//静态市盈率=股价/当期每股收益 ，动态市盈率=静态市盈率/（1+年复合增长率）N次方
	private String code;
	private String name;
	private String marketType;
	private String exchange_type;
	private String closePrice;
	private String openPrice;
	private String prevClose;
	private String minPrice;
	private String maxPrice;
	private String isAddToBuy;
	private Integer count;

	public String getIsAddToBuy() {
		return isAddToBuy;
	}

	public void setIsAddToBuy(String isAddToBuy) {
		this.isAddToBuy = isAddToBuy;
	}

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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
