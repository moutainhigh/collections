package com.gwssi.ebaic.apply.security.user.event;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;



@Service("changeRegInfoBeforeEvent")
public class ChangeRegInfoBeforeEvent implements OnEventListener{

	public void execEdit(TorchController controller, Map<String, String> formData,EditConfigBo editConfigBo,Object result)   {
		
		if(formData == null || formData.isEmpty()){
			throw new EBaicException("获取页面用户信息失败!");
		}
		
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtils.isBlank(userId)){
			throw new EBaicException("获取页面用户信息失败,请重新登录");
		}
		formData.put("userId", userId);
		
	}



	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
	}


}
