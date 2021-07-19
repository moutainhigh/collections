package com.gwssi.report.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class EBusinessService extends BaseService {
	private static Logger log = Logger.getLogger(EBusinessService.class);

	
	
	/**
	 * 
	 
	 SELECT A.*, SUM(A.CNT) OVER() AS TOTAL
     FROM (SELECT S.A,
               SUM(CASE WHEN S.B = '电子商务' THEN NVL(S.CNT,0) ELSE 0 END) AS B1,
               SUM(CASE WHEN S.B = '邮购' THEN NVL(S.CNT,0) ELSE 0 END) AS B2,
               SUM(CASE WHEN S.B = '电话购物' THEN NVL(S.CNT,0) ELSE 0 END) AS B3,
               SUM(CASE WHEN S.B = '电视购物' THEN NVL(S.CNT,0) ELSE 0 END) AS B4,
               --SUM(CASE WHEN S.B IS NULL THEN NVL(S.CNT,0) ELSE 0 END) AS B5,        
               SUM(CASE WHEN S.C = '交易平台类' THEN NVL(S.CNT,0) ELSE 0 END) AS C1,
               SUM(CASE WHEN S.C = '应用类' THEN NVL(S.CNT,0) ELSE 0 END) AS C2,
               SUM(CASE WHEN S.C = '服务类' THEN NVL(S.CNT,0) ELSE 0 END) AS C3,
               SUM(CASE WHEN S.C = '互联网门户' THEN NVL(S.CNT,0) ELSE 0 END) AS C4,
               SUM(CASE WHEN S.C = '其他' THEN NVL(S.CNT,0) ELSE 0 END) AS C5,                 
               SUM(NVL(S.CNT, 0)) AS CNT,
               SUM(NVL(T.CNT, 0)) AS LASE_CNT
          FROM (SELECT T.NAME AS A,
                       M.REMSHOTYPE,
                       T1.NAME AS B,
                       M.WEBSITETYPE,
                       T2.NAME AS C,
                       COUNT(1) AS CNT
                  FROM DC_CPR_INFOWARE           S,
                       DC_CPR_INVOLVED_MAIN      M,
                       DC_CPR_INVOLVED_OBJECT    O,
                       DC_CODE.DC_12315_CODEDATA T,
                       DC_CODE.DC_12315_CODEDATA T1,
                       DC_CODE.DC_12315_CODEDATA T2
                 WHERE S.INVMAIID = M.INVMAIID
                   AND S.INVOBJID = O.INVOBJID
                   AND O.BUSINESSTYPE = 'CH20'
                   AND O.INVOBJTYPE = T.CODE
                   AND M.REMSHOTYPE = T1.CODE(+)
                   AND T1.CODETABLE(+) = 'ZH17'
                   AND M.WEBSITETYPE = T2.CODE(+)
                   AND T2.CODETABLE(+) = 'ZH04'
                   AND S.REGTIME >= TO_DATE('2017-01-01', 'YYYY-MM-DD')
                   AND S.REGTIME <= TO_DATE('2017-12-31', 'YYYY-MM-DD')
                 GROUP BY T.NAME,
                          M.REMSHOTYPE,
                          T1.NAME,
                          M.WEBSITETYPE,
                          T2.NAME) S,
               (SELECT T.NAME AS A,
                       M.REMSHOTYPE,
                       T1.NAME AS B,
                       M.WEBSITETYPE,
                       T2.NAME AS C,
                       COUNT(1) AS CNT
                  FROM DC_CPR_INFOWARE           S,
                       DC_CPR_INVOLVED_MAIN      M,
                       DC_CPR_INVOLVED_OBJECT    O,
                       DC_CODE.DC_12315_CODEDATA T,
                       DC_CODE.DC_12315_CODEDATA T1,
                       DC_CODE.DC_12315_CODEDATA T2
                 WHERE S.INVMAIID = M.INVMAIID
                   AND S.INVOBJID = O.INVOBJID
                   AND O.BUSINESSTYPE = 'CH20'
                   AND O.INVOBJTYPE = T.CODE
                   AND M.REMSHOTYPE = T1.CODE(+)
                   AND T1.CODETABLE(+) = 'ZH17'
                   AND M.WEBSITETYPE = T2.CODE(+)
                   AND T2.CODETABLE(+) = 'ZH04'
                   AND S.REGTIME >=
                       ADD_MONTHS(TO_DATE('2017-01-01', 'YYYY-MM-DD'), -12)
                   AND S.REGTIME <=
                       ADD_MONTHS(TO_DATE('2017-12-31', 'YYYY-MM-DD'), -12)
                 GROUP BY T.NAME,
                          M.REMSHOTYPE,
                          T1.NAME,
                          M.WEBSITETYPE,
                          T2.NAME) T
         WHERE S.A = T.A(+)
         GROUP BY S.A) A

	 
	 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws OptimusException
	 */
	public List getList(String date1, String date2) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
		//date1="2017-01-01";
		//date2="2018-12-31";
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT A.*, SUM(A.CNT) OVER() AS TOTAL\n" +
						"  FROM (SELECT S.A,\n" + 
						"               SUM(CASE WHEN S.B = '电子商务' THEN NVL(S.CNT,0) ELSE 0 END) AS B1,\n" + 
						"               SUM(CASE WHEN S.B = '邮购' THEN NVL(S.CNT,0) ELSE 0 END) AS B2,\n" + 
						"               SUM(CASE WHEN S.B = '电话购物' THEN NVL(S.CNT,0) ELSE 0 END) AS B3,\n" + 
						"               SUM(CASE WHEN S.B = '电视购物' THEN NVL(S.CNT,0) ELSE 0 END) AS B4,\n" + 
						"               --SUM(CASE WHEN S.B IS NULL THEN NVL(S.CNT,0) ELSE 0 END) AS B5,\n" + 
						"                 SUM(CASE WHEN S.C = '交易平台类' THEN NVL(S.CNT,0) ELSE 0 END) AS C1,\n" + 
						"                 SUM(CASE WHEN S.C = '应用类' THEN NVL(S.CNT,0) ELSE 0 END) AS C2,\n" + 
						"                 SUM(CASE WHEN S.C = '服务类' THEN NVL(S.CNT,0) ELSE 0 END) AS C3,\n" + 
						"                 SUM(CASE WHEN S.C = '互联网门户' THEN NVL(S.CNT,0) ELSE 0 END) AS C4,\n" + 
						"                 SUM(CASE WHEN S.C = '其他' THEN NVL(S.CNT,0) ELSE 0 END) AS C5,\n" + 
						"                 SUM(NVL(S.CNT, 0)) AS CNT,\n" + 
						"                 SUM(NVL(T.CNT, 0)) AS LASE_CNT\n" + 
						"            FROM (SELECT T.NAME AS A,\n" + 
						"                         M.REMSHOTYPE,\n" + 
						"                         T1.NAME AS B,\n" + 
						"                         M.WEBSITETYPE,\n" + 
						"                         T2.NAME AS C,\n" + 
						"                         COUNT(1) AS CNT\n" + 
						"                    FROM DC_CPR_INFOWARE           S,\n" + 
						"                         DC_CPR_INVOLVED_MAIN      M,\n" + 
						"                         DC_CPR_INVOLVED_OBJECT    O,\n" + 
						"                         DC_CODE.DC_12315_CODEDATA T,\n" + 
						"                         DC_CODE.DC_12315_CODEDATA T1,\n" + 
						"                         DC_CODE.DC_12315_CODEDATA T2\n" + 
						"                   WHERE S.INVMAIID = M.INVMAIID\n" + 
						"                     AND S.INVOBJID = O.INVOBJID\n" + 
						"                     AND O.BUSINESSTYPE = 'CH20'\n" + 
						"                     AND O.INVOBJTYPE = T.CODE\n" + 
						"                     AND M.REMSHOTYPE = T1.CODE(+)\n" + 
						"                     AND T1.CODETABLE(+) = 'ZH17'\n" + 
						"                     AND M.WEBSITETYPE = T2.CODE(+)\n" + 
						"                     AND T2.CODETABLE(+) = 'ZH04'\n" + 
						"                     AND S.REGTIME >= TO_DATE('"+date1+"', 'YYYY-MM-DD')\n" + 
						"                     AND S.REGTIME <= TO_DATE('"+date2+"', 'YYYY-MM-DD')\n" + 
						"                   GROUP BY T.NAME,\n" + 
						"                            M.REMSHOTYPE,\n" + 
						"                            T1.NAME,\n" + 
						"                            M.WEBSITETYPE,\n" + 
						"                            T2.NAME) S,\n" + 
						"                 (SELECT T.NAME AS A,\n" + 
						"                         M.REMSHOTYPE,\n" + 
						"                         T1.NAME AS B,\n" + 
						"                         M.WEBSITETYPE,\n" + 
						"                         T2.NAME AS C,\n" + 
						"                         COUNT(1) AS CNT\n" + 
						"                    FROM DC_CPR_INFOWARE           S,\n" + 
						"                         DC_CPR_INVOLVED_MAIN      M,\n" + 
						"                         DC_CPR_INVOLVED_OBJECT    O,\n" + 
						"                         DC_CODE.DC_12315_CODEDATA T,\n" + 
						"                         DC_CODE.DC_12315_CODEDATA T1,\n" + 
						"                         DC_CODE.DC_12315_CODEDATA T2\n" + 
						"                   WHERE S.INVMAIID = M.INVMAIID\n" + 
						"                     AND S.INVOBJID = O.INVOBJID\n" + 
						"                     AND O.BUSINESSTYPE = 'CH20'\n" + 
						"                     AND O.INVOBJTYPE = T.CODE\n" + 
						"                     AND M.REMSHOTYPE = T1.CODE(+)\n" + 
						"                     AND T1.CODETABLE(+) = 'ZH17'\n" + 
						"                     AND M.WEBSITETYPE = T2.CODE(+)\n" + 
						"                     AND T2.CODETABLE(+) = 'ZH04'\n" + 
						"                     AND S.REGTIME >=\n" + 
						"                         ADD_MONTHS(TO_DATE('"+date1+"', 'YYYY-MM-DD'), -12)\n" + 
						"                     AND S.REGTIME <=\n" + 
						"                         ADD_MONTHS(TO_DATE('"+date2+"', 'YYYY-MM-DD'), -12)\n" + 
						"                   GROUP BY T.NAME,\n" + 
						"                            M.REMSHOTYPE,\n" + 
						"                            T1.NAME,\n" + 
						"                            M.WEBSITETYPE,\n" + 
						"                            T2.NAME) T\n" + 
						"           WHERE S.A = T.A(+)\n" + 
						"           GROUP BY S.A) A");

		List result = dao.queryForList(sql.toString(), null);
		for (Object object : result) {
			//System.out.println("" +  object);
			log.info("log" + object);
		}
		if(result!=null&&result.size()>0) {
			return result;
		}else {
			return null;
		}
	}
	
}
