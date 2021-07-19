package com.gwssi.rodimus.validate.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import com.gwssi.rodimus.exception.ValidateException;

/**
 * 后台校验方法
 * @author liuhailong
 */
public class Assert {
	
	public static void notNull(AbsDaoBussinessObject bo,String msg) {
		if(bo==null){
			throw new ValidateException(msg+"不能为空。");
		}
	}
	
	public static void notBlank(String field,String msg) {
		if(StringUtils.isBlank(field)){
			throw new ValidateException(msg+"不能为空。");
		}
	}
	
	public static void notNull(List<?> list,String msg) {
		if(list==null || list.isEmpty()){
			throw new ValidateException(msg+"不能为空。");
		}
	}
	
	public static void notNull(Map<?, ?> map, String msg) {
		if(map == null || map.isEmpty()){
			throw new ValidateException(msg+"不能为空。");
		}
	}
	
	public static void stringEqual(String field1,String field2,String msg) {
		if(field1==null || field2==null || !field1.equals(field2)){
			throw new ValidateException(msg);
		}
	}
	
	/**
	 * 小于等于。
	 * @param value
	 * @param compareTo
	 * @param fieldName
	 * @
	 */
	public static void le(BigDecimal value,BigDecimal compareTo,String fieldName) {
		if(null==value){
			throw new ValidateException(fieldName+"不能为空。");
		}
		if(compareTo==null){
			return ;
		}
		if(value.compareTo(compareTo)>0){
			throw new ValidateException(String.format("%s 不能大于 %s 。", fieldName,compareTo.doubleValue()));
		}
	}
	
	/**
	 * 大于等于。
	 * @param value
	 * @param compareTo
	 * @param fieldName
	 * @
	 */
	public static void ge(BigDecimal value,BigDecimal compareTo,String fieldName) {
		if(null==value){
			throw new ValidateException(fieldName+"不能为空。");
		}
		if(compareTo==null){
			return ;
		}
		if(value.compareTo(compareTo)<0){
			throw new ValidateException(String.format("%s 不能小于 %s 。", fieldName,compareTo.doubleValue()));
		}
	}
	
	public static void yesNoString(String val,String filedName) {
		if("1".equals(val) || "0".equals(val)){
			return ;
		}else{
			throw new ValidateException(filedName+"不能为空。");
		}
	}
}
