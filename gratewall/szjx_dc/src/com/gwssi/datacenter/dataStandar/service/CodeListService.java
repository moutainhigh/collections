package com.gwssi.datacenter.dataStandar.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.model.DcStandardCodeindexBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;
import com.gwssi.optimus.util.UuidGenerator;

@Service(value = "codeListService")
public class CodeListService extends BaseService{
	
	/**
	 * 根据标识符、代码集名称查询标准代码集
	 * @return 标准代码集集合
	 * @throws OptimusException 
	 */
	public List getCodeSet(Map params) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();

		//获取查询条件
		String standardCodeindex = StringUtil.getMapStr(params, "standardCodeindex").trim();
		String codeindexName = StringUtil.getMapStr(params, "codeindexName").trim();
		
		//编写sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_STANDARD_CODEINDEX where EFFECTIVE_MARKER=?");
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//当identifier不为空时
		if(StringUtils.isNotEmpty(standardCodeindex)){
			sql.append(" and ( lower(STANDARD_CODEINDEX) like ? or upper(STANDARD_CODEINDEX) like ? or STANDARD_CODEINDEX like ?  )");
			listParam.add("%"+standardCodeindex+"%");
			listParam.add("%"+standardCodeindex+"%");
			listParam.add("%"+standardCodeindex+"%");
		}
		
		//当codeindexName不为空时
		if(StringUtils.isNotEmpty(codeindexName)){
			sql.append(" and codeindex_name like ?");
			listParam.add("%"+codeindexName+"%");
		}
		
