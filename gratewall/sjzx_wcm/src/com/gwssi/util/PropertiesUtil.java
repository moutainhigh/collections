package com.gwssi.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;  

/**
 * 读取属性文件工具类。
 * 
 * @author liuhailong
 */
public class PropertiesUtil {    
    
    private Properties props = null;    
    
    private PropertiesUtil(String fileName){    
    	if(StringUtils.isBlank(fileName)){
    		throw new RuntimeException("属性文件名不能为空。");
    	}
    	try {
    		if(fileName.indexOf(".properties")==-1){
    			fileName += ".properties";
    		}
    		if(!fileName.startsWith("/")){
    			fileName = "/" + fileName;
    		}
            props = new Properties();
            InputStream is = getClass().getResourceAsStream(fileName);
            if(is == null){
            	throw new RuntimeException("找不到属性文件："+fileName);
            }
            props.load(new InputStreamReader(is, "UTF-8")); 
        } catch (IOException e) {    
            e.printStackTrace();
        }      
    }    
      
    protected static Map<String,PropertiesUtil> cache = new ConcurrentHashMap<String,PropertiesUtil>() ;
    
    /**
     * 获得实例。
     * @param propertyFileName
     * @return
     */
    public static PropertiesUtil getInstance(String propertyFileName){
    	PropertiesUtil ret = cache.get(propertyFileName);
    	if(ret == null){
    		ret = new PropertiesUtil(propertyFileName);
    		cache.put(propertyFileName, ret);
    	}
    	return ret;
    }
    /**
     * 获取属性值。
     * 
     * @param key
     * @return
     */
    public String getProperty(String key){    
        return props.getProperty(key);    
    } 
}    