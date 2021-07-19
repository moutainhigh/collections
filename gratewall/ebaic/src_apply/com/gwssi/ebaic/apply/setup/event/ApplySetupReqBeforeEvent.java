package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

@Service("ApplySetupReqBeforeEvent")
public class ApplySetupReqBeforeEvent implements OnEventListener{

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
	}
	
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		HttpServletRequest request = RequestUtil.getHttpRequest();
		String gid = request.getParameter("gid");
		if(StringUtil.isBlank(gid)) {
			 throw new EBaicException("传入的业务gid不能为空。");
		}
		params.put("gid", gid);
	}


}
