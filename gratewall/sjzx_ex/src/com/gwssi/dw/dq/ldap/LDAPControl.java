package com.gwssi.dw.dq.ldap;

import java.util.List;
import java.util.Set;

import com.gwssi.dw.dq.DqContants;
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;
import com.novell.ldap.LDAPSearchResults;

public class LDAPControl
{
	private String host;
	private int port;
	private String anthor;
	private String password;
	private String suffix;
	private String[] groups;
	
	private LDAPConnection lc = null;
	
	/**
	 * Ĭ�Ϸ���,���ü���
	 */
	public LDAPControl() {
		init();
		this.host = DqContants.LDAP_HOST;
		this.port = DqContants.LDAP_PORT;
		this.anthor = "cn=Directory Manager";
		this.password = "11111111";
		this.suffix = "dc=gwssi,dc=com";
	}
	
	public LDAPControl(String host, int port) {
		init();
		this.host = host;
		this.port = port;
		this.anthor = "cn=Directory Manager";
		this.password = "11111111";
		this.suffix = "dc=gwssi,dc=com";
	}
	public LDAPControl(String host, int port, String author, String password, String suffix) {
		init();
		this.host = host;
		this.port = port;
		this.anthor = author;
		this.password = password;
		this.suffix = suffix;
	}
	
	private void init() {
		groups = new String[]{"����Ǽ���ͨ�û���","����ǼǸ߼��û���",
				"��ҵ�����ͨ�û���","��ҵ��ܸ߼��û���",
				"����������ͨ�û���","��������߼��û���",
				"���߾ٱ���ͨ�û���","���߾ٱ��߼��û���",
				"��Ʒ�����ͨ�û���","��Ʒ��ܸ߼��û���",
				"��������ͨ�û���","������߼��û���",
				"ʳƷ��ȫ��ͨ�û���","ʳƷ��ȫ�߼��û���",
				"ʳƷ�����ͨ�û���","ʳƷ��ɸ߼��û���"};
	}
	
	/**
	 * ��LDAP������û�,������û���������
	 * @param userId
	 * @param userName �û���������
	 */
	public boolean addUserNoGroup(String userId, String userName) {
		boolean flag = true;
		try {
			addEntry(userId, userName);
		} catch (LDAPException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			closeConnection();
		}
		return flag;
	}
	public boolean insertOrUpdateUserWithGroups(List list) {
		boolean flag = true;
		LDAPUser user;
		for (int i=0;i < list.size();i++) {
			user = (LDAPUser)list.get(i);
			if("".equals(user.getGroupIds())){
				flag = insertOrUpdateUserWithGroups(user.getUid(), user.getCn(), new String[0]);
			}else{
				flag = insertOrUpdateUserWithGroups(user.getUid(), user.getCn(), user.getGroupIds().split(","));
			}
			if (!flag) {
				System.out.println("83��");
				break;
			}
		}
		return flag;
	}
	/**
	 * ��LDAP������û����������Ϣ
	 * @param userId
	 * @param userName �û���������
	 * @param groupIds ����±�����,String
	 */
	public boolean insertOrUpdateUserWithGroups(String userId, String userName, String[] groupIds) {
		return insertOrUpdateUserWithGroups(userId, userName, userId, groupIds);
	}
	/**
	 * ��LDAP������û������뼰�������Ϣ
	 * @param userId
	 * @param userName �û���������
	 * @param groupIds ����±�����,String
	 */
	public boolean insertOrUpdateUserWithGroups(String userId, String userName, String password, String[] groupIds) {
		boolean flag = true;
		try {
			if (!checkEntry(userId)) { //�û�������
				addEntry(userId, password, userName);
			}
			else { //�û��Ѿ�����
				removeFromAllGroup(userId);
			}
			if (groupIds != null && groupIds.length != 0) {
				setEntryToGroups(userId, transID2CN(groupIds));
			}
		} catch (LDAPException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			closeConnection();
		}
		return flag;
	}
	/**
	 * ���̨���Ľ�ɫȨ��ʱ,�����������µ��û�Ȩ��
	 * @param userIds
	 * @param groupIds
	 * @return
	 */
	public boolean batchUpdateUsers(String[] userIds, String[] groupIds) {
		boolean flag = true;
		if (userIds == null || groupIds == null)
			return false;
		try {
			String[] newGroupIds = transID2CN(groupIds);
			for (int i=0;i < userIds.length;i++) {
				if (checkEntry(userIds[i])) { //�û�����
					removeFromAllGroup(userIds[i]);
					if (newGroupIds != null && newGroupIds.length != 0) {
						setEntryToGroups(userIds[i], newGroupIds);
					}
				}
			}
		} catch (LDAPException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			closeConnection();
		}
		return flag;
	}
	/**
	 * ��LDAP�û������������д��
	 * @param userId
	 * @param groupIds ����±�����,String,ֻ���µ�ID����
	 */
	public boolean setUserToGroups(String userId, String[] groupIds) {
		boolean flag = true;
		try {
			removeFromAllGroup(userId);
			setEntryToGroups(userId, transID2CN(groupIds));
		} catch (LDAPException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			closeConnection();
		}
		return flag;
	}
	/**
	 * ��LDAP��ɾ���û��Լ������������Ϣ
	 * @param userId
	 */
	public boolean deleteUser(String userId) {
		boolean flag = true;
		try {
			removeFromAllGroup(userId);
			delEntry(userId);
		} catch (LDAPException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			closeConnection();
		}
		return flag;
	}
	
