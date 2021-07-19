<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../../../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../../../../../include/public_server.jsp"%>
<%@include file="../../../../../include/convertor_helper.jsp"%>

<%
	String strCurrPage = request.getParameter("passInputPageIndex");
	String strParametersValue = request.getParameter("parametersValue");
	String strParametersKey = request.getParameter("parametersKey");
	
	if((strCurrPage==null)||(strCurrPage=="")) {
		strCurrPage = "1";
	}
	if((strParametersValue==null)||(strParametersValue=="")) {
		strParametersValue = "7";
	}
	if((strParametersKey==null)||(strParametersKey=="")) {
		strParametersKey = "SiteIds";
	}

	int nCurrPage = Integer.parseInt(strCurrPage);
	int nParametersValue = Integer.parseInt(strParametersValue);
	//对象定义

	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	//指定要调用的服务/方法名
	String sServiceId = "wcm6_viewdocument", sMethodName = "query";
	//指定参数
	HashMap parameters = new HashMap();
	parameters.put(strParametersKey, nParametersValue);
	parameters.put("PageSize", "8");
	parameters.put("CurrPage", nCurrPage);

	//执行服务请求
	ViewDocuments result = (ViewDocuments) processor.excute(sServiceId,sMethodName, parameters);
	ViewDocuments objs = (ViewDocuments)result;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Photo_Lib</title>
	<!-- 引入图片库界面样式 -->
	<link href="photo_lib.css" rel="stylesheet" type="text/css" />
	<!-- 引入树 组件 Start -->
	<link href="../../../../../../app/js/source/wcmlib/com.trs.tree/resource/TreeNav.css" rel="stylesheet" type="text/css" />
	<link href="../../../../../../app/nav_tree/nav_tree.css" rel="stylesheet" type="text/css" />

	<script src="../../../../../../app/js/runtime/myext-debug.js"></script>
	<script src="../../../../../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../../../../js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../../../../js/source/wcmlib/com.trs.tree/TreeNav.js"></script>
	<script src="../../../../../js/data/locale/nav_tree.js"></script>
	<script src="../../../../../js/data/locale/tree.js"></script>
	<script src="../../../../../js/easyversion/ajax.js"></script>
	<!-- 引入树 组件 End -->
	<!-- 引入分页 组件 Start -->
	<script src="pagenav/jquery-1.7.1.min.js"></script><!-- 引入jquery的js-->
	<link href="pagenav/jquery-ui-1.8.16.custom.css" rel="stylesheet" /><!-- 引入jquery-ui的css和js-->
	<script src="pagenav/jquery-ui-1.8.16.custom.min.js"></script>
	<link href="pagenav/jquery.ui.pagenav.css" rel="stylesheet" /><!-- 引入分页组件pagenav的css和js-->
	<script src="pagenav/jquery.ui.pagenav.js"></script>
	<!-- 引入分页 组件 End -->
	<!--AJAX Start-->
	<script src="../../../../../../app/js/data/locale/system.js"></script>
	<script src="../../../../../../app/js/data/locale/ajax.js"></script>
	<script src="../../../../../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<!-- AJAX End -->

<script language="javascript">
/*
*引入树组件
*/
	var TreeNav = com.trs.tree.TreeNav;
	//重写com.trs.tree.TreeNav的动态载入方法
	com.trs.tree.TreeNav.makeGetChildrenHTMLAction = function(_elElementLi){
		var nPos	= _elElementLi.id.indexOf("_");
		//获取父节点的类型
		var sParentType	= _elElementLi.id.substring(0, nPos);
		//获取父节点的id
		var sParentId	= _elElementLi.id.substring(nPos+1);
		//返回tree_html_creator.jsp请求页面的结果，返回的结果为子节点的代码
		return "../../../../../nav_tree/tree_html_creator.jsp" + (location.search||'?1=1') + "&Type=0&FromSelect=0&ParentType=" + sParentType + "&ParentId=" + sParentId;
	}
	com.trs.tree.TreeNav.doActionOnClickA=function (event,oSrcElement){
		var strMessage = window.event.srcElement.parentNode.id;
		strs=strMessage.split("_"); 
		var treeDomType = strs[0];//r,s,c
		var treeDomId = strs[1];//number
		switch(treeDomType){
			case "r":
				var parametersKey = "SiteIds";/*"RootIds";*/
				treeDomId = 7;
				break;
			case "s":
				var parametersKey = "SiteIds";
				break;
			case "c":
				var parametersKey = "ChannelIds";
				break;
			default:
				var parametersKey = "SiteIds";
				break;
		}
		document.getElementById("parametersValue").value = treeDomId;
		document.getElementById("parametersKey").value = parametersKey;
		var formObj = document.getElementById('passForm');
		formObj.submit();
		setTimeout("document.getElementById('div_photolib_tree').style.display = 'none'",300);
		return false;
	}
