package com.gwssi.optimus.plugin.auth.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.plugin.auth.model.TPtGnBO;
import com.gwssi.optimus.plugin.auth.model.TPtUrlzyBO;
import com.gwssi.optimus.plugin.auth.model.TPtZjzyBO;


@Service(value = "funcService")
@SuppressWarnings({"rawtypes"})
public class FuncService extends BaseService  {
    
    public List queryFuncTree() throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.gn_id as id, t.sjgn_id as pid, t.gn_mc as name " +
        		"from t_pt_gn t order by t.xh";
        List funcList = dao.queryForList(sql, null);
        return funcList;
    }
    
    public List queryChildFunc(String sjgnId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select * from t_pt_gn t where t.sjgn_id='"+sjgnId+"'";
        List funcList = dao.pageQueryForList(sql, null);
        return funcList;
    }
    
    public void deleteFunc(List<String> funcIdList) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        dao.deleteByKey(TPtGnBO.class, funcIdList);
    }
    
    public TPtGnBO getFuncById(String gnId) throws OptimusException{
        if(StringUtils.isEmpty(gnId))
            return null;
        IPersistenceDAO dao = getPersistenceDAO();
        TPtGnBO gnBO = dao.queryByKey(TPtGnBO.class, gnId);
        return gnBO;
    }
    
    public String saveFunc(TPtGnBO gnBO) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        if(StringUtils.isNotEmpty(gnBO.getGnId())){
            if("0".equals(gnBO.getSfcd())){
            	String sql = "SELECT count(GN_ID) FROM t_pt_gn WHERE SFCD = '1' AND GN_ID<>'" 
            	    + gnBO.getGnId() + "' START WITH GN_MC='" + gnBO.getGnMc() 
            	    + "' CONNECT BY PRIOR GN_ID = SJGN_ID ";
            	int num = dao.queryForInt(sql, null);
            	if(num>0){
            		return "fail";
            	}
            }else if("1".equals(gnBO.getSfcd())){
            	String sql = "SELECT GN_ID FROM t_pt_gn WHERE SFCD = '0' AND GN_ID<>'" 
            	    + gnBO.getGnId() + "' START WITH GN_MC='" + gnBO.getGnMc() 
            	    + "' CONNECT BY PRIOR SJGN_ID = GN_ID";
            	List<Map> list = dao.queryForList(sql, null);
            	if(list.isEmpty()){
            		
            	}else{
            		StringBuffer localSql = new StringBuffer("UPDATE t_pt_gn SET SFCD='1'" +
            				" WHERE GN_ID IN (");
            		for(int i = 0;i<list.size();i++){
            			String gnId = (String) list.get(i).get("gnId");
            			if(i!=list.size()-1){
            				localSql.append("'" + gnId + "',");
            			}else{
            				localSql.append("'" + gnId + "'");
            			}
            		}
            		localSql.append(")");
            		dao.execute(localSql.toString(), null);
            	}
            }
        	dao.update(gnBO);
        	return "success";
        }else{
            if(StringUtils.isEmpty(gnBO.getSjgnId())){
                gnBO.setSjgnId("0");
            }else{
	            if("1".equals(gnBO.getSfcd())){
	            	String sql = "SELECT GN_ID FROM t_pt_gn WHERE SFCD = '0' AND GN_MC<>'" 
	            	    + gnBO.getGnMc() + "' START WITH GN_ID='" + gnBO.getSjgnId() 
	            	    + "' CONNECT BY PRIOR SJGN_ID = GN_ID";
	            	List<Map> list = dao.queryForList(sql, null);
	            	if(list.isEmpty()){
	            		
	            	}else{
	            		StringBuffer localSql = new StringBuffer("UPDATE t_pt_gn SET SFCD='1' " +
	            				"WHERE GN_ID IN (");
	            		for(int i = 0;i<list.size();i++){
	            			String gnId = (String) list.get(i).get("gnId");
	            			if(i!=list.size()-1){
	            				localSql.append("'" + gnId + "',");
	            			}else{
	            				localSql.append("'" + gnId + "'");
	            			}
	            		}
	            		localSql.append(")");
	            		dao.execute(localSql.toString(), null);
	            	}
	            }
            }
            dao.insert(gnBO);
            return "success";
        }
    }
    
    public List queryFuncUrl(String gnId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select * from t_pt_urlzy t where t.gn_id='"+gnId+"'";
        List urlList = dao.pageQueryForList(sql, null);
        return urlList;
    }
    
    public TPtUrlzyBO getFuncUrl(String urlId) throws OptimusException{
        if(StringUtils.isEmpty(urlId))
            return null;
        IPersistenceDAO dao = getPersistenceDAO();
        TPtUrlzyBO urlBO = dao.queryByKey(TPtUrlzyBO.class, urlId);
        return urlBO;
    }
    
    public void saveFuncUrl(TPtUrlzyBO urlBO) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        if(StringUtils.isNotEmpty(urlBO.getUrlId())){
            dao.update(urlBO);
        }else{
            dao.insert(urlBO);
        }
    }
    
    public void deleteUrl(List urlList) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        dao.delete(urlList);
    }
    
    public List queryFuncZj(String gnId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select * from t_pt_zjzy t where t.gn_id='"+gnId+"'";
        List zjlList = dao.pageQueryForList(sql, null);
        return zjlList;
    }
    
    public String saveFuncZj(TPtZjzyBO zjBO) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.zj_code from t_pt_zjzy t";
        List zjCodeList = dao.pageQueryForList(sql, null);
        if(zjCodeList!=null&&zjCodeList.size()>0){
        	for(int i = 0 ; i<zjCodeList.size();i++){
        		Map map = (Map)zjCodeList.get(i);
        		String code = (String)map.get("zjCode");
        		if(StringUtils.isNotEmpty(zjBO.getZjCode())&&zjBO.getZjCode().equals(code)){
        			return "fail";
        		}
        	}
        }
        if(StringUtils.isNotEmpty(zjBO.getZjId())){
            dao.update(zjBO);
        }else{
            dao.insert(zjBO);
        }
        return "success";
    }
    
    public TPtZjzyBO getFuncZj(String zjId) throws OptimusException{
        if(StringUtils.isEmpty(zjId))
            return null;
        IPersistenceDAO dao = getPersistenceDAO();
        TPtZjzyBO zjBO = dao.queryByKey(TPtZjzyBO.class, zjId);
        return zjBO;
    }
    
    public void deleteZj(List list) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        dao.delete(list);
    }
}
