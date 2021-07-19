package com.gwssi.datacenter.dataSource.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import oracle.jdbc.driver.OracleConnection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmFunctionBO;
import com.gwssi.application.model.SmGrantAuthBO;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.application.model.SmServicesBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.datacenter.model.DcColumnBO;
import com.gwssi.datacenter.model.DcDataSourceBO;
import com.gwssi.datacenter.model.DcTableBO;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.persistence.dao.OptimusPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.util.UuidGenerator;


@Service
public class DataStructLoadService extends BaseService{
	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * 获取当前数据源的Table数
	 * @param smser
	 * @return
	 */
	public List<Map<String,Object>>  findCurrUserTable(DcDataSourceBO smser) {

		List<DcTableBO> a1 =getTables(smser);
		List<Map<String,Object>> a2=changDcTableBOToTreeList(a1);
		return a2;
	}	
	
	public List<Map<String, Object>> changDcTableBOToTreeList(
			List<DcTableBO>  list) {
		List system = new ArrayList();
		Map systemN=null;
		for(DcTableBO sm:list){	
			systemN=new HashMap();
			systemN.put("id", sm.getTableNameEn());
			if(StringUtils.isEmpty(sm.getTableNameCn())){
				systemN.put("name", sm.getTableNameEn()+"("+sm.getTableNameEn()+")");
			}else{
				systemN.put("name", sm.getTableNameCn()+"("+sm.getTableNameEn()+")");
			}
	
			systemN.put("pid", "system");
			system.add(systemN);
		}
		
		return system;
	}
	/**
	 * 获取原始Connection对象 进行处理
	 * @param smser
	 * @return
	 */
	public Connection getconn(DcDataSourceBO smser){
		Connection conn=null;
	/*	DataSoucreBO datasourcebo =new DataSoucreBO();
		datasourcebo.setDbtype(smser.getDbType());
		String  driverClass = "oracle.jdbc.OracleDriver"; 
		datasourcebo.setDriverClassName(driverClass);
		datasourcebo.setKey(smser.getPkDcDataSource());
		datasourcebo.setPassword(smser.getPwd());
		String url="jdbc:oracle:thin:@"+smser.getDataSourceIp()+":"+smser.getAccessPort()+":"+smser.getDbInstance();
		datasourcebo.setUrl(url);
		datasourcebo.setUsername(smser.getUserName());
		DBContextHolder.setDataSoureBO(datasourcebo);
		DataSourceManagerChild a =new DataSourceManagerChild();*/
		//a.setDataSource();
		 try {
			 conn=null;
			 //conn=	DataSourceManager.getInstance().getConnection(smser.getPkDcDataSource()).getMetaData().getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//移除数据源 
			//a.removekey(smser.getPkDcDataSource());
		}
		
		return conn;
	}
	/**
	 * 获取列单个表的列信息
	 * @param smser
	 * @param table
	 * @return
	 */
	public List<DcColumnBO> getTableColumn(DcDataSourceBO smser,DcTableBO table){
		List<DcColumnBO>  list= new ArrayList<DcColumnBO>();
		Connection conn=getconn(smser);
		// 从缓存中获取当前登录用户信息
	    ResultSet rs = null;
	    try {
	      
	       
	    	//设置连接属性,使得可获取到表的REMARK(备注)
	    	if(smser.getDbType().equals("oracle")){
	    		((OracleConnection)conn).setRemarksReporting(true); 
	    	}

	    	DatabaseMetaData dbmd = conn.getMetaData();
	    	rs =dbmd.getColumns(null, null, table.getTableNameEn().toUpperCase(), null);
	
	    	

	
	      while(rs.next()){
	    	 String columnName = rs.getString("COLUMN_NAME");  //列名  
	    	 String dataTypeName = rs.getString("TYPE_NAME");  //java.sql.Types类型名称(列类型名称)
	    	 int columnSize = rs.getInt("COLUMN_SIZE");  //列大小  
	         String remarks = rs.getString("REMARKS");  //列描述  
	          /**
              *  0 (columnNoNulls) - 该列不允许为空
              *  1 (columnNullable) - 该列允许为空
              *  2 (columnNullableUnknown) - 不确定该列是否为空
              */
             int nullAble = rs.getInt("NULLABLE");  //是否允许为null  
             DcColumnBO bo =new DcColumnBO();
             bo.setColumnLength(new BigDecimal(columnSize));
             
             bo.setColumnNameEn(columnName);
             bo.setPkDcTable(table.getPkDcTable());
             bo.setColumnNameCn(remarks);
             bo.setColumnType(dataTypeName);
             

            	 
             //是否为空
             if(0 ==nullAble){
            	 bo.setIsNull(AppConstants.ISNULL_N);
             }else {
             	 bo.setIsNull(AppConstants.ISNULL_Y);	 
             }
             
             
	         list.add(bo);
	      }
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }finally{
	      close(rs,conn);
	    }		
		return list;
	}
	
