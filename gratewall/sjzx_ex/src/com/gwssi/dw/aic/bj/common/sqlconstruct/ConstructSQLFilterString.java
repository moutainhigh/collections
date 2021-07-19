/**
 * 
 */
package com.gwssi.dw.aic.bj.common.sqlconstruct;

/**
 * @author caiwd
 * @createDate 2008-10-15
 *
 */
public class ConstructSQLFilterString
{
	/**
	 * 构造方法
	 */
	public ConstructSQLFilterString()
	{
	}

	/**
	 * 
	 * @creator caiwd
	 * @createtime 2008-10-15
	 *             上午10:58:38
	 * @param querySql 查询sql字段及表构造,列如
	 * （select a,b,c from tablename t where 1=1 ）
	 * @param colFilterName 过滤字段名称 t.a 
	 * @param colFilterValue 过滤值
	 * @param filCondition 过滤条件 （= 、<、>、<=、>=、like、liker、likel）
	 * @param filConditionType 字段类型 N -- number S-- String
	 * @return
	 *
	 */
	public static String conFilterSql(String querySql, String colFilterName,
			String colFilterValue, String filCondition, String filConditionType)
	{

		if (!"".equals(colFilterValue) && null != colFilterValue) {
			if (filCondition.equalsIgnoreCase("like")) {
				querySql += " AND " + colFilterName + " LIKE '%"
						+ colFilterValue + "%'";
			} else if (filCondition.equalsIgnoreCase("liker")) {
				querySql += " AND " + colFilterName + " LIKE '"
						+ colFilterValue + "%'";
			} else if (filCondition.equalsIgnoreCase("likel")) {
				querySql += " AND " + colFilterName + " LIKE '%"
				+ colFilterValue + "'";
	        } else {
	        	if(filConditionType.equalsIgnoreCase("N")){
					querySql += " AND " + colFilterName + " " + filCondition 
					+ colFilterValue ;
	        	}else{
					querySql += " AND " + colFilterName + " " + filCondition + " '"
					+ colFilterValue + "'";
	        	}

			}
		}

		return querySql;
	}
	
	/**
	 * 
	 * @creator caiwd
	 * @createtime 2008-10-15
	 *             上午10:58:46
	 * @param querySql 查询sql字段及表构造,列如
	 * （select a,b,c from tablename t where 1=1 ）
	 * @param colFilterName 过滤字段名称 t.a 
	 * @param colFilterValue 过滤值
	 * @param filCondition 过滤条件 （= 、<、>、<=、>=、like、liker、likel）
	 * @return querySql
	 *
	 */
	public static String conFilterSql(String querySql, String colFilterName,
			String colFilterValue, String filCondition){
		if (!"".equals(colFilterValue) && null != colFilterValue) {
			if (filCondition.equalsIgnoreCase("like")) {
				querySql += " AND " + colFilterName + " LIKE '%"
						+ colFilterValue + "%'";
			} else if (filCondition.equalsIgnoreCase("liker")) {
				querySql += " AND " + colFilterName + " LIKE '"
						+ colFilterValue + "%'";
			} else if (filCondition.equalsIgnoreCase("likel")) {
				querySql += " AND " + colFilterName + " LIKE '%"
				+ colFilterValue + "'";
	        } else {
				querySql += " AND " + colFilterName + " " + filCondition + " '"
				+ colFilterValue + "'";
			}
		}

		return querySql;
	}
}
