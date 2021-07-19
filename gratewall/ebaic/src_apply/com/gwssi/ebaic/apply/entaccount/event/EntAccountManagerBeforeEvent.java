package com.gwssi.ebaic.apply.entaccount.event;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.entaccount.domain.EntAccountBo;
import com.gwssi.ebaic.apply.util.SessionConst;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 根据企业名称查询名称预核文号，作为查询登陆后的业务信息的条件
 * @author qiaozy
 *
 */
@Service("entAccountManagerBeforeEvent")
public class EntAccountManagerBeforeEvent implements OnEventListener {

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		if (params == null || params.isEmpty()) {
			throw new EBaicException("获取页面信息失败!");
		}
		
		HttpSession session = HttpSessionUtil.getSession();
		EntAccountBo account = (EntAccountBo)session.getAttribute(SessionConst.ENT_ACCOUNT);
		if(account==null){
			 throw new EBaicException("登录超时，请重新登录。");
		}
		
		params.put("accountId", account.getAccountId());
//		params.put("accountId", "6");
		
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
	}

	

}
