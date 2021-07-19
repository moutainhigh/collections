package com.gwssi.cxf.validatecode.inter;

import javax.jws.WebParam;
import javax.jws.WebResult;

public interface ValidateCodeInter {
	//调用该服务能查询单条房屋信息
		@WebResult(name="String")
		public String getHouseInfo(@WebParam(name="xmlString") String xmlString) throws Exception;
}
