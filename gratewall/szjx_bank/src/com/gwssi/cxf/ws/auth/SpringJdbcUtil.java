package com.gwssi.cxf.ws.auth;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 数据库操作工具类。
 * 
 * @author chaihaowei, liuhailong
 */
public class SpringJdbcUtil {
	
	private static Logger logger = Logger.getLogger(SpringJdbcUtil.class);
	
	/**
	 * 加载数据源
	 */
	 private static DataSource dataSource;

	  public static DataSource getDataSource(String dataSourceKey){
	    if (dataSource == null) {
	      Resource resource = new ClassPathResource("applicationContext.xml");
	      BeanFactory factory = new XmlBeanFactory(resource);
	      dataSource = (DataSource)factory.getBean(dataSourceKey);
	    }
	    return dataSource;
	  }
	
	/**
	 * 获取数据源
	 * @param key
	 * @return
	 */
	
	/**
	 * 获取JdbcTemplate。
	 * 
	 * @param dataSourceKey 数据源key
	 * @return
	 */
	protected static JdbcTemplate getJdbcTemplate(String dataSourceKey){
		DataSource dataSource = getDataSource(dataSourceKey);
		// TODO 可做缓存，避免重复创建。
		JdbcTemplate ret = new JdbcTemplate(dataSource);
		return ret;
	}
	
	/**
	 * 查询。
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
		logger.info("[SQL QUERY]DataSource:"+datasourceKey+";SQL：" + sql +";Params: " + params);
		if (params == null){
			ret = jdbcTemplate.queryForList(sql);
		}else{
			ret = jdbcTemplate.queryForList(sql, params);
		}
		logger.info("[SQL QUERY]Result:" + ret);
		return ret;
	}
	/**
	 * 查询。
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
	 * 查询。
	 * 
	 * @param datasourceName
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int queryForInt(String datasourceKey,String sql,String...params){
		JdbcTemplate jdbcTemplate = getJdbcTemplate(datasourceKey);
		int ret = -1;

		logger.info("[SQL QUERY]DataSource:"+datasourceKey+";SQL：" + sql +";Params: " + params);
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

		logger.info("[SQL QUERY]DataSource:"+datasourceKey+";SQL：" + sql +";Params: " + params);
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
		logger.info("[SQL QUERY]DataSource:"+datasourceKey+";SQL：" + sql +";Params: " + params);
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
		logger.info("[SQL QUERY]DataSource:"+datasourceKey+";SQL：" + sql +";Params: " + params);
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
