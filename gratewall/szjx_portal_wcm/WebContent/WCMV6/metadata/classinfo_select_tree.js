var ClassInfos = {
	servicesName : 'wcm6_ClassInfo',
	initMethodName : 'createClassInfoTreeHTMLOfIds',
	loadMethodName : 'createClassInfoTreeHTML',
	initTree : function(){
		var selectedValue = window.selectedValue || getParameter("selectedValue");
		if(selectedValue && selectedValue != "0" && /^\d+(,\d+)*$/.test(selectedValue)){
			var oHelper = new top.com.trs.web2frame.BasicDataHelper();
			oHelper.Call(this.servicesName, this.initMethodName, {
				objectIds	: selectedValue,
				SelectType	: 1,
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
		}else{
			ClassInfos.normalLoad();
		}
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
		}
	}
};

/*---------------------------------TreeNav Relative Start-----------------------------------------**/
var treeType = parseInt(getParameter("treeType"), 10) || 0;
com.trs.tree.TreeNav.setType(treeType + 1);
//com.trs.tree.TreeNav.setType(parseInt(getParameter("treeType")) || com.trs.tree.TreeNav.TYPE_RADIO);
//com.trs.tree.TreeNav.setType(parseInt(getParameter("treeType")) || com.trs.tree.TreeNav.TYPE_CHECKBOX);
Object.extend(com.trs.tree.TreeNav, {
	initTreeBySelf : true,
	_sAttrNameRealtedValue : "objectId",
	isOpened : function(divElement){
		return divElement.className.indexOf("Opened") >= 0;
	},
	makeGetChildrenHTMLAction : function(divElement){
		var urlPrefix = getWebURL()+'center.do?serviceid=' + ClassInfos.servicesName + '&methodname='; 
		var params = "&parentId=" + divElement.getAttribute("objectId") + "&SelectType=1";
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


/*---------------------------------Crash Board Relative Start-----------------------------------------**/
function init(oParams){
	window.objectId = oParams["objectId"];
	window.objectName = oParams["objectName"];
	window.selectedValue = oParams["selectedValue"];
	ClassInfos.initTree();
}

function onOk(){
    try{
        var sSelIds = com.trs.tree.TreeNav.getCheckValues("TreeView");
        var sSelNames = com.trs.tree.TreeNav["SelectedNames"];
		window.parent.notifyParent2CloseMe(document.FRAME_NAME);
		window.parent.notifyParentOnFinished(document.FRAME_NAME, {ids:sSelIds, names:sSelNames});
    }catch(error){
        //防止选择树iframe没有加载完成就单击确定按钮而导致脚本错误
		//alert(error.message);
    }
}

function onCancel(){
    try{
        if (window.parent){
            window.parent.notifyParent2CloseMe(document.FRAME_NAME);
        }
    }catch(error){
        //防止选择树iframe没有加载完成就单击确定按钮而导致脚本错误
		//alert(error.message);
    }
}
/*---------------------------------Crash Board Relative End-----------------------------------------**/