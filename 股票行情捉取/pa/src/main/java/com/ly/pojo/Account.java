package com.ly.pojo;

public class Account  implements java.io.Serializable {
	private static final long serialVersionUID = -3859526119096611813L;
	private String account;
	private String name;
	private String tradeCode;
	private String tokenId;
	private String appName;
	private String updateTime;
	private String isWeiTuo;
	private String keYongZiJing;
	private Integer count;
	
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getKeYongZiJing() {
		return keYongZiJing;
	}

	public void setKeYongZiJing(String keYongZiJing) {
		this.keYongZiJing = keYongZiJing;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsWeiTuo() {
		return isWeiTuo;
	}

	public void setIsWeiTuo(String isWeiTuo) {
		this.isWeiTuo = isWeiTuo;
	}

}
