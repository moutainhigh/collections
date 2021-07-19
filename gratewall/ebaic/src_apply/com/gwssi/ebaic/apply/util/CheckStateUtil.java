package com.gwssi.ebaic.apply.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
/**
 * 检查身份证号、用户认证、手机验证状态。
 * @author chaiyoubing
 *
 */
public class CheckStateUtil {
	/**
	 * @param params
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public static void CheckIdCardState(Map<String, Object> ret) throws ServletException, IOException{
		String idCardState =(String)ret.get("idCardState");
		if(!"0".equals(idCardState)){//不合法
			ret.put("Cresult","idCardFail");
		}
	}
	
	/**
	 * @param params
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public static void CheckIdentityState(Map<String, Object> ret) throws ServletException, IOException{
		String identityState =(String)ret.get("identityState");
		if(!"1".equals(identityState)){//身份核查状态（0或空：未认证；1-已经认证，2-未通过，3-过期失效）
			ret.put("Iresult", "identityFail");
		}
	}
	
	/**
	 * @param params
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public static void CheckMobState(Map<String, Object> ret) throws ServletException, IOException{
		String mobState =(String)ret.get("mobState");
		if(!"1".equals(mobState)){//移动电话码是否校验（1：已经校验，-1：校验未通过，空：未校验）
			ret.put("Mresult", "mobFail");
		}
	}
	
}
