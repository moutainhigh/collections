<!--

// 选择域定义列表
var	_selectFieldList = new Array();

// 选择项的格式：value、name、validFlag、sortName、description
// 域的类型：select、browsebox、checkbox、radio

// 定义select域的信息
function SelectInputField( frameName, fieldName, selectType, 
		optionName, showType, codeBox, nameBox, mixBox, filter, parameter, selectList )
{
	this.frameName = frameName;
	this.fieldName = fieldName;
	this.selectType = selectType;
	this.optionName = optionName;
	this.showType = showType;
	
	if( codeBox == null ){
		this.codeBox = '';
	}
	else{
		this.codeBox = codeBox;
	}
	
	if( nameBox == null ){
		this.nameBox = '';
	}
	else{
		this.nameBox = nameBox;
	}
	
	if( mixBox == null ){
		this.mixBox = '';
	}
	else{
		this.mixBox = mixBox;
	}
	
	this.filter = filter;
	this.parameter = parameter;
	this.selectList = excludeNullSelectList(selectList);
	
	// 被替换的事件:radio/checlbox，只读时需要替换这个事件，这里保存原先的事件
	this.onclick = null;
	this.onmousedown = null;
	this.restoreValue = null
	
	// 其他信息
	this.innerHTML = null;
	this.align = 'horizontal';
	
	// 是否已经初始化
	this.initFlag = false;
	
	// 如果需要从服务器动态下载数据，这里保存的是最近一次下载数据的参数
	this.loader_parameter = optionName;
	
	// 取保存参数的域名称和对象
	this.getAttrName = _select_getAttrName;
	this.getAttrObject = _select_getAttrObject;
	
	// 标题，域的名称
	this.getCaption = _select_getCaption;
	this.setCaption = _select_setCaption;
	
	// 域的校验规则
	this.getValidator = _select_getValidator;
	this.setValidator = _select_setValidator;
	
	// 是否能空
	this.getNotnull = _select_getNotnull;
	this.setNotnull = _select_setNotnull;
	
	// 校验标志
	this.getCheckFlag = _select_getCheckFlag;
	this.setCheckFlag = _select_setCheckFlag;
	
	// 只读标志
	this.getReadonly = _select_getReadonly;
	this.setReadonly = _select_setReadonly;
	
	// 有效标志
	this.getDisabled = _select_getDisabled;
	this.setDisabled = _select_setDisabled;
	
	// 可见标志
	this.getVisible = _select_getVisible;
	this.setVisible = _select_setVisible;
	
	// 缺省值
	this.getDefaultValue = _select_getDefaultValue;
	this.setDefaultValue = _select_setDefaultValue;
	
	// 提示信息
	this.getHint = _select_getHint;
	this.setHint = _select_setHint;
	
	// 初始化的值
	this.getInitValue = _select_getInitValue;
	
	// 取值和赋值函数
	this.getValue = _select_getOriginalValue;
	
	if( selectType == 'browsebox' ){
		this.getText = _select_getBrowseText;
		this.setValue = _select_setBrowseValue;
		this.setText = _select_setBrowseText;
		this.initField = _select_initBrowseFieldOptions;
	}
	else if( selectType == 'checkbox' ){
		this.getText = _select_getCheckText;
		this.setValue = _select_setCheckValue;
		this.setText = _select_setCheckText;
		this.initField = _select_initCheckFieldOptions;
	}
	else if( selectType == 'radio' ){
		this.getText = _select_getRadioText;
		this.setValue = _select_setRadioValue;
		this.setText = _select_setRadioText;
		this.initField = _select_initRadioFieldOptions;
	}
	else{
		this.getText = _select_getSelectedText;
		this.setValue = _select_setSelectedValue;
		this.setText = _select_setSelectedText;
		this.initField = _select_initSelectFieldOptions;
	}
	
	// 设置下拉列表
	this.setSelectList = _select_setSelectList;
	
	// 公共函数
	this.prepareHiddenField = _select_prepareHiddenField;	// 生成hidden域
	this.getFullName = _select_getFullName;					// 取域的完整名称，(增加前缀)
	this.getSelectShowBoxName = fz_getSelectShowBoxName;	// 取显示域的名称
	this.getCodeType = fz_getCodeType;						// 取值的类型：code=显示代码、name=显示名称、mix=混合代码和名称；缺省采集代码
	this.getShowType = fz_getShowType;						// 显示的类型：code=显示代码、name=显示名称、mix=混合代码和名称；缺省显示名称
	this.transSelectValue = _fz_transSelectValue;			// 转换值，在code、name、mix之间
	this.setCodeBoxValue = fz_setCodeBoxValue;				// 设置代码域的值
	this.setNameBoxValue = fz_setNameBoxValue;				// 设置文本域的值
	this.setMixBoxValue = fz_setMixBoxValue;				// 设置mix域的值
	this.setHiddenFieldValue = fz_setHiddenFieldValue;		// 设置隐藏域的值
	
	// 校验函数
	this.valid = _valid_checkSelectField;
	this.checkOneSelectField = _valid_checkOneSelectField;
}

function addSelectInputField( field )
{
	var	num = _selectFieldList.length;
	_selectFieldList[num] = field;
}

// 根据名称获取选择域信息
function getSelectInfoByFieldName( fieldName )
{
	for( var ii=0; ii<_selectFieldList.length; ii++ ){
		var name = _selectFieldList[ii].getFullName( );
		if( name == fieldName ){
			return _selectFieldList[ii];
		}
	}
	
	return null;
}


// 根据显示域的名称获取字段名称
function getSelectFieldByShowBox( showName )
{
	var	selectFld = null;
	for( var ii=0; ii<_selectFieldList.length; ii++ ){
		if( _selectFieldList[ii].getSelectShowBoxName() == showName ){
			selectFld = _selectFieldList[ii];
			break;
		}
	}
	
	return selectFld;
}


// 在名称前面增加前缀，生成域的名称
function _select_getFullName( name )
{
	if( name == '' ){
		return '';
	}
	
	if( name == null ){
		name = this.fieldName;
	}
	
	if( this.frameName != null && this.frameName != "" ){
		if( name.indexOf(this.frameName + ":") != 0 ){
			return this.frameName + ":" + name;
		}
	}
	else{
		return name;
	}
}

// 获取保存属性的域对象
function _select_getAttrName()
{
	// 取保存校验信息的对象:radio和check保存在table中
	if( this.selectType == 'radio' ){
		var fieldName = 'radio:' + this.getFullName(this.fieldName);
	}
	else if( this.selectType == 'checkbox' ){
		var fieldName = 'checkbox:' + this.getFullName(this.fieldName);
	}
	else if( this.selectType == 'browsebox' ){
		var fieldName = this.getSelectShowBoxName( );
	}
	else{
		var fieldName = this.getFullName(this.fieldName);
	}
	
	return fieldName;
}

function _select_getAttrObject(index)
{
	if( index == null || isNaN(parseInt(index)) || index < 0 ){
		index = 0;
	}
	
	// 保存属性的对象名称
	var fieldName = this.getAttrName();
	
	var fields = document.getElementsByName( fieldName );
	if( fields.length <= index ){
		return null;
	}
	
	return fields[index];
}


// 取域的标题
function _select_getCaption(index)
{
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return "";
	}
	
	var caption = attrObject.fieldCaption;
	
	// 判断是否存在
	if( caption == null ){
		var labelName = "label:" + this.getFullName();
		var labels = document.getElementsByName( labelName );
		if( index >= labels.length ){
			if( labels.length != 1 ){
				return '';
			}
			else{
				index = 0;
			}
		}
		// 设置标题的信息
		caption = labels[index].innerHTML;
		if( caption.lastIndexOf('：') == caption.length - 1 ){
			caption = caption.substring( 0, caption.length-1 );
		}
		attrObject.fieldCaption = caption;
	}
	
	return caption;
}

// 设置标题
function _select_setCaption(index, caption)
{
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return;
	}
	// 设置标题
	attrObject.fieldCaption = caption;
	
	// 显示标题
	var fieldName = this.getFullName(this.fieldName);
	_setFieldCaption( fieldName, index );
}

