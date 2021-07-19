package com.gwssi.doublePublic.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gwssi.doublePublic.util.Utils;
import com.gwssi.entSelect.util.StringUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;


/**
 * 行政处罚
 * @author ye
 *
 */
@Service
public class XZCFService extends BaseService {

	private static Logger logger = Logger.getLogger(XZCFService.class);
	

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate dao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getList(String index,String rows, String keyWord){
		String totalSql =null;
		
		int pageIndex = Integer.valueOf(index);
		int pageSize = Integer.valueOf(rows);
		Integer i = null;
		String sql = null;
		List list = null;
		
		//得到当前的日期
		 Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")); 
		 int year = c.get(Calendar.YEAR);    //获取年
		// System.out.println("====================>系统提示【infos】\t "+year +"\t\n");
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 String date = sdf.format(new Date());
		
		
		if(Utils.isNotEmpty(keyWord)){
			Pattern p = Pattern.compile(".*\\d+.*");
			Matcher m = p.matcher(keyWord);
			if(m.matches()){
				//System.out.println("行政处罚决定书文号，行政相对人代码查询：");
				 totalSql  = "select count(1) from v_dc_t_busi_sz_xzcfxx_t where CF_JDRQ >= to_date('"+year+"-01-01','yyyy-MM-dd') and CF_JDRQ <= to_date('"+date+"','yyyy-MM-dd') and  CF_WSH = ? or CF_CFMC = ?  or CF_XDR_SHXYM = ?  or CF_XDR_ZDM =? or CF_XDR_GSDJ = ? or CF_XDR_SFZ = ?";
				 i = dao.queryForObject(totalSql,new Object[]{keyWord,keyWord,keyWord,keyWord,keyWord,keyWord}, Integer.class);
				 //System.out.println(totalSql + " ==>" + "参数"+keyWord);
				// System.out.println("记录数共"+i+"条");
				/* if(i>=1000){
					 i=1000;
				 }*/
				// System.out.println("当前取"+i+"条");
				 String sql2 = "select RECORDID,CF_CFMC,CF_JDRQ,CF_XDR_MC from v_dc_t_busi_sz_xzcfxx_t where CF_JDRQ >= to_date('"+year+"-01-01','yyyy-MM-dd') and CF_JDRQ <= to_date('"+date+"','yyyy-MM-dd') and CF_WSH = ? or CF_CFMC = ? or CF_XDR_SHXYM = ?  or CF_XDR_ZDM =? or CF_XDR_GSDJ = ? or CF_XDR_SFZ = ? order by CF_JDRQ desc,RECORDID";
				 sql = " SELECT * FROM ( SELECT A.*,rownum rn FROM ("+sql2+" ) A WHERE rownum <=" + (pageIndex) * pageSize + " ) WHERE rn>" + (pageIndex - 1) * pageSize;
				 list = dao.queryForList(sql,new Object[]{keyWord,keyWord,keyWord,keyWord,keyWord,keyWord});
				// System.out.println(sql + " ==>" + "参数  "+keyWord);
				// System.out.println(list);
			}else{
				// System.out.println("行政相对人名称查询：");
				// totalSql  = "select count(1) from v_dc_t_busi_sz_xzcfxx_t where  CF_JDRQ >= to_date('"+year+"-01-01','yyyy-MM-dd') and CF_JDRQ <= to_date('"+date+"','yyyy-MM-dd') and CF_XDR_MC like  ?  or CF_CFMC like ?";//当前没有拼接时间，是错误的。。。
				 totalSql  = "select count(1) from v_dc_t_busi_sz_xzcfxx_t where  CF_JDRQ >= to_date('"+year+"-01-01','yyyy-MM-dd') and CF_JDRQ <= to_date('"+date+"','yyyy-MM-dd') and instr(CF_XDR_MC,?,1,1)<>0   or instr(CF_CFMC,?,1,1)<>0";
				// i = dao.queryForObject(totalSql,new Object[]{"%" + keyWord+"%","%" + keyWord+"%"}, Integer.class);
				 i = dao.queryForObject(totalSql,new Object[]{ keyWord,keyWord}, Integer.class);
				// System.out.println("记录数共"+i+"条");
				 /*if(i>=1000){
					 i=1000;
				 }*/
				// System.out.println("当前取"+i+"条");
				// System.out.println(totalSql + " ==>" + "参数  "+keyWord);
				 
				 //String tempSql = "SELECT RECORDID,CF_CFMC,CF_JDRQ,CF_XDR_MC FROM  v_dc_t_busi_sz_xzcfxx_t where CF_JDRQ >= to_date('"+year+"-01-01','yyyy-MM-dd') and CF_JDRQ <= to_date('"+date+"','yyyy-MM-dd') and CF_XDR_MC like ? or CF_CFMC like ? order by CF_JDRQ desc,RECORDID";
				 String tempSql = "SELECT RECORDID,CF_CFMC,CF_JDRQ,CF_XDR_MC FROM  v_dc_t_busi_sz_xzcfxx_t where CF_JDRQ >= to_date('"+year+"-01-01','yyyy-MM-dd') and CF_JDRQ <= to_date('"+date+"','yyyy-MM-dd') and instr(CF_XDR_MC,?,1,1)<>0   or instr(CF_CFMC,?,1,1)<>0  order by CF_JDRQ desc,RECORDID";
				 
				 sql = " SELECT * FROM ( SELECT A.*,rownum rn FROM ("+tempSql+") A WHERE rownum <=" + (pageIndex) * pageSize + " ) WHERE rn>" + (pageIndex - 1) * pageSize;
				// list = dao.queryForList(sql,new Object[]{"%"+keyWord+"%","%" + keyWord+"%"});
				 list = dao.queryForList(sql,new Object[]{keyWord,keyWord});
				// System.out.println(sql + " ==>" + "参数  "+keyWord);
				// System.out.println(list);
			}
		}else{
			totalSql  = "select count(1) from v_dc_t_busi_sz_xzcfxx_t  where CF_JDRQ >= to_date('"+year+"-01-01','yyyy-MM-dd') and CF_JDRQ <= to_date('"+date+"','yyyy-MM-dd')";
			 i = dao.queryForObject(totalSql, Integer.class);
			 logger.info("SQL == > " + totalSql);
			// System.out.println("记录数共"+i+"条");
			 /*if(i>=1000){
				 i=1000;
			 }*/
			// System.out.println("当前取"+i+"条");
			 
			 String tempSql = "SELECT RECORDID,CF_CFMC,CF_JDRQ,CF_XDR_MC FROM  v_dc_t_busi_sz_xzcfxx_t where CF_JDRQ >= to_date('"+year+"-01-01','yyyy-MM-dd') and CF_JDRQ <= to_date('"+date+"','yyyy-MM-dd') order by CF_JDRQ desc,RECORDID";
			 
			// sql = " SELECT * FROM ( SELECT A.*,rownum rn FROM ( SELECT * FROM  dc_T_BUSI_SZ_XZXKXX where  CF_JDRQ is not null ) A WHERE rownum <=" + (pageIndex) * pageSize + " ) WHERE rn>" + (pageIndex - 1) * pageSize +" order by CF_JDRQ DESC ";
			 sql = " SELECT * FROM ( SELECT A.*,rownum rn FROM ( "+tempSql+" ) A WHERE rownum <=" + (pageIndex) * pageSize + " ) WHERE rn>" + (pageIndex - 1) * pageSize;
			// System.out.println(" >> "+ sql);
			 list = dao.queryForList(sql);
			// System.out.println(">>> "  + list);
		}
		
		List results = new ArrayList();
		Map map = new HashMap();
		map.put("total", i);
		map.put("list", list);
		logger.info("SQL == > " + sql);
		results.add(map);
		return results;
	}
	
	
	

