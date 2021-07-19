package com.gwssi.common.delivery.remote.ems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.common.delivery.domain.CpWkDeliveryOrderBO;
import com.gwssi.common.delivery.domain.CpWkDeliveryOrderMaterialBO;
import com.gwssi.common.delivery.remote.DeliveryRemoteService;
import com.gwssi.common.delivery.remote.ems.domain.MessageRequest;
import com.gwssi.common.delivery.remote.ems.domain.MessageResponse;
import com.gwssi.common.delivery.remote.ems.domain.MessageRetCode;
import com.gwssi.common.delivery.remote.ems.invoke.EmsInvoker;
import com.gwssi.common.sysconfig.SystemParamsConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;

@Service(value="emsDeliveryRemoteService")
public class EmsDeliveryRemoteService implements DeliveryRemoteService{
	
	protected final static Logger logger = Logger.getLogger(EmsDeliveryRemoteService.class);
	
	public String createOrder(String reqId, CpWkDeliveryOrderBO orderBO,
			List<CpWkDeliveryOrderMaterialBO> materialList)
			throws OptimusException {
		//String yjhm = null;//邮件号码
		// 1 构造请求对象
		MessageRequest request = new MessageRequest();
		request.setFuncCode("N003");
		// 1.1 订单信息
		List<Map<String, String>> orderDataSet = new ArrayList<Map<String, String>>();
		Map<String, String> order = new HashMap<String, String>();
		order.put("DDHM", orderBO.getDdhm());
		order.put("DDMC", orderBO.getDdmc());
		order.put("DDYWLX", orderBO.getDdywlx());
		order.put("DDZT", orderBO.getDdzt());
		order.put("DDSLJG", orderBO.getDdsljg());
		order.put("SMQF", orderBO.getSmqf());
		order.put("JJR_XM", orderBO.getJjrXm());
		order.put("JJR_DZ", orderBO.getJjrDz());
		order.put("JJR_DH", orderBO.getJjrDh());
		order.put("SJR_XM", orderBO.getSjrXm());
		order.put("SJR_DZ", orderBO.getSjrDz());
		order.put("SJR_DH", orderBO.getSjrDh());
		order.put("JDD_CSDM", orderBO.getJddCsdm());
		order.put("SFD_CSDM", orderBO.getSfdCsdm());
		order.put("DSHK", orderBO.getDshk());
		order.put("DSJE", orderBO.getDsje());
		order.put("SJR_DW", orderBO.getSjrDw());
		order.put("BZ", orderBO.getBz());
		order.put("YLZD1", orderBO.getYlzd1());
		order.put("YLZD2", orderBO.getYlzd2());
		order.put("YLZD3", orderBO.getYlzd3());
		order.put("YLZD4", orderBO.getYlzd4());
		
		orderDataSet.add(order);
		request.putDataSet("101", orderDataSet);
		// 1.2 内件信息
		List<Map<String, String>> orderMaterialDataSet = new ArrayList<Map<String, String>>();
		for(CpWkDeliveryOrderMaterialBO materialBO : materialList){
			Map<String, String> material = new HashMap<String, String>();
			material.put("DDHM", materialBO.getDdhm());
			material.put("NJXH", materialBO.getNjxh());
			material.put("NJHM", materialBO.getNjhm());
			material.put("NJMC", materialBO.getNjmc());
			material.put("NJLX", materialBO.getNjlx());
			orderMaterialDataSet.add(material);
		}
		request.putDataSet("102", orderMaterialDataSet);
		
		// 2 调用
		String configValue = SystemParamsConfigManager.get("ems.is_rmi_directly");
		//临时开关 是否调用ems接口
		if(configValue.equals("1")){//开启临时开关
			return "OK";
		}else{
			List<Map<String,String>> dataSet = request.getBODY().getDataSet("101");
			String csdm = "";
			if(dataSet!=null && dataSet.size()>0){
				Map<String,String> data = dataSet.get(0);
				//获取行政区划代码
				csdm = data.get("SFD_CSDM");
			}else{
				throw new OptimusException("未找到订单信息。");
			}
				
			MessageResponse response = EmsInvoker.invoke(request,csdm);
			// 3  处理返回结果
			MessageRetCode retCode = response.getRetCode();
			
			String retCodeString = response.getRetCodeString() ;
			String retMsgString = response.getRET_MSG();
			
			if(retCode != MessageRetCode.OK){
				logger.error(String.format("EMS 下单失败：[%s][%s] ", retCodeString,retMsgString));
			}
					
			//请求为用户发起并失败的将错误信息抛出
			if(retCode != MessageRetCode.OK && "0".equals(orderBO.getDeliveryFrom())){
				throw new OptimusException(retMsgString);
			}
			return retCodeString;
		}
	}

	public void cancelOrder(String yjhm) throws OptimusException {
		
	}
	
}
