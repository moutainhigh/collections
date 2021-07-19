package com.gwssi.report.auth;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/**
 * 用户部门信息获取
 * @author wuyincheng ,Nov 23, 2016
 */
public class UserDepartmentUtils extends BaseService {
	
	private static final UserDepartmentUtils UTILS = new UserDepartmentUtils();
	
	private Map<String, Set<SimpleReportInfo>> reportDepartMaps = new HashMap<String, Set<SimpleReportInfo>>(1024);
	
	private static final Set<SimpleReportInfo> EMPTY = new HashSet<SimpleReportInfo>();
	
	private UserDepartmentUtils() {
		try {
			initReportDepartment();
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}
	
	//根据部门code获取所属报表集合
	public Set<SimpleReportInfo> getReportNames(String departmentCode){
		if(StringUtils.isBlank(departmentCode))
			return EMPTY;
		final Set<SimpleReportInfo> reportNames = reportDepartMaps.get(departmentCode);
		return reportNames;
	}


	public static UserDepartmentUtils getInstance() {
		return UTILS;
	}
	
	//初始化报表与部门的关系映射map
	@SuppressWarnings("rawtypes")
	private void initReportDepartment() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dbCsdb");
		List<Map> r = dao.queryForList("select * from T_REPORT_DEPARTMENT", null);
		String departmentCodes = null;
		String reportName = null;
		String reportType = null;
		String [] codes = null;
		if(r != null){
			for(Map m : r){
				departmentCodes = (String) m.get("departmentCodes");
				reportName = (String) m.get("reportname");
				reportType = (String) m.get("reporttype");
				if(departmentCodes == null || reportName == null)	
					continue ;
				codes = departmentCodes.split(",");
				for(String code : codes){
					if(reportDepartMaps.get(code) != null){
						reportDepartMaps.get(code).add(new SimpleReportInfo(reportName, reportType));
					}else{
						Set<SimpleReportInfo> sets = new HashSet<SimpleReportInfo>();
						sets.add(new SimpleReportInfo(reportName, reportType));
						reportDepartMaps.put(code, sets);
					}
				}
			}
		}
		
	}
}

