package com.ly.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;


/*DROP TRIGGER
IF EXISTS UPDATESTS;
CREATE TRIGGER UPDATESTS BEFORE UPDATE ON BUY FOR EACH ROW
BEGIN
IF NEW.`CODE` LIKE '000%' THEN
SET NEW.MARKETTYPE = '4609';
SET NEW.EXCHANGE_TYPE = '2';

ELSEIF NEW.`CODE` LIKE '002%' THEN
SET NEW.MARKETTYPE = '4614';
SET NEW.EXCHANGE_TYPE = '2';

ELSEIF NEW.`CODE` LIKE '300%' THEN
SET NEW.MARKETTYPE = '4621';
SET NEW.EXCHANGE_TYPE = '2';

ELSEIF NEW.`CODE` LIKE '60%' THEN
SET NEW.MARKETTYPE = '4353';
SET NEW.EXCHANGE_TYPE = '1';
END
IF;
END
*/
//http://www.cnblogs.com/liangxinxinbo/p/6092664.html
//https://www.sojson.com/sql.html
//https://www.cnblogs.com/qlqwjy/p/9545453.html
//https://blog.csdn.net/qq_37782076/article/details/83753076
@Entity
@DynamicUpdate(true)
@Table(indexes={@Index(name="code",columnList="code"),@Index(name="name",columnList="name"),@Index(name="pinyin",columnList="pinyin")})
public class Important_Stock_Up implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String exchangeType;
	private String marketType;
	private String pinyin;
	private String hangye;
	private String diQu;
	private String liuTongGu;
	private String shiyinglvJing;
	private String zongGuBen;
	private String closePrice;
	private String openPrice;
	private String prevClose;
	private String qianRiClose;
	private String minPrice;
	private String maxPrice;
	private String time;
	private String totalHand;
	
	private String prefix;
	
	@Column
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	@Column
	public String getQianRiClose() {
		return qianRiClose;
	}


	public void setQianRiClose(String qianRiClose) {
		this.qianRiClose = qianRiClose;
	}


	
	@Column
	public String getClosePrice() {
		return closePrice;
	}
	@Id
	public String getCode() {
		return code;
	}
	@Column
	public String getDiQu() {
		return diQu;
	}
	
	@Column
	public String getExchangeType() {
		return exchangeType;
	}
	@Column
	public String getHangye() {
		return hangye;
	}
	@Column
	public String getLiuTongGu() {
		return liuTongGu;
	}
	@Column
	public String getMarketType() {
		return marketType;
	}
	@Column
	public String getMaxPrice() {
		return maxPrice;
	}
	@Column
	public String getMinPrice() {
		return minPrice;
	}
	@Column
	public String getName() {
		return name;
	}
	@Column
	public String getOpenPrice() {
		return openPrice;
	}
	@Column
	public String getPinyin() {
		return pinyin;
	}
	@Column
	public String getPrevClose() {
		return prevClose;
	}
	@Column
	public String getShiyinglvJing() {
		return shiyinglvJing;
	}
	
	@Column
	public String getTime() {
		return time;
	}


	@Column
	public String getZongGuBen() {
		return zongGuBen;
	}


	@Column
	public String getTotalHand() {
		return totalHand;
	}

	public void setTotalHand(String totalHand) {
		this.totalHand = totalHand;
	}

	public void setClosePrice(String closePrice) {
		this.closePrice = closePrice;
	}


	public void setCode(String code) {
		this.code = code;
	}
	public void setDiQu(String diQu) {
		this.diQu = diQu;
	}
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	public void setHangye(String hangye) {
		this.hangye = hangye;
	}
	public void setLiuTongGu(String liuTongGu) {
		this.liuTongGu = liuTongGu;
	}
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOpenPrice(String openPrice) {
		this.openPrice = openPrice;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public void setPrevClose(String prevClose) {
		this.prevClose = prevClose;
	}
	public void setShiyinglvJing(String shiyinglvJing) {
		this.shiyinglvJing = shiyinglvJing;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setZongGuBen(String zongGuBen) {
		this.zongGuBen = zongGuBen;
	}
}
