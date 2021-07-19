package com.gwssi.share.interfaces.dao;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.UuidGenerator;

public class ShareInterface extends BaseTable
{
	public ShareInterface()
	{

	}

	/**
	 * 注册SQL语句
	 */
	protected void register()
	{
		registerSQLFunction("getShareBusiSystem", DaoFunction.SQL_ROWSET,
				"获取提供共享的业务系统");
		registerSQLFunction("getTopicByBusSystem", DaoFunction.SQL_ROWSET,
				"根据业务系统ID获取本业务系统下的所有主题");
		registerSQLFunction("getTableByTopic", DaoFunction.SQL_ROWSET,
				"根据业务主题ID获取本业务主题下的所有表");
		registerSQLFunction("getItemsByTable", DaoFunction.SQL_ROWSET,
				"根据表ID获取本表下的所有共享数据项");
		registerSQLFunction("getInfoByitfState", DaoFunction.SQL_ROWSET,
		"根据指定代码集获得接口统计信息");
		registerSQLFunction("getInfoById", DaoFunction.SQL_ROWSET,
		"根据服务对象获得服务统计信息");
		registerSQLFunction("insertTableCondition", DaoFunction.SQL_INSERT,
				"插入表关联关系");
		registerSQLFunction("insertTableParam", DaoFunction.SQL_INSERT,
				"插入表查询条件");
		registerSQLFunction("querySelectTable", DaoFunction.SQL_ROWSET,
				"查询接口配置信息");
		registerSQLFunction("queryObjectByTopics", DaoFunction.SQL_ROWSET,
				"查询业务系统根据主题");
		registerSQLFunction("queryTableParam", DaoFunction.SQL_ROWSET,
				"查询表的查询条件");
		registerSQLFunction("queryTableCondition", DaoFunction.SQL_ROWSET,
				"查询表的关联条件");
		registerSQLFunction("deleteTableParam", DaoFunction.SQL_DELETE,
				"查询表的查询条件");
		registerSQLFunction("deleteTableCondition", DaoFunction.SQL_DELETE,
				"查询表的关联条件");
		registerSQLFunction("queryTableTopColumn", DaoFunction.SQL_ROWSET,
				"查询表的头几条");
		registerSQLFunction("setInterfaceIsmarkupAsDelete", DaoFunction.SQL_UPDATE,
				"逻辑删除设置标志位");
		registerSQLFunction("query_interface_markuplist", DaoFunction.SQL_ROWSET,
				"取逻辑标志位有效的列表");
		registerSQLFunction("getInfoByService", DaoFunction.SQL_ROWSET,
				"query interface by interface id");
		registerSQLFunction("queryServiceByInterfaceId", DaoFunction.SQL_SELECT, "根据接口查询服务");
	}

