package com.gwssi.ad;
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Hashtable;  
import java.util.List;  
import java.util.Map;  
import javax.naming.Context;  
import javax.naming.NamingEnumeration;  
import javax.naming.directory.Attributes;  
import javax.naming.directory.SearchControls;  
import javax.naming.directory.SearchResult;  
import javax.naming.ldap.InitialLdapContext;  
import javax.naming.ldap.LdapContext;  
  
/** 
 * LDAP 连接 
 */  
public class LDAPConnection {  
  
    private String baseDN;  
    private String filter;  
    private String[] attributes;  
    private Hashtable<String, String> env = null;  
  
    private static LDAPConnection lc;  
  
    private LDAPConnection() {  
  
        //搜索根节点  
        baseDN = "******";  
        //要查询的属性列  
        attributes = new String[]{"cn"};  
        //过滤条件  
        if ((filter == null) || (filter == ""))  
            filter = "objectclass=*";  
    }  
  
    public static LDAPConnection getInstance() {  
        if (lc == null) {  
            lc = new LDAPConnection();  
        }  
        return lc;  
    }  
  
    /** 
     * 建立LDAP连接 
     * @return boolean 
     */  
    private LdapContext getLdapContext() {  
        env = new Hashtable<String, String>();  
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");  
        env.put(Context.PROVIDER_URL, "ldap://*****:389");  
        env.put(Context.SECURITY_AUTHENTICATION, "simple");  
        env.put(Context.SECURITY_CREDENTIALS, "123456");  
        env.put(Context.SECURITY_PRINCIPAL, "****");  
        try {  
            return new InitialLdapContext(env, null);  
        } catch (Exception e) {  
            System.out.println("连接服务器失败！");  
            e.printStackTrace();  
        }  
        return null;  
    }  
    /**  
     * 获取用户信息  
     * @return List<Map>  
     */  
    public List<Map> getUsers() {  
          
        LdapContext ctx = getLdapContext();  
        if(ctx == null){  
            return null;  
        }  
        List<Map> list = new ArrayList<Map>();  
        try {  
            SearchControls constraints = new SearchControls();  
            constraints.setReturningAttributes(attributes);  
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);  
            NamingEnumeration<?> en = ctx.search(baseDN, filter, constraints);  
  
            while (en != null && en.hasMoreElements()) {  
                Object obj = en.nextElement();  
                if (obj instanceof SearchResult) {  
  
                    SearchResult si = (SearchResult) obj;  
  
                    Attributes attrs = si.getAttributes();  
                    Map<String, Object> map = new HashMap<String, Object>();  
                    for (int i = 0; i < attributes.length; i++) {  
                        String attributeName = attributes[i];  
  
                        if(attrs.get(attributeName) == null){  
                            map.put(attributeName, attrs.get(attributeName));  
                        }else{  
                            map.put(attributeName, attrs.get(attributeName).get());  
                        }  
                    }  
                    System.out.println(map);  
                    list.add(map);  
                } else {  
                    System.out.println(obj);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        System.out.println("总符合条件记录数："+list.size());  
        return list;  
    }  
  
    public static void main(String arg[]) {  
        LDAPConnection.getInstance().getUsers();  
    }  
  
}  