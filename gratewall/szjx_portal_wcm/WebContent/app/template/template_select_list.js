Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	searchEnable : true,
	serviceId : 'wcm61_template',
	methodName : 'sQuery',
	objectType : 'template',
	initParams : {
		"FieldsToHTML" : "tempname,tempdesc,folder.name",
		"SelectFields" : "",
		"PageSize" : 12
	}
});

Ext.apply(wcm.Grid, {
	rowType : function(){
		return WCMConstants.OBJ_TYPE_TEMPLATE;
	},
	rowInfo : {
	}
});

Ext.apply(PageContext, {	
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'templateInChannel' : 'templateInSite'
		});
		return context;
	},
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		Ext.applyIf(this.params, {
			OBJECTTYPE : PageContext.intHostType,
			OBJECTID : PageContext.hostId
		});
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	pageFilters : (function(){
		if(!PageContext.filterEnable)return null;
		var filters = new wcm.PageFilters({
			displayNum : 4,
			filterType : getParameter('FilterType') || 0
		});
		return filters;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'template'
		});
		return tabs;
	}())
});

Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.UNIT || '个',
	TypeName : wcm.LANG.TEMPLATE || '模板'
});
function onOk(cb){
	//cb.hide();
	var rst = $('chkNone').checked ? {selectedIds: [], selectedNames: []} : buildValues();
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
				this.hide();
				//debugger
				var rst = $('chkNone').checked ? {selectedIds: [], selectedNames: []} : buildValues();
				//if(!$('chkNone').checked && rst.selectedIds.length==0)return true;
				this.notify(rst);
				return false;
			}
		},
		{
			text : wcm.LANG.REFRESH || '刷新',
			cmd : function(){
				PageContext.loadList(PageContext.params);
				return false;
			}
		},
		{text : wcm.LANG.CANCEL || '取消'}
	]
};

wcm.ListQuery.register({
	/*检索控件追加到的容器*/
	container : 'search', 
	/*是否追加“全部”这个检索项, default to false*/
	appendQueryAll : true,
	/*是否自动加载, default to true*/
	autoLoad : PageContext.searchEnable,
	/*检索项的内容*/
	items : [
		{name : 'TempName', desc : wcm.LANG.TEMPLATE_TEMPNAME||'模板名称', type : 'string'},
		{name : 'TempDesc', desc : wcm.LANG.TEMPLATE_TEMPDESC_0||'模板描述', type : 'string'},
		{name : 'CrUser', desc : wcm.LANG.TEMPLATE_TEMPCRUSER||'创建者', type : 'string'},
		{name : 'TempId', desc : wcm.LANG.TEMPLATE_TEMPID||'模板Id', type : 'int'}
	],
	/*执行检索按钮时执行的回调函数*/
	callback : function(params){
		PageContext.loadList(params);
	}
});

var hasSelected = [];
var hasSelectedNames = [];

function buildValues(){
	var selectedIds = [];
	var selectedNames = [];
	var sType = getParameter("selectType") || 'radio';
	if($('chkNone').checked) return {selectedIds : [], selectedNames : []};
	return {selectedIds : hasSelected, selectedNames : hasSelectedNames};
}

function init(params){
	if(!params){
		hasSelected = [];
		hasSelectedNames = [];
		return;
	}
	hasSelected = (params['templateIds'])?params['templateIds'].split(","):[];
	hasSelectedNames = (params['tempNames'])?params['tempNames'].split(","):[];
}


Event.observe(document, 'click', function(event){
	event = window.event || event;
	var dom = Event.element(event);
	if(dom.className == "sp_name"){
		var _id = dom.getAttribute("_id");
		dom = $('chk_'+_id);
		if($('chk_'+_id).checked){
			if($('chk_'+_id).type!='radio'){
				$('chk_'+_id).checked = false;
			}
		}else{
			$('chk_'+_id).checked = true;
		}
	}
	if(!dom.value) return;
	var type = dom.getAttribute('type');
	if(type != "radio" && type != "checkbox") return;	
	if(type == "radio"){
		hasSelected = [dom.value];
		hasSelectedNames = [dom.getAttribute('_name', 2)];
		return;
	}
	if(dom.checked){
		hasSelected.push(dom.value);
		hasSelectedNames.push(dom.getAttribute('_name', 2));
	}else{
		var index = hasSelected.indexOf(dom.value);
		if(index >= 0){
			hasSelected.splice(index, 1);
			hasSelectedNames.splice(index, 1);
		}
	}
});

Event.observe('unselect', 'click', function(event){
	if($('chkNone').checked){
		$('chkNone').checked = false;
		disableTempSelect(false);
	}else{
		$('chkNone').checked = true;
		disableTempSelect(true);
	}
});

var m_bFirstShowMask = true;
function disableTempSelect(_bFlag){
	if(_bFlag === false) {
		Element.hide('divMask');
		Element.show('divcontent');
	}else{	
		if(m_bFirstShowMask) {
			Position.clone($('divcontent'), $('divMask'));
			m_bFirstShowMask = false;
		}
		Element.hide('divcontent');
		Element.show('divMask');
	}
}

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CURRPAGE,
	afterinit : function(event){
		var selectedIds = "," + hasSelected.join(",") + ","; 
		var chks = document.getElementsByName('TempId');
		for (var i = 0; i < chks.length; i++){
			var chk = chks[i];
			if(selectedIds.indexOf(","+chk.value+",") >= 0){
				chk.checked = true;
			}
		}
	}
});
$MsgCenter.un($MsgCenter.getListener('sys_gridrow'));
$MsgCenter.un($MsgCenter.getListener('sys_gridcell'));
$MsgCenter.un($MsgCenter.getListener('sys_allcmsobjs'));

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CURRPAGE,
	afterinit : function(event){
		setTimeout(function(){
			var dom = $('wcm_table_grid');
			if(!dom) return;
			Element.addClassName(dom, 'fix-ie6-redraw');
			Element.removeClassName(dom, 'fix-ie6-redraw');
		}, 0);
	}
});