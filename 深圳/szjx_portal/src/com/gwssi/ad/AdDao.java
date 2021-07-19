package com.gwssi.ad;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gwssi.optimus.core.common.ConfigManager;

/**
 * 操作windows AD域类
 * 
 * @author Leezen
 */
public class AdDao {

	private static Logger logger = Logger.getLogger(AdDao.class);

	// LdapContext ctx = null;

	// 登录脚本标志。如果通过 ADSI LDAP 进行读或写操作时，该标志失效。如果通过 ADSI WINNT，该标志为只读。
	int UF_SCRIPT = 0X0001;

	// 主文件夹标志
	int UF_HOMEDIR_REQUIRED = 0X0008;

	// 过期标志
	int UF_LOCKOUT = 0X0010;

	// 本地帐号标志
	int UF_TEMP_DUPLICATE_ACCOUNT = 0X0100;

	// 普通用户的默认帐号类型
	int UF_NORMAL_ACCOUNT = 0X0200;

	// 跨域的信任帐号标志
	int UF_INTERDOMAIN_TRUST_ACCOUNT = 0X0800;

	// 工作站信任帐号标志
	int UF_WORKSTATION_TRUST_ACCOUNT = 0x1000;

	// 服务器信任帐号标志
	int UF_SERVER_TRUST_ACCOUNT = 0X2000;

	// 用户密码不是必须的
	int UF_PASSWD_NOTREQD = 0X0020;

	// 密码不能更改标志
	int UF_PASSWD_CANT_CHANGE = 0X0040;

	// 密码永不过期标志
	int UF_DONT_EXPIRE_PASSWD = 0X10000;

	// 使用可逆的加密保存密码
	int UF_ENCRYPTED_TEXT_PASSWORD_ALLOWED = 0X0080;

	// 用户帐号禁用标志
	int UF_ACCOUNTDISABLE = 0X0002;

	// 交互式登录必须使用智能卡
	int UF_SMARTCARD_REQUIRED = 0X40000;

	// 用户帐号可委托标志
	int UF_TRUSTED_TO_AUTHENTICATE_FOR_DELEGATION = 0X1000000;

	// 当设置该标志时，即使服务帐号是通过 Kerberos 委托信任的，敏感帐号不能被委托
	int UF_NOT_DELEGATED = 0X100000;

	// 此帐号需要 DES 加密类型
	int UF_USE_DES_KEY_ONLY = 0X200000;

	// 当设置该标志时，服务帐号（用户或计算机帐号）将通过 Kerberos 委托信任
	int UF_TRUSTED_FOR_DELEGATION = 0X80000;

	// 不要进行 Kerberos 预身份验证
	int UF_DONT_REQUIRE_PREAUTH = 0X4000000;

	// MNS 帐号标志
	int UF_MNS_LOGON_ACCOUNT = 0X20000;

	// 使用AES加密
	int UF_USE_AES_KEYS = 0x8000000;

	// 用户密码过期标志
	int UF_PASSWORD_EXPIRED = 0X800000;

	/**
	 * 连接AD域（供查询用）
	 * 
	 * @return 连接状态
	 */
	public LdapContext conAd389() {

		logger.info("进入连接AD域方法");

		// 读出配置文件里的AD域超级管理员的用户名和密码
		Properties properties = ConfigManager.getProperties("ad");
		String adminName = properties.getProperty("ad.admin.name"); // 用户名
		String adminPassword = properties.getProperty("ad.admin.pwd"); // 密码

		// 读出配置文件里的AD域的连接路径
		String ldapUrl = properties.getProperty("ad.ldap.url.389"); // 连接路径

		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory"); // 设置连接
		env.put(Context.SECURITY_AUTHENTICATION, "simple"); // 使用用户名和密码认证
		env.put(Context.SECURITY_PRINCIPAL, adminName);
		env.put(Context.SECURITY_CREDENTIALS, adminPassword);
		env.put(Context.PROVIDER_URL, ldapUrl);
		env.put("com.sun.jndi.ldap.connect.pool", "true");
		env.put("java.naming.referral", "follow");

		logger.info("连接对象env为:  " + env);
		LdapContext ctx = null;
		try {
			ctx = new InitialLdapContext(env, null);
			logger.info("连接AD域成功！");
		} catch (NamingException e) {
			try {
				ctx.close();
				logger.debug("连接AD域 失败！");
				e.printStackTrace();
				logger.debug("连接AD域 失败原因===> "+e.getMessage());
			} catch (NamingException e1) {
				e1.printStackTrace();
				logger.debug("连接AD域 失败原因===> "+e1.getMessage());
			}
			
		}
		return ctx;
	}