// 取域的校验规则
function _select_getValidator(index)
{
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return "";
	}
	
	return attrObject.validator;
}

// 设置校验规则
function _select_setValidator(index, rule)
{
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return;
	}
	
	attrObject.validator = rule;
}

// 判断域是否能够空
function _select_getNotnull(index)
{
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return false;
	}
	
	if( attrObject.notnull == 'true' ){
		return true;
	}
	else{
		return false;
	}
}

// 设置是否能空标志
function _select_setNotnull(index, flag)
{
	if( flag != false && flag != 'false' ){
		flag = 'true';
	}
	
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return;
	}
	
	attrObject.notnull = flag;
	
	// 显示标题
	var fieldName = this.getFullName(this.fieldName);
	_setFieldCaption( fieldName, index );
}

// 取域是否需要校验的标志
function _select_getCheckFlag(index)
{
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return false;
	}
	
	if( attrObject.checkFlag == 'true' ){
		return true;
	}
	else{
		return false;
	}
}

// 设置域是否需要校验的标志
function _select_setCheckFlag(index, flag)
{
	if( flag != false && flag != 'false' ){
		flag = 'true';
	}
	
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return;
	}
	
	attrObject.checkFlag = flag;
	
	// 浏览框处理
	if( this.selectType == 'browsebox' ){
		// 不允许输入时，点击打开选择框
		var	showBox = this.getSelectShowBoxName( );
		var objs = document.getElementsByName(showBox);
		if( objs.length <= index ){
			return;
		}
		
		var obj = objs[index];
		
		// 设置事件
		if( flag == 'true' ){
			// 鼠标属性，点击时打开选择框
			obj.style.cursor = 'hand';
			
			// onclick
			obj.onclick = function(){ document.getElementsByName('img:'+showBox)[index].fireEvent('onclick'); };
		}
		else{
			obj.style.cursor = '';
			obj.onclick = null;
		}
	}
}

// 取域是否只读标志
function _select_getReadonly(index)
{
	if( index == null ){
		index = 0;
	}
	
	// 对于select域，只读时，选择域被隐藏，只能判断显示内容的域
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	if( objs.length <= index ){
		return false;
	}
	
	// select特殊处理
	var readOnly;
	if( this.selectType == 'select' ){
		if( objs[index].readOnly == 'true' ){
			readOnly = true;
		}
		else{
			readOnly = false;
		}
	}
	else{
		readOnly = objs[index].readOnly;
	}
	
	return readOnly;
}

// 设置域是否只读标志
function _select_setReadonly(index, flag)
{
	if( index == null ){
		index = 0;
	}
	
	if( flag != false && flag != 'false' ){
		flag = true;
	}
	
	// 域变量名称
	var fieldName = this.getFullName(this.fieldName);
	
	// 对于select域，只读时，选择域被隐藏，只能判断显示内容的域
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	
	// select、radio、checkbox特殊处理
	if( this.selectType == 'select' ){
		if( objs.length <= index ){
			return;
		}
		
		// 设置只读标志
		objs[index].readOnly = flag;
		
		// 隐藏/显示域
		if( flag ){
			objs[index].style.display = 'none';
			
			var hobjs = document.getElementsByName( "hidden:" + fieldName );
			if( hobjs.length > index ){
				hobjs[index].style.display = '';
			}
			
			// 设置显示的内容
			hobjs[index].value = this.getText( index );
		}
		else{
			objs[index].style.display = '';
			
			var hobjs = document.getElementsByName( "hidden:" + fieldName );
			if( hobjs.length > index ){
				hobjs[index].style.display = 'none';
			}
		}
	}
	else if( this.selectType == 'radio' ){
		for( var ii=0; ii<objs.length; ii++ ){
			objs[ii].readOnly = flag;
			
			// 事件函数
			if( flag ){
				objs[ii].onmousedown = function(){ _fz_saveValue(fieldName); };
				objs[ii].onclick = function(){ _fz_restoreValue(fieldName); };
			}
			else{
				objs[ii].onmousedown = this.onmousedown;
				objs[ii].onclick = this.onclick;
			}
		}
	}
	else if( this.selectType == 'checkbox' ){
		for( var ii=0; ii<objs.length; ii++ ){
			objs[ii].readOnly = flag;
			
			// 事件函数
			if( flag ){
				objs[ii].onmousedown = function(){ _fz_saveValue(fieldName); };
				objs[ii].onclick = function(){ _fz_restoreValue(fieldName); };
			}
			else{
				objs[ii].onmousedown = this.onmousedown;
				objs[ii].onclick = this.onclick;
			}
		}
	}
	else if( this.selectType == 'browsebox' ){
		if( objs.length <= index ){
			return;
		}
		
		// 设置只读标志
		objs[index].readOnly = flag;
		
		// 设置图标是否显示
		var imgName = 'img:' + showName;
		var imgBox = document.getElementsByName(imgName)[index];
		
		// 是否隐藏
		if( objs[index].style.display == 'none' ){
			imgBox.style.display = 'none';
		}
		else if( flag == true ){
			imgBox.style.display = 'none';
		}
		else{
			imgBox.style.display = '';
		}
	}
	else{
		if( objs.length <= index ){
			return;
		}
		
		// 设置只读标志
		objs[index].readOnly = flag;
	}
	
	// 显示标题
	_setFieldCaption( fieldName, index );
}

// 取域是否禁止标志
function _select_getDisabled(index)
{
	if( index == null ){
		index = 0;
	}
	
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	if( objs.length <= index ){
		return false;
	}
	
	return objs[index].disabled;
}

// 设置是否禁止标志
function _select_setDisabled(index, flag)
{
	if( index == null ){
		index = 0;
	}
	
	if( flag != false && flag != 'false' ){
		flag = true;
	}
	
	// 取显示域
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	if( objs.length == 0 ){
		return;
	}
	
	// 设置域的状态，radio和checkbox，设置所有的域禁止状态
	if( this.selectType == 'radio' ){
		for( var ii=0; ii<objs.length; ii++ ){
			objs[ii].disabled = flag;
		}
	}
	else if( this.selectType == 'checkbox' ){
		for( var ii=0; ii<objs.length; ii++ ){
			objs[ii].disabled = flag;
		}
	}
	else if( this.selectType == 'browsebox' ){
		if( objs.length <= index ){
			return;
		}
		
		objs[index].disabled = flag;
		
		// 设置图标是否显示
		var imgName = 'img:' + showName;
		var imgBox = document.getElementsByName(imgName)[index];
		
		// 是否隐藏
		if( objs[index].style.display == 'none' ){
			imgBox.style.display = 'none';
		}
		else if( flag == true ){
			imgBox.style.display = 'none';
		}
		else{
			imgBox.style.display = '';
		}
	}
	else{
		if( objs.length <= index ){
			return;
		}
		
		objs[index].disabled = flag;
	}
	
	// 显示标题
	var fieldName = this.getFullName(this.fieldName);
	_setFieldCaption( fieldName, index );
}


// 取域是否可见标志
function _select_getVisible(index)
{
	if( index == null ){
		index = 0;
	}
	
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	if( objs.length <= index ){
		return false;
	}
	
	var display = objs[index].style.display;
	if( display == "none" ){
		if( this.selectType == 'select' ){
			var objs = document.getElementsByName( "hidden:" + this.getFullName() );
			if( objs.length <= index ){
				return false;
			}
			
			return fz_isFieldVisible( objs[index] );
		}
		else if(this.selectType == 'radio'){
			return true;
		}else{
			return false;
		}
	}
	else{
		return fz_isFieldVisible( objs[index] );
	}
}

