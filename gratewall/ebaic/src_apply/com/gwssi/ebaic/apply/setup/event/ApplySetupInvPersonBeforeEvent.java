package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;


@Service("applySetupInvPersonBeforeEvent")
public class ApplySetupInvPersonBeforeEvent implements OnEventListener{

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		TPtYhBO user = HttpSessionUtil.getCurrentUser();
		if(user==null){
			throw new EBaicException("登录超时，请重新登录");
		}
		
		
		// 查验身份证号合法性
		String cerType = params.get("cerType");
		String cerNo = params.get("cerNo");
		if("1".equals(cerType)){// 如果是身份证
			String name = params.get("inv");
			boolean idCheckResult = IdentityCardUtil.check(name, cerNo);
			if(!idCheckResult){
				throw new EBaicException("姓名和身份证号码不匹配，请查验后重新输入。");
			}
		}
		String investorId = params.get("investorId");
		String sql = "select gid from be_wk_investor i where i.investor_id=?";
		String gid = DaoUtil.getInstance().queryForOneString(sql, investorId);
		
		sql = "select count(1) as cnt from be_wk_investor i where i.investor_id<>? and i.cer_type=? and i.cer_no=? and i.gid = ?";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, investorId,cerType,cerNo,gid);
		if(cnt>0){
			throw new EBaicException("证件号码与其他股东重复，请查验后再保存。");
		}
		String applyUserId = DaoUtil.getInstance().queryForOneString("select app_user_id from be_wk_requisition where gid=?", gid);
		if(user.getUserId().equals(applyUserId)){
			//股东是申请人，默认已授权
			params.put("authToApplyUser", "1");
		}else{
			params.put("authToApplyUser", "0");
		}
		
	}

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		// do nothing
	}

}
