function $(el){
	if(!el)return null;
	if(typeof el != 'string')return el;
	return document.getElementById(el) || document.getElementsByName(el)[0];
}
function Ok(){
	if(!valid()){
		return;
	}
	$('ObjectForm').ObjectXML.value = getPostData($('DataForm'));
	new ajaxRequest({
			url : './infoview_fieldattrs_dowith.jsp',
			method : 'post',
			parameters : $toQueryStr({
				ObjectXML : $('ObjectForm').ObjectXML.value,
				InfoViewId : $('InfoViewId').value,
				IVFieldIds : $('IVFieldIds').value
			}),
			onSuccess : function(_trans, _json){
				parent.Ok({
					FieldName : m_sFieldName,
					Nillable : $('Nillable').checked,
					FrontReadOnly : $('ReadOnly').checked,
					BackReadOnly :  $('BackReadOnly').checked,
					Default : $('DefaultValue').value
				});
			},
			onFailure : function(_trans, _json){
				alert(_trans.responseText);
			}
	});
}
function doAlert(_sAlertion, _sFocusId){
	alert(wcm.LANG.infoview_fieldattrs_1000 || "填写的默认值不符合该字段的类型！");
	$(_sFocusId).focus();
	$(_sFocusId).select();
	return false;
}
function valid(){
	if($('DefaultValue') && $('DataTypeStr') && $('DefaultValue').value.trim() != ''){
		if($('DataTypeStr').value == 'string' && $('DisplayType').value == 'DTPicker_DTText' && $('DefaultValue').value != '$$CurrTime$$' && $('DefaultValue').value != '$$CurrDate$$')
		  $('DefaultValue').setAttribute("validation", "required:'false',type:'date',showDefault:'false',date_format:'" + $('DatePattern').value + "'");
		if(!ValidationHelper.doValid('DefaultValueSp', doAlert))
			return false;
	}
	var sDisplayName = $('FieldDesc').value;
	if(sDisplayName.byteLength()>100){
		alert(wcm.LANG['INFOVIEW_DOC_41'] || '别名超长，长度限制为50个汉字.');
		try{
			$('FieldDesc').focus();
		}catch (ex){
		}
		return false;
	}	
	if(m_sDataType=='string'){
		if($('ValidFormat') && $('ValidFormat').value.trim()!=''){
			if($('ValidFormatMsg') && $('ValidFormatMsg').value.trim()==''){
				alert(wcm.LANG.infoview_fieldattrs_2000 || '自定义规则的描述不能为空');
				try{
					$('ValidFormatMsg').focus();
				}catch (ex){
				}
				return false;
			}
		}
	}
	if(m_bShowValid){
		return _buildDataControl();
	}
	else if(m_bShowNillable){
		var bNillable = $('Nillable').checked;
		if(m_bAttachment){
			if(!m_bFileAttachment && $('FileExt').value==''){
				$('FileExt').value = m_sImageExt;
			}
			var sControl = 'extvalid:\'' + ($('FileExt').value||'') + '\'';
			var sEvalExp = (('/^.*\\.('+$('FileExt').value.replace(/\,/g, '|'))+')$/ig'||'/^.*\\..*$/ig');
			try{
				eval(sEvalExp);
			}catch(err){
				alert(wcm.LANG['INFOVIEW_DOC_42'] || '请以,号分隔多个文件后缀.');
				return false;
			}
			sControl += ',format:\'' + sEvalExp + '\'';
			if(!bNillable){
				sControl += ',required:true';
			}
			$('DataControl').value = sControl;
			return true;
		}
		else{
			$('DataControl').value = (!bNillable)?'required:true':'';
			return true;
		}
	}
}
var m_sImageExt = 'jpg,png,gif,bmp,jpeg';
function SetDataControl(_sValue){
	$('FormatMsg').style.display = 'none';
	$('FormatContainer').style.display = 'none';
	$('DateTimeContainer').style.display = 'none';
	if(_sValue=='string'){
		$('FormatContainer').style.display = '';
		$('FormatMsg').style.display = '';
	}
	if(_sValue=='date'){
		$('DateTimeContainer').style.display = '';
	}
}
Event.observe(window, 'load', function(){
	var sDataControl = $('DataControl').value;
	var oJson = null;
	try{
		eval('var oJson={'+sDataControl+'};');
	}catch(err){
	}
	if(m_bAttachment && oJson!=null){
		if(oJson['extvalid']!=null){
			$('FileExt').value = oJson['extvalid'];
		}
		else if(!m_bFileAttachment){
			$('FileExt').value = m_sImageExt;
		}
	}
	if(m_bShowValid && oJson!=null){
		if(oJson['date_format']!=null){
			$('DatePattern').value = oJson['date_format'];
		}
		if(oJson['format']!=null){
			$('ValidFormat').value = oJson['format'];
		}
		if(oJson['message']!=null){
			$('ValidFormatMsg').value = oJson['message'];
		}
		if(oJson['length_range']!=null){
			var range = oJson['length_range'].split(',');
			$('MinLength').value = range[0];
			$('MaxLength').value = range[1];
		}
		else{
			if(oJson['max_len']!=null){
				$('MaxLength').value = oJson['max_len'];
			}
			if(oJson['min_len']!=null){
				$('MinLength').value = oJson['min_len'];
			}
		}
		if(oJson['value_range']!=null){
			var range = oJson['value_range'].split(',');
			$('MinNum').value = range[0];
			$('MaxNum').value = range[1];
		}
		else{
			if(oJson['max']!=null){
				$('MaxNum').value = oJson['max'];
			}
			if(oJson['min']!=null){
				$('MinNum').value = oJson['min'];
			}
		}
	}
	if($('ValidFormat')){
		$('ValidFormat').onblur = function(){
			if($('ValidFormat').value!=""){
				$('ValidFormatMsgTip').style.display = "";
			}
			if($('ValidFormat').value==""){
				$('ValidFormatMsgTip').style.display = "none";
			}
		}
	}

	if($('DefaultValue') && $('DataTypeStr')){
		var sDataType = $('DataTypeStr').value;
		if($('DataTypeStr').value != 'string'){
			 $('DefaultValue').setAttribute("validation", "required:'false',type:'" + $('DataTypeStr').value + "'");
		}
	}
});
function buildValueRange(_bFloat){
	var sMaxNum = $('MaxNum').value.trim();
	if(!sMaxNum.match(/^[\+\-]?\d*(\.\d+)?$/g)){
		alert(wcm.LANG['INFOVIEW_DOC_43'] || '最大值只能为数字型！');
		try{
			$('MaxNum').focus();
		}catch(err){}
		return false;
	}
	var sMinNum = $('MinNum').value.trim();
	if(!sMinNum.match(/^[\+\-]?\d*(\.\d+)?$/g)){
		alert(wcm.LANG['INFOVIEW_DOC_44'] || '最小值只能为数字型！');
		try{
			$('MinNum').focus();
		}catch(err){}
		return false;
	}
	var parseFunc = (_bFloat)?parseFloat:parseInt;
	var nMax = (parseFunc)(sMaxNum || '0', 10);
	var nMin = (parseFunc)(sMinNum || '0', 10);
	if(isNaN(nMax)){
		nMax = 0;
	}
	if(isNaN(nMin)){
		nMin = 0;
	}
	if(sMaxNum && sMinNum){
		if(nMax < nMin){
			alert(wcm.LANG['INFOVIEW_DOC_45'] || '最大值不能小于最小值！');
			try{
				$('MaxNum').focus();
			}catch(err){}
			return false;
		}
		return ',value_range:\''+nMin+','+nMax+'\'';
	}
	else{
		if(sMaxNum){
			return ',max:'+nMax;
		}
		else if(sMinNum){
			return ',min:'+nMin;
		}
	}
	return '';
}
function bulidLengthRange(){
	var sMaxLength = $('MaxLength').value;
	if(!sMaxLength.match(/^\s*[\+\-]?\d*\s*$/g)){
		alert(wcm.LANG['INFOVIEW_DOC_46'] || '最大长度只能为整数型！');
		try{
			$('MaxLength').focus();
		}catch(err){}
		return false;
	}
	var sMinLength = $('MinLength').value;
	if(!sMinLength.match(/^\s*[\+\-]?\d*\s*$/g)){
		alert(wcm.LANG['INFOVIEW_DOC_47'] || '最小长度只能为整数型！');
		try{
			$('MinLength').focus();
		}catch(err){}
		return false;
	}
	var nMaxLength = parseInt(sMaxLength || '0', 10);
	var nMinLength = parseInt(sMinLength || '0', 10);
	var bNillable = $('Nillable').checked;
	if(isNaN(nMaxLength)){
		nMaxLength = 0;
	}
	if(isNaN(nMinLength)){
		nMinLength = 0;
	}
	if(nMaxLength>0 && nMinLength>0){
		if(nMaxLength < nMinLength){
			alert(wcm.LANG['INFOVIEW_DOC_48'] || '最大长度不能小于最小长度！');
			try{
				$('MaxLength').focus();
			}catch(err){}
			return false;
		}
		return ',length_range:\''+nMinLength+','+nMaxLength+'\'';
	}
	else{
		if(nMaxLength>0){
			return ',max_len:'+nMaxLength;
		}
		else if(nMinLength>0 && !(nMinLength==1&&!bNillable)){
			return ',min_len:'+nMinLength;
		}
	}
	return '';
}
function _buildDataControl(){
	var sType = m_sDataType;
	if(sType=='double')sType = 'float';
	if(sType=='integer')sType = 'int';
	var bNillable = $('Nillable').checked;
	var sDataControl = '';
	if(sType=='int'){
		sDataControl = 'type:\''+sType+'\'';
		var sValueRange = buildValueRange(false);
		if(sValueRange===false)return false;
		sDataControl += sValueRange;
	}
	else if(sType=='float'){
		sDataControl = 'type:\''+sType+'\'';
		var sValueRange = buildValueRange(true);
		if(sValueRange===false)return false;
		sDataControl += sValueRange;
	}else{
		//sType = $('DataPattern').value;
		sDataControl = 'type:\''+sType+'\'';
		var sValueRange = bulidLengthRange();
		if(sValueRange===false)return false;
		sDataControl += sValueRange;
		if(sType=='string'){
			var sValidFormat = $('ValidFormat').value.trim();
			if(sValidFormat!=''){
				try{
					eval('var re='+sValidFormat);
					re.test('');
				}catch(err){
					try{
						if(!sValidFormat.startsWith('^')){
							sValidFormat = '^' + sValidFormat;
						}
						if(!sValidFormat.endsWith('$')){
							sValidFormat = sValidFormat + '$';
						}
						var re = new RegExp(sValidFormat, '');
						sValidFormat = re.toString();
					}catch(err2){
						alert(wcm.LANG['INFOVIEW_DOC_49'] || '自定义校验的正则表达式有误!');
						return;
					}
				}
				try{
					var sFormat = sValidFormat.replace(/([\'\\\"])/g, '\\$1');
					eval('var re='+sFormat);
				}catch(ex){
					alert((wcm.LANG['INFOVIEW_DOC_50'] || "不正确的正则表达式,如非完整表达式中不能含有/!(")+ex.message+")");
					return;
				}
				sDataControl += ',format:\''+sFormat+'\'';
				sDataControl += ',message:\''+ $('ValidFormatMsg').value.trim()+'\'';
			}
		}
		else if(sType=='date' || sType=='datetime'){
			if($('DateTimeContainer').style.display == ''){
				var sDatePattern = $('DatePattern').value;
				if(sDatePattern.trim()!=''){
					sDataControl += ',date_format:\''+sDatePattern.trim()+'\'';
				}
				else{
					sDataControl += ',date_format:\'yyyy-mm-dd\'';
				}
			}
		}
	}
	if(!bNillable){
		sDataControl += ',required:true';
	}
	if(sDataControl.byteLength() > 200){
		alert(wcm.LANG.infoview_fieldattrs_3000 || "自定义匹配式或字符匹配类型超长，请重新输入！");
		return false;
	}
	$('DataControl').value = sDataControl;
	return true;
}
function SetDefaultValue(_sValue){
	$('DefaultValue').value = _sValue;
}
function GetDefaultValue(_oSelect){
	_oSelect.value = $('DefaultValue').value;
}
Ext.apply(String.prototype, {
	byteLength : function(){
		var length = 0;
		this.replace(/[^\x00-\xff]/g,function(){length++;});
		return this.length+length;
	}
});
function getPostData(frmData){
	var inputElements = frmData.getElementsByTagName('input');
	var selectElements = frmData.getElementsByTagName('select');
	var result = ['<WCMOBJ><PROPERTIES>'];
	for (var i = 0; i < inputElements.length; i++){
		var element = inputElements[i];
		if(element.name=='MaxNum'|| element.name=='MinNum' || element.name=='ValidFormat' 
		|| element.name=='FileExt' || element.name=='ValidFormatMsg')continue;
		if(element.type=='button'||element.type=='reset')continue;
		if(element.type =='checkbox')
			element.value = element.checked ? '1' : '0';
			if(element.name=='ReadOnly' || element.name=='BackReadOnly')
			element.value = element.checked ? '0' : '1';
		var name = element.name.toUpperCase();
		result.push("<", name, "><![CDATA[", element.value, "]]></", name, ">");
	}
	for (var i = 0; i < selectElements.length; i++){
		var selElement = selectElements[i];
		if(selElement.name=="DataTypeStr" || selElement.name=="DisplayType" 
			|| selElement.name=="VarName" || selElement.name=="DatePattern")continue;
		var selName = selElement.name.toUpperCase();
		result.push("<", selName, "><![CDATA[", selElement.value, "]]></", selName, ">");
	}
	result.push('</PROPERTIES></WCMOBJ>');
	return result.join("");
}
function $toQueryStr(_oParams){
	var arrParams = _oParams || {};
	var rst = [], value;
	for (var param in arrParams){
		value = arrParams[param];
		rst.push(param + '=' + value);
	}
	return rst.join('&');
}