<!--
// 设置域只读、设置域的最小输入长度、设置域的最大输入长度

// 当前正在处理的FORM的名称
var currentFormName = null;

// 增加隐藏的缺省表单域
function fz_addHiddenField( name, value )
{
	var	buffer = '<input type="hidden" id="' + name + '" name="' + name + '" value="' + value + '">';
	document.write( buffer );
}

// 初始化FORM
function fz_prepareForm( name, action, flowno )
{
	// 当前表单
	currentFormName = name;
	
	// 流水号
	fz_addHiddenField('inner-flag:flowno',flowno);
	
	// 窗口的打开方式
	fz_addHiddenField('inner-flag:open-type', openWindowType);
	
	// 数据是否已经修改过，对于初始的功能，比如增加记录时，这个值是真；对于修改的界面，根据内容设置
	fz_addHiddenField('inner-flag:modify-flag','false');
  
	// 缺省时把提交的数据重新返回:yes、success、failure、no
	fz_addHiddenField('inner-flag:reload-flag','yes');
	
	// 缺省时继续操作这个界面:yes、success、failure、no
	fz_addHiddenField('inner-flag:continue-flag','yes');

	// 关闭时是否需要导航到下一个页面，当满足FIELD_CONTINUE参数时跳转
	fz_addHiddenField('inner-flag:next-page','');
	
	// 操作成功时的提示信息，缺省为空
	fz_addHiddenField('inner-flag:success-hint','');
	
	// 操作失败时的提示信息，缺省为空
	fz_addHiddenField('inner-flag:failure-hint','');
	
	// 需要加载的节点名称
	fz_addHiddenField('inner-flag:load-node','');
	
	// 当前点击的按钮名称
	fz_addHiddenField('inner-flag:menu-name','');
}



// 字段信息：
// fieldName : 字段名称
function _FormFieldInfo( fieldName, field )
{
	// 名称 和 域
	this.fieldName = fieldName;
	this.field = field;
	
	// 序号在初始化的时候生成
	this.index = -1;
}

// 表单信息
function _FormDefine( formName )
{
	this.formName = formName;
	this.length = -1;	// 表单中元素的个数，用于判断域是否已经变化。这个不是fields中元素的个数
	this.fields = null;
}

// 表单列表
var _formDefineList = new Array();

// 取FORM的字段列表
function _getFormFieldList( formName )
{
	if( formName == null ){
		formName = currentFormName;
		if( formName == null ){
			return null;
		}
	}
	
	var form = document.forms[formName];
	if( form == null ) return null;
	
	if( form.elements == null ){
		alert( '页面上有两个同名的FORM[' + formName + ']' );
		form = form[0];
	}
	
	// 判断是否已经修改过
	var length = form.length;
	
	// 查找缓存区
	var formDefine = null;
	for( var ii=0; ii<_formDefineList.length; ii++ ){
		if( _formDefineList[ii].formName == formName ){
			formDefine = _formDefineList[ii];
			break;
		}
	}
	
	if( formDefine == null ){
		formDefine = new _FormDefine( formName );
		_formDefineList.push( formDefine );
	}
	else if( formDefine.length == length ){
		return formDefine.fields;
	}
	
	// browse中指定了show='name'和namebox 或show='mix和mixbox 时需要转换名称
	var browseNameMap = new Array();
	for( var x=0; x<_selectFieldList.length; x++ ){
		var bf = _selectFieldList[x];
		if( bf.selectType == 'browsebox' ){
			var fn = '';
			if( bf.showType == 'name' && bf.nameBox != '' ){
				fn = (bf.frameName != null && bf.frameName != "") ? bf.frameName + ':' + bf.nameBox : bf.nameBox;
			}
			else if( bf.showType == 'mix' && bf.mixBox != '' ){
				fn = (bf.frameName != null && bf.frameName != "") ? bf.frameName + ':' + bf.mixBox : bf.mixBox;
			}
			
			if( fn != '' ){
				var fn2 = (bf.frameName != null && bf.frameName != "") ? bf.frameName + ':' + bf.fieldName : bf.fieldName;
				browseNameMap.push( [fn, fn2] );
			}
		}
	}
	
	// 不能重复的域列表
	var _repeatField = new Array();
	
	// 字段列表
	var fieldList = new Array();
	
	// 分析域
	var elements = form.elements;
	for( var ii=0; ii<length; ii++ ){
		var obj = elements[ii];
		if( obj.name == null || obj.name == '' ){
			continue;
		}
		
		// 属性字段
		var fieldName = obj.name;
		if( fieldName.indexOf('attribute-node:') >= 0 ){
			continue;
		}
		
		// 内部临时域
		var ptr = fieldName.indexOf(':_flag');
		if( ptr >= 0 && ptr == fieldName.length-6 ){
			continue;
		}
		
		ptr = fieldName.indexOf(':_select-all');
		if( ptr >= 0 && ptr == fieldName.length-12 ){
			continue;
		}
		
		ptr = fieldName.indexOf('_selectPage');
		if( ptr >= 0 && ptr == fieldName.length-11 ){
			continue;
		}
		
		if( fieldName.indexOf('hidden:') == 0 ){
			continue;
		}
		
		// 分析域
		var tName = obj.tagName.toLowerCase();
		var fieldType = obj.type;
		if( tName == 'input' ){
			if( fieldType == 'hidden' || fieldType == 'button' || fieldType == 'submit' || fieldType == 'reset' ){
				continue;
			}
			else if( fieldType == 'radio' || fieldType == 'checkbox' ){
				// 过滤重复字段
				var _isRepeat = false;
				for( var jj=0; jj<_repeatField.length; jj++ ){
					if( fieldName == _repeatField[jj] ){
						_isRepeat = true;
						break;
					}
				}
				
				if( _isRepeat ){
					continue;
				}
				
				_repeatField.push( fieldName );
			}
			else if( fieldType == 'text' ){
				for( var x=0; x<browseNameMap.length; x++ ){
					if( fieldName == browseNameMap[x][0] ){
						fieldName = browseNameMap[x][1];
						break;
					}
				}
			}
			
			// 临时域 需要 转换
			var ptr = fieldName.indexOf( ':_tmp_' );
			if( ptr > 0 ){
				fieldName = fieldName.substring(0, ptr+1) + fieldName.substring(ptr+6);
			}
		}
		else if( tName == 'button' ){
			continue;
		}
		
		// alert( tName + '==' + fieldType );
		fieldList.push( new _FormFieldInfo(fieldName, obj) );
	}
	
	// 生成index
	for( var ii=0; ii<fieldList.length; ii++ ){
		if( fieldList[ii].index >= 0 ){
			continue;
		}
		
		var index = 1;
		var name = fieldList[ii].fieldName;
		fieldList[ii].index = 0;
		for( var jj=ii+1; jj<fieldList.length; jj++ ){
			if( fieldList[jj].fieldName == name ){
				fieldList[jj].index = index++;
			}
		}
	}
	
	/* var str = '';
	for( var ii=0; ii<fieldList.length; ii++ ){
		str = str + fieldList[ii].fieldName + ':' + fieldList[ii].index + '\n';
	}
	alert( fieldList.length + '==\n' + str ); */
	
	// 缓存
	formDefine.length = length;
	formDefine.fields = fieldList;
	
	return fieldList;
}


// 提示信息
document.write('<div id=freezeHintLayer style="position: absolute; z-index: 999; background-color:#FFCCCC; font-size: 10pt; color:#0000FF; display: none"></div>');


// 设置域的焦点
function _setFocus( obj )
{
	if( obj.disabled == true || obj.readOnly == true ){
		return;
	}
	
	// 设置焦点
	try{
		obj.focus();
	}
	catch( e ){
		
	}
}


// 取按钮列表
function fz_getButtonList( frameName )
{
	var	buttonList = new Array();
	
	// 空
	if( gframes == null ){
		return buttonList;
	}
	
	// 查找有效按钮
	var	ii, jj;
	
	// 判断是否取指定块的按钮列表
	if( frameName != null ){
		var frame = getFrameDefine( frameName );
		if( frame == null ){
			frame = getGridDefine( frameName );
		}
		
		if( frame != null && frame.menus != null ){
			for( jj=0; jj<frame.menus.length; jj++ ){
				buttonList = __addButton2List( buttonList, frameName, frame.menus[jj] );
			}
		}
		
		if( buttonList.length > 0 ){
			return buttonList;
		}
	}
	
	// 所有block的按钮列表
	for( ii=0; ii<gframes.length; ii++ ){
		var buttons = gframes[ii].menus;
		if( buttons != null ){
			for( jj=0; jj<buttons.length; jj++ ){
				buttonList = __addButton2List( buttonList, gframes[ii].frameName, buttons[jj] );
			}
		}
	}
	
	// 所有grid的按钮
	for( ii=0; ii<grids.length; ii++ ){
		var buttons = grids[ii].menus;
		if( buttons != null ){
			for( jj=0; jj<buttons.length; jj++ ){
				buttonList = __addButton2List( buttonList, grids[ii].gridName, buttons[jj] );
			}
		}
	}
	
	// 返回排序以后的列表
	return buttonList.sort(__compareButton);
}