	/**
	 * 连接AD域（供查编辑用）
	 * 
	 * 636端口慎用，需要用ssl协议一旦没有关闭连接系统会报错socket close
	 * 
	 * @return 连接状态
	 */
	public LdapContext conAd636() {

		logger.info("进入连接AD域方法");

		Properties properties = ConfigManager.getProperties("ad");
		String adminName = properties.getProperty("ad.admin.name");
		String adminPassword = properties.getProperty("ad.admin.pwd");

		// 读出配置文件里的 ssl证书在jdk下的路径
		String keystore = properties.getProperty("keystore");

		// 读出配置文件里的AD域的连接路径
		String ldapUrl = properties.getProperty("ad.ldap.url.636");

		// 设置系统使用ssl协议
		System.setProperty("javax.net.ssl.trustStore", keystore);

		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory"); // 设置连接
		env.put(Context.SECURITY_AUTHENTICATION, "simple"); // 使用用户名和密码认证
		env.put(Context.SECURITY_PRINCIPAL, adminName);
		env.put(Context.SECURITY_CREDENTIALS, adminPassword);
		env.put(Context.PROVIDER_URL, ldapUrl);
		env.put(Context.SECURITY_PROTOCOL, "ssl");
		env.put("com.sun.jndi.ldap.connect.pool", "true");
		env.put("java.naming.referral", "follow");

		logger.info("连接对象env为:  " + env);
		LdapContext ctx = null;
		try {
			ctx = new InitialLdapContext(env, null);
			logger.info("连接AD域成功！");
		} catch (NamingException e) {
			try {
				ctx.close();
			} catch (NamingException e1) {
				logger.info("AD域关闭失败" + e1.getMessage());
				e1.printStackTrace();
			}
			logger.debug("连接AD域 失败！");
			e.printStackTrace();
		} finally {
			if(ctx!=null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					logger.info("AD域关闭失败" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return ctx;
	}

	/**
	 * 关闭AD域
	 */
	public void closeAd(LdapContext ctx) {
		try {
			ctx.close();
			logger.info("关闭AD域连接成功！");
		} catch (NamingException e) {
			e.printStackTrace();
			logger.debug("关闭AD域连接失败！");
		}
	}

	/**
	 * 测试ad域连接
	 * 
	 * @return String
	 */
	public String testCon() {
		String str389 = "";
		LdapContext ctx = conAd389();
		if (null != ctx) {
			closeAd(ctx);
			str389 = "连接AD域389端口成功";
		} else {
			str389 = "连接AD域389端口失败";
		}
		String str636 = "";
		LdapContext ctx636 = conAd636();
		if (null != ctx636) {
			closeAd(ctx636);
			str636 = "连接AD域636端口成功";
		} else {
			str636 = "连接AD域636端口失败";
		}
		return str389 + str636;
	}

	/**
	 * 创建用户
	 * 
	 * @param person
	 * @return 创建用户状态
	 */
	public Map createOnePerson(Person person) {
		// 返回值
		Map resMap = new HashMap();
		Properties properties = ConfigManager.getProperties("ad");
		String res = properties.getProperty("create.user.success"); // 成功状态返回代码

		BasicAttribute ocattr = new BasicAttribute("objectClass");
		ocattr.add("top");
		ocattr.add("person");
		ocattr.add("organizationalPerson");
		ocattr.add("user");

		Attributes attrs = new BasicAttributes(true);
		attrs.put(ocattr);
		attrs.put("sn", person.getSn()); // 姓
		attrs.put("givenName", person.getGiveName()); // 名
		attrs.put("cn", person.getCn()); // 面板显示名称
		attrs.put("displayName", person.getDisplayName()); // 显示姓名

		String samAccountName = "";

		// 判断用户的组织单位是否存在，如不存在创建组织单位
		boolean isOuExist = checkPersonOu(person.getOu());
		logger.info("用户组织是否存在：" + isOuExist);

		if (isOuExist) {
			// 判断用户登录ID是否存在重复，如果重复按照规则在后面依次加上数字
			samAccountName = checkPersonName(person.getsAMAccountName());
			logger.info("samAccountName名称为：" + samAccountName);

			attrs.put("samAccountName", samAccountName);
			attrs.put("userAccountControl",	Integer.toString(UF_NORMAL_ACCOUNT + UF_ACCOUNTDISABLE));
			attrs.put("userPrincipalName",samAccountName + properties.getProperty("ad.dn.com"));
			// 建立636端口连接
			LdapContext ctx = conAd636();
			try {
				// 创建用户
				ctx.createSubcontext(person.getDistinguishedName(), attrs);
				// 为用户设置默认密码
				ModificationItem[] mods = new ModificationItem[3];
				String newQuotedPassword = "\""	+ properties.getProperty("default.pwd") + "\"";
				byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("unicodePwd", newUnicodePassword));
				mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("userAccountControl",Integer.toString(UF_NORMAL_ACCOUNT+ UF_PASSWORD_EXPIRED)));
				// 设置用户第一次登录必须修改密码
				mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("pwdLastSet", "0"));
				ctx.modifyAttributes(person.getDistinguishedName(), mods);

				resMap.put("USER_NAME", samAccountName);
				resMap.put("PASSWORD", properties.getProperty("default.pwd"));
				logger.info("创建用户     " + person.getDistinguishedName()+ "  成功！");
			} catch (NamingException e) {
				res = properties.getProperty("create.user.fail"); // 失败状态返回代码
				e.printStackTrace();
				logger.info("创建用户     " + person.getDistinguishedName()+ "  失败！");
				logger.info(samAccountName + "创建用户失败原因  ===>  " +e.getMessage());
				logger.info(Integer.toString(UF_NORMAL_ACCOUNT + UF_ACCOUNTDISABLE) + "userAccountControl原因  ===>  " +e.getMessage());
				logger.info(samAccountName + properties.getProperty("ad.dn.com") + "userPrincipalName 原因  ===>  " +e.getMessage());
			} catch (UnsupportedEncodingException e) {
				logger.info(samAccountName + "创建用户失败原因  ===>  " +e.getMessage());
				e.printStackTrace();
			} finally {
				// 关闭连接
				resMap.put("RETURN_CODE", res);
				closeAd(ctx);
			}
			return resMap;
		} else {
			res = properties.getProperty("user.ou.noexist"); // 失败状态返回代码
			logger.info("创建用户     " + person.getDistinguishedName()+ "  失败，因为用户所属组织机构不存在！");
			resMap.put("RETURN_CODE", res);
			return resMap;
		}
	}