	/** 
	 * 执行SQL语句前的处理
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * 
	 * getAllBusSystem(获取所有业务系统) 接口配置里用，在本系统中被称为服务对象。 只查询有效 2013.3.18 lizheng
	 * add
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getShareBusiSystem(TxnContext request, DataBus inputData)
	{
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer .append("select t.service_targets_id as key , t.service_targets_name as title "
				+ "from res_service_targets t where t.is_markup = '"
				+ ExConstant.IS_MARKUP_Y
				+ "' and (select count(1) from res_business_topics s where s.service_targets_id = t.service_targets_id) > 0"
				+ " order by t.service_targets_type, t.show_order");

		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		// stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}

	/**
	 * getTopicByBusSystem 根据业务系统ID获取本业务系统下的所有主题
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getTopicByBusSystem(TxnContext request,
			DataBus inputData)
	{
		String key = "";
		if (null != request.getValue("key")
				&& !"".equals(request.getValue("key").toString())) {
			key = request.getValue("key").toString();
		}

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.topics_no as key , "
				+ "t.topics_name as title from res_business_topics t ");
		if (!"".equals(key))
			sqlBuffer.append(" where t.service_targets_id ='" + key
					+ "' order by show_order");

		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		// stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}

	/**
	 * 
	 * getTableByTopic
	 * 
	 * 根据业务主题ID获取本业务主题下的所有表
	 * 
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getTableByTopic(TxnContext request, DataBus inputData)
	{
		String key = "";
		if (null != request.getValue("key")
				&& !"".equals(request.getValue("key").toString())) {
			key = request.getValue("key").toString();
		}
		System.out.println("key_________________________________" + key);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("select t.share_table_id as key ,"
						+ "t.table_name_cn as title,t.table_name_en as code from res_share_table t where t.table_name_cn is not null ");
		if (!"".equals(key))
			sqlBuffer.append(" and t.business_topics_id = '" + key
					+ "' order by show_order");
System.out.println("根据主题查表："+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}

	/**
	 * 
	 * getItemsByTable) 根据表ID获取本表下的所有共享数据项
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement insertTableCondition(TxnContext request,
			DataBus inputData)
	{
		StringBuffer sql = new StringBuffer(
				"insert into share_inter_condition (CONDITION_ID, ")
				.append(
						"INTERFACE_ID, FRIST_CONNECTOR, LEFT_PAREN,LEFT_TABLE_NO,LEFT_TABLE_NAME_EN, LEFT_TABLE_NAME_CN,")
				.append(
						"LEFT_COLUMN_NO,LEFT_COLUMN_NAME_EN,LEFT_COLUMN_NAME_CN, SECOND_CONNECTOR, RIGHT_TABLE_NO,RIGHT_TABLE_NAME_EN, ")
				.append(
						"RIGHT_TABLE_NAME_CN, RIGHT_COLUMN_NO,RIGHT_COLUMN_NAME_EN, RIGHT_COLUMN_NAME_CN, RIGHT_PAREN,")
				.append("SHOW_ORDER) values (");
		sql.append("'").append(UuidGenerator.getUUID()).append("',");
		sql.append("'").append(inputData.getValue("interface_id")).append("',");
		sql.append("'").append(inputData.getValue("FRIST_CONNECTOR")).append(
				"',");
		sql.append("'',");
		sql.append("'").append(inputData.getValue("LEFT_TABLE_NO"))
				.append("',");
		sql.append("'").append(inputData.getValue("LEFT_TABLE_NAME_EN"))
				.append("',");
		sql.append("'").append(inputData.getValue("LEFT_TABLE_NAME_CN"))
				.append("',");
		sql.append("'").append(inputData.getValue("LEFT_COLUMN_NO")).append(
				"',");
		sql.append("'").append(inputData.getValue("LEFT_COLUMN_NAME_EN"))
				.append("',");
		sql.append("'").append(inputData.getValue("LEFT_COLUMN_NAME_CN"))
				.append("',");
		sql.append("'").append(inputData.getValue("SECOND_CONNECTOR")).append(
				"',");
		sql.append("'").append(inputData.getValue("RIGHT_TABLE_NO")).append(
				"',");
		sql.append("'").append(inputData.getValue("RIGHT_TABLE_NAME_EN"))
				.append("',");
		sql.append("'").append(inputData.getValue("RIGHT_TABLE_NAME_CN"))
				.append("',");
		sql.append("'").append(inputData.getValue("RIGHT_COLUMN_NO")).append(
				"',");
		sql.append("'").append(inputData.getValue("RIGHT_COLUMN_NAME_EN"))
				.append("',");
		sql.append("'").append(inputData.getValue("RIGHT_COLUMN_NAME_CN"))
				.append("',");
		sql.append("'',");
		sql.append("SEQ_SHARE_INTERFACE_CONDITION.NEXTVAL)");
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	/**
	 * 
	 * getItemsByTable) 根据表ID获取本表下的所有共享数据项
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement insertTableParam(TxnContext request, DataBus inputData)
	{
		StringBuffer sql = new StringBuffer(
				"insert into SHARE_INTER_PARAM (PARAM_ID, ")
				.append(
						"INTERFACE_ID, FRIST_CONNECTOR, LEFT_PAREN,TABLE_NO,TABLE_NAME_EN, TABLE_NAME_CN,")
				.append(
						"COLUMN_NO,COLUMN_NAME_EN,COLUMN_NAME_CN, SECOND_CONNECTOR, PARAM_TYPE,PARAM_VALUE, ")
				.append("RIGHT_PAREN,SHOW_ORDER) values (");
		sql.append("'").append(UuidGenerator.getUUID()).append("',");
		sql.append("'").append(inputData.getValue("interface_id")).append("',");
		sql.append("'").append(inputData.getValue("FRIST_CONNECTOR")).append(
				"',");
		sql.append("'").append(inputData.getValue("LEFT_PAREN")).append("',");
		sql.append("'").append(inputData.getValue("TABLE_NO")).append("',");
		sql.append("'").append(inputData.getValue("TABLE_NAME_EN"))
				.append("',");
		sql.append("'").append(inputData.getValue("TABLE_NAME_CN"))
				.append("',");
		sql.append("'").append(inputData.getValue("COLUMN_NO")).append("',");
		sql.append("'").append(inputData.getValue("COLUMN_NAME_EN")).append(
				"',");
		sql.append("'").append(inputData.getValue("COLUMN_NAME_CN")).append(
				"',");
		sql.append("'").append(inputData.getValue("SECOND_CONNECTOR")).append(
				"',");
		String param_type = URLDecoder.decode(inputData.getValue("PARAM_TYPE"));
		sql.append("'").append(param_type).append("',");
		sql.append("'").append(inputData.getValue("PARAM_VALUE")).append("',");
		sql.append("'").append(inputData.getValue("RIGHT_PAREN")).append("',");
		sql.append("SEQ_SHARE_INTER_PARAM.NEXTVAL)");
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	/**
	 * 
	 * getItemsByTable) 根据表ID获取本表下的所有共享数据项
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getItemsByTable(TxnContext request, DataBus inputData)
	{
		String key = "";
		if (null != request.getValue("key")
				&& !"".equals(request.getValue("key").toString())) {
			key = request.getValue("key").toString();
		}

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.share_dataitem_id as key , ")
			.append("t.dataitem_name_cn as title,t.dataitem_name_en as code,")
			.append("t.code_table as code_table,")
			.append(" t.dataitem_type_r as col_type from res_share_dataitem t ")
			.append("where t.dataitem_name_cn is not null ");
		if (StringUtils.isNotBlank(key))
			sqlBuffer.append(" and t.share_table_id = '" + key + "'");
		sqlBuffer.append(" order by t.show_order");
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}

	/**
	 * 
	 * queryInterfaceDetail(获取接口配置的信息) TODO(这里描述这个方法适用条件 – 可选)
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 */
	public SqlStatement querySelectTable(TxnContext request, DataBus inputData)
	{
		String key = request.getRecord("record").getValue("table_id");
		SqlStatement stmt = new SqlStatement();
		if (StringUtils.isNotBlank(key)) {
			String nos = "";
			String[] keys = key.split(",");
			for (int i = 0; i < keys.length; i++) {
				nos += (nos == "" ? "'" + keys[i] + "'" : ",'" + keys[i] + "'");
			}
			StringBuffer sql = new StringBuffer();
			sql
					.append(
							"select business_topics_id as topics_id,table_no as key, table_name_en as code, table_name_cn title")
					.append(" from res_share_table t where t.table_no in (")
					.append(nos).append(") order by show_order");
			stmt.addSqlStmt(sql.toString());
		}
		return stmt;
	}

