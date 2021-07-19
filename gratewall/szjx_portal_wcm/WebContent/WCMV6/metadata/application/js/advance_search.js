var ViewDataEditor = {
	objectId			: 0,//对象的ID属性
	objectContainer		: 'objectContainer',//页面 form Element 容器
	objectForm			: 'objectForm', //表单form id
	objectTemplate		: 'objectTemplate', //textarea id
	offerBasicValidation: true,	//是否提供简单的校验
	_alertMsg_			: false,

	/**---------------------------------------选择实现的方法　STAET-------------------------------------**/
	/**
	*init data here before request.	if need, override the method.
	*/
	_beforeInitData : function(){
		if(this._alertMsg_) this._fAlert('beforeInitData...');
		if(this.beforeInitData) this.beforeInitData();
	},
	/**
	*init data here after request.	if need, override the method.
	*/
	_afterInitData : function(transport, json){
		if(this._alertMsg_) this._fAlert('afterInitData...');
		HTMLElementParser.parse();
		if(this.afterInitData) this.afterInitData(transport, json);
		adjustDimension();
	},
	/**
	*bind some events here.	if need, override the method.
	*/
	_bindEvents : function(){
		if(this._alertMsg_) this._fAlert('bindEvents...');
		if(this.bindEvents) this.bindEvents();
	},
	/**
	*compose the post data for ajax saving here. return {...} or formId.
	*if need, override the method.
	*/
	_getPostData : function(){
		if(this._alertMsg_) this._fAlert('getPostData...');
		if(this.getPostData) return this.getPostData();
		//default deal with.
		if(this.objectForm) return this.objectForm;
	},
	/**
	*valid here. if need, override the method.
	*@return	if valid return true, else return false.
	*/
	_validData : function(fValidCallBack){
		if(this._alertMsg_) this._fAlert('validData...');
		if(this.validData) return this.validData(fValidCallBack);
		return true;
	},
	/**
	*execute the ajax request for saving. if need, override the method.
	*/
	_doSave : function(){
		if(this._alertMsg_) this._fAlert('doSave...');
		if(this.doSave) {
			this.doSave();
		}else{//default deal with.
			ViewTemplateMgr.save(this.objectId, this._getPostData(), function(){
			}, this._afterDoSave.bind(this));				
		}
		try{
			FloatPanel.close();		
		}catch(error){
			window.close();
		}
	},
	_afterDoSave : function(transport, json){
		//exec something after save the data.
		//deal with the releated appendixs.
		if(this.afterDoSave){
			this.afterDoSave();
		}else{
			$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext');
			try{
				FloatPanel.close(true);		
			}catch(error){
				//just skip it. 兼容window.open方式
			}
		}
	},
	/**
	*show the operate process, if need, override the method.
	*/
	_fAlert : function(msg){
		if(this.fAlert){
			this.fAlert(msg);
		}else{//default deal with.
			alert(msg);
		}
	},
	/**---------------------------------------选择实现的方法　END-------------------------------------**/
	
	/**---------------------------------------内部实现方法　START-------------------------------------**/
	_doSave_ : function(){
		if(this._alertMsg_) this._fAlert('_doSave_...');
		//init the SimpleEditor.
		if(window.SimpleEditorCache){
			for (var i = 0; i < SimpleEditorCache.length; i++){
				var oInputElement = $(SimpleEditorCache[i][0]);
				var oAttachElement = $(SimpleEditorCache[i][1]);
				if(oInputElement && oAttachElement){
					//oInputElement.value = oAttachElement.innerHTML;
					oInputElement.value = top.getValueForEditor(oAttachElement);
				}
			}	
		}
		this._doSave();
	},
	saveData : function(){
		if(this._alertMsg_) this._fAlert('saveData...');
		if(this._validData(this._doSave_.bind(this))){
			this._doSave_();
		}
		return false;
	},
	initData : function(){	
		this._beforeInitData();
		this.loadData();
	},
	getChannelId : function(){
		var channelId = getParameter("channelId");
		if(!channelId || channelId == "undefined"){
			return "0";
		}
		return channelId;
	},
	loadData : function(){
//		ViewTemplateMgr.findById(this.objectId, {viewId : getPageParams()["viewId"]}, null, this.dataLoaded.bind(this));
		if(this.getChannelId() == "0"){
			var aFieldset = document.getElementsByTagName("FIELDSET");
			if(aFieldset.length > 0){
				aFieldset[0].style.display = 'none';
			}
			this.dataLoaded({}, {METAVIEWDATA : {}});
		}else{
			ViewTemplateMgr.findById(this.objectId, {channelid : getParameter("channelId")}, null, this.dataLoaded.bind(this));
		}
	},
	dataLoaded : function(transport, json){
		var sValue = TempEvaler.evaluateTemplater(this.objectTemplate, json);
		sValue = sValue.replace(/(<\/?\s*)_textarea(\s[^<]*>|>)/ig, '$1textarea$2');
//		Element.update(this.objectContainer, sValue);
		new Insertion.Top(this.objectContainer, sValue);
		this._afterInitData(transport, json);
		this.initDataOfMain();
		this.initEvent();
	},
	initEvent : function(){
		this._bindEvents();
		if(ViewDataEditor.offerBasicValidation){
			ValidationHelper.initValidation();
		}
	}
	/**---------------------------------------内部实现方法　END-------------------------------------**/
};

