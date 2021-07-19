Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : true,
	filterEnable : true,
	draggable : true,
	searchEnable : false,
	listOrderEnable : true,
	serviceId : 'wcm61_photo',
	methodName : 'RecycleQuery',
	/**/
	objectType : WCMConstants.OBJ_TYPE_PHOTORECYCLE,
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"PageSize"	:  parent.m_CustomizeInfo.listPageSize.paramValue || "20"
	}
});

wcm.MyThumbItem = Ext.extend(wcm.ThumbItem, {
	itemType : function(){
		return WCMConstants.OBJ_TYPE_PHOTORECYCLE;
	},
	itemInfo : {
		docId : true,
		itemId : true,
		rightValue : true
	}
});

Ext.apply(wcm.MyThumbItem, {
	type : "photorecycle"
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
			relateType : bIsChannel ? 'photorecycleInChannel' :
				(bIsSite ? 'photorecycleInSite' : 'photorecycleInRoot')
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
				desc:wcm.LANG.PHOTO_CONFIRM_16 || '全部图片', 
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
	_buildParams : function(wcmEvent, actionType, valueDom){
		if(wcmEvent.length() <= 0) return; 
		var obj = wcmEvent.getObjs().getAt(0);
		if(actionType=='save' && wcmEvent.getObjs().getType()==WCMConstants.OBJ_TYPE_PHOTORECYCLE){
			var host = wcmEvent.getHost();
			return {
				Force : {
					ObjectId : obj ? obj.getDocId() : 0
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
	UnitName : wcm.LANG.PHOTO_CONFIRM_7 || '个',
	TypeName : wcm.LANG.PHOTO_CONFIRM_8 || '图片'
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

	wcm.ListOrder.register({
		container : 'list-order-box', 
		appendTip : false,
		autoLoad : PageContext.listOrderEnable,
		items : [
			{name : 'WCMDOCUMENT.crtime', desc : (wcm.LANG.PHOTO_CONFIRM_41 || '创建时间'), isActive : true},
			{name : 'WCMDOCUMENT.doctitle', desc : (wcm.LANG.PHOTO_CONFIRM_42 || '图片标题')},
			{name : 'WCMDOCUMENT.cruser', desc : (wcm.LANG.PHOTO_CONFIRM_43 || '创建者')}
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

function showPhotoSearch(){
	var channelId = getParameter("channelId") || 0 ;
	var siteId = getParameter("siteId") || 0 ;
	wcm.CrashBoarder.get('Dialog_Photo_QuickSearcher').show({
		title : wcm.LANG.PHOTO_CONFIRM_40 || '图片快速检索',
		src : WCMConstants.WCM6_PATH + 'photo/photo_search.htm',
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
//		if(Ext.isGecko){
//			new Insertion.After(_imgloaded, '<div style="position:absolute;width:100%;height:100%;"></div>');		
//		}
	}
}
var isMove = false;
function $openMaxWin(_sUrl, _sName, _bResizable){
	if(isMove) {
		isMove = false;
		return false;
	}
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
	objType : WCMConstants.OBJ_TYPE_PHOTO,
	afteradd : function(event){
		PageContext.loadList(Ext.applyIf({
			CURRDOCID : event.getIds().join(),
			SELECTIDS : ''
		}, PageContext.params));
	}
});

Event.observe(window, 'load', function(){
	if(!PageContext.draggable) return;
	Ext.apply(new wcm.dd.SiteThumbDragDrop({id:'wcm_table_grid'}), {
		_getHint : function(item){
			if(!PageContext.getParameter("channelid")){
					return "<span style='font-weight:bold;background:gray;'>" + (wcm.LANG.PHOTO_CONFIRM_128||"当前图片列表不支持拖动排序") + "</span>";
				}
			var objId = item.getId();
			var siteDesc = $(item["desc_id_prefix"] + objId).innerHTML;
			var showBorder = $(item["cbx_id_prefix"] + objId).checked == true ? 1 : 0;
			var aHtml = [];
			aHtml.push(
				'<div class="thumb">',
					'<div class="thumbDrag">',
						'<img src=' + $("img_src" + objId).src + ' border=' + showBorder + ' alt="" align="absmiddle">',
					'</div>',
				'</div>'
			);
			return aHtml.join("");
		},
		_moveAfter : function(currItem, prevItem){
			if(!PageContext.getParameter("channelid")){
				return false;
			}
			var orderBy = PageContext.params.ORDERBY;
			if(orderBy && orderBy.indexOf("siteorder") < 0) return false;
			if(!confirm(wcm.LANG.PHOTO_CONFIRM_127||"确实要改变图片排列顺序吗？")) return false;
			isMove = true;
			var myThumbList = (PageContext.getThumbList || Ext.emptyFn)() || window.myThumbList;
			var itemDom = Element.first($("wcm_table_grid"));
			var nextDom = Element.next(itemDom);
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

//全选按钮事件绑定
Event.observe($("selectAll"), 'click', function(){
	$("selectAll").checked ? myThumbList.selectAll() : myThumbList.unselectAll();
});

