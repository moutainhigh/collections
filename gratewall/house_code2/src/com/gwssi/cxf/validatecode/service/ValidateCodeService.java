package com.gwssi.cxf.validatecode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service(value = "validateCodeService")
public class ValidateCodeService extends BaseService {
	
	/**
	 * 配置数据源
	 */
	private static final String DATASOURS = "dc_dc";
	
	private static final Logger LOG = LoggerFactory.getLogger(ValidateCodeService.class);
	
	@SuppressWarnings("rawtypes")
	public List<Map> getAddrInfo(String houseid) {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List<String> params = new ArrayList<String>();
		String sql = "select t.house_add from dc_house_housenum t where t.house_num = ?";
		params.add(houseid);
		LOG.info("sql："+sql+";参数："+params);
		
		try {
			List<Map> queryForList = dao.queryForList(sql, params);
			LOG.info("查询结果："+queryForList);
			return queryForList;
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return null;
	}

}
