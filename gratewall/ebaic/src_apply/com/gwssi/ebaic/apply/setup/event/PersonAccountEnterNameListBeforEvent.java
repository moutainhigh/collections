package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.springframework.stereotype.Service;
import com.gwssi.ebaic.torch.event.BaseEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 公司设立入口 Before Event。
 * 
 * @author chaiyoubing
 */
@Service("PersonAccountEnterNameListBefor")
public class PersonAccountEnterNameListBeforEvent extends BaseEventListener {

	@Override
	public void exec(Map<String, String> formData) {
		 String userId = HttpSessionUtil.getCurrentUserId();
	     if(StringUtil.isBlank(userId)) {
	        throw new EBaicException("登录超时，请重新登录。");
	     }
	     formData.put("userId", userId);
	}
}
