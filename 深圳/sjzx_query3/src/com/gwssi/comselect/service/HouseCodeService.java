package com.gwssi.comselect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service(value = "houseCodeService")
public class HouseCodeService extends BaseService {
	/**
	 * 数据源
	 */
	private static final String DATASOURS = "dc_dc";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> getRegCode(Object object,
			HttpServletRequest httpRequest) {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List list = null;
		String sql = "select h.region text,h.region_code value from dc_dc.dc_house_housenum h group by h.region_code,h.region order by h.region_code";
		try {
			list = dao.queryForList(sql, null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, Object>> getStrCode(String param,
			HttpServletRequest request) {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List list1 = null;
		List list = new ArrayList();
		list.add(param);
		String sql = "select h.street_code value,h.street text from dc_dc.dc_house_housenum h where substr(h.street_code,0,6)=? group by  h.street_code,h.street order by h.street_code";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getBuildingInfo(Map<String, String> map,
			HttpServletRequest httpRequest) {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		StringBuffer sql = new StringBuffer();
		List<Map> list1 = new ArrayList<Map>();
		sql.append("select t.building_code,t.building_name,t.building_add from dc_dc.dc_house_housenum t where 1=1 ");
		List list = new ArrayList();
		if (map != null) {
			Object object = map.get("region");
			if (object != null) {
				if (object.toString().trim().length() > 0) {
					sql.append(" and t.region_code = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("street") != null) {
				Object object2 = map.get("street");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and t.street_code = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("buildingName") != null) {
				Object object2 = map.get("buildingName");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and t.building_name = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("buildingAdd") != null) {
				Object object2 = map.get("buildingAdd");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and t.building_add = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("buildingCode") != null) {
				Object object2 = map.get("buildingCode");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and t.building_code = ?");
					list.add(object2.toString().trim());
				}
			}
		}
		try {
			list1 = dao.pageQueryForList(sql.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getHouseInfo(String param, HttpServletRequest httpRequest) {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List<Map> list1 = new ArrayList<Map>();
		List list = new ArrayList();
		list.add(param);
		String sql = "select h.house_num ,h.house_add from dc_dc.dc_house_housenum h where 1=1 and h.building_code= ? ";
		try {
			list1 = dao.pageQueryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getHouseInfoByForm(Map<String, String> map,
			HttpServletRequest httpRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		StringBuffer sql = new StringBuffer();
		List<Map> list1 = new ArrayList<Map>();
		sql.append("select h.house_num ,h.house_add from dc_dc.dc_house_housenum h where 1=1");
		List list = new ArrayList();
		if (map != null) {

			if (map.get("houseNum") != null) {
				Object object = map.get("houseNum");
				if (object.toString().trim().length() > 0) {
					sql.append(" and h.house_num = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("houseAdd") != null) {
				Object object = map.get("houseAdd");
				if (object.toString().trim().length() > 0) {
					sql.append(" and h.house_add = ?");
					list.add(object.toString().trim());
				}
			}
		}

		list1 = dao.pageQueryForList(sql.toString(), list);
		return list1;
	}

}
