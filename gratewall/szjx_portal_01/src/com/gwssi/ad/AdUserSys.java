package com.gwssi.ad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

public class AdUserSys {
	public AdUserSys() {
	}

	/**
	 * 
	 * 方法描述:获取AD域所有的用户 并进行分页。如果用户不大于1000个那么可以不用部分，如果大于1000 不分页 只能读取1000个
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 */
	public List<Map<String, String>> getADInfo(String host, String port,
			String adminName, String adminPassword, String ou, String dc,
			String dc_houzui, Map<String, String> usermap) {

		String company = "";
		List<Map<String, String>> li = new ArrayList<Map<String, String>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> namerow = new HashMap<String, String>();

		String url = new String("ldap://" + host + ":" + port);
		Hashtable HashEnv = new Hashtable();
		HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别
		HashEnv.put(Context.SECURITY_PRINCIPAL, adminName); // AD User
		HashEnv.put(Context.SECURITY_CREDENTIALS, adminPassword); // AD Password
		HashEnv.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
		HashEnv.put(Context.PROVIDER_URL, url);
		// HashEnv.put(Context.BATCHSIZE, 2500+"");
		int pageSize = 980; // 每次获取多少条
		int total; // 总共获取的条数
		byte[] cookie = null;
		try {
			LdapContext ctx = new InitialLdapContext(HashEnv, null);
			ctx.setRequestControls(new Control[] { new PagedResultsControl(
					pageSize, Control.CRITICAL) });// 分页读取控制---因为LDAP
													// 默认情况只能读取1000条数据
			// 域节点

			do {
				String OU = ou;
				String searchBase = "DC=" + dc + ",DC=" + dc_houzui;
				if (OU.length() > 0) {
					searchBase = "OU=" + OU + ",DC=" + dc + ",DC=" + dc_houzui;
				}
				// LDAP搜索过滤器类
				// (&(|(objectclass=user)(objectclass=person)(objectclass=inetOrgPerson)(objectclass=organizationalPerson)))
				String searchFilter = "(&(objectclass=user)(sAMAccountName=*))";// 获取帐号
				// 搜索控制器
				SearchControls searchCtls = new SearchControls(); // Create the
				// 创建搜索控制器
				searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

				/*
				 * 1 姓(L) sn 2 名(F) givenName 3 显示名称(S) displayName 4 描述(D)
				 * description 5 办公室(C) physicalDeliveryOfficeName 6 英文缩写(I)
				 * initials 7 电话号码(T) telephoneNumber 8 电子邮件(M) mail 9 网页(W)
				 * wWWHomePage 10 电话号码-其它(O)... otherTelephone 11 网页-其它(R)...
				 * url ----- 1 国家/地区(O) co 2 省/自治区(V) st 3 市/县(C) l 4街道(S)
				 * streetAddress 5 邮政信箱(B) postOfficeBox 6 邮政编码(Z) postalCode
				 * --------- 1 用户登录名(U) userPrincipalName 2 用户登录名(Windows 2000
				 * 以前版本)(W) sAMAccountName ------------ 1 家庭电话(M) homePhone 2
				 * 寻呼机(P) pager 3 移动电话(B) mobile 4 传真(F)
				 * facsimileTelephoneNumber 5 IP电话(I) ipPhone 6 注释 info 7
				 * 家庭电话-其它(O) otherHomePhone 8 寻呼机-其它(T) otherPager 9 移动电话-其它(B)
				 * otherMobile 10 传真-其它(E) otherFacsimileTelephoneNumber 11
				 * IP电话-其它(R) otherIpPhone ---------- 1 公司(C) company 2 部门(D)
				 * department 3 职务(J) title 4 经理-姓名(N) manager 5 直接下属(E)
				 * directReports ---------
				 */
				String[] returnedAtts = { "uSNCreated", "name",
						"userPrincipalName" };// 定制返回属性
				searchCtls.setReturningAttributes(returnedAtts); // 设置返回属性集
				// System.out.println(searchCtls.);
				// 根据设置的域节点、过滤器类和搜索控制器搜索LDAP得到结果
				int totalResults = 0;
				int rows = 0;

				NamingEnumeration answer = ctx.search(searchBase, searchFilter,
						searchCtls);
				// 初始化搜索结果数为0
				while (null != answer && answer.hasMoreElements()) {// 遍历结果集
					SearchResult sr = (SearchResult) answer.next();// 得到符合搜索条件的DN
					String dn = sr.getName();
					Attributes Attrs = sr.getAttributes();// 得到符合条件的属性集
					if (Attrs != null) {
						Map<String, String> row = new HashMap<String, String>();
						for (NamingEnumeration ne = Attrs.getAll(); ne
								.hasMore();) {
							Attribute Attr = (Attribute) ne.next();// 得到下一个属性
							// 读取属性值
							for (NamingEnumeration e = Attr.getAll(); e
									.hasMore(); totalResults++) {
								company = e.next().toString();
							}
							row.put(Attr.getID().toString(), company);
						}
						row.put("userdn", dn);
						// System.out.println(row.toString());
						li.add(row);
					}
				}
				Control[] controls = ctx.getResponseControls();
				if (controls != null) {
					for (int i = 0; i < controls.length; i++) {
						if (controls[i] instanceof PagedResultsResponseControl) {
							PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
							total = prrc.getResultSize();
							cookie = prrc.getCookie();
						} else {
						}
					}
				}

				ctx.setRequestControls(new Control[] { new PagedResultsControl(
						pageSize, cookie, Control.CRITICAL) });
			} while (cookie != null);
			ctx.close();
			System.out.println("总共:" + li.size() + "条信息.");
			for (int i = 0; i < li.size(); i++) {
				Map userrow = li.get(i);
				// String userdn=DbMapUtil.getValue(userrow, "userdn", "");
				// //如果DN等于空那么默认为顶节点
				// String name=DbMapUtil.getValue(userrow, "name", "");
				// //如果DN等于空那么默认为顶节点
				String userdn = userrow.get("userdn").toString();
				String name = userrow.get("name").toString();

				if (userdn.length() > 0) {
					String userdns = userdn.replace("CN=" + name, "");
					if (userdns.trim().length() == 0) {
						userrow.put("orgid", "-1000");
					} else {
						userdns = userdn.replace("CN=" + name + ",", "");
						if (usermap != null) {
							userrow.put("orgid", usermap.get(userdns));
						}
					}
					// System.out.println(userrow.toString());
					list.add(userrow);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public static void main(String args[]) {
		// 实例化
		AdUserSys ad = new AdUserSys();
		Map<String, String> usermap = new HashMap<String, String>();
		usermap.put("OU=Domain Controllers", "5828");
		usermap.put("OU=行政部", "12844");
		usermap.put("OU=人力部,OU=行政部", "53324");
		usermap.put("OU=管理部,OU=行政部", "12847");
		usermap.put("OU=杭州慧智电子科技", "53308");
		ad.getADInfo("192.168.0.14", "389", "shejiguanli\\administrator",
				"abc123!", "", "shejiguanli", "com", usermap);
		// ad.getUnit("192.168.0.14","389","shejiguanli\\administrator","abc123!","","shejiguanli","com");

	}
}
