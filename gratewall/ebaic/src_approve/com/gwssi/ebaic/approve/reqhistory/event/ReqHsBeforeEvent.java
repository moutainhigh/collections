package com.gwssi.ebaic.approve.reqhistory.event;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.approve.util.ApproveUserUtil;
import com.gwssi.ebaic.approve.util.AssignUtil;
import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

@Service("reqHsBeforeEvent")
public class ReqHsBeforeEvent implements OnEventListener{

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		SysmgrUser sysmgrUser = ApproveUserUtil.getLoginUser();
		if(null == sysmgrUser){
			throw new EBaicException("登录超时，请重新登录");
		}
		params.put("regOrg", sysmgrUser.getOrgCodeFk());
		
		//统一社会信用代码小写转大写
		String uniScid = params.get("uniScid");
		if(StringUtil.isNotBlank(uniScid)){
			uniScid = uniScid.toUpperCase();
			params.put("uniScid", uniScid.trim());
		}
		
		//去掉查询条件中的空格，并且判断根据名称查询的查询方式(1-以...开头     0-模糊)
		String queryType = params.get("queryType");
		String entName = params.get("entName");
		String regNo = params.get("regNo");
		if(StringUtil.isNotBlank(entName)){
			params.put("entName", entName.trim());
		}
		if(StringUtil.isNotBlank(queryType)){
			if("1".equals(queryType)){
				entName = entName+"%";
				params.put("entName", entName.trim());
			}
		}
		if(StringUtil.isNotBlank(regNo)){
			params.put("regNo", regNo.trim());
		}
		
		String appDateTemp = params.get("appEndDate");
		//查询条件中截止日期天数+1
		if(StringUtil.isNotBlank(appDateTemp)){
			appDateTemp = AssignUtil.getDateAdd(appDateTemp);
			
			params.put("appDateTemp", appDateTemp);
		}
		
		
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
	}

}
