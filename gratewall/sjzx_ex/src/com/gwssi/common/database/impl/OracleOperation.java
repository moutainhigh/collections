package com.gwssi.common.database.impl;

import com.gwssi.common.database.DBOperation;

public class OracleOperation extends DBOperation{
	private final String paginationPefix = "select /*+FIRST_ROWS*/ * from (select a.*,rownum rn from(";
	private final String paginationInfix = ") a )where rn<=";
	private final String paginationSuffix = " and rn>=";
	
	private static OracleOperation operation = new OracleOperation();
	
	private static OracleOperation timeOutOperation = new OracleOperation(true);
	
	
	private OracleOperation(){}
	
	private OracleOperation(boolean limit){
		String max = super.getMaxWaiteTime();
		int maxTime = 60;
		if(max != null && !max.trim().equals("")){
			maxTime = Integer.parseInt(max);
		}
		super.setMaxSeconds(maxTime);
	}
	
	public static OracleOperation getInstance(){
		return operation;
	}
	
	public static OracleOperation getTimeOutInstance(){
		return timeOutOperation;
	}
	
	protected String getPaginationSQL(String sql,int from,int to) {
		StringBuffer strBuffer = new StringBuffer(paginationPefix);
		strBuffer.append(sql)
		         .append(paginationInfix)
                 .append(String.valueOf(to))
                 .append(paginationSuffix)
                 .append(String.valueOf(from));
		System.out.println("sql============>"+strBuffer.toString());
		return strBuffer.toString();
	}
	
	public static void main(String[] args){
		System.out.println(operation.getPaginationSQL("select * from employee order by empno desc", 1, 20));
	}

}
