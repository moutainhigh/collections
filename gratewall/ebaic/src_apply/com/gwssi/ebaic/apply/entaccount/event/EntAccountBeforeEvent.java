package com.gwssi.ebaic.apply.entaccount.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.entaccount.domain.EntAccountBo;
import com.gwssi.ebaic.apply.util.SessionConst;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.dao.DaoUtil;
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
@Service("entAccountBeforeEvent")
public class EntAccountBeforeEvent implements OnEventListener {

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		if (params == null || params.isEmpty()) {
			throw new EBaicException("获取页面信息失败!");
		}
		String appBeginDate = params.get("appBeginDate");
		String appEndDate = params.get("appEndDate");
		String operationType = params.get("operationType");
		String state = params.get("state");
		String linkman = params.get("linkman");
		if(appBeginDate!=null&&"".equals(appBeginDate)){
			params.remove("appBeginDate");
		}
		if(appEndDate!=null&&"".equals(appEndDate)){
			params.remove("appEndDate");
		}
		if("00".equals(operationType)){
			params.remove("operationType");
		}
		if("00".equals(state)){
			params.remove("state");
		}
		if(linkman!=null&&"".equals(linkman)){
			params.remove("linkman");
		}
		
		HttpSession session = HttpSessionUtil.getSession();
		EntAccountBo account = (EntAccountBo)session.getAttribute(SessionConst.ENT_ACCOUNT);
		if(account==null){
			 throw new EBaicException("登录超时，请重新登录。");
		}
		String sql = "select not_no from nm_rs_name n where n.ent_name = ?";
		List<String> param = new ArrayList<String>();
		param.add(account.getEntName());
		String nameAppId = DaoUtil.getInstance().queryForOneString(sql, param);
		
		params.put("nameAppId", nameAppId);
//		params.put("nameAppId", "(京昌)名称预核(个)字 [2017] 第 0061990 号");
		
		
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
	}

	

}
