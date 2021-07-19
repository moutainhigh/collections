package com.gwssi.optimus.plugin.auth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.gwssi.application.model.SmWhiteFunctionBO;
import com.gwssi.optimus.core.cache.CacheBlock;
import com.gwssi.optimus.core.cache.CacheManager;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.common.Constants;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.security.AuthManager;
import com.gwssi.optimus.plugin.auth.model.Role;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;

public class OptimusAuthManager implements AuthManager {
	
	public final static String SUPERADMIN = "superadmin";
	public final static String LOGIN_NAME = "dlm";
	public final static String USER = "user";
	
	public final static String USER_TYPE_NORMAL = "1"; //用户类型：普通用户
	public final static String USER_TYPE_ADMIN = "2"; //用户类型：后台用户
	
	public final static String DEFAULT_ROLE_ID = "default_role"; //默认角色ID（普通用户）
	
	
	@Autowired
	private AuthService authService;
	
	public static boolean SECURITY_USE_CACHE = false; //用户角色权限 是否使用缓存
	
    public static int CACHE_SECURITY_INDEX = 3;  //缓存功能角色权限数据的分区号，默认为3
    
	
	private static CacheBlock cacheBlock;

	private final static Logger logger = LoggerFactory.getLogger(OptimusAuthManager.class);
	
	
    /**
     * 根据配置文件项SECURITY_USE_CACHE，若为true，则将每个角色对应的URL和组件集放入缓存中
     */
    public void init(){
    	
        //读取缓存功能角色权限的分区序号
        String cacheSecurityIndex = ConfigManager.getProperty("cache.security.index");
        if (StringUtils.isNotEmpty(cacheSecurityIndex))
            CACHE_SECURITY_INDEX = Integer.parseInt(cacheSecurityIndex);
        cacheBlock = CacheManager.getBlock(CACHE_SECURITY_INDEX);
      //是否使用缓存用户角色权限（默认不使用）
        String securityUserCache = ConfigManager.getProperty("security.useCache");
        if (StringUtils.isNotEmpty(securityUserCache) && securityUserCache.equals("true")) {
            SECURITY_USE_CACHE = true;
        }
        if(!SECURITY_USE_CACHE)
            return;
        if(Constants.CACHE_TARGET==Constants.CACHE_TARGET_REDIS && 
                !Constants.CACHE_LOAD_REDIS)
            return;
           
        //将角色权限相关数据加载到缓存
    	cacheBlock.clear();
    	
    	
    	//缓存应用集成系统，ID
    	 String appId=ConfigManager.getProperty("system.appID");
    	 cacheBlock.put("appId",appId);
    	//缓存角色 -功能---------功能 <角色ID，功能List>  // 为后面用户登入做检查
    	/**
    	 * 缓存角色-功能
    	 * 缓存数据格式<key,list>
    	 * key为角色ID，list为角色对应url
    	 */
    	try {
    		List<Map> roleList = authService.getRoleIdList();
    		for(Map roleMap : roleList){
    			String roleId = (String) roleMap.get("pkRole");
    			//通过角色ID,查询所有与角色对应功能
    			List<Map> urlLit = authService.getUrlList(roleId);
    			Set<Map> urlSet = new HashSet<Map>();
    			for(Map mapUrl : urlLit){
    				urlSet.add(mapUrl);
    			}
    			//界面组件功能未开发完全注释掉
//    			List<Map> zjList = authService.getZjList(roleId);
//    			Set<String> zjSet = new HashSet<String>();
//    			for(Map mapZj : zjList){
//    				String zj = (String) mapZj.get("zjCode");
//    				zjSet.add(zj);
//    			}
    			Role role = new Role();
    			role.setRoleId(roleId);
    			role.setUrlSet(urlSet);
//    			role.setWidgetCodeSet(zjSet);
                cacheBlock.put(roleId, role);
    		}
    		
    		//将白名单加入缓存
    		List<SmWhiteFunctionBO> urlWhiteList = authService.getUrlWhiteList();
    		cacheBlock.put("urlWhiteList", urlWhiteList);
    		
    		
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("", e);
		}
    }

    
    /** 更新角色功能缓存，功能角色维护发生变化需要更新
     * 
     * @param roleId 角色ID
     * @param urlList 功能列表，结构List<Map<"key",value>>，key名称为functionUrl
     */
    public static void updateCache(String roleId,List<Map> urlList){
    
			//通过角色ID,查询所有与角色对应功能
			Set<Map> urlSet = new HashSet<Map>();
			for(Map mapUrl : urlList){
				urlSet.add(mapUrl);
			}
			Role role = new Role();
			role.setRoleId(roleId);
			role.setUrlSet(urlSet);
            cacheBlock.put(roleId, role);
    }
    
    
    
    
    
    
    /**
     * 检查是否拥有当前操作权限
     * 返回值 1/2/3
     * 1、为权限验证通过
     * 2、未登入
     * 3、无操作权限
     */
    public int hasUrlRight(HttpServletRequest req, String url) {
    	int flag = URL_AUTH_NO_RIGHT;
    	/**
    	 * 去除超级用户，不分后台管理用户，所有功能改为通过角色功能控制
    	 */
//    	Object obj = req.getSession().getAttribute(SUPERADMIN);
//    	if(null!=obj){
//    		boolean isSA = (Boolean)obj;
//    		if(isSA){
//    			return URL_AUTH_PASS;
//    		}
//    	}
    	//查询数据库不进行修改，将权限存储于缓存
		if(StringUtils.isNotEmpty(url)){
			if(SECURITY_USE_CACHE){
				flag = hasUrlRightByCache(req, url);
			}else{
				
				/**
				 * 如需要通过数据库检查当前用户对当前操作有权限访问，请修改下面注释方法
				 */
//				flag = hasUrlRightByDB(req, url);
			}
		}
		return flag;
    }