// 设置域是否可见标志
function _select_setVisible(index, flag)
{
	// 样式
	var	vs;
	if( flag == true || flag == 'true' ){
		vs = '';
	}
	else{
		vs = 'none';
	}
	
	// 域的名称
	fieldName = this.getFullName(this.fieldName);
	
	// 设置标题的状态
	var	labelBox = document.getElementsByName( 'label:' + fieldName );
	if( labelBox != null ){
		if( labelBox.length > index ){
			labelBox[index].style.display = vs;
		}
	}
	
	// 取显示的域
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	
	// 隐藏域，radio和checkbox，设置所有的域隐藏
	if( this.selectType == 'radio' ){
		for( var ii=0; ii<objs.length; ii++ ){
			objs[ii].style.display = vs;
		}
		
		// 隐藏标题
		var	captionBox = document.getElementsByName( '_span_' + showName );
		if( captionBox != null ){
			for( ii=0; ii<captionBox.length; ii++ ){
				captionBox[ii].style.display = vs;
			}
		}
	}
	else if( this.selectType == 'checkbox' ){
		for( var ii=0; ii<objs.length; ii++ ){
			objs[ii].style.display = vs;
		}
		
		// 隐藏标题
		var	captionBox = document.getElementsByName( '_span_' + showName );
		if( captionBox != null ){
			for( ii=0; ii<captionBox.length; ii++ ){
				captionBox[ii].style.display = vs;
			}
		}
	}
	else if( this.selectType == 'select' ){
		if( objs.length > index ){
			if( flag == false ){
				// 隐藏域
				objs[index].style.display = vs;
				
				// 隐藏只读域
				var hobjs = document.getElementsByName( "hidden:" + fieldName );
				if( hobjs.length > index ){
					hobjs[index].style.display = vs;
				}
			}
			else{
				// 显示
				if( objs[index].readOnly == 'true' ){
					// 显示只读域
					var hobjs = document.getElementsByName( "hidden:" + fieldName );
					if( hobjs.length > index ){
						hobjs[index].style.display = vs;
					}
				}
				else{
					objs[index].style.display = vs;
				}
			}
		}
	}
	else{ // browsebox
		// 隐藏域
		if( objs.length > index ){
			objs[index].style.display = vs;
			
			// 隐藏图标
			var	imgBoxs = document.getElementsByName( 'img:' + showName );
			if( imgBoxs.length > index ){
				imgBoxs[index].style.display = vs;
			}
		}
	}
}


// 取域的缺省值
function _select_getDefaultValue(index)
{
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return null;
	}
	
	return attrObject._default;
}

// 设置缺省值
function _select_setDefaultValue(index, defaultValue)
{
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return;
	}
	
	// 设置标题
	attrObject._default = defaultValue;
}


// 取域的提示信息
function _select_getHint(index)
{
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return null;
	}
	
	return attrObject.hint;
}

// 设置提示信息
function _select_setHint(index, hint)
{
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return;
	}
	
	// 设置标题
	attrObject.hint = hint;
}


// 取初始化的值
function _select_getInitValue( index )
{
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return null;
	}
	
	return attrObject._value;
}


/* ************************ 取值和设置函数开始 ************************ */

// 取原始的值
function _select_getOriginalValue( index )
{
	// 取保存属性的对象
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return null;
	}
	
	return __getFieldInitValue( attrObject );
}

// 取内容
function _select_getBrowseValue( index )
{
	var	value = "";
	
	// 没有对照表
	if( this.selectList == null || this.selectList.length == 0 ){
		var objName = this.getFullName( this.fieldName );
		var	obj = document.getElementsByName( objName );
		if( index < obj.length ){
			value = obj[index].value;
		}
		
		return value;
	}
	
	// 需要转换
	var objName = this.getSelectShowBoxName();
	var	obj = document.getElementsByName( objName );
	if( index < obj.length ){
		value = obj[index].value;
	}
	
	// 转换数据
	var	codeType = this.getCodeType();
	var	showType = this.getShowType();
	return this.transSelectValue( value, index, showType, codeType );
}


// 取标题
function _select_getBrowseText( index )
{
	var objName = this.getSelectShowBoxName();
	
	// 取显示域
	var	value = "";
	var	obj = document.getElementsByName( objName );
	if( index < obj.length ){
		value = obj[index].value;
	}
	
	var	showType = this.getShowType();
	return this.transSelectValue( value, index, showType, "name" );
}

// 设置内容
function _select_setBrowseValue( index, value )
{
	if( value == null ){ value = index; index = 0; }
	
	// 转换成名称
	var	codeType = this.getCodeType();
	var	showType = this.getShowType();
	value = this.transSelectValue( value, index, codeType, showType );
	
	var objName = this.getSelectShowBoxName();
	var	obj = document.getElementsByName( objName );
	if( index >= obj.length ){
		return;
	}
	
	// 取显示的值
	obj[index].value = value;
	
	// 设置隐藏域
	this.setHiddenFieldValue( index, value );
}

// 设置标题
function _select_setBrowseText( index, text )
{
	if( text == null ){ text = index; index = 0; }
	
	// 设置内容
	var objName = this.getSelectShowBoxName();
	var	obj = document.getElementsByName( objName );
	if( index >= obj.length ){
		return;
	}
	
	var	showType = this.getShowType();
	text = this.transSelectValue( value, index, "name", showType );
	
	// 取显示的值
	obj[index].value = text;
	
	// 设置隐藏域
	this.setHiddenFieldValue( index, text );
}



// 取select域的内容
function _select_getSelectedValue( index )
{
	var objName = this.getSelectShowBoxName();
	var obj = document.getElementsByName( objName );
	if( index >= obj.length ){
		return "";
	}
	
	// 当前域
	obj = obj[index];
	
	// 判断是否选中
	for (var i=0; i<obj.options.length; i++) {
		if( obj.options[i].selected == true ){
			return obj.options[i].value;
		}
	}
	
	return "";
}

// 取select域的文本
function _select_getSelectedText( index )
{
	var objName = this.getSelectShowBoxName();
	var obj = document.getElementsByName( objName );
	if( index >= obj.length ){
		return "";
	}
	
	// 当前域
	obj = obj[index];
	
	// 判断是否选中
	for (var i=0; i<obj.options.length; i++) {
		if( obj.options[i].selected == true ){
			return obj.options[i].text;
		}
	}
	
	return "";
}

// 设置select域的文本
function _select_setSelectedText( index, text )
{
	if( text == null ){ text = index; index = 0; }
	
	var objName = this.getSelectShowBoxName();
	var obj = document.getElementsByName( objName );
	if( index >= obj.length ){
		return;
	}
	
	// 当前域
	obj = obj[index];
	obj.options[0].selected = true;
	
	// 判断是否选中
	for (var i=0; i<obj.options.length; i++) {
		if( obj.options[i].text == text ){
			obj.options[i].selected = true;
			break;
		}
	}
}

// 设置select域的内容
function _select_setSelectedValue( index, value )
{
	if( value == null ){ value = index; index = 0; }
	
	var objName = this.getSelectShowBoxName();
	var obj = document.getElementsByName( objName );
	if( index >= obj.length ) return;
	
	// 当前域
	obj = obj[index];
	if( obj.options.length == 0 ){
		obj._value = value;
		return;
	}
	
	// 判断是否选中
	obj.options[0].selected = true;
	for (var i=0; i<obj.options.length; i++) {
		if( obj.options[i].value == value ){
			obj.options[i].selected = true;
			break;
		}
	}
}

// 取radio域的内容
function _select_getRadioValue( index )
{
	var objName = this.getSelectShowBoxName();
	var obj = document.getElementsByName( objName );
	for( var i=0; i<obj.length; i++ ){
		if( obj[i].checked == true ){
			return obj[i].value;
		}
	}
	
	return "";
}

function _select_getRadioText( index )
{
	var	value = this.getValue( );
	
	// 转换数据
	var codeType = this.getCodeType();
	return this.transSelectValue( value, index, codeType, this.showType );
}


// 设置radio域的内容
function _select_setRadioValue( index, value )
{
	if( value == null ){ value = index; index = 0; }
	
	var objName = this.getSelectShowBoxName();
	var obj = document.getElementsByName( objName );
	
	for( var i=0; i<obj.length; i++ ){
		if( obj[i].value == value ){
			obj[i].checked = true;
			break;
		}
	}
}

