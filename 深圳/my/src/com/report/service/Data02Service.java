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
import com.report.task.ReportCacheBusTask;

@Transactional
@Service
public class Data02Service extends BaseDao {
	private static Logger logger = Logger.getLogger("s02"); // 获取logger实例
	
	

	// 数量12月26到本期的
	public List getSumJiDu(String time) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年

		String currentYear = str + " 23:59:59";
		String lastYear = rightNow.get(Calendar.YEAR) + "-12-26 00:00:00";

		System.out.println(currentYear);
		System.out.println(lastYear);

		String sql ="with x as(\n" + 
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
				"      c.DEP_NAME  admin_cn,\n" + 
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
				"and substr(b.enttype,0,2)<>'95'\n" + 
				")\n" + 
				"\n" + 
				"select t.*,p.总数 from\n" + 
				"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
				"from x\n" + 
				"WHERE X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
				"and  X.ESTDATE>=TO_DATE('"+lastYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
				"AND X.entstatus IN ('1')\n" + 
				"GROUP BY X.regorg_CN,X.admin_cn\n" + 
				"ORDER BY  COUNT(*) DESC\n" + 
				") t,\n" + 
				"(select X.regorg_CN 辖区, count(*) 总数\n" + 
				"from x\n" + 
				"where X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
				"and  X.ESTDATE>=TO_DATE('"+lastYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
				"AND X.entstatus IN ('1')\n" + 
				"GROUP BY X.regorg_CN\n" + 
				") p\n" + 
				"where p.辖区=t.辖区";

		
		logger.debug("数量12月26到本期的\n" + sql);
		
		//sql = "select * from  AA_TEST_DATA04";
		
