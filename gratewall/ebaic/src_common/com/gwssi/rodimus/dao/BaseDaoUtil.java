package com.gwssi.rodimus.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.persistence.jdbc.StoredProcParam;
import com.gwssi.rodimus.exception.DaoException;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 
 * @author liuhailong
 */
public abstract class BaseDaoUtil{
	
	protected Logger logger = Logger.getLogger(BaseDaoUtil.class);

	/**
	 * 
	 * @return Dao对象，特殊场景下才需要。
	 */
	public abstract IPersistenceDAO getDao() ;
	/**
	 * 执行SQL。
	 * 
	 * @param dao
	 * @param sql
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public int execute(IPersistenceDAO dao,String sql,Object...params){
		try {
			if(dao==null){
				dao = getDao();
			}
			List<Object> paramList = prepareParams(params);
			int ret = dao.execute(sql, paramList);
			return ret;
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
	}
	
	/**
	 * 批量执行
	 * @param sql
	 * @param sqlParamsList
	 * @return
	 */
	public int executeBatch(String sql,List<List<Object>> sqlParamsList){
		try {
			IPersistenceDAO dao = getDao();
			return dao.executeBatch(sql, sqlParamsList);
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
		
		 
	}
	/**
	 * 执行SQL。
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int execute(String sql,Object...params){
		IPersistenceDAO dao = getDao();
		return execute(dao,sql,params);
	}
	
	@SuppressWarnings("unchecked")
	private List<Object> prepareParams(Object...params){
		if(params!=null && params.length>0 && params[0] instanceof List){
			return (List<Object>)params[0];
		}
		List<Object> paramList = new ArrayList<Object>();
		if(params==null||params.length<1){
			return paramList;
		}
		for(Object item : params){
			paramList.add(item);
		}
		return paramList ;
	}
	
	/**
	 * 查询，不分页。
	 * 
	 * @param dao
	 * @param sql
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String,Object>> queryForList(IPersistenceDAO dao,String sql,Object...params) {
		try{
			if(dao==null){
				dao = getDao();
			}
			List<Object> paramList = prepareParams(params);
			List<Map> list = dao.queryForList(sql, paramList);
			List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
			for(Map item : list){
				ret.add(item);
			}
			return ret;
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
	}
	public List<Map<String,Object>> queryForList(String sql,Object...params) {
		return queryForList(null,sql,params);
	}
	/**
	 * 查询，不分页。
	 * 
	 * @param sql
	 * @param clz
	 * @param params
	 * @return
	 */
	public <T> List<T> queryForListBo(String sql,Class<T> clz, Object...params){
		try{
			IPersistenceDAO dao = getDao();
			List<Object> paramList = prepareParams(params);
			List<T> retList = dao.queryForList(clz, sql, paramList);
			return retList;
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String,Object>> pageQueryForList(String sql,List<String> params,
			int pageSize,int pageIndex){
		try {
			IPersistenceDAO dao = getDao();
			List<Map> list = dao.pageQueryForList(sql, params, pageSize,pageIndex);
			List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
			for(Map item : list){
				ret.add(item);
			}
			return ret;
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String,Object>> pageQueryForList(String sql,Object...params) {
		try{
			IPersistenceDAO dao = getDao();
			List<Object> paramList = prepareParams(params);
			List<Map> list = dao.pageQueryForList(sql, paramList);
			List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
			for(Map item : list){
				ret.add(item);
			}
			return ret;
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
	}
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> List<T> pageQueryForListBo(String sql,Class<T> clz,Object...params) {
		try{
			IPersistenceDAO dao = getDao();
			List<Object> paramList = prepareParams(params);
			List<T> retList = dao.pageQueryForList(clz, sql, paramList);
			return retList;
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
	}
	
	/**
	 * @param dao
	 * @param sql
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public Map<String, Object> queryForRow(String sql,Object...params) {
		Map<String, Object> retMap = null;
		List<Map<String, Object>> list = queryForList(sql, params);
		if(list==null || list.isEmpty()){
			return null;
		}
		retMap = list.get(0);
		return retMap;
	}
	
	/**
	 * 
	 * @param sql
	 * @param clz
	 * @param params
	 * @return
	 */
	public  <T> T queryForRowBo(String sql, Class<T> clz, Object...params) {
		List<T> list = queryForListBo(sql,clz,params);
		if(list==null || list.isEmpty()){
			return null;
		}
		T ret = list.get(0);
		return ret;
	}
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public Object queryForOne(String sql,Object...params){
		Map<String, Object> row = queryForRow(sql, params);
		if(row==null || row.isEmpty() || row.values().isEmpty()){
			return null;
		}else{
			Object[] values = row.values().toArray();
			Object ret = values[0];
			return ret;
		}
	}
	
	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public String queryForOneString(String sql,Object...params) {
		Object val = queryForOne(sql,params);
		String ret = "";
		if(val instanceof BigDecimal){
			BigDecimal numberRet = (BigDecimal)val;
			ret = numberRet.doubleValue() + "";
			return ret;
		}
		if(val instanceof Calendar){
			Calendar date = (Calendar)val;
			ret = DateUtil.toDateStrWithTime(date);
			return ret;
		}
		ret = StringUtil.safe2String(val);
		return ret;
	}
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public Calendar queryForOneCalendar(String sql,Object...params) {
		Object val = queryForOne(sql,params);
		if(val instanceof Calendar){
			Calendar ret = (Calendar)val;
			return ret;
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public BigDecimal queryForOneBigDecimal(String sql,Object...params) {
		Object val = queryForOne(sql,params);
		if(val instanceof BigDecimal){
			BigDecimal ret = (BigDecimal)val;
			return ret;
		}else{
			if("0".equals(val)){
				return BigDecimal.ZERO;
			}
			return null;
		}
	}
	
	/**
	 * 
	 * @param dao
	 * @param sql
	 * @param params
	 * @return
	 */
	public long queryForOneLong(String sql,Object...params) {
		BigDecimal ret = queryForOneBigDecimal(sql,params);
		if(ret==null){
			return 0L;
		}else{
			return ret.longValue();
		}
	}
	/**
	 * 
	 * @param bo
	 */
	public void insert(AbsDaoBussinessObject bo){
		try {
			IPersistenceDAO dao = getDao();
			dao.insert(bo);
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
	}
	/**
	 * 
	 * @param clz
	 * @param id
	 */
	public void delete(Class<? extends AbsDaoBussinessObject> clz,Serializable id){
		try {
			IPersistenceDAO dao = getDao();
			dao.deleteByKey(clz, id);
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
	}
	/**
	 * 
	 * @param bo
	 */
	public void delete(AbsDaoBussinessObject bo){
		try {
			IPersistenceDAO dao = getDao();
			dao.delete(bo);
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
	}
	/**
	 * 
	 * @param bo
	 * @return
	 */
	public int update(IPersistenceDAO dao,AbsDaoBussinessObject bo){
		try {
			if(dao==null){
				dao = getDao();
			}
			int ret = -1;
			ret = dao.update(bo);
			return ret;
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
	}
	public int update(AbsDaoBussinessObject bo){
		return update(null,bo);
	}
	/**
	 * 
	 * @param clz
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> clz,Serializable id){
		try {
			IPersistenceDAO dao = getDao();
			T ret = dao.queryByKey(clz, id);
			return ret;
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
	}
	public List<?> callStoreProcess(IPersistenceDAO dao , String command, List<StoredProcParam> params) {
		List<?> ret = null;
		try {
			if(dao==null){
				dao = getDao();
			}
			ret = dao.callStoreProcess(command, params);
		} catch (OptimusException e) {
			throw new DaoException(e.getMessage(),e);
		}
		return ret;
	}
	public List<?> callStoreProcess(String command, List<StoredProcParam> params) {
		List<?> ret = callStoreProcess(null,command, params);
		return ret;
	}
}