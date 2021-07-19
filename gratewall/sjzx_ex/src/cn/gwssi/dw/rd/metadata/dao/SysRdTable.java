package cn.gwssi.dw.rd.metadata.dao;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.common.util.CalendarUtil;

public class SysRdTable extends BaseTable
{
   public SysRdTable()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction( "insertColumnTable", DaoFunction.SQL_INSERT, "插入已认领字段" );
	   registerSQLFunction( "updateClaimTable", DaoFunction.SQL_UPDATE, "更新已认领表" );
	   registerSQLFunction( "insertClaimTable", DaoFunction.SQL_INSERT, "插入已认领表" );
	   registerSQLFunction( "querySysRdTableRelationShip", DaoFunction.SQL_ROWSET, "查询表关联关系" );
	   registerSQLFunction( "deleteTable", DaoFunction.SQL_DELETE, "删除已认领表" );
	   registerSQLFunction( "deleteColumnTable", DaoFunction.SQL_DELETE, "删除已认领字段" );
	   registerSQLFunction( "selectStateOfClaimTable", DaoFunction.SQL_ROWSET, "统计已认领表数量" );
	   registerSQLFunction( "querySysRdTable", DaoFunction.SQL_ROWSET, "查询已认领表" );
	   registerSQLFunction("selectTableNo", DaoFunction.SQL_ROWSET, "查询TableNo" );
	   registerSQLFunction( "querySysRdTablePreview", DaoFunction.SQL_ROWSET, "查询已认领表用于概览" );
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
   
   public SqlStatement selectTableNo(TxnContext request,DataBus inputData) throws TxnException{
	   SqlStatement stmt = new SqlStatement();
	   String sysId = request.getRecord("record").getValue("sys_name");
	   String sqlstr = "select max(table_no) mc from sys_rd_table t where t.sys_rd_system_id='"+sysId+"'";
	   stmt.addSqlStmt(sqlstr);
	   return stmt;
   }
   
