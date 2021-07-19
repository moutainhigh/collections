package com.gwssi.otherselect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;

@Service(value = "otherSelectService")
public class OtherSelectService extends BaseService{
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
    
	
	 /**
     * 餐饮服务许可证信息查询列表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getCYFWList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from ( select t.id,t.certificate_no,t.unit_name,t.legal_response from dc_f1_CYFW_ENT_BASIC_INFO t WHERE 1=1 "); //t.full_address,t.category,t.remark,substr(to_char(t.to_effect_limit,'yyyy-MM-dd'),0,10) as to_effect_limit
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("unitName");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and t.unit_name like ?");
					list.add("%"+object.toString().trim()+"%");
				}
			}
			if (map.get("certificateNo")!=null) {
				Object object2 = map.get("certificateNo");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.certificate_no = ?");
					list.add(object2.toString().trim());
				}
			}
		}
		sql.append(" ) where rownum<=10 ");
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
	//	LogUtil.insertLog("餐饮服务许可证信息查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}
	
	
	 /**
     * 食品流通许可证信息查询列表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getSPFWList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from (select t.id,t.splt_license_no,t.company_name,t.responsible_person from dc_F1_SPLT_ENT_BASIC_INFO t  WHERE 1=1 ");//,t.business_place,a.operation_type,'自'||substr(to_char(t.applicant_date,'yyyy-MM-dd'),0,10)||'至'||substr(to_char(t.valid_until,'yyyy-MM-dd'),0,10)  as validdate   left join dc_F1_SPLT_OPERATION_TYPE a on t.id = a.splt_ent_basic_info
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("spltLicenseNo");
			if (object!=null) {
				if (object.toString().trim().length()>0) {
					sql.append(" and t.splt_license_no = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("companyName")!=null) {
				Object object2 = map.get("companyName");
				if (object2.toString().trim().length()>0) {
					sql.append(" and t.company_name like ?");
					list.add("%"+object2.toString().trim()+"%");
				}
			}
		}
		sql.append(") where rownum<=10 ");
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
	//	LogUtil.insertLog("食品流通许可证信息查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}
	
	/**
	 * 餐饮许可证信息详情
	 * @param sExtSequence
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getCYQueryById(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		String sql=" select t.certificate_no,t.unit_name,t.legal_response,t.full_address,t.category,"
				+ "t.remark,substr(to_char(t.to_effect_limit,'yyyy-MM-dd'),0,10) as to_effect_limit "
				+ "from dc_f1_CYFW_ENT_BASIC_INFO t WHERE t.id = ? ";
		List list=new ArrayList();
		list.add(id);
		List<Map> pageQueryForList = dao.pageQueryForList(sql, list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList.get(0);
		}
		return null;
	}
	
	/**
	 * 食品流通许可证信息详情
	 * @param sExtSequence
	 * @return
	 * @throws OptimusException
	 */
	public Map getSPQueryById(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		String sql=" select t.splt_license_no,t.company_name,t.business_place,t.responsible_person,"
				+ "a.operation_type,'自'||substr(to_char(t.applicant_date,'yyyy-MM-dd'),0,10)||'至'||substr(to_char(t.valid_until,'yyyy-MM-dd'),0,10)  as validdate "
				+ "from dc_F1_SPLT_ENT_BASIC_INFO t left join dc_F1_SPLT_OPERATION_TYPE a on t.id = a.splt_ent_basic_info where t.id = ? ";
		List list=new ArrayList();
		list.add(id);
		List<Map> pageQueryForList = dao.pageQueryForList(sql, list);
		if(pageQueryForList !=null && pageQueryForList.size()>0){
			return pageQueryForList.get(0);
		}
		return null;
	}
}
