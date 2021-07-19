package com.gwssi.ebaic.approve.identity.event;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.approve.util.ApproveUserUtil;
import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

@Service("identityListBeforeEvent")
public class IdentityListBeforeEvent implements OnEventListener{

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		SysmgrUser sysmgrUser=ApproveUserUtil.getLoginUser();
		if(null == sysmgrUser){
				throw new EBaicException("登录超时，请重新登录");
		}
		params.put("regOrg", sysmgrUser.getOrgCodeFk());
		String flag = params.get("flag"); 
		String pageNo = params.get("pageNo"); 
		String pageSize = params.get("pageSize"); 
		if(StringUtil.isBlank(flag)) {
            params.put("flag", "0");
        }
		if(StringUtil.isBlank(pageNo)) {
            params.put("pageNo", "1");
        }
		if(StringUtil.isBlank(pageSize)) {
            params.put("pageSize", "10");
        }
		
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		// TODO Auto-generated method stub
		
	}

}
