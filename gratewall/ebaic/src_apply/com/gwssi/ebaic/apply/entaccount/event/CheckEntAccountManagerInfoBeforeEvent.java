package com.gwssi.ebaic.apply.entaccount.event;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;


/*
 *在执行查询企业账号管理员和修改企业账号管理员时，进行一些数据的校验 
 */
@Component("checkEntAccountManagerInfoBeforeEvent")
public class CheckEntAccountManagerInfoBeforeEvent  implements OnEventListener{

	public void execQuery(TorchController controller, Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
	}

	public void execEdit(TorchController controller, Map<String, String> params, EditConfigBo editConfigBo,
			Object result) {
		if(params == null || params.isEmpty()){
			throw new EBaicException("获取页面信息失败!");
		}
	}

}
