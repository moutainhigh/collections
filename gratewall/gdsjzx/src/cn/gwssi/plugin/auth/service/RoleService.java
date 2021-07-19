package cn.gwssi.plugin.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gwssi.plugin.auth.model.TPtJsBO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import cn.gwssi.plugin.auth.model.TPtJsgnBO;
import com.gwssi.optimus.util.StringUtil;


@Service
public class RoleService extends BaseService{

	public List queryRoleTree() throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.js_id as id, t.js_mc as name from t_pt_js t";
        List roleList = dao.queryForList(sql, null);
        return roleList;
    }
	
	public List queryRole(Map params) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String jsMc = StringUtil.getMapStr(params, "jsMc");
        String sql = "select * from t_pt_js t";
        if(StringUtils.isNotEmpty(jsMc)){
            sql += " where t.js_mc like '%"+jsMc+"%'";
        }
        List list = dao.pageQueryForList(sql, null);
        return list;
    }
    
    public void saveRole(TPtJsBO jsBO, List<String> funcIds) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        if(StringUtils.isNotEmpty(jsBO.getJsId())){
            dao.update(jsBO);
            String sql = "delete from t_pt_jsgn where js_id='"+jsBO.getJsId()+"'";
            dao.execute(sql, null);
        }else{
            dao.insert(jsBO);
        }
        if(funcIds.isEmpty()){
        	
        }else{
	        String jsId = jsBO.getJsId();
	        List jsgnBOList = new ArrayList();
	        for(String funcId : funcIds){
	            TPtJsgnBO jsgnBO = new TPtJsgnBO();
	            jsgnBO.setJsId(jsId);
	            jsgnBO.setGnId(funcId);
	            jsgnBOList.add(jsgnBO);
	        }
	        dao.insert(jsgnBOList);
        }
    }
    
    public Map getRole(String jsId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
       // TPtJsBO jsBO = dao.queryByKey(TPtJsBO.class, jsId);
       List<Map> jsLM=dao.queryForList("select * from t_pt_js j where j.js_id='"+jsId+"'",null);
       Map jsMap=null;
       if(jsLM.size()>0){
       		jsMap=jsLM.get(0);
       	}
       return jsMap;
    }
    
    public List queryAuthFunc(String jsId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.gn_id from t_pt_jsgn t where t.js_id='"+jsId+"'";
        List authFuncList = dao.queryForList(sql, null);
        return authFuncList;
    }
    
    public void deleteRole(List<TPtJsBO> funcIdList) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        dao.delete(funcIdList);
    }
}