		Query query = this.getSession().createSQLQuery(sql);
		query.setFirstResult(0);
		query.setMaxResults(10);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		System.out.println(list);
		return list;
	}

	// 累计的---有史以来到本期的
	public List getSumAll(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年

		String currentYear = str + " 23:59:59";
		String lastYear = rightNow.get(Calendar.YEAR) + "-12-26 00:00:00";

		System.out.println(currentYear);
		System.out.println(lastYear);

		String sql ="with x as(\n" + 
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
				"      c.DEP_NAME  admin_cn,\n" + 
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
				"and substr(b.enttype,0,2)<>'95'\n" + 
				")\n" + 
				"\n" + 
				"select t.*,p.总数 from\n" + 
				"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
				"from x\n" + 
				"WHERE X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
				"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\n" + 
				"AND X.entstatus IN ('1')\n" + 
				"GROUP BY X.regorg_CN,X.admin_cn\n" + 
				"ORDER BY  COUNT(*) DESC\n" + 
				") t,\n" + 
				"(select X.regorg_CN 辖区, count(*) 总数\n" + 
				"from x\n" + 
				"where X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
				"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\n" + 
				"AND X.entstatus IN ('1')\n" + 
				"GROUP BY X.regorg_CN\n" + 
				") p\n" + 
				"where p.辖区=t.辖区";
		
		
		logger.debug("累计的---有史以来到本期的\n" + sql);
		//sql = "select * from  AA_TEST_DATA04";
		Query query = this.getSession().createSQLQuery(sql);
		query.setFirstResult(0);
		query.setMaxResults(10);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();

		return list;

	}

	// 内资去年12月26到本期的
	public List getNeiZiJiDu(String time) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年

		String currentYear = str + " 23:59:59";
		String lastYear = rightNow.get(Calendar.YEAR) + "-12-26 00:00:00";

		System.out.println(currentYear);
		System.out.println(lastYear);

		
		String sql ="with x as(\n" +
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
				"and ((x.enttype in ('4000','4100','4110','4200','4210','4300','4310','4311','4312','4313','4400','4410',\n" + 
				"                           '3000','3100','3101','3102','3103','3600','3700',\n" + 
				"                           '4120','4220','4320','4321','4322','4323','4420',\n" + 
				"                           '3200','3201','3202','3203',\n" + 
				"                           '2140','2213','2223',\n" + 
				"                           '3301','4600','4601','4602','4603','4604','4605','4606','4607','4608','4609','4611','4612','4613','4700','4701','4800','4330','4340',\n" + 
				"                           '3500','3501','3502','3503','3504','3505','3506','3507','3508','3509','3511','3512','3513','3300','3400',\n" + 
				"               '1140','1213','1223'))\n" + 
				"       or (x.enttype in ('1110','2110') and (substr(trim(x.specflag),8,1)='0' or trim(x.specflag) is null))\n" + 
				"       or (x.enttype in ('2000','2100','2120','2200','2210','2220','A000',\n" + 
				"                            '1000','1100','1120','1130','1152','1200','1210','1220','1230',\n" + 
				"                            '1300','2300') and substr(x.specflag,8,1)='0'))\n" + 
				"GROUP BY X.regorg_CN,X.admin_cn\n" + 
				"ORDER BY  COUNT(*) DESC\n" + 
				") t,\n" + 
				"(select X.regorg_CN 辖区, count(*) 总数\n" + 
				"from x\n" + 
				"where X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
				"and  X.ESTDATE>=TO_DATE('"+lastYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
				"AND X.entstatus IN ('1')\n" + 
				"and ((x.enttype in ('4000','4100','4110','4200','4210','4300','4310','4311','4312','4313','4400','4410',\n" + 
				"                           '3000','3100','3101','3102','3103','3600','3700',\n" + 
				"                           '4120','4220','4320','4321','4322','4323','4420',\n" + 
				"                           '3200','3201','3202','3203',\n" + 
				"                           '2140','2213','2223',\n" + 
				"                           '3301','4600','4601','4602','4603','4604','4605','4606','4607','4608','4609','4611','4612','4613','4700','4701','4800','4330','4340',\n" + 
				"                           '3500','3501','3502','3503','3504','3505','3506','3507','3508','3509','3511','3512','3513','3300','3400',\n" + 
				"               '1140','1213','1223'))\n" + 
				"       or (x.enttype in ('1110','2110') and (substr(trim(x.specflag),8,1)='0' or trim(x.specflag) is null))\n" + 
				"       or (x.enttype in ('2000','2100','2120','2200','2210','2220','A000',\n" + 
				"                            '1000','1100','1120','1130','1152','1200','1210','1220','1230',\n" + 
				"                            '1300','2300') and substr(x.specflag,8,1)='0'))\n" + 
				"GROUP BY X.regorg_CN\n" + 
				") p\n" + 
				"where p.辖区=t.辖区";
		
		
		
		
		logger.debug("内资去年12月26到本期的\n" + sql);
		Query query = this.getSession().createSQLQuery(sql);
		query.setMaxResults(10);
		query.setFirstResult(0);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		System.out.println(list);
		return list;
	}

	// 内资累计的---有史以来到本期的
	public List getNeiZiAll(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年

		String currentYear = str + " 23:59:59";
		String lastYear = rightNow.get(Calendar.YEAR) + "-12-26 00:00:00";

		/*System.out.println(currentYear);
		System.out.println(lastYear);*/

		
		
		String sql ="with x as(\n" +
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
						"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"and ((x.enttype in ('4000','4100','4110','4200','4210','4300','4310','4311','4312','4313','4400','4410',\n" + 
						"                           '3000','3100','3101','3102','3103','3600','3700',\n" + 
						"                           '4120','4220','4320','4321','4322','4323','4420',\n" + 
						"                           '3200','3201','3202','3203',\n" + 
						"                           '2140','2213','2223',\n" + 
						"                           '3301','4600','4601','4602','4603','4604','4605','4606','4607','4608','4609','4611','4612','4613','4700','4701','4800','4330','4340',\n" + 
						"                           '3500','3501','3502','3503','3504','3505','3506','3507','3508','3509','3511','3512','3513','3300','3400',\n" + 
						"               '1140','1213','1223'))\n" + 
						"       or (x.enttype in ('1110','2110') and (substr(trim(x.specflag),8,1)='0' or trim(x.specflag) is null))\n" + 
						"       or (x.enttype in ('2000','2100','2120','2200','2210','2220','A000',\n" + 
						"                            '1000','1100','1120','1130','1152','1200','1210','1220','1230',\n" + 
						"                            '1300','2300') and substr(x.specflag,8,1)='0'))\n" + 
						"GROUP BY X.regorg_CN,X.admin_cn\n" + 
						"ORDER BY  COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN 辖区, count(*) 总数\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"and ((x.enttype in ('4000','4100','4110','4200','4210','4300','4310','4311','4312','4313','4400','4410',\n" + 
						"                           '3000','3100','3101','3102','3103','3600','3700',\n" + 
						"                           '4120','4220','4320','4321','4322','4323','4420',\n" + 
						"                           '3200','3201','3202','3203',\n" + 
						"                           '2140','2213','2223',\n" + 
						"                           '3301','4600','4601','4602','4603','4604','4605','4606','4607','4608','4609','4611','4612','4613','4700','4701','4800','4330','4340',\n" + 
						"                           '3500','3501','3502','3503','3504','3505','3506','3507','3508','3509','3511','3512','3513','3300','3400',\n" + 
						"               '1140','1213','1223'))\n" + 
						"       or (x.enttype in ('1110','2110') and (substr(trim(x.specflag),8,1)='0' or trim(x.specflag) is null))\n" + 
						"       or (x.enttype in ('2000','2100','2120','2200','2210','2220','A000',\n" + 
						"                            '1000','1100','1120','1130','1152','1200','1210','1220','1230',\n" + 
						"                            '1300','2300') and substr(x.specflag,8,1)='0'))\n" + 
						"GROUP BY X.regorg_CN\n" + 
						") p\n" + 
						"where p.辖区=t.辖区";
		
		
		//sql = "select * from  AA_TEST_DATA04";
		logger.debug("内资累计的---有史以来到本期的\n" + sql);
		Query query = this.getSession().createSQLQuery(sql);
		query.setMaxResults(10);
		query.setFirstResult(0);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();

		return list;

	}

	// 私营 去年12月26到本期的
	public List getSiYinJiDu(String time) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年

		String currentYear = str + " 23:59:59";
		String lastYear = rightNow.get(Calendar.YEAR) + "-12-26 00:00:00";

	/*	System.out.println(currentYear);
		System.out.println(lastYear);
		*/
	
		
		
		String sql ="with x as(\n" +
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
						"and (substr(x.enttype,1,2) in ('45','39')\n" + 
						"      or (x.enttype in ('1151','1212','1222','2130','2151','2152','2212','2222'))\n" + 
						"      or (x.enttype in ('1121','1122','1123','1153','1190','1211','1219','1221','1229','2121','2122','2123','2153','2190','2211','2219','2221','2229','1150','2160'))  --总局为内资，其中1150为新增归为其他私营有限责任公司\n" + 
						"      or (x.enttype in ('1110','2110') and substr(trim(x.specflag),8,1) <>'0')\n" + 
						"      or (x.enttype in ('1000','1100','1120','1130','1152','1200','1210','1220','1230','1300','2000','2100','2120','2200','2210','2220','2300','A000') and (substr(x.specflag,8,1)<>'0' or trim(x.specflag) is null))\n" + 
						"      )\n" + 
						"GROUP BY X.regorg_CN,X.admin_cn\n" + 
						"ORDER BY  COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN 辖区, count(*) 总数\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"and  X.ESTDATE>=TO_DATE('"+lastYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"and (substr(x.enttype,1,2) in ('45','39')\n" + 
						"      or (x.enttype in ('1151','1212','1222','2130','2151','2152','2212','2222'))\n" + 
						"      or (x.enttype in ('1121','1122','1123','1153','1190','1211','1219','1221','1229','2121','2122','2123','2153','2190','2211','2219','2221','2229','1150','2160'))  --总局为内资，其中1150为新增归为其他私营有限责任公司\n" + 
						"      or (x.enttype in ('1110','2110') and substr(trim(x.specflag),8,1) <>'0')\n" + 
						"      or (x.enttype in ('1000','1100','1120','1130','1152','1200','1210','1220','1230','1300','2000','2100','2120','2200','2210','2220','2300','A000') and (substr(x.specflag,8,1)<>'0' or trim(x.specflag) is null))\n" + 
						"      )\n" + 
						"GROUP BY X.regorg_CN\n" + 
						") p\n" + 
						"where p.辖区=t.辖区\n"; 

		logger.debug("私营 去年12月26到本期的\n" + sql);
		//sql = "select * from  AA_TEST_DATA04";
		Query query = this.getSession().createSQLQuery(sql);
		query.setMaxResults(10);
		query.setFirstResult(0);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		System.out.println(list);
		return list;
	}

	// 私营的---有史以来到本期的
	public List getSiYinAll(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年

		String currentYear = str + " 23:59:59";
		String lastYear = rightNow.get(Calendar.YEAR) + "-12-26 00:00:00";

	/*	System.out.println(currentYear);
		System.out.println(lastYear);*/
		
		
		String sql ="with x as(\n" +
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
						"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"and (substr(x.enttype,1,2) in ('45','39')\n" + 
						"      or (x.enttype in ('1151','1212','1222','2130','2151','2152','2212','2222'))\n" + 
						"      or (x.enttype in ('1121','1122','1123','1153','1190','1211','1219','1221','1229','2121','2122','2123','2153','2190','2211','2219','2221','2229','1150','2160'))  --总局为内资，其中1150为新增归为其他私营有限责任公司\n" + 
						"      or (x.enttype in ('1110','2110') and substr(trim(x.specflag),8,1) <>'0')\n" + 
						"      or (x.enttype in ('1000','1100','1120','1130','1152','1200','1210','1220','1230','1300','2000','2100','2120','2200','2210','2220','2300','A000') and (substr(x.specflag,8,1)<>'0' or trim(x.specflag) is null))\n" + 
						"      )\n" + 
						"GROUP BY X.regorg_CN,X.admin_cn\n" + 
						"ORDER BY  COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN 辖区, count(*) 总数\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"and (substr(x.enttype,1,2) in ('45','39')\n" + 
						"      or (x.enttype in ('1151','1212','1222','2130','2151','2152','2212','2222'))\n" + 
						"      or (x.enttype in ('1121','1122','1123','1153','1190','1211','1219','1221','1229','2121','2122','2123','2153','2190','2211','2219','2221','2229','1150','2160'))  --总局为内资，其中1150为新增归为其他私营有限责任公司\n" + 
						"      or (x.enttype in ('1110','2110') and substr(trim(x.specflag),8,1) <>'0')\n" + 
						"      or (x.enttype in ('1000','1100','1120','1130','1152','1200','1210','1220','1230','1300','2000','2100','2120','2200','2210','2220','2300','A000') and (substr(x.specflag,8,1)<>'0' or trim(x.specflag) is null))\n" + 
						"      )\n" + 
						"GROUP BY X.regorg_CN\n" + 
						") p\n" + 
						"where p.辖区=t.辖区\n"; 

		logger.debug("私营的---有史以来到本期的\n" + sql);
	//	sql = "select * from  AA_TEST_DATA04";
		Query query = this.getSession().createSQLQuery(sql);
		query.setMaxResults(10);
		query.setFirstResult(0);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();

		return list;

	}

	// 
	public List getWaiZiJiDu(String time) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年

		String currentYear = str + " 23:59:59";
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
						"and  X.ESTDATE>=TO_DATE('"+lastYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"and substr(x.enttype,1,1) in ('5','6','Y','W')\n" + 
						"GROUP BY X.regorg_CN,X.admin_cn\n" + 
						"ORDER BY  COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN 辖区, count(*) 总数\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"and  X.ESTDATE>=TO_DATE('"+lastYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"and substr(x.enttype,1,1) in ('5','6','Y','W')\n" + 
						"GROUP BY X.regorg_CN\n" + 
						") p\n" + 
						"where p.辖区=t.辖区";

		
		
		logger.debug("外资 去年12月26到本期的\n" + sql);
		//sql = "select * from  AA_TEST_DATA04";
		Query query = this.getSession().createSQLQuery(sql);
		query.setMaxResults(10);
		query.setFirstResult(0);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		System.out.println(list);
		return list;
	}

	//
	public List getWaiZiAll(String time) throws ParseException {
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年

		String currentYear = str + " 23:59:59";
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
						"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"and substr(x.enttype,1,1) in ('5','6','Y','W')\n" + 
						"GROUP BY X.regorg_CN,X.admin_cn\n" + 
						"ORDER BY  COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN 辖区, count(*) 总数\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE('"+currentYear+"','YYYY-MM-DD hh24:mi:ss')\n" + 
						"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\n" + 
						"AND X.entstatus IN ('1')\n" + 
						"and substr(x.enttype,1,1) in ('5','6','Y','W')\n" + 
						"GROUP BY X.regorg_CN\n" + 
						") p\n" + 
						"where p.辖区=t.辖区";

		logger.debug(" 外资 的---有史以来到本期的\n" + sql);
		//sql = "select * from  AA_TEST_DATA04";
		Query query = this.getSession().createSQLQuery(sql);
		query.setMaxResults(10);
		query.setFirstResult(0);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();

		return list;

	}

}
