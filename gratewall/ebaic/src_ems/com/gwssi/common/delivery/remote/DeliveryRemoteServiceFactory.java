package com.gwssi.common.delivery.remote;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.common.delivery.remote.ems.EmsDeliveryRemoteService;
import com.gwssi.common.delivery.remote.sf.SfDeliveryRemoteService;
import com.gwssi.optimus.core.exception.OptimusException;

@Service(value="deliveryRemoteServiceFactory")
public class DeliveryRemoteServiceFactory {
	
	@Autowired
	EmsDeliveryRemoteService emsDeliveryRemoteService;
	
	@Autowired
	SfDeliveryRemoteService sfDeliveryRemoteService;
	protected final static Logger logger = Logger.getLogger(DeliveryRemoteServiceFactory.class);
	/**
	 * 获得快递公司实例。
	 * @param deliveryCorpCode 如:"EMS","SF"
	 * @return
	 * @throws OptimusException 
	 */
	public DeliveryRemoteService getDeliveryRemoteService(String deliveryCorpCode) throws OptimusException{
		logger.info("快递公司代码："+deliveryCorpCode);
		if("ems".equals(deliveryCorpCode)){
			return emsDeliveryRemoteService;
		}else if("sf".equals(deliveryCorpCode)){
			return emsDeliveryRemoteService;
		}else{
			throw new OptimusException("当前仅支持EMS(代码：EMS)和顺丰快递（代码：SF），不支持“"+deliveryCorpCode+"”，请联系系统管理员，检查配置是否正确。");
		}
	}
	
}
