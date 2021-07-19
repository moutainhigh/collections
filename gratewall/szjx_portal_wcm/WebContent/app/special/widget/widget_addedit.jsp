<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.Widget" %>
<%@ page import="com.trs.components.common.publish.widget.Widgets" %>
<%@ page import="com.trs.components.common.publish.widget.ResourceStyle" %>
<%@ page import="com.trs.components.common.publish.widget.ResourceStyles" %>
<%@ page import="com.trs.components.common.publish.widget.ContentStyle" %>
<%@ page import="com.trs.components.common.publish.widget.ContentStyles" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.infra.util.ExceptionNumber"%>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer"%>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@include file="../../include/public_server.jsp"%>

<%
	int nWidgetId = currRequestHelper.getInt("objectid",0);
	Widget currWidget = null;
	if(nWidgetId>0){
		currWidget = Widget.findById(nWidgetId);
		if(currWidget == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nWidgetId),WCMTypes.getLowerObjName(Widget.OBJ_TYPE)}));
		}
		if(!currWidget.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, CMyString.format(LocaleServer.getString("layout_addedit.jsp.locked", "当前对象被[{0}]锁定，您不能修改!"),  new Object[]{currWidget.getLockerUser()}));
		}
		if(!SpecialAuthServer.hasRight(loginUser, currWidget, SpecialAuthServer.WIDGET_EDIT)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, LocaleServer.getString("widget_addedit.modify.noRight","您没有权限修改资源。"));
		}
	}else{
		currWidget = Widget.createNewInstance();
		if(!SpecialAuthServer.hasRight(loginUser, currWidget, SpecialAuthServer.WIDGET_ADD)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, LocaleServer.getString("widget_addedit.build.noRight","您没有权限新建资源。"));
		}
	}
	String sWidgetName = CMyString.filterForHTMLValue(currWidget.getWname());
	String sWdigetDesc = CMyString.filterForHTMLValue(currWidget.getWdesc());
	String sWidgetContent = CMyString.filterForHTMLValue(currWidget.getWidgetcontent());
	String sWidgetAttrURL = CMyString.filterForHTMLValue(currWidget.getWidgetAttrURL());
	int nWidgetType = currWidget.getWidgetType();
	String sWDefaultStyle = currWidget.getWDefaultStyle();//资源默认风格
	String sContentStyle = currWidget.getContentStyle();//内容可用风格
	String sCDefaultStyle = CMyString.showNull(currWidget.getCDefaultStyle());//内容默认风格
	String sWidgetPic = CMyString.filterForHTMLValue(currWidget.getWidgetpic());
	String sPicFileName = mapFile(sWidgetPic);
	if(CMyString.isEmpty(sWidgetPic)){
		sPicFileName = "../images/list/none.gif";
	}
	int nWidgetCategory = currWidget.getPropertyAsInt("WidgetCategory", -1);
	String sWidgetParamPage = "widgetparameter_list.html?widgetId="+nWidgetId;
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title WCMAnt:param="widget_addedit.title">新建/修改资源</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/widgets.css" rel="stylesheet" type="text/css" />	
<link href="widget_addedit.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	*{
		margin:0px;
		padding:0px;
	}
	.wcm-tabpanel{
		width:100%;
	}
	.ext-ie6 .rstyle_box, .ext-ie6 .cstyle_content{
		width:100%;
		overflow-x:hidden;
	}
	.ext-ie6 .cstyle_content{
		height:330px!important;
	}
</style>
<script src="../../js/easyversion/lightbase.js"></script>
<script src="../../js/easyversion/extrender.js"></script>
<script src="../../js/easyversion/elementmore.js"></script>
<script src="../../js/easyversion/ajax.js"></script>
<script src="../../js/easyversion/lockutil.js"></script>
<script src="../js/adapter4Top.js"></script>

<!--validator start-->
<script src="../../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<!--validator end-->
<script src="../../js/source/wcmlib/tabpanel/TabPanel.js"></script>

<script src="widget_addedit.js"></script>
</head>

