package com.laoda.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Goods {

	private String goodId;
	private String goodName;
	private String goodPrice;

	public Goods() {
		super();
	}

	public Goods(String goodId) {
		super();
		this.goodId = goodId;
	}

	@Id
	@GeneratedValue(generator = "myId")
	@GenericGenerator(name = "myId", strategy = "uuid")
	public String getGoodId() {
		return goodId;
	}

	@Column
	public String getGoodName() {
		return goodName;
	}

	@Column
	public String getGoodPrice() {
		return goodPrice;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}

}
