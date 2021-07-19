package com.gwssi.common.delivery.remote.ems.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public enum MessageRetCode {
	
	OK("0","处理成功"),NOT_EXISTS("-1","记录不存在"),SYS_BUSY("-2","系统忙"),
	SYS_ERROR("-3","系统故障"),
	MESSAGE_FORMAT_ERROR("-4","报文格式错误"),
	DATA_EXISTS_ALLREADY("-5","记录已存在，不能重复提交"),
	ILL_MESSAGE("-6","非法报文"),SIGN_ERROR("-7","非法签名"),OTHER_ERROR("-99","其他错误");
	
	private String code;
	private String msg;
	
    private MessageRetCode(String code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }
    public String getMsg(){
    	return msg;
    }

    
    private static final Map<String,MessageRetCode> lookup = new HashMap<String,MessageRetCode>(); 
    static { 
	    for(MessageRetCode s : EnumSet.allOf(MessageRetCode.class)){
	    	lookup.put(s.getCode(), s);  
	    }  
    }  
    public static MessageRetCode get(String code) {  
    	return lookup.get(code);
    }  
}
