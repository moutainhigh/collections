package com.gwssi.common.rodimus.report.expr.func;

import org.apache.log4j.Logger;

public class StrJoin extends BaseFunction {
	protected final static Logger logger = Logger.getLogger(BaseFunction.class);
	
	@Override
	public Object run(Object... args) {
		if(args==null || args.length==0){
			return "";
		}
		StringBuffer ret = new StringBuffer();
		for(Object s : args){
			ret.append(s.toString());
		}
		return ret.toString();
	}

}
