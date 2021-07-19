Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	searchEnable : true,
	serviceId : 'wcm61_master',
	methodName : 'queryMasterByMasterType',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"PageSize" : 6
	}
});

Ext.apply(PageContext, {	
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	pageFilters : (function(){
		if(!PageContext.filterEnable)return null;
		var filters = new wcm.PageFilters({
			displayNum : 4,
			filterType : getParameter('FilterType') || 0
		});
		return filters;
	}())
});

Ext.apply(PageContext.PageNav,{
	UnitName :  '个',
	TypeName : '母板'
});
function onOk(cb){
	//cb.hide();
	var rst = $('chkNone').checked ? {selectedId: "", selectedName: ""} : buildValues();
	setTimeout(function(){
		cb.callback(rst);		
		cb.close();
	}, 10);
	return false;
}
function onNext(cb){
	PageContext.loadList(PageContext.params);
	return false;
}
window.m_cbCfg = {
	btns : [
		{
			text : wcm.LANG.TRUE || '确定',
			cmd : function(){
				putAwayImgIfExist();
				this.hide();
				//debugger
				var rst = $('chkNone').checked ? {selectedId: "", selectedName: ""} : buildValues();
				//if(!$('chkNone').checked && rst.selectedIds.length==0)return true;
				this.notify(rst);
				return false;
			}
		},
		{
			text : wcm.LANG.CANCEL || '取消',
			extraCls : 'wcm-btn-close',
			cmd : function(){
				putAwayImgIfExist();
			}
		}
	]
};

wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			MNAME : sValue
		});
	}
});

var hasSelected = "";
var hasSelectedName = "";

function buildValues(){
	var selectedId = "";
	var selectedName = "";
	if($('chkNone').checked) return {selectedId : "", selectedName : ""};
	return {selectedId : hasSelected, selectedName : hasSelectedName};
}

function init(params){
	if(!params){
		hasSelected = "";
		hasSelectedName = "";
		return;
	}
	hasSelected = (params['checkedMId']) ? params['checkedMId'] : "";
	hasSelectedName = (params['checkedMName']) ? params['checkedMName'] : "";
}


Event.observe(document, 'click', function(event){
	event = window.event || event;
	var dom = Event.element(event);
	if(dom.className == "sp_name"){
		var _id = dom.getAttribute("_id");
		dom = $('chk_'+_id);
		if(!$('chk_'+_id).checked){
				$('chk_'+_id).checked = true;
		}
	}
	if(!dom.value) return;
	var type = dom.getAttribute('type');
	if(type != "radio") return;	
	if(dom.checked && type == "radio"){
		hasSelected = dom.value;
		hasSelectedName = dom.getAttribute('_name', 2);
		return;
	}
});

Event.observe('unselect', 'click', function(event){
	if($('chkNone').checked){
		$('chkNone').checked = false;
		disableMasterSelect(false);
	}else{
		$('chkNone').checked = true;
		disableMasterSelect(true);
	}
});

var m_bFirstShowMask = true;
function disableMasterSelect(_bFlag){
	if(_bFlag === false) {
		Element.hide('divMask');
		Element.show('divcontent');
	}else{	
		if(m_bFirstShowMask) {
			Position.clone($('divcontent'), $('divMask'));
			m_bFirstShowMask = false;
		}
		//WCM-446 解决ie8会刷新页面启用兼容性视图的问题
		document.getElementById('divcontent').style.visibility = 'hidden';
		//Element.hide('divcontent');
		Element.show('divMask');
	}
}

PageContext.addListener('afterrender', function(){
	var elements = document.getElementsByName("MasterId");
	var selectedId = hasSelected;
	for(var i=0,j=elements.length;i<j;i++){
		if(elements[i].value == selectedId){
			elements[i].checked = true;
			break;
		}
	}
});
//母板列表查看大图，采用jQuery flyout插件实现
function imgFly(){
	var animating = false;
	jQuery('.piclook').click(function(){
		if(animating === true) return false;
		animating = true;
		var parentDoc = parent.document;
		var dom = parentDoc.getElementById('jquery-temp');
		if(!dom){
			var dom = parentDoc.createElement('div');
			dom.id = 'jquery-temp';
			dom.style.position = 'absolute';
			dom.style.display = 'none';
			parentDoc.body.appendChild(dom);
		}		
		//清除掉父窗口中的临时节点
		if(parent.$('master-loader')){
			parent.Element.remove(parent.$('master-loader'));
		}
		//在更新dom的innerHTML之前，先解除dom内a标签的绑定事件
		var firstChild = dom.firstChild;
		if(firstChild){
			parent.jQuery(firstChild).unbind("click");
		}
		//a标签的outerhtml包含图片相对路径href，赋值到父页面的节点之前需要调整相对路径
		var outerHtml = this.outerHTML.replace(/\.\.\/images\/list\/none\.gif/g,"./images/list/none.gif");
		dom.innerHTML =  outerHtml;
		var aEl = dom.firstChild;
		parent.jQuery(aEl).flyout({
			loader : 'master-loader',
			inOpacity:0, 
			outOpacity:1, 
			fullSizeImage:false, 
			inSpeed: 800,
			outSpeed : 1000, 
			closeTip : " - 点击关闭",
			flyOutStart : function(){
			},
			flyOutFinish : function(){
				animating = false;
			}
		});
		aEl.fireEvent('onclick');
		return false;
	});
}

//弹出图片和缩略图以外的元素上发生点击事件时，触发弹出图片的click事件，让其隐藏
Event.observe(document, 'click', function(event){
	putAwayImgIfExist();
});
Event.observe(parent.document, 'click', function(event){
	putAwayImgIfExist();
});
function putAwayImgIfExist(){
	if(parent.$('master-loader')){
		parent.jQuery('#master-loader img').click();
	}
}
