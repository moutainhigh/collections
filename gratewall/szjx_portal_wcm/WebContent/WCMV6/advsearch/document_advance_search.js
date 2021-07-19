Object.extend(PageContext, {
	loadPage : function(){
		this.__bindRadionEvent('CrTime');
		this.__bindRadionEvent('PubTime');
		//默认搜索范围
		var $p = getParameter;
		this.setDefaultRegion($p('nodeid'), $p('nodetype'), decodeURIComponent($p('nodename')));

		//捕捉栏目/站点选区列表的删除键响应
		Event.observe($('selSearchRegion'), 'keydown', detectRemoving, false);
	},
	__bindRadionEvent : function(_sElmClsName){
		var rds = document.getElementsByName(_sElmClsName + 'Interval');
		for (var i = 0; i < rds.length; i++){
			var rd = rds[i];
			Event.observe(rd, 'click', function(){
				if(this.value == -1) {
					enableTimeSelect(_sElmClsName, true);
				}else{
					enableTimeSelect(_sElmClsName, false);
				}
			}.bind(rd), false);//*/

			delete rd;
		}		
	},
	setSource : function(){
		var sources = com.trs.util.JSON.toUpperCase(com.trs.wcm.AllDocSource);
		if(sources) {
			var sValue = TempEvaler.evaluateTemplater('template_docsource', 
				sources, {});
			Element.update($('doc_source'), sValue);
		}
		this.m_bSourceStatusPrepared = true;
	},
	setStatus : function(){
		var status = com.trs.util.JSON.toUpperCase(com.trs.wcm.AllDocStatus);
		if(status) {
			var sValue = TempEvaler.evaluateTemplater('template_status', 
				status, {});
			Element.update($('doc_status'), sValue);
		}
		this.m_bSourceStatusPrepared = true;
	},
	setDefaultRegion : function(_nNodeId, _sNodeType, _sNodeName){
		_nNodeId = parseInt(_nNodeId);
		_sNodeType = (_sNodeType || '').trim();
		_sNodeName = (_sNodeName || '').trim();


		var selRegion = $('selSearchRegion');
		var eOptions = selRegion.options;
		if(eOptions.length > 0) {
			for (var i = eOptions.length; i >0;){
				eOptions.remove(--i);
			}
		}

		if(isNaN(_nNodeId) || _sNodeType == '' || _sNodeName == '') {
			return;
		}
		
		var sNodeName = '';
		if(_sNodeType == 's') {
			sNodeName = '[站点-' + _nNodeId + '] ';
		}else if(_sNodeType == 'c') {
			sNodeName = '[栏目-' + _nNodeId + '] ';
		}else if(_sNodeType == 'r'){
			//do nothin'
		}else{
			return;
		}
		sNodeName += _sNodeName;

		var eOption = document.createElement("OPTION");
		eOptions.add(eOption);
		eOption.value = _nNodeId;
		eOption.innerText = sNodeName;
		eOption.setAttribute('_type', _sNodeType);
		//eOption.selected = true;
		delete eOption;
	},
	render : function(){
		var sUrlNormal	= 'document/document_normal_index.html?disable_sheet=1';
		var sUrlRead	= 'document/document_readmode_index.html';
		//首先进行校验数据是否合法
		if(this.__validateQueryInfo() != true) {
			return;
		}
		this.queryInfo = this.__prepareQueryInfo();
		if(this.queryInfo == null) {
			return;
		}
		// 判断是否正处于阅读模式
		var main = $MessageCenter.getMain();
		if(main && main.location.href.indexOf(sUrlRead) != -1) {
			$tab().enableTabSheets(false);
			window.setTimeout(function(){
				main.PageContext.search(this.queryInfo);
			}.bind(this), 10);
			return;
		}
		//else
		var sSearch = (top.actualTop||top).location_search;
		$MessageCenter.changeSrc('main', sUrlNormal, this.queryInfo);
	},
	getQueryInfo : function(){
		return this.queryInfo;
	},
	selectRegion : function(){
		var sUrl = './advsearch/channel_select_search.html';
		//传入channlids/siteids
		var selectedInfo = this.getRegion();
		if(selectedInfo != null) {
			sUrl += '?' + $toQueryStr(selectedInfo);
		}
		FloatPanel.open(sUrl, '文档检索的栏目/站点', 300, 370);
		
	},
	setRegion : function(_oSelectedInfo){
		//alert($toQueryStr(_oSelectedInfo));
		var selRegion = $('selSearchRegion');
		var eOptions = selRegion.options;
		if(eOptions.length > 0) {
			for (var i = eOptions.length; i >0;){
				eOptions.remove(--i);
			}
		}
		var info = _oSelectedInfo;
		if(info['channels'] == null && info['sites'] == null && info['sitetype'] == null) {
			delete eOptions;
			return;
		}
		var sNodeName = '', sNodeType = '', arrItems = null;
		if(info['sites']) {
			sNodeName = '站点';
			sNodeType = 's';
			arrItems = info['sites'];
		}else if(info['channels']){
			sNodeName = '栏目';
			sNodeType = 'c';
			arrItems = info['channels'];
		}else{
			sNodeType = 'r';//TODO 可能需要支持其他站点类型
			arrItems = info['sitetype'];
		}
		for (var i = 0; i < arrItems.length; i++){
			var item = arrItems[i];
			var eOption = document.createElement("OPTION");
			eOptions.add(eOption);
			eOption.value = item.id;
			eOption.innerText = (sNodeType == 'r') ? item.name
				: ('[' + sNodeName + '-' + item.id + '] ' + item.name);
			eOption.setAttribute('_type', sNodeType);
			//delete eOption;
		}
		//delete eOptions;
	},
	getRegion : function(){
		var arrSiteId = [], arrChnlId = [], sSiteType = null;
		var eOptions = $('selSearchRegion').options;
		if(eOptions.length > 0) {
			for (var i = 0; i < eOptions.length; i++){
				var eOption = eOptions[i];
				var sNodeType = eOption.getAttribute('_type', 2);
				if(sNodeType == 's') {
					arrSiteId.push(eOption.value);
				}else if(sNodeType == 'c'){
					arrChnlId.push(eOption.value);
				}else if(sNodeType == 'r'){
					sSiteType = eOption.value;
				}
				delete eOption;
			}
		}
		delete eOptions;
		var result = new Object();
		if(arrChnlId.length != 0) {
			result['channelids'] = arrChnlId.join(',');
		}else if(arrSiteId.length != 0) {
			result['siteids'] = arrSiteId.join(',');
		}else if(sSiteType != null){
			result['sitetype'] = sSiteType;
		}else{
			return null;
		}
		return result;
	},
	__validateQueryInfo : function(){
		// crtime
		if(Element.visible('divCrTime')) {
			var ctTime = this.getRadioValue('CrTimeInterval');
			if(ctTime == -1) {//指定日期
				return CanlenderHelper.checkComparation('StartDate', 'EndDate', '创建时间设置');
			}
		}
		// pubtime
		if(Element.visible('divPubTime')) {
			var ctTime = this.getRadioValue('PubTimeInterval');
			if(ctTime == -1) {//指定日期
				return CanlenderHelper.checkComparation('StartPubDate', 'EndPubDate', '发布时间设置');
			}
		}	
		return true;
	},
	__prepareQueryInfo : function(){
		var result = {};
		var detail = {};
		//title and author
		Object.extend(result, this.__appendTextParam('DOCTITLE', 'txtDocTitle'));
		Object.extend(result, this.__appendTextParam('CRUSER', 'txtCrUser'));
		Object.extend(result, this.__appendTextParam('DOCAUTHOR', 'txtDocAuthor'));

		//siteids, channelids
		var regionInfo = this.getRegion();
		if(regionInfo == null) {
			$fail('请指定要搜索的站点或栏目！');
			return null;
		}
		Object.extend(result, regionInfo);

		// crtime
		if(Element.visible('divCrTime')) {
			var ctTime = this.getRadioValue('CrTimeInterval');
			if(ctTime == -1) {//指定日期
				//TODO
				Object.extend(result, {
					'STARTDATE': $('StartDate').value + ':00',
					'ENDDATE': $('EndDate').value + ':59'
				});
			}else if (ctTime != 0){
				result['CRTIMEINTERVAL'] = ctTime;
			}
		}
		// pubtime
		if(Element.visible('divPubTime')) {
			var ctTime = this.getRadioValue('PubTimeInterval');
			if(ctTime == -1) {//指定日期
				//TODO
				Object.extend(result, {
					'STARTPUBDATE': $('StartPubDate').value + ':00',
					'ENDPUBDATE': $('EndPubDate').value + ':59'
				});
			}else if (ctTime != 0){
				result['PUBTIMEINTERVAL'] = ctTime;
			}
		}
		//高级选项
		if(Element.visible('divAdvanced')) {
			//doc source
			if(this.m_bSourceStatusPrepared) {
				//doc source
				var sDocSource = $('txtDocSource').value;
				if(sDocSource != '') {
					result['DOCSOURCENAME'] = sDocSource;
//					result['DOCSOURCE'] = nDocSource;
				}
				//doc status
				var arrDocStatus = this.__getCheckBoxValue('DocStatus');
				if(arrDocStatus.length != 0) {
					result['DOCSTATUS'] = arrDocStatus.join(',');
				}
			}

			//keywords
			Object.extend(result, this.__appendTextParam('DOCKEYWORDS', 'txtDocKeywords'));

			//contains children?
			if($('chkContainsChildren').checked) {
				result['CONTAINSCHILDREN'] = true;
			}
		}
		
		detail = Object.extend({}, result);
		Object.extend(detail, this.__prepareSpecailDetail(detail));
		result.queryInfoDetail = detail;

		//alert(Object.parseSource(result));
		return result;
	},
	__prepareSpecailDetail : function(_rawInfo){
		var result = {};
		// crtime
		if(Element.visible('divCrTime')) {
			if(_rawInfo['CRTIMEINTERVAL']) {
				try{
					result['CRTIMEINTERVAL'] = this.__getIntervalDesc(_rawInfo['CRTIMEINTERVAL']);
				}catch(err){
					//TODO logger
					//alert(err.message);
				}
				
			}
		}
		// pubtime
		if(Element.visible('divPubTime')) {
			if(_rawInfo['PUBTIMEINTERVAL']) {
				try{
					result['PUBTIMEINTERVAL'] = this.__getIntervalDesc(_rawInfo['PUBTIMEINTERVAL']);
				}catch(err){
					//TODO logger
					//alert(err.message);
				}
			}
		}

		//高级选项
		if(Element.visible('divAdvanced')) {
			if(this.m_bSourceStatusPrepared) {
				var sDocSource = $('txtDocSource').value;
				if(sDocSource != ''){
					result['DOCSOURCENAME'] = sDocSource;
				}
				/*
				if(eSelSources.value != -1) {
					var eSelOption = eSelSources.options[eSelSources.selectedIndex];
					result['DOCSOURCE'] = {
						VALUE: eSelOption.getAttribute('value', 2),
						LABEL: eSelOption.text
					};
				}
				*/
				var elms = document.getElementsByName('DocStatus');
				var arrStatus = [];
				for (var i = 0; i < elms.length; i++){
					var elm = elms[i];
					if(elm.checked) {
						arrStatus.push({VALUE: elm.value, LABEL: elm.getAttribute('_label', 2)});
					}
				}
				if(arrStatus.length != 0) {
					result['DOCSTATUS'] = arrStatus;
				}
			}
		}

		//region
		var arrRegion = [];
		var eOptions = $('selSearchRegion').options;
		if(eOptions.length > 0) {
			for (var i = 0; i < eOptions.length; i++){
				arrRegion.push({ID: eOptions[i].value, NAME: eOptions[i].innerText});
			}
		}
		delete eOptions;
		result['REGION'] = arrRegion;

		//alert(Object.parseSource(result));
		return result;
	},
	__getIntervalDesc : function(_nIntervalType){
		if(this.arrIntervalDesc == null) {
			this.arrIntervalDesc = ['三天内', '一周内', '一月内'];
		}
		return this.arrIntervalDesc[_nIntervalType - 5];
	},
	__appendTextParam : function(_sName, _sId){
		var sVal = $(_sId).value;
		if(sVal.trim() == '') {
			return {};
		}
		//else
		result = {};
		result[_sName] = sVal;
		return result;
	},
	getRadioValue : function(_sName){
		var elms = document.getElementsByName(_sName);
		if(elms == null || elms.length <= 0) {
			return null;
		}
		//else
		for (var i = 0; i < elms.length; i++){
			var elm = elms[i];
			if(elm.checked) {
				return elm.value;
			}
		}

		return null;
	},
	__getCheckBoxValue : function(_sName){
		var elms = document.getElementsByName(_sName);
		if(elms == null || elms.length <= 0) {
			return null;
		}
		//else
		var result = [];
		for (var i = 0; i < elms.length; i++){
			var elm = elms[i];
			if(elm.checked) {
				result.push(elm.value);
			}
		}

		return result;
	}
});

