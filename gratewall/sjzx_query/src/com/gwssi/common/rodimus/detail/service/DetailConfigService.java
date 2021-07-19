package com.gwssi.common.rodimus.detail.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.gwssi.common.rodimus.report.entity.ReportDetailConfig;
import com.gwssi.common.rodimus.report.util.DaoUtil;
import com.gwssi.optimus.core.service.BaseService;


@Service("DetailConfigService")
public class DetailConfigService extends BaseService {
	

	
	protected static JSONObject getConfigFromDatabase(String configCode) {
		String sql = "select * from rpt_detail_config c where c.code = ? ";
		List<Map<String,Object>> listBo = DaoUtil.getDefaultInstance().queryForList(sql,configCode);
		//if(listBo)
		String reportDetailConfig = (String) listBo.get(0).get("config");
		JSONObject ret = JSONObject.parseObject(reportDetailConfig);
		return ret;
	}
	
}
