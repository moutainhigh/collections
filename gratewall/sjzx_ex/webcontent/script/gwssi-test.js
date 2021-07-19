<!--

// 测试
function test_fz_getFormFieldValue()
{
	var	data;
	var	str = prompt('输入测试数据：@字段=取所有记录；$字段=取选中的记录；字段,index=取指定值');
  	if( str.substring(0,1) == '$' ){
  		data = getFormFieldValues( str.substring(1) );
  		alert( '记录数量==>' + data.length );
  		for( i=0; i<data.length; i++ ){
  			alert( data[i] );
  		}
  	}
  	else if( str.substring(0,1) == '@' ){
  		data = getFormAllFieldValues( str.substring(1) );
  		alert( '记录数量==>' + data.length );
  		for( i=0; i<data.length; i++ ){
  			alert( data[i] );
  		}
  	}
  	else if( str.substring(0,1) == '!' ){
  		ptr = str.indexOf(',');
  		index = str.substring(ptr+1);
  		str = str.substring(1,ptr);
  		data = getFormFieldText( str, index );
  		alert( data );
  	}
  	else if( str.substring(0,1) == '*' ){
  		ptr = str.indexOf(',');
  		ptr1 = str.indexOf(',', ptr+1);
  		value = str.substring( ptr1+1 );
  		index = str.substring(ptr+1, ptr1);
  		str = str.substring(1,ptr);
  		setFormFieldValue( str, index, value );
  	}
  	else if( str.substring(0,1) == '&' ){
  		ptr = str.indexOf(',');
  		ptr1 = str.indexOf(',', ptr+1);
  		value = str.substring( ptr1+1 );
  		index = str.substring(ptr+1, ptr1);
  		str = str.substring(1,ptr);
  		setFormFieldText( str, index, value );
  	}
  	else{
  		ptr = str.indexOf(',');
  		index = str.substring(ptr+1);
  		str = str.substring(0,ptr);
  		data = getFormFieldValue( str, index );
  		alert( data );
  	}
}

function _test_getValue()
{
	var fieldName = _test_getFieldName();
	var fieldIndex = document.getElementById('@fieldIndex').value;
	var str = getFormFieldValue( fieldName, fieldIndex );
	alert( str );
}

function _test_setValue()
{
	var fieldName = _test_getFieldName();
	var fieldIndex = document.getElementById('@fieldIndex').value;
	var fieldValue = document.getElementById('@fieldValue').value;
	setFormFieldValue( fieldName, fieldIndex, fieldValue );
}

function _test_getText()
{
	var fieldName = _test_getFieldName();
	var fieldIndex = document.getElementById('@fieldIndex').value;
	var str = getFormFieldText( fieldName, fieldIndex );
	alert( str );
}

function _test_setText()
{
	var fieldName = _test_getFieldName();
	var fieldIndex = document.getElementById('@fieldIndex').value;
	var fieldValue = document.getElementById('@fieldValue').value;
	setFormFieldText( fieldName, fieldIndex, fieldValue );
}

function _test_getAllValue()
{
	var fieldName = _test_getFieldName();
	var str = getFormAllFieldValues( fieldName );
	alert( str );
}

function _test_getSelectedValue()
{
	var fieldName = _test_getFieldName();
	var str = getFormFieldValues( fieldName );
	alert( str );
}

function _test_getFieldName()
{
	var obj = document.getElementById('@fieldName');
	if( obj.tagName.toLowerCase() == 'input' ){
		return obj.value;
	}
	else{
		for (i=0; i<obj.options.length; i++) {
			if( obj.options[i].selected == true ){
				return obj.options[i].text;
			}
		}
	}
	
	return '';
}

function initButton()
{
	var opts = '';
	if( currentFormName != null ){
		var varList = _getFormFieldList( );
		for( var ii=0; ii<varList.length; ii++ ){
			opts += '<option value="' + varList[ii].index + '">' + varList[ii].fieldName + '</option>';
		}
	}
	
	if( opts == '' ){
		opts = '<input type="text" name="@fieldName" id="@fieldName">';
	}
	else{
		opts = '<select name="@fieldName" id="@fieldName">' + opts + '</select>';
	}
	
	var str = '<p align=center>' + 
		'名称：' + opts + '&nbsp;' + 
		'序号：<input type="text" name="@fieldIndex" id="@fieldIndex">&nbsp;' + 
		'内容：<input type="text" name="@fieldValue" id="@fieldValue"></p>' + 
		'<p align="center">' + 
		'<input type="button" value="getValue" onclick="_test_getValue()">&nbsp;' + 
		'<input type="button" value="setValue" onclick="_test_setValue()">&nbsp;' + 
		'<input type="button" value="getText" onclick="_test_getText()">&nbsp;' + 
		'<input type="button" value="setText" onclick="_test_setText()">&nbsp;' + 
		'<input type="button" value="getAllValue" onclick="_test_getAllValue()">&nbsp;' + 
		'<input type="button" value="getSelectedValue" onclick="_test_getSelectedValue()"></p>';
	document.write( str );
}

initButton();




//-->


