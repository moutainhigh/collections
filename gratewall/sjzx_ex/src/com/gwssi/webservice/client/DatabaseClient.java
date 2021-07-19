package com.gwssi.webservice.client;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.logger.TxnLogger;


import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.testconnection.ConnAccess;
import com.gwssi.common.testconnection.ConnDatabaseUtil;
import com.gwssi.common.testconnection.DatabaseResource;
import com.gwssi.common.util.AnalyCollectFile;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

public class DatabaseClient
{
	public DatabaseClient()
	{
	}

	protected static Logger	logger		= TxnLogger.getLogger(FtpClient.class
												.getName());	// 日志

	TaskInfo				taskInfo	= new TaskInfo();		// 任务信息

	/**
	 * 
	 * doCollectTaskFtp 执行 FTP 采集任务
	 * 
	 * @param taskId
	 * @throws DBException
	 * @throws IOException
	 * @throws TxnDataException 
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void doCollectTaskDatabase(String collect_task_id) throws DBException, IOException, TxnDataException
	{
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select a.*,b.collect_task_id,b.service_targets_id,b.task_name,b.collect_type,b.creator_id,b.last_modify_id,b.log_file_path,c.service_targets_name from ");
		sql.append("res_data_source a,collect_task b,res_service_targets c ");
		sql.append("where a.data_source_id = b.data_source_id and b.service_targets_id = c. service_targets_id and b.collect_task_id = '"+collect_task_id+"'");
	    System.out.println("sql==="+sql);
	    List databaseList=null;
	    Map tablepMap=null;
	    HashMap taskMap=null;
	    String URL = "";//数据库URL
	    String db_user = "";//数据库用户名
	    String db_password = "";//数据库用户密码
	    
	    String user="";
	    String ip = "";
	    ServiceDAO	daoTable	= new ServiceDAOImpl(); // 操作数据表Dao
		
		taskMap = (HashMap)daoTable.queryService(sql.toString());
		String db_type = taskMap.get("DB_TYPE").toString();
		if(taskMap.get("LAST_MODIFY_ID")!=null&&!"".equals(taskMap.get("LAST_MODIFY_ID"))){
			user=taskMap.get("LAST_MODIFY_ID").toString();
		}else{
			user=taskMap.get("CREATOR_ID").toString();
		}
		db_user = taskMap.get("DB_USERNAME").toString();
		db_password = taskMap.get("DB_PASSWORD").toString();
		ip = taskMap.get("DATA_SOURCE_IP").toString();
//getUrl(String db_type,String data_source_ip,String access_port,String db_instance,String db_username,String db_password){			
		String[] connRes = getUrl(db_type,ip,taskMap.get("ACCESS_PORT").toString(),taskMap.get("DB_INSTANCE").toString(),db_user,db_password);
		URL = connRes[0];
		if(connRes[1].equals("0")){
			throw new TxnDataException("error", "连接数据库数据源失败!");
		}
		//得到查询的数据库数据项表信息
		databaseList = getDataBaseList(collect_task_id);
		
		
		Map map= new HashMap();
		String sourceTablename = null;
		String sourceColumn = null;
		String collect_table = null;
		String collect_mode=null;
		String database_task_id = null;
		
		
		String cj_zt=null;
		String logFile=(String) taskMap.get("LOG_FILE_PATH");
		String tableName=null;
		String tableId=null;
		
		String startTime = CalendarUtil.getCurrentDateTime();
		if(logFile==null||"".equals(logFile)){
			logFile=ExConstant.FILE_DATABASE+File.separator+startTime.replace("-", "").replace(":", "").replace(" ", "")+"_CollectFileImportResult.txt";
		}
		
		
		if(databaseList!=null&&databaseList.size()>0){
			
			for(int i=0;i<databaseList.size();i++){
				map= (Map)databaseList.get(i);
				sourceTablename=(String) map.get("SOURCE_COLLECT_TABLE");
				sourceColumn = (String) map.get("SOURCE_COLLECT_COLUMN");
				collect_mode = (String)map.get("COLLECT_MODE");
				collect_table = (String)map.get("COLLECT_TABLE");
				database_task_id = (String)map.get("DATABASE_TASK_ID");
				//HashMap sourceColumnS = getSourColumns(db_type,URL,db_user,db_password,sourceTablename) ;//存放对方数据库中表的字段信息
				ConnDatabaseUtil cdu = new ConnDatabaseUtil();
				HashMap<Integer,String> sourceColumnS = cdu.getTableColumnName(db_type,URL,db_user,db_password,sourceTablename) ;//存放对方数据库中表的字段信息
				HashMap<String, String> sourceColumntType = cdu.getColumnNameAndType(db_type, URL, db_user, db_password, sourceTablename);
				HashMap<Integer,String> resultMap=new HashMap<Integer,String>();//目标数据库中表的字段信息
				
				if(sourceTablename!=null&&!"".equals(sourceTablename)){
					tablepMap= new HashMap();
					
					AnalyCollectFile file = new AnalyCollectFile();
					sql = new StringBuffer();
					sql.append("select a.*,b.table_name_en from ");
					sql.append("collect_database_task a,res_collect_table b ");
					sql.append("where a.collect_table = b.collect_table_id and a.database_task_id = '"+database_task_id+"'  and a.collect_task_id = '"+collect_task_id+"'");
					System.out.println("查询数据库文件表sql=="+sql);
					tablepMap = daoTable.queryService(sql.toString());
					tableName=(String)tablepMap.get("TABLE_NAME_EN");//采集表
					tableId=(String)tablepMap.get("COLLECT_TABLE");//采集表ID
					System.out.println("tableId=="+tableId);
					System.out.println("tableName=="+tableName);
					collect_mode=(String)tablepMap.get("COLLECT_MODE");//采集方式
					taskMap.put("TASK_ID", tablepMap.get("DATABASE_TASK_ID"));//任务ID
					taskMap.put("SERVICE_NO", "");//任务编号
					taskMap.put("COLLECT_TABLE", tableId);//采集表ID
					taskMap.put("COLLECT_TABLE_NAME", tableName);//采集表名称
					taskMap.put("COLLECT_MODE", collect_mode);//采集方式
					
					
					sql = new StringBuffer();
					sql.append("select a.* from ");
					sql.append("res_collect_dataitem a ");
					sql.append("where a.collect_table_id = '"+ tableId+"'");
					
					List list = daoTable.query(sql.toString());
					Map tableMap= new HashMap();
					
					StringBuffer colTable_ColumnInfo = new StringBuffer();
					if(list!=null&&list.size()>0){
						for(int j=0;j<list.size();j++){
							tableMap=(Map)list.get(j);
							resultMap.put(j, tableMap.get("DATAITEM_NAME_EN").toString().toUpperCase());
							String CNName = "";
							if(tableMap.get("DATAITEM_NAME_CN")!=null){
								CNName = tableMap.get("DATAITEM_NAME_CN").toString();
							}
							
							if(j!=list.size()){
								colTable_ColumnInfo.append(tableMap.get("DATAITEM_NAME_EN").toString()+"("+CNName+"),");
							}else{
								colTable_ColumnInfo.append(tableMap.get("DATAITEM_NAME_EN").toString()+"("+CNName+")");
							}
							}
					}
					
					String[] arrSqlDatas = 	insertDatas(db_type,sourceColumntType,sourceColumnS,resultMap,sourceTablename,tableName,collect_mode,sourceColumn,URL,db_user,db_password);
					
					if(cj_zt!=null&&cj_zt.equals(CollectConstants.COLLECT_STATUS_FAIL)){
						file.collectDatabase(db_type,sourceColumntType,taskMap,arrSqlDatas,tableName,resultMap,collect_mode,sourceColumnS,user,logFile,colTable_ColumnInfo);
					}else{
					    cj_zt=file.collectDatabase(db_type,sourceColumntType,taskMap,arrSqlDatas,tableName,resultMap,collect_mode,sourceColumnS,user,logFile,colTable_ColumnInfo);
					}
					
				}
			}
			
			// 更新采集状态
			Connection conn=null;
			Statement st = null; 
			String sql2 = "update collect_task set collect_status = '" + cj_zt
					+ "',log_file_path = '" + logFile
					+ "' where collect_task_id = '" + collect_task_id + "'";
			System.out.println("更新采集任务表采集状态及日志文件路径sql=====" + sql2);
			try{
				conn = DbUtils.getConnection("2");  //实例化con 5对应的是DataSource5这个数据源
				st = conn.createStatement();
				st.executeUpdate(sql2);//执行sql
			}catch (Exception e) {
				throw new TxnDataException("error", "更新数据库文件表文件路径失败!");
			}
			finally {
				try {
					if (null != st){
						st.close();
					}
					DbUtils.freeConnection(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
	}
		
	}
	
	
	/**
	 * 
	 * getUrl(获取数据库的url和连接情况)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param db_type
	 * @param data_source_ip
	 * @param access_port
	 * @param db_instance
	 * @param db_username
	 * @param db_password
	 * @return        
	 * String[]  String[0]:存放数据库的url;String[1]:存放连接结果，1表示连接成功；0表示连接失败；     
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public String[] getUrl(String db_type,String data_source_ip,String access_port,String db_instance,String db_username,String db_password){
		String url="";
		String result="";
		
		if(db_type!=null&&!db_type.equals("")){
			if("00".equals(db_type))//oracle
			{
				url = "jdbc:oracle:thin:@"+data_source_ip+":"+access_port+":"+db_instance;
				ConnDatabaseUtil db = new ConnDatabaseUtil();
				boolean rs = db.testOracleConn(url, db_username, db_password);
				
				if(rs){
					result = "1";
				}else
					result = "0";
				
			}else if("01".equals(db_type))//db2
			{
				//jdbc:db2://172.30.7.189:50000/newxcdb
				url = "jdbc:db2://"+data_source_ip+":"+access_port+"/"+db_instance;
				ConnDatabaseUtil db = new ConnDatabaseUtil();
				boolean rs = db.testDB2Conn(url, db_username, db_password);
				
				if(rs){
					result = "1";
				}else
					result = "0";
				
			}else if("02".equals(db_type))//sql server
			{
				//jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=mydb
				url = "jdbc:sqlserver://"+data_source_ip+":"+access_port+";DatabaseName="+db_instance;
				ConnDatabaseUtil db = new ConnDatabaseUtil();
				boolean rs = db.testSqlServer2005Conn(url, db_username, db_password);
				
				if(rs){
					result = "1";
				}else
					result = "0";
			}else if("03".equals(db_type))//mysql
			{
				//jdbc:mysql://localhost:3306/myuser
				url = "jdbc:mysql://"+data_source_ip+":"+access_port+"/"+db_instance;
				ConnDatabaseUtil db = new ConnDatabaseUtil();
				boolean rs = db.testMySqlConn(url, db_username, db_password);
				
				if(rs){
					result = "1";
				}else
					result = "0";
				
			}else if("04".equals(db_type)){
				url = "jdbc:rmi://"+data_source_ip+"/jdbc:odbc:"+db_instance;
				ConnAccess ca = new ConnAccess();
				boolean rs = ca.testConnection(data_source_ip, db_instance, db_username, db_password);
				if(rs){
					result = "1";
				}else
					result = "0";
			}else{
				
			}
		}
		String[] ArrRes  = new String[2];
		ArrRes[0] = url;
		ArrRes[1] = result;
		return ArrRes;
	}
	
	/**
	 * 
	 * getDataBaseList(查询数据库采集任务)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param collect_task_id
	 * @return
	 * @throws DBException        
	 * List       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public List getDataBaseList(String collect_task_id) throws DBException{
		List databaseList = new ArrayList();
		StringBuffer databaseSql = new StringBuffer("select t.SOURCE_COLLECT_TABLE,t.SOURCE_COLLECT_COLUMN,t.COLLECT_TABLE,t.COLLECT_MODE,t.DATABASE_TASK_ID  from collect_database_task  t  where t.collect_task_id = '"+collect_task_id+"'");
		ServiceDAO	daoTable	= new ServiceDAOImpl(); // 操作数据表Dao
		databaseList = daoTable.query(databaseSql.toString());
		return databaseList;
		
	}
	
	/**
	 * 
	 * insertDatas(以sql语句的形式返回需要采集的数据)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param souColumns
	 * @param desColumns
	 * @param souTable
	 * @param desTable
	 * @param collect_mode
	 * @param sour_collect_column
	 * @param url
	 * @param db_username
	 * @param db_password
	 * @return
	 * @throws TxnDataException
	 * @throws DBException        
	 * String[]       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public String[] insertDatas(String db_type,HashMap<String,String> souColumnsType,HashMap<Integer,String> souColumns,HashMap desColumns,String souTable,String desTable,String collect_mode,String sour_collect_column,String url,String db_username,String db_password) throws TxnDataException, DBException{
		List<String> sqlList = new ArrayList<String>();
		DatabaseResource dr = new DatabaseResource();
		String bigNumType = dr.getBigNumType(db_type);
		
		try {
			ConnDatabaseUtil cd = new ConnDatabaseUtil();
			Connection conn = cd.getConn(db_type,url, db_username, db_password);
			Statement stmt = null;
			ResultSet rs = null;
			String sql = "";
			//StringBuffer colSql = new StringBuffer();
			StringBuffer insertColumns = new StringBuffer();//相匹配插入的字段信息
			AnalyCollectFile ac = new AnalyCollectFile();
			String[] dataStr = ac.getDatabaseDesDataItem(db_type,souColumnsType,souColumns, desColumns);
			String colSql = dataStr[1];
			String[] colColumnStrings = colSql.split(",");
			//conn = getConn(db_type,url, db_username, db_password);
			conn = DriverManager.getConnection(url, db_username, db_password);
			stmt = conn.createStatement();
			//String sql = "select table_name from all_tab_comments where owner = '"+user.toUpperCase()+"' and table_type = 'TABLE'";
			if (CollectConstants.COLLECT_MODE_ALL.equals(collect_mode)) {//全量
				AnalyCollectFile acf = new AnalyCollectFile();
				String delSql = "delete from " + desTable;
				acf.execDelSql(delSql);
				//获取大字段类型
				
				if("01".equals(db_type)){
					//sql = "select "+colSql+" from SKS."+souTable;
					sql = "select "+colSql+" from "+db_username.toUpperCase()+"."+souTable;
				}else{
					sql = "select "+colSql+" from "+souTable;
				}
				
				/*Map  desColumnsTmp = new HashMap();
				desColumnsTmp.put("0", "ID");
				desColumnsTmp.put("1", "USERNAME");
				desColumnsTmp.put("2", "PASSWORD1");
				desColumnsTmp.put("3", "PASSWORD22");
				desColumns = desColumnsTmp;*/
				
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					
					StringBuffer insertVal = new StringBuffer();
					StringBuffer insertCol = new StringBuffer();
					for(int m=0;m<colColumnStrings.length;m++){
						String tmpValue = rs.getString(m+1);
						String tmpKey = colColumnStrings[m].toUpperCase();
						
						if((desColumns.containsValue(tmpKey))&&(bigNumType.indexOf(souColumnsType.get(tmpKey).toUpperCase())<0)){
							if (insertCol == null
									|| insertCol.toString().equals("")) {
								insertCol.append(tmpKey);
								
								if(tmpValue!=null){
									if("null".equals(tmpValue))
										tmpValue="";
								}else{
									tmpValue = "";
								}
								
								insertVal.append("'" + tmpValue+ "'");
							} else {
								insertCol.append("," + tmpKey);
								System.out.println("1111tmpValue="+tmpValue);
								if(tmpValue!=null){
									if("null".equals(tmpValue))
										tmpValue="";
								}else{
									tmpValue = "";
								}
								System.out.println("2222tmpValue="+tmpValue);
								insertVal.append(",'" + tmpValue+ "'");
							}
						}
						
					}
					
