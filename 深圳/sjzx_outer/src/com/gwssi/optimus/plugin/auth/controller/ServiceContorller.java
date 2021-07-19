package com.gwssi.optimus.plugin.auth.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.gwssi.application.model.SmGrantAuthBO;
import com.gwssi.application.model.SmServicesBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.FuncService;
import com.gwssi.optimus.plugin.auth.service.ServiceService;
import com.gwssi.optimus.util.TreeUtil;


@Controller
@RequestMapping("/auth")
public class ServiceContorller {
	
    @Autowired
    private ServiceService serviceService;
    
    @Autowired
    private FuncService funcService;
    
    @Autowired
	private OptimusAuthManager optimusAuthManager;
    /**
     * 权限管理--菜单管理（查询功能）
     * 查询符合条件的系统菜单
     * @param req
     * @param res
     * @throws OptimusException
     */
    @RequestMapping("getSomeServiceMenu")
	public void getSomeMenu(OptimusRequest req, OptimusResponse res) throws OptimusException{
    	SmServicesBO sms = req.getForm("formpanel", SmServicesBO.class);

		boolean isSuperAdmin = false; // 是否是超级管理员
		boolean isAdmin = false; // 是否是本系统管理员
		
		//获取当前用户角色类型
		Map<String,String> map1 =funcService.isadmin();

		
		if(map1.get("isSuperAdmin").equals("Y")){
			isSuperAdmin=true;
		}
		if(map1.get("isAdmin").equals("Y")){
			isAdmin=true;
		}
		
		List zhuxitong =null;
		if(isSuperAdmin){//超级管理员
			zhuxitong = serviceService.findMenu(sms); 
		
		}
		if(isAdmin){
			
			//1.获取所具有的子系统管理员的系统主键
			List <String> pksys = funcService.getAdminList();
			
			//2.只显示拥有子系统的
	        zhuxitong = serviceService.findMenu(pksys,sms); //更具主系统id查询系统所有东西
		}   	
		  res.addGrid("gridpanel", zhuxitong);    
    	
	}     
    /**
     * 获取所有的服务管理菜单
     * @param req
     * @param res
     * @throws OptimusException
     */
	@RequestMapping("getServiceMenu")
	public void getMenu(OptimusRequest req, OptimusResponse res) throws OptimusException{
		boolean isSuperAdmin = false; // 是否是超级管理员
		boolean isAdmin = false; // 是否是本系统管理员
		
		//获取当前用户角色类型
		Map<String,String> map1 =funcService.isadmin();

		
		if(map1.get("isSuperAdmin").equals("Y")){
			isSuperAdmin=true;
		}
		if(map1.get("isAdmin").equals("Y")){
			isAdmin=true;
		}

		List zhuxitong =null;
		if(isSuperAdmin){
			 //显示所有的
			zhuxitong = serviceService.findMenu(null);
		
		}
		if(isAdmin){
			
			//1.获取所具有的子系统管理员的系统主键
			List <String> pksys = funcService.getAdminList();
			
			//2.只显示拥有子系统的
	        zhuxitong = serviceService.findMenu(pksys,null); //更具主系统id查询系统所有东西
				
		}
		res.addGrid("gridpanel", zhuxitong);  

	}    
	/**
	 * 页面跳转 跳转到service_manage.jsp
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getService_manageHtml")
	public void getService_manageHtml(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		resp.addPage("/page/auth/service_manage.jsp");  
	}	    
	/**
	 * 页面跳转 跳转到service_add.jsp
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getService_addHtml")
	public void getService_addHtml(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		String type= req.getParameter("type");
		String pkSmServices=req.getParameter("pkSmServices");
		if(type==null||type.equals("")||type.equals("")){
			type="";
		}
		if(StringUtils.isBlank(pkSmServices)){
			pkSmServices="";
		}
		resp.addAttr("type",type);
		resp.addAttr("pkSmServices",pkSmServices);
		resp.addPage("/page/auth/service_crud.jsp");  
	}	
	/**
	 * 页面跳转 跳转到service_system.jsp
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getService_system_authHtml")
	public void getService_system_manageHtml(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		String pkSmServices=req.getParameter("pkSmServices");
		if(StringUtils.isBlank(pkSmServices)){
			pkSmServices="";
		}

		resp.addAttr("pkSmServices",pkSmServices);
		resp.addPage("/page/auth/service_system_auth.jsp");  
	}	
	/**
	 * 服务管理--新增 （获取服务类型）
	 * 获取服务类型
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/findServiceType")
	public void findServiceType(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		
		Map systemN=null;
		systemN=new HashMap();
		List system = new ArrayList();

		systemN=new HashMap();
		systemN.put("text", "同步");
		systemN.put("value", ""+AppConstants.SERVICES_TYPE_SYNC);
		system.add(systemN);
	
		systemN=new HashMap();
		systemN.put("text", "异步");
		systemN.put("value",""+AppConstants.SERVICES_TYPE_ASYNC);
		system.add(systemN);
	    resp.addTree("serviceType", system);
	}	
	/**
	 * 服务管理--新增（获取协议类型）
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/findAgreementType")
	public void findAgreementType(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		 List<Map<String, Object>> funcList =null;
	        funcList  = serviceService.findAgreementTypeTree();
	        resp.addTree("agreementType", funcList); 
	        
	}	
	/**
	 * 服务管理--新增（获取厂商主键）
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/findServicePkSmFirme")
	public void findServicePkSmFirme(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		 List<Map<String, Object>> funcList =null;
	        funcList  = serviceService.findServicePkSmFirme();
	        resp.addTree("pkSmFirm", funcList); 
	        
	}	
	/**
	 * 服务管理--新增（联系人主键）
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/findServicePkSmLikeman")
	public void findServicePkSmLikeman(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		 List<Map<String, Object>> funcList =null;
	        funcList  = serviceService.findServicePkSmLikeman();
	        resp.addTree("pkSmLikeman", funcList); 
	        
	}	
	
	/**
	 * 切换联系人
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */

