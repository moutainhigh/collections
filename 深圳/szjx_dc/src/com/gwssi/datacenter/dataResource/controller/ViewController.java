package com.gwssi.datacenter.dataResource.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.dataResource.service.ViewService;
import com.gwssi.datacenter.model.DcViewBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

@Controller
@RequestMapping("/viewList")
public class ViewController extends BaseController{
	
	@Autowired
	private ViewService viewService;
	
	/**
	 * 进入视图管理页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getViewPage")
	public void getViewPage(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//进入视图管理页面
		resp.addPage("/page/dataResource/view_list.jsp");
	}
	
	/**
	 * 通过条件查询视图信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void queryViewList(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		Map param = req.getForm("formpanel");
		
		//通过条件查询
		List list = viewService.queryViewList(param);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
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
		List list = viewService.queryBusiObjectName();
		
		//封装数据并返回
		resp.addTree("pkDcBusiObject", list);
	}
	
	/**
	 * 进入视图修改页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("updateView")
	public void updateView(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		String pkDcView = req.getParameter("pkDcView");
		String type = req.getParameter("type");
		
		//封装数据
		resp.addAttr("pkDcView", pkDcView);
		resp.addAttr("type", type);
	
		//进入修改视图界面
		resp.addPage("/page/dataResource/view_edit.jsp");
	}
	
	/**
	 * 将选中的视图 信息回显
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("showView")
	public void showView(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		String pkDcView = req.getParameter("pkDcView");
		
		//通过主键查询视图
		List<Map> list = viewService.queryViewListById(pkDcView);
		
		//封装数据并返回前台
		resp.addAttr("pkDcView", list.get(0).get("pkDcView"));
		resp.addAttr("viewName", list.get(0).get("viewName"));
		resp.addAttr("busiObjectName", list.get(0).get("busiObjectName"));
		resp.addAttr("viewUseDesc", list.get(0).get("viewUseDesc"));
		resp.addAttr("viewSql", list.get(0).get("viewSql"));
		resp.addAttr("createrName", list.get(0).get("createrName"));
		resp.addAttr("createrTime", list.get(0).get("createrTime"));
		resp.addAttr("modifierName", list.get(0).get("modifierName"));
		resp.addAttr("modifierTime", list.get(0).get("modifierTime"));
		resp.addAttr("remarks", list.get(0).get("remarks"));
	}
	
	
	/**
	 * 查看视图信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("checkView")
	public void checkView(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		String pkDcView = req.getParameter("pkDcView");
		String type = req.getParameter("type");
				
		//封装数据
		resp.addAttr("pkDcView", pkDcView);
		resp.addAttr("type", type);
			
		//进入修改视图界面
		resp.addPage("/page/dataResource/view_edit.jsp");
	}
	
	
	/**
	 * 保存修改的视图信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveUpdateView")
	public void saveUpdateView(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取数据
		DcViewBO dcViewBO = req.getForm("formpanel_edit", DcViewBO.class);
		
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	
		
		dcViewBO.setModifierId(user.getUserId());
		dcViewBO.setModifierName(user.getUserName());
		dcViewBO.setModifierTime(Calendar.getInstance());
		
		dcViewBO.setIsCheck(AppConstants.EFFECTIVE_Y);
		
		//保存修改的数据
		viewService.saveUpdate(dcViewBO);
		
		//封装数据并返回
		resp.addAttr("back", "success");
		
	}

}
