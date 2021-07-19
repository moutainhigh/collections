<!--

// ѡ�������б�
var	_selectFieldList = new Array();

// ѡ����ĸ�ʽ��value��name��validFlag��sortName��description
// ������ͣ�select��browsebox��checkbox��radio

// ����select�����Ϣ
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
	
	// ���滻���¼�:radio/checlbox��ֻ��ʱ��Ҫ�滻����¼������ﱣ��ԭ�ȵ��¼�
	this.onclick = null;
	this.onmousedown = null;
	this.restoreValue = null
	
	// ������Ϣ
	this.innerHTML = null;
	this.align = 'horizontal';
	
	// �Ƿ��Ѿ���ʼ��
	this.initFlag = false;
	
	// �����Ҫ�ӷ�������̬�������ݣ����ﱣ��������һ���������ݵĲ���
	this.loader_parameter = optionName;
	
	// ȡ��������������ƺͶ���
	this.getAttrName = _select_getAttrName;
	this.getAttrObject = _select_getAttrObject;
	
	// ���⣬�������
	this.getCaption = _select_getCaption;
	this.setCaption = _select_setCaption;
	
	// ���У�����
	this.getValidator = _select_getValidator;
	this.setValidator = _select_setValidator;
	
	// �Ƿ��ܿ�
	this.getNotnull = _select_getNotnull;
	this.setNotnull = _select_setNotnull;
	
	// У���־
	this.getCheckFlag = _select_getCheckFlag;
	this.setCheckFlag = _select_setCheckFlag;
	
	// ֻ����־
	this.getReadonly = _select_getReadonly;
	this.setReadonly = _select_setReadonly;
	
	// ��Ч��־
	this.getDisabled = _select_getDisabled;
	this.setDisabled = _select_setDisabled;
	
	// �ɼ���־
	this.getVisible = _select_getVisible;
	this.setVisible = _select_setVisible;
	
	// ȱʡֵ
	this.getDefaultValue = _select_getDefaultValue;
	this.setDefaultValue = _select_setDefaultValue;
	
	// ��ʾ��Ϣ
	this.getHint = _select_getHint;
	this.setHint = _select_setHint;
	
	// ��ʼ����ֵ
	this.getInitValue = _select_getInitValue;
	
	// ȡֵ�͸�ֵ����
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
	
	// ���������б�
	this.setSelectList = _select_setSelectList;
	
	// ��������
	this.prepareHiddenField = _select_prepareHiddenField;	// ����hidden��
	this.getFullName = _select_getFullName;					// ȡ����������ƣ�(����ǰ׺)
	this.getSelectShowBoxName = fz_getSelectShowBoxName;	// ȡ��ʾ�������
	this.getCodeType = fz_getCodeType;						// ȡֵ�����ͣ�code=��ʾ���롢name=��ʾ���ơ�mix=��ϴ�������ƣ�ȱʡ�ɼ�����
	this.getShowType = fz_getShowType;						// ��ʾ�����ͣ�code=��ʾ���롢name=��ʾ���ơ�mix=��ϴ�������ƣ�ȱʡ��ʾ����
	this.transSelectValue = _fz_transSelectValue;			// ת��ֵ����code��name��mix֮��
	this.setCodeBoxValue = fz_setCodeBoxValue;				// ���ô������ֵ
	this.setNameBoxValue = fz_setNameBoxValue;				// �����ı����ֵ
	this.setMixBoxValue = fz_setMixBoxValue;				// ����mix���ֵ
	this.setHiddenFieldValue = fz_setHiddenFieldValue;		// �����������ֵ
	
	// У�麯��
	this.valid = _valid_checkSelectField;
	this.checkOneSelectField = _valid_checkOneSelectField;
}

function addSelectInputField( field )
{
	var	num = _selectFieldList.length;
	_selectFieldList[num] = field;
}

// �������ƻ�ȡѡ������Ϣ
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


// ������ʾ������ƻ�ȡ�ֶ�����
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


// ������ǰ������ǰ׺�������������
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

// ��ȡ�������Ե������
function _select_getAttrName()
{
	// ȡ����У����Ϣ�Ķ���:radio��check������table��
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
	
	// �������ԵĶ�������
	var fieldName = this.getAttrName();
	
	var fields = document.getElementsByName( fieldName );
	if( fields.length <= index ){
		return null;
	}
	
	return fields[index];
}


// ȡ��ı���
function _select_getCaption(index)
{
	// ȡ�������ԵĶ���
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return "";
	}
	
	var caption = attrObject.fieldCaption;
	
	// �ж��Ƿ����
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
		// ���ñ������Ϣ
		caption = labels[index].innerHTML;
		if( caption.lastIndexOf('��') == caption.length - 1 ){
			caption = caption.substring( 0, caption.length-1 );
		}
		attrObject.fieldCaption = caption;
	}
	
	return caption;
}

