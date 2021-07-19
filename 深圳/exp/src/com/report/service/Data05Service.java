package com.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.report.comon.TransferTime;
import com.report.dao.BaseDao;

@Component
@Transactional
public class Data05Service extends BaseDao{
	private static Logger logger = Logger.getLogger("s05"); // 获取logger实例
	
	public List getList(String begTime,String endTime,boolean isBenQi) {
		
		String sql = this.getQuerySQL(false);
		sql+=
				"select t.*,p.总数 from\n" +
						"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
						"from x\n" + 
						"WHERE X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n" ;

						if(isBenQi) {
							sql+=" and  X.ESTDATE>=TO_DATE("+begTime+",'YYYY-MM-DD hh24:mi:ss')\n" ;
						}
						sql+=" AND X.entstatus IN ('1')\n" + 
						"and substr(x.enttype,0,2)='95'\n" + 
						"GROUP BY X.regorg_CN,X.admin_cn\n" + 
						"ORDER BY  COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN 辖区, count(*) 总数\n" + 
						"from x\n" + 
						"WHERE X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n" ;

						if(isBenQi) {
							sql+=" and  X.ESTDATE>=TO_DATE("+begTime+",'YYYY-MM-DD hh24:mi:ss')\n" ;
						}
						sql+=" AND X.entstatus IN ('1')\n" + 
						"and substr(x.enttype,0,2)='95'\n" + 
						"GROUP BY X.regorg_CN\n" + 
						") p\n" + 
						"where p.辖区=t.辖区";

		
	     logger.debug(sql);
		
		Query query = this.getSession().createSQLQuery(sql);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(0);
        query.setMaxResults(10);
		List list = query.list();
		
		System.out.println(list);
		
		return list;

	}
	
	public static void main(String[] args) throws ParseException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml") ;
		Data05Service ss = ac.getBean(Data05Service.class);
		String timeStr = "2019-03-25";

		
		
		Map<String,String> map = TransferTime.getStartTimeAndEndTimeByStr(timeStr);
		
		
		String quNianTongQi = map.get("选择查询截止时间的去年同期时间");
		
		String begTime = map.get("今年年初时间");
		String endTime = map.get("选择查询截止时间");
		//ss.getListByDangQi(begTime, endTime);
		
		
		ss.getList(begTime, endTime,true);
	}
	
}
