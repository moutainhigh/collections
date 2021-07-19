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

public class Stocks implements java.io.Serializable {
	private static final long serialVersionUID = -3859526119096611813L;
	private String code;
	private String name;
	private String exchange_type;
	private String marketType;
	private String pinyin;
	private Integer count;

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
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
