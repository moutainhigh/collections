package com.gwssi.report.auth;

public class SimpleReportInfo{
	
	public String reportName;
	public String reportType;
	
	public SimpleReportInfo(String reportName, String reportType) {
		this.reportName = reportName;
		this.reportType = reportType;
	}
	
	@Override
	public int hashCode() {
		return (this.reportName == null  ? 0 : (this.reportName.hashCode() *11)) +
				(this.reportType == null  ? 0 : (this.reportType.hashCode() * 17));
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		SimpleReportInfo sri = (SimpleReportInfo) obj;
		return (this.reportName == null ? sri.reportName == null : this.reportName.equals(sri.reportName)) && 
				(this.reportType == null ? sri.reportType == null : this.reportType.equals(sri.reportType));
	}
	
	@Override
	public String toString() {
		return this.reportType + "_" + this.reportName;
	}
}