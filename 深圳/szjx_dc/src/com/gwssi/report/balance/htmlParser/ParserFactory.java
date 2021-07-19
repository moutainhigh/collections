package com.gwssi.report.balance.htmlParser;

import com.gwssi.report.model.TCognosReportBO;

/**
 * @author wuyincheng ,Nov 1, 2016
 * 解析器简单工厂
 */
public class ParserFactory {

	private static final HTMLTableParser SIMPLE_PARSER = new SimpleTableParser();
	
	private static final SHICHANG1Parser SHICHANG_1 = new SHICHANG1Parser();
	
	public static HTMLTableParser createSimpleParser() {
		return SIMPLE_PARSER;
	}
	
	//@TODO 貌似没用上
	public static HTMLTableParser createMultiParser() {
		return new MultiListTableParser();
	}
	
	//@TODO
	public static HTMLTableParser createParser(TCognosReportBO report){
		if(report.getReportname().equals("竞争执法2表") || 
		   report.getReportname().equals("内资4表") ||
		   report.getReportname().equals("广告2表"))
			return createMultiParser();
		if(report.getReportname().equals("市场1表"))
			return SHICHANG_1;
		return SIMPLE_PARSER;
	}
}