function _select_setRadioText( index, text )
{
	if( text == null ){ text = index; index = 0; }
	
	// 内容转换城名称
	var codeType = this.getCodeType();
	text = this.transSelectValue( text, index, this.showType, codeType );
	
	this.setValue( index, text );
}


// 设置和获取check的内容
function _select_getCheckValue( )
{
	var objName = this.getSelectShowBoxName();
	obj = document.getElementsByName( objName );
	
	var	value = '';
	for( var i=0; i<obj.length; i++ ){
		if( obj[i].checked == true ){
			if( value == '' ){
				value = obj[i].value;
			}
			else{
				value = value + ',' + obj[i].value;
			}
		}
	}
	
	return value
}

function _select_getCheckText( index )
{
	var	value = this.getValue( );
	
	var codeType = this.getCodeType();
	return this.transSelectValue( value, index, codeType, this.showType );
}


function _select_setCheckValue( index, value )
{
	if( value == null ){ value = index; index = 0; }
	
	var objName = this.getSelectShowBoxName();
	obj = document.getElementsByName( objName );
	
	// 清空
	for( var i=0; i<obj.length; i++ ){
		obj[i].checked = false;
	}
	
	if( value == null || value == '' ){
		return;
	}
	
	// 值列表
	var	list = value.split( ',' );
	for( var i=0; i<list.length; i++ ){
		list[i] = trimSpace( list[i], 0 );
	}
	
	for( var i=0; i<obj.length; i++ ){
		for( var j=0; j<list.length; j++ ){
			if( list[j] == obj[i].value ){
				obj[i].checked = true;
				break;
			}
		}
	}
}

function _select_setCheckText( index, text )
{
	if( text == null ){ text = index; index = 0; }
	
	// 内容转换城名称
	var	codeType = this.getCodeType();
	text = this.transSelectValue( text, index, this.showType, codeType );
	
	this.setValue( index, text );
}


/* ************************ 取值和设置函数结束 ************************ */


function excludeNullSelectList( options )
{
	var b;
	var jj = 0;
	var list = new Array();
	for( var ii=0; ii<options.length; ii++ ){
		var p = options[ii];
		if( p[2] == true || p[2] == 'true' || p[2] == null ){
			b = true;
		}else{
		    b = false;
		}
		if((p[0] == null || p[0] == 'null') && (p[1] == null || p[1] == 'null')){		    
		    b = false;
		}
		// 蔡千修改，过滤重复数据,修改日期2009-1-5
		for(var kk=0;kk<list.length;kk++){
		   var tmp = list[kk];
		   if(p[0]==tmp[0]){
		       b = false;
		       break;
		   }
		}
		if(b){
		    list[jj++] = [p[0], p[1], b, p[3], p[4]];
		}		
	}
	return list;	
}

// 设置下拉框的数据
function _select_setSelectList( options )
{
	var b;
	var jj = 0;
	var list = new Array();
	for( var ii=0; ii<options.length; ii++ ){
		var p = options[ii];
		if( p[2] == true || p[2] == 'true' || p[2] == null ){
			b = true;
		}else{
		    b = false;
		}
		if((p[0] == null || p[0] == 'null') && (p[1] == null || p[1] == 'null')){		    
		    b = false;
		}
		if(b){
		    list[jj++] = [p[0], p[1], b, p[3], p[4]];
		}		
	}
	this.selectList = list;	
}

// 取显示域的名称
function fz_getSelectShowBoxName( )
{
	var	bname;
	if( this.selectType == 'checkbox' ){
		bname = '_tmp_' + this.fieldName;
	}
	else if( this.selectType == 'browsebox' ){
		if( this.showType == 'code' ){
			bname = this.codeBox;
		}
		else if( this.showType == 'name' ){
			bname = this.nameBox;
		}
		else{
			bname = this.mixBox;
		}
		
		if( bname == '' ){
			bname = '_tmp_' + this.fieldName;
		}
	}
	else{
		bname = this.fieldName;
	}
	
	return this.getFullName(bname);
}

// 缺省采集数据类型
function fz_getCodeType()
{
	var	codeType = 'code';
	if( this.fieldName == this.nameBox ){
		codeType = 'name';
	}
	else if( this.fieldName == this.mixBox ){
		codeType = 'mix';
	}
	
	return codeType;
}

// 取显示的类型
// 不是显示文本的域，而是接受用户输入的域，比如：select显示名称、保存代码；此时接受用户输入的域是code域
function fz_getShowType()
{
	var	showType = this.showType;
	if( this.selectType == 'browsebox' ){
		if( showType == 'mix' && this.selectList != null ){
			showType = 'code';
			for( var ii=0; ii<this.selectList.length; ii++ ){
				if( this.selectList[ii][0] != this.selectList[ii][1] ){
					showType = 'mix';
					break;
				}
			}
		}
	}
	else {
		if( this.fieldName == this.codeBox ){
			showType = 'code';
		}
		else if( this.fieldName == this.nameBox ){
			showType = 'name';
		}
		else{
			showType = 'mix';
		}
	}
	
	return showType;
}



// 标题和内容之间转换
function _fz_transSelectValue( value, index, fromType, toType )
{
	// 没有选项
	if( this.selectList == null || this.selectList.length == 0 ){
		return value;
	}
	
	// 空值
	if( value == null || value == '' ){
		return '';
	}
	
	// 判断是否和选择域一致
	if( fromType == toType ){
		return value;
	}
	
	// 转换数据
	var	selList = value.split( ',' );
	var	selectList = this.selectList;
	
	// 检查数据是否在列表中
	var j = 0;
	var	codeText = '';
	var	optionValue = '';
	for( var i=0; i<selList.length; i++ ){
		selList[i] = trimSpace( selList[i], 0 );
		for( j=0; j<selectList.length; j++ ){
			if( fromType == 'mix' ){
				optionValue = selectList[j][0] + '--' + selectList[j][1];
			}
			else if( fromType == 'name' ){
				optionValue = selectList[j][1];
			}
			else{
				optionValue = selectList[j][0];
			}
			
			if( optionValue == selList[i] ){
				break;
			}
		}
		
		// 判断是否是选择列表中的数据
		if( j >= selectList.length ){
			if( this.getCheckFlag(index) && this.parameter != '' ){
				alert( '设置输入域 【' + this.getCaption(index) + '】 时输入了一个无效数据 【' + selList[i] + '】' );
			}
			
			codeText = value;
		}
		else{
			// 设置内容
			if( toType == 'code' ){
				if( i == 0 ){
					codeText = selectList[j][0];
				}
				else{
					codeText = codeText + ',' + selectList[j][0];
				}
			}
			else if( toType == 'name' ){
				if( i == 0 ){
					codeText = selectList[j][1];
				}
				else{
					codeText = codeText + ',' + selectList[j][1];
				}
			}
			else{
				if( i == 0 ){
					codeText = selectList[j][0] + '--' + selectList[j][1];
				}
				else{
					codeText = codeText + ',' + selectList[j][0] + '--' + selectList[j][1];
				}
			}
		}
	}
	
	return codeText;
}



/* *********************** 设置隐藏域的值 *********************** */

