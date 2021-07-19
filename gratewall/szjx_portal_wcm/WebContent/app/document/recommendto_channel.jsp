<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.*" %>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.trs.components.wcm.content.domain.ChannelMgr" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.presentation.nav.TreeCreator" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>


<%@include file="../include/public_server.jsp"%>

<%
//1,参数获取(要考虑参数之间的覆盖作用和参数优先级)
	int[] pSiteTypes = {0, 1, 2, 4};
	String sSiteTypes = CMyString.showNull(currRequestHelper.getString("SiteTypes"));
	boolean bShowOneType = currRequestHelper.getBoolean("ShowOneType",false);

	//MultiSiteType可由传入的SiteTypes或ShowOneType推断
	boolean defaultValue = !bShowOneType || (sSiteTypes != null && sSiteTypes.indexOf(",") >= 0);
	boolean bMultiSiteType = currRequestHelper.getBoolean("MultiSiteType", defaultValue);
	int nCurrSiteId = currRequestHelper.getInt("CurrSiteId", 0);

	//兼容 MultiSites 的逻辑:仅仅显示单个站点,MultiSiteType为false
	if(!currRequestHelper.getBoolean("MultiSites", false)){
		bMultiSiteType = false;
	}

	//选中指定的节点
	int nCurrChannelId	= currRequestHelper.getInt("CurrChannelId", 0);

	String sSelectedChannelIds = currRequestHelper.getString("SelectedChannelIds");
	int nFristChannelSiteType = 0;
	if(	sSelectedChannelIds != null && sSelectedChannelIds.length()>0){		
		nFristChannelSiteType = getSiteTypeOfChannel(CMyString.splitToInt(
                sSelectedChannelIds, ",")[0]);
	}

	//显示哪些库
	if(bMultiSiteType){
		int[] pSiteTypesTemp = {0,1,2,4};
		pSiteTypes = pSiteTypesTemp;
	}
	else{		
		// 1. 指定了显示的站点类型
		if(sSiteTypes != null && (sSiteTypes=sSiteTypes.trim()).length()>0){
			pSiteTypes = CMyString.splitToInt(sSiteTypes, ",");
		}
		// 2. 根据SelectXXX和CurrXXX确定显示哪些库
		else{					
			//========SelectedXXX 优先级高====================
			// 1. 选中指定的栏目
			if(	sSelectedChannelIds != null && sSelectedChannelIds.length()>0){			
				//pSiteTypes = getSiteTypesOfChannels(sSelectedChannelIds);			
				pSiteTypes = new int[]{nFristChannelSiteType};			
			}	
			//================================================
			//========CurrXXX 优先级最小======================
			// 1. 当前焦点在栏目上
			if(nCurrChannelId>0){		
				pSiteTypes = new int[]{getSiteTypeOfChannel(nCurrChannelId)};
			}
			// 2. 当前焦点在站点上
			else if(nCurrSiteId>0){
				pSiteTypes = new int[]{getSiteTypeOfSite(nCurrSiteId)};
			}
		}
	}

	boolean bIsRadio = currRequestHelper.getBoolean("IsRadio", false); 
//-------------------------------------------------------------------------------------
//	从参数获取过程抽取出pSiteTypes  ,nCurrChannelId ,sSelectedChannelIds ,bIsRadio四个参数传入对象获取过程.
//-------------------------------------------------------------------------------------
	
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE WCMAnt:param="recommendto_channel.jsp.title">主站推荐栏目</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
<link href="../js/source/wcmlib/com.trs.tree/resource/TreeNav.css" rel="stylesheet" type="text/css" />
<link href="../nav_tree/nav_tree.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.ext-ie .TreeView{border:0px;overflow:auto;height:100%;width:96%}
	.ext-gecko .TreeView{border:0px;overflow:auto;height:98%;width:96%}
	.ext-safari .TreeView{border:0px;overflow:auto;height:98%;width:96%}
	.ext-gecko .TreeView div{overflow:visible;}
	.ext-safari .TreeView div{overflow:visible;}
</style>
</HEAD>
<BODY style="margin:0;padding:0;">
<div class="nav_tree" style="width:100%;height:100%;border:0;">
<ul class=TreeView id="FirstTree">

<%	
	//输出推荐树
	//printTree(pSiteTypes, nCurrChannelId, nCurrSiteId, loginUser, currRequestHelper, out);
	writeTreeHTML(pSiteTypes,loginUser,currRequestHelper,out);
