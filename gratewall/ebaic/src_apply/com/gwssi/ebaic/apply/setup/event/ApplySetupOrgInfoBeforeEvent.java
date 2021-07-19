package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

@Service("ApplySetupOrgInfoBeforeEvent")
public class ApplySetupOrgInfoBeforeEvent implements OnEventListener{

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {

	}
	
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		HttpServletRequest request = RequestUtil.getHttpRequest();
		String gid = request.getParameter("gid");
		if(StringUtils.isEmpty(gid)){
			throw new EBaicException("获取gid异常！");
		}
		params.put("gid",gid);
	}


}
