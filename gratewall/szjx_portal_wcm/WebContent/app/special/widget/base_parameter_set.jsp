<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>

<%@ page import="com.trs.components.common.publish.widget.WidgetParameter" %>
<%@ page import="com.trs.components.common.publish.widget.Widget" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetParameters" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetConstants" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetInstance" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetInstParameter" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetInstParameters" %>
<%@ page import="com.trs.components.common.publish.widget.IWidgetInstParameterMgr" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.common.publish.widget.ResourceStyle" %>
<%@ page import="com.trs.components.common.publish.widget.ResourceStyles" %>
<%@ page import="com.trs.components.common.publish.widget.ContentStyle" %>
<%@ page import="com.trs.components.common.publish.widget.ContentStyles" %>
<%@ page import="com.trs.components.common.publish.widget.fieldtype.FieldTypesCreatorFactory" %>
<%@ page import="com.trs.components.common.publish.widget.fieldtype.IFieldTypesCreator" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="java.io.File" %>
<%@ page import="java.lang.reflect.Method" %>
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
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,LocaleServer.getString("base_parameter_set.noeffect","没有传入有效的widgetInstanceId"));
	}
	int nTemplateId = oWidgetInstance.getTemplateId();
	int nWidgetId = oWidgetInstance.getWidgetId();
	processor.setAppendParameters(new String[]{
		"widgetId" ,""+nWidgetId
	});
	WidgetParameters oWidgetParameters = (WidgetParameters) processor.excute("wcm61_widgetparameter", "query");
	
	Widget currWidget = Widget.findById(nWidgetId);
	if(currWidget==null)throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("base_parameter_set.id.zero","没有找到Widget对象[Id={0}]"),new int[]{nWidgetId}));
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

	String sRelateJsPath = getPath();
	
%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="base_parameter_set.title">设置资源变量</title>
<%@ include file="widgetparameter_set_include_resource.jsp"%>
<%@include file="../../special/widget/fieldtypes_include.jsp"%>
<script src="../js/adapter4Top.js"></script>
<script src="../js/TypeField.js"></script>
<script language="javascript" src="base_parameter_set.js" type="text/javascript"></script>
<%
	//引入公共级别的js
	File widgetCommonDir = new File(sRelateJsPath + "common");
	if(widgetCommonDir.exists() && widgetCommonDir.isDirectory()){
        File[] widgeteCommonFiles = widgetCommonDir.listFiles();
        for (int i = 0; i < widgeteCommonFiles.length; i++) {
            if (widgeteCommonFiles[i].isFile()) {
                String sWidgetJSFileName = widgeteCommonFiles[i].getName();
				if(!sWidgetJSFileName.endsWith(".js")){
					continue;
				}
%>
<script src="ext-js/common/<%=sWidgetJSFileName%>"></script>
<%
            }
        }
	}
%>
<%
	//页面资源级别的js
	//判断是否需要引入特殊处理的JS
	String sIdentityName = currWidget.getIdentityFlag(currWidget.getWname());
	File widgetJSFile = new File(sRelateJsPath + sIdentityName + ".js");

	if (widgetJSFile.exists()) {
%>
<script src="ext-js/<%=sIdentityName%>.js"></script>
<%
	}
%>
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="widgetparameter_set.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	*{
		margin:0px;
		padding:0px;
	}
	.row{
		margin-top:8px;
	}
	.row .label{
		width:120px;
	}

	.row .value{
		margin-left:135px;
	}
</style>

</head>

<body>
<div class="box_content" id="data" action="">
	<input type="hidden" name="widgetInstanceId" id="widgetInstanceId" value="<%=nWidgetInstanceId%>" />
	<input type="hidden" name="templateId" id="templateId" value="<%=nTemplateId%>" />
	<input type="hidden" name="widgetId" id="widgetId" value="<%=nWidgetId%>" />
	<input type="hidden" name="bAdd" id="bAdd" value="<%=bAdd%>" />
		<div class="base_block_content" style="padding-top:0px;">
		<div class="row" style=''>
			<div class="label">资源名称：</div>
			<div class="value">
				<%=CMyString.transDisplay(oWidgetInstance.getWidget().getWname())%>
			</div>
		</div>
		<%
			for (int i = 0; i < oWidgetParameters.size(); i++){
				WidgetParameter currWidgetParameter = (WidgetParameter)oWidgetParameters.getAt(i);
				String sWidgetParamName = currWidgetParameter.getWidgetParamName();
				String sWidgetParamDesc = currWidgetParameter.getWidgetParamDesc();	
				int nParamType = currWidgetParameter.getWidgetParamType();
				if(nParamType==WidgetConstants.FIELD_TYPE_HTML||nParamType==WidgetConstants.FIELD_TYPE_HTML_CHAR){
					bHasEditor = true;
				}
				//几个固有变量在别的tabpanel列出来
				if(WidgetConstants.isFixedParameter(sWidgetParamName))continue;
		%>
		<div class="row" style=''>
			<div class="label"><%=CMyString.transDisplay(sWidgetParamDesc)%>：</div>
			<div class="value">
				<%=parse(oWidgetInstance,currWidgetParameter,bAdd)%>
			</div>
		</div>
		<script language="javascript">
		<!--
			$TypeField.addFieldKey("<%=CMyString.filterForJs(sWidgetParamName)%>","<%=nParamType%>");
		//-->
		</script>
		<%
			}
		%>
		</div>
