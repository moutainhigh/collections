<%--
/** Title:			rightdef_list.jsp
 *  Description:
 *		权限列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-06 11:36:59
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-06 / 2005-04-06
 *	Update Logs:
 *		NZ@2005-04-06 产生此文件
 *
 *  Parameters:
 *		see rightdef_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.RightDef" %>
<%@ page import="com.trs.cms.auth.persistent.RightDefs" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());

	int nObjType = currRequestHelper.getInt("ObjType",0);

//5.权限校验
	if (!AuthServer.hasRight(loginUser,null,WCMRightTypes.SYS_RIGHTDEF)){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "您没有权限查看权限列表！");
	}
//6.业务代码
	String sWhere = "";
	if(nObjType>0) {
		sWhere = "OBJTYPE="+nObjType;
	}

	String sSelectFields = "OBJTYPE,RIGHTINDEX,RIGHTNAME,RIGHTDESC,SYSDEFINED";
	WCMFilter filter = currRequestHelper.getPageFilter(new WCMFilter("", sWhere, ""));

	if(IS_DEBUG) {
		System.out.println("where:"+sWhere);
		System.out.println("order:"+currRequestHelper.getOrderSQL());
	}
	

	RightDefs currRightDefs = RightDefs.openWCMObjs(loginUser, filter);

	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currRightDefs.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2 <%=LocaleServer.getString("auth.label.list", "权限列表")%>页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
<%@include file="../include/public_client_list.jsp"%>
</head>
<BODY topmargin="0" leftmargin="0" style="margin:5px">	
	
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
<!--~--- ROW1 ---~-->
<TR>
  <TD height="26" class="head_td" align=left>
	<TABLE width="100%" height="26" border="0" cellpadding="0" cellspacing="0">
	<TR>
		<TD width="24"><IMG src="../images/bite-blue-open.gif" width="24" height="24"></TD>
		<TD width="235"><%=LocaleServer.getString("auth.label.list", "权限列表")%></TD> 
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
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.add", "新建")%>", "addNew();", "../images/button_new.gif", "<%=LocaleServer.getString("auth.tip.add", "新建权限")%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.drop", "删除")%>", "deleteRightDef(getRightDefIds());", "../images/button_delete.gif", "<%=LocaleServer.getString("auth.tip.drop", "删除选定的权限")%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("auth.tip.refresh", "刷新当前页面")%>");
				oTRSButtons.draw();	
			</script>      
      </TD>
	  <TD width="400" nowrap>
	  <form name="frmSearch" onsubmit="RightDef_doSearch(this);return false;">
	    <%=LocaleServer.getString("auth.label.objtype", "对象类型")%>：
	    <select name="ObjType">
			<option value="0">所有</option>
			<option value="<%=WebSite.OBJ_TYPE%>"><%=WCMTypes.getObjName(WebSite.OBJ_TYPE, true)%></option>
			<option value="<%=Channel.OBJ_TYPE%>"><%=WCMTypes.getObjName(Channel.OBJ_TYPE, true)%></option>
			<option value="<%=Document.OBJ_TYPE%>"><%=WCMTypes.getObjName(Document.OBJ_TYPE, true)%></option>
			<option value="1">系统属性</option>
		</select>
		&nbsp;&nbsp;&nbsp;
		<select name="SearchKey">
			<option value="RIGHTNAME,RIGHTDESC">全部</option>
			<option value="RIGHTNAME" <%=("RIGHTNAME".equals(currRequestHelper.getSearchKey()))?"selected":""%>>权限名称</option>
			<option value="RIGHTDESC" <%=("RIGHTDESC".equals(currRequestHelper.getSearchKey()))?"selected":""%>>权限说明</option>
		</select>
		<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
		<input type="submit" value="<%=LocaleServer.getString("system.button.search", "检索")%>">		
	  </form>
	  </TD>
	  <script>
	    document.frmSearch.ObjType.value = "<%=nObjType%>";
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
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
    <!--~-- ROW11 --~-->
    <TR bgcolor="#BEE2FF" class="list_th">
		<TD width="40" height="20" NOWRAP><a href="javascript:TRSHTMLElement.selectAllByName('RightDefIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
		
		
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("OBJTYPE", LocaleServer.getString("auth.label.objtype", "对象类型"), sOrderField, sOrderType)%></TD>
		

		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("RIGHTINDEX", LocaleServer.getString("auth.label.index", "权限索引"), sOrderField, sOrderType)%></TD>
		

		<TD bgcolor="#BEE2FF" nowrap><a href="javascript:TRSHTMLElement.selectAllByName('RightDefIds');"><%=PageViewUtil.getHeadTitle("RIGHTNAME", LocaleServer.getString("auth.label.name", "权限名称"), sOrderField, sOrderType)%></a></TD>
		

		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("RIGHTDESC", LocaleServer.getString("auth.label.desc", "权限说明"), sOrderField, sOrderType)%></TD>
		

		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("SYSDEFINED", LocaleServer.getString("auth.label.sysdefined", "系统定义权限"), sOrderField, sOrderType)%></TD>
		

		<TD bgcolor="#BEE2FF" nowrap><%=LocaleServer.getString("system.label.modify", "修改")%></TD>
    </TR>
    <!--~ END ROW11 ~-->
	<%
	RightDef currRightDef = null;
	for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
	{//begin for
		try{
			currRightDef = (RightDef)currRightDefs.getAt(i-1);
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取第["+i+"]篇权限失败！", ex);
		}
		if(currRightDef == null) continue;

		try{
%>
	<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
		<TD width="40" NOWRAP><INPUT TYPE="checkbox" NAME="RightDefIds" VALUE="<%=currRightDef.getId()%>" <%=(currRightDef.isSysDefined())?"disabled":""%>><%=i%></TD>
		
		
		<TD align="center"><%=PageViewUtil.toHtml((currRightDef.getObjType()==1)?"系统属性":WCMTypes.getObjName(currRightDef.getObjType(), true))%></TD>
		

		<TD align="center"><%=PageViewUtil.toHtml(currRightDef.getPropertyAsString("RIGHTINDEX"))%></TD>
		

		<TD><A onclick="viewRightDef(<%=currRightDef.getId()%>)" href="#"><%=PageViewUtil.toHtml(currRightDef.getPropertyAsString("RIGHTNAME"))%></A></TD>
		

		<TD><%=PageViewUtil.toHtml(currRightDef.getPropertyAsString("RIGHTDESC"))%></TD>
		

		<TD align="center"><%=currRightDef.isSysDefined()?"是":"否"%></TD>
		

		<TD align="center">&nbsp;<A onclick="edit(<%=currRightDef.getId()%>);return false;" href="#"><IMG border="0" src="../images/icon_edit.gif"></A></TD>
	</TR>
<%
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "获取第["+i+"]篇权限的属性失败！", ex);
		}
	}//end for	
%>
    </TABLE>
    <!--~ END TABLE9 ~-->
    </TD>
  </TR>
  <!--~ END ROW10 ~-->
  <!--~-- ROW32 for PageIndex --~-->
  <TR>
    <TD class="navigation_page_td" valign="top">
	<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), "权限配置", "项")%>
    </TD>
  </TR>
  <!--~ END ROW32 for PageIndex ~-->
  </TABLE>
  <!--~ END TABLE3 ~-->
  </TD>
</TR>
<!--~- END ROW3 -~-->
</TABLE>
<script>
	function getRightDefIds(){
		return TRSHTMLElement.getElementValueByName('RightDefIds');
	}

	function addNew(){
		var sURL		= "rightdef_addedit.jsp";

		var oTRSAction = new CTRSAction(sURL);
		var bResult = oTRSAction.doDialogAction(500, 450);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}

	function edit(_nRightDefId){	
		var oTRSAction = new CTRSAction("rightdef_addedit.jsp");
		oTRSAction.setParameter("RightDefId", _nRightDefId);
		var bResult = oTRSAction.doDialogAction(500, 450);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}
	
	function deleteRightDef(_sRightDefIds){
		//参数校验
		if(_sRightDefIds == null || _sRightDefIds.length <= 0){
			CTRSAction_alert("请选择需要删除的权限!");
			return;
		}
		if(!CTRSAction_confirm("您确定需要删除这些权限吗?"))
			return;
		
		var oTRSAction = new CTRSAction("rightdef_delete.jsp");
		oTRSAction.setParameter("RightDefIds", _sRightDefIds);		
		oTRSAction.doDialogAction(500, 300);
		CTRSAction_refreshMe();
	}
	
	function RightDef_doSearch(_oForm){
		var oForm = _oForm;
		if(_oForm == null)
			oForm = document.frmSearch;
		if(oForm.SearchKey == null || oForm.SearchValue == null){
			CTRSAction_alert("Search Form Invalid!");
			return;
		}

		var oTRSAction = new CTRSAction();
		
		oTRSAction.setParameter("SearchKey", oForm.SearchKey.value);
		oTRSAction.setParameter("SearchValue", oForm.SearchValue.value);
		
		oTRSAction.setParameter("PageIndex", 1);
		
		oTRSAction.setParameter("ObjType", oForm.ObjType.value);
		
		oTRSAction.doAction();
	}

	function viewRightDef(nRightDefId){

		var oTRSAction = new CTRSAction("../auth/rightdef_show.jsp");
		oTRSAction.setParameter("RightDefId", nRightDefId);
		oTRSAction.doOpenWinAction(400,300);
	}

</script>
</BODY>
</HTML>
<%
//6.资源释放
	currRightDefs.clear();
%>