package com.gwssi.OAmove.wcmTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.gwssi.util.SpringJdbcUtil;
import com.trs.components.common.job.BaseStatefulScheduleWorker;
import com.trs.components.video.util.StringUtil;
import com.trs.infra.common.WCMException;
import com.trs.web2frame.WCMServiceCaller;

/**
 * 为了配合工作流 采取下方组织的方式保存人与组织的关联关系数据
 * 	   判断每个组织的人员是否有管理权限 如果没有则向上找组织 直到找到有管理权限的人员的组织
 * 	 	然后将这个人的组织下方至底层 即增加人与组织关联关系数据 
 * @author yangzihao
 */
public class WcmSaveOrUpdateGroup extends BaseStatefulScheduleWorker{
	
	private static Logger logger = Logger.getLogger(WcmSaveOrUpdateGroup.class);
	
	private static String datasourcekey = "sjw_wcm1201";
	private static String sql = "SELECT * FROM WCMGROUP m start with m.groupid ='1'  connect by m.parentid= prior m.groupid ";
	
	private static Map<String,Map<String,Object>> org; 
	private static List<Map<String, Object>> groupList;//所有组织结构的groupId集合
	
	private static  List<String> adminlist = null;
	/**
	 * 初始化数据每次定时任务都重新取出
	 */
	protected  void init(){
		logger.info(">>>>>>>>>>>>组织机构map初始化");
		groupList = SpringJdbcUtil.query(datasourcekey,sql);
		org=new HashMap<String,Map<String,Object>>();
		for(Map map:groupList){
			org.put(((BigDecimal) map.get("GROUPID")).toString(), map);
		}
		

		logger.info(">>>>>>>>>获取有管理员的组织机构");
		sql ="select p.groupid as groupid from Wcmgrpuser p where p.isadministrator ='1'";
		List<Map<String, Object>> groupInfo = SpringJdbcUtil.query(datasourcekey, sql);
		if(groupInfo!=null&&groupInfo.size()>0){
			adminlist=new ArrayList<String>();
			for(Map<String,Object> map:groupInfo){
				String gid =((BigDecimal) map.get("GROUPID")).toString();
				adminlist.add(gid);
			}
		}
	
	
	}
	
	/**
	 * 获取所有的上级组织机构ID
	 * @param currOrgid
	 */
	public List<String> getUpOrgList(String currOrgid){
		List<String> upOrglist =new ArrayList<String>();
		currOrgid=getUpOrg(currOrgid);
				
		while(currOrgid!=null){
			upOrglist.add(getUpOrg(currOrgid));
			currOrgid=getUpOrg(currOrgid);
		}
		return upOrglist;
	}
	
	/**
	 * 获取上级组织机构id
	 * @param currOrgid
	 * @return
	 */
	public String getUpOrg(String currOrgid){
		Map<String ,Object> map = org.get(currOrgid);
		if(map!=null){
			
			return((BigDecimal) map.get("PARENTID")).toString();
		}else{
			return null;
		}
	}
	
	/**
	 * 获取是管理员的组织机构
	 * @param currOrgid
	 * @return
	 */
	public String getUpAdminStr(String currOrgid){
		
		if(adminlist.contains(currOrgid)){
			return currOrgid;
		}else{
			while(!adminlist.contains(currOrgid)){
				currOrgid=getUpOrg(currOrgid);
				if(currOrgid==null){
					break ;
				}
			}
			return currOrgid;
		}
	}
	
	
	protected void execute() throws WCMException {
		logger.info(">>>>>>>>>>正在进行初始化工作>>>>>>>>>>>>>>>>>");
		init();
		
		BigDecimal groupId;
		String groupIdstr = "";
		List<Map<String,Object>> upadminuserlist=null; //上级组织机构管理员
		String sServiceId = "wcm61_group", sMethodName="saveGroups";
		
		
		
		
		for(Map<String, Object> group : groupList){
			
			groupId = (BigDecimal) group.get("GROUPID");
			groupIdstr = groupId.toString();
		
			String admingroup =getUpAdminStr(groupIdstr);
			logger.info("上级组织机构："+admingroup+"本级别组织机构："+groupId);
			List<String> adminlist=null;
			if(admingroup==null||admingroup.equals(groupIdstr)){
				 
				continue;
			}else{
				adminlist=getAdminUserBygroups(admingroup);
				if(adminlist==null||adminlist.size()<1){
					continue;
				}
				
			}
			
/*			
		for(String userId:adminlist){
				logger.info(">>>>"+userId);
			}
			
			logger.info("");*/
				
		for(String userId:adminlist){
				if(StringUtil.isNotEmpty(userId)){
					ConcurrentHashMap<String, String> groupUser = new ConcurrentHashMap<String,String>();
					String gleft=getUserIdsCurrGroup(userId);
					
					groupUser.put("UserId", userId);
					groupUser.put("CurrGroupIds", gleft+""+groupIdstr);//传入多个
					logger.info("UserId>>>>"+userId+"  CurrGroupIds>>>>>"+gleft+""+groupIdstr);
					//groupUser.put("ISADMINISTRATOR", "1");//触发器写入
					
					try {
						WCMServiceCaller.Call(sServiceId,sMethodName,groupUser,true);
					} catch (Exception e) {
						System.err.println(e.getMessage()+"保存用户组织信息出错 UserId="+userId+"  groupId="+groupId);
						logger.info("保存用户组织信息出错 UserId="+userId+"  groupId="+groupId);
						e.printStackTrace();
					}
				}else{
					logger.info("保存用户组织信息有误 该组织无管理者 且所有父组织也皆无管理者 groupId="+groupId);
				}
		
			}
		}
	}
	
	
	
