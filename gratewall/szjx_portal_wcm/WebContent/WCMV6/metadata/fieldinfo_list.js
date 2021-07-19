Object.extend(PageContext,{
	//是否将数据提供者切换到本地的开关
	isLocal : false,
	
	//远程服务的相关属性
	ObjectServiceId : 'wcm6_MetaDataDef',
	ObjectMethodName : 'jQueryDBFieldInfos',
	AbstractParams : {
	},//服务所需的参数

	//为了使页面具有行为,定义Mgr对象
	ObjectMgr : FieldInfoMgr,

	//为了右侧显示操作栏和属性栏,定义右侧面板的类型
	ObjectType	: 'FieldInfo',
	enableAttrPanel : false,
	_doBeforeRefresh : function(sLocationSearch){
		/*
		if((top.actualTop || top).location_search0){
	        (top.actualTop || top).location_search = (top.actualTop || top).location_search0;
		}
		if(sLocationSearch.indexOf("tableInfoId") >= 0){
			this.sLocationSearch = sLocationSearch;
		}else if(this.sLocationSearch){
			Object.extend(PageContext.params, this.sLocationSearch.toQueryParams());
		}
		*/
		Object.extend(PageContext.params, {PageSize : -1}); 
	},
	bindBasicEvents : function(){
		Event.observe('operatorContainer', 'click', function(event){
			event = window.event || event;
			var srcElement = Event.element(event);
			var oprKey = srcElement.getAttribute("key");
			if(!oprKey) return;
			
			var objectIds = Grid.getRowIds();
			PageContext["ObjectMgr"][oprKey](objectIds);
			(Grid[oprKey + "After"] || Prototype.emptyFunction).call(Grid, objectIds);
		});

		Event.observe('returnTableInfoId', 'click', function(){
			/*
			window.location.href = 'tableinfo_thumbs_index.html';
			*/
			var oNodeInfo = $nav().getPathNodeInfo();
			var urlParams = '?SiteType=' + oNodeInfo.nodeId + "&RightValue=" + oNodeInfo.rightValue;
			(top.actualTop||top).location_search = urlParams;//记录上次访问的地址参数
			window.location.href = 'tableinfo_thumbs_index.html' + urlParams;
		});
	}
});

Object.extend(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '元数据字段'
});

