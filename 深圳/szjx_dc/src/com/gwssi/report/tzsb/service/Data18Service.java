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
public class Data18Service extends BaseService {

	public List queryListByDingJianLv(Map map) throws OptimusException {

		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();// 参数准备
		List<Map> resuts = null;
		
		boolean isChangType = false;
		
		// 福田局,罗湖局,南山局,盐田局,宝安局,龙岗局,光明局,坪山局,龙华局,大鹏局
		StringBuffer sb = new StringBuffer();

		Map<String, String> codeMap = new HashMap<String, String>();

		codeMap.put("福田局", "440304");
		codeMap.put("罗湖局", "440303");
		codeMap.put("南山局", "440305");
		codeMap.put("盐田局", "440308");
		codeMap.put("宝安局", "440306");
		codeMap.put("龙岗局", "440307");
		codeMap.put("光明局", "440309");
		codeMap.put("坪山局", "440310");
		codeMap.put("龙华局", "440342");
		codeMap.put("大鹏局", "440343");
		//codeMap.put("深汕监管局", "440344");
		codeMap.put("深汕监管局", "441521");
		

		Map<String, String> keyMap = new HashMap<String, String>();

		keyMap.put("4000", "福田局");
		keyMap.put("4100", "罗湖局");
		keyMap.put("4200", "南山局");
		keyMap.put("4300", "盐田局");
		keyMap.put("4400", "宝安局");
		keyMap.put("4500", "龙岗局");
		keyMap.put("4600", "光明局");
		keyMap.put("4700", "坪山局");
		keyMap.put("4800", "龙华局");
		keyMap.put("4900", "大鹏局");
		keyMap.put("4A00", "深汕监管局");
		
		String sql = 
				"WITH TMEP AS\n" +
						"(SELECT C.ENTID,\n" + 
						"       C.SUPUNITBYSUP,\n" + 
						"       C.SUPDEPBYSUP,\n" + 
						"       C.GRIDBYSUP,\n" + 
						"       U.DEP_NAME,\n" + 
						"       U1.DEP_NAME AS SHOU_NAME,\n" + 
						"       G.GRIDNAME\n" + 
						"  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\n" + 
						"       DC_JG_SYS_RIGHT_DEPARTMENT U,\n" + 
						"       DC_JG_SYS_RIGHT_DEPARTMENT U1,\n" + 
						"       DC_JG_MS_BA_GRID           G\n" + 
						" WHERE C.SUPCODE = 'B100'\n" + 
						"   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\n" + 
						"   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\n" + 
						"   AND G.GRID(+) = C.GRIDBYSUP)\n" + 
						"SELECT 品种种类,统计类型,福田局,罗湖局,南山局,盐田局,宝安局,龙岗局,光明局,坪山局,龙华局,大鹏局,深汕监管局,未分派,合计\n" + 
						" FROM (\n" + 
						"SELECT\n" + 
						"    NVL(S.ITEM_VALUE,'总体') AS 品种种类,\n" + 
						"    S.CNT_TYPE AS 统计类型,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4000' THEN S.CNT ELSE 0 END) AS 福田局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4100' THEN S.CNT ELSE 0 END) AS 罗湖局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4200' THEN S.CNT ELSE 0 END) AS 南山局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4300' THEN S.CNT ELSE 0 END) AS 盐田局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4400' THEN S.CNT ELSE 0 END) AS 宝安局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4500' THEN S.CNT ELSE 0 END) AS 龙岗局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4600' THEN S.CNT ELSE 0 END) AS 光明局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4700' THEN S.CNT ELSE 0 END) AS 坪山局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4800' THEN S.CNT ELSE 0 END) AS 龙华局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4900' THEN S.CNT ELSE 0 END) AS 大鹏局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4A00' THEN S.CNT ELSE 0 END) AS 深汕监管局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '440399' THEN S.CNT ELSE 0 END) AS 未分派,\n" + 
						"    SUM(S.CNT) AS 合计,\n" + 
						"    CASE WHEN CNT_TYPE = '超期数' THEN 1 ELSE 2 END AS NUM_CNT\n" + 
						"  FROM (SELECT T.ITEM_VALUE,\n" + 
						"               '超期数' AS CNT_TYPE,\n" + 
						"               NVL(G.SUPUNITBYSUP,'440399') AS CODE_VALUE,\n" + 
						"               COUNT(1) AS CNT,\n" + 
						"               0 AS CNT0\n" + 
						"          FROM DC_SE_EQUIPMENT S,\n" + 
						"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
						"                  FROM DC_SC_DICT_ITEM T\n" + 
						"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,\n" + 
						"               TMEP G\n" + 
						"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"           AND S.STATUS = '1'\n" + 
						"           AND S.CHECKOUT_STATUS = '在用'\n" + 
						"           AND NOT EXISTS ( SELECT 1 FROM DC_SE_EQU_CONTAINER_PARAM P WHERE S.ID = P.EQU_ID AND P.IS_SIMPLE_CONTAINER='1' )\n" + 
						"           AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE)\n" + 
						"           AND S.REG_CODE = G.ENTID(+)\n" + 
						"         GROUP BY T.ITEM_VALUE,G.SUPUNITBYSUP\n" + 
						"        UNION ALL\n" + 
						"        SELECT T.ITEM_VALUE,\n" + 
						"               '在用总数' AS CNT_TYPE,\n" + 
						"               NVL(G.SUPUNITBYSUP,'440399') AS CODE_VALUE,\n" + 
						"               COUNT(1) AS CNT,\n" + 
						"               0 AS CNT0\n" + 
						"          FROM DC_SE_EQUIPMENT S,\n" + 
						"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
						"                  FROM DC_SC_DICT_ITEM T\n" + 
						"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,\n" + 
						"               TMEP G\n" + 
						"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"           AND S.STATUS = '1'\n" + 
						"           AND S.REG_CODE = G.ENTID(+)\n" + 
						"           AND NOT EXISTS ( SELECT 1 FROM DC_SE_EQU_CONTAINER_PARAM P WHERE S.ID = P.EQU_ID AND P.IS_SIMPLE_CONTAINER='1' )\n" + 
						"         GROUP BY T.ITEM_VALUE,G.SUPUNITBYSUP\n" + 
						"        ) S\n" + 
						" GROUP BY ROLLUP(S.ITEM_VALUE),S.CNT_TYPE\n" + 
						"UNION ALL\n" + 
						"SELECT\n" + 
						"    NVL(S.ITEM_VALUE,'总体') AS 品种种类,\n" + 
						"    S.CNT_TYPE AS 统计类型,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4000' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 福田局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4100' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 罗湖局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4200' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 南山局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4300' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 盐田局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4400' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 宝安局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4500' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙岗局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4600' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 光明局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4700' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 坪山局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4800' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙华局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4900' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 大鹏局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4A00' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 深汕监管局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '440399' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 未分派,\n" + 
						"    (1-ROUND(SUM(CNT0)/SUM(CNT),4))*100 AS 合计,\n" + 
						"    3 AS NUM_CNT\n" + 
						"  FROM (SELECT T.ITEM_VALUE,\n" + 
						"               '定检率(%)' AS CNT_TYPE,\n" + 
						"               S.CODE_VALUE,\n" + 
						"               COUNT(1) AS CNT,\n" + 
						"               SUM(CASE WHEN S.CHECKOUT_STATUS = '在用' AND S.FLAG = 0 AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE) THEN 1 ELSE 0 END) AS CNT0\n" + 
						"          FROM (SELECT S.*,NVL(G.SUPUNITBYSUP,'440399') AS CODE_VALUE,\n" + 
						"                     CASE\n" + 
						"                       WHEN P.EQU_ID IS NULL THEN 0\n" + 
						"                       ELSE 1\n" + 
						"                     END AS FLAG\n" + 
						"                FROM DC_SE_EQUIPMENT S, DC_SE_EQU_CONTAINER_PARAM P,TMEP G\n" + 
						"               WHERE S.ID = P.EQU_ID(+)\n" + 
						"                 AND IS_SIMPLE_CONTAINER(+) = '1'\n" + 
						"                 AND S.REG_CODE = G.ENTID(+)) S,\n" + 
						"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
						"                  FROM DC_SC_DICT_ITEM T\n" + 
						"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T\n" + 
						"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"           AND S.STATUS = '1'\n" + 
						"         GROUP BY T.ITEM_VALUE,S.CODE_VALUE\n" + 
						"        ) S\n" + 
						" GROUP BY S.ITEM_VALUE,S.CNT_TYPE\n" + 
						"UNION ALL\n" + 
						"SELECT\n" + 
						"    '总体' AS 品种种类,\n" + 
						"    '定检率(%)' AS 统计类型,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4000' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 福田局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4100' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 罗湖局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4200' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 南山局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4300' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 盐田局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4400' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 宝安局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4500' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙岗局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4600' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 光明局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4700' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 坪山局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4800' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙华局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4900' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 大鹏局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '4A00' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 深汕监管局,\n" + 
						"    SUM(CASE WHEN S.CODE_VALUE = '440399' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 未分派,\n" + 
						"    (1-ROUND(SUM(CNT0)/SUM(CNT),4))*100 AS 合计,\n" + 
						"    3 AS NUM_CNT\n" + 
						"  FROM (SELECT S.CODE_VALUE,\n" + 
						"               COUNT(1) AS CNT,\n" + 
						"               SUM(CASE WHEN S.CHECKOUT_STATUS = '在用' AND S.FLAG = 0 AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE) THEN 1 ELSE 0 END) AS CNT0\n" + 
						"          FROM (SELECT S.*,NVL(G.SUPUNITBYSUP,'440399') AS CODE_VALUE,\n" + 
						"                     CASE\n" + 
						"                       WHEN P.EQU_ID IS NULL THEN 0\n" + 
						"                       ELSE 1\n" + 
						"                     END AS FLAG\n" + 
						"                FROM DC_SE_EQUIPMENT S, DC_SE_EQU_CONTAINER_PARAM P,TMEP G\n" + 
						"               WHERE S.ID = P.EQU_ID(+)\n" + 
						"                 AND IS_SIMPLE_CONTAINER(+) = '1'\n" + 
						"                 AND S.REG_CODE = G.ENTID(+)) S,\n" + 
						"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
						"                  FROM DC_SC_DICT_ITEM T\n" + 
						"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T\n" + 
						"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
						"           AND S.STATUS = '1'\n" + 
						"         GROUP BY S.CODE_VALUE\n" + 
						"        ) S\n" + 
						") T ORDER BY  品种种类,NUM_CNT";

		

		String regCode = (String) map.get("regCode");
		String adminbrancode = (String) map.get("adminbrancode");

		if (regCode != null && !regCode.equals("")) {
			String gongshangJu = keyMap.get(regCode);
			//String codeOld = codeMap.get(gongshangJu);
			
		   //String commonSQL = "SUM(CASE WHEN S.CODE_VALUE = '"+codeOld+"' THEN S.CNT ELSE 0 END) AS "+gongshangJu +" , ";
			
			
		  String  titleSql = gongshangJu;
		   
		  sql  = "select 品种种类,统计类型, "+titleSql+ " ,"+ titleSql + " 合计   from (WITH TMEP AS\r\n" + 
					"(SELECT C.ENTID,\r\n" + 
					"       C.SUPUNITBYSUP,\r\n" + 
					"       C.SUPDEPBYSUP,\r\n" + 
					"       C.GRIDBYSUP,\r\n" + 
					"       U.DEP_NAME,\r\n" + 
					"       U1.DEP_NAME AS SHOU_NAME,\r\n" + 
					"       G.GRIDNAME\r\n" + 
					"  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\r\n" + 
					"       DC_JG_SYS_RIGHT_DEPARTMENT U,\r\n" + 
					"       DC_JG_SYS_RIGHT_DEPARTMENT U1,\r\n" + 
					"       DC_JG_MS_BA_GRID           G\r\n" + 
					" WHERE C.SUPCODE = 'B100'\r\n" + 
					"   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\r\n" + 
					"   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\r\n" + 
					"   AND G.GRID(+) = C.GRIDBYSUP and c.SUPUNITBYSUP='"+regCode+"' ) \r\n" +
					"SELECT 品种种类,统计类型,福田局,罗湖局,南山局,盐田局,宝安局,龙岗局,光明局,坪山局,龙华局,大鹏局,深汕监管局,未分派,合计\n" + 
					" FROM (\n" + 
					"SELECT\n" + 
					"    NVL(S.ITEM_VALUE,'总体') AS 品种种类,\n" + 
					"    S.CNT_TYPE AS 统计类型,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4000' THEN S.CNT ELSE 0 END) AS 福田局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4100' THEN S.CNT ELSE 0 END) AS 罗湖局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4200' THEN S.CNT ELSE 0 END) AS 南山局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4300' THEN S.CNT ELSE 0 END) AS 盐田局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4400' THEN S.CNT ELSE 0 END) AS 宝安局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4500' THEN S.CNT ELSE 0 END) AS 龙岗局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4600' THEN S.CNT ELSE 0 END) AS 光明局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4700' THEN S.CNT ELSE 0 END) AS 坪山局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4800' THEN S.CNT ELSE 0 END) AS 龙华局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4900' THEN S.CNT ELSE 0 END) AS 大鹏局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4A00' THEN S.CNT ELSE 0 END) AS 深汕监管局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '440399' THEN S.CNT ELSE 0 END) AS 未分派,\n" + 
					"    SUM(S.CNT) AS 合计,\n" + 
					"    CASE WHEN CNT_TYPE = '超期数' THEN 1 ELSE 2 END AS NUM_CNT\n" + 
					"  FROM (SELECT T.ITEM_VALUE,\n" + 
					"               '超期数' AS CNT_TYPE,\n" + 
					"               NVL(G.SUPUNITBYSUP,'440399') AS CODE_VALUE,\n" + 
					"               COUNT(1) AS CNT,\n" + 
					"               0 AS CNT0\n" + 
					"          FROM DC_SE_EQUIPMENT S,\n" + 
					"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
					"                  FROM DC_SC_DICT_ITEM T\n" + 
					"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,\n" + 
					"               TMEP G\n" + 
					"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
					"           AND S.STATUS = '1'\n" + 
					"           AND S.CHECKOUT_STATUS = '在用'\n" + 
					"           AND NOT EXISTS ( SELECT 1 FROM DC_SE_EQU_CONTAINER_PARAM P WHERE S.ID = P.EQU_ID AND P.IS_SIMPLE_CONTAINER='1' )\n" + 
					"           AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE)\n" + 
					"           AND S.REG_CODE = G.ENTID(+)\n" + 
					"         GROUP BY T.ITEM_VALUE,G.SUPUNITBYSUP\n" + 
					"        UNION ALL\n" + 
					"        SELECT T.ITEM_VALUE,\n" + 
					"               '在用总数' AS CNT_TYPE,\n" + 
					"               NVL(G.SUPUNITBYSUP,'440399') AS CODE_VALUE,\n" + 
					"               COUNT(1) AS CNT,\n" + 
					"               0 AS CNT0\n" + 
					"          FROM DC_SE_EQUIPMENT S,\n" + 
					"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
					"                  FROM DC_SC_DICT_ITEM T\n" + 
					"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,\n" + 
					"               TMEP G\n" + 
					"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
					"           AND S.STATUS = '1'\n" + 
					"           AND S.REG_CODE = G.ENTID(+)\n" + 
					"           AND NOT EXISTS ( SELECT 1 FROM DC_SE_EQU_CONTAINER_PARAM P WHERE S.ID = P.EQU_ID AND P.IS_SIMPLE_CONTAINER='1' )\n" + 
					"         GROUP BY T.ITEM_VALUE,G.SUPUNITBYSUP\n" + 
					"        ) S\n" + 
					" GROUP BY ROLLUP(S.ITEM_VALUE),S.CNT_TYPE\n" + 
					"UNION ALL\n" + 
					"SELECT\n" + 
					"    NVL(S.ITEM_VALUE,'总体') AS 品种种类,\n" + 
					"    S.CNT_TYPE AS 统计类型,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4000' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 福田局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4100' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 罗湖局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4200' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 南山局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4300' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 盐田局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4400' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 宝安局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4500' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙岗局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4600' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 光明局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4700' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 坪山局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4800' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙华局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4900' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 大鹏局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4A00' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 深汕监管局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '440399' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 未分派,\n" + 
					"    (1-ROUND(SUM(CNT0)/SUM(CNT),4))*100 AS 合计,\n" + 
					"    3 AS NUM_CNT\n" + 
					"  FROM (SELECT T.ITEM_VALUE,\n" + 
					"               '定检率(%)' AS CNT_TYPE,\n" + 
					"               S.CODE_VALUE,\n" + 
					"               COUNT(1) AS CNT,\n" + 
					"               SUM(CASE WHEN S.CHECKOUT_STATUS = '在用' AND S.FLAG = 0 AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE) THEN 1 ELSE 0 END) AS CNT0\n" + 
					"          FROM (SELECT S.*,NVL(G.SUPUNITBYSUP,'440399') AS CODE_VALUE,\n" + 
					"                     CASE\n" + 
					"                       WHEN P.EQU_ID IS NULL THEN 0\n" + 
					"                       ELSE 1\n" + 
					"                     END AS FLAG\n" + 
					"                FROM DC_SE_EQUIPMENT S, DC_SE_EQU_CONTAINER_PARAM P,TMEP G\n" + 
					"               WHERE S.ID = P.EQU_ID(+)\n" + 
					"                 AND IS_SIMPLE_CONTAINER(+) = '1'\n" + 
					"                 AND S.REG_CODE = G.ENTID(+)) S,\n" + 
					"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
					"                  FROM DC_SC_DICT_ITEM T\n" + 
					"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T\n" + 
					"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
					"           AND S.STATUS = '1'\n" + 
					"         GROUP BY T.ITEM_VALUE,S.CODE_VALUE\n" + 
					"        ) S\n" + 
					" GROUP BY S.ITEM_VALUE,S.CNT_TYPE\n" + 
					"UNION ALL\n" + 
					"SELECT\n" + 
					"    '总体' AS 品种种类,\n" + 
					"    '定检率(%)' AS 统计类型,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4000' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 福田局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4100' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 罗湖局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4200' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 南山局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4300' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 盐田局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4400' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 宝安局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4500' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙岗局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4600' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 光明局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4700' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 坪山局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4800' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙华局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4900' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 大鹏局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4A00' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 深汕监管局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '440399' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 未分派,\n" + 
					"    (1-ROUND(SUM(CNT0)/SUM(CNT),4))*100 AS 合计,\n" + 
					"    3 AS NUM_CNT\n" + 
					"  FROM (SELECT S.CODE_VALUE,\n" + 
					"               COUNT(1) AS CNT,\n" + 
					"               SUM(CASE WHEN S.CHECKOUT_STATUS = '在用' AND S.FLAG = 0 AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE) THEN 1 ELSE 0 END) AS CNT0\n" + 
					"          FROM (SELECT S.*,NVL(G.SUPUNITBYSUP,'440399') AS CODE_VALUE,\n" + 
					"                     CASE\n" + 
					"                       WHEN P.EQU_ID IS NULL THEN 0\n" + 
					"                       ELSE 1\n" + 
					"                     END AS FLAG\n" + 
					"                FROM DC_SE_EQUIPMENT S, DC_SE_EQU_CONTAINER_PARAM P,TMEP G\n" + 
					"               WHERE S.ID = P.EQU_ID(+)\n" + 
					"                 AND IS_SIMPLE_CONTAINER(+) = '1'\n" + 
					"                 AND S.REG_CODE = G.ENTID(+)) S,\n" + 
					"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
					"                  FROM DC_SC_DICT_ITEM T\n" + 
					"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T\n" + 
					"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
					"           AND S.STATUS = '1'\n" + 
					"         GROUP BY S.CODE_VALUE\n" + 
					"        ) S\n" + 
					") T ORDER BY  品种种类,NUM_CNT ) ";
					
			
			
			
					
			
			if (adminbrancode != null && !adminbrancode.equals("")) {
				//  commonSQL = "SUM(CASE WHEN S.CODE_VALUE = '"+codeOld+"' THEN S.CNT ELSE 0 END) AS "+gongshangJu +" , ";
				  
				//commonSQL =  " SUM(CASE WHEN S.CODE_VALUE = '"+codeOld+"' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 南山局, ";
				//String regCode = (String) map.get("regCode");
			//	String adminbrancode = (String) map.get("adminbrancode");

				
				
		//	List tempList = select distinct gssmc as text,gssdm as value from v_jg_ent t where gssdm = '5020'
					 //dao_dc.queryForList(sql, params);
				
			List jianGuanSuoParam = new ArrayList();
			jianGuanSuoParam.add(adminbrancode);
			String jianGuanSuoName = "select distinct gssmc as text,gssdm as value from v_jg_ent t where gssdm = ? ";
			List listJianGuanSuoName = dao_dc.queryForList(jianGuanSuoName, jianGuanSuoParam);
			System.out.println(listJianGuanSuoName);
			HashMap retMap = (HashMap) listJianGuanSuoName.get(0);
			String tempAdmin = (String) retMap.get("text");
			System.out.println(tempAdmin);
			
		   isChangType = true;
				
			sql  = "select 品种种类,统计类型, "+titleSql+ "  as \""+tempAdmin+ "\"  "+ ","+ titleSql + " 合计   from (WITH TMEP AS\r\n" + 
					"(SELECT C.ENTID,\r\n" + 
					"       C.SUPUNITBYSUP,\r\n" + 
					"       C.SUPDEPBYSUP,\r\n" + 
					"       C.GRIDBYSUP,\r\n" + 
					"       U.DEP_NAME,\r\n" + 
					"       U1.DEP_NAME AS SHOU_NAME,\r\n" + 
					"       G.GRIDNAME\r\n" + 
					"  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\r\n" + 
					"       DC_JG_SYS_RIGHT_DEPARTMENT U,\r\n" + 
					"       DC_JG_SYS_RIGHT_DEPARTMENT U1,\r\n" + 
					"       DC_JG_MS_BA_GRID           G\r\n" + 
					" WHERE C.SUPCODE = 'B100'\r\n" + 
					"   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\r\n" + 
					"   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\r\n" + 
					"   AND G.GRID(+) = C.GRIDBYSUP and c.SUPUNITBYSUP='"+regCode+"' and  C.SUPDEPBYSUP='"+adminbrancode+"' ) \r\n" + 
					"SELECT 品种种类,统计类型,福田局,罗湖局,南山局,盐田局,宝安局,龙岗局,光明局,坪山局,龙华局,大鹏局,深汕监管局,未分派,合计\n" + 
					" FROM (\n" + 
					"SELECT\n" + 
					"    NVL(S.ITEM_VALUE,'总体') AS 品种种类,\n" + 
					"    S.CNT_TYPE AS 统计类型,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4000' THEN S.CNT ELSE 0 END) AS 福田局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4100' THEN S.CNT ELSE 0 END) AS 罗湖局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4200' THEN S.CNT ELSE 0 END) AS 南山局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4300' THEN S.CNT ELSE 0 END) AS 盐田局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4400' THEN S.CNT ELSE 0 END) AS 宝安局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4500' THEN S.CNT ELSE 0 END) AS 龙岗局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4600' THEN S.CNT ELSE 0 END) AS 光明局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4700' THEN S.CNT ELSE 0 END) AS 坪山局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4800' THEN S.CNT ELSE 0 END) AS 龙华局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4900' THEN S.CNT ELSE 0 END) AS 大鹏局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4A00' THEN S.CNT ELSE 0 END) AS 深汕监管局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '440399' THEN S.CNT ELSE 0 END) AS 未分派,\n" + 
					"    SUM(S.CNT) AS 合计,\n" + 
					"    CASE WHEN CNT_TYPE = '超期数' THEN 1 ELSE 2 END AS NUM_CNT\n" + 
					"  FROM (SELECT T.ITEM_VALUE,\n" + 
					"               '超期数' AS CNT_TYPE,\n" + 
					"               NVL(G.SUPUNITBYSUP,'440399') AS CODE_VALUE,\n" + 
					"               COUNT(1) AS CNT,\n" + 
					"               0 AS CNT0\n" + 
					"          FROM DC_SE_EQUIPMENT S,\n" + 
					"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
					"                  FROM DC_SC_DICT_ITEM T\n" + 
					"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,\n" + 
					"               TMEP G\n" + 
					"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
					"           AND S.STATUS = '1'\n" + 
					"           AND S.CHECKOUT_STATUS = '在用'\n" + 
					"           AND NOT EXISTS ( SELECT 1 FROM DC_SE_EQU_CONTAINER_PARAM P WHERE S.ID = P.EQU_ID AND P.IS_SIMPLE_CONTAINER='1' )\n" + 
					"           AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE)\n" + 
					"           AND S.REG_CODE = G.ENTID(+)\n" + 
					"         GROUP BY T.ITEM_VALUE,G.SUPUNITBYSUP\n" + 
					"        UNION ALL\n" + 
					"        SELECT T.ITEM_VALUE,\n" + 
					"               '在用总数' AS CNT_TYPE,\n" + 
					"               NVL(G.SUPUNITBYSUP,'440399') AS CODE_VALUE,\n" + 
					"               COUNT(1) AS CNT,\n" + 
					"               0 AS CNT0\n" + 
					"          FROM DC_SE_EQUIPMENT S,\n" + 
					"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
					"                  FROM DC_SC_DICT_ITEM T\n" + 
					"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,\n" + 
					"               TMEP G\n" + 
					"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
					"           AND S.STATUS = '1'\n" + 
					"           AND S.REG_CODE = G.ENTID(+)\n" + 
					"           AND NOT EXISTS ( SELECT 1 FROM DC_SE_EQU_CONTAINER_PARAM P WHERE S.ID = P.EQU_ID AND P.IS_SIMPLE_CONTAINER='1' )\n" + 
					"         GROUP BY T.ITEM_VALUE,G.SUPUNITBYSUP\n" + 
					"        ) S\n" + 
					" GROUP BY ROLLUP(S.ITEM_VALUE),S.CNT_TYPE\n" + 
					"UNION ALL\n" + 
					"SELECT\n" + 
					"    NVL(S.ITEM_VALUE,'总体') AS 品种种类,\n" + 
					"    S.CNT_TYPE AS 统计类型,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4000' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 福田局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4100' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 罗湖局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4200' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 南山局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4300' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 盐田局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4400' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 宝安局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4500' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙岗局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4600' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 光明局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4700' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 坪山局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4800' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙华局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4900' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 大鹏局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4A00' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 深汕监管局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '440399' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 未分派,\n" + 
					"    (1-ROUND(SUM(CNT0)/SUM(CNT),4))*100 AS 合计,\n" + 
					"    3 AS NUM_CNT\n" + 
					"  FROM (SELECT T.ITEM_VALUE,\n" + 
					"               '定检率(%)' AS CNT_TYPE,\n" + 
					"               S.CODE_VALUE,\n" + 
					"               COUNT(1) AS CNT,\n" + 
					"               SUM(CASE WHEN S.CHECKOUT_STATUS = '在用' AND S.FLAG = 0 AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE) THEN 1 ELSE 0 END) AS CNT0\n" + 
					"          FROM (SELECT S.*,NVL(G.SUPUNITBYSUP,'440399') AS CODE_VALUE,\n" + 
					"                     CASE\n" + 
					"                       WHEN P.EQU_ID IS NULL THEN 0\n" + 
					"                       ELSE 1\n" + 
					"                     END AS FLAG\n" + 
					"                FROM DC_SE_EQUIPMENT S, DC_SE_EQU_CONTAINER_PARAM P,TMEP G\n" + 
					"               WHERE S.ID = P.EQU_ID(+)\n" + 
					"                 AND IS_SIMPLE_CONTAINER(+) = '1'\n" + 
					"                 AND S.REG_CODE = G.ENTID(+)) S,\n" + 
					"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
					"                  FROM DC_SC_DICT_ITEM T\n" + 
					"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T\n" + 
					"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
					"           AND S.STATUS = '1'\n" + 
					"         GROUP BY T.ITEM_VALUE,S.CODE_VALUE\n" + 
					"        ) S\n" + 
					" GROUP BY S.ITEM_VALUE,S.CNT_TYPE\n" + 
					"UNION ALL\n" + 
					"SELECT\n" + 
					"    '总体' AS 品种种类,\n" + 
					"    '定检率(%)' AS 统计类型,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4000' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 福田局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4100' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 罗湖局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4200' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 南山局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4300' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 盐田局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4400' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 宝安局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4500' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙岗局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4600' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 光明局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4700' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 坪山局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4800' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙华局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4900' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 大鹏局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '4A00' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 深汕监管局,\n" + 
					"    SUM(CASE WHEN S.CODE_VALUE = '440399' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 未分派,\n" + 
					"    (1-ROUND(SUM(CNT0)/SUM(CNT),4))*100 AS 合计,\n" + 
					"    3 AS NUM_CNT\n" + 
					"  FROM (SELECT S.CODE_VALUE,\n" + 
					"               COUNT(1) AS CNT,\n" + 
					"               SUM(CASE WHEN S.CHECKOUT_STATUS = '在用' AND S.FLAG = 0 AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE) THEN 1 ELSE 0 END) AS CNT0\n" + 
					"          FROM (SELECT S.*,NVL(G.SUPUNITBYSUP,'440399') AS CODE_VALUE,\n" + 
					"                     CASE\n" + 
					"                       WHEN P.EQU_ID IS NULL THEN 0\n" + 
					"                       ELSE 1\n" + 
					"                     END AS FLAG\n" + 
					"                FROM DC_SE_EQUIPMENT S, DC_SE_EQU_CONTAINER_PARAM P,TMEP G\n" + 
					"               WHERE S.ID = P.EQU_ID(+)\n" + 
					"                 AND IS_SIMPLE_CONTAINER(+) = '1'\n" + 
					"                 AND S.REG_CODE = G.ENTID(+)) S,\n" + 
					"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\n" + 
					"                  FROM DC_SC_DICT_ITEM T\n" + 
					"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T\n" + 
					"         WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\n" + 
					"           AND S.STATUS = '1'\n" + 
					"         GROUP BY S.CODE_VALUE\n" + 
					"        ) S\n" + 
					") T ORDER BY  品种种类,NUM_CNT )";
				
				
			}
			
		}
			
		System.out.println("\n" );
		System.out.println(sql);
		
		
		 resuts = dao_dc.queryForList(sql, params);
		 
		 System.out.println(resuts);
		 return resuts;

	}
}
