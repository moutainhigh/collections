//�����ѯ��������֤js����





/**
 * �жϲ�ѯ���������Ƿ�ȫΪ�գ�ȫ��ʱ ����true��
 * queryNodeName ΪJSPҳ���ѯ��ڵ�NAME�������˲���Ĭ��select-key
 * initFieldName Ϊ��Ҫ��ʼ������propert �����","�ָ�
 * return true or false
 */
 function isQueryFieldAllNull(queryNodeName,initFieldName){
 	var _queryNodeName = 'select-key';
	if(null != queryNodeName ) _queryNodeName = queryNodeName;
	
 	var _initFieldName ='';	
 	var _initFieldArray = new Array();
 	if(null != initFieldName ) _initFieldName = initFieldName;
 	_initFieldArray = _initFieldName.split(',');
 	
 	/*��ȡ�ڵ�����������*/
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
  * �Ƿ�����ų���֤���ֶ�
  * ����initFieldArray 
  * ���� true(����) �� false�������ڣ�
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
 * ��������������Ϊ��
 * queryNodeName ΪJSPҳ���ѯ��ڵ�NAME�������˲���Ĭ��select-key
 * initFieldValue Ϊҳ����Ҫ��ʼ��ֵ�������򣬴�ֵ��ʽ��'property:value' �����","�ָ�;
 *
 */
 function resetQueryFieldNull(queryNodeName,initFieldValue){
	var _queryNodeName = 'select-key';
	if(null != queryNodeName ) _queryNodeName = queryNodeName;
	
 	var _initFieldValue ='';	
 	if(null != initFieldValue ) _initFieldValue = initFieldValue;		
	
	/*��ȡ�ڵ�����������*/
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
 * ���ݴ���ķ��̨�ڵ�property ֵ��ȡ����������
 * ���ؿ���property������inputArr
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

//=========================================��֤����=============================================
/**
 * ��֤���������Ƿ���ڵ�ǰ����
 * ���ڵ�ǰ���ڷ��� true
 * 
 */
function validate_Date(inputField,meg){
	if(inputField=="" || null==inputField)return;
	if(""==meg || null==meg){
		meg ="��ʾ���������ڴ��ڵ�ǰ���ڣ�";
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
 * ��ʼ�����������֤ ����ʼ���ڴ��ڽ�������ʱ���� true��
 * strInputField:��ʼ������'inputNode��colName'����'colName'ʱĬ��'select-key:colName'��
 * endInputField:����������'inputNode��colName'����'colName'ʱĬ��'select-key:colName'��
 * meg: ���岻����ҵ�������ʾ��Ϣ
 */
function compare_Date(strInputField,endInputField,meg){
	if(strInputField == '' || '' == endInputField) return ;
	if(null == meg || ''== meg) meg = "��ʾ����ʼ���ڲ��ܴ��ڽ�������";
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
 * ��ֵ�Ƚ���֤ �� true��
 * strInputField:��ʼ������'inputNode��colName'����'colName'ʱĬ��'select-key:colName'��
 * endInputField:����������'inputNode��colName'����'colName'ʱĬ��'select-key:colName'��
 * meg: ���岻����ҵ�������ʾ��Ϣ
 */
function compare_Value(strInputField,endInputField,meg){
	if(strInputField == '' || '' == endInputField) return ;
	if(null == meg || ''== meg) meg = "��ʾ����ѯ��Χ�����߼���";
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

//=========================================��������====================================================

/**
 * ������resetQueryFieldNull(queryNodeName,initFieldValue) 10��27��
 * ��������������Ϊ��
 * strList��ѯ�������ַ���ͨ��","�ָ
 * queryNodeName ΪJSPҳ���ѯ��ڵ�NAME�������˲���Ĭ��select-key
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
 * �жϲ�ѯ���������Ƿ�ȫΪ�գ�ȫ��ʱ ����true��
 * strList��ѯ�������ַ���ͨ��","�ָ
 * queryNodeName ΪJSPҳ���ѯ��ڵ�NAME�������˲���Ĭ��select-key
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