/*-----------------------------------------command button start-------------------------------*/
try{
	FloatPanel.addCloseCommand("关闭");   
	FloatPanel.addCommand('okbtn', '确定', ViewDataEditor.saveData, ViewDataEditor);
}catch(error){
	//just skip it. 兼容window.open方式
}
/*-----------------------------------------command button end---------------------------------*/

/*-----------------------------------------Validation start-------------------------------*/
if(ViewDataEditor.offerBasicValidation){
	ValidationHelper.addValidListener(function(){
		try{
			FloatPanel.disableCommand('okbtn', false);
		}catch(error){
			//just skip it. 兼容window.open方式
		}
	}, ViewDataEditor.objectForm);
	ValidationHelper.addInvalidListener(function(){
		try{
			FloatPanel.disableCommand('okbtn', true);
		}catch(error){
			//just skip it. 兼容window.open方式
		}
	}, ViewDataEditor.objectForm);
}
/*-----------------------------------------Validation end---------------------------------*/

Event.observe(window, 'load', function(){
	ViewDataEditor.initData();

	Event.observe('copyToClipBoard', 'click', function(){
		try{
			var mainWin = $main();		
			var params = Object.clone(mainWin.PageContext.params);
			Object.extend(params, formatParamsForMode(getParams(true)));
			var aExcludeItem = ["RightValue", "IsVirtual", "tab_type", "ContainsRight", "FilterType"];
			for (var i = 0; i < aExcludeItem.length; i++){
				delete params[aExcludeItem[i]];
			}
			com.trs.util.CommonHelper.copy($toQueryStr(params));
			alert("已经复制到剪切板中！");
		}catch(ex){
			alert('您的浏览器不支持自动复制操作');
		}
	});
});

function adjustDimension(){
	var minWidth = 520, minHeight = 200, maxWidth = 800, maxHeight = 500;
	var realWidth = document.body.scrollWidth;		
	var realHeight = document.body.scrollHeight;
	realWidth = realWidth > maxWidth ? maxWidth : (realWidth < minWidth ? minWidth : realWidth);
	realHeight = realHeight > maxHeight ? maxHeight : (realWidth < minHeight ? minHeight : realHeight);
	//if(realHeight == maxHeight){
		$(ViewDataEditor.objectForm).style.overflow = "auto";
	//}
	try{
		FloatPanel.setSize(realWidth, realHeight);
		//FloatPanel.show();
	}catch(error){
		//just skip it. 兼容window.open方式
	}
}

//保存选中的分类法信息
try{
	var gMainWin = $main();
}catch(error){
	var gMainWin = top.getElementById("main").contentWindow;
}
if(!gMainWin.ClassInfoDescs){
	gMainWin.ClassInfoDescs = {};
}
function getParams(isGet){
	var params = {isor:$('mode_query_isOr').checked};
	var elements = Form.getElements(ViewDataEditor.objectForm);
	for (var i = 0; i < elements.length; i++){
		var oElement = elements[i];
		if(!oElement.name || oElement.getAttribute("ignore")){
			continue;
		}
		var sName = oElement.name;
		var sValue = Form.Element.getValue(oElement);
		if(sValue == undefined) continue;
		if(isGet){
			sValue = encodeURIComponent(sValue);
		}

		//保存选中的分类法信息
		if(oElement.getAttribute("_type") == "classInfo"){
			var oSelectedClassInfo = $(sName + "_Text");
			if(oSelectedClassInfo){
				gMainWin.ClassInfoDescs[sName] = oSelectedClassInfo.innerHTML;
			}
		}

		if(!params[sName]){
			params[sName] = sValue;
		}else if(String.isString(params[sName])){
			params[sName] = [params[sName], sValue];
		}else if(Array.isArray(params[sName])){
			params[sName].push(sValue);
		}else{
			throw new Error("未知的变量类型");
		}
	}	
	return params;
}

