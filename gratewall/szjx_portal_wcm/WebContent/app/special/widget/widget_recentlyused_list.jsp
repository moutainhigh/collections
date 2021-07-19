<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.Widget" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.lang.Integer"%>
<%@include file="../../include/public_server.jsp"%>
<%	
	int nWidgetType = Integer.parseInt(request.getParameter("widgetType"));
	//从cookie中获取最近使用的资源的id序列
	Cookie[] cookies = request.getCookies();
	String sRecentWidgetIds = null;
	String[] aRecentWidgetIds = null;
	if(cookies != null){
		//从cookie中获取上次选择记录的WidgetIds
		for(int i = 0; i < cookies.length; i++){
			if(cookies[i].getName().equals("WidgetIds")){
				sRecentWidgetIds = cookies[i].getValue();
				break;
			}
		}
		if(sRecentWidgetIds != null)
			aRecentWidgetIds = sRecentWidgetIds.split("/");
	}

%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="widget_recentlyused_list.title">最近使用的资源块</title>
</head>
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/list.css" rel="stylesheet" type="text/css" />
<script src="../../js/easyversion/lightbase.js"></script>
<script src="../../js/easyversion/extrender.js"></script>
<script src="../../js/easyversion/elementmore.js"></script>
<script src="../js/adapter4Top.js"></script>
<style type="text/css">
html,body{
	height:100%;
	width:100%;
	padding:0px;
	width:0px;
	overflow:hidden;
}
.listBox{
	height:100%;
	width:100%;
}
.thumb .cmds .selectWidget{
	width:260px;
	height:26px;
	overflow:hidden;
	white-space:nowrap;
	background-image: url(../images/widget/zyxz_an.png);
	background-repeat: no-repeat;
	cursor:pointer;
}
.cruser {
	height:20px!important;
	line-height:20px!important;
}
.crtime{
	height:20px!important;
	line-height:20px!important;
}
.ext-ie6 .content{
	top:20px;
	bottom:10px;
	left:0px;
	right:0px;
	padding:20px 10px;
}
.ext-strict .content{
	top:20px;
	bottom:10px;
	left:0px;
	right:0px;
	padding:20px 10px;
}
.thumb .img-box{
	width:260px;
	height:200px;
	line-height:200px;
	overflow:hidden;
	vertical-align:middle;
	text-align:center;
}
</style>
<script language="javascript">
<!--
	//将最新访问的几个资源块记录在cookie中
function setRecentWidgetInCookie(_selId){
	if(_selId <= 0)return;
	var currCookieForWidget = getCookieValue('WidgetIds');
	var aCurrCookieForWidget = currCookieForWidget.split('/');
	//经重复的id从记录的cookie中移除掉
	aCurrCookieForWidget = removeRepeatCookieId(_selId, aCurrCookieForWidget);
	//当前已经存储的个数为6，则移除掉最后一位。
	if(aCurrCookieForWidget.length == 6){
		aCurrCookieForWidget = aCurrCookieForWidget.slice(0, (aCurrCookieForWidget.length-1));
	}
	//循环往后移位，将当前放置第一位
	aCurrCookieForWidget = moveToAfterAndSetFirst(_selId, aCurrCookieForWidget);
	//将数组组织为一个用/分隔的串
	currCookieForWidget = aCurrCookieForWidget.join('/');
	//设置当前cookie
	document.cookie='WidgetIds=' + currCookieForWidget;
}
function removeRepeatCookieId(_selId, aCurrCookieForWidget){
	if(aCurrCookieForWidget.length == 0 || _selId <= 0)
		return aCurrCookieForWidget;
	for(var i=0; i<aCurrCookieForWidget.length; i++){
		if(aCurrCookieForWidget[i] != _selId) 
			continue;
		else{
			if(i < (aCurrCookieForWidget.length - 1)){
				for(var p = i; p < aCurrCookieForWidget.length; p++){
					if(p == aCurrCookieForWidget.length - 1)continue;
					aCurrCookieForWidget[p] = aCurrCookieForWidget[p+1];
				}
			}
			aCurrCookieForWidget = aCurrCookieForWidget.slice(0, (aCurrCookieForWidget.length-1));
			break;
		}
	}
	return aCurrCookieForWidget;
}

function moveToAfterAndSetFirst(_selId, aCurrCookieForWidget){
	if(aCurrCookieForWidget.length > 0){
		for(var k=aCurrCookieForWidget.length-1; k>=0; k--){
			aCurrCookieForWidget[k+1] = aCurrCookieForWidget[k];
		}
	}
	aCurrCookieForWidget[0] = _selId;
	return aCurrCookieForWidget;
}