	public SqlStatement queryObjectByTopics(TxnContext request,
			DataBus inputData)
	{
		String key = request.getValue("key");
		SqlStatement stmt = new SqlStatement();
		if (StringUtils.isNotBlank(key)) {
			StringBuffer sql = new StringBuffer();
			sql.append("select c.service_targets_id ").append(
					"from res_business_topics c where c.topics_no='").append(
					key).append("'");
			System.out.println(sql.toString());
			stmt.addSqlStmt(sql.toString());
		}
		return stmt;
	}

	public SqlStatement queryTableParam(TxnContext request, DataBus inputData)
	{
		String key = request.getValue("key");
		SqlStatement stmt = new SqlStatement();
		if (StringUtils.isNotBlank(key)) {
			StringBuffer sql = new StringBuffer();
			sql.append("select a.frist_connector,a.left_paren,a.table_no,");
			sql.append(" b.table_name_en,b.table_name_cn,a.column_no,");
			sql.append(" c.dataitem_name_en,c.dataitem_name_cn,a.param_type,");
			sql.append(" a.param_value,a.right_paren,c.dataitem_type_r from (select * ");
			sql.append(" from share_inter_param t where t.interface_id = '");
			sql.append(key).append("' ) a, res_share_table b,");
			sql.append("res_share_dataitem c where a.table_no = b.table_no");
			sql
					.append(" and a.column_no = c.share_dataitem_id order by b.table_name_cn,c.dataitem_name_cn");
			System.out.println(sql.toString());
			stmt.addSqlStmt(sql.toString());
			return stmt;
		} else {
			return null;
		}

	}

