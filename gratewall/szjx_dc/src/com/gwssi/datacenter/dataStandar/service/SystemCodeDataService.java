package com.gwssi.datacenter.dataStandar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.datacenter.model.DcDmJcdmFxBO;
import com.gwssi.datacenter.model.DcStandarCodeindexBO;
import com.gwssi.datacenter.model.DcStandardCodedataBO;
import com.gwssi.datacenter.model.DcStandardCodeindexBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;
import com.gwssi.optimus.util.UuidGenerator;

@Service(value = "systemCodeDataService")
public class SystemCodeDataService extends BaseService{

	/**
	 * 获取代码级风项
	 * @param params
	 * @param dcDmId
	 * @return
	 * @throws OptimusException
	 */
	public List getCodeData(Map params, String dcDmId) throws OptimusException {
		
		//获取formpanel中填写的数据
		String dcSjfxDm = StringUtil.getMapStr(params, "dcSjfxDm").trim();
		String dcSjfxMc = StringUtil.getMapStr(params, "dcSjfxMc").trim();
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dc_dm_jcdm_fx  where dc_dm_id = ? ");
		listParam.add(dcDmId);
		
		//当codeindexCode不为空时
		if(StringUtils.isNotEmpty(dcSjfxDm)){
			sql.append(" and dc_sjfx_id like ?");
			listParam.add("%"+dcSjfxDm+"%");
		}
		//当codeindexValue不为空时
		if(StringUtils.isNotEmpty(dcSjfxMc)){
			sql.append(" and dc_sjfx_mc like ?");
			listParam.add("%"+dcSjfxMc+"%");
		}
		return dao.pageQueryForList(sql.toString(), listParam);
	}

	
	public DcDmJcdmFxBO queryCodeData(String dcDmId, String sjfxDm) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		
		DcDmJcdmFxBO oldDc = null;
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dc_dm_jcdm_fx where dc_sjfx_dm=? and dc_dm_id=?");
		listParam.add(sjfxDm);
		listParam.add(dcDmId);
		
		//封装结果集
		List list = dao.queryForList(DcDmJcdmFxBO.class,sql.toString(), listParam);
		
		//当list不为空时
		if(list!=null&&!list.isEmpty()){
			oldDc = (DcDmJcdmFxBO) list.get(0);
			
		}
		return oldDc;
	}


	public void dosaveCodeDataAdd(DcDmJcdmFxBO bo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(bo);
		
	}


	public DcDmJcdmFxBO dogetCodeDataById(String dcSjfxId) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		return	dao.queryByKey(DcDmJcdmFxBO.class, dcSjfxId);
	}

	/**
	 *  更新
	 * @param bo
	 * @throws OptimusException
	 */
	public void dosaveCodeDataUpdate(DcDmJcdmFxBO bo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(bo);
	}


	public void dodeleteCodeData(String dcSjfxId) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		dao.deleteByKey(DcDmJcdmFxBO.class, dcSjfxId);
	}
	
	
	
}
