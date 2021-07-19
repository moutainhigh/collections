Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	searchEnable : true,
	serviceId : 'wcm61_metaview',
	methodName : getParameter('methodname') || 'jQuery',
	objectType : 'metaview',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		 PageSize : 12,
		"MetaViewId" : getParameter('MetaViewId') || 0
	}
});

Ext.apply(wcm.Grid, {
	rowType : function(){
		return WCMConstants.OBJ_TYPE_METAVIEW;
	},
	rowInfo : {
	}
});

Ext.apply(PageContext, {	
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		var bIsSite = !!getParameter("SiteId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'metaviewInChannel' :
				(bIsSite ? 'metaviewInSite' : 'metaviewInRoot')
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
			displayNum : 6,
			filterType : getParameter('FilterType') || 0
		});
		filters.register([
			//TODO type filters here
		]);
		return filters;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'metaview'
		});
		return tabs;
	}())
});

Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.METAVIEW_UNITNAME||'个',
	TypeName : wcm.LANG.METAVIE_TYPENAME||'视图'
});

window.m_cbCfg = {
	btns : [
		{
			text : wcm.LANG.TRUE || '确定',
			cmd : function(){
				var cbr = wcm.CrashBoarder.get(window);
				cbr.hide();
				var params = $('chkNone').checked ? {selectedIds: [], selectedNames: []} : buildValues();
				Object.extend( params,{ContainsChildren: $('ContainsChildren').checked} );
				cbr.notify(params);
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

Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'search', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : PageContext.searchEnable,
		/*检索项的内容*/
		items : [
			{name: 'queryViewDesc', desc: wcm.LANG.METAVIEW_VIEWDESC||'视图名称', type: 'string'},
			{name: 'CrUser', desc: wcm.LANG.METAVIEW_CRUSER||'创建者', type: 'string'},
			{name: 'queryViewId', desc: wcm.LANG.METAVIEW_VIEWID||'视图ID', type: 'int'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(params);
		}
	});
});

var hasSelected = [];
var hasSelectedNames = [];

function init(params){
	if(!params){
		hasSelected = [];
		hasSelectedNames = [];
		return;
	}
	if(params['ContainsChildrenBox'] != null && !params['ContainsChildrenBox']){//默认为false
		$('ContainsChildrenBox').style.display = 'none';
	}
	var selectIds = new String(params['selectIds']);
	hasSelected = (selectIds) ? (selectIds.split(",")):[];
	hasSelectedNames = [];
}

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CURRPAGE,
	afterinit : function(event){
		var selectedIds = "," + hasSelected.join(",") + ","; 
		var chks = document.getElementsByName('ViewId');
		for (var i = 0; i < chks.length; i++){
			var chk = chks[i];
			if(selectedIds.indexOf(","+chk.value+",") >= 0){
				chk.checked = true;
			}
		}
	}
});


Event.observe(document, 'click', function(event){
	event = window.event || event;
	var dom = Event.element(event);
	if(dom.className == "sp_name"){
		var _id = dom.getAttribute("_id");
		dom = $('chk_'+_id);
		if(dom.disabled){
			return;
		}
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


function buildValues(){
	var selectedIds = [];
	var selectedNames = [];
	var sType = getParameter("selectType") || 'radio';
	var chks = document.getElementsByName('ViewId');
	for (var i = 0; i < chks.length; i++){
		var chk = chks[i];
		if(!chk.checked) continue;
		selectedIds.push(chk.value);
		selectedNames.push(chk.getAttribute('_name', 2));
	}	
	return {ViewId : selectedIds, selectedNames : selectedNames};
}

var m_bFirstShowMask = true;
function disableViewSelect(_bFlag){
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


$MsgCenter.un($MsgCenter.getListener('sys_gridrow'));
$MsgCenter.un($MsgCenter.getListener('sys_gridcell'));
$MsgCenter.un($MsgCenter.getListener('sys_allcmsobjs'));

//解决ie7 窗口重绘问题：初始界面一些效果不起作用，但拖动一下后又起作用了。
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CURRPAGE,
	afterinit : function(event){
		setTimeout(function(){
			var dom = $('wcm_table_grid');
			if(!dom) return;
			Element.addClassName(dom, 'fix-ie7-redraw');
			Element.removeClassName(dom, 'fix-ie7-redraw');
		}, 0);
	}
});