   /**
    * 插入已认领表表处理函数
    */
   public SqlStatement insertClaimTable(TxnContext request,DataBus inputData) throws TxnException
	{	   
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("record");
		DataBus data1 = request.getRecord("record1");
		
		//获取认领表ID
		String sys_rd_table_id = data.getValue("sys_rd_table_id");
		//获取数据源id
		String sys_rd_data_source_id = data.getValue("sys_rd_data_source_id");
		//获取物理表
		String table_code = data.getValue("table_code");
		//获取物理表中文名
		String table_name = data.getValue("table_name");
		//获取父表名
		String parent_table = data.getValue("parent_table");
		//获取父表主键名
		String parent_pk = data.getValue("parent_pk");
		//获取外键名
		String table_fk = data.getValue("table_fk");
		//获取数据量
		String first_record_count = data.getValue("first_record_count");
		//获取表类型
		String table_type = data.getValue("table_type");
		//获取表主键
		String table_primary_key = data.getValue("table_primary_key");
		//获取表索引
		String table_index = data.getValue("table_index");
		//获取用途
		String table_use = data.getValue("table_use");
		//获取备注
		String memo = data.getValue("memo");
		//获取表模式
		String object_schema = data.getValue("object_schema");
		//获取业务主题ID
		String sys_rd_system_id = data.getValue("sys_name");
		//获取Table_no
		String  table_no = "";
		if(StringUtils.isBlank(data1.getValue("mc"))){
			table_no = sys_rd_system_id+"T0001";
		}else{
			table_no = getTableNo(sys_rd_system_id, data1.getValue("mc"));
		}
		//是否可查询
		String is_query = "0";
		//是否可共享
		String is_trans = "0";
		//是否可下载
		String is_download = "0";
		
		//是否可查询
		//String is_query = data.getValue("is_query").replaceAll(",", "\\,");
		//System.out.println("is_query::::::"+is_query);
		//获取权限
		String authority = data.getValue("authority");
		
		if(authority!=null && !authority.equals("")){
			if(authority.indexOf("0")!=-1){
				is_query="1";
			}
			if(authority.indexOf("1")!=-1){
				is_trans="1";
			}
			if(authority.indexOf("2")!=-1){
				is_download="1";
			}
		}
		//如果没有中文表名，则不给查询、共享和下载权限
		if("".equals(table_name) || table_name.length()==0){
			is_query = "0";
			is_download = "0";
			is_trans = "0";
		}
		
		//获取当前日期和时间
		String claim_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();
		
		VoUser user = request.getOperData();
		String userName = user.getOperName();
		StringBuffer insertSql = new StringBuffer(); 
		
		if(sys_rd_system_id!=null && !"".equals(sys_rd_system_id)){
			insertSql.append("insert into sys_rd_table(sys_rd_table_id, sys_rd_system_id, sys_no,sys_name," +
					  								  "sys_rd_data_source_id, table_code, table_name, parent_table," +
					  								  "parent_pk,table_fk,first_record_count,table_type," +
					  								  "table_primary_key,table_index,table_use,claim_operator," +
					  								  "claim_date,object_schema,memo,is_query,is_trans,is_download,timestamp,table_no) " +
					  								  "select '"+sys_rd_table_id+"',sys_rd_system_id,sys_no,sys_name," +
					  								  "'"+sys_rd_data_source_id+"','"+table_code+"','"+table_name+"','"+parent_table+"'," +
					  								  "'"+parent_pk+"','"+table_fk+"','"+first_record_count+"','"+table_type+"'," +
					  								  "'"+table_primary_key+"','"+table_index+"','"+table_use+"','"+userName+"'," +
					  								  "'"+claim_date+"','"+object_schema+"','"+memo+"','"+is_query+"','"+is_trans+"','"+is_download+"','"+timestamp+"','"+table_no+"' "+
					  								  " from sys_rd_system where sys_rd_system_id = '"+sys_rd_system_id+"'");
		}else{
			insertSql.append("insert into sys_rd_table(sys_rd_table_id, " +
					  								  "sys_rd_data_source_id, table_code, table_name, parent_table," +
					  								  "parent_pk,table_fk,first_record_count,table_type," +
					  								  "table_primary_key,table_index,table_use,claim_operator," +
					  								  "claim_date,object_schema,memo,is_query,is_trans,is_download,timestamp,table_no) " +
					  								  "values( '"+sys_rd_table_id+"'," +
					  								  "'"+sys_rd_data_source_id+"','"+table_code+"','"+table_name+"','"+parent_table+"'," +
					  								  "'"+parent_pk+"','"+table_fk+"','"+first_record_count+"','"+table_type+"'," +
					  								  "'"+table_primary_key+"','"+table_index+"','"+table_use+"','"+userName+"'," +
					  								  "'"+claim_date+"','"+object_schema+"','"+memo+"','"+is_query+"','"+is_trans+"','"+is_download+"','"+timestamp+"','"+table_no+"' "+
					  								  " )");
		}
	
		
		//System.out.println("insertClaimTable::::::\n"+insertSql.toString());
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}
   
   /**
    * 插入已认领表字段表处理函数
    */
   public SqlStatement insertColumnTable(TxnContext request,DataBus inputData) throws TxnException
	{	   
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("record");
		//获取未认领表ID
		String sys_rd_unclaim_table_id = data.getValue("sys_rd_unclaim_table_id");
		//获取认领表ID
		String sys_rd_table_id = data.getValue("sys_rd_table_id");
		//获取当前日期和时间
		String claim_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();
		String table_no = request.getRecord("record1").getValue("mc");
		if(StringUtils.isBlank(table_no)){
			table_no = data.getValue("sys_rd_system_id")+"T0001";
		}else{
			table_no = getTableNo(data.getValue("sys_rd_system_id"), table_no);
		}
		VoUser user = request.getOperData();
		String userName = user.getOperName();
		StringBuffer insertSql = new StringBuffer("insert into sys_rd_column(sys_rd_column_id,sys_rd_table_id,sys_rd_data_source_id," +
																			"table_code,column_code,column_name,column_type,column_length,is_primary_key," +
																			"is_index,is_null,default_value,timestamp,claim_operator,claim_date,use_type,table_no,column_no,sys_column_type) " +
																			"select sys_guid(),'"+sys_rd_table_id+"', sys_rd_data_source_id," +
																			"unclaim_tab_code,unclaim_column_code,unclaim_column_name,unclaim_column_type,unclaim_column_length,is_primary_key," +
																			"is_index,is_null,default_value,'"+timestamp+"','"+userName+"','"+claim_date+"',0,'"+table_no+"','"+table_no+"'||'C'||rownum," +
																			"case when (unclaim_column_type = 'N' or unclaim_column_type = 'n') then '3' "+
																			"when unclaim_column_type = 'DT' then '2' else '1' end "+
																			"from sys_rd_unclaim_column t where t.sys_rd_unclaim_table_id = '"+
																			sys_rd_unclaim_table_id+"'");
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}
   
