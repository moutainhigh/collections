Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	searchEnable : true,
	serviceId : 'wcm61_classinfo',
	methodName : 'TQuery',
	objectType : WCMConstants.OBJ_TYPE_CLASSINFO,
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"orderby" : "crtime desc",
		PageSize : 12
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
		var bIsSite = !!getParameter("SiteId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'classinfoInChannel' :
				(bIsSite ? 'classinfoInSite' : 'classinfoInRoot')
		});
		return context;
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
			activeTabType : 'classinfo'
		});
		return tabs;
	}())
});

Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.CLASSINFO_2 || '个',
	TypeName : wcm.LANG.CLASSINFO_3 || '分类法'
});

window.m_cbCfg = {
	btns : [
		{
			text : wcm.LANG.CLASSINFO_22 || '确定',
			cmd : function(){
				var cbr = wcm.CrashBoarder.get('classInfo_Select');
				cbr.notify($('chkNone').checked ? {selectedIds: [], selectedNames: []} : buildValues());
				cbr.hide();
				cbr.close();
				return false;
			}
		},
		{
			extraCls : 'wcm-btn-refresh',
			text : wcm.LANG.CLASSINFO_16 || '刷新',
			cmd : function(){
				PageContext.loadList(PageContext.params);
				return false;
			}
		},
		{
			extraCls : 'wcm-btn-close',
			text : wcm.LANG.CLASSINFO_24 || '取消'
		}
	]
};

//检索框信息
wcm.ListQuery.register({
	container : 'search', 
	appendQueryAll : true,
	autoLoad : PageContext.searchEnable,
	items : [
		{name: 'queryName', desc: wcm.LANG.CLASSINFO_4 || '分类法名称', type: 'string'},
		{name: 'queryDesc', desc: wcm.LANG.CLASSINFO_5 || '分类法描述', type: 'string'},
		{name: 'CrUser', desc: wcm.LANG.CLASSINFO_6 || '创建者', type: 'string'},
		{name: 'queryId', desc: wcm.LANG.CLASSINFO_7 || '分类法ID', type: 'int'}
	],
	callback : function(params){
		PageContext.loadList(Ext.apply({
			//some params must remember here
		}, params));
	}
});

function buildValues(){
	var selectedIds = [];
	var selectedNames = [];
	var sType = getParameter("selectType") || 'radio';
	var chks = document.getElementsByName('queryId');
	for (var i = 0; i < chks.length; i++){
		var chk = chks[i];
		if(!chk.checked) continue;
		selectedIds.push(chk.value);
		selectedNames.push(chk.getAttribute('_name', 2));
	}	
	return {selectedIds : selectedIds, selectedNames : selectedNames};
}

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
		var selectedIds = "," + getParameter('selId') + ","; 
		var chks = document.getElementsByName('queryId');
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