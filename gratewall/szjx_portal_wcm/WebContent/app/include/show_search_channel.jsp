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
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.lang.Integer" %>
<%@ page import="com.trs.infra.util.CMyString" %>

<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	Cookie[] cookies = request.getCookies();
	String sSelectedChannelIds = null;
	String[] aSelectedChannelIds = null;
	Channels objs = null;
	//基本参数的获取
	String sSearchFiled = currRequestHelper.getString("SearchField");
	String sSearchValue = currRequestHelper.getString("SearchValue");
	boolean bIsRadio = currRequestHelper.getBoolean("IsRadio", false);
	boolean bShowOneType = currRequestHelper.getBoolean("ShowOneType", false);
	int nCurrChannelId = currRequestHelper.getInt("CurrChannelId", 0);
	String sCurrSelectedChannelIds = currRequestHelper.getString("SelectedChannelIds");
	int nRightIndex = currRequestHelper.getInt("RightIndex", 0);
	String[] aCurrSelectedChannelIds = null;
	Channel oCurrSelChannel = null;
	int nCurrSiteType = currRequestHelper.getInt("CurrSiteType",0);
	//获取当前栏目
	if(nCurrChannelId > 0){
		oCurrSelChannel = Channel.findById(nCurrChannelId);
	}
	if(sCurrSelectedChannelIds != null && oCurrSelChannel == null){
		aCurrSelectedChannelIds = sCurrSelectedChannelIds.split(",");
		if(!aCurrSelectedChannelIds[0].equals(""))
			oCurrSelChannel = Channel.findById(Integer.parseInt(aCurrSelectedChannelIds[0]));
	}
	if(oCurrSelChannel != null){
		WebSite oCurrWebSite = oCurrSelChannel.getSite();
		if(oCurrWebSite != null) nCurrSiteType = oCurrWebSite.getType();
	}
	String sSiteTypes = currRequestHelper.getString("SiteTypes");
	if(sSiteTypes !=null && (sSiteTypes.indexOf("1")>0 || sSiteTypes.indexOf("2")>0) && nCurrSiteType ==0){
		nCurrSiteType = 1;
	}
	//将一些额外的参数初始化到HashHap中
	initExcludeInfo(currRequestHelper);

	if(cookies != null && sSearchFiled == null){
		//从cookie中获取上次选择记录的channelIds
		for(int i = 0; i < cookies.length; i++){
			if(cookies[i].getName().equals("latestSelChnlIds")){
				sSelectedChannelIds = cookies[i].getValue();
				if(sSelectedChannelIds != null)
					aSelectedChannelIds = sSelectedChannelIds.split("/");
				break;
			}
		}
	}else{
		//进行检索
		JSPRequestProcessor processor = new JSPRequestProcessor(request, response); 
		processor.setAppendParameters(new String[] {sSearchFiled, sSearchValue});
		objs = (Channels)processor.excute("wcm61_channel", "query");
		
		//初始化字段名和字段描述的对应关系
		intFieldMap();
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
<title></title>
<script type="text/javascript" src="../../app/js/runtime/myext-debug.js"></script>
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
	height:87%;
	overflow:auto;
	font-size:12px;
	margin-top:10px;
	overflow:auto;
	scrollbar-face-color: #e6e6e6;
	scrollbar-highlight-color: #fff;
	scrollbar-shadow-color: #eeeeee; 
	scrollbar-3dlight-color: #eeeeee; 
	scrollbar-arrow-color: #000; 
	scrollbar-track-color: #fff; 
	scrollbar-darkshadow-color: #fff;
}
.search{
	height:10%;
}
.pathsp{
	float:left;
	width:100%;
	display:inline-block;
	white-space:nowrap;
}
.querybox{width:280px;}
.querybox .qbr{zoom:1;padding-right:3px;}
.querybox .qbc{width:100%;height:26px;overflow:hidden;padding-right:10px;}
.querybox .elebox{padding-left:3px;padding-top:2px;text-align:right;}
.querybox input{color:gray;font-size:12px;width: 125px;}
.querybox select{font-size: 12px;width:75px;margin:0px 4px;}
.querybox .search{width:50px;}
.querybox .search div{cursor:pointer;height:18px;margin-top:3px;overflow:hidden;background:url(../images/list/list2.png) 0px -879px no-repeat;}

