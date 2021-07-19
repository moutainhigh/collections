package com.gwssi.cxf.feedback.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gwssi.cxf.validatecode.service.ValidateCodeService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service(value="feedBackInfoService")
public class FeedBackInfoService extends BaseService {
	
	/**
	 * 配置数据源
	 */
	private static final String DATASOURS = "dc_dc";
	
	private static final Logger LOG = LoggerFactory.getLogger(FeedBackInfoService.class);

	/**
	 * 根据时间戳获取房屋反馈信息
	 * @throws OptimusException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getHouseInfo(String startTime, String endTime) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dc_house_feedback t where t.flag = '0' and t.send_flag = '0'");
		if (StringUtils.isNotEmpty(startTime)) {
			sql.append(" and t.updatetime >= to_date(?, 'yyyy-MM-dd')");
			params.add(startTime);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			sql.append(" and t.updatetime <= to_date(?, 'yyyy-MM-dd')");
			params.add(endTime);
		}
		LOG.info("根据时间戳获取房屋反馈信息sql："+sql.toString()+"，参数"+params);
		List<Map> list = dao.queryForList(sql.toString(), params);
		return list;
		
	}

	/**
	 * 反馈状态更改
	 * @throws OptimusException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateFeedFlag(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("update dc_house_feedback set send_flag = '1' where 1=1");
		if (StringUtils.isNotEmpty(id)) {
			sql.append(" and id = ?");
			params.add(id);
			dao.execute(sql.toString(), params);
			dao.execute("commit", null);
		}
	}

	/**
	 * 根据企业业务id精确查找房屋反馈信息
	 * @throws OptimusException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getHouseInfoById(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dc_house_feedback t where t.flag = '0' and t.send_flag = '0'");
		if (StringUtils.isNotEmpty(id)) {
			sql.append(" and t.id = ?");
			params.add(id);
			LOG.info("根据ID查询sql："+sql.toString()+"，参数："+id);
			List<Map> list = dao.queryForList(sql.toString(), params);
			return list;
		}
		return null;
	}

}
