package com.gwssi.datacenter.dataResource.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.dataResource.service.FunctionService;
import com.gwssi.datacenter.model.DcTriggerBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

@Controller
@RequestMapping("/functionList")
public class FunctionController extends BaseController{
	
	@Autowired
	private FunctionService functionService;
	
	/**
	 * 进入函数管理页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getFunctionPage")
	public void getFunctionPage(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//进入函数管理页面
		resp.addPage("/page/dataResource/function_list.jsp");
	}
	
	/**
	 * 查询业务系统
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryBusiObjectName")
	public void queryBusiObjectName(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//查询业务系统
		List list = functionService.queryBusiObjectName();
				
		//封装数据并返回
		resp.addTree("pkDcBusiObject", list);
	}
	
	
	/**
	 * 通过条件查询函数信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void queryFunctionList(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		Map param = req.getForm("formpanel");
		
		//通过条件查询
		List list = functionService.queryFunctionList(param);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
	}
	
	
	/**
	 * 进入函数修改页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("updateFunction")
	public void updateFunction(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		String pkDcTrigger = req.getParameter("pkDcTrigger");
		String type = req.getParameter("type");
		
		//封装数据
		resp.addAttr("pkDcTrigger", pkDcTrigger);
		resp.addAttr("type", type);
	
		//进入修改函数界面
		resp.addPage("/page/dataResource/function_edit.jsp");
	}
	
	
	/**
	 * 将选中的函数信息回显
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("showFunction")
	public void showFunction(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		String pkDcTrigger = req.getParameter("pkDcTrigger");
				
		//通过主键查询函数
		List<Map> list = functionService.queryFunctionListById(pkDcTrigger);
				
		//封装数据并返回前台
		resp.addAttr("pkDcTrigger", list.get(0).get("pkDcTrigger"));
		resp.addAttr("triggerName", list.get(0).get("triggerName"));
		resp.addAttr("busiObjectName", list.get(0).get("busiObjectName"));
		resp.addAttr("triggerUseDesc", list.get(0).get("triggerUseDesc"));
		resp.addAttr("createrName", list.get(0).get("createrName"));
		resp.addAttr("createrTime", list.get(0).get("createrTime"));
		resp.addAttr("modifierName", list.get(0).get("modifierName"));
		resp.addAttr("modifierTime", list.get(0).get("modifierTime"));
		resp.addAttr("remarks", list.get(0).get("remarks"));
			
			
	}
	
	
	/**
	 * 进入查看函数信息的界面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("checkFunction")
	public void checkFunction(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		String pkDcTrigger = req.getParameter("pkDcTrigger");
		String type = req.getParameter("type");
		
		//封装数据
		resp.addAttr("pkDcTrigger", pkDcTrigger);
		resp.addAttr("type", type);
	
		//进入修改函数界面
		resp.addPage("/page/dataResource/function_edit.jsp");
		
	}
	
	
	/**
	 * 保存修改的数据
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveUpdateFunction")
	public void saveUpdateFunction(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取数据
		DcTriggerBO dcTriggerBO = req.getForm("formpanel_edit", DcTriggerBO.class);
				
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	
				
		dcTriggerBO.setModifierId(user.getUserId());
		dcTriggerBO.setModifierName(user.getUserName());
		dcTriggerBO.setModifierTime(Calendar.getInstance());
		
		dcTriggerBO.setIsCheck(AppConstants.EFFECTIVE_Y);
				
		//保存修改的数据
		functionService.saveUpdate(dcTriggerBO);
				
		//封装数据并返回
		resp.addAttr("back", "success");
				
	}
}
