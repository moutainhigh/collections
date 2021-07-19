package com.gwssi.ebaic.apply.entaccount.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.entaccount.domain.EntAccountBo;
import com.gwssi.ebaic.apply.entaccount.service.EntAccountService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.JSON;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.ParamUtil;

/**
 * 
 * 
 * @author qiaozy
 */
@Controller
@RequestMapping("/apply/entManagerAccount")
public class EntManagerAccountController {

	@Autowired
	EntAccountService entAccountService;

	/**
	 * 删除企业管理员用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteManagers")
	public void delete(OptimusRequest request, OptimusResponse response) throws OptimusException {
		String managerIds = ParamUtil.get("managerIds");
		entAccountService.deleteAccountManager(managerIds);
		response.addAttr("result", "success");
	}

	/**
	 * 切换企业管理员用户状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/updateManagers")
	public void updateState(OptimusRequest request, OptimusResponse response) throws OptimusException {
		String state = ParamUtil.get("state");
		String managerId = ParamUtil.get("managerId");
		entAccountService.updateAccountManagerState(state, managerId);
		response.addAttr("result", "success");
	}

	/**
	 * 查询管理员用户操作权限
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/queryOpr")
	public void queryOperation(OptimusRequest request, OptimusResponse response) throws OptimusException {
		String managerId = ParamUtil.get("managerId");
		String operation = entAccountService.queryOpr(managerId);
		response.addAttr("operation", operation);
	}

	/**
	 * 校验移动电话是否已被当前企业用户或管理员用户使用
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/checkMobileIsUsed")
	public void checkMobileIsUsed(OptimusRequest request, OptimusResponse response) throws OptimusException {
		String mobile = ParamUtil.get("mobile");
		String managerId = ParamUtil.get("managerId");
		boolean isUsed = entAccountService.checkMobileIsUsed(mobile, managerId);
		response.addAttr("isUsed", isUsed);
	}

	/**
	 * 通过entId查询企业信息(电子营业执照使用)
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/queryEntInfoByEntId")
	public void queryEntInfoByEntId(OptimusRequest request, OptimusResponse response) throws OptimusException {
		String gid = ParamUtil.get("gid");
		
		if(gid==null){
			throw new EBaicException("获取企业信息失败");
		}
		Map<String, Object> resultMap = entAccountService.queryEntInfoByEntId(gid);
		if (resultMap != null && resultMap.size() > 0) {
			response.addResponseBody(JSON.toJSON(resultMap));
		} else {
			throw new EBaicException("当前企业暂无执照");
		}
	}

	/**
	 * 获取uniScid(电子营业执照使用)
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/getUniScid")
	public void getUniScid(OptimusRequest request, OptimusResponse response) throws OptimusException {
		HttpSession session = HttpSessionUtil.getSession();
		// 如果输入的是法人移动电话
		EntAccountBo entAccount = (EntAccountBo) session.getAttribute("entAccount");
		if (entAccount != null) {
			String uniScid = entAccount.getUniScid();
			response.addResponseBody(uniScid);
		} else {
			throw new EBaicException("获取企业信息失败，可能是登录超时，请重新登录。");
		}
		// response.addAttr("gid",gid);
	}
}
