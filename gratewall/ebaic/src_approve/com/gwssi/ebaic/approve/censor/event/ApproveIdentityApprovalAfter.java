package com.gwssi.ebaic.approve.censor.event;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.QueryBaseEventListener;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.rodimus.indentity.domain.IdentityCardBO;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.db.result.PageBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 辅助审查-身份认证
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service("approveIdentityApprovalAfter")
public class ApproveIdentityApprovalAfter extends QueryBaseEventListener{

	@Override
	public void exec(TorchController controller, Map<String, String> formData, QueryConfigBo queryConfigBo,
			Object result) {
		// 0. 参数判断
		if(result==null){
			return ;
		}
		if(!(result instanceof PageBo)){
			return ;
		}
		PageBo pageBo = (PageBo) result;
		
		List<Map<String,Object>> list = pageBo.getResult();
		if(list==null || list.isEmpty()){
			return ;
		}
		// 1. 循环处理数据，调用公安部接口获得图像信息
		String name,cerType,cerNo,appUserId  ;
		IdentityCardBO identityBo;
		for(Map<String,Object> row : list){
			try {
				name = StringUtil.safe2String(row.get("name"));
				cerType = StringUtil.safe2String(row.get("cerType"));
				cerNo = StringUtil.safe2String(row.get("cerNo"));
				if(!"1".equals(cerType)){// 如果证件类型不是身份证，则无法处理
					continue ;
				}
				identityBo = IdentityCardUtil.getIdentityCardInfo(name, cerNo);
				if(identityBo==null || StringUtil.isBlank(identityBo.getFolk())){// 调用公安部接口，校验失败，则无法处理
					continue ;
				}
				row.put("identityPicUrl", identityBo.getPicUrl());// 获得身份图像路径，放入返回结果
				
				//判断app_user_id是否为空，为空则不能进行审批
				appUserId = StringUtil.safe2String(row.get("appUserId"));
				if(StringUtil.isBlank(appUserId)){
					throw new OptimusException("身份认证审批申请未绑定网登用户!");
				}
				
			} catch (OptimusException e) {
				throw new EBaicException(e.getMessage(),e);
			}
		}
		
	}
}