	@RequestMapping("/findServicePkSmLikemanByPksys")
	public void findServicePkSmLikemanByPksys(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		String pkSmFirm= req.getParameter("pkSmFirm");
		if(pkSmFirm==null||pkSmFirm.equals("")||pkSmFirm.equals("")){
			pkSmFirm="";
		}
		 List<Map<String, Object>> funcList =null;
	        funcList  = serviceService.findServicePkSmLikeman(pkSmFirm);
	        resp.addTree("pkSmLikeman", funcList); 
	}
	/**
	 * 根据服务管理的主键 查询厂商联系人
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/findPkSmLikemanBypkSmServices")
	public void findPkSmLikemanBypkSmServices(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {

		 	SmServicesBO service =new SmServicesBO();
			String pkSmServices = req.getParameter("pkSmServices");	
			service=serviceService.findSmServicesByPK(pkSmServices);
			String pkSmFirm= service.getPkSmFirm();
			if(pkSmFirm==null||pkSmFirm.equals("")||pkSmFirm.equals("")){
				pkSmFirm="";
			}
			 List<Map<String, Object>> funcList =null;
		        funcList  = serviceService.findServicePkSmLikeman(pkSmFirm);
		        resp.addTree("pkSmLikeman", funcList); 
	}	
	/**
	 *服务管理--新增（获取系统主键）
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/findServicePkSysIntegration")
	public void findServicePkSysIntegration(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		boolean isSuperAdmin = false; // 是否是超级管理员
		boolean isAdmin = false; // 是否是本系统管理员
		
		List<SmSysIntegrationBO> list=null;
		List<Map<String, Object>> funcList =null;
		
		//获取当前用户角色类型
		Map<String,String> map1 =funcService.isadmin();

		
		if(map1.get("isSuperAdmin").equals("Y")){
			isSuperAdmin=true;
		}
		if(map1.get("isAdmin").equals("Y")){
			isAdmin=true;
		}
		
		List zhuxitong =null;
		if(isSuperAdmin){//超级管理员
			//2 获取List<SmSysIntegrationBO>
			list=serviceService.findSuperAdminSmSysInte();			
		}
		if(isAdmin){
			
			//获取所具有的子系统管理员的系统主键
			List <String> pksys = funcService.getAdminList();
			//2 获取List<SmSysIntegrationBO>
			list=serviceService.findAdminSmSysInte(pksys);
		}   
		//3把List<SmSysIntegrationBO>转换为List<Map<String, Object>>
		funcList=serviceService.changSmSysIntegrationBOToTreeList(list);

		
	        resp.addTree("funcTree", funcList);
	}	
	

	/**
	 * 权限管理-服务管理--新增（设置新增具体内容）
	 */
	@RequestMapping("/setSmService")
	public void setSMFunction(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		String type = req.getParameter("type");	
		SmServicesBO smser = new SmServicesBO();
		//读取静态user
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);		
		
		
		 Map form = new HashMap();
		 SmServicesBO service =new SmServicesBO();
	
