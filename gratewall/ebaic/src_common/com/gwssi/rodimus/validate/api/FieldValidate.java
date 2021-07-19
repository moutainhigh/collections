package com.gwssi.rodimus.validate.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.gwssi.optimus.core.cache.dictionary.DicData;
import com.gwssi.optimus.core.cache.dictionary.DictionaryManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.rodimus.exception.ValidateException;
import com.gwssi.rodimus.util.StringUtil;
/**
 * 单字段校验。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class FieldValidate {
	
	
	static {
		init();
	}

	public static void init(){
		valMethods = new HashMap<String,Integer>();
		methodAccess = MethodAccess.get(FieldValidate.class);
		String[] methodNames = methodAccess.getMethodNames();
		for(int i=0;i<methodNames.length;i++){
			int index = methodAccess.getIndex(methodNames[i]);
			valMethods.put(methodNames[i],Integer.valueOf(index));
		}
	}
	private static MethodAccess methodAccess ;
	private static Map<String,Integer> valMethods ;
	
	/**
	 * 
	 * @param type 校验类型，如：eq、maxLength等
	 * @param label 中文标识
	 * @param value 待校验的数值
	 * @param params
	 */
	public void validate(String type,String label,String value,String...params){
		
		List<Object> maParams = new ArrayList<Object>();
		maParams.add(label);
		maParams.add(value);
		if(params!=null && params.length>0){
			for(Object o : params){
				maParams.add(o);
			}
		}
		if(valMethods.get(type)==null){
			throw new ValidateException(String.format("【%s】 rule is currently not supported. ", type));
		}
		int index  = valMethods.get(type);
		methodAccess.invoke(this, index, maParams.toArray());
	}
	
	public static void main(String[] args){
		Val.field.validate("maxLength", "姓名", "刘蒂芬", "3");
		System.out.println(Val.getMsg());
		
	}
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 整数
	 */
	private final String INTEGER = "^-?(([1-9]\\d*$)|0)";
	public void numberInt(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, INTEGER)) {
			Val.getMsg().add(label, value, "需是整数");
		}
	}
	/**
	 * 正整数
	 */
	private final String INTEGER_NEGATIVE = "^[1-9]\\d*|0$";
	public void numberPlusInt(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, INTEGER_NEGATIVE)) {
			Val.getMsg().add(label, value, "需是正整数");
		}
	}
	
	/**
	 * 负整数
	 */
	private final String INTEGER_POSITIVE = "^-[1-9]\\d*|0$";
	public void isIntegerLeZero(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, INTEGER_POSITIVE)) {
			Val.getMsg().add(label, value, "需是负整数");
		}
	}
	
	/**
	 * Double
	 */
	private final String DOUBLE = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
	public void number(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, DOUBLE)) {
			Val.getMsg().add(label, value, "需是数字");
		}
	}
	
	/**
	 * 正Double
	 */
	private final String DOUBLE_NEGATIVE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
	public void isNumberGeZero(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, DOUBLE_NEGATIVE)) {
			Val.getMsg().add(label, value, "需是大于等于0的数字");
		}
	}
	
	/**
	 * 负Double
	 */
	private final String DOUBLE_POSITIVE = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";
	public void isNumberLeZero(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, DOUBLE_POSITIVE)) {
			Val.getMsg().add(label, value, "需是小于等于0的数字");
		}
	}
	
	/**
	 * 匹配由数字、26个英文字母或者下划线组成的字符串 ^\w+$
	 */
	//private final String STR_ENG_NUM_ = "^\\w+$";
	
	/**
	 * 匹配由数字和26个英文字母组成的字符串 ^[A-Za-z0-9]+$
	 */
	private final String STR_ENG_NUM = "^[A-Za-z0-9]+";
	public void number$english(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, STR_ENG_NUM)) {
			Val.getMsg().add(label, value, "只能包含字母或数字");
		}
	}
	
	/**
	 * 匹配由26个英文字母组成的字符串 ^[A-Za-z]+$
	 */
	private final String STR_ENG = "^[A-Za-z]+$";
	public void english(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, STR_ENG)) {
			Val.getMsg().add(label, value, "只能包含字母");
		}
	}
	/**
	 * 过滤特殊字符串正则 regEx=
	 * "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	 */
//	private final String STR_SPECIAL = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

	/***
	 * 日期正则 支持： YYYY-MM-DD YYYY/MM/DD YYYY_MM_DD YYYYMMDD YYYY.MM.DD的形式
	 */
	private final String DATE_ALL = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(10|12|0?[13578])([-\\/\\._]?)(3[01]|[12][0-9]|0?[1-9])$)"
			+ "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(11|0?[469])([-\\/\\._]?)(30|[12][0-9]|0?[1-9])$)"
			+ "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(0?2)([-\\/\\._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([3579][26]00)"
			+ "([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)"
			+ "|(^([1][89][0][48])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][0][48])([-\\/\\._]?)"
			+ "(0?2)([-\\/\\._]?)(29)$)"
			+ "|(^([1][89][2468][048])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._]?)(0?2)"
			+ "([-\\/\\._]?)(29)$)|(^([1][89][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|"
			+ "(^([2-9][0-9][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$))";
	
	public void isDate(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, DATE_ALL)) {
			Val.getMsg().add(label, value, "日期格式不正确");
		}
	}
	
	/**
	 * yyyyMMddHHmmss
	 */
