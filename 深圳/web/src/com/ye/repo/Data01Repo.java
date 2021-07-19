package com.ye.repo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class Data01Repo  extends BaseDao{
	
	private static Logger logger = Logger.getLogger("s01"); // 获取logger实例


	
	public List getThisYear(String month) {
		String endTime = month + "-25 23:59:59";
		String sql = this.getQuerySQLForTable1() ;
		
		  String commonOrdey = Constrant.COMMON_ORDER_BY;
		
		sql +=" select t.*,p.总数 from\n" +
				"(select X.regorg_CN \"辖区\",X.industryphy_cn \"行业\",COUNT(*) \"数量\"\n" + 
				"from x\n" + 
				"WHERE X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" + 
				"AND X.entstatus IN ('1')\n" + 
				"GROUP BY X.regorg_CN,X.industryphy_cn\n" + 
				"ORDER BY X.regorg_CN ASC, COUNT(*) DESC\n" + 
				") t,\n" + 
				"(select X.regorg_CN \"辖区\", count(*) \"总数\"\n" + 
				"from x\n" + 
				"WHERE X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" + 
				"AND X.entstatus IN ('1')\n" + 
				"GROUP BY X.regorg_CN\n" + 
				") p\n" + 
				"where p.辖区=t.辖区";
		
		 
			/*	String wrapSql = " select * from (SELECT t.*" +
					"   FROM (SELECT ROW_NUMBER() OVER(PARTITION BY 辖区 ORDER BY 数量 DESC) rn," + 
					"         b.*" + 
					"         FROM ("+sql+" ) b) t  WHERE t.rn <= 3) t  order by instr('福田局,罗湖局,盐田局,南山局,宝安局,光明局,龙岗局,坪山局,龙华局,大鹏局,深汕监管局', t.辖区) , 数量 desc ";*/
		
		
		String wrapSql = " select * from (SELECT t.*" +
				"   FROM (SELECT ROW_NUMBER() OVER(PARTITION BY 辖区 ORDER BY 数量 DESC) rn," + 
				"         b.*" + 
				"         FROM ("+sql+" ) b) t  WHERE t.rn <= 3) t  order by instr('"+commonOrdey+"', t.辖区) , 数量 desc ";
						
						
				
				logger.debug("\n\n今年数据\n\n" + sql + "\n\n 结束");
				
				
				
				Query query = this.getSession().createSQLQuery(wrapSql);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				
				List list = query.list();
				System.out.println(list);
				
				
				return list;
		
	}
	
	
	
	public List getLastYear(String  month) {
		
		String sql = this.getQuerySQLForTable1() ;
		
		  String commonOrdey = Constrant.COMMON_ORDER_BY;
		
		String timeStr = 	  month + "-25 23:59:59";
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date  date = null;
		try {
			date = format.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		
		//https://www.cnblogs.com/bunuo/p/6140750.html
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, -1);
		Date y = c.getTime();
		String year = format.format(y);
		System.out.println("过去一年："+year);
		
		String endTime = year;
		
		
		sql +=" select t.*,p.总数 from\n" +
				"(select X.regorg_CN \"辖区\",X.industryphy_cn \"行业\",COUNT(*) \"数量\"\n" + 
				"from x\n" + 
				"WHERE X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" + 
				"AND X.entstatus IN ('1')\n" + 
				"GROUP BY X.regorg_CN,X.industryphy_cn\n" + 
				"ORDER BY X.regorg_CN ASC, COUNT(*) DESC\n" + 
				") t,\n" + 
				"(select X.regorg_CN \"辖区\", count(*) \"总数\"\n" + 
				"from x\n" + 
				"WHERE X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" + 
				"AND X.entstatus IN ('1')\n" + 
				"GROUP BY X.regorg_CN\n" + 
				") p\n" + 
				"where p.辖区=t.辖区";
		
		
		String wrapSql = " select * from (SELECT t.*" +
				"   FROM (SELECT ROW_NUMBER() OVER(PARTITION BY 辖区 ORDER BY 数量 DESC) rn," + 
				"         b.*" + 
				"         FROM ("+sql+" ) b) t  WHERE t.rn <= 3) t  order by instr('"+commonOrdey+"', t.辖区) , 数量 desc ";
						
						
				
				logger.debug("\n\n去年数据\n\n" + sql + "\n\n 结束");
				
				
				
				Query query = this.getSession().createSQLQuery(wrapSql);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				
				List list = query.list();
				System.out.println(list);
				
				
				return list;
		
	}
	
	
	
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");
		Data01Repo ss =  ac.getBean(Data01Repo.class);
		
		ss.getLastYear("2019-06");
		
		
	}
}
