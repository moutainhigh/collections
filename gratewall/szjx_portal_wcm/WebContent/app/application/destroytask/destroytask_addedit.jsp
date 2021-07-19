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

<%@ include file="/app/include/public_server.jsp"%>

<%
	// 1 参数解析
	String sValue = request.getParameter("ObjectType");
	if(CMyString.isEmpty(sValue)){
		throw new WCMException("没有传入ObjectType参数，大小写问题？还是漏了？");
	}
	int nObjectType = Integer.parseInt(sValue);
	
	String sRecIdsOfChnlDoc = request.getParameter("ObjectIds");
	if(CMyString.isEmpty(sRecIdsOfChnlDoc)){
		throw new WCMException("没有传入ObjectIds参数，大小写问题？还是漏了？");
	}

	// 工作流相关特性处理
	int nFlowDocId = 0;
	if(request.getParameter("FlowDocId") != null){
		nFlowDocId = Integer.parseInt(request.getParameter("FlowDocId"));
	}	

	// 2 判断权限
	
	// 3 获取需要撤销的文档，构造显示的标题以及对应的URL
	// 找ChnlDoc
	ChnlDocs chnlDocs = ChnlDocs.findByIds(null, sRecIdsOfChnlDoc);
	if (chnlDocs == null || chnlDocs.isEmpty()) {
		throw new WCMException("指定的文档都不存在！");
	}

	// 根据ChnlDoc找文档标题和URL
	StringBuffer sbObjectTitles = new StringBuffer(chnlDocs.size() * 30);
	StringBuffer sbObjectIds = new StringBuffer(chnlDocs.size() * 10);
	String[] pTitles = new String[chnlDocs.size()];
	String[] pURLs = new String[chnlDocs.size()];
	for (int i = 0, nSize = chnlDocs.size(); i < nSize; i++) {
		ChnlDoc chnldoc = (ChnlDoc) chnlDocs.getAt(i);
		if (chnldoc.getStatusId() != Status.STATUS_ID_PUBLISHED) {
			continue;
        }

		int nDocId = chnldoc.getDocId(), nChannelId = chnldoc
				.getChannelId();

		Document document = Document.findById(nDocId,
				"DocTitle,CrTime,DocChannel,DocId");
		if (document == null)
			continue;
		
		// 权限判断
		if (!DocumentAuthServer.hasRight(loginUser, chnldoc,
				WCMRightTypes.DOC_PUBLISH)) {
			throw new WCMException("您没有权限撤销这条信息：[" + document.getTitle()
					+ "]!");
		}

		pTitles[i] = document.getTitle();

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
		pURLs[i] = compass.getHttpUrl(content, 0);

		sbObjectTitles.append("撤销申请：");
		sbObjectTitles.append(pTitles[i]);
		sbObjectTitles.append("~");
	
		sbObjectIds.append(chnldoc.getId());
		sbObjectIds.append(",");		
	}
	if(sbObjectTitles.length()>0)sbObjectTitles.setLength(sbObjectTitles.length());
	if(sbObjectIds.length()>0)sbObjectIds.setLength(sbObjectIds.length());

	
%>

<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="destroytask_addedit.jsp.title">撤销申请</title>
<script language="javascript">
<!--
	var m_nChannelId = 83;
	var m_nObjectId = 0;
	var m_nFlowDocId = 0;	
	var m_pURLs = [
	<% 
		for (int i = 0, nSize = pURLs.length; i < nSize; i++) { 
			if(pURLs[i] == null)continue;
			out.println("'" +  pURLs[i] + "',");
		} 
	%>
	];


//-->
</script>

<%@ include file="/app/application/common/metaviewdata_addedit_include_resource.jsp"%>
<link href="metaviewdata_addedit_cn.css" rel="stylesheet" type="text/css" WCMAnt:locale="metaviewdata_addedit_$locale$.css"/>

<%@ include file="/app/application/common/metaviewdata_addedit_include_jquery_resource.jsp"%>


<SCRIPT LANGUAGE="JavaScript">
<!--
	var MyAjaxCaller = new com.trs.util.CAjaxCaller();
	MyAjaxCaller.addListener("aftersave", function(){window.close()});

	// 提交数据
	function saveApplyData(){
		// 再次校验数据
		if(!ValidationHelper.doValid('data')){
			return;
		}

		// 发出AJAX请求提交数据
		MyAjaxCaller.save('data');	
	}
