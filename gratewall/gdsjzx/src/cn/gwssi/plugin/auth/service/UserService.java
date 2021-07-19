package cn.gwssi.plugin.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gwssi.plugin.auth.model.TPtJsBO;
import cn.gwssi.plugin.auth.model.TPtYhBO;
import cn.gwssi.plugin.auth.model.TPtYhjsBO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class UserService extends BaseService{

    private Object aaa;

	public void saveUser(TPtYhBO yhBO) throws OptimusException{
        // TODO Auto-generated method stub
        IPersistenceDAO dao = getPersistenceDAO();
        yhBO.setYxBj("1");
        dao.insert(yhBO);
    }
    
    public Map<String,String> getUser(String userId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.* from t_pt_yh t,t_pt_js a where t.USER_ID = '"+userId+"'";
        String sql2 = "select a.JS_MC as name from t_pt_yhjs t,t_pt_js a " +
        		"where a.JS_ID=t.JS_ID and t.USER_ID='"+userId+"'";
        List<Map> list = dao.queryForList(sql, null);
        List<Map> list2 = dao.queryForList(sql2, null);
        String roleList = "";
        for(int i=0 ; i< list2.size();i++){
        	roleList = roleList + (String)list.get(i).get("name");
        	if(i!=list2.size()){
        		roleList = roleList + ",";
        	}
        }
        Map<String,String> map = list.get(0);
        map.put("jsId", roleList);
        return map;
    }

    public List<Map> queryUser(Map params) throws OptimusException{
        String loginName =null;
        String userType =null;
        if(params!=null){
            loginName= (String)params.get("loginName");
            userType=(String)params.get("userType");
        }

        IPersistenceDAO dao = getPersistenceDAO();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*, a.JS_MC as zyJs " 
                + "from t_pt_yh t left join t_pt_js a " 
                + "on t.ZY_JS_ID=a.JS_ID where t.yx_bj='1'");
        if(StringUtils.isNotBlank(loginName)){
            sql.append(" and t.LOGIN_NAME = '"+loginName+"'");
        }
        if(StringUtils.isNotBlank(userType)){
            sql.append(" and t.user_type='"+userType+"'");
        }
        sql.append(" order by t.create_time desc");
        List<Map> userList = dao.pageQueryForList(sql.toString(), null);
        return userList;
    }
    
    /**
     * 根据登录名查询用户
     * @param loginName 登录名
     * @return
     * @throws OptimusException
     */
    public 	Map queryUserByLoginName(String loginName) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        Map map=null;
        if(StringUtils.isBlank(loginName))
            return null;
        TPtYhBO yhBO = new TPtYhBO();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from t_pt_yh t where t.login_name='")
            .append(loginName).append("'");
        List<Map> userList = dao.queryForList(sql.toString(), null);
        if(userList!=null && !userList.isEmpty())
        		{
        		map=userList.get(0);
        	
        	}
        return map;
    }
   
    public void updateUser(TPtYhBO yhBO) throws OptimusException{
        // TODO Auto-generated method stub
        IPersistenceDAO dao = getPersistenceDAO();
        dao.update(yhBO);
    }

    /**
     * 删除用户信息（非真正删除，只是将“有效标记”字段置为0）
     * @param userId 用户ID
     * @throws OptimusException
     */
    public void deleteUser(String userId) throws OptimusException{
        if(StringUtils.isBlank(userId))
            return ;
        IPersistenceDAO dao = getPersistenceDAO();
        //删除用户记录
        StringBuffer sqlSbf = new StringBuffer();
        sqlSbf.append("update t_pt_yh set yx_bj='0' where user_id='")
            .append(userId).append("'");
        dao.execute(sqlSbf.toString(), null);
    }
    
    public List<TPtJsBO> queryAuthRole(String userId) throws OptimusException{
    	
    	
        IPersistenceDAO dao = getPersistenceDAO();
       // dao.queryForList(arg0, arg1)
        TPtYhBO yhBO = dao.queryByKey(TPtYhBO.class, userId);
        String zyjsId = yhBO.getZyJsId();
        String sql = "select r.* from t_pt_js r, t_pt_yhjs yj where r.js_id=yj.js_id"
              + " and yj.USER_ID='"+userId+"'";
        List list = dao.queryForList(sql, null);
        for(int i=0,len=list.size();i<len;i++){
            Map map = (Map)list.get(i);
            String jsId = (String)map.get("jsId");
            if(jsId.equals(zyjsId)){
                map.put("zyjs", true);
            }
        }
        return list;
    }
    
    public List queryRole(String userId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        TPtYhBO yhBO = dao.queryByKey(TPtYhBO.class, userId);
        String zyjsId = yhBO.getZyJsId();
        String sql = "select r.js_id as value,r.js_mc as text from t_pt_js r, t_pt_yhjs yj where r.js_id=yj.js_id"
              + " and yj.USER_ID='"+userId+"'";
        List list = dao.queryForList(sql, null);
        for(int i=0,len=list.size();i<len;i++){
            Map map = (Map)list.get(i);
            String jsId = (String)map.get("value");
            if(jsId.equals(zyjsId)){
                map.put("checked", true);
            }
        }
        return list;
    }
    
    public List queryZzjgList(String userId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select code as value,value as text from T_DM_GSGLJGDM";
        List list = dao.queryForList(sql, null);
        return list;
    }
    
    public List queryzzjgCheckList(String userId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        TPtYhBO yhBO = dao.queryByKey(TPtYhBO.class, userId);
        String zyjsId = yhBO.getZjjgid();
        String sql = "select r.code as value,r.value as text from T_DM_GSGLJGDM r";
        List list = dao.queryForList(sql, null);
        for(int i=0,len=list.size();i<len;i++){
            Map map = (Map)list.get(i);
            String jsId = (String)map.get("value");
            if(jsId.equals(zyjsId)){
                map.put("checked", true);
            }
        }
        return list;
    }
    
    public String queryzzjgName(String code) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select distinct r.value as value from T_DM_GSGLJGDM r where r.code =?";
        List<String> list = new ArrayList<String>();
        list.add(code);
        List<Map> result = dao.queryForList(sql, list);
        String resultRetrun="";
        if(result!=null && result.size()>0){
        	for(Map m:result){
        		resultRetrun = (String)m.get("value");
        	}
        }
        return resultRetrun;
    }
    
    public List queryUserRole(String userId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.js_id from t_pt_yhjs t where t.USER_ID='"+userId+"'";
        List userRoleList = dao.queryForList(sql, null);
        return userRoleList;
    }
    
    public List queryZyJs(String userId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.zy_js_id as id from t_pt_yh t where t.USER_ID='"+userId+"'";
        List userRoleList = dao.queryForList(sql, null);
        return userRoleList;
    }
    
    public List<TPtJsBO> queryUnAuthRole(String userId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select r.* from t_pt_js r where r.js_id not in (select " +
        		" js_id from t_pt_yhjs yj where yj.USER_ID='"+userId+"')";
        List list = dao.queryForList(sql, null);
        return list;
    }
    
    public void saveAuthRole(String userId, List<String> roleList) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        if(roleList==null || roleList.isEmpty())
            return;
        List yhjsList = new ArrayList();
        for(int i=0,len=roleList.size();i<len;i++){
            TPtYhjsBO yhjsBO = new TPtYhjsBO();
            yhjsBO.setJsId(roleList.get(i));
            yhjsBO.setUserId(userId);
            yhjsList.add(yhjsBO);
        }
        dao.insert(yhjsList);
    }
    
    public void deleteAuthRole(String userId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        TPtYhBO yhBO = dao.queryByKey(TPtYhBO.class, userId);
        
        String zyjsId = yhBO.getZyJsId();
        String sql = "delete from t_pt_yhjs where USER_ID='"+userId+"'";
        dao.execute(sql, null);
    }
    
    public void setUserChiefRole(String userId, String jsId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        TPtYhBO yhBO = dao.queryByKey(TPtYhBO.class, userId);
        yhBO.setZyJsId(jsId);
        dao.update(yhBO);
    }
    
}
