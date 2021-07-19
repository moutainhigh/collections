package com.gwssi.adtransfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

/*
 * 查出“用户”组织机构下的user
 */
public class UserList_O_OLD {

	public static void main(String[] args) {

		Properties env = new Properties();
		String adminName = "changruan@szaic.gov.cn";
		String adminPassword = "1234@abcd";
		String ldapURL = "LDAP://10.0.0.2:389";
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, adminName);
		env.put(Context.SECURITY_CREDENTIALS, adminPassword);
		env.put(Context.PROVIDER_URL, ldapURL);

		queryUser(env);

	}

	public static void queryUser(Properties env) {
		try {
			LdapContext context = new InitialLdapContext(env, null);
			// 基于baseDN查询
			String baseDN = "DC=szaic,DC=gov,DC=cn";
			String startDN = "OU=用户,DC=szaic,DC=gov,DC=cn";
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
			// OBJECT_SCOPE
			// 设置为false时返回结果占用内存减少
			cons.setReturningObjFlag(true);
			// 执行查询
			NamingEnumeration<SearchResult> sEnum = context.search(startDN,
					filter, cons);
			int size = 0;
			ArrayList sqlList = new ArrayList();
			StringBuffer strsql = new StringBuffer();
			while (sEnum.hasMoreElements()) {
				StringBuffer strb = new StringBuffer();
				strb.append("insert into AD_USER_OLD_O_1 (USER_NAME,USER_ID,USER_STR) values (");
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
							// strb.append("displayName = ");
							strb.append("'");
							strb.append(obj);
							strb.append("'");
							strb.append(",");
							// strb.append("\n");
						}
					}
					if ("sAMAccountName".equals(attr.getID())) {
						for (int i = 0; i < attr.size(); i++) {
							Object obj = attr.get(i);
							// strb.append("sAMAccountName = ");
							strb.append("'");
							strb.append(obj.toString().toUpperCase());
							strb.append("'");
							strb.append(",");
							// strb.append("\n");
						}
					}

				}
				size++;
				// strb.append("OLD_DN: ");
				strb.append("'");
				strb.append(DN);
				strb.append(",");
				strb.append(startDN);
				strb.append("'");
//				strb.append(")");

				 strb.append(");");
				 strb.append("\n");
				 strsql.append(strb);
//				sqlList.add(strb);
			}
			System.out.println(size + "个");
//			context.close();
			// System.out.println("共返回： " + size);
			// StringBuffer strall = new StringBuffer();
			// strall.append("共返回： " + size);
			// strall.append("\n");
			// strall.append(strb);
			// strb.append("commit;");
			// strb.append("\n");
//			conOracle(sqlList);
			 FileOperation.aa("D:\\wwwwwwww.sql", strsql.toString());
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	public static void conOracle(List insertList) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@10.3.70.134:1521:oragxk";
			conn = DriverManager.getConnection(url, "cjk", "cjk");
			stmt = conn.createStatement();
			for (int i = 0; i < insertList.size(); i++) {
				stmt.executeUpdate(insertList.get(i).toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
