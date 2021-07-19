package com.gwssi.upload.pojo;

/**
 * 迁出信息
 * @author Administrator
 *
 */
public class EMoveOut {
	
	private String moutId; 
	private String pripid; 
	private String moutletnum; 
	private String minarea; 
	private String moutrea; 
	private String minareregorg; 
	private String moutdate;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getMoutId() {
		return moutId;
	}
	public void setMoutId(String moutId) {
		this.moutId = moutId;
	}
	public String getPripid() {
		return pripid;
	}
	public void setPripid(String pripid) {
		this.pripid = pripid;
	}
	public String getMoutletnum() {
		return moutletnum;
	}
	public void setMoutletnum(String moutletnum) {
		this.moutletnum = moutletnum;
	}
	public String getMinarea() {
		return minarea;
	}
	public void setMinarea(String minarea) {
		this.minarea = minarea;
	}
	public String getMoutrea() {
		return moutrea;
	}
	public void setMoutrea(String moutrea) {
		this.moutrea = moutrea;
	}
	public String getMoutdate() {
		return moutdate;
	}
	public void setMoutdate(String moutdate) {
		this.moutdate = moutdate;
	}
	public String getMinareregorg() {
		return minareregorg;
	}
	public void setMinareregorg(String minareregorg) {
		this.minareregorg = minareregorg;
	}
	@Override
	public String toString() {
		return "EMoveOut [moutId=" + moutId + ", pripid=" + pripid
				+ ", moutletnum=" + moutletnum + ", minarea=" + minarea
				+ ", moutrea=" + moutrea + ", minareregorg=" + minareregorg
				+ ", moutdate=" + moutdate + ", count=" + count + "]";
	}
	
}
