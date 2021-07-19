<%--
/** Title:			securekey_list.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“WCM52交互密钥管理/列表”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2006-05-21 13:02
 *  Vesion:			1.0
 *  Last EditTime:	2006-05-21/2006-05-21
 *	Update Logs:
 *		wenyh@2006-05-21 created the file 
 *		wenyh@2006-08-29 修正导出,使用相对路径
 *
 *  Parameters:
 *		see securekey_list.xml
 *
 */
--%>
 
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.service.ISecurityService" %>
<%@ page import="com.trs.infra.support.security.SecureKey" %>
<%@ page import="com.trs.infra.support.security.SecureKeys" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%
//4.初始化（获取数据）
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());

//5.权限校验

	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,"只有系统管理员可以查看/管理系统交互密钥!");
	}

//6.业务代码
	ISecurityService service = (ISecurityService) DreamFactory.createObjectById("ISecurityService");	
	SecureKeys secureKeys = service.getKeys(currRequestHelper.getPageFilter(null));//get all keys.

	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( secureKeys.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

//7.结束
	out.clear();
%>

<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 系统交互密钥管理::::::::::::::::::::::::::::::::::..</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
<%@include file="../include/public_client_list.jsp"%>

<script>
	function addNew(){
		var oTRSAction = new CTRSAction("securekey_addedit.jsp");
		var bResult = oTRSAction.doDialogAction(500, 360);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}

	function exportKey(_zIsPrivate){
		var nSecureKeyId = TRSHTMLElement.getElementValueByName("SecureKeyId",",");
		if(nSecureKeyId == null || nSecureKeyId.length <=0){
			CTRSAction_alert("请选择一个你需要导出的密钥!");
			return;
		}

		if(nSecureKeyId.indexOf(",") != -1){
			CTRSAction_alert("一次只能导出一个密钥!");
			return;
		}

		var oTRSAction = new CTRSAction("securekey_export.jsp");
		oTRSAction.setParameter("SecureKeyId",nSecureKeyId);
		var nPrivate = 0;
		if(_zIsPrivate){
			nPrivate = 1;
		}
		oTRSAction.setParameter("IsPrivateKey",nPrivate);

		var bResult = oTRSAction.doDialogAction(500,360);
		var frm = document.all("ifrmDownload");
		//wenyh@2006-08-29 14:59 使用相对路径
		var sUrl = "../file/read_file.jsp?DownName=SecureKey&FileName="+bResult;
		frm.src = sUrl;
	}

	function deleteKey(){
		var secureKeyIds = TRSHTMLElement.getElementValueByName('SecureKeyId');
		if(secureKeyIds == null || secureKeyIds.length <=0){
			CTRSAction_alert("请选择要删除的密钥!");
			return;
		}

		if(!CTRSAction_confirm("您确定需要删除这些密钥吗?")) return;

		var oTRSAction = new CTRSAction("securekey_delete.jsp");
		oTRSAction.setParameter("SecureKeyIds",secureKeyIds);
		oTRSAction.doDialogAction(500, 300);
		CTRSAction_refreshMe();
	}
</script>
</head>
<BODY topmargin="0" leftmargin="0" style="margin:5px">		
<iframe name="ifrmDownload" width=0 height=0 src=""></iframe>
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
<!--~--- ROW1 ---~-->
<TR>
  <TD height="26" class="head_td" align=left>
	<TABLE width="100%" height="26" border="0" cellpadding="0" cellspacing="0">
	<TR>
		<TD width="24"><IMG src="../images/bite-blue-open.gif" width="24" height="24"></TD>
		<TD width="235"><%=LocaleServer.getString("syssecurekey.label.list", "系统密钥列表")%></TD> 
		<TD class="navigation_channel_td">&nbsp;</TD> 
		<TD width="28">&nbsp;</TD>
	</TR>
	</TABLE>
  </TD>
</TR>
<!--~- END ROW1 -~-->
<!--~--- ROW3 ---~-->
<TR>
  <TD valign="top">
  <!--~== TABLE3 ==~-->
  <TABLE width="100%" border="0" cellpadding="2" height="100%" cellspacing="0" bgcolor="#FFFFFF">
  <!--~--- ROW4 ---~-->
  <TR>
    <TD height="25">
    <!--~== TABLE4 ==~-->
    <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
    <!--~--- ROW5 ---~-->
    <TR>
      <TD align="left" valign="top">
			<script>
				//定义一个单行按钮
				var oTRSButtons = new CTRSButtons();
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.add", "新建")%>","addNew();", "../images/button_new.gif", "<%=LocaleServer.getString("syssecurekey.tip.add", "新建系统密钥")%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("syssecurekey.buton.export", "导出密钥")%>","exportKey(true);", "../images/button_export.gif", "<%=LocaleServer.getString("syssecurekey.tip.export", "导出密钥")%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("syssecurekey.buton.export", "导出公钥")%>","exportKey(false);", "../images/button_export.gif", "<%=LocaleServer.getString("syssecurekey.tip.export", "导出公钥")%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("syssecurekey.buton.remove", "删除")%>","deleteKey();", "../images/button_delete.gif", "<%=LocaleServer.getString("syssecurekey.tip.remove", "删除密钥")%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("sysconfig.tip.refresh", "刷新当前页面")%>");
				oTRSButtons.draw();	
			</script>      
      </TD>
	  <TD width="250" nowrap>
	  <form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">		
		<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
		<select name="SearchKey">
			<option value="KEYNAME,ALGORITHM">全部</option>
			<option value="KEYNAME">密钥名称</option>
			<option value="ALGORITHM">密钥算法</option>			
		</select>
		<input type="submit" value="<%=LocaleServer.getString("system.button.search", "检索")%>">		
	  </form>
	  </TD>
	  <script>
		document.frmSearch.SearchKey.value = "<%=CMyString.filterForJs(currRequestHelper.getSearchKey())%>";
	  </script>
    </TR>
    <!--~- END ROW5 -~-->	
    </TABLE>
    <!--~ END TABLE4 ~-->
    </TD>
  </TR>
  <!--~- END ROW4 -~-->
  <!--~-- ROW10 --~-->
  <TR>
    <TD align="left" valign="top" height="20">
    <!--~== TABLE9 ==~-->
	<div style="OVERFLOW-Y: auto; HEIGHT: 360px" id="dvBody">
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
    <!--~-- ROW11 --~-->
    <TR bgcolor="#BEE2FF" class="list_th" style="position: relative;top:expression(this.offsetParent.scrollTop);">
		<TD width="40" height="20" NOWRAP><a href="javascript:TRSHTMLElement.selectAllByName('SecureKeyId');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("KEYNAME", LocaleServer.getString("syssecurekey", "密钥名称"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("KEYSIZE", LocaleServer.getString("syssecurekey", "密钥长度"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("ALGORITHM", LocaleServer.getString("syssecurekey", "密钥算法"), sOrderField, sOrderType)%></TD>
	</TR>
    <!--~ END ROW11 ~-->
	<%
		SecureKey secureKey = null;		
		for(int i=currPager.getFirstItemIndex();i<=currPager.getLastItemIndex();i++){
			secureKey = (SecureKey)secureKeys.getAt(i-1);			
			if(secureKey == null) continue;
	%>
	<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
		<TD width="40" NOWRAP><INPUT TYPE="checkbox" NAME="SecureKeyId" VALUE="<%=secureKey.getId()%>"><%=i%></TD>
		<TD><%=PageViewUtil.toHtml(secureKey.getKeyName())%></TD>
		<TD><%=secureKey.getKeySize()%></TD>
		<TD><%=PageViewUtil.toHtml(secureKey.getAlgorithm())%></TD>		
	</TR>
	<%
		}//end of for.	
	%>
    </TABLE>
	</DIV>
    <!--~ END TABLE9 ~-->
    </TD>
  </TR>
  <!--~ END ROW10 ~-->
  <!--~-- ROW32 for PageIndex --~-->
  <TR>
    <TD class="navigation_page_td" valign="top">
	<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), "系统密钥", "个")%>
    </TD>
  </TR>
  <!--~ END ROW32 for PageIndex ~-->
  </TABLE>
  <!--~ END TABLE3 ~-->
  </TD>
</TR>
<!--~- END ROW3 -~-->
</TABLE>

</BODY>
</HTML>
<%
//6.资源释放
	secureKeys.clear();
%>