	public SqlStatement queryTableCondition(TxnContext request,
			DataBus inputData)
	{
		String key = request.getValue("key");
		System.out.println("queryTableCondition-key:" + key);
		SqlStatement stmt = new SqlStatement();
		if (StringUtils.isNotBlank(key)) {
			StringBuffer sql = new StringBuffer();
			sql
					.append("select * from share_inter_condition t where t.interface_id='");
			sql.append(key).append("' order by t.show_order");
			stmt.addSqlStmt(sql.toString());
			stmt.setPageRows(-1);
			System.out.println("queryTableCondition-sql:" + sql.toString());
			return stmt;
		} else {
			return null;
		}
	}

	public SqlStatement deleteTableCondition(TxnContext request,
			DataBus inputData)
	{
		String key = request.getValue("key");
		SqlStatement stmt = new SqlStatement();
		if (StringUtils.isNotBlank(key)) {
			StringBuffer sql = new StringBuffer();
			sql.append("delete from share_inter_condition t where t.interface_id='");
			sql.append(key).append("'");
			stmt.addSqlStmt(sql.toString());
			return stmt;
		} else {
			return null;
		}
	}

	public SqlStatement deleteTableParam(TxnContext request, DataBus inputData)
	{
		String key = request.getValue("key");
		SqlStatement stmt = new SqlStatement();
		if (StringUtils.isNotBlank(key)) {
			StringBuffer sql = new StringBuffer();
			sql.append("delete from share_inter_param t where t.interface_id='");
			sql.append(key).append("'");
			stmt.addSqlStmt(sql.toString());
			return stmt;
		} else {
			return null;
		}
	}
	
