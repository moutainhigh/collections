<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.ajaxservice.ProcessService" %>
<%@ page import="com.trs.cms.process.IFlowContent" %>
<%@ page import="com.trs.cms.process.FlowContentFactory" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.cms.process.engine.FlowDocs" %>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.cms.process.IFlowServer" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.cms.process.FlowEngineHelper" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.cms.process.definition.FlowNode" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.text.*" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@include file="../include/public_server.jsp"%>

<%
	int nContentId = currRequestHelper.getInt("cid",0);
	int nContentType = currRequestHelper.getInt("ctype",0);
	int nFlowDocId = currRequestHelper.getInt("flowDocId", 0);
	String crUserName = currRequestHelper.getString("cruser");
	String  crTime = currRequestHelper.getDateTime("crtime").toString("MM-dd hh:mm");
	IFlowContent content = FlowContentFactory.makeFlowContent(nContentType,nContentId);
	if (content == null) {
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,
				CMyString.format(LocaleServer.getString("document.detail.show.tracing","指定的内容[Type={0} , Id={1},]没有找到"),new String[]{WCMTypes.getLowerObjName(nContentType),String.valueOf(nContentId)}));
	}
//3.执行操作（获取指定文档的处理过程）
	IFlowServer m_oFlowServer = (IFlowServer) DreamFactory.createObjectById("IFlowServer");
	FlowDocs oFlowDocs = m_oFlowServer.getFlowDocs(content, null);
	int nFlowId = 0;
%>

<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="workflow_process_tracing.jsp.title">查看处理过程</TITLE>
<link href="../css/workflow.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../../app/js/wcm52/CTRSRequestParam.js"></script>
<script src="../../app/js/wcm52/CTRSAction.js"></script>
<!--my import-->
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/iflowcontent.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
<script src="../../app/js/source/wcmlib/pagecontext/BubblePanel.js"></script>
<!-- CarshBoard Inner Start -->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!-- CarshBoard Inner End -->
<script src="workflow_process_tracing.js"></script>
 
<script language="javascript">

	/*window.m_cbCfg = {
        btns : [
            {
                text : wcm.LANG['IFLOWCONTENT_90'] || '确定'
            },
            {
                text : wcm.LANG['IFLOWCONTENT_91'] ||　'取消'
            }
        ]
    };*/

</script>
<STYLE>
	table{
		font-size: 12px;
	}
	body{
		margin-top: 0;
		font-size: 12px;
		padding: 0;
	}
	#spTitle{
		font-weight: bold;
		width: 85%;
		white-space: nowrap;
		text-overflow: ellipsis;
		overflow: hidden;
	}
	#spCrUser, #spCrTime, #spContent{
		color: #010101;
	}
	.grid_head_column{
		background: #F4FCFF;
		cursor: default;
		border-top: 1px solid silver;
		float:none !important;/*去除浮动*/
	}
	.grid_row{
		cursor: default;
	}
	.current_list_node_{
		background-image:url(../images/workflow/green.gif)
	}
	.gunter_node_common{
		border: 1px solid gray;
		margin-left: -1px;
		font-family: georgia;
		background: whitesmoke;
		color: gray;
		height: 40px;
		margin-top: 5px;
		white-space:nowrap;
		padding: 5px;
		float: left;
	}
	/*.gunter_sep_header{
		width: 11px; 
		height: 100%;
		background-image: url(../images/workflow/icon_arrow_header.gif);
		background-position: left center; 
		background-repeat: no-repeat; 	
	}
	.gunter_sep_rear{
		width: 12px; 
		height: 100%;
		background-image: url(../images/workflow/icon_arrow_rear.gif);
		background-position: right center; 
		background-repeat: no-repeat; 	
	}*/
	.gunter_node_title{
		font-weight: bold; 
		font-size: 16px; 
		text-align: center;
		line-height: 20px;
		height: 20px;
		display:inline-block;
		float:left;		
	}
	.gunter_node_subtitle{
		font-size: 12px; 
		text-align: center; 
		font-weight: normal;
		border: 0px solid black;
		line-height: 14px;
		height: 14px;
		display:inline;
	}
	.gunter_node_posttime{
		font-size: 10px; 
		text-align: center;
		font-weight: normal; 
		border: 0px solid red;
		line-height: 10px;
		height: 10px;
		color: gray;
		display:inline;
	}
	.current_gunter_node{
		color: #010101; 
		font-weight: bold;
	}
	.bg_none_{
		/*background: #eeeeee;*/
	}
	.dv_item{
		width: 100%;
	}
	.dv_item_sep{
		border-top: 1px dashed silver;
		width: 100%;
		height: 3px;
	}
	.help-tooltip {
		position:	absolute;
		border:		1px Solid WindowFrame;
		background:	aliceblue;
		color:		InfoText;
		font:		StatusBar;
		filter:		progid:DXImageTransform.Microsoft.Shadow(color="#777777", Direction=135, Strength=3);
		z-index:	10000;
		width:	300px;
		padding: 5px;
	}
	.grid_column_autowrap{
		white-space:nowrap ;/*在指定td宽度的时候会换行*/
		word-break:keep-all;
		float:none !important;
	}
	.ext-gecko .grid_column_autowrap{
		padding : 0px;
		line-height : 29px;
	}
	.sp_detail{
		margin-top: 5px;
		padding: 3px;
		font-size: 14px;
		line-height: 50px;
		width: 100%;
		overflow:auto;
		display:inline-block;
	}
	#tableList{
		border-collapse:collapse;
	}
	#tableList .headrow{
		border: 1px silver solid;
	}
