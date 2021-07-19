package com.gwssi.rodimus.util;

import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;
/**
 * 
 * @author zhuyang
 */
public class UUIDUtil {   
	
	public static void main(String[] args){
		System.out.println(getVerCode());
	}
	
	/**
	 * 得到四位数字验证码。
	 * 
	 * @return
	 */
	public static String getVerCode(){
		double d = Math.random();
		long l = (long) (d * 10000);
		String s = l + "";
		if(s.length()<4){
			int len = 4 - s.length();
			for(int i=0;i<len;++i){
				s = "0" + s;
			}
		}
  	    return s;
	}
	
	/**
	 * 生成32位UUID，返回类型为String（32）
	 * @return
	 */
	public static String getUUID(){
		UUIDGenerator UUIDgenerator = UUIDGenerator.getInstance();
    	UUID uuid = UUIDgenerator.generateRandomBasedUUID();
    	String result = uuid.toString().replaceAll("-", "");
  	    return result;
	}
	
	static long currentTimeMillis = System.currentTimeMillis();
	/**
     * 获得long型内码
     * @return long型唯一内码
     */
    static synchronized public long getLongId(){
        return currentTimeMillis++;
    }
    /**
     * 
     * @param num
     * @return
     */
    public static String getRandomPwd(int num){
    	if (num>32) {
			return UUIDUtil.getUUID();
		}else {
			return UUIDUtil.getUUID().substring(0, num);
		}
    }
  
}  

