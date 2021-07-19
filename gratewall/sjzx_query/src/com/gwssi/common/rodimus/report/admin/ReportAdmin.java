package com.gwssi.common.rodimus.report.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.gwssi.common.rodimus.report.util.DaoUtil;
import com.gwssi.common.rodimus.report.util.NumberUtil;
import com.gwssi.common.rodimus.report.util.StringUtil;

public class ReportAdmin {

	protected final static Logger logger = Logger.getLogger(ReportAdmin.class);
	
	/**
	 * 从request中获取查询参数。
	 * 
	 * @param config
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParams(HttpServletRequest request){
		Map<String, Object> params=new HashMap<String, Object>();
		
		// 查询参数
		String reportName = request.getParameter("reportName");
		params.put("reportName", reportName);
		String reportCode = request.getParameter("reportCode");
		params.put("reportCode", reportCode);
		String reportCategory = request.getParameter("reportCategory");
		params.put("reportCategory", reportCategory);
		String flag = request.getParameter("flag");
		params.put("flag", flag);
		// 分页参数
		String pageSize = request.getParameter("pageSize");
		params.put("pageSize", pageSize);
		String pageNo = request.getParameter("pageNo");
		params.put("pageNo", pageNo);
		
		return params;
	}
	
	
	/**
	 * 执行分页查询。
	 * 
	 * @param request
	 * @return
	 */
	public static JSONObject pagingQuery(HttpServletRequest request){
		JSONObject ret = new JSONObject();
		
		Map<String, Object> params = ReportAdmin.getParams(request);
		
		// 配置信息
		String sql = "select t.id,t.code,t.name,t.type,t.category,t.flag,t.scope,t.user_id,t.timestamp from rpt_config t where 1=1 ";
		
		List<Object> sqlParams = new ArrayList<Object>();
		String reportName = StringUtil.safe2String(params.get("reportName"));
		if(!StringUtils.isEmpty(reportName)){
			sql += " and name like ? ";
			sqlParams.add("%"+reportName+"%");
		}
		
		String reportCode = StringUtil.safe2String(params.get("reportCode"));
		if(!StringUtils.isEmpty(reportCode)){
			sql += " and code like ? ";
			sqlParams.add("%"+reportCode+"%");
		}
		
		String reportCategory = StringUtil.safe2String(params.get("reportCategory"));
		if(!StringUtils.isEmpty(reportCategory)){
			sql += " and category = ? ";
			sqlParams.add(reportCategory);
		}
		
		String flag = StringUtil.safe2String(params.get("flag"));
		if(!StringUtils.isEmpty(flag)){
			sql += " and flag = ? ";
			sqlParams.add(flag);
		}
		
		sql += " order by code asc";
		
		// 分页信息
		long totalCnt, pageCnt, pageSize, pageNo ;
		String sqlForCnt = "select count(1) as cnt from ( "+ sql +" ) t";
		totalCnt = DaoUtil.getDefaultInstance().queryForLong(sqlForCnt, sqlParams);
		totalCnt = (totalCnt>0)?totalCnt:0;
		pageSize = NumberUtil.obj2Long(params.get("pageSize"),20);
		pageNo = NumberUtil.obj2Long(params.get("pageNo"),1);
		pageCnt = totalCnt / pageSize ;
		
		JSONObject paging = new JSONObject();
		paging.put("totalCnt", totalCnt);
		paging.put("pageCnt", pageCnt);
		paging.put("pageSize", pageSize);
		paging.put("pageNo", pageNo);
		ret.put("paging", paging);
		// 数据查询
		StringBuffer sb = new StringBuffer("select * from (  select t_.*, rownum as row_idx from (");
        sb.append( sql ).append(" ) t_ where rownum <= " + (pageSize*pageNo)
                + " ) where row_idx > " + pageSize*(pageNo-1));
		List<Map<String, Object>> rows = DaoUtil.getDefaultInstance().queryForList(sb.toString(), sqlParams);
		
		ret.put("rows", rows);
		return ret;
	}
}
