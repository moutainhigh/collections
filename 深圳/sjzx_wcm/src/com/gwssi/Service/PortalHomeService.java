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

/**
 * 门户首页支撑。
 * <ol>
 * 	<li>获得系统入口清单。</li>
 * </ol>
 * @author chaihaowei
 */
public class PortalHomeService{
	
	private static Logger logger = Logger.getLogger(PortalHomeService.class);
	private static PropertiesUtil d1 = PropertiesUtil.getInstance("GwssiDataSource");
	private static PropertiesUtil cpro = PropertiesUtil.getInstance("GwssiComment");
	
	public  Map<String,String>  deleteFarOne(HttpServletRequest request){
		Map<String,String> map= new HashMap<String,String>();
		String  pid= request.getParameter("pid");
		if(StringUtils.isEmpty(pid)){
			 map.put("code", "-1");
			 map.put("message", "参数获取失败");
			return map;
		}

		String userid="";
		String ip="";
		if(null!=request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_IP)){
			ip=(String)request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_IP);
			userid=(String)request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_ID);
			userid=StringUtils.upperCase(userid);
		}else{
			 map.put("code", "-1");
			 map.put("message", "用户名获取失败");
			return map;
		}
		
		
		StringBuffer sql2= new StringBuffer();
		sql2.append(" delete from SM_FAVORITE_FOLD t where t.user_id ='");
		sql2.append(userid).append("'");
		
		sql2.append("  and t.pk_sys_integration ='");
		sql2.append(pid);
		sql2.append("'");
	
		SpringJdbcUtil.Execute(AppConstants.DATASOURCE_KEY_YYJC, sql2.toString());	
		System.out.println(sql2);
		 map.put("code", "1");
		 map.put("message", "删除成功");
		
		 
		return map;
		//return 0;
		
	}
	
	public Map<String,String> insertFarOne(HttpServletRequest request){	
		Map<String,String>  map = new HashMap<String,String>();
		String  pid= request.getParameter("pid");
		if(StringUtils.isEmpty(pid)){
			 map.put("code", "-1");
			 map.put("message", "参数获取失败");
			return map;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from sm_favorite_fold f,appall t where f.pk_sys_integration =t.PK_SYS_INTEGRATION and upper(f.user_id)=?");
		String userid="";
		String ip="";
		if(null!=request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_IP)){
			ip=(String)request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_IP);
			userid=(String)request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_ID);
			userid=StringUtils.upperCase(userid);
		}else{
			 map.put("code", "-1");
			 map.put("message", "用户ID获取失败");
			return map;
		}
		
		
		StringBuffer isExsql =new StringBuffer();
		isExsql.append("select count(1) from sm_favorite_fold t where upper(t.user_id)= '").append(userid);
		isExsql.append("' and upper(t.pk_sys_integration)='").append(StringUtils.upperCase(pid)).append("'");
		int excount =SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_YYJC,isExsql.toString());
		if(excount>0){
			 map.put("code", "-1");
			 map.put("message", "该系统在收藏夹中已经存在");
			return map;
		}
				
				

		int count =SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_YYJC,sql.toString(), userid);
		String maxnoStr= cpro.getProperty("portal.favorite.size");
		int maxno =  new Integer(maxnoStr);
		
		if(count<maxno){
			//开始插入操作
			StringBuffer sql2= new StringBuffer();
			sql2.append(" insert into sm_favorite_fold  (user_id, pk_sys_integration)  values  ('").append(userid).append("'");
			sql2.append(" , ").append("'").append(pid).append("'").append(")");
			SpringJdbcUtil.Execute(AppConstants.DATASOURCE_KEY_YYJC, sql2.toString());
		  map.put("code", "1");
		  map.put("message", "增加成功");
		}else{
		  map.put("message", "收藏夹数量已超出最大值"+maxno);
		  map.put("code", "-1");
			
		}
		
		
		
		return map;
	}
	
	
	/**
	 * 获取系统列表 为收藏夹众多的系统 ISFAR：Y
	 * @autor chaihaowei
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> getAppListNew(HttpServletRequest request) {
	   
	
		String userid="";
		// 0、 定义变量
		List<Map<String, Object>> ret =null;
		if(request==null){
			String sql = "select * from appALL t where t.ip is null ";
			ret = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql);	
			return ret;
		}
		
		
		
		// 2、获取待匹配的IP（IP地址一部分）
		String ip="";
		if(null!=request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_IP)){
			ip=(String)request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_IP);
			userid=(String)request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_ID);
			userid=StringUtils.upperCase(userid);
		}
		// = IpUtil.getIpAddress(request);
		logger.debug("ip:"+ip);
		String[] ips = ip.split("\\.");
		if(ips!=null && ips.length>=3){
			ip=ips[0]+"."+ips[1];
		}else{
			String sql = "select * from appALL t where t.ip is null ";
			ret = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql);	
			return ret;
		}
		logger.info("匹配后IP："+ip);
		
		

		String  sql2=" select count(1) from  SM_OLD_SYS  t  where t.ip = ? ";
		int count2 =SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_YYJC,sql2, ip);	
		if(count2>0){
			StringBuffer sql = new StringBuffer();
			sql.append("select t.*, (case  when t.PK_SYS_INTEGRATION in   (select u.pk_sys_integration from SM_FAVORITE_FOLD u WHERE upper(u.user_id) = ?) then  'Y' ELSE   'N' end) ISFAR from appall t where t.ip is null or ( t.ip is not null and t.ip = ? )");
			
			
			//String sql = "select * from appALL t where t.ip is null or ( t.ip is not null and t.ip = ? )";
			ret= SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql.toString(), userid, ip);	
		}else{
			StringBuffer sql = new StringBuffer();
			sql.append("select t.*, (case  when t.PK_SYS_INTEGRATION in   (select u.pk_sys_integration from SM_FAVORITE_FOLD u WHERE upper(u.user_id) = ?) then  'Y' ELSE   'N' end) ISFAR from appall t where t.ip is null or ( t.ip is not null and t.ip = '300.300' )");
			
			//String sql = "select * from appALL t where t.ip is null or ( t.ip is not null and t.ip = '300.300' ) ";
			ret= SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql.toString(), userid);	
		}
		
		
/*		String sql = "select * from appALL t where t.ip is null or ( t.ip is not null and t.ip = ? )";
		ret= SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql, ip);	*/
		return ret;
		
	}
	
	 
	/**
	 * <h3>获取应用系统清单</h3>
	 * <pre>
	 * request 中可以包含filter参数，含义如下：
	 * filter = all 获取所有；
	 * filter =  byIp 获取需要ip的；
	 * filter = common 获取 为空的。
	 * 默认为all。
	 * </pre>
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> getAppList(HttpServletRequest request) {
		
		
		
		
		// 0、 定义变量
		List<Map<String, Object>> ret =null;
		if(request==null){
			String sql = "select * from appALL t where t.ip is null ";
			ret = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql);	
			return ret;
		}
		
		
		
		// 2、获取待匹配的IP（IP地址一部分）
		String ip="";
		if(null!=request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_IP)){
			ip=(String)request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_IP);
		}
		// = IpUtil.getIpAddress(request);
		logger.debug("ip:"+ip);
		String[] ips = ip.split("\\.");
		if(ips!=null && ips.length>=3){
			ip=ips[0]+"."+ips[1];
		}else{
			String sql = "select * from appALL t where t.ip is null ";
			ret = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql);	
			return ret;
		}
		logger.info("匹配后IP："+ip);
		
		
		// 3、根据filter参数值不同，执行不同查询，获得应用清单
		String filter = request.getParameter("filter");
		
		// 3.1、 所有的
		if("all".equalsIgnoreCase(filter)){
			String sql = "select * from appALL t where t.ip is null or (t.ip is not null and t.ip = ? )";
			ret= SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql, ip);	
			return ret;
		}
		
		// 3.2、 通用的，无需通过IP过滤的
		if("common".equalsIgnoreCase(filter)){
			String sql = "select * from appALL t where t.ip is null ";
			ret = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql);	
			return ret;
		}
		
		// 3.3、 不要通用的，只要通过Ip过滤的。有应用场景吗？
		if("byIp".equalsIgnoreCase(filter)){
			String sql = "select * from appALL t where t.ip is not null and t.ip = ? ";
			ret = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql, ip);	
			return ret;
		}
		
		// 3.4、 默认，同 all
		String sql = "select * from appALL t where t.ip is null or ( t.ip is not null and t.ip = ? )";
		ret= SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql, ip);	
		return ret;
	}

	public List<Map<String, Object>> getLearder(HttpServletRequest request) {
		PropertiesUtil dataSourceProperties = PropertiesUtil.getInstance("GwssiGetLeader"); 
		String sql =dataSourceProperties.getProperty("getLearderSQL");
		String userid= request.getParameter("userid");
		List<Map<String, Object>> ret =null;
		
		System.out.println("获取当前用户的领导人，当前用户ID为页面传递过来的 ，用户ID为 ====> " + userid);
		
		if(userid==null ||"null".equalsIgnoreCase(userid))
		{
			if(null!=request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_ID)){
				userid =(String) request.getSession().getAttribute(AppConstants.SESSION_KEY_USER_ID);
				
				System.out.println("获取当前用户的领导人，当前用户ID为从session中获取的 ，用户ID为 ====> " + userid);
				
				userid=StringUtils.replaceChars(StringUtils.lowerCase(userid), "@", "_");
			}
		}
	
/*		if(StringUtils.isNotEmpty(userid)){
			ret= SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCM,sql, userid);		
		}*/
		
		
		
		if(ret==null||ret.size()<1){
		
			//ret =getLeaderGroup(userid);
			ret =getLeaderGroupByYYJC(userid);
		}
		
		
		
		if(ret!=null&&ret.size()>0){
	        List<Map<String,Object>> listMap2 = new LinkedList<Map<String,Object>>();  
	        Set<Map> setMap = new HashSet<Map>();  
	        for(Map<String,Object> map1 : ret){  
	            if(setMap.add(map1)){  
	                listMap2.add(map1);  
	            }  
	        }  
	        
	        return listMap2;
		}

          

 
          
		
		return ret;
	}
	
	
	/**
	 * 获取某人的领导
	 * @param currgroupid
	 * @return
	 */
	public  List<Map<String, Object>>  getLeaderGroup(String username){
		Map<String,Map<String,Object>> org; 
		List<Map<String, Object>> groupList;//所有组织结构的groupId集合
		List<String> adminlist = null;
		
		//查询用户id
		String useridsql="select userid from wcmuser u  where u.username = ?  and u.status ='30' ";
		int userid =SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_WCM_SYNC, useridsql, username);
		
		String groupidssql="select w.groupid from  Wcmgrpuser w where w.userid = '"+userid+"'";
		List<Map<String, Object>>  currgroups =SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCM_SYNC,groupidssql);
		
		
		String sql = "SELECT * FROM WCMGROUP m start with m.groupid ='1'  connect by m.parentid= prior m.groupid ";
		groupList = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCM_SYNC,sql);
		org=new HashMap<String,Map<String,Object>>();
		for(Map map:groupList){
			org.put(((BigDecimal) map.get("GROUPID")).toString(), map);
		}
		

		logger.info(">>>>>>>>>获取有管理员的组织机构");
		sql ="select p.groupid as groupid from Wcmgrpuser p where p.isadministrator ='1'";
		List<Map<String, Object>> groupInfo = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCM_SYNC, sql);
		if(groupInfo!=null&&groupInfo.size()>0){
			adminlist=new ArrayList<String>();
			for(Map<String,Object> map:groupInfo){
				String gid =((BigDecimal) map.get("GROUPID")).toString();
				adminlist.add(gid);
			}
		}
		
		
		 
	    HashSet<String> leadergroup=new HashSet<String>();	
		if(currgroups!=null&&currgroups.size()>0){
			for(Map<String,Object> map:currgroups){
				String uid=((BigDecimal) map.get("GROUPID")).toString();
			
				String leardergroupid=getUpAdminStr(org,adminlist,uid);
				logger.info("当前组织机构id："+uid +"上级组织机构管理机构id："+leardergroupid) ;
				if(StringUtils.isNotEmpty(leardergroupid)){
					leadergroup.add(leardergroupid);
				}
			}
		}
		
		String sql1="select  u.userid,u.username,u.truename from Wcmgrpuser t ,wcmuser u where t.isadministrator = '1' and u.userid =t.userid ";
		if(leadergroup.size()<1){
			 logger.info("该部门上级管理机构为空，获取所有可管理人员");
			//return null;
		}else{
			sql1=sql1+ " and t.groupid in(";
			  String[] leaders = leadergroup.toArray(new String[] {});   
		        for (int i = 0; i < leaders.length; i++) {
		            if(i==leaders.length-1){
		            	sql1=sql1+"'";
		                sql1+=leaders[i];
		                sql1=sql1+"'";
		                break;
		            }
		            sql1=sql1+"'";
		            sql1+=leaders[i]+"'"+",";
		        
		        }
				sql1=sql1+")" ;    
		}



		

		
		
		List<Map<String, Object>>  leadergroup11 = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCM_SYNC, sql1);
		return leadergroup11;
		
	}
	
	public String getUpAdminStr(Map<String,Map<String,Object>> org,List<String> adminlist,String currOrgid){
		
		if(adminlist.contains(currOrgid)){
			return currOrgid;
		}else{
			while(!adminlist.contains(currOrgid)){
				currOrgid=getUpOrg(org,currOrgid);
				if(currOrgid==null){
					break ;
				}
			}
			return currOrgid;
		}
	}
	
	public String getUpOrg(Map<String,Map<String,Object>> org,String currOrgid){
		Map<String ,Object> map = org.get(currOrgid);
		if(map!=null){
			
			return((BigDecimal) map.get("PARENTID")).toString();
		}else{
			return null;
		}
	}
	public static void main(String[] args) throws WCMException{
		//String userid="HUANGXD2_SZAIC";
		String userid="WANGQIAN4_SZAIC";
		userid=StringUtils.replaceChars(StringUtils.lowerCase(userid), "@", "_");
		System.out.println(userid);
		PortalHomeService WcmSaveOrUpdateGroup = new PortalHomeService();
		//List<Map<String, Object>> list=WcmSaveOrUpdateGroup.getLeaderGroup(userid);
		List<Map<String, Object>> list=WcmSaveOrUpdateGroup.getLeaderGroupByYYJC(userid);
		
		for(Map<String,Object> m:list){
			System.out.println(m.get("USERNAME"));
			
		}
	}
	
	
	
	/**
	 * 从应用集成库中获取用户的当前管理员
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>以下为从应用集成中获取代办>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 * @param username
	 * @return
	 */
	public  List<Map<String, Object>>  getLeaderGroupByYYJC(String username){
		Map<String,Map<String,Object>> org; 
		List<Map<String, Object>> groupList;//所有组织结构的groupId集合
		List<String> adminlist = null;
		//查询用户id
		if(StringUtils.isEmpty(username)){
			return null;
		}
		
		username= StringUtils.replace(StringUtils.upperCase(username), "_", "@");
		
		
		String useridsql="select T.DEPARTMENT_CODE from jc_public_people T WHERE T.USER_ID = ?  ";
		List<Map<String, Object>> DEPARTMENT_CODE =SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC, useridsql, username);
		if(DEPARTMENT_CODE==null||DEPARTMENT_CODE.size()<1){
			return null;
		}
		//获取当前组织机构的id
		String currgroup =(String) DEPARTMENT_CODE.get(0).get("DEPARTMENT_CODE") ;
		
		//获取组织机构数
		String sql = "SELECT * FROM jc_public_department m start with m.code = '0000'  connect by  m.parent_code = prior m.code ";
		groupList = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql);
		org=new HashMap<String,Map<String,Object>>();
		for(Map map:groupList){
			org.put(( map.get("CODE")).toString(), map);
		}
		
		
		//获取管理员的组织机构数
		//sql ="select distinct(p.department_code) department_code from jc_wcm_usergroupadmin t ,jc_public_people  p where t.user_id =p.user_id ";
		
		sql="select distinct(department_code) as department_code from (select p.department_code department_code from jc_wcm_usergroupadmin t, jc_public_people p where t.user_id = p.user_id union select p.department_code from jc_wcm_usergroupadmin p)  u where u.department_code is not null ";
		List<Map<String, Object>> groupInfo = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC, sql);
		if(groupInfo!=null&&groupInfo.size()>0){
			adminlist=new ArrayList<String>();
			for(Map<String,Object> map:groupInfo){
				String gid =map.get("DEPARTMENT_CODE").toString();
				adminlist.add(gid);
			}
		}
		
		String yyjcname = d1.getProperty("db.yyjc.user.name");
	    HashSet<String> leadergroup=new HashSet<String>();	
	    String leardergroupid=getYYJCUpAdminStr(org,adminlist,currgroup);
	   logger.info("当前人员>>"+username+"当前部门>>"+currgroup+" 领导部门>>"+leardergroupid);
	    
	    
	    StringBuffer leadergroupsql  = new StringBuffer();
	    leadergroupsql.append("select  u.userid,u.username,u.truename from wcmuser u where u.username in(");
	    
	    
	    StringBuffer sqlgetyyjcleader = new StringBuffer();
	    sqlgetyyjcleader.append("select  replace(lower(t.user_id),'@','_') userid").append(" from ").append(" jc_wcm_usergroupadmin t, ");
	    sqlgetyyjcleader.append("jc_public_people u  ").append("where t.user_id = u.user_id ");
	    if(StringUtils.isEmpty(leardergroupid)){
//	    	 logger.info("该部门>>"+currgroup+">>人员"+username+"上级管理机构为空，获取所有可管理人员");
	    	 logger.info("该部门>>"+currgroup+">>人员"+username+"上级管理机构为空，只能手动指定人员信息");

	    	 return  null;
	    }else{
	    	sqlgetyyjcleader.append("and (u.department_code =").append("'").append(leardergroupid).append("'");
	    	sqlgetyyjcleader.append("or t.department_code =").append("'").append(leardergroupid).append("')");
	    }
	    List<Map<String, Object>>  leadergroup11  =SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sqlgetyyjcleader.toString());
	    List<String> yyjcleaders = new ArrayList<String>();
	    if(leadergroup11!=null&&leadergroup11.size()>0){
	    	for(Map<String,Object> map:leadergroup11){
	    		yyjcleaders.add((String) map.get("USERID"));
	    	}
	    }
	  String[] leaders = yyjcleaders.toArray(new String[] {});   
        for (int i = 0; i < leaders.length; i++) {
            if(i==leaders.length-1){
            	leadergroupsql.append("'");
            	leadergroupsql.append(leaders[i]);
            	leadergroupsql.append("'");
                break;
            }
            leadergroupsql.append("'");
        	leadergroupsql.append(leaders[i]);
        	  leadergroupsql.append("' ,");
        }
	 
	    

	    leadergroupsql.append(")");
	    
	    
	    List<Map<String, Object>>  leadergroup12  =SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCM_SYNC,leadergroupsql.toString());
	    return leadergroup12;
	   // return null;
	}
	
	
	public String getYYJCUpAdminStr(Map<String,Map<String,Object>> org,List<String> adminlist,String currOrgid){
		
		if(adminlist.contains(currOrgid)){
			return currOrgid;
		}else{
			while(!adminlist.contains(currOrgid)){
				currOrgid=getYYJCUpOrg(org,currOrgid);
				if(currOrgid==null){
					break ;
				}
			}
			return currOrgid;
		}
	}
	
	public String getYYJCUpOrg(Map<String,Map<String,Object>> org,String currOrgid){
		Map<String ,Object> map = org.get(currOrgid);
		if(map!=null){
			if(map.get("PARENT_CODE")==null){
				return null;
			}else{
				return map.get("PARENT_CODE").toString();
			}
			
		
		}else{
			return null;
		}
	}
	
	
	
	
}
