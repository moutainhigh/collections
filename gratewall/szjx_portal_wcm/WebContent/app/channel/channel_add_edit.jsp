<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error_for_dialog.jsp"%>
<%@include file="../include/public_processor.jsp"%>
<%@include file="channel_add_edit_init.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title WCMAnt:param="channel_add_edit.jsp.title">add or edit a certain channel</title>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
<script src="../../app/js/data/locale/channel.js"></script>
<script src="../../app/js/data/locale/flow.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/core/LockUtil.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/source/wcmlib/tabpanel/TabPanel.js"></script>
<link href="../../app/js/source/wcmlib/tabpanel/resource/tabpanel.css" rel="stylesheet" type="text/css" />
<script src="../../app/template/TemplateSelector.js"></script>
<script src="../../app/flow/flow_addedit_select.js"></script>
<script src="../../app/js/source/wcmlib/pagecontext/BlurMgr.js"></script>

<script language="javascript">
<!--
	var hasEditRight = <%=hasEditRight%>;	
	var nObjectId = <%=oChannel.getId()%>;
//-->
</script>
<script language="javascript" src="channel_add_edit.js"></script>
<link href="channel_add_edit.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../../app/js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../../app/js/easyversion/calendar_lang/$locale$.js" type="text/javascript"></script>
<script language="javascript" src="../../app/js/easyversion/calendar2.js" type="text/javascript"></script>
<link href="../../app/js/easyversion/resource/calendar.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.tab-body {
		height:98% !important;/*修正ie9、10下多出的滚动条*/
	}
</style>
<script language="javascript">
<!--
    Event.observe(window, 'load', function(){
        new wcm.TabPanel({
            id : 'tabPanel',
            activeTab : 'tab-general'
        }).show();
        setTimeout(function(){
			try{
				$('chnlName').focus();
				$('chnlName').select();
			}catch(err){
				//有可能由于不能编辑(例如对象已经被其他用户锁定), 可以直接跳过
			}
		},0);
    });
