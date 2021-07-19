package com.gwssi.common.rodimus.report.expr.func;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.gwssi.common.rodimus.report.expr.ExprUtil;
import com.gwssi.common.rodimus.report.util.StringUtil;

/**
 * @author liuhailong
 */
public class GetVar extends BaseFunction {
	
	protected final static Logger logger = Logger.getLogger(GetVar.class);
	
	
	public Object run(Object... args) {
		
		if(args.length<1){
			throw new IllegalArgumentException("GetVar函数至少需要传入一个参数。");
		}
		String nestedPropertyName = StringUtil.safe2String(args[0]);
		Object[] params = new String[args.length];
		for(int i=1;i<args.length;++i){
			params[i-1] = args[i];
		}
		nestedPropertyName = String.format(nestedPropertyName, params);
		
		Object var = ExprUtil.getVarFromContext("var");
		if(var==null){
			return null;
		}
		try {
			Object ret = PropertyUtils.getNestedProperty(var, nestedPropertyName);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
