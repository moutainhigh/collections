package com.gwssi.common.delivery.remote;

import java.util.List;

import com.gwssi.common.delivery.domain.CpWkDeliveryOrderBO;
import com.gwssi.common.delivery.domain.CpWkDeliveryOrderMaterialBO;
import com.gwssi.optimus.core.exception.OptimusException;

/**
 * 调用快递公司接口，完成下单，取消订单等业务操作。
 * 
 * @author liuhailong
 */
public interface DeliveryRemoteService{
	
	public String createOrder(String reqId, CpWkDeliveryOrderBO orderBO, List<CpWkDeliveryOrderMaterialBO> materialList) throws OptimusException;
	
	public void cancelOrder(String yjhm) throws OptimusException;
	
}
