package com.gwssi.optimus.core.common;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**读取文件配置信息表
 * 
 * @description 类功能描述:ConfigManager <br/>
 * @author  ynr <br/>
 * @see  [相关类/方法] <br/>
 * @since  [产品/模块版本] <br/>
 * @mail  9442478@qq.com<br/>
 * @version 1.0.0 <br/>
 */
public class ConfigManager {

    private final static String DEFAULT_CONFIG_FILE = "optimus";
    
    private static ConfigManager instance;
    
    private Map<String, Properties> propertiesCache;
    
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    
    static{
        instance = new ConfigManager();
    }
    
    public ConfigManager(){
        propertiesCache = new ConcurrentHashMap<String, Properties>();
    }
    
    public static ConfigManager getInstance(){
        if(instance==null){
            instance = new ConfigManager();
        }
        return instance;
    }
    
    public static Properties loadProperties(String file){
//        if (getInstance().propertiesCache.containsKey(file)) {
//            return getInstance().propertiesCache.get(file);
//        }
        Properties properties = new Properties();
        String fileName = file + ".properties";
        try {
            properties.load(ConfigManager.class.getResourceAsStream("/"+fileName));
        } catch (Exception e) {
            logger.error("读取配置文件异常，文件名:" + fileName, e);
            return null;
        }
        addProperties(fileName, properties);
        return properties;
    }
    
    public static void addProperties(String file, Properties props){
        synchronized (instance) {
            getInstance().propertiesCache.put(file, props);
        }
    }
    
    public static String getProperty(String key) {
        return getProperty(DEFAULT_CONFIG_FILE, key, "");
    }
    
    public static Properties getProperties(String file) {
        Properties props = null;
        if (getInstance().propertiesCache.containsKey(file)) {
            props = getInstance().propertiesCache.get(file);
        }else{
            props = loadProperties(file);
        }
        return props;
    }
    
    public static String getProperty(String file, String key) {
        return getProperty(file, key, "");
    }
    
    public static boolean hasProperty(String file, String key){
        String value = getProperty(file, key);
        if(value!=null && !"".equals(value)){
            return true;
        }
        return false;
    }
    
    public static String getProperty(String file, String key, String defaultValue) {
        Properties properties = null;
        if (!getInstance().propertiesCache.containsKey(file)) {
            synchronized (instance) {
                if (!instance.propertiesCache.containsKey(file)) {
                    properties = loadProperties(file);
                }
            }
        }else{
            properties = getInstance().propertiesCache.get(file);
        }
        return properties==null? defaultValue : properties.getProperty(key, defaultValue);
    }
    
    public static void setProperty(String file, String key, String value) {
        Properties properties = null;
        if (!getInstance().propertiesCache.containsKey(file)) {
            synchronized (instance) {
                if (!instance.propertiesCache.containsKey(file)) {
                    properties = new Properties();
                    getInstance().propertiesCache.put(file, properties);
                }
            }
        }else{
            properties = getInstance().propertiesCache.get(file);
        }
        properties.setProperty(key, value);
    }
}
