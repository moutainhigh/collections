/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_viewdocument',
	methodName : 'j4selectQuery',
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	initParams : {
		"FieldsToHTML" : "DocTitle,DocChannel.Name",
		"ChnlDocSelectFields" : "WCMChnlDoc.DOCKIND,WCMChnlDoc.DOCID,WCMChnlDoc.ChnlId,WCMChnlDoc.DocStatus,WCMChnlDoc.DocChannel,WCMChnlDoc.DocOrderPri,WCMChnlDoc.Modal,WCMChnlDoc.RecId",
		"DocumentSelectFields" : "DOCID,DocTitle,DocType,CrUser,CrTime,AttachPic,FLOWOPERATIONMARK",
		"ChannelIds" : getParameter("ChannelId") || "",// 将传入ChannelId转义为ChannelIds参数,确保符合服务的有效性
		"SiteIds" : getParameter("SiteId") || "",
		"CURRPAGE" : "1",
		"_sqlWhere_" : getParameter("positionSetting") ? "docorderpri=0":"",
		"positionSetting" : getParameter("positionSetting") || "",
		"currDocOrder"	: getParameter("currDocOrder") || ""
	}
});

 
/*-------------指定列表上的过滤器--------------*/
var filters = [
	{desc:wcm.LANG['FILTER_DOCUMENT_ALL']||'全部文档', type:0},
	{desc:wcm.LANG['FILTER_NEW']||'新稿', type:1},	
	{desc:wcm.LANG['FILTER_CANPUB']||'可发', type:2},
	{desc:wcm.LANG['FILTER_PUBED']||'已发', type:3},
	{desc:wcm.LANG['FILTER_REJECTED']||'已否', type:8},
	{desc:wcm.LANG['FILTER_MY']||'我创建的', type:4},
	{desc:wcm.LANG['FILTER_LAST3']||'最近三天', type:5},
	{desc:wcm.LANG['FILTER_LASTWEEK']||'最近一周', type:6},
	{desc:wcm.LANG['FILTER_LASTMONTH']||'最近一月', type:7}
];

//高级检索结果页面中去掉可发状态
if(!!PageContext.getParameter("IsSearch")){
	filters.splice(2,1);
}
PageContext.setFilters([
	filters
]);

/*-------------检索框信息--------------*/
PageContext.searchEnable = false;
if(!!PageContext.getParameter("IsSearch")){
	PageContext.searchEnable = false;
}	
function getSearchFieldInfo(queryItem){
	if(arguments.callee.invoked){
		return;
	}
	arguments.callee.invoked = true;
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'search', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : PageContext.searchEnable,
		items : queryItem,
		callback : function(params){
			PageContext.loadList(Ext.apply(PageContext.params, params));
		}
	});
};

 
/*-------------指定各种情况下右侧的操作面板--------------*/
/*
*设置第一块操作面板的类型;
*第二块和第三块面板的类型通过如下方式获取:
*如果列表页面没有选择记录,则为导航树节点对应的类型(如:website,channel);
*如果列表当前选中一条记录,则类型为当前列表的rowType(如:chnldoc);
*如果列表当前选中多条记录,则类型为当前列表的rowType+s(如:chnldocs);
*操作面板中操作的具体定义在文件wcm/app/js/data/opers/xxx.js中;
*/
PageContext.setRelateType(
	!!PageContext.getParameter("IsSearch") ? "docInSearch" :
		(!!PageContext.getParameter("ChannelId") ? "documentInChannel" : "documentInSite")
);
 

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG['DOC_UNIT'] || '篇',
	TypeName : wcm.LANG['DOCUMENT'] || '文档'
});


/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'chnldoc_view' : function(event){//单击列表上查看图标时执行的操作
		wcm.domain.ChnlDocMgr['view'](event);
	}
});
 

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中;
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'document'
});


/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_CHNLDOC
});
 