// 增加按钮
function __addButton2List( buttonList, frameName, button )
{
	var obj = null;
	var buttonName = frameName + '_' + button.name;
	var objs = document.getElementsByName( buttonName );
	if( objs != null && objs.length > 0 ){
		obj = objs[0];
	}
	
	// 跳过禁止的按钮
	if( obj != null && obj.disabled == false && obj.style.display != 'none' ){
		binfo = new Array();
		binfo[0] = buttonName;
		binfo[1] = button.hotkey;
		binfo[2] = frameName;
		binfo[3] = button.menuId;
		buttonList[buttonList.length] = binfo;
	}
	
	return buttonList;
}


// 用于排序的比较函数
function __compareButton( button1, button2 )
{
	if( button1[3] == button2[3] ){
		return 0;
	}
	else if( button1[3] > button2[3] ){
		return 1;
	}
	else{
		return -1;
	}
}


// 显示提示信息
function fz_showFieldHint( obj )
{
	var	fieldType;
	var	hintLayer = document.all.freezeHintLayer;
	
	if( obj == null || obj.name == null || obj.name == '' ){
		fz_hiddenHintLayer();
		fz_currentFocusField = null;
	}
	else{
		// 显示被隐藏的select域
		if( hintLayer.style != null && hintLayer.style.display != "none" ){
			fz_showSelectBox();
		}
		
		// 检查是否已经有隐藏的域，可能是其它操作隐藏了select域
		if( fz_currentHiddenSelectBox != null ) return;
		fz_currentFocusField = obj;
		
		// 取名称
		var	objName = obj.name;
		if( objName.indexOf('_span_') == 0 ){
			objName = objName.substring(6);
		}
		
		// 序号
		var	index = getFormFieldIndex( obj );
		
		// 域的名称，对于选择域，显示的域不一定和域的名称一致，比如browsebox，或者指定了code参数
		var	field = getSelectFieldByShowBox( objName );
		
		// 提示信息
		var	hint = null;
		if( field == null ){
			// 格式信息
			hint = obj.hint;
			var formatHint = _getDataFormatHint( obj );
			if( formatHint != null ){
				if( hint == null ){
					hint = formatHint;
				}
				else{
					hint += '。' + formatHint;
				}
			}
		}
		else{
			// 显示提示信息的域
			if( field.selectType == 'radio' || field.selectType == 'checkbox' ){
				var	objs = document.getElementsByName( objName );
				obj = objs[0];
				index = 0;
			}
			
			hint = field.getHint( index );
		}
		
		// 是否有提示信息
		if( hint == null || hint == '' ){
			fz_hiddenHintLayer();
		}
		else{
			hintLayer.style.width = document.body.clientWidth * 0.8;
			hintLayer.innerHTML = '<span>' + hint + '</span>';
			hintLayer.style.display = '';
			
			// 计算宽度
			var	hintBody = hintLayer.childNodes[0];
			if( hintLayer.clientWidth > hintBody.offsetWidth + 10 ){
				hintLayer.style.width = hintBody.offsetWidth + 10;
			}
			
			// 定位：browse和select优先在上
			setHintframePosition( hintLayer, obj );
			
			// 隐藏select域
			fz_hiddenSelectBox( hintLayer );
		}
		
		// 弹出窗口
		if( field != null ){
			if( field.selectType == 'browsebox' && _browseAutoOpen ){
				var imgName = 'img:' + field.getFullName();
				var	imgObj = document.getElementsByName(imgName);
				if( imgObj.length > index ){
					imgObj[index].fireEvent( 'onclick' );
				}
			}
		}
		else if( obj._type == 'datebox' && _dateAutoOpen ){
			var	imgName = 'img:' + objName;
			var	imgObj = document.getElementsByName(imgName);
			if( imgObj.length > index ){
				imgObj[index].fireEvent( 'onclick' );
			}
		}
	}
}

// 重新设置提示框的显示位置
function fz_reShowFieldHint( obj )
{
	var	hintLayer = document.all.freezeHintLayer;
	if( hintLayer == null || hintLayer.style == null || hintLayer.style.display == "none" ){
		return;
	}
	
	// 显示被隐藏的select域
	fz_showSelectBox();
	
	// 定位：browse和select优先在上
	setHintframePosition( hintLayer, obj );
	
	// 隐藏select域
	fz_hiddenSelectBox( hintLayer );
}

// 隐藏提示信息
function fz_hiddenHintLayer()
{
	var	hintLayer = document.all.freezeHintLayer;
	if( hintLayer == null || hintLayer.style == null || hintLayer.style.display == "none" ){
		return;
	}
	
	// 隐藏
	hintLayer.style.display = "none";
	
	// 显示被隐藏的select域
	fz_showSelectBox();
}

function fz_showSelectBox()
{
	// 显示被隐藏的select域
	if( fz_currentHiddenSelectBox != null ){
		for( var ii=0; ii<fz_currentHiddenSelectBox.length; ii++ ){
			fz_currentHiddenSelectBox[ii].style.display = "";
		}
		
		fz_currentHiddenSelectBox = null;
	}
}

// 隐藏可能被hint覆盖的select域
function fz_hiddenSelectBox( layer )
{
	var	hintTop = layer.offsetTop;
	var	hintBottom = layer.offsetTop + layer.clientHeight;
	var	hintLeft = layer.offsetLeft;
	var	hintRight = layer.offsetLeft + layer.clientWidth;
	
	// 检查是否已经有隐藏的域
	if( fz_currentHiddenSelectBox != null ){
		return;
	}
	
	// 检查是否有被覆盖的域
	var objs = document.getElementsByTagName( 'select' );
	for( var ii=0; ii<objs.length; ii++ ){
		var obj = objs[ii];
		if( obj.style == null || obj.style.display == 'none' ) continue;
		
		var t = obj.offsetTop;
		var l = obj.offsetLeft;
		var h = obj.clientHeight;
		var w = obj.clientWidth;
		while (obj = obj.offsetParent){
	 		t += obj.offsetTop - obj.scrollTop;
	 		l += obj.offsetLeft - obj.scrollLeft;
	 	}
	 	
	 	// 横座标
	 	if( l >= hintRight || l+w <= hintLeft ) continue;
		
		// 检查是否有冲突
		if( t >= hintBottom || t+h <= hintTop ) continue;
		
		if( fz_currentHiddenSelectBox == null ){
			fz_currentHiddenSelectBox = new Array();
		}
		
		var	num = fz_currentHiddenSelectBox.length;
		fz_currentHiddenSelectBox[num] = objs[ii];
		objs[ii].style.display = "none";
	}
}


// 取当前域的序号
function fz_getCurrentFieldIndex( obj )
{
	if( obj == null || obj.name == null || obj.name == '' || obj.form == null ){
		return -1;
	}
	
	// FORM的名称
	var formName = obj.form.name;
	
	// 取名称
	var	fieldName = obj.name;
	var index = getFormFieldIndex( obj );
	
	// 域的名称，对于选择域，显示的域不一定和域的名称一致，比如browsebox，或者指定了code参数
	var	field = getSelectFieldByShowBox( fieldName );
	if( field != null ){
		fieldName = field.getFullName();
	}
	
	var	fieldIndex = -1;
	var varList = _getFormFieldList( formName );
	for( var ii=0; ii<varList.length; ii++ ){
		if( varList[ii].fieldName == fieldName && varList[ii].index == index ){
			fieldIndex = ii;
			break;
		}
	}
	
	return fieldIndex;
}



