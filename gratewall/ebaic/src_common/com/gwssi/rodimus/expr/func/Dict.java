package com.gwssi.rodimus.expr.func;

import com.gwssi.expression.core.lang.UnsupportedArgumentException;
import com.gwssi.optimus.core.cache.dictionary.DicData;
import com.gwssi.optimus.core.cache.dictionary.DictionaryManager;
import com.gwssi.rodimus.exception.ExprException;
import com.gwssi.rodimus.util.StringUtil;

public class Dict extends BaseExprFunction {

	@Override
	public Object run(Object... args) {
		if ((args == null) || (args.length < 2) ) {
			throw new UnsupportedArgumentException("Dict参数不正确。", args);
		}
		
		String value = StringUtil.safe2String(args[0]);
		if(StringUtil.isNotBlank(value)){
			return "";
		}
		
		String dmbId = StringUtil.safe2String(args[1]);
		if(StringUtil.isNotBlank(dmbId)){
			return value;
		}
		try {
			DicData dicData = DictionaryManager.getData(dmbId);
			if(dicData==null){
				return value;
			}
			String ret = dicData.getText(value);
			if(StringUtil.isNotBlank(ret)){
				return ret;
			}
			
			Object[] params = new Object[3];
			params[0] = "select d.wb from t_pt_dmsjb d where d.dmb_id = ? and d.dm = ? and d.qy_bj='1' ";
			params[1] = dmbId ;
			params[2] = value ;
			Object retObj = FuncUtil.queryForValue(params);
			ret = StringUtil.safe2String(retObj);
			return ret;
			
		} catch (Exception e) {
			logger.error("在表达式中执行SQL出错：" + e.getMessage());
			throw new ExprException(e.getMessage(),e);
		}
	}

}
