package com.gwssi.doublePublic.service;

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

import com.gwssi.doublePublic.controller.XZXKController;
import com.gwssi.doublePublic.util.Utils;

/**
 * 行政许可
 * 
 * @author ye
 * 
 */
@Service
public class XZXKService {
	private static Logger logger = Logger.getLogger(XZXKController.class);
	//http://blog.csdn.net/wagnteng/article/details/53421772

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate dao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getList(String index,String rows,String keyWord){
		
		String totalSql =null;
		
		Integer i = null;
		int pageIndex = Integer.valueOf(index);
		int pageSize = Integer.valueOf(rows);
		String sql = null;
		List list = null;
		
		//得到当前的日期
		 Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")); 
		 int year = c.get(Calendar.YEAR);    //获取年
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 String date = sdf.format(new Date());
		// System.out.println("====================>系统提示【infos】\t "+year +"\t\n");

		if(Utils.isNotEmpty(keyWord)){
			Pattern p = Pattern.compile(".*\\d+.*");
			Matcher m = p.matcher(keyWord);
			 String keyWordDB = "select keywords from DOUBLE_PUBLIC_KEYWORD t where t.keywords= ? ";
			 String keys = null;
			if(m.matches()){
				 //System.out.println("数字查询方式：");
				 //System.out.println("行政许可决定文书号，行政相对人代码查询：");
				 totalSql  = "select count(1) from V_DC_T_BUSI_SZ_XZXKXX_T where  xk_jdrq >= to_date('"+year+"-01-01','yyyy-MM-dd') and xk_jdrq <= to_date('"+date+"','yyyy-MM-dd') and XK_WSH = ?   or XK_XDR_SHXYM = ?  or XK_XDR_ZDM =? or XK_XDR_GSDJ = ? or XK_XDR_SFZ = ?";
				  //totalSql  = "select count(1) from V_DC_T_BUSI_SZ_XZXKXX_T where  xk_jdrq >= to_date('"+year+"-01-01','yyyy-MM-dd') and xk_jdrq <= to_date('"+date+"','yyyy-MM-dd') and instr(XK_WSH,?,1,1)<>0   or instr(XK_XDR_SHXYM,?,1,1)<>0    or  instr(XK_XDR_ZDM,?,1,1)<>0  or instr(XK_XDR_GSDJ,?,1,1)<>0  or instr(XK_XDR_SFZ,?,1,1)<>0 ";
				 i = dao.queryForObject(totalSql,new Object[]{keyWord,keyWord,keyWord,keyWord,keyWord}, Integer.class);
				// System.out.println(totalSql + " ==>" + "查询参数  "+keyWord);
				// System.out.println("记录数共" + i + "条");
				/* if(i>=1000){
					 i=1000;
				 }*/
				 //System.out.println("当前取" + i + "条");
				 String sql2 = "select RECORDID,XK_XMMC,XK_XDR,XK_JDRQ from V_DC_T_BUSI_SZ_XZXKXX_T where  xk_jdrq >= to_date('"+year+"-01-01','yyyy-MM-dd') and xk_jdrq <= to_date('"+date+"','yyyy-MM-dd') and XK_WSH = ?  or XK_XDR_SHXYM = ?  or XK_XDR_ZDM =? or XK_XDR_GSDJ = ? or XK_XDR_SFZ = ? order by xk_jdrq desc,recordid";
				 // String sql2 = "select RECORDID,XK_XMMC,XK_XDR,XK_JDRQ from V_DC_T_BUSI_SZ_XZXKXX_T where  xk_jdrq >= to_date('"+year+"-01-01','yyyy-MM-dd') and xk_jdrq <= to_date('"+date+"','yyyy-MM-dd') and instr(XK_WSH,?,1,1)<>0   or instr(XK_XDR_SHXYM,?,1,1)<>0    or  instr(XK_XDR_ZDM,?,1,1)<>0  or instr(XK_XDR_GSDJ,?,1,1)<>0  or instr(XK_XDR_SFZ,?,1,1)<>0 order by xk_jdrq desc,recordid";
				 
				 sql = " SELECT * FROM ( SELECT A.*,rownum rn FROM ("+sql2+" ) A WHERE rownum <=" + (pageIndex) * pageSize + " ) WHERE rn>" + (pageIndex - 1) * pageSize;
				 list = dao.queryForList(sql,new Object[]{keyWord,keyWord,keyWord,keyWord,keyWord});
				// System.out.println(sql + " ==>" + "查询参数  "+keyWord);
				// System.out.println(list);
			}else{
				// System.out.println("非数字查询方式：");
				// System.out.println("项目名称-生产-查询：");
				 //totalSql  = "select count(1) from V_DC_T_BUSI_SZ_XZXKXX_T where xk_jdrq >= to_date('"+year+"-01-01','yyyy-MM-dd') and xk_jdrq <= to_date('"+date+"','yyyy-MM-dd') and XK_XMMC like ? or XK_XDR like  ? ";
				 totalSql  = "select count(1) from V_DC_T_BUSI_SZ_XZXKXX_T where xk_jdrq >= to_date('"+year+"-01-01','yyyy-MM-dd') and xk_jdrq <= to_date('"+date+"','yyyy-MM-dd') and  instr(XK_XMMC,?,1,1)<>0  or instr(XK_XDR,?,1,1)<>0  ";
				
				 
				//2018-06-28 修改开始
				 /*if(keyWord.equals("药品经营许可（零售）")||keyWord.equals("工业产品生产许可证核发")||keyWord.equals("气瓶、移动式压力容器充装单位许可")||keyWord.equals("非公司企业法人设立登记")){
					 totalSql  = "select count(1) from V_DC_T_BUSI_SZ_XZXKXX_T where   instr(XK_XMMC,?,1,1)<>0  or instr(XK_XDR,?,1,1)<>0  ";
				 }*/
				 try {
					 keys  = dao.queryForObject(keyWordDB,new Object[]{keyWord},String.class);
				} catch (Exception e) {
					 e.printStackTrace();
					keys = "";
				}
				 if(keyWord.equals(keys)||keys.equals("")) {
					 totalSql  = "select count(1) from V_DC_T_BUSI_SZ_XZXKXX_T where   instr(XK_XMMC,?,1,1)<>0  or instr(XK_XDR,?,1,1)<>0  ";
				 }
				//2018-06-28 修改结束
				 
				 //i = dao.queryForObject(totalSql,new Object[]{"%"+keyWord+"%","%"+keyWord+"%"}, Integer.class);
				 i = dao.queryForObject(totalSql,new Object[]{keyWord,keyWord}, Integer.class);
				// System.out.println("记录数共" + i + "条");
				/* if(i>=1000){
					 i=1000;
				 }*/
				// System.out.println(totalSql + " ==>" + "查询参数  "+keyWord);
				 //System.out.println("当前取" + i + "条");
				 
//				 String sql2= "SELECT RECORDID,XK_XMMC,XK_XDR,XK_JDRQ FROM  V_DC_T_BUSI_SZ_XZXKXX_T where xk_jdrq >= to_date('"+year+"-01-01','yyyy-MM-dd') and xk_jdrq <= to_date('"+date+"','yyyy-MM-dd') and  XK_XMMC like ?  or XK_XDR like ? order by xk_jdrq desc,recordid";
				 String sql2= "SELECT RECORDID,XK_XMMC,XK_XDR,XK_JDRQ FROM  V_DC_T_BUSI_SZ_XZXKXX_T where xk_jdrq >= to_date('"+year+"-01-01','yyyy-MM-dd') and xk_jdrq <= to_date('"+date+"','yyyy-MM-dd') and instr(XK_XMMC,?,1,1)<>0  or instr(XK_XDR,?,1,1)<>0 order by xk_jdrq desc,recordid";
				 
				 
				 ////2018-06-28 修改开始
				 /*if(keyWord.equals("药品经营许可（零售）")||keyWord.equals("工业产品生产许可证核发")||keyWord.equals("气瓶、移动式压力容器充装单位许可")){
					 sql2= "SELECT RECORDID,XK_XMMC,XK_XDR,XK_JDRQ FROM  V_DC_T_BUSI_SZ_XZXKXX_T where instr(XK_XMMC,?,1,1)<>0  or instr(XK_XDR,?,1,1)<>0 order by xk_jdrq desc,recordid";
				 }*/
				 try {
					 keys  = dao.queryForObject(keyWordDB,new Object[]{keyWord}, String.class);
				} catch (Exception e) {
					 e.printStackTrace();
					keys = "";
				}
				 if(keyWord.equals(keys)||keys.equals("")) {
					 sql2  = "SELECT RECORDID,XK_XMMC,XK_XDR,XK_JDRQ FROM  V_DC_T_BUSI_SZ_XZXKXX_T where instr(XK_XMMC,?,1,1)<>0  or instr(XK_XDR,?,1,1)<>0 order by xk_jdrq desc,recordid";
				 }
				//2018-06-28 修改结束
				 
				 
				 
				 sql = " SELECT * FROM ( SELECT A.*,rownum rn FROM ("+sql2+" ) A WHERE rownum <=" + (pageIndex) * pageSize + " ) WHERE rn>" + (pageIndex - 1) * pageSize;
				// list = dao.queryForList(sql,new Object[]{"%"+keyWord+"%","%"+keyWord+"%"});
				 list = dao.queryForList(sql,new Object[]{keyWord,keyWord});
				// System.out.println(sql + " ==>" + "查询参数  "+keyWord);
				// System.out.println(list);
			}
		}else{
			
			 totalSql  = "select count(1) from V_DC_T_BUSI_SZ_XZXKXX_T where xk_jdrq >= to_date('"+year+"-01-01','yyyy-MM-dd') and  xk_jdrq <= to_date('"+date+"','yyyy-MM-dd')";
			 i = dao.queryForObject(totalSql, Integer.class);
			// System.out.println("记录数共" + i + "条");
			/* if(i>=1000){
				 i=1000;
			 }*/
			 //System.out.println("当前取" + i + "条");
			 StringBuffer sb = new StringBuffer();
			 sb.append("SELECT RECORDID,XK_XMMC,XK_XDR,XK_JDRQ FROM  V_DC_T_BUSI_SZ_XZXKXX_T where xk_jdrq >= to_date('"+year+"-01-01','yyyy-MM-dd')  ");
			 sb.append("and  xk_jdrq <= to_date('"+date+"','yyyy-MM-dd') order by xk_jdrq desc,RECORDID");
			 sql = " SELECT * FROM ( SELECT A.*,rownum rn FROM ( "+sb.toString()+") A WHERE rownum <= "+ (pageIndex) * pageSize +" ) WHERE rn>" + (pageIndex - 1) * pageSize;
			 list = dao.queryForList(sql);
			 //System.out.println(">>> "  + list);
		}
		
		List results = new ArrayList();
		Map map = new HashMap();
		map.put("total", i);
		map.put("list", list);
		results.add(map);
		return results;
	}
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map findDetailById(String id){
		String sql = "select * from V_DC_T_BUSI_SZ_XZXKXX_T where RECORDID = ? ";
		logger.info("SQL == > " + sql + "参数  " + id);
		Map map = null;
		try {
			List list = dao.queryForList(sql,new Object[]{id});
			if(list!=null&&list.size()>0){
				map = (Map) list.get(0);
				String idCard = (String) map.get("XK_XDR_SFZ");
				String endTimes = (String) map.get("ZZYXQX");//许可截止期
				
				if(endTimes.indexOf("5")!=-1) { //如果日期是以5000 截取5开头。则将许可截止期 显示为长期
					map.put("ZZYXQX","长期");
				}
				
				StringBuffer bf = new StringBuffer();
				if(idCard!=null&&idCard.length()==18){
					 bf.append(idCard.substring(0, 6));
					 bf.append("****");
					 bf.append(idCard.substring(14,idCard.length()));
					 map.put("XK_XDR_SFZ",bf.toString());
				}
				if(idCard!=null&&idCard.length()==15){
					 bf.append(idCard.substring(0, 6));
					 bf.append("****");
					 bf.append(idCard.substring(11,idCard.length()));
					 map.put("XK_XDR_SFZ",bf.toString());
				}
			}
		} catch (Exception e) {
			return null;
		}
		return map;
	}
	
}
