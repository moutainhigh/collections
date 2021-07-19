<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@include file="../include/public_server.jsp"%>
<%
	//获取当前视图和当前文档ID（即被关联的对象）
	String scurrMetaViewId = currRequestHelper.getString("MetaViewId");
	String scurrDocId = currRequestHelper.getString("docId");

	//获取当前文档关联的视图名称和ID
	String sRelateViewNames = currRequestHelper.getString("relateViewNames");
	String sRelateViewIds = currRequestHelper.getString("relateViewIds");
	
	String[] arrViewNames = sRelateViewNames.split(",");
	String[] arrViewIds = sRelateViewIds.split(",");
%>
<HTML xmlns:TRS_UI>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<TITLE></TITLE>
	<script language="javascript">
		var PageContext = {};
		var PRV_OBJ_TYPE_DOCUMENT = "Document_Relation";
		var PRV_OBJ_TYPE_CHNLDOC = "ChnlDoc_Relation";
		var PRV_OBJ_TYPE_CURRPAGE = "CurrPage_Relation";

		/*被关联的视图id*/
		var m_nRelatedViewId = <%=scurrMetaViewId%>;
		/*被相关的文档id*/
		var m_nRelatedDocId = <%=scurrDocId%>;

		/*关联的视图id*/
		var m_nRelatingViewIds = "<%=sRelateViewIds%>";
	</script>
	<script src="../js/runtime/myext-debug.js"></script>
	<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../js/data/locale/metaviewdata.js"></script>
	<script src="../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../js/source/wcmlib/core/CMSObj.js"></script>
	<script src="../js/easyversion/ajax.js"></script>
	<script src="../js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
	<script src="../js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>

	<link href="../app/application/common/viewdata_detail.css" rel="stylesheet" type="text/css" />
	<link href="viewdata_detail.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="./metaviewdata_relations_back_select.css"/>

	<!--jquery begin-->
	<script src="../application/common/jquery/js/jquery-1.6.1.min.js"></script>
	<script src="../application/common/jquery/js/jquery-ui-1.8.13.custom.min.js"></script>
	<link href="../application/common/jquery/css/ui-lightness/jquery-ui-1.8.13.custom.css" rel="stylesheet" type="text/css" />
	<!--jquery end-->
	<script>
		jQuery.noConflict()(function() {
			jQuery("#tabs").tabs();
		})
	</script>
</HEAD>

<BODY style="margin:0;padding:0;">
<div id="box">
	<div id="tabs">
		<ul>
		<%
			for(int i=0; i<arrViewNames.length; i++){
		%>
			<li>
				<a href="#tabs-<%=arrViewIds[i]%>"><%=arrViewNames[i]%></a>
			</li>
		<%}%>
		</ul>
		<%
			for(int i=0; i<arrViewNames.length; i++){
		%>
		<div id="tabs-<%=arrViewIds[i]%>">
			<div class="row" style="text-align:center; margin:0px 0px;">
				<!--
					by CC 20120416 需要在这里内嵌一个显示当前视图关联的所有栏目的内容！
				-->
				<iframe src="./metaviewdata_forallrelations_back_detail_select.jsp?ViewName=<%=arrViewNames[i]%>&ViewId=<%=arrViewIds[i]%>&scurrDocId=<%=scurrDocId%>&scurrMetaViewId=<%=scurrMetaViewId%>" class="" id="<%=arrViewNames[i]%>_frame" frameborder="0" width="800px" height="490px"></iframe>
			</div>
		</div>
		<%}%>
	</div>
	<div class="buttonBox" id="CommandButtons" style="text-align:center; height:53px;">
		<button onclick="submitData(true);return false;" WCMAnt:param="viewdata_detail.jsp.confirm" style="margin-top:10px; width:80px;">确&nbsp;&nbsp;&nbsp;&nbsp;定</button>&nbsp;&nbsp;&nbsp;
		<button onclick="window.close();" WCMAnt:param="viewdata_detail.jsp.cancel" style="margin-top:10px; width:80px;">取&nbsp;&nbsp;&nbsp;&nbsp;消</button>&nbsp;&nbsp;&nbsp;
	</div>
</div>
<script language="javascript">
<!--
	//点击确认按钮后，执行保存操作！
	function submitData(){
		//1 获取到所有的关联视图ID
		var m_nRelatingViewIdArr = m_nRelatingViewIds.split(",");
		
		//2 获取到所有的iframes，每一个iframe都对应着一个关联视图
		var iframes = window.frames;
		var aCombine = [];
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		
		//3 for循环的作用主要是将每个关联视图的保存请求进行封装
		for(var i=0; i< iframes.length; i++){
			var iframeDOM = iframes[i];
			var docIds = iframeDOM.getRelationDocIds();
			var relationFieldName = iframeDOM.m_Relations.RELATIONS.RELATIONFIELD;
			var params = {
				RelatedDocId : m_nRelatedDocId,
				RelatingDocIds : docIds,
				RelatingViewId : m_nRelatingViewIdArr[i],
				RelatingFieldName : relationFieldName
			}
			aCombine.push(oHelper.Combine('wcm61_metaviewdata','saveRelatingViewDatas',params));
		}
		//4 执行保存逻辑！
		oHelper.MultiCall(aCombine, function(_transport,_json){
			var lenght = $a(_json, "MULTIRESULT.NULL").length;
			if(length != iframes.length){
				Ext.Msg.alert("保存关联Tab视图的请求服务和返回的结果集数目不一致！请确认下是否都保存成功");
			}
			window.close();
		});
	}
//-->
</script>
</BODY>
</HTML>