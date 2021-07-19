<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="/app/include/error.jsp"%>

<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDocs" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document,com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.haier.destroytask.DestroyTask" %>

<%@ include file="/app/include/public_server.jsp"%>

<%
	// 1 参数解析
	String sValue = request.getParameter("ObjectId");
	if(CMyString.isEmpty(sValue)){
		throw new WCMException("没有传入ObjectId参数，大小写问题？还是漏了？");
	}
	int  nObjectId = Integer.parseInt(sValue);
	

	// 工作流相关特性处理
	int nFlowDocId = 0;
	if(request.getParameter("FlowDocId") != null){
		nFlowDocId = Integer.parseInt(request.getParameter("FlowDocId"));
	}	

	// 2 判断权限
	
	// 3 获取需要撤销的文档，构造显示的标题以及对应的URL
	DestroyTask task = DestroyTask.findById(nObjectId);
	ChnlDoc chnldoc = (ChnlDoc)task.getContent();
	
	int nDocId = chnldoc.getDocId(), nChannelId = chnldoc
			.getChannelId();

	Document document = Document.findById(nDocId,
			"DocTitle,CrTime,DocChannel,DocId");	

	String sTitle = document.getTitle();

	IPublishContent content = null;
	if (nChannelId == document.getChannelId()) {
		content = (IPublishContent) PublishElementFactory
				.makeElementFrom(document);
	} else {
		IPublishFolder folder = (IPublishFolder) PublishElementFactory
				.makeElementFrom(chnldoc.getChannel());
		content = PublishElementFactory.makeContentFrom(document,
				folder);
	}
	PublishPathCompass compass = new PublishPathCompass();
	String sURL = compass.getHttpUrl(content, 0);
%>

<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="destroytask_detail.jsp.title">撤销申请</title>
<script language="javascript">
<!--
	var m_nChannelId = 83;
	var m_nObjectId = 0;
	var m_nFlowDocId = 0;	
	
//-->
</script>

<%@ include file="/app/application/common/metaviewdata_addedit_include_resource.jsp"%>
<link href="metaviewdata_addedit_cn.css" rel="stylesheet" type="text/css" WCMAnt:locale="metaviewdata_addedit_$locale$.css"/>

<%@ include file="/app/application/common/metaviewdata_addedit_include_jquery_resource.jsp"%>



</head>
<script>
	//fix:firefox在使用getComputedStyle时，如果当前窗口不可见，那么将出现错误。
	//这导致编辑器和附件管理这个iframe嵌套在jquery的tab下时将引发错误。
	//目前通过单击指定的tab时再加载进行解决
	(function(){
		/**
		*container节点中是否包含dom节点
		*/
		var contains = function(container, dom){
			if(container.contains) return container.contains(dom);
			while(dom && dom.tagName != 'BODY'){
				if(container == dom) return true;
				dom = dom.parentNode;
			}
			return false;
		}
		//初始化tab控件
		jQuery.noConflict()(function() {
			jQuery("#tabs").tabs({
				show: function(event, ui) {
					//已经加载，则不再加载
					if(ui.panel.getAttribute("__init__")) return;
					ui.panel.setAttribute("__init__", "1");

					//遍历页面所有的延迟加载控件，包括编辑器及附件管理
					var components = com.trs.ui.ComponentMgr.getAllLazyRenderComponents();
					for(var index = 0; index < components.length; index++){
						var component = components[index];

						//对延迟加载的元素进行初始化
						var renderTo = $(component.getProperty('renderTo'));
						if(contains(ui.panel, renderTo)){
							component.render();
						}
					}

					// 延迟加载产品细览页面
					if(ui.panel.id == "tabs-5"){
						$("frmDetail").src = '<%=sURL%>';						
					}

					// 延迟加载处理信息页面
					if(ui.panel.id == "tabs-process"){
						var sURL  = "../../flowdoc/workflow_process_tracing.jsp";
						var oParameters = {
							flowid	: <%=nFlowDocId%>,
							title	: '<%=CMyString.filterForJs(sTitle)%>',
							ctype	: <%=DestroyTask.OBJ_TYPE%>,
							cid		: <%=nObjectId%>,
							cruser	: '<%=CMyString.filterForJs(task.getCrUserName())%>',
							crtime	: '<%=task.getCrTime()%>',
							isend	: 0,
							gunter_view:'false'
						};

						sURL = sURL + "?" + $toQueryStr(oParameters);
						$("frmProcess").src = sURL;	
					}
				}
			});
		});
	})();
</script>
<body>

<div class="box" id="data">
	
	
	<div id="tabs">
		<ul>
			
			<li>
				<a href="#tabs-4" WCMAnt:param="destroytask_detail.jsp.title">撤销请求</a>
			</li>
		
			<li>
				<a href="#tabs-5" WCMAnt:param="destroytask_detail.jsp.publishpage">已发布的页面</a>
			</li>		
			<%if(nFlowDocId>0){%>
			<li>
				<a href="#tabs-process" WCMAnt:param="destroytask_detail.jsp.process">处理情况</a>
			</li>
			<%}%>
		</ul>
		
		
		<div id="tabs-4">
			<div class="row" style=''>
				<div class="label" WCMAnt:param="destroytask_detail.jsp.poster">申请人</div>
				<div class="sep">:</div>
				<div class="value"style="color:blue">
					<%=task.getCrUserName()%>
				</div>						
			</div>

			<div class="row" style=''>
				<div class="label" WCMAnt:param="destroytask_detail.jsp.date">申请时间</div>
				<div class="sep">:</div>
				<div class="value"style="color:blue">
					<%=task.getCrTime()%>
				</div>						
			</div>			
			
			<div class="row" style=''>
				<div class="label" WCMAnt:param="destroytask_detail.jsp.destroydatas">申请撤销的文档</div>
				<div class="sep">:</div>
				<div class="value"style="color:blue">
					<%=sTitle%>
				</div>						
			</div>

			<div class="row" style=''>
				<div class="label" WCMAnt:param="destroytask_detail.jsp.reason">撤销理由</div>
				<div class="sep">:</div>
				<div class="value">
					<!--普通文本-->
					<script language="javascript">
					<!--
						new com.trs.ui.XTextArea({
							disabled : 1,
							name : 'Desc',
							value : '<%=CMyString.filterForJs(task.getDesc())%>',
							validation:"type:'string',required:'1',max_len:'300',desc:'撤销理由'"
						}).render();
					//-->
					</script>
				</div>						
			</div>
		</div>
		
		<div id="tabs-5">			
			<iframe id="frmDetail" style="height:470px;border:0;"></iframe>
		</div>		
		
		<%if(nFlowDocId>0){%>
		<div id="tabs-process">
			<div class="row">
					<iframe id="frmProcess" style="height:470px;border:0;"></iframe>
			</div>
		</div>
		<%}%>
		
		
</div>
<div class="buttonBox" id="CommandButtons" >
		<button onclick="window.close();" WCMAnt:param="destroytask_detail.jsp.close">关&nbsp;&nbsp;闭</button>
	</div>
</div>
</body>
</html>