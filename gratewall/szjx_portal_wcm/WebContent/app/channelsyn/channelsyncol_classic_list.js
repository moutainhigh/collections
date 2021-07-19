/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	serviceId : 'wcm61_channelsyncol',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"CHANNELASTARGET" : true
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	//{desc:wcm.LANG.CURRPOSITON || '当前位置', type:1}
	//TODO type filters here
]);

/*-------------指定各种情况下右侧的操作面板--------------*/
PageContext.setRelateType(
	!!PageContext.getParameter("ChannelId") ? "channelsyncolInChannel" :
		(!!PageContext.getParameter("SiteId") ? "channelsyncolInSite" : "channelsyncolInRoot")
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.CHANNELSYN_UNIT || '个',
	TypeName : wcm.LANG.CHANNELSYN_DOCUMENTSYN || '文档同步'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){//单击列表上修改图标时执行的操作
		var myMgr = wcm.domain.ChannelSynColMgr;
		myMgr['edit'](event);
	},
	'delete' : function(event){//单击列表上删除图标时执行的操作
		var myMgr = wcm.domain.ChannelSynColMgr;
		myMgr['delete'](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中;
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'channelsyn'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_CHANNELSYNCOL
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
		autoLoad : false,
		/*检索项的内容*/
		items : [
			/*
			{name : 'TempDesc', desc : '显示名称', type : 'string'},
			{name : 'TempName', desc : '唯一标识', type : 'string'},
			//*/
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
function convertStatus(){
	location.href = WCMConstants.WCM6_PATH + 'channelsyn/channelsyn_classic_list.html' + location.search;
	return;
}

ClassicList.cfg = {
	toolbar : [
		{
			id : 'channelsyncol_new',
			fn : function(event, elToolbar){
				wcm.domain.ChannelSynColMgr.add(event);
			},
			name : wcm.LANG.CHANNELSYN_VALID_23 ||'新建',
			rightIndex : 13,
			isHost	: true
		}, {
			id : 'channelsyncol_delete',
			fn : function(event, elToolbar){
				wcm.domain.ChannelSynColMgr['delete'](event);
			},
			name : wcm.LANG.CHANNELSYN_VALID_24 ||'删除',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 13
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.CHANNELSYN_VALID_25 ||'刷新'
		}
	],
	listTitle : wcm.LANG.CHANNELSYN_VALID_32 ||'栏目汇总列表',
	path : [
		'<span style="padding-left:8px;cursor:pointer;float:right;" class="mode_class" id="convertor" title="',
		wcm.LANG.CHANNELSYN_VALID_34 || "切换为栏目分发列表" ,
		'" onclick="convertStatus()"></span>'
			].join('')
}
