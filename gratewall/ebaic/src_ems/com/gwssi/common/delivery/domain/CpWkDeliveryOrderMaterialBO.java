package com.gwssi.common.delivery.domain;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * CP_WK_DELIVERY_ORDER_MATERIAL表对应的实体类
 */
@Entity
@Table(name = "CP_WK_DELIVERY_ORDER_MATERIAL")
public class CpWkDeliveryOrderMaterialBO extends AbsDaoBussinessObject {
	
	public CpWkDeliveryOrderMaterialBO(){}
	
	private String njhm;	
	private String ddhm;	
	private String njxh;	
	private String njmc;	
	private String njsl;	
	private String njzl;	
	private String njlx;	
	private String gid;	
	private Calendar timestamp;	
	private String orderId;
	
	@Id
	@Column(name = "NJHM")
	public String getNjhm(){
		return njhm;
	}
	public void setNjhm(String njhm){
		this.njhm = njhm;
		markChange("njhm", njhm);
	}
	
	@Column(name = "DDHM")
	public String getDdhm(){
		return ddhm;
	}
	public void setDdhm(String ddhm){
		this.ddhm = ddhm;
		markChange("ddhm", ddhm);
	}
	@Column(name = "NJXH")
	public String getNjxh(){
		return njxh;
	}
	public void setNjxh(String njxh){
		this.njxh = njxh;
		markChange("njxh", njxh);
	}
	
	@Column(name = "NJMC")
	public String getNjmc(){
		return njmc;
	}
	public void setNjmc(String njmc){
		this.njmc = njmc;
		markChange("njmc", njmc);
	}
	@Column(name = "NJSL")
	public String getNjsl(){
		return njsl;
	}
	public void setNjsl(String njsl){
		this.njsl = njsl;
		markChange("njsl", njsl);
	}
	@Column(name = "NJZL")
	public String getNjzl(){
		return njzl;
	}
	public void setNjzl(String njzl){
		this.njzl = njzl;
		markChange("njzl", njzl);
	}
	@Column(name = "NJLX")
	public String getNjlx(){
		return njlx;
	}
	public void setNjlx(String njlx){
		this.njlx = njlx;
		markChange("njlx", njlx);
	}
	@Column(name = "GID")
	public String getGid(){
		return gid;
	}
	public void setGid(String gid){
		this.gid = gid;
		markChange("gid", gid);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIMESTAMP")
	public Calendar getTimestamp(){
		return timestamp;
	}
	public void setTimestamp(Calendar timestamp){
		this.timestamp = timestamp;
		markChange("timestamp", timestamp);
	}
	@Column(name = "ORDER_ID")
	public String getOrderId(){
		return orderId;
	}
	public void setOrderId(String orderId){
		this.orderId = orderId;
		markChange("orderId", orderId);
	}
}