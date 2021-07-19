package com.gwssi.ebaic.apply.setup.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.setup.service.SetupEntranceService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.validate.api.Val;
import com.gwssi.rodimus.validate.msg.ValidateMsg;

/**
 * 设立入口。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller
@RequestMapping("/apply/setup/entrance")
public class EntranceController {
	
	@Autowired
	SetupEntranceService setupEntranceService; 
	/**
	 * 公司设立入口。
	 * @param requeest
	 * @param response
	 * @throws OptimusException 
	 */
	@RequestMapping("/cpCheckin")
	public void cpCheckIn(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
//		String entName = ParamUtil.get("entName");
//		String cerNo = ParamUtil.get("cerNo");
		
		String gid = setupEntranceService.cpCheckIn();
		response.addAttr("gid", gid);
	}
	
	/**
	 * 公司设立本账户申请的名称列表入口。
	 * @param requeest
	 * @param response
	 * @throws OptimusException 
	 */
	@RequestMapping("/cpNameListEnter")
	public void cpNameListEnter(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String nameId = ParamUtil.get("nameId");
		String gid = setupEntranceService.cpNameListEnter(nameId);
		response.addAttr("gid", gid);
		response.addAttr("result", "success");
	}
	
	
	/**
	 * 公司设立起名的有效期校验     待办名称列表中的进入办理处理事件
	 * @param request
	 * @param response
	 * @throws OptimusException 
	 */
	@RequestMapping("/cpSetupNameValid")
	public void checkSetupNameValid(OptimusRequest request,OptimusResponse response) throws OptimusException{
		
		String nameId = ParamUtil.get("nameId");
		
		String setupValidTime = setupEntranceService.checkEntValidTime(nameId);
		
		double setupValidTimeInteger = Double.parseDouble(setupValidTime);
				
		if(setupValidTimeInteger>=0.0 & setupValidTimeInteger<=3.0){
			response.addAttr("result", "1");  //"1"表示三天及三天之内会过期
		}else if(setupValidTimeInteger > 3){
			response.addAttr("result", "2");  //"2"表示无需提醒 短期内不会过期
		}else{
			response.addAttr("result", "3");  //"3"表示该名称已经过期 需要重新申请
		}
	}
	
	
	/**
	 * 公司设立起名的有效期校验    非本账户申请
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/cpSetupNameValid2")
	public void cpSetupNameValid2(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String entName = ParamUtil.get("entName");
		String cerNo = ParamUtil.get("cerNo");
		
		Val.field.idcard("身份证号码", cerNo);
		ValidateMsg msg = Val.getMsg();
		if(!Val.getMsg().isEmpty()){
			String errorMsg = msg.getAllMsgString();
			throw new OptimusException(errorMsg);
		}
		
		String setupValidTime = setupEntranceService.checkEntValidTime2(entName,cerNo);
		
		double setupValidTimeInteger = Double.parseDouble(setupValidTime);
		
		if(setupValidTimeInteger>=0.0 & setupValidTimeInteger<=3.0){
			response.addAttr("result", "1");  //"1"表示三天及三天之内会过期
		}else if(setupValidTimeInteger > 3){
			response.addAttr("result", "2");  //"2"表示无需提醒 短期内不会过期
		}else{
			response.addAttr("result", "3");  //"3"表示该名称已经过期 需要重新申请
		}
	}
}
