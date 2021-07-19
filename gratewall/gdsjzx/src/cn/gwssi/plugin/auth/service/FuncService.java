package cn.gwssi.plugin.auth.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import cn.gwssi.plugin.auth.model.TPtGnBO;
import cn.gwssi.plugin.auth.model.TPtUrlzyBO;
import cn.gwssi.plugin.auth.model.TPtZjzyBO;


@Service(value = "funcService")
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
        List funcList = dao.queryForList(sql, null);
        return funcList;
    }
    
    public void deleteFunc(List<String> funcIdList) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        dao.deleteByKey(TPtGnBO.class, funcIdList);
    }
    
    public List<Map> getFuncById(String gnId) throws OptimusException{
        if(StringUtils.isEmpty(gnId))
            return null;
        IPersistenceDAO dao = getPersistenceDAO();
      //  TPtGnBO gnBO = dao.queryByKey(TPtGnBO.class, gnId);
        String sql="SELECT * FROM T_PT_GN WHERE gn_ID='"+gnId+"'";
        List<Map> queryfun=dao.queryForList(sql, null);
        return queryfun;
    }
    
    public String saveFunc(TPtGnBO gnBO) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        if(StringUtils.isNotEmpty(gnBO.getGnId())){
            if("0".equals(gnBO.getSfcd())){
            	String sql = "SELECT GN_ID FROM t_pt_gn WHERE SFCD = '0' AND GN_MC<>'" 
	            	    + gnBO.getGnMc() + "'  and SJGN_ID='" + gnBO.getSjgnId() 
	            	    + "' and SJGN_ID = GN_ID";
            	int num = dao.queryForInt(sql, null);
            	if(num>0){
            		return "fail";
            	}
            }else if("1".equals(gnBO.getSfcd())){
            	String sql = "SELECT GN_ID FROM t_pt_gn WHERE SFCD = '0' AND GN_MC<>'" 
	            	    + gnBO.getGnMc() + "'  and SJGN_ID='" + gnBO.getSjgnId() 
	            	    + "' and SJGN_ID = GN_ID";
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
	            	    + gnBO.getGnMc() + "'  and SJGN_ID='" + gnBO.getSjgnId() 
	            	    + "' and SJGN_ID = GN_ID";
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
    
    public List<Map> queryFuncUrl(String gnId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select * from t_pt_urlzy t where t.gn_id='"+gnId+"'";
        List<Map> urlList = dao.queryForList(sql, null);
        return urlList;
    }
    
    public List getFuncUrl(String urlId) throws OptimusException{
        if(StringUtils.isEmpty(urlId))
            return null;
        IPersistenceDAO dao = getPersistenceDAO();
        String sql="select * from t_pt_urlzy where url_id = "+urlId;
     	List funcUrl=dao.queryForList(sql, null);
        return funcUrl;
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
        List zjlList = dao.queryForList(sql, null);
        return zjlList;
    }
    
    public String saveFuncZj(TPtZjzyBO zjBO) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.zj_code from t_pt_zjzy t";
        List zjCodeList = dao.queryForList(sql, null);
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
    
    public List getFuncZj(String zjId) throws OptimusException{
        if(StringUtils.isEmpty(zjId))
            return null;
        IPersistenceDAO dao = getPersistenceDAO();
        String sql="select zj_id,gn_id,zj_mc,zj_code from t_pt_zjzy where zj_id="+zjId;
        List funcZj=dao.queryForList(sql, null);
        return funcZj;
    }
    
    public void deleteZj(List list) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        dao.delete(list);
    }
}
