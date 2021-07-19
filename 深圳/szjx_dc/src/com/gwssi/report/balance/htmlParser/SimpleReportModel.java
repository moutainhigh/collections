package com.gwssi.report.balance.htmlParser;

import com.gwssi.report.balance.api.ReportModel;
import com.gwssi.report.model.TCognosReportBO;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * @author wuyincheng ,Nov 1, 2016
 * 简单报表模型
 */
public class SimpleReportModel implements ReportModel{
	
	private String [][] reportData;
	
	private TCognosReportBO reportInfo;
	
	private boolean isEmpty = true;
	
	public SimpleReportModel(String [][] data) {
		this.reportData = data;
		if(!(reportData == null || reportData.length == 0 || reportData[0].length == 0))
			isEmpty = false;
	}

	@Override
	public String getPoint(int row, int column) {
		if(isEmpty || reportData.length <= row || reportData[0].length <= column)
			return null;
		return reportData[row][column];
	}
	
	public void print(){
		for(String [] t : reportData){
			System.out.println(Arrays.toString(t));
		}
	}

	public void setName(TCognosReportBO reportInfo){
		this.reportInfo = reportInfo;
	}
	
	public void setReportInfo(TCognosReportBO reportInfo) {
		this.reportInfo = reportInfo;
	}
	
	@Override
	public TCognosReportBO getReportInfo() {
		return reportInfo;
	}

	@Override
	public int getRows() {
		return isEmpty ? 0 : reportData.length;
	}

	@Override
	public int getColumns() {
		return isEmpty ? 0 : reportData[0].length;
	}
	
	@Override
	public String toString() {
		return "[" + getRows() + " X " + getColumns() + "]";
	}
}