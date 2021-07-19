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
import com.gwssi.datacenter.model.DcStandardDataelementBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;
import com.gwssi.optimus.util.UuidGenerator;

@Service(value = "metadataService")
public class MetadataService extends BaseService{

	/**
	 * 根据标识符、字段名称查询标准元数据
	 * @return 标准元数据集合
	 * @throws OptimusException 
	 */
	public List getMetadata(Map params) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取查询条件
		String identifier = StringUtil.getMapStr(params, "identifier").trim();
		String columnNane = StringUtil.getMapStr(params, "columnNane").trim();
		System.out.println(columnNane);
		
		//编写sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_STANDARD_DATAELEMENT where EFFECTIVE_MARKER=?");
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//当identifier不为空时
		if(StringUtils.isNotEmpty(identifier)){
			sql.append(" and ( lower(IDENTIFIER) like ? or upper(IDENTIFIER) like ? or IDENTIFIER like ?  ) ");
			listParam.add("%"+identifier+"%");
			listParam.add("%"+identifier+"%");
			listParam.add("%"+identifier+"%");
		}
		
		//当columnNane不为空时
		if(StringUtils.isNotEmpty(columnNane)){
			sql.append(" and ( lower(COLUMN_NANE) like ? or upper(COLUMN_NANE) like ? or COLUMN_NANE like ? )  ");
			listParam.add("%"+columnNane+"%");
			listParam.add("%"+columnNane+"%");
			listParam.add("%"+columnNane+"%");
		}
		
		//sql.append(" order by MODIFIER_TIME ");
		sql.append("  order by IDENTIFIER");
		
		//封装结果集
		return dao.pageQueryForList(sql.toString(), listParam);	
	}
	
	/**
	 * 通过标识符查询标准元数据
	 * @return 标准元数据集合
	 * @throws OptimusException 
	 */
	public DcStandardDataelementBO queryMetadataByIdentifier(String identifier) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		DcStandardDataelementBO oldDc = null;
		
		//编写sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_STANDARD_DATAELEMENT where IDENTIFIER=? and EFFECTIVE_MARKER=? ");
		listParam.add(identifier);
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		List list = dao.queryForList(DcStandardDataelementBO.class,sql.toString(), listParam);
		//当list有值时，将查询的记录返回
		if(list!=null&&!list.isEmpty()){
			oldDc = (DcStandardDataelementBO) list.get(0);
		}
		return oldDc;
	}
	
	/**
	 * 保存新增的标准元数据
	 * @throws OptimusException 
	 */
	public void saveMetadataAdd(DcStandardDataelementBO dcStandardDataelementBO) throws OptimusException {
		
       IPersistenceDAO dao = getPersistenceDAO();
		
		//给主键pkDcStandardDataelement赋值
		String pkDcStandardDataelement=UuidGenerator.getUUID();
		dcStandardDataelementBO.setPkDcStandardDataelement(pkDcStandardDataelement);
		
		//添加记录
		dao.insert(dcStandardDataelementBO);
		
	}

	/**
	 * 通过主键，删除标准元数据
	 * @param pkDcStandardDataelement
	 * @throws OptimusException 
	 */
	public void deleteMetadata(String pkDcStandardDataelement) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("update DC_STANDARD_DATAELEMENT set EFFECTIVE_MARKER=? where PK_DC_STANDARD_DATAELEMENT=?");
		listParam.add(AppConstants.EFFECTIVE_N);
		listParam.add(pkDcStandardDataelement);
		
		//执行sql
		dao.execute(sql.toString(), listParam);
		
	}
	
	/**
	 * 通过主键，查询标准元数据
	 * @param pkDcStandardDataelement
	 * @throws OptimusException 
	 */
	public DcStandardDataelementBO getMetadataById(String pkDcStandardDataelement) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//封装结果集
		return dao.queryByKey(DcStandardDataelementBO.class,pkDcStandardDataelement);
	}

	/**
	 * 保存编辑后的标准元数据
	 * @param dcStandardDataelementBO
	 * @throws OptimusException 
	 */
	public void saveUpdate(DcStandardDataelementBO dcStandardDataelementBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(dcStandardDataelementBO);
		
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
