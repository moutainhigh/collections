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
			"盐田局", "光明局", "坪山局", "龙华局", "大鹏局", "食药准入处" };

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
		if (beginTime == null || beginTime.length() == 0) {
			list.add(sdf.format(new Date()));
		} else {
			list.add(beginTime);
		}
		String sql =

		"--表1 各辖区持有效《食品经营许可证》主体分类统计表\n"
				+ "select  REPLACE(NVL(d.name, '合计'), 'NULL', '') AS 辖区,\n"
				+ "       count((case when c.jy like '%食品销售经营者%' and c.jy like '%商场超市%' then 1 else null end)) as 食品商场超市,\n"
				+ "       count((case when c.jy like '%食品销售经营者%' and  c.jy like '%便利店%' then 1 else null end)) as 食品便利店,\n"
				+ "       count((case when c.jy like '%食品销售经营者%' and c.jy like '%食品贸易商%' then 1 else null end)) as 食品贸易商,\n"
				+ "       count((case when c.jy like '%食品销售经营者%' and c.jy like '%酒类批发商%' then 1 else null end)) as 酒类批发商,\n"
				+ "       count((case when c.jy like '%食品销售经营者%' and c.jy like '%其他食品%' then 1 else null end)) as 食品其他,\n"
				+ "       count(case when c.jy like '%食品销售经营者%' then 1 else null end) as 食品合计,\n"
				+ "       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%大型餐馆%'then 1 else null end)) as 大型餐馆,\n"
				+ "       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%中型餐馆%'then 1 else null end)) as 中型餐馆,\n"
				+ "       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%小型餐馆%'then 1 else null end)) as 小型餐馆,\n"
				+ "       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%微小餐饮%'then 1 else null end)) as 微小餐饮,\n"
				+ "       count((case when c.jy like '%餐饮服务经营者%' and c.jy like '%饮品店%'then 1 else null end)) as 饮品店,\n"
				+ "       count((case when c.jy like '%餐饮服务经营者%' and (c.jy like '%中央厨房%' or c.jy like '%集体用餐%')then 1 else null end)) as 中央,\n"
				+ "       count((case when c.jy like '%餐饮服务经营者%' and   c.jy like '%其它餐饮%' then 1 else null end)) as 餐饮其他,\n"
				+ "       count((case when c.jy like '%餐饮服务经营者%' then 1 else null end)) as 餐饮合计,\n"
				+ "       count((case when c.jy like '%单位食堂%' and   (c.jy like '%学校%' or c.jy like '%幼儿园%' ) then 1 else null end)) as 学校食堂,\n"
				+ "       count((case when c.jy like '%单位食堂%' and   c.jy like '%机关%'  then 1 else null end)) as 机关食堂,\n"
				+ "       count((case when c.jy like '%单位食堂%' and   c.jy like '%其他单位%'  then 1 else null end)) as 其他单位食堂,\n"
				+ "       count((case when c.jy like '%单位食堂%' then 1 else null end)) as 食堂合计,\n"
				+ "       count(1) as 总计 from\n"
				+ "                (select  a.main_format_name jy,a.engage_place_county_code as quju\n"
				+ "                         from dc_dc.dc_food_LICENSE_INFO a\n"
				+ "                         where  a.engage_place_county_code is not null\n"
				+ "                          and a.validity_to >to_date(?,'yyyy-mm-dd')) c,\n"
				+ "                 (select a1.engage_place_county_code code,\n"
				+ "                       (case when a1.engage_place_county_code='440303' then '罗湖区'\n"
				+ "                             when a1.engage_place_county_code='440304' then '福田区'\n"
				+ "                             when a1.engage_place_county_code='440305' then '南山区'\n"
				+ "                             when a1.engage_place_county_code='440307' then '龙岗区'\n"
				+ "                             when a1.engage_place_county_code='440308' then '盐田区'\n"
				+ "                             when a1.engage_place_county_code='440309' then '光明新区'\n"
				+ "                             when a1.engage_place_county_code='440310' then '坪山新区'\n"
				+ "                             when a1.engage_place_county_code='440342' then '龙华新区'\n"
				+ "                             when a1.engage_place_county_code='440343' then '大鹏新区'\n"
				+ "                             when a1.engage_place_county_code='440306' then '宝安区'\n"
				+ "                             when a1.engage_place_county_code='440313' then '前海区' end) as name\n"
				+ "                             from dc_dc.dc_food_LICENSE_INFO a1 where a1.engage_place_county_code is not null\n"
				+ "                             group by a1.engage_place_county_code) d\n"
				+ "                where c.quju=d.code\n"
				+ "group by rollup(d.name)";

		try {
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
	public Map<String, Map> queryShiPinZhunRu(String beginTime, String endTime,
			int i, HttpServletRequest req) {
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		Map<String, Map> map = new HashMap<String, Map>();
		String yearBegin = beginTime.substring(0, 4) + "-01-01";
		list.add(yearBegin);
		list.add(endTime);
		list.add(beginTime);
		list.add(endTime);
		list.add(yearBegin);
		list.add(endTime);
		list.add(beginTime);
		list.add(endTime);
		String sql = "\n"
				+ "--表3  食药准入2017年3月份业务工作完成情况报表（食品）\n"
				+ "select b.单位,b.食品经营受理，b.食品经营办理,\n"
				+ "nvl(a.食品生产受理,0) 食品生产受理,nvl(a.食品生产办理,0) 食品生产办理 from\n"
				+ "--食品生产\n"
				+ "(select s.单位,\n"
				+ "sum(case when s.biz_type_code= 'FoodProductionLicence' then s.sl else 0 end )食品生产受理，\n"
				+ "sum(case when s.biz_type_code= 'FoodProductionLicence' and s.biz_state='Permit' then s.sl else 0 end) 食品生产办理 from\n"
				+ "(select accept_no,accept_time,biz_type_code,biz_state,infoflow_name,\n"
				+ "        (case when substr(t.sign_no,0,6)='440300'  or t.sign_no like '20%' then '食药准入处年'\n"
				+ "             when substr(t.sign_no,0,6)='440304'  then  '福田局年'\n"
				+ "             when substr(t.sign_no,0,6)='440303'  then '罗湖局年'\n"
				+ "             when substr(t.sign_no,0,6)='440305'  then '南山局年'\n"
				+ "             when substr(t.sign_no,0,6)='440308'  then '盐田局年'\n"
				+ "             when substr(t.sign_no,0,6)='440306'  then '宝安局年'\n"
				+ "             when substr(t.sign_no,0,6)='440307'  then '龙岗局年'\n"
				+ "             when substr(t.sign_no,0,6)='440309'  then '光明局年'\n"
				+ "             when substr(t.sign_no,0,6)='440310'  then '坪山局年'\n"
				+ "             when substr(t.sign_no,0,6)='440311'  then '龙华局年'\n"
				+ "             when substr(t.sign_no,0,6)='440312'  then '大鹏局年' end) as 单位 ,\n"
				+ "             count(1) as sl\n"
				+ "       from dc_g_giap_apply_base t\n"
				+ "       where biz_type_code  ='FoodProductionLicence'\n"
				+ "       and accept_no is not null\n"
				+ "       and accept_time is not null\n"
				+ "       and biz_type_code is not null\n"
				+ "       and biz_state is not null\n"
				+ "       and infoflow_name is not null\n"
				+ "       and sign_no is not null\n"
				+ "       and to_char(t.accept_time,'yyyy-mm-dd')>=?\n"
				+ "       and to_char(t.accept_time,'yyyy-mm-dd')<=?\n"
				+ "       group by accept_no,accept_time,biz_type_code,biz_state,infoflow_name,sign_no) s\n"
				+ "       group by s.单位\n"
				+ "union all\n"
				+ "select s.单位,sum(case when s.biz_type_code= 'FoodProductionLicence' then s.sl else 0 end )食品生产受理，\n"
				+ "sum(case when s.biz_type_code= 'FoodProductionLicence' and s.biz_state='Permit' then s.sl else 0 end) 食品生产办理 from\n"
				+ "(select accept_no,accept_time,biz_type_code,biz_state,infoflow_name,\n"
				+ "        (case when t.sign_no like '20%' or substr(t.sign_no,0,6)='440300'    then '食药准入处月'\n"
				+ "             when substr(t.sign_no,0,6)='440304'  then  '福田局月'\n"
				+ "             when substr(t.sign_no,0,6)='440303'  then '罗湖局月'\n"
				+ "             when substr(t.sign_no,0,6)='440305'  then '南山局月'\n"
				+ "             when substr(t.sign_no,0,6)='440308'  then '盐田局月'\n"
				+ "             when substr(t.sign_no,0,6)='440306'  then '宝安局月'\n"
				+ "             when substr(t.sign_no,0,6)='440307'  then '龙岗局月'\n"
				+ "             when substr(t.sign_no,0,6)='440309'  then '光明局月'\n"
				+ "             when substr(t.sign_no,0,6)='440310'  then '坪山局月'\n"
				+ "             when substr(t.sign_no,0,6)='440311'  then '龙华局月'\n"
				+ "             when substr(t.sign_no,0,6)='440312'  then '大鹏局月' end) as 单位 ,\n"
				+ "             count(1) as sl\n"
				+ "       from dc_g_giap_apply_base t\n"
				+ "       where biz_type_code  ='FoodProductionLicence'\n"
				+ "       and accept_no is not null\n"
				+ "       and accept_time is not null\n"
				+ "       and biz_type_code is not null\n"
				+ "       and biz_state is not null\n"
				+ "       and infoflow_name is not null\n"
				+ "       and sign_no is not null\n"
				+ "       and to_char(t.accept_time,'yyyy-mm-dd')>=?\n"
				+ "       and to_char(t.accept_time,'yyyy-mm-dd')<=?\n"
				+ "       group by accept_no,accept_time,biz_type_code,biz_state,infoflow_name,sign_no) s\n"
				+ "       group by s.单位)a,\n"
				+ "       ( ----食品经营\n"
				+ "        select s.单位,\n"
				+ "sum(case when s.biz_type_code= 'FoodLicence' then s.sl else 0 end) 食品经营受理，\n"
				+ "sum(case when s.biz_type_code= 'FoodLicence' and s.biz_state='Permit' then s.sl else 0 end) 食品经营办理 from\n"
				+ "(select accept_no,accept_time,biz_type_code,biz_state,infoflow_name,\n"
				+ "       (case when substr(t.sign_no,0,6)='440300'  or t.sign_no like '20%' then '食药准入处年'\n"
				+ "             when substr(t.sign_no,0,6)='440304'  then  '福田局年'\n"
				+ "             when substr(t.sign_no,0,6)='440303'  then '罗湖局年'\n"
				+ "             when substr(t.sign_no,0,6)='440305'  then '南山局年'\n"
				+ "             when substr(t.sign_no,0,6)='440308'  then '盐田局年'\n"
				+ "             when substr(t.sign_no,0,6)='440306'  then '宝安局年'\n"
				+ "             when substr(t.sign_no,0,6)='440307'  then '龙岗局年'\n"
				+ "             when substr(t.sign_no,0,6)='440309'  then '光明局年'\n"
				+ "             when substr(t.sign_no,0,6)='440310'  then '坪山局年'\n"
				+ "             when substr(t.sign_no,0,6)='440311'  then '龙华局年'\n"
				+ "             when substr(t.sign_no,0,6)='440312'  then '大鹏局年' end) as 单位 ,\n"
				+ "             count(1) as sl\n"
				+ "       from dc_g_giap_apply_base t\n"
				+ "       where biz_type_code in ('FoodLicence')\n"
				+ "       and accept_no is not null\n"
				+ "       and accept_time is not null\n"
				+ "       and biz_type_code is not null\n"
				+ "       and biz_state is not null\n"
				+ "       and infoflow_name is not null\n"
				+ "       and sign_no is not null\n"
				+ "       and (substr(sign_no,0,4)='4403' or sign_no like '20%')\n"
				+ "       and to_char(t.accept_time,'yyyy-mm-dd')>=?\n"
				+ "       and to_char(t.accept_time,'yyyy-mm-dd')<=?\n"
				+ "       group by accept_no,accept_time,biz_type_code,biz_state,infoflow_name,sign_no) s\n"
				+ "       group by s.单位\n"
				+ "union all\n"
				+ "select s.单位,\n"
				+ "sum(case when s.biz_type_code= 'FoodLicence' then s.sl else 0 end) 食品经营，\n"
				+ "sum(case when s.biz_type_code= 'FoodLicence' and s.biz_state='Permit' then s.sl else 0 end) 食品经营办理 from\n"
				+ "(select accept_no,accept_time,biz_type_code,biz_state,infoflow_name,\n"
				+ "       (case when t.sign_no like '20%' or substr(t.sign_no,0,6)='440300'    then '食药准入处月'\n"
				+ "             when substr(t.sign_no,0,6)='440304'  then  '福田局月'\n"
				+ "             when substr(t.sign_no,0,6)='440303'  then '罗湖局月'\n"
				+ "             when substr(t.sign_no,0,6)='440305'  then '南山局月'\n"
				+ "             when substr(t.sign_no,0,6)='440308'  then '盐田局月'\n"
				+ "             when substr(t.sign_no,0,6)='440306'  then '宝安局月'\n"
				+ "             when substr(t.sign_no,0,6)='440307'  then '龙岗局月'\n"
				+ "             when substr(t.sign_no,0,6)='440309'  then '光明局月'\n"
				+ "             when substr(t.sign_no,0,6)='440310'  then '坪山局月'\n"
				+ "             when substr(t.sign_no,0,6)='440311'  then '龙华局月'\n"
				+ "             when substr(t.sign_no,0,6)='440312'  then '大鹏局月' end) as 单位 ,\n"
				+ "             count(1) as sl\n"
				+ "       from dc_g_giap_apply_base t\n"
				+ "       where biz_type_code in ('FoodLicence')\n"
				+ "       and accept_no is not null\n"
				+ "       and accept_time is not null\n"
				+ "       and biz_type_code is not null\n"
				+ "      and biz_state is not null\n"
				+ "       and infoflow_name is not null\n"
				+ "       and sign_no is not null\n"
				+ "       and (substr(sign_no,0,4)='4403' or sign_no like '20%')\n"
				+ "       and to_char(t.accept_time,'yyyy-mm-dd')>=?\n"
				+ "       and to_char(t.accept_time,'yyyy-mm-dd')<=?\n"
				+ "       group by accept_no,accept_time,biz_type_code,biz_state,infoflow_name,sign_no) s\n"
				+ "       group by s.单位\n" + "       )b\n"
				+ "       where a.单位(+)=b.单位";
		try {
			list1 = dao.queryForList(sql, list);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		for (int j = 0, jj = list1.size(); j < jj; j++) {
			map.put(list1.get(j).get("单位").toString(), list1.get(j));
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
		}
		logop.logInfoYeWu("下载食药准入××××年××月份业务工作完成情况报表（食品）", "WDY",
				i == 1 ? "查看报表" : "下载报表", sql, beginTime + "," + endTime, req);
		return map;
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
