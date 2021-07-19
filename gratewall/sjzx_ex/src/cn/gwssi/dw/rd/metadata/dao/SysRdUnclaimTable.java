package cn.gwssi.dw.rd.metadata.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

public class SysRdUnclaimTable extends BaseTable
{
   public SysRdUnclaimTable()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   //
	   registerSQLFunction( "queryUnclaimTable", DaoFunction.SQL_ROWSET, "查询未认领表" );
	   registerSQLFunction( "queryUnclaimViewList", DaoFunction.SQL_ROWSET, "查询未认领视图" );
	   registerSQLFunction( "queryUnclaimFunctionList", DaoFunction.SQL_ROWSET, "查询未认领函数" );
	   registerSQLFunction( "selectStateOfTable", DaoFunction.SQL_ROWSET, "统计物理表的认领数量" );
	   registerSQLFunction( "queryUnclaimProcedureList", DaoFunction.SQL_ROWSET, "查询未认领存储过程" );
	   registerSQLFunction("deleteByDataSource",DaoFunction.SQL_DELETE,"根据数据源删除未认领表");
	   registerSQLFunction("insertBySync",DaoFunction.SQL_INSERT,"数据源同步时向未认领表插入信息");
	   registerSQLFunction( "updateRecordCount", DaoFunction.SQL_UPDATE, "更新未认领表数据量字段" );
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
    * 查询未认领表
    * @param request
    * @param inputData
    * @return
    * @throws TxnException
    */
	public SqlStatement queryUnclaimTable(TxnContext request,DataBus inputData) throws TxnException
	{	   
		SqlStatement stmt = new SqlStatement();
		
		String sys_rd_data_source_id = request.getRecord("select-key").getValue("sys_rd_data_source_id");
		String object_schema = request.getRecord("select-key").getValue("object_schema");
		String unclaim_table_code = request.getRecord("select-key").getValue("unclaim_table_code");
		String unclaim_table_name = request.getRecord("select-key").getValue("unclaim_table_name");
		StringBuffer querySql = new StringBuffer("select d.db_name,a.sys_rd_unclaim_table_id,a.sys_rd_data_source_id,a.unclaim_table_code,a.unclaim_table_name,a.cur_record_count,a.data_object_type, a.tb_pk_columns,a.tb_index_columns,a.object_schema,a.remark from sys_rd_unclaim_table a, sys_rd_data_source d where a.data_object_type = 'T' and a.sys_rd_data_source_id=d.sys_rd_data_source_id ");
		
		if(sys_rd_data_source_id!=null && !"".equals(sys_rd_data_source_id)){
			querySql.append(" and d.sys_rd_data_source_id = '").append(sys_rd_data_source_id).append("'");
		}
		
		if(object_schema!=null && !"".equals(object_schema)){
			querySql.append(" and a.object_schema = '").append(object_schema).append("'");
		}
		
		if(unclaim_table_code!=null && !"".equals(unclaim_table_code)){
			querySql.append(" and a.unclaim_table_code like '%").append(unclaim_table_code).append("%'");
		}
		
		if(unclaim_table_name!=null && !"".equals(unclaim_table_name)){
			querySql.append(" and a.unclaim_table_name like '%").append(unclaim_table_name).append("%'");
		}
		
		querySql.append(" and a.sys_rd_unclaim_table_id not in ( select b.sys_rd_unclaim_table_id from sys_rd_unclaim_table b ,sys_rd_table c where b.sys_rd_data_source_id = c.sys_rd_data_source_id and b.unclaim_table_code = c.table_code and b.object_schema= c.object_schema)");
		querySql.append(" order by a.sys_rd_data_source_id,a.object_schema,a.unclaim_table_code ");
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}
	
