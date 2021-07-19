package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

@Service("applySetupContactBeforeEvent")
public class ApplySetupContactBeforeEvent implements OnEventListener{

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		if(params == null || params.isEmpty()){
			throw new EBaicException("获取页面信息失败!");
		}
		
		// 查验身份证号合法性
		String cerType = params.get("cerType");
		if("1".equals(cerType)){// 如果是身份证
			String name = params.get("textName");
			String cerNo = params.get("cerNo");
			boolean idCheckResult = IdentityCardUtil.check(name, cerNo);
			if(!idCheckResult){
				throw new EBaicException("企业联系人姓名和身份证号码不匹配，请查验后重新输入。");
			}
		}
		String name = params.get("name");
		String textName = params.get("textName");
		if(StringUtils.isEmpty(name) || StringUtils.isEmpty(textName)){
			throw new EBaicException("获取名称信息异常，请联系管理员");
		}
		//由于前台是autocompletecomboxfield，想保留下拉框的值这设置隐藏域，并把值赋给他
		name = textName;
		params.put("name", name);
		
//		String cerValFrom = params.get("cerValFrom");
		//String cerValTo = params.get("cerValTo");
		/*if( StringUtils.isBlank(cerValFrom) ){
			throw new EBaicException("获取页面信息失败!");
		}*/
		//由于torch bug，暂且把插入证件有效期的放在after事件中
		/*params.put("cerValFrom", null);
		params.put("cerValTo", null);*/
	}
	
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
	}
}