		if(type.equals("add")){
			service.setCreaterId(user.getUserId());
			service.setCreaterName(user.getUserName());
			service.setCreaterTime(Calendar.getInstance());
			
		}else if(type.equals("update")){
			String pkSmServices = req.getParameter("pkSmServices");	
			smser=serviceService.findSmServicesByPK(pkSmServices);
			
			Calendar calendar1 = Calendar.getInstance();
			//设置修改人ID  name time
	        smser.setModifierId(user.getUserId());
	        smser.setModifierName(user.getUserName());
	        smser.setModifierTime(calendar1);
		}else if(type.equals("view")){
			String pkSmServices = req.getParameter("pkSmServices");	
			smser=serviceService.findSmServicesByPK(pkSmServices);
			
		}

		
		if(type.equals("add")){
			resp.addForm("formpanel", service);
		}else if(type.equals("update")||type.equals("view")){
			resp.addForm("formpanel", smser);
		}
	}
	
	/**
	 * 权限管理--服务管理（新增服务）
	 */
	@RequestMapping("saveSmServices")
	public void saveSmServices(OptimusRequest req, OptimusResponse resp) 
			throws OptimusException {
		String back ="failue";
		Map<String,String> map = req.getForm("formpanel"); 	
		
		SmServicesBO smfunction = req.getForm("formpanel", SmServicesBO.class);
		smfunction.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
		
		String str=map.get("createrTime");
		smfunction.setCreaterTime(funcService.changeStringToCalendar(str));	//更新时间  String 转换为Calendar
		try{
			serviceService.insertSmServicesBO(smfunction);
			back="success";
		}catch(Exception e){
			back="failue";
		}
		
		resp.addAttr("back", back);
	}	
	/**
	 *权限管理--菜单管理--菜单维护--具体细节（修改功能）
	 * 修改功能
	 * 菜单管理中的修改菜单
	 */
	@RequestMapping("updateSmServices")
	public void updateSmServices(OptimusRequest req, OptimusResponse resp) 
			throws OptimusException {
		String back ="failue";
		SmServicesBO smfunction = req.getForm("formpanel", SmServicesBO.class);
		Map<String,String> map = req.getForm("formpanel"); 	
/*		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
		smfunction.setModifierId(user.getUserId());//修改人ID
		smfunction.setModifierName(user.getUserName());
		smfunction.setModifierTime(Calendar.getInstance());	//修改时间
*/		
		String str1=map.get("modifierTime");
		smfunction.setModifierTime(funcService.changeStringToCalendar(str1));
	
		String str=map.get("createrTime");//String 转换为Calendar
		smfunction.setCreaterTime(funcService.changeStringToCalendar(str));	//
		try{
			serviceService.updateSmServices(smfunction);//更新
			back="success";
		}catch(Exception e){
			back="failue";
		}
		resp.addAttr("back", back);
	}	
	/**
	 * 服务管理（服务删除）
	 * @param req
	 * @param resp
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/deleteSmServices")
	public void deleteSmServices(OptimusRequest req, OptimusResponse resp)
			throws OptimusException {
		String pkID = req.getAttr("pkId").toString();
		
		SmServicesBO smfunction = serviceService.findSmServicesByPK(pkID);
		smfunction.setEffectiveMarker(AppConstants.EFFECTIVE_N);
		
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
		
		smfunction.setModifierId(user.getUserId());//修改人ID
		smfunction.setModifierName(user.getUserName());
		smfunction.setModifierTime(Calendar.getInstance());	//修改时间
		String back=null;
		try{
	        //删除某个系统所拥有的服务
	        serviceService.deleteSmGrantAuthBO(smfunction.getPkSmServices());
	        
			serviceService.updateSmServices(smfunction);//更新
			back="success";
		}catch(Exception e){
			back="failue";
		}
		resp.addAttr("back", back);
	}	
	/**
	 * 服务管理 --权限分配（获取系统树）
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
    @RequestMapping("/queryAuthServiceTree")
    public void queryAuthServiceTree(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
       	String pkSmServices = req.getParameter("pkSmServices");

        List authPosiList = serviceService.findAuthService(pkSmServices);//已经选择的岗位
       
    	List listPost = serviceService.findAllSystem();
        TreeUtil.makeCheckedTree(listPost, authPosiList, "id");
        Map rootNode = new HashMap();
        rootNode.put("name", "深圳市市场监督管理委");
        rootNode.put("id", "0");
        rootNode.put("open", true);
		listPost.add(rootNode);
		res.addTree("listPost", listPost);
    }	
    
    /**
     * 服务管理--分配服务
     * @param req
     * @param res
     * @throws OptimusException
     */
    @RequestMapping("/saveServiceAuth")
    public void saveServiceAuth(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
    	
    	//获取服务所分配的系统
        String funcIdsStr = (String)req.getAttr("funcIdsStr");
        List<String> pksys = new ArrayList<String>();//系统代码
        if(StringUtils.isEmpty(funcIdsStr)){
        }else{
        	pksys = Arrays.asList(funcIdsStr.split(","));
        }
        
        String pkSmServices=(String)req.getAttr("pkSmServices");

        //获取当前系统所拥有服务
        //List  <SmGrantAuthBO>  smgrant =serviceService.findSmgrantAuthByPkSmSer(pkSmServices);
        
        //删除某个系统所拥有的服务
        serviceService.deleteSmGrantAuthBO(pkSmServices);
        
        //保存某个系统所拥有的服务
        SmGrantAuthBO smg=null;
        for(String s1 :pksys){
        	smg= new SmGrantAuthBO();
        	smg.setPkSmServices(pkSmServices);
        	smg.setPkSysIntegration(s1);
        	serviceService.insertSmGrantAuthBO(smg);
        }
        


    }   
    
}