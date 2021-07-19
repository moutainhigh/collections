package com.gwssi.query.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service(value = "houseCodeService")
public class HouseCodeService extends BaseService {
	private static Logger logger = Logger.getLogger(HouseCodeService.class);
 	
	/**
	 * 数据源
	 */
 	private static final String DATASOURS = "dc_dc";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> getRegCode(Object object,
			HttpServletRequest httpRequest) {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List list = null;
		String sql = "select h.region text,h.region value from dc_dc.dc_house_housenum h group by h.region,h.region";
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
		String sql = 
				"select ' ' value,'请选择' text from dual\n" +
						"union all\n" + 
						"select  nvl(v.street,'qita') value, nvl(v.street,'其他') text from dc_dc.dc_address_view v \n" + 
						"where v.district= ? group by v.street";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list1;
	}

	
	/*
	sql = " SELECT * FROM ( SELECT A.*,rownum rn FROM (" + sql2+ " ) A WHERE rownum <=" + (pageIndex) * pageSize + " ) WHERE rn>" + (pageIndex - 1) * pageSize;
	 */
	//查询楼栋信息总条数
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getHouseListCount(Map map) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		StringBuffer sql=new StringBuffer();
		sql.append("select v.house_id,v.district,v.street,v.community,v.road,v.road_num,v.village,v.building from dc_address_view v  where 1=1 ");
		List list = new ArrayList();
		if (map != null&&map.size()!=0) {
			Object object = map.get("qu");
			if (object != null) {
				if (object.toString().trim().length() > 0) {
					sql.append(" and v.district = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("jieDao") != null) {
				Object object2 = map.get("jieDao");
				boolean flag="qita".equals(object2);
				if (object2.toString().trim().length() > 0 &&!flag) {
					sql.append(" and v.street = ?");
					list.add(object2.toString().trim());
				}else if(object2.toString().trim().length() > 0 && flag){
					sql.append(" and v.street is null ");
				}
			}
			if (map.get("sheQu") != null) {
				Object object2 = map.get("sheQu");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.community like ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("lu") != null) {
				Object object2 = map.get("lu");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.road like ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("jianZhuWu") != null) {
				Object object2 = map.get("jianZhuWu");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.village like ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("louDong") != null) {
				Object object2 = map.get("louDong");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.building like ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("louCeng") != null) {
				Object object2 = map.get("louCeng");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.FLOOR = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("fangHao") != null) {
				Object object2 = map.get("fangHao");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.room_num like  ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("FangWuBianMa") != null) {
				Object object2 = map.get("FangWuBianMa");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.house_id like  ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("FangWuDiZhi") != null) {
				Object object2 = map.get("FangWuDiZhi");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.house_add like  ?");
					list.add(object2.toString().trim());
				}
			}
		}else{
			sql.append(" and 1=2");
		}
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getBuildingInfo(Map<String, String> map,String pageIndex,String pageSize,
			HttpServletRequest httpRequest) {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		StringBuffer sql = new StringBuffer();
		List<Map> list1 = new ArrayList<Map>();
		sql.append("SELECT * FROM ( SELECT A.*, ROWNUM RN\n" + "FROM (");
		sql.append("select nvl(v.building_id,' ')building_id,nvl(v.house_id,' ')house_id,nvl(v.district,' ')district,nvl(v.street,' ')street,nvl(v.community,' ')community,nvl(v.road,' ')road,nvl(v.road_num,' ')road_num, nvl(v.village, ' ')village,nvl(v.building,' ')building,nvl(v.floor,' ')floor,nvl(v.room_num,' ')roo_num from dc_address_view v  where 1=1");
		List list = new ArrayList();
		if (map != null&&map.size()!=0) {
			Object object = map.get("qu");
			if (object != null) {
				if (object.toString().trim().length() > 0) {
					sql.append(" and v.district = ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("jieDao") != null) {
				Object object2 = map.get("jieDao");
				boolean flag="qita".equals(object2);
				if (object2.toString().trim().length() > 0 &&!flag) {
					sql.append(" and v.street = ?");
					list.add(object2.toString().trim());
				}else if(object2.toString().trim().length() > 0 && flag){
					sql.append(" and v.street is null ");
				}
			}
			if (map.get("sheQu") != null) {
				Object object2 = map.get("sheQu");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.community like ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("lu") != null) {
				Object object2 = map.get("lu");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.road like ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("jianZhuWu") != null) {
				Object object2 = map.get("jianZhuWu");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.village like ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("louDong") != null) {
				Object object2 = map.get("louDong");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.building like ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("louCeng") != null) {
				Object object2 = map.get("louCeng");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.FLOOR = ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("fangHao") != null) {
				Object object2 = map.get("fangHao");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.room_num like  ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("FangWuBianMa") != null) {
				Object object2 = map.get("FangWuBianMa");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.house_id like  ?");
					list.add(object2.toString().trim());
				}
			}
			if (map.get("FangWuDiZhi") != null) {
				Object object2 = map.get("FangWuDiZhi");
				if (object2.toString().trim().length() > 0) {
					sql.append(" and v.house_add like  ?");
					list.add(object2.toString().trim());
				}
			}
			sql.append(" ) A WHERE ROWNUM <= ? *? ) WHERE RN >= (?-1)*?+1");
			list.add(pageIndex==null||pageIndex.length()==0?1:pageIndex);
			list.add(pageSize==null||pageSize.length()==0?10:pageSize);
			list.add(pageIndex==null||pageIndex.length()==0?1:pageIndex);
			list.add(pageSize==null||pageSize.length()==0?10:pageSize);
			try {
				list1 = dao.pageQueryForList(sql.toString(), list);
			} catch (OptimusException e) {
				e.printStackTrace();
			}
			return list1;
		}else {
			System.out.println("参数为空");
			return null;
		}
		
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getHouseInfo(String param,String floor,String pageIndex,String pageSize, HttpServletRequest httpRequest) {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List<Map> list1 = new ArrayList<Map>();
		List list = new ArrayList();
		list.add(param);
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT * FROM ( SELECT A.*, ROWNUM RN\n" + "FROM (");
		sql.append( "select nvl(v.house_id,' ') house_id,nvl(v.HOUSE_ADD,' ')HOUSE_ADD from dc_dc.dc_address_view v where v.building_id= ?  ");
		if (floor!=null&&floor.length()!=0) {
			sql.append("and v.floor= ? ");
			list.add(floor);
		}
		
		sql.append(" ) A WHERE ROWNUM <= ? *? ) WHERE RN >= (?-1)*?+1");
		list.add(pageIndex==null||pageIndex.length()==0?1:pageIndex);
		list.add(pageSize==null||pageSize.length()==0?10:pageSize);
		list.add(pageIndex==null||pageIndex.length()==0?1:pageIndex);
		list.add(pageSize==null||pageSize.length()==0?10:pageSize);
		try {
			list1 = dao.pageQueryForList(sql.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return list1;
	}
	
	//根据楼栋编号获取该楼栋中的房屋数量
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getHouseCount(String param,String floor,HttpServletRequest httpRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List list = new ArrayList();
		StringBuffer  sql=new StringBuffer();
		sql.append("select nvl(v.house_id,' ') house_id,nvl(v.HOUSE_ADD,' ')HOUSE_ADD from dc_dc.dc_address_view v where v.building_id= ?  ");
		list.add(param);
		if (floor!=null&&floor.length()!=0) {
			sql.append("and v.floor= ? ");
			list.add(floor);
		}
		String testsql =  "select count(1) count from ("+sql.toString()+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getHouseInfoByForm(Map<String, String> map,String buildingNum,
			HttpServletRequest httpRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		StringBuffer sql = new StringBuffer();
		List<Map> list1 = new ArrayList<Map>();
		sql.append("select  nvl(v.house_id,' ')house_id,nvl(v.house_add,' ')house_add from dc_dc.dc_address_view v where v.building_id=? "
				+ " ");
		List list = new ArrayList();
		list.add(buildingNum);
		if (!"abcd".equals(map.get("floor"))) {
			sql.append(" and v.floor =? ");
			list.add(map.get("floor"));
		}
		//list.add(map.get("floor"));
		
		if (map != null&&map.size()!=0) {

			if (map.get("houseNum") != null) {
				Object object = map.get("houseNum");
				if (object.toString().trim().length() > 0) {
					sql.append(" and v.house_id like  ?");
					list.add(object.toString().trim());
				}
			}
			if (map.get("houseAdd") != null) {
				Object object = map.get("houseAdd");
				if (object.toString().trim().length() > 0) {
					sql.append(" and v.house_add like ?");
					list.add(object.toString().trim());
				}
			}
			
			list1 = dao.pageQueryForList(sql.toString(), list);
			return list1;
		}else{
			return null;
		}

		
	}

	public void queryFeedback(Map form, HttpServletRequest request) {
		List list=new ArrayList();
		//String sql="select "
	}
/*	public static void main(String[] args) {
		String strJson = null;
		String url = "https://api.szaic.gov.cn/sms/123/0/15022012842/123";
		try {
			strJson = MyX509TrustManager.getClient(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject json = JSONObject.parseObject(strJson);
		System.out.println(json);
}*/

}
