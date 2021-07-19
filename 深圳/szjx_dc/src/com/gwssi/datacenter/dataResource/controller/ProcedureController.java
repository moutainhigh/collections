package com.gwssi.datacenter.dataResource.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.dataResource.service.ProcedureService;
import com.gwssi.datacenter.model.DcProcedureBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

@Controller
@RequestMapping("/proceList")
public class ProcedureController extends BaseController{
	
	@Autowired
	private ProcedureService proceService;
	
	/**
	 * 进入存储过程管理页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getProcePage")
	public void getProcePage(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//进入存储过程管理页面
		resp.addPage("/page/dataResource/proce_list.jsp");
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
		List list = proceService.queryBusiObjectName();
		
		//封装数据并返回
		resp.addTree("pkDcBusiObject", list);
	}
	
	/**
	 * 通过条件查询存储过程信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void queryProceList(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		Map param = req.getForm("formpanel");
		
		//通过条件查询
		List list = proceService.queryProceList(param);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
	}
	
	/**
	 * 进入存储过程修改页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("updateProce")
	public void updateProce(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		String pkDcProcedure = req.getParameter("pkDcProcedure");
		String type = req.getParameter("type");
		
		//封装数据
		resp.addAttr("pkDcProcedure", pkDcProcedure);
		resp.addAttr("type", type);
		
		//进入修改存储过程页面
		resp.addPage("/page/dataResource/proce_edit.jsp");
	}
	
	/**
	 * 将选中的存储过程信息回显
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("showProce")
	public void showProce(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		String pkDcProcedure = req.getParameter("pkDcProcedure");
		
		//通过主键查询存储过程
		List<Map> list = proceService.queryProceListById(pkDcProcedure);
		
		//封装数据并返回前台
		resp.addAttr("pkDcProcedure", list.get(0).get("pkDcProcedure"));
		resp.addAttr("procName", list.get(0).get("procName"));
		resp.addAttr("busiObjectName", list.get(0).get("busiObjectName"));
		resp.addAttr("procUseDesc", list.get(0).get("procUseDesc"));
		resp.addAttr("procSql", list.get(0).get("procSql"));
		resp.addAttr("createrName", list.get(0).get("createrName"));
		resp.addAttr("createrTime", list.get(0).get("createrTime"));
		resp.addAttr("modifierName", list.get(0).get("modifierName"));
		resp.addAttr("modifierTime", list.get(0).get("modifierTime"));
		resp.addAttr("remarks", list.get(0).get("remarks"));
	}
	
	/**
	 * 查看存储过程信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("checkProce")
	public void checkProce(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		String pkDcProcedure = req.getParameter("pkDcProcedure");
		String type = req.getParameter("type");
				
		//封装数据
		resp.addAttr("pkDcProcedure", pkDcProcedure);
		resp.addAttr("type", type);
				
		//进入修改存储过程页面
		resp.addPage("/page/dataResource/proce_edit.jsp");
	}
	
	/**
	 * 保存修改的内容
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveUpdateProce")
	public void saveUpdateProce(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取数据
		DcProcedureBO dcProcedureBO = req.getForm("formpanel_edit",DcProcedureBO.class);
		
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	
		
		dcProcedureBO.setModifierId(user.getUserId());
		dcProcedureBO.setModifierName(user.getUserName());
		dcProcedureBO.setModifierTime(Calendar.getInstance());
		
		dcProcedureBO.setIsCheck(AppConstants.EFFECTIVE_Y);
		proceService.saveUpdateProce(dcProcedureBO);
		
		//封装数据
		resp.addAttr("back","success");	
		
	}
	
}
