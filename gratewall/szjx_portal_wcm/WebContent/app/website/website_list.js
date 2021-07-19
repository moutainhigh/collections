/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_website',
	methodName : 'jListQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"HostType" : PageContext.intHostType,
		"hostId" : PageContext.hostId
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	//TODO type filters here
]);

/*-------------指定列表上的检索--------------*/
wcm.ListQuery.register({
	container : 'search', 
	appendQueryAll : true,
	autoLoad : PageContext.searchEnable,
	items : [
		{name : 'sitedesc', desc : wcm.LANG.WEBSITE_SITEDESC||'显示名称', type : 'string'},
		{name : 'sitename', desc : wcm.LANG.WEBSITE_SITENAME||'唯一标识', type : 'string'},
		{name : 'cruser', desc : wcm.LANG.WEBSITE_CRUSER||'创建者', type : 'string'},
		{name : 'id', desc : wcm.LANG.WEBSITE_ID||'站点ID', type : 'int'}
	],
	callback : function(params){
		delete PageContext.params["SelectIds"];
		PageContext.loadList(Ext.apply(PageContext.params, params));
	}
});

/*-------------指定各种情况下右侧的操作面板--------------*/
/*
*设置第一块操作面板的类型；
*第二块和第三块面板的类型通过如下方式获取：
*如果列表页面没有选择记录，则为导航树节点对应的类型(如：website,channel);
*如果列表当前选中一条记录，则类型为当前列表的rowType(如：chnldoc);
*如果列表当前选中多条记录，则类型为当前列表的rowType+s(如：chnldocs);
*操作面板中操作的具体定义在文件wcm/app/js/data/opers/xxx.js中；
*/
PageContext.setRelateType(
	'websiteInRoot'
);
 

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.WEBSITE_UNITNAME||'个',
	TypeName : wcm.LANG.WEBSITE_TYPENAME||'站点'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'preview' : function(event){//单击列表上预览图标时执行的操作
		wcm.domain.WebSiteMgr.preview(event);
	},
	'edit' : function(event){//单击列表上标题链接时执行的操作
		wcm.domain.WebSiteMgr.edit(event);
	},
	'increasepublish' : function(event){//单击列表上增量发布执行的操作
		wcm.domain.WebSiteMgr.increasepublish(event);
	}

});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'website'
});


/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_WEBSITE
});



//路径信息
Ext.apply(PageContext.literator, {
	enable : true
});
Ext.apply(PageContext.literator.params, {
	tracesite : true
});

function listeningForMyObjs(){
	$MsgCenter.on({
		objType : PageContext.objectType,//WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
		afteradd : function(event){
			//if event.getHost()==PageContext.hostType\hostId then to do, else return;
			var host = event.getHost();
			if(PageContext.hostType != host.getType() 
					|| PageContext.hostId != host.getId()){
				return;
			}
			PageContext.refreshList(PageContext.params, [event.getIds()]);
		},
		afteredit : function(event){
			//if event.getIds() obtains list to do, else return;
			var objId = event.getObj().getId();
			if(!myThumbList.contain(objId)) return;
			PageContext.refreshList(PageContext.params, 
				PageContext.getIds ? PageContext.getIds(event) : event.getIds()
			);
		},
		afterdelete : function(event){
			//if event.getIds() obtains list to do, else return;
			var objId = event.getObj().getId();
			if(!myThumbList.contain(objId)) return;
			PageContext.loadList(PageContext.params);
		}
	});
}
