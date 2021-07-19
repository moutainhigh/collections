package cn.gwssi.monitor.collect.server;

import java.util.HashMap;
import java.util.Map;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;


import cn.gwssi.monitor.collect.util.WLS8Collect;

public class WLS8CollectorImpl implements WLS8Collector
{

	@SuppressWarnings("rawtypes")
	public Map collect(String url, String userName, String password)
	{
		Map map=new HashMap();
		try {
			map = WLS8Collect.collect(url, userName, password);
		} catch (MalformedObjectNameException e) {

			e.printStackTrace();
		} catch (AttributeNotFoundException e) {

			e.printStackTrace();
		} catch (InstanceNotFoundException e) {

			e.printStackTrace();
		} catch (NullPointerException e) {

			e.printStackTrace();
		} catch (MBeanException e) {

			e.printStackTrace();
		} catch (ReflectionException e) {

			e.printStackTrace();
		}
		return map;
	}

	public String ping()
	{
		// TODO Auto-generated method stub
		return "000000";
	}

}
