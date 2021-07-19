$personalCon = PageContext.individuationConfig
top.actualTop = window;
Object.extend(PageContext, {
	rootHost:($personalCon.defaultSystemSheet || {}).paramValue || 'website',
	siteHost:($personalCon.defaultSiteSheet || {}).paramValue || 'channel', 
	channelHost:($personalCon.defaultChannelSheet || {}).paramValue || 'document', 
	mode : ($personalCon.documentDefaultShow || {}).paramValue || "normal", 
	documentSynMode : (top.$personalCon.documentSynDefaultShow || {}).paramValue || 'dis',
	tabMoreStatus : "close",
	navigate_bar_status : true,
	attribute_bar_status : true
});

function toggleNav(){
	var navTd = $('td_nav');
	var navAdvanced = $('nav_advanced');
	var navSimpled = $('nav_simple');
	var eArrow = $('arrow_toggle');
	if(navTd.getAttribute("nav_status")=='advanced'){
		navTd.setAttribute("nav_status",'simple');
		navAdvanced.style.display = 'none';
		navTd.className = 'index_td_nav_simple';
		eArrow.className = 'index_arrow index_arrow_right';
	}
	else{
		navTd.setAttribute("nav_status",'advanced');
		navAdvanced.style.display = '';
		navTd.className = 'index_td_nav';
		eArrow.className = 'index_arrow index_arrow_left';
	}
}
function stepResize(_ele,_toSize,_iStep,_fCallBack){
	var size = Element.getDimensions(_ele);
	var iWidth = size["width"];
	var iHeight = size["height"];
	var iToWidth = (_toSize[0]!=null)?_toSize[0]:iWidth;
	var iToHeight = (_toSize[1]!=null)?_toSize[1]:iHeight;
	var aStep = [(iToWidth-iWidth)/_iStep,(iToHeight-iHeight)/_iStep];
	setTimeout(function(_ele,_toSize,_aStep,_iStep,_fCallBack){
		if((_iStep==0)||(_aStep[0]==0&&_aStep[1]==0)){
			if(_toSize[0]==0||_toSize[1]==0){
				_ele.style.width = '';
				_ele.style.display = 'none';
			}
			if(_fCallBack)_fCallBack();
			return;
		}
		if(_ele.style.display=='none'&&(_aStep[0]>0||_aStep[1]>0)){
			_ele.style.display = '';
		}
		if(_aStep[0]!=0){
			var iStep = _aStep[0];
			if(parseInt(_ele.style.width)+iStep>0){
				_ele.style.width = (parseInt(_ele.style.width)+iStep)+'px';
			}
		}
		if(_aStep[1]!=0){
			var iStep = _aStep[1];
			if(parseInt(_ele.style.height)+iStep>0){
				_ele.style.height = (parseInt(_ele.style.height)+iStep)+'px';
			}
		}
		setTimeout(arguments.callee.bind(null,_ele,_toSize,_aStep,_iStep-1,_fCallBack),1);
		delete _ele;
	}.bind(null,_ele,_toSize,aStep,_iStep,_fCallBack),1);
	delete _ele;
}
Object.extend($logger,{
	stopError : true,
	ServerLogLevel : 3,
	LogError2Server : true
});
function isNavAdvanced(){
	return Element.visible('nav_advanced');
}

function showQuickLocate(position){
	TRSCrashBoard.setMaskable(false);
	if(window.quicklocateDialog == null) {
		 window.quicklocateDialog = TRSDialogContainer.register('quicklocate', 
		'快速定位', 'quicklocate.html', '370px', '270px', false);
	}
	TRSDialogContainer.display('quicklocate', [], position[0],position[1]);	
	TRSCrashBoard.setMaskable(true);
}