// 跳到下一个域
// fieldIndex : 域的编号
// nextFlag : true=向后、false=向前
// 没有导航到下一个域时返回false
function fz_nextInputField( fieldIndex, nextFlag )
{
	var varList = _getFormFieldList( );
	if( varList == null ){
		return false;
	}
	
	// 判断是否为textarea
	if( fieldIndex >= 0 && fieldIndex < varList.length ){
		var	field = varList[fieldIndex];
		var objs = document.getElementsByName( field.fieldName );
		if( objs.length > field.index ){
			var obj = objs[field.index];
			if( obj.tagName != null && obj.tagName.toLowerCase() == 'textarea' ){
				return false;
			}
		}
	}
	else if( fieldIndex < 0 ){
		fieldIndex = -1;
	}
	else{
		return false;
	}
	
	// nextFlag : true=向后、false=向前
	if( nextFlag != false ){
		nextFlag = true;
	}
	
	// 设置下一个域的焦点
	if( nextFlag ){
		for( var ii=fieldIndex+1; ii<varList.length; ii++ ){
			var objName = varList[ii].fieldName;
			var index = varList[ii].index;
			
			// 设置焦点
			if( _fz_setFieldFocus(objName, index) ){
				return true;
			}
		}
	}
	else{
		for( var ii=fieldIndex-1; ii>-1; ii-- ){
			var objName = varList[ii].fieldName;
			var index = varList[ii].index;
			
			// 设置焦点
			if( _fz_setFieldFocus(objName, index) ){
				return true;
			}
		}
	}
	
	// 取frame的名称
	var frameName = null;
	if( fieldIndex >= 0 && fieldIndex < varList.length ){
		var ptr = varList[fieldIndex].fieldName.indexOf( ':' );
		if( ptr > 0 ){
			frameName = varList[fieldIndex].fieldName.substring( 0, ptr );
		}
	}
	
	// 再查看按钮
	var	button = null;
	var	buttonList = fz_getButtonList( frameName );
	
	// 隐藏提示信息
	fz_hiddenHintLayer( );
	fz_currentFocusField = null;
	
	// 导航到按钮
	if( nextFlag ){
		for( var ii=0; ii<buttonList.length; ii++ ){
			var bt = document.getElementsByName(buttonList[ii][0])[0];
			if( bt.disabled == false ){
				button = bt;
				break;
			}
		}
	}
	else{
		for( var ii=buttonList.length-1; ii>-1; ii-- ){
			var bt = document.getElementsByName(buttonList[ii][0])[0];
			if( bt.disabled == false ){
				button = bt;
				break;
			}
		}
	}
	
	// 设置按钮的焦点
	if( button != null ){
		_setFocus( button );
		return true;
	}
	
	return false;
}

// 设置域的焦点：返回[true]时设置焦点成功
function _fz_setFieldFocus( objName, index )
{
	// 是否可以设置焦点
	if( isFormFieldVisible(objName, index) == false ){
		// 不可见
		return false;
	}
	
	if( isFormFieldReadonly(objName, index) || isFormFieldDisabled(objName, index) ){
		// 只读或者禁止
		return false;
	}
	
	// 查找选择域的显示域名称
	var	field = getSelectInfoByFieldName( objName );
	if( field != null ){
		objName = field.getSelectShowBoxName( );
	}
	
	var objs = document.getElementsByName( objName );
	if( objs.length <= index ){
		return false;
	}
	
	var obj = objs[index];
	
	// 设置焦点
	_setFocus( obj );
	
	// 光标移动到尾
	if( obj.type == 'text' ){
		obj.value = obj.value;
	}
	
	// 过虑选择项
	fz_filterOptionList( obj );
	
	// 显示提示信息
	fz_showFieldHint( obj );
	return true;
}

// 设置按钮的焦点：上箭头和左箭头 前移：下箭头和右箭头 后移
function fz_moveButtonFocus( obj, keyCode )
{
	var	ii;
	var	buttonList = fz_getButtonList();
	
	// 只有一个按钮
	if( buttonList.length <= 1 ){
		return;
	}
	
	// 匹配名称
	for( ii=0; ii<buttonList.length; ii++ ){
		if( buttonList[ii][0] == obj.name ){
			break;
		}
	}
	
	if( ii >= buttonList.length ){
		return;
	}
	
	if( keyCode == 37 || keyCode == 38 ){
		// 上箭头
		if( ii > 0 ){
			ii = ii - 1;
		}
		else{
			ii = buttonList.length - 1;
		}
	}
	else{
		// 下箭头
		if( ii == buttonList.length - 1 ){
			ii = 0;
		}
		else{
			ii = ii + 1;
		}
	}
	
	// 设置焦点
	var	nextName = buttonList[ii][0];
	var	nextButton = document.getElementsByName( nextName );
	_setFocus( nextButton[0] );
}


// 对于select域 遇到 TAB键时的特殊处理
function fz_selectFieldTabKey()
{
	var obj = window.event.srcElement;
	var	field = getSelectFieldByShowBox( obj.name );
	if( field == null || field.selectType != 'select' ){
		return false;
	}
	
	// 跳到下一个域
	var varList = _getFormFieldList( );
	if( varList == null ){
		return false;
	}
	
	var fieldName = field.getFullName();
	for( ii=0; ii<varList.length-1; ii++ ){
		if( varList[ii].fieldName == fieldName ){
			break;
		}
	}
	
	if( ii >= varList.length-1 ){
		fz_showFieldHint( null );
		return false;
	}
	
	// 跳到下一个域
	fieldName = varList[ii+1].fieldName;
	var index = varList[ii+1].index;
	
	// 对于选择域，需要取显示的域名称
	var	field = getSelectInfoByFieldName(fieldName);
	if( field != null ){
		fieldName = field.getSelectShowBoxName();
	}
	
	// 取显示的对象
	var	objs = document.getElementsByName(fieldName);
	if( objs.length <= index ){
		fz_showFieldHint( null );
		return false;
	}
	
	// 显示提示信息，过虑选择域
	obj = objs[index];
	
	// 过虑选择项
	fz_filterOptionList( obj );
	
	// 显示提示信息
	fz_showFieldHint( obj );
	
	return true;
}



// 处理frame的按键事件
function fire_frameKeyEvent( obj, eventName )
{
	// 取frame对象
	var frameObject = getFieldFrameObject( obj );
	if( frameObject == null ){
		return;
	}
	
	// 取frame
	var frameName = frameObject.id;
	var	frame = getFrameDefine( frameName );
	if( frame == null ){
		return;
	}
	
	// 判断是否有处理函数
	var	eventFunction = null;
	if( eventName == 'onkeyup' ){
		eventFunction = frame.onkeyup;
	}
	else if( eventName == 'onkeydown' ){
		eventFunction = frame.onkeydown;
	}
	
	// 调用事件
	if( eventFunction != null && eventFunction != "" ){
		return eval(eventFunction);
	}
}


// 缺省导航键的处理
function process_defaultKey( obj, nextFlag )
{
	var	fieldIndex = fz_getCurrentFieldIndex( obj );
	if( fieldIndex >= 0 ){
		fz_showFieldHint( null );
		
		// 判断是否已经跳转到下一个域
		var focusFlag = fz_nextInputField( fieldIndex, nextFlag );
		if( focusFlag == true ){
			window.event.keyCode = 0;
			window.event.returnValue = false;
			return false;
		}
	}
	else{
		if( obj.tagName == 'BODY' ){
			fz_showFieldHint( null );
			setFirstFieldFocus();
			window.event.keyCode = 0;
			window.event.returnValue = false;
			return false;
		}
	}
	
	return true;
}

// 上下键处理函数
function process_updownKey(obj, keyCode)
{
	if( obj.name == null || obj.name == '' ){
		return true;
	}
	
	// SELECT域不处理
	if( obj.tagName == null || obj.tagName.toLowerCase() == 'select' ){
		return true;
	}
	
	var fieldName = obj.name;
	
	// 判断是否是grid的数据项
	var	grid = null
	var ptr = fieldName.indexOf( ':' );
	if( ptr > 0 ){
		var	gridName = fieldName.substring(0, ptr);
		grid = getGridDefine(gridName);
	}
	
	if( grid != null ){
		var objs = document.getElementsByName( obj.name );
		if( objs.length > 1 ){
			var	fieldIndex = 0;
			for( fieldIndex=0; fieldIndex<objs.length; fieldIndex++ ){
				if( objs[fieldIndex] == obj ){
					break;
				}
			}
			
			if( keyCode == 38 ){
				fieldIndex = fieldIndex - 1;
			}
			else{
				fieldIndex = fieldIndex + 1;
			}
			
			if( fieldIndex >= 0 && fieldIndex < objs.length ){
				var nextField = objs[fieldIndex];
				_setFocus( nextField );
				window.event.keyCode = 0;
				window.event.returnValue = false;
				return false;
			}
		}
	}
	else{
		if( keyCode == 38 ){
			var nextFlag = false;
		}
		else{
			var nextFlag = true;
		}
		
		process_defaultKey( obj, nextFlag );
	}
}


