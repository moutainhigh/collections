package com.ly.pojo;

public class SMSSender  implements java.io.Serializable {
	private static final long serialVersionUID = -3859526119096611813L;
	private String appId;
	private String appKey;
	private String sign;
	private String templateIdForBuy;
	private String templateIdForSell;
	private String times;
	private Integer count;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTemplateIdForBuy() {
		return templateIdForBuy;
	}

	public void setTemplateIdForBuy(String templateIdForBuy) {
		this.templateIdForBuy = templateIdForBuy;
	}

	public String getTemplateIdForSell() {
		return templateIdForSell;
	}

	public void setTemplateIdForSell(String templateIdForSell) {
		this.templateIdForSell = templateIdForSell;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "SMSSender [appId=" + appId + ", appKey=" + appKey + ", sign=" + sign + ", templateIdForBuy=" + templateIdForBuy + ", templateIdForSell=" + templateIdForSell + ", times=" + times + ", count=" + count + "]";
	}

}