	/**
	 * 权限检查
	 * @param req
	 * @param url 用户请求url
	 * @return
	 */
	private int hasUrlRightByCache(HttpServletRequest req, String url){
	    //判断url是否在白名单中：暂时留着
	    Object urlWhiteListObj = cacheBlock.get("urlWhiteList");
	    
    	if(urlWhiteListObj != null){
    	    List<SmWhiteFunctionBO> urlWhiteList = (List<SmWhiteFunctionBO>)urlWhiteListObj;
			for(SmWhiteFunctionBO bo : urlWhiteList){
				String flag =bo.getIsExactMatch();
				String whiteUrl = bo.getFunctionUrl();
				if("1".equals(flag)){
					if(url.equals(whiteUrl)){
						return URL_AUTH_PASS;
					}
				}else{
					 PathMatcher matcher = new AntPathMatcher();
					 if(matcher.match(whiteUrl, url)){
						 return URL_AUTH_PASS;
					 } 
				}
			}
		}
    	String dlm = (String) req.getSession().getAttribute(LOGIN_NAME);
    	if(StringUtils.isEmpty(dlm)){
	    	return URL_AUTH_NOT_LOGIN; 
    	}
    	User user = (User)req.getSession().getAttribute(USER);
        if(user==null){
            return URL_AUTH_NO_RIGHT;
        }
        
        List<Map> roleList = user.getRoleList();
        for(Map  map : roleList){
        	
        	String roleId=(String) map.get("pkRole");
            Object roleObj = cacheBlock.get(roleId);
            
            if(roleObj==null)
                continue;
            Role role = (Role)roleObj;
            Set<Map> urlSet = role.getUrlSet();
            if(urlSet == null)
                continue;
            for(Map urlMap : urlSet){
                String functionUrl = (String) urlMap.get("functionUrl");
//                if("1".equals(flag)){
                    if(url.equals(functionUrl)){
                        return URL_AUTH_PASS;
                    }
//                }else{
//                    PathMatcher matcher = new AntPathMatcher();
//                    if(matcher.match(whiteUrl, url)){
//                        return URL_AUTH_PASS;
//                    } 
//                }
            }
        }
    	return URL_AUTH_NO_RIGHT;
    }
    
