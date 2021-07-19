
/*
*************************************************************************************************
	#int			整数
	#double			1-9和小数点	
	#money			金额
	#number			数字
	#string			汉字长度算两位
	#password		密码，转换成MD5
	#date			日期			//1 代表 2009-09-30 , 2 代表 2009/09/30 , 3 代表 20090930									
	#idcard			必须是15位或者18位的数字
	#idcard18		18位身份证，如果是15位身份证，自动转换
	#mail			E-Mail
	#phone			电话号码
	#phones			电话号码列表，分割符[,|;]
	#mobile			移动电话
	#zip			邮政编码
	#url			url地址
	#qq				qq号码
	#file			file
	#calssName		JAVA类名称
*************************************************************************************************
*/

// 数据格式说明
var _dataformatList = [
	[ 'int', '整数，不能包含小数点' ],
	[ 'double', '小数，可以包含正负号，可以包含小数点' ],
	[ 'money', '金额，可以包含正负号，必须包含两位小数点' ],
	[ 'number', '数字，不能包含正负号，不能包含小数点' ],
	[ 'date1', '日期，如 [2009-09-30]' ],
	[ 'date2', '日期，如 [2009/09/30]' ],
	[ 'date3', '日期，如 [20090930]' ],
	[ 'date4', '日期，如 [2009年09月30日]' ],
	[ 'date5', '日期，如 [2005-07-13 14:51:06]' ],
	[ 'idcard', '15位或18位身份证号码' ],
	[ 'idcard18', '18位身份证号码' ],
	[ 'mail', '电子邮件地址，如：wh.zx@gwssi.com.cn' ],
	[ 'phone', '电话号码，如：010-87654321 或 87654321' ],
	[ 'phones', '一个或多个电话号码，用[;]分割，如：010-87654321;87654321' ],
	[ 'mobile', '移动电话号码，如：13*********' ],
	[ 'zip', '6位的邮政编码' ],
	[ 'url', '有效的网址，如：http://www.gwssi.com.cn' ],
	[ 'qq', '4~9位的QQ号码' ],
	[ 'calssName', 'JAVA类名称，路径必须小写，类名的第一个字母必须大写' ]
];