// ���ñ���
function _select_setCaption(index, caption)
{
	// ȡ�������ԵĶ���
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return;
	}
	// ���ñ���
	attrObject.fieldCaption = caption;
	
	// ��ʾ����
	var fieldName = this.getFullName(this.fieldName);
	_setFieldCaption( fieldName, index );
}

// ȡ���У�����
function _select_getValidator(index)
{
	// ȡ�������ԵĶ���
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return "";
	}
	
	return attrObject.validator;
}

// ����У�����
function _select_setValidator(index, rule)
{
	// ȡ�������ԵĶ���
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return;
	}
	
	attrObject.validator = rule;
}

// �ж����Ƿ��ܹ���
function _select_getNotnull(index)
{
	// ȡ�������ԵĶ���
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

// �����Ƿ��ܿձ�־
function _select_setNotnull(index, flag)
{
	if( flag != false && flag != 'false' ){
		flag = 'true';
	}
	
	// ȡ�������ԵĶ���
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return;
	}
	
	attrObject.notnull = flag;
	
	// ��ʾ����
	var fieldName = this.getFullName(this.fieldName);
	_setFieldCaption( fieldName, index );
}

// ȡ���Ƿ���ҪУ��ı�־
function _select_getCheckFlag(index)
{
	// ȡ�������ԵĶ���
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

// �������Ƿ���ҪУ��ı�־
function _select_setCheckFlag(index, flag)
{
	if( flag != false && flag != 'false' ){
		flag = 'true';
	}
	
	// ȡ�������ԵĶ���
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return;
	}
	
	attrObject.checkFlag = flag;
	
	// �������
	if( this.selectType == 'browsebox' ){
		// ����������ʱ�������ѡ���
		var	showBox = this.getSelectShowBoxName( );
		var objs = document.getElementsByName(showBox);
		if( objs.length <= index ){
			return;
		}
		
		var obj = objs[index];
		
		// �����¼�
		if( flag == 'true' ){
			// ������ԣ����ʱ��ѡ���
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

// ȡ���Ƿ�ֻ����־
function _select_getReadonly(index)
{
	if( index == null ){
		index = 0;
	}
	
	// ����select��ֻ��ʱ��ѡ�������أ�ֻ���ж���ʾ���ݵ���
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	if( objs.length <= index ){
		return false;
	}
	
	// select���⴦��
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

// �������Ƿ�ֻ����־
function _select_setReadonly(index, flag)
{
	if( index == null ){
		index = 0;
	}
	
	if( flag != false && flag != 'false' ){
		flag = true;
	}
	
	// ���������
	var fieldName = this.getFullName(this.fieldName);
	
	// ����select��ֻ��ʱ��ѡ�������أ�ֻ���ж���ʾ���ݵ���
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	
	// select��radio��checkbox���⴦��
	if( this.selectType == 'select' ){
		if( objs.length <= index ){
			return;
		}
		
		// ����ֻ����־
		objs[index].readOnly = flag;
		
		// ����/��ʾ��
		if( flag ){
			objs[index].style.display = 'none';
			
			var hobjs = document.getElementsByName( "hidden:" + fieldName );
			if( hobjs.length > index ){
				hobjs[index].style.display = '';
			}
			
			// ������ʾ������
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
			
			// �¼�����
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
			
			// �¼�����
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
		
		// ����ֻ����־
		objs[index].readOnly = flag;
		
		// ����ͼ���Ƿ���ʾ
		var imgName = 'img:' + showName;
		var imgBox = document.getElementsByName(imgName)[index];
		
		// �Ƿ�����
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
		
		// ����ֻ����־
		objs[index].readOnly = flag;
	}
	
	// ��ʾ����
	_setFieldCaption( fieldName, index );
}

// ȡ���Ƿ��ֹ��־
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

// �����Ƿ��ֹ��־
function _select_setDisabled(index, flag)
{
	if( index == null ){
		index = 0;
	}
	
	if( flag != false && flag != 'false' ){
		flag = true;
	}
	
	// ȡ��ʾ��
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	if( objs.length == 0 ){
		return;
	}
	
	// �������״̬��radio��checkbox���������е����ֹ״̬
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
		
		// ����ͼ���Ƿ���ʾ
		var imgName = 'img:' + showName;
		var imgBox = document.getElementsByName(imgName)[index];
		
		// �Ƿ�����
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
	
	// ��ʾ����
	var fieldName = this.getFullName(this.fieldName);
	_setFieldCaption( fieldName, index );
}


// ȡ���Ƿ�ɼ���־
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

// �������Ƿ�ɼ���־
function _select_setVisible(index, flag)
{
	// ��ʽ
	var	vs;
	if( flag == true || flag == 'true' ){
		vs = '';
	}
	else{
		vs = 'none';
	}
	
	// �������
	fieldName = this.getFullName(this.fieldName);
	
	// ���ñ����״̬
	var	labelBox = document.getElementsByName( 'label:' + fieldName );
	if( labelBox != null ){
		if( labelBox.length > index ){
			labelBox[index].style.display = vs;
		}
	}
	
	// ȡ��ʾ����
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	
	// ������radio��checkbox���������е�������
	if( this.selectType == 'radio' ){
		for( var ii=0; ii<objs.length; ii++ ){
			objs[ii].style.display = vs;
		}
		
		// ���ر���
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
		
		// ���ر���
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
				// ������
				objs[index].style.display = vs;
				
				// ����ֻ����
				var hobjs = document.getElementsByName( "hidden:" + fieldName );
				if( hobjs.length > index ){
					hobjs[index].style.display = vs;
				}
			}
			else{
				// ��ʾ
				if( objs[index].readOnly == 'true' ){
					// ��ʾֻ����
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
		// ������
		if( objs.length > index ){
			objs[index].style.display = vs;
			
			// ����ͼ��
			var	imgBoxs = document.getElementsByName( 'img:' + showName );
			if( imgBoxs.length > index ){
				imgBoxs[index].style.display = vs;
			}
		}
	}
}


// ȡ���ȱʡֵ
function _select_getDefaultValue(index)
{
	// ȡ�������ԵĶ���
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return null;
	}
	
	return attrObject._default;
}

// ����ȱʡֵ
function _select_setDefaultValue(index, defaultValue)
{
	// ȡ�������ԵĶ���
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return;
	}
	
	// ���ñ���
	attrObject._default = defaultValue;
}


// ȡ�����ʾ��Ϣ
function _select_getHint(index)
{
	// ȡ�������ԵĶ���
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return null;
	}
	
	return attrObject.hint;
}

// ������ʾ��Ϣ
function _select_setHint(index, hint)
{
	// ȡ�������ԵĶ���
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return;
	}
	
	// ���ñ���
	attrObject.hint = hint;
}


// ȡ��ʼ����ֵ
function _select_getInitValue( index )
{
	// ȡ�������ԵĶ���
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return null;
	}
	
	return attrObject._value;
}


/* ************************ ȡֵ�����ú�����ʼ ************************ */

// ȡԭʼ��ֵ
function _select_getOriginalValue( index )
{
	// ȡ�������ԵĶ���
	var attrObject = this.getAttrObject(index);
	if( attrObject == null ){
		return null;
	}
	
	return __getFieldInitValue( attrObject );
}

// ȡ����
function _select_getBrowseValue( index )
{
	var	value = "";
	
	// û�ж��ձ�
	if( this.selectList == null || this.selectList.length == 0 ){
		var objName = this.getFullName( this.fieldName );
		var	obj = document.getElementsByName( objName );
		if( index < obj.length ){
			value = obj[index].value;
		}
		
		return value;
	}
	
	// ��Ҫת��
	var objName = this.getSelectShowBoxName();
	var	obj = document.getElementsByName( objName );
	if( index < obj.length ){
		value = obj[index].value;
	}
	
	// ת������
	var	codeType = this.getCodeType();
	var	showType = this.getShowType();
	return this.transSelectValue( value, index, showType, codeType );
}


// ȡ����
function _select_getBrowseText( index )
{
	var objName = this.getSelectShowBoxName();
	
	// ȡ��ʾ��
	var	value = "";
	var	obj = document.getElementsByName( objName );
	if( index < obj.length ){
		value = obj[index].value;
	}
	
	var	showType = this.getShowType();
	return this.transSelectValue( value, index, showType, "name" );
}

// ��������
function _select_setBrowseValue( index, value )
{
	if( value == null ){ value = index; index = 0; }
	
	// ת��������
	var	codeType = this.getCodeType();
	var	showType = this.getShowType();
	value = this.transSelectValue( value, index, codeType, showType );
	
	var objName = this.getSelectShowBoxName();
	var	obj = document.getElementsByName( objName );
	if( index >= obj.length ){
		return;
	}
	
	// ȡ��ʾ��ֵ
	obj[index].value = value;
	
	// ����������
	this.setHiddenFieldValue( index, value );
}

// ���ñ���
function _select_setBrowseText( index, text )
{
	if( text == null ){ text = index; index = 0; }
	
	// ��������
	var objName = this.getSelectShowBoxName();
	var	obj = document.getElementsByName( objName );
	if( index >= obj.length ){
		return;
	}
	
	var	showType = this.getShowType();
	text = this.transSelectValue( value, index, "name", showType );
	
	// ȡ��ʾ��ֵ
	obj[index].value = text;
	
	// ����������
	this.setHiddenFieldValue( index, text );
}



// ȡselect�������
function _select_getSelectedValue( index )
{
	var objName = this.getSelectShowBoxName();
	var obj = document.getElementsByName( objName );
	if( index >= obj.length ){
		return "";
	}
	
	// ��ǰ��
	obj = obj[index];
	
	// �ж��Ƿ�ѡ��
	for (var i=0; i<obj.options.length; i++) {
		if( obj.options[i].selected == true ){
			return obj.options[i].value;
		}
	}
	
	return "";
}

// ȡselect����ı�
function _select_getSelectedText( index )
{
	var objName = this.getSelectShowBoxName();
	var obj = document.getElementsByName( objName );
	if( index >= obj.length ){
		return "";
	}
	
	// ��ǰ��
	obj = obj[index];
	
	// �ж��Ƿ�ѡ��
	for (var i=0; i<obj.options.length; i++) {
		if( obj.options[i].selected == true ){
			return obj.options[i].text;
		}
	}
	
	return "";
}

// ����select����ı�
function _select_setSelectedText( index, text )
{
	if( text == null ){ text = index; index = 0; }
	
	var objName = this.getSelectShowBoxName();
	var obj = document.getElementsByName( objName );
	if( index >= obj.length ){
		return;
	}
	
	// ��ǰ��
	obj = obj[index];
	obj.options[0].selected = true;
	
	// �ж��Ƿ�ѡ��
	for (var i=0; i<obj.options.length; i++) {
		if( obj.options[i].text == text ){
			obj.options[i].selected = true;
			break;
		}
	}
}

// ����select�������
function _select_setSelectedValue( index, value )
{
	if( value == null ){ value = index; index = 0; }
	
	var objName = this.getSelectShowBoxName();
	var obj = document.getElementsByName( objName );
	if( index >= obj.length ) return;
	
	// ��ǰ��
	obj = obj[index];
	if( obj.options.length == 0 ){
		obj._value = value;
		return;
	}
	
	// �ж��Ƿ�ѡ��
	obj.options[0].selected = true;
	for (var i=0; i<obj.options.length; i++) {
		if( obj.options[i].value == value ){
			obj.options[i].selected = true;
			break;
		}
	}
}

// ȡradio�������
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
	
	// ת������
	var codeType = this.getCodeType();
	return this.transSelectValue( value, index, codeType, this.showType );
}