</style>
<script language="javascript">
<!--
var sSearchFieldName = "<%=(sSearchFiled == null ? "" : CMyString.filterForJs(sSearchFiled))%>";
var sSearchFieldValue = "<%=(sSearchValue == null ? "" : CMyString.filterForJs(sSearchValue))%>";
function getCheckValues(){
	var arValues = [];
	if(document.getElementById('selContainer')){
		var checkEls = document.getElementsByTagName('input');
		for(var i=0; i < checkEls.length; i++){
			if(checkEls[i].checked)
				arValues.push(checkEls[i].value);
		}
	}
	return arValues;
}

function getCheckNames(){
	var arNames = [];
	if(document.getElementById('selContainer')){
		var checkEls = document.getElementsByTagName('input');
		for(var i=0; i < checkEls.length; i++){
			if(checkEls[i].checked)
				arNames.push(checkEls[i].getAttribute('channelName'));
		}
	}
	return arNames;
}

function getUrlJson(){
	var json = {};
	var sUrl = location.href;
	if(sUrl.indexOf('?') > 0){
		sUrl = sUrl.substring(sUrl.indexOf('?') + 1, sUrl.length);
		json = sUrl.parseQuery();
	}
	return json;
}

function pageInfoDoSearch(json){
	var sURL = null;
	if(location.href.indexOf('?') > 0){
		sURL = location.href.substring(0,location.href.indexOf('?')+1) + $toQueryStr(json);
	}else{
		sURL = location.href + '?' + $toQueryStr(json);
	}
	location.href = sURL;
}

wcm.ListQuery.register({
	container : 'search', 
	appendQueryAll : false,
	autoLoad : true,
	maxStrLen : 50,
	items : [
		{name : 'chnldesc', desc : '<%=LocaleServer.getString("show_search_channel.label.chnldesc", "显示名称")%>', type : 'string'},
		{name : 'chnlname', desc : '<%=LocaleServer.getString("show_search_channel.label.chnlname", "唯一标识")%>', type : 'string'},
		{name : 'cruser', desc : '<%=LocaleServer.getString("show_search_channel.label.cruser", "创建者")%>', type : 'string'},
		{name : 'id', desc : '<%=LocaleServer.getString("show_search_channel.label.chnnlid", "栏目ID")%>', type : 'int'}
	],
	callback : function(params){
		var json = getUrlJson();
		for(key in params){
			json['SearchField'] = key;
			json['SearchValue'] = params[key];
		}
		pageInfoDoSearch(json);     
	}
});
Object.extend(wcm.ListQuery.Checker,{
	"int" : function(sValue){
		if(!/^\d{1,}$/.test(sValue)){
			return <%=LocaleServer.getString("show_search_channel.querychecker.info", "'请输入正整数:1~2147483647'")%>;
		}
		return false;
	}
});
 