// 快捷键
// onkeyup
function event_fieldKeyUp()
{
	var	obj = window.event.srcElement;
	if( obj == null ){
		fz_showFieldHint( null );
		return;
	}
	
	// 处理frame的按键事件
	if( fire_frameKeyEvent( obj, 'onkeyup' ) == false ){
		return;
	}
	
	// ESC
	if( window.event == null || window.event.keyCode == 27 ){
		fz_showFieldHint( null );
		return;
	}
	
	// 快捷键
	var keyName = fz_getCurrentKeyCode();
	if( _keyboard.fireEvent(keyName) ){
		window.event.keyCode = 0;
		window.event.returnValue = false;
		return;
	}
	
	// 按键代码
	var	keyCode = window.event.keyCode;
	
	// 是否是按钮，按钮支持左右键和上下键
	if( obj.type == 'button' && gframes != null ){
		if( keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ){
			fz_moveButtonFocus( obj, keyCode );
		}
		
		return;
	}
	
	// 判断是否导航键
	if( _isDefaultTabKey(keyCode) ){
		if( window.event.shiftKey ){
			var nextFlag = false;
		}
		else{
			var nextFlag = true;
		}
		
		if( process_defaultKey(obj, nextFlag) == false ){
			return;
		}
	}
	
	// 上下箭头
	// 38 = UP、40=DOWN
	if( keyCode == 38 || keyCode == 40 ){
		if( process_updownKey(obj, keyCode) == false ){
			return;
		}
	}
	
	if( fz_currentFocusField != obj ){
		// 过虑选择项
		fz_filterOptionList( obj );
		
		// 显示提示信息
		fz_showFieldHint( obj );
	}
	
	// 处理用户事件
	if( _browse.onkeyup != null ){
		_browse.onkeyup();
	}
}

// onmousedown
function event_fieldMouseDown()
{
	if( window.event == null ){
		fz_showFieldHint( null );
		return;
	}
	
	var	obj = window.event.srcElement;
	if( obj == null ){
		fz_showFieldHint( null );
		return;
	}
	
	// 取FORM的名称
	if( obj.form != null ){
		var formName = obj.form.name;
		if( formName != null ){
			currentFormName = formName;
		}
	}
	
	// 设置焦点:select域不要设置焦点，否则弹出下拉框后不能滚动
	if( obj.type == null || (obj && obj.type && obj.type.indexOf('select') < 0) ){
		_setFocus( obj );
	}
	
	if( fz_currentFocusField != obj ){
		// 显示提示信息
		fz_showFieldHint( obj );
	}
	
	// 处理用户事件
	/*if( _browse.onmousedown != null ){
		_browse.onmousedown();
	}*/
}

// 鼠标移动到select域时过虑
function event_fieldMouseOver()
{
	if( window.event == null ){
		return;
	}
	
	var	obj = window.event.srcElement;
	if( obj == null ){
		return;
	}
	
	// 过虑
	if( obj.disabled == false ){
		// 过虑选择项
		fz_filterOptionList( obj );
	}
	
	// 处理用户事件
	/*if( _browse.onmouseover != null ){
		_browse.onmouseover();
	}*/
}

// onkeydown
function event_fieldKeyDown()
{
	if( window.event == null || window.event.srcElement == null ) return;
	
	// 取FORM的名称
	var	obj = window.event.srcElement;
	if( obj.form != null ){
		var formName = obj.form.name;
		if( formName != null ){
			currentFormName = formName;
		}
	}
	
	
	// DELETE 在可编辑域的处理
	if( event.keyCode == 8 ){
		// 可编辑域，执行原来的操作
		if( obj.type == "text" || obj.type == "textarea" || obj.type == "password" || obj.type == "file" ){
			// 判断是否只读
			if( obj.readOnly || obj.disabled ){
				window.event.keyCode=0;
				window.event.returnValue=false;
			}
			
			return;
		}
		
		// XsDhtmlEditor
		if( obj.tagName == "XsDhtmlEditor" ) return;
		
		// 可编辑域
		var editAble = obj.contentEditable;
		if( editAble == "inherit" ){
			var pobj = obj;
			while( pobj = pobj.parentElement ){
				editAble = pobj.contentEditable;
				if( editAble == "true" || editAble == "false" ) break;
			}
			
			if( pobj == null ) editAble = "false";
		}
		
		// 可编辑域，执行原来的操作
		if( editAble == "true" ) return;
	}
	
	// 处理frame的按键事件
	if( fire_frameKeyEvent( obj, 'onkeydown' ) == false ) return;
	
	var	keyCode = fz_getCurrentKeyCode();
	if( keyCode == null ) return;
	
	// select域的TAB键需要特殊处理 对于IE6 有效， 其他的需要测试
	if( keyCode == 'TAB' ){
		fz_selectFieldTabKey();
	}
	
	// 别名
	var	aliasName = fz_getKeyAliasName( keyCode );
	
	// 定位快捷键
	var	buttonName = null;
	var	buttonList = fz_getButtonList();
	for( var ii = 0; ii < buttonList.length; ii ++ ){
		if( buttonList[ii][1] == keyCode || buttonList[ii][1] == aliasName ){
			buttonName = buttonList[ii][0];
		}
	}

	// 下箭头对browse域展开
	if( buttonName == null ){
		if( keyCode == 'DOWN' ){
			openBrowseFieldBox( window.event.srcElement );
			return;
		}
	}
	else{
		// 触发click事件，按钮的快捷键
		var	obj = document.getElementsByName(buttonName)[0];
		if( obj.disabled == false ){
			_setFocus( obj );
			obj.fireEvent( 'onclick' );
			window.event.keyCode = 0;
			window.event.returnValue = false;
			return;
		}
	}
	
	// 判断是否是F5
	if( window.event.keyCode == 116 ){
		window.event.keyCode=0;
		window.event.returnValue=false;
		return;
	}
	
	// CTRL + N = 打开新窗口
	if( event.ctrlKey && event.keyCode == 78 ){
		window.event.keyCode=0;
		window.event.returnValue=false;
		return;
	}
	
	// CTRL + W = 打开新窗口
	if( event.ctrlKey && event.keyCode == 119 ){
		event.keyCode=0;
		event.returnValue=false;
		return;
	}
	
	// CTRL + R = 刷新窗口
	if( event.ctrlKey && event.keyCode == 82 ){
		event.keyCode=0;
		event.returnValue=false;
		return;
	}
	
	// SHIFT + F10 打开菜单
	if( (event.shiftKey || event.ctrlKey) && event.keyCode == 121 ){
		event.keyCode=0;
		event.returnValue=false;
		return;
	}
	
	// DELETE 回退
	if( event.keyCode == 8 ){
		event.keyCode=0;
		event.returnValue=false;
		return;
	}
	
	// 上一页、下一页
	if( event.keyCode == 166 || event.keyCode == 167 ){
		event.keyCode=0;
		event.returnValue=false;
		return;
	}
	
	// 处理用户事件
	if( _browse.onkeydown != null ){
		_browse.onkeydown();
	}
}

// 快捷键
document.onkeyup = event_fieldKeyUp;
document.onkeydown = event_fieldKeyDown;
document.onmousedown = event_fieldMouseDown;
document.onmouseover = event_fieldMouseOver;

// 需要隐藏的select域，避免不能正确显示 freezeHintLayer
var	fz_currentHiddenSelectBox = null;

// 当前具有焦点的域
var	fz_currentFocusField = null;


// 设置第一个可输入的域
function setFirstFieldFocus()
{
	if( document.readyState != 'complete' ){
		setTimeout( setFirstFieldFocus, 1 );
	}
	else{
		fz_nextInputField( -1 );
	}
}


// 缺省时把提交的数据重新返回:yes、success、failure、no
function setReloadFlag( flag )
{
  var field = document.forms[currentFormName].elements["inner-flag:reload-flag"];
  if( field != null ){
    field.value = flag;
  }
}

// 缺省时继续操作这个界面:yes、success、failure、no
function setContinueFlag( flag )
{
  var field = document.forms[currentFormName].elements["inner-flag:continue-flag"];
  if( field != null ){
    field.value = flag;
  }
}

// 关闭时是否需要导航到下一个页面，当满足FIELD_CONTINUE参数时跳转
function setNextPage( url )
{
  var field = document.forms[currentFormName].elements["inner-flag:next-page"];
  if( field != null ){
    field.value = url;
  }
}

// 记录是否被修改:true=被修改
function setModifyFlag( flag )
{
  var field = document.forms[currentFormName].elements["inner-flag:modify-flag"];
  if( field != null ){
    field.value = flag;
  }
}

