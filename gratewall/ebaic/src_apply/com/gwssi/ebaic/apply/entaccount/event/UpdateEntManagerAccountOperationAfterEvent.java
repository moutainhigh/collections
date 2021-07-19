package com.gwssi.ebaic.apply.entaccount.event;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.ParamUtil;
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
@Component("updateEntManagerAccountOperationAfterEvent")
public class UpdateEntManagerAccountOperationAfterEvent implements OnEventListener {

	public void execEdit(TorchController controller, Map<String, String> params, EditConfigBo editConfigBo,
			Object result) {

		if (params == null || params.isEmpty()) {
			throw new EBaicException("获取页面信息失败!");
		}
		
		String operation = ParamUtil.get("operation");
//		String operation = params.get("operation");// 读取到已经赋值的用户操作权限的复选框下面选中的值，按逗号隔开
		params.put("operation", operation);
		String managerId = params.get("managerId");// 根据对应的企业的身份标识更新管理员对应的可选操作权限
		if (!StringUtil.isBlank(operation)) {
			String sql = "update cp_account_manager set operation =? where manager_id = ?";// 将码表里面的数字更新管理员账号表里面的操作权限字段
			DaoUtil.getInstance().execute(sql, operation, managerId);// 更新对应的管理员权限复选框选中的列表
		}

	}

	public void execQuery(TorchController controller, Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {

		if (params == null || params.isEmpty()) {
			throw new EBaicException("系统繁忙，请稍后再试");
		}
		
		/*String managerId =  params.get("managerId");
		
		// 拆分对应表里面管理员权限，并将对应的复选框选中
		String sql = "select operation from cp_account_manager where manager_id  = ?";
		String operation =   (String) DaoUtil.getInstance().queryForOne(sql, managerId);
		
		params.put("operation", operation);*/
		
	}

}
