package com.gwssi.application.reporthome.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.report.balance.api.ReportModel;
import com.gwssi.report.balance.api.ReportSource;
import com.gwssi.report.model.TCognosReportBO;

@Service
public class ReportHomeService extends BaseService implements ReportSource {
	IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
	private String sqlYeWu = "\n"
			+ "---业务岗查询逻辑\n"
			+ "select distinct r.menu_code as function_code,\n"
			+ "r.menu_name as function_name ,\n"
			+ "r.menu_short  as function_name_shrot ,\n"
			+ "r.menu_type as function_type ,\n"
			+ "case when r.MENU_URL is not null then r.menu_url end as function_url,\n"
			+ "r.menu_level AS LEVEL_CODE,\n"
			+ "r.id  AS  pk_sys_Integration ,\n"
			+ "case when r.menu_parent_code is not null then r.menu_parent_code end as super_func_code from\n"
			+ "  (\n"
			+ "select distinct * from (\n"
			+ "select * from report_menus m\n"
			+ "start with id in (select br.menu_id from report_business_role br where menu_dept like (select  '%'||u.dep_code||'%' from report_users u where u.user_id= ? ))\n"
			+ "connect by prior menu_parent_code = menu_code\n"
			+ "union all\n"
			+ "select * from report_menus m\n"
			+ "start with id in (select br.menu_id from report_business_role br where menu_dept like (select '%'||u.dep_code||'%' from report_users u where u.user_id=?  ))\n"
			+ "connect by prior menu_code = menu_parent_code)\n"
			+ "UNION ALL\n"
			+ "SELECT * FROM REPORT_MENUS WHERE MENU_NAME ='首页'\n" + ") r \n"
			+ "where r.menu_flag='1'"
			+ " ORDER BY FUNCTION_CODE";

	private String sqlOther = "--其他岗位查询逻辑\n"
			+ "select distinct r.menu_code as function_code,\n"
			+ "r.menu_name as function_name ,\n"
			+ "r.menu_short  as function_name_shrot ,\n"
			+ "r.menu_type as function_type ,\n"
			+ "case when r.MENU_URL is not null then r.menu_url end as function_url,\n"
			+ "r.menu_level AS LEVEL_CODE,\n"
			+ "r.id  AS  pk_sys_Integration ,\n"
			+ "case when r.menu_parent_code is not null then r.menu_parent_code end as super_func_code from\n"
			+ "(select distinct  M.* from report_users u,\n"
			+ "     report_user_role ur,\n" + "     report_roles r,\n"
			+ "     report_role_func rf ,\n" + "     report_func f ,\n"
			+ "     report_func_menu fm,\n" + "     report_MeNus m\n"
			+ "     where u.id=ur.user_id\n" + "     and ur.role_id=r.id\n"
			+ "     and r.ID=rf.role_id\n" + "     and rf.func_id=f.id\n"
			+ "     and f.id=fm.func_id\n" + "     and fm.menu_id=m.id\n"
			+ "     and u.user_id=?  \n" + "     union all\n"
			+ "     SELECT * FROM REPORT_MENUS WHERE MENU_NAME ='首页'\n"
			+ "     ) r\n"
			+ "where r.menu_flag='1'"
			+ " ORDER BY FUNCTION_CODE";
	private String sqlGetRole = "--根据用户获取角色信息\n"
			+ "select r.role_name from report_users u,report_user_role ur ,report_roles r\n"
			+ "  where u.id=ur.user_id and ur.role_id=r.id and u.user_id=? ";
	private String sqlSuperSys = "--超级管理员（CHANGRUAN@SZAIC）访问全部页面   \n"
			+ "select distinct  r.menu_code as function_code,\n"
			+ "menu_name as function_name ,\n"
			+ "menu_short  as function_name_shrot ,\n"
			+ "menu_type as function_type ,\n"
			+ "case when MENU_URL is not null then menu_url end as function_url,\n"
			+ "menu_level AS LEVEL_CODE,\n"
			+ "id  AS  pk_sys_Integration ,\n"
			+ "case when menu_parent_code is not null then menu_parent_code end as super_func_code from dc_dc.report_menus r\n"
			+ "where r.menu_flag='1'"
			+ "  ORDER BY FUNCTION_CODE\n" + "";

	@Override
	public ReportModel getReport(TCognosReportBO queryInfo) {
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getAppFuncByAppId(String userId) {
		dao = this.getPersistenceDAO("dc_dc");
		List<Map> list = new ArrayList();
		List<Map> result = new ArrayList();
		List lists = new ArrayList();
		lists.add(userId);
		try {
			if ("CHANGRUAN@SZAIC".equals(userId)) {
				result = dao.queryForList(sqlSuperSys, null);
			} else {
				list = dao.queryForList(sqlGetRole, lists);
				if (list != null
						&& list.size() != 0
						&& list.get(0) != null
						&& list.get(0).size() != 0
						&& list.get(0).get("roleName") != null
						&& "YEWU_REPORT_ROLE".equals(list.get(0)
								.get("roleName"))) {
					lists.add(userId);
					result = dao.queryForList(sqlYeWu, lists);
				} else {
					result = dao.queryForList(sqlOther, lists);
				}
			}
		} catch (OptimusException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	public String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是GB2312
				String s = encode;
				return s; // 是的话，返回“GB2312“，以下代码同理
			}
		} catch (Exception exception) {
		}
		encode = "windows-1252";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是ISO-8859-1
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是ISO-8859-1
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}

		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是UTF-8
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是GBK
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return ""; // 如果都不是，说明输入的内容不属于常见的编码格式。
	}

}
