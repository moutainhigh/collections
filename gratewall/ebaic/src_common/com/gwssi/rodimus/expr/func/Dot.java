package com.gwssi.rodimus.expr.func;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gwssi.rodimus.util.StringUtil;


/**
 * @author liuhailong
 */
public class Dot extends BaseExprFunction {
	
	protected final static Logger logger = Logger.getLogger(Dot.class);
	
	
	public Object run(Object... args) {
		
		if(args.length!=2){
			throw new IllegalArgumentException("'.'操作符需传入两个参数。");
		}
		Object object = args[0];
		if(object==null){
			return null;
		}
		

	    //FunctionHelper.getNamingObjectNodeLiteral(context, args[1]);
	      
		String propName = StringUtil.safe2String(args[1]);
		if(StringUtils.isEmpty(propName)){
			throw new IllegalArgumentException("'.'操作符右侧属性名不能为空。");
		}
		propName = propName.trim();
		
		if(object instanceof Map){
			@SuppressWarnings("rawtypes")
			Map objectMap = (Map)object;
			Object ret = objectMap.get(propName);
			ret = (ret==null)?"":ret;
			return ret;
		}
		
		if(propName.indexOf(".")>-1){
			try {
				Object ret = PropertyUtils.getNestedProperty(object, propName);
				ret = (ret==null)?"":ret;
				return ret;
			} catch (IllegalAccessException e){
				e.printStackTrace();
				return "";
			}catch (InvocationTargetException e){
				e.printStackTrace();
				return "";
			}catch (NoSuchMethodException e) {
				e.printStackTrace();
				return "";
			}
		}
		
		try {
			Object ret = PropertyUtils.getProperty(object, propName);
			ret = (ret==null)?"":ret;
			return ret;
		} catch (Exception  e) {
			e.printStackTrace();
			return "";
		}
	}

}