	/**
	 * 查询未认领视图列表
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement queryUnclaimViewList(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sb = new StringBuffer();
		String sys_rd_data_source_id = request.getRecord("select-key").getValue("sys_rd_data_source_id");
		String object_schema = request.getRecord("select-key").getValue("object_schema");
		String unclaim_table_code = request.getRecord("select-key").getValue("unclaim_table_code");
		String data_object_type = "V";
		String hql = "select a.sys_rd_unclaim_table_id,b.db_name,a.object_schema,a.unclaim_table_code,a.remark from sys_rd_unclaim_table a ,SYS_RD_DATA_SOURCE b where a.sys_rd_data_source_id=b.SYS_RD_DATA_SOURCE_ID and a.data_object_type='"+data_object_type+"'" ;
		sb.append(hql);
		if (sys_rd_data_source_id != null && !"".equals(sys_rd_data_source_id)) {
			sb.append(" and b.sys_rd_data_source_id ='").append(
					sys_rd_data_source_id.trim()).append("'");
		}
		if (object_schema != null && !"".equals(object_schema)) {
			sb.append(" and a.object_schema ='").append(object_schema.trim()).append("'");
		}
		if (unclaim_table_code != null && !"".equals(unclaim_table_code)) {
			sb.append(" and a.unclaim_table_code like '").append(unclaim_table_code.trim()).append("%").append("'");
		}
		
		sb.append(" and a.sys_rd_unclaim_table_id not in (select c.sys_rd_unclaim_table_id from sys_rd_unclaim_table c ,SYS_RD_CLAIM_VIEW d where c.sys_rd_data_source_id=d.SYS_RD_DATA_SOURCE_ID and c.object_schema=d.object_schema and c.unclaim_table_code = d.view_name and c.data_object_type=d.object_type and c.data_object_type='V') ");
		sb.append(" order by a.sys_rd_data_source_id,a.object_schema,a.unclaim_table_code ");
		stmt.addSqlStmt(sb.toString());
		stmt.setCountStmt("select count(1) from (" + sb.toString() + ")");
		return stmt;
	}
	
	/**
	 * 查询未认领函数列表
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement queryUnclaimFunctionList(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sb = new StringBuffer();
		String sys_rd_data_source_id = request.getRecord("select-key").getValue("sys_rd_data_source_id");
		String object_schema = request.getRecord("select-key").getValue("object_schema");
		String unclaim_table_code = request.getRecord("select-key").getValue("unclaim_table_code");
		String data_object_type = "F";
		String hql = "select a.sys_rd_unclaim_table_id,b.db_name,a.object_schema,a.unclaim_table_code,a.remark from sys_rd_unclaim_table a ,SYS_RD_DATA_SOURCE b where a.sys_rd_data_source_id=b.SYS_RD_DATA_SOURCE_ID and a.data_object_type='"+data_object_type+"'" ;
		sb.append(hql);
		if (sys_rd_data_source_id != null && !"".equals(sys_rd_data_source_id)) {
			sb.append(" and b.sys_rd_data_source_id ='").append(
					sys_rd_data_source_id.trim()).append("'");
		}
		if (object_schema != null && !"".equals(object_schema)) {
			sb.append(" and a.object_schema ='").append(object_schema.trim()).append("'");
		}
		if (unclaim_table_code != null && !"".equals(unclaim_table_code)) {
			sb.append(" and a.unclaim_table_code like '").append(unclaim_table_code.trim()).append("%").append("'");
		}
		
		sb.append(" and a.sys_rd_unclaim_table_id not in (select c.sys_rd_unclaim_table_id from sys_rd_unclaim_table c ,SYS_RD_CLAIM_VIEW d where c.sys_rd_data_source_id=d.SYS_RD_DATA_SOURCE_ID and c.object_schema=d.object_schema and c.unclaim_table_code = d.view_name and c.data_object_type=d.object_type and c.data_object_type='F')");
		sb.append(" order by a.sys_rd_data_source_id,a.object_schema,a.unclaim_table_code ");
		stmt.addSqlStmt(sb.toString());
		stmt.setCountStmt("select count(1) from (" + sb.toString() + ")");
		return stmt;
	}
	
	/**
	 * 查询未认领存储过程列表
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement queryUnclaimProcedureList(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sb = new StringBuffer();
		String sys_rd_data_source_id = request.getRecord("select-key").getValue("sys_rd_data_source_id");
		String object_schema = request.getRecord("select-key").getValue("object_schema");
		String unclaim_table_code = request.getRecord("select-key").getValue("unclaim_table_code");
		String data_object_type = "P";
		String hql = "select a.sys_rd_unclaim_table_id,b.db_name,a.object_schema,a.unclaim_table_code,a.remark from sys_rd_unclaim_table a ,SYS_RD_DATA_SOURCE b where a.sys_rd_data_source_id=b.SYS_RD_DATA_SOURCE_ID and a.data_object_type='"+data_object_type+"'" ;
		sb.append(hql);
		if (sys_rd_data_source_id != null && !"".equals(sys_rd_data_source_id)) {
			sb.append(" and b.sys_rd_data_source_id ='").append(
					sys_rd_data_source_id.trim()).append("'");
		}
		if (object_schema != null && !"".equals(object_schema)) {
			sb.append(" and a.object_schema ='").append(object_schema.trim()).append("'");
		}
		if (unclaim_table_code != null && !"".equals(unclaim_table_code)) {
			sb.append(" and a.unclaim_table_code like '").append(unclaim_table_code.trim()).append("%").append("'");
		}
		
		sb.append(" and a.sys_rd_unclaim_table_id not in (select c.sys_rd_unclaim_table_id from sys_rd_unclaim_table c ,SYS_RD_CLAIM_VIEW d where c.sys_rd_data_source_id=d.SYS_RD_DATA_SOURCE_ID and c.object_schema=d.object_schema and c.unclaim_table_code = d.view_name and c.data_object_type=d.object_type and c.data_object_type='P')");
		sb.append(" order by a.sys_rd_data_source_id,a.object_schema,a.unclaim_table_code ");
		stmt.addSqlStmt(sb.toString());
		stmt.setCountStmt("select count(1) from (" + sb.toString() + ")");
		return stmt;
	}
	
	
	public SqlStatement selectStateOfTable(TxnContext request,DataBus inputData) throws TxnException
	{	   
		SqlStatement stmt = new SqlStatement();
		
		//获取数据源名称
		String sys_rd_data_source_id = request.getRecord("select-key").getValue("sys_rd_data_source_id");
		
		//VoUser user = request.getOperData();
		//String userName = user.getOperName();
		
		//StringBuffer querySql = new StringBuffer("select t2.sys_rd_data_source_id,t3.db_name,t2.total_num,t2.unclaim_num,t2.claim_num from (select t.sys_rd_data_source_id,nvl(total_num,0) total_num, nvl(total_num - claim_num,0)  unclaim_num,nvl(claim_num,0) claim_num   from (select sys_rd_data_source_id,nvl(count(sys_rd_data_source_id),0) total_num from sys_rd_unclaim_table where data_object_type='T' group by sys_rd_data_source_id) t,(select sys_rd_data_source_id,nvl(count(sys_rd_data_source_id),0) claim_num from sys_rd_table group by sys_rd_data_source_id) t1 where t.sys_rd_data_source_id=t1.sys_rd_data_source_id(+)) t2, sys_rd_data_source t3 where t2.sys_rd_data_source_id=t3.sys_rd_data_source_id ");
		StringBuffer querySql = new StringBuffer("select t2.sys_rd_data_source_id,t3.db_name,t2.total_num,t2.unclaim_num,t2.claim_num,t2.data_object_type from");
		querySql.append("( select a.sys_rd_data_source_id,nvl(a.cnt1,0) as total_num,nvl(nvl(a.cnt1,0)-nvl(cnt2,0),0) as unclaim_num,nvl(b.cnt2,0) as claim_num,data_object_type from (select sys_rd_data_source_id, data_object_type,count(*) as cnt1  from sys_rd_unclaim_table where data_object_type in('V','P','F') group by data_object_type,sys_rd_data_source_id) a left join (select sys_rd_data_source_id, object_type, count(*) as cnt2  from sys_rd_claim_view  where object_type in('V','P','F') group by object_type,sys_rd_data_source_id) b on a.sys_rd_data_source_id = b.sys_rd_data_source_id and a.data_object_type=b.object_type ");
		querySql.append("union");
		querySql.append(" select t.sys_rd_data_source_id,nvl(total_num,0) total_num, nvl(total_num - claim_num,0) unclaim_num,nvl(claim_num,0) claim_num, t.data_object_type from (select sys_rd_data_source_id,'T' data_object_type, count(sys_rd_data_source_id) total_num from sys_rd_unclaim_table where data_object_type='T' group by sys_rd_data_source_id) t, (select sys_rd_data_source_id, nvl(count(sys_rd_data_source_id),0) claim_num  from sys_rd_table  group by sys_rd_data_source_id) t1  where t.sys_rd_data_source_id=t1.sys_rd_data_source_id(+) ");
		querySql.append(" )t2,sys_rd_data_source t3 where t2.sys_rd_data_source_id=t3.sys_rd_data_source_id ");
		
		if(sys_rd_data_source_id !=null && !"".equals(sys_rd_data_source_id)){
			querySql.append(" and t3.sys_rd_data_source_id='"+sys_rd_data_source_id+"'");
		}
		querySql.append(" order by t2.sys_rd_data_source_id,t2.data_object_type ");
		//System.out.println("selectStateOfClaimTable:::"+querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}
	
	/**
	 * 根据数据源删除未认领表
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement deleteByDataSource(TxnContext request,DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
		if(sys_rd_data_source_id!=null && !"".equals(sys_rd_data_source_id)){
			stmt.addSqlStmt("delete sys_rd_unclaim_table where sys_rd_data_source_id='" + sys_rd_data_source_id + "'");
		}
		return stmt;
	}
	
	/**
	 * 数据源同步时向未认领表插入信息
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement insertBySync(TxnContext request,DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String sys_rd_unclaim_table_id = inputData.getValue("sys_rd_unclaim_table_id");
		String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
		String unclaim_table_code = inputData.getValue("unclaim_table_code");
		String unclaim_table_name = inputData.getValue("unclaim_table_name");
		String object_schema = inputData.getValue("object_schema");
		String tb_index_name = inputData.getValue("tb_index_name");
		String tb_index_columns = inputData.getValue("tb_index_columns");
		String tb_pk_name = inputData.getValue("tb_pk_name");
		String tb_pk_columns = inputData.getValue("tb_pk_columns");
		String cur_record_count = inputData.getValue("cur_record_count");
		String remark = inputData.getValue("remark");
		String data_object_type = inputData.getValue("data_object_type");
		String timestamp = inputData.getValue("timestamp");
		String object_script = inputData.getValue("object_script");
		if(sys_rd_unclaim_table_id!=null && !"".equals(sys_rd_unclaim_table_id)){
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("insert into sys_rd_unclaim_table (sys_rd_unclaim_table_id,sys_rd_data_source_id," +
					"unclaim_table_code,unclaim_table_name,object_schema,tb_index_name,tb_index_columns," +
					"tb_pk_name,tb_pk_columns,cur_record_count,remark,data_object_type," +
					"timestamp,object_script) values('");
			sqlBuffer.append(sys_rd_unclaim_table_id);
			sqlBuffer.append("','");
			sqlBuffer.append(sys_rd_data_source_id);
			sqlBuffer.append("','");
			sqlBuffer.append(unclaim_table_code);
			sqlBuffer.append("','");
			sqlBuffer.append(unclaim_table_name);
			sqlBuffer.append("','");
			sqlBuffer.append(object_schema);
			sqlBuffer.append("','");
			sqlBuffer.append(tb_index_name);
			sqlBuffer.append("','");
			sqlBuffer.append(tb_index_columns);
			sqlBuffer.append("','");
			sqlBuffer.append(tb_pk_name);
			sqlBuffer.append("','");
			sqlBuffer.append(tb_pk_columns);
			sqlBuffer.append("','");
			sqlBuffer.append(cur_record_count);
			sqlBuffer.append("','");
			sqlBuffer.append(remark);
			sqlBuffer.append("','");
			sqlBuffer.append(data_object_type);
			sqlBuffer.append("','");
			sqlBuffer.append(timestamp);
			sqlBuffer.append("','");
			sqlBuffer.append(object_script);
			sqlBuffer.append("')");
			//System.out.println("insertsql:"+sqlBuffer.toString());
			stmt.addSqlStmt(sqlBuffer.toString());
		}
	
		return stmt;
	}
	
	/**
	    * 更新未认领表数据量字段
	    */
	   public SqlStatement updateRecordCount(TxnContext request,DataBus inputData) throws TxnException
		{	   
			SqlStatement stmt = new SqlStatement();
			DataBus data = request.getRecord("record");
			//获取未认领表ID
			String sys_rd_unclaim_table_id = data.getValue("sys_rd_unclaim_table_id");
			//获取数据源id
			String unclaim_table_code = data.getValue("unclaim_table_code");
		
			StringBuffer insertSql =new StringBuffer("update sys_rd_unclaim_table  set cur_record_count=" +
																			"(select count(*) from " + unclaim_table_code+") " +
																			" where sys_rd_unclaim_table_id='"+sys_rd_unclaim_table_id+"'");
			//System.out.println(">>>>>updateRecordCount::::::"+insertSql.toString());
			stmt.addSqlStmt(insertSql.toString());
			return stmt;
		}
}
