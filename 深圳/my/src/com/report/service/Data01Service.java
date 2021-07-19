package com.report.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.dao.BaseDao;


@Transactional
@Service
public class Data01Service extends BaseDao {
	private static Logger logger = Logger.getLogger("s01"); // 获取logger实例
	
	
	public List getList(String time) {
		logger.debug("Data01Service===>list  \n");
		
		String sql = "select   辖区, 行业, 数量, 总数 from (\n" +
						"SELECT t.*\n" + 
						"   FROM (SELECT ROW_NUMBER() OVER(PARTITION BY 辖区 ORDER BY 数量 DESC) rn,\n" + 
						"         b.*\n" + 
						"         FROM (with x as(\n" + 
						"select\n" + 
						"      b.pripid id,\n" + 
						"      b.estdate estdate,\n" + 
						"      b.entname entname,\n" + 
						"      b.enttype enttype,\n" + 
						"      b.industryphy industryphy,\n" + 
						"      b.industryphy_cn industryphy_cn,\n" + 
						"      b.entstatus entstatus,\n" + 
						"      b.regorg regorg,\n" + 
						"      b.regorg_cn regorg_cn,\n" + 
						"      b.adminbrancode adminbrancode,\n" + 
						"      e.specflag specflag,\n" + 
						"      (CASE WHEN B.industryphy = 'A' and B.industryco is null THEN '0500'\n" + 
						"            WHEN B.industryphy = 'B' and B.industryco is null THEN '1200'\n" + 
						"            WHEN B.industryphy = 'C' and B.industryco is null THEN '4300'\n" + 
						"            WHEN B.industryphy = 'D' and B.industryco is null THEN '4600'\n" + 
						"            WHEN B.industryphy = 'E' and B.industryco is null THEN '5000'\n" + 
						"            WHEN B.industryphy = 'F' and B.industryco is null THEN '5100'\n" + 
						"            WHEN B.industryphy = 'G' and B.industryco is null THEN '6000'\n" + 
						"            WHEN B.industryphy = 'H' and B.industryco is null THEN '6200'\n" + 
						"            WHEN B.industryphy = 'I' and B.industryco is null THEN '6500'\n" + 
						"            WHEN B.industryphy = 'J' and B.industryco is null THEN '6900'\n" + 
						"            WHEN B.industryphy = 'K' and B.industryco is null THEN '7000'\n" + 
						"            WHEN B.industryphy = 'L' and B.industryco is null THEN '7200'\n" + 
						"            WHEN B.industryphy = 'M' and B.industryco is null THEN '7500'\n" + 
						"            WHEN B.industryphy = 'N' and B.industryco is null THEN '7800'\n" + 
						"            WHEN B.industryphy = 'O' and B.industryco is null THEN '8100'\n" + 
						"            WHEN B.industryphy = 'P' and B.industryco is null THEN '8200'\n" + 
						"            WHEN B.industryphy = 'Q' and B.industryco is null THEN '8400'\n" + 
						"            WHEN B.industryphy = 'R' and B.industryco is null THEN '8900'\n" + 
						"            WHEN B.industryphy = 'S' and B.industryco is null THEN '9500'\n" + 
						"            WHEN B.industryphy = 'T' and B.industryco is null THEN '9600'\n" + 
						"       ELSE NVL(B.INDUSTRYCO,' ') END) AS INDUSTRYCO\n" + 
						"from dc_ra_mer_base_query b,\n" + 
						"     dc_ra_mer_base_ext e\n" + 
						"where b.pripid=e.main_tb_id\n" + 
						")\n" + 
						"select t.\"辖区\",t.\"行业\",t.\"数量\",p.总数 from\n" + 
						"(select X.regorg_CN \"辖区\",X.industryphy_cn \"行业\",COUNT(*) \"数量\"\n" + 
						"from x\n" + 
						"WHERE X.ESTDATE<=TO_DATE('"+time+" 23:59:59','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"GROUP BY X.regorg_CN,X.industryphy_cn\n" + 
						"ORDER BY X.regorg_CN ASC, COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN \"辖区\", count(*) \"总数\"\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE('"+time+" 23:59:59','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"GROUP BY X.regorg_CN\n" + 
						") p\n" + 
						"where p.辖区=t.辖区) b) t\n" + 
						"  WHERE t.rn <= 3  ) t order by instr('福田局,罗湖局,盐田局,南山局,宝安局,光明局,龙岗局,坪山局,龙华局,大鹏局', t.辖区) , 数量 desc ";


		
		logger.debug(sql);
		//sql = "select * from ye_test_rec";
		Query query = this.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();

		return list;

	}
	
	
	
