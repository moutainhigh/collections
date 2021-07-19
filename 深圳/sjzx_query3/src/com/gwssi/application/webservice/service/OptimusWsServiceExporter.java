package com.gwssi.application.webservice.service;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.remoting.jaxws.AbstractJaxWsServiceExporter;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

/**
 * 重写AbstractJaxWsServiceExporter将@WebService发布成服务
 * AbstractJaxWsServiceExporter类自动搜索加载@WebService注解但是由于使用AOP，使其加载的是bean的代理类发现不了@WebService注解
 * 
 * @author lizheng
 * 
 */
public class OptimusWsServiceExporter extends AbstractJaxWsServiceExporter {

	// 日志
	protected final Log logger = LogFactory.getLog(getClass());

	public OptimusWsServiceExporter() {
		port = 8888;
		backlog = -1;
		shutdownDelay = 0;
		basePath = "/";
		localServer = false;
	}

	private HttpServer server;

	// 端口
	private int port;

	// 访问地址
	private String hostname;

	private int backlog;

	private int shutdownDelay;

	// 根路径
	private String basePath;

	// Context里的filter
	private List<Filter> filters;

	// 认证
	private Authenticator authenticator;

	// 本地服务是否加载
	private boolean localServer;

	private Map<String, Object> endpointProperties;

	private Executor executor;

	// bean工厂
	private ListableBeanFactory beanFactory;

	// 发布服务集合
	private final Set<Endpoint> publishedEndpoints = new LinkedHashSet<Endpoint>();

	@Override
	public void afterPropertiesSet() throws Exception {

		if (server == null) {
			// 组装访问地址和端口
			InetSocketAddress address = (hostname == null ? new InetSocketAddress(
					port) : new InetSocketAddress(hostname, port));

			// 创建并启动HttpServer
			server = HttpServer.create(address, backlog);
			server.start();

			// 设置本地已启动
			localServer = true;

			logger.info("Start http server success,hostname is :" + hostname
					+ ";and port is :" + port);
		}

		// 实例化beanNames集合
		Set<String> beanNames = new LinkedHashSet<String>(
				this.beanFactory.getBeanDefinitionCount());

		// 取出bean工厂里加载的所有bean
		beanNames.addAll(Arrays.asList(this.beanFactory
				.getBeanDefinitionNames()));
		if (this.beanFactory instanceof ConfigurableBeanFactory) {
			beanNames.addAll(Arrays
					.asList(((ConfigurableBeanFactory) this.beanFactory)
							.getSingletonNames()));
		}

		// 循环每个bean，查看是否有@WebService注解
		for (String beanName : beanNames) {
			try {
				Class<?> type = this.beanFactory.getType(beanName);
				if (type != null && !type.isInterface()) {
					WebService wsAnnotation = this.beanFactory
							.findAnnotationOnBean(beanName, WebService.class);
					WebServiceProvider wsProviderAnnotation = this.beanFactory
							.findAnnotationOnBean(beanName,
									WebServiceProvider.class);
					// 当bean里有@WebService注解时
					if (wsAnnotation != null || wsProviderAnnotation != null) {

						// 改成获取spring中的对象（此处需要优化）
						Object proxy = this.beanFactory.getBean(beanName);
						Object originalObject = AopUtils.getTargetClass(proxy)
								.newInstance();

						if (wsAnnotation != null
								|| wsProviderAnnotation != null) {
							// 将bean对象End point
							Endpoint endpoint = createEndpoint(originalObject);
							if (this.endpointProperties != null) {
								endpoint.setProperties(this.endpointProperties);
							} else if (this.executor != null) {
								endpoint.setExecutor(this.executor);
							} else if (wsAnnotation != null) {
								publishEndpoint(endpoint, wsAnnotation);// 发布服务
							} else {
								publishEndpoint(endpoint, wsProviderAnnotation);
							}
							this.publishedEndpoints.add(endpoint);
						}
					}
				}
			} catch (CannotLoadBeanClassException ex) {
				logger.error("bean：" + ex.getBeanName() + "在发布服务时出错，详细信息为"
						+ ex.getMessage());
			}
		}
	}

	@Override
	protected void publishEndpoint(Endpoint endpoint, WebService annotation) {
		logger.info("发现WebService注解：" + annotation.serviceName());
		endpoint.publish(buildHttpContext(annotation.serviceName(),
				annotation.portName()));
	}

	@Override
	protected void publishEndpoint(Endpoint endpoint,
			WebServiceProvider annotation) {
		logger.info("发现WebServiceProvider注解：" + annotation.serviceName());
		endpoint.publish(buildHttpContext(annotation.serviceName(),
				annotation.portName()));
	}

	/**
	 * 根据访问全路径创建HttpContext
	 * 
	 * @param serviceName
	 * @param portName
	 * @return HttpContext
	 */
	protected HttpContext buildHttpContext(String serviceName, String portName) {
		// 获取访问全路径
		String fullPath = calculateEndpointPath(serviceName, portName);
		logger.info("Listen http context at full path " + fullPath);

		// 创建httpContext
		HttpContext httpContext = server.createContext(fullPath);
		if (filters != null)
			httpContext.getFilters().addAll(filters);
		if (authenticator != null)
			httpContext.setAuthenticator(authenticator);

		return httpContext;
	}

	/**
	 * 拼访问路径
	 * 
	 * @param serviceName
	 * @param portName
	 * @return 访问URL
	 */
	protected String calculateEndpointPath(String serviceName, String portName) {
		if (null == portName) {
			return (new StringBuilder(String.valueOf(basePath))).append(
					serviceName).toString();
		}
		return (new StringBuilder(String.valueOf(basePath)))
				.append(serviceName).append("/").append(portName).toString();
	}

	@Override
	public void destroy() {
		super.destroy();
		if (localServer) {
			logger.info("Stopping HttpServer");
			server.stop(shutdownDelay);
		}
	}

	/* server */
	public void setServer(HttpServer server) {
		this.server = server;
	}

	/* port */
	public void setPort(int port) {
		this.port = port;
	}

	/* hostname */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/* backlog */
	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}

	/* shutdownDelay */
	public void setShutdownDelay(int shutdownDelay) {
		this.shutdownDelay = shutdownDelay;
	}

	/* basePath */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/* filters */
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	/* authenticator */
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	/* beanFactory */
	public void setBeanFactory(BeanFactory beanFactory) {
		if (!(beanFactory instanceof ListableBeanFactory)) {
			throw new IllegalStateException(getClass().getSimpleName()
					+ " requires a ListableBeanFactory");
		}
		this.beanFactory = (ListableBeanFactory) beanFactory;
	}

}