Object.extend(PageContext,{
	//是否将数据提供者切换到本地的开关
	isLocal : false,
	
	//远程服务的相关属性	
	ObjectServiceId : 'wcm6_watermark',
	ObjectMethodName : 'query',	
	ObjectType : 'watermark',
	AbstractParams : {
			"SelectFields" : ""	,
			"LibId" : getParameter("siteid") || 0
	},//服务所需的参数	
	
	_doBeforeRefresh : function(_params){
		var p = _params.toQueryParams();
		Object.extend(this.params,{LibId:p.siteid});
		PageContext.drawLiterator('literator_path',"siteid="+p.siteid);
	}
});


Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '水印'
});

Object.extend(Grid,{
	draggable : false,	
	ctrlE : function(event){				
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length >= 1 ){
			if(!this._jdRightOnKeyAction("编辑水印.")) return;
			WatermarkMgr["edit"](pSelectedIds[0], PageContext.params);
		}		
	},
	ctrlN : function(event){
		if(!this._jdRightOnKeyAction("新建水印.")) return;
		WatermarkMgr["add"](0, PageContext.params);
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
			if(!this._jdRightOnKeyAction("删除水印.")) return;
			if(pSelectedIds.length == 1){
				WatermarkMgr['delete'](pSelectedIds[0],PageContext.params);
			}else{
				WatermarkMgr['delete'](pSelectedIds,PageContext.params);
			}
		}
		
	},
	keyDelete : function(event){
		this.keyD(event);
	},
	_jdRightOnKeyAction : function(_msg){
		if(!isAccessable4WcmObject(PageContext.params.RightValue||'0',32)){
			$timeAlert('您没有权限在当前图库'+_msg,5);				
			return false;
		}

		return true;
	}
});

var CURRLIST_ID = "list_crtime";
var ORDERFIELD = "crtime";
var ORDER_DIRECT = " desc";
function showOrderTypes(_evt){
		_evt = _evt || window.event;
		var sOrderTypes = '<div class="listOrders0"><div class="listOrders1">';
		sOrderTypes += '<ul class="listOrders" id="list_orders">';
		sOrderTypes += '<li id="list_crtime"><a href="#" onclick="orderContent(0); return false;" _field="crtime">创建时间</a></li>';
		sOrderTypes += '<li id="list_title"><a href="#" onclick="orderContent(1); return false;">水印标题</a></li>';		
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
		CURRLIST_ID = "list_title";
		ORDERFIELD = "WMNAME";		
	}else if(_field == 2){
		CURRLIST_ID = "list_cruser";
		ORDERFIELD = "cruser";
	}else{
		CURRLIST_ID = "list_crtime";
		ORDERFIELD = "crtime";		
	}

	ORDER_DIRECT = " desc";
	var orderby = ORDERFIELD + ORDER_DIRECT;
	
	Object.extend(PageContext.params,{OrderBy:orderby});
	
	setOrderListStyle();
	hideHelpTip($('lnkFirer'));
	BasicDataHelper.call("wcm6_watermark","query",PageContext.params,true,PageContext.PageLoaded)
}

function antitone(_evt){
	if(CURRLIST_ID == "list_crtime"){
		ORDERFIELD = "crtime";				
	}else if(CURRLIST_ID == "list_title"){		
		ORDERFIELD = "wmname";			
	}else{
		ORDERFIELD = "cruser";			
	}

	if(ORDER_DIRECT.indexOf("desc") != -1){
		ORDER_DIRECT = " asc";
	}else{
		ORDER_DIRECT = " desc";
	}

	$('spOrderClue').className = ORDER_DIRECT;

	var orderby = ORDERFIELD + ORDER_DIRECT;
	Object.extend(PageContext.params,{OrderBy:orderby});

	BasicDataHelper.call("wcm6_watermark","query",PageContext.params,true,PageContext.PageLoaded)
}

function mapFileName(_fn){	
	if(_fn == null || _fn.trim().length == 0){
		return "../images/photo/pic_notfound.gif";
	}
	
	var fn = "/webpic/" + _fn.substr(0,8) + "/" + _fn.substr(0,10) + "/" + _fn;
	/*
	var imgLoader = new Image();
	imgLoader.src = fn;
	imgLoader.onload = function(){
		alert(imgLoader.height);
	}*/

	return fn;
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
		if(width != 97){			
			_imgloaded.style.right = 45+Math.floor((97-width)/2)+"px";
		}
		if(height != 110){
			_imgloaded.style.bottom = 50+Math.floor((110-height)/2)+"px";
		}
	}
}


function getRightFromHost(){
	return $nav().getCurrElementInfo()["RightValue"];
}