   /**
    * 更新已认领表表处理函数
    */
   public SqlStatement updateClaimTable(TxnContext request,DataBus inputData) throws TxnException
	{	   
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("record");
		//获取认领表ID
		String sys_rd_table_id = data.getValue("sys_rd_table_id");
		//获取数据源id
		String sys_rd_data_source_id = data.getValue("sys_rd_data_source_id");
		//获取物理表
		String table_code = data.getValue("table_code");
		//获取业务主题ID
		String sys_rd_system_id = data.getValue("sys_rd_system_id");
		//获取数据量
		String first_record_count = data.getValue("first_record_count");
		//获取表主键
		String table_primary_key = data.getValue("table_primary_key");
		//获取表类型
		String table_type = data.getValue("table_type");
		//获取物理表中文名
		String table_name = data.getValue("table_name");
		//获取父表名
		String parent_table = data.getValue("parent_table");
		//获取父表主键名
		String parent_pk = data.getValue("parent_pk");
		//获取外键名
		String table_fk = data.getValue("table_fk");
		
		//获取用途
		String table_use = data.getValue("table_use");
		//获取备注
		String memo = data.getValue("memo");
		
		//是否可查询
		String is_query = "0";
		//是否可共享
		String is_trans = "0";
		//是否可下载
		String is_download = "0";
		
		//是否可查询
		//String is_query = data.getValue("is_query").replaceAll(",", "\\,");
		//System.out.println("is_query::::::"+is_query);
		//获取权限
		String authority = data.getValue("authority");
		
		
		if(authority!=null && !authority.equals("")){
			if(authority.indexOf("0")!=-1){
				is_query="1";
			}
			if(authority.indexOf("1")!=-1){
				is_trans="1";
			}
			if(authority.indexOf("2")!=-1){
				is_download="1";
			}
		}
		
		//是否可查询
		//String is_query = data.getValue("is_query").replaceAll(",", "\\,");
		//System.out.println("是否可查询：："+is_query);
		
		//获取当前日期和时间
		String claim_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();
		
		VoUser user = request.getOperData();
		String userName = user.getOperName();
	
		StringBuffer insertSql =new StringBuffer("update sys_rd_table t set (sys_rd_system_id,sys_no,sys_name)=" +
																		"(select sys_rd_system_id,sys_no,sys_name from sys_rd_system t1 " +
																		" where  t1.sys_rd_system_id='"+sys_rd_system_id+"' )," +
																		" table_code='"+table_code+"'," +
																		" first_record_count='"+first_record_count+"'," +
																		" table_primary_key='"+table_primary_key+"'," +
																		" table_type='"+table_type+"'," +
																		" table_name='"+table_name+"'," +
																		" parent_table='"+parent_table+"'," +
																		" parent_pk='"+parent_pk+"'," +
																		" table_fk='"+table_fk+"'," +
																		" table_use='"+table_use+"'," +
																		" memo='"+memo+"'," +
																		" claim_date='"+claim_date+"'," +
																		" timestamp='"+timestamp+"'," +
																		" is_query='"+is_query+"'," +
																		" is_trans='"+is_trans+"'," +
																		" is_download='"+is_download+"'," +
																		" claim_operator='"+userName+"'" +
																		"  where t.sys_rd_table_id='"+sys_rd_table_id+"'");
		//System.out.println("updateColumnTable:::"+insertSql.toString());
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}
	
