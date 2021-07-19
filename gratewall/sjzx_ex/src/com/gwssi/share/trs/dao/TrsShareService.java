package com.gwssi.share.trs.dao;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.common.util.DateUtil;
import com.gwssi.webservice.server.ExcuteService;
import com.trs.client.TRSConnection;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;

/**
 * ���ݱ�[trs_share_service]�Ĵ�����
 * 
 * @author Administrator
 * 
 */
public class TrsShareService extends BaseTable
{
	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(ExcuteService.class
											.getName());

	private static String	SEARCH_HOST;

	private static String	SEARCH_PORT;

	private static String	SEARCH_USER;

	private static String	SEARCH_PASSWORD;

	private static String	SEARCH_STATS;

	static {
		SEARCH_HOST = java.util.ResourceBundle.getBundle("trs").getString(
				"searchHost");
		SEARCH_PORT = java.util.ResourceBundle.getBundle("trs").getString(
				"searchPort");
		SEARCH_USER = java.util.ResourceBundle.getBundle("trs").getString(
				"searchUser");
		SEARCH_PASSWORD = java.util.ResourceBundle.getBundle("trs").getString(
				"searchPassword");
		SEARCH_STATS = java.util.ResourceBundle.getBundle("trs").getString(
				"searchStats");
	}

	public TrsShareService()
	{

	}

	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register()
	{
		// ������ע���û��Զ��庯���Ĺ���
		// ��������������SQL�������ƣ����ͣ�����
		// ҵ�������ͨ�����º�������:
		// table.executeFunction( "loadTrsShareServiceList", context, inputNode,
		// outputNode );
		// XXX: registerSQLFunction( "loadTrsShareServiceList",
		// DaoFunction.SQL_ROWSET, "��ȡtrs����ӿ��б�" );
		registerSQLFunction("getMaxTrsService_no", DaoFunction.SQL_SELECT,
				"��ȡ��������");
		registerSQLFunction("getTrsCountBySvrObjType", DaoFunction.SQL_ROWSET,
				"ͳ��TRS�ͷ����������");
		registerSQLFunction("queryTrsShareList", DaoFunction.SQL_ROWSET,
				"ͳ��TRS�ͷ����������");
		registerSQLFunction("getTrsCountBySvrState", DaoFunction.SQL_ROWSET,
				"ͳ��TRS�ͷ���״̬����");
		registerSQLFunction("getTrsServiceRule", DaoFunction.SQL_ROWSET,
				"ͳ��TRS�������");
		registerSQLFunction("queryTrsRule", DaoFunction.SQL_ROWSET,
				"��ѯTRS�������");
	}

	/**
	 * ִ��SQL���ǰ�Ĵ���
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * ִ����SQL����Ĵ���
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * XXX:�û��Զ����SQL��� ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼��������� ������������䣬ֻ��Ҫ����һ�����
	 * 
	 * @param request
	 *            ���׵�������
	 * @param inputData
	 *            ������������ڵ�
	 * @return public SqlStatement loadTrsShareServiceList( TxnContext request,
	 *         DataBus inputData ) { SqlStatement stmt = new SqlStatement( );
	 *         stmt.addSqlStmt( "select * from trs_share_service" );
	 *         stmt.setCountStmt( "select count(*) from trs_share_service" );
	 *         return stmt; }
	 */

	public SqlStatement getMaxTrsService_no(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt("select * from (select TO_NUMBER(ltrim(t.trs_service_no,'trsService')) as snum  from trs_share_service t  where t.trs_service_no like 'trsService%' order by snum desc) where rownum =1");
		return stmt;
	}

	public SqlStatement getTrsCountBySvrObjType(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sql = "with tmp as (select service_targets_id key,service_targets_name title,service_targets_type, sum(c) amount,"
				+ " show_order from("
				+ "select a.*,case when b.trs_service_id is null then 0 else 1 end c  from("
				+ "select r.service_targets_id,r.service_targets_type,r.service_targets_name,r.show_order "
				+ "from res_service_targets r where r.is_markup = 'Y')a,(select * from trs_share_service "
				+ "where is_markup = 'Y')b where a.service_targets_id = b.service_targets_id(+)) "
				+ "group by service_targets_id,service_targets_type,service_targets_name,show_order)"
				+ " select * from (select * from tmp where service_targets_type = '000' order by show_order)"
				+ " union all select * from (select * from tmp where service_targets_type <> '000'"
				+ " order by service_targets_type, title)";
		// System.out.println("4300001--"+sql);
		stmt.addSqlStmt(sql);
		return stmt;
	}

