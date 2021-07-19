package cn.gwssi.analysis.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;


@Service
public class SCZTAnalysisService extends BaseService{

	public List<Map> getScztGK(String jjxz, String qylx, String zcxs, String cydl,
			String cy, String zjgm) {
			IPersistenceDAO dao=this.getPersistenceDAO("iqDataSource");
			StringBuffer buffer=new StringBuffer();
			buffer.append("  ");
			
			
		
		
		return null;
		
	}

}