// 校验规则
function FormVerify() {
	this.dateFormatType=3;		//1 代表 2009-09-30 , 2 代表 2009/09/30 , 3 代表 20090930 , 4 代表 2009年09月30日 , 5 代表 2005-07-13 14:51:06
	this.regDate1 = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
	this.exampleDate1="2009-09-30";
	this.regDate2 = /^(\d{1,4})(\/|\/)(\d{1,2})\2(\d{1,2})$/;
	this.exampleDate2="2009/09/30"; 
	this.regDate3 = /^(\d{1,4})(|\/)(\d{1,2})\2(\d{1,2})$/;
	this.exampleDate3="20090930";
	this.regDate4 = /^(\d{4})(年)(\d{2})(月)(\d{2})(日)$/;
	this.exampleDate4="2009年09月30日";
	this.regDate5 = /^(\d{4})(-|\/)(\d{2})\2(\d{2})(\s|\/)(\d{2})(:|\/)(\d{2})$/;
	this.exampleDate5="2009-09-30 14:51";
	this.regRequire = /.+/;
	this.regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	this.regPhone = /^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(-d{1,5})?$/;
	this.regPhones = /^(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(-d{1,5})?([,|;](\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(-d{1,5})?)*$/;
	this.regMobile = /^((\(\d{3}\))|(\d{3}\-))?13\d{9}$/,
	this.regUrl = /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;	//'
	this.className = /^([_a-z0-9]+\.)*[A-Z][_A-Za-z0-9]+$/;
	this.regIdCard15 = /^\d{15}(\d{2}[A-Za-z0-9])?$/;
	this.regIdCard18 = /^\d{17}(?:\d|x|X)$/;
	this.regMoney = /[0-9](\.|\/)(\d{2})$/;
	this.regNumber = /^\d+$/;
	this.regZip = /^[1-9]\d{5}$/;
	this.regQQ = /^[1-9]\d{3,8}$/;
	this.regInteger = /^[-\+]?\d+$/;
	this.regDouble = /^[-\+]?\d+(\.\d+)?$/;
	this.regEnglish = /^[A-Za-z]+$/;
	this.regChinese =  /^[\u0391-\uFFE5]+$/;
	this.regUnSafe = /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/;	//'
	this.regNotChar=/^[^`~!@#$%^&*()+=|\\\][\]\{\}:;'\,.<>/?]{1}[^`~!@$%^&()+=|\\\][\]\{\}:;'\,.<>?]{0,32}$/;
	this.regChar=/[(\/)(\\)(')(")(<)(>)]/g;		//'
	this.extArray = new Array(".gif",".jpg",".png",".bmp",".xml",".txt",".doc",".docx",".xls",".xlsx",".zip",".rar",".pdf",".dbf");
}

var FormVerify = new FormVerify();


// 数据被转换以前的原始信息
var valid_original_value_list = new Array();
function add_valid_original_value_list( obj, value )
{
	var v = new Array();
	v[0] = obj;
	v[1] = value;
	
	var l = valid_original_value_list.length;
	valid_original_value_list[l] = v;
}

function reset_valid_original_value( )
{
	var obj;
	var value;
	for( var ii=0; ii<valid_original_value_list.length; ii++ ){
		obj = valid_original_value_list[ii][0];
		value = valid_original_value_list[ii][1];
		obj.value = value;
	}
	
	valid_original_value_list = new Array();
}


// 取数据的格式
function _getDataFormatHint( obj )
{
	var datatype = obj.datatype;
	if( datatype == null || datatype == '' ){
		return null;
	}
	
	if( datatype == 'date' ){
//		datatype = datatype + _defaultDateFormat;
		datatype = null;
	}
	
	for( var ii=0; ii<_dataformatList.length; ii++ ){
		if( _dataformatList[ii][0] == datatype ){
			return '格式：' + _dataformatList[ii][1];
		}
	}
	
	return null;
}


/* ************** 检查域的有效性 *************** */

// 检查域，有效性在域的定义中
function checkInputFieldValid( fieldName )
{
	// 检查
	var flag = true;
	var field = document.getElementsByName( fieldName );
	for( var index=0; index<field.length; index++ ){
		flag = _checkInputFieldValid( field, index );
		if( flag == false ){
			return false;
		}
	}//end for
	
	return true;
}


// 检查域的有效性，包括自定义的规则
function _checkInputFieldValid( field, index )
{
	// 判断域的FORM和当前提交的域是否一致
	if( currentFormName != null && field[index].form != null && field[index].form.name != currentFormName ){
		return true;
	}
	
	var flag = true;
	
	// 是否检查有效性
	if( field[index].checkFlag != 'true' ){
		return true;
	}
	
	// 取有效性参数
	var notnull = true;
	if( field[index].notnull != 'true' ){
		notnull = false;
	}
	
	// 最小长度或最小值
	var minlength = field[index].minvalue;
	if( minlength == null ){
		minlength = 0;
	}
	
	// 最大长度或最大值
	var maxlength = field[index].maxvalue;
	if( maxlength == null ){
		maxlength = 0;
	}
	
	// 数据类型
	var datatype = field[index].datatype;
	if( datatype == null ){
		datatype = 'string';
	}
	
	// 数据格式
	var dataformat = field[index].dataformat;
	if( dataformat == null ){
		dataformat = '';
	}
	
	// 标题
	var fieldCaption = field[index].fieldCaption;
	if( fieldCaption == null ){
		fieldCaption = '';
	}
	
	// 检查域的自定义规则
	flag = _checkFieldValidatorRule( field, index, field[index].validator, fieldCaption );
	if( flag == false ){
		return false;
	}
	
	// 通用规则检查
	flag = _checkFieldValid( field[index], notnull, minlength, maxlength, datatype, dataformat, fieldCaption );
	if( flag == false ){
		return false;
	}
	
	return true;
}

// 检查域的自定义规则
function _checkFieldValidatorRule( field, index, rule, fieldCaption )
{
	if( rule == null || rule == '' ){
		return true;
	}
	
	var flag = true;
	if( rule.indexOf('/') == 0 && rule.lastIndexOf('/') == rule.length-1 ){
		var obj = field[index];
		var _str = obj.value;
		
		// 检查格式有效性:正则表达式[/表达式/]
		flag = _test_dataformat(_str, rule, fieldCaption);
	}
	else{
		// 自定义校验函数:替换[']
		rule = rule.replace(/`/g, "'");
		
		// 增加参数
		var param = 'field, ' + index + ', "' + fieldCaption + '"';
		var idx = rule.indexOf(')');
		if( idx < 0 ){
			rule = rule + '(' + param + ')';
		}
		else{
			var s = rule.substring(0, idx).trim()
			if( s.charAt(s.length-1) != '(' ){
				s = s + ',';
			}
			
			rule = s + param + rule.substring(idx);
		}
		
		flag = eval( rule );
	}
	
	if( flag == false ){
		field[index].focus();
		return false;
	}
	
	return true;
}


// 检查域的有效性
function _checkFieldValid(field, notnull, minlength, maxlength, datatype, dataformat, fieldCaption)
{
	if( minlength == null ){
		minlength = 0;
	}
	
	if( maxlength == null ){
		maxlength = 0;
	}
	
	// 转成字符串
	if( dataformat == null ){
		dataformat = '';
	}
	else{
		dataformat = '' + dataformat;
	}
	
	// 是否检查有效性
	if( minlength == 0 && 
		maxlength == 0 &&
		notnull == false &&
		datatype == 'string' &&
		dataformat == ''
	)
	{
		return true;
	}
	
	// 取值
	var _str=field.value;
	
	// 检查数值型数据是否能够空
	if( notnull == true && getStringLength(_str)==0 ){
		reset_valid_original_value();
  		alert("输入域[" + fieldCaption + "]不能为空！");
  		field.focus();
  		return false;			
  	}
  	
  	// 转换成小写
  	datatype = datatype.toLowerCase();
  	
  	// 如果是文件，需要先看accept,里面保存的文件后缀，如果没有设置这个参数，判断是否是缺省的文件类型
  	var oldFileList = FormVerify.extArray;
  	if( datatype == "file" ){
  		var accept = field.accept;
  		if( accept != null && accept != '' ){
	  		accept = accept.replace( /[;]/g, ',' );
	  		var list = accept.split( ',' );
	  		for( var ii=0; ii<list.length; ii++ ){
	  			var s = list[ii].trim();
	  			var iptr = s.lastIndexOf( '.' );
	  			if( iptr > 0 ){
	  				list[ii] = s.substring( iptr );
	  			}
	  			else{
	  				list[ii] = '.' + s;
	  			}
	  		}
	  		
	  		FormVerify.extArray = list;
	  	}
  	}
	
	// 检查数据有效性
	var flag = checkValueValid( _str, minlength, maxlength, datatype, dataformat, fieldCaption );
	
	
	if (datatype=="date"){
		if (_str.length>0){
			// 修改field.value
			var _tmp = _str;
			if(dataformat == '4' || dataformat == '2') {
				_tmp = _str.substring(0, 4) + "-" + _str.substring(5,7) + "-" + _str.substring(8,10);	
			}
			if(dataformat == '3') {
				_tmp = _str.substring(0, 4) + "-" + _str.substring(4,6) + "-" + _str.substring(6,8);	
			}
			field.value = _tmp;			
		}
	}

	// 恢复文件的后缀列表
  	if( datatype == "file" ){
  		FormVerify.extArray = oldFileList;
  	}
	
	// 判断是否有效
	if( flag == false ){
		reset_valid_original_value();
		field.focus();
		return false;
	}
	
	// 转换18位身份证 和 加密密码
	if( _str != null && _str != '' ){
		// 保存原始数据
		add_valid_original_value_list( field, _str );
		
		if( datatype == "idcard18" ){
			field.value = transIdcard18( _str );
		}
		else if( datatype == "password" ){
			field.value = calcMD5( _str );
		}
	}
	
	// 正则表达式[/表达式/]
	if( dataformat.indexOf('/') == 0 && dataformat.lastIndexOf('/') == dataformat.length-1 ){
		flag = _test_dataformat(_str, dataformat, fieldCaption);
		if( flag == false ){
			field.focus();
			return false;
		}
	}
}


// 检查数据的有效性
function checkValueValid(value, minlength, maxlength, datatype, dataformat, fieldCaption)
{
	//>>>>int<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	if (datatype=="int"){			
		//处理数据类型
		if (value.length>0){
			if(isInteger(value,fieldCaption)==false){
				return false;
			}	
		
			//处理数字的大小、非空
			if (checkInteger(value,minlength,maxlength,fieldCaption)==false){
				return false;	
			}
		}
		return true;
		
	//>>>>double<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	}else if (datatype=="double"){
		//处理数据类型
		if (value.length>0){
			if(isDouble(value,fieldCaption)==false){
				return false;
			}
			
			//处理数字的大小、非空
			if (checkInteger(value,minlength,maxlength,fieldCaption)==false){
				return false;	
			}
		}
		return true;
		
	//>>>>money<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="money"){
		if( value.length>0 ){
			if(isMoney(value,fieldCaption)==false){
				return false;
			}
			
			//处理金额的大小、非空
			if (checkInteger(value,minlength,maxlength,fieldCaption)==false){
				return false;	
			}
		}
		return true;
		
	//>>>>String<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	}else if (datatype=="string" || datatype=="password" || datatype=="password2"){
		//处理字符串的长度、非空
		if (checkStrLength(value,minlength,maxlength,fieldCaption)==false){
			return false;	
		}
		return true;

	//>>>>date<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	}else if (datatype=="date"){
		if( dataformat != '1' && dataformat != '2' && dataformat != '4' && dataformat != '5'){
			dataformat = '3';
		}
		
		if (value.length>0){
			if(isValidDate(value,fieldCaption,dataformat)==false){
				return false;
			}	
		}
		else if (minlength>0){
			alert("["+fieldCaption+"]输入不能为空!");
			return false;	
		}
		
		return true;
		
	//>>>>mail<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="mail"){
		if (value.length>0){
			if(isEmail(value,fieldCaption)==false){
				return false;
			}	
		}
		else if (minlength>0){
			alert("["+fieldCaption+"]输入不能为空!");
			return false;	
		}
		
		return true;
	//>>>>idcard<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="idcard" || datatype=="idcard18"){
		if (value.length>0){
			if(isIdCard(value,fieldCaption)==false){
				return false;
			}	
		}
		else if (minlength>0){
			alert("["+fieldCaption+"]输入不能为空!");
			return false;	
		}
		
		return true;
		
	//>>>>phone<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="phone"){
		if (value.length>0){
			if(isPhone(value,fieldCaption)==false){
				return false;
			}	
		}
		else if (minlength>0){
			alert("["+fieldCaption+"]输入不能为空!");
			return false;	
		}
		
		return true;
		
	//>>>>phones<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="phones"){
		if (value.length>0){
			if(isPhones(value,fieldCaption)==false){
				return false;
			}	
		}
		else if (minlength>0){
			alert("["+fieldCaption+"]输入不能为空!");
			return false;	
		}
		
		return true;
		
	//>>>>mobile<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="mobile"){
		if (value.length>0){
			if(isMobile(value,fieldCaption)==false){
				return false;
			}	
		}
		else if (minlength>0){
			alert("["+fieldCaption+"]输入不能为空!");
			return false;	
		}
		
		return true;

	//>>>>zip<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="zip"){
		if (value.length>0){
			if(isZip(value,fieldCaption)==false){
				return false;
			}	
		}
		else if (minlength>0){
			alert("["+fieldCaption+"]输入不能为空!");
			return false;	
		}
		
		return true;


	//>>>>url<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="url"){
		if (value.length>0){
			if(isUrl(value,fieldCaption)==false){
				return false;
			}	
		}
		else if (minlength>0){
			alert("["+fieldCaption+"]输入不能为空!");
			return false;	
		}
		
		return true;

	//>>>>class<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="classname"){if (value.length>0){
			if(isClassName(value,fieldCaption)==false){
				return false;
			}	
		}
		else if (minlength>0){
			alert("["+fieldCaption+"]输入不能为空!");
			return false;	
		}
		
		return true;

	//>>>>qq<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="qq"){
		if (value.length>0){
			if(isQQ(value,fieldCaption)==false){
				return false;
			}	
		}
		else if (minlength>0){
			alert("["+fieldCaption+"]输入不能为空!");
			return false;	
		}
		
		return true;
	
	//>>>>number<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="number"){
		//处理字符串的长度、非空
		if (checkStrLength(value,minlength,maxlength,fieldCaption)==false){
			return false;	
		}
		
		if (value.length>0){
			if(isNumber(value,fieldCaption)==false){
				return false;
			}	
		}
		
		return true;
		
	//>>>>file<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="file"){
		if (value.length>0){
			if(isFileType(value,fieldCaption)==false){
				return false;
			}	
		}
		else if (minlength>0){
			alert("["+fieldCaption+"]输入不能为空!");
			return false;	
		}
		
		return true;
		
	//>>>>file<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if ( datatype != "" && datatype != null ){
		alert( fieldCaption + ':未知的数据类型[' + datatype + ']' );
		return false;
		
	//>>>>default string<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 
	}else{
		//处理字符串的长度、非空
		if (checkStrLength(value,minlength,maxlength,fieldCaption)==false){
			return false;
		}
		
		return true;
	}//end if
}

/*	
检测输入非空		
str				输入的字符
Msg				输入域标题,用于提示
返回值			true or false
*/
function isRequire(str,Msg) {
	var reg=FormVerify.regRequire;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]不能为空!");
		return false;
	}else{		
		return true;
	}
}


/*	
检测E-Mail输入的合法性		user@user.com
str						输入的E-Mail
Msg						输入域标题,用于提示
返回值					true or false
*/
function isEmail(str,Msg) {
	var reg=FormVerify.regEmail;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]输入的格式不正确!");
		return false;
	}else{		
		return true;
	}
}


/*	
检测电话号码输入的合法性		010-62967799 or 62967799
str						输入的电话号码
Msg						输入域标题,用于提示
返回值					true or false
*/
function isPhone(str,Msg) {
	var reg=FormVerify.regPhone;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]输入的格式不正确!");
		return false;
	}else{		
		return true;
	}
}

// 电话号码列表
function isPhones(str,Msg) {
	var reg=FormVerify.regPhones;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]输入的格式不正确!");
		return false;
	}else{		
		return true;
	}
}


/* 
检测移动电话号码输入的合法性		13310922255
str							输入的移动电话号码
Msg							输入域标题,用于提示
返回值						true or false
*/
function isMobile(str,Msg) {
	var reg=FormVerify.regMobile;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]输入的格式不正确!");
		return false;
	}else{		
		return true;
	}
}


/* 
检测连接地址输入的合法性		http://www.sina.com.cn
str						输入的连接地址
Msg						输入域标题,用于提示
返回值					true or false
*/
function isUrl(str,Msg) {
	var reg=FormVerify.regUrl;
	if (reg.test(str.toLowerCase())==false){
		window.alert("["+Msg+"]输入的格式不正确!");
		return false;
	}else{		
		return true;
	}
}

/* 
检测JAVA类名称输入的合法性		cn.gwss.app.user.Operator
str						输入的连接地址
Msg						输入域标题,用于提示
返回值					true or false
*/
function isClassName(str,Msg) {
	var ptr = str.indexOf( '{' );
	if( ptr > 0 ){
		var ptr1 = str.indexOf( '}', ptr );
		if( ptr1 > 0 ){
			var rs = str.substring( ptr, ptr1+1 );
			window.alert("["+Msg+"]请替换[" + rs + "]部分的内容!");
			return false;
		}
	}
	
	var reg=FormVerify.className;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]输入的格式不正确!");
		return false;
	}else{		
		return true;
	}
}


/* 
检测身份证号码输入的合法性	15位或者18位的数字
str						输入的身份证号码
Msg						输入域标题,用于提示
返回值					true or false
*/
function isIdCard(str,Msg) {
	var reg = (str.length==18) ? FormVerify.regIdCard18 : FormVerify.regIdCard15;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]输入的格式不正确!");
		return false;
	}else{
		//校验取值范围
		_arrayymd=getIdCardYearMonthDay(str,Msg);		
		if (checkYearMonthDay(_arrayymd,Msg)==false){
			alert("["+Msg+"]中日期的取值范围不正确!");
			return false;		
		}	
	}//end if
	
	// 强制检查身份证的校验位
	var	checkValue = strictCheckIdcard(str);
	if( checkValue != null ){
		alert( "["+Msg+"]的校验位不正确，应该是[" + checkValue + "]" );
		return false;
	}
	
	return true;
}


//从身份证中取得年、月、日的数组
function getIdCardYearMonthDay(str,Msg){	
	var _array=new Array();
	if(str.length==18){		
		_array[0]=str.substr(6,4);
		_array[1]=str.substr(10,2);
		_array[2]=str.substr(12,2);
	}else if(str.length==15){		
		_array[0]="19"+str.substr(6,2);
		_array[1]=str.substr(8,2);
		_array[2]=str.substr(10,2);	
	}
	return _array
}

// 取出生日期
function getBirthdayFromIdCard( str )
{
	var	val;
	if(str.length==18){
		val = str.substr(6, 8);
	}
	else{
		val = "19" + str.substr(6, 6);
	}
	
	return val;
}

function getGenderFromIdCard( str )
{
	var	val;
	if(str.length==18){
		val = str.substr(17, 1);
	}
	else{
		val = str.substr(15);
	}
	
	if( val == '0' || val == '2' || val == '4' || val == '6' || val == '8' ){
		val = '女';
	}
	else{
		val = '男';
	}
	
	return val;
}

/*
检查是否是金额类型		2355.00
str					传入的金额
Msg					输入域标题,用于提示
返回值				true or false
*/
function isMoney(str,Msg){
	var reg=FormVerify.regMoney;	
	if (reg.test(str)==false){
		alert("["+Msg+"]格式必须是 2355.00!");
		return false;	
	}else{
		return true;
	}
}

/*
检查是否是数字		1-9 	
str				传入的数字
Msg				输入域标题,用于提示
返回值			true or false
*/
function isNumber(str,Msg){
	var reg=FormVerify.regNumber;	
	if (reg.test(str)==false){
		alert("["+Msg+"]必须是0-9的数字!");
		return false;	
	}else{
		return true;
	}
}


/*
检查是否是邮政编码		100085 	
str					传入的邮政编码
Msg					输入域标题,用于提示
返回值				true or false
*/
function isZip(str,Msg){
	var reg=FormVerify.regZip;	
	if (reg.test(str)==false){
		alert("["+Msg+"]必须是六位的数字!");
		return false;	
	}else{
		return true;
	}
}


/*
检查是否是整数		 	+- 0-9
str					传入的整数
Msg					输入域标题,用于提示
返回值				true or false
*/
function isInteger(str,Msg){
	var reg=FormVerify.regInteger;	
	if (reg.test(str)==false){
		alert("["+Msg+"]输入不是整数类型!");
		return false;	
	}else{
		return true;
	}
}


/*
检查是否是QQ号码		 	
str					传入的QQ号码
Msg					输入域标题,用于提示
返回值				true or false
*/
function isQQ(str,Msg){
	var reg=FormVerify.regQQ;	
	if (reg.test(str)==false){
		alert("["+Msg+"]格式不正确!");
		return false;	
	}else{
		return true;
	}
}


/*
检查是否是Double类型	12333.99	 	
str					传入的Double类型
Msg					输入域标题,用于提示
返回值				true or false
*/
function isDouble(str,Msg){
	var reg=FormVerify.regDouble;
	
	if (reg.test(str)==false){
		alert("["+Msg+"]输入不是Double类型!");
		return false;	
	}else{
		return true;
	}
}

/*
检查是否是英文			a-z and A-Z	 	
str					传入的英文
Msg					输入域标题,用于提示
返回值				true or false
*/
function isEnglish(str,Msg){
	var reg=FormVerify.English;	
	if (reg.test(str)==false){
		alert("["+Msg+"]只能输入英文字母(a-z and A-Z)!");
		return false;	
	}else{
		return true;
	}
}


/*
检查是否是汉字			只能是汉字	 	
str					传入的汉字
Msg					输入域标题,用于提示
返回值				true or false
*/
function isChinese(str,Msg){
	var reg=FormVerify.regChinese;	
	if (reg.test(str)==false){
		alert("["+Msg+"]只能输入汉字!");
		return false;	
	}else{
		return true;
	}
}


/*
检查是否是含有非法字符				
str						传入的字符
Msg						输入域标题,用于提示
返回值					true or false
*/
function isUnSafe(str,Msg){
	var reg=FormVerify.regUnSafe;	
	if (reg.test(str)==false){
		alert("["+Msg+"]输入还有非法字符!");
		return false;	
	}else{
		return true;
	}
}


/*
检查上传文件类型				
str						传入的字符
Msg						输入域标题,用于提示
返回值					true or false
*/
function isFileType(str,Msg) {
	allowSubmit = false; 
	if (!str) {
		return false;
	}
	
	while (str.indexOf("\\") != -1){
		str = str.slice(str.indexOf("\\") + 1);
	}
	
	ext = str.slice(str.lastIndexOf(".")).toLowerCase();	
	for (var i = 0; i < FormVerify.extArray.length; i++) {
		if( FormVerify.extArray[i] == ".*" || FormVerify.extArray[i] == ext ){ 
			allowSubmit = true; 
			break; 
		}
	}
	
	if (allowSubmit==false) {	
		alert("["+Msg+"]只能上传"+ (FormVerify.extArray.join("  ")) + ",\n请重新选择数据类型！");
		return false;
	}
	
	return true;
}


/*
检查是否是合法日期格式,日期的格式从FormVerify类中取得				
str						传入的字符
Msg						输入域标题,用于提示
返回值					true or false
*/
function isValidDate(str,Msg,datetype){
	var exampleDate="";
	var reg=""
	var strlength="";
	
	if (datetype=="1"){
		exampleDate=FormVerify.exampleDate1;
		reg=FormVerify.regDate1;
		strlength=10;	
	}
	else if (datetype=="2"){
		exampleDate=FormVerify.exampleDate2;
		reg=FormVerify.regDate2;
		strlength=10;
	}
	else if (datetype=="3"){
		exampleDate=FormVerify.exampleDate3;
		reg=FormVerify.regDate3;
		strlength=8;	
	}
	else if (datetype=="4"){
		exampleDate=FormVerify.exampleDate4;
		reg=FormVerify.regDate4;
		strlength=11;	
	}
	else if(datetype=="5"){
		exampleDate=FormVerify.exampleDate5;
		reg=FormVerify.regDate5;
		strlength=16;	
	}
	
	if (str.length!=strlength){
		alert("["+Msg+"]格式不正确,格式必须是"+exampleDate+"!");
		return false;	
	}
	
	if (reg.test(str)==false){
		alert("["+Msg+"]格式不正确,格式必须是"+exampleDate+"!");
		return false;	
	}
	else{
		//校验取值范围
		var _array=getArrayDate(str,datetype);
		if (checkYearMonthDay(_array,Msg)==false){
			alert("["+Msg+"]取值范围不正确!");
			return false;		
		}
		else{
			return true;
		}
	}//end if
}

/*
检查是否是含有特殊字符				
str						传入的字符
Msg						输入域标题,用于提示
返回值					true or false
*/
function isNotChar(str,Msg){	
	reg=FormVerify.regNotChar;	
	if (reg.test(str)==false){
		alert("["+Msg+"]不能含有特殊字符!");
		return false;	
	}else{
		return true;
	}
}



/*
判断输入的字符串的长度，汉字算两个字符
str					输入的待检测字符串
返回值				长度
*/
function getStringLength(str){ 
	_i=0; 
	if(str.length>=1){
		for(i=0;i<str.length;i++){
			if(ChineseLength(str.charAt(i))){
				_i=_i+2;
			}else{
				_i++;
			}
		}
	}	
	return _i;
} 

function ChineseLength(str){
	var reg=FormVerify.regChinese;
	if (reg.test(str)==true){
		return true; //全部是汉字
	}else{
		return false; //含有字符
	}
}


/*
检测输入内容的长度(String、Number)
str				输入的待检测字符串
msg 			输入域标题,用于提示
maxL			最大长度   0 、 小于minL 、 ""
minL			最小长度
返回值			true or false
*/
function checkStrLength(str,minL,maxL,Msg){
    if (minL>0){
    	if (getStringLength(str)==0){
    		alert("["+Msg+"]输入不能为空！");
    		return false;			
    	}else{
    		if (isStrMaxMin(str,minL,maxL,Msg)==false){			
				return false;	
	    	}
    	}
    }else{
    	if (isStrMaxMin(str,minL,maxL,Msg)==false){			
			return false;	
    	}
    }    
}

// 比较输入字符串的长度
function isStrMaxMin(str,minL,maxL,Msg)
{
	if( maxL=='' ){
		maxL = 0;
	}
	else{
		maxL = parseInt( maxL );
	}
	
	if( minL=='' ){
		minL = 0;
	}
	else{
		minL = parseInt( minL );
	}
	
	//只判断最小长度
	if (maxL==0){
		if (getStringLength(str)<minL){
			alert("["+Msg+"]输入内容的长度必须大于"+minL+"!");
			return false;	
    	}
    //输入参数错误
	}
	else if(maxL<minL){		
		alert("输入的参数错误，最大长度必须大于最小长度！");
		return false;
	}
	else{
		//在最大长度和最小长度之间
		if (maxL!=minL){
			if (getStringLength(str)<minL ||getStringLength(str)>maxL){
				alert("["+Msg+"]输入内容的长度必须大于"+minL+" 小于"+maxL+"!");
				return false;	
			}
		//必须相等
		}else{
			if (getStringLength(str)!=minL){
				alert("["+Msg+"]输入内容的长度必须等于"+minL+"!");
				return false;	
			}
		}
	}
	
	return true;
}


/*
检测输入数据的大小是否正确(Integer、Double转换成int在比较大小)
str					输入的待检测字符串
msg 				输入域标题,用于提示
maxL				最大长度   0 、 小于minL 、 ""
minL				最小长度
返回值				true or false
*/
function checkInteger(str,minL,maxL,Msg){
	//检测str
	if(isNaN(parseInt(str))==true){
		alert("输入的数据["+str+"]类型不正确！");
		return false;
	}
	
	if( minL != maxL ){
		if((maxL-minL)<0){		
			alert("输入的参数错误，最大值必须大于最小值");
			return false;
		}
		
		if( minL != -999999999 ){
			if( str - minL < 0 ){
				alert("["+Msg+"]输入数据必须大于"+minL+"!");
				return false;	
	    }
	  }
	  
	  if( maxL != 999999999 ){
			if( str - maxL > 0 ){
				alert("["+Msg+"]输入数据必须小于"+maxL+"!");
				return false;	
	    }
	  }
	}
}


function checkDouble(str,minL,maxL,Msg){
	//检测str
	if(isNaN(parseFloat(str))==true){
		alert("输入的数据["+str+"]类型不正确！");
		return false;
	}
	
	if( minL != maxL ){
		if((maxL-minL)<0){		
			alert("输入的参数错误，最大值必须大于最小值");
			return false;
		}
		
		if( minL != -999999999 ){
		  if( str - minL < 0 ){
			alert("["+Msg+"]输入数据必须大于"+minL+"!");
			return false;	
	      }
	    }
	  
	    if( maxL != 999999999 ){
		  if( str - maxL > 0 ){
			alert("["+Msg+"]输入数据必须小于"+maxL+"!");
			return false;	
    	  }
	   }
	}
}


/*
日期格式转换 			转换完成后的格式2009-09-09
str					输入的日期,必须是日期格式检测之后的日期
formattype  		1 代表 2009-09-30 , 2 代表 2009/09/30 , 3 代表 20090930
返回值				格式化后的日期
*/
function formatDate(str,formattype){
	if(formattype=="1"){
		return str;
	}else if(formattype=="2"){
		if(str.length==10){					
			return str.substr(0,4)+"-"+str.substr(5,2)+"-"+str.substr(8,2);
		}else{
			return str;
		}
	}else if(formattype=="3"){
		if(str.length==8){
			return str.substr(0,4)+"-"+str.substr(4,2)+"-"+str.substr(6,2);
		}else{
			return str;
		}
	}else{
		return str;
	}
}


/*
检查日期中年月日的合法性					
str							传入年月日的数组
Msg							输入域标题,用于提示
返回值						true or false
*/
function checkYearMonthDay(str,Msg){				
	if (str!=null){	
		//处理年	
		var years=str[0];
		if((parseInt(years)%4==0 && parseInt(years)%100!=0) || parseInt(years)%400==0){
			leapyear=true;
		}else{
			leapyear=false;
		}
		if(years.length!=4 || years==0){ 
			return false;
		}
		for(i=0;(i<years.length);i++){
			if("0123456789".indexOf(years.charAt(i))==-1){
			   return false;
			 }
		}
		
		//处理月
		var months=str[1];
		if(!months || months==0) {
			return false;
		} 
		
		for(i=0;(i<months.length);i++){
			if("0123456789".indexOf(months.charAt(i))==-1){
				return false;
			}
		}
		if(parseInt(months, 10)>12) {
			return false;
		}
		
		//处理日
		var days=str[2];
		if(!days || days==0) {
			return false;
		}
		
		for(i=0;(i<days.length);i++){
			if("0123456789".indexOf(days.charAt(i))==-1){
				return false;
			}
		}
		D=30;
		switch(parseInt(months, 10)){
		case 4:
		case 6:
		case 9:
		case 11: D=30; break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12: D=31; break;
		case 2: if(leapyear) 
		           D=29;
		        else         
		           D=28;
		}
		if(days>D){
			return false;
		}
		
		if(str.length>3){
    		//处理时
    		var hours=str[3];
    		if(!hours) {
    			return false;
    		} 
    		
    		for(i=0;(i<hours.length);i++){
    			if("0123456789".indexOf(hours.charAt(i))==-1){
    				return false;
    			}
    		}
    		if(parseInt(hours, 10)>24) {
    			return false;
    		}
    		
    		//处理分
    		var minutes=str[4];
    		if(!minutes) {
    			return false;
    		} 
    		
    		for(i=0;(i<minutes.length);i++){
    			if("0123456789".indexOf(minutes.charAt(i))==-1){
    				return false;
    			}
    		}
    		if(parseInt(minutes, 10)>60) {
    			return false;
    		}
    		
    		// Comment By WeiQiang 2007-07-12
    /*		//处理秒
    		var seconds=str[5];
    		if(!seconds) {
    			return false;
    		} 
    		
    		for(i=0;(i<seconds.length);i++){
    			if("0123456789".indexOf(seconds.charAt(i))==-1){
    				return false;
    			}
    		}
    		if(parseInt(seconds, 10)>60) {
    			return false;
    		}
    		*/
		}
			
	return true;		
	}//END IF
	
	return false;	
}


/*
获得年、月、日的数组					
str							传入验证后的日期
返回值						年、月、日数组
*/
function getArrayDate(str,datetype){
	var _array=new Array();;
	if (datetype=="1"){
		_array[0]=str.substr(0,4);
		_array[1]=str.substr(5,2);
		_array[2]=str.substr(8,2);
	}else if (datetype=="2"){
		_array[0]=str.substr(0,4);
		_array[1]=str.substr(5,2);
		_array[2]=str.substr(8,2);
	}else if (datetype=="3"){
		_array[0]=str.substr(0,4);
		_array[1]=str.substr(4,2);
		_array[2]=str.substr(6,2);
	}else if (datetype=="4"){
		_array[0]=str.substr(0,4);
		_array[1]=str.substr(5,2);
		_array[2]=str.substr(8,2);
	}else if (datetype=="5"){
		_array[0]=str.substr(0,4);
		_array[1]=str.substr(5,2);
		_array[2]=str.substr(8,2);
		
		_array[3]=str.substr(11,2);
		_array[4]=str.substr(14,2);
		// Comment By WeiQiang 2007-07-12
		// _array[5]=str.substr(17,2);
	}
	
	return _array;
}






// 严格检查身份证的有效性
function strictCheckIdcard( id )
{
	// 只检查18位身份证
	if( id.length != 18 ){
		return null;
	}
	
	var	checkValue = getIdCheckNumber( id );
	var	iv = id.substring(17).toUpperCase();
	if( checkValue != iv ){
		return checkValue;
	}
	
	return null;
}

// 15位身份证转换成18位身份证
function transIdcard18( id )
{
	if( id.length == 18 ){
		return id.toUpperCase();
	}
	
	if( id.length != 15 ){
		// alert( '身份证的长度错误' );
		return id;
	}
	
	id = id.substr(0, 6) + '19' + id.substr(6);
	return id + getIdCheckNumber(id);
}

function getIdCheckNumber( id )
{
	var	arrVerifyCode = "1,0,X,9,8,7,6,5,4,3,2".split(",");
	var	wi = "7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2".split(",");
	
	var	checkNumber = 0;
	for( i=0; i<17; i++ ){
		checkNumber = checkNumber + parseInt(id.substring(i, i+1)) * wi[i];
	}
	
	var	modValue = checkNumber % 11;
	var	checkValue = arrVerifyCode[modValue];
	
	return checkValue;
}


// 检查两个域的内容是否一致
// 输入参数：字段1列表，字段1编号，字段1标题，字段2名称，字段2标题
function equals( field2Name, field2Caption, fields, index, caption )
{
	var fields2 = document.getElementsByName( field2Name );
	if( fields2.length <= index ){
		alert( "输入域[" + caption + "]和[" + field2Caption + "]的值不相同" );
		return false;
	}
	
	if( fields[index].value != fields2[index].value ){
		alert( "输入域[" + caption + "]和[" + field2Caption + "]的值不相同" );
		return false;
	}
	
	return true;
}



// 检查输入域的内容格式，安装正则表达式
function _test_dataformat( str, dataformat, caprion )
{
	if( dataformat == null || dataformat == '' ){
		return true;
	}
	
	// 删除前后边界
	if( dataformat.indexOf('/') == 0 && dataformat.lastIndexOf('/') == dataformat.length-1 ){
		dataformat = dataformat.substring(1, dataformat.length-1);
	}
	
	var reg = new RegExp( dataformat );
	if( reg.test(str) == false ){
		alert( "[" + caprion + "]输入数据的格式错误!" );
		return false;
	}
	else{
		return true;
	}
}




// md5
/*
 * A JavaScript implementation of the RSA Data Security, Inc. MD5 Message
 * Digest Algorithm, as defined in RFC 1321.
 * Copyright (C) Paul Johnston 1999 - 2000.
 * Updated by Greg Holt 2000 - 2001.
 * See http://pajhome.org.uk/site/legal.html for details.
 */

/*
 * Convert a 32-bit number to a hex string with ls-byte first
 */
var md5_hex_chr = "0123456789ABCDEF";
function rhex(num)
{
  str = "";
  for(j = 0; j <= 3; j++)
    str += md5_hex_chr.charAt((num >> (j * 8 + 4)) & 0x0F) +
           md5_hex_chr.charAt((num >> (j * 8)) & 0x0F);
  return str;
}

/*
 * Convert a string to a sequence of 16-word blocks, stored as an array.
 * Append padding bits and the length, as described in the MD5 standard.
 */
function str2blks_MD5(str)
{
  nblk = ((str.length + 8) >> 6) + 1;
  blks = new Array(nblk * 16);
  for(i = 0; i < nblk * 16; i++) blks[i] = 0;
  for(i = 0; i < str.length; i++)
    blks[i >> 2] |= str.charCodeAt(i) << ((i % 4) * 8);
  blks[i >> 2] |= 0x80 << ((i % 4) * 8);
  blks[nblk * 16 - 2] = str.length * 8;
  return blks;
}

/*
 * Add integers, wrapping at 2^32. This uses 16-bit operations internally 
 * to work around bugs in some JS interpreters.
 */
function __add(x, y)
{
  var lsw = (x & 0xFFFF) + (y & 0xFFFF);
  var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
  return (msw << 16) | (lsw & 0xFFFF);
}

/*
 * Bitwise rotate a 32-bit number to the left
 */
function rol(num, cnt)
{
  return (num << cnt) | (num >>> (32 - cnt));
}

/*
 * These functions implement the basic operation for each round of the
 * algorithm.
 */
function __cmn(q, a, b, x, s, t)
{
  return __add(rol(__add(__add(a, q), __add(x, t)), s), b);
}
function __ff(a, b, c, d, x, s, t)
{
  return __cmn((b & c) | ((~b) & d), a, b, x, s, t);
}
function __gg(a, b, c, d, x, s, t)
{
  return __cmn((b & d) | (c & (~d)), a, b, x, s, t);
}
function __hh(a, b, c, d, x, s, t)
{
  return __cmn(b ^ c ^ d, a, b, x, s, t);
}
function __ii(a, b, c, d, x, s, t)
{
  return __cmn(c ^ (b | (~d)), a, b, x, s, t);
}

/*
 * Take a string and return the hex representation of its MD5.
 */
function calcMD5(str)
{
  x = str2blks_MD5(str);
  a =  1732584193;
  b = -271733879;
  c = -1732584194;
  d =  271733878;

  for(i = 0; i < x.length; i += 16)
  {
    olda = a;
    oldb = b;
    oldc = c;
    oldd = d;

    a = __ff(a, b, c, d, x[i+ 0], 7 , -680876936);
    d = __ff(d, a, b, c, x[i+ 1], 12, -389564586);
    c = __ff(c, d, a, b, x[i+ 2], 17,  606105819);
    b = __ff(b, c, d, a, x[i+ 3], 22, -1044525330);
    a = __ff(a, b, c, d, x[i+ 4], 7 , -176418897);
    d = __ff(d, a, b, c, x[i+ 5], 12,  1200080426);
    c = __ff(c, d, a, b, x[i+ 6], 17, -1473231341);
    b = __ff(b, c, d, a, x[i+ 7], 22, -45705983);
    a = __ff(a, b, c, d, x[i+ 8], 7 ,  1770035416);
    d = __ff(d, a, b, c, x[i+ 9], 12, -1958414417);
    c = __ff(c, d, a, b, x[i+10], 17, -42063);
    b = __ff(b, c, d, a, x[i+11], 22, -1990404162);
    a = __ff(a, b, c, d, x[i+12], 7 ,  1804603682);
    d = __ff(d, a, b, c, x[i+13], 12, -40341101);
    c = __ff(c, d, a, b, x[i+14], 17, -1502002290);
    b = __ff(b, c, d, a, x[i+15], 22,  1236535329);    

    a = __gg(a, b, c, d, x[i+ 1], 5 , -165796510);
    d = __gg(d, a, b, c, x[i+ 6], 9 , -1069501632);
    c = __gg(c, d, a, b, x[i+11], 14,  643717713);
    b = __gg(b, c, d, a, x[i+ 0], 20, -373897302);
    a = __gg(a, b, c, d, x[i+ 5], 5 , -701558691);
    d = __gg(d, a, b, c, x[i+10], 9 ,  38016083);
    c = __gg(c, d, a, b, x[i+15], 14, -660478335);
    b = __gg(b, c, d, a, x[i+ 4], 20, -405537848);
    a = __gg(a, b, c, d, x[i+ 9], 5 ,  568446438);
    d = __gg(d, a, b, c, x[i+14], 9 , -1019803690);
    c = __gg(c, d, a, b, x[i+ 3], 14, -187363961);
    b = __gg(b, c, d, a, x[i+ 8], 20,  1163531501);
    a = __gg(a, b, c, d, x[i+13], 5 , -1444681467);
    d = __gg(d, a, b, c, x[i+ 2], 9 , -51403784);
    c = __gg(c, d, a, b, x[i+ 7], 14,  1735328473);
    b = __gg(b, c, d, a, x[i+12], 20, -1926607734);
    
    a = __hh(a, b, c, d, x[i+ 5], 4 , -378558);
    d = __hh(d, a, b, c, x[i+ 8], 11, -2022574463);
    c = __hh(c, d, a, b, x[i+11], 16,  1839030562);
    b = __hh(b, c, d, a, x[i+14], 23, -35309556);
    a = __hh(a, b, c, d, x[i+ 1], 4 , -1530992060);
    d = __hh(d, a, b, c, x[i+ 4], 11,  1272893353);
    c = __hh(c, d, a, b, x[i+ 7], 16, -155497632);
    b = __hh(b, c, d, a, x[i+10], 23, -1094730640);
    a = __hh(a, b, c, d, x[i+13], 4 ,  681279174);
    d = __hh(d, a, b, c, x[i+ 0], 11, -358537222);
    c = __hh(c, d, a, b, x[i+ 3], 16, -722521979);
    b = __hh(b, c, d, a, x[i+ 6], 23,  76029189);
    a = __hh(a, b, c, d, x[i+ 9], 4 , -640364487);
    d = __hh(d, a, b, c, x[i+12], 11, -421815835);
    c = __hh(c, d, a, b, x[i+15], 16,  530742520);
    b = __hh(b, c, d, a, x[i+ 2], 23, -995338651);

    a = __ii(a, b, c, d, x[i+ 0], 6 , -198630844);
    d = __ii(d, a, b, c, x[i+ 7], 10,  1126891415);
    c = __ii(c, d, a, b, x[i+14], 15, -1416354905);
    b = __ii(b, c, d, a, x[i+ 5], 21, -57434055);
    a = __ii(a, b, c, d, x[i+12], 6 ,  1700485571);
    d = __ii(d, a, b, c, x[i+ 3], 10, -1894986606);
    c = __ii(c, d, a, b, x[i+10], 15, -1051523);
    b = __ii(b, c, d, a, x[i+ 1], 21, -2054922799);
    a = __ii(a, b, c, d, x[i+ 8], 6 ,  1873313359);
    d = __ii(d, a, b, c, x[i+15], 10, -30611744);
    c = __ii(c, d, a, b, x[i+ 6], 15, -1560198380);
    b = __ii(b, c, d, a, x[i+13], 21,  1309151649);
    a = __ii(a, b, c, d, x[i+ 4], 6 , -145523070);
    d = __ii(d, a, b, c, x[i+11], 10, -1120210379);
    c = __ii(c, d, a, b, x[i+ 2], 15,  718787259);
    b = __ii(b, c, d, a, x[i+ 9], 21, -343485551);

    a = __add(a, olda);
    b = __add(b, oldb);
    c = __add(c, oldc);
    d = __add(d, oldd);
  }
  return rhex(a) + rhex(b) + rhex(c) + rhex(d);
}