Event.observe(window, 'load', function(){
	setTimeout(function(){
		if($('queryValue')){
			$('queryValue').value = sSearchFieldValue;
		}
		if($('queryType')){
			var selOptions = $('queryType').options;
			for(var i = 0; i < selOptions.length; i++){
				if(selOptions[i].value == sSearchFieldName){
					$('queryType').selectedIndex = i;
					break;
				}
			}
		}
	},0)
});
//-->
</script>
</head>
<body>
<DIV class="search" id=search>
</DIV>
<div class="dvContainer" id="selContainer">
<%
	if(aSelectedChannelIds != null){
%>
<span style="font-size:13px;" ><%=LocaleServer.getString("show_search_channel.label.latestusechannel", "最近使用的栏目：")%></span><br>
<%
		for(int k = aSelectedChannelIds.length -1 ; k >=0; k--){
			int nChannelId = 0;
			if(aSelectedChannelIds[k].equals(""))continue;
			nChannelId = Integer.parseInt(aSelectedChannelIds[k]);
			Channel oChannel = Channel.findById(nChannelId);
			if(oChannel == null)continue;
			if(oChannel.getStatus() < 0)continue;
			//排除掉不符合条件的栏目
			if(bHasNeedExclude(oChannel)) continue;
			if(!AuthServer.hasRight(loginUser, oChannel, nRightIndex))
				continue;
			String sChannelPath = oChannel.getDesc();
			Channel oParentChannel = oChannel.getParent();
			while(oParentChannel != null){
				sChannelPath = oParentChannel.getDesc() + '\\' + sChannelPath;
				oParentChannel = oParentChannel.getParent();
			}
			WebSite currSite = oChannel.getSite();
			if(currSite != null){
				//if(bShowOneType && nCurrSiteType != currSite.getType())continue;
				//修正在文字库和资源库下进行搜索的问题，在文字库或者资源库进行搜索操作时，既显示文字库下的结果又显示资源库下的结果
				if(nCurrSiteType != currSite.getType()){
					if(bShowOneType) continue; //若只显示当前库下的栏目，则continue
					if(nCurrSiteType == 0 && currSite.getType() != 4) continue;
					if(nCurrSiteType == 4 && currSite.getType() != 0) continue;
				}
				sChannelPath = currSite.getDesc() + '\\' + sChannelPath;
				String sSiteTypeDesc = findSiteTypeDesc(currSite.getType());
				sChannelPath = sSiteTypeDesc + "\\" + sChannelPath;
			}
			
%>
<span class="pathsp"><input type="<%=bIsRadio ? "radio" : "checkbox"%>" <%=(sSelectedChannelIds.indexOf(""+nChannelId) != -1)?"checked":""%> value="<%=nChannelId%>" channelName="<%=oChannel.getDesc()%>" name=selTreeNav/><span title="<%=sChannelPath%>"><%=sChannelPath%></span></span></br>
<%
		}
	}else if(objs != null){
%>
<span style="font-size:13px;" id="tipinfo"></span><br>
<%
		int k = 0;
		for(int m = 0; m < objs.size(); m++){
			Channel oChannel2 = (Channel)objs.getAt(m);
			if(oChannel2 == null)continue;
			if(oChannel2.getStatus() < 0)continue;
			if(bHasNeedExclude(oChannel2)) continue;
			if(!AuthServer.hasRight(loginUser, oChannel2, nRightIndex))
				continue;
			String sChannelPath2 = oChannel2.getDesc();
			Channel oParentChannel2 = oChannel2.getParent();
			while(oParentChannel2 != null){
				sChannelPath2 = oParentChannel2.getDesc() + '\\' + sChannelPath2;
				oParentChannel2 = oParentChannel2.getParent();
			}
			WebSite currSite2 = oChannel2.getSite();
			if(currSite2 != null){
				//if(bShowOneType && nCurrSiteType != currSite2.getType())continue;
				//修正在文字库和资源库下进行搜索的问题，在文字库或者资源库进行搜索操作时，既显示文字库下的结果又显示资源库下的结果
				if(nCurrSiteType != currSite2.getType()){
					if(bShowOneType) continue; //若只显示当前库下的栏目，则continue
					if(nCurrSiteType == 0 && currSite2.getType() != 4) continue;
					if(nCurrSiteType == 4 && currSite2.getType() != 0) continue;
				}
				sChannelPath2 = currSite2.getDesc() + '\\' + sChannelPath2;
				String sSiteTypeDesc2 = findSiteTypeDesc(currSite2.getType());
				sChannelPath2 = sSiteTypeDesc2 + "\\" + sChannelPath2;
			}
			k++;
%>
<span class="pathsp"><input type="<%=bIsRadio ? "radio" : "checkbox"%>" value="<%=oChannel2.getId()%>" channelName="<%=CMyString.filterForHTMLValue(oChannel2.getDesc())%>" name=selTreeNav/><span title="<%=CMyString.filterForHTMLValue(sChannelPath2)%>"><%=CMyString.transDisplay(sChannelPath2)%></span></span></br>

<%
  }
%>
<script language="javascript">
<!--
	<%
		if(!"".equals(sSearchValue)){
	%>
	document.getElementById('tipinfo').innerHTML = '<%=CMyString.filterForJs(CMyString.format(LocaleServer.getString("show_search_channel.searchchannel", "搜索[{0}]为[{1}]的栏目获得[{2}]个:"),
                new String[] {findDescByFieldName(sSearchFiled), sSearchValue, String.valueOf(k)}))%>';
	<%
		}else{	
	%>
		document.getElementById('tipinfo').innerHTML = '<%=CMyString.filterForJs(CMyString.format(LocaleServer.getString("show_search_channel.searchchanneltatal", "共检索出[{0}]个可用的栏目:"),
                new String[] {String.valueOf(k)}))%>';
		
	<%
		}	
	%>