	/**
	 * 获取表中索引列
	 * @param smser 数据源类
	 * @param table 表
	 * @return
	 */
	public List<String> getIndexInfo(DcDataSourceBO smser,DcTableBO table){
		Connection conn =getconn(smser);
		List<String> strList =new ArrayList<String>();
	    ResultSet rs = null;
	    try {
	    	DatabaseMetaData dbmd = conn.getMetaData();
	        rs = dbmd.getIndexInfo(null, null, table.getTableNameEn().toUpperCase(), false, true);  
		      while(rs.next()){
		    	  String columnName = rs.getString("COLUMN_NAME");//列名
		    	if(StringUtils.isNotEmpty(columnName)){
		    		  strList.add(columnName);
		    	  }
			  }
	    } catch (Exception ex) {
		      ex.printStackTrace();
	    }finally{
		      close(rs,conn);
		 }	
		return strList;
	}
	/**
	 * 获取表中主键列名
	 * @param smser
	 * @param table
	 * @return
	 */
	public List<String> getPrimaryKeysInfo(DcDataSourceBO smser,DcTableBO table){
		List<String> strList =new ArrayList<String>();
		Connection conn=getconn(smser);
	    ResultSet rs = null;
	    try {

	    	DatabaseMetaData dbmd = conn.getMetaData();
	    	rs = dbmd.getPrimaryKeys(null, null, table.getTableNameEn().toUpperCase());  
		      while(rs.next()){
		    	  String columnName = rs.getString("COLUMN_NAME");//列名
		    	if(StringUtils.isNotEmpty(columnName)){
		    		  strList.add(columnName);
		    	  }
			  }
	    } catch (Exception ex) {
		      ex.printStackTrace();
	    }finally{
		      close(rs,conn);
		 }	
		return strList;
	}	
	/**
	 * 获取当前数据源下的表
	 * @return
	 */
	public List<DcTableBO> getTables(DcDataSourceBO smser){
		List<DcTableBO>  list= new ArrayList<DcTableBO>();
		Connection conn=getconn(smser);
	    ResultSet rs = null;
	    try {
	      
	       
	    	//设置连接属性,使得可获取到表的REMARK(备注)
	    	if(smser.getDbType().equals("oracle")){
	    		((OracleConnection)conn).setRemarksReporting(true); 
	    	}

	    	DatabaseMetaData dbmd = conn.getMetaData();
	    	rs = dbmd.getTables(conn.getCatalog(), smser.getUserName().toUpperCase(), null, new String[]{"TABLE"}); 
	
	
	      while(rs.next()){
	         String tableCat = rs.getString("TABLE_CAT");  //表类别(可为null) 
	         String tableSchemaName = rs.getString("TABLE_SCHEM");//表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知     
	         String tableName = rs.getString("TABLE_NAME");  //表名  
	         String tableType = rs.getString("TABLE_TYPE");  //表类型,典型的类型是 "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM"。
	         String remarks = rs.getString("REMARKS");       //表备注  
	         
	         DcTableBO bo = new DcTableBO();
	         bo.setTableNameEn(tableName);
	         bo.setTableNameCn(remarks);
	         //表编码 --两个数据源都有相同的表编码    要生成一个唯一的表编码
	         //表类型 --让用户自己选择 业务表 字典表 系统表 不在这里维护。
	         list.add(bo);
	      }
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }finally{
	      close(rs,conn);
	    }		
		return list;
	}	
	
	
	/**
	 * 处理保存的 所加载的表 以及表中字段
	 * @param tablenames 表名
	 * @param smser 数据源BO
	 * @return
	 * @throws OptimusException 
	 */
	public boolean saveTableAndFileds(List<String> tablenames,
			DcDataSourceBO smser) throws OptimusException {
		
		// 从缓存中获取当前登录用户信息
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);		
		

		
		//某个数据源当前用户下的所有表
		List<DcTableBO> tabls= getTables(smser);
		
