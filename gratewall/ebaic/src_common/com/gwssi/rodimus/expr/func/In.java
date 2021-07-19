package com.gwssi.rodimus.expr.func;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gwssi.rodimus.util.StringUtil;


/**
 * @author liuhailong
 */
public class In extends BaseExprFunction {
	
	protected final static Logger logger = Logger.getLogger(In.class);
	
	
	public Object run(Object... args) {
		
		if(args.length<2){
			throw new IllegalArgumentException("IN函数至少传入两个参数。");
		}
		Object o = args[0];
		if(o==null){
			return false;
		}
		
		String s = StringUtil.safe2String(o);
		if(StringUtil.isBlank(s)){
			return false;
		}
		
		if(o instanceof List){
			@SuppressWarnings("rawtypes")
			List list = (List)o;
			return list.size();
		}
		
		Set<Object> set = new HashSet<Object>();
		for(int i=2,len = args.length;i<len;++i){
			Object p = args[i];
			set.add(p);
		}
		
		if(set.contains(o)){
			return true ;
		}else{
			return false;
		}
	}

}
