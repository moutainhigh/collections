package com.gwssi.comselect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service(value = "legalPensonSelectService")
public class LegalPensonSelectService extends BaseService{
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
    /**
     * 法定代表人查询列表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getLegalPensonList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from v_legalperson_base t where 1=1 ");
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("lerep");
			if (object!=null) {
				if (object.toString().length()>0) {
					sql.append(" and lerep = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("cerno")!=null) {
				String cerno = (String)map.get("cerno");
				String oldcerno = "";
				if (cerno.length()>0) {
					if(cerno.length() == 18){
						oldcerno = cerno.substring(0, 6) + cerno.substring(8, 17);
					}
					sql.append(" and (cerno = ? or cerno = ? )");
					list.add(cerno.trim());
					list.add(oldcerno.trim());
				}
			}
		}
		//String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		LogUtil.insertLog("法定代表人查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getLegalPensonListCount(Map map) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from v_legalperson_base t where 1=1 ");
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("lerep");
			if (object!=null) {
				if (object.toString().length()>0) {
					sql.append(" and lerep = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("cerno")!=null) {
				String cerno = (String)map.get("cerno");
				if (cerno.length()>0) {
					if(cerno.length() == 18){
						String oldcerno = cerno.replace(cerno.subSequence(6, 8), "");
					}
					sql.append(" and cerno = ?");
					list.add(cerno.trim());
				}
			}
		}
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}


}
