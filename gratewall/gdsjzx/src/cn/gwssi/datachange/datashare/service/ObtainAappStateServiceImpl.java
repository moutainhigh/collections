package cn.gwssi.datachange.datashare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class ObtainAappStateServiceImpl extends BaseService{

	/**
	 * 查找具有服务的对象，并测试该对象应用是否启用（从服务中找到服务对象信息） 
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectServiceObject(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("select  * from t_pt_fwdxjbxx a where fwdxjbid in (select serviceobjectid from t_pt_fwjbxx where serviceobjectid is not null and serviceobjectid<>'')");
		return dao.queryForList(sbf.toString(), null);
	}
	
	/**
	 * 更新服务运行状态 
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public void updateServiceObject(String fwdxjbid) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("update t_pt_fwjbxx set serviceRunState=? where serviceobjectid=?");
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add(fwdxjbid);
		dao.execute(sbf.toString(), list);
	}
}
