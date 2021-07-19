package com.gwssi.query.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service(value = "feedbackHouseInfoService")
public class FeedbackHouseInfoService extends BaseService {
	private static final String DATASOURS = "dc_dc";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String feedbackInfo(Map map, HttpServletRequest request)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List list = new ArrayList();
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		int i = 0, j = 0, h = 0;
		list.add((String) map.get("qiDengYeWuBianHao").toString().trim());
		i = dao.queryForInt(
				"   select count(1) from dc_dc.dc_house_feedback f where f.id=?  and flag='0'  ",
				list);
		list2.add(map.get("jingBanRenShouJi").toString().trim());
		list2.add(map.get("yanZhengMa").toString().trim());
		h = dao.queryForInt(
				"select count(1) as count from v_dc_house_phone t where t.phone=? and t.code=?  and isused='1' ",
				list2);
		
			if (i > 0) {
				// return "问题反馈处理中，请不要重复提交！";
				//dao.execute("update dc_house_phone_code t set isused='0' where t.phone=? and t.code=? and t.isused='1'", list2);
				return "001";
				
			} else {
				if (h > 0) {
				String sql =
						"insert into dc_house_feedback\n" +
								"  (id,\n" + 
								"   main_tab_id,\n" + 
								"   pripid,\n" + 
								"   entname,\n" + 
								"   regno,\n" + 
								"   unifsocicrediden,\n" + 
								"   district,-- 区  100\n" + 
								"   street ,-- 街道100\n" + 
								"   COMMUNITY,-- 社区100\n" + 
								"   dom,\n" + 
								"   handler,\n" + 
								"   handler_mobtel,\n" + 
								"   addr_content,\n" + 
								"   feedback_time,\n" + 
								"   flag,\n" + 
								"   send_flag,\n" + 
								"   updatetime,\n" + 
								"   cause_of_feedback)\n" + 
								"select ?  as id,\n" + 
								"b.id as MAIN_TAB_ID,\n" + 
								"         pripid,\n" + 
								"         entname,\n" + 
								"         regno,\n" + 
								"         unifsocicrediden,\n" + 
								"         ? as district,\n" + 
								"         ? as street,\n" + 
								"         ? as COMMUNITY,\n" + 
								"         ? as dom,\n" + 
								"         ? as handler,\n" + 
								"         ? as handler_mobtel,\n" + 
								"         ? as addr_content,\n" + 
								"         sysdate as feedback_time,\n" + 
								"         '0' as flag,\n" + 
								"         '0' as send_flag,\n" + 
								"         sysdate as updatetime,\n" + 
								"         ? as cause_of_feedback\n" + 
								"          from dc_ra_mer_base b\n" + 
								"   where b.entname = ?\n" + 
								"     and rownum = 1";

				list1.add(map.get("qiDengYeWuBianHao"));
				list1.add(map.get("qu1"));
				list1.add(map.get("jieDao1"));
				list1.add(map.get("sheQu1"));
				list1.add(map.get("fangWuDiZhi"));
				list1.add(map.get("jingBanRen"));
				list1.add(map.get("jingBanRenShouJi"));
				list1.add(map.get("fangWuDiZhiMiaoShu"));
				list1.add(map.get("fanKuiYuanYin"));
				list1.add(map.get("qiYeMingCheng"));
				j = dao.execute(sql, list1);
				dao.execute("commit", null);
				if (j == 1) {
					dao.execute("update dc_house_phone_code t set isused='0' where t.phone=? and t.code=? and t.isused='1'", list2);
					// return "问题已经提交";
					return "002";
				} else {
					// return "企业名称错误或不存在，请核对";
					return "003";
				}
			}else {
				return "004";// 验证码通不过
			}
		} 
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map queryFeedback(Map map, HttpServletRequest request) {
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS);
		List list = new ArrayList();
		List<Map> list2 = null;
		StringBuffer sb = new StringBuffer();
		Map result = new HashMap();
		sb.append("  select f.house_num,f.flag,f.id,f.entname,f.dom,f.handler,f.addr_content,f.cause_of_feedback,f.handler_mobtel ,f.district ,f.street ,f.community from dc_house_feedback f where 1=1 and f.id=?  \n");
		list.add(map.get("qiDengYeWuBianHao").toString().trim());

