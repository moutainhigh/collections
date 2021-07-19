package com.gwssi.report.shenziqiye.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.report.service.EBusinessService;
import com.gwssi.report.util.LogOperation;

@Service
public class ShengZiQiYeShuLiangService  extends BaseService {
	
	
	private static Logger log = Logger.getLogger(ShengZiQiYeHangYeService.class);
	LogOperation logop = new LogOperation();
	

	public List<Map> query(String beginTime, String endTime, HttpServletRequest req) {
		IPersistenceDAO dao = this.getPersistenceDAO("tm_updata");
		List list = new ArrayList();
		List<Map> list1 = new ArrayList<Map>();
		int thisYear = Integer.valueOf(endTime.split("-")[0]) ;
		int lastyear = thisYear - 1;
		String tempStartTime = lastyear +"-"+ beginTime.substring(beginTime.indexOf("-")+1);
		String tempEndTime = lastyear +"-"+ endTime.substring(endTime.indexOf("-")+1);
		
		
		list.add(beginTime);
		list.add(endTime);
		list.add(tempStartTime);
		list.add(tempEndTime);
		list.add(endTime);
		
		list.add(beginTime);
		list.add(endTime);
		list.add(tempStartTime);
		list.add(tempEndTime);
		list.add(endTime);
		
		String sql = 
				"SELECT XM \"项目\",\n" +
						"       BYXZ \"本月新增\",\n" + 
						"       CASE\n" + 
						"         WHEN SNBYXZ = 0 THEN\n" + 
						"          0\n" + 
						"         ELSE\n" + 
						"          ROUND((BYXZ - SNBYXZ) / SNBYXZ * 100, 2)\n" + 
						"       END || '%' \"同比增长\",\n" + 
						"       BYLJ \"本月累计\"\n" + 
						"  FROM (SELECT '企业总数' XM,\n" + 
						"               SUM((CASE\n" + 
						"                     WHEN B.ESTDATE >= TO_DATE(?, 'yyyy-mm-dd') AND\n" + 
						"                          B.ESTDATE <=\n" + 
						"                          TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') THEN\n" + 
						"                      1\n" + 
						"                     ELSE\n" + 
						"                      0\n" + 
						"                   END)) BYXZ, --开始时间、截止时间\n" + 
						"               SUM((CASE\n" + 
						"                     WHEN B.ESTDATE >= TO_DATE(?, 'yyyy-mm-dd') AND\n" + 
						"                          B.ESTDATE <=\n" + 
						"                          TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') THEN\n" + 
						"                      1\n" + 
						"                     ELSE\n" + 
						"                      0\n" + 
						"                   END)) SNBYXZ, --上年开始时间、上年截止时间\n" + 
						"               SUM((CASE\n" + 
						"                     WHEN B.ESTDATE <=\n" + 
						"                          TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND\n" + 
						"                          B.REGSTATE = '1' THEN\n" + 
						"                      1\n" + 
						"                     ELSE\n" + 
						"                      0\n" + 
						"                   END)) BYLJ --截止时间\n" + 
						"          FROM (SELECT T.PRIPID, T.ESTDATE, T.REGSTATE, T.REGCAP\n" + 
						"                  FROM E_BASEINFO T\n" + 
						"                 WHERE T.REGORG = '441521'\n" + 
						"                UNION ALL\n" + 
						"                SELECT T.PRIPID, T.ESTDATE, T.REGSTATE, T.FUNDAM AS REGCAP\n" + 
						"                  FROM E_PB_BASEINFO T\n" + 
						"                 WHERE T.REGORG = '441521') B\n" + 
						"         WHERE EXISTS\n" + 
						"         (SELECT 1\n" + 
						"                  FROM (SELECT P.PRIPID\n" + 
						"                          FROM E_INV_PERSON P\n" + 
						"                         WHERE P.CERNO LIKE '4403%'\n" + 
						"                        UNION ALL\n" + 
						"                        SELECT I.PRIPID\n" + 
						"                          FROM E_INV_INVESTMENT I\n" + 
						"                         WHERE NOT EXISTS (SELECT 1\n" + 
						"                                  FROM E_BASEINFO T\n" + 
						"                                 WHERE T.REGORG = '441521'\n" + 
						"                                   AND T.PRIPID = I.PRIPID)) S\n" + 
						"                 WHERE B.PRIPID = S.PRIPID))\n" + 
						"UNION ALL\n" + 
						"SELECT XM \"项目\",\n" + 
						"       BYXZ \"本月新增\",\n" + 
						"       CASE\n" + 
						"         WHEN SNBYXZ = 0 THEN\n" + 
						"          0\n" + 
						"         ELSE\n" + 
						"          ROUND((BYXZ - SNBYXZ) / SNBYXZ * 100, 2)\n" + 
						"       END || '%' \"同比增长\",\n" + 
						"       BYLJ \"本月累计\"\n" + 
						"  FROM (SELECT '注册资本' XM,\n" + 
						"               SUM((CASE\n" + 
						"                     WHEN B.ESTDATE >= TO_DATE(?, 'yyyy-mm-dd') AND\n" + 
						"                          B.ESTDATE <=\n" + 
						"                          TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') THEN\n" + 
						"                      ROUND(B.REGCAP, 2)\n" + 
						"                     ELSE\n" + 
						"                      0\n" + 
						"                   END)) BYXZ, --开始时间、截止时间\n" + 
						"               SUM((CASE\n" + 
						"                     WHEN B.ESTDATE >= TO_DATE(?, 'yyyy-mm-dd') AND\n" + 
						"                          B.ESTDATE <=\n" + 
						"                          TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') THEN\n" + 
						"                      ROUND(B.REGCAP, 2)\n" + 
						"                     ELSE\n" + 
						"                      0\n" + 
						"                   END)) SNBYXZ, --上年开始时间、上年截止时间\n" + 
						"               SUM((CASE\n" + 
						"                     WHEN B.ESTDATE <=\n" + 
						"                          TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND\n" + 
						"                          B.REGSTATE = '1' THEN\n" + 
						"                      ROUND(B.REGCAP, 2)\n" + 
						"                     ELSE\n" + 
						"                      0\n" + 
						"                   END)) BYLJ --截止时间\n" + 
						"          FROM (SELECT T.PRIPID, T.ESTDATE, T.REGSTATE, T.REGCAP\n" + 
						"                  FROM E_BASEINFO T\n" + 
						"                 WHERE T.REGORG = '441521'\n" + 
						"                UNION ALL\n" + 
						"                SELECT T.PRIPID, T.ESTDATE, T.REGSTATE, T.FUNDAM AS REGCAP\n" + 
						"                  FROM E_PB_BASEINFO T\n" + 
						"                 WHERE T.REGORG = '441521') B\n" + 
						"         WHERE EXISTS\n" + 
						"         (SELECT 1\n" + 
						"                  FROM (SELECT P.PRIPID\n" + 
						"                          FROM E_INV_PERSON P\n" + 
						"                         WHERE P.CERNO LIKE '4403%'\n" + 
						"                        UNION ALL\n" + 
						"                        SELECT I.PRIPID\n" + 
						"                          FROM E_INV_INVESTMENT I\n" + 
						"                         WHERE NOT EXISTS (SELECT 1\n" + 
						"                                  FROM E_BASEINFO T\n" + 
						"                                 WHERE T.REGORG = '441521'\n" + 
						"                                   AND T.PRIPID = I.PRIPID)) S\n" + 
						"                 WHERE B.PRIPID = S.PRIPID))";

 
			try {
				
				//System.out.println(sql);
				list = dao.queryForList(sql, list);
			} catch (OptimusException e) {
				e.printStackTrace();
			}
		return list;
	}



	
	
	public void main2() {
		
	}

}
