package com.gwssi.ebaic.apply.entaccount.event;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 更新企业管理员操作信息权限
 * 
 * @author ye,
 *         细则查询ApplySetupFrContactBeforeEvent和ApplySetupMbrAddAfterEvent两个类的demo
 */
@Component("updateEntManagerAccountOperationBeforeEvent")
public class UpdateEntManagerAccountOperationBeforeEvent implements OnEventListener {

	public void execEdit(TorchController controller, Map<String, String> params, EditConfigBo editConfigBo,
			Object result) {

		if (params == null || params.isEmpty()) {
			throw new RodimusException("获取页面信息失败,系统繁忙中，请稍后再试，谢谢");
		}

		String mobile = params.get("mobile");
		if (mobile == null) {
			throw new RodimusException("手机号不能为空，请输入手机号!");
		}

		String verCode = params.get("mobileCode");
		if (verCode == null) {
			throw new RodimusException("校验码不能为空，请输入手机接收到的认证码!");
		}
		String name = params.get("name");
		if (StringUtil.isBlank(name)) {
			throw new RodimusException("管理员名称不能为空，请输入管理员名称!");
		}
		String cerNo = params.get("cerNo");
		if (StringUtil.isBlank(cerNo)) {
			throw new RodimusException("管理员身份证号不能为空，请输入管理员身份证号!");
		}
		boolean isIdCardOk = IdentityCardUtil.check(name, cerNo);
		if(!isIdCardOk){
			throw new EBaicException("姓名和身份证号码不匹配，请查对后重新输入。");
		}
		// SmsVerCodeUtil.send(smsCode);

		SmsVerCodeUtil.verify(mobile, verCode);//验证码手机和对应的手机验证码

		/*
		 * String managerId = params.get("managerId");
		 * 
		 * String sql =
		 * "select operation from cp_account_manager where manager_id  = ?";
		 * String operation = (String) DaoUtil.getInstance().queryForOne(sql,
		 * managerId);
		 * 
		 * params.put("operation", operation);
		 */

	}

	public void execQuery(TorchController controller, Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {

		if (params == null || params.isEmpty()) {
			throw new RodimusException("系统繁忙，请稍后再试");
		}

		/*
		 * String managerId = params.get("managerId");
		 * 
		 * // 拆分对应表里面管理员权限，并将对应的复选框选中 String sql =
		 * "select operation from cp_account_manager where manager_id  = ?";
		 * String operation = (String) DaoUtil.getInstance().queryForOne(sql,
		 * managerId);
		 * 
		 * params.put("operation", operation);
		 */

	}

}
