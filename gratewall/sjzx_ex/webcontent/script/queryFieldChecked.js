//关与查询条件的验证js方法





/**
 * 判断查询输入条件是否全为空（全空时 返回true）
 * queryNodeName 为JSP页面查询块节点NAME，不传此参数默认select-key
 * initFieldName 为需要初始化的域propert 多个用","分割
 * return true or false
 */
 function isQueryFieldAllNull(queryNodeName,initFieldName){
 	var _queryNodeName = 'select-key';
	if(null != queryNodeName ) _queryNodeName = queryNodeName;
	
 	var _initFieldName ='';	
 	var _initFieldArray = new Array();
 	if(null != initFieldName ) _initFieldName = initFieldName;
 	_initFieldArray = _initFieldName.split(',');
 	
 	/*获取节点输入域数组*/
 	var _inputArr = new Array();
 	_inputArr = getInputFieldArray(_queryNodeName);
  	 		
 	for(var i=0;i<_inputArr.length;i++){
 	    
		var fieldName = _queryNodeName+':'+_inputArr[i];
		var temp = getFormFieldValue(fieldName,0);
		var isInitField = conAndOrStr(_initFieldArray , _inputArr[i]);
		temp = temp.trim();
		if(null != temp && ''!= temp ){			    
			if(!isInitField){
				return true; 
			}						
		}else{
			setFormFieldValue(fieldName,0,'');					
		}	 	    	 	    
 	} 	

	return false; 
 }
 

 /**
  * 是否存在排除验证域字段
  * 数组initFieldArray 
  * 返回 true(存在) 或 false（不存在）
  */
 function conAndOrStr(initFieldArray , checkFieldName ){
 	var _tempFieldArr = new Array();
 	var _checkFieldName ='';
 	//var _isExistField = false;
 	if(null==initFieldArray || ''==initFieldArray ||null==checkFieldName || ''== checkFieldName){
 		return false ;
 	}else{
 		_tempFieldArr = initFieldArray;
 		_checkFieldName = checkFieldName;
 	}
 	
 	for(var i=0;i<_tempFieldArr.length;i++){
 		if(_checkFieldName == _tempFieldArr[i]){
 			return true; 
 		}
 	}
 	return false;
 }



/**
 * 设置所有输入域为空
 * queryNodeName 为JSP页面查询块节点NAME，不传此参数默认select-key
 * initFieldValue 为页面需要初始化值的输入域，传值方式：'property:value' 多个用","分隔;
 *
 */
 function resetQueryFieldNull(queryNodeName,initFieldValue){
	var _queryNodeName = 'select-key';
	if(null != queryNodeName ) _queryNodeName = queryNodeName;
	
 	var _initFieldValue ='';	
 	if(null != initFieldValue ) _initFieldValue = initFieldValue;		
	
	/*获取节点输入域数组*/
 	var _inputArr = new Array();
 	_inputArr = getInputFieldArray(_queryNodeName);
 	
 	if(null == _inputArr || _inputArr.length == 0){  return; }
 	
 	for(var i=0;i<_inputArr.length;i++){
 		var fieldName = _queryNodeName + ':' +_inputArr[i];
 		setFormFieldValue(fieldName,0,'');
 	}
	
	if(null != _initFieldValue ||''!= _initFieldValue ){
		var initFieldArray = _initFieldValue.split(',');
		var initLen = initFieldArray.length;
		for(var m=0;m<initLen;m++){
			var tempArray = initFieldArray[m].split(':');
 			if(2==tempArray.length){
 				var _fieldName = _queryNodeName+':'+tempArray[0];
 				var _fieldValue = tempArray[1];
 				setFormFieldValue(_fieldName,0,_fieldValue);
 			}
		}
	}	 
 }

/**
 * 根据传入的烽火台节点property 值获取所有输入域
 * 返回块内property的数组inputArr
 *
 */
function getInputFieldArray(queryNode){
	var inputArr = new Array();
	var tagIdString = '';
	var tt = document.getElementById(queryNode).all;
	var j = 0;
	for (var i = 0; i < tt.length; i++){
		tagIdString = tt[i].id;
		if(''!= tagIdString){
			var tempArr = tagIdString.split(':');
			if(2==tempArr.length && tempArr[0]==queryNode){
				inputArr[j] = tempArr[1];
				j++;
			}
		}
	}
	return inputArr;
}

//=========================================验证日期=============================================
/**
 * 验证输入日期是否大于当前日期
 * 大于当前日期返回 true
 * 
 */
