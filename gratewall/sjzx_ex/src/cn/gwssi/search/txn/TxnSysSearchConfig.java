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
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnSysSearchConfig.class,
														SysSearchConfigContext.class);

	// 数据表名称
	private static final String	TABLE_NAME		= "sys_search_config";

	// 查询列表
	private static final String	ROWSET_FUNCTION	= "select sys_search_config list";

	// 查询记录
	private static final String	SELECT_FUNCTION	= "select one sys_search_config";

	// 修改记录
	private static final String	UPDATE_FUNCTION	= "update one sys_search_config";

	// 增加记录
	private static final String	INSERT_FUNCTION	= "insert one sys_search_config";

	// 删除记录
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
	 * 构造函数
	 */
	public TxnSysSearchConfig()
	{

	}

	/**
	 * 初始化函数
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * 查询全文检索列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn50030101(SysSearchConfigContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoSysSearchConfigSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录集 VoSysSearchConfig result[] = context.getSysSearchConfigs(
		// outputNode );
	}

	/**
	 * 修改全文检索信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn50030102(SysSearchConfigContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 修改记录的内容 VoSysSearchConfig sys_search_config =
		// context.getSysSearchConfig( inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 增加全文检索信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn50030103(SysSearchConfigContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 增加记录的内容 VoSysSearchConfig sys_search_config =
		// context.getSysSearchConfig( inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 查询全文检索用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn50030104(SysSearchConfigContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoSysSearchConfigPrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录内容 VoSysSearchConfig result = context.getSysSearchConfig(
		// outputNode );
	}

	/**
	 * 删除全文检索信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn50030105(SysSearchConfigContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 删除记录的主键列表 VoSysSearchConfigPrimaryKey primaryKey[] =
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
		String queryStr = db.getValue("queryStr");// 查询信息
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
			System.out.println("【txn50030122】---------->>>>> ");
			System.out.println("-----------------【搜索参数】--------------\n "
					+ "查询输入： " + queryStr + " \n" + "当前页数为： " + currentPage
					+ "\n" + "每页显示记录数为： " + countPerPage);
			TRSConnection trscon = null;
			TRSResultSet trsrs = null;
			String words = queryStr;
			try {
				// 建立连接
				trscon = new TRSConnection();
				trscon.connect(SEARCH_HOST, SEARCH_PORT, SEARCH_USER,
						SEARCH_PASSWORD, "T10");
				StringBuffer sbStr = new StringBuffer();
				queryStr = queryStr.trim();
				queryStr = queryStr.replaceAll(" ", " OR ");

				// sbStr.append("内容=(").append(queryStr).append(")");
				sbStr.append("标题/10,内容/5+=(").append(queryStr).append(")");
				if (!strWhere.equals("")) {
					sbStr.append(strWhere);
				}

				if (!filterWhere.equals("")) {
					sbStr.append(filterWhere);
				}

				System.out.println("检索条件为： " + sbStr.toString());

				// 排序并裁减保存的结果记录数，同时自动设置"非精确命中点"模式，需要SERVER6.10.3300以上。
				trscon.SetExtendOption("SORTPRUNE", "1000");
				// 有效排序结果记录数，即部分排序，按排序表达式排序的结果只保证前一部分记录是有序的，需要SERVER6.10.3300以上。
				trscon.SetExtendOption("SORTVALID", "1000");
				// 2：在进行相关性排序时，用一个记录中命中词的单位向量长度，以及命中词的词频和作为记录的相关度。
				// 即当多条记录命中词的单位向量长度相等时，这些记录将再按命中词的词频和的降序排列；
				trscon.SetExtendOption("RELEVANTMODE", "2");
				// 如果值为1，则表示对排序表达式中不存在的字段，按字段无值时情况处理(记录将排在后面)；否则报错。需要SERVER6.50.4000以上。
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
				System.out.println("查询结果：\n " + "结果总数为：" + totalSize + " \n"
						+ "总页数为：" + totalPage + "当前页数为： " + currentPage);

				dbPage.put("currentPage", currentPage);

				// 当检索有结果时
				if (totalSize > 0) {
					Recordset rs = new Recordset();
					// if (totalSize == 1l) {
					// trsrs.moveFirst();
					// // System.out.println("第" + 1 + "条记录"
					// // +trsrs.getString("内容","red"));
					// DataBus dbr = new DataBus();
					// // dbr.setValue("resultStr", "[1]
					// //
					// "+trsrs.getString("标题")+"<BR>"+trsrs.getString("内容","red"));
					//
					// dbr.setValue("resultStr", "[1] "
					// + trsrs.getString("内容", "red"));
					// rs.add(dbr);
					// } else {
					for (long i = (currentPage - 1) * countPerPage; i < (currentPage)
							* countPerPage; i++) {
						long t = i + 1;
						if (t > totalSize) {
							break;
						}
						trsrs.moveTo(0, i);
						// System.out.println("第" + i + "条记录 \n" +"查询条件是
						// "+trsrs.getWhereExpression()+ "\n
						// "+trsrs.getString("内容","red"));
						String showContent = getShowContent(trsrs, words);
						// System.out.println(showContent);
						String href = getURL(trsrs);
						DataBus dbr = new DataBus();
						dbr.setValue("resultStr",
								" <span class='trs-title'> <a href='"
										+ href
										+ "' target='_blank' >"
										+ addHighLighting("red", trsrs
												.getString("标题", "red"), words,
												false) + "</a></span><BR>"
										+ showContent);
						// dbr.setValue("resultStr", "["+t+"]
						// "+trsrs.getString("标题","red")+"<BR>"+trsrs.getString("内容","red"));
						// dbr.setValue("resultStr", "["+t+"]
						// "+trsrs.getString("内容","red"));
						rs.add(dbr);
					}
					// }
					System.out.println("记录数:" + totalSize);

					context.addRecord("record", rs);

					Recordset stats = new Recordset();

					// 统计分类
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
				// 插入系统功能日志
				StringBuffer sql = new StringBuffer(
						"insert into first_page_query( first_page_query_id, first_cls, second_cls, count, num, query_date, query_time, username, opername, orgid, orgname, ipaddress) values(");
				String id = UuidGenerator.getUUID();
				String time = CalendarUtil
						.getCalendarByFormat(CalendarUtil.FORMAT7);
				sql
						.append("'")
						.append(id)
						.append("',")
						.append("'全文检索',")
						.append("'全文检索',")
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
				// 插入全文检索日志
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
				// 输出错误信息
				DataBus dbError = new DataBus();
				dbError.put("code", ex.getErrorCode());
				dbError.setValue("codeStr", ex.getErrorString());
				context.addRecord("error", dbError);
				throw new TxnDataException("error", "检索系统错误");
			} finally {
				// 关闭结果集
				if (trsrs != null)
					trsrs.close();
				trsrs = null;

				// 关闭连接
				if (trscon != null)
					trscon.close();
				trscon = null;
			}
		}
	}

	/*
	 * 处理搜索结果拼链接
	 */
	private String getURL(TRSResultSet trsrs)
	{
		String href = "";
		String tableName = "";
		try {
			tableName = trsrs.getString("表名");

		} catch (TRSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String ent_id = "";
		try {
			ent_id = trsrs.getString("主体ID");
		} catch (TRSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("tableName="+tableName);
		// System.out.println("ent_id="+ent_id);
		if (tableName.equals("REG_INDIV_BASE")
				|| tableName.equals("REG_INDIV_OPERATOR")
				|| tableName.equals("REG_INDIV_BASE_HS")) { // 个体
			// System.out.println("个体");
			href += "/txn60110008.do?primary-key:reg_bus_ent_id=" + ent_id;
		} else if (tableName.equals("ENTER_BLACK")) { // 黑牌 换表了需要重新判断
			// System.out.println("黑牌");
			String ent_name = "";
			String reg_no = "";
			String ent_blk_one_id = "";
			try {
				reg_no = trsrs.getString("企业注册号");
			} catch (TRSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				ent_blk_one_id = trsrs.getString("黑牌ID");
			} catch (TRSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ent_name = trsrs.getString("主体名称");
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

		} else if (tableName.equals("EXC_QUE_REG")) { // 非企业
			// System.out.println("非企业");
			href += "/txn60114003.do?select-key:exc_que_reg_id=" + ent_id;
		} else if (tableName.equals("CASE_BUS_CASE")) { // 如果是案件的情况
			// System.out.println("案件");
			try {
				ent_id = trsrs.getString("案件ID");
			} catch (TRSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			href += "/txn60130004.do?primary-key:case_bus_case_id=" + ent_id;
		} else if (tableName.equals("ENTER_ENTERPRISEBASE")) { // 登记 需实际数据测试
			// System.out.println("总局登记");
			// System.out.println("ent_id="+ent_id);
			String reg_no = "";
			String title = "";
			try {
				reg_no = trsrs.getString("企业注册号");
			} catch (TRSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				title = trsrs.getString("标题");
			} catch (TRSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			href += "/txn60119001.do?select-key:reg_bus_ent_id=" + ent_id
					+ "&select-key:ent_name=" + title + "&select-key:reg_no="
					+ reg_no;
		} else {// 企业机构、投资人，主要人员，主体变更 都在实体里查
			// System.out.println("其他");
			href += "/txn60110001.do?primary-key:reg_bus_ent_id=" + ent_id;
		}

		return href;
	}

	/*
	 * 判断是否汉字
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
	 * 处理搜索结果用于展示
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
			tableName = trsrs.getString("表名");
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
					if (column[i].equals("数据更新日期")) {
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
	 * 查询加高亮,只给汉字加高亮 color 颜色 source 结果 words 查询词 isSplit是否划分到字
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
		String queryStr = db.getValue("queryStr");// 本次查询信息
		if (queryStr == null)
			queryStr = "";
		String oldQueryStr = db.getValue("oldQueryStr");// 上次查询信息
		if (oldQueryStr == null)
			oldQueryStr = "";
		long startNumber = db.getLong("startNumber"); // 开始记录数
		int currentPage = db.getInt("currentPage");// 当前页数
		int countPerPage = db.getInt("countPerPage");// 每页显示记录数
		int totalPage = db.getInt("totalPage");// 总页数
		long totalCount = db.getLong("totalCount");// 总记录数

		if (!queryStr.equals("")) {
			// 本次查询输入查询信息时
			if (!oldQueryStr.equals("")) {
				// 有上次查询时
				if (queryStr.equals(oldQueryStr)) {
					// 考虑分页
					if (currentPage == 0)
						currentPage = 1;
				} else {
					// 新查询
					countPerPage = 10;
					currentPage = 1;
				}

			} else {
				// 新查询
				countPerPage = 10;
				currentPage = 1;
			}
			db.setValue("oldQueryStr", queryStr);

			System.out.println("开始搜索------>>>>> ");
			System.out.println("用户搜索参数：\n " + "查询输入： " + queryStr + " \n"
					+ "上次输入： " + oldQueryStr + " \n" + "当前页数为： " + currentPage
					+ "\n" + "每页显示记录数为： " + countPerPage);
			TRSConnection trscon = null;
			TRSResultSet trsrs = null;

			try {
				// 建立连接
				trscon = new TRSConnection();
				trscon.connect(SEARCH_HOST, SEARCH_PORT, SEARCH_USER,
						SEARCH_PASSWORD);

				// 从demo3中检索标题或正文含有"中国"的记录

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
				System.out.println("查询结果：\n " + "结果总数为：" + totalSize + " \n"
						+ "总页数为：" + totalPage + "当前页数为： " + currentPage);

				db.put("currentPage", currentPage);

				System.out.println("记录数:" + totalSize);

				if (totalSize > 0) {
					Recordset rs = new Recordset();
					if (totalSize == 1l) {
						trsrs.moveFirst();
						System.out.println("第" + 1 + "条记录"
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
							System.out.println("第" + i + "条记录 \n" + "查询条件是 "
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

				// 插入系统功能日志
				StringBuffer sql = new StringBuffer(
						"insert into first_page_query( first_page_query_id, first_cls, second_cls, count, num, query_date, query_time, username, opername, orgid, orgname, ipaddress) values(");
				String id = UuidGenerator.getUUID();
				String time = CalendarUtil
						.getCalendarByFormat(CalendarUtil.FORMAT7);
				sql
						.append("'")
						.append(id)
						.append("',")
						.append("'全文检索',")
						.append("'全文检索',")
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
				// 插入全文检索日志
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
				// 输出错误信息
				System.out.println(ex.getErrorCode() + ":"
						+ ex.getErrorString());
				// ex.printStackTrace();
			} finally {
				// 关闭结果集
				if (trsrs != null)
					trsrs.close();
				trsrs = null;

				// 关闭连接
				if (trscon != null)
					trscon.close();
				trscon = null;
			}
		} else {
			System.out.println("首次进入查询页面！");

		}

	}

	/**
	 * 检索接口调用的通用查询
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50030130(SysSearchConfigContext context) throws TxnException
	{
		DataBus db = context.getRecord("select-key");
		String queryStr = db.getValue("queryStr");// 查询信息
		String svr_name = db.getValue("svr_name");// 接口服务名称
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
			// 查询接口配置
			table.executeFunction("loadSysSearchSvr", context, inputNode,
					outputNode);
			System.out.println(context);
			// 获取要展示的列
			String columns = context.getRecord("record").getValue("columns");
			// 获取要查询的数据源
			String svr_db = context.getRecord("record").getValue("svr_db");
			System.out.println("调用全文检索接口【txn50030130】---------->>>>> ");
			System.out.println("-----------------【搜索参数】--------------\n "
					+ "查询输入： " + queryStr + " \n" + "当前页数为： " + currentPage
					+ "\n" + "每页显示记录数为： " + countPerPage);
			TRSConnection trscon = null;
			TRSResultSet trsrs = null;
			int totalPage = 0;
			try {
				// 建立连接
				trscon = new TRSConnection();
				trscon.connect(SEARCH_HOST, SEARCH_PORT, SEARCH_USER,
						SEARCH_PASSWORD, "T10");
				trsrs = trscon.executeSelect(svr_db, "标题/10,内容/5+=(" + queryStr+ ")", false);
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
				// 当检索有结果时
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
							System.out.println("第" + j + "个 " + column[j]
									+ " \n" + trsrs.getString(column[j]));
						}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭结果集
				if (trsrs != null)
					trsrs.close();
				trsrs = null;

				// 关闭连接
				if (trscon != null)
					trscon.close();
				trscon = null;
			}
		}
		
	}

	/**
	 * 获取所有TRS检索库元数据
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50030131(SysSearchConfigContext context) throws TxnException
	{
		TRSConnection trscon = null;
		// System.out.println("查询所有字段-----------------》》》》》》》》 \n "+context);
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
	 * 获取所有TRS检索库
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
	 * 重载父类的方法，用于替换交易接口的输入变量 调用函数
	 * 
	 * @param funcName
	 *            方法名称
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数");
		}

		// 执行
		SysSearchConfigContext appContext = new SysSearchConfigContext(context);
		invoke(method, appContext);
	}
}
