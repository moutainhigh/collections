package com.gwssi.rodimus.expr.func;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwssi.expression.core.lang.UnsupportedArgumentException;
import com.gwssi.rodimus.exception.ExprException;

/**
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class SqlForDict extends BaseExprFunction {

	@Override
	public Object run(Object... args) {
		if ((args == null) || (args.length < 1) || (args[0] == null)) {
			throw new UnsupportedArgumentException("SqlForDict", args);
		}
		Map<Object,Object> ret = new HashMap<Object,Object>();
		try {
			List<Map<String,Object>> list = FuncUtil.queryForList(args);
			if(list!=null){
				for(Map<String,Object> row : list){
					Object key = row.get("key");
					Object value = row.get("value");
					if(key!=null && value!=null){
						ret.put(key, value);
					}
				}
			}
		} catch (Exception e) {
			logger.error("在表达式中执行SQL出错：" + e.getMessage());
			throw new ExprException(e.getMessage(),e);
		}
		return ret;
	}

}
