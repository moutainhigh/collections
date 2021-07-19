Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : true,
	filterEnable : true,
	gridDraggable : false,
	searchEnable : false,
	listOrderEnable : true,
	draggable : true,
	serviceId : 'wcm61_video',
	methodName : 'vQuery',
	/**/
	objectType : WCMConstants.OBJ_TYPE_VIDEO,
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"PageSize"	: "15",
		"ChannelIds" : getParameter("channelId"),
		"SiteIds" : getParameter("siteId"),
		"PageSize" : parent.m_CustomizeInfo.listPageSize.paramValue || "20"
	}
});

wcm.MyThumbItem = Ext.extend(wcm.ThumbItem, {
	itemType : function(){
		return WCMConstants.OBJ_TYPE_VIDEO;
	},
	itemInfo : {
		docId : true,
		itemId : true,
		channelid : true,
		currchnlid : true,
		rightValue : true,
		converting : true,
		convertFlag : true
	}
});

Ext.apply(wcm.MyThumbItem, {
	type : "video"
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
			relateType : bIsChannel ? 'videoInChannel' :
				(bIsSite ? 'videoInSite' : 'videoInRoot')
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
			activeTabType : 'document'
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
	_buildParams : function(wcmEvent, actionType, valueDom){
		if(wcmEvent.length() <= 0) return; 
		var obj = wcmEvent.getObjs().getAt(0);
		if(actionType=='save' && wcmEvent.getObjs().getType()==WCMConstants.OBJ_TYPE_VIDEO){
			var host = wcmEvent.getHost();
			return {
				Force : {
					ObjectId : obj ? obj.getPropertyAsInt("docId", 0) : 0
				},
				ChannelId : PageContext.getParameter("ChannelId") || 0,
				SiteId : PageContext.getParameter("SiteId") || 0
			}
		}else if(actionType=='changestatus'){
			return {
				objectIds : obj.getId(),
				StatusId : valueDom.getAttribute("_fieldValue", 2)
			};
		}
	}
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.VIDEO_CONFIRM_7 || '个',
	TypeName : wcm.LANG.VIDEO_CONFIRM_8 || '视频'
});
//检索框信息
PageContext.searchEnable = true;
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
			{name : 'DOCTITLE', desc : '视频标题', type : 'string'}
		
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(Ext.apply(
				PageContext.params, params));
		}
	});

	wcm.ListOrder.register({
		container : 'list-order-box', 
		appendTip : false,
		autoLoad : PageContext.listOrderEnable,
		items : [
			{name : 'WCMDOCUMENT.crtime', desc : (wcm.LANG.VIDEO_CONFIRM_41 || '创建时间'), isActive : true},
			{name : 'WCMDOCUMENT.doctitle', desc : (wcm.LANG.VIDEO_CONFIRM_42 || '视频标题')},
			{name : 'WCMDOCUMENT.cruser', desc : (wcm.LANG.VIDEO_CONFIRM_43 || '创建者')}
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
	width : 350
});

function showVideoSearch(){
	var channelId = getParameter("channelId") || 0 ;
	var siteId = getParameter("siteId") || 0 ;
	wcm.CrashBoarder.get('Dialog_Video_QuickSearcher').show({
		title : wcm.LANG.VIDEO_CONFIRM_40 || '视频快速检索',
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
	objType : WCMConstants.OBJ_TYPE_VIDEO,
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

Event.observe(window, 'load', function(){
	if(!PageContext.draggable) return;
	var bSortable = true;
	var sRight = PageContext.getParameter("rightValue");
	if(sRight!=null&&!wcm.AuthServer.checkRight(sRight, 62)){
			bSortable = false;
	}
	Ext.apply(new wcm.dd.SiteThumbDragDrop({id:'wcm_table_grid'}), {
		_getHint : function(item){
			if(!PageContext.getParameter("channelid")){
					return "<span style='font-weight:bold;background:gray;'>" + ("当前视频列表不支持拖动排序") + "</span>";
			}
			if(!bSortable){
				return "<span>当前视频没有权限排序</span>";
			}
			var objId = item.getId();
			var siteDesc = $(item["desc_id_prefix"] + objId).innerHTML;
			var showBorder = $(item["cbx_id_prefix"] + objId).checked == true ? 1 : 0;
			var aHtml = [];
			aHtml.push(
				'<div class="thumb">',
					'<div class="thumbDrag">',
						'<img src=' + $("img_src" + objId).src + ' border=' + showBorder + ' alt="" width="100" height="100" align="absmiddle">',
					'</div>',
				'</div>'
			);
			return aHtml.join("");
		},
		_isSortable : function(item){
			return bSortable;
		},
		_moveAfter : function(currItem, prevItem){
			if(!PageContext.getParameter("channelid")){
				return false;
			}
			var bCurrTopped = currItem.getDom().isTopped;
			var orderBy = PageContext.params.ORDERBY;
			isMove = true;
			var myThumbList = (PageContext.getThumbList || Ext.emptyFn)() || window.myThumbList;
			var itemDom = Element.first($("wcm_table_grid"));
			var nextDom = Element.next(itemDom);
           var bTargetTopped = nextDom.getAttribute("isTopped");
		   if("true"==bCurrTopped||"true"==bTargetTopped){
		   Ext.Msg.$timeAlert('置顶视频与非置顶视频间不能交叉排序.',5);
					return false;
		   }
			if(orderBy && orderBy.indexOf("siteorder") < 0) return false;
			if(!confirm("确实要改变视频排列顺序吗？")) return false;
			new com.trs.web2frame.BasicDataHelper().call('wcm61_viewDocument', "changeOrder", {
				FromDocId:currItem.getItemInfo().docId,
				ToDocId: prevItem == null ? nextDom.getAttribute("docid") : prevItem.getItemInfo().docId,
				position:prevItem == null ? 1 : 0,
				channelid: PageContext.getParameter("channelid")
			}, true, function(){
				CMSObj.createFrom(currItem.getItemInfo(), PageContext.getContext()).afteredit();
			});
			return true;
		}
	});
});

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_XTHUMBITEM,
	beforeselect : function(event){
		var context = event.getContext();
		var obj = context.item.getItemInfo();
		return obj.convertFlag != "0";
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