function validate_Date(inputField,meg){
	if(inputField=="" || null==inputField)return;
	if(""==meg || null==meg){
		meg ="提示：输入日期大于当前日期！";
	}
	var preStr ='';
	var tempArr = inputField.split(":");
	if(tempArr.length == 1){
		preStr ='select-key:';
	}
	var _inputField =preStr+inputField;	
	var _date = new Date();
	var currentDate = _date.getCurrentDate().replaceAll("/",'');
	var vali_date = getFormFieldValue(_inputField,0).trim();
	if(vali_date.length >0){
		vali_date = vali_date.replaceAll("-",'');
		if(vali_date > currentDate){
			alert(meg);
			setFormFieldValue(_inputField,0,'');
			document.getElementById(_inputField).focus();
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}	
}


/**
 * 起始日期输入框验证 （起始日期大于结束日期时返回 true）
 * strInputField:起始日期域（'inputNode：colName'，当'colName'时默认'select-key:colName'）
 * endInputField:结束日期域（'inputNode：colName'，当'colName'时默认'select-key:colName'）
 * meg: 定义不符合业务规则提示信息
 */
function compare_Date(strInputField,endInputField,meg){
	if(strInputField == '' || '' == endInputField) return ;
	if(null == meg || ''== meg) meg = "提示：起始日期不能大于结束日期";
	var preStr_s ='';
	var preStr_e ='';
	var tempArr_s = strInputField.split(':');
	var tempArr_e = endInputField.split(':');
	if(tempArr_s.length == 1){
		preStr_s ='select-key:';
		strInputField = preStr_s+strInputField;
	}
	if(tempArr_e.length == 1){
		preStr_e ='select-key:';
		endInputField = preStr_e+endInputField;
	}
	var strDate = getFormFieldValue(strInputField,0).trim();
	var endDate = getFormFieldValue(endInputField,0).trim();
	if(strDate.length >0 && endDate.length >0){
		if(strDate > endDate){
			alert(meg);
			document.getElementById(strInputField).focus();
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
}

/**
 * 数值比较验证 （ true）
 * strInputField:起始日期域（'inputNode：colName'，当'colName'时默认'select-key:colName'）
 * endInputField:结束日期域（'inputNode：colName'，当'colName'时默认'select-key:colName'）
 * meg: 定义不符合业务规则提示信息
 */
function compare_Value(strInputField,endInputField,meg){
	if(strInputField == '' || '' == endInputField) return ;
	if(null == meg || ''== meg) meg = "提示：查询范围不合逻辑！";
	var preStr_s ='';
	var preStr_e ='';
	var tempArr_s = strInputField.split(':');
	var tempArr_e = endInputField.split(':');
	if(tempArr_s.length == 1){
		preStr_s ='select-key:';
		strInputField = preStr_s+strInputField;
	}
	if(tempArr_e.length == 1){
		preStr_e ='select-key:';
		endInputField = preStr_e+endInputField;
	}
	var strValue = getFormFieldValue(strInputField,0).trim();
	var endValue = getFormFieldValue(endInputField,0).trim();
	if( ''!=strValue && ''!=endValue){
		if(parseFloat(strValue) > parseFloat(endValue)){
			alert(meg);
			document.getElementById(strInputField).focus();
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
}

//=========================================即将废弃====================================================

/**
 * 尽量用resetQueryFieldNull(queryNodeName,initFieldValue) 10月27日
 * 设置所有输入域为空
 * strList查询输入域字符串通过","分割；
 * queryNodeName 为JSP页面查询块节点NAME，不传此参数默认select-key
 */
function resetAllFieldNull(strList,queryNodeName){
	var preStr ='';
	if(strList.length == 0)return;	
	if(''==queryNodeName || null==queryNodeName) preStr = 'select-key:';
	var strArr = strList.split(",");
	var len = strArr.length;
	for(var i=0;i<len;i++){
		var fieldName = preStr+ strArr[i];
	    setFormFieldValue(fieldName,0,'');
	}
}

/**
 * 判断查询输入条件是否全为空（全空时 返回true）
 * strList查询输入域字符串通过","分割；
 * queryNodeName 为JSP页面查询块节点NAME，不传此参数默认select-key
 * return true or false
 */
function isAllQueryFieldNull(strList,queryNodeName){
	var isAllFieldNull = 'Y';
	var preStr ='';
	if(strList.length == 0)return;	
	if(''==queryNodeName || null==queryNodeName) preStr = 'select-key:';
	var strArr = strList.split(",");
	var len = strArr.length;
    for(var i=0;i<len;i++){
		var fieldName = preStr+ strArr[i];
		var temp = getFormFieldValue(fieldName,0);
		temp = temp.trim();
		if( null!=temp && temp.length > 0){
			isAllFieldNull = 'N';
			return ;
		}else{
			setFormFieldValue(fieldName,0,'');
		}
	}	
	if('Y'== isAllFieldNull){
		return true;
	}else{
		return false;
	}
}