Object.extend(PageContext,{	
	isLocal : false,	
	ObjectServiceId : 'wcm6_photo',
	ObjectMethodName : 'query',	
	AbstractParams : {
//		"SelectFields" : "DocTitle,DocId,CrTime,CrUser,DocChannel,DOCSTATUS,DocType,ATTRIBUTE,DocRelWords,DocAbstract,DocPeople,DocPlace",
		"PageSize" : 9,
		"ChnlDocSelectFields" : "WCMChnlDoc.DOCID,WCMChnlDoc.ChnlId,WCMChnlDoc.DocStatus,WCMChnlDoc.DocChannel,WCMChnlDoc.Modal,WCMChnlDoc.RecId",
		"DocumentSelectFields" : "DOCID,DocTitle,DocType,CrUser,CrTime,DocRelWords,DocAbstract,DocPeople,DocPlace,ATTRIBUTE,DocChannel,DOCSTATUS"
	},
	_doBeforeRefresh : function(_params){
		Object.extend(PageContext.params, {
			channelids: PageContext.params['channelid'],
			siteids: PageContext.params['siteid']
		});
		if(PageContext.params['channelid']){
			var sRightValue = PageContext.params['RightValue'];
			if(isAccessable4WcmObject(sRightValue,31)){
				Element.show('tb_newphoto');
			}
			else{
				Element.hide('tb_newphoto');
			}
		}
		else{
			Element.hide('tb_newphoto');
		}
	},
	addNewPhoto : function(){
		var chnlId = PageContext.params['channelid']||0;
		window.showModalDialog("../photo/photo_upload_editor.html?DocId=0&ChannelId="+chnlId,window,
			"dialogWidth:560px;dialogHeight:420px;status:0;resizable:0;help:0;center:1;")
	},
	QuickSearch : function(){
		m_oQuickSearcher.openSearcher(PageContext.params);
		/*
		var siteId = PageContext.params['siteid']||0;
		var chnlId = PageContext.params['channelid']||0;
		window.showModalDialog("../photo/photo_search_editor.html?SiteId="+siteId+"&ChannelId="+chnlId,window,
			"dialogWidth:740px;dialogHeight:630px;status:0;resizable:0;help:0;center:1;")
		*/
	},
	DoSearch : function(_oArgs){
		if(this.params['channelid']){
			Object.extend(this.params,{
				ChannelIds : this.params['channelid']||0
			});
		}
		else{
			Object.extend(this.params,{
				SiteIds : this.params['siteid']
			});
		}
		Object.extend(this.params,_oArgs);
		this.RefreshList();
	},
	DoRefresh : function(){
		/*
		this.params = {
			ChannelId : this.params['channelid']||0,
			ChannelIds : this.params['channelid']||0,
			SiteId : this.params['siteid']||0,
			SiteIds : this.params['siteid']||0,
			RightValue : this.params['RightValue']
		}
		Object.extend(this.params,this.AbstractParams);
		*/
		this.RefreshList();
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '幅',
	TypeName : '图片'
});
Object.extend(Grid,{
	draggable : false,
	_getSelectAbleClass : function(_eRow){
		return 'grid_selectable_row';
	},
	_exec : function(_sFunctionType,_sRowId,_eRow,_eSrcElement){
		var sFunctionType = _sFunctionType.toLowerCase();
		switch(sFunctionType){
			case 'photo_select':
//				var sPhotoSrcs = _eRow.getAttribute("photo_srcs",2);
//				parent.window.SetPhotoSelected(_sRowId,sPhotoSrcs,_eSrcElement.checked);
				break;
			case 'photo_view':
				var chnlId = PageContext.params['channelid']||0;
				var siteId = PageContext.params['siteid']||0;
				var nDocId = _eRow.getAttribute("docid",2);
				if(_IE){
					window.showModalDialog("../photo/photo_show_editor.html?PhotoId="+nDocId+"&ChannelId="+chnlId+"&SiteId="+siteId,"","dialogWidth:336px;dialogHeight:426px;status:0;resizable:0;help:0;center:1;");
				}
				else{
					window.open("../photo/photo_show.html?PhotoId="+nDocId+"&ChannelId="+chnlId+"&SiteId="+siteId);
				}
				break;
		}
		return false;
	},
	_dealWithSelectedRows : function(){
		var arrDocIds = document.getElementsByName("DocId");
		for(var i=0;i<arrDocIds.length;i++){
			var eTmp = arrDocIds[i];
			var sPhotoSrc = eTmp.getAttribute("photo_srcs",2);
			var sPhotoDesc = eTmp.getAttribute("photo_desc",2);
			var bIncluded = parent.SelectedPhotoIds.include(eTmp.value);
			if(eTmp.checked&&!bIncluded){
				parent.SetPhotoSelected(eTmp.value,sPhotoSrc,true,eTmp.getAttribute("docid",2), sPhotoDesc);
			}
			else if(!eTmp.checked&&bIncluded){
				parent.SetPhotoSelected(eTmp.value,sPhotoSrc,false,eTmp.getAttribute("docid",2), sPhotoDesc);
			}
		}
		PageContext.SelectedRowIds = parent.SelectedPhotoIds;
	}
});
function RefreshSelected(){
	var arrDocIds = document.getElementsByName("DocId");
	for(var i=0;i<arrDocIds.length;i++){
		arrDocIds[i].checked = parent.SelectedPhotoIds.include(arrDocIds[i].value);
	}
	PageContext.SelectedRowIds = parent.SelectedPhotoIds;
}
var CURRLIST_ID = "list_crtime";
var ORDERFIELD = "crtime";
var ORDER_DIRECT = " desc";
function showOrderTypes(_evt){
		_evt = _evt || window.event;
		var sOrderTypes = '<div class="listOrders0"><div class="listOrders1">';
		sOrderTypes += '<ul class="listOrders" id="list_orders">';
		sOrderTypes += '<li id="list_crtime"><a href="#" onclick="orderContent(0); return false;" _field="crtime">创建时间</a></li>';
		sOrderTypes += '<li id="list_doctitle"><a href="#" onclick="orderContent(1); return false;">文档标题</a></li>';		
		sOrderTypes += '<li id="list_cruser"><a href="#" onclick="orderContent(2); return false;">创建者</a></li>';		
		sOrderTypes += '</ul></div></div>';

		showHelpTip(_evt, sOrderTypes, false);
		setOrderListStyle();
}

function setOrderListStyle(){		
	$('spOrderClue').innerHTML = $(CURRLIST_ID).childNodes[0].innerHTML;
	var liEls = document.getElementsByTagName("li");
	var liEl = null;
	for(var i=0;i<3;i++){
		liEl = liEls[i];		
		if(liEl.id == CURRLIST_ID){
			liEl.className = "listOrderCurrent";
		}else{
			liEl.className = "listOrderOthers";			
		}
	}
}

function orderContent(_field){	
	if(_field == 1){
		CURRLIST_ID = "list_doctitle";
		ORDERFIELD = "wcmdocument.doctitle";		
	}else if(_field == 2){
		CURRLIST_ID = "list_cruser";
		ORDERFIELD = "wcmchnldoc.cruser";
	}else{
		CURRLIST_ID = "list_crtime";
		ORDERFIELD = "wcmchnldoc.crtime";		
	}

	ORDER_DIRECT = " desc";
	var orderby = ORDERFIELD + ORDER_DIRECT;
	
	Object.extend(PageContext.params,{OrderBy:orderby});
	$('spOrderClue').className = ORDER_DIRECT;
	setOrderListStyle();
	hideHelpTip($('lnkFirer'));
	PageContext.RefreshList();
//	var oHelper = new com.trs.web2frame.BasicDataHelper();
//	oHelper.Call("wcm6_photo","query",PageContext.params,true,PageContext.PageLoaded);
}

function antitone(_evt){
	if(CURRLIST_ID == "list_crtime"){
		ORDERFIELD = "wcmdocument.crtime";				
	}else if(CURRLIST_ID == "list_cruser"){		
		ORDERFIELD = "wcmchnldoc.cruser";
	}else{		
		ORDERFIELD = "wcmdocument.doctitle";				
	}

	if(ORDER_DIRECT.indexOf("desc") != -1){
		ORDER_DIRECT = " asc";
	}else{
		ORDER_DIRECT = " desc";
	}
	$('spOrderClue').className = ORDER_DIRECT;

	var orderby = ORDERFIELD + ORDER_DIRECT;
	Object.extend(PageContext.params,{OrderBy:orderby});
	PageContext.RefreshList();
//	var oHelper = new com.trs.web2frame.BasicDataHelper();
//	oHelper.Call("wcm6_photo","query",PageContext.params,true,PageContext.PageLoaded)
}

function mapFileName(_fn,_ix,_default){
	if(_fn == null || _fn.trim().length == 0){
		return "../images/photo/pic_notfound.gif";
	}

	var fn = _fn.split(",");
	if(fn.length < _ix){
		_ix = 0;
	}

	var r = fn[_ix];
	if(!r){
		//r = fn[0];
		r = "../../file/read_file.jsp?FileName=" +  _default;
		return r;
	}
	return "/webpic/" + r.substr(0,8) + "/" + r.substr(0,10) + "/" + r;
}
function dealWithRight(rightValue,rightIndex){
	return isAccessable4WcmObject(rightValue,rightIndex)?'grid_function="multi"':"disabled";
}
function dealWithRightCss(rightValue,rightIndex){
	return isAccessable4WcmObject(rightValue,rightIndex)?"grid_selectable_row":"grid_selectdisable_row";
}

var DIALOG_PHOTO_QUICKSEARCHER = "Dialog_Photo_QuickSearcher";
QuickSearcher = Class.create("QuickSearcher");
QuickSearcher.prototype = {		
	initialize : function(){
		var mytop = top || top.actualTop;
		mytop.m_eSearcher = TRSDialogContainer.register(DIALOG_PHOTO_QUICKSEARCHER, '图片快速检索', '../photo/photo_search.htm', '400px', '350px', false);		
		mytop.m_eSearcher.onFinished = function(_args){				
			if(!_args) return;			
			var queryData = {};
			if(_args["DocStatus"] == 1){
				queryData["DocStatus"] = "10";
			}else if(_args["DocStatus"] == 2){
				queryData["NotDocStatus"] = "10";
			}
			delete _args["DocStatus"];		
			
			Object.extend(queryData,_args);			
			PageContext.refresh($toQueryStr(queryData));
		}
		
		TRSCrashBoard.setMaskable(true);
	},
	openSearcher : function(_params){	
		var hostParams = {};
		if(_params){			
			hostParams["siteid"] = _params["siteids"] || 0;			
			hostParams["channelid"] = _params["channelids"] || 0;
		}
		
		var positions = Position.getAbsolutePositionInTop(Event.element(Event.findEvent()));
		TRSDialogContainer.display(DIALOG_PHOTO_QUICKSEARCHER, hostParams,positions[0]-200,positions[1]+20);	
	}
};

Event.observe(window,"load",function(){
	m_oQuickSearcher = new QuickSearcher();
});