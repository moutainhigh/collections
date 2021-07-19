package com.gwssi.ad;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class UserList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		List ouList = new ArrayList();
		UserList userList = new UserList();
		
		txttest tt =new txttest();
		List ouList = tt.getOU();
		
		StringBuffer strsql = new StringBuffer();
		
		for(int i=0;i<ouList.size();i++){
			String n = ouList.get(i).toString();
			StringBuffer str = userList.getUser(n);
			strsql.append(str);
		}
		
		strsql.append("commit;");
		FileOperation.contentToTxt("D:\\alluser.sql",
				strsql.toString());
		
	}
	
	public StringBuffer getUser(String n){
		Properties env = new Properties();
//		String adminName = "changruan@szaic.gov.cn";// username@domain
		String adminName = "testadmin@mastest.com";// username@domain
		String adminPassword = "1234@abcd";// password
//		String ldapURL = "LDAP://10.0.0.2:389";// ip:port
		
		String ldapURL = "LDAP://10.3.70.1:389";
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");// "none","simple","strong"
		env.put(Context.SECURITY_PRINCIPAL, adminName);
		env.put(Context.SECURITY_CREDENTIALS, adminPassword);
		env.put(Context.PROVIDER_URL, ldapURL);
		
		StringBuffer strb = new StringBuffer();
		
		try {
			LdapContext context = new InitialLdapContext(env, null);
			// 基于baseDN查询
//			String baseDN = "DC=szaic,DC=gov,DC=cn";
			String baseDN = "DC=msatest,DC=com";
//			String n= "020 认证监督管理处";
			String startDN = "OU="+n+",OU=用户,DC=msatest,DC=com";
			String objectCategory = "objectCategory=cn=Person,cn=Schema,cn=Configuration,"
					+ baseDN;
			String filter = "(objectClass=top)";
			filter = "(&(objectClass=person)" + filter + ")";
			filter = "(&(objectClass=user)" + filter + ")";
			filter = "(&(objectClass=organizationalPerson)" + filter + ")";
			filter = "(&(" + objectCategory + ")" + filter + ")";
			// 实例化一个搜索器
			SearchControls cons = new SearchControls();
			// 搜索范围： 1、平级检索；2、树形检索
			cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
			// 设置为false时返回结果占用内存减少
			cons.setReturningObjFlag(true);
			// 执行查询
			NamingEnumeration<SearchResult> sEnum = context.search(startDN,
					filter, cons);
			int size = 0;
			
//			StringBuffer strsql = new StringBuffer();
			while (sEnum.hasMoreElements()) {
				strb.append("insert into TT_USER_OLD_DN (p_name,user_id,old_ou_str) values (");
				
				
				SearchResult sr = sEnum.nextElement();
				String DN = sr.getName();
				Attributes attrs = sr.getAttributes();
				// 取到所有属性'
				NamingEnumeration<? extends Attribute> aEnum = attrs.getAll();
				while (aEnum.hasMoreElements()) {
					Attribute attr = aEnum.nextElement();
					if (attr == null) {
						continue;
					}
					// 打印属性名和属性值，属性值可以为多个
					if ("displayName".equals(attr.getID())) {
						for (int i = 0; i < attr.size(); i++) {
							Object obj = attr.get(i);
//							strb.append("displayName = ");
							strb.append("'");
							strb.append(obj);
							strb.append("'");
							strb.append(",");
//							strb.append("\n");
						}
					}
					
					if ("sAMAccountName".equals(attr.getID())) {
						for (int i = 0; i < attr.size(); i++) {
							Object obj = attr.get(i);
//							strb.append("sAMAccountName = ");
							strb.append("'");
							strb.append(obj.toString().toUpperCase());
							strb.append("'");
							strb.append(",");
//							strb.append("\n");
						}
					}

				}
				size++;
//				strb.append("OLD_DN: ");
				strb.append("'");
				strb.append(DN);
				strb.append(",");
				strb.append(startDN);
				strb.append("'");
				strb.append(");");
				strb.append("\n");
			}
			context.close();
			// System.out.println("共返回： " + size);
//			StringBuffer strall = new StringBuffer();
//			strall.append("共返回： " + size);
//			strall.append("\n");
//			strall.append(strb);
//			strb.append("commit;");
//			strb.append("\n");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}finally{
			return strb;
		}
	}
	
	
	

}