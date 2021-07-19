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
public class Data22Service  extends BaseService {
	private static Logger log = Logger.getLogger(Data22Service.class);


	public List<Map<String, Object>> getSheBeiStatus() throws OptimusException {
		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT name as text , value as value from DC_TZSB_CODE where  type='sbStatus_data22'  ");
		List list = dao_dc.queryForList(sql.toString(), null);
		return list;
	}
	
	
	public List queryListByChangSuo(Map map) throws OptimusException {
		
		
		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();// 参数准备
		List resuts = null;

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append("  s.old_code_value, ");
		sb.append(" s.EQU_ADDR_COUNTY_CODE, ");
		sb.append("  SUM(CASE WHEN S.APPLY_LOCATION = '01' THEN S.CNT ELSE 0 END) AS 车站, ");
		sb.append("  SUM(CASE WHEN S.APPLY_LOCATION = '02' THEN S.CNT ELSE 0 END) AS 口岸, ");
		sb.append("  SUM(CASE WHEN S.APPLY_LOCATION = '03' THEN S.CNT ELSE 0 END) AS 客运码头, ");
		sb.append("  SUM(CASE WHEN S.APPLY_LOCATION = '04' THEN S.CNT ELSE 0 END) AS 商场, ");
		sb.append("  SUM(CASE WHEN S.APPLY_LOCATION = '05' THEN S.CNT ELSE 0 END) AS 学校, ");
		sb.append("  SUM(CASE WHEN S.APPLY_LOCATION = '06' THEN S.CNT ELSE 0 END) AS 幼儿园, ");
		sb.append("  SUM(CASE WHEN S.APPLY_LOCATION = '07' THEN S.CNT ELSE 0 END) AS 医院, ");
		sb.append("  SUM(CASE WHEN S.APPLY_LOCATION = '08' THEN S.CNT ELSE 0 END) AS 体育场馆,     ");
		sb.append("   SUM(CASE WHEN S.APPLY_LOCATION = '09' THEN S.CNT ELSE 0 END) AS 展览馆, ");
		sb.append("   SUM(CASE WHEN S.APPLY_LOCATION = '10' THEN S.CNT ELSE 0 END) AS 公园, ");
		sb.append("    SUM(CASE WHEN S.APPLY_LOCATION LIKE '11' THEN S.CNT ELSE 0 END) AS 地铁,     ");
		sb.append("    SUM(CASE WHEN S.APPLY_LOCATION IN ('01','02','03','04','05','06','07','08','09','10','11') THEN S.CNT ELSE 0 END) AS 合计 ");
		sb.append("   FROM (SELECT NVL(C.CODE_NAME, '待分区域') AS EQU_ADDR_COUNTY_CODE,c.old_code_value, ");
		sb.append("                S.APPLY_LOCATION, ");
		sb.append("                COUNT(1) AS CNT ");
		sb.append("           FROM DC_SE_EQUIPMENT S, ");
		sb.append("                (SELECT * FROM CODEDATA C WHERE C.CODE_INDEX = 'CA11') C ");
		sb.append("         WHERE S.EQU_ADDR_COUNTY_CODE = C.CODE_VALUE(+) ");
		sb.append("        GROUP BY C.CODE_NAME, S.APPLY_LOCATION,c.old_code_value   ");      
		sb.append("       ) S ");
		sb.append("  GROUP BY s.old_code_value,s.EQU_ADDR_COUNTY_CODE ");
		sb.append("  order by s.old_code_value ");
		
		
		

		if(map.get("regCode")!=null&&(!map.get("regCode").equals(""))) {
			System.out.println(sb.toString());
			params.add((String) map.get("regCode"));
			
			
			String sql = "SELECT * from (" +sb.toString() + ") t where 1 =1 and t.OLD_CODE_VALUE = ? ";
			
			
			
			if(map.get("adminbrancode")!=null&&(!map.get("adminbrancode").equals(""))) {
				String admicode = (String) map.get("adminbrancode");
				String regCode = (String) map.get("regCode");
				params.clear();
				sql = this.getListSQLBYJianGuan(admicode);
				params.add(admicode);
			}
			System.out.println("=====> "+ sql);
			return resuts = dao_dc.queryForList(sql, params);
		}else {
			System.out.println(sb.toString());
			return resuts = dao_dc.queryForList(sb.toString(), params);
		}
	}
	
	
	
	
	
	
	
	
	public List queryByType(Map map) throws OptimusException {
		
		
		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		List<String> params = new ArrayList<String>();// 参数准备
		List resuts = null;

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append(" 	   s.ITEM_CODE, ");
		sb.append(" 	   s.EQU_ADDR_COUNTY_CODE, ");
		sb.append("  SUM(CASE WHEN S.APPLY_LOCATION = '01' THEN S.CNT ELSE 0 END) AS 车站, ");
		sb.append("    SUM(CASE WHEN S.APPLY_LOCATION = '02' THEN S.CNT ELSE 0 END) AS 口岸, ");
		sb.append("     SUM(CASE WHEN S.APPLY_LOCATION = '03' THEN S.CNT ELSE 0 END) AS 客运码头, ");
		sb.append("     SUM(CASE WHEN S.APPLY_LOCATION = '04' THEN S.CNT ELSE 0 END) AS 商场, ");
		sb.append(" 	    SUM(CASE WHEN S.APPLY_LOCATION = '05' THEN S.CNT ELSE 0 END) AS 学校, ");
		sb.append("     SUM(CASE WHEN S.APPLY_LOCATION = '06' THEN S.CNT ELSE 0 END) AS 幼儿园, ");
		sb.append(" 	    SUM(CASE WHEN S.APPLY_LOCATION = '07' THEN S.CNT ELSE 0 END) AS 医院, ");
		sb.append(" 	    SUM(CASE WHEN S.APPLY_LOCATION = '08' THEN S.CNT ELSE 0 END) AS 体育场馆,     ");
		sb.append(" 	    SUM(CASE WHEN S.APPLY_LOCATION = '09' THEN S.CNT ELSE 0 END) AS 展览馆, ");
		sb.append(" 	    SUM(CASE WHEN S.APPLY_LOCATION = '10' THEN S.CNT ELSE 0 END) AS 公园, ");
		sb.append(" 	    SUM(CASE WHEN S.APPLY_LOCATION LIKE '11' THEN S.CNT ELSE 0 END) AS 地铁,     ");
		sb.append(" 	    SUM(CASE WHEN S.APPLY_LOCATION IN ('01','02','03','04','05','06','07','08','09','10','11') THEN S.CNT ELSE 0 END) AS 合计 ");
		sb.append(" 	  FROM (SELECT NVL(T.ITEM_VALUE, '待分区域') AS EQU_ADDR_COUNTY_CODE,t.item_code, ");
		sb.append(" 	               S.APPLY_LOCATION, ");
		sb.append(" 	               COUNT(1) AS CNT ");
		sb.append(" 	          FROM DC_SE_EQUIPMENT S, ");
		sb.append(" 	              (SELECT T.ITEM_CODE, T.ITEM_VALUE ");
		sb.append(" 	                  FROM DC_SC_DICT_ITEM T ");
		sb.append(" 		                 WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T ");
		sb.append(" 		         WHERE  SUBSTR(S.EQU_KIND, 1, 1) = SUBSTR(T.ITEM_CODE, 1, 1) ");
		sb.append(" 		         GROUP BY T.ITEM_VALUE, S.APPLY_LOCATION   ,t.ITEM_CODE     ") ;
		sb.append(" 		        ) S ");
		sb.append(" 		 GROUP BY s.item_code,S.EQU_ADDR_COUNTY_CODE ");
		sb.append(" 		 order by s.item_code ");

		

		if(map.get("regCode")!=null&&(!map.get("regCode").equals(""))) {
			params.add((String) map.get("regCode"));
			
			String sql = this.getEqumentByTypeWithJu();
			
			sql = "select * from ("+sql+") t where 1 = 1 and t.SUPUNITBYSUP = ? ";
			
			if(map.get("adminbrancode")!=null&&(!map.get("adminbrancode").equals(""))) {
				String admicode = (String) map.get("adminbrancode");
				String regCode = (String) map.get("regCode");
				params.clear();
				sql = this.getJianGuanSuoSheBeiType();
				sql = "select * from ("+sql+") t where 1 = 1 and t.SUPDEPBYSUP = ? ";
				System.out.println(sql);
				params.add(admicode);
			}
			
			return resuts = dao_dc.queryForList(sql, params);
		}else {
			System.out.println(sb.toString());
			return resuts = dao_dc.queryForList(sb.toString(), params);
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
	//监管所---设备场所
	public String getListSQLBYJianGuan(String jianguanCode) {
		String sql ="SELECT "+
			   " S.EQU_ADDR_COUNTY_CODE ,"+
			   " SUM(CASE WHEN S.APPLY_LOCATION = '01' THEN S.CNT ELSE 0 END) AS 车站,"+
			   "  SUM(CASE WHEN S.APPLY_LOCATION = '02' THEN S.CNT ELSE 0 END) AS 口岸,"+
			   " SUM(CASE WHEN S.APPLY_LOCATION = '03' THEN S.CNT ELSE 0 END) AS 客运码头,"+
			   " SUM(CASE WHEN S.APPLY_LOCATION = '04' THEN S.CNT ELSE 0 END) AS 商场,"+
			   "  SUM(CASE WHEN S.APPLY_LOCATION = '05' THEN S.CNT ELSE 0 END) AS 学校,"+
			   "  SUM(CASE WHEN S.APPLY_LOCATION = '06' THEN S.CNT ELSE 0 END) AS 幼儿园,"+
			   "  SUM(CASE WHEN S.APPLY_LOCATION = '07' THEN S.CNT ELSE 0 END) AS 医院,"+
			   "  SUM(CASE WHEN S.APPLY_LOCATION = '08' THEN S.CNT ELSE 0 END) AS 体育场馆,"+    
			   " SUM(CASE WHEN S.APPLY_LOCATION = '09' THEN S.CNT ELSE 0 END) AS 展览馆,"+
			   "  SUM(CASE WHEN S.APPLY_LOCATION = '10' THEN S.CNT ELSE 0 END) AS 公园,"+
			   "  SUM(CASE WHEN S.APPLY_LOCATION LIKE '11' THEN S.CNT ELSE 0 END) AS 地铁,"+    
			   "  SUM(CASE WHEN S.APPLY_LOCATION IN ('01','02','03','04','05','06','07','08','09','10','11') THEN S.CNT ELSE 0 END) AS 合计 "+
			   "  FROM (SELECT T.shou_name AS EQU_ADDR_COUNTY_CODE, "+
			   "            S.APPLY_LOCATION ,"+
			   "            COUNT(1) AS CNT  "+
			   "      FROM DC_SE_EQUIPMENT S , "+
			   "           (SELECT C.ENTID,"+
			   "                   C.SUPUNITBYSUP,"+
			   "                    C.SUPDEPBYSUP,"+
			   "                     C.GRIDBYSUP,"+
			   "                     U.DEP_NAME,"+
			   "                   U1.DEP_NAME AS SHOU_NAME,"+
			    "                  G.GRIDNAME "+
			    "            FROM DC_JG_MS_BA_ENT_ALL_INFO   C,"+
			   "                DC_JG_SYS_RIGHT_DEPARTMENT U,"+
			   "                   DC_JG_SYS_RIGHT_DEPARTMENT U1,"+
			   "                  DC_JG_MS_BA_GRID           G "+
			   "            WHERE C.SUPCODE = 'B100' "+
			   "              AND U.SYS_RIGHT_DEPARTMENT_ID = C.SUPUNITBYSUP "+
			   "                 AND U1.SYS_RIGHT_DEPARTMENT_ID(+) = C.SUPDEPBYSUP "+
			   "                AND G.GRID(+) = C.GRIDBYSUP) T "+
			   "       WHERE S.REG_CODE = T.ENTID   and t.SUPDEPBYSUP= ? "+
			   "        GROUP BY T.shou_name, S.APPLY_LOCATION     "+   
			   "      ) S "+
			   " GROUP BY S.EQU_ADDR_COUNTY_CODE ";
		System.out.println("\n" + sql);
		return sql;
		
		
	}
	
	
	
	
	
	//设备类别所属局
	public String getEqumentByTypeWithJu() {
		String sql ="SELECT \r\n" + 
				"    s.dep_name,\r\n" + 
				"    s.SUPUNITBYSUP,\r\n" + 
				"    S.EQU_ADDR_COUNTY_CODE ,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '01' THEN S.CNT ELSE 0 END) AS 车站,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '02' THEN S.CNT ELSE 0 END) AS 口岸,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '03' THEN S.CNT ELSE 0 END) AS 客运码头,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '04' THEN S.CNT ELSE 0 END) AS 商场,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '05' THEN S.CNT ELSE 0 END) AS 学校,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '06' THEN S.CNT ELSE 0 END) AS 幼儿园,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '07' THEN S.CNT ELSE 0 END) AS 医院,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '08' THEN S.CNT ELSE 0 END) AS 体育场馆,    \r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '09' THEN S.CNT ELSE 0 END) AS 展览馆,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '10' THEN S.CNT ELSE 0 END) AS 公园,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION LIKE '11' THEN S.CNT ELSE 0 END) AS 地铁,    \r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION IN ('01','02','03','04','05','06','07','08','09','10','11') THEN S.CNT ELSE 0 END) AS 合计\r\n" + 
				"  FROM (SELECT NVL(T.ITEM_VALUE, '待分区域') AS EQU_ADDR_COUNTY_CODE,c.dep_name,c.SUPUNITBYSUP,\r\n" + 
				"               S.APPLY_LOCATION,\r\n" + 
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
				"           AND  S.REG_CODE = C.ENTID\r\n" + 
				"         GROUP BY T.ITEM_VALUE, S.APPLY_LOCATION,C.dep_name,c.SUPUNITBYSUP       \r\n" + 
				"        ) S\r\n" + 
				" GROUP BY S.EQU_ADDR_COUNTY_CODE,s.SUPUNITBYSUP,dep_name";
		
		
		
		return sql;
	
	}
	
	
	
	
	//设备类别的所属监管所
	public String getJianGuanSuoSheBeiType() {
		
		String sql = "SELECT \r\n" + 
				"    s.dep_name,\r\n" + 
				"    s.SUPUNITBYSUP,\r\n" + 
				"    s.SUPDEPBYSUP,\r\n" + 
				"    S.EQU_ADDR_COUNTY_CODE ,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '01' THEN S.CNT ELSE 0 END) AS 车站,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '02' THEN S.CNT ELSE 0 END) AS 口岸,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '03' THEN S.CNT ELSE 0 END) AS 客运码头,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '04' THEN S.CNT ELSE 0 END) AS 商场,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '05' THEN S.CNT ELSE 0 END) AS 学校,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '06' THEN S.CNT ELSE 0 END) AS 幼儿园,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '07' THEN S.CNT ELSE 0 END) AS 医院,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '08' THEN S.CNT ELSE 0 END) AS 体育场馆,    \r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '09' THEN S.CNT ELSE 0 END) AS 展览馆,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION = '10' THEN S.CNT ELSE 0 END) AS 公园,\r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION LIKE '11' THEN S.CNT ELSE 0 END) AS 地铁,    \r\n" + 
				"    SUM(CASE WHEN S.APPLY_LOCATION IN ('01','02','03','04','05','06','07','08','09','10','11') THEN S.CNT ELSE 0 END) AS 合计\r\n" + 
				"  FROM (SELECT NVL(T.ITEM_VALUE, '待分区域') AS EQU_ADDR_COUNTY_CODE,c.dep_name,c.SUPUNITBYSUP,c.SUPDEPBYSUP,\r\n" + 
				"               S.APPLY_LOCATION,\r\n" + 
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
				"           AND  S.REG_CODE = C.ENTID\r\n" + 
				"         GROUP BY T.ITEM_VALUE, S.APPLY_LOCATION,C.dep_name,c.SUPUNITBYSUP,c.SUPDEPBYSUP     \r\n" + 
				"        ) S\r\n" + 
				" GROUP BY S.EQU_ADDR_COUNTY_CODE,s.SUPUNITBYSUP,dep_name,s.SUPDEPBYSUP ";


		return sql;
		
	}
	
}
