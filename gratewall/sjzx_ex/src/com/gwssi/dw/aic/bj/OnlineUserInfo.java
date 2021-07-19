package com.gwssi.dw.aic.bj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.gwssi.common.component.exception.TxnErrorException;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.util.CalendarUtil;

/**
 * ��¼��ǰ�����û������ÿ���û���½
 * @author BarryWei
 */
public class OnlineUserInfo
{
	
	public static final String DB_CONFIG = "app";
	private static final String DB_CONNECT_TYPE = "dbConnectionType";
	
	private static String getConnectType(){
		return java.util.ResourceBundle.getBundle(DB_CONFIG).getString(DB_CONNECT_TYPE);
	}
	
	public static String dbType = getConnectType();
	
	/**
	 * �ѵ�ǰ��SessionID��Ϊkey����sessionId�����SessionData������Ϊvalue����
	 * @param sid
	 * @param data
	 * @throws DBException 
	 */
	public static void addLoginUser(String sid, String userName, String ipAddress, String jgCode, String userID) throws DBException{
		// ���֮ǰ�ȿ����ü�¼�Ƿ��Ѿ����ڣ��������(��ˢ�°�ť�õ���)��ɶҲ��������������ڣ������
		String bSql = "select * from login_session where SESSION_ID = '" + sid + "'";
		List bList = DbUtils.select(bSql, dbType);
		if (bList.size() > 0){
			// DbUtils.select("delete from login_session where SESSION_ID = '" + sid + "'", dbType);
		}else{
			List sqlList = new ArrayList();
			String date = CalendarUtil.getCalendarByFormat(CalendarUtil.FORMAT11);
			String times = CalendarUtil.getCalendarByFormat(CalendarUtil.FORMAT7);
			
			sqlList.add( "insert into login_session (SESSION_ID, " +
					"USERNAME, IPADDRESS,LOGIN_DATE,LOGIN_TIME,JGID_PK,YHID_PK) values ('" + sid + "', '" +
					userName + "', '" + ipAddress + "', '" + date + "', '" +
					times + "','" + jgCode + "','"+userID+"')");
			sqlList.add( "insert into login_session_hs (SESSION_ID, " +
					"USERNAME, IPADDRESS,LOGIN_DATE,LOGIN_TIME,JGID_PK,YHID_PK) values ('" + sid + "', '" +
					userName + "', '" + ipAddress + "', '" + date + "', '" +
					times + "', '" + jgCode + "','"+userID+"')");			
			DbUtils.execute(sqlList, dbType);
		}
		
//		printOnlineUserMap();
	}
	
	/**
	 * ����SessionIDɾ��Map�е�ָ������
	 * @param sid
	 * @throws DBException 
	 */
	public static void removeLoginUser(String sid) throws DBException{	
		String sql = "delete from login_session where SESSION_ID = '" + sid + "'";		
		DbUtils.execute(sql, dbType);
		System.out.println("�˳�ϵͳ!sessionId:" + sid);
//		printOnlineUserMap();
	}
	
	/**
	 * �õ�һ��ָ���û������߸���
	 * @param userName
	 * @throws DBException 
	 */
	public static int getOnlineUserNumber(String userName) throws DBException{
		String sql = "select ipaddress from login_session t where upper(t.username)='"
			+ userName.toUpperCase() + "' group by t.ipaddress";
		List list = DbUtils.select(sql, dbType);
		return list.size();
	}
	
	/**
	 * ��ȡ��ǰ�û��ܷ����
	 * @param sid
	 * @throws DBException 
	 * @throws TxnErrorException 
	 */
	public static boolean hasLoginPriv(String userName, String ipAddress) throws DBException, TxnErrorException{
		String sql = "select * from login_session t where upper(t.username)='"
			+ userName.toUpperCase() + "' and t.ipAddress = '" + ipAddress + "'";
		List list = null;
		try{
			list = DbUtils.select(sql, dbType);
		}catch(Exception e){
			e.printStackTrace();
		}
		// ����ҵ���¼��֤���Ѿ���½����ֱ�ӷ���true
		if (list.size() > 0){
			return true;
		}else{// ��һ�ε�½
			// ��ʼ��ȡ�û�����½����
			list = DbUtils.select("select maxline from xt_zzjg_yh_new where upper(yhzh) = '" + userName.toUpperCase() + "'", dbType);
			// ����������С��1.���������������
			if (list.size() < 1){
				throw new TxnErrorException("999999", "�û�������!");
			}else{
				Map map = (Map)list.get(0);
				String ss = map.get("MAXLINE") != null ? map.get("MAXLINE").toString() : "1";
				int maxLoginNumber = Integer.parseInt( ss );
				// ������ֵС��1
				if (maxLoginNumber < 1){
					throw new TxnErrorException("999999", "����½�����ô���!");
				}
				if ( getOnlineUserNumber(userName) < maxLoginNumber){
					return true;
				}
			}
		}
		
		// �������㶼�����㣬����false����ֹ��½
		return false;
	}
	
	/**
	 * ��ӡ��ǰ�ĵ�½��Ϣ
	 * @throws DBException 
	 */
	public static void printOnlineUserMap() throws DBException{
		List list = DbUtils.select("select * from login_session", dbType);
		Iterator iter = list.iterator();
		System.out.println("------------------------ ��ӡSession�б� -----------------------------");
		while(iter.hasNext()){
			Map key = (Map) iter.next();
			System.out.println("- key :" + key.get("SESSION_ID"));
			System.out.println("- IP  :" + key.get("IPADDRESS"));
			System.out.println("- Name:" + key.get("USERNAME"));
			System.out.println("-----------�û��ָ���---------");
		}
		System.out.println("---------------------------------------------------------------------");
	}
}