function getCookieValue(_cookieKey){
	var cookieValue = '';
	var arrStr = document.cookie.split(";");
	for(var k = 0; k < arrStr.length; k++){
		var temp = arrStr[k].split("=");
		if(temp[0].trim() == _cookieKey){
			cookieValue = temp[1];
			break;
		}
	}
	return cookieValue;
}
function selectWidget(_widgetId, _widgetName){
	var oPostData = {
		templateId : getParameter("templateId"),
		widgetId : _widgetId
	};
	var postDataHelper = new com.trs.web2frame.BasicDataHelper();
	postDataHelper.Call('wcm61_visualtemplate', 'saveWidgetInstance', oPostData, true, function(_trans,json){
		var oparams = {
			bAdd : 1,
			oldWidgetInstanceId : getParameter("oldWidgetInstanceId"),
			pageStyleId : getParameter("pageStyleId"),
			widgetInstanceId : $v(json,"RESULT")
		};
			//需要给专题页面返回资源实例id
		wcm.CrashBoarder.get('set_widgetparameter_value').show({
			title : '设置资源属性',
			src : 'widget/widgetparameter_set.jsp',
			width:'850px',
			height:'450px',
			params : oparams,
			maskable:'true',
			callback : function(params){
				setRecentWidgetInCookie(_widgetId);
				var cbr = wcm.CrashBoarder.get('add-widget-to-page');
				cbr.notify({id:oparams['widgetInstanceId'], wName:_widgetName});
				cbr.close();
			}
		});	
	});
	
}

function resizeIfNeed(_loadImg){
	if(!_loadImg) return;
	var maxWidth = 260;
	var maxHeight = 200;

	getFittableImageSize(_loadImg, maxWidth, maxHeight, function(width, height){
		_loadImg.width = width;
		_loadImg.height = height;
	});
}


function getFittableImageSize(imgDom, maxWidth, maxHeight, fCallback){
	if(!imgDom) return;
	var img = new Image();
	img.onload = function(){
		var height = img.height, width = img.width;

		if(height > maxHeight || width > maxWidth){	
			if(height > width){
				width = maxHeight * width/height;
				height = maxHeight
			}else{
				height = maxWidth * height/width;
				width = maxWidth;
			}
		}
		img.onload = null;
		if(fCallback) fCallback(width, height);
	}
	img.src = imgDom.src;
}


