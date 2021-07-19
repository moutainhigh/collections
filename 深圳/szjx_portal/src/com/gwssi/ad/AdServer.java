package com.gwssi.ad;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import com.gwssi.optimus.core.common.ConfigManager;

@Service("adServer")
@WebService(targetNamespace="http://www.gwssi.com.cn", serviceName="services", portName="ad")
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class AdServer
  extends SpringBeanAutowiringSupport
{
  @Autowired
  private ExClient exClient;
  private static Logger logger = Logger.getLogger(AdServer.class);
  
  @WebMethod(exclude=false, operationName="con")
  @WebResult(name="ret", partName="ret")
  public String con()
  {
    logger.info("进入测试方法，webservice服务端成功发布！");
    logger.info("测试连接AD域是否成功");
    long t1 = new Date().getTime();
    AdDao ad = new AdDao();
    String ret = ad.testCon();
    long t2 = new Date().getTime();
    logger.info("con耗时：" + (t2 - t1) + "毫秒");
    return ret;
  }
  
  @WebMethod(exclude=false, operationName="createUser")
  @WebResult(name="ret", partName="ret")
  public String createUser(@WebParam(name="user", partName="user") String user)
  {
    logger.info("客户端传入参数user为：" + user);
    long t1 = new Date().getTime();
    
    InputStream fis = AdServer.class.getClassLoader().getResourceAsStream(
      "ad.properties");
    Properties properties = new Properties();
    try
    {
      properties.load(new InputStreamReader(fis, "UTF-8"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    Map pMap = XmlToMapUtil.dom2Map(user);
    String name = (String)pMap.get("NAME");
    String ou = (String)pMap.get("OU");
    String srvCode = (String)pMap.get("SRV_CODE");
    name = name.trim();
    ou = ou.trim();
    
    String sn = "";
    String giveName = "";
    String sAMAccountName = "";
    


    int nameLength = name.length();
    if (nameLength == 2)
    {
      sAMAccountName = Cn2Spell.converterToSpell(name);
      sn = name.substring(0, 1);
      giveName = name.substring(1, 2);
    }
    else
    {
      String snStr = properties.getProperty("first.name.cn");
      String[] cnList = snStr.split(",");
      
      int size = 0;
      boolean flag = false;
      for (int i = 0; i < cnList.length; i++) {
        if (name.startsWith(cnList[i]))
        {
          size = i;
          flag = true;
          sn = cnList[i];
          giveName = name.substring(cnList[i].length());
          break;
        }
      }
      if (flag)
      {
        String sAMAccountNameStr = properties
          .getProperty("first.name.en");
        String[] saList = sAMAccountNameStr.split(",");
        StringBuffer sAMAccountNameSb = new StringBuffer();
        sAMAccountNameSb.append(saList[size]);
        sAMAccountNameSb.append(
          Cn2Spell.converterToFirstSpell(giveName));
        sAMAccountName = sAMAccountNameSb.toString();
      }
      else
      {
        StringBuffer sAMAccountNameSb = new StringBuffer();
        sn = name.substring(0, 1);
        giveName = name.substring(1);
        sAMAccountNameSb.append(Cn2Spell.converterToSpell(sn));
        sAMAccountNameSb.append(
          Cn2Spell.converterToFirstSpell(giveName));
        sAMAccountName = sAMAccountNameSb.toString();
      }
    }
    AdDao ad = new AdDao();
    Person person = new Person();
    person.setSn(sn);
    person.setGiveName(giveName);
    person.setDisplayName(name);
    person.setCn(name);
    person.setsAMAccountName(sAMAccountName);
    

    String[] ouStr = ou.split(",");
    StringBuffer distinguishedName = new StringBuffer();
    StringBuffer ouString = new StringBuffer();
    distinguishedName.append("CN=");
    distinguishedName.append(name);
    distinguishedName.append(",");
    if (ouStr.length == 0) {
      distinguishedName.append("CN=Users,");
    } else {
      for (int i = 0; i < ouStr.length; i++)
      {
        ouString.append("OU=");
        ouString.append(ouStr[i]);
        if (i <= ouStr.length - 1) {
          ouString.append(",");
        }
      }
    }
    distinguishedName.append(ouString);
    String baseDN = properties.getProperty("ad.dn.base");
    distinguishedName.append(baseDN);
    ouString.append(baseDN);
    
    person.setDistinguishedName(distinguishedName.toString());
    person.setOu(ouString.toString());
    
    logger.info("要创建的用户信息为：");
    logger.info("姓：" + person.getSn());
    logger.info("名：" + person.getGiveName());
    logger.info("显示姓名：" + person.getDisplayName());
    logger.info("面板显示名称：" + person.getCn());
    logger.info("登陆ID(未处理重ID之前)：" + person.getsAMAccountName());
    logger.info("组织单位：" + person.getOu());
    logger.info("DN串：" + person.getDistinguishedName());
    
    Map resMap = ad.createOnePerson(person);
    long t2 = new Date().getTime();
    logger.info("createUser耗时：" + (t2 - t1) + "毫秒");
    
    this.exClient.enbleEX(pMap, resMap);
    
    return XmlToMapUtil.map2Dom(resMap);
  }
  
  @WebMethod(exclude=false, operationName="disableUser")
  @WebResult(name="ret", partName="ret")
  public String disableUser(@WebParam(name="user", partName="user") String user)
  {
    logger.info("客户端传入参数user为：" + user);
    long t1 = new Date().getTime();
    

    Map pMap = XmlToMapUtil.dom2Map(user);
    String userid = null;
    if (pMap.get("USERID") != null) {
      userid = (String)pMap.get("USERID");
    }
    String name = (String)pMap.get("NAME");
    String ou = (String)pMap.get("OU");
    String srvCode = (String)pMap.get("SRV_CODE");
    

    boolean m1 = (name != null) && (ou != null);
    boolean m1return = false;
    boolean m2 = StringUtils.isNotEmpty(userid);
    String xmlreturn = null;
    Map resMap = null;
    if (m1)
    {
      name = name.trim();
      ou = ou.trim();
      
      Properties properties = ConfigManager.getProperties("ad");
      String baseDN = properties.getProperty("ad.dn.base");
      String[] ouStr = ou.split(",");
      
      StringBuffer distinguishedName = new StringBuffer();
      distinguishedName.append("CN=");
      distinguishedName.append(name);
      distinguishedName.append(",");
      if (ouStr.length == 0) {
        distinguishedName.append("CN=Users,");
      } else {
        for (int i = 0; i < ouStr.length; i++)
        {
          distinguishedName.append("OU=");
          distinguishedName.append(ouStr[i]);
          if (i <= ouStr.length - 1) {
            distinguishedName.append(",");
          }
        }
      }
      distinguishedName.append(baseDN);
      
      Person person = new Person();
      person.setDistinguishedName(distinguishedName.toString());
      
      logger.info("要禁用的用户信息为：");
      logger.info("用户DN串为" + person.getDistinguishedName());
      
      AdDao ad = new AdDao();
      resMap = ad.disablePerson(person);
      long t2 = new Date().getTime();
      logger.info("disableUser耗时：" + (t2 - t1) + "毫秒");
      xmlreturn = XmlToMapUtil.map2Dom(resMap);
    }
    if (m1)
    {
      String rs = (String)resMap.get("RETURN_CODE");
      int returnno = 1;
      try
      {
        returnno = Integer.valueOf(rs).intValue();
      }
      catch (NumberFormatException e)
      {
        e.printStackTrace();
      }
      if ((returnno % 2 == 1) && (m2))
      {
        AdDao ad = new AdDao();
        resMap = ad.disablePerson(ad.searchOnePersion(userid));
        long t2 = new Date().getTime();
        logger.info("disableUser耗时：" + (t2 - t1) + "毫秒");
        xmlreturn = XmlToMapUtil.map2Dom(resMap);
      }
    }
    else if (m2)
    {
      AdDao ad = new AdDao();
      resMap = ad.disablePerson(ad.searchOnePersion(userid));
      long t2 = new Date().getTime();
      logger.info("disableUser耗时：" + (t2 - t1) + "毫秒");
      xmlreturn = XmlToMapUtil.map2Dom(resMap);
    }
    return xmlreturn;
  }
  
  @WebMethod(exclude=false, operationName="enableUser")
  @WebResult(name="ret", partName="ret")
  public String enableUser(@WebParam(name="user", partName="user") String user)
  {
    logger.info("客户端传入参数user为：" + user);
    long t1 = new Date().getTime();
    

    Map pMap = XmlToMapUtil.dom2Map(user);
    String name = (String)pMap.get("NAME");
    String ou = (String)pMap.get("OU");
    String srvCode = (String)pMap.get("SRV_CODE");
    

    String userid = null;
    if (pMap.get("USERID") != null) {
      userid = (String)pMap.get("USERID");
    }
    boolean m1 = (name != null) && (ou != null);
    boolean m1return = false;
    boolean m2 = StringUtils.isNotEmpty(userid);
    String xmlreturn = null;
    Map resMap = null;
    if (m1)
    {
      name = name.trim();
      ou = ou.trim();
      Properties properties = ConfigManager.getProperties("ad");
      String baseDN = properties.getProperty("ad.dn.base");
      String[] ouStr = ou.split(",");
      
      StringBuffer distinguishedName = new StringBuffer();
      distinguishedName.append("CN=");
      distinguishedName.append(name);
      distinguishedName.append(",");
      if (ouStr.length == 0) {
        distinguishedName.append("CN=Users,");
      } else {
        for (int i = 0; i < ouStr.length; i++)
        {
          distinguishedName.append("OU=");
          distinguishedName.append(ouStr[i]);
          if (i <= ouStr.length - 1) {
            distinguishedName.append(",");
          }
        }
      }
      distinguishedName.append(baseDN);
      
      Person person = new Person();
      person.setDistinguishedName(distinguishedName.toString());
      
      logger.info("要启用的用户信息为：");
      logger.info("用户DN串为" + person.getDistinguishedName());
      
      AdDao ad = new AdDao();
      resMap = ad.enablePerson(person);
      long t2 = new Date().getTime();
      logger.info("enableUser耗时：" + (t2 - t1) + "毫秒");
      xmlreturn = XmlToMapUtil.map2Dom(resMap);
    }
    if (m1)
    {
      String rs = (String)resMap.get("RETURN_CODE");
      int returnno = 1;
      try
      {
        returnno = Integer.valueOf(rs).intValue();
      }
      catch (NumberFormatException e)
      {
        e.printStackTrace();
      }
      if ((returnno % 2 == 1) && (m2))
      {
        AdDao ad = new AdDao();
        resMap = ad.enablePerson(ad.searchOnePersion(userid));
        long t2 = new Date().getTime();
        logger.info("enableUser耗时：" + (t2 - t1) + "毫秒");
        xmlreturn = XmlToMapUtil.map2Dom(resMap);
      }
    }
    else if (m2)
    {
      AdDao ad = new AdDao();
      resMap = ad.enablePerson(ad.searchOnePersion(userid));
      long t2 = new Date().getTime();
      logger.info("enableUser耗时：" + (t2 - t1) + "毫秒");
      xmlreturn = XmlToMapUtil.map2Dom(resMap);
    }
    return xmlreturn;
  }
  
  @WebMethod(exclude=false, operationName="resetPwd")
  @WebResult(name="ret", partName="ret")
  public String resetPwd(@WebParam(name="user", partName="user") String user)
  {
    logger.info("参数user：" + user);
    long t1 = new Date().getTime();
    

    Map pMap = XmlToMapUtil.dom2Map(user);
    String name = (String)pMap.get("NAME");
    String ou = (String)pMap.get("OU");
    String srvCode = (String)pMap.get("SRV_CODE");
    

    String userid = null;
    if (pMap.get("USERID") != null) {
      userid = (String)pMap.get("USERID");
    }
    boolean m1 = (name != null) && (ou != null);
    boolean m2 = StringUtils.isNotEmpty(userid);
    String xmlreturn = null;
    Map resMap = null;
    if (m1)
    {
      name = name.trim();
      ou = ou.trim();
      

      Properties properties = ConfigManager.getProperties("ad");
      String baseDN = properties.getProperty("ad.dn.base");
      String[] ouStr = ou.split(",");
      
      StringBuffer distinguishedName = new StringBuffer();
      distinguishedName.append("CN=");
      distinguishedName.append(name);
      distinguishedName.append(",");
      if (ouStr.length == 0) {
        distinguishedName.append("CN=Users,");
      } else {
        for (int i = 0; i < ouStr.length; i++)
        {
          distinguishedName.append("OU=");
          distinguishedName.append(ouStr[i]);
          if (i <= ouStr.length - 1) {
            distinguishedName.append(",");
          }
        }
      }
      distinguishedName.append(baseDN);
      
      Person person = new Person();
      person.setDisplayName(name);
      person.setDistinguishedName(distinguishedName.toString());
      
      logger.info("要重置密码的用户信息为：");
      logger.info("用户DN串为" + person.getDistinguishedName());
      
      AdDao ad = new AdDao();
      resMap = ad.resetPwd(person);
      long t2 = new Date().getTime();
      logger.info("resetPwd耗时：" + (t2 - t1) + "毫秒");
      xmlreturn = XmlToMapUtil.map2Dom(resMap);
    }
    if (m1)
    {
      String rs = (String)resMap.get("RETURN_CODE");
      int returnno = 1;
      try
      {
        returnno = Integer.valueOf(rs).intValue();
      }
      catch (NumberFormatException e)
      {
        e.printStackTrace();
      }
      if ((returnno % 2 == 1) && (m2))
      {
        AdDao ad = new AdDao();
        Person person = ad.searchOnePersion(userid);
        


        logger.info("要重置密码的用户信息为：");
        logger.info("用户DN串为" + person.getDistinguishedName());
        resMap = ad.resetPwd(person);
        long t2 = new Date().getTime();
        logger.info("resetPwd耗时：" + (t2 - t1) + "毫秒");
        xmlreturn = XmlToMapUtil.map2Dom(resMap);
      }
    }
    else if (m2)
    {
      AdDao ad = new AdDao();
      Person person = ad.searchOnePersion(userid);
      logger.info("要重置密码的用户信息为：");
      logger.info("用户DN串为" + person.getDistinguishedName());
      resMap = ad.resetPwd(person);
      long t2 = new Date().getTime();
      logger.info("resetPwd耗时：" + (t2 - t1) + "毫秒");
      xmlreturn = XmlToMapUtil.map2Dom(resMap);
    }
    return xmlreturn;
  }
  
  @WebMethod(exclude=false, operationName="changeUserOu")
  @WebResult(name="ret", partName="ret")
  public String changeUserOu(@WebParam(name="user", partName="user") String user)
  {
    long t1 = new Date().getTime();
    Map pMap = XmlToMapUtil.dom2Map(user);
    logger.info("客户端传入参数user为：" + user);
    String name = (String)pMap.get("NAME");
    String oldOu = (String)pMap.get("OLD_OU");
    String newOu = (String)pMap.get("NEW_OU");
    String srvCode = (String)pMap.get("SRV_CODE");
    newOu = newOu.trim();
    
    String userid = null;
    if (pMap.get("USERID") != null) {
      userid = (String)pMap.get("USERID");
    }
    boolean m1 = (name != null) && (oldOu != null) && (newOu != null);
    boolean m2 = (StringUtils.isNotEmpty(userid)) && (newOu != null);
    
    String xmlreturn = null;
    Map resMap = null;
    if (m1)
    {
      name = name.trim();
      oldOu = oldOu.trim();
      

      AdDao ad = new AdDao();
      resMap = ad.changePersonOu(name, oldOu, newOu);
      long t2 = new Date().getTime();
      logger.info("changeUserOu耗时：" + (t2 - t1) + "毫秒");
      xmlreturn = XmlToMapUtil.map2Dom(resMap);
    }
    if (m1)
    {
      String rs = (String)resMap.get("RETURN_CODE");
      int returnno = 1;
      try
      {
        returnno = Integer.valueOf(rs).intValue();
      }
      catch (NumberFormatException e)
      {
        e.printStackTrace();
      }
      if ((returnno % 2 == 1) && (m2))
      {
        AdDao ad = new AdDao();
        Person person = ad.searchOnePersion(userid);
        String dn = person.getDistinguishedName();
        if (dn != null)
        {
          name = dn.substring(0, dn.indexOf(","));
          name = name.replace("CN=", "");
          oldOu = dn;
          resMap = ad.changePersonOu(name, oldOu, newOu);
          long t2 = new Date().getTime();
          logger.info("changeUserOu耗时：" + (t2 - t1) + "毫秒");
          xmlreturn = XmlToMapUtil.map2Dom(resMap);
        }
        else
        {
          logger.info("changeUserOu 未找到该人" + userid);
        }
      }
    }
    else if (m2)
    {
      AdDao ad = new AdDao();
      Person person = ad.searchOnePersion(userid);
      String dn = person.getDistinguishedName();
      if (dn != null)
      {
        name = dn.substring(0, dn.indexOf(","));
        name = name.replace("CN=", "");
        oldOu = dn;
        resMap = ad.changePersonOu(name, oldOu, newOu);
        long t2 = new Date().getTime();
        logger.info("changeUserOu耗时：" + (t2 - t1) + "毫秒");
        xmlreturn = XmlToMapUtil.map2Dom(resMap);
      }
      else
      {
        logger.info("changeUserOu 未找到该人" + userid);
      }
    }
    return xmlreturn;
  }
  
  @WebMethod(exclude=false, operationName="creatOu")
  @WebResult(name="ret", partName="ret")
  public String creatOu(@WebParam(name="ouname", partName="ouname") String ouname)
  {
    logger.info("客户端传入参数ouname为：" + ouname);
    long t1 = new Date().getTime();
    Map pMap = XmlToMapUtil.dom2Map(ouname);
    String name = (String)pMap.get("NAME");
    String ouStr = (String)pMap.get("OU");
    String srvCode = (String)pMap.get("SRV_CODE");
    name = name.trim();
    ouStr = ouStr.trim();
    
    Properties properties = ConfigManager.getProperties("ad");
    String baseDN = properties.getProperty("ad.dn.base");
    
    String[] str = ouStr.split(",");
    StringBuffer distinguishedName = new StringBuffer();
    for (int i = 0; i < str.length; i++)
    {
      distinguishedName.append("OU=");
      distinguishedName.append(str[i]);
      if (i <= str.length - 1) {
        distinguishedName.append(",");
      }
    }
    distinguishedName.append(baseDN);
    
    Ou ou = new Ou();
    ou.setName(name);
    ou.setOu(name);
    ou.setDistinguishedName(distinguishedName.toString());
    
    logger.info("要创建的组织单位为：");
    System.out.println("组织单位DN串为 " + ou.getDistinguishedName());
    
    AdDao ad = new AdDao();
    Map resMap = ad.createOneOu(ou);
    long t2 = new Date().getTime();
    logger.info("creatOu耗时：" + (t2 - t1) + "毫秒");
    return XmlToMapUtil.map2Dom(resMap);
  }
  
  @WebMethod(exclude=false, operationName="changeOu")
  @WebResult(name="ret", partName="ret")
  public String changeOu(@WebParam(name="ouname", partName="ouname") String ouname)
  {
    long t1 = new Date().getTime();
    Map pMap = XmlToMapUtil.dom2Map(ouname);
    logger.info("客户端传入参数ouname为：" + ouname);
    String oldOuName = (String)pMap.get("OLD_NAME");
    String ouStr = (String)pMap.get("OU");
    String newOuName = (String)pMap.get("NEW_NAME");
    String srvCode = (String)pMap.get("SRV_CODE");
    
    oldOuName = oldOuName.trim();
    ouStr = ouStr.trim();
    newOuName = newOuName.trim();
    
    Properties properties = ConfigManager.getProperties("ad");
    String baseDN = properties.getProperty("ad.dn.base");
    
    String[] str = ouStr.split(",");
    StringBuffer oldDistinguishedName = new StringBuffer();
    StringBuffer newDistinguishedName = new StringBuffer();
    for (int i = 0; i < str.length; i++)
    {
      oldDistinguishedName.append("OU=");
      oldDistinguishedName.append(str[i]);
      newDistinguishedName.append("OU=");
      if (i == 0) {
        newDistinguishedName.append(newOuName);
      } else {
        newDistinguishedName.append(str[i]);
      }
      if (i <= str.length - 1)
      {
        oldDistinguishedName.append(",");
        newDistinguishedName.append(",");
      }
    }
    oldDistinguishedName.append(baseDN);
    newDistinguishedName.append(baseDN);
    logger.info("老组织单位为：" + oldDistinguishedName);
    logger.info("新组织单位为：" + newDistinguishedName);
    
    AdDao ad = new AdDao();
    Map resMap = ad.updateOneOu(oldDistinguishedName.toString(), 
      newDistinguishedName.toString());
    
    long t2 = new Date().getTime();
    logger.info("changeOu耗时：" + (t2 - t1) + "毫秒");
    return XmlToMapUtil.map2Dom(resMap);
  }
  
  
  
	
	//根据用户ID得到用户的OU
	@WebMethod(exclude = false, operationName = "getUserOUByUserId")
	@WebResult(name = "ret", partName = "ret")
	public String getUserOUByUserId(@WebParam(name = "userId", partName = "userId") String  userId) {
		logger.info("进入获取用户OU的方法，webservice服务端成功获取！");
		AdDao ad = new AdDao();
		//String ret = ad.testCon();
		Person p= ad.searchOnePersion(userId);
		System.out.println("当前userId 为"+userId+"的对应的组织单位为：" + p.getDistinguishedName());
		logger.info("当前userId 为"+userId+"的对应的组织单位为：" + p.getDistinguishedName());
		String ret =  p.getDistinguishedName();
		if(ret!=null) {
			return ret;
		}else {
			return "该用户暂无组织信息";
		}
	}
	
	
	

	
	
}
