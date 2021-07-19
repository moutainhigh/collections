package com.ye.base;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


public class BaseDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	//sessionFactory要关闭吗?
	//链接池---sessionFactory---->
	public Session getSession() {
		if (sessionFactory != null) {
			//return sessionFactory.openSession(); //一定手动关闭session
			//了解区分一下openSession getCurrentSession的区别场景,get load
			return sessionFactory.getCurrentSession();//必须要结合事务才有意义
		}
		return null;
	}

	
	
	public String getQuerySQLForTable1() {
		String sql ="\n with x as(\n" +
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
						" from dc_ra_mer_base_query b,\n" + 
						"     dc_ra_mer_base_ext e\n" + 
						" where b.pripid=e.main_tb_id\n" + 
						" )";

		

		
		
		return sql;
	}
	
	
	
	public String getQuerySQL(boolean isTotal) {
		String sql = "\n with x as(\n" +
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
						"and b.adminbrancode=c.SYS_RIGHT_DEPARTMENT_ID ";
		
				
			if(isTotal) {
				sql +="and substr(b.enttype,0,2)<>'95' ";
			}
			sql+= " )";

		return sql;
	}
}
