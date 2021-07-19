<%--
/** Title:			logo_list.jsp
 *  Description:
 *		Logo列表页面
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
 *		see logo_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.publish.logo.Logo" %>
<%@ page import="com.trs.components.wcm.publish.logo.Logos" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nHostType		= currRequestHelper.getInt("HostType", 0);
	int nHostId			= currRequestHelper.getInt("HostId", 0);
	String sHostDesc	= currRequestHelper.getString("HostDesc");
	String sTitle = "";
	if(nHostType==101){
		Channel oChannel = Channel.findById(nHostId);
		if(oChannel!=null){
			sTitle = oChannel.getDesc() + "-" + nHostId;
			sHostDesc = oChannel.getDesc().length() > 10? oChannel.getDesc().substring(0,9) + ".." : sTitle;
		}
	}
	if(nHostType==605){
		Document oDocument = Document.findById(nHostId);
		if(oDocument!=null){
			sTitle = oDocument.getTitle();
			sHostDesc = oDocument.getTitle().length() > 10?(oDocument.getTitle().substring(0,9)+".."):oDocument.getTitle();
		}
	}

//5.权限校验

//6.业务代码	
	Logos currLogos = Logos.findOf(loginUser, nHostType, nHostId, null);
	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 10));
	currPager.setItemCount( currLogos.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="logo_list.jsp.title">TRS WCM Logo列表页面</title>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/logo.js"></script>
<script src="../js/data/locale/wcm52.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
<!-- dialog  Start -->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHashtable.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSRequestParam.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSAction.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLTr.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLElement.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></SCRIPT>
<%=currRequestHelper.toTRSRequestParam()%>
<!-- Dialog 引入的资源 BEGIN --->
<link type="text/css" rel="StyleSheet" href="../../style/style_CTRSCrashBoard.css" />
<script type="text/javascript" src="../../js/prototype.js"></script>
<script type="text/javascript" src="../../js/CTRSCrashBoard.js"></script>
<!-- Dialog 引入的资源 END --->
<style>
.grid_row_odd{
	background-color:#FFFFFF;
	cursor : pointer;
}
.grid_row_even{
	background-color:#efefef;
	cursor : pointer;
}
.grid_row_active{
	background-color:#ffffef;
	cursor : pointer;
}

</style>
</head>
<BODY topmargin="0" leftmargin="0" style="overflow:hidden">

<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="1" class="list_table" valign="top">
	<TR>
		<TD height="26" class="head_td">
		<TABLE width="100%" height="26" border="0" cellpadding="0"
			cellspacing="0">
			<TR>
				<TD width="245">&nbsp;&nbsp;<span WCMAnt:param="logo_delete.jsp.subhead">Logo列表管理</span>&nbsp;[<span title="<%=CMyString.filterForHTMLValue(sTitle)%>"><%=CMyString.transDisplay(sHostDesc)%></span>]&nbsp;</TD>
				<TD></TD>
				<TD class="navigation_channel_td"></TD>
				<TD width="28">&nbsp;</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
	<TR bgcolor="#FFFFFF">
		<TD valign="top">
		<TABLE width="100%" border="0" cellpadding="2" height="100%"
			cellspacing="0" bgcolor="#FFFFFF">
			<TR>
				<TD height="25" valign=top>
				<script>
					//定义一个单行按钮
					var oTRSButtons = new CTRSButtons();
					oTRSButtons.addTRSButton(wcm.LANG.LOGO_CONFIRM_1 || "新建", "addNew();", "../../images/button_new.gif", 
					<% if(nHostType == 101){ %>
						wcm.LANG.LOGO_CONFIRM_2 || "新建栏目Logo");
					<%}else{%>
						wcm.LANG.LOGO_CONFIRM_14 || "新建文档Logo");
					<%}%>
					oTRSButtons.addTRSButton(wcm.LANG.LOGO_CONFIRM_3 || "删除", "deleteLogo(getLogoIds());", "../../images/button_delete.gif", wcm.LANG.LOGO_CONFIRM_4 ||"删除选定的Logo");
					oTRSButtons.addTRSButton(wcm.LANG.LOGO_CONFIRM_5 ||"刷新", "CTRSAction_refreshMe();", "../../images/button_refresh.gif", wcm.LANG.LOGO_CONFIRM_6 ||"刷新当前页面");
					oTRSButtons.draw();	
				</script>
				</TD>
			</TR>
			<TR>
				<TD align="left" valign="top">
<div style="width:100%;height:100%;overflow:auto;">
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="1"
					class="list_table">
					<TR bgcolor="#D8E2E8" class="list_th">
						<TD bgcolor="#D8E2E8" width="50" height="20" NOWRAP><a
							href="javascript:TRSHTMLElement.selectAllByName('LogoIds');" WCMAnt:param="logo_delete.jsp.selectAll">全选</a></TD>						

						<TD bgcolor="#D8E2E8" WCMAnt:param="logo_delete.jsp.filename">文件名</TD>
						

						<TD bgcolor="#D8E2E8">Logo</TD>
						

						<TD bgcolor="#D8E2E8" WCMAnt:param="logo_delete.jsp.operation">操作</TD>
					</TR>
		<%
			Logo currLogo = null;
			for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
			{//begin for
				String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
				try{
					currLogo = (Logo)currLogos.getAt(i-1);
				} catch(Exception ex){
					throw new WCMException(CMyString.format(LocaleServer.getString("logo_list.getFailed","获取第[{0}]篇Logo失败!"),new int[i]), ex);
				}
				if(currLogo == null){
					throw new WCMException(CMyString.format(LocaleServer.getString("logo_list.notFound","没有找到第[{0}]篇Logo!"),new int[]{i}));
				}

				try{
		%>
					<TR class="<%=sRowClassName%>" rowId=<%=i%> onclick="TRSHTMLTr.onSelectedTR(this);">
						<TD style="text-align:center" NOWRAP><INPUT TYPE="checkbox" NAME="LogoIds"
							VALUE="<%=currLogo.getId()%>"><%=i%></TD>
						<TD><%=currLogo.getFileName()%></TD>

						<TD align=center><a href="../../file/read_image.jsp?FileName=<%=currLogo.getFileName()%>" onclick="return checkExists(<%=currLogo.getId()%>);" target="_blank" title="点击查看原图" WCMAnt:paramattr="title:logo_list.jsp.titleAttr"><img border=0  width="50" height="50" src="../../file/read_image.jsp?FileName=<%=currLogo.getFileName()%>"></a></TD>
						

						<TD align="center">&nbsp;<A title=""
							onclick="edit(<%=currLogo.getId()%>);return false;"
							href="#" WCMAnt:param="logo_delete.jsp.update">更新</A> | <A title="<%=LocaleServer.getString("logo_list.jsp.title1Attr", "重新排列Logo的顺序")%>" onclick="sort(<%=currLogo.getId()%>, <%=i%>);return false;" href="#" WCMAnt:param="logo_delete.jsp.order">排序</A></TD>
					</TR>
		<%
				} catch(Exception ex){
					throw new WCMException(CMyString.format(LocaleServer.getString("logo_list.attribute.getFailed","获取第[{0}]篇Logo的属性失败!"),new int[i]), ex);
				}
			}//end for	
		%>	
				</TABLE>
</div>
				</TD>
			</TR>	
		   </TABLE>
		</TD>
	</TR>
	<tr bgcolor="#FFFFFF" style="height:30px;">
		<td valign="top">
		<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 10), currRequestHelper.getInt("PageMaxCount", 1000),LocaleServer.getString("logo_list.jsp.PageNav1", "Logo图片"),LocaleServer.getString("logo_list.jsp.PageNav2", "个"))%>
		</td>
	</tr>
</TABLE>
<script>
var sysConfigName = "CHANNEL_LOGO_SIZE_LIMIT";
//注册页面使用的Dialog
var DIALOG_ADD_FILE = "AddFileDialog";
var DIALOG_EDIT_FILE = "EditFileDialog";
var DIALOG_SORT = "SortDialog";

TRSDialogCotainer.register(DIALOG_ADD_FILE,"上传一个Logo图片", '../file/file_upload.jsp?AllowExt=jpg,gif,bmp,png&Type='
+sysConfigName, '400px', '130px');
TRSDialogCotainer.register(DIALOG_EDIT_FILE,"修改当前Logo图片", '../file/file_upload.jsp?AllowExt=jpg,gif,bmp,png&Type='
+sysConfigName, '400px', '130px');
TRSDialogCotainer.register(DIALOG_SORT, "修改Logo顺序", '../logo/logo_sort.html', '300px', '130px');


//实现Dialog要求的方法
function notifyParentOnFinishedImpl(_sOperationName, _args){
	$('mask').style.display = 'none';
	if(_args == null || _args.length<=0){
		return;
	}

	switch(_sOperationName){
	case DIALOG_ADD_FILE :
	case DIALOG_EDIT_FILE:
		var oTRSAction = new CTRSAction("/wcm/app/logo/logo_addedit_dowith.jsp");
		oTRSAction.setParameter("LogoId", m_nLogId);
		oTRSAction.setParameter("HostDesc", "<%=CMyString.filterForJs(sHostDesc)%>");
		oTRSAction.setParameter("HostType", <%=nHostType%>);
		oTRSAction.setParameter("HostId", <%=nHostId%>);
		oTRSAction.setParameter("FileName", _args[0]);

		oTRSAction.doAction();
		break;
	case DIALOG_SORT :	
		var oTRSAction = new CTRSAction("/wcm/app/logo/logo_addedit_dowith.jsp");
		oTRSAction.setParameter("LogoId", m_nLogId);
		oTRSAction.setParameter("HostDesc", "<%=CMyString.filterForJs(sHostDesc)%>");
		oTRSAction.setParameter("HostType", <%=nHostType%>);
		oTRSAction.setParameter("HostId", <%=nHostId%>);
		oTRSAction.setParameter("LogoOrder", _args);

		oTRSAction.doAction();
		break;
	default:
		TRSDialogCotainer.close(_sOperationName);
		break;
	}
}

TRSDialog.prototype.close = function(){
	$('mask').style.display = 'none';
	this.clear();
	this._hideBoard();	
}
</script>

<script>
	var m_nLogId = 0;
	var m_nSize = <%=currLogos.size()%>;

	function getLogoIds(){
		return TRSHTMLElement.getElementValueByName('LogoIds');
	}

	function addNew(){
		m_nLogId = 0;
		TRSDialogCotainer.display(DIALOG_ADD_FILE);
		$('mask').style.display = '';	
	}
	
	function checkExists(_nLogoId){
		var oTRSAction = new CTRSAction("/wcm/app/logo/logo_findbyid.jsp");
		oTRSAction.setParameter("LogoId", _nLogoId);
		var r = oTRSAction.doXMLHttpAction();		
		if(r == _nLogoId){
			return true;
		}
		alert("The specified logo [ID="+_nLogoId+"] cannot be found.");
		CTRSAction_refreshMe();
		return false;
	}

	function edit(_nLogoId){	
		if(!checkExists(_nLogoId)) return false;
		m_nLogId = _nLogoId;
		TRSDialogCotainer.display(DIALOG_EDIT_FILE);
		$('mask').style.display = '';
		
	}

	function sort(_nLogoId, _nIndex){
		if(!checkExists(_nLogoId)) return false;
		m_nLogId = _nLogoId;
		var args = [m_nSize, _nIndex];
		TRSDialogCotainer.display(DIALOG_SORT, args);
		$('mask').style.display = '';		
	}
	
	function deleteLogo(_sLogoIds){
		//参数校验
		if(_sLogoIds == null || _sLogoIds.length <= 0){
			alert("请选择需要删除的Logo!");
			return;
		}
		if(!confirm("您确定需要删除选定的Logo吗?"))
			return;
		
		var oTRSAction = new CTRSAction("logo_delete.jsp");
		oTRSAction.setParameter("LogoIds", _sLogoIds);		
		oTRSAction.doDialogAction(500, 200);
		CTRSAction_refreshMe();
	}
</script>
<style type="text/css">
	#mask{
		position:absolute;
		left:0px;
		top:0px;
		width:100%;
		height:100%;
		overflow:hidden;
		filter:alpha(opacity=70);
	}
</style>
<iframe src="../include/blank.html" style="display:none;" id="mask"></iframe>
</BODY>
</HTML>
<%
//6.资源释放
	currLogos.clear();
%>