package com.gwssi.report.balance.api;

import com.gwssi.report.model.TCognosReportBO;

/**
 * @author wuyincheng ,Nov 1, 2016
 * 报表数据源接口
 */
public interface ReportSource {
	
	//根据报表摘要信息获取报表模型
	public ReportModel getReport(TCognosReportBO queryInfo);
	
}