// 交易处理成功时的提示信息
function setSuccessHint( hint )
{  
  if( currentFormName == null ){
	return 0;
  }
  
  if( hint == null ){
  	hint = '';
  }
  
  var field = document.forms[currentFormName].elements["inner-flag:success-hint"];
  if( field != null ){
    field.value = hint;
  }
}

// 交易处理失败时的提示信息
function setFailureHint( hint )
{  
  if( currentFormName == null ){
	return 0;
  }
  
  if( hint == null ){
  	hint = '';
  }

  var field = document.forms[currentFormName].elements["inner-flag:failure-hint"];
  if( field != null ){
    field.value = hint;
  }
}

// 当前页面连续成功的记录数量
function getSuccessNumber( )
{
	if( currentFormName == null ){
		return 0;
	}
	
	var field = document.forms[currentFormName].elements["inner-flag:success-number"];
	if( field != null ){
		return parseInt(field.value);
	}
	else{
		return 0;
	}
}

// 设置需要加载的节点名称
function setLoadNode( nodeName )
{
	if( currentFormName == null ){
		return 0;
	}
	
  var field = document.forms[currentFormName].elements["inner-flag:load-node"];
  if( field != null ){
    field.value = nodeName;
  }
}



// 清除数据
function clearFormFieldValue( formName )
{
	// 取FORM
	if( formName == null ){
		var	obj = window.event.srcElement;
		if( obj == null || obj.form == null ){
			return;
		}
		
		formName = obj.form.name;
	}
	
	// 取字段列表
	var	fieldList = _getFormFieldList( formName );
	if( fieldList == null ){
		return;
	}
	
	// 清空内容
	var	obj;
	var	value;
	for( var ii=0; ii<fieldList.length; ii++ ){
		field = fieldList[ii];
		
		// 先检查original:字段
		obj = document.getElementsByName('original:' + field.fieldName);
		if( obj != null && obj.length > field.index ){
			obj = obj[field.index];
			value = obj.value;
		}
		else{
			value = getFormFieldDefaultValue( field.fieldName, field.index );
			if( value == null ){
				value = '';
			}
		}
		
		setFormFieldValue( field.fieldName, field.index, value );
	}
}

// 提交表单
function _formSubmit( formName, hint, action )
{
	if( formName == null ){
		// 取缺省的表单
		var obj = null;
		if (window.event != null) {
			var obj = window.event.srcElement;
		}
		if( obj != null ){
			formName = _getFormName( obj );
		}
		else{
			formName = currentFormName;
		}
		
		// 没有指定需要提交的表单
		if( formName == null ){
			return false;
		}
	}
	
	// 检查有效性
	var	flag = checkFormValidator( formName );
	if( flag == false ){
		return false;
	}
	
	// 检查页面数据是否修改过，对于不能重入的请求，数据没有修改时不允许提交
	var reentry = document.forms[formName].reentry;
	if( reentry == 'false' ){
		// 内容修改标志
		var modifyFlag = document.getElementById('inner-flag:modify-flag').value;
		if( modifyFlag == 'false' ){
			// 对于处理成功时不继续处理的请求，按调用成功处理
			var continueFlag = document.getElementById('inner-flag:continue-flag').value;
			if( continueFlag == 'false' || continueFlag == 'no' || continueFlag == 'failure' ){
				var nextPage = document.getElementById('inner-flag:next-page').value;
				
				// 当前页面处理成功的记录数量
				var successNumber = document.getElementById('inner-flag:success-number').value;
				if( isNaN(parseInt(successNumber))==true ){
					successNumber = 0;
				}
				else{
					successNumber = parseInt(successNumber);
				}
				
				// 设置错误代码
				errorCode = '100000';
				
				// 模式窗口关闭窗口，其它窗口刷新页面
				_auto_goBack( nextPage, successNumber );
			}
			else{
				// 不要在这里设置错误代码，因为只有处理成功的页面才会检查数据修改标志
				// 如果在这里设置了错误代码，下一次提交请求时，inner-flag:modify-flag=true
				alert( '错误[100000] ==> 数据没有被修改过，交易不能提交' );
			}
			
			return false;
		}
	}
	
	// 增加本地日志，检查是否重复提交
	var rst = _browse.addLogger( window, formName );
	if( rst != null ){
		alert( rst );
		return false;
	}
	
	// 设置页面已经提交过的标志
	if( reentry == 'false' ){
		_browse.addSubmitPage( window );
	}
	
	// 修改提交的动作
	if( action == null || action == '' ){
		action = document.forms[formName].action;
	}
	
	// 增加参数:pre-page
	if( _prePageName != '' ) action = _addHrefParameter( action, 'inner-flag:pre-page', _prePageName );
	
	// 格式化action
	
	document.forms[formName].action = _browse.resolveURL( action );
	
	clickFlag = 1;
	_showProcessHintWindow( hint );
	document.forms[formName].submit();
	
	return true;
}


// 加载明细数据
function _loadDetailRecord()
{
  setContinueFlag('yes');
  setReloadFlag('yes');
  
  // 提交数据
  _formSubmit( null, '正在加载明细记录 ... ...' );
}

// 保存记录
function saveRecord( successHint, failureHint, hint )
{
  setSuccessHint( successHint );
  setFailureHint( failureHint );
  setContinueFlag('yes');
  setReloadFlag('yes');
  
  // 提交数据
  if( hint == null ){
  	hint = '正在保存记录 ... ...';
  }
  
  _formSubmit( null, hint );
}

// 保存记录，不回显数据，继续增加数据
function saveAndContinue( successHint, failureHint )
{
  setSuccessHint( successHint );
  setFailureHint( failureHint );
  setContinueFlag('yes');
  setReloadFlag('failure');
  
  // 提交数据
  _formSubmit( null, '正在保存记录 ... ...' );
}

// 保存记录并退出，如果不设置nextPage，返回到上一个窗口时不刷新数据
function saveAndExit( successHint, failureHint, nextPage )
{
  // 设置标志位
  setSuccessHint( successHint );
  setFailureHint( failureHint );
  setReloadFlag('yes');
  
  // 缺省导航到上一个页面
  if( nextPage == null ){
  	nextPage = _prePageName;
  }
  
  if( nextPage != null && nextPage != '' ){
	  var addr;
	  if( nextPage.indexOf('?') > 0 ){
	  	addr = nextPage + '&inner-flag:back-flag=true';
	  }
	  else{
	  	addr = nextPage + '?inner-flag:back-flag=true';
	  }
	  
  	  setContinueFlag('failure');
	  setNextPage( addr );
  }
  else if( openWindowType == 'modal' || openWindowType == 'new-window' ){
  	setContinueFlag('failure');
  }
  else{
  	alert( '必须设置处理成功以后的导航页面地址' );
  	setContinueFlag('yes');
  }
  
  // 提交数据
  _formSubmit( null, '正在保存记录 ... ...' );
}

// 保存记录并导航到下一个页面
function saveAndGoNext( successHint, failureHint, nextPage )
{
	if( nextPage == null || nextPage == '' ){
		alert( '没有设置下一个导航页' );
		return;
	}
	
	setSuccessHint( successHint );
	setFailureHint( failureHint );
	setContinueFlag('failure');
	setReloadFlag('yes');
	setNextPage( nextPage );
	
	// 提交数据
	_formSubmit( null, '正在保存记录 ... ...' );
}

// 查询记录
function _querySubmit( )
{
	//DC2-jufeng-20120724
	var pageRow2 = 10;
	var ss = document.getElementById('customPageRowSeleted')
	/*var index = ss.selectedIndex
	if( index&&index==-1 ){
			// 重置每个GRID的开始行
		for( var ii=0; ii<grids.length; ii++ ){
			getAttributeObject(grids[ii].gridName, grids[ii].index, 'start-row').value = '1';
		}
	}else{
		pageRow2 = parseInt( ss.options[ss.selectedIndex].text );
		// 重置每个GRID的开始行
		for( var ii=0; ii<grids.length; ii++ ){
			getAttributeObject(grids[ii].gridName, grids[ii].index, 'start-row').value = '1';
			getAttributeObject(grids[ii].gridName, grids[ii].index, 'page-row').value = pageRow2;
		} 
		
	}*/
	for( var ii=0; ii<grids.length; ii++ ){
			getAttributeObject(grids[ii].gridName, grids[ii].index, 'start-row').value = '1';
		}
	_formSubmit(null, '正在查询，请等待... ...');
}

