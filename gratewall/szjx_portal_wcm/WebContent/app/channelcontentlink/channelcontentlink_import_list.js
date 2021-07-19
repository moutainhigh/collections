Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	searchEnable : false,
	serviceId : 'wcm61_channelcontentlink',
	methodName : 'tQuery',
	/**/
	objectType : 'contentlink',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		PageSize : 10
	}
});
Ext.apply(wcm.Grid, {
	rowType : function(){
		return WCMConstants.OBJ_TYPE_CHANNELCONTENTLINK;
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
			relateType : bIsChannel ? 'contentlinkInChannel' :
				(bIsSite ? 'contentlinkInSite' : 'contentlinkInRoot')
		});
		return context;
	},
	/**
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		Ext.applyIf(this.params, {
			HostType : PageContext.intHostType,
			hostId : PageContext.hostId
		});
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	//*/
	/**/
	pageFilters : (function(){
		if(!PageContext.filterEnable)return null;
		var filters = new wcm.PageFilters({
			displayNum : 6,
			filterType : getParameter('FilterType') || 0
		});
		filters.register([
			//TODO type filters here
		]);
		/*
		filters.register({
			desc : '某个状态',
			type : 1001,
			fn : function(){
				PageContext.loadList({
					"DocStatus" : 1
				});
			},
			order : 5
		});
		*/
		return filters;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'contentlink'
		});
		return tabs;
	}()),
	gridFunctions : function(){
		/*
		var myMgr = wcm.domain.ChannelContentLinkMgr;
		wcm.Grid.addFunction('edit', function(event){
			myMgr['edit'](event);
		});
		//*/
	}
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.CHANNELCONTENTLINK_UNIT || '个',
	TypeName : wcm.LANG.CHANNELCONTENTLINK_MGR || '热词分类'
});
//检索框信息
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'search', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : false,
		/*检索项的内容*/
		items : [
//			{name : 'LINKNAME', desc : wcm.LANG.NAME || '热词名称', type : 'string'},
//			{name : 'LINKTITLE', desc : wcm.LANG.DESC || '热词描述', type : 'string'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
	});
});
//路径信息
Ext.apply(PageContext.literator, {
	enable : false,
	width : 350
});
var selectedTypes = [];
var selectOperas = 0;
function selectOpera(_selectOpera){
	selectOperas = _selectOpera.value;
}
function recordValue(_checkbox){
	if(!_checkbox) return;
	var v = _checkbox.value;
	if(_checkbox.checked){
		if(selectedTypes.length == 0)
			FloatPanel.disableCommand('setLinkType', false);
		selectedTypes.push(v);
	}else{
		selectedTypes.remove(v);
		if(selectedTypes.length == 0)
			FloatPanel.disableCommand('setLinkType', true);
	}
}
function setLinkType(){
	importSysLinks();	
	return false;
}
function importSysLinks(){
	if(selectedTypes.length ==0){
		FloatPanel.close();
		return true;
	}else{
		var params = {"ContentLinkTypeIds":selectedTypes,"sameNameOpera":selectOperas,"ChannelId":getParameter('channelid'),"SiteId":getParameter('siteid')};
		BasicDataHelper.Call(PageContext.serviceId,"importSysLinks",params,true,function(_transport,_json){		
			notifyFPCallback(_transport);
			FloatPanel.close(true);		
		});
	}
	return false;
}
function selectAll(){
	var checkBoxes = document.getElementsByName("LinkType");
	if(!checkBoxes)
		return;
	if(selectedTypes.length < checkBoxes.length){
		//清除原来的赋值重新设置
		selectedTypes = [];
		for(var k=0; k<checkBoxes.length;k++){
			checkBoxes[k].checked = true;
			var v = checkBoxes[k].value;
			selectedTypes.push(v);
		}
		$('selectAll').checked = true;
	}else{
		selectedTypes = [];
		for(var k=0; k<checkBoxes.length;k++){
			checkBoxes[k].checked = false;
		}
		$('selectAll').checked = false;
	}
	if(selectedTypes.length == 0){
		FloatPanel.disableCommand('setLinkType', true);
	}else{
		FloatPanel.disableCommand('setLinkType', false);
	}
}

Event.observe(window, 'load', function(){
	FloatPanel.disableCommand('setLinkType', true);
});

$MsgCenter.on({
	objType : 'contentlink',
	afterselect : function(event){
		FloatPanel.disableCommand('setLinkType', false);
	}
});

$MsgCenter.un($MsgCenter.getListener('sys_gridrow'));
$MsgCenter.un($MsgCenter.getListener('sys_gridcell'));
$MsgCenter.un($MsgCenter.getListener('sys_allcmsobjs'));
