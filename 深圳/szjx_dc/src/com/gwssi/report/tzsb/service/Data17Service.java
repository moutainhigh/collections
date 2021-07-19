package com.gwssi.report.tzsb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class Data17Service extends BaseService {
	private static Logger log = Logger.getLogger(Data17Service.class);


	public List queryListZaiYong(Map map) throws OptimusException {

		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();// 参数准备
		List resuts = null;
		
		
		String sql = 
				"SELECT\n" +
						"    NVL(S.EQU_ADDR_COUNTY_CODE,'合计') AS 项目,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器合计,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) - SUM(S.CNT0) AS 非简单压力容器,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '电梯' THEN S.CNT ELSE 0 END) AS 电梯,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场车,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 游乐设施,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道,\n" + 
						"    SUM(S.CNT) AS 合计\n" + 
						"  FROM (SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,\n" + 
						"               T.ITEM_VALUE,\n" + 
						"               COUNT(P.ID) AS CNT0,\n" + 
						"               COUNT(1) AS CNT\n" + 
						"          FROM DC_SE_EQUIPMENT S,\n" + 
						"               (SELECT *\n" + 
						"                  FROM DC_SE_EQU_CONTAINER_PARAM\n" + 
						"                 WHERE IS_SIMPLE_CONTAINER = '1') P,\n" + 
						"               (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
						"                  FROM DC_SC_DICT_ITEM T\n" + 
						"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,\n" + 
						"               (SELECT C.ENTID,\n" + 
						"                       C.SUPUNITBYSUP,\n" + 
						"                       C.SUPDEPBYSUP,\n" + 
						"                       C.GRIDBYSUP,\n" + 
						"                       U.DEP_NAME,--局\n" + 
						"                       --U1.DEP_NAME,--所\n" + 
						"                       G.GRIDNAME\n" + 
						"                  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\n" + 
						"                       DC_JG_SYS_RIGHT_DEPARTMENT U,\n" + 
						"                       DC_JG_SYS_RIGHT_DEPARTMENT U1,\n" + 
						"                       DC_JG_MS_BA_GRID           G\n" + 
						"                 WHERE C.SUPCODE = 'B100'\n" + 
						"                   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\n" + 
						"                   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\n" + 
						"                   AND G.GRID(+) = C.GRIDBYSUP) C\n" + 
						"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"           AND S.STATUS = '1'\n" + 
						"           AND S.ID = P.EQU_ID(+)\n" + 
						"           AND S.REG_CODE = C.ENTID(+)\n" + 
						"         GROUP BY C.DEP_NAME, T.ITEM_VALUE\n" + 
						"\n" + 
						"        ) S\n" + 
						" GROUP BY ROLLUP(S.EQU_ADDR_COUNTY_CODE)";

		
		
		
		sql  = "select * from ("+sql +") t order by instr('福田局,罗湖局,南山局,盐田局,宝安局,龙岗局,光明局,坪山局,龙华局,大鹏局,深汕监管局,待分区域,合计',t.项目)";
				

		
		if(map.get("regCode")!=null&&(!map.get("regCode").equals(""))) {
			params.add((String) map.get("regCode"));
			
			
			sql = 
					"SELECT\n" +
							"    NVL(S.EQU_ADDR_COUNTY_CODE,'合计') AS 项目,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器合计,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) - SUM(S.CNT0) AS 非简单压力容器,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '电梯' THEN S.CNT ELSE 0 END) AS 电梯,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场车,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 游乐设施,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道,\n" + 
							"    SUM(S.CNT) AS 合计\n" + 
							"  FROM (SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,\n" + 
							"               T.ITEM_VALUE,\n" + 
							"               COUNT(P.ID) AS CNT0,\n" + 
							"               COUNT(1) AS CNT\n" + 
							"          FROM DC_SE_EQUIPMENT S,\n" + 
							"               (SELECT *\n" + 
							"                  FROM DC_SE_EQU_CONTAINER_PARAM\n" + 
							"                 WHERE IS_SIMPLE_CONTAINER = '1') P,\n" + 
							"               (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
							"                  FROM DC_SC_DICT_ITEM T\n" + 
							"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,\n" + 
							"               (SELECT C.ENTID,\n" + 
							"                       C.SUPUNITBYSUP,\n" + 
							"                       C.SUPDEPBYSUP,\n" + 
							"                       C.GRIDBYSUP,\n" + 
							"                       --U.DEP_NAME,--局\n" + 
							"                       U1.DEP_NAME,--所\n" + 
							"                       G.GRIDNAME\n" + 
							"                  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\n" + 
							"                       DC_JG_SYS_RIGHT_DEPARTMENT U,\n" + 
							"                       DC_JG_SYS_RIGHT_DEPARTMENT U1,\n" + 
							"                       DC_JG_MS_BA_GRID           G\n" + 
							"                 WHERE C.SUPCODE = 'B100'\n" + 
							"                   ANd U.Dep_Name =? \n" + 
							"                   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\n" + 
							"                   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\n" + 
							"                   AND G.GRID(+) = C.GRIDBYSUP) C\n" + 
							"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
							"           AND S.STATUS = '1'\n" + 
							"           AND S.ID = P.EQU_ID(+)\n" + 
							"           AND S.REG_CODE = C.ENTID\n" + 
							"         GROUP BY C.DEP_NAME, T.ITEM_VALUE\n" + 
							"\n" + 
							"        ) S\n" + 
							" GROUP BY ROLLUP(S.EQU_ADDR_COUNTY_CODE)";

			
			
			
			
			 if(map.get("adminbrancode")!=null&&(!map.get("adminbrancode").equals(""))) {
					String admicode = (String) map.get("adminbrancode");
					params.add(admicode);
					
					
					sql = "SELECT\n" +
									"    NVL(S.EQU_ADDR_COUNTY_CODE,'合计') AS 项目,\n" + 
									"    SUM(CASE WHEN S.ITEM_VALUE = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉,\n" + 
									"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器合计,\n" + 
									"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) - SUM(S.CNT0) AS 非简单压力容器,\n" + 
									"    SUM(CASE WHEN S.ITEM_VALUE = '电梯' THEN S.CNT ELSE 0 END) AS 电梯,\n" + 
									"    SUM(CASE WHEN S.ITEM_VALUE = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械,\n" + 
									"    SUM(CASE WHEN S.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场车,\n" + 
									"    SUM(CASE WHEN S.ITEM_VALUE = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 游乐设施,\n" + 
									"    SUM(CASE WHEN S.ITEM_VALUE = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道,\n" + 
									"    SUM(CASE WHEN S.ITEM_VALUE = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道,\n" + 
									"    SUM(S.CNT) AS 合计\n" + 
									"  FROM (SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,\n" + 
									"               T.ITEM_VALUE,\n" + 
									"               COUNT(P.ID) AS CNT0,\n" + 
									"               COUNT(1) AS CNT\n" + 
									"          FROM DC_SE_EQUIPMENT S,\n" + 
									"               (SELECT *\n" + 
									"                  FROM DC_SE_EQU_CONTAINER_PARAM\n" + 
									"                 WHERE IS_SIMPLE_CONTAINER = '1') P,\n" + 
									"               (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
									"                  FROM DC_SC_DICT_ITEM T\n" + 
									"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,\n" + 
									"               (SELECT C.ENTID,\n" + 
									"                       C.SUPUNITBYSUP,\n" + 
									"                       C.SUPDEPBYSUP,\n" + 
									"                       C.GRIDBYSUP,\n" + 
									"                      -- U.DEP_NAME,--局\n" + 
									"                      U1.DEP_NAME,--所\n" + 
									"                       G.GRIDNAME\n" + 
									"                  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\n" + 
									"                       DC_JG_SYS_RIGHT_DEPARTMENT U,\n" + 
									"                       DC_JG_SYS_RIGHT_DEPARTMENT U1,\n" + 
									"                       DC_JG_MS_BA_GRID           G\n" + 
									"                 WHERE C.SUPCODE = 'B100'\n" + 
									"                  ANd U.Dep_Name =? \n" + 
									"                   and U1.Dep_Name =? \n" + 
									"                   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\n" + 
									"                   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\n" + 
									"                   AND G.GRID(+) = C.GRIDBYSUP) C\n" + 
									"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
									"           AND S.STATUS = '1'\n" + 
									"           AND S.ID = P.EQU_ID(+)\n" + 
									"           AND S.REG_CODE = C.ENTID\n" + 
									"         GROUP BY C.DEP_NAME, T.ITEM_VALUE\n" + 
									"\n" + 
									"        ) S\n" + 
									" GROUP BY ROLLUP(S.EQU_ADDR_COUNTY_CODE)";

			 }
			
			
			
		}
		System.out.println(sql + "\n");
		return resuts = dao_dc.queryForList(sql, params);
	}
	
	
	
	
	
	
	
	
	
	public List queryListByDanWei(Map map) throws OptimusException {

		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();// 参数准备
		List resuts = null;
		
		String sql  = null;
		sql  = "WITH TMEP AS\n" + 
						"(SELECT C.ENTID,\n" + 
						"       C.SUPUNITBYSUP,\n" + 
						"       C.SUPDEPBYSUP,\n" + 
						"       C.GRIDBYSUP,\n" + 
						"       U.DEP_NAME,--局\n" + 
						"       --U1.DEP_NAME, --所\n" + 
						"       --U1.DEP_NAME AS SHOU_NAME,\n" + 
						"       G.GRIDNAME\n" + 
						"  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\n" + 
						"       DC_JG_SYS_RIGHT_DEPARTMENT U,\n" + 
						"       DC_JG_SYS_RIGHT_DEPARTMENT U1,\n" + 
						"       DC_JG_MS_BA_GRID           G\n" + 
						" WHERE C.SUPCODE = 'B100'\n" + 
						"   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\n" + 
						"   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\n" + 
						"   AND G.GRID(+) = C.GRIDBYSUP),\n" + 
						"TMP_P AS (SELECT * FROM DC_SE_EQU_CONTAINER_PARAM WHERE IS_SIMPLE_CONTAINER = '1'),\n" + 
						"TMP_T AS (SELECT T.ITEM_CODE, T.ITEM_VALUE FROM DC_SC_DICT_ITEM T WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND')\n" + 
						"SELECT\n" + 
						"    NVL(S.EQU_ADDR_COUNTY_CODE,'待分区域') AS 项目,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器合计,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) - SUM(S.CNT0) AS 非简单压力容器,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '电梯' THEN S.CNT ELSE 0 END) AS 电梯,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场车,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 游乐设施,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = 'NULL' THEN S.CNT1 ELSE 0 END) AS 合计\n" + 
						"  FROM (SELECT T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE, SUM(CNT0) AS CNT0, SUM(CNT1) AS CNT1,SUM(CNT) AS CNT\n" + 
						"          FROM (SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,\n" + 
						"                       T.ITEM_VALUE,\n" + 
						"                       0 AS CNT0,\n" + 
						"                       0 AS CNT1,\n" + 
						"                       1 AS CNT\n" + 
						"                  FROM DC_SE_EQUIPMENT S,\n" + 
						"                       TMP_P P,\n" + 
						"                       TMP_T T,\n" + 
						"                       TMEP  C\n" + 
						"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"                   AND S.ID = P.EQU_ID(+)\n" + 
						"                   AND  S.REG_CODE = C.ENTID(+)\n" + 
						"                   AND S.STATUS = '1'\n" + 
						"                 GROUP BY C.DEP_NAME, T.ITEM_VALUE, S.USE_UNIT_ID\n" + 
						"                UNION ALL\n" + 
						"                SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,\n" + 
						"                       T.ITEM_VALUE,\n" + 
						"                       1 AS CNT0,\n" + 
						"                       0 AS CNT1,\n" + 
						"                       0 AS CNT\n" + 
						"                  FROM DC_SE_EQUIPMENT S,\n" + 
						"                       TMP_P P,\n" + 
						"                       TMP_T T,\n" + 
						"                       TMEP  C\n" + 
						"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"                   AND S.ID = P.EQU_ID\n" + 
						"                   AND S.REG_CODE = C.ENTID(+)\n" + 
						"                   AND S.STATUS = '1'\n" + 
						"                 GROUP BY C.DEP_NAME, T.ITEM_VALUE, S.USE_UNIT_ID\n" + 
						"                UNION ALL --20190425\n" + 
						"                SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,\n" + 
						"                       'NULL' ITEM_VALUE,\n" + 
						"                       0 AS CNT0,\n" + 
						"                       1 AS CNT1,\n" + 
						"                       0 AS CNT\n" + 
						"                  FROM DC_SE_EQUIPMENT S,\n" + 
						"                       TMP_P P,\n" + 
						"                       TMP_T T,\n" + 
						"                       TMEP  C\n" + 
						"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"                   AND S.ID = P.EQU_ID(+)\n" + 
						"                   AND S.REG_CODE = C.ENTID(+)\n" + 
						"                   AND S.STATUS = '1'\n" + 
						"                 GROUP BY C.DEP_NAME, S.USE_UNIT_ID) T\n" + 
						"         GROUP BY T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE\n" + 
						"        ) S\n" + 
						" GROUP BY S.EQU_ADDR_COUNTY_CODE\n" + 
						" UNION ALL\n" + 
						" SELECT\n" + 
						"    NVL(S.EQU_ADDR_COUNTY_CODE,'合计') AS 项目,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器合计,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) - SUM(S.CNT0) AS 非简单压力容器,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '电梯' THEN S.CNT ELSE 0 END) AS 电梯,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场车,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 游乐设施,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道,\n" + 
						"    SUM(S.CNT1) AS 合计\n" + 
						"  FROM (SELECT T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE, SUM(CNT0) AS CNT0, SUM(CNT1) AS CNT1,SUM(CNT) AS CNT\n" + 
						"          FROM (SELECT '全市' AS EQU_ADDR_COUNTY_CODE,\n" + 
						"                       T.ITEM_VALUE,\n" + 
						"                       0 AS CNT0,\n" + 
						"                       0 AS CNT1,\n" + 
						"                       1 AS CNT\n" + 
						"                  FROM DC_SE_EQUIPMENT S,\n" + 
						"                       TMP_P P,\n" + 
						"                       TMP_T T,\n" + 
						"                       TMEP  C\n" + 
						"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"                   AND S.ID = P.EQU_ID(+)\n" + 
						"                   AND S.STATUS = '1'\n" + 
						"                   AND S.REG_CODE = C.ENTID(+)\n" + 
						"                 GROUP BY T.ITEM_VALUE, S.USE_UNIT_ID\n" + 
						"                UNION ALL\n" + 
						"                SELECT '全市' AS EQU_ADDR_COUNTY_CODE,\n" + 
						"                       T.ITEM_VALUE,\n" + 
						"                       1 AS CNT0,\n" + 
						"                       0 AS CNT1,\n" + 
						"                       0 AS CNT\n" + 
						"                  FROM DC_SE_EQUIPMENT S,\n" + 
						"                       TMP_P P,\n" + 
						"                       TMP_T T,\n" + 
						"                       TMEP  C\n" + 
						"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"                   AND S.ID = P.EQU_ID\n" + 
						"                   AND S.STATUS = '1'\n" + 
						"                   AND S.REG_CODE = C.ENTID(+)\n" + 
						"                 GROUP BY T.ITEM_VALUE, S.USE_UNIT_ID\n" + 
						"                 UNION ALL\n" + 
						"                 SELECT '全市' AS EQU_ADDR_COUNTY_CODE,\n" + 
						"                       'NULL' AS ITEM_VALUE,\n" + 
						"                       0 AS CNT0,\n" + 
						"                       1 AS CNT1,\n" + 
						"                       0 AS CNT\n" + 
						"                  FROM DC_SE_EQUIPMENT S,\n" + 
						"                       TMP_P P,\n" + 
						"                       TMP_T T,\n" + 
						"                       TMEP  C\n" + 
						"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"                   AND S.ID = P.EQU_ID(+)\n" + 
						"                   AND S.STATUS = '1'\n" + 
						"                   AND S.REG_CODE = C.ENTID(+)\n" + 
						"                 GROUP BY  S.USE_UNIT_ID) T\n" + 
						"         GROUP BY T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE\n" + 
						"        ) S\n" + 
						" GROUP BY S.EQU_ADDR_COUNTY_CODE ";

		
		
		
		sql  = "select * from ("+sql +") t order by instr('福田局,罗湖局,南山局,盐田局,宝安局,龙岗局,光明局,坪山局,龙华局,大鹏局,深汕监管局,待分区域,全市',t.项目)";
		
		if(map.get("regCode")!=null&&(!map.get("regCode").equals(""))) {
			params.add((String) map.get("regCode"));
			
			
		 
			sql = "WITH TMEP AS\n" +
							"(SELECT C.ENTID,\n" + 
							"       C.SUPUNITBYSUP,\n" + 
							"       C.SUPDEPBYSUP,\n" + 
							"       C.GRIDBYSUP,\n" + 
							"       --U.DEP_NAME,--局\n" + 
							"       U1.DEP_NAME, --所\n" + 
							"       --U1.DEP_NAME AS SHOU_NAME,\n" + 
							"       G.GRIDNAME\n" + 
							"  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\n" + 
							"       DC_JG_SYS_RIGHT_DEPARTMENT U,\n" + 
							"       DC_JG_SYS_RIGHT_DEPARTMENT U1,\n" + 
							"       DC_JG_MS_BA_GRID           G\n" + 
							" WHERE C.SUPCODE = 'B100'\n" + 
							"   AND U.DEP_NAME = ?  \n" + 
							"   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\n" + 
							"   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\n" + 
							"   AND G.GRID(+) = C.GRIDBYSUP),\n" + 
							"TMP_P AS (SELECT * FROM DC_SE_EQU_CONTAINER_PARAM WHERE IS_SIMPLE_CONTAINER = '1'),\n" + 
							"TMP_T AS (SELECT T.ITEM_CODE, T.ITEM_VALUE FROM DC_SC_DICT_ITEM T WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND')\n" + 
							"SELECT\n" + 
							"    NVL(S.EQU_ADDR_COUNTY_CODE,'待分区域') AS 项目,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器合计,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) - SUM(S.CNT0) AS 非简单压力容器,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '电梯' THEN S.CNT ELSE 0 END) AS 电梯,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场车,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 游乐设施,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = 'NULL' THEN S.CNT1 ELSE 0 END) AS 合计\n" + 
							"  FROM (SELECT T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE, SUM(CNT0) AS CNT0, SUM(CNT1) AS CNT1,SUM(CNT) AS CNT\n" + 
							"          FROM (SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,\n" + 
							"                       T.ITEM_VALUE,\n" + 
							"                       0 AS CNT0,\n" + 
							"                       0 AS CNT1,\n" + 
							"                       1 AS CNT\n" + 
							"                  FROM DC_SE_EQUIPMENT S,\n" + 
							"                       TMP_P P,\n" + 
							"                       TMP_T T,\n" + 
							"                       TMEP  C\n" + 
							"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
							"                   AND S.ID = P.EQU_ID(+)\n" + 
							"                   AND  S.REG_CODE = C.ENTID\n" + 
							"                   AND S.STATUS = '1'\n" + 
							"                 GROUP BY C.DEP_NAME, T.ITEM_VALUE, S.USE_UNIT_ID\n" + 
							"                UNION ALL\n" + 
							"                SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,\n" + 
							"                       T.ITEM_VALUE,\n" + 
							"                       1 AS CNT0,\n" + 
							"                       0 AS CNT1,\n" + 
							"                       0 AS CNT\n" + 
							"                  FROM DC_SE_EQUIPMENT S,\n" + 
							"                       TMP_P P,\n" + 
							"                       TMP_T T,\n" + 
							"                       TMEP  C\n" + 
							"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
							"                   AND S.ID = P.EQU_ID\n" + 
							"                   AND S.REG_CODE = C.ENTID\n" + 
							"                   AND S.STATUS = '1'\n" + 
							"                 GROUP BY C.DEP_NAME, T.ITEM_VALUE, S.USE_UNIT_ID\n" + 
							"                UNION ALL --20190425\n" + 
							"                SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,\n" + 
							"                       'NULL' ITEM_VALUE,\n" + 
							"                       0 AS CNT0,\n" + 
							"                       1 AS CNT1,\n" + 
							"                       0 AS CNT\n" + 
							"                  FROM DC_SE_EQUIPMENT S,\n" + 
							"                       TMP_P P,\n" + 
							"                       TMP_T T,\n" + 
							"                       TMEP  C\n" + 
							"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
							"                   AND S.ID = P.EQU_ID(+)\n" + 
							"                   AND S.REG_CODE = C.ENTID\n" + 
							"                   AND S.STATUS = '1'\n" + 
							"                 GROUP BY C.DEP_NAME, S.USE_UNIT_ID) T\n" + 
							"         GROUP BY T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE\n" + 
							"        ) S\n" + 
							" GROUP BY S.EQU_ADDR_COUNTY_CODE\n" + 
							" UNION ALL\n" + 
							" SELECT\n" + 
							"    NVL(S.EQU_ADDR_COUNTY_CODE,'合计') AS 项目,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器合计,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) - SUM(S.CNT0) AS 非简单压力容器,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '电梯' THEN S.CNT ELSE 0 END) AS 电梯,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场车,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 游乐设施,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道,\n" + 
							"    SUM(CASE WHEN S.ITEM_VALUE = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道,\n" + 
							"    SUM(S.CNT1) AS 合计\n" + 
							"  FROM (SELECT T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE, SUM(CNT0) AS CNT0, SUM(CNT1) AS CNT1,SUM(CNT) AS CNT\n" + 
							"          FROM (SELECT '全市' AS EQU_ADDR_COUNTY_CODE,\n" + 
							"                       T.ITEM_VALUE,\n" + 
							"                       0 AS CNT0,\n" + 
							"                       0 AS CNT1,\n" + 
							"                       1 AS CNT\n" + 
							"                  FROM DC_SE_EQUIPMENT S,\n" + 
							"                       TMP_P P,\n" + 
							"                       TMP_T T,\n" + 
							"                       TMEP  C\n" + 
							"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
							"                   AND S.ID = P.EQU_ID(+)\n" + 
							"                   AND S.STATUS = '1'\n" + 
							"                   AND S.REG_CODE = C.ENTID\n" + 
							"                 GROUP BY T.ITEM_VALUE, S.USE_UNIT_ID\n" + 
							"                UNION ALL\n" + 
							"                SELECT '全市' AS EQU_ADDR_COUNTY_CODE,\n" + 
							"                       T.ITEM_VALUE,\n" + 
							"                       1 AS CNT0,\n" + 
							"                       0 AS CNT1,\n" + 
							"                       0 AS CNT\n" + 
							"                  FROM DC_SE_EQUIPMENT S,\n" + 
							"                       TMP_P P,\n" + 
							"                       TMP_T T,\n" + 
							"                       TMEP  C\n" + 
							"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
							"                   AND S.ID = P.EQU_ID\n" + 
							"                   AND S.STATUS = '1'\n" + 
							"                   AND S.REG_CODE = C.ENTID\n" + 
							"                 GROUP BY T.ITEM_VALUE, S.USE_UNIT_ID\n" + 
							"                 UNION ALL\n" + 
							"                 SELECT '全市' AS EQU_ADDR_COUNTY_CODE,\n" + 
							"                       'NULL' AS ITEM_VALUE,\n" + 
							"                       0 AS CNT0,\n" + 
							"                       1 AS CNT1,\n" + 
							"                       0 AS CNT\n" + 
							"                  FROM DC_SE_EQUIPMENT S,\n" + 
							"                       TMP_P P,\n" + 
							"                       TMP_T T,\n" + 
							"                       TMEP  C\n" + 
							"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
							"                   AND S.ID = P.EQU_ID(+)\n" + 
							"                   AND S.STATUS = '1'\n" + 
							"                   AND S.REG_CODE = C.ENTID\n" + 
							"                 GROUP BY  S.USE_UNIT_ID) T\n" + 
							"         GROUP BY T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE\n" + 
							"        ) S\n" + 
							" GROUP BY S.EQU_ADDR_COUNTY_CODE";
 
			
			if(map.get("adminbrancode")!=null&&(!map.get("adminbrancode").equals(""))) {
				
				params.add((String) map.get("adminbrancode"));
				
				sql = "WITH TMEP AS\n" +
						"(SELECT C.ENTID,\n" + 
						"       C.SUPUNITBYSUP,\n" + 
						"       C.SUPDEPBYSUP,\n" + 
						"       C.GRIDBYSUP,\n" + 
						"       --U.DEP_NAME,--局\n" + 
						"       U1.DEP_NAME, --所\n" + 
						"       --U1.DEP_NAME AS SHOU_NAME,\n" + 
						"       G.GRIDNAME\n" + 
						"  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\n" + 
						"       DC_JG_SYS_RIGHT_DEPARTMENT U,\n" + 
						"       DC_JG_SYS_RIGHT_DEPARTMENT U1,\n" + 
						"       DC_JG_MS_BA_GRID           G\n" + 
						" WHERE C.SUPCODE = 'B100'\n" + 
						"   AND U.DEP_NAME = ?  \n" + 
						"   AND U1.DEP_NAME = ?  \n" + 
						"   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\n" + 
						"   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\n" + 
						"   AND G.GRID(+) = C.GRIDBYSUP),\n" + 
						"TMP_P AS (SELECT * FROM DC_SE_EQU_CONTAINER_PARAM WHERE IS_SIMPLE_CONTAINER = '1'),\n" + 
						"TMP_T AS (SELECT T.ITEM_CODE, T.ITEM_VALUE FROM DC_SC_DICT_ITEM T WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND')\n" + 
						"SELECT\n" + 
						"    NVL(S.EQU_ADDR_COUNTY_CODE,'待分区域') AS 项目,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器合计,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) - SUM(S.CNT0) AS 非简单压力容器,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '电梯' THEN S.CNT ELSE 0 END) AS 电梯,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场车,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 游乐设施,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = 'NULL' THEN S.CNT1 ELSE 0 END) AS 合计\n" + 
						"  FROM (SELECT T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE, SUM(CNT0) AS CNT0, SUM(CNT1) AS CNT1,SUM(CNT) AS CNT\n" + 
						"          FROM (SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,\n" + 
						"                       T.ITEM_VALUE,\n" + 
						"                       0 AS CNT0,\n" + 
						"                       0 AS CNT1,\n" + 
						"                       1 AS CNT\n" + 
						"                  FROM DC_SE_EQUIPMENT S,\n" + 
						"                       TMP_P P,\n" + 
						"                       TMP_T T,\n" + 
						"                       TMEP  C\n" + 
						"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"                   AND S.ID = P.EQU_ID(+)\n" + 
						"                   AND  S.REG_CODE = C.ENTID\n" + 
						"                   AND S.STATUS = '1'\n" + 
						"                 GROUP BY C.DEP_NAME, T.ITEM_VALUE, S.USE_UNIT_ID\n" + 
						"                UNION ALL\n" + 
						"                SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,\n" + 
						"                       T.ITEM_VALUE,\n" + 
						"                       1 AS CNT0,\n" + 
						"                       0 AS CNT1,\n" + 
						"                       0 AS CNT\n" + 
						"                  FROM DC_SE_EQUIPMENT S,\n" + 
						"                       TMP_P P,\n" + 
						"                       TMP_T T,\n" + 
						"                       TMEP  C\n" + 
						"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"                   AND S.ID = P.EQU_ID\n" + 
						"                   AND S.REG_CODE = C.ENTID\n" + 
						"                   AND S.STATUS = '1'\n" + 
						"                 GROUP BY C.DEP_NAME, T.ITEM_VALUE, S.USE_UNIT_ID\n" + 
						"                UNION ALL --20190425\n" + 
						"                SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,\n" + 
						"                       'NULL' ITEM_VALUE,\n" + 
						"                       0 AS CNT0,\n" + 
						"                       1 AS CNT1,\n" + 
						"                       0 AS CNT\n" + 
						"                  FROM DC_SE_EQUIPMENT S,\n" + 
						"                       TMP_P P,\n" + 
						"                       TMP_T T,\n" + 
						"                       TMEP  C\n" + 
						"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"                   AND S.ID = P.EQU_ID(+)\n" + 
						"                   AND S.REG_CODE = C.ENTID\n" + 
						"                   AND S.STATUS = '1'\n" + 
						"                 GROUP BY C.DEP_NAME, S.USE_UNIT_ID) T\n" + 
						"         GROUP BY T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE\n" + 
						"        ) S\n" + 
						" GROUP BY S.EQU_ADDR_COUNTY_CODE\n" + 
						" UNION ALL\n" + 
						" SELECT\n" + 
						"    NVL(S.EQU_ADDR_COUNTY_CODE,'合计') AS 项目,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器合计,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) - SUM(S.CNT0) AS 非简单压力容器,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '电梯' THEN S.CNT ELSE 0 END) AS 电梯,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场车,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 游乐设施,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道,\n" + 
						"    SUM(CASE WHEN S.ITEM_VALUE = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道,\n" + 
						"    SUM(S.CNT1) AS 合计\n" + 
						"  FROM (SELECT T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE, SUM(CNT0) AS CNT0, SUM(CNT1) AS CNT1,SUM(CNT) AS CNT\n" + 
						"          FROM (SELECT '全市' AS EQU_ADDR_COUNTY_CODE,\n" + 
						"                       T.ITEM_VALUE,\n" + 
						"                       0 AS CNT0,\n" + 
						"                       0 AS CNT1,\n" + 
						"                       1 AS CNT\n" + 
						"                  FROM DC_SE_EQUIPMENT S,\n" + 
						"                       TMP_P P,\n" + 
						"                       TMP_T T,\n" + 
						"                       TMEP  C\n" + 
						"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"                   AND S.ID = P.EQU_ID(+)\n" + 
						"                   AND S.STATUS = '1'\n" + 
						"                   AND S.REG_CODE = C.ENTID\n" + 
						"                 GROUP BY T.ITEM_VALUE, S.USE_UNIT_ID\n" + 
						"                UNION ALL\n" + 
						"                SELECT '全市' AS EQU_ADDR_COUNTY_CODE,\n" + 
						"                       T.ITEM_VALUE,\n" + 
						"                       1 AS CNT0,\n" + 
						"                       0 AS CNT1,\n" + 
						"                       0 AS CNT\n" + 
						"                  FROM DC_SE_EQUIPMENT S,\n" + 
						"                       TMP_P P,\n" + 
						"                       TMP_T T,\n" + 
						"                       TMEP  C\n" + 
						"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"                   AND S.ID = P.EQU_ID\n" + 
						"                   AND S.STATUS = '1'\n" + 
						"                   AND S.REG_CODE = C.ENTID\n" + 
						"                 GROUP BY T.ITEM_VALUE, S.USE_UNIT_ID\n" + 
						"                 UNION ALL\n" + 
						"                 SELECT '全市' AS EQU_ADDR_COUNTY_CODE,\n" + 
						"                       'NULL' AS ITEM_VALUE,\n" + 
						"                       0 AS CNT0,\n" + 
						"                       1 AS CNT1,\n" + 
						"                       0 AS CNT\n" + 
						"                  FROM DC_SE_EQUIPMENT S,\n" + 
						"                       TMP_P P,\n" + 
						"                       TMP_T T,\n" + 
						"                       TMEP  C\n" + 
						"                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"                   AND S.ID = P.EQU_ID(+)\n" + 
						"                   AND S.STATUS = '1'\n" + 
						"                   AND S.REG_CODE = C.ENTID\n" + 
						"                 GROUP BY  S.USE_UNIT_ID) T\n" + 
						"         GROUP BY T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE\n" + 
						"        ) S\n" + 
						" GROUP BY S.EQU_ADDR_COUNTY_CODE";
				 

			 }
			
		}
		
		
			
		System.out.println(sql+"\n");
		
		return resuts = dao_dc.queryForList(sql, params);
		
	}
}
