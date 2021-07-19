package com.gwssi.application.integration.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;

/**
 * 
 * 组织机构共享 Service。
 * 
 * 文档：document\yyjc\3.生产过程产品\04.编码开发\04.应用系统\02.应用集成\01.组织机构共享接口\金信工程-应用集成-组织机构共享接口-长城软件-20161109.doc
 * 
 * @author liuhailong2008@foxmail.com
 */
@Service
public class OrgPubService {

	/**
	 * 查询组织机构数据。
	 * 
	 * @param invokerId 调用者编号
	 * @param version
	 * @return
	 * @throws OptimusException 
	 */
	@SuppressWarnings("rawtypes")
	public String getOrgData(String invokerId,int version) throws OptimusException{
		Map<String,Object> ret = new LinkedHashMap<String,Object>();
		// 1. 校验调用者有效性。
		// select * from jc_applicaiton t where t.app_code = ? // invokerId
		
		// 2. 查询数据。
		String sql = "select t.code,t.name,t.parent_code,t.lvl as \"level\",t.type,t.busi_type,t.flag,t.version,t.modify_sign from jc_public_department t where t.version > ? order by t.code asc";
		IPersistenceDAO dao = DAOManager.getPersistenceDAO();
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(version);
		List<Map> dataList = dao.queryForList(sql, paramList);
		if(dataList==null){
			dataList = new ArrayList<Map>();
		}
		int count = dataList.size();
		
		sql = "select nvl(max(t.version),0) as max_version from jc_public_department t ";
		int maxVersion = dao.queryForInt(sql, null);
		
		// 3. 封装范围结果。
		Map<String,Object> requestMap = new LinkedHashMap<String,Object>();
		requestMap.put("invokerId", invokerId);
		requestMap.put("version", version);
		ret.put("request", requestMap);
		ret.put("count", count);
		ret.put("version", maxVersion);
		ret.put("data", dataList);
		String retJSon = JSON.toJSONString(ret, true);
		// 4. 记录调用日志。
		
		return retJSon;
	}
}