/*
*树组件展现的js
*/
	function treeShowHidden(){
		if(document.getElementById('div_photolib_tree').style.display != "block"){
			document.getElementById('div_photolib_tree').style.display = "block";
		}
		else{
			document.getElementById('div_photolib_tree').style.display = "none";
		}
	}
/*
*尺寸大小展现的js
*/
	function scaleSizeShowHidden(){
		if(document.getElementById('div_photolib_scaleSize').style.display != "block"){
			document.getElementById('div_photolib_scaleSize').style.display = "block";
		}
		else{
			document.getElementById('div_photolib_scaleSize').style.display = "none";
		}
	}
/*
*引入分页组件
*/
	var jq=jQuery.noConflict();
	jq(function(){
		jq('#page-nav').pagenav({
			//初始化参数
			recordNum : <%=objs.size()%>,
			pageSize : 8,
			pageIndex : <%=nCurrPage%>,
			callback : function(pageIndex, pageSize){
				//通过form发送js值给java
				var strPageIndex = pageIndex; //定义js变量 
				document.getElementById("passInputPageIndex").value = strPageIndex;//将js变量的值放到form中的一个隐藏域中
				document.getElementById("parametersValue").value = document.getElementById("hiddenValue").value;
				document.getElementById("parametersKey").value = document.getElementById("hiddenKey").value;
				var formObj = document.getElementById('passForm');
				formObj.submit();
			}
		});
	});
/*
*选中要插入图片事件
*/
	function checkIf(checkDom,_id){
	var recid = checkDom.getAttribute("photo_chnlid") + "_";
	var sPhotoSrcs = checkDom.getAttribute("photo_srcs") + "_";
	
	var inputPushValue = document.getElementById('selectedId').value;
	var inputPushSrcs = document.getElementById('sPhotoSrcs').value;

	if(document.getElementById(_id).checked) {
		inputPushValue += recid;
		inputPushSrcs += sPhotoSrcs;
	}
	if(!(document.getElementById(_id).checked)) {
		if(inputPushValue.indexOf(recid)!=-1){
			//去掉选中后又取消的值
			var arrValue = inputPushValue.split("_");
			var aValue="";
			for(i=0;i<arrValue.length-1;i++){
				if(arrValue[i] == checkDom.getAttribute("photo_chnlid")) {
					continue;
				}
				aValue += arrValue[i]+"_";
			}
			inputPushValue = aValue;
			var arr = inputPushSrcs.split("_");
			var a="";
			for(i=0;i<arr.length-1;i++){
				if(arr[i] == checkDom.getAttribute("photo_srcs")) {
					continue;
				}
				a += arr[i]+"_";
			}
			inputPushSrcs = a;
		}
	}
	document.getElementById('selectedId').value = inputPushValue;
	document.getElementById('sPhotoSrcs').value = inputPushSrcs;

}
/*获取当前编辑器对象*/
var CKEDITOR	= parent.CKEDITOR;
var dlg			= CKEDITOR.dialog.getCurrent();
var editor		= dlg.getParentEditor();
/*
*插入图片逻辑
*/
function ok(){
	var strSelectedId = document.getElementById('selectedId').value;
	var selectedSize = document.getElementById('div_selectSize').value;
	if(!selectedSize) {
		selectedSize = 2;
	}
	if(!strSelectedId) {
		alert("没有选择要插入的图片，请选择");
		return;
	}
	var tmpSelectedPhotoIds = strSelectedId.split("_");
	
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	var oPostData = {
		//
		"ChnlDocIds" : tmpSelectedPhotoIds.join(),
		"ScaleIndex" : selectedSize
	}
	oHelper.Call('wcm6_photo', 'getPublishUrls', oPostData, true, function(_transport,_json){
		var arrUrls = _json["URLS"];
		for(var i=0;i<tmpSelectedPhotoIds.length-1;i++){
			var nPhotoId = tmpSelectedPhotoIds[i];
			var sPhotoSrcs = document.getElementById('sPhotoSrcs').value;
			var arrPhotoSrcs = sPhotoSrcs.split(',');
			var sPhotoSrc = arrUrls[i][0];
			var sSourceLink = arrUrls[i][1];

			var sourceFileName = sSourceLink.split('/');
				var ckImgEl = editor.document.createElement( 'IMG' ) ;
				var oImage = ckImgEl.$;
			oImage.setAttribute('_fcksavedurl', sPhotoSrc ) ;
			oImage.setAttribute('FromPhoto', 1 ) ;
			oImage.setAttribute('border', 0 ) ;
			if(arrUrls[i][2]!=""){
				oImage.setAttribute('ignore', 1 ) ;	
			}
			if(arrPhotoSrcs.length <= 1){
				oImage.src = WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName=' +  sourceFileName[4];//图片的fileName
			}else{
				oImage.src = sPhotoSrc;
			}
			editor.insertElement(ckImgEl);
			dlg.hide();
		}
	});
	return false;
}
/*
*选择尺寸大小
*/
function selectSize(dom){
	var selectSize = dom.getAttribute('value');
	document.getElementById('div_selectSize').value = selectSize;
	setTimeout("document.getElementById('div_photolib_scaleSize').style.display = 'none'",300);
	document.getElementById('size_change').innerHTML = dom.innerHTML;
	//document.getElementById('size_change').style.backgroundColor = "blue"; onblur事件触发背景色再改变
	return false;
}