// 设置隐藏域的内容
function fz_setHiddenFieldValue( index, value )
{
	if( value == null ){
		value = '';
	}
	
	// 不检查
	selectList = this.selectList;
	if( selectList == null || selectList.length == 0 || value == '' ){
		this.setCodeBoxValue( index, value );
		this.setNameBoxValue( index, value );
		this.setMixBoxValue( index, value );
		return true;
	}
	
	// 生成的内容
	var	nameText = '';
	var	codeText = '';
	var	mixText = '';
	
	value = '' + value;
	var	selList = value.split( ',' );
	
	// valueType 保存原始数据的类型
	var	showType = this.getShowType();
	
	// 检查数据是否在列表中
	var	i, j;
	var	optionValue;
	if( value != '' ){
		for( i=0; i<selList.length; i++ ){
			selList[i] = trimSpace( selList[i], 0 );
			
			for( j=0; j<selectList.length; j++ ){
				if( showType == 'mix' ){
					optionValue = selectList[j][0] + '--' + selectList[j][1];
				}
				else if( showType == 'name' ){
					optionValue = selectList[j][1];
				}
				else{
					optionValue = selectList[j][0];
				}
				
				if( optionValue == selList[i] ){
					break;
				}
			}
			
			// 判断是否是选择列表中的数据
			if( j >= selectList.length ){
				if( this.getCheckFlag(index) ){
					alert( '在输入域 【' + this.getCaption(index) + '】 中输入了一个无效数据 【' + selList[i] + '】' );
					return false;
				}
				
				// 没有找到转换信息，并且不检查有效性
				nameText = value;
				codeText = value;
				mixText = value;
			}
			else{
				// 设置内容
				if( i == 0 ){
					nameText = selectList[j][1];
					codeText = selectList[j][0];
					mixText = selectList[j][0] + '--' + selectList[j][1];
				}
				else{
					nameText = nameText + ',' + selectList[j][1];
					codeText = codeText + ',' + selectList[j][0];
					mixText = mixText + ',' + selectList[j][0] + '--' + selectList[j][1];
				}
			}
		}
	}
	
	// 设置隐藏域的内容
	this.setCodeBoxValue( index, codeText );
	this.setNameBoxValue( index, nameText );
	this.setMixBoxValue( index, mixText );
	
	return true;
}

// 设置代码域的内容
function fz_setCodeBoxValue( index, value )
{
	// 显示域名称
	var	showBox = this.getSelectShowBoxName( );
	
	// 设置内容
	var codeBox = this.codeBox;
	if( codeBox != null && codeBox != '' ){
		codeBox = this.getFullName( codeBox );
		if( codeBox != showBox ){
			var	obj = document.getElementsByName(codeBox);
			obj[index].value = value;
		}
	}
}

// 设置名称域的内容
function fz_setNameBoxValue( index, value )
{
	// 显示域名称
	var	showBox = this.getSelectShowBoxName( );
	
	// 设置内容
	var nameBox = this.nameBox;
	if( nameBox != null && nameBox != '' ){
		nameBox = this.getFullName( nameBox );
		if( nameBox != showBox ){
			var	obj = document.getElementsByName(nameBox);
			obj[index].value = value;
		}
	}
}

// 设置混合域的内容
function fz_setMixBoxValue( index, value )
{
	// 显示域名称
	var	showBox = this.getSelectShowBoxName( );
	
	// 设置内容
	var mixBox = this.mixBox;
	if( mixBox != null && mixBox != '' ){
		mixBox = this.getFullName( mixBox );
		if( mixBox != showBox ){
			var	obj = document.getElementsByName(mixBox);
			obj[index].value = value;
		}
	}
}



/* *********************** 校验有效性 *************************** */


/* 检查选择域的数据有效性
** 在自动生成的代码中调用，验证数据的有效性
*/
function _valid_checkSelectField( )
{
	var	index;
	var	flag = true;
	
	// 变量名称
	var	showBox = this.getSelectShowBoxName( );
	var	obj = document.getElementsByName(showBox)
	if( obj == null ){
		return false;
	}
	
	// 获取数据并检查有效性
	if( this.selectType == "select" ){
		for( index=0; index<obj.length; index++ ){
			flag = this.checkOneSelectField( obj, index );
			if( flag == false ){
				break;
			}
		}
	}
	else if( this.selectType == "browsebox" ){
		for( index=0; index<obj.length; index++ ){
			flag = this.checkOneSelectField( obj, index );
			if( flag == false ){
				break;
			}
		}
	}
	else if( this.selectType == "checkbox" ){
		flag = this.checkOneSelectField( obj, 0 );
	}
	else{
		flag = this.checkOneSelectField( obj, 0 );
	}
	
	return flag;
}

// 检查单个域的有效性
function _valid_checkOneSelectField( obj, index )
{
	var flag = true;
	
	// 自定义规则
	flag = _checkFieldValidatorRule( obj, index, this.getValidator(index), this.getCaption(index) )
	if( flag == false ){
		fz_setSelectFieldFocus( this, index );
		return false;
	}
	
	// 设置隐藏的数据
	var	value;
	if( this.selectType == "select" ){
		value = fz_checkSelectFieldValid( this, index );
		if( value == null ){
			fz_setSelectFieldFocus( this, index );
			return false;
		}
	}
	else if( this.selectType == "browsebox" ){
		value = fz_checkBrowseFiedlValid( this, index );
		if( value == null ){
			fz_setSelectFieldFocus( this, index );
			return false;
		}
	}
	else if( this.selectType == "checkbox" ){
		value = fz_checkCheckFieldValid( this, index );
		if( value == null ){
			fz_setSelectFieldFocus( this, index );
			return false;
		}
	}
	else{
		value = fz_checkRadioFieldValid( this, index );
		if( value == null ){
			fz_setSelectFieldFocus( this, index );
			return false;
		}
	}
	
	// 设置内容
	flag = this.setHiddenFieldValue( index, value );
	if( flag == false ){
		fz_setSelectFieldFocus( this, index );
		return false;
	}
	
	return true;
}



// 设置焦点
function fz_setSelectFieldFocus( field, index )
{
	var	showBox = field.getSelectShowBoxName( );
	
	// 获取对象
	obj = document.getElementsByName(showBox)
	if( obj != null && obj.length > index ){
		obj[index].focus();
	}
}


// 检查select的有效性，返回内容，如果是空，说明检查没有通过
function fz_checkSelectFieldValid( field, index )
{
	var	val = field.getValue( index );
	
	// 判断是否空
	if( field.getNotnull(index) == true && val == '' ){
		alert( '输入域 【' + field.getCaption(index) +'】 必须选择一个数据' );
		val = null;
	}
	
	// 取数据
	return val;
}

function fz_checkBrowseFiedlValid( field, index )
{
	var	showBox = field.getSelectShowBoxName( );
	var objs = document.getElementsByName(showBox);
	
	var val = '';
	if( objs.length > index ){
		val = objs[index].value;
	}
	
	// 判断是否空
	if( field.getNotnull(index) == true && val == '' ){
		alert( '输入域 【' + field.getCaption(index) +'】 必须选择一个数据' );
		val = null;
	}
	
	return val;
}

function fz_checkRadioFieldValid( field, index )
{
	var	val = field.getValue( );
	
	// 判断是否空
	if( val == null ){
		if( field.getNotnull(index) == true ){
			alert( '输入域 【' + field.getCaption(index) +'】 必须选择一个数据' );
		}
		else{
			val = '';
		}
	}
	
	return val;
}

function fz_checkCheckFieldValid( field, index )
{
	var	val = field.getValue( );
	
	// 判断是否空
	if( field.getNotnull(index) == true && val == '' ){
		alert( '输入域 【' + field.getCaption(index) + '】 必须选择一个数据' );
		val = null;
	}
	
	return val;
}



/* ********************** 初始化 ************************ */
function _select_initField( fieldName, index )
{
	// 查找定义
	var field = getSelectInfoByFieldName( fieldName );
	if( field == null ){
		return;
	}
	
	if( index == null || index == '' ){
		index = 0;
	}
	
	var obj = field.getAttrObject( index );
	if( obj == null || obj._inited ){
		return;
	}
	
	// 设置已经初始化标志
	obj._inited = true;
	
	field.initField( fieldName, index );
}


