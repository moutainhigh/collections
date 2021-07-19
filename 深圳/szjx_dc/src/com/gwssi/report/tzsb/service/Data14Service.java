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
public class Data14Service  extends BaseService {
	private static Logger log = Logger.getLogger(Data17Service.class);


	
	public List<Map<String, Object>> tjxm_code_value() throws OptimusException {
		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT name as text , value as value from DC_TZSB_CODE where  type='sggz_data14'  ");
		List list = dao_dc.queryForList(sql.toString(), null);
		return list;
	}
	
	
	
	public List<Map<String, Object>> sggzlx_code_value() throws OptimusException {
		IPersistenceDAO dao_dc = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT name as text , value as value from DC_TZSB_CODE where  type='sggz_type_data14'  ");
		List list = dao_dc.queryForList(sql.toString(), null);
		return list;
	}
	
	
	
	
	
	
	
	public List queryByDw(Map map) throws OptimusException {

		IPersistenceDAO dao_dc = getPersistenceDAO("lc_ts");
		List<String> params = new ArrayList<String>();// 参数准备
		StringBuffer sb  = new StringBuffer();
		
		
		
		sb.append(" SELECT ");
		sb.append("  NVL(S.ACCEPT_CORP_NAME,'全市') AS 办理单位,");
		sb.append(" SUM(S.CNT) AS 合计宗数,");
		sb.append(" SUM(S.CNT1) AS 合计台数,");
		sb.append("  SUM(CASE WHEN S.LB = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉宗数,");
		sb.append(" SUM(CASE WHEN S.LB = '锅炉' THEN S.CNT1 ELSE 0 END) AS 锅炉台数,   ");   
		sb.append(" SUM(CASE WHEN S.LB = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器宗数,");
		sb.append("  SUM(CASE WHEN S.LB = '压力容器' THEN S.CNT1 ELSE 0 END) AS 压力容器台数,   ");  
		sb.append("  SUM(CASE WHEN S.LB = '电梯' THEN S.CNT ELSE 0 END) AS 电梯宗数,");
		sb.append(" SUM(CASE WHEN S.LB = '电梯' THEN S.CNT1 ELSE 0 END) AS 电梯台数,   "); 
		sb.append("  SUM(CASE WHEN S.LB = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械宗数,");
	    sb.append("  SUM(CASE WHEN S.LB = '起重机械' THEN S.CNT1 ELSE 0 END) AS 起重机械台数,      ");
	    sb.append("  SUM(CASE WHEN S.LB = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场内机动车辆宗数,");
	    sb.append("  SUM(CASE WHEN S.LB = '场（厂）内专用机动车辆' THEN S.CNT1 ELSE 0 END) AS 场内机动车辆台数,  ");    
	    sb.append("  SUM(CASE WHEN S.LB = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 大型游乐设施宗数,");
	    sb.append("  SUM(CASE WHEN S.LB = '大型游乐设施' THEN S.CNT1 ELSE 0 END) AS 大型游乐设施台数,   ");    
	    sb.append("  SUM(CASE WHEN S.LB = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道宗数,");
	    sb.append("  SUM(CASE WHEN S.LB = '压力管道' THEN S.CNT1 ELSE 0 END) AS 压力管道台数,    ");   
	    sb.append("  SUM(CASE WHEN S.LB = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道宗数,");
	    sb.append("  SUM(CASE WHEN S.LB = '客运索道' THEN S.CNT1 ELSE 0 END) AS 客运索道台数  ");
	    sb.append("  FROM (SELECT NVL(REPLACE(S.ACCEPT_CORP_NAME, '深圳市市场和质量监督管理委员会', ''),'待分区') AS ACCEPT_CORP_NAME,");
	    sb.append("  T.LB,");
	    sb.append("  S.ACCEPT_TIME, --受理日期 \r\n");
	    sb.append("  S.INFOFLOW_NAME, --事项名称\r\n ");
	    sb.append("  COUNT(DISTINCT S.DATA_ID) AS CNT, --宗数  \r\n");
	    sb.append("   COUNT(1) AS CNT1 --台数 \r\n");
	    sb.append("    FROM GCLOUD_GIAP_SE.GIAP_APPLY_BASE       S,");
	    sb.append("         ( ");
	    sb.append("          SELECT  MAIN_TBL_PK,'锅炉' AS LB  FROM LS_CFORM.GuoLu_AZ --施工告知安装锅炉信息表 \r\n      ");
	    sb.append("          UNION ALL");
	    sb.append(" SELECT  MAIN_TBL_PK,'压力容器' AS LB FROM LS_CFORM.YaLiRongQi_AZ --施工告知安装压力容器信息表  \r\n  ");
	    sb.append(" UNION ALL");
	    sb.append(" SELECT  MAIN_TBL_PK,'电梯' AS LB  FROM LS_CFORM.Dianti_AZ --施工告知安装电梯信息表\r\n "); 
	    sb.append("   UNION ALL");
	    sb.append("   SELECT  MAIN_TBL_PK,'起重机械' AS LB  FROM LS_CFORM.QiZhongJiXie_AZ --施工告知安装起重机械信息表  \r\n ");
	    sb.append("   UNION ALL");
	    sb.append(" SELECT  MAIN_TBL_PK,'场（厂）内专用机动车辆' AS LB  FROM LS_CFORM.ChangNaCheLiang_AZ --施工告知安装场（厂）内专用机动车辆信息表   \r\n");
	    sb.append("  UNION ALL");
	    sb.append("   SELECT  MAIN_TBL_PK,'大型游乐设施' AS LB  FROM LS_CFORM.YouLeSheShi_AZ --施工告知安装大型游乐设施信息表    \r\n");
	    sb.append("  UNION ALL");
	    sb.append("  SELECT  MAIN_TBL_PK,'压力管道' AS LB  FROM LS_CFORM.YaLiGuanDao_AZ --施工告知安装压力管道信息表  \r\n  ");
	    sb.append("   UNION ALL");
	    sb.append("   SELECT  MAIN_TBL_PK,'客运索道' AS LB  FROM LS_CFORM.KeYunSuoDao_AZ --施工告知安装客运索道信息表 \r\n   ");
	    sb.append("           UNION ALL");
	    sb.append("             SELECT  MAIN_TBL_PK,'锅炉' AS LB  FROM LS_CFORM.GuoLu_GZ --施工告知改造锅炉信息表       \r\n");
	    sb.append("            UNION ALL");
	    sb.append("            SELECT  MAIN_TBL_PK,'压力容器' AS LB  FROM LS_CFORM.YaLiRongQi_GZ --施工告知改造压力容器信息表    \r\n");
	    sb.append("             UNION ALL");
	    sb.append("            SELECT  MAIN_TBL_PK,'电梯' AS LB  FROM LS_CFORM.Dianti_GZ --施工告知改造电梯信息表 \r\n ");
	    sb.append("              UNION ALL");
	    sb.append("            SELECT  MAIN_TBL_PK,'起重机械' AS LB  FROM LS_CFORM.QiZhongJiXie_GZ --施工告知改造起重机械信息表   \r\n");
	    sb.append("             UNION ALL");
	    sb.append("           SELECT  MAIN_TBL_PK,'场（厂）内专用机动车辆' AS LB  FROM LS_CFORM.ChangNaCheLiang_GZ --施工告知改造场（厂）内专用机动车辆信息表  \r\n ");
	    sb.append("              UNION ALL");
	    sb.append("            SELECT  MAIN_TBL_PK,'大型游乐设施' AS LB  FROM LS_CFORM.YouLeSheShi_GZ --施工告知改造大型游乐设施信息表\r\n    ");
	    sb.append("             UNION ALL");
	    sb.append("             SELECT  MAIN_TBL_PK,'压力管道' AS LB  FROM LS_CFORM.YaLiGuanDao_GZ --施工告知改造压力管道信息表    \r\n");
	    sb.append("             UNION ALL");
	    sb.append("            SELECT  MAIN_TBL_PK,'客运索道' AS LB  FROM LS_CFORM.KeYunSuoDao_GZ --施工告知改造客运索道信息表   \r\n ");
	    sb.append("            UNION ALL");
	    sb.append("            SELECT  MAIN_TBL_PK,'锅炉' AS LB  FROM LS_CFORM.GuoLu_XL --施工告知修理锅炉信息表      \r\n ");
	    sb.append("           UNION ALL");
	    sb.append("           SELECT  MAIN_TBL_PK,'压力容器' AS LB  FROM LS_CFORM.YaLiRongQi_XL --施工告知修理压力容器信息表   \r\n ");
	    sb.append("            UNION ALL");
	    sb.append("           SELECT  MAIN_TBL_PK,'电梯' AS LB  FROM LS_CFORM.Dianti_XL --施工告知修理电梯信息表 \r\n ");
	    sb.append("            UNION ALL");
	    sb.append("             SELECT  MAIN_TBL_PK,'起重机械' AS LB  FROM LS_CFORM.QiZhongJiXie_XL --施工告知修理起重机械信息表 \r\n  ");
	    sb.append("             UNION ALL");
	    sb.append("             SELECT  MAIN_TBL_PK,'场（厂）内专用机动车辆' AS LB  FROM LS_CFORM.ChangNaCheLiang_XL --施工告知修理场（厂）内专用机动车辆信息表  \r\n ");
	    sb.append("             UNION ALL");
	    sb.append("           SELECT  MAIN_TBL_PK,'大型游乐设施' AS LB  FROM LS_CFORM.YouLeSheShi_XL --施工告知修理大型游乐设施信息表 \r\n   ");
	    sb.append("            UNION ALL");
	    sb.append("            SELECT  MAIN_TBL_PK,'压力管道' AS LB  FROM LS_CFORM.YaLiGuanDao_XL --施工告知修理压力管道信息表 \r\n   ");
	    sb.append("            UNION ALL");
	    sb.append("             SELECT  MAIN_TBL_PK,'客运索道' AS LB  FROM LS_CFORM.KeYunSuoDao_XL --施工告知修理客运索道信息表    \r\n");
	    sb.append("             UNION ALL");
	    sb.append("            SELECT  MAIN_TBL_PK,'锅炉' AS LB  FROM LS_CFORM.SGGZ_YZ_GUOLU --施工告知移装锅炉信息表      \r\n");
	    sb.append("            UNION ALL");
	    sb.append("             SELECT  MAIN_TBL_PK,'压力容器' AS LB  FROM LS_CFORM.SGGZ_YZ_YaLiRongQi --施工告知移装压力容器信息表  \r\n  ");
	    sb.append("            UNION ALL");
	    sb.append("             SELECT  MAIN_TBL_PK,'电梯' AS LB  FROM LS_CFORM.SGGZ_YZ_DIANTI --施工告知移装电梯信息表\r\n  ");
	    sb.append("             UNION ALL");
	    sb.append("           SELECT  MAIN_TBL_PK,'起重机械' AS LB  FROM LS_CFORM.SGGZ_YZ_QIZHONGJIXIE --施工告知移装起重机械信息表   \r\n");
	    sb.append("             UNION ALL");
	    sb.append("             SELECT  MAIN_TBL_PK,'场（厂）内专用机动车辆' AS LB  FROM LS_CFORM.SGGZ_YZ_CHANGNACHELIANG --施工告知移装场（厂）内专用机动车辆信息表 \r\n  ");
	    sb.append("             UNION ALL");
	    sb.append("            SELECT  MAIN_TBL_PK,'大型游乐设施' AS LB  FROM LS_CFORM.SGGZ_YZ_YOULESHESHI --施工告知移装大型游乐设施信息表 \r\n");    
	    sb.append("           UNION ALL");
	    sb.append("           SELECT  MAIN_TBL_PK,'压力管道' AS LB  FROM LS_CFORM.SGGZ_YZ_YALIGUANDAO --施工告知移装压力管道信息表  \r\n  "); 
	    sb.append("            UNION ALL ");
	    sb.append("            SELECT  MAIN_TBL_PK,'客运索道' AS LB  FROM LS_CFORM.SGGZ_YZ_KEYUNSUODAO --施工告知移装客运索道信息表 \r\n   ");
	    sb.append("            UNION ALL");
	    sb.append("          SELECT  MAIN_TBL_PK,'锅炉' AS LB  FROM LS_CFORM.SGGZ_YZGZ_GUOLU --施工告知移装改造锅炉信息表       \r\n");
	    sb.append("           UNION ALL");
	    sb.append("           SELECT  MAIN_TBL_PK,'压力容器' AS LB  FROM LS_CFORM.SGGZ_YZGZ_YaLiRongQi --施工告知移装改造压力容器信息表  \r\n  ");
	    sb.append("            UNION ALL");
	    sb.append("          SELECT  MAIN_TBL_PK,'电梯' AS LB  FROM LS_CFORM.SGGZ_YZGZ_DIANTI --施工告知移装改造电梯信息表 \r\n");  
	    sb.append("           UNION ALL");
	    sb.append("           SELECT  MAIN_TBL_PK,'起重机械' AS LB  FROM LS_CFORM.SGGZ_YZGZ_QIZHONGJIXIE --施工告知移装改造起重机械信息表  \r\n  ");
	    sb.append("           UNION ALL");
	    sb.append("         SELECT  MAIN_TBL_PK,'场（厂）内专用机动车辆' AS LB  FROM LS_CFORM.SGGZ_YZGZ_CHANGNACHELIANG --施工告知移装改造场（厂）内专用机动车辆信息表    \r\n");
	    sb.append("          UNION ALL");
	    sb.append("           SELECT  MAIN_TBL_PK,'大型游乐设施' AS LB  FROM LS_CFORM.SGGZ_YZGZ_YOULESHESHI --施工告知移装改造大型游乐设施信息表    \r\n ");
	    sb.append("          UNION ALL");
	    sb.append("          SELECT  MAIN_TBL_PK,'压力管道' AS LB  FROM LS_CFORM.SGGZ_YZGZ_YALIGUANDAO --施工告知移装改造压力管道信息表     \r\n");
	    sb.append("          UNION ALL");
	    sb.append("          SELECT  MAIN_TBL_PK,'客运索道' AS LB  FROM LS_CFORM.SGGZ_YZGZ_KEYUNSUODAO --施工告知移装改造客运索道信息表   \r\n  ");
	    sb.append("          UNION ALL");
	    sb.append("         SELECT  MAIN_TBL_PK,'锅炉' AS LB  FROM LS_CFORM.GUOLU_HXQX --施工告知化学清洗锅炉信息表  \r\n");
	    sb.append("         UNION ALL");
	    sb.append("          SELECT  MAIN_TBL_PK,'电梯' AS LB  FROM LS_CFORM.SGGZ_WB_DianTi --施工告知维保电梯信息表   \r\n");
	    sb.append("          UNION ALL");
	    sb.append("          SELECT  MAIN_TBL_PK,'起重机械' AS LB  FROM LS_CFORM.SGGZ_WB_QIZHONGJIXIE---施工告知维保起重机械信息表 \r\n ");
	    sb.append("          ) T");
	    sb.append("    WHERE S.DATA_ID = T.MAIN_TBL_PK");
	    sb.append("      AND S.BIZ_TYPE_NAME = '特种设备施工告知'");
	    sb.append("     AND S.BIZ_STATE = 'Accepted'");
	   

		
		/*if (map.get("tjxm") != null && (!map.get("tjxm").equals(""))) {
			params.add((String) map.get("tjxm"));
		    sb.append("      AND SUBSTR(S.ACCEPT_CORP_NAME, 16, 2) IS NOT NULL  ");
		    sb.append("      AND SUBSTR(S.ACCEPT_CORP_NAME, 16, 2) = ?  ");
		}*/
		
		if (map.get("sggzlx") != null && (!map.get("sggzlx").equals(""))) {
			sb.append("  and    S.INFOFLOW_NAME = ? ");
			params.add((String) map.get("sggzlx"));
		}
		
		if (map.get("spBegTime") != null && (!map.get("spBegTime").equals(""))) {
			 sb.append("  and to_char(S.ACCEPT_TIME,'YYYY-MM-DD') >= ? ");
			 params.add((String) map.get("spBegTime"));
		}
		if (map.get("spEndTime") != null && (!map.get("spEndTime").equals(""))) {
			 sb.append("  and to_char(S.ACCEPT_TIME,'YYYY-MM-DD') <= ? ");
			params.add((String) map.get("spEndTime"));
		}
		
		
		 sb.append("    GROUP BY S.ACCEPT_CORP_NAME,T.LB, S.ACCEPT_TIME, S.INFOFLOW_NAME ");
		 sb.append("    ) S");
		 sb.append("  GROUP BY ROLLUP(S.ACCEPT_CORP_NAME)");
		 
		 System.out.println(sb.toString());
		 
		List resuts = dao_dc.queryForList(sb.toString(), params);;
		return resuts;
		
	}
	
	
	
	
	public List queryByXiaQu(Map map) throws OptimusException {
		
		IPersistenceDAO dao_dc = getPersistenceDAO("lc_ts");
		List<String> params = new ArrayList<String>();// 参数准备
		StringBuffer sb  = new StringBuffer();
	
		 sb.append("   SELECT ");
		 sb.append("    NVL(S.ACCEPT_CORP_NAME,'全市') AS 辖区, ");
		 sb.append("    SUM(S.CNT) AS 合计宗数, ");
		 sb.append("     SUM(S.CNT1) AS 合计台数, ");
		 sb.append("     SUM(CASE WHEN S.LB = '锅炉' THEN S.CNT ELSE 0 END) AS 锅炉宗数, ");
		 sb.append("     SUM(CASE WHEN S.LB = '锅炉' THEN S.CNT1 ELSE 0 END) AS 锅炉台数,       ");
		 sb.append("     SUM(CASE WHEN S.LB = '压力容器' THEN S.CNT ELSE 0 END) AS 压力容器宗数, ");
		 sb.append("      SUM(CASE WHEN S.LB = '压力容器' THEN S.CNT1 ELSE 0 END) AS 压力容器台数,      ");
		 sb.append("     SUM(CASE WHEN S.LB = '电梯' THEN S.CNT ELSE 0 END) AS 电梯宗数, ");
		 sb.append("     SUM(CASE WHEN S.LB = '电梯' THEN S.CNT1 ELSE 0 END) AS 电梯台数,     ");
		 sb.append("    SUM(CASE WHEN S.LB = '起重机械' THEN S.CNT ELSE 0 END) AS 起重机械宗数, ");
		 sb.append("    SUM(CASE WHEN S.LB = '起重机械' THEN S.CNT1 ELSE 0 END) AS 起重机械台数,       ");
		 sb.append("     SUM(CASE WHEN S.LB = '场（厂）内专用机动车辆' THEN S.CNT ELSE 0 END) AS 场内机动车辆宗数, ");
		 sb.append("     SUM(CASE WHEN S.LB = '场（厂）内专用机动车辆' THEN S.CNT1 ELSE 0 END) AS 场内机动车辆台数,     ");  
		 sb.append("      SUM(CASE WHEN S.LB = '大型游乐设施' THEN S.CNT ELSE 0 END) AS 大型游乐设施宗数, ");
		 sb.append("      SUM(CASE WHEN S.LB = '大型游乐设施' THEN S.CNT1 ELSE 0 END) AS 大型游乐设施台数,    ");    
		 sb.append("      SUM(CASE WHEN S.LB = '压力管道' THEN S.CNT ELSE 0 END) AS 压力管道宗数, ");
		 sb.append("     SUM(CASE WHEN S.LB = '压力管道' THEN S.CNT1 ELSE 0 END) AS 压力管道台数,   ");     
		 sb.append("     SUM(CASE WHEN S.LB = '客运索道' THEN S.CNT ELSE 0 END) AS 客运索道宗数, ");
		 sb.append("      SUM(CASE WHEN S.LB = '客运索道' THEN S.CNT1 ELSE 0 END) AS 客运索道台数   ");
		// sb.append("  FROM (SELECT NVL(REPLACE(T.TQ,'新区','区'),'待分区') AS ACCEPT_CORP_NAME, ");
		 sb.append("  FROM (SELECT NVL(REPLACE(REPLACE(T.TQ,'新区','区'),'特别',''),'待分区') AS ACCEPT_CORP_NAME, ");
		 sb.append("             T.LB, ");
		 sb.append("           S.ACCEPT_TIME, --受理日期 \r\n ");
		 sb.append("           S.INFOFLOW_NAME, --事项名称  \r\n ");
		 sb.append("          COUNT(DISTINCT S.DATA_ID) AS CNT, --宗数  \r\n");
		 sb.append("          COUNT(1) AS CNT1 --台数  \r\n ");
		 sb.append("     FROM GCLOUD_GIAP_SE.GIAP_APPLY_BASE       S, ");
		 sb.append(" ( ");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_GL AS TQ,'锅炉' AS LB  FROM LS_CFORM.GuoLu_AZ --施工告知安装锅炉信息表   \r\n   ");
		 sb.append("          UNION ALL");
		 sb.append("         SELECT  MAIN_TBL_PK,QUMINGCHEN_RQ AS TQ,'压力容器' AS LB FROM LS_CFORM.YaLiRongQi_AZ --施工告知安装压力容器信息表 \r\n  ");
		 sb.append("    UNION ALL ");
		 sb.append("        SELECT  MAIN_TBL_PK,QUMINGCHEN_DT AS TQ,'电梯' AS LB  FROM LS_CFORM.Dianti_AZ --施工告知安装电梯信息表 \r\n");
		 sb.append("          UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_QZ AS TQ,'起重机械' AS LB  FROM LS_CFORM.QiZhongJiXie_AZ --施工告知安装起重机械信息表  \r\n");
		 sb.append("           UNION ALL ");
		 sb.append("           SELECT  MAIN_TBL_PK,QUMINGCHEN_CL AS TQ,'场（厂）内专用机动车辆' AS LB  FROM LS_CFORM.ChangNaCheLiang_AZ --施工告知安装场（厂）内专用机动车辆信息表   \r\n");
		 sb.append("           UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_YL AS TQ,'大型游乐设施' AS LB  FROM LS_CFORM.YouLeSheShi_AZ --施工告知安装大型游乐设施信息表   \r\n");
		 sb.append("           UNION ALL ");
		 sb.append("           SELECT  MAIN_TBL_PK,QUMINGCHEN_GD AS TQ,'压力管道' AS LB  FROM LS_CFORM.YaLiGuanDao_AZ --施工告知安装压力管道信息表   \r\n");
		 sb.append("          UNION ALL ");
		 sb.append("        SELECT  MAIN_TBL_PK,QUMINGCHEN_SD AS TQ,'客运索道' AS LB  FROM LS_CFORM.KeYunSuoDao_AZ --施工告知安装客运索道信息表    \r\n");
		 sb.append("         UNION ALL ");
		 sb.append("         SELECT  MAIN_TBL_PK,QUMINGCHEN_GL AS TQ,'锅炉' AS LB  FROM LS_CFORM.GuoLu_GZ --施工告知改造锅炉信息表    \r\n  ");
		 sb.append("        UNION ALL ");
		 sb.append("         SELECT  MAIN_TBL_PK,QUMINGCHEN_RQ AS TQ,'压力容器' AS LB  FROM LS_CFORM.YaLiRongQi_GZ --施工告知改造压力容器信息表    \r\n");
		 sb.append("         UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_DT AS TQ,'电梯' AS LB  FROM LS_CFORM.Dianti_GZ --施工告知改造电梯信息表\r\n   ");
		 sb.append("         UNION ALL ");
		 sb.append("         SELECT  MAIN_TBL_PK,QUMINGCHEN_QZ AS TQ,'起重机械' AS LB  FROM LS_CFORM.QiZhongJiXie_GZ --施工告知改造起重机械信息表   \r\n");
		 sb.append("          UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_CL AS TQ,'场（厂）内专用机动车辆' AS LB  FROM LS_CFORM.ChangNaCheLiang_GZ --施工告知改造场（厂）内专用机动车辆信息表 \r\n"); 
		 sb.append("           UNION ALL ");
		 sb.append("         SELECT  MAIN_TBL_PK,QUMINGCHEN_YL AS TQ,'大型游乐设施' AS LB  FROM LS_CFORM.YouLeSheShi_GZ --施工告知改造大型游乐设施信息表 \r\n  ");
		 sb.append("         UNION ALL ");
		 sb.append("         SELECT  MAIN_TBL_PK,QUMINGCHEN_GD AS TQ,'压力管道' AS LB  FROM LS_CFORM.YaLiGuanDao_GZ --施工告知改造压力管道信息表   \r\n");
		 sb.append("          UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_SD AS TQ,'客运索道' AS LB  FROM LS_CFORM.KeYunSuoDao_GZ --施工告知改造客运索道信息表   \r\n ");
		 sb.append("          UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_GL AS TQ,'锅炉' AS LB  FROM LS_CFORM.GuoLu_XL --施工告知修理锅炉信息表   \r\n   "); 
		 sb.append("          UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_RQ AS TQ,'压力容器' AS LB  FROM LS_CFORM.YaLiRongQi_XL --施工告知修理压力容器信息表  \r\n  ");
		 sb.append("         UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_DT AS TQ,'电梯' AS LB  FROM LS_CFORM.Dianti_XL --施工告知修理电梯信息表 \r\n ");
		 sb.append("        UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_QZ AS TQ,'起重机械' AS LB  FROM LS_CFORM.QiZhongJiXie_XL --施工告知修理起重机械信息表 \r\n  ");
		 sb.append("           UNION ALL ");
		 sb.append("        SELECT  MAIN_TBL_PK,QUMINGCHEN_CL AS TQ,'场（厂）内专用机动车辆' AS LB  FROM LS_CFORM.ChangNaCheLiang_XL --施工告知修理场（厂）内专用机动车辆信息表\r\n    ");
		 sb.append("          UNION ALL ");
		 sb.append("            SELECT  MAIN_TBL_PK,QUMINGCHEN_YL AS TQ,'大型游乐设施' AS LB  FROM LS_CFORM.YouLeSheShi_XL --施工告知修理大型游乐设施信息表 \r\n    ");
		 sb.append("         UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_GD AS TQ,'压力管道' AS LB  FROM LS_CFORM.YaLiGuanDao_XL --施工告知修理压力管道信息表\r\n     ");
		 sb.append("           UNION ALL ");
		 sb.append("           SELECT  MAIN_TBL_PK,QUMINGCHEN_SD AS TQ,'客运索道' AS LB  FROM LS_CFORM.KeYunSuoDao_XL --施工告知修理客运索道信息表  \r\n  ");
		 sb.append("          UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,SBSYDD_GL_COU AS TQ,'锅炉' AS LB  FROM LS_CFORM.SGGZ_YZ_GUOLU --施工告知移装锅炉信息表     \r\n  ");
		 sb.append("         UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,SBSYDD_RQ_COU AS TQ,'压力容器' AS LB  FROM LS_CFORM.SGGZ_YZ_YaLiRongQi --施工告知移装压力容器信息表    \r\n");
		 sb.append("          UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,SBSYDD_DT_COU AS TQ,'电梯' AS LB  FROM LS_CFORM.SGGZ_YZ_DIANTI --施工告知移装电梯信息表 \r\n   ");
		 sb.append("        UNION ALL ");
		 sb.append("        SELECT  MAIN_TBL_PK,SBSYDD_QZ_COU AS TQ,'起重机械' AS LB  FROM LS_CFORM.SGGZ_YZ_QIZHONGJIXIE --施工告知移装起重机械信息表 \r\n  ");
		 sb.append("          UNION ALL ");
		 sb.append("           SELECT  MAIN_TBL_PK,SBSYDD_CL_COU AS TQ,'场（厂）内专用机动车辆' AS LB  FROM LS_CFORM.SGGZ_YZ_CHANGNACHELIANG --施工告知移装场（厂）内专用机动车辆信息表  \r\n ");
		 sb.append("           UNION ALL ");
		 sb.append("           SELECT  MAIN_TBL_PK,SBSYDD_YL_COU AS TQ,'大型游乐设施' AS LB  FROM LS_CFORM.SGGZ_YZ_YOULESHESHI --施工告知移装大型游乐设施信息表   \r\n ");
		 sb.append("           UNION ALL ");
		 sb.append("            SELECT  MAIN_TBL_PK,SBSYDD_GD_COU AS TQ,'压力管道' AS LB  FROM LS_CFORM.SGGZ_YZ_YALIGUANDAO --施工告知移装压力管道信息表  \r\n  ");
		 sb.append("         UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,SBSYDD_SD_COU AS TQ,'客运索道' AS LB  FROM LS_CFORM.SGGZ_YZ_KEYUNSUODAO --施工告知移装客运索道信息表  \r\n  ");
		 sb.append("          UNION ALL");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_GL AS TQ,'锅炉' AS LB  FROM LS_CFORM.SGGZ_YZGZ_GUOLU --施工告知移装改造锅炉信息表  \r\n  ");  
		 sb.append("          UNION ALL ");
		 sb.append("           SELECT  MAIN_TBL_PK,QUMINGCHEN_RQ AS TQ,'压力容器' AS LB  FROM LS_CFORM.SGGZ_YZGZ_YaLiRongQi --施工告知移装改造压力容器信息表   \r\n ");
		 sb.append("        UNION ALL ");
		 sb.append("           SELECT  MAIN_TBL_PK,QUMINGCHEN_DT AS TQ,'电梯' AS LB  FROM LS_CFORM.SGGZ_YZGZ_DIANTI --施工告知移装改造电梯信息表  \r\n");
		 sb.append("           UNION ALL ");
		 sb.append("           SELECT  MAIN_TBL_PK,QUMINGCHEN_QZ AS TQ,'起重机械' AS LB  FROM LS_CFORM.SGGZ_YZGZ_QIZHONGJIXIE --施工告知移装改造起重机械信息表  \r\n");
		 sb.append("         UNION ALL ");
		 sb.append("           SELECT  MAIN_TBL_PK,QUMINGCHEN_CL AS TQ,'场（厂）内专用机动车辆' AS LB  FROM LS_CFORM.SGGZ_YZGZ_CHANGNACHELIANG --施工告知移装改造场（厂）内专用机动车辆信息表  \r\n ");
		 sb.append("          UNION ALL ");
		 sb.append("           SELECT  MAIN_TBL_PK,QUMINGCHEN_YL AS TQ,'大型游乐设施' AS LB  FROM LS_CFORM.SGGZ_YZGZ_YOULESHESHI --施工告知移装改造大型游乐设施信息表 \r\n   ");
		 sb.append("           UNION ALL ");
		 sb.append("            SELECT  MAIN_TBL_PK,QUMINGCHEN_GD AS TQ,'压力管道' AS LB  FROM LS_CFORM.SGGZ_YZGZ_YALIGUANDAO --施工告知移装改造压力管道信息表  \r\n  ");
		 sb.append("            UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,QUMINGCHEN_SD AS TQ,'客运索道' AS LB  FROM LS_CFORM.SGGZ_YZGZ_KEYUNSUODAO --施工告知移装改造客运索道信息表 \r\n  ");
		 sb.append("         UNION ALL ");
		 sb.append("         SELECT  MAIN_TBL_PK,SBSYDD_GL_COU AS TQ,'锅炉' AS LB  FROM LS_CFORM.GUOLU_HXQX --施工告知化学清洗锅炉信息表 \r\n");
		 sb.append("          UNION ALL ");
		 sb.append("          SELECT  MAIN_TBL_PK,SBSYDD_DT_COU AS TQ,'电梯' AS LB  FROM LS_CFORM.SGGZ_WB_DianTi --施工告知维保电梯信息表  \r\n");
		 sb.append("          UNION ALL ");
		 sb.append("           SELECT  MAIN_TBL_PK,SBSYDD_QZ_COU AS TQ,'起重机械' AS LB  FROM LS_CFORM.SGGZ_WB_QIZHONGJIXIE---施工告知维保起重机械信息表 \r\n");
		 sb.append("          ) T");
		 sb.append("     WHERE S.DATA_ID = T.MAIN_TBL_PK ");
		 sb.append("     AND S.BIZ_TYPE_NAME = '特种设备施工告知' ");
		 sb.append("     AND S.BIZ_STATE = 'Accepted' ");
		

		
		
		
		
		
		

	/*	if (map.get("tjxm") != null && (!map.get("tjxm").equals(""))) {
			params.add((String) map.get("tjxm"));
		}*/
		if (map.get("sggzlx") != null && (!map.get("sggzlx").equals(""))) {
			 
			 sb.append("    and    S.INFOFLOW_NAME = ?  --事项名称  \r\n ");
			params.add((String) map.get("sggzlx"));
		}
		
		if (map.get("spBegTime") != null && (!map.get("spBegTime").equals(""))) {
			sb.append("    and     to_char(S.ACCEPT_TIME,'yyyy-mm-dd') >=?  --受理日期 \r\n ");
			params.add((String) map.get("spBegTime"));
		}
		if (map.get("spEndTime") != null && (!map.get("spEndTime").equals(""))) {
			sb.append("   and     to_char(S.ACCEPT_TIME,'yyyy-mm-dd') <=?  --受理日期 \r\n ");
			params.add((String) map.get("spEndTime"));
		}
		
		 sb.append("    GROUP BY T.TQ,T.LB,S.INFOFLOW_NAME ,S.ACCEPT_TIME");
		 sb.append("    ) S ");
		 sb.append("   GROUP BY ROLLUP(S.ACCEPT_CORP_NAME) ");
		 System.out.println(sb.toString());
		List resuts = dao_dc.queryForList(sb.toString(), params);;
		System.out.println(resuts);
		return resuts;
		
		
	}
}
