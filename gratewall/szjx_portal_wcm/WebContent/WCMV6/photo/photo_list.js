Object.extend(PageContext,{
	//是否将数据提供者切换到本地的开关
//	isLocal : true,
	isLocal : false,
	
	//远程服务的相关属性
	ObjectServiceId : 'wcm6_photo',
	ObjectMethodName : 'query',	
	ObjectType : 'photo',
	AbstractParams : {
			"SelectFields" : ""			
	},//服务所需的参数

	//页面过滤器的相关配置
	PageFilterDisplayNum : (top.$personalCon.listFilterSize || {}).paramValue || 6,
	PageFilters : (function(){
		var filters = [
			{Name:'全部图片',Type:0},
			{Name:'新稿',Type:1},
			{Name:'可发',Type:2},
			{Name:'已发',Type:3},
			{Name:'已否',Type:8},
			{Name:'我创建的',Type:4},
			{Name:'最近三天',Type:5},
			{Name:'最近一周',Type:6},
			{Name:'最近一月',Type:7}
		];
		if(top.$personalCon.listDefaultFilter){
			var paramValue = top.$personalCon.listDefaultFilter.paramValue;
			for (var i = 0; i < filters.length; i++){
				if(filters[i].Type == paramValue){
					filters[i].IsDefault = true;
					break;
				}
			}
		}else{
			filters[0].IsDefault = true;
		}
		return filters;
	}()),
	_doBeforeRefresh : function(_params){		
		$("selAll").checked = false; //wenyh@2008-06-13 刷新全选状态
		if(this.searchPostData){// 查询模式		
			var temp = this.searchPostData;
			if(temp.queryInfoDetail) {
				this.queryInfoDetail = temp.queryInfoDetail;
			}
			delete temp.queryInfoDetail;
			
			Object.extend(PageContext.params, temp);
			//clear query 
			this._doAfterBound = function(){
				for(var p in this.searchPostData){
					delete PageContext.params[p];					
				}
				this.searchPostData = null;				
			};		
			Grid.draggable = false;
		}else{// 普通列表模式		
			Object.extend(PageContext.params, {
				channelids: PageContext.params['channelid'],
				siteids: PageContext.params['siteid']
			});
			this._doAfterBound = null;
			Grid.draggable = false;
		}
		//保存现场
		var oParams = getExtraExchangeParams();
		if(oParams){
			Object.extend(PageContext.params,{
					"FilterType": oParams["FilterType"],
					"CurrPage": oParams["CurrPage"],
					"CurrDocId": oParams["CurrDocId"],
					'OrderBy': oParams["OrderBy"],
					"SwitchMode": true
				});
		}
	},	
	editImportedPics : function(_params){
		FloatPanel.open("./photo/photoprops_edit.html?"+_params,"标注图片属性",680,350);
	},
	RefreshList2 : function(_id){
		PageContext.updateCurrRows();
		//refresh the img,may be reupload.		
		$("listpic_"+_id).src += "?" + Math.random();		
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
	/**
	* @param _oPostData[Object]: {objecttype, objectids, objectrights}
	*/
	_getAdditionRowSelInfo : function(_oPostData){
		var sObjIds = _oPostData.objectids;
		var result = null;
		if(sObjIds.length != 1) {
			var arrRows = this.getRows();
			var  arrDocIds = [];
			for (var i = 0; i < arrRows.length; i++){
				arrDocIds.push(arrRows[i].getAttribute('docid', 2));				
			}
			result = {				
				docids: arrDocIds
			};
		}else{
			var eRow = this.getRows()[0];
			result = {			
				docids: [eRow.getAttribute('docid', 2)],
				objectids: eRow.getAttribute('grid_rowid', 2)
			}
			delete eRow;
		}
		
		return result;
	},
	ctrlE : function(event){				
		var pSelectedRows = this.getRows();
		if( pSelectedRows && pSelectedRows.length == 1 ){
			var pSelectedRow = pSelectedRows[0];						
			if(!this._jdRightOnKeyAction(pSelectedRow.getAttribute("right"),32,"编辑图片.")) return;
			var docId = pSelectedRow.getAttribute("docid");
			//var rights = pSelectedRow.getAttribute("rigths");			
			Object.extend(PageContext.params,{DocId:docId});			
			PhotoMgr["edit"](docId, PageContext.params);
		}		
	},
	ctrlN : function(event){
		if(this._jdRightOnKeyAction(PageContext.params.RightValue,31,"新建图片.")){
			PhotoMgr["upload"](0, PageContext.params);		
		}
	},
	keyUp : function(event){
		var rows = document.getElementsByClassName(this._getSelectAbleClass(),$(this.gridId));				
		var lineLength = 0;
		for (lineLength = 1,count = rows.length; lineLength < count; lineLength++){
			if(rows[lineLength].offsetTop != rows[lineLength-1].offsetTop)
				break;
		}					

		var aChecked = this.getRows();
		if(!aChecked || aChecked.length == 0) return;		
		var firstSelectedIndex = aChecked[0].getAttribute("index")-1;//index begins 1.
		var preCanSelectRow = rows[firstSelectedIndex-lineLength];
		
		if(preCanSelectRow){
			this.toggleCurrRow(preCanSelectRow,event.ctrlKey);
		}
		aChecked = null;
		delete rows;
	},
	keyDown : function(event){
		var rows = document.getElementsByClassName(this._getSelectAbleClass(),$(this.gridId));				
		var lineLength = 0;
		for (lineLength = 1,count = rows.length; lineLength < count; lineLength++){
			if(rows[lineLength].offsetTop != rows[lineLength-1].offsetTop)
				break;
		}					

		var aChecked = this.getRows();
		if(!aChecked || aChecked.length == 0) return;		
		var lastSelectedIndex = aChecked[aChecked.length-1].getAttribute("index")-1;//index begins 1.
		var nextCanSelectRow = rows[lastSelectedIndex+lineLength];
		
		if(nextCanSelectRow){
			this.toggleCurrRow(nextCanSelectRow,event.ctrlKey);
		}
		aChecked = null;		
		delete rows;		
	},
	keyLeft : function(event){
		this.selectPreRow(event.ctrlKey);		
	},	
	keyRight : function(event){
		this.selectNextRow(event.ctrlKey);		
	},
	keyD : function(event){		
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length >= 1 ){			
			if(pSelectedIds.length == 1){				
				if(this._jdRightOnKeyAction(this.getRows()[0].getAttribute("right"),33,"删除这幅图片.")){
					PhotoMgr['delete'](pSelectedIds[0],PageContext.params);
				}
			}else{
				if(this._jdRightOnKeyAction(PageContext.params.RightValue,33,"删除图片.")){
					PhotoMgr['delete'](pSelectedIds,PageContext.params);
				}
			}
		}		
	},
	keyDelete : function(event){
		this.keyD(event);
	},
	_jdRightOnKeyAction : function(_rightValue,_rightIndex,_msg){
		if(!isAccessable4WcmObject(_rightValue||'0',_rightIndex)){
			$timeAlert('您没有权限在当前分类'+_msg,5);				
			return false;
		}

		return true;
	}
});