%>
</UL>
</div>

<script src="../js/easyversion/lightbase.js"></script>
<script src="../js/easyversion/extrender.js"></script>
<script language="javascript">
	initExtCss();
</script>
<script src="../js/easyversion/elementmore.js"></script>
<script src="../js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/data/locale/nav_tree.js"></script>
<script src="../js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/data/locale/tree.js"></script>
<script src="../js/source/wcmlib/com.trs.tree/TreeNav.js"></script>
<!--<script src="../js/easyversion/ajax.js"></script>-->
<SCRIPT LANGUAGE="JavaScript">
<!--
var TreeNav = com.trs.tree.TreeNav;
//设置树的类型
TreeNav.setType(<%=bIsRadio ? 2 : 1%>);
//设置记录checkbox Value在LI元素上的属性名称
TreeNav.setAttrNameRelatedValue("SValue");
Object.extend(TreeNav,{
/*
*设置该参数后，影响树组件初始化时_arCheckedValues参数(TreeNav.js该文件),所以去掉
*对于是否传入sSelectedChannelIds这个参数，需要通过NotSelect来判断
*--by@sj--2012-05-23
<%
	if(!CMyString.isEmpty(sSelectedChannelIds)){
		out.print("_arCheckedValues : ["+sSelectedChannelIds+"],");
	}
%>
*/
_initChildrenNodes : function(_oParentEl, _oRootEl, _sSelectName){
		var sSelectName = _sSelectName || "selTreeNav";
		var TreeNav = com.trs.tree.TreeNav; 
		var nodes = _oParentEl.getElementsByTagName('DIV');
		var sSelectedChannelIds = '<%=CMyString.filterForJs(CMyString.showNull(currRequestHelper.getString("SelectedChannelIds")))%>';
		/*
		*当传入参数"NotSelect"时，不设置当前栏目为选中栏目
		*--by@sj--2012-05-23
		*/
		if(getParameter("NotSelect") == null || getParameter("NotSelect") == ""){
			TreeNav.setCheckedValues(sSelectedChannelIds.split(","));
		}
		//判断各个节点是否有子节点
		for(var j=0; j<nodes.length; j++){	
			var sClassNamePre = nodes[j].getAttribute("ClassPre");
			if(!sClassNamePre){
				sClassNamePre = "";
			}
			var sClassName = "Page";
			var oTopChildelement = com.trs.tree.TreeNav._getTopChildElement(nodes[j]);
			//因为此处是没有库的树结构,故去掉库作为root的处理逻辑.
			
			if(_oRootEl != null && nodes[j].parentNode == _oRootEl){
				nodes[j].isRoot = true;
				if(oTopChildelement != null){
					sClassName = ((oTopChildelement.style.display=="none") ? "Root":"RootOpened");
					if(oTopChildelement.getElementsByTagName("DIV").length == 0){
						sClassName = "Root";
						Element.hide(oTopChildelement);
					}
				}
			}else if(oTopChildelement != null){
				sClassName = ((oTopChildelement.style.display=="none") ? "Folder":"FolderOpened");
				if(oTopChildelement.getElementsByTagName("DIV").length == 0){
					sClassName = "Folder";
					Element.hide(oTopChildelement);
				}
			}
			Element.addClassName(nodes[j], sClassNamePre + sClassName);
			if(com.trs.tree.TreeNav.nTreeType == com.trs.tree.TreeNav.TYPE_NORM)continue;
			//是否为OnlyNode
			var sOnlyNode = nodes[j].getAttribute("OnlyNode");
			if(sOnlyNode != null && sOnlyNode.toLowerCase()=="true")continue;
			//modify by hdg@2009.11.2 
			//如果有子栏目并且该栏目不是聚合栏目
			if(TreeNav._getTopChildElement(nodes[j])!=null && nodes[j].getAttribute("isCluster")!="1")continue;
			//处理其它类型的树
			var sDisalbedAttr = nodes[j].getAttribute("SDisabled");
			if(sDisalbedAttr != null)sDisalbedAttr = sDisalbedAttr.toLowerCase();
			var bDisabled =  (sDisalbedAttr == "true");
			var sValue = bDisabled ? "0" : com.trs.tree.TreeNav.__getSelectValue( nodes[j] );
			var sHTML = "";
			switch(com.trs.tree.TreeNav.nTreeType){
				case com.trs.tree.TreeNav.TYPE_CHECKBOX:
					sHTML = '<input type=checkbox name=' + sSelectName
							+ TreeNav._getCheckedHTML(sValue)
							+ TreeNav._getDisabledHTML(bDisabled)
							+ ' id="c'+nodes[j].id+'" '//ID的追加是否影响性能??
							+' value="'+sValue+'"/>';
					//this.__createLableElement(nodes[j]);
					break;
				case com.trs.tree.TreeNav.TYPE_RADIO:
					sHTML = '<input type=radio name=' + sSelectName
							+ TreeNav._getCheckedHTML(sValue)
							+ TreeNav._getDisabledHTML(bDisabled)
							+ ' id="c'+nodes[j].id+'" '//ID的追加是否影响性能??
							+' value="'+sValue+'"/>';
					//this.__createLableElement(nodes[j]);
					break;
				default:
					continue;
			}
			

			new Insertion.Top(nodes[j], sHTML);
		}
	},
	onClickNode : function(_event){
		var event = window.event || _event;
		var oSrcElement = Event.element(event);

		var sOnclick = null;
		if((sOnclick = oSrcElement.getAttribute('onclick', 2)) != null
			&& (sOnclick != '')) {
			try{
				eval(sOnclick);
				return false;
			}catch(err){
				//just skip over
			}
		}
		switch(oSrcElement.tagName){
			case "DIV":
				com.trs.tree.TreeNav._onClickFolder(oSrcElement);
				break;
			case "A":
				// modify by hdg @2009-11-3
				//点击文字时不折叠树
				//if(TreeNav._getTopChildElement(oSrcElement.parentNode)!=null){
				//	com.trs.tree.TreeNav._onClickFolder(oSrcElement.parentNode);
				//}else{
					var input = oSrcElement.parentNode.getElementsByTagName('INPUT')[0];
					if(input!=null && !input.disabled)input.checked = !input.checked;
				//}
				return false;
			default:
				break;
		}		
		return true;
	},
	makeGetChildrenHTMLAction:function(){
		return false;
	}
});

