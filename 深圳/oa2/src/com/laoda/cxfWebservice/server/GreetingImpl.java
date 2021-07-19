package com.laoda.cxfWebservice.server;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwssi.redis.RedisDao;
import com.gwssi.service.OAService;

@WebService(endpointInterface = "com.laoda.cxfWebservice.server.Greeting")
public class GreetingImpl implements Greeting {
	private static Logger logger = Logger.getLogger(GreetingImpl.class); // 获取logger实例

	@Autowired
	private OAService oaDao;

	@Autowired
	private RedisDao redisDao;

	private static int recordeTimeOut = 10;// 10秒

	public Integer greeting(String userId) {
		String keys = userId + "[登记系统]";

		Integer i = redisDao.get(keys, Integer.class);
		if (i == null) {
			i = oaDao.getDengJi(userId);
			redisDao.add(keys, recordeTimeOut, i);
		}

		logger.debug("登记系统==> " + userId + "  ===> 总计  ====>" + i +"\n");
		// System.out.println("登记系统==> " + userId + " ===> 总计 ====>" + i);
		return i;
	}

	@Override
	public Integer greetGetZc(String userId) {
		String keys = userId + "[固定资产]";
		Integer i = redisDao.get(keys, Integer.class);
		
		if (i == null) {
			i = oaDao.getGDZC(userId);
			redisDao.add(keys, recordeTimeOut, i);
		}
		logger.debug("固定资产==> " + userId + "  ===> 总计  ====>" + i +"\n");
		// System.out.println("固定资产==> " + userId + " ===> 总计 ====>" + i);
		return i;
	}

	@Override
	public Integer greetTodo(String userId) {
		//System.out.println("人事系统  " + userId);
		String keys = userId + "[人事系统]";
		Integer i = redisDao.get(keys, Integer.class);
		if (i == null) {
			i = oaDao.getRenShiXiTong(userId);
			redisDao.add(keys, recordeTimeOut, i);
		}
		
		logger.debug("人事系统  ==> " + userId + "  ===> 总计  ====>" + i+"\n");
		// System.out.println("人事系统 ==> " + userId + " ===> 总计 ====>" + i);
		return i;
	}
	
	

	// 取得IP

	//public String getIP() {
	//	Message message = PhaseInterceptorChain.getCurrentMessage();
	//	HttpServletRequest httprequest = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
	//	String msg = httprequest.getRemoteAddr();
	//	return msg;
	//}

}