</div>
<input type="hidden" name="bHasEditor" id="bHasEditor" value="<%=bHasEditor%>" />
<%
//added by hxj：
//提供资源改变类型的功能，从已有的资源实例里面获取所有变量，并为新的资源实例变量赋值。
//为了更好的使用这个功能，需要确保新老资源的变量命名统一。
int nOldWidgetInstanceId = processor.getParam("oldWidgetInstanceId", 0);
String sWidgetInstParametersAsArray = getWidgetInstParametersAsArray(nOldWidgetInstanceId);

%>
<script language="javascript">
<!--
	var $OldWidgetInstParameters = <%=sWidgetInstParametersAsArray%>;
//-->
</script>
</body>
</html>
<%!

	private String getChannelNames(String sChnlIds)throws WCMException{
		if(CMyString.isEmpty(sChnlIds)){
			return "";
		}
		Channels oChannels = Channels.findByIds(null, sChnlIds);
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

		return sbResult.toString();
	}

	private String getWidgetInstParametersAsArray(int nWidgetInstanceId)throws WCMException{
		if(nWidgetInstanceId <= 0){
			return "[]";
		}

		WidgetInstance instance = WidgetInstance.findById(nWidgetInstanceId);
		if(instance == null){
			return "[]";
		}

		StringBuffer sbParameters = new StringBuffer();
		sbParameters.append("[");

		IWidgetInstParameterMgr m_oWidgetInstParameterMgr = (IWidgetInstParameterMgr) DreamFactory
			.createObjectById("IWidgetInstParameterMgr");
		WidgetInstParameters parameters = m_oWidgetInstParameterMgr
					.getWidgetInstParameters(instance, null);

		for (int i = 0, nSize = parameters.size(); i < nSize; i++) {
			WidgetInstParameter parameter = (WidgetInstParameter) parameters.getAt(i);
			if (parameter == null)
				continue;

			sbParameters.append("{");
			sbParameters.append("name:'").append(CMyString.filterForJs(parameter.getParamName())).append("',");

			//所属栏目需要做特殊处理
			if("所属栏目".equals(parameter.getParamName())){
				String sChnlIds = parameter.getParamValue();
				String sChnlNames = getChannelNames(sChnlIds);
				sbParameters.append("value:{");
				sbParameters.append("ids:'" + CMyString.filterForJs(sChnlIds)).append("',");
				sbParameters.append("names:'" + CMyString.filterForJs(sChnlNames)).append("'");
				sbParameters.append("}\n");
			}else{
				sbParameters.append("value:'").append(CMyString.filterForJs(parameter.getParamValue())).append("'");
			}
			sbParameters.append("},\n");
		}
		sbParameters.append("]");
		return sbParameters.toString();
	}

	private String parse(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		int nWidgetParamType = _currWidgetParameter.getWidgetParamType();
		String sHTMLContent = null;
		IFieldTypesCreator instance = FieldTypesCreatorFactory.getTypeCreator(nWidgetParamType);
		String sMethodName = instance.getTypeMethodName();
		try{
			Class Clazz = this.getClass();
			Method oMethod = Clazz.getDeclaredMethod(sMethodName,new Class[]{WidgetInstance.class,WidgetParameter.class,boolean.class});
			sHTMLContent = oMethod.invoke(this, new Object[]{oWidgetInstance,_currWidgetParameter,Boolean.valueOf(_bAdd)}).toString();
		}catch(Exception e){
			throw new WCMException("执行方法出错。",e);
		}
		
		return sHTMLContent;
	}

	private String getParameterValue(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter)throws WCMException{
		if(oWidgetInstance==null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,LocaleServer.getString("base_parameter_set.empty","资源实例对象为空"));
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

	private String mapFile(String _sFileName){
		if(CMyString.isEmpty(_sFileName) || _sFileName.equals("0") || _sFileName.equalsIgnoreCase("none.gif")){
			return "../images/list/none.gif";
		}else{
			return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
		}
	}

	private String makeHtmlCon(String _sHtml) throws WCMException{
		if(CMyString.isEmpty(_sHtml)) return _sHtml;
		com.trs.cms.content.HTMLContent htmlCon = new com.trs.cms.content.HTMLContent(_sHtml);
		return htmlCon.parseHTMLContent(null);
	}

    // 获取资源块附加JS的存储路径
	private String getPath() throws WCMException {
		String sPath = ConfigServer.getServer().getInitProperty("WCM_PATH");
		sPath = CMyString.setStrEndWith(sPath, File.separatorChar);
		String m_sWcmApp = "app";
		/** 专题的路径 */
		String m_sSpecialPath = "special";
		/** 资源块的路径 */
		String m_sStylePath = "widget";
		/** 资源块内嵌JS的路径 */
		String m_sCommonPath = "ext-js";
		sPath += m_sWcmApp + File.separator + m_sSpecialPath + File.separator
				+ m_sStylePath + File.separator + m_sCommonPath
				+ File.separator;
		return sPath;
	}
%>