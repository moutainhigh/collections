package cn.gwssi.trs.caseinfoentity.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.gwssi.resource.Conts;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class CaseInfoService extends BaseService{

	public Map<String, Object> selectCaseInfoById(String caseid) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer(Conts.CASEINFO);
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotBlank(caseid)){
			sbf.append(" where a.caseid = ? ");
			list.add(caseid);
		}
		List<Map> result = dao.queryForList(sbf.toString(), list);
		Map<String, Object> resultMap= new HashMap<String, Object>();
		if(result!=null && result.size()>0){
			resultMap = result.get(0);
		}
		System.out.println(resultMap.toString());
		return resultMap;
	}

	public Map<String, Object> selectCaseInfoShowTab() throws OptimusException{
		Map map=new LinkedHashMap();
		IPersistenceDAO dao= this.getPersistenceDAO();
		String sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '210' order by sort";
		List<Map> datalist=dao.queryForList(sql.toString(), null);
		for(int i=0;i<datalist.size();i++){
			Map<String,String> m=datalist.get(i);
			map.put(m.get("fieldcn"), m.get("fieldeng"));
		}
		return map;
	}
}
