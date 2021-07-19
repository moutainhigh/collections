package com.ly.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
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
//https://www.sojson.com/sql.html
//https://www.cnblogs.com/qlqwjy/p/9545453.html
//http://www.cnblogs.com/liangxinxinbo/p/6092664.html
//https://blog.csdn.net/sufei58/article/details/48223731
//http://www.360doc.com/content/14/0806/17/18637323_399899895.shtml
@Entity
@DynamicUpdate(true)
@Table(indexes={@Index(name="code",columnList="code")})
public class Stock implements java.io.Serializable{
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
	private String prefix;
	
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
	//https://www.jianshu.com/p/feb3ffcb32c1
	@Column
	//@ColumnDefault("'0'")
	public String getLiuTongGu() {
		return liuTongGu;
	}
	@Column
	public String getMarketType() {
		return marketType;
	}
	@Column
	public String getName() {
		return name;
	}
	@Column
	public String getPinyin() {
		return pinyin;
	}
	@Column
	public String getPrefix() {
		return prefix;
	}
	@Column
	public String getShiyinglvJing() {
		return shiyinglvJing;
	}
	@Column
	public String getZongGuBen() {
		return zongGuBen;
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
	public void setName(String name) {
		this.name = name;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setShiyinglvJing(String shiyinglvJing) {
		this.shiyinglvJing = shiyinglvJing;
	}
	public void setZongGuBen(String zongGuBen) {
		this.zongGuBen = zongGuBen;
	}
	@Override
	public String toString() {
		return "Stock [code=" + code + ", name=" + name + "]";
	}
	
	
}
