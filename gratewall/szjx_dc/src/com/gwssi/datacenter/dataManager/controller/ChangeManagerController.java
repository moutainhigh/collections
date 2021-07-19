/**
 * 
 */
package com.gwssi.datacenter.dataManager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.datacenter.dataManager.service.ChangeService;
import com.gwssi.datacenter.dataManager.service.TableManagerService;
import com.gwssi.datacenter.dataSource.service.DataSourceManagerService;
import com.gwssi.datacenter.model.DcBusiObjectBO;
import com.gwssi.datacenter.model.DcChangeBO;
import com.gwssi.datacenter.model.DcDataSourceBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

/**
 * @author xiaohan
 *
 */

@Controller
@RequestMapping("/changeManager")
public class ChangeManagerController {

	@Autowired
	DataSourceManagerService datasource;
	
	@Autowired
	TableManagerService tableservice;
	
	@Autowired
	ChangeService changeservice;

	/**
	 * 表变更管理-列表始化页面跳转
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/initListChange")
	public void initlistChange(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		resp.addPage("/page/tableManager/change_list.jsp");
	}

	/**
	 * 表变更管理-Id查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findChangeById")
	@ResponseBody
	public void findChangeById(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		String pkDcChange = req.getAttr("pkDcChange").toString();
		DcChangeBO ChangeBo = changeservice.findChangeById(pkDcChange);
		resp.addForm("formpanel", ChangeBo);
	}

	/**
	 * 表变更管理-列表查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findChangeByList")
	@ResponseBody
	public void findChangeByList(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		Map<String, String> formMap = req.getForm("formpanel");
		List listChange = changeservice.findChangeByList(formMap);
		resp.addGrid("gridpanel", listChange);
	}
	
	/**
	 * 表管理-修改页面跳转
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/viewChange")
	public void viewChange(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkDcChange = req.getParameter("pkDcChange");
		String pkDcDataSource = req.getParameter("pkDcDataSource");
		String busiObjectName = "" , dataSourceName = "";
		DcDataSourceBO datasourcebo = datasource.findDcDataSourceBOByPK(pkDcDataSource);
		dataSourceName =  datasourcebo.getDataSourceName();
		String pkDcBusiObject =  datasourcebo.getPkDcBusiObject();
		if(pkDcBusiObject != null){
			DcBusiObjectBO busibo = tableservice.findBusiById(pkDcBusiObject);
			busiObjectName = busibo.getBusiObjectName();
			
		}
		
		resp.addAttr("dataSourceName", dataSourceName);
		resp.addAttr("busiObjectName", busiObjectName);
		resp.addAttr("pkDcChange", pkDcChange);
		resp.addPage("/page/tableManager/change_view.jsp");
	}
	
	/**
	 * 查询所有变更类型
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getChangeType")
	@ResponseBody
	public void getChangeType(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		List listChangeType = changeservice.getChangeType();
		resp.addTree("listChangeType", listChangeType);
	}

}
