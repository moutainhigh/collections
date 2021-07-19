package com.gwssi.webservice.server;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.StringUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.log.sharelog.dao.ShareLogVo;
import com.gwssi.share.service.vo.ShareLockVo;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：SQLHelper 类描述：组织数据查询SQL的方法 创建人：lizheng 创建时间：Mar 28,
 * 2013 10:12:57 AM 修改人：lizheng 修改时间：Mar 28, 2013 10:12:57 AM 修改备注：
 * 
 * @version
 * 
 */
public class SQLHelper
{

	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(SQLHelper.class
											.getName());

	public synchronized static String queryOldSerCodeSql(String serviceCode,
			String userName)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select s.service_targets_id, s.service_id, "
				+ "s.service_no from share_service s, "
				+ "res_service_targets r where "
				+ "s.service_targets_id = r.service_targets_id "
				+ "and r.service_targets_no = ");
		sql.append("'");
		sql.append(userName);
		sql.append("' ");
		sql.append("and s.old_service_no = ");
		sql.append("'");
		sql.append(serviceCode);
		sql.append("' ");
		logger.debug("方法queryOldSerCodeSql执行的SQL为：" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryService 查询服务
	 * 
	 * @param serviceCode
	 * @param userName
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String queryService(String serviceCode, String userName)
	{
		StringBuffer sql = new StringBuffer();
		sql
				.append("select t.service_targets_id,t.ip,t.service_password,"
						+ "t.service_targets_no,t.is_formal,t.is_bind_ip,"
						+ "t.service_targets_type,t.service_status,s.service_id,"
						+ "s.service_type,s.service_no,s.service_state,s.is_month_data,"
						+ "s.is_markup,t.service_targets_name,s.service_name,"
						+ "s.visit_period from res_service_targets t, share_service s "
						+ "where t.service_targets_id = s.service_targets_id "
						+ "and t.service_targets_no = ");
		sql.append("'");
		sql.append(userName);
		sql.append("' ");
		sql.append("and s.service_no = ");
		sql.append("'");
		sql.append(serviceCode);
		sql.append("' ");
		logger.debug("方法queryService执行的SQL为：" + sql);
		System.out.println("方法queryService执行的SQL为：" + sql);
		return sql.toString();
	}

	public static String queryTrsSerCodeSql(String serviceCode)
	{
		StringBuffer sql = new StringBuffer();
		sql
				.append("select s.service_targets_id, s.TRS_SERVICE_ID as SERVICE_ID, "
						+ "s.TRS_SERVICE_NO as SERVICE_NO from trs_share_service s "
						+ " where ");
		sql.append(" s.TRS_SERVICE_NO = ");
		sql.append("'");
		sql.append(serviceCode);
		sql.append("' ");
		logger.debug("方法queryTrsSerCodeSql执行的SQL为：" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryServiceSQL 根据服务code查询服务的详细信息
	 * 
	 * @param serviceCode
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String queryServiceSQL(String serviceCode)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from share_service t where t.service_no =  ");
		sql.append("'");
		sql.append(serviceCode);
		sql.append("'");
		logger.debug("方法queryServiceSQL执行的SQL为：" + sql);
		return sql.toString();
	}

	public static String queryServiceById(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from share_service t where t.service_id =  ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("'");
		logger.debug("方法queryServiceSQL执行的SQL为：" + sql);
		return sql.toString();
	}

	public static String queryTrsServiceById(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql
				.append("select * from trs_share_service t where t.trs_service_id =  ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("'");
		logger.debug("方法queryServiceSQL执行的SQL为：" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * getQuerySQL查询结果集
	 * 
	 * @param sql
	 * @param startNum
	 * @param endNum
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String getQuerySQL(String serverId, String sql, int startNum,
			int endNum)
	{
		ServerInfo dao = new ServerInfo();
		String tableId = dao.getTableId(serverId);
		String tableName = dao.getTableName(tableId);

		StringBuffer sb = new StringBuffer(
				"select * from (select a.*,rownum rn from(");
		sb.append(sql);
		sb.append(" order by ");
		sb.append(tableName);
		sb.append(") a ");
		sb.append(") where rn <= ");
		sb.append(endNum);
		sb.append(" and rn >= ");
		sb.append(startNum);
		System.out.println("sb is " + sb);
		return sb.toString();
	}

	public static String getQueryNoOrderSQL(String sql, int startNum, int endNum)
	{
		if (sql.contains("/*+ ordered */")) {
			StringBuffer sqlBuffer = new StringBuffer();
			int a = sql.indexOf("*/");
			sqlBuffer.append(sql.substring(0, a + 2));
			sqlBuffer.append(" ROWNUM AS RN,");
			sqlBuffer.append(sql.substring(a + 2, sql.length()));
			sql = sqlBuffer.toString();
		} else {
			sql = sql.replaceFirst("SELECT ", "SELECT ROWNUM AS RN,");
		}
		StringBuffer sb = new StringBuffer("SELECT * FROM (");
		sb.append(sql).append(" AND ROWNUM<=").append(endNum).append(
				" ) WHERE rn >= ").append(startNum);
		return sb.toString();
	}

	/**
	 * 
	 * getCountSQL查询结果总条数
	 * 
	 * @param querySql
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String getCountSQL(String querySql)
	{
		StringBuffer sb = new StringBuffer("select count(1) as totals from (");
		sb.append(querySql).append(") t");
		return sb.toString();
	}

	/**
	 * 
	 * getLoginSQL
	 * 
	 * @param serviceId
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String getLoginSQL(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from res_service_targets t "
				+ "where t.service_targets_id = "
				+ "(select srv.service_targets_id "
				+ "from share_service srv where srv.service_id = ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("') ");
		logger.debug("方法getLoginSQL执行的SQL为：" + sql);
		return sql.toString();
	}

	public static String getTrsLoginSQL(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from res_service_targets t "
				+ "where t.service_targets_id = "
				+ "(select srv.service_targets_id "
				+ "from TRS_SHARE_SERVICE srv where srv.TRS_SERVICE_ID = ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("') ");
		logger.debug("方法getLoginSQL执行的SQL为：" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * getServiceDateSQL 查询服务例外日期
	 * 
	 * @param svrDate
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String getServiceDateSQL(String svrDate)
	{
		StringBuffer sql = new StringBuffer();

		sql.append("select t.exception_date ");
		sql.append("from res_exception_date t ");
		sql.append("where to_date(t.exception_date, 'yyyy-MM-dd') = ");
		sql.append("to_date('");
		sql.append(svrDate);
		sql.append("', 'yyyy-MM-dd')");
		sql.append(" and t.is_markup = '");
		sql.append(ExConstant.IS_MARKUP_Y);
		sql.append("'");
		logger.debug("方法getServiceDateSQL执行的SQL为：" + sql);
		return getCountSQL(sql.toString());
	}

	public static String queryCountDataRule(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) TOTALS ");
		sql.append("from share_service_rule t ");
		sql.append(" where t.service_id = ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("' ");
		sql.append(" and t.week = ");
		sql.append("'");
		sql.append(DateUtil.getWeek());
		sql.append("'");
		logger.debug("方法queryCountDataRule执行的SQL为：" + sql);
		return sql.toString();
	}

	public static String queryDateRule(String serviceId, String nowtime)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) TOTALS from share_service_rule t where ");
		sql.append("'");
		sql.append(nowtime);
		sql.append("' ");
		sql.append("between t.start_time and t.end_time ");
		sql.append("and t.week = ");
		sql.append("'");
		sql.append(DateUtil.getWeek());
		sql.append("' ");
		sql.append("and t.service_id = ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("' ");

		logger.debug("方法queryDateRule执行的SQL为：" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryRule 查询规则 因为存在多条记录可以选择最大的那一条
	 * 
	 * @param serviceId
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String queryRule(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select max(t.times_day) times_day,");
		sql.append("max(t.count_dat) count_dat,");
		sql.append("max(t.total_count_day) total_count_day ");
		sql.append("from share_service_rule t ");
		sql.append("where t.week = ");
		sql.append("'");
		sql.append(DateUtil.getWeek());
		sql.append("' ");
		sql.append(" and t.service_id = ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("' ");
		logger.debug("方法queryRule执行的SQL为：" + sql.toString());
		return sql.toString();
	}

	/**
	 * 
	 * getServiceLogSQL 查询服务日志
	 * 
	 * @param serviceId
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String getServiceLogSQL(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql
				.append("select count(1) TIMES, NVL(sum(t.record_amount), 0) AMOUNT  ");
		sql.append("from share_log t ");
		sql.append("where t.service_id = '");
		sql.append(serviceId);
		sql.append("' ");
		sql
				.append("and t.service_start_time > to_char(sysdate, 'yyyy-mm-dd') ");
		sql
				.append("and t.service_end_time < to_char(sysdate + 1, 'yyyy-mm-dd') ");

		sql.append(" and t.log_type = '" + ExConstant.LOG_TYPE_USER + "' ");
		logger.debug("方法getServiceLogSQL执行的SQL为：" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * insertLog 插入日志
	 * 
	 * @param shareLogVo
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String insertLog(ShareLogVo shareLogVo)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into share_log (log_id,service_id,"
				+ "service_no,service_name,service_targets_id,"
				+ "service_targets_name,targets_type,"
				+ "service_type,access_ip,service_start_time,"
				+ "service_end_time,consume_time,record_start,record_end,"
				+ "record_amount,patameter,service_state,log_type,"
				+ "return_codes,sel_res_consume,"
				+ "sel_count_consume,all_amount,is_formal) values (");
		sql.append("'" + UuidGenerator.getUUID() + "'");
		sql.append(",");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getService_id())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getService_no())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getService_name())
				+ "',");
		sql.append("'"
				+ StringUtil.nullToEmpty(shareLogVo.getService_targets_id())
				+ "',");
		sql.append("'"
				+ StringUtil.nullToEmpty(shareLogVo.getService_targets_name())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getTargets_type())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getService_type())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getAccess_ip())
				+ "',");
		sql.append("'"
				+ StringUtil.nullToEmpty(shareLogVo.getService_start_time())
				+ "',");
		sql.append("'"
				+ StringUtil.nullToEmpty(shareLogVo.getService_end_time())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getConsume_time())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getRecord_start())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getRecord_end())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getRecord_amount())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getPatameter())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getService_state())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getLog_type())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getReturn_codes())
				+ "',");
		sql.append("'"
				+ StringUtil.nullToEmpty(shareLogVo.getSel_res_consume())
				+ "',");
		sql.append("'"
				+ StringUtil.nullToEmpty(shareLogVo.getSel_count_consume())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getAll_amount())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLogVo.getIs_formal())
				+ "'");
		sql.append(")");
		logger.debug("方法insertLog执行的SQL为：" + sql);
		return sql.toString();
	}
	
	
	public static String insertLock(ShareLockVo shareLockVo)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into share_service_lock (service_lock_id,"
				+ "service_targets_id,service_id,lock_code,"
				+ "lock_desp,lock_time) values (");
		sql.append("'" + UuidGenerator.getUUID() + "'");
		sql.append(",");
		sql.append("'" + StringUtil.nullToEmpty(shareLockVo.getService_targets_id())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLockVo.getService_id())
				+ "',");
		sql.append("'"
				+ StringUtil.nullToEmpty(shareLockVo.getLock_code())
				+ "',");
		sql.append("'"
				+ StringUtil.nullToEmpty(shareLockVo.getLock_desc())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(shareLockVo.getLock_time())
				+ "'");
		sql.append(")");
		logger.debug("方法insertLock执行的SQL为：" + sql);
		return sql.toString();
	}

	public static String queryParamSQL(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from share_service_condition t "
				+ "where t.service_id= ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("' and t.need_input = ");
		sql.append("'Y'");
		logger.debug("方法queryParamSQL执行的SQL为：" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryFtpServiceSQL 根据service id查询FTP服务
	 * 
	 * @param serviceId
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String queryFtpServiceSQL(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from share_ftp_service t where t.service_id = ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("'");
		logger.debug("方法queryFtpServiceSQL执行的SQL为：" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryFTPDatasource 根据数据源ID获取数据源的详细信息
	 * 
	 * @param datasourceId
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String queryFTPDatasource(String datasourceId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from res_data_source t where t.data_source_id = ");
		sql.append("'");
		sql.append(datasourceId);
		sql.append("'");
		logger.debug("方法qqueryFTPDatasource执行的SQL为：" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryFtpParamSQL 查询FTP参数
	 * 
	 * @param ftpServiceId
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String queryFtpParamSQL(String ftpServiceId)
	{
		StringBuffer sql = new StringBuffer();
		sql
				.append("select * from share_ftp_srv_param t where t.ftp_service_id = ");
		sql.append("'");
		sql.append(ftpServiceId);
		sql.append("'");
		sql.append(" order by t.showorder");
		logger.debug("方法queryFtpParamSQL执行的SQL为：" + sql);
		return sql.toString();
	}
	
	/**
	 * 
	 * queryServiceLoked 查询服务是否被锁    
	 * @param serviceId
	 * @return        
	 * String       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public static String queryServiceLokedSQL(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * ");
		sql.append("from share_service_lock t ");
		sql.append("where substr(t.lock_time, 0, 10) = to_char(sysdate, 'yyyy-mm-dd') ");
		sql.append("and t.service_id = ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("'");
		logger.debug("方法queryServiceLokedSQL执行的SQL为：" + sql);
		return sql.toString();
	}

	public static void main(String[] args)
	{
		// String sql = "SELECT REG_INDIV_BASE.REG_INDIV_BASE_ID
		// ,REG_INDIV_BASE.INDI_NAME ,REG_INDIV_BASE.REG_NO
		// ,REG_INDIV_BASE.INDI_TYPE ,REG_INDIV_BASE.OP_LOC
		// ,REG_INDIV_OPERATOR.TEL ,REG_INDIV_BASE.PT_BUS_SCOPE
		// ,REG_INDIV_BASE.OPER ,REG_INDIV_OPERATOR.CERTYPE
		// ,REG_INDIV_OPERATOR.CERNO ,REG_INDIV_BASE.DOM_DISTRICT
		// ,REG_INDIV_BASE.EST_DATE ,REG_INDIV_BASE.REG_ORG
		// ,REG_INDIV_BASE.OP_SCOPE ,REG_INDIV_BASE.OP_SUFFIX
		// ,REG_INDIV_BASE.APPROVE_DATE ,REG_INDIV_BASE.INDIVI_STATE
		// ,REG_INDIV_BASE.CANCEL_DATE ,REG_INDIV_BASE.REVOKE_DATE FROM
		// REG_INDIV_BASE,REG_INDIV_INDIVIDUALSUB,REG_INDIV_OPERATOR WHERE
		// REG_INDIV_BASE.REG_INDIV_BASE_ID =
		// REG_INDIV_OPERATOR.REG_INDIV_BASE_ID(+) AND
		// REG_INDIV_BASE.REG_INDIV_BASE_ID =
		// REG_INDIV_INDIVIDUALSUB.REG_INDIV_BASE_ID(+) AND (
		// REG_INDIV_BASE.INDI_NAME <> '***' ) AND (
		// to_date(REG_INDIV_BASE.UPDATE_DATE,'YYYY-MM-DD HH24:mi:ss') >=
		// to_date('2013-07-25 00:00:00','YYYY-MM-DD HH24:mi:ss') AND
		// to_date(REG_INDIV_BASE.UPDATE_DATE,'YYYY-MM-DD HH24:mi:ss') <=
		// to_date('2013-07-25 23:59:59','YYYY-MM-DD HH24:mi:ss') )";
		// String b = getQueryNoOrderSQL(sql, 1, 5);
		// logger.debug("b is " + b);
	}
}
