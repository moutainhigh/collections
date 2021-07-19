Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	searchEnable : true,
	listOrderEnable : true,
	serviceId : 'wcm61_watermark',
	methodName : 'jThumbQuery',
	/**/
	objectType : WCMConstants.OBJ_TYPE_WATERMARK,
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"PageSize"	: parent.m_CustomizeInfo.listPageSize.paramValue || "20"
	}
});

wcm.MyThumbItem = Ext.extend(wcm.ThumbItem, {
	itemType : function(){
		return WCMConstants.OBJ_TYPE_WATERMARK;
	},
	itemInfo : {
		itemId : true,
		rightValue : true
	}
});

Ext.apply(wcm.MyThumbItem, {
	type : "watermark"
});

wcm.ThumbItemMgr.registerType(wcm.MyThumbItem["type"], wcm.MyThumbItem);
var myThumbList = new wcm.ThumbList(wcm.MyThumbItem["type"]);

Ext.apply(PageContext, {
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		var bIsSite = !!getParameter("SiteId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'watermarkInChannel' :
				(bIsSite ? 'watermarkInSite' : 'watermarkInRoot')
		});
		return context;
	},
	//**
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		Ext.applyIf(this.params, {
			LIBID : PageContext.hostId
		});
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	//*/
	/**/
	pageFilters : (function(){
		if(!PageContext.filterEnable)return null;
		var filters = new wcm.PageFilters({
			displayNum : 6,
			filterType : getParameter('FilterType') || 0
		});
		filters.register([
			//TODO type filters here
		]);
		/*
		filters.register({
			desc : '某个状态',
			type : 1001,
			fn : function(){
				PageContext.loadList({
					"DocStatus" : 1
				});
			},
			order : 5
		});
		*/
		return filters;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'watermark'
		});
		return tabs;
	}()),
	gridFunctions : function(){
		/*
		var myMgr = wcm.domain.WatermarkMgr;
		wcm.Grid.addFunction('edit', function(event){
			myMgr['edit'](event);
		});
		//*/
	}
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.WATERMARK_PROCESS_8 || '个',
	TypeName : wcm.LANG.WATERMARK_PROCESS_9 || '水印'
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
			{name : 'WMNAME', desc : wcm.LANG.WATERMARK_PROCESS_28 || '水印名称', type : 'string'},
			{name : 'CRUSER', desc : wcm.LANG.WATERMARK_PROCESS_29 || '创建者', type : 'string'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
	});
	wcm.ListOrder.register({
		container : 'list-order-box', 
		appendTip : false,
		autoLoad : PageContext.listOrderEnable,
		items : [
			{name : 'CRTIME', desc : wcm.LANG.WATERMARK_PROCESS_27 || '创建时间', isActive : true},
			{name : 'WMNAME', desc : wcm.LANG.WATERMARK_PROCESS_28 || '水印名称'},
			{name : 'CRUSER', desc : wcm.LANG.WATERMARK_PROCESS_29 || '创建者'}
		],
		callback : function(sOrder){
			PageContext.loadList(Ext.apply(PageContext.params, {
				orderBy : sOrder,
				SelectIds : myThumbList.getIds()
			}));
		}
	});
});
//路径信息
Ext.apply(PageContext.literator, {
	enable : false,
	width : 350,
	doBefore : function(){
		ClassicList.makeLoad();
	}
});
function resizeIfNeed(_imgloaded){
	if(_imgloaded){
		var height = _imgloaded.height;
		var width = _imgloaded.width;		
		if(height > 124 || width > 97){			
			if(height > width){
				_imgloaded.height = 110;				
				width = 110*width/height;
				height = 110;
			}else{
				_imgloaded.width = 97;					
				height = 97 * height/width;
				width = 97;
			}
		}
		_imgloaded.style.left = Math.floor((180-width)/2)+"px";
		_imgloaded.style.top = Math.floor((150-height)/2)+"px";
	}
}
ClassicList.cfg = {
	toolbar : [
		{
			id : 'watermark_new',
			fn : function(event, elToolbar){
				wcm.domain.WatermarkMgr.add(event);
			},
			name : wcm.LANG.WATERMARK_PROCESS_19 || '上传',
			desc : wcm.LANG.WATERMARK_PROCESS_17 || '上传新水印',
			rightIndex : 32,
			isHost	: true
		}, {
			id : 'watermark_delete',
			fn : function(event, elToolbar){
				wcm.domain.WatermarkMgr['delete'](event);
			},
			name : wcm.LANG.WATERMARK_PROCESS_21 || '删除',
			desc : wcm.LANG.WATERMARK_PROCESS_22 || '删除这个/些水印',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 32
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.WATERMARK_PROCESS_23 || '刷新',
			desc : wcm.LANG.WATERMARK_PROCESS_24 || '刷新列表'
		}
	],
	listTitle : wcm.LANG.WATERMARK_PROCESS_25 || '水印列表管理',
	path : ''
}

//全选按钮事件绑定
Event.observe($("selectAll"), 'click', function(){
	$("selectAll").checked ? myThumbList.selectAll() : myThumbList.unselectAll();
});