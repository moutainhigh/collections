package com.gwssi.application.webservice.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.common.ParamUtil;
import com.gwssi.application.home.service.HomeService;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;

/**
 * 接口实现类/调用人事系统服务实现类 com.gwssi.application.webservice.receive
 * ReceiveHrAppImpl.java 下午2:01:05
 * 
 * @author wuminghua
 */

public class ReceiveHrAppImpl implements IReceiveHrApp {
	public static Properties userRolepro = ConfigManager.getProperties("UserRolesGet");
	/**
	 * 设置用户的岗位，角色，是否超级管理员，管理员所对应的系统列表，所具有的系统的列表
	 */
	@Override
	public User getUserInfo(String userId) {
		User user = new User();
		List list = new ArrayList<String>();

		PostService postService = new PostService();
		try {
			HrUsersBO userBo = postService.getLoginUser(userId);
			list = postService.getPostByUser(userBo.getOrganId());

			user.setUserId(userId);
			user.setUserName(userBo.getUserName());
			user.setPostList(list);
			
			/*获取角色部分*/
			AuthService authService = new AuthService();		
			List<Map> roleList = authService.getRoleIdByPost(
					user.getPostList());
			user.setRoleList(roleList);
	
			this.setAdminAndList(user);
			this.setUserfunc(user);
			this.setAuthfuncList(user);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return user;
	}
	/**
	 * 判断是否管理员以及存储对应列表
	 * @param user
	 */
	private void setAdminAndList(User user){

		List<Map> roleList= user.getRoleList();
		SysListService sysListService =new SysListService();
		
		Set<String> adminPksysList = new HashSet <String>();//管理员系统主键
		Set<String> userPksysList = new HashSet <String>();//所具有的系统主键
		String supersys=null;
		SmRoleBO bo = new SmRoleBO();
		System.out.println("系统角色名："+user.getRoleList());
		try {
			for (int i = 0; i < roleList.size(); i++) {
				ParamUtil.mapToBean((Map) roleList.get(i), bo, false);
				if (AppConstants.ROLE_TYPE_SUPER.equals(bo.getRoleType())) {
					/*超级管理员*/
					user.setSuperAdmin(true);
					supersys=bo.getPkSysIntegration();
					SmSysIntegrationBO bo1 =sysListService.doqueySysbyKey(supersys) ;
					System.out.println("超级管理员系统名："+bo1.getSystemName() +"  "+bo1.getSystemCode()+"   "+bo1.getIntegratedUrl());
				} else if (AppConstants.ROLE_TYPE_SYS.equals(bo.getRoleType())) {
					/*子系统管理员*/
					adminPksysList.add(bo.getPkSysIntegration());
				}
				userPksysList.add(bo.getPkSysIntegration());
			}
			
			user.setUserSysList(sysListService.doquerySysList(userPksysList));
			List<SmSysIntegrationBO> adminSysList =sysListService.doquerySysList(adminPksysList);
			user.setAdminSysList(adminSysList);
			
	
			Properties properties = ConfigManager.getProperties("common");
			String sysAppCode = properties.getProperty("common.sys.code");
			
			if(adminSysList!=null&&adminSysList.size()>0){
			/*判断是否当前系统管理员*/
				for(SmSysIntegrationBO smbo:adminSysList){
					if(StringUtils.equals(sysAppCode, smbo.getSystemCode())){
						user.setCurrAdmin(true);
					}
				}
		 
			}
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置当前用户所访问的功能
	 * @param user
	 */
	private  void setUserfunc(User user){
		HomeService homeService = new HomeService();
		boolean isSuperAdmin =user.getIsSuperAdmin();
		boolean isCurradmin = user.getIsCurrAdmin();
		List<Map> funcList = new ArrayList();
		Properties properties = ConfigManager.getProperties("common");
		String sysAppCode = properties.getProperty("common.sys.code");
		String sysFuncCode = properties.getProperty("common.sys.func");
		try {
/*				if (isSuperAdmin){
					funcList = homeService.getFunc(sysAppCode);
				}else */
				if(isCurradmin||(user.getAdminSysList()!=null&&user.getAdminSysList().size()>0)){
					funcList = homeService.getFunc(sysAppCode);
					//funcList = homeService.getFunc(sysAppCode, sysFuncCode);
				}else{
					
						funcList = homeService.getFunc(user.getRoleList(), sysAppCode);
		
				}
		user.setFunclist(funcList);
		
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 需要验证的功能列表
	 * @param user
	 */
	private void setAuthfuncList(User user){
	//	ApplicationContext app1 = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getSession().getServletContext());
		//AuthService authService=(AuthService) app1.getBean("authService");
		AuthService authService = new 	AuthService ();
		List<String> needFilterlist = new ArrayList<String>();
		List<Map> urlList = null;
		try {
			urlList = authService.dofiterUrl();
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		
		for(Map<String, String> map1:urlList){
			needFilterlist.add(map1.get("functionUrl"));
		}
		//needFilterlist.remove("/page/home/index.jsp");
		user.setAuthfunclist(needFilterlist);
	}
	/**
	 * 获取客户端ip
	 * @param request
	 * @return
	 */
	@Override
    public String getIP(HttpServletRequest request) {     
       String ip = request.getHeader("x-forwarded-for");     
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
          ip = request.getHeader("Proxy-Client-IP");     
      }     
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
          ip = request.getHeader("WL-Proxy-Client-IP");     
       }     
      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
           ip = request.getRemoteAddr();     
      }     
      return ip;     
    }
	/**
	 * 通过用户角色表直接获取用户信息
	 */
	@Override
	public User getUserInfoByUserRoles(String userId) {

		User user = new User();
		List list = new ArrayList<String>();

		PostService postService = new PostService();
		try {
			HrUsersBO userBo = postService.getLoginUser(userId);
			//list = postService.getPostByUser(userBo.getOrganId());  //获取岗位，不用岗位进行判断，直接获取用户的角色

			user.setUserId(userId);
			user.setUserName(userBo.getUserName());
			//user.setPostList(list);
			
			/*获取角色部分*/
			AuthService authService = new AuthService();	
			System.out.println("开始获取角色");
			
			
			
			List<Map> roleList = authService.getRoleIdByUsersRole(
					user.getUserId());
			
			user.setRoleList(roleList);
	
			this.setAdminAndListByUserViews(user);
			this.setUserfunc(user);
			this.setAuthfuncList(user);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return user;
		
	}    
	
	/**
	 * 通过用户和角色关联表 来完成
	 * 2016年11月7日14:49:09
	 * 柴浩伟
	 * @param user
	 * @throws OptimusException 
	 */
	private void setAdminAndListByUserViews(User user) throws OptimusException {
		List<Map> roleList= user.getRoleList();
		SysListService sysListService =new SysListService();
		Set<String> adminPksysList = new HashSet <String>();//管理员系统主键
		Set<String> userPksysList = new HashSet <String>();//所具有的系统主键
		String supersys=null;
		
/*		String superstr = userRolepro.getProperty("curr.sys.superAdmin.code");
		superstr=StringUtils.upperCase(superstr);
		List<String> superlist = null;
		if(StringUtils.isNotBlank(superstr)){
			superlist= new ArrayList<String>();
			String cnList[] = superstr.split(",");
			Collections.addAll(superlist, cnList);
		}*/
		String adminstr = userRolepro.getProperty("curr.sys.adimin.code");
		adminstr=StringUtils.upperCase(adminstr);
		List<String> adminlist = null;
		if(StringUtils.isNotBlank(adminstr)){
			adminlist= new ArrayList<String>();
			String cnList[] = adminstr.split(",");
			Collections.addAll(adminlist, cnList);
		}
		
		for(int i=0;i<roleList.size();i++){	
	
			String roleCode = (String) roleList.get(i).get("roleCode");
			System.out.println(roleCode);
/*			if(superlist!=null){
				if(superlist.contains(roleCode)){
					user.setSuperAdmin(true);
					System.out.println("是当前系统超级管理员");
					//supersys=(String) roleList.get(i).get("pkSysIntegration");
				}
			}*/
			if(adminlist!=null){
				if(adminlist.contains(roleCode)){
					System.out.println("是当前系统管理员");
					user.setCurrAdmin(true);
				}
			}
		}
		
/*		List<Map> roleList= user.getRoleList();
		for(Map<String,Object> map:roleList){
			System.out.println(map.get("roleCode"));
			String roleTyp1=null;
			if(null!=map.get("roleType")){
				roleTyp1=(String) map.get("roleType");
			}
			if(AppConstants.ROLE_TYPE_SYS.equals(roleTyp1)||AppConstants.ROLE_TYPE_SUPER.equals(roleTyp1)){
				user.setCurrAdmin(true);	
			}
		}*/
		
/*		
		List<Map> roleList= user.getRoleList();
		SysListService sysListService =new SysListService();
		Set<String> adminPksysList = new HashSet <String>();//管理员系统主键
		Set<String> userPksysList = new HashSet <String>();//所具有的系统主键
		String supersys=null;
		
		for(int i=0;i<roleList.size();i++){
			if(null!=roleList.get(i).get("roleType")){
			String roleType = (String) roleList.get(i).get("roleType");
			if (AppConstants.ROLE_TYPE_SUPER.equals(roleType)) {
				超级管理员
				user.setSuperAdmin(true);
				
				supersys=(String) roleList.get(i).get("pkSysIntegration");
				SmSysIntegrationBO bo1 =sysListService.doqueySysbyKey(supersys) ;
				System.out.println("超级管理员系统名："+bo1.getSystemName() +"  "+bo1.getSystemCode()+"   "+bo1.getIntegratedUrl());
			} else if (AppConstants.ROLE_TYPE_SYS.equals(roleType)) {
				子系统管理员
				adminPksysList.add((String) roleList.get(i).get("pkSysIntegration"));
			}
			userPksysList.add((String) roleList.get(i).get("pkSysIntegration"));
			}
		}*/
	}
}
