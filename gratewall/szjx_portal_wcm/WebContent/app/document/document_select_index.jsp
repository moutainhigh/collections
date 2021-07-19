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
	<!--AJAX-->
	<script src="../js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<script language="javascript">
		//从上下文中获取参数。
		var CurrChannelId = getParameter("DocChannelId");
		var CurrSiteId = getParameter("SiteId");
		var bOnlyPublished = getParameter("OnlyPublished") || 0;
		var PRV_OBJ_TYPE_CHNLDOC = "ChnlDoc_Relation";
		var PRV_OBJ_TYPE_DOCUMENT = "Document_Relation";
		var PRV_OBJ_TYPE_CURRPAGE = "CurrPage_Relation";
	</script>
	<style type="text/css">
		body {
			padding-top:0px;
			background-color: #CCCCCC;
		}
		.input_btn{
			width:60px;
			margin-right:10px;
		}
	</style>
	<script language="javascript">
	<!--
		$MsgCenter.on({
			objType : WCMConstants.OBJ_TYPE_TREENODE,
			afterclick : function(event){
				//负责导航树对应的页面切换
				
						debugger;
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
						break;
					case WCMConstants.OBJ_TYPE_CHANNEL:
						sParams = '&ChannelId=' + objId;
						sParams += '&IsVirtual=' + context.isVirtual;
						sParams += '&ChannelType=' + context.channelType;
						break;
				}
				sParams += '&SiteType=' + context.siteType;
				sParams += '&RightValue=' + context.right;
				sParams += '&selectType=' + getParameter('selectType');
				sParams += '&OnlyPublished='+bOnlyPublished;
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
				var from = event.from();
				var siteType = parseInt(from.substring(from.indexOf("&SiteType=")+10,from.indexOf("&RightValue=")));
				AddSelectedDoc(obj.getId(), {
					recId : obj.getId(), 
					docId : obj.getPropertyAsInt('docid', 0), 
					docTitle : obj.getProperty('docTitle'),
					siteType: siteType
				});
				defaultSelect.push(obj.getId());
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
			docTitle:"<%=CMyString.filterForJs(CMyString.showEmpty(chnldoc.getDocument().getTitle()))%>",
			trueRecId:"<%=chnldoc.getId()%>"
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
		<TABLE style="table-layout:fixed;">
			<TR>
				<TD width="200" height="385" valign="top">
					<iframe src="../include/blank.html" id="treeNav" allowtransparency="true" scrolling="no" frameborder="0"></iframe>
				</TD>
				<td width="495" height="385" valign="top">
					<iframe id='channel_doc_list' src="../include/blank.html" allowtransparency="true" scrolling="no" frameborder="0"></iframe>
				</TD>
				<TD width="10" align="center" valign="top">
					<div style="width:2px;border-left:1px solid gray;background:white;height:390px;"></div></td>
				<TD width="90" valign="top">
					<input id="btnOk" class="input_btn" onclick="window.parent.Ok();"
						type="button" value="确定" WCMAnt:paramattr="value:link_document_select.html.ok"/>
					<div style="height:5px;"></div>
					<input name="button2" class="input_btn" type="button" value="取消" onclick="window.close();" WCMAnt:paramattr="value:link_document_select.html.cancel"/>
				</TD>
			</TR>
		</TABLE>
<script>
	Event.observe(window, 'load', function(){
		$('treeNav').src = '../nav_tree/nav_tree_inner.html?siteTypes=0,4&ChannelId='+CurrChannelId + '&SiteId=' + CurrSiteId;;
	});
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