/**
* 负责收集和传递页面参数的辅助对象
*/
// 1.定义页面环境
Object.extend(PageContext.params, {
	queryViewDesc			: getParameter("queryViewDesc") || "",
	queryViewName			: getParameter("queryViewName") || "",
	queryViewId				: getParameter("queryViewId") || "",
	CrUser					: getParameter("crUser") || "",
	orderBy					: getParameter("orderBy") || "",
	ContainsRight			: true,
	pagesize				: -1,
	fieldsToHtml			: 'VIEWNAME,VIEWDESC'
});

/**
* 用于控制列表显示的列表集合对象
*/
var OBJECTS_CLZ_DEF = {
	outer_box			: 'tabular', // 外围元素
	disactive_element	: 'inner_tabular', // 非活动元素
	active_element		: 'inner_tabular_blueness', // 活动元素
	checkbox			: 'element_list_checkbox' // 复选框
};

/**
*对象相关信息
*/
var OBJECTS_REL_DEF = {
	sDatonId		: 'objects',
	sSrcId			: '_ObjectId',
	checkboxId		: 'chk_',
	editPanelId		: 'txt_dispname_',
	rights			: '_rights',
	innerTabularId	: 'inner_tabular_',
	tabularId		: 'tabular_',
	optType			: '_optType',
	mgr				: $viewInfoMgr
};