<body style="overflow:hidden;">
<form name="widget_data" id="widget_data" action="return false;">
<input type="hidden" name="objectId" id="objectId" value="<%=nWidgetId%>" />
<input type="hidden" name="PicFile" id="PicFile" value="<%=sWidgetPic%>" />
<div class="wcm-tabpanel" id="tabPanel">
	<div class="head-box">
		<div class="wrapper" style="width:100%">
			<div class="tab-head" item="tab-body-base"><div class="r"><div class="c">
				<a href="#" WCMAnt:param="widget_addedit.attribute">基本属性</a>
			</div></div></div>
			<div class="tab-head" item="tab-body-parameter"><div class="r"><div class="c">
				<a href="#" WCMAnt:param="widget_addedit.var">资源变量</a>
			</div></div></div>
			<div class="tab-head" item="tab-body-rstyle"><div class="r"><div class="c">
				<a href="#" WCMAnt:param="widget_addedit.res.style">资源风格</a>
			</div></div></div>
			<div class="tab-head" item="tab-body-cstyle"><div class="r"><div class="c">
				<a href="#" WCMAnt:param="widget_addedit.content.style">内容风格</a>
			</div></div></div>
			<div style="position:absolute;right:10px;bottom:5px;display:none;" id="searchquery-box">
				<label>检索：</label>
				<input type="text" name="searchquery" id="searchquery" value="" style="border-width:0px;border-bottom:1px solid gray;" />
			</div>
		</div>
	</div>
	<div class="body-box">
		<div class="tab-body" id="tab-body-base">
           <div class="base_block">
				<div class="row">
					<span class="leftTip" WCMAnt:param="widget_addedit.name">资源名称：</span><input type="text" name="widgetName" id="widgetName" value="<%=sWidgetName%>" oldValue="<%=sWidgetName%>" validation="type:'string',required:'true',max_len :'50',desc:'资源名称',rpc:checkWidgetName" validation_desc="资源名称" WCMAnt:paramattr="validation_desc:widget_addedit.res.name"/>
				</div>
				<div class="row">
					<span class="leftTip">前一个资源：</span><select class="cstyle_sel" name="WidgetOrder" id="WidgetOrder" _value="<%=currWidget.getOrder()%>" style="width:180px;">
						<option value="-1">最前面</option>
<%
					processor.reset();
					processor.setAppendParameters(new String[]{
						"pagesize", "-1", 
						"selectfields", "WIDGETID,WNAME,WidgetOrder"
					});

					Widgets oWidgets = (Widgets) processor.excute("wcm61_widget", "query");
					for (int i = 0; i < oWidgets.size(); i++){
						Widget obj = (Widget)oWidgets.getAt(i);
						if (obj == null)
							continue;
%>
						<option value="<%=obj.getOrder()%>">
							<%=CMyString.transDisplay(obj.getWname())%>
						</option>
<%
					}
