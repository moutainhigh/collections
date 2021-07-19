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
<%
	if(!session.isNew()){
		session.invalidate();//清空session
		session = request.getSession(true);
	}
%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server_nologin.jsp"%>
<%@include file="../password_strength.jsp"%>
<%!
//常量定义
	/** 调试开关 **/
	private final static boolean  IS_DEBUG = false;
	private final static String  m_sLocale = "zh_CN";
%>
<%
//3.初始化（获取数据）
	
//4.业务代码	
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	String sUserName = currRequestHelper.getString("UserName");
	String sPassWord = currRequestHelper.getString("PassWord");
	if("true".equals(currRequestHelper.getString("ForceKick"))){
		currLoginHelper.setForceKick(true);		
	}
	int nResult = currLoginHelper.login( sUserName, sPassWord );
	if(sUserName==null){
		sUserName = "";
	}
	String sFormUrl = request.getParameter("FromUrl");
	if( nResult==LoginHelper.RESULT_OK ){
		//设置用户的密码强度，只有当用户属于未标记时才会重新计算用户的密码强度
		setPasswordLev(currLoginHelper.getLoginUser(),sPassWord);
		session.setAttribute("login_now","true");
		LocaleServer.setFavorLanguage(currLoginHelper.getLoginUser(), m_sLocale);
		if(!CMyString.isEmpty(sFormUrl)){
			response.sendRedirect(sFormUrl);
		}
		else{
			String sQuery = request.getQueryString();
			if(sQuery!=null){
				response.sendRedirect("main.jsp?" + sQuery);
			}else{
				response.sendRedirect("main.jsp");
			}
		}
		return;
	}
//5.清除服务器端的输出
	out.clear();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
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
</form>
<script src="../js/easyversion/lightbase.js"></script>
<script src="../js/easyversion/extrender.js"></script>
<script src="../js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/data/locale/system.js"></script>
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
			var confirmMsg = String.format(WCMLANG['LOGIN_DOWITH_FORCE_LOGIN'], '<%=CMyString.filterForJs(sUserName)%>', '<%=(currLoginHelper.getLoginUser()!=null)?currLoginHelper.getLoginUser().getLoginIP():""%>');
			if(confirm(confirmMsg)){
				document.frmAction.submit();
			}else{
				window.location.href="../login.jsp?UserName=<%=CMyString.filterForJs(sUserName)%>&"+sIsdebug;
			}
			return;
		}
		alert(String.format(WCMLANG['LOGIN_DOWITH_ERRMSG'], '<%=LoginHelper.getErrorMsg(nResult)%>'));
		if(location.search.substring(1)){
			window.location.href = "../login.jsp?UserName=<%=CMyString.filterForJs(sUserName)%>&" + 
										location.search.substring(1);
		}else{
			window.location.href = "../login.jsp?UserName=<%=CMyString.filterForJs(sUserName)%>"; 
		}
	});
</SCRIPT>
</BODY>
</HTML>
<%!
	public void setPasswordLev(User _currUser,String sPassword)throws WCMException{
		int level = _currUser.getPasswordLev();
		if(level==0){
			//level为0表示为未标记用户，重新计算它的密码强度
			level=checkPasswordLevel(sPassword);
			_currUser.setPasswordLev(level);
			_currUser.save();
		}
	}
%>