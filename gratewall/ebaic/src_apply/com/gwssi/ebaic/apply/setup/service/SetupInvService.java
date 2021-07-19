package com.gwssi.ebaic.apply.setup.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;

@Service("setupInvService")
public class SetupInvService {

	/**
	 * 通过gid获取企业类型catId
	 */
	public String getCatId(String gid){
		
		String sql = " select r.cat_id from be_wk_requisition r where r.gid = ? ";
	    String catId = DaoUtil.getInstance().queryForOneString(sql, gid);
	    if(StringUtils.isBlank(catId)){
	    	//数据库数据有问题，暂时去掉
	    	throw new EBaicException("获取catId异常，请联系管理员");
	    }
		return catId;
	}
	
	/**
	 * 通过gid获取entType
	 * @param gid
	 * @return
	 */
	public String getEntType(String gid) {
		
		String sql = "select ent_type from be_wk_ent where gid = ?";
		String entType = DaoUtil.getInstance().queryForOneString(sql, gid);
		
		return entType;
	}
}
