/**
 * 
 */
package com.gwssi.sysmgr.priv.datapriv.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gwssi.sysmgr.priv.datapriv.exception.UserNotFoundException;


/**
 * @author 周扬
 * 用户信息持久类
 */
public class UserDAO {
	private static final Logger log = Logger.getLogger(DBase.class);
	private static UserDAO inst = new UserDAO(); 		// 唯一实例
	private Map usersMap = new HashMap();
	
	protected UserDAO(){
	}
	
	static public UserDAO getInst(){
		return inst;
	}
	
	/**
	 * 清空缓存
	 **/
	public void init(){
		usersMap.clear();
	}
	
	/**
	 * 根据用户ID获得用户的角色信息
	 * @param userId 用户ID
	 * @return 角色列表
	 * @throws UserNotFoundException,SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List getUserRoles(String userId) throws UserNotFoundException,SQLException{
		Map user = getUserInfo(userId);
		return (List) user.get("Roles");
	}
	
	/**
	 * 根据用户id获得用户信息
	 * @param userId 用户id
	 * @return 用户信息Map
	 * @throws UserNotFoundException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Map getUserInfo(String userId) throws UserNotFoundException, SQLException{
		if(usersMap.containsKey(userId)){
			Map user = (Map) usersMap.get(userId);
			log.debug("已从缓存中载入用户信息！");
			return user;
		}
		
		String sql = "select YHID_PK,YHZH,MAINROLE,ROLEIDS from xt_zzjg_yh_new WHERE YHID_PK = '" + userId + "'" ;
		List users = DBase.getInst().query(sql);
		if(users.size() > 0){
			Map user = (Map) users.get(0);
			List roles = new ArrayList();
			log.debug("用户主要角色id=" + user.get("MAINROLE"));
			if(user.get("MAINROLE") != null){
				roles.add(user.get("MAINROLE"));
			}
			try{
				String roleidString = user.get("ROLEIDS").toString();
				if(roleidString.length() == 0) throw new NullPointerException();
				String[] roleids = roleidString.split(",");
				for(int i = 0; i < roleids.length; i++){
					roles.add(roleids[i]);
				}
			} catch(NullPointerException e){
				log.debug("用户未分配次要角色");
			}
			user.put("Roles", roles);
			log.debug("ID为[" + userId + "]的用户信息已缓存");
			usersMap.put(userId, user);
			return user;
		}
		log.debug("ID为[" + userId + "]的用户不存在");
		throw new UserNotFoundException();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DBase.getInst().open();
			UserDAO.getInst().getUserRoles("5bc2e70650e948d09852f1853c41d801");
			UserDAO.getInst().getUserRoles("5bc2e70650e948d09852f1853c41d801");
			UserDAO.getInst().getUserRoles("a162f2ebed4a4692809583099373377b");
			UserDAO.getInst().getUserRoles("a162f2ebed4a4692809583099373377b");
			DBase.getInst().close();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
