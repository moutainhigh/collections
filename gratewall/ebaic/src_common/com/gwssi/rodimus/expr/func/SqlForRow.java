package com.gwssi.rodimus.expr.func;

import java.util.HashMap;
import java.util.Map;

import com.gwssi.expression.core.lang.UnsupportedArgumentException;
import com.gwssi.rodimus.exception.ExprException;

public class SqlForRow extends BaseExprFunction {

	@Override
	public Object run(Object... args) {
		if ((args == null) || (args.length < 1) || (args[0] == null)) {
			throw new UnsupportedArgumentException("SqlForRow", args);
		}
		Map<String, Object> ret = null;
		try {
			ret = FuncUtil.queryForRow(args);
		} catch (Exception e) {
			logger.error("在表达式中执行SQL出错：" + e.getMessage());
			throw new ExprException(e.getMessage(),e);
		}
		if (ret == null) {
			ret = new HashMap<String, Object>();
		}
		return ret;
	}

}
