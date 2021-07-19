package com.gwssi.common.rodimus.report.expr.func;

import org.apache.log4j.Logger;

public class Get extends BaseFunction {
	protected final static Logger logger = Logger.getLogger(BaseFunction.class);
	
	@Override
	public Object run(Object... args) {
		logger.debug(args.length);
		return null;
	}

}
