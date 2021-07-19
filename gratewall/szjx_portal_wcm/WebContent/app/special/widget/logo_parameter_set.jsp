<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error_for_dialog.jsp"%>

<%@ page import="com.trs.components.common.publish.widget.WidgetParameter" %>
<%@ page import="com.trs.components.common.publish.widget.Widget" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetParameters" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetConstants" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetInstance" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetInstParameter" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetInstParameters" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.common.publish.widget.ResourceStyle" %>
<%@ page import="com.trs.components.common.publish.widget.ResourceStyles" %>
<%@ page import="com.trs.components.common.publish.widget.ContentStyle" %>
<%@ page import="com.trs.components.common.publish.widget.ContentStyles" %>
<%@ page import="com.trs.wcm.photo.IImageLibConfig" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>

<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@include file="../../include/public_server.jsp"%>
<%@ page buffer="64kb" %>
<%
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	int nWidgetInstanceId = currRequestHelper.getInt("widgetInstanceId",0);
	//区分新增和修改
	boolean bAdd = currRequestHelper.getBoolean("bAdd",true);
	WidgetInstance oWidgetInstance = WidgetInstance.findById(nWidgetInstanceId);
	if(oWidgetInstance==null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,LocaleServer.getString("logo_parameter_set.noteffect","没有传入有效的widgetInstanceId"));
	}
	int nTemplateId = oWidgetInstance.getTemplateId();
	int nWidgetId = oWidgetInstance.getWidgetId();
	processor.setAppendParameters(new String[]{
		"widgetId" ,""+nWidgetId
	});
	WidgetParameters oWidgetParameters = (WidgetParameters) processor.excute("wcm61_widgetparameter", "query");
	
	Widget currWidget = Widget.findById(nWidgetId);
	if(currWidget==null)throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("logo_parameter_set.id.zero","没有找到Widget对象[Id={0}]"),new int[]{nWidgetId}));
	String sWidgetAttrURL = currWidget.getWidgetAttrURL();
	if(CMyString.isEmpty(sWidgetAttrURL)){
		sWidgetAttrURL = "";
	}
	String sWidgetPic = CMyString.filterForHTMLValue(currWidget.getWidgetpic());
	String sPicFileName = mapFile(sWidgetPic);
	if(CMyString.isEmpty(sWidgetPic)){
		sPicFileName = "../images/list/none.gif";
	}
	boolean bHasEditor = false;
	boolean m_bIsPhotoLibPluginEnabled = com.trs.presentation.plugin.PluginConfig.isStartPhoto();
%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>设置资源变量</title>
<%@ include file="widgetparameter_set_include_resource.jsp"%>
<script src="../../js/easyversion/lightbase.js"></script>
<script src="../../js/easyversion/extrender.js"></script>
<script src="../../js/easyversion/elementmore.js"></script>
<script src="../js/adapter4Top.js"></script>
<script src="../swfobject.js"></script>
<script language="javascript">
<!--
	var bEnablePicLib = <%=m_bIsPhotoLibPluginEnabled%>;
//-->
</script>
<script language="javascript" src="logo_parameter_set.js" type="text/javascript"></script>
<link href="widgetparameter_set.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	#pic_element{
		display:block;
		margin:0 auto;
	}
	#LOGO_TITLE{
		width:150px;
	}
	.pic_show{
		text-align:center;
		overflow:auto;
		height:120px;
	}
	.left{
		float:left;
		width:80%;
		padding:5px;
		border-right:1px solid gray;
		height:150px;
	}
	.right{
		float:left;
		padding-left:10px;
	}	
	.XAppendix{
		display:block;
	}
	.XAppendix .appendix-text{
		display:none;
	}
	.XAppendix .appendix-browser{
		background:url('../images/widget/upload.gif');
		width:75px;
		height:25px;
		margin:5px 0;
	}
	.XAppendix .appendix-delete{
		background:url('../images/widget/delete.gif');
		width:75px;
		height:25px;
		margin:5px 0;
	}
	.btn-pic-edit{
		background:url('../images/widget/edit.gif');
		width:75px;
		height:25px;
		margin:5px 0;
		border:0px;
	}
	.btn-pic-edit.disabled{
		background:url('../images/widget/edit_gray.gif');
		width:75px;
		height:25px;
		margin:5px 0;
		border:0px;
	}
	.pic_info{
		padding-left:10px;
		display:inline;
		color:blue;
	}
	.pic_reminder{
		padding-left:10px;
		color:blue;
		display:inline;
	}
	.hide{
		display:none !important;
	}
