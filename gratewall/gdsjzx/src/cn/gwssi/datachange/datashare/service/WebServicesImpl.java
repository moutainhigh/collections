package cn.gwssi.datachange.datashare.service;

import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

import cn.gwssi.datachange.datashare.inter.WebServiceInterface;

/**		对不同服务，则需要进行不同处理；处理方式如下：
			a、其他应用程序调用服务时，clint jar 通过其他应用传入serviceid调用服务内容查询服务，查询真实服务地址
 * cn.gwssi.datachange.datashare.service
 * GDGSWebServices.java
 * 下午2:26:39
 * @author wuminghua
 */
@WebService
public class WebServicesImpl implements WebServiceInterface {
	/**所有调用服务入口
	 * @param serviceId 服务代码
	 * @param username  用户名
	 * @param password  密码
	 * @param mapCondition  参数
	 * @param requiredItems 返回数据项
	 */
	//对所有的服务需要进行拦截
	@WebMethod
	public Map callWebService(String serviceId,String ip,String username,String password, String mode,Map mapCondition,Map requiredItems ) {
		/**
		1.通过serviceId查询真实服务地址
		2.通过服务提供方进行判断，服务提供方为本地数据库、外部数据库、外部webservice或本地webservice
			request是线程安全的！
		*/
		
		return null;
	}
	
	
}
