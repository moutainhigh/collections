<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="/app/application/common/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.govinfo.GovInfoViewFinder" %>
<%@ page import="com.trs.components.metadata.service.UserRelateInfoMaker" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ include file="/app/application/common/metaviewdata_addedit_include_import.jsp"%>
<%@ include file="/app/application/common/metaviewdata_addedit_include_data_build.jsp"%>
<%@ include file="/app/application/common/metaviewdata_addedit_include_function.jsp"%>
<%@ page buffer="64kb" %>
<%!int m_nViewId = 5;%>
<%@ include file="/app/application/common/metaviewdata_addedit_inflow_right_redirect.jsp"%>
<%@ include file="metaviewdata_addedit_govinfo_classinfo.jsp"%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%-- /*wenyh@2011-07-14 force the IE9 use a IE8 document mode.*/ --%>
<meta http-equiv="X-UA-Compatible" content="IE=8">
<title WCMAnt:param="metaviewdata_addedit.jsp.title">新建/修改[政府公开信息]数据</title>
<script language="javascript">
<!--
	var m_nChannelId = DocChannelId = <%=nChannelId%>;
	window.channelId = <%=nChannelId%>;
	window.DocChannelId = <%=nChannelId%>;
	var m_nObjectId = <%=nObjectId%>;
	var m_nFlowDocId = <%=nFlowDocId%>;
	var m_nViewId = <%=m_nViewId%>;
	var bPhotoPluginsEnable = <%=bPhotoPluginsEnable%>;
	var sOrgancat = "<%=sOrgancat%>";
	var sPublisher = "<%=sPublisher%>";
	var nOrgancat = <%=nOrgancat%>;
	var bAdd = <%=bAdd%>;
	var sShowSubcat = "<%=sShowSubcat%>";
	var nSubcat = <%=nSubcat%>;
	var sShowThemecat = "<%=sShowThemecat%>";
	var nThemecat = <%=nThemecat%>;
//-->
</script>
<%@ include file="/app/application/common/metaviewdata_addedit_include_resource.jsp"%>
<script language="javascript" src="metaviewdata_addedit.js" type="text/javascript"></script>
<link href="metaviewdata_addedit_cn.css" rel="stylesheet" type="text/css" WCMAnt:locale="metaviewdata_addedit_$locale$.css"/>
<link href="metaviewdata_addedit.css" rel="stylesheet" type="text/css"/>
<%@ include file="/app/application/common/metaviewdata_addedit_include_jquery_resource.jsp"%>
<script src="../../individuation/customize_config.jsp"></script>
<script src="/app/js/source/wcmlib/WCMConstants.js"></script>
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
				}
			});
		});
	})();
	<%-- /*热词等按钮请求路径*/ --%>
	var editorCfg = {basePath:WCMConstants.WCM6_PATH + 'document/'};
