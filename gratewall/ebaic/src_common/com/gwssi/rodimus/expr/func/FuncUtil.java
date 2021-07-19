package com.gwssi.rodimus.expr.func;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gwssi.expression.core.lang.UnsupportedArgumentException;
import com.gwssi.rodimus.dao.ApproveDaoUtil;
import com.gwssi.rodimus.dao.BaseDaoUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.util.ThreadLocalManager;

/**
 * 表达式引擎，自定义函数。
 * 工具类。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class FuncUtil {
	
	private static BaseDaoUtil getDao(){
		String datasourceKey = "datasourceName";
		String datasourceName = (String)ThreadLocalManager.get(datasourceKey);
		if("approveDataSource".equals(datasourceName)){
			return ApproveDaoUtil.getInstance();
		}
		
		return DaoUtil.getInstance();
	}
	
	/**
	 * 查询SQL，返回一行。
	 * 
	 * @param args
	 * @return
	 */
	public static Map<String, Object> queryForRow(Object[] args) {
		if ((args == null) || (args.length < 1) || (args[0] == null)) {
			throw new UnsupportedArgumentException("SqlForRow", args);
		}
		String sql = StringUtil.safe2String(args[0]);
		List<Object> params = new ArrayList<Object>();
		if(args.length>1){
			for(int i=1;i<args.length;++i){
				params.add(args[i]);
			}
		}
		Map<String, Object> ret = getDao().queryForRow(sql, params);
		return ret;
	}
	
	
	/**
	 * 查询SQL，返回一个值。
	 * 
	 * @param args
	 * @return
	 */
	public static Object queryForValue(Object[] args) {
		if ((args == null) || (args.length < 1) || (args[0] == null)) {
			throw new UnsupportedArgumentException("SqlForRow", args);
		}
		String sql = StringUtil.safe2String(args[0]);
		List<Object> params = new ArrayList<Object>();
		if(args.length>1){
			for(int i=1;i<args.length;++i){
				params.add(args[i]);
			}
		}
		Object ret = getDao().queryForOne(sql, params);
		return ret;
	}
	
	/**
	 * 查询SQL，返回列表。
	 * 
	 * @param args
	 * @return
	 */
	public static List<Map<String, Object>> queryForList(Object[] args) {
		if ((args == null) || (args.length < 1) || (args[0] == null)) {
			throw new UnsupportedArgumentException("SqlForRow", args);
		}
		String sql = StringUtil.safe2String(args[0]);
		List<Object> params = new ArrayList<Object>();
		if(args.length>1){
			for(int i=1;i<args.length;++i){
				params.add(args[i]);
			}
		}
		List<Map<String, Object>> ret = getDao().queryForList(sql, params);
		return ret;
	}


}
