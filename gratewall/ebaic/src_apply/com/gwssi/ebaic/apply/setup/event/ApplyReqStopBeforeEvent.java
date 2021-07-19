package com.gwssi.ebaic.apply.setup.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;
import com.gwssi.rodimus.validate.msg.ValidateMsgEntity;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 业务终止 Before Event。
 * 
 * @author lxb
 */
@Service("applyReqStopBeforeEvent")
public class ApplyReqStopBeforeEvent implements OnEventListener {

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
		
		
		
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		OptimusRequest request = RequestUtil.getOptimusRequest();
		String gid = request.getParameter("gid");
		if(StringUtil.isBlank(gid)) {
			 throw new EBaicException("传入的业务gid不能为空。");
		}
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtils.isBlank(userId)){
			throw new EBaicException("登录超时，请重新登陆");
		}
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("gid", gid);
		paramsMap.put("userId", userId);
		ValidateMsg msg= RuleUtil.getInstance().runRule("ebaic_apply_req_stop",paramsMap);
		List<ValidateMsgEntity> errors =  msg.getAllMsg();
		if(errors!=null && !errors.isEmpty()){
			throw new EBaicException(msg.getAllMsgString());
		}
	}
	
	

}
