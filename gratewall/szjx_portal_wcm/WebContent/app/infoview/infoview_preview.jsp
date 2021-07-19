<%--
/** Title:			infoview_addedit.jsp
 *  Description:
 *		WCM5.2 自定义表单的编辑修改的界面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			fcr
 *  Created:		2006.04.12 20:36:04
 *  Vesion:			1.0
 *	Update Logs:
 *		2006.04.12	fcr created
 *
 *  Parameters:
 *		see infoview_addedit.xml
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	int nInfoViewId = currRequestHelper.getInt("InfoViewId", 0);
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="infoview_preview.jsp.title">TRS WCM 预览表单:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<style type="text/css">
body, td{font-size:10pt;}
body{padding:0;margin:0;width:100%;height:100%;overflow:hidden;}
.ctrsbtn_left{background:url(../../images/button_left.gif) no-repeat 0 0;height:24px;}
.ctrsbtn_right{background:url(../../images/button_right.gif) no-repeat right 0;height:24px;line-height:24px;}
.ctrsbtn{cursor:pointer;background:url(../../images/button_bg_line.gif) repeat-x 4px 0;font-size:12px;height:24px;text-align:center;margin:0 5px;width:90px;display:inline-table!important;display:inline;}
.curr-view .c{background:#FFFFFF; font:bold 10pt; color:#000000; cursor:default; border-bottom:1pt solid #000000; border-top:1pt solid #FFFFFF;}
.deactive-view .c{background:#ECE9D8; font:bold 10pt; color:#933A93; cursor:hand; border-bottom:1pt solid #000000; border-top:0pt solid #000000;}
.deactive-view .l{padding-left:8px;background:url(sheet_close_head.gif) no-repeat left center;}
.deactive-view .r{padding-right:8px;background:url(sheet_close_tail.gif) no-repeat right center;}
.curr-view .l{padding-left:8px;background:url(sheet_open_head.gif) no-repeat left center;}
.curr-view .r{padding-right:8px;background:url(sheet_open_tail.gif) no-repeat right center;}
.curr-view,.deactive-view{float:left;}
</style>
</HEAD>

<BODY>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" id="action_body">
	<TR>
		<TD id="infoview">
		</TD>
	</TR>
	<TR height="32">
		<TD align="center" valign="middle" id="btns_td"></TD>
	</TR>
</TABLE>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/easyversion/ctrsbutton.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="infoview_preview.js"></script>
<script src="infoview_multiview.js"></script>
<script language="javascript">
var m_nInfoViewId = <%= nInfoViewId %>;
Event.observe(window, 'load', function(){
	wcm.MultiView.draw('infoview', {
		InfoViewId : m_nInfoViewId,
		ViewMode : 0
	});
});
var m_myArrBtns = [{
		html : '<%=LocaleServer.getString("infoview.preview.closebutton" ,"关闭")%>',
		action : function(){
			window.close();
		},
		id : 'exit'
	}
];
var oTRSButton = new CTRSButton('btns_td');
oTRSButton.setButtons(m_myArrBtns);
oTRSButton.init();
</script>
</BODY>
</HTML>