//package com.gwssi.common.rodimus.report.service;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.stereotype.Service;
//
//import com.gwssi.common.rodimus.report.entity.SysmgrUser;
//import com.gwssi.common.rodimus.report.util.DaoUtil;
//import com.gwssi.common.rodimus.report.util.StringUtil;
//import com.gwssi.framework.common.result.Page;
//import com.gwssi.framework.common.result.PageUtil;
//import com.gwssi.framework.common.result.Result;
//
//@Service("reportManagerService")
//public class ReportManagerService {
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public Result queryReportList(int pagesize, int currentPage, Map arg,SysmgrUser currentUser) {
//		String  userId = "LXB";//用户id
//		String userType = "12";//用户类型
//		List<String> params=new ArrayList<String>();
//		String sql="select r.id,code,name,decode(flag,'1','启用','0','停用')  as flag ,"
//				+ "case when f.id is null then '0' else '1' end favFlag ,r.remark "
//				+ "from rpt_config r left join rpt_fav f on r.id=f.report_id and f.user_id='"+userId+"'"
//				+ "where flag<>'x'";
//		
//		sql += " and r.scope like '%"+userType+"%' ";
//		String reportName = StringUtil.safe2String(arg.get("reportName"));
//		if(!StringUtils.isEmpty(reportName)){
//			sql += " and name like ? ";
//			params.add("%"+reportName+"%");
//		}
//		
//		String reportCode = StringUtil.safe2String(arg.get("reportCode"));
//		if(!StringUtils.isEmpty(reportCode)){
//			sql += " and code like ? ";
//			params.add("%"+reportCode+"%");
//		}
//		String reportRemark = StringUtil.safe2String(arg.get("reportRemark"));
//		if(!StringUtils.isEmpty(reportRemark)){
//			sql += " and r.remark like ? ";
//			params.add("%"+reportRemark+"%");
//		}
//		String reportCategory = StringUtil.safe2String(arg.get("reportCategory"));
//		if(!StringUtils.isEmpty(reportCategory)){
//			sql += " and category = ? ";
//			params.add(reportCategory);
//		}
//		String flag = StringUtil.safe2String(arg.get("flag"));
//		if(!StringUtils.isEmpty(flag)){
//			sql += " and flag = ? ";
//			params.add(flag);
//		}
//		sql += " order by id asc ";
//		List list=DaoUtil.getInstance().queryForList(sql, params);
//		long totals=0;
//		String execCount = getQueryTotal(list, currentPage,pagesize);
//	
//		if (execCount.equals("execCount")) {
//			String countSql="select count(1) as cnt from("+sql+ ")";
//			List countList=DaoUtil.getInstance().queryForList(countSql, params);
//			if(countList!=null&&countList.size()>0){
//				Map<String,Object> row = (Map<String,Object>)countList.get(0);
//				totals =((BigDecimal)row.get("cnt")).longValue();
//			}
//		} else {
//			totals = list.size();
//		}
//		
//		Page page = PageUtil.createPage(pagesize, currentPage, totals);
//		Result result = new Result(page, list);
//		return result;
//	}
//	/**
//	 * 处理是否执行countsql的逻辑
//	 * @param content
//	 * @param currentPage
//	 * @return
//	 */
//	@SuppressWarnings("rawtypes")
//	public static String getQueryTotal(List content,int currentPage,int everyPage){
//		//如果查询为空，不执行countsql，默认总数为0
//		//如果查询结果不为空
//		if (!content.isEmpty()) {
//			//如果为第一页
//			if (currentPage == 1) {
//				//如果不够10条，总数为第一页查询返回的结果数量
//				if (content.size() < everyPage) {
//					return content.size()+"";
//				}else {
//					return "execCount";
//				}
//			}else {
//				return "execCount";
//			}
//		}else {
//			return "0";
//		}
//	}
//	
//	
//}
