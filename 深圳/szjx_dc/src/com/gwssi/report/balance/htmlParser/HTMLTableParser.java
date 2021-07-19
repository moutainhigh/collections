package com.gwssi.report.balance.htmlParser;

import com.gwssi.report.balance.api.ReportModel;

/**
 * @author wuyincheng ,Oct 31, 2016
 * 解析器接口
 */
public interface HTMLTableParser {
	
	public ReportModel parse(String htmlContext);

}
