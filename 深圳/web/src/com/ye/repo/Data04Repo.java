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
public class Data04Repo extends BaseDao{
	private static Logger logger = Logger.getLogger("s04"); // 获取logger实例
	
	public List getLastYearTongQi(String month) {
		String commonOrdey = Constrant.COMMON_ORDER_BY;
		String timeStr = month + "-25 23:59:59";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date  date = null;
		try {
			date = format.parse(month+"-26 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}  
	   
		
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		calendar.add(Calendar.MONTH, -3);  //设置为前3月
		
		Date dBefore = calendar.getTime();
		String defaultStartDate = format.format(dBefore);
		
		String thisYearBegTime = defaultStartDate;
		
		String thisYearEndTime = timeStr;
		
		
		
		Date  date01 = null;
		Date  date02 = null;
		try {
			date01 = format.parse(thisYearBegTime);
			date02 = format.parse(thisYearEndTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		
		 Calendar c1 = Calendar.getInstance();
		 c1.setTime(date01);
	     c1.add(Calendar.YEAR, -1);
	     Date y1 = c1.getTime();
	     String begTime  = format.format(y1);
		
	     Calendar c2 = Calendar.getInstance();
	     c2.setTime(date02);
	     c2.add(Calendar.YEAR, -1);
	     Date y2 = c2.getTime();
	     String endTime = format.format(y2);
		 
		String sql = this.getQuerySQL(false);
		
		sql+=" select t.*,p.总数 from\n" +
		"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
		"from x\n" + 
		"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" ;
			sql+=" and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\n" ; 
		
		sql+= " AND X.entstatus IN ('1')\n" + 
		"and substr(x.enttype,0,2)<>'95'\n" + 
		"GROUP BY X.regorg_CN,X.admin_cn\n" + 
		"ORDER BY X.regorg_CN ASC, COUNT(*) DESC\n" + 
		") t,\n" + 
		"(select X.regorg_CN 辖区, count(*) 总数\n" + 
		"from x\n" + 
		"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" ;
		sql+=" and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\n" ; 
		
		sql+=" AND X.entstatus IN ('1')\n" + 
		"and substr(x.enttype,0,2)<>'95'\n" + 
		"GROUP BY X.regorg_CN\n" + 
		") p\n" + 
		"where p.辖区=t.辖区";

		
		

		sql ="SELECT *  FROM (SELECT 辖区,监管所，数量，总数,"+
				   "            ROW_NUMBER() OVER(PARTITION BY 辖区 ORDER BY 辖区) AS RN"+
				  "        FROM ("+sql+") \n)"+
				  " WHERE RN <= 3   order by instr('"+commonOrdey+"', 辖区) , 数量 desc";

		
	     logger.debug(sql);
		
		Query query = this.getSession().createSQLQuery(sql);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(Constrant.FIRST_PAGE);
        query.setMaxResults(Constrant.MAX_PAGE);
		List list = query.list();
		
		System.out.println(list);
		
		return list;

		
	}


/**
 * 当期的(按季度来算)
 * @param begTime
 * @param endTime
 * @return
 */
public List getBenQi(String month) {
	
	  String commonOrdey = Constrant.COMMON_ORDER_BY;
	String timeStr = month + "-25 23:59:59";
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	Date  date = null;
	try {
		date = format.parse(month+"-26 00:00:00");
	} catch (ParseException e) {
		e.printStackTrace();
	}  
   
	
	Calendar calendar = Calendar.getInstance(); //得到日历
	calendar.setTime(date);//把当前时间赋给日历
	calendar.add(Calendar.MONTH, -3);  //设置为前3月
	
	Date dBefore = calendar.getTime();
	String defaultStartDate = format.format(dBefore);
	
	String begTime = defaultStartDate;
	
	String endTime = timeStr;
	
	
	String sql = this.getQuerySQL(false);
	
	
	sql+=" select t.*,p.总数 from\n" +
	"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
	"from x\n" + 
	"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" ;
		sql+=" and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\n" ; 
	
	sql+= " AND X.entstatus IN ('1')\n" + 
	"and substr(x.enttype,0,2)<>'95'\n" + 
	"GROUP BY X.regorg_CN,X.admin_cn\n" + 
	"ORDER BY X.regorg_CN ASC, COUNT(*) DESC\n" + 
	") t,\n" + 
	"(select X.regorg_CN 辖区, count(*) 总数\n" + 
	"from x\n" + 
	"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" ;
	sql+=" and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\n" ; 
	
	sql+=" AND X.entstatus IN ('1')\n" + 
	"and substr(x.enttype,0,2)<>'95'\n" + 
	"GROUP BY X.regorg_CN\n" + 
	") p\n" + 
	"where p.辖区=t.辖区";

	
	

	sql ="SELECT *  FROM (SELECT 辖区,监管所，数量，总数,"+
			   "            ROW_NUMBER() OVER(PARTITION BY 辖区 ORDER BY 辖区) AS RN"+
			  "        FROM ("+sql+") \n)"+
			  " WHERE RN <= 3   order by instr('"+commonOrdey+"', 辖区) , 数量 desc";

     logger.debug(sql);
	
	Query query = this.getSession().createSQLQuery(sql);
	
	query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	query.setFirstResult(Constrant.FIRST_PAGE);
    query.setMaxResults(Constrant.MAX_PAGE);
	List list = query.list();
	
	System.out.println(list);
	
	return list;

	
}
	

 //今年从一月到本月的数据
public List getThisYearFromJanuary(String month) {
	
	  String commonOrdey = Constrant.COMMON_ORDER_BY;
	String timeStr = month + "-25 23:59:59";
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   
	String yearStr = month.split("-")[0];
		
	Integer lastYearStr = Integer.valueOf(yearStr) - 1 ;
		
		
	String defaultStartDate = lastYearStr + "-12-26 00:00:00";
		
	String begTime = defaultStartDate;
		
	String endTime = timeStr;
		
	String sql = this.getQuerySQL(false);
	
	
	sql+=" select t.*,p.总数 from\n" +
	"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
	"from x\n" + 
	"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" ;
		sql+=" and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\n" ; 
	
	sql+= " AND X.entstatus IN ('1')\n" + 
	"and substr(x.enttype,0,2)<>'95'\n" + 
	"GROUP BY X.regorg_CN,X.admin_cn\n" + 
	"ORDER BY X.regorg_CN ASC, COUNT(*) DESC\n" + 
	") t,\n" + 
	"(select X.regorg_CN 辖区, count(*) 总数\n" + 
	"from x\n" + 
	"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" ;
	sql+=" and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\n" ; 
	
	sql+=" AND X.entstatus IN ('1')\n" + 
	"and substr(x.enttype,0,2)<>'95'\n" + 
	"GROUP BY X.regorg_CN\n" + 
	") p\n" + 
	"where p.辖区=t.辖区";

	
	

	sql ="SELECT *  FROM (SELECT 辖区,监管所，数量，总数,"+
			   "            ROW_NUMBER() OVER(PARTITION BY 辖区 ORDER BY 辖区) AS RN"+
			  "        FROM ("+sql+") \n)"+
			  " WHERE RN <= 3   order by instr('"+commonOrdey+"', 辖区) , 数量 desc";

     logger.debug(sql);
	
	Query query = this.getSession().createSQLQuery(sql);
	
	query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	query.setFirstResult(Constrant.FIRST_PAGE);
    query.setMaxResults(Constrant.MAX_PAGE);
	List list = query.list();
	
	System.out.println(list);
	
	return list;

	
}



//去年从一月到本月的数据
public List getLastYearFromJanuary(String month) {
		String commonOrdey = Constrant.COMMON_ORDER_BY;

		String yearStrTemp = month.split("-")[0];
		String monthStrTemp = month.split("-")[1];

		month = Integer.valueOf(yearStrTemp) - 1 + "-" + monthStrTemp;

		String timeStr = month + "-25 23:59:59";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String yearStr = month.split("-")[0];

		Integer lastYearStr = Integer.valueOf(yearStr) - 1;

		String defaultStartDate = lastYearStr + "-12-26 00:00:00";

		String begTime = defaultStartDate;

		String endTime = timeStr;

		String sql = this.getQuerySQL(false);
	
	
	sql+=" select t.*,p.总数 from\n" +
	"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
	"from x\n" + 
	"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" ;
		sql+=" and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\n" ; 
	
	sql+= " AND X.entstatus IN ('1')\n" + 
	"and substr(x.enttype,0,2)<>'95'\n" + 
	"GROUP BY X.regorg_CN,X.admin_cn\n" + 
	"ORDER BY X.regorg_CN ASC, COUNT(*) DESC\n" + 
	") t,\n" + 
	"(select X.regorg_CN 辖区, count(*) 总数\n" + 
	"from x\n" + 
	"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" ;
	sql+=" and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\n" ; 
	
	sql+=" AND X.entstatus IN ('1')\n" + 
	"and substr(x.enttype,0,2)<>'95'\n" + 
	"GROUP BY X.regorg_CN\n" + 
	") p\n" + 
	"where p.辖区=t.辖区";

	
	

	sql ="SELECT *  FROM (SELECT 辖区,监管所，数量，总数,"+
			   "            ROW_NUMBER() OVER(PARTITION BY 辖区 ORDER BY 辖区) AS RN"+
			  "        FROM ("+sql+") \n)"+
			  " WHERE RN <= 3   order by instr('"+commonOrdey+"', 辖区) , 数量 desc";

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
	Data04Repo ss = ac.getBean(Data04Repo.class);
	String timeStr = "2019-06";
	
	//ss.getBenQi(timeStr);
	//ss.getLastYearFromJanuary(timeStr);
	
	
	ss.getThisYearFromJanuary(timeStr);
	
	ss.getLastYearFromJanuary(timeStr);
}
	
}