	public void listAll() {
		try {
			getConnecton();
			LDAPSearchResults rs = search("objectClass=*");
			int count = 0;
	        while(rs.hasMore()){
	            System.out.println(rs.next());
	            count++;
	        }
	        System.out.println("����"+count+"����¼��");
		} catch (LDAPException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
	private void setEntryToGroups(String uid, String[] groupCNs) throws LDAPException {
		getConnecton();
		LDAPModification addIn = new LDAPModification(LDAPModification.ADD, new LDAPAttribute("uniqueMember","uid=" + uid +"," + suffix));
		for (int i=0;i < groupCNs.length;i++) {
			try {
				lc.modify("cn=" + groupCNs[i] + "," + suffix, addIn);
			} catch (LDAPException e) {
				//Ӧ�ò���������������ڵ������?!
			}
		}
	}
	
	private void addEntry(String uid, String cn) throws LDAPException {
		addEntry(uid, uid, cn);
	}
	
	private void addEntry(String uid, String userPassword, String cn) throws LDAPException {
		getConnecton();
		LDAPAttributeSet attributeSet = new LDAPAttributeSet();
	    attributeSet.add(new LDAPAttribute("objectclass", new String[] {"top","person","organizationalPerson","inetorgperson"}));
	    attributeSet.add(new LDAPAttribute("cn", cn));
	    attributeSet.add(new LDAPAttribute("givenname", cn));
	    attributeSet.add(new LDAPAttribute("sn", uid));
	    attributeSet.add(new LDAPAttribute("userPassword", userPassword));
	    attributeSet.add(new LDAPAttribute("preferredLanguage", "zh-CN"));
	    LDAPEntry entry = new LDAPEntry("uid=" + uid +"," + suffix, attributeSet);
	    lc.add(entry);
	}
	
	private void delEntry(String uid) throws LDAPException {
		getConnecton();
		lc.delete("uid=" + uid + "," + suffix);
	}
	
	private void removeFromAllGroup(String uid) throws LDAPException {
		getConnecton();
		LDAPModification modify = new LDAPModification(LDAPModification.DELETE, new LDAPAttribute("uniqueMember","uid=" + uid +"," + suffix));
		LDAPSearchResults rs = search("objectClass=groupofuniquenames");
		while (rs.hasMore()) {
			LDAPEntry entry = rs.next();
			try {
				lc.modify(entry.getDN(), modify);
			} catch (LDAPException e) {
				//û�и��û����ԵĻ������쳣
			}
		}
	}
	
	/**
	 * �ж��û��Ƿ��Ѿ���LDAP�û���
	 * @param uid
	 * @return true ����<br/>
	 * 		  false �����ڻ����
	 */
	private boolean checkEntry(String uid) {
		LDAPSearchResults rs = null;
		try {
			getConnecton();
			rs = search("uid=" + uid);
		} catch (LDAPException e) {
			e.printStackTrace();
			return false;
		}
		if (rs.hasMore())
			return true;
		else
			return false;
	}
	
	private LDAPSearchResults search(String filter) throws LDAPException {
		LDAPSearchResults rs = null;
		rs = lc.search(suffix,LDAPConnection.SCOPE_SUB,filter,null,false);
		return rs;
	}
	
	private void getConnecton() throws LDAPException {
		if (lc != null && lc.isConnected()) {
			return ;
		}
		lc = new LDAPConnection();
		lc.connect(host,port);
		lc.bind(LDAPConnection.LDAP_V3,anthor,password);
	}
	
	private void closeConnection() {
		if (lc != null && lc.isConnected()) {
			try {
				lc.disconnect();
			} catch (LDAPException e) {
//				e.printStackTrace();
			}
		}
	}
	
	private String[] transID2CN(String[] groupIds) {
		String[] newGroupIds = ignoreRepeat(groupIds);
		String[] groupNames = new String[newGroupIds.length];
		try {
			for (int i=0;i < newGroupIds.length;i++) {
				groupNames[i] = this.groups[Integer.parseInt(newGroupIds[i])-1];
			}
		} catch(Exception e) {
			System.out.println("��ֵת������");
		}
		return groupNames;
	}
	
	/**
	 * ȥ�������е��ظ�ֵ
	 * @param hasRepeat
	 * @return
	 */
	private String[] ignoreRepeat(String[] hasRepeat) {
		Set s = new java.util.HashSet();
		for (int i = 0; i < hasRepeat.length; i++) {
			s.add(hasRepeat[i]);
		}
		String[] noRepeat = new String[s.size()];
		s.toArray(noRepeat);
//		for (int i = 0; i < noRepeat.length; i++) {
//			System.out.println(noRepeat[i]);
//		}
		return noRepeat;
	}
	
	public static void main(String args[]) {
		LDAPControl control = new LDAPControl("160.100.0.218", 389);
//		control.ignoreRepeat(new String[]{"1","2","3","2","2","1"});
//		System.out.println(control.checkEntry("admin"));
		control.listAll();
//		try {
//			control.setEntryToGroups("20090220124616255941",new String[]{"����Ǽ���ͨ�û���","��ҵ�����ͨ�û���","����������ͨ�û���","���߾ٱ���ͨ�û���","��Ʒ�����ͨ�û���","��������ͨ�û���"});
//		} catch (LDAPException e) {
//			e.printStackTrace();
//		}
		control.insertOrUpdateUserWithGroups("temp15","��ʱ�û�15","gw3625",new String[]{"1","3","5","7","9","11","13"});
//		control.deleteUser("senior1");
		System.out.println("----------------------");
		control.listAll();
	}

}
