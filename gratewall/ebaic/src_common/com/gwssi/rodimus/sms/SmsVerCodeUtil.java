package com.gwssi.rodimus.sms;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gwssi.optimus.core.cache.CacheBlock;
import com.gwssi.optimus.core.cache.CacheElement;
import com.gwssi.rodimus.cache.CacheUtil;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.sms.domain.SmsBusiType;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.UUIDUtil;

/**
 * 短信验证码。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class SmsVerCodeUtil {

	protected final static Logger logger = Logger.getLogger(SmsVerCodeUtil.class);
	protected static final String CACHE_KEY_PREFIX = "sms_ver";
	
	/**
	 * 发送验证码。
	 * TODO 需限制发送间隔。
	 * TODO 内容需通过模板配置。
	 */
	public static void send(String mobile){
		if(StringUtil.isBlank(mobile)){
			throw new RodimusException("移动电话码不能为空。");
		}
		mobile = mobile.trim();
		// 生成验证码
		String verCode = UUIDUtil.getVerCode();
		// 保存到缓存中
		String cacheKey = String.format("%s_%s", CACHE_KEY_PREFIX,mobile); // sms_ver_18600107299
		CacheElement element = new CacheElement(cacheKey, verCode);
		element.setTimeToIdleSeconds(120);//有效期120秒
		
		CacheBlock cb = CacheUtil.getCacheBlock();
		cb.put(element);
		// 发送短信
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("code", verCode);
		SmsUtil.send(mobile, SmsBusiType.VER_CODE, params);
	}
	
	/**
	 * 检查验证码是否正确。
	 * 
	 * @param code
	 */
	public static void verify(String mobile , String verCode){
		if(StringUtil.isBlank(mobile) ){
			throw new RodimusException("移动电话码不能为空。");
		}
		
		if(StringUtil.isBlank(verCode)){
			throw new RodimusException("短信校验码不能为空。");
		}
		// 从缓存中取出验证码
		String cacheKey = String.format("%s_%s", CACHE_KEY_PREFIX,mobile); // sms_ver_18600107299
		String verCodeInServer = StringUtil.safe2String(CacheUtil.get(cacheKey));
		if(StringUtil.isBlank(verCodeInServer)){
			throw new RodimusException("短信验证码已经失效，请重新点击“获取验证码”。");
		}
		if(!verCodeInServer.equals(verCode)){
			throw new RodimusException("您输入的短信验证码不正确。");
		}
		// 验证通过，清除缓存
		CacheUtil.put(cacheKey, null);
	}
}
