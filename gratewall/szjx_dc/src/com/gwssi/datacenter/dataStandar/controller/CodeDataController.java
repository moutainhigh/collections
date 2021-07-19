package com.gwssi.datacenter.dataStandar.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import javassist.bytecode.CodeIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.datacenter.dataStandar.service.CodeDataService;
import com.gwssi.datacenter.model.DcStandarCodeindexBO;
import com.gwssi.datacenter.model.DcStandardCodedataBO;
import com.gwssi.datacenter.model.DcStandardCodeindexBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

@Controller
@RequestMapping("/codeDataList")
public class CodeDataController extends BaseController{
	 
	@Autowired
	private CodeDataService codeDataService;
	
	/**进入标准代码页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getCodeDataPage")
	public void getCodeDataPage(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String standardCodeindex = req.getParameter("standardCodeindex");
		
		//封装数据
		resp.addAttr("standardCodeindex", standardCodeindex);
		
		//进入标准代码查询管理页面
		resp.addPage("/page/dataStandard/codeData_list.jsp");
		
	}
	
	/**通过主键查询标准代码集的标识符、代码集名称
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getCodeSetById")
	public void getCodeSetById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String standardCodeindex = req.getParameter("standardCodeindex");
				
		//通过主键查询标准代码集
		DcStandardCodeindexBO dcStandardCodeindexBO = codeDataService.getCodeSetById(standardCodeindex);
				
		//封装数据并返回前台
		resp.addForm("formpanel", dcStandardCodeindexBO);
		
	}
	
	/**通过代码值、代码内容查询标准代码
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("query")
	public void getCodeData(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取formpanel中填写的数据
		Map params = req.getForm("formpanel");
		
		//获取参数
		String standardCodeindex = req.getParameter("standardCodeindex");
				
		//根据代码集标识、代码值、内容查询标准代码
		List list = codeDataService.getCodeData(params,standardCodeindex);
				
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);	
	}
	
	/** 
	 * 进入标准代码新增页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	
	@RequestMapping("addCodeData")
	public void addCodeData(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取参数
		String standardCodeindex = req.getParameter("standardCodeindex");
		String codeindexCode = req.getParameter("codeindexCode");
		String type = req.getParameter("type");
		
		//封装数据
		resp.addAttr("standardCodeindex",standardCodeindex);
		resp.addAttr("codeindexCode",codeindexCode);
		resp.addAttr("type", type);
		
		//进入标准代码新增页面
		resp.addPage("/page/dataStandard/codeData_edit.jsp");
	}
	
	/** 
	 * 保存新增的标准代码
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveCodeDataAdd")
	public void saveCodeDataAdd(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		String back = "fail";
		
		//获取formpanel中填写的数据
		DcStandardCodedataBO dcStandardCodedataBO = req.getForm("formpanel_edit", DcStandardCodedataBO.class);
		String standardCodeindex = req.getParameter("standardCodeindex");
		String codeindexCode = dcStandardCodedataBO.getCodeindexCode();
		//通过标准代码集表主键、代码值查询
		DcStandardCodedataBO oldDc = codeDataService.queryCodeData(standardCodeindex,codeindexCode);
		
		//当添加的记录不存在时
		if(oldDc == null){
			//保存新增的信息
			codeDataService.saveCodeDataAdd(dcStandardCodedataBO);
			back = "success";
		} 
		
		//封装结果集
		resp.addAttr("back",back);
	}
	
	/**
	 * 删除标准代码
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("delete")
	public void deleteCodeData(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取主键
		String standardCodeindex = req.getParameter("standardCodeindex");
		String codeindexCode = req.getParameter("codeindexCode");
		
		//通过主键删除标准代码
		codeDataService.deleteCodeData(standardCodeindex,codeindexCode);
		
		//封装数据并返回前台
		resp.addAttr("back","success");
	}
	
	/**
	 * 将选中的标准代码记录回显
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("show")
	public void getCodeDataById(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取主键
		String standardCodeindex = req.getParameter("standardCodeindex");
		String codeindexCode = req.getParameter("codeindexCode");
		
		//通过主键查询记录是否存在
		DcStandardCodedataBO dcStandardCodedataBO =	codeDataService.getCodeDataById(standardCodeindex,codeindexCode);
		
		//封装数据并返回前台
		resp.addForm("formpanel_edit",dcStandardCodedataBO);
	}
	
	/**
	 * 保存编辑的标准代码
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveCodeDataUpdate")
	public void saveCodeDataUpdate(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		//获取formpanel中填写的数据
		DcStandardCodedataBO dcStandardCodedataBO = req.getForm("formpanel_edit", DcStandardCodedataBO.class);
		
		//保存修改的记录
		codeDataService.saveCodeDataUpdate(dcStandardCodedataBO);
	
		//封装数据
		resp.addAttr("back", "success");
	}
}
