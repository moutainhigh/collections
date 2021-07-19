package com.gwssi.common.delivery.remote.sf;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gwssi.common.delivery.domain.CpWkDeliveryOrderBO;
import com.gwssi.common.delivery.domain.CpWkDeliveryOrderMaterialBO;
import com.gwssi.common.delivery.remote.DeliveryRemoteService;
import com.gwssi.optimus.core.exception.OptimusException;

@Service(value="sfDeliveryRemoteService")
public class SfDeliveryRemoteService implements DeliveryRemoteService{

	public String createOrder(String reqId, CpWkDeliveryOrderBO orderBO,
			List<CpWkDeliveryOrderMaterialBO> materialList)
			throws OptimusException {
		// TODO Auto-generated method stub
		return null;
	}

	public void cancelOrder(String yjhm) throws OptimusException {
		// TODO Auto-generated method stub
		
	}


}