		//要增加到记录的表
		List<DcTableBO> needToInsertTables =new ArrayList<DcTableBO>();
		for(DcTableBO bo:tabls){
			if(tablenames.contains(bo.getTableNameEn())){
				bo.setCreaterId(user.getUserId());
				bo.setCreaterName(user.getUserName());
				bo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
				bo.setCreaterTime(Calendar.getInstance());
				bo.setPkDcDataSource(smser.getPkDcDataSource());
				bo.setPkDcTable(UuidGenerator.getUUID());
				needToInsertTables.add(bo);
			}
		}
		
		//增加所需要增加的表 
		insertDcTableBO(needToInsertTables);
		
		//这一段代码 有问题 需要改
		//需要增加的字段
		 List<DcColumnBO>  needToInsertFields=new ArrayList<DcColumnBO>();
			for(DcTableBO bo:needToInsertTables){

				 List<DcColumnBO>  needFields=new ArrayList<DcColumnBO>();
				 needFields=getTableColumn(smser,bo);
				 //设置索引列。。。
				 needToInsertFields.addAll(setKeyIndex(smser,bo,needFields));
			}
		
		for(DcColumnBO bo:needToInsertFields){
			bo.setCreaterId(user.getUserId());
			bo.setCreaterName(user.getUserName());
			bo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
			bo.setCreaterTime(Calendar.getInstance());
			bo.setPkDcDataSource(smser.getPkDcDataSource());
		}	
		//新增字段
		insertDcColumnBO(needToInsertFields);
		return true;
	}  
	/**
	 * 处理主键和索引等
	 * @param smser
	 * @param table
	 * @param needFields
	 * @return
	 */
	public  List<DcColumnBO> setKeyIndex(DcDataSourceBO smser,DcTableBO table, List<DcColumnBO> needFields){
    	List<String> indexList=getIndexInfo(smser, table);
    	//获取表中主键
    	List<String>  primaryList=getPrimaryKeysInfo(smser,  table);
    	
		 for(DcColumnBO co:needFields){
             //是否索引
             if(indexList.contains(co.getColumnNameEn())){
            	 co.setIsIndex(AppConstants.IS_INDEX_Y);
             }else{
            	 co.setIsIndex(AppConstants.IS_INDEX_N);
             }
             
             //是否为主键
             if(primaryList.contains(co.getColumnNameEn())){
            	co.setIsPrimaryKey(AppConstants.IS_PRIMARY_KEY_Y); 
             }else{
            	 co.setIsPrimaryKey(AppConstants.IS_PRIMARY_KEY_N);  
             }
			 
		 }
		return needFields; 
		
		
	}
	
	/**
	 * 新增字段 
	 * @param needToInsertTables
	 * @throws OptimusException
	 */
	public void insertDcColumnBO(List<DcColumnBO>  needToInsertFields)
			throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(needToInsertFields);
	}		
	/**
	 * 新增表 
	 * @param needToInsertTables
	 * @throws OptimusException
	 */
	public void insertDcTableBO(List<DcTableBO>  needToInsertTables)
			throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(needToInsertTables);
	}	
	
	  //关闭连接
    public  void close(Object o){  
        if (o == null){  
            return;  
        }  
        if (o instanceof ResultSet){  
            try {  
                ((ResultSet)o).close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        } else if(o instanceof Statement){  
            try {  
                ((Statement)o).close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        } else if (o instanceof Connection){  
            Connection c = (Connection)o;  
            try {  
                if (!c.isClosed()){  
                    c.close();  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }    
    }  	
	  public  void close(ResultSet rs, Statement stmt,   
	            Connection conn){  
	    	close(rs);  
	    	close(stmt);  
	    	close(conn);  
	    	
	  }  
    
	   public  void close(ResultSet rs,   
	            Connection conn){  
	    	close(rs);   
	    	close(conn);  
	  }


    	
}
