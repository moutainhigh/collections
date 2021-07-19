package com.gwssi.ad;

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

public class OuList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties env = new Properties();
		String adminName = "changruan@szaic.gov.cn";// username@domain
		String adminPassword = "1234@abcd";// password
		String ldapURL = "LDAP://10.0.0.2:389";// ip:port
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");// "none","simple","strong"
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
			// 设置为false时返回结果占用内存减少
			cons.setReturningObjFlag(true);
			// 执行查询
			NamingEnumeration<SearchResult> sEnum = context.search(startDN,
					filter, cons);

			int size = 0;
			StringBuffer strb = new StringBuffer();
			while (sEnum.hasMoreElements()) {
				SearchResult sr = sEnum.nextElement();
				String DN = sr.getName();
				System.out.println("DN: " + DN);
				strb.append("DN: ");
				strb.append(DN);
				strb.append("\n");
				Attributes attrs = sr.getAttributes();
				// 取到所有属性
				NamingEnumeration<? extends Attribute> aEnum = attrs.getAll();
				while (aEnum.hasMoreElements()) {
					Attribute attr = aEnum.nextElement();
					if (attr == null) {
						continue;
					}
					// 打印属性名和属性值，属性值可以为多个
					// System.out.print("id = " + attr.getID() + ", value = ");
					if ("distinguishedName".equals(attr.getID())) {
						for (int i = 0; i < attr.size(); i++) {
							Object obj = attr.get(i);
							// System.out.print(obj + " ");
							strb.append("distinguishedName = ");
							strb.append(obj);
							strb.append("\n");
						}
					}

				}
				size++;
				strb.append("~~~~~");
				strb.append("\n");
			}
			context.close();
			StringBuffer strall = new StringBuffer();
			strall.append("共返回： " + size);
			strall.append("\n");
			strall.append(strb);
			FileOperation.contentToTxt("D:\\组织机构----用户.txt", strall.toString());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		// 关闭

	}

}