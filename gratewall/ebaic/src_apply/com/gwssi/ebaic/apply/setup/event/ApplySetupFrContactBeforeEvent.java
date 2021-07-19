package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

@Service("applySetupFrContactBeforeEvent")
public class ApplySetupFrContactBeforeEvent implements OnEventListener{

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
		if(params == null || params.isEmpty()){
			throw new EBaicException("获取页面信息失败!");
		}
		
		String name = params.get("name");
		String textFname = params.get("textFname");
		if(StringUtils.isEmpty(name) || StringUtils.isEmpty(textFname)){
			throw new EBaicException("获取名称信息异常，请联系管理员");
		}
		
		//由于前台是autocompletecomboxfield，想保留下拉框的"文本值",则需设置隐藏域，并把值赋给他
		name = textFname;
		params.put("name", name);
		
//		String cerValFrom = params.get("fcerValFrom");
		//String cerValTo = params.get("fcerValTo");
		/*if( StringUtils.isBlank(cerValFrom) ){
			throw new EBaicException("获取页面信息失败!");
		}*/
		//由于torch bug，暂且把插入证件有效期的放在after事件中
		/*params.put("fcerValFrom", null);
		params.put("fcerValTo", null);*/
		
		// 查验身份证号合法性
		String cerType = params.get("fcerType");
		String cerNo = params.get("cerNo");
		if("1".equals(cerType)){// 如果是身份证
			boolean idCheckResult = IdentityCardUtil.check(name, cerNo);
			if(!idCheckResult){
				throw new EBaicException("财务负责人姓名和身份证号码不匹配，请查验后重新输入。");
			}
		}
		
		//检查财务负责人是否是监事
		String sql = "select count(1) as cnt from be_wk_entmember m left join be_wk_job j on m.entmember_id = j.entmember_id "
				+ " where j.position_type = '3' and m.cer_type=? and m.cer_no=? and m.gid = ?";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, cerType,cerNo,params.get("gid"));
		if(cnt>0){
			throw new EBaicException("监事不能兼任财务负责人，请查验后重新输入。");
		}	
	}
	
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
	}


}
