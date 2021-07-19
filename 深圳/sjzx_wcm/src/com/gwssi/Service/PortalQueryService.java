package com.gwssi.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.gwssi.AppConstants;
import com.gwssi.util.SpringJdbcUtil;
import com.trs.infra.common.WCMException;

/**
 * 门户首页支撑。
 * <ol>
 * <li>获得系统入口清单。</li>
 * </ol>
 * 
 * @author wenzetian（原作者） chaihaowei(修改)
 */
public class PortalQueryService {

	private static Logger logger = Logger.getLogger(PortalQueryService.class);



	public static void main(String[] args) throws WCMException {
		PortalQueryService p = new PortalQueryService();
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("docChannelId", "12");
		paramMap.put("coun", "55");
	//	paramMap.put("title", "光明");
/*		paramMap.put("startCreateTime", "2016-11-02");
		paramMap.put("endCreateTime", "2016-12-02");
		paramMap.put("createName", "admin");*/
		List<Map<String, Object>> resultMap = p.queryWcmDocumentByDocChannelId_page(paramMap,1,20,false);
		// JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
		// String
		String resultJSonString=JSON.toJSONString(resultMap,SerializerFeature.WriteDateUseDateFormat);
	//	String resultJSonString = JSON.toJSONString(resultMap, true);
		// String resultJSonString = JSON.toJSONStringWithDateFormat(resultMap,
		// SerializerFeature.WriteDateUseDateFormat);
		System.out.println("result>>>>>>>>>>>>>>>>" + resultJSonString);
		// System.out.println(resultMap.get(0).size());
	}


	public List<Map<String, Object>> queryWcmDocumentByDocChannelId_page(Map<String, String> map, int currentPage,
			int pageSize, boolean b) {

		logger.debug("queryWcmDocumentByDocChannelId_page start>>>>>>>>>>>>>>>");

		String docChannelId = map.get(AppConstants.DOC_CHANNEL_ID);
		String title = map.get(AppConstants.TITLE);
		String startCreateTime = map.get(AppConstants.START_CREATE_TIME);
		String endCreateTime = map.get(AppConstants.END_CREATE_TIME);
		String createName = map.get(AppConstants.CREATE_NAME);
		String count=map.get("coun");
		List<String> paramList = new ArrayList<String>();
		

		StringBuilder sql = new StringBuilder();
		if (!b) {
			sql.append(" SELECT * ");
			sql.append(" FROM (");
			sql.append(" SELECT tt.*, ");
			sql.append(" ROWNUM AS rowno ");
			sql.append(" FROM ");
			sql.append(" (SELECT t.*  ");
			sql.append(" ,'").append(count).append("' as coun");
			
		}else{
			sql.append("select count(1) as coun ");
		}
		
		
		sql.append(" FROM WCMDOCUMENT t ");
		sql.append(" WHERE docstatus='10' and DOCCHANNEL= ? ");
		paramList.add(docChannelId);

		if (StringUtils.isNotBlank(title)) {
			sql.append(" AND DOCTITLE LIKE " + "CONCAT(CONCAT(\'%\',?), \'%\') ");
			paramList.add(title);
		}
		if (StringUtils.isNotBlank(startCreateTime)) {
			sql.append("  AND CRTIME >= to_date(?, 'yyyy-mm-dd')");
			paramList.add(StringUtils.isNotBlank(startCreateTime) ? startCreateTime
					: new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}
		if (StringUtils.isNotBlank(endCreateTime)) {
			sql.append("  AND CRTIME <= to_date(?, 'yyyy-mm-dd')");
			paramList.add(StringUtils.isNotBlank(endCreateTime) ? endCreateTime
					: new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}
		if (StringUtils.isNotBlank(createName)) {
			sql.append(" AND CRUSER LIKE  " + "CONCAT(CONCAT(\'%\',?), \'%\') ");
			paramList.add(createName);
		}
		
		
		if (!b) {
		sql.append(" ORDER BY CRTIME DESC) tt ");
		sql.append(" WHERE ROWNUM <= ?) table_alias ");
		sql.append(" WHERE table_alias.rowno >= ? ");
		paramList.add(String.valueOf((currentPage-1) * pageSize +pageSize));
		paramList.add(String.valueOf((currentPage-1) * pageSize+1));
		}

		
		System.out.println((currentPage-1) * pageSize +pageSize);
		System.out.println(String.valueOf((currentPage-1) * pageSize+1));
		
		logger.debug("excute sql param is >>>>>>>>>>>>>>>" + paramList);
		List<Map<String, Object>> resultList = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCM, sql.toString(),
				paramList.toArray());
		return resultList;
	
	}
}
