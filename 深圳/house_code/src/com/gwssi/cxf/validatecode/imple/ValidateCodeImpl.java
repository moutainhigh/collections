package com.gwssi.cxf.validatecode.imple;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSONObject;
import com.gwssi.cxf.validatecode.inter.ValidateCodeInter;
import com.gwssi.cxf.validatecode.manager.MyX509TrustManager;
import com.gwssi.cxf.validatecode.service.ValidateCodeService;

/**
 * 房屋地址信息核查服务端
 * @author Administrator
 *
 */
@WebService(serviceName="ValidateCodeService")
public class ValidateCodeImpl implements ValidateCodeInter{
	
	/*
	 * @WebService注解不会把类申明为Spring中的Bean，所以不能用注解@Autowired方式来实现业务层类的注入。
	 * 在此处采用ContextLoader的方式来获取对象。
	 */
	private ValidateCodeService validateCodeService = 
										ContextLoader
											.getCurrentWebApplicationContext()
												.getBean(ValidateCodeService.class);

	@Override
	@WebResult(name="String")
	public String getHouseInfo(@WebParam(name="houseId") String houseid) throws Exception {
		//System.out.println("房屋id："+houseid);
		String url = "https://api.szaic.gov.cn/addrcode/48DD4E04E17841878D44E864A3F6C4E1/"+houseid;
		String strJson = MyX509TrustManager.getClient(url);
		JSONObject json = JSONObject.parseObject(strJson);
		if (json != null && json.size()>0) {
			String code = (String) json.get("code");
			if ("0".equals(code)) {
				return (String)json.get("data");
			} else {
				List<Map> addrList = validateCodeService.getAddrInfo(houseid);
				if (addrList != null && addrList.size()>0) {
					String addrData = (String) addrList.get(0).get("houseAdd");
					return addrData;
				}
			}
		}
		return "查无此信息";
	}
	
}