    public SqlStatement querySysRdTableRelationShip(TxnContext request,DataBus inputData) throws TxnException
	{	   
		SqlStatement stmt = new SqlStatement();
		String sql = "";
		String sqlCount = "";
        
		StringBuffer stringBfSql = new StringBuffer("select ss.db_name,st.table_code,st.table_primary_key,st.table_fk,st.parent_table,st.parent_pk from sys_rd_table st left join sys_rd_data_source ss on st.sys_rd_data_source_id=ss.sys_rd_data_source_id where 1=1 ");
		StringBuffer stringBfCount = new StringBuffer("select count(*) from sys_rd_table st left join sys_rd_data_source ss on st.sys_rd_data_source_id=ss.sys_rd_data_source_id where 1=1 ");
		
		DataBus dataBus = request.getRecord("select-key");	
		String db_name = dataBus.getValue("db_name");
		String table_code = dataBus.getValue("table_code");
		String table_primary_key = dataBus.getValue("table_primary_key");
		String table_fk = dataBus.getValue("table_fk");
		String parent_table = dataBus.getValue("parent_table");
		String parent_pk = dataBus.getValue("parent_pk");
		
		StringBuffer buffSql=new StringBuffer();
		
        if(db_name!=null&&!db_name.equals("")){
        	buffSql.append(" and ss.db_name like '%"+db_name+"%' ");
        }
        if(table_code!=null&&!table_code.equals("")){
        	buffSql.append("and st.table_code like '%"+table_code+"%' ");
        }
		if(table_primary_key!=null&&!table_primary_key.equals("")){
			buffSql.append("and st.table_primary_key like '%"+table_primary_key+"%' ");
		}
		if(table_fk!=null&&!table_fk.equals("")){
			buffSql.append("and st.table_fk like '%"+table_fk+"%' ");
		}
		if(parent_table!=null&&!parent_table.equals("")){
			buffSql.append("and st.parent_table like '%"+parent_table+"%' ");
		}
		if(parent_pk!=null&&!parent_pk.equals("")){
			buffSql.append("and st.parent_pk like '%"+parent_pk+"%' ");
		}
		
		stringBfSql.append(buffSql);
		stringBfCount.append(buffSql);
		sql = stringBfSql.toString();
		sqlCount = stringBfCount.toString();
		stmt.addSqlStmt(sql);	
		stmt.setCountStmt(sqlCount);	
		return stmt;
	}
    
