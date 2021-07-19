package com.gwssi.share.service.dao;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.collect.webservice.vo.VoCollectTaskScheduling;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.task.SimpleTriggerRunner;
import com.gwssi.common.util.DateUtil;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

/**
 * 数据表[share_service]的处理类
 * 
 * @author Administrator
 * 
 */
public class ShareService extends BaseTable
{
	public ShareService()
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
		// table.executeFunction( "loadShareServiceList", context, inputNode,
		// outputNode );
		// XXX: registerSQLFunction( "loadShareServiceList",
		// DaoFunction.SQL_ROWSET, "获取共享服务的服务表列表" );
		registerSQLFunction("queryShareServiceList", DaoFunction.SQL_ROWSET,
				"获取服务列表");
		registerSQLFunction("queryShareServiceListOrder",
				DaoFunction.SQL_ROWSET, "获取服务列表");
		registerSQLFunction("queryServiceNo", DaoFunction.SQL_ROWSET, "获取服务号");
		registerSQLFunction("setIsmarkupAsDelete", DaoFunction.SQL_UPDATE,
				"逻辑删除设置标志位");
		registerSQLFunction("getMaxService_no", DaoFunction.SQL_SELECT,
				"获取最大服务编号");
		registerSQLFunction("getInfoBysvrState", DaoFunction.SQL_ROWSET,
				"根据指定代码集获得服务统计信息");
		registerSQLFunction("getInfoByTarget", DaoFunction.SQL_ROWSET,
				"根据服务对象获得服务统计信息");
		registerSQLFunction("getInfoByService", DaoFunction.SQL_ROWSET,
				"根据服务对象获得服务统计信息");
		registerSQLFunction("queryServiceNo_tmp", DaoFunction.SQL_ROWSET,
				"临时获取服务号");
		registerSQLFunction("deleteFtpService", DaoFunction.SQL_UPDATE,
		"删除ftp共享服务信息");
		
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
	 * queryShareServiceList(获取服务列表) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程
	 * – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement queryShareServiceList(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		DataBus db = request.getRecord("select-key");
		String service_targets_id = db.getValue("service_targets_id");
		String service_type = db.getValue("service_type");
		String service_targets_type = db.getValue("service_targets_type");
		String interface_id = db.getValue("interface_id");
		String service_state = db.getValue("service_state");
		String service_name = db.getValue("service_name");
		String created_time_start = db.getValue("created_time");
		
		StringBuffer sql = new StringBuffer("select service_id,t.interface_id,t.interface_id as interface_id1,t.service_targets_id,t.service_targets_id as service_targets_id1,service_name,service_type,service_no,column_no,column_name_en,column_name_cn,column_alias,sort_column,t.sql,sql_one,sql_two,service_description,regist_description,service_state,t.is_markup,t.creator_id,SUBSTR(t.created_time,0,10) as created_time,t.last_modify_id,SUBSTR(t.last_modify_time,0,10) as last_modify_time ,jsoncolumns,t.fj_fk,t.fjmc,limit_data,old_service_no,t1.yhxm from xt_zzjg_yh_new t1,share_service t ");
		//StringBuffer sql = new StringBuffer("select service_id,interface_id,interface_id as interface_id1,t.service_targets_id,t.service_targets_id as service_targets_id1,service_name,service_type,service_no,column_no,column_name_en,column_name_cn,column_alias,sort_column,sql,sql_one,sql_two,service_description,regist_description,service_state,t.is_markup,t.creator_id,SUBSTR(t.created_time,0,10) as created_time,t.last_modify_id,SUBSTR(t.last_modify_time,0,10) as last_modify_time ,jsoncolumns,t.fj_fk,t.fjmc,limit_data,old_service_no,t1.yhxm from xt_zzjg_yh_new t1,share_service t ");
		
		sql.append(" left join res_service_targets s on t.service_targets_id=s.service_targets_id")
			.append(" left join share_interface i on i.interface_id=t.interface_id");
		sql.append(" where t.last_modify_id = t1.yhid_pk and t.is_markup='").append(ExConstant.IS_MARKUP_Y).append("'")
			.append(" and s.is_markup='").append(ExConstant.IS_MARKUP_Y).append("' and i.is_markup='").append(ExConstant.IS_MARKUP_Y).append("'");

		if (service_name != null && !service_name.equals("")) {
			sql.append(" and t.service_name like '%" + service_name + "%'");
		}
		if (service_state != null && !service_state.equals("")) {
			sql.append(" and t.service_state = '" + service_state + "'");
		}
		if (interface_id != null && !interface_id.equals("")) {
			sql.append(" and t.interface_id = '" + interface_id + "'");
		}
		if (service_type != null && !service_type.equals("")) {
			sql.append(" and t.service_type = '" + service_type + "'");
		}
		if (service_targets_id != null && !service_targets_id.equals("")) {
			sql.append(" and t.service_targets_id = '" + service_targets_id
					+ "'");
		}
		if (StringUtils.isNotBlank(created_time_start)) {
			if (!created_time_start.equals("点击选择日期")) {
				String[] times = DateUtil.getDateRegionByDatePicker(
						created_time_start, true);
				sql.append(" and t.created_time >= '" + times[0] + "'");
				sql.append(" and t.created_time <= '" + times[1] + "'");
			}
		}
		if (StringUtils.isNotBlank(service_targets_type)) {
			sql.append(" and s.service_targets_type='").append(service_targets_type).append("' ");
			/*String tmpSql = sql.toString();
			sql.setLength(0);
			sql.append("select a.* from (")
					.append(tmpSql)
					.append(" ) a, res_service_targets b")
					.append(" where a.service_targets_id = b.service_targets_id and b.service_targets_type = '")
					.append(service_targets_type)
					.append("' and b.is_markup='Y'")
					.append(" order by a.last_modify_time desc");*/
		}
		sql.append(" order by  t.last_modify_time desc ");
		//System.out.println("queryShareServiceList="+sql.toString());
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(*) from (" + sql.toString() + ")");
		return stmt;
	}

