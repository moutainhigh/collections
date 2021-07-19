Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : true,
	filterEnable : true,
	gridDraggable : false,
	searchEnable : false,
	listOrderEnable : true,
	serviceId : 'wcm61_video',
	methodName : 'videoRecycleQuery',
	/**/
	objectType : WCMConstants.OBJ_TYPE_VIDEORECYCLE,
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"PageSize"	: "15",
		"ChannelIds" : getParameter("channelId"),
		"SiteIds" : getParameter("siteId")
	}
});

wcm.MyThumbItem = Ext.extend(wcm.ThumbItem, {
	itemType : function(){
		return WCMConstants.OBJ_TYPE_VIDEORECYCLE;
	},
	itemInfo : {
		docId : true,
		itemId : true,
		channelid : true,
		currchnlid : true,
		rightValue : true
	}
});

Ext.apply(wcm.MyThumbItem, {
	type : "videorecycle"
});

wcm.ThumbItemMgr.registerType(wcm.MyThumbItem["type"], wcm.MyThumbItem);
var myThumbList = new wcm.ThumbList(wcm.MyThumbItem["type"]);
wcm.ThumbItemMgr.createListeners(wcm.ThumbItem, function(){
	this.on('beforeclick', function(event){
		var dom = Event.element(event);
		while(dom && dom.tagName != 'BODY'){
			if(dom.getAttribute('itemId')) return true;
			if(Element.hasClassName(dom, 'thumb')){
				return false;
			}
			dom = dom.parentNode;
		}
		return true;
	});
});
wcm.ThumbItemMgr.createListeners(wcm.ThumbItem, function(){
	this.on('click', function(event){
		var box = $('wcm_table_grid');
		var dom = Element.first(box);
		while(dom){
			if(wcm.ThumbItemMgr.isThumbItem(dom)){
				var thumbItem = myThumbList.get(dom);
				if(thumbItem.isActive()){
					$("selectAll").checked = false;
					break;
				}
			}
			dom = Element.next(dom);
		}
	});
});
Ext.apply(PageContext, {
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		var bIsSite = !!getParameter("SiteId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'videorecycleInChannel' :
				(bIsSite ? 'videorecycleInSite' : 'videorecycleInRoot')
		});
		return context;
	},
	/**
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		Ext.applyIf(this.params, {
			HostType : PageContext.intHostType,
			hostId : PageContext.hostId
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
			{
				desc:wcm.LANG.VIDEO_CONFIRM_16 || '全部视频', 
				type:0,
				fn : function(){
					PageContext.loadList({
						"FILTERTYPE" : 0
					});
				}
			},
			{
				desc: wcm.LANG.VIDEO_CONFIRM_17 || '新稿', 
				type:1,
				fn : function(){
					PageContext.loadList({
						"FILTERTYPE" : 1
					});
				}
			},
			{
				desc: wcm.LANG.VIDEO_CONFIRM_18 || '可发', 
				type:2,
				fn : function(){
					PageContext.loadList({
						"FILTERTYPE" : 2
					});
				}
			},
			{
				desc: wcm.LANG.VIDEO_CONFIRM_19 || '已发', 
				type:3,
				fn : function(){
					PageContext.loadList({
						"FILTERTYPE" : 3
					});
				}
			},
			{
				desc: wcm.LANG.VIDEO_CONFIRM_20 || '已否', 
				type:8,
				fn : function(){
					PageContext.loadList({
						"FILTERTYPE" : 8
					});
				}
			},
			{
				desc: wcm.LANG.VIDEO_CONFIRM_21 || '我创建的', 
				type:4,
				fn : function(){
					PageContext.loadList({
						"FILTERTYPE" : 4
					});
				}
			},
			{
				desc: wcm.LANG.VIDEO_CONFIRM_22 || '最近三天', 
				type:5,
				fn : function(){
					PageContext.loadList({
						"FILTERTYPE" : 5
					});
				}
			},
			{
				desc: wcm.LANG.VIDEO_CONFIRM_23 || '最近一周', 
				type:6,
				fn : function(){
					PageContext.loadList({
						"FILTERTYPE" : 6
					});
				}
			},
			{
				desc: wcm.LANG.VIDEO_CONFIRM_24 || '最近一月', 
				type:7,
				fn : function(){
					PageContext.loadList({
						"FILTERTYPE" : 7
					});
				}
			}
		]);
		return filters;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'docrecycle'
		});
		return tabs;
	}()),
	gridFunctions : function(){
		/*
		var myMgr = wcm.domain.photoMgr;
		wcm.Grid.addFunction('edit', function(event){
			myMgr['edit'](event);
		});
		//*/
	},
	_buildParams : function(wcmEvent, actionType){
		if(wcmEvent.length() <= 0) return; 
		if(actionType=='save' && wcmEvent.getObjs().getType()==WCMConstants.OBJ_TYPE_VIDEORECYCLE){
			var obj = wcmEvent.getObjs().getAt(0);
			var host = wcmEvent.getHost();
			return {
				Force : {
					ObjectId : obj ? obj.getPropertyAsInt("docId", 0) : 0
				},
				ChannelId : PageContext.getParameter("ChannelId") || 0,
				SiteId : PageContext.getParameter("SiteId") || 0
			}
		}
	}
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.VIDEORECYCLE_UNIT || '个',
	TypeName : wcm.LANG.VIDEORECYLE || '视频'
});

Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
	
		container : 'search', 
		appendQueryAll : true,
		autoLoad : PageContext.searchEnable,
		items : [
			/*
			{name : 'TempDesc', desc : '鏄剧ず鍚嶇О', type : 'string'},
			{name : 'TempName', desc : '鍞竴鏍囪瘑', type : 'string'},
			//*/
		],
		/*鎵ц妫??绱㈡寜閽椂鎵ц鐨勫洖璋冨嚱鏁??*/
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
			{name : 'WCMDOCUMENT.crtime', desc : (wcm.LANG.VIDEORECYCLE_CONFIRM_41 || '创建时间'), isActive : true},
			{name : 'WCMDOCUMENT.doctitle', desc : (wcm.LANG.VIDEORECYCLE_CONFIRM_42 || '视频标题')},
			{name : 'WCMDOCUMENT.cruser', desc : (wcm.LANG.VIDEORECYCLE_CONFIRM_43 || '创建者')}
		],
		callback : function(sOrder){
			PageContext.loadList(Ext.apply(PageContext.params, {
				orderBy : sOrder,
				SelectIds : myThumbList.getIds()
			}));
		}
	});
});
//璺緞淇℃伅
Ext.apply(PageContext.literator, {
	enable : false,
	width : 350
});