</script>
<!-- 隐藏的发送当前也的表单 -->
<form  method="post" action="photo.jsp" id ="passForm"> 
	<input id = "passInputPageIndex" type = "hidden" name="passInputPageIndex">
	<input id = "parametersValue" type = "hidden" name="parametersValue">
	<input id = "parametersKey" type = "hidden" name="parametersKey">
</form>
<input id = "hiddenValue" type = "hidden" value="<%=nParametersValue%>">
<input id = "hiddenKey" type = "hidden" value="<%=strParametersKey%>">
<input id = "selectedId" type = "hidden"><!-- 隐藏的选择的图片id -->
<input id = "sPhotoSrcs" type = "hidden"><!-- 隐藏的选择图片src -->
<input id = "div_selectSize" type = "hidden">

<style type="text/css">
/*
*树组件css
*/
	.div_photolib_tree{
		position:absolute;
		width:177px;
		height:150px;
		border:1px solid #e2e3ea;
		z-index:1000;
		left:34px;
		top:32px;
		background:white;
		display:none;
	}
/*
*分页组件css
*/
	.page-nav{
		font-size:12px;
		position:absolute;
		top:350px;
		left:25%;
		width:350px;
		clear:both;
	}
/*
*上传按钮css
*/
	.btn_div{
		color:white;
		font-size:14px;
		text-align:center;
		line-height:29px;
		font-weight:600;
		font-family:'微软雅黑';
		position:absolute;
		right:25px;
		top:345px;
		background:url(images/btn_div.png) no-repeat;
		width:76px;
		height:29px;
		cursor:hand;
	}
/*
*尺寸大小css
*/
	.div_photolib_scaleSize{
		position:absolute;
		width:140px;
		height:150px;
		border:1px solid #e2e3ea;
		z-index:1000;
		left:130px;
		top:32px;
		background:white;
		display:none;	
	}
	.div_selected_scaleSize{
		height:24px;
		width:100%;
		border-bottom:1px solid #e2e3ea;
		font-family:'微软雅黑';
		font-size:14px;
		cursor:hand;
		line-height:24px;
	}
	.div_input{
		overflow:hidden;
	}
</style>
</head>

<body>
<!--  -->
	<div id="" class="div_header">
		<div id="" class="div_input">
			图片库
		</div>
		<div id="" class="div_input_img" onclick="treeShowHidden()">
			
		</div>
		<div id="size_change" class="div_input">
				尺寸大小
		</div>
		<div id="" class="div_input_img" onclick="scaleSizeShowHidden()">
			
		</div>
		
		<div id="" class="div_search" contentEditable="true">
			
		</div>
	</div>
