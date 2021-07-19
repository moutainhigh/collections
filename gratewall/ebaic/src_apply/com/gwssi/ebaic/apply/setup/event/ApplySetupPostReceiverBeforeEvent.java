package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.util.CpParamUtil;
import com.gwssi.ebaic.domain.BeWkReqBO;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 
 * @author lxb
 *
 */
@Service("applySetupPostReceiverBeforeEvent")
public class ApplySetupPostReceiverBeforeEvent implements OnEventListener {

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtil.isBlank(userId)){
			throw new EBaicException("登录超时，请重新登陆。");
		}
		params.put("userId", userId);
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
		OptimusRequest request = RequestUtil.getOptimusRequest();
		String gid = request.getParameter("gid");
		if(StringUtil.isBlank(gid)) {
			 throw new EBaicException("传入的业务gid不能为空。");
		}
		params.put("gid", gid);
		TPtYhBO user = HttpSessionUtil.getCurrentUser();
		if(user==null){
			throw new EBaicException("登录超时，请重新登陆。");
		}
		params.put("userId", user.getUserId());
		String mobileCode = params.get("mobileCode");
		String mobile = user.getMobile();
		//验证码校验
		SmsVerCodeUtil.verify(mobile, mobileCode);
		/** 寄递信息保存时需要提供
		 *	kdgs --快递公司 默认就是"ems"
		 * 	ddsljg --订单受理机构（分局代码:110108000）
		 *	jdd_csdm--寄达地城市代码（用户所在区6位代码：110105）
		 *	sfd_csdm --始发地城市代码（工商局所在区6位代码：110108）
		 **/
		BeWkReqBO reqBo = CpParamUtil.getReqBo();
		String regOrg = reqBo.getRegOrg();
		if(StringUtil.isBlank(regOrg)){
			throw new EBaicException("数据异常：业务核准机关为空。");
		}
		params.put("ddsljg", regOrg);
		String sfdCsdm = regOrg.substring(0, 6);
		params.put("sfdCsdm", sfdCsdm);
		String jddCsdm = params.get("receiverCity");
		params.put("jddCsdm", jddCsdm);
		
		//将库里原有的记录（如果有） 状态设置为失效 
		//用于退回修改时再次提交
		//同一笔业务只能有一条有效的邮寄记录
		String sql = "UPDATE BE_WK_POST_RECEIVER SET FLAG='0' WHERE GID=?";
		DaoUtil.getInstance().execute(sql, gid);
	}

}
