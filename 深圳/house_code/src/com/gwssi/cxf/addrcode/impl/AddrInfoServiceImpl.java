package com.gwssi.cxf.addrcode.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.context.ContextLoaderListener;

import com.gwssi.cxf.addrcode.inter.AddrInfoServiceInterface;
import com.gwssi.cxf.feedback.common.SysResult;
import com.gwssi.ip.service.SafeVisitService;
import com.gwssi.ip.util.IPUtil;
import com.gwssi.ip.util.StringUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.query.webservice.client.HouseServiceClient;

/**
 * 地址信息核查接口
 * 	  通过房屋编码来精确查找房屋地址
 * 
 * @author lokn
 *  2018/01/05
 */
@WebService(serviceName = "AddrInfoService")
public class AddrInfoServiceImpl extends BaseService implements AddrInfoServiceInterface {
	
	private static Logger logger = Logger.getLogger(AddrInfoServiceImpl.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	private SafeVisitService service = new SafeVisitService();
	
	// 调用远程接口服务获取房屋基本信息
	private HouseServiceClient houseServiceClient = new HouseServiceClient();
	
	@SuppressWarnings("rawtypes")
	@Override
	@WebResult(name = "String")
	public String getAddrInfo(@WebParam(name = "houseCode", targetNamespace="http://impl.addrcode.cxf.gwssi.com/") String houseCode) {
	
		long startTime = System.currentTimeMillis();
		logger.info("请求开始时间" + startTime);
		// 获取客户端ip地址
		String ip = getClientIpInfo();
		try {
			if (StringUtil.isNotEmpty(ip)) {
				service.saveIPLog(ip);
			}
		} catch (OptimusException e1) {
			e1.printStackTrace();
			// 其他
			return SysResult.codeStatus_9();
		}
		
		/*if (StringUtils.isEmpty(houseCode)||StringUtils.isNotEmpty(houseCode)&&houseCode.length()!=25) {
			// 请求参数无效
			return SysResult.codeStatus_2();
		}*/
		
		if (StringUtils.isEmpty(houseCode)) {
			// 请求参数无效
			return SysResult.codeStatus_2();
		}
		
		String code = houseCode.trim();
		
		
		boolean flag = this.isGovmentBusinessPlace(code); //政府办公驻地为真
		System.out.println("是否是政府驻地===>  " + flag);
		if(!flag) {
			// 根据房屋编码精确获取房屋地址,原来的接口2
			//List<Map> listInfo = houseServiceClient.getHouseByParam(null, houseCode);
			List<Map> listInfo = houseServiceClient.getHouseByMoreAddress(null, code.trim());
			logger.info("请求入口 ===》  com.gwssi.cxf.addrcode.impl.AddrInfoServiceImpl.getAddrInfo(String)" + houseCode);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			Map<String, String> map2 = null;
			
			if (listInfo != null && listInfo.size() > 0) {
				for (Map m : listInfo) {
					map = new HashMap<String, String>();
					map2 = new HashMap<String, String>();
					map.put("houseCode", (String) m.get("houseId"));
					map2.put("houseCode", (String) m.get("houseId"));
					String houseAddr = (String) m.get("houseAdd");
					String houseShortAdd = (String) m.get("houseShortAdd");
					String realAddr = (String) m.get("realAddr");
					
					// 判断地址中是否有"广东省"，有，则去除
					if (houseAddr != null && houseAddr.contains("广东省")) {
						houseAddr = houseAddr.substring(3);
						
						map.put("houseAddr", houseShortAdd);
						map.put("houseShortAdd", null);
						map.put("realAddr", realAddr);
						
						map2.put("houseAddr", houseAddr);
						map2.put("houseShortAdd", null);
						map2.put("realAddr", realAddr);
					} else {
						map.put("houseAddr", houseShortAdd);
						map.put("houseShortAdd", null);
						map.put("realAddr", realAddr);
						
						map2.put("houseAddr", houseAddr);
						map2.put("houseShortAdd", null);
						map2.put("realAddr", realAddr);
					}
					list.add(map);
					list.add(map2);
				}
				long endTime = System.currentTimeMillis();
				double time =  endTime-startTime;
				logger.info("请求结束时间  " + endTime + ",耗时 " + (time/1000)+"秒");
				String jsonStr;
				try {
					jsonStr = MAPPER.writeValueAsString(list);
					// 数据获取成功
					System.out.println(jsonStr);
					return SysResult.codeStatus_0(jsonStr);
				} catch (Exception e) {
					e.printStackTrace();
					// 其他错误
					return SysResult.codeStatus_9();
				}
				
				
			} else { // 如果调用远程接口没有数据，则在本地数据库查询数据
				IPersistenceDAO dao = getPersistenceDAO("dc_dc");
				String sql = "select house_id, house_add from dc_address_view where house_id = ?";
				List params = new ArrayList();
				params.add(houseCode.trim());
				
				try {
					List<Map> queryForList = dao.queryForList(sql, params);
					map = new HashMap<String, String>();
					
					if (queryForList == null || queryForList.size() <= 0) {
						// 暂无数据
						return SysResult.codeStatus_1(); 
					}
					
					map.put("houseCode", (String) queryForList.get(0).get("houseId"));
					
					String houseAdd = (String) queryForList.get(0).get("houseAdd");
					if (houseAdd != null && houseAdd.contains("广东省")) {
						houseAdd = houseAdd.substring(3);
						map.put("houseAddr", houseAdd);
					} else {
						map.put("houseAddr", houseAdd);
					}
					long endTime = System.currentTimeMillis();
					double time =  endTime-startTime;
					logger.info("请求结束时间[调用远程接口]" + endTime + "耗时 " + (time/1000)+"秒");
					//logger.info("请求结束时间" + endTime + ",耗时 " + (endTime-startTime)/1000+"秒");
					list.add(map);
					String jsonStr = MAPPER.writeValueAsString(list);
					// 数据获取成功
					return SysResult.codeStatus_0(jsonStr);
				} catch (OptimusException e ) {
					e.printStackTrace();
					// 其他错误
					return SysResult.codeStatus_9();
				} catch (IOException e) {
					e.printStackTrace();
					// 其他错误
					return SysResult.codeStatus_9();
				}
			}
		}else {
			return SysResult.codeStatus_1();
		}
		
		
		
	}
	
	
	/**
	 * 是政府办公驻地，则返回真
	 * @param code
	 * @return
	 */
	private boolean isGovmentBusinessPlace(String code) {
		boolean flag = false;
		
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		String sql = "select * from DC_HOUSE_CODE_KEYWORDS t where  t.word like  ?  ";
		List params = new ArrayList();
		params.add("%"+code+"%");
		List<Map> queryForList;
		try {
			queryForList = dao.queryForList(sql, params);
			if(queryForList!=null&&queryForList.size()>0) {
				flag = true;
			}
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * 获取客户端的IP地址
	 * @return IP
	 */
	private String getClientIpInfo() {
		Message message = (Message) PhaseInterceptorChain.getCurrentMessage();  
		HttpServletRequest request = (HttpServletRequest)message.get(AbstractHTTPDestination.HTTP_REQUEST);
		String ipPropAddr = IPUtil.getIpPropAddr(request);
		logger.info("com.gwssi.cxf.addrcode.impl.AddrInfoServiceImpl.getAddrInfo(String)===>" + ipPropAddr);
		return ipPropAddr;
	}
	
	public static void main(String[] args) {
		String str = "4403040040100600004000215";//4403060010050400021000077   4403040080070300003000003
		System.out.println(str.length());
	}
	
		
}
		

