package com.gwssi.report.shenziqiye.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.report.service.EBusinessService;
import com.gwssi.report.util.LogOperation;


@Service
public class ShengZiQiYeHangYeService extends BaseService {
	
	
	private static Logger log = Logger.getLogger(ShengZiQiYeHangYeService.class);
	LogOperation logop = new LogOperation();
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> query(String beginTime, String endTime, HttpServletRequest req) {
		IPersistenceDAO dao1 = this.getPersistenceDAO("tm_updata");
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(endTime);
		list.add(beginTime);
		
		list.add(endTime);
		
		
		String sql = 	
				"with x as(\n" +
						"  select\n" + 
						"        (case when substr(b.industryco,1,2) between '71' and '72' then '租赁和商业服务业'\n" + 
						"              when substr(b.industryco,1,2) between '66' and '69' then '金融业'\n" + 
						"              when substr(b.industryco,1,2) between '51' and '52' then '批发和零售业'\n" + 
						"              when substr(b.industryco,1,2) between '63' and '65' then '信息传输、软件和信息技术服务业'\n" + 
						"              when substr(b.industryco,1,2) between '73' and '75' then '科学研究和技术服务业'\n" + 
						"              else '其他行业'\n" + 
						"         end) hylx,\n" + 
						"        (case when b.estdate<=to_date(?,'yyyy-mm-dd hh24:mi:ss') and b.regstate='1' then 1\n" + 
						"              else 0\n" + 
						"         end) ljqy,   --截止时间\n" + 
						"        (case when b.estdate>=to_date(?,'yyyy-mm-dd') and b.estdate<=to_date(?,'yyyy-mm-dd hh24:mi:ss') then 1\n" + 
						"              else 0\n" + 
						"         end) xzqy  --开始时间、截止时间\n" + 
						"  FROM e_baseinfo b\n" + 
						"  where b.regorg = '441521'\n" + 
						")\n" + 
						"  select x.hylx \"行业类型\",\n" + 
						"         round(sum(x.ljqy)/(select sum(x.ljqy) from x),4)*100||'%' \"累计企业\",\n" + 
						"         round(sum(x.xzqy)/(select sum(x.xzqy) from x),4)*100||'%' \"新增企业\"\n" + 
						"  from x\n" + 
						"  group by x.hylx";
;
		try {
			//System.out.println(sql);
			list1 = dao1.queryForList(sql, list);
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list1;
	}
	
	
	


}
