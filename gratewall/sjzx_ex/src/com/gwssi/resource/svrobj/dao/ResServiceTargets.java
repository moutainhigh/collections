package com.gwssi.resource.svrobj.dao;

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

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：ResServiceTargets 类描述： 创建人：lvhao 创建时间：2013-3-15
 * 下午03:20:58 修改人：lvhao 修改时间：2013-3-15 下午03:20:58 修改备注：
 * 
 * @version
 * 
 */
public class ResServiceTargets extends BaseTable
{
	public ResServiceTargets()
	{

	}

	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register()
	{
		// 以下是注册用户自定义函数的过程
		// 包括三个参数：SQL语句的名称，类型，描述
		// 业务类可以通过以下函数调用:
		// table.executeFunction( "loadResServiceTargetsList", context,
		// inputNode, outputNode );
		// XXX: registerSQLFunction( "loadResServiceTargetsList",
		// DaoFunction.SQL_ROWSET, "获取服务对象表列表" );
		registerSQLFunction("setIs_markToZero", DaoFunction.SQL_UPDATE,
				"逻辑删除设置标志位");
		registerSQLFunction("queryResServiceTargetsList",
				DaoFunction.SQL_ROWSET, "获取服务对象表列表");
		registerSQLFunction("queryServiceUse", DaoFunction.SQL_ROWSET,
				"查询服务对象是否被引用");
		registerSQLFunction("getInfoBySvrObjType", DaoFunction.SQL_ROWSET,
				"获取根据服务对象类型统计个数");
		registerSQLFunction("getTargetTopOrder", DaoFunction.SQL_SELECT,
						"获取根据服务对象类型获取最大排序");
		registerSQLFunction("getTargetCollectTable", DaoFunction.SQL_ROWSET,
				"获取根据服务对象的采集任务对应的采集表");
		registerSQLFunction("getTargetStatistics", DaoFunction.SQL_ROWSET,
				"获取根据服务对象的采集任务对应的采集表");
	}

	/**
	 * 执行SQL语句前的处理
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * 执行完SQL语句后的处理
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * XXX:用户自定义的SQL语句 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句 对于其它的语句，只需要生成一个语句
	 * 
	 * @param request
	 *            交易的上下文
	 * @param inputData
	 *            生成语句的输入节点
	 * @return
	 * 
	 *         public SqlStatement loadResServiceTargetsList( TxnContext
	 *         request, DataBus inputData ) { SqlStatement stmt = new
	 *         SqlStatement( ); stmt.addSqlStmt(
	 *         "select * from res_service_targets" ); stmt.setCountStmt(
	 *         "select count(*) from res_service_targets" ); return stmt; }
	 */

	/**
	 * 
	 * queryResServiceTargetsList(根据输入条件查询服务对象列表) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement queryResServiceTargetsList(TxnContext request,
			DataBus inputData)
	{

		DataBus db = request.getRecord("select-key");
		String service_targets_id = db.getValue("service_targets_id");
		String service_targets_type = db.getValue("service_targets_type");
		String service_status = db.getValue("service_status");
		String created_time_start = db.getValue("created_time");
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer(
				"select t.service_targets_id, t.service_targets_no, t.service_targets_name, t.service_targets_type, "
						+ "t.is_bind_ip, t.ip, t.service_password, t.service_status, t.service_desc, t.is_markup, t.creator_id, SUBSTR(t.created_time,0,10) as created_time ,y1.yhxm as creator_name,y2.yhxm as last_modify_name, "
						+ "t.last_modify_id, SUBSTR(t.last_modify_time,0,10) as last_modify_time,t.is_formal from res_service_targets t ,xt_zzjg_yh_new y1,xt_zzjg_yh_new y2 ");

		sql.append("where t.is_markup='"
				+ ExConstant.IS_MARKUP_Y
				+ "' and t.creator_id=y1.yhid_pk(+) and t.last_modify_id=y2.yhid_pk(+) ");

		if (service_targets_id != null && !service_targets_id.equals("")) {
			sql.append(" and t.service_targets_id = '" + service_targets_id
					+ "'");
		}
		if (service_targets_type != null && !service_targets_type.equals("")) {
			sql.append(" and t.service_targets_type = '" + service_targets_type
					+ "'");
		}
		if (service_status != null && !service_status.equals("")) {
			sql.append(" and t.service_status = '" + service_status + "'");
		}
		if (StringUtils.isNotBlank(created_time_start)) {
			if (!created_time_start.equals("点击选择日期")) {
				String[] times = DateUtil.getDateRegionByDatePicker(
						created_time_start, true);
				sql.append(" and t.created_time >= '" + times[0] + "'");
				sql.append(" and t.created_time <= '" + times[1] + "'");
			}
		}
	/*	if("000".equals(service_targets_type) || StringUtils.isBlank(service_targets_type)){
			sql.append(" order by t.service_targets_type, t.show_order ");
		}else{
			sql.append(" order by t.service_targets_type, t.service_targets_name ");
		}*/
		
