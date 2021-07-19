<%--
/** Title:			logo_addedit_dowith.jsp
 *  Description:
 *		处理Logo的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2006-03-28 19:56:25
 *  Vesion:			1.0
 *  Last EditTime:	2006-03-28 / 2006-03-28
 *	Update Logs:
 *		TRS WCM 5.2@2006-03-28 产生此文件
 *
 *  Parameters:
 *		see logo_addedit_dowith.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.publish.logo.Logo" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.wcm.publish.logo.Logos" %>
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nLogoId = currRequestHelper.getInt("LogoId", 0);
	Logo currLogo = null;
	if(nLogoId > 0){
		currLogo = Logo.findById(nLogoId);
		if(currLogo == null){
		    throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found_a", "没有找到指定ID为[{0}]的对象!"), new int[]{nLogoId}));
		}
	}else{//nLogoId==0 create a new group
		currLogo = Logo.createNewInstance();
	}

	String sFileName	= currRequestHelper.getString("FileName");
	int nLogoOrder		= currRequestHelper.getInt("LogoOrder", 0);
	int nHostType		= currRequestHelper.getInt("HostType", 0);
	int nHostId			= currRequestHelper.getInt("HostId", 0);
	
//5.权限校验
	if(nLogoId > 0){
		if(!currLogo.canEdit(loginUser)){
		    throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.locked.modify", "Logo被用户[{0}]锁定!"), new String[]{currLogo.getLockerUserName()}));
		}
	}

//6.业务代码
	if(sFileName != null){
		currLogo.setFileName(sFileName);
	}
	if(nHostType>0){
		currLogo.setHostType(nHostType);
	}
	if(nHostId>0){
		currLogo.setHostId(nHostId);
	}
	if(nLogoOrder>0){
		String sWhere = "HostId=?";
		WCMFilter filter = new WCMFilter("",sWhere,"LOGOORDER asc");
		filter.addSearchValues(nHostId);
		Logos currLogos = Logos.openWCMObjs(loginUser,filter);
		Logo newLogo = (Logo)currLogos.getAt(nLogoOrder-1);
		int nNewLogo = newLogo.getLogoOrder();
		currLogo.setLogoOrder(nNewLogo);
	}

	
	currLogo.save(loginUser);

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="logo_addedit_dowith.jsp.title">TRS WCM 处理添加修改Logo</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHashtable.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSRequestParam.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSAction.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLTr.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLElement.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></SCRIPT>
<%=currRequestHelper.toTRSRequestParam()%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
	window.location.href = "/wcm/app/logo/logo_list.jsp?" + TRSRequestParam.toURLParameters();
</SCRIPT>
</BODY>
</HTML>