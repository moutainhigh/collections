<%--
/** Title:			Filter_list.jsp
 *  Description:
 *		Filter 列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2011-07-03 00:24:48
 *  Vesion:			1.0
 *  Last EditTime:	2011-07-03 / 2011-07-03
 *	Update Logs:
 *		TRS WCM 5.2@2011-07-03 产生此文件
 *
 *  Parameters:
 *		see filter_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.filter.Filter" %>
<%@ page import="com.trs.components.wcm.filter.Filters" %>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.CPager"%>
<%@ page import="com.trs.presentation.util.PageHelper"%>
<%@ page import="com.trs.presentation.util.PageViewUtil"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.filter.FilterMgr"%>
<%@ page import="com.trs.DreamFactory"%>

<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	int nHostId	= currRequestHelper.getInt("HostId", 0);
	String sHostDesc = "";
	Channel oChannel = Channel.findById(nHostId);
	if(oChannel!=null){
		sHostDesc = oChannel.getDesc() + "-" + nHostId;
	}

	String sWhere = currRequestHelper.getWhereSQL();
	WCMFilter filter = new WCMFilter("", sWhere, "");

	FilterMgr currFilterMgr = (FilterMgr)DreamFactory.createObjectById("FilterMgr");
	Filters currFilters = currFilterMgr.queryByChannel(oChannel, filter);
	
	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currFilters.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="filter_list.jsp.trswcmfilterlistpage">TRS WCM ::::::筛选器列表页面</title>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="../../app/js/resource/widget.css">
<style type="text/css">
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
	font-size:14px;
	height:22px;
	vertical-align:bottom;
}
</style>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHashtable.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSRequestParam.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSAction.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLTr.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLElement.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></SCRIPT>

<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanel.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<!-- Component Start -->
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<!-- Component End -->
<%=currRequestHelper.toTRSRequestParam()%>
</head>
<BODY topmargin="0" leftmargin="0" style="overflow:hidden">

<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="1" class="list_table" valign="top">
	<TR>
		<TD height="26" class="head_td">
		<TABLE width="100%" height="26" border="0" cellpadding="0"
			cellspacing="0">
			<TR>
				<TD width="24"><IMG src="../../images/bite-blue-open.gif" width="24"
					height="24"></TD>
				<TD width="245">&nbsp;&nbsp;<span WCMAnt:param="filter_list.jsp.filterlistmgr">筛选器列表管理</span>&nbsp;[<span title="<%=CMyString.filterForHTMLValue(sHostDesc)%>"><%=CMyString.transDisplay(sHostDesc)%></span>]&nbsp;</TD>
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
				<TD height="25">
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
					<TR>
						<TD align="left" valign="top">
							<script>
								//定义一个单行按钮
								var oTRSButtons = new CTRSButtons();
								oTRSButtons.addTRSButton("新建", "addNew();", "../../images/button_new.gif", "新建筛选器");
								oTRSButtons.addTRSButton("删除", "deleteFilter(getFilterIds());", "../../images/button_delete.gif", "删除选定的Filter");
								oTRSButtons.addTRSButton("复制", "copyFilter(getFilterIds());", "../../images/button_copy.gif", "复制选定的Filter");
								oTRSButtons.addTRSButton("导入", "importFilter();", "../../images/button_import.gif", "导入Filter");
								oTRSButtons.addTRSButton("导出", "exportFilter(getFilterIds());", "../../images/button_export.gif", "导出选定的Filter");
								oTRSButtons.addTRSButton("生成筛选器", "createXML(getFilterIds());", "../../images/button_publishinc.gif", "为筛选器生成xml");
								oTRSButtons.addTRSButton("生成预览筛选器", "createPreviewXML(getFilterIds());", "../../images/button_publishinc.gif", "为筛选器生成xml");
								oTRSButtons.addTRSButton("刷新", "CTRSAction_refreshMe();", "../../images/button_refresh.gif", "刷新当前页面");
								oTRSButtons.draw();	
							</script>
						</TD>
						<TD width="270" nowrap>
							<form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">
								&nbsp; <input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
								<select name="SearchKey">
									<option value="FILTERNAME" WCMAnt:param="filter_list.jsp.all">全部</option>
									<option value="FILTERNAME" WCMAnt:param="filter_list.jsp.filtername">筛选器名称</option>
								</select> 
								<input type="submit" value="检索" WCMAnt:paramattr="value:filter_list.jsp.search">
							</form>
						</TD>						
						<script>
							document.frmSearch.SearchKey.value = "<%=currRequestHelper.getSearchKey()%>";
						</script>
					</TR>
				</TABLE>
				</TD>
			</TR>
			<TR>
				<TD align="left" valign="top" height="100%" width="100%">
		<div style="width:100%;height:100%;overflow:auto;">
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="1"
					class="list_table">
					<TR bgcolor="#BEE2FF" class="list_th">
						<TD width="40" height="20" NOWRAP><a
							href="javascript:TRSHTMLElement.selectAllByName('FilterIds');"  WCMAnt:param="filter_list.jsp.selectall">全选</a></TD>
						<TD bgcolor="#BEE2FF" width="100" WCMAnt:param="filter_list.jsp.modify">修改</TD>
						<TD bgcolor="#BEE2FF" WCMAnt:param="filter_list.jsp.filtername">筛选器名称</TD>
						<TD bgcolor="#BEE2FF" WCMAnt:param="filter_list.jsp.viewname">视图名称</TD>
					</TR>
		<%
			Filter currFilter = null;
			for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
			{//begin for
				try{
					currFilter = (Filter)currFilters.getAt(i-1);
				} catch(Exception ex){
					throw new WCMException(CMyString.format(LocaleServer.getString("filter_list.jsp.getnofilterfail", "获取第[{0}]篇筛选器失败！"), new int[]{i}), ex);
				}
				if(currFilter == null){
					throw new WCMException(CMyString.format(LocaleServer.getString("filter_list.jsp.notfindnofilter", "没有找到第[{0}]篇筛选器！"), new int[]{i}));
				}

				try{
		%>
					<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
						<TD width="40" NOWRAP><INPUT TYPE="checkbox" NAME="FilterIds"
							VALUE="<%=currFilter.getId()%>"><%=i%></TD>
						<TD align="center">&nbsp;<A
							onclick="edit(<%=currFilter.getId()%>);return false;"
							href="#"><IMG border="0" src="../../images/icon_edit.gif"></A></TD>
						<TD><a href="#" onclick="editDetail(<%=currFilter.getId()%>);return false;"><%=PageViewUtil.toHtml(currFilter.getFilterName())%></a></TD>
						<TD width="300"><%=PageViewUtil.toHtml(currFilter.getViewName())%></TD>
					</TR>
		<%
				} catch(Exception ex){
					throw new WCMException(CMyString.format(LocaleServer.getString("filter_list.jsp.getnofilterattrfail", "获取第[{0}]篇Filter的属性失败！"), new int[]{i}), ex);
				}
			}//end for	
		%>
				</TABLE>
	</div>
				</TD>
			</TR>
			<TR>
				<TD class="navigation_page_td" valign="top">
					<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 20), LocaleServer.getString("filter_list.jsp.filter","筛选器"), LocaleServer.getString("filter_list.jsp.ge","个"))%>
				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
<script>
	var m_nChannelId = <%=nHostId%>;
	function getFilterIds(){
		return TRSHTMLElement.getElementValueByName('FilterIds');
	}
	function addNew(){
		var sURL = "filter_addedit.jsp";
		var oTRSAction = new CTRSAction(sURL);
			oTRSAction.setParameter("ChannelId", m_nChannelId);
		var bResult = oTRSAction.doDialogAction(500, 300);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}

	function edit(_nFilterId){	
		var oTRSAction = new CTRSAction("filter_addedit.jsp");
		oTRSAction.setParameter("FilterId", _nFilterId);
		oTRSAction.setParameter("ChannelId", m_nChannelId);
		var bResult = oTRSAction.doDialogAction(500, 300);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}
	function editDetail(_nFilterId){
		var sUrl = "./filter_detail_info.jsp?FilterId=" + _nFilterId
		$openMaxWin(sUrl);
	}
	function createXML(_sFilterIds){
		var oTRSAction = new CTRSAction("create_XML_for_filter.jsp");
		oTRSAction.setParameter("ChannelId", m_nChannelId);
		oTRSAction.setParameter("FilterIds", _sFilterIds);
		oTRSAction.setParameter("IsPreview", "false");
		oTRSAction.doDialogAction(200, 100);
	}

	function createPreviewXML(_sFilterIds){
		var oTRSAction = new CTRSAction("create_XML_for_filter.jsp");
		oTRSAction.setParameter("ChannelId", m_nChannelId);
		oTRSAction.setParameter("FilterIds", _sFilterIds);
		oTRSAction.setParameter("IsPreview", "true");
		oTRSAction.doDialogAction(200, 100);
	}
	function deleteFilter(_sFilterIds){
		//参数校验
		if(_sFilterIds == null || _sFilterIds.length <= 0){
			alert("请选择需要删除的筛选器!");
			return;
		}
		if(!confirm("您确定需要删除这些筛选器吗?"))
			return;
		
		var oTRSAction = new CTRSAction("filter_delete.jsp");
		oTRSAction.setParameter("FilterIds", _sFilterIds);
		oTRSAction.setParameter("ChannelId", m_nChannelId);
		oTRSAction.doDialogAction(200, 100);
		CTRSAction_refreshMe();
	}
	function copyFilter(_sFilterIds){
		if(_sFilterIds == null || _sFilterIds.length <= 0){
			alert("请选择需要复制的筛选器!");
			return;
		}
		var args = {
			IsRadio : 0,
			ExcludeTop : 1,
			ExcludeLink : 1,
			ExcludeVirtual : 0,
			ExcludeInfoView : 1,
			ExcludeOnlySearch : 0,
			ShowOneType : 1,
			SelectedChannelIds : '',
			NotSelect : 1,
			RightIndex : 31,
			canEmpty : false,
			ExcludeSelf : 1,
			CurrChannelId : m_nChannelId
		};
		FloatPanel.open(
			WCMConstants.WCM6_PATH + 'include/channel_select.html?close=1',
			'筛选器复制到...',
			function(selectIds, selectChnlDescs){
				if(!selectIds||selectIds.length==0) {
					Ext.Msg.$alert('请选择当前筛选器要复制到的目标栏目!');
					return false;
				}
				var oPostData = {
					"FilterIds" : _sFilterIds,
					"ChannelIds" : selectIds.join(',')
				};
				var basicDataHelper = new com.trs.web2frame.BasicDataHelper();
				basicDataHelper.call('wcm61_filtercenter','copy', oPostData, true,
					function(_transport,_json){
						//Ext.Msg.report(_json,'筛选器复制结果', func);
						FloatPanel.close();
					},
					function(_transport,_json){
						$render500Err(_transport,_json);
					}
				);
			},
			dialogArguments = args
		);
	}
	function exportFilter(_sFilterIds){
		if(_sFilterIds == null || _sFilterIds.length <= 0){
			alert("请选择需要导出的筛选器!");
			return;
		}
		var basicDataHelper = new com.trs.web2frame.BasicDataHelper();
		basicDataHelper.call('wcm61_filtercenter',"export", {FilterIds : _sFilterIds}, true, function(_oTrans, _json){
			var sFileUrl = _oTrans.responseText;
			var frm = document.getElementById("ifrmDownload");
			if(frm == null) {
				frm = document.createElement('iframe');
				frm.style.height = 0;
				frm.style.width = 0;
				document.body.appendChild(frm);
			}
			sFileUrl = WCMConstants.WCM6_PATH + "file/read_file.jsp?DownName=" + sFileUrl + "&FileName=" + sFileUrl;
			frm.src = sFileUrl;
		});
	}
	function importFilter(){
		FloatPanel.open(WCMConstants.WCM6_PATH + 'filter/filter_import.html?ChannelId=' + m_nChannelId, 
			'筛选器导入',function(){
			CTRSAction_refreshMe();
		});
	}
	function $openMaxWin(_sUrl, _sName, _bResizable){
		var nWidth	= window.screen.width - 12;
		var nHeight = window.screen.height - 60;
		var nLeft	= 0;
		var nTop	= 0;
		var sName	= _sName || "";
		var oWin = window.open(_sUrl, sName, "resizable=" + (_bResizable == true ? "yes" : "no") + ",top=" + nTop + ",left=" + nLeft + ",menubar =no,toolbar =no,width=" + nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no");
		if(oWin)oWin.focus();
	}
	function byteLength(str){
		var length = 0;
		str.replace(/[^\x00-\xff]/g,function(){length++;});
		return str.length+length;
	}
</script>
</BODY>
</HTML>
<%
//6.资源释放
	currFilters.clear();
%>