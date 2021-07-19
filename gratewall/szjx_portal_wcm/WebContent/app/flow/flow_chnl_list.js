/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, { 
	serviceId : 'wcm61_flow',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		OrderBy : 'crtime desc',
		containsChildren : true,
		OwnerType : PageContext.intHostType,
		OwnerId : PageContext.hostId
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	//TODO type filters here
]);

/*-------指定列表上的当前位置----------------*/
Ext.apply(PageContext.literator.params, {
	tracesitetype : true,
	tracesite : true
});
Ext.apply(PageContext.literator, {
	enable : true,
	width : 350
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
	!!getParameter("ChannelId")? 'flowInChannel' : 
		(!!PageContext.getParameter("siteid")?'flowInSite':'flowInSys')
);

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'flow'
});

 /*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_FLOW,
	getContext : function(){
		var context = this.getContext0();
		Ext.apply(context, {
			OwnerType : PageContext.intHostType,
			OwnerId : PageContext.hostId
			
		});
		return context;
	}
});

Ext.apply(PageContext, {
	_doBeforeLoad : function(){
		var bIsChannel = !!getParameter("ChannelId");
		if(bIsChannel){
			this.serviceId = WCMConstants.WCM6_PATH + 'flow/flow_employee_view.jsp';
		}else{
			this.serviceId = 'wcm61_flow';
		}
	}
});
Ext.apply(wcm.Grid, {
	initInChannel : function(info){
		var context = Ext.apply({sysOpers : wcm.SysOpers}, PageContext.getContext());
		var oCmsObjs = CMSObj.createEnumsFrom({
			objType : info.objType
		}, context);
		if(info.objId!=0){
			oCmsObjs.addElement(CMSObj.createFrom(info));
		}
		oCmsObjs.afterselect();
	}
});

