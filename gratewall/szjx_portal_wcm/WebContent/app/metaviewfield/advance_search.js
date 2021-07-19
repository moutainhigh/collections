var ViewDataEditor = {
	objectId			: 0,//对象的ID属性
	objectForm			: 'objectForm', //表单form id
	offerBasicValidation: true,	//是否提供简单的校验
	_alertMsg_			: false,

	//元素事件绑定需要的参数
	classInfoSelectIdSuffix		: '_SelectClassInfo',
	classInfoTextIdSuffix		: '_Text',

	getChannelId : function(){
		var channelId = getParameter("channelId");
		if(!channelId || channelId == "undefined"){
			return "0";
		}
		return channelId;
	},
	dataLoaded : function(){
		this.initDataOfMain();
		this.initEvent();
	},
	initEvent : function(){
		this.adjustDimension();
		this.bindEvents();
		if(ViewDataEditor.offerBasicValidation){
			ValidationHelper.initValidation();
		}
	},
	adjustDimension : function(){
		var minWidth = 520, minHeight = 200, maxWidth = 800, maxHeight = 500;
		var realWidth = document.body.scrollWidth;		
		var realHeight = document.body.scrollHeight;
		realWidth = realWidth > maxWidth ? maxWidth : (realWidth < minWidth ? minWidth : realWidth);
		realHeight = realHeight > maxHeight ? maxHeight : (realWidth < minHeight ? minHeight : realHeight);
		$(ViewDataEditor.objectForm).style.overflow = "auto";

	}
};


