<%--
/** Title:			contentlink_list.jsp
 *  Description:
 *		热词的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-01 15:08:21
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-01 / 2005-04-01
 *	Update Logs:
 *		NZ@2005-04-01 产生此文件
 *		wenyh@2007-02-26 添加说明的校验,不允许特殊字符
 *
 *  Parameters:
 *		see contentlink_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLink" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkType" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nContentLinkId = currRequestHelper.getInt("ContentLinkId", 0);
	ContentLink currContentLink = null;
	if(nContentLinkId > 0){
		currContentLink = ContentLink.findById(nContentLinkId);
		if(currContentLink == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nContentLinkId+"]的热词失败！");
		}
	}else{//nContentLinkId==0 create a new group
		currContentLink = ContentLink.createNewInstance();
	}

	//wenyh@2006-12-12 09:33 添加分类,不再与站点关联
	int nLinkTypeId = currRequestHelper.getInt("ContentLinkTypeId",0);
	ContentLinkType type = ContentLinkType.findById(nLinkTypeId);
	if(type == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,"没有找到[Id="+nLinkTypeId+"]的分类！");
	}

	/*
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	WebSite currWebSite = WebSite.findById(nSiteId);
	if(currWebSite == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入参数有误，没有找到ID为["+nSiteId+"]的站点！");
	}*/

	if(IS_DEBUG) {
		System.out.println("nContentLinkId:"+nContentLinkId);
		System.out.println("nLinkTypeId:"+nLinkTypeId);
	}


//5.权限校验
	if(nContentLinkId > 0){
		if(!currContentLink.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, "热词["+currContentLink.getId()+"]["+currContentLink.getName()+"]被用户［"+currContentLink.getLockerUserName()+"］锁定！您不能修改！");
		}
	}
//6.业务代码
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("contentlink.label.addedit", "添加修改热词")%>              :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<base target="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
<Script>
TRSValidator.addValidators("contentlink_name",  "CWCMContentLinkNameValidator",  "../js/CWCMContentLinkNameValidator.js");    
</Script>
<SCRIPT LANGUAGE="JavaScript">
function submitForm(){
	var frmData = document.frmData;
	var sUrl = frmData.LINKURL.value || "";
	if(sUrl && sUrl.length >= 0){
		var arProtocol = ["ftp", "gopher", "http", "https", "mailto", "news", "telnet", "wais"];
		if(sUrl.indexOf(":") < 0){
			CTRSAction_alert("链接地址必须包含协议头！\n如：[http://] , [mailto:]！");
			return false;
		}
		var sPrefix = sUrl.substring(0, sUrl.indexOf(":")).toLowerCase();
		var bValidProtocl = false;
		for(var i=0; i<arProtocol.length; i++){
			if(!arProtocol[i]) continue;
			if(sPrefix == arProtocol[i]){
				bValidProtocl = true;
				break;
			}
		}
		if(sPrefix == "ftp" || sPrefix == "gopher" || sPrefix == "http" || sPrefix == "https"){
			var nPos = sUrl.indexOf(":");
			var sSlash = sUrl.substring(nPos+1, nPos+3);
			if(sSlash != "//"){
				CTRSAction_alert("链接地址必须包含协议头！\n如：[http://] , [mailto:]！");
				return false;
			}
		}
		if(!bValidProtocl){
			CTRSAction_alert("链接地址必须包含协议头！\n如：[http://] , [mailto:]！");
			return false;
		}
	}
	
	var sTitle = frmData.LINKTITLE.value || "";
	
	if(sTitle.length > 0){
		var pattern = /^[\w\u4e00-\u9fa5]*$/;
		if(!pattern.test(sTitle)){
			CTRSAction_alert("说明只能由汉字、字母、数字、下划线组成！");
			frmData.LINKTITLE.focus();
			frmData.LINKTITLE.select();
			return false;
		}
	}
	
	WCMAction.doPost(frmData, document.frmAction);	
}

function resetForm(){
	var frm = document.all("frmData");
	frm.reset();
}
</SCRIPT>
</HEAD>

<BODY onUnload="unlock(<%=nContentLinkId%>,<%=ContentLink.OBJ_TYPE%>);">
<FORM NAME="frmAction" ID="frmAction" METHOD=POST ACTION="./contentlink_addedit_dowith.jsp" style="margin-top:0">
	<%=currRequestHelper.toHTMLHidden()%>
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</FORM>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<TR>
<TD height="25">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		WCMDialogHead.draw("添加/修改热词");
	</SCRIPT>
</TD>
</TR>
<TR>
<TD align="center" valign="top" class="tanchu_content_td">

<FORM NAME="frmData" ID="frmData" onSubmit="return false;">
	<INPUT TYPE="hidden" name="ContentLinkId" value="<%=nContentLinkId%>">
	<INPUT TYPE="hidden" name="SiteId" value="0">
	<INPUT TYPE="hidden" name="ContentLinkType" value="<%=nLinkTypeId%>">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
	<TR>
	<TD class="tanchu_content">
		<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
		<!-- 请注意在此修改相应字段名，addedit页面的字段名必须与该对象数据表字段名相对应 -->
		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("contentlink.label.content", "内容")%>：</TD>
		<TD><INPUT name="LINKNAME" type="text" elname="<%=LocaleServer.getString("contentlink.label.content", "内容")%>" size="30" pattern="contentlink_name" not_null="1" max_len="30" LinkTypeId=<%=nLinkTypeId%> ContentLinkId=<%=nContentLinkId%> value="<%=PageViewUtil.toHtmlValue(currContentLink.getPropertyAsString("LINKNAME"))%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>

		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("contentlink.label.desc", "说明")%>：</TD>
		<TD><INPUT name="LINKTITLE" type="text" elname="<%=LocaleServer.getString("contentlink.label.desc", "说明")%>" size="30" pattern="string" max_len="100" value="<%=PageViewUtil.toHtmlValue(currContentLink.getPropertyAsString("LINKTITLE"))%>"></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>		

		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("contentlink.label.url", "URL")%>：</TD>
		<TD><INPUT name="LINKURL" type="text" elname="<%=LocaleServer.getString("contentlink.label.url", "URL")%>" size="30" pattern="string" not_null="1" max_len="100" value="<%=PageViewUtil.toHtmlValue(currContentLink.getPropertyAsString("LINKURL"))%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		
		

		</TABLE>
	</TD>
	</TR>
	<TR>
	<TD align="center">
		<script src="../js/CTRSButton.js"></script>
		<script>
			//定义一个TYPE_ROMANTIC_BUTTON按钮
			var oTRSButtons		= new CTRSButtons();
			
			oTRSButtons.cellSpacing	= "0";
			oTRSButtons.nType	= TYPE_ROMANTIC_BUTTON;

			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.confirm", "确定")%>", "submitForm()");
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "unlock(<%=nContentLinkId%>,<%=ContentLink.OBJ_TYPE%>);window.close();");
			
			oTRSButtons.draw();	
		</script>
	</TD>
	</TR>
	</TABLE>
</FORM>
</TD>
</TR>
</TABLE>
</BODY>
</HTML>