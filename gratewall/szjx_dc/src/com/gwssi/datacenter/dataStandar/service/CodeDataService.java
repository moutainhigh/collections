package com.gwssi.datacenter.dataStandar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.datacenter.model.DcStandarCodeindexBO;
import com.gwssi.datacenter.model.DcStandardCodedataBO;
import com.gwssi.datacenter.model.DcStandardCodeindexBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;
import com.gwssi.optimus.util.UuidGenerator;

@Service(value = "codeDataService")
public class CodeDataService extends BaseService{
	
	/**
	 * 通过主键，查询标准代码集
	 * @param pkDcStandarCodeindex
	 * @throws OptimusException 
	 */
	public DcStandardCodeindexBO getCodeSetById(String standardCodeindex) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		return dao.queryByKey(DcStandardCodeindexBO.class, standardCodeindex);
	}

	/**
	 * 通过代码值、代码内容，查询标准代码
	 * @param pkDcStandarCodeindex
	 * @throws OptimusException 
	 */
	public List getCodeData(Map params, String standardCodeindex) throws OptimusException {
		
		//获取formpanel中填写的数据
		String codeindexCode = StringUtil.getMapStr(params, "codeindexCode").trim();
		String codeindexValue = StringUtil.getMapStr(params, "codeindexValue").trim();
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_STANDARD_CODEDATA where STANDARD_CODEINDEX=?");
		listParam.add(standardCodeindex);
		
		//当codeindexCode不为空时
		if(StringUtils.isNotEmpty(codeindexCode)){
			sql.append(" and CODEINDEX_CODE like ?");
			listParam.add("%"+codeindexCode+"%");
		}
		//当codeindexValue不为空时
		if(StringUtils.isNotEmpty(codeindexValue)){
			sql.append(" and CODEINDEX_VALUE like ?");
			listParam.add("%"+codeindexValue+"%");
		}
		return dao.pageQueryForList(sql.toString(), listParam);
	}
	
	/**
	 * 通过标准代码集的主键、代码值查询该条记录是否已存在
	 * @param codeindexCode
	 * @param pkDcStandarCodeindex
	 * @throws OptimusException 
	 */
	public DcStandardCodedataBO queryCodeData(String standardCodeindex,String codeindexCode ) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		
		DcStandardCodedataBO oldDc = null;
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_STANDARD_CODEDATA where STANDARD_CODEINDEX=? and CODEINDEX_CODE=?");
		listParam.add(standardCodeindex);
		listParam.add(codeindexCode);
		
		//封装结果集
		List list = dao.queryForList(DcStandardCodedataBO.class,sql.toString(), listParam);
		
		//当list不为空时
		if(list!=null&&!list.isEmpty()){
			oldDc = (DcStandardCodedataBO) list.get(0);
			
		}
		return oldDc;
	}

	/**
	 * 新增标准代码
	 * @param dcStandardCodedataBO
	 * @throws OptimusException 
	 */
	public void saveCodeDataAdd(DcStandardCodedataBO dcStandardCodedataBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		dao.insert(dcStandardCodedataBO);	
		
	}
	
	/**
	 * 删除标准代码
	 * @param pkDcStandardCodedata
	 * @throws OptimusException 
	 */
	public void deleteCodeData(String standardCodeindex,String codeindexCode) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("delete from DC_STANDARD_CODEDATA where STANDARD_CODEINDEX=? and CODEINDEX_CODE=?");
		listParam.add(standardCodeindex);
		listParam.add(codeindexCode);
		
		//执行sql
		dao.execute(sql.toString(), listParam);
		
	}
	
	/**
	 * 查询该代码值是否存在
	 * @param standardCodeindex
	 * @param codeindexCode
	 * @return
	 * @throws OptimusException
	 */
	public DcStandardCodedataBO getCodeDataById(String standardCodeindex,String codeindexCode) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		DcStandardCodedataBO DcBO = null;
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_STANDARD_CODEDATA where STANDARD_CODEINDEX=? and CODEINDEX_CODE=?");
		listParam.add(standardCodeindex);
		listParam.add(codeindexCode);
		
		//封装结果集
		List list = dao.queryForList(DcStandardCodedataBO.class,sql.toString(), listParam);
		
		//当list不为空时
		if(list!=null&&!list.isEmpty()){
			DcBO = (DcStandardCodedataBO) list.get(0);
			
		}
		return DcBO;
		
		
		
		
		
	}
	
	/**
	 * 保存修改的代码值
	 * @param dcStandardCodedataBO
	 * @throws OptimusException
	 */
	public void saveCodeDataUpdate(DcStandardCodedataBO dcStandardCodedataBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		String standardCodeindex = dcStandardCodedataBO.getStandardCodeindex();
		String codeindexCode = dcStandardCodedataBO.getCodeindexCode();
		String codeindexValue = dcStandardCodedataBO.getCodeindexValue();
		String illustrate = dcStandardCodedataBO.getIllustrate();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("update DC_STANDARD_CODEDATA set CODEINDEX_VALUE=?,ILLUSTRATE=? where STANDARD_CODEINDEX=? and CODEINDEX_CODE=?");
		listParam.add(codeindexValue);
		listParam.add(illustrate);
		listParam.add(standardCodeindex);
		listParam.add(codeindexCode);
		
		dao.execute(sql.toString(), listParam);
	}

	
}
