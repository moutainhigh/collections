package com.gwssi.query.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class HouseAddService extends BaseService {
	private static final String DATASOURS = "dc_dc";

	@Autowired  
    private RedisTemplate redisTemplate; 
	
	
	public List getHouseListFromDB(String keyWord) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List params = new ArrayList();
		List result = new ArrayList();

		if (keyWord != null) {
			params.add(keyWord);
		}
		String sql = " select *  from DC_ADDRESS_VIEW t where instr(t.HOUSE_ADD,?,1,1)<>0  and  rownum<=4";
		result = dao.queryForList(sql, params);
		if (result != null && result.size() > 0) {
			return result;
		}
		return null;
	}

	
	//http://blog.csdn.net/liang_love_java/article/details/50497281
	//http://blog.csdn.net/fengyao1995/article/details/72794899
	public List getHouseListFromRedis(String keyWord) {
		
	     	return null;

	}
	//http://blog.csdn.net/liang_love_java/article/details/50497281
	//http://blog.csdn.net/fengyao1995/article/details/72794899
	//http://blog.csdn.net/fengzheku/article/details/49735785
	//http://blog.csdn.net/liang_love_java/article/details/50497281
	//https://www.cnblogs.com/aoeiuv/p/6760742.html
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getHouseList(String keyWord) throws OptimusException {
		ListOperations<String, Object> listOperations  = redisTemplate.opsForList();  ;
		List list = listOperations.range("list", 0, 1);
		if(list==null){
			list =  getHouseListFromDB(keyWord);
			listOperations.rightPush("list", list);
		}
		return list;

	}

}