//-->
</script>
<%@include file="channel_add_edit_include_4_extension.jsp"%>
</head>
<body>
<%
	try{
%>
<form id="addEditForm" name="form1" method="post" action="">
	<input type="hidden" name="objectId" id="objectId" value="<%=oChannel.getId()%>">
	<input type="hidden" name="siteId" id="siteId" value="<%=oChannel.getSiteId()%>">
	<input type="hidden" name="siteType" id="siteType" value="<%=oSite.getType()%>">
	<input type="hidden" name="parentId" id="parentId" value="<%=oChannel.getParentId()%>">
	<input type="hidden" name="status" id="status" value="<%=nStatus%>">
	<input type="hidden" name="ChnlNameFieldLen" id="ChnlNameFieldLen" value="<%=nDBChnlNameFieldLen%>">
	<input type="hidden" name="readOnly" id="readOnly" value="">
	<input type="hidden" name="outlineTemplates" id="outlineTemplates">	
	<input type="hidden" name="onlyManager" id="onlyManager"/>
	<input type="hidden" name="InfoviewId" id="InfoviewId"/>
	<div class="wcm-tab-panel" id="tabPanel" style="display:none;">
		<div class="head-box" style="padding-left:20px;">
			<div class="tab-head" item="tab-general"><a href="#" WCMAnt:param="channel_add_edit.jsp.norman">常规</a></div>
			<div class="tab-head" item="tab-advance" id="tab-advance-header"><a href="#" WCMAnt:param="channel_add_edit.jsp.high">高级</a></div>
		</div>
	    <div class="body-box tabbox">
	        <div class="tab-body" id="tab-general">
				<div class="block_box">
					<div class="header_row_left">
						<div class="header_row_right">
							<div class="header_row_center">
								<div class="header_row_text" WCMAnt:param="channel_add_edit.jsp.desc">基本描述</div>
							</div>
						</div>
					</div>
					<div class="body_box">
						<div>
							<span class="spAttrClue spAttrClueTitle" WCMAnt:param="channel_add_edit.jsp.site">
								所属站点：
							</span>
							<span class="length_limit" title="<%=sSiteName%>">
								<%=CMyString.transDisplay(sSiteDesc)%>
							</span>
						</div>
						<div>
							<span class="spAttrClue spAttrClueTitle" WCMAnt:param="channel_add_edit.jsp.parentchnl">
								父栏目：
							</span>
							<span class="spAttrItem length_limit" title="<%=sParentName%>">
								<%=sParentDesc%>
							</span>
						</div>
						<div>
							<span class="spAttrClue spAttrClueTitle" WCMAnt:param="channel_add_edit.jsp.cruser">
								创建者：
							</span>
							<span class="spAttrItem"><%=CMyString.transDisplay(sCrUser)%></span>
						</div>
						<div>
							<span class="spAttrClue spAttrClueTitle" WCMAnt:param="channel_add_edit.jsp.type">
								栏目类型：
							</span>
							<span id="spSelType" style="display:none;">
								<select name="chnlType" id="chnlType" class="kuang_as" value="<%=nChnlType%>" _value="<%=nChnlType%>">
									<option value="0" WCMAnt:param="channel_add_edit.jsp.normanchnl">普通栏目</option>
									<%if(!bExistPicsChannel){%>
									<option value="1" WCMAnt:param="channel_add_edit.jsp.image">图片新闻</option>
									<%}%>
									<%if(!bExistNewsChannel){%>
									<option value="2" WCMAnt:param="channel_add_edit.jsp.first">头条新闻</option>
									<%}%>
									<option value="11" WCMAnt:param="channel_add_edit.jsp.link">链接栏目</option>
								</select>
								<script language="javascript">
								try{
									<%if(infoviews!=null && infoviews.size()>0){%>
										var eOption = document.createElement("OPTION");
										$('chnlType').options.add(eOption);
										eOption.value = 13;
										eOption.innerText = wcm.LANG.CHANNEL_52||'自定义表单栏目';
									<%}%>
								}catch(err){
									//just skip it
								}
							</script>
							</span>
							<span id="spTextType">
								<span id="spChannelType"></span>
							</span>
						</div>
						<div>
							<span class="spAttrClue spAttrClueTitle" WCMAnt:param="channel_add_edit.jsp.name">
								唯一标识：
							</span>
							<span class="spAttrItem spAttrItemCs">
								<%
									boolean bCanEditChnlName = true;
									if(!oChannel.isAddMode()){//修改模式下
										
										String sCanEditChnlName = ConfigServer.getServer().getSysConfigValue("CHNLNAME_CAN_EDIT", "true");
										if(sCanEditChnlName.equalsIgnoreCase("false") || sCanEditChnlName.equals("0")){
											bCanEditChnlName = false;
										}
									}
								%>
								<%if(bCanEditChnlName){%>
								<input name="chnlName" id="chnlName" type="text" class="kuang_as" value="<%=CMyString.filterForHTMLValue(sChnlName)%>"/>
								<%}else{%>
									<div style="display:none;"><input name="chnlName" id="chnlName" type="hidden" class="kuang_as" value="<%=CMyString.filterForHTMLValue(sChnlName)%>"/></div>
									<span class="length_limit" title="<%=CMyString.filterForHTMLValue(sChnlName)%>">
										<%=CMyString.transDisplay(sChnlName)%>
									</span>
								<%}%>
							</span>
						</div>
						<div>
							<span class="spAttrClue spAttrClueTitle" WCMAnt:param="channel_add_edit.jsp.showname">
								显示名称：
							</span>
							<span class="spAttrItem spAttrItemCs">
								<input name="chnlDesc" id="chnlDesc" type="text" class="kuang_as" value="<%=CMyString.filterForHTMLValue(sChnlDesc)%>"/>
							</span>
						</div>
						<div  id="divLinkUrl">
							<span class="spAttrClue spAttrClueTitle" WCMAnt:param="channel_add_edit.jsp.linkurl">
								链接地址：
							</span>
							<span class="spAttrItem spAttrItemCs">
								<input name="linkUrl" id="linkUrl" type="text" class="kuang_as" value="<%=CMyString.filterForHTMLValue(sLinkUrl)%>"/>
							</span>
							<span id="urlMsg" style="display:block;padding-left:10px;"></span>
						</div>
						<div id="divSelectInfoviews" style="display: none">
							<span class="spAttrClue spAttrClueTitle" WCMAnt:param="channel_add_edit.jsp.selecttable">
								选择表单：
							</span>
							<span class="spAttrItem">
								<select id="selInfoviews" style="width:170px;">
									<option value="-1" WCMAnt:param="channel_add_edit.jsp.slect">==请选择==</option>
								<%
									if(infoviews!=null && infoviews.size()>0){
										for(int i=0; i <infoviews.size(); i++){//start for1
											   Element infoview = (Element)infoviews.get(i);
											   String infoviewName = CMyString.transDisplay(infoview.elementText("Name"));
											   String infoviewId = CMyString.transDisplay(infoview.elementText("Id"));
											   String sUsed = infoview.elementText("Used");
								%>
									<option value="<%=infoviewId%>" <%=sUsed!=null && sUsed.equals("true") ? "selected" : ""%>><%=infoviewName%></option>
								<%
										}
									}

								%>
								</select><span style="color: red">*</span>
							</span>
						</div>
						<div id="divBasicChannelOrder">
							<span class="spAttrClue spAttrClueTitle" WCMAnt:param="channel_add_edit.jsp.fontchnl">
								前一栏目：
							</span>
							<span class="spAttrItem">
								<select name="chnlOrder" id="chnlOrder" style="width:170px;" _value="<%=nOrder%>">
									<option value="-1" WCMAnt:param="channel_add_edit.jsp.font">最前面</option>
									<%
										for (int i = 0, length = oSiblings.size(); i < length; i++) {
											Channel channel = (Channel) oSiblings.getAt(i);
											if (channel == null)
												continue;
									%>
										<option value="<%=channel.getOrder()%>"><%=CMyString.transDisplay(channel.getDesc())%></option>
									<%
										}
									%>
								</select>
							</span>
						</div>
						<%
						if(bHasExtendFieldRight && oChannel.isAddMode()){
						%>
						<span id="extendfieldbox">
							<span class="spAttrPadding_less" >&nbsp;</span>
							<span class="spAttrItem">
								<input id="inheritextendfield" type="checkbox" name="inheritextendfield" isboolean="1" />
								<label for="inheritextendfield" WCMAnt:param="channel_add_edit.jsp.inheritef" WCMAnt:paramattr="title:channel_add_edit.jsp.inheriteftips" title="继承父扩展字段">继承父扩展字段</label>
							</span>
						</span>
						<%}%>

						<span id="divClusterChannel" style="display: none">
							<span class="spAttrPadding_less" >&nbsp;</span>
							<span class="spAttrItem">
								<input id="chkIsCluster" type="checkbox" name="IsCluster" isboolean="1" <%=oChannel.isCluster()?"checked":""%>> <label for="chkIsCluster" WCMAnt:param="channel_add_edit.jsp.isCluster">作为聚合栏目</label>
							</span>
						</span>	
						<span id="ContainsChildren" style="display: none">
							<span class="spAttrPadding_less" >&nbsp;</span>
							<span class="spAttrItem">
								<input id="chkIsContainsChildren" type="checkbox" name="IsContainsChildren" isboolean="1" <%=oChannel.isContainsChildren()?"checked":""%>> <label for="chkIsContainsChildren" WCMAnt:param="channel_add_edit.jsp.IsContainsChildren">显示子栏目文档</label>
							</span>
						</span>
						<span id="useDocLevel">
							<span class="spAttrPadding_less" >&nbsp;</span>
							<span class="spAttrItem">
								<input id="chkUseDocLevel" type="checkbox" name="UseDocLevel" isboolean="1" <%=oChannel.isUseDocLevel()?"checked":""%>> <label for="chkUseDocLevel" WCMAnt:param="channel_add_edit.jsp.docdense">文档使用密级管理</label>
							</span>
						</span>
						
					</div>
				</div>
				<div class="block_box" id="divBasicPublishAttrs">
					<div class="header_row_left">
						<div class="header_row_right">
							<div class="header_row_center">
								<div class="header_row_text" WCMAnt:param="channel_add_edit.jsp.pub">发布设置</div>
							</div>
						</div>
					</div>
					<div class="body_box">
						<div>
							<span class="spAttrClue spAttrClueTemplate">
								<span WCMAnt:param="channel_add_edit.jsp.basctemp" class="templatesp">首页模板</span>
								<a href="#" name="template" index="0" class="template_select" style="display:none;" title="选择模板" WCMAnt:paramattr="title:channel_add_edit.jsp.selecttemplatetips"></a>：
							</span>
							<input type="hidden" name="outlineTemplate" id="outlineTemplate" value="<%=nOutlineTemplateId%>"/>
							<span class="spAttrItem spAttrSpcItem" id="spOutlineTemp">							
								<%=sOutlineTemplateName%>
							</span>
						</div>
						<div>
							<span class="spAttrClue spAttrClueTemplate">
								<span WCMAnt:param="channel_add_edit.jsp.detailtemp" class="templatesp">细览模板</span>
								<a href="#" name="template" index="1" class="template_select" style="display:none;" title="选择模板" WCMAnt:paramattr="title:channel_add_edit.jsp.selecttemplatetips"></a>：
							</span>
							<input type="hidden" name="detailTemplate" id="detailTemplate" value="<%=nDetailTemplateId%>"/>
							<span class="spAttrItem spAttrSpcItem" id="spDetailTemp">							
								<%=sDetailTemplateName%>
							</span>
						</div>
						<div id="selectinfoviewprinttemp">
							<span class="spAttrClue spAttrClueTemplate" style="width:90px;">
								<span WCMAnt:param="channel_add_edit.jsp.infoviewprinttemp" class="templatesp">表单打印模板</span>
								<a href="#" name="template" index="3" class="template_select" style="display:none;" title="选择模板" WCMAnt:paramattr="title:channel_add_edit.jsp.selecttemplatetips"></a>：
							</span>
							<input type="hidden" name="InfoviewPrintTemplate" id="infoviewPrintTemplate" value="<%=nInfoviewPrintTemplateId%>"/>
							<span class="spAttrItem spAttrSpcItem" id="spInfoviewPrintTemp">							
								 <%=sInfoviewPrintTemplateName%>
							</span>
						</div>
						<div>
							<span class="spAttrClue spAttrClueDatapath" WCMAnt:param="channel_add_edit.jsp.datapath">
								存放位置：
							</span>
							<span class="spAttrItem">
								<input name="dataPath" id="dataPath" type="text"
									class="kuang_as" style="width:100px;" value="<%=CMyString.filterForHTMLValue(
									sDataPath)%>"/>
							</span>
							<div id='dataPathValidDesc' style="margin-left:6px;height:20px;"></div>
						</div>
						<div>
							<span class="spAttrPadding" >
								&nbsp;
							</span>
							<span class="spAttrItem">
								<input id="chkCanPub" type="checkbox" name="CanPub" isboolean="1" <%=oChannel.isCanPub()?"checked":""%>> <label for="chkCanPub" WCMAnt:param="channel_add_edit.jsp.canpub">&nbsp;允许发布该栏目</label>
							</span>
						</div>					
					</div>
				</div>
				<div class="block_box" id="divBasicWFAttrs">
					<div class="header_row_left">
						<div class="header_row_right">
							<div class="header_row_center">
								<div class="header_row_text" WCMAnt:param="channel_add_edit.jsp.process">工作流</div>
							</div>
						</div>
					</div>
					<div class="body_box">
						<div>
							<span class="spAttrClue selectFlowSp">
								<span WCMAnt:param="channel_add_edit.jsp.process">工作流</span>
								<a href="#" rightIndex="21-25,29" name="selWorkflow" class="workflow_select" WCMAnt:paramattr="title:channel_add_edit.jsp.lnkFlowview" title="选择工作流"></a>：
							</span>
							<span class="spAttrItem" style="margin-top: 0px;">
									<a id="lnkFlow" href="#" onclick="flowSelector.viewWorkflow(); return false;" WCMAnt:paramattr="title:channel_add_edit.jsp.lnkFlow" title="点击进行新建" WCMAnt:param="channel_add_edit.jsp.none">无</a><span id="spAltLnkFlow" style="display: none"></span>
							</span>					
						</div>
					</div>
				</div>
				<div class="block_box" id="divSpecialPublishAttr" style="margin-top: 10px;">
					<div class="header_row_left">
						<div class="header_row_right">
							<div class="header_row_center">
								<div class="header_row_text" WCMAnt:param="channel_add_edit.jsp.pub">发布设置</div>
							</div>
						</div>
					</div>
					<div class="body_box" style="padding-top:5px;">
						<div id="divSpecialCanPubAttr">
							<span class="spAttrPadding" >&nbsp;</span>
							<span class="spAttrItem">
								<input id="chkSpCanPub" type="checkbox" isboolean="1" <%=oChannel.isCanPub() ? "checked" : ""%> ><span WCMAnt:param="channel_add_edit.jsp.canpub">&nbsp;允许发布该栏目</span>
							</span>
						</div>
						<div>
							<span class="spAttrPadding" >&nbsp;</span>
							<span class="spAttrItem">
								<input type="checkbox" name="sp-OnlyManager" ignore="1" <%=oChannel.isOnlyManager()?"checked":""%>>&nbsp;<span WCMAnt:param="channel_add_edit.jsp.pubtomanager">发布为管理节点</span>
							</span>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-body" id="tab-advance">
				<div class="block_box" id="dvFilterAndOrder">
					<input type="hidden" name="bHiddenDocTitle" id="bHiddenDocTitle" value="<%=bHiddenDocTitle%>">
					<div class="header_row_left">
						<div class="header_row_right">
							<div class="header_row_center">
								<div class="header_row_text" WCMAnt:param="channel_add_edit.jsp.search">检索与排序</div>
							</div>
						</div>
					</div>
					<div class="body_box" style="padding-top:5px;">
						<div>
							<span class="spAttrClue queryordersp" WCMAnt:param="channel_add_edit.jsp.query">
								检索条件：
							</span>
							<input name="chnlQuery" id="chnlQuery" type="text" class="kuang_as" value="<%=CMyString.filterForHTMLValue(oChannel.getQuery())%>"/>
							<span onclick="openSearchCondition();return false;" title="点击选择检索条件" class="text_param" WCMAnt:paramattr="title:channel_add_edit.jsp.searchcondition" id="chnlQuerySp">&nbsp;&nbsp;&nbsp;</span>						
							<span id="queryByValidDesc" style="width:120px;"></span>
						</div>
						<div style="padding-left: 10px; margin-bottom: 5px;">
							<input id="chkOnlySearch" type="checkbox" name="onlySearch" isboolean="1" <%=oChannel.isOnlySearch()?"checked":""%>>&nbsp;<label for="chkOnlySearch" WCMAnt:param="channel_add_edit.jsp.usesearch">只使用检索条件检索</label>
						</div>
						<div id="DoDeptFilter" style="display:none;padding-left: 10px; margin-bottom: 5px;">						
							<input  type="checkbox" name="DoDeptFilter" isboolean="1" <%=oChannel.isDoDeptFilter()?"checked":""%> />&nbsp;<span WCMAnt:param="channel_add_edit.jsp.filter">按部门过滤文档</span>
						</div>
						<div>
							<span class="spAttrClue queryordersp" WCMAnt:param="channel_add_edit.jsp.paixu">
								排序方式：
							</span>
							<input id="chnlOrderBy" name="chnlOrderBy" type="text" class="kuang_as" value="<%=CMyString.filterForHTMLValue(oChannel.getOrderBy())%>"></input>
							<span onclick="popupEditor();return false;" title="点击选择排序方式" class="text_param" WCMAnt:paramattr="title:channel_add_edit.jsp.popupEditor" id="selectordersp">&nbsp;&nbsp;&nbsp;</span>						
							<span id="txtOrderByValidDesc" style="width:120px;"></span>
						</div>
					</div>
				</div>
				<div class="block_box">
					<div class="header_row_left">
						<div class="header_row_right">
							<div class="header_row_center">
								<div class="header_row_text" WCMAnt:param="channel_add_edit.jsp.otherpub">其他发布设置</div>
							</div>
						</div>
					</div>
					<div class="body_box">
						<div>						
							<span class="spAttrClue spAttrClueOtherTemplate">
								<span WCMAnt:param="channel_add_edit.jsp.otheroutline">其他概览模板</span>
								<a href="#" name="template" index="2" class="template_select" style="display:none;" WCMAnt:paramattr="title:channel_add_edit.jsp.selectothertemplatetips" title="选择模板"></a>：
							</span>
							<input type="hidden" name="otherTemplates" id="otherTemplates" value="<%=sOtherTemplateIds%>">
							<span class="spAttrItem spAttrSpcItem" id="spOtherTemps">
							<%
								String sTempNames = "";
								for (int i = 0, length = oOtherTemplates.size(); i < length; i++) {
									Template tempate = (Template) oOtherTemplates.getAt(i);
									if (tempate == null)
										continue;
									if(i!=length-1){
										sTempNames += (CMyString.transDisplay(tempate.getName()) + ",");
							%>
									<%=CMyString.transDisplay(tempate.getName())%>, 
							<%
									}else{
										sTempNames += CMyString.transDisplay(tempate.getName());
							%>
									<%=CMyString.transDisplay(tempate.getName())%>
							<%
									}
								}

								if(oOtherTemplates.size() <= 0) out.print(LocaleServer.getString("channel.label.none","无"));
							%>
							</span>
						</div>
						<div>
                            <span style="padding-left:10px;"></span>
							<span class="spAttrItem">
								<input id="pubtomanager" type="checkbox" name="ad-OnlyManager" ignore="1" <%=oChannel.isOnlyManager()?"checked":""%>>
								<label for="pubtomanager" WCMAnt:param="channel_add_edit.jsp.pubtomanager">发布为管理节点</label>
							</span>
						</div>
						<div>
							<span style="padding-left:10px;"><input type="hidden" name="PublishLimit" id="PublishLimit" isAttr="1"  value="<%=sPublishLimit%>">
							<input type="checkbox" name="PublishLimitCtrl" id="PublishLimitCtrl" value="1" onclick="onPublishLimit();" <%=(sPublishLimit=="1")?"checked":""%>/><label for="PublishLimitCtrl" WCMAnt:param="channel_add_edit.jsp.pubstartdate" >发布后仅显示指定时间开始撰写的文档</label></span><span id="showPubLimit"><input type="text" name="PUBSTARTDATE" id="PUBSTARTDATE" elname="文档发布开始时间" WCMAnt:paramattr="elname:channel_add_edit.jsp.pubstarttime" value='<%=sPubStartDate%>' isAttr="1" class="kuang_as"><button id="pubstart" class="calendarShow" type="button"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0 style="margin-top:-1px;margin-left:-1px"></button></span>
						</div>
						<div>
							<span class="spAttrClue queryordersp" WCMAnt:param="channel_add_edit.jsp.chaxun">
								页面类型：
							</span>
							<select name="listtype" id="listtype" isattr='1' style="width:300px;" _value="<%=CMyString.showNull(oChannel.getAttributeValue("listtype"))%>">
								<option value="0">顶部栏目导航</option>
								<option value="1">普通的文档列表页面</option>
								<option value="2">图片标题加摘要列表</option>
								<option value="3">图片缩略图列表</option>
								<option value="4">视频缩略图列表</option>
								<option value="5">普通栏目列表</option>
								<option value="6">小图标栏目列表</option>
								<option value="7">顶部大图+底部图片标题加摘要列表</option>
								<option value="8">文档热点排行列表</option>
								<option value="9">地图页面</option>
								<option value="10">标签切换单篇文档</option>
								<option value="11">官方微博</option>
								<option value="12">股票行情</option>
								<option value="13">顶部大图+普通文档列表</option>
								<option value="14">普通文档列表+只包含标题</option>
								<option value="15">图片文档列表+不包含时间</option>
								<option value="16">栏目缩略图自适应宽度列表</option>
								<option value="17">访谈列表</option>
								<option value="18">文档小图标列表</option>
							</select>	
							<script language="javascript">
							<!--
								if($('listtype').getAttribute('_value')){
									$('listtype').value = $('listtype').getAttribute('_value');
								}
							//-->
							</script>
						</div>
					</div>
				</div>
				<div class="block_box">
					<div class="header_row_left">
						<div class="header_row_right">
							<div class="header_row_center">
								<div class="header_row_text" WCMAnt:param="channel_add_edit.jsp.planpub">计划发布</div>
							</div>
						</div>
					</div>
					<div class="body_box">
						<input type="hidden" name="ScheduleMode" id="ScheduleMode" value="<%=nScheduleMode%>"></input>
						<div>
							<span class="spAttrShort">
								<input type="radio" name="hScheduleMode" id="rdSchdMode_0" ignore="true" value="0" onclick="switchSchdDisp(0)"/>
							</span>
							<span class="spAttrSpcItem">
								<label for="rdSchdMode_0" WCMAnt:param="channel_add_edit.jsp.notpub">不设置计划发布</label>
							</span>
						</div>
						<div style="margin-top: 0px;">
							<span class="spAttrShort">
								<input type="radio" name="hScheduleMode" id="rdSchdMode_1" ignore="true" value="1" onclick="switchSchdDisp(1)"/>
							</span>
							<span class="spAttrSpcItem">
								<label for="rdSchdMode_1" WCMAnt:param="channel_add_edit.jsp.onetime">每天运行一次</label>
							</span>
						</div>
						<div style="display:none; margin-top: 0px; padding-bottom: 5px;" id="trSchdRunOnce">
							<div style="padding-left:50px;">
								<span WCMAnt:param="channel_add_edit.jsp.runtime">运行时间:</span>
								<input name="execTime" type="hidden" id="execTime" value=""/>
								<input name="execTimeHour" class="kuang_as time" type="text" id="execTimeHour" value="<%=CMyString.filterForHTMLValue(sExecTimeHour)%>" ignore=1 maxlength=2/><span>:</span><input type="text" class="kuang_as time" name="execTimeMinute" id="execTimeMinute" value="<%=CMyString.filterForHTMLValue(sExecTimeMinute)%>" ignore=1 maxlength=2/>
								<span id='exec'></span>
							</div>
							<div style="padding-left:50px;">
								<input name="FORCESCHEDULEPUB" type="checkbox" id="rdForcepub_1" <%="1".equals(sAttrForceSchedulePub)?"checked":""%> isattr="1" isboolean="1" ignore="1"/>
								<label for="rdForcepub_1"><span WCMAnt:param="channel_add_edit.jsp.scheduleforcepub">强制发布</span></label> 						
							</div>
						</div>	
						<div style="margin-top: 0px;">
							<span class="spAttrShort">
								<input type="radio" name="hScheduleMode" ignore="true" id="rdSchdMode_2" value="2" onclick="switchSchdDisp(2)"/>
							</span>
							<span class="spAttrSpcItem">
								<label for="rdSchdMode_2" WCMAnt:param="channel_add_edit.jsp.manytimes">每天运行多次</label>
							</span>
						</div>
						<div style="display:none; margin-top: 0px; padding-bottom: 5px;" id="trSchdRunTimes">
							<div class="PubTimeDiv">
								<span WCMAnt:param="channel_add_edit.jsp.starttime">开始时间:</span>
								<input name="startTime" id="startTime" type="hidden" value=""/>
								<input name="startTimeHour" class="kuang_as time" type="text" id="startTimeHour" value="<%=CMyString.filterForHTMLValue(sStartTimeHour)%>" ignore=1 maxlength=2/><span>:</span><input type="text" class="kuang_as time" name="startTimeMinute" id="startTimeMinute" value="<%=CMyString.filterForHTMLValue(sStartTimeMinute)%>" ignore=1 maxlength=2/>
								<span id='start'></span>
							</div>
							<div class="PubTimeDiv">
								<span WCMAnt:param="channel_add_edit.jsp.endtime">结束时间:</span>
								<input name="endTime" id="endTime" type="hidden" value=""/>
								<input name="endTimeHour" class="kuang_as time" type="text" id="endTimeHour" value="<%=CMyString.filterForHTMLValue(sEndTimeHour)%>" ignore=1 maxlength=2/><span>:</span><input type="text" class="kuang_as time" name="endTimeMinute" id="endTimeMinute" value="<%=CMyString.filterForHTMLValue(sEndTimeMinute)%>" ignore=1 maxlength=2/>
								<span id='end'></span>
							</div>   
							<div class="PubTimeDiv">
								<span WCMAnt:param="channel_add_edit.jsp.interval">间隔时间:</span>
								<input name="interval" id="interval" type="hidden" value=""/>
								<input name="intervalHour" class="kuang_as time" type="text" id="intervalHour" value="<%=sIntervalHour%>" ignore=1 maxlength=2/><span>:</span><input type="text" class="kuang_as time" name="intervalMinute" id="intervalMinute" value="<%=sIntervalMinute%>" ignore=1 maxlength=2/>
								<span id='interv'></span>
							</div>
							<div class="PubTimeDiv">
								<input name="FORCESCHEDULEPUB" type="checkbox" id="rdForcepub_2" <%="1".equals(sAttrForceSchedulePub)?"checked":""%> isattr="1" isboolean="1" ignore="1"/><span>
								<label for="rdForcepub_2"><span WCMAnt:param="channel_add_edit.jsp.scheduleforcepub">强制发布</span></label>
							</div>
						</div>
						<div style="margin-left:10px;">
							<input type="checkbox" name="unpubjob" id="unpubjob" value="" <%=(unpubSchedule==null?"":"checked")%> onclick="onPubjobset();">
							<label for="unpubjob" WCMAnt:param="channel_add_edit.jsp.unpubschedule">定时撤销发布</label>
							<span id="unpubjobdatetime" style="display:<%=(unpubSchedule==null?"none":"inline")%>">							
								 <input type="hidden" name="UnpubSchId" id="UnpubSchId" value="<%=(unpubSchedule==null?0:unpubSchedule.getId())%>">							 
								 <input type="text" name="UNPUBTIME" id="UNPUBTIME" value='<%=CMyString.filterForHTMLValue((unpubSchedule==null? "":unpubSchedule.getExeTime().toString("yyyy-MM-dd HH:mm")))%>' class="kuang_as" elname="撤销发布时间" WCMAnt:paramattr="elname:channel_add_edit.jsp.unpubscheduledesc" />
								 <button id="calunpubtime" type="button" class="calendarShow"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0></button>
							</span>
						</div>
					</div>
				</div>
				<div class="block_box" id="divCustomPagesSetting">
					<div class="header_row_left">
						<div class="header_row_right">
							<div class="header_row_center">
								<div class="header_row_text" WCMAnt:param="channel_add_edit.jsp.self">自定义页面</div>
							</div>
						</div>
					</div>
					<div class="body_box">
						<div>
							<span class="spAttrLongClue" WCMAnt:param="channel_add_edit.jsp.doc">
								文档新建修改页面：
							</span>
							<span class="spAttrItem">
								<input name="contentAddEditPage" type="text" class="kuang_as" value="<%=CMyString.filterForHTMLValue(sContentAddEditPage)%>" size="45" id="contentAddEditPage"/>
							</span>
						</div>
						<div>
							<span class="spAttrLongClue" WCMAnt:param="channel_add_edit.jsp.doclist">
								文档列表页面：
							</span>
							<span class="spAttrItem">
								<input name="contentListPage" type="text" class="kuang_as" value="<%=CMyString.filterForHTMLValue(sContentListPage)%>" size="45" id="contentListPage"/>
							</span>
						</div>
						<div>
							<span class="spAttrLongClue" WCMAnt:param="channel_add_edit.jsp.docview">
								文档查看页面：
							</span>
							<span class="spAttrItem">
								<input name="contentShowPage" type="text" class="kuang_as" value="<%=CMyString.filterForHTMLValue(sContentShowPage)%>" size="45" id="contentShowPage"/>
							</span>
						</div>
						<div style="height:20px;">
							<span class="spAttrItem">
								<span id="validation_desc_advance" style="padding-left:60px;float:left;"></span>
								<span style="float:right;padding-right:5px;" id="divApplyDefault">
									<a href="#" onclick="applyPaths(); return false" WCMAnt:param="channel_add_edit.jsp.restore">还原为默认值</a>
								</span>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<%
	}catch(Throwable t){
		t.printStackTrace();
		throw t;
	}							
%>
<script>
	wcm.TRSCalendar.get({
		top: -180,
		input : 'UNPUBTIME',
		handler : 'calunpubtime'
	});
	wcm.TRSCalendar.get({
		input : 'PUBSTARTDATE',
		handler : 'pubstart',
		top : -160
	});
</script>

</body>
</html>