%>
					</select>
				</div>
				<div style="padding:10px 0px 15px 64px;">
					<span>资源分类：</span>&nbsp;&nbsp;<select name="WidgetCategory" id="WidgetCategory" class="cstyle_sel" style="border:1px solid #3A72BD;width:180px;">
						<option value='-1' WCMAnt:param="widget_addedit.choice">--请选择--</option>
						<%
							//文字列表#1,图片列表#2,栏目导航#3,单篇文章#4,其他#1218
							String sWidgetCategorys = ConfigServer.getServer().getSysConfigValue("WIDGET_CATEGORYS", "");
							if(!CMyString.isEmpty(sWidgetCategorys)){
								String sItemSplit = ",";
								String sValueSplit = "#";
								String[] aWidgetCategorys = sWidgetCategorys.split(sItemSplit);
								String sWidgetCategoryName = "";
								String sWidgetCategoryId = "";
								for (int i = 0; i < aWidgetCategorys.length; i++) {
									String[] aWidgetCategory = aWidgetCategorys[i].split(sValueSplit);
									sWidgetCategoryName = aWidgetCategory[0];
									sWidgetCategoryId = aWidgetCategory[1];
						%>
						<option value='<%=CMyString.filterForHTMLValue(sWidgetCategoryId)%>'><%=CMyString.transDisplay(sWidgetCategoryName)%></option>
						<%
								}
							}
						%>
					</select>
				</div>
				<div class="row_desc">
					<span class="leftTip" WCMAnt:param="widget_addedit.desc">资源描述：</span><textarea id="widgeDesc" class="text_desc" validation="type:'string',max_len:'100',desc:'资源描述',showid:'wDescVal'"><%=sWdigetDesc%></textarea><br>
					<span id="wDescVal" class="wDescVal"></span>
				</div>
				<div class="row_content">
					<span class="leftTip" WCMAnt:param="widget_addedit.content">资源内容：</span><textarea id="widgetContent" class="text_content"  ><%=sWidgetContent%></textarea>
				</div>
				<div class="row">
					<span class="leftTip" WCMAnt:param="widget_addedit.attr.page.set">属性设置页面：</span><input type="text" name="widgetAttrURL" class="attributeAddr" id="widgetAttrURL" value="<%=sWidgetAttrURL%>" validation="type:'string',max_len :'200',desc:'地址'"/>
				</div>
				<div class="row">
					<span class="leftTip" WCMAnt:param="widget_addedit.fit.template.type">适应模板类型：</span><input type="radio" style="border:none;" name="WIDGETTYPE" id="" value="1" <%=(nWidgetType==1)?"checked":""%>  WCMAnt:param="widget_addedit.survey"/><span  WCMAnt:param="widget_addedit.survey">概览</span>&nbsp;&nbsp;<input type="radio" style="border:none;" name="WIDGETTYPE" id="" value="2" <%=(nWidgetType==2)?"checked":""%> /><span  WCMAnt:param="widget_addedit.detail">细览</span>&nbsp;&nbsp;<input type="radio" style="border:none;" name="WIDGETTYPE" id="" value="0" <%=(nWidgetType==0)?"checked":""%>/><span  WCMAnt:param="widget_addedit.fitAll">都适应</span>
				</div>
				<div class="pic_block">
					<img id="img_ViewThumb" src="<%=sPicFileName%>" border=0 alt="" style="width:172px;height:112px;"/>
					<table align="center">
					<tr>
						<td><span style="padding-left:15px;padding-top:5px;cursor:pointer;" onclick="resumeThumb()"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span></td>
						<td colSpan="2">&nbsp;</td>
						<td valign="top" align="right" width="45px" style="padding-right:20px;cursor:pointer;"><IFRAME src="../file/file_upload.jsp" id="PortalViewThumbUpload" name="PortalViewThumbUpload" frameborder="no" border="2px solid red" framespacing="0" width="45px" height="23px" scrolling="no" ></IFRAME></td>
					</tr>
				</table>
				</div>
		   </div>		
		</div>
		<div class="tab-body" id="tab-body-parameter">
            <div id="contentbox" style="width:100%;height:100%;"><iframe src="<%=sWidgetParamPage%>" style="background:transparent;border:0;" frameborder="0" width="100%" height="100%" id="paramIfrm"></iframe></div>	
			<div id="divMask" style="position: absolute; z-index: 999; background: whitesmoke; display: none; margin-bottom: 5px; border: 1px solid #C7C7C7;width:100%!important;"><div class="no_object_found" style="font-style:normal;"  WCMAnt:param="widget_addedit.save">请先保存资源再来维护资源变量</div></div>
		</div>
		
		<div class="tab-body" id="tab-body-rstyle">
            <div class="rstyle_box" id="rstyle_box" style="height:356px;">
				<%
					processor.setAppendParameters(new String[]{
						"PageStyleId" ,"0"
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
				<div class="thumb" id="thumb_item_<%=nRowId%>" stylename="<%=CMyString.filterForHTMLValue(sRStyleName)%>" itemId="<%=nRowId%>" title="<%=LocaleServer.getString("widget.label.websiteId", "编号")%>:&nbsp;<%=nRowId%>&#13;<%=LocaleServer.getString("widget_addedit.widget.label.chnlname", "资源风格名称")%>:&nbsp;<%=CMyString.filterForHTMLValue(sRStyleName)%>&#13;<%=LocaleServer.getString("widget.label.cruser", "创建者")%>:&nbsp;<%=CMyString.filterForHTMLValue(sCrUser)%>&#13;<%=LocaleServer.getString("widget.label.crtime", "创建时间")%>:&nbsp;<%=CMyString.filterForHTMLValue(sCrTime)%>">
					<div><img src="<%=CMyString.filterForHTMLValue(sRPicFileName)%>" width="330px" height="200px" border="0" alt=""></div>
					<div class="desc" style="width:300px;overflow:hidden;vertical-align:middle;">
						<input type="radio" name="WStyle" style="border:0px;" value="<%=CMyString.filterForHTMLValue(sRStyleKey)%>" id="wstyle-cbx_<%=nRowId%>" <%=(sRStyleKey.equals(sWDefaultStyle))?"checked":""%>/> <label for="wstyle-cbx_<%=nRowId%>"><%=CMyString.transDisplay(sRStyleName)%></label>
					</div>
				</div>
				<%
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				%>
			</div>
			<div style='height:30px;line-height:30px;background:#E4F4FE;padding-left:10px;'><input type="checkbox" name="" id="cancelSel" value="" style="border:none;"/> <label for="cancelSel" WCMAnt:param="widget_addedit.cancel">取消选择</label></div>
		</div>
		<div class="tab-body" id="tab-body-cstyle">
            <div class="cStyle_block">
				<div class="cstyle_content" id="cstyle_content" style="height:351px;">
					<%
						processor.reset();
						processor.setAppendParameters(new String[]{
							"PageStyleId" ,"0"
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
								//是否需要权限的处理
								String sCrUser = obj.getPropertyAsString("cruser");
								String sCrTime = obj.getPropertyAsString("CrTime");
								String sStyleThumb = CMyString.transDisplay(obj.getStyleThumb());
								String sCPicFileName = mapFile(sStyleThumb);
								if(CMyString.isEmpty(sStyleThumb)){
									sCPicFileName = "../images/none_small.gif";
								}
					%>

					<div class="thumb" id="thumb_item_<%=nRowId%>" stylename="<%=CMyString.filterForHTMLValue(sCStyleName)%>" itemId="<%=nRowId%>" title="<%=LocaleServer.getString("widget.label.websiteId", "编号")%>:&nbsp;<%=nRowId%>&#13;<%=LocaleServer.getString("widget.label.chnlname", "内容风格名称")%>:&nbsp;<%=CMyString.filterForHTMLValue(sCStyleName)%>&#13;<%=LocaleServer.getString("widget.label.cruser", "创建者")%>:&nbsp;<%=CMyString.filterForHTMLValue(sCrUser)%>&#13;<%=LocaleServer.getString("widget.label.crtime", "创建时间")%>:&nbsp;<%=CMyString.filterForHTMLValue(sCrTime)%>" style="margin-right:20px;">
						<div><img src="<%=CMyString.filterForHTMLValue(sCPicFileName)%>" width="330px" height="200px" border="0" alt=""></div>
						<div class="desc" style="width:300px;overflow:hidden;">
							<input type="checkbox" name="CStyle" style="border:0px;" _value="<%=nRowId%>" value="<%=CMyString.filterForHTMLValue(sCStyleKey)%>" id="cstyle-cbx_<%=nRowId%>" cStyleName="<%=CMyString.filterForHTMLValue(sCStyleName)%>" hasSelect="<%=includeCSty(sContentStyle,sCStyleKey)?true:false%>"/> <label for="cstyle-cbx_<%=nRowId%>"><%=CMyString.transDisplay(sCStyleName)%></label>
						</div>
					</div>

					<%
							}catch(Exception ex){
								ex.printStackTrace();
							}
						}
					%>
				</div>
				
				<div class="cDefaultStyle_box"><span class="cstyle_seltitle" WCMAnt:param="widget_addedit.moren">资源的默认内容风格：</span><select name="" id="cDefalutStyle" class="cstyle_sel">
					<option value='' WCMAnt:param="widget_addedit.choice">--请选择--</option>
				</select></div>
			</div>	
		</div>
	</div>
</div>
</form>
<script language="javascript">
<!--

	$('WidgetCategory').value = '<%=nWidgetCategory%>';
	if($('WidgetCategory').selectedIndex == -1){
		$('WidgetCategory').selectedIndex = 0;
	}
	Event.observe('cstyle_content','click', function(event){
		//refreshCStyle();
		event = window.event || event;
		var srcElement = event.srcElement || event.target;
		if(srcElement.tagName.toLowerCase()!="input")return;
		if(srcElement.type.toLowerCase()!="checkbox")return;
		//1.获取cstyle_content下的所有checkbox。
		//2.判断checkbox选中的个数。
		//3.如果选中的个数超过了30个，提示用户资源可用内容风格不能超过30个，返回不做选中后操作，将选择的checkbox取消选中。
		var cStylesNum = 0;
		var doms = document.getElementById("cstyle_content").getElementsByTagName("input");
		for(var i=0;i<doms.length;i++){
			if(doms[i].type.toLowerCase()!="checkbox")return;
			if(doms[i].checked){
				cStylesNum++;
			}
			if(cStylesNum > 30){
				Ext.Msg.alert("选中的可用内容风格不可超过30个！");
				doms[i].checked = false;
				return false;
			}
		}
		if(srcElement.checked){
			var optionEl = document.createElement("OPTION");
			$('cDefalutStyle').appendChild(optionEl);
			optionEl.setAttribute("id","selCStyle"+srcElement.getAttribute("_value"));
			optionEl.value = srcElement.value;
			optionEl.innerHTML = srcElement.getAttribute("cStyleName");
			if(srcElement.value=="<%=sCDefaultStyle%>"){optionEl.selected=true;}
		}else{
			if($('selCStyle'+srcElement.getAttribute("_value")))
				$('cDefalutStyle').removeChild($('selCStyle'+srcElement.getAttribute("_value")));
		}
	});
	//取消选择的处理
	Event.observe(window, 'load', function(){
		var doms = document.getElementsByName('WStyle');
		var bIsHasWStyle =true;
		for (var i = 0,nLen = doms.length; i < nLen; i++){
			if(doms[i].checked){
				bIsHasWStyle = false;
				break;
			}
		}
		$('cancelSel').checked = bIsHasWStyle;
	});

	Event.observe($('cancelSel'), 'click', function(){
		if($('cancelSel').checked){
			var doms = document.getElementsByName('WStyle');
			for (var i = 0,nLen = doms.length; i < nLen; i++){
				if(doms[i].checked){
					doms[i].checked = false;
				}
			}
		}
	});

	//如果有选中了资源风格，则取消选择的这个checkbox需要设置为没有选中
	Event.observe('rstyle_box', 'click', function(event){
		if($('cancelSel').checked){//只有它被选中了才需要做
			event = window.event || event;
			var srcElement = event.srcElement || event.target;
			if(srcElement.tagName.toLowerCase()!="input")return;
			if(srcElement.type.toLowerCase()!="radio")return;
			if(srcElement.checked){
				$('cancelSel').checked = false;
			}
		}
	});

	var nWidgetId = <%=nWidgetId%>;
	var nObjType = <%=currWidget.getWCMType()%>;
	Event.observe(window, 'unload', function(){
		if(nWidgetId != 0){
			LockerUtil.unlock(nWidgetId, nObjType);
		}
	});
//-->
</script>
</body>
</html>
<%!
	private String mapFile(String _sFileName){
		if(CMyString.isEmpty(_sFileName) || _sFileName.equals("0") || _sFileName.equalsIgnoreCase("none.gif")){
			return "../images/list/none.gif";
		}else{
			return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
		}
	}
	private boolean includeCSty(String _sContentStyle,String _sCStyle) throws WCMException{
		if(CMyString.isEmpty(_sContentStyle))return false;
		String[] aContentStyle = _sContentStyle.split(",");
		for (int i = 0; i < aContentStyle.length; i++){
			if(aContentStyle[i].equals(_sCStyle)){
				return true;
			}
			continue;
		}
		return false;
	}
%>