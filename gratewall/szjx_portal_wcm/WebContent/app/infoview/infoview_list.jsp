<%--
/** Title:			infoview_list.jsp
 *  Description:
 *		WCM5.2 自定义表单的列表页面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			fcr
 *  Created:		2006.04.12 20:36:04
 *  Vesion:			1.0
 *	Update Logs:
 *		2006.04.12	fcr created
 *		2006.05.23	wenyh 添加表单的删除前的提示确认
 *		2006.08.29	wenyh 添加分发发布相关资源文件的方法(相关文件放置在infoview/infoview_pubsrc目录下)
 *
 *  Parameters:
 *		see infoview_list.xml
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%@page import="com.trs.presentation.right.PluginAdminAuth"%>
<%-- ------- WCM IMPORTS END ---------- --%>

<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//1.初始化(获取数据)
//	int nSiteId    = currRequestHelper.getInt("SiteId",    0);
//	if (nSiteId == 0)
//	{
//		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入站点ID错误，必须传入一个有效的站点ID！");
//	}
//	WebSite oWebSite = (WebSite) WebSite.findById(nSiteId);
//	if (oWebSite == null)
//	{
//		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "没有找到ID为" + nSiteId + "]的站点");
//	}

	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());

//2.业务代码
	WCMFilter filter = currRequestHelper.getPageFilter(null);
	InfoViews oInfoViews = m_oInfoViewService.getInfoViews(filter);
	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( oInfoViews.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

//3.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="infoview_list.jap.title">TRS WCM 自定义表单列表::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<link href="../../app/js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.list_th{
	background-image: url(../images/infoview/button-bg.png);
	background-repeat: repeat-x;
	height: 29px;
	font-size: 12px;
	padding: 6px 0;
}
.list_tr, .list_tr_selected{
	height: 29px;		
	line-height: 29px;
	font-family: 'Times New Roman';
}
.tblListHeader{
	background-image: url(../images/infoview/label/label_bg.png);
	text-align: left;
	font-size: 10pt;
	padding: 2px;
}
.grid_row_active{
	background-color:#FFFFCC;
	font-size:14px;
	height:29px;
	line-height: 29px;
	vertical-align:bottom;
	font-family: 'Times New Roman';
}
.toolbar_item{
	cursor:pointer;
	border:1px solid gray; background:url(../../images/button_bg.gif);
	display:inline-block;
	margin-left:5px;
	height:22px;
	border-collapse:collapse
}
html, body{margin:0px;padding:0px;}
body{
	border: 1px solid #9D9D9D;
	border-bottom: 0;
	height:100%;	
	width:99%;
}
#dvBody{
	position:relative;
}
#dvBody>table{
	position:absolute;
	left:0px;
	top:0px;
	right:0px;
	bottom:0px;
	height:auto;
}
#next-addNew{
	width:86px;
}
.ext-gecko #next-addNew{
	width:100px;
}
</style>
<!--[if ie 6]>	
<style type="text/css">
	.wcm-cbd .body .cb-table {
		width:auto !important;
	}
</style>
<![endif]-->
<!--[if ie 8]>	
<style type="text/css">
	.wcm-cbd .body .cb-table {
		width:auto !important;
	}
</style>
<![endif]-->
<!--[if ie 9]>	
<style type="text/css">
	.wcm-cbd .body .cb-table {
		width:auto !important;
	}
</style>
<![endif]-->
<script src="../../app/js/easyversion/cssrender.js"></script>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/easyversion/crashboard.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script> 
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<!-- Component Start -->
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script> 
<!-- Component End -->
<!--ajax相关开始-->
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<!--ajax相关结束-->
<script language="javascript">
<!--
Ext.apply(String.prototype, {
	 parseQuery : function() {
		var pairs = this.match(/^\??(.*)$/)[1].split('&');
		return pairs.inject({}, function(params, pairString) {
			var pair = pairString.split('=');
			params[pair[0]] = pair[1];
			return params;
		});
	}
});
Ext.apply(Array.prototype, {
	each: function(iterator) {
		try {
			for (var i = 0; i < this.length; i++){
				iterator(this[i], i);
			}
		} catch (e) {
			if (e != Ext.$break) throw e;
		}
	},
	inject: function(memo, iterator) {
		this.each(function(value, index) {
			memo = iterator(memo, value, index);
		});
		return memo;
	}
});
function $toQueryStr(_oParams){
	var arrParams = _oParams || {};
	var rst = [], value;
	for (var param in arrParams){
		value = arrParams[param];
		rst.push(param + '=' + encodeURIComponent(value));
	}
	return rst.join('&');
}
function $toQueryStr2(_oParams){
	var arrParams = _oParams || {};
	var rst = [], value;
	for (var param in arrParams){
		value = arrParams[param];
		rst.push(param + '=' + value);
	}
	return rst.join('&');
}
function $openMaxWin(_sUrl, _sName, _bReplace, _bResizable){
	var nWidth	= window.screen.width - 12;
	var nHeight = window.screen.height - 60;
	var nLeft	= 0;
	var nTop	= 0;
	var sName	= _sName || "";
	sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
	var oWin = window.open(_sUrl, sName, "resizable=" 
		+ (_bResizable == true ? "yes" : "no") + ",top=" + nTop + ",left=" + nLeft + ",menubar =no,toolbar =no,width=" 
		+ nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no", _bReplace);
	if(oWin)oWin.focus();
	return oWin;
}
function CTRSAction_gotoPage(pageNumOrEl, event){
	var pageNum;
	if(!(typeof(pageNumOrEl) == 'number')){
		event = event || window.event;
		switch(event.type){
			case 'blur':
				pageNum = parseInt(pageNumOrEl.value);
				if(isNaN(pageNum)){pageNumOrEl.value=''; return;}
				break;
			case 'keydown':
				if(event.keyCode==13){
					pageNumOrEl.blur();
					return;
				}
//				Event.stop(event);
				return;
				break;
				
		}
		
	}
	else pageNum = pageNumOrEl;
	var json = getUrlJson();
	json["PageIndex"] = pageNum;
	pageInfoDoSearch(json);
}
 
function doPageItemChange(el){
	var json = getUrlJson();
	var sValue = document.getElementsByName('pageItemCount')[1].value;
	if(isNaN(sValue)) return false; 
	if(sValue <= 0) return false; 
	json["PageItemCount"] = sValue;
	pageInfoDoSearch(json);
}
function doPageMaxChange(_oEl){
	var json = getUrlJson();
	var sValue = document.getElementsByName('PageMaxCount')[1].value;
	if(isNaN(sValue)) return false; 
	if(sValue <= 0) return false; 
	json["PageMaxCount"] = sValue;
	pageInfoDoSearch(json);
}
function  selectAllByName(_sElementName, _bChecked, _oCheckValue){
		//1.参数校验
		if(_sElementName == null){
			Ext.Msg.alert("Element Name invalid!");
			return;
		}
		//2.获取
		var arElements = document.getElementsByName(_sElementName);	
		if(arElements == null)return;

		if(!arElements.length){//单个元素
			//wsw @ 2005-7-14 修改点击方式，这种方式不会触发背景色的修改
			//arElements.checked = !arElements.checked;
			arElements.click();
			return;
		}
		
		var bSelectAllStatus = false;
		if(arguments.length == 2){
			bSelectAllStatus = _bChecked;
		}else{
			bSelectAllStatus = this.getSelectAllStatus(arElements);
		}
		//3.遍历
		for(var i=0; i<arElements.length; i++){
			if(_oCheckValue == null || typeof _oCheckValue == "undefined"){
				if(bSelectAllStatus != arElements[i].checked)
				arElements[i].click();
			}else{
				if(_oCheckValue.constructor == Array){
					for(var j=0; j<_oCheckValue.length; j++){
						if( arElements[i].value == _oCheckValue[j]){
							arElements[i].checked = _bChecked;	
							break;
						}
					}
				}else if( arElements[i].value == _oCheckValue){
					arElements[i].checked = _bChecked;	
				}
				
			}
			//arElements[i].checked = bSelectAllStatus;
		}
}
function getLength(_str){
	var nTotalLen = 0;
	for (var i=0; i < _str.length;i++){
		var nCharCode = _str.charCodeAt(i);
		if (nCharCode == 38){
			var sPart = _str.substring(i, i + 4);
			var nSkip = 0;
			if (sPart == '&lt;' || sPart == '&gt;') {
				nSkip = 4;
			}else if((sPart = _str.substring(i, i + 6)) == '&quot;'){
				nSkip = 6;
			}
			if (nSkip != 0){
				nTotalLen++;
				i += (nSkip - 1);
				continue;
			}
		}
		if (nCharCode >= 0 && nCharCode <= 128) {
			nTotalLen++;
		}else {
			nTotalLen+= 2;
		}
	} 
	return nTotalLen;	
}
function doSearch(_form){
	var nLen = getLength(_form.SearchValue.value);
	if(nLen > 64) {
		Ext.Msg.alert("<%=LocaleServer.getString("infoview.list.selectdelete.infoview.searcefieldlength", "检索字段长度为['")%>" + nLen + "<%=LocaleServer.getString("infoview.list.selectdelete.infoview.zifulength", "']个字符长度(每1汉字相当于2个字符长度,约定其不应大于[64]！")%>");
		_form.SearchValue.focus();
		_form.SearchValue.select();
		return;
	}
	var json = getUrlJson();
	json["SearchValue"] = _form.SearchValue.value;
	json["SearchTable"] = 'WCMINFOVIEW';
	json["SearchKey"] = _form.SearchKey.value;
	pageInfoDoSearch(json);
}
function viewUseList(_nInfoViewId){
	var cb = wcm.CrashBoard.get({
		id : 'showusedinfo',
		title : '<%=LocaleServer.getString("infoview.list.showaplicationinfo", "查看表单应用情况")%>',
		url : 'infoview_uselist.jsp?InfoViewId=' + _nInfoViewId,
		width: '420px',
		height: '250px'
	});
	cb.show();
}
function edit(_nInfoViewId){
	$openMaxWin('../infoview/infoview_addedit_setting.jsp?InfoViewId='+_nInfoViewId); 
}
function reload(_nInfoViewId){
	var cb = wcm.CrashBoard.get({
		id : 'addNew',
		title : '<%=LocaleServer.getString("infoview.list.addoredit.title", "添加/修改自定义表单")%>',
		url : 'infoview_addedit.jsp?InfoViewId=' + _nInfoViewId,
		width: '570px',
		height: '290px',
		callback : function(){
			location.reload();
		},
		next : '<%=LocaleServer.getString("infoview.list.addoredit.next", "下一步(编辑)")%>'
	});
	cb.show();
	$('next-addNew').style.display = '';
}
function preview(_nInfoViewId){
	$openMaxWin('../infoview/infoview_preview.jsp?InfoViewId='+_nInfoViewId);
}
function onDelete(){
	var sValues = getInfoViewIds('InfoViewIds');
	if (sValues == null || sValues.length == 0){
		Ext.Msg.alert('<%=LocaleServer.getString("infoview.list.selectdeleteinfoview", "请您首先选择要删除的表单！")%>');
		return;
	}
	if (!confirm('<%=LocaleServer.getString("infoview.list.selectdelete.infoview.confirm", "您确定要删除表单吗?")%>')) return;
	new ajaxRequest({
		url : './infoview_delete.jsp',
		method : 'post',
		parameters : $toQueryStr({InfoViewIds : sValues}),
		onSuccess : function(trans){
			//location.reload();
			window.location.href=window.location.href;
		},
		onFailure : function(_transport){
			Ext.Msg.alert('<%=LocaleServer.getString("infoview.list.selectdelete.infoview.deleteinfoviewshibai", "删除表单失败")%>');
		}
	});
	return false;
}
function exportInfoView(){
	var sValues = getInfoViewIds('InfoViewIds')
	if (sValues == null || sValues.length == 0){
		Ext.Msg.alert('<%=LocaleServer.getString("infoview.list.selectexport.infoview.pleaseselect", "请您首先选择要导出的表单!")%>');
		return;
	}
	sValues = sValues.split(",");
	if(sValues.length > 1){
		Ext.Msg.alert('<%=LocaleServer.getString("infoview.list.selectexport.infoview.onlyone", "只允许导出一个表单!")%>');
		return;
	}
	window.open('./infoview_export_xsnfile.jsp?InfoViewId=' + sValues[0]);
}
function distributeResource(){
	var cb = wcm.CrashBoard.get({
		id : 'selectSite',
		title : '<%=LocaleServer.getString("infoview.list.selectdelete.infoview.selectsite", "选择站点")%>',
		url : '../include/site_select.jsp',
		width: '300px',
		height: '350px',
		callback : function(_args){
			ProcessBar.start('<%=LocaleServer.getString("infoview.list.distributeresource", "同步附件")%>');
			new ajaxRequest({
				url : './infoview_resource_distribute.jsp',
				method : 'post',
				parameters : $toQueryStr({SiteIds : _args}),
				onSuccess : function(trans){
					ProcessBar.close();
					cb.close();
				}
			});
			return false;
		}
	});
	cb.show();
}
var m_bFirstClick = true;
var TRSHTMLTr = {
	unselectedClassName		: "list_tr",
	selectedClassName		: "grid_row_active",
	arElCheckBeforeSelect	: [],
	arElTRBeforeSelect		: [],
	onSelectedTR : function(_elTR){
		var evt = window.event;
		if(!evt || evt == 'undefined'){
			evt = TRSHTMLTr.onSelectedTR.caller;
			while(evt){
				var arg0=evt.arguments[0];
				if(arg0){
					if(arg0 instanceof Event){ // event 
						evt = arg0;
						break;
					}
				}
				evt = evt.caller;
			}
		}
		var elEvent = evt.target||evt.srcElement;
		//不响应某些元素的点击
		if(this.isInvalidElement(elEvent))
			return;
		//参数校验
		if(_elTR == null){
			Ext.Msg.alert('<%=LocaleServer.getString("infoview.list.infoviewerror", "调用错误!")%>');
			return;
		}
		var OldStyle = _elTR.className;
		var arInputs = _elTR.getElementsByTagName("INPUT");
		if(m_bFirstClick ){
			m_bFirstClick = false;
			if(_elTR.className != this.unselectedClassName){
				this.unselectedClassName = _elTR.className;
				//this.selectedClassName = _elTR.className + "_selected";
			}
		}

		//判断Ctrl键是否按下
		if(!(evt.ctrlKey || (elEvent.tagName == "INPUT" && elEvent.type == "checkbox")))
		{//取消以前的选择
			for(var i=(this.arElCheckBeforeSelect.length-1); i>=0; i--){
				if(this.arElTRBeforeSelect[i] != _elTR){
					this.arElCheckBeforeSelect[i].bTrigger = true;
					if(this.arElCheckBeforeSelect[i].checked)
						this.arElCheckBeforeSelect[i].click();
					this.arElCheckBeforeSelect[i].bTrigger = false;
				}
				this.arElCheckBeforeSelect.pop();

				if(this.arElTRBeforeSelect[i] != _elTR)
					this.arElTRBeforeSelect[i].className		= this.unselectedClassName;
				this.arElTRBeforeSelect.pop();
			}			
		}

		//获取Checkbox		
		var bChecked = false;
		if(elEvent.tagName == "INPUT" && elEvent.type == "checkbox")
		{//Checkbox点击		
			bChecked = elEvent.checked;
			//记录选中的元素			
			if(bChecked){
				this.arElCheckBeforeSelect[this.arElCheckBeforeSelect.length]	= elEvent;
				this.arElTRBeforeSelect[this.arElTRBeforeSelect.length]			= _elTR;
			}
			
		}else{//TR点击
			if(arInputs.length == 0){
				Ext.Msg.alert(LocaleServer.getString("infoview.list.alertinfo", "没有定义INPUT!"));
				return;
			}
			
			for(var i=0; i<arInputs.length; i++){
				if(arInputs[i].type == "checkbox"){
					arInputs[i].bTrigger = true;
					arInputs[i].click();
					arInputs[i].bTrigger = false;
					
					if(arInputs[i].checked)
						bChecked = true;
					break;
				}
			}
			//记录选中的元素			
			if(bChecked){
				this.arElCheckBeforeSelect[this.arElCheckBeforeSelect.length]	= arInputs[i];
				this.arElTRBeforeSelect[this.arElTRBeforeSelect.length]			= _elTR;
			}
		}
		//设置样式
		if(bChecked){
			_elTR.className = this.selectedClassName;
		}else{
			_elTR.className = this.unselectedClassName;
		}
		//this.unselectedClassName = OldStyle;
	},
	isInvalidElement : function(_currElement){
		var elTemp = _currElement;
		for(var i=0; i<10; i++){
			if(elTemp == null)return false;
			if(elTemp.tagName == "A")return true;		

			if(elTemp.tagName == "INPUT" && elTemp.type != "checkbox")return true;		

			if(elTemp.tagName == "INPUT" && (elTemp.type == "checkbox") && elTemp.bTrigger)return true;		
			

			elTemp = elTemp.parentNode;
		}
		return false;
	}
}
function getUrlJson(){
	var json = {};
	var sUrl = location.href;
	if(sUrl.indexOf('?') > 0){
		sUrl = sUrl.substring(sUrl.indexOf('?') + 1, sUrl.length);
		json = sUrl.parseQuery();
	}
	return json;
}
function CTRSAction_doOrderBy(_sOrderField, _sOrderType){
	var json = getUrlJson();
	json["OrderField"] = _sOrderField;
	json["OrderType"] = _sOrderType;
	json["PageIndex"] = 1;
	pageInfoDoSearch(json);
}
function pageInfoDoSearch(json){
	var sURL = null;
	if(location.href.indexOf('?') > 0){
		sURL = location.href.substring(0,location.href.indexOf('?')+1) + $toQueryStr(json);
	}
	else{
		sURL = location.href + '?' + $toQueryStr(json);
	}
	location.href = sURL;
}
function selectAll(ids){
	selectAllByName(ids);
}
function addNew()
{	
	var oArgs = {
		InfoViewId : 0
	}
	var cb = wcm.CrashBoard.get({
		id : 'addNew',
		title : '<%=LocaleServer.getString("infoview.list.addoredit.title", "添加/修改自定义表单")%>',
		url : 'infoview_addedit.jsp',
		width: '570px',
		height: '290px',
		params : oArgs,
		callback : function(){
			location.reload();
		},
		next : '<%=LocaleServer.getString("infoview.list.addoredit.next", "下一步(编辑)")%>'
	});
	cb.show();
	$('next-addNew').style.display = '';
}
function getInfoViewIds(_sElementName){
		//1.参数校验
		if(_sElementName == null){
			Ext.Msg.alert("Element Name invalid!");
			return;
		}		
		//2.获取
		var arElements = document.getElementsByName(_sElementName);				
		if(arElements == null)return "";
		var sDelim = ",";
		if(arElements.length<=0){
			if(arElements.checked)
				return arElements.value;
			return "";
		}
		//3.遍历
		var bFirst = true;
		var sValue	= "";
		for(var i=0; i<arElements.length; i++){
			if(!arElements[i].checked)continue;
			if(bFirst){
				bFirst = false;
				sValue = arElements[i].value;
			}
			else
				sValue += ',' + arElements[i].value;
		}
		return sValue
}
function getSelectAllStatus(_arElements){
	for(var i=0; i<_arElements.length; i++){
		if(_arElements[i].disabled) continue;
		if(!_arElements[i].checked)return true;
	}
	return false;
}
Event.observe(window, 'load', function(){
	$('addbtn').disabled = false;
	$('deletebtn').disabled = false;
	$('disbtn').disabled = false;
	$('searchbutton').disabled = false;
	$('exportbtn').disabled = false;
});

//-->
</script>
</head>
<BODY topmargin="0" leftmargin="0" style="margin:0px;">	
    <TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
	<TR height="26px">
	<TD class="head_td">
		<table class="tblListHeader" width="100%" height="29" border="0" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;<%=LocaleServer.getString("infoview.lobel.infoviewlist","表单列表")%></td>
				<td align="right">
					<a href="../../integrate_installed/infoview_functional_check.jsp?redirectable=false" WCMAnt:param="infoview_list.jap.backtoemploy">返回表单配置说明页面</a>&nbsp;&nbsp;
				</td>
			</tr>
		</tbody>
		</table>
	</TD>
	</TR>
    <TR>
	<TD valign="top">
	    <TABLE width="100%" height="100%" border="0" cellpadding="2" cellspacing="0" bgcolor="#FFFFFF">
	    <TR height="25">
		<TD>
		    <TABLE width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
		    <TR>
			<TD align="left" valign="top">
			<input type="button" value="新建"  id="addbtn" onclick="addNew();return false;" class="toolbar_item" WCMAnt:paramattr="value:infoview_list.jap.addnew" disabled>
			<input type="button" value="删除"  id="deletebtn" onclick="onDelete();return false;" class="toolbar_item" WCMAnt:paramattr="value:infoview_list.jap.onDelete" disabled>
			<input type="button" value="同步附件"  id="disbtn" onclick="distributeResource();return false;" class="toolbar_item" WCMAnt:paramattr="value:infoview_list.jap.distributeResource" disabled>
			<input type="button" value="导出表单"  id="exportbtn" onclick="exportInfoView();return false;" class="toolbar_item" WCMAnt:paramattr="value:infoview_list.jap.exportinfoview" disabled>
			</TD> 
				<TD width="250" align="right" nowrap>
			<form name="frmSearch" onsubmit="doSearch(this);return false;">		
				<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
				<select name="SearchKey">
					<option value="INFOVIEWNAME,INFOVIEWDESC"><%=LocaleServer.getString("system.label.all", "全部")%></option>
					<option value="INFOVIEWNAME"><%=LocaleServer.getString("infoview.label.name_of_infoview", "表单名称")%></option>
					<option value="INFOVIEWDESC"><%=LocaleServer.getString("infoview.label.desc_of_infoview", "表单描述")%></option>
				</select>
				<input type="submit" id="searchbutton" value="<%=LocaleServer.getString("system.button.search", "检索")%>" class="toolbar_item" disabled>
				</form>
			</TD>
			<script>document.frmSearch.SearchKey.value = "<%=CMyString.filterForJs(currRequestHelper.getSearchKey())%>";</script>
		    </TR>
		    </TABLE>
		</TD>
	    </TR>
	    <TR>
		<TD align="left" valign="top">
			<div style="OVERFLOW-Y:auto;height:100%;" id="dvBody">
		    <TABLE width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
		    <TR class="list_th">
				<TD align="center" width="36" height="20"><a href="#" onclick="selectAll('InfoViewIds')" id="selectAll"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
				<TD align="center" width="300"><span style="cursor:pointer;"><%= PageViewUtil.getHeadTitle("INFOVIEWNAME", LocaleServer.getString("infoview.label.name_of_infoview", "表单名称"), sOrderField, sOrderType)%></span></TD>
				<TD align="center" width="300"><span style="cursor:pointer;"><%= PageViewUtil.getHeadTitle("INFOVIEWDESC", LocaleServer.getString("infoview.label.desc_of_infoview", "表单描述"), sOrderField, sOrderType)%></span></TD>
				<TD align="center" nowrap><%= LocaleServer.getString("system.label.edit", "编辑")%></TD>
				<TD align="center" nowrap><%= LocaleServer.getString("system.label.reload", "重新导入")%></TD>
				<TD align="center" nowrap><%= LocaleServer.getString("system.label.setverifycode", "外网提交是否需要验证码")%></TD>
				<TD align="center" nowrap><%= LocaleServer.getString("infoview.label.applychannel", "应用状态") %></TD>
				<TD align="center" nowrap><%= PageViewUtil.getHeadTitle("CRUSER", LocaleServer.getString("infoview.label.cruser", "创建人"), sOrderField, sOrderType) %></TD>
				<TD align="center" nowrap><%= PageViewUtil.getHeadTitle("CRTIME", LocaleServer.getString("infoview.label.crtime", "创建时间"), sOrderField, sOrderType) %></TD>
		    </TR>
<%
	int nInfoViewId = 0;
	InfoView aInfoView = null;
	
	for(int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++)
	{
		aInfoView = (InfoView) oInfoViews.getAt(i-1);
		boolean isNeedVerifycode = aInfoView.isNeedVerifycode();
		if (aInfoView == null)
		{
			continue;
		}

		if(!loginUser.isAdministrator() && !PluginAdminAuth.hasRight(loginUser)){
			if(!loginUser.getName().equals(aInfoView.getCrUserName())){
				continue;
			}
		}
		nInfoViewId = aInfoView.getId();
		try
		{
%>
		    <TR class="list_tr" bgcolor="#FFFFFF" height="20" onclick="TRSHTMLTr.onSelectedTR(this);">
				<TD width="36"><INPUT TYPE="checkbox" NAME="InfoViewIds" VALUE="<%= nInfoViewId %>"><%= i %></TD>
				<TD><A href="#InfoviewId=<%=nInfoViewId%>" onclick="preview(<%= nInfoViewId %>);return false;" target="_blank"><SPAN title="预览表单[ID=<%=nInfoViewId%>]" WCMAnt:paramattr="title:infoview_list.jap.previewinfoview"><%= PageViewUtil.toHtml(aInfoView.getName()) %></SPAN></A></TD>
				<TD><%= PageViewUtil.toHtml(aInfoView.getDesc()) %></TD>
				<TD align="center" nowrap><A href="#" onclick="edit(<%= nInfoViewId %>);return false;" target="_blank"><SPAN title="编辑表单" WCMAnt:paramattr="title:infoview_list.jap.edit">编辑</SPAN></A></TD>
				<TD align="center" nowrap><A href="#" onclick="reload(<%= nInfoViewId %>);return false;" target="_blank"><SPAN title="重新导入" WCMAnt:paramattr="title:infoview_list.jap.reload">重新导入</SPAN></A></TD>
				<TD align="center" nowrap>
					<input type="radio" infoviewId="<%=nInfoViewId%>" name="verifyRadio<%=nInfoViewId%>" value="1" <%=isNeedVerifycode?"checked":""%> />需要 
					<input type="radio" name="verifyRadio<%=nInfoViewId%>" value="0" infoviewId="<%=nInfoViewId%>" <%=isNeedVerifycode?"":"checked"%>/>不需要
				</TD>
				<TD align="center" nowrap><A href="#" onclick="viewUseList(<%= nInfoViewId %>);return false;" target="_blank"><SPAN title="查看表单被应用的栏目" WCMAnt:paramattr="title:infoview_list.jap.showinfo"><%= LocaleServer.getString("system.label.view", "查看") %></SPAN></A></TD>
				<TD align="center" nowrap><%= PageViewUtil.toHtml(aInfoView.getCrUserName()) %></TD>
				<TD align="center" nowrap><%= PageViewUtil.toHtml(aInfoView.getCrTime().toString("yyyy-MM-dd"))%></TD>
		    </TR>
<%
		}
		catch(Exception e)
		{
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, CMyString.format(LocaleServer.getString("infoview_list.form.attribute.wrong","获取第[{0}]个表单的属性出错！"), new int[]{i}),e);
		}
	}
%>
		    </TABLE>
			</DIV>
		</TD>
	    </TR>
	    <TR height="30px">
		<TD class="navigation_page_td" valign="top" height="30px"><%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), LocaleServer.getString("system.label.view.infoview", "表单"), LocaleServer.getString("system.label.view.infoview.unit", "个"))%></TD>
	    </TR>
	    </TABLE>
	</TD>
    </TR>
    </TABLE>
	<script language="javascript">
	<!--
		Event.observe($('dvBody'),'click',function(event){
			event = event || window.event;
			var target = event.target || event.srcElement;
			var indexOfVerify = ((target.name) || "").indexOf("verifyRadio");
			//假如元素的name中含有verifyRadio字样，并且value的值是0或者1的时候，就发送ajax请求
			if(indexOfVerify != -1 &&(target.value == "1" || target.value == "0")){
				var verifyParamValue = target.value;
				var infoviewId = target.getAttribute('infoviewId');
				//发送ajax请求保存是否需要验证码的值
				BasicDataHelper.Call('wcm6_infoview', 'setVerifyParamValue',{infoviewId:infoviewId,verifyParamValue:verifyParamValue}, true);
			}
		});
	//-->
	</script>
</BODY>
</HTML>
<%!
	IInfoViewService m_oInfoViewService = (IInfoViewService) DreamFactory.createObjectById("IInfoViewService");
%>
<%!
	private boolean hasEmployer(InfoView oInfoView) throws WCMException
	{
		List list = m_oInfoViewService.getInfoViewEmployers(oInfoView);
		if (list != null && list.size() > 0)
		{
			return true;
		}
		return false;
	}
%>