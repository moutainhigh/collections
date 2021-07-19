package com.gwssi.ebaic.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.rodimus.indentity.domain.IdentityCardBO;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping("/doc")
	public void doc(OptimusRequest request,OptimusResponse response) throws OptimusException{
		/*String listCode = "setup_1100";
		Map<String,Object> params = new HashMap<String,Object>();
		String gid = ParamUtil.get("gid");
		params.put("gid", gid);
		DocUtil.buildDoc(listCode, params);
		*/
//		String gid = ParamUtil.get("gid");
//		Map<String,Object> params = new HashMap<String, Object>();
//		params.put("gid", gid);
//		ValidateMsg msg = RuleUtil.getInstance().runRule("ebaic_reg_inv",params);
//		List<ValidateMsgEntity> errors =  msg.getAllMsg();
//		if(errors!=null && !errors.isEmpty()){
//			throw new OptimusException(msg.getLockedMsg());
//		}
//		SmsUtil.send("18600107299", "恭喜您，您的申请已获核准！请到北京市工商行政管理局继续办理后续手续。");
		
		IdentityCardBO bo = IdentityCardUtil.getIdentityCardInfo("刘伊轩", "110108201107080040");
		response.addResponseBody(JSON.toJSONString(bo, true));
	}
}
