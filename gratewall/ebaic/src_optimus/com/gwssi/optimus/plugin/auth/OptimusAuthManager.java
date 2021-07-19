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
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.gwssi.optimus.core.cache.CacheBlock;
import com.gwssi.optimus.core.cache.CacheManager;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.common.Constants;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.security.AuthManager;
import com.gwssi.optimus.plugin.auth.model.Role;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;


@Component
@SuppressWarnings({"rawtypes","unchecked"})
public class OptimusAuthManager implements AuthManager {
	
	public final static String SUPERADMIN = "superadmin";
	public final static String LOGIN_NAME = "dlm";
	public final static String USER = "user";
	
	public final static String USER_TYPE_NORMAL = "1"; //用户类型：普通用户
	public final static String USER_TYPE_ADMIN = "2"; //用户类型：后台用户
	
	public final static String DEFAULT_ROLE_ID = "default_role"; //默认角色ID（普通用户）
	
	public final static String LOGIN_USER_TYPE = "login_user_type";//登录用户类型
	public final static String LOGIN_USER_TYPE_PERSON = "person";//自然人用户
	public final static String LOGIN_USER_TYPE_ENT = "ent";//企业用户
	
	
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
    	try {
    		List<Map> roleList = authService.getRoleIdList();
    		for(Map roleMap : roleList){
    			String roleId = (String) roleMap.get("jsId");
    			List<Map> urlLit = authService.getUrlList(roleId);
    			Set<Map> urlSet = new HashSet<Map>();
    			List<Map> zjList = authService.getZjList(roleId);
    			Set<String> zjSet = new HashSet<String>();
    			for(Map mapUrl : urlLit){
    				urlSet.add(mapUrl);
    			}
    			for(Map mapZj : zjList){
    				String zj = (String) mapZj.get("zjCode");
    				zjSet.add(zj);
    			}
    			Role role = new Role();
    			role.setRoleId(roleId);
    			role.setUrlSet(urlSet);
    			role.setWidgetCodeSet(zjSet);
                cacheBlock.put(roleId, role);
    		}
    		List<Map> urlWhiteList = authService.getUrlWhiteList();
    		cacheBlock.put("urlWhiteList", urlWhiteList);
//    		Set<String> testSet = (Set) CacheManagerPrivate.get(Constants.CACHE_SECURITY_INDEX, "urlWhiteList");
//    		User user2 = (User) CacheManagerPrivate.get(Constants.CACHE_SECURITY_INDEX, "zhuyang");
//			Role role2 = (Role) CacheManagerPrivate.get(Constants.CACHE_SECURITY_INDEX, "54b1d7435d6e41b385da165991e4c8fb");
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("", e);
		}
    }

   
    public int hasUrlRight(HttpServletRequest req, String url) {
        // TODO Auto-generated method stub
    	int flag = URL_AUTH_NO_RIGHT;
    	Object obj = req.getSession().getAttribute(SUPERADMIN);
    	if(null!=obj){
    		boolean isSA = (Boolean)obj;
    		if(isSA){
    			return URL_AUTH_PASS;
    		}
    	}
		if(StringUtils.isNotEmpty(url)){
			if(SECURITY_USE_CACHE){
				flag = hasUrlRightByCache(req, url);
			}else{
				flag = hasUrlRightByDB(req, url);
			}
		}
		return flag;
    }

    /* (non-Javadoc)
     * @see com.gwssi.optimus.core.web.security.AuthManager#hasWidgetRight(javax.servlet.http.HttpServletRequest, java.util.List)
     */
    public List<String> hasWidgetRight(HttpServletRequest req, List<String> widgetCodeList) {
        // TODO Auto-generated method stub
    	if(null==req.getSession().getAttribute(SUPERADMIN)){
    	}else{
	    	boolean isSA = (Boolean) req.getSession().getAttribute(SUPERADMIN);
	    	if(isSA){
	    		return widgetCodeList;
	    	}
    	}
    	String dlm = (String) req.getSession().getAttribute(LOGIN_NAME);
    	if(StringUtils.isEmpty(dlm)){
    		return null;
    	}else{
    		if(SECURITY_USE_CACHE){
    			return hasWidgetRightByCache(req, widgetCodeList);
    		}else{
    			return hasWidgetRightByDB(req, widgetCodeList);
    		}
    	}
	}

	/**
	 * @param req
	 * @param url
	 * @return
	 */
	private int hasUrlRightByCache(HttpServletRequest req, String url){
	    //判断url是否在白名单中
	    Object urlWhiteListObj = cacheBlock.get("urlWhiteList");
    	if(urlWhiteListObj != null){
    	    List<Map> urlWhiteList = (List<Map>)urlWhiteListObj;
			for(Map urlMap : urlWhiteList){
				String flag = (String) urlMap.get("sfjqlj");
				String whiteUrl = (String) urlMap.get("url");
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
        List<String> roleList = user.getRoleIdList();
        for(String roleId : roleList){
            Object roleObj = cacheBlock.get(roleId);
            if(roleObj==null)
                continue;
            Role role = (Role)roleObj;
            Set<Map> urlSet = role.getUrlSet();
            if(urlSet == null)
                continue;
            for(Map urlMap : urlSet){
                String flag = (String) urlMap.get("sfjqlj");
                String whiteUrl = (String) urlMap.get("url");
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
    	return URL_AUTH_NO_RIGHT;
    }
    
    private int hasUrlRightByDB(HttpServletRequest req, String url){
    	try {
			List<Map> urlWhiteList = authService.getUrlWhiteList();
			if(urlWhiteList != null){
				for(Map urlMap : urlWhiteList){
					String flag = (String) urlMap.get("sfjqlj");
					String whiteUrl = (String) urlMap.get("url");
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
			if(StringUtils.isNotEmpty(dlm)){
				List<Map> roleList = authService.getRoleIdListByLoginName(dlm);
				for(Map roleMap : roleList){
	    			String roleId = (String) roleMap.get("jsId");
	    			List<Map> urlList = authService.getUrlList(roleId);
	    			for(Map urlMap : urlList){
	    				String flag = (String) urlMap.get("sfjqlj");
	    				String whiteUrl = (String) urlMap.get("url");
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
			}else{
				return URL_AUTH_NOT_LOGIN;
			}
			return URL_AUTH_NO_RIGHT;
    	} catch (OptimusException e) {
			e.printStackTrace();
			return URL_AUTH_NO_RIGHT;
		}
    }
    
    private List<String> hasWidgetRightByCache(HttpServletRequest req,
			List<String> widgetCodeList) {
		// TODO Auto-generated method stub
    	//String dlm = (String)req.getSession().getAttribute(LOGIN_NAME);
    	User user = (User) req.getSession().getAttribute(USER);
    	List<String> roleList = new ArrayList<String>();
    	List<String> codeList = new ArrayList<String>();
    	Set<String> weightCodeSet = new HashSet<String>();
    	if(user!=null){
    		roleList = user.getRoleIdList();
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
    
	private List<String> hasWidgetRightByDB(HttpServletRequest req,
			List<String> widgetCodeList) {
		// TODO Auto-generated method stub
		try {
			String dlm = (String)req.getSession().getAttribute(LOGIN_NAME);
			Set<String> weightCodeSet = new HashSet<String>();
			List<String> codeList = new ArrayList<String>();
			List<Map> roleList = authService.getRoleIdListByLoginName(dlm);
			for(Map roleMap : roleList){
    			String roleId = (String) roleMap.get("jsId");
    			List<Map> zjList = authService.getZjList(roleId);
    			for(Map zjMap : zjList){
    				String zjCode = (String) zjMap.get("zjCode");
    				weightCodeSet.add(zjCode);
    			}
			}
			if(widgetCodeList!=null){
				for(Iterator<String> it = widgetCodeList.iterator();it.hasNext(); ){
					String widgetCode = it.next();
					if(StringUtils.isNotEmpty(widgetCode)&&!weightCodeSet.contains(widgetCode)){
					}else{
						codeList.add(widgetCode);
					}
					
		    	}
	        }
			return codeList;
    	} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
