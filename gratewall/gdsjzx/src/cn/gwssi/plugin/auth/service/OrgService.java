package cn.gwssi.plugin.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service(value = "orgService")
public class OrgService extends BaseService  {
    
    public List queryOrgTree() throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.xz_code as id, '440000' as pid, t.xz_value as name " +
        		"from T_COGNOS_XZQHDM t where t.xz_code <>'440000' order by t.xz_code";
        List orgList = dao.queryForList(sql, null);
        return orgList;
    }
    
}