	/**
	 * 
	 * queryShareServiceList(获取服务列表) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程
	 * – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement queryShareServiceListOrder(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus db = request.getRecord("select-key");
		String service_targets_id = db.getValue("service_targets_id");
		String service_targets_type = db.getValue("service_targets_type");
		String service_type = db.getValue("service_type");
		String interface_id = db.getValue("interface_id");
		String service_state = db.getValue("service_state");
		String service_name = db.getValue("service_name");
		String created_time_start = db.getValue("created_time_start");
		String created_time_end = db.getValue("created_time_end");

		StringBuffer sql = new StringBuffer(
				"select service_id,interface_id,service_targets_id,service_targets_id as service_targets_name,service_name,service_type,service_no,old_service_no,column_no,column_name_en,column_name_cn,column_alias,sort_column,sql,sql_one,sql_two,service_description,regist_description,service_state,is_markup,creator_id,SUBSTR(created_time,0,10) as created_time,last_modify_id,last_modify_time,jsoncolumns,fj_fk,fjmc,limit_data from share_service t");

		sql.append(" where t.is_markup='" + ExConstant.IS_MARKUP_Y + "'");

		if (service_name != null && !service_name.equals("")) {
			sql.append(" and t.service_name like '%" + service_name + "%'");
		}
		if (service_state != null && !service_state.equals("")) {
			sql.append(" and t.service_state = '" + service_state + "'");
		}
		if (interface_id != null && !interface_id.equals("")) {
			sql.append(" and t.interface_id = '" + interface_id + "'");
		}
		if (service_type != null && !service_type.equals("")) {
			sql.append(" and t.service_type = '" + service_type + "'");
		}

		if (StringUtils.isNotBlank(service_targets_type)) {
			sql.append(" and t.service_targets_id in(select r.service_targets_id ");
			sql.append("from res_service_targets r where r.service_targets_type='" + service_targets_type
					+ "') ");
		}

		if (service_targets_id != null && !service_targets_id.equals("")) {
			sql.append(" and t.service_targets_id = '" + service_targets_id
					+ "'");
		}
		if (StringUtils.isNotBlank(created_time_start)) {
			sql.append(" and t.created_time >= '" + created_time_start + "'");
		}
		if (StringUtils.isNotBlank(created_time_end)) {
			sql.append(" and t.created_time <= '" + created_time_end + "'");
		}
		sql.append(" order by  t.last_modify_time desc ");
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(*) from (" + sql.toString() + ")");
		return stmt;
	}

	/**
	 * 
	 * queryServiceNo(获取服务号(service_no)) TODO(这里描述这个方法适用条件 – 可选)
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
	public SqlStatement queryServiceNo(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		String service_id = request.getRecord("select-key").getValue(
				"service_id");

		StringBuffer querySql = new StringBuffer(
				"select t.service_no,t.column_name_cn as permit_column_cn_array ,t.column_alias ,t.column_name_en as permit_column_en_array from share_service t "
						+ " where 1=1 ");
		if (service_id != null && !"".equals(service_id)) {
			querySql.append(" and t.service_id = '" + service_id + "'");
		}
		log.debug("查询service_no:" + querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;

	}

	/**
	 * 20130621-add by dwn queryServiceNo(测试专用_临时获取服务号(service_no))
	 * TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 *             SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement queryServiceNo_tmp(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		String service_id = request.getRecord("select-key").getValue(
				"service_id");

		StringBuffer querySql = new StringBuffer(
				"select t.service_targets_no  as username,t.service_password  as password ,t1.service_no,t1.column_name_cn as permit_column_cn_array ,t1.column_alias ,t1.column_name_en as permit_column_en_array from res_service_targets t,share_service t1 where t1.service_targets_id = t.service_targets_id  ");
		if (service_id != null && !"".equals(service_id)) {
			querySql.append(" and t1.service_id = '" + service_id + "'");
		}
		stmt.addSqlStmt(querySql.toString());
		return stmt;

	}

	public SqlStatement setIsmarkupAsDelete(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String userId = request.getRecord("oper-data").getValue("userID");
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());

		String sqlSetFlag = "update share_service t set is_markup='"
				+ ExConstant.IS_MARKUP_N + "',last_modify_id='" + userId
				+ "',last_modify_time='" + datetime
				+ "'   where t.service_id= ";
		try {
			for (int i = 0; i < request.getRecordset("primary-key").size(); i++) {
				String service_id = request.getRecordset("primary-key").get(i)
						.getValue("service_id");
				if (i == 0) {
					sqlSetFlag += "'" + service_id + "' ";
				} else {
					sqlSetFlag += "or t.service_id= '" + service_id + "' ";
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
	 * 删除ftp共享服务信息
	 * @param request
	 * @param inputData
	 * @return
	 * @throws DBException 
	 * @throws TxnException 
	 */
	public SqlStatement deleteFtpService(TxnContext request,
			DataBus inputData) throws DBException, TxnException
	{
		SqlStatement stmt = new SqlStatement();
		
		//删除任务调度
		String service_id=request.getRecord("primary-key").getValue("service_id");
		String srv_scheduling_id=null;
		ServiceDAO	daoTable	= new ServiceDAOImpl();; // 操作数据表Dao
		String sql="select srv_scheduling_id from share_srv_scheduling where service_id = '"+service_id+"'";
	
		Map tablepMap = daoTable.queryService(sql);// 获取任务调度ID
		if(tablepMap!=null&&!tablepMap.isEmpty()){
			srv_scheduling_id=(String)tablepMap.get("SRV_SCHEDULING_ID");
			if(srv_scheduling_id!=null&&!"".equals(srv_scheduling_id)){
				VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
				vo.setcollect_task_id(service_id);
				SimpleTriggerRunner.removeFromScheduler(vo);
			}
		}
		
		
		String sqlDelFlag = "delete from share_ftp_srv_param t  where t.ftp_service_id in  "
			+"(select ftp_service_id from share_ftp_service v where v.service_id = ";
		
		try {
			for (int i = 0; i < request.getRecordset("primary-key").size(); i++) {
				service_id = request.getRecordset("primary-key").get(i)
						.getValue("service_id");
				if (i == 0) {
					sqlDelFlag += "'" + service_id + "' ";
				} else {
					sqlDelFlag += "or t.service_id= '" + service_id + "' ";
				}

			}
		} catch (TxnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlDelFlag+=")";
		//System.out.println("sqlDelFlag====="+sqlDelFlag);
		stmt.addSqlStmt(sqlDelFlag);
		
		//删除share_ftp_service信息
		sqlDelFlag = "delete from share_ftp_service t  where t.service_id = ";
		try {
			for (int i = 0; i < request.getRecordset("primary-key").size(); i++) {
				service_id = request.getRecordset("primary-key").get(i)
						.getValue("service_id");
				if (i == 0) {
					sqlDelFlag += "'" + service_id + "' ";
				} else {
					sqlDelFlag += "or t.service_id= '" + service_id + "' ";
				}

			}
		} catch (TxnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("删除share_ftp_service信息====="+sqlDelFlag);
		stmt.addSqlStmt(sqlDelFlag);
		
		//删除share_srv_scheduling信息
		sqlDelFlag = "delete from share_srv_scheduling t  where t.service_id  = ";
		try {
			for (int i = 0; i < request.getRecordset("primary-key").size(); i++) {
				service_id = request.getRecordset("primary-key").get(i)
						.getValue("service_id");
				if (i == 0) {
					sqlDelFlag += "'" + service_id + "' ";
				} else {
					sqlDelFlag += "or t.service_id= '" + service_id + "' ";
				}

			}
		} catch (TxnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("删除share_srv_scheduling====="+sqlDelFlag);
		stmt.addSqlStmt(sqlDelFlag);
		
		return stmt;
	}

	public SqlStatement getMaxService_no(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt("select * from (select TO_NUMBER(ltrim(t.service_no,'service')) as snum  from share_service t  where t.service_no like 'service%' order by snum desc) where rownum =1");
		return stmt;
	}

	/**
	 * 
	 * getInfoBysvrState 根据指定代码集返回该字段相关的服务统计信息 比如服务状态，分别统计 启用、停用、归档的服务各有多少个
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement getInfoBysvrState(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append("select cd.codename as title, cd.codevalue as key, NVL(t.mt, 0) as amount ")
					.append("from codedata cd, ")
					.append("(select svr.")
					.append(column)
					.append(", count(svr.service_id) as mt ")
					.append("from share_service svr ,res_service_targets r")
					.append(" where svr.is_markup = 'Y' and r.service_targets_id=svr.service_targets_id")
					.append(" and r.is_markup='Y' group by svr.")
					.append(column).append(") t where cd.codetype = '")
					.append(codeType).append("' and cd.codevalue = t.")
					.append(column).append("(+) order by amount desc");
			//System.out.println("getInfoBysvrState="+sqlBuffer.toString());
			
			stmt.addSqlStmt(sqlBuffer.toString());
			// stmt.setCountStmt("select count(*) from (" +
			// sqlBuffer.toString()+ ")");
		}
		return stmt;
	}
	
	public SqlStatement getInfoByService(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from ( select s1.interface_id as key, ")
				.append("s1.interface_name as title,  (select count(1) ")
				.append("from share_service t ")
				.append("where t.interface_id = s1.interface_id and t.is_markup = '")
				.append(ExConstant.IS_MARKUP_Y)
				.append("') as amount ")
				.append("from share_interface s1 where s1.is_markup='Y' ) t order by t.amount desc ");
		stmt.addSqlStmt(sqlBuffer.toString());
		//System.out.println("getInfoByService="+sqlBuffer.toString());
		return stmt;
	}

	/**
	 * 
	 * getInfoByTarget 根据指定字段获得服务的统计信息 比如每个服务对象配置多少个服务
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
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
					.append("with a as (select tar." + column + " as key, tar." + title
							+ " as title, NVL(t.mt, 0) as amount")
					.append("res_service_targets".equals(table) ? ", tar.service_targets_type "
							: ", tar.interface_state ")
					.append(" ,tar.show_order, tar.last_modify_time ")
					.append("from " + table + " tar, ")
					.append("(select s." + column
							+ ", count(s.service_id) as mt ")
					.append("from share_service s where s.is_markup = 'Y' ")
					.append("group by s." + column + ") t ")
					.append("where tar.is_markup = 'Y' and tar." + column
							+ " = t." + column + "(+)) ");
			if("res_service_targets".equals(table)){
				sqlBuffer.append(" select * from (select * from a")
					.append(" where service_targets_type = '000'")			
					.append(" order by show_order) union all ")
					.append(" select * from(select * from a")
					.append(" where service_targets_type <> '000'")
					.append(" order by service_targets_type,title)");
			}else{
				sqlBuffer.append("select * from a order by tar.last_modify_time");
			}
					
			//System.out.println("getInfoByTarget="+sqlBuffer.toString());
			stmt.addSqlStmt(sqlBuffer.toString());
		}

		return stmt;
	}

}
