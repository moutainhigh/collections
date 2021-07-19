package cn.gwssi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class ToolService extends BaseService{
	
	/**
	 * 根据代码和代码表翻译中文
	 * @param code
	 * @param tableName
	 * @return
	 * @throws OptimusException
	 */
	public Map codeToValue(String code,String tableName) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("select value from ").append(tableName).append(" where code=?");
		List<String> params = new ArrayList<String>();
		params.add(code);
		List<Map> list=dao.queryForList(sbf.toString(), params);
		Map returnValue=null;
		if(list!=null&&list.size()>0){
			returnValue=list.get(0);
		}
		return returnValue;
	}
	
	/**
	 * 根据代码和代码表翻译中文
	 * @param code
	 * @param tableName
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> codeToValues(String code,String tableName) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("select value from ").append(tableName).append(" where code=?");
		List<String> params = new ArrayList<String>();
		params.add(code);
		return dao.queryForList(sbf.toString(), params);
	}
	
}
