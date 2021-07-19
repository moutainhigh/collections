package com.gwssi.common.rodimus.report.expr.func.service;


/**
 * <h2>表达式数据源：码表</h2>
 * 
 * <p>所有方法必须以get开头。</p>
 * 
 * <p>TODO 稍后考虑缓存。</p>
 * 
 * @author liuhailong
 */
public class DictManager {
//	/**
//	 * @return 审核机关。
//	 * @author liuhailong
//	 */
//	public List<Map<String, Object>> getRegOrg(){
//		String sql = "select substr(o.org_code,0,6) as value,o.org_name as text from sysmgr_org o where o.org_type = '5' or o.org_type = '3' order by o.org_code asc";
//		List<Map<String,Object>> list = DaoUtil.getInstance("").queryForList(sql);
//		return list;
//	}
//	
//	/**
//	 * @return 企业类型。
//	 */
//	public List<Map<String, Object>> getEntType(){
//		String sql = "select v.code_value as id,v.code_name as name,p.code_value as p_id "+
//			" from sysmgr_cvalue v left join sysmgr_cvalue p on v.parent_id=p.code_id and p.type_id_fk='CA16' "+
//			" where v.type_id_fk='CA16' order by v.code_value ";
//		List<Map<String,Object>> list = DaoUtil.getInstance().queryForList(sql);
//		return list;
//	}
}
