package com.gwssi.ebaic.apply.setup.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;
import com.gwssi.rodimus.validate.msg.ValidateMsgEntity;
import com.gwssi.torch.util.JSON;

@Controller
@RequestMapping("/rule")
public class RuleTestController {
	@RequestMapping("/run")
	public void run(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String n = ParamUtil.get("n");
		String c = ParamUtil.get("c");
		System.out.println("------->"+n);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("gid", n);
		ValidateMsg ret = RuleUtil.getInstance().runApp(c, n);
		List<ValidateMsgEntity> listMsg=ret.getAllMsg();
		for (ValidateMsgEntity vmsg:listMsg) {
			//如果规则错误信息不为空，说明规则不通过
			if (StringUtils.isNotBlank(vmsg.getMsg())) {
				System.out.println("--->"+vmsg.getName());
				System.out.println("--->"+vmsg.getType());//1锁定 0 提示
				System.out.println("--->"+vmsg.getMsg());
			}
		}
		response.addResponseBody(JSON.toJSON(ret.getAllMsg()));
	}
}
