package com.ye.repo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ye.base.BaseDao;
import com.ye.constrant.Constrant;

@Repository
@Transactional
public class Data02Repo extends BaseDao{
	private static Logger s02_zs_nc = Logger.getLogger("s02_zs_nc"); // 获取logger实例
	private static Logger s02_zs_ysyl = Logger.getLogger("s02_zs_ysyl"); // 获取logger实例
	
	
	private static Logger s02_nz_nc = Logger.getLogger("s02_zs_nc"); // 获取logger实例
	private static Logger s02_nz_ysyl = Logger.getLogger("s02_zs_ysyl"); // 获取logger实例
	
	
	private static Logger s02_sy_nc = Logger.getLogger("s02_sy_nc"); // 获取logger实例
	private static Logger s02_sy_ysyl = Logger.getLogger("s02_sy_ysyl"); // 获取logger实例
	
	private static Logger s02_wz_nc = Logger.getLogger("s02_wz_nc"); // 获取logger实例
	private static Logger s02_wz_ysyl = Logger.getLogger("s02_wz_ysyl"); // 获取logger实例
	
	
	public List getJianGuanSuoPaiXuByZongShu(String month , boolean isNianChu) {
		
		String timeStr = month + "-25 23:59:59";

		String yearStr = month.split("-")[0];

		Integer lastYearStr = Integer.valueOf(yearStr) - 1;

		String defaultStartDate = lastYearStr + "-12-26 00:00:00";

		String begTime = defaultStartDate;

		String endTime = timeStr;

		String sql = this.getQuerySQL(true);
			sql+=" select t.*,p.总数 from\n" + 
						"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
						"from x\n" + 
						"WHERE X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" ;
						
		
						if(isNianChu) {
							sql+=" and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\n" ;
						}
		
		
						
						sql +=" AND X.entstatus IN ('1')\n" + 
						"GROUP BY X.regorg_CN,X.admin_cn\n" + 
						"ORDER BY  COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN 辖区, count(*) 总数\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\n" ;
						
						if(isNianChu) {
							sql+=" and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\n" ; 
						}
						
						sql+=" AND X.entstatus IN ('1') GROUP BY X.regorg_CN ) p where p.辖区=t.辖区";
						
		
		
		
		if(isNianChu) {
			s02_zs_nc.debug(sql);
		}else {
			s02_zs_ysyl.debug(sql);
		}
		
		
		
	
		
		Query query = this.getSession().createSQLQuery(sql);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(0);
        query.setMaxResults(10);
		List list = query.list();
		
		List ret = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Map dm = (Map) list.get(i);
			
			 ret.add(dm.get("监管所"));
		}
		
