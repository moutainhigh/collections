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
	 * ���췽��
	 */
	public ConstructSQLFilterString()
	{
	}

	/**
	 * 
	 * @creator caiwd
	 * @createtime 2008-10-15
	 *             ����10:58:38
	 * @param querySql ��ѯsql�ֶμ�����,����
	 * ��select a,b,c from tablename t where 1=1 ��
	 * @param colFilterName �����ֶ����� t.a 
	 * @param colFilterValue ����ֵ
	 * @param filCondition �������� ��= ��<��>��<=��>=��like��liker��likel��
	 * @param filConditionType �ֶ����� N -- number S-- String
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
	 *             ����10:58:46
	 * @param querySql ��ѯsql�ֶμ�����,����
	 * ��select a,b,c from tablename t where 1=1 ��
	 * @param colFilterName �����ֶ����� t.a 
	 * @param colFilterValue ����ֵ
	 * @param filCondition �������� ��= ��<��>��<=��>=��like��liker��likel��
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
