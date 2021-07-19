package com.gwssi.datacenter.dataStandar.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import javassist.bytecode.CodeIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;









import com.gwssi.datacenter.dataStandar.service.SystemCodeDataService;
import com.gwssi.datacenter.model.DcDmJcdmFxBO;
import com.gwssi.datacenter.model.DcStandardCodedataBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

@Controller
@RequestMapping("/sysCodeDataList")
public class sysCodeDataController extends BaseController{
	 
	@Autowired
	private SystemCodeDataService systemCodeDataService;
	
	/**进入标准代码页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getCodeDataPage")
	public void getCodeDataPage(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String standardCodeindex = req.getParameter("dcDmId");
		
		//封装数据
		resp.addAttr("dcDmId", standardCodeindex);
		
		//进入标准代码查询管理页面
		resp.addPage("/page/dataStandard/sysCodeData_list.jsp");
		
	}
	/**
	 * 查询系统代码所对应的代码值
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void getCodeData(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取formpanel中填写的数据
		Map params = req.getForm("formpanel");
		
		//获取参数
		String dcDmId = req.getParameter("dcDmId");
				
		//根据代码集标识、代码值、内容查询标准代码
		List list = systemCodeDataService.getCodeData(params,dcDmId);
				
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);	
	}
	
	/** 
	 * 进入系统代码值新增页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("addCodeData")
	public void addCodeData(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String dcDmId = req.getParameter("dcDmId");
		String dcSjfxId = req.getParameter("dcSjfxId");
		String type = req.getParameter("type");
		
		//封装数据
		resp.addAttr("dcDmId",dcDmId);
		resp.addAttr("dcSjfxId",dcSjfxId);
		resp.addAttr("type", type);
		
		//进入标准代码新增页面
		resp.addPage("/page/dataStandard/sysCodeData_edit.jsp");
	}
	
	@RequestMapping("saveCodeDataAdd")
	public void saveCodeDataAdd(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		String back = "fail";
		
		//获取formpanel中填写的数据
		DcDmJcdmFxBO bo = req.getForm("formpanel_edit", DcDmJcdmFxBO.class);
		String dcDmId = req.getParameter("dcDmId");
		
		String sjfxDm=bo.getDcSjfxDm();

		//通过标准代码集表主键、代码值查询
		DcDmJcdmFxBO oldDc = systemCodeDataService.queryCodeData(dcDmId,sjfxDm);		//获取当前用户
/*		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	*/


		//当添加的记录不存在时
		if(oldDc == null){
			//保存新增的信息
			systemCodeDataService.dosaveCodeDataAdd(bo);
			back = "success";
		} 
		
		//封装结果集
		resp.addAttr("back",back);
	}
	
	
	@RequestMapping("show")
	public void getCodeDataById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取主键
		String dcSjfxId = req.getParameter("dcSjfxId");
	
		
		//通过主键查询记录是否存在
		DcDmJcdmFxBO dcStandardCodedataBO =	systemCodeDataService.dogetCodeDataById(dcSjfxId);
		
		//封装数据并返回前台
		resp.addForm("formpanel_edit",dcStandardCodedataBO);
	}
	
	@RequestMapping("saveCodeDataUpdate")
	public void saveCodeDataUpdate(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取formpanel中填写的数据
		DcDmJcdmFxBO bo = req.getForm("formpanel_edit", DcDmJcdmFxBO.class);
		
		//保存修改的记录
		systemCodeDataService.dosaveCodeDataUpdate(bo);
	
		//封装数据
		resp.addAttr("back", "success");
	}
	
	@RequestMapping("delete")
	public void deleteCodeData(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取主键
		String dcSjfxId = req.getParameter("dcSjfxId");


		systemCodeDataService.dodeleteCodeData(dcSjfxId);
		
		//封装数据并返回前台
		resp.addAttr("back","success");
	}
	
	
}
