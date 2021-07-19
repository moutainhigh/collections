package com.gwssi.resource.svrobj.dao;

import org.apache.commons.lang.StringUtils;

import com.ctc.wstx.util.DataUtil;
import com.gwssi.common.util.DateUtil;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

public class ResDataSource extends BaseTable
{
	public ResDataSource()
	{

	}

	/**
	 * 注册SQL语句
	 */
	protected void register()
	{
		registerSQLFunction("queryDataSource", DaoFunction.SQL_ROWSET, "查询数据源");
		registerSQLFunction("deleteDataSource", DaoFunction.SQL_UPDATE, "删除数据源");
		registerSQLFunction("queryDataSourceUse", DaoFunction.SQL_ROWSET,
				"查询数据源是否被引用");
		registerSQLFunction("queryDSCountByType", DaoFunction.SQL_ROWSET,
				"根据数据源类型数据源个数");
		registerSQLFunction("queryDSCountBySvrObj", DaoFunction.SQL_ROWSET,
				"根据数据源类型数据源个数");
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
	 * 
	 * queryDataSource(查询数据源) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 *             SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement queryDataSource(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		String data_source_id = request.getRecord("select-key").getValue(
				"data_source_id");
		String data_source_type = request.getRecord("select-key").getValue(
				"data_source_type");
		String service_targets_id = request.getRecord("select-key").getValue(
				"service_targets_id");
		String service_targets_type = request.getRecord("select-key").getValue(
				"service_targets_type");
		String created_time = request.getRecord("select-key").getValue(
				"created_time");

		StringBuffer querySql = new StringBuffer(
				"select t.data_source_id,t.service_targets_id,t.data_source_type,t.data_source_name,t.data_source_ip," +
				"t.access_port,t.access_url,t.db_type,t.db_instance,t.db_username,t.db_password,t.db_desc,t.db_status," +
				"t.is_markup,nvl(substr(t.last_modify_time,0,10),substr(t.created_time,0,10))  time,nvl(y2.yhxm,y1.yhxm) name,t1.service_targets_name " +
				"from res_data_source t,res_service_targets t1,xt_zzjg_yh_new y1,xt_zzjg_yh_new y2 "
						+ " where t.service_targets_id=t1.service_targets_id and  t.creator_id=y1.yhid_pk(+) and t.last_modify_id=y2.yhid_pk(+)");
		if (data_source_id != null && !"".equals(data_source_id)) {
			querySql.append(" and t.data_source_id like '%" + data_source_id
					+ "%'");
		}

		if (data_source_type != null && !"".equals(data_source_type)) {
			querySql.append(" and t.data_source_type = '" + data_source_type
					+ "'");
		}

		if (service_targets_id != null && !"".equals(service_targets_id)) {
			querySql.append(" and t.service_targets_id = '"
					+ service_targets_id + "'");
		}

		if (StringUtils.isNotBlank(service_targets_type)) {
			querySql.append(" and t1.service_targets_type = '"
					+ service_targets_type + "'");
		}

		if (StringUtils.isNotBlank(created_time)) {
			String[] times = DateUtil.getDateRegionByDatePicker(created_time,
					true);
			if (StringUtils.isNotBlank(times[0])) {
				querySql.append(" and t.created_time >= '" + times[0] + "'");
			}
			if (StringUtils.isNotBlank(times[1])) {
				querySql.append(" and t.created_time <= '" + times[1] + "'");
			}
		}
		querySql.append(" and t.is_markup='Y' and t1.is_markup='Y'");
		querySql.append(" order by time desc ");

	    //System.out.println("查询数据源："+querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;

	}

	/**
	 * 
	 * deleteDataSource(删除数据源) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 *             SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement deleteDataSource(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("primary-key");
		String data_source_id = data.getValue("data_source_id");

		StringBuffer updateSql = new StringBuffer(
				"update res_data_source t set is_markup='N' "
						+ "  where t.data_source_id='" + data_source_id + "'");
		System.out.println("删除数据源:::" + updateSql.toString());
		stmt.addSqlStmt(updateSql.toString());
		return stmt;
	}

	/**
	 * 
	 * queryDataSource(查询数据源是否被引用) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 –
	 * 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 *             SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement queryDataSourceUse(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		String data_source_id = request.getRecord("primary-key").getValue(
				"data_source_id");

		StringBuffer querySql = new StringBuffer(
				"select data_source_id from collect_task t3 where 1=1");
		if (data_source_id != null && !"".equals(data_source_id)) {
			querySql.append(" and t3.data_source_id ='" + data_source_id + "'");
		}

		// System.out.println("查询数据源使用情况："+querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;

	}

	public SqlStatement queryDSCountByType(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer querySql = new StringBuffer(
				"select r.data_source_type key,count(1) amount ");
		querySql.append(" from res_data_source r,res_service_targets s ");
		querySql.append(" where r.service_targets_id=s.service_targets_id ");
		querySql.append("and r.is_markup='Y' and s.is_markup = 'Y' group by r.data_source_type");
		querySql.append(" order by r.data_source_type");
		stmt.addSqlStmt(querySql.toString());
		//System.out.println("queryDSCountByType="+querySql.toString());
		return stmt;

	}

	public SqlStatement queryDSCountBySvrObj(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer querySql = new StringBuffer(
				"with tmp as (select t.service_targets_id key,t.service_targets_name title,t.service_targets_type,nvl(a.c,0) amount ,t.show_order from( ");
		querySql.append(" select r.service_targets_id,s.service_targets_name,count(1) c")
			.append(" from res_data_source r,res_service_targets s where r.service_targets_id=s.service_targets_id")
			.append(" and r.is_markup='Y' group by r.service_targets_id,s.service_targets_name)a,res_service_targets t")
			.append(" where a.service_targets_id(+)=t.service_targets_id and t.is_markup='Y')")
			.append(" select * from (select * from tmp where service_targets_type = '000' order by show_order)")
			.append(" union all select * from (select * from tmp where service_targets_type <> '000' order by service_targets_type, title)");
		
		//System.out.println("queryDSCountBySvrObj="+querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		return stmt;

	}

}
