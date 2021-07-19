<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="java.util.Map,java.util.Map.Entry,java.util.Iterator" %>

<%
	String sViewId = request.getParameter("viewId");
	int nViewId = 0;
	MetaView oView = null;
	if(sViewId != null){
		nViewId = Integer.parseInt(sViewId);
		oView = MetaView.findById(nViewId);
	}
	if(oView == null){
		throw new Exception("没有找到指定的视图对像[viewId=" + nViewId + "]");
	}
	IMetaDataDefCacheMgr oMetaDataDefCacheMgr = (IMetaDataDefCacheMgr) DreamFactory.createObjectById("IMetaDataDefCacheMgr");
	Map mViewFields = oMetaDataDefCacheMgr.getMetaViewFields(nViewId);

	String sSelectFields = request.getParameter("selectFields");
	if(sSelectFields == null){
		throw new Exception("没有指定对应的参数selectFields");
	}

	String[] aSelectFields = sSelectFields.split(",");
	String[] aFieldDescs = null;
	String sFieldDescs = CMyString.getStr(request.getParameter("fieldDescs"));
	if(sFieldDescs == null){
		aFieldDescs = aSelectFields;
	}else{
		aFieldDescs = sFieldDescs.split(",");
	}
%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>选择字段</TITLE>
<!-- ~Common CSS Begin~ -->
<link href="../../../css/common.css" rel="stylesheet" type="text/css" />
<link href="../../../css/wcm_list_abstract.css" rel="stylesheet" type="text/css" />
<!-- ~Common CSS End~ -->
<SCRIPT language=JavaScript src="../../../js/com.trs.util/Common.js"></SCRIPT>
<script language="javascript">
<!--
	//top.actualTop = window;
	$import("com.trs.wcm.MessageCenter");
	$import("com.trs.wcm.SimpleQuery");
	$import("com.trs.wcm.Grid");
	$import("com.trs.dialog.Dialog");
	$import("com.trs.drag.SimpleDragger");
	$import('com.trs.wcm.PageFilter');
//-->
</script>
<script language="javascript">
<!--
Object.extend(Element, {
	visible: function(element) {
		element = $(element);
		return element.offsetWidth > 0 && element.offsetHeight > 0;
	}
});
//-->
</script>
<SCRIPT language=JavaScript src="../js/related_fields_list.js"></SCRIPT>
<style type="text/css">
	.grid_head,.grid_head_column{
		background-image:none;
		background-color:#D3D3D2;
	}
	.grid_head{
		border-top:1px solid black;
	}
	.grid_row{
		background-color:white;
		color:#07478A;
		border:0px;
		white-space:nowrap;
		overflow:hidden;
	}
	.grid_row_active{
		background-color:lightblue;
	}
	.grid_column{
		border:0px;
		text-overflow:ellipsis;
	}	
</style>
<style type="text/css" id="dynamicStyle">
	.columnWidth{
		width:auto;
	}
	.Row0{
		background-color:white;
	}
	.Row1{
		background-color:#EFEFEF;
	}
	.editorColumn{	
		color:gray;
	}
	<%
        for (Iterator iterator = mViewFields.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            MetaViewField oViewField = (MetaViewField) entry.getValue();
            if (oViewField.getPropertyAsInt("inMultiTable", 1) == 0) {
	%>
			.<%=oViewField.getName()%>{display:none;}
	<%
            }
        }

	%>
</style>
</HEAD>
<BODY>
<table id="table_body" class="wcm_table_layout" style="border:1px solid black;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top" class="wcm_list_view">
			<table class="wcm_table_layout" border=0 cellspacing=0 cellpadding=0>
			<tbody>
				<tr class="list_pageinfo">
					<td id="ListHead">						
						<div style="float:right;" id="query_box"></div>  
						<div style="height:100%;cursor:move;" id="DragHandler"></div>
					</td>
				</tr>
				<tr class="list_view">
					<td valign="top">
						<DIV id='objects_grid' class="grid">
							<table class="wcm_table_layout" border=0 cellspacing=0 cellpadding=0>
								<tr style="height:29px">
									<td>
										<DIV class="grid_head" id="grid_head">
<%
	for(int i = 0; i < aFieldDescs.length; i++){
%>
		<SPAN class="grid_head_column columnWidth <%=CMyString.filterForHTMLValue(aSelectFields[i])%>" grid_sortby="<%=CMyString.filterForHTMLValue(aSelectFields[i])%>">
			<%=CMyString.transDisplay(aFieldDescs[i])%>
		</SPAN>
<%
	}
%>
										</DIV>
									</td>
								</tr>
								<tr>
									<td valign="top">
										<div id="grid_data" class="grid_data"></div>
									</td>
								</tr>
							</table>
						</DIV>
					</td>
				</tr>
				<tr class="list_navigator">
					<td>
						<DIV style="float:left;font-size:12px;padding:4px;">
							<input type="checkbox" name="" value="" onclick="unSelect();">取消选择
						</DIV>
						<DIV id="list_navigator" class="list_navigator_div"></DIV>
					</td>
				</tr>
			</tbody>
			</table>
		</td>
	</tr>
</table>
</BODY>
</HTML>