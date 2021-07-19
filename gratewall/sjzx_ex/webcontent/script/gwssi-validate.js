
/*
*************************************************************************************************
	#int			����
	#double			1-9��С����	
	#money			���
	#number			����
	#string			���ֳ�������λ
	#password		���룬ת����MD5
	#date			����			//1 ���� 2009-09-30 , 2 ���� 2009/09/30 , 3 ���� 20090930									
	#idcard			������15λ����18λ������
	#idcard18		18λ���֤�������15λ���֤���Զ�ת��
	#mail			E-Mail
	#phone			�绰����
	#phones			�绰�����б��ָ��[,|;]
	#mobile			�ƶ��绰
	#zip			��������
	#url			url��ַ
	#qq				qq����
	#file			file
	#calssName		JAVA������
*************************************************************************************************
*/

// ���ݸ�ʽ˵��
var _dataformatList = [
	[ 'int', '���������ܰ���С����' ],
	[ 'double', 'С�������԰��������ţ����԰���С����' ],
	[ 'money', '�����԰��������ţ����������λС����' ],
	[ 'number', '���֣����ܰ��������ţ����ܰ���С����' ],
	[ 'date1', '���ڣ��� [2009-09-30]' ],
	[ 'date2', '���ڣ��� [2009/09/30]' ],
	[ 'date3', '���ڣ��� [20090930]' ],
	[ 'date4', '���ڣ��� [2009��09��30��]' ],
	[ 'date5', '���ڣ��� [2005-07-13 14:51:06]' ],
	[ 'idcard', '15λ��18λ���֤����' ],
	[ 'idcard18', '18λ���֤����' ],
	[ 'mail', '�����ʼ���ַ���磺wh.zx@gwssi.com.cn' ],
	[ 'phone', '�绰���룬�磺010-87654321 �� 87654321' ],
	[ 'phones', 'һ�������绰���룬��[;]�ָ�磺010-87654321;87654321' ],
	[ 'mobile', '�ƶ��绰���룬�磺13*********' ],
	[ 'zip', '6λ����������' ],
	[ 'url', '��Ч����ַ���磺http://www.gwssi.com.cn' ],
	[ 'qq', '4~9λ��QQ����' ],
	[ 'calssName', 'JAVA�����ƣ�·������Сд�������ĵ�һ����ĸ�����д' ]
];


// У�����
function FormVerify() {
	this.dateFormatType=3;		//1 ���� 2009-09-30 , 2 ���� 2009/09/30 , 3 ���� 20090930 , 4 ���� 2009��09��30�� , 5 ���� 2005-07-13 14:51:06
	this.regDate1 = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
	this.exampleDate1="2009-09-30";
	this.regDate2 = /^(\d{1,4})(\/|\/)(\d{1,2})\2(\d{1,2})$/;
	this.exampleDate2="2009/09/30"; 
	this.regDate3 = /^(\d{1,4})(|\/)(\d{1,2})\2(\d{1,2})$/;
	this.exampleDate3="20090930";
	this.regDate4 = /^(\d{4})(��)(\d{2})(��)(\d{2})(��)$/;
	this.exampleDate4="2009��09��30��";
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


// ���ݱ�ת����ǰ��ԭʼ��Ϣ
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


// ȡ���ݵĸ�ʽ
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
			return '��ʽ��' + _dataformatList[ii][1];
		}
	}
	
	return null;
}


/* ************** ��������Ч�� *************** */

