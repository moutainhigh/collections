package com.gwssi.datacenter.dataStandar.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.dataStandar.service.CodeListService;
import com.gwssi.datacenter.model.DcStandardCodeindexBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

@Controller
@RequestMapping("/codeList")
public class CodeListController extends BaseController{

	@Autowired
	private CodeListService codeListService;
	
	/**进入标准代码集管理页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("codeSetPage")
	public void getCodeSetPage(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//进入标准代码集的页面
		resp.addPage("/page/dataStandard/codeSet_list.jsp");
	}
	
	/**通过标识符、代码集名称查询标准代码集
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void getCodeSet(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取formpanel中填写的数据
		Map params = req.getForm("formpanel");
				
		//根据标识符、字段名称查询标准元数据
		List list = codeListService.getCodeSet(params);
				
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);	
	}
	
	/**进入标准代码集新增页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("addCodeSet")
	public void addCodeSet(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String type = req.getParameter("type");
		String standardCodeindex = req.getParameter("standardCodeindex");
		
		//封装数据
		resp.addAttr("type", type);
		resp.addAttr("standardCodeindex", standardCodeindex);
		
		//进入标准代码集新增页面
		resp.addPage("/page/dataStandard/codeSet_edit.jsp");
		
	}
	
	/**设置创建、最后修改的id、name、time
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("setMessage")
	public void setMessage(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String standardCodeindex = req.getParameter("standardCodeindex");
		String type = req.getParameter("type");
		
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	
		
		//创建对象
		DcStandardCodeindexBO dcStandardCodeindexBO = new DcStandardCodeindexBO();
		
		//新增时设置创建、最后修改信息
		if(type.equals("add") ){
			dcStandardCodeindexBO.setCreaterId(user.getUserId());
			dcStandardCodeindexBO.setCreaterName(user.getUserName());
			dcStandardCodeindexBO.setCreaterTime(Calendar.getInstance());
			dcStandardCodeindexBO.setModifierId(user.getUserId());
			dcStandardCodeindexBO.setModifierName(user.getUserName());
			dcStandardCodeindexBO.setModifierTime(Calendar.getInstance());
			
			//封装数据并返回前台
			resp.addForm("formpanel",dcStandardCodeindexBO);
		}
		
	} 
	
	/**保存新增的标准代码集
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveCodeSetAdd")
	public void saveCodeSetAdd(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String back ="fail";
		
		//获取formpanel中填写的数据
		Map<String,String> map = req.getForm("formpanel_edit");
		DcStandardCodeindexBO dcStandardCodeindexBO = req.getForm("formpanel_edit", DcStandardCodeindexBO.class);
				
		//通过标识符查询该代码集
		DcStandardCodeindexBO oldDc = codeListService.queryCodeSetById(dcStandardCodeindexBO.getStandardCodeindex());
		DcStandardCodeindexBO oldDc1 = codeListService.queryCodeSetByIdN(dcStandardCodeindexBO.getStandardCodeindex());
		
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	
		
		//当oldDc1不为空时表示删除的记录中存在该标识符
		if(oldDc1 != null){
			
			dcStandardCodeindexBO.setCreaterId(user.getUserId());
			dcStandardCodeindexBO.setCreaterName(user.getUserName());
			dcStandardCodeindexBO.setCreaterTime(Calendar.getInstance());
			dcStandardCodeindexBO.setModifierId(user.getUserId());
			dcStandardCodeindexBO.setModifierName(user.getUserName());
			dcStandardCodeindexBO.setModifierTime(Calendar.getInstance());
			
			//将已经删除的信息修改后保存
			codeListService.updateCodeSetAdd(dcStandardCodeindexBO);
			back="success";
		}
		else{
		
		
			//判断添加的记录是否已存在
			if(oldDc == null){
				dcStandardCodeindexBO.setCreaterId(user.getUserId());
				dcStandardCodeindexBO.setCreaterTime(Calendar.getInstance());
				dcStandardCodeindexBO.setModifierId(user.getUserId());
				dcStandardCodeindexBO.setModifierTime(Calendar.getInstance());
				dcStandardCodeindexBO.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
		    
				//保存新增的信息
				codeListService.saveCodeSetAdd(dcStandardCodeindexBO);
				back="success";		
					
			}
		}
		//封装数据并返回前台
			resp.addAttr("back", back);
		
				
	}
	
	/**删除标准代码集(将有效标记设为N)
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("delete")
	public void deleteCodeSet(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取主键
		String standardCodeindex = req.getAttr("standardCodeindex").toString();
		
		//通过标识查询代码值
		List list = codeListService.getCodeDataById(standardCodeindex);
		
		if(!list.isEmpty()){
			//删除该代码集中所有的代码值
			codeListService.deleteCodeData(standardCodeindex);
		}
		//通过主键删除标准代码集
		codeListService.deleteCodeSet(standardCodeindex);
		
		//封装数据并返回前台
		resp.addAttr("back", "success");
	}
	
	/**编辑、查看标准代码集，回显选中记录
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getCodeSetById")
	public void getCodeSetById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String standardCodeindex = req.getParameter("standardCodeindex");
		
		//通过主键查询标准代码集
		DcStandardCodeindexBO dcStandardCodeindexBO = codeListService.getCodeSetById(standardCodeindex);
		
		//封装数据并返回前台
		resp.addForm("formpanel_edit", dcStandardCodeindexBO);
	}
	
	/**保存编辑的标准代码集
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveCodeSetUpdate")
	public void saveCodeSetUpdate(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		String back ="fail";
	
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	
		
		//获取formpanel中填写的数据
		Map<String,String> map = req.getForm("formpanel_edit");
		DcStandardCodeindexBO dcStandardCodeindexBO = req.getForm("formpanel_edit", DcStandardCodeindexBO.class);
		dcStandardCodeindexBO.setModifierTime(Calendar.getInstance());
		dcStandardCodeindexBO.setModifierId(user.getUserId());
		dcStandardCodeindexBO.setModifierName(user.getUserName());
		
		//String 转换为Calendar
		String str=map.get("createrTime");
		if(StringUtils.isNotEmpty(str)){
			dcStandardCodeindexBO.setCreaterTime(codeListService.changeStringToCalendar(str));	
		}
		
		//修改标准代码集
		try{
			codeListService.saveCodeSetUpdate(dcStandardCodeindexBO);
			back = "success";
		}catch(Exception e){
			back="fail";
		}
		resp.addAttr("back", back);
		
	}
	
	/**
	 * 查询标准规范
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("queryStandardSpec")
	public void queryStandardSpec(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//查询标准规范
		List list = codeListService.queryStandardSpec();
		
		//封装数据并返回
		resp.addTree("pkDcStandardSpec", list);
	}
}
