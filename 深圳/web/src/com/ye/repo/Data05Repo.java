package com.ye.repo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ye.base.BaseDao;
import com.ye.constrant.Constrant;



@Repository
@Transactional
public class Data05Repo extends BaseDao{
	private static Logger logger = Logger.getLogger("s05"); // 获取logger实例
	
	
	
	  //今年从一月到本月的数据
	   public List getThisYearFromJanuary(String month) {
		String commonOrdey = Constrant.COMMON_ORDER_BY;
		String timeStr = month + "-25 23:59:59";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String yearStr = month.split("-")[0];

		Integer lastYearStr = Integer.valueOf(yearStr) - 1;

		String defaultStartDate = lastYearStr + "-12-26 00:00:00";

		String begTime = defaultStartDate;

		String endTime = timeStr;

		String sql = this.getQuerySQL(false);
			
			sql+= " select t.*,p.总数 from\r\n" + 
					"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\r\n" + 
					"from x\r\n" + 
					"WHERE X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
					"and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
					"AND X.entstatus IN ('1')\r\n" + 
					"and substr(x.enttype,0,2)='95'\r\n" + 
					"GROUP BY X.regorg_CN,X.admin_cn\r\n" + 
					"ORDER BY  COUNT(*) DESC\r\n" + 
					") t,\r\n" + 
					"(select X.regorg_CN 辖区, count(*) 总数\r\n" + 
					"from x\r\n" + 
					"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
					"and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
					"AND X.entstatus IN ('1')\r\n" + 
					"and substr(x.enttype,0,2)='95'\r\n" + 
					"GROUP BY X.regorg_CN\r\n" + 
					") p\r\n" + 
					"where p.辖区=t.辖区 ";

			
		     logger.debug(sql);
			
			Query query = this.getSession().createSQLQuery(sql);
			
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			query.setFirstResult(Constrant.FIRST_PAGE);
	        query.setMaxResults(Constrant.MAX_PAGE);
			List list = query.list();
			
			System.out.println(list);
			
			return list;
		   
	   }
	
	/*
	 * 有史以来
	 */
	public List getYouShiYiLai(String month) {
		String commonOrdey = Constrant.COMMON_ORDER_BY;
		String timeStr = month + "-25 23:59:59";
		
		String endTime = timeStr;
		
		String sql = this.getQuerySQL(false);
		sql+="select t.*,p.总数 from\r\n" + 
				"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\r\n" + 
				"from x\r\n" + 
				"WHERE X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and substr(x.enttype,0,2)='95'\r\n" + 
				"GROUP BY X.regorg_CN,X.admin_cn\r\n" + 
				"ORDER BY  COUNT(*) DESC\r\n" + 
				") t,\r\n" + 
				"(select X.regorg_CN 辖区, count(*) 总数\r\n" + 
				"from x\r\n" + 
				"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and substr(x.enttype,0,2)='95'\r\n" + 
				"GROUP BY X.regorg_CN\r\n" + 
				") p\r\n" + 
				"where p.辖区=t.辖区 ";

		
	     logger.debug(sql);
		
		Query query = this.getSession().createSQLQuery(sql);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(Constrant.FIRST_PAGE);
        query.setMaxResults(Constrant.MAX_PAGE);
		List list = query.list();
		
		System.out.println(list);
		
		return list;

	}
	
	
	
	
	
	public static void main(String[] args) throws ParseException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml") ;
		Data05Repo ss = ac.getBean(Data05Repo.class);
		String timeStr = "2019-06";

		ss.getThisYearFromJanuary(timeStr);
		//ss.getYouShiYiLai(timeStr);
	}
	
}
