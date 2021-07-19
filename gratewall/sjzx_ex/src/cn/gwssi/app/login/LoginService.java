package cn.gwssi.app.login;

import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.security.KeyManager;
import cn.gwssi.common.component.security.RSAPrivateKey;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.Constants;
import com.gwssi.common.util.MD5;

public class LoginService extends TxnService
{
  protected void prepare(TxnContext context)
    throws TxnException
  {
  }

  public void login(TxnContext context)
    throws TxnException
  {
    boolean ssoFlag = false;
    String username = null;
    String certName = context.getValue("certname");

    if (certName != null) {
      ssoFlag = true;

      RSAPrivateKey key = KeyManager.getInstance().getLocalKey();
      if ((key != null) && (key.getAliasName().compareTo(certName) == 0))
      {
        username = context.getValue("username");
      }
      else
      {
        username = context.getValue("username");
      }
    }
    else
    {
      username = context.getValue("username");
    }

    String password = context.getValue(Constants.LOGIN_PASSWORD);
    password = MD5.md5(password.toUpperCase()).toUpperCase();
    this.log.debug("csdb登录用户名: " + username);
    this.log.debug("csdb登录密码: " + password);

    callService("com.gwssi.sysmgr.user.txn.TxnXt_zzjg_yh", "login", context);
    DataBus userMessage = context.getRecord("user-info");
    this.log.debug("登录用 户信息：\n" + userMessage);

    String logintype = context.getValue("logintype");
    //验证用户是否存在
    if(logintype!=null&&logintype.equals("000001")){
        if ((userMessage == null) || (userMessage.isEmpty()))
            throw new TxnErrorException(logintype, "用户帐号不存在！");
          if (Constants.status_offuse.equals(userMessage.getValue("sfyx")))
            throw new TxnErrorException(logintype, "用户帐号已注销！");
          
          String yhmm=userMessage.getValue("yhmm");
          
/*		去除用户名密码验证
  	if (yhmm.length() != 32) {
				yhmm =  MD5.md5(yhmm.toUpperCase()).toUpperCase();
			}
          if (!(password.equals(yhmm))) {
            throw new TxnErrorException(logintype, "用户密码输入错误，请重新输入！");
          }  	   */
    }
    else{
        if ((userMessage == null) || (userMessage.isEmpty()))
            throw new TxnErrorException("999999:未知的错误", "用户帐号不存在！");
          if (Constants.status_offuse.equals(userMessage.getValue("sfyx")))
            throw new TxnErrorException("999999:未知的错误", "用户帐号已注销！");
          if (!(password.equals(userMessage.getValue("yhmm")))) {
            throw new TxnErrorException("999999:未知的错误", "用户密码输入错误，请重新输入！");
          }
    }

    String mainrole = userMessage.getValue("mainrole");

    String roles = userMessage.getValue("roleids");
    String rolelist = null;

    if ((mainrole == null) || (mainrole.length() == 0)) {
      String[] roleArray = roles.split(",");
      mainrole = roleArray[0];
    }

    if ((roles != null) && (roles.length() > 0)) {
      rolelist = roles.replaceAll(",", ";");
    }

    String mainrolename = userMessage.getValue("mainrolename");

    String rolenames = userMessage.getValue("rolenames");
    String rolenamelist = null;

    if ((mainrolename == null) || (mainrolename.length() == 0)) {
      String[] rolenameArray = rolenames.split(",");
      mainrolename = rolenameArray[0];
    }

    if ((rolenames != null) && (rolenames.length() > 0)) {
      rolenamelist = rolenames.replaceAll(",", ";");
    }

    String userid = userMessage.getValue("yhid_pk");
    String userXM = userMessage.getValue("yhxm");

    String jgid = userMessage.getValue("jgid_fk");

    TxnContext queryContext = new TxnContext();
    queryContext.getRecord("primary-key").setValue("jgid_pk", jgid);
    callService("com.gwssi.sysmgr.org.txn.TxnXt_zzjg_jg", "findJgnameByLogin", queryContext);

    String jgname = queryContext.getRecord("record").getValue("jgmc");
    String sjjgname = queryContext.getRecord("record").getValue("sjjgname");

    queryContext = new TxnContext();
    queryContext.getRecord("select-key").setValue("roles", roles);
    callService("com.gwssi.sysmgr.role.txn.TxnOperrole", "queryMaxCount", queryContext);
    String maxCount = queryContext.getRecord("record").getValue("maxcount");

    if ((mainrole != null) && (!(mainrole.equals("")))) {
      loadRoleInfo(context, mainrolename, rolenamelist);
    }

    String txnList = context.getRecord("role-info").getValue("txnList");
    VoUser operData = context.getOperData();

    operData.setUserName(username);

    operData.setOperName(userXM);

    operData.setValue(Constants.USER_ID, userid);

    operData.setOrgCode(jgid);
    operData.setOrgName(jgname);
    operData.setValue("sjjgname", sjjgname);

    operData.setValue(Constants.MAIN_ROLE_ID, mainrole);

    operData.setValue(Constants.MAX_COUNT, maxCount);
    operData.setRoleList(rolelist);
    operData.setTxnList(txnList);
  }

  protected void loadTxnList(TxnContext context, String funcList)
    throws TxnException
  {
    DataBus data = new DataBus();
    data.setValue("funclist", funcList);
    context.setValue("funcinfo", data);

    BaseTable funcInfo = TableFactory.getInstance().getTableObject(this, "funcinfo");
    funcInfo.executeFunction("loadTxnList", context, "funcinfo", null);
  }

  protected void loadRoleInfo(TxnContext context, String roleName, String roleGroup)
    throws TxnException
  {
    DataBus data = new DataBus();
    data.setValue("rolename", roleName);
    if (roleGroup != null) {
      data.setValue("rolegroup", roleGroup);
    }

    context.setValue("input-role-name", data);

    BaseTable funcInfo = TableFactory.getInstance().getTableObject(this, "operrole");
    funcInfo.executeFunction("loadRoleInfo", context, "input-role-name", "role-info");

    DataBus roleInfo = context.getRecord("role-info");

    VoUser operData = context.getOperData();
    String homePage = roleInfo.getValue("homepage");
    if ((homePage != null) && (homePage.compareTo("/main.jsp") != 0)) {
      operData.setWelcomePage(homePage);
    }

    String layoutName = roleInfo.getValue("layout");
    if ((layoutName != null) && (layoutName.length() != 0))
      operData.setLayoutName(layoutName);
  }
}