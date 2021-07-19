<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>

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
	int nOldWidgetInstanceId = processor.getParam("oldWidgetInstanceId",0);
	int nWidgetInstanceId = currRequestHelper.getInt("widgetInstanceId",0);
	int nPageStyleId = currRequestHelper.getInt("pageStyleId",0);
	//区分新增和修改
	boolean bAdd = currRequestHelper.getBoolean("bAdd",true);
	WidgetInstance oWidgetInstance = WidgetInstance.findById(nWidgetInstanceId);
	if(oWidgetInstance==null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,LocaleServer.getString("widgetparameter_set.noeffect","没有传入有效的widgetInstanceId"));
	}
	int nTemplateId = oWidgetInstance.getTemplateId();
	int nWidgetId = oWidgetInstance.getWidgetId();
	processor.setAppendParameters(new String[]{
		"widgetId" ,""+nWidgetId
	});
	WidgetParameters oWidgetParameters = (WidgetParameters) processor.excute("wcm61_widgetparameter", "query");
	
	Widget currWidget = Widget.findById(nWidgetId);
	if(currWidget==null)throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("widgetparameter_set..id.zero","没有找到Widget对象[Id={0}]"),new int[]{nWidgetId}));
	String sWidgetAttrURL = currWidget.getWidgetAttrURL();
	int bAddforInt = bAdd?1:0;
	if(CMyString.isEmpty(sWidgetAttrURL)){
		sWidgetAttrURL = "base_parameter_set.jsp";
	}
	String sWDefaultStyle = currWidget.getWDefaultStyle();//资源默认风格
	String sContentStyle = currWidget.getContentStyle();//内容可用风格
	String sCDefaultStyle = currWidget.getCDefaultStyle();//内容默认风格
	String sWStyleFlag = "";//改用风格标识
	String sCStyleFlag = "";
	//获取指定的资源风格和内容风格
	if(bAdd){//如果是新增则表示使用的是默认的资源风格和内容风格
		sWStyleFlag = sWDefaultStyle;
		sCStyleFlag = sCDefaultStyle;
	}else{//修改
		for (int i = 0; i < oWidgetParameters.size(); i++){
			WidgetParameter currWidgetParameter = (WidgetParameter)oWidgetParameters.getAt(i);
			String sWidgetParamName = CMyString.transDisplay(currWidgetParameter.getWidgetParamName());
			if(WidgetConstants.FIEXED_PARAMETERS[0][0].equalsIgnoreCase(sWidgetParamName)){
				String sWStyleValue = getParameterValue(oWidgetInstance,currWidgetParameter);
				if(!CMyString.isEmpty(sWStyleValue)){
					sWStyleFlag = sWStyleValue;
				}else {
					//sWStyleFlag = sWDefaultStyle;
				}
			}else if(WidgetConstants.FIEXED_PARAMETERS[1][0].equalsIgnoreCase(sWidgetParamName)){
				String sCStyleValue = getParameterValue(oWidgetInstance,currWidgetParameter);
				if(!CMyString.isEmpty(sCStyleValue)){
					sCStyleFlag = sCStyleValue;
				}else {
					//sCStyleFlag = sCDefaultStyle;
				}
			}
		}
	}
	
	String sWidgetPic = CMyString.filterForHTMLValue(currWidget.getWidgetpic());
	String sPicFileName = mapFile(sWidgetPic);
	if(CMyString.isEmpty(sWidgetPic)){
		sPicFileName = "../images/list/none.gif";
	}
	
	
%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="widgetparameter_set.title">设置资源变量</title>

<script src="../../application/core/core.js"></script>
<script src="../../application/core/com.trs.util/Observable.js"></script>
<script src="../../application/core/com.trs.util/CAjaxCaller.js"></script>
<script src="../../application/core/com.trs.ui/BaseComponent.js"></script>
<script src="../../application/core/com.trs.ui/radio/XRadio.js"></script>
<link href="../../application/core/com.trs.ui/radio/resource/XRadio.css" rel="stylesheet" type="text/css" />