//-->
</script>
<%
}
%>
</div>
</body>
</html>
<%! 
	private HashMap hExcludeInfoMap = new HashMap();
	private HashMap hFieldName = new HashMap();
	private void intFieldMap(){
		hFieldName.put("chnldesc", LocaleServer.getString("show_search_channel.label.chnldesc", "显示名称"));
		hFieldName.put("chnlname", LocaleServer.getString("show_search_channel.label.chnlname", "唯一标识"));
		hFieldName.put("cruser", LocaleServer.getString("show_search_channel.label.cruser", "创建者"));
		hFieldName.put("id", LocaleServer.getString("show_search_channel.label.chnnlid", "栏目ID"));
	}
	private void initExcludeInfo(RequestHelper _RequestHelper)throws WCMException{
		hExcludeInfoMap.put("ExcludeVirtual", String.valueOf(_RequestHelper.getBoolean("ExcludeVirtual", true)));
		hExcludeInfoMap.put("ExcludeTop", String.valueOf(_RequestHelper.getBoolean("ExcludeTop", true)));
		hExcludeInfoMap.put("ExcludeLink", String.valueOf(_RequestHelper.getBoolean("ExcludeLink", true)));
		hExcludeInfoMap.put("ExcludeInfoView", String.valueOf(_RequestHelper.getBoolean("ExcludeInfoView", true)));
		hExcludeInfoMap.put("ExcludeSelf", String.valueOf(_RequestHelper.getBoolean("ExcludeSelf", false)));
		hExcludeInfoMap.put("ExcludeOnlySearch", String.valueOf(_RequestHelper.getBoolean("ExcludeOnlySearch", true)));
		hExcludeInfoMap.put("CurrChannelId", String.valueOf(_RequestHelper.getInt("CurrChannelId", 0)));
	}
	private boolean bHasNeedExclude(Channel _obj){
		if(hExcludeInfoMap.get("ExcludeVirtual")!= null && hExcludeInfoMap.get("ExcludeVirtual").equals("true") && _obj.isVirtual())return true;
		if(hExcludeInfoMap.get("ExcludeTop")!= null && hExcludeInfoMap.get("ExcludeTop").equals("true") && (_obj.getType() == Channel.TYPE_TOP_NEWS || _obj.getType() == Channel.TYPE_TOP_PICS))return true;
		if(hExcludeInfoMap.get("ExcludeLink")!= null && hExcludeInfoMap.get("ExcludeLink").equals("true") && _obj.isLink()) return true;
		if(hExcludeInfoMap.get("ExcludeInfoView")!= null && hExcludeInfoMap.get("ExcludeInfoView").equals("true") && _obj.getType() == 13) return true;
		if(hExcludeInfoMap.get("ExcludeSelf")!= null && hExcludeInfoMap.get("ExcludeSelf").equals("true") && hExcludeInfoMap.get("CurrChannelId")!= null && _obj.getId() == Integer.parseInt((String)(hExcludeInfoMap.get("CurrChannelId")))) return true;
		if(hExcludeInfoMap.get("ExcludeOnlySearch")!= null && hExcludeInfoMap.get("ExcludeOnlySearch").equals("true") && _obj.isOnlySearch()) return true;
		return false;
	}
	public String findSiteTypeDesc(int _nSiteType) throws Throwable {
		switch (_nSiteType) {
			case 0:
				return LocaleServer.getString("websiteroot_findbyid.label.wordsitetype", "文字库");
			case 1:
				return LocaleServer.getString("websiteroot_findbyid.label.imgsitetype", "图片库");
			case 2:
				return LocaleServer.getString("websiteroot_findbyid.label.videositetype", "视频库");
			case 4:
				return LocaleServer.getString("websiteroot_findbyid.label.resource", "资源库");
			case 100:
				return LocaleServer.getString("websiteroot_findbyid.label.xiezuoservice", "协作服务");
			default:
				return LocaleServer.getString("websiteroot_findbyid.label.wordsitetype", "文字库");
		}
	}
	public String findDescByFieldName(String sFieldName){
		return  (String)hFieldName.get(sFieldName);
	}
%>