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
public class Data18Service2 extends BaseService {

	public List queryListByDingJianLv(Map map) throws OptimusException {

		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();// 参数准备
		List resuts = null;

		// 福田局,罗湖局,南山局,盐田局,宝安局,龙岗局,光明局,坪山局,龙华局,大鹏局
		StringBuffer sb = new StringBuffer();
		/*
		 * List list = new ArrayList(); list.add("福田局"); list.add("罗湖局");
		 * list.add("南山局"); list.add("盐田局"); list.add("宝安局"); list.add("龙岗局");
		 * list.add("光明局"); list.add("坪山局"); list.add("龙华局"); list.add("大鹏局");
		 */

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

		/*
		 * for (Map.Entry<String, String> entry : codeMap.entrySet()) {
		 * System.out.println("Key = " + entry.getKey() + ", Value = " +
		 * entry.getValue()); }
		 */

		String regCode = (String) map.get("regCode");
		String adminbrancode = (String) map.get("adminbrancode");

		if (regCode != null && !regCode.equals("")) {
			String gongshangJu = keyMap.get(regCode);
			String codeOld = codeMap.get(gongshangJu);
			StringBuffer sb2 = new StringBuffer();
			
			sb2.append(" 				     SUM(CASE WHEN S.CODE_VALUE = '"+codeOld+"' THEN S.CNT ELSE 0 END) AS "+gongshangJu+",");
			/*sb2.append(" 				     SUM(CASE WHEN S.CODE_VALUE = '440303' THEN S.CNT ELSE 0 END) AS 罗湖局,");
			sb2.append(" 			     SUM(CASE WHEN S.CODE_VALUE = '440305' THEN S.CNT ELSE 0 END) AS 南山局,");
			sb2.append(" 			     SUM(CASE WHEN S.CODE_VALUE = '440308' THEN S.CNT ELSE 0 END) AS 盐田局,");
			sb2.append(" 			     SUM(CASE WHEN S.CODE_VALUE = '440306' THEN S.CNT ELSE 0 END) AS 宝安局,");
			sb2.append(" 			     SUM(CASE WHEN S.CODE_VALUE = '440307' THEN S.CNT ELSE 0 END) AS 龙岗局,");
			sb2.append(" 			     SUM(CASE WHEN S.CODE_VALUE = '440309' THEN S.CNT ELSE 0 END) AS 光明局,");
			sb2.append(" 		     SUM(CASE WHEN S.CODE_VALUE = '440310' THEN S.CNT ELSE 0 END) AS 坪山局,");
			sb2.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440342' THEN S.CNT ELSE 0 END) AS 龙华局,");
			sb2.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440343' THEN S.CNT ELSE 0 END) AS 大鹏局,   ");   */
			
			
			sb.append("SELECT 品种种类,统计类型,"+gongshangJu+",合计 ");
			sb.append(" FROM ( ");
			sb.append(" SELECT ");
			sb.append(" NVL(S.ITEM_VALUE,'总体') AS 品种种类, ");
			sb.append("   S.CNT_TYPE AS 统计类型, ");
			sb.append(sb2);            
			sb.append("   SUM(CASE WHEN S.CODE_VALUE IS NOT NULL THEN S.CNT ELSE 0 END) AS 合计, ");
			sb.append(" CASE WHEN CNT_TYPE = '超期数' THEN 1 ELSE 2 END AS NUM_CNT ");
			sb.append("  FROM (SELECT T.ITEM_VALUE,");
			sb.append("               '超期数' AS CNT_TYPE,");
			sb.append("               C.CODE_VALUE,");
			sb.append("	               COUNT(1) AS CNT,");
			sb.append("               0 AS CNT0");
			sb.append("   FROM DC_SE_EQUIPMENT S,");
			sb.append("       (SELECT T.ITEM_CODE, T.ITEM_VALUE ");
			sb.append("           FROM DC_SC_DICT_ITEM T ");
			sb.append("          WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,");
			sb.append("        (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C ");
			sb.append("  WHERE S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE ");
			sb.append("    AND SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)");
			sb.append("    AND S.STATUS = '1' ");
			sb.append("    AND S.CHECKOUT_STATUS = '在用' ");
			sb.append("    AND NOT EXISTS ( SELECT 1 FROM DC_SE_EQU_CONTAINER_PARAM P WHERE S.ID = P.EQU_ID AND P.IS_SIMPLE_CONTAINER='1' ) ");
			sb.append("    AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE) ");
			//sb.append("    --AND TRIM(S.NEXT_CHECKOUT_DATE) IS NOT NULL ");
			sb.append("  GROUP BY T.ITEM_VALUE,C.CODE_VALUE ");
			sb.append("   UNION ALL ");
			sb.append("     SELECT T.ITEM_VALUE, ");
			sb.append("        '在用总数' AS CNT_TYPE, ");
			sb.append("        C.CODE_VALUE, ");
			sb.append("        COUNT(1) AS CNT, ");
			sb.append("        0 AS CNT0 ");
			sb.append("   FROM DC_SE_EQUIPMENT S, ");
			sb.append("       (SELECT T.ITEM_CODE, T.ITEM_VALUE ");
			sb.append("           FROM DC_SC_DICT_ITEM T ");
			sb.append("          WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T, ");
			sb.append("        (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C ");
			sb.append("  WHERE S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE ");
			sb.append("    AND SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1) ");
			sb.append("    AND S.STATUS = '1' ");
			sb.append("    AND NOT EXISTS ( SELECT 1 FROM DC_SE_EQU_CONTAINER_PARAM P WHERE S.ID = P.EQU_ID AND P.IS_SIMPLE_CONTAINER='1' ) ");
			sb.append("  GROUP BY T.ITEM_VALUE,C.CODE_VALUE      ");             
			sb.append(" ) S ");
			sb.append(" GROUP BY ROLLUP(S.ITEM_VALUE),S.CNT_TYPE ");
			sb.append(" UNION ALL ");
			sb.append(" SELECT  ");
			sb.append("    NVL(S.ITEM_VALUE,'总体') AS 品种种类, ");
			sb.append("S.CNT_TYPE AS 统计类型, ");
			sb.append(sb2);           
			sb.append("	    (1-ROUND(SUM(CNT0)/SUM(CNT),4))*100 AS 合计, ");
			sb.append("	    3 AS NUM_CNT ");
			sb.append("	  FROM (SELECT T.ITEM_VALUE, ");
			sb.append("	               '定检率(%)' AS CNT_TYPE, ");
			sb.append("	               C.CODE_VALUE, ");
			sb.append("               COUNT(1) AS CNT, ");
			sb.append("               SUM(CASE WHEN S.CHECKOUT_STATUS = '在用' AND S.FLAG = 0 AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE) THEN 1 ELSE 0 END) AS CNT0 ");
			sb.append("          FROM (SELECT S.*, ");
			sb.append("                     CASE ");
			sb.append("	                       WHEN P.EQU_ID IS NULL THEN ");
			sb.append("	0 ");
			sb.append("                       ELSE ");
			sb.append("	1 ");
			sb.append("	                     END AS FLAG ");
			sb.append("               FROM DC_SE_EQUIPMENT S, DC_SE_EQU_CONTAINER_PARAM P ");
			sb.append("	               WHERE S.ID = P.EQU_ID(+) ");
			sb.append("                 AND IS_SIMPLE_CONTAINER(+) = '1') S, ");
			sb.append("              (SELECT T.ITEM_CODE, T.ITEM_VALUE ");
			sb.append("                  FROM DC_SC_DICT_ITEM T ");
			sb.append("             WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T, ");
			sb.append("               (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C ");
			sb.append("         WHERE S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE ");
			sb.append("	           AND SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1) ");
			sb.append("	           AND S.STATUS = '1' ");
			sb.append("	         GROUP BY T.ITEM_VALUE,C.CODE_VALUE     ");               
			sb.append("	        ) S ");
			sb.append("	 GROUP BY S.ITEM_VALUE,S.CNT_TYPE  ");
			sb.append("	UNION ALL ");
			sb.append("	SELECT  ");
			sb.append("	    '总体' AS 品种种类, ");
			sb.append("	    '定检率(%)' AS 统计类型, ");
			sb.append(sb2);             
			sb.append("    (1-ROUND(SUM(CNT0)/SUM(CNT),4))*100 AS 合计, ");
			sb.append("    3 AS NUM_CNT ");
			sb.append("  FROM (SELECT C.CODE_VALUE, ");
			sb.append("               COUNT(1) AS CNT, ");
			sb.append("               SUM(CASE WHEN S.CHECKOUT_STATUS = '在用' AND S.FLAG = 0 AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE) THEN 1 ELSE 0 END) AS CNT0 ");
			sb.append("          FROM (SELECT S.*, ");
			sb.append("	                     CASE ");
			sb.append("	                       WHEN P.EQU_ID IS NULL THEN ");
			sb.append("	0 ");
			sb.append("	                       ELSE ");
			sb.append("	1 ");
			sb.append("	                     END AS FLAG ");
			sb.append("	                FROM DC_SE_EQUIPMENT S, DC_SE_EQU_CONTAINER_PARAM P ");
			sb.append("	               WHERE S.ID = P.EQU_ID(+) ");
			sb.append("	                 AND IS_SIMPLE_CONTAINER(+) = '1') S, ");
			sb.append("              (SELECT T.ITEM_CODE, T.ITEM_VALUE ");
			sb.append("                  FROM DC_SC_DICT_ITEM T ");
			sb.append("                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T, ");
			sb.append("               (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C ");
			sb.append("         WHERE S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE ");
			sb.append("           AND SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1) ");
			sb.append("	           AND S.STATUS = '1' ");
			sb.append("	         GROUP BY C.CODE_VALUE         ");           
			sb.append("	        ) S ");
			sb.append("	) T ORDER BY  品种种类,NUM_CNT");
			
			
					System.out.println(sb.toString());
			
			
			
			
			
			
/*			sb.append("SELECT 品种种类,统计类型,福田局,罗湖局,南山局,盐田局,宝安局,龙岗局,光明局,坪山局,龙华局,大鹏局,合计");
			sb.append(" FROM ( ");
					sb.append(" 		 SELECT ");
					sb.append(" 		     NVL(S.ITEM_VALUE,'总体') AS 品种种类,");
					sb.append(" 		     S.CNT_TYPE AS 统计类型,");
					sb.append(" 				     SUM(CASE WHEN S.CODE_VALUE = '440304' THEN S.CNT ELSE 0 END) AS 福田局,");
					sb.append(" 				     SUM(CASE WHEN S.CODE_VALUE = '440303' THEN S.CNT ELSE 0 END) AS 罗湖局,");
					sb.append(" 			     SUM(CASE WHEN S.CODE_VALUE = '440305' THEN S.CNT ELSE 0 END) AS 南山局,");
					sb.append(" 			     SUM(CASE WHEN S.CODE_VALUE = '440308' THEN S.CNT ELSE 0 END) AS 盐田局,");
					sb.append(" 			     SUM(CASE WHEN S.CODE_VALUE = '440306' THEN S.CNT ELSE 0 END) AS 宝安局,");
					sb.append(" 			     SUM(CASE WHEN S.CODE_VALUE = '440307' THEN S.CNT ELSE 0 END) AS 龙岗局,");
					sb.append(" 			     SUM(CASE WHEN S.CODE_VALUE = '440309' THEN S.CNT ELSE 0 END) AS 光明局,");
					sb.append(" 		     SUM(CASE WHEN S.CODE_VALUE = '440310' THEN S.CNT ELSE 0 END) AS 坪山局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440342' THEN S.CNT ELSE 0 END) AS 龙华局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440343' THEN S.CNT ELSE 0 END) AS 大鹏局,   ");           
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE IS NOT NULL THEN S.CNT ELSE 0 END) AS 合计,");
					sb.append(" 		     CASE WHEN CNT_TYPE = '超期数' THEN 1 ELSE 2 END AS NUM_CNT");
					sb.append(" 		   FROM (SELECT T.ITEM_VALUE,");
					sb.append(" 	                '超期数' AS CNT_TYPE,");
					sb.append(" 	                C.CODE_VALUE,");
					sb.append(" 	                COUNT(1) AS CNT,");
					sb.append(" 	                0 AS CNT0");
					sb.append(" 	           FROM DC_SE_EQUIPMENT S,");
					sb.append(" 	               (SELECT T.ITEM_CODE, T.ITEM_VALUE");
					sb.append(" 	                   FROM DC_SC_DICT_ITEM T");
					sb.append(" 	                  WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,");
					sb.append(" 	                (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C");
					sb.append(" 	          WHERE S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE");
					sb.append(" 	            AND SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)");
					sb.append(" 	            AND S.STATUS = '1'");
					sb.append(" 	            AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE)");
					sb.append(" 	          GROUP BY T.ITEM_VALUE,C.CODE_VALUE");
					sb.append(" 	         UNION ALL");
					sb.append(" 	         SELECT T.ITEM_VALUE,");
					sb.append(" 	                '在用总数' AS CNT_TYPE,");
					sb.append(" 	                C.CODE_VALUE,");
					sb.append(" 	                COUNT(1) AS CNT,");
					sb.append(" 	                0 AS CNT0");
					sb.append(" 	           FROM DC_SE_EQUIPMENT S,");
					sb.append(" 	               (SELECT T.ITEM_CODE, T.ITEM_VALUE");
					sb.append("                   FROM DC_SC_DICT_ITEM T");
					sb.append(" 	                  WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,");
					sb.append(" 	                (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C");
					sb.append(" 	          WHERE S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE");
					sb.append(" 	            AND SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)");
					sb.append(" 	            AND S.STATUS = '1'");
					sb.append(" 	          GROUP BY T.ITEM_VALUE,C.CODE_VALUE   ");               
					sb.append("          ) S");
					sb.append(" 	  GROUP BY ROLLUP(S.ITEM_VALUE),S.CNT_TYPE");
					sb.append(" 	 UNION ALL");
					sb.append(" 	 SELECT ");
					sb.append(" 	     NVL(S.ITEM_VALUE,'总体') AS 品种种类,");
					sb.append(" 	     S.CNT_TYPE AS 统计类型,");
					sb.append("      SUM(CASE WHEN S.CODE_VALUE = '440304' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 福田局,");
					sb.append("      SUM(CASE WHEN S.CODE_VALUE = '440303' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 罗湖局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440305' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 南山局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440308' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 盐田局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440306' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 宝安局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440307' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙岗局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440309' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 光明局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440310' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 坪山局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440342' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙华局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440343' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 大鹏局,      ");        
					sb.append(" 	     (1-ROUND(SUM(CNT0)/SUM(CNT),4))*100 AS 合计,");
					sb.append(" 	     3 AS NUM_CNT");
					sb.append(" 	   FROM (SELECT T.ITEM_VALUE,");
					sb.append(" 	                '定检率(%)' AS CNT_TYPE,");
					sb.append(" 	                C.CODE_VALUE,");
					sb.append(" 	                COUNT(1) AS CNT,");
					sb.append(" 	                SUM(CASE WHEN S.CHECKOUT_STATUS = '在用' AND S.FLAG = 0 AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE) THEN 1 ELSE 0 END) AS CNT0");
					sb.append(" 	           FROM (SELECT S.*,");
					sb.append(" 	                      CASE");
					sb.append(" 	                        WHEN P.EQU_ID IS NULL THEN");
					sb.append(" 	 0");
					sb.append(" 	                        ELSE");
					sb.append(" 	 1");
					sb.append(" 	                      END AS FLAG");
					sb.append(" 	                 FROM DC_SE_EQUIPMENT S, DC_SE_EQU_CONTAINER_PARAM P");
					sb.append(" 	                WHERE S.ID = P.EQU_ID(+)");
					sb.append(" 	                  AND IS_SIMPLE_CONTAINER(+) = '1') S,");
					sb.append(" 	               (SELECT T.ITEM_CODE, T.ITEM_VALUE");
					sb.append(" 	                   FROM DC_SC_DICT_ITEM T");
					sb.append(" 	                  WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,");
					sb.append(" 	                (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C");
					sb.append(" 	          WHERE S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE");
					sb.append(" 	            AND SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)");
					sb.append(" 	            AND S.STATUS = '1'");
					sb.append(" 	          GROUP BY T.ITEM_VALUE,C.CODE_VALUE            ");       
					sb.append(" 	         ) S");
					sb.append(" 		  GROUP BY S.ITEM_VALUE,S.CNT_TYPE ");
					sb.append(" 		 UNION ALL");
					sb.append(" 	 SELECT ");
					sb.append(" 	     '总体' AS 品种种类,");
					sb.append(" 	     '定检率(%)' AS 统计类型,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440304' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 福田局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440303' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 罗湖局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440305' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 南山局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440308' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 盐田局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440306' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 宝安局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440307' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙岗局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440309' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 光明局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440310' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 坪山局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440342' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 龙华局,");
					sb.append(" 	     SUM(CASE WHEN S.CODE_VALUE = '440343' THEN (1-ROUND(CNT0/CNT,4))*100 ELSE 0 END) AS 大鹏局,   ");           
					sb.append(" 	     (1-ROUND(SUM(CNT0)/SUM(CNT),4))*100 AS 合计,");
					sb.append(" 	     3 AS NUM_CNT");
					sb.append("    FROM (SELECT C.CODE_VALUE,");
					sb.append("                 COUNT(1) AS CNT,");
					sb.append(" 	                SUM(CASE WHEN S.CHECKOUT_STATUS = '在用' AND S.FLAG = 0 AND S.NEXT_CHECKOUT_DATE < TRUNC(SYSDATE) THEN 1 ELSE 0 END) AS CNT0");
					sb.append(" 	           FROM (SELECT S.*,");
					sb.append(" 	                      CASE");
					sb.append(" 	                        WHEN P.EQU_ID IS NULL THEN");
					sb.append(" 	 0");
					sb.append(" 	                        ELSE");
					sb.append(" 	 1");
					sb.append(" 	                      END AS FLAG");
					sb.append(" 	                 FROM DC_SE_EQUIPMENT S, DC_SE_EQU_CONTAINER_PARAM P");
					sb.append(" 	                WHERE S.ID = P.EQU_ID(+)");
					sb.append(" 	                  AND IS_SIMPLE_CONTAINER(+) = '1') S,");
					sb.append(" 	               (SELECT T.ITEM_CODE, T.ITEM_VALUE");
					sb.append(" 	                   FROM DC_SC_DICT_ITEM T");
					sb.append(" 	                  WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,");
					sb.append(" 	                (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C");
					sb.append(" 	          WHERE S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE");
					sb.append(" 	            AND SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)");
					sb.append(" 	            AND S.STATUS = '1'");
					sb.append(" 	          GROUP BY C.CODE_VALUE         ");          
					sb.append(" 	         ) S ");
					sb.append(" 	 ) T ORDER BY  品种种类,NUM_CNT");*/
			
			
			
		}
		/*if (adminbrancode != null && !adminbrancode.equals("")) {

			String gongshangJu = keyMap.get(regCode);
			String codeOld = codeMap.get(gongshangJu);
		}*/

		if (regCode.equals("") && adminbrancode.equals("")) {
			sb = new StringBuffer();
			sb.append("select * from v_dc_tzsb_djl ");
			System.out.println("==============无参数==================");
			
		}
		
		System.out.println("================得到的SQL语句 =========");
		System.out.println(sb.toString());
		return resuts = dao_dc.queryForList(sb.toString(), params);

	}
}