	public List getList2(String time) throws ParseException {
		logger.debug("Data01Service===>getList2 \n");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		//System.out.println(reStr);
		String lastYear = reStr; // 去年这个日期
		
		
		String sql = "select   辖区, 行业, 数量, 总数 from (\n" +
						"SELECT t.*\n" + 
						"   FROM (SELECT ROW_NUMBER() OVER(PARTITION BY 辖区 ORDER BY 数量 DESC) rn,\n" + 
						"         b.*\n" + 
						"         FROM (with x as(\n" + 
						"select\n" + 
						"      b.pripid id,\n" + 
						"      b.estdate estdate,\n" + 
						"      b.entname entname,\n" + 
						"      b.enttype enttype,\n" + 
						"      b.industryphy industryphy,\n" + 
						"      b.industryphy_cn industryphy_cn,\n" + 
						"      b.entstatus entstatus,\n" + 
						"      b.regorg regorg,\n" + 
						"      b.regorg_cn regorg_cn,\n" + 
						"      b.adminbrancode adminbrancode,\n" + 
						"      e.specflag specflag,\n" + 
						"      (CASE WHEN B.industryphy = 'A' and B.industryco is null THEN '0500'\n" + 
						"            WHEN B.industryphy = 'B' and B.industryco is null THEN '1200'\n" + 
						"            WHEN B.industryphy = 'C' and B.industryco is null THEN '4300'\n" + 
						"            WHEN B.industryphy = 'D' and B.industryco is null THEN '4600'\n" + 
						"            WHEN B.industryphy = 'E' and B.industryco is null THEN '5000'\n" + 
						"            WHEN B.industryphy = 'F' and B.industryco is null THEN '5100'\n" + 
						"            WHEN B.industryphy = 'G' and B.industryco is null THEN '6000'\n" + 
						"            WHEN B.industryphy = 'H' and B.industryco is null THEN '6200'\n" + 
						"            WHEN B.industryphy = 'I' and B.industryco is null THEN '6500'\n" + 
						"            WHEN B.industryphy = 'J' and B.industryco is null THEN '6900'\n" + 
						"            WHEN B.industryphy = 'K' and B.industryco is null THEN '7000'\n" + 
						"            WHEN B.industryphy = 'L' and B.industryco is null THEN '7200'\n" + 
						"            WHEN B.industryphy = 'M' and B.industryco is null THEN '7500'\n" + 
						"            WHEN B.industryphy = 'N' and B.industryco is null THEN '7800'\n" + 
						"            WHEN B.industryphy = 'O' and B.industryco is null THEN '8100'\n" + 
						"            WHEN B.industryphy = 'P' and B.industryco is null THEN '8200'\n" + 
						"            WHEN B.industryphy = 'Q' and B.industryco is null THEN '8400'\n" + 
						"            WHEN B.industryphy = 'R' and B.industryco is null THEN '8900'\n" + 
						"            WHEN B.industryphy = 'S' and B.industryco is null THEN '9500'\n" + 
						"            WHEN B.industryphy = 'T' and B.industryco is null THEN '9600'\n" + 
						"       ELSE NVL(B.INDUSTRYCO,' ') END) AS INDUSTRYCO\n" + 
						"from dc_ra_mer_base_query b,\n" + 
						"     dc_ra_mer_base_ext e\n" + 
						"where b.pripid=e.main_tb_id\n" + 
						")\n" + 
						"select t.\"辖区\",t.\"行业\",t.\"数量\",p.总数 from\n" + 
						"(select X.regorg_CN \"辖区\",X.industryphy_cn \"行业\",COUNT(*) \"数量\"\n" + 
						"from x\n" + 
						"WHERE X.ESTDATE<=TO_DATE('"+lastYear+" 23:59:59','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"GROUP BY X.regorg_CN,X.industryphy_cn\n" + 
						"ORDER BY X.regorg_CN ASC, COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN \"辖区\", count(*) \"总数\"\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE('"+lastYear+" 23:59:59','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"GROUP BY X.regorg_CN\n" + 
						") p\n" + 
						"where p.辖区=t.辖区) b) t\n" + 
						"  WHERE t.rn <= 3  ) t order by instr('福田局,罗湖局,盐田局,南山局,宝安局,光明局,龙岗局,坪山局,龙华局,大鹏局', t.辖区) , 数量 desc ";


		logger.debug("Data01Service  ==>" +sql);
		
		//sql = "select * from ye_test_rec";
		
		Query query = this.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();

		return list;

	}

}