// 下载文件
function download( successHint, failureHint )
{
  setSuccessHint( successHint );
  setFailureHint( failureHint );
  setContinueFlag('yes');
  setReloadFlag('yes');
  
  // 提交数据
  _formSubmit( null, '正在申请文件 ... ...' );
  
  // 使按钮有效
  clickFlag = 0;
  checkAllMenuItem();
  
  // 关闭提示窗口
  _hideProcessHintWindow();
}


/* ******************** 初始化 ************************** */

// 初始化页面信息，包括grid、block、browse、select、radio、check、标题等
function __initHtmlPage( )
{
	// 初始化FORM
	var fs = document.forms;
	if( fs != null ){
		for( var ii=0; ii<fs.length; ii++ ){
			var formName = fs[ii].name;
			
			// 初始化
			var varList = _getFormFieldList( formName );
			if( varList != null ){
				for( var jj=0; jj<varList.length; jj++ ){
					// 初始化格式
					_initFieldStyle( varList[jj].fieldName, varList[jj].index, varList[jj].field );
					
					// 生成标题
					_setFieldCaption( varList[jj].fieldName, varList[jj].index, varList[jj].field );
					
					// 初始化SELECT域
					_select_initField( varList[jj].fieldName, varList[jj].index );
				}
			}
		}
	}
	
	// 初始化grid
	for( var ii=0; ii<grids.length; ii++ ){
		grids[ii].initGridStyle( );
	}
}

// 设置页面的初始化函数
_browse.initHtmlPage = __initHtmlPage;




/* ************************ 检查有效性 ******************** */

// 验证有效性
function checkFormValidator( formName )
{
	// 清空转换数据时保存的原始数据列表
	valid_original_value_list = new Array();
	
	// 检查文本域的有效性
	var varList = _getFormFieldList( formName );
	if( varList != null ){
		for( var ii=0; ii<varList.length; ii++ ){
			var fieldName = varList[ii].fieldName;
			var index = varList[ii].index;
			if( _form_checkFieldValid(fieldName, index) == false ){
				reset_valid_original_value();
				return false;
			}
		}
	}
	
	// 参数
	var _form = document.forms[formName];
	onsubmit = _form._onsubmit;
	
	// 提交前的处理
	if( onsubmit != null && onsubmit != '' ){
		onsubmit = trimSpace( onsubmit );
		if( onsubmit.indexOf('return') == 0 ){
			onsubmit = onsubmit.substring( 6 );
			onsubmit = trimSpace( onsubmit );
		}
		
		if( onsubmit.indexOf('(') < 0 ){
			onsubmit = onsubmit + '()';
		}
		
		var b = eval( onsubmit );
		if( b == false ){
			reset_valid_original_value();
			return false;
		}
	}
	
	// 清空转换数据时保存的原始数据列表，有效数据在提交的时候需要转换，比如密码等，如果检查未通过，需要恢复成原有的数据
	valid_original_value_list = new Array();
	
	// 计算页面数据是否修改过
	var b = __checkModify( formName );
	
	var modifyFlag = _form._modify;
	if( modifyFlag == 'true' ){
		b = true;
	}
	else if( errorCode != '' && errorCode != '000000' ){
		b = true;
	}
	
	if( b ){
		setModifyFlag( 'true' );
	}
	
	return true;
}


// 判断FORM中的数据是否已经被修改
function __checkModify( formName )
{
	var flag = false;
	
	// 显示域的个数
	var visibleFieldNumber = 0;
	
	// 字段列表
	var varList = _getFormFieldList( formName );
	if( varList == null ){
		return true;
	}
	
	// 比较值是否被修改
	var val;
	var _initValue;
	
	for( var i=0; i<varList.length; i++ ){
	  	var index = varList[i].index;
	  	var fieldName = varList[i].fieldName;
	  	
	  	// 判断域是否看见
	  	if( isFormFieldVisible(fieldName, index) ){
	  		visibleFieldNumber ++;
	  	}
	  	
	  	// 取对象
	    var obj = document.getElementsByName( fieldName );
	  	
	  	// 是否自定义SELECT域
		var selField = getSelectInfoByFieldName( fieldName );
		if( selField != null ){
	  		if( selField.selectType == "radio" ){
	  			val = "";
	  			for( var j=0; j<obj.length; j++ ){
		    		if( obj[j].checked == true ){
		    			val = obj[j].value;
		    			break;
		    		}
		    	}
		    }
		    else{
		    	val = obj[index].value;
		    }
		    
		    // 初始化的值
		    _initValue = selField.getInitValue( index );
	  	}
	  	else if( obj[index]._type == 'edit' ){	// 内容不转换，并且认为已经修改
	  		val = '@';
	  	}
	  	else{
	  		// 格式化
	  		val = obj[index].value.replace(/[\']/g, '"' );	// '"
	    	
	    	// 是否删除空格
	    	if( _keepFieldSpace == false ){
	    		val = trimSpace( val, 0 );
	    	}
	    	
	    	obj[index].value = val;
	    	val = val.replace(/[\n\r\\]/g, '`' );
	    	
	    	// 初始化的值
	    	_initValue = obj[index]._value;
	  	}
	  	
	  	// 初始化的值可能空
    	if( _initValue == null ){
    		_initValue = '';
    	}
    	
	  	// 比较
	    if( val != _initValue ){
			flag = true;
	    }
	}
	
	// 如果没有可见字符，视作被修改过
	if( visibleFieldNumber == 0 ){
		flag = true;
	}
	
	return flag;
}


// 检查域的有效性
function _form_checkFieldValid( fieldName, index )
{
	var field = getSelectInfoByFieldName( fieldName );
	if( field == null ){
		var obj = document.getElementsByName( fieldName );
		if( obj.length <= index ){
			return true;
		}
		
		if( _checkInputFieldValid(obj, index) == false ){
			return false;
		}
	}
	else{
		// 变量名称
		var	showBox = field.getSelectShowBoxName( );
		var	obj = document.getElementsByName(showBox)
		if( obj.length <= index ){
			return true;
		}
		
		if( field.checkOneSelectField(obj, index) == false ){
			return false;
		}
	}
	
	return true;
}



// 域的操作函数:visible

// 隐藏域
// frame:frame的名称
// fieldName : 域的名称
// visibleFlag : 是否显示：true=显示、false=隐藏
// 向前兼容，请使用setAllFieldVisible 代替
function setFieldVisible( frameName, fieldName, visibleFlag )
{
	// 取域的名称
	var	fname;
	if( frameName == null || frameName == '' ){
		fname = fieldName;
	}
	else{
		fname = frameName + ':' + fieldName;
	}
	
	setAllFieldVisible( fname, visibleFlag );
}

// 设置所有域的显示属性
function setAllFieldVisible( fieldName, visibleFlag )
{
	// 样式
	var	vs;
	if( visibleFlag == true || visibleFlag == 'true' ){
		vs = '';
	}
	else{
		vs = 'none';
	}
	
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		if( selField.selectType == 'radio' || selField.selectType == 'checkbox' ){
			selField.setVisible( 0, visibleFlag );
		}
		else{
			// 隐藏所有域
			var showName = selField.getSelectShowBoxName();
			var	objs = document.getElementsByName( showName );
			for( var ii=0; ii<objs.length; ii++ ){
				selField.setVisible( ii, visibleFlag );
			}
		}
	}
	else{
		// 设置标题的状态
		var	labelBox = document.getElementsByName( 'label:' + fieldName );
		for( ii=0; ii<labelBox.length; ii++ ){
			labelBox[ii].style.display = vs;
		}
		
		// 设置图形的状态
		var	imgBox = document.getElementsByName( 'img:' + fieldName );
		for( ii=0; ii<imgBox.length; ii++ ){
			imgBox[ii].style.display = vs;
		}
		
		// 设置输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length == 0 ){
			// cell或link
			fieldBox = document.getElementsByName( 'span_' + fieldName );
		}
		
		for( ii=0; ii<fieldBox.length; ii++ ){
			fieldBox[ii].style.display = vs;
		}
	}
}

// 设置域是否可见
function setFormFieldVisible( fieldName, index, visibleFlag )
{
	if( visibleFlag == null ){
		visibleFlag = index;
		index = 0;
	}
	
	// 样式
	var	vs;
	if( visibleFlag == true || visibleFlag == 'true' ){
		vs = '';
	}
	else{
		vs = 'none';
	}
	
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		selField.setVisible( index, visibleFlag );
	}
	else{
		// 设置标题的状态
		var	labelBox = document.getElementsByName( 'label:' + fieldName );
		if( labelBox.length > index ){
			labelBox[index].style.display = vs;
		}
		
		// 设置图形的状态
		var	imgBox = document.getElementsByName( 'img:' + fieldName );
		if( imgBox.length > index ){
			imgBox[index].style.display = vs;
		}
		
		// 设置输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length == 0 ){
			// cell或link
			fieldBox = document.getElementsByName( 'span_' + fieldName );
		}
		
		if( fieldBox.length > index ){
			fieldBox[index].style.display = vs;
		}
	}
}