Grid.handleRowClick0 = Grid.handleRowClick;
Object.extend(Grid,{
	draggable : false,
	_id : '_id',
	_rowIdPrefix : 'row_',
	_editOrSaveIdPrefix : 'editOrSave_',
	lastContainerTypes : {},
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
	keyQ : function(event){
		this.quickAdd();
	},
	keyD : function(event){//Trash
		this._delete(event);
	},
	keyDelete : function(event){//Trash
		this._delete(event);
	},
	keyN : function(event){//Edit
		//1.根据页面传入的权限判断是否有权限操作
		if(!this._hasRight("add")){
			return false;
		}

		//2.获取选中的对象ID,返回一个数组
		PageContext.ObjectMgr.add(0, PageContext.params);		
	},
	keyE : function(event){//Edit
		//1.根据页面传入的权限判断是否有权限操作
		if(!this._hasRight("edit")){
			return false;
		}

		//2.获取选中的对象ID,返回一个数组
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length == 1 ){
			PageContext.ObjectMgr.edit(pSelectedIds[0], PageContext.params);
		}
	},
	
	//==================内部方法========================//
	_hasRight : function(_sOperation){
		return true;
	},

	_delete : function(event){//Trash
		//1.根据页面传入的权限判断是否有权限操作
		if(!this._hasRight("delete")){
			return false;
		}

		//2.获取选中的对象ID,返回一个数组
		var pSelectedIds = this.getRowIds();		
		if( pSelectedIds && pSelectedIds.length>0 ){
			PageContext.ObjectMgr["delete"](pSelectedIds.join(','), PageContext.params);
		}
		this.verdict();
	},
	deleteAfter : function(sObjectId){
		this.verdict();
	},

	cancel : function(_sRowId){
		var eRow = $(this._rowIdPrefix + _sRowId);
		if(!eRow.getAttribute("realId")){//if blank row, then delete the row.
			FieldInfoMgr["delete"](_sRowId);
			this.verdict();
		}else{//return to view mode.
			this._editInRow_(_sRowId, 'object_save', 'object_edit', '', 'none', 'toPlain', false, '', 'none');
		}
	},
	
	verdict:function(){
		var firsRow = getFirstHTMLChild($('grid_data'));
		if(Element.hasClassName(firsRow, 'grid_row')){
			if(!Element.visible(firsRow)){
				Element.update('grid_data', $('divNoObjectFound').innerHTML);
			}
		}
		else{
		Element.update('grid_data', $('divNoObjectFound').innerHTML);
		}
	},

	excludeItems : function(oSelect, eRow){
		if(!eRow.getAttribute("realId")) return;
		if(oSelect.getAttribute("isExcluded") || oSelect.name != "fieldType"){
			return;
		}
		oSelect.setAttribute("isExcluded", true);
		var currDataType = oSelect.options[oSelect.selectedIndex].getAttribute("_value").toLowerCase();
		var dataTypeObjs = FieldInfoMgr.mappingDataType;
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
					if(plainElement.type == 'checkbox'){
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
				FieldInfoMgr.setEnumValue(enumValue.value, function(_params){
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
		FieldInfoMgr.save(objectId, {
			tableInfoId : getParameter("tableInfoId") || 0,
			fieldName : getNextHTMLSibling($('fieldName_' + _sRowId)).value,
			anotherName : getNextHTMLSibling($('anotherName_' + _sRowId).parentNode).value,
			fieldType : getNextHTMLSibling($('fieldType_' + _sRowId)).value,
			enmValue : getNextHTMLSibling($('enmValue_' + _sRowId)).value,
			classId : getNextHTMLSibling($('classId_' + _sRowId)).value || 0,
			dbType : getNextHTMLSibling($('dbType_' + _sRowId)).value
		}, null, function(transport, json){
			if(objectId == 0){
				var realId = com.trs.util.JSON.value(json, "MetaDBField.DBFIELDINFOID");
				eRow.setAttribute("realId", realId);
			}
			//set the title of field
			var oFieldName = $('fieldName_' + _sRowId);
			var title = getNextHTMLSibling(oFieldName).value;
			oFieldName.parentNode.setAttribute("title", title);
			oFieldName.parentNode.parentNode.setAttribute("objectName", title);

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
		var oRelateInfo = FieldInfoMgr.mappingDataType[key];
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
		var oRelateInfo = FieldInfoMgr.mappingDataType[key];
		var dbType = getNextHTMLSibling($('dbType_' + _sRowId));
		if(oRelateInfo && oRelateInfo["dbType"]){
			for (var i = 0, length = dbType.options.length; i < length; i++){
				var option = dbType.options[i];
				if(option.getAttribute("_value").toLowerCase() == oRelateInfo["dbType"]){
					dbType.value = option.value;
					break;
				}
			}
			dbType.disabled = true;
		}else{
			if(!oSelect.getAttribute("isExcluded")){
				dbType.disabled = false;	
			}else{
				dbType.disabled = true;	
			}
		}
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


Object.extend(Grid, {
	toggleAllRows : function(){
		var bSelectedAll = false;
		var rows = this._QuickGetRows(this._getSelectAbleClass());
		for (var i = 0; i < rows.length; i++){
			var eCheckBox = (document.getElementsByClassName(_GRID_['CSS_CHECKBOX'],rows[i])||[])[0];
			if(!eCheckBox.checked){
				bSelectedAll = true;
				break;
			}
		}
		var aChecked = [];
		for (var i = 0; i < rows.length; i++){
			var eCheckBox = (document.getElementsByClassName(_GRID_['CSS_CHECKBOX'],rows[i])||[])[0];
			if(bSelectedAll){
				Element.addClassName(rows[i],_GRID_['CSS_ROW_ACTIVE']);
				var eCheckBox = (document.getElementsByClassName(_GRID_['CSS_CHECKBOX'],rows[i])||[])[0];
				if(eCheckBox)eCheckBox.checked = true;
				aChecked.push(rows[i],eCheckBox);
			}else{
				Element.removeClassName(rows[i],_GRID_['CSS_ROW_ACTIVE']);
				var eCheckBox = (document.getElementsByClassName(_GRID_['CSS_CHECKBOX'],rows[i])||[])[0];
				if(eCheckBox)eCheckBox.checked = false;
			}
		}
		this.checkedElements = aChecked;

		//TODO
		//this._dealWithSelectedRows();
		setTimeout(this._dealWithSelectedRows.bind(this),0);
	}
});


Event.observe(window, 'load', function(){
	var arQueryFields = [
		{name: 'queryAnotherName', desc: '中文名称', type: 'string'},
		{name: 'queryName', desc: '英文名称', type: 'string'},
		{name: 'CrUser', desc: '创建者', type: 'string'},
		{name: 'FieldInfoId', desc: '字段ID', type: 'int'},
		{name: 'classId', desc: '分类法ID', type: 'int'}
	];
	SimpleQuery.register('query_box', arQueryFields, function(_params){
		Object.extend(PageContext.params, _params);
		PageContext.RefreshList();
	}, true);

	top.showHideAttrPanel(true, false);

	//init the tableinfo 
	var titleOfTableAnotherName = decodeURIComponent(getParameter("tableAnotherName")) || "";
	var tableAnotherName = titleOfTableAnotherName;
	if(tableAnotherName.length > 20){
		tableAnotherName = titleOfTableAnotherName.substr(0, 20) + "...";
	}
	$('tableInfo').innerHTML = $trans2Html(tableAnotherName) + "[" + getParameter("tableInfoId") + "]";
	$('tableInfo').setAttribute("title", titleOfTableAnotherName);

	PageContext.bindBasicEvents();
});

Event.observe(window, 'unload', function(){
//    (top.actualTop || top).location_search0 = null;
	top.showHideAttrPanel(true, true);
	$destroy(Grid);
});