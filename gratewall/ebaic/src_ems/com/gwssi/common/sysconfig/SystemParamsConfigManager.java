package com.gwssi.common.sysconfig;

import com.gwssi.common.sysconfig.impl.SystemParamsConfigDatabaseImpl;

/**
 *<pre>系统配置参数读取工具类，用于取代properties配置文件。
 * 配置信息保存在  system_params_config 表。
 * 用法：
 * String configValue = SystemParamsConfigManager
 *            .get("ems.is_rmi_directly");</pre>
 * @author liuhailong
 */
public class SystemParamsConfigManager {
	
	static SystemParamsConfig instance = new SystemParamsConfigDatabaseImpl();
	
	/**
	 * 
	 * 通过key获取配置值。
	 * 
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key){
		return instance.get(key);
	}
}