</STYLE>
</HEAD>
<BODY align="center">
<script>
	function $style(_sCssText){
		var eStyle=document.createElement('STYLE');
		eStyle.setAttribute("type", "text/css");
		if(eStyle.styleSheet){// IE
			eStyle.styleSheet.cssText = _sCssText;
		} else {// w3c
			var cssText = document.createTextNode(_sCssText);
			eStyle.appendChild(cssText);
		}
		return eStyle;
	}
	function $removeChilds(_node){
		_node.innerHTML = "";
	}
</script>
<div style="width: 98%; overflow: auto;height:330px; background: #fff; padding: 5px;">
	<div style="width: 98%; border-bottom: 1px dashed silver; line-height: 16px; padding: 5px;" id="divContent">
		<div style="font-size: 14px;">
			<span id="spTitle"></span>
		</div>
		<div style="font-family: georgia; margin-top: 5px; color: gray">
			<span WCMAnt:param="workflow_process_4wcm52.jsp.createUser">创建者: </span><span id="spCrUser"><%=CMyString.transDisplay(crUserName)%></span>&nbsp;<span WCMAnt:param="workflow_process_4wcm52.jsp.createTime">创建时间: </span><span id="spCrTime"><%=CMyString.transDisplay(crTime)%></span>&nbsp;<span WCMAnt:param="workflow_process_4wcm52.jsp.documentid">文档ID: </span><span id="spContent"><%=nContentId%></span>
		</div>
	</div>
	<div style="width: 98%; margin-top: 10px; overflow: auto;line-height: 16px; padding: 2px;">
		<div style="width: 95%; border-bottom: 1px solid lightblue">
			<span>
				<input id="rdList" type="radio" name="viewMode" value="" onclick="switchMode(event, 1)"><span WCMAnt:param="workflow_process_4wcm52.jsp.rdList">列表模式</span>&nbsp;
				<input id="rdGunter" type="radio" name="viewMode" value="" onclick="switchMode(event, 2)"><span WCMAnt:param="workflow_process_4wcm52.jsp.rdGunter">甘特图模式</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" id="workflowmode" title="在新窗口中打开" WCMAnt:param="workflow_process_4wcm52.jsp.flowmode" WCMAnt:paramattr="title:workflow_process_4wcm52.jsp.flowmodetitle">工作流图模式</a>&nbsp;
			</span>
		</div>
		<div id="divList" style="font-family: georgia; margin-top: 5px; color: gray;overflow:hidden;">
			<table id="tableList" style="table-layout:fixed;width:100%" border=0 cellspacing=0 cellpadding=0>
				<thead>
					<tr style="height:20px" class="headrow">
						<!--列表头模板-->
						<td class="grid_head_column dywidth1" WCMAnt:param="workflow_process_4wcm52.jsp.operhistory">操作历史</td>
						<td class="grid_head_column" style="width:90px;white-space:nowrap;text-overflow:ellipsis" WCMAnt:param="workflow_process_4wcm52.jsp.opertime" title="操作时间" WCMAnt:paramattr="title:workflow_process_4wcm52.jsp.time">时间</td>
						<td class="grid_head_column" style="width:80px" WCMAnt:param="workflow_process_4wcm52.jsp.operuser">操作</td>									
						<td class="grid_head_column" WCMAnt:param="workflow_process_4wcm52.jsp.submit" style="width:125px;">提交给</td>											
						<td class="grid_head_column dywidth2" style="border-right:0" WCMAnt:param="workflow_process_4wcm52.jsp.desc">意见</td>
					</tr>
				</thead>
				<tbody id=grid_data>
					<%
						for(int i=0;i< oFlowDocs.size();i++){
							FlowDoc doc = (FlowDoc)oFlowDocs.getAt(i);
							if(nFlowDocId == doc.getId()){
								int nNodeId = doc.getNodeId();
								FlowNode oFlowNode = FlowNode.findById(nNodeId);
								if(oFlowNode != null){
									nFlowId = oFlowNode.getFlowId();
								}
							}
					%>
					<tr class="grid_row" grid_rowid="<%=doc.getId()%>" id="list_node_<%=doc.getId()%>" _flag="<%=doc.getFlag()%>" _worked="<%=doc.isWorked() ? "1" : "0"%>" style="border-left: 1px solid silver; border-right: 1px solid silver;">
						<td class="grid_column_autowrap dywidth1" title="<%=doc.getFlagDesc()%>">
							<%=doc.getFlagDesc()%>&nbsp;
						</td>
						<td class="grid_column_autowrap" style="width:90px; font-family: Georgia">
							<%=doc.getPostTime().toString("MM-dd HH:mm")%>&nbsp;
						</td>
						<td class="grid_column_autowrap" style="width:80px;" title="<%=doc.getPostUserTrueName()%>">
							<%=doc.getPostUserTrueName()%>&nbsp;
						</td>
						<td class="grid_column_autowrap bg_none_<%=doc.getToUserTrueNames()%>" title="<%=doc.getToUserTrueNames()%>" style="width:125px; ">
							<%=doc.getToUserTrueNames()%>&nbsp;
						</td>
						<td class="grid_column dywidth2" style="text-align: left; text-overflow: ellipsis; overflow: hidden; padding-left:5px; border-right: 0;white-space:nowrap;" title="点击查看全部" WCMAnt:paramattr="title:workflow_process_4wcm52.jsp.showalltitle">
							<a href="#"  onclick="showDetail(event, this); return false;" _index="<%=i%>">

							<%
									String[] pComments = FlowEngineHelper.parseComments(doc);
									if (pComments != null) {
										for (int k = 0; k < pComments.length; k++) {
								%>
											<span style="padding-right: 10px;">
													<span style="color: gray"><%= pComments[k]%></span>: 
													<span><% k++; %><%=CMyString.filterForHTMLValue(pComments[k])%></span>
											</span>
								<%
										}
									}
								%>
							</a>&nbsp;
						</td>				
					</tr>
					<%  }%>
				</tbody>
			</table>		
		</div>
		<div id="divGunter" style="font-family: georgia; margin-top: 5px; padding: 5px; color: gray; display: none; height: 100%; overflow: hidden;">
		
			<%
				for(int i=oFlowDocs.size()-1;i>=0 ;i--){
					FlowDoc doc = (FlowDoc)oFlowDocs.getAt(i);
			%>
			<span class="gunter_node_common" id="gunter_node_<%=doc.getId()%>" _flag="<%=doc.getFlag()%>" _worked=<%=doc .isWorked() ? "1" : "0"%>>
				<div class="gunter_node_title" title="<%=LocaleServer.getString("flowdoc.label.posttime", "时间")%>:<%=doc.getPostTime().toString("MM-dd HH:mm")%> &#13;<%=LocaleServer.getString("flowdoc.label.postdesc", "意见")%>:<%=CMyString.showNull(doc.getPostDesc())%>">
						<%=doc.getFlagDesc()%>
						<br />
						<div class="gunter_node_subtitle">
							<span class=""></span><%=doc.getPostUserTrueName()%>
						</div>
						<br />
						<div class="gunter_node_posttime">
							<%=doc.getPostTime().toString("MM-dd HH:mm")%>
						</div>
				</div>
			</span>	
		 <%}%>
		</div>
	</div>
</div>
<div id="showDetail" class="help-tooltip" style="display:none;"><div id="content"></div></div>
</BODY>
<script language="javascript">
<!--
var nFlowId = <%=nFlowId%>;
var nFlowDocId = <%=nFlowDocId%>;
//-->
</script>
</HTML>