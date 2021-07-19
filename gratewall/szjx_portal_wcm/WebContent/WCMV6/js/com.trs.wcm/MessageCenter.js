$package('com.trs.wcm');

var myActualTop = (top.actualTop||top);
if(myActualTop==self){
	$import('com.trs.wcm.MessageCenterTop');
	$import('com.trs.wcm.FloatPanel');
	$import('com.trs.wcm.ProcessBar');
	$import('com.trs.wcm.HrefToSheetMap');

//	$import('com.trs.wcm.notify.MessageCenterExt');
}
else if(myActualTop.$MessageCenter){
	$import('com.trs.wcm.FloatPanel');
	$import('com.trs.wcm.ProcessBar');
	var $MessageCenter = myActualTop.$MessageCenter;
}
else {
	alert("top frame must import com.trs.wcm.MessageCenter");
}

if(!window.PageContext){
	var PageContext = {
		params : {}
	};
}
PageContext.getParams = function(){
	var result = {};
	for( var sName in PageContext){
		var oParam = PageContext[sName];
		if(oParam == null || typeof(oParam) == 'function' || typeof(oParam) == 'object') 
			continue;
		result[sName] = oParam;
	}	
	return result;
}
PageContext.getPageNavHtml = function(iCurrPage,iPages){
	var sHtml = '';
	//output first
	if(iCurrPage!=1){
		sHtml += '<span class="nav_page" title="首页" onclick="PageContext.PageNav.goFirst();">1</span>';
	}else{
		sHtml += '<span class="nav_page nav_currpage" title="首页">1</span>';
	}
	var i,j;
	if(iPages-iCurrPage<=1){
		i = iPages-3;
	}
	else if(iCurrPage<=2){
		i = 2;
	}
	else{
		i = iCurrPage-1;
	}
	var sCenterHtml = '';
	var nFirstIndex = 0;
	var nLastIndex = 0;
	//output 3 maybe
	for(j=0;j<3&&i<iPages;i++){
		if(i<=1)continue;
		j++;
		if(j==1)nFirstIndex = i;
		if(j==3)nLastIndex = i;
		if(iCurrPage!=i){
			sCenterHtml += '<span class="nav_page" onclick="PageContext.PageNav.go('+i+','+iPages+');">'+i+'</span>';
		}else{
			sCenterHtml += '<span class="nav_page nav_currpage">'+i+'</span>';
		}
	}
	//not just after the first page
	if(nFirstIndex!=0&&nFirstIndex!=2){
		sHtml += '<span class="nav_morepage" title="更多">...</span>';
	}
	sHtml += sCenterHtml;
	//not just before the last page
	if(nLastIndex!=0&&nLastIndex!=iPages-1){
		sHtml += '<span class="nav_morepage" title="更多">...</span>';
	}
	//output last
	if(iCurrPage!=iPages){
		sHtml += '<span class="nav_page" title="尾页" onclick="PageContext.PageNav.goLast();">'+iPages+'</span>';
	}else{
		sHtml += '<span class="nav_page nav_currpage" title="尾页">'+iPages+'</span>';
	}
	return sHtml;
}
PageContext.getNavigatorHtml = function(){
	var iRecordNum = parseInt(PageContext["RecordNum"]);
	if(iRecordNum==0)return '';
	var iCurrPage = parseInt(PageContext.params["CurrPage"]||1);
	var iPageSize = parseInt(PageContext["PageSize"]);
	var iPages = parseInt(PageContext["PageCount"]);
	var sHtml = '';
	var sTypeDesc = PageContext.PageNav["UnitName"]+PageContext.PageNav["TypeName"];
	sHtml += '<span class="nav_page_detail">共<span class="nav_pagenum">'+iPages+'</span>页'
				+'<span class="nav_recordnum">'+iRecordNum+'</span>'
				+sTypeDesc+',每页<span class="nav_pagesize">'+iPageSize+'</span>'+sTypeDesc
				+'.</span>';
//				+',当前为第<span class="nav_currpage">'+iCurrPage+'</span>页.</span>';
	/*
	if(iPages>1){
		sHtml += '<span class="nav_go">转到第<input type="text" name="nav_go_num" id="nav_go_num">页'
					+'<span class="nav_go_btn" onclick="PageContext.PageNav.go($(\'nav_go_num\').value,'
					+iPages+')"></span></span>';
	}
	*/
	if(iPages>1){
		sHtml += PageContext.getPageNavHtml(iCurrPage,iPages);
	}
	return sHtml;
	//
	if(iPages>1){
		sHtml += '<span class="nav_specpage_go">';
		if(iCurrPage!=1){
			sHtml += '<span class="wcm_pointer nav_go_first" title="首页" onclick="PageContext.PageNav.goFirst();"></span>';
			sHtml += '<span class="wcm_pointer nav_go_pre" title="上一页" onclick="PageContext.PageNav.goPre();"></span>';
		}else{
			sHtml += '<span class="nav_go_first_disabled"></span>';
			sHtml += '<span class="nav_go_pre_disabled"></span>';
		}
		if(iCurrPage!=iPages){
			sHtml += '<span class="wcm_pointer nav_go_next" title="下一页" onclick="PageContext.PageNav.goNext();"></span>';
			sHtml += '<span class="wcm_pointer nav_go_last" title="尾页" onclick="PageContext.PageNav.goLast();"></span>';
		}else{
			sHtml += '<span class="nav_go_next_disabled"></span>';
			sHtml += '<span class="nav_go_last_disabled"></span>';
		}

		sHtml += '</span>';
	}
	return sHtml;
}
PageContext.drawNavigator = function(){
	var eNavigator = $(PageContext.PageNav.NavId);
	if(!eNavigator)return;
	var sHtml = PageContext.getNavigatorHtml();
	Element.update(eNavigator,sHtml);
}
PageContext.PageNav = {
	UnitName : '条',
	TypeName : '记录',
	NavId : 'list_navigator',
	go : function(_iPage,_iPageNum){
		alert('must implements');
	},
	goFirst : function(){
		PageContext.PageNav.go(1,PageContext["PageCount"]);
	},
	goPre : function(){
		if(PageContext.params["CurrPage"]>1){
			PageContext.PageNav.go(PageContext.params["CurrPage"]-1,PageContext["PageCount"]);
		}
	},
	goNext : function(){
		if((PageContext.params["CurrPage"]||1)<PageContext["PageCount"]){
			PageContext.PageNav.go((PageContext.params["CurrPage"]||1)+1,PageContext["PageCount"]);
		}
	},
	goLast : function(){
		PageContext.PageNav.go(PageContext["PageCount"],PageContext["PageCount"]);
	}
}
function keyDown(event){
	try{
		if(myActualTop.cancelKeyDown){//取消键盘事件的处理
			return;
		}
		var worker = $MessageCenter.keyDownWorker || {};
		var event = event || window.event;
		var eTarget = Event.element(event);
		//TODO 进一步过滤其他Form元素
		var bIsTextInput = true;
		if(eTarget != null){
			bIsTextInput = (eTarget.nodeName.toUpperCase() == 'INPUT' && eTarget.type != 'checkbox')
				||(eTarget.nodeName.toUpperCase() == 'TEXTAREA')
				||(eTarget.nodeName.toUpperCase() == 'SELECT');
		}
		if(bIsTextInput
			|| (worker.checkSpecSrcElement && worker.checkSpecSrcElement(eTarget)) === false) {
			return;
		}
		if(event.ctrlKey){
			var c = '';
			switch(event.keyCode){
				case 33: // PgUp
					c = 'PgUp';
					break;
				case 34: // PgUp
					c = 'PgDn';
					break;
				case 35://
					c = 'End';
					break;
				case 36://
					c = 'Home';
					break;
				default:
	//						c = String.fromCharCode(event.keyCode);
			};
			if(worker['ctrl'+c]&&worker['ctrl'+c](event)==false){
				Event.stop(event);
				return false;
			}
		}
		else if(event.altKey || (myActualTop.menuControllers && myActualTop.menuControllers[0] && myActualTop.menuControllers[0].isOpened())){
			if(myActualTop.menuControllers && myActualTop.menuControllers[0])
				myActualTop.menuControllers[0].onKeyDown(event);				
		}
		else{
			var sFunction = '';
			switch(event.keyCode){
				case Event.KEY_DELETE:
					if(event.shiftKey){
						sFunction = 'keyShiftDelete';
					}
					else{
						sFunction = 'keyDelete';
					}
					break;
				case Event.KEY_UP:
					sFunction = 'keyUp';
					break;
				case Event.KEY_DOWN:
					sFunction = 'keyDown';
					break;
				case Event.KEY_LEFT:
					sFunction = 'keyLeft';
					break;
				case Event.KEY_RIGHT:
					sFunction = 'keyRight';
					break;
				case Event.KEY_RETURN:
					sFunction = 'keyReturn';
					break;
				case 113: // F2
					sFunction = 'keyF2';
					break;
				case Event.KEY_BACKSPACE:
					return false;
				default:
					var c = String.fromCharCode(event.keyCode);
					if(worker['key'+c]){
						sFunction = 'key'+c;
					}
					else{
						sFunction = 'ctrl'+c;
					}
					break;
			}
			if(worker[sFunction]&&worker[sFunction](event)==false){
				Event.stop(event);
				return false;
			}
		}
	}catch(err){
		//Just Skip it.
	}
};

