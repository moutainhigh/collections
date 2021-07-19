package com.gwssi.upload.pojo;

/**
 * 企业经营异常名录股东信息
 * @author Administrator
 *
 */
public class AoOpanomalyInv {
	
	private String blinvid;
	private String busexclist;
	private String pripid;
	private String invid;
	private String invtype;
	private String inv;
	private String certype;
	private String cerno;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getBlinvid() {
		return blinvid;
	}
	public void setBlinvid(String blinvid) {
		this.blinvid = blinvid;
	}
	public String getBusexclist() {
		return busexclist;
	}
	public void setBusexclist(String busexclist) {
		this.busexclist = busexclist;
	}
	public String getPripid() {
		return pripid;
	}
	public void setPripid(String pripid) {
		this.pripid = pripid;
	}
	public String getInvid() {
		return invid;
	}
	public void setInvid(String invid) {
		this.invid = invid;
	}
	public String getInvtype() {
		return invtype;
	}
	public void setInvtype(String invtype) {
		this.invtype = invtype;
	}
	public String getInv() {
		return inv;
	}
	public void setInv(String inv) {
		this.inv = inv;
	}
	public String getCertype() {
		return certype;
	}
	public void setCertype(String certype) {
		this.certype = certype;
	}
	public String getCerno() {
		return cerno;
	}
	public void setCerno(String cerno) {
		this.cerno = cerno;
	}
	
	@Override
	public String toString() {
		return "AoOpanomalyInv [blinvid=" + blinvid + ", busexclist="
				+ busexclist + ", pripid=" + pripid + ", invid=" + invid
				+ ", invtype=" + invtype + ", inv=" + inv + ", certype="
				+ certype + ", cerno=" + cerno + "]";
	}
	
	
}