	public SqlStatement getTrsCountBySvrState(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sql = "select t2.codevalue, t2.codename, nvl(t1.countn, 0) amount"
				+ " from (select t.service_state, count(1) countn"
				+ " from trs_share_service t"
				+ " where t.is_markup = 'Y'"
				+ " group by t.service_state) t1,"
				+ " (select * from codedata c where c.codetype = '��Դ����_һ�����״̬') t2"
				+ " where t1.service_state(+) = t2.codevalue"
				+ " order by codevalue desc";

		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * ��ѯȫ�ļ�������
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getTrsServiceRule(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String trs_service_id = request.getRecord("primary-key").getValue(
				"trs_service_id");
		String sql = "select week, wm_concat(start_time || '-' || end_time) time_str,";
		sql+="max(t.times_day) times_day,max(t.count_dat) count_dat,max(t.total_count_day)" +
		" total_count_day from share_service_rule t where t.service_id = '"+trs_service_id+
		"' group by week order by week ";
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	/**
	 * 
	 * getTrsServiceRule(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param request
	 * @param inputData
	 * @return        
	 * SqlStatement       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement queryTrsRule(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String trs_service_id = request.getRecord("primary-key").getValue(
				"trs_service_id");
		String sql = "select * from share_service_rule where service_id='"+trs_service_id+"'";
		
		stmt.addSqlStmt(sql);
		return stmt;
	}

	public SqlStatement queryTrsShareList(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String service_targets_type = request.getRecord("select-key").getValue(
				"service_targets_type");
		String service_state = request.getRecord("select-key").getValue(
				"service_state");
		System.out.println("service_state=" + service_state);
		String service_targets_id = request.getRecord("select-key").getValue(
				"service_targets_id");
		String created_time = request.getRecord("select-key").getValue(
				"created_time");
		StringBuffer sql = new StringBuffer(
				"select t1.*,t1.service_targets_id as service_targets_id1,SUBSTR(t1.last_modify_time,0,10) as lasttime,t2.yhxm from (select * from trs_share_service s where 1=1 ");
		if (StringUtils.isNotBlank(service_targets_type)) {
			sql.append(" and s.service_targets_id in(select service_targets_id from res_service_targets r");
			sql.append(" where r.service_targets_type='").append(
					service_targets_type);
			sql.append("') ");
		}
		if (StringUtils.isNotBlank(service_targets_id)) {
			sql.append(" and s.service_targets_id ='")
					.append(service_targets_id).append("'");
		}
		if (StringUtils.isNotBlank(service_state)) {
			sql.append(" and s.service_state ='").append(service_state)
					.append("'");
		}
		if (StringUtils.isNotBlank(created_time)) {
			if (!created_time.equals("���ѡ������")) {
				String[] times = DateUtil.getDateRegionByDatePicker(
						created_time, true);
				sql.append(" and s.created_time>='").append(times[0])
						.append("'");
				sql.append(" and s.created_time<='").append(times[1])
						.append("'");
			}
		}
		sql.append(")t1,xt_zzjg_yh_new t2 where t1.last_modify_id=t2.yhid_pk(+) order by t1.last_modify_time desc");
		stmt.addSqlStmt(sql.toString());
		System.out.println(sql.toString());
		stmt.setCountStmt("select count(1) from(" + sql.toString() + ")");
		return stmt;
	}

	public TRSConnection getTrsConnection()
	{
		TRSConnection trscon = null;
		try {
			logger.debug("��������TRS����������");
			trscon = new TRSConnection();
			logger.debug("��ʼ����TRS������");
			trscon.connect(SEARCH_HOST, SEARCH_PORT, SEARCH_USER,
					SEARCH_PASSWORD, "T10");
			logger.debug("����TRS�������ɹ�");

		} catch (TRSException e) {
			logger.debug("����TRS������ʧ��" + e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trscon;
	}

	/**
	 * 
	 * queryTrsDataBase(��ѯTRS������������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������
	 * �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param queryStr
	 *            ��ѯ�ַ�
	 * @param search_db
	 *            ��ѯ�ⷶΧ�ԡ�,���ŷָ�
	 * @param trs_search_column
	 *            ��ѯ���ַ�����Χ
	 * @return
	 * @throws TxnException
	 *             TRSResultSet
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public TRSResultSet queryTrsDataBase(TRSConnection trscon,
			TRSResultSet trsrs, String queryStr, String search_db,
			String trs_search_column) throws TxnException
	{
		try {
			if (null == trscon) {
				trscon = this.getTrsConnection();
			}
			StringBuffer sbStr = new StringBuffer();
			// ׼����ѯ�Ĵ���
			queryStr = queryStr.trim();
			queryStr = preQueryString(queryStr);
			// ׼���������ַ���
			String[] trs_search_column_arry = trs_search_column.split(";");
			if (trs_search_column_arry.length > 1) {// ���ʱĬ��ֻ��ѯ���ֶ�
				sbStr.append("����=(").append(queryStr).append(")");
			} else {
				String[] tmp = trs_search_column_arry[0].split(":");
				if (tmp.length == 2) {
					if (tmp[1].equals("all")) {
						sbStr.append("����=(").append(queryStr).append(")");
					} else {
						sbStr.append(tmp[1] + "+=(").append(queryStr)
								.append(")");
					}
				}
			}
			logger.debug("��������Ϊ�� " + sbStr.toString());
			// ���򲢲ü�����Ľ����¼����ͬʱ�Զ�����"�Ǿ�ȷ���е�"ģʽ����ҪSERVER6.10.3300���ϡ�
			// trscon.SetExtendOption("SORTPRUNE", search_num);
			// ��Ч��������¼�������������򣬰�������ʽ����Ľ��ֻ��֤ǰһ���ּ�¼������ģ���ҪSERVER6.10.3300���ϡ�
			// trscon.SetExtendOption("SORTVALID", search_num);
			// 2���ڽ������������ʱ����һ����¼�����дʵĵ�λ�������ȣ��Լ����дʵĴ�Ƶ����Ϊ��¼����ضȡ�
			// ����������¼���дʵĵ�λ�����������ʱ����Щ��¼���ٰ����дʵĴ�Ƶ�͵Ľ������У�
			trscon.SetExtendOption("RELEVANTMODE", "2");
			// ���ֵΪ1�����ʾ��������ʽ�в����ڵ��ֶΣ����ֶ���ֵʱ�������(��¼�����ں���)�����򱨴���ҪSERVER6.50.4000���ϡ�
			trscon.SetExtendOption("SORTMISCOL", "1");
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String startTime = sDateFormat.format(new java.util.Date()); // ��ȡ��������ǰ������ʱ����
			logger.debug("��ǰʱ��Ϊ��" + startTime + "��ʼ����TRS����...");
			Long start = System.currentTimeMillis(); // ��ʼʱ�����ڼ����ʱ
			trsrs = trscon.executeSelect(search_db, sbStr.toString(), false);
			logger.debug("�ɹ����ؼ������,�����������Ϊ:" + trsrs.getRecordCount());
			Long end = System.currentTimeMillis();
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("TRS��������ʱ��" + consumeTime + "�룡");
			return trsrs;
		} catch (TRSException ex) {
			// ���������Ϣ
			DataBus dbError = new DataBus();
			dbError.put("code", ex.getErrorCode());
			dbError.setValue("codeStr", ex.getErrorString());
			ex.printStackTrace();
			logger.error("TRS���������쳣��");
			throw new TxnDataException("error", ex.getErrorString());
		}
	}

	public static boolean isNumeric(String str)
	{
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean lastCharIsNumeric(String str)
	{
		int chr = str.charAt(str.length() - 1);
		if (chr >= 48 && chr <= 57)
			return true;
		else
			return false;
	}

	/*
	 * ��ѯ�ֶ�Ԥ����
	 */
	public static String preQueryString(String queryStr)
	{
		queryStr = queryStr.trim();
		// System.out.println(queryStr);
		// queryStr = queryStr.replaceAll(" ", " OR ");���ܵ����滻
		StringBuffer result = new StringBuffer();
		if (queryStr.length() < 50) {
			String[] queryPart = queryStr.split(" ");
			for (int i = 0; i < queryPart.length; i++) {
				String tmp = "";
				if (!queryPart[i].equals("")) {
//					if (lastCharIsNumeric(queryPart[i]))// ���һλ�����־ͼӸ�%
//					{
//						tmp = queryPart[i] + "%";
//						// System.out.println(tmp);
//					} else {
//						tmp = queryPart[i];
//					}
					tmp = queryPart[i];
					if (i == 0) {
						result.append("'" + tmp + "'");
					} else {
						result.append(" and '" + tmp + "'");
					}
				}
			}

		} else {
			result.append("LIKE('" + queryStr + "',1)");
		}
		// System.out.println(result.toString());
		return result.toString();
	}

	/**
	 * 
	 * queryTrsDataBase(��ѯTRS������������ҳ) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param queryStr
	 *            ��ѯ�ַ�
	 * @param search_db
	 *            ��ѯ�ⷶΧ�ԡ�,���ŷָ�
	 * @param trs_search_column
	 *            ��ѯ���ַ�����Χ
	 * @param countPerPage
	 *            ÿҳ����
	 * @param currentPage
	 *            ��ǰҳ��
	 * @return
	 * @throws TxnException
	 *             TRSResultSet
	 * @throws TRSException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public TRSResultSet queryTrsDataBase(TRSConnection trscon,
			TRSResultSet trsrs, String queryStr, String search_db,
			String trs_search_column, int countPerPage, int currentPage)
			throws TxnException, TRSException
	{
		trsrs = queryTrsDataBase(trscon, trsrs, queryStr, search_db,
				trs_search_column);
		trsrs.setPageSize(countPerPage);
		trsrs.setPage(currentPage);
		return trsrs;
	}

	public static void main(String[] args) throws TRSException
	{
		TrsShareService trsshare = new TrsShareService();
		TRSResultSet trsrs = null;
		TRSConnection trscon = trsshare.getTrsConnection();

		try {
			trsrs = trsshare.queryTrsDataBase(trscon, trsrs, "����ͨ",
					"interface_12315",
					"interface_12315:name,name_hs,dom,corp_rpt,reg_no", 10, 5);
			System.out.println(trsrs.getRecordCount());
			System.out.println(trsrs.getPage());
			System.out.println(trsrs.getPageCount());
			System.out.println(trsrs.getPageSize());
			System.out.println(trsrs.getPosition());

		} catch (TxnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ر�����

			if (trscon != null) {
				trscon.close();
			}
			trscon = null;
			if (trsrs != null) {
				trsrs.close();
			}
			trsrs = null;
		}
	}
}
