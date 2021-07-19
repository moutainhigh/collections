<%
/** Title:			login.jsp
 *  Description:
 *		WCM6 用户登录处理页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2006-12-26 10:12
 *  Vesion:			1.0
 *  Last EditTime:	2006-12-26 / 2006-12-26
 *	Update Logs:
 *		CH@2006-12-26 created
 *  Parameters:
 *		see login.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="java.util.Hashtable" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.config.ConfigServer"%>
<%
	// 验证码保存在session中,先将验证码取出来，再清除session liubaomin 2012-10-31
	String rand = (String)session.getAttribute("rand");
	session.removeAttribute("rand");
	if(!session.isNew()){
		session.invalidate();//清空session
		session = request.getSession(true);
	}
%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="include/public_server_nologin.jsp"%>
<%@include file="password_strength.jsp"%>
<%@include file="login_dowith_include_4_locale.jsp"%>
<%@include file="login_autofunc.jsp"%>
<%!
//常量定义
	/** 调试开关 **/
	private final static boolean  IS_DEBUG = false;
%>
<%
	 String sVerifyCodeMsg = "";
	 String sRand = currRequestHelper.getString("rand");
//3.初始化（获取数据）
	
//4.业务代码	
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	 
	String sUserName = currRequestHelper.getString("UserName");
	String sPassWord = currRequestHelper.getString("PassWord");
	if("true".equals(currRequestHelper.getString("ForceKick"))){
		currLoginHelper.setForceKick(true);		
	}
	//nResult初始化为-1，表示密码错误
	int nResult = -1; 
	//需要验证码
	boolean bNeedVerifyCode = currLoginHelper.isLoginWithVerifyCode();
    if(bNeedVerifyCode){
			sVerifyCodeMsg = "";
			if(rand == null || !rand.equals(sRand)){
				sVerifyCodeMsg = "验证码不正确！";
			}else{
				sVerifyCodeMsg = "";
			}
			//验证码通过才允许登录
			if("".equals(sVerifyCodeMsg)){
				nResult = currLoginHelper.login( sUserName, sPassWord );
			}
	}else{
	        //不需要验证码时
			nResult = currLoginHelper.login( sUserName, sPassWord );
	}
	if(sUserName==null){
		sUserName = "";
	}

	boolean bLogined = (nResult == LoginHelper.RESULT_OK);
	//add by liuhm@20130122 已经登录，强行登录的情况下，请求会再次提交到此页面，由于session被重置，
	//导致验证码丢掉，所以这种情况下，需要将验证码再设回去。
	if(nResult == LoginHelper.ERR_USER_LOGINED && bNeedVerifyCode){
		session.setAttribute("rand", rand);
	}
	//处理登录cookie.
	setCookie(response,AUTOLOGIN,CMyString.showNull(currRequestHelper.getString("autologin")));
	setCookie(response,makeTokenCookieName(sUserName),bLogined?sPassWord:"");
	setCookie(response,TRSUSERNAME,sUserName);

	//2013.1.11注释，存在安全漏洞，例如：如果一个普通用户发给管理员一个wcm地址带上添加用户的页面请求，管理员点击登陆WCM后就会创建一个用户，因此取消跳转到用户访问之前的页面。
	String sFormUrl = "";//request.getParameter("FromUrl");
	String sTargetUrl = "";
	String sLoginTimeoutLimitValue = ConfigServer.getServer().getSysConfigValue("LOGIN_TIMEOUT_LIMIT", "0");
	if( bLogined ){
		//设置用户的密码强度，只有当用户属于未标记时才会重新计算用户的密码强度
		setPasswordLev(currLoginHelper.getLoginUser(),sPassWord);
		session.setAttribute("login_now","true");
		if(Integer.parseInt(sLoginTimeoutLimitValue) > 0){
			session.setMaxInactiveInterval((Integer.parseInt(sLoginTimeoutLimitValue))*60);
		}
		LocaleServer.setFavorLanguage(currLoginHelper.getLoginUser(), m_sLocale);
		if(!CMyString.isEmpty(sFormUrl)){
			sFormUrl = sFormUrl.replace("\n","");
			sFormUrl = sFormUrl.replace("\r","");
			sTargetUrl = sFormUrl;
		}
		else{
			String sQuery = request.getQueryString();
			if(sQuery!=null){
				sQuery = sQuery.replaceAll("(?i)%0d|%0a","");
				sTargetUrl ="main.jsp?" + sQuery;
			}else{
				sTargetUrl ="main.jsp";
			}
		}
%>
<script language="javascript">
<%
	String isPermit = ConfigServer.getServer().getSysConfigValue("IS_REMIND_WEAK_PASSWORD", "false");
	if (isPermit.equalsIgnoreCase("true")) {
		int nPasswordLev = currLoginHelper.getLoginUser().getPasswordLev();
		if(nPasswordLev > 0 && nPasswordLev < 3){
%>
			alert("系统检测到您当前密码为弱密码，请修改登录密码！");
<%
		}
	}
%>
	window.location.href = '<%=sTargetUrl%>';
</script>

<%
		return;
	}
