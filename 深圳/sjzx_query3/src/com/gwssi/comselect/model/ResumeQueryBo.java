package com.gwssi.comselect.model;

/**
 * 消保查询Bo类
 * @author yzh
 *
 */
public class ResumeQueryBo {
	
	private String regIno;//登记编号
	private String infoOri;//信息来源
	private String regDep;//登记部门
	private String accRegper;//受理登记人
	private String regTime ;//登记时间
	private String infoType ;//信息类别
	
	public String getRegIno() {
		return regIno;
	}


	public void setRegIno(String regIno) {
		this.regIno = regIno;
	}


	public String getInfoOri() {
		return infoOri;
	}


	public void setInfoOri(String infoOri) {
		this.infoOri = infoOri;
	}


	public String getRegDep() {
		return regDep;
	}


	public void setRegDep(String regDep) {
		this.regDep = regDep;
	}


	public String getAccRegper() {
		return accRegper;
	}


	public void setAccRegper(String accRegper) {
		this.accRegper = accRegper;
	}


	public String getRegTime() {
		return regTime;
	}


	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}


	public String getInfoType() {
		return infoType;
	}


	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	@Override
	public String toString() {
		return "ResumeQueryBo [regIno=" + regIno + ", infoOri=" + infoOri
				+ ", regDep=" + regDep + ", accRegper=" + accRegper
				+ ", regTime=" + regTime + ", infoType=" + infoType + "]";
	}
}
