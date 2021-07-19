package com.gwssi.report.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
/**
 * 报表权限,对应DB_CSDB库
 * @author wuyincheng ,Nov 24, 2016
 */
public class ReportAuthService extends BaseService {
	
	private static final Map<String, String> REGION_CODE = new HashMap<String, String>(16){
		private static final long serialVersionUID = -6950401821681704485L;
		{
			put("4000", "440304");//福田局
			put("4100", "440303");//罗湖局
			put("4200", "440305");//南山局
			put("4300", "440308");//盐田局
			put("4400", "440306");//宝安局
			put("4500", "440307");//龙岗局
			put("4600", "440309");//光明局
			put("4700", "440310");//坪山局
			put("4800", "440342");//龙华局
			put("4900", "440343");//大鹏局
			put("0000", "001");
			put("1000", "001");
			put("2000", "001");
			put("3000", "001");
			put("3100", "001");
			put("3200", "001");
			put("6000", "001");
			put("6200", "001");
			put("6230", "001");
			put("6270", "001");
			put("6300", "001");
			put("6500", "001");
			put("0200", "001");//市局
		}
	};
	
	//报表系统中的区域代码 与 标准代码转换(硬编码)
	public String getReportRegionCode(String departMentCode){
		if(departMentCode == null)
			return null;
		return REGION_CODE.get(departMentCode.trim());
	}

	//用户部门及上级部门 {部门code, 上级部门code, 角色code}
	@SuppressWarnings("rawtypes")
	public String[] getUserDepartment(String userName) {
		IPersistenceDAO dao = getPersistenceDAO("dbCsdb");
		String sql = "SELECT department_code, department_parent_code, door_user_role from xt_zzjg_yh where yhzh=?";
		List<String> params = new ArrayList<String>(1);
		params.add(userName);
		List<Map> r = null;
		String [] result = null;
		try {
			r = dao.queryForList(sql, params);
			if(r.size() == 0)
				return null; 
			result = new String [] {
				(String) r.get(0).get("departmentCode"),
				(String) r.get(0).get("departmentParentCode"),
				(String) r.get(0).get("doorUserRole")};
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//报表-部门映射关系
	@SuppressWarnings("rawtypes")
	public List<Map> getReportDepartment(String department) {
		IPersistenceDAO dao = getPersistenceDAO("dbCsdb");
		String sql = "SELECT * from T_REPORT_DEPARTMENT";
		List<Map> res = null;
		try {
			res = dao.queryForList(sql, null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	//部门-区域关系,根据部门获取区域
	public String getRegion(String department){
		IPersistenceDAO dao = getPersistenceDAO("dbCsdb");
		String sql="SELECT * FROM XT_REGION_DEPARTMENT WHERE DEPARTMENT=?";
		List<String> params = new ArrayList<String>();
		params.add(department);
		List<Map> res=null;
		String region = null;
		try {
			res= dao.queryForList(sql, params);
			if(res.size() == 0)
				return null; 
			region = (String) res.get(0).get("region");
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return region;
	}
	
}
