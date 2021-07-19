package com.gwssi.report.balance;

import java.util.ArrayList;
import java.util.List;

/**
 * 表平衡关系映射
 * @author wuyincheng ,Oct 17, 2016
 */
public class BalanceInfo {
	
	private String reportId;      //一张表对应一个平衡(废弃:Update by wuyincheng[Nov 2,2016])
	private String rowBalance;    //行平衡关系：1>=2;2>=3;2=6+8+12
	private String columnBalance; //列平衡关系：1=2+3;2=4+5+6
	private String gridBalance;   //单元格平衡关系：(1,1)>=(13,5),(1,1)>=(14,5)
	//相关联的表都需要加上?
	private String tableBalance;  //表间平衡关系;内资1表(1,2)+内资2表(1,2)==内资3表(1,2)
	
	public BalanceInfo() {}
	
	public List<String> getTableBalanceInfo(){
		return getBalanceInfo(tableBalance);
	}
	
	public List<String> getRowBalanceInfo(){
		return getBalanceInfo(rowBalance);
	}
	
	public List<String> getColumnBalanceInfo(){
		return getBalanceInfo(columnBalance);
	}
	
	public List<String> getGridBalanceInfo(){
		return getBalanceInfo(gridBalance);
	}
	
	private List<String> getBalanceInfo(String balanceInfo){
		List<String> result = null;
		if(balanceInfo != null && !balanceInfo.trim().equals("")){
			result = new ArrayList<String>(16);
			final String [] rbs = balanceInfo.trim().split(";");
			for(String s : rbs){
				result.add(s);
			}
		}
		return result;
	}
	
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getRowBalance() {
		return rowBalance;
	}

	public void setRowBalance(String rowBalance) {
		this.rowBalance = rowBalance;
	}

	public String getColumnBalance() {
		return columnBalance;
	}

	public void setColumnBalance(String columnBalance) {
		this.columnBalance = columnBalance;
	}

	public String getGridBalance() {
		return gridBalance;
	}

	public void setGridBalance(String gridBalance) {
		this.gridBalance = gridBalance;
	}

	public String getTableBalance() {
		return tableBalance;
	}

	public void setTableBalance(String tableBalance) {
		this.tableBalance = tableBalance;
	}
	
	@Override
	public int hashCode() {
		return rowBalance == null ? 0 : rowBalance.hashCode() * 5 + 
		        columnBalance == null ? 0 : columnBalance.hashCode() * 11 + 
		        gridBalance == null ? 0 : gridBalance.hashCode() * 17 + 
		        tableBalance == null ? 0 : tableBalance.hashCode() * 31;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof BalanceInfo){
			final BalanceInfo b = (BalanceInfo) obj;
			return rowBalance == null ? b.rowBalance == null : rowBalance.equals(b.rowBalance) &&
			       columnBalance == null ? b.columnBalance == null : columnBalance.equals(b.columnBalance) &&
			       gridBalance == null ? b.gridBalance == null : columnBalance.equals(b.columnBalance) &&
			       tableBalance == null ? b.tableBalance == null : gridBalance.equals(b.gridBalance); 
		}
		return false;
	}
	
}
