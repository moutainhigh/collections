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
 * CP_WK_DELIVERY_STATUS表对应的实体类
 */
@Entity
@Table(name = "CP_WK_DELIVERY_STATUS")
public class CpWkDeliveryStatusBO extends AbsDaoBussinessObject {
	
	public CpWkDeliveryStatusBO(){}

	private String ddhm;	
	private String psqk;	
	private String yjhm;	
	private String yjzt;	
	private String qsrXm;	
	private String qssj;	
	private String qsfs;	
	private String wttyy;	
	private String gid;	
	private Calendar timestamp;	
	private String bz;
	
	@Id
	@Column(name = "DDHM")
	public String getDdhm(){
		return ddhm;
	}
	public void setDdhm(String ddhm){
		this.ddhm = ddhm;
		markChange("ddhm", ddhm);
	}
	@Column(name = "PSQK")
	public String getPsqk(){
		return psqk;
	}
	public void setPsqk(String psqk){
		this.psqk = psqk;
		markChange("psqk", psqk);
	}
	@Column(name = "YJHM")
	public String getYjhm(){
		return yjhm;
	}
	public void setYjhm(String yjhm){
		this.yjhm = yjhm;
		markChange("yjhm", yjhm);
	}
	@Column(name = "YJZT")
	public String getYjzt(){
		return yjzt;
	}
	public void setYjzt(String yjzt){
		this.yjzt = yjzt;
		markChange("yjzt", yjzt);
	}
	@Column(name = "QSR_XM")
	public String getQsrXm(){
		return qsrXm;
	}
	public void setQsrXm(String qsrXm){
		this.qsrXm = qsrXm;
		markChange("qsrXm", qsrXm);
	}
	@Column(name = "QSSJ")
	public String getQssj(){
		return qssj;
	}
	public void setQssj(String qssj){
		this.qssj = qssj;
		markChange("qssj", qssj);
	}
	@Column(name = "QSFS")
	public String getQsfs(){
		return qsfs;
	}
	public void setQsfs(String qsfs){
		this.qsfs = qsfs;
		markChange("qsfs", qsfs);
	}
	@Column(name = "WTTYY")
	public String getWttyy(){
		return wttyy;
	}
	public void setWttyy(String wttyy){
		this.wttyy = wttyy;
		markChange("wttyy", wttyy);
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
	@Column(name = "BZ")
	public String getBz(){
		return bz;
	}
	public void setBz(String bz){
		this.bz = bz;
		markChange("bz", bz);
	}
}