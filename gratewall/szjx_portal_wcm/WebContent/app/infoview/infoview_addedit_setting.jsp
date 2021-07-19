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
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewViews" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%-- ----- WCM IMPORTS END ---------- --%>
<%@include file="./infoview_public_include.jsp"%>
<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	IInfoViewService currService = ServiceHelper.createInfoViewService();
	int nInfoViewId = currRequestHelper.getInt("InfoViewId", 0);
	InfoView infoview = currService.findById(nInfoViewId);
	if (infoview == null) {
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("infoview_addedit_setting.jsp.fail2_get_infoviewId", "获取ID为[{0}]的自定义表单失败！"), new int[]{nInfoViewId}));
	}

	String strTitlePattern = infoview.getTitlePattern();
	if(strTitlePattern == null)
		strTitlePattern = "";

	int nHasDocSerial = infoview.hasDocSerial()? 1 : 0;
	String strSerialPattern = infoview.getSerialPattern();
	if(strSerialPattern == null)
		strSerialPattern = "";
	int nSerialPeriod = infoview.getSerialPeriod();
	String strSerialField = infoview.getSerialField();
	if(strSerialField == null) {
		strSerialField = "";
	}

	int nIsNoticeRequired = infoview.isReplyNoticeRequired()?1:0;
	String strNotcieFiled = CMyString.showNull(infoview.getNoticeRelatedField(),"");
	String strSubjectPattern = CMyString.showNull(infoview.getNoticeSubjectPattern(),"");
	String strContentPattern = CMyString.showNull(infoview.getNoticeContentPattern(),"");
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM <%= LocaleServer.getString("infoview.label.addedit_of_infoview", "添加/修改自定义表单") %>:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<SCRIPT>
var m_nInfoViewId = <%= nInfoViewId %>;
function onOk() {
	doValidTitlePattern();
	var postdatas = getPostData(frmData);
	new ajaxRequest({
		url : './infoview_save_info.jsp?r=' + new Date().getTime(),
		method : 'post',
		parameters : $toQueryStr({
			ObjectXML : postdatas,
			InfoviewId : $('InfoViewId').value
		}),
		onSuccess : function(_trans, _json){
			 window.close();
		}
	});
}
function getPostData(frmData){
	var inputElements = frmData.getElementsByTagName('input');
	var result = ['<WCMOBJ><PROPERTIES>'];
	for (var i = 0; i < inputElements.length; i++){
		var element = inputElements[i];
		var name = element.name.toUpperCase();
		result.push("<", name, "><![CDATA[", element.value, "]]></", name, ">");
	}
	result.push('</PROPERTIES></WCMOBJ>');
	return result.join("");
}
function $toQueryStr(_oParams){
	var arrParams = _oParams || {};
	var rst = [], value;
	for (var param in arrParams){
		value = arrParams[param];
		rst.push(param + '=' + value);
	}
	return rst.join('&');
}
function doValidTitlePattern(){
	var title = $('TitlePattern').value;
	if(byteLength(title)>200 && title.indexOf('$')>0){
		var a = title.split("$");
		var lastStrlength = parseInt(a[(a.length-1)].length) + 1;
		$('TitlePattern').value = title.substring(0, title.length - lastStrlength);
		if(byteLength($('TitlePattern').value)>200){
			doValidTitlePattern();
		}
	}
	return;
}
function byteLength(str){
	var length = 0;
	str.replace(/[^\x00-\xff]/g,function(){length++;});
	return str.length+length;
}
</SCRIPT>
<style type="text/css">
body, td{font-size:10pt;}
body{padding:0;margin:0;width:100%;height:100%;overflow:hidden;}
.ctrsbtn_left{background:url(../../images/button_left.gif) no-repeat 0 0;height:24px;}
.ctrsbtn_right{background:url(../../images/button_right.gif) no-repeat right 0;height:24px;line-height:24px;}
.ctrsbtn{cursor:pointer;background:url(../../images/button_bg_line.gif) repeat-x 4px 0;font-size:12px;height:24px;text-align:center;margin:0 5px;width:90px;display:inline-table!important;display:inline;}
#dvAdvance{background:url(../images/icon/config.gif) no-repeat 0 bottom;color:red;cursor:pointer;margin-right:20px;padding:16px 0 0 20px;font-weight:bold;}
.downbutton{background:url(infoview/DownButton.gif) no-repeat 0 bottom;cursor:pointer;padding:12px 0 0 12px;}
#dvAdvSetting{position:absolute;background:#EFEFEF;border:1px solid gray;width:130px;text-align:left;padding:5px;cursor:pointer;}
#dvAdvSetting:focus{outline:0;}
#titleSystemMenu{position:absolute;background:#EFEFEF;border:1px solid gray;width:180px;text-align:left;padding:5px;cursor:pointer;}
#titleSystemMenu:focus{outline:0;}
.ext-gecko #titleSystemMenu{width:150px;}
.menuitem{line-height:20px;font-size:14px;height:20px;}
.menuitem_active{background:#FFFFEF;font-weight:bold;}
.cbd-btn{width:60px;border:1px solid gray;height:22px;line-height:24px;cursor:pointer;}
.curr-view .c{background:#FFFFFF; font:bold 10pt; color:#000000; cursor:default; border-bottom:1pt solid #000000; border-top:1pt solid #FFFFFF;}
.deactive-view .c{background:#ECE9D8; font:bold 10pt; color:#933A93; cursor:hand; border-bottom:1pt solid #000000; border-top:0pt solid #000000;}
.deactive-view .l{padding-left:8px;background:url(sheet_close_head.gif) no-repeat left center;}
.deactive-view .r{padding-right:8px;background:url(sheet_close_tail.gif) no-repeat right center;}
.curr-view .l{padding-left:8px;background:url(sheet_open_head.gif) no-repeat left center;}
.curr-view .r{padding-right:8px;background:url(sheet_open_tail.gif) no-repeat right center;}
.curr-view,.deactive-view{float:left;}
.publishable,.isdefault,.unpublishable,.unisdefault{display:inline;padding:0 10px;width:0;height:0;}
.publishable,.isdefault{background:url(ok.gif) no-repeat 2px center;}
#bubble-panel:focus{outline:0;}
#bubble-panel{border:1px solid gray;border:1px solid gray;background:#ECECEC;padding:5px 0;cursor:pointer;width:120px;}
#bubble-panel .menuitem{padding:4px 5px 4px 15px;font-size:10pt;}
#bubble-panel .menuitem_active{background:#FFFFEF;font-weight:normal;}
</style>
<link href="../../app/js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css" />
<script>
var m_myArrBtns = [{
		html : "<%=LocaleServer.getString("system.button.onok", "确定")%>",
		action : onOk,
		id : 'preview_1'
	},{
		html : "<%=LocaleServer.getString("system.button.oncancel", "取消")%>",
		action : function(){
			window.close();
		},
		id : 'saveexit'
	}
];
document.title = '<%=LocaleServer.getString("infoview.label.setting", "设置表单属性")%>';
</script>
</HEAD>

<BODY onUnload="unlock(<%= nInfoViewId %>,<%= InfoView.OBJ_TYPE %>);">
<FORM NAME="frmSubmit" ID="frmSubmit" METHOD=POST ACTION="./infoview_save_info.jsp" style="margin-top:0;display:none">
	<INPUT TYPE="hidden" NAME="InfoViewId" Value="<%= nInfoViewId %>">
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</FORM>
<FORM NAME="frmData" ID="frmData" style="margin:0;padding:0;width:100%;height:100%" onsubmit="return false">
<input type="hidden" name="HasDocSerial" id="HasDocSerial" value="<%=nHasDocSerial%>">
<input type="hidden" name="SerialPattern" id="SerialPattern" value="<%=strSerialPattern%>">
<input type="hidden" name="SerialPeriod" id="SerialPeriod" value="<%=nSerialPeriod%>">
<input type="hidden" name="SerialField" id="SerialField" value="<%=strSerialField%>">
<input type="hidden" name="ReplyNoticeRequired" id="ReplyNoticeRequired" value="<%=nIsNoticeRequired%>">
<input type="hidden" name="NoticeRelatedField" id="NoticeRelatedField" value="<%=strNotcieFiled%>">
<input type="hidden" name="NoticeSubjectPattern" id="NoticeSubjectPattern" value="<%=strSubjectPattern%>">
<input type="hidden" name="NoticeContentPattern" id="NoticeContentPattern" value="<%=strContentPattern%>">
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" id="action_body">
	<TR height="29">
		<TD width="80%">
			<%=LocaleServer.getString("infoview.label.doctitle", "标题")%>：&nbsp;&nbsp;
			<input type="text" name="TitlePattern" id="TitlePattern" style="width:80%;height:18px;border:1px solid gray;" value="<%=strTitlePattern%>">
			<span class="downbutton" _action="titlesystemmenu" title="<%=LocaleServer.getString("infoview.label.settitlepattern", "设置标题模式串")%>">&nbsp;</span>
			<DIV style="display:none;" id="titleSystemMenu">
				<DIV class="menuitem" unselectable="on" _action="titlesetting" _pattern="${::user}">
					<%=LocaleServer.getString("infoview.patterndesc.user", "用户名")%>
				</DIV>
				<DIV class="menuitem" unselectable="on" _action="titlesetting" _pattern="<%=LocaleServer.getString("infoview.titlepattern.time1", "${::time,yyyy年MM月dd日}")%>">
					<%=LocaleServer.getString("infoview.patterndesc.time1", "时间:YYYY年MM月DD日")%>
				</DIV>
				<DIV class="menuitem" unselectable="on" _action="titlesetting" _pattern="<%=LocaleServer.getString("infoview.titlepattern.time2", "${::time,yy年MM月dd日}")%>">
					<%=LocaleServer.getString("infoview.patterndesc.time2", "时间:YY年MM月DD日")%>
				</DIV>
				<DIV class="menuitem" unselectable="on" _action="titlesetting" _pattern="<%=LocaleServer.getString("infoview.titlepattern.time3", "${::time,yyyy-MM-dd}")%>">
					<%=LocaleServer.getString("infoview.patterndesc.time3", "时间:YYYY-MM-DD")%>
				</DIV>
				<DIV class="menuitem" unselectable="on" _action="titlesetting" _pattern="<%=LocaleServer.getString("infoview.titlepattern.time4", "${::time,yyyyMMdd}")%>">
					<%=LocaleServer.getString("infoview.patterndesc.time4", "时间:YYYYMMDD")%>
				</DIV>
			</DIV>
		</TD>
		<TD align="right">
			<SPAN id="dvAdvance" _action="toggleAdvance"><%=LocaleServer.getString("infoview.lable.advsetting", "表单高级设置")%></SPAN>
			<DIV style="display:none;" id="dvAdvSetting">
				<DIV class="menuitem" unselectable="on" _action="serialcode" title="<%=LocaleServer.getString("infoview.advsettingdesc.serialcode", "设置自定义表单的编号管理机制")%>">
					<%=LocaleServer.getString("infoview.advsetting.serialcode", "表单编号设置")%>
				</DIV>
				<DIV class="menuitem" unselectable="on" _action="viewsetting" title="<%=LocaleServer.getString("infoview.advsettingdesc.viewsetting", "为应用表单的栏目设置缺省的自定义视图")%>">
					<%=LocaleServer.getString("infoview.advsetting.viewsetting", "自定义视图设置")%>
				</DIV>
				<DIV class="menuitem" unselectable="on" _action="searchsetting" title="<%=LocaleServer.getString("infoview.advsettingdesc.searchsetting", "设置检索字段构造高级检索页面")%>">
					<%=LocaleServer.getString("infoview.advsetting.searchsetting", "检索字段设置")%>
				</DIV>
				<DIV class="menuitem" unselectable="on" _action="ordersetting" title="设置列表页面默认排序字段">
					排序字段设置
				</DIV>
			</DIV>
		</TD>
	</TR>
	<TR>
		<TD id="infoview" width="80%">
		</TD>
		<TD id="infoview_fields" width="20%">
			<iframe id="fields_tree" src="infoview_fields_tree.jsp?InfoViewId=<%=nInfoViewId%>" style="width:100%;height:100%;" scrolling="no" frameborder="0"></iframe>
		</TD>
	</TR>
	<TR height="32" style="padding-top:5px;">
		<TD align="center" valign="top" id="btns_td" colspan="2"></TD>
	</TR>
</TABLE>
</FORM>
<form name="frmAction" id="frmAction" style="display:none">
	<input name="ChannelId" type="hidden">
	<input name="DocumentId" type="hidden">
	<input name="ViewMode" type="hidden" value="1">
	<input id="InfoViewId" name="InfoViewId" type="hidden" value="<%=nInfoViewId%>">
	<textarea style="display:none" name="ObjectXML"><%= infoview.getTemplateFileContent() %></textarea>
</form>
<div id=bubble-panel style="display: none; z-index: 999; position: absolute" _fxtype="bubble-panel"></div>
<div id=fly-button style="border:0;display:none; z-index: 99; cursor: pointer; position: absolute" _fxtype="fly-button"><img src="infoview/DownButton.gif" border=0></div>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<script src="../../app/js/easyversion/crashboard.js"></script>
<script src="../../app/js/easyversion/ctrsbutton.js"></script>
<script src="../../app/js/easyversion/bubblepanel.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/position.js"></script>
<script src="infoview_multiview.js"></script>
<script src="infoview_addedit_setting.js"></script>
<script language="javascript">
	function unlockObj(_nObjId, _nObjType){
		var postData = {
			ObjId :  _nObjId,
			ObjType : _nObjType
		};
		var sRequestUrl = '../include/object_unlock.jsp';
		new ajaxRequest({
			url : sRequestUrl,
			method : 'post',
			parameters : $toQueryStr(postData),
			onSuccess : function(_transport){
			},
			onFailure : function(_transport){
				 alert('<%=LocaleServer.getString("infoview.label.unlockerror", "解锁失败")%>');
			}
		});
	}
	function unlock(_nObjId, _nObjType){
		if(_nObjId>0) {
			unlockObj(_nObjId, _nObjType);
		}
	}

	var oTRSButton = new CTRSButton('btns_td');
	oTRSButton.setButtons(m_myArrBtns);
	oTRSButton.init();

	Event.observe(window,'load',function(){
		window.focus();
	});
</script>
</BODY>
</HTML>