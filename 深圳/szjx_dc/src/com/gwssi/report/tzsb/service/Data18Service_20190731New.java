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
public class Data18Service_20190731New extends BaseService {

	public List queryListByDingJianLv(Map map) throws OptimusException {

		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();// 参数准备
		List<Map> resuts = null;
		
		
		String sql  =  
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

	
		
		
		 resuts = dao_dc.queryForList(sql, params);
		 
		 System.out.println(resuts);
		 return resuts;

	}
}