// 初始化browse的样式，主要是对于需要校验有效性的域，不允许手工输入
function _select_initBrowseFieldOptions( fieldName, index )
{
	// 取域的对象
	var	showBox = this.getSelectShowBoxName( );
	var objs = document.getElementsByName(showBox);
	if( objs.length <= index ){
		return;
	}
	
	var obj = objs[index];
	
	// 设置data域的内容
	var dataType = this.getCodeType();
	if( this.showType != dataType ){
		var hobj = document.createElement( "<input type='hidden' name='" + fieldName + "'>" );
		hobj.id = fieldName;
		_browse.appendElement( obj.parentNode, hobj );
		hobj = null;
	}
	
	// 生成hidden域
	this.prepareHiddenField( obj.parentNode, index );
	
	// 样式
	obj.style.width = '100%';
	obj.id = showBox;
	
	// 设置TABLE的样式
	var tableObj = findAncestorWithName( obj, "TABLE" );
	if( tableObj != null ){
		tableObj.border = 0;
		tableObj.cellSpacing = 0;
		tableObj.cellPadding = 0;
		tableObj = null;
	}
	
	// td的宽度
	obj.parentNode.width = '99%';
	
	// 设置图形按钮
	var td = document.createElement( "TD" );
	_browse.appendElement( obj.parentNode.parentNode, td );
	td.width = '1%';
	
	var img = document.createElement( '<img name="img:' + showBox + '">' );
	_browse.appendElement( td, img );
	
	// id、样式
	img.id = 'img:' + showBox;
	img.border = 0;
	img.vspace = 0;
	img.hspace = 0;
	img.style.cursor = 'hand';
	
	// 是否隐藏
	if( obj.style.display == 'none' ){
		img.style.display = 'none';
	}
	
	// 提示信息
	if( obj.fieldCaption != null && obj.fieldCaption != '' ){
		img.alt = "请选择:" + obj.fieldCaption;
	}
	else{
		img.alt = "请选择内容";
	}
	
	// 事件
	img.onclick = function(){openBrowse(fieldName, index)};
	
	// 设置图标
	if( _browseImage.complete ){
		img.src = _browseImage.src;
		img = null;
	}
	else{
		setTimeout( function(){img.src = _browseImage.src; img = null;}, 1 );
	}
	
	// 不允许输入时，点击打开选择框
	var func = function(){document.getElementsByName('img:' + showBox)[index].fireEvent('onclick')};
	if( this.getCheckFlag() ){
		// 鼠标属性，点击时打开选择框
		obj.style.cursor = 'hand';
		
		// onclick
		obj.onclick = func;
	}
	
	// 双击事件
	obj.ondblclick = func;
	
	// onkeydown:没有选择信息，并且需要校验时，不允许输入
	var field = this;
	obj.onkeydown = function(){ _browse_onkeydown(field) };
	
	// 初始化的值
	var value = __getFieldInitValue( obj );
	
	// 准备下载browse的参数
	if( document.readyState == 'complete' ){
		if( value != null && value != '' ){
			setFormFieldValue( fieldName, index, value );
		}
		
		// 修改取值函数
		this.getValue = _select_getBrowseValue;
	}
	else{
		_addBrowseInitInfo( fieldName, index, value );
	}
	
	//alert( obj.parentNode.parentNode.innerHTML );
	td = null;
	obj = null;
}

// 初始化browse的选择框数据，针对二级下拉框有效
function fz_prepareBrowseFieldOptions( field, index )
{
	var fieldName = field.getFullName();
	
	// 取值
	var fieldValue = getFormFieldValue( fieldName, index );
	if( fieldValue == null || fieldValue == '' ){
		return;
	}
	
	// 取浏览框的下载参数
	if( field.parameter != null && field.parameter != '' ){
		// 判断是否需要重新下载数据:当下载条件变化 或者 下载参数变化 都需要重新下载
		var optName = field.optionName;
		
		// 取参数
		var parameter = eval( field.parameter );
		
		// 代码集一样，需要判断下载代码使用的参数不能空
		if( optName == field.optionName ){
			if( parameter == null ){
				return;
			}
			
			// 判断参数是否为空
			var emptyParameter = true;
			var pv = parameter.split( '&' );
			for( var ii=0; ii<pv.length; ii++ ){
				var v = pv[ii].trim();
				if( v.charAt(v.length-1) != '=' ){
					emptyParameter = false;
					break;
				}
			}
			
			if( emptyParameter == true ){
				return;
			}
		}
		
		field.loader_parameter = parameter;
		
		// 格式化内容
		if( parameter != null ){
			parameter = parameter.replace( /[=]/g, '%3D' );
			parameter = parameter.replace( /[&]/g, '%26' );
	 		parameter = parameter.replace( /[#]/g, '%23' );
		}
		else{
			parameter = '';
		}
		
		// 生成提交数据
		return "option-name=" + field.optionName + "&parameter=" + parameter + "&fieldName=" + fieldName + "&index=" + index;
	}
}



// 初始化radio 和 check
function _select_initRadioFieldOptions( fieldName, index )
{
	// 修改取值函数
	this.getValue = _select_getRadioValue;
	
	// 取域的对象
	var objs = document.getElementsByName( 'radio:' + fieldName );
	if( objs.length <= index ){
		return;
	}
	
	// 设置样式
	var tableObj = objs[index];
	tableObj.border = 0;
	tableObj.cellSpacing = 0;
	tableObj.cellPadding = 0;
	
	// 初始化的值
	var value = __getFieldInitValue( tableObj );
	
	// 生成hidden域
	this.prepareHiddenField( tableObj.parentNode, index );
	
	// 初始化选择项
	fz_initRadioAndCheckFieldOptions( objs[index], fieldName, index, value );
	
	// 保存事件
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	if( objs.length > 0 ){
		this.onclick = objs[0].onclick;
		this.onmousedown = objs[0].onmousedown;
	}
	
	// 设置只读事件
	for( var ii=0; ii<objs.length; ii++ ){
		if( objs[ii].readOnly ){
			objs[ii].onmousedown = function(){ _fz_saveValue(fieldName); };
			objs[ii].onclick = function(){ _fz_restoreValue(fieldName); };
		}
		
		// 设置样式
		objs[ii].style.cursor = 'hand';
	}
	
	// 设置标题的样式
	var caps = document.getElementsByName( '_span_' + showName );
	for( var ii=0; ii<caps.length; ii++ ){
		caps[ii].style.cursor = 'hand';
		caps[ii].style.width = '70%';
	}
}


function _select_initCheckFieldOptions( fieldName, index )
{
	// 修改取值函数
	this.getValue = _select_getCheckValue;
	
	// 取域的对象
	var objs = document.getElementsByName( 'checkbox:' + fieldName );
	if( objs.length <= index ){
		return;
	}
	
	// 初始化的值
	var value = __getFieldInitValue( objs[index] );
	
	// 设置data域的内容
	var hobj = document.createElement( "<input type='hidden' name='" + fieldName + "'>" );
	hobj.id = fieldName;
	
	hobj.value = value;
	_browse.appendElement( objs[index].parentNode, hobj );
	
	// 生成hidden域
	this.prepareHiddenField( objs[index].parentNode, index );
	
	// 设置选择项
	fz_initRadioAndCheckFieldOptions( objs[index], fieldName, index, value );
	
	// 保存事件
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	if( objs.length > 0 ){
		this.onclick = objs[0].onclick;
		this.onmousedown = objs[0].onmousedown;
	}
	
	// 设置只读事件
	for( var ii=0; ii<objs.length; ii++ ){
		if( objs[ii].readOnly ){
			objs[ii].onmousedown = function(){ _fz_saveValue(fieldName); };
			objs[ii].onclick = function(){ _fz_restoreValue(fieldName); };
		}
		
		// 设置样式
		objs[ii].style.cursor = 'hand';
	}
	
	// 设置标题的样式
	var caps = document.getElementsByName( '_span_' + showName );
	for( var ii=0; ii<caps.length; ii++ ){
		caps[ii].style.cursor = 'hand';
		caps[ii].style.width = '70%';
	}
}

// 保存和恢复到原来的值
function _fz_saveValue( fieldName )
{
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField == null ){
		return;
	}
	
	selField.restoreValue = getFormFieldValue( fieldName );
}

function _fz_restoreValue( fieldName )
{
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField == null ){
		return;
	}
	
	setFormFieldValue( fieldName, 0, selField.restoreValue );
}

