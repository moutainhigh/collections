Ext.ns('wcm.TreeNode');
wcm.TreeNode = function(_element){
	var context = (_element)?this.buildContext(_element):null;
	this.objType = WCMConstants.OBJ_TYPE_TREENODE;
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
		oTreeNode.context = (_element && _tmpTreeNode.buildContext)?
								_tmpTreeNode.buildContext(_element):null;
		return oTreeNode;
	}
})();
Ext.apply(com.trs.tree.TreeNav, {
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
	onContextMenu : function(e){
		e = window.event || e;
		Event.stop(e);
        var myExtEvent = extEvent(e);
		var oSrcElement = Event.element(e);
		var oTreeNode = wcm.TreeNode.fly(oSrcElement);
		var newContextFn = wcm.TreeNode.prototype._buildContextForContextMenu;
		var context = newContextFn ? newContextFn(oTreeNode.context) : oTreeNode.context;
		context = context || {};
		oTreeNode.context = Ext.apply({
			extEvent : myExtEvent,
			event : myExtEvent.browserEvent,
			wcmEvent : CMSObj.createEvent(context, context),
			targetElement : oSrcElement,
			fromTree : true
		}, context);
		oTreeNode.contextmenu();
	}
});

window.TblFunction = {
	refreshTree : function (event, currObj){
		//TODO
		refreshTree();
	},
	quickLocate : function(event, currObj){
		wcm.CrashBoarder.get('quicklocate').show({
			title : wcm.LANG.nav_tree_title_1 || '快速定位',
			src : WCMConstants.WCM6_PATH + "nav_tree/quicklocate.html",
			left: '180px',
            top: '60px', 
			width: '430px',
			height: '270px',
			reloadable : false,
			params : [],
			maskable : true,
			callback : function(info){
				//focus node with info
				focus(info[0], info[1], null, function(){
					try{
						var treeNodeDom = $(info.join('_'));
						if(treeNodeDom==null)return;
						treeNodeDomA = treeNodeDom.getElementsByTagName('A')[0];
						if(treeNodeDomA==null)return;
						var oTreeNode = wcm.TreeNode.fly(treeNodeDomA);
						var bReturn = oTreeNode.click();
						if(bReturn!==false){
							oTreeNode.afterclick();
						}
					}catch(err){}
				});
				this.close();
			}
		});
	},
	moreAction : function(event, currObj){
		//TODO
		if(!currObj) currObj = $('showTreeMoreId');
		//showTreeMoreActions(currObj);
		if(window.parent.showTreeMoreActions){
			var position = Position.page(currObj);
			position[0] += currObj.offsetWidth;
			position[1] += currObj.offsetHeight;
			window.parent.showTreeMoreActions(position);
			//nUserID = window.parent.userId;
		}
	}
};

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
		case 'i':
			return WCMConstants.OBJ_TYPE_WEBSITEROOT;
		case 'mywork':
			return WCMConstants.OBJ_TYPE_MYFLOWDOCLIST;
		case 'mymsg':
			return WCMConstants.OBJ_TYPE_MYMSGLIST;
		case 'trsserver':
			return WCMConstants.OBJ_TYPE_TRSSERVERLIST;
	}
	return WCMConstants.OBJ_TYPE_UNKNOWN;
}

function evalate2(objType){
	switch(objType){
		case WCMConstants.OBJ_TYPE_WEBSITEROOT:
			return 'r';
		case WCMConstants.OBJ_TYPE_WEBSITE:
			return 's';
		case WCMConstants.OBJ_TYPE_CHANNEL:
		case WCMConstants.OBJ_TYPE_CHANNELMASTER:
			return 'c';
		case WCMConstants.OBJ_TYPE_MYFLOWDOCLIST:
			return 'mywork';
		case WCMConstants.OBJ_TYPE_MYMSGLIST:
			return 'mymsg';
		case WCMConstants.OBJ_TYPE_TRSSERVERLIST:
			return 'trsserver';
	}
	return '';
}

function isFocus(nodeType, nodeId){
	var oFocusElementA = com.trs.tree.TreeNav.oPreSrcElementA;
	if(!oFocusElementA) return false;
	var aIdInfo = oFocusElementA.name.split("_");
	return (aIdInfo[0] == 'a' + nodeType) && (aIdInfo[1] == nodeId);
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
	var nChnlType = eCurrParent.getAttribute("ChannelType", 2) || 0;
	var context = {
		navTree : true,
		siteType : sSiteType,
		objType : evalate(sType),
		objId : sObjectId,
		isFolder : bIsFolder,
		right : eCurrParent.getAttribute('RV', 2) || '',
		channelType	: nChnlType,
		chnlType	: nChnlType, 
		isVirtual	: eCurrParent.getAttribute("IsV", 2) || '',
		element : _element
	};
	//兼容context一般都会带上params这个参数
	context.params = Ext.Json.toUpperCase(context);
	return context;
}
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_TREENODE,
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
Event.observe(window, 'load', function(){
	//tblOptions
	Ext.get('tblOptions').on('click', function(event, target){
		if(target.tagName!='SPAN')return;
		var sFuncType = target.getAttribute('_type', 2);
		if(window.TblFunction[sFuncType]){
			window.TblFunction[sFuncType].call(target, event, target);
		}
	});
});

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_MAINPAGE,
	afteredit : function(event){
	},
	afterinit : function(event){
		var host = event.getHost();
		var nodeType = evalate2(host.getType());
		if(!nodeType) return;
		var nodeId = host.getId()||"0";
		if(isFocus(nodeType, nodeId)) return;
		focus(nodeType, nodeId);
	},
	redirect : function(event){
		var host = event.getHost();
		var context = {};
		if(host){
			var nodeType = evalate2(host.getType());
			var nodeId = host.getId();
			context = event.getContext();
		}
		if(nodeType == null || nodeId == null){//if no host, then use current focus node info.
			var info = getFocusedNodeInfo();
			nodeType = info.type;
			nodeId = info.id;
		}
		focus(nodeType, nodeId, null, function(){
			try{
				var treeNodeDom = $(nodeType + '_' + nodeId);
				if(treeNodeDom==null)return;
				treeNodeDomA = treeNodeDom.getElementsByTagName('A')[0];
				if(treeNodeDomA==null)return;
				var oTreeNode = wcm.TreeNode.fly(treeNodeDomA);
				oTreeNode.tabType = context.tabType;
				oTreeNode.params = context.params;
				var bReturn = oTreeNode.click();
				if(bReturn!==false){
					oTreeNode.afterclick();
				}
			}catch(err){}
		});
	}
});

