package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 
 * @author Administrator
 *
 */
@Service("ApplySetupBeWkEntInfo")
public class ApplySetupBeWkEntInfoBeforEvent implements OnEventListener {

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		/**
		 * 1、得到前台传过来的业务gid
		 */
		OptimusRequest request = RequestUtil.getOptimusRequest();
		String gid = request.getParameter("gid");
		if(StringUtil.isBlank(gid)) {
			 throw new EBaicException("传入的业务gid不能为空。");
		}
		params.put("gid", gid);
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
	}

}
