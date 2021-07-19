package com.gwssi.report.balance.api;

import com.gwssi.report.model.TCognosReportBO;

/**
 * @author wuyincheng ,Nov 1, 2016
 * 报表模型接口
 */
public interface ReportModel {
	
	//reportInfo get/set报表摘要信息
	public TCognosReportBO getReportInfo();
	public void setReportInfo(TCognosReportBO info);
	
	//getSingle point 根据坐标值获取单个点的数值
	public String getPoint(int row, int column);
	//获取总行数
	public int getRows();
	//获取总列数
	public int getColumns();
	
}
