package com.gwssi.socket.share.server;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.StringUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.log.sharelog.dao.ShareLogVo;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�SQLHelper ����������֯���ݲ�ѯSQL�ķ��� �����ˣ�lizheng ����ʱ�䣺Mar 28,
 * 2013 10:12:57 AM �޸��ˣ�lizheng �޸�ʱ�䣺Mar 28, 2013 10:12:57 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public class SQLHelper
{

	// ��־
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
		logger.debug("����queryOldSerCodeSqlִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryServiceSQL ���ݷ���code��ѯ�������ϸ��Ϣ
	 * 
	 * @param serviceCode
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String queryServiceSQL(String serviceCode)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from share_service t where t.service_no =  ");
		sql.append("'");
		sql.append(serviceCode);
		sql.append("'");
		logger.debug("����queryServiceSQLִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	public synchronized static String queryServiceById(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from share_service t where t.service_id =  ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("'");
		logger.debug("����queryServiceSQLִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * getQuerySQL��ѯ�����
	 * 
	 * @param sql
	 * @param startNum
	 * @param endNum
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String getQuerySQL(String sql, int startNum,
			int endNum)
	{
		StringBuffer sb = new StringBuffer(
				"select * from (select a.*,rownum rn from(");
		sb.append(sql).append(") a ").append(") where rn <= ").append(endNum)
				.append(" and rn >= ").append(startNum);
		return sb.toString();
	}

	public synchronized static String getQueryNoOrderSQL(String sql,
			int startNum, int endNum)
	{
		sql = sql.replaceFirst("SELECT ", "SELECT ROWNUM AS RN,");
		StringBuffer sb = new StringBuffer("SELECT * FROM (");
		sb.append(sql).append(" AND ROWNUM<=").append(endNum).append(
				" ) WHERE rn >= ").append(startNum);
		return sb.toString();
	}

	/**
	 * 
	 * getCountSQL��ѯ���������
	 * 
	 * @param querySql
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String getCountSQL(String querySql)
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
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String getLoginSQL(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from res_service_targets t "
				+ "where t.service_targets_id = "
				+ "(select srv.service_targets_id "
				+ "from share_service srv where srv.service_id = ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("') ");
		logger.debug("����getLoginSQLִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * getServiceDateSQL ��ѯ������������
	 * 
	 * @param svrDate
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String getServiceDateSQL(String svrDate)
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

		return getCountSQL(sql.toString());
	}

	/**
	 * 
	 * getServiceRuleSQL ��ѯ�������
	 * 
	 * @param serviceCode
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String getServiceRuleSQL(String serviceCode)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select r.*  ");
		sql.append("from share_service_rule r, share_service s ");
		sql.append("where r.service_id = s.service_id ");
		sql.append("and s.service_no = '");
		sql.append(serviceCode);
		sql.append("' and r.week = '");
		sql.append(DateUtil.getWeek());
		sql.append("'");
		logger.debug("����getServiceRuleSQLִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * getServiceLogSQL ��ѯ������־
	 * 
	 * @param serviceCode
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String getServiceLogSQL(String serviceCode)
	{
		StringBuffer sql = new StringBuffer();
		sql
				.append("select count(s.log_id) TIMES, NVL(sum(s.record_amount), 0) AMOUNT  ");
		sql.append("from (select t.log_id, t.record_amount ");
		sql.append("from share_log t, share_service e ");
		sql.append("where t.service_id = e.service_id ");
		sql.append("and e.service_no = '");
		sql.append(serviceCode);
		sql.append("' and e.is_markup = '");
		sql.append(ExConstant.IS_MARKUP_Y);
		sql.append("' ");
		sql
				.append("and t.service_start_time > to_char(sysdate, 'yyyy-mm-dd') ");
		sql
				.append("and t.service_end_time < to_char(sysdate + 1, 'yyyy-mm-dd') ");

		sql.append(" and t.log_type = '" + ExConstant.LOG_TYPE_USER + "' ");
		sql.append(") s");

		logger.debug("����getServiceLogSQLִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * insertLog ������־
	 * 
	 * @param shareLogVo
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String insertLog(ShareLogVo shareLogVo)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into share_log (log_id,service_id,"
				+ "service_no,service_name,service_targets_id,"
				+ "service_targets_name,targets_type,"
				+ "service_type,access_ip,service_start_time,"
				+ "service_end_time,consume_time,record_start,record_end,"
				+ "record_amount,patameter,service_state,log_type,"
				+ "return_codes) values (");
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
				+ "'");
		sql.append(")");

		logger.debug("����insertLogִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	public synchronized static String queryParamSQL(String serviceId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from share_service_condition t "
				+ "where t.service_id= ");
		sql.append("'");
		sql.append(serviceId);
		sql.append("' and t.need_input = ");
		sql.append("'Y'");
		logger.debug("����queryParamSQLִ�е�SQLΪ��" + sql);
		return sql.toString();
	}
}
