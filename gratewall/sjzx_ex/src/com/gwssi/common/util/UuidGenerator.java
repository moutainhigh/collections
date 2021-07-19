package com.gwssi.common.util;

import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

public class UuidGenerator {
	
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
    

	   /*
     * Demonstraton and self test of class
     */
    public static void main(String args[]) {
//    	/**
//    	 *test getLongId
//    	 */
//    	for (int i=0;i<100;i++){
//    		System.out.println(i+"   getLongId=" + getLongId()); 
//    	}
//    	/**
//    	 * test uuid generate
//    	 */
//        for (int i=0; i< 100; i++) {
//        	UUIDGenerator UUIDgenerator = UUIDGenerator.getInstance();
//        	UUID uuid = UUIDgenerator.generateRandomBasedUUID();
//        	String result = uuid.toString().replaceAll("-", "");
//        	System.out.println(i+"   UUID=" + uuid.toString());        	
//      	    System.out.println(i+"   resu=" + result);
//      	    System.out.println(i+"   size=" + result.length());
//        }
    	System.out.println(UuidGenerator.getRandomPwd(8));
    }
    
    public static String getRandomPwd(int num){
    	if (num>32) {
			return UuidGenerator.getUUID();
		}else {
			return UuidGenerator.getUUID().substring(0, num);
		}
    }
    
}
