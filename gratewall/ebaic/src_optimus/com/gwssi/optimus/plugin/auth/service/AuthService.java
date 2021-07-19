package com.gwssi.optimus.plugin.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.common.Constants;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.TPtJsBO;
import com.gwssi.optimus.plugin.auth.model.TPtYhBO;

@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class AuthService extends BaseService {

	public List<Map> getUserList() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "SELECT dlm from t_pt_yh";
		return dao.queryForList(sql, null);
	}

	/**
	 * 根据用户登录名查询出该用户对应的角色列表
	 * @param userId
	 *            用户登录名
	 * @return 用户对应的角色列表，Map中key是jsId
	 * @throws OptimusException
	 */
	public List<Map> getRoleIdListByLoginName(String userId) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select b.JS_ID from t_pt_yh a,t_pt_yhjs b ");
		if (StringUtils.isNotEmpty(userId)) {
		    sql.append("where a.LOGIN_NAME='" + userId + "' and a.USER_ID=b.USER_ID");
		}
		List roleList = dao.queryForList(sql.toString(), null);
		if(roleList==null)
            roleList = new ArrayList<Map>();
        if(roleList.isEmpty()){
            Map map = new HashMap();
            map.put("jsId", OptimusAuthManager.DEFAULT_ROLE_ID);
            roleList.add(map);
        }
		return roleList;
	}

	public List<Map> getRoleIdList() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select JS_ID from t_pt_js";
		return dao.queryForList(sql.toString(), null);
	}

	public List<Map> getUrlList(String roleId) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		String urlSql = "SELECT b.URL,b.SFJQLJ FROM t_pt_jsgn a,t_pt_urlzy b WHERE a.js_id='"
		    + roleId +"' AND a.gn_id=b.gn_id";
		List urlList = dao.queryForList(urlSql, null);
		//查询角色对应的菜单功能自身的URL集
		String gnSql = "select t.url, '1' as sfjqlj from t_pt_gn t, t_pt_jsgn jg " +
				"where t.gn_id=jg.gn_id and jg.js_id='"+roleId+"'";
		List gnList = dao.queryForList(gnSql, null);
		if(gnList!=null && !gnList.isEmpty()){
		    if(urlList==null)
		        urlList = new ArrayList<Map>();
		    urlList.addAll(gnList);
		}
		return urlList;
	}

	public List<Map> getZjList(String roleId) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "SELECT b.zj_code FROM t_pt_jsgn a,t_pt_zjzy b WHERE a.js_id='"
		     + roleId +"' AND a.gn_id=b.gn_id";
		return dao.queryForList(sql, null);
	}

	public List<Map> getUrlWhiteList() throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select * from t_pt_urlzy WHERE LX='2'";
		return dao.queryForList(sql, null);
	}
	
	/**
     * 根据用户的登录名判断是否可以登录
     * 
     * 
     * @param user_name 登录名
     * @param user_pwd 密码
     * @return 布尔值是或否
     * @throws OptimusException
     */
    public TPtYhBO login(String login_name,String user_pwd) throws OptimusException {
//      boolean flag = false;
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "SELECT * from t_pt_yh t where login_name = '" + login_name 
            + "' and user_pwd = '" + user_pwd + "'  ";
        List<TPtYhBO> list_user= dao.queryForList(TPtYhBO.class, sql, null);
        if(list_user==null || list_user.isEmpty())
            return null;
        TPtYhBO user = list_user.get(0);
        return user;
    }

    /**
     * 根据登录名获取功能树中是菜单的部分
     * @param dlm 登录名
     * @param flag  布尔值表示是否是超级管理员
     * @return  该登录名下是菜单的功能树
     * @throws OptimusException
     */
    public List queryMenu(String login_name,boolean flag) throws OptimusException {
        // TODO Auto-generated method stub
        IPersistenceDAO dao = getPersistenceDAO();
        StringBuffer sqlSbf = null;
        if(Constants.SECURITY_AUTHCHECK && !flag){
            sqlSbf = new StringBuffer("select gn.gn_id as id, " +
                    "gn.sjgn_id as pid, gn.gn_mc as name ,gn.url as frameUrl " 
                    + "from t_pt_gn gn,t_pt_yhjs yhjs,t_pt_jsgn jsgn,t_pt_yh yh " 
                    + "WHERE gn.sfcd='1' ");
            sqlSbf.append("AND yh.user_id=yhjs.USER_ID AND yhjs.js_id=jsgn.js_id " +
                    "AND jsgn.gn_id = gn.gn_id AND yh.login_name = '" )
                    .append(login_name).append( "'");
        }else{
            sqlSbf = new StringBuffer("select t.gn_id as id, t.sjgn_id as pid, " +
                "t.gn_mc as name ,t.url as frameUrl from t_pt_gn t WHERE t.sfcd='1' ");
        }
        sqlSbf.append(" order by xh");
        List funcList = dao.queryForList(sqlSbf.toString(), null);
        return funcList;
    }
    
    
//  public List<Map> getUserList() throws OptimusException {
//      IPersistenceDAO dao = getPersistenceDAO();
//      String sql = "SELECT dlm from t_pt_yh";
//      return dao.queryForList(sql, null);
//  }
    
    /**
     * 根据登录名得到登陆后跳转的URL
     * @param user_name 登录名
     * @return 登录后跳转的URL列表，其中为空是没有默认跳转URL
     * @throws OptimusException
     */
    public List getDefaultUrl(String login_name) throws OptimusException {
        // TODO Auto-generated method stub
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "SELECT b.DEF_URL from t_pt_yh a,t_pt_js b where a.login_name= '" 
            + login_name + "' and a.ZY_JS_ID = b.JS_ID ";
        List urlList = dao.queryForList(sql, null);
        return urlList;
    }
    
    /**
     * 获取默认角色（普通用户）
     * @return
     * @throws OptimusException
     */
    public TPtJsBO getDefaultRole() throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        TPtJsBO defaultJsBO = dao.queryByKey(TPtJsBO.class, OptimusAuthManager.DEFAULT_ROLE_ID);
        return defaultJsBO;
    }
}
