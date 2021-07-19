package com.gwssi.common.delivery.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.common.delivery.domain.CpWkDeliveryOrderBO;
import com.gwssi.common.delivery.domain.CpWkDeliveryStatusBO;
import com.gwssi.common.delivery.domain.CpWkDeliveryStatusDetailBO;
import com.gwssi.common.delivery.service.DeliveryService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.torch.util.StringUtil;

/**
 * 手动下订单。
 * 
 * @author liuxiangqian
 * @author liuhailong
 */
@Controller
@RequestMapping("/delivery/order")
public class DeliveryController extends BaseController {
	
	@Autowired
	DeliveryService deliveryService;
	/**
	 * 材料寄递环节，申请人填写寄件人姓名、地址、联系方式，执行快递下单操作。
	 * 下单按成功后，快递人员上门取走申请材料。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/create")
	public void create(OptimusRequest request, OptimusResponse response) throws OptimusException {
		String gid = ParamUtil.get("gid");
		if(StringUtils.isBlank(gid)){
			throw new OptimusException("“gid”不能为空。");
		}
		//是否已经下单
		if(!deliveryService.noOrder(gid)){
			throw new OptimusException("当前业务已经下过订单，请勿重复下单。");
		}
		//创建订单信息
		CpWkDeliveryOrderBO orderBO = deliveryService.prepareOrderMsg(gid);
		String deliveryCorpCode = orderBO.getKdgs();
		if(StringUtils.isBlank(deliveryCorpCode)){
			throw new OptimusException("未找到快递公司信息。");
		}
		// retCode 是远端返回的状态码
		String retCode = deliveryService.createOrder(orderBO);
		
		response.addAttr("result", "1"); // result 设为1 表示成功
		response.addAttr("retCode",retCode);
	}
	
	@RequestMapping("/queryStatus")
	public void queryStatus(OptimusRequest request, OptimusResponse response) throws OptimusException {
		String reqId = ParamUtil.get("gid");
		List<CpWkDeliveryStatusBO> orders = deliveryService.queryOrderStatusByGid(reqId);
		if(orders!=null){
			if(orders.size()>0){
				CpWkDeliveryStatusBO orderStatus1 = orders.get(0);
				response.addAttr("orderStatus1", orderStatus1);
				String ddhm1 = orderStatus1.getDdhm();
				List<CpWkDeliveryStatusDetailBO> statusDetails1 = deliveryService.queryOrderStatusDetail(ddhm1);
				response.addGrid("statusDetails1", statusDetails1);
			}
			if(orders.size()>1){
				CpWkDeliveryStatusBO orderStatus2 = orders.get(1);
				response.addAttr("orderStatus2", orderStatus2);
				String ddhm2 = orderStatus2.getDdhm();
				List<CpWkDeliveryStatusDetailBO> statusDetails2 = deliveryService.queryOrderStatusDetail(ddhm2);
				response.addGrid("statusDetails2", statusDetails2);
			}
		}
	}
	/**
	 * 获取订单详细状态
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/queryDetailStatus")
	public void queryDetailStatus(OptimusRequest request, OptimusResponse response) throws OptimusException{
		//由于该方法加载页面完成调用，所以为避免抛出异常，采取这种接受方式。
		String gid = request.getParameter("gid");
		if(StringUtils.isEmpty(gid)){
			gid = StringUtil.safe2String(request.getAttr("gid"));
		}
		if(StringUtils.isEmpty(gid)){
			List<CpWkDeliveryStatusDetailBO> detailList = new ArrayList<CpWkDeliveryStatusDetailBO>();
			response.addGrid("deliveryDetailGridpanel",detailList);
		}else{
			List<CpWkDeliveryStatusDetailBO> detailList = deliveryService.queryOrderStatusDetail(gid);
			response.addGrid("deliveryDetailGridpanel",detailList);
		}
	}
}
