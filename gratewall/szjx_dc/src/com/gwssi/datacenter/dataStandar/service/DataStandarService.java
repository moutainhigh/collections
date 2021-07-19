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
import com.gwssi.datacenter.model.DcStandardSpecBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;

@Service(value = "dataStandarService")
public class DataStandarService extends BaseService{
	
	
	/**
	 * 通过规范发布名称、单位查询标准规范记录
	 * @param map
	 * @throws OptimusException
	 */
	public List getDataStandar(Map map) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取数据
		String standardName = StringUtil.getMapStr(map, "standardName").trim();
		String standardCom = StringUtil.getMapStr(map, "standardCom").trim();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_STANDARD_SPEC where EFFECTIVE_MARKER=?");
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//当规范发布名称不为空时
		if(StringUtils.isNotEmpty(standardName)){
			sql.append(" and STANDARD_NAME like ?");
			listParam.add("%"+standardName+"%");
		}
		
		//当规范发布单位不为空时
		if(StringUtils.isNotEmpty(standardCom)){
			sql.append(" and STANDARD_COM like ?");
			listParam.add("%"+standardCom+"%");
		}
		return dao.pageQueryForList(sql.toString(), listParam);
	}
	
	
	/**
	 * 保存新增的数据
	 * @param dcStandardSpecBO
	 * @throws OptimusException
	 */
	public void saveAdd(DcStandardSpecBO dcStandardSpecBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(dcStandardSpecBO);
		
	}
	
	/**
	 * 查询表中记录的总个数
	 * @return int
	 * @throws OptimusException
	 */
	public int getNumData() throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select count(1) from DC_STANDARD_SPEC"; 
		return dao.queryForInt(sql.toString(),null);
		
		
	}

	/**
	 * 删除选中的标准规范
	 * @param pkDcStandardSpec
	 * @throws OptimusException
	 */
	public void deleteDataStandar(String pkDcStandardSpec) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("update DC_STANDARD_SPEC set EFFECTIVE_MARKER=? where PK_DC_STANDARD_SPEC=?");
		listParam.add(AppConstants.EFFECTIVE_N);
		listParam.add(pkDcStandardSpec);
		
		//执行sql
		dao.execute(sql.toString(), listParam);
	}

	
	/**
	 * 通过主键查询标准规范
	 * @param param
	 * @throws OptimusException
	 */
	public List<DcStandardSpecBO> getDataStandarById(List param) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//通过主键查询
		List<DcStandardSpecBO> list = dao.queryByKey(DcStandardSpecBO.class, param);
		return list;
	}

	
	/**
	 * 编辑标准规范
	 * @param dcStandardSpecBO
	 * @throws OptimusException
	 */
	public void saveUpdate(DcStandardSpecBO dcStandardSpecBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(dcStandardSpecBO);
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
	 * 通过主键查询文件的路径
	 * 
	 * @param pkDcStandardSpec
	 * @return List
	 */
	public List<Map> getUrlAndName(String pkDcStandardSpec) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DC_STANDARD_SPEC where EFFECTIVE_MARKER=? and PK_DC_STANDARD_SPEC=?");
		listParam.add(AppConstants.EFFECTIVE_Y);
		listParam.add(pkDcStandardSpec);
		
		//封装数据
		return dao.queryForList(sql.toString(), listParam);
			 
	}

	

}
