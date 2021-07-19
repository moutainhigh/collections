package com.gwssi.rodimus.expr.func;

import java.util.List;
import java.util.Map;

import com.gwssi.expression.core.lang.UnsupportedArgumentException;
import com.gwssi.rodimus.util.StringUtil;

/**
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class GetColumnValues extends BaseExprFunction {

	@Override
	public Object run(Object... args) {
		if ((args == null) || (args.length < 2) || args[0] == null || args[1] == null ) {
			throw new UnsupportedArgumentException("GetColumn", args);
		}
		if(!(args[0] instanceof List)){
			throw new UnsupportedArgumentException("GetColumn第1个参数必须是List类型。");
		}
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = (List<Map<String,Object>>)args[0] ;

		String columnName = StringUtil.safe2String(args[1]) ;
		if(StringUtil.isBlank(columnName)){
			throw new UnsupportedArgumentException("GetColumn第2个参数不能为空。");
		}
		String splitChar = "";
		if(args.length>2){
			splitChar  = StringUtil.safe2String(args[2]) ;
		}
		if(StringUtil.isBlank(splitChar)){
			splitChar = "、";
		}
		StringBuffer sb = new StringBuffer();
		boolean first = true ;
		for(Map<String,Object> row : list){
			String columnValue = StringUtil.safe2String(row.get(columnName));
			if(StringUtil.isBlank(columnValue)){
				continue ;
			}
			if(!first){
				sb.append(splitChar);
			}else{
				first = false;
			}
			sb.append(columnValue);
		}
		return sb.toString();
	}

}