PageEventHandler={
	register : function(_oWorker){
		$MessageCenter.keyDownWorker = _oWorker;
	},
	unregister : function(){
		$MessageCenter.keyDownWorker = null;
	}
};
Event.observe(document,'keydown',keyDown);
//IE ImageCache
(function(){
    /*Use Object Detection to detect IE6*/
    var  m = document.uniqueID /*IE*/
    && document.compatMode  /*>=IE6*/
    && !window.XMLHttpRequest /*<=IE6*/
    && document.execCommand;
    
    try{
        if(!!m){
            m("BackgroundImageCache", false, true) /* = IE6 only */ 
        }
        
    }catch(oh){};
})();

var $nav = function(){
	return $MessageCenter.iframes['nav_tree'].contentWindow;
}
var $main = function(){
	return $MessageCenter.iframes['main'].contentWindow;
}
var $oap = function(){
	return $MessageCenter.iframes['oper_attr_panel'].contentWindow;
}
var $tab = function(){
	return $MessageCenter.iframes['footer'].contentWindow;
}

var TabType = {
	SYSTEM : "SYSTEM",
	SITE : "SITE",
	CHANNEL : "CHANNEL"
};
var m_pSimilarIndexs = [
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[11,13,12,15,16,17,19,18,53,55,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[21,23,22,25,24,21,24,28,0,0,0,0,0,0,0,0,0,0,0,0],
	[21,23,21,28,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[18,31,32,33,33,34,38,39,34,34,34,35,36,37,56,54,8,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[18,18,33,33,56,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[18,32,33,33,38,39,35,36,37,56,54,0,0,0,0,0,0,0,0,0],
	[36,37,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
];

/*
var m_pDependIndexs = [
    [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[33,32,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
];
//*/

var m_pSpecialIndexs = [38,39];//合并权限时作或操作

function _hasSimlarRight(_sRight, _nRightIndex) {
	if(m_pSimilarIndexs.length <= _nRightIndex)return false;
	var exitRecursion = false;//递归是否结束的标记
	if(!window.similarRightIndexCache){
		window.similarRightIndexCache = {};
		exitRecursion = true;
	}
	if(window.similarRightIndexCache[_nRightIndex]){
		return false;
	}
	window.similarRightIndexCache[_nRightIndex] = true;
	var pSimilarIndexs = m_pSimilarIndexs[_nRightIndex];
	var bResult = false;
	for (var i = 0; i < pSimilarIndexs.length; i++) {
		var nSimilarIndex = pSimilarIndexs[i];
		if (nSimilarIndex == 0)break;
		if (isAccessable4WcmObject(_sRight, nSimilarIndex)) {
			 bResult = true;
			 break;
		}
	}	
	if(exitRecursion){
		window.similarRightIndexCache = null;
	}
	return bResult;
}

/*
function _hasDependRight(_sRight, _nRightIndex) {
	if(m_pDependIndexs.length <= _nRightIndex)return false;	
	var pDependIndexs = m_pDependIndexs[_nRightIndex];
	var bResult = false;
	for (var i = 0; i < pDependIndexs.length; i++) {
		var nDependIndex = pDependIndexs[i];
		if(nDependIndex == 0) break;
		bResult = isAccessable4WcmObject(_sRight, nDependIndex);
		if (!bResult)break;
	}
	return bResult;
}
//*/

function _hasRight(_sRight, _iRightIndex){
	var len = _sRight.length;
	if(_iRightIndex>=len)return false;
	var nReverseIndex = len-1-_iRightIndex;
	return _sRight.charAt(nReverseIndex)=='1';
}

function isAccessable4WcmObject(_sRight, _iRightIndex){
	//将下面的语句提前执行 modified by hxj 2008-06-12
	if(_iRightIndex == -1)return true;
	if(_iRightIndex == -2){
		if(isAdmin()){
			return true;
		}
		return false;
	}
	if(!_sRight)return false;
	// 如果索引为64，则表示判断该对象的“访问权限”，即只要有任何一种权限就返回true
	if (_iRightIndex == 64 && _sRight.indexOf("1") >= 0)
		return true; // 如果权限值中有数
	/*
	if(_iRightIndex == -1)return true;
	if(_iRightIndex == -2){
		if(isAdmin()){
			return true;
		}
		return false;
	}
	*/
	// 待实现
	if (_hasRight(_sRight, _iRightIndex))
		return true;
	// 权限包含关系的判断
	if (_hasSimlarRight(_sRight, _iRightIndex)){
//		alert(1);
		return true;
	}
	// 权限依赖关系的判断
/*	if (_hasDependRight(_sRight, _iRightIndex)){
//		alert(2);
		return true;
	}//*/
	return false;
}

function checkRight(rightValue, rightIndex){      
	if(isAdmin()) return true;

	if(rightIndex == -1)
		return true;
	if(!rightValue) return false;

	if(Number.isNumber(rightIndex)){//eg. rightIndex:5
		return isAccessable4WcmObject(rightValue, rightIndex);
	}else if(String.isString(rightIndex)){//eg. rightIndex:"3"; rightIndex:"21-34"; rightIndex:"21-34,38"
		var rightIndexs = rightIndex.split(",");
		if(rightIndexs.length>1){
			for (var i = 0; i < rightIndexs.length; i++){
				if(checkRight(rightValue, rightIndexs[i]))
					return true;
			}
			return false;
		}
		var rightIndexs = rightIndex.split("-");
		if(rightIndexs.length==1){
			return isAccessable4WcmObject(rightValue, rightIndex);
		}
		for (var i = parseInt(rightIndexs[0]); i <= parseInt(rightIndexs[1]); i++){
			if(isAccessable4WcmObject(rightValue, i))
				return true;
		}
		return false;
	}else if(Array.isArray(rightIndex)){//eg. rightIndex:[3,"32-45"]
		for (var i = 0; i < rightIndex.length; i++){
			if(checkRight(rightValue, rightIndex[i]))
				return true;
		}
		return false;
	}
	return false;
}

function mergeRights(_arrRight){
	var rightsNum = 0;
	for(var i=0;i<_arrRight.length;i++){
		if(_arrRight[i].length>rightsNum){
			rightsNum = _arrRight[i].length;
		}
	}
	var sRetRight = '';
	var tmpCurrIndex = -1;
	for(var i=0;i<rightsNum;i++){
		if(rightsNum>i&&m_pSpecialIndexs.include(rightsNum-1-i)){
			var c = '0';
			for(var j=0;j<_arrRight.length;j++){
				tmpCurrIndex = i+_arrRight[j].length-rightsNum;
				if(tmpCurrIndex>=0&&_arrRight[j].charAt(tmpCurrIndex)=='1'){
					c = '1';
					break;
				}
			}
		}
		else{
			var c = '1';
			for(var j=0;j<_arrRight.length;j++){
				tmpCurrIndex = i+_arrRight[j].length-rightsNum;
				if(tmpCurrIndex<0||_arrRight[j].charAt(tmpCurrIndex)=='0'){
					c = '0';
					break;
				}
			}
		}
		sRetRight += c;
	}
	delete _arrRight;
	return sRetRight;
}

function isAdmin(){
	return myActualTop.global_IsAdmin;
}

function testIAmHere(_object){
	alert('i\'m here!'+Object.parseSource(_object));
}
function getTabType(hrefStr){
	var query = hrefStr||window.location.search;
	if(!/channelid=0/.test(query) 
			&& (/channelid/.test(query) || /objtype=101/i.test(query))){
		return TabType.CHANNEL;
	}
	if(/siteid/.test(query) || /objtype=103/i.test(query)){
		return TabType.SITE;
	}
	return tabType = TabType.SYSTEM;
}

function getSheetType(hrefStr){
	var oRegTabType = /tab_type=([^&]*)/ig;
	var matchArray = oRegTabType.exec(hrefStr||window.location.href);
	if(matchArray!=null&&matchArray[1]){
		return matchArray[1];
	}
	matchArray = /\/([^\/\?]+)\.[^\/\?]+(\?.*$|$)/.exec(hrefStr||window.location.href);
	if(matchArray == null){
		var errMsg = "得到底部标签类型时出现错误,造成这种现象的可能是：\n";
		errMsg += "\t链接地址[" + (hrefStr||window.location.href) + "]格式错误;";
		throw new Error(errMsg);
		//alert("@getSheetType:地址[" + (hrefStr||window.location.href) + "]\n没有匹配取得sheet类型的正则表达式");
		//return;
	}
	var sheetType = myActualTop.$HrefToSheetMap[matchArray[1]];
	if(sheetType == null){
		var errMsg = "得到底部标签类型时出现错误,造成这种现象的可能是：\n";
		errMsg += "\t没有为此链接配置对应的标签类型．\n";
		errMsg += "\t请先检查HrefToSheetMap.js文件中是否配置了对应的隐射";
		errMsg += "[" + matchArray[1] + "]";
		throw new Error(errMsg);
		//alert(errMsg);
	}
	return sheetType;
}

function checkExistSheet(hrefStr){
	var query = hrefStr||window.location.href;
	var params = query.toQueryParams();
	if(query.indexOf("?") > 0){
		params = query.match(/^.+\?(.+)$/)[1].toQueryParams();
	}	
	var tabType = getTabType(query);
	var sheetType = getSheetType(query);
	return $tab().checkExistSheet(tabType, sheetType, params["RightValue"], params["ChannelType"], (params['IsVirtual'] == 1));
}
//检验配置的页面是否会返回404
var m_hsChecked404Pages = {};
function checkPage404(_hrefSrc){
	_hrefSrc = _hrefSrc.replace(/\?.*$/,'');

	//元数据是动态生成的引用，所以有所区别
	if(_hrefSrc.indexOf("metadata/application") >= 0){
		if(m_hsChecked404Pages[_hrefSrc] === false){
			return false;
		}
	}else if(m_hsChecked404Pages[_hrefSrc]!=null){
		return m_hsChecked404Pages[_hrefSrc];
	}
	var r = new Ajax.Request(_hrefSrc,{
		asynchronous:false,
		method:'GET'
	});
	var bResult = r.transport.status==404;
	m_hsChecked404Pages[_hrefSrc] = bResult;
	return bResult;
}

function execSkeepForNotSheet(dealType, sheetArray, _sSrc){
	if(dealType == "1"){//取消当前操作，返回原视图
		var lastSecondNode = $nav().com.trs.tree.TreeNav.lastSecondSrcElementA;
		if(lastSecondNode){
			var id = lastSecondNode.parentNode.getAttribute("id").split("_");
			$nav().focus(id[0], id[1]);//聚焦树节点
		}
	}else{//'2','3'
		//跳转到第一个存在的标签或则跳转到指定的标签页,如果指定标签页不存在,则跳转到第一个存在的标签页
		if(sheetArray[0] == null){
			alert("当前用户竟然一个标签[sheet]都不存在！");
			return;				
		}
		var sheetType = sheetArray[0]["type"];
		if(dealType == '3'){
			var defaultSheetType = top.PageContext.defaultSheetType;
			for (var i = 0; i < sheetArray.length; i++){
				if(sheetArray[i]["type"] == defaultSheetType){
					sheetType = defaultSheetType;
					break;
				}
			}
		}	
/*
*不需要保存sheet类型
		var id = $nav().com.trs.tree.TreeNav.oPreSrcElementA.parentNode.getAttribute("id").split("_");
		switch(id[0]){
			case 'c':
				top.PageContext.channelHost = sheetType;
				break;
			case 's':
				top.PageContext.siteHost = sheetType;
				break;
			default:
				top.PageContext.rootHost = sheetType
				break;
		}
*/
		var search = _sSrc.replace(/^[^\?]*\?/, '?');
		$nav().$changeSheet(search, sheetType);
	}
}

function isFocusedNode(nodeId){
	try{
		var nodeInfo = $MessageCenter.getNav().getFocusedNodeInfo();
		if((nodeInfo.type + "_" + nodeInfo.id) == nodeId)
			return true;
		return false;
	}catch(error){
		//alert(error.message);
	}
}

PageContext.drawLiterator = function(_sLitId, _params, _fGetParameter){
	//TODO url的缺省值
	var eLit = $(_sLitId);
	if(eLit == null) {
		alert('试图显示页面上没有的literator元素！');
		return;
	}
	var url = eLit.getAttribute('url') || '';
	//url += (url.indexOf('?') == -1 ? '?' : '&') + 'random=' + Math.random();
	if(_params) {
		params = _params;
	}else{
		var params = eLit.getAttribute('params');
		if(params) {
			var reg = /\{\$(\w+)\}/ig;
			params = params.replace(reg, function($0,$1,$2){
				return (_fGetParameter || getParameter)($1);
			})
		}else{
			params = '';
		}
	}
	params += (params.indexOf('=') == -1 ? '' : '&') + 'random=' + Math.random();
	params = encodeParams(params);
	var sHtml = ''
			+ 'var __fillHtml = function(_tran){\n'
			+ '		var lit = $("' + _sLitId + '");\n'
			+ '		lit.innerHTML = _tran.responseText;\n'
			+ '		createWardOfLiterator("' + _sLitId + '");\n'
			+ '		bindEventsOfLiterator("' + _sLitId + '");\n'
			+ '};'
			+ 'new Ajax.Request("' + url + '",{method:"get",parameters:"'
				+ params + '", onComplete: __fillHtml });\n';
	eval(sHtml);
}
function getWidthOfLiterator(_sLitId){
	var literator = $(_sLitId);

	//get width for the literator.
	var bodyWidth = parseInt(document.body.offsetWidth, 10);
	var sQueryBox = window.SimpleQuery ? SimpleQuery.Container : "query_box";
	var queryBoxWidth = 300; 
	if($(sQueryBox)){
		var width = parseInt($(sQueryBox).offsetWidth, 10);
		if(width > queryBoxWidth){
			queryBoxWidth = width;
		}
	}
	var previousSibling = getPreviousHTMLSibling(literator);
	var previousWidth = 0;
	if(previousSibling && previousSibling.id != sQueryBox){
		previousWidth = previousSibling.offsetWidth;
	}
	var width = bodyWidth - queryBoxWidth - previousWidth - 60;	
	return width > 0 ? width : 30;
}
function createWardOfLiterator(_sLitId){
	var literator = $(_sLitId);
	if(!window.literatorInited){
		//init the literator style.
		literator.style.width = getWidthOfLiterator(_sLitId);
		literator.style.whiteSpace = "nowrap";
		literator.style.overflow = "hidden";
		literator.style.textOverflow = "ellipsis";
		literator.style.margin = "0px 5px";
		literator.scrollLeft = literator.offsetWidth;
			
		//create ward.
		var createWard = function(sInnerText, oParentNode, oNode){
			var ward = document.createElement("span");
			oParentNode.insertBefore(ward, oNode);
			ward.style.visibility = 'hidden';
			ward.style.color = "blue";
			ward.innerText = sInnerText;
			return ward;
		};
		var foreWard = createWard("<<", literator.parentNode, literator);
		var backWard = createWard(">>", literator.parentNode, getNextHTMLSibling(literator));
	}else{
		var foreWard = getPreviousHTMLSibling(literator);	
	}
	if(literator.scrollLeft > 0){
		foreWard.style.visibility = 'visible';
	}
}

function bindEventsOfLiterator(_sLitId){
	if(window.literatorInited){
		return;
	}
	var literator = $(_sLitId);
	var foreWard = getPreviousHTMLSibling(literator);
	var backWard = getNextHTMLSibling(literator);

	Event.observe(foreWard, 'mouseover', function(){
		backWard.style.visibility = 'visible';
		window.foreWardHandler = setInterval(function(){
			var oldScrollLeft = literator.scrollLeft;
			literator.scrollLeft = parseInt(literator.scrollLeft, 10) - 5;
			if(literator.scrollLeft == oldScrollLeft){
				foreWard.style.visibility = 'hidden';
			}
		},40);
	});
	Event.observe(foreWard, 'mouseout', function(){
		clearInterval(window.foreWardHandler);
	});
	Event.observe(backWard, 'mouseover', function(){
		foreWard.style.visibility = 'visible';
		window.backWardHandler = setInterval(function(){
			var oldScrollLeft = literator.scrollLeft;
			literator.scrollLeft = parseInt(literator.scrollLeft, 10) + 5;
			if(literator.scrollLeft == oldScrollLeft){
				backWard.style.visibility = 'hidden';
			}
		},40);
	});
	Event.observe(backWard, 'mouseout', function(){
		clearInterval(window.backWardHandler);
	});
	Event.observe(window, 'resize', function(){
		literator.style.width = getWidthOfLiterator(_sLitId);
		literator.scrollLeft = literator.offsetWidth;
		foreWard.style.visibility = 'hidden';
		backWard.style.visibility = 'hidden';
		if(literator.scrollLeft > 0){
			foreWard.style.visibility = 'visible';
		}
	});
	window.literatorInited = true;
}

var RotatingBar = {
	TIMEOUT: 5000,
	start : function(_eRegion, _sTitle){
		try{
			if(this.m_oTimer != null){
				this.stop();
				window.clearTimeout(this.m_oTimer);
			}
			_eRegion = _eRegion || $MessageCenter.iframes['main'];
			_sTitle = _sTitle || '正在切换视图，请稍候...';
			showCoverAll(997);
			this.mask = $('divGlobalMask');

			this.region = $('divRatatingMask');
			Position.clone(_eRegion, this.region);
			this.bar = $('divRotatingBar');
			var dim = Element.getDimensions(_eRegion);
			//$alert(getNavStatus())
			this.bar.style.left = (dim.width + 200)/2;
			this.bar.style.top  = (dim.height)/2;
			$('spTitle').innerHTML = _sTitle;

			if(_eRegion.src.indexOf('document_readmode_index.html') != -1) {
				this.bar.style.left = (dim.width + 200)/2 - 86;
			}else{
				Element.show(this.mask);
			}
			//Element.show(this.mask);
			Element.show(this.bar);
			delete _eRegion;

		}catch(err){
			//some page not in main,just skip
		}finally{
			//超时后自动关闭
			this.m_oTimer = setTimeout(function(){this.stop();}.bind(this), this.TIMEOUT);
		}
	},
	stop : function(){
		try{
			if(this.m_oTimer) {
				clearTimeout(this.m_oTimer);
			}
			if(!this.bar || !this.mask) return;
			Element.hide(this.bar);
			Element.hide(this.mask);
			hideCoverAll();
		}catch(err){
			//some page not in main,just skip
		}
	},
	visible : function(){
		if(this.mask == null || this.bar == null) {
			return false;
		}
		return Element.visible(this.mask);	
	}
}
function $beginSimpleRB(_sTitle){
	if((top.actualTop || top).RotatingBar) {
		(top.actualTop || top).RotatingBar.start(null, _sTitle);
	}
}
function $endSimpleRB(){
	if((top.actualTop || top).RotatingBar) {
		(top.actualTop || top).RotatingBar.stop();
	}
}

function setExtraExchangeParams(params){
	(top.actualTop || top)._extraExchangeParams_ = params;
}

function getExtraExchangeParams(){
	var extraExchangeParams = (top.actualTop || top)._extraExchangeParams_;
	(top.actualTop || top)._extraExchangeParams_ = undefined;
	return extraExchangeParams;
}
function dealWithFresh(_sSearch){
	var query = _sSearch||window.location.search;
	
	// ge gfc add @ 2007-1-15 10:52 增加对虚sheet的执行逻辑的判断
	var path = window.location.pathname;
	var nPos = path.lastIndexOf('/');
	if(nPos > 0) {
		path = path.substr(nPos + 1);
		if(myActualTop.$VirtualSheetArray.include(path) || query.indexOf('disable_sheet=1') != -1) {
			$MessageCenter.sendMessage('footer', 'enableTabSheets', null, false, false, true);
			return;
		}
		$MessageCenter.sendMessage('footer', 'enableTabSheets', null, true, false, true);
	}
	var params = query.toQueryParams();
	var tabType = getTabType(query);

	if(tabType == TabType.CHANNEL){//导航树中栏目处于选中状态
		var channelid = params['channelid'] || params['ObjId'];
		if(!isFocusedNode('c_' + channelid))
			$MessageCenter.sendMessage('nav_tree', 'focusChannel', null, channelid, true, true);	
	}else if(tabType == TabType.SITE){//导航树中站点处于选中状态
		var siteid = params['siteid'] || params['ObjId'];
		if(!isFocusedNode('s_' + siteid))
			$MessageCenter.sendMessage('nav_tree', 'focusSite', null, siteid, true, true);	
	}else{//导航树中站点类型处于选中状态
		var sitetype = params['SiteType'];
		if(!isFocusedNode('r_' + sitetype))
			$MessageCenter.sendMessage('nav_tree', 'focus', null, ['r', params["SiteType"]], true, true);	
	}
	try{
		var sheetType = getSheetType(window.location.href);
		$MessageCenter.sendMessage('footer', 'loadTab', null, [tabType, sheetType, params["RightValue"], params["ChannelType"], (params['IsVirtual'] == 1)], true, true);
	}catch(error){
		//TODO logger
		//alert(error.message);
	}
}

(function(){
	if(myActualTop!=self){
		var iframe = window.frameElement;
		if(iframe){
			$MessageCenter.register(iframe.id,iframe);
		}
		Event.observe(window,'unload',function(){
			var iframe = window.frameElement;
			if(iframe){
				$MessageCenter.unregister(iframe.id);
			}
		});
	}
	if(window.frameElement && window.frameElement.id == "main"){
		dealWithFresh();	
		Event.observe(window,'unload',PageEventHandler.unregister);
		if(!window.EnableContextMenu && getParameter("isdebug", top.location.search) != 1){
			document.oncontextmenu = function(){return false;};
		}
	}
	if(parent.InnerWindowLoaded)parent.InnerWindowLoaded();
})();