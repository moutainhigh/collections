package com.gwssi.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gwssi.AppConstants;
import com.gwssi.util.PropertiesUtil;
import com.gwssi.util.SpringJdbcUtil;
import com.trs.infra.common.WCMException;

//�Ż�����ͳ��  1��ҳ��������ܼơ�2��ҳ���������ܼ�
public class PortalTrafficStatisticsService{
	
	private static Logger logger = Logger.getLogger(PortalTrafficStatisticsService.class);
	private static PropertiesUtil d1 = PropertiesUtil.getInstance("GwssiDataSource");
	
	public List<Map<String, Object>> getCommentCount(HttpServletRequest request) {
		String iNewsID=request.getParameter("iNewsID");
		if(StringUtils.isEmpty(iNewsID)){
			return null;
		}
		StringBuilder sql = new StringBuilder();
		List<String> paramList = new ArrayList<String>();
		if (StringUtils.isNotBlank(iNewsID)) {
			sql.append(" select CommentCount from NcNews where ID=? ");
			paramList.add(iNewsID);
		}
		
	    List<Map<String, Object>> visitorCountList = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCMOPTION, sql.toString(),
						paramList.toArray());
		return visitorCountList;
	}
	public void addHitsCount(HttpServletRequest request) {
		String iNewsID=request.getParameter("iNewsID");
		if(StringUtils.isEmpty(iNewsID)){
			return;
		}
		//��ȡ��ǰҳ��ǰ�ĵ������
		StringBuilder sql = new StringBuilder();
		List<String> paramList = new ArrayList<String>();
		if (StringUtils.isNotBlank(iNewsID)) {
			sql.append(" select hitscount from wcmdocument where docid=? ");
			paramList.add(iNewsID);
		}
		int hitsCountNow =0;
		hitsCountNow = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_WCM_SYNC, sql.toString(),
						paramList.toArray());
		//֮�󣬸��µ�ǰҳ�ĵ������
		StringBuilder sql2 = new StringBuilder();
		int hitsCountNew =0;
		hitsCountNew=hitsCountNow+1;
		sql2.append(" update wcmdocument set hitscount = "+hitsCountNew+" where docid= "+iNewsID+" ");
		SpringJdbcUtil.Execute(AppConstants.DATASOURCE_KEY_WCM_SYNC, sql2.toString());
		
	}

	public int getHitsCount(HttpServletRequest request) {
		String iNewsID=request.getParameter("iNewsID");
		if(StringUtils.isEmpty(iNewsID)){
			return (Integer) null;
		}
		//��ȡ��ǰҳ��ǰ�ĵ������
		StringBuilder sql = new StringBuilder();
		List<String> paramList = new ArrayList<String>();
		if (StringUtils.isNotBlank(iNewsID)) {
			sql.append(" select hitscount from wcmdocument where docid=? ");
			paramList.add(iNewsID);
		}
		int hitsCountNow =0;
		hitsCountNow = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_WCM_SYNC, sql.toString(),
						paramList.toArray());
		return hitsCountNow;
	}

	
}