(function(){
	
	function getTopWin(){
		try{
			return window.top;
		}catch(error){
			return window;
		}
	}

	window.imageMouseOver = function(event, imgDom, index){
		initBigImageBox();
		var srcElement = Event.element(event||window.event);
		var position = Position.getPageInTop(srcElement);
		getFittableImageSize(imgDom, maxWidth, maxHeight, function(width, height){
			var imgEl = getTopWin().$(bigImageId), tmpBigImageBox = getTopWin().$(bigImageBoxId);
			imgEl.src = imgDom.src;
			imgEl.style.width = width + "px";
			imgEl.style.height = height + "px";

			var offset = (index % 2 == 0) ? -width - 5 : imgDom.offsetWidth + 5;
			tmpBigImageBox.style.left = Math.max(0, position[0] + offset) + 'px';
			tmpBigImageBox.style.top = position[1] + 'px';
			tmpBigImageBox.style.width = width + "px";
			tmpBigImageBox.style.height = height + "px";
			tmpBigImageBox.style.display = 'block';
		});
	}

	var maxWidth = 900;
	var maxHeight = 600;
	var bigImageBoxId = 'trs-big-image-box';
	var bigImageId = 'trs-big-image';

	function initBigImageBox(){
		if(getTopWin().$(bigImageBoxId)) return;

		var tmpBigImageBox = getTopWin().document.createElement('div');
		tmpBigImageBox.id = bigImageBoxId;
		tmpBigImageBox.style.visibility = 'hidden';
		Element.addClassName(tmpBigImageBox, 'big-image-box');
		getTopWin().document.body.appendChild(tmpBigImageBox);

		var arrow = getTopWin().document.createElement('div');
		Element.addClassName(arrow, 'arrow');
		tmpBigImageBox.appendChild(arrow);

		var imgEl = getTopWin().document.createElement('img');
		imgEl.id = bigImageId;
		tmpBigImageBox.appendChild(imgEl);
	}

	window.imageMouseOver = function(event, thumb, index){
		initBigImageBox();
		var imgDom = thumb.getElementsByTagName('img')[0];

		var position = Position.getPageInTop(thumb);
		getFittableImageSize(imgDom, maxWidth, maxHeight, function(width, height){
			var imgEl = getTopWin().$(bigImageId), tmpBigImageBox = getTopWin().$(bigImageBoxId);
			imgEl.src = imgDom.src;
			imgEl.style.width = width + "px";
			imgEl.style.height = height + "px";

			if(height < 114){
				tmpBigImageBox.style.height = "140px";
			}else{
				tmpBigImageBox.style.height = "auto";
			}

			var left = (index % 2 == 0) ? (position[0] - imgEl.offsetWidth - 32) : (position[0] + thumb.offsetWidth + 10);
			var top = position[1] - (tmpBigImageBox.offsetHeight - thumb.offsetHeight) / 2;
			if(top < 0) top = 0;
			var topBodyHeight = getTopWin().document.body.offsetHeight;
			if((top + tmpBigImageBox.offsetHeight) > topBodyHeight) top = topBodyHeight - tmpBigImageBox.offsetHeight;

			tmpBigImageBox.style.left = left + 'px';
			tmpBigImageBox.style.top = top + 'px';
			tmpBigImageBox.style.visibility = 'visible';
			Element[index %2 == 0 ? 'addClassName' : 'removeClassName'](tmpBigImageBox, 'arrow-right');
		});
	}

	window.imageMouseOut = function(event, thumb){
		event = event || window.event;
		var toElement = event.toElement;
		if(thumb.contains(toElement)) return;
		var tmpBigImageBox = getTopWin().$(bigImageBoxId);
		if(tmpBigImageBox) tmpBigImageBox.style.visibility = 'hidden';
	}

	window.imageMouseDown = function(event, thumb){
		var tmpBigImageBox = getTopWin().$(bigImageBoxId);
		if(tmpBigImageBox) tmpBigImageBox.style.visibility = 'hidden';
	}

})();
//-->
</script>
<body>
<%
	if(aRecentWidgetIds != null){
%>
	<div class="content" id="list-data">
<%
		for(int i=0; i < aRecentWidgetIds.length; i++){
			int nWidgetId =  Integer.parseInt((String)aRecentWidgetIds[i]);
			if(nWidgetId > 0){
				Widget oWidget = Widget.findById(nWidgetId);
				if(oWidget == null){
					continue;
				}else{
					int nCurrWtype = oWidget.getWidgetType();
					if(nCurrWtype > 0 && nCurrWtype != nWidgetType)
						continue;
					String sName = CMyString.showNull(oWidget.getWname());
					String sCrTime = CMyString.showNull(oWidget.getCrTime().toString(CMyDateTime.DEF_DATETIME_FORMAT_PRG));
					String sCrUser = CMyString.showNull(oWidget.getCrUserName());
					String sWidgetPic = CMyString.transDisplay(oWidget.getWidgetpic());
					String sPicFileName = mapFile(sWidgetPic);
					if(CMyString.isEmpty(sWidgetPic)){
						sPicFileName = "../images/list/none.gif";
					}
%>
	<div class="thumb" id="thumb_item_<%=nWidgetId%>" itemId="<%=nWidgetId%>" wName="<%=CMyString.filterForHTMLValue(sName)%>"   onmouseover="imageMouseOver(event, this, <%=i-1%>);" onmouseout="imageMouseOut(event, this, <%=i-1%>);" onmousedown="imageMouseDown(event, this);">
	<div class="desc">
		<span><%=i+1%>.</span> <%=CMyString.transDisplay(sName)%>
	</div>
	<div class="img-box"><img src="<%=sPicFileName%>" border="0" alt="" onload="resizeIfNeed(this);"></div>
	<div class="cruser" WCMAnt:param="widget_recentlyused_list.creator">创建者：<%=sCrUser%></div>
	<div class="crtime" WCMAnt:param="widget_recentlyused_list.time">时间：<%=sCrTime%></div>
	<div class="cmds">
		<li class="selectWidget" onclick="selectWidget('<%=nWidgetId%>', '<%=CMyString.filterForJs(sName)%>')"></li>
	</div>
</div>
<%
				}
			}
		}
%>
	</div>
<%
	}else{
%>
	<div id="objectNotFound"><div class="no_object_found" WCMAnt:param="widget_recentlyused_list.obj.not.found">不好意思, 没有找到符合条件的对象!</div></div>
<%
	}
%>
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
%>