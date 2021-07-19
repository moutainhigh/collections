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
public class Data02Service extends BaseDao{
	private static Logger logger = Logger.getLogger("s02"); // 获取logger实例
	
	
	public List getZongShu(String begTime,String endTime,boolean isBenQi) {
		
		
			String sql =  this.getQuerySQL(true);
			sql+=" select t.*,p.总数 from\n" + 
						"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
						"from x\n" + 
						"WHERE X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n" ;
						
		
						if(isBenQi) {
							sql+=" and  X.ESTDATE>=TO_DATE("+begTime+",'YYYY-MM-DD hh24:mi:ss')\n" ;
						}
		
		
						
						sql +=" AND X.entstatus IN ('1')\n" + 
						"GROUP BY X.regorg_CN,X.admin_cn\n" + 
						"ORDER BY  COUNT(*) DESC\n" + 
						") t,\n" + 
						"(select X.regorg_CN 辖区, count(*) 总数\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n" ;
						
						if(isBenQi) {
							sql+=" and  X.ESTDATE>=TO_DATE("+begTime+",'YYYY-MM-DD hh24:mi:ss')\n" ; 
						}
						
						sql+=" AND X.entstatus IN ('1') GROUP BY X.regorg_CN ) p where p.辖区=t.辖区";
						
		
		
		
		String wrapSql = " select 监管所  from ("+sql+") ";
						
		logger.debug(sql);
		
		Query query = this.getSession().createSQLQuery(wrapSql);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(0);
        query.setMaxResults(10);
		List list = query.list();
		
		
		System.out.println(list);
		return list;

	}
	
	
	
	
	
	
	
	
	public List getNeiZiQiYe(String begTime,String endTime,boolean isBenQi) {
		
		List jianGuanSuo = getZongShu(begTime,endTime,isBenQi);
		
		
		String sql = this.getQuerySQL(false);
		sql +=" select t.*,p.总数 from\n" + 
						"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n ";
		
						if(isBenQi) {
							sql += " and  X.ESTDATE>=TO_DATE("+begTime+",'YYYY-MM-DD hh24:mi:ss')\n"; 
						}
		
						
						sql +=" AND X.entstatus IN ('1')\n" + 
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
						"where X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n ";
						
						if(isBenQi) {
							sql += " and  X.ESTDATE>=TO_DATE("+begTime+",'YYYY-MM-DD hh24:mi:ss')\n"; 
						}
						
						sql +=" AND X.entstatus IN ('1')\n" + 
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
						"where p.辖区=t.辖区   \n" ;
						
						sql  = "select * from ( "+sql+" ) t where t.监管所 in( ";
						
						
						StringBuffer sbQueryParams = new StringBuffer();
						StringBuffer sbOrderByParams = new StringBuffer();
						if(jianGuanSuo!=null&&jianGuanSuo.size()>0) {
							
							for (int i = 0; i < jianGuanSuo.size(); i++) {
								Map map = (Map) jianGuanSuo.get(i);
								String jianGuanSuoName = (String) map.get("监管所");
								
								sbQueryParams.append(" '"+jianGuanSuoName+"',");
								
								
								sbOrderByParams.append(jianGuanSuoName + " ,");
							}
							
							
						}
						
						
						String sbQueryParamsStr = sbQueryParams.toString().substring(0, sbQueryParams.toString().length()-1);
						String sbOrderParamsStr = sbOrderByParams.toString().substring(0, sbOrderByParams.toString().length()-1);
								
								
						
						sql +=  sbQueryParamsStr + " ) \n";
						sql += " order by instr('"+sbOrderParamsStr+"', t.监管所) , 数量 desc";
		
		
		
	     logger.debug(sql);
		
		Query query = this.getSession().createSQLQuery(sql);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(0);
        query.setMaxResults(10);
		List list = query.list();
		
		System.out.println(list);
		
		return list;


		
	}
	
	
	
	
	
	
	