Object.extend(ViewDataEditor, {
	// execute search.
	initDataOfMain : function(){
		var iframeObj = (top.actualTop||top).document.getElementById("main");
		var params = Object.clone(iframeObj.AdvanceSearchParams || {});
		for(prop in params){
			var controls = document.getElementsByName(prop);
			if(!controls || controls.length < 0) continue;
			var bIsArray = Array.isArray(params[prop]);
			for (var i = 0; i < controls.length; i++){
				switch((controls[i].type || "").toUpperCase()){
					case 'CHECKBOX':
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
					case 'RADIO':
						break;
					default:
						controls[i].value = params[prop];
						break;					
				}

				//初始化保存选中的分类法信息
				if(controls[i].getAttribute("_type") == "classInfo"){
					var oSelectedClassInfo = $(controls[i].name + "_Text");
					var oSelectedClassInfoInput = $(controls[i].name);
					var sClassInfoDesc = gMainWin.ClassInfoDescs[controls[i].name];
					if(!sClassInfoDesc){
						oSelectedClassInfoInput.value = "";
					}
					if(oSelectedClassInfo && sClassInfoDesc){
						oSelectedClassInfo.innerHTML = sClassInfoDesc;
						delete gMainWin.ClassInfoDescs[controls[i].name];
					}
				}
			}
		}
	},
	search : function(){
		//校验时间范围 (这里因为时间控件的输入框是控件代码生成，不能很好的被标示，故这里只用了这个class来标示时间类型)
		var dateTimeFields = document.getElementsByClassName("inputtext");
		if(dateTimeFields && dateTimeFields.length > 0 && dateTimeFields.length%2 == 0){
			for(var i = 0; i < dateTimeFields.length; i=i+2)
			if(Date.dateDiff(Date.str2date(dateTimeFields[i].value), Date.str2date(dateTimeFields[i+1].value)) > 0){
				var errMsg = wcm.LANG.METAVIEWDATA_115 || "开始时间大于结束时间，请重新输入.";
				Ext.Msg.alert(errMsg, function(){
					setTimeout(function(){
						dateTimeFields[i].focus();
					}, 10);
				});
				return false;
			}
			
		}
		var iframeObj = (top.actualTop||top).document.getElementById("main");
		var params = getParams(false);
		//save the params, for init the data when opening the window next.
		iframeObj.AdvanceSearchParams = Object.clone(params);

		var aModeQuery = document.getElementsByName("mode_query");
		for (var i = 0; i < aModeQuery.length; i++){
			iframeObj.AdvanceSearchParams[aModeQuery[i].id] = aModeQuery[i].checked;
		}
		
		if(iframeObj){
			notifyFPCallback(formatParamsForMode(params));
			FloatPanel.close();
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
						'<span style="width:80px;display:inline-block;" UNSELECTABLE="on" title="' + options[i].text + '">',
							'<input type="checkbox" id="cbx_' + options[i].value + '" value="' + options[i].value + '">',
							'<label for="cbx_' + options[i].value + '">' + options[i].text + '</label>',
						'</span>'
					);
					if(i%2 == 1){
					aHTML.push("<br/>")}
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

		//绑定form中带输入框元素的事件。
		var elements = document.getElementsByName('controller');
		for (var i = 0; i < elements.length; i++){
			var eventElement = elements[i].parentNode;//因为此处元素name和id都取的字段名，name = id
			var type = eventElement.getAttribute('_type');
			if(type == 'classInfo'){
				//构造参数和属性
				var name = eventElement.getAttribute('_name');
				var elementid = eventElement.id;
				//classinfo事件1
				Event.observe(elementid, 'click', function(event){
					
					//此处用Event.element(event)，否则当出现多个分类法字段时用上面的eventElement找不准元素。
					var _eventElement = Event.element(event || window.event);
					var _name = _eventElement.getAttribute('_name');
					var classId = _eventElement.getAttribute("_classId");
					var sClassInfoTextId = _name + ViewDataEditor.classInfoTextIdSuffix ;
					var inputElement = Element.previous(_eventElement);
					var selectedValue = inputElement.value;
					if(classId == "0"){
						Ext.Msg.alert(wcm.LANG.METAVIEWDATA_90 || "没有设置字段的分类法信息！");
						return;
					}
					wcm.ClassInfoSelector.selectClassInfoTree({
						objectId : classId,
						treeType : _eventElement.getAttribute("_treeType"),
						selectedValue : selectedValue
					}, function(_args){
						if(_args.ids.length == 0){
							inputElement.value = "";
							$(sClassInfoTextId).innerHTML = "";
						}else{
							var aResult = [];
							for (var i = 0, length = _args.ids.length; i < length; i++){
								aResult.push(_args.names[i] + "[" + _args.ids[i] + "]");
							}
							$(sClassInfoTextId).innerHTML = $trans2Html(aResult.join(","));
							$(_name).value = _args.ids.join();
						}
					});
				});
			
			}
			if(type == 'inputselect'){
				var sAttachElement = eventElement.getAttribute("_attachElement");
				var sSelectId = sAttachElement + "_sel";
				//init the value.
				var _value = $F(sAttachElement);
				var options = $(sSelectId).options;
				for (var j = 0; j < options.length; j++){
					if(options[j].getAttribute("_value") == _value){
						$(sAttachElement).value = options[j].value;
						break;
					}
				}

				Event.observe(sSelectId, 'click', function(event){
					var _eventElement = Event.element(event || window.event);
					var _sAttachElement = _eventElement.getAttribute("_attachElement");
					var _sSelectId = _sAttachElement + "_sel";
					var oInput = $(_sAttachElement);
					var oSelect = $(_sSelectId);
					oSelect.value = oInput.value;
				});
				Event.observe(sSelectId, 'change', function(event){
					var _eventElement = Event.element(event || window.event);
					var _sAttachElement = _eventElement.getAttribute("_attachElement");
					var _sSelectId = _sAttachElement + "_sel";
					var oInput = $(_sAttachElement);
					var oSelect = $(_sSelectId);
					oInput.value = oSelect.value;
					ValidationHelper.forceValid(oInput);			
				});
				
		
			}
		}
	}
});


/*-----------------------------------------command button start-------------------------------*/
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'search',
		name : wcm.LANG.METAVIEWDATA_1 || '确定'
	}],
	size : [550, 500]
};
/*-----------------------------------------command button end---------------------------------*/
function search(){
	ViewDataEditor.search();
	return false;
}

/*-----------------------------------------Validation start-------------------------------*/
if(ViewDataEditor.offerBasicValidation){
	ValidationHelper.addValidListener(function(){
		try{
			FloatPanel.disableCommand('search', false);
		}catch(error){
			
		}
	}, ViewDataEditor.objectForm);
	ValidationHelper.addInvalidListener(function(){
		try{
			FloatPanel.disableCommand('search', true);
		}catch(error){
			
		}
	}, ViewDataEditor.objectForm);
}
/*-----------------------------------------Validation end---------------------------------*/

Event.observe(window, 'load', function(){
	//加载jsp数据后操作
	ViewDataEditor.dataLoaded();

	//初始化分组信息：
	initFieldGroup();
});

//保存选中的分类法信息
try{
	var gMainWin = (top.actualTop||top).document.getElementById("main");
}catch(error){
	var gMainWin = top.getElementById("main").contentWindow;
}
if(!gMainWin.ClassInfoDescs){
	gMainWin.ClassInfoDescs = {};
}

