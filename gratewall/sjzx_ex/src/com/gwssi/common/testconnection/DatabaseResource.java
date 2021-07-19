package com.gwssi.common.testconnection;

import java.util.ResourceBundle;




public class DatabaseResource
{
	public static final String	DB_CONFIG	= "database";
	public ResourceBundle		sqlFactory	= ResourceBundle.getBundle(DB_CONFIG);
	
	/**
	 * 
	 * getTableNameSql(获取当前用户下的所有表名称的sql语句)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param db_type:数据库类型
	 * @param db_name：用户名
	 * @return        
	 * String       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public String getTableNameSql(String db_type,String db_name){
		String sql = "";
		if("00".equals(db_type)){//oracle
			sql = sqlFactory.getString("00getOracleTableName");
		}else if ("01".equals(db_type)){
			sql = sqlFactory.getString("01getDB2TableName").replace("${owner}$", "'"+db_name+"'");
		}else if ("02".equals(db_type)){
			sql = sqlFactory.getString("02getSqlServerTableName");
		}else if ("03".equals(db_type)){
			sql = sqlFactory.getString("03getMySqlTableName");
		}else if ("04".equals(db_type)){//access
			
		}
		
		System.out.println("sql="+sql);
		return sql;
	}
	
	/**
	 * 
	 * getTableColumnSql(获取表的字段信息sql)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param db_type
	 * @param table_name
	 * @return        
	 * String       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public String getTableColumnSql(String db_type,String table_name){
		String sql = "";
		if("00".equals(db_type)){
			//sql = 
		}else if ("01".equals(db_type)){
			
		}
		
		return sql;
	}
	
	public String  getBigNumType(String db_type)
	{
		String str = "";
		if("00".equals(db_type)){
			str = sqlFactory.getString("oracleBigNumType");
		}else if("01".equals(db_type)){
			str = sqlFactory.getString("db2BigNumType");
		}else if("02".equals(db_type)){
			str = sqlFactory.getString("sqlServerBigNumType");
		}else if("03".equals(db_type)){
			str = sqlFactory.getString("mysqlBigNumType");
		}else if("04".equals(db_type)){
			str = sqlFactory.getString("accessBigNumType");
		}
		
		return str;
	}
	
	/**    
	 * main(这里用一句话描述这个方法的作用)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param args        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1    
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		DatabaseResource dr = new DatabaseResource();
		//dr.getTableNameSql("00","zxk");
		String  str = dr.getBigNumType("00");
		boolean isBigNumType = str.indexOf("CLOB")<0;
		System.out.println("isBigNumType="+isBigNumType);
		//System.out.println(str.indexOf("CLOB")>=0);
		String str1 = "SYS_DB_CONFIG_ID,SYS_DB_VIEW_ID,SYS_DB_USER_ID,CONFIG_NAME,PERMIT_COLUMN,ALIAS_COLUMN,";
		if(str1.endsWith(",")){
			str1 = str1.substring(0, str1.length()-1);
		}
		System.out.println("str1="+str1);

	}

}
