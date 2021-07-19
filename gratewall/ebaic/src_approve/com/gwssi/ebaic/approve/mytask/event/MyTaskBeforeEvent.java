package com.gwssi.ebaic.approve.mytask.event;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.approve.util.ApproveUserUtil;
import com.gwssi.ebaic.approve.util.AssignUtil;
import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.ebaic.torch.event.BaseEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 我的任务，执行前方法，获取当前用户ID。
 * 
 * @author xupeng
 */
@Service("myTaskBeforeEvent")
public class MyTaskBeforeEvent extends BaseEventListener{
	
	@Override
	public void exec(Map<String, String> params) {
		
		SysmgrUser sysmgrUser=ApproveUserUtil.getLoginUser();
		if(null == sysmgrUser){
			throw new EBaicException("登录超时，请重新登录。");
		}
		params.put("censorUserId", sysmgrUser.getUserId());
		params.put("approveUserId", sysmgrUser.getUserId());
		
		String appEndDate = params.get("appEndDate");
		
		//查询条件去掉前后空格，并且判断根据名称进行查询的方式(1-以...开头     0-模糊)
		String queryType = params.get("queryType");
		String entName = params.get("Name");
		String regNo = params.get("regNo");
		if(StringUtil.isNotBlank(entName)){
			params.put("Name", entName.trim());
		}
		if(StringUtil.isNotBlank(queryType)){
			if("1".equals(queryType)){
				entName = entName+"%";
			}
		}
		if(StringUtil.isNotBlank(regNo)){
			params.put("regNo", regNo.trim());
		}
		
		//统一社会信用代码小写转大写
		String uniScid = params.get("uniScid");
		if(StringUtil.isNotBlank(uniScid)){
			uniScid = uniScid.toUpperCase();
			params.put("uniScid", uniScid.trim());
		}
		
		//查询条件中截止日期+1
		if(StringUtil.isNotBlank(appEndDate)){
			appEndDate = AssignUtil.getDateAdd(appEndDate);
			
			params.put("appEndDate", appEndDate);
		}
	}
	
}
