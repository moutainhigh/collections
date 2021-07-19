/**
 * 
 */
package com.gwssi.datacenter.dataManager.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.dataManager.service.ColumnManagerService;
import com.gwssi.datacenter.dataManager.service.RelationService;
import com.gwssi.datacenter.dataManager.service.TableManagerService;
import com.gwssi.datacenter.dataSource.service.DataSourceManagerService;
import com.gwssi.datacenter.model.DcBusiObjectBO;
import com.gwssi.datacenter.model.DcColumnBO;
import com.gwssi.datacenter.model.DcDataSourceBO;
import com.gwssi.datacenter.model.DcRelationBO;
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
@RequestMapping("/relationManager")
public class RelationManagerController {
	
	@Autowired
	RelationService relationservice;

	@Autowired
	DataSourceManagerService datasource;

	@Autowired
	TableManagerService tableservice;

	@Autowired
	ColumnManagerService columnservice;
	
	/**
	 * 表关系管理-列表始化页面跳转
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/initListRelation")
	public void initListRelation(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		resp.addPage("/page/tableManager/relation_list.jsp");
	}
	
	/**
	 * 表关系管理-初始化表
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
		Map<String, String> formMap = req.getForm("formpanel");
		List listTable = relationservice.findTableByList(formMap);
		resp.addGrid("gridpanel", listTable);
	}
	
	/**
	 * 表关系管理-添加页面跳转
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/editorRelation")
	public void editorRelation(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkDcDataSource = req.getParameter("pkDcDataSource");
		String pkDcTable = req.getParameter("pkDcTable");
		String dataSourceName = "" , busiObjectName = "" , tableNameEn = "" , dcTopic = "";
		DcDataSourceBO datasourcebo = datasource.findDcDataSourceBOByPK(pkDcDataSource);
		dataSourceName = datasourcebo.getDataSourceName();
		String pkDcBusiObject =  datasourcebo.getPkDcBusiObject();
		if(pkDcBusiObject != null){
			DcBusiObjectBO busibo = tableservice.findBusiById(pkDcBusiObject);
			busiObjectName = busibo.getBusiObjectName();
		}
		DcTableBO dctablebo = tableservice.findTableById(pkDcTable);
		tableNameEn = dctablebo.getTableNameEn();
		dcTopic = dctablebo.getDcTopic();
		resp.addAttr("dataSourceName", dataSourceName);
		resp.addAttr("busiObjectName", busiObjectName);
		resp.addAttr("tableNameEn", tableNameEn);
		resp.addAttr("pkDcTable", pkDcTable);
		resp.addAttr("pkDcDataSource", pkDcDataSource);
		resp.addAttr("dcTopic", dcTopic);
		resp.addPage("/page/tableManager/table_relation_editor.jsp");
	}
	
	/**
	 * 表关系管理-添加
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/saveRelation")
	public void saveRelation(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		DcRelationBO relationbo = new DcRelationBO();
		Map<String,String> form = req.getForm("formpanel");
		String pkDcDataSource =  form.get("pkDcDataSource");
		relationbo.setPkDcDataSource(pkDcDataSource);
		String pkDcTable =  form.get("pkDcTable");
		relationbo.setPkDcTable(pkDcTable);
		DcTableBO dctablebo = tableservice.findTableById(pkDcTable);
		String tableNameEn = dctablebo.getTableNameEn();
		relationbo.setTableNameEn(tableNameEn);
		String tableNameCn = dctablebo.getTableNameCn();
		relationbo.setTableNameCn(tableNameCn);
		String pkDcColumn =  form.get("pkDcColumn");
		DcColumnBO dccolumn = columnservice.findColumnById(pkDcColumn);
		String columnNameEn = dccolumn.getColumnNameEn();
		relationbo.setColumnNameEn(columnNameEn);
		String columnNameCn = dccolumn.getColumnNameCn();
		relationbo.setColumnNameCn(columnNameCn);
		
		String pkDcDataSourceRelation =  form.get("pkDcDataSourceRelation");
		relationbo.setPkDcDataSourceRelation(pkDcDataSourceRelation);
		String pkDcTableRelation =  form.get("pkDcTableRelation");
		relationbo.setPkDcTableRelation(pkDcTableRelation);
		DcTableBO dctablerelationbo = tableservice.findTableById(pkDcTableRelation);
		String tableNameEnRelation = dctablerelationbo.getTableNameEn();
		relationbo.setTableNameEnRelation(tableNameEnRelation);
		String tableNameCnRelation = dctablerelationbo.getTableNameCn();
		relationbo.setTableNameCnRelation(tableNameCnRelation);
		String pkDcColumnRelation =  form.get("pkDcColumnRelation");
		DcColumnBO dccolumnrelation = columnservice.findColumnById(pkDcColumnRelation);
		String columnNameEnRelation = dccolumnrelation.getColumnNameEn();
		relationbo.setColumnNameEnRelation(columnNameEnRelation);
		String columnNameCnRelation = dccolumnrelation.getColumnNameCn();
		relationbo.setColumnNameCnRelation(columnNameCnRelation);
		relationbo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);	
		relationbo.setCreaterId(user.getUserId());
		relationbo.setCreaterName(user.getUserName());
		relationbo.setCreaterTime(Calendar.getInstance());
		relationservice.saveRelation(relationbo);
	}
	
	/**
	 * 表关系管理-删除
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteRelation")
	public void deleteRelation(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkDcRelation = req.getAttr("pkDcRelation").toString();
		DcRelationBO relationbo = relationservice.findRelationById(pkDcRelation);
		relationbo.setEffectiveMarker(AppConstants.EFFECTIVE_N);
		relationservice.updateRelation(relationbo);
	}

	/**
	 * 表关系管理-列表查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findRelationByList")
	@ResponseBody
	public void findRelationByList(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		String pkDcDataSource = req.getAttr("pkDcDataSource").toString();
		String pkDcTable = req.getAttr("pkDcTable").toString();
		List relationList = relationservice.findTableRelationByList(pkDcDataSource,pkDcTable);
		resp.addGrid("relationList", relationList);
	}
	
	/**
	 * 表关系管理-列表查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findRelationColumn")
	@ResponseBody
	public void findRelationColumn(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		//String pkDcDataSource = req.getParameter("pkDcDataSource");
		String pkDcTable = req.getParameter("pkDcTable");
		List listColumn = columnservice.findRelationColumnByList(pkDcTable);
		resp.addTree("listColumn", listColumn);
	}
	
	/**
	 * 数据源代码集
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findRelationDataSource")
	@ResponseBody
	public void findRelationDataSource(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		List listDataSource = datasource.findKeyValueDataSourceBO();
		resp.addTree("listDataSource", listDataSource);
	}
	
	/**
	 * 查询所属业务系统
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changeRelationBusi")
	@ResponseBody
	public void changeRelationBusi(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		String pkDcDataSource = req.getAttr("pkDcDataSource").toString();
		String busiObjectName = "" ;
		DcDataSourceBO datasourcebo = datasource.findDcDataSourceBOByPK(pkDcDataSource);
		String pkDcBusiObject =  datasourcebo.getPkDcBusiObject();
		if(pkDcBusiObject != null){
			DcBusiObjectBO busibo = tableservice.findBusiById(pkDcBusiObject);
			busiObjectName = busibo.getBusiObjectName();
		}
		resp.addAttr("busiObjectName", busiObjectName);
	}
	
	/**
	 * 查询数据源下的表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changeRelationTable")
	@ResponseBody
	public void changeRelationTable(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		String pkDcDataSource = req.getAttr("pkDcDataSource").toString();
		List listTable = tableservice.findKeyValueTableBO(pkDcDataSource);
		resp.addTree("listTable", listTable);
	}
	
	/**
	 * 查询数据源下的表的所有字段
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changeRelationColumn")
	@ResponseBody
	public void changeRelationColumn(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		//String pkDcDataSource = req.getAttr("pkDcDataSource").toString();
		String pkDcTable = req.getAttr("pkDcTable").toString();
		List listColumn = columnservice.findRelationColumnByList(pkDcTable);
		resp.addTree("listColumn", listColumn);
	}

}