function displayBlock(_sFieldName, _bJustSwitchBlockDisp){
	var block = $('div' + _sFieldName);
	if(block == null) {
		return;
	}

	if(Element.visible(block)) {
		Element.hide(block);
	}else{
		Element.show(block);
	}

	if(_bJustSwitchBlockDisp == true) {
		return;
	}
	//else
	var eMask = $('divMask' + _sFieldName + 'Interval');	
	var rdVal = PageContext.getRadioValue(_sFieldName + 'Interval');
	enableTimeSelect(_sFieldName, false);
	//alert(_sFieldName + ', ' + rdVal);
	if(rdVal != -1 && Element.visible(block)) {
		Element.show(eMask);
	}else{
		Element.hide(eMask);
	}
	
}

function enableTimeSelect(_sFieldName, _bFlag){
	var elm = $('div' + _sFieldName + 'Interval');
	if(elm == null) {
		return;
	}
	//else
	var eMask = $('divMask' + _sFieldName + 'Interval');
	//alert(_sFieldName + ', ' + eMask)
	if(_bFlag === false) { // 不设置时间，显示mask
		Position.clone(elm, eMask);
		Element.show(eMask);
	}else{// 设置时间，隐藏mask
		Element.hide(eMask);
	}
}
//
function repositionPubMask(){
	var elm = $('divPubTimeInterval');
	var eMask = $('divMaskPubTimeInterval');
	if(Element.visible(elm) && Element.visible(eMask)) {
		Position.clone(elm, eMask);
	}	
}
function detectRemoving(event){
	var evt = event || window.event;
	if(evt.keyCode == Event.KEY_DELETE || evt.keyCode == 68) {
		var selRegion = $('selSearchRegion');
		if(selRegion.selectedIndex == -1) {
			return;
		}
		selRegion.options.remove(selRegion.selectedIndex);
	}
}