function formatParamsForMode(params){
	//format the checkbox and classinfo.
	var aModeQuery = document.getElementsByName("mode_query");
	for (var i = 0; i < aModeQuery.length; i++){
		var elementId = aModeQuery[i].id.split("_")[1];
		if(!elementId) continue;
		if(!params[elementId]) {
			params[elementId] = [];
			continue;
		}
		if(String.isString(params[elementId])){
			params[elementId] = params[elementId].split(",");
		}
		if(Array.isArray(params[elementId])){ 
			params[elementId] = params[elementId].join(aModeQuery[i].checked ? " " : "|");
		}
	}
	return params;
}

Object.extend(ViewDataEditor, {
	// execute search.
	initDataOfMain : function(){
		var mainWin = $main();		
		var params = Object.clone(mainWin.AdvanceSearchParams || {});
		for(prop in params){
			var controls = document.getElementsByName(prop);
			if(!controls || controls.length < 0) continue;
			var bIsArray = Array.isArray(params[prop]);
			for (var i = 0; i < controls.length; i++){
				switch((controls[i].type || "").toUpperCase()){
					case 'CHECKBOX':
					case 'RADIO':
						if(Boolean.isBoolean(params[prop])){
							controls[i].checked = params[prop];
						}else{
							if(Array.isArray(params[prop])){
								params[prop] = "," + params[prop].join(",") + ",";
							}
							if(bIsArray){
								if(params[prop].indexOf("," + controls[i].value + ",") >= 0){
									controls[i].checked = true;
								}else{
									controls[i].checked = false;
								}
							}else{
								controls[i].checked = (params[prop] == controls[i].value);
							}
						}
						break;
					default:
						controls[i].value = params[prop];
						break;					
				}

				//初始化保存选中的分类法信息
				if(controls[i].getAttribute("_type") == "classInfo"){
					var oSelectedClassInfo = $(controls[i].name + "_Text");
					if(oSelectedClassInfo && gMainWin.ClassInfoDescs[controls[i].name]){
						oSelectedClassInfo.innerHTML = gMainWin.ClassInfoDescs[controls[i].name];
						delete gMainWin.ClassInfoDescs[controls[i].name];
					}
				}
			}
		}
	},
	doSave : function(){
		try{
			var params = getParams(false);
			var mainWin = $main();		
			//save the params, for init the data when opening the window next.
			mainWin.AdvanceSearchParams = mainWin.Object.clone(params);

			//format the checkbox and classinfo.
			var aModeQuery = document.getElementsByName("mode_query");
			for (var i = 0; i < aModeQuery.length; i++){
				mainWin.AdvanceSearchParams[aModeQuery[i].id] = aModeQuery[i].checked;
			}
			Object.extend(mainWin.PageContext.params, formatParamsForMode(params));			
			mainWin.PageContext.RefreshList();	
			FloatPanel.close(true);
		}catch(error){
			alert(error.message);
		}
	}
});

