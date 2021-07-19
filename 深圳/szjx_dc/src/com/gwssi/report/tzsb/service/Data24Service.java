package com.gwssi.report.tzsb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class Data24Service extends BaseService {
	private static Logger log = Logger.getLogger(Data24Service.class);


	// 按区域
	public List getListByQuYu(Map map) throws OptimusException {
		StringBuffer sb = new StringBuffer();
		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();// 参数准备
		List resuts = null;
		/*sb.append("SELECT ");
		sb.append(" S.Old_Code_Value, ");
		sb.append("NVL(S.EQU_ADDR_COUNTY_CODE,'合计') AS 区域, ");
		sb.append(" SUM(CASE WHEN S.STATUS = '1' THEN S.CNT ELSE 0 END) AS 在用, ");
		sb.append("SUM(CASE WHEN S.STATUS = '2' THEN S.CNT ELSE 0 END) AS 停用, ");
		sb.append("SUM(CASE WHEN S.STATUS = '3' THEN S.CNT ELSE 0 END) AS 在建, ");
		sb.append("SUM(CASE WHEN S.STATUS = '4' THEN S.CNT ELSE 0 END) AS 注销, ");
		sb.append(" SUM(CASE WHEN S.STATUS = '5' THEN S.CNT ELSE 0 END) AS 报废, ");
		sb.append(" SUM(CASE WHEN S.STATUS = '6' THEN S.CNT ELSE 0 END) AS 待核实,    ");
		sb.append("SUM(CASE WHEN S.STATUS IN ('1','2','3','4','5','6') THEN S.CNT ELSE 0 END) AS 合计 ");
		sb.append("FROM (SELECT NVL(C.CODE_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,C.Old_Code_Value, ");
		sb.append("       S.STATUS, ");
		sb.append("    COUNT(1) AS CNT ");
		sb.append("FROM DC_SE_EQUIPMENT S, ");
		sb.append("     (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C ");
		sb.append(" WHERE S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE(+) ");
		sb.append(" GROUP BY C.CODE_NAME, S.STATUS,c.Old_Code_Value     ");
		sb.append(" ) S ");
		sb.append("GROUP BY S.Old_Code_Value,s.EQU_ADDR_COUNTY_CODE ");
		sb.append("order by s.Old_Code_Value ");
		/*resuts = dao_dc.queryForList(sb.toString(), params);
		return resuts;
		
		if(map.get("regCode")!=null&&(!map.get("regCode").equals(""))) {
			System.out.println(sb.toString());
			params.add((String) map.get("regCode"));
			
			String sql = "SELECT * from (" +sb.toString() + ") t where 1 =1 and t.OLD_CODE_VALUE = ? ";
			System.out.println("=====> "+ sql);
			return resuts = dao_dc.queryForList(sql, params);
		}else {
			System.out.println(sb.toString());
			return resuts = dao_dc.queryForList(sb.toString(), params);
		}*/
		
		
		String sql =" SELECT \r\n" + 
				"    s.SUPUNITBYSUP,\r\n" + 
				"    S.EQU_ADDR_COUNTY_CODE AS 区域,\r\n" + 
				"    SUM(CASE WHEN S.STATUS = '1' THEN S.CNT ELSE 0 END) AS 在用,\r\n" + 
				"    SUM(CASE WHEN S.STATUS = '2' THEN S.CNT ELSE 0 END) AS 停用,\r\n" + 
				"    SUM(CASE WHEN S.STATUS = '3' THEN S.CNT ELSE 0 END) AS 在建,\r\n" + 
				"    SUM(CASE WHEN S.STATUS = '4' THEN S.CNT ELSE 0 END) AS 注销,\r\n" + 
				"    SUM(CASE WHEN S.STATUS = '5' THEN S.CNT ELSE 0 END) AS 报废,\r\n" + 
				"    SUM(CASE WHEN S.STATUS = '6' THEN S.CNT ELSE 0 END) AS 待核实,   \r\n" + 
				"    SUM(CASE WHEN S.STATUS IN ('1','2','3','4','5','6') THEN S.CNT ELSE 0 END) AS 合计\r\n" + 
				"  FROM (SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,c.SUPUNITBYSUP,\r\n" + 
				"               S.STATUS,\r\n" + 
				"               COUNT(1) AS CNT\r\n" + 
				"          FROM DC_SE_EQUIPMENT S,\r\n" + 
				"               (SELECT C.ENTID,\r\n" + 
				"                       C.SUPUNITBYSUP,\r\n" + 
				"                       C.SUPDEPBYSUP,\r\n" + 
				"                       C.GRIDBYSUP,\r\n" + 
				"                       U.DEP_NAME,\r\n" + 
				"                       U1.DEP_NAME AS SHOU_NAME,\r\n" + 
				"                       G.GRIDNAME\r\n" + 
				"                  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\r\n" + 
				"                       DC_JG_SYS_RIGHT_DEPARTMENT U,\r\n" + 
				"                       DC_JG_SYS_RIGHT_DEPARTMENT U1,\r\n" + 
				"                       DC_JG_MS_BA_GRID           G\r\n" + 
				"                 WHERE C.SUPCODE = 'B100'\r\n" + 
				"                   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\r\n" + 
				"                   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\r\n" + 
				"                   AND G.GRID(+) = C.GRIDBYSUP) C\r\n" + 
				"         WHERE S.REG_CODE = C.ENTID\r\n" + 
				"         GROUP BY C.DEP_NAME, S.STATUS, c.SUPUNITBYSUP\r\n" + 
				"        ) S\r\n" + 
				" GROUP BY S.EQU_ADDR_COUNTY_CODE,s.SUPUNITBYSUP order by s.SUPUNITBYSUP ";
		
		
		
		if(map.get("regCode")!=null&&(!map.get("regCode").equals(""))) {
			params.add((String) map.get("regCode"));
			
			sql = "SELECT * from ( " +sql + " ) t where 1 =1 and t.SUPUNITBYSUP = ? ";
			System.out.println("=====> "+ sql);
			
			
			if(map.get("adminbrancode")!=null&&(!map.get("adminbrancode").equals(""))) {
				String admicode = (String) map.get("adminbrancode");
				//String regCode = (String) map.get("regCode");
				//params.clear();
			   /*sql   = "SELECT \r\n" + 
						"    s.SUPUNITBYSUP,s.SUPDEPBYSUP,\r\n" + 
						"    S.EQU_ADDR_COUNTY_CODE AS 区域,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '1' THEN S.CNT ELSE 0 END) AS 在用,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '2' THEN S.CNT ELSE 0 END) AS 停用,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '3' THEN S.CNT ELSE 0 END) AS 在建,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '4' THEN S.CNT ELSE 0 END) AS 注销,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '5' THEN S.CNT ELSE 0 END) AS 报废,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '6' THEN S.CNT ELSE 0 END) AS 待核实,   \r\n" + 
						"    SUM(CASE WHEN S.STATUS IN ('1','2','3','4','5','6') THEN S.CNT ELSE 0 END) AS 合计\r\n" + 
						"  FROM (SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,c.SUPUNITBYSUP,c.SUPDEPBYSUP,\r\n" + 
						"               S.STATUS,\r\n" + 
						"               COUNT(1) AS CNT\r\n" + 
						"          FROM DC_SE_EQUIPMENT S,\r\n" + 
						"               (SELECT C.ENTID,\r\n" + 
						"                       C.SUPUNITBYSUP,\r\n" + 
						"                       C.SUPDEPBYSUP,\r\n" + 
						"                       C.GRIDBYSUP,\r\n" + 
						"                       U.DEP_NAME,\r\n" + 
						"                       U1.DEP_NAME AS SHOU_NAME,\r\n" + 
						"                       G.GRIDNAME\r\n" + 
						"                  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\r\n" + 
						"                       DC_JG_SYS_RIGHT_DEPARTMENT U,\r\n" + 
						"                       DC_JG_SYS_RIGHT_DEPARTMENT U1,\r\n" + 
						"                       DC_JG_MS_BA_GRID           G\r\n" + 
						"                 WHERE C.SUPCODE = 'B100'\r\n" + 
						"                   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\r\n" + 
						"                   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\r\n" + 
						"                   AND G.GRID(+) = C.GRIDBYSUP) C\r\n" + 
						"         WHERE S.REG_CODE = C.ENTID\r\n" + 
						"         GROUP BY C.DEP_NAME, S.STATUS, c.SUPUNITBYSUP,c.SUPDEPBYSUP\r\n" + 
						"        ) S\r\n" + 
						" GROUP BY S.EQU_ADDR_COUNTY_CODE,s.SUPUNITBYSUP,s.SUPDEPBYSUP ";*/
				
				sql =" SELECT \r\n" + 
						"    s.SUPUNITBYSUP,s.SUPDEPBYSUP,\r\n" + 
						"    S.SHOU_NAME AS 区域,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '1' THEN S.CNT ELSE 0 END) AS 在用,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '2' THEN S.CNT ELSE 0 END) AS 停用,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '3' THEN S.CNT ELSE 0 END) AS 在建,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '4' THEN S.CNT ELSE 0 END) AS 注销,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '5' THEN S.CNT ELSE 0 END) AS 报废,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '6' THEN S.CNT ELSE 0 END) AS 待核实,   \r\n" + 
						"    SUM(CASE WHEN S.STATUS IN ('1','2','3','4','5','6') THEN S.CNT ELSE 0 END) AS 合计\r\n" + 
						"  FROM (SELECT NVL(C.DEP_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,c.SUPUNITBYSUP,c.SUPDEPBYSUP,c.SHOU_NAME,\r\n" + 
						"               S.STATUS,\r\n" + 
						"               COUNT(1) AS CNT\r\n" + 
						"          FROM DC_SE_EQUIPMENT S,\r\n" + 
						"               (SELECT C.ENTID,\r\n" + 
						"                       C.SUPUNITBYSUP,\r\n" + 
						"                       C.SUPDEPBYSUP,\r\n" + 
						"                       C.GRIDBYSUP,\r\n" + 
						"                       U.DEP_NAME,\r\n" + 
						"                       U1.DEP_NAME AS SHOU_NAME,\r\n" + 
						"                       G.GRIDNAME\r\n" + 
						"                  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\r\n" + 
						"                       DC_JG_SYS_RIGHT_DEPARTMENT U,\r\n" + 
						"                       DC_JG_SYS_RIGHT_DEPARTMENT U1,\r\n" + 
						"                       DC_JG_MS_BA_GRID           G\r\n" + 
						"                 WHERE C.SUPCODE = 'B100'\r\n" + 
						"                   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\r\n" + 
						"                   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\r\n" + 
						"                   AND G.GRID(+) = C.GRIDBYSUP) C\r\n" + 
						"         WHERE S.REG_CODE = C.ENTID\r\n" + 
						"         GROUP BY C.DEP_NAME, S.STATUS, c.SUPUNITBYSUP,c.SUPDEPBYSUP,c.SHOU_NAME\r\n" + 
						"        ) S\r\n" + 
						" GROUP BY S.EQU_ADDR_COUNTY_CODE,s.SUPUNITBYSUP,s.SUPDEPBYSUP,s.SHOU_NAME  ";
				
			    sql ="select * from (" +sql+ " ) t where 1=1 and t.SUPUNITBYSUP =? and t.SUPDEPBYSUP = ? ";
				params.add(admicode);
			}
		}
		System.out.println("\n lasted =====> "+ sql);
		return resuts = dao_dc.queryForList(sql, params);
	}

	// 按品种查询
	public List getListByPinZhong(Map map) throws OptimusException {
		StringBuffer sb = new StringBuffer();
		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();// 参数准备
		List resuts = null;
		
		/*List resuts = null;
		sb.append(" SELECT "); 
		sb.append(" s.item_code, ");
		sb.append("  NVL(S.ITEM_VALUE,'合计') AS 设备品种, ");
		sb.append("  SUM(CASE WHEN S.STATUS = '1' THEN S.CNT ELSE 0 END) AS 在用, ");
		sb.append("  SUM(CASE WHEN S.STATUS = '2' THEN S.CNT ELSE 0 END) AS 停用, ");
		sb.append("  SUM(CASE WHEN S.STATUS = '3' THEN S.CNT ELSE 0 END) AS 在建, ");
		sb.append("  SUM(CASE WHEN S.STATUS = '4' THEN S.CNT ELSE 0 END) AS 注销, ");
		sb.append("  SUM(CASE WHEN S.STATUS = '5' THEN S.CNT ELSE 0 END) AS 报废, ");
		sb.append("  SUM(CASE WHEN S.STATUS = '6' THEN S.CNT ELSE 0 END) AS 待核实,    ");
		sb.append("   SUM(CASE WHEN S.STATUS IN ('1','2','3','4','5','6') THEN S.CNT ELSE 0 END) AS 合计 ");
		sb.append("  FROM (SELECT NVL(T.ITEM_VALUE, '待分') AS ITEM_VALUE,t.item_code, ");
		sb.append("              S.STATUS, ");
		sb.append("              COUNT(1) AS CNT ");
		sb.append("        FROM DC_SE_EQUIPMENT S, ");
		sb.append("           (SELECT T.ITEM_CODE, T.ITEM_VALUE ");
		sb.append("               FROM DC_SC_DICT_ITEM T ");
		sb.append("              WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T ");
		sb.append("       WHERE  SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1) ");
		sb.append("       GROUP BY T.ITEM_VALUE, S.STATUS,t.item_code    ");     
		sb.append("      ) S ");
		sb.append(" GROUP BY item_code,s.ITEM_VALUE ");
		sb.append("  order by s.item_code ");
		resuts = dao_dc.queryForList(sb.toString(), params);*/
		
		
		String sql ="SELECT \r\n" + 
				"    S.ITEM_VALUE  AS 设备品种,\r\n" + 
				"    SUM(CASE WHEN S.STATUS = '1' THEN S.CNT ELSE 0 END) AS 在用,\r\n" + 
				"    SUM(CASE WHEN S.STATUS = '2' THEN S.CNT ELSE 0 END) AS 停用,\r\n" + 
				"    SUM(CASE WHEN S.STATUS = '3' THEN S.CNT ELSE 0 END) AS 在建,\r\n" + 
				"    SUM(CASE WHEN S.STATUS = '4' THEN S.CNT ELSE 0 END) AS 注销,\r\n" + 
				"    SUM(CASE WHEN S.STATUS = '5' THEN S.CNT ELSE 0 END) AS 报废,\r\n" + 
				"    SUM(CASE WHEN S.STATUS = '6' THEN S.CNT ELSE 0 END) AS 待核实,   \r\n" + 
				"    SUM(CASE WHEN S.STATUS IN ('1','2','3','4','5','6') THEN S.CNT ELSE 0 END) AS 合计\r\n" + 
				"  FROM (SELECT NVL(T.ITEM_VALUE, '待分') AS ITEM_VALUE,\r\n" + 
				"               S.STATUS,\r\n" + 
				"               COUNT(1) AS CNT\r\n" + 
				"          FROM DC_SE_EQUIPMENT S,\r\n" + 
				"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\r\n" + 
				"                  FROM DC_SC_DICT_ITEM T\r\n" + 
				"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,\r\n" + 
				"               (SELECT C.ENTID,\r\n" + 
				"                       C.SUPUNITBYSUP,\r\n" + 
				"                       C.SUPDEPBYSUP,\r\n" + 
				"                       C.GRIDBYSUP,\r\n" + 
				"                       U.DEP_NAME,\r\n" + 
				"                       U1.DEP_NAME AS SHOU_NAME,\r\n" + 
				"                       G.GRIDNAME\r\n" + 
				"                  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\r\n" + 
				"                       DC_JG_SYS_RIGHT_DEPARTMENT U,\r\n" + 
				"                       DC_JG_SYS_RIGHT_DEPARTMENT U1,\r\n" + 
				"                       DC_JG_MS_BA_GRID           G\r\n" + 
				"                 WHERE C.SUPCODE = 'B100'\r\n" + 
				"                   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\r\n" + 
				"                   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\r\n" + 
				"                   AND G.GRID(+) = C.GRIDBYSUP) C\r\n" + 
				"         WHERE  SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\r\n" + 
				"           AND S.REG_CODE = C.ENTID(+) \r\n" + 
				"         GROUP BY T.ITEM_VALUE, S.STATUS        \r\n" + 
				"        ) S\r\n" + 
				" GROUP BY S.ITEM_VALUE\r\n ";
		
		
		if(map.get("regCode")!=null&&(!map.get("regCode").equals(""))) {
			params.add((String) map.get("regCode"));
			
			sql = "SELECT \r\n" + 
					"    s.SUPUNITBYSUP,\r\n" + 
					"    S.ITEM_VALUE  AS 设备品种,\r\n" + 
					"    SUM(CASE WHEN S.STATUS = '1' THEN S.CNT ELSE 0 END) AS 在用,\r\n" + 
					"    SUM(CASE WHEN S.STATUS = '2' THEN S.CNT ELSE 0 END) AS 停用,\r\n" + 
					"    SUM(CASE WHEN S.STATUS = '3' THEN S.CNT ELSE 0 END) AS 在建,\r\n" + 
					"    SUM(CASE WHEN S.STATUS = '4' THEN S.CNT ELSE 0 END) AS 注销,\r\n" + 
					"    SUM(CASE WHEN S.STATUS = '5' THEN S.CNT ELSE 0 END) AS 报废,\r\n" + 
					"    SUM(CASE WHEN S.STATUS = '6' THEN S.CNT ELSE 0 END) AS 待核实,   \r\n" + 
					"    SUM(CASE WHEN S.STATUS IN ('1','2','3','4','5','6') THEN S.CNT ELSE 0 END) AS 合计\r\n" + 
					"  FROM (SELECT NVL(T.ITEM_VALUE, '待分') AS ITEM_VALUE,S.STATUS,COUNT(1) AS CNT,c.SUPUNITBYSUP\r\n" + 
					"          FROM DC_SE_EQUIPMENT S,\r\n" + 
					"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\r\n" + 
					"                  FROM DC_SC_DICT_ITEM T\r\n" + 
					"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,\r\n" + 
					"               (SELECT C.ENTID,\r\n" + 
					"                       C.SUPUNITBYSUP,\r\n" + 
					"                       C.SUPDEPBYSUP,\r\n" + 
					"                       C.GRIDBYSUP,\r\n" + 
					"                       U.DEP_NAME,\r\n" + 
					"                       U1.DEP_NAME AS SHOU_NAME,\r\n" + 
					"                       G.GRIDNAME\r\n" + 
					"                  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\r\n" + 
					"                       DC_JG_SYS_RIGHT_DEPARTMENT U,\r\n" + 
					"                       DC_JG_SYS_RIGHT_DEPARTMENT U1,\r\n" + 
					"                       DC_JG_MS_BA_GRID           G\r\n" + 
					"                 WHERE C.SUPCODE = 'B100'\r\n" + 
					"                   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\r\n" + 
					"                   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\r\n" + 
					"                   AND G.GRID(+) = C.GRIDBYSUP) C\r\n" + 
					"         WHERE  SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\r\n" + 
					"           AND S.REG_CODE = C.ENTID(+) \r\n" + 
					"         GROUP BY T.ITEM_VALUE, S.STATUS,c.SUPUNITBYSUP       \r\n" + 
					"        ) S\r\n" + 
					" GROUP BY S.ITEM_VALUE,s.SUPUNITBYSUP ";
			
			
			sql ="select * from ( "+sql+" ) t   where t.SUPUNITBYSUP= ? ";
			
			
			if(map.get("adminbrancode")!=null&&(!map.get("adminbrancode").equals(""))) {
				String admicode = (String) map.get("adminbrancode");
				params.clear();
				params.add(admicode);
				sql="SELECT \r\n" + 
						"    s.SUPDEPBYSUP,\r\n" + 
						"    S.ITEM_VALUE  AS 设备品种,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '1' THEN S.CNT ELSE 0 END) AS 在用,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '2' THEN S.CNT ELSE 0 END) AS 停用,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '3' THEN S.CNT ELSE 0 END) AS 在建,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '4' THEN S.CNT ELSE 0 END) AS 注销,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '5' THEN S.CNT ELSE 0 END) AS 报废,\r\n" + 
						"    SUM(CASE WHEN S.STATUS = '6' THEN S.CNT ELSE 0 END) AS 待核实,   \r\n" + 
						"    SUM(CASE WHEN S.STATUS IN ('1','2','3','4','5','6') THEN S.CNT ELSE 0 END) AS 合计\r\n" + 
						"  FROM (SELECT NVL(T.ITEM_VALUE, '待分') AS ITEM_VALUE,S.STATUS,COUNT(1) AS CNT,c.SUPDEPBYSUP\r\n" + 
						"          FROM DC_SE_EQUIPMENT S,\r\n" + 
						"              (SELECT T.ITEM_CODE, T.ITEM_VALUE\r\n" + 
						"                  FROM DC_SC_DICT_ITEM T\r\n" + 
						"                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T,\r\n" + 
						"               (SELECT C.ENTID,\r\n" + 
						"                       C.SUPUNITBYSUP,\r\n" + 
						"                       C.SUPDEPBYSUP,\r\n" + 
						"                       C.GRIDBYSUP,\r\n" + 
						"                       U.DEP_NAME,\r\n" + 
						"                       U1.DEP_NAME AS SHOU_NAME,\r\n" + 
						"                       G.GRIDNAME\r\n" + 
						"                  FROM DC_JG_MS_BA_ENT_ALL_INFO   C,\r\n" + 
						"                       DC_JG_SYS_RIGHT_DEPARTMENT U,\r\n" + 
						"                       DC_JG_SYS_RIGHT_DEPARTMENT U1,\r\n" + 
						"                       DC_JG_MS_BA_GRID           G\r\n" + 
						"                 WHERE C.SUPCODE = 'B100'\r\n" + 
						"                   AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP\r\n" + 
						"                   AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP\r\n" + 
						"                   AND G.GRID(+) = C.GRIDBYSUP) C\r\n" + 
						"         WHERE  SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1)\r\n" + 
						"           AND S.REG_CODE = C.ENTID(+) \r\n" + 
						"         GROUP BY T.ITEM_VALUE, S.STATUS,c.SUPDEPBYSUP       \r\n" + 
						"        ) S\r\n" + 
						" GROUP BY S.ITEM_VALUE,s.SUPDEPBYSUP ";
						
						sql = "select * from ( "+ sql +" ) t where t.SUPDEPBYSUP=? ";
			
			}
			
		} 
		System.out.println("\nLast SQL \n ");
		System.out.println(sql);
		
		resuts = dao_dc.queryForList(sql, params);
		
		return resuts;
	}
}
