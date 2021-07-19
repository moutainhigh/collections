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
 * @author ����
 * �û���Ϣ�־���
 */
public class UserDAO {
	private static final Logger log = Logger.getLogger(DBase.class);
	private static UserDAO inst = new UserDAO(); 		// Ψһʵ��
	private Map usersMap = new HashMap();
	
	protected UserDAO(){
	}
	
	static public UserDAO getInst(){
		return inst;
	}
	
	/**
	 * ��ջ���
	 **/
	public void init(){
		usersMap.clear();
	}
	
	/**
	 * �����û�ID����û��Ľ�ɫ��Ϣ
	 * @param userId �û�ID
	 * @return ��ɫ�б�
	 * @throws UserNotFoundException,SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List getUserRoles(String userId) throws UserNotFoundException,SQLException{
		Map user = getUserInfo(userId);
		return (List) user.get("Roles");
	}
	
	/**
	 * �����û�id����û���Ϣ
	 * @param userId �û�id
	 * @return �û���ϢMap
	 * @throws UserNotFoundException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Map getUserInfo(String userId) throws UserNotFoundException, SQLException{
		if(usersMap.containsKey(userId)){
			Map user = (Map) usersMap.get(userId);
			log.debug("�Ѵӻ����������û���Ϣ��");
			return user;
		}
		
		String sql = "select YHID_PK,YHZH,MAINROLE,ROLEIDS from xt_zzjg_yh_new WHERE YHID_PK = '" + userId + "'" ;
		List users = DBase.getInst().query(sql);
		if(users.size() > 0){
			Map user = (Map) users.get(0);
			List roles = new ArrayList();
			log.debug("�û���Ҫ��ɫid=" + user.get("MAINROLE"));
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
				log.debug("�û�δ�����Ҫ��ɫ");
			}
			user.put("Roles", roles);
			log.debug("IDΪ[" + userId + "]���û���Ϣ�ѻ���");
			usersMap.put(userId, user);
			return user;
		}
		log.debug("IDΪ[" + userId + "]���û�������");
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
