var AdvSearch = {
	loadPage : function(){
		this.__bindRadionEvent('CrTime');
		this.__bindRadionEvent('PubTime');
		//默认搜索范围
		var $p = getParameter;
		this.setDefaultRegion($p('nodeid'), $p('nodetype'), decodeURIComponent($p('nodename')),$p('ChnlType'),$p('rootType'));
		//绑定检索选区的删除键响应
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
	render : function(){
		//首先进行校验数据是否合法
		if(Element.visible('divCrTimeInterval')){
			setAttri(["StartDate","EndDate"]);
		}else{	
			removeAttri(["StartDate","EndDate"]);			
		}
		if(Element.visible('divPubTimeInterval')){
			setAttri(["StartPubDate","EndPubDate"]);
		}else{	
			removeAttri(["StartPubDate","EndPubDate"]);		
		}
		if(!ValidationHelper.doValid('frmData')){
			return false;
		}
		if(this.__validateQueryInfo() != true) {
			return;
		}
		this.queryInfo = this.__prepareQueryInfo();
		//alert(Object.parseSource(this.queryInfo));
		if(this.queryInfo == null) {
			return;
		}
		var oSearchData = new wcm.CMSObj({
			objType : WCMConstants.OBJ_TYPE_SEARCH,
			data : this.queryInfo,
			type : 'document'
		}, null);
		oSearchData.addEvents(['search']);
		oSearchData.search();
	},
	selectRegion : function(){
		var sUrl = WCMConstants.WCM6_PATH + 'advsearch/channel_select_search.html?SiteTypes=0,2,4';
		//传入channlids/siteids
		var selectedInfo = this.getRegion();
		if(selectedInfo != null) {
			sUrl += '&' + $toQueryStr(selectedInfo);
		}
		FloatPanel.open(sUrl, (wcm.LANG.SEARCHTITLE || '文档检索的位置'), this.setRegion.bind(this));
	},
	setRegion : function(result){
		var selRegion = $('selSearchRegion');
		var eOptions = selRegion.options;
		if(eOptions.length > 0) {
			for (;selRegion.options.length>0;){
				Ext.get(selRegion.options[0]).remove();
			}
		}
		var info = result;
		if(info['channels'] == null && info['sites'] == null && info['sitetype'] == null) {
			return;
		}
		var sNodeName = '', sNodeType = '', arrItems = null;
		if(info['sites']) {
			sNodeName = wcm.LANG.WEBSITE || '站点';
			sNodeType = 's';
			arrItems = info['sites'];
		}else if(info['channels']){
			sNodeName = wcm.LANG.CHANNEL || '栏目';
			sNodeType = 'c';
			arrItems = info['channels'];
		}else{
			sNodeType = 'r';
			arrItems = info['sitetype'];
		}
		for (var i = 0; i < arrItems.length; i++){
			var item = arrItems[i];
			var eOption = document.createElement("OPTION");
			eOptions.add(eOption);
			eOption.value = item.id;
			eOption.innerHTML = (sNodeType == 'r') ? item.name
				: ('[' + sNodeName + '-' + item.id + '] ' + item.name);
			eOption.setAttribute('_type', sNodeType);
		}
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
			}
		}
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
			var ctTime = getRadioValue('CrTimeInterval');
			if(ctTime == -1) {//指定日期
				return checkComparation('StartDate', 'EndDate', wcm.LANG.CRTIME || '创建时间设置');
			}
		}
		// pubtime
		if(Element.visible('divPubTime')) {
			var ctTime = getRadioValue('PubTimeInterval');
			if(ctTime == -1) {//指定日期
				return checkComparation('StartPubDate', 'EndPubDate', wcm.LANG.PUBTIME || '发布时间设置');
			}
		}	
		return true;
	},
	__prepareQueryInfo : function(){
		var result = {
			IsSearch : 1,
			disableTab : 1
		};
		var detail = {};
		//title and author
		Object.extend(result, this.__appendTextParam('DOCTITLE', 'txtDocTitle'));
		Object.extend(result, this.__appendTextParam('CRUSER', 'txtCrUser'));
		//siteids, channelids
		var regionInfo = this.getRegion();
		if(regionInfo == null) {
			Ext.Msg.alert(wcm.LANG.ALERTPLACE || '请指定要搜索的位置!');
			return null;
		}
		Object.extend(result, regionInfo);
		// crtime
		if(Element.visible('divCrTime')) {
			var ctTime = getRadioValue('CrTimeInterval');
			if(ctTime == -1) {//指定日期
				//TODO
				Object.extend(result, {
					'STARTDATE': $('StartDate').value,
					'ENDDATE': $('EndDate').value
				});
			}else if (ctTime != 0){
				result['CRTIMEINTERVAL'] = ctTime;
			}
		}
		// pubtime
		if(Element.visible('divPubTime')) {
			var ctTime = getRadioValue('PubTimeInterval');
			if(ctTime == -1) {//指定日期
				//TODO
				Object.extend(result, {
					'STARTPUBDATE': $('StartPubDate').value,
					'ENDPUBDATE': $('EndPubDate').value
				});
			}else if (ctTime != 0){
				result['PUBTIMEINTERVAL'] = ctTime;
			}
		}
		//高级选项
		if(Element.visible('divAdvanced')) {
			//doc source
			var sDocSource = $('txtDocSource').value;
			if(sDocSource != '') {
				result['DOCSOURCENAME'] = encodeURIComponent(sDocSource);
//					result['DOCSOURCE'] = nDocSource;
			}
			//doc status
			var arrDocStatus = this.__getCheckBoxValue('DocStatus');
			if(arrDocStatus.length != 0) {
				result['DOCSTATUS'] = arrDocStatus.join(',');
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
			var sDocSource = $('txtDocSource').value;
			if(sDocSource != ''){
				result['DOCSOURCENAME'] = encodeURIComponent(sDocSource);
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
					arrStatus.push({VALUE: encodeURIComponent(elm.value), LABEL: encodeURIComponent(elm.getAttribute('_label', 2))});
				}
			}
			if(arrStatus.length != 0) {
				result['DOCSTATUS'] = arrStatus;
			}
		}

		//region
		var arrRegion = [];
		var eOptions = $('selSearchRegion').options;
		if(eOptions.length > 0) {
			for (var i = 0; i < eOptions.length; i++){
				arrRegion.push({ID: eOptions[i].value, NAME: encodeURIComponent(eOptions[i].innerHTML)});
			}
		}
		delete eOptions;
		result['REGION'] = arrRegion;

		//alert(Object.parseSource(result));
		return result;
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
	},
	setDefaultRegion : function(_nNodeId, _sNodeType, _sNodeName,_nChnlType,_sRootType){
		//对链接型和表单型栏目,直接返回
		//表单类型的栏目不需要返回 add by liuhm@1010-1-18
		if(_nChnlType == 11) return;
		if(_sRootType.indexOf("r_1") != -1 || _sRootType.indexOf("r_2") != -1) return;
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
			sNodeName = String.format('[站点 - {0}]',_nNodeId);
		}else if(_sNodeType == 'c') {
			sNodeName = String.format('[栏目 - {0}]',_nNodeId);
		}else if(_sNodeType == 'r'){
			//do nothin'
		}else{
			return;
		}
		sNodeName += _sNodeName;

		var eOption = document.createElement("OPTION");
		eOptions.add(eOption);
		eOption.value = _nNodeId;
		eOption.innerHTML = sNodeName;
		eOption.setAttribute('_type', _sNodeType);
		//eOption.selected = true;
		delete eOption;
	},
	__appendTextParam : function(_sName, _sId){
		var sVal = $(_sId).value;
		if(sVal.trim() == '') {
			return {};
		}
		//else
		result = {};
		result[_sName] = encodeURIComponent(sVal);
		return result;
	}
};
function checkComparation(_sFormerId, _sLaterId, _sFieldDesc){
	var former = $(_sFormerId);
	var later = $(_sLaterId);
	if(former == null || later == null) {
		return;
	}
	//else
	var sFormerVal = former.value;
	var sLaterVal = later.value;
	if($compareDate(sFormerVal, sLaterVal) > 0) {
		Ext.Msg.alert(String.format("{0}:结束时间不应该小于开始时间!",_sFieldDesc));
		return false;
	}

	return true;
}
function $compareDate(dateStr1, dateStr2, dateFormat){
	dateFormat = dateFormat || "%y-%m-%d %H-%M";
	date1 = Date.parseDate(dateStr1, dateFormat);
	date2 = Date.parseDate(dateStr2, dateFormat);
	return date1.getTime() - date2.getTime();
}

