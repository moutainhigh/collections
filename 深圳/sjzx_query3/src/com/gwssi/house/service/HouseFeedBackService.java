package com.gwssi.house.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class HouseFeedBackService extends BaseService {
	private static final String DATASOURS_DC_DC = "dc_dc";

	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getCode() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct fjdm  as value, fjmc as text  from v_jg_ent  order by value ");
		List<Map> list = dao.queryForList(sb.toString(), null);
		if(list!=null&&list.size()>0) {
			return list;
		}else {
			return null;
		}
		
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getHouseFeedCount(Map map) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT count(1) as count FROM DC_HOUSE_FEEDBACK T  where 1 = 1 ");
		List paramList = new ArrayList();
		if (map != null) {
			Object feedback_time = map.get("feedback_time1");
			Object feedback_time1 = map.get("feedback_time2");
			Object district = map.get("district");
			
			System.out.println("=====> " +feedback_time);
			if (feedback_time != null&&!feedback_time.equals("")) {
				if (feedback_time.toString().trim().length() > 0) {
					//sb.append(" and  t.feedback_time >= to_date(?,'yyyy-MM-dd hh24:mi:ss')");
					//paramList.add(feedback_time.toString().trim()+" 00:00:00");
					
					sb.append(" and to_char(t.feedback_time,'yyyy-mm-dd')>= ?");
					paramList.add(feedback_time.toString().trim());
					
				}
			}
			
			if (feedback_time1 != null&&!feedback_time1.equals("")) {
				if (feedback_time1.toString().trim().length() > 0) {
					//sb.append(" and  t.feedback_time < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
					//paramList.add(feedback_time.toString().trim() +" 23:59:59");
					
					sb.append(" and to_char(t.feedback_time,'yyyy-mm-dd')<= ?");
					paramList.add(feedback_time1.toString().trim());
				}
			}
			if (district != null&&!district.equals("")) {
				if(!district.equals("all")) {
					if (district.toString().trim().length() > 0) {
						sb.append(" and district = ?");
						paramList.add(district.toString().trim());
					}
				}
			}
		}
		List<Map> list = dao.queryForList(sb.toString(), paramList);
		if(list!=null&&list.size()>0) {
			return list.get(0).get("count").toString();
		}else {
			return "0";
		}
		
	}

	// --房屋地址反馈信息
	@SuppressWarnings("unchecked")
	public List<Map> getHouseFeed(Map map, HttpServletRequest httpServletRequest) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT T.DISTRICT,COUNT(1) as count  FROM DC_HOUSE_FEEDBACK T  where 1 = 1 ");
		List paramList = new ArrayList();
		
		if (map != null) {
			Object feedback_time = map.get("feedback_time1");
			Object feedback_time1 = map.get("feedback_time2");
			Object district = map.get("district");
			
			if (feedback_time != null&&!feedback_time.equals("")) {
				if (feedback_time.toString().trim().length() > 0) {
					//sb.append(" and  t.feedback_time >= to_date(?,'yyyy-MM-dd hh24:mi:ss')");
					//paramList.add(feedback_time.toString().trim()+" 00:00:00");
					
					sb.append(" and to_char(t.feedback_time,'yyyy-mm-dd')>= ?");
					paramList.add(feedback_time.toString().trim());
					
				}
			}
			
			if (feedback_time1 != null&&!feedback_time1.equals("")) {
				if (feedback_time1.toString().trim().length() > 0) {
					//sb.append(" and  t.feedback_time < to_date(?,'yyyy-MM-dd hh24:mi:ss')");
					//paramList.add(feedback_time.toString().trim() +" 23:59:59");
					
					sb.append(" and to_char(t.feedback_time,'yyyy-mm-dd')<= ?");
					paramList.add(feedback_time1.toString().trim());
				}
			}
			
			
			if (district != null&&!district.equals("")) {
				if(!district.equals("all")) {
					if (district.toString().trim().length() > 0) {
						sb.append(" and district = ?");
						paramList.add(district.toString().trim());
					}
				}
			}
		}

		sb.append(" GROUP BY T.DISTRICT");
		List<Map> pageQueryForList = dao.pageQueryForList(sb.toString(), paramList);
		LogUtil.insertLog("房屋反馈查询", sb.toString(), paramList.toString(), httpServletRequest, dao);
		return pageQueryForList;
		// --feedback_time date y 反馈问题时间
		// --district varchar2(100) y 区

	}

}
