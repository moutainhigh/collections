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
public class Data13Service extends BaseService {
	private static Logger log = Logger.getLogger(Data13Service.class);

	
	
	
	
	public List<Map<String, Object>> queryCodeValue() throws OptimusException {
		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT name as text , value as value from DC_TZSB_CODE where  type='sldw_data13'  ");
		List list = dao_dc.queryForList(sql.toString(), null);
		return list;
	}

	
	
	public List<Map<String, Object>> querySLTypeCodeValue() throws OptimusException {
		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT name as text , value as value from DC_TZSB_CODE where  type='sllx_data13'  ");
		List list = dao_dc.queryForList(sql.toString(), null);
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	public List querySYdJ(Map map) throws OptimusException {
		IPersistenceDAO dao_dc = getPersistenceDAO("lc_ts");
		List<String> params = new ArrayList<String>();// 参数准备
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT ");
		sb.append(" NVL(S.ACCEPT_CORP_NAME,'合计') AS 办理单位,");
		sb.append("  SUM(S.CNT) AS 合计受理宗数,");
		sb.append(" SUM(S.CNT1) AS 合计申请设备数,");
		sb.append(" SUM(S.CNT2) AS 合计设备通过数,");
		sb.append(" SUM(CASE WHEN T.ITEM_VALUE = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉受理宗数,");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '锅炉' THEN S.CNT1 ELSE 0 END) AS 锅炉申请设备数, ");
		sb.append(" SUM(CASE WHEN T.ITEM_VALUE = '锅炉' THEN S.CNT2 ELSE 0 END) AS 锅炉设备通过数,     ");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器受理宗数,");
		sb.append("   SUM(CASE WHEN T.ITEM_VALUE = '压力容器' THEN S.CNT1 ELSE 0 END) AS 压力容器申请设备数,   ");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '压力容器' THEN S.CNT2 ELSE 0 END) AS 压力容器设备通过数,   ");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '电梯' THEN S.CNT ELSE 0 END) AS 电梯受理宗数,");
		sb.append(" SUM(CASE WHEN T.ITEM_VALUE = '电梯' THEN S.CNT1 ELSE 0 END) AS 电梯申请设备数,   ");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '电梯' THEN S.CNT2 ELSE 0 END) AS 电梯设备通过数,     ");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械受理宗数,");
		sb.append(" SUM(CASE WHEN T.ITEM_VALUE = '起重机械' THEN S.CNT1 ELSE 0 END) AS 起重机械申请设备数, ");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '起重机械' THEN S.CNT2 ELSE 0 END) AS 起重机械设备通过数,     ");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场内机动车辆受理宗数,");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT1 ELSE 0 END) AS 场内机动车辆申请设备数,  ");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '场（厂）内专用机动车辆' THEN S.CNT2 ELSE 0 END) AS 场内机动车辆设备通过数,   ");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 大型游乐设施受理宗数,");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '大型游乐设施' THEN S.CNT1 ELSE 0 END) AS 大型游乐设施申请设备数, ");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '大型游乐设施' THEN S.CNT2 ELSE 0 END) AS 大型游乐设施设备通过数,   ");
		sb.append(" SUM(CASE WHEN T.ITEM_VALUE = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道受理宗数,");
		sb.append(" SUM(CASE WHEN T.ITEM_VALUE = '压力管道' THEN S.CNT1 ELSE 0 END) AS 压力管道申请设备数,");
		sb.append(" SUM(CASE WHEN T.ITEM_VALUE = '压力管道' THEN S.CNT2 ELSE 0 END) AS 压力管道设备通过数,  ");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道受理宗数,");
		sb.append("  SUM(CASE WHEN T.ITEM_VALUE = '客运索道' THEN S.CNT1 ELSE 0 END) AS 客运索道申请设备数,  ");
		sb.append(" SUM(CASE WHEN T.ITEM_VALUE = '客运索道' THEN S.CNT2 ELSE 0 END) AS 客运索道设备通过数   ");
		//sb.append(" FROM (SELECT NVL(SUBSTR(S.ACCEPT_CORP_NAME, 16, 2),'待分区') AS ACCEPT_CORP_NAME,");
		sb.append(" FROM (SELECT NVL(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(S.ACCEPT_CORP_NAME, '深圳市市场和质量监督管理委员会', ''), '市场监督管理局', ''), '局', ''), '监管', ''), '减员库', ''), '委派出机构', ''), '待分区') AS ACCEPT_CORP_NAME, ");
		sb.append(" --B.SHENBAODANWEIDIZHI_COU,\r\n");
		sb.append(" --B.SHENBAODANWEIDIZHI_STR,\r\n");
		sb.append(" B.SHEBEIZHONGLEI AS LB,");
		sb.append(" S.ACCEPT_TIME, --受理日期  \r\n");
		sb.append(" S.RECENT_PERMIT_TIME, --审批日期  \r\n");
		sb.append(" B.DENGJILEIXING,  --参照字典登记类型 \r\n ");

		
		sb.append("  COUNT(DISTINCT S.DATA_ID) AS CNT, --受理宗数 \r\n");
		sb.append("  COUNT(1) AS CNT1, --申请设备数  \r\n");
		sb.append("  SUM(CASE WHEN T.FLAG = '0' THEN 1 ELSE 0 END) AS CNT2 --设备通过数 \r\n ");
		sb.append("     FROM GCLOUD_GIAP_SE.GIAP_APPLY_BASE       S,");
		sb.append("         LS_CFORM.SUB_GIAP_TZSB_SYDJBG_SBDWXX B,");
		sb.append("          (");
		sb.append("           SELECT  T.MAIN_TBL_PK,T.BANLIJIELUN_GL AS FLAG FROM LS_CFORM.SYDJBG_GUOLU_NEW T");
		sb.append("           UNION ALL");
		sb.append("   SELECT  T.MAIN_TBL_PK,T.BANLIJIELUN_RQ AS FLAG FROM LS_CFORM.SYDJBG_YALIRONGQI_NEW T");
		sb.append("     UNION ALL");
		sb.append("     SELECT  T.MAIN_TBL_PK,T.BANLIJIELUN_DT AS FLAG FROM LS_CFORM.SYDJBG_DIANTI_NEW T");
		sb.append("      UNION ALL");
		sb.append("       SELECT  T.MAIN_TBL_PK,T.BANLIJIELUN_QZ AS FLAG FROM LS_CFORM.SYDJBG_QIZHONGJIXIE_NEW T");
		sb.append(" UNION ALL");
		sb.append("  SELECT  T.MAIN_TBL_PK,T.BANLIJIELUN_CL AS FLAG FROM LS_CFORM.SYDJBG_CHANGNEICHELIANG_NEW T");
		sb.append("      UNION ALL");
		sb.append("          SELECT  T.MAIN_TBL_PK,T.BANLIJIELUN_YL AS FLAG FROM LS_CFORM.SYDJBG_YOULESHESHI_NEW T");
		sb.append("           UNION ALL");
		sb.append("           SELECT  T.MAIN_TBL_PK,T.BANLIJIELUN_GD AS FLAG FROM LS_CFORM.SYDJBG_YALIGUANDAO_NEW T");
		sb.append("           UNION ALL");
		sb.append("           SELECT  T.MAIN_TBL_PK,T.BANLIJIELUN_SD AS FLAG FROM LS_CFORM.SYDJBG_KEYUNSUODAO_NEW T");
		sb.append("          ) T");
		sb.append("    WHERE S.DATA_ID = B.MAIN_TBL_PK");
		sb.append("      AND S.DATA_ID = T.MAIN_TBL_PK");
		sb.append("      AND S.BIZ_TYPE_NAME = '特种设备使用登记'");
		sb.append("      AND S.BIZ_STATE = 'Permit'");
		
		
		if (map.get("sldw") != null && (!map.get("sldw").equals(""))) {
			//sb.append(" 	    AND SUBSTR(S.ACCEPT_CORP_NAME, 16, 2) IS NOT NULL");
			//sb.append(" 	    AND SUBSTR(S.ACCEPT_CORP_NAME, 16, 2)  = ? ");
			sb.append(" 	    AND NVL(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(S.ACCEPT_CORP_NAME, '深圳市市场和质量监督管理委员会', ''), '市场监督管理局', ''), '局', ''), '监管', ''), '减员库', ''), '委派出机构', ''), '待分区')  = ?  ");
			params.add((String) map.get("sldw"));
		}
		
		if (map.get("slBegTime") != null && (!map.get("slBegTime").equals(""))) {
			sb.append(" and to_char(S.ACCEPT_TIME ,'YYYY-MM-DD') >= ?  --受理日期 \r\n  ");
			params.add((String) map.get("slBegTime"));
		}
		if (map.get("slEndTime") != null && (!map.get("slEndTime").equals(""))) {
			sb.append(" and to_char(S.ACCEPT_TIME ,'YYYY-MM-DD') <= ?  --受理日期\r\n   ");
			params.add((String) map.get("slEndTime"));
		}
		
		if (map.get("spBegTime") != null && (!map.get("spBegTime").equals(""))) {
			sb.append(" and to_char(S.RECENT_PERMIT_TIME,'YYYY-MM-DD') >= ?  --审批日期 \r\n ");
			params.add((String) map.get("spBegTime"));
		}
		if (map.get("spEndTime") != null && (!map.get("spEndTime").equals(""))) {
			sb.append(" and to_char(S.RECENT_PERMIT_TIME ,'YYYY-MM-DD') <= ?  --受理日期 \r\n ");
			params.add((String) map.get("spEndTime"));
		}
		
		if (map.get("slType") != null && (!map.get("slType").equals(""))) {
			sb.append(" and  B.DENGJILEIXING = ?   --参照字典登记类型 \r\n");
			params.add((String) map.get("slType"));
		}
		

		sb.append("  GROUP BY S.ACCEPT_CORP_NAME,B.SHEBEIZHONGLEI, S.ACCEPT_TIME,S.RECENT_PERMIT_TIME,B.DENGJILEIXING ");
		sb.append(" 	 ) S,");
		sb.append(" 	 (SELECT T.ITEM_CODE,T.ITEM_VALUE FROM GCLOUD_SC.SC_DICT_ITEM T WHERE T.DICT_CODE = 'GIAP_SE_EQU_KIND') T");
		sb.append(" 	WHERE S.LB = T.ITEM_CODE");
		sb.append(" 	GROUP BY ROLLUP(S.ACCEPT_CORP_NAME)");

		System.out.println(sb.toString() +"\n\n");

		List resuts = dao_dc.queryForList(sb.toString(), params);
		return resuts;

	}
}
