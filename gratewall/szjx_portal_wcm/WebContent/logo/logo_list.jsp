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
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nHostType		= currRequestHelper.getInt("HostType", 0);
	int nHostId			= currRequestHelper.getInt("HostId", 0);
	String sHostDesc	= currRequestHelper.getString("HostDesc");
	if(nHostType==101){
		Channel oChannel = Channel.findById(nHostId);
		if(oChannel!=null){
			sHostDesc = oChannel.getDesc() + "-" + nHostId;
		}
	}

//5.权限校验

//6.业务代码	
	Logos currLogos = Logos.findOf(loginUser, nHostType, nHostId, null);

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM V6::::::Logo列表页面[<%=PageViewUtil.toHtml(sHostDesc)%>]</title>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
<%@include file="../include/public_client_list.jsp"%>

<!-- Dialog 引入的资源 BEGIN --->
<link type="text/css" rel="StyleSheet" href="../style/style_CTRSCrashBoard.css" />
<script type="text/javascript" src="../js/prototype.js"></script>
<script type="text/javascript" src="../js/CTRSCrashBoard.js"></script>
<!-- Dialog 引入的资源 END --->

</head>
<BODY topmargin="0" leftmargin="0" style="margin:5px">

<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="1" class="list_table" valign=top>
	<TR>
		<TD height="26" class="head_td">
		<TABLE width="100%" height="26" border="0" cellpadding="0"
			cellspacing="0">
			<TR>
				<TD width="245">&nbsp;&nbsp;Logo列表管理[<%=PageViewUtil.toHtml(sHostDesc)%>]</TD>
				<TD></TD>
				<TD class="navigation_channel_td"></TD>
				<TD width="28">&nbsp;</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
	<TR>
		<TD valign="top">
		<TABLE width="100%" border="0" cellpadding="2" height="100%"
			cellspacing="0" bgcolor="#FFFFFF">
			<TR>
				<TD height="25" valign=top>
				<script>
					//定义一个单行按钮
					var oTRSButtons = new CTRSButtons();
					oTRSButtons.addTRSButton("新建", "addNew();", "../images/button_new.gif", "新建Logo");
					oTRSButtons.addTRSButton("删除", "deleteLogo(getLogoIds());", "../images/button_delete.gif", "删除选定的Logo");
					oTRSButtons.addTRSButton("刷新", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "刷新当前页面");
					oTRSButtons.draw();	
				</script>
				</TD>
			</TR>
			<TR>
				<TD align="left" valign="top">
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="1"
					class="list_table">
					<TR bgcolor="#BEE2FF" class="list_th">
						<TD width="40" height="20" NOWRAP><a
							href="javascript:TRSHTMLElement.selectAllByName('LogoIds');">全选</a></TD>						

						<TD bgcolor="#BEE2FF">文件名</TD>
						

						<TD bgcolor="#BEE2FF">Logo</TD>
						

						<TD bgcolor="#BEE2FF">操作</TD>
					</TR>
		<%
			Logo currLogo = null;
			for(int i=1, nSize=currLogos.size(); i<=nSize; i++)
			{//begin for
				try{
					currLogo = (Logo)currLogos.getAt(i-1);
				} catch(Exception ex){
					throw new WCMException("获取第["+i+"]篇Logo失败！", ex);
				}
				if(currLogo == null){
					throw new WCMException("没有找到第["+i+"]篇Logo！");
				}

				try{
		%>
					<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
						<TD width="40" NOWRAP><INPUT TYPE="checkbox" NAME="LogoIds"
							VALUE="<%=currLogo.getId()%>"><%=i%></TD>
						<TD><%=currLogo.getFileName()%></TD>

						<TD align=center><a href="../file/read_image.jsp?FileName=<%=currLogo.getFileName()%>" target="_blank" title="点击查看原图"><img border=0  width="50" height="50" src="../file/read_image.jsp?FileName=<%=currLogo.getFileName()%>"></a></TD>
						

						<TD align="center">&nbsp;<A title=""
							onclick="edit(<%=currLogo.getId()%>);return false;"
							href="#">更新</A> | <A title="重新排列Logo的顺序" 
							onclick="sort(<%=currLogo.getId()%>, <%=i%>);return false;"
							href="#">排序</A></TD>
					</TR>
		<%
				} catch(Exception ex){
					throw new WCMException("获取第["+i+"]篇Logo的属性失败！", ex);
				}
			}//end for	
		%>
				</TABLE>
				</TD>
			</TR>			
		</TABLE>
		</TD>
	</TR>
</TABLE>
<script>
//注册页面使用的Dialog
var DIALOG_ADD_FILE = "AddFileDialog";
var DIALOG_EDIT_FILE = "EditFileDialog";
var DIALOG_SORT = "SortDialog";

TRSDialogCotainer.register(DIALOG_ADD_FILE, '上传一个Logo图片', '../file/file_upload.jsp?AllowExt=jpg,gif,bmp,png', '300px', '130px');
TRSDialogCotainer.register(DIALOG_EDIT_FILE, '修改当前Logo图片', '../file/file_upload.jsp?AllowExt=jpg,gif,bmp,png', '300px', '130px');
TRSDialogCotainer.register(DIALOG_SORT, '修改Logo顺序', '../logo/logo_sort.html', '300px', '130px');


//实现Dialog要求的方法
function notifyParentOnFinishedImpl(_sOperationName, _args){
	if(_args == null || _args.length<=0){
		return;
	}

	switch(_sOperationName){
	case DIALOG_ADD_FILE :
	case DIALOG_EDIT_FILE:
		var oTRSAction = new CTRSAction("../logo/logo_addedit_dowith.jsp");
		oTRSAction.setParameter("LogoId", m_nLogId);
		oTRSAction.setParameter("HostDesc", "<%=CMyString.filterForJs(sHostDesc)%>");
		oTRSAction.setParameter("HostType", <%=nHostType%>);
		oTRSAction.setParameter("HostId", <%=nHostId%>);
		oTRSAction.setParameter("FileName", _args[0]);

		oTRSAction.doAction();
		break;
	case DIALOG_SORT :	
		var oTRSAction = new CTRSAction("../logo/logo_addedit_dowith.jsp");
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
	}
	
	
	function edit(_nLogoId){	
		m_nLogId = _nLogoId;
		TRSDialogCotainer.display(DIALOG_EDIT_FILE);
		
	}

	function sort(_nLogoId, _nIndex){
		m_nLogId = _nLogoId;

		var args = [m_nSize, _nIndex];

		TRSDialogCotainer.display(DIALOG_SORT, args);
		
	}
	
	function deleteLogo(_sLogoIds){
		//参数校验
		if(_sLogoIds == null || _sLogoIds.length <= 0){
			alert("请选择需要删除的Logo!");
			return;
		}
		if(!CTRSAction_confirm("您确定需要删除这些Logo吗?"))
			return;
		
		var oTRSAction = new CTRSAction("logo_delete.jsp");
		oTRSAction.setParameter("LogoIds", _sLogoIds);		
		oTRSAction.doDialogAction(500, 200);
		CTRSAction_refreshMe();
	}
</script>

</BODY>
</HTML>
<%
//6.资源释放
	currLogos.clear();
%>