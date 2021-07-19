<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDocs" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/public_server.jsp"%>
<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE WCMAnt:param="link_document_select.html.selectLinkDocument">选择链接文档</TITLE>
	<!--css-->
    <link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
    <link rel="stylesheet" type="text/css" href="../css/wcm-list-common.css">

	<script src="../js/runtime/myext-debug.js"></script>
	<script src="../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../js/data/locale/document.js"></script>
	<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../js/source/wcmlib/core/CMSObj.js"></script>
	<SCRIPT src="../js/source/wcmlib/Observable.js"></SCRIPT>

	<script src="../js/source/wcmlib/dragdrop/dd.js"></script>
	<script src="../js/source/wcmlib/dragdrop/wcm-dd.js"></script>
	<script src="../js/source/wcmlib/Component.js"></script>

	<script src="../js/source/wcmlib/crashboard/CrashBoard.js"></script>
	<script src="../js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
	<!--AJAX-->
	<script src="../js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<script language="javascript">
		//从上下文中获取参数。
		var CurrChannelId = getParameter("DocChannelId");
		var nDocId = getParameter("DocId")
		var CurrSiteId = getParameter("SiteId");
		var PRV_OBJ_TYPE_CHNLDOC = "ChnlDoc_Relation";
		var PRV_OBJ_TYPE_DOCUMENT = "Document_Relation";
		var PRV_OBJ_TYPE_CURRPAGE = "CurrPage_Relation";
	</script>
	<style type="text/css">
		body {
			padding-top:0px;
			
		}
		.input_btn{
			width:60px;
			margin-right:10px;
		}
		.ext-ie8 .bottomBorder{
			border-top: 1px solid #686868;
			border-right: 1px solid #686868;
			border-bottom: 1px solid #686868;
			width:95%;
		}
		.fieldset{
			background:#FFFFFF;
			padding:0;
			height:270px;
		}
		.ext-ie8 .fieldset{
			padding-right:1px;
		}
	</style>
	<script language="javascript">
		window.m_cbCfg = {
			btns : [
				{
					text : wcm.LANG.document_attachments_index_1001 || '确定',
					cmd : function(){return onOk();}
				},
				{
					text : wcm.LANG.document_attachments_index_1002 || '取消',
					cmd : function(){
						var param = {};
						var cbr = wcm.CrashBoarder.get('Trs_Region_Select_Appendix');
						cbr.notify(param);
					}
				}
			]
		};

		function onOk(){
			var param = {};
			var radioEls = $('attachments_list').contentWindow.document.getElementsByTagName('input');
			param.SelectedDocInfo = hsSelectedInfo;
			for(var i = 0; i < radioEls.length; i++){
				var radioEl = radioEls[i];
				if(radioEl.type == 'radio' && radioEl.checked){
					param.appendixId = radioEl.value;
					param.appendixPath = radioEl.getAttribute("filename") || '';
					param.showname = radioEl.getAttribute("showname") || '';
				}
			}
			var cbr = wcm.CrashBoarder.get('Trs_Region_Select_Appendix');
			cbr.notify(param);
			cbr.close();
			return false;
		}
		$MsgCenter.on({
			objType : WCMConstants.OBJ_TYPE_TREENODE,
			afterclick : function(event){
				//负责导航树对应的页面切换
				var context = event.getContext();
				var objId = context.objId;
				var objType = context.objType;
				var sParams = '';
				var sHostType = mappingHostWithObjType(objType);
				var mySrc = '../document/document_select_list.html';
				switch(objType){
					case WCMConstants.OBJ_TYPE_WEBSITEROOT:
						return false;
					case WCMConstants.OBJ_TYPE_WEBSITE:
						sParams = '&SiteId=' + objId;
						sParams += '&SiteType=' + context.siteType;
						break;
					case WCMConstants.OBJ_TYPE_CHANNEL:
						sParams = '&ChannelId=' + objId;
						sParams += '&SiteType=' + context.siteType;
						sParams += '&IsVirtual=' + context.isVirtual;
						sParams += '&ChannelType=' + context.channelType;
						break;
				}
				sParams += '&RightValue=' + context.right;
				sParams += '&selectType=' + getParameter('selectType');
		//		sParams += '&' + $('FieldName').value + '=' + $('SearchWord').value;
				$('channel_doc_list').src = mySrc + '?' + sParams;
				return false;
			}
		});
		$MsgCenter.on({
			objType : PRV_OBJ_TYPE_CURRPAGE,
			afterinit : function(event){
				if(event.from()==wcm.getMyFlag())return;
				var objs = CMSObj.createEnumsFrom({
					objType : PRV_OBJ_TYPE_CHNLDOC
				});
				for(var i=0, n=defaultSelect.length; i<n; i++){
					objs.addElement(CMSObj.createFrom({
						objId : defaultSelect[i],
						objType : PRV_OBJ_TYPE_CHNLDOC
					}));
				}
				objs.afterselect();
			}
		});
		$MsgCenter.on({
			objType : PRV_OBJ_TYPE_CHNLDOC,
			afterselect : function(event){
				if(event.from()==wcm.getMyFlag())return;
				var obj = event.getObjs().getAt(0);
				if(obj==null)return;
				if(getParameter('selectType').toUpperCase()=='RADIO'){
					defaultSelect = [];
					hsSelectedInfo = {};
				}
				AddSelectedDoc(obj.getId(), {
					recId : obj.getId(), 
					docId : obj.getPropertyAsInt('docid', 0), 
					docTitle : obj.getProperty('docTitle')
				});
				defaultSelect.push(obj.getId());
				$('attachments_list').src = WCMConstants.WCM6_PATH + 'document/document_attachments_select.html?documentId='+obj.getPropertyAsInt('docid', 0);
			},
			afterunselect : function(event){
				var obj = event.getObjs().getAt(0);
				if(obj==null)return;
				RemoveSelectedDoc(obj.getId());
				defaultSelect.remove(obj.getId());
			}
		});
		var hsSelectedInfo = {};
		<%
			int nChannelId = 0;
			String sChnlDocIds = currRequestHelper.getString("recids");
			ChnlDocs chnlDocs = ChnlDocs.findByIds(loginUser, sChnlDocIds);
			for (int i = 0, nSize = chnlDocs.size(); i < nSize; i++) {
				ChnlDoc chnldoc = (ChnlDoc) chnlDocs.getAt(i);
				if (chnldoc == null)
					continue;
				else 
					nChannelId = chnldoc.getChannelId();
		%>
		hsSelectedInfo["<%=chnldoc.getId()%>"] = {
			recId:"<%=chnldoc.getId()%>", 
			docId:"<%=chnldoc.getDocId()%>", 
			docTitle:"<%=CMyString.filterForJs(CMyString.showEmpty(chnldoc.getDocument().getTitle()))%>"
		};
		if(<%=nChannelId%> > 0){
			CurrChannelId = "<%=nChannelId%>";
		}
		<%
			}
		%>	
		var defaultSelect = [];
		//不能那样做，传过来的可能有一些不存在的，应该从服务器端得到
		for (var sId in hsSelectedInfo){
			defaultSelect.push(sId);
		}
		//var recIds = getParameter('recIds');
		//if(recIds && recIds != "0"){
		//	defaultSelect = recIds.split(",");
		//}
		function mappingHostWithObjType(objType){
			switch(objType){
				case WCMConstants.OBJ_TYPE_WEBSITEROOT:
					return WCMConstants.TAB_HOST_TYPE_WEBSITEROOT;
				case WCMConstants.OBJ_TYPE_WEBSITE:
					return WCMConstants.TAB_HOST_TYPE_WEBSITE;
				case WCMConstants.OBJ_TYPE_CHANNEL:
					return WCMConstants.TAB_HOST_TYPE_CHANNEL;
				case WCMConstants.OBJ_TYPE_MYFLOWDOCLIST:
					return WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST;
				case WCMConstants.OBJ_TYPE_MYMSGLIST:
					return WCMConstants.TAB_HOST_TYPE_MYMSGLIST;
			}
		}
		function AddSelectedDoc(_nRecId, infos){
			hsSelectedInfo[_nRecId] = infos;
		}
		function RemoveSelectedDoc(_nRecId){
			delete hsSelectedInfo[_nRecId];
		}
		function Ok(){
			window.returnValue = {ids:defaultSelect, infos:hsSelectedInfo};
			window.close();
		}
	</script>
