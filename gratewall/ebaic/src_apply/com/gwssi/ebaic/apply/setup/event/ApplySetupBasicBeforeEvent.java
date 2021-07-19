package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.setup.service.SetupBasicInfoService;
import com.gwssi.ebaic.torch.event.BaseEventListener;
import com.gwssi.rodimus.exception.EBaicException;

@Service("applySetupBasicBeforeEvent")
public class ApplySetupBasicBeforeEvent extends BaseEventListener {
	@Autowired 
	SetupBasicInfoService setupBasicInfoService;

	public void exec(Map<String, String> params) {
		if (params == null || params.isEmpty()) {
			throw new EBaicException("获取参数异常！");
		}
		String tradeTerm = params.get("tradeTerm");
		if(StringUtils.isEmpty(tradeTerm)){
			throw new EBaicException("未获取到tradeTerm");
		}
		if("999999".equals(tradeTerm)){
			tradeTerm = null;
		}
		params.put("tradeTerm", tradeTerm);
		//保存企业基本信息修改的内容
		setupBasicInfoService.saveChanged(params);
		
	}
	
	

}
