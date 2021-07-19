package com.gwssi.common.sysconfig;

public interface SystemParamsConfig {
	
	/**
	 * 通过key获取配置值。
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key);
	
	/**
	 * 从数据库重新加载配置信息。
	 * 适用于缓存的情况。
	 * 
	 */
	public void reload();
	
	/**
	 * 从数据库重新加载制定类型的配置信息。
	 * 适用于缓存的情况。
	 * 
	 * @param configTypeCode 类型code
	 */
	public void reload(String configTypeCode);
}
