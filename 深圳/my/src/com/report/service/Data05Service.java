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
public class Data05Service extends BaseDao {

	private static Logger logger = Logger.getLogger("s05"); // 获取logger实例
	
	
	
	public static void main(String[] args) throws ParseException {
		Data05Service s = new Data05Service();
		s.getList2("2018-10-23");
	}
	
	//
	@SuppressWarnings("rawtypes")
	public  List getList(String time) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年
		
		
		String currentYear = str+" 23:59:59";
		String lastYear = rightNow.get(Calendar.YEAR) + "-12-26 00:00:00";
		
		System.out.println(currentYear);
		System.out.println(lastYear);
		
		String sql = "with x as(\n" +
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
						"      c.dep_name  admin_cn,\n" + 
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
						"     dc_ra_mer_base_ext e,\n" + 
						"     dc_jg_sys_right_department c\n" + 
						"where b.pripid=e.main_tb_id\n" + 
						"and b.adminbrancode=c.SYS_RIGHT_DEPARTMENT_ID\n" + 
						")\n" + 
						"\n" + 
						"select t.*,p.总数 from\n" + 
						"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
						"from x\n" + 
						"WHERE X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"and  X.ESTDATE>=TO_DATE('"+lastYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"and substr(x.enttype,0,2)='95'\n" + 
						"GROUP BY X.regorg_CN,X.admin_cn\n" + 
						"ORDER BY  COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN 辖区, count(*) 总数\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						" and  X.ESTDATE>=TO_DATE('"+lastYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						" AND X.entstatus IN ('1')\n" + 
						"and substr(x.enttype,0,2)='95'\n" + 
						"GROUP BY X.regorg_CN\n" + 
						") p\n" + 
						"where p.辖区=t.辖区";

		logger.debug(" 年初到本季度的\n"  + sql);

		

		Query query = this.getSession().createSQLQuery(sql);
		query.setMaxResults(10);
		query.setFirstResult(0);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List list = query.list();
		System.out.println(list);
		System.out.println("\n ===> count  " + list.size());
		
		return list;
	}
	
	
	
	
	
	
	//累计的
	@SuppressWarnings("rawtypes")
	public List getList2(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年
		
		
		String currentYear = str+" 23:59:59";
		String lastYear = rightNow.get(Calendar.YEAR) + "-12-26 00:00:00";
		
		System.out.println(currentYear);
		System.out.println(lastYear);
		
		
		String sql = 
				"with x as(\n" +
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
						"      c.dep_name  admin_cn,\n" + 
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
						"     dc_ra_mer_base_ext e,\n" + 
						"     dc_jg_sys_right_department c\n" + 
						"where b.pripid=e.main_tb_id\n" + 
						"and b.adminbrancode=c.SYS_RIGHT_DEPARTMENT_ID\n" + 
						")\n" + 
						"\n" + 
						"select t.*,p.总数 from\n" + 
						"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
						"from x\n" + 
						"WHERE X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"--and  X.ESTDATE>=TO_DATE('"+lastYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"and substr(x.enttype,0,2)='95'\n" + 
						"GROUP BY X.regorg_CN,X.admin_cn\n" + 
						"ORDER BY  COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN 辖区, count(*) 总数\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"--and  X.ESTDATE>=TO_DATE('"+lastYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"and substr(x.enttype,0,2)='95'\n" + 
						"GROUP BY X.regorg_CN\n" + 
						") p\n" + 
						"where p.辖区=t.辖区";

		logger.debug(" 累计的\n"  + sql);
		
		//sql = "select * from ye_test_rec";
		Query query = this.getSession().createSQLQuery(sql);
		query.setMaxResults(10);
		query.setFirstResult(0);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		System.out.println(list);
		System.out.println("\n ===> count  " + list.size());
		
		return list;

	}

}
