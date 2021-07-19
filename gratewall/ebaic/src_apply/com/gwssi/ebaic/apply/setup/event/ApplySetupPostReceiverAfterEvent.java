package com.gwssi.ebaic.apply.setup.event;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bjca.org.apache.log4j.Logger;

import com.gwssi.common.delivery.domain.CpWkDeliveryOrderBO;
import com.gwssi.common.delivery.service.DeliveryService;
import com.gwssi.ebaic.approve.util.ProcessUtil;
import com.gwssi.ebaic.common.util.SubmitUtil;
import com.gwssi.ebaic.domain.BeWkReqBO;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.sms.SmsUtil;
import com.gwssi.rodimus.sms.domain.SmsBusiType;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

@Service("applySetupPostReceiverAfterEvent")
public class ApplySetupPostReceiverAfterEvent implements OnEventListener{
	
	@Autowired
	DeliveryService deliveryService;
	
	public static final Logger logger = Logger.getLogger(ApplySetupPostReceiverAfterEvent.class);
	
	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		//1.更新 取照方式和时间
		HttpServletRequest request = RequestUtil.getHttpRequest();
		String gid = request.getParameter("gid");
		if(StringUtil.isBlank(gid)) {
			 throw new EBaicException("传入的业务gid不能为空。");
		}
		String licenseWay = request.getParameter("licenseWay");
		if(StringUtil.isBlank(licenseWay)) {
			 throw new EBaicException("取照方式不能为空。");
		}
		String sql ="update be_wk_requisition r set r.license_get_type =?,r.license_order_time=sysdate where gid=?";
		DaoUtil.getInstance().execute(sql,licenseWay,gid);
		String sql1 ="update be_wk_requisition@test_2_100 r set r.license_get_type =?,r.license_order_time=sysdate where gid=?";
		DaoUtil.getInstance().execute(sql1,licenseWay,gid);
		
//		ApproveDaoUtil.getInstance().execute(sql,licenseWay,gid);
		
		//2.EMS下订单，暂时先不下单，因为对下单业务不熟，防止下单成功（刘祥乾）
		try{
			//是否已经下单
			if(!deliveryService.noOrder(gid)){
				throw new RuntimeException("当前业务已经下过订单，请勿重复下单。");
			}
			//创建订单信息
			CpWkDeliveryOrderBO orderBO = deliveryService.prepareOrderMsg(gid);
			String deliveryCorpCode = orderBO.getKdgs();
			if(StringUtils.isBlank(deliveryCorpCode)){
				throw new RuntimeException("未找到快递公司信息。");
			}
			// retCode 是远端返回的状态码
			String retCode = deliveryService.createOrder(orderBO);
			logger.info("EmsRetCode=======> "+retCode);
		}catch(OptimusException e){
			throw new RuntimeException(e.getMessage());
		}
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("“gid”不能为空。");
		}
		//3.发送短信通知
		IPersistenceDAO dao = DAOManager.getPersistenceDAO();
		BeWkReqBO beWkReqBO = null;
		try {
			beWkReqBO = dao.queryByKey(BeWkReqBO.class, gid);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		String mobTel = ProcessUtil.getBeWkLeRepMobile(gid);
		Map<String,Object> smsParams = new HashMap<String,Object>();
		String entName = beWkReqBO.getEntName();
		smsParams.put("entName", entName);
		smsParams.put("linkman", beWkReqBO.getLinkman());
		String licenseWayText = "";
		if("1".equals(licenseWay)){
			licenseWayText = "寄递";
		}else{
			licenseWayText = "自取";
		}
		
		smsParams.put("licGetType", licenseWayText);
		SmsUtil.send(mobTel, SmsBusiType.LIC_GET_NOTICE, smsParams);
		
		SubmitUtil.processRecord(gid, "08","8", ("选择取照方式为"+licenseWayText),"");
	}	
	
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
	}

	

}
