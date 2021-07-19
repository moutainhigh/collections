package com.gwssi.ebaic.apply.entaccount.event;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;


/**
 * 保存企业补充信息时，添加的后台校验前台用户数据是否有效
 * @author ye
 * 
 */
@Component("checkEntSndInfoBeforeEvent")
public class CheckEntSndInfoBeforeEvent implements OnEventListener{

	public void execQuery(TorchController controller, Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
	}
	
	public void execEdit(TorchController controller, Map<String, String> params, EditConfigBo editConfigBo,
			Object result) {
		if(params == null || params.isEmpty()){
			throw new EBaicException("获取页面信息失败!");
		}
		
		//数据安全性效验
	}

}