</HEAD>
<BODY>
		<TABLE style="table-layout:fixed;" cellspacing="0" cellpadding="0">
			<TR>
				<TD>
				<table border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
				<tbody>
					<tr>
						<TD width="200px" height="250px" valign="top">
							<iframe src="../include/blank.html" id="treeNav" allowtransparency="true" scrolling="auto" frameborder="0" width="100%" height="100%;" class="bottomBorder"></iframe>
						</TD>
						<TD width="715" height="260" valign="top">
							<iframe id='channel_doc_list' src="../include/blank.html" allowtransparency="true" scrolling="no" frameborder="0"></iframe>
						</TD>
					</tr>
				</tbody>
				</table>
				</TD>
			</TR>
			<TR>
				<TD width="920" height="270px" valign="top">
					<fieldset class="fieldset">
						<legend style="line-height:24px;font-size:12px;" WCMAnt:param="document_relations.html.documentappendix">文档的附件</legend>
						<div style="height:90%"><iframe id='attachments_list' src="../include/blank.html"  allowtransparency="true" scrolling="no" frameborder="0"></iframe></div>
					</fieldset>
					
				</TD>
			</TR>
		</TABLE>
<script>
	Event.observe(window, 'load', function(){
		$('treeNav').src = '../nav_tree/nav_tree_inner.html?siteTypes=0,4&ChannelId='+CurrChannelId + '&SiteId=' + CurrSiteId;
	});
	function init(param){
		if(param.DocId > 0)
			$('attachments_list').src = WCMConstants.WCM6_PATH + 'document/document_attachments_select.html?documentId='+ param.DocId + '&AppendixType=50&SelectAppendixIds=' + param.AppendixId;
	}
	function doSearch(){
		var oPostData = Ext.Json.toUpperCase($('channel_doc_list').src.parseQuery());
		var selFieldName = $('FieldName');
		var sFieldName = selFieldName.value;
		for (var i = 0; i < selFieldName.options.length; i++){
			delete oPostData[selFieldName.options[i].value.toUpperCase()];
		}
		oPostData[sFieldName] = $('SearchWord').value;
		$('channel_doc_list').src = 'document_select_list.html?' + $toQueryStr(oPostData);
	}
</script>
</BODY>
</HTML>