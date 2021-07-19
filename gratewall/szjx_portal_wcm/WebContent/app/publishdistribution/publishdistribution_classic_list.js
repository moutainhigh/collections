/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	serviceId : 'wcm61_publishdistribution',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"orderby" : "crtime desc",
		"FOLDERTYPE" : PageContext.intHostType,
		"FOLDERID" : PageContext.hostId
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	{
		desc:wcm.LANG.SERVICE_ALL ||'全部服务', 
		type:0,
		fn : function(){
			PageContext.loadList({
				"FILTERTYPE" : 0
			});
		}
	},
	{
		desc:wcm.LANG.SERVICE_USE ||'已启用', 
		type:1,
		fn : function(){
			PageContext.loadList({
				"FILTERTYPE" : 1
			});
		}
	},
	{
		desc:wcm.LANG.SERVICE_STOP ||'未启用',
		type:2,
		fn : function(){
			PageContext.loadList({
				"FILTERTYPE" : 2
			});
		}
	}
]);

/*-------------指定各种情况下右侧的操作面板--------------*/
PageContext.setRelateType(
	!!PageContext.getParameter("ChannelId") ? "publishdistributionInChannel" :
		(!!PageContext.getParameter("SiteId") ? "publishdistributionInSite" : "publishdistributionInRoot")
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.PUBLISHDISTRUBUTION_UNIT || '个',
	TypeName : wcm.LANG.PUBLISHDISTRUBUTION_DISTRIBUTE || '站点分发'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){//单击列表上修改图标时执行的操作
		var myMgr = wcm.domain.PublishDistributionMgr;
		myMgr['edit'](event);
	},
	'delete' : function(event){//单击列表上删除图标时执行的操作
		var myMgr = wcm.domain.PublishDistributionMgr;
		myMgr['delete'](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中;
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'publishdistribution'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION
});

//检索框信息
Event.observe(window, 'load', function(){
	ClassicList.makeLoad();
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'query_box', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : true,
		/*检索项的内容*/
		items : [
			//*/
			{name : 'TARGETTYPE', desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_31 || '分发类型', type : 'string'},
			{name : 'TARGETSERVER', desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_32 || '服务器地址', type : 'string'},
			{name : 'DATAPATH', desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_33 || '分发路径', type : 'string'}
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
	enable : true,
	width : 350,
	doBefore : function(){
		ClassicList.makeLoad();
	}	
});
Ext.apply(PageContext.literator.params, {
	tracesitetype : true,
	tracesite : true
});
ClassicList.cfg = {
	toolbar : [
		{
			id : 'publishdistribution_new',
			fn : function(event, elToolbar){
				wcm.domain.PublishDistributionMgr.add(event);
			},
			name : wcm.LANG.PUBLISHDISTRUBUTION_VALID_24 || '新建',
			rightIndex : 1,
			isHost	: true
		},{
			id : 'publishdistribution_enable',
			fn : function(event, elToolbar){
				wcm.domain.PublishDistributionMgr['enable'](event);
			},
			name : wcm.LANG.PUBLISHDISTRUBUTION_VALID_34 ||'启用',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 1
		},{
			id : 'publishdistribution_disable',
			fn : function(event, elToolbar){
				wcm.domain.PublishDistributionMgr['disable'](event);
			},
			name : wcm.LANG.PUBLISHDISTRUBUTION_VALID_36 ||'禁用',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 1
		}, {
			id : 'publishdistribution_enableall',
			fn : function(event, elToolbar){
				 wcm.domain.PublishDistributionMgr['enableall'](event);
			},
			name : wcm.LANG.PUBLISHDISTRUBUTION_VALID_38 ||'启用所有',
			rightIndex : 1,
			isHost	: true
		}, {
			id : 'publishdistribution_disableall',
			fn : function(event, elToolbar){
				wcm.domain.PublishDistributionMgr['disableall'](event);
			},
			name : wcm.LANG.PUBLISHDISTRUBUTION_VALID_39 ||'禁用所有',
			rightIndex : 1,
			isHost	: true
		}, {
			id : 'publishdistribution_delete',
			fn : function(event, elToolbar){
				wcm.domain.PublishDistributionMgr['delete'](event);
			},
			name : wcm.LANG.PUBLISHDISTRUBUTION_VALID_26 || '删除',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 1
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.PUBLISHDISTRUBUTION_VALID_28 || '刷新'
		}
	],
	listTitle : wcm.LANG.PUBLISHDISTRUBUTION_VALID_30 || '站点分发列表',
	path : ''
}
