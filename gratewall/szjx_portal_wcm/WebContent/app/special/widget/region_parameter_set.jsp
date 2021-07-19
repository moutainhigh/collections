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
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
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
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,
                    "没有传入有效的widgetInstanceId");
	}
	int nWidgetId = oWidgetInstance.getWidgetId();
	processor.setAppendParameters(new String[]{
		"widgetId" ,""+nWidgetId
	});
	WidgetParameters oWidgetParameters = (WidgetParameters) processor.excute("wcm61_widgetparameter", "query");
	
	Widget currWidget = Widget.findById(nWidgetId);
	if(currWidget==null)throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,
					"没有找到Widget对象[Id="+nWidgetId+"]");

	out.clear();
%>
<?xml version="1.0" encoding="UTF-8"?>
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
<script language="javascript" src="region_parameter_set.js" type="text/javascript"></script>
<link href="widgetparameter_set.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.regionNameDv .region-select{
	width:16px;
	height:20px;
	cursor:pointer;
	background:url(../images/region_select.gif) center center no-repeat;
}
.regionName-text{
	 
}
.base_block_content{
	padding-top:10px;
}
.label{
	width:120px;
}
.value{
	margin-left:130px;
}
</style>
</head>
<body>
<div class="box_content" id="data" action="">
		<div class="base_block_content">
		<%
			for (int i = 0; i < oWidgetParameters.size(); i++){
				WidgetParameter currWidgetParameter = (WidgetParameter)oWidgetParameters.getAt(i);
				String sWidgetParamName = CMyString.transDisplay(currWidgetParameter.getWidgetParamName());
				//几个固有变量在别的tabpanel列出来
				if(isFixedParameter(sWidgetParamName))continue;
		%>
				<div class="row">
						<div class="label"><%=CMyString.transDisplay(currWidgetParameter.getWidgetParamDesc())%>：</div>
						<div class="value">
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
	//选择导读
	private String dealWithSelRegion(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		String sDesc = "";
		String sDefaultValue = _currWidgetParameter.getDefaultValue();
		
		String sParameterValue = getParameterValue(oWidgetInstance, _currWidgetParameter);
		if(_bAdd){
			sParameterValue = sDefaultValue;
		}
		String sParamName = CMyString.transDisplay(_currWidgetParameter.getWidgetParamDesc());
		StringBuffer sb = new StringBuffer();
		sb.append("<DIV class='regionNameDv'><INPUT id='" + sParamName + "'");
		sb.append("' type=hidden value='" + sParameterValue + "' name='" + sParamName + "' bName='true'" + "'>");
		sb.append("<DIV class=region-select id='regionNameSelectBtn' onclick='selectRegion()'>" + "</DIV>");
		sb.append("<DIV class=regionName-text id='regionNameText'>" + sParameterValue + "</DIV></DIV>");
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
	private String getParameterValue(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter)throws WCMException{
		if(oWidgetInstance==null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,
                    "资源实例对象为空");
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
		return sParameterValue;
	}

	private String parse(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		int nWidgetParamType = _currWidgetParameter.getWidgetParamType();
		String sParameterName = _currWidgetParameter.getWidgetParamName();
		if(sParameterName.indexOf("名称") > 0)
			return dealWithSelRegion(oWidgetInstance,_currWidgetParameter,_bAdd);
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
		sb.append("',max_len:'80',desc:'可输入提示'\"");
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
		sb.append("',max_len:'80',desc:'可输入下拉'\"");
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
		sb.append("', validation : \"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',desc:'附件管理',allowExt:'jpg,gif,png,bmp'\"");
		sb.append(" }).render();");
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
		sb.append("是`1~否`0");
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
		sb.append("', blank : {label : \"--请选择--\", value : -1},");
		sb.append(" value : '");
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
		sb.append("', validation:\"type:'int',value_range:'0,2000',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',no_desc:''\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}

	private String mapFile(String _sFileName){
		if(CMyString.isEmpty(_sFileName) || _sFileName.equals("0") || _sFileName.equalsIgnoreCase("none.gif")){
			return "../images/list/none.gif";
		}else{
			return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
		}
	}
%>