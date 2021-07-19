package com.gwssi.gdnb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.yaoxie.service.YaoXieQyService;


@Service
public class GunDongNianBaoService extends BaseService{
	private static  Logger log=Logger.getLogger(YaoXieQyService.class);
	
	public String getCreateSQL (String flag,String parentcode) {
		String sql = "SELECT S.ANCHEYEAR,\n" +
						"       --S.NB_DATE,\n" + 
						"       S.PARENTCODE,\n" + 
						"       S.DCODE,\n" + 
						"       NVL(D.CODENAME,'未认领') AS DEP_NAME,\n" + 
						"       S.FLAG,\n" + 
						"       S.SORT,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '01' THEN S.QYYB_CNT END),0) AS QYYB_CNT_01, --企业-应报数,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '01' THEN S.QYYBO_CNT END),0) AS QYYBO_CNT_01, --企业-已报数\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '02' THEN S.QYYB_CNT END),0) AS QYYB_CNT_02, --企业-应报数,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '02' THEN S.QYYBO_CNT END),0) AS QYYBO_CNT_02, --企业-已报数\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '03' THEN S.QYYB_CNT END),0) AS QYYB_CNT_03, --企业-应报数,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '03' THEN S.QYYBO_CNT END),0) AS QYYBO_CNT_03, --企业-已报数\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '04' THEN S.QYYB_CNT END),0) AS QYYB_CNT_04, --企业-应报数,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '04' THEN S.QYYBO_CNT END),0) AS QYYBO_CNT_04, --企业-已报数\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '05' THEN S.QYYB_CNT END),0) AS QYYB_CNT_05, --企业-应报数,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '05' THEN S.QYYBO_CNT END),0) AS QYYBO_CNT_05, --企业-已报数\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '06' THEN S.QYYB_CNT END),0) AS QYYB_CNT_06, --企业-应报数,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '06' THEN S.QYYBO_CNT END),0) AS QYYBO_CNT_06, --企业-已报数\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '07' THEN S.QYYB_CNT END),0) AS QYYB_CNT_07, --企业-应报数,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '07' THEN S.QYYBO_CNT END),0) AS QYYBO_CNT_07, --企业-已报数\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '08' THEN S.QYYB_CNT END),0) AS QYYB_CNT_08, --企业-应报数,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '08' THEN S.QYYBO_CNT END),0) AS QYYBO_CNT_08, --企业-已报数\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '09' THEN S.QYYB_CNT END),0) AS QYYB_CNT_09, --企业-应报数,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '09' THEN S.QYYBO_CNT END),0) AS QYYBO_CNT_09, --企业-已报数\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '10' THEN S.QYYB_CNT END),0) AS QYYB_CNT_10, --企业-应报数,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '10' THEN S.QYYBO_CNT END),0) AS QYYBO_CNT_10, --企业-已报数\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '11' THEN S.QYYB_CNT END),0) AS QYYB_CNT_11, --企业-应报数,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '11' THEN S.QYYBO_CNT END),0) AS QYYBO_CNT_11, --企业-已报数\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '12' THEN S.QYYB_CNT END),0) AS QYYB_CNT_12, --企业-应报数,\n" + 
						"       NVL(MAX(CASE WHEN S.YUE = '12' THEN S.QYYBO_CNT END),0) AS QYYBO_CNT_12 --企业-已报数\n" + 
						"  FROM DC_GDNB_TJ_BASE_YUE S, DC_BM_GXQY D\n" + 
						"WHERE S.DCODE = D.CODE(+)\n" + 
						"  AND S.FLAG = D.TYPE(+)\n" + 
						"  AND S.SORT = D.SORT(+)\n" ;
			
			  sql+= "  AND S.FLAG = '"+flag +"' \n" ;
			  
			  if(StringUtils.isNotEmpty(parentcode)) {
				  sql+= "  AND S.PARENTCODE = '"+parentcode +"' \n" ;
			  }
			  sql+=	"  AND S.NB_DATE IN (SELECT MAX(T.NB_DATE)\n" + 
						"                    FROM DC_GDNB_TJ_BASE_YUE T\n" + 
						"                      WHERE T.ANCHEYEAR = TO_CHAR(TO_NUMBER(SUBSTR(?,0,4)) -1)\n" + 
						"                        AND T.NB_DATE <= ? \n" + 
						"                    GROUP BY SUBSTR(T.NB_DATE, 1, 6))\n" + 
						" GROUP BY S.ANCHEYEAR,/*S.NB_DATE,*/S.PARENTCODE, S.DCODE,NVL(D.CODENAME,'未认领'),S.FLAG,S.SORT";

				sql+= " ORDER BY S.DCODE ";
		return sql;
	}
	
	
	
	public List queryCode_value(String timeStr) throws OptimusException {
		IPersistenceDAO dao_dc=getPersistenceDAO("dc_dc");
		List<String> str = new ArrayList<String>();//参数准备
		
		//sql.append("SELECT * FROM DC_GDNB_TJ_VIEW T WHERE T.FLAG IN ('5')  and t.NB_DATE = ?  ORDER BY T.DCODE");
		
		String sql	=	this.getCreateSQL("5",null);
		str.add(timeStr);
		str.add(timeStr);
		
		List list = dao_dc.queryForList(sql.toString(),str);
		return list;
	}
	
	
	public List queryGdnbSon(String depParentCode,String timeStr) throws OptimusException {
		IPersistenceDAO dao_dc=getPersistenceDAO("dc_dc");
		//StringBuffer sql = new StringBuffer(); 
		List<String> str = new ArrayList<String>();//参数准备
		
		//sql.append("SELECT * FROM DC_GDNB_TJ_VIEW T WHERE T.FLAG IN ('6') ");
	
		String sql = this.getCreateSQL("6", depParentCode);
		str.add(timeStr);
		str.add(timeStr);
		List list = dao_dc.queryForList(sql.toString(),str);
		return list;
	}
	
	

	public List queryQuShi() throws OptimusException {
		IPersistenceDAO dao_dc=getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer(); 
		List<String> str = new ArrayList<String>();//参数准备
		sql.append("select * from dc_gdnb_qs_view ");
		List list = dao_dc.queryForList(sql.toString(),null);
		return list;
	}
	
}
