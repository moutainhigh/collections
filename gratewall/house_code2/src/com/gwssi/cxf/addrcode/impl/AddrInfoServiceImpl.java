package com.gwssi.cxf.addrcode.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.gwssi.cxf.addrcode.inter.AddrInfoServiceInterface;
import com.gwssi.cxf.feedback.common.SysResult;
import com.gwssi.query.webservice.client.HouseServiceClient;

/**
 * 地址信息核查接口
 * @author lokn
 *
 */
@WebService(serviceName = "AddrInfoService")
public class AddrInfoServiceImpl implements AddrInfoServiceInterface {
	
	// 调用远程接口服务获取房屋基本信息
	private HouseServiceClient houseServiceClient = new HouseServiceClient();
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@SuppressWarnings("rawtypes")
	@Override
	@WebResult(name = "String")
	public String getAddrInfo(@WebParam(name = "name") String name, @WebParam(name = "houseCode") String houseCode) {
	
		if (StringUtils.isNotEmpty(name) || StringUtils.isNotEmpty(houseCode)) {
			List<Map> listInfo = houseServiceClient.getHouseByParam(name, houseCode);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> map = null;
			
			if (listInfo != null && listInfo.size() > 0) {
				for (Map m : listInfo) {
					map = new HashMap<String, String>();
					map.put("houseCode", (String) m.get("houseId"));
					String houseAddr = (String) m.get("houseAdd");
					// 判断地址中是否有"广东省"，有，则去除
					if (houseAddr.contains("广东省")) {
						houseAddr = houseAddr.substring(3);
						map.put("houseAddr", houseAddr);
					} else {
						map.put("houseAddr", (String) m.get("houseAdd"));
					}
					list.add(map);
				}
				
				String jsonStr;
				try {
					jsonStr = MAPPER.writeValueAsString(list);
					return SysResult.codeStatus_0(jsonStr);
				} catch (Exception e) {
					e.printStackTrace();
					return SysResult.codeStatus_9();
				}
			}
			return SysResult.codeStatus_1();
		}
		return SysResult.codeStatus_2();
	}
		
}
		