// 初始化选择域
function fz_initRadioAndCheckFieldOptions( table, fieldName, index, value )
{
	var	row = table.rows( 0 );
	if( row == null ){
		return;
	}
	
	var	cell = row.cells( 0 );
	if( cell == null ){
		return;
	}
	
	// 取域的定义信息
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField == null ){
		return;
	}
	
	// 生成语句
	selField.innerHTML = cell.innerHTML;
	if( cell.width == '100%' ){
		selField.align = 'vertical';
	}
	
	// 删除
	row.deleteCell( 0 );
	if( selField.align == 'vertical' ){
		table.deleteRow( 0 );
	}
	
	// 选中的字段列表，可能是多选
	var j;
	var selectedList = new Array();
	if( value != null && value != '' ){
		value = value.replaceAll( ',', ';' );
		selectedList = value.split( ';' );
	}
	
	// 生成列表
	var radioNumber = 0;
	var selectedValue = null;
	var selectedFlag;
	var showValue;
	var dataValue;
	var dt = selField.getCodeType();
	var st = selField.showType;
	var selList = selField.selectList;
	var selOpt;
	for( var i=0; i<selList.length; i++ ){
		selOpt = selList[i];
		if( st == 'mix' ){
			showValue = selOpt[0] + '--' + selOpt[1];
		}
		else if( st == 'name' ){
			showValue = selOpt[1];
		}
		else{
			showValue = selOpt[0];
		}
		
		if( dt == 'mix' ){
			dataValue = selOpt[0] + '--' + selOpt[1];
		}
		else if( dt == 'name' ){
			dataValue = selOpt[1];
		}
		else{
			dataValue = selOpt[0];
		}
		
		// 是否被选中
		selectedFlag = false;
		for( j=0; j<selectedList.length; j++ ){
			if( selectedList[j] == dataValue ){
				selectedFlag = true;
				break;
			}
		}
		
		if( selOpt[2] == false && selectedFlag == false ){
			continue;
		}
		
		// 增加
		radioHtml = fz_getCreateHtmlString( selField.innerHTML, radioNumber, dataValue, showValue, selectedFlag );
		
		// 增加内容
		radioNumber = radioNumber + 1;
		if( selField.align == 'vertical' ){
			row = table.insertRow( radioNumber-1 );
			cell = row.insertCell( 0 );
			cell.width = '100%';
		}
		else{
			cell = row.insertCell( radioNumber-1 );
		}
		
		cell.innerHTML = radioHtml;
	}
	
	// 设置宽度
	if( selField.align != 'vertical' ){
		var wd = 100/radioNumber + '%';
		for( i=0; i<radioNumber; i++ ){
			row.cells(i).width = wd;
		}
	}
}

// 生成RADIO或CHECK的代码
function fz_getCreateHtmlString( modelHTML, index, dataValue, showValue, selectedFlag )
{
	if( selectedFlag ){
		var iptr = modelHTML.indexOf( "id=" );
		if( iptr > 0 ){
			modelHTML = modelHTML.substring( 0, iptr ) + 'checked ' + modelHTML.substring(iptr);
		}
	}
	
	modelHTML = replaceAll( modelHTML, '[index*]', index );
	modelHTML = replaceAll( modelHTML, '[value*]', dataValue );
	modelHTML = replaceAll( modelHTML, '[caption*]', showValue );
	return modelHTML;
}


// 初始化select的选择项列表
function _select_initSelectFieldOptions( fieldName, index )
{
	// 修改取值函数
	this.getValue = _select_getSelectedValue;
	
	// 取域的对象
	var objs = document.getElementsByName( fieldName );
	if( objs == null || objs.length <= index ){
		return;
	}
	
	var obj = objs[index];
	obj.id = fieldName;
	
	// 初始化的值
	var value = __getFieldInitValue( obj );
	
	// 生成hidden域
	this.prepareHiddenField( obj.parentNode, index );
	
	// 增加空行
	var opt;
	if( this.getNotnull(index) == true ){
		opt = new Option( "请选择...", "" );
	}
	else{
		opt = new Option( "全部", "" );
	}
	
	obj.add( opt );
	
	// 选中的字段列表，可能是多选
	var j;
	var selectedList = new Array();
	if( value != null && value != '' ){
		value = value.replace( ',', ';' );
		selectedList = value.split( ';' );
	}
	
	// 增加选择列表
	var selectedValue = null;
	var selectedFlag;
	var showValue;
	var dataValue;
	var dt = this.getCodeType();
	var st = this.showType;
	var selList = this.selectList;
	for( var i=0; i<selList.length; i++ ){
		var selOpt = selList[i];
		if( st == 'mix' ){
			showValue = selOpt[0] + '--' + selOpt[1];
		}
		else if( st == 'name' ){
			showValue = selOpt[1];
		}
		else{
			showValue = selOpt[0];
		}
		
		if( dt == 'mix' ){
			dataValue = selOpt[0] + '--' + selOpt[1];
		}
		else if( dt == 'name' ){
			dataValue = selOpt[1];
		}
		else{
			dataValue = selOpt[0];
		}
		
		// 是否被选中
		selectedFlag = false;
		for( j=0; j<selectedList.length; j++ ){
			if( selectedList[j] == dataValue ){
				selectedFlag = true;
				break;
			}
		}
		
		if( selOpt[2] == false && selectedFlag == false ){
			continue;
		}
		
		// 增加
		opt = new Option( showValue, dataValue );
		obj.add( opt );
		
		// 是否选中的记录
		if( selectedFlag == true ){
			obj.options[obj.options.length-1].selected = true;
			if( selectedValue == null || selectedValue == '' ){
				selectedValue = showValue;
			}
			else{
				selectedValue = selectedValue + ';' + showValue;
			}
		}
	}
	
	// 如果是只读的，设置文本域的数据
	var hobj = document.createElement( "<input type='text' name='hidden:" + fieldName + "'>" );
	hobj.readOnly = true;
	hobj.style.cssText = obj.style.cssText;
	_browse.appendElement( obj.parentNode, hobj );
	
	if( obj.accessKey != '' ){
		hobj.accessKey = obj.accessKey;
	}
	
	if( obj.tabIndex > 0 ){
		hobj.tabIndex = obj.tabIndex;
	}
	
	if( obj.size > 0 ){
		hobj.size = obj.size;
	}
	
	if( selectedValue != null && selectedValue != '' ){
		hobj.value = selectedValue;
	}
	
	// 设置域是否显示
	if( objs[index].readOnly == 'true' ){
		objs[index].style.display = 'none';
	}
	else{
		hobj.style.display = 'none';
	}
}


// 生成hidden域
function _select_prepareHiddenField( node, index )
{
	var show = this.showType;
	var data = this.getCodeType();
	
	if( this.selectType != 'browsebox' ){
		show = '';
	}
	
	// 生成其他的隐藏域
	if( this.codeBox != '' ){
		if( data != "code" && show != "code" ){
			var codeBox = this.getFullName( this.codeBox );
			var hobj = document.createElement( "<input type='hidden' name='" + codeBox + "'>" );
			hobj.id = codeBox;
			_browse.appendElement( node, hobj );
			hobj = null;
		}
	}
	
	if( this.nameBox != '' ){
		if( data != "name" && show != "name" ){
			var nameBox = this.getFullName( this.nameBox );
			var hobj = document.createElement( "<input type='hidden' name='" + nameBox + "'>" );
			hobj.id = nameBox;
			_browse.appendElement( node, hobj );
			hobj = null;
		}
	}

	if( this.mixBox != '' ){
		if( data != "mix" && show != "mix" ){
			var mixBox = this.getFullName( this.mixBox );
			var hobj = document.createElement( "<input type='hidden' name='" + mixBox + "'>" );
			hobj.id = mixBox;
			_browse.appendElement( node, hobj );
			hobj = null;
		}
	}
}


/* ********************* 事件处理 ***************************** */

