package com.gwssi.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * ���ݿ���������ࡣ
 * 
 * @author chaihaowei, liuhailong
 */
public class SpringJdbcUtil {
	
	private static Map<String,DataSource> datasource = null;
	private static Logger logger = Logger.getLogger(SpringJdbcUtil.class);
	
	/**
	 * ��������Դ
	 */
	static{
		PropertiesUtil dataSourceProperties = PropertiesUtil.getInstance("GwssiDataSource"); 
		String dataSourceKeyString = dataSourceProperties.getProperty("DataSourceKeys");
		String dataSourceConfigFileName = dataSourceProperties.getProperty("DataSourceConfigFile");
		if(StringUtils.isBlank(dataSourceConfigFileName)){
			dataSourceConfigFileName = "GwssiDataSource.xml"; // ���û�����ã������Ĭ��ֵ
		}
		
		
		String[] dataSourceKeyArray = null;
		if(StringUtils.isNotBlank(dataSourceKeyString)){
			dataSourceKeyArray = dataSourceKeyString.trim().split(",");
		}
		if(dataSourceKeyArray == null){
			throw new RuntimeException("����Դ�б�Ϊ�������ļ�DataSourceGwssi");
		}
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(dataSourceConfigFileName); 
	    
		datasource = new HashMap<String,DataSource>();
        for(int i=0;i<dataSourceKeyArray.length;i++){
        	String dataSourceName = dataSourceKeyArray[i];
        	DataSource  dataSource = (DataSource)ctx.getBean(dataSourceName);  
        	logger.info(dataSourceName + ":" +dataSource);
        	datasource.put(dataSourceName, dataSource);
        }
		
	}
	
	/**
	 * ��ȡ����Դ
	 * @param key
	 * @return
	 */
	protected static DataSource getDataSource(String dataSourceKey){
		DataSource ds = datasource.get(dataSourceKey);
		if(ds == null){
			throw new RuntimeException("û������Ϊ��" + dataSourceKey + "��������Դ������DataSourceGwssi.properties��datasourceConfigure.xml�е����á�");
		}
		return ds;
		
	}
	/**
	 * ��ȡJdbcTemplate��
	 * 
	 * @param dataSourceKey ����Դkey
	 * @return
	 */
	protected static JdbcTemplate getJdbcTemplate(String dataSourceKey){
		DataSource dataSource = getDataSource(dataSourceKey);
		// TODO �������棬�����ظ�������
		JdbcTemplate ret = new JdbcTemplate(dataSource);
		return ret;
	}
	
	/**
	 * ��ѯ��
	 * 
	 * @param datasourceName
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> query(String datasourceKey,String sql,Object...params){
		JdbcTemplate jdbcTemplate = getJdbcTemplate(datasourceKey);
		
		List<Map<String,Object>> ret =null;
		logger.info("[SQL QUERY]DataSource:"+datasourceKey+";SQL��" + sql +";Params: " + params);
		if (params == null){
			ret = jdbcTemplate.queryForList(sql);
		}else{
			ret = jdbcTemplate.queryForList(sql, params);
		}
		logger.info("[SQL QUERY]Result:" + ret);
		return ret;
	}
	/**
	 * ��ѯ��
	 * 
	 * @param datasourceKey
	 * @param sql
	 * @return
	 */
	public static List<Map<String,Object>> query(String datasourceKey,String sql){
		Object[] params = null;
		List<Map<String,Object>> ret = query(datasourceKey,sql,params);
		return ret;
	}
	
	/**
	 * ��ѯ��
	 * 
	 * @param datasourceName
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int queryForInt(String datasourceKey,String sql,String...params){
		JdbcTemplate jdbcTemplate = getJdbcTemplate(datasourceKey);
		int ret = -1;

		logger.info("[SQL QUERY]DataSource:"+datasourceKey+";SQL��" + sql +";Params: " + params);
		if (params==null){
			ret = jdbcTemplate.queryForInt(sql);
		}else{
			ret = jdbcTemplate.queryForInt(sql, params);
		}
		logger.info("[SQL QUERY]Result:" + ret);
		return ret;
	}
	
	public static void Execute(String datasourceKey,String sql){
		JdbcTemplate jdbcTemplate = getJdbcTemplate(datasourceKey);

	
		jdbcTemplate.execute(sql);

	
		//return 1;
	}
	
	
	public static int queryForInt(String datasourceKey,String sql,Object...params){
		JdbcTemplate jdbcTemplate = getJdbcTemplate(datasourceKey);
		int ret = -1;

		logger.info("[SQL QUERY]DataSource:"+datasourceKey+";SQL��" + sql +";Params: " + params);
		if (params==null){
			ret = jdbcTemplate.queryForInt(sql);
		}else{
			ret = jdbcTemplate.queryForInt(sql, params);
		}
		logger.info("[SQL QUERY]Result:" + ret);
		return ret;
	}
	@SuppressWarnings("unchecked")
	public static List<Map<String,String>> query2Wcm(String datasourceKey,String sql,Object...params){
		JdbcTemplate jdbcTemplate = getJdbcTemplate(datasourceKey);
		
		List<Map<String,String>> ret =null;
		logger.info("[SQL QUERY]DataSource:"+datasourceKey+";SQL��" + sql +";Params: " + params);
		if (params == null){
			ret = jdbcTemplate.queryForList(sql);
		}else{
			ret = jdbcTemplate.queryForList(sql, params);
		}
		logger.info("[SQL QUERY]Result:" + ret);
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String,BigDecimal>> queryBigDecimal(String datasourceKey,String sql,Object...params){
		JdbcTemplate jdbcTemplate = getJdbcTemplate(datasourceKey);
		
		List<Map<String,BigDecimal>> ret =null;
		logger.info("[SQL QUERY]DataSource:"+datasourceKey+";SQL��" + sql +";Params: " + params);
		if (params == null){
			ret = jdbcTemplate.queryForList(sql);
		}else{
			ret = jdbcTemplate.queryForList(sql, params);
		}
		logger.info("[SQL QUERY]Result:" + ret);
		return ret;
	}
	
	public static List<Map<String,String>> query2Wcm(String datasourceKey,String sql){
		Object[] params = null;
		List<Map<String,String>> ret = query2Wcm(datasourceKey,sql,params);
		return ret;
	}
	
	public static List<Map<String,BigDecimal>> queryBigDecimal(String datasourceKey,String sql){
		Object[] params = null;
		List<Map<String,BigDecimal>> ret = queryBigDecimal(datasourceKey,sql,params);
		return ret;
	}
}