Object.extend(ViewDataEditor, {
	bindEvents : function(){
		var aSelectMultiValue = document.getElementsByName("selectMultiValue");
		for (var i = 0; i < aSelectMultiValue.length; i++){
			Event.observe(aSelectMultiValue[i], 'click', function(){
				var sAttachElement = this.getAttribute("_attachElement");
				var inputElement = $(sAttachElement);
				var selectElement = $(sAttachElement + "_sel");
				var aHTML = [];
				var options = selectElement.options;
				for (var i = 0; i < options.length; i++){
					aHTML.push(
						'<span UNSELECTABLE="on" title="' + options[i].text + '">',
							'<input type="checkbox" id="cbx_' + options[i].value + '" value="' + options[i].value + '">',
							'<label for="cbx_' + options[i].value + '">' + options[i].text + '</label>',
						'</span>'
					);
				}
				var multiValueContainerInner = $("multiValueContainerInner");
				multiValueContainerInner.innerHTML = aHTML.join("");

				var multiValueContainer = $("multiValueContainer");
				Position.clone(inputElement, multiValueContainer, {setWidth:false,setHeight:false,offsetTop:inputElement.offsetHeight});
				multiValueContainer.style.display = '';
				multiValueContainer.style.cursor = 'default';
				multiValueContainer.setAttribute("_attachElement", sAttachElement);

				var multiValueShield = $("multiValueShield");
				Position.clone(multiValueContainer, multiValueShield);
				multiValueShield.style.display = '';

				//set value.
				var selectedValue = inputElement.value.split(/(\s+|\s*\|\s*)/);
				for (var i = 0; i < selectedValue.length; i++){
					var oCbx = $("cbx_" + selectedValue[i]);
					if(oCbx){
						oCbx.checked = true;
					}
				}
				multiValueContainer.focus();
			}.bind(aSelectMultiValue[i]));
		}
		Event.observe('multiValueContainer', 'blur', function(event){
			var multiValueContainer = $("multiValueContainer");
			if(!multiValueContainer.getAttribute("isIn")){
				SelectMulitValue_onCancel();
			}else{
				try{
					multiValueContainer.focus();
				}catch(error){}
			}
		});
		Event.observe('multiValueContainer', 'mouseover', function(event){
			var multiValueContainer = $("multiValueContainer");
			multiValueContainer.setAttribute("isIn", true);
		});
		Event.observe('multiValueContainer', 'mouseout', function(event){
			var multiValueContainer = $("multiValueContainer");
			multiValueContainer.setAttribute("isIn", false);
		});
	}
});
function SelectMulitValue_onOk(){
	var multiValueContainer = $("multiValueContainer");
	var multiValueShield = $("multiValueShield");
	var sAttachElement = multiValueContainer.getAttribute("_attachElement");
	var inputElement = $(sAttachElement);
	var result = [];
	var aCbx = multiValueContainer.getElementsByTagName("input");
	for (var i = 0; i < aCbx.length; i++){
		if(aCbx[i].checked){
			result.push(aCbx[i].value);
		}
	}
	inputElement.value = result.join("|");
	SelectMulitValue_onCancel();
}
function SelectMulitValue_onCancel(){
	var multiValueContainer = $("multiValueContainer");
	var multiValueShield = $("multiValueShield");
	multiValueShield.style.display = 'none';
	multiValueContainer.style.display = 'none';
}

function showViewFields(btn, sInputControll){
	var viewFieldSel = $('viewFields');
	if(!viewFieldSel.isLoaded){
		new com.trs.web2frame.BasicDataHelper().call(
			'wcm6_MetaDataDef',
			'queryViewFieldInfos',
			{SelectFields:'FIELDNAME,DBFIELDNAME,ANOTHERNAME', viewId : getPageParams()["viewId"]},
			true,
			function(transport, json){
				viewFieldSel.isLoaded = true;
				var tableName = getPageParams()["tableName"];
				var aViewField = $a(json, "MetaViewFields.MetaViewField") || [];
				var aHTML = [];
				for (var i = 0; i < aViewField.length; i++){
					var value = $v(aViewField[i], "FIELDNAME");
					var text = $v(aViewField[i], "ANOTHERNAME");
					aHTML.push(
						"<div class='row' onmouseover='RowMouseOver(this);' onmouseout='RowMouseOut(this);' style='cursor:pointer;' onclick='RowClick(this);' _related='" + sInputControll +"'>",
							"<span>" + text + "</span>",
							"<div>" + (tableName + "." + value) + "</div>",
						"</div>"
					);
				}
				viewFieldSel.innerHTML = aHTML.join("");
			}
		);
	}
	if(Element.visible(viewFieldSel)){
		viewFieldSel.style.display = 'none';
	}else{
		viewFieldSel.style.display = 'block';
	}
}

window._lastActiveRow = null;
function RowMouseOver(oRow){
	oRow.style.color = "white";
	oRow.style.backgroundColor = "highlight";
}

function RowMouseOut(oRow){
	oRow.style.color = "black";
	oRow.style.backgroundColor = "white";
}

function RowClick(oRow){
	var oRelatedElement = $(oRow.getAttribute("_related"));
	if(!oRelatedElement){
		return;
	}
	var sRowValue = oRow.getElementsByTagName("div")[0].innerText;
	oRelatedElement.value = oRelatedElement.value + " " + sRowValue;
}
