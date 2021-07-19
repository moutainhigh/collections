<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../../error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.conjection.ComponentEntryConfigConstants" %>
<%@ page import="com.trs.components.common.conjection.persistent.EntryConfig" %>
<%@ page import="com.trs.components.common.conjection.service.IComponentEntryConfigService" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.URLConnection" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetParameter" %>
<%@ page import="com.trs.components.common.publish.widget.Widget" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetParameters" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetConstants" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetInstance" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetInstParameter" %>
<%@ page import="com.trs.components.common.publish.widget.WidgetInstParameters" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page buffer="64kb" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../include/public_server.jsp"%>
<%
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	int nWidgetInstanceId = currRequestHelper.getInt("widgetInstanceId",0);
	//区分新增和修改
	boolean bAdd = currRequestHelper.getBoolean("bAdd",true);
	WidgetInstance oWidgetInstance = WidgetInstance.findById(nWidgetInstanceId);
	if(oWidgetInstance==null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,LocaleServer.getString("poll_list.noeffect","没有传入有效的widgetInstanceId"));
	}
	int nTemplateId = oWidgetInstance.getTemplateId();
	int nWidgetId = oWidgetInstance.getWidgetId();
	processor.setAppendParameters(new String[]{
		"widgetId" ,""+nWidgetId
	});
	WidgetParameters oWidgetParameters = (WidgetParameters) processor.excute("wcm61_widgetparameter", "query");
	
	Widget currWidget = Widget.findById(nWidgetId);
	if(currWidget==null)throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("poll_list.id.zero","没有找到Widget对象[Id={0}]"),new int[]{nWidgetId}));
	String sWidgetAttrURL = currWidget.getWidgetAttrURL();
	if(CMyString.isEmpty(sWidgetAttrURL)){
		sWidgetAttrURL = "";
	}
	
	String sWidgetPic = CMyString.filterForHTMLValue(currWidget.getWidgetpic());
	String sPicFileName = mapFile(sWidgetPic);
	if(CMyString.isEmpty(sWidgetPic)){
		sPicFileName = "../../images/list/none.gif";
	}

	String sSelectedId = "";
	for (int i = 0; i < oWidgetParameters.size(); i++){
		WidgetParameter currWidgetParameter = (WidgetParameter)oWidgetParameters.getAt(i);
		String sWidgetParamName = CMyString.transDisplay(currWidgetParameter.getWidgetParamName());
		if(sWidgetParamName.equalsIgnoreCase(LocaleServer.getString("poll_list.id.invest","调查ID"))){
			sSelectedId = CMyString.filterForJs(getParameterValue(oWidgetInstance,currWidgetParameter));
		}
	}
%>
<%

	IComponentEntryConfigService configSrv = (IComponentEntryConfigService) DreamFactory
			.createObjectById("IComponentEntryConfigService");

	EntryConfig currConfig = configSrv.getTypedEntryConfig(ComponentEntryConfigConstants.TYPE_POLL);

	String sUserLoginEncodInfo = currLoginHelper.createPluginEncodeUserInfo();

	//调查配置路径检测
	String targetUrl = currConfig.getLinkPath();
	boolean configEnable = false;
	if(!CMyString.isEmpty(targetUrl)){	
		HttpURLConnection connection = null;
		try{
			URL target = new URL(targetUrl+"/certification.txt");
			connection = (HttpURLConnection) target.openConnection();
		
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);

			byte[] buff = new byte[64];
			int nLen;
			StringBuffer reponseBuff = new StringBuffer(64);
			while((nLen=connection.getInputStream().read(buff,0,buff.length))!=-1){
				reponseBuff.append(new String(buff,0,nLen));
			}		

			if("youseeit".equals(reponseBuff.toString())){
				configEnable = true;			
			}
		}catch(Exception e){
		}finally{
			if(connection != null){
				connection.disconnect();
			}
		}
	}
	if(!configEnable){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, LocaleServer.getString("poll_list.error.notrun","调查配置有误或未启动！"));
	}
	String sURL = CMyString.setStrEndWith(currConfig.getLinkPath(), '/');

	//获取当前WCM的应用地址
	HttpServletRequest req = currRequestHelper.getRequest();
	String sHost = "127.0.0.1";
	int nPort = 8080;
	if (req != null) {
		sHost = req.getServerName();
		nPort = req.getServerPort();
	}
	String wcmPath = "http://" + sHost + ":" + nPort
			+ "/wcm/app/special/widget/poll_temp.html";

	sURL += "poll/poll_list_forspecial.jsp?" + sUserLoginEncodInfo + "&selectedId=" + sSelectedId +"&href=" + wcmPath;
	//currRequestHelper.getResponse().sendRedirect(sURL);
	out.clear();

