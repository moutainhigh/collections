package cn.gwssi.plugin.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import cn.gwssi.plugin.auth.model.TPtUrlzyBO;

@Service
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
    public Map getUrl(String urlId) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        //TPtUrlzyBO tPtUrlzyBO = dao.queryByKey(TPtUrlzyBO.class,urlId);
        List params=new ArrayList();
        params.add(urlId);
        List<Map> tPtUrlzyList=dao.queryForList("select * from T_PT_URLZY where URL_ID=?", params);
        Map tPtUrlzyMap=tPtUrlzyList.get(0);
        return tPtUrlzyMap;
    }

    /**
     * @param urlMc
     * @return
     * @throws OptimusException
     */
    public List<Map> queryUrl(String urlMc) throws OptimusException{
        // TODO Auto-generated method stub
        IPersistenceDAO dao = getPersistenceDAO();
        StringBuffer sql = new StringBuffer();
        sql.append("select * from t_pt_urlzy t where t.LX='2'");
        if(urlMc == null){
            
        }else{
            sql.append(" and t.URL_MC like '"+urlMc+"%'");
        }
        List userList = dao.pageQueryForList(sql.toString(), null);
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
