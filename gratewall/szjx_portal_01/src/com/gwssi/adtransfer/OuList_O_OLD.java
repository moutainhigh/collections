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
/**
 * 查出“用户”下的所有OU
 * @author chaihw
 *
 */
public class OuList_O_OLD {

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

		try {
			LdapContext context = new InitialLdapContext(env, null);
			// 基于baseDN查询

			String baseDN = "DC=szaic,DC=gov,DC=cn";
			String startDN = "OU=用户,DC=szaic,DC=gov,DC=cn";
			String filter = "(&(objectClass=top)(objectClass=organizationalUnit))";

			// 实例化一个搜索器
			SearchControls cons = new SearchControls();
			// 搜索范围： 1、平级检索；2、树形检索
			cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
			// cons.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			// cons.setSearchScope(SearchControls.OBJECT_SCOPE);
			// 设置为false时返回结果占用内存减少
			cons.setReturningObjFlag(true);
			// 执行查询
			NamingEnumeration<SearchResult> sEnum = context.search(startDN,
					filter, cons);

			int size = 0;
			// StringBuffer strb = new StringBuffer();
			StringBuffer sqlAll = new StringBuffer();
			ArrayList sqlList = new ArrayList();
			while (sEnum.hasMoreElements()) {
				SearchResult sr = sEnum.nextElement();
				String DN = sr.getName();
				StringBuffer sql = new StringBuffer();

				sql.append("INSERT INTO AD_OU_OLD_O (OU_NAME,OU_STR) VALUES (");
				// strb.append("DN: ");
				// strb.append(DN);
				// strb.append("\n");
				Attributes attrs = sr.getAttributes();
				// 取到所有属性
				NamingEnumeration<? extends Attribute> aEnum = attrs.getAll();
				while (aEnum.hasMoreElements()) {
					Attribute attr = aEnum.nextElement();
					if (attr == null) {
						continue;
					}
					// 打印属性名和属性值，属性值可以为多个
					if ("name".equals(attr.getID())) {
						for (int i = 0; i < attr.size(); i++) {
							Object obj = attr.get(i);
							sql.append("'");
							sql.append(obj);
							sql.append("',");
							// strb.append("name = ");
							// strb.append(obj);
							// strb.append("\n");
						}

					}
					if ("distinguishedName".equals(attr.getID())) {
						for (int i = 0; i < attr.size(); i++) {
							Object obj = attr.get(i);
							// strb.append("distinguishedName = ");
							// strb.append(obj);
							// strb.append("\n");
							sql.append("'");
							sql.append(obj);
							sql.append("')");
						}
					}

				}
				sqlList.add(sql.toString());
				sql.append("\n");
				sqlAll.append(sql);
				// size++;
				// strb.append("\n");
			}
			context.close();
			// StringBuffer strall = new StringBuffer();
			// strall.append("共返回： " + size);
			// strall.append("\n");
			// strall.append(strb);
			// System.out.println(strall);
			// FileOperation.contentToTxt("D:\\123.sql",
			// sqlAll.toString());
			// FileOperation.aa("D:\\123.sql",
			// sqlAll.toString());
			conOracle(sqlList);
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {

		}
		// 关闭

	}

	public static void conOracle(List insertList) {
		Connection conn = null;
		Statement stmt = null;
		// ResultSet rs = null;
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
		} finally {
			try {
				// 关闭数据库，结束进程
				// rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}