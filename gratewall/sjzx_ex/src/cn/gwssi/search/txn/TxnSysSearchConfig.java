package cn.gwssi.search.txn;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.search.vo.SysSearchConfigContext;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;
import com.trs.client.ClassInfo;
import com.trs.client.TRSConnection;
import com.trs.client.TRSDataBase;
import com.trs.client.TRSDataBaseColumn;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;

public class TxnSysSearchConfig extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnSysSearchConfig.class,
														SysSearchConfigContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME		= "sys_search_config";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION	= "select sys_search_config list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION	= "select one sys_search_config";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION	= "update one sys_search_config";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION	= "insert one sys_search_config";

	// ɾ����¼
	private static final String	DELETE_FUNCTION	= "delete one sys_search_config";

	private static String		SEARCH_HOST;

	private static String		SEARCH_PORT;

	private static String		SEARCH_USER;

	private static String		SEARCH_PASSWORD;

	private static String		SEARCH_STATS;

	private static String		SEARCH_DB;

	private static String		SEARCH_COLUMN;

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
		SEARCH_DB = java.util.ResourceBundle.getBundle("trs").getString(
				"searchDB");
		SEARCH_COLUMN = java.util.ResourceBundle.getBundle("trs").getString(
				"search_column");
	}

	/**
	 * ���캯��
	 */
	public TxnSysSearchConfig()
	{

	}

	/**
	 * ��ʼ������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * ��ѯȫ�ļ����б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50030101(SysSearchConfigContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoSysSearchConfigSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoSysSearchConfig result[] = context.getSysSearchConfigs(
		// outputNode );
	}

	/**
	 * �޸�ȫ�ļ�����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50030102(SysSearchConfigContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoSysSearchConfig sys_search_config =
		// context.getSysSearchConfig( inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ����ȫ�ļ�����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50030103(SysSearchConfigContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoSysSearchConfig sys_search_config =
		// context.getSysSearchConfig( inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��ѯȫ�ļ��������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50030104(SysSearchConfigContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoSysSearchConfigPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoSysSearchConfig result = context.getSysSearchConfig(
		// outputNode );
	}

	/**
	 * ɾ��ȫ�ļ�����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50030105(SysSearchConfigContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoSysSearchConfigPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	public void txn50030122(SysSearchConfigContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		VoUser user = context.getOperData();
		long before = System.currentTimeMillis();
		/*
		 * System.out.println("user \n" +user); System.out.println("context is
		 * -------------------------- \n"+context +"\n \n");
		 */
		String ipaddress = user.getValue("ipaddress");

		DataBus db = context.getRecord("select-key");
		String queryStr = db.getValue("queryStr");// ��ѯ��Ϣ
		if (queryStr == null)
			queryStr = "";
		String originQueryStr = queryStr;
		String strWhere = db.getValue("strWhere");
		if (strWhere == null)
			strWhere = "";
		String filterWhere = db.getValue("filterWhere");
		if (filterWhere == null)
			filterWhere = "";
		DataBus dbPage = context.getRecord("page");
		int currentPage = dbPage.getInt("currentPage");
		int totalPage = dbPage.getInt("totalPage");
		long totalCount = dbPage.getLong("totalCount");
		int countPerPage = dbPage.getInt("countPerPage");

		if (!queryStr.equals("")) {
			System.out.println("��txn50030122��---------->>>>> ");
			System.out.println("-----------------������������--------------\n "
					+ "��ѯ���룺 " + queryStr + " \n" + "��ǰҳ��Ϊ�� " + currentPage
					+ "\n" + "ÿҳ��ʾ��¼��Ϊ�� " + countPerPage);
			TRSConnection trscon = null;
			TRSResultSet trsrs = null;
			String words = queryStr;
			try {
				// ��������
				trscon = new TRSConnection();
				trscon.connect(SEARCH_HOST, SEARCH_PORT, SEARCH_USER,
						SEARCH_PASSWORD, "T10");
				StringBuffer sbStr = new StringBuffer();
				queryStr = queryStr.trim();
				queryStr = queryStr.replaceAll(" ", " OR ");

				// sbStr.append("����=(").append(queryStr).append(")");
				sbStr.append("����/10,����/5+=(").append(queryStr).append(")");
				if (!strWhere.equals("")) {
					sbStr.append(strWhere);
				}

				if (!filterWhere.equals("")) {
					sbStr.append(filterWhere);
				}

				System.out.println("��������Ϊ�� " + sbStr.toString());

				// ���򲢲ü�����Ľ����¼����ͬʱ�Զ�����"�Ǿ�ȷ���е�"ģʽ����ҪSERVER6.10.3300���ϡ�
				trscon.SetExtendOption("SORTPRUNE", "1000");
				// ��Ч��������¼�������������򣬰�������ʽ����Ľ��ֻ��֤ǰһ���ּ�¼������ģ���ҪSERVER6.10.3300���ϡ�
				trscon.SetExtendOption("SORTVALID", "1000");
				// 2���ڽ������������ʱ����һ����¼�����дʵĵ�λ�������ȣ��Լ����дʵĴ�Ƶ����Ϊ��¼����ضȡ�
				// ����������¼���дʵĵ�λ�����������ʱ����Щ��¼���ٰ����дʵĴ�Ƶ�͵Ľ������У�
				trscon.SetExtendOption("RELEVANTMODE", "2");
				// ���ֵΪ1�����ʾ��������ʽ�в����ڵ��ֶΣ����ֶ���ֵʱ�������(��¼�����ں���)�����򱨴���ҪSERVER6.50.4000���ϡ�
				trscon.SetExtendOption("SORTMISCOL", "1");

				trsrs = trscon
						.executeSelect(SEARCH_DB, sbStr.toString(), false);

				// trsrs = trscon.executeSelectQuick(SEARCH_DB,
				// sbStr.toString(), "RELEVANCE", "", 0, 100, 0,
				// TRSConstant.TCE_OFFSET);

				trsrs.setPageSize(countPerPage);
				trsrs.setPage(currentPage);
				long totalSize = trsrs.getRecordCount();
				if (totalSize > 0) {
					dbPage.put("totalCount", totalSize);
					if (totalSize % countPerPage == 0) {
						totalPage = (int) totalSize / countPerPage;
					} else {
						totalPage = (int) totalSize / countPerPage + 1;
					}
					dbPage.put("totalPage", totalPage);
				} else {
					dbPage.put("totalPage", 0);
					dbPage.put("totalCount", 0);
				}
				System.out.println("��ѯ�����\n " + "�������Ϊ��" + totalSize + " \n"
						+ "��ҳ��Ϊ��" + totalPage + "��ǰҳ��Ϊ�� " + currentPage);

				dbPage.put("currentPage", currentPage);

				// �������н��ʱ
				if (totalSize > 0) {
					Recordset rs = new Recordset();
					// if (totalSize == 1l) {
					// trsrs.moveFirst();
					// // System.out.println("��" + 1 + "����¼"
					// // +trsrs.getString("����","red"));
					// DataBus dbr = new DataBus();
					// // dbr.setValue("resultStr", "[1]
					// //
					// "+trsrs.getString("����")+"<BR>"+trsrs.getString("����","red"));
					//
					// dbr.setValue("resultStr", "[1] "
					// + trsrs.getString("����", "red"));
					// rs.add(dbr);
					// } else {
					for (long i = (currentPage - 1) * countPerPage; i < (currentPage)
							* countPerPage; i++) {
						long t = i + 1;
						if (t > totalSize) {
							break;
						}
						trsrs.moveTo(0, i);
						// System.out.println("��" + i + "����¼ \n" +"��ѯ������
						// "+trsrs.getWhereExpression()+ "\n
						// "+trsrs.getString("����","red"));
						String showContent = getShowContent(trsrs, words);
						// System.out.println(showContent);
						String href = getURL(trsrs);
						DataBus dbr = new DataBus();
						dbr.setValue("resultStr",
								" <span class='trs-title'> <a href='"
										+ href
										+ "' target='_blank' >"
										+ addHighLighting("red", trsrs
												.getString("����", "red"), words,
												false) + "</a></span><BR>"
										+ showContent);
						// dbr.setValue("resultStr", "["+t+"]
						// "+trsrs.getString("����","red")+"<BR>"+trsrs.getString("����","red"));
						// dbr.setValue("resultStr", "["+t+"]
						// "+trsrs.getString("����","red"));
						rs.add(dbr);
					}
					// }
					System.out.println("��¼��:" + totalSize);

					context.addRecord("record", rs);

					Recordset stats = new Recordset();

					// ͳ�Ʒ���
					String[] sta = SEARCH_STATS.split(",");
					if (sta.length > 0) {

						for (int j = 0; j < sta.length; j++) {
							String stas = sta[j];
							// System.out.println(stas);
							int iClassNum = trsrs.classResult(stas, "", 0, "",
									false, false);
							// System.out.println(iClassNum);
							if (iClassNum > 0) {
								StringBuffer sb = new StringBuffer();
								long size = 0l;
								for (int i = 0; i < iClassNum; i++) {
									ClassInfo classInfo = trsrs.getClassInfo(i);
									size += classInfo.iRecordNum;

								}
								sb.append(stas).append(":").append(
										Long.toString(size));
								// System.out.println("ClassCount= "
								// + trsrs.getClassCount());
								for (int i = 0; i < iClassNum; i++) {
									ClassInfo classInfo = trsrs.getClassInfo(i);
									sb.append(";").append(classInfo.strValue)
											.append(":").append(
													classInfo.iRecordNum);
									// System.out.println(classInfo.toString()+"
									// "+ classInfo.strValue + ": " +
									// classInfo.iValidNum + "/" +
									// classInfo.iRecordNum);
								}
								DataBus dbs = new DataBus();
								dbs.setValue("str", sb.toString());
								stats.add(dbs);
							}
						}
					}

					context.addRecord("stats", stats);

				} else {
					context.addRecord("record", new Recordset());
					context.addRecord("stats", new Recordset());
				}
				long spendtime = System.currentTimeMillis() - before;
				long fromnum = (currentPage - 1) * countPerPage + 1;
				long tonum = (currentPage) * countPerPage;
				// ����ϵͳ������־
				StringBuffer sql = new StringBuffer(
						"insert into first_page_query( first_page_query_id, first_cls, second_cls, count, num, query_date, query_time, username, opername, orgid, orgname, ipaddress) values(");
				String id = UuidGenerator.getUUID();
				String time = CalendarUtil
						.getCalendarByFormat(CalendarUtil.FORMAT7);
				sql
						.append("'")
						.append(id)
						.append("',")
						.append("'ȫ�ļ���',")
						.append("'ȫ�ļ���',")
						.append("1,")
						.append("0,")
						.append("'")
						.append(
								CalendarUtil
										.getCalendarByFormat(CalendarUtil.FORMAT11))
						.append("',").append("'").append(time).append("',")
						.append("'").append(user.getUserName()).append("',")
						.append("'").append(user.getOperName()).append("',")
						.append("'").append(user.getOrgCode()).append("',")
						.append("'").append(user.getOrgName()).append("',")
						.append("'").append(user.getValue("ipaddress")).append(
								"')");

				table.executeUpdate(sql.toString());
				// ����ȫ�ļ�����־
				StringBuffer sql2 = new StringBuffer(
						"insert into sys_search_func_log(search_func_log_id,query,content,time,fromnum,tonum,resultnum,spendtime,ip) values(");
				sql2.append("'").append(id).append("',").append("'").append(
						sbStr).append("',").append("'").append(originQueryStr)
						.append("',").append("'").append(time).append("',")
						.append("'").append(Long.toString(fromnum))
						.append("',").append("'").append(Long.toString(tonum))
						.append("',").append("'").append(
								Long.toString(totalSize)).append("',").append(
								"'").append(Long.toString(spendtime)).append(
								"',").append("'").append(
								user.getValue("ipaddress")).append("')");

				table.executeUpdate(sql2.toString());

			} catch (TRSException ex) {
				// ���������Ϣ
				DataBus dbError = new DataBus();
				dbError.put("code", ex.getErrorCode());
				dbError.setValue("codeStr", ex.getErrorString());
				context.addRecord("error", dbError);
				throw new TxnDataException("error", "����ϵͳ����");
			} finally {
				// �رս����
				if (trsrs != null)
					trsrs.close();
				trsrs = null;

				// �ر�����
				if (trscon != null)
					trscon.close();
				trscon = null;
			}
		}
	}

	/*
	 * �����������ƴ����
	 */
	private String getURL(TRSResultSet trsrs)
	{
		String href = "";
		String tableName = "";
		try {
			tableName = trsrs.getString("����");

		} catch (TRSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String ent_id = "";
		try {
			ent_id = trsrs.getString("����ID");
		} catch (TRSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("tableName="+tableName);
		// System.out.println("ent_id="+ent_id);
		if (tableName.equals("REG_INDIV_BASE")
				|| tableName.equals("REG_INDIV_OPERATOR")
				|| tableName.equals("REG_INDIV_BASE_HS")) { // ����
			// System.out.println("����");
			href += "/txn60110008.do?primary-key:reg_bus_ent_id=" + ent_id;
		} else if (tableName.equals("ENTER_BLACK")) { // ���� ��������Ҫ�����ж�
			// System.out.println("����");
			String ent_name = "";
			String reg_no = "";
			String ent_blk_one_id = "";
			try {
				reg_no = trsrs.getString("��ҵע���");
			} catch (TRSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				ent_blk_one_id = trsrs.getString("����ID");
			} catch (TRSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ent_name = trsrs.getString("��������");
			} catch (TRSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			href += "txn60115010.do?select-key:nodes=HP&inner-flag:open-type=new-window&select-key:ent_sort=HP&select-key:entid="
					+ ent_id
					+ "&select-key:ent_name="
					+ ent_name
					+ "&select-key:reg_no="
					+ reg_no
					+ "&select-key:ent_blk_one_id="
					+ ent_blk_one_id
					+ "&inner-flag:flowno=" + "" + "&inner-flag:flowno=" + "";

		} else if (tableName.equals("EXC_QUE_REG")) { // ����ҵ
			// System.out.println("����ҵ");
			href += "/txn60114003.do?select-key:exc_que_reg_id=" + ent_id;
		} else if (tableName.equals("CASE_BUS_CASE")) { // ����ǰ��������
			// System.out.println("����");
			try {
				ent_id = trsrs.getString("����ID");
			} catch (TRSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			href += "/txn60130004.do?primary-key:case_bus_case_id=" + ent_id;
		} else if (tableName.equals("ENTER_ENTERPRISEBASE")) { // �Ǽ� ��ʵ�����ݲ���
			// System.out.println("�ֵܾǼ�");
			// System.out.println("ent_id="+ent_id);
			String reg_no = "";
			String title = "";
			try {
				reg_no = trsrs.getString("��ҵע���");
			} catch (TRSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				title = trsrs.getString("����");
			} catch (TRSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			href += "/txn60119001.do?select-key:reg_bus_ent_id=" + ent_id
					+ "&select-key:ent_name=" + title + "&select-key:reg_no="
					+ reg_no;
		} else {// ��ҵ������Ͷ���ˣ���Ҫ��Ա�������� ����ʵ�����
			// System.out.println("����");
			href += "/txn60110001.do?primary-key:reg_bus_ent_id=" + ent_id;
		}

		return href;
	}

	/*
	 * �ж��Ƿ���
	 */
	public static String checkChs(String str)
	{
		boolean mark = false;
		Pattern pattern = Pattern.compile("[\u4E00-\u9FA5]");
		Matcher matc = pattern.matcher(str);
		StringBuffer stb = new StringBuffer();
		while (matc.find()) {
			mark = true;
			stb.append(matc.group());
		}
		return stb.toString();
	}

	/*
	 * ���������������չʾ
	 */
	private String getShowContent(TRSResultSet trsrs, String words)
	{
		String result = "";

		// int columnNum = trsrs.getColumnCount();
		// for (int i = 0; i < columnNum; i++) {
		// String colName = trsrs.getColumnName(i);
		// System.out.println("-----------"+colName+"-------------");
		// }
		String tableName = "";
		try {
			tableName = trsrs.getString("����");
			System.out.println("tableName=" + tableName);
		} catch (TRSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String columns = java.util.ResourceBundle.getBundle("trs").getString(
				tableName);
		System.out.println("columns=" + columns);
		String[] column = columns.trim().split(",");
		for (int i = 0; i < column.length; i++) {
			try {
				String content = trsrs.getString(column[i]);
				if (StringUtils.isNotBlank(content)) {
					if (column[i].equals("���ݸ�������")) {
						content = content.replaceAll("\\.", "-");
					}
					result = result +"["+ column[i] + "]:"
							+ addHighLighting("red", content, words, false)
							+ " <br>";
				}
			} catch (TRSException e) {
				e.printStackTrace();
			}
		}
		result = "<div class='trs-content'>" + result + "</div>";
		return result;
	}

	/*
	 * ��ѯ�Ӹ���,ֻ�����ּӸ��� color ��ɫ source ��� words ��ѯ�� isSplit�Ƿ񻮷ֵ���
	 */

	public String addHighLighting(String color, String source, String words,
			boolean isSplit)
	{
		HashSet set = new HashSet();

		if (isSplit) {
			words = checkChs(words);
			String[] word = words.split("");

			for (int i = 0; i < word.length; i++) {
				if (!(word[i].equals("")) && !(word[i].equals(" ")))
					set.add(word[i]);
			}
			Iterator it = set.iterator();
			while (it.hasNext()) {
				String ss = (String) it.next();
				source = source.replaceAll(ss, "<font color='" + color + "'>"
						+ ss + "</font>");

			}
		} else {
			String[] word = words.split(" ");

			for (int i = 0; i < word.length; i++) {

				if (!(word[i].equals("")) && !(word[i].equals(" "))) {
					// System.out.println("word["+i+"]B"+word[i] );
					// word[i] = checkChs(word[i]);
					// System.out.println("word["+i+"]A"+word[i] );
					set.add(word[i]);
				}
			}
			Iterator it = set.iterator();
			while (it.hasNext()) {
				String ss = (String) it.next();
				source = source.replaceAll(ss, "<font color='" + color + "'>"
						+ ss + "</font>");

			}
		}

		return source;
	}

	private String createStrSources(String str)
	{
		String result = "";

		return result;
	}

	public void txn50030121(SysSearchConfigContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		VoUser user = context.getOperData();
		long before = System.currentTimeMillis();
		/*
		 * System.out.println("user \n" +user); System.out.println("context is
		 * -------------------------- \n"+context +"\n \n");
		 */
		String ipaddress = user.getValue("ipaddress");
		System.out.println("ipaddress " + ipaddress);

		DataBus db = context.getRecord(inputNode);
		String queryStr = db.getValue("queryStr");// ���β�ѯ��Ϣ
		if (queryStr == null)
			queryStr = "";
		String oldQueryStr = db.getValue("oldQueryStr");// �ϴβ�ѯ��Ϣ
		if (oldQueryStr == null)
			oldQueryStr = "";
		long startNumber = db.getLong("startNumber"); // ��ʼ��¼��
		int currentPage = db.getInt("currentPage");// ��ǰҳ��
		int countPerPage = db.getInt("countPerPage");// ÿҳ��ʾ��¼��
		int totalPage = db.getInt("totalPage");// ��ҳ��
		long totalCount = db.getLong("totalCount");// �ܼ�¼��

		if (!queryStr.equals("")) {
			// ���β�ѯ�����ѯ��Ϣʱ
			if (!oldQueryStr.equals("")) {
				// ���ϴβ�ѯʱ
				if (queryStr.equals(oldQueryStr)) {
					// ���Ƿ�ҳ
					if (currentPage == 0)
						currentPage = 1;
				} else {
					// �²�ѯ
					countPerPage = 10;
					currentPage = 1;
				}

			} else {
				// �²�ѯ
				countPerPage = 10;
				currentPage = 1;
			}
			db.setValue("oldQueryStr", queryStr);

			System.out.println("��ʼ����------>>>>> ");
			System.out.println("�û�����������\n " + "��ѯ���룺 " + queryStr + " \n"
					+ "�ϴ����룺 " + oldQueryStr + " \n" + "��ǰҳ��Ϊ�� " + currentPage
					+ "\n" + "ÿҳ��ʾ��¼��Ϊ�� " + countPerPage);
			TRSConnection trscon = null;
			TRSResultSet trsrs = null;

			try {
				// ��������
				trscon = new TRSConnection();
				trscon.connect(SEARCH_HOST, SEARCH_PORT, SEARCH_USER,
						SEARCH_PASSWORD);

				// ��demo3�м�����������ĺ���"�й�"�ļ�¼

				trsrs = trscon.executeSelect("TRS_REG_INDIV_BASE", "CONTENT=("
						+ queryStr + ")", false);
				trsrs.setPageSize(countPerPage);
				trsrs.setPage(currentPage);

				long totalSize = trsrs.getRecordCount();
				if (totalSize > 0) {
					db.put("totalCount", totalSize);
					if (totalSize % countPerPage == 0) {
						totalPage = (int) totalSize / countPerPage;
					} else {
						totalPage = (int) totalSize / countPerPage + 1;
					}
					db.put("totalPage", totalPage);
				} else {
					db.put("totalPage", 0);
					db.put("totalCount", 0);
				}
				System.out.println("��ѯ�����\n " + "�������Ϊ��" + totalSize + " \n"
						+ "��ҳ��Ϊ��" + totalPage + "��ǰҳ��Ϊ�� " + currentPage);

				db.put("currentPage", currentPage);

				System.out.println("��¼��:" + totalSize);

				if (totalSize > 0) {
					Recordset rs = new Recordset();
					if (totalSize == 1l) {
						trsrs.moveFirst();
						System.out.println("��" + 1 + "����¼"
								+ trsrs.getString("CONTENT", "red"));
						DataBus dbr = new DataBus();
						dbr.setValue("resultStr", "[1] "
								+ trsrs.getString("ENT_NAME") + "<BR>"
								+ trsrs.getString("CONTENT", "red"));
						rs.add(dbr);
					} else {
						for (long i = (currentPage - 1) * countPerPage; i < (currentPage)
								* countPerPage; i++) {
							long t = i + 1;
							if (t > totalSize) {
								break;
							}
							trsrs.moveTo(0, i);
							System.out.println("��" + i + "����¼ \n" + "��ѯ������ "
									+ trsrs.getWhereExpression() + "\n "
									+ trsrs.getString("CONTENT", "red"));

							DataBus dbr = new DataBus();
							dbr.setValue("resultStr", "[" + t + "] "
									+ trsrs.getString("ENT_NAME", "red")
									+ "<BR>"
									+ trsrs.getString("CONTENT", "red"));
							rs.add(dbr);
						}
					}

					context.addRecord("record", rs);
				} else {
					context.addRecord("record", new Recordset());
				}
				long spendtime = System.currentTimeMillis() - before;
				long fromnum = (currentPage - 1) * countPerPage + 1;
				long tonum = (currentPage) * countPerPage;

				// ����ϵͳ������־
				StringBuffer sql = new StringBuffer(
						"insert into first_page_query( first_page_query_id, first_cls, second_cls, count, num, query_date, query_time, username, opername, orgid, orgname, ipaddress) values(");
				String id = UuidGenerator.getUUID();
				String time = CalendarUtil
						.getCalendarByFormat(CalendarUtil.FORMAT7);
				sql
						.append("'")
						.append(id)
						.append("',")
						.append("'ȫ�ļ���',")
						.append("'ȫ�ļ���',")
						.append("1,")
						.append("0,")
						.append("'")
						.append(
								CalendarUtil
										.getCalendarByFormat(CalendarUtil.FORMAT11))
						.append("',").append("'").append(time).append("',")
						.append("'").append(user.getUserName()).append("',")
						.append("'").append(user.getOperName()).append("',")
						.append("'").append(user.getOrgCode()).append("',")
						.append("'").append(user.getOrgName()).append("',")
						.append("'").append(user.getValue("ipaddress")).append(
								"')");

				table.executeUpdate(sql.toString());
				// ����ȫ�ļ�����־
				StringBuffer sql2 = new StringBuffer(
						"insert into sys_search_func_log(search_func_log_id,query,time,fromnum,tonum,resultnum,spendtime,ip) values(");
				sql2.append("'").append(id).append("',").append("'").append(
						queryStr).append("',").append("'").append(time).append(
						"',").append("'").append(Long.toString(fromnum))
						.append("',").append("'").append(Long.toString(tonum))
						.append("',").append("'").append(
								Long.toString(totalSize)).append("',").append(
								"'").append(Long.toString(spendtime)).append(
								"',").append("'").append(
								user.getValue("ipaddress")).append("')");

				table.executeUpdate(sql2.toString());

			} catch (TRSException ex) {
				// ���������Ϣ
				System.out.println(ex.getErrorCode() + ":"
						+ ex.getErrorString());
				// ex.printStackTrace();
			} finally {
				// �رս����
				if (trsrs != null)
					trsrs.close();
				trsrs = null;

				// �ر�����
				if (trscon != null)
					trscon.close();
				trscon = null;
			}
		} else {
			System.out.println("�״ν����ѯҳ�棡");

		}

	}

	/**
	 * �����ӿڵ��õ�ͨ�ò�ѯ
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50030130(SysSearchConfigContext context) throws TxnException
	{
		DataBus db = context.getRecord("select-key");
		String queryStr = db.getValue("queryStr");// ��ѯ��Ϣ
		String svr_name = db.getValue("svr_name");// �ӿڷ�������
		DataBus dbPage = context.getRecord("page");
		int currentPage = (dbPage.getValue("currentPage") == null ? 1 : Integer
				.parseInt(dbPage.getValue("currentPage")));
		int countPerPage = (dbPage.getValue("countPerPage") == null ? 10
				: Integer.parseInt(dbPage.getValue("countPerPage")));
		// String columns = null;
		// String strWhere = null;
		// String queryPrefix = null;

		System.out.println("queryStr is " + queryStr);
		System.out.println("svr_name is " + svr_name);
		System.out.println("currentPage is " + currentPage);
		System.out.println("countPerPage is " + countPerPage);
		if (StringUtils.isNotBlank(queryStr)
				&& StringUtils.isNotBlank(svr_name)) {
			BaseTable table = TableFactory.getInstance().getTableObject(this,
					TABLE_NAME);
			// ��ѯ�ӿ�����
			table.executeFunction("loadSysSearchSvr", context, inputNode,
					outputNode);
			System.out.println(context);
			// ��ȡҪչʾ����
			String columns = context.getRecord("record").getValue("columns");
			// ��ȡҪ��ѯ������Դ
			String svr_db = context.getRecord("record").getValue("svr_db");
			System.out.println("����ȫ�ļ����ӿڡ�txn50030130��---------->>>>> ");
			System.out.println("-----------------������������--------------\n "
					+ "��ѯ���룺 " + queryStr + " \n" + "��ǰҳ��Ϊ�� " + currentPage
					+ "\n" + "ÿҳ��ʾ��¼��Ϊ�� " + countPerPage);
			TRSConnection trscon = null;
			TRSResultSet trsrs = null;
			int totalPage = 0;
			try {
				// ��������
				trscon = new TRSConnection();
				trscon.connect(SEARCH_HOST, SEARCH_PORT, SEARCH_USER,
						SEARCH_PASSWORD, "T10");
				trsrs = trscon.executeSelect(svr_db, "����/10,����/5+=(" + queryStr+ ")", false);
				trsrs.setPageSize(countPerPage);
				trsrs.setPage(currentPage);
				long totalSize = trsrs.getRecordCount();
				if (totalSize > 0) {
					dbPage.put("totalCount", totalSize);
					if (totalSize % countPerPage == 0) {
						totalPage = (int) totalSize / countPerPage;
					} else {
						totalPage = (int) totalSize / countPerPage + 1;
					}
					dbPage.put("totalPage", totalPage);
				} else {
					dbPage.put("totalPage", 0);
					dbPage.put("totalCount", 0);
				}
				// �������н��ʱ
				if (totalSize > 0) {
					Recordset rs = new Recordset();
					String[] column = columns.split(",");
					for (long i = (currentPage - 1) * countPerPage; i < (currentPage)
							* countPerPage; i++) {
						long t = i + 1;
						if (t > totalSize) {
							break;
						}
						trsrs.moveTo(0, i);

						DataBus dbr = new DataBus();
						StringBuffer sb = new StringBuffer();
						for (int j = 0; j < column.length; j++) {
							System.out.println("��" + j + "�� " + column[j]
									+ " \n" + trsrs.getString(column[j]));
						}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// �رս����
				if (trsrs != null)
					trsrs.close();
				trsrs = null;

				// �ر�����
				if (trscon != null)
					trscon.close();
				trscon = null;
			}
		}
		
	}

	/**
	 * ��ȡ����TRS������Ԫ����
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50030131(SysSearchConfigContext context) throws TxnException
	{
		TRSConnection trscon = null;
		// System.out.println("��ѯ�����ֶ�-----------------���������������� \n "+context);
		DataBus db2 = context.getRecord("select-key");
		String trsDb = db2.getValue("trsDb");
		Recordset rs = new Recordset();

		if (trsDb == null || trsDb.equals("")) {
			trsDb = "*";
		}
		try {
			trscon = new TRSConnection();
			trscon.connect(SEARCH_HOST, SEARCH_PORT, SEARCH_USER,
					SEARCH_PASSWORD, "T10");
			TRSDataBase[] db = trscon.getDataBases(trsDb);
			for (int i = 0; i < db.length; i++) {
				TRSDataBase dbt = db[i];
				String dbName = db[i].getName();
				TRSDataBaseColumn[] columns = dbt.getColumns();
				for (int n = 0; n < columns.length; n++) {
					TRSDataBaseColumn col = columns[n];
					DataBus db3 = new DataBus();
					db3.setValue("column_name", col.getName());
					db3.setValue("column_type", col.getColTypeName());
					db3.setValue("db_name", dbName);
					rs.add(db3);
				}
			}
		} catch (TRSException e) {
			System.out.println("ErrorCode: " + e.getErrorCode());
			System.out.println("ErrorString: " + e.getErrorString());
		} finally {
			if (trscon != null)
				trscon.close();
			trscon = null;
		}
		context.addRecord("record", rs);

	}

	/**
	 * ��ȡ����TRS������
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50030132(SysSearchConfigContext context) throws TxnException
	{
		TRSConnection trscon = null;
		StringBuffer sb = new StringBuffer();
		ArrayList list = new ArrayList();
		Recordset rs = new Recordset();
		try {
			trscon = new TRSConnection();
			trscon.connect(SEARCH_HOST, SEARCH_PORT, SEARCH_USER,
					SEARCH_PASSWORD, "T10");
			TRSDataBase[] db = trscon.getDataBases("*");
			for (int i = 0; i < db.length; i++) {
				TRSDataBase dbt = db[i];
				String dbName = db[i].getName();
				list.add(dbName);

			}
		} catch (TRSException e) {
			System.out.println("ErrorCode: " + e.getErrorCode());
			System.out.println("ErrorString: " + e.getErrorString());
		} finally {
			if (trscon != null)
				trscon.close();
			trscon = null;
		}
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				String name = (String) list.get(j);
				if (j == 0) {
					sb.append(name);
				} else {
					sb.append(",").append(name);
				}
			}
		}
		DataBus db = new DataBus();
		db.setValue("db_name_str", sb.toString());
		context.addRecord("record", db);

	}

	/**
	 * ���ظ���ķ����������滻���׽ӿڵ�������� ���ú���
	 * 
	 * @param funcName
	 *            ��������
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����");
		}

		// ִ��
		SysSearchConfigContext appContext = new SysSearchConfigContext(context);
		invoke(method, appContext);
	}
}