</style>
</head>

<body>
<div class="box_content" id="data" action="">
	<input type="hidden" name="widgetInstanceId" id="widgetInstanceId" value="<%=nWidgetInstanceId%>" />
	<input type="hidden" name="templateId" id="templateId" value="<%=nTemplateId%>" />
	<input type="hidden" name="widgetId" id="widgetId" value="<%=nWidgetId%>" />
	<input type="hidden" name="bAdd" id="bAdd" value="<%=bAdd%>" />
		<div class="base_block_content">
		<%
			for (int i = 0; i < oWidgetParameters.size(); i++){
				WidgetParameter currWidgetParameter = (WidgetParameter)oWidgetParameters.getAt(i);
				String sWidgetParamName = CMyString.transDisplay(currWidgetParameter.getWidgetParamName());
				String sWidgetParamDesc = CMyString.transDisplay(currWidgetParameter.getWidgetParamDesc());	if(currWidgetParameter.getWidgetParamType()==WidgetConstants.FIELD_TYPE_HTML||currWidgetParameter.getWidgetParamType()==WidgetConstants.FIELD_TYPE_HTML_CHAR){bHasEditor = true;}
				//几个固有变量在别的tabpanel列出来
				if(WidgetConstants.isFixedParameter(sWidgetParamName))continue;
				String sValue = getParameterValue(oWidgetInstance,currWidgetParameter);
		%>
		<div class="row" style='' id="<%=sWidgetParamName%>_row">
		<%
			if(currWidgetParameter.getWidgetParamType()==WidgetConstants.FIELD_TYPE_APPENDIX){
				String sHttpPath = "";
				if(!CMyString.isEmpty(sValue)){
					sHttpPath = CMyString.setStrEndWith(FilesMan.getFilesMan().mapFilePath(sValue,FilesMan.PATH_HTTP),'/');;
					sHttpPath +=sValue;
				}
		%>
			<div class="left">
			<div class="pic_reminder" WCMAnt:param="logo_parameter_set.tips">
				提示：合适宽度为 <span id="suitabled_size"></span>
			</div>
			<div class="pic_info">
				<span id="pic_size"></span>
			</div>
			<hr/>
			<div class="pic_show">
				<div class="" id="flashcontent_box">
					<div class="flashcontent" id="flashcontent">
					</div>
				</div>
				<img id="pic_element" src="<%=CMyString.isEmpty(sValue)?"../images/wt.gif":"../../../file/read_image.jsp?FileName="+sValue%>" filename="<%=sValue%>" httpPath="<%=sHttpPath%>" />
			</div>
			</div>
		<%
		}
		%>
		<div class="label <%=currWidgetParameter.getWidgetParamType()==WidgetConstants.FIELD_TYPE_APPENDIX?"hide":""%>"><%=CMyString.transDisplay(sWidgetParamDesc)%>：</div>
		<div class="right">
				<%=parse(oWidgetInstance,currWidgetParameter,bAdd)%>
		</div>
		</div>
		<%
	}
		%>
		</div>