$MsgCenter.on({
	objType : [WCMConstants.OBJ_TYPE_WEBSITE, WCMConstants.OBJ_TYPE_CHANNEL, WCMConstants.OBJ_TYPE_CHANNELMASTER],
	afteradd : function(event){
		var obj = event.getObj();
		if(WCMConstants.OBJ_TYPE_WEBSITE == obj.getType()){
			if(obj.getId() == 0) {
				refresh('r', findFocusNodeSiteType()); 
				return;
			}
			doAfterModifySite(obj.getId(), false);
			return;
		}
		var host = event.getHost();
		var nodeType = evalate2(host.getType());
		var nodeId = host.getId();
		refresh(nodeType, nodeId);
	},
	afteredit : function(event){
		var host = event.getObj();
		var nodeType = evalate2(host.getType());
		var nodeId = host.getId();
		var focusInfo = getFocusNodeInfo({objType:true, objId:true});
		var bFocus = isAncestor(nodeType, nodeId);
		refreshNode(nodeType, nodeId, function(){
			if(bFocus){
				focus(focusInfo['objType'], focusInfo['objId']);
			}
		});		
	},
	afterdelete : function(event){
		var host = event.getObj();
		var nodeType = evalate2(host.getType());
		var nodeId = host.getId();
		if(isFocus(nodeType, nodeId)) clickParent(nodeType, nodeId);
		refreshNode(nodeType, nodeId);
	},
	aftermove : function(event){
		var srcObjectIds = event.getObjsOrHost().getIds();
		var context = event.getContext();
		var dstObjectId = context.get('dstObjectId');
		var isSite = context.get('isSite');
		doAfterMove(srcObjectIds.join(","), dstObjectId, isSite, true, function(){
			var host = event.getObj();
			if(host.getType() == WCMConstants.OBJ_TYPE_CHANNELMASTER){//move the focus node
				focus(evalate2(host.getType()), host.getId());
			}
		});
	}
});

function refreshTree(callBack){
	//获取当前Focus对象的ID
	var oFocusElement = com.trs.tree.TreeNav.oPreSrcElementA;
	if( oFocusElement != null ){
		oFocusElement = oFocusElement.parentNode;
	}
	var sFocusId =  null;
	if(oFocusElement != null) sFocusId = oFocusElement.id;

	//清空所有的树
    var aTreeRoot = [0, 1, 2, 3, 4];
	for(var i=0; i<aTreeRoot.length; i++){
		if($("r_" + aTreeRoot[i])){
			com.trs.tree.TreeNav.clearChildren("r_" + aTreeRoot[i]);
		}
	}
	//重新装载上次Focus的节点
	if(sFocusId != null){
		var nPos = sFocusId.indexOf("_");
		if(nPos <= 0){
			alert(String.format("Id[{0}]规则不一致",sFocusId));
			return;
		}
		var sNodeType = sFocusId.substring(0, nPos);
		var sNodeId = sFocusId.substring(nPos+1);
		focus(sNodeType, sNodeId, null, callBack);
	}
}
function refreshMain(){
	var oFocusElementA = com.trs.tree.TreeNav.oPreSrcElementA;
    //之所以调用下面这条语句，是为了防止oFocusElementA从节点树中删除之后，引入错误
    oFocusElementA = $(oFocusElementA.name);
	oFocusElementA.fireEvent("onclick");
}

$MsgCenter.on({
	objType : [WCMConstants.OBJ_TYPE_SITERECYCLE, WCMConstants.OBJ_TYPE_CHNLRECYCLE],
	afterdelete : function(event){
		var host = event.getHost();
		var nodeType = evalate2(host.getType());
		var nodeId = host.getId();
		refresh(nodeType, nodeId);
	}
});

/*
*根据浏览器地址栏上的参数，定位导航树节点和中间的页面
*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_MAINPAGE,
	afterinit : function(event){
		//仅在主页面首次加载的时候做处理，主页面加载完成之后，再做切换时，不处理
		if(arguments.callee.invoked) return;
		arguments.callee.invoked = true;

		//获取参数，判断有哪个参数，才执行下面的，跳转到相应节点
		var objType = getParameter('objType');
		var objId = getParameter('objId');
		if(!objType || !objId){
			return;
		}

		focus(objType, objId, null, function(){
			try{
				var treeNodeDom = $(objType+'_'+objId);
				if(treeNodeDom==null)return;
				treeNodeDomA = treeNodeDom.getElementsByTagName('A')[0];
				if(treeNodeDomA==null)return;
				var oTreeNode = wcm.TreeNode.fly(treeNodeDomA);
				var bReturn = oTreeNode.click();
				if(bReturn!==false){
					oTreeNode.afterclick();
				}
			}catch(err){}
		});
	}
});