<!-- 隐藏的树组件界面 -->
<div id="div_photolib_tree" class="div_photolib_tree" tabIndex="100">
	<DIV class="TreeView">
		<DIV id="r_1" title="图片库 "SiteType="1" classPre="SiteType1" isRoot="true" style="zoom:1">
			<A href="#">图片库</A>
		</DIV>
		<ul></ul>
	</DIV >
</div>
<!-- 隐藏的尺寸大小界面 -->
<div id="div_photolib_scaleSize" class="div_photolib_scaleSize">
	<div id="" class="div_selected_scaleSize" value="0" onclick="selectSize(this)">缩略图(75 x 75)</div>
	<div id="" class="div_selected_scaleSize" value="1" onclick="selectSize(this)">图片列表(124)</div>
	<div id="" class="div_selected_scaleSize" value="2" onclick="selectSize(this)">发布概览(133)</div>
	<div id="" class="div_selected_scaleSize" value="3" onclick="selectSize(this)">中图(240)</div>
	<div id="" class="div_selected_scaleSize" value="4" onclick="selectSize(this)">大图(500)</div>
	<div id="" class="div_selected_scaleSize" value="5" onclick="selectSize(this)">超大图(640)</div>
</div>
<!-- 图片库界面的展现 -->
	<div id="" class="div_box">
		<ul id="" class="">
<%
	//遍历结果集
	for (int i = 1; i <= objs.size(); i++) {
		try{
			ViewDocument obj = (ViewDocument)objs.getAt(i - 1);
			if (obj == null)
				continue;
			Document currDocument = Document.findById(obj.getDocId());
			if(currDocument == null){
				throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("../../../../../photo/photo_list_editor_query.jsp.doc_notfound", "没有找到指定ID为[{0}]的文档!"), new int[]{ obj.getDocId()}));
			}
			int nDocId = currDocument.getId();
			int nRowId = obj.getChnlDocProperty("recid",0);
			ChnlDoc chnldoc = ChnlDoc.findById(nRowId);
			if(chnldoc == null){
				throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("photo_list_editor_query.jsp.chnldoc_notfound", "没有找到指定ID为[{0}]的chnldoc!"), new int[]{nRowId}));
			}
			int nModal = chnldoc.getPropertyAsInt("MODAL",0);
			String sFileNameLike = currDocument.getPropertyAsString("docrelwords");
			String sDefault = currDocument.getAttributeValue("srcfile");
			String sPicUrl = currDocument.getRelateWords();
			String sFileName = mapfile(sFileNameLike,0,sDefault);			
			String sRightValue = obj.getRightValue(loginUser).toString();
%>
			<li class="photolib_li" id="thumb_item_<%=nRowId%>">
				<div id="" class="div_img">
					<img src="<%=sFileName%>" style="cursor:hand;height:94px;width:112px;"/>
				</div>
				<div id="" class="">
					<span class="span_font" title="<%=CMyString.filterForHTMLValue(currDocument.getTitle())%>"><%=CMyString.filterForHTMLValue(currDocument.getTitle())%></span>
					<input type="checkbox" name="AppendixId" class='img_checkbox' id="cbx_<%=nRowId%>" photo_srcs="<%=sPicUrl%>" photo_url="<%=sDefault%>" photo_chnlid="<%=nRowId%>" onclick="checkIf(this,id)">
					<!-- nRowId为docid nDocId为recid 要传recid -->
					<!-- select DOCID, ATTRIBUTE, docrelwords, CRTIME from WCMDOCUMENT order by DOCID desc;select * from WCMCHNLDOC order by RECID desc; -->
				</div>
			</li>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
		</ul>
	</div>
	<!-- 分页 -->
	<div class="page-nav" id="page-nav"></div>
	<!-- 按钮 -->
	<div id="btn_div" class="btn_div" onclick="ok()">确   定</div>
</body>
</html>
<%!
	private String mapfile(String sFileName,int temp,String _default) throws WCMException{
		if(sFileName == null || (sFileName.trim()).equals("")){
				return "../../../../../images/photo/pic_notfound.gif";
			}
			String[] fn = sFileName.split(",");
			String r = "";
			if(fn.length <= temp){
				r = "../../../../../../file/read_image.jsp?FileName=" +  _default;
				return r;
			}
			r = fn[temp];
			return "/webpic/" + r.substring(0,8) + "/" + r.substring(0,10) + "/" + r;	
	}
%>