//	private final String DATE_FORMAT = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

	/**
	 * URL正则表达式 匹配 http www ftp
	 */
	private final String URL = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?"
			+ "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*"
			+ "(\\w*:)*(\\w*\\+)*(\\w*\\.)*"
			+ "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";
	public void url(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, URL)) {
			Val.getMsg().add(label, value, "URL格式不正确");
		}
	}
	

//	/**
//	 * 机构代码
//	 */
//	private final String JIGOU_CODE = "^[A-Z0-9]{8}-[A-Z0-9]$";

//	/**
//	 * 数字
//	 */
//	private final String bigDecimal = "^\\d+(\\.\\d+)?$";
//	/**
//	 * 匹配数字组成的字符串 ^[0-9]+$
//	 */
//	private final String STR_NUM = "^[0-9]+$";
	

	/**
	 * 匹配是否符合正则表达式pattern 匹配返回true
	 * 
	 * @param str
	 *            匹配的字符串
	 * @param pattern
	 *            匹配模式
	 * @return boolean
	 */
	private boolean Regular(String str, String pattern) {
		if (null == str || str.trim().length() <= 0)
			return false;
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();
	}
//	/**
//	 * 是否数字。
//	 * 
//	 * @param label
//	 * @param value
//	 */
//	public void isNumber(String label,String value) {
//		if(StringUtil.isBlank(value)){
//			return;
//		}
//		if (!Regular(value, bigDecimal)) {
//			Val.getMsg().add(label, value, "数字格式不正确");
//		}
//	}
	/**
	 * 不能为空。
	 * 
	 * @param label
	 * @param value
	 */
	public void must(String label,String value) {
		if (StringUtil.isBlank(value)) {
			Val.getMsg().add(label, null, "不能为空");
		}
	}
//	/**
//	 * 不能是空字符串。
//	 * 
//	 * @param label
//	 * @param value
//	 */
//	public void notBlank(String label,String value) {
//		if (StringUtil.isBlank(value)) {
//			Val.getMsg().add(label, value, "不能为空");
//		}
//	}
	
	/**
	 * 身份证正则表达式
	 */
	private final String IDCARD = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})"
			+ "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}"
			+ "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";
	/**
	 * 身份证号码。
	 * @param label
	 * @param value
	 */
	public void idcard(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, IDCARD)) {
			Val.getMsg().add(label, value, "身份证号码格式不正确");
		}
	}

	/**
	 * 国内6位邮编
	 */
	private final String POST_CODE = "[0-9]\\d{5}(?!\\d)";
	public void postal(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, POST_CODE)) {
			Val.getMsg().add(label, value, "邮政编码格式不正确");
		}
	}
/**
	 * 移动电话码
	 */
	private final String MOBILE = "^(13[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$";

	public void cellphone(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, MOBILE)) {
			Val.getMsg().add(label, value, "移动电话格式不正确");
		}
	}
	
	/**
	 * 电话号码
	 */
	private final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
	
	public void telephone(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, PHONE)) {
			Val.getMsg().add(label, value, "座机号码格式不正确");
		}
	}
	
	public void isTelOrMobile(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!(Regular(value, PHONE) || Regular(value, MOBILE))) {
			Val.getMsg().add(label, value, "电话号码格式不正确");
		}
	}
	/**
	 * isEmail
	 * 邮件
	 */
	private final String EMAIL = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";
	
	public void email(String label,String value) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (!Regular(value, EMAIL)) {
			Val.getMsg().add(label, value, "电子邮箱地址格式不正确");
		}
	}
	
