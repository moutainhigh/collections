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
 * Ϊ����Ϲ����� ��ȡ�·���֯�ķ�ʽ����������֯�Ĺ�����ϵ����
 * 	   �ж�ÿ����֯����Ա�Ƿ��й���Ȩ�� ���û������������֯ ֱ���ҵ��й���Ȩ�޵���Ա����֯
 * 	 	Ȼ������˵���֯�·����ײ� ������������֯������ϵ���� 
 * @author yangzihao
 */
public class WcmSaveOrUpdateGroup extends BaseStatefulScheduleWorker{
	
	private static Logger logger = Logger.getLogger(WcmSaveOrUpdateGroup.class);
	
	private static String datasourcekey = "sjw_wcm1201";
	private static String sql = "SELECT * FROM WCMGROUP m start with m.groupid ='1'  connect by m.parentid= prior m.groupid ";
	
	private static Map<String,Map<String,Object>> org; 
	private static List<Map<String, Object>> groupList;//������֯�ṹ��groupId����
	
	private static  List<String> adminlist = null;
	/**
	 * ��ʼ������ÿ�ζ�ʱ��������ȡ��
	 */
	protected  void init(){
		logger.info(">>>>>>>>>>>>��֯����map��ʼ��");
		groupList = SpringJdbcUtil.query(datasourcekey,sql);
		org=new HashMap<String,Map<String,Object>>();
		for(Map map:groupList){
			org.put(((BigDecimal) map.get("GROUPID")).toString(), map);
		}
		

		logger.info(">>>>>>>>>��ȡ�й���Ա����֯����");
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
	 * ��ȡ���е��ϼ���֯����ID
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
	 * ��ȡ�ϼ���֯����id
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
	 * ��ȡ�ǹ���Ա����֯����
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
		logger.info(">>>>>>>>>>���ڽ��г�ʼ������>>>>>>>>>>>>>>>>>");
		init();
		
		BigDecimal groupId;
		String groupIdstr = "";
		List<Map<String,Object>> upadminuserlist=null; //�ϼ���֯��������Ա
		String sServiceId = "wcm61_group", sMethodName="saveGroups";
		
		
		
		
		for(Map<String, Object> group : groupList){
			
			groupId = (BigDecimal) group.get("GROUPID");
			groupIdstr = groupId.toString();
		
			String admingroup =getUpAdminStr(groupIdstr);
			logger.info("�ϼ���֯������"+admingroup+"��������֯������"+groupId);
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
					groupUser.put("CurrGroupIds", gleft+""+groupIdstr);//������
					logger.info("UserId>>>>"+userId+"  CurrGroupIds>>>>>"+gleft+""+groupIdstr);
					//groupUser.put("ISADMINISTRATOR", "1");//������д��
					
					try {
						WCMServiceCaller.Call(sServiceId,sMethodName,groupUser,true);
					} catch (Exception e) {
						System.err.println(e.getMessage()+"�����û���֯��Ϣ���� UserId="+userId+"  groupId="+groupId);
						logger.info("�����û���֯��Ϣ���� UserId="+userId+"  groupId="+groupId);
						e.printStackTrace();
					}
				}else{
					logger.info("�����û���֯��Ϣ���� ����֯�޹����� �����и���֯Ҳ���޹����� groupId="+groupId);
				}
		
			}
		}
	}
	
	
	
	/**
	 * ��ȡadmin�û�
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
	 * ��ȡ�û�����֯����
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
	 * ��ȡ�û���֯����
	 * @param groupId
	 * @return
	 */
	protected List<Map<String, Object>> getOrgAdminUserGroups(String groupId){
		String sql="select  r.groupid,r.userid  from Wcmgrpuser r where r.userid in(select t.userid from  Wcmgrpuser t where t.isadministrator ='1' and t.groupid =?)";
		
		List<Map<String, Object>> groupInfo = SpringJdbcUtil.query(datasourcekey, sql, groupId);

		return groupInfo;
	}
	
	
	

	/**
	 * ��ȡĳ�˵��쵼
	 * @param currgroupid
	 * @return
	 */
	public  List<Map<String, Object>>  getLeaderGroup(String username){
		init();
		
		//��ѯ�û�id
		String useridsql="select userid from wcmuser u  where u.username = ?  and u.status ='30' ";
		int userid =SpringJdbcUtil.queryForInt(datasourcekey, useridsql, username);
		
		String groupidssql="select w.groupid from  Wcmgrpuser w where w.userid = '"+userid+"'";
		List<Map<String, Object>>  currgroups =SpringJdbcUtil.query(datasourcekey,groupidssql);
		
	    HashSet<String> leadergroup=new HashSet<String>();	
		if(currgroups!=null&&currgroups.size()>0){
			for(Map<String,Object> map:currgroups){
				String uid=((BigDecimal) map.get("GROUPID")).toString();
				
				String leadergroupid =getUpAdminStr(uid);
				logger.info("��ǰ��֯����id��"+uid +"  �쵼��֯����id��"+leadergroupid);
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
		//logger.info("���>>>>>>>>>>>"+s);
		WcmSaveOrUpdateGroup.init();
		//WcmSaveOrUpdateGroup.getUpAdminStr("300");
		
		//��ȡ����Ա����֯����
		String upgroupid = WcmSaveOrUpdateGroup.getUpAdminStr("296");
		System.out.println("�ϼ�����Ա��֯������"+upgroupid);
		//System.out.println(WcmSaveOrUpdateGroup.getUpAdminStr("68"));
		//WcmSaveOrUpdateGroup.execute();
*/	}
}
