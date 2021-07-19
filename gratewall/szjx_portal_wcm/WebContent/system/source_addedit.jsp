<%--
/** Title:			test.jsp
 *  Description:
 *		文档来源的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-04-01 15:34:11
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-01 / 2006-05-22
 *	Update Logs:
 *		CH@2005-04-01 产生此文件
 *		wenyh@2006-05-22 文档来源的分站点
 *
 *  Parameters:
 *		see test.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Securities" %>
<%@ page import="com.trs.components.wcm.resource.Security" %>
<%@ page import="com.trs.components.wcm.resource.Source" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nSourceId = currRequestHelper.getInt("SourceId", 0);
	Source currSource = null;
	if(nSourceId > 0){
		currSource = Source.findById(nSourceId);
		if(currSource == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nSourceId+"]的文档来源失败！");
		}
	}else{//nSourceId==0 create a new group
		currSource = Source.createNewInstance();
	}
//5.权限校验
	if(nSourceId > 0){
		if(!currSource.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, "文档来源被用户["+currSource.getLockerUserName()+"]锁定！");
		}
	}

//6.业务代码
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("document.label.addedit_of_source", "添加修改文档来源")%>:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<base target="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
<Script>
//register WCMSiteName Validator
TRSValidator.addValidators("source_name",  "CWCMSourceNameValidator",  "../js/validator/CWCMSourceNameValidator.js");    
</Script>
<SCRIPT LANGUAGE="JavaScript">
function submitForm(){
	var frmData = document.frmData;
	var frmAction = document.frmAction;
	WCMAction.doPost(frmData, document.frmAction);	
}

function resetForm(){
	var frm = document.all("frmData");
	frm.reset();
}
</SCRIPT>
</HEAD>

<BODY onUnload="unlock(<%=nSourceId%>,<%=Source.OBJ_TYPE%>);">
<FORM NAME="frmAction" ID="frmAction" METHOD=POST ACTION="./source_addedit_dowith.jsp" style="margin-top:0">
	<%=currRequestHelper.toHTMLHidden()%>
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</FORM>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<TR>
<TD height="25">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		WCMDialogHead.draw("<%=LocaleServer.getString("document.label.addedit_of_source", "添加/修改文档来源")%>");
	</SCRIPT>
</TD>
</TR>
<TR>
<TD align="center" valign="top" class="tanchu_content_td">

<FORM NAME="frmData" ID="frmData" onSubmit="return false;">
	<INPUT TYPE="hidden" name="SourceId" value="<%=nSourceId%>">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
	<TR>
	<TD class="tanchu_content">
		<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("document.label.name_of_source", "来源名称")%>：</TD>
		<TD><INPUT name="SRCNAME" type="text" elname="<%=LocaleServer.getString("document.label.name_of_source", "来源名称")%>" size="30" pattern="source_name" not_null="1" max_len="50" value="<%=PageViewUtil.toHtmlValue(currSource.getPropertyAsString("SRCNAME"))%>" SourceId="<%=nSourceId%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("document.label.desc_of_source", "来源描述")%>：</TD>
		<TD><INPUT name="SRCDESC" type="text" size="30" pattern="string" max_len="20" elname="<%=LocaleServer.getString("document.label.desc_of_source", "来源描述")%>" not_null="1" value="<%=PageViewUtil.toHtmlValue(currSource.getPropertyAsString("SRCDESC"))%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		

		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("document.label.link_of_source", "来源链接")%>：</TD>
		<TD><INPUT name="SRCLINK" type="text" size="30" pattern="string" max_len="200" elname="<%=LocaleServer.getString("document.label.link_of_source", "来源链接")%>" value="<%=PageViewUtil.toHtmlValue(currSource.getPropertyAsString("SRCLINK"))%>"></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		

		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("document.label.docsecurity", "安全级别")%>：</TD>
		<TD>
			<SELECT name="Security">
			<%=showDocSecurity(loginUser, currSource.getSecurityId())%>
			</SELECT>
		</TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		
		<TR>
		<TD width="60" align="left"><%=LocaleServer.getString("document.label.docsource_hostsite", "所属站点")%>：</TD>
		<TD>
			<SELECT name="SiteId" id="SiteId" pattern="integer" not_null="1" elname="所属站点" value='' min_value="1">
			<%=showHostSite(loginUser, currSource.getPropertyAsInt("SITEID",0))%>
			</SELECT><span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span>
		</TD>
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
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "unlock(<%=nSourceId%>,<%=Source.OBJ_TYPE%>);window.close();");
			
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
<%!
private String showDocSecurity(User _user, int _nCurrSecurityId) throws WCMException {
	String sSelectFields = "SECURITYID,SNAME,SDISP,SDESC,SVALUE";
	String sOrder = "SVALUE ASC";
	WCMFilter filter = new WCMFilter("", "", sOrder, sSelectFields);

	Securities currSecurities = Securities.openWCMObjs(_user, filter);

	if(currSecurities == null || currSecurities.isEmpty()){
		return "";
	}

	int nSize = currSecurities.size();
	Security currSecurity = null;
	StringBuffer sb = new StringBuffer();
	sb.append("<option value=''>--请选择--</option>");
	for(int i=0; i<nSize; i++){
		currSecurity = (Security) currSecurities.getAt(i);
		int nSecurityId = currSecurity.getId();
		if( currSecurity == null ){
			continue;
		}
		sb.append("<option value=\"");
		sb.append(nSecurityId);
		sb.append("\"");
		if(nSecurityId == _nCurrSecurityId)
			sb.append(" selected ");
		sb.append(">");
		sb.append(currSecurity.getDisp());
		sb.append("</option>");
	}

	currSecurities.clear();

	return sb.toString();
}

private String showHostSite(User _user, int _nSiteId) throws WCMException{
	WebSites sites = new WebSites(_user);
	WCMFilter filter = new WCMFilter("", "status=0", "");
	sites.open(filter);

	if(sites.isEmpty()) return "";
	
	int nSize = sites.size();
	int nSiteId = 0;
	WebSite site = null;
	StringBuffer sb = new StringBuffer(256);
	sb.append("<option value=''>--请选择--</option>");
	for(int i=0; i<nSize; i++){
		site = (WebSite)sites.getAt(i);
		if (site == null) continue;
		nSiteId = site.getId();
		sb.append("<option value='").append(nSiteId).append("'");
		if(_nSiteId == nSiteId) sb.append(" selected");
		sb.append(">");
		sb.append(site.getDesc()).append("</option>");
	}

	sites.clear();
	return sb.toString();
}
%>