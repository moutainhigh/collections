package com.gwssi.application.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;

/**
 * Dao工具类。
 * @author liuhailong2008@foxmail.com
 */
public class DaoUtil {
	/**
	 * 获得默认Dao实例。
	 * 
	 * @return
	 */
	public static IPersistenceDAO getDao(){
		IPersistenceDAO ret = DAOManager.getPersistenceDAO();
		return ret;
	}
	/**
	 * 查询列表。
	 * 
	 * @param sql
	 * @return
	 */
	public static List<Map<String,Object>> queryForList(String sql){
		Object[] params = null;
		return queryForList(sql,params);
	}
	/**
	 * 查询列表。
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String,Object>> queryForList(String sql,Object...params){
		try{
			List<Object> paramsList = objectArray2List(params);
			IPersistenceDAO dao = getDao();
			List<Map> list = dao.queryForList(sql, paramsList);
			if(list==null || list.isEmpty()){
				return new ArrayList<Map<String,Object>>();
			}
			List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
			for(Map row : list){
				ret.add(row);
			}
			return ret;
		}catch(OptimusException e){
			e.printStackTrace();
			throw new RuntimeException("数据库查询出错："+e.getMessage());
		}
	}

	
	
	
	/**
	 * 数组转为List。
	 * @param array
	 * @return
	 */
	private static List<Object> objectArray2List(Object[] array) {
		List<Object> ret = new ArrayList<Object>();
		if(array==null || array.length <1){
			return ret;
		}
		for(int i=0;i<array.length;++i){
			ret.add(array[i]);
		}
		return ret;
	}
}