<script src="../../js/easyversion/lightbase.js"></script>
<script src="../../js/easyversion/extrender.js"></script>
<script src="../../js/easyversion/elementmore.js"></script>
<script src="../js/adapter4Top.js"></script>
<script src="../../js/source/wcmlib/tabpanel/TabPanel.js"></script>
<script language="javascript" src="widgetparameter_set.js" type="text/javascript"></script>
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/tabpanel.css" rel="stylesheet" type="text/css" />
<link href="widgetparameter_set.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.wcm-tabpanel{
		width:100%;
	}
	.wcm-tabpanel .body-box{
		height:240px;
		height:350px;
	}
	.ext-ie6 .rstyle_box{
		width:100%;
		overflow-x:hidden;
	}
	.wcm-tabpanel .head-box .wrapper{position:absolute;left:0px;right:0px;width:100%;}

</style>
</head>

<body>
<div class="box" id="data" action="">
	<input type="hidden" name="widgetInstanceId" id="widgetInstanceId" value="<%=nWidgetInstanceId%>" />
	<input type="hidden" name="templateId" id="templateId" value="<%=nTemplateId%>" />
	<input type="hidden" name="widgetId" id="widgetId" value="<%=nWidgetId%>" />
	<input type="hidden" name="bAdd" id="bAdd" value="<%=bAdd%>" />
	<input type="hidden" name="cStyle" id="cStyle" value="<%=CMyString.filterForHTMLValue(sCStyleFlag)%>" />
	<input type="hidden" name="wStyle" id="wStyle" value="<%=CMyString.filterForHTMLValue(sWStyleFlag)%>" />
	<div class="wcm-tabpanel" id="tabPanel">
	<div class="head-box">
		<div class="wrapper">
			<div class="tab-head" item="tab-body-base"><div class="r"><div class="c">
				<a href="#" WCMAnt:param="widgetparameter_set.attribute">基本属性</a>
			</div></div></div>
			<div class="tab-head" item="tab-body-rstyle"><div class="r"><div class="c">
				<a href="#" WCMAnt:param="widgetparameter_set.res.style">资源风格</a>
			</div></div></div>
			<div class="tab-head" item="tab-body-cstyle"><div class="r"><div class="c">
				<a href="#" WCMAnt:param="widgetparameter_set.cont.style">内容风格</a>
			</div></div></div>
			<div style="position:absolute;right:10px;bottom:5px;display:none;" id="searchquery-box">
				<label>检索：</label>
				<input type="text" name="searchquery" id="searchquery" value="" style="border-width:0px;border-bottom:1px solid gray;" />
			</div>
		</div>
	</div>
	<div class="body-box" id="body-box">
		<div class="tab-body" id="tab-body-base">
			<div class="base_block" id="base_block" style="height:340px;">
			<iframe src="<%=sWidgetAttrURL+"?widgetInstanceId="+nWidgetInstanceId+"&bAdd="+bAddforInt%>&oldWidgetInstanceId=<%=nOldWidgetInstanceId%>" style="background:transparent;border:0;width:100%;height:100%;" id="main" frameborder="0" ></iframe>
			</div>
		</div>
		<div class="tab-body" id="tab-body-rstyle">
			<div>
			<div class="rstyle_box" id="rstyle_box" style="height:302px;">
				<%
					processor.setAppendParameters(new String[]{
						"PageStyleId" ,""+nPageStyleId
					});
					ResourceStyles oResourceStyles = (ResourceStyles) processor.excute("wcm61_resourcestyle", "query");
					for (int i = 0; i < oResourceStyles.size(); i++){
						try{
							ResourceStyle obj = (ResourceStyle)oResourceStyles.getAt(i);
							if (obj == null)
								continue;
							int nRowId = obj.getId();
							String sRStyleName = obj.getPropertyAsString("STYLENAME");
							String sRStyleKey = obj.getCssFlag();
							//是否需要权限的处理
							String sCrUser = obj.getPropertyAsString("cruser");
							String sCrTime = obj.getPropertyAsString("CrTime");
							String sStyleThumb = CMyString.transDisplay(obj.getStyleThumb());
							String sRPicFileName = mapFile(sStyleThumb);
							if(CMyString.isEmpty(sStyleThumb)){
								sRPicFileName = "../images/none_small.gif";
							}
				%>
				<div class="thumb" id="thumb_item_<%=nRowId%>" stylename="<%=CMyString.filterForHTMLValue(sRStyleName)%>" itemId="<%=nRowId%>" title="<%=LocaleServer.getString("widget.label.websiteId", "编号")%>:&nbsp;<%=nRowId%>&#13;<%=LocaleServer.getString("widgetparameter_set.widget.label.chnlname", "资源风格名称")%>:&nbsp;<%=CMyString.filterForHTMLValue(sRStyleName)%>&#13;<%=LocaleServer.getString("widget.label.cruser", "创建者")%>:&nbsp;<%=CMyString.filterForHTMLValue(sCrUser)%>&#13;<%=LocaleServer.getString("widget.label.crtime", "创建时间")%>:&nbsp;<%=CMyString.filterForHTMLValue(sCrTime)%>">
					<div><img src="<%=CMyString.filterForHTMLValue(sRPicFileName)%>" width="330px" height="200px" border="0" alt=""></div>
					<div class="desc" style="width:300px;overflow:hidden;">
						<input type="radio" name="WStyle_option" style="border:0px;" value="<%=CMyString.filterForHTMLValue(sRStyleKey)%>" id="wstyle-cbx_<%=nRowId%>" <%=(sRStyleKey.equals(sWStyleFlag))?"checked":""%>/> <label for="wstyle-cbx_<%=nRowId%>"><%=CMyString.transDisplay(sRStyleName)%></label>
					</div>
				</div>
				<%
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				%>
			</div>
			
			<%
				for (int i = 0; i < oWidgetParameters.size(); i++){
					WidgetParameter currWidgetParameter = (WidgetParameter)oWidgetParameters.getAt(i);
					String sWidgetParamName = CMyString.transDisplay(currWidgetParameter.getWidgetParamName());
					//列出是否显示头部
					if(!WidgetConstants.FIEXED_PARAMETERS[2][0].equalsIgnoreCase(sWidgetParamName))continue;
					sWidgetParamName = WidgetConstants.FIEXED_PARAMETERS[2][1];
			%>
			<div class="row" style='height:30px;line-height:30px;background:#E4F4FE;'>
				<div class="label"><%=sWidgetParamName%>：</div>
				<div style="float:right;margin-right:15px;"><input type="checkbox" name="" id="cancelSelWStyle" value="" style="border:none;"/> <label for="cancelSelWStyle" WCMAnt:param="widgetparameter_set.cancel">取消选择</label></div>
				<div class="value">
					<%=dealWithTrueOrFalse(oWidgetInstance,currWidgetParameter,bAdd)%>
				</div>
				
			</div>
			<%
				}
			%>

			
			</div>
		</div>
		<div class="tab-body" id="tab-body-cstyle">
            <div>
				<div class="cstyle_content" id="cstyle_content" style="height:304px;">
					<%
						processor.reset();
						processor.setAppendParameters(new String[]{
							"PageStyleId" ,""+nPageStyleId
						});
						ContentStyles oContentStyles = (ContentStyles) processor.excute("wcm61_contentstyle", "query");
						for (int i = 0; i < oContentStyles.size(); i++){
							try{
								ContentStyle obj = (ContentStyle)oContentStyles.getAt(i);
								if (obj == null)
									continue;
								int nRowId = obj.getId();
								String sCStyleName = obj.getPropertyAsString("STYLENAME");
								String sCStyleKey = obj.getCssFlag();
								if(!includeCSty(sContentStyle,sCStyleKey))continue;
								//是否需要权限的处理
								String sCrUser = obj.getPropertyAsString("cruser");
								String sCrTime = obj.getPropertyAsString("CrTime");
								String sStyleThumb = CMyString.transDisplay(obj.getStyleThumb());
								String sCPicFileName = mapFile(sStyleThumb);
								if(CMyString.isEmpty(sStyleThumb)){
									sCPicFileName = "../images/none_small.gif";
								}
					%>

					<div class="thumb" id="thumb_item_<%=nRowId%>" stylename="<%=CMyString.filterForHTMLValue(sCStyleName)%>" itemId="<%=nRowId%>" title="<%=LocaleServer.getString("widget.label.websiteId", "编号")%>:&nbsp;<%=nRowId%>&#13;<%=LocaleServer.getString("widget.label.chnlname", "内容风格名称")%>:&nbsp;<%=CMyString.filterForHTMLValue(sCStyleName)%>&#13;<%=LocaleServer.getString("widget.label.cruser", "创建者")%>:&nbsp;<%=CMyString.filterForHTMLValue(sCrUser)%>&#13;<%=LocaleServer.getString("widget.label.crtime", "创建时间")%>:&nbsp;<%=CMyString.filterForHTMLValue(sCrTime)%>">
						<div><img src="<%=sCPicFileName%>" width="330px" height="200px" border="0" alt=""></div>
						<div class="desc" style="width:300px;overflow:hidden;">
							<input type="radio" name="CStyle_option" style="border:0px;" value="<%=CMyString.filterForHTMLValue(sCStyleKey)%>" id="cstyle-cbx_<%=nRowId%>" cStyleName="<%=CMyString.filterForHTMLValue(sCStyleName)%>" <%=(sCStyleKey.equals(sCStyleFlag))?"checked":""%>/> <label for="cstyle-cbx_<%=nRowId%>"><%=CMyString.transDisplay(sCStyleName)%></label>
						</div>
					</div>

					<%
							}catch(Exception ex){
								ex.printStackTrace();
							}
						}
					%>
				</div>
				<div style="height:30px;line-height:30px;background:#E4F4FE;padding-left:10px;"><input type="checkbox" name="" id="cancelSelCStyle" value="" style="border:none;"/> <label for="cancelSelCStyle" WCMAnt:param="widgetparameter_set.cancel">取消选择</label></div>
			</div>	
		</div>
	</div>
</div>
<script language="javascript">
<!--
	//资源风格取消选择的处理
	Event.observe($('cancelSelWStyle'), 'click', function(){
		if($('cancelSelWStyle').checked){
			var doms = document.getElementsByName('WStyle_option');
			for (var i = 0,nLen = doms.length; i < nLen; i++){
				if(doms[i].checked){
					doms[i].checked = false;
				}
			}
		}
	});
	//如果有选中了资源风格，则取消选择的这个checkbox需要设置为没有选中
	Event.observe('rstyle_box', 'click', function(event){
		if($('cancelSelWStyle').checked){//只有它被选中了才需要做
			event = window.event || event;
			var srcElement = event.srcElement || event.target;
			if(srcElement.tagName.toLowerCase()!="input")return;
			if(srcElement.type.toLowerCase()!="radio")return;
			if(srcElement.checked){
				$('cancelSelWStyle').checked = false;
			}
		}
	});
	//内容风格取消选择的处理
	Event.observe($('cancelSelCStyle'), 'click', function(){
		if($('cancelSelCStyle').checked){
			var doms = document.getElementsByName('CStyle_option');
			for (var i = 0,nLen = doms.length; i < nLen; i++){
				if(doms[i].checked){
					doms[i].checked = false;
				}
			}
		}
	});
	//如果有选中了内容风格，则取消选择的这个checkbox需要设置为没有选中
	Event.observe('cstyle_content', 'click', function(event){
		if($('cancelSelCStyle').checked){//只有它被选中了才需要做
			event = window.event || event;
			var srcElement = event.srcElement || event.target;
			if(srcElement.tagName.toLowerCase()!="input")return;
			if(srcElement.type.toLowerCase()!="radio")return;
			if(srcElement.checked){
				$('cancelSelCStyle').checked = false;
			}
		}
	});
//-->
</script>
</body>
</html>
<%!

	private String getParameterValue(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter)throws WCMException{
		if(oWidgetInstance==null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,LocaleServer.getString("widgetparameter_set.null","资源实例对象为空"));
		}
		String sWidgetParamName = _currWidgetParameter.getWidgetParamName();
		String sDefaultValue = CMyString.showNull(_currWidgetParameter.getDefaultValue(),"");
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
		sb.append("' }).render();");
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
	private boolean includeCSty(String _sContentStyle,String _sCStyleFlag) throws WCMException{
		if(CMyString.isEmpty(_sContentStyle))return false;
		String[] aContentStyle = _sContentStyle.split(",");
		for (int i = 0; i < aContentStyle.length; i++){
			//ContentStyle oContentStyle = ContentStyle.findById(Integer.parseInt(aContentStyle[i]));
			//if(oContentStyle==null)continue;
			//String sCanSelCStyleFlag = oContentStyle.getCssFlag();
			if(_sCStyleFlag.equals(aContentStyle[i])){
				return true;
			}
			continue;
		}
		return false;
	}

%>