package com.gwssi.report.balance.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

import com.gwssi.report.balance.api.ReportModel;
import com.gwssi.report.balance.api.ReportSource;
import com.gwssi.report.balance.htmlParser.HTMLTableParser;
import com.gwssi.report.balance.htmlParser.ParserFactory;
import com.gwssi.report.balance.htmlParser.ReportLimitRowsModel;
import com.gwssi.report.model.TCognosReportBO;

import edu.emory.mathcs.backport.java.util.Arrays;
/**
 * test Balance
 * @author wuyincheng ,Nov 9, 2016
 */
public class TestReportSource implements ReportSource{
	
	private HashMap<TCognosReportBO, ReportModel> reportCache =
			new HashMap<TCognosReportBO, ReportModel>(128);
	
	public TestReportSource() {
		init();
		Set<TCognosReportBO> keys = reportCache.keySet();
		for(TCognosReportBO t : keys) {
			System.out.println(reportCache.get(t).getReportInfo().getReportname() + " : " + reportCache.get(t));
		}
		
		System.out.println();
	}
	
	@Override
	public ReportModel getReport(TCognosReportBO queryInfo) {
		return reportCache.get(queryInfo);
	}
	
	private void init() {
		//本地测试
		TCognosReportBO temp = null;
		ReportModel model = null;
		TCognosReportBO queryInfo = new TCognosReportBO();
		String dirPath = "/home/wu/Documents/changcheng/report/平衡关系";
		File f = new File(dirPath);
		HTMLTableParser parser = null;
		if(f.exists() && f.isDirectory()){
			File [] children = f.listFiles();
			System.out.println(Arrays.toString(children));
			for(File t : children){
				if(!t.getName().endsWith("~") && !t.isDirectory()){
					temp = queryInfo.clone();
					temp.setReportname(t.getName());
					temp.setReporttype("1");
					parser = ParserFactory.createParser(temp);
					model = parser.parse(getFileContext(t));
					model.setReportInfo(temp);
//					reportCache.put(temp, model);
					reportCache.put(temp, new ReportLimitRowsModel(model));
				}
			}
		}
		System.out.println("");
	}
	

	private static String getFileContext(File f){
		StringBuilder s = null;
		if(f.exists() && !f.isDirectory()){
			s = new StringBuilder((int) f.length());
		}
		String t = null;
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			while((t = br.readLine()) != null) {
				s.append(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s.toString();
	}
	
	public static void main(String[] args) {
		ReportSource source = new TestReportSource();
		TCognosReportBO t = new TCognosReportBO();
		t.setReportname("市场1表");
		t.setReporttype("1");
		ReportModel model = source.getReport(t);
		
		System.out.println(model);
		System.out.println("Hello");
	}

	
}