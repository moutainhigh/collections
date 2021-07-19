package cn.gwssi.monitor.collect.server;

import java.util.Map;

public interface WLS8Collector
{	
	/**
	 * 测试连接
	 * @return
	 */
	public String ping();

	/**
	 * 采集监控指标数据
	 * @return
	 */
	public Map collect(String url, String userName, String password);
	
}
