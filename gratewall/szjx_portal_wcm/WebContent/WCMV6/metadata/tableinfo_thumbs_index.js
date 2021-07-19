/**
* 负责收集和传递页面参数的辅助对象
*/
// 1.定义页面环境
Object.extend(PageContext.params, {
	queryTableAnotherName	: getParameter("queryTableAnotherName") || "",
	queryTableName			: getParameter("queryTableName") || "",
	queryTableId			: getParameter("queryTableId") || "",
	orderBy					: getParameter("orderBy") || "",
	ContainsRight			: true,
	pagesize				: -1,
	fieldsToHtml			: 'tablename,anothername,tabledesc'
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
	mgr				: $tableInfoMgr
};

var TableInfos = Object.extend(new Abstract.ElementContainer(OBJECTS_REL_DEF, OBJECTS_CLZ_DEF), {
	servicesName : 'wcm6_MetaDataDef',
	queryMethodName : 'jQueryDBTableInfos',

	loadPage : function(beforeLoad, afterLoad){
		$endSimpleRB();
		$beginSimpleRB("正在刷新列表,请稍候...");
		(beforeLoad || Prototype.emptyFunction)();
		this.getHelper().Call(this.servicesName, this.queryMethodName, PageContext.params, true, function(_transport, _json){		
			if(_transport.getResponseHeader("Num") != 0){
				Element.update($(TableInfos.sDatonId), _transport.responseText);
				TableInfos.reset();
			}else{
				Element.update($(TableInfos.sDatonId), $('divNoObjectFound').innerHTML);
			}
			TableInfos.inspect([]);
			(afterLoad || Prototype.emptyFunction)();
			$endSimpleRB();		
		});
	},
	refreshList : function(aObjectIds){//保存选中状态刷新列表页面
		var selectedIds = aObjectIds || TableInfos.getSelectedIds();
		TableInfos.loadPage(null, function(){
			TableInfos.toggleCertains(selectedIds);
		});
	},
	updateObject : function(oPostData){//由于修改属性面板导致的更新
		var id = TableInfos.getSelectedIds()[0];
		if(oPostData["AnotherName"]){
			UIEditPanel.setValue("txt_dispname_" + id, oPostData["AnotherName"]);
		}
		var mapping = {
			TableName : [1, "名称:"]
		};
		var key = '';
		for(prop in oPostData){
			key = prop;
			break;
		}
		if(mapping[key]){
			var title = $("tabular_" + id).getAttribute("title");
			var array = title.split("\r");
			array[mapping[key][0]] = mapping[key][1] + oPostData[key];
			$("tabular_" + id).setAttribute("title", array.join("\r"));
		}
	},
	updateDesc : function(_oPostData){
		TableInfos.mgr.save(PageContext.params.objectids.last(), _oPostData, null, function(){
			try{
				$oap().UIEditPanel.setValue("AnotherName", _oPostData["AnotherName"]);
			}catch(err){
				//TODO logger
				//alert('inspect:'+err.message);
			}	
		});
	},
	inspect : function(_arrAllIds){
		try{
			var checkedElmIds = _arrAllIds ? _arrAllIds: TableInfos.getSelectedIds();
			var aRights = [1111111111111111111111111111111111111111111111111];//TableInfos.getRights();
			Object.extend(PageContext.params, {objectids:checkedElmIds});
			$MessageCenter.sendMessage('oper_attr_panel','PageContext.response',"PageContext",
				Object.extend(Object.extend({}, PageContext.params),{"objecttype":"TableInfo","objectrights":aRights}), true, true);
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
		TableInfos.initEventCenter0();
		TableInfos.dblclickElement = function(element, event){
			var tableAnotherName = $('txt_dispname_' + element.getAttribute('_ObjectId')).innerHTML;
			TableInfos.mgr.trace(element.getAttribute('_ObjectId'), {
				tableAnotherName : encodeURIComponent(tableAnotherName.unescapeHTML())
			});
		};
	},
	//邦定一些基本事件，只邦定一次的事件
	bindBasicEvents : function(eDaton){
		TableInfos.registerPageEvents();	
		eDaton = eDaton || $('objectsroot');
		Event.observe(eDaton, 'dblclick', TableInfos.dispatchEvent.bind(TableInfos));
		Event.observe(eDaton, 'click', TableInfos.dispatchEvent.bind(TableInfos));
		Event.observe(eDaton, 'mousemove', TableInfos.dispatchEvent.bind(TableInfos));
	},
	registerPageEvents : function(){
		PageEventHandler.register(TableInfos);
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
			TableInfos.mgr["add"]();
		}
	},	
	ctrlE : function(){
		var selectIndexArray = TableInfos.getSelectedIds();
		if(selectIndexArray.length == 1) {
			if(TableInfos.checkRight(1)){
				TableInfos.mgr.edit(TableInfos.getSelectedIds()[0]);
			}
		}
		return false;
	},
	ctrlD : function(event){
		TableInfos.keyDelete(event || window.event);
	},
	keyDelete : function(event){
		var selectIndexArray = TableInfos.getSelectedIds();
		if(selectIndexArray.length > 0) {
			if(TableInfos.checkRight(2)){
				TableInfos.mgr["delete"](TableInfos.getSelectedIds());
			}
		}
	},
	keyF2 : function(event){
		if(TableInfos.getSelectedIds().length == 1) {
			UIEditPanel.edit('txt_dispname_' + TableInfos.getSelectedIds()[0]);
		}
	},
	keyReturn : function(event){
		if(TableInfos.getSelectedIds().length == 1) {
			var objectId = TableInfos.getSelectedIds()[0];
			var tableAnotherName = $('txt_dispname_' + objectId).innerHTML;
			TableInfos.mgr.trace(objectId, {
				tableAnotherName : encodeURIComponent(tableAnotherName.unescapeHTML())
			});
		}
	},
	checkRight : function(rightIndex, rights){
		var aRights = rights || TableInfos.getRights();
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
	TableInfos.loadPage();
	TableInfos.initEventCenter();
	TableInfos.bindBasicEvents();

	var arQueryFields = [
		{name: 'queryTableAnotherName', desc: '元数据别名', type: 'string'},
		{name: 'queryTableName', desc: '元数据名称', type: 'string'},
		{name: 'queryTableDesc', desc: '元数据描述', type: 'string'},
		{name: 'CrUser', desc: '创建者', type: 'string'},
		{name: 'queryTableId', desc: '元数据ID', type: 'int'}
	];
	SimpleQuery.register('query_box', arQueryFields, function(_params){
		Object.extend(PageContext.params, _params);
		TableInfos.loadPage();
	}, true);

	Element.show('query_box');	
});

Event.observe(window, 'resize', function(){
	TableInfos.setListGridLength();
});

Event.observe(window, 'unload', function(){
	$destroy(window.TableInfos);
	$destroy(window.PageContext);
});

//---------------------------------------init order start-----------------------------------------------//
function showOrderTypes(_evt){
	_evt = _evt || window.event;
	sOrderTypes = '<div class="listOrders0"><div class="listOrders1"><ul class="listOrders" id="list_orders">\
		<li id="list_default" class="listOrderCurrent"><a href="#" onclick="orderContent(\'\'); return false;">默认排序</a></li>\
		<li id="list_anothername"><a href="#" onclick="orderContent(\'AnotherName\'); return false;">元数据别名</a></li>\
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
	TableInfos.refreshList();
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
		TableInfos.refreshList();
	}else if(spOrderClue.className == 'order_asc'){
		spOrderClue.className = 'order_desc';
		PageContext.params["orderBy"] = currOrder + " desc ";
		TableInfos.refreshList();
	}
}
//---------------------------------------init order end-----------------------------------------------//