Event.observe(window, 'load', function(){
	//检索页面初始化
	AdvSearch.loadPage();
});

function detectRemoving(event){
	var evt = event || window.event;
	var keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
	if(keyCode == 46 || keyCode == 68) {
		var selRegion = $('selSearchRegion');
		if(selRegion.selectedIndex == -1) {
			return;
		}
		selRegion.remove(selRegion.selectedIndex);
	}
}
//检索页中相关JS
function defaultTime(_bIsEnd){
	var dt = new Date().format("yyyy-mm-dd HH:MM");
	return dt;
}
function displayAdvItems(){
	displayBlock('Advanced', true);
	ensureDocSource();
}
function ensureDocSource(){
	if(ensureDocSource.isLoaded) return;
	ensureDocSource.isLoaded = true;
	//检索区文档来源suggestion设置
	var sg1 = new wcm.Suggestion();
	sg1.init({
		el : 'txtDocSource',
		request : function(sValue){
			var all = [];
			BasicDataHelper.JspRequest(
			WCMConstants.WCM6_PATH + "nav_tree/source_create.jsp?SourceName=",
			{},  true,
			function(transport, json){
				var result = eval(transport.responseText.trim());
				for (var i = 0; i < result.length; i++){
					var sGroup = {};
					sGroup.value = result[i].title;
					sGroup.label = result[i].desc;
					all.push(sGroup);
				}
				var items = [];
				for (var i = 0; i < all.length; i++){
					if(all[i].label.toUpperCase().indexOf(sValue.toUpperCase()) >= 0) items.push(all[i]);
				}
				sg1.setItems(items);
			});
		}
	});
}
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
	var eMask = $('div' + _sFieldName + 'Interval');	
	var rdVal = getRadioValue(_sFieldName + 'Interval');
	enableTimeSelect(_sFieldName, false);
	//alert(_sFieldName + ', ' + rdVal);
	if(rdVal != -1 && Element.visible(block)) {
		Element.hide(eMask);
	}else{
		Element.show(eMask);
	}	
}
function getRadioValue(_sName){
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
}
function enableTimeSelect(_sFieldName, _bFlag){
	var elm = $('div' + _sFieldName + 'Interval');
	if(elm == null) {
		return;
	}
	//else
	//var eMask = $('divMask' + _sFieldName + 'Interval');
	//alert(_sFieldName + ', ' + eMask)
	if(_bFlag === false) { // 不设置时间,显示mask
		//Position.clone(elm, eMask);
		Element.hide(elm);
	}else{// 设置时间,隐藏mask
		Element.show(elm);
	}
}
function repositionPubMask(){
	var elm = $('divPubTimeInterval');
	var eMask = $('divMaskPubTimeInterval');
	if(Element.visible(elm) && Element.visible(eMask)) {
		Position.clone(elm, eMask);
	}	
}
var DocSourceHelper = {
	NotifyInput : function(){
		$('txtDocSource').value = $('selDocSource').value;
	},
	NotifySelect : function(){
		$('selDocSource').value = $('txtDocSource').value;
	}
}

function setAttri (group){
	for(var i=0;i<group.length;i++){
		$(group[i]).setAttribute("validation","type:date,date_format:yyyy-mm-dd hh:MM ");
	}
}
function removeAttri (group){
	for(var i=0;i<group.length;i++){
		$(group[i]).removeAttribute("validation");
	}
}