</div>
</body>
</html>
<%!
	private boolean isFixedParameter(String sParameterName){
		sParameterName = sParameterName.toUpperCase();
		for (int i = 0; i < WidgetConstants.FIEXED_PARAMETERS.length; i++) {
			if(WidgetConstants.FIEXED_PARAMETERS[i][0].equals(sParameterName)){
				return true;
			}
		}
		return false;
	}

	private String parse(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		int nWidgetParamType = _currWidgetParameter.getWidgetParamType();
		String sHTMLContent = null;
		switch(nWidgetParamType){
			case WidgetConstants.FIELD_TYPE_APPENDIX:
				sHTMLContent = dealWithAppendix(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
			case WidgetConstants.FIELD_TYPE_INT:
				sHTMLContent = dealWithInt(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
			case WidgetConstants.FIELD_TYPE_CLASS:
				sHTMLContent = dealWithClassInfo(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
			case WidgetConstants.FIELD_TYPE_HTML:
			case WidgetConstants.FIELD_TYPE_HTML_CHAR:
				sHTMLContent = dealWithEditor(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
			case WidgetConstants.FIELD_TYPE_INPUT_SELECT:
				sHTMLContent = dealWithInputSelect(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
			case WidgetConstants.FIELD_TYPE_SUGGESTION:
				sHTMLContent = dealWithSuggestion(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
			case WidgetConstants.FIELD_TYPE_RADIO:
				sHTMLContent = dealWithRadio(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
			case WidgetConstants.FIELD_TYPE_SELECT:
				sHTMLContent = dealWithSelect(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
			case WidgetConstants.FIELD_TYPE_CHECKBOX:
				sHTMLContent = dealWithCheckBox(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
			case WidgetConstants.FIELD_TYPE_NORMALTEXT:
				sHTMLContent = dealWithNormalText(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
			case WidgetConstants.FIELD_TYPE_TRUEORFALSE:
				sHTMLContent = dealWithTrueOrFalse(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
			case WidgetConstants.FIELD_TYPE_SELCHANNEL:
				sHTMLContent = dealWithSelChannel(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
		}
		return sHTMLContent;
	}
	//处理可输入下拉列表-Suggestion
	private String dealWithSuggestion(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XSuggestion({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(getParameterValue(oWidgetInstance,_currWidgetParameter));
		sb.append("', items : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getEnmvalue()));
		sb.append("', validation:\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',max_len:'80',desc:'"+LocaleServer.getString("logo_parameter_set.inputtips","可输入提示")+"'\"");
		sb.append("}).render();");
		sb.append("</script>");
		return sb.toString();
	}

	//处理可输入下拉列表-inputselect
	private String dealWithInputSelect(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XCombox({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(getParameterValue(oWidgetInstance,_currWidgetParameter));
		sb.append("', items : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getEnmvalue()));
		sb.append("', validation:\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',max_len:'80',desc:'"+LocaleServer.getString("logo_parameter_set.inputselect","可输入下拉")+"'\"");
		sb.append("}).render();");
		sb.append("</script>");
		return sb.toString();
	}


	//处理可视化编辑器
	private String dealWithEditor(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XEditor({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(getParameterValue(oWidgetInstance,_currWidgetParameter));
		sb.append("', validation:\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',no_desc:''\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}

	//处理分类法
	private String dealWithClassInfo(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		String sDesc = "";
		String sDefaultValue = _currWidgetParameter.getDefaultValue();
		String sParameterValue = getParameterValue(oWidgetInstance,_currWidgetParameter);
		if(_bAdd){
			sParameterValue = sDefaultValue;
		}
		ClassInfos classInfos = ClassInfos.findByIds(null,
				sParameterValue);
		StringBuffer sbResult = new StringBuffer(classInfos.size() * 20);
		for (int i = 0, nSize = classInfos.size(); i < nSize; i++) {
			ClassInfo classInfo = (ClassInfo) classInfos.getAt(i);
			if (classInfo == null)
				continue;
			sbResult.append(classInfo.getName());
			sbResult.append(",");
		}
		if (sbResult.length() > 0) {
			sbResult.setLength(sbResult.length() - 1);
		}
		sDesc = sbResult.toString();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XClassInfo({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(CMyString.filterForJs(sParameterValue));
		sb.append("', desc : '");
		sb.append(CMyString.filterForJs(sDesc));
		sb.append("', rootId : '");
		sb.append(_currWidgetParameter.getClassId());
		sb.append("', treeType : '");
		sb.append(_currWidgetParameter.getRadorchk());
		sb.append("', validation:\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',max_len:'80',no_desc:''\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}
	//处理附件
	private String dealWithAppendix(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XAppendix({");
		sb.append("disabled : 0, ");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(getParameterValue(oWidgetInstance,_currWidgetParameter));
		sb.append("', pathFlag: '");
		sb.append(FilesMan.FLAG_WEBFILE);
		sb.append("', validation : \"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',desc:'"+LocaleServer.getString("logo_parameter_set.appendixmanage","附件管理")+"',allowExt:'jpg,gif,png,bmp,swf'\"");
		sb.append("}).render();");
		sb.append("</script>");
		sb.append("<button id='btn-pic-edit' class='btn-pic-edit' onclick=\"pic_edit('");
		sb.append(CMyString.filterForHTMLValue(_currWidgetParameter.getWidgetParamName()));
		sb.append("')\"></button>");
		return sb.toString();
	}

	//处理是否
	private String dealWithTrueOrFalse(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XRadio({");
		sb.append("disabled : 0,");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', items : '");
		sb.append(LocaleServer.getString("logo_parameter_set.yes.no","是`1~否`0"));
		sb.append("', value : '");
		sb.append(getParameterValue(oWidgetInstance,_currWidgetParameter));
		sb.append("', validation:\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',max_len:'20',desc:'");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("'\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}

	//处理多选checkbox
	private String dealWithCheckBox(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XCheckbox({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', items : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getEnmvalue()));
		sb.append("', value : '");
		sb.append(getParameterValue(oWidgetInstance,_currWidgetParameter));
		sb.append("', validation:\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',max_len:'80',no_desc:''\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}

	//处理单选radio
	private String dealWithRadio(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XRadio({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', items : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getEnmvalue()));
		sb.append("', value : '");
		sb.append(getParameterValue(oWidgetInstance,_currWidgetParameter));
		sb.append("', validation:\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',max_len:'80',no_desc:''\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}

	//处理下拉文本
	private String dealWithSelect(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XSelect({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', items : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getEnmvalue()));
		sb.append("', blank : {label : \"--"+LocaleServer.getString("logo_parameter_set.choice","请选择")+"--\", value : -1},");
		sb.append(" value : '");
		sb.append(getParameterValue(oWidgetInstance,_currWidgetParameter));
		sb.append("', validation:\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',max_len:'80',no_desc:''\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}

	//处理普通文本
	private String dealWithNormalText(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XText({");
		sb.append("disabled : ");
		sb.append(_currWidgetParameter.getNotEdit());
		sb.append(",name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(getParameterValue(oWidgetInstance,_currWidgetParameter));
		sb.append("', validation:\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',max_len:'80',no_desc:''\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}
	//栏目选择树
	private String dealWithSelChannel(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		//return "<input type='text' name='"+CMyString.filterForHTMLValue(_currWidgetParameter.getWidgetParamName())+"'/>";
		String sDesc = "";
		String sDefaultValue = _currWidgetParameter.getDefaultValue();
		String sParameterValue = getParameterValue(oWidgetInstance,_currWidgetParameter);
		if(_bAdd){
			sParameterValue = sDefaultValue;
		}
		Channels oChannels = Channels.findByIds(null,
				sParameterValue);
		StringBuffer sbResult = new StringBuffer(oChannels.size() * 20);
		for (int i = 0, nSize = oChannels.size(); i < nSize; i++) {
			Channel currChannel = (Channel) oChannels.getAt(i);
			if (currChannel == null)
				continue;
			sbResult.append(currChannel.getName());
			sbResult.append(",");
		}
		if (sbResult.length() > 0) {
			sbResult.setLength(sbResult.length() - 1);
		}
		sDesc = sbResult.toString();

		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XChannelTree({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(CMyString.filterForJs(sParameterValue));
		sb.append("', desc : '");
		sb.append(CMyString.filterForJs(sDesc));
		sb.append("', treeType : '");
		sb.append(_currWidgetParameter.getRadorchk());
		sb.append("', validation:\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',max_len:'80',no_desc:''\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}

	private String dealWithInt(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XInteger({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(getParameterValue(oWidgetInstance,_currWidgetParameter));
		sb.append("', validation:\"type:'int',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',no_desc:''\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}

	private String getParameterValue(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter)throws WCMException{
		if(oWidgetInstance==null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,LocaleServer.getString("logo_parameter_set.empty","资源实例对象为空"));
		}
		String sWidgetParamName = _currWidgetParameter.getWidgetParamName();
		String sDefaultValue = CMyString.showNull(_currWidgetParameter.getDefaultValue());
		int nWidgetInstanceId = oWidgetInstance.getId();
		String sParameterValue = "";
		String sWhere = "WIDGETINSTID=? and PARAMNAME=?";
        WCMFilter oFilter = new WCMFilter(WidgetInstParameter.DB_TABLE_NAME,
                sWhere, "");
        oFilter.addSearchValues(nWidgetInstanceId);
		oFilter.addSearchValues(sWidgetParamName);
		WidgetInstParameters oWidgetInstParameters = WidgetInstParameters.openWCMObjs(
                ContextHelper.getLoginUser(), oFilter);
		if(oWidgetInstParameters.size()<=0)return sDefaultValue;
		WidgetInstParameter currWidgetInstParameter = (WidgetInstParameter)oWidgetInstParameters.getAt(0);
		if(currWidgetInstParameter!=null){
			sParameterValue = currWidgetInstParameter.getParamValue();
		}
		return CMyString.showNull(sParameterValue);
	}

	private String mapFile(String _sFileName){
		if(CMyString.isEmpty(_sFileName) || _sFileName.equals("0") || _sFileName.equalsIgnoreCase("none.gif")){
			return "../images/list/none.gif";
		}else{
			return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
		}
	}
%>