	/**
	 * 创建用户时先查看用户所属机构是否存在
	 * 
	 * @param person
	 */
	private boolean checkPersonOu(String ouStr) {
		logger.info("用户的组织单位为：" + ouStr);
		boolean isExist = false;
		String startDN = ouStr;
		String filter = "(&(objectClass=top)(objectClass=organizationalUnit))";
		// 实例化一个搜索器
		SearchControls cons = new SearchControls();
		// 搜索范围： 树形检索
		cons.setSearchScope(SearchControls.OBJECT_SCOPE);
		// 设置为false时返回结果占用内存减少
		cons.setReturningObjFlag(true);
		// 定制返回属性
		String returnedAtts[] = { "name", "ou", "distinguishedName" };
		cons.setReturningAttributes(returnedAtts);
		LdapContext ctx = this.conAd389();
		// 执行查询
		try {
			NamingEnumeration<SearchResult> sEnum = ctx.search(startDN, filter,
					cons);
			while (sEnum.hasMoreElements()) {
				SearchResult sr = sEnum.nextElement();
				Attributes attrs = sr.getAttributes();

				NamingEnumeration<? extends Attribute> aEnum = attrs.getAll(); // 取到所有属性
				while (aEnum.hasMoreElements()) {
					Attribute attr = aEnum.nextElement();
					if (attr == null) {
						continue;
					}
					if ("distinguishedName".equals(attr.getID())) {
						String name = (String) attr.get(0);
						if (ouStr.equals(name)) {
							
							isExist = true;
						}
					}
				}
			}
		} catch (NamingException e) {
		//	e.printStackTrace();
			logger.info("AD域查询初始搜索器化出错：该"+ouStr+"   组织不存在");
		} finally {
			closeAd(ctx);
		}
		return isExist;
	}