var CURRLIST_ID = "list_crtime";
var ORDERFIELD = "wcmchnldoc.crtime";
var ORDER_DIRECT = " desc";
function showOrderTypes(_evt){
		_evt = _evt || window.event;
		var sOrderTypes = '<div class="listOrders0"><div class="listOrders1">';
		sOrderTypes += '<ul class="listOrders" id="list_orders">';
		sOrderTypes += '<li id="list_crtime"><a href="#" onclick="orderContent(0); return false;" _field="crtime">创建时间</a></li>';
		sOrderTypes += '<li id="list_doctitle"><a href="#" onclick="orderContent(1); return false;">图片标题</a></li>';		
		sOrderTypes += '<li id="list_cruser"><a href="#" onclick="orderContent(2); return false;">创建者</a></li>';		
		sOrderTypes += '</ul></div></div>';

		$('spOrderClue').className = ORDER_DIRECT;
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
	
	setOrderListStyle();
	hideHelpTip($('lnkFirer'));
	BasicDataHelper.call("wcm6_photo","query",PageContext.params,true,PageContext.PageLoaded)
}

function antitone(_evt){
	if(CURRLIST_ID == "list_crtime"){
		ORDERFIELD = "wcmchnldoc.crtime";				
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

	BasicDataHelper.call("wcm6_photo","query",PageContext.params,true,PageContext.PageLoaded)
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
	 return isAccessable(rightValue,rightIndex)?"":"disabled";	
}
function isAccessable(rightValue,rightIndex){
	//var currRight = $nav().getCurrElementInfo()["RightValue"];
	return isAccessable4WcmObject(rightValue,rightIndex);//&&isAccessable4WcmObject(currRight,rightIndex);
}
function dealWithRightCss(rightValue,rightIndex){
	return isAccessable(rightValue,rightIndex)?"grid_selectable_row":"grid_selectdisable_row";
}
function showPic(_id,_chnlId,_rightValue,_recId){
	if(!isAccessable(_rightValue,34)) return;
	var chnlId = _chnlId || 0;
	var sUrl = "../photo/photo_show.html?PhotoId="+_id;
	var siteId = PageContext.params["siteid"] || 0;
	if(siteId > 0){
		sUrl += "&SiteId="+siteId;}
	else{
		sUrl += "&ChannelId="+chnlId;
	}
	sUrl += "&RecId="+_recId;
	$openMaxWin(sUrl);
}

function quickSearch(){
	m_oQuickSearcher.openSearcher(PageContext.params);
}

var DIALOG_PHOTO_QUICKSEARCHER = "Dialog_Photo_QuickSearcher";
QuickSearcher = Class.create("QuickSearcher");
QuickSearcher.prototype = {		
	initialize : function(){
		var mytop = top || top.actualTop;
		mytop.m_eSearcher = TRSDialogContainer.register(DIALOG_PHOTO_QUICKSEARCHER, '图片快速检索', './photo/photo_search.htm', '400px', '350px', false);		
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
			PageContext.searchPostData = queryData;
			//PageContext.refresh($toQueryStr(queryData));			
			$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', $toQueryStr(queryData));
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
		TRSDialogContainer.display(DIALOG_PHOTO_QUICKSEARCHER, hostParams,positions[0]-400,positions[1]);	
	}
};

Event.observe(window,"load",function(){
	m_oQuickSearcher = new QuickSearcher();
});