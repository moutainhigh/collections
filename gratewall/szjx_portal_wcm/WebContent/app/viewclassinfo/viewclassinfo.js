com.trs.tree.TreeNav.makeGetChildrenHTMLAction = function(dom){
	var nPos = dom.id.indexOf("_");
	if(nPos <= 0){
		alert(wcm.LANG.METARECDATA_ALERT_11 || "不符合规则！");
		return;
	}

	var sParentType	= dom.id.substring(0, nPos);
	var sParentId		= dom.id.substring(nPos+1);
	return "viewclassinfo_creator.jsp?ParentType=" + sParentType + "&ParentId=" + sParentId;
}

com.trs.tree.TreeNav.doActionOnClickA = function(_event, _elElementA){
	if(_elElementA && _elElementA.name.indexOf("acls") >= 0){
		var nPos = _elElementA.name.indexOf("_");
		if(nPos <= 0){
			alert(wcm.LANG.METARECDATA_ALERT_11 || "不符合规则！");
			return;
		}
		var sParentId = _elElementA.name.substring(nPos+1);
		if(sParentId=='0'){
			_elElementA.parentNode.fireEvent('onclick');
			return;
		}
	}
	if(_elElementA && _elElementA.name.indexOf("av") >= 0){
		_elElementA.parentNode.fireEvent('onclick');
		return;
	}
	var oTreeNode = wcm.ClsTreeNode.fly(_elElementA);
	var bReturn = oTreeNode.click();
	if(bReturn!==false){
		oTreeNode.afterclick();
	}
	return false;
}

function findViewId(dom){
    if(dom == null) return null;
    var sViewId = dom.getAttribute("ViewId");
	if(sViewId) return sViewId;
	var oParentNade = dom.parentNode;
	if(oParentNade == null || oParentNade.tagName == 'BODY') return null;
	sViewId = findViewId(oParentNade);
	if(sViewId){
		dom.setAttribute("RootId", sViewId);
	}
    return sViewId;
}

Ext.ns('wcm.ClsTreeNode');
wcm.ClsTreeNode = function(_element){
	var context = (_element)?this.buildContext(_element):null;
	this.objType = WCMConstants.OBJ_TYPE_CLSTREENODE;
	wcm.ClsTreeNode.superclass.constructor.call(this, null, context);
	this.addEvents(['click', 'afterclick', 'contextmenu']);
};
CMSObj.register(WCMConstants.OBJ_TYPE_CLSTREENODE, 'wcm.ClsTreeNode');
Ext.extend(wcm.ClsTreeNode, wcm.CMSObj, {
	buildContext : function(_element){
		return null;
	}
});
(function(){
	var _tmpTreeNode = new wcm.ClsTreeNode(null);
	wcm.ClsTreeNode.fly = function(_element){
		var oTreeNode = {};
		Ext.apply(oTreeNode, _tmpTreeNode);
		oTreeNode.context = (_element && _tmpTreeNode.buildContext)?
								_tmpTreeNode.buildContext(_element):null;
		return oTreeNode;
	}
})();

wcm.ClsTreeNode.prototype.buildContext = function(_element){
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
	var sViewId = findViewId(eCurrParent) || 0;
	var sType = eCurrParent.id.substring(0, nPos);
	var sObjectId = eCurrParent.id.substring(nPos+1);
	return {
		navTree : true,
		objType : evalate(sType),
		objId : sObjectId,
		viewId : sViewId,
		element : _element
	}
}

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CLSTREENODE,
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

function evalate(nodeType){
	switch(nodeType){
		case 'v':
			return WCMConstants.OBJ_TYPE_METAVIEW;
		case 'cls':
			return WCMConstants.OBJ_TYPE_CLASSINFO;
	}
	return "";
}

function evalate2(objType){
	switch(objType){
		case WCMConstants.OBJ_TYPE_METAVIEW:
			return 'v';
		case WCMConstants.OBJ_TYPE_CLASSINFO:
			return 'cls';
	}
	return "";
}

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CLASSINFO,
	afteradd : function(event){
		var host = event.getHost();
		if(!host) return;
		var nodeType = evalate2(host.getType());
		var nodeId = host.getId();
		refresh(nodeType, nodeId);
	},
	afteredit : function(event){
		var host = event.getObj();
		if(!host) return;
		var nodeType = evalate2(host.getType());
		var nodeId = host.getId();
		var bFocus = isFocus(nodeType, nodeId);
		refreshNode(nodeType, nodeId, function(){
			if(bFocus){
				com.trs.tree.TreeNav.focus(nodeType + "_" + nodeId);
			}
		});		
	},
	afterdelete : function(event){
		var host = event.getObj();
		if(!host) return;
		var nodeType = evalate2(host.getType());
		var nodeId = host.getId();
		if(isFocus(nodeType, nodeId)) clickParent(nodeType, nodeId);
		refreshNode(nodeType, nodeId);
	}
});

function refresh(_sNodeType, _nNodeId){
	var dom = $(_sNodeType + "_" + _nNodeId);
	if(!dom) return;
	com.trs.tree.TreeNav.reloadChildren(dom, true);
}

function refreshAndCallBack(_sObjType, _nObjId, _fCallBack){
	var dom = $(_sObjType + "_" + _nObjId);
	if(!dom) return;
	_fCallBack = _fCallBack || Ext.emptyFn;
	com.trs.tree.TreeNav.reloadChildren(dom, true, _fCallBack);
}

function refreshNode(_sNodeType, _nNodeId, _fCallBack){
	var dom = $(_sNodeType + "_" + _nNodeId);
	if(!dom) return;
	var eTmp = com.trs.tree.TreeNav._getParentElement(dom);
	_fCallBack = _fCallBack || Ext.emptyFn;
	com.trs.tree.TreeNav.reloadChildren(eTmp, true, _fCallBack);
}

function isFocus(_sNodeType, _nNodeId){
	var linkNode = com.trs.tree.TreeNav.oPreSrcElementA;
	if(!linkNode) return false;
	var divNode = linkNode.parentNode;
	return divNode.id == (_sNodeType + "_" + _nNodeId)
}

function clickParent(_sNodeType, _nNodeId){
	var dom = $(_sNodeType + "_" + _nNodeId);
	if(!dom) return;
	var eTmp = com.trs.tree.TreeNav._getParentElement(dom);
	if(eTmp) eTmp.getElementsByTagName("a")[0].fireEvent('onclick');
}

Event.observe(window, 'load', function(){
	var objType = getParameter('objType');
	var objId = getParameter('objId');
	if(objType && objId){
		refreshAndCallBack(objType,objId,function(){
			var viewDom = $(objType + "_" + objId);
			//方式不太好，性能不高
			var ULDom = Element.next(viewDom);
			var firstClassDom = Element.first(ULDom);
			var aClassDom = Element.first(firstClassDom);
			aClassDom.click();
		});//请求取到这个节点的子节点
	}
});
