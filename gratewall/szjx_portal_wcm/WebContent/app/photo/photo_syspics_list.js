Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	searchEnable : false,
	serviceId : 'wcm61_photo',
	methodName : 'tGetSysPics',
	/**/
	objectType : WCMConstants.OBJ_TYPE_PHOTO,
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"PageSize" : 6
	}
});
wcm.MyThumbItem = Ext.extend(wcm.ThumbItem, {
	itemType : function(){
		return WCMConstants.OBJ_TYPE_PHOTO;
	},
	itemInfo : {
		docId : true,
		itemId : true,
		rightValue : true
	}
});

Ext.apply(wcm.MyThumbItem, {
	type : "photo"
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
			relateType : bIsChannel ? 'photoInChannel' :
				(bIsSite ? 'photoInSite' : 'photoInRoot')
		});
		return context;
	},
	//**
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		Ext.applyIf(this.params, {"SelectIds" : (parent.SelectedPhotoIds || []).join(",")});
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	_doBeforeLoad : function(){
		Ext.apply(this.params, {"SELECTIDS" : (parent.SelectedPhotoIds || []).join(",")});
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
	_buildParams : function(wcmEvent, actionType){
		if(wcmEvent.length() <= 0) return; 
		if(actionType=='save' && wcmEvent.getObjs().getType()==WCMConstants.OBJ_TYPE_PHOTO){
			var obj = wcmEvent.getObjs().getAt(0);
			var host = wcmEvent.getHost();
			return {
				Force : {
					ObjectId : obj ? obj.getDocId() : 0
				},
				ChannelId : PageContext.getParameter("ChannelId") || 0,
				SiteId : PageContext.getParameter("SiteId") || 0
			}
		}
	}
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.PHOTO_CONFIRM_7 || '???',
	TypeName : wcm.LANG.PHOTO_CONFIRM_8 || '??????'
});
//???????????????
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*??????????????????????????????*/
		container : 'query_box', 
		/*???????????????????????????????????????, default to false*/
		appendQueryAll : true,
		/*??????????????????, default to true*/
		autoLoad : true,
		/*??????????????????*/
		items : [
			/*
			//*/
			{name : 'DocTitle', desc : wcm.LANG.PHOTO_CONFIRM_108 ||"??????", type : 'string'},
			{name : 'DocKeywords', desc : wcm.LANG.PHOTO_CONFIRM_109 ||"?????????", type : 'string'}
			//????????????????????????????????????db2????????????sqlFilterForClob
			//{name : 'DocHtmlCon', desc : wcm.LANG.PHOTO_CONFIRM_110 ||"??????", type : 'string'}
		],
		/*??????????????????????????????????????????*/
		callback : function(params){
			//alert(Ext.toSource(params));
			var searchKey="",searchValue="";
			if(params.isor){
				if(params.DocTitle != ""){
					searchValue = params.DocTitle;
				}else{
					PageContext.loadList(PageContext.params);
				}
				searchKey = ["DOCTITLE","DOCKEYWORDS"];
			}else if(params.DocTitle){
				searchValue = params.DocTitle;
				searchKey = "DOCTITLE";
			}else if(params.DocKeywords){
				searchValue = params.DocKeywords;
				searchKey = "DOCKEYWORDS";
			}else if(params.DocHtmlCon){
				searchValue = params.DocHtmlCon;
				searchKey = "DOCHTMLCON";				
			}else{
				searchKey = "";
				searchValue = "";
			}
			var oPostData = {"SEARCHKEY":searchKey,"SEARCHVALUE":searchValue};
			Object.extend(PageContext.params,oPostData);
			PageContext.loadList(oPostData);
		}
	});
});
//????????????
Ext.apply(PageContext.literator, {
	enable : false,
	width : 350
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
function RefreshSelected(){
	var arrDocIds = document.getElementsByName("AppendixId");
	for(var i=0;i<arrDocIds.length;i++){
		arrDocIds[i].checked = parent.SelectedPhotoIds.include(arrDocIds[i].value);
	}
}
//????????????/??????????????????
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_XTHUMBLIST,
	selectedchange : function(event){
		var arrDocIds = document.getElementsByName("AppendixId");
		for(var i=0;i<arrDocIds.length;i++){
			var eTmp = arrDocIds[i];
			var sPhotoSrc = eTmp.getAttribute("photo_srcs",2);
			var bIncluded = parent.SelectedPhotoIds.include(eTmp.value);
			if(eTmp.checked&&!bIncluded){
				parent.SetPhotoSelected(eTmp.value,sPhotoSrc,true);
			}
			else if(!eTmp.checked&&bIncluded){
				parent.SetPhotoSelected(eTmp.value,sPhotoSrc,false);
			}
		}
		return false;
	}
});
//?????????????????????????????????
function showDetail(_sSrc,_nId){
	var postParams ={PicName:_sSrc,PhotoId:_nId};
	var targetUrl = "photo_syspic_showdetail.jsp?" + $toQueryStr(postParams);				
	if(document.all && window.external){ //IE
		window.showModalDialog(targetUrl,'',"dialogWidth:530px;dialogHeight:506px;status:0;resizable:0;help:0;center:1;");
	}
	else{
		window.open(targetUrl);
	}
}

//????????????????????????
Event.observe($("selectAll"), 'click', function(){
	$("selectAll").checked ? myThumbList.selectAll() : myThumbList.unselectAll();
});

$MsgCenter.un($MsgCenter.getListener('sys_gridrow'));
$MsgCenter.un($MsgCenter.getListener('sys_gridcell'));
$MsgCenter.un($MsgCenter.getListener('sys_allcmsobjs'));