// 过虑选择的列表，当鼠标移动到select域时，根据条件过虑数据
function fz_filterOptionList( obj )
{
	if( obj == null || obj.name == null || obj.name == '' ){
		return;
	}
	
	// 取名称
	var	objName = obj.name;
	
	// 取定义
	var	field = null;
	for( var ii=0; ii<_selectFieldList.length; ii++ ){
		if( _selectFieldList[ii].getSelectShowBoxName() == objName ){
			field = _selectFieldList[ii];
			break;
		}
	}
	
	if( field == null || field.filter == '' || field.selectType != 'select' ){
		return;
	}
	
	// 取序号
	var	index = getFormFieldIndex( obj );
	
	// 删除option
	var	selectedValue = null;
	for( var x=obj.options.length-1; x>0; x-- ){
		if( obj.options[x].selected ){
			selectedValue = obj.options[x].value;
		}
		
		obj.remove(x);
	}
	
	// 重新生成option
	var	data;
	var	flag;
	var	optValue;
	var	optText;
	var	newOptionItem;
	for( var x=0; x<field.selectList.length; x++ ){
		data = field.selectList[x];
		if( field.codeBox == field.fieldName ){
			optValue = data[0];
		}
		else if( field.nameBox == field.fieldName ){
			optValue = data[1];
		}
		else{
			optValue = data[0] + '--' + data[1];
		}
		
		if( field.showType == 'code' ){
			optText = data[0];
		}
		else if( field.showType == 'mix' ){
			optText = data[0] + '--' + data[1];
		}
		else{
			optText = data[1];
		}
		
		if( optValue == selectedValue ){
			newOptionItem = new Option( optText, optValue );
			obj.add( newOptionItem );
			obj.options[obj.options.length-1].selected = true;
		}
		else{
			flag = eval( field.filter + '( data, index )' );
			if( flag != false ){
				newOptionItem = new Option( optText, optValue );
				obj.add( newOptionItem );
			}
		}
	}
}



// browse:没有选择信息，并且需要校验时，不允许输入
function _browse_onkeydown( field )
{
	// 判断是否校验
	if( field.getCheckFlag() ){
		// 判断是否有选择项
//		var list = field.selectList;
//		if( list != null && isArray(list) && list.length > 1 ){
			window.event.returnValue = false;
//		}
	}
}


//checkbox按钮使用
function OnClick_Checked( checkName )
{
	var	iptr = checkName.indexOf( ":" );
	if( iptr < 0 ){
		var tmpName = '_tmp_' + checkName;
	}
	else{
		var tmpName = checkName.substring(0,iptr+1) + '_tmp_' + checkName.substring(iptr+1);
	}
	
	var chkObject = document.getElementsByName( tmpName );
	
	// 隐藏域的内容
	var val = '';
	for( var i=0; i<chkObject.length; i++ ){
		if( chkObject[i].checked != false ){
			if( val == '' ){
		    	val = chkObject[i].value;
			}
			else{
		  		val = val + ',' + chkObject[i].value;
			}
		}
	}
	
	var	obj = document.getElementById(checkName);
	if( obj != null ){
		obj.value = val;
	}
}


// 触发点击事件
function fireCheckClickEvent( boxName, index )
{
	var	objs = document.getElementsByName( boxName );
	if( objs.length > index && objs[index].readOnly == false ){
		// 设置checkbox的内容
		objs[index].checked = (objs[index].checked) ? false : true;
		objs[index].fireEvent( 'onclick' );
	}
}

function fireRadioClickEvent( boxName, index )
{
	var	objs = document.getElementsByName( boxName );
	if( objs.length > index && objs[index].readOnly == false ){
		objs[index].checked = true;
		objs[index].fireEvent( 'onclick' );
	}
}


// 创建browse对象
function _createBrowse( parentNode, name, valueset, parameter, multiple, width, properties )
{
	var fieldName = name;
	var frameName = '';
	var iptr = fieldName.indexOf( ':' );
	if( iptr > 0 ){
		frameName = fieldName.substring( 0, iptr );
		fieldName = fieldName.substring( iptr + 1 );
	}
	
	var _browseDefine = null;
	var _selectList = _selectFieldList;
	for( var ii=0; ii<_selectList.length; ii++ ){
		if( _selectList[ii].frameName == frameName && _selectList[ii].fieldName == fieldName ){
			_browseDefine = _selectList[ii];
			break;
		}
	}
	
	// 是否创建对象
	if( _browseDefine == null ){
		if( multiple == true || multiple == 'true' ){
			_browseDefine = new SelectInputField( frameName, fieldName, 'browsebox',
					 valueset, 'code', fieldName, '', '', '', parameter, null );
		}
		else{
			_browseDefine = new SelectInputField( frameName, fieldName, 'browsebox',
					 valueset, 'mix', fieldName, '', '_tmp_' + fieldName, '', parameter, null );
		}
		
		_browseDefine.loader_parameter = '';
		_selectList.push( _browseDefine );
	}
	else{
		if( _browseDefine.optionName != valueset ){
			_browseDefine.loader_parameter = '';
			_browseDefine.optionName = valueset;
			_browseDefine.selectList = null;
		}
	}
	
	// 创建域
	properties = (properties == null) ? '' : ' ' + properties;
	if( width == null ) width = '95%';
	
	if( multiple == true || multiple == 'true' ){
		var result = "<table width='" + width + "'><tr><td><input type='text' " +
			"name='" + name + "' id='" + name + "' checkFlag='false' multiple='true'" +
			properties +
			"></td></tr></table>";
	}
	else{
		var result = "<table width='" + width + "'><tr><td><input type='text' " +
			"name='_tmp_" + name + "' id='_tmp_" + name + "' checkFlag='false'" +
			properties +
			"></td></tr></table>";
	}
	
	if( parentNode == null ){
		document.write( result );
	}
	else{
		parentNode.innerHTML = result;
	}
	
	return _browseDefine;
}


// 用于下载二级下拉列表参数
var _browseLoadOptions = new Array();

function _BrowseOption( fieldName, index, value )
{
	this.fieldName = fieldName;
	this.index = index;
	this.value = value;
}

// 查找
function _lookupBrowseOption( fieldName, index )
{
	for( var ii=0; ii<_browseLoadOptions.length; ii++ ){
		var o = _browseLoadOptions[ii];
		if( o.fieldName == fieldName && o.index == index ){
			return ii;
		}
	}
	
	return -1;
}

// 增加
function _addBrowseInitInfo( fieldName, index, value )
{
	var i = _lookupBrowseOption( fieldName, index );
	if( i < 0 ){
		var o = new _BrowseOption( fieldName, index, value );
		_browseLoadOptions.push( o );
	}
}

// 下载参数
function __initBrowseValue()
{
	var param = null;
	for( var ii=0; ii<_browseLoadOptions.length; ii++ ){
		var o = _browseLoadOptions[ii];
		
		// 取定义信息
		var field = getSelectInfoByFieldName( o.fieldName );
		if( field == null ){
			continue;
		}
		
		// 修改取值函数
		field.getValue = _select_getBrowseValue;
		
		// 设置内容
		if( o.value != null && o.value != '' ){
			setFormFieldValue(o.fieldName, o.index, o.value);
		}
		
		// 下载数据
		var xurl = fz_prepareBrowseFieldOptions( field, o.index );
		if( xurl != null ){
			if( param == null ){
				param = xurl;
			}
			else{
				param = param + '&' + xurl;
			}
		}
	}
	
	// 避免重复下载
	_browseLoadOptions = new Array();
	
	if( param == null ){
		return;
	}
	
	// 下载参数
	var xurl = _browse.contextPath + "/tag.service?txn-code=load-option&" + param;
	openInnerWindow( xurl, 1, 1, null, false );
}


// 下载成功时处理
function _onLoadBrowseComplete( fieldName, index, options )
{
	if( options == null || options.length == 0 ){
		return;
	}
	
	// 取选择域信息
	var field = getSelectInfoByFieldName( fieldName );
	if( field == null ){
		return;
	}
	
	// 取原来的数据
	var fieldValue = getFormFieldValue( fieldName, index );
	
	// 设置下拉框数据
	field.setSelectList( options );
	
	// 设置新数据
	setFormFieldValue( fieldName, index, fieldValue );
}

//-->