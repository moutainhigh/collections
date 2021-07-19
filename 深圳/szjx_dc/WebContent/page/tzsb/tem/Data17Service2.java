package com.gwssi.report.tzsb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;


//原来的  使用不到了
//@Service   
public class Data17Service2 extends BaseService {
	private static Logger log = Logger.getLogger(Data17Service2.class);


	public List queryListZaiYong(Map map) throws OptimusException {

		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();// 参数准备
		List resuts = null;

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append(" S.OLD_CODE_VALUE, ");
		sb.append("   S.EQU_ADDR_COUNTY_CODE AS 项目, ");
		sb.append("     SUM(CASE WHEN S.ITEM_VALUE = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉, ");
		sb.append("     SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器合计, ");
		sb.append("     SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) - SUM(S.CNT0) AS 非简单压力容器, ");
		sb.append("     SUM(CASE WHEN S.ITEM_VALUE = '电梯' THEN S.CNT ELSE 0 END) AS 电梯, ");
		sb.append("     SUM(CASE WHEN S.ITEM_VALUE = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械, ");
		sb.append("     SUM(CASE WHEN S.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场车, ");
		sb.append("     SUM(CASE WHEN S.ITEM_VALUE = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 游乐设施, ");
		sb.append("     SUM(CASE WHEN S.ITEM_VALUE = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道, ");
		sb.append("     SUM(CASE WHEN S.ITEM_VALUE = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道, ");
		sb.append("     SUM(S.CNT) AS 合计 ");
		sb.append("   FROM (SELECT NVL(C.CODE_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,C.OLD_CODE_VALUE, ");
		sb.append("	                T.ITEM_VALUE, ");
		sb.append("                COUNT(P.ID) AS CNT0, ");
		sb.append("                COUNT(1) AS CNT ");
		sb.append("           FROM DC_SE_EQUIPMENT S, ");
		sb.append("                (SELECT * ");
		sb.append("                   FROM DC_SE_EQU_CONTAINER_PARAM ");
		sb.append("                  WHERE IS_SIMPLE_CONTAINER = '1') P, ");
		sb.append("                (SELECT T.ITEM_CODE, T.ITEM_VALUE ");
		sb.append("                   FROM DC_SC_DICT_ITEM T ");
		sb.append("                  WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T, ");
		sb.append("                (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C ");
		sb.append("          WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1) ");
		sb.append("	            AND S.STATUS = '1' ");
		sb.append("            AND S.ID = P.EQU_ID(+) ");
		sb.append("            AND S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE(+) ");
		sb.append("	          GROUP BY C.CODE_NAME, T.ITEM_VALUE,C.OLD_CODE_VALUE ");
		sb.append("	         ) S ");
		sb.append("	  GROUP BY s.OLD_CODE_VALUE,S.EQU_ADDR_COUNTY_CODE ");
		sb.append("	  order by s.OLD_CODE_VALUE ");

		
		System.out.println(sb.toString());
		return resuts = dao_dc.queryForList(sb.toString(), params);
	}
	
	
	
	public List queryListByDanWei(Map map) throws OptimusException {

		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();// 参数准备
		List resuts = null;

		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT ");
		sb.append("s.old_code_value, ");
		sb.append(" S.EQU_ADDR_COUNTY_CODE, "); //使用单位统计
		//sb.append(" -- NVL(S.EQU_ADDR_COUNTY_CODE,'合计') AS 项目, ");
		sb.append("  SUM(CASE WHEN S.ITEM_VALUE = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉, ");
		sb.append("   SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器合计, ");
		sb.append("   SUM(CASE WHEN S.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) - SUM(S.CNT0) AS 非简单压力容器, ");
		sb.append("  SUM(CASE WHEN S.ITEM_VALUE = '电梯' THEN S.CNT ELSE 0 END) AS 电梯, ");
		sb.append("  SUM(CASE WHEN S.ITEM_VALUE = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械, ");
		sb.append("   SUM(CASE WHEN S.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场车, ");
		sb.append("   SUM(CASE WHEN S.ITEM_VALUE = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 游乐设施, ");
		sb.append("   SUM(CASE WHEN S.ITEM_VALUE = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道, ");
		sb.append("   SUM(CASE WHEN S.ITEM_VALUE = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道, ");
		sb.append("    SUM(S.CNT) AS 合计 ");
		sb.append("  FROM (SELECT T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE, SUM(CNT0) AS CNT0, SUM(CNT) AS CNT,t.old_code_value ");
		sb.append("          FROM (SELECT NVL(C.CODE_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,c.old_code_value, ");
		sb.append("                       T.ITEM_VALUE, ");
		sb.append("                       0 AS CNT0, ");
		sb.append("	                       1 AS CNT ");
		sb.append("	                  FROM DC_SE_EQUIPMENT S, ");
		sb.append("	                       (SELECT * ");
		sb.append("	                          FROM DC_SE_EQU_CONTAINER_PARAM ");
		sb.append("	                         WHERE IS_SIMPLE_CONTAINER = '1') P, ");
		sb.append("	                       (SELECT T.ITEM_CODE, T.ITEM_VALUE ");
		sb.append("	                          FROM DC_SC_DICT_ITEM T ");
		sb.append("	                         WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T, ");
		sb.append("	                       (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C ");
		sb.append("	                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1) ");
		sb.append("	                   AND S.ID = P.EQU_ID(+) ");
		sb.append("	                   AND S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE(+) ");
		sb.append("	                   AND S.STATUS = '1' ");
		sb.append("	                 GROUP BY C.CODE_NAME, T.ITEM_VALUE, S.USE_UNIT_ID,c.old_code_value ");
		sb.append("	                UNION ALL ");
		sb.append("	                SELECT NVL(C.CODE_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,c.old_code_value, ");
		sb.append("	                       T.ITEM_VALUE, ");
		sb.append("	                       1 AS CNT0, ");
		sb.append("	                       0 AS CNT ");
		sb.append("	                  FROM DC_SE_EQUIPMENT S, ");
		sb.append("	                       (SELECT * ");
		sb.append("	                          FROM DC_SE_EQU_CONTAINER_PARAM ");
		sb.append("	                         WHERE IS_SIMPLE_CONTAINER = '1') P, ");
		sb.append("	                       (SELECT T.ITEM_CODE, T.ITEM_VALUE ");
		sb.append("	                          FROM DC_SC_DICT_ITEM T ");
		sb.append("	                         WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T, ");
		sb.append("	                       (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C ");
		sb.append("	                 WHERE SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1) ");
		sb.append("		                   AND S.ID = P.EQU_ID ");
		sb.append("	                   AND S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE(+) ");
		sb.append("	                   AND S.STATUS = '1' ");
		sb.append("		                 GROUP BY C.CODE_NAME, T.ITEM_VALUE, S.USE_UNIT_ID,c.old_code_value) T ");
		sb.append("	         GROUP BY t.old_code_value, T.EQU_ADDR_COUNTY_CODE, T.ITEM_VALUE       ");
		sb.append("		        ) S ");
		sb.append("	 GROUP BY s.old_code_value, s.EQU_ADDR_COUNTY_CODE ");//--ROLLUP(S.EQU_ADDR_COUNTY_CODE) 
		sb.append("	order by s.old_code_value ");
		
		System.out.println("按使用单位统计的SQL\n");
		System.out.println(sb.toString()+"\n");
		return resuts = dao_dc.queryForList(sb.toString(), params);
	}
}
