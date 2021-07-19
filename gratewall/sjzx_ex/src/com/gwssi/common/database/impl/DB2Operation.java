package com.gwssi.common.database.impl;

import com.gwssi.common.database.DBOperation;

public class DB2Operation extends DBOperation{
	private final String paginationPefix = "select * from (select a.*,rownumber() over() as row_next from (";
	private final String paginationInfix = ") a) as b where row_next between ";
	private final String paginationSuffix = " and ";

	private static DB2Operation operation = new DB2Operation();
	private static DB2Operation timeOutOperation = new DB2Operation(true);
	private DB2Operation(){}
	
	private DB2Operation(boolean limit){
		String max = super.getMaxWaiteTime();
		int maxTime = 60;
		if(max != null && max.trim().equals("")){
			maxTime = Integer.parseInt(max);
		}
		super.setMaxSeconds(maxTime);
	}
	
	public static DB2Operation getInstance(){
		return operation;
	}
	
	public static DB2Operation getTimeOutInstance(){
		return timeOutOperation;
	}
	
	protected String getPaginationSQL(String sql,int from,int to) {
		StringBuffer strBuffer = new StringBuffer(paginationPefix);
		strBuffer.append(sql)
		         .append(paginationInfix)
                 .append(String.valueOf(from))
                 .append(paginationSuffix)
                 .append(String.valueOf(to));
		return strBuffer.toString();
	}
}
