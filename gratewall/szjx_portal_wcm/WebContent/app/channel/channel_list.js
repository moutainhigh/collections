/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_channel',
	methodName : 'jListQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	//TODO type filters here
]);

/*-------------指定列表上的检索--------------*/
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		container : 'search', 
		appendQueryAll : true,
		autoLoad : PageContext.searchEnable,
		items : [
			{name : 'chnldesc', desc : wcm.LANG.CHANNEL_DESC||'显示名称', type : 'string'},
			{name : 'chnlname', desc : wcm.LANG.CHANNEL_CHNLNAME||'唯一标识', type : 'string'},
			{name : 'cruser', desc : wcm.LANG.CHANNEL_CRUSER||'创建者', type : 'string'},
			{name : 'id', desc : wcm.LANG.CHANNEL_ID||'栏目ID', type : 'int'}
		],
		callback : function(params){
			PageContext.loadList(params);
		}
	});
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
	!!PageContext.getParameter("ChannelId") ? "channelHost" : "websiteHost"
);

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'preview' : function(event){//单击列表上预览图标时执行的操作
		wcm.domain.ChannelMgr.preview(event);
	},
	'edit' : function(event){//单击列表上标题链接时执行的操作
		wcm.domain.ChannelMgr.edit(event);
	},
	'increasingpublish' : function(event){//单击列表上增量发布执行的操作
		wcm.domain.ChannelMgr.increasingpublish(event);
	}

});


/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.CHANNEL_UNITNAME||'个',
	TypeName : wcm.LANG.CHANNEL_TYPENAME||'栏目'
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'channel'
});


/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_CHANNEL
});

/*-------------记录上附带的信息--------------*/
Ext.apply(wcm.Grid, {
	rowInfo : {
		itemId : true,
		rightValue : true,
		isVirtual : true,
		chnlType : true
	}
});

/*-------------当前栏目的宿主类型--------------*/
Ext.apply(PageContext, {
	hostType : (function(){
		return !!PageContext.getParameter("channelid")?
				WCMConstants.OBJ_TYPE_CHANNELMASTER : WCMConstants.OBJ_TYPE_WEBSITE;
	})()
});



//路径信息
Ext.apply(PageContext.literator, {
	enable : true
});
Ext.apply(PageContext.literator.params, {
	tracesite : true,
	tracesitetype : true
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
			if(!wcm.Grid.contain(objId)) return;
			PageContext.refreshList(PageContext.params, 
				PageContext.getIds ? PageContext.getIds(event) : event.getIds()
			);
		},
		afterdelete : function(event){
			//if event.getIds() obtains list to do, else return;
			var objId = event.getObj().getId();
			if(!wcm.Grid.contain(objId)) return;
			delete PageContext.params["SelectIds"];
			PageContext.loadList(PageContext.params);
		}
	});
}
function listeningForHosts(){
	$MsgCenter.on({
		objType : WCMConstants.OBJ_TYPE_CHANNEL,
		afteredit : function(event){
			//监听从树上过来的消息
			if(PageContext.hostType!=WCMConstants.OBJ_TYPE_CHANNELMASTER)return;
			//if event.getObj().getId() equals hostId to do, else return;
			if(event.getObj().getId()!=PageContext.hostId)return;
			//if list has some selected items may directly return;
			event.getObj().afterselect();
		}
	});
	$MsgCenter.on({
		objType : PageContext.hostType,//WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
		afteredit : function(event){
			var host = event.getObj();
			if(host.getId()!=PageContext.hostId)return;
			if(event.getContext()==null){
				host.setContext(PageContext.getContext());
			}
			host.afterselect();
		}
	});
}
