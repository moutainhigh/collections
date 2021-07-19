<%
/** Title:			site_select.jsp
 *  Description:
 *		WCM5.2 站点选择页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wenyh
 *  Created:		2006-04-30 13:34
 *  Vesion:			1.0
 *  Last EditTime:	2006-04-30 / 2006-04-30
 *	Update Logs:
 *		
 *
 *  Parameters:
 *		see site_select.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.metadata.definition.MetaView"%>
<%@ page import="com.trs.components.metadata.center.IMetaViewEmployerMgr"%>
<%@ page import="com.trs.DreamFactory"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer"%>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>

<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	int nMetaViewId = currRequestHelper.getInt("MetaViewId",0);
	if(nMetaViewId == 0){
		throw new WCMException(LocaleServer.getString("metaview_channel_list.jsp.label.runtimeexception", "参数MetaViewId的值必须大于0！"));
	}
	MetaView metaview = MetaView.findById(nMetaViewId);
	if(metaview == null){
		throw new WCMException(CMyString.format(LocaleServer.getString("metaview_channel_list.jsp.notfound", "无法找到ID为【{0}】的视图！"), new int[]{nMetaViewId}));
	}
	IMetaViewEmployerMgr oMetaViewEmployerMgr = (IMetaViewEmployerMgr) DreamFactory.createObjectById("IMetaViewEmployerMgr");
	Channels channels = oMetaViewEmployerMgr.getEmployers(metaview, null);
	//权限的过滤
	int nRightIndex = WCMRightTypes.DOC_OUTLINE;
	int nFromBackSelect = currRequestHelper.getInt("FromBackSelect",0);
	if(nFromBackSelect == 1){
		nRightIndex = WCMRightTypes.DOC_EDIT;
	}
	for(int j=channels.size()-1; j>=0; j--){
		Channel chl = (Channel)channels.getAt(j);
		boolean bOutlineRight = AuthServer.hasRight(loginUser, chl, nRightIndex);
		if(!bOutlineRight){
			channels.remove(chl, false);
		}
	}
	if(channels.size() == 0){
%>
		<script language="javascript">
			alert("相关视图没有被任何栏目所使用或者无权查看相关的文档列表！");
		</script>
<%
		return;
	}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
<title></title>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/locale/cn.js" WCMAnt:locale="../../app/js/locale/$locale$.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<!--wcm-dialog start-->
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<script type="text/javascript" src="../../app/js/data/locale/system.js"></script>
<script src="../../app/js/locale/cn.js" WCMAnt:locale="../../app/js/locale/$locale$.js"></script>
<script type="text/javascript" src="../../app/js/source/wcmlib/ListWidget.js"></script>
<style type="text/css">

html,body{
	margin-right:0px;
	margin-bottom:0px;
	margin-top:2px;
	overflow:hidden;
}
.dvContainer{
	width:100%;
	height:320px;
	overflow:auto;
	font-size:14px;
	color: #000000;
	margin-top:10px;
	scrollbar-face-color: #e6e6e6;
	scrollbar-highlight-color: #fff;
	scrollbar-shadow-color: #eeeeee; 
	scrollbar-3dlight-color: #eeeeee; 
	scrollbar-arrow-color: #000; 
	scrollbar-track-color: #fff; 
	scrollbar-darkshadow-color: #fff;
}
.dv_selected{
	font-family: "宋体";
	color: #F16000;
	font-weight:bold;
}
.box{
	height:25px;
	position:relative;
    text-align:left;
	cursor:pointer;
	white-space:nowrap;
}
.box span{
	padding-left:20px;
}
.channelDv{
	height:20px;
}
img{
	position:absolute;
	vertical-align:middle;
}
.box a:link{
	color: black;
}
.dv_selected a:link{
	color: #F16000;
	text-decoration: underline;
}

</style>
</head>
<body>
<div class="dvContainer" id="channelContainer">
<%
	for(int i = 0;i< channels.size(); i++){
		Channel channel = (Channel)channels.getAt(i);
		int nChannelId = channel.getId();
		String channelDesc = channel.getDesc();
		int nChannelType = channel.getType();
		Channel oParentChannel = channel.getParent();
		String sChannelPath = channelDesc;
		while(oParentChannel != null){
			sChannelPath = oParentChannel.getDesc() + '\\' + sChannelPath;
			oParentChannel = oParentChannel.getParent();
		}
		WebSite currSite = channel.getSite();
		if(currSite != null){
			sChannelPath = currSite.getDesc() + '\\' + sChannelPath;
		}


%>
<div class="box" channelId="<%=nChannelId%>" channelType="<%=nChannelType%>">
	<div clsss="channelDv">
		<img src="../../app/images/icon/icon_channel_normal.gif" border=0 alt="" />
		<span>
			<a href="#" onclick="return false;" class="channellink" title="<%=CMyString.filterForHTMLValue(sChannelPath)%>"><%=CMyString.transDisplay(channelDesc)%></a>
		</span>
	</div>
</div>
<%
	}
%>
</div>
<script language="javascript">

<!--
Event.observe(window,'load',function(){
	var docAEls = document.getElementsByClassName('channellink');
	if(docAEls.length == 0){
		return;
	}
	var firstEl = docAEls[0];
	if(firstEl.dispatchEvent){
		var evt = document.createEvent('Event'); 
		evt.initEvent('click',true,true); 
		firstEl.dispatchEvent(evt);   
	}else if(firstEl.fireEvent){
		firstEl.click();
	}
})
Event.observe('channelContainer','click',function(eve){
	eve = window.event || eve;
	var srcEl = Event.element(eve);
	if(!Element.hasClassName(srcEl,'channellink')){
		return;
	}
	var parentDv = findItem(srcEl,'box');
	changeSelectedClass(parentDv);
	var channelId = parentDv.getAttribute('channelId');
	var channelType = parentDv.getAttribute('channelType');
	var contentSrc = '../document/metadata_relation_select.jsp?ChannelId=' + channelId + '&SiteType=4&IsVirtual=false&ChannelType='+channelType;
	if(parent.getMainUrl){
		contentSrc = parent.getMainUrl() + '?ChannelId=' + channelId + '&SiteType=4&IsVirtual=false&ChannelType='+channelType;
	}
	parent.document.getElementById('channel_doc_list').src = contentSrc;
});
var M_LastClick_Dv = null;
function changeSelectedClass(_eCurrObj){
	//去掉上次单击的元素样式
	if(M_LastClick_Dv){
		if(Element.hasClassName(M_LastClick_Dv,"dv_selected")){
			Element.removeClassName(M_LastClick_Dv,"dv_selected");
		}
	}
	
	//添加当前元素的样式
	if(!Element.hasClassName(_eCurrObj,"dv_selected")){
		Element.addClassName(_eCurrObj,"dv_selected");
	}
	//记录标记
	M_LastClick_Dv = _eCurrObj;
}
//-->
</script>
</body>
</html>