var ViewInfos = Object.extend(new Abstract.ElementContainer(OBJECTS_REL_DEF, OBJECTS_CLZ_DEF), {
	servicesName : 'wcm6_MetaDataDef',
	queryMethodName : 'jQueryViews',

	loadPage : function(beforeLoad, afterLoad){
		$endSimpleRB();
		$beginSimpleRB("正在刷新列表,请稍候...");
		(beforeLoad || Prototype.emptyFunction)();
		this.getHelper().Call(this.servicesName, this.queryMethodName, PageContext.params, true, function(_transport, _json){		
			if(_transport.getResponseHeader("Num") != 0){
				Element.update($(ViewInfos.sDatonId), _transport.responseText);
				ViewInfos.reset();
			}else{
				Element.update($(ViewInfos.sDatonId), $('divNoObjectFound').innerHTML);
			}
			ViewInfos.inspect([]);
			(afterLoad || Prototype.emptyFunction)();
			$endSimpleRB();		
		});
	},
	refreshList : function(aObjectIds, sObjectDesc){//保存选中状态刷新列表页面
		if(aObjectIds && !Array.isArray(aObjectIds)){
			aObjectIds = [aObjectIds];
		}
		var selectedIds = aObjectIds || ViewInfos.getSelectedIds();
		ViewInfos.loadPage(null, function(){
			ViewInfos.toggleCertains(selectedIds);
		});
	},
	RefreshList : function(aObjectIds, sObjectDesc){
		this.refreshList(aObjectIds, sObjectDesc);
	},
	updateObject : function(oPostData){//由于修改属性面板导致的更新
		var sId = ViewInfos.getSelectedIds()[0];
		ViewInfos.setTitle(sId, oPostData);
		if(oPostData["ViewDesc"]){
			UIEditPanel.setValue("txt_dispname_" + sId, oPostData["ViewDesc"]);
		}
	},
	updateDesc : function(_oPostData){
		var sId = ViewInfos.getSelectedIds()[0];
		ViewInfos.mgr.save(PageContext.params.objectids.last(), _oPostData, null, function(){
			try{
				$oap().UIEditPanel.setValue("ViewDesc", _oPostData["ViewDesc"]);
				ViewInfos.setTitle(sId, _oPostData);
		}catch(err){
				//TODO logger
				//alert('inspect:'+err.message);
			}	
		});
	},
	setTitle : function(sId, _oPostData){
		var mapping = {
			//TableName : [1, "名称:"],
			ViewDesc : [2, "名称:"]
		};
		var key = '';
		for(prop in _oPostData){
			key = prop;
			break;
		}
		if(mapping[key]){
			var title = $("tabular_" + sId).getAttribute("title");
			var array = title.split("\r");
			array[mapping[key][0]] = mapping[key][1] + _oPostData[key];
			$("tabular_" + sId).setAttribute("title", array.join("\r"));
		}
	},
	isSingleTable : function(checkedElmIds){
		if(checkedElmIds.length != 1) return false;
		var element = $('tabular_' + checkedElmIds[0]);
		if(element && (element.getAttribute("_isSingleTable") == 'true' 
				|| element.getAttribute("_isSingleTable") == true)){
			return true;
		}
		return false;
	},
	inspect : function(_arrAllIds){
		try{
			var checkedElmIds = _arrAllIds ? _arrAllIds: ViewInfos.getSelectedIds();
			var aRights = [1111111111111111111111111111111111111111111111111];//ViewInfos.getRights();
			Object.extend(PageContext.params, {objectids:checkedElmIds, isSingleTable : this.isSingleTable(checkedElmIds)});
			$MessageCenter.sendMessage('oper_attr_panel','PageContext.response',"PageContext",
				Object.extend(Object.extend({}, PageContext.params),{"objecttype":"ViewInfo","objectrights":aRights}), true, true);
		}catch(err){
			//TODO logger
			//alert('inspect:'+err.message);
		}
	},

	/*---------------------------------------------------------------------------------*/
	/**
	*事件中心，初始化一些事件相关函数
	*/
	initEventCenter : function(){
		ViewInfos.initEventCenter0();
		ViewInfos.dblclickElement = function(element, event){
			var viewDesc = $('txt_dispname_' + element.getAttribute('_ObjectId')).innerHTML;
			ViewInfos.mgr.trace(element.getAttribute('_ObjectId'), {
				viewDesc : encodeURIComponent(viewDesc.unescapeHTML())
			});
		};
	},
	//邦定一些基本事件，只邦定一次的事件
	bindBasicEvents : function(eDaton){
		ViewInfos.registerPageEvents();	
		eDaton = eDaton || $('objectsroot');
		Event.observe(eDaton, 'dblclick', ViewInfos.dispatchEvent.bind(ViewInfos));
		Event.observe(eDaton, 'click', ViewInfos.dispatchEvent.bind(ViewInfos));
		Event.observe(eDaton, 'mousemove', ViewInfos.dispatchEvent.bind(ViewInfos));
	},
	registerPageEvents : function(){
		PageEventHandler.register(ViewInfos);
	},
	/**
	*@friend -> PageEventHandler
	*/
	checkSpecSrcElement : function(_eTarget){
		if (_eTarget != null && _eTarget.type == 'text' && _eTarget.type == 'checkbox')
			return false;
	},
	ctrlN : function(){
		if(isAdmin()){
			ViewInfos.mgr["add"]();
		}
	},	
	ctrlE : function(){
		var selectIndexArray = ViewInfos.getSelectedIds();
		if(selectIndexArray.length == 1) {
			if(ViewInfos.checkRight(1)){
				ViewInfos.mgr.edit(ViewInfos.getSelectedIds()[0], {
					isSingleTable:ViewInfos.isSingleTable(selectIndexArray)
				});
			}
		}
		return false;
	},
	ctrlD : function(event){
		ViewInfos.keyDelete(event || window.event);
	},
	keyDelete : function(event){
		var selectIndexArray = ViewInfos.getSelectedIds();
		if(selectIndexArray.length > 0) {
			if(ViewInfos.checkRight(2)){
				ViewInfos.mgr["delete"](ViewInfos.getSelectedIds());
			}
		}
	},
	keyF2 : function(event){
		if(ViewInfos.getSelectedIds().length == 1) {
			UIEditPanel.edit('txt_dispname_' + ViewInfos.getSelectedIds()[0]);
		}
	},
	keyReturn : function(event){
		if(ViewInfos.getSelectedIds().length == 1) {
			var objectId = ViewInfos.getSelectedIds()[0];
			var viewDesc = $('txt_dispname_' + objectId).innerHTML;
			ViewInfos.mgr.trace(objectId, {
				viewDesc : encodeURIComponent(viewDesc.unescapeHTML())
			});
		}
	},
	checkRight : function(rightIndex, rights){
		var aRights = rights || ViewInfos.getRights();
		for (var i = 0; i < aRights.length; i++){
			if(!isAccessable4WcmObject(aRights[i], rightIndex)){
				$timeAlert('您没有权限执行此操作',5);
				return false;
			}
		}
		return true;
	}
});