		sql.append(" order by t.last_modify_time desc");
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(*) from(" + sql.toString() + ")");
		return stmt;
	}

	/**
	 * 
	 * setIs_markToZero(设置有效标记为为无效，逻辑删除服务对象) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement setIs_markToZero(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String userId = request.getRecord("oper-data").getValue("userID");
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());

		String sqlSetFlag = "update res_service_targets t set is_markup='"
				+ ExConstant.IS_MARKUP_N + "',last_modify_id='" + userId
				+ "',last_modify_time='" + datetime
				+ "'   where t.service_targets_id= ";
		try {
			for (int i = 0; i < request.getRecordset("primary-key").size(); i++) {
				String service_targets_id = request.getRecordset("primary-key")
						.get(i).getValue("service_targets_id");
				if (i == 0) {
					sqlSetFlag += "'" + service_targets_id + "' ";
				} else {
					sqlSetFlag += "or t.service_targets_id= '"
							+ service_targets_id + "' ";
				}

			}
		} catch (TxnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stmt.addSqlStmt(sqlSetFlag);
		return stmt;
	}

	/**
	 * 
	 * queryDataSource(查询服务对象在数据源中是否被引用) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 *             SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement queryServiceUse(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		String service_targets_id = request.getRecord("primary-key").getValue(
				"service_targets_id");

		StringBuffer querySql = new StringBuffer(
				"select service_targets_id from (select distinct service_targets_id from res_data_source where is_markup='Y' "
						+ " union select distinct service_targets_id from RES_COLLECT_TABLE where is_markup='Y' "
						+ " union select distinct service_targets_id from  SHARE_SERVICE where is_markup='Y' "
						+ " union select distinct service_targets_id from  trs_share_service where is_markup='Y' "
						+ " union select distinct service_targets_id from COLLECT_TASK where is_markup='Y') t where 1=1  ");
		if (service_targets_id != null && !"".equals(service_targets_id)) {
			querySql.append(" and t.service_targets_id ='" + service_targets_id
					+ "'");
		}

		// System.out.println("查询服务对象的使用情况："+querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;

	}

	public SqlStatement getInfoBySvrObjType(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		String sql = "with tmp as (select r.service_targets_id key,r.service_targets_name title,r.service_targets_type, count(1) amount,show_order from res_service_targets r,xt_zzjg_yh_new t "
				+ " where r.creator_id=t.yhid_pk(+) and r.is_markup='Y' group by r.service_targets_id,r.service_targets_type,r.service_targets_name,show_order )"
				+" select * from (select * from tmp where service_targets_type = '000' order by show_order) union all"
				+" select * from (select * from tmp where service_targets_type <> '000' order by service_targets_type, title)";
		System.out.println("201001--"+sql);
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	public SqlStatement getTargetTopOrder(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String type = request.getRecord("select-key").getValue("service_targets_type");
		String sql = "select max(t.show_order)+1 as toporder from res_service_targets t";
		if(StringUtils.isNotBlank(type)){
			sql += " where t.service_targets_type = '"+ type +"'";
		}

		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	public SqlStatement getTargetCollectTable(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String targetId = request.getRecord("select-key").getValue(
				"service_targets_id");
		String sql = "select distinct rt.collect_table_id as service_targets_id, rt.table_name_cn as service_targets_name "
				+ "from collect_task ct, res_collect_table rt "
				+ "where rt.service_targets_id = ct.service_targets_id "
				+ "and ct.task_status = 'Y' and rt.is_markup = 'Y' and ct.is_markup = 'Y' "
				+ "and ct.service_targets_id = '" + targetId + "'";

		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	public SqlStatement getTargetStatistics(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String targetId = request.getRecord("select-key").getValue(
				"service_targets_id");
		String sql = " select '' as service_targets_id, '' as min_date, "
				+ "nvl(sum(s.sum_record_amount), 0) as amount, nvl(floor(sum(s.exec_count) / count(1)), 0) as avg_times, "
				+ "nvl(trunc(sum(s.avg_consume_time) / count(1), 2), 0) as avg_time, nvl(sum(s.error_num), 0) as error_num, "
				+ "nvl(trunc(sum(s.error_num) / sum(s.exec_count), 2), 0) as error_r from share_log_statistics s "
				+ "where s.service_targets_id = '"+ targetId + "' "
				+ "union all select '' as service_targets_id, '' as min_date, nvl(sum(c.sum_record_amount), 0), "
				+ "nvl(floor(sum(c.exec_count) / count(1)), 0), nvl(trunc(sum(c.avg_consume_time) / count(1), 2), 0) as avt_time, "
				+ "0 as error_num, 0 as error_r from collect_log_statistics c " 
				+ "where c.service_targets_id = '"+ targetId + "' "
				+ " union all "
				+ "select '"
				+ targetId
				+ "' as service_targets_id, min(t.log_date) log_date, "
				+ "-1, -1, -1, -1, -1 from (select s.log_date "
				+ "from (select min(t.log_date) log_date, t.service_targets_id "
				+ "from share_log_statistics t group by t.service_targets_id) s "
				+ "where s.service_targets_id = '"
				+ targetId
				+ "' "
				+ "union all select min(cls.last_time) log_date from collect_log_statistics cls "
				+ "where cls.service_targets_id = '" + targetId + "') t ";
		System.out.println("201016:"+sql);
		stmt.addSqlStmt(sql);
		return stmt;
	}

}
