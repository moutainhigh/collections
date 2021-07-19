package com.gwssi.report.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.report.balance.api.ReportModel;
import com.gwssi.report.balance.api.ReportSource;
import com.gwssi.report.model.TCognosReportBO;
import com.gwssi.report.util.LogOperation;

@Service
public class QueryFoodStuffServer extends BaseService implements ReportSource {
	IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
	LogOperation logop = new LogOperation();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String[] regs = new String[] { "罗湖局", "福田局", "南山局", "宝安局", "龙岗局",
			"盐田局", "光明局", "坪山局", "龙华局", "大鹏局","深汕监管局", "食药准入处" };

	@Override
	public ReportModel getReport(TCognosReportBO queryInfo) {
		// TODO Auto-generated method stub 继承的函数
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> queryShiPinCanYinXuKe(String beginTime, int i,
			HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		if (beginTime == null || beginTime.length() == 0) {
			list.add(sdf.format(new Date()));
			list.add(sdf.format(new Date()));
		} else {
			list.add(beginTime);
			list.add(beginTime);
		}
		String sql = "SELECT /*+ PARALLEL(8)*/ NVL(S.FJMC,'合计') AS 辖区,S.*　FROM\n"
				+ "(SELECT /*+ PARALLEL(8)*/\n"
				+ "       C2.FJMC,\n"
				+ "       COUNT(CASE  WHEN D.CATEGORY LIKE '特大型餐馆%' THEN 1 ELSE  NULL END) AS 特大型餐饮,\n"
				+ "       COUNT(CASE  WHEN D.CATEGORY LIKE '大型餐馆%' THEN  1   ELSE NULL END) AS 大型餐饮,\n"
				+ "       COUNT(CASE WHEN D.CATEGORY LIKE '%中型餐馆%' THEN  1 ELSE NULL  END) AS 中型餐饮,\n"
				+ "       COUNT(CASE WHEN D.CATEGORY LIKE '%小型餐馆%' THEN  1 ELSE NULL END) AS 小型餐饮,\n"
				+ "       COUNT(CASE  WHEN D.CATEGORY LIKE '%快餐店%' THEN  1  ELSE  NULL END) AS 快餐店,\n"
				+ "       COUNT(CASE  WHEN D.CATEGORY LIKE '%小吃店%' THEN 1  ELSE  NULL  END) AS 小吃店,\n"
				+ "       COUNT(CASE  WHEN D.CATEGORY LIKE '%饮品店%' THEN  1  ELSE  NULL  END) AS 饮品店,\n"
				+ "       COUNT(CASE  WHEN D.CATEGORY LIKE '%食堂%' THEN  1 ELSE  NULL  END) AS 食堂,\n"
				+ "       COUNT(CASE  WHEN D.CATEGORY LIKE '%集体用餐配送单位%' THEN  1 ELSE  NULL  END) AS 集体用餐配送单位,\n"
				+ "       COUNT(CASE  WHEN D.CATEGORY LIKE '%中央厨房%' THEN  1 ELSE NULL END) AS 中央厨房,\n"
				+ "       COUNT(CASE  WHEN D.CATEGORY = '11' THEN  1  ELSE NULL END) AS 食品流通,\n"
				+ "       COUNT(CASE WHEN D.CATEGORY <> '11' THEN  1 ELSE  NULL  END) AS 合计,\n"
				+ "       count(1) as 总计\n"
				+ "  FROM (SELECT /*+ PARALLEL(8)*/ A1.PRIPID, '11' AS CATEGORY, '食品流通许可证' AS TYPE\n"
				+ "          FROM DC_F1_SPLT_ENT_BASIC_INFO A1\n"
				+ "         WHERE A1.VALID_UNTIL > to_date(?,'yyyy-mm-dd') \n"
				+ "        UNION ALL\n"
				+ "        SELECT /*+ PARALLEL(8)*/ A2.PRIPID, substr(A2.CATEGORY,4,500), '餐饮服务许可证' AS TYPE\n"
				+ "          FROM DC_F1_CYFW_ENT_BASIC_INFO A2\n"
				+ "         WHERE A2.TO_EFFECT_LIMIT > to_date(?,'yyyy-mm-dd')) D,\n"
				+ "       (SELECT /*+ PARALLEL(8)*/ DISTINCT ENTID, SUPUNITBYSUP\n"
				+ "          FROM DC_JG_MS_BA_ENT_ALL_INFO\n"
				+ "         WHERE SUPCODE = 'A100') B2,\n"
				+ "       (SELECT /*+ PARALLEL(8)*/ FJDM, FJMC FROM V_JG_ENT GROUP BY FJMC, FJDM) C2\n"
				+ " WHERE D.PRIPID = B2.ENTID(+)\n"
				+ "   AND B2.SUPUNITBYSUP = C2.FJDM\n"
				+ " GROUP BY /*D.TYPE,*/ rollup(C2.FJMC)) S\n"
				+ "   ORDER BY S.总计";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu("各辖区持有效《餐饮服务许可证》、《食品流通许可证》主体分类统计表", "WDY",
				i == 1 ? "查看报表" : "下载报表", sql, beginTime, req);
		return list1;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> queryShiPinXuKe(String beginTime, int i,
			HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		/*if (beginTime == null || beginTime.length() == 0) {
			list.add(sdf.format(new Date())+" 23:59:59");
		} else {
			list.add(beginTime+" 23:59:59");
		}*/
		
		list.add(sdf.format(new Date()));
		
		/*String sql=
				"--表1 各辖区持有效《食品经营许可证》主体分类统计表\n" +
						"select  REPLACE(NVL(c.quju, '合计'), 'NULL', '') AS 辖区,\n" + 
						"       count((case when c.jy like '%食品销售经营者%' and c.jy like '%商场超市%' then 1 else null end)) as 食品商场超市,\n" + 
						"       count((case when c.jy like '%食品销售经营者%' and  c.jy like '%便利店%' then 1 else null end)) as 食品便利店,\n" + 
						"       count((case when c.jy like '%食品销售经营者%' and c.jy like '%食品贸易商%' then 1 else null end)) as 食品贸易商,\n" + 
						"       count((case when c.jy like '%食品销售经营者%' and c.jy like '%酒类批发商%' then 1 else null end)) as 酒类批发商,\n" + 
						"       count((case when c.jy like '%食品销售经营者%' and c.jy like '%其他食品%' then 1 else null end)) as 食品其他,\n" + 
						"       count(case when c.jy like '%食品销售经营者%' then 1 else null end) as 食品合计,\n" + 
						"       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%大型餐馆%'then 1 else null end)) as 大型餐馆,\n" + 
						"       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%中型餐馆%'then 1 else null end)) as 中型餐馆,\n" + 
						"       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%小型餐馆%'then 1 else null end)) as 小型餐馆,\n" + 
						"       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%微小餐饮%'then 1 else null end)) as 微小餐饮,\n" + 
						"       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%饮品店%'then 1 else null end)) as 饮品店,\n" + 
						"       count((case when c.jy like '%餐饮服务经营者%' and (c.jy like '%中央厨房%' or c.jy like '%集体用餐%')then 1 else null end)) as 中央,\n" + 
						"       count((case when c.jy like '%餐饮服务经营者%' and   c.jy like '%其它餐饮%' then 1 else null end)) as 餐饮其他,\n" + 
						"       count((case when c.jy like '%餐饮服务经营者%' then 1 else null end)) as 餐饮合计,\n" + 
						"       count((case when c.jy like '%单位食堂%' and   (c.jy like '%学校%' or c.jy like '%幼儿园%' ) then 1 else null end)) as 学校食堂,\n" + 
						"       count((case when c.jy like '%单位食堂%' and   c.jy like '%机关%'  then 1 else null end)) as 机关食堂,\n" + 
						"       count((case when c.jy like '%单位食堂%' and   c.jy like '%其他单位%'  then 1 else null end)) as 其他单位食堂,\n" + 
						"       count((case when c.jy like '%单位食堂%' then 1 else null end)) as 食堂合计,\n" + 
						"       count(1) as 总计 from\n" + 
						"                (select  a.main_format_name jy,a.engage_place_county_name as quju\n" + 
						"                         from dc_dc.dc_food_LICENSE_INFO a\n" + 
						"                         where  a.engage_place_county_code is not null\n" + 
						"                         and a.engage_place_county_name is not null\n" + 
						"                          and a.validity_to >to_date(?,'yyyy-mm-dd hh24:mi:ss')\n" + 
						"                           ) c\n" + 
						"group by rollup(c.quju)";*/
		
		
		/*String sql = " select  REPLACE(NVL(c.quju, '合计'), 'NULL', '') AS 辖区,\r\n" + 
				"       count((case when c.jy like '%食品销售经营者%' and c.jy like '%商场超市%' then 1 else null end)) as 食品商场超市,\r\n" + 
				"       count((case when c.jy like '%食品销售经营者%' and  c.jy like '%便利店%' then 1 else null end)) as 食品便利店,\r\n" + 
				"       count((case when c.jy like '%食品销售经营者%' and c.jy like '%食品贸易商%' then 1 else null end)) as 食品贸易商,\r\n" + 
				"       count((case when c.jy like '%食品销售经营者%' and c.jy like '%酒类批发商%' then 1 else null end)) as 酒类批发商,\r\n" + 
				"       count((case when c.jy like '%食品销售经营者%' and c.jy like '%其他食品%' then 1 else null end)) as 食品其他,\r\n" + 
				"       count(case when c.jy like '%食品销售经营者%' then 1 else null end) as 食品合计,\r\n" + 
				"       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%大型餐馆%'then 1 else null end)) as 大型餐馆,\r\n" + 
				"       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%中型餐馆%'then 1 else null end)) as 中型餐馆,\r\n" + 
				"       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%小型餐馆%'then 1 else null end)) as 小型餐馆,\r\n" + 
				"       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%微小餐饮%'then 1 else null end)) as 微小餐饮,\r\n" + 
				"       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%饮品店%'then 1 else null end)) as 饮品店,\r\n" + 
				"       count((case when c.jy like '%餐饮服务经营者%' and (c.jy like '%中央厨房%' or c.jy like '%集体用餐%')then 1 else null end)) as 中央,\r\n" + 
				"       count((case when c.jy like '%餐饮服务经营者%' and   c.jy like '%其它餐饮%' then 1 else null end)) as 餐饮其他,\r\n" + 
				"       count((case when c.jy like '%餐饮服务经营者%' then 1 else null end)) as 餐饮合计,\r\n" + 
				"       count((case when c.jy like '%单位食堂%' and   (c.jy like '%学校%' or c.jy like '%幼儿园%' ) then 1 else null end)) as 学校食堂,\r\n" + 
				"       count((case when c.jy like '%单位食堂%' and   c.jy like '%机关%'  then 1 else null end)) as 机关食堂,\r\n" + 
				"       count((case when c.jy like '%单位食堂%' and   c.jy like '%其他单位%'  then 1 else null end)) as 其他单位食堂,\r\n" + 
				"       count((case when c.jy like '%单位食堂%' then 1 else null end)) as 食堂合计,\r\n" + 
				"       count(1) as 总计 from\r\n" + 
				"                (select  a.main_format_name jy,a.engage_place_county_name as quju\r\n" + 
				"                         from dc_dc.dc_food_LICENSE_INFO a,DC_FOOD_ENT_INFO c\r\n" + 
				"                         where  a.engage_place_county_code is not null\r\n" + 
				"                         and a.engage_place_county_name is not null\r\n" + 
				"                          and a.validity_to >  to_date(?,'yyyy-mm-dd hh24:mi:ss') \r\n" + 
				"                          and a.LICENSE_TYPE ='1' \r\n" + 
				"                          and a.license_state = 10  \r\n" + 
				"                          and C.company_state = 10 \r\n" + 
				"                          and A.MAIN_TB_ID = C.ID\r\n" + 
				"                          AND A.LICENSE_NO IS NOT NULL\r\n" + 
				"                           ) c\r\n" + 
				"group by rollup(c.quju)";*/
		
		
		String sql   = 
				"select T.DEPCODE AS 辖区,\n" +
						"       count((case when T.MAIN_FORMAT like '%1%' and T.MAIN_FORMAT_NAME like '%商场超市%' then 1 else null end)) as 食品商场超市,\n" + 
						"       count((case when T.MAIN_FORMAT like '%1%' and  T.MAIN_FORMAT_NAME like '%便利店%' then 1 else null end)) as 食品便利店,\n" + 
						"       count((case when T.MAIN_FORMAT like '%1%' and T.MAIN_FORMAT_NAME like '%食品贸易商%' then 1 else null end)) as 食品贸易商,\n" + 
						"       count((case when T.MAIN_FORMAT like '%1%' and T.MAIN_FORMAT_NAME like '%酒类批发商%' then 1 else null end)) as 酒类批发商,\n" + 
						"       count((case when T.MAIN_FORMAT like '%1%' and T.MAIN_FORMAT_NAME like '%其他食品%' then 1 else null end)) as 食品其他,\n" + 
						"       count(case when T.MAIN_FORMAT like '%1%' then 1 else null end) as 食品合计,\n" + 
						"       count((case when T.MAIN_FORMAT like '%2%' and T.MAIN_FORMAT_NAME like '%大型餐馆%'then 1 else null end)) as 大型餐馆,\n" + 
						"       count((case when T.MAIN_FORMAT like '%2%' and T.MAIN_FORMAT_NAME like '%中型餐馆%'then 1 else null end)) as 中型餐馆,\n" + 
						"       count((case when T.MAIN_FORMAT like '%2%' and T.MAIN_FORMAT_NAME like '%小型餐馆%'then 1 else null end)) as 小型餐馆,\n" + 
						"       count((case when T.MAIN_FORMAT like '%2%' and T.MAIN_FORMAT_NAME like '%微小餐饮%'then 1 else null end)) as 微小餐饮,\n" + 
						"       count((case when T.MAIN_FORMAT like '%2%' and T.MAIN_FORMAT_NAME like '%饮品店%'then 1 else null end)) as 饮品店,\n" + 
						"       count((case when T.MAIN_FORMAT like '%2%' and (T.MAIN_FORMAT_NAME like '%中央厨房%' or T.MAIN_FORMAT_NAME like '%集体用餐%')then 1 else null end)) as 中央,\n" + 
						"       count((case when T.MAIN_FORMAT like '%2%' and   T.MAIN_FORMAT_NAME like '%其它餐饮%' then 1 else null end)) as 餐饮其他,\n" + 
						"       count((case when T.MAIN_FORMAT like '%2%' then 1 else null end)) as 餐饮合计,\n" + 
						"       count((case when T.MAIN_FORMAT like '%3%' and   (T.MAIN_FORMAT_NAME like '%学校%' or T.MAIN_FORMAT_NAME like '%幼儿园%' ) then 1 else null end)) as 学校食堂,\n" + 
						"       count((case when T.MAIN_FORMAT like '%3%' and   T.MAIN_FORMAT_NAME like '%机关%'  then 1 else null end)) as 机关食堂,\n" + 
						"       count((case when T.MAIN_FORMAT like '%3%' and   T.MAIN_FORMAT_NAME like '%其他单位%'  then 1 else null end)) as 其他单位食堂,\n" + 
						"       count((case when T.MAIN_FORMAT like '%3%' then 1 else null end)) as 食堂合计,\n" + 
						"       count(1) as 总计\n" + 
						"  FROM (SELECT NVL(SUPUNITBYSUP_CN, '未认领') AS DEPCODE,MAIN_FORMAT,MAIN_FORMAT_NAME FROM V_FOOD_LICENSE WHERE NEWFLAG = '1' AND LICTYPE = '1' and VALID_DATE >= ? ) T\n" + 
						"group by rollup(T.DEPCODE)";
		
		
		
		try {
			System.out.println(sql);
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		logop.logInfoYeWu(
				"各辖区持有效《食品经营许可证》主体分类统计表",
				"WDY",
				i == 1 ? "查看报表" : "下载报表",
				sql,
				beginTime == null || beginTime.length() == 0 ? sdf
						.format(new Date()) : beginTime, req);
		return list1;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public Map  queryShiPinZhunRu(String beginTime, String endTime,
			int i, HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> jingYingList = new ArrayList<Map>();
		List<Map>  shengChangList =new ArrayList<Map>();
		Map<String, Map> map = new HashMap<String, Map>();
		String yearBegin = beginTime.substring(0, 4) + "-01-01 00:00:00";
		
		Map retMap = new HashMap();
		
		
		
		beginTime  = beginTime + " 00:00:00";
		endTime = endTime + " 00:00:00";
		
		list.add(beginTime);
		list.add(endTime);
		
		list.add(beginTime);
		list.add(endTime);
		
		list.add(yearBegin);
		list.add(endTime);
		
		list.add(yearBegin);
		list.add(endTime);


		/*String sql  = 

					"SELECT S.SUBMIT_ACCEPT_CORPNAME AS 单位,\n" +
					"       CASE WHEN S.NY_FLAG = '1' THEN '当月数' ELSE '本年度累计' END AS NY_FLAG, -- 1 ：代表月，2：代表年累计\n" + 
					"       SUM(S.SCSL_CNT) AS 食品生产受理,\n" + 
					"       SUM(S.SCBL_CNT) AS 食品生产办理,\n" + 
					"       SUM(S.JYSL_CNT) AS 食品经营受理,\n" + 
					"       SUM(S.JYBL_CNT) AS 食品经营办理 \n"+

						"  FROM (SELECT T.BIZ_TYPE_CODE,\n" + 
						"               '1' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodProductionLicence' THEN COUNT(1) ELSE 0 END AS SCSL_CNT, --食品生产许可证受理数\n" + 
						"               0 AS SCBL_CNT, --食品生产许可证办理数\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodLicence' THEN COUNT(1) ELSE 0 END AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               0 AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodLicence', 'FoodProductionLicence')\n" + 
						"           AND T.ACCEPT_TIME >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.ACCEPT_TIME <=  TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.BIZ_STATE NOT IN ('DraftOut','DraftIn','WaitSign','WainingSign','WaitAccept')\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"        UNION ALL\n" + 
						"        SELECT T.BIZ_TYPE_CODE,\n" + 
						"               '1' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               0 AS SCSL_CNT, --食品生产许可证受理数\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodProductionLicence' THEN COUNT(1) ELSE 0 END AS SCBL_CNT, --食品生产许可证办理数\n" + 
						"               0 AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodLicence' THEN COUNT(1) ELSE 0 END AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodLicence', 'FoodProductionLicence')\n" + 
						"           AND T.FINISH_TIME >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.FINISH_TIME <= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.IS_FINISH = '1'\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"         UNION ALL\n" + 
						"         SELECT T.BIZ_TYPE_CODE,\n" + 
						"                '2' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodProductionLicence' THEN COUNT(1) ELSE 0 END AS SCSL_CNT, --食品生产许可证受理数\n" + 
						"               0 AS SCBL_CNT, --食品生产许可证办理数\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodLicence' THEN COUNT(1) ELSE 0 END AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               0 AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodLicence', 'FoodProductionLicence')\n" + 
						"           AND T.ACCEPT_TIME >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.ACCEPT_TIME <= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.BIZ_STATE NOT IN ('DraftOut','DraftIn','WaitSign','WainingSign','WaitAccept')\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"        UNION ALL\n" + 
						"        SELECT T.BIZ_TYPE_CODE,\n" + 
						"               '2' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               0 AS SCSL_CNT, --食品生产许可证受理数\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodProductionLicence' THEN COUNT(1) ELSE 0 END AS SCBL_CNT, --食品生产许可证办理数\n" + 
						"               0 AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodLicence' THEN COUNT(1) ELSE 0 END AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodLicence', 'FoodProductionLicence')\n" + 
						"           AND T.FINISH_TIME >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.FINISH_TIME <= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.IS_FINISH = '1'\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						

						"UNION ALL\n" +
						"        SELECT T.BIZ_TYPE_CODE,\n" + 
						"               '1' AS NY_FLAG,\n" + 
						"              NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"              0 AS SCSL_CNT, --食品生产许可证受理数\n" + 
						"              0 AS SCBL_CNT, --食品生产许可证办理数\n" + 
						"              0 AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"              0 AS JYBL_CNT --食品经营许可证办理数\n" + 
						"         FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"        WHERE T.BIZ_TYPE_CODE IN ('FoodLicence', 'FoodProductionLicence')\n" + 
						"          AND T.BIZ_STATE NOT IN ('DraftOut','DraftIn','WaitSign','WainingSign','WaitAccept')\n" + 
						"        GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"       UNION ALL\n" + 
						"       SELECT T.BIZ_TYPE_CODE,\n" + 
						"              '2' AS NY_FLAG,\n" + 
						"              NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"              0 AS SCSL_CNT, --食品生产许可证受理数\n" + 
						"              0 AS SCBL_CNT, --食品生产许可证办理数\n" + 
						"              0 AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"              0 AS JYBL_CNT --食品经营许可证办理数\n" + 
						"         FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"        WHERE T.BIZ_TYPE_CODE IN ('FoodLicence', 'FoodProductionLicence')\n" + 
						"          AND T.IS_FINISH = '1'\n" + 
						"        GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME"+
						"         ) S\n" + 
						" GROUP BY S.SUBMIT_ACCEPT_CORPNAME,S.NY_FLAG\n" + 
						" ORDER BY S.SUBMIT_ACCEPT_CORPNAME,S.NY_FLAG ";*/

		////经营
		
		String sql = 
				"SELECT D.PARENT_DEP_NAME AS 辖区,\n" +
						"       S.SUBMIT_ACCEPT_CORPNAME AS 单位,\n" + 
						"       CASE WHEN S.NY_FLAG = '1' THEN '当月数' ELSE '本年度累计' END AS NY_FLAG, -- 1 ：代表月，2：代表年累计\n" + 
						"       SUM(S.JYSL_CNT) AS 食品经营受理,\n" + 
						"       SUM(S.JYBL_CNT) AS 食品经营办理\n" + 
						"  FROM (SELECT T.BIZ_TYPE_CODE,\n" + 
						"               '1' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodLicence' THEN COUNT(1) ELSE 0 END AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               0 AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodLicence')\n" + 
						"           AND T.ACCEPT_TIME >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.ACCEPT_TIME <=  TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.BIZ_STATE NOT IN ('DraftOut','DraftIn','WaitSign','WainingSign','WaitAccept')\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"        UNION ALL\n" + 
						"        SELECT T.BIZ_TYPE_CODE,\n" + 
						"               '1' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               0 AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodLicence' THEN COUNT(1) ELSE 0 END AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodLicence')\n" + 
						"           AND T.FINISH_TIME >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.FINISH_TIME <= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.IS_FINISH = '1'\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"         UNION ALL\n" + 
						"         SELECT T.BIZ_TYPE_CODE,\n" + 
						"                '2' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodLicence' THEN COUNT(1) ELSE 0 END AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               0 AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodLicence')\n" + 
						"           AND T.ACCEPT_TIME >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.ACCEPT_TIME <= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.BIZ_STATE NOT IN ('DraftOut','DraftIn','WaitSign','WainingSign','WaitAccept')\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"        UNION ALL\n" + 
						"        SELECT T.BIZ_TYPE_CODE,\n" + 
						"               '2' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               0 AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodLicence' THEN COUNT(1) ELSE 0 END AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodLicence')\n" + 
						"           AND T.FINISH_TIME >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.FINISH_TIME <= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.IS_FINISH = '1'\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"        UNION ALL\n" + 
						"         SELECT T.BIZ_TYPE_CODE,\n" + 
						"                '1' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               0 AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               0 AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodLicence')\n" + 
						"           --AND T.BIZ_STATE NOT IN ('DraftOut','DraftIn','WaitSign','WainingSign','WaitAccept')\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"        UNION ALL\n" + 
						"        SELECT T.BIZ_TYPE_CODE,\n" + 
						"               '2' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               0 AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               0 AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodLicence')\n" + 
						"           --AND T.IS_FINISH = '1'\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"         ) S,DC_GIAP_APPLY_BASE_CODE D\n" + 
						"WHERE S.SUBMIT_ACCEPT_CORPNAME = D.DEP_NAME\n" + 
						" GROUP BY D.PARENT_DEP_NAME,S.SUBMIT_ACCEPT_CORPNAME,S.NY_FLAG\n" + 
						" ORDER BY D.PARENT_DEP_NAME,S.SUBMIT_ACCEPT_CORPNAME,S.NY_FLAG ";

		
		
		
		
		
		
		
		String sql2 =  
				"SELECT D.PARENT_DEP_NAME AS 辖区,\n" +
						"       S.SUBMIT_ACCEPT_CORPNAME AS 单位,\n" + 
						"       CASE WHEN S.NY_FLAG = '1' THEN '当月数' ELSE '本年度累计' END AS NY_FLAG, -- 1 ：代表月，2：代表年累计\n" + 
						"       SUM(S.JYSL_CNT) AS 食品生产受理,\n" + 
						"       SUM(S.JYBL_CNT) AS 食品生产办理\n" + 
						"  FROM (SELECT T.BIZ_TYPE_CODE,\n" + 
						"               '1' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodProductionLicence' THEN COUNT(1) ELSE 0 END AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               0 AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodProductionLicence')\n" + 
						"           AND T.ACCEPT_TIME >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.ACCEPT_TIME <=  TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.BIZ_STATE NOT IN ('DraftOut','DraftIn','WaitSign','WainingSign','WaitAccept')\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"        UNION ALL\n" + 
						"        SELECT T.BIZ_TYPE_CODE,\n" + 
						"               '1' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               0 AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodProductionLicence' THEN COUNT(1) ELSE 0 END AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodProductionLicence')\n" + 
						"           AND T.FINISH_TIME >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.FINISH_TIME <= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.IS_FINISH = '1'\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"         UNION ALL\n" + 
						"         SELECT T.BIZ_TYPE_CODE,\n" + 
						"                '2' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodProductionLicence' THEN COUNT(1) ELSE 0 END AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               0 AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodProductionLicence')\n" + 
						"           AND T.ACCEPT_TIME >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.ACCEPT_TIME <= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.BIZ_STATE NOT IN ('DraftOut','DraftIn','WaitSign','WainingSign','WaitAccept')\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"        UNION ALL\n" + 
						"        SELECT T.BIZ_TYPE_CODE,\n" + 
						"               '2' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               0 AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               CASE WHEN T.BIZ_TYPE_CODE = 'FoodProductionLicence' THEN COUNT(1) ELSE 0 END AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodProductionLicence')\n" + 
						"           AND T.FINISH_TIME >= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.FINISH_TIME <= TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n" + 
						"           AND T.IS_FINISH = '1'\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"        UNION ALL\n" + 
						"         SELECT T.BIZ_TYPE_CODE,\n" + 
						"                '1' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               0 AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               0 AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodProductionLicence')\n" + 
						"           --AND T.BIZ_STATE NOT IN ('DraftOut','DraftIn','WaitSign','WainingSign','WaitAccept')\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"        UNION ALL\n" + 
						"        SELECT T.BIZ_TYPE_CODE,\n" + 
						"               '2' AS NY_FLAG,\n" + 
						"               NVL(T.SUBMIT_ACCEPT_CORPNAME, '未认领') AS SUBMIT_ACCEPT_CORPNAME, --提交受理单位名称\n" + 
						"               0 AS JYSL_CNT, --食品经营许可证受理数\n" + 
						"               0 AS JYBL_CNT --食品经营许可证办理数\n" + 
						"          FROM DC_GIAP_APPLY_BASE_VIEW T\n" + 
						"         WHERE T.BIZ_TYPE_CODE IN ('FoodProductionLicence')\n" + 
						"           --AND T.IS_FINISH = '1'\n" + 
						"         GROUP BY T.BIZ_TYPE_CODE, T.SUBMIT_ACCEPT_CORPNAME\n" + 
						"         ) S,DC_GIAP_APPLY_BASE_CODE D\n" + 
						"WHERE S.SUBMIT_ACCEPT_CORPNAME = D.DEP_NAME\n" + 
						" GROUP BY D.PARENT_DEP_NAME,S.SUBMIT_ACCEPT_CORPNAME,S.NY_FLAG\n" + 
						" ORDER BY D.PARENT_DEP_NAME,S.SUBMIT_ACCEPT_CORPNAME,S.NY_FLAG";

		
		
	
		
		try {
			System.out.println(sql);
			
			jingYingList = dao.queryForList(sql, list);
			shengChangList =  dao.queryForList(sql2, list);
			
			
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		
		
		
		retMap.put("shengChangList", shengChangList);
		retMap.put("jingYingList", jingYingList);
		
		
		
	/*	for (int j = 0, jj = list1.size(); j < jj; j++) {
			String key = null;
			Map val =null;
			System.out.println(list1);
			if(list1.get(j)!=null&&list1.get(j).get("单位")!=null) {
				key = list1.get(j).get("单位").toString();
				val = list1.get(j);
				map.put(key, val);
			}
			
		}
		for (int j = 0; j < regs.length; j++) {
			if (map.get(regs[j] + "年") == null) {
				map.put(regs[j] + "年", new HashMap<String, String>() {
					{
						put("食品经营受理", "0");
						put("食品经营办理", "0");
						put("食品生产受理", "0");
						put("食品生产办理", "0");
					}
				});
			}
			if (map.get(regs[j] + "月") == null) {
				map.put(regs[j] + "月", new HashMap<String, String>() {
					{
						put("食品经营受理", "0");
						put("食品经营办理", "0");
						put("食品生产受理", "0");
						put("食品生产办理", "0");
					}
				});
			}
		}*/
		logop.logInfoYeWu("下载食药准入××××年××月份业务工作完成情况报表（食品）", "WDY",
				i == 1 ? "查看报表" : "下载报表", sql, beginTime + "," + endTime, req);
		return retMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> queryShiPinShenCha(String beginTime, String endTime,
			int i, HttpServletRequest req) {
		IPersistenceDAO dao1 = this.getPersistenceDAO("dc_dc");
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		list.add(beginTime);
		list.add(endTime);
		String sql = "select tt.organ_name as 部门,\n"
				+ "      round(\n"
				+ "            sum(case when tt.inspect_result in ('1','2') then ROUND(TO_NUMBER(tt.inspect_time-tt.create_time)*tt.num) else 0 end)/\n"
				+ "                  (case when sum(case when tt.inspect_result in ('1','2') then tt.num else 0 end)=0 then 1 else sum(case when tt.inspect_result in ('1','2') then tt.num else 0 end)end)\n"
				+ "            ) as 首次审查工作时效,\n"
				+ "      round(\n"
				+ "            sum( case when tt.assign_type = '1' and tt.inspect_result = '4' then ROUND(TO_NUMBER(tt.record_time-tt.assign_time)*tt.num) else 0 end )/\n"
				+ "              (case when sum(case when  tt.assign_type = '1' and tt.inspect_result = '4' then tt.num else 0 end)=0 then 1 else  sum(case when  tt.assign_type = '1' and tt.inspect_result = '4' then tt.num else 0 end) end)\n"
				+ "            )as 复验审查工作时效,\n"
				+ "       round(\n"
				+ "            sum(case when tt.inspect_result = '4' and tt.inspect_time is not null then  ROUND(TO_NUMBER(to_date(tt.up_time,'yyyy-mm-dd hh244:mi:ss')-tt.fuendtime)*tt.num) when tt.inspect_result in ('1','2') then ROUND(TO_NUMBER(to_date(tt.up_time,'yyyy-mm-dd hh24:mi:ss')-tt.inspect_time)*tt.num) else 0 end)/\n"
				+ "           (case when sum(case when (tt.inspect_result = '4' and tt.inspect_time is not null)or (tt.inspect_result in ('1','2')) then tt.num else 0 end)=0 then 1 else\n"
				+ "                 sum(case when (tt.inspect_result = '4' and tt.inspect_time is not null)or (tt.inspect_result in ('1','2')) then tt.num else 0 end) end)\n"
				+ "            ) as 照片上传工作时效,\n"
				+ "       round((case when sum(tt.num)=0 then 0 else\n"
				+ "             ((sum(tt.num)-sum(case when tt.instance_state='1' then tt.num else 0 end))/sum(tt.num)) end),4)*100||'%' as 整改率，\n"
				+ "       round((case when sum(tt.num)=0 then 0 else\n"
				+ "             ( sum(case when tt.instance_state in ('1','4') then tt.num else 0 end)/sum(tt.num))end ),4)*100 as 通过率,\n"
				+ "       sum(case when tt.instance_state in ('1','4') and tt.zhuti like '%大型%' then tt.num else 0 end ) as 大型,\n"
				+ "        sum(case when tt.instance_state in ('1','4') and tt.zhuti like '%中型%' then  tt.num  else 0 end ) as 中型,\n"
				+ "        sum(case when tt.instance_state in ('1','4') and tt.zhuti like '%食品销售%' then tt.num else 0 end ) as 食品销售,\n"
				+ "        sum(case when tt.instance_state in ('1','4') and (tt.zhuti like '%饮品%' or tt.zhuti like '%糕点%') then tt.num else 0 end ) as 饮品店糕点店\n"
				+ "       -- sum(case when tt.instance_state='1' then tt.num else 0 end) as ceshi\n"
				+ "from\n"
				+ "(select s.create_time,\n"
				+ "        s.record_time,--创建时间,\n"
				+ "(case when s.inspect_result in ('1','2')then s.inspect_time end)  as inspect_time,--首次审查时间\n"
				+ "\n"
				+ "(case when s.assign_type = '1' then s.assign_time end ) as assign_time,--复审申请时间\n"
				+ "(case when s.inspect_result = '4' then s.inspect_time end ) as fuendtime,--复审结束时间\n"
				+ "s.up_time,--上传时间\n" + "s.inspect_result,\n" + "s.num,\n"
				+ "s.assign_type,\n" + "s.organ_name,\n"
				+ "s.instance_state,\n"
				+ "s.zhutiyetaifenleimingchen as zhuti\n" + "from\n"
				+ "(select\n" + "       i.create_time,\n"
				+ "       r.inspect_time,\n" + "       r.record_time,\n"
				+ "       a.assign_time,\n" + "       d.up_time,\n"
				+ "       r.inspect_result,\n" + "       a.assign_type,\n"
				+ "       o.organ_name,\n" + "       i.instance_state,\n"
				+ "       zhutiyetaifenleimingchen,\n"
				+ "       count(1) as num\n" + "  from\n"
				+ "dc_dc.dc_food_giap_inspect_instance  i\n" + "left join\n"
				+ " dc_dc.dc_food_giap_inspect_record    r\n"
				+ " on i.id = r.instance_id and r.inspect_result is not null\n"
				+ " left join dc_dc.dc_food_giap_inspect_assign a\n"
				+ " on i.id = a.instance_id and a.to_organ_type = 1\n"
				+ " left join dc_dc.dc_food_pub_stru                 t\n"
				+ " on t.organ_id = r.recorder_id\n"
				+ " left join dc_dc.dc_food_pub_organ                o\n"
				+ " on t.parent_id = o.organ_id\n"
				+ " left join dc_dc.dc_food_sub_giap_spjy_qyjbxx    q\n"
				+ " on q.wangshangshenqinghao = i.apply_no\n"
				+ " left join dc_dc.dc_food_sub_giap_spjy_xksqxx      x\n"
				+ " on x.main_tbl_pk = q.main_tbl_pk\n"
				+ " left join dc_dc.dc_food_gcloud_file_store_ext e\n"
				+ " on e.biz_key = i.id\n"
				+ " left join dc_dc.dc_food_doc_info              d\n"
				+ " on e.store_id = d.store_id\n" + " where\n"
				+ " o.organ_name like '%审查%'\n"
				+ "   and to_char(i.create_time, 'yyyy-mm-dd') >= ?\n"
				+ "   and to_char(i.create_time, 'yyyy-mm-dd') <= ?\n"
				+ "  group by i.create_time,\n" + "       r.inspect_time,\n"
				+ "       r.record_time,\n" + "       a.assign_time,\n"
				+ "       d.up_time,\n" + "       r.inspect_result,\n"
				+ "       a.assign_type,\n" + "       o.organ_name,\n"
				+ "       i.instance_state,\n"
				+ "       zhutiyetaifenleimingchen\n" + ") s\n" + ")tt\n"
				+ "group by tt.organ_name";
		try {
			list1 = dao1.queryForList(sql, list);
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logop.logInfoYeWu("食品审查", "WDY", i == 1 ? "查看报表" : "下载报表", sql,
				beginTime + "," + endTime, req);
		return list1;
	}
}