%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="poll_list.title">问卷调查列表页面</title>
<%@ include file="../widgetparameter_set_include_resource.jsp"%>
<script src="../../../js/easyversion/lightbase.js"></script>
<script src="../../../js/easyversion/extrender.js"></script>
<script src="../../../js/easyversion/elementmore.js"></script>
<script src="../../js/adapter4Top.js"></script>
<link href="../../css/common.css" rel="stylesheet" type="text/css" />
<link href="../widgetparameter_set.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript">
<!--
	Event.observe(window, 'load', function(){
		initValidation();
		if($("innerFrame")){
			$("innerFrame").src='<%=sURL%>';
		}
	});
	/*
	*初始化校验处理
	*/
	function initValidation(){
		ValidationHelper.addValidListener(function(){
		},"data");
		ValidationHelper.addInvalidListener(function(){
		},"data");
		ValidationHelper.initValidation();
	};

	function makeData(data){
		var param= "";
		if($("头部信息") && $("Iframe高度")){
			param = "头部信息:" + $("头部信息").value + ";Iframe高度:" + $("Iframe高度").value;
		}
		return data + ";" + param;
		
	}
//-->
</SCRIPT>
</head>
<BODY style="overflow:hidden">
<div>
<%
	for (int i = 0; i < oWidgetParameters.size(); i++){
		WidgetParameter currWidgetParameter = (WidgetParameter)oWidgetParameters.getAt(i);
		String sWidgetParamName = CMyString.transDisplay(currWidgetParameter.getWidgetParamName());
		String sWidgetParamDesc = CMyString.transDisplay(currWidgetParameter.getWidgetParamDesc());	
		if(sWidgetParamName.equalsIgnoreCase(LocaleServer.getString("poll_list.id.invest","调查ID"))){
			sSelectedId = parse(currWidget,oWidgetInstance,currWidgetParameter,bAdd);
		}
		if(currWidgetParameter.getNotEdit() == 1){
			continue;
		}
		//几个固有变量在别的tabpanel列出来
		if(WidgetConstants.isFixedParameter(sWidgetParamName))continue;
%>
<div class="row" style=''>
	<div class="label"><%=CMyString.transDisplay(sWidgetParamDesc)%>：</div>
	<div class="value">
		<%=parse(currWidget,oWidgetInstance,currWidgetParameter,bAdd)%>
	</div>
</div>
<%
	}
%>
</div>
<div  style="overflow:auto;" height="100%;">
<iframe src="#" width="100%"  height="190px;" id="innerFrame" frameBorder="0"></iframe>
</div>
</BODY>
</HTML>
<%!
	private String parse(Widget currWidget,WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		int nWidgetParamType = _currWidgetParameter.getWidgetParamType();
		String sHTMLContent = null;
		switch(nWidgetParamType){
			case WidgetConstants.FIELD_TYPE_INT:
				sHTMLContent = dealWithInt(currWidget,oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
			case WidgetConstants.FIELD_TYPE_NORMALTEXT:
				sHTMLContent = dealWithNormalText(oWidgetInstance,_currWidgetParameter,_bAdd);
				break;
		}
		return sHTMLContent;
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
		sb.append(CMyString.filterForJs(getParameterValue(oWidgetInstance,_currWidgetParameter)));
		sb.append("', validation:\"type:'string',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',max_len:'80',desc:'");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamDesc()));
		sb.append("'\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}

	private String dealWithInt(Widget currWidget,WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">");
		sb.append("new com.trs.ui.XInteger({");
		sb.append("name : '");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamName()));
		sb.append("', value : '");
		sb.append(CMyString.filterForJs(getParameterValue(oWidgetInstance,_currWidgetParameter)));
		sb.append("', validation:\"type:'int',value_range:'0,2000',required:'");
		sb.append(_currWidgetParameter.getNotnull());
		sb.append("',desc:'");
		sb.append(CMyString.filterForJs(_currWidgetParameter.getWidgetParamDesc()));
		sb.append("'\"");
		sb.append(" }).render();");
		sb.append("</script>");
		return sb.toString();
	}

	private String getParameterValue(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter)throws WCMException{
		if(oWidgetInstance==null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,LocaleServer.getString("poll_list.id.obj.empty","资源实例对象为空"));
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
%>