		//封装结果集
		return dao.pageQueryForList(sql.toString(), listParam);	
	}
	
	/**
	 * 通过标识符查询代码集是否已经存在
	 * @return 标准代码集集合
	 * @throws OptimusException 
	 */
	public DcStandardCodeindexBO queryCodeSetById(String standardCodeindex) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		DcStandardCodeindexBO oldDc = null;
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_STANDARD_CODEINDEX where STANDARD_CODEINDEX=? and EFFECTIVE_MARKER=?");
		listParam.add(standardCodeindex);
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		List list = dao.queryForList(DcStandardCodeindexBO.class, sql.toString(), listParam);
		
		//当list不为空时
		if(list!=null&&!list.isEmpty()){
			oldDc = (DcStandardCodeindexBO) list.get(0);
		}
		
		return oldDc;
	}
	
	/**
	 * 通过标识符查询代码集是否已经存在
	 * @return 标准代码集集合
	 * @throws OptimusException 
	 */
	public DcStandardCodeindexBO queryCodeSetByIdN(String standardCodeindex) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		DcStandardCodeindexBO oldDc = null;
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_STANDARD_CODEINDEX where STANDARD_CODEINDEX=? and EFFECTIVE_MARKER=?");
		listParam.add(standardCodeindex);
		listParam.add(AppConstants.EFFECTIVE_N);
		
		List list = dao.queryForList(DcStandardCodeindexBO.class, sql.toString(), listParam);
		
		//当list不为空时
		if(list!=null&&!list.isEmpty()){
			oldDc = (DcStandardCodeindexBO) list.get(0);
		}
		
		return oldDc;
	}
	
	/**
	 * 保存代码集
	 * @param dcStandarCodeindexBO
	 * @throws OptimusException 
	 */
	public void saveCodeSetAdd(DcStandardCodeindexBO dcStandardCodeindexBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(dcStandardCodeindexBO);
		
	}
	
	/**
	 * 通过主键，删除标准代码集
	 * @param standardCodeindex
	 * @throws OptimusException 
	 */
	public void deleteCodeSet(String standardCodeindex) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("update DC_STANDARD_CODEINDEX set EFFECTIVE_MARKER=? where STANDARD_CODEINDEX=?");
		listParam.add(AppConstants.EFFECTIVE_N);
		listParam.add(standardCodeindex);
		
		//执行sql
		dao.execute(sql.toString(), listParam);
		
		
	}
	
	/**
	 * 通过主键，删除标准代码集中所有的相关的代码值
	 * @param standardCodeindex
	 * @throws OptimusException 
	 */
	public void deleteCodeData(String standardCodeindex) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("delete from DC_STANDARD_CODEDATA where STANDARD_CODEINDEX=?");
		listParam.add(standardCodeindex);
		
		//执行sql
		dao.execute(sql.toString(), listParam);
		
		
	}

	
	/**
	 * 通过主键，查询标准代码集
	 * @param standardCodeindex
	 * @throws OptimusException 
	 */
	public DcStandardCodeindexBO getCodeSetById(String standardCodeindex) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//封装结果集
		return dao.queryByKey(DcStandardCodeindexBO.class, standardCodeindex);
	}
	
	/**
	 * 把字符串时间转换为Calendar类型
	 * 
	 * @param str
	 * @return
	 */
	public Calendar changeStringToCalendar(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;

	}
	
	/**
	 * 保存编辑后的标准代码集
	 * @param dcStandardCodeindexBO
	 * @throws OptimusException 
	 */
	public void saveCodeSetUpdate(DcStandardCodeindexBO dcStandardCodeindexBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(dcStandardCodeindexBO);
		
	}
	
	/**
	 * 通过代码集标识查询代码值
	 * @param standardCodeindex
	 * @return 代码值集合
	 * @throws OptimusException
	 */
	public List getCodeDataById(String standardCodeindex) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_STANDARD_CODEDATA where STANDARD_CODEINDEX=?");
		listParam.add(standardCodeindex);
		
		return dao.queryForList(sql.toString(), listParam);
	}
	
	/**
	 * 修改已经删除的记录
	 * @param dcStandardCodeindexBO
	 * @throws OptimusException
	 */
	public void updateCodeSetAdd(DcStandardCodeindexBO dcStandardCodeindexBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取数据
		String standardCodeindex = dcStandardCodeindexBO.getStandardCodeindex();
		String standardType = dcStandardCodeindexBO.getStandardType();
		String codeindexName = dcStandardCodeindexBO.getCodeindexName();
		String representation = dcStandardCodeindexBO.getRepresentation();
		String version = dcStandardCodeindexBO.getVersion();
		String codingMethods = dcStandardCodeindexBO.getCodingMethods();
		String illustrate = dcStandardCodeindexBO.getIllustrate();
		String remarks = dcStandardCodeindexBO.getRemarks();
		String createrName = dcStandardCodeindexBO.getCreaterName();
		String createrId = dcStandardCodeindexBO.getCreaterId();
		Calendar createrTime = dcStandardCodeindexBO.getCreaterTime();
		String modifierName = dcStandardCodeindexBO.getModifierName();
		String modifierId = dcStandardCodeindexBO.getModifierId();
		Calendar modifierTime = dcStandardCodeindexBO.getModifierTime();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("update DC_STANDARD_CODEINDEX set CODEINDEX_NAME=?,STANDARD_TYPE=?,REPRESENTATION=?,CODING_METHODS=?,ILLUSTRATE=?, ")
			.append(" VERSION=?,REMARKS=?,EFFECTIVE_MARKER=?,CREATER_ID=?,CREATER_NAME=?,CREATER_TIME=?,MODIFIER_ID=?,MODIFIER_NAME=?,MODIFIER_TIME=?  where STANDARD_CODEINDEX=?");
		listParam.add(codeindexName);
		listParam.add(standardType);
		listParam.add(representation);
		listParam.add(codingMethods);
		listParam.add(illustrate);
		listParam.add(version);
		listParam.add(remarks);
		listParam.add(AppConstants.EFFECTIVE_Y);
		listParam.add(createrId);
		listParam.add(createrName);
		listParam.add(createrTime);
		listParam.add(modifierId);
		listParam.add(modifierName);
		listParam.add(modifierTime);
		listParam.add(standardCodeindex);
		
		//执行sql
		dao.execute(sql.toString(), listParam);
		
		
	}
	
	/**
	 * 查询标准规范
	 * @return
	 * @throws OptimusException
	 */
	public List queryStandardSpec() throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select PK_DC_STANDARD_SPEC as value,STANDARD_NAME as text from DC_STANDARD_SPEC where EFFECTIVE_MARKER=?");
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		return dao.queryForList(sql.toString(), listParam);
	}

	

}
