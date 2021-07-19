package com.gwssi.query.service;

import java.util.ArrayList;
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
		int i = 0, j = 0;
		list.add((String) map.get("fangWuBianMa"));
		i = dao.queryForInt(
				"select count(1) from dc_dc.dc_house_feedback f where f.house_num=?  and flag='0'",
				list);
		if (i > 0) {
			//return "问题反馈处理中，请不要重复提交！";
			return "001";
		} else {
			String sql = "insert into dc_house_feedback\n" + "  (id,\n"
					+ "   MAIN_TAB_ID,\n" + "   pripid,\n" + "   entname,\n"
					+ "   regno,\n" + "   unifsocicrediden,\n" + "   dom,\n"
					+ "   HOUSE_ID,\n" + "   house_num,\n" + "   handler,\n"
					+ "   handler_mobtel,\n" + "   content,\n"
					+ "   feedback_time,\n" + "   FLAG,\n" + "   SEND_FLAG,\n"
					+ "   UPDATETIME)\n" + "  select sys_guid() as id,\n"
					+ "         b.id as MAIN_TAB_ID,\n" + "         pripid,\n"
					+ "         entname,\n" + "         regno,\n"
					+ "         unifsocicrediden,\n" + "         ? as dom,\n"
					+ "         '' as HOUSE_ID,\n" + "         ? house_num,\n"
					+ "         ? handler,\n" + "         ? handler_mobtel,\n"
					+ "         ? content,\n"
					+ "         sysdate as feedback_time,\n"
					+ "         '0' FLAG,\n" + "         '0' SEND_FLAG,\n"
					+ "         sysdate as UPDATETIME\n"
					+ "    from dc_ra_mer_base b\n"
					+ "   where b.entname = ?\n" + "     and rownum = 1 \n";
			list1.add(map.get("zhuSuo"));
			list1.add(map.get("fangWuBianMa"));
			list1.add(map.get("jingBanRen"));
			list1.add(map.get("JingBanRenShouJi"));
			list1.add(map.get("wenTiMiaoShu"));
			list1.add(map.get("qiYeMingCheng"));
			j = dao.execute(sql, list1);
			dao.execute("commit",null);
			System.out.println(j);
			if (j == 1) {
				//return "问题已经提交";
				return "002";
			} else {
				//return "企业名称错误或不存在，请核对";
				return "003";
			}
		}
		
	}
}