</script>
<body>
<div class="box" id="data" action="metaviewdata_addedit_dowith.jsp" serviceId="wcm61_metaviewdata" methodName="saveMetaViewData">
	<input type="hidden" name="objectId" id="objectId" value="<%=nObjectId%>" />
	<input type="hidden" name="flowdocId" id="flowdocId" value="<%=nFlowDocId%>" />
	<input type="hidden" name="channelId" id="channelId" value="<%=nChannelId%>" />
	<input type="hidden" name="docStatus" id="docStatus" value="<%=nCurrStatus%>" />
	<input type="hidden" name="bCanPub" id="bCanPub" value="<%=bCanPub%>" />
	<input type="hidden" name="DocLevel" id="DocLevel" value="" />
	
	<input type="hidden" name="ToUserIds" id="ToUserIds" value="" />
	<input type="hidden" name="NotifyTypes" id="NotifyTypes" value="" />
	<input type="hidden" name="startInFlow" id="startInFlow" value="true" />
	<input type="hidden" name="Force2start" id="Force2start" value="" />

	<div id="tabs">
		<ul>
			
				<li>
					<a href="#tabs-21">政府信息公开</a>
				</li>
			
				<li>
					<a href="#tabs-22">详细内容</a>
				</li>
			
			
				<li>
					<a href="#tabs-picmgr"  WCMAnt:param="metaviewdata_addedit.jsp.pic_manage">图片管理</a>
				</li>
			
			
				<li>
					<a href="#tabs-filemgr"  WCMAnt:param="metaviewdata_addedit.jsp.file_manage">文件管理</a>
				</li>
			
			
				<li>
					<a href="#tabs-linkmgr"  WCMAnt:param="metaviewdata_addedit.jsp.link_manage">链接管理</a>
				</li>
			
			<li>
				<a href="#tabs-quote" WCMAnt:param="metaviewdata_addedit.jsp.quote">另存为</a>
			</li>
			<li>
				<a href="#tabs-others" WCMAnt:param="metaviewdata_addedit.jsp.other">其他</a>
			</li>
			<%if(nFlowDocId>0){%>
			<li>
				<a href="#tabs-preview"  WCMAnt:param="METAVIEWDATA_LIST.HEAD.PREVIEW">预览</a>
			</li>
			<li>
				<a href="#tabs-process" WCMAnt:param="metaviewdata_addedit.jsp.process">流转信息</a>
			</li>
			<%}%>
		</ul>
		<%if(nFlowDocId>0){%>
		<div id="tabs-process">
			<div class="row">
					<script language="javascript">
					<!--
						new com.trs.ui.XProcess({
							name : 'workflowprocess',
							objectId : '<%=nObjectId%>',
							FlowDocId : '<%=nFlowDocId%>'
						}).render();
					//-->
					</script>
			</div>
		</div>
		<div id="tabs-preview">
			<div class="row">
					<script language="javascript">
					<!--
						new com.trs.ui.XPreview({
							lazyRender : true,
							name : 'datapreview',
							folderId : '<%=nChannelId%>',
							folderType : 101,
							objectId : '<%=nChnlDocId%>'
						}).render();
					//-->
					</script>
			</div>
		</div>
		<%}%>

		
			<div id="tabs-21">
				
				
					<fieldset><legend>基本信息</legend>
						<!-- 过滤掉当复杂编辑器字段使用二级分组时，显示出来"显示名称" 详见JIRA：METADATA-93 -->
						
						
						
							<div class="row" style=''>
								<div class="label">索引</div>
								<div class="sep">：</div>
								<div class="value">
									<!--普通文本-->