	/**
	 * 检查用户重复
	 * 
	 * @param String登录ID
	 */
	private String checkPersonName(String sAMAccountName) {

		// 实例化一个搜索器
		SearchControls searchCtls = new SearchControls();
		// 搜索范围： 树形检索
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		// 设置为false时返回结果占用内存减少
		searchCtls.setReturningObjFlag(true);
		// 设置查询过滤器
		String searchFilter = "(sAMAccountName=" + sAMAccountName + "*)";
		// String searchFilter = "(&(objectClass=user)(sAMAccountName=CeM))";

		// 设置搜索域节点
		Properties properties = ConfigManager.getProperties("ad");
		String searchBase = properties.getProperty("ad.dn.base");
		// 定制返回属性
		String returnedAtts[] = { "sAMAccountName" };
		searchCtls.setReturningAttributes(returnedAtts);

		LdapContext ctx = this.conAd389();
		List userNameList = new ArrayList();
		int size = 0;
		String res = "";
		// 执行查询
		try {

			NamingEnumeration<SearchResult> sEnum = ctx.search(searchBase,
					searchFilter, searchCtls);
			if (sEnum == null || sEnum.equals(null)) {
				logger.info("没有重ID的用户");
				res = sAMAccountName;
			} else {
//				logger.info("有重ID的用户,重id的用户分别是");
				while (sEnum.hasMoreElements()) {
					SearchResult sr = sEnum.nextElement();
					Attributes attrs = sr.getAttributes();

					NamingEnumeration<? extends Attribute> aEnum = attrs
							.getAll(); // 取到所有属性
					while (aEnum.hasMoreElements()) {
						Attribute attr = aEnum.nextElement();
						if (attr == null) {
							continue;
						}
						if ("sAMAccountName".equals(attr.getID())) {
							String name = (String) attr.get(0);
							userNameList.add(name.toLowerCase());
							//logger.info(name);
							size++;
						}
					}
				}
			}
			res = getName(userNameList, sAMAccountName);
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			closeAd(ctx);
		}
		return res;
	}
	/**
	 * 具体创建用户名
	 * @author chaihw
	 * @param nameList
	 * @param name
	 * @return
	 */
	public static String getName(List<String> nameList, String name) {
		String sAMAccountName="";
		int nameright=0;
		if(nameList!=null&&nameList.size()>0){
		
			for(String existname:nameList){
				if(StringUtils.equals(existname, name)){
					nameright=nameright+1;
					logger.info(existname);
				}else{
//					System.out.println(existname+"处理后字符串"+existname.substring(name.length()));
					
					boolean isnumber = isNumeric(existname.substring(name.length()));
//					System.out.println("是否为数字："+isnumber);
					if(isnumber){
						logger.info(existname);
						nameright=nameright+1;
					}
				}
			}
		}
		if(nameright>0){
			logger.info("有重ID的用户,以上为重id的用户，共有"+nameright+"重复");
		}else{
			logger.info("没有重复的ID直接创建");
		}
		if(nameright!=0){
			sAMAccountName=name+nameright;
		}else{
			sAMAccountName=name;
		}
		if(nameList.contains(sAMAccountName)){
//			System.out.println("包含");
			while(nameList.contains(sAMAccountName)){
				nameright =nameright+1;
				sAMAccountName =name+nameright;
			}
			return sAMAccountName;
		}else{
//			System.out.println("不包含");
			return sAMAccountName;
		}
	}
	
	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		  for (int i = str.length();--i>=0;){   
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
	}
	/**
	 * 
	 * @param nameList
	 * @param name
	 * @return 用户ID
	 */
/*	public static String getName(List<String> nameList, String name) {
		// 比较器
		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String o1, String o2) {
				// 前面3个IF主要是判空的
				if (o1 == o2) {
					return 0;
				}
				if (o1 == null) {
					return 1;
				}
				if (o2 == null) {
					return -1;
				}
				// 这里没有做太多的判断, index 代表第几个开始是数字, 直接从后面遍历
				// 比如 aa11, 我们就会判断从下标[2]开始为不是数字, 就直接截取 [2] 后面, 即11
				int length1 = o1.length() - 1;
				int length2 = o2.length() - 1;
				int index = 0;
				int num1 = 0;
				int num2 = 0;
				for (index = length1; index >= 0
						&& (o1.charAt(index) >= '0' && o1.charAt(index) <= '9'); index--)
					;
				if (index < length1) {
					num1 = Integer.parseInt(o1.substring(index + 1));
				} else {
					num1 = 0;
				}

				for (index = length2; index >= 0
						&& (o2.charAt(index) >= '0' && o2.charAt(index) <= '9'); index--)
					;
				if (index < length2) {
					num2 = Integer.parseInt(o2.substring(index + 1));
				} else {
					num2 = 0;
				}
				return num1 - num2;
			}
		};

		if (nameList.size() > 0) {
			Collections.sort(nameList, comparator);
			String maxName = nameList.get(nameList.size() - 1);
			int index = 0;
			int length = maxName.length() - 1;
			int num = 0;
			for (index = length; index >= 0
					&& (maxName.charAt(index) >= '0' && maxName.charAt(index) <= '9'); index--)
				;
			if (index < length) {
				num = Integer.parseInt(maxName.substring(index + 1));
			} else {
				num = 0;
			}
			num++;
			return name + num;
		} else {
			return name;
		}
	}
*/
	/**
	 * 禁用用户
	 * 
	 * @param person
	 * @return 禁用用户状态
	 */
	public Map disablePerson(Person person) {

		Map resMap = new HashMap();
		Properties properties = ConfigManager.getProperties("ad");
		String res = properties.getProperty("disable.user.success");

		String userName = person.getDistinguishedName();
		ModificationItem[] mods = new ModificationItem[1];
		mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
				new BasicAttribute("userAccountControl",
						Integer.toString(UF_ACCOUNTDISABLE + UF_NORMAL_ACCOUNT
								+ UF_DONT_EXPIRE_PASSWD)));
		if(userName==null){
			logger.info("禁用用户失败 ，未找到该用户");
			res=properties.getProperty("user.is.noexist");
			resMap.put("RETURN_CODE", res);
			
			return resMap;
		}
		// 建立636端口连接
		LdapContext ctx = conAd636();
		try {
			ctx.modifyAttributes(userName, mods);
			logger.info("禁用用户     " + userName + "  成功！");
		} catch (NamingException e) {
			res = properties.getProperty("disable.user.fail");
			e.printStackTrace();
			logger.info("禁用用户     " + userName + "  失败！");
		} finally {
			resMap.put("RETURN_CODE", res);
			closeAd(ctx);
		}
		return resMap;
	}

	/**
	 * 启用用户
	 * 
	 * @param person
	 * @return 启用用户状态
	 */
	public Map enablePerson(Person person) {
		Map resMap = new HashMap();

		Properties properties = ConfigManager.getProperties("ad");
		String res = properties.getProperty("enable.user.success");

		String userName = person.getDistinguishedName();
		ModificationItem[] mods = new ModificationItem[1];
		mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
				new BasicAttribute("userAccountControl",
						Integer.toString(UF_NORMAL_ACCOUNT)));
		
		if(userName==null){
			logger.info("启用用户失败 ，未找到该用户");
			res=properties.getProperty("user.is.noexist");
			resMap.put("RETURN_CODE", res);
			
			return resMap;
		}
		
		// 建立636端口连接
		LdapContext ctx = conAd636();
		try {
			ctx.modifyAttributes(userName, mods);
			logger.info("启用用户     " + userName + "  成功！");
		} catch (NamingException e) {
			res = properties.getProperty("enable.user.fail");
			e.printStackTrace();
			logger.info("启用用户     " + userName + "  失败！");
		} finally {
			resMap.put("RETURN_CODE", res);
			closeAd(ctx);
		}
		return resMap;
	}

	/**
	 * 重置用户密码
	 * 
	 * @param person
	 * @return Map
	 */
	public Map resetPwd(Person person) {

		String sAMAccountName = checkPersonExist(person);

		Map resMap = new HashMap();
		Properties properties = ConfigManager.getProperties("ad");
		String res = properties.getProperty("reset.pwd.success");		
		
		if (!"".equals(sAMAccountName)) {
			person.setsAMAccountName(sAMAccountName);

			// 建立636端口连接
			LdapContext ctx = conAd636();
			try {
				ModificationItem[] mods = new ModificationItem[3];
				String newQuotedPassword = "\""
						+ properties.getProperty("default.pwd") + "\"";
				byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute("unicodePwd", newUnicodePassword));
				mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute("userAccountControl",
								Integer.toString(UF_NORMAL_ACCOUNT
										+ UF_PASSWORD_EXPIRED)));
				mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute("pwdLastSet", "0"));
				ctx.modifyAttributes(person.getDistinguishedName(), mods);

				resMap.put("USER_NAME", person.getsAMAccountName());
				resMap.put("PASSWORD", properties.getProperty("default.pwd"));
				logger.info("重置用户密码     " + person.getDistinguishedName()
						+ "  成功！");
			} catch (NamingException e) {
				res = properties.getProperty("reset.pwd.fail");
				logger.info("重置用户密码失败原因     " + e.getMessage());
				logger.info("重置用户密码     " + person.getDistinguishedName()	+ "  失败！");
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				logger.info("重置用户密码失败原因     " + e.getMessage());
				e.printStackTrace();
			} finally {
				resMap.put("RETURN_CODE", res);
				closeAd(ctx);
			}
			return resMap;
		} else {
			res = properties.getProperty("user.is.noexist");
			resMap.put("RETURN_CODE", res);
			resMap.put("USER_NAME", person.getDisplayName());
			resMap.put("USERID", person.getsAMAccountName());
			return resMap;
		}

	}

	/**
	 * 
	 * @param person
	 * @return
	 */
	private String checkPersonExist(Person person) {

		// 实例化一个搜索器
		SearchControls searchCtls = new SearchControls();
		// 搜索范围： 树形检索
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		// 设置为false时返回结果占用内存减少
		searchCtls.setReturningObjFlag(true);
		// 设置查询过滤器
		String searchFilter = "(distinguishedName="
				+ person.getDistinguishedName() + ")";

		// 设置搜索域节点
		Properties properties = ConfigManager.getProperties("ad");
		String searchBase = properties.getProperty("ad.dn.base");
		// 定制返回属性
		String returnedAtts[] = { "sAMAccountName" };
		searchCtls.setReturningAttributes(returnedAtts);

		LdapContext ctx = this.conAd389();
		List userNameList = new ArrayList();
		String res = "";
		// 执行查询
		try {

			NamingEnumeration<SearchResult> sEnum = ctx.search(searchBase,
					searchFilter, searchCtls);
			if (sEnum == null || sEnum.equals(null)) {
				logger.info("没有重ID的用户");
			} else {
				logger.info("有重ID的用户,重id的用户分别是");
				while (sEnum.hasMoreElements()) {
					SearchResult sr = sEnum.nextElement();
					Attributes attrs = sr.getAttributes();

					NamingEnumeration<? extends Attribute> aEnum = attrs
							.getAll(); // 取到所有属性
					while (aEnum.hasMoreElements()) {
						Attribute attr = aEnum.nextElement();
						if (attr == null) {
							continue;
						}
						if ("sAMAccountName".equals(attr.getID())) {
							res = (String) attr.get(0);
							logger.info("要重置密码的用户【sAMAccountName】为：" + res);
						}
					}
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			closeAd(ctx);
		}
		return res;
	}

	/**
	 * 修改用户组织单位
	 * 
	 * @param name
	 *            用户姓名
	 * @param oldOu
	 *            原组织单位
	 * @param newOu
	 *            新组织单位
	 * @return Map
	 */
	public Map changePersonOu(String name, String oldOu, String newOu) {
		Map resMap = new HashMap();

		Properties properties = ConfigManager.getProperties("ad");
		String baseDN = properties.getProperty("ad.dn.base");
		String res = properties.getProperty("change.ou.success");
		StringBuffer oldDistinguishedName=null;
		if(!oldOu.startsWith("CN=")){
			oldDistinguishedName= new StringBuffer();
			oldDistinguishedName.append("CN=");
			oldDistinguishedName.append(name);
			oldDistinguishedName.append(",");
	
			String oldOuStr[] = oldOu.split(",");
			if (oldOuStr.length == 0) {
				oldDistinguishedName.append("CN=Users,");
			} else {
				for (int i = 0; i < oldOuStr.length; i++) {
					oldDistinguishedName.append("OU=");
					oldDistinguishedName.append(oldOuStr[i]);
					if (i <= oldOuStr.length - 1)
						oldDistinguishedName.append(",");
				}
			}
			oldDistinguishedName.append(baseDN);
		}else{
			oldDistinguishedName=new StringBuffer(oldOu);
		}
		StringBuffer newDistinguishedName = new StringBuffer();
		newDistinguishedName.append("CN=");
		newDistinguishedName.append(name);
		newDistinguishedName.append(",");

		if(name==null){
			logger.info("启用用户失败 ，未找到该用户");
			res=properties.getProperty("user.is.noexist");
			resMap.put("RETURN_CODE", res);
			
			return resMap;
		}
		
		StringBuffer newUserOu = new StringBuffer();

		String newOuStr[] = newOu.split(",");
		if (newOuStr.length == 0) {
			newDistinguishedName.append("CN=Users,");
		} else {
			for (int i = 0; i < newOuStr.length; i++) {
				newDistinguishedName.append("OU=");
				newDistinguishedName.append(newOuStr[i]);
				newUserOu.append("OU=");
				newUserOu.append(newOuStr[i]);
				if (i <= newOuStr.length - 1) {
					newDistinguishedName.append(",");
					newUserOu.append(",");
				}
			}
		}
		newDistinguishedName.append(baseDN);
		newUserOu.append(baseDN);
		logger.info("用户要调整到：" + newUserOu + "部门");
		// 判断新的组织单位是否存在
		boolean isOuExist = checkPersonOu(newUserOu.toString());
		if (isOuExist) {
			// 建立636端口连接
			LdapContext ctx = conAd636();
			try {
				ctx.rename(oldDistinguishedName.toString(),
						newDistinguishedName.toString());

				logger.info("将用户" + oldDistinguishedName + "修改组织单位"
						+ newDistinguishedName + "    成功！");
			} catch (NamingException e) {
				e.printStackTrace();
				res = properties.getProperty("change.ou.fail"); // 返回值
				logger.info("将用户" + oldDistinguishedName + "修改组织单位"
						+ newDistinguishedName + "   失败！");
			} finally {
				resMap.put("RETURN_CODE", res);
				closeAd(ctx);
			}
			return resMap;
		} else {
			res = properties.getProperty("user.ou.noexist"); // 返回值
			resMap.put("RETURN_CODE", res);
			logger.info("将用户" + oldDistinguishedName + "修改组织单位"
					+ newDistinguishedName + "   失败！");
			return resMap;
		}
	}

	/**
	 * 创建组织单位
	 * 
	 * @param ou
	 * @return Map
	 */
	public Map createOneOu(Ou ou) {
		Map resMap = new HashMap();

		Properties properties = ConfigManager.getProperties("ad");
		String res = properties.getProperty("create.ou.success");
		String baseDN = properties.getProperty("ad.dn.base");

		BasicAttribute ocattr = new BasicAttribute("objectClass");
		ocattr.add("top");
		ocattr.add("organizationalUnit");

		Attributes attrs = new BasicAttributes(true);
		attrs.put(ocattr);
		attrs.put("name", ou.getName());
		attrs.put("ou", ou.getOu());
		attrs.put("distinguishedName", ou.getDistinguishedName());

		String ouStr = ou.getDistinguishedName();

		String ouFatherStr = ouStr.substring(("OU=" + ou.getName()	+ ",").length());

		boolean isExist = false;

		// 判断上级组织单位是否存在
		boolean isOuExist1 = checkPersonOu(ouFatherStr.toString());
		
		logger.debug("上级单位存在："+isOuExist1);
		// 判断新的组织单位是否存在
		boolean isOuExist = checkPersonOu(ouStr.toString());

		if (!isOuExist) {
			if (isOuExist1) {
				// 建立636端口连接
				LdapContext ctx = conAd636();
				try {
					ctx.createSubcontext(ou.getDistinguishedName(), attrs);
					logger.info("创建组织单位     " + ou.getDistinguishedName()
							+ "  成功！");
				} catch (NamingException e) {
					res = properties.getProperty("create.ou.fail");
					logger.info("创建组织单位     " + ou.getDistinguishedName()
							+ "  失败！");
					e.printStackTrace();
				} finally {
					resMap.put("RETURN_CODE", res);
					closeAd(ctx);
				}
				return resMap;
			} else {
				logger.info("创建组织单位     " + ou.getDistinguishedName()
						+ "  失败，该组织上级机构不存在！");
				res = properties.getProperty("ou.up.noexist");
				resMap.put("RETURN_CODE", res);
				return resMap;
			}
		} else {
			logger.info("创建组织单位     " + ou.getDistinguishedName()
					+ "  失败，该组织已经存在！");
			res = properties.getProperty("ou.is.exist");
			resMap.put("RETURN_CODE", res);
			return resMap;
		}
	}

	/**
	 * 修改组织单位名称
	 * 
	 * @param oldName
	 *            原名称
	 * @param newName
	 *            新名称
	 * @return Map
	 */
	public Map updateOneOu(String oldName, String newName) {
		Map resMap = new HashMap();
		Properties properties = ConfigManager.getProperties("ad");
		String res = properties.getProperty("update.ou.success");

		// 建立636端口连接
		LdapContext ctx = conAd636();
		try {
			ctx.rename(oldName, newName);
			logger.info("修改组织单位名称     " + oldName + "  成功！");
		} catch (NamingException e) {
			res = properties.getProperty("update.ou.fail");
			logger.info("修改组织单位名称     " + oldName + "  失败！");
			logger.info("修改组织单位名称失败原因====>     " +e.getMessage());
			e.printStackTrace();
		} finally {
			closeAd(ctx);
			resMap.put("RETURN_CODE", res);
		}
		return resMap;
	}

	/**
	 * 手动迁移用户
	 * 
	 * @param oldOu
	 * @param newOu
	 */
	public void change(String oldOu, String newOu) {
		// 建立636端口连接
		LdapContext ctx = conAd636();
		try {
			ctx.rename(oldOu.toString(), newOu.toString());
			System.out.println("修改用户组织成功！");
		} catch (NamingException e) {
			e.printStackTrace();
			System.out.println("将用户" + oldOu + "修改组织单位" + newOu + "   失败！");
		} finally {
			closeAd(ctx);
		}
	}
	/**
	 * 更具登录id sAMAccountName 查询某人的组织机构
	 * @param persionid
	 * @return 
	 */
	public Person searchOnePersion(String sAMAccountName){
		Person person = new Person();;
		// 实例化一个搜索器
		SearchControls searchCtls = new SearchControls();
		// 搜索范围： 树形检索
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		// 设置为false时返回结果占用内存减少
		searchCtls.setReturningObjFlag(true);
		// 设置查询过滤器
		String searchFilter = "(sAMAccountName=" + sAMAccountName + ")";
		// String searchFilter = "(&(objectClass=user)(sAMAccountName=CeM))";

		// 设置搜索域节点
		Properties properties = ConfigManager.getProperties("ad");
		String searchBase = properties.getProperty("ad.dn.base");
		// 定制返回属性
		String returnedAtts[] = { "sAMAccountName","distinguishedName" };
		searchCtls.setReturningAttributes(returnedAtts);

		LdapContext ctx = this.conAd389();
		List userNameList = new ArrayList();
		int size = 0;
		String res = "";
		// 执行查询
		try {

			NamingEnumeration<SearchResult> sEnum = ctx.search(searchBase,
					searchFilter, searchCtls);
			if (sEnum == null || sEnum.equals(null)) {
				logger.info("未找到该ID的用户，该id为："+sAMAccountName);
				res = sAMAccountName;
			} else {
			
				while (sEnum.hasMoreElements()) {
					SearchResult sr = sEnum.nextElement();
					Attributes attrs = sr.getAttributes();

					NamingEnumeration<? extends Attribute> aEnum = attrs
							.getAll(); // 取到所有属性
					
					while (aEnum.hasMoreElements()) {
						Attribute attr = aEnum.nextElement();
						if (attr == null) {
							continue;
						}
						
						if ("sAMAccountName".equals(attr.getID())) {
							String name = (String) attr.get(0);
							//userNameList.add(name.toLowerCase());
							person.setsAMAccountName(name);
						}
						
						if("distinguishedName".equalsIgnoreCase(attr.getID())){
							String name = (String) attr.get(0);
						//	userNameList.add(name.toLowerCase());

							person.setDistinguishedName(name);
							logger.info("找到该用户，该用户的DistinguishedName串为："+person.getDistinguishedName());
							return person;
						}
					}
				}
			}
			//res = getName(userNameList, sAMAccountName);
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			closeAd(ctx);
		}
		return person;
	}
	
	
	//https://www.cnblogs.com/cnjavahome/p/9043490.html
    /**
     * @Description:修改AD域用户属性
     * @author moonxy
     * @date 2018-05-15
     */
    public boolean modifyInformation(String dn, String fieldValue) {
    	 DirContext dc = null;
    	 String root = null;
        try {
        	dc = this.conAd389();
            ModificationItem[] mods = new ModificationItem[1];  
            // 修改属性
            Attribute attr0 = new BasicAttribute("homePhone",fieldValue);  
            //mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr0);//新增属性
            //mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,attr0);//删除属性
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr0);//覆盖属性
            dc.modifyAttributes(dn + "," + root, mods); 
            System.out.println("修改AD域用户属性成功");
            return true;
        } catch (Exception e) {
            System.err.println("修改AD域用户属性失败");
            e.printStackTrace();
            return false;
        }
    }
	
}
