package com.gwssi.expression.config;

import com.gwssi.expression.core.lang.ExpressionException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationFactory{
	
  static final Logger logger = LoggerFactory.getLogger(ConfigurationFactory.class);
  static Set<String> configLocationSet = new HashSet<String>();
  static Map<Object, Configuration> confMap = new HashMap<Object, Configuration>();
  static final String defaultPath = "classpath:com/gwssi/common/rodimus/report/expr/config/expression-config.xml";

  public static void addSingleConfigLocation(String configPath, boolean isSkipExists){
    if ((configPath == null) || ("".equals(configPath))){
      throw new IllegalArgumentException("配置文件路径为空");
    }
    
    if ((isSkipExists) && (configLocationSet.contains(configPath))){
      logger.info("没有应用配置，原因：已存在。配置路径：" + configPath);
      return;
    }
    configLocationSet.add(configPath);
    Configuration config = null;
//    if(defaultPath.equals(config)){
    	config = new Configuration();
//    }else{
//    	config = new Configuration(confMap.get("default"));
//    }
    config.applyConfigPath(configPath);
    String configName = (String)config.get("config.name");
    confMap.put(configName, config);
    logger.info("应用表达式配置：" + configName);
  }

  public static Configuration getConfiguration(Object configName){
    Configuration config = (Configuration)confMap.get(configName);
    if (config == null){
      throw new ExpressionException("请在程序或web.xml中配置表达式配置文件：" + configName);
    }
    return config;
  }

  public static String toLogString(){
    Set<Object> localSet = confMap.keySet();
    return String.valueOf(localSet);
  }

  public static void addConfigLocation(String configPath)
    throws Exception{
    if ((configPath == null) || ("".equals(configPath))){
      return;
    }
    
    String[] arrayOfString = configPath.split(";");
    for (int i = 0; i < arrayOfString.length; i++)
    {
      String str = arrayOfString[i].trim();
      if ("".equals(str))
        continue;
      addSingleConfigLocation(str, true);
    }
  }
//  static{
//	  addSingleConfigLocation(defaultPath,true);
//  }
}