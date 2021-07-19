package com.gwssi.common.database;

import com.gwssi.common.database.impl.DB2Operation;
import com.gwssi.common.database.impl.OracleOperation;

public class DBOperationFactory {
	private static final String DB_CONFIG = "app";
	private static final String DB_TYPE = "dbType";

	private static final String DB2 = "db2";
	private static final String ORACLE = "oracle";
	
	public static DBOperation createOperation(){
		String dbType = java.util.ResourceBundle.getBundle(DB_CONFIG)
				.getString(DB_TYPE);
		if (DB2.equalsIgnoreCase(dbType)) {
			return DB2Operation.getInstance();
		} else {
			return OracleOperation.getInstance();
		}
	}
	
	public static DBOperation createTimeOutOperation(){
		String dbType = java.util.ResourceBundle.getBundle(DB_CONFIG).getString(DB_TYPE);
		if (DB2.equalsIgnoreCase(dbType)) {
			return DB2Operation.getTimeOutInstance();
		} else {
			return OracleOperation.getTimeOutInstance();
		}
	}
}