function getCheckValues(){
	return TreeNav.getCheckValues('FirstTree');
}
function getCheckNames(){
	return TreeNav["SelectedNames"];
}

</SCRIPT>

<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script language="javascript">
	// WCM-1763  提供一个系统工具，可以对WCM导航树中的某些节点设置为显示或不显示
	Event.observe(window, 'load', function(){
		var CustomizeInfo = window.top.m_CustomizeInfo;	//获得父页面关于是否显示导航树的相关值		
		if(!CustomizeInfo){
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.JspRequest("../individuation/systemindividuation_get.jsp",{},false, 
				function(transport,json){				
					if(!json){
						return;
					}
					var adiv=document.getElementsByTagName("div");
					for(var i=0;i<adiv.length;i++){
						if(adiv[i].id=="r_0" && json["TEXTLIB"]=="false"){
							document.getElementById("r_0").style.display = "none"; 
						}else if(adiv[i].id=="r_1" && json["PICTURELIB"]=="false"){
							document.getElementById("r_1").style.display = "none";
						}else if(adiv[i].id=="r_2" && json["VIDEOLIB"]=="false"){
							document.getElementById("r_2").style.display = "none";
						}else if(adiv[i].id=="r_4" && json["RESOURCESLIB"]=="false"){
							document.getElementById("r_4").style.display = "none";
						}
					}
				}
			);
		}else{
			var adiv=document.getElementsByTagName("div");
			if(!CustomizeInfo["textLib"]){
				return;
			}
			for(var i=0;i<adiv.length;i++){
				if(adiv[i].id=="r_0" && CustomizeInfo["textLib"].paramValue=="false"){
					document.getElementById("r_0").style.display = "none";
				}else if(adiv[i].id=="r_1" && CustomizeInfo["pictureLib"].paramValue=="false"){
					document.getElementById("r_1").style.display = "none";
				}else if(adiv[i].id=="r_2" && CustomizeInfo["videoLib"].paramValue=="false"){
					document.getElementById("r_2").style.display = "none";
				}else if(adiv[i].id=="r_4" && CustomizeInfo["resourcesLib"].paramValue=="false"){
					document.getElementById("r_4").style.display = "none";
				}
			}
		}
	});


</script>
</BODY>
</HTML>

