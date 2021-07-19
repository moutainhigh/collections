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

@Service(value = "FoodLicSelectService")
public class FoodLicSelectService extends BaseService{
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
    /**
     * 食品许可证查询列表表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from v_food_license t where 1=1 ");
		List list=new ArrayList();
		if (map!=null) {
			Object name = map.get("entname");
			String licno = (String)map.get("licno");
			if (name!=null) {
				if (name.toString().length()>0) {
					sql.append(" and t.entname = ?");
					list.add(name.toString().trim());
				}
			}
			if (licno!=null) {
				if (licno.toString().length()>0) {
					sql.append(" and t.licno = ?");
					list.add(licno.toString().trim());
				}
			}
		}
		//String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		LogUtil.insertLog("食品许可证查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getSXListCount(Map<String, String> map) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from v_qy_TZRXX t where 1=1 ");
		List list=new ArrayList();
		if (map!=null) {
			Object name = map.get("name");
			Object cerno = map.get("cerno");
			if (name!=null) {
				if (name.toString().length()>0) {
					sql.append(" and t.INV = ?");
					list.add(name.toString().trim());
				}
			}
			if (cerno!=null) {
				if (cerno.toString().length()>0) {
					sql.append(" and t.CERNO = ?");
					list.add(cerno.toString().trim());
				}
			}
		}
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}


}