//	public void isDate(String label,String value){
//		if(StringUtil.isBlank(value)){
//			return;
//		}
//		try {
//			sdf.parse(value);
//		} catch (Exception e) {
//			Val.getMsg().add(label, value, "日期类型格式不正确");
//		}
//	}
	/**
	 * 长度定值校验
	 * length;4;10
	 * @param label
	 * @param value
	 * @param length
	 */
	public void length(String label,String value,String length) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if(StringUtils.isBlank(length)){
			return;
		}
		int min;
		int max;
		if(length.indexOf(";")!=-1){
			String[] notInclude = length.split(";");
			min = StringUtil.str2Int(notInclude[0],0);
			if(min>=value.length()){
				Val.getMsg().add(label, value, "长度必须大于%s个字符", min);
			}
			max = StringUtil.str2Int(notInclude[1],0);
			if(max<=value.length()){
				Val.getMsg().add(label, value, "长度必须小于%s个字符", max);
			}
		}else if(length.indexOf(",")!=-1){
			String[] include = length.split(",");
			min = StringUtil.str2Int(include[0],0);
			if(min>value.length()){
				Val.getMsg().add(label, value, "长度必须大于等于%s个字符", min);
			}
			max = StringUtil.str2Int(include[1],0);
			if(max<value.length()){
				Val.getMsg().add(label, value, "长度必须小于等于%s个字符", max);
			}
		}else{
			int lengthValue = StringUtil.str2Int(length, -1);
			if(lengthValue==-1){
				return ;
			}
			
			if (value.length() != lengthValue) {
				Val.getMsg().add(label, value, "长度必须为%s个字符", length);
			}
		}
		
		
	}
	/**
	 * maxLength:0
	 * 校验字符最大长度。
	 * 
	 * @param value
	 * @param maxLen
	 * @param property
	 * @param label
	 * @param msgList
	 */
	public void maxLength(String label,String value,String maxLength) {
		if(StringUtil.isBlank(value)){
			return;
		}
		int maxLengthValue = StringUtil.str2Int(maxLength, Integer.MAX_VALUE);
		if (value.length() > maxLengthValue) {
			Val.getMsg().add(label, value, "最多%s个字符", maxLength);
		}
	}
	/**
	 * numberLimit:12,5
	 * 校验字段值的精度和标度
	 * @param label
	 * @param precision
	 * @param accuracy
	 * @param value
	 */
	public void numberLimit(String label,String value,String precisionString,String accuracyString) {
		if(StringUtil.isBlank(value)){
			return;
		}
		int precision = StringUtil.str2Int(precisionString, -1);
		int accuracy = StringUtil.str2Int(accuracyString, -1);
		
		int index = value.indexOf(".");
		String valInt = "";
		String valDec = "";
		if(index != -1){
			valInt = value.substring(0, index);
			valDec = value.substring(index+1);
		}else{
			valInt = value;
		}
		if(precision!=-1 && valInt.length() > precision){
			Val.getMsg().add(label, value, "整数部分不能超过%s位", precision);
		} 
				
		if(accuracy!=-1 && valDec.length() > accuracy){
			Val.getMsg().add(label, value, "小数部分不能超过%s位", accuracy);
		}
	}
	/**
	 * 大于
	 * gt:3
	 * @param label
	 * @param value
	 * @param target
	 */
	public void gt(String label,String value,String target) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (value != null && value.compareTo(target) <= 0) {
			Val.getMsg().add(label, value, "需大于%s", target);
		}
	}
	/**
	 * 小于
	 * lt:3
	 * @param label
	 * @param value
	 * @param target
	 */
	public void lt(String label,String value,String target) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (value != null && value.compareTo(target) >= 0) {
			Val.getMsg().add(label, value, "需小于%s", target);
		}
	}
	/**
	 * 大于等于
	 * ge:3
	 * @param label
	 * @param value
	 * @param target
	 */
	public void ge(String label,String value,String target) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if(StringUtil.isBlank(value)){
			return;
		}
		if (value != null && value.compareTo(target) < 0) {
			Val.getMsg().add(label, value, "需大于或等于%s", target);
		}
	}
	/**
	 * 小于等于
	 * le:3
	 * @param label
	 * @param value
	 * @param target
	 */
	public void le(String label,String value,String target) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (value != null && value.compareTo(target) > 0) {
			Val.getMsg().add(label, value, "需小于或等于%s", target);
		}
	}
	/**
	 * 等于
	 * eq:3
	 * @param label
	 * @param value
	 * @param target
	 */
	public void eq(String label,String value,String targetString) {
		if(StringUtil.isBlank(value)){
			return;
		}
		if (value != null && value.compareTo(targetString) != 0) {
			Val.getMsg().add(label, value, "需等于%s", targetString);
		}
	}

	/**
	 * 码表
	 * dictRange:CA16
	 * 
	 * @param value
	 * @param dmbId
	 *            　
	 * @param property
	 * @param label
	 * @param msgList
	 */
	public void dictRange(String label,String value,String dictTypeId) {
		if(StringUtil.isBlank(value)){
			return;
		}
		DicData dict = null;
		try {
			dict = DictionaryManager.getData(dictTypeId);
		} catch (OptimusException e) {
			e.printStackTrace();
			//Val.getMsg().add(label, value, "码表未成功加载：%s。",e.getMessage());
		}
		if (dict == null) {
			Val.getMsg().add(label, value, "不存在码表Key：%s", dictTypeId);
		}
	
		String text = dict.getText(value);
		if (StringUtil.isBlank(text)) {
			Val.getMsg().add(label, value, "只能在码表(%s)范围内取值", dictTypeId);
		}
	}
}