// ����radio�������
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
	
	// ����ת��������
	var codeType = this.getCodeType();
	text = this.transSelectValue( text, index, this.showType, codeType );
	
	this.setValue( index, text );
}


// ���úͻ�ȡcheck������
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
	
	// ���
	for( var i=0; i<obj.length; i++ ){
		obj[i].checked = false;
	}
	
	if( value == null || value == '' ){
		return;
	}
	
	// ֵ�б�
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
	
	// ����ת��������
	var	codeType = this.getCodeType();
	text = this.transSelectValue( text, index, this.showType, codeType );
	
	this.setValue( index, text );
}


/* ************************ ȡֵ�����ú������� ************************ */


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
		// ��ǧ�޸ģ������ظ�����,�޸�����2009-1-5
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

// ���������������
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

// ȡ��ʾ�������
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

// ȱʡ�ɼ���������
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

// ȡ��ʾ������
// ������ʾ�ı����򣬶��ǽ����û�������򣬱��磺select��ʾ���ơ�������룻��ʱ�����û����������code��
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



// ���������֮��ת��
function _fz_transSelectValue( value, index, fromType, toType )
{
	// û��ѡ��
	if( this.selectList == null || this.selectList.length == 0 ){
		return value;
	}
	
	// ��ֵ
	if( value == null || value == '' ){
		return '';
	}
	
	// �ж��Ƿ��ѡ����һ��
	if( fromType == toType ){
		return value;
	}
	
	// ת������
	var	selList = value.split( ',' );
	var	selectList = this.selectList;
	
	// ��������Ƿ����б���
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
		
		// �ж��Ƿ���ѡ���б��е�����
		if( j >= selectList.length ){
			if( this.getCheckFlag(index) && this.parameter != '' ){
				alert( '���������� ��' + this.getCaption(index) + '�� ʱ������һ����Ч���� ��' + selList[i] + '��' );
			}
			
			codeText = value;
		}
		else{
			// ��������
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



/* *********************** �����������ֵ *********************** */

// ���������������
function fz_setHiddenFieldValue( index, value )
{
	if( value == null ){
		value = '';
	}
	
	// �����
	selectList = this.selectList;
	if( selectList == null || selectList.length == 0 || value == '' ){
		this.setCodeBoxValue( index, value );
		this.setNameBoxValue( index, value );
		this.setMixBoxValue( index, value );
		return true;
	}
	
	// ���ɵ�����
	var	nameText = '';
	var	codeText = '';
	var	mixText = '';
	
	value = '' + value;
	var	selList = value.split( ',' );
	
	// valueType ����ԭʼ���ݵ�����
	var	showType = this.getShowType();
	
	// ��������Ƿ����б���
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
			
			// �ж��Ƿ���ѡ���б��е�����
			if( j >= selectList.length ){
				if( this.getCheckFlag(index) ){
					alert( '�������� ��' + this.getCaption(index) + '�� ��������һ����Ч���� ��' + selList[i] + '��' );
					return false;
				}
				
				// û���ҵ�ת����Ϣ�����Ҳ������Ч��
				nameText = value;
				codeText = value;
				mixText = value;
			}
			else{
				// ��������
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
	
	// ���������������
	this.setCodeBoxValue( index, codeText );
	this.setNameBoxValue( index, nameText );
	this.setMixBoxValue( index, mixText );
	
	return true;
}

// ���ô����������
function fz_setCodeBoxValue( index, value )
{
	// ��ʾ������
	var	showBox = this.getSelectShowBoxName( );
	
	// ��������
	var codeBox = this.codeBox;
	if( codeBox != null && codeBox != '' ){
		codeBox = this.getFullName( codeBox );
		if( codeBox != showBox ){
			var	obj = document.getElementsByName(codeBox);
			obj[index].value = value;
		}
	}
}

// ���������������
function fz_setNameBoxValue( index, value )
{
	// ��ʾ������
	var	showBox = this.getSelectShowBoxName( );
	
	// ��������
	var nameBox = this.nameBox;
	if( nameBox != null && nameBox != '' ){
		nameBox = this.getFullName( nameBox );
		if( nameBox != showBox ){
			var	obj = document.getElementsByName(nameBox);
			obj[index].value = value;
		}
	}
}

// ���û���������
function fz_setMixBoxValue( index, value )
{
	// ��ʾ������
	var	showBox = this.getSelectShowBoxName( );
	
	// ��������
	var mixBox = this.mixBox;
	if( mixBox != null && mixBox != '' ){
		mixBox = this.getFullName( mixBox );
		if( mixBox != showBox ){
			var	obj = document.getElementsByName(mixBox);
			obj[index].value = value;
		}
	}
}



/* *********************** У����Ч�� *************************** */


/* ���ѡ�����������Ч��
** ���Զ����ɵĴ����е��ã���֤���ݵ���Ч��
*/
function _valid_checkSelectField( )
{
	var	index;
	var	flag = true;
	
	// ��������
	var	showBox = this.getSelectShowBoxName( );
	var	obj = document.getElementsByName(showBox)
	if( obj == null ){
		return false;
	}
	
	// ��ȡ���ݲ������Ч��
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

// ��鵥�������Ч��
function _valid_checkOneSelectField( obj, index )
{
	var flag = true;
	
	// �Զ������
	flag = _checkFieldValidatorRule( obj, index, this.getValidator(index), this.getCaption(index) )
	if( flag == false ){
		fz_setSelectFieldFocus( this, index );
		return false;
	}
	
	// �������ص�����
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
	
	// ��������
	flag = this.setHiddenFieldValue( index, value );
	if( flag == false ){
		fz_setSelectFieldFocus( this, index );
		return false;
	}
	
	return true;
}



// ���ý���
function fz_setSelectFieldFocus( field, index )
{
	var	showBox = field.getSelectShowBoxName( );
	
	// ��ȡ����
	obj = document.getElementsByName(showBox)
	if( obj != null && obj.length > index ){
		obj[index].focus();
	}
}


// ���select����Ч�ԣ��������ݣ�����ǿգ�˵�����û��ͨ��
function fz_checkSelectFieldValid( field, index )
{
	var	val = field.getValue( index );
	
	// �ж��Ƿ��
	if( field.getNotnull(index) == true && val == '' ){
		alert( '������ ��' + field.getCaption(index) +'�� ����ѡ��һ������' );
		val = null;
	}
	
	// ȡ����
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
	
	// �ж��Ƿ��
	if( field.getNotnull(index) == true && val == '' ){
		alert( '������ ��' + field.getCaption(index) +'�� ����ѡ��һ������' );
		val = null;
	}
	
	return val;
}

function fz_checkRadioFieldValid( field, index )
{
	var	val = field.getValue( );
	
	// �ж��Ƿ��
	if( val == null ){
		if( field.getNotnull(index) == true ){
			alert( '������ ��' + field.getCaption(index) +'�� ����ѡ��һ������' );
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
	
	// �ж��Ƿ��
	if( field.getNotnull(index) == true && val == '' ){
		alert( '������ ��' + field.getCaption(index) + '�� ����ѡ��һ������' );
		val = null;
	}
	
	return val;
}



/* ********************** ��ʼ�� ************************ */
function _select_initField( fieldName, index )
{
	// ���Ҷ���
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
	
	// �����Ѿ���ʼ����־
	obj._inited = true;
	
	field.initField( fieldName, index );
}


// ��ʼ��browse����ʽ����Ҫ�Ƕ�����ҪУ����Ч�Ե��򣬲������ֹ�����
function _select_initBrowseFieldOptions( fieldName, index )
{
	// ȡ��Ķ���
	var	showBox = this.getSelectShowBoxName( );
	var objs = document.getElementsByName(showBox);
	if( objs.length <= index ){
		return;
	}
	
	var obj = objs[index];
	
	// ����data�������
	var dataType = this.getCodeType();
	if( this.showType != dataType ){
		var hobj = document.createElement( "<input type='hidden' name='" + fieldName + "'>" );
		hobj.id = fieldName;
		_browse.appendElement( obj.parentNode, hobj );
		hobj = null;
	}
	
	// ����hidden��
	this.prepareHiddenField( obj.parentNode, index );
	
	// ��ʽ
	obj.style.width = '100%';
	obj.id = showBox;
	
	// ����TABLE����ʽ
	var tableObj = findAncestorWithName( obj, "TABLE" );
	if( tableObj != null ){
		tableObj.border = 0;
		tableObj.cellSpacing = 0;
		tableObj.cellPadding = 0;
		tableObj = null;
	}
	
	// td�Ŀ��
	obj.parentNode.width = '99%';
	
	// ����ͼ�ΰ�ť
	var td = document.createElement( "TD" );
	_browse.appendElement( obj.parentNode.parentNode, td );
	td.width = '1%';
	
	var img = document.createElement( '<img name="img:' + showBox + '">' );
	_browse.appendElement( td, img );
	
	// id����ʽ
	img.id = 'img:' + showBox;
	img.border = 0;
	img.vspace = 0;
	img.hspace = 0;
	img.style.cursor = 'hand';
	
	// �Ƿ�����
	if( obj.style.display == 'none' ){
		img.style.display = 'none';
	}
	
	// ��ʾ��Ϣ
	if( obj.fieldCaption != null && obj.fieldCaption != '' ){
		img.alt = "��ѡ��:" + obj.fieldCaption;
	}
	else{
		img.alt = "��ѡ������";
	}
	
	// �¼�
	img.onclick = function(){openBrowse(fieldName, index)};
	
	// ����ͼ��
	if( _browseImage.complete ){
		img.src = _browseImage.src;
		img = null;
	}
	else{
		setTimeout( function(){img.src = _browseImage.src; img = null;}, 1 );
	}
	
	// ����������ʱ�������ѡ���
	var func = function(){document.getElementsByName('img:' + showBox)[index].fireEvent('onclick')};
	if( this.getCheckFlag() ){
		// ������ԣ����ʱ��ѡ���
		obj.style.cursor = 'hand';
		
		// onclick
		obj.onclick = func;
	}
	
	// ˫���¼�
	obj.ondblclick = func;
	
	// onkeydown:û��ѡ����Ϣ��������ҪУ��ʱ������������
	var field = this;
	obj.onkeydown = function(){ _browse_onkeydown(field) };
	
	// ��ʼ����ֵ
	var value = __getFieldInitValue( obj );
	
	// ׼������browse�Ĳ���
	if( document.readyState == 'complete' ){
		if( value != null && value != '' ){
			setFormFieldValue( fieldName, index, value );
		}
		
		// �޸�ȡֵ����
		this.getValue = _select_getBrowseValue;
	}
	else{
		_addBrowseInitInfo( fieldName, index, value );
	}
	
	//alert( obj.parentNode.parentNode.innerHTML );
	td = null;
	obj = null;
}

// ��ʼ��browse��ѡ������ݣ���Զ�����������Ч
function fz_prepareBrowseFieldOptions( field, index )
{
	var fieldName = field.getFullName();
	
	// ȡֵ
	var fieldValue = getFormFieldValue( fieldName, index );
	if( fieldValue == null || fieldValue == '' ){
		return;
	}
	
	// ȡ���������ز���
	if( field.parameter != null && field.parameter != '' ){
		// �ж��Ƿ���Ҫ������������:�����������仯 ���� ���ز����仯 ����Ҫ��������
		var optName = field.optionName;
		
		// ȡ����
		var parameter = eval( field.parameter );
		
		// ���뼯һ������Ҫ�ж����ش���ʹ�õĲ������ܿ�
		if( optName == field.optionName ){
			if( parameter == null ){
				return;
			}
			
			// �жϲ����Ƿ�Ϊ��
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
		
		// ��ʽ������
		if( parameter != null ){
			parameter = parameter.replace( /[=]/g, '%3D' );
			parameter = parameter.replace( /[&]/g, '%26' );
	 		parameter = parameter.replace( /[#]/g, '%23' );
		}
		else{
			parameter = '';
		}
		
		// �����ύ����
		return "option-name=" + field.optionName + "&parameter=" + parameter + "&fieldName=" + fieldName + "&index=" + index;
	}
}



// ��ʼ��radio �� check
function _select_initRadioFieldOptions( fieldName, index )
{
	// �޸�ȡֵ����
	this.getValue = _select_getRadioValue;
	
	// ȡ��Ķ���
	var objs = document.getElementsByName( 'radio:' + fieldName );
	if( objs.length <= index ){
		return;
	}
	
	// ������ʽ
	var tableObj = objs[index];
	tableObj.border = 0;
	tableObj.cellSpacing = 0;
	tableObj.cellPadding = 0;
	
	// ��ʼ����ֵ
	var value = __getFieldInitValue( tableObj );
	
	// ����hidden��
	this.prepareHiddenField( tableObj.parentNode, index );
	
	// ��ʼ��ѡ����
	fz_initRadioAndCheckFieldOptions( objs[index], fieldName, index, value );
	
	// �����¼�
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	if( objs.length > 0 ){
		this.onclick = objs[0].onclick;
		this.onmousedown = objs[0].onmousedown;
	}
	
	// ����ֻ���¼�
	for( var ii=0; ii<objs.length; ii++ ){
		if( objs[ii].readOnly ){
			objs[ii].onmousedown = function(){ _fz_saveValue(fieldName); };
			objs[ii].onclick = function(){ _fz_restoreValue(fieldName); };
		}
		
		// ������ʽ
		objs[ii].style.cursor = 'hand';
	}
	
	// ���ñ������ʽ
	var caps = document.getElementsByName( '_span_' + showName );
	for( var ii=0; ii<caps.length; ii++ ){
		caps[ii].style.cursor = 'hand';
		caps[ii].style.width = '70%';
	}
}


function _select_initCheckFieldOptions( fieldName, index )
{
	// �޸�ȡֵ����
	this.getValue = _select_getCheckValue;
	
	// ȡ��Ķ���
	var objs = document.getElementsByName( 'checkbox:' + fieldName );
	if( objs.length <= index ){
		return;
	}
	
	// ��ʼ����ֵ
	var value = __getFieldInitValue( objs[index] );
	
	// ����data�������
	var hobj = document.createElement( "<input type='hidden' name='" + fieldName + "'>" );
	hobj.id = fieldName;
	
	hobj.value = value;
	_browse.appendElement( objs[index].parentNode, hobj );
	
	// ����hidden��
	this.prepareHiddenField( objs[index].parentNode, index );
	
	// ����ѡ����
	fz_initRadioAndCheckFieldOptions( objs[index], fieldName, index, value );
	
	// �����¼�
	var showName = this.getSelectShowBoxName();
	var objs = document.getElementsByName( showName );
	if( objs.length > 0 ){
		this.onclick = objs[0].onclick;
		this.onmousedown = objs[0].onmousedown;
	}
	
	// ����ֻ���¼�
	for( var ii=0; ii<objs.length; ii++ ){
		if( objs[ii].readOnly ){
			objs[ii].onmousedown = function(){ _fz_saveValue(fieldName); };
			objs[ii].onclick = function(){ _fz_restoreValue(fieldName); };
		}
		
		// ������ʽ
		objs[ii].style.cursor = 'hand';
	}
	
	// ���ñ������ʽ
	var caps = document.getElementsByName( '_span_' + showName );
	for( var ii=0; ii<caps.length; ii++ ){
		caps[ii].style.cursor = 'hand';
		caps[ii].style.width = '70%';
	}
}

// ����ͻָ���ԭ����ֵ
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

// ��ʼ��ѡ����
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
	
	// ȡ��Ķ�����Ϣ
	var selField = getSelectInfoByFieldName( fieldName );
	if( selField == null ){
		return;
	}
	
	// �������
	selField.innerHTML = cell.innerHTML;
	if( cell.width == '100%' ){
		selField.align = 'vertical';
	}
	
	// ɾ��
	row.deleteCell( 0 );
	if( selField.align == 'vertical' ){
		table.deleteRow( 0 );
	}
	
	// ѡ�е��ֶ��б������Ƕ�ѡ
	var j;
	var selectedList = new Array();
	if( value != null && value != '' ){
		value = value.replaceAll( ',', ';' );
		selectedList = value.split( ';' );
	}
	
	// �����б�
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
		
		// �Ƿ�ѡ��
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
		
		// ����
		radioHtml = fz_getCreateHtmlString( selField.innerHTML, radioNumber, dataValue, showValue, selectedFlag );
		
		// ��������
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
	
	// ���ÿ��
	if( selField.align != 'vertical' ){
		var wd = 100/radioNumber + '%';
		for( i=0; i<radioNumber; i++ ){
			row.cells(i).width = wd;
		}
	}
}

// ����RADIO��CHECK�Ĵ���
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


// ��ʼ��select��ѡ�����б�
function _select_initSelectFieldOptions( fieldName, index )
{
	// �޸�ȡֵ����
	this.getValue = _select_getSelectedValue;
	
	// ȡ��Ķ���
	var objs = document.getElementsByName( fieldName );
	if( objs == null || objs.length <= index ){
		return;
	}
	
	var obj = objs[index];
	obj.id = fieldName;
	
	// ��ʼ����ֵ
	var value = __getFieldInitValue( obj );
	
	// ����hidden��
	this.prepareHiddenField( obj.parentNode, index );
	
	// ���ӿ���
	var opt;
	if( this.getNotnull(index) == true ){
		opt = new Option( "��ѡ��...", "" );
	}
	else{
		opt = new Option( "ȫ��", "" );
	}
	
	obj.add( opt );
	
	// ѡ�е��ֶ��б������Ƕ�ѡ
	var j;
	var selectedList = new Array();
	if( value != null && value != '' ){
		value = value.replace( ',', ';' );
		selectedList = value.split( ';' );
	}
	
	// ����ѡ���б�
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
		
		// �Ƿ�ѡ��
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
		
		// ����
		opt = new Option( showValue, dataValue );
		obj.add( opt );
		
		// �Ƿ�ѡ�еļ�¼
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
	
	// �����ֻ���ģ������ı��������
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
	
	// �������Ƿ���ʾ
	if( objs[index].readOnly == 'true' ){
		objs[index].style.display = 'none';
	}
	else{
		hobj.style.display = 'none';
	}
}


// ����hidden��
function _select_prepareHiddenField( node, index )
{
	var show = this.showType;
	var data = this.getCodeType();
	
	if( this.selectType != 'browsebox' ){
		show = '';
	}
	
	// ����������������
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


/* ********************* �¼����� ***************************** */

// ����ѡ����б�������ƶ���select��ʱ������������������
function fz_filterOptionList( obj )
{
	if( obj == null || obj.name == null || obj.name == '' ){
		return;
	}
	
	// ȡ����
	var	objName = obj.name;
	
	// ȡ����
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
	
	// ȡ���
	var	index = getFormFieldIndex( obj );
	
	// ɾ��option
	var	selectedValue = null;
	for( var x=obj.options.length-1; x>0; x-- ){
		if( obj.options[x].selected ){
			selectedValue = obj.options[x].value;
		}
		
		obj.remove(x);
	}
	
	// ��������option
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



// browse:û��ѡ����Ϣ��������ҪУ��ʱ������������
function _browse_onkeydown( field )
{
	// �ж��Ƿ�У��
	if( field.getCheckFlag() ){
		// �ж��Ƿ���ѡ����
//		var list = field.selectList;
//		if( list != null && isArray(list) && list.length > 1 ){
			window.event.returnValue = false;
//		}
	}
}


//checkbox��ťʹ��
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
	
	// �����������
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


// ��������¼�
function fireCheckClickEvent( boxName, index )
{
	var	objs = document.getElementsByName( boxName );
	if( objs.length > index && objs[index].readOnly == false ){
		// ����checkbox������
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


// ����browse����
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
	
	// �Ƿ񴴽�����
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
	
	// ������
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


// �������ض��������б����
var _browseLoadOptions = new Array();

function _BrowseOption( fieldName, index, value )
{
	this.fieldName = fieldName;
	this.index = index;
	this.value = value;
}

// ����
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

// ����
function _addBrowseInitInfo( fieldName, index, value )
{
	var i = _lookupBrowseOption( fieldName, index );
	if( i < 0 ){
		var o = new _BrowseOption( fieldName, index, value );
		_browseLoadOptions.push( o );
	}
}

// ���ز���
function __initBrowseValue()
{
	var param = null;
	for( var ii=0; ii<_browseLoadOptions.length; ii++ ){
		var o = _browseLoadOptions[ii];
		
		// ȡ������Ϣ
		var field = getSelectInfoByFieldName( o.fieldName );
		if( field == null ){
			continue;
		}
		
		// �޸�ȡֵ����
		field.getValue = _select_getBrowseValue;
		
		// ��������
		if( o.value != null && o.value != '' ){
			setFormFieldValue(o.fieldName, o.index, o.value);
		}
		
		// ��������
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
	
	// �����ظ�����
	_browseLoadOptions = new Array();
	
	if( param == null ){
		return;
	}
	
	// ���ز���
	var xurl = _browse.contextPath + "/tag.service?txn-code=load-option&" + param;
	openInnerWindow( xurl, 1, 1, null, false );
}


// ���سɹ�ʱ����
function _onLoadBrowseComplete( fieldName, index, options )
{
	if( options == null || options.length == 0 ){
		return;
	}
	
	// ȡѡ������Ϣ
	var field = getSelectInfoByFieldName( fieldName );
	if( field == null ){
		return;
	}
	
	// ȡԭ��������
	var fieldValue = getFormFieldValue( fieldName, index );
	
	// ��������������
	field.setSelectList( options );
	
	// ����������
	setFormFieldValue( fieldName, index, fieldValue );
}

//-->