	public SqlStatement queryTableTopColumn(TxnContext request, DataBus inputData)
	{
		String tableIds = request.getRecord("select-key").getValue("tableIds");
		SqlStatement stmt = new SqlStatement();
		if (StringUtils.isNotBlank(tableIds)) {
			String tids= tableIds.split(",")[0];
			StringBuffer sql = new StringBuffer();
			sql.append("select * from(select t.share_table_id,t1.table_name_en,");
			sql.append("t.dataitem_name_en,t.dataitem_name_cn ");
			sql.append("from res_share_dataitem t,res_share_table t1 where t.share_table_id");
			sql.append("=t1.share_table_id and t.share_table_id = '");
			sql.append(tids).append("' and t.show_order is not null)  where rn <= 3");
			stmt.addSqlStmt(sql.toString());
			stmt.setPageRows(-1);
			return stmt;
		} else {
			return null;
		}
//		if (StringUtils.isNotBlank(tableIds)) {
//			String tids="";
//			String[] ids=tableIds.split(",");
//			for (int i = 0; i < ids.length; i++) {
//				tids+= (tids=="" ? "'"+ids[i]+"'" : ",'"+ids[i]+"'");
//			}
//			StringBuffer sql = new StringBuffer();
//			sql.append("select * from(select t.share_table_id,t1.table_name_en,");
//			sql.append("t.dataitem_name_en,t.dataitem_name_cn,row_number() ");
//			sql.append("over(partition by t.share_table_id order by t.show_order) rn");
//			sql.append(" from res_share_dataitem t,res_share_table t1 where t.share_table_id");
//			sql.append("=t1.share_table_id and t.share_table_id in (");
//			sql.append(tids).append(") and t.show_order is not null)  where rn <= 3");
//			stmt.addSqlStmt(sql.toString());
//			stmt.setPageRows(-1);
//			return stmt;
//		} else {
//			return null;
//		}
	}

	
	public SqlStatement setInterfaceIsmarkupAsDelete( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String userId = request.getRecord("oper-data").getValue("userID");
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		String sqlSetFlag = "update share_interface t set is_markup='"+ExConstant.IS_MARKUP_N+"',last_modify_id='"+userId+"',last_modify_time='"+datetime+"'   where t.interface_id= ";
		try {
			for(int i=0;i<request.getRecordset("primary-key").size();i++){
				String service_id=request.getRecordset("primary-key").get(i).getValue("interface_id");
				if(i==0){
					sqlSetFlag+="'"+service_id+"' ";
				}
				else{
					sqlSetFlag+="or t.interface_id= '"+service_id+"' ";
				}
				
			}
		} catch (TxnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stmt.addSqlStmt( sqlSetFlag );
		return stmt;
	}
	
	public SqlStatement getInfoById(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String table = inputData.getValue("table_name");
		String column = inputData.getValue("col_name");
		String title = inputData.getValue("col_title");
		if (StringUtils.isNotBlank(table) && StringUtils.isNotBlank(column)
				&& StringUtils.isNotBlank(title)) {
			sqlBuffer
			.append("select tar." + column + " as key, tar." + title
					+ " as title ")
			.append("from " + table + " tar ")
			.append("where tar.is_markup = 'Y' ")
			.append("order by tar.last_modify_time ");
			stmt.addSqlStmt(sqlBuffer.toString());
		}

		return stmt;
	}
	
	public SqlStatement getInfoByService(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select s1.interface_id as key, ")
				.append("s1.interface_name as title from share_interface s1 where s1.is_markup='Y' order by interface_name ");
		stmt.addSqlStmt(sqlBuffer.toString());

		return stmt;
	}
	
	public SqlStatement getInfoByitfState(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append("select cd.codename as title, cd.codevalue as key, NVL(t.mt, 0) as amount ")
					.append("from codedata cd, ")
					.append("(select itf.")
					.append(column)
					.append(", count(itf.interface_id) as mt ")
					.append("from share_interface itf where itf.is_markup = 'Y' group by itf.")
					.append(column).append(") t where cd.codetype = '")
					.append(codeType).append("' and cd.codename != '归档' and cd.codevalue = t.")
					.append(column).append("(+) order by amount desc");
			stmt.addSqlStmt(sqlBuffer.toString());
			// stmt.setCountStmt("select count(*) from (" +
			// sqlBuffer.toString()+ ")");
		}
		return stmt;
	}
	public SqlStatement query_interface_markuplist( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus db = request.getRecord("select-key");
		String interface_name = db.getValue("interface_name");
		String interface_state = db.getValue("interface_state");
		String interface_id = db.getValue("interface_id");
		String created_time_start = db.getValue("created_time");
		//String created_time_end = db.getValue("created_time_end");
		
		StringBuffer sql = new StringBuffer("select t.*,substr(t.last_modify_time,0,10) as last_time,t1.yhxm from share_interface t,xt_zzjg_yh_new t1 "); 
		
		sql.append(" where t.last_modify_id = t1.yhid_pk and t.is_markup='"+ExConstant.IS_MARKUP_Y+"'");
		
		if(interface_name != null && !interface_name.equals("")){
			sql.append( " and t.interface_name like '%" + interface_name + "%'");
		}
		if (interface_id != null && !interface_id.equals("")) {
			sql.append(" and t.interface_id = '" + interface_id + "'");
		}
		if(interface_state != null && !interface_state.equals("")){
			sql.append( " and t.interface_state = '" + interface_state + "'");
		}
		if (StringUtils.isNotBlank(created_time_start)) {
			if (!created_time_start.equals("点击选择日期")) {
				String[] times = DateUtil.getDateRegionByDatePicker(
						created_time_start, true);
				sql.append(" and t.created_time >= '" + times[0] + "'");
				sql.append(" and t.created_time <= '" + times[1] + "'");
			}
		}
		sql.append(" order by  t.last_modify_time desc ");
		
		stmt.addSqlStmt( sql.toString() );
		stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );
		return stmt;
	}
	
	public SqlStatement queryServiceByInterfaceId( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus db = request.getRecord("primary-key");
		String interface_id = db.getValue("interface_id");
		
		StringBuffer sql = new StringBuffer("select count(1) amount from (select t.service_id from share_service t where 1=1 "); 
		sql.append(" and t.is_markup='"+ExConstant.IS_MARKUP_Y+"' ");
		
		if(StringUtils.isNotBlank(interface_id)){
			sql.append( " and t.interface_id = '" + interface_id + "'");
		}
		sql.append(")");
		stmt.addSqlStmt( sql.toString() );
//		stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );
		return stmt;
	}
	
	/**
	 * 执行完SQL语句后的处理
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}
	
	public static void main(String[] args)
	{
//		String b="REG_BUS_ENT.REG_BUS_ENT_ID,REG_BUS_ENT.ENT_NAME,REG_BUS_ENT.REG_NO,REG_BUS_ENT_APP.HYPOTAXIS,REG_BUS_ENT_APP.DEP_IN_CHA,REG_BUS_ENT_APP.OP_FORM";
//		String a="SELECT * FROM REG_BUS_ENT,REG_BUS_ENT_APP WHERE REG_BUS_ENT.REG_BUS_ENT_ID=REG_BUS_ENT_APP.REG_BUS_ENT_ID AND REG_BUS_ENT.REG_BUS_ENT_ID=REG_BUS_ENT_APP.HYPOTAXIS AND REG_BUS_ENT.REG_NO>='1100'";
//		System.out.println(a.replace("*", b));
	}

}
