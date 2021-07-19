package com.gwssi.adtransfer;

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

import com.gwssi.ad.FileOperation;

public class OuList_test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		Ldapbyuserinfo("cemy");
		
//		Properties env = new Properties();
//		String adminName = "testadmin@msatest.com";
//		String adminPassword = "1234@abcd";
//		String ldapURL = "ldap://10.3.70.1:636";
//		env.put(Context.INITIAL_CONTEXT_FACTORY,
//				"com.sun.jndi.ldap.LdapCtxFactory");
//		env.put(Context.SECURITY_AUTHENTICATION, "simple");
//		env.put(Context.SECURITY_PRINCIPAL, adminName);
//		env.put(Context.SECURITY_CREDENTIALS, adminPassword);
//		env.put(Context.PROVIDER_URL, ldapURL);
//		try {
//			LdapContext context = new InitialLdapContext(env, null);
//
//			// 基于baseDN查询
//			String baseDN = "DC=msatest,DC=com";
//
//			String startDN = "OU=用户（测试）,DC=msatest,DC=com";
//
//			String filter = "(&(objectClass=top)(objectClass=organizationalUnit))";
//
//			// 实例化一个搜索器
//			SearchControls cons = new SearchControls();
//			// 搜索范围： 1、平级检索；2、树形检索
//			cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
//			// 设置为false时返回结果占用内存减少
//			cons.setReturningObjFlag(true);
//			// 执行查询
//			NamingEnumeration<SearchResult> sEnum = context.search(startDN,
//					filter, cons);
//
//			int size = 0;
//			StringBuffer strb = new StringBuffer();
//			while (sEnum.hasMoreElements()) {
//				SearchResult sr = sEnum.nextElement();
//				String DN = sr.getName();
//				System.out.println("DN: " + DN);
//				strb.append("DN: ");
//				strb.append(DN);
//				strb.append("\n");
//				Attributes attrs = sr.getAttributes();
//				// 取到所有属性
//				NamingEnumeration<? extends Attribute> aEnum = attrs.getAll();
//				while (aEnum.hasMoreElements()) {
//					Attribute attr = aEnum.nextElement();
//					if (attr == null) {
//						continue;
//					}
//					// 打印属性名和属性值，属性值可以为多个
//					// System.out.print("id = " + attr.getID() + ", value = ");
//					if ("distinguishedName".equals(attr.getID())) {
//						for (int i = 0; i < attr.size(); i++) {
//							Object obj = attr.get(i);
//							// System.out.print(obj + " ");
//							strb.append("distinguishedName = ");
//							strb.append(obj);
//							strb.append("\n");
//						}
//					}
//
//				}
//				size++;
//				strb.append("~~~~~");
//				strb.append("\n");
//			}
//			context.close();
//			StringBuffer strall = new StringBuffer();
//			strall.append("共返回： " + size);
//			strall.append("\n");
//			strall.append(strb);
//			System.out.println(strall);
//			// FileOperation.contentToTxt("D:\\组织机构----用户.txt",
//			// strall.toString());
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//		// 关闭

	}

	public static void Ldapbyuserinfo(String userName) {

		Properties env = new Properties();
		String adminName = "testadmin@msatest.com";
		String adminPassword = "1234@abcd";
		String ldapURL = "ldap://10.3.70.1:389";
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, adminName);
		env.put(Context.SECURITY_CREDENTIALS, adminPassword);
		env.put(Context.PROVIDER_URL, ldapURL);

		// Create the search controls
		SearchControls searchCtls = new SearchControls();
		// Specify the search scope
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		// specify the LDAP search filter
		String searchFilter = "sAMAccountName=" + userName+"*";
		// Specify the Base for the search 搜索域节点
		String searchBase = "DC=msatest,DC=COM";
		int totalResults = 0;
		String returnedAtts[] = { "url", "whenChanged", "employeeID", "name",
				"userPrincipalName", "physicalDeliveryOfficeName",
				"departmentNumber", "telephoneNumber", "homePhone", "mobile",
				"department", "sAMAccountName", "whenChanged", "mail" }; // 定制返回属性

		searchCtls.setReturningAttributes(returnedAtts); // 设置返回属性集

		// searchCtls.setReturningAttributes(null); // 不定制属性，将返回所有的属性集

		try {
			LdapContext context = new InitialLdapContext(env, null);

			NamingEnumeration answer = context.search(searchBase, searchFilter,
					searchCtls);
			if (answer == null || answer.equals(null)) {
				System.out.println("answer is null");
			} else {
				System.out.println("answer not null");
			}
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				System.out
						.println("************************************************");
				System.out.println("getname=" + sr.getName());
				Attributes Attrs = sr.getAttributes();
				if (Attrs != null) {
					try {

						for (NamingEnumeration ne = Attrs.getAll(); ne
								.hasMore();) {
							Attribute Attr = (Attribute) ne.next();
							System.out.println("AttributeID="
									+ Attr.getID().toString());
							// 读取属性值
							for (NamingEnumeration e = Attr.getAll(); e
									.hasMore(); totalResults++) {
								String user = e.next().toString(); // 接受循环遍历读取的userPrincipalName用户属性
								System.out.println(user);
							}
							// System.out.println(" ---------------");
							// // 读取属性值
							// Enumeration values = Attr.getAll();
							// if (values != null) { // 迭代
							// while (values.hasMoreElements()) {
							// System.out.println(" 2AttributeValues="
							// + values.nextElement());
							// }
							// }
							// System.out.println(" ---------------");
						}
					} catch (NamingException e) {
						System.err.println("Throw Exception : " + e);
					}
				}
			}
			context.close();
			System.out.println("Number: " + totalResults);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Throw Exception : " + e);
		} finally{
			
		}
	}

}