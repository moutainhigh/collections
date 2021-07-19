var Categorys = {
	servicesName : 'wcm61_category',
	initMethodName : 'createCategoryTreeHTMLOfIds',
	loadMethodName : 'createCategoryTreeHTML',
	initTree : function(){
		var selectedValue = window.selectedValue || getParameter("selectedValue");
		if(selectedValue && selectedValue != "0" && /^\d+(,\d+)*$/.test(selectedValue)){
			var oHelper = new top.com.trs.web2frame.BasicDataHelper();
			oHelper.Call(this.servicesName, this.initMethodName, {
				objectIds	: getParameter("objectId") + "," +selectedValue ,
				SelectType	: getParameter("selectType")||1,
				type		: 1
			}, true, function(transport, json){
				var sText = transport.responseText.trim();
				if(sText.length <= 0){
					Categorys.normalLoad();
					return;
				}
				Element.update("TreeView", sText);
				com.trs.tree.TreeNav.initTree();
			});
		}else{
			Categorys.normalLoad();
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
	methodType : 'get',
	initTreeBySelf : true,
	_sAttrNameRealtedValue : "objectId",
	isOpened : function(divElement){
		return divElement.className.indexOf("Opened") >= 0;
	},
	makeGetChildrenHTMLAction : function(divElement){
		var urlPrefix = '/'+(location.pathname.split('/')[1]||'wcm') + '/center.do?serviceid=' + Categorys.servicesName + '&methodname='; 
		var params = "&parentId=" + divElement.getAttribute("objectId") + "&SelectType="+(getParameter("selectType")||1);
		return urlPrefix + Categorys.loadMethodName + params;
	}	
});
function getFirstHTMLChild(domNode){
	if(domNode == null) return null;
	for (var i = 0; i < domNode.childNodes.length; i++){
		if(domNode.childNodes[i].nodeType == 1){
			return domNode.childNodes[i];
		}
	}
	return null;
}

com.trs.tree.TreeNav.observe('onload', function(){
	var divElement = getFirstHTMLChild($('TreeView'));
	if(!com.trs.tree.TreeNav.isOpened(divElement)){
		com.trs.tree.TreeNav._onClickFolder(divElement, function(){
			Categorys.initData();
		});
	}else{
		Categorys.initData();
	}
	com.trs.tree.TreeNav.focus(divElement);
});
/*---------------------------------TreeNav Relative End-----------------------------------------**/


/*---------------------------------Crash Board Relative Start-----------------------------------------**/
function init(oParams){
	Ext.toSource(oParams);
	window.objectId = oParams["objectId"];
	window.objectName = oParams["objectName"];
	window.selectedValue = oParams["selectedValue"];
	Categorys.initTree();
}

function onOk(){
    try{
        var sSelIds = com.trs.tree.TreeNav.getCheckValues("TreeView");
        var sSelNames = com.trs.tree.TreeNav["SelectedNames"];
		var cbr = this;
		cbr.notify({ids:sSelIds, names:sSelNames});
		cbr.hide();
		cbr.close();
		return false;
    }catch(error){
        //防止选择树iframe没有加载完成就单击确定按钮而导致脚本错误
		//alert(error.message);
    }
}

function onCancel(){
}

function onCancelSelect(){
	var selTreeNav = document.getElementsByName("selTreeNav")
	if(selTreeNav && selTreeNav.length > 0){
		 for(var i = 0 ; i < selTreeNav.length; i++ ){
			 selTreeNav[i].checked = false;
		 }
	}
	return false;
}
/*---------------------------------Crash Board Relative End-----------------------------------------**/