	/**
	 * 获取admin用户
	 * @param groupId
	 * @return
	 */
	protected List<String> getAdminUserBygroups(String groupId){
		List<String> list=null;
		String sql="select t.userid from  Wcmgrpuser t where t.isadministrator ='1' and t.groupid =?";
		List<Map<String, Object>> groupInfo = SpringJdbcUtil.query(datasourcekey, sql, groupId);
		if(groupInfo!=null&&groupInfo.size()>0){
			list= new ArrayList<String>();
			for(Map<String,Object> map:groupInfo){
				String uid=((BigDecimal) map.get("USERID")).toString();
				list.add(uid);
			}
		}
		return list;
	}

	
	/**
	 * 获取用户的组织机构
	 * @param userid
	 * @return
	 */
	public String getUserIdsCurrGroup(String userid){
		String s="";
		String sql="select t.groupid from  Wcmgrpuser t where   t.userid =?";
		List<Map<String, Object>> groupInfo = SpringJdbcUtil.query(datasourcekey, sql, userid);
		if(groupInfo!=null&&groupInfo.size()>0){
			
			for(Map<String,Object> map:groupInfo){
				String uid=((BigDecimal) map.get("GROUPID")).toString();
				s=s+uid+",";
			}
		}
		return s;
		
	}
	
	
	/**
	 * 获取用户组织机构
	 * @param groupId
	 * @return
	 */
	protected List<Map<String, Object>> getOrgAdminUserGroups(String groupId){
		String sql="select  r.groupid,r.userid  from Wcmgrpuser r where r.userid in(select t.userid from  Wcmgrpuser t where t.isadministrator ='1' and t.groupid =?)";
		
		List<Map<String, Object>> groupInfo = SpringJdbcUtil.query(datasourcekey, sql, groupId);

		return groupInfo;
	}
	
	
	

	/**
	 * 获取某人的领导
	 * @param currgroupid
	 * @return
	 */
	public  List<Map<String, Object>>  getLeaderGroup(String username){
		init();
		
		//查询用户id
		String useridsql="select userid from wcmuser u  where u.username = ?  and u.status ='30' ";
		int userid =SpringJdbcUtil.queryForInt(datasourcekey, useridsql, username);
		
		String groupidssql="select w.groupid from  Wcmgrpuser w where w.userid = '"+userid+"'";
		List<Map<String, Object>>  currgroups =SpringJdbcUtil.query(datasourcekey,groupidssql);
		
	    HashSet<String> leadergroup=new HashSet<String>();	
		if(currgroups!=null&&currgroups.size()>0){
			for(Map<String,Object> map:currgroups){
				String uid=((BigDecimal) map.get("GROUPID")).toString();
				
				String leadergroupid =getUpAdminStr(uid);
				logger.info("当前组织机构id："+uid +"  领导组织机构id："+leadergroupid);
				leadergroup.add(leadergroupid);
			}
		}
		
		String sql1="select  u.userid,u.username,u.truename from Wcmgrpuser t ,wcmuser u where t.isadministrator = '1' and u.userid =t.userid  and t.groupid in(";
		if(leadergroup.size()<1){
			return null;
		}else{
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
		}
		sql1=sql1+")" ;    


		

		
		
		List<Map<String, Object>>  leadergroup11 = SpringJdbcUtil.query(datasourcekey, sql1);
		return leadergroup11;
		
	}
	
	public static void main(String[] args) throws WCMException{
		
		WcmSaveOrUpdateGroup WcmSaveOrUpdateGroup = new WcmSaveOrUpdateGroup();
		List<Map<String, Object>> list=WcmSaveOrUpdateGroup.getLeaderGroup("changruan_szaic");
		
		for(Map<String,Object> m:list){
			System.out.println(m.get("USERNAME"));
			
		}
		  
/*		
	//	String s=WcmSaveOrUpdateGroup.getUserIdsCurrGroup("9043");
		//logger.info("输出>>>>>>>>>>>"+s);
		WcmSaveOrUpdateGroup.init();
		//WcmSaveOrUpdateGroup.getUpAdminStr("300");
		
		//获取管理员的组织机构
		String upgroupid = WcmSaveOrUpdateGroup.getUpAdminStr("296");
		System.out.println("上级管理员组织机构："+upgroupid);
		//System.out.println(WcmSaveOrUpdateGroup.getUpAdminStr("68"));
		//WcmSaveOrUpdateGroup.execute();
*/	}
}
