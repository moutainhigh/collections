package com.gwssi.datacenter.dataSource.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.dataSource.service.DataSourceManagerService;
import com.gwssi.datacenter.model.DcDataSourceBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.util.UuidGenerator;




/**
* @ClassName: DataSourceManagerController
* @Description:  数据源管理 包括数据源的查询 增加 删除 修改（数据库存储的数据）
* @author chaihaowei
*/
@Controller
@RequestMapping("/dataSource")
public class DataSourceManagerController {
    
    @Autowired
    private DataSourceManagerService dataSourceManagerService;

    
    
	/**
	 * 数据源管理界面 跳转
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getDataSource_ManagerHtml")
	public void getDataSource_manageHtml(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		resp.addPage("/page/datasource/datasource_manage.jsp");
	}
	
	
	/**
	 * 获取系统表的		
	 * @throws OptimusException 
	 */
	@RequestMapping("/getSmSysIntegrationBOKeyValue")
	public void getSmSysIntegrationBOKeyValue(OptimusRequest req, OptimusResponse resp) throws OptimusException{
	 	List<Map<String, Object>> funcList =null;
        funcList  = dataSourceManagerService.querySmSysIntegrationBOKeyValue();
        resp.addTree("pkDcBusiObject", funcList); 
		
	}
	
	/**
	 * 数据源增改查 页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getDataSource_addHtml")
	public void getDataSource_addHtml(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		String type= req.getParameter("type");
		String pkDcDataSource=req.getParameter("pkDcDataSource");
		if(type==null||type.equals("")||type.equals("")){
			type="";
		}
		if(StringUtils.isBlank(pkDcDataSource)){
			pkDcDataSource="";
		}
		resp.addAttr("type",type);
		resp.addAttr("pkDcDataSource",pkDcDataSource);
		resp.addPage("/page/datasource/datasource_crud.jsp");  
	}		

    /**
     * 数据源管理 获取所有的数据源
     * @param req
     * @param res
     * @throws OptimusException
     */
	@RequestMapping("getDataSourceManagerMenu")
	public void getDataSourceManagerMenu(OptimusRequest req, OptimusResponse res) throws OptimusException{
		List zhuxitong =null;
	    zhuxitong = dataSourceManagerService.findDataSourceMenu(); //更具主系统id查询系统所有东西
		res.addGrid("gridpanel", zhuxitong);  
	}  
    /**
     * 系统源管理查询符合条件的数据源
     * @param req
     * @param res
     * @throws OptimusException
     */
    @RequestMapping("getSomeDataSourceManagerMenu")
	public void getSomeDataSourceManagerMenu(OptimusRequest req, OptimusResponse res) throws OptimusException{
    	DcDataSourceBO datasource = req.getForm("formpanel", DcDataSourceBO.class);
    	List zhuxitong =null;
    	zhuxitong = dataSourceManagerService.findDataSourceMenu(datasource); 
		
		 res.addGrid("gridpanel", zhuxitong);    
    	
	}  	
    /**
     * 获取业务对象的主键 与 对应的名称
     * @param req
     * @param resp
     * @throws OptimusException
     */
	@RequestMapping("/findKeyValueDcBusiObjectBO")
	public void findKeyValueDcBusiObjectBO(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		 	List<Map<String, Object>> funcList =null;
	        funcList  = dataSourceManagerService.findKeyValueDcBusiObjectBO();
	        resp.addTree("pkDcBusiObject", funcList); 
	}
	/**
	 * 数据源测试  后台生成UUID作为key
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("/DataSourceTest")
	public void DataSourceTest(OptimusRequest req, OptimusResponse resp) 
	        throws Exception {
		
		//测试是能正常连接 
		//	1 true 正常连接  
		// 	2   false 连接失败）
		Boolean dataSourceConnect =false;
		
		DcDataSourceBO datasource = req.getForm("formpanel", DcDataSourceBO.class);
		
		//数据源类型
		//String dataSourceType =datasource.getDataSourceType();
		String dbType = datasource.getDbType();
		if(dbType.equals("oracle")){
			dataSourceConnect= dataSourceManagerService.dodataSourceConnectDbNotExists(datasource);	
		}else{
			//错误----
		}
		if(dataSourceConnect){
			resp.addAttr("back","success");
		}else{
			resp.addAttr("back","failure");
		}
	}
	/**
	 * 通过主键进行数据源测试 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("/DataSourceTestbyPK")
	public void DataSourceTestbyPK(OptimusRequest req, OptimusResponse resp) 
	        throws Exception {
		
		//测试是能正常连接 
		//	1 true 正常连接  
		// 	2   false 连接失败）
		Boolean dataSourceConnect =false;
		
		String pkID = req.getAttr("pkId").toString();
		
		DcDataSourceBO datasource = dataSourceManagerService.findDcDataSourceBOByPK(pkID);
		
		//数据源类型
		String dataSourceType =datasource.getDataSourceType();
		
		if(dataSourceType.equals("DB")){
			dataSourceConnect= dataSourceManagerService.dodataSourceConnectDbExists(datasource);
			//dataSourceConnect= dataSourceManagerService.dataSourceConnect(datasource,datasource.getPkDcDataSource());
		}else{
			//错误----
		}
		if(dataSourceConnect){
			resp.addAttr("back","success");
		}else{
			resp.addAttr("back","failure");
		}
	}		
	
	/**
	 * 新增数据源
	 */
	@RequestMapping("saveDcDataSourceBO")
	public void saveDcDataSourceBO(OptimusRequest req, OptimusResponse resp) 
			throws OptimusException {
		String back ="success";
		
		//获取静态数据
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);		
		
