package com.gwssi.resource.collect.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import jxl.read.biff.BiffException;
import oracle.jdbc.OracleConnection;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.ds.ConnectFactory;
import cn.gwssi.common.dao.ds.source.DBController;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.search.trs.TrsSearchServlet;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.database.DBPoolConnection;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.ExlUtil;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

public class ResCollectTable extends BaseTable
{

	protected static Logger	logger	= TxnLogger
											.getLogger(TrsSearchServlet.class
													.getName());	// ��־

	public ResCollectTable()
	{

	}

	/**
	 * ע��SQL���
	 */
	protected void register()
	{
		registerSQLFunction("queryCollectTableList", DaoFunction.SQL_ROWSET,
				"��ѯ���ݱ��б�");
		registerSQLFunction("getItemByTableId", DaoFunction.SQL_ROWSET,
				"��ȡ���ݿ���Ӧ������");
		registerSQLFunction("getItemByTableIdNoCreateTable", DaoFunction.SQL_ROWSET,
				"��ȡ���ݿ��δ����������Ӧ������");
		registerSQLFunction("deleteTableItem", DaoFunction.SQL_DELETE,
				"ɾ�����ݿ���Ӧ������");
		registerSQLFunction("deleteTable", DaoFunction.SQL_DELETE, "ɾ�����ݿ��");
		registerSQLFunction("creatTable", DaoFunction.SQL_UPDATE, "�ɼ����������ݱ�");
		registerSQLFunction("alterTable", DaoFunction.SQL_UPDATE, "�޸Ĳɼ����������ݱ�");
		registerSQLFunction("importExcel", DaoFunction.SQL_INSERT, "����excel");
		registerSQLFunction("queryCollectTableUse", DaoFunction.SQL_ROWSET,
				"��ѯ���ݱ��Ƿ񱻲ɼ���������");
		registerSQLFunction("querySynchroTableList", DaoFunction.SQL_ROWSET,
				"��ѯ��Ҫͬ�������ݱ��б�");
		registerSQLFunction("insertBySync", DaoFunction.SQL_INSERT,
				"����Դͬ��ʱ��δ����������Ϣ");
		registerSQLFunction("getInfoBycjly", DaoFunction.SQL_ROWSET,
				"����ָ�����뼯��÷���ͳ����Ϣ");
		registerSQLFunction("getInfoByTarget", DaoFunction.SQL_ROWSET,
				"����ָ�����뼯��÷���ͳ����Ϣ");
		registerSQLFunction("getInfoBytabType", DaoFunction.SQL_ROWSET,
				"����ָ�����뼯��÷���ͳ����Ϣ");
		registerSQLFunction("getInfoBycrtState", DaoFunction.SQL_ROWSET,
				"����ָ�����뼯��÷���ͳ����Ϣ");
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

	public SqlStatement getInfoBycjly(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append(
							"select cd.codename as title, cd.codevalue as key, NVL(t.mt, 0) as amount ")
					.append("from codedata cd, ")
					.append("(select rescol.")
					.append(column)
					.append(", count(rescol.collect_table_id) as mt ")
					.append(
							"from res_collect_table rescol where rescol.is_markup = 'Y' group by rescol.")
					.append(column).append(") t where cd.codetype = '").append(
							codeType).append("' and cd.codevalue = t.").append(
							column).append("(+) order by amount desc");
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}

	public SqlStatement getInfoByTarget(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String table = inputData.getValue("table_name");
		String column = inputData.getValue("col_name");
		String title = inputData.getValue("col_title");
		if (StringUtils.isNotBlank(table) && StringUtils.isNotBlank(column)
				&& StringUtils.isNotBlank(title)) {
			sqlBuffer
					.append(
							"with tmp as (select * from (select tar." + column + " as key, tar." + title
									+ " as title, NVL(t.mt, 0) as amount")
					.append(
							"res_service_targets".equals(table) ? ", tar.service_targets_type "
									: ", tar.interface_state ")
					.append(" ,tar.show_order from " + table + " tar, ")
					.append(
							"(select s." + column
									+ ", count(s.collect_table_id) as mt ")
					.append("from res_collect_table s where s.is_markup = 'Y' ")
					.append("group by s." + column + ") t ").append(
							"where tar.is_markup = 'Y' and tar." + column
									+ " = t." + column + "(+) ) m where m.amount <> '0')")
					.append(" select * from (select * from tmp where service_targets_type = '000' order by show_order)")
					.append(" union all select * from (select * from tmp  where service_targets_type <> '000' order by service_targets_type, title)");
			
			System.out.println("20209002--"+sqlBuffer.toString());
			stmt.addSqlStmt(sqlBuffer.toString());
		}

		return stmt;
	}

	public SqlStatement getInfoBytabType(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append(
							"select cd.codename as title, cd.codevalue as key, NVL(t.mt, 0) as amount ")
					.append("from codedata cd, ")
					.append("(select rescol.")
					.append(column)
					.append(", count(rescol.collect_table_id) as mt ")
					.append(
							"from res_collect_table rescol where rescol.is_markup = 'Y' group by rescol.")
					.append(column).append(") t where cd.codetype = '").append(
							codeType).append("' and cd.codevalue = t.").append(
							column).append("(+) order by amount desc");
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}

	public SqlStatement getInfoBycrtState(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append(
							"select cd.codename as title, cd.codevalue as key, NVL(t.mt, 0) as amount ")
					.append("from codedata cd, ")
					.append("(select rescol.")
					.append(column)
					.append(", count(rescol.collect_table_id) as mt ")
					.append(
							"from res_collect_table rescol where rescol.is_markup = 'Y' group by rescol.")
					.append(column).append(") t where cd.codetype = '").append(
							codeType).append("' and cd.codevalue = t.").append(
							column).append("(+) order by amount desc");
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}

	/**
	 * 
	 * queryCollectTableList(��ѯ���ݱ��б�)
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 *             SqlStatement
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement queryCollectTableList(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		DataBus db = request.getRecord("select-key");
		String cj_ly = db.getValue("cj_ly");
		String if_creat = db.getValue("if_creat");
		String table_type = db.getValue("table_type");
		String service_targets_type = db.getValue("service_targets_type");
		String service_targets_id = db.getValue("service_targets_id");
		String created_time_start = db.getValue("created_time");
		// String created_time_end = db.getValue("created_time_end");

		/*
		 * String service_targets_id =
		 * request.getRecord("select-key").getValue("service_targets_id");//�������ID
		 * String table_type =
		 * request.getRecord("select-key").getValue("table_type");//������ String
		 * cj_ly = request.getRecord("select-key").getValue("cj_ly");//�ɼ���Դ
		 * String if_creat =
		 * request.getRecord("select-key").getValue("if_creat");//�ɼ����Ƿ��Ѵ�����
		 * String table_name_en =
		 * request.getRecord("select-key").getValue("table_name_en");//���ݱ�����
		 * String table_name_cn =
		 * request.getRecord("select-key").getValue("table_name_cn");//���ݱ���������
		 */
		
		StringBuffer querySql = new StringBuffer(
				"select collect_table_id,service_targets_id,service_targets_id as service_targets_id1,table_name_en,table_name_cn,table_type,table_desc,table_status,is_markup," +
				"nvl(substr(t.last_modify_time,0,10),substr(t.created_time,0,10))  time,nvl(y2.yhxm,y1.yhxm) name," +
				"cj_ly,if_creat from res_collect_table t,xt_zzjg_yh_new y1,xt_zzjg_yh_new y2 "
						+ " where t.creator_id=y1.yhid_pk(+) and t.last_modify_id=y2.yhid_pk(+) ");

		if (StringUtils.isNotBlank(service_targets_type)) {
			querySql
					.append(" and t.service_targets_id in(select r.service_targets_id ");
			querySql
					.append("from res_service_targets r where r.service_targets_type='"
							+ service_targets_type + "') ");
		}

		if (service_targets_id != null && !service_targets_id.equals("")) {
			querySql.append(" and t.service_targets_id = '"
					+ service_targets_id + "'");
		}
		if (table_type != null && !"".equals(table_type)) {// ������
			querySql.append(" and table_type = '" + table_type + "'");
		}
		if (cj_ly != null && !"".equals(cj_ly)) {// �ɼ���Դ
			querySql.append(" and cj_ly = '" + cj_ly + "'");
		}
		if (if_creat != null && !"".equals(if_creat)) {// �ɼ����Ƿ��Ѵ�����
			querySql.append(" and if_creat = '" + if_creat + "'");
		}
		if (StringUtils.isNotBlank(created_time_start)) {
			if (!created_time_start.equals("���ѡ������")) {
				String[] times = DateUtil.getDateRegionByDatePicker(
						created_time_start, true);
				querySql.append(" and t.created_time >= '" + times[0] + "'");
				querySql.append(" and t.created_time <= '" + times[1] + "'");
			}
		}

		/*
		 * if (table_name_en != null && !"".equals(table_name_en)) {//���ݱ�����
		 * querySql .append(" and (table_name_en like '%" + table_name_en + "%'
		 * or table_name_en like '%"+table_name_en.toLowerCase()+"%' or
		 * table_name_en like '%"+table_name_en.toUpperCase()+"%')"); } if
		 * (table_name_cn != null && !"".equals(table_name_cn)) {//���ݱ���������
		 * querySql .append(" and table_name_cn like '%" + table_name_cn +
		 * "%'"); }
		 */
		querySql.append(" and is_markup= '" + ExConstant.IS_MARKUP_Y + "'");
		//querySql.append(" order by t.cj_ly, nvl(t.last_modify_time,0) desc ");
		querySql.append(" order by time desc ");
			
		
		System.out.println(querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;

	}

	/**
	 * getItemByTableId �������ݱ�ID��ѯ���Ӧ����������
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */

	public SqlStatement getItemByTableId(TxnContext request, DataBus inputData)
	{
		String collect_table_id = request.getRecord("primary-key").getValue(
				"collect_table_id");// ���ݱ�ID
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from res_collect_dataitem t ");
		if (collect_table_id != null) {
			sqlBuffer.append(" where t.collect_table_id = '" + collect_table_id
					+ "' and t.is_markup = '" + ExConstant.IS_MARKUP_Y
					+ "' and (t.dataitem_state is null or t.dataitem_state = '1')  order by created_time");
		}
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt
				.setCountStmt("select count(1) from (" + sqlBuffer.toString()
						+ ")");
		return stmt;
	}
	
	/**
	 * getItemByTableIdNoCreateTable �������ݱ�ID��ѯ���Ӧ����   δ���������  ��������
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */

	public SqlStatement getItemByTableIdNoCreateTable(TxnContext request, DataBus inputData)
	{
		String collect_table_id = request.getRecord("primary-key").getValue(
				"collect_table_id");// ���ݱ�ID
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from res_collect_dataitem t ");
		if (collect_table_id != null) {
			sqlBuffer.append(" where t.collect_table_id = '" + collect_table_id
					+ "' and t.is_markup = '" + ExConstant.IS_MARKUP_Y
					+ "' and (t.dataitem_state  = '0')  order by created_time");
		}
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt
				.setCountStmt("select count(1) from (" + sqlBuffer.toString()
						+ ")");
		return stmt;
	}

	/**
	 * deleteTableItem �������ݱ�IDɾ�����ݱ��Ӧ����������
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement deleteTableItem(TxnContext request, DataBus inputData)
	{
		String collect_table_id = request.getRecord("primary-key").getValue(
				"collect_table_id");// ���ݱ�ID
		StringBuffer sqlBuffer = new StringBuffer();
		SqlStatement stmt = new SqlStatement();
		if (collect_table_id != null) {
			sqlBuffer.append("delete from res_collect_dataitem t ");
			sqlBuffer.append(" where t.collect_table_id = '" + collect_table_id
					+ "'");
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}

	/**
	 * deleteTable ɾ�����ݱ�
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement deleteTable(TxnContext request, DataBus inputData)
	{
		String collect_table_id = request.getRecord("primary-key").getValue(
				"collect_table_id");// ���ݱ�ID
		StringBuffer sqlBuffer = new StringBuffer();
		SqlStatement stmt = new SqlStatement();
		if (collect_table_id != null) {
			sqlBuffer.append("update  res_collect_table  set is_markup = '"
					+ ExConstant.IS_MARKUP_N);
			sqlBuffer.append("' where collect_table_id = '" + collect_table_id
					+ "'");
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}

	/**
	 * creatTable �ɼ����������ݱ�
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException
	 * @throws TxnDataException
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement creatTable(TxnContext request, DataBus inputData)
			throws DBException, TxnDataException
	{
		/**
		 * ���²ɼ��� �Ƿ��������ݱ��ֶ� Ϊ1
		 */
		String collect_table_id = request.getRecord("record").getValue(
				"collect_table_id");// ���ݱ�ID
		String name_nums = request.getRecord("record").getValue("name_nums");

		StringBuffer sqlBufferTable = new StringBuffer();
		StringBuffer sqlBufferItem = new StringBuffer();
		StringBuffer sqlBufferDropTable = new StringBuffer();// drop ��
		StringBuffer sqlBufferCreatTable = new StringBuffer();// ������
		StringBuffer sqlBufferCreatTableComment = new StringBuffer();// ע��
		StringBuffer sqlBufferCreatTableKey = new StringBuffer();// ����
		String dataitem_name_en = null;// ����������
		String dataitem_name_cn = null;// ��������������
		String dataitem_type = null;// ����������
		String type = null;// ����������
		String dataitem_long = null;// �������
		String isKey = null;// �Ƿ�����

		ServiceDAO daoTable = new ServiceDAOImpl();// �������ݱ�Dao
		ServiceDAO daoItem = new ServiceDAOImpl();// ����������Dao
		sqlBufferTable
				.append("select t.table_name_en,t.table_name_cn from res_collect_table t");
		sqlBufferItem.append("select * from res_collect_dataitem t ");

		if (collect_table_id != null) {
			sqlBufferTable.append(" where t.collect_table_id = '"
					+ collect_table_id + "'");
			sqlBufferItem.append(" where t.collect_table_id = '"
					+ collect_table_id + "' and t.is_markup = '"
					+ ExConstant.IS_MARKUP_Y + "' order by created_time");
		}
		System.out.println("��ȡ���ݱ����� sql============"
				+ sqlBufferTable.toString());
		// ��ȡ���ݱ�����
		Map tablepMap = daoTable.queryService(sqlBufferTable.toString());
		String table_name_en = (String) tablepMap.get("TABLE_NAME_EN");// ������
		System.out.println("���ݱ�����============" + table_name_en);
		String table_name_cn = (String) tablepMap.get("TABLE_NAME_CN");// ����������
		if (table_name_cn != null && !"".equals(table_name_cn)) {
			// sqlBufferCreatTableComment.append("alter table
			// "+table_name_en.toUpperCase()+" comment
			// '"+table_name_cn+"';\n");//��ע��
			sqlBufferCreatTableComment.append("comment on table "
					+ table_name_en.toUpperCase() + " is '" + table_name_cn
					+ "';");// ��ע��
		}
		sqlBufferCreatTable.append("create table "
				+ table_name_en.toUpperCase() + " (\n");

		Map itemMap = new HashMap();
		// ��ȡ����������
		List itemList = daoItem.query(sqlBufferItem.toString());
		if (itemList != null && itemList.size() > 0) {
			for (int i = 0; i < itemList.size(); i++) {
				itemMap = new HashMap();
				itemMap = (Map) itemList.get(i);
				dataitem_name_en = (String) itemMap.get("DATAITEM_NAME_EN");// ����������
				dataitem_name_cn = (String) itemMap.get("DATAITEM_NAME_CN");// ��������������
				dataitem_type = (String) itemMap.get("DATAITEM_TYPE");// ����������
				dataitem_long = (String) itemMap.get("DATAITEM_LONG");// �������
				isKey = (String) itemMap.get("IS_KEY");// �Ƿ�����
				if (i != 0) {
					sqlBufferCreatTable.append(",\n");
				}
				if (dataitem_type != null && !"".equals(dataitem_type)) {
					int num = Integer.parseInt(dataitem_type);// 1:char
																// 2:varchar2
																// 3:int 4 date
					switch (num) {
					case 1:
						type = "char";
						sqlBufferCreatTable.append(dataitem_name_en
								.toUpperCase()
								+ " CHAR(" + dataitem_long + ")");
						break;
					case 2:
						type = "varchar2";
						sqlBufferCreatTable.append(dataitem_name_en
								.toUpperCase()
								+ " VARCHAR2(" + dataitem_long + ")");
						break;
					case 3:
						type = "int";
						sqlBufferCreatTable.append(dataitem_name_en
								.toUpperCase()
								+ " NUMBER");
						break;
					case 4:
						type = "date";
						sqlBufferCreatTable.append(dataitem_name_en
								.toUpperCase()
								+ " DATE");
						break;
					case 5:
						type = "timestamp";
						sqlBufferCreatTable.append(dataitem_name_en
								.toUpperCase()
								+ " TIMESTAMP(6)");
						break;
					}
				}
				if (isKey != null && isKey.equals(CollectConstants.IS_KEY)) {// ������
					sqlBufferCreatTable.append(" not null");
					sqlBufferCreatTableKey.append("alter table "
							+ table_name_en.toUpperCase()
							+ " add constraint PK_"
							+ table_name_en.toUpperCase() + " primary key ("
							+ dataitem_name_en.toUpperCase() + ") using index");
					// System.out.println("sql============"+sqlBufferCreatTableKey.toString());
				}
				if (dataitem_name_cn != null && !dataitem_name_cn.equals("")) {// �ֶ�ע��
					// sqlBufferCreatTableComment.append("alter table
					// "+table_name_en.toUpperCase()+" modify column
					// "+dataitem_name_en.toUpperCase()+" "+type+" comment
					// '"+dataitem_name_cn+"';\n");//��������ע��
					sqlBufferCreatTableComment.append("comment on column "
							+ table_name_en.toUpperCase() + "."
							+ dataitem_name_en.toUpperCase() + " is '"
							+ dataitem_name_cn + "';");// ��������ע��

				}
			}
			sqlBufferCreatTable.append(",\n");
			sqlBufferCreatTable.append(" UPDATE_DATE VARCHAR2(20)");
			sqlBufferCreatTableComment.append("comment on column "
					+ table_name_en.toUpperCase() + "."
					+ " UPDATE_DATE  is '��������';");// ��������ע��
		}
		sqlBufferCreatTable.append(")\n");

		// sqlBufferCreatTable.append(sqlBufferCreatTableComment); //ִ�������� ��δ���
		// sqlBufferCreatTable.append(sqlBufferCreatTableKey);

		// System.out.println("sql============"+sqlBufferCreatTable.toString());
		DBPoolConnection conn = null;
		String erro = null;
		int count = 0;
		try {
			conn = new DBPoolConnection(CollectConstants.DATASOURCE_CJK);

			// ����
			if (name_nums != null && !name_nums.equals("0")) {
				sqlBufferDropTable.append(" drop table "
						+ table_name_en.toUpperCase());
				conn.executeDDL(sqlBufferDropTable.toString());// drop��
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new TxnDataException("error", "���Ӳɼ���ʧ��!");
		}
		try {
			System.out.println("sql====" + sqlBufferCreatTable.toString());
			erro = conn.executeDDL(sqlBufferCreatTable.toString());// ������
			System.out.println("erro========" + erro);
			if (erro != null && erro.equals("sql����쳣")) {
				throw new TxnDataException("error", "������ʧ��!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new TxnDataException("error", "������ʧ��!");
		}
		try {
			if (sqlBufferCreatTableKey.toString() != null
					&& !"".equals(sqlBufferCreatTableKey.toString())) {
				System.out.println("sqlBufferCreatTableKey=="
						+ sqlBufferCreatTableKey.toString());
				count = conn.executeDML(sqlBufferCreatTableKey.toString());// ��������
				if (count == -1) {
					sqlBufferDropTable = new StringBuffer();
					sqlBufferDropTable.append(" drop table "
							+ table_name_en.toUpperCase());
					conn.executeDDL(sqlBufferDropTable.toString());// drop��
					throw new TxnDataException("error", "��������ʧ��!");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

			throw new TxnDataException("error", "��������ʧ��!");
		}
		try {
			if (sqlBufferCreatTableComment.toString() != null
					&& !"".equals(sqlBufferCreatTableComment.toString())) {
				String comment[] = sqlBufferCreatTableComment.toString().split(
						";");
				count = 0;
				for (int i = 0; i < comment.length; i++) {
					System.out.println("ע��sql============" + comment[i]);
					count = conn.executeDML(comment[i]);
					if (count == -1) {
						sqlBufferDropTable = new StringBuffer();
						sqlBufferDropTable.append(" drop table "
								+ table_name_en.toUpperCase());
						conn.executeDDL(sqlBufferDropTable.toString());// drop��
						throw new TxnDataException("error", "����ע��ʧ��!");
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new TxnDataException("error", "����ע��ʧ��!");
		}

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("update res_collect_table t set t.if_creat = '"
				+ CollectConstants.TYPE_IF_CREAT_YES
				+ "' where t.collect_table_id = '" + collect_table_id + "'");
		System.out.println("update res_collect_table ============"
				+ sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		
		
		StringBuffer sqlBuffer2 = new StringBuffer();
		sqlBuffer2.append("update res_collect_dataitem t set t.dataitem_state = '"
				+ CollectConstants.TYPE_IF_CREAT_YES
				+ "' where t.collect_table_id = '" + collect_table_id + "'");
		System.out.println("update res_collect_dataitem ============"
				+ sqlBuffer2.toString());
		
		stmt.addSqlStmt(sqlBuffer2.toString());
		
		return stmt;
	}
	
	
	/**
	 * alterTable �ɼ���     �޸�    ���ݱ�
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException
	 * @throws TxnDataException
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement alterTable(TxnContext request, DataBus inputData)
			throws DBException, TxnDataException
	{
		/**
		 * ���²ɼ��� 
		 */
		String collect_table_id = request.getRecord("record").getValue(
				"collect_table_id");// ���ݱ�ID
		
		StringBuffer sqlBufferTable = new StringBuffer();
		StringBuffer sqlBufferItem = new StringBuffer();
		
		StringBuffer sqlBufferAlterTable = new StringBuffer();// ������
		StringBuffer sqlBufferCreatTableComment = new StringBuffer();// ע��
		StringBuffer sqlBufferCreatTableKey = new StringBuffer();// ����
		String dataitem_name_en = null;// ����������
		String dataitem_name_cn = null;// ��������������
		String dataitem_type = null;// ����������
		String type = null;// ����������
		String dataitem_long = null;// �������
		String isKey = null;// �Ƿ�����

		ServiceDAO daoTable = new ServiceDAOImpl();// �������ݱ�Dao
		ServiceDAO daoItem = new ServiceDAOImpl();// ����������Dao
		
		
		sqlBufferTable.append("select t.table_name_en,t.table_name_cn from res_collect_table t");
		sqlBufferItem.append("select * from res_collect_dataitem t ");

		if (collect_table_id != null) {
			sqlBufferTable.append(" where t.collect_table_id = '"+ collect_table_id + "'");
		
			sqlBufferItem.append(" where t.collect_table_id = '"
					+ collect_table_id + "' and t.is_markup = '"
					+ ExConstant.IS_MARKUP_Y + "' and t.dataitem_state = '0' order by created_time");
		}
		
		// ��ȡ���ݱ�����
		Map tablepMap = daoTable.queryService(sqlBufferTable.toString());
		String table_name_en = (String) tablepMap.get("TABLE_NAME_EN");// ������
		System.out.println("���ݱ�����============" + table_name_en);
		String table_name_cn = (String) tablepMap.get("TABLE_NAME_CN");// ����������
		
		

		Map itemMap = new HashMap();
		// ��ȡ����������
		List itemList = daoItem.query(sqlBufferItem.toString());
		if (itemList != null && itemList.size() > 0) {
			for (int i = 0; i < itemList.size(); i++) {
				
				itemMap = new HashMap();
				itemMap = (Map) itemList.get(i);
				dataitem_name_en = (String) itemMap.get("DATAITEM_NAME_EN");// ����������
				dataitem_name_cn = (String) itemMap.get("DATAITEM_NAME_CN");// ��������������
				dataitem_type = (String) itemMap.get("DATAITEM_TYPE");// ����������
				dataitem_long = (String) itemMap.get("DATAITEM_LONG");// �������
				
				if (i != 0) {
					sqlBufferAlterTable.append("\n");
					sqlBufferCreatTableComment.append("\n");
				}
				
				if (dataitem_type != null && !"".equals(dataitem_type)) {
					int num = Integer.parseInt(dataitem_type);// 1:char
																// 2:varchar2
																// 3:int 4 date
					sqlBufferAlterTable.append("alter table "+table_name_en+" add "+dataitem_name_en.toUpperCase());
					switch (num) {
					case 1:
						type = "char";
						
						sqlBufferAlterTable.append(" CHAR(" + dataitem_long + ");");
						
						break;
					case 2:
						type = "varchar2";
						sqlBufferAlterTable.append(" VARCHAR2(" + dataitem_long + ");");
						
						break;
					case 3:
						type = "int";
						sqlBufferAlterTable.append(" NUMBER;");
						break;
					case 4:
						type = "date";
						sqlBufferAlterTable.append(" DATE;");
						break;
					case 5:
						type = "timestamp";
						sqlBufferAlterTable.append(" TIMESTAMP(6);");
						break;
					}
				}
				
				if (dataitem_name_cn != null && !dataitem_name_cn.equals("")) {// �ֶ�ע��
				
					sqlBufferCreatTableComment.append("comment on  column "+table_name_en+"."+dataitem_name_en +" is '"+dataitem_name_cn+"';");

				}
			}
			
		}
		

		// sqlBufferCreatTable.append(sqlBufferCreatTableComment); //ִ�������� ��δ���
		// sqlBufferCreatTable.append(sqlBufferCreatTableKey);

		// System.out.println("sql============"+sqlBufferCreatTable.toString());
		DBPoolConnection conn = null;
		String erro = null;
		int count = 0;
		try {
			conn = new DBPoolConnection(CollectConstants.DATASOURCE_CJK);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new TxnDataException("error", "���Ӳɼ���ʧ��!");
		}
		
			try {
				if (sqlBufferAlterTable.toString() != null
						&& !"".equals(sqlBufferAlterTable.toString())) {
					String alterSql[] = sqlBufferAlterTable.toString().split(
							";");
					count = 0;
					for (int i = 0; i < alterSql.length; i++) {
						System.out.println("�޸ı�sql============" + alterSql[i]);
						erro =  conn.executeDDL(alterSql[i]);
						if (erro != null && erro.equals("sql����쳣")) {
							throw new TxnDataException("error", "�޸ı��ֶ�ʧ��!");
						}
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new TxnDataException("error", "�޸ı��ֶ�ʧ��!");
			}
			
			////�����ֶ�ע��
			try {
				if (sqlBufferCreatTableComment.toString() != null
						&& !"".equals(sqlBufferCreatTableComment.toString())) {
					String commentSql[] = sqlBufferCreatTableComment.toString().split(
							";");
					count = 0;
					for (int i = 0; i < commentSql.length; i++) {
						System.out.println("�޸ı��ֶ�ע��sql============" + commentSql[i]);
						erro =  conn.executeDDL(commentSql[i]);
						if (erro != null && erro.equals("sql����쳣")) {
							throw new TxnDataException("error", "�����ֶ�ע��ʧ��!");
						}
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new TxnDataException("error", "�����ֶ�ע��ʧ��!");
			}

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("update res_collect_dataitem t set t.dataitem_state = '"
				+ CollectConstants.TYPE_IF_CREAT_YES
				+ "' where t.collect_table_id = '" + collect_table_id + "'");
		System.out.println("update res_collect_table ============"
				+ sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}

	/**
	 * importExcel ����excel
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws IOException
	 * @throws BiffException
	 * @throws DBException
	 * @throws TxnException
	 * @throws SQLException
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement importExcel(TxnContext request, DataBus inputData)
			throws BiffException, IOException, DBException, TxnException
	{

		SqlStatement stmt = new SqlStatement();
		String filePath = "";

		ConnectFactory cf = ConnectFactory.getInstance();
		DBController dbcon = cf.getConnection();
		OracleConnection conn = (OracleConnection) dbcon.getConnection();
		ResultSet rs = null;

		conn.setRemarksReporting(true);

		// ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
		String fj_fk = request.getRecord("record").getValue("fj_fk");
		System.out.println("fj_fk==" + fj_fk);
		if (fj_fk != null && !"".equals(fj_fk)) {
			fj_fk = fj_fk.replace(",", "");
		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer
				.append("select a.CCGML||'/'||b.cclj||'/'||b.wybs as filePath from xt_ccgl_wjlb a,xt_ccgl_wjys b where a.cclbbh_pk = b.cclbbh_pk ");
		pathBuffer.append(" and b.ysbh_pk = '" + fj_fk + "'");
		System.out.println("path sql===" + pathBuffer.toString());
		try {
			logger.info("start===" + pathBuffer.toString());
			rs = conn.createStatement().executeQuery(pathBuffer.toString()); // ��ȡ�����
			logger.info("start2===");
			if (rs.next()) {
				filePath = rs.getString("filePath");
				filePath = filePath.replace("\\", "/");
				System.out.println("filePath===" + filePath);
				logger.info("filePath===" + filePath);
			}

			// Map pathMap=daoTable.queryService(pathBuffer.toString());
			// if(pathMap!=null&&!pathMap.isEmpty()){
			// filePath=(String)pathMap.get("WYBS");
			// filePath=filePath.replace("\\", "/");
			// }

			System.out.println("filePath====" + filePath);
			logger.info("filePath==112=" + filePath);
			InputStream fs = null;
			fs = new FileInputStream(filePath);
			logger.info("==========1=========");
			ExlUtil exlutil = new ExlUtil();
			logger.info("==========2=========");
			stmt = exlutil.impTodataitem(fs, stmt, request);
			logger.info("==========end=========");
		} catch (Exception e) {
			e.printStackTrace();
			throw new TxnDataException("error", "����Excel�����ļ������ϸ�ʽ�淶,��ο�ģ���ļ�!");
		} finally {
			dbcon.closeResultSet(rs);
		}
		return stmt;
	}

	/**
	 * 
	 * queryCollectTableUse(��ѯ���ݱ��Ƿ񱻲ɼ���������)
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 *             SqlStatement
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement queryCollectTableUse(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String collect_table_id = request.getRecord("primary-key").getValue(
				"collect_table_id");

		StringBuffer querySql = new StringBuffer(
				"select collect_table as collect_table_id from ( select distinct collect_table from collect_webservice_task union ");
		querySql.append(" select distinct collect_table from collect_ftp_task union ");
		querySql.append(" select distinct collect_table from collect_file_upload_task union ");
		querySql.append(" select distinct collect_table from collect_database_task ) t where 1=1 ");
		if (collect_table_id != null && !"".equals(collect_table_id)) {
			querySql.append(" and t.collect_table ='" + collect_table_id + "'");
		}
		System.out.println("��ѯ�ɼ����ݱ��Ƿ񱻲ɼ�����ʹ�ã�" + querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}

	/**
	 * 
	 * querySynchroTableList(��ѯ��Ҫͬ�������ݱ��б�)
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 *             SqlStatement
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement querySynchroTableList(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		StringBuffer querySql = new StringBuffer(
				"select collect_table_id,service_targets_id,table_name_en,table_name_cn,table_type,table_desc,table_status,is_markup,creator_id,substr(created_time,0,10) created_time,last_modify_id,last_modify_time,cj_ly,if_creat from res_collect_table t"
						+ " where 1=1 ");

		querySql.append(" and is_markup= '" + ExConstant.IS_MARKUP_Y + "'");
		querySql.append(" order by t.created_time desc");
		System.out.println(querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;

	}

	/**
	 * ����Դͬ��ʱ��δ����������Ϣ
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 * @throws DBException
	 */
	public SqlStatement insertBySync(TxnContext request, DataBus inputData)
			throws TxnException, DBException
	{
		SqlStatement stmt = new SqlStatement();
		String collect_table_id = inputData.getValue("collect_table_id");// �ɼ����ݱ�ID
		String service_targets_id = ExConstant.SERVIVE_OBJECT_IN;// �������ID
																	// �ڲ��ɼ���
		System.out.println("fuwuid====" + service_targets_id);
		String table_name_en = inputData.getValue("table_name_en");// ������
		String table_name_cn = inputData.getValue("table_name_cn");// ����������
		String table_type = inputData.getValue("table_type");// ������
		String table_desc = inputData.getValue("table_desc");// ������
		String table_status = inputData.getValue("table_status");// ��״̬
		String is_markup = inputData.getValue("is_markup");// ����� ��Ч or ��Ч
		String creator_id = inputData.getValue("creator_id");// ������ID
		String created_time = inputData.getValue("created_time");// ����ʱ��
		String cj_ly = inputData.getValue("cj_ly");// �ɼ���Դ
		String if_creat = inputData.getValue("if_creat");// �ɼ����Ƿ����ɲɼ���
		if (collect_table_id != null && !"".equals(collect_table_id)) {

			// ɾ����
			StringBuffer sqlBuffer = new StringBuffer();
			ServiceDAO daoTable = new ServiceDAOImpl();
			; // �������ݱ�Dao
			ServiceDAO daoItem = new ServiceDAOImpl();
			; // ����������Dao

			if (table_name_en != null && !"".equals(table_name_en)) {
				sqlBuffer
						.append("select t.collect_table_id from res_collect_table t");
				sqlBuffer.append(" where t.table_name_en = '"
						+ table_name_en.toUpperCase() + "' and t.cj_ly = '"
						+ CollectConstants.TYPE_CJLY_IN + "'");
				System.out.println("��ȡ���ݱ����� sql============"
						+ sqlBuffer.toString());
				// ��ȡ���ݱ�ID
				Map tablepMap = daoTable.queryService(sqlBuffer.toString());
				String table_name_id = (String) tablepMap
						.get("COLLECT_TABLE_ID");// ������
				if (table_name_id != null && !"".equals(table_name_id)) {
					// ɾ���������
					sqlBuffer = new StringBuffer();
					sqlBuffer
							.append("delete from res_collect_dataitem t where t.collect_table_id = '"
									+ table_name_id + "'");
					stmt.addSqlStmt(sqlBuffer.toString());

					// ɾ�����ݱ�
					sqlBuffer = new StringBuffer();
					sqlBuffer
							.append("delete from res_collect_table t where t.table_name_en = '"
									+ table_name_en + "'");
					stmt.addSqlStmt(sqlBuffer.toString());
				}
			}
			sqlBuffer = new StringBuffer();
			sqlBuffer
					.append("insert into res_collect_table (collect_table_id,service_targets_id,"
							+ "table_name_en,table_name_cn,table_type,table_desc,table_status,"
							+ "is_markup,creator_id,created_time,cj_ly,if_creat) values('");
			sqlBuffer.append(collect_table_id);
			sqlBuffer.append("','");
			sqlBuffer.append(service_targets_id);
			sqlBuffer.append("','");
			sqlBuffer.append(table_name_en.toUpperCase());
			sqlBuffer.append("','");
			sqlBuffer.append(table_name_cn);
			sqlBuffer.append("','");
			sqlBuffer.append(table_type);
			sqlBuffer.append("','");
			sqlBuffer.append(table_desc);
			sqlBuffer.append("','");
			sqlBuffer.append(table_status);
			sqlBuffer.append("','");
			sqlBuffer.append(is_markup);
			sqlBuffer.append("','");
			sqlBuffer.append(creator_id);
			sqlBuffer.append("','");
			sqlBuffer.append(created_time);
			sqlBuffer.append("','");
			sqlBuffer.append(cj_ly);
			sqlBuffer.append("','");
			sqlBuffer.append(if_creat);
			sqlBuffer.append("')");
			System.out.println("insertsql:" + sqlBuffer.toString());
			stmt.addSqlStmt(sqlBuffer.toString());
		}

		return stmt;
	}
}
