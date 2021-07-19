function getWebURL(){
	var url = window.location.protocol;
	url += "//" + window.location.host;
	var matchs = window.location.pathname.match(/^(\/[^\/]+\/).*$/);
	if(matchs && matchs[1]){
		url += matchs[1];
	}
	return url;
}

var ClassInfos = {
	servicesName : 'wcm6_ClassInfo',
	initMethodName : 'createClassInfoTreeHTMLOfIds',
	loadMethodName : 'createClassInfoTreeHTML',
	initTree : function(){
		var selectedValue = window.selectedValue || getParameter("selectedValue");
		if(!selectedValue || selectedValue == "0" || !/^\d+(,\d+)*$/.test(selectedValue)){
			selectedValue = window.objectId || getParameter("objectId");
		}
		if(!selectedValue || selectedValue == "0" || !/^\d+(,\d+)*$/.test(selectedValue)){
			Ext.Msg.alert((wcm.LANG.METARECDATA_VALID_1 || "参数不合法:") + selectedValue);
			return;
		}
		var oHelper = new top.com.trs.web2frame.BasicDataHelper();
		oHelper.Call(this.servicesName, this.initMethodName, {
			objectIds	: selectedValue,
			selectType	: 1,
			type		: 1
		}, true, function(transport, json){
			var sText = transport.responseText.trim();
			if(sText.length <= 0){
				ClassInfos.normalLoad();
				return;
			}
			Element.update("TreeView", sText);
			com.trs.tree.TreeNav.initTree();
		});
	},
	normalLoad : function(){
		var divElement = getFirstHTMLChild($('TreeView'));
		divElement.setAttribute("objectId", window.objectId || getParameter("objectId"));
		divElement.setAttribute("title", "ID:" + (window.objectId || getParameter("objectId")));
		divElement.getElementsByTagName("a")[0].innerText = window.objectName || getParameter("objectName");
		com.trs.tree.TreeNav.initTree();
	},
	initData : function(){
		var selectedValue = window.selectedValue || getParameter("selectedValue");
		if(selectedValue && selectedValue != "0"){
			var aSelectedValue = selectedValue.split(",");
			for (var i = 0; i < aSelectedValue.length; i++){
				var cbox = $('cnode_' + aSelectedValue[i]);
				if(cbox){
					cbox.checked = true;
				}
			}
			if(parent.notifyLoad){
				parent.notifyLoad();
			}
		}
	}
};

/*---------------------------------TreeNav Relative Start-----------------------------------------**/
//com.trs.tree.TreeNav.setType(parseInt(getParameter("treeType")) || com.trs.tree.TreeNav.TYPE_RADIO);
com.trs.tree.TreeNav.setType(parseInt(getParameter("treeType")) || com.trs.tree.TreeNav.TYPE_CHECKBOX);
Object.extend(com.trs.tree.TreeNav, {
	methodType : 'get',
	initTreeBySelf : true,
	_sAttrNameRealtedValue : "objectId",
	isOpened : function(divElement){
		return divElement.className.indexOf("Opened") >= 0;
	},
	makeGetChildrenHTMLAction : function(divElement){		
		var urlPrefix = getWebURL()+'center.do?selectType=1&serviceid=' + ClassInfos.servicesName + '&methodname='; 		
		var params = "&parentId=" + divElement.getAttribute("objectId");
		return urlPrefix + ClassInfos.loadMethodName + params;
	}	
});
com.trs.tree.TreeNav.observe('onload', function(){
	var divElement = getFirstHTMLChild($('TreeView'));
	if(!com.trs.tree.TreeNav.isOpened(divElement)){
		com.trs.tree.TreeNav._onClickFolder(divElement, function(){
			ClassInfos.initData();
		});
	}else{
		ClassInfos.initData();
	}
	com.trs.tree.TreeNav.focus(divElement);
});
/*---------------------------------TreeNav Relative End-----------------------------------------**/

function getFirstHTMLChild(domNode){
	if(domNode == null) return null;
	for (var i = 0; i < domNode.childNodes.length; i++){
		if(domNode.childNodes[i].nodeType == 1){
			return domNode.childNodes[i];
		}
	}
	return null;
}

function init(oParams){
	window.objectId = oParams["objectId"];
	window.objectName = oParams["objectName"];
	window.selectedValue = oParams["selectedValue"];
	ClassInfos.initTree();
}

Event.observe(window, 'load', init, false);

function getCheckedValues(){
	var sSelIds = com.trs.tree.TreeNav.getCheckValues("TreeView");
	var sSelNames = com.trs.tree.TreeNav["SelectedNames"];
	return {ids:sSelIds, names:sSelNames};
}

var disabledCache = {};

function clear(){
	for (var objectId in disabledCache){
		if(disabledCache[objectId]){
			var cbox = $('cnode_' + objectId);
			if(cbox){
				cbox.disabled = false;
			}
		}
	}
	disabledCache = {};
}

function disable(sObjectIds){
	if(sObjectIds == null || sObjectIds == "") return;
	var aObjectIds = sObjectIds.split(",");
	for (var i = 0; i < aObjectIds.length; i++){
		var cbox = $('cnode_' + aObjectIds[i]);
		if(cbox){
			cbox.disabled = true;
			disabledCache[aObjectIds[i]] = true;
		}
	}
}

function enable(sObjectIds){
	if(sObjectIds == null || sObjectIds == "") return;
	var aObjectIds = sObjectIds.split(",");
	for (var i = 0; i < aObjectIds.length; i++){
		var cbox = $('cnode_' + aObjectIds[i]);
		if(cbox){
			cbox.disabled = false;
			delete disabledCache[aObjectIds[i]];
		}
	}
}