<%!
	// 输出站点树结构
	private void writeSitesTree(int _nSiteType, User _loginUser, RequestHelper _requestHelper, JspWriter _out) throws Exception {
		//复用一般选择数的一些参数,获取所有可见的栏目id
		String sClusterChannelIds = getClusterChannelIds();
		String sAllChannelIds = getAllChannelIds(sClusterChannelIds);
		//获取站点(过滤)
		WCMFilter filter = null;
		filter = new WCMFilter("","","","");
		
		StringBuffer sWhereBuf = new StringBuffer("siteid in(select siteid from wcmchannel where iscluster=1 and status>=0) and status>=0 and SiteType =?");
		filter.setWhere(sWhereBuf.toString());
		filter.addSearchValues(_nSiteType);
		filter.setOrder("siteorder desc");
		// 获取站点类型下的所有站点
		WebSites oWebSites = WebSites.openWCMObjs(_loginUser, filter);
		for (int i = 0; i < oWebSites.size(); i++) {
            WebSite oWebSite =(WebSite)oWebSites.getAt(i);
            _out.println("<div "+getExtraAttrOfA(oWebSite)
                    + "id=\"s_" + oWebSite.getId() + "\" classPre=\"site\""
			        + "OnlyNode=\"true\">");
            _out.println("<a href=#>" + CMyString.transDisplay(oWebSite.getDesc()) + "</a>");
            _out.println("</div>");

            _out.println("<ul siteid=\"" + oWebSite.getId() + "\">");
			// 输出栏目树结构
			writeChannelTree(oWebSite,sAllChannelIds, _requestHelper, _out);
			_out.println("</ul>");
        }
		//wenyh@2011-11-29 不可见的已选栏目保留
		String sSelectedChannelIds = _requestHelper.getString("SelectedChannelIds");
		int nCurrChannelId = _requestHelper.getInt("CurrChannelId", 0);
		if(CMyString.isEmpty(sSelectedChannelIds)){
			return;
		}
		java.util.List listed = new java.util.ArrayList(32);
		listed.addAll(java.util.Arrays.asList(sClusterChannelIds.split(",")));
		listed.addAll(java.util.Arrays.asList(sAllChannelIds.split(",")));
		int[] channelIds = CMyString.splitToInt(sSelectedChannelIds,",");
		StringBuffer buff = new StringBuffer(128);
		buff.append("<div id='selectedChnlsWithoutPriv' style='display:none' OnlyNode='true'>");
		for (int i = 0, len = channelIds.length; i < len; i++) {
			int id = channelIds[i];
			//修复将非聚合文档复制到聚合文档会同时在当前栏目复制该文档的bug//added by sj on 2012-05-24
			if(id == nCurrChannelId) {
				continue;
			}//end
			if(listed.contains(String.valueOf(id))) continue;
			Channel channel = Channel.findById(id);
			if (channel == null || channel.isDeleted()) {
				continue;
			}
			buff.append("<span cid='");
			buff.append(id);
			buff.append("'>");
			buff.append(CMyString.transDisplay(channel.getDispDesc()));
			buff.append("</span>");
		}
		buff.append("</div>");
		_out.println(buff.toString());	
}

	private int getSiteTypeOfChannel(int _nChannelId) throws Exception {
        final String SQL_GET_SITETYPE = "select SiteType from WCMWebSite"
                + " where exists("
                + "select WCMChannel.ChannelId from WCMChannel"
                + "  where WCMChannel.ChannelId=?"
                + " and WCMChannel.SiteId=WCMWebSite.SiteId"//
                + ")";
        int nFirstLoadSiteType = DBManager.getDBManager().sqlExecuteIntQuery(
                SQL_GET_SITETYPE, new int[] { _nChannelId });

        return nFirstLoadSiteType;
    }

	private String getExtraAttrOfA(BaseChannel _host) {
        String sTitle = LocaleServer.getString("tree.label.bianhao", "编号:")
				+ _host.getId()
				+ " \n"//
				+ LocaleServer.getString("tree.label.biaoshi", "唯一标识:")
				+ CMyString.filterForHTMLValue(_host.getName())
				+ " \n"//
				+ LocaleServer.getString("tree.label.cruser", "创建者:")
				+ CMyString.filterForHTMLValue(_host.getCrUserName())
				+ " \n"//
				+ LocaleServer.getString("tree.label.crtime", "创建时间:")
				+ _host.getCrTime();
		return " title=\"" + sTitle + "\" ";
    }

	private String getClassPre(BaseChannel _siteOrChannel) {
        if (_siteOrChannel.isSite())
            return "site";
        Channel channel = (Channel) _siteOrChannel;
        if (channel.isNormType())
            return "channel";
        return "channel" + channel.getType();
    }
	private int getSiteTypeOfSite(int _nSiteId) throws Exception {
        final String SQL_GET_SITETYPE = "select SiteType from WCMWebSite"
                + " where SiteId=?";
        return DBManager.getDBManager().sqlExecuteIntQuery(SQL_GET_SITETYPE,
                new int[] { _nSiteId });
    }

	//----------------------------------------------
	// 输出从站点类型开始的树结构
	public void writeTreeHTML(int[] _pSiteTypes,User _loginUser,RequestHelper _requestHelper, JspWriter _out)
            throws Exception {
        int[] pSiteTypes = { 0, 1, 2, 4 };
        if (_pSiteTypes != null && _pSiteTypes.length > 0) {
            pSiteTypes = _pSiteTypes;
        }
  
		for (int i = 0; i < pSiteTypes.length; i++) {
            int nSiteType = pSiteTypes[i];
            _out.println("<div title=\"" + getDesc(nSiteType)
                    + " \" id=\"r_" + nSiteType + "\" classPre=\"SiteType"
                    + nSiteType + "\" SiteType=\"" + nSiteType + "\" OnlyNode=\"true\">");
            _out.println("<a href=#>" + getDesc(nSiteType) + "</a>");
            _out.println("</div>");

            _out.println("<ul SiteType=\"" + nSiteType + "\">");
			writeSitesTree(nSiteType,_loginUser, _requestHelper, _out);
			_out.println("</ul>");
			
        }
    }
	//获取类型描述
	private String getDesc(int _siteType){
		String desc = LocaleServer.getString("nav_tree.label.textWarehouse", "文字库");
		switch(_siteType){
			case 1:
				desc = LocaleServer.getString("nav_tree.label.picWarehouse", "图片库");
				break;
			case 2:
				desc = LocaleServer.getString("nav_tree.label.videoWarehouse", "视频库");
				break;
			case 4:
				desc = LocaleServer.getString("nav_tree.label.resourceWarehouse", "资源库");
				break;
		}
		return desc;
	}

	//获取所有要显示的栏目
	private Channels getAllChannels(Channels _currChannels)throws Exception{
		Channels allChannels = _currChannels;
		Channel currChannel = null;
		for(int i=0;i<_currChannels.size() ; i++){
			//如果 存在父栏目
			currChannel = (Channel) _currChannels.getAt(i);
			if(currChannel.getParentId()!=0){
				allChannels.addWith(getAllSupChannels(currChannel));
			}
		}
		return allChannels;
	}
	//获取所有聚合栏目id，以","分隔
	private String getClusterChannelIds()throws Exception{
		WCMFilter filter = new WCMFilter("WCMChannel","","ChnlOrder desc");
		
		StringBuffer sWhereBuf = new StringBuffer();
		sWhereBuf.append("IsCluster=1 and status>=0");
		filter.setWhere(sWhereBuf.toString());
		Channels currRecommendChannels = Channels.openWCMObjs(null, filter);
		String sChannelIds = currRecommendChannels.getIdListAsString();
		if(sChannelIds == null){
			sChannelIds = "";
		}
		return sChannelIds;
	}
	// 获取所有要显示的栏目,以","分隔
	private String getAllChannelIds(String _clusterChannelIds)throws Exception{
		Channels allChannels = Channels.findByIds(null,_clusterChannelIds);
		Channel currChannel = null;
		for(int i=0;i<allChannels.size() ; i++){
			//如果 存在父栏目
			currChannel = (Channel) allChannels.getAt(i);
			if(currChannel.getParentId()!=0){
				allChannels.addWith(getAllSupChannels(currChannel));
			}
		}
		String sChannelIds = allChannels.getIdListAsString();
		if(sChannelIds == null){
			sChannelIds = "";
		}
		return sChannelIds;
	}
	// 获取栏目的所有父栏目
	private Channels getAllSupChannels(Channel _currChannel)throws Exception{
		Channels supChannels = Channels.createNewInstance(null);
		Channel supChannel = null;
		if(_currChannel.getParentId()!=0){
			supChannel = _currChannel.getParent();
			supChannels.addElement(supChannel);
			supChannels.addWith(getAllSupChannels(supChannel));
		}
		return supChannels;
	}
	// 输出栏目树结构
	private void writeChannelTree(BaseChannel _oSiteOrChannel,String _allChannelIds, RequestHelper _requestHelper,JspWriter  _out)throws Exception{
		Channels childrenChnls = getChildren(_oSiteOrChannel,"ChnlOrder desc");
		Channel childChnl = null;
		for(int i=0;i<childrenChnls.size();i++){
			childChnl = (Channel)childrenChnls.getAt(i);
			int nChildChnlId = childChnl.getId();
			//如果这个栏目在要显示的栏目中
			if((","+_allChannelIds+",").indexOf(","+nChildChnlId+",")>=0){
				// 是否可选中
				String sDisabled="";
				if((_requestHelper.getInt("CurrChannelId",0)==nChildChnlId && _requestHelper.getInt("ExcludeSelf",0)==1) || (_requestHelper.getBoolean("ExcludeOnlySearch", false) && childChnl.isOnlySearch()) 
				|| childChnl.getType()==Channel.TYPE_LINK 
				|| (childChnl.getType()==Channel.TYPE_INFOVIEW && _requestHelper.getInt("ExcludeInfoView",0)==1)
				|| (_requestHelper.getInt("ExcludeVirtual",0)==1 && childChnl.isVirtual())
				)
					sDisabled = "sDisabled=true";
				String sClusterChannelRightFilter = ConfigServer.getServer().getSysConfigValue(
                "CLUSTER_CHANNEL_RIGHT_FILTER", "false");
				if(sClusterChannelRightFilter != null && "true".equals(sClusterChannelRightFilter) && !AuthServer.hasRight(ContextHelper.getLoginUser(), childChnl, _requestHelper.getInt("RightIndex", 0)))
					sDisabled = "sDisabled=true";					
				if(childChnl.isCluster()){
					//如果没有子栏目要显示
					if(!hasChildChnlVisable(childChnl,_allChannelIds)){
						 _out.println("<div " + getExtraAttrOfA(childChnl)
								+ " SValue='" + nChildChnlId + "' classPre='"+ getClassPre(childChnl) +"' isCluster='1'" +sDisabled + "><A href='#' class=''>" + CMyString.transDisplay(childChnl.getDesc())  + "</A></div>");
					}else{
						 _out.println("<div " + getExtraAttrOfA(childChnl)
								+ " SValue='" + nChildChnlId + "' classPre='"+ getClassPre(childChnl) +"' isCluster='1'" +sDisabled +"><A href='#' class=''>" + CMyString.transDisplay(childChnl.getDesc())  + "</A></div>");
						_out.println("<ul channelid=\"" + nChildChnlId + "\">");
						writeChannelTree(childChnl,_allChannelIds, _requestHelper, _out);
						_out.println("</ul>");
					}
				}else{
						_out.println("<div " + getExtraAttrOfA(childChnl)
								+ " SValue='" + nChildChnlId + "' classPre='"+ getClassPre(childChnl) +"' isCluster='0'" +sDisabled + "><A href='#' class=''>" + CMyString.transDisplay(childChnl.getDesc())  + "</A></div>");
						_out.println("<ul channelid=\"" + nChildChnlId + "\">");
						writeChannelTree(childChnl,_allChannelIds, _requestHelper, _out);
						_out.println("</ul>");
				}
			}
		}
	}
	// 是否有子栏目需要显示
	private boolean hasChildChnlVisable(Channel _supChannel,String _allChannelIds)throws Exception{
		List childrenChannels = _supChannel.getAllChildren(null);
		if(childrenChannels==null || childrenChannels.size()==0)
			return false;
		for(int i=0;i<childrenChannels.size();i++){
			Channel childChannel = (Channel)(childrenChannels.get(i));
			int childChannelId =childChannel.getId();
			if((","+_allChannelIds+",").indexOf(","+childChannelId+",")>=0)
				return true;
		}
		return false;
	}
	private Channels getChildren(BaseChannel _oSiteOrChannel,String _orderBy)throws WCMException{
		WCMFilter aFilter = null;
        if (_oSiteOrChannel.isSite()) {
            aFilter = new WCMFilter("", "ParentId=0 and SiteId="+_oSiteOrChannel.getId(), _orderBy);
        } else {
            aFilter = new WCMFilter("", "ParentId=" + _oSiteOrChannel.getId(), _orderBy);
        }
        return Channels.openWCMObjs(ContextHelper.getLoginUser(), aFilter);
	}

%>