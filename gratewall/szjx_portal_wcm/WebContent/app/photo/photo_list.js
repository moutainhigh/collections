/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	gridDraggable : false,
	serviceId : 'wcm61_photo',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	{
		desc: wcm.LANG.PHOTO_CONFIRM_16 || '全部图片', 
		type:0,
		fn : function(){
			PageContext.loadList({
				"FILTERTYPE" : 0
			});
		}
	},
	{
		desc: wcm.LANG.PHOTO_CONFIRM_17 || '新稿', 
		type:1,
		fn : function(){
			PageContext.loadList({
				"FILTERTYPE" : 1
			});
		}
	},
	{
		desc: wcm.LANG.PHOTO_CONFIRM_18 || '可发', 
		type:2,
		fn : function(){
			PageContext.loadList({
				"FILTERTYPE" : 2
			});
		}
	},
	{
		desc: wcm.LANG.PHOTO_CONFIRM_19 || '已发', 
		type:3,
		fn : function(){
			PageContext.loadList({
				"FILTERTYPE" : 3
			});
		}
	},
	{
		desc: wcm.LANG.PHOTO_CONFIRM_20 || '已否', 
		type:8,
		fn : function(){
			PageContext.loadList({
				"FILTERTYPE" : 8
			});
		}
	},
	{
		desc: wcm.LANG.PHOTO_CONFIRM_21 || '我创建的', 
		type:4,
		fn : function(){
			PageContext.loadList({
				"FILTERTYPE" : 4
			});
		}
	},
	{
		desc: wcm.LANG.PHOTO_CONFIRM_22 || '最近三天', 
		type:5,
		fn : function(){
			PageContext.loadList({
				"FILTERTYPE" : 5
			});
		}
	},
	{
		desc: wcm.LANG.PHOTO_CONFIRM_23 || '最近一周', 
		type:6,
		fn : function(){
			PageContext.loadList({
				"FILTERTYPE" : 6
			});
		}
	},
	{
		desc: wcm.LANG.PHOTO_CONFIRM_24 || '最近一月', 
		type:7,
		fn : function(){
			PageContext.loadList({
				"FILTERTYPE" : 7
			});
		}
	}
]);

/*-------------指定各种情况下右侧的操作面板--------------*/
PageContext.setRelateType(
	!!PageContext.getParameter("ChannelId") ? "photoInChannel" :
		(!!PageContext.getParameter("SiteId") ? "photoInSite" : "photoInRoot")
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.PHOTO_CONFIRM_7 || '个',
	TypeName : wcm.LANG.PHOTO_CONFIRM_8 || '图片'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){//单击列表上删除图标时执行的操作
		var myMgr = wcm.domain.photoMgr;
		myMgr['edit'](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'photo'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_PHOTO
});

//检索框信息
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

function calGetValue(spId){
	return function(){
		var el = $(spId);
		return el.tagName=='INPUT' ? el.value : el.getAttribute("_fieldValue", 2);
	}
}
function calSetValue(spId){
	return function(v){
		var el = $(spId);
		if(el.tagName=='INPUT'){
			el.value = v;
		}else{
			var inputDom = document.createElement('INPUT');
			inputDom.value = v;
			inputDom.name = spId.split('_')[0];
			wcm.PageOper.transHelper.setCalendarValue(el, inputDom);
		}
	}
}

//路径信息
Ext.apply(PageContext.literator, {
	enable : false,
	width : 350
});