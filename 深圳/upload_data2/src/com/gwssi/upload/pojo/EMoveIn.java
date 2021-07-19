package com.gwssi.upload.pojo;

/**
 * 迁入信息
 * @author Administrator
 *
 */
public class EMoveIn {

	private String minId; 
	private String pripid; 
	private String minletnum; 
	private String moutarea; 
	private String minrea; 
	private String moutareregorg; 
	private String mindate;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getMinId() {
		return minId;
	}
	public void setMinId(String minId) {
		this.minId = minId;
	}
	public String getPripid() {
		return pripid;
	}
	public void setPripid(String pripid) {
		this.pripid = pripid;
	}
	public String getMinletnum() {
		return minletnum;
	}
	public void setMinletnum(String minletnum) {
		this.minletnum = minletnum;
	}
	public String getMoutarea() {
		return moutarea;
	}
	public void setMoutarea(String moutarea) {
		this.moutarea = moutarea;
	}
	public String getMinrea() {
		return minrea;
	}
	public void setMinrea(String minrea) {
		this.minrea = minrea;
	}
	public String getMoutareregorg() {
		return moutareregorg;
	}
	public void setMoutareregorg(String moutareregorg) {
		this.moutareregorg = moutareregorg;
	}
	public String getMindate() {
		return mindate;
	}
	public void setMindate(String mindate) {
		this.mindate = mindate;
	}
	@Override
	public String toString() {
		return "EMoveIn [minId=" + minId + ", pripid=" + pripid
				+ ", minletnum=" + minletnum + ", moutarea=" + moutarea
				+ ", minrea=" + minrea + ", moutareregorg=" + moutareregorg
				+ ", mindate=" + mindate + ", count=" + count + "]";
	}
	
}