function getParams(isGet){
	var params = {isor:$('mode_query_isOr').checked};
	var elements = Form.getElements(ViewDataEditor.objectForm);
	var bAdvanceParams = {
		"notNull":'2',
		"notEdit":'2',
		"hiddenField":'2',
		"IsURLField":'2',
		"inOutline":'2',
		"titleField":'2',
		"searchField":'2',
		"inDetail":'2',
		"identityField":'2'
	};

	for (var i = 0; i < elements.length; i++){
		var oElement = elements[i];
		if(!oElement.name || oElement.getAttribute("ignore")){
			continue;
		}
		var sName = oElement.name;
		//注意ff下取值要用element.value，不能用element.getAttribute("value")
		var sValue = oElement.value;
		if(oElement.type =='radio' || oElement.type =='checkbox'){
			if(oElement.checked)
				sValue = oElement.value;
			else {
				sValue = null;
			}
		}
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
		if(bAdvanceParams[sName] && bAdvanceParams[sName]==sValue) continue;
		if(sName=='FieldGroupId' && sValue=='-1') continue;
		if(!params[sName]){
			params[sName] = sValue;
		}else if(String.isString(params[sName])){
			params[sName] = [params[sName], sValue];
		}else if(Array.isArray(params[sName])){
			params[sName].push(sValue);
		}else{
			throw new Error(wcm.LANG.METAVIEWDATA_91 || "未知的变量类型");
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

	var elements = Form.getElements(ViewDataEditor.objectForm);
	for (var i = 0; i < elements.length; i++){
		var oElement = elements[i];
		if(!oElement.name || oElement.getAttribute("ignore")){
			continue;
		}
		var sName = oElement.name;
		//注意ff下取值要用element.value，不能用element.getAttribute("value")
		var sValue = oElement.value;
		if(!sValue) continue;
		params[sName] =	getValueFromLable(oElement, params[sName]);
	}
	return params;
}


function getValueFromLable(el, sDefaultValue){
	if(el.getAttribute("_type") != "inputselect") return sDefaultValue;
	var sValue = el.value;
	var aValue = sValue.split("|");
	var sel = $(el.id + "_sel");
	var opts = sel.options;
	var result = [];
	for(var index = 0; index < aValue.length; index++){
		sel.value = aValue[index];
		if(sel.selectedIndex != -1){	
			result.push(opts[sel.selectedIndex].getAttribute("_value"));
		}
	}
	return result.join("|");
}


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
			'wcm61_metaviewfield',
			'queryViewFieldInfos',
			{SelectFields:'FIELDNAME,DBFIELDNAME,ANOTHERNAME', viewId : getPageParams()["viewId"], pageSize:-1},
			true,
			function(transport, json){
				viewFieldSel.isLoaded = true;
				var tableName = getPageParams()["tableName"];
				var aViewField = $a(json, "MetaViewFields.MetaViewField") || [];
				var aHTML = [];
				for (var i = 0; i < aViewField.length; i++){
					//此处value取dbfieldname，解决视图英文字段名和元数据英文字段名不一致的问题。
					var value ;
					if(getPageParams()["isMutiTable"]){
						value = $v(aViewField[i], "FIELDNAME");
					}else{
						value = $v(aViewField[i], "DBFIELDNAME");
					}
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

function checkSQL(_oPostData, _fValid, _fInValid){
	(_fValid || Ext.emptyFn)();
	return;
}

function initFieldGroup(){
	var fieldGroupSelect = $('FieldGroupId');
	if(!fieldGroupSelect)return;
	var oPostData = {
		MetaViewId : getPageParams()["viewId"]
	}
	var currGroupId = fieldGroupSelect.getAttribute("_value") || 0;
	BasicDataHelper.JspRequest('../metaviewfieldgroup/viewfield_group_create.jsp',oPostData,false,function(_trans,_json){
		//移除当前的options
		for(var i=fieldGroupSelect.length-1;i>0;i--){
			if(i>0)fieldGroupSelect.remove(i);	
		}
		var groupInfos = com.trs.util.JSON.eval(_trans.responseText.trim());
		for(var i=0;i<groupInfos.length;i++){
			var groupInfo = groupInfos[i];
			var eOption = document.createElement("OPTION");
			fieldGroupSelect.appendChild(eOption);
			eOption.value = groupInfo.GROUPID;
			eOption.innerText = groupInfo.GROUPNAME;
			if(eOption.value == currGroupId){
				fieldGroupSelect.selectedIndex = i+1;
			}
		}
		var eOption = document.createElement("OPTION");
		fieldGroupSelect.appendChild(eOption);
		eOption.value = 0;
		eOption.innerText = "未进行分组字段";
	});
}