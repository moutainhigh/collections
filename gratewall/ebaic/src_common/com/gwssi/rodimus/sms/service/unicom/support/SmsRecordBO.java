package com.gwssi.rodimus.sms.service.unicom.support;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * SMS_RECORD表对应的实体类
 */
@Entity
@Table(name = "SMS_RECORD")
public class SmsRecordBO extends AbsDaoBussinessObject {
	
	public SmsRecordBO(){}

	private String smsId;	
	private String bid;	
	private String recvNum;	
	private String content;	
	private String sendTime;	
	private String status;	
	private String reportTime;	
	private String gid;	
	private String reqState;	
	private String repeat;	
	
	
	@Id
	@Column(name = "SMS_ID")
	public String getSmsId() {
		return smsId;
	}
	public void setSmsId(String smsId) {
		this.smsId = smsId;
		markChange("smsId", smsId);
	}
	
	@Column(name = "BID")
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
		markChange("bid", bid);
	}
	@Column(name = "RECV_NUM")
	public String getRecvNum() {
		return recvNum;
	}
	public void setRecvNum(String recvNum) {
		this.recvNum = recvNum;
		markChange("recvNum", recvNum);
	}
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
		markChange("content", content);
	}
	@Column(name = "SEND_TIME")
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
		markChange("sendTime", sendTime);
	}
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		markChange("status", status);
	}
	@Column(name = "REPORT_TfIME")
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
		markChange("repordTime", reportTime);
	}
	@Column(name = "GID")
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
		markChange("gid", gid);
	}
	@Column(name = "REQ_STATE")
	public String getReqState() {
		return reqState;
	}
	public void setReqState(String reqState) {
		this.reqState = reqState;
		markChange("reqState", reqState);
	}
	@Column(name = "REPEAT")
	public String getRepeat() {
		return repeat;
	}
	public void setRepeat(String repeat) {
		this.repeat = repeat;
		markChange("repeat", repeat);
	}
	
}