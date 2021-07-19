package com.gwssi.optimus.plugin.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.plugin.auth.model.TPtUrlzyBO;

@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class UrlService extends BaseService{

    /**
     * @param tPtUrlzyBO
     * @throws OptimusException
     */
    public void saveUrl(TPtUrlzyBO tPtUrlzyBO) throws OptimusException{
        // TODO Auto-generated method stub
        IPersistenceDAO dao = getPersistenceDAO();
        dao.insert(tPtUrlzyBO);
    }
    
    /**
     * @param urlId
     * @return
     * @throws OptimusException
     */
    public TPtUrlzyBO getUrl(String urlId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        TPtUrlzyBO tPtUrlzyBO = dao.queryByKey(TPtUrlzyBO.class, urlId);
        return tPtUrlzyBO;
    }

    /**
     * @param urlMc
     * @return
     * @throws OptimusException
     */
    public List<TPtUrlzyBO> queryUrl(String urlMc) throws OptimusException{
        // TODO Auto-generated method stub
        IPersistenceDAO dao = getPersistenceDAO();
        StringBuffer sql = new StringBuffer();
        sql.append("select * from t_pt_urlzy t where LX='2'");
        if(urlMc == null){
            
        }else{
            sql.append(" and t.URL_MC like '"+urlMc+"%'");
        }
        List userList = dao.pageQueryForList(TPtUrlzyBO.class,sql.toString(), null);
        return userList;
    }
    
    /**
     * @param tPtUrlzyBO
     * @throws OptimusException
     */
    public void updateUrl(TPtUrlzyBO tPtUrlzyBO) throws OptimusException{
        // TODO Auto-generated method stub
        IPersistenceDAO dao = getPersistenceDAO();
        dao.update(tPtUrlzyBO);
    }
    
    
    /**
     * @param tPtUrlzyBO
     * @throws OptimusException
     */
    public void deleteUrl(TPtUrlzyBO tPtUrlzyBO) throws OptimusException{
        // TODO Auto-generated method stub
        IPersistenceDAO dao = getPersistenceDAO();
        dao.delete(tPtUrlzyBO);
    }
    
}