CanlenderHelper = {
	checkComparation : function(_sFormerId, _sLaterId, _sFieldDesc){
		var former = $(_sFormerId);
		var later = $(_sLaterId);
		if(former == null || later == null) {
			return;
		}
		//else
		var sFormerVal = former.value;
		var sLaterVal = later.value;
		if($compareDate(sFormerVal, sLaterVal) > 0) {
			$alert(_sFieldDesc + '：结束时间不应该小于开始时间！');
			return false;
		}

		return true;
	},
	drawApdatedTime : function(_sId, _bIsEnd){
		var dt = new Date();
		var sAdaptedTime = _bIsEnd == true ? '23:59:59' : '00:00:00';
		sAdaptedTime = dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + sAdaptedTime;
		TRSCalendar.drawWithTime(_sId, sAdaptedTime);
	}
}

//ge gfc add @ 2007-8-3 15:42 在页面加载完后调用
function doWhenSwitch2Search(){
	return;
	//每次切换到搜索页（非第一次加载），就更新一下DocSource数据
	BasicDataHelper.JspRequest('../system/source_create.jsp',
		null, false, 
		function(_transport,_json){
			eval(_transport.responseText);
			PageContext.setSource();
		}
	);
	
}

var DocSourceHelper = {
	NotifyInput : function(){
		$('txtDocSource').value = $('selDocSource').value;
	},
	NotifySelect : function(){
		$('selDocSource').value = $('txtDocSource').value;
	}
};