    /**
     * 查询已认领表
     */
   public SqlStatement querySysRdTable(TxnContext request,DataBus inputData) throws TxnException
	{	   
		SqlStatement stmt = new SqlStatement();
		
		//获取数据源名称
		String sys_rd_data_source_id = request.getRecord("select-key").getValue("sys_rd_data_source_id");
		String sys_rd_system_id = request.getRecord("select-key").getValue("sys_rd_system_id");
		String table_code = request.getRecord("select-key").getValue("table_code");
		String table_name = request.getRecord("select-key").getValue("table_name");
		String column_code = request.getRecord("select-key").getValue("column_code");
		String column_name = request.getRecord("select-key").getValue("column_name");
		String table_type = request.getRecord("select-key").getValue("table_type");
		
		//VoUser user = request.getOperData();
		//String userName = user.getOperName();
		
		StringBuffer querySql = new StringBuffer("select sys_rd_table_id,sys_rd_system_id,sys_no,sys_name,sys_rd_data_source_id,table_code,table_name,table_no,table_sql,table_sort,table_dist,table_time,parent_table,parent_pk,table_fk,first_record_count,last_record_count,table_type,table_primary_key,table_index,table_use,gen_code_column,prov_code_column,city_code_column,content,claim_operator,claim_date,changed_status,object_schema,memo,is_query,is_trans,is_download,sort,timestamp from sys_rd_table ");
		
		if((column_code !=null && !"".equals(column_code))||(column_name !=null && !"".equals(column_name))){
			querySql.append(" where sys_rd_table_id in (select t1.sys_rd_table_id from sys_Rd_table t1,sys_Rd_column t2 where t1.sys_rd_table_id = t2.sys_rd_table_id ");
			if(sys_rd_data_source_id !=null && !"".equals(sys_rd_data_source_id)){
				querySql.append(" and  t1.sys_rd_data_source_id ='"+sys_rd_data_source_id+"'");
			}
			if(sys_rd_system_id !=null && !"".equals(sys_rd_system_id)){
				querySql.append(" and  t1.sys_rd_system_id ='"+sys_rd_system_id+"'");
			}
			if(table_code !=null && !"".equals(table_code)){
				querySql.append(" and t1.table_code like '%"+table_code+"%'");
			}
			if(table_name !=null && !"".equals(table_name)){
				querySql.append(" and t1.table_name like '%"+table_name+"%'");
			}
			if(column_code !=null && !"".equals(column_code)){
				querySql.append(" and t2.column_code like '%"+column_code+"%'");
			}
			if(column_name !=null && !"".equals(column_name)){
				querySql.append(" and t2.column_name like '%"+column_name+"%'");
			}
			if(table_type !=null && !"".equals(table_type)){
				querySql.append(" and  t1.table_type ='"+table_type+"'");
			}	
			querySql.append(" ) ");
		}else{//字段代码和字段名称都为空
			querySql.append(" where 1=1 ");
			if(sys_rd_data_source_id !=null && !"".equals(sys_rd_data_source_id)){
				querySql.append(" and  sys_rd_data_source_id ='"+sys_rd_data_source_id+"'");
			}
			if(sys_rd_system_id !=null && !"".equals(sys_rd_system_id)){
				querySql.append(" and  sys_rd_system_id ='"+sys_rd_system_id+"'");
			}
			if(table_code !=null && !"".equals(table_code)){
				querySql.append(" and table_code like '%"+table_code+"%'");
			}
			if(table_name !=null && !"".equals(table_name)){
				querySql.append(" and table_name like '%"+table_name+"%'");
			}
			if(table_type !=null && !"".equals(table_type)){
				querySql.append(" and  table_type ='"+table_type+"'");
			}	
		}
		
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		System.out.println(querySql.toString());
		return stmt;
	}
   
   public SqlStatement querySysRdTablePreview(TxnContext request,DataBus inputData) throws TxnException
   {
	   	SqlStatement stmt = new SqlStatement();
		
		StringBuffer querySql = new StringBuffer("select sys_rd_table_id,sys_rd_system_id,sys_no,sys_name,sys_rd_data_source_id,table_code,table_name,table_no,table_sql,table_sort,table_dist,table_time,parent_table,parent_pk,table_fk,first_record_count,last_record_count,table_type,table_primary_key,table_index,table_use,gen_code_column,prov_code_column,city_code_column,content,claim_operator,claim_date,changed_status,object_schema,memo,is_query,is_trans,is_download,sort,timestamp from sys_rd_table ");
			String sys_system_ids = request.getRecord("select-key").getValue("sys_system_ids");
			String sys_rd_system_id = request.getRecord("select-key").getValue("sys_rd_system_id");
			String table_code = request.getRecord("select-key").getValue("table_code");
			String table_name = request.getRecord("select-key").getValue("table_name");
			String table_type = request.getRecord("select-key").getValue("table_type");
			querySql.append(" where table_name is not null ");
			if(sys_system_ids!=null && sys_system_ids.indexOf(",")>0){
				String[] ids = sys_system_ids.split(",");
				sys_system_ids = "";
				for(int i=0;i<ids.length;i++){
					sys_system_ids += "'" + ids[i] + "',";
				}
				sys_system_ids = sys_system_ids.substring(0, sys_system_ids.length()-1);
				sys_system_ids = "( "+sys_system_ids+" )";
				querySql.append(" and sys_rd_system_id in "+sys_system_ids);
			}
			if(sys_rd_system_id !=null && StringUtils.isNotBlank(sys_rd_system_id)){
				querySql.append(" and  sys_rd_system_id ='"+sys_rd_system_id+"'");
			}
			if(table_code !=null && StringUtils.isNotBlank(table_code)){
				querySql.append(" and table_code like '%"+table_code+"%'");
			}
			if(table_name !=null && StringUtils.isNotBlank(table_name)){
				querySql.append(" and table_name like '%"+table_name+"%'");
			}
			if(table_type !=null && StringUtils.isNotBlank(table_type)){
				querySql.append(" and  table_type ='"+table_type+"'");
			}	
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		System.out.println(querySql.toString());
		
		return stmt;
	}
   