/*-------------快捷键的定义--------------*/
Ext.apply(PageContext.keyProvider, {
	keyC : function(winEvent){
		if(!wcm.Grid)return;
		if(!window["keyProvider-keyC"]){
			window["keyProvider-keyC"] = new wcm.DelayedTask();
		}
		var keyCode = winEvent.keyCode;
		window["keyProvider-keyC"].delay(200, function(){
			var items = wcm.Grid.getRowInfos(Ext.apply({
					doctitle : true
				}, wcm.Grid.rowInfo));
			var context = Ext.apply({
				allRowInfos : wcm.Grid.getAllRowInfos(wcm.Grid.rowInfo),
				keyCode : keyCode
			}, PageContext.getContext());
			Ext.apply(context, wcm.Grid.info || {});
			var oCmsObjs = CMSObj.createEnumsFrom({
				objType : wcm.Grid.rowType()
			}, context);
			oCmsObjs.addElement(items);
			oCmsObjs.addEvents(['aftercopy']);
			oCmsObjs.aftercopy();
		});
	},
	keyW : function(winEvent){
		PageContext.keyProvider.keyC(winEvent);
	},
	keyV : function(winEvent){
		if(!wcm.Grid)return;
		if(!window["keyProvider-keyV"]){
			window["keyProvider-keyV"] = new wcm.DelayedTask();
		}
		var keyCode = winEvent.keyCode;
		window["keyProvider-keyV"].delay(200, function(){
			var context = Ext.apply({
				keyCode : keyCode
			}, PageContext.getContext());
			var oCmsObjs = CMSObj.createEnumsFrom({
				objType : wcm.Grid.rowType()
			}, context);
			oCmsObjs.addEvents(['afterpaste']);
			oCmsObjs.afterpaste();
		});
	},
	keyQ : function(winEvent){
		PageContext.keyProvider.keyV(winEvent);
	}
});


$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CHNLDOC,
	beforeaftercopy : function(event){
		var context = event.getContext();
		if(context==null)return false;
		var nRightIndex = String.fromCharCode(context.keyCode).toUpperCase()=='C'?34:33;
		var right = PageContext.getParameter("RightValue");
		if(!wcm.AuthServer.hasRight(right, nRightIndex))return false;
	},
	beforeafterpaste : function(event){
		var context = event.getContext();
		if(context==null)return false;
		var nRightIndex = String.fromCharCode(context.keyCode).toUpperCase()=='Q'?31:31;
		var right = PageContext.getParameter("RightValue");
		var bIsVirtual  = PageContext.getParameter("IsVirtual");
		if(bIsVirtual || !wcm.AuthServer.hasRight(right, nRightIndex))return false;
	}
});
 

/*
*监听文档保存时,做的刷新处理
*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	aftersave : function(event){
		var context = event.getContext();
		var chnldocId = context.get('chnldocId');
		PageContext.updateCurrRows(chnldocId);
	}
});


window.m_cbCfg = {
	btns : [
		{
			id : 'btnTrue',
			text : wcm.LANG.document4select_list_1000 || '确定',
			cmd : function(){
				//alert(getParameter("currDocOrder"));
				var oRadioEles = document.getElementsByName("RowId");
				var count = 0;
				var nSelDocPos = 0;
				for (var i = 0; i < oRadioEles.length; i++){
					if(!oRadioEles[i].checked){
						count++;
					}else{
						nSelDocPos = oRadioEles[i].getAttribute("_value");
						if(getParameter("currDocOrder") && getParameter("currDocOrder") +1 < nSelDocPos){
							nSelDocPos = nSelDocPos -1;
						}
					}
				}
				if(count==oRadioEles.length){
					Ext.Msg.alert(wcm.LANG.document4select_list_1001 || "请选择文档");
					return false;
				}
				this.hide();
                this.notify(nSelDocPos);
				return false;
			}
		},
		{
			id : 'btnClose',
			text : wcm.LANG.document4select_list_1002 || '取消',
			cmd : function(){
			}
		}
	]
}