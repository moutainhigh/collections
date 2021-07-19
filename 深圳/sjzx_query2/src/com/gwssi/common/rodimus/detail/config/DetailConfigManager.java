package com.gwssi.common.rodimus.detail.config;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.gwssi.common.rodimus.report.entity.ReportDetailConfig;
import com.gwssi.common.rodimus.report.util.DaoUtil;
import com.gwssi.optimus.core.service.BaseService;

public class DetailConfigManager extends BaseService {
	
	
	public static JSONObject getConfig(String configCode){
		// 获得配置信息
		JSONObject ret = getConfigFromDatabase(configCode);
		if(ret==null){
			throw new RuntimeException(String.format("没有获取到对应的配置信息，编号：%s。",configCode));
		}
		return ret;
	}
	
	protected static JSONObject getConfigFromDatabase(String configCode) {
		String sql = "select * from rpt_detail_config c where c.code = ? ";
		List<ReportDetailConfig> listBo = DaoUtil.getDefaultInstance().queryForListBo(sql,ReportDetailConfig.class,configCode);
		if(listBo == null){
			throw new RuntimeException(String.format("数据库中获取数据出错"));
		}
		String reportDetailConfig = listBo.get(0).getConfig();
		JSONObject ret = JSONObject.parseObject(reportDetailConfig);
		return ret;
	}
	
}
