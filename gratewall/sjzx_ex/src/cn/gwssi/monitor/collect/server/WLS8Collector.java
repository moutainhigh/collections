package cn.gwssi.monitor.collect.server;

import java.util.Map;

public interface WLS8Collector
{	
	/**
	 * ��������
	 * @return
	 */
	public String ping();

	/**
	 * �ɼ����ָ������
	 * @return
	 */
	public Map collect(String url, String userName, String password);
	
}
