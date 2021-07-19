package com.gwssi.ebaic.approve.util;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.optimus.core.common.ThreadLocalManager;
import com.gwssi.rodimus.dao.ApproveDaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.db.cache.RedisManager;

/**
 * 审核用户工具类。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class ApproveUserUtil {
	protected final static Logger logger = Logger.getLogger(ApproveUserUtil.class);
	/**
	 * 获取审批平台当前登录用户。
	 * 
	 * @return
	 */
	public static SysmgrUser getLoginUser(){
		HttpServletRequest req = (HttpServletRequest) ThreadLocalManager.get(ThreadLocalManager.RAW_HTTP_REQUEST);
		String sessionKey= getCookie(req, "DJAPPROVEUSER");
		//System.out.println("-----sessionKey---->"+sessionKey);
		if (StringUtils.isBlank(sessionKey)) {
			//return getDummyUser();
		    throw new EBaicException("登录超时，请退出重新登录。");
		}
		Jedis jedis= RedisManager.getResource(32);
		//System.out.println("-----getData1---->"+jedis.get(sessionKey.getBytes()));
		if (null==jedis.get(sessionKey.getBytes())) {
			//return getDummyUser();
		    throw new EBaicException("登录超时，请退出重新登录。");
		}else {
			@SuppressWarnings("unchecked")
			Map<String, String> map_session= (Map<String, String>)RedisManager.unserialize(jedis.get(sessionKey.getBytes()));
			//System.out.println("-----getData2---->"+map_session.size());
			SysmgrUser sysmgrUser=new SysmgrUser();
			sysmgrUser.setUserId(map_session.get("userId").toString());
			sysmgrUser.setUserName(map_session.get("userName").toString());
			sysmgrUser.setOrgCodeFk(map_session.get("userOrgId").toString());
			sysmgrUser.setStaffNo(map_session.get("userStaffNo")==null ? "" : map_session.get("userStaffNo"));
			sysmgrUser.setCaCertId(map_session.get("caCertId")==null ? "" : map_session.get("caCertId"));
			sysmgrUser.setSignPicUrl(map_session.get("signPicUrl")==null ? "" : map_session.get("signPicUrl"));
			//System.out.println(sysmgrUser.getUserId()+"------->"+sysmgrUser.getUserName());
			logger.info("审批系统登陆用户session同步信息:"+map_session.toString());
			return sysmgrUser;
		}
		
	}
	/**
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	private static String getCookie(HttpServletRequest request,String key){
		if(request==null){
			return null;
		}
		if(StringUtil.isBlank(key)){
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if(cookies==null || cookies.length==0){
			return null;
		}
		for(Cookie c : cookies){
			if(key.equals(c.getName())){
				String ret = c.getValue();
				return ret;
			}
		}
		return null;
	}
	
	/**
	 * 测试调试用。
	 * 
	 * @return 得到模拟账户。
	 */
	protected static SysmgrUser getDummyUser(){
		// FIXME 发布到生产环境时，应返回null。
		//throw new EBaicException("登录超时，请重新登录。");
		SysmgrUser sysmgrUser=new SysmgrUser();
		//sysmgrUser.setUserId("2c9e87b11864fc7b0118651648cd0068");
		sysmgrUser.setUserId("ff80808151e67f190151e74b8c180099");
		sysmgrUser.setOrgCodeFk("110108000");
		sysmgrUser.setUserName("史高占");
		sysmgrUser.setStaffNo("250");
		sysmgrUser.setCaCertId("sf3438643846853485737");
		sysmgrUser.setSignPicUrl("sdsfsfdsfs");
		return sysmgrUser;
	}
	
	/**
	 * 通过userId获取审批平台用户信息。
	 * 
	 * @param userId
	 * @return
	 */
	public static SysmgrUser getById(String userId){
		String sql = "select t.* from sysmgr_user t where t.flag='1' and t.user_id = ?";
		SysmgrUser ret = ApproveDaoUtil.getInstance().queryForRowBo(sql, SysmgrUser.class, userId);
		return ret;
	}
	
	/**
	 * 通过userName获取审批平台用户信息。
	 * 
	 * @param userName 真实姓名
	 * @return
	 */
	public static SysmgrUser getByName(String userName){
		String sql = "select t.* from sysmgr_user t where t.flag='1' and t.user_name = ?";
		SysmgrUser ret = ApproveDaoUtil.getInstance().queryForRowBo(sql, SysmgrUser.class, userName);
		return ret;
	}
	

	
}
