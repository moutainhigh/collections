package com.gwssi.rodimus.expr.func;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gwssi.expression.core.lang.UnsupportedArgumentException;
import com.gwssi.rodimus.exception.ExprException;

public class SqlForList extends BaseExprFunction {

	@Override
	public Object run(Object... args) {
		if ((args == null) || (args.length < 1) || (args[0] == null)) {
			throw new UnsupportedArgumentException("SqlForRow", args);
		}
		List<Map<String,Object>> ret = null;
		try {
			ret = FuncUtil.queryForList(args);
		} catch (Exception e) {
			logger.error("在表达式中执行SQL出错：" + e.getMessage());
			throw new ExprException(e.getMessage(),e);
		}
		if (ret == null) {
			ret = new ArrayList<Map<String, Object>>();
		}
		return ret;
	}

}
