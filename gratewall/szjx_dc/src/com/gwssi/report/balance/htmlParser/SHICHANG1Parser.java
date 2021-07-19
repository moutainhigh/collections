package com.gwssi.report.balance.htmlParser;

import com.gwssi.report.balance.api.ReportModel;

/**
 * 市场1表解析
 * 需要参考市场1的报表结构
 * @author wuyincheng ,Dec 10, 2016
 */
public class SHICHANG1Parser extends SimpleTableParser{
	
	@Override
	public ReportModel parse(String htmlContext) {
		final SimpleReportModel model = (SimpleReportModel) super.parse(htmlContext);
		String [][] data = new String[27][12];
		for(int i = 0; i < 14; i ++){
			data[i][0] = model.getPoint(i, 0);
		}
		int j = 0;
		for(int i = 14; i < 27; i ++){
			for(j = 1; j < 12; j ++){
				data[i][j] = model.getPoint(i - 14, j + 3);
			}
		}
		return new SimpleReportModel(data);
	}
	
}
