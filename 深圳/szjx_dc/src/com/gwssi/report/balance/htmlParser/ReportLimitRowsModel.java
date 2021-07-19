package com.gwssi.report.balance.htmlParser;

import java.util.HashMap;
import java.util.Map;

import com.gwssi.report.balance.api.NotValidDataException;
import com.gwssi.report.balance.api.ReportModel;
import com.gwssi.report.model.TCognosReportBO;

/**
 * 1.限制报表行数(参考内资1、内资2等补充资料栏目，不属于行、列校验范围之内)
 * 2.某些行列校验数据为*，非正常数据不参与校验。
 * 参考《工商行政管理系统统计报表制度(2013年版)》
 * @author wuyincheng ,Dec 2, 2016
 */
public class ReportLimitRowsModel implements ReportModel{
	
	//key:reportType+reportName
	private static final Map<String, Integer> REPORT_ROWS = new HashMap<String, Integer>(16){
		private static final long serialVersionUID = 4103219911419074260L;
		{
			put("1内资1表", 109);
			put("1内资2表", 23);
			put("1内资3表", 23);
			put("1外资1表", 12);
			put("1个体1表", 23);
			put("1农合1表", 4);
			put("1消保2表", 12);
			put("1市场2表", 8);
			put("1市场3表", 18);
			put("1市场4表", 11);
			put("1合同1表", 4);
			put("1商标4表", 5);
		}
	};
	
	private ReportModel model;
	
	public ReportLimitRowsModel(ReportModel model) {
		this.model = model;
	}

	@Override
	public TCognosReportBO getReportInfo() {
		return model.getReportInfo();
	}

	@Override
	public void setReportInfo(TCognosReportBO info) {
		model.setReportInfo(info);
	}

	@Override
	public String getPoint(int row, int column) {
		final String res = model.getPoint(row, column);
		if(res == null || res.contains("*"))
			throw new NotValidDataException("NotValidDataException: " + model.getReportInfo().getReportname() + "[" + (row + 1) + "," + (column + 1) + "]: " + res);
		return res;
	}

	@Override
	public int getRows() {
		//如果是带有补充资料的报表，限制行数。（补充资料不需要参与行校验）
		final String key = model.getReportInfo().getReporttype() + model.getReportInfo().getReportname();
		Integer rows = 0;
		if((rows = REPORT_ROWS.get(key)) != null)
			return rows;
		return model.getRows();
	}

	@Override
	public int getColumns() {
		return model.getColumns();
	}
	
	@Override
	public String toString() {
		return this.getRows() + " x " + this.getColumns();
	}

}
