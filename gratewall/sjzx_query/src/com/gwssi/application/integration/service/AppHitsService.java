package com.gwssi.application.integration.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;


@Service
public class AppHitsService<T> extends BaseService {

	
	/*public List getApplist(String param) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		//String sql = "select * from  (select distinct user_id,operation_describe as des,count(operation_describe) as 次数  from v_log_operation group by user_id,operation_describe order by operation_describe,次数 desc) where rownum <= 10";
		
		
		StringBuffer bf = new StringBuffer();
		bf.append("select distinct user_id,operation_describe,count(operation_describe) as hits from log_operation b ");
		bf.append("   group by user_id, operation_describe ");
		bf.append("   having operation_describe = ?  ");
		bf.append("   order by operation_describe, hits   desc ");
		
		
		List params = new ArrayList<T>();
		params.add(param);
		List list  =  dao.pageQueryForList(bf.toString(), params);
		System.out.println(list);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	*/
	
	
	
	
	
	@SuppressWarnings("rawtypes")
	public List getApplistView() throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		//String sql = "select * from  (select distinct user_id,operation_describe as des,count(operation_describe) as 次数  from v_log_operation group by user_id,operation_describe order by operation_describe,次数 desc) where rownum <= 10";
		List params = new ArrayList<T>();
		String sql = "select * from v_tm_fontpage order by hits desc,operation_describe";
		List list  =  dao.queryForList(sql, null);
		System.out.println(list);
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
}