	@SuppressWarnings("rawtypes")
	public Map findDetailById(String id){
		//select * from dc_T_BUSI_SZ_XZCFXX where CF_xh = 'AS201512280001';
		String sql = "select * from v_dc_t_busi_sz_xzcfxx_t where RECORDID = ? ";
		Map map = null;
		logger.info("SQL == > " + sql + "参数  " + id);
		try {
			//map = dao.queryForMap(sql,new Object[]{id});
			List list = dao.queryForList(sql,new Object[]{id});
			if(list!=null&&list.size()>0){
				map = (Map) list.get(0);
				String idCard = (String) map.get("CF_XDR_SFZ");
				StringBuffer bf = new StringBuffer();
				if(idCard!=null&&idCard.length()==18){
					 bf.append(idCard.substring(0, 6));
					 bf.append("****");
					 bf.append(idCard.substring(14,idCard.length()));
					 map.put("CF_XDR_SFZ",bf.toString());
				}
				if(idCard!=null&&idCard.length()==15){
					 bf.append(idCard.substring(0, 6));
					 bf.append("****");
					 bf.append(idCard.substring(11,idCard.length()));
					 map.put("CF_XDR_SFZ",bf.toString());
				}
				
				
			}
		} catch (Exception e) {
			return null;
		}
		return map;
	}
	
	
	
	
	
	
	/*///
	public List<Map> queryCFList(String index,String rows, String keyWord) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sqlCount = null;
		StringBuffer listSql = null;
		int pageIndex = Integer.valueOf(index);
		int pageSize = Integer.valueOf(rows);
		List<Map> listCount = null;
		List<Map> listResult = null;
		
		List result = new ArrayList();
		Map map = new HashMap();
		BigDecimal total = null;
		
		sqlCount = new StringBuffer("select count(1) as count from  v_dc_t_busi_sz_xzcfxx_t ");
		listSql = new StringBuffer("select * from  v_dc_t_busi_sz_xzcfxx_t ");
		if(StringUtil.isNotEmpty(keyWord)){
			sqlCount.append("where CF_WSH = ? or CF_CFMC = ?  or CF_XDR_SHXYM = ?  or CF_XDR_ZDM =? or CF_XDR_GSDJ = ? or CF_XDR_SFZ = ?");
			List<String> paramsCount = new ArrayList<String>(); //参数
			paramsCount.add(keyWord);
			listCount  = dao.queryForList(sqlCount.toString(), paramsCount);
			total = (BigDecimal) listCount.get(0).get("count");//总记录数
			map.put("total", total);
			
			
			listSql.append("where CF_WSH = ? or CF_CFMC = ?  or CF_XDR_SHXYM = ?  or CF_XDR_ZDM =? or CF_XDR_GSDJ = ? or CF_XDR_SFZ = ?");
			List<String> paramsList = new ArrayList<String>(); //参数
			paramsList.add(keyWord);
			listResult  = dao.queryForList(listSql.toString(), paramsList);//记录列表
			map.put("list", listResult);
			
			result.add(map);
		}
		
		sjw_wcm_option
		sjw_wcm_option0622
		oragxkj
		return result;
	
		//return util.typechage();
	}
	
	////
*/	
	
	
	
	
}