function showVideoSearch(){
	var channelId = getParameter("channelId") || 0 ;
	var siteId = getParameter("siteId") || 0 ;
	wcm.CrashBoarder.get('Dialog_Video_QuickSearcher').show({
		title : wcm.LANG.VIDEORECYCLE_CONFIRM_40 || '视频快速检索',
		src : WCMConstants.WCM6_PATH + 'video/video_search.htm',
		width: '400px',
		height: '350px',
		top : '40px',
		left : '750px',
		reloadable : false,
		params : [channelId, siteId],
		maskable : true,
		callback : function(_args){	
			if(!_args) return;	
			var queryData = {};
			if(_args["DocStatus"] == 1){
				queryData["DocStatus"] = "10";
			}else if(_args["DocStatus"] == 2){
				queryData["NotDocStatus"] = "10";
			}
			delete _args["DocStatus"];		
			Object.extend(queryData,_args);
			if(PageContext.params["DOCSTATUS"])
				delete PageContext.params["DOCSTATUS"];
			if(PageContext.params["NOTDOCSTATUS"])
				delete PageContext.params["NOTDOCSTATUS"];
			//alert(Ext.toSource(PageContext.params));
			PageContext.loadList(Ext.apply(PageContext.params,queryData));
		}
	});
}
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
function $openMaxWin(_sUrl, _sName, _bResizable){
	var nWidth	= window.screen.width - 12;//document.body.clientWidth;
	var nHeight = window.screen.height - 60;//document.body.clientHeight;
	var nLeft	= 0;//(window.screen.availWidth - nWidth) / 2;
	var nTop	= 0;//(window.screen.availHeight - nHeight) / 2;
	var sName	= _sName || "";

	var oWin = window.open(_sUrl, sName, "resizable=" + (_bResizable == true ? "yes" : "no") + ",top=" + nTop + ",left=" + nLeft + ",menubar =no,toolbar =no,width=" + nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no");
	if(oWin)oWin.focus();
}

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

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_VIDEORECYCLE,
	afteradd : function(event){
		PageContext.loadList(Ext.applyIf({
			CURRDOCID : event.getIds().join(),
			SELECTIDS : ''
		}, PageContext.params));
	}
});

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_MAINPAGE,
	afterinit : function(event){
		checkConvertingVideos();
	}
});  