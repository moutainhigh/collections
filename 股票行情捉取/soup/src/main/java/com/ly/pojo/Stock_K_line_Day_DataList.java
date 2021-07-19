package com.ly.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

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

@Entity
@DynamicUpdate(true)
/*@Table(indexes = { @Index(name = "code", columnList = "code"), @Index(name = "name", columnList = "name"),
		@Index(name = "pinyin", columnList = "pinyin") })*/
@Table(indexes = { @Index(name = "code", columnList = "code")})
public class Stock_K_line_Day_DataList implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String closePrice;
	private String preClose;
	private String code;
	private String ma10;
	private String ma10Vol;
	private String ma20;
	private String ma30;
	private String ma5;
	private String ma5Vol;
	//private String ma60;
	private String marketType;
	private String maxPrice;
	private String minPrice;
	private String openPrice;
	private String totalHand;
	private String date;
	private String montime;
	private String fritime;
	//private String zongGuBen;
	//private String liuTongGu;
	//private String hangye;
	
	
	/*@Column
	public String getHangye() {
		return hangye;
	}
	public void setHangye(String hangye) {
		this.hangye = hangye;
	}
	@Column
	public String getZongGuBen() {
		return zongGuBen;
	}
	public void setZongGuBen(String zongGuBen) {
		this.zongGuBen = zongGuBen;
	}
	@Column
	public String getLiuTongGu() {
		return liuTongGu;
	}
	public void setLiuTongGu(String liuTongGu) {
		this.liuTongGu = liuTongGu;
	}*/
	@Column
	public String getPreClose() {
		return preClose;
	}
	public void setPreClose(String preClose) {
		this.preClose = preClose;
	}
	@Column
	public String getClosePrice() {
		return closePrice;
	}
	@Column
	public String getCode() {
		return code;
	}

	@Column
	public String getDate() {
		return date;
	}


	public String getFritime() {
		return fritime;
	}

	@Id
	@GeneratedValue(generator = "myId")
	@GenericGenerator(name = "myId", strategy = "uuid")
	public String getId() {
		return id;
	}

	@Column
	public String getMa10() {
		return ma10;
	}

	@Column
	public String getMa10Vol() {
		return ma10Vol;
	}

	@Column
	public String getMa20() {
		return ma20;
	}

	@Column
	public String getMa30() {
		return ma30;
	}

	@Column
	public String getMa5() {
		return ma5;
	}

	@Column
	public String getMa5Vol() {
		return ma5Vol;
	}

	/*@Column
	public String getMa60() {
		return ma60;
	}*/

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
	public String getMontime() {
		return montime;
	}


	@Column
	public String getOpenPrice() {
		return openPrice;
	}


	@Column
	public String getTotalHand() {
		return totalHand;
	}


	public void setClosePrice(String closePrice) {
		this.closePrice = closePrice;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDate(String date) {
		this.date = date;
	}


	public void setFritime(String fritime) {
		this.fritime = fritime;
	}


	public void setId(String id) {
		this.id = id;
	}


	public void setMa10(String ma10) {
		this.ma10 = ma10;
	}

	public void setMa10Vol(String ma10Vol) {
		this.ma10Vol = ma10Vol;
	}

	public void setMa20(String ma20) {
		this.ma20 = ma20;
	}

	public void setMa30(String ma30) {
		this.ma30 = ma30;
	}

	public void setMa5(String ma5) {
		this.ma5 = ma5;
	}

	public void setMa5Vol(String ma5Vol) {
		this.ma5Vol = ma5Vol;
	}

	/*public void setMa60(String ma60) {
		this.ma60 = ma60;
	}*/

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	@Column
	public void setMontime(String montime) {
		this.montime = montime;
	}


	public void setOpenPrice(String openPrice) {
		this.openPrice = openPrice;
	}

	public void setTotalHand(String totalHand) {
		this.totalHand = totalHand;
	}


}