// 判断域是否看见 : 兼容，请使用 isFormFieldVisible
function isFieldVisible( frameName, fieldName, index )
{
	// 取域的名称
	var	fname;
	if( frameName == null || frameName == '' ){
		fname = fieldName;
	}
	else{
		fname = frameName + ':' + fieldName;
	}
	
	return isFormFieldVisible( fname, index );
}

// 判断域是否看见
function isFormFieldVisible( fieldName, index )
{
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		return selField.getVisible( index );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		// 取输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			return fz_isFieldVisible( fieldBox[index] );
		}
		else{
			return false;
		}
	}
}



// 判断域是否看见
function fz_isFieldVisible( obj )
{
	// <input type='hidden'>
	if( obj.tagName.toLowerCase() == 'input' && obj.type.toLowerCase() == 'hidden' ){
		return false;
	}
	
	while( obj ){
		if( obj.style.display == 'none' ){
			if( obj.tagName != null && obj.tagName.toLowerCase() != 'div' ){
				return false;
			}
		}
		
		obj = obj.parentElement;
	}
	
	return true;
}



// 标题，域的名称
function getFormFieldCaption( fieldName, index )
{
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		return selField.getCaption( index );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		// 取输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length <= index ){
			return '';
		}
		
		var obj = fieldBox[index];
		var caption = obj.fieldCaption;
		
		// 判断是否空
		if( caption == null ){
			var labelName = "label:" + fieldName;
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
			
			obj.fieldCaption = caption;
		}
		
		return caption;
	}
}

function setFormFieldCaption( fieldName, index, caption )
{
	if( caption == null ){
		caption = index;
		index = 0;
	}
	
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		selField.setCaption( index, caption );
	}
	else{
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			var obj = fieldBox[index];
			if( obj.type == 'button' ){
				obj.style.width = '';
				obj.value = caption;
				if( obj.clientWidth < 64 ) obj.style.width = '64px';
			}
			else{
				// 显示标题
				obj.fieldCaption = caption;
				_setFieldCaption( fieldName, index, obj );
			}
		}
	}
}


// 域的校验规则
function getFormFieldValidator( fieldName, index )
{
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		return selField.getValidator( index );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		// 取输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			return fieldBox[index].validator;
		}
		else{
			return '';
		}
	}
}

function setFormFieldValidator( fieldName, index, rule )
{
	if( rule == null ){
		rule = index;
		index = 0;
	}
	
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		selField.setValidator( index, rule );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		// 设置输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			fieldBox[index].validator = rule;
		}
		else{
			return;
		}
	}
}

// 是否能空
function isFormFieldNotnull( fieldName, index )
{
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		return selField.getNotnull( index );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		// 取输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			if( fieldBox[index].notnull == 'true' ){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
}

function setFormFieldNotnull( fieldName, index, flag )
{
	if( flag == null ){
		flag = index;
		index = 0;
	}
	
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		selField.setNotnull( index, flag );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		if( flag != false && flag != 'false' ){
			flag = 'true';
		}
		else{
			flag = 'false';
		}
		
		// 设置输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			fieldBox[index].notnull = flag;
			
			// 显示标题
			_setFieldCaption( fieldName, index, fieldBox[index] );
		}
		else{
			return;
		}
	}
}


// 校验标志:只对select类型的域有效
function isFormFieldCheckFlag( fieldName, index )
{
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		return selField.getCheckFlag( index );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		// 取输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			if( fieldBox[index].checkFlag == 'true' ){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
}

function setFormFieldCheckFlag( fieldName, index, flag )
{
	if( flag == null ){
		flag = index;
		index = 0;
	}
	
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		selField.setCheckFlag( index, flag );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		if( flag != false && flag != 'false' ){
			flag = 'true';
		}
		
		// 设置输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			fieldBox[index].checkFlag = flag;
		}
		else{
			return;
		}
	}
}


// 只读标志
function isFormFieldReadonly( fieldName, index )
{
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		return selField.getReadonly( index );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		// 取输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			return fieldBox[index].readOnly;
		}
		else{
			return true;
		}
	}
}

function setFormFieldReadonly( fieldName, index, flag )
{
	if( flag == null ){
		flag = index;
		index = 0;
	}
	
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		selField.setReadonly( index, flag );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		if( flag != false && flag != 'false' ){
			flag = true;
		}
		
		// 设置输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			fieldBox[index].readOnly = flag;
			
			// 显示标题
			_setFieldCaption( fieldName, index, fieldBox[index] );
		}
		else{
			return;
		}
	}
}

// 有效标志
function isFormFieldDisabled( fieldName, index )
{
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		return selField.getDisabled( index );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		// 取输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length <= index ){
			return true;
		}
		
		var flag = fieldBox[index].disabled;
		if( flag != true ){
			flag = false;
		}
		
		return flag;
	}
}

function setFormFieldDisabled( fieldName, index, flag )
{
	if( flag == null ){
		flag = index;
		index = 0;
	}
	
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		selField.setDisabled( index, flag );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		if( flag != false && flag != 'false' ){
			flag = true;
		}
		
		// 设置输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length <= index ){
			return;
		}
		
		var obj = fieldBox[index];
		
		// 判断是否按钮
		if( isButton(obj) ){
			obj._disabled = flag;
			_fz_setButtonStatus_1( obj, flag );
		}
		else{
			// 设置状态
			obj.disabled = flag;
			
			// 显示标题
			_setFieldCaption( fieldName, index, obj );
		}
	}
}

// 域的提示信息
function getFormFieldHint( fieldName, index )
{
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		return selField.getHint( index );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		// 取输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			var obj = fieldBox[index];
			var hint = obj.hint;
			
			// 格式信息
			var formatHint = _getDataFormatHint( obj );
			if( formatHint != null ){
				if( hint == null ){
					hint = formatHint;
				}
				else{
					hint += '。' + formatHint;
				}
			}
			
			return hint;
		}
		else{
			return null;
		}
	}
}

function setFormFieldHint( fieldName, index, hint )
{
	if( hint == null ){
		hint = index;
		index = 0;
	}
	
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		selField.setHint( index, hint );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		// 设置输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			fieldBox[index].hint = hint;
		}
		else{
			return;
		}
	}
}


// 域的缺省值
function getFormFieldDefaultValue( fieldName, index )
{
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		return selField.getDefaultValue( index );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		// 取输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			return fieldBox[index]._default;
		}
		else{
			return null;
		}
	}
}

function setFormFieldDefaultValue( fieldName, index, defaultValue )
{
	if( defaultValue == null ){
		defaultValue = index;
		index = 0;
	}
	
	// 选择域单独处理
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField != null ){
		selField.setDefaultValue( index, defaultValue );
	}
	else{
		if( index == null ){
			index = 0;
		}
		
		// 设置输入域的状态
		var	fieldBox = document.getElementsByName( fieldName );
		if( fieldBox.length > index ){
			fieldBox[index]._default = defaultValue;
		}
		else{
			return;
		}
	}
}

// 取输入域的INDEX
function getFormFieldIndex( obj )
{
	if( obj.name != null && obj.name != '' ){
		var objs = document.getElementsByName( obj.name );
		if( objs.length > 1 ){
			var	fieldIndex = 0;
			for( fieldIndex=0; fieldIndex<objs.length; fieldIndex++ ){
				if( objs[fieldIndex] == obj ){
					return fieldIndex;
				}
			}
		}
	}
	
	return 0;
}




// 设置焦点
function setFormFieldFocus( fieldName, index )
{
	if( index == null ) index = 0;
	var	objs = document.getElementsByName( fieldName );
	if( objs.length <= index ) return;
	try{ objs[index].focus(); } catch(e){}
}



/* ************** 取值函数 *********************** */


