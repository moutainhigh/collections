package com.gwssi.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.report.model.ReportDistribute;
/**
 * @author wuyincheng ,Oct 14, 2016
 */
@Service
public class ReportDistributeService extends BaseService {
	
	
	/**
	 *报表与机构关联
	 * @param reportId 报表id
	 * @param deptId 局id
	 */
	public void updateDeptsTree(String reportId, String deptId){
//		IPersistenceDAO dao = this.getPersistenceDAO("defaultDataSource");
		List<ReportDistribute> adds = new ArrayList<ReportDistribute>(8);
		String [] deptIds = deptId.split(",");//更新后的code
		deleteDeptsTree(reportId);
		for(String t : deptIds) {
			adds.add(new ReportDistribute(UUID.randomUUID().toString(), reportId, t));
		}
		if(adds.size() > 0)
			addDeptsTree(adds);
	}
	
	//增加关联关系
	public void addDeptsTree(List<ReportDistribute> lists) {
		IPersistenceDAO dao = this.getPersistenceDAO("defaultDataSource");
		try {
			dao.insert(lists);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}
	
	//删除关联关系
	public void deleteDeptsTree(String reportId){
		IPersistenceDAO dao = this.getPersistenceDAO("defaultDataSource");
		if(StringUtils.isBlank(reportId))
			return ;
		List<String> params = new ArrayList<String>(1);
		try {
			params.add(reportId);
			dao.execute("delete from db_csdb.T_REPORT_DISTRIBUTE where reportid=?", params);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}
	
	//取某报表所有关联局
	/*public List<ReportDistribute> getDeptsByReportId(String reportId){
		if(StringUtils.isBlank(reportId))
			return null;
		IPersistenceDAO dao = this.getPersistenceDAO("defaultDataSource");
		List<String> params = new ArrayList<String>(1);
		params.add(reportId);
		List<ReportDistribute> result = null;
		try {
			result = dao.queryForList(ReportDistribute.class, 
					"select * from db_csdb.T_REPORT_DISTRIBUTE", params);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return result;
	}*/
	
	//取局所树形结构
	public List<Map> getDeptsTree(String reportId){
		IPersistenceDAO dao = this.getPersistenceDAO("dc_code");
		List<Map> result = null;
		try {
			result = dao.queryForList("select code as id, deptname as name, upperdept as pid from tcdept where depttype='4' or depttype='6'", null);
			//已选择需要checked:true
			List<Map> checkeds = getReportDepts(reportId);
			for(Map temp  : checkeds) {
				for(Map  res : result){
					if(temp.get("deptcode") != null && temp.get("deptcode").equals(res.get("id"))){
						res.put("checked", "true");
					}
				}
			}
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Map> getReportDepts(String reportId){
		IPersistenceDAO dao = this.getPersistenceDAO("defaultDataSource");
		List<Map> result = null;
		List<String> params = new ArrayList<String>();
		params.add(reportId);
		try {
			result = dao.queryForList("select * from DB_CSDB.T_REPORT_DISTRIBUTE where reportid=?", params);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//取所有“局”机构
	public List<Map> getAllUpperDept(){
		IPersistenceDAO dao = this.getPersistenceDAO("dc_code");
		List<Map> result = null;
		try {
			result = dao.queryForList("select code as id, deptname as name, upperdept as pid from tcdept where depttype='4'", null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//取所有“”机构
	public List<Map> getAllDept(String upperdept){
		IPersistenceDAO dao = this.getPersistenceDAO("dc_code");
		List<Map> result = null;
		List<String> params = new ArrayList<String>(1);
		try {
			StringBuilder s = new StringBuilder("select code as dept_id, deptname as name, upperdept as pid from tcdept where depttype='6' ");
			if(!StringUtils.isBlank(upperdept)) {
				params.add(upperdept);
				s.append(" and upperdept=? ");
			}
			result = dao.queryForList(s.toString(), params);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return result;
	}
	//public List<Map> get

}
