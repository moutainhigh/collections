package com.gwssi.dw.runmgr.services.common;

import com.gwssi.common.util.MD5;


public class SQLHelper
{
	/**
	 * 构建查询语句----通过用户账号、密码获取用户基本信息和用户受限配置信息
	 * @param name
	 * @param pwd
	 * @return
	 */
	
	public synchronized static String loginSQL(String name, String pwd){
//		String md5pwd = new MD5().getMD5ofStr(pwd);
		StringBuffer sql = new StringBuffer();
		//DC2-jufeng-2012-07-07
		sql.append( " SELECT sys_svr_user_id, login_name, user_name, state, user_type, is_ip_bind, ip_bind, is_limit " );
		sql.append( " FROM sys_svr_user  where " );
		sql.append("  login_name").append("='").append(name).append("' AND password='").append(pwd).append("'");
		System.out.println("查询用户登录信息 \n"+sql);
		return sql.toString();
	}
	
	public synchronized static String queryServiceSQL(String whereColumn, String value){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT sys_svr_service_id, max_records, svr_type, svr_name FROM sys_svr_service WHERE ");
		sql.append(whereColumn).append(" ='");
		sql.append(value).append("'");
		
		return sql.toString();
	}
	
	public synchronized static String queryConfigSQL(String uId, String svrId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT sys_svr_config_id,is_pause,query_sql FROM sys_svr_config WHERE sys_svr_user_id='"+uId+"' AND sys_svr_service_id='"+svrId+"'");
		
		return sql.toString();
	}
	
	public synchronized static String getQuerySQL(String sql, int ksjls, int jsjls){
		
		StringBuffer sb = new StringBuffer("select * from (select a.*,rownum rn from(");
		
		sb.append(sql)
		.append(") a ")
		.append(") where rn >= ")
		.append(ksjls)
		.append(" and rn <= ")
		.append(jsjls);
		
		return sb.toString();
	}
	
	/**
	 * 查询服务配置参数SQL
	 * @param configId 配置ID
	 * @param paramType 参数类型：0：系统参数 | 1：用户参数
	 * @return
	 */
	public synchronized static String queryConfigParamSQL(String configId, String paramType){
		StringBuffer sb = new StringBuffer("SELECT p.OPERATOR1, p.LEFT_PAREN, p.LEFT_TABLE_NAME, p.LEFT_COLUMN_NO, p.LEFT_COLUMN_NAME, p.OPERATOR2, p.PARAM_VALUE, p.RIGHT_PAREN, c.edit_type FROM sys_svr_config_param p, sys_column_semantic c where sys_svr_config_id='");
		sb.append(configId)
		  .append("' AND p.param_type=")
		  .append(paramType)
		  .append(" AND p.Left_Column_No=c.column_no ORDER BY PARAM_ORDER");
		
		return sb.toString();
	}
	
	public synchronized static String getTestSQL(String querySql){
		StringBuffer sb = new StringBuffer("select * from (select a.*,rownum rn from(");
		sb.append(querySql)
		.append(") a where rownum <= 5 ) where rn >= 1");
		
		return sb.toString();
	}
			
	public synchronized static String getCountSQL(String querySql){
		StringBuffer sb = new StringBuffer("select count(1) as totals from (");
		sb.append(querySql)
		.append(") t");
		
		return sb.toString();
	}
	
	public synchronized static String getSysLimitSQL(String sys_svr_user_id,String sys_svr_service_id){
		StringBuffer sb = new StringBuffer("select is_limit_week, is_limit_time, is_limit_number, is_limit_total,limit_week, limit_time,limit_start_time, limit_end_time, limit_number, limit_total, limit_desp from SYS_SVR_LIMIT where 1=1 ");
		sb.append(" AND sys_svr_user_id='")
		  .append(sys_svr_user_id)
		  .append("' AND sys_svr_service_id='")
		  .append(sys_svr_service_id).append("' order by limit_week asc ");
		System.out.println("限制条件sql语句是 \n"+sb);
		return sb.toString();
	}
	
	public synchronized static String getLocked(String sys_svr_user_id,String sys_svr_service_id){
		StringBuffer sb = new StringBuffer("select lock_code, lock_time, lock_desp from SYS_SVR_LOCK where 1=1 ");
		sb.append(" AND sys_svr_user_id='")
		  .append(sys_svr_user_id)
		  .append("' AND sys_svr_service_id='")
		  .append(sys_svr_service_id).append("' order by lock_time desc ");
		System.out.println("查询加锁用户服务语句是 \n"+sb);
		return sb.toString();
	}
	public synchronized static String getCountVisited(String sys_svr_user_id,String sys_svr_service_id){
		StringBuffer sb = new StringBuffer("select count(1) as totals from SYS_SVR_VISIT where 1=1 ");
		sb.append(" AND sys_svr_user_id='")
		  .append(sys_svr_user_id)
		  .append("' AND sys_svr_service_id='")
		  .append(sys_svr_service_id).append("' ");
		return sb.toString();
	}
	public synchronized static String getSumVisited(String sys_svr_user_id,String sys_svr_service_id){
		StringBuffer sb = new StringBuffer("select sum(records_mount) as totals from SYS_SVR_VISIT where 1=1 ");
		sb.append(" AND sys_svr_user_id='")
		  .append(sys_svr_user_id)
		  .append("' AND sys_svr_service_id='")
		  .append(sys_svr_service_id).append("' ");
		return sb.toString();
	}
}
