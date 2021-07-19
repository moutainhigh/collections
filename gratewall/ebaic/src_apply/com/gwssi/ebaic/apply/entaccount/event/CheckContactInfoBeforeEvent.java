package com.gwssi.ebaic.apply.entaccount.event;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 保存企业联系人信息时，校验用户输入的信息是否正确的后台验证类
 * 
 * @author ye
 *
 */
@Component("checkContactInfoBeforeEvent")
public class CheckContactInfoBeforeEvent implements OnEventListener {

	public void execQuery(TorchController controller, Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {

	}

	public void execEdit(TorchController controller, Map<String, String> params, EditConfigBo editConfigBo,
			Object result) {
		if (params == null || params.isEmpty()) {
			throw new EBaicException("获取页面信息失败!");
		}
	}

}