		Map<String,String> map = req.getForm("formpanel"); 	
		
		DcDataSourceBO datasource = req.getForm("formpanel", DcDataSourceBO.class);
		datasource.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
		datasource.setCreaterId(user.getUserId());
		datasource.setCreaterName(user.getUserName());
		datasource.setCreaterTime(Calendar.getInstance());
		
		//设置主键
		datasource.setPkDcDataSource(UuidGenerator.getUUID());
		dataSourceManagerService.insertDcDataSourceBO(datasource);
		
		resp.addAttr("back", back);
	}	      
	/**
	 * 编辑查看数据源
	 */
	@RequestMapping("/getDcDataSourceBO")
	public void setSMFunction(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		String type = req.getParameter("type");	
		DcDataSourceBO smser = new DcDataSourceBO();

		 Map form = new HashMap();
	
		if(type.equals("add")){

			
		}else if(type.equals("update")){
			String pkDcDataSource = req.getParameter("pkDcDataSource");	
			smser=dataSourceManagerService.findDcDataSourceBOByPK(pkDcDataSource);
			

		}else if(type.equals("view")){
			String pkDcDataSource = req.getParameter("pkDcDataSource");	
			smser=dataSourceManagerService.findDcDataSourceBOByPK(pkDcDataSource);
			
		}


		
		if(type.equals("add")){
	
		}else if(type.equals("update")||type.equals("view")){
			resp.addForm("formpanel", smser);
		}
	}
	/**
	 * 修改数据源
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("updateDcDataSourceBO")
	public void updateDcDataSourceBO(OptimusRequest req, OptimusResponse resp) 
			throws OptimusException {
		String back ="success";
		DcDataSourceBO datasource = req.getForm("formpanel", DcDataSourceBO.class);
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
		
		Map<String,String> map = req.getForm("formpanel"); 	
		
		datasource.setModifierId(user.getUserId());//修改人ID
		datasource.setModifierName(user.getUserName());
		datasource.setModifierTime(Calendar.getInstance());	//修改时间

		dataSourceManagerService.updateDcDataSourceBO(datasource);//更新

		resp.addAttr("back", back);
	}	
	/**
	 * 删除数据源
	 * @param req
	 * @param resp
	 * @throws Exception 
	 */
	@RequestMapping("/deleteDataSource")
	public void deleteDataSource(OptimusRequest req, OptimusResponse resp)
			throws Exception {
		String pkID = req.getAttr("pkId").toString();
		
		DcDataSourceBO datasource = dataSourceManagerService.findDcDataSourceBOByPK(pkID);
		datasource.setEffectiveMarker(AppConstants.EFFECTIVE_N);
		
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
		
		datasource.setModifierId(user.getUserId());//修改人ID
		datasource.setModifierName(user.getUserName());
		datasource.setModifierTime(Calendar.getInstance());	//修改时间
		String back=null;
	
		//删除动态数据源
		dataSourceManagerService.deleteDynamicDataSourceBO(datasource);
			

		back="success";

		resp.addAttr("back", back);
	}		
	
    /**
     * 获取数据源的key -value
     * @param req
     * @param resp
     * @throws OptimusException
     */
	@RequestMapping("/findKeyValueDcDmDstypeBO")
	public void findKeyValueDcDmDstypeBO(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		 	List<Map<String, Object>> funcList =null;
	        funcList  = dataSourceManagerService.findKeyValueDcDmDstypeBO();
	        resp.addTree("dataSourceType", funcList); 
	}
	
	
    /**
     * 获取数据源的key -value 带默认值
     * @param req
     * @param resp
     * @throws OptimusException
     */
	@RequestMapping("/findKeyValueDcDmDstypeBODefault")
	public void findKeyValueDcDmDstypeBODefault(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		 	List<Map<String, Object>> funcList =null;
	        funcList  = dataSourceManagerService.findKeyValueDcDmDstypeBO();
	
	        //默认选择数据库 
			for (Map<String, Object> m : funcList) {

				if (m.get("value").equals("DB")) {
					m.put("checked",true );
				}
			}
			
			String back="success";

			//resp.addAttr("back", back);	
		 	//resp.addAttr("obj", funcList);
	        resp.addTree("dataSourceType", funcList); 
	}	
    /**
     * 获取数据库的key -value
     * @param req
     * @param resp
     * @throws OptimusException
     */
	@RequestMapping("/findKeyValueDcDmDbtypeBO")
	public void findKeyValueDcDmDbtypeBO(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		 	List<Map<String, Object>> funcList =null;
	        funcList  = dataSourceManagerService.findKeyValueDcDmDbtypeBO();
	        resp.addTree("dataSourceType", funcList); 
	}	
}