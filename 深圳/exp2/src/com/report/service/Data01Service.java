package com.report.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.report.comon.TimeUtil;
import com.report.comon.TransferTime;
import com.report.dao.BaseDao;


@Component
@Transactional
public class Data01Service extends BaseDao{
	
	private static Logger logger = Logger.getLogger("s01"); // 获取logger实例

	
	
	
	public List jinNian(String endTime){
		String sql = this.getQuerySQLForTable1();
		//and t.lastdate &lt;= to_date('${etime}', 'yyyy-MM-dd hh24:mi:ss')
		
		sql +=" select t.*,p.总数 from\n" +
						"(select X.regorg_CN \"辖区\",X.industryphy_cn \"行业\",COUNT(*) \"数量\"\n" + 
						"from x\n" + 
						//"WHERE X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n" + 
						"WHERE to_char( X.ESTDATE,'yyyy-mm-dd') <= "+endTime+"  \n" + 
						"AND X.entstatus IN ('1')\n" + 
						"GROUP BY X.regorg_CN,X.industryphy_cn\n" + 
						"ORDER BY X.regorg_CN ASC, COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN \"辖区\", count(*) \"总数\"\n" + 
						"from x\n" + 
						//"WHERE X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n" + 
						"WHERE to_char( X.ESTDATE,'yyyy-mm-dd') <= "+endTime+"  \n" + 
						"AND X.entstatus IN ('1')\n" + 
						"GROUP BY X.regorg_CN\n" + 
						") p\n" + 
						"where p.辖区=t.辖区";

		
		
						 
		String wrapSql = " select * from (SELECT t.*" +
				"   FROM (SELECT ROW_NUMBER() OVER(PARTITION BY 辖区 ORDER BY 数量 DESC) rn," + 
				"         b.*" + 
				"         FROM ("+sql+" ) b) t  WHERE t.rn <= 3) t  order by instr('福田局,罗湖局,盐田局,南山局,宝安局,光明局,龙岗局,坪山局,龙华局,大鹏局', t.辖区) , 数量 desc ";

				
				 
		logger.debug("\n\n今年数据\n\n" + sql + "\n\n 结束");
		
		Query query = this.getSession().createSQLQuery(wrapSql);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();
		System.out.println(list);
		return list;
	}
	
	
	
	

	public List quNianTongQi(String endTime) {
		
	     
	    String sql = " ";
		sql += this.getQuerySQLForTable1();
		sql +=" select t.*,p.总数 from\n" +
				"(select X.regorg_CN \"辖区\",X.industryphy_cn \"行业\",COUNT(*) \"数量\"\n" + 
				"from x\n" + 
				//"WHERE X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n" + 
				"WHERE to_char( X.ESTDATE,'yyyy-mm-dd') <= "+endTime+"  \n" + 
				"AND X.entstatus IN ('1')\n" + 
				"GROUP BY X.regorg_CN,X.industryphy_cn\n" + 
				"ORDER BY X.regorg_CN ASC, COUNT(*) DESC\n" + 
				") t,\n" + 
				"(select X.regorg_CN \"辖区\", count(*) \"总数\"\n" + 
				"from x\n" + 
				//"WHERE X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n" + 
				"WHERE to_char( X.ESTDATE,'yyyy-mm-dd') <= "+endTime+"  \n" + 
				"AND X.entstatus IN ('1')\n" + 
				"GROUP BY X.regorg_CN\n" + 
				") p\n" + 
				"where p.辖区=t.辖区";



				 
        String wrapSql = " select * from (SELECT t.*" +
		"   FROM (SELECT ROW_NUMBER() OVER(PARTITION BY 辖区 ORDER BY 数量 DESC) rn," + 
		"         b.*" + 
		"         FROM ("+sql+" ) b) t  WHERE t.rn <= 3) t  order by instr('福田局,罗湖局,盐田局,南山局,宝安局,光明局,龙岗局,坪山局,龙华局,大鹏局', t.辖区) , 数量 desc ";
				 
		logger.debug("\n\n去年相同月份数据\n\n" + sql);
		
		Query query = this.getSession().createSQLQuery(wrapSql);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();
		System.out.println(list);
		return list;
		
	}
	
	public static void main(String[] args) throws ParseException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml") ;
		
		
		Data01Service ss = ac.getBean(Data01Service.class);
	
		String timeStr = "2019-09-25";
		
		Map<String,String> map = TransferTime.getStartTimeAndEndTimeByStr(timeStr);
		
		String quNianTongQi = map.get("选择查询截止时间的去年同期时间");
		
		String begTime = map.get("今年年初时间");
		String endTime = map.get("选择查询截止时间");
		
		System.out.println(map);
		
		ss.jinNian(endTime);
		
		
		
		ss.quNianTongQi(quNianTongQi);
		
	}

}
