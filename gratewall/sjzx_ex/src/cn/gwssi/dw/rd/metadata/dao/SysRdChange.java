package cn.gwssi.dw.rd.metadata.dao;

import com.gwssi.common.util.CalendarUtil;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class SysRdChange extends BaseTable
{
   public SysRdChange()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction( "queryChangeTable", DaoFunction.SQL_ROWSET, "查询变更记录表" );
	   registerSQLFunction( "updatePrimary", DaoFunction.SQL_UPDATE, "表主键变更" );
	   registerSQLFunction( "updateIndex", DaoFunction.SQL_UPDATE, "表索引变更" );
	   registerSQLFunction( "deleteTable", DaoFunction.SQL_DELETE, "表删除" );
	   registerSQLFunction( "addColumn", DaoFunction.SQL_INSERT, "字段增加" );
	   registerSQLFunction( "changeColumnType", DaoFunction.SQL_UPDATE, "字段类型变更" );
	   registerSQLFunction( "changeColumnLength", DaoFunction.SQL_UPDATE, "字段长度变更" );
	   registerSQLFunction( "deleteColumn", DaoFunction.SQL_UPDATE, "字段删除" );
	   registerSQLFunction("deleteChangeByDataSource",DaoFunction.SQL_DELETE,"未处理变更记录删除");
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
    * 查询表更记录表
    * */
   public SqlStatement queryChangeTable(TxnContext request,DataBus inputData) throws TxnException
	{	   
	   
		SqlStatement stmt = new SqlStatement();
		
		String sys_rd_change_id = request.getRecord("select-key").getValue("sys_rd_change_id");
		String table_name = request.getRecord("select-key").getValue("table_name");
		String column_name = request.getRecord("select-key").getValue("column_name");
		String change_item = request.getRecord("select-key").getValue("change_item");
		String change_result = request.getRecord("select-key").getValue("change_result");
		
		StringBuffer querySql = new StringBuffer("select t.sys_rd_change_id,t.sys_rd_data_source_id,t.db_name,t.db_username,t.table_name," +
														"t.table_name_cn,t.column_name,t.column_name_cn,t.change_item,t.change_before," +
														"t.change_after,t.change_result,t.change_oprater,t.change_time,t.change_reason," +
														"t.timestamp,t1.sys_rd_table_id  from sys_rd_change t, sys_rd_table t1 " +
														"where t.sys_rd_data_source_id = t1.sys_rd_data_source_id"+
														" and t.table_name = t1.table_code"+
														" and t.db_username = t1.object_schema");
		if(sys_rd_change_id!=null && !"".equals(sys_rd_change_id)){
			querySql.append(" and t.sys_rd_change_id = '"+sys_rd_change_id+"'");
		}
		
		if(table_name!=null && !"".equals(table_name)){
			querySql.append(" and t.table_name = '"+table_name+"'");
		}
		
		if(column_name!=null && !"".equals(column_name)){
			querySql.append(" and t.column_name = '"+column_name+"'");
		}
		
		if(change_item!=null && !"".equals(change_item)){
			querySql.append(" and t.change_item = '"+change_item+"'");
		}
		
		if(change_result!=null && !"".equals(change_result)){
			querySql.append(" and t.change_result = '"+change_result+"'");
		}
		
		querySql.append(" order by t.change_result desc");
		
		//System.out.println("queryChangeTable>>>"+querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		
		return stmt;
	}
   
  /**
   * 表主键变更处理函数
   * */
   public SqlStatement updatePrimary(TxnContext request,DataBus inputData) throws TxnException
	{	   
		SqlStatement stmt = new SqlStatement();
		
		DataBus data = request.getRecord("record");
		//获取数据源ID
		String data_source_id = data.getValue("sys_rd_data_source_id");
		//获取用户名
		String object_schema = data.getValue("db_username");
		//获取物理表
		String table_code = data.getValue("table_name");
		//获取变更后的主键字段
		String table_primary_key = data.getValue("change_after"); 
		//获取当前日期和时间
		String claim_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();
		
		
		VoUser user = request.getOperData();
		String userName = user.getOperName();
		
		StringBuffer updateSql = new StringBuffer("update sys_rd_table set ");
		updateSql.append(" table_primary_key = '"+table_primary_key+"'");
		updateSql.append(",claim_operator = '"+userName+"'");
		updateSql.append(",claim_date = '"+claim_date+"'");
		updateSql.append(",timestamp = '"+timestamp+"'");
		updateSql.append(" where SYS_RD_DATA_SOURCE_ID = '"+data_source_id+"'");
		updateSql.append(" and table_code = '"+table_code+"'");
		updateSql.append(" and OBJECT_SCHEMA = '"+object_schema+"'");
		
		//System.out.println("updatePrimary>>>"+updateSql.toString());
		stmt.addSqlStmt(updateSql.toString());
		return stmt;
	}
    
    /**
     * 表索引变更处理函数
     * */
     public SqlStatement updateIndex(TxnContext request,DataBus inputData) throws TxnException
  	{	   
  		SqlStatement stmt = new SqlStatement();
  		
  		DataBus data = request.getRecord("record");
  		//获取数据源ID
  		String data_source_id = data.getValue("sys_rd_data_source_id");
  		//获取用户名
  		String object_schema = data.getValue("db_username");
  		//获取物理表
  		String table_code = data.getValue("table_name");
  		//获取变更后的索引字段
  		String table_index = data.getValue("change_after"); 
  		//获取当前日期和时间
		String claim_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();
  		
  		
  		VoUser user = request.getOperData();
  		String userName = user.getOperName();
  		
  		StringBuffer updateSql = new StringBuffer("update sys_rd_table set ");
  		updateSql.append(" table_index = '"+table_index+"'");
  		updateSql.append(",claim_operator = '"+userName+"'");
  		updateSql.append(",claim_date = '"+claim_date+"'");
		updateSql.append(",timestamp = '"+timestamp+"'");
  		updateSql.append(" where SYS_RD_DATA_SOURCE_ID = '"+data_source_id+"'");
  		updateSql.append(" and table_code = '"+table_code+"'");
  		updateSql.append(" and OBJECT_SCHEMA = '"+object_schema+"'");
  		
  		//System.out.println("updateIndex>>>"+updateSql.toString());
  		stmt.addSqlStmt(updateSql.toString());
  		return stmt;
  	}
     
     /**
      * 删除表处理函数
      * */
      public SqlStatement deleteTable(TxnContext request,DataBus inputData) throws TxnException
   	{	   
   		SqlStatement stmt = new SqlStatement();
   		
   		DataBus data = request.getRecord("record");
   		//获取数据源ID
   		String data_source_id = data.getValue("sys_rd_data_source_id");
   		//获取用户名
   		String object_schema = data.getValue("db_username");
   		//获取物理表
   		String table_code = data.getValue("table_name");
   		
   		StringBuffer deleteSql = new StringBuffer("delete from sys_rd_table  ");
   		deleteSql.append(" where SYS_RD_DATA_SOURCE_ID = '"+data_source_id+"'");
   		deleteSql.append(" and table_code = '"+table_code+"'");
   		deleteSql.append(" and OBJECT_SCHEMA = '"+object_schema+"'");
   		
   		//System.out.println("deleteTable>>>"+deleteSql.toString());
   		stmt.addSqlStmt(deleteSql.toString());
   		return stmt;
   	}
      
      /**
       * 删除表字段函数:变更类型3和7共用
       * */
       public SqlStatement deleteColumn(TxnContext request,DataBus inputData) throws TxnException
    	{	   
    		SqlStatement stmt = new SqlStatement();
    		
    		DataBus data = request.getRecord("record");
    		//获取数据源ID
    		String data_source_id = data.getValue("sys_rd_data_source_id");
    		//获取用户名
    		String object_schema = data.getValue("db_username");
    		//获取物理表
    		String table_code = data.getValue("table_name");
    		//获取字段
    		String column_name = data.getValue("column_name");
    		//获取变更类型
    		String change_item = data.getValue("change_item");
    		
    		StringBuffer deleteSql = new StringBuffer("delete from sys_rd_column  ");
    		deleteSql.append(" where sys_rd_table_id IN ( select sys_rd_table_id from sys_rd_table where SYS_RD_DATA_SOURCE_ID = '"+data_source_id+"'");
    		deleteSql.append(" and table_code = '"+table_code+"'");
    		deleteSql.append(" and OBJECT_SCHEMA = '"+object_schema+"')");
    		//变更类型为字段删除时，需要添加字段的where条件判定
    		if("7".equals(change_item)){
    			deleteSql.append(" and COLUMN_CODE = '"+column_name+"'");
    		}
    		//System.out.println("deleteColumn>>>"+deleteSql.toString());
    		stmt.addSqlStmt(deleteSql.toString());
    		return stmt;
    	}
       
       /**
        * 增加表字段处理函数
        * */
        public SqlStatement addColumn(TxnContext request,DataBus inputData) throws TxnException
     	{	   
     		SqlStatement stmt = new SqlStatement();
     		
     		DataBus data = request.getRecord("record");
     		//获取数据源ID
     		String data_source_id = data.getValue("sys_rd_data_source_id");
     		//获取用户名
     		String object_schema = data.getValue("db_username");
     		//获取物理表
     		String table_code = data.getValue("table_name");
     		//获取字段
     		String column_name = data.getValue("column_name");
     		//获取已认领表id
     		System.out.println("before");
     		String sys_rd_table_id = data.getValue("sys_rd_table_id");
     		System.out.println("addColumn>>>>sys_rd_table_id::"+sys_rd_table_id);
     		//获取当前日期和时间
    		String claim_date = CalendarUtil.getCurrentDate();
    		String timestamp = CalendarUtil.getCurrentDateTime();
     		
     		VoUser user = request.getOperData();
    		String userName = user.getOperName();
     		
     		StringBuffer addSql = new StringBuffer("insert into sys_rd_column(sys_rd_column_id,sys_rd_table_id,sys_rd_data_source_id," +
					"table_code,column_code,column_name,column_type,column_length,is_primary_key," +
					"is_index,is_null,default_value,timestamp,claim_operator,claim_date ) " +
					"select sys_guid(),'"+sys_rd_table_id+"', sys_rd_data_source_id," +
					"unclaim_tab_code,unclaim_column_code,unclaim_column_name,unclaim_column_type,unclaim_column_length,is_primary_key," +
					"is_index,is_null,default_value,'"+timestamp+"','"+userName+"','"+claim_date+"'" +
					"from sys_rd_unclaim_column t where t.sys_rd_unclaim_table_id IN (select sys_rd_unclaim_table_id from sys_rd_unclaim_table where "+
					"SYS_RD_DATA_SOURCE_ID ='"+data_source_id+"'"+
					" and UNCLAIM_TABLE_CODE = '"+table_code+"'"+
					" and object_schema = '"+object_schema+"')"+
					" and t.UNCLAIM_COLUMN_CODE = '"+column_name+"'");
     		
     		
     		//System.out.println("addColumn>>>"+addSql.toString());
     		stmt.addSqlStmt(addSql.toString());
     		return stmt;
     	}
        
        /**
         * 字段类型变更处理函数
         * */
         public SqlStatement changeColumnType(TxnContext request,DataBus inputData) throws TxnException
      	{	   
      		SqlStatement stmt = new SqlStatement();
      		
      		DataBus data = request.getRecord("record");
      		//获取数据源ID
      		String data_source_id = data.getValue("sys_rd_data_source_id");
      		//获取用户名
      		String object_schema = data.getValue("db_username");
      		//获取物理表
      		String table_code = data.getValue("table_name");
      		//获取字段内容
      		String column_name = data.getValue("column_name");
      		//获取变更后的字段类型
      		String column_type = data.getValue("change_after"); 
      		//获取当前日期和时间
    		String claim_date = CalendarUtil.getCurrentDate();
    		String timestamp = CalendarUtil.getCurrentDateTime();
      		
      		
      		VoUser user = request.getOperData();
      		String userName = user.getOperName();
      		
      		StringBuffer updateSql = new StringBuffer("update sys_rd_column set ");
      		updateSql.append(" column_type = '"+column_type+"'");
      		updateSql.append(",claim_operator = '"+userName+"'");
      		updateSql.append(",claim_date = '"+claim_date+"'");
    		updateSql.append(",timestamp = '"+timestamp+"'");
      		updateSql.append(" where sys_rd_table_id IN ( select sys_rd_table_id from sys_rd_table where SYS_RD_DATA_SOURCE_ID = '"+data_source_id+"'");
      		updateSql.append(" and table_code = '"+table_code+"'");
      		updateSql.append(" and OBJECT_SCHEMA = '"+object_schema+"')");
      		updateSql.append(" and COLUMN_CODE = '"+column_name+"'");
      		
      		//System.out.println("changeColumnType>>>"+updateSql.toString());
      		stmt.addSqlStmt(updateSql.toString());
      		return stmt;
      	}
         
         /**
          * 字段长度变更处理函数
          * */
          public SqlStatement changeColumnLength(TxnContext request,DataBus inputData) throws TxnException
       	{	   
       		SqlStatement stmt = new SqlStatement();
       		
       		DataBus data = request.getRecord("record");
       		//获取数据源ID
       		String data_source_id = data.getValue("sys_rd_data_source_id");
       		//获取用户名
       		String object_schema = data.getValue("db_username");
       		//获取物理表
       		String table_code = data.getValue("table_name");
       		//获取字段内容
       		String column_name = data.getValue("column_name");
       		//获取变更后的字段长度
       		String column_length = data.getValue("change_after"); 
       		//获取当前日期和时间
    		String claim_date = CalendarUtil.getCurrentDate();
    		String timestamp = CalendarUtil.getCurrentDateTime();
       		
       		
       		VoUser user = request.getOperData();
       		String userName = user.getOperName();
       		
       		StringBuffer updateSql = new StringBuffer("update sys_rd_column set ");
       		updateSql.append(" column_length = '"+column_length+"'");
       		updateSql.append(",claim_operator = '"+userName+"'");
       		updateSql.append(",claim_date = '"+claim_date+"'");
    		updateSql.append(",timestamp = '"+timestamp+"'");
       		updateSql.append(" where sys_rd_table_id IN ( select sys_rd_table_id from sys_rd_table where SYS_RD_DATA_SOURCE_ID = '"+data_source_id+"'");
       		updateSql.append(" and table_code = '"+table_code+"'");
       		updateSql.append(" and OBJECT_SCHEMA = '"+object_schema+"')");
       		updateSql.append(" and COLUMN_CODE = '"+column_name+"'");
       		
       		//System.out.println("changeColumnLength>>>"+updateSql.toString());
       		stmt.addSqlStmt(updateSql.toString());
       		return stmt;
       	}
        /**
         * 根据数据源删除未处理变更记录
         * @param request
         * @param inputData
         * @return
         * @throws TxnException
         */
        public SqlStatement deleteChangeByDataSource(TxnContext request,DataBus inputData) throws TxnException
        {
        	SqlStatement stmt = new SqlStatement();
        	String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
        	String db_name = inputData.getValue("db_name");
        	String db_username = inputData.getValue("object_schema");
        	String sql = "delete from sys_rd_change where sys_rd_data_source_id='" +sys_rd_data_source_id+ "' and db_name='" +db_name+ "' and db_username='" +db_username+"' and change_result='2'";
        	
        	stmt.addSqlStmt(sql);
        	return stmt;
        }

}
