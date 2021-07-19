/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_contentextfield',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"orderby" : "wcmcontentextfield.extorder desc,wcmcontentextfield.CRTIME desc ,wcmcontentextfield.CONTENTEXTFIELDID asc",
		"HOSTTYPE" : PageContext.intHostType,
		"HOSTID" : PageContext.hostId	
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	//{desc:wcm.LANG.CURRPOSITON || '当前位置', type:1}
	//TODO type filters here
]);

/*-------------指定各种情况下右侧的操作面板--------------*/
PageContext.setRelateType(
	!!PageContext.getParameter("ChannelId") ? "contentextfieldInChannel" :
		(!!PageContext.getParameter("SiteId") ? "contentextfieldInSite" : "contentextfieldInRoot")
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.CONTENTEXTFIELD_UNIT || '个',
	TypeName : wcm.LANG.CONTENTEXTFIELD_CONTENTEXTFIELD || '扩展字段'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){//单击列表上修改图标时执行的操作
		var myMgr = wcm.domain.ContentExtFieldMgr;
		myMgr['edit'](event);
	},
	'delete' : function(event){//单击列表上删除图标时执行的操作
		var myMgr = wcm.domain.ContentExtFieldMgr;
		myMgr['delete'](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中;
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'contentextfield'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_CONTENTEXTFIELD,
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		var bIsSite = !!getParameter("SiteId");
		Ext.apply(context, {
			HostType : PageContext.intHostType,
			HostId : PageContext.hostId
		});
		return context;
	}
});

/*-------------操作面板上下文环境信息--------------*/
Ext.apply(PageContext, {
	_buildParams : function(wcmEvent, actionType, valueDom){
		if(wcmEvent.length() <= 0) return; 
		if(actionType=='save'){
			return {
				HostType : PageContext.intHostType,
				HostId : PageContext.hostId
			};
		}
	}
});

Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'query_box', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : true,
		/*检索项的内容*/
		items : [
			{name: 'LogicFieldDesc', desc: wcm.LANG.CONTENTEXTFIELD_OUTNAME || '显示名称', type: 'string', length: 25},
			{name: 'DBFieldName', desc: wcm.LANG.CONTENTEXTFIELD_FIELDNAME || '字段名称', type: 'string', length: 50}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			//alert(Object.parseSource(params));
			PageContext.loadList( Ext.apply( {
				ContainsChildren : PageContext.params ? PageContext.params["CONTAINSCHILDREN"] : false	
			},params));
		}
	});
});
//是否显示子对象
function queryWithChildren(){
	var show_children = $("show_children");
	if(show_children.className == "unfold_mode"){
		Object.extend(PageContext.params,{"ContainsChildren":false});
		show_children.title = wcm.LANG.CONTENTEXTFIELD_SHOWEXT || "显示子对象的扩展字段";
		show_children.className = "fold_mode";
	}else{
		Object.extend(PageContext.params,{"ContainsChildren":true});	
		show_children.title = wcm.LANG.CONTENTEXTFIELD_HIDEEXT || "隐藏子对象的扩展字段";
		show_children.className = "unfold_mode";
	}
	PageContext.loadList(PageContext.params);
};
//绘制路径相关
Ext.apply(PageContext.literator, {
	enable : true,
	width : 350
});
Ext.apply(PageContext.literator.params, {
	tracesitetype : true,
	tracesite : true
});
//重载翻页的处理
Ext.apply(PageContext.PageNav, {
	go : function(_iPage, _maxPage){
		delete PageContext.params["SELECTIDS"];
		PageContext.loadList({
			CurrPage : (_iPage<1)? 1 : ((_iPage > _maxPage) ? _maxPage : _iPage),
			ContainsChildren : PageContext.params['ContainsChildren'.toUpperCase()]
		},null, true);
	}
});