Event.observe(window, 'load', function(){
	ViewInfos.loadPage();
	ViewInfos.initEventCenter();
	ViewInfos.bindBasicEvents();

	var arQueryFields = [
		{name: 'queryViewDesc', desc: '视图名称', type: 'string'},
		{name: 'CrUser', desc: '创建者', type: 'string'},
		{name: 'queryViewId', desc: '视图ID', type: 'int'}
	];
	SimpleQuery.register('query_box', arQueryFields, function(_params){
		Object.extend(PageContext.params, _params);
		ViewInfos.loadPage();
	}, true);

	Element.show('query_box');	
});

Event.observe(window, 'resize', function(){
	ViewInfos.setListGridLength();
});

Event.observe(window, 'unload', function(){
	$destroy(window.ViewInfos);
	$destroy(window.PageContext);
});


//---------------------------------------init order start-----------------------------------------------//
function showOrderTypes(_evt){
	_evt = _evt || window.event;
	sOrderTypes = '<div class="listOrders0"><div class="listOrders1"><ul class="listOrders" id="list_orders">\
		<li id="list_default" class="listOrderCurrent"><a href="#" onclick="orderContent(\'\'); return false;">默认排序</a></li>\
		<li id="list_viewdesc"><a href="#" onclick="orderContent(\'ViewDesc\'); return false;">视图名称</a></li>\
		<li id="list_crtime"><a href="#" onclick="orderContent(\'CrTime\'); return false;">创建时间</a></li>\
		<li id="list_cruser"><a href="#" onclick="orderContent(\'CrUser\'); return false;">创建者</a></li></ul></div></div>';
	showHelpTip(_evt, sOrderTypes, false);
	setOrderListStyle(false);
}	
function setOrderListStyle(isChangeStyle){
	var sCurrListId = PageContext.params.orderBy == '' ? 'list_default' : 'list_' + PageContext.params.orderBy.split(" ")[0].toLowerCase();
	var lists = $('list_orders').getElementsByTagName('li');
	for (var i = 0; i < lists.length; i++){
		var list = lists[i];
		if(list.id == sCurrListId) {
			list.className = 'listOrderCurrent';
			continue;
		}
		list.className = 'listOrderOther';
	}
	$('spOrderClue').innerHTML = $(sCurrListId).childNodes[0].innerHTML;
	
	if(!isChangeStyle) return;
	if(sCurrListId != 'list_default'){
		$('spOrderClue').className = 'order_desc';
		$('spOrderClue').style.cursor = 'pointer';
	}else{
		$('spOrderClue').className = '';
		$('spOrderClue').style.cursor = 'default';
	}
}
function orderContent(_sOrderType){
	if(_sOrderType){
		PageContext.params["orderBy"] = _sOrderType + " desc ";
	}else{
		PageContext.params["orderBy"] = '';
	}
	ViewInfos.refreshList();
	setOrderListStyle(true);
	hideHelpTip($('lnkFirer'));
}
function changeOrder(){
	var currOrder = PageContext.params["orderBy"].split(" ")[0];
	if(currOrder == '') return;
	var spOrderClue = $('spOrderClue');
	if(spOrderClue.className == 'order_desc'){
		spOrderClue.className = 'order_asc';
		PageContext.params["orderBy"] = currOrder + " asc ";
		ViewInfos.refreshList();
	}else if(spOrderClue.className == 'order_asc'){
		spOrderClue.className = 'order_desc';
		PageContext.params["orderBy"] = currOrder + " desc ";
		ViewInfos.refreshList();
	}
}
//---------------------------------------init order end-----------------------------------------------//
