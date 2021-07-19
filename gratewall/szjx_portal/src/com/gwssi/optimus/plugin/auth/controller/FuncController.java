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
import com.gwssi.application.model.SmFunctionBO;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.FuncService;


@Controller
@RequestMapping("/auth")
public class FuncController {
    
    @Autowired
    private FuncService funcService;
    @Autowired
	private OptimusAuthManager optimusAuthManager;
    
    @RequestMapping("/publishFunc")
    public void publishFunc(OptimusRequest req, OptimusResponse res) throws OptimusException{
    	if(OptimusAuthManager.SECURITY_USE_CACHE){
    		optimusAuthManager.init();
    		res.addAttr("back", "success");
    	}else{
    		res.addAttr("back", "fail");
    	}
    }
    /**
     * 权限管理--菜单管理（查询功能）
     * 查询符合条件的系统菜单
     * @param req
     * @param res
     * @throws OptimusException
     */
    @RequestMapping("getSomeMenu")
	public void getSomeMenu(OptimusRequest req, OptimusResponse res) throws OptimusException{
		SmSysIntegrationBO sms = req.getForm("formpanel", SmSysIntegrationBO.class);
		String systemName=sms.getSystemName();
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
			zhuxitong = funcService.findMenu(sms); 
		
		}
		
		
		if(isAdmin){
			//1.获取启用的角色所具有的子系统管理员的系统主键
			List <String> pksys = funcService.getAdminList();
	        zhuxitong = funcService.findMenu(pksys,sms); //更具主系统id查询系统所有东西
				
		}   	
		  res.addGrid("gridpanel", zhuxitong);  
    	
	}   
    /** 
     * 权限管理--菜单管理（获取系统）
     * 获取符合当前用户的管理菜单
     */
	@RequestMapping("getMenu")
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
			 zhuxitong = funcService.findMenu(null); 
		
		}
		if(isAdmin){
			//1.获取所具有的子系统管理员的系统主键
			List <String> pksys = funcService.getAdminList();
	        zhuxitong = funcService.findMenu(pksys,null); //更具主系统id查询系统所有东西
				
		}
		res.addGrid("gridpanel", zhuxitong);  

	}    
	
	/**
	 * 权限管理--菜单管理--菜单维护--具体细节（新增功能）
	 * 主系统下的新增功能
	 * 菜单管理中的新增菜单
	 */
	@RequestMapping("saveSmfunction")
	public void saveSmfunction(OptimusRequest req, OptimusResponse resp) 
			throws OptimusException {
		String back ="failue";
		
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
		
		//获取需要保存的BO
		Map<String,String> map = req.getForm("formpanel"); 	
		String superFuncCode =map.get("superFuncCode");
		SmFunctionBO smfunction = req.getForm("formpanel", SmFunctionBO.class);
		smfunction.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
		
		//设置BO
		if(smfunction.getFunctionUrl().length()==0){
			//为空或者是功能模块时   功能url保存为#
			smfunction.setFunctionUrl("");//
		}
/*		if(smfunction.getFunctionType().equals(AppConstants.FUNC_TYPE_1)){//为模块时候  做处理
			//是功能模块时   功能url保存为#
			smfunction.setFunctionUrl("");//
		}*/
		
		//判断上级功能代码 （system 为系统需要null）
		if(smfunction.getSuperFuncCode().length()==0||smfunction.getSuperFuncCode().equals("system")){
			//判断上级菜单是否为空菜单就是系统主键
			smfunction.setSuperFuncCode(null);
		}
		
		//转换时间类型
		String str=map.get("createrTime");
		smfunction.setCreaterTime(funcService.changeStringToCalendar(str));	//更新时间  String 转换为Calendar
		smfunction.setCreaterName(user.getUserName());
		smfunction.setCreaterId(user.getUserId());
		
/*		try{*/
			//保存BO
			funcService.insertSmfunction(smfunction);
			
			//判断上级菜单是否为模块 不是模块就改变为模块
			if(!StringUtils.isEmpty(smfunction.getSuperFuncCode())){
				funcService.updateFuncType(smfunction.getSuperFuncCode());//改变上级系统菜单
			}
			back="success";
/*		}catch(Exception e){
			back="failue";
		}*/
		
		resp.addAttr("back", back);
	}	
	/**
	 *权限管理--菜单管理--菜单维护--具体细节（修改功能）
	 * 修改功能
	 * 菜单管理中的修改菜单
	 */
	@RequestMapping("updateSmfunction")
	public void updateSmfunction(OptimusRequest req, OptimusResponse resp) 
			throws OptimusException {
		String back ="failue";
		
		//获取当前BO
		SmFunctionBO smfunction = req.getForm("formpanel", SmFunctionBO.class);
		Map<String,String> map = req.getForm("formpanel"); 	
		
		//获取静态user
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
		
		//转换时间类型
		String str1=map.get("modifierTime");
		smfunction.setModifierTime(funcService.changeStringToCalendar(str1));

		//判断上级菜单
		if(smfunction.getSuperFuncCode().length()==0||smfunction.getSuperFuncCode().equals("system")){//获取有无空的上级菜单
			smfunction.setSuperFuncCode(null);
		}

		//通过主键获取未更改前的功能
		SmFunctionBO Oldsmfunction =funcService.findFuncMenuByPK(smfunction.getFunctionCode());
		
		//获取当前菜单下的所有叶子节点 并改变叶子节点的层次码
		List <SmFunctionBO> leafsmfunction=funcService.findleaf(Oldsmfunction);//获取所有叶子
	
		String []old = Oldsmfunction.getLevelCode().split("\\.");
		int oldlen=old.length;
		if(leafsmfunction.size()>0){
			for(SmFunctionBO sm : leafsmfunction){
						String str="";
				 		String[]  nld= sm.getLevelCode().split("\\.");
				 		for(int i=0;i<(nld.length-oldlen);i++){
				 		 str=str+nld[i]+".";	
				 		}
				 		str=str+smfunction.getLevelCode();
				 		sm.setLevelCode(str);	
						sm.setModifierId(user.getUserId());
						sm.setModifierTime(Calendar.getInstance());	
						sm.setModifierName(user.getUserName());
				 		funcService.updateSmfunction(sm);//更新	
			}
		}
	
		//保存当前更改项目
		String str=map.get("createrTime");//String 转换为Calendar
		smfunction.setCreaterTime(funcService.changeStringToCalendar(str));	//
		funcService.updateSmfunction(smfunction);//更新
		if(!StringUtils.isEmpty(smfunction.getSuperFuncCode())){
			funcService.updateFuncType(smfunction.getSuperFuncCode());
		}
		
		back="success";
		resp.addAttr("back", back);
	}
	/**
	 *权限管理--菜单管理 --菜单维护（获取系统的名字）
	 *获取系统名字通过系统的主键
	 */
	@RequestMapping("getSystemName")
	public void getSystemName(OptimusRequest req, OptimusResponse res) throws OptimusException{
		String pkSysIntegration=null;
		pkSysIntegration = req.getParameter("pkSysIntegration");
		SmSysIntegrationBO sms =funcService.findSystem(pkSysIntegration);
		res.addAttr("back", "success");
		res.addAttr("systemName",sms.getSystemName());
		
	}
	/**
	 * 得到单个系统信息通过主键（pkSysIntegration）
	 */
	@RequestMapping("getSmSysIntegrationByPk")
	public void getSmSysIntegrationByPk(OptimusRequest req, OptimusResponse res) throws OptimusException{
		String pkSysIntegration=null;
		pkSysIntegration = req.getParameter("pkSysIntegration");
		SmSysIntegrationBO sms =funcService.findSystem(pkSysIntegration);

		  res.addForm("formpanel", sms);
	
		
	}
	/**
	 * 修改主系统（SmSysIntegration）
	 */
	@RequestMapping("UpdateSmSysIntegration")
	public void UpdateSmSysIntegration(OptimusRequest req, OptimusResponse res) throws OptimusException{  
			String back ="failue";
			SmSysIntegrationBO sms = req.getForm("formpanel", SmSysIntegrationBO.class);	
			funcService.UpdateSmSysIntegration(sms);
			back="success";
			res.addAttr("back", back);	
		
	}		
	/**
	 * 权限管理--菜单管理--菜单维护（查询功能）
	 * 获取某个系统的子功能
	 */
	@RequestMapping("getFuncMenu")
	public void getFuncMenu(OptimusRequest req, OptimusResponse res) throws OptimusException{
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		String funcName=null;//功能名称
		if(req.getParameter("funcName")!=null){
			funcName = req.getParameter("funcName");
		}
		List<String> sysid = new ArrayList<String>();				
		sysid.add(pkSysIntegration);		
		List list=funcService.findFuncMenu(sysid,funcName);
		res.addGrid("gridpanel", list); 
		   
	}

	/**
	 *权限管理--菜单管理--菜单维护（查询功能（查询某些功能））
	 * 查询功能菜单
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	@RequestMapping("getSomeFuncMenu")
	public void getSomeFuncMenu(OptimusRequest req, OptimusResponse res) throws OptimusException{
		SmFunctionBO sms = req.getForm("formpanel", SmFunctionBO.class);
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		String funcName=sms.getFunctionName();
		List<String> sysid = new ArrayList<String>();				
		sysid.add(pkSysIntegration);				
		List list=funcService.findFuncMenu(sysid,funcName);
		res.addGrid("gridpanel", list); 
		   
	} 	
	/**
	 * 权限管理--菜单管理--菜单维护（获取系统的所有功能名称）
	 * 获取某个系统的子功能的所有名字
	 */
	@RequestMapping("queryFuncName")
	public void queryFuncName(OptimusRequest req, OptimusResponse res) throws OptimusException{
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		String funcName=null;
		if(req.getParameter("funcName")!=null){
			funcName = req.getParameter("funcName");
		}

		List<String> sysid = new ArrayList<String>();				
		sysid.add(pkSysIntegration);		
		List list1 =funcService.findFuncName(sysid);
		res.addAttr("functionName", list1);
	} 
	
	/**
	 * 权限管理--菜单管理--菜单维护--具体维护（获取一个功能）
	 * 通过主键获取一个功能
	 */
	@RequestMapping("getFuncMenuByPK")
	public void getFuncMenuByPK(OptimusRequest req, OptimusResponse res) throws OptimusException{
        String pkFunction = req.getParameter("pkFunction");//主键 就是functioncode
       // SmFunctionBO smf = funcService.getFuncMenuByPK(pkFunction);
    	List <Map> sysid =funcService.findFunByPk(pkFunction);//id
    	Map map1 =sysid.get(0);
        if(map1.get("superFuncCode")==null){
        	map1.put("superFuncCode", "system");
        	
        }
        
        //获取当前user
        HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
		Calendar calendar1 = Calendar.getInstance();
        
		//设置修改人ID  name time
        map1.remove("modifierId");
        map1.put("modifierId", user.getUserId());
        
        map1.remove("modifierName");
        map1.put("modifierName", user.getUserName());
        
        map1.remove("modifierTime");
        map1.put("modifierTime", calendar1);
        
        map1.put("pkFunction", map1.get("functionCode"));
        res.addForm("formpanel", map1);
        //res.addForm("formpanel", smf); 
		   
	} 	
	/** 
	 * 权限管理--菜单管理--菜单维护--具体维护
	 *  某个系统的所有子系统返回树
	 */
	@RequestMapping("getSupermenuTree")
	public void getFuncSupermenu(OptimusRequest req, OptimusResponse res) throws OptimusException{
		
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		String pkFunction="";
		List<Map<String, Object>> supermenu=null;
		
        List <String> pksys= new ArrayList<String>();
        pksys.add(pkSysIntegration);
        
        //获取当前菜单主键 看是否为空
		if(req.getParameter("pkFunction")!=null){
			
			//不为空
			pkFunction = req.getParameter("pkFunction");
			
			//获取所有菜单
			 supermenu =funcService.findFuncSuperMenu(pksys,pkFunction);
			 List<Map> lowermenu =null;
			 
			 //获取子节点菜单
			 lowermenu=funcService.findFuncDownMenu(pksys, pkFunction);

			 //移除子节点
			if(lowermenu.size()>0){
				//supermenu.removeAll(lowermenu);
				 for(Map map1:lowermenu){
						if(supermenu.contains(map1)){
							supermenu.remove(map1);
						}
					}
			}	
		}else{
			//上级菜单为空 找到所有的子节点
	        supermenu =funcService.findFuncSuperMenu(pksys,"");
		}
		
		//设置根节点
		for(Map map1:supermenu){
			if(map1.get("pId")==null){
				map1.put("pId", "system");
			}
		}
        Map rootNode = new HashMap();
        SmSysIntegrationBO sms =funcService.findSystem(pkSysIntegration);   
        rootNode.put("name", sms.getSystemName());
        rootNode.put("id", "system");
        rootNode.put("open", true);
        supermenu.add(rootNode);
        
        
        res.addTree("superFuncCode", supermenu);  	

  
	} 
	/**
	 * 权限管理--菜单管理--删除功能
	 * 并不是真正意义上的删除
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
    @RequestMapping("/deleteFunc1")
    public void deleteFunc1(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        String gnId = (String)req.getAttr("gnId");
        String pkSysIntegration=(String)req.getAttr("pkSysIntegration");
        if(StringUtils.isEmpty(gnId)||StringUtils.isEmpty(pkSysIntegration)){
            return;
        }
        	//设置菜单的修改时间 修改人..
    		SmFunctionBO smf = new SmFunctionBO();
			smf.setFunctionCode(gnId);
			HttpSession session = WebContext.getHttpSession();
			User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
			Calendar calendar1 = Calendar.getInstance();
			smf.setModifierTime(calendar1);	
			smf.setModifierId(user.getUserId());
			smf.setEffectiveMarker(AppConstants.EFFECTIVE_N);
			smf.setModifierName(user.getUserName());
			smf.setPkSysIntegration(pkSysIntegration);
			
			//获取叶子节点 设置修改人 。。 
			List <SmFunctionBO> leafsmfunction=funcService.findleaf(smf);//获取所有叶子
			if(leafsmfunction.size()>0){
				for(SmFunctionBO sm : leafsmfunction){
					sm.setModifierTime(calendar1);	
					sm.setModifierId(user.getUserId());
					sm.setEffectiveMarker(AppConstants.EFFECTIVE_N);
					sm.setModifierName(user.getUserName());
					funcService.deleteSomeFunc(sm);
					funcService.deleteRoleFunc(sm);
				}
			}			
			
			//删除当前节点
			funcService.deleteSomeFunc(smf);
			funcService.deleteRoleFunc(smf);
     
    }	
	/**
	 * 权限管理--菜单管理--删除功能
	 * 并不是真正意义上的删除
	 * 没有用--
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
    @RequestMapping("/deleteFunc")
    public void deleteFunc(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
        String funcIdsStr = (String)req.getAttr("funcIdsStr");
        if(StringUtils.isEmpty(funcIdsStr)){
            return;
        }
        List<String> funcIds = Arrays.asList(funcIdsStr.split(","));
        for(String s: funcIds){
    		SmFunctionBO smf = new SmFunctionBO();
			smf.setFunctionCode(s);
			HttpSession session = WebContext.getHttpSession();
			User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
			Calendar calendar1 = Calendar.getInstance();
			smf.setModifierTime(calendar1);	
			smf.setModifierId(user.getUserId());
			smf.setEffectiveMarker(AppConstants.EFFECTIVE_N);
			funcService.deleteSomeFunc(smf);
			funcService.deleteRoleFunc(smf);
        }
     
    }
	/**
	 * 权限管理--菜单管理--删除功能
	 */
    @RequestMapping("/deleteSomeFunc")
    public void deleteUser(OptimusRequest req, OptimusResponse resp) 
            throws OptimusException {
    	List<Map<String, String>> list = req.getGrid("gridpanel");
    	for(Map<String, String> map : list){
			String pkFunction = (String) map.get("pkFunction");
			SmFunctionBO smf = new SmFunctionBO();
			smf.setFunctionCode(pkFunction);
			smf.setEffectiveMarker(AppConstants.EFFECTIVE_N);
			funcService.deleteSomeFunc(smf);
		}
		resp.addAttr("back", "success");
    }	

	/**
	 * 删除某个系统
	 */
	@RequestMapping("/deleteSomeSystem")
	public void deleteSomeSystem(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		List<Map<String, String>> list = req.getGrid("gridpanel");
		for(Map<String, String> map : list){
			String pkSysIntegration = (String) map.get("pkSysIntegration");
			SmSysIntegrationBO sms = new SmSysIntegrationBO();
			sms.setPkSysIntegration(pkSysIntegration);
			funcService.deleteSomeSystem(sms);
		}
		resp.addAttr("back", "success");
	}	


		

		
	/**
	 * 权限管理--菜单管理--菜单维护--具体细节
	 * 功能新增设置
	 */
	@RequestMapping("/setSMFunction")
	public void setSMFunction(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		//获取当前user
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User	
		
		//获取改功能所在的当前系统
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		SmSysIntegrationBO sys = funcService.findSystem(pkSysIntegration);//获取system
	
		//设置左侧功能代码
		String roleCodeleft=sys.getSystemCode();
		
		//获取上级功能代码
		String superfunction =req.getParameter("superFunction");
		SmFunctionBO smfunction = new SmFunctionBO();
	
		//设置ID TIME 系统
		smfunction.setCreaterId(user.getUserId());
		smfunction.setCreaterTime(Calendar.getInstance());
		smfunction.setPkSysIntegration(pkSysIntegration);
        
		
		List <String> pksys= new ArrayList<String>();
        pksys.add(pkSysIntegration);
        Map form = new HashMap();
       //以下部分为设置层次码
		if(superfunction==null||superfunction.equals(null)||superfunction.equals("")||superfunction.equals("system")){
			//上级功能代码为空时
	        int supermenu =funcService.findFuncNumer(pksys,superfunction).size();
	        SmFunctionBO sm =funcService.findLeveCode(smfunction, supermenu, roleCodeleft);//得到层次码
	        smfunction.setFunctionCode(sm.getFunctionCode());
	        smfunction.setLevelCode(sm.getLevelCode());
	        
		}else{
			//上级功能代码部位空
			form.put("superFuncCode",superfunction);
			SmFunctionBO sm1=funcService.findFuncMenuByPK(superfunction);//这里的superfunction为pkFunction
			int supermenu =funcService.findFuncNumer(pksys,sm1.getFunctionCode()).size();
			SmFunctionBO sm =funcService.findLeveCodeHaveSuperCode(smfunction, supermenu, roleCodeleft, sm1);//得到层次码
								
	        smfunction.setFunctionCode(sm.getFunctionCode());
	        smfunction.setLevelCode(sm.getLevelCode());
		}

        form.put("pkSysIntegration", smfunction.getPkSysIntegration());
        form.put("functionCode", smfunction.getFunctionCode());
        form.put("createrTime", smfunction.getCreaterTime());
        form.put("createrId", smfunction.getCreaterId());
        form.put("createrName",user.getUserName());
        form.put("pkFunction", smfunction.getFunctionCode());
        form.put("levelCode", smfunction.getLevelCode());
        resp.addForm("formpanel", form);
		//resp.addForm("formpanel", smfunction); 

	}	
	/**
	 * 权限管理--菜单管理--菜单维护--具体细节（修改功能 修改层次码）
	 * 修改功能码
	 * 改变层次码
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/ChangelevelCode")
	public void setlevelCode(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		//获取系统主键  上级功能菜单  功能主键
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		String superFuncCode =req.getParameter("superFuncCode");	
		String pkFunction=req.getParameter("pkFunction");
		
		//当前BO
	    SmFunctionBO oldsmf = funcService.findFuncMenuByPK(pkFunction);//原来
	    
	    
		SmFunctionBO smfunction = new SmFunctionBO();
		smfunction.setPkSysIntegration(pkSysIntegration);
        List <String> pksys= new ArrayList<String>();
        pksys.add(pkSysIntegration);	
        int oldsupermenu =0;   
        
        //获取当前系统
		SmSysIntegrationBO sys = funcService.findSystem(pkSysIntegration);//获取system
		
		//左侧部分code
		String roleCodeleft=sys.getSystemCode();
	
		
		if((superFuncCode==null||superFuncCode.equals(null)||superFuncCode.equals(""))||(superFuncCode.equals("system")&&(pkFunction==null||pkFunction.equals("")||pkFunction.equals(null)))){
			//上级为空时
			if(oldsmf!=null){//判断是新增还是修改 现在为修改
				smfunction.setLevelCode(oldsmf.getFunctionCode());
			}else{
				//新增
		        int supermenu =funcService.findFuncNumer(pksys,superFuncCode).size();
		        SmFunctionBO sm =funcService.findLeveCode(smfunction, supermenu, roleCodeleft);//得到层次码
		        smfunction.setFunctionCode(sm.getFunctionCode());
		        smfunction.setLevelCode(sm.getLevelCode());				
			}		
		}else if(superFuncCode.equals("system")&&pkFunction!=null){
			//修改 并且上级功能为空
			smfunction.setLevelCode(pkFunction);
			smfunction.setFunctionCode(pkFunction);
		}else{
			
			//新增并且上级功能不为空
			
			SmFunctionBO  sm1 = null;
			//获取上级功能BO
			List <SmFunctionBO> l1 =funcService.findFuncMenuBycode(superFuncCode,pksys);
			for(SmFunctionBO s1:l1){
				sm1=s1;
			}
			int supermenu =funcService.findFuncNumer(pksys,superFuncCode).size();
	        if(oldsmf!=null){//修改
	        	smfunction.setLevelCode(""+oldsmf.getFunctionCode()+"."+sm1.getLevelCode());
	        }
	        else{//新增
				supermenu=supermenu+1;
				oldsupermenu=supermenu;
				SmFunctionBO sm2=funcService.findFuncMenuByPK(superFuncCode);//这里的superfunction为pkFunction
				SmFunctionBO sm =funcService.findLeveCodeHaveSuperCode(smfunction, supermenu, roleCodeleft, sm2);//得到层次码
									
		        smfunction.setFunctionCode(sm.getFunctionCode());
		        smfunction.setLevelCode(sm.getLevelCode());			
	        }
		}
			if(oldsmf!=null){//返回确定，前台判断是否有功能代码
				resp.addAttr("add", "N");
			}else{
				resp.addAttr("add", "Y");
			}
				
			resp.addAttr("functionCode",smfunction.getFunctionCode());
			resp.addAttr("levelCode", smfunction.getLevelCode());
		//resp.addForm("formpanel", smfunction); 
		
	}
	/**
	 * 属操作权限管理--菜单管理--菜单维护--（修改修改层次码）
	 * 暂时未使用 使用  tree改变功能菜单
	 * 改变层次码
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/ChangeTreelevelCode")
	public synchronized void  setTreelevelCode(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		//获取系统主键  上级功能菜单  功能主键
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		String superFuncCode =req.getParameter("superFuncCode");	
		String pkFunction=req.getParameter("pkFunction");
		
		//当前BO
	    SmFunctionBO oldsmf = funcService.findFuncMenuByPK(pkFunction);//原来
	    
	    
		SmFunctionBO smfunction = new SmFunctionBO();
		smfunction.setPkSysIntegration(pkSysIntegration);
        List <String> pksys= new ArrayList<String>();
        pksys.add(pkSysIntegration);	
        //获取当前系统
		SmSysIntegrationBO sys = funcService.findSystem(pkSysIntegration);//获取system
		
	
		//修改第一个功能的
		if(superFuncCode.equals("system")||StringUtils.isEmpty(superFuncCode)){
			//修改 并且上级功能为空
			smfunction.setLevelCode(pkFunction);
		}else{
			SmFunctionBO  sm1 = null;
			//获取上级功能BO
			List <SmFunctionBO> l1 =funcService.findFuncMenuBycode(superFuncCode,pksys);
			for(SmFunctionBO s1:l1){
				sm1=s1;
			}
	        	smfunction.setLevelCode(""+oldsmf.getFunctionCode()+"."+sm1.getLevelCode());
		}
		
		//获取静态user
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User		
		
		//通过主键获取未更改前的功能
		SmFunctionBO Oldsmfunction =oldsmf;
		
		smfunction.setFunctionCode(pkFunction);
		smfunction.setModifierId(user.getUserId());
		smfunction.setModifierTime(Calendar.getInstance());	
		smfunction.setModifierName(user.getUserName());
		smfunction.setSuperFuncCode(superFuncCode);
		funcService.updateSmfunction(smfunction);//更新	
		
		//获取当前菜单下的所有叶子节点 并改变叶子节点的层次码
		List <SmFunctionBO> leafsmfunction=funcService.findleaf(Oldsmfunction);//获取所有叶子
		String []old = Oldsmfunction.getLevelCode().split("\\.");
		int oldlen=old.length;
		if(leafsmfunction.size()>0){
			for(SmFunctionBO sm : leafsmfunction){
						String str="";
				 		String[]  nld= sm.getLevelCode().split("\\.");
				 		for(int i=0;i<(nld.length-oldlen);i++){
				 		 str=str+nld[i]+".";	
				 		}
				 		str=str+smfunction.getLevelCode();
				 		sm.setLevelCode(str);	
						sm.setModifierId(user.getUserId());
						sm.setModifierTime(Calendar.getInstance());	
						sm.setModifierName(user.getUserName());
				 		funcService.updateSmfunction(sm);//更新	
			}
		}
				
		resp.addAttr("status","success");

		//resp.addForm("formpanel", smfunction); 
		
		
	}	
	
	/**
	 * 
	 */
	@RequestMapping("/isFunc")
	public void isFunc(OptimusRequest req, OptimusResponse res) 
	        throws OptimusException {
        String pkFunction = req.getParameter("pkFunction");//就是functioncode
        SmFunctionBO smf = funcService.findFuncMenuByPK(pkFunction);

        if (smf.getFunctionType().equals(AppConstants.FUNC_TYPE_1)){
        	res.addAttr("back", false);
        }else{
        	res.addAttr("back", true);
        }
        
	}	
	/**
	 * 权限管理--菜单管理--菜单维护--具体细节（判断有无下一层）
	 * 根据当前菜单  判断有无下一层
	 */
	@RequestMapping("/haveleaf")
	public void haveleaf(OptimusRequest req, OptimusResponse res) 
	        throws OptimusException {
        String pkFunction = req.getParameter("pkFunction");
        SmFunctionBO smf = funcService.findFuncMenuByPK(pkFunction);
        List <String> pksys= new ArrayList<String>();
        pksys.add(smf.getPkSysIntegration());     
        int supermenu =funcService.findFuncNumer(pksys,smf.getFunctionCode()).size();	
        if (supermenu==0){
        	res.addAttr("back", false);
        }else{
        	res.addAttr("back", true);
        }
        
	}	
	/**
	 * 权限管理--菜单管理（跳转页面） 
	 * 跳转到菜单管理Menu_manage
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getMenu_manageHtml")
	public void getMenu_manageHtml(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		resp.addPage("/page/auth/menu_manage.jsp");  
	}	
	/**
	 * 权限管理--菜单管理--菜单维护（页面跳转）
	 * 从菜单管理到 该菜单的功能维护
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	
	@RequestMapping("/getMenu_fixHtml")
	public void getMenu_fixHtml(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		String pkSysIntegration = req.getParameter("pkSysIntegration");//获取系统主键
		resp.addAttr("pkSysIntegration",pkSysIntegration);//传递系统主键
		//resp.addPage("/page/auth/menu_fix.jsp");
		resp.addPage("/page/auth/menu_func_list.jsp");
		//resp.addPage("/page/auth/yangshi11.jsp");
        
	}	
	/**
	 * 权限管理--菜单管理--菜单维护（页面跳转 ）
	 * 从功能维护到具体的增改查 操作页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("/getMenu_crudHtml")
	public void getMenu_crudHtml(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {
		String pkSysIntegration = req.getParameter("pkSysIntegration");
		String pkFunction=req.getParameter("pkFunction");//这里的pkfunction 就是functioncode
		String queryone=req.getParameter("queryone");
		String superFunction=req.getParameter("superFunction");//pkfunction
		String type= req.getParameter("type");
		if (pkSysIntegration==null||pkSysIntegration.equals("")||pkSysIntegration.equals(null)){
			pkSysIntegration="";
		}
		if(pkFunction==null||pkFunction.equals("")||pkFunction.equals(null)){
			pkFunction="";
		}
		if(queryone==null||queryone.equals("")||queryone.equals(null)){
			queryone="";
		}
		if(superFunction==null||superFunction.equals("")||superFunction.equals(null)){
			superFunction="";
		}
		if(type==null||type.equals("")||type.equals("")){
			type="";
		}
		resp.addAttr("pkSysIntegration",pkSysIntegration);
		resp.addAttr("pkFunction",pkFunction);	
		resp.addAttr("queryone",queryone);
		resp.addAttr("superFunction",superFunction);	
		resp.addAttr("type", type);
		resp.addPage("/page/auth/menu_crud.jsp");
		
        
	}		
	/**
	 * 菜单管理--查询功能树
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
    @RequestMapping("/queryFuncTree")
    public void queryFuncTree(OptimusRequest req, OptimusResponse res) 
            throws OptimusException {
		String pkSysIntegration=null;
		pkSysIntegration = req.getParameter("pkSysIntegration");
		 List<Map<String, Object>> funcList =null;
        funcList  = funcService.findFuncTree(pkSysIntegration);
        List changeList =funcService.getChangeList(funcList,"system");//让空的pid变为system

        Map rootNode = new HashMap();
        SmSysIntegrationBO sms =funcService.findSystem(pkSysIntegration);   
        rootNode.put("name", sms.getSystemName());
        rootNode.put("id", "system");//将系统名的id设置为system
        rootNode.put("open", true);
        funcList.add(rootNode);
        res.addTree("funcTree", changeList);
    }	

    /**
     * 菜单管理--菜单维护（功能类型选项）
     * @param req
     * @param resp
     * @throws OptimusException
     */
	@RequestMapping("/findFuncType")
	public void findFuncType(OptimusRequest req, OptimusResponse resp) 
	        throws OptimusException {

		Map systemN=null;
		systemN=new HashMap();
	
		systemN=new HashMap();
		systemN.put("text", "首页或公共页面");
		systemN.put("value", ""+AppConstants.FUNC_TYPE_0);
		List system = new ArrayList();
		system.add(systemN);
		systemN=new HashMap();
		systemN.put("text", "模块");
		systemN.put("value",""+AppConstants.FUNC_TYPE_1);
		system.add(systemN);
		systemN=new HashMap();
		systemN.put("text", "功能");
		systemN.put("value",""+AppConstants.FUNC_TYPE_2);
		system.add(systemN);
	    resp.addTree("functionType", system);
	}  
	/**
	 * 权限管理--菜单维护（删除功能）
	 *删除某个系统的所有功能
	 */
	@RequestMapping("deleteAllFunc")
	public void deleteAllFunc(OptimusRequest req, OptimusResponse res) throws OptimusException{
		String pkSysIntegration = (String)req.getAttr("pkSysIntegration");
		String funcName=null;//功能名称
		HttpSession session = WebContext.getHttpSession();
		List<String> sysid = new ArrayList<String>();				
		sysid.add(pkSysIntegration);		
		List<Map> list=funcService.findFuncMenu(sysid,funcName);
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User	
		Calendar calendar1 = Calendar.getInstance();
		for(Map map1:list){
    		SmFunctionBO smf = new SmFunctionBO();
			smf.setFunctionCode(map1.get("functionCode").toString());
			smf.setModifierTime(calendar1);	
			smf.setModifierId(user.getUserId());
			smf.setEffectiveMarker(AppConstants.EFFECTIVE_N);
			funcService.deleteSomeFunc(smf);
			funcService.deleteRoleFunc(smf);
		}
		   
	}
}