					// 需要插入的sql
					if (desTable != null && !"".equals(desTable)
							&& insertCol.toString() != null
							&& !"".equals(insertCol.toString())
							&& insertVal.toString() != null
							&& !"".equals(insertVal.toString())) {
						String insertSql = "insert into " + desTable
								+ " (" + insertCol.toString()
								+ ") values(" + insertVal.toString()
								+ ")";
						System.out.println("全量--插入sql="+insertSql);
						sqlList.add(insertSql);
					}
					
				}
				
			}else if (CollectConstants.COLLECT_MODE_ADD.equals(collect_mode)){//增量
				if (desColumns.containsValue(sour_collect_column.toUpperCase())){//增量字段在目的采集表中
					String count = "0";
					Connection cjConn = null;
					Statement st = null;
					ResultSet cjRS = null;
					//String querySql = "select *  from "+desTable+" where rownum = 1";
					String querySql = "select *  from "+desTable+" where rownum=1";
					System.out.println("querySql="+querySql);
				try{	
					cjConn = DbUtils.getConnection("5");//连接采集库
					cjConn.setAutoCommit(false);
					st = cjConn.createStatement();
					
					cjRS = st.executeQuery(querySql);
					
					
					while(cjRS.next()){
						count="1";
					}
					
				}catch(Exception e){
					e.printStackTrace();
					throw new TxnDataException("error","查询目标表失败！");
				}finally{
					try{
						if(null!=cjRS){
							cjRS.close();
						}
						if(cjConn!=null){
							conn.setAutoCommit(true);
						}
						DbUtils.freeConnection(cjConn);
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
					
					
					System.out.println("count="+count);
					if(count.equals("0")){//第一次增量导入,数据为空，全部数据入库
						if("01".equals(db_type)){
							sql = "select "+colSql+" from "+db_username.toUpperCase()+"."+souTable;
						}else{
							sql = "select "+colSql+" from "+souTable;
						}
						//sql = "select "+colSql+" from " +souTable ;
					}else{
						String queryMaxColumn = "select max("+sour_collect_column+") as max_date from "+desTable;
						Connection cjConn1 = null;
						Statement st1 = null;
						ResultSet cjRS1 = null;
						String max_date = "";
						try{
							cjConn1 = DbUtils.getConnection("5");
							st1 = cjConn1.createStatement();
							cjRS1 = st1.executeQuery(queryMaxColumn);
							while(cjRS1.next()){
								max_date = cjRS1.getString(1);
							}
							/*if(cjRS1!=null){
								
							}*/
						}catch(Exception e){
							throw new TxnDataException("error","查询目标表失败！");
						}finally{
							try{
								if(null!=cjRS1){
									cjRS1.close();
								}
								DbUtils.freeConnection(cjConn1);
							}catch(SQLException e){
								e.printStackTrace();
							}
						}
						
						if("01".equals(db_type)){
							//sql = "select "+colSql+" from SKS."+souTable+"  where  "+sour_collect_column +" > '"+max_date+"' ";
							sql = "select "+colSql+" from "+db_username.toUpperCase()+"."+souTable+"  where  "+sour_collect_column +" > '"+max_date+"' ";
						}else{
							sql = "select "+colSql.toString()+" from "+souTable+"  where  "+sour_collect_column +" > '"+max_date+"' ";
						}
						
						//sql = "select "+colSql.toString()+" from "+souTable+"  where  "+sour_collect_column +" > '"+max_date+"' ";
					}
					
					
					
					rs = stmt.executeQuery(sql);
					while(rs.next()){
						
						StringBuffer insertVal = new StringBuffer();
						StringBuffer insertCol = new StringBuffer();
						for(int m=0;m<colColumnStrings.length;m++){
							String tmpValue = rs.getString(m+1);
							String tmpKey = colColumnStrings[m].toUpperCase();
							
							if((desColumns.containsValue(tmpKey))&&(bigNumType.indexOf(souColumnsType.get(tmpKey).toUpperCase())<0)){
								if (insertCol == null
										|| insertCol.toString().equals("")) {
									insertCol.append(tmpKey);
									if(tmpValue!=null){
										if("null".equals(tmpValue))
											tmpValue="";
									}else{
										tmpValue = "";
									}
									insertVal.append("'" + tmpValue+ "'");
								} else {
									insertCol.append("," + tmpKey);
									if(tmpValue!=null){
										if("null".equals(tmpValue))
											tmpValue="";
									}else{
										tmpValue = "";
									}
									insertVal.append(",'" + tmpValue+ "'");
								}
							}
							
						}
						
						// 需要插入的sql
						if (desTable != null && !"".equals(desTable)
								&& insertCol.toString() != null
								&& !"".equals(insertCol.toString())
								&& insertVal.toString() != null
								&& !"".equals(insertVal.toString())) {
							String insertSql = "insert into " + desTable
									+ " (" + insertCol.toString()
									+ ") values(" + insertVal.toString()
									+ ")";
							
							sqlList.add(insertSql);
						}
						
					}
	
				}else{
					throw new TxnDataException("error", "增量字段不在目标采集表【"+desTable+"】中！");
				}
				
			}
			
			
				
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQL异常");
				e.printStackTrace();
			
		}
		
		
		
		String[] sql = new String[sqlList.size()];
		for (int i = 0; i < sql.length; i++) {
			sql[i] = sqlList.get(i);
		}
		
		return sql;
	}
	
	/**
	 * 
	 * getSourColumns(获取数据源中的表的字段信息)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param url
	 * @param db_username
	 * @param db_password
	 * @param tablename
	 * @return        
	 * HashMap<Integer,String>       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public HashMap<Integer,String> getSourColumns(String db_type,String url,String db_username,String db_password,String tablename){
		HashMap<Integer,String>  columns = new HashMap<Integer,String>();
		try {
			ConnDatabaseUtil cd = new ConnDatabaseUtil();
			Connection conn = cd.getConn(db_type,url,db_username, db_password);
			Statement stmt = null;
			ResultSet rs = null;
			
				try {
					conn = DriverManager.getConnection(url, db_username, db_password);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if("00".equals(db_type)){//oracle
					stmt = conn.createStatement();
					//String sql = "select table_name from all_tab_comments where owner = '"+user.toUpperCase()+"' and table_type = 'TABLE'";
					String sql = "select column_id, column_name, data_type, data_length, data_precision, data_scale,nullable,data_default from user_tab_columns where table_name = '"+tablename.toUpperCase()+"' order by column_id";
					rs = stmt.executeQuery(sql);
					int num =0;
					while(rs.next()){
						String column_name = rs.getString(2);
						columns.put(num, column_name);
						num++;
					}
					
					rs.close();
					stmt.close();
					conn.close();
				}else if("01".equals(db_type))//db2
				{
					stmt = conn.createStatement();
					//String sql = "select table_name from all_tab_comments where owner = '"+user.toUpperCase()+"' and table_type = 'TABLE'";
					String sql = "select COLUMN_SIZE, COLUMN_NAME, DATA_TYPE from syscolumns where tbname = '"+tablename.toUpperCase()+"' ";
					rs = stmt.executeQuery(sql);
					int num =0;
					while(rs.next()){
						String column_name = rs.getString(2);
						columns.put(num, column_name);
						num++;
					}
					rs.close();
					stmt.close();
					conn.close();
					
				}else if("02".equals(db_type))//sql server
				{
					stmt = conn.createStatement();
					//String sql = "select table_name from all_tab_comments where owner = '"+user.toUpperCase()+"' and table_type = 'TABLE'";
					String sql = "select name syscolumns where ID = OBJECT_ID('"+tablename.toUpperCase()+"') ";
					rs = stmt.executeQuery(sql);
					int num =0;
					while(rs.next()){
						String column_name = rs.getString(1);
						columns.put(num, column_name);
						num++;
					}
					rs.close();
					stmt.close();
					conn.close();
				}else if("03".equals(db_type))//mysql
				{
					stmt = conn.createStatement();
					//String sql = "select table_name from all_tab_comments where owner = '"+user.toUpperCase()+"' and table_type = 'TABLE'";
					String sql = "select name syscolumns where ID = OBJECT_ID('"+tablename.toUpperCase()+"') ";
					rs = stmt.executeQuery(sql);
					int num =0;
					while(rs.next()){
						String column_name = rs.getString(1);
						columns.put(num, column_name);
						num++;
					}
					rs.close();
					stmt.close();
					conn.close();
					
				}else if("04".equals(db_type)){
					
					DatabaseMetaData dbmd = conn.getMetaData();
					rs = dbmd.getColumns(null, null, tablename, "%");//access
					int num = 0;
					while(rs.next()){
						String column_name = rs.getString("COLUMN_NAME");
						columns.put(num, column_name);
						num++;
					}
					rs.close();
					conn.close();
					
				}else{
					
				}
				
		} catch (SQLException e) {
			System.out.println("SQL异常");
				e.printStackTrace();
		}//数据库连接
		
		return columns;
	}
	
	
}
