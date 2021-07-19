package cn.gwssi.monitor.collect.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.naming.Context;
import javax.naming.NamingException;

import sun.management.ManagementFactory;
import weblogic.jndi.Environment;
import weblogic.management.MBeanHome;
import weblogic.management.configuration.JDBCConnectionPoolMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.runtime.JDBCConnectionPoolRuntimeMBean;

import com.sun.management.OperatingSystemMXBean;

public class WLS8Collect
{

	public synchronized static Map collect(String url, String userName,
			String password) throws MalformedObjectNameException,
			NullPointerException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException
	{
		Map<String, Serializable> map = new HashMap();
		ArrayList errorList = new ArrayList();

		Environment env = new Environment();
		env.setProviderUrl(url);
		env.setSecurityPrincipal(userName);
		env.setSecurityCredentials(password);
		Context ctx;
		MBeanHome home = null;
		try {
			ctx = env.getInitialContext();
			home = (MBeanHome) ctx.lookup(MBeanHome.ADMIN_JNDI_NAME);
		} catch (NamingException e) {
			errorList.add("999999");
			getStrFromList(errorList);
			map.put("error", getStrFromList(errorList));
			return map;
		}

		map.put("domain", home.getActiveDomain().getName());

		MBeanServer server = home.getMBeanServer();

		Set ss = home.getMBeansByType("Server");
		Iterator itt = ss.iterator();
		Long threadPoolSize = new Long(0l);
		String serverName = "myserver";
		while (itt.hasNext()) {
			ServerMBean mbean = (ServerMBean) itt.next();
			serverName = mbean.getName();
			/*
			 * System.out.println(" {ThreadPoolSize}  ThreadPoolSize is " +
			 * mbean.getThreadPoolSize() ); threadPoolSize = new Long(
			 * mbean.getThreadPoolSize() );
			 */
		}
		map.put("threadPoolSize", threadPoolSize);
		map.put("server", serverName);
		// 获取JDBC POOL 配置信息
		Set connPool = home.getMBeansByType("JDBCConnectionPool");
		Iterator it1 = connPool.iterator();

		ArrayList connList = new ArrayList();
		while (it1.hasNext()) {
			JDBCConnectionPoolMBean jpm = (JDBCConnectionPoolMBean) it1.next();
			System.out.println("{conn pool config} " + jpm.getName()
					+ "   MaxCapacity is " + jpm.getMaxCapacity());
			HashMap mapp = new HashMap();
			mapp.put("connPoolName", jpm.getName());
			mapp.put("connPoolSize", new Long(jpm.getMaxCapacity()));
			connList.add(mapp);
		}

		// 获取JDBC POOL 运行信息
		Set connPoolRuntime = null;
		try {
			connPoolRuntime = server.queryMBeans(new ObjectName(
					"*:Type=JDBCConnectionPoolRuntime,*"), null);
		} catch (MalformedObjectNameException e1) {
			errorList.add("100101");
		}
		for (Iterator itt2 = connPoolRuntime.iterator(); itt2.hasNext();) {
			Object o = itt2.next();
			ObjectInstance m = (ObjectInstance) o;
			try {
				Integer ActiveConnectionsCurrentCount = (Integer) server
						.getAttribute(m.getObjectName(),
								"ActiveConnectionsCurrentCount");
				Integer LeakedConnectionCount = (Integer) server.getAttribute(
						m.getObjectName(), "LeakedConnectionCount");
				String name2 = (String) server.getAttribute(m.getObjectName(),
						"Name");
				String state = (String) server.getAttribute(m.getObjectName(),
						"State");

				if (connList.size() > 0) {
					for (int n = 0; n < connList.size(); n++) {
						HashMap cp = (HashMap) connList.get(n);
						String name = (String) cp.get("connPoolName");

						if (name != null && !name.equals("")
								&& name.equals(name2)) {
							cp.put("activeConn", new Long(
									ActiveConnectionsCurrentCount.intValue()));
							cp.put("leakedConn",
									new Long(LeakedConnectionCount.intValue()));
							cp.put("state", state);
							break;
						}
					}
				}
				System.out.println("{conn pool current}---->> "
						+ server.getAttribute(m.getObjectName(), "Name")
						+ " ActiveConnectionsCurrentCount is "
						+ server.getAttribute(m.getObjectName(),
								"ActiveConnectionsCurrentCount")
						+ ";  LeakedConnectionCount is "
						+ server.getAttribute(m.getObjectName(),
								"LeakedConnectionCount"));
			} catch (Exception e) {

				errorList.add("100102");

			}
		}

		try {
			if (connList != null && connList.size() > 0) {
				for (int j = 0; j < connList.size(); j++) {
					HashMap map2 = (HashMap) connList.get(j);
					String poolName = (String) map2.get("connPoolName");
					JDBCConnectionPoolRuntimeMBean mbean = (JDBCConnectionPoolRuntimeMBean) home
							.getRuntimeMBean(poolName,
									"JDBCConnectionPoolRuntime");
					System.out.println("poolName " + mbean.getName());
					// System.out.println("testPool " + mbean.testPool());
					System.out.println("getConnectionsTotalCount "
							+ mbean.getConnectionsTotalCount());
					// System.out.println("testPool " + mbean.testPool());
					System.out.println("getCurrCapacity "
							+ mbean.getCurrCapacity());
					System.out.println("getActiveConnectionsCurrentCount "
							+ mbean.getActiveConnectionsCurrentCount());
					System.out.println("getLeakedConnectionCount "
							+ mbean.getLeakedConnectionCount());
					System.out.println("getWaitingForConnectionCurrentCount "
							+ mbean.getWaitingForConnectionCurrentCount());
					System.out.println("getMaxCapacity "
							+ mbean.getMaxCapacity());
					System.out.println("getNumAvailable "
							+ mbean.getNumAvailable());
					System.out.println("getNumUnavailable "
							+ mbean.getNumUnavailable());
					map2.put("activeConn",
							new Long(mbean.getActiveConnectionsCurrentCount()));
					map2.put("leakedConn",
							new Long(mbean.getLeakedConnectionCount()));
					map2.put("availConn", new Long(mbean.getNumAvailable()));
					map2.put("state", mbean.getState());
					System.out.println(" getState" + mbean.getState());
				}
			}

		} catch (InstanceNotFoundException e1) {

		}

		map.put("connPool", connList);

		// 获取JVM 内存信息
		Set jvm = null;
		try {
			jvm = server.queryMBeans(new ObjectName("*:Type=JVMRuntime,*"),
					null);
		} catch (MalformedObjectNameException e) {

			errorList.add("100103");
		}
		for (Iterator itt2 = jvm.iterator(); itt2.hasNext();) {
			Object o = itt2.next();
			ObjectInstance m = (ObjectInstance) o;
			try {
				Long HeapFreeCurrent = (Long) server.getAttribute(
						m.getObjectName(), "HeapFreeCurrent");
				Long HeapSizeCurrent = (Long) server.getAttribute(
						m.getObjectName(), "HeapSizeCurrent");
				map.put("heapFreeCurrent", HeapFreeCurrent);
				map.put("heapSizeCurrent", HeapSizeCurrent);

				System.out.println("{Heap}---->> "
						+ server.getAttribute(m.getObjectName(), "Name")
						+ " HeapFreeCurrent is "
						+ server.getAttribute(m.getObjectName(),
								"HeapFreeCurrent")
						+ ";  HeapSizeCurrent is "
						+ server.getAttribute(m.getObjectName(),
								"HeapSizeCurrent"));

			} catch (Exception e) {

				errorList.add("100104");
				getStrFromList(errorList);
				map.put("error", getStrFromList(errorList));
				return map;

			}
		}

		// 获取线程队列信息
		try {
			Set queues = server.queryMBeans(new ObjectName(
					"*:Type=ExecuteQueueRuntime,*"), null);
			for (Iterator itt2 = queues.iterator(); itt2.hasNext();) {
				Object o = itt2.next();
				ObjectInstance m = (ObjectInstance) o;
				System.err.println("{queues}---->> "
						+ server.getAttribute(m.getObjectName(), "Name")
						+ " ExecuteThreadCurrentIdleCount is "
						+ server.getAttribute(m.getObjectName(),
								"ExecuteThreadCurrentIdleCount")
						+ " PendingRequestCurrentCount is "
						+ server.getAttribute(m.getObjectName(),
								"PendingRequestCurrentCount"));
			}
		} catch (Exception e) {
			errorList.add("100105");
			getStrFromList(errorList);
			map.put("error", getStrFromList(errorList));
			return map;
		}

		// 获取套接字信息
		Set runtime = server.queryMBeans(new ObjectName(
				"*:Type=ServerRuntime,*"), null);
		for (Iterator itt2 = runtime.iterator(); itt2.hasNext();) {
			Object o = itt2.next();
			ObjectInstance m = (ObjectInstance) o;
			System.err.println("{runtime}---->> "
					+ server.getAttribute(m.getObjectName(), "Name")
					+ " OpenSocketsCurrentCount is "
					+ server.getAttribute(m.getObjectName(),
							"OpenSocketsCurrentCount"));

		}

		env = null;
		home = null;

		/*
		 * 获取主机内存信息
		 */
		int kb = 1024;
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();
		// 总的物理内存
		long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
		// 剩余的物理内存
		// long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / kb;
		// 已使用的物理内存
		long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb
				.getFreePhysicalMemorySize()) / kb;
		map.put("memoryInfo", (usedMemory*100/totalMemorySize));
		
		try {
			ctx.close();
		} catch (NamingException e) {
			errorList.add("100200");
			getStrFromList(errorList);
			map.put("error", getStrFromList(errorList));
		}
		
		errorList.add("000000");
		// getStrFromList(errorList);
		map.put("error", getStrFromList(errorList));
		return map;
	}

	private synchronized static String getStrFromList(ArrayList list)
	{
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				if (j == 0) {
					sb.append(list.get(j));
				} else {
					sb.append("," + list.get(j));
				}
			}
		}
		return sb.toString();
	}

}
