package com.gwssi.dw.runmgr.services.txn;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.MD5;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.services.common.Constants;
import com.gwssi.dw.runmgr.services.vo.SysSvrUserContext;

public class TxnSysSvrUser extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods					= getAllMethod(
																	TxnSysSvrUser.class,
																	SysSvrUserContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME					= "sys_svr_user";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION				= "select sys_svr_user list";

	// ��ѯ�б�
	private static final String	EXIST_USER_ROWSET_FUNCTION	= "select exist sys_svr_user list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION				= "select one sys_svr_user";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION				= "update one sys_svr_user";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION				= "insert one sys_svr_user";

	// ɾ����¼
	private static final String	DELETE_FUNCTION				= "delete one sys_svr_user";

	// ��ѯ��¼
	private static final String	SELECT_USERNAME_FUNCTION	= "select one sys_svr_user_name";

	/**
	 * ���캯��
	 */
	public TxnSysSvrUser()
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
	 * ��ѯ�û��б� DC2-jufeng-2012-07-10
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50200001(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		// ��ѯ��¼������ VoSysSvrUserSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction("queryUser", context, inputNode, outputNode);
		Recordset rs = context.getRecordset(outputNode);
		if (rs != null && rs.size() > 0) {
			for (int j = 0; j < rs.size(); j++) {
				DataBus db = rs.get(j);
				String ws_num = db.getValue("ws_num");
				String view_num = db.getValue("view_num");
				String svr_num = "0";
				// System.out.println("�� "+(j+1)+" �� ws_num is " +ws_num + "
				// view_num is "+ view_num);
				try {
					long num = (Long.valueOf(ws_num).longValue() + Long
							.valueOf(view_num).longValue());
					svr_num = String.valueOf(num);
				} catch (Exception e) {

				}
				db.setValue("svr_num", svr_num);
			}
		}
		// ��ѯ���ļ�¼�� VoSysSvrUser result[] = context.getSysSvrUsers( outputNode );
	}

	/**
	 * ��ת���û����񵼺���
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50200011(SysSvrUserContext context) throws TxnException
	{
		String sys_svr_user_id = context.getRecord(inputNode).getValue(
				"sys_svr_user_id");
		DataBus db = new DataBus();

		db.setValue("sys_svr_user_id", sys_svr_user_id);
		context.addRecord("select-key", db);
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("selectSvrUserById", context, inputNode, outputNode);
	}

	/**
	 * Ajax ���ò�ѯĳ���û��ķ������
	 */
	public void txn50200021(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		// ��ѯ��¼������ VoSysSvrUserSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction("queryUserServices", context, inputNode,
				outputNode);
		// ��ѯ���ļ�¼�� VoSysSvrUser result[] = context.getSysSvrUsers( outputNode );
	}

	/**
	 * ��ѯĳ���û���WebService�������
	 */
	public void txn50200022(SysSvrUserContext context) throws TxnException
	{
		TxnContext limit = new TxnContext();
		DataBus db = context.getRecord(inputNode);
		DataBus info_base = new DataBus();
		DataBus db2 = new DataBus();
		db2.setValue("sys_svr_user_id", db.getValue("sys_svr_user_id"));
		db2.setValue("sys_svr_service_id", db.getValue("sys_svr_service_id"));
		String stype = db.getValue("stype");
		if (stype != null && stype.equals("W")) {
			info_base.setValue("stype", "WebService");
		} else if (stype != null && stype.equals("V")) {
			info_base.setValue("stype", "View");
		}

		limit.addRecord("select-key", db2);
		// ��ѯ�޶�����
		this.callService("50011701", limit);
		Recordset rs = limit.getRecordset("record");
		ArrayList list = new ArrayList();
		if (rs != null && rs.size() > 0) {
			DataBus limit0 = rs.get(0);
			info_base.setValue("is_limit_time", limit0
					.getValue("is_limit_time"));
			info_base.setValue("is_limit_number", limit0
					.getValue("is_limit_number"));
			info_base.setValue("is_limit_total", limit0
					.getValue("is_limit_total"));
			info_base.setValue("limit_week", limit0.getValue("limit_week"));
			info_base.setValue("limit_start_time", limit0
					.getValue("limit_start_time"));
			info_base.setValue("limit_end_time", limit0
					.getValue("limit_end_time"));
			info_base.setValue("limit_number", limit0.getValue("limit_number"));
			info_base.setValue("limit_total", limit0.getValue("limit_total"));
			for (int j = 0; j < rs.size(); j++) {
				DataBus limit2 = rs.get(j);
				String is_limit_week = limit2.getValue("is_limit_week");
				String limit_week = limit2.getValue("limit_week");
				if (is_limit_week != null && is_limit_week.equals("0")) {
					list.add(limit_week);
				}
			}
			String is_limit_time = limit0.getValue("is_limit_time");
			if (is_limit_time != null && is_limit_time.equals("1")) {
				String limit_start_time = limit0.getValue("limit_start_time");
				String limit_end_time = limit0.getValue("limit_end_time");
				info_base.setValue("open_time", getOpenDayStr(list) + " "
						+ limit_start_time + " �� " + limit_end_time);
			} else {
				info_base.setValue("open_time", getOpenDayStr(list) + " ȫ�� ");
			}
		}
		String ss = info_base.getValue("open_time");
		if (ss == null || ss.equals("")) {
			info_base.setValue("open_time", "������");
		}
		String limit_conn1 = "";
		String limit_conn2 = "";
		if (StringUtils.isNotBlank(info_base.getValue("limit_number"))) {
			limit_conn1 = info_base.getValue("limit_number") + "��/ÿ��";
		}
		if (StringUtils.isNotBlank(info_base.getValue("limit_total"))) {
			limit_conn2 = info_base.getValue("limit_total") + "��/ÿ��";
		}
		info_base.setValue("limit_conn", limit_conn1 + " " + limit_conn2);
		if (limit_conn1 == "" && limit_conn2 == "") {
			info_base.setValue("limit_conn", "������");
		}

		context.addRecord("info-base", info_base);
		TxnContext content_config = new TxnContext();
		content_config.addRecord("select-key", db2);
		// ��ȡ������Ϣ�ͷ��������Ϣ
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("selectServiceConfig", content_config, inputNode,
				"config-info");
		info_base.setValue("svr_code", content_config.getRecord("config-info")
				.getValue("svr_code"));
		info_base.setValue("create_by", content_config.getRecord("config-info")
				.getValue("create_by"));
		info_base.setValue("create_date", content_config.getRecord(
				"config-info").getValue("create_date"));
		String column_no = content_config.getRecord("config-info").getValue(
				"permit_column");
		String[] columns = column_no.trim().split(",");
		String cols = "";
		for (int i = 0; i < columns.length; i++) {
			cols += (cols == "" ? "'" + columns[i] + "'" : "," + "'"
					+ columns[i] + "'");
		}
		// ��ȡ������Ϣ�ͷ��������Ϣ
		TxnContext content_column = new TxnContext();
		content_column.getRecord("select-key").setValue("column_nos", cols);
		table.executeFunction("selectServiceColumn", content_column, inputNode,
				outputNode);
		Recordset rs_column = content_column.getRecordset(outputNode);

		if (!rs_column.isEmpty()) {
			cols = "";
			for (int i = 0; i < rs_column.size(); i++) {
				DataBus db_column = rs_column.get(i);
				cols += "<span style='color:red'>"
						+ db_column.getValue("table_name") + "</span>";
				cols += db_column.getValue("column_names");
				if (i != rs_column.size() - 1) {
					cols += "<Br/>";
				}
			}
		}
		// System.out.println(cols);
		DataBus db_info = new DataBus();
		db_info.setValue("shared_columns", cols);
		context.addRecord("info-config", db_info);
		TxnContext content_param = new TxnContext();
		DataBus db_param = new DataBus();
		db_param.setValue("sys_svr_config_id", content_config.getRecord(
				"config-info").getValue("sys_svr_config_id"));
		content_param.addRecord("select-key", db_param);
		//��ȡϵͳ����
		table.executeFunction("selectServiceColumnConfig", content_param, inputNode,outputNode);
		Recordset rs_param = content_param.getRecordset(outputNode);
		String user_param="";
		String sys_param="";
		if (!rs_param.isEmpty()) {
			cols = "";
			for (int i = 0; i < rs_param.size(); i++) {
				DataBus db_column = rs_param.get(i);
				String content=db_column.getValue("param_text");
				if (content.indexOf("����ֵ")==-1) {
					sys_param+=content+"<Br/>";
				}else{
					user_param+=content+"<Br/>";
				}
			}
			//ȥ�����һ��<Br/>
			if (StringUtils.isNotBlank(user_param)) {
				user_param=user_param.substring(0, user_param.length()-6);
			}
			//ȥ�����һ��<Br/>
			if (StringUtils.isNotBlank(sys_param)) {
				sys_param=sys_param.substring(0, sys_param.length()-6);
			}
			context.getRecord("info-config").setValue("sys_params", sys_param);
			context.getRecord("info-config").setValue("user_params", user_param);
		}
		System.out.println(context);

	}

	// TxnContext temp = new TxnContext();
	// TxnContext limit = new TxnContext();
	// DataBus db = context.getRecord(inputNode);
	// DataBus info_base = new DataBus();
	// DataBus db2 = new DataBus();
	// db2.setValue("sys_svr_user_id", db.getValue("sys_svr_user_id"));
	// db2.setValue("sys_svr_service_id", db.getValue("sys_svr_service_id"));
	// String stype = db.getValue("stype");
	// if (stype != null && stype.equals("W")) {
	// info_base.setValue("stype", "WebService");
	// } else if (stype != null && stype.equals("V")) {
	// info_base.setValue("stype", "View");
	// }
	//
	// String is_limit_number = null;
	// String limit_number = null;
	// String is_limit_total = null;
	// String limit_total = null;
	// // ��ѯ�޶�����
	// // String is_limit = db.getValue("is_limit");
	//
	// limit.addRecord("select-key", db2);
	// this.callService("50011701", limit);
	//
	// Recordset rs = limit.getRecordset("record");
	// ArrayList list = new ArrayList();
	// if (rs != null && rs.size() > 0) {
	// DataBus limit0 = rs.get(0);
	//
	// info_base.setValue("is_limit_time", limit0
	// .getValue("is_limit_time"));
	// info_base.setValue("is_limit_number", limit0
	// .getValue("is_limit_number"));
	// info_base.setValue("is_limit_total", limit0
	// .getValue("is_limit_total"));
	// info_base.setValue("limit_week", limit0.getValue("limit_week"));
	// info_base.setValue("limit_start_time", limit0
	// .getValue("limit_start_time"));
	// info_base.setValue("limit_end_time", limit0
	// .getValue("limit_end_time"));
	// info_base.setValue("limit_number", limit0.getValue("limit_number"));
	// info_base.setValue("limit_total", limit0.getValue("limit_total"));
	//
	// for (int j = 0; j < rs.size(); j++) {
	// DataBus limit2 = rs.get(j);
	// String is_limit_week = limit2.getValue("is_limit_week");
	// String limit_week = limit2.getValue("limit_week");
	// if (is_limit_week != null && is_limit_week.equals("0")) {
	// list.add(limit_week);
	// }
	// }
	// String is_limit_time = limit0.getValue("is_limit_time");
	// if (is_limit_time != null && is_limit_time.equals("1")) {
	// String limit_start_time = limit0.getValue("limit_start_time");
	// String limit_end_time = limit0.getValue("limit_end_time");
	// info_base.setValue("open_time", getOpenDayStr(list) + " "
	// + limit_start_time + " �� " + limit_end_time);
	// } else {
	// info_base.setValue("open_time", getOpenDayStr(list) + " ȫ�� ");
	// }
	//
	// is_limit_number = limit0.getValue("is_limit_number");
	// limit_number = limit0.getValue("limit_number");
	// is_limit_total = limit0.getValue("is_limit_total");
	// limit_total = limit0.getValue("limit_total");
	//
	// }
	// String ss = info_base.getValue("open_time");
	// if (ss == null || ss.equals("")) {
	// info_base.setValue("open_time", "������");
	// }
	// // ��ѯ������Ϣ
	// temp.addRecord("select-key", db2);
	// this.callService("50203004", temp);
	// System.out.println("!!!!!!!!!!!!!!!!!!!!!!��ѯ50203004 \n" + temp);
	// DataBus dbr = temp.getRecord("record");
	// DataBus config_inf = temp.getRecord("config-inf");
	//
	// String colCn = config_inf.getValue("permit_column_cn_array");
	// if (StringUtils.isNotBlank(colCn)) {
	// Recordset colinfo = temp.getRecordset("col-info");
	// Map table_name = new HashMap();
	// String tab_str = "";
	// for (int i = 0; i < colinfo.size(); i++) {
	// DataBus cinfo = colinfo.get(i);
	// String tableName = cinfo.getValue("table_name_cn");
	// if (!table_name.containsKey(tableName)) {
	// tab_str += (tab_str == "") ? tableName : "," + tableName;
	// }
	// String cont = "";
	// if (colCn.indexOf(cinfo.getValue("column_name_cn").toString()) != -1) {
	// cont = cinfo.getValue("column_name_cn") + "("
	// + cinfo.getValue("column_name") + ")";
	// cont = cont + ",";
	// }
	// String content = table_name.get(tableName) == null ? "<span
	// style='color:red'>"
	// + tableName + "��</span>" + cont
	// : table_name.get(tableName).toString() + cont;
	// table_name.put(cinfo.getValue("table_name_cn"), content);
	// }
	// String con = "";
	// if (StringUtils.isNotBlank(tab_str) && !tab_str.equals("null")) {
	// String[] str = tab_str.split(",");
	//
	// for (int i = 0; i < str.length; i++) {
	// String tab_name = table_name.get(str[i]).toString();
	// if (StringUtils.isNotBlank(tab_name)) {
	// if (tab_name.substring(tab_name.length() - 1).equals(
	// ",")) {
	// con += tab_name.substring(0, tab_name.length() - 1)
	// + "<br/>";
	// }
	// }
	//
	// }
	// config_inf.setValue("permit_column_cn_array", con);
	// }
	// }
	//
	// info_base.setValue("create_by", dbr.getValue("create_by"));
	// info_base.setValue("max_records", dbr.getValue("max_records"));
	// info_base.setValue("svr_code", dbr.getValue("svr_code"));
	// info_base.setValue("svr_name", dbr.getValue("svr_name"));
	// info_base.setValue("create_date", dbr.getValue("create_date"));
	// info_base.setValue("is_pause", config_inf.getValue("is_pause"));
	// // info_base.setValue("is_limit",is_limit);
	//
	// String max_records = dbr.getValue("max_records");
	// StringBuffer limitSb = new StringBuffer(max_records + "��/ÿ��");
	// if (is_limit_number != null && is_limit_number.equals("1")) {
	// limitSb.append(", " + limit_number + "��/ÿ��");
	// }
	// if (is_limit_total != null && is_limit_total.equals("1")) {
	// limitSb.append(", " + limit_total + "��/ÿ��");
	// }
	// info_base.setValue("limit_conn", limitSb.toString());
	// // Recordset rs_tbl = temp.getRecordset("tbl-info");
	// Recordset rs_config = temp.getRecordset("config-param");
	// // System.out.println("������Ϣ����Ϊ ---- "+ rs_config.size());
	//
	// /*
	// * Recordset rs_col = temp.getRecordset("col-info"); ArrayList list =
	// * new ArrayList();
	// *
	// * if(rs_tbl!=null&&rs_tbl.size()>0){
	// *
	// * for(int j=0; j<rs_tbl.size(); j++){ DataBus tbl_db = rs_tbl.get(j);
	// * String table_no = tbl_db.getValue("table_no"); String table_name_cn =
	// * tbl_db.getValue("table_name_cn"); HashMap map = new HashMap();
	// * map.put("tname", table_name_cn); ArrayList list2 = new ArrayList();
	// * for(int i=0; i<rs_col.size(); i++){ DataBus col_db = rs_col.get(i);
	// * String table_no2 = col_db.getValue("table_no"); String column_name_cn =
	// * col_db.getValue("column_name_cn");
	// * if(table_no!=null&&!table_no.equals("")&&table_no.equals(table_no2)){
	// * list2.add(column_name_cn); } } map.put("clist", list2);
	// * list.add(map); } } StringBuffer sb = new StringBuffer();
	// * if(list!=null&&list.size()>0){ for(int j=0; j<list.size(); j++){
	// * HashMap map = (HashMap)list.get(j); String tname = null; Object obj =
	// * map.get("tname"); if(obj!=null){ tname= "<font
	// * color=red>"+map.get("tname")+"</font>"; //System.out.println("
	// * ----------->>>> tname "+tname); StringBuffer sb2 = new
	// * StringBuffer(); sb2.append(tname).append(":"); ArrayList list2 =
	// * (ArrayList)map.get("clist"); for(int i=0; i<list2.size(); i++){
	// * if(i==0){ sb2.append(list2.get(i)); }else{
	// * sb2.append(","+list2.get(i)); } } if(j==0){ sb.append(sb2); }else{
	// * sb.append("<br>"+sb2); } } } }
	// */
	// // ϵͳ����
	// StringBuffer sbc = new StringBuffer();
	// // �û�����
	// StringBuffer sbs = new StringBuffer();
	//
	// if (rs_config != null && rs_config.size() > 0) {
	// for (int j = 0; j < rs_config.size(); j++) {
	// DataBus conf = rs_config.get(j);
	// String param_type = conf.getValue("param_type");
	// if (param_type != null && param_type.equals("0")) {
	// sbc.append(conf.getValue("param_text")).append("<br>");
	// } else if (param_type != null && param_type.equals("1")) {
	// sbs.append(conf.getValue("param_text")).append("<br>");
	// }
	// }
	// }
	//
	// DataBus db_config = new DataBus();
	// /*
	// * if(sb!=null&&!sb.equals("null")&&!sb.equals("")){
	// * db_config.setValue("shared_columns", sb.toString()); }
	// */
	// db_config.setValue("shared_columns", config_inf
	// .getValue("permit_column_cn_array"));
	// if (sbc != null && !sbc.equals("null") && !sbc.equals("")
	// && sbc.length() > 0) {
	// db_config.setValue("sys_params", sbc.toString().equals("") ? "��"
	// : sbc.toString());
	// }
	// if (sbs != null && !sbs.equals("null") && !sbs.equals("")
	// && sbs.length() > 0) {
	// db_config.setValue("user_params", sbs.toString().equals("") ? "��"
	// : sbs.toString());
	// }
	// context.addRecord("info-config", db_config);
	// context.addRecord("info-base", info_base);

	/**
	 * ��ѯĳ���û�����ͼ���
	 */
	public void txn50200023(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		TxnContext temp = new TxnContext();
		DataBus db = context.getRecord(inputNode);
		DataBus info_base = new DataBus();
		DataBus db2 = new DataBus();
		String sys_db_user_id = db.getValue("sys_svr_user_id");
		String sys_db_view_id = db.getValue("sys_svr_service_id");
		db2.setValue("sys_db_user_id", db.getValue("sys_svr_user_id"));
		db2.setValue("sys_db_view_id", db.getValue("sys_svr_service_id"));
		String stype = db.getValue("stype");
		if (stype != null && stype.equals("W")) {
			info_base.setValue("stype", "WebService");
		} else if (stype != null && stype.equals("V")) {
			info_base.setValue("stype", "��ͼ");
		} else if (stype != null && stype.equals("CV")) {
			info_base.setValue("stype", "�Զ�����ͼ");
		}
		// ��ͨ��user_id �� db_id �ж� �����ݿ���ͼ��view���ͻ����������
		TxnContext temp2 = new TxnContext();
		temp2.addRecord("select-key", db2);
		DataBus result = new DataBus();
		StringBuffer sbsql = new StringBuffer(
				" select count(1) as cc  from sys_db_config t where 1=1");
		sbsql.append(" and sys_db_user_id='").append(sys_db_user_id)
				.append("'").append(" and sys_db_view_id='").append(
						sys_db_view_id).append("'").append(
						" and config_type='01'");

		table.executeSelect(sbsql.toString(), result, "record");
		DataBus res = result.getRecord("record");
		String cc = res.getValue("cc");
		DataBus dbr = null;
		// cc=1 ������view cc=0 �������Զ�����ͼ
		if (cc != null && cc.equals("1")) {
			// ��ѯview������Ϣ
			temp.addRecord("select-key", db2);
			this.callService("52102004", temp);
			dbr = temp.getRecord("view-service");
			if (dbr != null && temp != null) {
				info_base.setValue("create_by", dbr.getValue("create_by"));
				info_base.setValue("svr_code", dbr.getValue("view_code"));
				info_base.setValue("svr_name", dbr.getValue("view_name"));
				info_base.setValue("create_date", dbr.getValue("create_date"));
			}
			DataBus db_config = new DataBus();
			Recordset rs_tbl = temp.getRecordset("table-info");
			Recordset rs_col = temp.getRecordset("column-info");
			ArrayList list = new ArrayList();
			if (rs_tbl != null && rs_tbl.size() > 0) {
				for (int j = 0; j < rs_tbl.size(); j++) {
					DataBus tbl_db = rs_tbl.get(j);
					String table_no = tbl_db.getValue("table_no");
					String table_name_cn = tbl_db.getValue("table_name_cn");
					HashMap map = new HashMap();
					map.put("tname", table_name_cn);
					ArrayList list2 = new ArrayList();
					for (int i = 0; i < rs_col.size(); i++) {
						DataBus col_db = rs_col.get(i);
						String table_no2 = col_db.getValue("table_no");
						String column_name_cn = col_db
								.getValue("column_name_cn");
						if (table_no != null && !table_no.equals("")
								&& table_no.equals(table_no2)) {
							list2.add(column_name_cn);
						}
					}
					map.put("clist", list2);
					list.add(map);
				}
			}
			StringBuffer sb = new StringBuffer();
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					HashMap map = (HashMap) list.get(j);
					String tname = null;
					Object obj = map.get("tname");
					if (obj != null) {
						tname = "<font color=red>" + map.get("tname")
								+ "</font>";
						// System.out.println(" ----------->>>> tname "+tname);
						StringBuffer sb2 = new StringBuffer();
						sb2.append(tname).append(":");
						ArrayList list2 = (ArrayList) map.get("clist");
						for (int i = 0; i < list2.size(); i++) {
							if (i == 0) {
								sb2.append(list2.get(i));
							} else {
								sb2.append("," + list2.get(i));
							}
						}
						if (j == 0) {
							sb.append(sb2);
						} else {
							sb.append("<br>" + sb2);
						}
					}
				}
			}
			// ��������
			StringBuffer sbc = new StringBuffer();

			Recordset rs_config = temp.getRecordset("record");

			if (rs_config != null && rs_config.size() > 0) {
				for (int j = 0; j < rs_config.size(); j++) {
					DataBus conf = rs_config.get(j);
					sbc.append(conf.getValue("param_text")).append("<br>");
				}
			}

			if (sb != null && !sb.equals("null") && !sb.equals("")) {
				db_config.setValue("shared_columns", sb.toString());
			}
			if (sbc != null && !sbc.equals("null") && !sbc.equals("")) {
				db_config.setValue("conn_params", sbc.toString());
			}

			context.addRecord("info-config", db_config);
			context.addRecord("info-base", info_base);
			// System.out.println("��ɲ�ѯ�û���ͼ��Ϣ�������� \n " +context);
		} else {
			// ��ѯ�Զ�����ͼ��Ϣ
			DataBus db3 = new DataBus();
			db3.setValue("sys_db_user_id", db.getValue("sys_svr_user_id"));
			db3.setValue("sys_db_config_id", db.getValue("sys_svr_service_id"));
			temp.addRecord("select-key", db3);
			this.callService("52103011", temp);

			DataBus db4 = temp.getRecord("record");

			info_base.setValue("create_by", db4.getValue("create_by"));
			info_base.setValue("create_date", db4.getValue("create_date"));
			info_base.setValue("svr_code", db4.getValue("config_name"));

			DataBus db_config = new DataBus();
			db_config.setValue("grant_table", db4.getValue("alias_column"));
			db_config.setValue("query_sql", db4.getValue("query_sql"));
			context.addRecord("info-base", info_base);
			context.addRecord("info-config", db_config);
			// System.out.println("��ѯ�û��Զ�����ͼ��Ϣ 52103011 \n "+temp);
			throw new TxnDataException("cv", null);
		}

	}

	public void txn50200024(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		// ��ѯ��¼������ VoSysSvrUserSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction("queryCanUsedServicesAndViews", context,
				inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoSysSvrUser result[] = context.getSysSvrUsers( outputNode );
	}

	public void txn50200025(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		// ��ѯ��¼������ VoSysSvrUserSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction("queryUserCanCopy", context, inputNode,
				outputNode);
		// ��ѯ���ļ�¼�� VoSysSvrUser result[] = context.getSysSvrUsers( outputNode );
	}

	/**
	 * �޸�IP
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50200027(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		// ��ѯ��¼������ VoSysSvrUserSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction("update sys_svr_user ip", context, inputNode,
				outputNode);
		// ��ѯ���ļ�¼�� VoSysSvrUser result[] = context.getSysSvrUsers( outputNode );
	}

	/**
	 * �޸�����
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50200028(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		// ��ѯ��¼������ VoSysSvrUserSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction("reset sys_svr_user password", context,
				inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoSysSvrUser result[] = context.getSysSvrUsers( outputNode );
	}

	/**
	 * ��ѯ�û����Կ��������û��ķ����б�
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50200029(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		// ��ѯ��¼������ VoSysSvrUserSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction("queryUserSvrCanCopy", context, inputNode,
				outputNode);
		// ��ѯ���ļ�¼�� VoSysSvrUser result[] = context.getSysSvrUsers( outputNode );
	}

	/**
	 * ���������û��ķ���������Ϣ
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50200030(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		BaseTable tableCfg = TableFactory.getInstance().getTableObject(this,
				"sys_svr_config");
		BaseTable tableCfgParam = TableFactory.getInstance().getTableObject(
				this, "sys_svr_config_param");
		DataBus db = context.getRecord("select-key");
		TxnContext temp = new TxnContext();
		String cfgid = db.getValue("sys_svr_config_id");
		String sid = db.getValue("sys_svr_service_id");
		String uid = db.getValue("sys_svr_user_id");
		String cfgid2 = UuidGenerator.getUUID();
		DataBus r1 = new DataBus();
		StringBuffer s1 = new StringBuffer(
				"select * from sys_svr_config where sys_svr_config_id='");
		s1.append(cfgid).append("'");
		table.executeSelect(s1.toString(), r1, "record");
		DataBus dbr = r1.getRecord("record");
		DataBus dbr2 = (DataBus) dbr.clone();
		DataBus db3 = new DataBus();
		// ��ȡconfig-order
		table.executeSelect(
				"SELECT MAX(config_order) as config_order FROM sys_svr_config",
				db3, "max-order");
		String max = db3.getRecord("max-order").getValue("config_order");
		if (max.trim().equals("")) {
			max = "0";
		}
		int next = Integer.parseInt(max) + 1;
		dbr2.setValue("create_date", CalendarUtil.getCurrentDate());
		String uId = context.getOperData().getValue("oper-name");
		dbr2.setValue("create_by", uId);
		dbr2.setValue("sys_svr_config_id", cfgid2);
		dbr2.setValue("sys_svr_user_id", uid);
		dbr2.setValue("config_order", "" + next);
		dbr2.setValue("query_sql", formatSQL(dbr2.getValue("query_sql")));
		dbr2.setValue("column_alias", formatSQL(dbr2.getValue("column_alias")));
		temp.addRecord("record", dbr2);
		// ����������������Ϣ
		tableCfg.executeFunction("insert one sys_svr_config", temp, "record",
				"record");

		System.out.println("�������������Ϣ \n" + temp);

		// ��ʼ�����û��������ò�����Ϣ
		TxnContext r2 = new TxnContext();
		StringBuffer s2 = new StringBuffer(
				"select * from sys_svr_config_param where sys_svr_config_id='");
		s2.append(cfgid).append("' order by param_order ");
		table.executeRowset(s2.toString(), r2, "record");

		Recordset rs = r2.getRecordset("record");
		System.out.println("��Ҫ���Ƶ��û����ò����ĸ���Ϊ��" + rs.size());
		if (rs != null && rs.size() > 0) {
			// ��ȡparam_order
			DataBus db4 = new DataBus();
			table
					.executeSelect(
							" SELECT MAX(param_order) as param_order FROM sys_svr_config_param ",
							db4, "max-order");
			String max2 = db4.getRecord("max-order").getValue("param_order");
			if (max2.trim().equals("")) {
				max2 = "0";
			}
			int next2 = Integer.parseInt(max2) + 1;
			for (int j = 0; j < rs.size(); j++) {
				DataBus db5 = rs.get(j);
				DataBus db6 = (DataBus) db5.clone();
				db6
						.setValue("sys_svr_config_param_id", UuidGenerator
								.getUUID());
				db6.setValue("param_value", formatSQL(db6
						.getValue("param_value")));
				db6.setValue("sys_svr_config_id", cfgid2);
				db6.setValue("param_order", "" + next2);
				TxnContext temp2 = new TxnContext();
				temp2.addRecord("record", db6);
				tableCfgParam.executeFunction(
						"insert one sys_svr_config_param", temp2, "record",
						"record");
				next2++;
				System.out.println("��" + (j + 1) + "�������������������Ϣ \n" + temp2);
			}
		}

	}

	/**
	 * ��ӡ�ӿ�word��Ϣ
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50200031(SysSvrUserContext context) throws TxnException
	{
		DataBus db = context.getRecord("select-key");
		DataBus db2 = new DataBus();

		db2.setValue("sys_svr_user_id", db.getValue("sys_svr_user_id"));
		db2.setValue("sys_svr_service_id", db.getValue("sys_svr_service_id"));
		db2.setValue("stype", "W");
		TxnContext ctx = new TxnContext();
		ctx.addRecord("select-key", db2);
		// callService("50200022", ctx);
		// 50200032������Ϊ������ֶγ��Ⱥ��ֶ����͵Ĵ����ף�50200022��������ֶγ��Ⱥ��ֶ����͵Ĵ����� add by
		// dwn20121107
		callService("50200032", ctx);
		// System.out.println("ctx:" + ctx);
		DataBus info_config = ctx.getRecord("info-config");
		DataBus info_base = ctx.getRecord("info-base");

		Recordset info_columns = ctx.getRecordset("info-column");

		context.addRecord("info-column", info_columns);
		context.addRecord("info_config", info_config);
		context.addRecord("info_base", info_base);

		TxnContext ctx2 = new TxnContext();
		DataBus db3 = new DataBus();
		db3.setValue("sys_svr_user_id", db.getValue("sys_svr_user_id"));
		ctx2.addRecord("record", db3);
		this.callService("50201004", ctx2);
		DataBus record = ctx2.getRecord("record");
		context.addRecord("record", record);

	}

	/**
	 * �ӿ����õ����ļ�������ֶ����ͺͳ���
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50200032(SysSvrUserContext context) throws TxnException
	{
		StringBuffer columns = new StringBuffer();
		TxnContext temp = new TxnContext();
		TxnContext limit = new TxnContext();
		DataBus db = context.getRecord(inputNode);
		DataBus info_base = new DataBus();
		DataBus db2 = new DataBus();
		db2.setValue("sys_svr_user_id", db.getValue("sys_svr_user_id"));
		db2.setValue("sys_svr_service_id", db.getValue("sys_svr_service_id"));
		String stype = db.getValue("stype");
		if (stype != null && stype.equals("W")) {
			info_base.setValue("stype", "WebService");
		} else if (stype != null && stype.equals("V")) {
			info_base.setValue("stype", "View");
		}

		String is_limit_number = null;
		String limit_number = null;
		String is_limit_total = null;
		String limit_total = null;
		// ��ѯ�޶�����
		// String is_limit = db.getValue("is_limit");

		limit.addRecord("select-key", db2);
		this.callService("50011701", limit);

		Recordset rs = limit.getRecordset("record");
		ArrayList list = new ArrayList();
		if (rs != null && rs.size() > 0) {
			DataBus limit0 = rs.get(0);

			info_base.setValue("is_limit_time", limit0
					.getValue("is_limit_time"));
			info_base.setValue("is_limit_number", limit0
					.getValue("is_limit_number"));
			info_base.setValue("is_limit_total", limit0
					.getValue("is_limit_total"));
			info_base.setValue("limit_week", limit0.getValue("limit_week"));
			info_base.setValue("limit_start_time", limit0
					.getValue("limit_start_time"));
			info_base.setValue("limit_end_time", limit0
					.getValue("limit_end_time"));
			info_base.setValue("limit_number", limit0.getValue("limit_number"));
			info_base.setValue("limit_total", limit0.getValue("limit_total"));

			for (int j = 0; j < rs.size(); j++) {
				DataBus limit2 = rs.get(j);
				String is_limit_week = limit2.getValue("is_limit_week");
				String limit_week = limit2.getValue("limit_week");
				if (is_limit_week != null && is_limit_week.equals("0")) {
					list.add(limit_week);
				}
			}
			String is_limit_time = limit0.getValue("is_limit_time");
			if (is_limit_time != null && is_limit_time.equals("1")) {
				String limit_start_time = limit0.getValue("limit_start_time");
				String limit_end_time = limit0.getValue("limit_end_time");
				info_base.setValue("open_time", getOpenDayStr(list) + " "
						+ limit_start_time + " �� " + limit_end_time);
			} else {
				info_base.setValue("open_time", getOpenDayStr(list) + " ȫ�� ");
			}

			is_limit_number = limit0.getValue("is_limit_number");
			limit_number = limit0.getValue("limit_number");
			is_limit_total = limit0.getValue("is_limit_total");
			limit_total = limit0.getValue("limit_total");

		}
		String ss = info_base.getValue("open_time");
		if (ss == null || ss.equals("")) {
			info_base.setValue("open_time", "������");
		}
		// ��ѯ������Ϣ
		temp.addRecord("select-key", db2);
		this.callService("50203004", temp);
		DataBus dbr = temp.getRecord("record");
		DataBus config_inf = temp.getRecord("config-inf");
		String colCn = config_inf.getValue("permit_column_cn_array");
		if (StringUtils.isNotBlank(colCn)) {
			Recordset colinfo = temp.getRecordset("col-info");
			Map table_name = new HashMap();
			String tab_str = "";
			for (int i = 0; i < colinfo.size(); i++) {
				DataBus cinfo = colinfo.get(i);
				String tableName = cinfo.getValue("table_name_cn");
				// �����ֶκŻ�ȡ�ֶγ��Ⱥ����� add by dwn 20121107
				if (i != 0) {
					if (cinfo.getValue("column_no") != null
							&& !cinfo.getValue("column_no").equals("")
							&& !columns.toString().equals("")) {
						columns
								.append(",'" + cinfo.getValue("column_no")
										+ "'");
					}
				} else {
					columns.append("'" + cinfo.getValue("column_no") + "'");
				}

				if (!table_name.containsKey(tableName)) {
					tab_str += (tab_str == "") ? tableName : "," + tableName;
				}
				String cont = cinfo.getValue("column_name_cn") + "("
						+ cinfo.getValue("column_name") + ")";
				String content = table_name.get(tableName) == null ? "<span style='color:red'>"
						+ tableName + "��</span>" + cont
						: table_name.get(tableName).toString() + "," + cont;
				table_name.put(cinfo.getValue("table_name_cn"), content);
			}

			String con = "";
			if (StringUtils.isNotBlank(tab_str) && !tab_str.equals("null")) {
				String[] str = tab_str.split(",");
				for (int i = 0; i < str.length; i++) {
					con += table_name.get(str[i]) + "<br/>";
				}
				config_inf.setValue("permit_column_cn_array", con);
			}
		}

		info_base.setValue("create_by", dbr.getValue("create_by"));
		info_base.setValue("max_records", dbr.getValue("max_records"));
		info_base.setValue("svr_code", dbr.getValue("svr_code"));
		info_base.setValue("svr_name", dbr.getValue("svr_name"));
		info_base.setValue("create_date", dbr.getValue("create_date"));
		info_base.setValue("is_pause", config_inf.getValue("is_pause"));
		// info_base.setValue("is_limit",is_limit);

		String max_records = dbr.getValue("max_records");
		StringBuffer limitSb = new StringBuffer(max_records + "��/ÿ��");
		if (is_limit_number != null && is_limit_number.equals("1")) {
			limitSb.append(", " + limit_number + "��/ÿ��");
		}
		if (is_limit_total != null && is_limit_total.equals("1")) {
			limitSb.append(", " + limit_total + "��/ÿ��");
		}
		info_base.setValue("limit_conn", limitSb.toString());
		// Recordset rs_tbl = temp.getRecordset("tbl-info");
		Recordset rs_config = temp.getRecordset("config-param");
		// System.out.println("������Ϣ����Ϊ ---- "+ rs_config.size());

		// ϵͳ����
		StringBuffer sbc = new StringBuffer();
		// �û�����
		StringBuffer sbs = new StringBuffer();

		if (rs_config != null && rs_config.size() > 0) {
			for (int j = 0; j < rs_config.size(); j++) {
				DataBus conf = rs_config.get(j);
				String param_type = conf.getValue("param_type");
				if (param_type != null && param_type.equals("0")) {
					sbc.append(conf.getValue("param_text")).append("<br>");
				} else if (param_type != null && param_type.equals("1")) {
					sbs.append(conf.getValue("param_text")).append("<br>");
				}
			}
		}

		DataBus db_config = new DataBus();
		/*
		 * if(sb!=null&&!sb.equals("null")&&!sb.equals("")){
		 * db_config.setValue("shared_columns", sb.toString()); }
		 */
		db_config.setValue("shared_columns", config_inf
				.getValue("permit_column_cn_array"));
		if (sbc != null && !sbc.equals("null") && !sbc.equals("")
				&& sbc.length() > 0) {
			db_config.setValue("sys_params", sbc.toString().equals("") ? "��"
					: sbc.toString());
		}
		if (sbs != null && !sbs.equals("null") && !sbs.equals("")
				&& sbs.length() > 0) {
			db_config.setValue("user_params", sbs.toString().equals("") ? "��"
					: sbs.toString());
		}

		// �ֶ����ͺ��ֶγ���
		TxnContext ctxColumn = new TxnContext();
		DataBus dbColumn = new DataBus();
		dbColumn.setValue("column_no", columns.toString());
		ctxColumn.addRecord("select-key", dbColumn);
		this.callService("80002507", ctxColumn);
		Recordset recordColumn = ctxColumn.getRecordset("record");
		context.addRecord("info-column", recordColumn);

		context.addRecord("info-config", db_config);
		context.addRecord("info-base", info_base);

		// table.executeFunction( "queryOneService", context, inputNode,
		// outputNode );
		// System.out.println(" ---------------->>>>>> \n txn50200022
		// \n-------------------->>>>> \n"+context);
		// ��ѯ��¼������ VoSysSvrUserSelectKey selectKey = context.getSelectKey(
		// inputNode );
		// table.executeFunction( "queryUserLimit", context, inputNode,
		// outputNode );
		// ��ѯ���ļ�¼�� VoSysSvrUser result[] = context.getSysSvrUsers( outputNode );

	}

	/**
	 * ��ѯ��������б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50201001(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		if (context.getRecord(inputNode).getValue("showall") != null) {
			Attribute.setPageRow(context, outputNode, -1);
		}
		// ��ѯ��¼������ VoSysSvrUserSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼�� VoSysSvrUser result[] = context.getSysSvrUsers( outputNode );
	}

	/**
	 * �޸ķ��������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50201002(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String uName = context.getRecord("record").getValue("login_name");
		String old_uName = context.getRecord("record").getValue(
				"old_login_name");
		String uId = context.getRecord("record").getValue("sys_svr_user_id");
		String userName = context.getRecord("record").getValue("user_name");
		String old_userName = context.getRecord("record").getValue(
				"old_user_name");
		DataBus bus = new DataBus();
		bus = new DataBus();
		int i = 0;
		if (!old_uName.equals(uName)) {
			bus.setValue("login_name", uName);
			context.addRecord("select-key", bus);
			i = table.executeFunction(SELECT_USERNAME_FUNCTION, context,
					"select-key", "exist-user");
			if (i > 0) {
				String existUId = context.getRecord("exist-user").getValue(
						"sys_svr_user_id");
				if (!existUId.trim().equalsIgnoreCase(uId)) {
					throw new TxnDataException("999999", "���û����Ѵ��ڣ�");
				}
			}
			context.remove("exist-user");
		}
		if (!old_userName.equals(userName)) {
			bus = new DataBus();
			bus.setValue("user_name", context.getRecord("record").getValue(
					"user_name"));
			context.addRecord("select-key", bus);
			i = table.executeFunction(SELECT_USERNAME_FUNCTION, context,
					"select-key", "exist-user");
			if (i > 0) {
				String existUId = context.getRecord("exist-user").getValue(
						"sys_svr_user_id");
				if (!existUId.trim().equalsIgnoreCase(uId)) {
					throw new TxnDataException("999999", "�������Ѵ��ڣ�");
				}
			}
			context.remove("exist-user");
		}

		// �޸ļ�¼������ VoSysSvrUser sys_svr_user = context.getSysSvrUser( inputNode
		// );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);

		setBizLog("�޸ķ������", context, context.getRecord("record").getValue(
				"login_name"));
	}

	/**
	 * ���ӷ��������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50201003(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		TxnContext cont = new TxnContext();
		table.executeSelect(
				"SELECT MAX(user_order) as user_order FROM sys_svr_user", cont,
				"max-order");
		String v = cont.getRecord("max-order").getValue("user_order");
		int max = Integer.parseInt(v.equalsIgnoreCase("") ? "0" : v);
		context.getRecord("record").setValue(
				"create_date",
				new SimpleDateFormat(Constants.DB_DATE_FORMAT)
						.format(new Date()));
		String uId = context.getOperData().getValue("oper-name");
		context.getRecord("record").setValue("create_by", uId);
		context.getRecord("record").setValue("state", "0");
		context.getRecord("record").setValue("password",
				UuidGenerator.getUUID().substring(0, 8));
		context.getRecord("record").setValue("sys_svr_user_id",
				UuidGenerator.getUUID());
		context.getRecord("record").setValue("user_order", "" + (max + 1));
		// ���Ӽ�¼������ VoSysSvrUser sys_svr_user = context.getSysSvrUser( inputNode
		// );

		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);

		setBizLog("���ӷ������", context, context.getRecord("record").getValue(
				"login_name"));
	}

	/**
	 * ��ѯ������������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50201004(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoSysSvrUserPrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// ��ѯ���ļ�¼���� VoSysSvrUser result = context.getSysSvrUser( outputNode );
	}

	/**
	 * ɾ�����������Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50201005(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoSysSvrUserPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);

		setBizLog("ɾ���������", context, context.getRecord("record").getValue(
				"login_name"));
	}

	/**
	 * �޸ķ������״̬
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50201006(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String state = context.getRecord("record").getValue("state");
		table.executeFunction("updateState", context, inputNode, outputNode);
		if (state
				.equalsIgnoreCase(com.gwssi.common.util.Constants.RUNMGR_USER_STATE_START)) {
			setBizLog("���÷������", context, context.getRecord("record").getValue(
					"login_name"));
		} else {
			setBizLog("ͣ�÷������", context, context.getRecord("record").getValue(
					"login_name"));
		}
	}

	/**
	 * ��ѯ��������Ƿ������÷���
	 * 
	 * @param context
	 * @throws TxnException
	 */
	public void txn50201007(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("checkConfig", context, inputNode, outputNode);
	}

	/**
	 * ��ԃ�������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50201009(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("checkExistUser", context, inputNode, outputNode);
	}

	/**
	 * ��ʼ�������������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50201010(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		String pwd = new MD5().getMD5ofStr(context.getRecord(inputNode)
				.getValue("password"));
		context.getRecord(inputNode).setValue("password", pwd);
		table.executeFunction("resetPassword", context, inputNode, outputNode);
		setBizLog("�����ʼ����", context, context.getRecord("record").getValue(
				"login_name"));
	}

	/**
	 * ɾ���û�����
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50201011(SysSvrUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryUserConfigId", context, inputNode,
				outputNode);
		context.getRecord("select-key").setValue("sys_svr_config_id",
				context.getRecord(outputNode).getValue("sys_svr_config_id"));
		// ɾ������������ò���
		this.callService("50206005", context);
		// ɾ����¼�������б� VoSysSvrConfigPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction("deleteUserConfig", context, inputNode,
				outputNode);

	}

	/**
	 * ͣ�û������û�����
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn50201012(SysSvrUserContext context) throws TxnException
	{

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		table.executeFunction("updateUserConfigPause", context, inputNode,
				outputNode);
	}

	/**
	 * ��¼��־
	 * 
	 * @param type
	 * @param context
	 */
	private void setBizLog(String type, TxnContext context, String jgmc)
	{

		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME)
				.setValue(com.gwssi.common.util.Constants.VALUE_NAME,
						type + jgmc);
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
		SysSvrUserContext appContext = new SysSvrUserContext(context);
		invoke(method, appContext);
	}

	private String getOpenDayStr(ArrayList list)
	{
		String[] week1 = { "-", "��һ", "�ܶ�", "����", "����", "����", "����", "����" };
		String[] week2 = { "-", "1", "2", "3", "4", "5", "6", "7" };
		ArrayList list2 = new ArrayList();
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (int i = 1; i < 8; i++) {
				for (int j = 0; j < list.size(); j++) {
					String s = "" + list.get(j);
					if (week2[i].equals(s)) {
						list2.add(week1[i]);
						break;
					}
				}
			}
		}
		if (list2 != null && list2.size() > 0) {
			for (int j = 0; j < list2.size(); j++) {
				if (j == 0) {
					sb.append("" + list2.get(j));
				} else {
					sb.append("��" + list2.get(j));
				}
			}
			return new String("ÿ�� " + sb.toString());
		} else {
			return "��";
		}
	}

	private String formatSQL(String sql)
	{
		String sb = sql.replaceAll("'", "''");

		return sb;
	}
}
