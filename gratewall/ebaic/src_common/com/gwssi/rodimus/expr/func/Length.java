package com.gwssi.rodimus.expr.func;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author liuhailong
 */
public class Length extends BaseExprFunction {
	
	protected final static Logger logger = Logger.getLogger(Length.class);
	
	
	public Object run(Object... args) {
		
		if(args.length!=1){
			throw new IllegalArgumentException("Length函数只需要传入一个参数。");
		}
		Object o = args[0];
		if(o==null){
			return 0;
		}
		
		if(o instanceof List){
			@SuppressWarnings("rawtypes")
			List list = (List)o;
			return list.size();
		}
		
		if(o instanceof Map){
			@SuppressWarnings("rawtypes")
			Map m = (Map)o;
			return m.size();
		}

		if(o instanceof Object[]){
			Object[] c = (Object[])o;
			return c.length;
		}

		if(o instanceof String){
			String s = (String)o;
			return s.length();
		}
		
		if(o instanceof Collection){
			@SuppressWarnings("rawtypes")
			Collection c = (Collection)o;
			return c.size();
		}
		
		if(o instanceof Set){
			@SuppressWarnings("rawtypes")
			Set c = (Set)o;
			return c.size();
		}
		
		return -1;
	}

}
