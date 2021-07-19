package com.gwssi.rodimus.expr.func;

import org.apache.log4j.Logger;

import com.gwssi.rodimus.util.StringUtil;


/**
 * @author liuhailong
 */
public class IsBlank extends BaseExprFunction {
	
	protected final static Logger logger = Logger.getLogger(IsBlank.class);
	
	
	public Object run(Object... args) {
		
		if(args.length<1){
			throw new IllegalArgumentException("isNull函数至少传入1个参数。");
		}
		Object o = args[0];
		if(o==null){
			return true;
		}else{
			String s = StringUtil.safe2String(o);
			boolean isBlank = StringUtil.isBlank(s);
			return isBlank;
		}
	}

}
