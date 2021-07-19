package com.ly.pojo;

public class Sell  implements java.io.Serializable {
	private static final long serialVersionUID = -3859526119096611813L;
	private String code;
	private String name;
	private String weiTuoBuyPrice;
	private String weiTuoTodaySellPrice;
	private String weiTuoBuyNum;
	private String buyDate;
	private String exchange_type;
	private String marketType;
	private Integer count;
	
	
	public String getWeiTuoTodaySellPrice() {
		return weiTuoTodaySellPrice;
	}
	public void setWeiTuoTodaySellPrice(String weiTuoTodaySellPrice) {
		this.weiTuoTodaySellPrice = weiTuoTodaySellPrice;
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
	public String getWeiTuoBuyPrice() {
		return weiTuoBuyPrice;
	}
	public void setWeiTuoBuyPrice(String weiTuoBuyPrice) {
		this.weiTuoBuyPrice = weiTuoBuyPrice;
	}
	public String getWeiTuoBuyNum() {
		return weiTuoBuyNum;
	}
	public void setWeiTuoBuyNum(String weiTuoBuyNum) {
		this.weiTuoBuyNum = weiTuoBuyNum;
	}
	
	public String getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
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

	

}