		System.out.println(list);
		System.out.println("排列出来的监管所===>" + ret);
		return ret;

	}
	
	
	/**
	 * 内资今年从一月到本月的数据
	 * @param month
	 * @return
	 */
	public List getNeiZiFromJanuary(String month,List orderList) {
	    String sql = this.getQuerySQL(false);
	    String timeStr = month + "-25 23:59:59";
	    
		

		String yearStr = month.split("-")[0];

		Integer lastYearStr = Integer.valueOf(yearStr) - 1;

		String defaultStartDate = lastYearStr + "-12-26 00:00:00";

		String begTime = defaultStartDate;

		String endTime = timeStr;

		 
		sql += "select t.*,p.总数 from\r\n" + 
				"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\r\n" + 
				"from x\r\n" + 
				"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and ((x.enttype in ('4000','4100','4110','4200','4210','4300','4310','4311','4312','4313','4400','4410',\r\n" + 
				"                           '3000','3100','3101','3102','3103','3600','3700',\r\n" + 
				"                           '4120','4220','4320','4321','4322','4323','4420',\r\n" + 
				"                           '3200','3201','3202','3203',\r\n" + 
				"                           '2140','2213','2223',\r\n" + 
				"                           '3301','4600','4601','4602','4603','4604','4605','4606','4607','4608','4609','4611','4612','4613','4700','4701','4800','4330','4340',\r\n" + 
				"                           '3500','3501','3502','3503','3504','3505','3506','3507','3508','3509','3511','3512','3513','3300','3400',\r\n" + 
				"               '1140','1213','1223'))\r\n" + 
				"       or (x.enttype in ('1110','2110') and (substr(trim(x.specflag),8,1)='0' or trim(x.specflag) is null)) \r\n" + 
				"       or (x.enttype in ('2000','2100','2120','2200','2210','2220','A000',\r\n" + 
				"                            '1000','1100','1120','1130','1152','1200','1210','1220','1230',\r\n" + 
				"                            '1300','2300') and substr(x.specflag,8,1)='0'))\r\n" + 
				"GROUP BY X.regorg_CN,X.admin_cn\r\n" + 
				"ORDER BY  COUNT(*) DESC\r\n" + 
				") t,\r\n" + 
				"(select X.regorg_CN 辖区, count(*) 总数\r\n" + 
				"from x\r\n" + 
				"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and ((x.enttype in ('4000','4100','4110','4200','4210','4300','4310','4311','4312','4313','4400','4410',\r\n" + 
				"                           '3000','3100','3101','3102','3103','3600','3700',\r\n" + 
				"                           '4120','4220','4320','4321','4322','4323','4420',\r\n" + 
				"                           '3200','3201','3202','3203',\r\n" + 
				"                           '2140','2213','2223',\r\n" + 
				"                           '3301','4600','4601','4602','4603','4604','4605','4606','4607','4608','4609','4611','4612','4613','4700','4701','4800','4330','4340',\r\n" + 
				"                           '3500','3501','3502','3503','3504','3505','3506','3507','3508','3509','3511','3512','3513','3300','3400',\r\n" + 
				"               '1140','1213','1223'))\r\n" + 
				"       or (x.enttype in ('1110','2110') and (substr(trim(x.specflag),8,1)='0' or trim(x.specflag) is null)) \r\n" + 
				"       or (x.enttype in ('2000','2100','2120','2200','2210','2220','A000',\r\n" + 
				"                            '1000','1100','1120','1130','1152','1200','1210','1220','1230',\r\n" + 
				"                            '1300','2300') and substr(x.specflag,8,1)='0'))\r\n" + 
				"GROUP BY X.regorg_CN\r\n" + 
				") p\r\n" + 
				"where p.辖区=t.辖区 ";
		
		
		
		
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		for (int i = 0; i < orderList.size(); i++) {
			if(i<orderList.size()-1) {
				sb.append("'"+orderList.get(i)+"',");
				sb2.append(orderList.get(i) +",");
			}else {
				sb.append("'"+orderList.get(i)+"'");
				
				sb2.append(orderList.get(i));
			}
			
		}
		
		
		String tempSql = " select * from ( " +sql + "  ) t where t.监管所 in (" +sb.toString()+")  order by instr('"+sb2.toString()+"', t.监管所) , 数量 desc ";
		
		
		s02_nz_nc.debug(tempSql);
		

		Query query = this.getSession().createSQLQuery(tempSql);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(Constrant.FIRST_PAGE);
        query.setMaxResults(Constrant.MAX_PAGE);
		List list = query.list();
		
		System.out.println(list);
		
		
		return list;
	}
	
	/**
	 *  * 内资有史以来
	 * @param month
	 * @return
	 */
	public List getNeiZiYouShiYiLai(String month,List orderListYouShiYiLai) {
		String timeStr = month + "-25 23:59:59";
		
		String endTime = timeStr;
		String sql = this.getQuerySQL(false);
		sql +=" select t.*,p.总数 from\r\n" + 
				"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\r\n" + 
				"from x\r\n" + 
				"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and ((x.enttype in ('4000','4100','4110','4200','4210','4300','4310','4311','4312','4313','4400','4410',\r\n" + 
				"                           '3000','3100','3101','3102','3103','3600','3700',\r\n" + 
				"                           '4120','4220','4320','4321','4322','4323','4420',\r\n" + 
				"                           '3200','3201','3202','3203',\r\n" + 
				"                           '2140','2213','2223',\r\n" + 
				"                           '3301','4600','4601','4602','4603','4604','4605','4606','4607','4608','4609','4611','4612','4613','4700','4701','4800','4330','4340',\r\n" + 
				"                           '3500','3501','3502','3503','3504','3505','3506','3507','3508','3509','3511','3512','3513','3300','3400',\r\n" + 
				"               '1140','1213','1223'))\r\n" + 
				"       or (x.enttype in ('1110','2110') and (substr(trim(x.specflag),8,1)='0' or trim(x.specflag) is null)) \r\n" + 
				"       or (x.enttype in ('2000','2100','2120','2200','2210','2220','A000',\r\n" + 
				"                            '1000','1100','1120','1130','1152','1200','1210','1220','1230',\r\n" + 
				"                            '1300','2300') and substr(x.specflag,8,1)='0'))\r\n" + 
				"GROUP BY X.regorg_CN,X.admin_cn\r\n" + 
				"ORDER BY  COUNT(*) DESC\r\n" + 
				") t,\r\n" + 
				"(select X.regorg_CN 辖区, count(*) 总数\r\n" + 
				"from x\r\n" + 
				"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and ((x.enttype in ('4000','4100','4110','4200','4210','4300','4310','4311','4312','4313','4400','4410',\r\n" + 
				"                           '3000','3100','3101','3102','3103','3600','3700',\r\n" + 
				"                           '4120','4220','4320','4321','4322','4323','4420',\r\n" + 
				"                           '3200','3201','3202','3203',\r\n" + 
				"                           '2140','2213','2223',\r\n" + 
				"                           '3301','4600','4601','4602','4603','4604','4605','4606','4607','4608','4609','4611','4612','4613','4700','4701','4800','4330','4340',\r\n" + 
				"                           '3500','3501','3502','3503','3504','3505','3506','3507','3508','3509','3511','3512','3513','3300','3400',\r\n" + 
				"               '1140','1213','1223'))\r\n" + 
				"       or (x.enttype in ('1110','2110') and (substr(trim(x.specflag),8,1)='0' or trim(x.specflag) is null)) \r\n" + 
				"       or (x.enttype in ('2000','2100','2120','2200','2210','2220','A000',\r\n" + 
				"                            '1000','1100','1120','1130','1152','1200','1210','1220','1230',\r\n" + 
				"                            '1300','2300') and substr(x.specflag,8,1)='0'))\r\n" + 
				"GROUP BY X.regorg_CN\r\n" + 
				") p\r\n" + 
				"where p.辖区=t.辖区 ";
		
		
		
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		for (int i = 0; i < orderListYouShiYiLai.size(); i++) {
			if(i<orderListYouShiYiLai.size()-1) {
				sb.append("'"+orderListYouShiYiLai.get(i)+"',");
				sb2.append(orderListYouShiYiLai.get(i) +",");
			}else {
				sb.append("'"+orderListYouShiYiLai.get(i)+"'");
				
				sb2.append(orderListYouShiYiLai.get(i));
			}
			
		}
		
		
		String tempSql = " select * from ( " +sql + "  ) t where t.监管所 in (" +sb.toString()+")  order by instr('"+sb2.toString()+"', t.监管所) , 数量 desc ";
		
		
		s02_nz_ysyl.debug(tempSql);
		
		
		Query query = this.getSession().createSQLQuery(tempSql);
		
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(Constrant.FIRST_PAGE);
        query.setMaxResults(Constrant.MAX_PAGE);
		List list = query.list();
		
		System.out.println(list);
		
		
		return list;
	}
	
	
	
	
	
	/**
	 * 私营今年从一月到本月的数据
	 * @param month
	 * @return
	 */
	public List getSiYingFromJanuary(String month,List orderList) {
	    String sql = this.getQuerySQL(false);
	    String timeStr = month + "-25 23:59:59";
	    
		String yearStr = month.split("-")[0];

		Integer lastYearStr = Integer.valueOf(yearStr) - 1;

		String defaultStartDate = lastYearStr + "-12-26 00:00:00";

		String begTime = defaultStartDate;

		String endTime = timeStr;
		
		
		
		sql +=  " select t.*,p.总数 from\r\n" + 
				"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\r\n" + 
				"from x\r\n" + 
				"WHERE X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and (substr(x.enttype,1,2) in ('45','39')\r\n" + 
				"      or (x.enttype in ('1151','1212','1222','2130','2151','2152','2212','2222'))\r\n" + 
				"      or (x.enttype in ('1121','1122','1123','1153','1190','1211','1219','1221','1229','2121','2122','2123','2153','2190','2211','2219','2221','2229','1150','2160'))  --总局为内资，其中1150为新增归为其他私营有限责任公司\r\n" + 
				"      or (x.enttype in ('1110','2110') and substr(trim(x.specflag),8,1) <>'0')\r\n" + 
				"      or (x.enttype in ('1000','1100','1120','1130','1152','1200','1210','1220','1230','1300','2000','2100','2120','2200','2210','2220','2300','A000') and (substr(x.specflag,8,1)<>'0' or trim(x.specflag) is null))\r\n" + 
				"      )\r\n" + 
				"GROUP BY X.regorg_CN,X.admin_cn\r\n" + 
				"ORDER BY  COUNT(*) DESC\r\n" + 
				") t,\r\n" + 
				"(select X.regorg_CN 辖区, count(*) 总数\r\n" + 
				"from x\r\n" + 
				"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and (substr(x.enttype,1,2) in ('45','39')\r\n" + 
				"      or (x.enttype in ('1151','1212','1222','2130','2151','2152','2212','2222'))\r\n" + 
				"      or (x.enttype in ('1121','1122','1123','1153','1190','1211','1219','1221','1229','2121','2122','2123','2153','2190','2211','2219','2221','2229','1150','2160'))  --总局为内资，其中1150为新增归为其他私营有限责任公司\r\n" + 
				"      or (x.enttype in ('1110','2110') and substr(trim(x.specflag),8,1) <>'0')\r\n" + 
				"      or (x.enttype in ('1000','1100','1120','1130','1152','1200','1210','1220','1230','1300','2000','2100','2120','2200','2210','2220','2300','A000') and (substr(x.specflag,8,1)<>'0' or trim(x.specflag) is null))\r\n" + 
				"      )\r\n" + 
				"GROUP BY X.regorg_CN\r\n" + 
				") p\r\n" + 
				"where p.辖区=t.辖区 ";
		
		
			 
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		for (int i = 0; i < orderList.size(); i++) {
			if(i<orderList.size()-1) {
				sb.append("'"+orderList.get(i)+"',");
				sb2.append(orderList.get(i) +",");
			}else {
				sb.append("'"+orderList.get(i)+"'");
				
				sb2.append(orderList.get(i));
			}
			
		}
		
		
		String tempSql = " select * from ( " +sql + "  ) t where t.监管所 in (" +sb.toString()+")  order by instr('"+sb2.toString()+"', t.监管所) , 数量 desc ";
		
		
		s02_sy_nc.debug(tempSql);
		
		
		Query query = this.getSession().createSQLQuery(tempSql);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(Constrant.FIRST_PAGE);
        query.setMaxResults(Constrant.MAX_PAGE);
		List list = query.list();
		
		System.out.println(list);
		
		
		return list;
	}
	
	/**
	 *  * 私营有史以来
	 * @param month
	 * @return
	 */
	public List getSiYingYouShiYiLai(String month,List orderListYouShiYiLai) {
		String timeStr = month + "-25 23:59:59";
		
		String endTime = timeStr;
		String sql = this.getQuerySQL(false);
		
		
		sql +=  " select t.*,p.总数 from\r\n" + 
				"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\r\n" + 
				"from x\r\n" + 
				"WHERE X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and (substr(x.enttype,1,2) in ('45','39')\r\n" + 
				"      or (x.enttype in ('1151','1212','1222','2130','2151','2152','2212','2222'))\r\n" + 
				"      or (x.enttype in ('1121','1122','1123','1153','1190','1211','1219','1221','1229','2121','2122','2123','2153','2190','2211','2219','2221','2229','1150','2160'))  --总局为内资，其中1150为新增归为其他私营有限责任公司\r\n" + 
				"      or (x.enttype in ('1110','2110') and substr(trim(x.specflag),8,1) <>'0')\r\n" + 
				"      or (x.enttype in ('1000','1100','1120','1130','1152','1200','1210','1220','1230','1300','2000','2100','2120','2200','2210','2220','2300','A000') and (substr(x.specflag,8,1)<>'0' or trim(x.specflag) is null))\r\n" + 
				"      )\r\n" + 
				"GROUP BY X.regorg_CN,X.admin_cn\r\n" + 
				"ORDER BY  COUNT(*) DESC\r\n" + 
				") t,\r\n" + 
				"(select X.regorg_CN 辖区, count(*) 总数\r\n" + 
				"from x\r\n" + 
				"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and (substr(x.enttype,1,2) in ('45','39')\r\n" + 
				"      or (x.enttype in ('1151','1212','1222','2130','2151','2152','2212','2222'))\r\n" + 
				"      or (x.enttype in ('1121','1122','1123','1153','1190','1211','1219','1221','1229','2121','2122','2123','2153','2190','2211','2219','2221','2229','1150','2160'))  --总局为内资，其中1150为新增归为其他私营有限责任公司\r\n" + 
				"      or (x.enttype in ('1110','2110') and substr(trim(x.specflag),8,1) <>'0')\r\n" + 
				"      or (x.enttype in ('1000','1100','1120','1130','1152','1200','1210','1220','1230','1300','2000','2100','2120','2200','2210','2220','2300','A000') and (substr(x.specflag,8,1)<>'0' or trim(x.specflag) is null))\r\n" + 
				"      )\r\n" + 
				"GROUP BY X.regorg_CN\r\n" + 
				") p\r\n" + 
				"where p.辖区=t.辖区 ";
		
		
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		for (int i = 0; i < orderListYouShiYiLai.size(); i++) {
			if(i<orderListYouShiYiLai.size()-1) {
				sb.append("'"+orderListYouShiYiLai.get(i)+"',");
				sb2.append(orderListYouShiYiLai.get(i) +",");
			}else {
				sb.append("'"+orderListYouShiYiLai.get(i)+"'");
				
				sb2.append(orderListYouShiYiLai.get(i));
			}
			
		}
		
		
		String tempSql = " select * from ( " +sql + "  ) t where t.监管所 in (" +sb.toString()+")  order by instr('"+sb2.toString()+"', t.监管所) , 数量 desc ";
		
		
		s02_sy_ysyl.debug(tempSql);
		
		
		
		Query query = this.getSession().createSQLQuery(tempSql);
		
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(Constrant.FIRST_PAGE);
        query.setMaxResults(Constrant.MAX_PAGE);
		List list = query.list();
		
		System.out.println(list);
		
		
		return list;
	}
	
	/**
	 * 外资今年从一月到本月的数据
	 * @param month
	 * @return
	 */
	public List getWaiZiFromJanuary(String month,List orderList) {
	    String sql = this.getQuerySQL(false);
	    String timeStr = month + "-25 23:59:59";
	    
		

		String yearStr = month.split("-")[0];

		Integer lastYearStr = Integer.valueOf(yearStr) - 1;

		String defaultStartDate = lastYearStr + "-12-26 00:00:00";

		String begTime = defaultStartDate;

		String endTime = timeStr;
		
		
		sql += "  select t.*,p.总数 from\r\n" + 
				"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\r\n" + 
				"from x\r\n" + 
				"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and substr(x.enttype,1,1) in ('5','6','Y','W')\r\n" + 
				"GROUP BY X.regorg_CN,X.admin_cn\r\n" + 
				"ORDER BY  COUNT(*) DESC\r\n" + 
				") t,\r\n" + 
				"(select X.regorg_CN 辖区, count(*) 总数\r\n" + 
				"from x\r\n" + 
				"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"and  X.ESTDATE>=TO_DATE('"+begTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and substr(x.enttype,1,1) in ('5','6','Y','W') \r\n" + 
				"GROUP BY X.regorg_CN\r\n" + 
				") p\r\n" + 
				"where p.辖区=t.辖区 ";
		
		
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		for (int i = 0; i < orderList.size(); i++) {
			if(i<orderList.size()-1) {
				sb.append("'"+orderList.get(i)+"',");
				sb2.append(orderList.get(i) +",");
			}else {
				sb.append("'"+orderList.get(i)+"'");
				
				sb2.append(orderList.get(i));
			}
			
		}
		
		
		String tempSql = " select * from ( " +sql + "  ) t where t.监管所 in (" +sb.toString()+")  order by instr('"+sb2.toString()+"', t.监管所) , 数量 desc ";
		
		
		s02_wz_nc.debug(tempSql);
		
		Query query = this.getSession().createSQLQuery(tempSql);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(Constrant.FIRST_PAGE);
        query.setMaxResults(Constrant.MAX_PAGE);
		List list = query.list();
		
		System.out.println(list);
		
		
		return list;
	}
	
	/**
	 *  * 外资有史以来
	 * @param month
	 * @return
	 */
	public List getWaiZiYouShiYiLai(String month,List orderListYouShiYiLai) {
		String timeStr = month + "-25 23:59:59";
		
		String endTime = timeStr;
		String sql = this.getQuerySQL(false);
		
		
		
		sql +="select t.*,p.总数 from\r\n" + 
				"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\r\n" + 
				"from x\r\n" + 
				"WHERE X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and substr(x.enttype,1,1) in ('5','6','Y','W')\r\n" + 
				"GROUP BY X.regorg_CN,X.admin_cn\r\n" + 
				"ORDER BY  COUNT(*) DESC\r\n" + 
				") t,\r\n" + 
				"(select X.regorg_CN 辖区, count(*) 总数\r\n" + 
				"from x\r\n" + 
				"where X.ESTDATE<=TO_DATE('"+endTime+"','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"--and  X.ESTDATE>=TO_DATE('2017-12-26 00:00:00','YYYY-MM-DD hh24:mi:ss')\r\n" + 
				"AND X.entstatus IN ('1')\r\n" + 
				"and substr(x.enttype,1,1) in ('5','6','Y','W') \r\n" + 
				"GROUP BY X.regorg_CN\r\n" + 
				") p\r\n" + 
				"where p.辖区=t.辖区  ";
		
		
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		for (int i = 0; i < orderListYouShiYiLai.size(); i++) {
			if(i<orderListYouShiYiLai.size()-1) {
				sb.append("'"+orderListYouShiYiLai.get(i)+"',");
				sb2.append(orderListYouShiYiLai.get(i) +",");
			}else {
				sb.append("'"+orderListYouShiYiLai.get(i)+"'");
				
				sb2.append(orderListYouShiYiLai.get(i));
			}
			
		}
		
		
		String tempSql = " select * from ( " +sql + "  ) t where t.监管所 in (" +sb.toString()+")  order by instr('"+sb2.toString()+"', t.监管所) , 数量 desc ";
		
		
		s02_wz_ysyl.debug(tempSql);
		
		
		Query query = this.getSession().createSQLQuery(tempSql);
		
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(Constrant.FIRST_PAGE);
        query.setMaxResults(Constrant.MAX_PAGE);
		List list = query.list();
		
		System.out.println(list);
		System.out.println("采集完成");
		
		return list;
	}
	

	public static void main(String[] args) throws ParseException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml") ;
		Data02Repo ss = ac.getBean(Data02Repo.class);
		String timeStr = "2019-06";

		//ss.getJianGuanSuoPaiXuByZongShu(timeStr,false);
		//List orderList = ss.getJianGuanSuoPaiXuByZongShu(timeStr,true);
		
		List orderListYouShiYiLai = ss.getJianGuanSuoPaiXuByZongShu(timeStr,false);
		
		//ss.getNeiZiYouShiYiLai(timeStr, orderListYouShiYiLai);
		
		ss.getWaiZiYouShiYiLai(timeStr, orderListYouShiYiLai);
		//ss.getSiYingYouShiYiLai(timeStr, orderListYouShiYiLai);
		
		
		
		
		//List orderList = ss.getJianGuanSuoPaiXuByZongShu(timeStr,true);
		
		//ss.getNeiZiFromJanuary(timeStr, orderList);
		
		//ss.getSiYingFromJanuary(timeStr, orderList);
		
		//ss.getWaiZiFromJanuary(timeStr, orderList);
		
	}
	
	
}
