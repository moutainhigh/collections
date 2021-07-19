package com.gwssi.ebaic.approve.approval.contrller;

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
@Service("approveBeforeEvent")
public class ApproveBeforeEvent implements OnEventListener {

    public void execQuery(TorchController controller,
            Map<String, String> params, QueryConfigBo editConfigBo,Object result) {
        //输入的查询条件去除空格
    	String queryType = params.get("queryType");   //1-以...开头   2-模糊
        String entName = params.get("entName");
        String uniScid = params.get("uniScid");
        String regNo = params.get("regNo");
        String nameAppId = params.get("nameAppId");
        if(StringUtil.isNotBlank(entName) && StringUtil.isNotBlank(queryType)){
        	if("1".equals(queryType)){
        		entName = entName+"%";
        	}
            params.put("entName", entName.trim());
        }
        if(StringUtil.isNotBlank(uniScid)) {
            params.put("uniScid", uniScid.trim().toUpperCase());
        }
        if(StringUtil.isNotBlank(regNo)) {
            params.put("regNo", regNo.trim());
        }
        if(StringUtil.isNotBlank(nameAppId)) {
            params.put("nameAppId", nameAppId.trim());
        }
        //因当前开发只针对海淀。。先写死
        SysmgrUser sysmgrUser=ApproveUserUtil.getLoginUser();
        if (null==sysmgrUser) {
        	throw new EBaicException("登录超时，请重新登录。");
		}
        params.put("regOrg", sysmgrUser.getOrgCodeFk());
        
        //当前审核人员ID
        params.put("censorUserId", sysmgrUser.getUserId());
        
        
        //判断查询条件中的日期是否合法
        String appDateEnd = params.get("appDateEnd");
        //查询条件中截止日期天数+1
        if(StringUtil.isNotBlank(appDateEnd)){
            appDateEnd = AssignUtil.getDateAdd(appDateEnd);
            params.put("appDateEnd", appDateEnd);
        }
    }

    public void execEdit(TorchController controller,
            Map<String, String> params, EditConfigBo editConfigBo,Object result) {
        System.out.println("execEdit before");
    }


}
