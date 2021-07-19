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
function evalate(nodeType){
	switch(nodeType){
		case 'r':
			return WCMConstants.OBJ_TYPE_WEBSITEROOT;
		case 's':
			return WCMConstants.OBJ_TYPE_WEBSITE;
		case 'c':
			return WCMConstants.OBJ_TYPE_CHANNEL;
	}
	return WCMConstants.OBJ_TYPE_UNKNOWN;
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
	objType : WCMConstants.OBJ_TYPE_TREENODE,
	beforeclick : function(event){
		return true;
	},
	afterclick : function(event){
		//阻止消息继续执行
		return false;
	}
});