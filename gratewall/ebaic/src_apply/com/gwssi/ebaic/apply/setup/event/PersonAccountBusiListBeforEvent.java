package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 个人账户我的业务EVENT  BEFOR
 * @author chaiyoubing
 *
 */

@Service("PersonAccountBusiListBeforEvent")
public class PersonAccountBusiListBeforEvent implements OnEventListener{

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtil.isBlank(userId)) {
		   throw new EBaicException("登录超时，请重新登录。");
		}
		params.put("userId", userId);
		OptimusRequest request = RequestUtil.getOptimusRequest();
		String state = (String)request.getAttr("selectState");
		String operType = (String)request.getAttr("operType");
		String entName = (String)request.getAttr("entName");
		String flag = (String)request.getAttr("flag");
		params.put("state", state);
		params.put("operationType", operType);
		params.put("entName", entName);
		params.put("flag", flag);
		if("00".equals(state)){
			params.put("state", "");//00表示查询全部，state为空，表示过滤掉torch的这个条件 
		}
		if("00".equals(operType)){
			params.put("operationType", "");//00表示查询全部，查询operationType为空，表示过滤掉torch的这个条件 
		}
		if("00".equals(flag)){
			params.put("flag", "");
		}
		if(StringUtil.isNotBlank(entName)){//如果是输入名称查询，那么将其他两个去掉，只根据名称查
			params.put("state", "");//state为空，表示过滤掉torch的这个条件 
			params.put("operationType", "");//operationType为空，表示过滤掉torch的这个条件
			params.put("flag", "");
		}
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
	}

}
