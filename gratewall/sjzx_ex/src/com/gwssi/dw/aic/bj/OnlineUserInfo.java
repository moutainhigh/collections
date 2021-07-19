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
 * 记录当前在线用户情况，每次用户登陆
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
	 * 把当前的SessionID作为key，把sessionId对象的SessionData对象作为value传入
	 * @param sid
	 * @param data
	 * @throws DBException 
	 */
	public static void addLoginUser(String sid, String userName, String ipAddress, String jgCode, String userID) throws DBException{
		// 添加之前先看看该记录是否已经存在，如果存在(点刷新按钮得到的)，啥也不做，如果不存在，就添加
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
	 * 根据SessionID删除Map中的指定数据
	 * @param sid
	 * @throws DBException 
	 */
	public static void removeLoginUser(String sid) throws DBException{	
		String sql = "delete from login_session where SESSION_ID = '" + sid + "'";		
		DbUtils.execute(sql, dbType);
		System.out.println("退出系统!sessionId:" + sid);
//		printOnlineUserMap();
	}
	
	/**
	 * 得到一个指定用户的在线个数
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
	 * 获取当前用户能否登入
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
		// 如果找到记录，证明已经登陆过，直接返回true
		if (list.size() > 0){
			return true;
		}else{// 第一次登陆
			// 开始获取用户最大登陆数量
			list = DbUtils.select("select maxline from xt_zzjg_yh_new where upper(yhzh) = '" + userName.toUpperCase() + "'", dbType);
			// 如果最大数量小于1.或者其他特殊情况
			if (list.size() < 1){
				throw new TxnErrorException("999999", "用户名错误!");
			}else{
				Map map = (Map)list.get(0);
				String ss = map.get("MAXLINE") != null ? map.get("MAXLINE").toString() : "1";
				int maxLoginNumber = Integer.parseInt( ss );
				// 如果最大值小于1
				if (maxLoginNumber < 1){
					throw new TxnErrorException("999999", "最大登陆数配置错误!");
				}
				if ( getOnlineUserNumber(userName) < maxLoginNumber){
					return true;
				}
			}
		}
		
		// 以上两点都不满足，返回false，禁止登陆
		return false;
	}
	
	/**
	 * 打印当前的登陆信息
	 * @throws DBException 
	 */
	public static void printOnlineUserMap() throws DBException{
		List list = DbUtils.select("select * from login_session", dbType);
		Iterator iter = list.iterator();
		System.out.println("------------------------ 打印Session列表 -----------------------------");
		while(iter.hasNext()){
			Map key = (Map) iter.next();
			System.out.println("- key :" + key.get("SESSION_ID"));
			System.out.println("- IP  :" + key.get("IPADDRESS"));
			System.out.println("- Name:" + key.get("USERNAME"));
			System.out.println("-----------用户分隔符---------");
		}
		System.out.println("---------------------------------------------------------------------");
	}
}
