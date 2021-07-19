/**
 * 
 */
package com.gwssi.datacenter.dataManager.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.dataManager.service.ColumnManagerService;
import com.gwssi.datacenter.dataManager.service.TableManagerService;
import com.gwssi.datacenter.dataSource.service.DataSourceManagerService;
import com.gwssi.datacenter.model.DcBusiObjectBO;
import com.gwssi.datacenter.model.DcColumnBO;
import com.gwssi.datacenter.model.DcDataSourceBO;
import com.gwssi.datacenter.model.DcTableBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

/**
 * @author xiaohan
 *
 */

@Controller
@RequestMapping("/tableManager")
public class TableManagerController {

	@Autowired
	TableManagerService tableservice;

	@Autowired
	ColumnManagerService colunmservice;
	
	@Autowired
	DataSourceManagerService datasource;

	/**
	 * 表管理-列表始化页面跳转
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/initListTable")
	public void initListTable(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkDcBusiObject = req.getParameter("pkDcBusiObject");
		resp.addAttr("pkDcBusiObject", pkDcBusiObject);
		resp.addPage("/page/tableManager/table_list.jsp");
	}

	/**
	 * 表管理-初始化页面跳转
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/initTable")
	@ResponseBody
	public void initTable(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		List listTable = tableservice.findTableByList(null,"");
		resp.addGrid("gridpanel", listTable);
	}
	
	/**
	 * 表管理-修改页面跳转
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/editorTable")
	public void editorTable(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkDcDataSource = req.getParameter("pkDcDataSource");
		String pkDcTable = req.getParameter("pkDcTable");
		String busiObjectName = "" , primaryKeyName = "";
		DcDataSourceBO datasourcebo = datasource.findDcDataSourceBOByPK(pkDcDataSource);
		String pkDcBusiObject =  datasourcebo.getPkDcBusiObject();
		if(pkDcBusiObject != null){
			DcBusiObjectBO busibo = tableservice.findBusiById(pkDcBusiObject);
			busiObjectName = busibo.getBusiObjectName();
			
		}
		
		List<Map> ColumnList = colunmservice.findColumnByList(pkDcDataSource,pkDcTable);
		for(Map column : ColumnList){
			primaryKeyName += column.get("columnNameEn").toString() + ",";
		}
		if(primaryKeyName.length() > 0 ){
			primaryKeyName = primaryKeyName.substring(0,primaryKeyName.length()-1);
		}
		resp.addAttr("primaryKeyName", primaryKeyName);
		resp.addAttr("busiObjectName", busiObjectName);
		resp.addPage("/page/tableManager/table_editor.jsp");
	}

	/**
	 * 表管理-和修改
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateTable")
	public void updateTable(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		DcTableBO tablebo = (DcTableBO) req.getForm("formpanel",
				DcTableBO.class);
		System.out.println(tablebo.getDcTopic());
		//tablebo.setIsCheck(AppConstants.EFFECTIVE_Y);
		tableservice.updateTable(tablebo);
		//this.initListTable(req, resp);
	}
	/**
	 *认领表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/updateCheckTable")
	public void updateCheckTable(OptimusRequest req, OptimusResponse resp) throws OptimusException
			{
		String pkDcTable = req.getAttr("pkDcTable").toString();
		DcTableBO bo =new DcTableBO();
		bo.setIsCheck(AppConstants.EFFECTIVE_Y);
		bo.setPkDcTable(pkDcTable);
		boolean success=false;
		try{
			HttpSession session = WebContext.getHttpSession();
			User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
			bo.setModifierId(user.getUserId());
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());
			
			tableservice.updateTable(bo);
			success=true;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(success){
				resp.addAttr("back","success");
			}else{
				resp.addAttr("back","failure");
			}
			
		}
	}
	
	/**
	 *共享表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/updateShareTable")
	public void updateShareTable(OptimusRequest req, OptimusResponse resp) throws OptimusException
			{
		String pkDcTable = req.getAttr("pkDcTable").toString();
		DcTableBO bo =new DcTableBO();
		bo.setIsShare(AppConstants.EFFECTIVE_Y);
		bo.setPkDcTable(pkDcTable);
		boolean success=false;
		try{
			HttpSession session = WebContext.getHttpSession();
			User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
			bo.setModifierId(user.getUserId());
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());
			
			tableservice.updateTable(bo);
			success=true;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(success){
				resp.addAttr("back","success");
			}else{
				resp.addAttr("back","failure");
			}
			
		}
	}
	
	/**
	 * 取消共享表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteShareTable")
	public void deleteShareTable(OptimusRequest req, OptimusResponse resp) throws OptimusException
			{
		String pkDcTable = req.getAttr("pkDcTable").toString();
		DcTableBO bo =new DcTableBO();
		bo.setIsShare(AppConstants.EFFECTIVE_N);
		bo.setPkDcTable(pkDcTable);
		boolean success=false;
		try{
			HttpSession session = WebContext.getHttpSession();
			User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
			bo.setModifierId(user.getUserId());
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());
			
			tableservice.updateTable(bo);
			success=true;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(success){
				resp.addAttr("back","success");
			}else{
				resp.addAttr("back","failure");
			}
			
		}
	}
	
	/**
	 *可查询表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/updateQueryTable")
	public void updateQueryTable(OptimusRequest req, OptimusResponse resp) throws OptimusException
			{
		String pkDcTable = req.getAttr("pkDcTable").toString();
		DcTableBO bo =new DcTableBO();
		bo.setIsQuery(AppConstants.EFFECTIVE_Y);
		bo.setPkDcTable(pkDcTable);
		boolean success=false;
		try{
			HttpSession session = WebContext.getHttpSession();
			User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
			bo.setModifierId(user.getUserId());
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());
			
			tableservice.updateTable(bo);
			success=true;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(success){
				resp.addAttr("back","success");
			}else{
				resp.addAttr("back","failure");
			}
			
		}
	}
	
	/**
	 * 取消可查询表
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteQueryTable")
	public void deleteQueryTable(OptimusRequest req, OptimusResponse resp) throws OptimusException
			{
		String pkDcTable = req.getAttr("pkDcTable").toString();
		DcTableBO bo =new DcTableBO();
		bo.setIsQuery(AppConstants.EFFECTIVE_N);
		bo.setPkDcTable(pkDcTable);
		boolean success=false;
		try{
			HttpSession session = WebContext.getHttpSession();
			User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
			bo.setModifierId(user.getUserId());
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());
			
			tableservice.updateTable(bo);
			success=true;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(success){
				resp.addAttr("back","success");
			}else{
				resp.addAttr("back","failure");
			}
			
		}
	}
	
	/**
	 * 表管理-Id查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findTableById")
	@ResponseBody
	public void findTableById(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		String pkDcTable = req.getAttr("pkDcTable").toString();
		DcTableBO TableBo = tableservice.findTableById(pkDcTable);
		resp.addForm("formpanel", TableBo);
	}

	/**
	 * 表管理-列表查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findTableByList")
	@ResponseBody
	public void findTableByList(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		String pkDcBusiObject = req.getParameter("pkDcBusiObject");
		Map<String, String> formMap = req.getForm("formpanel");
		//formMap.put("pkDcBusiObject", pkDcBusiObject);
		List listTable = tableservice.findTableByList(formMap,pkDcBusiObject);
		resp.addGrid("gridpanel", listTable);
	}

	/**
	 * 字段管理-查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findColumnByList")
	@ResponseBody
	public void findColumnByList(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		String pkDcDataSource = req.getAttr("pkDcDataSource").toString();
		String pkDcTable = req.getAttr("pkDcTable").toString();
		Map<String, String> formMap = req.getForm("formpanel");
		List listTable = colunmservice.findColumnByList(formMap,
				pkDcDataSource, pkDcTable);
		resp.addGrid("gridpanel", listTable);
	}

	/**
	 * 字段管理-更新
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/updateColumn")
	public void updateColumn(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		DcColumnBO colunmbo = (DcColumnBO) req.getForm("formpanel",
				DcColumnBO.class);
		//colunmbo.setIsCheck(AppConstants.EFFECTIVE_Y);
		colunmservice.updateColumn(colunmbo);
	}
	
	/**
	 * 标准数据元		
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("/getStand")
	public void getCodeSet(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		//获取当前全部系统名称
		List systemList = colunmservice.doqueryStand();
		
		//封装数据并返回前台
		resp.addTree("standardCode", systemList);

	}
	/**
	 * 获取系统代码集
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("/getSysCodeSet")
	public void getSysCodeSet(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		DcDataSourceBO dataSourceBo =null;
		//获取数据源主键
		String pkDcDataSource = req.getParameter("pkDcDataSource");
		if(StringUtils.isNotEmpty(pkDcDataSource)&&(!"null".equals(pkDcDataSource))){
			dataSourceBo=colunmservice.doGetDcDataSource(pkDcDataSource);
		}
		if(dataSourceBo!=null){
			List systemList = colunmservice.doquerySysCodeSet(dataSourceBo.getPkDcBusiObject());
			
			//封装数据并返回前台
			resp.addTree("dcDmId", systemList);
		}
		
	}
	/**
	 * 获取代码集
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("/getCode")
	public void getCode(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		//获取当前全部系统名称
		List systemList = colunmservice.doqueryCode();
		
		//封装数据并返回前台
		resp.addTree("standardCode", systemList);

	}
	@RequestMapping("/getALLCodeOrStand")
	public void getALLCodeOrStand(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		//获取当前全部系统名称
		List systemList = colunmservice.getALLCodeOrStand();
		
		//封装数据并返回前台
		resp.addTree("standardCode", systemList);

	}
	/**
	 * 认领字段
	 * @param req
	 * @param resp
	 * @throws OptimusException 
	 * @throws Exception
	 */
	@RequestMapping("/updateCheckColumn")
	public void updateCheck(OptimusRequest req, OptimusResponse resp) throws OptimusException
			{
		String pkDcColumn = req.getAttr("pkDcColumn").toString();
		DcColumnBO colunmbo =new DcColumnBO();
		colunmbo.setIsCheck(AppConstants.EFFECTIVE_Y);
		colunmbo.setPkDcColumn(pkDcColumn);
		boolean success=false;
		try{
			HttpSession session = WebContext.getHttpSession();
			User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
			colunmbo.setModifierId(user.getUserId());
			colunmbo.setModifierName(user.getUserName());
			colunmbo.setModifierTime(Calendar.getInstance());
			
			colunmservice.updateColumn(colunmbo);
			success=true;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(success){
				resp.addAttr("back","success");
			}else{
				resp.addAttr("back","failure");
			}
			
		}
		
	}

	/**
	 * 字段管理-查询
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/findColumnById")
	@ResponseBody
	public void findColumnById(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkDcColumn = req.getAttr("pkDcColumn").toString();
		DcColumnBO colunmbo = colunmservice.findColumnById(pkDcColumn);
		resp.addForm("formpanel", colunmbo);
	}
}