	public List getSiYingQiYe(String begTime,String endTime,boolean isBenQi) {
		
		List jianGuanSuo = getZongShu(begTime,endTime,isBenQi);
		
		
		String sql = this.getQuerySQL(false);
		
		sql +=  
				"select t.*,p.总数 from\n" +
						"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
						"from x\n" + 
						"where X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n ";
						if(isBenQi) {
							sql += " and  X.ESTDATE>=TO_DATE("+begTime+",'YYYY-MM-DD hh24:mi:ss')\n"; 
						}
						sql += " AND X.entstatus IN ('1')\n" + 
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
						"where X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n ";
						
						if(isBenQi) {
							sql += " and  X.ESTDATE>=TO_DATE("+begTime+",'YYYY-MM-DD hh24:mi:ss')\n"; 
						}
						
						
						
						sql +=" AND X.entstatus IN ('1')\n" + 
						"and (substr(x.enttype,1,2) in ('45','39')\n" + 
						"      or (x.enttype in ('1151','1212','1222','2130','2151','2152','2212','2222'))\n" + 
						"      or (x.enttype in ('1121','1122','1123','1153','1190','1211','1219','1221','1229','2121','2122','2123','2153','2190','2211','2219','2221','2229','1150','2160'))  --总局为内资，其中1150为新增归为其他私营有限责任公司\n" + 
						"      or (x.enttype in ('1110','2110') and substr(trim(x.specflag),8,1) <>'0')\n" + 
						"      or (x.enttype in ('1000','1100','1120','1130','1152','1200','1210','1220','1230','1300','2000','2100','2120','2200','2210','2220','2300','A000') and (substr(x.specflag,8,1)<>'0' or trim(x.specflag) is null))\n" + 
						"      )\n" + 
						"GROUP BY X.regorg_CN\n" + 
						") p\n" + 
						"where p.辖区=t.辖区 ";

		
						
						sql  = "select * from ( "+sql+" ) t where t.监管所 in( ";
						
						
						StringBuffer sbQueryParams = new StringBuffer();
						StringBuffer sbOrderByParams = new StringBuffer();
						if(jianGuanSuo!=null&&jianGuanSuo.size()>0) {
							
							for (int i = 0; i < jianGuanSuo.size(); i++) {
								Map map = (Map) jianGuanSuo.get(i);
								String jianGuanSuoName = (String) map.get("监管所");
								
								sbQueryParams.append(" '"+jianGuanSuoName+"',");
								
								
								sbOrderByParams.append(jianGuanSuoName + " ,");
							}
							
							
						}
						
						
						String sbQueryParamsStr = sbQueryParams.toString().substring(0, sbQueryParams.toString().length()-1);
						String sbOrderParamsStr = sbOrderByParams.toString().substring(0, sbOrderByParams.toString().length()-1);
								
								
						
						sql +=  sbQueryParamsStr + " ) \n";
						sql += " order by instr('"+sbOrderParamsStr+"', t.监管所) , 数量 desc";
		
		
		
	     logger.debug(sql);
		
		Query query = this.getSession().createSQLQuery(sql);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(0);
        query.setMaxResults(10);
		List list = query.list();
		
		System.out.println(list);
		
		return list;


		
	}
	
	
	
	
public List getWaiZiQiYe(String begTime,String endTime,boolean isBenQi) {
		
		List jianGuanSuo = getZongShu(begTime,endTime,isBenQi);
		
		
		String sql = this.getQuerySQL(false);
		
		sql +=  " select t.*,p.总数 from\n" +
			"(select X.regorg_CN 辖区,X.admin_cn 监管所,COUNT(*) 数量\n" + 
			"from x\n" + 
			"where X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n ";
			if(isBenQi) {
				sql += " and  X.ESTDATE>=TO_DATE("+begTime+",'YYYY-MM-DD hh24:mi:ss')\n"; 
			}
			sql +=" AND X.entstatus IN ('1')\n" + 
			"and substr(x.enttype,1,1) in ('5','6','Y','W')\n" + 
			"GROUP BY X.regorg_CN,X.admin_cn\n" + 
			"ORDER BY  COUNT(*) DESC\n" + 
			") t,\n" + 
			"(select X.regorg_CN 辖区, count(*) 总数\n" + 
			"from x\n" + 
			"where X.ESTDATE<=TO_DATE("+endTime+",'YYYY-MM-DD hh24:mi:ss')\n ";
			if(isBenQi) {
				sql += " and  X.ESTDATE>=TO_DATE("+begTime+",'YYYY-MM-DD hh24:mi:ss')\n"; 
			}
			sql +=" AND X.entstatus IN ('1')\n" + 
			"and substr(x.enttype,1,1) in ('5','6','Y','W')\n" + 
			"GROUP BY X.regorg_CN\n" + 
			") p\n" + 
			"where p.辖区=t.辖区";

						sql  = "select * from ( "+sql+" ) t where t.监管所 in( ";
						
						StringBuffer sbQueryParams = new StringBuffer();
						StringBuffer sbOrderByParams = new StringBuffer();
						if(jianGuanSuo!=null&&jianGuanSuo.size()>0) {
							
							for (int i = 0; i < jianGuanSuo.size(); i++) {
								Map map = (Map) jianGuanSuo.get(i);
								String jianGuanSuoName = (String) map.get("监管所");
								
								sbQueryParams.append(" '"+jianGuanSuoName+"',");
								
								
								sbOrderByParams.append(jianGuanSuoName + " ,");
							}
							
							
						}
						
						
						String sbQueryParamsStr = sbQueryParams.toString().substring(0, sbQueryParams.toString().length()-1);
						String sbOrderParamsStr = sbOrderByParams.toString().substring(0, sbOrderByParams.toString().length()-1);
								
								
						
						sql +=  sbQueryParamsStr + " ) \n";
						sql += " order by instr('"+sbOrderParamsStr+"', t.监管所) , 数量 desc";
		
		
		
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
		Data02Service ss = ac.getBean(Data02Service.class);
		String timeStr = "2019-03-25";

		
		
		Map<String,String> map = TransferTime.getStartTimeAndEndTimeByStr(timeStr);
		
		System.out.println(map);
		String begTime = map.get("今年年初时间");
		String endTime = map.get("选择查询截止时间");
		//ss.getNeiZiQiYe(begTime, endTime, true); //是否是 本期登记，true本期
		//ss.getSiYingQiYe(begTime, endTime, true);
		
		ss.getWaiZiQiYe(begTime, endTime, true);
	}
	
}
