 Object.extend(PageContext,{
	//是否将数据提供者切换到本地的开关
	isLocal : false,
	
	//远程服务的相关属性
	ObjectServiceId : 'wcm6_MetaDataDef',
	ObjectMethodName : 'jQueryViewFieldInfos',
	AbstractParams : {
	},//服务所需的参数

	//为了使页面具有行为,定义Mgr对象
	ObjectMgr : ViewFieldInfoMgr,

	//为了右侧显示操作栏和属性栏,定义右侧面板的类型
	ObjectType	: 'ViewFieldInfo',
	enableAttrPanel : false,

	_doBeforeRefresh : function(sLocationSearch){
		Object.extend(PageContext.params, {PageSize : -1}); 
	},
	/**
	*根据页面的输入参数，初始化一些额外参数信息
	*/
	initParams : function(fCallBack){
		var channelId = PageContext.params["channelid"] || getParameter("channelid");
		if(channelId){//导航树选中栏目
			$viewInfoMgr.getViewFromChannel(channelId, null, null, function(transport, json){
				PageContext._initParam_(json);
				if(fCallBack) fCallBack();
			});
			return;
		}
		var viewId = PageContext.params["viewId"] || getParameter("viewId");
		if(viewId){//导航树选中资源库
			$viewInfoMgr.findById(viewId, null, null, function(transport, json){
				PageContext._initParam_(json);
				if(fCallBack) fCallBack();
			});
			return;
		}
	},
	_initParam_ : function(json){
		if(json && json["METAVIEW"]){
			PageContext.viewId = com.trs.util.JSON.value(json, "METAVIEW.VIEWINFOID");		
			PageContext.viewDesc = com.trs.util.JSON.value(json, "METAVIEW.VIEWDESC");
			var viewName = com.trs.util.JSON.value(json, "METAVIEW.VIEWNAME");
			var mainTableName = com.trs.util.JSON.value(json, "METAVIEW.MAINTABLENAME");
			PageContext.isSingleTable = (viewName.toLowerCase() == mainTableName.toLowerCase());
		}
	},
	_doAfterBound : function(_transport, _json){
		if(!this._doAfterBounded){
			this._doAfterBounded = true;
			this.initParams(this.doAfterBound2.bind(this, _transport, _json));
		}else{
			this.doAfterBound2(_transport, _json);
		}
	},
	allHasRight : false,
	checkRight : function(){
		return this.allHasRight;
	},
	doAfterBound2 : function(_transport, _json){
		this.initHeadAndOperators();
		var isNull = _transport.getResponseHeader("IsNull");
		var sDisplayMethod1 = "show";
		var sDisplayMethod2 = "hide";
		if(isNull == 'true'){
			sDisplayMethod1 = "hide";
			sDisplayMethod2 = "show";
		}
		var aElementIds1 = ['selectDBFieldsOperator', 'selectFieldsOperator', 'generateOperator', 'setAsTitleOperator', 'setAsRetrieveOperator', 'deleteOperator', 'viewInfo'];
		var aElementIds2 = ['addViewOperator'];
		for (var i = 0; i < aElementIds1.length; i++){
			Element[sDisplayMethod1](aElementIds1[i]);
		}
		for (var i = 0; i < aElementIds2.length; i++){
			Element[sDisplayMethod2](aElementIds2[i]);
		}

		var hasRight = _transport.getResponseHeader("HasRight");
		this.allHasRight = hasRight == 'true' ? true : false;
		if(hasRight == "true"){
			var aElementIds1 = ['selectDBFieldsOperator', 'selectFieldsOperator', 'generateOperator', 'setAsTitleOperator', 'setAsRetrieveOperator', 'deleteOperator', 'viewInfo'];
			for (var i = 0; i < aElementIds1.length; i++){
				Element["removeClassName"](aElementIds1[i], "no_right");
				$(aElementIds1[i]).disabled = false;
			}
		}

		//多表情况下，隐藏维护物理字段的操作入口
		if(!PageContext.isSingleTable){
			Element.hide('selectDBFieldsOperator');
		}
	},
	initHeadAndOperators : function(){
		Element.eachChild('operatorContainer', function(element){
			Element.hide(element);
		});
		if(PageContext.params["channelid"]){//栏目下的视图字段列表页面
			var showArray = ['addViewOperator', 'setViewOperator', 'selectDBFieldsOperator', 'selectFieldsOperator', 'generateOperator', 'setAsTitleOperator', 'setAsRetrieveOperator', 'deleteOperator'];
			for (var i = 0; i < showArray.length; i++){
				Element.show(showArray[i]);
			}
		}else{//资源库下的视图字段列表页面
			var showArray = ['returnTableInfoId', 'selectDBFieldsOperator', 'selectFieldsOperator', 'generateOperator', 'setAsTitleOperator', 'setAsRetrieveOperator', 'deleteOperator'];
			for (var i = 0; i < showArray.length; i++){
				Element.show(showArray[i]);
			}
		}
		Element.show('operatorContainer');
		this.setViewHead();
	},
	setViewHead : function(viewId, viewDesc){
		viewId = viewId || this.viewId;
		viewDesc = viewDesc || this.viewDesc || "";
		var titleOfviewDesc = viewDesc;
		if(viewDesc.length > 20){
			viewDesc = titleOfviewDesc.substr(0, 20) + "...";
		}
		$('viewInfo').innerHTML = $trans2Html(viewDesc) + "[" + viewId + "]";
		$('viewInfo').setAttribute("title", titleOfviewDesc);
		Element.show('viewInfo');
	},
	getViewInfo : function(){
		if(PageContext.viewId){
			return {
				objectId	: PageContext.viewId,
				objectName	: PageContext.viewDesc
			};
		}
	},
	//执行操作命令的预处理，如果返回false，则不再执行相应的操作
	beforeExecCommand : function(oprKey){
		var aExclude = ['addView', 'setView'];
		for(var index = 0; index < aExclude.length; index++){
			if(aExclude[index] == oprKey){
				return true;
			}
		}
		return this.allHasRight;
	},
	bindBasicEvents : function(){
		Event.observe('operatorContainer', 'click', function(event){
			event = window.event || event;
			var srcElement = Event.element(event);
			var oprKey = srcElement.getAttribute("key");
			if(!oprKey) return;
			var objectIds = Grid.getRowIds();
			if(PageContext.beforeExecCommand(oprKey) === false){
				return;
			}
			if(PageContext["ObjectMgr"][oprKey]){
				PageContext["ObjectMgr"][oprKey](objectIds);
			}else if(PageContext[oprKey]){
				PageContext[oprKey](objectIds);
			}
		});
		Event.observe('returnTableInfoId', 'click', function(){
			//(top.actualTop||top).PageContext.viewInfoMode = 'view';
			var oNodeInfo = $nav().getPathNodeInfo();
			var urlParams = '?SiteType=' + oNodeInfo.nodeId + "&RightValue=" + oNodeInfo.rightValue;
			(top.actualTop||top).location_search = urlParams;//记录上次访问的地址参数
			window.location.href = 'viewinfo_thumbs_index.html' + urlParams;
		});
	},
	/**
	*页面顶部的一些操作
	*/
	addView : function(objectIds){
		this._doAfterBounded = false;
		$viewInfoMgr.add(0, {channelId:PageContext.params["channelid"] || 0});
	},
	generate : function(objectIds){
		$viewInfoMgr.generate(PageContext.viewId);
	},
	_delete : function(_sObjectIds){
		if(!_sObjectIds || _sObjectIds.length <= 0){
			$alert("请选择某些行再进行操作");
			return;
		}
		ViewFieldInfoMgr["delete"](_sObjectIds);
	},
	setAsTitle : function(_sObjectIds){
		//1. get the realid.
		if(typeof _sObjectIds == 'string'){
			_sObjectIds = _sObjectIds.split(",");
		}
		var objectIds = [];
		for (var i = 0; i < _sObjectIds.length; i++){
			var eRow = $("row_" + _sObjectIds[i]);
			if(eRow){
				 var realId = eRow.getAttribute("realId");
				 if(realId){
					 objectIds.push(realId);
				 }
			}
		}
		if(objectIds.length <= 0 || objectIds.length >= 2){
			$alert("请选择某一行再进行操作");
			return;
		}
		//2.she the title field.
		ViewFieldInfoMgr.save(objectIds, {titleField : 1, inOutline : 1}, null, function(){
			PageContext.afterSetAsTitle(objectIds);
			//$success('设置标题字段成功。');
		});
	},
	lastTitleFieldObjectId : -1, //上一次标题字段的id
	titleFieldCls : 'titleField', //标题字段的样式
	afterSetAsTitle : function(objectIds){
		var sObjectId = objectIds[0];
		//移除原来标题字段的样式
		if(this.lastTitleFieldObjectId != -1){
			Element.removeClassName($('cbx_' + this.lastTitleFieldObjectId).parentNode, this.titleFieldCls);
			$('cbx_' + this.lastTitleFieldObjectId).parentNode.title = "";
			getNextHTMLSibling($('inOutline_' + this.lastTitleFieldObjectId)).disabled = false;
		}

		//给新标题字段添加样式
		Element.addClassName($('cbx_' + sObjectId).parentNode, this.titleFieldCls);
		$('cbx_' + sObjectId).parentNode.title = "标题字段";
		Element.update('inOutline_' + sObjectId, "是");
		getNextHTMLSibling($('inOutline_' + sObjectId)).disabled = true;
		this.lastTitleFieldObjectId = sObjectId;
	},
	setAsRetrieve : function(_sObjectIds){
		//1. get the realid.
		if(typeof _sObjectIds == 'string'){
			_sObjectIds = _sObjectIds.split(",");
		}
		var objectIds = [];
		for (var i = 0; i < _sObjectIds.length; i++){
			var eRow = $("row_" + _sObjectIds[i]);
			if(eRow){
				 var realId = eRow.getAttribute("realId");
				 if(realId){
					 objectIds.push(realId);
				 }
			}
		}
		if(objectIds.length <= 0){
			$alert("请选择某些行再进行操作");
			return;
		}
		//2.she the retrieve field.
		ViewFieldInfoMgr.setViewFieldsInfo(objectIds, {propertyName : "SEARCHFIELD"}, null, function(){
			$success('设置检索字段成功。');
		});
	},
	setView : function(objectIds){
		this._doAfterBounded = false;
		$viewInfoMgr.selectView(PageContext.viewId, null, function(args){
			var viewId = args.ids || 0;
			PageContext.viewId = viewId;
			var isContainsChildren = args["ContainsChildren"] || false;
			$viewInfoMgr.setChannelView(viewId, {
				channelid : PageContext.params.channelid,
				ContainsChildren : isContainsChildren
			}, null, function(){
				PageContext.RefreshList();
			});
		});
	},
	selectDBFields : function(){
		this._doAfterBounded = false;
		if(PageContext.isSingleTable == false){
			$viewInfoMgr.editMultiTable(PageContext.viewId);
		}else if(PageContext.isSingleTable == true){
			$viewInfoMgr.addEditStepTwo(PageContext.viewId, null);
		}
	},
	selectFields : function(){
		this._doAfterBounded = false;
		$viewInfoMgr.editMultiTable(PageContext.viewId);
		return;
		//修改成只弹出多表的情况
		if(PageContext.isSingleTable == false){
			$viewInfoMgr.editMultiTable(PageContext.viewId);
		}else if(PageContext.isSingleTable == true){
			$viewInfoMgr.addEditStepTwo(PageContext.viewId, null);
		}
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '视图字段'
});

Grid.handleRowClick0 = Grid.handleRowClick;
Object.extend(Grid,{
	draggable : true,
	_id : '_id',
	_rowIdPrefix : 'row_',
	_editOrSaveIdPrefix : 'editOrSave_',
	lastContainerTypes : {},
	_doBeforeRowToggle : function(event , _eRow){
		var srcElement = Event.element(event);
		if((srcElement.nodeName.toUpperCase()=='INPUT' && srcElement.type.toLowerCase() == "checkbox")
				||srcElement.nodeName.toUpperCase()=='A'){
			return true;
		}
		return this.checkRight(_eRow);
	},
	checkRight : function(_eRow){
		var nHasRight = _eRow.getAttribute("hasRight");
		if(nHasRight == 1){
			return true;
		}
		return false;				
	},
	
	bindRowEvents : function(eRow){
		Event.observe(eRow,'click',this.handleRowClick.bindAsEventListener(this,eRow));
		if(this.draggable){
			var d = new com.trs.wcm.ListDragger(eRow);
			d.daton = this.gridId;
			this.draggers.push(d);
		}
		this.arrEvOb.push(eRow);		
	},
	handleRowClick : function(event , _eRow){
		var srcElement = Event.element(event);
		if(!Element.hasClassName(srcElement, 'grid_checkbox') && 
				(['INPUT', 'SELECT', 'TEXTAREA'].include(srcElement.tagName.toUpperCase())
				|| srcElement.getAttribute("noSelected"))){
			return;
		}
		if(!Element.hasClassName(_eRow, this._getSelectAbleClass()))return;
		this.handleRowClick0.apply(this, arguments); 
	},

	keyV : function(){
		if(PageContext.allHasRight){
			if(!Element.visible("addViewOperator")) return;
			PageContext.addView();
		}
	},
	keyS : function(){
		if(PageContext.allHasRight){
			if(!Element.visible("setViewOperator")) return;
			PageContext.setView();
		}
	},
	keyC : function(){	
		if(PageContext.allHasRight){
			if(!Element.visible("selectFieldsOperator")) return;
			PageContext.selectFields();
		}
	},
	keyT : function(){
		if(PageContext.allHasRight){
			if(!Element.visible("setAsTitleOperator")) return;
			PageContext.setAsTitle(this.getRowIds());
		}
	},
	keyR : function(){
		if(PageContext.allHasRight){
			if(!Element.visible("setAsRetrieveOperator")) return;
			PageContext.setAsRetrieve(this.getRowIds());
		}
	},
	keyG : function(){
		if(PageContext.allHasRight){
			if(!Element.visible("generateOperator")) return;
			PageContext.generate();
		}
	},
	keyQ : function(event){
		if(PageContext.allHasRight){
			return;
			this.quickAdd();
		}
	},
	keyD : function(event){//Trash
		if(PageContext.allHasRight){
			this._delete(event);
		}
	},
	keyDelete : function(event){//Trash
		if(PageContext.allHasRight){
			this._delete(event);
		}
	},
	keyN : function(event){//Edit
		return;
		//1.根据页面传入的权限判断是否有权限操作
		if(PageContext.allHasRight){
			if(!this._hasRight("add")){
				return false;
			}

			//2.获取选中的对象ID,返回一个数组
			PageContext.ObjectMgr.add(0, PageContext.params);	
		}
	},
	keyE : function(event){//Edit
		//1.根据页面传入的权限判断是否有权限操作
		if(PageContext.allHasRight){
			if(!this._hasRight("edit")){
				return false;
			}

			//2.获取选中的对象ID,返回一个数组
			var pSelectedIds = this.getRowIds();		
			if( pSelectedIds && pSelectedIds.length == 1 ){
				PageContext.ObjectMgr.edit(pSelectedIds[0], PageContext.params);
			}
		}
	},
	
	//==================内部方法========================//
	_hasRight : function(_sOperation){
		return true;
	},

	_delete : function(event){//Trash
		if(!Element.visible("deleteOperator")) return;
		
		//1.根据页面传入的权限判断是否有权限操作
		if(!this._hasRight("delete")){
			return false;
		}
		 
		//2.获取选中的对象ID,返回一个数组
		var pSelectedIds = this.getRowIds();		
		if(!pSelectedIds || pSelectedIds.length<=0 ){
			$alert("请选择某些行再进行操作");
			return;
		}
		PageContext.ObjectMgr["delete"](pSelectedIds.join(','), PageContext.params);
	},

	cancel : function(_sRowId){
		var eRow = $(this._rowIdPrefix + _sRowId);
		if(!eRow.getAttribute("realId")){//if blank row, then delete the row.
			ViewFieldInfoMgr["delete"](_sRowId);
		}else{//return to view mode.
			eRow.removeAttribute('draggable');
			this._editInRow_(_sRowId, 'object_save', 'object_edit', '', 'none', 'toPlain', false, '', 'none');
		}
	},

	excludeItems : function(oSelect, eRow){
		if(!eRow.getAttribute("realId")) return;
		if(oSelect.getAttribute("isExcluded") || oSelect.name != "fieldType"){
			return;
		}
		oSelect.setAttribute("isExcluded", true);
		var currDataType = oSelect.options[oSelect.selectedIndex].getAttribute("_value").toLowerCase();
		var dataTypeObjs = ViewFieldInfoMgr.mappingDataType;
		var currDBType = dataTypeObjs[currDataType]["dbType"];
		if(!currDBType){
			var aIdInfo = eRow.id.split("_");
			var _sRowId = aIdInfo[aIdInfo.length - 1];
			var oDBTypeSpan = $("dbType_" + _sRowId);
			var dbTypeSel = getNextHTMLSibling(oDBTypeSpan);
			dbTypeSel.value = oDBTypeSpan.getAttribute("_id");
			currDBType = dbTypeSel.options[dbTypeSel.selectedIndex].getAttribute("_value").toLowerCase();
		}
		for (var i = 0; i < oSelect.options.length; i++){
			var option = oSelect.options[i];
			var dataType = option.getAttribute("_value").toLowerCase();
			if(dataType == 'selfdefine') continue;
			var dbType = dataTypeObjs[dataType]["dbType"];
			if(dbType != currDBType){
				oSelect.options.remove(i);
				i--;
			}
		}
	},

	_dealWithElementValue_ : function(plainElement, oFormElement, sSetType, eRow){
		if(sSetType == 'toElement'){
			switch(oFormElement.tagName.toUpperCase()){
				case "INPUT":
					//var initValue = plainElement.innerHTML.unescapeHTML();
					var initValue = plainElement.innerText;
					if(oFormElement.type == 'checkbox'){
						oFormElement.checked = initValue == "是" ? true : false;
					}else{
						oFormElement.value = initValue;
					}
					break;
				case "SELECT":
					var initValue = plainElement.getAttribute(this._id);
					//initValue = initValue || plainElement.innerHTML.unescapeHTML();
					initValue = initValue || plainElement.innerText;
					oFormElement.value = initValue;
					this.excludeItems(oFormElement, eRow);
					break;
				default:
					break;
			}
		}else{
			switch(oFormElement.tagName.toUpperCase()){
				case "INPUT":
					if(oFormElement.type == 'checkbox'){
						plainElement.innerHTML = oFormElement.checked ? "是" : "否";
					}else{
						plainElement.innerHTML = oFormElement.value.escapeHTML();
					}
					break;
				case "SELECT":
					if(plainElement.getAttribute(this._id) != null){
						plainElement.setAttribute(this._id, oFormElement.value);
						if(oFormElement.selectedIndex == -1){
							var initValue = "";
						}else{
							var initValue = oFormElement.options[oFormElement.selectedIndex].innerHTML.escapeHTML();
						}
					}else{
						var initValue = oFormElement.innerHTML.escapeHTML();
					}
					plainElement.innerHTML = initValue;
					break;
				default:
					break;
			}
		}		
	},
	_editInRow_ : function(_sRowId, sRemoveClassName, 
			sAddClassName, sPlainDisplay, sFormElementDisplay, sSetType, isRelatedValue, deleteDisplay, cancelDisplay){
		var editOrSave = $(this._editOrSaveIdPrefix + _sRowId);
		Element.removeClassName(editOrSave, sRemoveClassName);
		Element.addClassName(editOrSave, sAddClassName);
		var eRow = $(this._rowIdPrefix + _sRowId);
		var formElements = Form.getElements(eRow);
		for (var i = 0; i < formElements.length; i++){
			if(!formElements[i].getAttribute("formElement")) continue;
			var plainElement = $(formElements[i].name + "_" + _sRowId);
			if(!plainElement) continue;
			if(isRelatedValue){
				this._dealWithElementValue_(plainElement, formElements[i], sSetType, eRow);
			}
			plainElement.style.display = sPlainDisplay;
			formElements[i].style.display = sFormElementDisplay;
		}		
		$('delete_' + _sRowId).style.display = deleteDisplay;
		$('cancel_' + _sRowId).style.display = cancelDisplay;
	},
	editInRow : function(_sRowId, fSaveCallBack){
		var editOrSave = $(this._editOrSaveIdPrefix + _sRowId);
		if(Element.hasClassName(editOrSave, 'object_edit')){//edit action.
			this.doEdit(_sRowId);
		}else{//save action.
			this.doSave(_sRowId, fSaveCallBack);
		}
	},
	doEdit : function(_sRowId){
		//do something before start edit.
		this.doBeforeEditRow(_sRowId);
		//change some style for edit.
		this._editInRow_(_sRowId, 'object_edit', 'object_save', 'none', '', 'toElement', true, 'none', '');
		//do something after start edit. eg. bind events.
		this.doAfterEditRow(_sRowId);		
	},
	doSave : function(_sRowId, fMustDoCallBack, fSaveCallBack){
		//do something before start save. eg. check valid.
		if(!this.doBeforeSaveRow(_sRowId)) return;
		//do something after start save.
		this.doAfterSaveRow(_sRowId, function(){
			//unbind events.
			this.unloadEvent(_sRowId);
			//change some style for save.
			this._editInRow_(_sRowId, 'object_save', 'object_edit', '', 'none', 'toPlain', true, '', 'none');
			//eg. add blank row.
			if(fMustDoCallBack) fMustDoCallBack();
		}.bind(this), fSaveCallBack);
	},
	doBeforeEditRow : function(){},
	doBeforeSaveRow : function(_sRowId){
		//check no releated elements are valid here.
		var oAnotherName = getNextHTMLSibling($('anotherName_' + _sRowId).parentNode);
		if(oAnotherName){
			var msg = null;
			if(/[<>;&]/.test(oAnotherName.value)){
				msg = "<font color='red'>中文名称中含有特殊字符<b>;&&lt;&gt;</b></font>.";
			}else if(/^\d+/.test(oAnotherName.value)){
				msg = "<font color='red'>中文名称不能以数字开头</font>.";
			}
			if(msg){
				try{
					$alert(msg, function(){
						$dialog().hide();
						setTimeout(function(){
							oAnotherName.focus();
						}, 10);
					});
				}catch(error){
					alert(msg);
					oAnotherName.focus();
				}
				return false;
			}
		}
		if(!ValidationHelper.doValid(this._rowIdPrefix + _sRowId)){
			return false;
		}
		//valid the enumValueContainer element.
		/*
		var enumValue = $('enmValueContainer_' + _sRowId).getElementsByTagName("input")[0];
		if(Element.realVisible(enumValue)){
			var warning = '';
			if(enumValue.value.length <= 0){
				warning = "枚举值不能为空。";
			}else if(enumValue.value.length > 120){
				warning = "枚举值长度不能超过120。";
			}
			if(warning){
				changeBorderStyle(enumValue, $ValidatorConfigs["WARNING_BORDER"]);
				$alert("枚举值不能为空。", function(){
					$dialog().hide();
					enumValue.focus();
				});
				return false;
			}else{
				changeBorderStyle(enumValue);
			}
		}
		*/

		//valid the key words.
		var aFields = [getNextHTMLSibling($("fieldName_" + _sRowId))];
		if(containKeyWordsInFields(aFields)){
			return false;
		}

		return true;
	},
	doAfterEditRow : function(_sRowId){
		//bind event.
		var eRow = $(this._rowIdPrefix + _sRowId);
		eRow.setAttribute('draggable', 'false');
		var selects = eRow.getElementsByTagName("select");
		for (var i = 0; i < selects.length; i++){
			var changeEvent = selects[i].getAttribute("changeEvent");
			if(changeEvent){
				selects[i].onchange = this[changeEvent].bind(this, selects[i], _sRowId);
			}
		}
		eRow.onkeydown = this.enterFormElement.bind(this, _sRowId);

		//bind enumvalue event.
		var enumValue = $('enmValueContainer_' + _sRowId).getElementsByTagName("input")[0];
		if(enumValue){
			enumValue.ondblclick = function(){
				ViewFieldInfoMgr.setEnumValue(enumValue.value, function(_params){
					enumValue.value = _params ? _params["enumValue"] : "";
				});
			}
		}

		//bind title event.
		PopTip.registerAtOnce([enumValue]);

		//fire event for init value.
		for (var i = 0; i < selects.length; i++){
			selects[i].fireEvent("onchange");
		}
	},
	enterFormElement : function(_sRowId, event){
		event = window.event || event;
		if(event.keyCode != Event.KEY_RETURN){
			return;
		}
		var srcElement = Event.element(event);
		if(!["INPUT", "SELECT", "TEXTAREA"].include(srcElement.tagName.toUpperCase())){
			return;
		}
		var eRow = $(this._rowIdPrefix + _sRowId);
		var nextRow = getNextHTMLSibling(eRow);
		if(!nextRow || !Element.visible(nextRow)){//the last visible row
			this.doSave(_sRowId, null, this.quickAdd.bind(this));
		}else{//not the last visible row, then save the row
			this.doSave(_sRowId, null, null);
		}
	},
	unloadEvent : function(_sRowId){
		//1. unbind event.
		var eRow = $(this._rowIdPrefix + _sRowId);
		var selects = eRow.getElementsByTagName("select");
		for (var i = 0; i < selects.length; i++){
			var changeEvent = selects[i].getAttribute("changeEvent");
			if(changeEvent){
				selects[i].onchange = null;
			}
		}
		eRow.onkeydown = null;

		//bind enumvalue event.
		var enumValue = $('enmValueContainer_' + _sRowId).getElementsByTagName("input")[0];
		if(enumValue){
			enumValue.ondblclick = null;
		}
	
		//unbind title event.
		PopTip.unRegisterAtOnce([enumValue]);
	},
	doAfterSaveRow : function(_sRowId, fMusbDoCallBack, fSaveCallBack){
		//1. save data.
		var eRow = $(this._rowIdPrefix + _sRowId);
		var objectId = eRow.getAttribute("realId") || 0;
		ViewFieldInfoMgr.save(objectId, {
			viewId : PageContext.viewId || 0,
			fieldName : getNextHTMLSibling($('fieldName_' + _sRowId)).value,
			anotherName : getNextHTMLSibling($('anotherName_' + _sRowId).parentNode).value,
			fieldType : getNextHTMLSibling($('fieldType_' + _sRowId)).value,
			enmValue : getNextHTMLSibling($('enmValue_' + _sRowId)).value,
			classId : getNextHTMLSibling($('classId_' + _sRowId)).value || 0,
			inOutline : getNextHTMLSibling($('inOutline_' + _sRowId)).checked ? 1 : 0,
			dbType : getNextHTMLSibling($('dbType_' + _sRowId)).value
		}, null, function(transport, json){
			eRow.removeAttribute('draggable');
			var sViewFieldName = com.trs.util.JSON.value(json, "MetaViewField.FIELDNAME");
			if(objectId == 0){
				var realId = com.trs.util.JSON.value(json, "MetaViewField.VIEWFIELDINFOID");
				eRow.setAttribute("realId", realId);
			}

			//set the title of field
			var oFieldName = $('fieldName_' + _sRowId);
			getNextHTMLSibling(oFieldName).value = sViewFieldName;
			oFieldName.parentNode.setAttribute("title", sViewFieldName);
			oFieldName.parentNode.parentNode.setAttribute("objectName", sViewFieldName);

			//set the title of another name.
			var oAnotherName = $('anotherName_' + _sRowId).parentNode;
			var title = getNextHTMLSibling(oAnotherName).value;
			oAnotherName.parentNode.setAttribute("title", title);

			//keep the exec sequence.
			if(fMusbDoCallBack) fMusbDoCallBack();
			if(objectId == 0 && fSaveCallBack) fSaveCallBack(_sRowId, transport, json);
		});
	},
	dataTypeChange : function(oSelect, _sRowId, event){
		//1. get the option key.
		var fieldType = oSelect;
		var option = fieldType.options[fieldType.selectedIndex];
		var key = option.getAttribute("_value").toLowerCase();

		//2. deal with the enumValue or classId. 
		var oRelateInfo = ViewFieldInfoMgr.mappingDataType[key];
		if(this.lastContainerTypes[_sRowId] && $(this.lastContainerTypes[_sRowId])){
			Element.hide(this.lastContainerTypes[_sRowId]);
		}
		if(oRelateInfo && oRelateInfo["containerType"]){
			this.lastContainerTypes[_sRowId] = oRelateInfo["containerType"] + "_" + _sRowId;
			if($(this.lastContainerTypes[_sRowId])){
				Element.show(this.lastContainerTypes[_sRowId]);
			}
		}

		//3. deal with the dbType.
		var oRelateInfo = ViewFieldInfoMgr.mappingDataType[key];
		var dbType = getNextHTMLSibling($('dbType_' + _sRowId));
		if(oRelateInfo && oRelateInfo["dbType"]){
			for (var i = 0, length = dbType.options.length; i < length; i++){
				var option = dbType.options[i];
				if(option.getAttribute("_value").toLowerCase() == oRelateInfo["dbType"]){
					dbType.value = option.value;
					break;
				}
			}
//			dbType.disabled = true;
		}else{
//			dbType.disabled = false;
		}
		dbType.disabled = true;
	},
	quickAdd : function(){
		var firsRow = getFirstHTMLChild($('grid_data'));
		//remove objectNotFound
		if(!Element.hasClassName(firsRow, 'grid_row')){
			Element.remove(firsRow);
		}
		if(this._nextIndex_ == null){
			this._nextIndex_ = 0;
		}else{
			this._nextIndex_++;
		}
		var eRow = $("row_n" + this._nextIndex_);
		if(eRow){//have blank row.
			eRow.style.display = '';
			$('editOrSave_n' + this._nextIndex_).click();
			setTimeout(function(){
				eRow.getElementsByTagName("input")[1].focus();
			}, 10);
		}else{//no blank row.
			var sUrl = "fieldinfo_blankrows.jsp";
			var oRequest = new Ajax.Request(sUrl, {
				method : 'get',
				parameters : "START_INDEX=" + this._nextIndex_,
				asynchronous: false
			});
			if(oRequest.responseIsSuccess()){
				//1. update the content.
				new Insertion.Bottom('grid_data', oRequest.transport.responseText);
				//2. adjust colors.
				this.adjustColors();
				//3. bind rows event.
				var eRow = $("row_n" + this._nextIndex_);
				while(eRow){
					this.bindRowEvents(eRow);
					eRow = getNextHTMLSibling(eRow);
				}
				this._nextIndex_--;
				this.quickAdd();
			}
		}
	},

	//使用传入的权限
	_getRight : function(){
		return PageContext.params["RightValue"];
	}
});

Event.observe(window, 'load', function(){
	var arQueryFields = [
		{name: 'queryAnotherName', desc: '中文名称', type: 'string'},
		{name: 'queryFieldName', desc: '英文名称', type: 'string'},
		{name: 'CrUser', desc: '创建者', type: 'string'},
		{name: 'ViewFieldInfoId', desc: '字段ID', type: 'int'},
		{name: 'classId', desc: '分类法ID', type: 'int'}
	];
	SimpleQuery.register('query_box', arQueryFields, function(_params){
		Object.extend(PageContext.params, _params);
		PageContext.RefreshList();
	}, true);
	top.showHideAttrPanel(true, false);
	PageContext.bindBasicEvents();
	//PageContext.initHeadAndOperators();

});
 

Event.observe(window, 'unload', function(){
	top.showHideAttrPanel(true, true);
	$destroy(Grid);
});

/**
*同步新建/修改视图时的刷新方法
*/
var ViewInfos = {
	refreshList : function(objectId, objectDesc){
		PageContext.viewId = objectId;
		PageContext.viewDesc = objectDesc;
		PageContext.setViewHead(objectId, objectDesc);
	}
};
var ViewFieldsInfos = {
	refreshList : function(objectId){
		PageContext.viewId = objectId;
		if(PageContext.params.channelid){
			$viewInfoMgr.setChannelView(objectId, {channelId : PageContext.params.channelid}, null, function(){
				PageContext.RefreshList();
			});
		}else{
			PageContext.RefreshList();
		}
	}
};


//拖动功能
Object.extend(com.trs.wcm.ListDragger.prototype, {
	_isSortable : function(_eRoot){
		return PageContext.checkRight();
	},
	_getHint : function(_eRoot){
		return _eRoot.getAttribute("objectName");
	},
	_move : function(_eCurr,_iPosition,_eTarget,_eTargetMore){
			if(!_eCurr.getAttribute('realId') || !_eTarget.getAttribute('realId')){
				return false;
			}
			var oPostData = {
				fromId : _eCurr.getAttribute('realId'),
				toId : _eTarget.getAttribute('realId'),
				position : _iPosition
			};
			ViewFieldInfoMgr.changeViewFieldOrder(oPostData, function(){
				Grid.adjustColors();
			});
			return true;
	}
});

var $render500Err0 = window.DefaultAjax500CallBack0 = window.DefaultAjax500CallBack;
var $render500Err = window.DefaultAjax500CallBack = function(_trans, _json, _bIsJson, _fClose){
	try{
	if(_json){
		var code = $v(_json,'fault.code');
		if(_bIsJson === true) {
			code = _json.code;
		}
		if(code == '1011'){
			var fClose = fClose;
			_fClose = function(){
				FaultDialog.hide();
				$nav().refreshMain();
				if(fClose) fClose();
			};
		}
	}
	window.DefaultAjax500CallBack0(_trans, _json, _bIsJson, _fClose);
	}catch(error){alert(error.message);}
}
