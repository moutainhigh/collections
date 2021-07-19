/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm6_regioninfo',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		OrderBy : 'crtime desc',
		containsChildren : true,
		ChannelId : getParameter('ChannelId'),
		WebSiteId : getParameter('SiteId')
	}
});

//检索信息
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'search',
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : true,
		/*检索项的内容*/
		items : [
			{name : 'RName', desc : wcm.LANG.region_list_1000 || '导读名称', type : 'string'},
			{name : 'CrUser', desc : '创建者', type : 'string'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
		   PageContext.loadList(params);

		}
	});
});
//路径信息
Ext.apply(PageContext.literator, {
	enable : true,
	width : 350
});
Ext.apply(PageContext.literator.params, {
	tracesitetype : true
});
/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
]);
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
	!!getParameter("ChannelId")? 'regionInChannel' : 'regionInSite'
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.region_list_2000 || '个',
	TypeName : '导读'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){
		wcm.domain.RegionMgr["editDetail"](event);
	},
	'showEmploy' : function(event){
		wcm.domain.RegionMgr["showEmploy"](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 7,
	activeTabType : 'region'
});

 /*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_REGION,
	getContext : function(){
		var context = this.getContext0();
		Ext.apply(context, {
		
		});
		return context;
	}
});
//是否显示子对象
function switchChildrenDisp(){
	var _eFirer = $("divDispMode");
	if(_eFirer.className =="view_mode_normal"){
		Object.extend(PageContext.params,{"containsChildren":true});
		_eFirer.title = wcm.LANG.region_list_3000 || '点击切换递归显示所有导读\n当前为:递归显示';
		_eFirer.className = "view_mode_recursive";
	}else{
		Object.extend(PageContext.params,{"containsChildren":false});
		_eFirer.title = wcm.LANG.region_list_4000 || '点击切换递归显示所有导读\n当前为:仅显示当前';
		_eFirer.className = "view_mode_normal";
	}
	 PageContext.loadList(PageContext.params);
}
function getWidthOfLiterator(_sLitId){
	var literator = $(_sLitId);
	//get width for the literator.
	var bodyWidth = parseInt(document.body.offsetWidth, 10);
	var sQueryBox =  'search';
	var queryBoxWidth = 300; 
	if($(sQueryBox)){
		var width = parseInt($(sQueryBox).offsetWidth, 10);
		if(width > queryBoxWidth){
			queryBoxWidth = width;
		}
	}
	//alert(queryBoxWidth);
	var previousSibling = getPreviousHTMLSibling(literator);
	var previousWidth = 0;
	if(previousSibling && previousSibling.id != sQueryBox){
		previousWidth = previousSibling.offsetWidth;
	}
	var width = bodyWidth - queryBoxWidth - previousWidth - 250;
	//if($MsgCenter.getActualTop().m_bClassicList || location.href.indexOf('_classic_') > 0)
	//	width = bodyWidth - previousWidth - 200;
	return width > 0 ? width : 30;
}

