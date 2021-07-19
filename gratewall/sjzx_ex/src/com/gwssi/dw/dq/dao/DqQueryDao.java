/**
 * 
 */
package com.gwssi.dw.dq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.dw.dq.action.Pager;
import com.gwssi.dw.dq.action.PagerRecords;

/**
 * <p>Title: </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) Sep 17, 2008</p>
 * <p>Company: 长城软件</p>
 * @author zhouyi
 * @version 1.0
 */
public class DqQueryDao
{
	public String queryValue(String sql,String account){
		//DbUtils.selectInteger(sql, );
		String value  = "";
		try {
			Map valueMap = DbUtils.selectOne(sql, account);
			value = valueMap.get("VALUE").toString();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return value;
	}
	
	public String[] queryValues(String[] sqls,Object[][] sqlsValues,String account){
		Connection conn = null;
		String sql;
		Object[] values;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String[] resutls = new String[sqls.length];
		try {
			conn = DbUtils.getConnection(account);
			for(int i=0;i<sqls.length;i++){
				sql = sqls[i].toUpperCase().replaceAll("AND \\( \\)", " ");
				values = sqlsValues[i];
				ps = conn.prepareStatement(sql);
				if(values!=null){
					for(int j=0;j<values.length;j++){
						ps.setObject(j+1, values[j]);
					}
				}
				try {
					rs = ps.executeQuery();
					while(rs.next()){
						resutls[i] = rs.getString(1);
					}
				} catch (RuntimeException e) {
					e.printStackTrace();
					resutls[i] = "0";
				}
			}
			
		} catch (DBException e) {
			System.out.println("数据库查询错误:"+e.getMessage());
		}catch(Exception e){
			System.out.println("行查询错误:"+e.getMessage());
		}finally{
			if(conn!=null){
				try {
					DbUtils.freeConnection(conn);
				} catch (DBException e) {
					e.printStackTrace();
				}
			}
		}
		return resutls;
	}

	/**
	 * 查询统计数据的明细
	 * @param sql
	 * @param values
	 */
	public PagerRecords pagerDetails(Pager pager,String statSql, Object[] values,Map filterMap,
			List detailsList,String entityTable,Set foreignTableNames,String account)
	{
		List valueList = new ArrayList();
		for(int i=0;i<values.length;i++){
			valueList.add(values[i]);
		}
		
		statSql = statSql.toUpperCase();
		StringBuffer sqlBuf = new StringBuffer();
		int whereIndex = statSql.indexOf("WHERE");
		String from;
		
		sqlBuf.append("SELECT ");
		Map detailsMap;
		List detailsFields = new ArrayList();
		Map sortMap = new HashMap();//排序字段
		String flagSort;
		
		String detailsField;
		for(int i=0;i<detailsList.size();i++){
			detailsMap = (Map)detailsList.get(i);
			detailsField = detailsMap.get("DETAILS_FIELD").toString();
			foreignTableNames.add(detailsField.split("\\.")[0]);//加入显示条件上的外键关系表
			if(i>0)sqlBuf.append(",");
			flagSort = detailsMap.get("FLAG_SORT")==null?"0":detailsMap.get("FLAG_SORT").toString();
			if(flagSort.equals("1")){
				sortMap.put(detailsField,"asc");
			}else if(flagSort.equals("2")){
				sortMap.put(detailsField,"desc");
			}
			sqlBuf.append(detailsField);
			detailsFields.add(detailsField);
		}
		
		Iterator filterIter = filterMap.keySet().iterator();
		String filterKey,filterValue;
		StringBuffer filterSqlBuf = new StringBuffer();
		while(filterIter.hasNext()){
			filterKey = filterIter.next().toString();
			filterValue = filterMap.get(filterKey).toString();
			foreignTableNames.add(filterKey.split("\\.")[0]);//加入查询条件上的外键关系表
			if(filterValue!=null&&!filterValue.equals("")){
				filterSqlBuf.append(" and ");
				filterSqlBuf.append(filterKey);
				filterSqlBuf.append(" like ?");
				valueList.add("%"+filterValue+"%");
			}
		}
		
		from  = this.getSqlFrom(entityTable,foreignTableNames,account);
		int leftOuterJoinIndex = statSql.indexOf("LEFT OUTER JOIN");
		if(leftOuterJoinIndex!=-1){
			from = from+" "+statSql.substring(leftOuterJoinIndex,whereIndex-1);
		}
		sqlBuf.append(" FROM ");
		sqlBuf.append(from);
		sqlBuf.append(" ");
		sqlBuf.append(statSql.substring(whereIndex));
		sqlBuf.append(filterSqlBuf);
		//加入排序字段
		if(sortMap.size()>0){
			sqlBuf.append(" order by ");
			Iterator sortIter = sortMap.keySet().iterator();
			String sortKey,sortType;
			while(sortIter.hasNext()){
				sortKey = sortIter.next().toString();
				sortType = sortMap.get(sortKey).toString();
				
				sqlBuf.append(sortKey);
				sqlBuf.append(" ");
				sqlBuf.append(sortType);
				sqlBuf.append(",");
				
				sortKey = null;
				sortType = null;
			}
			sqlBuf.delete(sqlBuf.length()-1, sqlBuf.length());
		}
		
		return findByPager(pager,sqlBuf.toString(),valueList.toArray(new Object[valueList.size()]),detailsFields,account);
	}
	private String getSqlFrom(String entityTable, Set foreignTableNames,String account)
	{
		StringBuffer sqlFrom = new StringBuffer(entityTable);
		foreignTableNames.remove(entityTable);
		if(foreignTableNames.size()>0){//获得外键
			StringBuffer sqlBuf = new StringBuffer();
			Iterator foreignTableIter = foreignTableNames.iterator();
			StringBuffer inBuf = new StringBuffer();
			while(foreignTableIter.hasNext()){
				inBuf.append(",");
				inBuf.append(foreignTableIter.next());
			}
			
			sqlBuf.append("select entity1.entity_table  as FOREIGNTABLE,foreign1.condition from ");
			sqlBuf.append("		(select t.* from dq_entity_foreign t left join dq_entity entity on entity.entity_id=t.entity_id where entity.entity_table= '"+entityTable+"')");
			sqlBuf.append("		foreign1 left join dq_entity entity1 on  foreign1.foreign_entity_id=entity1.entity_id where entity1.entity_table in ('"+inBuf.substring(1)+"')");
			try {
				System.out.println("查询外键关系："+sqlBuf);
				List list = DbUtils.select(sqlBuf.toString(), account);
				Map map;
				for(int i=0;i<list.size();i++){
					map = (Map)list.get(i);
					sqlFrom.append(" left join ");
					sqlFrom.append(map.get("FOREIGNTABLE"));
					sqlFrom.append(" on ");
					sqlFrom.append(map.get("CONDITION"));
					map = null;
				}
			} catch (DBException e) {
				System.out.println("数据库错误："+e.getMessage());
			}
		}
		sqlFrom.append(" ");
		return sqlFrom.toString();
	}

	private PagerRecords findByPager(Pager pager,String sql,Object[] values,List fields,String account){
		String countSql = "select count(*) from ("+sql+")";
		String querySql = this.wrapPagerSql(sql);
		int totalCount =0;
		List list  = new ArrayList();
		int queryType = pager.getQueryType();
		if(queryType==Pager.QUERY_TYPE_ALL||queryType==Pager.QUERY_TYPE_COUNT){//查询数量
			totalCount = this.getCounts(countSql, values, account);
		}
		if(queryType==Pager.QUERY_TYPE_ALL||queryType==Pager.QUERY_TYPE_LIST){//查询结果集
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;	
			try {
				conn = DbUtils.getConnection(account);
				System.out.println("querySql:"+querySql+" values.length"+values.length);
				ps = conn.prepareStatement(querySql);
				for(int i=0;i<values.length;i++){
					ps.setObject(i+1, values[i]);
				}
				
				ps.setInt(values.length+1, pager.getEndIndex());//小于等于
				ps.setInt(values.length+2, pager.getStartIndex());//大于等于
				
				rs = ps.executeQuery();
				
				while(rs.next()){
					String columnName;
					String columnValue;
					Map map = new HashMap();
					for(int i=0;i<fields.size();i++){
						columnName = fields.get(i).toString().toUpperCase().replaceAll("\\.", "_");
						//TODO特殊字段类型处理
						try {
							columnValue = rs.getObject(i+1).toString();
						} catch (RuntimeException e) {
							columnValue = "";
						}
						map.put(columnName,columnValue);
						columnName = null;
						columnValue = null;
					}
					list.add(map);
				}
			} catch (DBException e) {
				System.out.println("1数据库查询错误:"+e.getMessage());
			} catch (SQLException e) {
				System.out.println("2数据库查询错误:"+e.getMessage());
			}finally{
				if(conn!=null){
					try {
						DbUtils.freeConnection(conn);
					} catch (DBException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return new PagerRecords(totalCount,list);
	}
	
	private int getCounts(String countSql,Object[] values,String account){
		int count = 0;
		Connection conn = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DbUtils.getConnection(account);
			ps = conn.prepareStatement(countSql);
			System.out.println("countSql:"+countSql+" values.length"+values.length);
			for(int i=0;i<values.length;i++){
				ps.setObject(i+1, values[i]);
			}
			rs = ps.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
		}catch (DBException e) {
			System.out.println("count:1数据库查询错误:"+e.getMessage());
		} catch (SQLException e) {
			System.out.println("count:2数据库查询错误:"+e.getMessage());
		}finally{
			if(conn!=null){
				try {
					DbUtils.freeConnection(conn);
				} catch (DBException e) {
					e.printStackTrace();
				}
			}
		}
		return count;
	}
	
	protected String wrapPagerSql(String sql) {
		return "select * from ( select row_.*, rownum rownum_ from ( "+sql+" ) row_ where rownum <= ?) where rownum_ > ?";
	}
	/**
	 * List<Map<String,String>>
	 * @param entityTable
	 * @return
	 */
	public List getEntityDetails(String entityTable,String account){
		String sql = "select " +
				"		DETAILS_ID," +//
				"		DETAILS_FIELD," +//
				"		DETAILS_NAME," +//
				"		DETAILS_WIDTH," +//
				"		DETAILS_HREF," +//
				"		DETAILS_PARAMS," +//
				"		DETAILS_PARAMSALIAS," +//
				"		ISFILTER," +//
				"		ISSHOW," +//
				"		ISACTIVE," +//
				"		DETAILS_NUM," +//
				"		FLAG_SORT " +//
				"	from " +
				"	DQ_ENTITY_DETAILS details, DQ_ENTITY entity " +
				"	where  details.ENTITY_ID=entity.ENTITY_ID and entity.entity_table='"+entityTable+"'" +
				" 	order by details.isshow desc,details.details_num";
		List list = new ArrayList();
		try {
			list = DbUtils.select(sql, account);
		} catch (DBException e) {
			System.out.println("数据库错误！"+e.getMessage());
		}
		return list;
	}
}