// 内部函数，取字段的值(支持select、radio、checkbox等)
function form_getFieldValue( fields, index )
{
	if( fields.length <= index ){
		return "";
	}
	
	var obj = fields[index];
	var tagName = obj.tagName.toLowerCase();
	
	// 取值
	var	value = "";
	
	if( tagName == 'select' ){
		for (i=0; i<obj.options.length; i++) {
			if( obj.options[i].selected == true ){
				value = obj.options[i].value;
				break;
			}
		}
	}
	else if( tagName == 'input' ){
		var ftype = obj.type;
		if( ftype == 'radio' ){
			for( var i=0; i<fields.length; i++ ){
				if( fields[i].checked == true ){
					value = fields[i].value;
					break;
				}
			}
		}
		else if( ftype == 'checkbox' ){
			if( fields[i].checked == true ){
				if( value == '' ){
					value = fields[i].value;
				}
				else{
					value = value + ',' + fields[i].value;
				}
			}
		}
		else{
			value = obj.value;
		}
	}
	else if( tagName == 'div' || tagName == 'span' || tagName == 'p' ){
		value = obj.innerText;
	}
	else{
		value = obj.value;
	}
	
	return value;
}

// 内部函数：取普通域的内容
function form_getCommonFieldValue( fieldName, index )
{
	var	value = "";
	
	var	objs = document.getElementsByName( fieldName );
	if( objs.length > 0 ){
		value = form_getFieldValue( objs, index );
	}
	else{
		// 判断是否是grid的数据项
		var	grid = null;
		var ptr = fieldName.indexOf( ':' );
		if( ptr > 0 ){
			var	gridName = fieldName.substring(0, ptr);
			var	cellName = fieldName.substring( ptr+1 );
			grid = getGridDefine(gridName);
		}
		
		if( grid != null ){
			value = grid.getCellValue( parseInt(index)+1, cellName );
		}
		else{
			// 判断是否是block的cell域
			var cellBox = document.getElementsByName( "span_" + fieldName );
			if( cellBox != null && cellBox.length > index ){
				value = cellBox[index].innerText;
			}
		}
	}
	
	return value;
}

// 内部函数：取普通域的所有值
function form_getFieldAllValues( fieldName )
{
	var	value = new Array();
	
	var	field = getSelectInfoByFieldName( fieldName );
	if( field != null ){
		fieldName = field.getAttrName();
		var	objs = document.getElementsByName( fieldName );
		for( var ii=0; ii<objs.length; ii++ ){
			value[ii] = field.getValue( ii );
		}
	}
	else{
		var	objs = document.getElementsByName( fieldName );
		if( objs.length > 0 ){
			for( var ii=0; ii<objs.length; ii++ ){
				value[ii] = form_getFieldValue( objs, ii );
			}
		}
		else{
			// 判断是否是block的cell域
			var cellBox = document.getElementsByName( "span_" + fieldName );
			if( cellBox != null && cellBox.length > 0 ){
				for( var ii=0; ii<cellBox.length; ii++ ){
					value[ii] = cellBox[ii].innerText;
				}
			}
		}
	}
	
	return value;
}

// 内部函数，设置普通域的内容
function form_setFieldValue( fields, index, value )
{
	if( fields.length <= index ){
		return;
	}
	
	var obj = fields[index];
	var tagName = obj.tagName.toLowerCase();
	
	// 设置内容
	if( tagName == 'select' ){
		obj.options[0].selected = true;
		
		// 判断是否选中
		for (var i=0; i<obj.options.length; i++) {
			var v = obj.options[i].value;
			if( v == '' ){
				v = obj.options[i].text;
			}
			
			if( v == value ){
				obj.options[i].selected = true;
				break;
			}
		}
	}
	else if( tagName == 'input' ){
		var ftype = obj.type;
		if( ftype == 'radio' ){
			for( var i=0; i<fields.length; i++ ){
				if( fields[i].value == value ){
					fields[i].checked = true;
					break;
				}
			}
		}
		else if( ftype == 'checkbox' ){
			// 清空
			for( var i=0; i<fields.length; i++ ){
				fields[i].checked = false;
			}
			
			// 赋值
			if( value != null ){
				var	list = value.split( ',' );
				for( var i=0; i<list.length; i++ ){
					list[i] = trimSpace( list[i], 0 );
				}
				
				for( var i=0; i<fields.length; i++ ){
					for( var j=0; j<list.length; j++ ){
						if( list[j] == obj[i].value ){
							obj[i].checked = true;
							break;
						}
					}
				}
			}
		}
		else{
			obj.value = value;
		}
	}
	else if( tagName == 'div' || tagName == 'span' || tagName == 'p' ){
		obj.innerText = value;
	}
	else{
		obj.value = value;
	}
}


// 内部函数，设置字段的值
// 然后修改GRID
// 再修改普通域
// 再修改BLOCK的cell域或link域
function form_setCommonFieldValue( fieldName, index, value )
{
	// 判断是否是grid的数据项
	var	grid = null
	var ptr = fieldName.indexOf( ':' );
	if( ptr > 0 ){
		var	gridName = fieldName.substring(0, ptr);
		var	cellName = fieldName.substring( ptr+1 );
		grid = getGridDefine(gridName);
	}
	
	if( grid != null ){
		grid.setCellValue( parseInt(index)+1, cellName, value );
	}
	else{
		var	obj = document.getElementsByName( fieldName );
		if( obj.length > 0 ){
			form_setFieldValue( obj, index, value );
		}
		else{
			// 判断是否是block的cell域
			var cellBox = document.getElementsByName( "span_" + fieldName );
			if( cellBox != null && cellBox.length > index ){
				cellBox[index].innerText = value;
			}
		}
	}
}

// 获取域的名称 和 内容
function getFormFieldValue( fieldName, index )
{
	if( index == null || index == '' ){
		index = 0;
	}
	
	var	field = getSelectInfoByFieldName( fieldName );
	if( field != null ){
		// 自定义的SELECT域
		var value = field.getValue( index );
	}
	else{
		var value = form_getCommonFieldValue( fieldName, index );
	}
	
	return value;
}

// 取域的TEXT
function getFormFieldText( fieldName, index )
{
	if( index == null || index == '' ){
		index = 0;
	}
	
	var	field = getSelectInfoByFieldName( fieldName );
	if( field != null ){
		// 自定义的SELECT域
		var text = field.getText( index );
	}
	else{
		var text = form_getCommonFieldValue( fieldName, index );
	}
	
	return text;
}


// 设置域的名称 和 内容
// 先修改自定义的select域，这些域不应该成为grid的键字
// 然后修改GRID
// 再修改普通域
// 再修改BLOCK的cell域或link域
function setFormFieldValue( fieldName, index, value )
{
	if( index == null || index == '' ){
		index = 0;
	}
	else if( value == null ){
		value = index;
		index = 0;
	}
	
	// 获取域的定义信息
	var	field = getSelectInfoByFieldName( fieldName );
	if( field != null ){
		field.setValue( index, value );
	}
	else{
		form_setCommonFieldValue( fieldName, index, value );
	}
}

function setFormFieldText( fieldName, index, text )
{
	if( index == null || index == '' ){
		index = 0;
	}
	else if( text == null ){
		text = index;
		index = 0;
	}
	
	// 获取域的定义信息
	var	field = getSelectInfoByFieldName( fieldName );
	if( field != null ){
		field.setText( index, text );
	}
	else{
		form_setCommonFieldValue( fieldName, index, text );
	}
}


// 取域的列表
function getFormAllFieldValues( fieldName )
{
	var	value = new Array();
	
	// 判断是否是grid的数据项
	var	grid = null
	var ptr = fieldName.indexOf( ':' );
	if( ptr > 0 ){
		var	gridName = fieldName.substring(0, ptr);
		grid = getGridDefine(gridName);
	}
	
	if( grid != null ){
		fieldName = fieldName.substring(ptr+1);
		value = grid.getAllFieldValues( fieldName );
	}
	else{
		value = form_getFieldAllValues( fieldName );
	}
	
	return value;
}


// 取域的已经选中的列表
function getFormFieldValues( fieldName )
{
	var	value = new Array();
	
	// 判断是否是grid的数据项
	var	grid = null
	var ptr = fieldName.indexOf( ':' );
	if( ptr > 0 ){
		var	gridName = fieldName.substring(0, ptr);
		grid = getGridDefine(gridName);
	}
	
	if( grid != null ){
		fieldName = fieldName.substring(ptr+1);
		value = grid.getFieldValues( fieldName );
	}
	else{
		value = form_getFieldAllValues( fieldName );
	}
	
	return value;
}




/* *********************** 初始化的数据 ************************** */

// 取初始化页面时的域内容
function __getFieldInitValue( obj )
{
	var value = obj._value;
	if( value == null || value == '' ){
		value = obj._default;
		if( value == null ){
			value = '';
		}
	}
	
	return value;
}




//-->

