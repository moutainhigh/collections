package cn.gwssi.datachange.datashare.service;

import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**记录日志服务，数据中心，数据服务模块-内部服务，不公开，用作，记录clint调用服务端时间、........
 * cn.gwssi.datachange.datashare.service
 * LogWebservice.java
 * 下午2:43:58
 * @author wuminghua
 */
@WebService
public class LogWebservice {
	
	/**记录日志详情，如，调用开始，接受请求......
	 * @param map
	 * @return
	 */
	@WebMethod
	public String writeLogDetial(Map map){
		//1.记录详情
		
		
		return null;
	}
	
	/**记录服务调用日志
	 * @param map
	 * @return
	 */
	@WebMethod
	public String writeLog(Map map){
		//1.记录本条服务被调用情况
		
		
		return null;
	}
	
	
	

}
