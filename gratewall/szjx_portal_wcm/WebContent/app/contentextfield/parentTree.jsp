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
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>

<%@include file="../include/public_server.jsp"%>

<%
	//1,参数获取
	int nCurrSiteId = currRequestHelper.getInt("SiteId", 0);
	int nCurrChannelId	= currRequestHelper.getInt("ChannelId", 0);
	boolean bIsRadio = currRequestHelper.getBoolean("IsRadio", false); 
	int nSiteType = 0;
	boolean bInChannelsHost = true;
	Channel currChannel = null;
	WebSite currWebSite = null;
	if(nCurrChannelId > 0){
		currChannel = Channel.findById(nCurrChannelId);
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("parentTree.jsp.noidchnl", "没有找到ID为{0}的栏目"), new int[]{nCurrChannelId}));
		}
		nSiteType = currChannel.getSite().getType();
	}else{
		currWebSite = WebSite.findById(nCurrSiteId);
		if(currWebSite == null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("parentTree.jsp.noidsite", "没有找到ID为{0}的站点"), new int[]{nCurrSiteId}));
		}
		bInChannelsHost = false;
		nSiteType = currWebSite.getType();
	}
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
<link href="../js/source/wcmlib/com.trs.tree/resource/TreeNav.css" rel="stylesheet" type="text/css" />
<link href="../nav_tree/nav_tree.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.ext-ie .TreeView{border:0px;overflow:auto;height:100%;width:100%}
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
	//输出父节点树
	writeTreeHTML(bInChannelsHost,currChannel,nSiteType,loginUser,currRequestHelper,out);
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
<script src="../js/easyversion/ajax.js"></script>
<SCRIPT LANGUAGE="JavaScript">
Ext.ns('wcm.TreeNode');
wcm.TreeNode = function(_element){
	var context = (_element)?this.buildContext(_element):null;
	this.objType = 'extfieldNode';//WCMConstants.OBJ_TYPE_TREENODE;
	wcm.TreeNode.superclass.constructor.call(this, null, context);
	this.addEvents(['click', 'afterclick', 'contextmenu']);
};
CMSObj.register(WCMConstants.OBJ_TYPE_TREENODE, 'wcm.TreeNode');
Ext.extend(wcm.TreeNode, wcm.CMSObj, {
	buildContext : function(_element){
		return null;
	}
});
(function(){
	var _tmpTreeNode = new wcm.TreeNode(null);
	wcm.TreeNode.fly = function(_element){
		var oTreeNode = {};
		Ext.apply(oTreeNode, _tmpTreeNode);
		oTreeNode.context = (_element && _tmpTreeNode.buildContext)?_tmpTreeNode.buildContext(_element):null;
		return oTreeNode;
	}
})();
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
Object.extend(com.trs.tree.TreeNav,{
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
		//var oTreeNode = new wcm.TreeNode(oSrcElement);
		//使用更为高效的构造TreeNode的方法，避免重复创建事件监听方法
		var oTreeNode = wcm.TreeNode.fly(oSrcElement);
		switch(oSrcElement.tagName){
			case "DIV":
				var bReturn = oTreeNode.click();
				if(bReturn!==false){
					com.trs.tree.TreeNav._onClickFolder(oSrcElement);
					oTreeNode.afterclick();
				}
				break;
			case "A":
				if(oSrcElement.getAttribute("_stop", 2)!=null){
					var oSrcElement = oSrcElement.parentNode;
					oTreeNode = wcm.TreeNode.fly(oSrcElement);
					var bReturn = oTreeNode.click();
					if(bReturn!==false){
						com.trs.tree.TreeNav._onClickFolder(oSrcElement);
						oTreeNode.afterclick();
					}
					Event.stop(event);
					return false;
				}
				if(com.trs.tree.TreeNav.oPreSrcElementA != null){
					Element.removeClassName(com.trs.tree.TreeNav.oPreSrcElementA, "Selected");
				}
				Element.addClassName(oSrcElement, "Selected");
				com.trs.tree.TreeNav.lastSecondSrcElementA = com.trs.tree.TreeNav.oPreSrcElementA;//modified by hxj
				com.trs.tree.TreeNav.oPreSrcElementA = oSrcElement;
				var bReturn = oTreeNode.click();
				if(bReturn!==false){
					bReturn = com.trs.tree.TreeNav._onClickFolder(oSrcElement);
					oTreeNode.afterclick();
				}
				if(bReturn==false){
					Event.stop(event);
					return false;
				}
				break;
			default:
				return;
		}
		Event.stop(event);
		return false;
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
<SCRIPT LANGUAGE="JavaScript">
function evalate(nodeType){
	switch(nodeType){
		case 'r':
			return WCMConstants.OBJ_TYPE_WEBSITEROOT;
		case 's':
			return WCMConstants.OBJ_TYPE_WEBSITE;
		case 'c':
			return WCMConstants.OBJ_TYPE_CHANNEL;
		case 'mywork':
			return WCMConstants.OBJ_TYPE_MYFLOWDOCLIST;
		case 'mymsg':
			return WCMConstants.OBJ_TYPE_MYMSGLIST;
	}
	return WCMConstants.OBJ_TYPE_UNKNOWN;
}
function findSiteType(_element){
    if(_element == null) return null;
    var sSiteType = _element.getAttribute("SiteType");
    if(sSiteType == null){
		var oParentNade = _element.parentNode;
		if( oParentNade == null 
				|| (oParentNade.tagName != null && oParentNade.tagName == 'BODY') ) return null;
		sSiteType = findSiteType(oParentNade);
		if(sSiteType!=null){
	        _element.setAttribute("SiteType", sSiteType);
		}
    }
    return sSiteType;
}
wcm.TreeNode.prototype.buildContext = function(_element){
	if(_element.tagName!='A' && _element.tagName!='DIV')
		return null;
	var bIsFolder = true;
	var eCurrParent = _element;
	if(_element.tagName=='A'){
		eCurrParent = _element.parentNode;
		bIsFolder = false;
	}
	var nPos = eCurrParent.id.indexOf("_");
	if(nPos <= 0){
		return null;
	}
	var sSiteType = findSiteType(eCurrParent);
	var sType = eCurrParent.id.substring(0, nPos);
	var sObjectId = eCurrParent.id.substring(nPos+1);
	return {
		innerTree : true,
		siteType : sSiteType,
		objType : evalate(sType),
		objId : sObjectId,
		isFolder : bIsFolder,
		right : eCurrParent.getAttribute('RV', 2) || '',
		channelType	: eCurrParent.getAttribute("ChannelType", 2) || 0,
		isVirtual	: eCurrParent.getAttribute("IsV", 2) || '',
		element : _element
	}
}
$MsgCenter.on({
	objType : "extfieldNode",
	beforeclick : function(event){
		var context = event.getContext();
		return context!=null;
	},
	beforeafterclick : function(event){
		var context = event.getContext();
		return context!=null && !context.isFolder;
	},
	afterclick : function(event){
		var context = event.getContext();
		if(context.element){
			context.element.blur();
		}
		m_bNodeClicked = true;
	}
});
</SCRIPT>
</BODY>
</HTML>

<%!
	// 输出站点树结构
	private void writeSitesTree(boolean _bInChannelsHost,Channel _oChannel,int _nSiteType, User _loginUser, RequestHelper _requestHelper, JspWriter _out) throws Exception {
		if(!_bInChannelsHost)
			return;
		// 获取父类站点
		WebSite oWebSite =_oChannel.getSite();
		_out.println("<div "+getExtraAttrOfA(oWebSite)
				+ "id=\"s_" + oWebSite.getId() + "\" classPre=\"site\""
				+ "OnlyNode=\"true\">");
		_out.println("<a href=#>" + oWebSite.getDesc() + "</a>");
		_out.println("</div>");

		_out.println("<ul siteid=\"" + oWebSite.getId() + "\">");
		// 输出栏目树结构
		ArrayList aList=new ArrayList();
		Channel _otempChannel = _oChannel;
		while(_otempChannel.getParent() != null){
			_otempChannel = _otempChannel.getParent();
			aList.add(String.valueOf(_otempChannel.getId()));
		}
		if(aList.size() > 0){
			writeChannelTree(aList, _requestHelper, _out);
		}
		_out.println("</ul>");
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

	//----------------------------------------------
	// 输出从站点类型开始的树结构
	public void writeTreeHTML(boolean _bInChannelsHost,Channel _oChannel,int _nSiteType, User _loginUser,RequestHelper _requestHelper, JspWriter _out)
            throws Exception {
		
		_out.println("<div title=\"" + getDesc(_nSiteType)
				+ " \" id=\"r_" + _nSiteType + "\" classPre=\"SiteType"
				+ _nSiteType + "\" SiteType=\"" + _nSiteType + "\" OnlyNode=\"true\">");
		_out.println("<a href=#>" + getDesc(_nSiteType) + "</a>");
		_out.println("</div>");
		_out.println("<ul SiteType=\"" + _nSiteType + "\">");
		writeSitesTree(_bInChannelsHost,_oChannel,_nSiteType,_loginUser, _requestHelper, _out);
		_out.println("</ul>");
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
	// 输出父栏目树结构
	private void writeChannelTree(ArrayList aList, RequestHelper _requestHelper,JspWriter  _out)throws Exception{
		if(aList.size() > 0){
			for(int i=aList.size()-1; i>=0;i--){
				Channel _oChannel = Channel.findById(Integer.parseInt((String)aList.get(i)));
				_out.println("<div id=\"c_" + _oChannel.getId() + "\" " + getExtraAttrOfA(_oChannel)	 
				+ "' classPre='"+ getClassPre(_oChannel) + "><A href='#' class=''>" + _oChannel.getDesc()  + "</A></div>");
				_out.println("<ul channelid=\"" + _oChannel.getId() + "\">");
				aList.remove(String.valueOf(_oChannel.getId()));		
				if(aList != null)
					writeChannelTree(aList, _requestHelper, _out);
				_out.println("</ul>");
				break;
			}
		}
	}
	private String getChannelClass(ArrayList aList){
		return (aList.size() > 1) ? "" : "channelPage";
	}
%>