// �������Ч������Ķ�����
function checkInputFieldValid( fieldName )
{
	// ���
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


// ��������Ч�ԣ������Զ���Ĺ���
function _checkInputFieldValid( field, index )
{
	// �ж����FORM�͵�ǰ�ύ�����Ƿ�һ��
	if( currentFormName != null && field[index].form != null && field[index].form.name != currentFormName ){
		return true;
	}
	
	var flag = true;
	
	// �Ƿ�����Ч��
	if( field[index].checkFlag != 'true' ){
		return true;
	}
	
	// ȡ��Ч�Բ���
	var notnull = true;
	if( field[index].notnull != 'true' ){
		notnull = false;
	}
	
	// ��С���Ȼ���Сֵ
	var minlength = field[index].minvalue;
	if( minlength == null ){
		minlength = 0;
	}
	
	// ��󳤶Ȼ����ֵ
	var maxlength = field[index].maxvalue;
	if( maxlength == null ){
		maxlength = 0;
	}
	
	// ��������
	var datatype = field[index].datatype;
	if( datatype == null ){
		datatype = 'string';
	}
	
	// ���ݸ�ʽ
	var dataformat = field[index].dataformat;
	if( dataformat == null ){
		dataformat = '';
	}
	
	// ����
	var fieldCaption = field[index].fieldCaption;
	if( fieldCaption == null ){
		fieldCaption = '';
	}
	
	// �������Զ������
	flag = _checkFieldValidatorRule( field, index, field[index].validator, fieldCaption );
	if( flag == false ){
		return false;
	}
	
	// ͨ�ù�����
	flag = _checkFieldValid( field[index], notnull, minlength, maxlength, datatype, dataformat, fieldCaption );
	if( flag == false ){
		return false;
	}
	
	return true;
}

// �������Զ������
function _checkFieldValidatorRule( field, index, rule, fieldCaption )
{
	if( rule == null || rule == '' ){
		return true;
	}
	
	var flag = true;
	if( rule.indexOf('/') == 0 && rule.lastIndexOf('/') == rule.length-1 ){
		var obj = field[index];
		var _str = obj.value;
		
		// ����ʽ��Ч��:������ʽ[/���ʽ/]
		flag = _test_dataformat(_str, rule, fieldCaption);
	}
	else{
		// �Զ���У�麯��:�滻[']
		rule = rule.replace(/`/g, "'");
		
		// ���Ӳ���
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


// ��������Ч��
function _checkFieldValid(field, notnull, minlength, maxlength, datatype, dataformat, fieldCaption)
{
	if( minlength == null ){
		minlength = 0;
	}
	
	if( maxlength == null ){
		maxlength = 0;
	}
	
	// ת���ַ���
	if( dataformat == null ){
		dataformat = '';
	}
	else{
		dataformat = '' + dataformat;
	}
	
	// �Ƿ�����Ч��
	if( minlength == 0 && 
		maxlength == 0 &&
		notnull == false &&
		datatype == 'string' &&
		dataformat == ''
	)
	{
		return true;
	}
	
	// ȡֵ
	var _str=field.value;
	
	// �����ֵ�������Ƿ��ܹ���
	if( notnull == true && getStringLength(_str)==0 ){
		reset_valid_original_value();
  		alert("������[" + fieldCaption + "]����Ϊ�գ�");
  		field.focus();
  		return false;			
  	}
  	
  	// ת����Сд
  	datatype = datatype.toLowerCase();
  	
  	// ������ļ�����Ҫ�ȿ�accept,���汣����ļ���׺�����û����������������ж��Ƿ���ȱʡ���ļ�����
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
	
	// ���������Ч��
	var flag = checkValueValid( _str, minlength, maxlength, datatype, dataformat, fieldCaption );
	
	
	if (datatype=="date"){
		if (_str.length>0){
			// �޸�field.value
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

	// �ָ��ļ��ĺ�׺�б�
  	if( datatype == "file" ){
  		FormVerify.extArray = oldFileList;
  	}
	
	// �ж��Ƿ���Ч
	if( flag == false ){
		reset_valid_original_value();
		field.focus();
		return false;
	}
	
	// ת��18λ���֤ �� ��������
	if( _str != null && _str != '' ){
		// ����ԭʼ����
		add_valid_original_value_list( field, _str );
		
		if( datatype == "idcard18" ){
			field.value = transIdcard18( _str );
		}
		else if( datatype == "password" ){
			field.value = calcMD5( _str );
		}
	}
	
	// ������ʽ[/���ʽ/]
	if( dataformat.indexOf('/') == 0 && dataformat.lastIndexOf('/') == dataformat.length-1 ){
		flag = _test_dataformat(_str, dataformat, fieldCaption);
		if( flag == false ){
			field.focus();
			return false;
		}
	}
}


// ������ݵ���Ч��
function checkValueValid(value, minlength, maxlength, datatype, dataformat, fieldCaption)
{
	//>>>>int<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	if (datatype=="int"){			
		//������������
		if (value.length>0){
			if(isInteger(value,fieldCaption)==false){
				return false;
			}	
		
			//�������ֵĴ�С���ǿ�
			if (checkInteger(value,minlength,maxlength,fieldCaption)==false){
				return false;	
			}
		}
		return true;
		
	//>>>>double<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	}else if (datatype=="double"){
		//������������
		if (value.length>0){
			if(isDouble(value,fieldCaption)==false){
				return false;
			}
			
			//�������ֵĴ�С���ǿ�
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
			
			//������Ĵ�С���ǿ�
			if (checkInteger(value,minlength,maxlength,fieldCaption)==false){
				return false;	
			}
		}
		return true;
		
	//>>>>String<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	}else if (datatype=="string" || datatype=="password" || datatype=="password2"){
		//�����ַ����ĳ��ȡ��ǿ�
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
			alert("["+fieldCaption+"]���벻��Ϊ��!");
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
			alert("["+fieldCaption+"]���벻��Ϊ��!");
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
			alert("["+fieldCaption+"]���벻��Ϊ��!");
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
			alert("["+fieldCaption+"]���벻��Ϊ��!");
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
			alert("["+fieldCaption+"]���벻��Ϊ��!");
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
			alert("["+fieldCaption+"]���벻��Ϊ��!");
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
			alert("["+fieldCaption+"]���벻��Ϊ��!");
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
			alert("["+fieldCaption+"]���벻��Ϊ��!");
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
			alert("["+fieldCaption+"]���벻��Ϊ��!");
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
			alert("["+fieldCaption+"]���벻��Ϊ��!");
			return false;	
		}
		
		return true;
	
	//>>>>number<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if (datatype=="number"){
		//�����ַ����ĳ��ȡ��ǿ�
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
			alert("["+fieldCaption+"]���벻��Ϊ��!");
			return false;	
		}
		
		return true;
		
	//>>>>file<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 	
	}else if ( datatype != "" && datatype != null ){
		alert( fieldCaption + ':δ֪����������[' + datatype + ']' );
		return false;
		
	//>>>>default string<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 
	}else{
		//�����ַ����ĳ��ȡ��ǿ�
		if (checkStrLength(value,minlength,maxlength,fieldCaption)==false){
			return false;
		}
		
		return true;
	}//end if
}

/*	
�������ǿ�		
str				������ַ�
Msg				���������,������ʾ
����ֵ			true or false
*/
function isRequire(str,Msg) {
	var reg=FormVerify.regRequire;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]����Ϊ��!");
		return false;
	}else{		
		return true;
	}
}


/*	
���E-Mail����ĺϷ���		user@user.com
str						�����E-Mail
Msg						���������,������ʾ
����ֵ					true or false
*/
function isEmail(str,Msg) {
	var reg=FormVerify.regEmail;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]����ĸ�ʽ����ȷ!");
		return false;
	}else{		
		return true;
	}
}


/*	
���绰��������ĺϷ���		010-62967799 or 62967799
str						����ĵ绰����
Msg						���������,������ʾ
����ֵ					true or false
*/
function isPhone(str,Msg) {
	var reg=FormVerify.regPhone;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]����ĸ�ʽ����ȷ!");
		return false;
	}else{		
		return true;
	}
}

// �绰�����б�
function isPhones(str,Msg) {
	var reg=FormVerify.regPhones;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]����ĸ�ʽ����ȷ!");
		return false;
	}else{		
		return true;
	}
}


/* 
����ƶ��绰��������ĺϷ���		13310922255
str							������ƶ��绰����
Msg							���������,������ʾ
����ֵ						true or false
*/
function isMobile(str,Msg) {
	var reg=FormVerify.regMobile;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]����ĸ�ʽ����ȷ!");
		return false;
	}else{		
		return true;
	}
}


/* 
������ӵ�ַ����ĺϷ���		http://www.sina.com.cn
str						��������ӵ�ַ
Msg						���������,������ʾ
����ֵ					true or false
*/
function isUrl(str,Msg) {
	var reg=FormVerify.regUrl;
	if (reg.test(str.toLowerCase())==false){
		window.alert("["+Msg+"]����ĸ�ʽ����ȷ!");
		return false;
	}else{		
		return true;
	}
}

/* 
���JAVA����������ĺϷ���		cn.gwss.app.user.Operator
str						��������ӵ�ַ
Msg						���������,������ʾ
����ֵ					true or false
*/
function isClassName(str,Msg) {
	var ptr = str.indexOf( '{' );
	if( ptr > 0 ){
		var ptr1 = str.indexOf( '}', ptr );
		if( ptr1 > 0 ){
			var rs = str.substring( ptr, ptr1+1 );
			window.alert("["+Msg+"]���滻[" + rs + "]���ֵ�����!");
			return false;
		}
	}
	
	var reg=FormVerify.className;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]����ĸ�ʽ����ȷ!");
		return false;
	}else{		
		return true;
	}
}


/* 
������֤��������ĺϷ���	15λ����18λ������
str						��������֤����
Msg						���������,������ʾ
����ֵ					true or false
*/
function isIdCard(str,Msg) {
	var reg = (str.length==18) ? FormVerify.regIdCard18 : FormVerify.regIdCard15;
	if (reg.test(str)==false){
		window.alert("["+Msg+"]����ĸ�ʽ����ȷ!");
		return false;
	}else{
		//У��ȡֵ��Χ
		_arrayymd=getIdCardYearMonthDay(str,Msg);		
		if (checkYearMonthDay(_arrayymd,Msg)==false){
			alert("["+Msg+"]�����ڵ�ȡֵ��Χ����ȷ!");
			return false;		
		}	
	}//end if
	
	// ǿ�Ƽ�����֤��У��λ
	var	checkValue = strictCheckIdcard(str);
	if( checkValue != null ){
		alert( "["+Msg+"]��У��λ����ȷ��Ӧ����[" + checkValue + "]" );
		return false;
	}
	
	return true;
}


//�����֤��ȡ���ꡢ�¡��յ�����
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

// ȡ��������
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
		val = 'Ů';
	}
	else{
		val = '��';
	}
	
	return val;
}

/*
����Ƿ��ǽ������		2355.00
str					����Ľ��
Msg					���������,������ʾ
����ֵ				true or false
*/
function isMoney(str,Msg){
	var reg=FormVerify.regMoney;	
	if (reg.test(str)==false){
		alert("["+Msg+"]��ʽ������ 2355.00!");
		return false;	
	}else{
		return true;
	}
}

/*
����Ƿ�������		1-9 	
str				���������
Msg				���������,������ʾ
����ֵ			true or false
*/
function isNumber(str,Msg){
	var reg=FormVerify.regNumber;	
	if (reg.test(str)==false){
		alert("["+Msg+"]������0-9������!");
		return false;	
	}else{
		return true;
	}
}


/*
����Ƿ�����������		100085 	
str					�������������
Msg					���������,������ʾ
����ֵ				true or false
*/
function isZip(str,Msg){
	var reg=FormVerify.regZip;	
	if (reg.test(str)==false){
		alert("["+Msg+"]��������λ������!");
		return false;	
	}else{
		return true;
	}
}


/*
����Ƿ�������		 	+- 0-9
str					���������
Msg					���������,������ʾ
����ֵ				true or false
*/
function isInteger(str,Msg){
	var reg=FormVerify.regInteger;	
	if (reg.test(str)==false){
		alert("["+Msg+"]���벻����������!");
		return false;	
	}else{
		return true;
	}
}


/*
����Ƿ���QQ����		 	
str					�����QQ����
Msg					���������,������ʾ
����ֵ				true or false
*/
function isQQ(str,Msg){
	var reg=FormVerify.regQQ;	
	if (reg.test(str)==false){
		alert("["+Msg+"]��ʽ����ȷ!");
		return false;	
	}else{
		return true;
	}
}


/*
����Ƿ���Double����	12333.99	 	
str					�����Double����
Msg					���������,������ʾ
����ֵ				true or false
*/
function isDouble(str,Msg){
	var reg=FormVerify.regDouble;
	
	if (reg.test(str)==false){
		alert("["+Msg+"]���벻��Double����!");
		return false;	
	}else{
		return true;
	}
}

/*
����Ƿ���Ӣ��			a-z and A-Z	 	
str					�����Ӣ��
Msg					���������,������ʾ
����ֵ				true or false
*/
function isEnglish(str,Msg){
	var reg=FormVerify.English;	
	if (reg.test(str)==false){
		alert("["+Msg+"]ֻ������Ӣ����ĸ(a-z and A-Z)!");
		return false;	
	}else{
		return true;
	}
}


/*
����Ƿ��Ǻ���			ֻ���Ǻ���	 	
str					����ĺ���
Msg					���������,������ʾ
����ֵ				true or false
*/
function isChinese(str,Msg){
	var reg=FormVerify.regChinese;	
	if (reg.test(str)==false){
		alert("["+Msg+"]ֻ�����뺺��!");
		return false;	
	}else{
		return true;
	}
}


/*
����Ƿ��Ǻ��зǷ��ַ�				
str						������ַ�
Msg						���������,������ʾ
����ֵ					true or false
*/
function isUnSafe(str,Msg){
	var reg=FormVerify.regUnSafe;	
	if (reg.test(str)==false){
		alert("["+Msg+"]���뻹�зǷ��ַ�!");
		return false;	
	}else{
		return true;
	}
}


/*
����ϴ��ļ�����				
str						������ַ�
Msg						���������,������ʾ
����ֵ					true or false
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
		alert("["+Msg+"]ֻ���ϴ�"+ (FormVerify.extArray.join("  ")) + ",\n������ѡ���������ͣ�");
		return false;
	}
	
	return true;
}


/*
����Ƿ��ǺϷ����ڸ�ʽ,���ڵĸ�ʽ��FormVerify����ȡ��				
str						������ַ�
Msg						���������,������ʾ
����ֵ					true or false
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
		alert("["+Msg+"]��ʽ����ȷ,��ʽ������"+exampleDate+"!");
		return false;	
	}
	
	if (reg.test(str)==false){
		alert("["+Msg+"]��ʽ����ȷ,��ʽ������"+exampleDate+"!");
		return false;	
	}
	else{
		//У��ȡֵ��Χ
		var _array=getArrayDate(str,datetype);
		if (checkYearMonthDay(_array,Msg)==false){
			alert("["+Msg+"]ȡֵ��Χ����ȷ!");
			return false;		
		}
		else{
			return true;
		}
	}//end if
}

/*
����Ƿ��Ǻ��������ַ�				
str						������ַ�
Msg						���������,������ʾ
����ֵ					true or false
*/
function isNotChar(str,Msg){	
	reg=FormVerify.regNotChar;	
	if (reg.test(str)==false){
		alert("["+Msg+"]���ܺ��������ַ�!");
		return false;	
	}else{
		return true;
	}
}



/*
�ж�������ַ����ĳ��ȣ������������ַ�
str					����Ĵ�����ַ���
����ֵ				����
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
		return true; //ȫ���Ǻ���
	}else{
		return false; //�����ַ�
	}
}


/*
����������ݵĳ���(String��Number)
str				����Ĵ�����ַ���
msg 			���������,������ʾ
maxL			��󳤶�   0 �� С��minL �� ""
minL			��С����
����ֵ			true or false
*/
function checkStrLength(str,minL,maxL,Msg){
    if (minL>0){
    	if (getStringLength(str)==0){
    		alert("["+Msg+"]���벻��Ϊ�գ�");
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

// �Ƚ������ַ����ĳ���
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
	
	//ֻ�ж���С����
	if (maxL==0){
		if (getStringLength(str)<minL){
			alert("["+Msg+"]�������ݵĳ��ȱ������"+minL+"!");
			return false;	
    	}
    //�����������
	}
	else if(maxL<minL){		
		alert("����Ĳ���������󳤶ȱ��������С���ȣ�");
		return false;
	}
	else{
		//����󳤶Ⱥ���С����֮��
		if (maxL!=minL){
			if (getStringLength(str)<minL ||getStringLength(str)>maxL){
				alert("["+Msg+"]�������ݵĳ��ȱ������"+minL+" С��"+maxL+"!");
				return false;	
			}
		//�������
		}else{
			if (getStringLength(str)!=minL){
				alert("["+Msg+"]�������ݵĳ��ȱ������"+minL+"!");
				return false;	
			}
		}
	}
	
	return true;
}


/*
����������ݵĴ�С�Ƿ���ȷ(Integer��Doubleת����int�ڱȽϴ�С)
str					����Ĵ�����ַ���
msg 				���������,������ʾ
maxL				��󳤶�   0 �� С��minL �� ""
minL				��С����
����ֵ				true or false
*/
function checkInteger(str,minL,maxL,Msg){
	//���str
	if(isNaN(parseInt(str))==true){
		alert("���������["+str+"]���Ͳ���ȷ��");
		return false;
	}
	
	if( minL != maxL ){
		if((maxL-minL)<0){		
			alert("����Ĳ����������ֵ���������Сֵ");
			return false;
		}
		
		if( minL != -999999999 ){
			if( str - minL < 0 ){
				alert("["+Msg+"]�������ݱ������"+minL+"!");
				return false;	
	    }
	  }
	  
	  if( maxL != 999999999 ){
			if( str - maxL > 0 ){
				alert("["+Msg+"]�������ݱ���С��"+maxL+"!");
				return false;	
	    }
	  }
	}
}


function checkDouble(str,minL,maxL,Msg){
	//���str
	if(isNaN(parseFloat(str))==true){
		alert("���������["+str+"]���Ͳ���ȷ��");
		return false;
	}
	
	if( minL != maxL ){
		if((maxL-minL)<0){		
			alert("����Ĳ����������ֵ���������Сֵ");
			return false;
		}
		
		if( minL != -999999999 ){
		  if( str - minL < 0 ){
			alert("["+Msg+"]�������ݱ������"+minL+"!");
			return false;	
	      }
	    }
	  
	    if( maxL != 999999999 ){
		  if( str - maxL > 0 ){
			alert("["+Msg+"]�������ݱ���С��"+maxL+"!");
			return false;	
    	  }
	   }
	}
}


/*
���ڸ�ʽת�� 			ת����ɺ�ĸ�ʽ2009-09-09
str					���������,���������ڸ�ʽ���֮�������
formattype  		1 ���� 2009-09-30 , 2 ���� 2009/09/30 , 3 ���� 20090930
����ֵ				��ʽ���������
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
��������������յĺϷ���					
str							���������յ�����
Msg							���������,������ʾ
����ֵ						true or false
*/
function checkYearMonthDay(str,Msg){				
	if (str!=null){	
		//������	
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
		
		//������
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
		
		//������
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
    		//����ʱ
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
    		
    		//�����
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
    /*		//������
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
����ꡢ�¡��յ�����					
str							������֤�������
����ֵ						�ꡢ�¡�������
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






// �ϸ������֤����Ч��
function strictCheckIdcard( id )
{
	// ֻ���18λ���֤
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

// 15λ���֤ת����18λ���֤
function transIdcard18( id )
{
	if( id.length == 18 ){
		return id.toUpperCase();
	}
	
	if( id.length != 15 ){
		// alert( '���֤�ĳ��ȴ���' );
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


// ���������������Ƿ�һ��
// ����������ֶ�1�б��ֶ�1��ţ��ֶ�1���⣬�ֶ�2���ƣ��ֶ�2����
function equals( field2Name, field2Caption, fields, index, caption )
{
	var fields2 = document.getElementsByName( field2Name );
	if( fields2.length <= index ){
		alert( "������[" + caption + "]��[" + field2Caption + "]��ֵ����ͬ" );
		return false;
	}
	
	if( fields[index].value != fields2[index].value ){
		alert( "������[" + caption + "]��[" + field2Caption + "]��ֵ����ͬ" );
		return false;
	}
	
	return true;
}



// �������������ݸ�ʽ����װ������ʽ
function _test_dataformat( str, dataformat, caprion )
{
	if( dataformat == null || dataformat == '' ){
		return true;
	}
	
	// ɾ��ǰ��߽�
	if( dataformat.indexOf('/') == 0 && dataformat.lastIndexOf('/') == dataformat.length-1 ){
		dataformat = dataformat.substring(1, dataformat.length-1);
	}
	
	var reg = new RegExp( dataformat );
	if( reg.test(str) == false ){
		alert( "[" + caprion + "]�������ݵĸ�ʽ����!" );
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