	/**
	 * 注释================取消重数据库验证用户每次操作是否有权限
	 * @param req
	 * @param widgetCodeList
	 * @return
	 */
//    private int hasUrlRightByDB(HttpServletRequest req, String url){
//    	try {
//			List<Map> urlWhiteList = authService.getUrlWhiteList();
//			if(urlWhiteList != null){
//				for(Map urlMap : urlWhiteList){
//					String flag = (String) urlMap.get("sfjqlj");
//					String whiteUrl = (String) urlMap.get("url");
//					if("1".equals(flag)){
//						if(url.equals(whiteUrl)){
//							return URL_AUTH_PASS;
//						}
//					}else{
//						 PathMatcher matcher = new AntPathMatcher();
//						 if(matcher.match(whiteUrl, url)){
//							 return URL_AUTH_PASS;
//						 } 
//						 
//					}
//				}
//			}
//			String dlm = (String) req.getSession().getAttribute(LOGIN_NAME);
//			if(StringUtils.isNotEmpty(dlm)){
//				List<Map> roleList = authService.getRoleIdListByLoginName(dlm);
//				for(Map roleMap : roleList){
//	    			String roleId = (String) roleMap.get("jsId");
//	    			List<Map> urlList = authService.getUrlList(roleId);
//	    			for(Map urlMap : urlList){
//	    				String flag = (String) urlMap.get("sfjqlj");
//	    				String whiteUrl = (String) urlMap.get("url");
//	    				if("1".equals(flag)){
//	    					if(url.equals(whiteUrl)){
//	    						return URL_AUTH_PASS;
//	    					}
//	    				}else{
//	    					 PathMatcher matcher = new AntPathMatcher();
//	    					 if(matcher.match(whiteUrl, url)){
//	    						 return URL_AUTH_PASS;
//	    					 } 
//	    				}
//	    			}
//				}
//			}else{
//				return URL_AUTH_NOT_LOGIN;
//			}
//			return URL_AUTH_NO_RIGHT;
//    	} catch (OptimusException e) {
//			e.printStackTrace();
//			return URL_AUTH_NO_RIGHT;
//		}
//    }
//    
    private List<String> hasWidgetRightByCache(HttpServletRequest req,
			List<String> widgetCodeList) {
		// TODO Auto-generated method stub
    	String dlm = (String)req.getSession().getAttribute(LOGIN_NAME);
    	User user = (User) req.getSession().getAttribute(USER);
    	List<String> roleList = new ArrayList<String>();
    	List<String> codeList = new ArrayList<String>();
    	Set<String> weightCodeSet = new HashSet<String>();
    	if(user!=null){
//    		roleList = user.getRoleIdList();
    	}
    	for(String roleId : roleList){
    		Role role = (Role) cacheBlock.get(roleId);
    		Set<String> weightLocalSet = new HashSet<String>();
    		if(role!=null){
    			weightLocalSet = role.getWidgetCodeSet();
    			if(weightLocalSet!=null)
    				weightCodeSet.addAll(weightLocalSet);
    		}
        }
        if(widgetCodeList!=null){
			for(Iterator<String> i = widgetCodeList.iterator();i.hasNext(); ){
				String widgetCode = i.next();
				if(StringUtils.isNotEmpty(widgetCode)&&!weightCodeSet.contains(widgetCode)){
				}else{
					codeList.add(widgetCode);
				}
	    	}
        }
		return codeList;
	}


	@Override
	public List<String> hasWidgetRight(HttpServletRequest arg0,
			List<String> arg1) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int checkSession(HttpServletRequest paramHttpServletRequest) {
		// TODO Auto-generated method stub
		return 0;
	}
    

}