//-->
</SCRIPT>

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
						$("frmDetail").src = m_pURLs[0];
						//"http://www.haier.com/cn/consumer/laundry/gtxyj/201107/t20110702_15352.shtml";
					}
				}
			});
		});
	})();
</script>

<SCRIPT LANGUAGE="JavaScript">
<!--
	function onDetailChange(){
		$("frmDetail").src = $("selDetail").value;				
	}
//-->
</SCRIPT>
<body>

<div class="box" id="data" 
	serviceId="wcm61_haier" methodName="applyDestoryTask">
	<input type="hidden" name="objectId" id="objectId" value="0" />
	<input type="hidden" name="flowdocId" id="flowdocId" value="0" />
	<input type="hidden" name="channelId" id="channelId" value="0" />
	<input type="hidden" name="ObjectIds" id="ObjectIds" value="<%=sbObjectIds%>" />
	<input type="hidden" name="ObjectTitles" id="ObjectTitles" value="<%=CMyString.filterForHTMLValue(sbObjectTitles.toString())%>" />
	<input type="hidden" name="ObjectType" id="ObjectType" value="<%=nObjectType%>" />
	
	
	<div id="tabs">
		<ul>
			
			<li>
				<a href="#tabs-4" WCMAnt:param="destroytask_addedit.jsp.title">撤销请求</a>
			</li>
		
			<li>
				<a href="#tabs-5" WCMAnt:param="destroytask_addedit.jsp.publishpage">已发布的页面</a>
			</li>		
			<%if(nFlowDocId>0){%>
			<li>
				<a href="#tabs-process" WCMAnt:param="destroytask_addedit.jsp.process">处理情况</a>
			</li>
			<%}%>
		</ul>
		
		
		<div id="tabs-4">
			<div class="row" style=''>
				<div class="label" WCMAnt:param="destroytask_addedit.jsp.destroydatas">申请撤销的文档</div>
				<div class="sep">：</div>
				<div class="value">
					<OL style="margin-top:0;color:blue">
					<% for (int i = 0, nSize = pTitles.length; i < nSize; i++) { if(pTitles[i] == null)continue;%>
					<LI style="margin-bottom:8px;"><%=CMyString.filterForHTMLValue(pTitles[i])%></LI>
					<% } %>
					</OL>
					
				</div>						
			</div>

			<div class="row" style=''>
				<div class="label" WCMAnt:param="destroytask_addedit.jsp.reason">撤销理由</div>
				<div class="sep">:</div>
				<div class="value">
					<!--普通文本-->
					<script language="javascript">
					<!--
						new com.trs.ui.XTextArea({
							disabled : 0,
							name : 'Desc',
							value : '',
							validation:"type:'string',required:'1',max_len:'300',desc:'撤销理由'"
						}).render();
					//-->
					</script>
				</div>						
			</div>
		</div>
		
		<div id="tabs-5">			
			<div class="label" WCMAnt:param="destroytask_addedit.jsp.check1">查看</div> :
			<select name="selDetail" id="selDetail" onchange="onDetailChange();" style="color:blue">
				<% for (int i = 0, nSize = pTitles.length; i < nSize; i++) { if(pTitles[i] == null)continue;%>
				<option value="<%=pURLs[i]%>"><%=pTitles[i]%></option>
				<% } %>
			</select>
			
			<div style="display:inline"  WCMAnt:param="destroytask_addedit.jsp.check2">的发布页面</div>
			<BR/><BR/>
			<iframe id="frmDetail" style="height:470px;border:0;"></iframe>
		</div>		
		
		<%if(nFlowDocId>0){%>
		<div id="tabs-process">
			<div class="row">
					<script language="javascript">
					<!--
						var oProcessHTML = new com.trs.ui.XProcess({
							name : 'workflowprocess',
							objectId : '0',
							FlowDocId : '<%=nFlowDocId%>'
						});
						//oProcessHTML.render();
					//-->
					</script>
			</div>
		</div>
		<%}%>
		
		
</div>
<div class="buttonBox" id="CommandButtons" >
		<button id="btnSaveAndClose" onclick="saveApplyData(true);return false;" WCMAnt:param="destroytask_addedit.jsp.saveclose">申请撤销</button>&nbsp;&nbsp;&nbsp;
		<button onclick="window.close();" WCMAnt:param="destroytask_addedit.jsp.close">关&nbsp;&nbsp;闭</button>
	</div>
</div>
</body>
</html>