		// 企业名称
		if (map.get("qiYeMingCheng") != null) {
			Object object = map.get("qiYeMingCheng");
			if (!isEmpty(object)) {
				sb.append(" and f.entname = ?");
				list.add(object.toString().trim());
			}
		}
		// fangWuDiZhi
		if (map.get("fangWuDiZhi") != null) {
			Object object = map.get("fangWuDiZhi");
			if (!isEmpty(object)) {
				sb.append(" and f.dom = ?");
				list.add(object.toString().trim());
			}
		}
		if (map.get("jingBanRen") != null) {
			Object object = map.get("jingBanRen");
			if (!isEmpty(object)) {
				sb.append(" and f.handler = ?");
				list.add(object.toString().trim());
			}
		}
		if (map.get("jingBanRenShouJi") != null) {
			Object object = map.get("jingBanRenShouJi");
			if (!isEmpty(object)) {
				sb.append(" and f.handler_mobtel = ?");
				list.add(object.toString().trim());
			}
		}
		if (map.get("fangWuDiZhiMiaoShu")!=null) {
			Object object = map.get("fangWuDiZhiMiaoShu");
			if (!isEmpty(object)) {
				sb.append(" and f.addr_content = ?");
				list.add(object.toString().trim());
			}
		}
		if (map.get("fanKuiYuanYin")!=null) {
			Object object = map.get("fanKuiYuanYin");
			if (!isEmpty(object)) {
				sb.append(" and f.cause_of_feedback = ?");
				list.add(object.toString().trim());
			}
		}
		if (map.get("qu1")!=null) {
			Object object = map.get("qu1");
			if (!isEmpty(object)) {
				sb.append(" and f.district = ?");
				list.add(object.toString().trim());
			}
		}
		if (map.get("jieDao1")!=null) {
			Object object = map.get("jieDao1");
			if (!isEmpty(object)) {
				sb.append(" and f.street = ?");
				list.add(object.toString().trim());
			}
		}
		if (map.get("sheQu1")!=null) {
			Object object = map.get("sheQu1");
			if (!isEmpty(object)) {
				sb.append(" and f.COMMUNITY = ?");
				list.add(object.toString().trim());
			}
		}
		try {
			list2 = dao.queryForList(sb.toString(), list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}

		if (list2 == null || list2.size() == 0) {
			result.put("code", "查无此房屋地址！");
			result.put("id", "");
			result.put("entname", "");
			result.put("houseAddr", "");
			result.put("handler", "");
			result.put("addrContent", "");
			result.put("causeOfFeedback", "");
			result.put("phone", "");
			result.put("district", "");
			result.put("street", "");
			result.put("community", "");
		} else {
			if ("0".equals(list2.get(0).get("flag"))) {
				Map map2 = list2.get(0);
				result.put("code", "尚未救济！");
				result.put("id", map2.get("id"));// 不存在
				result.put("entname", map2.get("entname"));// 不存在
				result.put("houseAddr", map2.get("dom"));// 不存在
				result.put("handler", map2.get("handler"));// 不存在
				result.put("addrContent", map2.get("addrContent"));// 不存在
				result.put("causeOfFeedback", map2.get("causeOfFeedback"));// 不存在
				result.put("phone", map2.get("handlerMobtel"));
				result.put("district", map2.get("district"));
				result.put("street", map2.get("street"));
				result.put("community", map2.get("community"));
			} else {
				Map map2 = list2.get(0);
				result.put("code", map2.get("houseNum"));
				result.put("id", map2.get("id"));
				result.put("entname", map2.get("entname"));
				result.put("houseAddr", map2.get("dom"));
				result.put("handler", map2.get("handler"));
				result.put("addrContent", map2.get("addrContent"));
				result.put("causeOfFeedback", map2.get("causeOfFeedback"));
				result.put("phone", map2.get("handlerMobtel"));
				result.put("district", map2.get("district"));
				result.put("street", map2.get("street"));
				result.put("community", map2.get("community"));
			}
		}
		return result;
	}

	public boolean isEmpty(Object o) {

		if (o == null) {
			return true;
		} else if (o.toString().trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
