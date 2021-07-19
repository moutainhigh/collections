package com.gwssi.ad;

import java.util.Hashtable;
import javax.naming.ldap.*;
import javax.naming.directory.*;
import javax.naming.*;
import javax.net.ssl.*;
import java.io.*;

public class NewUser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Hashtable env = new Hashtable();
		String adminName = "testadmin@msatest.com";
		String adminPassword = "1234@abcd";
		String userName = "CN=zhangsan,OU=用户（测试）,DC=msatest,DC=com";
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, adminName);
		env.put(Context.SECURITY_CREDENTIALS, adminPassword);
		env.put(Context.PROVIDER_URL, "ldap://10.3.70.1:389");
		try {
			LdapContext ctx = new InitialLdapContext(env, null);
			Attributes attrs = new BasicAttributes(true);
			attrs.put("objectClass", "user");
//			attrs.put("giveName", "三");
//			attrs.put("sn", "张");
//			attrs.put("samAccountName", "zhangsan");
//			attrs.put("cn", "zhangsan");
			attrs.put("displayName", "张三");
			attrs.put("userPrincipalName", "zhangsan@msatest.com");

			int UF_ACCOUNTDISABLE = 0x0002;
			int UF_PASSWD_NOTREQD = 0x0020;
			int UF_PASSWD_CANT_CHANGE = 0x0040;
			int UF_NORMAL_ACCOUNT = 0x0200;
			int UF_DONT_EXPIRE_PASSWD = 0x10000;
			int UF_PASSWORD_EXPIRED = 0x800000;

			attrs.put(
					"userAccountControl",
					Integer.toString(UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD
							+ UF_PASSWORD_EXPIRED + UF_ACCOUNTDISABLE));

			Context result = ctx.createSubcontext(userName, attrs);
			System.out.println("Created disabled account for: " + userName);



			// now add the user to a group.

			// try{
			// ModificationItem member[] = new ModificationItem[1];
			// member[0]= new ModificationItem(DirContext.ADD_ATTRIBUTE, new
			// BasicAttribute("member", userName));
			//
			// ctx.modifyAttributes(groupName,member);
			// System.out.println("Added user to group: " + groupName);
			// }catch (NamingException e) {
			// System.err.println("Problem adding user to group: " + e);
			// }
			// Could have put tls.close() prior to the group modification
			// but it seems to screw up the connection or context ?
//			tls.close();
			ctx.close();

			System.out.println("Successfully created User: " + userName);

		} catch (NamingException e) {
			System.err.println("Problem creating object: " + e);
		}
	}
}