/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	serviceId : 'wcm6_regioninfo',
	methodName : 'jQueryForSelect',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		PageSize : 10,
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
		container : 'query_box',
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : true,
		/*检索项的内容*/
		items : [
			{name : 'RName', desc : wcm.LANG.region_select_list_1000 || '导读名称', type : 'string'},
			{name : 'CrUser', desc : '创建者', type : 'string'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
		   PageContext.loadList(params);

		}
	});
});
/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.region_select_list_2000 || '个',
	TypeName : '导读'
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
function submitData(){
	var currRegionName = "";//getParameter("regionName");
	var currRegionId = getParameter("regionId");
	var arrEles = document.getElementsByName('RowId');
	for (var i = 0; i < arrEles.length ; i++){
		var currEl = arrEles[i];
		if(!currEl.checked) continue;
		currRegionId = currEl.value;
		currRegionName = currEl.getAttribute("_regionname");
		break;
	}
	var regionInfo = {
		RegionId : currRegionId,
		RegionName : currRegionName
	}
	var cb = top.wcm.CrashBoarder.get(window);
	cb.notify(regionInfo);
}
window.m_cbCfg = {
	btns : [
		 {
			text : wcm.LANG.region_select_list_3000 || '确定',
			cmd : function(){
				submitData();
				return false;
			}
		},{
			text : wcm.LANG.region_select_list_4000 || '取消',
			extraCls : 'wcm-btn-close'//专题中使用该名称控制取消按钮的样式
		}
	]
}