    /**
     * 查询已认领表的数量
     */
   public SqlStatement selectStateOfClaimTable(TxnContext request,DataBus inputData) throws TxnException
	{	   
		SqlStatement stmt = new SqlStatement();
		
		//获取数据源名称
		String db_name = request.getRecord("select-key").getValue("db_name");
		
		//VoUser user = request.getOperData();
		//String userName = user.getOperName();
		
		StringBuffer querySql = new StringBuffer("select t.sys_rd_data_source_id,t1.db_name,t.claim_num from (select sys_rd_data_source_id,nvl(count(sys_rd_data_source_id),0) claim_num from sys_rd_table group by sys_rd_data_source_id) t, sys_rd_data_source t1 where t.sys_rd_data_source_id=t1.sys_rd_data_source_id ");
		if(db_name !=null && !"".equals(db_name)){
			querySql.append(" and t1.db_name like '%"+db_name+"%'");
		}
		
		
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}
   
   /**
    * 删除已认领表
    */
   public SqlStatement deleteTable(TxnContext request,DataBus inputData) throws TxnException
	{	   
		SqlStatement stmt = new SqlStatement();
		
		//获取已认领表id
		String sys_rd_table_id = request.getRecord("primary-key").getValue("sys_rd_table_id");
		
		//VoUser user = request.getOperData();
		//String userName = user.getOperName();
		
		StringBuffer deleteSql = new StringBuffer("delete from sys_rd_table ");
		if(sys_rd_table_id !=null && !"".equals(sys_rd_table_id)){
			deleteSql.append(" where sys_rd_table_id='"+sys_rd_table_id+"'");
		}
		
		
		stmt.addSqlStmt(deleteSql.toString());
		return stmt;
	}
   
   private String  getTableNo(String sysname,String maxNo){
		String temp = maxNo.replaceFirst(sysname, "");
		String num="";
		String head="";
		for (int i = 0; i < temp.length(); i++) {
			String tempi=String.valueOf(temp.charAt(i));
			if (StringUtils.isNumeric(tempi)) {
				num+=tempi;
			}else{
				head+=tempi;
			}
		}
		String table_no=String.valueOf(Integer.valueOf(num).intValue()+1);
		table_no=sysname+head+num.substring(0, num.length()-table_no.length())+table_no;
		return table_no;
	}
   
   /**
    * 删除已认领表字段表
    */
   public SqlStatement deleteColumnTable(TxnContext request,DataBus inputData) throws TxnException
	{	   
		SqlStatement stmt = new SqlStatement();
		
		//获取已认领表id
		String sys_rd_table_id = request.getRecord("primary-key").getValue("sys_rd_table_id");
		
		//VoUser user = request.getOperData();
		//String userName = user.getOperName();
		
		StringBuffer deleteSql = new StringBuffer("delete from sys_rd_column");
		if(sys_rd_table_id !=null && !"".equals(sys_rd_table_id)){
			deleteSql.append(" where sys_rd_table_id='"+sys_rd_table_id+"'");
		}
		
		stmt.addSqlStmt(deleteSql.toString());
		return stmt;
	}
	
}
