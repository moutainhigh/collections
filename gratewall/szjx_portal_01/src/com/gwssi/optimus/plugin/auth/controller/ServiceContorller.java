package com.gwssi.optimus.plugin.auth.controller;

import java.io.File;
import java.net.URLEncoder;
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
import com.gwssi.application.common.FileUtil;
import com.gwssi.application.log.annotation.LogBOAnnotation;
import com.gwssi.application.model.SmGrantAuthBO;
import com.gwssi.application.model.SmServicesBO;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.core.web.fileupload.OptimusFileItem;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.application.model.SmSysIntegrationBO;
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
    @LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="查询服务管理页面",operationCode=AppConstants.LOG_OPERATE_QUERY)
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="获取服务管理菜单",operationCode=AppConstants.LOG_OPERATE_QUERY)
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="页面跳转",operationCode=AppConstants.LOG_OPERATE_QUERY)
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="页面跳转",operationCode=AppConstants.LOG_OPERATE_QUERY)
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="页面跳转",operationCode=AppConstants.LOG_OPERATE_QUERY)
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="获取服务类型",operationCode=AppConstants.LOG_OPERATE_QUERY)
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="新增服务类型",operationCode=AppConstants.LOG_OPERATE_ADD)
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="获取厂商主键",operationCode=AppConstants.LOG_OPERATE_QUERY)
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="获取联系人主键",operationCode=AppConstants.LOG_OPERATE_QUERY)
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="切换联系人信息",operationCode=AppConstants.LOG_OPERATE_UPDATE)
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="查询联系人信息",operationCode=AppConstants.LOG_OPERATE_QUERY)
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="获取系统主键",operationCode=AppConstants.LOG_OPERATE_QUERY)
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="设置服务管理",operationCode=AppConstants.LOG_OPERATE_UPDATE)
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
	 * @throws Exception 
	 */
	@RequestMapping("saveSmServices")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="保存服务信息",operationCode=AppConstants.LOG_OPERATE_UPDATE)
	public void saveSmServices(OptimusRequest req, OptimusResponse resp) 
			throws Exception {
		String back ="failue";
		Map<String,String> map = req.getForm("formpanel"); 	
		SmServicesBO smfunction = req.getForm("formpanel", SmServicesBO.class);
		
		//获取文件
		Map fileInfo = new HashMap();
		List<OptimusFileItem> list = req.getUploadList("formpanel",
				"fileName");
		
		if (!list.isEmpty()) {
			String rootDir = ConfigManager.getProperty("rootDir");
			String uploadTempDir = ConfigManager.getProperty("upload.tempDir");
			//String uploadPath = req.getHttpRequest().getRealPath("/") ;
			
			//文件路径
			String uploadPath = rootDir + File.separator + uploadTempDir;
			String fileUrl = smfunction.getFileUrl();
			if(!"".equals(fileUrl)){
				FileUtil.deleteFile(uploadPath + fileUrl);
				
				//保存文件
				fileInfo = FileUtil.saveDocFile(list.get(0), uploadPath);
			}else{
				fileInfo = FileUtil.saveDocFile(list.get(0), uploadPath);
			}
			
			smfunction.setFileUrl(fileInfo.get("path").toString().replace("\\", "/"));
			
		}
		
		
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
	 * 下载文件
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("downloadDoc")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="下载文件",operationCode=AppConstants.LOG_OPERATE_QUERY)
	public void downloadDoc(OptimusRequest req,OptimusResponse resp)throws Exception{
		
		//获取参数
		String pkDcStandardSpec = req.getParameter("pkid");
		
		//通过主键查询文件url
		SmServicesBO bo=	serviceService.dofindSmServicesBOBykey(pkDcStandardSpec);
	
		String fileUrl = bo.getFileUrl();
		
		String rootDir = ConfigManager.getProperty("rootDir");
		String uploadTempDir = ConfigManager.getProperty("upload.tempDir");
		
		
		//文件路径
		String uploadPath = rootDir + File.separator + uploadTempDir;
		String path = uploadPath +fileUrl;
		fileUrl=path;
		//文件名
		/*String fileName = fileUrl.substring(fileUrl.lastIndexOf(File.separator)+1);*/
		String fName = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
		//String fileName = fName.substring(0, fName.lastIndexOf("+"))+fName.substring(fName.lastIndexOf("."));
		String fileName="C://temp//w.rar";
		
		//得到要下载的文件
		File f = new File(fileName); //临时文件
		
		resp.download(f, URLEncoder.encode(fileName, "UTF-8"), false);
		/*
		//读取要下载的文件，保存到文件输入流
		FileInputStream fin = new FileInputStream(f);
		resp.getHttpResponse().reset();
		resp.getHttpResponse().setContentType("application/x-download;charset=GBK");
		
		//设置响应头，控制浏览器下载该文件
		resp.getHttpResponse().setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
	
		//创建输出流	
		java.io.OutputStream os = resp.getHttpResponse().getOutputStream();
		
		//创建缓冲区
		byte[] b = new byte[1024];
		int len = 0;
		
		//循环将输入流中的内容读取到缓冲区当中
		while((len = fin.read(b))>0){
			
			//输出缓冲区的内容到浏览器，实现文件下载
			os.write( b, 0, len);
				
		}
		
		os.flush();
		
		//关闭文件输入流
		os.close();
		
		//关闭输出流
		fin.close();
			*/
	}
	/**
	 *权限管理--菜单管理--菜单维护--具体细节（修改功能）
	 * 修改功能
	 * 菜单管理中的修改菜单
	 * @throws Exception 
	 */
	@RequestMapping("updateSmServices")
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="更新服务信息",operationCode=AppConstants.LOG_OPERATE_UPDATE)
	public void updateSmServices(OptimusRequest req, OptimusResponse resp) 
			throws Exception {
		String back ="failue";
		SmServicesBO smfunction = req.getForm("formpanel", SmServicesBO.class);
		Map<String,String> map = req.getForm("formpanel"); 	
/*		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
		smfunction.setModifierId(user.getUserId());//修改人ID
		smfunction.setModifierName(user.getUserName());
		smfunction.setModifierTime(Calendar.getInstance());	//修改时间
*/		
		//获取文件
		Map fileInfo = new HashMap();
		List<OptimusFileItem> list = req.getUploadList("formpanel",
				"fileName");
		
		if (!list.isEmpty()) {
			String rootDir = ConfigManager.getProperty("rootDir");
			String uploadTempDir = ConfigManager.getProperty("upload.tempDir");
			//String uploadPath = req.getHttpRequest().getRealPath("/") ;
			
			//文件路径
			String uploadPath = rootDir + File.separator + uploadTempDir;
			String fileUrl = smfunction.getFileUrl();
			if(!"".equals(fileUrl)){
				FileUtil.deleteFile(uploadPath + fileUrl);
				
				//保存文件
				fileInfo = FileUtil.saveDocFile(list.get(0), uploadPath);
			}else{
				fileInfo = FileUtil.saveDocFile(list.get(0), uploadPath);
			}
			
			smfunction.setFileUrl(fileInfo.get("path").toString().replace("\\", "/"));
			
		}
		
		
		String str1=map.get("modifierTime");
		smfunction.setModifierTime(funcService.changeStringToCalendar(str1));
	
		String str=map.get("createrTime");//String 转换为Calendar
		smfunction.setCreaterTime(funcService.changeStringToCalendar(str));	//
		try{
			serviceService.updateSmServices(smfunction);//更新
			back="success";
		}catch(Exception e){
			e.printStackTrace();
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
	@LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="删除服务信息",operationCode=AppConstants.LOG_OPERATE_DELETE)
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
    @LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="获取系统信息",operationCode=AppConstants.LOG_OPERATE_QUERY)
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
    @LogBOAnnotation(systemCode="SM",functionCode="SM0202",operationType="保存分配功能",operationCode=AppConstants.LOG_OPERATE_SAVE)
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