package cn.gwssi.common.resource;

import java.text.SimpleDateFormat;

/**
 * 参数工具类
 * @author xue
 * @version 1.0
 * @since 2016/4/20
 */
public class ServiceConstants {
	public static final String LOG_HAND_TOPIC="logHandTopic";//日志
	public static final String DATA_HAND_TOPIC="dataHandTopic";
	public static final String SERVER_CAHCHE_TOPIC="serverCahcheTopic";//服务端缓存
	public static final String CLIENT_CAHCHE_TOPIC="clientCahcheTopic";//客户端缓存
	public static final String SYN_DATA_HAND_TOPIC="synDataHandTopic";//同步数据
	
	//服务配置
	public static final String SERVICE_URL = "http://10.1.2.114:9000"; 
	public static final String TYPE_XML = "application/xml";  
	public static final String TYPE_JSON = "application/json"; 
	
	public static final String UTFCHARSET="UTF-8";
	
	public static final String CLIENT_CAHCHE_FILE_NAME="clientCacheAuth.xml";
	public static final String SERVIE_CAHCHE_FILE_NAME="serviceCacheAuth.xml";
	
	//Data center client
	public static final String DATA_CENTER_AUTH_CHECK="DataCenterAuthcheck";//数据中心权限验证服务
	public static final String DATA_CENTER_AUTH_CLASS="cn.gwssi.datachange.datashare.service.AuthCacheClientImpl";//数据中心权限验证
	public static final String DATA_CENTER_AUTH_URL="http://10.1.2.114:9000/SOAService/synUnifiedService/cn.gwssi.datachange.datashare.service.AuthCacheClientImpl";//url
	
	public static final String DATA_CENTER_NOTICE="DataCenterNotice";//数据中心对象启动通知服务
	public static final String DATA_CENTER_NOTICE_CLASS="cn.gwssi.datachange.datashare.service.NoticeServiceImpl";//数据中心对象启动通知类
	public static final String DATA_CENTER_NOTICE_URL="http://10.1.2.114:9000/SOAService/synUnifiedService/cn.gwssi.datachange.datashare.service.NoticeServiceImpl";//url
	
	//Data center service
	public static final String SDATA_CENTER_AUTH_CHECK="DataCenterAuthcheck";//数据中心权限验证服务
	public static final String SDATA_CENTER_AUTH_CLASS="cn.gwssi.datachange.datashare.service.AuthCacheServiceImpl";//数据中心权限验证
	public static final String SDATA_CENTER_AUTH_URL="http://10.1.2.114:9000/SOAService/synUnifiedService/cn.gwssi.datachange.datashare.service.AuthCacheServiceImpl";//url
	
	public static final String SDATA_CENTER_NOTICE="DataCenterNotice";//数据中心对象启动通知服务
	public static final String SDATA_CENTER_NOTICE_CLASS="cn.gwssi.datachange.datashare.service.NoticeServiceImpl";//数据中心对象启动通知类
	public static final String SDATA_CENTER_NOTICE_URL="http://10.1.2.114:9000/SOAService/synUnifiedService/cn.gwssi.datachange.datashare.service.NoticeServiceImpl";//url
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static final Long PACKET_NUMBER=1000L;
	
	public static final String DATA_CENTER_NAME="广东省/广东省工商行政管理局/广东省数据中心";
	public static final String SERVER_LOG_PATH="cn.gwssi.blog.service.BlogServer";
	public static final String DATA_CENTER_IP="10.1.2.114";
	
}