//5.清除服务器端的输出
	out.clear();
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<HTML>
<HEAD>
	<TITLE></TITLE>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</HEAD>
<BODY>
<form name="frmAction" action="login_dowith.jsp" method="post" target="_self" style="display:none">
	<INPUT TYPE="password" name="PassWord" value="<%=CMyString.filterForHTMLValue(sPassWord)%>">
	<INPUT TYPE="hidden" name="UserName" value="<%=CMyString.filterForHTMLValue(sUserName)%>">
	<INPUT TYPE="hidden" name="ForceKick" value="true">
	<INPUT TYPE="hidden" name="FromUrl" value="<%=CMyString.filterForHTMLValue(sFormUrl)%>">
	<INPUT TYPE="hidden" name="rand" value="<%=CMyString.filterForHTMLValue(sRand)%>">
</form>
<script src="js/easyversion/lightbase.js"></script>
<script src="js/easyversion/extrender.js"></script>
<script src="js/source/wcmlib/WCMConstants.js"></script>
<script src="js/data/locale/system.js"></script>
<SCRIPT LANGUAGE="JavaScript">
    <%
		//wenyh@2009-09-06 xss防范
		sUserName = CMyString.filterForJs(sUserName);
	%>
	var bLogined = <%=(nResult == LoginHelper.ERR_USER_LOGINED)%>;
	var sIsdebug = '';
	if(/isdebug=1/i.test(location.href)){
		sIsdebug = 'isdebug=1';
	}
	Event.observe(window, 'load', function(){
		if(bLogined){
			var confirmMsg = String.format("用户[{0}]已在[{1}]登录!\n您希望强行登录吗?", '<%=CMyString.filterForJs(sUserName)%>', '<%=(currLoginHelper.getLoginUser()!=null)?currLoginHelper.getLoginUser().getLoginIP():""%>');
			if(confirm(confirmMsg)){
				document.frmAction.submit();
			}else{
				window.location.href="login.jsp?UserName=<%=CMyString.filterForJs(sUserName)%>&"+sIsdebug;
			}
			return;
		}
		if(<%=(sVerifyCodeMsg!="")%>){
			alert('<%=sVerifyCodeMsg%>');
		}else{
		alert(String.format("\n{0}！", '<%=LoginHelper.getErrorMsg(nResult)%>'));
		}
		if(location.search.substring(1)){
			window.location.href = "login.jsp?UserName=<%=CMyString.filterForJs(sUserName)%>&" + 
										location.search.substring(1);
		}else{
			window.location.href = "login.jsp?UserName=<%=CMyString.filterForJs(sUserName)%>"; 
		}
	});
</SCRIPT>
</BODY>
</HTML>
<%!
	public void setPasswordLev(User _currUser,String sPassword)throws WCMException{
		int level = _currUser.getPasswordLev();
		if(level == 0){
			//level为0表示为未标记用户，重新计算它的密码强度
			level=checkPasswordLevel(sPassword);
			_currUser.setPasswordLev(level);
			_currUser.save();
		}
	}
%>