<script language="javascript">
<!--
	new com.trs.ui.XText({
		disabled : 0,
		name : 'idxID',
		value : '<%=CMyString.filterForJs(getValue(obj, "idxID", ""))%>',
		validation:"type:'string',required:'0',max_len:'200',desc:'索引'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">名称</div>
								<div class="sep">：</div>
								<div class="value">
									<!--普通文本-->
<script language="javascript">
<!--
	new com.trs.ui.XText({
		disabled : 0,
		name : 'Title',
		value : '<%=CMyString.filterForJs(getValue(obj, "Title", ""))%>',
		validation:"type:'string',required:'1',max_len:'200',desc:'名称'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">文号</div>
								<div class="sep">：</div>
								<div class="value">
									<!--普通文本-->
<script language="javascript">
<!--
	new com.trs.ui.XText({
		disabled : 0,
		name : 'fileNum',
		value : '<%=CMyString.filterForJs(getValue(obj, "fileNum", ""))%>',
		validation:"type:'string',required:'0',max_len:'200',desc:'文号'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">生效期</div>
								<div class="sep">：</div>
								<div class="value">
									<!--时间-->
<script language="javascript">
<!--
	new com.trs.ui.XCalendar({
		disabled : 0,
		name : 'efectdate',
		elname : '生效期',
		value : '<%=CMyString.filterForJs(getCalendar(obj, "efectdate", "", "11"))%>',
		withtime: (11 == 1 ? true : false),
		dtFmt: (11 == 1 ? 'yyyy-mm-dd HH:MM:ss' : 'yyyy-mm-dd'),
		validation:"type:'string',required:'0',desc:'生效期'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">生成日期</div>
								<div class="sep">：</div>
								<div class="value">
									<!--时间-->
<script language="javascript">
<!--
	new com.trs.ui.XCalendar({
		disabled : 0,
		name : 'PubDate',
		elname : '生成日期',
		value : '<%=CMyString.filterForJs(getCalendar(obj, "PubDate", "", "11"))%>',
		withtime: (11 == 1 ? true : false),
		dtFmt: (11 == 1 ? 'yyyy-mm-dd HH:MM:ss' : 'yyyy-mm-dd'),
		validation:"type:'string',required:'1',desc:'生成日期'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">内容概述</div>
								<div class="sep">：</div>
								<div class="value">
									<!--多行文本-->
<script language="javascript">
<!--
	new com.trs.ui.XTextArea({
		disabled : 0,
		name : 'Description',
		value : '<%=CMyString.filterForJs(getValue(obj, "Description", ""))%>',
		validation:"type:'string',required:'0',max_len:'1000',desc:'内容概述'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">关键字</div>
								<div class="sep">：</div>
								<div class="value">
									<!--普通文本-->
<script language="javascript">
<!--
	new com.trs.ui.XText({
		disabled : 0,
		name : 'Keywords',
		value : '<%=CMyString.filterForJs(getValue(obj, "Keywords", ""))%>',
		validation:"type:'string',required:'0',max_len:'200',desc:'关键字'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">发布机构</div>
								<div class="sep">：</div>
								<div class="value">
									<!--普通文本-->
<script language="javascript">
<!--
	new com.trs.ui.XText({
		disabled : 0,
		name : 'Publisher',
		value : '<%=CMyString.filterForJs(getValue(obj, "Publisher", ""))%>',
		validation:"type:'string',required:'1',max_len:'200',desc:'发布机构'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
					</fieldset>
				
					<fieldset><legend>所属关系</legend>
						<!-- 过滤掉当复杂编辑器字段使用二级分组时，显示出来"显示名称" 详见JIRA：METADATA-93 -->
						
						
						
							<div class="row" style=''>
								<div class="label">所属主题</div>
								<div class="sep">：</div>
								<div class="value">
									<!--分类法-->
<%
	String mysubcat = getValue(obj, "subcat", "");
	String sParentOptionalsubcat = getClassInfoSelectType("subcat");
%>
<script language="javascript">
<!--
	new com.trs.ui.XClassInfo({
		disabled : 0,
		name : 'subcat',
		value : '<%=CMyString.filterForJs(mysubcat)%>', 
		desc : '<%=CMyString.filterForJs(getClassInfoDesc(obj, "subcat", ""))%>', 
		rootId : '81', 
		selectType : '<%=CMyString.filterForJs(sParentOptionalsubcat)%>',
		treeType : '0',
		validation:"type:'string',required:'1',max_len:'200',desc:'所属主题'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">所属体裁</div>
								<div class="sep">：</div>
								<div class="value">
									<!--分类法-->
<%
	String mythemecat = getValue(obj, "themecat", "");
	String sParentOptionalthemecat = getClassInfoSelectType("themecat");
%>
<script language="javascript">
<!--
	new com.trs.ui.XClassInfo({
		disabled : 0,
		name : 'themecat',
		value : '<%=CMyString.filterForJs(mythemecat)%>', 
		desc : '<%=CMyString.filterForJs(getClassInfoDesc(obj, "themecat", ""))%>', 
		rootId : '216', 
		selectType : '<%=CMyString.filterForJs(sParentOptionalthemecat)%>',
		treeType : '0',
		validation:"type:'string',required:'1',max_len:'200',desc:'所属体裁'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">所属机构</div>
								<div class="sep">：</div>
								<div class="value">
									<!--分类法-->
<%
	String myorgancat = getValue(obj, "organcat", "");
	String sParentOptionalorgancat = getClassInfoSelectType("organcat");
%>
<script language="javascript">
<!--
	new com.trs.ui.XClassInfo({
		disabled : 0,
		name : 'organcat',
		value : '<%=CMyString.filterForJs(myorgancat)%>', 
		desc : '<%=CMyString.filterForJs(getClassInfoDesc(obj, "organcat", ""))%>', 
		rootId : '1', 
		selectType : '<%=CMyString.filterForJs(sParentOptionalorgancat)%>',
		treeType : '1',
		validation:"type:'string',required:'1',max_len:'200',desc:'所属机构'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
					</fieldset>
				
					<fieldset><legend>其他信息</legend>
						<!-- 过滤掉当复杂编辑器字段使用二级分组时，显示出来"显示名称" 详见JIRA：METADATA-93 -->
						
						
						
							<div class="row" style=''>
								<div class="label">著录日期</div>
								<div class="sep">：</div>
								<div class="value">
									<!--时间-->
<script language="javascript">
<!--
	new com.trs.ui.XCalendar({
		disabled : 0,
		name : 'mdUpdTime',
		elname : '著录日期',
		value : '<%=CMyString.filterForJs(getCalendar(obj, "mdUpdTime", "", "11"))%>',
		withtime: (11 == 1 ? true : false),
		dtFmt: (11 == 1 ? 'yyyy-mm-dd HH:MM:ss' : 'yyyy-mm-dd'),
		validation:"type:'string',required:'0',desc:'著录日期'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">相关信息索引</div>
								<div class="sep">：</div>
								<div class="value">
									<!--普通文本-->
<script language="javascript">
<!--
	new com.trs.ui.XText({
		disabled : 0,
		name : 'RidxId',
		value : '<%=CMyString.filterForJs(getValue(obj, "RidxId", ""))%>',
		validation:"type:'string',required:'0',max_len:'200',desc:'相关信息索引'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">相关信息名称</div>
								<div class="sep">：</div>
								<div class="value">
									<!--多行文本-->
<script language="javascript">
<!--
	new com.trs.ui.XTextArea({
		disabled : 0,
		name : 'Rtitle',
		value : '<%=CMyString.filterForJs(getValue(obj, "Rtitle", ""))%>',
		validation:"type:'string',required:'0',max_len:'1000',desc:'相关信息名称'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">位置关键词</div>
								<div class="sep">：</div>
								<div class="value">
									<!--普通文本-->
<script language="javascript">
<!--
	new com.trs.ui.XText({
		disabled : 0,
		name : 'placekey',
		value : '<%=CMyString.filterForJs(getValue(obj, "placekey", ""))%>',
		validation:"type:'string',required:'0',max_len:'200',desc:'位置关键词'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">获取方式</div>
								<div class="sep">：</div>
								<div class="value">
									<!--普通文本-->
<script language="javascript">
<!--
	new com.trs.ui.XText({
		disabled : 0,
		name : 'acesmthd',
		value : '<%=CMyString.filterForJs(getValue(obj, "acesmthd", ""))%>',
		validation:"type:'string',required:'0',max_len:'200',desc:'获取方式'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
						
						
							<div class="row" style=''>
								<div class="label">废止日期</div>
								<div class="sep">：</div>
								<div class="value">
									<!--时间-->
<script language="javascript">
<!--
	new com.trs.ui.XCalendar({
		disabled : 0,
		name : 'Abolidate',
		elname : '废止日期',
		value : '<%=CMyString.filterForJs(getCalendar(obj, "Abolidate", "", "11"))%>',
		withtime: (11 == 1 ? true : false),
		dtFmt: (11 == 1 ? 'yyyy-mm-dd HH:MM:ss' : 'yyyy-mm-dd'),
		validation:"type:'string',required:'0',desc:'废止日期'"
	}).render();
//-->
</script>
								</div>
							</div>
						
						
						
						
						
					</fieldset>
				
			</div>
		
			<div id="tabs-22">
				
					<div class="row" style=''>
						
						
								<!--复杂编辑器-->
<script language="javascript">
<!--
	new com.trs.ui.XEditor({
		lazyRender : true,
		disabled : 0,
		name : 'Content', 
		params : {
			channelId : m_nChannelId, 
			featureViewIds : '', 
			featureChnlIds : ''
		},
		validation:"type:'string',required:'1',desc:'正文内容'",
		value : '<%=CMyString.filterForJs(makeHtmlCon(getValue(obj, "Content", "")))%>'
	}).render();
//-->
</script>
						
						
					</div>
				
				
			</div>
		

		<!-- 图片管理 -->
		
			<div id="tabs-picmgr">
				<div class="row">
					<div>
						<script language="javascript">
						<!--
							new com.trs.ui.XImgAppendixes({
								lazyRender : true,
								name : 'myImgAppendixes',
								objectId :'<%=nObjectId%>',
								type:20
							}).render();
						//-->
						</script>
					</div>
				</div>
			</div>
		
		
		<!-- 文件管理 -->
		
			<div id="tabs-filemgr">
				<div class="row">
					<div>
						<script language="javascript">
						<!--
							new com.trs.ui.XDocMultiAppendixes({
								lazyRender : true,
								name : 'myDocMultiAppendixes',
								objectId :'<%=nObjectId%>',
								type:10
							}).render();
						//-->
						</script>
					</div>
				</div>
			</div>
		

		<!-- 链接管理 -->
		
			<div id="tabs-linkmgr">
				<div class="row">
					<div>
						<script language="javascript">
						<!--
							new com.trs.ui.XDocLinkAppendixes({
								lazyRender : true,
								name : 'myDocLinkAppendixes',
								objectId :'<%=nObjectId%>',
								type:40
							}).render();
						//-->
						</script>
					</div>
				</div>
			</div>
		
		<!-- 另存为 -->
		<div id="tabs-quote">
			<div class="row">
				<div class="label" WCMAnt:param="metaviewdata_addedit.jsp.quote">另存为</div>
				<div class="sep">：</div>
				<div class="value">
					<script language="javascript">
					<!--
						new com.trs.ui.XQuote({
							lazyRender : true,
							name : 'myQuote',
							currchnlid : <%=nChannelId%>,
							objectId : '<%=nObjectId%>'
						}).render();
					//-->
					</script>
				</div>
			</div>
		</div>
		<!-- 其他 -->
		<div id="tabs-others">
			
				<%
					if(channels != null && channels.size() > 1){
				%>
					<div class="row">
						<div class="label" WCMAnt:param="metaviewdata_addedit.jsp.choosechannel">所属栏目</div>
						<div class="sep">：</div>
						<div class="value">
							<select name="channelSelect" id="selectChannel">
								<option value=0 WCMAnt:param="metaviewdata_addedit.jsp.pleaseselectchnl">请选择栏目</option> 
								<%
									for(int k=0;k< channels.size();k++){
										Channel oChannel = (Channel)channels.getAt(k);
										int currChannelId = oChannel.getId();
										String sChannelDesc = oChannel.getDesc();
								%>
								<option value='<%=currChannelId%>' <%=(nChannelId == currChannelId)?"selected='selected'":"" %> ><%=CMyString.transDisplay(sChannelDesc)%></option>
								<%
									}
								%>
							</select>
						</div>
					</div>
				<%
					}	
				%>

					<div class="row">
						<div class="label" WCMAnt:param="metaviewdata_addedit.jsp.choosetemp">选择模板</div>
						<div class="sep">：</div>
						<div class="value">
							<%@ include file="/app/application/common/TemplateSelectHandler.jsp"%>
						</div>
					</div>

					<!-- 定时发布撤稿设置 -->
				<%if(channel != null){%>
					<div class="row">
						<%@ include file="/app/application/common/PublishConfigHandler.jsp"%>
					</div>
				<%}%>
				<%if(nChannelId > 0 && channels == null){%>
					<%@ include file="/app/application/common/doclevel_set.jsp"%>
					<div class="row">
					<%@ include file="/app/application/common/TopSetHandler.jsp"%>
					</div>
				<%}%>
				
				<!-- 工作流流转方式设置相关 -->
				<%
					// 编辑草稿状态的文档，也可以指定流转参数信息
					if(nDocId == 0 || (nDocId > 0 && nCurrStatus == Status.STATUS_ID_DRAFT)){
						String sFlowSetting = ConfigServer.getServer().getSysConfigValue("DOC_ADD_FLOW_SETTING", "0");
						if(sFlowSetting != null && sFlowSetting.equals("1") && nFlowId>0) {
				%>
					<div class="row">
						<div class="label">流转信息</div>
						<div class="sep">：</div>
						<div class="value">
							<%@ include file="/app/application/common/FlowSetHandler.jsp"%>
						</div>
					</div>
				<%
						}
					}
				%>
		</div>
<div class="buttonBox" id="CommandButtons" >
<%
boolean bIsCanPreview = DocumentAuthServer.hasRight(loginUser,channel,document,WCMRightTypes.DOC_PREVIEW);
if(bIsCanPreview){%>		
		<button id="preview" onclick="preview();return false;">预览</button>&nbsp;&nbsp;&nbsp;
<%}%>
<%if(bShowSaveDraftBtn){%>
		<button id="saveAsDraft" onclick="saveAsDraft();return false;" WCMAnt:param="metaviewdata_addedit.jsp.saveAsDraft">存草稿</button>&nbsp;&nbsp;&nbsp;
<%}%>
		<button id="btnSaveAndClose" onclick="saveData(true);return false;" WCMAnt:param="metaviewdata_addedit.jsp.saveclose">保存并关闭</button>&nbsp;&nbsp;&nbsp;
<%if(nFlowDocId == 0){%>
		<button id="btnSaveAndNew" onclick="saveData(false);return false;" WCMAnt:param="metaviewdata_addedit.jsp.savenew">保存并新建</button>&nbsp;&nbsp;&nbsp;
<%}%>
<%if(bCanPub){%>
		<button id="btnSaveAndPublish" onclick="saveData(true,true);return false;" WCMAnt:param="metaviewdata_addedit.jsp.savePublish">保存并发布</button>&nbsp;&nbsp;&nbsp;
<%}%>
<%if(showSaveAndFlow(nObjectId, nChannelId)){%>
		<button id="btnSaveAndFlow" onclick="saveAndFlow();return false;"
		WCMAnt:param="metaviewdata_addedit.jsp.saveFlow">保存并流转</button>&nbsp;&nbsp;&nbsp;
<%}%>
		<button onclick="window.close();" WCMAnt:param="metaviewdata_addedit.jsp.close">关&nbsp;&nbsp;闭</button>
	</div>
</div>
<%
	if(nObjectId == 0 && processor.getParam("ClassInfoId", 0) > 0) {
		int nClassInfoId = processor.getParam("ClassInfoId", 0);
		ClassInfo chassInfo = ClassInfo.findById(nClassInfoId);
		int nRootClassInfoId = 0;
		if (chassInfo != null) nRootClassInfoId = chassInfo.getRootId();
		String sRootClassInfoFieldName = getRootClassInfoFieldName(m_nViewId,nRootClassInfoId);
		if(sRootClassInfoFieldName != null){
%>
	<script>
		Event.observe(window, 'load', function(event){
			var objField = com.trs.ui.ComponentMgr.get('<%=sRootClassInfoFieldName%>');
			if(!objField) return;
			objField.setValue({
				value : <%=nClassInfoId%>, //nClassInfoId为分类法字段值的id
				desc : '<%=chassInfo.getDesc()+"[id="+nClassInfoId+"]"%>' //sClassInfoDesc为分类法字段值的描述
			});
			
		});		
	</script>
<%}}%>
<script language="javascript">
<!--
	initIndexField();
	initFieldValue();
//-->
</script>
</body>
</html>
<%!
	private Channel findChannelByDocId(int _nDocId) throws WCMException {
		String sql = "select DOCCHANNEL from WCMCHNLDOC where DOCID=? and MODAL=1";
		DBManager dbman = DBManager.getDBManager();
		java.util.List params = new java.util.ArrayList(2);
		params.add(new Integer(_nDocId));
		int nChannelId = dbman.sqlExecuteQuery(sql,params,0);
		return Channel.findById(nChannelId);
	}
%>