//单击菜单更多图标时的相关处理
function showTreeMoreActions(position){
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call("wcm6_individuation", 'query', {
		ParamName	: "customSite",
		userId		: userId
	}, true, function(transport, json){
		var tempArray = [];
		window._allCustomSiteId_ = [];
		var individuations = json["INDIVIDUATIONS"]["INDIVIDUATION"];
		if(individuations){
			if(Array.isArray(individuations)){
				for(var i = 0; i < individuations.length; i++){
					if($v(individuations[i], "ISCHECKED") == "1"){
						tempArray.push($v(individuations[i], "INDIVIDUATIONID"));
					}
					window._allCustomSiteId_.push($v(individuations[i], "INDIVIDUATIONID"));
				}
			}else{
				if($v(individuations, "ISCHECKED") == "1"){
					tempArray.push($v(individuations, "INDIVIDUATIONID"));
					window._allCustomSiteId_.push($v(individuations, "INDIVIDUATIONID"));
				}
			}					
		}
		var sValue = TempEvaler.evaluateTemplater('customSite_template', json["INDIVIDUATIONS"]);
		var customSites = $('customSites');
		Element.update('customSites', sValue);
		window._lastItemRow_ = null;
		customSites.style.left = position[0];
		customSites.style.top = position[1];
		if(tempArray.length == 1){
			$("li_" + tempArray[0]).style.listStyleType = "disc";		
		}
		if(window._allCustomSiteId_.length > 0){
			$('deselectAllCustomSite').style.display = '';
		}else{
			$('deselectAllCustomSite').style.display = 'none';
		}
		customSites.style.display = '';
		customSites.focus();
	});	
}
Event.observe(window, 'load', function(){
	var moreActionBubble = new com.trs.wcm.BubblePanel($('customSites'));
	moreActionBubble.doAfterHide = function(event){
		var srcElement = Event.element(event);
		if(srcElement.getAttribute("triggerEvent")){
			BasicDataHelper.call("wcm6_individuation", 'setIsChecked', {
				excludeIds : window._allCustomSiteId_.join(","),
				includeIds : srcElement.id.split("_")[1],
				refreshCustomSiteSession : true
			}, true, function(){
				var objectIdsValue = srcElement.getAttribute("objectIdsValue");
				$nav().refreshTree(function(){$nav().refreshMain();});
			});
		}else if(srcElement.getAttribute("setCustomSite")){
			MenuOperates.individuate('customSite');
		}
	};
	Event.observe('customSites', 'mousemove', function(event){
		event = window.event || event;
		var srcElement = Event.element(event);
		if(srcElement.getAttribute("triggerEvent")){
			if(window._lastItemRow_){
				window._lastItemRow_.parentNode.style.backgroundColor = "transparent";
				window._lastItemRow_.parentNode.style.color = "black";
			}
			window._lastItemRow_ = srcElement;
			window._lastItemRow_.parentNode.style.backgroundColor = "highlight";
			window._lastItemRow_.parentNode.style.color = "white";
		}	
	});
});//*/
//单击菜单更多图标时的相关处理结束

/*
*使导航树显示或隐藏
*needDealStatus:是否需要保留导航树的显示状态
*status：修改后的状态，true显示,false隐藏
*/
function showHideNav(needDealStatus, status){
	if((status === true && !isNavAdvanced()) 
			|| (status === false && isNavAdvanced())
			|| (status === undefined)){
		toggleNav();
		if(needDealStatus){
			PageContext.navigate_bar_status = !PageContext.navigate_bar_status;
		}
	}
}
function getNavStatus(){
	return Element.visible('nav_advanced');
}
/*
*使属性面板显示或隐藏
*needDealStatus:是否需要保留属性面板的显示状态
*status：修改后的状态，true显示,false隐藏
*/
function showHideAttrPanel(needDealStatus, status){
	if(status === true){
		Element.show('oper_attr_panel_td');
		Element.hide('no_panel_td');	
	}else if(status === false){
		Element.hide('oper_attr_panel_td');
		Element.show('no_panel_td');	
	}else{
		Element.toggle('oper_attr_panel_td');
		Element.toggle('no_panel_td');			
	}
	if(needDealStatus){
		if(status === undefined){
			PageContext.attribute_bar_status = !PageContext.attribute_bar_status;		
		}else{
			PageContext.attribute_bar_status = status;
		}
	}
}
function getAttrPanelStatus(){
	return Element.visible('oper_attr_panel_td');
}
var currShowCoverAllCount = 0;
function showCoverAll(_nIndex){
	var eCoverAll = $('coverall');
//		eCoverAll.parentNode.appendChild(eCoverAll);
	eCoverAll.style.position = 'absolute';
	if(_nIndex)eCoverAll.style.zIndex = _nIndex;
	eCoverAll.style.opacity	= "0.2";
	eCoverAll.style.filter = "alpha(opacity=20)";
	eCoverAll.style.display = '';
	currShowCoverAllCount++;
}
function hideCoverAll(){
	currShowCoverAllCount--;
	var eCoverAll = $('coverall');
	if(currShowCoverAllCount<=0){
		eCoverAll.style.display = 'none';
		currShowCoverAllCount = 0;
	}
	else{
		if(eCoverAll.style.zIndex>900){
			eCoverAll.style.zIndex = 898;
		}
	}
}


window._FOCUSED = true;
Event.observe(document, 'focusout', function(){
	window._FOCUSED = false;
}, false);
Event.observe(document, 'focusin', function(){
	window._FOCUSED = true;
}, false);

Event.observe(window, 'load', function(){
	Event.observe('otherFieldsContainer', 'blur', function(){
		if(!window._ISMOUSEOVER){
			Element.hide('otherFieldsContainer');
		}
	});

	Event.observe('otherFieldsContainer', 'mouseover', function(){
		window._ISMOUSEOVER = true;
	}, false);
	Event.observe('otherFieldsContainer', 'mouseout', function(){
		var event = window.event || event;
		try{
			var parentElement = $('otherFieldsContainer').contentWindow.document.body;
		}catch(error){
		}
		if(event.toElement && parentElement && !parentElement.contains(event.toElement)){
			window._ISMOUSEOVER = false;
		}
	}, false);
});
