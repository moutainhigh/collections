package com.gwssi.common.delivery.domain;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.math.BigDecimal;
import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * CP_WK_DELIVERY_SYNC表对应的实体类
 */
@Entity
@Table(name = "CP_WK_DELIVERY_SYNC")
public class CpWkDeliverySyncBO extends AbsDaoBussinessObject {
	
	public CpWkDeliverySyncBO(){}

	private String id;	
	private String type;	
	private String busiSn;	
	private String sentStatus;	
	private Calendar sentTime;	
	private BigDecimal sentRetryCnt;	
	private String fetchStatus;	
	private Calendar fetchTime;	
	private BigDecimal fetchRetryCnt;	
	private String ddhm;
	//TODO 增加GID
	
	@Id
	@Column(name = "ID")
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
		markChange("id", id);
	}
	@Column(name = "TYPE")
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
		markChange("type", type);
	}
	@Column(name = "BUSI_SN")
	public String getBusiSn(){
		return busiSn;
	}
	public void setBusiSn(String busiSn){
		this.busiSn = busiSn;
		markChange("busiSn", busiSn);
	}
	@Column(name = "SENT_STATUS")
	public String getSentStatus(){
		return sentStatus;
	}
	public void setSentStatus(String sentStatus){
		this.sentStatus = sentStatus;
		markChange("sentStatus", sentStatus);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SENT_TIME")
	public Calendar getSentTime(){
		return sentTime;
	}
	public void setSentTime(Calendar sentTime){
		this.sentTime = sentTime;
		markChange("sentTime", sentTime);
	}
	@Column(name = "SENT_RETRY_CNT")
	public BigDecimal getSentRetryCnt(){
		return sentRetryCnt;
	}
	public void setSentRetryCnt(BigDecimal sentRetryCnt){
		this.sentRetryCnt = sentRetryCnt;
		markChange("sentRetryCnt", sentRetryCnt);
	}
	@Column(name = "FETCH_STATUS")
	public String getFetchStatus(){
		return fetchStatus;
	}
	public void setFetchStatus(String fetchStatus){
		this.fetchStatus = fetchStatus;
		markChange("fetchStatus", fetchStatus);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FETCH_TIME")
	public Calendar getFetchTime(){
		return fetchTime;
	}
	public void setFetchTime(Calendar fetchTime){
		this.fetchTime = fetchTime;
		markChange("fetchTime", fetchTime);
	}
	@Column(name = "FETCH_RETRY_CNT")
	public BigDecimal getFetchRetryCnt(){
		return fetchRetryCnt;
	}
	public void setFetchRetryCnt(BigDecimal fetchRetryCnt){
		this.fetchRetryCnt = fetchRetryCnt;
		markChange("fetchRetryCnt", fetchRetryCnt);
	}
	@Column(name = "DDHM")
	public String getDdhm(){
		return ddhm;
	}
	public void setDdhm(String ddhm){
		this.ddhm = ddhm;
		markChange("ddhm", ddhm);
	}
}